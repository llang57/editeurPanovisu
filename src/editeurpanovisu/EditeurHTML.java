/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import java.awt.Desktop;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditeurHTML {

    protected transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public void firePropertyChange(String propertyName, BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue != null && newValue != null && oldValue.compareTo(newValue) == 0) {
            return;
        }
        changeSupport.firePropertyChange(new PropertyChangeEvent(this, propertyName,
                oldValue, newValue));

    }
    
    
    private ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
    private boolean bValide = false;
    private boolean bAnnule = false;

    private HotspotHTML hsHTML;
    private ToggleGroup tgPositionHTML;
    private ToggleButton tgbGauche;
    private ToggleButton tgbCentre;
    private ToggleButton tgbDroite;
    private Slider slOpacite = new Slider(0, 1, 0.7);

    private final File fileRep = new File("");
    private final String strAppPath = fileRep.getAbsolutePath();
    private String strNomRepertHTML = strAppPath + "/pagesHTML";
    private Stage stEditeurHTML;
    private String strImages;
    private HTMLEditor heEditeurHTML;
    private Node nodeBarreIconesSuperieure;
    private Node nodeEditeurHTML;
    private ToolBar tbBarreIconesSuperieure;
    private WebView wvEditeurHTML;
    private WebEngine engEditeurHTML;
    private Button btnValide;
    private Button btnAnnule;
    private Button btnAjouteImage;
    private Button btnAjouteLien;
    private AnchorPane apDialog;
    private AnchorPane apEditeur;
    private AnchorPane apPrincipale;
    private String strNomFichierImage;
    private boolean bDejaSauve = true;
    private ColorPicker cpCouleurHTML;

    private final String jsCodeInsertHtml
            = "function insertHtmlAtCursor(html) {\n"
            + " var range, node;\n"
            + " if (window.getSelection && window.getSelection().getRangeAt) {\n"
            + " range = window.getSelection().getRangeAt(0);\n"
            + " node = range.createContextualFragment(html);\n"
            + " range.insertNode(node);\n"
            + " } else if (document.selection && document.selection.createRange) {\n"
            + " document.selection.createRange().pasteHTML(html);\n"
            + " }\n"
            + "};\n"
            + "insertHtmlAtCursor('####html####')";

    private static final String strSelectText
            = "(function getSelectionText() {\n"
            + " var text = \"\";\n"
            + " if (window.getSelection) {\n"
            + " text = window.getSelection().toString();\n"
            + " } else if (document.selection && document.selection.type != \"Control\") {\n"
            + " text = document.selection.createRange().text;\n"
            + " }\n"
            + " "
            + " return text;\n"
            + "})()";

    private static String hex(int i) {
        return Integer.toHexString(i);
    }

//a method to convert to a javas/js style string
//https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html
    private static String escapeJavaStyleString(String strEntree,
            boolean bEscapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder sbSortie = new StringBuilder("");
        if (strEntree == null) {
            return null;
        }
        int sz;
        sz = strEntree.length();
        for (int i = 0; i < sz; i++) {
            char chEntree = strEntree.charAt(i);

// handle unicode
            if (chEntree > 0xfff) {
                sbSortie.append("\\u").append(hex(chEntree));
            } else if (chEntree > 0xff) {
                sbSortie.append("\\u0").append(hex(chEntree));
            } else if (chEntree > 0x7f) {
                sbSortie.append("\\u00").append(hex(chEntree));
            } else if (chEntree < 32) {
                switch (chEntree) {
                    case '\b':
                        sbSortie.append('\\');
                        sbSortie.append('b');
                        break;
                    case '\n':
                        sbSortie.append('\\');
                        sbSortie.append('n');
                        break;
                    case '\t':
                        sbSortie.append('\\');
                        sbSortie.append('t');
                        break;
                    case '\f':
                        sbSortie.append('\\');
                        sbSortie.append('f');
                        break;
                    case '\r':
                        sbSortie.append('\\');
                        sbSortie.append('r');
                        break;
                    default:
                        if (chEntree > 0xf) {
                            sbSortie.append("\\u00").append(hex(chEntree));
                        } else {
                            sbSortie.append("\\u000").append(hex(chEntree));
                        }
                        break;
                }
            } else {
                switch (chEntree) {
                    case '\'':
                        if (bEscapeSingleQuote) {
                            sbSortie.append('\\');
                        }
                        sbSortie.append('\'');
                        break;
                    case '"':
                        sbSortie.append('\\');
                        sbSortie.append('"');
                        break;
                    case '\\':
                        sbSortie.append('\\');
                        sbSortie.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            sbSortie.append('\\');
                        }
                        sbSortie.append('/');
                        break;
                    default:
                        sbSortie.append(chEntree);
                        break;
                }
            }
        }
        return sbSortie.toString();
    }

    static public void copieFichierRepertoire(String strFichier, String strRepertoire) throws FileNotFoundException, IOException {
        File fileFrom = new File(strFichier);
        File fileTo = new File(strRepertoire + File.separator + strFichier.substring(strFichier.lastIndexOf(File.separator) + 1));
        Files.copy(fileFrom.toPath(), fileTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public String setTemplate() {
        String strTemplate = "";
        return strTemplate;
    }

    public void affiche(Number largeur, Number hauteur) {
        double diffHauteur = 260;
        bDejaSauve = true;
        int iLargeur = (int) largeur;
        int iHauteur = (int) hauteur;
        stEditeurHTML = new Stage(StageStyle.UTILITY);
        stEditeurHTML.initModality(Modality.APPLICATION_MODAL);
        stEditeurHTML.setResizable(true);
        heEditeurHTML = new HTMLEditor();
        if (getHsHTML().getStrTexteHTML().equals("")) {
            heEditeurHTML.setHtmlText("<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                    + "        <style>\n"
                    + "            *{\n"
                    + "                font-family: Verdana,Arial,sans-serif;                \n"
                    + "            }           \n"
                    + "            h1,h2,h3,h4,h5{\n"
                    + "                font-size : 1em;\n"
                    + "                font-weight: bold;                \n"
                    + "                color : #444;\n "
                    + "            }\n"
                    + "            h1{\n"
                    + "                font-size: 1.3em;\n"
                    + "            }\n"
                    + "            h2{\n"
                    + "                font-size: 1.2em;\n"
                    + "            }\n"
                    + "            h3{\n"
                    + "                font-size: 1.1em;\n"
                    + "            }\n"
                    + "\n"
                    + "        </style>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "    </body>\n"
                    + "</html>\n"
                    + "");
        } else {
            heEditeurHTML.setHtmlText(getHsHTML().getStrTexteHTML());
        }
        nodeBarreIconesSuperieure = heEditeurHTML.lookup(".bottom-toolbar");
        nodeEditeurHTML = heEditeurHTML.lookup(".web-view");
        strImages = strAppPath + File.separator + "images";

        apDialog = new AnchorPane();
        apDialog.setVisible(false);
        apEditeur = new AnchorPane();
        apPrincipale = new AnchorPane(apEditeur);
        apPrincipale.setPrefSize(iLargeur, iHauteur);
        apPrincipale.getChildren().add(apDialog);
        apEditeur.setPrefSize(iLargeur, iHauteur);

        Scene scPrincipale = new Scene(apPrincipale, iLargeur, iHauteur, null);
        scPrincipale.setFill(Color.gray(0.2));
        stEditeurHTML.setScene(scPrincipale);
        heEditeurHTML.setFocusTraversable(true);
        heEditeurHTML.requestFocus();
        heEditeurHTML.setLayoutX(10);
        heEditeurHTML.setLayoutY(10);
        heEditeurHTML.setPrefHeight(iHauteur - diffHauteur);
        heEditeurHTML.setMinHeight(iHauteur - diffHauteur);
        heEditeurHTML.setMaxHeight(iHauteur - diffHauteur);
        heEditeurHTML.setPrefWidth(getHsHTML().getLargeurHTML());
        heEditeurHTML.setMaxWidth(getHsHTML().getLargeurHTML());
        heEditeurHTML.setMinWidth(getHsHTML().getLargeurHTML());
        AnchorPane apEditeur2 = new AnchorPane(heEditeurHTML);
        ImageView ivFond = new ImageView(getPanoramiquesProjet()[getiPanoActuel()].getImgPanoramique());
        apEditeur2.setLayoutX(5);
        apEditeur2.setLayoutY(145);
        apEditeur2.setPrefWidth(iLargeur - 20);
        apEditeur2.setPrefHeight(heEditeurHTML.getPrefHeight() + 20);
        ivFond.setPreserveRatio(false);
        ivFond.setFitWidth(apEditeur2.getPrefWidth());
        ivFond.setFitHeight(apEditeur2.getPrefHeight());
        ivFond.setLayoutX(apEditeur2.getLayoutX());
        ivFond.setLayoutY(apEditeur2.getLayoutY());
        apEditeur.getChildren().addAll(ivFond, apEditeur2);
        int iRouge = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
        int iBleu = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
        int iVert = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
        String strCouleur = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getHsHTML().getOpaciteHTML() + ")";
        apEditeur2.setStyle("-fx-background-color : " + strCouleur + ";");
        switch (getHsHTML().getStrPositionHTML()) {
            case "left":
                heEditeurHTML.setLayoutX(10);
                break;
            case "center":
                heEditeurHTML.setLayoutX((iLargeur - 30 - heEditeurHTML.getPrefWidth()) / 2);
                break;
            case "right":
                heEditeurHTML.setLayoutX(iLargeur - 30 - heEditeurHTML.getPrefWidth() - 35);
                break;
        }

        //heEditeurHTML.setLayoutX(10);
        if (getHsHTML().isbLienExterieur()) {
            heEditeurHTML.setDisable(true);
        }

        ToggleGroup tgLien = new ToggleGroup();
        ToggleButton tgbLienExterieur = new ToggleButton(rbLocalisation.getString("editeurHTML.lienExterne"));
        tgbLienExterieur.setLayoutX(10);
        tgbLienExterieur.setLayoutY(10);
        tgbLienExterieur.setSelected(getHsHTML().isbLienExterieur());
        tgbLienExterieur.setToggleGroup(tgLien);

        ToggleButton tgbEditeurInterne = new ToggleButton(rbLocalisation.getString("editeurHTML.htmlInterne"));
        tgbEditeurInterne.setLayoutX(10);
        tgbEditeurInterne.setLayoutY(115);
        tgbEditeurInterne.setSelected(!getHsHTML().isbLienExterieur());

        tgbEditeurInterne.setToggleGroup(tgLien);
        TextField tfLienExterieur = new TextField(getHsHTML().getStrURLExterieure());
        tfLienExterieur.setPromptText("http://monsite.extension");
        tfLienExterieur.setPrefWidth(200);
        tfLienExterieur.setLayoutX(150);
        tfLienExterieur.setLayoutY(3);
        Tooltip tpLenExterieur = new Tooltip(rbLocalisation.getString("editeurHTML.urlComplete"));
        tfLienExterieur.setTooltip(tpLenExterieur);
        Label lblPositionEditeur = new Label(rbLocalisation.getString("editeurHTML.position"));
        lblPositionEditeur.setLayoutX(10);
        lblPositionEditeur.setLayoutY(45);
        tgPositionHTML = new ToggleGroup();
        tgbGauche = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlGauche.png", 16, 16, true, true)));
        tgbCentre = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlCentre.png", 16, 16, true, true)));
        tgbDroite = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlDroite.png", 16, 16, true, true)));
        tgbGauche.setSelected(getHsHTML().getStrPositionHTML().equals("left"));
        tgbCentre.setSelected(getHsHTML().getStrPositionHTML().equals("center"));
        tgbDroite.setSelected(getHsHTML().getStrPositionHTML().equals("right"));
        tgbGauche.setLayoutX(90);
        tgbGauche.setLayoutY(40);
        tgbGauche.setUserData("left");
        tgbGauche.setToggleGroup(tgPositionHTML);
        tgbCentre.setLayoutX(125);
        tgbCentre.setLayoutY(40);
        tgbCentre.setUserData("center");
        tgbCentre.setToggleGroup(tgPositionHTML);
        tgbDroite.setLayoutX(160);
        tgbDroite.setLayoutY(40);
        tgbDroite.setUserData("right");
        tgbDroite.setToggleGroup(tgPositionHTML);
        Label lblLargeurEditeur = new Label(rbLocalisation.getString("editeurHTML.largeur"));
        Slider slLargeurEditeur = new Slider(400, iLargeur - 30, getHsHTML().getLargeurHTML());
        lblLargeurEditeur.setLayoutX(250);
        lblLargeurEditeur.setLayoutY(45);
        slLargeurEditeur.setLayoutX(380);
        slLargeurEditeur.setLayoutY(45);
        Label lblCouleur = new Label(rbLocalisation.getString("editeurHTML.couleur"));
        lblCouleur.setLayoutX(10);
        lblCouleur.setLayoutY(75);
        cpCouleurHTML = new ColorPicker(Color.web(getHsHTML().getStrCouleurHTML()));
        cpCouleurHTML.setLayoutX(90);
        cpCouleurHTML.setLayoutY(70);
        Label lblOpacite = new Label(rbLocalisation.getString("editeurHTML.opacite"));
        lblOpacite.setLayoutX(250);
        lblOpacite.setLayoutY(75);
        slOpacite = new Slider(0, 1, getHsHTML().getOpaciteHTML());
        slOpacite.setLayoutX(380);
        slOpacite.setLayoutY(75);
        btnValide = new Button(rbLocalisation.getString("editeurHTML.valide"), new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        btnAnnule = new Button(rbLocalisation.getString("editeurHTML.annule"), new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValide.setPrefWidth(100);
        btnValide.setLayoutX(iLargeur - 130);
        btnValide.setLayoutY(iHauteur - 50);
        btnAnnule.setPrefWidth(100);
        btnAnnule.setLayoutX(iLargeur - 250);
        btnAnnule.setLayoutY(iHauteur - 50);
        apEditeur.getChildren().addAll(
                tgbLienExterieur, tfLienExterieur,
                lblPositionEditeur, tgbGauche, tgbCentre, tgbDroite, lblLargeurEditeur, slLargeurEditeur,
                lblCouleur, cpCouleurHTML, lblOpacite, slOpacite,
                tgbEditeurInterne,
                btnAnnule, btnValide
        );
        stEditeurHTML.show();
        //hideImageNodesMatching(htmlEditor, Pattern.compile(".*(Cut|Copy|Paste).*"), 0);
        int i = 0;
        if (nodeBarreIconesSuperieure instanceof ToolBar && nodeEditeurHTML instanceof WebView) {
            tbBarreIconesSuperieure = (ToolBar) nodeBarreIconesSuperieure;
            wvEditeurHTML = (WebView) nodeEditeurHTML;
            wvEditeurHTML.setMaxHeight(heEditeurHTML.getPrefHeight() - 70);
            wvEditeurHTML.setMinHeight(heEditeurHTML.getPrefHeight() - 70);
            wvEditeurHTML.setPrefHeight(heEditeurHTML.getPrefHeight() - 70);
            engEditeurHTML = wvEditeurHTML.getEngine();

            heEditeurHTML.setOnKeyPressed((evenement) -> {
                getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
                bDejaSauve = false;
            });
            engEditeurHTML.locationProperty().addListener((ov, ancValeur, nouvValeur) -> {
                try {
                    Desktop d = Desktop.getDesktop();
                    URI uriAdresse = new URI(nouvValeur);
                    System.out.println(uriAdresse);
                    d.browse(uriAdresse);
                    engEditeurHTML.getLoadWorker().cancel();
                    Platform.runLater(() -> {
                        heEditeurHTML.setHtmlText(getHsHTML().getStrTexteHTML());

                    });
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tbBarreIconesSuperieure.getItems().add(new Separator(Orientation.VERTICAL));
            ImageView ivAjouteImage = new ImageView(new Image("file:" + strImages + File.separator + "ajouteImage.png", 22, 16, true, true));
            btnAjouteImage = new Button("", ivAjouteImage);
            Tooltip tpAjouteImage = new Tooltip(rbLocalisation.getString("editeurHTML.ajouteImage"));
            btnAjouteImage.setTooltip(tpAjouteImage);
            tbBarreIconesSuperieure.getItems().add(btnAjouteImage);

            ImageView ivAjouteLien = new ImageView(new Image("file:" + strImages + File.separator + "lien.png", 16, 16, true, true));
            btnAjouteLien = new Button("", ivAjouteLien);
            Tooltip tpAjouteLien = new Tooltip(rbLocalisation.getString("editeurHTML.ajouteLien"));
            btnAjouteLien.setTooltip(tpAjouteLien);
            tbBarreIconesSuperieure.getItems().add(btnAjouteLien);

        }

        heEditeurHTML.requestFocus();
        tfLienExterieur.textProperty().addListener((ov, ancValeur, nouvValeur) -> {
            getHsHTML().setStrURLExterieure(nouvValeur);
        });

        stEditeurHTML.setOnCloseRequest((event) -> {
            quitteEditeurHTML(stEditeurHTML);
            event.consume();
        });
        cpCouleurHTML.setOnAction((evt) -> {
            String strCouleurHTML = cpCouleurHTML.getValue().toString().substring(2, 8);
            getHsHTML().setStrCouleurHTML(strCouleurHTML);
            int iRouge1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
            int iBleu1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
            int iVert1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
            String strCouleur1 = "rgba(" + iRouge1 + "," + iVert1 + "," + iBleu1 + "," + getHsHTML().getOpaciteHTML() + ")";
            apEditeur2.setStyle("-fx-background-color : " + strCouleur1 + ";");
        });
        slOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                getHsHTML().setOpaciteHTML((double) newValue);
                int iRouge1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
                int iBleu1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
                int iVert1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
                String strCouleur1 = "rgba(" + iRouge1 + "," + iVert1 + "," + iBleu1 + "," + getHsHTML().getOpaciteHTML() + ")";
                apEditeur2.setStyle("-fx-background-color : " + strCouleur1 + ";");

            }
        });
        tgbLienExterieur.setOnAction((evt) -> {
            tgbLienExterieur.setSelected(true);
            getHsHTML().setbLienExterieur(true);
        });
        tgbEditeurInterne.setOnAction((evt) -> {
            tgbEditeurInterne.setSelected(true);
            getHsHTML().setbLienExterieur(false);
        });

        tgPositionHTML.selectedToggleProperty()
                .addListener((ov, old_toggle, new_toggle) -> {
                    if (new_toggle != null) {
                        getHsHTML().setStrPositionHTML(tgPositionHTML.getSelectedToggle().getUserData().toString());
                        switch (getHsHTML().getStrPositionHTML()) {
                            case "left":
                                heEditeurHTML.setLayoutX(10);
                                break;
                            case "center":
                                heEditeurHTML.setLayoutX((stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                                break;
                            case "right":
                                heEditeurHTML.setLayoutX(stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth() - 35);
                                break;
                        }

                    }
                });

        tgLien.selectedToggleProperty()
                .addListener((ov, old_toggle, new_toggle) -> {
                    if (tgbLienExterieur.isSelected()) {
                        tfLienExterieur.setDisable(false);
                        heEditeurHTML.setDisable(true);
                        getHsHTML().setbLienExterieur(true);
                    } else {
                        tfLienExterieur.setDisable(true);
                        heEditeurHTML.setDisable(false);
                        getHsHTML().setbLienExterieur(false);
                    }
                }
                );

        btnAnnule.setOnAction((evenement) -> {
            quitteEditeurHTML(stEditeurHTML);
        }
        );
        btnValide.setOnAction((evenement) -> {
            getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText().replace("[", "&lbrace;").replace("]", "&rbrace;"));
            setbValide(true);
            stEditeurHTML.hide();
        });

        btnAjouteLien.setOnAction(
                (evenement) -> {
                    afficheConfigLien();
                }
        );

        btnAjouteImage.setOnAction(
                (evenement) -> {
                    afficheConfigImage();
                }
        );
        slLargeurEditeur.valueProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    heEditeurHTML.setPrefWidth((double) nouvValeur);
                    heEditeurHTML.setMaxWidth((double) nouvValeur);
                    heEditeurHTML.setMinWidth((double) nouvValeur);
                    getHsHTML().setLargeurHTML((double) nouvValeur);
                    switch (getHsHTML().getStrPositionHTML()) {
                        case "left":
                            heEditeurHTML.setLayoutX(10);
                            break;
                        case "center":
                            heEditeurHTML.setLayoutX((stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                            break;
                        case "right":
                            heEditeurHTML.setLayoutX(stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth() - 35);
                            break;
                    }
                });

        stEditeurHTML.widthProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    System.out.println(nouvValeur);
                    slLargeurEditeur.setMax((double) nouvValeur - 35.d);
                    if (slLargeurEditeur.getValue() > (double) nouvValeur - 35.d) {
                        slLargeurEditeur.setValue((double) nouvValeur - 35.d);
                        heEditeurHTML.setPrefWidth((double) nouvValeur - 35.d);
                        heEditeurHTML.setMaxWidth((double) nouvValeur - 35.d);
                        heEditeurHTML.setMinWidth((double) nouvValeur - 35.d);
                    }
                    apEditeur2.setPrefWidth((double) nouvValeur - 20);
                    switch (getHsHTML().getStrPositionHTML()) {
                        case "left":
                            heEditeurHTML.setLayoutX(10);
                            break;
                        case "center":
                            heEditeurHTML.setLayoutX((stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                            break;
                        case "right":
                            heEditeurHTML.setLayoutX(stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth() - 35);
                            break;
                    }
                    apEditeur.setPrefWidth((double) nouvValeur);
                    apEditeur.setMinWidth((double) nouvValeur);
                    apEditeur.setMaxWidth((double) nouvValeur);
                    apPrincipale.setPrefWidth((double) nouvValeur);
                    apPrincipale.setMinWidth((double) nouvValeur);
                    apPrincipale.setMaxWidth((double) nouvValeur);
                    ivFond.setFitWidth(apEditeur2.getPrefWidth());

                    btnValide.setLayoutX((double) nouvValeur - 135);
                    btnAnnule.setLayoutX((double) nouvValeur - 255);
                    apDialog.setLayoutX((apPrincipale.getPrefWidth() - apDialog.getPrefWidth()) / 2);

                });

        stEditeurHTML.heightProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    System.out.println(nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    heEditeurHTML.setPrefHeight((double) nouvValeur - diffHauteur);
                    heEditeurHTML.setMaxHeight((double) nouvValeur - diffHauteur);
                    heEditeurHTML.setMinHeight((double) nouvValeur - diffHauteur);
                    apEditeur2.setPrefHeight(heEditeurHTML.getPrefHeight() + 20);
                    ivFond.setFitHeight(apEditeur2.getPrefHeight());
                    btnValide.setLayoutY((double) nouvValeur - 75);
                    btnAnnule.setLayoutY((double) nouvValeur - 75);
                    apDialog.setLayoutY((apPrincipale.getPrefHeight() - apDialog.getPrefHeight()) / 2);
                    wvEditeurHTML.setMaxHeight(heEditeurHTML.getPrefHeight() - 70);
                    wvEditeurHTML.setMinHeight(heEditeurHTML.getPrefHeight() - 70);
                    wvEditeurHTML.setPrefHeight(heEditeurHTML.getPrefHeight() - 70);
                });

    }

    private void quitteEditeurHTML(Stage stFenetre) {
        setbAnnule(true);
        System.out.println(stEditeurHTML);
        stFenetre.hide();
    }

    private void afficheConfigLien() {
        String strSelection = (String) engEditeurHTML.executeScript(strSelectText);
        List<String> strLstTarget = new ArrayList<>();
        strLstTarget.add("_self");
        strLstTarget.add("_blank");
        strLstTarget.add("_parent");
        ObservableList<String> strListeTarget = FXCollections.observableList(strLstTarget);
        int iLargeur = 400;
        int iHauteur = 200;

        apEditeur.setDisable(true);
        apDialog.setVisible(true);
        apDialog.getChildren().clear();
        apDialog.setMaxSize(iLargeur, iHauteur);
        apDialog.setMinSize(iLargeur, iHauteur);
        apDialog.setPrefSize(iLargeur, iHauteur);
        apDialog.setStyle("-fx-background-color : #ddd;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;"
        );
        apDialog.setLayoutX((apPrincipale.getPrefWidth() - apDialog.getPrefWidth()) / 2);
        apDialog.setLayoutY((apPrincipale.getPrefHeight() - apDialog.getPrefHeight()) / 2);
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("editeurHTML.ajouteLien"));
        lblBarrePersonnalisee.setMinWidth(iLargeur - 10);
        lblBarrePersonnalisee.setAlignment(Pos.CENTER);
        lblBarrePersonnalisee.setStyle("-fx-background-color : #777;");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setLayoutX(5);
        lblBarrePersonnalisee.setLayoutY(10);
        lblBarrePersonnalisee.setFont(Font.font(14));
        apDialog.getChildren().add(lblBarrePersonnalisee);
        Button btnValideLien = new Button(rbLocalisation.getString("editeurHTML.valide"), new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleLien = new Button(rbLocalisation.getString("editeurHTML.annule"), new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideLien.setPrefWidth(100);
        btnValideLien.setLayoutX(iLargeur - 130);
        btnValideLien.setLayoutY(iHauteur - 50);
        btnAnnuleLien.setPrefWidth(100);
        btnAnnuleLien.setLayoutX(iLargeur - 250);
        btnAnnuleLien.setLayoutY(iHauteur - 50);
        apDialog.getChildren().addAll(btnValideLien, btnAnnuleLien);
        Label lblTexteLien = new Label(rbLocalisation.getString("editeurHTML.texteLien"));
        lblTexteLien.setLayoutX(10);
        lblTexteLien.setLayoutY(40);
        Label lblUrlLien = new Label(rbLocalisation.getString("editeurHTML.url"));
        lblUrlLien.setLayoutX(10);
        lblUrlLien.setLayoutY(70);
        TextField tfTexteLien = new TextField(strSelection);
        tfTexteLien.setPrefWidth(200);
        tfTexteLien.setLayoutX(150);
        tfTexteLien.setLayoutY(40);
        TextField tfURLLien = new TextField("");
        tfURLLien.setPromptText("http://site.extension");

        tfURLLien.setPrefWidth(200);
        tfURLLien.setLayoutX(150);
        tfURLLien.setLayoutY(70);
        Label lblCibleLien = new Label(rbLocalisation.getString("editeurHTML.cibleLien"));
        lblCibleLien.setLayoutX(10);
        lblCibleLien.setLayoutY(100);

        ComboBox cbListeTarget = new ComboBox(strListeTarget);
        cbListeTarget.setLayoutX(150);
        cbListeTarget.setLayoutY(100);

        apDialog.getChildren().addAll(
                lblTexteLien, tfTexteLien,
                lblUrlLien, tfURLLien,
                lblCibleLien, cbListeTarget
        );
        btnValideLien.setOnAction((evenement) -> {
            String strAjouteLien = "<a href='" + tfURLLien.getText() + "' target='" + cbListeTarget.getPromptText() + "'>" + tfTexteLien.getText() + "</a>";
            engEditeurHTML.executeScript(jsCodeInsertHtml);
            getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
            getHsHTML().setStrTexteHTML(getHsHTML().getStrTexteHTML().replace("####html####" + strSelection, strAjouteLien).replace(strSelection + "####html####", strAjouteLien));
            heEditeurHTML.setHtmlText(getHsHTML().getStrTexteHTML());
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
            bDejaSauve = false;
        });
        btnAnnuleLien.setOnAction((evenement) -> {
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
        });
    }

    private void afficheConfigImage() {
        int iHauteur = 700;
        int iLargeur = 900;
        apEditeur.setDisable(true);
        apDialog.setVisible(true);
        apDialog.getChildren().clear();

        apDialog.setMaxSize(iLargeur, iHauteur);
        apDialog.setMinSize(iLargeur, iHauteur);
        apDialog.setPrefSize(iLargeur, iHauteur);
        apDialog.setStyle("-fx-background-color : #ddd;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;"
        );
        apDialog.setLayoutX((apPrincipale.getPrefWidth() - apDialog.getPrefWidth()) / 2);
        apDialog.setLayoutY((apPrincipale.getPrefHeight() - apDialog.getPrefHeight()) / 2);
        Button btnValideImage = new Button(rbLocalisation.getString("editeurHTML.valide"), new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleImage = new Button(rbLocalisation.getString("editeurHTML.annule"), new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideImage.setPrefWidth(100);
        btnValideImage.setLayoutX(iLargeur - 130);
        btnValideImage.setLayoutY(iHauteur - 50);
        btnAnnuleImage.setPrefWidth(100);
        btnAnnuleImage.setLayoutX(iLargeur - 250);
        btnAnnuleImage.setLayoutY(iHauteur - 50);
        Label lblChoixFichierImage = new Label(rbLocalisation.getString("editeurHTML.choixImage"));
        lblChoixFichierImage.setLayoutX(10);
        lblChoixFichierImage.setLayoutY(40);
        TextField tfChoixFichierImage = new TextField("");
        tfChoixFichierImage.setPrefWidth(200);
        tfChoixFichierImage.setLayoutX(150);
        tfChoixFichierImage.setLayoutY(40);
        Button btnChoixFichierImage = new Button("...");
        btnChoixFichierImage.setLayoutX(370);
        btnChoixFichierImage.setLayoutY(40);
        ToggleGroup tgPositionImage = new ToggleGroup();
        ImageView ivImageGauche = new ImageView(new Image("file:" + strImages + File.separator + "imageGauche.png", 16, 16, true, true));
        ImageView ivImageCentre = new ImageView(new Image("file:" + strImages + File.separator + "imageCentre.png", 16, 16, true, true));
        ImageView ivImageDroite = new ImageView(new Image("file:" + strImages + File.separator + "imageDroite.png", 16, 16, true, true));
        ImageView ivImageDansTexte = new ImageView(new Image("file:" + strImages + File.separator + "imageDansTexte.png", 16, 16, true, true));

        ToggleButton tgbPositionGauche = new ToggleButton("", ivImageGauche);
        ToggleButton tgbPositionCentre = new ToggleButton("", ivImageCentre);
        ToggleButton tgbPositionDroite = new ToggleButton("", ivImageDroite);
        ToggleButton tgbPositionTexte = new ToggleButton("", ivImageDansTexte);
        Tooltip tpPositionGauche = new Tooltip(rbLocalisation.getString("editeurHTML.gauche"));
        tgbPositionCentre.setTooltip(tpPositionGauche);
        Tooltip tpPositionCentre = new Tooltip(rbLocalisation.getString("editeurHTML.centre"));
        tgbPositionGauche.setTooltip(tpPositionCentre);
        Tooltip tpPositionDroite = new Tooltip(rbLocalisation.getString("editeurHTML.droite"));
        tgbPositionDroite.setTooltip(tpPositionDroite);
        Tooltip tpPositionDansTexte = new Tooltip(rbLocalisation.getString("editeurHTML.texte"));
        tgbPositionTexte.setTooltip(tpPositionDansTexte);

        tgbPositionGauche.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setToggleGroup(tgPositionImage);
        tgbPositionDroite.setToggleGroup(tgPositionImage);
        tgbPositionTexte.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setSelected(true);
        Label lblPositionImage = new Label(rbLocalisation.getString("editeurHTML.position"));
        lblPositionImage.setLayoutX(10);
        lblPositionImage.setLayoutY(70);
        tgbPositionGauche.setLayoutX(150);
        tgbPositionGauche.setLayoutY(70);
        tgbPositionCentre.setLayoutX(190);
        tgbPositionCentre.setLayoutY(70);
        tgbPositionDroite.setLayoutX(230);
        tgbPositionDroite.setLayoutY(70);
        tgbPositionTexte.setLayoutX(270);
        tgbPositionTexte.setLayoutY(70);
        Label lblLargeurImage = new Label(rbLocalisation.getString("editeurHTML.tailleImage"));
        Slider slLargeurImage = new Slider(0, 600, 300);
        lblLargeurImage.setLayoutX(10);
        lblLargeurImage.setLayoutY(110);
        slLargeurImage.setLayoutX(150);
        slLargeurImage.setLayoutY(110);
        ImageView ivImageInserer = new ImageView();
        ScrollPane spImageInserer = new ScrollPane(ivImageInserer);
        spImageInserer.setPrefSize(iLargeur - 40, iHauteur - 200);
        spImageInserer.setMinSize(iLargeur - 40, iHauteur - 200);
        spImageInserer.setMaxSize(iLargeur - 40, iHauteur - 200);
        spImageInserer.setLayoutY(140);
        spImageInserer.setLayoutX(20);
        spImageInserer.setStyle("-fx-background-color : #eee;");

        apDialog.getChildren().addAll(
                lblChoixFichierImage, tfChoixFichierImage, btnChoixFichierImage,
                lblPositionImage, tgbPositionGauche, tgbPositionCentre, tgbPositionDroite,
                tgbPositionTexte,
                lblLargeurImage, slLargeurImage,
                spImageInserer,
                btnValideImage, btnAnnuleImage);

        btnChoixFichierImage.setOnAction((evenement) -> {
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichiers image (*.jpg,*.png)", "*.jpg", "*.png");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File fileRepert = new File(strImages);
            fcRepertChoix.setInitialDirectory(fileRepert);
            File fileProjet = null;
            fileProjet = fcRepertChoix.showOpenDialog(stEditeurHTML);
            if (fileProjet != null) {
                strNomFichierImage = fileProjet.getAbsolutePath();
                tfChoixFichierImage.setText(strNomFichierImage);
                Image imgImageInserer = new Image("file:" + strNomFichierImage);
                double largeur = imgImageInserer.getWidth();
                double hauteur = imgImageInserer.getHeight();
                System.out.println(largeur + "x" + hauteur);
                slLargeurImage.setMax(largeur * 2);
                slLargeurImage.setValue(largeur);
                ivImageInserer.setImage(imgImageInserer);
                ivImageInserer.setFitWidth(largeur);
                ivImageInserer.setPreserveRatio(true);
                ivImageInserer.setSmooth(true);
            }
        });
        slLargeurImage.valueProperty().addListener((ov, ancValeur, nouvValeur) -> {
            ivImageInserer.setFitWidth((double) nouvValeur);
        });
        btnValideImage.setOnAction((evenement) -> {
            ImageEditeurHTML imageEdit = new ImageEditeurHTML();
            imageEdit.setStrImagePath(strNomFichierImage);
            String strAjouteImage
                    = "</p><p style='text-align : center;'><img width='" + (int) slLargeurImage.getValue()
                    + "' src='file:////" + strNomFichierImage
                    + "'/></p><p>";
            if (tgbPositionGauche.isSelected()) {
                strAjouteImage = "<img style='position=absolute;left: 0px;float:left;padding : 10px;padding-left : 0px;' width='" + (int) slLargeurImage.getValue()
                        + "' src='file:////" + strNomFichierImage
                        + "'/>";
            }
            if (tgbPositionDroite.isSelected()) {
                strAjouteImage = "<img width='" + (int) slLargeurImage.getValue() + "' style='position=absolute;right: 0px;float:right;padding : 10px;padding-right : 0px;'"
                        + " src='file:////" + strNomFichierImage
                        + "'/>";
            }
            if (tgbPositionTexte.isSelected()) {
                strAjouteImage = "<img width='" + (int) slLargeurImage.getValue() + "' style='padding : 10px;padding-left : 0px;'"
                        + " src='file:////" + strNomFichierImage
                        + "'/>";
            }
            System.out.println(strAjouteImage);
            engEditeurHTML.executeScript(jsCodeInsertHtml.replace("####html####", escapeJavaStyleString(strAjouteImage, true, true)));
            getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
            System.out.println(getHsHTML().getStrTexteHTML());
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
            bDejaSauve = false;
            hsHTML.getImagesEditeur()[getHsHTML().getiNombreImages()] = imageEdit;
            getHsHTML().setiNombreImages(getHsHTML().getiNombreImages() + 1);
        });
        btnAnnuleImage.setOnAction((evenement) -> {
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
        });
    }

    public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int depth) {
        if (node instanceof ImageView) {
            ImageView imageView = (ImageView) node;
            String url = imageView.getImage().impl_getUrl();
            if (url != null && imageNamePattern.matcher(url).matches()) {
                Node button = imageView.getParent().getParent();
                button.setVisible(false);
                button.setManaged(false);
            }
        }
        if (node instanceof Parent) {
            ((Parent) node).getChildrenUnmodifiable().stream().forEach((child) -> {
                hideImageNodesMatching(child, imageNamePattern, depth + 1);
            });
        }
    }

    /**
     * @return the bValide
     */
    public boolean isbValide() {

        return bValide;
    }

    /**
     * @param bValide the bValide to set
     */
    public void setbValide(boolean bValide) {
        boolean ancienneValeur = this.bValide;
        this.bValide = bValide;
        boolean nouvelleValeur = this.bValide;
        this.changeSupport.firePropertyChange("bValide", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the bAnnule
     */
    public boolean isbAnnule() {
        return bAnnule;
    }

    /**
     * @param bAnnule the bAnnule to set
     */
    public void setbAnnule(boolean bAnnule) {
        boolean ancienneValeur = this.bAnnule;
        this.bAnnule = bAnnule;
        boolean nouvelleValeur = this.bAnnule;
        this.changeSupport.firePropertyChange("bAnnule", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the hsHTML
     */
    public HotspotHTML getHsHTML() {
        return hsHTML;
    }

    /**
     * @param hsHTML the hsHTML to set
     */
    public void setHsHTML(HotspotHTML hsHTML) {
        this.hsHTML = hsHTML;
    }

}
