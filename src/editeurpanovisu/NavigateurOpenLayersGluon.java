/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.layout.AnchorPane;

/**
 * Version de NavigateurOpenLayers utilisant Gluon Maps au lieu de WebView
 * Compatible avec l'API existante pour faciliter la migration
 * 
 * @author LANG Laurent
 */
public class NavigateurOpenLayersGluon {

    public AnchorPane apOpenLayers;
    private NavigateurCarteGluon navigateurCarte;
    private boolean bCarteChargee = false;

    /**
     * Affiche le navigateur de carte avec Gluon Maps
     * @return AnchorPane contenant la carte
     */
    public AnchorPane afficheNavigateurOpenLayer() {
        apOpenLayers = new AnchorPane();
        apOpenLayers.setPrefHeight(600);
        apOpenLayers.setPrefWidth(800);
        apOpenLayers.setStyle("-fx-background-color: #f0f0f0;");

        // Créer le navigateur de carte Gluon
        navigateurCarte = new NavigateurCarteGluon();
        navigateurCarte.setLayoutX(0);
        navigateurCarte.setLayoutY(0);
        
        apOpenLayers.getChildren().add(navigateurCarte);

        // Lier la taille de la carte à celle du conteneur
        navigateurCarte.prefWidthProperty().bind(apOpenLayers.widthProperty());
        navigateurCarte.prefHeightProperty().bind(apOpenLayers.heightProperty());
        navigateurCarte.getMapView().prefWidthProperty().bind(navigateurCarte.widthProperty());
        navigateurCarte.getMapView().prefHeightProperty().bind(navigateurCarte.heightProperty());

        bCarteChargee = true;
        System.out.println("✅ NavigateurOpenLayersGluon initialisé");

        return apOpenLayers;
    }

    /**
     * Centre la carte sur des coordonnées
     */
    public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacteurZoom) {
        if (navigateurCarte != null) {
            navigateurCarte.allerCoordonnees(
                String.valueOf(coordonnees.getLatitude()),
                String.valueOf(coordonnees.getLongitude())
            );
            if (iFacteurZoom > 0) {
                navigateurCarte.choixZoom(iFacteurZoom);
            }
        }
    }

    /**
     * Récupère les coordonnées du centre de la carte
     */
    public String[] recupereCoordonnees() {
        if (navigateurCarte != null) {
            return navigateurCarte.recupereCoordonnees();
        }
        return new String[]{"0", "0"};
    }

    /**
     * Supprime un marqueur
     */
    public void retireMarqueur(int iNumeroMarqueur) {
        if (navigateurCarte != null) {
            navigateurCarte.retireMarqueur(iNumeroMarqueur);
        }
    }

    /**
     * Ajoute un marqueur sur la carte
     */
    public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coordMarqueur, 
                               String strHTML, String strImage, int intEpaisseur) {
        if (navigateurCarte != null) {
            navigateurCarte.ajouteMarqueur(
                String.valueOf(coordMarqueur.getLatitude()),
                String.valueOf(coordMarqueur.getLongitude()),
                strImage,
                strHTML,
                intEpaisseur
            );
        }
    }

    /**
     * Centre la carte sur une adresse
     */
    public void allerAdresse(String strAdresse, int iFacteurZoom) {
        if (navigateurCarte != null) {
            navigateurCarte.allerAdresse(strAdresse);
            if (iFacteurZoom > 0) {
                navigateurCarte.choixZoom(iFacteurZoom);
            }
        }
    }

    /**
     * Définit le niveau de zoom
     */
    public void choixZoom(int iFacteurZoom) {
        if (navigateurCarte != null) {
            navigateurCarte.choixZoom(iFacteurZoom);
        }
    }

    /**
     * Définit la clé API Bing (non applicable avec Gluon Maps/OSM)
     */
    public void setBingApiKey(String strBingApiKey) {
        // Non applicable avec Gluon Maps qui utilise OpenStreetMap
        System.out.println("ℹ️ Bing API Key ignorée (Gluon Maps utilise OpenStreetMap)");
    }

    /**
     * Récupère la liste des types de cartes disponibles
     */
    public String recupereCartographiesOpenLayers() {
        if (navigateurCarte != null) {
            return navigateurCarte.recupereCartographiesOpenLayers();
        }
        return "OpenStreetMap";
    }

    /**
     * Change le type de carte
     */
    public void changeCarte(String strCarto) {
        if (navigateurCarte != null) {
            navigateurCarte.changeCarte(strCarto);
        }
    }

    /**
     * Affiche le radar (fonctionnalité à implémenter)
     */
    public void afficheRadar(double dlong1, double dlat1, double dlong2, double dlat2,
                            double dlong3, double dlat3, double dlong4, double dlat4,
                            double dlongPosition, double dlatPosition) {
        System.out.println("⚠️ afficheRadar() non implémenté dans Gluon Maps");
        // TODO: Implémenter avec des polygones et markers sur la carte
    }

    /**
     * Vérifie si la carte est chargée
     */
    public boolean isCarteChargee() {
        return bCarteChargee;
    }

    /**
     * Obtient l'instance de NavigateurCarteGluon
     */
    public NavigateurCarteGluon getNavigateurCarte() {
        return navigateurCarte;
    }
}
