/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

/**
 * NavigateurCarte utilisant Gluon Maps (solution native JavaFX)
 * Remplace l'ancienne approche basée sur WebView + Leaflet.js
 * 
 * Avantages:
 * - Rendu natif JavaFX (pas de problèmes WebView)
 * - Pas de pont JavaScript
 * - Performance optimale
 * - API JavaFX standard
 * 
 * @author LANG Laurent
 */
public class NavigateurCarteGluon extends Region {

    private final MapView mapView;
    private final PoiLayer markerLayer;
    private boolean bDebut = false;
    
    /**
     * Couche personnalisée pour afficher des marqueurs (POI = Points of Interest)
     */
    private static class PoiLayer extends MapLayer {
        private final ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();
        
        /**
         * Ajoute un marqueur à la carte
         * @param point Position géographique
         * @param icon Icône visuelle du marqueur
         */
        public void addPoint(MapPoint point, Node icon) {
            points.add(new Pair<>(point, icon));
            this.getChildren().add(icon);
            this.markDirty();
        }
        
        /**
         * Supprime un marqueur spécifique
         * @param index Index du marqueur à supprimer
         */
        public void removePoint(int index) {
            if (index >= 0 && index < points.size()) {
                Pair<MapPoint, Node> pair = points.remove(index);
                this.getChildren().remove(pair.getValue());
                this.markDirty();
            }
        }
        
        /**
         * Supprime tous les marqueurs
         */
        public void clearPoints() {
            points.clear();
            this.getChildren().clear();
            this.markDirty();
        }
        
        /**
         * Obtient le nombre de marqueurs
         */
        public int getPointCount() {
            return points.size();
        }
        
        @Override
        protected void layoutLayer() {
            // Mise à jour des positions des marqueurs lors du déplacement/zoom de la carte
            for (Pair<MapPoint, Node> candidate : points) {
                MapPoint point = candidate.getKey();
                Node icon = candidate.getValue();
                Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
                icon.setVisible(true);
                icon.setTranslateX(mapPoint.getX());
                icon.setTranslateY(mapPoint.getY());
            }
        }
    }

    /**
     * Constructeur - Initialise la carte centrée sur Paris
     */
    public NavigateurCarteGluon() {
        System.out.println("🗺️ Initialisation NavigateurCarteGluon (Gluon Maps)...");
        
        // Créer la vue carte
        mapView = new MapView();
        
        // Créer la couche de marqueurs
        markerLayer = new PoiLayer();
        mapView.addLayer(markerLayer);
        
        // Configuration initiale : Paris, zoom 13
        mapView.setCenter(48.8566, 2.3522);
        mapView.setZoom(13);
        
        // Ajouter la carte au conteneur
        getChildren().add(mapView);
        
        bDebut = true;
        System.out.println("✅ NavigateurCarteGluon initialisé");
    }
    
    // ===== API de compatibilité avec NavigateurOpenLayers =====
    
    /**
     * Vérifie si le navigateur est initialisé
     * @return true si initialisé
     */
    public boolean isbDebut() {
        return bDebut;
    }
    
    /**
     * Définit l'état d'initialisation
     * @param bDebut État
     */
    public void setbDebut(boolean bDebut) {
        this.bDebut = bDebut;
    }
    
    /**
     * Centre la carte sur des coordonnées géographiques
     * @param strLatitude Latitude en degrés
     * @param strLongitude Longitude en degrés
     */
    public void allerCoordonnees(String strLatitude, String strLongitude) {
        try {
            double lat = Double.parseDouble(strLatitude);
            double lon = Double.parseDouble(strLongitude);
            mapView.setCenter(lat, lon);
            System.out.println("✅ Carte centrée sur: " + lat + ", " + lon);
        } catch (NumberFormatException e) {
            System.err.println("❌ Coordonnées invalides: " + strLatitude + ", " + strLongitude);
        }
    }
    
    /**
     * Centre la carte sur des coordonnées géographiques avec zoom
     * @param coordonnees Coordonnées géographiques
     * @param iFacteurZoom Niveau de zoom (0-20)
     */
    public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacteurZoom) {
        if (coordonnees != null) {
            mapView.setCenter(coordonnees.getLatitude(), coordonnees.getLongitude());
            mapView.setZoom(iFacteurZoom);
            System.out.println("✅ Carte centrée sur: " + coordonnees.getLatitude() + ", " + coordonnees.getLongitude() + " (zoom=" + iFacteurZoom + ")");
        }
    }
    
    /**
     * Récupère les coordonnées du centre de la carte
     * @return CoordonneesGeographiques du centre
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        MapPoint center = mapView.getCenter();
        CoordonneesGeographiques coords = new CoordonneesGeographiques();
        coords.setLatitude(center.getLatitude());
        coords.setLongitude(center.getLongitude());
        return coords;
    }
    
    /**
     * Supprime un marqueur de la carte
     * @param index Index du marqueur (0-based)
     */
    public void retireMarqueur(int index) {
        markerLayer.removePoint(index);
        System.out.println("✅ Marqueur " + index + " supprimé");
    }
    
    /**
     * Supprime tous les marqueurs de la carte
     */
    public void retireMarqueurs() {
        markerLayer.clearPoints();
        System.out.println("✅ Tous les marqueurs supprimés");
    }
    
    /**
     * Ajoute un marqueur à la carte
     * @param strLatitude Latitude
     * @param strLongitude Longitude
     * @param strImage Nom de l'image (ignoré, on utilise un cercle rouge)
     * @param strTexte Texte du popup (ignoré pour l'instant)
     * @param intEpaisseur Épaisseur (ignoré)
     * @return Index du marqueur ajouté
     */
    public int ajouteMarqueur(String strLatitude, String strLongitude, String strImage, 
                              String strTexte, int intEpaisseur) {
        try {
            double lat = Double.parseDouble(strLatitude);
            double lon = Double.parseDouble(strLongitude);
            
            MapPoint point = new MapPoint(lat, lon);
            Circle marker = new Circle(7, Color.RED);
            marker.setStroke(Color.DARKRED);
            marker.setStrokeWidth(2);
            
            markerLayer.addPoint(point, marker);
            int index = markerLayer.getPointCount() - 1;
            
            System.out.println("✅ Marqueur ajouté à: " + lat + ", " + lon + " (index=" + index + ")");
            return index;
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Coordonnées invalides pour marqueur: " + strLatitude + ", " + strLongitude);
            return -1;
        }
    }
    
    /**
     * Ajoute un marqueur à la carte avec CoordonneesGeographiques
     * @param iNumero Numéro du marqueur
     * @param coordMarqueur Coordonnées du marqueur
     * @param strHTML HTML du popup (ignoré pour l'instant)
     */
    public void ajouteMarqueur(int iNumero, CoordonneesGeographiques coordMarqueur, String strHTML) {
        if (coordMarqueur != null) {
            MapPoint point = new MapPoint(coordMarqueur.getLatitude(), coordMarqueur.getLongitude());
            Circle marker = new Circle(7, Color.RED);
            marker.setStroke(Color.DARKRED);
            marker.setStrokeWidth(2);
            
            markerLayer.addPoint(point, marker);
            
            System.out.println("✅ Marqueur " + iNumero + " ajouté à: " + coordMarqueur.getLatitude() + ", " + coordMarqueur.getLongitude());
        }
    }
    
    /**
     * Géocode une adresse et centre la carte dessus
     * @param strAdresse Adresse à rechercher
     * 
     * Note: Nécessite une implémentation de géocodage (Nominatim API par exemple)
     * Pour l'instant, cette méthode est un placeholder
     */
    public void allerAdresse(String strAdresse) {
        System.out.println("⚠️ Géocodage d'adresse non implémenté: " + strAdresse);
        System.out.println("   TODO: Intégrer Nominatim ou autre service de géocodage");
        // TODO: Implémenter avec Nominatim API ou HttpClient
        // Exemple: https://nominatim.openstreetmap.org/search?format=json&q=address
    }
    
    /**
     * Géocode une adresse et centre la carte dessus avec zoom
     * @param strAdresse Adresse à rechercher
     * @param iZoom Niveau de zoom
     */
    public void allerAdresse(String strAdresse, int iZoom) {
        allerAdresse(strAdresse);
        choixZoom(iZoom);
    }
    
    /**
     * Définit le niveau de zoom
     * @param intZoom Niveau de zoom (0-20)
     */
    public void choixZoom(int intZoom) {
        mapView.setZoom(intZoom);
        System.out.println("✅ Zoom défini: " + intZoom);
    }
    
    /**
     * Retourne la liste des types de cartes disponibles
     * @return Chaîne CSV des types
     * 
     * Note: Pour l'instant, seul OpenStreetMap est pleinement fonctionnel
     * Esri World Imagery nécessiterait une réimplémentation du système de tuiles
     */
    public String recupereCartographiesOpenLayers() {
        return "OpenStreetMap,Esri World Imagery";
    }
    
    /**
     * Change le type de carte
     * @param strType Type de carte demandé ("OpenStreetMap" ou "Esri")
     * 
     * Note: Gluon Maps ne supporte pas nativement le changement de provider.
     * Pour Esri, il faudrait créer un nouveau MapView avec un TileRetriever custom.
     * Pour simplifier, on informe juste l'utilisateur du changement demandé.
     */
    public void changeCarte(String strType) {
        System.out.println("🗺️ Changement de carte demandé: " + strType);
        
        if ("Esri".equalsIgnoreCase(strType) || "Esri World Imagery".equalsIgnoreCase(strType)) {
            System.out.println("⚠️ Esri World Imagery nécessite un TileRetriever custom");
            System.out.println("   Gluon Maps OSM restera actif pour l'instant");
            System.out.println("   TODO: Implémenter un système de MapView interchangeable");
        } else {
            System.out.println("✅ OpenStreetMap (défaut de Gluon Maps)");
        }
    }
    
    /**
     * Obtient la vue carte Gluon Maps
     * @return Instance de MapView
     */
    public MapView getMapView() {
        return mapView;
    }
    
    /**
     * Obtient la couche de marqueurs
     * @return Instance de PoiLayer
     */
    public PoiLayer getMarkerLayer() {
        return markerLayer;
    }
    
    /**
     * Affiche un radar sur la carte
     * @param coords Coordonnées du radar
     * @param dAngle Angle d'orientation
     * @param dFOV Champ de vision (ouverture)
     * @param dTaille Taille du radar
     * @param strCouleurLigne Couleur de la ligne (hex avec #)
     * @param strCouleurFond Couleur de fond (hex avec #)
     * @param dOpacite Opacité (0-1)
     */
    public void afficheRadar(CoordonneesGeographiques coords, double dAngle,
                            double dFOV, double dTaille, String strCouleurLigne,
                            String strCouleurFond, double dOpacite) {
        if (coords != null) {
            System.out.println("⚠️ afficheRadar() non complètement implémenté");
            System.out.println("   Radar à: " + coords.getLatitude() + ", " + coords.getLongitude() + 
                             " (angle=" + dAngle + ", fov=" + dFOV + ", taille=" + dTaille + ")");
            // TODO: Implémenter avec un layer custom pour dessiner le radar
        }
    }
    
    /**
     * Retire le radar de la carte
     */
    public void retireRadar() {
        System.out.println("⚠️ retireRadar() non complètement implémenté");
        // TODO: Implémenter avec un layer custom pour le radar
    }
    
    /**
     * Affiche le navigateur de carte (pour compatibilité API)
     */
    public void afficheNavigateurOpenLayer() {
        System.out.println("✅ NavigateurCarteGluon visible (Gluon Maps)");
        setVisible(true);
    }
    
    /**
     * Affiche les choix de cartographie (pour compatibilité API)
     */
    public void afficheCartesOpenlayer() {
        System.out.println("✅ Choix de cartographie: OSM et Esri disponibles");
    }
    
    /**
     * Obtient le panneau de choix de cartographie (stub pour compatibilité)
     * @return null (géré différemment dans Gluon Maps)
     */
    public AnchorPane getApChoixCartographie() {
        return null; // Gluon Maps gère différemment l'UI
    }
    
    /**
     * Définit la clé API Bing (non utilisée avec Gluon Maps)
     * @param strKey Clé API
     */
    public void setBingApiKey(String strKey) {
        System.out.println("⚠️ setBingApiKey() ignoré (Gluon Maps n'utilise pas Bing)");
    }
    
    /**
     * Valide la clé API Bing (non utilisée avec Gluon Maps)
     * @param strKey Clé API
     * @return true (toujours valide car non utilisé)
     */
    public boolean valideBingApiKey(String strKey) {
        System.out.println("⚠️ valideBingApiKey() ignoré (Gluon Maps n'utilise pas Bing)");
        return true;
    }
}
