/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.image.Image;

/**
 * Définition des hotspots panoramiques
 *
 * @author LANG Laurent
 */
public class HotSpot {

    private double longitude, latitude;
    private String strFichierImage = "";
    private String strFichierXML = "";
    private String strInfo = "";
    private boolean bAnime;
    private String strTypeAnimation = "none"; // Type d'animation: none, pulse, rotation, etc.
    private boolean bAgranditSurvol = false;
    private String strCouleurPerso = ""; // Couleur personnalisée pour ce hotspot (vide = utiliser la couleur par défaut)
    private String strNomIconeSource = ""; // Nom du fichier d'icône source original (avant coloration)
    private int iTailleHotspot = 32; // Taille du hotspot en pixels (par défaut 32)
    private int iNumeroPano = -1;
    private double regardX=-1000;
    private double regardY=-1000;
    private double champVisuel=0;
    private Image imgVueHs;
    private Image imgIconeSource = null; // Image source de l'icône (PNG non colorée) pour ce hotspot spécifique

    /**
     *
     * @param longit
     * @param latit
     */
    public void HotSpot(Number longit, Number latit) {
        this.setLongitude((double) longit);
        this.setLatitude((double) latit);
        this.setAnime(false);
        this.setStrFichierXML("");
        this.setStrInfo("");
    }

    /**
     * Retourne la longitude du hotspot dans le panorama
     * 
     * <p>Position horizontale du hotspot dans l'espace sphérique panoramique.</p>
     * 
     * @return Longitude en degrés (-180° à +180°)
     * @see #setLongitude(Number)
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude du hotspot
     * 
     * @param longitude Position horizontale en degrés
     * @see #getLongitude()
     */
    public void setLongitude(Number longitude) {
        this.longitude = (double) longitude;
    }

    /**
     * Retourne la latitude du hotspot dans le panorama
     * 
     * <p>Position verticale du hotspot dans l'espace sphérique panoramique.</p>
     * 
     * @return Latitude en degrés (-90° à +90°)
     * @see #setLatitude(Number)
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude du hotspot
     * 
     * @param latitude Position verticale en degrés (-90° à +90°)
     * @see #getLatitude()
     */
    public void setLatitude(Number latitude) {
        this.latitude = (double) latitude;
    }

    /**
     * Retourne le chemin du fichier image d'icône du hotspot
     * 
     * @return Chemin du fichier image SVG/PNG utilisé pour l'affichage
     * @see #setStrFichierImage(String)
     */
    public String getStrFichierImage() {
        return strFichierImage;
    }

    /**
     * Définit le fichier image d'icône du hotspot
     * 
     * @param strFichierImage Chemin vers le fichier icône (SVG ou PNG)
     * @see #getStrFichierImage()
     */
    public void setStrFichierImage(String strFichierImage) {
        this.strFichierImage = strFichierImage;
    }

    /**
     * Retourne le nom du fichier XML du panorama cible
     * 
     * <p>Fichier XML vers lequel le hotspot pointe lors d'un clic (navigation).</p>
     * 
     * @return Nom du fichier XML cible, ou chaîne vide si aucun
     * @see #setStrFichierXML(String)
     */
    public String getStrFichierXML() {
        return strFichierXML;
    }

    /**
     * Définit le fichier XML du panorama cible
     * 
     * @param strFichierXML Nom du fichier XML vers lequel naviguer
     * @see #getStrFichierXML()
     */
    public void setStrFichierXML(String strFichierXML) {
        this.strFichierXML = strFichierXML;
    }

    /**
     * Retourne le texte d'information du hotspot
     * 
     * <p>Texte affiché dans l'infobulle au survol du hotspot.</p>
     * 
     * @return Texte d'information ou chaîne vide
     * @see #setStrInfo(String)
     */
    public String getStrInfo() {
        return strInfo;
    }

    /**
     * Définit le texte d'information du hotspot
     * 
     * @param strInfo Texte à afficher dans l'infobulle
     * @see #getStrInfo()
     */
    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }

    /**
     * Indique si le hotspot est animé
     * 
     * @return true si le hotspot possède une animation, false sinon
     * @see #setAnime(boolean)
     * @see #getStrTypeAnimation()
     */
    public boolean isAnime() {
        return bAnime;
    }

    /**
     * Active ou désactive l'animation du hotspot
     * 
     * <p>Si activé sans type d'animation spécifique, utilise "pulse" par défaut.</p>
     * 
     * @param bAnime true pour animer le hotspot, false sinon
     * @see #isAnime()
     * @see #setStrTypeAnimation(String)
     */
    public void setAnime(boolean bAnime) {
        this.bAnime = bAnime;
        // Compatibilité: si on active l'animation sans type spécifique, utiliser "pulse" par défaut
        if (bAnime && "none".equals(strTypeAnimation)) {
            strTypeAnimation = "pulse";
        } else if (!bAnime) {
            strTypeAnimation = "none";
        }
    }

    /**
     * Retourne le type d'animation du hotspot
     * 
     * @return Type d'animation : "none", "pulse", "rotation", etc.
     * @see #setStrTypeAnimation(String)
     */
    public String getStrTypeAnimation() {
        return strTypeAnimation;
    }

    /**
     * Définit le type d'animation du hotspot
     * 
     * <p>Synchronise automatiquement avec l'état animé/non-animé.</p>
     * 
     * @param strTypeAnimation Type d'animation ("none", "pulse", "rotation")
     * @see #getStrTypeAnimation()
     */
    public void setStrTypeAnimation(String strTypeAnimation) {
        this.strTypeAnimation = strTypeAnimation;
        // Synchroniser avec bAnime
        this.bAnime = !("none".equals(strTypeAnimation) || strTypeAnimation == null || strTypeAnimation.isEmpty());
    }

    /**
     * Indique si le hotspot s'agrandit au survol de la souris
     * 
     * @return true si agrandissement activé, false sinon
     * @see #setAgranditSurvol(boolean)
     */
    public boolean isAgranditSurvol() {
        return bAgranditSurvol;
    }

    /**
     * Active ou désactive l'agrandissement au survol
     * 
     * @param bAgranditSurvol true pour agrandir au survol, false sinon
     * @see #isAgranditSurvol()
     */
    public void setAgranditSurvol(boolean bAgranditSurvol) {
        this.bAgranditSurvol = bAgranditSurvol;
    }

    /**
     * Retourne le numéro du panorama cible
     * 
     * <p>Index du panorama vers lequel ce hotspot permet de naviguer.</p>
     * 
     * @return Numéro du panorama cible, ou -1 si aucun
     * @see #setNumeroPano(int)
     */
    public int getNumeroPano() {
        return iNumeroPano;
    }

    /**
     * Définit le numéro du panorama cible
     * 
     * @param iNumeroPano Index du panorama cible (-1 = aucun)
     * @see #getNumeroPano()
     */
    public void setNumeroPano(int iNumeroPano) {
        this.iNumeroPano = iNumeroPano;
    }

    /**
     * Retourne la longitude du regard cible
     * 
     * <p>Longitude vers laquelle orienter la vue lors de la navigation vers ce hotspot.</p>
     * 
     * @return Longitude du regard en degrés, ou -1000 si non défini
     * @see #setRegardX(double)
     */
    public double getRegardX() {
        return regardX;
    }

    /**
     * Définit la longitude du regard cible
     * 
     * @param regardX Longitude du regard en degrés (-1000 = non défini)
     * @see #getRegardX()
     */
    public void setRegardX(double regardX) {
        this.regardX = regardX;
    }

    /**
     * Retourne la latitude du regard cible
     * 
     * <p>Latitude vers laquelle orienter la vue lors de la navigation vers ce hotspot.</p>
     * 
     * @return Latitude du regard en degrés, ou -1000 si non défini
     * @see #setRegardY(double)
     */
    public double getRegardY() {
        return regardY;
    }

    /**
     * Définit la latitude du regard cible
     * 
     * @param regardY Latitude du regard en degrés (-1000 = non défini)
     * @see #getRegardY()
     */
    public void setRegardY(double regardY) {
        this.regardY = regardY;
    }

    /**
     * Retourne le champ visuel cible
     * 
     * <p>FOV à appliquer lors de la navigation vers ce hotspot.</p>
     * 
     * @return Champ visuel en degrés, ou 0 si non défini
     * @see #setChampVisuel(double)
     */
    public double getChampVisuel() {
        return champVisuel;
    }

    /**
     * Définit le champ visuel cible
     * 
     * @param champVisuel Champ visuel en degrés (0 = non défini)
     * @see #getChampVisuel()
     */
    public void setChampVisuel(double champVisuel) {
        this.champVisuel = champVisuel;
    }

    /**
     * Retourne l'image de prévisualisation du hotspot
     * 
     * <p>Vignette capturée montrant la vue vers laquelle pointe le hotspot.</p>
     * 
     * @return Image de prévisualisation, ou null si aucune
     * @see #setImgVueHs(Image)
     */
    public Image getImgVueHs() {
        return imgVueHs;
    }

    /**
     * Définit l'image de prévisualisation du hotspot
     * 
     * @param imgVueHs Image vignette de prévisualisation
     * @see #getImgVueHs()
     */
    public void setImgVueHs(Image imgVueHs) {
        this.imgVueHs = imgVueHs;
    }

    /**
     * Retourne la couleur personnalisée du hotspot
     * 
     * @return Couleur au format "hue;saturation;brightness", ou vide pour couleur par défaut
     * @see #setStrCouleurPerso(String)
     */
    public String getStrCouleurPerso() {
        return strCouleurPerso;
    }

    /**
     * Définit la couleur personnalisée du hotspot
     * 
     * @param strCouleurPerso Couleur au format "hue;saturation;brightness", ou vide pour défaut
     * @see #getStrCouleurPerso()
     */
    public void setStrCouleurPerso(String strCouleurPerso) {
        this.strCouleurPerso = strCouleurPerso;
    }

    /**
     * Retourne l'image source non colorée de l'icône
     * 
     * @return Image PNG source avant application de la couleur, ou null
     * @see #setImgIconeSource(Image)
     */
    public Image getImgIconeSource() {
        return imgIconeSource;
    }

    /**
     * Définit l'image source non colorée de l'icône
     * 
     * @param imgIconeSource Image PNG source utilisée pour la coloration
     * @see #getImgIconeSource()
     */
    public void setImgIconeSource(Image imgIconeSource) {
        this.imgIconeSource = imgIconeSource;
    }

    /**
     * Retourne le nom du fichier icône source original
     * 
     * @return Nom du fichier icône source (avant coloration)
     * @see #setStrNomIconeSource(String)
     */
    public String getStrNomIconeSource() {
        return strNomIconeSource;
    }

    /**
     * Définit le nom du fichier icône source original
     * 
     * @param strNomIconeSource Nom du fichier icône source
     * @see #getStrNomIconeSource()
     */
    public void setStrNomIconeSource(String strNomIconeSource) {
        this.strNomIconeSource = strNomIconeSource;
    }

    /**
     * Retourne la taille du hotspot en pixels
     * 
     * @return Taille en pixels (généralement 16, 32, 48, 64)
     * @see #setTailleHotspot(int)
     */
    public int getTailleHotspot() {
        return iTailleHotspot;
    }

    /**
     * Définit la taille du hotspot en pixels
     * 
     * @param iTailleHotspot Taille en pixels (16, 32, 48, 64)
     * @see #getTailleHotspot()
     */
    public void setTailleHotspot(int iTailleHotspot) {
        this.iTailleHotspot = iTailleHotspot;
    }

}
