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
    private Runnable onMapReadyCallback = null;
    private boolean bDebut = false; // Flag indiquant que la carte est chargée et prête

    /**
     *
     */
    NavigateurCarte() {
        // Corriger le fond transparent de la WebView
        webView.setStyle("-fx-background-color: white;");
        webEngine.setUserStyleSheetLocation("data:text/css;base64," + 
            java.util.Base64.getEncoder().encodeToString("body { background-color: white !important; }".getBytes()));
        
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
        
        // Charger la carte Leaflet unifiée directement depuis l'URL
        // Note: Utiliser load() au lieu de loadContent() pour préserver les chemins relatifs des ressources
        try {
            System.out.println("🔄 Chargement de la carte Leaflet unifiée...");
            
            final URL urlUnified = getClass().getResource("openstreetmap_unified.html");
            if (urlUnified == null) {
                throw new java.io.IOException("Fichier openstreetmap_unified.html introuvable");
            }
            
            System.out.println("✅ URL: " + urlUnified.toExternalForm());
            
            // Charger directement depuis l'URL - cela préserve le contexte de base pour les ressources relatives
            webEngine.load(urlUnified.toExternalForm());
            
            System.out.println("🔄 Chargement de la carte avec load() - les chemins relatifs fonctionneront");
            
            // Listener pour injecter la clé API LocationIQ après le chargement
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    System.out.println("✅ Page chargée, injection de la clé API LocationIQ...");
                    
                    // Marquer la carte comme prête
                    bDebut = true;
                    System.out.println("✅ NavigateurCarte.bDebut = true");
                    
                    // Injecter la clé API LocationIQ si configurée
                    if (ApiKeysConfig.hasLocationIQApiKey()) {
                        String locationIQKey = ApiKeysConfig.getLocationIQApiKey();
                        try {
                            webEngine.executeScript(
                                "if (typeof apiKey !== 'undefined') {" +
                                "  apiKey = '" + locationIQKey + "';" +
                                "  console.log('✅ Clé API LocationIQ injectée');" +
                                "}"
                            );
                            System.out.println("✅ Clé API LocationIQ injectée dans le contexte JavaScript");
                        } catch (Exception ex) {
                            System.err.println("⚠️ Impossible d'injecter la clé API: " + ex.getMessage());
                        }
                    } else {
                        System.out.println("⚠️ Utilisation de la clé API LocationIQ par défaut");
                    }
                    
                    // Exécuter le callback si défini
                    if (onMapReadyCallback != null) {
                        System.out.println("✅ Exécution du callback onMapReady");
                        javafx.application.Platform.runLater(onMapReadyCallback);
                    }
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de la carte Leaflet: " + e.getMessage());
        }
        
        // Ajouter le WebView comme enfant
        getChildren().add(webView);
        
        // Lier les dimensions du WebView à celles du Region
        webView.prefWidthProperty().bind(this.widthProperty());
        webView.prefHeightProperty().bind(this.heightProperty());
        webView.minWidthProperty().bind(this.widthProperty());
        webView.minHeightProperty().bind(this.heightProperty());
        webView.maxWidthProperty().bind(this.widthProperty());
        webView.maxHeightProperty().bind(this.heightProperty());
        
        // Listener pour invalider la taille de la carte Leaflet quand le Region est redimensionné
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            invalidateMapSize();
        });
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            invalidateMapSize();
        });
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
    
    /**
     * Affiche un radar sur la carte (champ de vision)
     * @param coords Coordonnées du centre du radar
     * @param dAngle Angle de direction en degrés
     * @param dFOV Ouverture du champ de vision en degrés
     * @param dTaille Taille du rayon en pixels
     * @param strCouleurLigne Couleur de la bordure (format #RRGGBB)
     * @param strCouleurFond Couleur de remplissage (format #RRGGBB)
     * @param dOpacite Opacité du remplissage (0-1)
     */
    public void afficheRadar(CoordonneesGeographiques coords, double dAngle,
                            double dFOV, double dTaille, String strCouleurLigne,
                            String strCouleurFond, double dOpacite) {
        if (coords == null) {
            System.err.println("⚠️ Coordonnées null, impossible d'afficher le radar");
            return;
        }
        
        try {
            String script = String.format(java.util.Locale.US,
                "if (typeof afficheRadar === 'function') { " +
                "  afficheRadar(%f, %f, %f, %f, %f, '%s', '%s', %f); " +
                "} else { " +
                "  console.error('❌ Fonction afficheRadar non disponible'); " +
                "}",
                coords.getLatitude(),
                coords.getLongitude(),
                dAngle,
                dFOV,
                dTaille,
                strCouleurLigne,
                strCouleurFond,
                dOpacite
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Radar affiché: lat=" + coords.getLatitude() + 
                             ", lng=" + coords.getLongitude() + 
                             ", angle=" + dAngle + "°, FOV=" + dFOV + "°");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'affichage du radar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Retire le radar de la carte
     */
    public void retireRadar() {
        try {
            String script = 
                "if (typeof retireRadar === 'function') { " +
                "  retireRadar(); " +
                "} else { " +
                "  console.error('❌ Fonction retireRadar non disponible'); " +
                "}";
            
            webEngine.executeScript(script);
            System.out.println("✅ Radar retiré");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du retrait du radar: " + e.getMessage());
        }
    }
    
    /**
     * Récupère les coordonnées actuelles du centre de la carte
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        return recupereCoordonnees(-1); // Par défaut, récupère le centre de la carte
    }
    
    /**
     * Récupère les coordonnées d'un marqueur ou du centre de la carte
     * @param index Index du marqueur (>= 0) ou -1 pour récupérer le centre de la carte
     * @return Les coordonnées géographiques
     */
    public CoordonneesGeographiques recupereCoordonnees(int index) {
        try {
            String script;
            if (index >= 0) {
                // Récupérer la position d'un marqueur spécifique
                script = 
                    "(function() { " +
                    "  if (typeof getMarkerPosition === 'function') { " +
                    "    var pos = getMarkerPosition(" + index + "); " +
                    "    if (pos && pos.lat !== undefined && pos.lng !== undefined) { " +
                    "      return pos.lat + ',' + pos.lng; " +
                    "    } " +
                    "  } " +
                    "  return '0,0'; " +
                    "})()";
                System.out.println("🔍 Récupération position du marqueur " + index);
            } else {
                // Récupérer le centre de la carte
                script = 
                    "(function() { " +
                    "  if (typeof map !== 'undefined' && map) { " +
                    "    var center = map.getCenter(); " +
                    "    return center.lat + ',' + center.lng; " +
                    "  } " +
                    "  return '0,0'; " +
                    "})()";
                System.out.println("🔍 Récupération centre de la carte");
            }
            
            Object result = webEngine.executeScript(script);
            if (result != null) {
                String[] coords = result.toString().split(",");
                if (coords.length == 2) {
                    double lat = Double.parseDouble(coords[0]);
                    double lng = Double.parseDouble(coords[1]);
                    System.out.println("✅ Coordonnées récupérées: lat=" + lat + ", lng=" + lng);
                    // CORRECTION: Le constructeur CoordonneesGeographiques attend (longitude, latitude)
                    return new CoordonneesGeographiques(lng, lat);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des coordonnées: " + e.getMessage());
            e.printStackTrace();
        }
        System.err.println("⚠️ Retour coordonnées par défaut (0,0)");
        return new CoordonneesGeographiques(0, 0);
    }
    
    /**
     * Retire tous les marqueurs de la carte
     */
    public void retireMarqueurs() {
        try {
            String script = 
                "if (typeof removeAllMarkers === 'function') { " +
                "  removeAllMarkers(); " +
                "} else { " +
                "  console.error('❌ Fonction removeAllMarkers non disponible'); " +
                "}";
            
            webEngine.executeScript(script);
            System.out.println("✅ Tous les marqueurs retirés");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du retrait des marqueurs: " + e.getMessage());
        }
    }
    
    /**
     * Ajoute un marqueur sur la carte
     * @param index Index du marqueur
     * @param coords Coordonnées du marqueur
     * @param strHTML Contenu HTML du popup
     */
    public void ajouteMarqueur(int index, CoordonneesGeographiques coords, String strHTML) {
        if (coords == null) {
            System.err.println("⚠️ Coordonnées null, impossible d'ajouter le marqueur");
            return;
        }
        
        try {
            // Échapper les guillemets simples et doubles dans le HTML
            String escapedHTML = strHTML
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
            
            String script = String.format(java.util.Locale.US,
                "if (typeof ajouteMarqueur === 'function') { " +
                "  ajouteMarqueur(%d, %f, %f, '%s'); " +
                "} else { " +
                "  console.error('❌ Fonction ajouteMarqueur non disponible'); " +
                "}",
                index,
                coords.getLongitude(),
                coords.getLatitude(),
                escapedHTML
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Marqueur ajouté: index=" + index + 
                             ", lat=" + coords.getLatitude() + 
                             ", lng=" + coords.getLongitude());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'ajout du marqueur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Centre la carte sur des coordonnées avec un niveau de zoom
     * @param coords Coordonnées du centre
     * @param iZoom Niveau de zoom (1-18)
     */
    public void allerCoordonnees(CoordonneesGeographiques coords, int iZoom) {
        if (coords == null) {
            System.err.println("⚠️ Coordonnées null, impossible de centrer la carte");
            return;
        }
        
        try {
            String script = String.format(java.util.Locale.US,
                "if (typeof centerOn === 'function') { " +
                "  centerOn(%f, %f, %d); " +
                "} else { " +
                "  console.error('❌ Fonction centerOn non disponible'); " +
                "}",
                coords.getLatitude(),
                coords.getLongitude(),
                iZoom
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Carte centrée sur: lat=" + coords.getLatitude() + 
                             ", lng=" + coords.getLongitude() + 
                             ", zoom=" + iZoom);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du centrage de la carte: " + e.getMessage());
        }
    }
    
    /**
     * Définit le niveau de zoom de la carte
     * @param iZoom Niveau de zoom (1-18)
     */
    public void choixZoom(int iZoom) {
        try {
            String script = String.format(
                "if (typeof choixZoom === 'function') { " +
                "  choixZoom(%d); " +
                "} else if (typeof setZoom === 'function') { " +
                "  setZoom(%d); " +
                "} else { " +
                "  console.error('❌ Fonctions de zoom non disponibles'); " +
                "}",
                iZoom,
                iZoom
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Zoom défini: " + iZoom);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du changement de zoom: " + e.getMessage());
        }
    }
    
    /**
     * Recherche une adresse et centre la carte dessus
     * @param strAdresse Adresse à rechercher
     * @param iZoom Niveau de zoom après la recherche
     */
    public void allerAdresse(String strAdresse, int iZoom) {
        if (strAdresse == null || strAdresse.trim().isEmpty()) {
            System.err.println("⚠️ Adresse vide, impossible de faire la recherche");
            return;
        }
        
        try {
            // Échapper les guillemets dans l'adresse
            String escapedAdresse = strAdresse
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
            
            String script = String.format(
                "if (typeof allerAdresse === 'function') { " +
                "  allerAdresse('%s'); " +
                "} else { " +
                "  console.error('❌ Fonction allerAdresse non disponible'); " +
                "}",
                escapedAdresse
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Recherche d'adresse lancée: " + strAdresse);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la recherche d'adresse: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Indique si la carte est prête (initialisée)
     * @return true si la carte est prête
     */
    public boolean isbDebut() {
        // Utiliser le flag local au lieu de tester JavaScript à chaque fois
        // Le flag est mis à true quand WebEngine.State.SUCCEEDED
        return bDebut;
    }
    
    /**
     * Définit un callback à exécuter quand la carte est chargée et prête
     * @param callback Runnable à exécuter
     */
    public void setOnMapReady(Runnable callback) {
        this.onMapReadyCallback = callback;
        
        // Si la carte est déjà chargée, exécuter immédiatement
        if (isbDebut()) {
            System.out.println("✅ Carte déjà chargée, exécution immédiate du callback");
            javafx.application.Platform.runLater(callback);
        }
    }
    
    /**
     * Change le type de carte (OSM ou Satellite)
     * @param strType Type de carte ("osm" ou "satellite")
     */
    public void changeCarte(String strType) {
        if (strType == null || strType.trim().isEmpty()) {
            System.err.println("⚠️ Type de carte vide");
            return;
        }
        
        try {
            String script = String.format(
                "if (typeof setMapType === 'function') { " +
                "  setMapType('%s'); " +
                "} else { " +
                "  console.error('❌ Fonction setMapType non disponible'); " +
                "}",
                strType.toLowerCase()
            );
            
            webEngine.executeScript(script);
            System.out.println("✅ Type de carte changé: " + strType);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du changement de type de carte: " + e.getMessage());
        }
    }
    
    /**
     * Crée un panneau de choix de type de carte (OSM ou Satellite)
     * @return AnchorPane avec les boutons de sélection
     */
    public javafx.scene.layout.AnchorPane getApChoixCartographie() {
        javafx.scene.layout.AnchorPane apChoix = new javafx.scene.layout.AnchorPane();
        apChoix.setPrefSize(220, 100);
        apChoix.setStyle("-fx-background-color: -fx-base; -fx-padding: 10px;");
        
        javafx.scene.control.Label lblTitre = new javafx.scene.control.Label("Type de carte");
        lblTitre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblTitre.setLayoutX(10);
        lblTitre.setLayoutY(10);
        
        javafx.scene.control.RadioButton rbOSM = new javafx.scene.control.RadioButton("OpenStreetMap");
        rbOSM.setLayoutX(10);
        rbOSM.setLayoutY(40);
        rbOSM.setSelected(true);
        
        javafx.scene.control.RadioButton rbSatellite = new javafx.scene.control.RadioButton("Satellite");
        rbSatellite.setLayoutX(10);
        rbSatellite.setLayoutY(65);
        
        javafx.scene.control.ToggleGroup tgType = new javafx.scene.control.ToggleGroup();
        rbOSM.setToggleGroup(tgType);
        rbSatellite.setToggleGroup(tgType);
        
        rbOSM.setOnAction(e -> changeCarte("osm"));
        rbSatellite.setOnAction(e -> changeCarte("satellite"));
        
        apChoix.getChildren().addAll(lblTitre, rbOSM, rbSatellite);
        
        return apChoix;
    }
    
    /**
     * Méthode vide pour compatibilité (l'interface est créée à la demande)
     */
    public void afficheCartesOpenlayer() {
        // Interface créée dynamiquement via getApChoixCartographie()
    }
}
