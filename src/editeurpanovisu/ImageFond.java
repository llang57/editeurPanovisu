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
    
    private String strFichierImage;
    private Image imgFond;
    private String strPosY="bottom",strPosX="right";
    private double offsetX=0,offsetY=0;
    private int tailleX,tailleY;
    private double opacite=0.8;
    private String strUrl="",strInfobulle="";
    private boolean bMasquable=true;

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
     * @return the imgFond
     */
    public Image getImgFond() {
        return imgFond;
    }

    /**
     * @param imgFond the imgFond to set
     */
    public void setImgFond(Image imgFond) {
        this.imgFond = imgFond;
    }

    /**
     * @return the posX
     */
    public String getStrPosX() {
        return strPosX;
    }

    /**
     * @param strPosX the posX to set
     */
    public void setStrPosX(String strPosX) {
        this.strPosX = strPosX;
    }

    /**
     * @return the posY
     */
    public String getStrPosY() {
        return strPosY;
    }

    /**
     * @param strPosY the posY to set
     */
    public void setStrPosY(String strPosY) {
        this.strPosY = strPosY;
    }

    /**
     * @return the offsetX
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * @param offsetX the offsetX to set
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * @return the offsetY
     */
    public double getOffsetY() {
        return offsetY;
    }

    /**
     * @param offsetY the offsetY to set
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * @return the url
     */
    public String getStrUrl() {
        return strUrl;
    }

    /**
     * @param strUrl the url to set
     */
    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
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

    /**
     * @return the tailleX
     */
    public int getTailleX() {
        return tailleX;
    }

    /**
     * @param tailleX the tailleX to set
     */
    public void setTailleX(int tailleX) {
        this.tailleX = tailleX;
    }

    /**
     * @return the tailleY
     */
    public int getTailleY() {
        return tailleY;
    }

    /**
     * @param tailleY the tailleY to set
     */
    public void setTailleY(int tailleY) {
        this.tailleY = tailleY;
    }

    /**
     * @return the masquable
     */
    public boolean isMasquable() {
        return bMasquable;
    }

    /**
     * @param masquable the masquable to set
     */
    public void setMasquable(boolean masquable) {
        this.bMasquable = masquable;
    }

    /**
     * @return the infobulle
     */
    public String getStrInfobulle() {
        return strInfobulle;
    }

    /**
     * @param strInfobulle the infobulle to set
     */
    public void setStrInfobulle(String strInfobulle) {
        this.strInfobulle = strInfobulle;
    }

    
    
}
