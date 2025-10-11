package editeurpanovisu;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Region;
import javax.swing.SwingUtilities;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Marqueur personnalisé - tooltip uniquement (pas d'affichage du nom sur la carte)
 */
class MapMarkerWithHtml extends MapMarkerDot {
    private String tooltipText; // Texte pour le tooltip seulement
    
    public MapMarkerWithHtml(double lat, double lon) {
        super(lat, lon);
        // Important: ne PAS appeler setName() pour éviter l'affichage sur la carte
    }
    
    /**
     * Définit le contenu (peut contenir du HTML: <span>titre</span><br/><span>nom</span>)
     * Extrait le texte brut et formate: "titre - nom" si titre existe, sinon juste "nom"
     */
    public void setHtmlContent(String html) {
        if (html != null && !html.isEmpty()) {
            // Séparer les parties par <br/> AVANT de supprimer les balises
            String[] htmlParts = html.split("<br\\s*/?>");
            
            String titre = "";
            String nom = "";
            
            if (htmlParts.length >= 2) {
                // Extraire le titre (première partie)
                titre = htmlParts[0].replaceAll("<[^>]+>", "")
                                   .replaceAll("&nbsp;", " ")
                                   .replaceAll("&amp;", "&")
                                   .replaceAll("&lt;", "<")
                                   .replaceAll("&gt;", ">")
                                   .trim();
                
                // Extraire le nom (deuxième partie)
                nom = htmlParts[1].replaceAll("<[^>]+>", "")
                                 .replaceAll("&nbsp;", " ")
                                 .replaceAll("&amp;", "&")
                                 .replaceAll("&lt;", "<")
                                 .replaceAll("&gt;", ">")
                                 .trim();
                
                // Formater : "titre - nom" si titre existe, sinon juste "nom"
                if (!titre.isEmpty() && !nom.isEmpty() && !titre.equals(nom)) {
                    tooltipText = titre + " - " + nom;
                } else if (!nom.isEmpty()) {
                    tooltipText = nom;
                } else if (!titre.isEmpty()) {
                    tooltipText = titre;
                }
            } else {
                // Pas de <br/>, extraire tout le texte
                tooltipText = html.replaceAll("<[^>]+>", "")
                                 .replaceAll("&nbsp;", " ")
                                 .replaceAll("&amp;", "&")
                                 .replaceAll("&lt;", "<")
                                 .replaceAll("&gt;", ">")
                                 .trim();
            }
        }
    }
    
    /**
     * Retourne le texte pour le tooltip (pas pour l'affichage sur la carte)
     */
    public String getTooltipText() {
        return tooltipText;
    }
    
    @Override
    public String getName() {
        // Retourner null pour ne RIEN afficher sur la carte
        return null;
    }
}

/**
 * NavigateurCarte utilisant JMapViewer (OpenStreetMap pour Java Desktop)
 * Remplace Gluon Maps qui crashait sur Desktop à cause du système de cache
 * 
 * Solution STABLE basée sur:
 * - JMapViewer (bibliothèque OSM éprouvée)
 * - SwingNode (intégration Swing dans JavaFX)
 * - Pas de WebView, pas de JavaScript
 * 
 * @author LANG Laurent
 */
public class NavigateurCarteGluon extends Region {

    private SwingNode swingNode;
    private JMapViewer mapViewer;
    private List<MapMarker> markers = new ArrayList<>();
    private boolean bDebut = false;
    private boolean mapInitialized = false;
    private String locationIqApiKey = null;

    /**
     * Constructeur - Initialisation minimale
     */
    public NavigateurCarteGluon() {
        System.out.println("🗺️ NavigateurCarteGluon créé (JMapViewer sera initialisé à la demande)");
        bDebut = false;
        
        // Charger la clé API LocationIQ
        loadLocationIqApiKey();
    }
    
    /**
     * Charge la clé API LocationIQ depuis api-keys.properties
     */
    private void loadLocationIqApiKey() {
        try {
            // Essayer d'abord depuis les ressources (src/)
            InputStream input = getClass().getClassLoader().getResourceAsStream("api-keys.properties");
            
            // Si pas trouvé, essayer depuis la racine du projet
            if (input == null) {
                java.io.File file = new java.io.File("api-keys.properties");
                if (file.exists()) {
                    input = new java.io.FileInputStream(file);
                }
            }
            
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                locationIqApiKey = prop.getProperty("locationiq.api.key");
                if (locationIqApiKey != null && !locationIqApiKey.isEmpty()) {
                    System.out.println("✅ Clé API LocationIQ chargée: " + locationIqApiKey.substring(0, Math.min(10, locationIqApiKey.length())) + "...");
                } else {
                    System.out.println("⚠️ Clé API LocationIQ non trouvée dans api-keys.properties");
                }
                input.close();
            } else {
                System.out.println("⚠️ Fichier api-keys.properties non trouvé (cherché dans src/ et racine du projet)");
                System.out.println("   💡 Copiez api-keys.properties.template vers api-keys.properties");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement de la clé API LocationIQ: " + e.getMessage());
        }
    }
    
    /**
     * Initialise réellement le JMapViewer (appelé la première fois qu'on affiche la carte)
     */
    private void initializeMap() {
        if (mapInitialized) {
            return;
        }
        
        System.out.println("🗺️ Initialisation de JMapViewer (OpenStreetMap)...");
        
        try {
            // Créer le SwingNode et le JMapViewer dans le thread Swing
            swingNode = new SwingNode();
            
            SwingUtilities.invokeLater(() -> {
                try {
                    mapViewer = new JMapViewer();
                    mapViewer.setDisplayPosition(new Coordinate(48.8566, 2.3522), 13); // Paris, zoom 13
                    mapViewer.setZoomControlsVisible(true);
                    mapViewer.setScrollWrapEnabled(true);
                    
                    // Ajouter un MouseMotionListener pour afficher les tooltips des marqueurs
                    mapViewer.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                        @Override
                        public void mouseMoved(java.awt.event.MouseEvent e) {
                            // Trouver le marqueur le plus proche du curseur
                            MapMarker closestMarker = null;
                            double minDistance = 15; // Distance max en pixels pour considérer un marqueur
                            
                            for (MapMarker marker : mapViewer.getMapMarkerList()) {
                                java.awt.Point markerPos = mapViewer.getMapPosition(
                                    marker.getLat(), marker.getLon(), false);
                                double distance = Math.sqrt(
                                    Math.pow(markerPos.x - e.getX(), 2) + 
                                    Math.pow(markerPos.y - e.getY(), 2));
                                
                                if (distance < minDistance) {
                                    minDistance = distance;
                                    closestMarker = marker;
                                }
                            }
                            
                            // Afficher le tooltip du marqueur le plus proche
                            if (closestMarker != null && closestMarker instanceof MapMarkerWithHtml) {
                                MapMarkerWithHtml customMarker = (MapMarkerWithHtml) closestMarker;
                                String tooltip = customMarker.getTooltipText();
                                mapViewer.setToolTipText(tooltip);
                            } else {
                                mapViewer.setToolTipText(null);
                            }
                        }
                    });
                    
                    swingNode.setContent(mapViewer);
                    
                    // Forcer le redimensionnement du mapViewer quand le SwingNode change de taille
                    Platform.runLater(() -> {
                        getChildren().add(swingNode);
                        
                        // Écouter les changements de taille pour redimensionner le mapViewer et le SwingNode
                        this.widthProperty().addListener((obs, oldVal, newVal) -> {
                            double width = newVal.doubleValue();
                            double height = getHeight();
                            
                            // Redimensionner le SwingNode
                            swingNode.resize(width, height);
                            
                            // Redimensionner le JMapViewer dans le thread Swing
                            SwingUtilities.invokeLater(() -> {
                                if (mapViewer != null && width > 0 && height > 0) {
                                    mapViewer.setSize((int)width, (int)height);
                                    mapViewer.setPreferredSize(new java.awt.Dimension((int)width, (int)height));
                                    mapViewer.revalidate();
                                    mapViewer.repaint();
                                }
                            });
                        });
                        
                        this.heightProperty().addListener((obs, oldVal, newVal) -> {
                            double width = getWidth();
                            double height = newVal.doubleValue();
                            
                            // Redimensionner le SwingNode
                            swingNode.resize(width, height);
                            
                            // Redimensionner le JMapViewer dans le thread Swing
                            SwingUtilities.invokeLater(() -> {
                                if (mapViewer != null && width > 0 && height > 0) {
                                    mapViewer.setSize((int)width, (int)height);
                                    mapViewer.setPreferredSize(new java.awt.Dimension((int)width, (int)height));
                                    mapViewer.revalidate();
                                    mapViewer.repaint();
                                }
                            });
                        });
                        
                        // Forcer le redimensionnement initial pour éviter le cadre noir
                        Platform.runLater(() -> {
                            double width = getWidth();
                            double height = getHeight();
                            
                            if (width > 0 && height > 0) {
                                swingNode.resize(width, height);
                                
                                SwingUtilities.invokeLater(() -> {
                                    if (mapViewer != null) {
                                        mapViewer.setSize((int)width, (int)height);
                                        mapViewer.setPreferredSize(new java.awt.Dimension((int)width, (int)height));
                                        mapViewer.revalidate();
                                        mapViewer.repaint();
                                    }
                                });
                            }
                        });
                        
                        mapInitialized = true;
                        bDebut = true;
                        System.out.println("✅ JMapViewer initialisé avec succès (responsive + interactive)");
                    });
                } catch (Exception e) {
                    System.err.println("❌ Erreur lors de l'initialisation de JMapViewer: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création du SwingNode: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== API de compatibilité avec NavigateurOpenLayers =====
    
    /**
     * Vérifie si le navigateur est initialisé
     */
    public boolean isbDebut() {
        return bDebut;
    }
    
    /**
     * Définit l'état d'initialisation
     */
    public void setbDebut(boolean bDebut) {
        this.bDebut = bDebut;
    }
    
    /**
     * Centre la carte sur des coordonnées géographiques
     */
    public void allerCoordonnees(String strLatitude, String strLongitude) {
        if (!mapInitialized) {
            System.out.println("⚠️ JMapViewer pas encore initialisé, initialisation...");
            initializeMap();
        }
        try {
            double lat = Double.parseDouble(strLatitude);
            double lon = Double.parseDouble(strLongitude);
            
            SwingUtilities.invokeLater(() -> {
                if (mapViewer != null) {
                    mapViewer.setDisplayPosition(new Coordinate(lat, lon), mapViewer.getZoom());
                    System.out.println("✅ Carte centrée sur: " + lat + ", " + lon);
                }
            });
        } catch (NumberFormatException e) {
            System.err.println("❌ Coordonnées invalides: " + strLatitude + ", " + strLongitude);
        }
    }
    
    /**
     * Centre la carte sur des coordonnées géographiques avec zoom
     */
    public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacteurZoom) {
        if (!mapInitialized) {
            System.out.println("⚠️ JMapViewer pas encore initialisé, initialisation...");
            initializeMap();
        }
        if (coordonnees != null) {
            SwingUtilities.invokeLater(() -> {
                if (mapViewer != null) {
                    mapViewer.setDisplayPosition(
                        new Coordinate(coordonnees.getLatitude(), coordonnees.getLongitude()), 
                        iFacteurZoom
                    );
                    mapViewer.repaint();
                    System.out.println("✅ Carte centrée sur: " + coordonnees.getLatitude() + ", " + 
                                     coordonnees.getLongitude() + " (zoom=" + iFacteurZoom + ")");
                }
            });
        }
    }
    
    /**
     * Récupère les coordonnées du centre de la carte
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        if (!mapInitialized || mapViewer == null) {
            // Retourner des coordonnées par défaut si la carte n'est pas initialisée
            CoordonneesGeographiques coords = new CoordonneesGeographiques();
            coords.setLatitude(48.8566);
            coords.setLongitude(2.3522);
            return coords;
        }
        
        org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate center = mapViewer.getPosition();
        CoordonneesGeographiques coords = new CoordonneesGeographiques();
        coords.setLatitude(center.getLat());
        coords.setLongitude(center.getLon());
        return coords;
    }
    
    /**
     * Supprime un marqueur de la carte
     */
    public void retireMarqueur(int index) {
        if (!mapInitialized) return;
        
        SwingUtilities.invokeLater(() -> {
            if (mapViewer != null && index >= 0 && index < markers.size()) {
                MapMarker marker = markers.remove(index);
                mapViewer.removeMapMarker(marker);
                System.out.println("✅ Marqueur " + index + " supprimé");
            }
        });
    }
    
    /**
     * Supprime tous les marqueurs de la carte
     */
    public void retireMarqueurs() {
        if (!mapInitialized) return;
        
        SwingUtilities.invokeLater(() -> {
            if (mapViewer != null) {
                mapViewer.removeAllMapMarkers();
                markers.clear();
                System.out.println("✅ Tous les marqueurs supprimés");
            }
        });
    }
    
    /**
     * Ajoute un marqueur à la carte
     */
    public int ajouteMarqueur(String strLatitude, String strLongitude, String strImage, 
                              String strTexte, int intEpaisseur) {
        if (!mapInitialized) {
            System.out.println("⚠️ JMapViewer pas encore initialisé, initialisation...");
            initializeMap();
        }
        try {
            double lat = Double.parseDouble(strLatitude);
            double lon = Double.parseDouble(strLongitude);
            
            SwingUtilities.invokeLater(() -> {
                if (mapViewer != null) {
                    MapMarkerWithHtml marker = new MapMarkerWithHtml(lat, lon);
                    marker.setColor(Color.RED);
                    marker.setBackColor(Color.RED);
                    if (strTexte != null && !strTexte.isEmpty()) {
                        marker.setHtmlContent(strTexte);
                    }
                    mapViewer.addMapMarker(marker);
                    markers.add(marker);
                    System.out.println("✅ Marqueur ajouté à: " + lat + ", " + lon + " (index=" + (markers.size() - 1) + ")");
                }
            });
            
            return markers.size() - 1;
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Coordonnées invalides pour marqueur: " + strLatitude + ", " + strLongitude);
            return -1;
        }
    }
    
    /**
     * Ajoute un marqueur à la carte avec CoordonneesGeographiques
     */
    public void ajouteMarqueur(int iNumero, CoordonneesGeographiques coordMarqueur, String strHTML) {
        if (!mapInitialized) {
            System.out.println("⚠️ JMapViewer pas encore initialisé, initialisation...");
            initializeMap();
        }
        if (coordMarqueur != null) {
            SwingUtilities.invokeLater(() -> {
                if (mapViewer != null) {
                    MapMarkerWithHtml marker = new MapMarkerWithHtml(
                        coordMarqueur.getLatitude(), 
                        coordMarqueur.getLongitude()
                    );
                    marker.setColor(Color.RED);
                    marker.setBackColor(Color.RED);
                    if (strHTML != null && !strHTML.isEmpty()) {
                        marker.setHtmlContent(strHTML);
                    }
                    mapViewer.addMapMarker(marker);
                    markers.add(marker);
                    System.out.println("✅ Marqueur " + iNumero + " ajouté à: " + 
                                     coordMarqueur.getLatitude() + ", " + coordMarqueur.getLongitude());
                }
            });
        }
    }
    
    /**
     * Géocode une adresse et centre la carte dessus
     */
    public void allerAdresse(String strAdresse) {
        if (strAdresse == null || strAdresse.trim().isEmpty()) {
            System.out.println("⚠️ Adresse vide");
            return;
        }
        
        if (locationIqApiKey == null || locationIqApiKey.isEmpty()) {
            System.err.println("❌ Clé API LocationIQ non disponible");
            return;
        }
        
        // Géocodage en arrière-plan
        new Thread(() -> {
            try {
                String encodedAddress = URLEncoder.encode(strAdresse.trim(), StandardCharsets.UTF_8.toString());
                String urlString = "https://us1.locationiq.com/v1/search.php?key=" 
                    + locationIqApiKey 
                    + "&q=" + encodedAddress 
                    + "&format=json&limit=1";
                
                System.out.println("🔍 Recherche d'adresse: " + strAdresse);
                
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    
                    // Parser le JSON (format simple)
                    String json = response.toString();
                    if (json.startsWith("[") && json.contains("\"lat\"") && json.contains("\"lon\"")) {
                        // Extraire lat et lon
                        int latIndex = json.indexOf("\"lat\":\"") + 7;
                        int latEnd = json.indexOf("\"", latIndex);
                        String lat = json.substring(latIndex, latEnd);
                        
                        int lonIndex = json.indexOf("\"lon\":\"") + 7;
                        int lonEnd = json.indexOf("\"", lonIndex);
                        String lon = json.substring(lonIndex, lonEnd);
                        
                        double latitude = Double.parseDouble(lat);
                        double longitude = Double.parseDouble(lon);
                        
                        System.out.println("✅ Adresse trouvée: " + latitude + ", " + longitude);
                        
                        // Centrer la carte sur JavaFX thread
                        Platform.runLater(() -> {
                            allerCoordonnees(new CoordonneesGeographiques(longitude, latitude), 15);
                        });
                    } else {
                        System.err.println("❌ Adresse non trouvée: " + strAdresse);
                    }
                } else {
                    System.err.println("❌ Erreur HTTP " + responseCode + " lors du géocodage");
                }
                
                conn.disconnect();
                
            } catch (Exception e) {
                System.err.println("❌ Erreur lors du géocodage: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Géocode une adresse avec zoom personnalisé
     */
    public void allerAdresse(String strAdresse, int iZoom) {
        if (strAdresse == null || strAdresse.trim().isEmpty()) {
            System.out.println("⚠️ Adresse vide");
            return;
        }
        
        if (locationIqApiKey == null || locationIqApiKey.isEmpty()) {
            System.err.println("❌ Clé API LocationIQ non disponible");
            return;
        }
        
        // Géocodage en arrière-plan
        new Thread(() -> {
            try {
                String encodedAddress = URLEncoder.encode(strAdresse.trim(), StandardCharsets.UTF_8.toString());
                String urlString = "https://us1.locationiq.com/v1/search.php?key=" 
                    + locationIqApiKey 
                    + "&q=" + encodedAddress 
                    + "&format=json&limit=1";
                
                System.out.println("🔍 Recherche d'adresse: " + strAdresse);
                
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    
                    // Parser le JSON (format simple)
                    String json = response.toString();
                    if (json.startsWith("[") && json.contains("\"lat\"") && json.contains("\"lon\"")) {
                        // Extraire lat et lon
                        int latIndex = json.indexOf("\"lat\":\"") + 7;
                        int latEnd = json.indexOf("\"", latIndex);
                        String lat = json.substring(latIndex, latEnd);
                        
                        int lonIndex = json.indexOf("\"lon\":\"") + 7;
                        int lonEnd = json.indexOf("\"", lonIndex);
                        String lon = json.substring(lonIndex, lonEnd);
                        
                        double latitude = Double.parseDouble(lat);
                        double longitude = Double.parseDouble(lon);
                        
                        System.out.println("✅ Adresse trouvée: " + latitude + ", " + longitude);
                        
                        // Centrer la carte sur JavaFX thread
                        Platform.runLater(() -> {
                            allerCoordonnees(new CoordonneesGeographiques(longitude, latitude), iZoom);
                        });
                    } else {
                        System.err.println("❌ Adresse non trouvée: " + strAdresse);
                    }
                } else {
                    System.err.println("❌ Erreur HTTP " + responseCode + " lors du géocodage");
                }
                
                conn.disconnect();
                
            } catch (Exception e) {
                System.err.println("❌ Erreur lors du géocodage: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Définit le niveau de zoom
     */
    public void choixZoom(int intZoom) {
        if (!mapInitialized) {
            System.out.println("⚠️ JMapViewer pas encore initialisé, initialisation...");
            initializeMap();
        }
        SwingUtilities.invokeLater(() -> {
            if (mapViewer != null) {
                mapViewer.setZoom(intZoom);
                System.out.println("✅ Zoom défini: " + intZoom);
            }
        });
    }
    
    /**
     * Retourne la liste des types de cartes disponibles
     */
    public String getListeTypeCartes() {
        return "OpenStreetMap,Esri World Imagery";
    }
    
    /**
     * Change le type de carte
     */
    public void changeCarte(String strType) {
        System.out.println("🗺️ Changement de carte demandé: " + strType);
        System.out.println("   Note: JMapViewer utilise uniquement OpenStreetMap");
    }
    
    /**
     * Obtient le nom du répertoire JavaScript (pour compatibilité API)
     */
    public String getRepertScript() {
        return "jmapviewer"; // Pour compatibilité
    }
    
    /**
     * Affiche un radar sur la carte (non implémenté)
     */
    public void afficheRadar(CoordonneesGeographiques coords, double dAngle,
                            double dFOV, double dTaille, String strCouleurLigne,
                            String strCouleurFond, double dOpacite) {
        System.out.println("⚠️ afficheRadar() non implémenté pour JMapViewer");
    }
    
    /**
     * Retire le radar de la carte (non implémenté)
     */
    public void retireRadar() {
        System.out.println("⚠️ retireRadar() non implémenté pour JMapViewer");
    }
    
    /**
     * Affiche le navigateur de carte
     */
    public void afficheNavigateurOpenLayer() {
        // Initialiser la carte lors du premier affichage
        initializeMap();
        System.out.println("✅ NavigateurCarteGluon visible (JMapViewer)");
        setVisible(true);
    }
    
    /**
     * Affiche les choix de cartographie
     */
    public void afficheCartesOpenlayer() {
        System.out.println("✅ JMapViewer utilise OpenStreetMap par défaut");
    }
    
    /**
     * Obtient le panneau de choix de cartographie (pour compatibilité API)
     */
    public javafx.scene.layout.Pane getApChoixCartographie() {
        // Retourner un panneau vide pour l'instant
        javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();
        pane.setPrefHeight(100); // Hauteur par défaut
        return pane;
    }
    
    /**
     * Définit la clé API Bing (ignoré pour JMapViewer)
     */
    public void setBingApiKey(String key) {
        System.out.println("⚠️ setBingApiKey() ignoré (JMapViewer n'utilise pas Bing)");
    }
    
    /**
     * Valide une clé API Bing (toujours OK pour JMapViewer car pas utilisée)
     */
    public boolean valideBingApiKey(String key) {
        return true; // JMapViewer n'utilise pas Bing, donc toujours valide
    }
}
