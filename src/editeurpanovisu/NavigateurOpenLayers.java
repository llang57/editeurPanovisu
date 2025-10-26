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
import javafx.scene.layout.VBox;
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
    private AnchorPane apOpenLayersContainer; // Container pour lazy loading
    private boolean bChoixMarqueurSaved; // Stocké pour lazy loading
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
     * Initialise NavigateurCarte en lazy loading (uniquement au premier appel)
     * pour éviter l'erreur "texture is null" au démarrage (bug JavaFX 19)
     */
    @SuppressWarnings("unused")
    public void ensureNavigateurCarteInitialized() {
        if (navigateurCarte != null) {
            return; // Déjà initialisé
        }
        
        if (apOpenLayersContainer == null) {
            System.err.println("❌ apOpenLayersContainer n'est pas encore créé!");
            return;
        }
        
        System.out.println("🚀 Initialisation LAZY de NavigateurCarte...");
        navigateurCarte = new NavigateurCarte();
        
        // Constantes pour la nouvelle architecture
        final double HEADER_HEIGHT = 50;
        final double PANEL_RIGHT_WIDTH = 200;
        final double SPACING = 10;
        
        // Position de la carte : juste en dessous du header, à gauche
        navigateurCarte.setLayoutX(SPACING);
        navigateurCarte.setLayoutY(HEADER_HEIGHT + SPACING);
        navigateurCarte.setVisible(false);
        
        // Configuration des dimensions : largeur = container - panneau droit - marges
        // hauteur = container - header - marges
        // IMPORTANT: Modifier NavigateurCarte (Region) et non WebView (qui est lié/bound)
        if (bChoixMarqueurSaved) {
            navigateurCarte.setPrefWidth(apOpenLayersContainer.getPrefWidth() - PANEL_RIGHT_WIDTH - (2 * SPACING));
            navigateurCarte.setPrefHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
            navigateurCarte.setMinWidth(apOpenLayersContainer.getPrefWidth() - PANEL_RIGHT_WIDTH - (2 * SPACING));
            navigateurCarte.setMinHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
            navigateurCarte.setMaxWidth(apOpenLayersContainer.getPrefWidth() - PANEL_RIGHT_WIDTH - (2 * SPACING));
            navigateurCarte.setMaxHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
        } else {
            navigateurCarte.setPrefWidth(apOpenLayersContainer.getPrefWidth() - (2 * SPACING));
            navigateurCarte.setPrefHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
            navigateurCarte.setMinWidth(apOpenLayersContainer.getPrefWidth() - (2 * SPACING));
            navigateurCarte.setMinHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
            navigateurCarte.setMaxWidth(apOpenLayersContainer.getPrefWidth() - (2 * SPACING));
            navigateurCarte.setMaxHeight(apOpenLayersContainer.getPrefHeight() - HEADER_HEIGHT - (2 * SPACING));
            navigateurCarte.setLayoutX(SPACING);
            navigateurCarte.setLayoutY(HEADER_HEIGHT + SPACING);
        }
        
        // Ajouter au container
        apOpenLayersContainer.getChildren().add(navigateurCarte);
        
        // Configuration des listeners WebEngine
        navigateurCarte.getWebEngine().getLoadWorker().stateProperty().addListener((paramObservableValue, from, to) -> {
            System.out.println("🔄 WebEngine State Change: " + from + " → " + to);
            
            if (to == State.SUCCEEDED) {
                System.out.println("✅✅✅ NavigateurCarte chargé avec SUCCÈS!");
                navigateurCarte.setVisible(true);

                valideBingApiKey(getBingApiKey());
                JSObject window = (JSObject) navigateurCarte.getWebEngine().executeScript("window");
                JavaApplication javaApp = new JavaApplication();
                window.setMember("javafx", javaApp);
                window.setMember("javaConnector", javaApp);
                System.out.println("✅ Pont JavaScript configuré (javafx + javaConnector)");
                // NE PAS centrer sur Metz ici - le code appelant gèrera le positionnement
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
            window.setMember("javaConnector", javaApp);
            System.out.println("✅ Pont JavaScript configuré (javafx + javaConnector)");
            // NE PAS centrer sur Metz ici - le code appelant gèrera le positionnement
            bDebut = true;
            getTabInterface().setDisable(false);
        }
        
        // Pas de listeners de redimensionnement - taille fixe
    }

    /**
     *
     * @param tfLongitude
     * @param tfLatitude
     * @param bChoixMarqueur
     * @return
     */
    @SuppressWarnings("unused")
    public AnchorPane afficheNavigateurOpenLayer(TextField tfLongitude, TextField tfLatitude, boolean bChoixMarqueur) {
        // Stocker les références aux TextFields pour mise à jour automatique
        this.tfLongitudeRef = tfLongitude;
        this.tfLatitudeRef = tfLatitude;
        this.bChoixMarqueurSaved = bChoixMarqueur; // Sauvegarder pour lazy loading
        
        apOpenLayersContainer = new AnchorPane();
        AnchorPane apOpenLayers = apOpenLayersContainer;
        apOpenLayers.getStyleClass().add("dialog-content-pane");
        apOpenLayers.setStyle("-fx-border-width : 1px;-fx-border-style : solid;-fx-border-color : #777;"
                + "-fx-border-color: #777;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;"
        );
        
        // ⚠️ LAZY LOADING: NavigateurCarte sera créé au premier clic sur géolocalisation
        navigateurCarte = null;
        
        File fileRep = new File("");
        String strRepertAppli = fileRep.getAbsolutePath();
        
        // ============ BARRE DU HAUT : Recherche + Bouton Fermer ============
        final double HEADER_HEIGHT = 50;
        final double PANEL_RIGHT_WIDTH = 200;
        final double SPACING = 10;
        
        // Champ de recherche d'adresse
        TextField tfRechercheAdresse = new TextField();
        tfRechercheAdresse.setPromptText("Rechercher une adresse...");
        tfRechercheAdresse.setPrefWidth(300);
        tfRechercheAdresse.setLayoutX(SPACING);
        tfRechercheAdresse.setLayoutY(SPACING);
        
        // Bouton recherche
        Button btnRechercheAdresse = new Button("🔍 Rechercher", 
            new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/loupe.png", -1, 16, true, true)));
        btnRechercheAdresse.setLayoutX(320);
        btnRechercheAdresse.setLayoutY(SPACING);
        btnRechercheAdresse.setPrefWidth(120);
        
        // Label d'attente (sur la zone carte)
        Label lblAttente = new Label("⏳ Chargement de la carte en cours...\nVeuillez patienter");
        lblAttente.setAlignment(Pos.CENTER);
        lblAttente.setStyle("-fx-background-color : #777; -fx-opacity: 0.9; -fx-padding: 20px;");
        lblAttente.setTextFill(Color.WHITE);
        lblAttente.setLayoutX(SPACING);
        lblAttente.setLayoutY(HEADER_HEIGHT + SPACING);
        
        // Bouton fermer (en haut à droite) - sera positionné après
        Button btnFerme = new Button("✖ Fermer");
        btnFerme.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");
        btnFerme.setLayoutY(SPACING);
        btnFerme.setPrefWidth(100);
        btnFerme.setLayoutX(900 - 110 - SPACING); // Position calculée pour largeur 900px
        btnFerme.setOnAction((e) -> {
            apOpenLayers.setVisible(false);
        });
        
        apOpenLayers.getChildren().addAll(tfRechercheAdresse, btnRechercheAdresse, lblAttente, btnFerme);
        
        if (bChoixMarqueur) {
            // ============ PANNEAU DE DROITE : Boutons de contrôle ============
            VBox panelRight = new VBox(SPACING);
            panelRight.getStyleClass().add("panel-right-geoloc"); // Utilise le CSS de l'application
            panelRight.setPrefWidth(PANEL_RIGHT_WIDTH);
            panelRight.setLayoutY(HEADER_HEIGHT);
            panelRight.setLayoutX(900 - PANEL_RIGHT_WIDTH); // Position à droite (900 = largeur fenêtre)
            
            // Boutons de contrôle
            Button btnRecupereCoordonnees = new Button("📍 Valider position");
            btnRecupereCoordonnees.setPrefWidth(PANEL_RIGHT_WIDTH - 20);
            btnRecupereCoordonnees.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            
            Button btnCentreCarte = new Button("🎯 Recentrer carte");
            btnCentreCarte.setPrefWidth(PANEL_RIGHT_WIDTH - 20);
            
            // Séparateur
            Label lblSeparator = new Label("─────────────────");
            lblSeparator.setStyle("-fx-text-fill: #999;");
            
            // Label pour le choix de carte
            Label lblChoixCarte = new Label("Type de carte :");
            lblChoixCarte.setStyle("-fx-font-weight: bold; -fx-padding: 10 0 5 0;");
            
            // Configuration du panel de choix de carte - utilise le CSS
            getApChoixCartographie().setMaxWidth(PANEL_RIGHT_WIDTH - 20);
            getApChoixCartographie().getStyleClass().add("panel-choix-carte");
            
            // Ajout des éléments au panneau droit
            panelRight.getChildren().addAll(
                btnRecupereCoordonnees,
                btnCentreCarte,
                lblSeparator,
                lblChoixCarte,
                getApChoixCartographie()
            );
            
            apOpenLayers.getChildren().add(panelRight);
            
            // Configuration initiale des dimensions (taille fixe - pas de listeners)
            lblAttente.setPrefSize(900 - PANEL_RIGHT_WIDTH - 30, 650 - HEADER_HEIGHT - 20);
            panelRight.setPrefHeight(650 - HEADER_HEIGHT);
            
            // Configuration des listeners pour les boutons
            btnCentreCarte.setOnAction((e) -> {
                if (bDebut) {
                    if (getMarqueur() != null) {
                        allerCoordonnees(getMarqueur(), 17);
                    }
                }
            });
            
            btnRecupereCoordonnees.setOnAction((e) -> {
                if (bDebut) {
                    System.out.println("🎯 Validation position marqueur - récupération coordonnées...");
                    // Récupérer la position du marqueur à l'index 0 (pas le centre de la carte)
                    setMarqueur(navigateurCarte.recupereCoordonnees(0));
                    System.out.println("📍 Coordonnées récupérées: " + getMarqueur().getLatitude() + ", " + getMarqueur().getLongitude());
                    
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
                    System.out.println("✅ Position validée et marqueur mis à jour");
                }
            });
        }
        
        // Tout le code de configuration navigateurCarte déplacé dans ensureNavigateurCarteInitialized()
        
        // Configuration des listeners pour le champ de recherche
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

        // Les listeners widthProperty et heightProperty sont maintenant dans ensureNavigateurCarteInitialized()
        // car ils dépendent de navigateurCarte
        
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
     * @return true si la carte est chargée et prête
     */
    public boolean isbDebut() {
        return bDebut;
    }

    /**
     * Classe pour la communication JavaScript &lt;-&gt; Java
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
