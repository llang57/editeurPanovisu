/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;

/**
 *
 * @author laure_000
 */
public class ImageFond {

    private String strFichierImage = "";
    private Image imgFond;
    private String strPosY = "bottom", strPosX = "right";
    private double offsetX = 0, offsetY = 0;
    private int tailleX, tailleY;
    private double opacite = 0.8;
    private String strUrl = "", strInfobulle = "";
    private boolean bMasquable = true;
    private String strType="aucun";
    private int iNumDiapo=-1;
    private String strCible="interne";
    private int iCalqueImage=1;

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
     * Retourne la valeur de imgFond.
     *
     * @return the imgFond
     */
    public Image getImgFond() {
        return imgFond;
    }

    /**
     * Définit la valeur de imgFond.
     *
     * @param imgFond the imgFond to set
     */
    public void setImgFond(Image imgFond) {
        this.imgFond = imgFond;
    }

    /**
     * Retourne la valeur de posX.
     *
     * @return the posX
     */
    public String getStrPosX() {
        return strPosX;
    }

    /**
     * Définit la valeur de posX.
     *
     * @param strPosX the posX to set
     */
    public void setStrPosX(String strPosX) {
        this.strPosX = strPosX;
    }

    /**
     * Retourne la valeur de posY.
     *
     * @return the posY
     */
    public String getStrPosY() {
        return strPosY;
    }

    /**
     * Définit la valeur de posY.
     *
     * @param strPosY the posY to set
     */
    public void setStrPosY(String strPosY) {
        this.strPosY = strPosY;
    }

    /**
     * Retourne la valeur de offsetX.
     *
     * @return the offsetX
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * Définit la valeur de offsetX.
     *
     * @param offsetX the offsetX to set
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Retourne la valeur de offsetY.
     *
     * @return the offsetY
     */
    public double getOffsetY() {
        return offsetY;
    }

    /**
     * Définit la valeur de offsetY.
     *
     * @param offsetY the offsetY to set
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Retourne la valeur de url.
     *
     * @return the url
     */
    public String getStrUrl() {
        return strUrl;
    }

    /**
     * Définit la valeur de url.
     *
     * @param strUrl the url to set
     */
    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
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
     * Retourne la valeur de tailleX.
     *
     * @return the tailleX
     */
    public int getTailleX() {
        return tailleX;
    }

    /**
     * Définit la valeur de tailleX.
     *
     * @param tailleX the tailleX to set
     */
    public void setTailleX(int tailleX) {
        this.tailleX = tailleX;
    }

    /**
     * Retourne la valeur de tailleY.
     *
     * @return the tailleY
     */
    public int getTailleY() {
        return tailleY;
    }

    /**
     * Définit la valeur de tailleY.
     *
     * @param tailleY the tailleY to set
     */
    public void setTailleY(int tailleY) {
        this.tailleY = tailleY;
    }

    /**
     * Retourne la valeur de masquable.
     *
     * @return the masquable
     */
    public boolean isMasquable() {
        return bMasquable;
    }

    /**
     * Définit la valeur de masquable.
     *
     * @param masquable the masquable to set
     */
    public void setMasquable(boolean masquable) {
        this.bMasquable = masquable;
    }

    /**
     * Retourne la valeur de infobulle.
     *
     * @return the infobulle
     */
    public String getStrInfobulle() {
        return strInfobulle;
    }

    /**
     * Définit la valeur de infobulle.
     *
     * @param strInfobulle the infobulle to set
     */
    public void setStrInfobulle(String strInfobulle) {
        this.strInfobulle = strInfobulle;
    }

    /**
     * Retourne la valeur de strType.
     *
     * @return the strType
     */
    public String getStrType() {
        return strType;
    }

    /**
     * Définit la valeur de strType.
     *
     * @param strType the strType to set
     */
    public void setStrType(String strType) {
        this.strType = strType;
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
     * Retourne la valeur de strCible.
     *
     * @return the strCible
     */
    public String getStrCible() {
        return strCible;
    }

    /**
     * Définit la valeur de strCible.
     *
     * @param strCible the strCible to set
     */
    public void setStrCible(String strCible) {
        this.strCible = strCible;
    }

    /**
     * Retourne la valeur de iCalqueImage.
     *
     * @return the iCalqueImage
     */
    public int getiCalqueImage() {
        return iCalqueImage;
    }

    /**
     * Définit la valeur de iCalqueImage.
     *
     * @param iCalqueImage the iCalqueImage to set
     */
    public void setiCalqueImage(int iCalqueImage) {
        this.iCalqueImage = iCalqueImage;
    }

}
