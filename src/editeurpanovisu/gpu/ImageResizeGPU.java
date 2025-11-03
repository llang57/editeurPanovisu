package editeurpanovisu.gpu;

import static org.jocl.CL.*;
import org.jocl.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Classe pour le redimensionnement d'images accéléré par GPU via OpenCL.
 * 
 * Supporte plusieurs méthodes d'interpolation :
 * - Bicubique (Catmull-Rom) : Recommandé pour la plupart des cas
 * - Lanczos3 : Meilleure qualité pour agrandissements critiques
 * 
 * Utilise le GPUManager pour la gestion du contexte OpenCL et effectue
 * automatiquement un fallback CPU si le GPU n'est pas disponible.
 * 
 * @author PanoVisu Team
 * @version 1.0
 */
public class ImageResizeGPU {
    
    private static cl_program bicubicProgram = null;
    private static cl_program lanczosProgram = null;
    private static cl_kernel bicubicKernel = null;
    private static cl_kernel lanczosKernel = null;
    
    /**
     * Seuil de taille d'image pour utiliser le GPU (en mégapixels)
     * En dessous de ce seuil, le CPU est plus rapide à cause de l'overhead GPU
     */
    private static final int GPU_THRESHOLD_MEGAPIXELS = 2; // 2MP = 1920×1080 environ
    
    /**
     * Redimensionne une image avec routage automatique GPU/CPU
     * 
     * @param source Image source
     * @param targetWidth Largeur cible
     * @param targetHeight Hauteur cible
     * @param method Méthode d'interpolation
     * @return Image redimensionnée
     * @throws InterruptedException Si l'opération est interrompue
     */
    public static Image resizeAuto(Image source, int targetWidth, int targetHeight, 
                                   InterpolationMethod method) throws InterruptedException {
        if (source == null) {
            throw new IllegalArgumentException("L'image source ne peut pas être null");
        }
        
        int srcWidth = (int) source.getWidth();
        int srcHeight = (int) source.getHeight();
        
        // Si l'image est déjà à la bonne taille
        if (srcWidth == targetWidth && srcHeight == targetHeight) {
            return source;
        }
        
        // Calcul du nombre de mégapixels
        double megapixels = (srcWidth * srcHeight) / 1_000_000.0;
        
        GPUManager gpu = GPUManager.getInstance();
        
        // Décision GPU vs CPU
        boolean useGPU = gpu.isGPUEnabled() && 
                        megapixels >= GPU_THRESHOLD_MEGAPIXELS &&
                        (method == InterpolationMethod.BICUBIC || method == InterpolationMethod.LANCZOS3);
        
        if (useGPU) {
            System.out.println("🎮 Redimensionnement " + method.getLocalizedName() + " sur GPU");
            System.out.println("   📐 Source: " + srcWidth + "×" + srcHeight + 
                             " (" + String.format("%.1f", megapixels) + " MP)");
            System.out.println("   📐 Cible: " + targetWidth + "×" + targetHeight);
            
            double scaleFactor = (double)targetWidth / srcWidth;
            System.out.println("   📊 Facteur: " + String.format("%.2f", scaleFactor) + "× " + 
                             (scaleFactor > 1.0 ? "agrandissement" : "réduction"));
            
            try {
                long startTime = System.currentTimeMillis();
                Image result = resizeGPU(source, targetWidth, targetHeight, method);
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("⏱️  Redimensionnement GPU terminé en " + duration + " ms");
                return result;
            } catch (Exception e) {
                System.err.println("⚠️  GPU échec, fallback CPU: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Fallback CPU
        System.out.println("💻 Redimensionnement " + method.getLocalizedName() + " sur CPU");
        System.out.println("   📐 Source: " + srcWidth + "×" + srcHeight);
        System.out.println("   📐 Cible: " + targetWidth + "×" + targetHeight);
        
        long startTime = System.currentTimeMillis();
        Image result = resizeCPU(source, targetWidth, targetHeight, method);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("⏱️  Redimensionnement CPU terminé en " + duration + " ms");
        
        return result;
    }
    
    /**
     * Redimensionnement via GPU OpenCL
     */
    private static Image resizeGPU(Image source, int targetWidth, int targetHeight, 
                                   InterpolationMethod method) throws Exception {
        GPUManager gpu = GPUManager.getInstance();
        
        if (!gpu.isGPUAvailable()) {
            throw new RuntimeException("GPU non disponible");
        }
        
        // Initialisation des kernels si nécessaire
        initializeKernels();
        
        int srcWidth = (int) source.getWidth();
        int srcHeight = (int) source.getHeight();
        
        // Conversion Image JavaFX → ByteBuffer
        ByteBuffer srcBuffer = imageToByteBuffer(source);
        
        // Allocation du buffer de sortie
        ByteBuffer dstBuffer = ByteBuffer.allocateDirect(targetWidth * targetHeight * 4);
        
        // Création des buffers OpenCL
        cl_mem srcMem = clCreateBuffer(
            gpu.getContext(),
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_uchar4 * srcWidth * srcHeight,
            Pointer.to(srcBuffer),
            null
        );
        
        cl_mem dstMem = clCreateBuffer(
            gpu.getContext(),
            CL_MEM_WRITE_ONLY,
            Sizeof.cl_uchar4 * targetWidth * targetHeight,
            null,
            null
        );
        
        // Sélection du kernel selon la méthode
        cl_kernel kernel;
        if (method == InterpolationMethod.LANCZOS3) {
            kernel = lanczosKernel;
        } else {
            kernel = bicubicKernel; // Par défaut bicubique
        }
        
        // Configuration des arguments du kernel
        clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(srcMem));
        clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(dstMem));
        clSetKernelArg(kernel, 2, Sizeof.cl_int, Pointer.to(new int[]{srcWidth}));
        clSetKernelArg(kernel, 3, Sizeof.cl_int, Pointer.to(new int[]{srcHeight}));
        clSetKernelArg(kernel, 4, Sizeof.cl_int, Pointer.to(new int[]{targetWidth}));
        clSetKernelArg(kernel, 5, Sizeof.cl_int, Pointer.to(new int[]{targetHeight}));
        
        // Calcul des dimensions du travail
        long[] globalWorkSize = new long[]{targetWidth, targetHeight};
        
        // Détermination automatique du local work size
        long[] localWorkSize = calculateOptimalLocalWorkSize(targetWidth, targetHeight);
        
        // Exécution du kernel
        clEnqueueNDRangeKernel(
            gpu.getCommandQueue(),
            kernel,
            2,
            null,
            globalWorkSize,
            localWorkSize,
            0,
            null,
            null
        );
        
        // Lecture du résultat
        clEnqueueReadBuffer(
            gpu.getCommandQueue(),
            dstMem,
            CL_TRUE,
            0,
            targetWidth * targetHeight * 4,
            Pointer.to(dstBuffer),
            0,
            null,
            null
        );
        
        // Nettoyage des ressources OpenCL
        clReleaseMemObject(srcMem);
        clReleaseMemObject(dstMem);
        
        // Conversion ByteBuffer → Image JavaFX
        return byteBufferToImage(dstBuffer, targetWidth, targetHeight);
    }
    
    /**
     * Redimensionnement via CPU (fallback)
     * Utilise la bibliothèque Thumbnails directement pour éviter la récursion
     */
    private static Image resizeCPU(Image source, int targetWidth, int targetHeight, 
                                   InterpolationMethod method) {
        try {
            // Convertir JavaFX Image en BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(source, null);
            
            // Utiliser Thumbnails directement (pas via ReadWriteImage pour éviter la récursion)
            BufferedImage resized = net.coobird.thumbnailator.Thumbnails.of(bufferedImage)
                .size(targetWidth, targetHeight)
                .asBufferedImage();
            
            // Convertir BufferedImage en JavaFX Image
            return SwingFXUtils.toFXImage(resized, null);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du redimensionnement CPU : " + e.getMessage());
            e.printStackTrace();
            // En dernier recours, retourner l'image source
            return source;
        }
    }
    
    /**
     * Initialise les kernels OpenCL (compilation à la demande)
     */
    private static void initializeKernels() throws Exception {
        GPUManager gpu = GPUManager.getInstance();
        
        // Compilation du kernel bicubique si nécessaire
        if (bicubicProgram == null) {
            String bicubicSource = GPUKernelLoader.loadBicubicKernel();
            bicubicProgram = clCreateProgramWithSource(
                gpu.getContext(),
                1,
                new String[]{bicubicSource},
                null,
                null
            );
            
            // Options de compilation pour éviter les problèmes avec clc.h
            String buildOptions = "-cl-std=CL1.2";
            int ret = clBuildProgram(bicubicProgram, 0, null, buildOptions, null, null);
            if (ret != CL_SUCCESS) {
                long[] logSize = new long[1];
                clGetProgramBuildInfo(bicubicProgram, gpu.getDevice(), 
                    CL_PROGRAM_BUILD_LOG, 0, null, logSize);
                byte[] log = new byte[(int) logSize[0]];
                clGetProgramBuildInfo(bicubicProgram, gpu.getDevice(), 
                    CL_PROGRAM_BUILD_LOG, logSize[0], Pointer.to(log), null);
                throw new RuntimeException("Erreur compilation kernel bicubique:\n" + new String(log));
            }
            
            bicubicKernel = clCreateKernel(bicubicProgram, "resize_bicubic", null);
            System.out.println("[GPU] ✅ Kernel Bicubique compilé");
        }
        
        // Compilation du kernel Lanczos si nécessaire
        if (lanczosProgram == null) {
            String lanczosSource = GPUKernelLoader.loadLanczosKernel();
            lanczosProgram = clCreateProgramWithSource(
                gpu.getContext(),
                1,
                new String[]{lanczosSource},
                null,
                null
            );
            
            // Options de compilation pour éviter les problèmes avec clc.h
            String buildOptions = "-cl-std=CL1.2";
            int ret = clBuildProgram(lanczosProgram, 0, null, buildOptions, null, null);
            if (ret != CL_SUCCESS) {
                long[] logSize = new long[1];
                clGetProgramBuildInfo(lanczosProgram, gpu.getDevice(), 
                    CL_PROGRAM_BUILD_LOG, 0, null, logSize);
                byte[] log = new byte[(int) logSize[0]];
                clGetProgramBuildInfo(lanczosProgram, gpu.getDevice(), 
                    CL_PROGRAM_BUILD_LOG, logSize[0], Pointer.to(log), null);
                throw new RuntimeException("Erreur compilation kernel Lanczos:\n" + new String(log));
            }
            
            lanczosKernel = clCreateKernel(lanczosProgram, "resize_lanczos3", null);
            System.out.println("[GPU] ✅ Kernel Lanczos3 compilé");
        }
    }
    
    /**
     * Calcule la taille optimale du local work size pour le GPU
     * Pour les très grandes images, retourne null (laisse OpenCL décider)
     */
    private static long[] calculateOptimalLocalWorkSize(int width, int height) {
        GPUManager gpu = GPUManager.getInstance();
        long maxWorkGroupSize = gpu.getMaxWorkGroupSize();
        
        // Pour les très grandes images (> 16K), laisser OpenCL décider
        if (width > 16384 || height > 16384) {
            return null;
        }
        
        // Tailles typiques : 16×16 = 256 ou 8×8 = 64
        int localX = 16;
        int localY = 16;
        
        // Ajustement si nécessaire
        if (localX * localY > maxWorkGroupSize) {
            localX = 8;
            localY = 8;
        }
        
        // Vérifier que les dimensions sont des multiples du local size
        // Sinon, utiliser des tailles plus petites ou null
        if (width % localX != 0 || height % localY != 0) {
            // Essayer 8×8
            if (width % 8 == 0 && height % 8 == 0) {
                return new long[]{8, 8};
            }
            // Sinon laisser OpenCL décider
            return null;
        }
        
        return new long[]{localX, localY};
    }

    
    /**
     * Convertit une Image JavaFX en ByteBuffer pour OpenCL (format RGBA)
     */
    private static ByteBuffer imageToByteBuffer(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        PixelReader reader = image.getPixelReader();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = reader.getArgb(x, y);
                
                // Extraction des composantes ARGB
                int alpha = (argb >> 24) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;
                
                // Écriture au format RGBA pour OpenCL
                buffer.put((byte) red);
                buffer.put((byte) green);
                buffer.put((byte) blue);
                buffer.put((byte) alpha);
            }
        }
        
        buffer.rewind();
        return buffer;
    }
    
    /**
     * Convertit un ByteBuffer OpenCL (format RGBA) en Image JavaFX
     */
    private static Image byteBufferToImage(ByteBuffer buffer, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();
        
        buffer.rewind();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = buffer.get() & 0xFF;
                int green = buffer.get() & 0xFF;
                int blue = buffer.get() & 0xFF;
                int alpha = buffer.get() & 0xFF;
                
                // Composition ARGB pour JavaFX
                int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                writer.setArgb(x, y, argb);
            }
        }
        
        return image;
    }
    
    /**
     * Libère les ressources OpenCL (à appeler lors de la fermeture de l'application)
     */
    public static void cleanup() {
        if (bicubicKernel != null) {
            clReleaseKernel(bicubicKernel);
            bicubicKernel = null;
        }
        if (lanczosKernel != null) {
            clReleaseKernel(lanczosKernel);
            lanczosKernel = null;
        }
        if (bicubicProgram != null) {
            clReleaseProgram(bicubicProgram);
            bicubicProgram = null;
        }
        if (lanczosProgram != null) {
            clReleaseProgram(lanczosProgram);
            lanczosProgram = null;
        }
    }
}

