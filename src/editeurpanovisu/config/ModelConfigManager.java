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
        try {
            String json = Files.readString(Path.of(OPENROUTER_CONFIG), StandardCharsets.UTF_8);
            openRouterConfig = gson.fromJson(json, ModelConfig.class);
            System.out.println("[Config] ✓ Configuration OpenRouter chargée: " + 
                             openRouterConfig.getModels().size() + " modèles");
            return openRouterConfig;
        } catch (IOException e) {
            System.err.println("[Config] ✗ Erreur chargement OpenRouter config: " + e.getMessage());
            return createDefaultOpenRouterConfig();
        }
    }
    
    /**
     * Charge la configuration Ollama depuis le fichier JSON
     */
    public ModelConfig loadOllamaConfig() {
        try {
            String json = Files.readString(Path.of(OLLAMA_CONFIG), StandardCharsets.UTF_8);
            ollamaConfig = gson.fromJson(json, ModelConfig.class);
            System.out.println("[Config] ✓ Configuration Ollama chargée: " + 
                             ollamaConfig.getModels().size() + " modèles");
            return ollamaConfig;
        } catch (IOException e) {
            System.err.println("[Config] ✗ Erreur chargement Ollama config: " + e.getMessage());
            return createDefaultOllamaConfig();
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
