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
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cube panoramique pour l'affichage de panoramas équirectangulaires.
 * Utilise des TriangleMesh (quads) au lieu de Box pour un mapping UV
 * couvrant la totalité de chaque face (0,0)→(1,1), évitant la distorsion
 * causée par le layout UV en croix de JavaFX Box.
 *
 * @author GitHub Copilot / PanoVisu
 */
public class PanoramicCube extends Group {

    private static final double CUBE_SIZE = 1000;
    private final MeshView[] faces = new MeshView[6];
    private final PhongMaterial[] materials = new PhongMaterial[6];

    // Indices des faces
    private static final int FRONT  = 0;
    private static final int BACK   = 1;
    private static final int LEFT   = 2;
    private static final int RIGHT  = 3;
    private static final int TOP    = 4;
    private static final int BOTTOM = 5;

    public PanoramicCube() {
        createCubeFaces();
        // Rotation initiale de 180° pour corriger l'orientation panoramique
        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
    }

    /**
     * Crée un quad (TriangleMesh) à partir de 4 coins dans l'ordre :
     * top-left, top-right, bottom-right, bottom-left (vu depuis l'intérieur).
     * UV : (0,0) top-left → (1,1) bottom-right — texture complète sur la face.
     */
    private MeshView createQuadFace(float x1, float y1, float z1,
                                    float x2, float y2, float z2,
                                    float x3, float y3, float z3,
                                    float x4, float y4, float z4) {
        TriangleMesh mesh = new TriangleMesh();

        // 4 coins de la face
        mesh.getPoints().addAll(
            x1, y1, z1,   // 0 : top-left
            x2, y2, z2,   // 1 : top-right
            x3, y3, z3,   // 2 : bottom-right
            x4, y4, z4    // 3 : bottom-left
        );

        // UV couvrant toute la texture
        mesh.getTexCoords().addAll(
            0f, 0f,   // 0 : top-left
            1f, 0f,   // 1 : top-right
            1f, 1f,   // 2 : bottom-right
            0f, 1f    // 3 : bottom-left
        );

        // Deux triangles formant le quad
        mesh.getFaces().addAll(
            0,0, 1,1, 2,2,   // triangle 1
            0,0, 2,2, 3,3    // triangle 2
        );

        MeshView mv = new MeshView(mesh);
        mv.setCullFace(CullFace.NONE); // visible depuis l'intérieur ET l'extérieur
        return mv;
    }

    /**
     * Crée les 6 faces du cube panoramique.
     * Les coins de chaque face sont définis dans l'ordre top-left, top-right,
     * bottom-right, bottom-left tels que vus depuis l'intérieur du cube.
     */
    private void createCubeFaces() {
        float h = (float) (CUBE_SIZE / 2);

        // ── FRONT  (z = +h) : caméra regardant +Z ──────────────────────────
        // vue intérieure : gauche = -X, droite = +X, haut = -Y
        faces[FRONT] = createQuadFace(
            -h, -h,  h,
             h, -h,  h,
             h,  h,  h,
            -h,  h,  h
        );

        // ── BACK  (z = -h) : caméra regardant -Z ───────────────────────────
        // vue intérieure : gauche = +X, droite = -X, haut = -Y
        faces[BACK] = createQuadFace(
             h, -h, -h,
            -h, -h, -h,
            -h,  h, -h,
             h,  h, -h
        );

        // ── RIGHT  (x = +h) : caméra regardant +X ──────────────────────────
        // vue intérieure : gauche = +Z, droite = -Z, haut = -Y
        faces[RIGHT] = createQuadFace(
             h, -h,  h,
             h, -h, -h,
             h,  h, -h,
             h,  h,  h
        );

        // ── LEFT  (x = -h) : caméra regardant -X ───────────────────────────
        // vue intérieure : gauche = -Z, droite = +Z, haut = -Y
        faces[LEFT] = createQuadFace(
            -h, -h, -h,
            -h, -h,  h,
            -h,  h,  h,
            -h,  h, -h
        );

        // ── TOP  (y = -h) : caméra regardant -Y (vers le haut) ─────────────
        // U flipé : gauche caméra (-X) → U=0, droite caméra (+X) → U=1
        faces[TOP] = createQuadFace(
            -h, -h, -h,
             h, -h, -h,
             h, -h,  h,
            -h, -h,  h
        );

        // ── BOTTOM  (y = +h) : caméra regardant +Y (vers le bas) ───────────
        // U flipé : gauche caméra (-X) → U=0, droite caméra (+X) → U=1
        faces[BOTTOM] = createQuadFace(
            -h,  h,  h,
             h,  h,  h,
             h,  h, -h,
            -h,  h, -h
        );

        // Créer et attacher les matériaux
        for (int i = 0; i < 6; i++) {
            materials[i] = new PhongMaterial();
            materials[i].setDiffuseColor(Color.BLACK);   // pas d'éclairage diffus
            materials[i].setSpecularColor(Color.BLACK);  // pas de reflets
            faces[i].setMaterial(materials[i]);
            getChildren().add(faces[i]);
        }
    }

    /**
     * Applique une image panoramique équirectangulaire sur le cube.
     *
     * @param panoramicImage L'image équirectangulaire source
     */
    public void setPanoramicImage(Image panoramicImage) {
        setPanoramicImage(panoramicImage, 1500, 750, 500);
    }

    /**
     * Applique une image panoramique équirectangulaire sur le cube.
     *
     * @param panoramicImage L'image équirectangulaire source
     * @param equiWidth      Largeur de l'image intermédiaire (doit être 2×equiHeight)
     * @param equiHeight     Hauteur de l'image intermédiaire
     * @param faceSize       Taille (px) de chaque face du cube
     */
    public void setPanoramicImage(Image panoramicImage, int equiWidth, int equiHeight, int faceSize) {
        if (panoramicImage == null) return;

        System.out.println("🔄 PanoramicCube.setPanoramicImage() — source : "
            + (int) panoramicImage.getWidth() + "×" + (int) panoramicImage.getHeight());

        try {
            Image resized = resizeToEquirectangular(panoramicImage, equiWidth, equiHeight);
            // equi2cubeAuto → [0]=Front [1]=Behind [2]=Right [3]=Left [4]=Top [5]=Bottom
            Image[] cubeFaces = TransformationsPanoramique.equi2cubeAuto(resized, faceSize);
            applyCubeFaces(cubeFaces);
        } catch (InterruptedException ex) {
            // fallback : texture brute sur toutes les faces
            for (int i = 0; i < 6; i++) {
                materials[i].setSelfIlluminationMap(panoramicImage);
                materials[i].setDiffuseMap(null);
                materials[i].setDiffuseColor(Color.BLACK);
            }
        }
    }

    /**
     * Applique directement des faces pré-calculées (cache).
     *
     * @param cubeFaces [0]=Front [1]=Behind [2]=Right [3]=Left [4]=Top [5]=Bottom
     */
    public void setCubeFaces(Image[] cubeFaces) {
        if (cubeFaces == null || cubeFaces.length != 6) {
            System.err.println("⚠️ PanoramicCube.setCubeFaces() — tableau invalide");
            return;
        }
        System.out.println("⚡ PanoramicCube.setCubeFaces() — utilisation du cache");
        applyCubeFaces(cubeFaces);
    }

    /**
     * Applique le tableau de faces [Front, Behind, Right, Left, Top, Bottom]
     * sur les matériaux du cube.
     */
    private void applyCubeFaces(Image[] cubeFaces) {
        // equi2cubeAuto : [0]=Front [1]=Behind [2]=Right [3]=Left [4]=Top [5]=Bottom
        // Nos indices  : FRONT=0  BACK=1  LEFT=2  RIGHT=3  TOP=4  BOTTOM=5
        materials[FRONT] .setSelfIlluminationMap(cubeFaces[0]);
        materials[BACK]  .setSelfIlluminationMap(cubeFaces[1]);
        materials[LEFT]  .setSelfIlluminationMap(cubeFaces[3]);
        materials[RIGHT] .setSelfIlluminationMap(cubeFaces[2]);
        // Top/Bottom inversés : axe Y JavaFX opposé à la convention equi2cube
        materials[TOP]   .setSelfIlluminationMap(cubeFaces[5]); // equi Bottom → face Top
        materials[BOTTOM].setSelfIlluminationMap(cubeFaces[4]); // equi Top → face Bottom

        for (int i = 0; i < 6; i++) {
            materials[i].setDiffuseMap(null);
            materials[i].setSpecularMap(null);
            materials[i].setDiffuseColor(Color.BLACK);
        }
    }

    // ── Redimensionnement ────────────────────────────────────────────────────

    private Image resizeToEquirectangular(Image source, int targetWidth, int targetHeight) {
        int sw = (int) source.getWidth();
        int sh = (int) source.getHeight();
        if (sw == targetWidth && sh == targetHeight) return source;

        try {
            Image resized = ImageResizeGPU.resizeAuto(source, targetWidth, targetHeight,
                                                      InterpolationMethod.BICUBIC);
            Logger.getLogger(PanoramicCube.class.getName()).log(Level.INFO,
                "📐 Redimensionné : {0}×{1} → {2}×{3} (Bicubic)",
                new Object[]{sw, sh, targetWidth, targetHeight});
            return resized;
        } catch (Exception e) {
            Logger.getLogger(PanoramicCube.class.getName()).log(Level.WARNING,
                "⚠️ GPU échoué, fallback CPU : {0}", e.getMessage());
            return resizeToEquirectangularCPU(source, targetWidth, targetHeight);
        }
    }

    private Image resizeToEquirectangularCPU(Image source, int tw, int th) {
        int sw = (int) source.getWidth();
        int sh = (int) source.getHeight();
        WritableImage out = new WritableImage(tw, th);
        PixelReader reader = source.getPixelReader();
        PixelWriter writer = out.getPixelWriter();
        double rx = (double) sw / tw;
        double ry = (double) sh / th;
        for (int y = 0; y < th; y++) {
            for (int x = 0; x < tw; x++) {
                int sx = Math.min((int) (x * rx), sw - 1);
                int sy = Math.min((int) (y * ry), sh - 1);
                writer.setColor(x, y, reader.getColor(sx, sy));
            }
        }
        return out;
    }

    /** @return La taille du cube */
    public double getCubeSize() { return CUBE_SIZE; }
}
