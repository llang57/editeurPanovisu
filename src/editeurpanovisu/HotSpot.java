/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 * Définition des hotspots panoramiques
 * 
 * @author LANG Laurent
 */
public class HotSpot {

    private double longitude, latitude;
    private String strFichierImage;
    private String strFichierXML;
    private String strInfo;
    private boolean bAnime;
    private int iNumeroPano=-1;

    
    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        this.setStrFichierXML("");
        this.setStrInfo("");
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Number longitude) {
        this.longitude = (double) longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Number latitude) {
        this.latitude = (double) latitude;
    }

    /**
     * @return the fichierImage
     */
    public String getStrFichierImage() {
        return strFichierImage;
    }

    /**
     * @param strFichierImage the fichierImage to set
     */
    public void setStrFichierImage(String strFichierImage) {
        this.strFichierImage = strFichierImage;
    }

    /**
     * @return the fichierXML
     */
    public String getStrFichierXML() {
        return strFichierXML;
    }

    /**
     * @param strFichierXML the fichierXML to set
     */
    public void setStrFichierXML(String strFichierXML) {
        this.strFichierXML = strFichierXML;
    }

    /**
     * @return the info
     */
    public String getStrInfo() {
        return strInfo;
    }

    /**
     * @param strInfo the info to set
     */
    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }

    /**
     * @return the anime
     */
    public boolean isAnime() {
        return bAnime;
    }

    /**
     * @param bAnime the anime to set
     */
    public void setAnime(boolean bAnime) {
        this.bAnime = bAnime;
    }

    /**
     * @return the numeroPano
     */
    public int getNumeroPano() {
        return iNumeroPano;
    }

    /**
     * @param iNumeroPano the numeroPano to set
     */
    public void setNumeroPano(int iNumeroPano) {
        this.iNumeroPano = iNumeroPano;
    }

}
