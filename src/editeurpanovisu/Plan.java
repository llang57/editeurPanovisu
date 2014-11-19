/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

/**
 *
 * @author llang
 */
public class Plan {

    private final HotSpot[] hotspots = new HotSpot[100];
    private String strTitrePlan = "";
    private String strImagePlan = "";
    private String strLienPlan = "";
    private int iNombreHotspots = 0;
    private final String[] strPanoramiques = new String[50];
    private int iNombrePanoramiques = 0;
    private double directionNord = 0;
    private String strPosition = "top:right";
    private double positionX = 20;
    private double positionY = 20;
    private double largeurPlan;
    private double hauteurPlan;

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
        this.setNombreHotspots(this.getNombreHotspots() + 1);
    }

    /**
     *
     * @param iNum
     */
    public void removeHotspot(int iNum) {
        for (int i = iNum; i < this.getNombreHotspots() - 1; i++) {
            this.hotspots[i] = this.hotspots[i + 1];
        }
        this.setNombreHotspots(this.getNombreHotspots() - 1);
        //int nombre
    }

    /**
     * @return the titrePLan
     */
    public String getTitrePlan() {
        return strTitrePlan;
    }

    /**
     *
     * @param strTitrePlan
     */
    public void setTitrePlan(String strTitrePlan) {
        this.strTitrePlan = strTitrePlan;
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
     * @return the panoramiques
     */
    public String[] getStrPanoramiques() {
        return strPanoramiques;
    }

    /**
     * @param strPanoramique the panoramiques to set
     */
    public void addStrPanoramique(String strPanoramique) {
        this.strPanoramiques[iNombrePanoramiques] = strPanoramique;
        this.iNombrePanoramiques++;
    }

    /**
     * @param strPanoramique the panoramiques to set
     */
    public void removeStrPanoramique(String strPanoramique) {
        boolean trouve = false;
        int numero = -1;
        for (int i = 0; i < iNombrePanoramiques; i++) {
            if (this.strPanoramiques[iNombrePanoramiques].equals(strPanoramique)) {
                trouve = true;
                numero = i;
            }

        }
        if (trouve) {
            for (int i = numero; i < iNombrePanoramiques - 1; i++) {
                this.strPanoramiques[i] = this.strPanoramiques[i + 1];
            }
            this.iNombrePanoramiques--;
        }
    }

    /**
     * @return the nombrePanoramiques
     */
    public int getNombrePanoramiques() {
        return iNombrePanoramiques;
    }

    /**
     * @param iNombrePanoramiques the nombrePanoramiques to set
     */
    public void setNombrePanoramiques(int iNombrePanoramiques) {
        this.iNombrePanoramiques = iNombrePanoramiques;
    }

    /**
     * @return the directionNord
     */
    public double getDirectionNord() {
        return directionNord;
    }

    /**
     * @param directionNord the directionNord to set
     */
    public void setDirectionNord(double directionNord) {
        this.directionNord = directionNord;
    }

    /**
     * @return the position
     */
    public String getStrPosition() {
        return strPosition;
    }

    /**
     * @param strPosition the position to set
     */
    public void setStrPosition(String strPosition) {
        this.strPosition = strPosition;
    }

    /**
     * @return the positionX
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * @param positionX the positionX to set
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * @return the positionY
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * @param positionY the positionY to set
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * @return the imagePlan
     */
    public String getStrImagePlan() {
        return strImagePlan;
    }

    /**
     * @param strImagePlan the imagePlan to set
     */
    public void setStrImagePlan(String strImagePlan) {
        this.strImagePlan = strImagePlan;
    }

    /**
     * @return the lienPlan
     */
    public String getStrLienPlan() {
        return strLienPlan;
    }

    /**
     * @param strLienPlan the lienPlan to set
     */
    public void setStrLienPlan(String strLienPlan) {
        this.strLienPlan = strLienPlan;
    }

    /**
     * @return the largeurPlan
     */
    public double getLargeurPlan() {
        return largeurPlan;
    }

    /**
     * @param largeurPlan the largeurPlan to set
     */
    public void setLargeurPlan(double largeurPlan) {
        this.largeurPlan = largeurPlan;
    }

    /**
     * @return the hauteurPlan
     */
    public double getHauteurPlan() {
        return hauteurPlan;
    }

    /**
     * @param hauteurPlan the hauteurPlan to set
     */
    public void setHauteurPlan(double hauteurPlan) {
        this.hauteurPlan = hauteurPlan;
    }

}
