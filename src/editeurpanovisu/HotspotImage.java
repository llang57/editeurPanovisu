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
    private String nomImg;
    private String urlImage;
    private String fichierImage, info;
    private boolean anime;

    
    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        this.setLienImg("");
        this.setInfo("");
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
    public String getFichierImage() {
        return fichierImage;
    }

    /**
     * @param fichierImage the fichierImage to set
     */
    public void setFichierImage(String fichierImage) {
        this.fichierImage = fichierImage;
    }

    /**
     * @return the fichierXML
     */
    public String getLienImg() {
        return nomImg;
    }

    /**
     * @param lienImg
     */
    public void setLienImg(String lienImg) {
        this.nomImg = lienImg;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the anime
     */
    public boolean isAnime() {
        return anime;
    }

    /**
     * @param anime the anime to set
     */
    public void setAnime(boolean anime) {
        this.anime = anime;
    }

    /**
     * @return the urlImage
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * @param urlImage the urlImage to set
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

}
