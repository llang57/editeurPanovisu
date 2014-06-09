/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

/**
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
            0.0f, -1.0f, 0.0f,
            -1.0f, 4.0f, -1.0f,
            0.0f, -1.0f, 0.0f
        };
        float[] sharpMatrix = new float[9];
        for (int i = 0; i < 9; i++) {
            sharpMatrix[i] = normalMatrix[i] + level * edgeMatrix[i];
        }
        return sharpMatrix;
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
        sharpenMatrix = ReadWriteImage.calculeSharpenMatrix(sharpenLevel);
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
        sharpenMatrix = ReadWriteImage.calculeSharpenMatrix(sharpenLevel);
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

}
