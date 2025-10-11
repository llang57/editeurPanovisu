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
        
        System.out.println("✅ NavigateurCarteGluon initialisé");
    }
    
    // ===== API de compatibilité avec NavigateurOpenLayers =====
    
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
     * Récupère les coordonnées du centre de la carte
     * @return Tableau [latitude, longitude]
     */
    public String[] recupereCoordonnees() {
        MapPoint center = mapView.getCenter();
        return new String[] {
            String.valueOf(center.getLatitude()),
            String.valueOf(center.getLongitude())
        };
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
     * Note: Gluon Maps utilise OpenStreetMap par défaut
     * Pour d'autres tuiles (satellite, etc.), il faut implémenter un TileRetriever custom
     */
    public String recupereCartographiesOpenLayers() {
        return "OpenStreetMap";
    }
    
    /**
     * Change le type de carte
     * @param strType Type de carte demandé
     * 
     * Note: Non implémenté - nécessite un TileRetriever custom pour satellite/autres
     */
    public void changeCarte(String strType) {
        System.out.println("⚠️ Changement de type de carte non implémenté: " + strType);
        System.out.println("   TODO: Implémenter TileRetriever custom pour satellite");
        // TODO: Voir com.gluonhq.maps.tile.TileRetriever interface
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
}
