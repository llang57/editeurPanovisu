/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import netscape.javascript.JSObject;

/**
 *
 * @author LANG Laurent
 */
public class NavigateurOpenLayersSeul {

    private CoordonneesGeographiques marqueur;
    private NavigateurCarteSeul navigateurCarteSeul;
    private boolean bDebut = false;
    private String[] strCartesOpenLayers;
    private final ToggleGroup tgCartesOpenLayers = new ToggleGroup();
    private AnchorPane apChoixCartographie = new AnchorPane();
    private String strCartoActive;
    private String bingApiKey = "";
    //private String bingApiKey = "";

    /**
     * @return the marqueur
     */
    public CoordonneesGeographiques getMarqueur() {
        return marqueur;
    }

    /**
     * @param marqueur the marqueur to set
     */
    public void setMarqueur(CoordonneesGeographiques marqueur) {
        this.marqueur = marqueur;
    }

    /**
     *
     * @param coordonnees
     * @param iFacteurZoom
     */
    public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacteurZoom) {
        navigateurCarteSeul.getWebEngine().executeScript("allerCoordonnees(" + coordonnees.getLongitude() + "," + coordonnees.getLatitude() + "," + iFacteurZoom + ")");
    }

    /**
     *
     * @return
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        CoordonneesGeographiques coordonnees = new CoordonneesGeographiques();
        String strCoord = navigateurCarteSeul.getWebEngine().executeScript("getCoordonnees()").toString();
        coordonnees.setLongitude(Double.parseDouble(strCoord.split(";")[0]));
        coordonnees.setLatitude(Double.parseDouble(strCoord.split(";")[1]));
        return coordonnees;
    }

    /**
     *
     * @param iNumeroMarqueur
     */
    public void retireMarqueur(int iNumeroMarqueur) {
        navigateurCarteSeul.getWebEngine().executeScript("enleveMarqueur(" + iNumeroMarqueur + ")");
    }

    /**
     *
     */
    public void retireMarqueurs() {
        navigateurCarteSeul.getWebEngine().executeScript("enleveMarqueurs()");
    }

    /**
     *
     * @param iNumeroMarqueur
     * @param coordMarqueur
     * @param strHTML
     */
    public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coordMarqueur, String strHTML) {
        navigateurCarteSeul.getWebEngine().executeScript("ajouteMarqueur(" + iNumeroMarqueur + "," + coordMarqueur.getLongitude() + "," + coordMarqueur.getLatitude() + ",\"" + strHTML + "\")");
    }

    /**
     *
     * @param strAdresse
     * @param iFacteurZoom
     */
    public void allerAdresse(String strAdresse, int iFacteurZoom) {
        navigateurCarteSeul.getWebEngine().executeScript("chercheAdresse('" + strAdresse + "'," + iFacteurZoom + ")");
    }

    /**
     *
     * @param iFacteurZoom
     */
    public void choixZoom(int iFacteurZoom) {
        navigateurCarteSeul.getWebEngine().executeScript("choixZoom(" + iFacteurZoom + ")");
    }

    /**
     *
     * @param bingApiKey
     */
    public void valideBingApiKey(String bingApiKey) {
        if (bingApiKey.equals("")) {
            bingApiKey = EditeurPanovisu.getStrBingAPIKey();
        };
        if (!bingApiKey.equals("")) {
            navigateurCarteSeul.getWebEngine().executeScript("setBingApiKey(\"" + bingApiKey + "\");");
        }
        afficheCartesOpenlayer();
    }

    /**
     *
     * @return
     */
    public String recupereCartographiesOpenLayers() {
        return navigateurCarteSeul.getWebEngine().executeScript("getNomsLayers()").toString();
    }

    public void retireRadar() {
        navigateurCarteSeul.getWebEngine().executeScript("retireRadar()");
    }

    /**
     *
     * @param centre
     * @param angle
     * @param ouverture
     * @param rayon
     * @param strCouleurFond
     * @param strCouleurLigne
     * @param opacite
     */
    public void afficheRadar(
            CoordonneesGeographiques centre,
            double angle,
            double ouverture,
            double rayon,
            String strCouleurFond,
            String strCouleurLigne,
            double opacite
    ) {
        navigateurCarteSeul.getWebEngine().executeScript(" afficheRadar("
                + centre.getLongitude() + ","
                + centre.getLatitude() + ","
                + ouverture + ","
                + angle + ","
                + rayon + ","
                + "'" + strCouleurLigne + "',"
                + "'" + strCouleurFond + "',"
                + opacite
                + ")");
    }

    /**
     *
     * @param strCarto
     */
    public void changeCarte(String strCarto) {
        navigateurCarteSeul.getWebEngine().executeScript("changeLayer('" + strCarto + "')");
    }

    /**
     *
     */
    public void afficheCartesOpenlayer() {
        getApChoixCartographie().getChildren().clear();
        String nomsCartes = recupereCartographiesOpenLayers();
        strCartesOpenLayers = nomsCartes.split("\\|");
        for (int i = 0; i < strCartesOpenLayers.length; i++) {
            boolean bCarteChoisie = false;
            if (strCartesOpenLayers[i].substring(0, 1).equals("*")) {
                strCartesOpenLayers[i] = strCartesOpenLayers[i].substring(1, strCartesOpenLayers[i].length());
                bCarteChoisie = true;
            }
            RadioButton rbCarto = new RadioButton(strCartesOpenLayers[i]);
            rbCarto.setLayoutX(10);
            rbCarto.setLayoutY(i * 30);
            rbCarto.setUserData(strCartesOpenLayers[i]);
            rbCarto.setSelected(bCarteChoisie);
            rbCarto.setToggleGroup(tgCartesOpenLayers);
            getApChoixCartographie().getChildren().add(rbCarto);
        }
        getApChoixCartographie().setPrefHeight(strCartesOpenLayers.length * 30 + 10);
    }

    /**
     *
     * @return
     */
    public AnchorPane afficheNavigateurOpenLayer() {
        AnchorPane apOpenLayers = new AnchorPane();
        apOpenLayers.setStyle("-fx-background-color : -fx-base;");
        Label lblAttente = new Label("Chargement en cours. Veuillez patienter");
        lblAttente.setAlignment(Pos.CENTER);
        lblAttente.setStyle("-fx-background-color : #777;");
        lblAttente.setTextFill(Color.WHITE);
        lblAttente.setLayoutX(10);
        lblAttente.setLayoutY(70);
        apOpenLayers.getChildren().add(lblAttente);
        navigateurCarteSeul = new NavigateurCarteSeul();
        navigateurCarteSeul.setLayoutX(10);
        navigateurCarteSeul.setLayoutY(70);
        navigateurCarteSeul.setVisible(false);
        getApChoixCartographie().setMaxWidth(180);
        apOpenLayers.getChildren().add(navigateurCarteSeul);
        lblAttente.setPrefSize(apOpenLayers.getPrefWidth() - 220, apOpenLayers.getPrefHeight() - 120);
        navigateurCarteSeul.getWebView().setPrefWidth(apOpenLayers.getPrefWidth());
        navigateurCarteSeul.getWebView().setPrefHeight(apOpenLayers.getPrefHeight());
        navigateurCarteSeul.setLayoutX(0);
        navigateurCarteSeul.setLayoutY(0);

        navigateurCarteSeul.getWebEngine().getLoadWorker().stateProperty().addListener((paramObservableValue, from, to) -> {
            if (to == State.SUCCEEDED) {
                navigateurCarteSeul.setVisible(true);
                valideBingApiKey(getBingApiKey());
                JSObject window = (JSObject) navigateurCarteSeul.getWebEngine().executeScript("window");
                window.setMember("javafx", new JavaApplication2());
                allerAdresse("Metz rue Serpenoise", 14);
                setbDebut(true);
            }
        });
        tgCartesOpenLayers.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgCartesOpenLayers.getSelectedToggle() != null) {
                setStrCartoActive(tgCartesOpenLayers.getSelectedToggle().getUserData().toString());
                changeCarte(getStrCartoActive());
            }
        });

        apOpenLayers.widthProperty().addListener((ov, av, nv) -> {
            navigateurCarteSeul.getWebView().setPrefWidth((double) nv);

        });

        apOpenLayers.heightProperty().addListener((ov, av, nv) -> {
            navigateurCarteSeul.getWebView().setPrefHeight((double) nv);
        });
        return apOpenLayers;
    }

    /**
     * @return the strCartoActive
     */
    public String getStrCartoActive() {
        return strCartoActive;
    }

    /**
     * @param strCartoActive the strCartoActive to set
     */
    public void setStrCartoActive(String strCartoActive) {
        this.strCartoActive = strCartoActive;
    }

    /**
     * @return the apChoixCartographie
     */
    public AnchorPane getApChoixCartographie() {
        return apChoixCartographie;
    }

    /**
     * @param apChoixCartographie the apChoixCartographie to set
     */
    public void setApChoixCartographie(AnchorPane apChoixCartographie) {
        this.apChoixCartographie = apChoixCartographie;
    }

    /**
     * @return the bingApiKey
     */
    public String getBingApiKey() {
        return bingApiKey;
    }

    /**
     * @param bingApiKey the bingApiKey to set
     */
    public void setBingApiKey(String bingApiKey) {
        this.bingApiKey = bingApiKey;
    }

    /**
     * @return the bDebut
     */
    public boolean isbDebut() {
        return bDebut;
    }

    /**
     * @param bDebut the bDebut to set
     */
    public void setbDebut(boolean bDebut) {
        this.bDebut = bDebut;
    }

    /**
     *
     */
    public class JavaApplication2 {

        /**
         *
         * @param msg
         */
        public void adresseInconnue(String msg) {
            System.out.println("Adresse Inconnue\n" + msg);
        }

        /**
         *
         * @param lon
         * @param lat
         */
        public void adresseTrouvee(double lon, double lat) {
            System.out.println(
                    "Adresse trouvée aux coordonnées : " + CoordonneesGeographiques.toDMS(lat) + "  " + CoordonneesGeographiques.toDMS(lon)
            );
        }

        /**
         *
         * @param strChaine
         */
        public void afficheChaine(String strChaine) {
            System.out.println(strChaine);

        }

        /**
         *
         * @param iZoom
         */
        public void zoom(int iZoom) {
            EditeurPanovisu.getGestionnaireInterface().getSlZoomCarte().setValue((double) iZoom);
        }

        /**
         *
         * @param strNomLayer
         */
        public void changeLayer(String strNomLayer) {
            EditeurPanovisu.getGestionnaireInterface().setStrNomLayers(strNomLayer);
        }
    }

}
