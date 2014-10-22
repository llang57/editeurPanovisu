/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * @return the strTexteHTML
     */
    public static String getStrTexteHTML() {
        return strTexteHTML;
    }

    /**
     * @param aStrTexteHTML the strTexteHTML to set
     */
    public static void setStrTexteHTML(String aStrTexteHTML) {
        strTexteHTML = aStrTexteHTML;
    }
    private final File fileRep = new File("");
    private final String strAppPath = fileRep.getAbsolutePath();
    private String strNomRepertHTML = strAppPath + "/pagesHTML";
    private Stage stEditeurHTML;
    private boolean bLienExterieur = true;
    private static String strTexteHTML = "";
    private int iNombreImages = 0;
    private ImageEditeurHTML[] imagesEditeur = new ImageEditeurHTML[50];
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
        bDejaSauve = true;
        int iLargeur = (int) largeur;
        int iHauteur = (int) hauteur;
        setStEditeurHTML(new Stage(StageStyle.UTILITY));
        getStEditeurHTML().initModality(Modality.APPLICATION_MODAL);
        getStEditeurHTML().setResizable(true);
        heEditeurHTML = new HTMLEditor();
        heEditeurHTML.setHtmlText(EditeurHTML.getStrTexteHTML());
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
        nodeBarreIconesSuperieure = heEditeurHTML.lookup(".bottom-toolbar");
        nodeEditeurHTML = heEditeurHTML.lookup(".web-view");
        strImages = getStrAppPath() + File.separator + "images";
        apDialog = new AnchorPane();
        apDialog.setVisible(false);
        apEditeur = new AnchorPane();
        apPrincipale = new AnchorPane(apEditeur);
        apPrincipale.setPrefSize(iLargeur, iHauteur);
        apPrincipale.getChildren().add(apDialog);
        apEditeur.setPrefSize(iLargeur, iHauteur);
        apEditeur.getChildren().add(heEditeurHTML);
        Scene scPrincipale = new Scene(apPrincipale, iLargeur, iHauteur, null);
        scPrincipale.setFill(Color.gray(0.2));
        getStEditeurHTML().setScene(scPrincipale);
        heEditeurHTML.setFocusTraversable(true);
        heEditeurHTML.requestFocus();
        heEditeurHTML.setPrefWidth(iLargeur - 30);
        heEditeurHTML.setLayoutX(10);
        heEditeurHTML.setPrefHeight(iHauteur - 130);
        heEditeurHTML.setLayoutY(70);
        heEditeurHTML.setDisable(true);
        ToggleGroup tgLien = new ToggleGroup();
        ToggleButton tgbLienExterieur = new ToggleButton("Lien externe");
        tgbLienExterieur.setLayoutX(10);
        tgbLienExterieur.setLayoutY(10);
        tgbLienExterieur.setSelected(true);
        tgbLienExterieur.setToggleGroup(tgLien);
        
        ToggleButton tgbEditeurInterne = new ToggleButton("Editeur HTML Interne");
        tgbEditeurInterne.setLayoutX(10);
        tgbEditeurInterne.setLayoutY(40);
        tgbEditeurInterne.setToggleGroup(tgLien);
        TextField tfLienExterieur = new TextField("");
        tfLienExterieur.setPromptText("http://monsite.extension");
        tfLienExterieur.setPrefWidth(200);
        tfLienExterieur.setLayoutX(150);
        tfLienExterieur.setLayoutY(3);
        Tooltip tpLenExterieur = new Tooltip("URL complète avec le préfixe http://");
        tfLienExterieur.setTooltip(tpLenExterieur);
        Label lblLargeurEditeur = new Label("largeur de l'éditeur");
        Slider slLargeurEditeur = new Slider(400, iLargeur - 30, iLargeur - 30);
        lblLargeurEditeur.setLayoutX(180);
        lblLargeurEditeur.setLayoutY(45);
        slLargeurEditeur.setLayoutX(300);
        slLargeurEditeur.setLayoutY(45);
        btnValide = new Button("valide", new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        btnAnnule = new Button("Annule", new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValide.setPrefWidth(100);
        btnValide.setLayoutX(iLargeur - 130);
        btnValide.setLayoutY(iHauteur - 50);
        btnAnnule.setPrefWidth(100);
        btnAnnule.setLayoutX(iLargeur - 250);
        btnAnnule.setLayoutY(iHauteur - 50);
        apEditeur.getChildren().addAll(
                tgbLienExterieur, tfLienExterieur,
                tgbEditeurInterne, lblLargeurEditeur, slLargeurEditeur,
                btnAnnule, btnValide
        );
        getStEditeurHTML().show();
        //hideImageNodesMatching(htmlEditor, Pattern.compile(".*(Cut|Copy|Paste).*"), 0);
        int i = 0;
        if (nodeBarreIconesSuperieure instanceof ToolBar && nodeEditeurHTML instanceof WebView) {
            tbBarreIconesSuperieure = (ToolBar) nodeBarreIconesSuperieure;
            wvEditeurHTML = (WebView) nodeEditeurHTML;
            wvEditeurHTML.setMaxHeight(iHauteur - 250);
            wvEditeurHTML.setMinHeight(iHauteur - 250);
            wvEditeurHTML.setPrefHeight(iHauteur - 250);
            engEditeurHTML = wvEditeurHTML.getEngine();
            heEditeurHTML.setOnKeyPressed((evenement) -> {
                setStrTexteHTML(heEditeurHTML.getHtmlText());
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
                        heEditeurHTML.setHtmlText(getStrTexteHTML());

                    });
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tbBarreIconesSuperieure.getItems().add(new Separator(Orientation.VERTICAL));
            ImageView ivAjouteImage = new ImageView(new Image("file:" + strImages + File.separator + "ajouteImage.png", 22, 16, true, true));
            btnAjouteImage = new Button("", ivAjouteImage);
            Tooltip tpAjouteImage = new Tooltip("Ajoute une image");
            btnAjouteImage.setTooltip(tpAjouteImage);
            tbBarreIconesSuperieure.getItems().add(btnAjouteImage);

            ImageView ivAjouteLien = new ImageView(new Image("file:" + strImages + File.separator + "lien.png", 16, 16, true, true));
            btnAjouteLien = new Button("", ivAjouteLien);
            Tooltip tpAjouteLien = new Tooltip("Ajoute un lien hypertexte");
            btnAjouteLien.setTooltip(tpAjouteLien);
            tbBarreIconesSuperieure.getItems().add(btnAjouteLien);

        }

        heEditeurHTML.requestFocus();

        getStEditeurHTML().setOnCloseRequest((event) -> {
            quitteEditeurHTML(getStEditeurHTML());
            event.consume();
        });

        tgbLienExterieur.setOnAction((evt)->{
            tgbLienExterieur.setSelected(true);
            setbLienExterieur(true);
        });
        tgbEditeurInterne.setOnAction((evt)->{
            tgbEditeurInterne.setSelected(true);
            setbLienExterieur(false);
        });

        tgLien.selectedToggleProperty()
                .addListener((ov, old_toggle, new_toggle) -> {
                    if (tgbLienExterieur.isSelected()) {
                        tfLienExterieur.setDisable(false);
                        heEditeurHTML.setDisable(true);
                        setbLienExterieur(true);
                    } else {
                        tfLienExterieur.setDisable(true);
                        heEditeurHTML.setDisable(false);
                        setbLienExterieur(false);
                    }
                }
                );

        btnAnnule.setOnAction((evenement) -> {
                    quitteEditeurHTML(getStEditeurHTML());
                }
        );
        btnValide.setOnAction((evenement) -> {
            OutputStreamWriter oswFichierHTML = null;
            try {
                String strPageHTML = getStrNomRepertHTML();
                String strPageHTMLImages = strPageHTML + File.separator + "/images";
                File filePageHTML = new File(strPageHTML);
                File filePageHTMLImages = new File(strPageHTMLImages);
                if (!filePageHTML.exists()) {
                    filePageHTML.mkdirs();
                }
                if (!filePageHTMLImages.exists()) {
                    filePageHTMLImages.mkdirs();
                }
                String strContenuHTML = heEditeurHTML.getHtmlText();
                System.out.println(strContenuHTML);
                System.out.println(" nombre Images : " + getiNombreImages());
                File fileIndexHTML = new File(strPageHTML + File.separator + "index.html");
                for (int j = 0; j < getiNombreImages(); j++) {
                    ImageEditeurHTML img1 = getImagesEditeur()[j];
                    strContenuHTML = strContenuHTML.replace("file:////" + img1.getStrImagePath(), "images/" + img1.getStrImage());
                    try {
                        copieFichierRepertoire(img1.getStrImagePath(), strPageHTMLImages);
                    } catch (IOException ex) {
                        Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                strContenuHTML = strContenuHTML.replace(">", ">\n");
                System.out.println(strContenuHTML);
                fileIndexHTML.setWritable(true);
                oswFichierHTML = new OutputStreamWriter(new FileOutputStream(fileIndexHTML), "UTF-8");
                try (BufferedWriter bwFichierHTML = new BufferedWriter(oswFichierHTML)) {
                    bwFichierHTML.write(strContenuHTML);
                    getStEditeurHTML().hide();
                } catch (IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException | FileNotFoundException ex) {
                Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    oswFichierHTML.close();
                } catch (IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
                    heEditeurHTML.setLayoutX((getStEditeurHTML().getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                });

        getStEditeurHTML().widthProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    System.out.println(nouvValeur);
                    slLargeurEditeur.setMax((double) nouvValeur - 35.d);
                    if (slLargeurEditeur.getValue() > (double) nouvValeur - 35.d) {
                        slLargeurEditeur.setValue((double) nouvValeur - 35.d);
                        heEditeurHTML.setPrefWidth((double) nouvValeur - 35.d);
                        heEditeurHTML.setMaxWidth((double) nouvValeur - 35.d);
                        heEditeurHTML.setMinWidth((double) nouvValeur - 35.d);
                    }
                    heEditeurHTML.setLayoutX((getStEditeurHTML().getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                    apEditeur.setPrefWidth((double) nouvValeur);
                    apEditeur.setMinWidth((double) nouvValeur);
                    apEditeur.setMaxWidth((double) nouvValeur);
                    apPrincipale.setPrefWidth((double) nouvValeur);
                    apPrincipale.setMinWidth((double) nouvValeur);
                    apPrincipale.setMaxWidth((double) nouvValeur);

                    btnValide.setLayoutX((double) nouvValeur - 135);
                    btnAnnule.setLayoutX((double) nouvValeur - 255);
                    apDialog.setLayoutX((apPrincipale.getPrefWidth() - apDialog.getPrefWidth()) / 2);

                }
                );

        getStEditeurHTML().heightProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    System.out.println(nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apEditeur.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    apPrincipale.setPrefHeight((double) nouvValeur);
                    heEditeurHTML.setPrefHeight((double) nouvValeur - 155.d);
                    heEditeurHTML.setMaxHeight((double) nouvValeur - 155.d);
                    heEditeurHTML.setMinHeight((double) nouvValeur - 155.d);
                    btnValide.setLayoutY((double) nouvValeur - 75);
                    btnAnnule.setLayoutY((double) nouvValeur - 75);
                    apDialog.setLayoutY((apPrincipale.getPrefHeight() - apDialog.getPrefHeight()) / 2);
                    wvEditeurHTML.setMaxHeight((double) nouvValeur - 250);
                    wvEditeurHTML.setMinHeight((double) nouvValeur - 250);
                    wvEditeurHTML.setPrefHeight((double) nouvValeur - 250);
                });

    }

    private void quitteEditeurHTML(Stage stFenetre) {
        System.out.println(getStEditeurHTML());
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
        Label lblBarrePersonnalisee = new Label("Ajouter un lien");
        lblBarrePersonnalisee.setMinWidth(iLargeur - 10);
        lblBarrePersonnalisee.setAlignment(Pos.CENTER);
        lblBarrePersonnalisee.setStyle("-fx-background-color : #777;");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setLayoutX(5);
        lblBarrePersonnalisee.setLayoutY(10);
        lblBarrePersonnalisee.setFont(Font.font(14));
        apDialog.getChildren().add(lblBarrePersonnalisee);
        Button btnValideLien = new Button("Ok", new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleLien = new Button("Annuler", new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideLien.setPrefWidth(100);
        btnValideLien.setLayoutX(iLargeur - 130);
        btnValideLien.setLayoutY(iHauteur - 50);
        btnAnnuleLien.setPrefWidth(100);
        btnAnnuleLien.setLayoutX(iLargeur - 250);
        btnAnnuleLien.setLayoutY(iHauteur - 50);
        apDialog.getChildren().addAll(btnValideLien, btnAnnuleLien);
        Label lblTexteLien = new Label("texte du lien");
        lblTexteLien.setLayoutX(10);
        lblTexteLien.setLayoutY(40);
        Label lblUrlLien = new Label("URL");
        lblUrlLien.setLayoutX(10);
        lblUrlLien.setLayoutY(70);
        TextField tfTexteLien = new TextField(strSelection);
        tfTexteLien.setPrefWidth(200);
        tfTexteLien.setLayoutX(150);
        tfTexteLien.setLayoutY(40);
        TextField tfURLLien = new TextField("");
        tfURLLien.setPromptText("http://monsite.extension");

        tfURLLien.setPrefWidth(200);
        tfURLLien.setLayoutX(150);
        tfURLLien.setLayoutY(70);
        Label lblCibleLien = new Label("cible du lien");
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
            setStrTexteHTML(heEditeurHTML.getHtmlText());
            setStrTexteHTML(getStrTexteHTML().replace("####html####" + strSelection, strAjouteLien).replace(strSelection + "####html####", strAjouteLien));
            heEditeurHTML.setHtmlText(getStrTexteHTML());
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
        Button btnValideImage = new Button("Ok", new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleImage = new Button("Annuler", new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideImage.setPrefWidth(100);
        btnValideImage.setLayoutX(iLargeur - 130);
        btnValideImage.setLayoutY(iHauteur - 50);
        btnAnnuleImage.setPrefWidth(100);
        btnAnnuleImage.setLayoutX(iLargeur - 250);
        btnAnnuleImage.setLayoutY(iHauteur - 50);
        Label lblChoixFichierImage = new Label("Choix de l'image");
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
        Tooltip tpPositionGauche = new Tooltip("à gauche");
        tgbPositionCentre.setTooltip(tpPositionGauche);
        Tooltip tpPositionCentre = new Tooltip("Centrée");
        tgbPositionGauche.setTooltip(tpPositionCentre);
        Tooltip tpPositionDroite = new Tooltip("à droite");
        tgbPositionDroite.setTooltip(tpPositionDroite);
        Tooltip tpPositionDansTexte = new Tooltip("Dans le texte");
        tgbPositionTexte.setTooltip(tpPositionDansTexte);

        tgbPositionGauche.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setToggleGroup(tgPositionImage);
        tgbPositionDroite.setToggleGroup(tgPositionImage);
        tgbPositionTexte.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setSelected(true);
        Label lblPositionImage = new Label("Position :");
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
        Label lblLargeurImage = new Label("Taille de l'image");
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
            fileProjet = fcRepertChoix.showOpenDialog(getStEditeurHTML());
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
            String strNomFich = strNomFichierImage.substring(strNomFichierImage.lastIndexOf(File.separator) + 1, strNomFichierImage.length());
            imageEdit.setStrImage(strNomFich);
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
            setStrTexteHTML(heEditeurHTML.getHtmlText());
            System.out.println(getStrTexteHTML());
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
            bDejaSauve = false;
            getImagesEditeur()[getiNombreImages()] = imageEdit;
            setiNombreImages(getiNombreImages() + 1);
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
     * @return the fileRep
     */
    public File getFileRep() {
        return fileRep;
    }

    /**
     * @return the strAppPath
     */
    public String getStrAppPath() {
        return strAppPath;
    }

    /**
     * @return the strNomRepertHTML
     */
    public String getStrNomRepertHTML() {
        return strNomRepertHTML;
    }

    /**
     * @param strNomRepertHTML the strNomRepertHTML to set
     */
    public void setStrNomRepertHTML(String strNomRepertHTML) {
        this.strNomRepertHTML = strNomRepertHTML;
    }

    /**
     * @return the stEditeurHTML
     */
    public Stage getStEditeurHTML() {
        return stEditeurHTML;
    }

    /**
     * @param stEditeurHTML the stEditeurHTML to set
     */
    public void setStEditeurHTML(Stage stEditeurHTML) {
        this.stEditeurHTML = stEditeurHTML;
    }

    /**
     * @return the bLienExterieur
     */
    public boolean isbLienExterieur() {
        return bLienExterieur;
    }

    /**
     * @param bLienExterieur the bLienExterieur to set
     */
    public void setbLienExterieur(boolean bLienExterieur) {
        this.bLienExterieur = bLienExterieur;
    }

    /**
     * @return the iNombreImages
     */
    public int getiNombreImages() {
        return iNombreImages;
    }

    /**
     * @param iNombreImages the iNombreImages to set
     */
    public void setiNombreImages(int iNombreImages) {
        this.iNombreImages = iNombreImages;
    }

    /**
     * @return the imagesEditeur
     */
    public ImageEditeurHTML[] getImagesEditeur() {
        return imagesEditeur;
    }

    /**
     * @param imagesEditeur the imagesEditeur to set
     */
    public void setImagesEditeur(ImageEditeurHTML[] imagesEditeur) {
        this.imagesEditeur = imagesEditeur;
    }
}
