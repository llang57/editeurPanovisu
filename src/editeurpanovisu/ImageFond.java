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
    
    private String fichierImage;
    private Image imgFond;
    private String posY="bottom",posX="right";
    private double offsetX=0,offsetY=0;
    private int tailleX,tailleY;
    private double opacite=0.8;
    private String url="",infobulle="";
    private boolean masquable=true;

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
    public String getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(String posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public String getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(String posY) {
        this.posY = posY;
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
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
        return masquable;
    }

    /**
     * @param masquable the masquable to set
     */
    public void setMasquable(boolean masquable) {
        this.masquable = masquable;
    }

    /**
     * @return the infobulle
     */
    public String getInfobulle() {
        return infobulle;
    }

    /**
     * @param infobulle the infobulle to set
     */
    public void setInfobulle(String infobulle) {
        this.infobulle = infobulle;
    }

    
    
}
