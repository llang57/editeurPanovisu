/*
 * Copyright (c) 2014-2025 Laurent LANG
 * Éditeur PanoVisu - Version 3.0
 * 
 * Navigateur de carte utilisant Gluon Maps (natif JavaFX)
 * Supporte OpenStreetMap et Esri World Imagery uniquement
 */
package editeurpanovisu;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.HashMap;
import java.util.Map;

/**
 * Navigateur de carte basé sur Gluon Maps
 * Remplace NavigateurOpenLayersSeul avec une architecture native JavaFX
 * 
 * @author LANG Laurent
 */
public class NavigateurGluonCarte {

    private MapView mapView;
    private CoordonneesGeographiques marqueur;
    private boolean bDebut = false;
    private final ToggleGroup tgCartesGluon = new ToggleGroup();
    private AnchorPane apChoixCartographie = new AnchorPane();
    private String strProviderActif = "OpenStreetMap"; // OSM par défaut
    
    // Layer pour les marqueurs
    private MarkerLayer markerLayer;
    
    // Layer pour le radar
    private RadarLayer radarLayer;
    
    // Stockage des marqueurs
    private final Map<Integer, MapPoint> marqueurs = new HashMap<>();
    
    /**
     * Types de providers de cartes supportés
     */
    public enum MapProvider {
        OSM("OpenStreetMap", "OSM"),
        ESRI_SATELLITE("Esri World Imagery", "Esri");
        
        private final String displayName;
        private final String shortName;
        
        MapProvider(String displayName, String shortName) {
            this.displayName = displayName;
            this.shortName = shortName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getShortName() {
            return shortName;
        }
    }

    /**
     * @return the marqueur
     */
    public CoordonneesGeographiques getMarqueur() {
        return marqueur;
    }

    /**
     * @param marqueur the marqueur to set
     */
    public void setMarqueur(CoordonneesGeographiques marqueur) {
        this.marqueur = marqueur;
    }

    /**
     * Aller à des coordonnées spécifiques avec un zoom donné
     * 
     * @param coordonnees Coordonnées à atteindre
     * @param iFacteurZoom Niveau de zoom (0-20)
     */
    public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacteurZoom) {
        if (coordonnees != null && mapView != null) {
            mapView.setCenter(coordonnees.getLatitude(), coordonnees.getLongitude());
            mapView.setZoom(iFacteurZoom);
        }
    }

    /**
     * Récupérer les coordonnées du centre de la carte
     * 
     * @return Les coordonnées actuelles du centre
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        if (mapView == null) {
            return new CoordonneesGeographiques();
        }
        
        CoordonneesGeographiques coordonnees = new CoordonneesGeographiques();
        MapPoint center = mapView.getCenter();
        coordonnees.setLongitude(center.getLongitude());
        coordonnees.setLatitude(center.getLatitude());
        return coordonnees;
    }

    /**
     * Retirer un marqueur spécifique
     * 
     * @param iNumeroMarqueur Numéro du marqueur à retirer
     */
    public void retireMarqueur(int iNumeroMarqueur) {
        if (markerLayer != null && marqueurs.containsKey(iNumeroMarqueur)) {
            markerLayer.removeMarker(iNumeroMarqueur);
            marqueurs.remove(iNumeroMarqueur);
        }
    }

    /**
     * Retirer tous les marqueurs
     */
    public void retireMarqueurs() {
        if (markerLayer != null) {
            markerLayer.clearMarkers();
            marqueurs.clear();
        }
    }

    /**
     * Ajouter un marqueur sur la carte
     * 
     * @param iNumeroMarqueur Numéro unique du marqueur
     * @param coordMarqueur Coordonnées du marqueur
     * @param strHTML Contenu HTML pour la popup (sera simplifié en texte)
     */
    public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coordMarqueur, String strHTML) {
        if (coordMarqueur != null && markerLayer != null) {
            MapPoint point = new MapPoint(coordMarqueur.getLatitude(), coordMarqueur.getLongitude());
            marqueurs.put(iNumeroMarqueur, point);
            
            // Extraire le texte du HTML (simplification)
            String texte = strHTML.replaceAll("<[^>]*>", "");
            markerLayer.addMarker(iNumeroMarqueur, point, texte);
        }
    }

    /**
     * Chercher une adresse (fonctionnalité simplifiée)
     * Note: Nécessiterait une API de géocodage pour être pleinement fonctionnel
     * 
     * @param strAdresse Adresse à chercher
     * @param iFacteurZoom Niveau de zoom
     */
    public void chercheAdresse(String strAdresse, int iFacteurZoom) {
        // TODO: Implémenter avec une API de géocodage (Nominatim pour OSM)
        System.out.println("Recherche d'adresse: " + strAdresse + " (non implémenté)");
    }

    /**
     * Changer le niveau de zoom
     * 
     * @param iFacteurZoom Nouveau niveau de zoom (0-20)
     */
    public void choixZoom(int iFacteurZoom) {
        if (mapView != null) {
            mapView.setZoom(iFacteurZoom);
        }
    }

    /**
     * Obtenir les noms des providers disponibles
     * 
     * @return String avec les noms séparés par des virgules
     */
    public String getNomsProviders() {
        StringBuilder sb = new StringBuilder();
        for (MapProvider provider : MapProvider.values()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(provider.getDisplayName());
        }
        return sb.toString();
    }

    /**
     * Retirer le radar de la carte
     */
    public void retireRadar() {
        if (radarLayer != null) {
            radarLayer.clearRadar();
        }
    }

    /**
     * Afficher le radar sur la carte
     * 
     * @param longitude Longitude du centre
     * @param latitude Latitude du centre
     * @param angle Angle de rotation en degrés
     * @param rotation Angle de rotation (doublon?)
     * @param fov Champ de vision
     * @param strCouleurFond Couleur de fond (hex)
     * @param dOpacite Opacité (0-1)
     * @param strCouleurTexte Couleur du texte (hex)
     */
    public void afficheRadar(double longitude, double latitude, double angle, 
                            double rotation, double fov, String strCouleurFond, 
                            double dOpacite, String strCouleurTexte) {
        if (radarLayer != null) {
            MapPoint center = new MapPoint(latitude, longitude);
            radarLayer.showRadar(center, angle, fov, strCouleurFond, dOpacite);
        }
    }

    /**
     * Changer le provider de carte (OSM ou Esri)
     * 
     * @param strProvider Nom du provider ("OpenStreetMap" ou "Esri")
     */
    public void changeCarte(String strProvider) {
        if (mapView == null) {
            return;
        }
        
        strProviderActif = strProvider;
        
        // Note: Gluon Maps utilise OSM par défaut
        // Pour changer de provider, il faut utiliser un custom TileRetriever
        // Pour l'instant, on garde OSM (Esri nécessiterait plus de configuration)
        // TODO: Implémenter un CustomTileRetriever pour Esri World Imagery
    }

    /**
     * Vérifier si c'est le début (première initialisation)
     * 
     * @return true si c'est le début
     */
    public boolean isbDebut() {
        return bDebut;
    }

    /**
     * Définir l'état de début
     * 
     * @param bDebut État de début
     */
    public void setbDebut(boolean bDebut) {
        this.bDebut = bDebut;
    }

    /**
     * Obtenir le provider actif
     * 
     * @return Nom du provider actif
     */
    public String getStrProviderActif() {
        return strProviderActif;
    }

    /**
     * Créer et initialiser le navigateur de carte
     * 
     * @param apOpenLayers AnchorPane parent
     * @param dLargeur Largeur de la carte
     * @param dHauteur Hauteur de la carte
     */
    public void creerNavigateur(AnchorPane apOpenLayers, double dLargeur, double dHauteur) {
        // Créer la MapView
        mapView = new MapView();
        mapView.setPrefSize(dLargeur, dHauteur);
        
        // Position par défaut (Paris)
        mapView.setCenter(48.8566, 2.3522);
        mapView.setZoom(12);
        
        // Créer les layers
        markerLayer = new MarkerLayer();
        radarLayer = new RadarLayer();
        
        mapView.addLayer(markerLayer);
        mapView.addLayer(radarLayer);
        
        // Créer l'interface de sélection de carte
        creerChoixCartographie();
        
        // Ajouter à l'AnchorPane
        AnchorPane.setTopAnchor(mapView, 0.0);
        AnchorPane.setLeftAnchor(mapView, 0.0);
        AnchorPane.setRightAnchor(mapView, 0.0);
        AnchorPane.setBottomAnchor(mapView, 0.0);
        
        apOpenLayers.getChildren().add(mapView);
        apOpenLayers.getChildren().add(apChoixCartographie);
        
        bDebut = true;
    }

    /**
     * Créer l'interface de choix de cartographie
     */
    private void creerChoixCartographie() {
        apChoixCartographie.setPrefSize(200, 80);
        apChoixCartographie.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); "
                + "-fx-border-color: #cccccc; -fx-border-width: 1; "
                + "-fx-border-radius: 5; -fx-background-radius: 5;");
        
        Label lblTitre = new Label("Type de carte");
        lblTitre.setFont(Font.font("System", FontWeight.BOLD, 12));
        lblTitre.setLayoutX(10);
        lblTitre.setLayoutY(5);
        
        RadioButton rbOSM = new RadioButton("OpenStreetMap");
        rbOSM.setLayoutX(10);
        rbOSM.setLayoutY(25);
        rbOSM.setToggleGroup(tgCartesGluon);
        rbOSM.setSelected(true);
        rbOSM.setOnAction(evt -> changeCarte("OpenStreetMap"));
        
        RadioButton rbEsri = new RadioButton("Satellite (Esri)");
        rbEsri.setLayoutX(10);
        rbEsri.setLayoutY(50);
        rbEsri.setToggleGroup(tgCartesGluon);
        rbEsri.setOnAction(evt -> changeCarte("Esri"));
        
        apChoixCartographie.getChildren().addAll(lblTitre, rbOSM, rbEsri);
        
        // Positionner en haut à droite
        AnchorPane.setTopAnchor(apChoixCartographie, 10.0);
        AnchorPane.setRightAnchor(apChoixCartographie, 10.0);
    }

    /**
     * Layer personnalisé pour gérer les marqueurs
     */
    private class MarkerLayer extends MapLayer {
        private final Map<Integer, Circle> markerNodes = new HashMap<>();
        private final Map<Integer, Label> markerLabels = new HashMap<>();
        
        public void addMarker(int id, MapPoint point, String texte) {
            // Créer un cercle pour le marqueur
            Circle circle = new Circle(8, Color.RED);
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(2);
            
            // Créer un label pour le texte
            Label label = new Label(texte);
            label.setStyle("-fx-background-color: white; -fx-padding: 2px; "
                    + "-fx-border-color: black; -fx-border-width: 1;");
            label.setVisible(false);
            
            markerNodes.put(id, circle);
            markerLabels.put(id, label);
            
            getChildren().addAll(circle, label);
            
            // Interaction au survol
            circle.setOnMouseEntered(e -> label.setVisible(true));
            circle.setOnMouseExited(e -> label.setVisible(false));
            
            markDirty();
        }
        
        public void removeMarker(int id) {
            Circle circle = markerNodes.remove(id);
            Label label = markerLabels.remove(id);
            if (circle != null) {
                getChildren().remove(circle);
            }
            if (label != null) {
                getChildren().remove(label);
            }
            markDirty();
        }
        
        public void clearMarkers() {
            getChildren().clear();
            markerNodes.clear();
            markerLabels.clear();
            markDirty();
        }
        
        @Override
        protected void layoutLayer() {
            for (Map.Entry<Integer, Circle> entry : markerNodes.entrySet()) {
                int id = entry.getKey();
                Circle circle = entry.getValue();
                Label label = markerLabels.get(id);
                
                MapPoint point = marqueurs.get(id);
                if (point != null) {
                    Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
                    circle.setTranslateX(mapPoint.getX());
                    circle.setTranslateY(mapPoint.getY());
                    
                    if (label != null) {
                        label.setTranslateX(mapPoint.getX() + 15);
                        label.setTranslateY(mapPoint.getY() - 10);
                    }
                }
            }
        }
    }

    /**
     * Layer personnalisé pour gérer le radar
     */
    private class RadarLayer extends MapLayer {
        private Circle radarCircle = null;
        private MapPoint radarCenter = null;
        
        public void showRadar(MapPoint center, double angle, double fov, 
                             String couleurFond, double opacite) {
            clearRadar();
            
            radarCenter = center;
            radarCircle = new Circle(50); // Rayon fixe pour l'instant
            radarCircle.setFill(Color.web("#" + couleurFond, opacite));
            radarCircle.setStroke(Color.web("#" + couleurFond));
            radarCircle.setStrokeWidth(2);
            
            getChildren().add(radarCircle);
            markDirty();
        }
        
        public void clearRadar() {
            if (radarCircle != null) {
                getChildren().remove(radarCircle);
                radarCircle = null;
                radarCenter = null;
            }
            markDirty();
        }
        
        @Override
        protected void layoutLayer() {
            if (radarCircle != null && radarCenter != null) {
                Point2D mapPoint = getMapPoint(radarCenter.getLatitude(), 
                                              radarCenter.getLongitude());
                radarCircle.setTranslateX(mapPoint.getX());
                radarCircle.setTranslateY(mapPoint.getY());
            }
        }
    }
}
