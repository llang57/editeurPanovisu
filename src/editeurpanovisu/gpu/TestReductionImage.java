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
 * Test comparatif des méthodes d'interpolation pour RÉDUCTION d'images
 * Compare Bilinéaire, Bicubique et Lanczos3 sur une réduction ÷2
 * 
 * @author PanoVisu Team
 */
public class TestReductionImage extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.out.println("=== Test Comparatif Réduction d'Images ===\n");
        
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
        fileChooser.setTitle("Sélectionner une GRANDE image à réduire");
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
        
        // Dimensions cibles (÷2)
        int targetWidth = srcWidth / 2;
        int targetHeight = srcHeight / 2;
        
        System.out.println("📐 Cible: " + targetWidth + "×" + targetHeight + " (÷2 réduction)\n");
        
        try {
            // Test 1: Bilinéaire (rapide)
            System.out.println("--- Test 1: Bilinéaire (Rapide) ---");
            long startBilinear = System.currentTimeMillis();
            
            Image bilinear = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BILINEAR
            );
            
            long durationBilinear = System.currentTimeMillis() - startBilinear;
            System.out.println("✅ Bilinéaire terminé en " + durationBilinear + " ms");
            
            // Test 2: Bicubique (recommandé)
            System.out.println("\n--- Test 2: Bicubique (Recommandé) ---");
            long startBicubic = System.currentTimeMillis();
            
            Image bicubic = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BICUBIC
            );
            
            long durationBicubic = System.currentTimeMillis() - startBicubic;
            System.out.println("✅ Bicubique terminé en " + durationBicubic + " ms");
            
            // Test 3: Lanczos3 (qualité max)
            System.out.println("\n--- Test 3: Lanczos3 (Qualité Max) ---");
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
            System.out.println("\n=== COMPARAISON RÉDUCTION ===");
            System.out.println("Bilinéaire : " + durationBilinear + " ms (baseline)");
            System.out.println("Bicubique  : " + durationBicubic + " ms (+" + 
                String.format("%.0f%%", ((double)(durationBicubic - durationBilinear) / durationBilinear * 100)) + ")");
            System.out.println("Lanczos3   : " + durationLanczos + " ms (+" + 
                String.format("%.0f%%", ((double)(durationLanczos - durationBilinear) / durationBilinear * 100)) + ")");
            
            System.out.println("\n💡 RECOMMANDATION:");
            System.out.println("   ⭐ Bicubique = Meilleur compromis qualité/vitesse");
            System.out.println("   ✨ Lanczos3  = Réductions drastiques (÷4+)");
            System.out.println("   ⚡ Bilinéaire = Prévisualisations rapides");
            
            // Afficher les résultats côte à côte
            showComparison(primaryStage, source, bilinear, bicubic, lanczos,
                          durationBilinear, durationBicubic, durationLanczos);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            showError(primaryStage, "Erreur: " + e.getMessage());
        }
    }
    
    private void showComparison(Stage stage, Image source, 
                               Image bilinear, Image bicubic, Image lanczos,
                               long timeBilinear, long timeBicubic, long timeLanczos) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(10));
        
        // Titre principal
        Label title = new Label("📉 Comparaison Méthodes de Réduction");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Infos globales
        String info = String.format(
            "Source: %d×%d → Résultat: %d×%d (÷2 réduction)",
            (int)source.getWidth(), (int)source.getHeight(),
            (int)bicubic.getWidth(), (int)bicubic.getHeight()
        );
        Label lblInfo = new Label(info);
        lblInfo.setStyle("-fx-font-size: 14px;");
        
        // Performance
        String perf = String.format(
            "⏱️  Bilinéaire: %d ms  |  Bicubique: %d ms (+%.0f%%)  |  Lanczos3: %d ms (+%.0f%%)",
            timeBilinear, timeBicubic, 
            ((double)(timeBicubic - timeBilinear) / timeBilinear * 100),
            timeLanczos,
            ((double)(timeLanczos - timeBilinear) / timeBilinear * 100)
        );
        Label lblPerf = new Label(perf);
        lblPerf.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        // Images côte à côte
        HBox imagesBox = new HBox(15);
        
        // Source (miniature)
        VBox sourceBox = new VBox(5);
        Label lblSource = new Label("📷 Source");
        lblSource.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        ImageView srcView = new ImageView(source);
        srcView.setFitWidth(280);
        srcView.setPreserveRatio(true);
        sourceBox.getChildren().addAll(lblSource, srcView);
        
        // Bilinéaire
        VBox bilinearBox = new VBox(5);
        Label lblBilinear = new Label("⚡ Bilinéaire");
        lblBilinear.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #999;");
        ImageView bilinearView = new ImageView(bilinear);
        bilinearView.setFitWidth(280);
        bilinearView.setPreserveRatio(true);
        Label lblBilinearDesc = new Label("Rapide mais flou");
        lblBilinearDesc.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        bilinearBox.getChildren().addAll(lblBilinear, bilinearView, lblBilinearDesc);
        
        // Bicubique
        VBox bicubicBox = new VBox(5);
        Label lblBicubic = new Label("⭐ Bicubique (RECOMMANDÉ)");
        lblBicubic.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #0066cc;");
        ImageView bicubicView = new ImageView(bicubic);
        bicubicView.setFitWidth(280);
        bicubicView.setPreserveRatio(true);
        Label lblBicubicDesc = new Label("Meilleur anti-aliasing");
        lblBicubicDesc.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        bicubicBox.getChildren().addAll(lblBicubic, bicubicView, lblBicubicDesc);
        
        // Lanczos3
        VBox lanczosBox = new VBox(5);
        Label lblLanczos = new Label("✨ Lanczos3");
        lblLanczos.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #cc6600;");
        ImageView lanczosView = new ImageView(lanczos);
        lanczosView.setFitWidth(280);
        lanczosView.setPreserveRatio(true);
        Label lblLanczosDesc = new Label("Préserve détails fins");
        lblLanczosDesc.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        lanczosBox.getChildren().addAll(lblLanczos, lanczosView, lblLanczosDesc);
        
        imagesBox.getChildren().addAll(sourceBox, bilinearBox, bicubicBox, lanczosBox);
        
        // Note comparative
        Label note = new Label(
            "💡 Réduction: Bicubique offre le meilleur compromis (anti-aliasing sans flou).\n" +
            "   Lanczos3 utile pour réductions drastiques (÷4+) avec détails complexes."
        );
        note.setStyle("-fx-font-size: 11px; -fx-text-fill: #666; -fx-padding: 10px;");
        
        // Recommandation
        Label reco = new Label("⭐ RECOMMANDATION : Utilisez Bicubique pour vos réductions");
        reco.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #0066cc; -fx-padding: 5px;");
        
        root.getChildren().addAll(title, lblInfo, lblPerf, imagesBox, note, reco);
        
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Comparaison Réduction d'Images");
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
