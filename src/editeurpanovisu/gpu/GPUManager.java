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
    private String openclVersion = "Unknown";
    private long deviceMemory = 0;
    private long deviceMaxAllocSize = 0;
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
            // Vérifier si OpenCL est disponible
            try {
                // Tenter d'accéder à la classe CL pour déclencher le chargement de la bibliothèque native
                Class.forName("org.jocl.CL");
            } catch (UnsatisfiedLinkError | ExceptionInInitializerError e) {
                System.out.println("⚠️  OpenCL non disponible sur ce système - Mode CPU activé");
                System.out.println("   Détails: " + e.getMessage());
                gpuAvailable = false;
                return;
            } catch (ClassNotFoundException e) {
                System.out.println("⚠️  Bibliothèque JOCL non trouvée - Mode CPU activé");
                gpuAvailable = false;
                return;
            }
            
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
            
            // 2. Chercher un GPU en privilégiant rusticl (moderne) sur Clover (ancien)
            cl_platform_id rusticlPlatform = null;
            cl_platform_id cloverPlatform = null;
            String rusticlName = null;
            String cloverName = null;
            
            for (cl_platform_id p : platforms) {
                // Récupérer le nom de la plateforme
                long[] size = new long[1];
                clGetPlatformInfo(p, CL_PLATFORM_NAME, 0, null, size);
                byte[] buffer = new byte[(int)size[0]];
                clGetPlatformInfo(p, CL_PLATFORM_NAME, buffer.length, Pointer.to(buffer), null);
                String pName = new String(buffer, 0, buffer.length - 1).toLowerCase();
                
                // Identifier rusticl vs Clover
                if (pName.contains("rusticl")) {
                    rusticlPlatform = p;
                    rusticlName = pName;
                } else if (pName.contains("clover")) {
                    cloverPlatform = p;
                    cloverName = pName;
                }
            }
            
            // Privilégier rusticl (OpenCL 3.0) sur Clover (OpenCL 1.1)
            cl_platform_id selectedPlatform = rusticlPlatform != null ? rusticlPlatform : cloverPlatform;
            String selectedName = rusticlPlatform != null ? rusticlName : cloverName;
            
            if (selectedPlatform != null) {
                // Chercher des GPU sur la plateforme sélectionnée
                int[] numDevices = new int[1];
                int result = clGetDeviceIDs(selectedPlatform, CL_DEVICE_TYPE_GPU, 0, null, numDevices);
                
                if (result == CL_SUCCESS && numDevices[0] > 0) {
                    cl_device_id[] devices = new cl_device_id[numDevices[0]];
                    clGetDeviceIDs(selectedPlatform, CL_DEVICE_TYPE_GPU, devices.length, devices, null);
                    
                    // Prendre le premier GPU disponible
                    platform = selectedPlatform;
                    device = devices[0];
                    platformName = selectedName;
                    
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
                    System.out.println("   🔧 Version: " + openclVersion);
                    System.out.println("   🎮 GPU: " + deviceName);
                    System.out.println("   💾 Mémoire globale: " + (deviceMemory / 1024 / 1024) + " MB");
                    System.out.println("   📦 Alloc max par buffer: " + (deviceMaxAllocSize / 1024 / 1024) + " MB");
                    System.out.println("   ⚡ Unités de calcul: " + computeUnits);
                    System.out.println("   👥 Taille max workgroup: " + maxWorkGroupSize);
                }
            }
            
            if (!gpuAvailable) {
                System.out.println("⚠️  Aucun GPU OpenCL détecté, utilisation du CPU");
            }
            
        } catch (UnsatisfiedLinkError | ExceptionInInitializerError e) {
            System.out.println("⚠️  Erreur de chargement de la bibliothèque OpenCL: " + e.getMessage());
            System.out.println("   L'application continuera en mode CPU");
            gpuAvailable = false;
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
            
            // Mémoire globale disponible
            long[] mem = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_GLOBAL_MEM_SIZE, Sizeof.cl_long, Pointer.to(mem), null);
            deviceMemory = mem[0];
            
            // Taille maximale d'allocation mémoire (important pour GPU intégrés)
            long[] maxAlloc = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_MAX_MEM_ALLOC_SIZE, Sizeof.cl_long, Pointer.to(maxAlloc), null);
            deviceMaxAllocSize = maxAlloc[0];
            
            // Unités de calcul
            int[] units = new int[1];
            clGetDeviceInfo(device, CL_DEVICE_MAX_COMPUTE_UNITS, Sizeof.cl_int, Pointer.to(units), null);
            computeUnits = units[0];
            
            // Taille max workgroup
            long[] workgroup = new long[1];
            clGetDeviceInfo(device, CL_DEVICE_MAX_WORK_GROUP_SIZE, Sizeof.size_t, Pointer.to(workgroup), null);
            maxWorkGroupSize = workgroup[0];
            
            // Version OpenCL du device
            clGetDeviceInfo(device, CL_DEVICE_VERSION, 0, null, size);
            buffer = new byte[(int)size[0]];
            clGetDeviceInfo(device, CL_DEVICE_VERSION, buffer.length, Pointer.to(buffer), null);
            openclVersion = new String(buffer, 0, buffer.length - 1);
            
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
     * Récupère la version OpenCL du device
     * @return Version OpenCL (ex: "OpenCL 1.1 Mesa 24.0.0", "OpenCL 3.0 rusticl")
     */
    public String getOpenCLVersion() {
        return openclVersion;
    }
    
    /**
     * Détermine les options de compilation OpenCL appropriées selon la version
     * Pour Clover (OpenCL 1.1): utilise "-cl-std=CL1.2" pour compatibilité
     * Pour rusticl et autres (OpenCL 2.0+): pas d'options spécifiques nécessaires
     * @return Options de compilation ou null
     */
    public String getBuildOptions() {
        if (openclVersion.contains("OpenCL 1.")) {
            // Clover ou anciennes implémentations OpenCL 1.x
            return "-cl-std=CL1.2";
        }
        // rusticl (OpenCL 3.0) et autres implémentations modernes
        return null;
    }
    
    /**
     * Récupère la mémoire globale disponible sur le GPU
     * @return Mémoire totale en octets
     */
    public long getDeviceMemory() {
        return deviceMemory;
    }
    
    /**
     * Récupère la taille maximale d'allocation mémoire pour un buffer
     * Pour GPU intégré AMD: peut être limité par le BIOS (512MB typique)
     * Cette valeur détermine la taille max des images traitables par le GPU
     * @return Taille max allocation en octets
     */
    public long getDeviceMaxAllocSize() {
        return deviceMaxAllocSize;
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
