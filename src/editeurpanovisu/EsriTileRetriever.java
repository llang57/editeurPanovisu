/*
 * Copyright (c) 2014-2025 Laurent LANG
 * Éditeur PanoVisu - Version 3.0
 * 
 * TileRetriever personnalisé pour Esri World Imagery
 */
package editeurpanovisu;

import javafx.scene.image.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TileRetriever pour les tuiles Esri World Imagery (satellite)
 * URL: https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}
 * 
 * Note: Gluon Maps charge les tuiles via Image(url) directement
 * 
 * @author LANG Laurent
 */
public class EsriTileRetriever {
    
    private static final Logger LOGGER = Logger.getLogger(EsriTileRetriever.class.getName());
    private static final String ESRI_TILE_URL = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/";
    
    /**
     * Construit l'URL d'une tuile Esri
     * 
     * @param zoom Niveau de zoom
     * @param x Coordonnée X de la tuile
     * @param y Coordonnée Y de la tuile
     * @return URL de la tuile
     */
    public static String getTileUrl(int zoom, long x, long y) {
        // Format de l'URL Esri: .../tile/{z}/{y}/{x}
        return ESRI_TILE_URL + zoom + "/" + y + "/" + x;
    }

    /**
     * Charge une tuile Esri
     * 
     * @param zoom Niveau de zoom
     * @param x Coordonnée X de la tuile
     * @param y Coordonnée Y de la tuile
     * @return Image de la tuile
     */
    public static Image loadTile(int zoom, long x, long y) {
        try {
            String urlString = getTileUrl(zoom, x, y);
            Image image = new Image(urlString, true); // true = background loading
            
            if (image.isError()) {
                LOGGER.log(Level.WARNING, "Erreur lors du chargement de la tuile Esri: " + urlString);
            }
            
            return image;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Impossible de charger la tuile Esri [" + zoom + ", " + x + ", " + y + "]: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtenir l'attribution pour Esri
     * 
     * @return Texte d'attribution
     */
    public static String getAttribution() {
        return "© Esri, DigitalGlobe, GeoEye, Earthstar Geographics, CNES/Airbus DS, USDA, USGS, AeroGRID, IGN, and the GIS User Community";
    }
}
