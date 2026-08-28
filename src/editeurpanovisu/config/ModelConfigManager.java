package editeurpanovisu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

/**
 * Gestionnaire de configuration des modèles IA
 * Charge, sauvegarde et vérifie la disponibilité des modèles
 */
public class ModelConfigManager {
    
    private static final String OPENROUTER_CONFIG = "configPV/openrouter-models.json";
    private static final String OLLAMA_CONFIG = "configPV/ollama-models.json";
    private static final String CACHE_FILE = "configPV/models-cache.json";
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private ModelConfig openRouterConfig;
    private ModelConfig ollamaConfig;
    private Map<String, Instant> verificationCache = new HashMap<>();
    
    /**
     * Charge la configuration OpenRouter depuis le fichier JSON
     */
    public ModelConfig loadOpenRouterConfig() {
        openRouterConfig = chargeCatalogue(OPENROUTER_CONFIG, "OpenRouter", this::createDefaultOpenRouterConfig);
        return openRouterConfig;
    }

    /**
     * Charge la configuration Ollama depuis le fichier JSON
     */
    public ModelConfig loadOllamaConfig() {
        ollamaConfig = chargeCatalogue(OLLAMA_CONFIG, "Ollama", this::createDefaultOllamaConfig);
        return ollamaConfig;
    }

    /**
     * Charge un catalogue de modèles, en trois temps.
     *
     * <p>D'abord le fichier de {@code configPV/}, que l'utilisateur peut modifier. S'il est
     * absent ou vide, le catalogue <b>embarqué dans le JAR</b> prend le relais, puis est
     * recopié dans {@code configPV/} pour rester modifiable.</p>
     *
     * <p>Ce repli est indispensable : le répertoire {@code configPV} est exclu de
     * l'installeur (il peut contenir des clés) et n'existe donc pas au premier lancement.
     * Sans lui, la liste de modèles était vide chez tout utilisateur installé.</p>
     *
     * @param chemin  Chemin relatif du catalogue, aussi utilisé comme nom de ressource
     * @param libelle Nom du fournisseur, pour les messages
     * @param repli   Configuration minimale si même la ressource embarquée manque
     * @return Une configuration jamais nulle
     */
    private ModelConfig chargeCatalogue(String chemin, String libelle, Supplier<ModelConfig> repli) {
        try {
            String json = Files.readString(Path.of(chemin), StandardCharsets.UTF_8);
            ModelConfig config = gson.fromJson(json, ModelConfig.class);
            if (config != null && config.getModels() != null && !config.getModels().isEmpty()) {
                System.out.println("[Config] ✓ Catalogue " + libelle + " chargé depuis " + chemin
                                 + " : " + config.getModels().size() + " modèles");
                return config;
            }
            System.err.println("[Config] ⚠ Catalogue " + libelle + " présent mais vide : "
                             + "utilisation du catalogue embarqué");
        } catch (IOException absent) {
            System.out.println("[Config] " + chemin + " absent : lecture du catalogue embarqué");
        }

        ModelConfig embarque = chargeDepuisClasspath(chemin, libelle);
        if (embarque != null) {
            amorceCatalogue(chemin, embarque);
            return embarque;
        }
        System.err.println("[Config] ✗ Aucun catalogue " + libelle + " disponible");
        return repli.get();
    }

    /**
     * Lit un catalogue depuis les ressources du JAR.
     *
     * @param chemin  Chemin relatif, transformé en nom de ressource absolu
     * @param libelle Nom du fournisseur, pour les messages
     * @return La configuration lue, ou {@code null} si la ressource est absente ou illisible
     */
    private ModelConfig chargeDepuisClasspath(String chemin, String libelle) {
        try (InputStream is = ModelConfigManager.class.getResourceAsStream("/" + chemin)) {
            if (is == null) {
                return null;
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            ModelConfig config = gson.fromJson(json, ModelConfig.class);
            if (config == null || config.getModels() == null || config.getModels().isEmpty()) {
                return null;
            }
            System.out.println("[Config] ✓ Catalogue " + libelle + " embarqué chargé : "
                             + config.getModels().size() + " modèles");
            return config;
        } catch (IOException e) {
            System.err.println("[Config] ✗ Lecture du catalogue embarqué " + libelle
                             + " impossible : " + e.getMessage());
            return null;
        }
    }

    /**
     * Recopie le catalogue embarqué dans {@code configPV/} pour le rendre modifiable.
     *
     * <p>Sans effet si le fichier existe déjà. Un échec n'est pas bloquant : l'application
     * fonctionne avec le catalogue embarqué, seule la personnalisation est perdue.</p>
     *
     * @param chemin Chemin relatif du fichier à créer
     * @param config Configuration à écrire
     */
    private void amorceCatalogue(String chemin, ModelConfig config) {
        try {
            Path cible = Path.of(chemin);
            if (Files.notExists(cible)) {
                if (cible.getParent() != null) {
                    Files.createDirectories(cible.getParent());
                }
                Files.writeString(cible, gson.toJson(config), StandardCharsets.UTF_8);
                System.out.println("[Config] ✓ " + chemin + " créé depuis le catalogue embarqué");
            }
        } catch (IOException e) {
            System.err.println("[Config] ⚠ Impossible de créer " + chemin + " : " + e.getMessage());
        }
    }
    
    /**
     * Sauvegarde la configuration OpenRouter
     */
    public void saveOpenRouterConfig(ModelConfig config) throws IOException {
        String json = gson.toJson(config);
        Files.writeString(Path.of(OPENROUTER_CONFIG), json, StandardCharsets.UTF_8);
        openRouterConfig = config;
        System.out.println("[Config] ✓ Configuration OpenRouter sauvegardée");
    }
    
    /**
     * Sauvegarde la configuration Ollama
     */
    public void saveOllamaConfig(ModelConfig config) throws IOException {
        String json = gson.toJson(config);
        Files.writeString(Path.of(OLLAMA_CONFIG), json, StandardCharsets.UTF_8);
        ollamaConfig = config;
        System.out.println("[Config] ✓ Configuration Ollama sauvegardée");
    }
    
    /**
     * Vérifie la disponibilité des modèles OpenRouter via l'API
     */
    public void verifyOpenRouterModels(String apiKey) {
        if (openRouterConfig == null || !openRouterConfig.isVerifyAtStartup()) {
            return;
        }
        
        System.out.println("[Config] 🔍 Vérification modèles OpenRouter...");
        
        try {
            // Appeler l'API pour lister les modèles disponibles
            URI uri = URI.create("https://openrouter.ai/api/v1/models");
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                String response = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                JsonArray dataArray = jsonResponse.getAsJsonArray("data");
                
                Set<String> availableIds = new HashSet<>();
                for (int i = 0; i < dataArray.size(); i++) {
                    JsonObject model = dataArray.get(i).getAsJsonObject();
                    availableIds.add(model.get("id").getAsString());
                }
                
                // Marquer les modèles disponibles
                int availableCount = 0;
                for (ModelConfig.ModelEntry entry : openRouterConfig.getModels()) {
                    boolean available = availableIds.contains(entry.getId());
                    entry.setAvailable(available);
                    if (available && entry.isEnabled()) {
                        availableCount++;
                    } else if (!available && entry.isEnabled()) {
                        System.out.println("[Config] ⚠️  Modèle indisponible: " + entry.getId());
                    }
                }
                
                System.out.println("[Config] ✓ OpenRouter: " + availableCount + "/" + 
                                 openRouterConfig.getEnabledModels().size() + " modèles disponibles");
                
                verificationCache.put("openrouter", Instant.now());
                
            } else {
                System.err.println("[Config] ✗ Erreur API OpenRouter: " + responseCode);
            }
            
            conn.disconnect();
            
        } catch (Exception e) {
            System.err.println("[Config] ✗ Erreur vérification OpenRouter: " + e.getMessage());
        }
    }
    
    /**
     * Vérifie la disponibilité des modèles Ollama locaux
     */
    public void verifyOllamaModels() {
        if (ollamaConfig == null || !ollamaConfig.isVerifyAtStartup()) {
            return;
        }
        
        System.out.println("[Config] 🔍 Vérification modèles Ollama...");
        
        try {
            String serverUrl = ollamaConfig.getServerUrl();
            if (serverUrl == null) serverUrl = "http://localhost:11434";
            
            URI uri = URI.create(serverUrl + "/api/tags");
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                String response = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                JsonArray models = jsonResponse.getAsJsonArray("models");
                
                Set<String> availableNames = new HashSet<>();
                for (int i = 0; i < models.size(); i++) {
                    JsonObject model = models.get(i).getAsJsonObject();
                    String name = model.get("name").getAsString();
                    availableNames.add(name);
                    // Ajouter aussi sans :latest pour matching flexible
                    if (name.endsWith(":latest")) {
                        availableNames.add(name.replace(":latest", ""));
                    } else {
                        availableNames.add(name + ":latest");
                    }
                }
                
                // Marquer les modèles disponibles
                int availableCount = 0;
                for (ModelConfig.ModelEntry entry : ollamaConfig.getModels()) {
                    String id = entry.getId();
                    String idWithoutTag = id.replace(":latest", "");
                    boolean available = availableNames.contains(id) || 
                                      availableNames.contains(idWithoutTag);
                    entry.setAvailable(available);
                    if (available && entry.isEnabled()) {
                        availableCount++;
                    } else if (!available && entry.isEnabled()) {
                        System.out.println("[Config] ⚠️  Modèle non installé: " + entry.getId());
                    }
                }
                
                System.out.println("[Config] ✓ Ollama: " + availableCount + "/" + 
                                 ollamaConfig.getEnabledModels().size() + " modèles disponibles");
                
                verificationCache.put("ollama", Instant.now());
                
            } else {
                System.err.println("[Config] ✗ Serveur Ollama inaccessible: " + responseCode);
            }
            
            conn.disconnect();
            
        } catch (Exception e) {
            System.err.println("[Config] ⚠️  Ollama non accessible: " + e.getMessage());
            // Ne pas considérer comme erreur critique
        }
    }
    
    /**
     * Crée une configuration OpenRouter par défaut
     */
    private ModelConfig createDefaultOpenRouterConfig() {
        ModelConfig config = new ModelConfig();
        config.setVerifyAtStartup(true);
        config.setCacheDurationSeconds(3600);
        config.setModels(new ArrayList<>());
        return config;
    }
    
    /**
     * Crée une configuration Ollama par défaut
     */
    private ModelConfig createDefaultOllamaConfig() {
        ModelConfig config = new ModelConfig();
        config.setVerifyAtStartup(true);
        config.setServerUrl("http://localhost:11434");
        config.setTimeoutSeconds(30);
        config.setModels(new ArrayList<>());
        return config;
    }
    
    /**
     * Vérifie si le cache est encore valide
     */
    public boolean isCacheValid(String provider) {
        Instant lastCheck = verificationCache.get(provider);
        if (lastCheck == null) return false;
        
        int cacheDuration = provider.equals("openrouter") && openRouterConfig != null ? 
                           openRouterConfig.getCacheDurationSeconds() : 3600;
        
        return Instant.now().isBefore(lastCheck.plusSeconds(cacheDuration));
    }
    
    public ModelConfig getOpenRouterConfig() {
        return openRouterConfig;
    }
    
    public ModelConfig getOllamaConfig() {
        return ollamaConfig;
    }
}
