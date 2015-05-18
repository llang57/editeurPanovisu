/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurHTML.copieFichierRepertoire;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LANG Laurent
 */
public class HotspotHTML {

    private double longitude, latitude;
    private String strInfo = "";
    private boolean bAnime = false;
    private String strURLExterieure = "";
    private boolean bLienExterieur = true;
    private double opaciteHTML = 1;
    private String strTexteHTML = "";
    private double largeurHTML = 1000;
    private String strPositionHTML = "center";
    private String strCouleurHTML = "#aaaaff";
    private ImageEditeurHTML[] imagesEditeur = new ImageEditeurHTML[50];
    private int iNombreImages = 0;

    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        setStrURLExterieure("");
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
     * @return the strURLExterieure
     */
    public String getStrURLExterieure() {
        return strURLExterieure;
    }

    /**
     * @param strURLExterieure the strURLExterieure to set
     */
    public void setStrURLExterieure(String strURLExterieure) {
        this.strURLExterieure = strURLExterieure;
    }

    /**
     * @return the bLienExterieur
     */
    public boolean isbLienExterieur() {
        return bLienExterieur;
    }

    /**
     * @param bLienExterieur the bLienExterieur to set
     */
    public void setbLienExterieur(boolean bLienExterieur) {
        this.bLienExterieur = bLienExterieur;
    }

    /**
     * @return the opaciteHTML
     */
    public double getOpaciteHTML() {
        return opaciteHTML;
    }

    /**
     * @param opaciteHTML the opaciteHTML to set
     */
    public void setOpaciteHTML(double opaciteHTML) {
        this.opaciteHTML = opaciteHTML;
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
     * @return the largeurHTML
     */
    public double getLargeurHTML() {
        return largeurHTML;
    }

    /**
     * @param largeurHTML the largeurHTML to set
     */
    public void setLargeurHTML(double largeurHTML) {
        this.largeurHTML = largeurHTML;
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
     * @return the imagesEditeur
     */
    public ImageEditeurHTML[] getImagesEditeur() {
        return imagesEditeur;
    }

    /**
     * @param imagesEditeur the imagesEditeur to set
     */
    public void setImagesEditeur(ImageEditeurHTML[] imagesEditeur) {
        this.imagesEditeur = imagesEditeur;
    }

    /**
     * @return the iNombreImages
     */
    public int getiNombreImages() {
        return iNombreImages;
    }

    /**
     * @param iNombreImages the iNombreImages to set
     */
    public void setiNombreImages(int iNombreImages) {
        this.iNombreImages = iNombreImages;
    }

    public void creeHTML(String strPageHTML) {
        OutputStreamWriter oswFichierHTML = null;
        try {
            String strPageHTMLImages = strPageHTML + File.separator + "/images";
            File filePageHTML = new File(strPageHTML);
            File filePageHTMLImages = new File(strPageHTMLImages);
            if (!filePageHTML.exists()) {
                filePageHTML.mkdirs();
            }
            if (!filePageHTMLImages.exists()) {
                filePageHTMLImages.mkdirs();
            }
            String strContenuHTML = this.getStrTexteHTML();
            
            System.out.println(" nombre Images : " + this.getiNombreImages());
            File fileIndexHTML = new File(strPageHTML + File.separator + "index.html");
            for (int j = 0; j < this.getiNombreImages(); j++) {
                ImageEditeurHTML img1 = this.getImagesEditeur()[j];
                System.out.println("Remplace :"+"file:////" + img1.getStrImagePath()+"\n par : "+"images/" + img1.getStrNomImage());
                strContenuHTML = strContenuHTML.replace("file:////" + img1.getStrImagePath(), "images/" + img1.getStrNomImage());
                try {
                    copieFichierRepertoire(img1.getStrImagePath(), strPageHTMLImages);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            strContenuHTML = strContenuHTML.replace(">", ">\n");
            fileIndexHTML.setWritable(true);
            System.out.println(fileIndexHTML.getAbsolutePath());
            oswFichierHTML = new OutputStreamWriter(new FileOutputStream(fileIndexHTML), "UTF-8");
            try (BufferedWriter bwFichierHTML = new BufferedWriter(oswFichierHTML)) {
                bwFichierHTML.write(strContenuHTML);

            } catch (IOException ex) {
                Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oswFichierHTML.close();
            } catch (IOException ex) {
                Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
