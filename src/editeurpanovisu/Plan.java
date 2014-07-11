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

    private HotSpot[] hotspots = new HotSpot[100];
    private String titrePlan="";
    private String imagePlan="";
    private String lienPlan;
    private int nombreHotspots = 0;
    private String[] panoramiques = new String[50];
    private int nombrePanoramiques = 0;
    private double directionNord=180;
    private String position="top:right";
    private double positionX=20;
    private double positionY=20;

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
     * @param num
     */
    public void removeHotspot(int num) {
        for (int i = num; i < this.getNombreHotspots() - 1; i++) {
            this.hotspots[i] = this.hotspots[i + 1];
        }
        this.setNombreHotspots(this.getNombreHotspots() - 1);
        //int nombre
    }

    /**
     * @return the titrePLan
     */
    public String getTitrePlan() {
        return titrePlan;
    }

    /**
     * @param titrePLan the titrePLan to set
     */
    public void setTitrePlan(String titrePlan) {
        this.titrePlan = titrePlan;
    }

    /**
     * @return the nombreHotspots
     */
    public int getNombreHotspots() {
        return nombreHotspots;
    }

    /**
     * @param nombreHotspots the nombreHotspots to set
     */
    public void setNombreHotspots(int nombreHotspots) {
        this.nombreHotspots = nombreHotspots;
    }

    /**
     * @return the panoramiques
     */
    public String[] getPanoramiques() {
        return panoramiques;
    }

    /**
     * @param panoramiques the panoramiques to set
     */
    public void addPanoramique(String panoramique) {
        this.panoramiques[nombrePanoramiques] = panoramique;
        this.nombrePanoramiques++;
    }

    /**
     * @param panoramiques the panoramiques to set
     */
    public void removePanoramique(String panoramique) {
        boolean trouve = false;
        int numero = -1;
        for (int i = 0; i < nombrePanoramiques; i++) {
            if (this.panoramiques[nombrePanoramiques].equals(panoramique)) {
                trouve = true;
                numero = i;
            }

        }
        if (trouve) {
            for (int i = numero; i < nombrePanoramiques-1; i++) {
                this.panoramiques[i]=this.panoramiques[i+1];
            }
            this.nombrePanoramiques--;
        }
    }

    /**
     * @return the nombrePanoramiques
     */
    public int getNombrePanoramiques() {
        return nombrePanoramiques;
    }

    /**
     * @param nombrePanoramiques the nombrePanoramiques to set
     */
    public void setNombrePanoramiques(int nombrePanoramiques) {
        this.nombrePanoramiques = nombrePanoramiques;
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
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
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
    public String getImagePlan() {
        return imagePlan;
    }

    /**
     * @param imagePlan the imagePlan to set
     */
    public void setImagePlan(String imagePlan) {
        this.imagePlan = imagePlan;
    }

    /**
     * @return the lienPlan
     */
    public String getLienPlan() {
        return lienPlan;
    }

    /**
     * @param lienPlan the lienPlan to set
     */
    public void setLienPlan(String lienPlan) {
        this.lienPlan = lienPlan;
    }

}
