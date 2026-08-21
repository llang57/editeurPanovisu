package test;

import editeurpanovisu.util.SvgIconLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;

/**
 * Test de la classe SvgIconLoader
 * Affiche toutes les icônes SVG disponibles avec différentes couleurs
 * 
 * @author PanoVisu
 */
public class SvgIconLoaderTest extends Application {
    
    private static final String[] ICON_NAMES = {
        "nouveau-projet",
        "ouvrir-projet",
        "sauve-projet",
        "ajoute-panoramique",
        "ajoute-plan",
        "genere-visite",
        "valide",
        "annule",
        "ajoute-image",
        "lien",
        "loupe",
        "ajoute",
        "suppr",
        "play-auto-tour",
        "pause-auto-tour",
        "maison",
        "modifie",
        "ferme",
        "expand",
        "shrink",
        "vue-sphere",
        "vue-cube"
    };
    
    @Override
    public void start(Stage primaryStage) {
        // Définir le chemin de base de l'application
        String basePath = System.getProperty("user.dir");
        SvgIconLoader.setBaseAppPath(basePath);
        
        // Créer un conteneur pour les icônes
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(20));
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setStyle("-fx-background-color: white;");
        
        // Tester chaque icône avec différentes couleurs
        Color[] colors = {
            Color.web("#333333"),  // Gris foncé (thème clair)
            Color.web("#E0E0E0"),  // Gris clair (thème sombre)
            Color.web("#2196F3"),  // Bleu Material
            Color.web("#4CAF50"),  // Vert Material
            Color.web("#F44336")   // Rouge Material
        };
        
        System.out.println("=== Test du chargement des icônes SVG ===");
        System.out.println("Chemin de base: " + basePath);
        System.out.println();
        
        for (String iconName : ICON_NAMES) {
            System.out.println("Chargement de l'icône: " + iconName);
            
            // Charger l'icône avec la première couleur
            try {
                ImageView iconView = SvgIconLoader.loadSvgIcon(iconName, 32, colors[0]);
                flowPane.getChildren().add(iconView);
                System.out.println("  ✓ " + iconName + " chargée avec succès");
            } catch (Exception e) {
                System.err.println("  ✗ Erreur lors du chargement de " + iconName);
                e.printStackTrace();
            }
        }
        
        System.out.println();
        System.out.println("=== Test de l'adaptation de couleur ===");
        
        // Tester une icône avec toutes les couleurs
        for (Color color : colors) {
            ImageView iconView = SvgIconLoader.loadSvgIcon("nouveau-projet", 48, color);
            flowPane.getChildren().add(iconView);
        }
        
        // Créer la scène
        Scene scene = new Scene(flowPane, 800, 600);
        primaryStage.setTitle("Test des icônes SVG - Apache Batik");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println("Fenêtre de test affichée avec succès !");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
