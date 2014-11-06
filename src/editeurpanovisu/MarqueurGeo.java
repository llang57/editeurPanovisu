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
public class MarqueurGeo {

    private CoordonneesGeographiques coordonnees;
    private String strXMLMarqueur;
    private String strHTMLMarqueur;

    MarqueurGeo() {
        coordonnees = new CoordonneesGeographiques(0.d, 0.d);
        strHTMLMarqueur = "";
        strXMLMarqueur = "";
    }

    MarqueurGeo(CoordonneesGeographiques coordonnees, String strHTML, String strXML) {
        this.coordonnees = coordonnees;
        this.strXMLMarqueur = strXML;
        this.strHTMLMarqueur = strHTML;
    }

    /**
     * @return the strXMLMarqueur
     */
    public String getStrXMLMarqueur() {
        return strXMLMarqueur;
    }

    /**
     * @param strXMLMarqueur the strXMLMarqueur to set
     */
    public void setStrXMLMarqueur(String strXMLMarqueur) {
        this.strXMLMarqueur = strXMLMarqueur;
    }

    /**
     * @return the strHTMLMarqueur
     */
    public String getStrHTMLMarqueur() {
        return strHTMLMarqueur;
    }

    /**
     * @param strHTMLMarqueur the strHTMLMarqueur to set
     */
    public void setStrHTMLMarqueur(String strHTMLMarqueur) {
        this.strHTMLMarqueur = strHTMLMarqueur;
    }

    /**
     * @return the coordonnees
     */
    public CoordonneesGeographiques getCoordonnees() {
        return coordonnees;
    }

    /**
     * @param coordonnees the coordonnees to set
     */
    public void setCoordonnees(CoordonneesGeographiques coordonnees) {
        this.coordonnees = coordonnees;
    }

}
