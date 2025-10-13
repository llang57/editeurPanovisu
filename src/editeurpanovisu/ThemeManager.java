package editeurpanovisu;

import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.scene.Scene;

import java.util.prefs.Preferences;

/**
 * Gestionnaire de thèmes pour l'application EditeurPanovisu.
 * Permet de basculer entre différents thèmes modernes AtlantaFX.
 */
public class ThemeManager {
    
    /**
     * Liste des thèmes disponibles
     */
    public enum Theme {
        // Thèmes clairs
        PRIMER_LIGHT("Primer Light", new PrimerLight(), false),
        NORD_LIGHT("Nord Light", new NordLight(), false),
        CUPERTINO_LIGHT("Cupertino Light", new CupertinoLight(), false),
        
        // Thèmes sombres
        PRIMER_DARK("Primer Dark", new PrimerDark(), true),
        NORD_DARK("Nord Dark", new NordDark(), true),
        CUPERTINO_DARK("Cupertino Dark", new CupertinoDark(), true),
        DRACULA("Dracula", new Dracula(), true),
        
        // Thèmes personnalisés legacy (vos CSS actuels)
        CUSTOM_LIGHT("Thème Clair Personnalisé", null, false),
        CUSTOM_DARK("Thème Foncé Personnalisé", null, true);
        
        private final String displayName;
        private final atlantafx.base.theme.Theme atlantaTheme;
        private final boolean isDark;
        
        Theme(String displayName, atlantafx.base.theme.Theme atlantaTheme, boolean isDark) {
            this.displayName = displayName;
            this.atlantaTheme = atlantaTheme;
            this.isDark = isDark;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public atlantafx.base.theme.Theme getAtlantaTheme() {
            return atlantaTheme;
        }
        
        public boolean isDark() {
            return isDark;
        }
        
        public boolean isCustom() {
            return atlantaTheme == null;
        }
    }
    
    private static final String PREF_THEME_KEY = "selected_theme";
    private static final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
    private static Theme currentTheme = Theme.PRIMER_LIGHT;
    
    /**
     * Applique un thème à une scène JavaFX
     * 
     * @param scene La scène à thématiser
     * @param theme Le thème à appliquer
     */
    public static void applyTheme(Scene scene, Theme theme) {
        if (scene == null) {
            return;
        }
        
        // Nettoyer les anciens stylesheets
        scene.getStylesheets().clear();
        
        // Nettoyer les classes CSS du root
        scene.getRoot().getStyleClass().removeAll(
            "primer-light", "primer-dark",
            "nord-light", "nord-dark",
            "cupertino-light", "cupertino-dark",
            "dracula"
        );
        
        if (theme.isCustom()) {
            // Utiliser les CSS personnalisés existants
            String cssPath = theme.isDark() ? 
                "/css/fonce.css" : 
                "/css/clair.css";
            
            String cssUrl = ThemeManager.class.getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(cssUrl);
            
            System.out.println("✅ Thème personnalisé appliqué : " + theme.getDisplayName());
        } else {
            // Utiliser AtlantaFX
            Application.setUserAgentStylesheet(theme.getAtlantaTheme().getUserAgentStylesheet());
            
            // Ajouter une classe CSS au root pour les sélecteurs spécifiques
            String themeClass = theme.name().toLowerCase().replace('_', '-');
            scene.getRoot().getStyleClass().add(themeClass);
            
            // Ajouter les CSS personnalisés en complément
            addCustomStyles(scene, theme);
            
            System.out.println("✅ Thème AtlantaFX appliqué : " + theme.getDisplayName());
            System.out.println("  ↳ Classe CSS ajoutée : " + themeClass);
        }
        
        currentTheme = theme;
        saveThemePreference(theme);
    }
    
    /**
     * Ajoute des styles personnalisés complémentaires
     * pour les composants spécifiques de l'application
     */
    private static void addCustomStyles(Scene scene, Theme theme) {
        try {
            // Charger le CSS personnalisé de l'éditeur (titres panels, etc.)
            String customCss = ThemeManager.class.getResource("/css/editeur-custom.css").toExternalForm();
            if (customCss != null) {
                scene.getStylesheets().add(customCss);
                System.out.println("  ↳ Styles personnalisés chargés : editeur-custom.css");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Impossible de charger editeur-custom.css : " + e.getMessage());
        }
    }
    
    /**
     * Charge le thème sauvegardé dans les préférences
     * 
     * @return Le thème sauvegardé ou PRIMER_LIGHT par défaut
     */
    public static Theme loadSavedTheme() {
        String themeName = prefs.get(PREF_THEME_KEY, Theme.PRIMER_LIGHT.name());
        try {
            currentTheme = Theme.valueOf(themeName);
        } catch (IllegalArgumentException e) {
            System.err.println("⚠️ Thème inconnu : " + themeName + ", utilisation du thème par défaut");
            currentTheme = Theme.PRIMER_LIGHT;
        }
        return currentTheme;
    }
    
    /**
     * Sauvegarde le thème dans les préférences
     */
    private static void saveThemePreference(Theme theme) {
        prefs.put(PREF_THEME_KEY, theme.name());
    }
    
    /**
     * Retourne le thème actuellement appliqué
     */
    public static Theme getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Applique le thème sauvegardé à une scène
     */
    public static void applySavedTheme(Scene scene) {
        Theme savedTheme = loadSavedTheme();
        applyTheme(scene, savedTheme);
    }
    
    /**
     * Bascule entre thème clair et sombre
     * (utilise les thèmes Primer par défaut)
     */
    public static void toggleDarkMode(Scene scene) {
        Theme newTheme = currentTheme.isDark() ? 
            Theme.PRIMER_LIGHT : 
            Theme.PRIMER_DARK;
        
        applyTheme(scene, newTheme);
    }
    
    /**
     * Retourne tous les thèmes disponibles
     */
    public static Theme[] getAllThemes() {
        return Theme.values();
    }
    
    /**
     * Retourne les thèmes clairs
     */
    public static Theme[] getLightThemes() {
        return java.util.Arrays.stream(Theme.values())
            .filter(t -> !t.isDark())
            .toArray(Theme[]::new);
    }
    
    /**
     * Retourne les thèmes sombres
     */
    public static Theme[] getDarkThemes() {
        return java.util.Arrays.stream(Theme.values())
            .filter(Theme::isDark)
            .toArray(Theme[]::new);
    }
}
