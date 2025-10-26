package editeurpanovisu;

/**
 * Classe de test pour les clients API IA
 * Démontre l'utilisation de HuggingFaceClient et OpenRouterClient
 * 
 * @author PanoVisu
 */
public class TestAIClients {
    
    /**
     * Point d'entrée pour tester les clients IA (Ollama, HuggingFace, OpenRouter)
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  TEST DES CLIENTS API IA - EditeurPanovisu");
        System.out.println("═══════════════════════════════════════════════\n");
        
        testHuggingFace();
        System.out.println("\n" + "─".repeat(50) + "\n");
        testOpenRouter();
        
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  FIN DES TESTS");
        System.out.println("═══════════════════════════════════════════════");
    }
    
    /**
     * Test du client Hugging Face
     */
    private static void testHuggingFace() {
        System.out.println("🤗 TEST HUGGING FACE CLIENT");
        System.out.println("─".repeat(50));
        
        HuggingFaceClient hfClient = new HuggingFaceClient();
        
        // Vérification de la configuration
        if (!hfClient.isConfigured()) {
            System.err.println("❌ Hugging Face non configuré");
            System.err.println("   Veuillez ajouter la clé dans api-keys.properties");
            return;
        }
        
        System.out.println("✅ Client configuré");
        System.out.println("📦 Modèle: " + hfClient.getModelName());
        
        // Test de génération de texte
        try {
            System.out.println("\n📝 Test de génération de texte...");
            String prompt = "Panorama 360 degree virtual tour of";
            System.out.println("   Prompt: \"" + prompt + "\"");
            
            String generated = hfClient.generateText(prompt, 50, 0.7);
            System.out.println("   Réponse: " + generated);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
        
        // Test asynchrone
        try {
            System.out.println("\n⚡ Test de génération asynchrone...");
            String prompt = "A beautiful landscape with";
            System.out.println("   Prompt: \"" + prompt + "\"");
            
            hfClient.generateTextAsync(prompt)
                   .thenAccept(result -> {
                       System.out.println("   ✅ Réponse asynchrone: " + result);
                   })
                   .exceptionally(error -> {
                       System.err.println("   ❌ Erreur asynchrone: " + error.getMessage());
                       return null;
                   });
            
            // Attendre un peu pour l'exécution asynchrone
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
    }
    
    /**
     * Test du client OpenRouter
     */
    private static void testOpenRouter() {
        System.out.println("🚀 TEST OPENROUTER CLIENT");
        System.out.println("─".repeat(50));
        
        OpenRouterClient orClient = new OpenRouterClient();
        
        // Vérification de la configuration
        if (!orClient.isConfigured()) {
            System.err.println("❌ OpenRouter non configuré");
            System.err.println("   Veuillez ajouter la clé dans api-keys.properties");
            return;
        }
        
        System.out.println("✅ Client configuré");
        System.out.println("📦 Modèle: " + orClient.getModelName());
        
        // Afficher les modèles gratuits disponibles
        System.out.println("\n💰 Modèles gratuits disponibles:");
        String[] freeModels = OpenRouterClient.getFreeModels();
        for (int i = 0; i < freeModels.length; i++) {
            System.out.println("   " + (i + 1) + ". " + freeModels[i]);
        }
        
        // Test de chat simple
        try {
            System.out.println("\n💬 Test de chat simple...");
            String message = "In one sentence, what is a 360 degree panorama?";
            System.out.println("   Question: \"" + message + "\"");
            
            String response = orClient.chat(message);
            System.out.println("   Réponse: " + response);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
        
        // Test avec message système
        try {
            System.out.println("\n🎯 Test avec contexte système...");
            String systemMsg = "Tu es un expert en photographie panoramique et visites virtuelles.";
            String userMsg = "Donne 3 conseils pour créer un bon panorama 360°";
            System.out.println("   Système: \"" + systemMsg + "\"");
            System.out.println("   Question: \"" + userMsg + "\"");
            
            String response = orClient.chat(userMsg, systemMsg);
            System.out.println("   Réponse:\n" + response);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
        
        // Test de génération de description
        try {
            System.out.println("\n📸 Test de génération de description...");
            String titre = "Château de Brézé";
            String tags = "château, architecture, visite virtuelle";
            System.out.println("   Titre: \"" + titre + "\"");
            System.out.println("   Tags: \"" + tags + "\"");
            
            String description = orClient.generatePanoramaDescription(titre, tags);
            System.out.println("   Description générée:\n   " + description);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
        
        // Test de suggestion de hotspots
        try {
            System.out.println("\n📍 Test de suggestion de hotspots...");
            String titre = "Cathédrale Notre-Dame";
            String description = "Vue intérieure de la cathédrale gothique avec ses vitraux colorés";
            System.out.println("   Titre: \"" + titre + "\"");
            System.out.println("   Description: \"" + description + "\"");
            
            String hotspots = orClient.suggestHotspots(titre, description);
            System.out.println("   Hotspots suggérés:\n" + hotspots);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
        
        // Test asynchrone
        try {
            System.out.println("\n⚡ Test de chat asynchrone...");
            String message = "What is the best time to take 360 photos?";
            System.out.println("   Question: \"" + message + "\"");
            
            orClient.chatAsync(message)
                   .thenAccept(result -> {
                       System.out.println("   ✅ Réponse asynchrone: " + result);
                   })
                   .exceptionally(error -> {
                       System.err.println("   ❌ Erreur asynchrone: " + error.getMessage());
                       return null;
                   });
            
            // Attendre un peu pour l'exécution asynchrone
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
        }
    }
}
