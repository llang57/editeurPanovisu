/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;

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
    private String strTypeAnimation = "none"; // Type d'animation: none, pulse, rotation, etc.
    private boolean bAgranditSurvol = false;
    private String strCouleurPerso = ""; // Couleur personnalisée pour ce hotspot (vide = utiliser la couleur par défaut)
    private String strNomIconeSource = ""; // Nom du fichier d'icône source original (avant coloration)
    private int iTailleHotspot = 32; // Taille du hotspot en pixels (par défaut 32)
    private Image imgIconeSource;  // Image source pour ce hotspot spécifique

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
     * Retourne la valeur de fichierImage.
     *
     * @return the fichierImage
     */
    public String getStrFichierImage() {
        return strFichierImage;
    }

    /**
     * Définit la valeur de fichierImage.
     *
     * @param strFichierImage the fichierImage to set
     */
    public void setStrFichierImage(String strFichierImage) {
        this.strFichierImage = strFichierImage;
    }

    /**
     * Retourne la valeur de fichierXML.
     *
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
     * Retourne la valeur de info.
     *
     * @return the info
     */
    public String getStrInfo() {
        return strInfo;
    }

    /**
     * Définit la valeur de info.
     *
     * @param strInfo the info to set
     */
    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }

    /**
     * Retourne la valeur de anime.
     *
     * @return the anime
     */
    public boolean isAnime() {
        return bAnime;
    }

    /**
     * Définit la valeur de anime.
     *
     * @param anime the anime to set
     */
    public void setAnime(boolean anime) {
        this.bAnime = anime;
        // Compatibilité: si on active l'animation sans type spécifique, utiliser "pulse" par défaut
        if (anime && "none".equals(strTypeAnimation)) {
            strTypeAnimation = "pulse";
        } else if (!anime) {
            strTypeAnimation = "none";
        }
    }

    /**
     * Retourne la valeur de typeAnimation.
     *
     * @return the typeAnimation
     */
    public String getStrTypeAnimation() {
        return strTypeAnimation;
    }

    /**
     * Définit la valeur de typeAnimation.
     *
     * @param strTypeAnimation the typeAnimation to set
     */
    public void setStrTypeAnimation(String strTypeAnimation) {
        this.strTypeAnimation = strTypeAnimation;
        // Synchroniser avec bAnime
        this.bAnime = !("none".equals(strTypeAnimation) || strTypeAnimation == null || strTypeAnimation.isEmpty());
    }

    /**
     * Retourne la valeur de agranditSurvol.
     *
     * @return the agranditSurvol
     */
    public boolean isAgranditSurvol() {
        return bAgranditSurvol;
    }

    /**
     * Définit la valeur de agranditSurvol.
     *
     * @param bAgranditSurvol the agranditSurvol to set
     */
    public void setAgranditSurvol(boolean bAgranditSurvol) {
        this.bAgranditSurvol = bAgranditSurvol;
    }

    /**
     * Retourne la valeur de urlImage.
     *
     * @return the urlImage
     */
    public String getStrUrlImage() {
        return strUrlImage;
    }

    /**
     * Définit la valeur de urlImage.
     *
     * @param strUrlImage the urlImage to set
     */
    public void setStrUrlImage(String strUrlImage) {
        this.strUrlImage = strUrlImage;
    }

    /**
     * Retourne la valeur de strCouleurFond.
     *
     * @return the strCouleurFond
     */
    public String getStrCouleurFond() {
        return strCouleurFond;
    }

    /**
     * Définit la valeur de strCouleurFond.
     *
     * @param strCouleurFond the strCouleurFond to set
     */
    public void setStrCouleurFond(String strCouleurFond) {
        this.strCouleurFond = strCouleurFond;
    }

    /**
     * Retourne la valeur de opacite.
     *
     * @return the opacite
     */
    public double getOpacite() {
        return opacite;
    }

    /**
     * Définit la valeur de opacite.
     *
     * @param opacite the opacite to set
     */
    public void setOpacite(double opacite) {
        this.opacite = opacite;
    }

    /**
     * Retourne la valeur de couleurPerso.
     *
     * @return the couleurPerso
     */
    public String getStrCouleurPerso() {
        return strCouleurPerso;
    }

    /**
     * Définit la valeur de couleurPerso.
     *
     * @param strCouleurPerso the couleurPerso to set (format: "hue;saturation;brightness" ou vide pour couleur par défaut)
     */
    public void setStrCouleurPerso(String strCouleurPerso) {
        this.strCouleurPerso = strCouleurPerso;
    }

    /**
     * Retourne la valeur de imgIconeSource.
     *
     * @return the imgIconeSource
     */
    public Image getImgIconeSource() {
        return imgIconeSource;
    }

    /**
     * Définit la valeur de imgIconeSource.
     *
     * @param imgIconeSource the imgIconeSource to set
     */
    public void setImgIconeSource(Image imgIconeSource) {
        this.imgIconeSource = imgIconeSource;
    }

    /**
     * Retourne la valeur de strNomIconeSource.
     *
     * @return the strNomIconeSource - Nom du fichier d'icône source original
     */
    public String getStrNomIconeSource() {
        return strNomIconeSource;
    }

    /**
     * Définit la valeur de strNomIconeSource.
     *
     * @param strNomIconeSource the strNomIconeSource to set - Nom du fichier d'icône source original
     */
    public void setStrNomIconeSource(String strNomIconeSource) {
        this.strNomIconeSource = strNomIconeSource;
    }

    /**
     * Retourne la valeur de iTailleHotspot.
     *
     * @return the iTailleHotspot - Taille du hotspot en pixels
     */
    public int getTailleHotspot() {
        return iTailleHotspot;
    }

    /**
     * Définit la valeur de iTailleHotspot.
     *
     * @param iTailleHotspot the iTailleHotspot to set - Taille du hotspot en pixels
     */
    public void setTailleHotspot(int iTailleHotspot) {
        this.iTailleHotspot = iTailleHotspot;
    }

}
