/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.geometry.Point2D;

/**
 *
 * @author LANG Laurent
 */
public class ZoneTelecommande {

    private String strIdZone;
    private String strTypeZone;
    private String strCoordonneesZone;
    private Point2D centre = new Point2D(0, 0);
    private int iNombrePoints = 0;

    /**
     * @return the strNomZone
     */
    public String getStrIdZone() {
        return strIdZone;
    }

    /**
     * @param strNomZone the strNomZone to set
     */
    public void setStrIdZone(String strIdZone) {
        this.strIdZone = strIdZone;
    }

    /**
     * @return the strTypeZone
     */
    public String getStrTypeZone() {
        return strTypeZone;
    }

    /**
     * @param strTypeZone the strTypeZone to set
     */
    public void setStrTypeZone(String strTypeZone) {
        this.strTypeZone = strTypeZone;
    }

    /**
     * @return the strCoordonneesZone
     */
    public String getStrCoordonneesZone() {
        return strCoordonneesZone;
    }

    /**
     * @param strCoordonneesZone the strCoordonneesZone to set
     */
    public void setStrCoordonneesZone(String strCoordonneesZone) {
        this.strCoordonneesZone = strCoordonneesZone;
    }

    /**
     * @return the centre
     */
    public Point2D getCentre() {
        return centre;
    }

    /**
     * @param centre the centre to set
     */
    public void setCentre(Point2D centre) {
        this.centre = centre;
    }

    public void calculeCentre() {
        if (!this.strCoordonneesZone.equals("") && this.strCoordonneesZone != null) {
            Point2D[] pointsZone = new Point2D[50];
            String[] strPoints;
            strPoints = this.strCoordonneesZone.split(",");
            switch (this.strTypeZone) {
                case "circle":
                    this.centre = new Point2D(Double.parseDouble(strPoints[0]), Double.parseDouble(strPoints[1]));
                    break;
                case "rect":
                    this.centre = new Point2D(
                            (Double.parseDouble(strPoints[0]) + Double.parseDouble(strPoints[2])) / 2.d,
                            (Double.parseDouble(strPoints[1]) + Double.parseDouble(strPoints[3])) / 2.d
                    );
                    break;
                case "poly":
                    double xMax = -5000;
                    double yMax = -5000;
                    double yMin = 5000;
                    double xMin = 5000;
                    for (int i = 0; i < strPoints.length; i += 2) {
                        if (Double.parseDouble(strPoints[i]) > xMax) {
                            xMax = Double.parseDouble(strPoints[i]);
                        }
                        if (Double.parseDouble(strPoints[i]) < xMin) {
                            xMin = Double.parseDouble(strPoints[i]);
                        }
                        if (Double.parseDouble(strPoints[i + 1]) > yMax) {
                            yMax = Double.parseDouble(strPoints[i + 1]);
                        }
                        if (Double.parseDouble(strPoints[i + 1]) < yMin) {
                            yMin = Double.parseDouble(strPoints[i + 1]);
                        }
                    }
                    this.centre = new Point2D((xMax + xMin) / 2.d, (yMax + yMin) / 2.d);
                    break;
            }

        }
    }
}
