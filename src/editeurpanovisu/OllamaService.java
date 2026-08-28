package editeurpanovisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import editeurpanovisu.config.ModelConfig;
import editeurpanovisu.config.ModelConfigManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Service pour interagir avec des API IA (Ollama local ou Hugging Face en ligne)
 * Ollama API: https://github.com/ollama/ollama/blob/main/docs/api.md
 * Hugging Face: https://huggingface.co/inference-api
 * Nominatim API: https://nominatim.org/release-docs/latest/api/Reverse/
 */
public class OllamaService {
    
    // Configuration Ollama (local - préféré)
    private static final String OLLAMA_URL = "http://localhost:11434";

    /**
     * Température d'échantillonnage des générations.
     *
     * <p>Volontairement très basse. Ollama applique 0,8 par défaut — un réglage
     * d'écriture créative — lorsque le bloc {@code options} est absent de la requête,
     * ce qui était le cas : les consignes anti-hallucination du prompt étaient
     * contredites par l'échantillonnage lui-même.</p>
     *
     * <p>Abaisser ce paramètre réduit fortement l'invention, sans jamais la supprimer :
     * un modèle de langage reste génératif. La vérification humaine reste nécessaire.</p>
     */
    private static final double TEMPERATURE = 0.1;

    /** Noyau d'échantillonnage restreint, pour la même raison que {@link #TEMPERATURE}. */
    private static final double TOP_P = 0.5;

    /** Nombre de candidats retenus à chaque étape ; 40 par défaut chez Ollama. */
    private static final int TOP_K = 20;

    /**
     * Longueur de contexte imposée à Ollama, en jetons.
     *
     * <p>Sans cette valeur, Ollama applique le contexte maximal du modèle — souvent 128 000
     * jetons — et tente d'allouer le cache correspondant. Sur une machine courante la
     * génération échoue alors en HTTP 500, y compris pour un petit modèle : un phi3 de
     * 2,2 Go réclamait 37 Gio de cache. Le cas est d'autant plus fréquent que la variable
     * d'environnement OLLAMA_NUM_PARALLEL multiplie cette allocation.</p>
     *
     * <p>8192 jetons couvrent largement le prompt, descriptions précédentes comprises.</p>
     */
    private static final int NUM_CTX = 8192;

    /** Longueur maximale de la réponse : une description fait 4 à 5 phrases. */
    private static final int NUM_PREDICT = 400;
    private static final String GENERATE_ENDPOINT = "/api/generate";
    private static final String TAGS_ENDPOINT = "/api/tags";
    private static String ollamaModel = "mistral"; // Sera détecté automatiquement
    
    // Configuration LocationIQ pour géocodage inverse (5000 req/jour gratuit, OpenStreetMap)
    private static final String LOCATIONIQ_URL = "https://us1.locationiq.com/v1/reverse";
    private static String LOCATIONIQ_TOKEN = null;
    
    // Configuration OpenRouter (API unifiée pour GPT-4, Claude, etc. - PRIORITÉ 1)
    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static String OPENROUTER_TOKEN = null;
    // Modèles recommandés par ordre de préférence pour DESCRIPTIONS GÉOGRAPHIQUES
    // Optimisés pour : précision factuelle, coût, rapidité (Nov 2025)
    // 💚 ÉCONOMIQUE / Gratuit disponible : google/gemini-2.0-flash-exp:free
    // 💚 ÉCONOMIQUE : google/gemini-1.5-flash, openai/gpt-4o-mini
    // 💰 PREMIUM : anthropic/claude-3.5-sonnet, openai/gpt-4o
    private static final String[] OPENROUTER_MODELS = {
        "google/gemini-2.0-flash-exp:free",     // 🆓 GRATUIT - Gemini 2.0 Flash experimental
        "google/gemini-2.5-flash",              // 💚 Économique - Gemini 2.5 Flash
        "openai/gpt-4o-mini",                   // 💚 Bon compromis
        "anthropic/claude-3.5-sonnet",          // ⭐ Qualité élevée
        "openai/gpt-4o",                        // 💰 Premium
        "anthropic/claude-3-haiku",             // Rapide / économique
        "google/gemini-2.5-pro",                // 💰 Version pro - Gemini 2.5 Pro
        "mistralai/mistral-nemo",               // Modèle Mistral cloud
        "anthropic/claude-3-opus",              // 💎 Qualité absolue
        "openai/gpt-4-turbo"                    // Ancien modèle
    };
    private static String openrouterModel = OPENROUTER_MODELS[0]; // Gemini 2.0 Flash free par défaut
    
    // Configuration Hugging Face (fallback en ligne - gratuit)
    // Liste de modèles de fallback (essayés dans l'ordre)
    private static final String[] HUGGINGFACE_MODELS = {
        "Qwen/Qwen2.5-0.5B-Instruct",          // Qwen 2.5 (instruction following, léger)
        "microsoft/Phi-3-mini-4k-instruct",    // Phi-3 (optimisé pour instructions)
        "facebook/opt-350m",                   // OPT-350m (léger, génération)
        "gpt2"                                 // GPT-2 (fallback ultime)
    };
    
    private static String HUGGINGFACE_URL = "https://api-inference.huggingface.co/models/" + HUGGINGFACE_MODELS[0];
    private static int currentModelIndex = 0;
    
    // Token Hugging Face - Chargé depuis api-keys.properties
    private static String HUGGINGFACE_TOKEN = null;
    
    private static final int TIMEOUT_MS = 5000;
    private static boolean useHuggingFace = false;
    
    // Mode forcé : si true, force l'utilisation d'Ollama même si OpenRouter est disponible
    private static boolean forceOllama = false;
    
    // Gestionnaire de configuration des modèles
    private static ModelConfigManager configManager = new ModelConfigManager();
    
    // Bloc static pour charger les tokens au démarrage
    static {
        chargerTokensAPI();
        chargerEtVerifierModeles();
    }
    
    /**
     * Charge les tokens API depuis le fichier api-keys.properties
     */
    private static void chargerTokensAPI() {
        try {
            // Chercher le fichier api-keys.properties à la racine du projet
            String cheminFichier = "api-keys.properties";
            Properties props = new Properties();
            
            // Essayer de charger depuis le fichier
            try (FileInputStream fis = new FileInputStream(cheminFichier)) {
                props.load(fis);
                OPENROUTER_TOKEN = props.getProperty("openrouter.api.key", "").trim();
                HUGGINGFACE_TOKEN = props.getProperty("huggingface.api.key", "").trim();
                LOCATIONIQ_TOKEN = props.getProperty("locationiq.api.key", "").trim();
                
                if (!OPENROUTER_TOKEN.isEmpty()) {
                    System.out.println("[IA] ✓ Token OpenRouter chargé depuis api-keys.properties");
                    System.out.println("[IA] ✓ Modèle par défaut: " + openrouterModel);
                }
                if (!HUGGINGFACE_TOKEN.isEmpty()) {
                    System.out.println("[IA] Token Hugging Face chargé depuis api-keys.properties");
                }
                if (!LOCATIONIQ_TOKEN.isEmpty()) {
                    System.out.println("[GEO] Token LocationIQ chargé depuis api-keys.properties");
                }
            } catch (Exception e) {
                // Fichier non trouvé, essayer depuis le classpath
                try (var stream = OllamaService.class.getClassLoader().getResourceAsStream("api-keys.properties")) {
                    if (stream != null) {
                        props.load(stream);
                        OPENROUTER_TOKEN = props.getProperty("openrouter.api.key", "").trim();
                        HUGGINGFACE_TOKEN = props.getProperty("huggingface.api.key", "").trim();
                        LOCATIONIQ_TOKEN = props.getProperty("locationiq.api.key", "").trim();
                        System.out.println("[IA] Tokens chargés depuis le classpath");
                    }
                } catch (Exception e2) {
                    System.out.println("[IA] Fichier api-keys.properties non trouvé");
                }
            }
            
            // Fallback sur System property si pas trouvé dans le fichier
            if (OPENROUTER_TOKEN == null || OPENROUTER_TOKEN.isEmpty()) {
                OPENROUTER_TOKEN = System.getProperty("openrouter.token", "");
                if (!OPENROUTER_TOKEN.isEmpty()) {
                    System.out.println("[IA] Token OpenRouter chargé depuis -Dopenrouter.token");
                }
            }
            if (HUGGINGFACE_TOKEN == null || HUGGINGFACE_TOKEN.isEmpty()) {
                HUGGINGFACE_TOKEN = System.getProperty("huggingface.token", "");
                if (!HUGGINGFACE_TOKEN.isEmpty()) {
                    System.out.println("[IA] Token Hugging Face chargé depuis -Dhuggingface.token");
                }
            }
            
        } catch (Exception e) {
            System.out.println("[IA] Erreur lors du chargement du token: " + e.getMessage());
            HUGGINGFACE_TOKEN = "";
        }
    }
    
    /**
     * Charge les configurations de modèles et vérifie leur disponibilité
     */
    private static void chargerEtVerifierModeles() {
        try {
            // Charger les configurations depuis les fichiers JSON
            ModelConfig openRouterConfig = configManager.loadOpenRouterConfig();
            ModelConfig ollamaConfig = configManager.loadOllamaConfig();
            
            // Vérifier la disponibilité des modèles si activé dans la config
            if (openRouterConfig != null && openRouterConfig.isVerifyAtStartup() && 
                OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty()) {
                configManager.verifyOpenRouterModels(OPENROUTER_TOKEN);
            }
            
            if (ollamaConfig != null && ollamaConfig.isVerifyAtStartup()) {
                configManager.verifyOllamaModels();
            }
            
        } catch (Exception e) {
            System.err.println("[Config] ⚠️  Erreur lors de la vérification des modèles: " + e.getMessage());
            // Continue même en cas d'erreur - utilise les valeurs par défaut
        }
    }
    
    /**
     * Récupère le gestionnaire de configuration des modèles
     * @return ModelConfigManager instance
     */
    public static ModelConfigManager getConfigManager() {
        return configManager;
    }
    
    /**
     * Vérifie si OpenRouter est disponible (token configuré)
     * @return true si OpenRouter est disponible
     */
    public static boolean verifierOpenRouterDisponible() {
        return OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty();
    }
    
    /**
     * Vérifie si un service IA est disponible (Ollama ou Hugging Face)
     * @return true si au moins un service est disponible
     */
    public static boolean isOllamaAvailable() {
        // Essayer d'abord Ollama (local - plus rapide)
        System.out.println("[IA] Vérification de la disponibilité d'Ollama...");
        try {
            URL url = new URL(OLLAMA_URL + "/api/tags");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            
            int responseCode = conn.getResponseCode();
            
            if (responseCode == 200) {
                // Détecter le premier modèle disponible
                detecterModeleOllama(conn);
                conn.disconnect();
                useHuggingFace = false;
                System.out.println("[IA] ✓ Ollama disponible (local)");
                System.out.println("[IA] Modèle détecté: " + ollamaModel);
                return true;
            }
            
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("[IA] ✗ Ollama non disponible: " + e.getMessage());
        }
        
        // Fallback sur Hugging Face (en ligne - gratuit)
        System.out.println("[IA] Utilisation de Hugging Face (en ligne)...");
        
        // Vérification du token Hugging Face
        if (HUGGINGFACE_TOKEN.isEmpty()) {
            System.out.println("[IA] ✗ Token Hugging Face manquant");
            System.out.println("[IA] Pour utiliser Hugging Face:");
            System.out.println("[IA] 1. Créer un compte gratuit sur https://huggingface.co/join");
            System.out.println("[IA] 2. Obtenir un token sur https://huggingface.co/settings/tokens");
            System.out.println("[IA] 3. Configurer: -Dhuggingface.token=VOTRE_TOKEN");
            System.out.println("[IA] ✗ Aucun service IA disponible");
            return false;
        }
        
        useHuggingFace = true;
        
        // Valider le token en testant l'API
        System.out.println("[IA] Test du token Hugging Face...");
        try {
            URL testUrl = new URL("https://api-inference.huggingface.co/models/gpt2");
            HttpURLConnection testConn = (HttpURLConnection) testUrl.openConnection();
            testConn.setRequestMethod("POST");
            testConn.setRequestProperty("Authorization", "Bearer " + HUGGINGFACE_TOKEN);
            testConn.setRequestProperty("Content-Type", "application/json");
            testConn.setDoOutput(true);
            testConn.setConnectTimeout(TIMEOUT_MS);
            testConn.setReadTimeout(TIMEOUT_MS);
            
            // Envoyer une requête minimale pour tester
            String testRequest = "{\"inputs\":\"test\"}";
            try (OutputStream os = testConn.getOutputStream()) {
                os.write(testRequest.getBytes(StandardCharsets.UTF_8));
            }
            
            int responseCode = testConn.getResponseCode();
            testConn.disconnect();
            
            if (responseCode == 200 || responseCode == 503) {
                // 200 = OK, 503 = modèle en chargement (mais token valide)
                System.out.println("[IA] ✓ Token Hugging Face valide (HTTP " + responseCode + ")");
                System.out.println("[IA] ✓ Hugging Face disponible");
                return true;
            } else if (responseCode == 401 || responseCode == 403) {
                System.out.println("[IA] ✗ Token Hugging Face invalide (HTTP " + responseCode + ")");
                System.out.println("[IA] Vérifiez votre token sur https://huggingface.co/settings/tokens");
                return false;
            } else {
                System.out.println("[IA] ⚠️ Réponse inattendue: HTTP " + responseCode);
                // Continuer quand même, le modèle spécifique pourrait fonctionner
                return true;
            }
        } catch (Exception e) {
            System.out.println("[IA] ✗ Erreur de test token: " + e.getMessage());
            // En cas d'erreur réseau, essayer quand même
            return true;
        }
    }
    
    /**
     * Détecte automatiquement le meilleur modèle Ollama installé
     * Priorise les modèles les plus fiables pour éviter les hallucinations
     */
    private static void detecterModeleOllama(HttpURLConnection conn) {
        try {
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            
            String jsonResponse = response.toString();
            System.out.println("[IA] Recherche du meilleur modèle disponible...");
            
            // Ordre de préférence : modèles légers et fiables d'abord
            // DeepSeek-R1 32B : plus léger que le 70B (~20 GB vs 42 GB)
            String[] modelesPreferences = {
                "mistral-nemo", // Créatif mais précis, taille raisonnable (7 GB)
                "deepseek-r1",  // Raisonnement avancé, version 32B (~20 GB)
                "qwen2.5",      // Excellent pour les faits, très précis (4.7 GB)
                "llama3.1",     // Très fiable, moins d'hallucinations (4.9 GB)
                "gemma2",       // Performant de Google (5.4 GB)
                "llama3",       // Bonne alternative
                "phi3",         // Compact mais précis de Microsoft
                "mistral",      // Fallback classique
                "llama2"        // Fallback ancien
            };
            
            // Parser la réponse JSON pour trouver tous les modèles
            // Format: {"models":[{"name":"model_name",...},{"name":"autre_model",...},...]}
            if (jsonResponse.contains("\"models\"")) {
                String bestModel = null;
                int bestPriority = Integer.MAX_VALUE;
                
                // Chercher tous les modèles disponibles
                int searchPos = 0;
                while ((searchPos = jsonResponse.indexOf("\"name\"", searchPos)) > 0) {
                    int startQuote = jsonResponse.indexOf("\"", searchPos + 7);
                    int endQuote = jsonResponse.indexOf("\"", startQuote + 1);
                    if (startQuote > 0 && endQuote > startQuote) {
                        String modelName = jsonResponse.substring(startQuote + 1, endQuote);
                        System.out.println("[IA]   - Trouvé: " + modelName);
                        
                        // Vérifier si ce modèle est dans notre liste de préférences
                        for (int i = 0; i < modelesPreferences.length; i++) {
                            if (modelName.toLowerCase().contains(modelesPreferences[i])) {
                                if (i < bestPriority) {
                                    bestPriority = i;
                                    bestModel = modelName;
                                    System.out.println("[IA]   ✓ Meilleur modèle jusqu'ici: " + modelName + " (priorité " + i + ")");
                                }
                                break;
                            }
                        }
                    }
                    searchPos = endQuote + 1;
                }
                
                // Utiliser le meilleur modèle trouvé, ou le premier si aucun match
                if (bestModel != null) {
                    ollamaModel = bestModel;
                    System.out.println("[IA] ✓ MODÈLE SÉLECTIONNÉ: " + bestModel + " (recommandé pour précision)");
                } else {
                    // Fallback sur le premier modèle trouvé
                    int nameIndex = jsonResponse.indexOf("\"name\"");
                    if (nameIndex > 0) {
                        int startQuote = jsonResponse.indexOf("\"", nameIndex + 7);
                        int endQuote = jsonResponse.indexOf("\"", startQuote + 1);
                        if (startQuote > 0 && endQuote > startQuote) {
                            ollamaModel = jsonResponse.substring(startQuote + 1, endQuote);
                            System.out.println("[IA] ⚠ Modèle par défaut: " + ollamaModel + " (aucun modèle recommandé trouvé)");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[IA] ⚠ Impossible de détecter le modèle, utilisation de: " + ollamaModel);
        }
    }
    
    /**
     * Récupère la liste des modèles Ollama installés localement
     * @return Liste des noms de modèles, ou liste vide si erreur
     */
    public static java.util.List<String> getModelesOllamaDisponibles() {
        java.util.List<String> modeles = new java.util.ArrayList<>();
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(OLLAMA_URL + "/api/tags");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                conn.disconnect();
                return modeles; // Liste vide
            }
            
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();
            
            String jsonResponse = response.toString();
            // Parser les noms de modèles du JSON
            int searchPos = 0;
            while ((searchPos = jsonResponse.indexOf("\"name\"", searchPos)) > 0) {
                int startQuote = jsonResponse.indexOf("\"", searchPos + 7);
                int endQuote = jsonResponse.indexOf("\"", startQuote + 1);
                if (startQuote > 0 && endQuote > startQuote) {
                    String modelName = jsonResponse.substring(startQuote + 1, endQuote);
                    modeles.add(modelName);
                }
                searchPos = endQuote + 1;
            }
            
        } catch (Exception e) {
            System.out.println("[IA] Erreur récupération modèles Ollama: " + e.getMessage());
        }
        return modeles;
    }
    
    /**
     * Retourne les modèles OpenRouter recommandés pour la génération de descriptions
     * @return Tableau des modèles OpenRouter disponibles
     */
    public static String[] getModelesOpenRouterDisponibles() {
        // tester aussi la vacuité : une configuration de repli porte une liste vide mais non
        // nulle, ce qui rendait la constante OPENROUTER_MODELS inatteignable précisément
        // dans le cas qu'elle devait couvrir — l'utilisateur se retrouvait sans aucun modèle
        if (configManager.getOpenRouterConfig() != null
                && configManager.getOpenRouterConfig().getModels() != null
                && !configManager.getOpenRouterConfig().getModels().isEmpty()) {
            return configManager.getOpenRouterConfig().getEnabledModels().stream()
                .map(editeurpanovisu.config.ModelConfig.ModelEntry::getId)
                .toArray(String[]::new);
        }
        return OPENROUTER_MODELS;
    }
    
    /**
     * Définit le modèle Ollama à utiliser
     * @param modelName Nom du modèle
     */
    public static void setOllamaModel(String modelName) {
        if (modelName != null && !modelName.trim().isEmpty()) {
            ollamaModel = modelName;
            System.out.println("[IA] Modèle Ollama configuré: " + ollamaModel);
        }
    }
    
    /**
     * Définit le modèle OpenRouter à utiliser
     * @param modelName Nom du modèle
     */
    public static void setOpenRouterModel(String modelName) {
        if (modelName != null && !modelName.trim().isEmpty()) {
            openrouterModel = modelName;
            System.out.println("[IA] Modèle OpenRouter configuré: " + openrouterModel);
        }
    }
    
    /**
     * Retourne le modèle Ollama actuellement configuré
     * @return Nom du modèle Ollama
     */
    public static String getOllamaModel() {
        return ollamaModel;
    }
    
    /**
     * Retourne le modèle OpenRouter actuellement configuré
     * @return Nom du modèle OpenRouter
     */
    public static String getOpenRouterModel() {
        return openrouterModel;
    }
    
    /**
     * Force l'utilisation d'Ollama (mode offline)
     * @param force true pour forcer Ollama, false pour utiliser la priorité normale
     */
    public static void setForceOllama(boolean force) {
        forceOllama = force;
        System.out.println("[IA] Mode forcé: " + (force ? "Ollama (offline)" : "Priorité normale (OpenRouter puis Ollama)"));
    }
    
    /**
     * Vérifie si le mode Ollama forcé est actif
     * @return true si Ollama est forcé
     */
    public static boolean isForceOllama() {
        return forceOllama;
    }
    
    /**
     * Retourne le nom du service actuellement utilisé
     * @return "OpenRouter (XXX)" ou "Ollama (local)" ou "Hugging Face (en ligne)"
     */
    public static String getServiceName() {
        // Si mode forcé Ollama
        if (forceOllama) {
            return "Ollama (" + ollamaModel + ")";
        }
        
        // Vérifier si OpenRouter est disponible et a priorité
        if (OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty()) {
            // Extraire le nom court du modèle (sans le préfixe fournisseur)
            String modelShort = openrouterModel;
            if (modelShort.contains("/")) {
                modelShort = modelShort.substring(modelShort.lastIndexOf("/") + 1);
            }
            return "OpenRouter (" + modelShort + ")";
        }
        
        return useHuggingFace ? "Hugging Face (en ligne)" : "Ollama (local)";
    }
    
    /**
     * Géocodage inverse : convertit des coordonnées GPS en adresse/lieu réel
     * Utilise l'API LocationIQ (5000 req/jour gratuit, OpenStreetMap)
     * @param latitude Latitude
     * @param longitude Longitude
     * @return Adresse complète formatée, ou null si échec
     */
    private static String geocodeReverse(String latitude, String longitude) {
        try {
            System.out.println("[GEO] Géocodage inverse LocationIQ: " + latitude + ", " + longitude);
            
            if (LOCATIONIQ_TOKEN == null || LOCATIONIQ_TOKEN.isEmpty()) {
                System.out.println("[GEO] ✗ Token LocationIQ manquant dans api-keys.properties");
                return null;
            }
            
            // Construire l'URL avec les paramètres LocationIQ
            String urlStr = LOCATIONIQ_URL + "?key=" + LOCATIONIQ_TOKEN 
                          + "&lat=" + latitude + "&lon=" + longitude 
                          + "&format=json&zoom=18&addressdetails=1&accept-language=fr";
            
            @SuppressWarnings("deprecation")
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("[GEO] ✗ Erreur HTTP " + responseCode);
                return null;
            }
            
            // Lire la réponse JSON
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            
            String json = response.toString();
            
            // Afficher le JSON complet pour debug
            System.out.println("[GEO] ═══════════════════════════════════════════════════════════");
            System.out.println("[GEO] JSON COMPLET reçu de LocationIQ:");
            System.out.println("[GEO] " + json);
            System.out.println("[GEO] ═══════════════════════════════════════════════════════════");
            
            // Parser les champs structurés pour une description précise
            String lieuStructure = parserLocationIQ(json);
            if (lieuStructure != null && !lieuStructure.isEmpty()) {
                System.out.println("[GEO] ✓ Lieu analysé: " + lieuStructure);
                return lieuStructure;
            }
            
            System.out.println("[GEO] ✗ Aucun lieu trouvé");
            return null;
            
        } catch (Exception e) {
            System.out.println("[GEO] ✗ Erreur: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Parse la réponse JSON de LocationIQ et construit une description structurée
     * pour que l'IA comprenne précisément chaque élément géographique
     * Focus sur les informations touristiques/historiques, pas les détails de voirie
     * 
     * Champs OpenStreetMap importants (https://wiki.openstreetmap.org/wiki/Map_Features):
     * - tourism: viewpoint, attraction, museum, artwork, gallery, theme_park, zoo
     * - historic: castle, ruins, monument, memorial, archaeological_site, battlefield, fort
     * - amenity: place_of_worship, theatre, arts_centre, library, community_centre
     * - natural: peak, cave_entrance, beach, cliff, waterfall
     * - leisure: park, garden, nature_reserve, sports_centre
     * - building: cathedral, church, chapel, temple, mosque, synagogue
     */
    private static String parserLocationIQ(String json) {
        // Champs touristiques/culturels à explorer
        String[] champsTouristiques = {
            "tourism", "historic", "natural", "leisure", "amenity", "building",
            "attraction", "government", "museum", "garden", "park", "bridge", "viewpoint", "artwork", "gallery", "theme_park", "zoo",
            "monument", "memorial", "archaeological_site", "battlefield", "fort", "manor", "citywalls", "tower",
            "cathedral", "church", "temple", "mosque", "synagogue", "peak", "cave_entrance", "beach", "cliff", "waterfall"
        };
        // Afficher tous les champs touristiques présents dans le JSON
        System.out.println("[GEO] Champs touristiques/culturels présents dans le JSON:");
        for (String champ : champsTouristiques) {
            String val = extraireChamp(json, champ);
            if (val != null && !val.isEmpty()) {
                System.out.println("      - " + champ + " = " + val);
            }
        }

        // Extraction classique
        String tourism = extraireChamp(json, "tourism");
        String historic = extraireChamp(json, "historic");
        String natural = extraireChamp(json, "natural");
        String leisure = extraireChamp(json, "leisure");
        String amenity = extraireChamp(json, "amenity");
        String building = extraireChamp(json, "building");
        String name = extraireChamp(json, "name");
        String waterway = extraireChamp(json, "waterway");
        String village = extraireChamp(json, "village");
        String town = extraireChamp(json, "town");
        String city = extraireChamp(json, "city");
        String municipality = extraireChamp(json, "municipality");
        String county = extraireChamp(json, "county");
        String state = extraireChamp(json, "state");
        String region = extraireChamp(json, "region");
        String country = extraireChamp(json, "country");
        
        // IMPORTANT : Ignorer le champ "name" s'il s'agit d'un cours d'eau (waterway)
        // Les noms de cours d'eau sont souvent incorrects dans OpenStreetMap
        //if (waterway != null && !waterway.isEmpty()) {
        //    System.out.println("[GEO] ⚠️ Cours d'eau détecté (" + waterway + "), nom ignoré: " + name);
        //    name = null; // Ignorer le nom s'il s'agit d'un cours d'eau
        //}

        // Construire une description structurée et explicite
        StringBuilder description = new StringBuilder();
        
        // 1. Type de lieu (PRIORITÉ ABSOLUE : tourism, historic, natural, leisure)
        // Transmettre TOUJOURS le type original EN ANGLAIS (crucial pour l'IA) + traduction française
        if (tourism != null && !tourism.isEmpty()) {
            description.append("CATÉGORIE TOURISTIQUE: ").append(tourism).append(" (").append(tradTypeTourisme(tourism)).append(")");
        } else if (historic != null && !historic.isEmpty()) {
            description.append("CATÉGORIE HISTORIQUE: ").append(historic).append(" (").append(tradTypeHistorique(historic)).append(")");
        } else if (natural != null && !natural.isEmpty()) {
            description.append("CATÉGORIE NATURELLE: ").append(natural).append(" (").append(tradTypeNaturel(natural)).append(")");
        } else if (leisure != null && !leisure.isEmpty()) {
            description.append("CATÉGORIE LOISIR: ").append(leisure).append(" (").append(tradTypeLoisir(leisure)).append(")");
        } else if (building != null && !building.isEmpty()) {
            description.append("CATÉGORIE BÂTIMENT: ").append(building);
        } else if (amenity != null && !amenity.isEmpty()) {
            description.append("CATÉGORIE ÉQUIPEMENT: ").append(amenity);
        }
        
        // 2. Nom du lieu (si différent du type)
        if (name != null && !name.isEmpty() && !name.equals(tourism) && !name.equals(historic)) {
            if (description.length() > 0) description.append(" | ");
            description.append("NOM: ").append(name);
        }
        
        if (waterway != null && !waterway.isEmpty()) {
            description.append("CATÉGORIE COURS D'EAU: ").append(waterway).append(" (").append(tradTypeEau(waterway)).append(")");
        }

        // 3. Commune/Ville (ne pas répéter si c'est le même que le nom)
        String localite = null;
        if (village != null && !village.isEmpty() && !village.equals(name)) {
            localite = village;
            if (description.length() > 0) description.append(" | ");
            description.append("VILLAGE: ").append(village);
        } else if (town != null && !town.isEmpty() && !town.equals(name)) {
            localite = town;
            if (description.length() > 0) description.append(" | ");
            description.append("COMMUNE: ").append(town);
        } else if (municipality != null && !municipality.isEmpty() && !municipality.equals(name)) {
            localite = municipality;
            if (description.length() > 0) description.append(" | ");
            description.append("COMMUNE: ").append(municipality);
        } else if (city != null && !city.isEmpty() && !city.equals(name)) {
            localite = city;
            if (description.length() > 0) description.append(" | ");
            description.append("VILLE: ").append(city);
        }
        
        // 4. Département/Comté (si différent de la localité)
        if (county != null && !county.isEmpty() && !county.equals(localite)) {
            if (description.length() > 0) description.append(" | ");
            description.append("DÉPARTEMENT: ").append(county);
        }
        
        // 5. Région (préférer state à region si disponible)
        String regionName = (state != null && !state.isEmpty()) ? state : region;
        if (regionName != null && !regionName.isEmpty() && !regionName.equals(county)) {
            if (description.length() > 0) description.append(" | ");
            description.append("RÉGION: ").append(regionName);
        }
        
        // 6. Pays
        if (country != null && !country.isEmpty()) {
            if (description.length() > 0) description.append(" | ");
            description.append("PAYS: ").append(country);
        }
        
        String result = description.length() > 0 ? description.toString() : null;
        
        // Debug: afficher le résultat parsé
        if (result != null) {
            System.out.println("[GEO] Structure analysée:");
            for (String part : result.split(" \\| ")) {
                System.out.println("      - " + part);
            }
        }
        
        return result;
    }
    
    /**
     * Traduit les types de tourisme OpenStreetMap en français explicite
     */
    private static String tradTypeTourisme(String type) {
        switch (type.toLowerCase()) {
            case "viewpoint": return "Point de vue panoramique";
            case "attraction": return "Attraction touristique";
            case "museum": return "Musée";
            case "artwork": return "Œuvre d'art";
            case "gallery": return "Galerie";
            case "theme_park": return "Parc à thème";
            case "zoo": return "Zoo";
            case "information": return "Point d'information touristique";
            default: return "Site touristique - " + type;
        }
    }
    
    /**
     * Traduit les types historiques OpenStreetMap en français explicite
     */
    private static String tradTypeHistorique(String type) {
        switch (type.toLowerCase()) {
            case "castle": return "Château";
            case "ruins": return "Ruines";
            case "monument": return "Monument";
            case "memorial": return "Mémorial";
            case "archaeological_site": return "Site archéologique";
            case "battlefield": return "Champ de bataille";
            case "fort": return "Fort";
            case "manor": return "Manoir";
            case "citywalls": return "Remparts";
            case "tower": return "Tour historique";
            default: return "Site historique - " + type;
        }
    }
    
    /**
     * Traduit les types naturels OpenStreetMap en français explicite
     */
    private static String tradTypeNaturel(String type) {
        switch (type.toLowerCase()) {
            case "peak": return "Sommet";
            case "cave_entrance": return "Entrée de grotte";
            case "beach": return "Plage";
            case "cliff": return "Falaise";
            case "waterfall": return "Cascade";
            case "valley": return "Vallée";
            case "glacier": return "Glacier";
            case "volcano": return "Volcan";
            default: return "Site naturel - " + type;
        }
    }
    
    /**
     * Traduit les types de loisirs OpenStreetMap en français explicite
     */
    private static String tradTypeLoisir(String type) {
        switch (type.toLowerCase()) {
            case "park": return "Parc";
            case "garden": return "Jardin";
            case "nature_reserve": return "Réserve naturelle";
            case "sports_centre": return "Centre sportif";
            case "playground": return "Aire de jeux";
            default: return "Lieu de loisirs - " + type;
        }
    }
    /**
     * Traduit les types de loisirs OpenStreetMap en français explicite
     */
    private static String tradTypeEau(String type) {
        switch (type.toLowerCase()) {
            case "river": return "Rivière";
            case "stream": return "Ruisseau";
            case "canal": return "Canal";
            case "drain": return "Drain";
            case "ditch": return "Fossé";
            case "weir": return "Seuil";
            case "waterway": return "Cours d'eau";
            default: return "Cours d'eau - " + type;
        }
    }
    
    /**
     * Extrait un champ d'un JSON simple (sans bibliothèque)
     */
    private static String extraireChamp(String json, String fieldName) {
        String searchStr = "\"" + fieldName + "\":\"";
        int startIndex = json.indexOf(searchStr);
        if (startIndex < 0) return null;
        
        startIndex += searchStr.length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex < 0) return null;
        
        return json.substring(startIndex, endIndex)
                   .replace("\\\"", "\"")
                   .replace("\\n", " ");
    }
    
    // Cache des descriptions déjà générées pour éviter les redondances
    private static final java.util.Map<String, String> descriptionsCache = new java.util.concurrent.ConcurrentHashMap<>();
    
    /**
     * Réinitialise le cache des descriptions (à appeler au changement de visite)
     */
    public static void reinitialiserCacheDescriptions() {
        descriptionsCache.clear();
        System.out.println("[IA] Cache des descriptions réinitialisé");
    }
    
    /**
     * Génère une description géographique basée sur les informations disponibles
     * IMPORTANT : Tient compte des descriptions déjà générées pour éviter les redondances
     * @param titreVisite Titre de la visite (peut être null)
     * @param titrePanoramique Titre du panoramique (peut être null)
     * @param latitude Latitude (peut être null)
     * @param longitude Longitude (peut être null)
     * @param locale Locale pour la langue de la description
     * @return CompletableFuture avec la description générée
     */
    public static CompletableFuture<String> genererDescription(
            String titreVisite,
            String titrePanoramique, 
            String latitude,
            String longitude,
            Locale locale) {
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("[IA] Début de la génération de description");
                System.out.println("[IA] Service utilisé: " + getServiceName());
                System.out.println("[IA] Titre visite: " + titreVisite);
                System.out.println("[IA] Titre panoramique: " + titrePanoramique);
                System.out.println("[IA] GPS: " + latitude + ", " + longitude);
                
                // NOUVEAU : Géocodage inverse pour convertir GPS en lieu réel
                String lieuReel = null;
                if (latitude != null && !latitude.trim().isEmpty() && 
                    longitude != null && !longitude.trim().isEmpty()) {
                    lieuReel = geocodeReverse(latitude, longitude);
                }
                
                // Récupérer les descriptions déjà générées pour éviter les redondances
                java.util.List<String> descriptionsPrec = new java.util.ArrayList<>(descriptionsCache.values());
                System.out.println("[IA] Cache contient " + descriptionsPrec.size() + " description(s) précédente(s)");
                
                String prompt = construirePrompt(titreVisite, titrePanoramique, latitude, longitude, lieuReel, descriptionsPrec, locale);
                System.out.println("[IA] Prompt construit (" + prompt.length() + " caractères)");
                
                String description = appellerOllama(prompt);

                // Le prompt et la temperature basse raréfient l invention sans la supprimer.
                // On relit donc systématiquement la sortie pour signaler les énoncés qu aucun
                // modèle ne peut garantir : dates, mesures, classements, superlatifs.
                String contexteFourni = (titreVisite == null ? "" : titreVisite) + " "
                        + (titrePanoramique == null ? "" : titrePanoramique) + " "
                        + (lieuReel == null ? "" : lieuReel);
                VerificationDescription.verifieEtTrace(description, contexteFourni);
                
                // Après génération réussie, stocker dans le cache
                if (description != null && !description.trim().isEmpty()) {
                    String cacheKey = (titreVisite != null ? titreVisite : "") + ":" + 
                                      (titrePanoramique != null ? titrePanoramique : "");
                    descriptionsCache.put(cacheKey, description);
                    System.out.println("[IA] Description ajoutée au cache (clé: " + cacheKey + ")");
                }
                
                return description;
            } catch (Exception e) {
                System.out.println("[IA] ✗ ERREUR: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la génération : " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Construit le prompt pour Ollama selon les données disponibles et la langue
     * Utilise le géocodage inverse pour obtenir le lieu réel depuis les coordonnées GPS
     * NOUVEAU : Intègre les descriptions précédentes pour éviter les redondances
     */
    private static String construirePrompt(String titreVisite, String titrePanoramique, 
                                          String latitude, String longitude, String lieuReel, 
                                          java.util.List<String> descriptionsPrec, Locale locale) {
        StringBuilder prompt = new StringBuilder();
        
        // Déterminer la langue
        String langue = "français";
        if (locale != null) {
            String langCode = locale.getLanguage();
            switch (langCode) {
                case "en":
                    langue = "English";
                    break;
                case "de":
                    langue = "German";
                    break;
                case "es":
                    langue = "Spanish";
                    break;
                case "pt":
                    langue = "Portuguese";
                    break;
                default:
                    langue = "français";
            }
        }
        
        boolean hasLieuReel = (lieuReel != null && !lieuReel.trim().isEmpty());
        
        // PRIORITÉ 1 : Si on a réussi le géocodage inverse, utiliser le lieu RÉEL structuré !
        if (hasLieuReel) {
            if (langue.equals("français")) {
                prompt.append("LOCALISATION GÉOGRAPHIQUE EXACTE :\n")
                      .append(lieuReel)
                      .append("\n\n");
                
                // PRIORITÉ : Titre de la visite (ex: "Châteaux de Lastours") > Titre du pano (ex: "Le belvédère")
                String sujetPrincipal = null;
                if (titreVisite != null && !titreVisite.trim().isEmpty()) {
                    sujetPrincipal = titreVisite;
                    if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
                        // Les deux : "Châteaux de Lastours - Le belvédère"
                        sujetPrincipal += " - " + titrePanoramique;
                    }
                } else if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
                    sujetPrincipal = titrePanoramique;
                }
                
                if (sujetPrincipal != null) {
                    prompt.append("SUJET PRINCIPAL DE LA DESCRIPTION : ").append(sujetPrincipal).append("\n");
                }
                
                // NOUVEAU : Ajouter les descriptions précédentes pour éviter les redondances
                if (descriptionsPrec != null && !descriptionsPrec.isEmpty()) {
                    prompt.append("\n═══════════════════════════════════════════════════════════\n");
                    prompt.append("DESCRIPTIONS PRÉCÉDENTES (à ne PAS répéter) :\n");
                    prompt.append("═══════════════════════════════════════════════════════════\n");
                    for (int i = 0; i < descriptionsPrec.size(); i++) {
                        prompt.append("\nPanoramique ").append(i + 1).append(" :\n");
                        prompt.append(descriptionsPrec.get(i)).append("\n");
                    }
                    prompt.append("\n⚠️ IMPORTANT : NE RÉPÈTE PAS les informations déjà mentionnées ci-dessus.\n");
                    prompt.append("Focus sur des aspects DIFFÉRENTS et COMPLÉMENTAIRES.\n");
                    prompt.append("═══════════════════════════════════════════════════════════\n");
                }
                
                prompt.append("\nINSTRUCTIONS :\n")
                      .append("Tu es un guide touristique expert, factuel et rigoureux. Ta tâche est de décrire ce lieu avec une précision chirurgicale.\n")
                      .append("1. Génère une description factuelle et sobre de 4 à 5 phrases maximum en français.\n")
                      .append("2. CONCENTRE-TOI sur le SUJET PRINCIPAL, en te limitant STRICTEMENT aux informations fournies ci-dessus.\n")
                      .append("3. Tu n'as PAS vu l'image. Ne décris donc AUCUN détail visuel que les données ne permettent pas de déduire : ni bâtiment, ni relief, ni végétation, ni couleur, ni météo, ni ce qui serait visible à l'horizon.\n")
                      .append("4. Le village/commune n'est que le contexte géographique.\n")
                      .append("5. Ne commence JAMAIS ta réponse par des formules de politesse ('Voici la description'). Donne uniquement le texte final.\n");
            } else {
                prompt.append("EXACT GEOGRAPHICAL LOCATION:\n")
                      .append(lieuReel)
                      .append("\n\n");
                
                // PRIORITÉ : Titre de la visite > Titre du pano
                String mainSubject = null;
                if (titreVisite != null && !titreVisite.trim().isEmpty()) {
                    mainSubject = titreVisite;
                    if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
                        mainSubject += " - " + titrePanoramique;
                    }
                } else if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
                    mainSubject = titrePanoramique;
                }
                
                if (mainSubject != null) {
                    prompt.append("MAIN SUBJECT OF DESCRIPTION: ").append(mainSubject).append("\n");
                }
                
                // NOUVEAU : Ajouter les descriptions précédentes pour éviter les redondances
                if (descriptionsPrec != null && !descriptionsPrec.isEmpty()) {
                    prompt.append("\n═══════════════════════════════════════════════════════════\n");
                    prompt.append("PREVIOUS DESCRIPTIONS (DO NOT repeat) :\n");
                    prompt.append("═══════════════════════════════════════════════════════════\n");
                    for (int i = 0; i < descriptionsPrec.size(); i++) {
                        prompt.append("\nPanoramic ").append(i + 1).append(" :\n");
                        prompt.append(descriptionsPrec.get(i)).append("\n");
                    }
                    prompt.append("\n⚠️ IMPORTANT: DO NOT REPEAT information already mentioned above.\n");
                    prompt.append("Focus on DIFFERENT and COMPLEMENTARY aspects.\n");
                    prompt.append("═══════════════════════════════════════════════════════════\n");
                }
                
                prompt.append("\nINSTRUCTIONS:\n")
                      .append("You are an expert, factual, and rigorous tour guide. Your task is to describe this place with surgical precision.\n")
                      .append("1. Generate a factual, restrained description of 4 to 5 sentences maximum in ").append(langue).append(".\n")
                      .append("2. FOCUS on the MAIN SUBJECT, restricting yourself STRICTLY to the information provided above.\n")
                      .append("3. You have NOT seen the image. Therefore describe NO visual detail the data does not support: no building, terrain, vegetation, colour, weather, or anything supposedly visible on the horizon.\n")
                      .append("4. The village/town is only the geographical context.\n")
                      .append("5. NEVER start your response with conversational filler ('Here is the description'). Output ONLY the final text.\n");
            }
            
        } else {
            // SANS GPS : construire avec titres seulement (moins fiable)
            if (langue.equals("français")) {
                prompt.append("Génère une description géographique courte (4-5 phrases) en français ");
            } else {
                prompt.append("Generate a short geographical description (4-5 sentences) in ").append(langue).append(" ");
            }
            
            if (titreVisite != null && !titreVisite.trim().isEmpty()) {
                if (langue.equals("français")) {
                    prompt.append("pour la visite '").append(titreVisite).append("'");
                } else {
                    prompt.append("for the tour '").append(titreVisite).append("'");
                }
            }
            
            if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
                if (langue.equals("français")) {
                    prompt.append(", panoramique '").append(titrePanoramique).append("'");
                } else {
                    prompt.append(", panoramic '").append(titrePanoramique).append("'");
                }
            }
        }
        
        // Consignes finales - ULTRA STRICTES (5 phrases factuelles)
        if (langue.equals("français")) {
            prompt.append("\n\n🚫 RÈGLES ANTI-HALLUCINATION ABSOLUES :\n");
            prompt.append("1) N'INVENTE AUCUN nom propre : ni bâtiment, ni monument, ni personne, ni événement historique.\n");
            prompt.append("2) N'INVENTE AUCUN nombre : ni date, ni siècle, ni hauteur, ni superficie, ni distance, ni fréquentation.\n");
            prompt.append("3) N'ATTRIBUE AUCUNE distinction : ni classement au patrimoine, ni label, ni protection, ni récompense.\n");
            prompt.append("4) N'EMPLOIE AUCUN superlatif ni qualificatif de notoriété (célèbre, renommé, incontournable, le plus beau, prisé).\n");
            prompt.append("5) Tu ne disposes QUE des informations ci-dessus. Tout ce qui n'en découle pas doit être OMIS : il vaut mieux une description plus courte qu'un détail inventé.\n");
            prompt.append("6) En l'absence de connaissance certaine sur ce lieu exact, reste au niveau du contexte géographique fourni (situation, commune, département, pays) sans ajouter de détail matériel.\n");
            prompt.append("7) N'exprime aucun doute par écrit : ne produis ni \"peut-être\", ni \"sans doute\", ni \"il se pourrait\". Si tu n'es pas certain, n'écris simplement pas l'information.\n");
            prompt.append("8) Ta réponse doit contenir exactement le texte de la description, sans aucune méta-phrase (pas de \"Voici...\").\n");
        } else {
            prompt.append("\n\n🚫 ABSOLUTE ANTI-HALLUCINATION RULES:\n");
            prompt.append("1) NEVER INVENT a proper name: no building, monument, person, or historical event.\n");
            prompt.append("2) NEVER INVENT a number: no date, century, height, area, distance, or visitor figure.\n");
            prompt.append("3) NEVER ATTRIBUTE a distinction: no heritage listing, label, protected status, or award.\n");
            prompt.append("4) NEVER USE superlatives or fame adjectives (famous, renowned, must-see, the most beautiful, popular).\n");
            prompt.append("5) You have ONLY the information above. Anything not derived from it must be OMITTED: a shorter description is better than an invented detail.\n");
            prompt.append("6) Without certain knowledge of this exact place, stay at the level of the geographical context provided (location, town, region, country) and add no material detail.\n");
            prompt.append("7) Do not express doubt in writing: no \"perhaps\", \"probably\", or \"it may be\". If you are unsure, simply omit the information.\n");
            prompt.append("8) Your response must contain exactly the description text, with no meta phrases (no \"Here is...\").\n");
        }
        
        String finalPrompt = prompt.toString();
        
        // AFFICHER LE PROMPT COMPLET POUR DEBUG
        System.out.println("[IA] ═══════════════════════════════════════════════════════════");
        System.out.println("[IA] PROMPT COMPLET envoyé à l'IA:");
        System.out.println("[IA] ───────────────────────────────────────────────────────────");
        System.out.println(finalPrompt);
        System.out.println("[IA] ═══════════════════════════════════════════════════════════");
        
        return finalPrompt;
    }
    
    /**
     * Appelle l'API appropriée (Ollama ou Hugging Face) pour générer le texte
     */
    private static String appellerOllama(String prompt) throws Exception {
        // PRIORITÉ 1 : OpenRouter (GPT-4, Claude, etc.) si token disponible ET pas en mode forcé Ollama
        if (!forceOllama && OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty()) {
            try {
                System.out.println("[IA] ➤ Tentative OpenRouter (" + openrouterModel + ")...");
                String resultat = appellerOpenRouter(prompt);
                System.out.println("[IA] ✓ OpenRouter : Succès");
                return resultat;
            } catch (Exception e) {
                System.out.println("[IA] ✗ OpenRouter échec: " + e.getMessage());
                System.out.println("[IA] ➤ Fallback vers Ollama local...");
            }
        } else if (forceOllama) {
            System.out.println("[IA] 🔸 Mode Ollama forcé - Skip OpenRouter");
        }
        
        // PRIORITÉ 2 : Ollama local (si disponible)
        if (!useHuggingFace) {
            try {
                return appellerOllamaLocal(prompt);
            } catch (Exception e) {
                System.out.println("[IA] ✗ Ollama local échec: " + e.getMessage());
                
                // Afficher un message d'erreur clair plutôt que d'essayer Hugging Face
                String messageErreur = "❌ Aucun service IA disponible !\n\n";
                
                if (forceOllama) {
                    messageErreur += "Mode Ollama forcé mais Ollama n'est pas disponible.\n";
                    messageErreur += "Solutions:\n";
                    messageErreur += "1. Démarrez Ollama (ollama serve)\n";
                    messageErreur += "2. Basculez en mode Online (🌐) pour utiliser OpenRouter\n";
                } else if (OPENROUTER_TOKEN == null || OPENROUTER_TOKEN.isEmpty()) {
                    messageErreur += "OpenRouter non configuré ET Ollama non disponible.\n";
                    messageErreur += "Solutions:\n";
                    messageErreur += "1. Configurez votre clé OpenRouter dans Fichier > Configuration\n";
                    messageErreur += "2. OU installez et démarrez Ollama (gratuit, local)\n";
                } else {
                    messageErreur += "OpenRouter et Ollama ont échoué.\n";
                    messageErreur += "Solutions:\n";
                    messageErreur += "1. Vérifiez votre connexion internet\n";
                    messageErreur += "2. Vérifiez votre clé API OpenRouter\n";
                    messageErreur += "3. Démarrez Ollama si installé (ollama serve)\n";
                }
                
                throw new Exception(messageErreur);
            }
        }
        
        // Si useHuggingFace est true (ne devrait plus arriver)
        throw new Exception("❌ Mode Hugging Face désactivé. Utilisez OpenRouter ou Ollama.");
    }
    
    /**
     * Appelle OpenRouter API (accès unifié à GPT-4, Claude, Gemini, etc.)
     * Documentation: https://openrouter.ai/docs
     */
    private static String appellerOpenRouter(String prompt) throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL(OPENROUTER_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + OPENROUTER_TOKEN);
        conn.setRequestProperty("HTTP-Referer", "https://github.com/editeurpanovisu"); // Optionnel
        conn.setRequestProperty("X-Title", "EditeurPanovisu"); // Optionnel
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(60000); // 60 secondes pour la génération
        
        // Construire le JSON de requête (format OpenAI compatible)
        // max_tokens augmenté à 1500 pour éviter les coupures (finish_reason: length)
        // Même construction par Gson que pour Ollama, et même température basse : la
        // valeur précédente (0,3) restait propice à la broderie sur des lieux peu connus.
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        JsonArray messages = new JsonArray();
        messages.add(message);
        JsonObject corpsRequete = new JsonObject();
        corpsRequete.addProperty("model", openrouterModel);
        corpsRequete.add("messages", messages);
        corpsRequete.addProperty("max_tokens", 1500);
        corpsRequete.addProperty("temperature", TEMPERATURE);
        corpsRequete.addProperty("top_p", TOP_P);
        String jsonRequest = corpsRequete.toString();
        
        // Envoyer la requête
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Lire la réponse
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            // Lire le message d'erreur
            StringBuilder errorMsg = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    errorMsg.append(line);
                }
            }
            throw new Exception("OpenRouter HTTP " + responseCode + ": " + errorMsg.toString());
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        
        conn.disconnect();
        
        // Parser la réponse JSON (format OpenAI)
        String jsonResponse = response.toString();
        System.out.println("[IA] OpenRouter réponse JSON: " + jsonResponse.substring(0, Math.min(200, jsonResponse.length())) + "...");
        
        // Extraire le contenu de choices[0].message.content
        int contentStart = jsonResponse.indexOf("\"content\":\"");
        if (contentStart == -1) {
            throw new Exception("Format de réponse OpenRouter invalide");
        }
        contentStart += 11; // Longueur de "\"content\":\""
        
        int contentEnd = jsonResponse.indexOf("\"", contentStart);
        String texteGenere = jsonResponse.substring(contentStart, contentEnd);
        
        // Décoder les caractères échappés
        texteGenere = texteGenere.replace("\\n", "\n")
                                 .replace("\\\"", "\"")
                                 .replace("\\\\", "\\");
        
        return texteGenere.trim();
    }
    
    /**
     * Appelle l'API Ollama locale pour générer le texte
     */
    private static String appellerOllamaLocal(String prompt) throws Exception {
        // VÉRIFICATION CRITIQUE: S'assurer que le modèle existe avant l'appel
        java.util.List<String> modelesDisponibles = getModelesOllamaDisponibles();
        if (modelesDisponibles.isEmpty()) {
            throw new Exception("❌ Ollama est démarré mais aucun modèle n'est installé.\n" +
                              "Installez un modèle avec: ollama pull mistral-nemo");
        }
        
        // Vérifier si le modèle configuré existe (avec ou sans tag :latest)
        boolean modeleExiste = false;
        String modeleComplet = ollamaModel;
        // 1) correspondance EXACTE, tag compris. Indispensable : ne comparer que le nom de
        // base faisait resoudre "qwen2.5:14b" en "qwen2.5:32b" si ce dernier venait en
        // premier — le modele demande etait alors remplace par une variante bien plus
        // lourde, qui echouait faute de memoire (HTTP 500).
        for (String modele : modelesDisponibles) {
            if (modele.equals(ollamaModel)) {
                modeleExiste = true;
                modeleComplet = modele;
                break;
            }
        }
        // 2) a defaut seulement, correspondance sur le nom de base, pour accepter
        // "mistral-nemo" quand "mistral-nemo:latest" est installe
        if (!modeleExiste) {
            String ollamaBase = ollamaModel.contains(":") ? ollamaModel.substring(0, ollamaModel.indexOf(":")) : ollamaModel;
            for (String modele : modelesDisponibles) {
                String modeleBase = modele.contains(":") ? modele.substring(0, modele.indexOf(":")) : modele;
                if (modeleBase.equals(ollamaBase)) {
                    modeleExiste = true;
                    modeleComplet = modele;
                    break;
                }
            }
        }
        
        if (!modeleExiste) {
            System.err.println("❌ Modèle '" + ollamaModel + "' non trouvé dans Ollama!");
            System.err.println("   Modèles disponibles: " + String.join(", ", modelesDisponibles));
            // Utiliser le premier modèle disponible comme fallback
            modeleComplet = modelesDisponibles.get(0);
            System.err.println("   ➤ Fallback vers: " + modeleComplet);
        }
        
        // Utiliser le nom complet du modèle (avec :latest si nécessaire)
        ollamaModel = modeleComplet;
        System.out.println("[IA] ✓ Modèle validé: " + ollamaModel);
        
        URL url = new URL(OLLAMA_URL + GENERATE_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(60000); // 60 secondes pour la génération
        
        // Construire le JSON de requête avec le modèle détecté
        System.out.println("[IA] Utilisation du modèle Ollama: " + ollamaModel);
        // JSON construit par Gson : l'échappement manuel précédent ne traitait pas
        // l'antislash, si bien qu'un titre de panoramique en contenant un produisait une
        // requête invalide, voire altérait les consignes envoyées au modèle.
        JsonObject options = new JsonObject();
        options.addProperty("temperature", TEMPERATURE);
        options.addProperty("top_p", TOP_P);
        options.addProperty("top_k", TOP_K);
        options.addProperty("num_ctx", NUM_CTX);
        options.addProperty("num_predict", NUM_PREDICT);
        JsonObject corpsRequete = new JsonObject();
        corpsRequete.addProperty("model", ollamaModel);
        corpsRequete.addProperty("prompt", prompt);
        corpsRequete.addProperty("stream", false);
        corpsRequete.add("options", options);
        String jsonRequest = corpsRequete.toString();
        
        // Envoyer la requête
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Lire la réponse
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            // Message d'erreur plus détaillé selon le code HTTP
            String errorMsg = "Erreur HTTP " + responseCode + " pour " + ollamaModel;
            if (responseCode == 500) {
                errorMsg += " (mémoire insuffisante? Essayez un modèle plus léger)";
            }
            throw new Exception(errorMsg);
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        
        conn.disconnect();
        
        // Parser le JSON pour extraire la réponse
        String jsonResponse = response.toString();
        return extraireReponse(jsonResponse);
    }
    
    /**
     * Appelle l'API Hugging Face pour générer le texte
     */
    private static String appellerHuggingFace(String prompt) throws Exception {
        System.out.println("[IA] Appel de l'API Hugging Face...");
        System.out.println("[IA] URL: " + HUGGINGFACE_URL);
        
        // Afficher le token (masqué pour la sécurité)
        if (HUGGINGFACE_TOKEN != null && !HUGGINGFACE_TOKEN.isEmpty()) {
            String tokenMasque = HUGGINGFACE_TOKEN.substring(0, Math.min(10, HUGGINGFACE_TOKEN.length())) + "...";
            System.out.println("[IA] Token chargé: " + tokenMasque + " (longueur: " + HUGGINGFACE_TOKEN.length() + ")");
        } else {
            System.out.println("[IA] ⚠️ ATTENTION: Aucun token configuré !");
        }
        
        URL url = new URL(HUGGINGFACE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (!HUGGINGFACE_TOKEN.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + HUGGINGFACE_TOKEN);
        }
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(120000); // 120 secondes (modèle peut être en chargement)
        
        // Construire le JSON de requête adapté au modèle
        String modelName = HUGGINGFACE_MODELS[currentModelIndex];
        String jsonRequest;
        
        // Paramètres optimisés pour génération de descriptions courtes
        jsonRequest = String.format(
            "{\"inputs\":\"%s\",\"parameters\":{\"max_new_tokens\":200,\"temperature\":0.1,\"do_sample\":false}}",
            prompt.replace("\"", "\\\"").replace("\n", "\\n")
        );
        
        System.out.println("[IA] Envoi de la requête...");
        
        // Envoyer la requête
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Lire la réponse
        int responseCode = conn.getResponseCode();
        System.out.println("[IA] Code de réponse HTTP: " + responseCode);
        
        // 503 = modèle en cours de chargement, attendre et réessayer
        if (responseCode == 503) {
            conn.disconnect();
            System.out.println("[IA] Modèle en cours de chargement, attente de 10 secondes...");
            Thread.sleep(10000); // Attendre 10 secondes
            return appellerHuggingFace(prompt); // Réessayer
        }
        
        // 404 = modèle non trouvé, essayer le modèle suivant
        if (responseCode == 404 && currentModelIndex < HUGGINGFACE_MODELS.length - 1) {
            conn.disconnect();
            currentModelIndex++;
            HUGGINGFACE_URL = "https://api-inference.huggingface.co/models/" + HUGGINGFACE_MODELS[currentModelIndex];
            System.out.println("[IA] Modèle non trouvé, essai avec: " + HUGGINGFACE_MODELS[currentModelIndex]);
            return appellerHuggingFace(prompt); // Réessayer avec le modèle suivant
        }
        
        if (responseCode != 200) {
            // Lire le message d'erreur
            StringBuilder errorMsg = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    errorMsg.append(line);
                }
            }
            throw new Exception("Erreur Hugging Face (HTTP " + responseCode + "): " + errorMsg.toString());
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        
        conn.disconnect();
        
        // Parser le JSON pour extraire la réponse Hugging Face
        String jsonResponse = response.toString();
        return extraireReponseHuggingFace(jsonResponse);
    }
    
    /**
     * Extrait la réponse du JSON retourné par Ollama
     */
    private static String extraireReponse(String json) {
        // Simple extraction du champ "response" sans utiliser de bibliothèque JSON
        int startIndex = json.indexOf("\"response\":\"");
        if (startIndex == -1) {
            return "Erreur: Impossible de parser la réponse";
        }
        
        startIndex += 12; // Longueur de "\"response\":\""
        int endIndex = json.indexOf("\"", startIndex);
        
        if (endIndex == -1) {
            return "Erreur: Impossible de parser la réponse";
        }
        
        String response = json.substring(startIndex, endIndex);
        
        // Déchapper les caractères spéciaux
        response = response.replace("\\n", "\n")
                          .replace("\\\"", "\"")
                          .replace("\\\\", "\\");
        
        return response.trim();
    }
    
    /**
     * Extrait la réponse du JSON retourné par Hugging Face
     */
    private static String extraireReponseHuggingFace(String json) {
        // Format Hugging Face: [{"generated_text":"..."}]
        int startIndex = json.indexOf("\"generated_text\":\"");
        if (startIndex == -1) {
            // Essayer aussi le format alternatif
            startIndex = json.indexOf("\"generated_text\": \"");
            if (startIndex == -1) {
                return "Erreur: Impossible de parser la réponse Hugging Face";
            }
            startIndex += 19; // Longueur de "\"generated_text\": \""
        } else {
            startIndex += 18; // Longueur de "\"generated_text\":\""
        }
        
        int endIndex = json.indexOf("\"", startIndex);
        
        if (endIndex == -1) {
            return "Erreur: Impossible de parser la réponse Hugging Face";
        }
        
        String response = json.substring(startIndex, endIndex);
        
        // Déchapper les caractères spéciaux
        response = response.replace("\\n", "\n")
                          .replace("\\\"", "\"")
                          .replace("\\\\", "\\")
                          .replace("\\t", "\t");
        
        return response.trim();
    }
}
