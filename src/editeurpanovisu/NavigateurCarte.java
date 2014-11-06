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
public class NavigateurCarte extends Region {

    private WebView webView = new WebView();
    private WebEngine webEngine = webView.getEngine();

    /**
     *
     */
    NavigateurCarte() {
        final URL urlGoogleMaps = getClass().getResource("openstreetmap.html");
        webEngine.load(urlGoogleMaps.toExternalForm());
        getChildren().add(webView);
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
}
