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
public class CoordonneesGeographiques {

    private double latitude, longitude;

    CoordonneesGeographiques(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    CoordonneesGeographiques() {
        this.latitude = 0.d;
        this.longitude = 0.d;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static double fromDMS(String degDMS) {
        String degres = degDMS.split("°")[0];
        String minutes = degDMS.split("°")[1].split("'")[0];
        String secondes = degDMS.split("°")[1].split("'")[1].split("\"")[0];
        double degreDecimal = Double.parseDouble(degres) + Double.parseDouble(minutes) / 60.d + Double.parseDouble(secondes) / 3600.d;
        System.out.println(degres + "°" + minutes + "'" + secondes + "\" = " + degreDecimal);
        return degreDecimal;
    }

    public static String toDMS(double degDecimal) {
        String signe = "";
        if (degDecimal < 0) {
            signe = "-";
            degDecimal = Math.abs(degDecimal);
        }
        int degres = (int) Math.round(degDecimal - 0.5);
        double min = (degDecimal - degres) * 60;
        int minutes = (int) Math.round((degDecimal - degres) * 60 - 0.5);
        double secondes = Math.round(((min - minutes) * 60) * 100.d) / 100.d;
        return signe + degres + "°" + minutes + "'" + secondes + "\"";
    }

}
