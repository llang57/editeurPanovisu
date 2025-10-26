/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 *
 * @author laure_000
 */
public class Diaporama implements Cloneable {

    private String strNomDiaporama = "";
    private String strFichierDiaporama = "";
    private String[] strFichiersImage = new String[100];
    private String[] strFichiers = new String[100];
    private String[] strLibellesImages = new String[100];
    private double delaiDiaporama = 5;
    private String strCouleurFondDiaporama = "#333333";
    private double opaciteDiaporama = 0.8;
    private int iNombreImages = 0;
    private int[] iOrdreDiaporama = new int[100];

    @Override
    public String toString() {
        String strSortie = "";
        strSortie += "Nom : " + strNomDiaporama + "\n"
                + "Ficher : " + strFichiers + "\n"
                + "delai : " + delaiDiaporama + "\n"
                + "couleur : " + strCouleurFondDiaporama + "\n"
                + "opacite : " + opaciteDiaporama + "\n"
                + "nombre Images : " + iNombreImages + "\n";
        for (int i = 0; i < iNombreImages; i++) {
            strSortie += "image  " + i + "\n"
                    + "fichiersImage  : \"" + strFichiersImage[i] + "\"\n"
                    + "fichiers       : \"" + strFichiers[i] + "\"\n"
                    + "libelles       : \"" + strLibellesImages[i] + "\"\n";
        }
        return strSortie;
    }

    /**
     * Retourne le nom du diaporama
     * 
     * @return Nom affiché pour identifier ce diaporama
     * @see #setStrNomDiaporama(String)
     */
    public String getStrNomDiaporama() {
        return strNomDiaporama;
    }

    /**
     * Définit le nom du diaporama
     * 
     * @param nomDiaporama Nom à attribuer au diaporama
     * @see #getStrNomDiaporama()
     */
    public void setStrNomDiaporama(String nomDiaporama) {
        this.strNomDiaporama = nomDiaporama;
    }

    /**
     * Retourne le tableau complet des fichiers images du diaporama
     * 
     * @return Tableau des chemins vers les fichiers images
     * @see #getStrFichiersImage(int)
     * @see #setStrFichiersImage(String[])
     */
    public String[] getStrFichiersImage() {
        return strFichiersImage;
    }

    /**
     * Crée une copie clone de l'objet Diaporama
     * 
     * <p>Utilise le mécanisme de clonage Java pour créer une copie superficielle
     * de l'instance courante.</p>
     * 
     * @return Copie clonée du diaporama, ou null en cas d'erreur
     * @see Cloneable
     */
    public Object clone() {
        Object objet = null;
        try {
            objet = super.clone();
        } catch (CloneNotSupportedException exception) {
            exception.printStackTrace(System.err);
        }
        return objet;
    }

    /**
     * Retourne le fichier image à un index spécifique
     * 
     * @param i Index de l'image (0 à nombreImages-1)
     * @return Chemin du fichier image à l'index donné
     * @see #getStrFichiersImage()
     * @see #setStrFichiersImage(String, int)
     */
    public String getStrFichiersImage(int i) {
        return strFichiersImage[i];
    }

    /**
     * Définit le tableau complet des fichiers images
     * 
     * @param strImages Tableau des chemins vers les fichiers images
     * @see #getStrFichiersImage()
     */
    public void setStrFichiersImage(String[] strImages) {
        this.strFichiersImage = strImages;
    }

    /**
     * Définit le fichier image à un index spécifique
     * 
     * @param strImages Chemin du fichier image
     * @param i Index où placer l'image (0 à nombreImages-1)
     * @see #getStrFichiersImage(int)
     */
    public void setStrFichiersImage(String strImages, int i) {
        this.strFichiersImage[i] = strImages;
    }

    /**
     * Retourne le tableau complet des libellés des images
     * 
     * @return Tableau des textes descriptifs des images
     * @see #getStrLibellesImages(int)
     * @see #setStrLibellesImages(String[])
     */
    public String[] getStrLibellesImages() {
        return strLibellesImages;
    }

    /**
     * Retourne le libellé d'une image spécifique
     * 
     * @param i Index de l'image (0 à nombreImages-1)
     * @return Texte descriptif de l'image à l'index donné
     * @see #getStrLibellesImages()
     * @see #setStrLibellesImages(String, int)
     */
    public String getStrLibellesImages(int i) {
        return strLibellesImages[i];
    }

    /**
     * Définit le tableau complet des libellés
     * 
     * @param strLibellesImages Tableau des textes descriptifs
     * @see #getStrLibellesImages()
     */
    public void setStrLibellesImages(String[] strLibellesImages) {
        this.strLibellesImages = strLibellesImages;
    }

    /**
     * Définit le libellé d'une image spécifique
     * 
     * @param strLibellesImages Texte descriptif de l'image
     * @param i Index où placer le libellé (0 à nombreImages-1)
     * @see #getStrLibellesImages(int)
     */
    public void setStrLibellesImages(String strLibellesImages, int i) {
        this.strLibellesImages[i] = strLibellesImages;
    }

    /**
     * Retourne le délai d'affichage entre les images
     * 
     * <p>Durée en secondes pendant laquelle chaque image est affichée
     * avant de passer à la suivante.</p>
     * 
     * @return Délai en secondes (par défaut 5.0)
     * @see #setDelaiDiaporama(double)
     */
    public double getDelaiDiaporama() {
        return delaiDiaporama;
    }

    /**
     * Définit le délai d'affichage entre les images
     * 
     * @param delaiDiaporama Délai en secondes (valeur positive)
     * @see #getDelaiDiaporama()
     */
    public void setDelaiDiaporama(double delaiDiaporama) {
        this.delaiDiaporama = delaiDiaporama;
    }

    /**
     * Retourne la couleur de fond du diaporama
     * 
     * @return Couleur au format hexadécimal (ex: "#333333")
     * @see #setStrCouleurFondDiaporama(String)
     */
    public String getStrCouleurFondDiaporama() {
        return strCouleurFondDiaporama;
    }

    /**
     * Définit la couleur de fond du diaporama
     * 
     * @param strCouleurFondDiaporama Couleur hexadécimale (ex: "#333333")
     * @see #getStrCouleurFondDiaporama()
     */
    public void setStrCouleurFondDiaporama(String strCouleurFondDiaporama) {
        this.strCouleurFondDiaporama = strCouleurFondDiaporama;
    }

    /**
     * Retourne l'opacité du fond du diaporama
     * 
     * @return Opacité entre 0.0 (transparent) et 1.0 (opaque), par défaut 0.8
     * @see #setOpaciteDiaporama(double)
     */
    public double getOpaciteDiaporama() {
        return opaciteDiaporama;
    }

    /**
     * Définit l'opacité du fond du diaporama
     * 
     * @param opaciteDiaporama Valeur entre 0.0 (transparent) et 1.0 (opaque)
     * @see #getOpaciteDiaporama()
     */
    public void setOpaciteDiaporama(double opaciteDiaporama) {
        this.opaciteDiaporama = opaciteDiaporama;
    }

    /**
     * Retourne le nombre d'images dans le diaporama
     * 
     * @return Nombre d'images (0 à 100)
     * @see #setiNombreImages(int)
     */
    public int getiNombreImages() {
        return iNombreImages;
    }

    /**
     * Définit le nombre d'images dans le diaporama
     * 
     * @param iNombreImages Nombre d'images (0 à 100)
     * @see #getiNombreImages()
     */
    public void setiNombreImages(int iNombreImages) {
        this.iNombreImages = iNombreImages;
    }

    /**
     * Retourne le tableau d'ordre de lecture du diaporama
     * 
     * <p>Permet de définir l'ordre d'affichage des images, différent de leur
     * ordre d'ajout.</p>
     * 
     * @return Tableau des indices définissant l'ordre de lecture
     * @see #setiOrdreDiaporama(int[])
     */
    public int[] getiOrdreDiaporama() {
        return iOrdreDiaporama;
    }

    /**
     * Définit l'ordre de lecture du diaporama
     * 
     * @param iOrdreDiaporama Tableau d'indices pour l'ordre d'affichage
     * @see #getiOrdreDiaporama()
     */
    public void setiOrdreDiaporama(int[] iOrdreDiaporama) {
        this.iOrdreDiaporama = iOrdreDiaporama;
    }

    /**
     * Retourne le nom du fichier de configuration du diaporama
     * 
     * @return Chemin du fichier de configuration
     * @see #setStrFichierDiaporama(String)
     */
    public String getStrFichierDiaporama() {
        return strFichierDiaporama;
    }

    /**
     * Définit le nom du fichier de configuration
     * 
     * @param strFichierDiaporama Chemin du fichier de configuration
     * @see #getStrFichierDiaporama()
     */
    public void setStrFichierDiaporama(String strFichierDiaporama) {
        this.strFichierDiaporama = strFichierDiaporama;
    }

    /**
     * Retourne le tableau complet des noms de fichiers
     * 
     * @return Tableau des noms de fichiers du diaporama
     * @see #getStrFichiers(int)
     * @see #setStrFichiers(String[])
     */
    public String[] getStrFichiers() {
        return strFichiers;
    }

    /**
     * Retourne le nom de fichier à un index spécifique
     * 
     * @param i Index du fichier (0 à nombreImages-1)
     * @return Nom du fichier à l'index donné
     * @see #getStrFichiers()
     * @see #setStrFichiers(String, int)
     */
    public String getStrFichiers(int i) {
        return strFichiers[i];
    }

    /**
     * Définit le tableau complet des noms de fichiers
     * 
     * @param strFichiers Tableau des noms de fichiers
     * @see #getStrFichiers()
     */
    public void setStrFichiers(String[] strFichiers) {
        this.strFichiers = strFichiers;
    }

    /**
     * Définit le nom de fichier à un index spécifique
     * 
     * @param strFichiers Nom du fichier
     * @param i Index où placer le nom (0 à nombreImages-1)
     * @see #getStrFichiers(int)
     */
    public void setStrFichiers(String strFichiers, int i) {
        this.strFichiers[i] = strFichiers;
    }

}
