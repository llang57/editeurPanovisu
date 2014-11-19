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
public class CartePanoVisu {

    private CoordonneesGeographiques centreCarte;
    private int facteurZoom;
    private int iNombreMarqueur;
    private MarqueurGeo[] marqueurs = new MarqueurGeo[100];
    private String strTypeCarte = "";

    /**
     *
     */
    public CartePanoVisu() {
        this.iNombreMarqueur = 0;
        this.facteurZoom = 13;
        this.centreCarte = new CoordonneesGeographiques(0.d, 0.d);
        this.strTypeCarte = "";
    }

    /**
     *
     * @param iNombreMarqueur
     * @param coordonneesCentre
     * @param strTypeCarte
     * @param marqueurs
     * @param facteurZoom
     */
    public CartePanoVisu(int iNombreMarqueur, CoordonneesGeographiques coordonneesCentre, String strTypeCarte, MarqueurGeo[] marqueurs, int facteurZoom) {
        this.strTypeCarte = strTypeCarte;
        this.iNombreMarqueur = iNombreMarqueur;
        this.facteurZoom = facteurZoom;
        this.centreCarte = coordonneesCentre;
        this.marqueurs = marqueurs;
    }

    /**
     *
     * @param coordonnees
     * @param strXML
     * @param strHTML
     */
    public void ajouteMarqueur(CoordonneesGeographiques coordonnees, String strXML, String strHTML) {
        getMarqueurs()[getiNombreMarqueur()] = new MarqueurGeo(coordonnees, strHTML, strXML);
        setiNombreMarqueur(getiNombreMarqueur() + 1);
    }

    /**
     * @return the centreCarte
     */
    public CoordonneesGeographiques getCentreCarte() {
        return centreCarte;
    }

    /**
     * @param centreCarte the centreCarte to set
     */
    public void setCentreCarte(CoordonneesGeographiques centreCarte) {
        this.centreCarte = centreCarte;
    }

    /**
     * @return the facteurZoom
     */
    public int getFacteurZoom() {
        return facteurZoom;
    }

    /**
     * @param facteurZoom the facteurZoom to set
     */
    public void setFacteurZoom(int facteurZoom) {
        this.facteurZoom = facteurZoom;
    }

    /**
     * @return the iNombreMarqueur
     */
    public int getiNombreMarqueur() {
        return iNombreMarqueur;
    }

    /**
     * @param iNombreMarqueur the iNombreMarqueur to set
     */
    public void setiNombreMarqueur(int iNombreMarqueur) {
        this.iNombreMarqueur = iNombreMarqueur;
    }

    /**
     * @return the marqueurs
     */
    public MarqueurGeo[] getMarqueurs() {
        return marqueurs;
    }

    /**
     * @param marqueurs the marqueurs to set
     */
    public void setMarqueurs(MarqueurGeo[] marqueurs) {
        this.marqueurs = marqueurs;
    }

    /**
     *
     * @param i
     * @param marqueurs
     */
    public void setMarqueursI(int i, MarqueurGeo marqueurs) {
        this.marqueurs[i] = marqueurs;
    }

    /**
     * @return the strTypeCarte
     */
    public String getStrTypeCarte() {
        return strTypeCarte;
    }

    /**
     * @param strTypeCarte the strTypeCarte to set
     */
    public void setStrTypeCarte(String strTypeCarte) {
        this.strTypeCarte = strTypeCarte;
    }

}
