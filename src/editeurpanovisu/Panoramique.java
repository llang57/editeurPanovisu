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
    private final HotspotDiaporama[] diapoHotspot = new HotspotDiaporama[100];
    private String strTitrePanoramique="";
    private String strNomFichier="";
    private double regardX = 0.d;
    private double regardY = 0.d;
    private double champVisuel=75.d;
    private double minLat=-1000;
    private double maxLat=1000;
    private double fovMax=70;
    private double fovMin=12;
    private boolean bMinLat=false;
    private boolean bMaxLat=false;
    private Image imgPanoramique;
    private Image imgVisuPanoramique;
    private Image imgVignettePanoramique;
    private Image imgPanoRect;
    private Image imgPanoRectListe;
    private Image imgCubeEqui;
    private int iNombreHotspots = 0;
    private int iNombreHotspotImage = 0;
    private int iNombreHotspotHTML = 0;
    private int iNombreHotspotDiapo = 0;
    private String strTypePanoramique="";
    private boolean bAfficheTitre;
    private boolean bAffDescription;
    private boolean bAfficheInfo;
    private boolean bAffichePlan;
    private int iNumeroPlan = -1;
    private double zeroNord = 0;
    private double nombreNiveaux = 0;
    private CoordonneesGeographiques marqueurGeolocatisation=null;
    private String strDescriptionIA = "";

   /**
    * 
    * @param strFichier 
    */
    public void Panoramique(String strFichier) {
        this.strNomFichier = strFichier;
        this.strTypePanoramique = Panoramique.SPHERE;
        this.bAfficheTitre = true;
        this.bAffDescription = false;
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
        this.setNombreHotspotHTML(this.getNombreHotspotHTML()+1);
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
     * @param i
     * @return le hotspot numero i
     */
    public HotspotDiaporama getHotspotDiapo(int i) {
        return diapoHotspot[i];
    }

    /**
     * @param hotspot the hotspots to set
     * @param i
     */
    public void setHotspotDiapo(HotspotDiaporama hotspot, int i) {
        this.diapoHotspot[i] = hotspot;
    }

    /**
     *
     * @param hotspot
     */
    public void addHotspotDiapo(HotspotDiaporama hotspot) {
        this.diapoHotspot[this.getiNombreHotspotDiapo()] = hotspot;
        this.setiNombreHotspotDiapo(this.getiNombreHotspotDiapo()+1);
    }

    /**
     *
     * @param num
     */
    public void removeHotspotdiapo(int num) {
        for (int i = num; i < this.iNombreHotspotDiapo - 1; i++) {
            this.diapoHotspot[i] = this.diapoHotspot[i + 1];
        }
        this.setiNombreHotspotDiapo(this.getiNombreHotspotDiapo()-1);
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
     * @return the affDescription
     */
    public boolean isAffDescription() {
        return bAffDescription;
    }

    /**
     * @param bAffDescription the affDescription to set
     */
    public void setAffDescription(boolean bAffDescription) {
        this.bAffDescription = bAffDescription;
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
        return this.imgVisuPanoramique;
    }

    /**
     * @param imgVisuPanoramique the imgVisuPanoramique to set
     */
    public void setImgVisuPanoramique(Image imgVisuPanoramique) {
        this.imgVisuPanoramique = imgVisuPanoramique;
    }

    /**
     * @return the marqueurGeolocatisation
     */
    public CoordonneesGeographiques getMarqueurGeolocatisation() {
        return marqueurGeolocatisation;
    }

    /**
     * @param marqueurGeolocatisation the marqueurGeolocatisation to set
     */
    public void setMarqueurGeolocatisation(CoordonneesGeographiques marqueurGeolocatisation) {
        this.marqueurGeolocatisation = marqueurGeolocatisation;
    }

    /**
     * @return the champVisuel
     */
    public double getChampVisuel() {
        return champVisuel;
    }

    /**
     * @param champVisuel the champVisuel to set
     */
    public void setChampVisuel(double champVisuel) {
        this.champVisuel = champVisuel;
    }

    /**
     * @return the imgPanoRect
     */
    public Image getImgPanoRect() {
        return imgPanoRect;
    }

    /**
     * @param imgPanoRect the imgPanoRect to set
     */
    public void setImgPanoRect(Image imgPanoRect) {
        this.imgPanoRect = imgPanoRect;
    }

    /**
     * @return the minLat
     */
    public double getMinLat() {
        return minLat;
    }

    /**
     * @param minLat the minLat to set
     */
    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    /**
     * @return the maxLat
     */
    public double getMaxLat() {
        return maxLat;
    }

    /**
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * @return the bMinLat
     */
    public boolean isbMinLat() {
        return bMinLat;
    }

    /**
     * @param bMinLat the bMinLat to set
     */
    public void setbMinLat(boolean bMinLat) {
        this.bMinLat = bMinLat;
    }

    /**
     * @return the bMaxLat
     */
    public boolean isbMaxLat() {
        return bMaxLat;
    }

    /**
     * @param bMaxLat the bMaxLat to set
     */
    public void setbMaxLat(boolean bMaxLat) {
        this.bMaxLat = bMaxLat;
    }

    /**
     * @return the iNombreHotspotDiapo
     */
    public int getiNombreHotspotDiapo() {
        return iNombreHotspotDiapo;
    }

    /**
     * @param iNombreHotspotDiapo the iNombreHotspotDiapo to set
     */
    public void setiNombreHotspotDiapo(int iNombreHotspotDiapo) {
        this.iNombreHotspotDiapo = iNombreHotspotDiapo;
    }

    /**
     * @return the imgPanoRectListe
     */
    public Image getImgPanoRectListe() {
        return imgPanoRectListe;
    }

    /**
     * @param imgPanoRectListe the imgPanoRectListe to set
     */
    public void setImgPanoRectListe(Image imgPanoRectListe) {
        this.imgPanoRectListe = imgPanoRectListe;
    }

    /**
     * @return the imgCubeEqui
     */
    public Image getImgCubeEqui() {
        return imgCubeEqui;
    }

    /**
     * @param imgCubeEqui the imgCubeEqui to set
     */
    public void setImgCubeEqui(Image imgCubeEqui) {
        this.imgCubeEqui = imgCubeEqui;
    }

    /**
     * @return the fovMax
     */
    public double getFovMax() {
        return fovMax;
    }

    /**
     * @param fovMax the fovMax to set
     */
    public void setFovMax(double fovMax) {
        this.fovMax = fovMax;
    }

    /**
     * @return the fovMin
     */
    public double getFovMin() {
        return fovMin;
    }

    /**
     * @param fovMin the fovMin to set
     */
    public void setFovMin(double fovMin) {
        this.fovMin = fovMin;
    }

    /**
     * @return the strDescriptionIA
     */
    public String getStrDescriptionIA() {
        return strDescriptionIA != null ? strDescriptionIA : "";
    }

    /**
     * @param strDescriptionIA the strDescriptionIA to set
     */
    public void setStrDescriptionIA(String strDescriptionIA) {
        this.strDescriptionIA = strDescriptionIA != null ? strDescriptionIA : "";
    }

}
