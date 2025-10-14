package test;

import editeurpanovisu.ThemeManager;
import editeurpanovisu.util.SvgIconLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.stage.Stage;

/**
 * Test de détection automatique du thème pour les icônes SVG
 * Teste tous les thèmes : AtlantaFX, MaterialFX, FlatLaf et thèmes personnalisés
 */
public class TestThemeDetection extends Application {
    
    private VBox iconContainer;
    private Label themeLabel;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialiser le chemin de base
        SvgIconLoader.setBaseAppPath(System.getProperty("user.dir"));
        
        // Conteneur principal
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        
        // Label du thème actuel
        themeLabel = new Label("Thème actuel : " + ThemeManager.getCurrentTheme().getDisplayName());
        themeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Section thèmes clairs
        Label lblClairs = new Label("🌞 THÈMES CLAIRS");
        lblClairs.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox themesClairs = new VBox(5);
        for (ThemeManager.Theme theme : ThemeManager.getLightThemes()) {
            Button btn = new Button(theme.getDisplayName());
            btn.setOnAction(e -> {
                ThemeManager.applyTheme(scene, theme);
                updateTheme();
            });
            themesClairs.getChildren().add(btn);
        }
        
        // Section thèmes sombres
        Label lblSombres = new Label("🌙 THÈMES SOMBRES");
        lblSombres.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox themesSombres = new VBox(5);
        for (ThemeManager.Theme theme : ThemeManager.getDarkThemes()) {
            Button btn = new Button(theme.getDisplayName());
            btn.setOnAction(e -> {
                ThemeManager.applyTheme(scene, theme);
                updateTheme();
            });
            themesSombres.getChildren().add(btn);
        }
        
        HBox themeButtons = new HBox(30, themesClairs, themesSombres);
        
        // Conteneur des icônes
        iconContainer = new VBox(10);
        loadIcons();
        
        ScrollPane scrollIcons = new ScrollPane(iconContainer);
        scrollIcons.setFitToWidth(true);
        scrollIcons.setPrefHeight(300);
        
        root.getChildren().addAll(
            themeLabel, 
            new Separator(),
            lblClairs,
            themeButtons, 
            new Separator(),
            new Label("Icônes SVG avec détection automatique du thème :"),
            scrollIcons
        );
        
        scene = new Scene(root, 900, 700);
        
        // Appliquer le thème sauvegardé
        ThemeManager.applySavedTheme(scene);
        
        primaryStage.setTitle("Test détection thème - Tous les thèmes AtlantaFX, MaterialFX, FlatLaf");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void loadIcons() {
        iconContainer.getChildren().clear();
        
        String[] iconNames = {
            "nouveau-projet", "ouvrir-projet", "sauve-projet",
            "ajoute-panoramique", "ajoute-plan", "genere-visite",
            "valide", "annule", "vue-sphere", "vue-cube",
            "ajoute-image", "lien", "loupe", "ajoute", "suppr"
        };
        
        for (String iconName : iconNames) {
            HBox iconBox = new HBox(10);
            iconBox.getChildren().add(new Label(String.format("%-25s : ", iconName)));
            iconBox.getChildren().add(SvgIconLoader.loadSvgIcon(iconName, 32));
            iconContainer.getChildren().add(iconBox);
        }
    }
    
    private void updateTheme() {
        themeLabel.setText("Thème actuel : " + ThemeManager.getCurrentTheme().getDisplayName() + 
                          " (" + (ThemeManager.getCurrentTheme().isDark() ? "SOMBRE" : "CLAIR") + ")");
        loadIcons(); // Recharger les icônes (le cache a été vidé automatiquement)
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
