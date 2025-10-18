package editeurpanovisu.gpu;

import javafx.scene.image.Image;
import java.io.File;

/**
 * Classe de test pour le redimensionnement GPU
 * Permet de tester les différentes méthodes d'interpolation
 * 
 * @author PanoVisu Team
 */
public class TestImageResize {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Test Redimensionnement GPU ===\n");
            
            // Initialisation du GPU
            GPUManager gpu = GPUManager.getInstance();
            if (!gpu.isGPUAvailable()) {
                System.err.println("❌ GPU non disponible");
                return;
            }
            
            System.out.println("✅ GPU détecté: " + gpu.getGPUInfo() + "\n");
            
            // Charger une image de test (vous devrez fournir un chemin valide)
            String testImagePath = "chemin/vers/votre/image.jpg";
            
            if (!new File(testImagePath).exists()) {
                System.out.println("⚠️  Image de test non trouvée: " + testImagePath);
                System.out.println("Pour tester, modifiez la variable testImagePath dans TestImageResize.java");
                return;
            }
            
            Image source = new Image("file:" + testImagePath);
            int srcWidth = (int) source.getWidth();
            int srcHeight = (int) source.getHeight();
            
            System.out.println("📐 Image source: " + srcWidth + "×" + srcHeight);
            
            // Test 1: Bicubique (agrandissement ×2)
            int targetWidth = srcWidth * 2;
            int targetHeight = srcHeight * 2;
            
            System.out.println("\n--- Test 1: Bicubique (×2) ---");
            Image bicubic = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BICUBIC
            );
            System.out.println("✅ Bicubique réussi: " + 
                (int)bicubic.getWidth() + "×" + (int)bicubic.getHeight());
            
            // Test 2: Lanczos3 (agrandissement ×2)
            System.out.println("\n--- Test 2: Lanczos3 (×2) ---");
            Image lanczos = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.LANCZOS3
            );
            System.out.println("✅ Lanczos3 réussi: " + 
                (int)lanczos.getWidth() + "×" + (int)lanczos.getHeight());
            
            // Test 3: Réduction (×0.5)
            int smallWidth = srcWidth / 2;
            int smallHeight = srcHeight / 2;
            
            System.out.println("\n--- Test 3: Réduction Bicubique (×0.5) ---");
            Image reduced = ImageResizeGPU.resizeAuto(
                source, 
                smallWidth, 
                smallHeight, 
                InterpolationMethod.BICUBIC
            );
            System.out.println("✅ Réduction réussie: " + 
                (int)reduced.getWidth() + "×" + (int)reduced.getHeight());
            
            // Test 4: Petite image (devrait utiliser CPU)
            System.out.println("\n--- Test 4: Petite image (CPU fallback) ---");
            Image small = new Image("file:" + testImagePath, 800, 600, true, false);
            Image smallResized = ImageResizeGPU.resizeAuto(
                small, 
                1600, 
                1200, 
                InterpolationMethod.BICUBIC
            );
            System.out.println("✅ Petite image traitée: " + 
                (int)smallResized.getWidth() + "×" + (int)smallResized.getHeight());
            
            // Nettoyage
            ImageResizeGPU.cleanup();
            System.out.println("\n🎉 Tous les tests réussis !");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
