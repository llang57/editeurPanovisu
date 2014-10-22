/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 *
 * @author LANG Laurent
 */
public class HotspotHTML {

    private double longitude, latitude;
    private String strUrl, strInfo;
    private boolean bAnime;
    private boolean bHTMLInterne = false;
    private String strTexteHTML;
    private double tailleHTML;
    private String strPositionHTML;
    private String strCouleurHTML;
    private String[] strFichierImage;

    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        this.setStrUrl("");
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
     *
     * @return
     */
    public String getStrUrl() {
        return strUrl;
    }

    /**
     * @param strUrl
     */
    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
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
     * @return the strTexteHTML
     */
    public String getStrTexteHTML() {
        return strTexteHTML;
    }

    /**
     * @param strTexteHTML the strTexteHTML to set
     */
    public void setStrTexteHTML(String strTexteHTML) {
        this.strTexteHTML = strTexteHTML;
    }

    /**
     * @return the tailleHTML
     */
    public double getTailleHTML() {
        return tailleHTML;
    }

    /**
     * @param tailleHTML the tailleHTML to set
     */
    public void setTailleHTML(double tailleHTML) {
        this.tailleHTML = tailleHTML;
    }

    /**
     * @return the strPositionHTML
     */
    public String getStrPositionHTML() {
        return strPositionHTML;
    }

    /**
     * @param strPositionHTML the strPositionHTML to set
     */
    public void setStrPositionHTML(String strPositionHTML) {
        this.strPositionHTML = strPositionHTML;
    }

    /**
     * @return the strCouleurHTML
     */
    public String getStrCouleurHTML() {
        return strCouleurHTML;
    }

    /**
     * @param strCouleurHTML the strCouleurHTML to set
     */
    public void setStrCouleurHTML(String strCouleurHTML) {
        this.strCouleurHTML = strCouleurHTML;
    }

    /**
     * @return the strFichierImage
     */
    public String[] getStrFichierImage() {
        return strFichierImage;
    }

    /**
     * @param strFichierImage the strFichierImage to set
     */
    public void setStrFichierImage(String[] strFichierImage) {
        this.strFichierImage = strFichierImage;
    }

    /**
     *
     * @param strFichierImage
     * @param i
     */
    public void setStrFichierImageI(String strFichierImage, int i) {
        this.strFichierImage[i] = strFichierImage;
    }

    /**
     *
     * @param i
     * @return
     */
    public String getStrFichierImageI(int i) {
        return this.strFichierImage[i];
    }

    /**
     * @return the bHTMLInterne
     */
    public boolean isbHTMLInterne() {
        return bHTMLInterne;
    }

    /**
     * @param bHTMLInterne the bHTMLInterne to set
     */
    public void setbHTMLInterne(boolean bHTMLInterne) {
        this.bHTMLInterne = bHTMLInterne;
    }

}
