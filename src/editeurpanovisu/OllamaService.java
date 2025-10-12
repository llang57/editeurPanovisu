package editeurpanovisu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Service pour interagir avec des API IA (Ollama local ou Hugging Face en ligne)
 * Ollama API: https://github.com/ollama/ollama/blob/main/docs/api.md
 * Hugging Face: https://huggingface.co/inference-api
 */
public class OllamaService {
    
    // Configuration Ollama (local - préféré)
    private static final String OLLAMA_URL = "http://localhost:11434";
    private static final String GENERATE_ENDPOINT = "/api/generate";
    private static final String TAGS_ENDPOINT = "/api/tags";
    private static String ollamaModel = "mistral"; // Sera détecté automatiquement
    
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
    
    // Bloc static pour charger le token au démarrage
    static {
        chargerTokenHuggingFace();
    }
    
    /**
     * Charge le token Hugging Face depuis le fichier api-keys.properties
     */
    private static void chargerTokenHuggingFace() {
        try {
            // Chercher le fichier api-keys.properties à la racine du projet
            String cheminFichier = "api-keys.properties";
            Properties props = new Properties();
            
            // Essayer de charger depuis le fichier
            try (FileInputStream fis = new FileInputStream(cheminFichier)) {
                props.load(fis);
                HUGGINGFACE_TOKEN = props.getProperty("huggingface.api.key", "").trim();
                
                if (!HUGGINGFACE_TOKEN.isEmpty()) {
                    System.out.println("[IA] Token Hugging Face chargé depuis api-keys.properties");
                }
            } catch (Exception e) {
                // Fichier non trouvé, essayer depuis le classpath
                try (var stream = OllamaService.class.getClassLoader().getResourceAsStream("api-keys.properties")) {
                    if (stream != null) {
                        props.load(stream);
                        HUGGINGFACE_TOKEN = props.getProperty("huggingface.api.key", "").trim();
                        System.out.println("[IA] Token Hugging Face chargé depuis le classpath");
                    }
                } catch (Exception e2) {
                    System.out.println("[IA] Fichier api-keys.properties non trouvé");
                }
            }
            
            // Fallback sur System property si pas trouvé dans le fichier
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
     * Détecte le premier modèle Ollama disponible
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
            // Parser la réponse JSON pour trouver le premier modèle
            // Format: {"models":[{"name":"model_name",...},...]}
            if (jsonResponse.contains("\"models\"")) {
                int nameIndex = jsonResponse.indexOf("\"name\"");
                if (nameIndex > 0) {
                    int startQuote = jsonResponse.indexOf("\"", nameIndex + 7);
                    int endQuote = jsonResponse.indexOf("\"", startQuote + 1);
                    if (startQuote > 0 && endQuote > startQuote) {
                        String detectedModel = jsonResponse.substring(startQuote + 1, endQuote);
                        // Garder le nom complet avec le tag (ex: gpt-oss:20b)
                        ollamaModel = detectedModel;
                        System.out.println("[IA] Modèle Ollama détecté: " + detectedModel);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[IA] Impossible de détecter le modèle, utilisation de: " + ollamaModel);
        }
    }
    
    /**
     * Retourne le nom du service actuellement utilisé
     * @return "Ollama (local)" ou "Hugging Face (en ligne)"
     */
    public static String getServiceName() {
        return useHuggingFace ? "Hugging Face (en ligne)" : "Ollama (local)";
    }
    
    /**
     * Génère une description géographique basée sur les informations disponibles
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
                
                String prompt = construirePrompt(titreVisite, titrePanoramique, latitude, longitude, locale);
                System.out.println("[IA] Prompt construit (" + prompt.length() + " caractères)");
                
                return appellerOllama(prompt);
            } catch (Exception e) {
                System.out.println("[IA] ✗ ERREUR: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la génération : " + e.getMessage(), e);
            }
        });
    }
    
    /**
     * Construit le prompt pour Ollama selon les données disponibles et la langue
     */
    private static String construirePrompt(String titreVisite, String titrePanoramique, 
                                          String latitude, String longitude, Locale locale) {
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
        
        // Instruction de base
        if (langue.equals("français")) {
            prompt.append("Génère une description géographique courte et précise (2-3 phrases maximum) en français ");
        } else {
            prompt.append("Generate a short and precise geographical description (2-3 sentences maximum) in ").append(langue).append(" ");
        }
        
        // Ajouter les informations disponibles
        boolean hasInfo = false;
        
        if (titreVisite != null && !titreVisite.trim().isEmpty()) {
            if (langue.equals("français")) {
                prompt.append("pour la visite intitulée '").append(titreVisite).append("'");
            } else {
                prompt.append("for the tour titled '").append(titreVisite).append("'");
            }
            hasInfo = true;
        }
        
        if (titrePanoramique != null && !titrePanoramique.trim().isEmpty()) {
            if (hasInfo) {
                prompt.append(", ");
            }
            if (langue.equals("français")) {
                prompt.append("panoramique '").append(titrePanoramique).append("'");
            } else {
                prompt.append("panoramic '").append(titrePanoramique).append("'");
            }
            hasInfo = true;
        }
        
        if (latitude != null && !latitude.trim().isEmpty() && 
            longitude != null && !longitude.trim().isEmpty()) {
            if (hasInfo) {
                if (langue.equals("français")) {
                    prompt.append(" situé aux coordonnées ");
                } else {
                    prompt.append(" located at coordinates ");
                }
            } else {
                if (langue.equals("français")) {
                    prompt.append("pour le lieu situé aux coordonnées ");
                } else {
                    prompt.append("for the location at coordinates ");
                }
            }
            prompt.append("(").append(latitude).append(", ").append(longitude).append(")");
        }
        
        // Consignes finales
        if (langue.equals("français")) {
            prompt.append(". Décris le lieu de manière concise et informative, en mentionnant les caractéristiques géographiques, historiques ou culturelles importantes. Pas de formule de politesse, juste la description.");
        } else {
            prompt.append(". Describe the location concisely and informatively, mentioning important geographical, historical or cultural features. No greetings, just the description.");
        }
        
        return prompt.toString();
    }
    
    /**
     * Appelle l'API appropriée (Ollama ou Hugging Face) pour générer le texte
     */
    private static String appellerOllama(String prompt) throws Exception {
        if (useHuggingFace) {
            return appellerHuggingFace(prompt);
        } else {
            return appellerOllamaLocal(prompt);
        }
    }
    
    /**
     * Appelle l'API Ollama locale pour générer le texte
     */
    private static String appellerOllamaLocal(String prompt) throws Exception {
        URL url = new URL(OLLAMA_URL + GENERATE_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(60000); // 60 secondes pour la génération
        
        // Construire le JSON de requête avec le modèle détecté
        System.out.println("[IA] Utilisation du modèle Ollama: " + ollamaModel);
        String jsonRequest = String.format(
            "{\"model\":\"%s\",\"prompt\":\"%s\",\"stream\":false}",
            ollamaModel,
            prompt.replace("\"", "\\\"").replace("\n", "\\n")
        );
        
        // Envoyer la requête
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // Lire la réponse
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Erreur HTTP " + responseCode);
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
            "{\"inputs\":\"%s\",\"parameters\":{\"max_new_tokens\":200,\"temperature\":0.7,\"do_sample\":true}}",
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
