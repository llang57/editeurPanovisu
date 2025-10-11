/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.net.URL;
import java.util.Base64;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author LANG Laurent
 */
public class NavigateurCarte extends Region {

    private WebView webView = new WebView();
    private WebEngine webEngine = webView.getEngine();

    /**
     *
     */
    NavigateurCarte() {
        // Activer la console JavaScript pour le débogage
        webEngine.setOnAlert(event -> System.out.println("JS Alert: " + event.getData()));
        
        // Écouter les changements d'état de chargement
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            System.out.println("WebEngine state: " + newState);
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                System.out.println("✅ Page Leaflet unifiée chargée avec succès");
            } else if (newState == javafx.concurrent.Worker.State.FAILED) {
                System.err.println("❌ Échec du chargement de la page");
                Throwable exception = webEngine.getLoadWorker().getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
        
        // Capturer les erreurs JavaScript
        webEngine.setOnError(event -> {
            System.err.println("❌ Erreur WebEngine: " + event.getMessage());
        });
        
        // Charger la carte Leaflet unifiée (sans iframe) en remplaçant les chemins relatifs par des URLs absolues
        try {
            System.out.println("🔄 Chargement de la carte Leaflet unifiée avec URLs absolues...");
            
            // Lire le contenu HTML unifié
            String htmlContent = new String(getClass().getResourceAsStream("openstreetmap_unified.html").readAllBytes(), 
                                           java.nio.charset.StandardCharsets.UTF_8);
            System.out.println("✅ HTML unifié chargé: " + htmlContent.length() + " caractères");
            
            // Obtenir les URLs absolues des ressources Leaflet
            java.net.URL leafletCssUrl = getClass().getResource("libs/leaflet/leaflet.css");
            java.net.URL leafletJsUrl = getClass().getResource("libs/leaflet/leaflet.js");
            
            if (leafletCssUrl == null || leafletJsUrl == null) {
                throw new java.io.IOException("Fichiers Leaflet introuvables dans le classpath");
            }
            
            System.out.println("✅ Leaflet CSS: " + leafletCssUrl.toString());
            System.out.println("✅ Leaflet JS: " + leafletJsUrl.toString());
            
            // Remplacer les chemins relatifs par les URLs absolues
            int cssCount = htmlContent.split("libs/leaflet/leaflet\\.css", -1).length - 1;
            int jsCount = htmlContent.split("libs/leaflet/leaflet\\.js", -1).length - 1;
            System.out.println("🔍 HTML avant remplacement: " + cssCount + " occurrences CSS, " + jsCount + " occurrences JS");
            
            htmlContent = htmlContent.replaceAll("(href=\")libs/leaflet/leaflet\\.css", "$1" + leafletCssUrl.toExternalForm());
            htmlContent = htmlContent.replaceAll("(src=\")libs/leaflet/leaflet\\.js", "$1" + leafletJsUrl.toExternalForm());
            
            // Injecter la clé API LocationIQ depuis la configuration
            String locationIQKey = ApiKeysConfig.getLocationIQApiKey();
            if (ApiKeysConfig.hasLocationIQApiKey()) {
                htmlContent = htmlContent.replace("const apiKey = 'pk.0f147952a41c555a5b70614039fd148b';", 
                                                 "const apiKey = '" + locationIQKey + "';");
                System.out.println("✅ Clé API LocationIQ injectée");
            } else {
                System.out.println("⚠️ Aucune clé API LocationIQ configurée - utilisation de la clé par défaut");
            }
            
            System.out.println("✅ Remplacements effectués");
            System.out.println("🔄 Chargement du HTML avec loadContent()...");
            
            // Charger le contenu modifié
            webEngine.loadContent(htmlContent, "text/html");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de la carte Leaflet: " + e.getMessage());
            // Fallback: charger directement le fichier
            final URL urlUnified = getClass().getResource("openstreetmap_unified.html");
            if (urlUnified != null) {
                webEngine.load(urlUnified.toExternalForm());
            }
        }
        
        getChildren().add(webView);
    }
    
    /**
     * Remplace les URLs d'images dans le CSS par des données base64
     */
    private String replaceImageUrlsWithBase64(String css) {
        try {
            // Lire et encoder chaque image
            String markerIcon = encodeImageToBase64("libs/leaflet/images/marker-icon.png");
            String markerIcon2x = encodeImageToBase64("libs/leaflet/images/marker-icon-2x.png");
            String markerShadow = encodeImageToBase64("libs/leaflet/images/marker-shadow.png");
            String layers = encodeImageToBase64("libs/leaflet/images/layers.png");
            String layers2x = encodeImageToBase64("libs/leaflet/images/layers-2x.png");
            
            // Remplacer les URLs dans le CSS
            css = css.replace("url(images/marker-icon.png)", "url(" + markerIcon + ")");
            css = css.replace("url(images/marker-icon-2x.png)", "url(" + markerIcon2x + ")");
            css = css.replace("url(images/marker-shadow.png)", "url(" + markerShadow + ")");
            css = css.replace("url(images/layers.png)", "url(" + layers + ")");
            css = css.replace("url(images/layers-2x.png)", "url(" + layers2x + ")");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'encodage des images: " + e.getMessage());
        }
        return css;
    }
    
    /**
     * Encode une image en base64
     */
    private String encodeImageToBase64(String imagePath) throws java.io.IOException {
        byte[] imageBytes = getClass().getResourceAsStream(imagePath).readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64;
    }

    /**
     * @return the webView
     */
    public WebView getWebView() {
        return webView;
    }

    /**
     * @param webView the webView to set
     */
    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    /**
     * @return the webEngine
     */
    public WebEngine getWebEngine() {
        return webEngine;
    }

    /**
     * @param webEngine the webEngine to set
     */
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }
    
    /**
     * Force le recalcul de la taille de la carte Leaflet
     */
    public void invalidateMapSize() {
        try {
            webEngine.executeScript("if (typeof map !== 'undefined' && map && typeof map.invalidateSize === 'function') { map.invalidateSize(true); }");
        } catch (Exception e) {
            System.err.println("Erreur lors du redimensionnement de la carte: " + e.getMessage());
        }
    }
}
