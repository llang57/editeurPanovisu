/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 *
 * @author LANG Laurent
 */
public class MarqueurGeolocalisation {

    private CoordonneesGeographiques coordonnees;
    private String strXMLMarqueur = "";
    private String strHTMLMarqueur = "";

    MarqueurGeolocalisation() {
        coordonnees = new CoordonneesGeographiques(0.d, 0.d);
        strHTMLMarqueur = "";
        strXMLMarqueur = "";
    }

    MarqueurGeolocalisation(CoordonneesGeographiques coordonnees, String strHTML, String strXML) {
        this.coordonnees = coordonnees;
        this.strXMLMarqueur = strXML;
        this.strHTMLMarqueur = strHTML;
    }

    /**
     * Retourne la valeur de strXMLMarqueur.
     *
     * @return the strXMLMarqueur
     */
    public String getStrXMLMarqueur() {
        return strXMLMarqueur;
    }

    /**
     * Définit la valeur de strXMLMarqueur.
     *
     * @param strXMLMarqueur the strXMLMarqueur to set
     */
    public void setStrXMLMarqueur(String strXMLMarqueur) {
        this.strXMLMarqueur = strXMLMarqueur;
    }

    /**
     * Retourne la valeur de strHTMLMarqueur.
     *
     * @return the strHTMLMarqueur
     */
    public String getStrHTMLMarqueur() {
        return strHTMLMarqueur;
    }

    /**
     * Définit la valeur de strHTMLMarqueur.
     *
     * @param strHTMLMarqueur the strHTMLMarqueur to set
     */
    public void setStrHTMLMarqueur(String strHTMLMarqueur) {
        this.strHTMLMarqueur = strHTMLMarqueur;
    }

    /**
     * Retourne la valeur de coordonnees.
     *
     * @return the coordonnees
     */
    public CoordonneesGeographiques getCoordonnees() {
        return coordonnees;
    }

    /**
     * Définit la valeur de coordonnees.
     *
     * @param coordonnees the coordonnees to set
     */
    public void setCoordonnees(CoordonneesGeographiques coordonnees) {
        this.coordonnees = coordonnees;
    }

}
