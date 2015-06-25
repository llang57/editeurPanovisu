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
     * @return the nomDiaporama
     */
    public String getStrNomDiaporama() {
        return strNomDiaporama;
    }

    /**
     * @param nomDiaporama the nomDiaporama to set
     */
    public void setStrNomDiaporama(String nomDiaporama) {
        this.strNomDiaporama = nomDiaporama;
    }

    /**
     * @return the strImages
     */
    public String[] getStrFichiersImage() {
        return strFichiersImage;
    }

    public Object clone() {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

    /**
     * @param i numero de l'image à retourner
     * @return the strImages
     */
    public String getStrFichiersImage(int i) {
        return strFichiersImage[i];
    }

    /**
     * @param strImages the strImages to set
     */
    public void setStrFichiersImage(String[] strImages) {
        this.strFichiersImage = strImages;
    }

    /**
     * @param strImages the strImages to set
     * @param i numero de l'image à modifier
     */
    public void setStrFichiersImage(String strImages, int i) {
        this.strFichiersImage[i] = strImages;
    }

    /**
     * @return the strLibellesImages
     */
    public String[] getStrLibellesImages() {
        return strLibellesImages;
    }

    /**
     * @param i numéro du libellé
     * @return the strLibellesImages
     */
    public String getStrLibellesImages(int i) {
        return strLibellesImages[i];
    }

    /**
     * @param strLibellesImages the strLibellesImages to set
     */
    public void setStrLibellesImages(String[] strLibellesImages) {
        this.strLibellesImages = strLibellesImages;
    }

    /**
     * @param strLibellesImages the strLibellesImages to set
     * @param i numéro du libellé à modifier
     */
    public void setStrLibellesImages(String strLibellesImages, int i) {
        this.strLibellesImages[i] = strLibellesImages;
    }

    /**
     * @return the delaiDiaporama
     */
    public double getDelaiDiaporama() {
        return delaiDiaporama;
    }

    /**
     * @param delaiDiaporama the delaiDiaporama to set
     */
    public void setDelaiDiaporama(double delaiDiaporama) {
        this.delaiDiaporama = delaiDiaporama;
    }

    /**
     * @return the strCouleurFondDiaporama
     */
    public String getStrCouleurFondDiaporama() {
        return strCouleurFondDiaporama;
    }

    /**
     * @param strCouleurFondDiaporama the strCouleurFondDiaporama to set
     */
    public void setStrCouleurFondDiaporama(String strCouleurFondDiaporama) {
        this.strCouleurFondDiaporama = strCouleurFondDiaporama;
    }

    /**
     * @return the opaciteDiaporama
     */
    public double getOpaciteDiaporama() {
        return opaciteDiaporama;
    }

    /**
     * @param opaciteDiaporama the opaciteDiaporama to set
     */
    public void setOpaciteDiaporama(double opaciteDiaporama) {
        this.opaciteDiaporama = opaciteDiaporama;
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

    /**
     * @return the iOrdreDiaporama
     */
    public int[] getiOrdreDiaporama() {
        return iOrdreDiaporama;
    }

    /**
     * @param iOrdreDiaporama the iOrdreDiaporama to set
     */
    public void setiOrdreDiaporama(int[] iOrdreDiaporama) {
        this.iOrdreDiaporama = iOrdreDiaporama;
    }

    /**
     * @return the strFichierDiaporama
     */
    public String getStrFichierDiaporama() {
        return strFichierDiaporama;
    }

    /**
     * @param strFichierDiaporama the strFichierDiaporama to set
     */
    public void setStrFichierDiaporama(String strFichierDiaporama) {
        this.strFichierDiaporama = strFichierDiaporama;
    }

    /**
     * @return the strFichiers
     */
    public String[] getStrFichiers() {
        return strFichiers;
    }

    /**
     * @param i numéro du nom fichier
     * @return the strFichiers
     */
    public String getStrFichiers(int i) {
        return strFichiers[i];
    }

    /**
     * @param strFichiers the strFichiers to set
     */
    public void setStrFichiers(String[] strFichiers) {
        this.strFichiers = strFichiers;
    }

    /**
     * @param strFichiers the strFichiers to set
     * @param i  numéro du nom fichier à modifier
     */
    public void setStrFichiers(String strFichiers, int i) {
        this.strFichiers[i] = strFichiers;
    }

}
