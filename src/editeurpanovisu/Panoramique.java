/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author LANG Laurent
 */
public class Panoramique {

    public static final String SPHERE = "sphere";
    public static final String CUBE = "cube";

    static private HotSpot[] hotspots = new HotSpot[100];
    static private String titrePanoramique;
    static private String nomFichier;
    static private double lookAtX, lookAtY;
    static private Image imagePanoramique;
    static private int nombreHotspots = 0;
    static private String typePanoramique;
    static private boolean afficheTitre;
    static private boolean afficheInfo;

    Panoramique(String fichier) {
        nomFichier = fichier;
        typePanoramique=Panoramique.SPHERE;
        afficheTitre=true;
        afficheInfo=true;
    }

    Panoramique() {
        typePanoramique=Panoramique.SPHERE;
        afficheTitre=true;
        afficheInfo=true;
    }
    
    /**
     * @return the nomFichier
     */
    public String getNomFichier() {
        return nomFichier;
    }

    /**
     * @param nFichier the nomFichier to set
     */
    public void setNomFichier(String nFichier) {
        nomFichier = nFichier;
    }

    /**
     * @return the lookAtX
     */
    public double getLookAtX() {
        return lookAtX;
    }

    /**
     * @param lkAtX the lookAtX to set
     */
    public void setLookAtX(double lkAtX) {
        lookAtX = lkAtX;
    }

    /**
     * @return the lookAtY
     */
    public double getLookAtY() {
        return lookAtY;
    }

    /**
     * @param lkAtY the lookAtY to set
     */
    public void setLookAtY(double lkAtY) {
        lookAtY = lkAtY;
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
        hotspots[i] = hotspot;
    }

    public void addHotspot(HotSpot hotspot) {
        hotspots[this.getNombreHotspots()] = hotspot;
        nombreHotspots++;
    }

    public void removeHotspot(int num) {
        System.out.println("Retire le point : " + num);
        for (int i = num; i < nombreHotspots - 1; i++) {
            hotspots[i] = hotspots[i + 1];
        }
        nombreHotspots--;
        //int nombre
        System.out.println("il reste  : " + nombreHotspots + " HotSpots");
    }

    /**
     * @return the imagePanoramique
     */
    public Image getImagePanoramique() {
        return imagePanoramique;
    }

    /**
     * @param imgPanoramique the imagePanoramique to set
     */
    public void setImagePanoramique(Image imgPanoramique) {
        imagePanoramique = imgPanoramique;
    }

    /**
     * @return the nombreHotspots
     */
    public int getNombreHotspots() {
        return nombreHotspots;
    }

    /**
     * @param nbHotspots the nombreHotspots to set
     */
    public void setNombreHotspots(int nbHotspots) {
        nombreHotspots = nbHotspots;
    }

    /**
     * @return the typePanoramique
     */
    public String getTypePanoramique() {
        return typePanoramique;
    }

    /**
     * @param tPanoramique the typePanoramique to set
     */
    public void setTypePanoramique(String tPanoramique) {
        typePanoramique = tPanoramique;
    }

    /**
     * @return the afficheTitre
     */
    public boolean isAfficheTitre() {
        return afficheTitre;
    }

    /**
     * @param affTitre the afficheTitre to set
     */
    public void setAfficheTitre(boolean affTitre) {
        afficheTitre = affTitre;
    }

    /**
     * @return the afficheInfo
     */
    public boolean isAfficheInfo() {
        return afficheInfo;
    }

    /**
     * @param affInfo the afficheInfo to set
     */
    public void setAfficheInfo(boolean affInfo) {
        afficheInfo = affInfo;
    }

    public Pane getAffichageHS() {
        Pane panneauHotSpots = new Pane();
        VBox vb1=new VBox();
        panneauHotSpots.getChildren().add(vb1);
        for (int o = 0; o < this.getNombreHotspots(); o++) {
            Pane pannneauHS = new Pane();
            String chLong1, chLat1;
            Label lbl;
            chLong1 = "Long : " + String.format("%.1f", this.getHotspot(o).getLongitude());
            chLat1 = "Lat : " + String.format("%.1f", this.getHotspot(o).getLatitude());
            lbl = new Label("point n°" + o + "\n ==> " + chLong1 + "," + chLat1);
            pannneauHS.setStyle("border : 1px solid #777;border-radius : 5px;padding : 5px;background-color : #bbb;");
            pannneauHS.setPrefWidth(280);
            pannneauHS.setTranslateX(10);
            pannneauHS.getChildren().add(lbl);
            vb1.getChildren().add(pannneauHS);
        }
        return panneauHotSpots;
    }

    /**
     * @return the titrePanoramique
     */
    public String getTitrePanoramique() {
        return titrePanoramique;
    }

    /**
     * @param tPanoramique the titrePanoramique to set
     */
    public void setTitrePanoramique(String tPanoramique) {
        titrePanoramique = tPanoramique;
    }

}
