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
    
    // Cache des faces du cube pré-calculées pour optimiser l'affichage
    private Image[] cubeFacesPetiteResolution = null;  // 6 faces 500×500
    private Image[] cubeFacesGrandeResolution = null;  // 6 faces 1000×1000
    private Image imgHauteResolution = null;  // Image transformée haute résolution (iRapport=1)
    
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
     * Retourne la valeur de affichePlan.
     *
     * @return the affichePlan
     */
    public  boolean isAffichePlan() {
        return bAffichePlan;
    }

    /**
     * Définit la valeur de affichePlan.
     *
     * @param bAffichePlan the affichePlan to set
     */
    public  void setAffichePlan(boolean bAffichePlan) {
        this.bAffichePlan = bAffichePlan;
    }

    /**
     * Retourne la valeur de nomFichier.
     *
     * @return the nomFichier
     */
    public String getStrNomFichier() {
        return strNomFichier;
    }

    /**
     * Définit la valeur de nomFichier.
     *
     * @param strNomFichier the nomFichier to set
     */
    public void setStrNomFichier(String strNomFichier) {
        this.strNomFichier = strNomFichier;
    }

    /**
     * Retourne la valeur de lookAtX.
     *
     * @return the lookAtX
     */
    public double getRegardX() {
        return regardX;
    }

    /**
     * Définit la valeur de lookAtX.
     *
     * @param lookAtX the lookAtX to set
     */
    public void setRegardX(double lookAtX) {
        this.regardX = lookAtX;
    }

    /**
     * Retourne la valeur de lookAtY.
     *
     * @return the lookAtY
     */
    public double getRegardY() {
        return regardY;
    }

    /**
     * Définit la valeur de lookAtY.
     *
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
     * Définit la valeur de hotspots.
     *
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
     * Définit la valeur de hotspots.
     *
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
     * Définit la valeur de hotspots.
     *
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
     * Définit la valeur de hotspots.
     *
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
     * Retourne la valeur de imagePanoramique.
     *
     * @return the imagePanoramique
     */
    public Image getImgPanoramique() {
        return imgPanoramique;
    }

    /**
     * Définit la valeur de imagePanoramique.
     *
     * @param imgPanoramique the imagePanoramique to set
     */
    public void setImgPanoramique(Image imgPanoramique) {
        this.imgPanoramique = imgPanoramique;
    }

    /**
     * Retourne la valeur de nombreHotspots.
     *
     * @return the nombreHotspots
     */
    public int getNombreHotspots() {
        return iNombreHotspots;
    }

    /**
     * Définit la valeur de nombreHotspots.
     *
     * @param iNombreHotspots the nombreHotspots to set
     */
    public void setNombreHotspots(int iNombreHotspots) {
        this.iNombreHotspots = iNombreHotspots;
    }

    /**
     * Retourne la valeur de typePanoramique.
     *
     * @return the typePanoramique
     */
    public String getStrTypePanoramique() {
        return strTypePanoramique;
    }

    /**
     * Définit la valeur de typePanoramique.
     *
     * @param strTypePanoramique the typePanoramique to set
     */
    public void setStrTypePanoramique(String strTypePanoramique) {
        this.strTypePanoramique = strTypePanoramique;
    }

    /**
     * Retourne la valeur de afficheTitre.
     *
     * @return the afficheTitre
     */
    public boolean isAfficheTitre() {
        return bAfficheTitre;
    }

    /**
     * Définit la valeur de afficheTitre.
     *
     * @param bAfficheTitre the afficheTitre to set
     */
    public void setAfficheTitre(boolean bAfficheTitre) {
        this.bAfficheTitre = bAfficheTitre;
    }

    /**
     * Retourne la valeur de affDescription.
     *
     * @return the affDescription
     */
    public boolean isAffDescription() {
        return bAffDescription;
    }

    /**
     * Définit la valeur de affDescription.
     *
     * @param bAffDescription the affDescription to set
     */
    public void setAffDescription(boolean bAffDescription) {
        this.bAffDescription = bAffDescription;
    }

    /**
     * Retourne la valeur de afficheInfo.
     *
     * @return the afficheInfo
     */
    public boolean isAfficheInfo() {
        return bAfficheInfo;
    }

    /**
     * Définit la valeur de afficheInfo.
     *
     * @param bAfficheInfo the afficheInfo to set
     */
    public void setAfficheInfo(boolean bAfficheInfo) {
        this.bAfficheInfo = bAfficheInfo;
    }

    /**
     * Retourne la valeur de titrePanoramique.
     *
     * @return the titrePanoramique
     */
    public String getStrTitrePanoramique() {
        return strTitrePanoramique;
    }

    /**
     * Définit la valeur de titrePanoramique.
     *
     * @param strTitrePanoramique the titrePanoramique to set
     */
    public void setStrTitrePanoramique(String strTitrePanoramique) {
        this.strTitrePanoramique = strTitrePanoramique;
    }

    /**
     * Retourne la valeur de vignettePanoramique.
     *
     * @return the vignettePanoramique
     */
    public Image getImgVignettePanoramique() {
        return imgVignettePanoramique;
    }

    /**
     * Définit la valeur de vignettePanoramique.
     *
     * @param imgVignettePanoramique the vignettePanoramique to set
     */
    public void setImgVignettePanoramique(Image imgVignettePanoramique) {
        this.imgVignettePanoramique = imgVignettePanoramique;
    }

    /**
     * Retourne la valeur de zeroNord.
     *
     * @return the zeroNord
     */
    public double getZeroNord() {
        return zeroNord;
    }

    /**
     * Définit la valeur de zeroNord.
     *
     * @param zeroNord the zeroNord to set
     */
    public void setZeroNord(double zeroNord) {
        this.zeroNord = zeroNord;
    }

    /**
     * Retourne la valeur de nombreNiveaux.
     *
     * @return the nombreNiveaux
     */
    public double getNombreNiveaux() {
        return nombreNiveaux;
    }

    /**
     * Définit la valeur de nombreNiveaux.
     *
     * @param nombreNiveaux the nombreNiveaux to set
     */
    public void setNombreNiveaux(double nombreNiveaux) {
        this.nombreNiveaux = nombreNiveaux;
    }

    /**
     * Retourne la valeur de nombreHotspotImage.
     *
     * @return the nombreHotspotImage
     */
    public int getNombreHotspotImage() {
        return iNombreHotspotImage;
    }

    /**
     * Définit la valeur de nombreHotspotImage.
     *
     * @param iNombreHotspotImage the nombreHotspotImage to set
     */
    public void setNombreHotspotImage(int iNombreHotspotImage) {
        this.iNombreHotspotImage = iNombreHotspotImage;
    }

    /**
     * Retourne la valeur de nombreHotspotHTML.
     *
     * @return the nombreHotspotHTML
     */
    public int getNombreHotspotHTML() {
        return iNombreHotspotHTML;
    }

    /**
     * Définit la valeur de nombreHotspotHTML.
     *
     * @param iNombreHotspotHTML the nombreHotspotHTML to set
     */
    public void setNombreHotspotHTML(int iNombreHotspotHTML) {
        this.iNombreHotspotHTML = iNombreHotspotHTML;
    }

    /**
     * Retourne la valeur de numeroPlan.
     *
     * @return the numeroPlan
     */
    public int getNumeroPlan() {
        return iNumeroPlan;
    }

    /**
     * Définit la valeur de numeroPlan.
     *
     * @param iNumeroPlan the numeroPlan to set
     */
    public void setNumeroPlan(int iNumeroPlan) {
        this.iNumeroPlan = iNumeroPlan;
    }

    /**
     * Retourne la valeur de imgVisuPanoramique.
     *
     * @return the imgVisuPanoramique
     */
    public Image getImgVisuPanoramique() {
        return this.imgVisuPanoramique;
    }

    /**
     * Définit la valeur de imgVisuPanoramique.
     *
     * @param imgVisuPanoramique the imgVisuPanoramique to set
     */
    public void setImgVisuPanoramique(Image imgVisuPanoramique) {
        this.imgVisuPanoramique = imgVisuPanoramique;
    }

    /**
     * Retourne la valeur de marqueurGeolocatisation.
     *
     * @return the marqueurGeolocatisation
     */
    public CoordonneesGeographiques getMarqueurGeolocatisation() {
        return marqueurGeolocatisation;
    }

    /**
     * Définit la valeur de marqueurGeolocatisation.
     *
     * @param marqueurGeolocatisation the marqueurGeolocatisation to set
     */
    public void setMarqueurGeolocatisation(CoordonneesGeographiques marqueurGeolocatisation) {
        this.marqueurGeolocatisation = marqueurGeolocatisation;
    }

    /**
     * Retourne la valeur de champVisuel.
     *
     * @return the champVisuel
     */
    public double getChampVisuel() {
        return champVisuel;
    }

    /**
     * Définit la valeur de champVisuel.
     *
     * @param champVisuel the champVisuel to set
     */
    public void setChampVisuel(double champVisuel) {
        this.champVisuel = champVisuel;
    }

    /**
     * Retourne la valeur de imgPanoRect.
     *
     * @return the imgPanoRect
     */
    public Image getImgPanoRect() {
        return imgPanoRect;
    }

    /**
     * Définit la valeur de imgPanoRect.
     *
     * @param imgPanoRect the imgPanoRect to set
     */
    public void setImgPanoRect(Image imgPanoRect) {
        this.imgPanoRect = imgPanoRect;
    }

    /**
     * Retourne la valeur de minLat.
     *
     * @return the minLat
     */
    public double getMinLat() {
        return minLat;
    }

    /**
     * Définit la valeur de minLat.
     *
     * @param minLat the minLat to set
     */
    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    /**
     * Retourne la valeur de maxLat.
     *
     * @return the maxLat
     */
    public double getMaxLat() {
        return maxLat;
    }

    /**
     * Définit la valeur de maxLat.
     *
     * @param maxLat the maxLat to set
     */
    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * Retourne la valeur de bMinLat.
     *
     * @return the bMinLat
     */
    public boolean isbMinLat() {
        return bMinLat;
    }

    /**
     * Définit la valeur de bMinLat.
     *
     * @param bMinLat the bMinLat to set
     */
    public void setbMinLat(boolean bMinLat) {
        this.bMinLat = bMinLat;
    }

    /**
     * Retourne la valeur de bMaxLat.
     *
     * @return the bMaxLat
     */
    public boolean isbMaxLat() {
        return bMaxLat;
    }

    /**
     * Définit la valeur de bMaxLat.
     *
     * @param bMaxLat the bMaxLat to set
     */
    public void setbMaxLat(boolean bMaxLat) {
        this.bMaxLat = bMaxLat;
    }

    /**
     * Retourne la valeur de iNombreHotspotDiapo.
     *
     * @return the iNombreHotspotDiapo
     */
    public int getiNombreHotspotDiapo() {
        return iNombreHotspotDiapo;
    }

    /**
     * Définit la valeur de iNombreHotspotDiapo.
     *
     * @param iNombreHotspotDiapo the iNombreHotspotDiapo to set
     */
    public void setiNombreHotspotDiapo(int iNombreHotspotDiapo) {
        this.iNombreHotspotDiapo = iNombreHotspotDiapo;
    }

    /**
     * Retourne la valeur de imgPanoRectListe.
     *
     * @return the imgPanoRectListe
     */
    public Image getImgPanoRectListe() {
        return imgPanoRectListe;
    }

    /**
     * Définit la valeur de imgPanoRectListe.
     *
     * @param imgPanoRectListe the imgPanoRectListe to set
     */
    public void setImgPanoRectListe(Image imgPanoRectListe) {
        this.imgPanoRectListe = imgPanoRectListe;
    }

    /**
     * Retourne la valeur de imgCubeEqui.
     *
     * @return the imgCubeEqui
     */
    public Image getImgCubeEqui() {
        return imgCubeEqui;
    }

    /**
     * Définit la valeur de imgCubeEqui.
     *
     * @param imgCubeEqui the imgCubeEqui to set
     */
    public void setImgCubeEqui(Image imgCubeEqui) {
        this.imgCubeEqui = imgCubeEqui;
    }

    /**
     * Retourne la valeur de fovMax.
     *
     * @return the fovMax
     */
    public double getFovMax() {
        return fovMax;
    }

    /**
     * Définit la valeur de fovMax.
     *
     * @param fovMax the fovMax to set
     */
    public void setFovMax(double fovMax) {
        this.fovMax = fovMax;
    }

    /**
     * Retourne la valeur de fovMin.
     *
     * @return the fovMin
     */
    public double getFovMin() {
        return fovMin;
    }

    /**
     * Définit la valeur de fovMin.
     *
     * @param fovMin the fovMin to set
     */
    public void setFovMin(double fovMin) {
        this.fovMin = fovMin;
    }

    /**
     * Retourne la valeur de cubeFacesPetiteResolution.
     *
     * @return the cubeFacesPetiteResolution
     */
    public Image[] getCubeFacesPetiteResolution() {
        return cubeFacesPetiteResolution;
    }

    /**
     * Définit la valeur de cubeFacesPetiteResolution.
     *
     * @param cubeFacesPetiteResolution the cubeFacesPetiteResolution to set
     */
    public void setCubeFacesPetiteResolution(Image[] cubeFacesPetiteResolution) {
        this.cubeFacesPetiteResolution = cubeFacesPetiteResolution;
    }

    /**
     * Retourne la valeur de cubeFacesGrandeResolution.
     *
     * @return the cubeFacesGrandeResolution
     */
    public Image[] getCubeFacesGrandeResolution() {
        return cubeFacesGrandeResolution;
    }

    /**
     * Définit la valeur de cubeFacesGrandeResolution.
     *
     * @param cubeFacesGrandeResolution the cubeFacesGrandeResolution to set
     */
    public void setCubeFacesGrandeResolution(Image[] cubeFacesGrandeResolution) {
        this.cubeFacesGrandeResolution = cubeFacesGrandeResolution;
    }

    /**
     * Retourne la valeur de imgHauteResolution.
     *
     * @return the imgHauteResolution
     */
    public Image getImgHauteResolution() {
        return imgHauteResolution;
    }

    /**
     * Définit la valeur de imgHauteResolution.
     *
     * @param imgHauteResolution the imgHauteResolution to set
     */
    public void setImgHauteResolution(Image imgHauteResolution) {
        this.imgHauteResolution = imgHauteResolution;
    }

    /**
     * Retourne la valeur de strDescriptionIA.
     *
     * @return the strDescriptionIA
     */
    public String getStrDescriptionIA() {
        return strDescriptionIA != null ? strDescriptionIA : "";
    }

    /**
     * Définit la valeur de strDescriptionIA.
     *
     * @param strDescriptionIA the strDescriptionIA to set
     */
    public void setStrDescriptionIA(String strDescriptionIA) {
        this.strDescriptionIA = strDescriptionIA != null ? strDescriptionIA : "";
    }

}
