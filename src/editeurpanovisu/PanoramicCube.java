package editeurpanovisu;

import editeurpanovisu.gpu.ImageResizeGPU;
import editeurpanovisu.gpu.InterpolationMethod;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cube panoramique pour l'affichage de panoramas équirectangulaires
 * Utilise 6 faces de cube au lieu d'une sphère pour une meilleure stabilité
 * 
 * @author GitHub Copilot
 */
public class PanoramicCube extends Group {
    
    private static final double CUBE_SIZE = 1000;
    private final Box[] faces = new Box[6];
    private final PhongMaterial[] materials = new PhongMaterial[6];
    
    // Indices des faces
    private static final int FRONT = 0;
    private static final int BACK = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int TOP = 4;
    private static final int BOTTOM = 5;
    
    public PanoramicCube() {
        createCubeFaces();
        // Rotation initiale de 180° pour corriger l'orientation
        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
    }
    
    /**
     * Crée les 6 faces du cube
     */
    private void createCubeFaces() {
        double halfSize = CUBE_SIZE / 2;
        
        // Face AVANT (Front) - Z positif
        faces[FRONT] = new Box(CUBE_SIZE, CUBE_SIZE, 0.1);
        faces[FRONT].setTranslateZ(halfSize);
        
        // Face ARRIÈRE (Back) - Z négatif
        faces[BACK] = new Box(CUBE_SIZE, CUBE_SIZE, 0.1);
        faces[BACK].setTranslateZ(-halfSize);
        faces[BACK].setRotationAxis(Rotate.Y_AXIS);
        faces[BACK].setRotate(180);
        
        // Face GAUCHE (Left) - X négatif
        faces[LEFT] = new Box(0.1, CUBE_SIZE, CUBE_SIZE);
        faces[LEFT].setTranslateX(-halfSize);
        
        // Face DROITE (Right) - X positif
        faces[RIGHT] = new Box(0.1, CUBE_SIZE, CUBE_SIZE);
        faces[RIGHT].setTranslateX(halfSize);
        
        // Face HAUT (Top) - Y négatif
        faces[TOP] = new Box(CUBE_SIZE, 0.1, CUBE_SIZE);
        faces[TOP].setTranslateY(-halfSize);
        
        // Face BAS (Bottom) - Y positif
        faces[BOTTOM] = new Box(CUBE_SIZE, 0.1, CUBE_SIZE);
        faces[BOTTOM].setTranslateY(halfSize);
        
        // Configurer les matériaux et ajouter au groupe
        for (int i = 0; i < 6; i++) {
            materials[i] = new PhongMaterial();
            materials[i].setDiffuseColor(Color.BLACK); // Noir pour éliminer l'éclairage diffus
            materials[i].setSpecularColor(Color.BLACK); // Pas de reflets spéculaires
            faces[i].setMaterial(materials[i]);
            faces[i].setCullFace(CullFace.BACK); // Cacher les faces arrière, montrer les faces avant (intérieur)
            getChildren().add(faces[i]);
        }
    }
    
    /**
     * Applique une image panoramique équirectangulaire sur le cube
     * Utilise TransformationsPanoramique pour découper l'image en 6 faces
     * 
     * @param panoramicImage L'image panoramique équirectangulaire
     */
    public void setPanoramicImage(Image panoramicImage) {
        setPanoramicImage(panoramicImage, 1500, 750, 500);
    }
    
    /**
     * Applique une image panoramique équirectangulaire sur le cube avec qualité configurable
     * 
     * @param panoramicImage L'image panoramique équirectangulaire
     * @param equiWidth Largeur de l'image équirectangulaire intermédiaire
     * @param equiHeight Hauteur de l'image équirectangulaire intermédiaire
     * @param faceSize Taille des faces du cube (pixels)
     */
    public void setPanoramicImage(Image panoramicImage, int equiWidth, int equiHeight, int faceSize) {
        if (panoramicImage == null) {
            return;
        }
        
        System.out.println("🔄 PanoramicCube.setPanoramicImage() appelé");
        System.out.println("   📏 Taille image source: " + (int)panoramicImage.getWidth() + "×" + (int)panoramicImage.getHeight());
        System.out.println("   📦 Cube demandé: " + faceSize + "×" + faceSize);
        
        try {
            // Redimensionner l'image au ratio 2:1 (obligatoire pour equi2cube)
            Image resizedImage = resizeToEquirectangular(panoramicImage, equiWidth, equiHeight);
            
            // Convertir l'image équirectangulaire en 6 faces de cube
            // Order equi2cube: [0]=Front, [1]=Behind, [2]=Right, [3]=Left, [4]=Top, [5]=Bottom
            Image[] cubeFaces = TransformationsPanoramique.equi2cubeAuto(resizedImage, faceSize);
            
            // Mapper les faces correctement avec selfIlluminationMap pour éliminer les ombres
            // Notre ordre de materials: [0]=FRONT, [1]=BACK, [2]=LEFT, [3]=RIGHT, [4]=TOP, [5]=BOTTOM
            // equi2cubeAuto retourne: [0]=Front, [1]=Behind, [2]=Right, [3]=Left, [4]=Top, [5]=Bottom
            // ATTENTION: Il faut inverser Top et Bottom pour l'affichage correct dans le visualiseur 3D
            materials[FRONT].setSelfIlluminationMap(cubeFaces[0]);   // Front -> Front
            materials[BACK].setSelfIlluminationMap(cubeFaces[1]);    // Behind -> Back
            materials[LEFT].setSelfIlluminationMap(cubeFaces[3]);    // Left -> Left
            materials[RIGHT].setSelfIlluminationMap(cubeFaces[2]);   // Right -> Right
            materials[TOP].setSelfIlluminationMap(cubeFaces[5]);     // Bottom -> Top (inversé pour affichage)
            materials[BOTTOM].setSelfIlluminationMap(cubeFaces[4]);  // Top -> Bottom (inversé pour affichage)
            
            // Désactiver diffuse et spéculaire pour éliminer totalement les ombres
            for (int i = 0; i < 6; i++) {
                materials[i].setDiffuseMap(null);
                materials[i].setSpecularMap(null);
                materials[i].setDiffuseColor(Color.BLACK); // Pas de couleur diffuse
            }
        } catch (InterruptedException ex) {
            // En cas d'interruption, appliquer l'image complète (fallback)
            for (int i = 0; i < 6; i++) {
                materials[i].setSelfIlluminationMap(panoramicImage);
                materials[i].setDiffuseMap(null);
                materials[i].setSpecularMap(null);
                materials[i].setDiffuseColor(Color.BLACK);
            }
        }
    }
    
    /**
     * Applique directement les faces du cube pré-calculées (optimisation)
     * Cette méthode évite de recalculer les faces à chaque affichage
     * 
     * @param cubeFaces Tableau de 6 images pré-calculées [Front, Behind, Right, Left, Top, Bottom]
     */
    public void setCubeFaces(Image[] cubeFaces) {
        if (cubeFaces == null || cubeFaces.length != 6) {
            System.err.println("⚠️ PanoramicCube.setCubeFaces() - Tableau invalide");
            return;
        }
        
        System.out.println("⚡ PanoramicCube.setCubeFaces() - Utilisation du cache");
        
        // Mapper les faces pré-calculées avec inversion Top/Bottom
        materials[FRONT].setSelfIlluminationMap(cubeFaces[0]);   // Front -> Front
        materials[BACK].setSelfIlluminationMap(cubeFaces[1]);    // Behind -> Back
        materials[LEFT].setSelfIlluminationMap(cubeFaces[3]);    // Left -> Left
        materials[RIGHT].setSelfIlluminationMap(cubeFaces[2]);   // Right -> Right
        materials[TOP].setSelfIlluminationMap(cubeFaces[5]);     // Bottom -> Top (inversé)
        materials[BOTTOM].setSelfIlluminationMap(cubeFaces[4]);  // Top -> Bottom (inversé)
        
        // Désactiver diffuse et spéculaire
        for (int i = 0; i < 6; i++) {
            materials[i].setDiffuseMap(null);
            materials[i].setSpecularMap(null);
            materials[i].setDiffuseColor(Color.BLACK);
        }
    }
    
    /**
     * Redimensionne une image au format équirectangulaire avec ratio 2:1
     * 
     * @param source Image source
     * @param targetWidth Largeur cible
     * @param targetHeight Hauteur cible (devrait être targetWidth/2 pour ratio 2:1)
     * @return Image redimensionnée au ratio 2:1
     */
    private Image resizeToEquirectangular(Image source, int targetWidth, int targetHeight) {
        int srcWidth = (int) source.getWidth();
        int srcHeight = (int) source.getHeight();
        
        // Si l'image est déjà à la bonne taille, la retourner directement
        if (srcWidth == targetWidth && srcHeight == targetHeight) {
            return source;
        }
        
        try {
            // Utiliser GPU resize avec Bicubic (qualité optimale)
            Image resized = ImageResizeGPU.resizeAuto(
                source, 
                targetWidth, 
                targetHeight, 
                InterpolationMethod.BICUBIC
            );
            
            Logger.getLogger(PanoramicCube.class.getName()).log(
                Level.INFO,
                String.format("📐 Panorama redimensionné: %dx%d → %dx%d (Bicubic)",
                    srcWidth, srcHeight, targetWidth, targetHeight
                )
            );
            
            return resized;
            
        } catch (Exception e) {
            // Fallback sur l'ancien algorithme en cas d'erreur
            Logger.getLogger(PanoramicCube.class.getName()).log(
                Level.WARNING,
                "⚠️ Erreur GPU, fallback sur CPU: " + e.getMessage()
            );
            
            return resizeToEquirectangularCPU(source, targetWidth, targetHeight);
        }
    }
    
    /**
     * Fallback CPU : Redimensionnement par échantillonnage simple (nearest neighbor).
     * Utilisé uniquement si le GPU échoue.
     * 
     * @param source Image source
     * @param targetWidth Largeur cible
     * @param targetHeight Hauteur cible
     * @return Image redimensionnée
     */
    private Image resizeToEquirectangularCPU(Image source, int targetWidth, int targetHeight) {
        int srcWidth = (int) source.getWidth();
        int srcHeight = (int) source.getHeight();
        
        // Créer une nouvelle image redimensionnée
        WritableImage resized = new WritableImage(targetWidth, targetHeight);
        PixelReader reader = source.getPixelReader();
        PixelWriter writer = resized.getPixelWriter();
        
        // Redimensionnement par échantillonnage simple
        double xRatio = (double) srcWidth / targetWidth;
        double yRatio = (double) srcHeight / targetHeight;
        
        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int srcX = (int) (x * xRatio);
                int srcY = (int) (y * yRatio);
                
                // S'assurer qu'on ne dépasse pas les limites de l'image source
                if (srcX >= srcWidth) srcX = srcWidth - 1;
                if (srcY >= srcHeight) srcY = srcHeight - 1;
                
                Color color = reader.getColor(srcX, srcY);
                writer.setColor(x, y, color);
            }
        }
        
        return resized;
    }
    
    /**
     * Obtient la taille du cube
     * @return La taille du cube
     */
    public double getCubeSize() {
        return CUBE_SIZE;
    }
}
