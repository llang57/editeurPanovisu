/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getPoGeolocalisation;
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
    private String strCartoActive="";
    private String bingApiKey = "";
    // Références aux TextFields pour mise à jour automatique
    private TextField tfLongitudeRef = null;
    private TextField tfLatitudeRef = null;
    // Flag pour savoir si le marqueur a été mis à jour par JavaScript
    private boolean marqueurMisAJourParJS = false;
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
        try {
            navigateurCarte.getWebEngine().executeScript("if (typeof centerOn === 'function') { centerOn(" + coordonnees.getLatitude() + "," + coordonnees.getLongitude() + "," + iFacteurZoom + "); }");
        } catch (netscape.javascript.JSException e) {
            System.err.println("Erreur allerCoordonnees: " + e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public CoordonneesGeographiques recupereCoordonnees() {
        // Si le marqueur a été mis à jour par JavaScript via updateCoordinates(), on utilise ces coordonnées
        if (marqueurMisAJourParJS && marqueur != null) {
            System.out.println("✅ Récupération depuis marqueur mis à jour par JS: Lat=" + marqueur.getLatitude() + ", Lng=" + marqueur.getLongitude());
            return marqueur;
        }
        
        // Sinon, on tente de récupérer depuis JavaScript (méthode legacy)
        CoordonneesGeographiques coordonnees = new CoordonneesGeographiques();
        try {
            Object result = navigateurCarte.getWebEngine().executeScript("typeof getCenter === 'function' ? JSON.stringify(getCenter()) : null");
            String strCoord = result != null ? result.toString() : "";
            
            // Si le résultat est un objet JSON, on l'ignore (problème de format)
            if (strCoord.startsWith("{")) {
                System.err.println("⚠️ Format JSON détecté, utilisation de getMarkerPosition() à la place");
                // Essayer de récupérer la position du marqueur directement
                result = navigateurCarte.getWebEngine().executeScript(
                    "typeof currentMarker !== 'undefined' && currentMarker ? " +
                    "currentMarker.getLatLng().lat + ';' + currentMarker.getLatLng().lng : null"
                );
                strCoord = result != null ? result.toString() : "";
            }
            
            if (strCoord.contains(";")) {
                coordonnees.setLongitude(Double.parseDouble(strCoord.split(";")[1]));
                coordonnees.setLatitude(Double.parseDouble(strCoord.split(";")[0]));
                System.out.println("✅ Coordonnées récupérées depuis JavaScript: Lat=" + coordonnees.getLatitude() + ", Lng=" + coordonnees.getLongitude());
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur recupereCoordonnees: " + e.getMessage());
            e.printStackTrace();
        }
        return coordonnees;
    }

    /**
     *
     * @param iNumeroMarqueur
     */
    public void retireMarqueur(int iNumeroMarqueur) {
        try {
            navigateurCarte.getWebEngine().executeScript("if (typeof removeMarker === 'function') { removeMarker(" + iNumeroMarqueur + "); }");
        } catch (netscape.javascript.JSException e) {
            System.err.println("Erreur retireMarqueur: " + e.getMessage());
        }
    }

    /**
     *
     * @param iNumeroMarqueur
     * @param coordMarqueur
     * @param strHTML
     */
    public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coordMarqueur, String strHTML) {
        try {
            navigateurCarte.getWebEngine().executeScript("if (typeof ajouteMarqueur === 'function') { ajouteMarqueur(" + iNumeroMarqueur + "," + coordMarqueur.getLongitude() + "," + coordMarqueur.getLatitude() + ",\"" + strHTML + "\"); }");
        } catch (Exception e) {
            System.err.println("Erreur ajouteMarqueur: " + e.getMessage());
        }
    }

    /**
     *
     * @param strAdresse
     * @param iFacteurZoom
     */
    public void allerAdresse(String strAdresse, int iFacteurZoom) {
        System.out.println("📞 Java allerAdresse() appelé avec: '" + strAdresse + "', zoom=" + iFacteurZoom);
        // Réinitialiser le flag car on va chercher une nouvelle adresse
        marqueurMisAJourParJS = false;
        try {
            // Vérifier quelle fonction existe dans le JavaScript
            Boolean hasChercheAdresse = (Boolean) navigateurCarte.getWebEngine().executeScript("typeof chercheAdresse === 'function'");
            Boolean hasAllerAdresse = (Boolean) navigateurCarte.getWebEngine().executeScript("typeof allerAdresse === 'function'");
            
            System.out.println("🔍 Fonctions JS disponibles:");
            System.out.println("   - chercheAdresse: " + hasChercheAdresse);
            System.out.println("   - allerAdresse: " + hasAllerAdresse);
            
            if (hasAllerAdresse) {
                System.out.println("✅ Appel allerAdresse() JavaScript...");
                String jsCode = "allerAdresse('" + strAdresse.replace("'", "\\'") + "')";
                System.out.println("📝 Code JS: " + jsCode);
                navigateurCarte.getWebEngine().executeScript(jsCode);
            } else if (hasChercheAdresse) {
                System.out.println("✅ Appel chercheAdresse() JavaScript...");
                navigateurCarte.getWebEngine().executeScript("chercheAdresse('" + strAdresse.replace("'", "\\'") + "'," + iFacteurZoom + ")");
            } else {
                System.err.println("❌ Aucune fonction de recherche trouvée dans le JavaScript!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur dans allerAdresse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param iFacteurZoom
     */
    public void choixZoom(int iFacteurZoom) {
        try {
            navigateurCarte.getWebEngine().executeScript("if (typeof choixZoom === 'function') { choixZoom(" + iFacteurZoom + "); }");
        } catch (Exception e) {
            System.err.println("Erreur choixZoom: " + e.getMessage());
        }
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
            // Note: setBingApiKey n'est plus utilisé avec la carte simple
            try {
                navigateurCarte.getWebEngine().executeScript("if (typeof setBingApiKey === 'function') { setBingApiKey(\"" + bingApiKey + "\"); }");
            } catch (Exception e) {
                System.out.println("setBingApiKey non disponible (normal avec carte simple)");
            }
        }
        afficheCartesOpenlayer();
    }

    /**
     *
     * @return
     */
    public String recupereCartographiesOpenLayers() {
        try {
            Object result = navigateurCarte.getWebEngine().executeScript("typeof getNomsLayers === 'function' ? getNomsLayers() : 'OpenStreetMap,Satellite'");
            return result != null ? result.toString() : "OpenStreetMap,Satellite";
        } catch (Exception e) {
            System.err.println("Erreur recupereCartographiesOpenLayers: " + e.getMessage());
            return "OpenStreetMap,Satellite";
        }
    }

    public void changeCarte(String strCarto) {
        try {
            navigateurCarte.getWebEngine().executeScript("if (typeof setMapType === 'function') { setMapType('" + (strCarto.toLowerCase().contains("satellite") ? "satellite" : "osm") + "'); }");
        } catch (Exception e) {
            System.err.println("Erreur changeCarte: " + e.getMessage());
        }
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
        // Stocker les références aux TextFields pour mise à jour automatique
        this.tfLongitudeRef = tfLongitude;
        this.tfLatitudeRef = tfLatitude;
        
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
            System.out.println("🔄 WebEngine State Change: " + from + " → " + to);
            
            if (to == State.SUCCEEDED) {
                System.out.println("✅✅✅ NavigateurCarte chargé avec SUCCÈS!");
                navigateurCarte.setVisible(true);

                valideBingApiKey(getBingApiKey());
                JSObject window = (JSObject) navigateurCarte.getWebEngine().executeScript("window");
                JavaApplication javaApp = new JavaApplication();
                window.setMember("javafx", javaApp);
                window.setMember("javaConnector", javaApp); // Connecteur pour updateCoordinates
                System.out.println("✅ Pont JavaScript configuré (javafx + javaConnector)");
                System.out.println("🔄 Appel allerAdresse() de test...");
                allerAdresse("Metz rue Serpenoise", 14);
                System.out.println("✅ bDebut mis à TRUE");
                bDebut = true;
                getTabInterface().setDisable(false);
            } else if (to == State.FAILED) {
                System.err.println("❌❌❌ ÉCHEC du chargement de la carte!");
                Throwable exception = navigateurCarte.getWebEngine().getLoadWorker().getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
        
        // Si la page est déjà chargée, rendre visible immédiatement
        if (navigateurCarte.getWebEngine().getLoadWorker().getState() == State.SUCCEEDED) {
            System.out.println("✅ NavigateurCarte déjà chargé - affichage immédiat");
            navigateurCarte.setVisible(true);
            valideBingApiKey(getBingApiKey());
            JSObject window = (JSObject) navigateurCarte.getWebEngine().executeScript("window");
            JavaApplication javaApp = new JavaApplication();
            window.setMember("javafx", javaApp);
            window.setMember("javaConnector", javaApp); // Connecteur pour updateCoordinates
            System.out.println("✅ Pont JavaScript configuré (javafx + javaConnector)");
            allerAdresse("Metz rue Serpenoise", 14);
            bDebut = true;
            getTabInterface().setDisable(false);
        }
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
                    getPoGeolocalisation().setbValide(true);
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
            System.out.println("🔍 Bouton Recherche cliqué!");
            System.out.println("📊 bDebut = " + bDebut);
            System.out.println("📝 Adresse à rechercher: '" + tfRechercheAdresse.getText() + "'");
            
            if (bDebut) {
                System.out.println("✅ Appel allerAdresse()...");
                allerAdresse(tfRechercheAdresse.getText(), 17);
            } else {
                System.out.println("❌ bDebut est false - carte pas encore chargée!");
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
     * Classe pour la communication JavaScript <-> Java
     */
    public class JavaApplication {

        /**
         *
         * @param msg
         */
        public void adresseInconnue(String msg) {
            //System.out.println("Adresse Inconnue\n" + msg);
        }

        /**
         *
         * @param lon
         * @param lat
         */
        public void adresseTrouvee(double lon, double lat) {
            //System.out.println(
//                    "Adresse trouvée aux coordonnées : " + CoordonneesGeographiques.toDMS(lat) + "  " + CoordonneesGeographiques.toDMS(lon)
//            );
        }

        /**
         *
         * @param strChaine
         */
        public void afficheChaine(String strChaine) {
            System.out.println(strChaine);
        }
        
        /**
         * Méthode appelée depuis JavaScript pour mettre à jour les coordonnées
         * @param lon Longitude en degrés décimaux
         * @param lat Latitude en degrés décimaux
         */
        public void updateCoordinates(double lon, double lat) {
            System.out.println("📍 Coordonnées reçues de JavaScript: Lat=" + lat + ", Lng=" + lon);
            System.out.println("🔍 tfLongitudeRef = " + (tfLongitudeRef != null ? "OK" : "NULL"));
            System.out.println("🔍 tfLatitudeRef = " + (tfLatitudeRef != null ? "OK" : "NULL"));
            
            // Mettre à jour le marqueur
            if (marqueur == null) {
                marqueur = new CoordonneesGeographiques();
            }
            marqueur.setLongitude(lon);
            marqueur.setLatitude(lat);
            marqueurMisAJourParJS = true; // Indiquer que le marqueur a été mis à jour par JavaScript
            System.out.println("✅ Marqueur mis à jour: Lat=" + marqueur.getLatitude() + ", Lng=" + marqueur.getLongitude() + " (marqueurMisAJourParJS=true)");
            
            // Mettre à jour les TextFields si présents
            javafx.application.Platform.runLater(() -> {
                if (tfLongitudeRef != null) {
                    String lonDMS = CoordonneesGeographiques.toDMS(lon);
                    tfLongitudeRef.setText(lonDMS);
                    System.out.println("✅ TextField Longitude mis à jour: " + lonDMS);
                } else {
                    System.err.println("❌ tfLongitudeRef est NULL!");
                }
                
                if (tfLatitudeRef != null) {
                    String latDMS = CoordonneesGeographiques.toDMS(lat);
                    tfLatitudeRef.setText(latDMS);
                    System.out.println("✅ TextField Latitude mis à jour: " + latDMS);
                } else {
                    System.err.println("❌ tfLatitudeRef est NULL!");
                }
            });
        }
    }

}
