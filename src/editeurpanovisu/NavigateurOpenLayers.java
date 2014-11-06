/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getTabInterface;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import java.io.File;
import javafx.concurrent.Worker.State;
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
import netscape.javascript.JSObject;

/**
 *
 * @author LANG Laurent
 */
public class NavigateurOpenLayers {

    private CoordonneesGeographiques marqueur;
    private NavigateurCarte navigateurCarte;
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
        navigateurCarte.getWebEngine().executeScript("allerCoordonnees(" + coordonnees.getLongitude() + "," + coordonnees.getLatitude() + "," + iFacteurZoom + ")");
    }

    /**
     *
     * @return
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        CoordonneesGeographiques coordonnees = new CoordonneesGeographiques();
        String strCoord = navigateurCarte.getWebEngine().executeScript("getCoordonnees()").toString();
        coordonnees.setLongitude(Double.parseDouble(strCoord.split(";")[0]));
        coordonnees.setLatitude(Double.parseDouble(strCoord.split(";")[1]));
        return coordonnees;
    }

    /**
     *
     * @param iNumeroMarqueur
     */
    public void retireMarqueur(int iNumeroMarqueur) {
        navigateurCarte.getWebEngine().executeScript("enleveMarqueur(" + iNumeroMarqueur + ")");
    }

    /**
     *
     * @param iNumeroMarqueur
     * @param coordMarqueur
     * @param strHTML
     */
    public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coordMarqueur, String strHTML) {
        navigateurCarte.getWebEngine().executeScript("ajouteMarqueur(" + iNumeroMarqueur + "," + coordMarqueur.getLongitude() + "," + coordMarqueur.getLatitude() + ",\"" + strHTML + "\")");
    }

    /**
     *
     * @param strAdresse
     * @param iFacteurZoom
     */
    public void allerAdresse(String strAdresse, int iFacteurZoom) {
        navigateurCarte.getWebEngine().executeScript("chercheAdresse('" + strAdresse + "'," + iFacteurZoom + ")");
    }

    /**
     *
     * @param iFacteurZoom
     */
    public void choixZoom(int iFacteurZoom) {
        navigateurCarte.getWebEngine().executeScript("choixZoom(" + iFacteurZoom + ")");
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
            navigateurCarte.getWebEngine().executeScript("setBingApiKey(\"" + bingApiKey + "\");");
        }
        afficheCartesOpenlayer();
    }

    /**
     *
     * @return
     */
    public String recupereCartographiesOpenLayers() {
        return navigateurCarte.getWebEngine().executeScript("getNomsLayers()").toString();
    }

    public void changeCarte(String strCarto) {
        navigateurCarte.getWebEngine().executeScript("changeLayer('" + strCarto + "')");
    }

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
    }

    /**
     *
     * @param tfLongitude
     * @param tfLatitude
     * @param bChoixMarqueur
     * @return
     */
    public AnchorPane afficheNavigateurOpenLayer(TextField tfLongitude, TextField tfLatitude, boolean bChoixMarqueur) {
        AnchorPane apOpenLayers = new AnchorPane();

        apOpenLayers.setStyle("-fx-background-color : -fx-base;-fx-border-width : 1px;-fx-border-style : solid;-fx-border-color : #777;"
                + "-fx-border-color: #777;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;"
        );
        Label lblAttente = new Label("Chargement en cours. Veuillez patienter");
        lblAttente.setAlignment(Pos.CENTER);
        lblAttente.setStyle("-fx-background-color : #777;");
        lblAttente.setTextFill(Color.WHITE);
        lblAttente.setLayoutX(10);
        lblAttente.setLayoutY(70);
        apOpenLayers.getChildren().add(lblAttente);
        navigateurCarte = new NavigateurCarte();
        navigateurCarte.setLayoutX(10);
        navigateurCarte.setLayoutY(70);
        navigateurCarte.setVisible(false);
        getApChoixCartographie().setMaxWidth(180);
        apOpenLayers.getChildren().add(navigateurCarte);
        File fileRep = new File("");
        String strRepertAppli = fileRep.getAbsolutePath();
        TextField tfRechercheAdresse = new TextField();
        Button btnRechercheAdresse = new Button("Recherche", new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/loupe.png", -1, 16, true, true)));
        Button btnRecupereCoordonnees = new Button("valide position");
        Button btnCentreCarte = new Button("recentre carte");
        Button btnFerme = new Button("X");
        btnFerme.setOnAction((e) -> {
            apOpenLayers.setVisible(false);
        });
        if (bChoixMarqueur) {
            tfRechercheAdresse.setPromptText("Votre recherche d'adresse");
            tfRechercheAdresse.setPrefWidth(200);
            tfRechercheAdresse.setLayoutX(10);
            tfRechercheAdresse.setLayoutY(40);
            btnRechercheAdresse.setLayoutX(220);
            btnRechercheAdresse.setLayoutY(40);
            btnRecupereCoordonnees.setLayoutX(10);
            btnRecupereCoordonnees.setLayoutY(10);
            btnRecupereCoordonnees.setPrefWidth(120);
            btnCentreCarte.setLayoutX(140);
            btnCentreCarte.setLayoutY(10);
            btnCentreCarte.setPrefWidth(120);
            apOpenLayers.getChildren().addAll(btnRecupereCoordonnees, btnCentreCarte, btnFerme,
                    tfRechercheAdresse, btnRechercheAdresse, getApChoixCartographie());
        }
        lblAttente.setPrefSize(apOpenLayers.getPrefWidth() - 220, apOpenLayers.getPrefHeight() - 120);
        if (bChoixMarqueur) {
            navigateurCarte.getWebView().setPrefWidth(apOpenLayers.getPrefWidth() - 220);
            navigateurCarte.getWebView().setPrefHeight(apOpenLayers.getPrefHeight() - 120);
            getApChoixCartographie().setLayoutX(apOpenLayers.getPrefWidth() - 180);
            getApChoixCartographie().setLayoutY(70);
        } else {
            navigateurCarte.getWebView().setPrefWidth(apOpenLayers.getPrefWidth());
            navigateurCarte.getWebView().setPrefHeight(apOpenLayers.getPrefHeight());
            navigateurCarte.setLayoutX(0);
            navigateurCarte.setLayoutY(0);
        }

        navigateurCarte.getWebEngine().getLoadWorker().stateProperty().addListener((paramObservableValue, from, to) -> {
            if (to == State.SUCCEEDED) {
                navigateurCarte.setVisible(true);

                valideBingApiKey(getBingApiKey());
                JSObject window = (JSObject) navigateurCarte.getWebEngine().executeScript("window");
                window.setMember("javafx", new JavaApplication());
                allerAdresse("Metz rue Serpenoise", 14);
                bDebut = true;
                getTabInterface().setDisable(false);
            }
        });
        if (bChoixMarqueur) {
            btnCentreCarte.setOnAction((e) -> {
                if (bDebut) {
                    if (getMarqueur() != null) {
                        allerCoordonnees(getMarqueur(), 17);
                    }
                }
            });
            btnRecupereCoordonnees.setOnAction((e) -> {
                if (bDebut) {
                    setMarqueur(this.recupereCoordonnees());
                    if (tfLongitude != null) {
                        tfLongitude.setText(CoordonneesGeographiques.toDMS(getMarqueur().getLongitude()));
                    }
                    if (tfLatitude != null) {
                        tfLatitude.setText(CoordonneesGeographiques.toDMS(getMarqueur().getLatitude()));
                    }
                    String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-weight:bold;font-size : 12px;'>"
                            + getPanoramiquesProjet()[getiPanoActuel()].getStrTitrePanoramique()
                            + "</span><br/>"
                            + "<span style='font-family : Verdana,Arial,sans-serif;bold;font-size : 10px;'>"
                            + getPanoramiquesProjet()[getiPanoActuel()].getStrNomFichier()
                            + "</span>";
                    strHTML = strHTML.replace("\\", "/");
                    retireMarqueur(0);
                    getPanoramiquesProjet()[getiPanoActuel()].setMarqueurGeolocatisation(getMarqueur());
                    ajouteMarqueur(0, getMarqueur(), strHTML);
                }
            });
        }
        tfRechercheAdresse.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (bDebut) {
                    allerAdresse(tfRechercheAdresse.getText(), 17);
                }
            }
        });

        btnRechercheAdresse.setOnAction((e) -> {
            if (bDebut) {
                allerAdresse(tfRechercheAdresse.getText(), 17);
            }
        });

        tgCartesOpenLayers.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgCartesOpenLayers.getSelectedToggle() != null) {
                setStrCartoActive(tgCartesOpenLayers.getSelectedToggle().getUserData().toString());
                changeCarte(getStrCartoActive());
            }
        });

        apOpenLayers.widthProperty().addListener((ov, av, nv) -> {
            if (bChoixMarqueur) {
                lblAttente.setPrefWidth(apOpenLayers.getPrefWidth() - 220);
                navigateurCarte.getWebView().setPrefWidth((double) nv - 200);
                getApChoixCartographie().setLayoutX((double) nv - 180);
                btnFerme.setLayoutX((double) nv - 35);
            } else {
                navigateurCarte.getWebView().setPrefWidth((double) nv);

            }
        });

        apOpenLayers.heightProperty().addListener((ov, av, nv) -> {
            if (bChoixMarqueur) {
                lblAttente.setPrefHeight(apOpenLayers.getPrefHeight() - 120);
                navigateurCarte.getWebView().setPrefHeight((double) nv - 80);
                btnFerme.setLayoutY(10);
            } else {
                navigateurCarte.getWebView().setPrefHeight((double) nv);
            }
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
     *
     */
    public class JavaApplication {

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
    }

}
