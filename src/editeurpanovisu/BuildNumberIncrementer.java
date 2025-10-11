package editeurpanovisu;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe utilitaire pour gérer l'incrémentation automatique du numéro de build
 * Utilisée par Maven lors de la compilation
 * 
 * @author Laurent LANG
 */
public class BuildNumberIncrementer {
    
    private static final String BUILD_NUM_FILE = "build.num";
    private static final String I18N_PROPERTIES_FILE = "src/editeurpanovisu/i18n/PanoVisu.properties";
    private static final String PROJECT_PROPERTIES_FILE = "src/project.properties";
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Incrémentation du numéro de build ===");
            
            // Lire le numéro de build actuel
            int currentBuild = readBuildNumber();
            System.out.println("Build actuel : " + currentBuild);
            
            // Incrémenter
            int newBuild = currentBuild + 1;
            System.out.println("Nouveau build : " + newBuild);
            
            // Mettre à jour les fichiers
            updateBuildNumFile(newBuild);
            updateI18nProperties(newBuild);
            updateProjectProperties(newBuild);
            
            System.out.println("✓ Numéro de build mis à jour avec succès !");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'incrémentation du build : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Lit le numéro de build depuis build.num
     */
    private static int readBuildNumber() throws IOException {
        File file = new File(BUILD_NUM_FILE);
        if (!file.exists()) {
            System.out.println("Fichier build.num non trouvé, création avec build=1990");
            return 1990;
        }
        
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        }
        
        String buildStr = props.getProperty("build.number", "1990");
        return Integer.parseInt(buildStr.trim());
    }
    
    /**
     * Met à jour le fichier build.num
     */
    private static void updateBuildNumFile(int buildNumber) throws IOException {
        Properties props = new Properties();
        props.setProperty("build.number", String.valueOf(buildNumber));
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        String timestamp = sdf.format(new Date());
        
        try (FileOutputStream fos = new FileOutputStream(BUILD_NUM_FILE)) {
            fos.write(("#Build Number - Auto-incremented by Maven\n").getBytes("UTF-8"));
            fos.write(("#" + timestamp + "\n").getBytes("UTF-8"));
            fos.write(("build.number=" + buildNumber + "\n").getBytes("UTF-8"));
        }
        
        System.out.println("✓ Fichier " + BUILD_NUM_FILE + " mis à jour");
    }
    
    /**
     * Met à jour le fichier i18n/PanoVisu.properties
     */
    private static void updateI18nProperties(int buildNumber) throws IOException {
        File file = new File(I18N_PROPERTIES_FILE);
        if (!file.exists()) {
            System.err.println("Fichier " + I18N_PROPERTIES_FILE + " non trouvé");
            return;
        }
        
        // Lire le contenu
        List<String> lines = Files.readAllLines(file.toPath(), java.nio.charset.StandardCharsets.UTF_8);
        
        // Mettre à jour la ligne build.numero
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("build.numero=")) {
                // Format avec espace insécable Unicode pour l'affichage : 1 990, 2 000, etc.
                String formattedBuild = formatBuildNumber(buildNumber);
                lines.set(i, "build.numero=" + formattedBuild);
                found = true;
                break;
            }
        }
        
        if (!found) {
            // Ajouter après la première ligne (date)
            String formattedBuild = formatBuildNumber(buildNumber);
            lines.add(1, "build.numero=" + formattedBuild);
        }
        
        // Réécrire le fichier
        Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
        
        System.out.println("✓ Fichier " + I18N_PROPERTIES_FILE + " mis à jour");
    }
    
    /**
     * Met à jour le fichier project.properties
     */
    private static void updateProjectProperties(int buildNumber) throws IOException {
        File file = new File(PROJECT_PROPERTIES_FILE);
        if (!file.exists()) {
            System.err.println("Fichier " + PROJECT_PROPERTIES_FILE + " non trouvé");
            return;
        }
        
        // Lire le contenu
        List<String> lines = Files.readAllLines(file.toPath(), java.nio.charset.StandardCharsets.UTF_8);
        
        // Mettre à jour la ligne Application.buildnumber
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("Application.buildnumber=")) {
                lines.set(i, "Application.buildnumber=" + buildNumber);
                found = true;
                break;
            }
        }
        
        if (!found) {
            // Ajouter après project.version
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("project.version=")) {
                    lines.add(i + 1, "Application.buildnumber=" + buildNumber);
                    break;
                }
            }
        }
        
        // Réécrire le fichier
        Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
        
        System.out.println("✓ Fichier " + PROJECT_PROPERTIES_FILE + " mis à jour");
    }
    
    /**
     * Formate le numéro de build avec espace insécable
     * Exemple : 1990 -> "1\u00a0990", 2000 -> "2\u00a0000"
     */
    private static String formatBuildNumber(int buildNumber) {
        String buildStr = String.valueOf(buildNumber);
        if (buildStr.length() <= 3) {
            return buildStr;
        }
        
        // Séparer les milliers avec \u00a0 (espace insécable)
        StringBuilder formatted = new StringBuilder();
        int len = buildStr.length();
        for (int i = 0; i < len; i++) {
            if (i > 0 && (len - i) % 3 == 0) {
                formatted.append("\\u00a0");
            }
            formatted.append(buildStr.charAt(i));
        }
        
        return formatted.toString();
    }
}
