/*
 * Chargeur de kernels OpenCL pour PanoVisu
 * Gère le chargement et la compilation des kernels depuis les ressources
 */
package editeurpanovisu.gpu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Utilitaire pour charger les kernels OpenCL depuis les ressources
 * 
 * @author PanoVisu Team
 */
public class GPUKernelLoader {
    
    /**
     * Charge un kernel OpenCL depuis les ressources
     * 
     * @param kernelName Nom du fichier kernel (sans le chemin, avec .cl)
     * @return Code source du kernel
     * @throws Exception Si le kernel ne peut pas être chargé
     */
    public static String loadKernel(String kernelName) throws Exception {
        // Charger depuis le classpath (editeurpanovisu/gpu)
        String resourcePath = "/editeurpanovisu/gpu/" + kernelName;
        
        try (InputStream is = GPUKernelLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new Exception("Kernel non trouvé: " + resourcePath);
            }
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String source = reader.lines().collect(Collectors.joining("\n"));
                
                if (source.isEmpty()) {
                    throw new Exception("Kernel vide: " + resourcePath);
                }
                
                System.out.println("✅ Kernel chargé: " + kernelName + " (" + source.length() + " caractères)");
                return source;
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement du kernel " + kernelName + ": " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Charge le kernel equi2cube
     * @return Code source du kernel
     * @throws Exception Si le kernel ne peut pas être chargé
     */
    public static String loadEqui2CubeKernel() throws Exception {
        return loadKernel("equi2cube.cl");
    }
    
    /**
     * Charge le kernel cube2equi
     * @return Code source du kernel
     * @throws Exception Si le kernel ne peut pas être chargé
     */
    public static String loadCube2EquiKernel() throws Exception {
        return loadKernel("cube2equi.cl");
    }
    
    /**
     * Charge le kernel de redimensionnement bicubique
     * @return Code source du kernel
     * @throws Exception Si le kernel ne peut pas être chargé
     */
    public static String loadBicubicKernel() throws Exception {
        return loadKernel("resize_bicubic.cl");
    }
    
    /**
     * Charge le kernel de redimensionnement Lanczos3
     * @return Code source du kernel
     * @throws Exception Si le kernel ne peut pas être chargé
     */
    public static String loadLanczosKernel() throws Exception {
        return loadKernel("resize_lanczos3.cl");
    }
}
