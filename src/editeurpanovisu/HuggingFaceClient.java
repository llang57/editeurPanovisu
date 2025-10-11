package editeurpanovisu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * Client pour l'API Hugging Face Inference
 * Permet d'interagir avec les modèles de traitement du langage
 * 
 * Documentation : https://huggingface.co/docs/api-inference/
 * 
 * @author PanoVisu
 */
public class HuggingFaceClient {
    
    private static final String API_BASE_URL = "https://api-inference.huggingface.co/models/";
    private final String apiKey;
    private final String modelName;
    
    /**
     * Constructeur avec configuration automatique depuis ApiKeysConfig
     */
    public HuggingFaceClient() {
        this.apiKey = ApiKeysConfig.getHuggingFaceApiKey();
        this.modelName = ApiKeysConfig.getHuggingFaceModel();
        
        if (!ApiKeysConfig.hasHuggingFaceApiKey()) {
            System.err.println("⚠️ Clé API Hugging Face non configurée dans api-keys.properties");
        }
    }
    
    /**
     * Constructeur avec clé API et modèle personnalisés
     * @param apiKey Clé API Hugging Face
     * @param modelName Nom du modèle (ex: "gpt2", "facebook/bart-large-cnn")
     */
    public HuggingFaceClient(String apiKey, String modelName) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }
    
    /**
     * Génère du texte à partir d'un prompt
     * @param prompt Le texte d'entrée
     * @return Le texte généré
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String generateText(String prompt) throws Exception {
        return generateText(prompt, 100, 0.7);
    }
    
    /**
     * Génère du texte avec des paramètres personnalisés
     * @param prompt Le texte d'entrée
     * @param maxLength Longueur maximale du texte généré
     * @param temperature Température pour la génération (0.0-1.0, plus élevé = plus créatif)
     * @return Le texte généré
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String generateText(String prompt, int maxLength, double temperature) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Clé API Hugging Face non configurée");
        }
        
        String endpoint = API_BASE_URL + modelName;
        URI uri = URI.create(endpoint);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        
        try {
            // Configuration de la requête
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Corps de la requête JSON
            String jsonInput = String.format(
                "{\"inputs\": \"%s\", \"parameters\": {\"max_length\": %d, \"temperature\": %.2f}}",
                escapeJson(prompt), maxLength, temperature
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
                    
                    // Parsing simple de la réponse JSON
                    // Format: [{"generated_text": "..."}]
                    String jsonResponse = response.toString();
                    return extractGeneratedText(jsonResponse);
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line);
                    }
                    throw new Exception("Erreur API Hugging Face (" + responseCode + "): " + error);
                }
            }
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Génère du texte de manière asynchrone
     * @param prompt Le texte d'entrée
     * @return CompletableFuture contenant le texte généré
     */
    public CompletableFuture<String> generateTextAsync(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return generateText(prompt);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la génération de texte", e);
            }
        });
    }
    
    /**
     * Résume un texte
     * @param text Le texte à résumer
     * @param maxLength Longueur maximale du résumé
     * @return Le résumé
     * @throws Exception Si une erreur survient lors de l'appel API
     */
    public String summarize(String text, int maxLength) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Clé API Hugging Face non configurée");
        }
        
        // Pour le résumé, utiliser un modèle spécialisé comme facebook/bart-large-cnn
        String endpoint = API_BASE_URL + "facebook/bart-large-cnn";
        URI uri = URI.create(endpoint);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        
        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            String jsonInput = String.format(
                "{\"inputs\": \"%s\", \"parameters\": {\"max_length\": %d}}",
                escapeJson(text), maxLength
            );
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return extractSummaryText(response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line);
                    }
                    throw new Exception("Erreur API Hugging Face (" + responseCode + "): " + error);
                }
            }
        } finally {
            conn.disconnect();
        }
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
     * Extrait le texte généré de la réponse JSON
     * Format: [{"generated_text": "..."}]
     */
    private String extractGeneratedText(String jsonResponse) {
        // Parsing simple - pour un parsing robuste, utiliser une lib JSON
        String marker = "\"generated_text\":";
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
        
        // Trouver la fin de la chaîne
        int endIdx = startIdx;
        while (endIdx < jsonResponse.length()) {
            if (jsonResponse.charAt(endIdx) == '"' && jsonResponse.charAt(endIdx - 1) != '\\') {
                break;
            }
            endIdx++;
        }
        
        return jsonResponse.substring(startIdx, endIdx);
    }
    
    /**
     * Extrait le texte résumé de la réponse JSON
     * Format: [{"summary_text": "..."}]
     */
    private String extractSummaryText(String jsonResponse) {
        String marker = "\"summary_text\":";
        int startIdx = jsonResponse.indexOf(marker);
        if (startIdx == -1) {
            return jsonResponse;
        }
        
        startIdx += marker.length();
        while (startIdx < jsonResponse.length() && jsonResponse.charAt(startIdx) != '"') {
            startIdx++;
        }
        startIdx++;
        
        int endIdx = startIdx;
        while (endIdx < jsonResponse.length()) {
            if (jsonResponse.charAt(endIdx) == '"' && jsonResponse.charAt(endIdx - 1) != '\\') {
                break;
            }
            endIdx++;
        }
        
        return jsonResponse.substring(startIdx, endIdx);
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
}
