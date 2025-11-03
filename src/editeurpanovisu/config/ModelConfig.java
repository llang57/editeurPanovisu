package editeurpanovisu.config;

import java.util.List;

/**
 * Configuration des modèles IA (OpenRouter ou Ollama)
 * Structure correspondant au fichier JSON de configuration
 */
public class ModelConfig {
    
    // Configuration générale
    private boolean verifyAtStartup = true;
    private int cacheDurationSeconds = 3600;
    private String serverUrl; // Pour Ollama uniquement
    private int timeoutSeconds = 30;
    
    // Liste des modèles
    private List<ModelEntry> models;
    
    /**
     * Entrée de modèle IA dans la configuration
     */
    public static class ModelEntry {
        private String id;
        private String displayName;
        private String description;
        private double price; // Prix en $ par 1M tokens (OpenRouter)
        private String size; // Taille en Go (Ollama)
        private int quality; // 1-5 étoiles
        private int priority; // Ordre de priorité (1 = premier essai)
        private boolean enabled = true;
        private transient boolean available = false; // Vérifié au runtime
        
        // Getters
        public String getId() { return id; }
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getSize() { return size; }
        public int getQuality() { return quality; }
        public int getPriority() { return priority; }
        public boolean isEnabled() { return enabled; }
        public boolean isAvailable() { return available; }
        
        // Setters
        public void setId(String id) { this.id = id; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        public void setDescription(String description) { this.description = description; }
        public void setPrice(double price) { this.price = price; }
        public void setSize(String size) { this.size = size; }
        public void setQuality(int quality) { this.quality = quality; }
        public void setPriority(int priority) { this.priority = priority; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public void setAvailable(boolean available) { this.available = available; }
        
        @Override
        public String toString() {
            return displayName + " (" + id + ")" + 
                   (available ? " ✓" : " ✗") + 
                   (enabled ? "" : " [DÉSACTIVÉ]");
        }
    }
    
    // Getters
    public boolean isVerifyAtStartup() { return verifyAtStartup; }
    public int getCacheDurationSeconds() { return cacheDurationSeconds; }
    public String getServerUrl() { return serverUrl; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
    public List<ModelEntry> getModels() { return models; }
    
    // Setters
    public void setVerifyAtStartup(boolean verifyAtStartup) { 
        this.verifyAtStartup = verifyAtStartup; 
    }
    public void setCacheDurationSeconds(int cacheDurationSeconds) { 
        this.cacheDurationSeconds = cacheDurationSeconds; 
    }
    public void setServerUrl(String serverUrl) { 
        this.serverUrl = serverUrl; 
    }
    public void setTimeoutSeconds(int timeoutSeconds) { 
        this.timeoutSeconds = timeoutSeconds; 
    }
    public void setModels(List<ModelEntry> models) { 
        this.models = models; 
    }
    
    /**
     * Retourne la liste des modèles activés et disponibles, triés par priorité
     */
    public List<ModelEntry> getEnabledModels() {
        if (models == null) return List.of();
        return models.stream()
            .filter(ModelEntry::isEnabled)
            .sorted((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .toList();
    }
    
    /**
     * Retourne la liste des modèles activés, disponibles et vérifiés, triés par priorité
     */
    public List<ModelEntry> getAvailableModels() {
        if (models == null) return List.of();
        return models.stream()
            .filter(ModelEntry::isEnabled)
            .filter(ModelEntry::isAvailable)
            .sorted((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
            .toList();
    }
}
