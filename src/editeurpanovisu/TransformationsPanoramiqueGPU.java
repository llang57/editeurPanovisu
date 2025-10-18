/*
 * Transformations panoramiques accélérées par GPU via OpenCL
 * Implémentation des conversions Équirectangulaire ↔ Cube sur GPU
 */
package editeurpanovisu;

import editeurpanovisu.gpu.GPUManager;
import editeurpanovisu.gpu.GPUKernelLoader;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.jocl.*;
import static org.jocl.CL.*;

import java.nio.ByteBuffer;

/**
 * Classe pour les transformations panoramiques accélérées par GPU
 * Utilise OpenCL pour des performances jusqu'à 50x plus rapides
 * 
 * @author PanoVisu Team
 */
public class TransformationsPanoramiqueGPU {
    
    private static cl_program equi2cubeProgram = null;
    private static cl_program cube2equiProgram = null;
    private static cl_kernel equi2cubeKernel = null;
    private static cl_kernel cube2equiKernel = null;
    
    /**
     * Initialise les programmes et kernels OpenCL
     * Doit être appelé une seule fois au démarrage
     */
    private static synchronized void initializeKernels() {
        if (equi2cubeKernel != null && cube2equiKernel != null) {
            return; // Déjà initialisés
        }
        
        GPUManager gpu = GPUManager.getInstance();
        if (!gpu.isGPUAvailable()) {
            throw new RuntimeException("GPU non disponible");
        }
        
        try {
            // Charger et compiler le kernel equi2cube
            String equi2cubeSource = GPUKernelLoader.loadEqui2CubeKernel();
            equi2cubeProgram = clCreateProgramWithSource(
                gpu.getContext(),
                1,
                new String[]{equi2cubeSource},
                null,
                null
            );
            
            clBuildProgram(equi2cubeProgram, 0, null, null, null, null);
            equi2cubeKernel = clCreateKernel(equi2cubeProgram, "equi2cube_face", null);
            
            // Charger et compiler le kernel cube2equi
            String cube2equiSource = GPUKernelLoader.loadCube2EquiKernel();
            cube2equiProgram = clCreateProgramWithSource(
                gpu.getContext(),
                1,
                new String[]{cube2equiSource},
                null,
                null
            );
            
            clBuildProgram(cube2equiProgram, 0, null, null, null, null);
            cube2equiKernel = clCreateKernel(cube2equiProgram, "cube2equi", null);
            
            System.out.println("✅ Kernels GPU initialisés avec succès");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'initialisation des kernels: " + e.getMessage());
            throw new RuntimeException("Échec de l'initialisation des kernels GPU", e);
        }
    }
    
    /**
     * Convertit une image JavaFX en buffer RGBA pour OpenCL
     */
    private static ByteBuffer imageToByteBuffer(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        PixelReader reader = image.getPixelReader();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                buffer.put((byte) (color.getRed() * 255));
                buffer.put((byte) (color.getGreen() * 255));
                buffer.put((byte) (color.getBlue() * 255));
                buffer.put((byte) (color.getOpacity() * 255));
            }
        }
        
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Convertit un buffer RGBA OpenCL en WritableImage JavaFX
     */
    private static WritableImage byteBufferToImage(ByteBuffer buffer, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();
        
        buffer.rewind();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = buffer.get() & 0xFF;
                int g = buffer.get() & 0xFF;
                int b = buffer.get() & 0xFF;
                int a = buffer.get() & 0xFF;
                
                Color color = Color.rgb(r, g, b, a / 255.0);
                writer.setColor(x, y, color);
            }
        }
        
        return image;
    }
    
    /**
     * Transformation Équirectangulaire vers Cube accélérée par GPU
     * 
     * @param equi Image équirectangulaire source
     * @param taille Taille souhaitée pour les faces du cube (-1 pour auto)
     * @return Tableau de 6 images représentant les faces du cube
     * @throws InterruptedException Si l'opération est interrompue
     */
    public static Image[] equi2cube(Image equi, int taille) throws InterruptedException {
        GPUManager gpu = GPUManager.getInstance();
        
        if (!gpu.isGPUEnabled()) {
            // Fallback CPU
            return TransformationsPanoramique.equi2cube(equi, taille);
        }
        
        try {
            initializeKernels();
            
            int tailleEqui = (int) equi.getWidth();
            int hauteurEqui = (int) equi.getHeight();
            int tailleCube = (taille == -1) ? 
                (int) (tailleEqui * TransformationsPanoramique.RAPPORTCUBEEQUI) : taille;
            
            System.out.println("🎮 Transformation Equi→Cube sur GPU");
            System.out.println("   📐 Équi: " + tailleEqui + "x" + hauteurEqui);
            System.out.println("   📦 Cube: " + tailleCube + "x" + tailleCube);
            
            long startTime = System.currentTimeMillis();
            
            // Convertir l'image source en buffer
            ByteBuffer equiBuffer = imageToByteBuffer(equi);
            
            // Créer les buffers OpenCL
            cl_mem equiMem = clCreateBuffer(
                gpu.getContext(),
                CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_uchar4 * tailleEqui * hauteurEqui,
                Pointer.to(equiBuffer),
                null
            );
            
            cl_mem cubeMem = clCreateBuffer(
                gpu.getContext(),
                CL_MEM_WRITE_ONLY,
                Sizeof.cl_uchar4 * tailleCube * tailleCube,
                null,
                null
            );
            
            // Préparer les résultats
            Image[] cubeImages = new Image[6];
            ByteBuffer cubeBuffer = ByteBuffer.allocateDirect(tailleCube * tailleCube * 4);
            
            // Traiter chaque face
            for (int face = 0; face < 6; face++) {
                // Configurer les arguments du kernel
                clSetKernelArg(equi2cubeKernel, 0, Sizeof.cl_mem, Pointer.to(equiMem));
                clSetKernelArg(equi2cubeKernel, 1, Sizeof.cl_mem, Pointer.to(cubeMem));
                clSetKernelArg(equi2cubeKernel, 2, Sizeof.cl_int, Pointer.to(new int[]{tailleEqui}));
                clSetKernelArg(equi2cubeKernel, 3, Sizeof.cl_int, Pointer.to(new int[]{hauteurEqui}));
                clSetKernelArg(equi2cubeKernel, 4, Sizeof.cl_int, Pointer.to(new int[]{tailleCube}));
                clSetKernelArg(equi2cubeKernel, 5, Sizeof.cl_int, Pointer.to(new int[]{face}));
                
                // Exécuter le kernel
                long[] globalWorkSize = new long[]{tailleCube, tailleCube};
                clEnqueueNDRangeKernel(
                    gpu.getCommandQueue(),
                    equi2cubeKernel,
                    2,
                    null,
                    globalWorkSize,
                    null,
                    0,
                    null,
                    null
                );
                
                // Lire le résultat
                cubeBuffer.rewind();
                clEnqueueReadBuffer(
                    gpu.getCommandQueue(),
                    cubeMem,
                    CL_TRUE,
                    0,
                    tailleCube * tailleCube * 4,
                    Pointer.to(cubeBuffer),
                    0,
                    null,
                    null
                );
                
                // Convertir en Image
                cubeImages[face] = byteBufferToImage(cubeBuffer, tailleCube, tailleCube);
                
                // Mettre à jour la progression
                final int currentFace = face;
                Platform.runLater(() -> {
                    if (EquiCubeDialogController.pbBarreImage != null) {
                        EquiCubeDialogController.pbBarreImage.setProgress((currentFace + 1) / 6.0f);
                    }
                });
                
                Thread.sleep(10);
            }
            
            // Libérer les ressources OpenCL
            clReleaseMemObject(equiMem);
            clReleaseMemObject(cubeMem);
            
            long endTime = System.currentTimeMillis();
            System.out.println("✅ Transformation GPU terminée en " + (endTime - startTime) + " ms");
            
            return cubeImages;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur GPU, fallback CPU: " + e.getMessage());
            e.printStackTrace();
            // Fallback vers CPU en cas d'erreur
            return TransformationsPanoramique.equi2cube(equi, taille);
        }
    }
    
    /**
     * Transformation Cube vers Équirectangulaire accélérée par GPU
     * 
     * @param cube Tableau de 6 images des faces du cube
     * @param tailleEqui Largeur souhaitée pour l'image équirectangulaire
     * @return Image équirectangulaire résultante
     * @throws InterruptedException Si l'opération est interrompue
     */
    public static Image cube2equi(Image[] cube, int tailleEqui) throws InterruptedException {
        GPUManager gpu = GPUManager.getInstance();
        
        if (!gpu.isGPUEnabled() || cube.length != 6) {
            // Fallback CPU
            return TransformationsPanoramique.cube2equi(cube, tailleEqui);
        }
        
        try {
            initializeKernels();
            
            int tailleCube = (int) cube[0].getWidth();
            int hauteurEqui = tailleEqui / 2;
            
            System.out.println("🎮 Transformation Cube→Equi sur GPU");
            System.out.println("   📦 Cube: " + tailleCube + "x" + tailleCube);
            System.out.println("   📐 Équi: " + tailleEqui + "x" + hauteurEqui);
            
            long startTime = System.currentTimeMillis();
            
            // Convertir les 6 faces en un seul buffer
            ByteBuffer cubeBuffer = ByteBuffer.allocateDirect(6 * tailleCube * tailleCube * 4);
            for (int i = 0; i < 6; i++) {
                ByteBuffer faceBuffer = imageToByteBuffer(cube[i]);
                cubeBuffer.put(faceBuffer);
            }
            cubeBuffer.rewind();
            
            // Créer les buffers OpenCL
            cl_mem cubeMem = clCreateBuffer(
                gpu.getContext(),
                CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_uchar4 * 6 * tailleCube * tailleCube,
                Pointer.to(cubeBuffer),
                null
            );
            
            cl_mem equiMem = clCreateBuffer(
                gpu.getContext(),
                CL_MEM_WRITE_ONLY,
                Sizeof.cl_uchar4 * tailleEqui * hauteurEqui,
                null,
                null
            );
            
            // Configurer les arguments du kernel
            clSetKernelArg(cube2equiKernel, 0, Sizeof.cl_mem, Pointer.to(cubeMem));
            clSetKernelArg(cube2equiKernel, 1, Sizeof.cl_mem, Pointer.to(equiMem));
            clSetKernelArg(cube2equiKernel, 2, Sizeof.cl_int, Pointer.to(new int[]{tailleCube}));
            clSetKernelArg(cube2equiKernel, 3, Sizeof.cl_int, Pointer.to(new int[]{tailleEqui}));
            clSetKernelArg(cube2equiKernel, 4, Sizeof.cl_int, Pointer.to(new int[]{hauteurEqui}));
            
            // Exécuter le kernel
            long[] globalWorkSize = new long[]{tailleEqui, hauteurEqui};
            clEnqueueNDRangeKernel(
                gpu.getCommandQueue(),
                cube2equiKernel,
                2,
                null,
                globalWorkSize,
                null,
                0,
                null,
                null
            );
            
            // Lire le résultat
            ByteBuffer equiBuffer = ByteBuffer.allocateDirect(tailleEqui * hauteurEqui * 4);
            clEnqueueReadBuffer(
                gpu.getCommandQueue(),
                equiMem,
                CL_TRUE,
                0,
                tailleEqui * hauteurEqui * 4,
                Pointer.to(equiBuffer),
                0,
                null,
                null
            );
            
            // Convertir en Image
            Image result = byteBufferToImage(equiBuffer, tailleEqui, hauteurEqui);
            
            // Libérer les ressources OpenCL
            clReleaseMemObject(cubeMem);
            clReleaseMemObject(equiMem);
            
            long endTime = System.currentTimeMillis();
            System.out.println("✅ Transformation GPU terminée en " + (endTime - startTime) + " ms");
            
            Platform.runLater(() -> {
                if (EquiCubeDialogController.pbBarreImage != null) {
                    EquiCubeDialogController.pbBarreImage.setProgress(1.0f);
                }
            });
            
            return result;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur GPU, fallback CPU: " + e.getMessage());
            e.printStackTrace();
            // Fallback vers CPU en cas d'erreur
            return TransformationsPanoramique.cube2equi(cube, tailleEqui);
        }
    }
    
    /**
     * Libère les ressources GPU
     */
    public static void cleanup() {
        try {
            if (equi2cubeKernel != null) {
                clReleaseKernel(equi2cubeKernel);
                equi2cubeKernel = null;
            }
            if (equi2cubeProgram != null) {
                clReleaseProgram(equi2cubeProgram);
                equi2cubeProgram = null;
            }
            if (cube2equiKernel != null) {
                clReleaseKernel(cube2equiKernel);
                cube2equiKernel = null;
            }
            if (cube2equiProgram != null) {
                clReleaseProgram(cube2equiProgram);
                cube2equiProgram = null;
            }
            System.out.println("✅ Kernels GPU libérés");
        } catch (Exception e) {
            System.err.println("⚠️  Erreur lors du nettoyage des kernels: " + e.getMessage());
        }
    }
}
