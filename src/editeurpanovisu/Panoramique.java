/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;

/**
 *
 * @author LANG Laurent
 */
public class Panoramique {

    /**
     * Constante de type de panoramique : Sphere
     */
    public static final String SPHERE = "sphere";

    /**
     * Constante de type de panoramique : Sphere
     */
    public static final String CUBE = "cube";

    private HotSpot[] hotspots = new HotSpot[100];
    private String titrePanoramique;
    private String nomFichier;
    private double lookAtX=0.d;
    private double lookAtY=0.d;
    private Image imagePanoramique;
    private Image vignettePanoramique;
    private int nombreHotspots = 0;
    private String typePanoramique;
    private boolean afficheTitre;
    private boolean afficheInfo;

    /**
     *
     * @param fichier
     */
    public void Panoramique(String fichier) {
        this.nomFichier = fichier;
        this.typePanoramique = Panoramique.SPHERE;
        this.afficheTitre = true;
        this.afficheInfo = true;
    }

    /**
     * @return the nomFichier
     */
    public String getNomFichier() {
        return nomFichier;
    }

    /**
     * @param nomFichier the nomFichier to set
     */
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    /**
     * @return the lookAtX
     */
    public double getLookAtX() {
        return lookAtX;
    }

    /**
     * @param lookAtX the lookAtX to set
     */
    public void setLookAtX(double lookAtX) {
        this.lookAtX = lookAtX;
    }

    /**
     * @return the lookAtY
     */
    public double getLookAtY() {
        return lookAtY;
    }

    /**
     * @param lookAtY the lookAtY to set
     */
    public void setLookAtY(double lookAtY) {
        this.lookAtY = lookAtY;
    }

    /**
     * @param i
     * @return le hotspot numero i
     */
    public HotSpot getHotspot(int i) {
        return hotspots[i];
    }

    /**
     * @param hotspot the hotspots to set
     * @param i
     */
    public void setHotspot(HotSpot hotspot, int i) {
        this.hotspots[i] = hotspot;
    }

    /**
     *
     * @param hotspot
     */
    public void addHotspot(HotSpot hotspot) {
        this.hotspots[this.getNombreHotspots()] = hotspot;
        this.nombreHotspots++;
    }

    /**
     *
     * @param num
     */
    public void removeHotspot(int num) {
        for (int i = num; i < this.nombreHotspots - 1; i++) {
            this.hotspots[i] = this.hotspots[i + 1];
        }
        this.nombreHotspots--;
        //int nombre
    }

    /**
     * @return the imagePanoramique
     */
    public Image getImagePanoramique() {
        return imagePanoramique;
    }

    /**
     * @param imagePanoramique the imagePanoramique to set
     */
    public void setImagePanoramique(Image imagePanoramique) {
        this.imagePanoramique = imagePanoramique;
    }

    /**
     * @return the nombreHotspots
     */
    public int getNombreHotspots() {
        return nombreHotspots;
    }

    /**
     * @param nombreHotspots the nombreHotspots to set
     */
    public void setNombreHotspots(int nombreHotspots) {
        this.nombreHotspots = nombreHotspots;
    }

    /**
     * @return the typePanoramique
     */
    public String getTypePanoramique() {
        return typePanoramique;
    }

    /**
     * @param typePanoramique the typePanoramique to set
     */
    public void setTypePanoramique(String typePanoramique) {
        this.typePanoramique = typePanoramique;
    }

    /**
     * @return the afficheTitre
     */
    public boolean isAfficheTitre() {
        return afficheTitre;
    }

    /**
     * @param afficheTitre the afficheTitre to set
     */
    public void setAfficheTitre(boolean afficheTitre) {
        this.afficheTitre = afficheTitre;
    }

    /**
     * @return the afficheInfo
     */
    public boolean isAfficheInfo() {
        return afficheInfo;
    }

    /**
     * @param afficheInfo the afficheInfo to set
     */
    public void setAfficheInfo(boolean afficheInfo) {
        this.afficheInfo = afficheInfo;
    }

    /**
     * @return the titrePanoramique
     */
    public String getTitrePanoramique() {
        return titrePanoramique;
    }

    /**
     * @param titrePanoramique the titrePanoramique to set
     */
    public void setTitrePanoramique(String titrePanoramique) {
        this.titrePanoramique = titrePanoramique;
    }

    /**
     * @return the vignettePanoramique
     */
    public Image getVignettePanoramique() {
        return vignettePanoramique;
    }

    /**
     * @param vignettePanoramique the vignettePanoramique to set
     */
    public void setVignettePanoramique(Image vignettePanoramique) {
        this.vignettePanoramique = vignettePanoramique;
    }

}
