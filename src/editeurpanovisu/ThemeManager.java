package editeurpanovisu;

import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.scene.Scene;

import java.util.prefs.Preferences;

/**
 * Gestionnaire de thèmes pour l'application EditeurPanovisu.
 * Permet de basculer entre différents thèmes modernes (AtlantaFX, MaterialFX, FlatLaf).
 */
public class ThemeManager {
    
    /**
     * Type de bibliothèque de thème
     */
    public enum ThemeProvider {
        ATLANTAFX,
        MATERIALFX,
        FLATLAF,
        CUSTOM
    }
    
    /**
     * Liste des thèmes disponibles - organisés par thème clair/sombre
     */
    public enum Theme {
        // ═══════════════════════════════════════════════════════════
        // 🌞 THÈMES CLAIRS
        // ═══════════════════════════════════════════════════════════
        
        // AtlantaFX - Thèmes clairs
        PRIMER_LIGHT("Primer Light", ThemeProvider.ATLANTAFX, "PrimerLight", false),
        NORD_LIGHT("Nord Light", ThemeProvider.ATLANTAFX, "NordLight", false),
        CUPERTINO_LIGHT("Cupertino Light", ThemeProvider.ATLANTAFX, "CupertinoLight", false),
        
        // MaterialFX - Thème clair
        MATERIAL_LIGHT("Material Light", ThemeProvider.MATERIALFX, null, false),
        
        // FlatLaf - Thèmes clairs
        FLATLAF_LIGHT("FlatLaf Light", ThemeProvider.FLATLAF, null, false),
        FLATLAF_INTELLIJ("FlatLaf IntelliJ", ThemeProvider.FLATLAF, "IntelliJ", false),
        
        // Personnalisé legacy
        CUSTOM_LIGHT("Thème Clair Personnalisé", ThemeProvider.CUSTOM, null, false),
        
        // ═══════════════════════════════════════════════════════════
        // 🌙 THÈMES SOMBRES
        // ═══════════════════════════════════════════════════════════
        
        // AtlantaFX - Thèmes sombres
        PRIMER_DARK("Primer Dark", ThemeProvider.ATLANTAFX, "PrimerDark", true),
        NORD_DARK("Nord Dark", ThemeProvider.ATLANTAFX, "NordDark", true),
        CUPERTINO_DARK("Cupertino Dark", ThemeProvider.ATLANTAFX, "CupertinoDark", true),
        DRACULA("Dracula", ThemeProvider.ATLANTAFX, "Dracula", true),
        
        // MaterialFX - Thème sombre
        MATERIAL_DARK("Material Dark", ThemeProvider.MATERIALFX, null, true),
        
        // FlatLaf - Thèmes sombres
        FLATLAF_DARK("FlatLaf Dark", ThemeProvider.FLATLAF, null, true),
        FLATLAF_DARCULA("FlatLaf Darcula", ThemeProvider.FLATLAF, "Darcula", true),
        
        // Personnalisé legacy
        CUSTOM_DARK("Thème Foncé Personnalisé", ThemeProvider.CUSTOM, null, true);
        
        private final String displayName;
        private final ThemeProvider provider;
        private final String atlantaThemeClassName; // Nom de la classe au lieu d'instance
        private final boolean isDark;
        private atlantafx.base.theme.Theme atlantaTheme; // Initialisation paresseuse
        
        Theme(String displayName, ThemeProvider provider, String atlantaThemeClassName, boolean isDark) {
            this.displayName = displayName;
            this.provider = provider;
            this.atlantaThemeClassName = atlantaThemeClassName;
            this.isDark = isDark;
            this.atlantaTheme = null; // Sera initialisé à la demande
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public ThemeProvider getProvider() {
            return provider;
        }
        
        public atlantafx.base.theme.Theme getAtlantaTheme() {
            // Initialisation paresseuse : créer l'instance seulement quand nécessaire
            if (atlantaTheme == null && atlantaThemeClassName != null) {
                try {
                    switch (atlantaThemeClassName) {
                        case "PrimerLight": atlantaTheme = new PrimerLight(); break;
                        case "PrimerDark": atlantaTheme = new PrimerDark(); break;
                        case "NordLight": atlantaTheme = new NordLight(); break;
                        case "NordDark": atlantaTheme = new NordDark(); break;
                        case "CupertinoLight": atlantaTheme = new CupertinoLight(); break;
                        case "CupertinoDark": atlantaTheme = new CupertinoDark(); break;
                        case "Dracula": atlantaTheme = new Dracula(); break;
                        default:
                            System.err.println("⚠️ Classe de thème AtlantaFX inconnue : " + atlantaThemeClassName);
                    }
                } catch (Exception e) {
                    System.err.println("⚠️ Erreur création thème AtlantaFX : " + e.getMessage());
                }
            }
            return atlantaTheme;
        }
        
        public boolean isDark() {
            return isDark;
        }
        
        public boolean isCustom() {
            return provider == ThemeProvider.CUSTOM;
        }
        
        public boolean isAtlantaFX() {
            return provider == ThemeProvider.ATLANTAFX;
        }
        
        public boolean isMaterialFX() {
            return provider == ThemeProvider.MATERIALFX;
        }
        
        public boolean isFlatLaf() {
            return provider == ThemeProvider.FLATLAF;
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
        cleanRootClasses(scene);
        
        // Appliquer le thème selon son type
        switch (theme.getProvider()) {
            case CUSTOM:
                applyCustomTheme(scene, theme);
                break;
            case MATERIALFX:
                applyMaterialFXTheme(scene, theme);
                break;
            case FLATLAF:
                applyFlatLafTheme(scene, theme);
                break;
            case ATLANTAFX:
            default:
                applyAtlantaFXTheme(scene, theme);
                break;
        }
        
        // Ajouter les styles personnalisés de l'application
        addCustomStyles(scene, theme);
        
        currentTheme = theme;
        saveThemePreference(theme);
    }
    
    /**
     * Nettoie toutes les classes CSS du root
     */
    private static void cleanRootClasses(Scene scene) {
        scene.getRoot().getStyleClass().removeAll(
            "primer-light", "primer-dark",
            "nord-light", "nord-dark",
            "cupertino-light", "cupertino-dark",
            "dracula"
        );
    }
    
    /**
     * Applique un thème AtlantaFX
     */
    private static void applyAtlantaFXTheme(Scene scene, Theme theme) {
        Application.setUserAgentStylesheet(theme.getAtlantaTheme().getUserAgentStylesheet());
        
        String themeClass = theme.name().toLowerCase().replace('_', '-');
        scene.getRoot().getStyleClass().add(themeClass);
        
        System.out.println("✅ Thème AtlantaFX appliqué : " + theme.getDisplayName());
        System.out.println("  ↳ Classe CSS ajoutée : " + themeClass);
    }
    
    /**
     * Applique un thème personnalisé (CSS legacy)
     */
    private static void applyCustomTheme(Scene scene, Theme theme) {
        Application.setUserAgentStylesheet(null); // Reset
        
        String cssPath = theme.isDark() ? 
            "/css/fonce.css" : 
            "/css/clair.css";
        
        String cssUrl = ThemeManager.class.getResource(cssPath).toExternalForm();
        scene.getStylesheets().add(cssUrl);
        
        System.out.println("✅ Thème personnalisé appliqué : " + theme.getDisplayName());
    }
    
    /**
     * Applique un thème MaterialFX
     */
    private static void applyMaterialFXTheme(Scene scene, Theme theme) {
        Application.setUserAgentStylesheet(null); // Reset
        
        try {
            // Charger le CSS Material adapté pour JavaFX
            String cssUrl = ThemeManager.class.getResource("/css/material-javafx.css").toExternalForm();
            scene.getStylesheets().add(cssUrl);
            
            // Ajouter une classe CSS pour le mode sombre/clair
            if (theme.isDark()) {
                scene.getRoot().getStyleClass().add("material-dark");
            } else {
                scene.getRoot().getStyleClass().add("material-light");
            }
            
            System.out.println("✅ Thème MaterialFX appliqué : " + theme.getDisplayName());
        } catch (Exception e) {
            System.err.println("⚠️ Erreur chargement thème MaterialFX : " + e.getMessage());
            // Fallback sur thème par défaut
            applyAtlantaFXTheme(scene, theme.isDark() ? Theme.PRIMER_DARK : Theme.PRIMER_LIGHT);
        }
    }
    
    /**
     * Applique un thème FlatLaf
     */
    private static void applyFlatLafTheme(Scene scene, Theme theme) {
        Application.setUserAgentStylesheet(null); // Reset
        
        try {
            // Charger le CSS FlatLaf adapté pour JavaFX
            String cssUrl = ThemeManager.class.getResource("/css/flatlaf-javafx.css").toExternalForm();
            scene.getStylesheets().add(cssUrl);
            
            // Ajouter une classe CSS selon le variant
            String variant = theme.atlantaThemeClassName; // On réutilise ce champ pour stocker le variant
            if (variant != null) {
                scene.getRoot().getStyleClass().add("flatlaf-" + variant.toLowerCase());
            } else {
                scene.getRoot().getStyleClass().add(theme.isDark() ? "flatlaf-dark" : "flatlaf-light");
            }
            
            System.out.println("✅ Thème FlatLaf appliqué : " + theme.getDisplayName());
        } catch (Exception e) {
            System.err.println("⚠️ Erreur chargement thème FlatLaf : " + e.getMessage());
            // Fallback sur thème par défaut
            applyAtlantaFXTheme(scene, theme.isDark() ? Theme.PRIMER_DARK : Theme.PRIMER_LIGHT);
        }
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
