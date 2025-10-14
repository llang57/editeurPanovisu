package editeurpanovisu.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

/**
 * Utilitaire pour charger des icônes SVG et les adapter au thème CSS
 * Les SVG utilisent "currentColor" qui peut être changé dynamiquement
 * Utilise Apache Batik pour convertir les SVG en images PNG
 * 
 * Couleurs adaptées automatiquement selon le thème :
 * - DRACULA : #BD93F9 (Mauve Dracula signature)
 * - FLATLAF_DARCULA : #9876AA (Violet IntelliJ)
 * - Autres thèmes sombres : #E0E0E0 (Gris clair)
 * - Thèmes clairs : #333333 (Gris foncé)
 * 
 * @author PanoVisu
 */
public class SvgIconLoader {
    
    private static final String SVG_PATH = "images/svg/";
    private static String baseAppPath = "";
    
    // Cache pour éviter de recharger les mêmes icônes
    private static final Map<String, Image> iconCache = new HashMap<>();
    
    /**
     * Définit le chemin de base de l'application
     * @param path Chemin absolu vers le répertoire de l'application
     */
    public static void setBaseAppPath(String path) {
        baseAppPath = path;
    }
    
    /**
     * Vide le cache des icônes (utile lors d'un changement de thème)
     */
    public static void clearCache() {
        iconCache.clear();
    }
    
    /**
     * Détecte la couleur appropriée selon le thème actif (clair ou foncé)
     * Fonctionne avec tous les thèmes : AtlantaFX, MaterialFX, FlatLaf et thèmes personnalisés
     * Couleurs spéciales pour Dracula et Darcula (mauve caractéristique)
     * @return Couleur adaptée au thème
     */
    private static Color getThemeColor() {
        try {
            // Utiliser ThemeManager pour détecter le thème actuel
            Class<?> themeManagerClass = Class.forName("editeurpanovisu.ThemeManager");
            java.lang.reflect.Method getCurrentThemeMethod = themeManagerClass.getMethod("getCurrentTheme");
            Object currentTheme = getCurrentThemeMethod.invoke(null);
            
            // Récupérer le nom du thème
            java.lang.reflect.Method nameMethod = currentTheme.getClass().getMethod("name");
            String themeName = (String) nameMethod.invoke(currentTheme);
            
            // Couleurs spéciales pour Dracula et Darcula
            if ("DRACULA".equals(themeName)) {
                return Color.web("#BD93F9"); // Mauve Dracula caractéristique
            } else if ("FLATLAF_DARCULA".equals(themeName)) {
                return Color.web("#9876AA"); // Violet Darcula IntelliJ
            }
            
            // Vérifier si le thème est sombre
            java.lang.reflect.Method isDarkMethod = currentTheme.getClass().getMethod("isDark");
            boolean isDark = (Boolean) isDarkMethod.invoke(currentTheme);
            
            // Thème sombre = icônes claires, thème clair = icônes foncées
            if (isDark) {
                return Color.web("#E0E0E0"); // Gris très clair pour thèmes sombres
            } else {
                return Color.web("#333333"); // Gris foncé pour thèmes clairs
            }
        } catch (Exception e) {
            // Fallback sur l'ancienne méthode avec EditeurPanovisu.getStrStyleCSS()
            try {
                Class<?> editeurClass = Class.forName("editeurpanovisu.EditeurPanovisu");
                java.lang.reflect.Method getStyleMethod = editeurClass.getMethod("getStrStyleCSS");
                String theme = (String) getStyleMethod.invoke(null);
                
                if ("fonce".equalsIgnoreCase(theme)) {
                    return Color.web("#E0E0E0");
                } else {
                    return Color.web("#333333");
                }
            } catch (Exception ex) {
                // En cas d'erreur totale, utiliser la couleur par défaut (thème clair)
                System.err.println("Impossible de détecter le thème, utilisation de la couleur par défaut");
                return Color.web("#333333");
            }
        }
    }
    
    /**
     * Charge une icône SVG et la convertit en ImageView avec dimensions personnalisées
     * @param iconName Nom du fichier SVG (sans extension)
     * @param width Largeur en pixels
     * @param height Hauteur en pixels
     * @param color Couleur de l'icône (optionnel, null pour détecter automatiquement selon le thème)
     * @return ImageView contenant l'icône
     */
    public static ImageView loadSvgIcon(String iconName, int width, int height, Color color) {
        // Si aucune couleur spécifiée, détecter automatiquement selon le thème
        if (color == null) {
            color = getThemeColor();
        }
        
        // Créer une clé unique pour le cache
        String cacheKey = iconName + "_" + width + "x" + height + "_" + color.toString();
        
        // Vérifier si l'icône est déjà en cache
        if (iconCache.containsKey(cacheKey)) {
            return new ImageView(iconCache.get(cacheKey));
        }
        
        try {
            String svgPath = baseAppPath + File.separator + SVG_PATH + iconName + ".svg";
            File svgFile = new File(svgPath);
            
            // Si le SVG n'existe pas, essayer de charger le PNG équivalent
            if (!svgFile.exists()) {
                return loadFallbackPng(iconName, width);
            }
            
            // Lire le contenu du SVG
            String svgContent = new String(Files.readAllBytes(Paths.get(svgPath)), "UTF-8");
            
            // Remplacer currentColor par la couleur spécifiée
            String hexColor = String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
            svgContent = svgContent.replace("currentColor", hexColor);
            
            // Remplacer également "white" par la couleur de fond (blanc pour thème clair)
            // Note: ceci est une simplification, dans un vrai système il faudrait détecter le thème
            
            // Convertir le SVG en PNG avec Batik
            BufferedImage bufferedImage = convertSvgToPng(svgContent, width, height);
            
            // Convertir BufferedImage en JavaFX Image
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            
            // Mettre en cache
            iconCache.put(cacheKey, fxImage);
            
            return new ImageView(fxImage);
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'icône SVG: " + iconName);
            e.printStackTrace();
            return loadFallbackPng(iconName, width);
        }
    }
    
    /**
     * Charge une icône SVG et la convertit en ImageView (version carrée)
     * @param iconName Nom du fichier SVG (sans extension)
     * @param size Taille en pixels (carré)
     * @param color Couleur de l'icône (optionnel, null pour détecter automatiquement selon le thème)
     * @return ImageView contenant l'icône
     */
    public static ImageView loadSvgIcon(String iconName, int size, Color color) {
        return loadSvgIcon(iconName, size, size, color);
    }
    
    /**
     * Convertit un SVG en BufferedImage PNG avec Batik (dimensions personnalisées)
     * @param svgContent Contenu du fichier SVG
     * @param width Largeur de sortie en pixels
     * @param height Hauteur de sortie en pixels
     * @return BufferedImage du PNG
     */
    private static BufferedImage convertSvgToPng(String svgContent, int width, int height) throws Exception {
        // Créer le transcoder PNG
        PNGTranscoder transcoder = new PNGTranscoder();
        
        // Définir la taille de sortie
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);
        
        // Créer un flux pour le SVG
        ByteArrayInputStream inputStream = new ByteArrayInputStream(svgContent.getBytes("UTF-8"));
        TranscoderInput input = new TranscoderInput(inputStream);
        
        // Créer un flux de sortie pour le PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outputStream);
        
        // Effectuer la conversion
        transcoder.transcode(input, output);
        
        // Convertir le flux en BufferedImage
        ByteArrayInputStream pngInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return javax.imageio.ImageIO.read(pngInputStream);
    }
    
    /**
     * Convertit un SVG en BufferedImage PNG avec Batik (version carrée)
     * @param svgContent Contenu du fichier SVG
     * @param size Taille de sortie en pixels (carré)
     * @return BufferedImage du PNG
     */
    private static BufferedImage convertSvgToPng(String svgContent, int size) throws Exception {
        return convertSvgToPng(svgContent, size, size);
    }
    
    /**
     * Charge une icône SVG avec la couleur par défaut
     * @param iconName Nom du fichier SVG
     * @param size Taille en pixels
     * @return ImageView contenant l'icône
     */
    public static ImageView loadSvgIcon(String iconName, int size) {
        return loadSvgIcon(iconName, size, null);
    }
    
    /**
     * Fallback: charge l'icône PNG si le SVG n'est pas disponible
     */
    private static ImageView loadFallbackPng(String iconName, int size) {
        try {
            // Mapper les noms SVG vers les noms PNG existants
            String pngName = mapSvgToPng(iconName);
            String pngPath = "file:" + baseAppPath + File.separator + "images" + File.separator + pngName;
            Image image = new Image(pngPath, size, size, true, true);
            return new ImageView(image);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du PNG fallback: " + iconName);
            // Retourner une ImageView vide plutôt que null
            return new ImageView();
        }
    }
    
    /**
     * Mappe les noms de fichiers SVG vers les noms PNG existants
     */
    private static String mapSvgToPng(String svgName) {
        switch (svgName) {
            case "nouveau-projet": return "nouveauProjet.png";
            case "ouvrir-projet": return "ouvrirProjet.png";
            case "sauve-projet": return "sauveProjet.png";
            case "ajoute-panoramique": return "ajoutePanoramique.png";
            case "ajoute-plan": return "ajoutePlan.png";
            case "genere-visite": return "genereVisite.png";
            case "ajoute-image": return "ajouteImage.png";
            case "play-auto-tour": return "playAutoTour.png";
            case "pause-auto-tour": return "pauseAutoTour.png";
            case "vue-sphere": return "equi2cube.png";
            case "vue-cube": return "cube2equi.png";
            case "valide": return "valide.png";
            case "annule": return "annule.png";
            case "lien": return "lien.png";
            case "loupe": return "loupe.png";
            case "ajoute": return "ajoute.png";
            case "suppr": return "suppr.png";
            case "maison": return "maison.png";
            case "modifie": return "modifie.png";
            case "ferme": return "ferme.png";
            case "expand": return "expand.png";
            case "shrink": return "shrink.png";
            default: return svgName + ".png";
        }
    }
    
    /**
     * Crée un ImageView avec couleur adaptée au thème
     * @param iconName Nom de l'icône
     * @param size Taille
     * @param isDarkTheme True si thème sombre
     * @return ImageView colorisée
     */
    public static ImageView loadThemedIcon(String iconName, int size, boolean isDarkTheme) {
        Color iconColor = isDarkTheme ? Color.web("#E0E0E0") : Color.web("#333333");
        return loadSvgIcon(iconName, size, iconColor);
    }
}
