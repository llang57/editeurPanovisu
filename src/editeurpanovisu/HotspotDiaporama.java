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
public class HotspotDiaporama {

    private double longitude, latitude;
    private int iNumDiapo=-1;
    private String strInfo="";
    private boolean bAnime = false;
    private String strTypeAnimation = "blink"; // Type d'animation par défaut pour les hotspots diaporama
    
    

    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
    }

    /**
     * Retourne la valeur de longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la valeur de longitude.
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(Number longitude) {
        this.longitude = (double) longitude;
    }

    /**
     * Retourne la valeur de latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la valeur de latitude.
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(Number latitude) {
        this.latitude = (double) latitude;
    }

    /**
     * Retourne la valeur de iNumDiapo.
     *
     * @return the iNumDiapo
     */
    public int getiNumDiapo() {
        return iNumDiapo;
    }

    /**
     * Définit la valeur de iNumDiapo.
     *
     * @param iNumDiapo the iNumDiapo to set
     */
    public void setiNumDiapo(int iNumDiapo) {
        this.iNumDiapo = iNumDiapo;
    }

    /**
     * Retourne la valeur de strInfo.
     *
     * @return the strInfo
     */
    public String getStrInfo() {
        return strInfo;
    }

    /**
     * Définit la valeur de strInfo.
     *
     * @param strInfo the strInfo to set
     */
    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }

    /**
     * Retourne la valeur de bAnime.
     *
     * @return the bAnime
     */
    public boolean isbAnime() {
        return bAnime;
    }

    /**
     * Définit la valeur de bAnime.
     *
     * @param bAnime the bAnime to set
     */
    public void setbAnime(boolean bAnime) {
        this.bAnime = bAnime;
    }

    /**
     * Retourne la valeur de strTypeAnimation.
     *
     * @return the strTypeAnimation
     */
    public String getStrTypeAnimation() {
        return strTypeAnimation;
    }

    /**
     * Définit la valeur de strTypeAnimation.
     *
     * @param strTypeAnimation the strTypeAnimation to set
     */
    public void setStrTypeAnimation(String strTypeAnimation) {
        this.strTypeAnimation = strTypeAnimation;
        // Synchroniser bAnime avec strTypeAnimation
        this.bAnime = !("none".equals(strTypeAnimation) || strTypeAnimation == null || strTypeAnimation.isEmpty());
    }

}
