/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 * Définition des hotspots Image
 *
 * @author LANG Laurent
 */
public class HotspotImage {

    private double longitude, latitude;
    private String strNomImg = "";
    private String strUrlImage = "";
    private String strFichierImage = "", strInfo = "";
    private String strCouleurFond="";
    private double opacite=-1;
    private boolean bAnime;

    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        this.setStrLienImg("");
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
    public String getStrLienImg() {
        return strNomImg;
    }

    /**
     * @param strLienImg
     */
    public void setStrLienImg(String strLienImg) {
        this.strNomImg = strLienImg;
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
     * @param anime the anime to set
     */
    public void setAnime(boolean anime) {
        this.bAnime = anime;
    }

    /**
     * @return the urlImage
     */
    public String getStrUrlImage() {
        return strUrlImage;
    }

    /**
     * @param strUrlImage the urlImage to set
     */
    public void setStrUrlImage(String strUrlImage) {
        this.strUrlImage = strUrlImage;
    }

    /**
     * @return the strCouleurFond
     */
    public String getStrCouleurFond() {
        return strCouleurFond;
    }

    /**
     * @param strCouleurFond the strCouleurFond to set
     */
    public void setStrCouleurFond(String strCouleurFond) {
        this.strCouleurFond = strCouleurFond;
    }

    /**
     * @return the opacite
     */
    public double getOpacite() {
        return opacite;
    }

    /**
     * @param opacite the opacite to set
     */
    public void setOpacite(double opacite) {
        this.opacite = opacite;
    }

}
