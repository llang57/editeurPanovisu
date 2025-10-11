package editeurpanovisu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestionnaire de configuration pour les clés API
 * Charge les clés depuis le fichier api-keys.properties
 * 
 * @author PanoVisu
 */
public class ApiKeysConfig {
    
    private static final String CONFIG_FILE = "api-keys.properties";
    private static Properties properties = null;
    
    /**
     * Charge le fichier de configuration des clés API
     */
    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            
            // Essayer de charger depuis le fichier système d'abord
            try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
                properties.load(fis);
                System.out.println("✅ Configuration API chargée depuis " + CONFIG_FILE);
            } catch (IOException e) {
                // Fichier système non trouvé, essayer dans les ressources
                try (InputStream is = ApiKeysConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                    if (is != null) {
                        properties.load(is);
                        System.out.println("✅ Configuration API chargée depuis les ressources");
                    } else {
                        System.err.println("⚠️ Fichier " + CONFIG_FILE + " non trouvé.");
                        System.err.println("   Veuillez créer ce fichier à partir de " + CONFIG_FILE + ".template");
                    }
                } catch (IOException ex) {
                    System.err.println("❌ Erreur lors du chargement de la configuration API: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Récupère la clé API LocationIQ
     * @return La clé API ou une chaîne vide si non configurée
     */
    public static String getLocationIQApiKey() {
        loadProperties();
        return properties.getProperty("locationiq.api.key", "");
    }
    
    /**
     * Vérifie si une clé API LocationIQ est configurée
     * @return true si une clé est présente
     */
    public static boolean hasLocationIQApiKey() {
        String key = getLocationIQApiKey();
        return key != null && !key.isEmpty() && !key.equals("YOUR_API_KEY_HERE");
    }
    
    /**
     * Récupère la clé API Hugging Face
     * @return La clé API ou une chaîne vide si non configurée
     */
    public static String getHuggingFaceApiKey() {
        loadProperties();
        return properties.getProperty("huggingface.api.key", "");
    }
    
    /**
     * Récupère le modèle Hugging Face configuré
     * @return Le nom du modèle ou "gpt2" par défaut
     */
    public static String getHuggingFaceModel() {
        loadProperties();
        return properties.getProperty("huggingface.model", "gpt2");
    }
    
    /**
     * Vérifie si une clé API Hugging Face est configurée
     * @return true si une clé est présente
     */
    public static boolean hasHuggingFaceApiKey() {
        String key = getHuggingFaceApiKey();
        return key != null && !key.isEmpty() && !key.startsWith("YOUR_");
    }
    
    /**
     * Récupère la clé API OpenRouter
     * @return La clé API ou une chaîne vide si non configurée
     */
    public static String getOpenRouterApiKey() {
        loadProperties();
        return properties.getProperty("openrouter.api.key", "");
    }
    
    /**
     * Récupère le modèle OpenRouter configuré
     * @return Le nom du modèle ou "meta-llama/llama-3.2-3b-instruct:free" par défaut
     */
    public static String getOpenRouterModel() {
        loadProperties();
        return properties.getProperty("openrouter.model", "meta-llama/llama-3.2-3b-instruct:free");
    }
    
    /**
     * Vérifie si une clé API OpenRouter est configurée
     * @return true si une clé est présente
     */
    public static boolean hasOpenRouterApiKey() {
        String key = getOpenRouterApiKey();
        return key != null && !key.isEmpty() && !key.startsWith("YOUR_");
    }
    
    /**
     * Récupère une propriété personnalisée
     * @param key Nom de la propriété
     * @param defaultValue Valeur par défaut si la propriété n'existe pas
     * @return La valeur de la propriété
     */
    public static String getProperty(String key, String defaultValue) {
        loadProperties();
        return properties.getProperty(key, defaultValue);
    }
}
