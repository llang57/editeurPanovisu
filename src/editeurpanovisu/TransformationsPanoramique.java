/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author llang
 */
public class TransformationsPanoramique {

    /**
     * Constante rapport de taille d'image 
     * transformation entre cube equi
     * Taille face de cube = RAPPORTCUBEEQUI*TailleEqui X RAPPORTCUBEEQUI*TailleEqui
     */
    public static final double RAPPORTCUBEEQUI = 0.3183;

    /**
     *
     * @param equi
     * @param taille
     * @return
     */
    public static Image[] equi2cube(Image equi, int taille) {
        PixelReader PREqui = equi.getPixelReader();
        WritableImage[] cube = new WritableImage[6];
        int tailleEqui = (int) equi.getWidth();
        int tailleCube;
        if (taille == -1) {
            tailleCube = (int) (tailleEqui * TransformationsPanoramique.RAPPORTCUBEEQUI);
        } else {
            tailleCube = taille;
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
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {
                double X = (2.d * (double) pX - tailleCube) / tailleCube;
                double Y = (2.d * (double) pY - tailleCube) / tailleCube;
                double Z = 1;
                if (X != 0) {
                    theta = Math.atan(X);
                } else {
                    theta = 0;
                }
                phi = Math.acos(-Y / Math.sqrt(X * X + Y * Y + Z * Z));
                pixelX = ((theta) * rapport + tailleEqui / 2.d);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pY = 0; pY < tailleCube; pY++) {

                double X = (2.d * pX - tailleCube) / tailleCube;
                double Y = (2.d * pY - tailleCube) / tailleCube;
                double Z = -1;
                if (X != 0) {
                    theta = Math.atan(-X);
                } else {
                    theta = 0;
                }
                phi = Math.acos(Y / Math.sqrt(X * X + Y * Y + Z * Z));
                theta = (theta + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                pixelX = ((theta) * rapport);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
                PWBehind.setColor(tailleCube - pX - 1, tailleCube - pY - 1, new Color(red, green, blue, 1));
//                PWBehind.setColor(tailleCube - pX - 1, tailleCube - pY - 1, PREqui.getColor(pixelX, pixelY));
            }
        }
        for (int pY = 0; pY < tailleCube; pY++) {
            for (int pZ = 0; pZ < tailleCube; pZ++) {

                double Z = (2.d * pZ - tailleCube) / tailleCube;
                double Y = (2.d * pY - tailleCube) / tailleCube;
                double X = 1;
                if (X != 0) {
                    theta = Math.atan(Y) + Math.PI / 2;
                } else {
                    theta = 0;
                }
                phi = Math.acos(Z / Math.sqrt(X * X + Y * Y + Z * Z));
                theta = (theta + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                pixelX = ((theta) * rapport);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
                PWLeft.setColor(pY, tailleCube - pZ - 1, new Color(red, green, blue, 1));
//                PWLeft.setColor(pY, tailleCube - pZ - 1, PREqui.getColor(pixelX, pixelY));
            }
        }
        for (int pY = 0; pY < tailleCube; pY++) {
            for (int pZ = 0; pZ < tailleCube; pZ++) {

                double Z = (2.d * pZ - tailleCube) / tailleCube;
                double Y = (2.d * pY - tailleCube) / tailleCube;
                double X = 1;
                if (X != 0) {
                    theta = Math.atan(Y) - Math.PI / 2;
                } else {
                    theta = 0;
                }
                phi = Math.acos(Z / Math.sqrt(X * X + Y * Y + Z * Z));
                theta = (theta + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                pixelX = ((theta) * rapport);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
                PWRight.setColor(pY, tailleCube - pZ - 1, new Color(red, green, blue, 1));
//                PWRight.setColor(pY, tailleCube - pZ - 1, PREqui.getColor(pixelX, pixelY));
            }
        }
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pZ = 0; pZ < tailleCube; pZ++) {

                double Z = (2.d * pZ - tailleCube) / tailleCube;
                double X = (2.d * pX - tailleCube) / tailleCube;
                double Y = 1;
                if (X != 0) {
                    theta = Math.atan(Z / X);
                } else {
                    theta = 0;
                }
                if (X < 0) {
                    theta = theta - Math.PI;
                }
                phi = Math.acos(Y / Math.sqrt(X * X + Y * Y + Z * Z));
                theta = (theta + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                pixelX = ((theta) * rapport);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
                PWTop.setColor(tailleCube - pZ - 1, tailleCube - pX - 1, new Color(red, green, blue, 1));
//                PWTop.setColor(tailleCube - pZ - 1, tailleCube - pX - 1, PREqui.getColor(pixelX, pixelY));
            }
        }
        for (int pX = 0; pX < tailleCube; pX++) {
            for (int pZ = 0; pZ < tailleCube; pZ++) {

                double Z = (2.d * pZ - tailleCube) / tailleCube;
                double X = (2.d * pX - tailleCube) / tailleCube;
                double Y = -1;
                if (X != 0) {
                    theta = Math.atan(Z / X);
                } else {
                    theta = 0;
                }
                if (X < 0) {
                    theta = theta - Math.PI;
                }
                phi = Math.acos(Y / Math.sqrt(X * X + Y * Y + Z * Z));
                theta = (theta + deuxPI) % deuxPI;
                phi = (phi + Math.PI) % Math.PI;
                pixelX = ((theta) * rapport);
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
                        if (pixY > tailleEqui / 2 - 1) {
                            pixY = tailleEqui / 2 - 1;
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
                PWBottom.setColor(tailleCube - pZ - 1, pX, new Color(red, green, blue, 1));
//                PWBottom.setColor(tailleCube - pZ - 1, pX, PREqui.getColor(pixelX, pixelY));
            }
        }

        return cube;
    }

    /**
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
    public static Image cube2rect(Image front, Image left, Image right, Image behind, Image top, Image bottom, int taille) {
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
        WritableImage equi = new WritableImage(
                (int) tailleEqui,
                (int) (tailleEqui / 2.d));
        PixelWriter PWEqui = equi.getPixelWriter();
        double rapport = 2.0d * Math.PI / tailleEqui;
        double red;
        double green;
        double blue;
        for (int X = 0; X < tailleEqui; X++) {
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

        return equi;
    }
}
