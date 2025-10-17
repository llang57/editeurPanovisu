package editeurpanovisu.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Serveur HTTP local simple pour servir les visites virtuelles
 * 
 * <p>Ce serveur permet d'éviter les problèmes de sécurité CORS qui se produisent
 * lorsqu'on ouvre un fichier HTML en mode file://. Il démarre automatiquement sur
 * un port disponible et sert les fichiers de la visite générée.</p>
 * 
 * <p><b>Utilisation :</b></p>
 * <pre>{@code
 * LocalHTTPServer server = LocalHTTPServer.getInstance();
 * server.setRootDirectory("/path/to/visite");
 * server.start();
 * String url = server.getUrl(); // http://localhost:8080
 * // ... Ouvrir le navigateur avec cette URL
 * server.stop(); // Arrêter quand terminé
 * }</pre>
 * 
 * @author EditeurPanovisu
 * @since 3.1
 */
public class LocalHTTPServer {
    
    private static LocalHTTPServer instance;
    private HttpServer server;
    private int port = 8080;
    private String rootDirectory;
    private boolean isRunning = false;
    
    // Types MIME pour les fichiers communs
    private static final Map<String, String> MIME_TYPES = new HashMap<>();
    
    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("htm", "text/html");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("js", "application/javascript");
        MIME_TYPES.put("json", "application/json");
        MIME_TYPES.put("xml", "application/xml");
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("gif", "image/gif");
        MIME_TYPES.put("svg", "image/svg+xml");
        MIME_TYPES.put("ico", "image/x-icon");
        MIME_TYPES.put("ttf", "font/ttf");
        MIME_TYPES.put("woff", "font/woff");
        MIME_TYPES.put("woff2", "font/woff2");
        MIME_TYPES.put("eot", "application/vnd.ms-fontobject");
        MIME_TYPES.put("otf", "font/otf");
    }
    
    /**
     * Constructeur privé (pattern Singleton)
     */
    private LocalHTTPServer() {
    }
    
    /**
     * Obtient l'instance unique du serveur
     * 
     * @return L'instance du serveur HTTP local
     */
    public static synchronized LocalHTTPServer getInstance() {
        if (instance == null) {
            instance = new LocalHTTPServer();
        }
        return instance;
    }
    
    /**
     * Définit le répertoire racine à servir
     * 
     * @param rootDirectory Chemin absolu du répertoire contenant les fichiers
     */
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
    
    /**
     * Définit le port du serveur
     * 
     * @param port Numéro de port (par défaut 8080)
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     * Démarre le serveur HTTP
     * 
     * @throws IOException Si le serveur ne peut pas démarrer
     */
    public void start() throws IOException {
        if (isRunning) {
            System.out.println("⚠️ Le serveur HTTP est déjà démarré");
            return;
        }
        
        if (rootDirectory == null || rootDirectory.isEmpty()) {
            throw new IllegalStateException("Le répertoire racine n'est pas défini");
        }
        
        // Trouver un port disponible (essai jusqu'à 100 ports)
        int tentatives = 0;
        int portOriginal = port;
        while (tentatives < 100) {
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                break;
            } catch (IOException e) {
                System.out.println("⚠️ Port " + port + " occupé, tentative port " + (port + 1));
                port++;
                tentatives++;
                if (tentatives >= 100) {
                    System.err.println("❌ Impossible de trouver un port disponible entre " + portOriginal + " et " + port);
                    throw new IOException("Impossible de trouver un port disponible après " + tentatives + " tentatives", e);
                }
            }
        }
        
        server.createContext("/", new FileHandler(rootDirectory));
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        isRunning = true;
        
        System.out.println("✅ Serveur HTTP démarré sur le port " + port);
        System.out.println("📂 Répertoire racine : " + rootDirectory);
        System.out.println("🌐 URL : " + getUrl());
    }
    
    /**
     * Arrête le serveur HTTP
     */
    public void stop() {
        if (server != null && isRunning) {
            server.stop(0);
            isRunning = false;
            System.out.println("⏹️ Serveur HTTP arrêté");
        }
    }
    
    /**
     * Réinitialise le serveur (arrête et remet le port à 8080)
     */
    public void reset() {
        stop();
        port = 8080;
        rootDirectory = null;
        System.out.println("🔄 Serveur HTTP réinitialisé");
    }
    
    /**
     * Vérifie si le serveur est en cours d'exécution
     * 
     * @return true si le serveur est démarré
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Obtient l'URL complète du serveur
     * 
     * @return L'URL (ex: http://localhost:8080)
     */
    public String getUrl() {
        return "http://localhost:" + port;
    }
    
    /**
     * Obtient le numéro de port utilisé
     * 
     * @return Le numéro de port
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Handler HTTP pour servir les fichiers
     */
    private static class FileHandler implements HttpHandler {
        private final String rootDirectory;
        
        public FileHandler(String rootDirectory) {
            this.rootDirectory = rootDirectory;
        }
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
            
            // Si la requête est pour "/", servir "index.html"
            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }
            
            // Construire le chemin du fichier
            Path filePath = Path.of(rootDirectory + requestPath);
            File file = filePath.toFile();
            
            if (!file.exists() || file.isDirectory()) {
                // Fichier non trouvé
                String response = "404 - Fichier non trouvé";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
            
            // Déterminer le type MIME
            String extension = getFileExtension(file.getName());
            String mimeType = MIME_TYPES.getOrDefault(extension, "application/octet-stream");
            
            // Envoyer le fichier
            exchange.getResponseHeaders().set("Content-Type", mimeType);
            
            // Ajouter les headers CORS pour éviter les problèmes
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            byte[] fileBytes = Files.readAllBytes(filePath);
            exchange.sendResponseHeaders(200, fileBytes.length);
            
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(fileBytes);
            }
        }
        
        /**
         * Extrait l'extension d'un nom de fichier
         * 
         * @param fileName Nom du fichier
         * @return L'extension en minuscules (sans le point)
         */
        private String getFileExtension(String fileName) {
            int lastDot = fileName.lastIndexOf('.');
            if (lastDot > 0 && lastDot < fileName.length() - 1) {
                return fileName.substring(lastDot + 1).toLowerCase();
            }
            return "";
        }
    }
}
