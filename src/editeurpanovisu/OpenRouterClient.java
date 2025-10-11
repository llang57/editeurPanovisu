package editeurpanovisu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * Client pour l'API OpenRouter
 * Permet d'accéder à différents modèles LLM via une interface unifiée
 * 
 * Documentation : https://openrouter.ai/docs
 * 
 * @author PanoVisu
 */
public class OpenRouterClient {
    
    private static final String API_BASE_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final String apiKey;
    private final String modelName;
    
    /**
     * Constructeur avec configuration automatique depuis ApiKeysConfig
     */
    public OpenRouterClient() {
        this.apiKey = ApiKeysConfig.getOpenRouterApiKey();
        this.modelName = ApiKeysConfig.getOpenRouterModel();
        
        if (!ApiKeysConfig.hasOpenRouterApiKey()) {
            System.err.println("⚠️ Clé API OpenRouter non configurée dans api-keys.properties");
        }
    }
    
    /**
     * Constructeur avec clé API et modèle personnalisés
     * @param apiKey Clé API OpenRouter
     * @param modelName Nom du modèle (ex: "meta-llama/llama-3.2-3b-instruct:free")
     */
    public OpenRouterClient(String apiKey, String modelName) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }
    
    /**
     * Envoie un message au modèle et obtient une réponse
     * @param userMessage Le message de l'utilisateur
     * @return La réponse du modèle
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String chat(String userMessage) throws Exception {
        return chat(userMessage, null, 0.7, 1000);
    }
    
    /**
     * Envoie un message avec un contexte système
     * @param userMessage Le message de l'utilisateur
     * @param systemMessage Message système pour définir le comportement du modèle
     * @return La réponse du modèle
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String chat(String userMessage, String systemMessage) throws Exception {
        return chat(userMessage, systemMessage, 0.7, 1000);
    }
    
    /**
     * Envoie un message avec tous les paramètres personnalisables
     * @param userMessage Le message de l'utilisateur
     * @param systemMessage Message système (peut être null)
     * @param temperature Température pour la génération (0.0-2.0)
     * @param maxTokens Nombre maximum de tokens à générer
     * @return La réponse du modèle
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String chat(String userMessage, String systemMessage, double temperature, int maxTokens) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Clé API OpenRouter non configurée");
        }
        
        URI uri = URI.create(API_BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        
        try {
            // Configuration de la requête
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("HTTP-Referer", "https://github.com/panovisu/editeur");
            conn.setRequestProperty("X-Title", "EditeurPanovisu");
            conn.setDoOutput(true);
            
            // Construction du corps de la requête
            StringBuilder messagesJson = new StringBuilder();
            messagesJson.append("[");
            
            // Message système si présent
            if (systemMessage != null && !systemMessage.isEmpty()) {
                messagesJson.append("{\"role\": \"system\", \"content\": \"")
                           .append(escapeJson(systemMessage))
                           .append("\"},");
            }
            
            // Message utilisateur
            messagesJson.append("{\"role\": \"user\", \"content\": \"")
                       .append(escapeJson(userMessage))
                       .append("\"}]");
            
            String jsonInput = String.format(
                "{\"model\": \"%s\", \"messages\": %s, \"temperature\": %.2f, \"max_tokens\": %d}",
                modelName, messagesJson, temperature, maxTokens
            );
            
            // Envoi de la requête
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Lecture de la réponse
            int responseCode = conn.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Extraction du message de la réponse
                    return extractMessageContent(response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line);
                    }
                    throw new Exception("Erreur API OpenRouter (" + responseCode + "): " + error);
                }
            }
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Envoie un message de manière asynchrone
     * @param userMessage Le message de l'utilisateur
     * @return CompletableFuture contenant la réponse
     */
    public CompletableFuture<String> chatAsync(String userMessage) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return chat(userMessage);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de l'appel à OpenRouter", e);
            }
        });
    }
    
    /**
     * Génère des suggestions de description pour un panorama
     * @param titre Titre du panorama
     * @param tags Tags associés
     * @return Une description générée
     * @throws Exception Si une erreur survient
     */
    public String generatePanoramaDescription(String titre, String tags) throws Exception {
        String systemMessage = "Tu es un assistant qui aide à créer des descriptions pour des panoramas 360°. " +
                              "Génère une description courte et attractive en français.";
        
        String userMessage = String.format(
            "Génère une description pour un panorama 360° intitulé '%s' avec les tags: %s",
            titre, tags
        );
        
        return chat(userMessage, systemMessage, 0.8, 200);
    }
    
    /**
     * Suggère des hotspots pertinents pour un panorama
     * @param titre Titre du panorama
     * @param description Description du lieu
     * @return Suggestions de hotspots
     * @throws Exception Si une erreur survient
     */
    public String suggestHotspots(String titre, String description) throws Exception {
        String systemMessage = "Tu es un assistant qui aide à identifier des points d'intérêt " +
                              "dans des panoramas 360°. Suggère 3-5 hotspots pertinents avec leurs descriptions.";
        
        String userMessage = String.format(
            "Pour un panorama '%s' décrit comme '%s', suggère des hotspots intéressants à ajouter.",
            titre, description
        );
        
        return chat(userMessage, systemMessage, 0.7, 500);
    }
    
    /**
     * Échappe les caractères spéciaux pour JSON
     */
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    /**
     * Extrait le contenu du message de la réponse JSON
     * Format OpenRouter: {"choices": [{"message": {"content": "..."}}]}
     */
    private String extractMessageContent(String jsonResponse) {
        // Parsing simple - pour un parsing robuste, utiliser une lib JSON
        String marker = "\"content\":";
        int startIdx = jsonResponse.indexOf(marker);
        if (startIdx == -1) {
            return jsonResponse; // Retourner la réponse brute si format non reconnu
        }
        
        startIdx += marker.length();
        // Trouver le début de la chaîne
        while (startIdx < jsonResponse.length() && jsonResponse.charAt(startIdx) != '"') {
            startIdx++;
        }
        startIdx++; // Passer le guillemet
        
        // Trouver la fin de la chaîne (en gérant les échappements)
        int endIdx = startIdx;
        while (endIdx < jsonResponse.length()) {
            if (jsonResponse.charAt(endIdx) == '"' && jsonResponse.charAt(endIdx - 1) != '\\') {
                break;
            }
            endIdx++;
        }
        
        String content = jsonResponse.substring(startIdx, endIdx);
        
        // Déséchapper les caractères
        return content.replace("\\n", "\n")
                     .replace("\\r", "\r")
                     .replace("\\t", "\t")
                     .replace("\\\"", "\"")
                     .replace("\\\\", "\\");
    }
    
    /**
     * Vérifie si le client est configuré correctement
     * @return true si la clé API est présente
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isEmpty();
    }
    
    /**
     * Récupère le nom du modèle utilisé
     * @return Le nom du modèle
     */
    public String getModelName() {
        return modelName;
    }
    
    /**
     * Liste des modèles gratuits populaires sur OpenRouter
     * @return Tableau de noms de modèles gratuits
     */
    public static String[] getFreeModels() {
        return new String[] {
            "meta-llama/llama-3.2-3b-instruct:free",
            "meta-llama/llama-3.2-1b-instruct:free",
            "mistralai/mistral-7b-instruct:free",
            "google/gemma-2-9b-it:free",
            "microsoft/phi-3-mini-128k-instruct:free"
        };
    }
}
