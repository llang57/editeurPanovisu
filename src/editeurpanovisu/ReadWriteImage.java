/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.apache.commons.imaging.common.BufferedImageFactory;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;

/**
 * fonctions de sauvegarde des images
 *
 * @author llang
 */
public class ReadWriteImage {

    private static float[] sharpenMatrix = new float[9];

    /**
     *
     * @param level
     * @return
     */
    private static float[] calculeSharpenMatrix(float level) {
        float[] normalMatrix = {
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f
        };
        float[] edgeMatrix = {
             0.0f, -1.0f,  0.0f,
            -1.0f,  4.0f, -1.0f,
             0.0f, -1.0f,  0.0f
        };        
        float[] sharpMatrix = new float[9];
        for (int i = 0; i < 9; i++) {
            sharpMatrix[i] = normalMatrix[i] + level * edgeMatrix[i];
        }
        return sharpMatrix;
    }

    public static class ManagedImageBufferedImageFactory implements
            BufferedImageFactory {

        @Override
        public BufferedImage getColorBufferedImage(final int iLargeur, final int iHauteur,
                final boolean bAasAlpha) {
            final GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            final GraphicsDevice gd = ge.getDefaultScreenDevice();
            final GraphicsConfiguration gc = gd.getDefaultConfiguration();
            return gc.createCompatibleImage(iLargeur, iHauteur,
                    Transparency.TRANSLUCENT);
        }

        @Override
        public BufferedImage getGrayscaleBufferedImage(final int width, final int height,
                final boolean hasAlpha) {
            return getColorBufferedImage(width, height, hasAlpha);
        }
    }

    public static Image readTiff(String strNomFich)
            throws ImagingException, IOException {
        File file = new File(strNomFich);
        
        // La nouvelle API ne nécessite plus de paramètres spécifiques
        final BufferedImage img = Imaging.getBufferedImage(file);

        Image image = SwingFXUtils.toFXImage(img, null);

        return image;
    }

    public static void writeTiff(Image imgImage, String strNomFich, boolean bSharpen, float sharpenLevel)
            throws ImagingException, IOException {
        File file = new File(strNomFich);
        BufferedImage imageRGBSharpen = null;
        BufferedImage imageRGB = SwingFXUtils.fromFXImage(imgImage, null);

        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(imageRGB, 0, 0, null);
        if (bSharpen) {
            imageRGBSharpen = new BufferedImage(imageRGB.getWidth(), imageRGB.getHeight(), BufferedImage.TYPE_INT_RGB);
            Kernel kernel = new Kernel(3, 3, sharpenMatrix);
            ConvolveOp cop = new ConvolveOp(kernel,
                    ConvolveOp.EDGE_NO_OP,
                    null);
            cop.filter(imageRGB, imageRGBSharpen);
        }

        final ImageFormat format = ImageFormats.TIFF;
        // La nouvelle API ne supporte plus les paramètres de compression personnalisés
        // dans writeImage - utilise des valeurs par défaut
       
        if (bSharpen) {
            try {
                Imaging.writeImage(imageRGBSharpen, file, format);
            } catch (ImagingException ex) {
                Logger.getLogger(ReadWriteImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Imaging.writeImage(imageRGB, file, format);
            } catch (ImagingException ex) {
                Logger.getLogger(ReadWriteImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static Image resizeImage(Image img, int newW, int newH) {
        BufferedImage image = SwingFXUtils.fromFXImage(img, null);

        try {
            BufferedImage imgRetour = Thumbnails.of(image).size(newW, newH).asBufferedImage();
            return SwingFXUtils.toFXImage(imgRetour, null);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param img
     * @param destFile
     * @param quality
     * @param sharpen
     * @param sharpenLevel
     * @throws IOException
     */
    public static void writeJpeg(Image img, String destFile, float quality, boolean sharpen, float sharpenLevel)
            throws IOException {
        sharpenMatrix = calculeSharpenMatrix(sharpenLevel);
        BufferedImage imageRGBSharpen = null;
        IIOImage iioImage = null;
        BufferedImage image = SwingFXUtils.fromFXImage(img, null); // Get buffered image.

        BufferedImage imageRGB = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.OPAQUE); // Remove alpha-channel from buffered image.

        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        if (sharpen) {
            imageRGBSharpen = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Kernel kernel = new Kernel(3, 3, sharpenMatrix);
            ConvolveOp cop = new ConvolveOp(kernel,
                    ConvolveOp.EDGE_NO_OP,
                    null);
            cop.filter(imageRGB, imageRGBSharpen);
        }
        ImageWriter writer = null;
        FileImageOutputStream output = null;
        try {
            writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            output = new FileImageOutputStream(new File(destFile));
            writer.setOutput(output);
            if (sharpen) {
                iioImage = new IIOImage(imageRGBSharpen, null, null);
            } else {
                iioImage = new IIOImage(imageRGB, null, null);
            }
            writer.write(null, iioImage, param);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (output != null) {
                output.close();
            }
        }
        graphics.dispose();
    }

    /**
     *
     * @param img
     * @param destFile
     * @param sharpen
     * @param sharpenLevel
     * @throws IOException
     */
    public static void writeBMP(Image img, String destFile, boolean sharpen, float sharpenLevel)
            throws IOException {
        sharpenMatrix = calculeSharpenMatrix(sharpenLevel);
        BufferedImage imageRGBSharpen = null;
        IIOImage iioImage = null;

        BufferedImage image = SwingFXUtils.fromFXImage(img, null); // Get buffered image.
        BufferedImage imageRGB = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.OPAQUE); // Remove alpha-channel from buffered image.

        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        if (sharpen) {

            imageRGBSharpen = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Kernel kernel = new Kernel(3, 3, sharpenMatrix);
            ConvolveOp cop = new ConvolveOp(kernel,
                    ConvolveOp.EDGE_NO_OP,
                    null);
            cop.filter(imageRGB, imageRGBSharpen);
        }
        ImageWriter writer = null;
        FileImageOutputStream output = null;
        try {
            writer = ImageIO.getImageWritersByFormatName("bmp").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            output = new FileImageOutputStream(new File(destFile));
            writer.setOutput(output);
            if (sharpen) {
                iioImage = new IIOImage(imageRGBSharpen, null, null);
            } else {
                iioImage = new IIOImage(imageRGB, null, null);
            }
            writer.write(null, iioImage, param);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (output != null) {
                output.close();
            }
        }
        graphics.dispose();
    }

    /**
     *
     * @param img
     * @param destFile
     * @param sharpen
     * @param sharpenLevel
     * @throws IOException
     */
    public static void writePng(Image img, String destFile, boolean sharpen, float sharpenLevel)
            throws IOException {
        sharpenMatrix = calculeSharpenMatrix(sharpenLevel);
        BufferedImage imageRGBSharpen = null;
        IIOImage iioImage = null;
        BufferedImage image = SwingFXUtils.fromFXImage(img, null); // Get buffered image.
        BufferedImage imageRGB = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.BITMASK);
        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        if (sharpen) {
            imageRGBSharpen = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Kernel kernel = new Kernel(3, 3, sharpenMatrix);
            ConvolveOp cop = new ConvolveOp(kernel,
                    ConvolveOp.EDGE_NO_OP,
                    null);
            cop.filter(imageRGB, imageRGBSharpen);
        }
        ImageWriter writer = null;
        FileImageOutputStream output = null;
        try {
            writer = ImageIO.getImageWritersByFormatName("png").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            output = new FileImageOutputStream(new File(destFile));
            writer.setOutput(output);
            if (sharpen) {
                iioImage = new IIOImage(imageRGBSharpen, null, null);
            } else {
                iioImage = new IIOImage(imageRGB, null, null);
            }
            writer.write(null, iioImage, param);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (output != null) {
                output.close();
            }
        }
        graphics.dispose();
    }

}
