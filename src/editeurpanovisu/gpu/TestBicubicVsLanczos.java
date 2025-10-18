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
import javafx.geometry.Insets;
import java.io.File;

/**
 * Test comparatif Bicubique vs Lanczos3
 * Permet de visualiser la différence de qualité entre les deux méthodes
 * 
 * @author PanoVisu Team
 */
public class TestBicubicVsLanczos extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.out.println("=== Test Comparatif Bicubique vs Lanczos3 ===\n");
        
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
        
        // Dimensions cibles (×2)
        int targetWidth = srcWidth * 2;
        int targetHeight = srcHeight * 2;
        
        try {
            // Test 1: Bicubique
            System.out.println("\n--- Test 1: Bicubique (×2) ---");
            long startBicubic = System.currentTimeMillis();
            
            Image bicubic = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BICUBIC
            );
            
            long durationBicubic = System.currentTimeMillis() - startBicubic;
            System.out.println("✅ Bicubique terminé en " + durationBicubic + " ms");
            
            // Test 2: Lanczos3
            System.out.println("\n--- Test 2: Lanczos3 (×2) ---");
            long startLanczos = System.currentTimeMillis();
            
            Image lanczos = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.LANCZOS3
            );
            
            long durationLanczos = System.currentTimeMillis() - startLanczos;
            System.out.println("✅ Lanczos3 terminé en " + durationLanczos + " ms");
            
            // Comparaison
            System.out.println("\n=== COMPARAISON ===");
            System.out.println("Bicubique : " + durationBicubic + " ms");
            System.out.println("Lanczos3  : " + durationLanczos + " ms");
            System.out.println("Ratio     : " + String.format("%.2f×", 
                (double)durationLanczos / durationBicubic));
            
            if (durationBicubic < durationLanczos) {
                System.out.println("➡️  Bicubique est plus rapide de " + 
                    (durationLanczos - durationBicubic) + " ms");
            } else {
                System.out.println("➡️  Lanczos3 est plus rapide de " + 
                    (durationBicubic - durationLanczos) + " ms");
            }
            
            // Afficher les résultats côte à côte
            showComparison(primaryStage, source, bicubic, lanczos, 
                          durationBicubic, durationLanczos);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            showError(primaryStage, "Erreur: " + e.getMessage());
        }
    }
    
    private void showComparison(Stage stage, Image source, Image bicubic, Image lanczos,
                               long timeBicubic, long timeLanczos) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(10));
        
        // Titre principal
        Label title = new Label("🎨 Comparaison Qualité d'Interpolation");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Infos globales
        String info = String.format(
            "Source: %d×%d → Résultat: %d×%d (×2 agrandissement)",
            (int)source.getWidth(), (int)source.getHeight(),
            (int)bicubic.getWidth(), (int)bicubic.getHeight()
        );
        Label lblInfo = new Label(info);
        lblInfo.setStyle("-fx-font-size: 14px;");
        
        // Performance
        String perf = String.format(
            "⏱️  Bicubique: %d ms  |  Lanczos3: %d ms  (ratio: %.2f×)",
            timeBicubic, timeLanczos, (double)timeLanczos / timeBicubic
        );
        Label lblPerf = new Label(perf);
        lblPerf.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        // Images côte à côte
        HBox imagesBox = new HBox(20);
        
        // Source (miniature)
        VBox sourceBox = new VBox(5);
        Label lblSource = new Label("📷 Source");
        lblSource.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        ImageView srcView = new ImageView(source);
        srcView.setFitWidth(350);
        srcView.setPreserveRatio(true);
        sourceBox.getChildren().addAll(lblSource, srcView);
        
        // Bicubique
        VBox bicubicBox = new VBox(5);
        Label lblBicubic = new Label("⭐ Bicubique (Standard)");
        lblBicubic.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0066cc;");
        ImageView bicubicView = new ImageView(bicubic);
        bicubicView.setFitWidth(350);
        bicubicView.setPreserveRatio(true);
        Label lblBicubicDesc = new Label("Balance qualité/vitesse");
        lblBicubicDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        bicubicBox.getChildren().addAll(lblBicubic, bicubicView, lblBicubicDesc);
        
        // Lanczos3
        VBox lanczosBox = new VBox(5);
        Label lblLanczos = new Label("✨ Lanczos3 (Haute Qualité)");
        lblLanczos.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cc6600;");
        ImageView lanczosView = new ImageView(lanczos);
        lanczosView.setFitWidth(350);
        lanczosView.setPreserveRatio(true);
        Label lblLanczosDesc = new Label("Meilleure netteté");
        lblLanczosDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        lanczosBox.getChildren().addAll(lblLanczos, lanczosView, lblLanczosDesc);
        
        imagesBox.getChildren().addAll(sourceBox, bicubicBox, lanczosBox);
        
        // Note comparative
        Label note = new Label(
            "💡 Conseil: Zoomez sur les détails pour voir la différence.\n" +
            "   Lanczos3 préserve mieux les bords mais est ~50% plus lent."
        );
        note.setStyle("-fx-font-size: 11px; -fx-text-fill: #666; -fx-padding: 10px;");
        
        root.getChildren().addAll(title, lblInfo, lblPerf, imagesBox, note);
        
        Scene scene = new Scene(root, 1150, 650);
        stage.setScene(scene);
        stage.setTitle("Comparaison Bicubique vs Lanczos3");
        stage.show();
    }
    
    private void showError(Stage stage, String message) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
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
