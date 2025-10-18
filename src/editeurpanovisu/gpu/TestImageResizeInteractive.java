package editeurpanovisu.gpu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Application de test interactive pour le redimensionnement GPU
 * Lance une fenêtre permettant de sélectionner une image et de tester
 * les différentes méthodes d'interpolation
 * 
 * @author PanoVisu Team
 */
public class TestImageResizeInteractive extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.out.println("=== Test Redimensionnement GPU Interactif ===\n");
        
        // Vérifier GPU
        GPUManager gpu = GPUManager.getInstance();
        if (!gpu.isGPUAvailable()) {
            System.err.println("❌ GPU non disponible");
            showError(primaryStage, "GPU non disponible");
            return;
        }
        
        System.out.println("✅ GPU détecté: " + gpu.getGPUInfo() + "\n");
        
        // Sélecteur de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image de test");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.bmp")
        );
        
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        
        if (selectedFile == null) {
            System.out.println("⚠️  Aucune image sélectionnée");
            primaryStage.close();
            return;
        }
        
        // Charger l'image
        Image source = new Image("file:" + selectedFile.getAbsolutePath());
        int srcWidth = (int) source.getWidth();
        int srcHeight = (int) source.getHeight();
        
        System.out.println("📐 Image source: " + srcWidth + "×" + srcHeight);
        
        // Test Bicubique (×2)
        int targetWidth = srcWidth * 2;
        int targetHeight = srcHeight * 2;
        
        try {
            System.out.println("\n--- Test Bicubique (×2) ---");
            long startTime = System.currentTimeMillis();
            
            Image bicubic = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BICUBIC
            );
            
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("✅ Terminé en " + duration + " ms");
            
            // Afficher les résultats
            showResults(primaryStage, source, bicubic, "Bicubique GPU (×2)", duration);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            showError(primaryStage, "Erreur: " + e.getMessage());
        }
    }
    
    private void showResults(Stage stage, Image source, Image result, String title, long duration) {
        VBox root = new VBox(10);
        
        // Infos
        Label info = new Label(String.format(
            "%s - Source: %d×%d → Résultat: %d×%d - Durée: %d ms",
            title,
            (int)source.getWidth(), (int)source.getHeight(),
            (int)result.getWidth(), (int)result.getHeight(),
            duration
        ));
        
        // Images côte à côte (miniatures)
        HBox images = new HBox(10);
        
        ImageView srcView = new ImageView(source);
        srcView.setFitWidth(400);
        srcView.setPreserveRatio(true);
        
        ImageView resView = new ImageView(result);
        resView.setFitWidth(400);
        resView.setPreserveRatio(true);
        
        images.getChildren().addAll(
            new VBox(5, new Label("Source"), srcView),
            new VBox(5, new Label("Résultat"), resView)
        );
        
        root.getChildren().addAll(info, images);
        
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.setTitle("Test Redimensionnement GPU");
        stage.show();
    }
    
    private void showError(Stage stage, String message) {
        VBox root = new VBox(10);
        root.getChildren().add(new Label("❌ " + message));
        
        Scene scene = new Scene(root, 400, 100);
        stage.setScene(scene);
        stage.setTitle("Erreur");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
