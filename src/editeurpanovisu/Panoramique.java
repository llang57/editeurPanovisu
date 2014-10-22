/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;

/**
 * Défintions des vues Panoramiques
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

    private final HotSpot[] hotspots = new HotSpot[100];
    private final HotspotImage[] imageHotspot = new HotspotImage[100];
    private final HotspotHTML[] HTMLHotspot = new HotspotHTML[100];
    private String strTitrePanoramique;
    private String strNomFichier;
    private double regardX = 0.d;
    private double regardY = 0.d;
    private Image imgPanoramique;
    private Image imgVisuPanoramique;
    private Image imgVignettePanoramique;
    private int iNombreHotspots = 0;
    private int iNombreHotspotImage = 0;
    private int iNombreHotspotHTML = 0;
    private String strTypePanoramique;
    private boolean bAfficheTitre;
    private boolean bAfficheInfo;
    private boolean bAffichePlan;
    private int iNumeroPlan = -1;
    private double zeroNord = 0;
    private double nombreNiveaux = 0;

    /**
     *
     * @param strFichier
     */
    public void Panoramique(String strFichier) {
        this.strNomFichier = strFichier;
        this.strTypePanoramique = Panoramique.SPHERE;
        this.bAfficheTitre = true;
        this.bAfficheInfo = true;
    }

    /**
     * @return the affichePlan
     */
    public  boolean isAffichePlan() {
        return bAffichePlan;
    }

    /**
     * @param bAffichePlan the affichePlan to set
     */
    public  void setAffichePlan(boolean bAffichePlan) {
        this.bAffichePlan = bAffichePlan;
    }

    /**
     * @return the nomFichier
     */
    public String getStrNomFichier() {
        return strNomFichier;
    }

    /**
     * @param strNomFichier the nomFichier to set
     */
    public void setStrNomFichier(String strNomFichier) {
        this.strNomFichier = strNomFichier;
    }

    /**
     * @return the lookAtX
     */
    public double getRegardX() {
        return regardX;
    }

    /**
     * @param lookAtX the lookAtX to set
     */
    public void setRegardX(double lookAtX) {
        this.regardX = lookAtX;
    }

    /**
     * @return the lookAtY
     */
    public double getRegardY() {
        return regardY;
    }

    /**
     * @param lookAtY the lookAtY to set
     */
    public void setRegardY(double lookAtY) {
        this.regardY = lookAtY;
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
        this.iNombreHotspots++;
    }

    /**
     *
     * @param num
     */
    public void removeHotspot(int num) {
        for (int i = num; i < this.iNombreHotspots - 1; i++) {
            this.hotspots[i] = this.hotspots[i + 1];
        }
        this.iNombreHotspots--;
        //int nombre
    }

    /**
     * @param i
     * @return le hotspot numero i
     */
    public HotspotImage getHotspotImage(int i) {
        return imageHotspot[i];
    }

    /**
     * @param hotspot the hotspots to set
     * @param i
     */
    public void setHotspotImage(HotspotImage hotspot, int i) {
        this.imageHotspot[i] = hotspot;
    }

    /**
     *
     * @param hotspot
     */
    public void addHotspotImage(HotspotImage hotspot) {
        this.imageHotspot[this.getNombreHotspotImage()] = hotspot;
        this.iNombreHotspotImage++;
    }

    /**
     *
     * @param num
     */
    public void removeHotspotImage(int num) {
        for (int i = num; i < this.iNombreHotspotImage - 1; i++) {
            this.imageHotspot[i] = this.imageHotspot[i + 1];
        }
        this.iNombreHotspotImage--;
        //int nombre
    }

    /**
     * @param i
     * @return le hotspot numero i
     */
    public HotspotHTML getHotspotHTML(int i) {
        return HTMLHotspot[i];
    }

    /**
     * @param hotspot the hotspots to set
     * @param i
     */
    public void setHotspotHTML(HotspotHTML hotspot, int i) {
        this.HTMLHotspot[i] = hotspot;
    }

    /**
     *
     * @param hotspot
     */
    public void addHotspotHTML(HotspotHTML hotspot) {
        this.HTMLHotspot[this.getNombreHotspotHTML()] = hotspot;
        this.iNombreHotspotHTML++;
    }

    /**
     *
     * @param num
     */
    public void removeHotspotHTML(int num) {
        for (int i = num; i < this.iNombreHotspotHTML - 1; i++) {
            this.HTMLHotspot[i] = this.HTMLHotspot[i + 1];
        }
        this.iNombreHotspotHTML--;
        //int nombre
    }

    /**
     * @return the imagePanoramique
     */
    public Image getImgPanoramique() {
        return imgPanoramique;
    }

    /**
     * @param imgPanoramique the imagePanoramique to set
     */
    public void setImgPanoramique(Image imgPanoramique) {
        this.imgPanoramique = imgPanoramique;
    }

    /**
     * @return the nombreHotspots
     */
    public int getNombreHotspots() {
        return iNombreHotspots;
    }

    /**
     * @param iNombreHotspots the nombreHotspots to set
     */
    public void setNombreHotspots(int iNombreHotspots) {
        this.iNombreHotspots = iNombreHotspots;
    }

    /**
     * @return the typePanoramique
     */
    public String getStrTypePanoramique() {
        return strTypePanoramique;
    }

    /**
     * @param strTypePanoramique the typePanoramique to set
     */
    public void setStrTypePanoramique(String strTypePanoramique) {
        this.strTypePanoramique = strTypePanoramique;
    }

    /**
     * @return the afficheTitre
     */
    public boolean isAfficheTitre() {
        return bAfficheTitre;
    }

    /**
     * @param bAfficheTitre the afficheTitre to set
     */
    public void setAfficheTitre(boolean bAfficheTitre) {
        this.bAfficheTitre = bAfficheTitre;
    }

    /**
     * @return the afficheInfo
     */
    public boolean isAfficheInfo() {
        return bAfficheInfo;
    }

    /**
     * @param bAfficheInfo the afficheInfo to set
     */
    public void setAfficheInfo(boolean bAfficheInfo) {
        this.bAfficheInfo = bAfficheInfo;
    }

    /**
     * @return the titrePanoramique
     */
    public String getStrTitrePanoramique() {
        return strTitrePanoramique;
    }

    /**
     * @param strTitrePanoramique the titrePanoramique to set
     */
    public void setStrTitrePanoramique(String strTitrePanoramique) {
        this.strTitrePanoramique = strTitrePanoramique;
    }

    /**
     * @return the vignettePanoramique
     */
    public Image getImgVignettePanoramique() {
        return imgVignettePanoramique;
    }

    /**
     * @param imgVignettePanoramique the vignettePanoramique to set
     */
    public void setImgVignettePanoramique(Image imgVignettePanoramique) {
        this.imgVignettePanoramique = imgVignettePanoramique;
    }

    /**
     * @return the zeroNord
     */
    public double getZeroNord() {
        return zeroNord;
    }

    /**
     * @param zeroNord the zeroNord to set
     */
    public void setZeroNord(double zeroNord) {
        this.zeroNord = zeroNord;
    }

    /**
     * @return the nombreNiveaux
     */
    public double getNombreNiveaux() {
        return nombreNiveaux;
    }

    /**
     * @param nombreNiveaux the nombreNiveaux to set
     */
    public void setNombreNiveaux(double nombreNiveaux) {
        this.nombreNiveaux = nombreNiveaux;
    }

    /**
     * @return the nombreHotspotImage
     */
    public int getNombreHotspotImage() {
        return iNombreHotspotImage;
    }

    /**
     * @param iNombreHotspotImage the nombreHotspotImage to set
     */
    public void setNombreHotspotImage(int iNombreHotspotImage) {
        this.iNombreHotspotImage = iNombreHotspotImage;
    }

    /**
     * @return the nombreHotspotHTML
     */
    public int getNombreHotspotHTML() {
        return iNombreHotspotHTML;
    }

    /**
     * @param iNombreHotspotHTML the nombreHotspotHTML to set
     */
    public void setNombreHotspotHTML(int iNombreHotspotHTML) {
        this.iNombreHotspotHTML = iNombreHotspotHTML;
    }

    /**
     * @return the numeroPlan
     */
    public int getNumeroPlan() {
        return iNumeroPlan;
    }

    /**
     * @param iNumeroPlan the numeroPlan to set
     */
    public void setNumeroPlan(int iNumeroPlan) {
        this.iNumeroPlan = iNumeroPlan;
    }

    /**
     * @return the imgVisuPanoramique
     */
    public Image getImgVisuPanoramique() {
        return imgVisuPanoramique;
    }

    /**
     * @param imgVisuPanoramique the imgVisuPanoramique to set
     */
    public void setImgVisuPanoramique(Image imgVisuPanoramique) {
        this.imgVisuPanoramique = imgVisuPanoramique;
    }

}
