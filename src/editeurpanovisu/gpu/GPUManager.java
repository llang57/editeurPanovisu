/*
 * Module de gestion GPU avec OpenCL pour PanoVisu
 * Détection automatique, initialisation et gestion des ressources GPU
 */
package editeurpanovisu.gpu;

import org.jocl.*;
import static org.jocl.CL.*;

/**
 * Gestionnaire singleton pour l'accélération GPU via OpenCL
 * Gère la détection, l'initialisation et le cycle de vie des ressources GPU
 * 
 * @author PanoVisu Team
 */
public class GPUManager {
    
    private static GPUManager instance;
    private boolean gpuAvailable = false;
    private boolean gpuEnabled = true; // Préférence utilisateur
    private cl_context context;
    private cl_command_queue commandQueue;
    private cl_device_id device;
    private cl_platform_id platform;
    
    private String deviceName = "Unknown";
    private String platformName = "Unknown";
    private long deviceMemory = 0;
    private int computeUnits = 0;
    private long maxWorkGroupSize = 0;
    
    /**
     * Constructeur privé (singleton)
     */
    private GPUManager() {
        initializeGPU();
    }
    
    /**
     * Récupère l'instance unique du gestionnaire GPU
     * @return Instance du GPUManager
     */
    public static synchronized GPUManager getInstance() {
        if (instance == null) {
            instance = new GPUManager();
        }
        return instance;
    }
    
    /**
     * Initialise OpenCL et détecte les GPU disponibles
     */
    private void initializeGPU() {
        try {
            // Activer les exceptions OpenCL
            CL.setExceptionsEnabled(true);
            
            // 1. Récupérer les plateformes OpenCL
            int[] numPlatforms = new int[1];
            clGetPlatformIDs(0, null, numPlatforms);
            
            if (numPlatforms[0] == 0) {
                System.out.println("⚠️  Aucune plateforme OpenCL détectée");
                return;
            }
            
            cl_platform_id[] platforms = new cl_platform_id[numPlatforms[0]];
            clGetPlatformIDs(platforms.length, platforms, null);
            
            // 2. Chercher un GPU
            for (cl_platform_id p : platforms) {
                // Récupérer le nom de la plateforme
                long[] size = new long[1];
                clGetPlatformInfo(p, CL_PLATFORM_NAME, 0, null, size);
                byte[] buffer = new byte[(int)size[0]];
                clGetPlatformInfo(p, CL_PLATFORM_NAME, buffer.length, Pointer.to(buffer), null);
                String pName = new String(buffer, 0, buffer.length - 1);
                
                // Chercher des GPU sur cette plateforme
                int[] numDevices = new int[1];
                int result = clGetDeviceIDs(p, CL_DEVICE_TYPE_GPU, 0, null, numDevices);
                
                if (result == CL_SUCCESS && numDevices[0] > 0) {
                    cl_device_id[] devices = new cl_device_id[numDevices[0]];
                    clGetDeviceIDs(p, CL_DEVICE_TYPE_GPU, devices.length, devices, null);
                    
                    // Prendre le premier GPU disponible
                    platform = p;
                    device = devices[0];
                    platformName = pName;
                    
                    // Récupérer les infos du GPU
                    retrieveDeviceInfo();
                    
                    // 3. Créer le contexte OpenCL
                    cl_context_properties contextProperties = new cl_context_properties();
                    contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);
                    context = clCreateContext(
                        contextProperties, 
                        1, 
                        new cl_device_id[]{device}, 
                        null, 
                        null, 
                        null
                    );
                    
                    // 4. Créer la file de commandes
                    cl_queue_properties properties = new cl_queue_properties();
                    commandQueue = clCreateCommandQueueWithProperties(context, device, properties, null);
                    
                    gpuAvailable = true;
                    System.out.println("✅ GPU initialisé avec succès");
                    System.out.println("   📍 Plateforme: " + platformName);
                    System.out.println("   🎮 GPU: " + deviceName);
                    System.out.println("   💾 Mémoire: " + (deviceMemory / 1024 / 1024) + " MB");
                    System.out.println("   ⚡ Unités de calcul: " + computeUnits);
                    System.out.println("   👥 Taille max workgroup: " + maxWorkGroupSize);
                    break;
                }
            }
            
            if (!gpuAvailable) {
                System.out.println("⚠️  Aucun GPU OpenCL détecté, utilisation du CPU");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'initialisation OpenCL: " + e.getMessage());
            gpuAvailable = false;
        }
    }
    
    /**
     * Récupère les informations détaillées du GPU
     */
    private void retrieveDeviceInfo() {
        try {
            // Nom du device
            long[] size = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_NAME, 0, null, size);
            byte[] buffer = new byte[(int)size[0]];
            clGetDeviceInfo(device, CL_DEVICE_NAME, buffer.length, Pointer.to(buffer), null);
            deviceName = new String(buffer, 0, buffer.length - 1);
            
            // Mémoire globale
            long[] mem = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_GLOBAL_MEM_SIZE, Sizeof.cl_long, Pointer.to(mem), null);
            deviceMemory = mem[0];
            
            // Unités de calcul
            int[] units = new int[1];
            clGetDeviceInfo(device, CL_DEVICE_MAX_COMPUTE_UNITS, Sizeof.cl_int, Pointer.to(units), null);
            computeUnits = units[0];
            
            // Taille max workgroup
            long[] workgroup = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_MAX_WORK_GROUP_SIZE, Sizeof.size_t, Pointer.to(workgroup), null);
            maxWorkGroupSize = workgroup[0];
            
        } catch (Exception e) {
            System.err.println("⚠️  Erreur lors de la récupération des infos GPU: " + e.getMessage());
        }
    }
    
    /**
     * Vérifie si un GPU est disponible
     * @return true si un GPU OpenCL est disponible
     */
    public boolean isGPUAvailable() {
        return gpuAvailable;
    }
    
    /**
     * Vérifie si l'utilisation du GPU est activée par l'utilisateur
     * @return true si le GPU doit être utilisé
     */
    public boolean isGPUEnabled() {
        return gpuEnabled && gpuAvailable;
    }
    
    /**
     * Active ou désactive l'utilisation du GPU
     * @param enabled true pour activer, false pour désactiver
     */
    public void setGPUEnabled(boolean enabled) {
        this.gpuEnabled = enabled;
        System.out.println(enabled ? 
            "✅ Utilisation du GPU activée" : 
            "⚠️  Utilisation du GPU désactivée (CPU utilisé)");
    }
    
    /**
     * Récupère le contexte OpenCL
     * @return Contexte OpenCL ou null si non disponible
     */
    public cl_context getContext() {
        return context;
    }
    
    /**
     * Récupère la file de commandes OpenCL
     * @return File de commandes OpenCL ou null si non disponible
     */
    public cl_command_queue getCommandQueue() {
        return commandQueue;
    }
    
    /**
     * Récupère le device OpenCL
     * @return Device OpenCL ou null si non disponible
     */
    public cl_device_id getDevice() {
        return device;
    }
    
    /**
     * Récupère le nom du GPU
     * @return Nom du GPU
     */
    public String getDeviceName() {
        return deviceName;
    }
    
    /**
     * Récupère le nom de la plateforme
     * @return Nom de la plateforme OpenCL
     */
    public String getPlatformName() {
        return platformName;
    }
    
    /**
     * Récupère la mémoire disponible sur le GPU
     * @return Mémoire en octets
     */
    public long getDeviceMemory() {
        return deviceMemory;
    }
    
    /**
     * Récupère le nombre d'unités de calcul
     * @return Nombre d'unités de calcul
     */
    public int getComputeUnits() {
        return computeUnits;
    }
    
    /**
     * Récupère la taille maximale d'un workgroup
     * @return Taille max workgroup
     */
    public long getMaxWorkGroupSize() {
        return maxWorkGroupSize;
    }
    
    /**
     * Récupère des informations détaillées sur le GPU sous forme de String
     * @return Informations formatées
     */
    public String getGPUInfo() {
        if (!gpuAvailable) {
            return "❌ Aucun GPU OpenCL disponible";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("✅ GPU OpenCL disponible\n");
        info.append("   📍 Plateforme: ").append(platformName).append("\n");
        info.append("   🎮 GPU: ").append(deviceName).append("\n");
        info.append("   💾 Mémoire: ").append(deviceMemory / 1024 / 1024).append(" MB\n");
        info.append("   ⚡ Unités de calcul: ").append(computeUnits).append("\n");
        info.append("   👥 Taille max workgroup: ").append(maxWorkGroupSize).append("\n");
        info.append("   🔧 Statut: ").append(gpuEnabled ? "Activé" : "Désactivé");
        
        return info.toString();
    }
    
    /**
     * Libère les ressources OpenCL
     */
    public void cleanup() {
        try {
            if (commandQueue != null) {
                clReleaseCommandQueue(commandQueue);
            }
            if (context != null) {
                clReleaseContext(context);
            }
            System.out.println("✅ Ressources GPU libérées");
        } catch (Exception e) {
            System.err.println("⚠️  Erreur lors du nettoyage GPU: " + e.getMessage());
        }
    }
}
