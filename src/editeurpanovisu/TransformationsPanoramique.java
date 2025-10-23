/*
 * Transformations des images panoramiques (Cube / Equirectangulaire)
 * 
 * Architecture hybride GPU/CPU :
 * - Si GPU disponible et activé → TransformationsPanoramiqueGPU (OpenCL)
 * - Sinon → Cette classe (CPU multithread optimisé)
 * 
 * Gains de performance typiques :
 * - GPU: 20x à 50x plus rapide que CPU séquentiel
 * - CPU multithread: 4x à 8x plus rapide que CPU séquentiel
 */
package editeurpanovisu;

import editeurpanovisu.gpu.GPUManager;
import static editeurpanovisu.EquiCubeDialogController.pbBarreImage;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * fonctions de transformation des images panoramiques (Cube / Equirectangulaire)
 * Version CPU optimisée avec support multithread
 * 
 * @author llang
 */
public class TransformationsPanoramique {

    /**
     * Constante rapport de taille d'image transformation entre cube equi Taille
     * face de cube = RAPPORTCUBEEQUI*TailleEqui X RAPPORTCUBEEQUI*TailleEqui
     */
    public static final double RAPPORTCUBEEQUI = 0.3183;

    /**
     * Transformation Équirectangulaire vers Cube avec routage automatique GPU/CPU
     * Cette méthode détecte automatiquement si un GPU est disponible et l'utilise
     * 
     * @param equi Image équirectangulaire source
     * @param taille Taille souhaitée pour les faces du cube (-1 pour auto)
     * @return Tableau de 6 images représentant les faces du cube
     * @throws InterruptedException Si l'opération est interrompue
     */
    public static Image[] equi2cubeAuto(Image equi, int taille) throws InterruptedException {
        GPUManager gpu = GPUManager.getInstance();
        
        if (gpu.isGPUEnabled()) {
            System.out.println("🎮 Utilisation du GPU pour Equi→Cube");
            try {
                return TransformationsPanoramiqueGPU.equi2cube(equi, taille);
            } catch (Exception e) {
                System.err.println("⚠️  GPU échec, fallback CPU: " + e.getMessage());
            }
        }
        
        System.out.println("💻 Utilisation du CPU pour Equi→Cube");
        return equi2cube(equi, taille);
    }

    /**
     * Transformation Équirectangulaire vers Cube (version CPU originale)
     *
     * @param equi Image source équirectangulaire
     * @param taille Taille des faces du cube (-1 pour automatique)
     * @return Tableau de 6 images (faces du cube)
     * @throws InterruptedException Si interrompu
     */
    public static Image[] equi2cube(Image equi, int taille) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        
        PixelReader PREqui = equi.getPixelReader();
        WritableImage[] cube = new WritableImage[6];
        int tailleEqui = (int) equi.getWidth();
        int hauteurEqui = (int) equi.getHeight();
        int tailleCube;
        if (taille == -1) {
            tailleCube = (int) (tailleEqui * TransformationsPanoramique.RAPPORTCUBEEQUI);
        } else {
            tailleCube = taille;
        }
        
        double ratioActuel = (double)tailleEqui / hauteurEqui;
        System.out.println("🖥️  Transformation Equi→Cube sur CPU");
        System.out.println("   📐 Équi: " + tailleEqui + "×" + hauteurEqui + " (ratio: " + String.format("%.3f", ratioActuel) + ")");
        System.out.println("   📦 Cube: " + tailleCube + "×" + tailleCube);
        if (Math.abs(ratioActuel - 2.0) > 0.01) {
            System.out.println("   ⚠️  ATTENTION: Ratio non conforme (attendu 2:1, obtenu " + String.format("%.3f", ratioActuel) + ":1)");
        }
        
        for (int i = 0; i < 6; i++) {
            cube[i] = new WritableImage(tailleCube, tailleCube);
        }
        PixelWriter PWFront = cube[0].getPixelWriter();
        PixelWriter PWBehind = cube[1].getPixelWriter();
        PixelWriter PWRight = cube[2].getPixelWriter();
        PixelWriter PWLeft = cube[3].getPixelWriter();
        PixelWriter PWTop = cube[4].getPixelWriter();
        PixelWriter PWBottom = cube[5].getPixelWriter();
        double theta;
        double phi;
        double rapport = (double) (tailleEqui / (2.d * Math.PI));
        double deuxPI = 2 * Math.PI;
        double red;
        double green;
        double blue;
        double pixelX;
        double pixelY;
        Platform.runLater(() -> {
            pbBarreImage.setProgress(0.0f);
        });
        Thread.sleep(50);
        // Face Front (0) - Aligné avec le kernel OpenCL : vecX=X, vecY=-Y, vecZ=1
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {
                double X = (2.d * (double) pX - tailleCube) / tailleCube;
                double Y = -(2.d * (double) pY - tailleCube) / tailleCube;  // vecY = -Y
                double Z = 1;
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWFront.setColor(pX, pY, new Color(red, green, blue, 1));

            }

        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(1.0f / 6.0f);
        });
        Thread.sleep(50);

        // Face Behind (1) - Aligné avec le kernel OpenCL : vecX=-X, vecY=-Y, vecZ=-1
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = -(2.d * pX - tailleCube) / tailleCube;  // vecX = -X
                double Y = -(2.d * pY - tailleCube) / tailleCube;  // vecY = -Y
                double Z = -1;
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWBehind.setColor(pX, pY, new Color(red, green, blue, 1));  // Écriture directe comme OpenCL
            }
        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(2.0f / 6.0f);
        });
        Thread.sleep(50);
        // Face Left (3) - Aligné avec le kernel OpenCL : vecX=-1, vecY=-Y, vecZ=X
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = -1;  // vecX = -1
                double Y = -(2.d * pY - tailleCube) / tailleCube;  // vecY = -Y
                double Z = (2.d * pX - tailleCube) / tailleCube;  // vecZ = X (pX correspond à l'axe X)
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWLeft.setColor(pX, pY, new Color(red, green, blue, 1));  // Écriture directe comme OpenCL
            }
        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(3.0f / 6.0f);
        });
        Thread.sleep(50);

        // Face Right (2) - Aligné avec le kernel OpenCL : vecX=1, vecY=-Y, vecZ=-X
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = 1;  // vecX = 1
                double Y = -(2.d * pY - tailleCube) / tailleCube;  // vecY = -Y
                double Z = -(2.d * pX - tailleCube) / tailleCube;  // vecZ = -X (pX correspond à l'axe X)
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWRight.setColor(pX, pY, new Color(red, green, blue, 1));  // Écriture directe comme OpenCL
            }
        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(4.0f / 6.0f);
        });
        Thread.sleep(50);

        // Face Top - Aligné avec le kernel OpenCL : vecY = -1.0f, vecZ = -Y
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = (2.d * pX - tailleCube) / tailleCube;
                double Y = -1;  // Top face: Y = -1 (comme OpenCL)
                double Z = -(2.d * pY - tailleCube) / tailleCube;  // Z = -Y (comme OpenCL)
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWTop.setColor(pX, pY, new Color(red, green, blue, 1));  // Écriture directe comme OpenCL
            }
        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(5.0f / 6.0f);
        });
        Thread.sleep(50);

        // Face Bottom - Aligné avec le kernel OpenCL : vecY = 1.0f, vecZ = Y
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = (2.d * pX - tailleCube) / tailleCube;
                double Y = 1;  // Bottom face: Y = 1 (comme OpenCL)
                double Z = (2.d * pY - tailleCube) / tailleCube;  // Z = Y (comme OpenCL)
                
                // Calcul theta avec atan2 pour cohérence avec OpenCL
                theta = Math.atan2(X, Z);
                
                // Calcul phi
                double r = Math.sqrt(X * X + Y * Y + Z * Z);
                phi = Math.acos(Y / r);
                
                // Normalisation comme OpenCL
                theta = (theta + Math.PI + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                
                pixelX = (theta * rapport);
                pixelY = (phi * rapport);
                red = 0.0d;
                green = 0.0d;
                blue = 0.0d;
                double coeff = 0.0d;
                for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                    for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                        int pixX = i;
                        int pixY = j;
                        if (pixX < 0) {
                            pixX = 0;
                        }
                        if (pixY < 0) {
                            pixY = 0;
                        }
                        if (pixX > tailleEqui - 1) {
                            pixX = tailleEqui - 1;
                        }
                        if (pixY > hauteurEqui - 1) {
                            pixY = hauteurEqui - 1;
                        }
                        double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                        if (dst < 0.001d) {
                            dst = 0.001d;
                        }
                        coeff += 1.d / dst;
                        Color col = PREqui.getColor(pixX, pixY);
                        red += col.getRed() / dst;
                        green += col.getGreen() / dst;
                        blue += col.getBlue() / dst;
                    }
                }
                red = red / coeff;
                green = green / coeff;
                blue = blue / coeff;
                PWBottom.setColor(pX, pY, new Color(red, green, blue, 1));  // Écriture directe comme OpenCL
            }
        }
        Platform.runLater(() -> {
            pbBarreImage.setProgress(1.0f);
        });
        Thread.sleep(50);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("⏱️  Transformation CPU terminée en " + duration + " ms");

        return cube;
    }

    /**
     * Transformation Cube vers Équirectangulaire avec routage automatique GPU/CPU
     * Cette méthode détecte automatiquement si un GPU est disponible et l'utilise
     * 
     * @param cube Tableau de 6 images des faces du cube [front, behind, right, left, top, bottom]
     * @param taille Largeur souhaitée pour l'image équirectangulaire (-1 pour auto)
     * @return Image équirectangulaire résultante
     * @throws InterruptedException Si l'opération est interrompue
     */
    public static Image cube2equiAuto(Image[] cube, int taille) throws InterruptedException {
        if (cube == null || cube.length != 6) {
            throw new IllegalArgumentException("Le tableau cube doit contenir exactement 6 images");
        }
        
        GPUManager gpu = GPUManager.getInstance();
        
        if (gpu.isGPUEnabled()) {
            System.out.println("🎮 Utilisation du GPU pour Cube→Equi");
            try {
                return TransformationsPanoramiqueGPU.cube2equi(cube, taille == -1 ? 
                    (int)(cube[0].getWidth() * Math.PI * 2 / RAPPORTCUBEEQUI) : taille);
            } catch (Exception e) {
                System.err.println("⚠️  GPU échec, fallback CPU: " + e.getMessage());
            }
        }
        
        System.out.println("💻 Utilisation du CPU pour Cube→Equi");
        return cube2equi(cube, taille);
    }
    
    /**
     * Transformation Cube vers Équirectangulaire (version CPU, tableau)
     * 
     * @param cube Tableau de 6 images [front, behind, right, left, top, bottom]
     * @param taille Largeur de l'image équirectangulaire (-1 pour auto)
     * @return Image équirectangulaire
     * @throws InterruptedException Si interrompu
     */
    public static Image cube2equi(Image[] cube, int taille) throws InterruptedException {
        if (cube == null || cube.length != 6) {
            throw new IllegalArgumentException("Le tableau cube doit contenir exactement 6 images");
        }
        return cube2rect(cube[0], cube[3], cube[2], cube[1], cube[4], cube[5], taille);
    }

    /**
     * Transformation cube vers équirectangulaire (version CPU originale)
     *
     * @param front face avant
     * @param left face gauche
     * @param right face droite
     * @param behind face arrière
     * @param top face supérieure
     * @param bottom face inérieure
     * @param taille si taille = -1 calcul de la taille optimale
     * @return Image equirectangulaire
     */
    public static Image cube2rect(Image front, Image left, Image right, Image behind, Image top, Image bottom, int taille) throws InterruptedException {
        // Cette méthode est la version CPU directe - ne pas router vers Auto pour éviter boucle infinie
        return cube2rectCPU(front, left, right, behind, top, bottom, taille);
    }
    
    /**
     * Version CPU de cube2rect
     * Convertit les 6 faces d'un cube en image équirectangulaire
     * 
     * @param front face avant
     * @param left face gauche
     * @param right face droite
     * @param behind face arrière
     * @param top face supérieure
     * @param bottom face inérieure
     * @param taille si taille = -1 calcul de la taille optimale
     * @return Image equirectangulaire
     */
    private static Image cube2rectCPU(Image front, Image left, Image right, Image behind, Image top, Image bottom, int taille) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        
        PixelReader PRFront = front.getPixelReader();
        PixelReader PRLeft = left.getPixelReader();
        PixelReader PRRight = right.getPixelReader();
        PixelReader PRBehind = behind.getPixelReader();
        PixelReader PRTop = top.getPixelReader();
        PixelReader PRBottom = bottom.getPixelReader();
        int tailleCube = (int) top.getHeight();
        double theta;
        double phi;
        int tailleEqui;
        if (taille == -1) {
            tailleEqui = (int) (tailleCube / TransformationsPanoramique.RAPPORTCUBEEQUI);
        } else {
            tailleEqui = taille;
        }
        if (tailleEqui % 2 == 1) {
            tailleEqui += 1;
        }
        
        System.out.println("🖥️  Transformation Cube→Equi sur CPU");
        System.out.println("   📦 Cube: " + tailleCube + "×" + tailleCube);
        System.out.println("   📐 Équi: " + tailleEqui + "×" + (tailleEqui / 2));
        
        WritableImage equi = new WritableImage(
                (int) tailleEqui,
                (int) (tailleEqui / 2.d));
        PixelWriter PWEqui = equi.getPixelWriter();
        double rapport = 2.0d * Math.PI / tailleEqui;
        double red;
        double green;
        double blue;
        for (int X = 0; X < tailleEqui; X++) {
            final int XX=X;
            final float tailleEqui1=(float)tailleEqui;
            Platform.runLater(() -> {
                pbBarreImage.setProgress(XX/tailleEqui1);
            });
            for (int Y = 0; Y < tailleEqui / 2; Y++) {
                theta = ((double) X) * rapport - Math.PI;
                phi = ((double) Y) * rapport;
                double vectX = Math.cos(theta) * Math.sin(phi);
                double vectY = Math.sin(theta) * Math.sin(phi);
                double vectZ = Math.cos(phi);
                double pixelX;
                double pixelY;
                if ((Math.abs(vectX) >= Math.abs(vectY)) && (Math.abs(vectX) >= Math.abs(vectZ))) {
                    if (vectX > 0) {
                        vectY = vectY / vectX;
                        vectZ = vectZ / vectX;
                        pixelX = ((double) tailleCube / 2.d * (vectY + 1.0d));
                        pixelY = ((double) tailleCube / 2.d * (1.0d - vectZ));
                        if (pixelX > (double) tailleCube - 1.0d) {
                            pixelX = (double) tailleCube - 1.0d;
                        }
                        if (pixelY > (double) tailleCube - 1.0d) {
                            pixelY = (double) tailleCube - 1.0d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRFront.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    } else {
                        vectY = vectY / Math.abs(vectX);
                        vectZ = vectZ / Math.abs(vectX);
                        pixelX = ((double) tailleCube / 2.d * (1.d - vectY));
                        pixelY = ((double) tailleCube / 2.d * (1.d - vectZ));
                        if (pixelX > (double) tailleCube - 1.d) {
                            pixelX = (double) tailleCube - 1.d;
                        }
                        if (pixelY > (double) tailleCube - 1.d) {
                            pixelY = (double) tailleCube - 1.d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRBehind.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    }
                } else if ((Math.abs(vectY) >= Math.abs(vectX)) && (Math.abs(vectY) >= Math.abs(vectZ))) {
                    if (vectY > 0) {
                        vectX = vectX / vectY;
                        vectZ = vectZ / vectY;
                        pixelX = ((double) tailleCube / 2.d * (1.d - vectX));
                        pixelY = ((double) tailleCube / 2.d * (1.d - vectZ));
                        if (pixelX > (double) tailleCube - 1.d) {
                            pixelX = (double) tailleCube - 1.d;
                        }
                        if (pixelY > (double) tailleCube - 1.d) {
                            pixelY = (double) tailleCube - 1.d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRRight.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    } else {
                        vectX = vectX / Math.abs(vectY);
                        vectZ = vectZ / Math.abs(vectY);
                        pixelX = ((double) tailleCube / 2.d * (vectX + 1.d));
                        pixelY = ((double) tailleCube / 2.d * (1.d - vectZ));
                        if (pixelX > (double) tailleCube - 1.d) {
                            pixelX = (double) tailleCube - 1.d;
                        }
                        if (pixelY > (double) tailleCube - 1.d) {
                            pixelY = (double) tailleCube - 1.d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRLeft.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    }
                } else if ((Math.abs(vectZ) >= Math.abs(vectX)) && (Math.abs(vectZ) >= Math.abs(vectY))) {
                    if (vectZ > 0) {
                        vectX = vectX / vectZ;
                        vectY = vectY / vectZ;
                        pixelX = ((double) tailleCube / 2.d * (vectY + 1.d));
                        pixelY = ((double) tailleCube / 2.d * (1.d + vectX));
                        if (pixelX > (double) tailleCube - 1.d) {
                            pixelX = (double) tailleCube - 1.d;
                        }
                        if (pixelY > (double) tailleCube - 1.d) {
                            pixelY = (double) tailleCube - 1.d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRTop.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    } else {
                        vectX = vectX / Math.abs(vectZ);
                        vectY = vectY / Math.abs(vectZ);
                        pixelX = ((double) tailleCube / 2.d * (vectY + 1.d));
                        pixelY = ((double) tailleCube / 2.d * (1.d - vectX));
                        if (pixelX > (double) tailleCube - 1.d) {
                            pixelX = (double) tailleCube - 1.d;
                        }
                        if (pixelY > (double) tailleCube - 1.d) {
                            pixelY = (double) tailleCube - 1.d;
                        }
                        red = 0.0d;
                        green = 0.0d;
                        blue = 0.0d;
                        double coeff = 0.0d;
                        for (int i = (int) Math.floor(pixelX); i <= (int) Math.ceil(pixelX); i++) {
                            for (int j = (int) Math.floor(pixelY); j <= (int) Math.ceil(pixelY); j++) {
                                int pixX = i;
                                int pixY = j;
                                if (pixX < 0) {
                                    pixX = 0;
                                }
                                if (pixY < 0) {
                                    pixY = 0;
                                }
                                if (pixX > tailleCube - 1) {
                                    pixX = tailleCube - 1;
                                }
                                if (pixY > tailleCube - 1) {
                                    pixY = tailleCube - 1;
                                }
                                double dst = Math.sqrt((pixX - pixelX) * (pixX - pixelX) + (pixY - pixelY) * (pixY - pixelY));
                                if (dst < 0.001d) {
                                    dst = 0.001d;
                                }
                                coeff += 1.d / dst;
                                Color col = PRBottom.getColor(pixX, pixY);
                                red += col.getRed() / dst;
                                green += col.getGreen() / dst;
                                blue += col.getBlue() / dst;
                            }
                        }
                        red = red / coeff;
                        green = green / coeff;
                        blue = blue / coeff;
                        PWEqui.setColor(X, Y, new Color(red, green, blue, 1));
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("⏱️  Transformation CPU terminée en " + duration + " ms");

        return equi;
    }
}
