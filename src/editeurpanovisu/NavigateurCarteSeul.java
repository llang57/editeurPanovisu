/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.net.URL;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author LANG Laurent
 */
public class NavigateurCarteSeul extends Region {

    private WebView webView = new WebView();
    private WebEngine webEngine = webView.getEngine();

    /**
     *
     */
    NavigateurCarteSeul() {
        // Corriger le fond transparent de la WebView
        webView.setStyle("-fx-background-color: white;");
        webEngine.setUserStyleSheetLocation("data:text/css;base64," + 
            java.util.Base64.getEncoder().encodeToString("body { background-color: white !important; }".getBytes()));
        
        final URL urlGoogleMaps = getClass().getResource("openstreetmap1.html");
        webEngine.load(urlGoogleMaps.toExternalForm());
        getChildren().add(webView);
    }

    /**
     * Retourne la valeur de webView.
     *
     * @return the webView
     */
    public WebView getWebView() {
        return webView;
    }

    /**
     * Définit la valeur de webView.
     *
     * @param webView the webView to set
     */
    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    /**
     * Retourne la valeur de webEngine.
     *
     * @return the webEngine
     */
    public WebEngine getWebEngine() {
        return webEngine;
    }

    /**
     * Définit la valeur de webEngine.
     *
     * @param webEngine the webEngine to set
     */
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }
}
