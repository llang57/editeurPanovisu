package editeurpanovisu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Programme de test simple pour Gluon Maps
 * Lance une fenêtre avec une carte interactive
 */
public class TestGluonMaps extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("🚀 Lancement du test Gluon Maps...");
        
        // Créer le navigateur avec Gluon Maps
        NavigateurOpenLayersGluon navigateur = new NavigateurOpenLayersGluon();
        StackPane root = new StackPane(navigateur.afficheNavigateurOpenLayer());
        
        // Créer la scène
        Scene scene = new Scene(root, 800, 600);
        
        // Configurer la fenêtre
        primaryStage.setTitle("Test Gluon Maps - PanoVisu");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Test: ajouter un marqueur après 2 secondes
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("➕ Ajout d'un marqueur de test...");
                
                javafx.application.Platform.runLater(() -> {
                    // Paris
                    CoordonneesGeographiques paris = new CoordonneesGeographiques(48.8566, 2.3522);
                    navigateur.ajouteMarqueur(0, paris, "Paris", "marker.png", 5);
                    
                    // Londres
                    CoordonneesGeographiques londres = new CoordonneesGeographiques(51.5074, -0.1278);
                    navigateur.ajouteMarqueur(1, londres, "Londres", "marker.png", 5);
                    
                    System.out.println("✅ Marqueurs ajoutés");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
        System.out.println("✅ Fenêtre de test affichée");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
