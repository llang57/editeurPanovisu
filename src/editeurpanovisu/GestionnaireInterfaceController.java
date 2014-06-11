/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.repertAppli;
import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jfxtras.labs.scene.control.BigDecimalField;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    private static final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
    /**
     *
     */
    public static final String styleDefaut = "retinavert";
    /**
     *
     */
    public static final String styleHotSpotDefaut = "hotspotvert.png";
    /**
     *
     */
    public static String positionBarre = "bottom:center";
    /**
     *
     */
    public static String styleHotSpots = styleHotSpotDefaut;

    /**
     *
     */
    public static String styleBarre = styleDefaut;

    /**
     *
     */
    public static double dXBarre = 0;

    /**
     *
     */
    public static double dYBarre = 10;

    /**
     *
     */
    public static double tailleBarre = 30;

    /**
     *
     */
    public static String toggleBarreVisibilite = "oui";

    /**
     *
     */
    public static String toggleBarreDeplacements = "oui";

    /**
     *
     */
    public static String toggleBarreZoom = "oui";

    /**
     *
     */
    public static String toggleBarreOutils = "oui";

    /**
     *
     */
    public static String toggleBoutonInfo = "oui";

    /**
     *
     */
    public static String toggleBoutonAide = "oui";

    /**
     *
     */
    public static String toggleBoutonRotation = "oui";

    /**
     *
     */
    public static String toggleBoutonFS = "oui";

    /**
     *
     */
    public static String toggleBoutonSouris = "oui";

    /**
     *
     */
    public static String titrePoliceNom = "Verdana";
    public static String titrePoliceStyle = "Regular";
    public static String titrePoliceTaille = "12.0";
    public static String couleurTitre = "#ffffff";
    public static String couleurFondTitre = "#000000";
    public static Double titreOpacite = 0.8;
    public static Double titreTaille = 50.0;

    public Pane tabInterface;
    private static HBox HBInterface;
    private static AnchorPane APVisualisation;
    private static VBox VBOutils;
    private static RadioButton RBClair;
    private static RadioButton RBSombre;
    private static ImageView IMVisualisation;
    final ToggleGroup grpImage = new ToggleGroup();
    final ToggleGroup grpPostBarre = new ToggleGroup();
    private static Image imageClaire;
    private static Image imageSombre;
    private static HBox HBbarreBoutons;
    private static HBox HBOutils;
    private static Label txtTitre;
    private static ImageView IVInfo;
    private static Image ImgInfo;
    private static ImageView IVAide;
    private static Image ImgAide;
    private static ImageView IVAutoRotation;
    private static Image ImgAutoRotation;
    private static ImageView IVModeSouris;
    private static Image ImgModeSouris;
    private static ImageView IVPleinEcran;
    private static Image ImgPleinEcran;
    private static HBox HBZoom;
    private static ImageView IVZoomPlus;
    private static Image ImgZoomPlus;
    private static ImageView IVZoomMoins;
    private static Image ImgZoomMoins;
    private static HBox HBDeplacements;
    private static ImageView IVHaut;
    private static Image ImgHaut;
    private static ImageView IVBas;
    private static Image ImgBas;
    private static ImageView IVGauche;
    private static Image ImgGauche;
    private static ImageView IVDroite;
    private static Image ImgDroite;
    private static ImageView IVHotSpot;
    private static Image ImgHotSpot;
    private static ComboBox CBlisteStyle;

    private static String repertBoutonsPrincipal;
    private static String repertHotSpots;
    private static RadioButton RBTopLeft;
    private static RadioButton RBTopCenter;
    private static RadioButton RBTopRight;
    private static RadioButton RBMiddleLeft;
    private static RadioButton RBMiddleCenter;
    private static RadioButton RBMiddleRight;
    private static RadioButton RBBottomLeft;
    private static RadioButton RBBottomCenter;
    private static RadioButton RBBottomRight;
    private static CheckBox CBVisible;
    private static CheckBox CBDeplacements;
    private static CheckBox CBZoom;
    private static CheckBox CBOutils;
    private static CheckBox CBFS;
    private static CheckBox CBSouris;
    private static CheckBox CBRotation;
    private static BigDecimalField dXSpinner;
    private static BigDecimalField dYSpinner;

    /**
     *
     * @param position
     * @param dX
     * @param dY
     * @param taille
     * @param styleBoutons
     * @param styleHS
     */
    public void afficheBouton(String position, double dX, double dY, double taille, String styleBoutons, String styleHS) {
        String repertBoutons = "file:" + repertBoutonsPrincipal + File.separator + styleBoutons;
        HBbarreBoutons = new HBox();
        HBbarreBoutons.setId("barreBoutons");
        HBbarreBoutons.setVisible(toggleBarreVisibilite.equals("oui"));
        ImgHotSpot = new Image("file:" + repertHotSpots + File.separator + styleHS);
        IVHotSpot = new ImageView(ImgHotSpot);
        IVHotSpot.setFitWidth(30);
        IVHotSpot.setPreserveRatio(true);
        IVHotSpot.setLayoutX(510);
        IVHotSpot.setLayoutY(310);
        int nombreBoutons = 11;
        if (toggleBarreDeplacements.equals("non")) {
            nombreBoutons -= 4;
        }
        if (toggleBarreZoom.equals("non")) {
            nombreBoutons -= 2;
        }
        if (toggleBarreOutils.equals("non")) {
            nombreBoutons -= 5;
        } else {
            if (toggleBoutonFS.equals("non")) {
                nombreBoutons -= 1;
            }
            if (toggleBoutonRotation.equals("non")) {
                nombreBoutons -= 1;
            }
            if (toggleBoutonSouris.equals("non")) {
                nombreBoutons -= 1;
            }
        }
        double tailleBarre = (double) nombreBoutons * (double) taille;
        HBbarreBoutons.setPrefWidth(tailleBarre);
        HBbarreBoutons.setPrefHeight((double) taille);
        HBbarreBoutons.setMinWidth(tailleBarre);
        HBbarreBoutons.setMinHeight((double) taille);
        HBbarreBoutons.setMaxWidth(tailleBarre);
        HBbarreBoutons.setMaxHeight((double) taille);
        HBDeplacements = new HBox();
        HBZoom = new HBox();
        HBOutils = new HBox();

        HBbarreBoutons.getChildren().addAll(HBDeplacements, HBZoom, HBOutils);
        if (toggleBarreDeplacements.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBDeplacements);
        }
        if (toggleBarreZoom.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBZoom);
        }
        if (toggleBarreOutils.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBOutils);
        }
        APVisualisation.getChildren().addAll(HBbarreBoutons, IVHotSpot);
        ImgHaut = new Image(repertBoutons + File.separator + "haut.png");
        ImgBas = new Image(repertBoutons + File.separator + "bas.png");
        ImgGauche = new Image(repertBoutons + File.separator + "gauche.png");
        ImgDroite = new Image(repertBoutons + File.separator + "droite.png");
        ImgZoomPlus = new Image(repertBoutons + File.separator + "zoomin.png");
        ImgZoomMoins = new Image(repertBoutons + File.separator + "zoomout.png");
        ImgInfo = new Image(repertBoutons + File.separator + "info.png");
        ImgAide = new Image(repertBoutons + File.separator + "aide.png");
        ImgModeSouris = new Image(repertBoutons + File.separator + "souris.png");
        ImgPleinEcran = new Image(repertBoutons + File.separator + "fs.png");
        ImgAutoRotation = new Image(repertBoutons + File.separator + "rotation.png");
        IVHaut = new ImageView(ImgHaut);
        IVHaut.setFitWidth(taille);
        IVHaut.setFitHeight(taille);
        IVBas = new ImageView(ImgBas);
        IVBas.setFitWidth(taille);
        IVBas.setFitHeight(taille);
        IVGauche = new ImageView(ImgGauche);
        IVGauche.setFitWidth(taille);
        IVGauche.setFitHeight(taille);
        IVDroite = new ImageView(ImgDroite);
        IVDroite.setFitWidth(taille);
        IVDroite.setFitHeight(taille);
        IVZoomPlus = new ImageView(ImgZoomPlus);
        IVZoomPlus.setFitWidth(taille);
        IVZoomPlus.setFitHeight(taille);
        IVZoomMoins = new ImageView(ImgZoomMoins);
        IVZoomMoins.setFitWidth(taille);
        IVZoomMoins.setFitHeight(taille);
        IVInfo = new ImageView(ImgInfo);
        IVInfo.setFitWidth(taille);
        IVInfo.setFitHeight(taille);
        IVPleinEcran = new ImageView(ImgPleinEcran);
        IVPleinEcran.setFitWidth(taille);
        IVPleinEcran.setFitHeight(taille);
        IVModeSouris = new ImageView(ImgModeSouris);
        IVModeSouris.setFitWidth(taille);
        IVModeSouris.setFitHeight(taille);
        IVAide = new ImageView(ImgAide);
        IVAide.setFitWidth(taille);
        IVAide.setFitHeight(taille);
        IVAutoRotation = new ImageView(ImgAutoRotation);
        IVAutoRotation.setFitWidth(taille);
        IVAutoRotation.setFitHeight(taille);
        String positVert = position.split(":")[0];
        String positHor = position.split(":")[1];
        HBDeplacements.getChildren().addAll(IVGauche, IVHaut, IVBas, IVDroite);
        HBZoom.getChildren().addAll(IVZoomPlus, IVZoomMoins);
        HBOutils.getChildren().addAll(IVPleinEcran, IVModeSouris, IVAutoRotation, IVInfo, IVAide);
        if (toggleBoutonFS.equals("non")) {
            HBOutils.getChildren().remove(IVPleinEcran);
        }
        if (toggleBoutonSouris.equals("non")) {
            HBOutils.getChildren().remove(IVModeSouris);
        }
        if (toggleBoutonRotation.equals("non")) {
            HBOutils.getChildren().remove(IVAutoRotation);
        }

        double LX = 0;
        double LY = 0;
        switch (positVert) {
            case "top":
                LY = IMVisualisation.getLayoutY() + dY;
                break;
            case "bottom":
                LY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight() - dY;
                break;
            case "middle":
                LY = IMVisualisation.getLayoutY() + (IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight()) / 2.d - dY;
                break;
        }

        switch (positHor) {
            case "right":
                LX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth() - dX;
                break;
            case "left":
                LX = IMVisualisation.getLayoutX() + dX;
                break;
            case "center":
                LX = IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth()) / 2 + dX;
                break;
        }
        HBbarreBoutons.setLayoutX(LX);
        HBbarreBoutons.setLayoutY(LY);
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> listerStyle(String repertoire) {
        ArrayList<String> liste = new ArrayList<String>();
        File[] Repertoires = new File(repertoire).listFiles();
        for (File repert : Repertoires) {
            if (repert.isDirectory()) {
                String nomRepert = repert.getName();
                if (!nomRepert.equals("hotspots")) {
                    liste.add(nomRepert);
                }
            }
        }
        return liste;
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> listerHotSpots(String repertoire) {
        ArrayList<String> liste = new ArrayList<String>();
        FilenameFilter FNFPng = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        File[] Repertoires = new File(repertoire).listFiles(IMAGE_FILTER);
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomRepert = repert.getName();
                liste.add(nomRepert);
            }
        }
        return liste;
    }

    /**
     *
     * @return
     */
    public String getTemplate() {
        String contenuFichier = "";
        contenuFichier
                += "styleBarre=" + styleBarre + "\n"
                + "styleHotspots=" + styleHotSpots + "\n"
                + "position=" + positionBarre + "\n"
                + "dX=" + dXBarre + "\n"
                + "dY=" + dYBarre + "\n"
                + "visible=" + toggleBarreVisibilite + "\n"
                + "deplacement=" + toggleBarreDeplacements + "\n"
                + "zoom=" + toggleBarreZoom + "\n"
                + "outils=" + toggleBarreOutils + "\n"
                + "rotation=" + toggleBoutonRotation + "\n"
                + "FS=" + toggleBoutonFS + "\n"
                + "souris=" + toggleBoutonSouris + "\n";
        return contenuFichier;
    }

    /**
     *
     * @param templ
     */
    public void setTemplate(List<String> templ) {
        for (String chaine : templ) {
            String variable = chaine.split("=")[0];
            String valeur = chaine.split("=")[1];
            switch (variable) {
                case "styleBarre":
                    styleBarre = valeur;
                    CBlisteStyle.setValue(valeur);
                    break;
                case "styleHotspots":
                    styleHotSpots = valeur;
                    break;
                case "position":
                    positionBarre = valeur;
                    switch (valeur) {
                        case "top:left":
                            RBTopLeft.setSelected(true);
                            break;
                        case "top:center":
                            RBTopCenter.setSelected(true);
                            break;
                        case "top:right":
                            RBTopRight.setSelected(true);
                            break;
                        case "middle:left":
                            RBMiddleLeft.setSelected(true);
                            break;
                        case "middle:center":
                            RBMiddleCenter.setSelected(true);
                            break;
                        case "middle:right":
                            RBMiddleRight.setSelected(true);
                            break;
                        case "bottom:left":
                            RBBottomLeft.setSelected(true);
                            break;
                        case "bottom:center":
                            RBBottomCenter.setSelected(true);
                            break;
                        case "bottom:right":
                            RBBottomRight.setSelected(true);
                            break;
                    }

                    break;
                case "dX":
                    dXBarre = Double.parseDouble(valeur);
                    dXSpinner.setNumber(new BigDecimal(dXBarre));
                    break;
                case "dY":
                    dYBarre = Double.parseDouble(valeur);
                    dYSpinner.setNumber(new BigDecimal(dYBarre));
                    break;
                case "visible":
                    toggleBarreVisibilite = valeur;
                    if (valeur.equals("oui")) {
                        CBVisible.setSelected(true);
                    } else {
                        CBVisible.setSelected(false);
                    }
                    break;
                case "deplacement":
                    toggleBarreDeplacements = valeur;
                    if (valeur.equals("oui")) {
                        CBDeplacements.setSelected(true);
                    } else {
                        CBDeplacements.setSelected(false);
                    }
                    break;
                case "zoom":
                    toggleBarreZoom = valeur;
                    if (valeur.equals("oui")) {
                        CBZoom.setSelected(true);
                    } else {
                        CBZoom.setSelected(false);
                    }
                    break;
                case "outils":
                    toggleBarreOutils = valeur;
                    if (valeur.equals("oui")) {
                        CBOutils.setSelected(true);
                    } else {
                        CBOutils.setSelected(false);
                    }
                    break;
                case "rotation":
                    toggleBoutonRotation = valeur;
                    if (valeur.equals("oui")) {
                        CBRotation.setSelected(true);
                    } else {
                        CBRotation.setSelected(false);
                    }
                    break;
                case "FS":
                    toggleBoutonFS = valeur;
                    if (valeur.equals("oui")) {
                        CBFS.setSelected(true);
                    } else {
                        CBFS.setSelected(false);
                    }
                    break;
                case "souris":
                    toggleBoutonSouris = valeur;
                    if (valeur.equals("oui")) {
                        CBSouris.setSelected(true);
                    } else {
                        CBSouris.setSelected(false);
                    }
                    break;

            }
        }
        APVisualisation.getChildren().remove(HBbarreBoutons);
        APVisualisation.getChildren().remove(IVHotSpot);
        afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);

    }

    /**
     *
     * @param width
     * @param height
     */
    public void creeInterface(int width, int height) {
        repertBoutonsPrincipal = repertAppli + File.separator + "panovisu/images";
        repertHotSpots = repertAppli + File.separator + "panovisu/images/hotspots";
        ArrayList<String> listeStyles = listerStyle(repertBoutonsPrincipal);
        ArrayList<String> listeHotSpots = listerHotSpots(repertHotSpots);
        int nombreHotSpots = listeHotSpots.size();
        ImageView[] IVHotspots = new ImageView[nombreHotSpots];
        imageClaire = new Image("file:" + repertAppli + File.separator + "images/claire.jpg");
        imageSombre = new Image("file:" + repertAppli + File.separator + "images/sombre.jpg");
        txtTitre = new Label("Titre du panoramique");
        Font fonte = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
        //System.out.println("'" + titrePoliceNom + "'" + fonte.getName() + "  " + fonte.getFamily() + "  " + fonte.getSize());
        txtTitre.setFont(fonte);

        tabInterface = new Pane();
        HBInterface = new HBox();
        HBInterface.setPrefWidth(width);
        HBInterface.setPrefHeight(height);
        tabInterface.getChildren().add(HBInterface);
        APVisualisation = new AnchorPane();
        APVisualisation.setPrefWidth(width * 0.8);
        APVisualisation.setPrefHeight(height);
        VBOutils = new VBox();
        VBOutils.setPrefWidth(width * 0.2);
        VBOutils.setPrefHeight(height);
        VBOutils.setStyle("-fx-background-color : #ccc;");
        HBInterface.getChildren().addAll(APVisualisation, VBOutils);
        IMVisualisation = new ImageView(imageClaire);
        IMVisualisation.setFitWidth(1200);
        IMVisualisation.setFitHeight(800);
        IMVisualisation.setSmooth(true);
        double LX = (width * 0.8 - IMVisualisation.getFitWidth()) / 2;
        IMVisualisation.setLayoutX(LX);
        IMVisualisation.setLayoutY(20);
        txtTitre.setMinSize(500, 30);
        txtTitre.setPadding(new Insets(5));
        txtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        txtTitre.setAlignment(Pos.CENTER);
        txtTitre.setOpacity(titreOpacite);
        txtTitre.setTextFill(Color.WHITE);
        txtTitre.setLayoutY(20);
        txtTitre.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
        RBClair = new RadioButton("Image claire");
        RBSombre = new RadioButton("Image Sombre");
        RBClair.setToggleGroup(grpImage);
        RBSombre.setToggleGroup(grpImage);
        APVisualisation.getChildren().addAll(RBClair, RBSombre);
        RBClair.setLayoutX(LX + 40);
        RBClair.setLayoutY(830);
        RBClair.setSelected(true);
        RBSombre.setLayoutX(LX + 180);
        RBSombre.setLayoutY(830);
        RBClair.setUserData("claire");
        RBSombre.setUserData("sombre");
        APVisualisation.getChildren().add(IMVisualisation);
        APVisualisation.getChildren().add(txtTitre);
        afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);

        CBlisteStyle = new ComboBox();
        listeStyles.stream().forEach((nomStyle) -> {
            CBlisteStyle.getItems().add(nomStyle);
        });
        CBlisteStyle.setLayoutX(150);
        CBlisteStyle.setLayoutY(90);

        RBTopLeft = new RadioButton();
        RBTopCenter = new RadioButton();
        RBTopRight = new RadioButton();
        RBMiddleLeft = new RadioButton();
        RBMiddleCenter = new RadioButton();
        RBMiddleRight = new RadioButton();
        RBBottomLeft = new RadioButton();
        RBBottomCenter = new RadioButton();
        RBBottomRight = new RadioButton();

        RBTopLeft.setUserData("top:left");
        RBTopCenter.setUserData("top:center");
        RBTopRight.setUserData("top:right");
        RBMiddleLeft.setUserData("middle:left");
        RBMiddleCenter.setUserData("middle:center");
        RBMiddleRight.setUserData("middle:right");
        RBBottomLeft.setUserData("bottom:left");
        RBBottomCenter.setUserData("bottom:center");
        RBBottomRight.setUserData("bottom:right");

        RBTopLeft.setToggleGroup(grpPostBarre);
        RBTopCenter.setToggleGroup(grpPostBarre);
        RBTopRight.setToggleGroup(grpPostBarre);
        RBMiddleLeft.setToggleGroup(grpPostBarre);
        RBMiddleCenter.setToggleGroup(grpPostBarre);
        RBMiddleRight.setToggleGroup(grpPostBarre);
        RBBottomLeft.setToggleGroup(grpPostBarre);
        RBBottomCenter.setToggleGroup(grpPostBarre);
        RBBottomRight.setToggleGroup(grpPostBarre);

        int posX = 250;
        int posY = 130;

        RBTopLeft.setLayoutX(posX);
        RBTopCenter.setLayoutX(posX + 20);
        RBTopRight.setLayoutX(posX + 40);
        RBTopLeft.setLayoutY(posY);
        RBTopCenter.setLayoutY(posY);
        RBTopRight.setLayoutY(posY);

        RBMiddleLeft.setLayoutX(posX);
        RBMiddleCenter.setLayoutX(posX + 20);
        RBMiddleRight.setLayoutX(posX + 40);
        RBMiddleLeft.setLayoutY(posY + 20);
        RBMiddleCenter.setLayoutY(posY + 20);
        RBMiddleRight.setLayoutY(posY + 20);

        RBBottomLeft.setLayoutX(posX);
        RBBottomCenter.setLayoutX(posX + 20);
        RBBottomRight.setLayoutX(posX + 40);
        RBBottomLeft.setLayoutY(posY + 40);
        RBBottomCenter.setLayoutY(posY + 40);
        RBBottomRight.setLayoutY(posY + 40);
        RBBottomCenter.setSelected(true);
        Label lblStyle = new Label(rb.getString("interface.style"));
        lblStyle.setLayoutX(30);
        lblStyle.setLayoutY(70);
        Label lblPosit = new Label(rb.getString("interface.position"));
        lblPosit.setLayoutX(30);
        lblPosit.setLayoutY(130);

        dXSpinner = new BigDecimalField(new BigDecimal(dXBarre));
        dXSpinner.setLayoutX(50);
        dXSpinner.setLayoutY(210);
        dXSpinner.setMaxValue(new BigDecimal(2000));
        dXSpinner.setMinValue(new BigDecimal(-2000));
        dXSpinner.setMaxWidth(100);
        dYSpinner = new BigDecimalField(new BigDecimal(dYBarre));
        dYSpinner.setLayoutX(250);
        dYSpinner.setLayoutY(210);
        dYSpinner.setMaxValue(new BigDecimal(2000));
        dYSpinner.setMinValue(new BigDecimal(-2000));
        dYSpinner.setMaxWidth(100);
        CBVisible = new CheckBox(rb.getString("interface.barreVisible"));
        CBVisible.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreVisibilite = "oui";
            } else {
                toggleBarreVisibilite = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });

        CBDeplacements = new CheckBox(rb.getString("interface.deplacementsVisible"));
        CBDeplacements.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreDeplacements = "oui";
            } else {
                toggleBarreDeplacements = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBZoom = new CheckBox(rb.getString("interface.zoomVisible"));
        CBZoom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreZoom = "oui";
            } else {
                toggleBarreZoom = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBOutils = new CheckBox(rb.getString("interface.outilsVisible"));
        CBOutils.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreOutils = "oui";
            } else {
                toggleBarreOutils = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBSouris = new CheckBox(rb.getString("interface.outilsSouris"));
        CBSouris.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonSouris = "oui";
            } else {
                toggleBoutonSouris = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBRotation = new CheckBox(rb.getString("interface.outilsRotation"));
        CBRotation.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonRotation = "oui";
            } else {
                toggleBoutonRotation = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBFS = new CheckBox(rb.getString("interface.outilsFS"));
        CBFS.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonFS = "oui";
            } else {
                toggleBoutonFS = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });

        CBVisible.setLayoutX(30);
        CBVisible.setLayoutY(40);
        Label lblVisibilite = new Label(rb.getString("interface.visibilite"));
        lblVisibilite.setLayoutX(30);
        lblVisibilite.setLayoutY(260);
        CBVisible.setSelected(true);
        CBDeplacements.setLayoutX(100);
        CBDeplacements.setLayoutY(280);
        CBDeplacements.setSelected(true);
        CBZoom.setLayoutX(100);
        CBZoom.setLayoutY(300);
        CBZoom.setSelected(true);
        CBOutils.setLayoutX(100);
        CBOutils.setLayoutY(320);
        CBOutils.setSelected(true);
        CBFS.setLayoutX(150);
        CBFS.setLayoutY(340);
        CBFS.setSelected(true);
        CBRotation.setLayoutX(150);
        CBRotation.setLayoutY(360);
        CBRotation.setSelected(true);
        CBSouris.setLayoutX(150);
        CBSouris.setLayoutY(380);
        CBSouris.setSelected(true);
        AnchorPane APHotSpots = new AnchorPane();
        double tailleHS = 35.d * (int) (nombreHotSpots / 8 + 1);
        APHotSpots.setPrefHeight(tailleHS);
        APHotSpots.setLayoutX(50);
        APHotSpots.setLayoutY(40);
        APHotSpots.setStyle("-fx-background-color : #fff");
        APHotSpots.setPadding(new Insets(5));
        int i = 0;
        double xHS;
        double yHS;
        for (String nomImage : listeHotSpots) {
            IVHotspots[i] = new ImageView(new Image("file:" + repertHotSpots + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 8;
            int row = i / 8;
            xHS = col * 40 + 5;
            yHS = row * 35 + 5;
            IVHotspots[i].setLayoutX(xHS);
            IVHotspots[i].setLayoutY(yHS);
            IVHotspots[i].setStyle("-fx-background-color : #ccc");
            IVHotspots[i].setOnMouseClicked((MouseEvent me) -> {
                APVisualisation.getChildren().remove(HBbarreBoutons);
                APVisualisation.getChildren().remove(IVHotSpot);
                styleHotSpots = nomImage;
                afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);

            });
            APHotSpots.getChildren().add(IVHotspots[i]);
            i++;

        }
        AnchorPane APBB = new AnchorPane();
        AnchorPane APHS = new AnchorPane();
        AnchorPane APTIT = new AnchorPane();
        AnchorPane APTitre = new AnchorPane();
        APTitre.setLayoutY(40);
        APTitre.setPrefHeight(170);
        Label lblPanelTitre = new Label(rb.getString("interface.barreBoutons"));
        lblPanelTitre.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelTitre.setStyle("-fx-background-color : #444");
        lblPanelTitre.setTextFill(Color.WHITE);
        lblPanelTitre.setPadding(new Insets(5));
        lblPanelTitre.setLayoutX(30);
        lblPanelTitre.setLayoutY(10);
        ImageView IVBtnPlusTitre = new ImageView(new Image("file:" + "images/moins.png", 20, 20, true, true));
        IVBtnPlusTitre.setLayoutX(VBOutils.getPrefWidth());
        IVBtnPlusTitre.setLayoutY(13);
        Button choixPoliceTitre = new Button("  ...  ");
        choixPoliceTitre.setLayoutX(250);
        choixPoliceTitre.setLayoutY(12);
        Label lblChoixPoliceTitre = new Label(rb.getString("interface.choixPolice"));
        lblChoixPoliceTitre.setLayoutX(30);
        lblChoixPoliceTitre.setLayoutY(15);
        Label lblChoixCouleurTitre = new Label(rb.getString("interface.choixCouleur"));
        lblChoixCouleurTitre.setLayoutX(30);
        lblChoixCouleurTitre.setLayoutY(45);
        ColorPicker CPCouleurTitre = new ColorPicker(Color.valueOf(couleurTitre));
        CPCouleurTitre.setLayoutX(250);
        CPCouleurTitre.setLayoutY(43);
        Label lblChoixCouleurFondTitre = new Label(rb.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondTitre.setLayoutX(30);
        lblChoixCouleurFondTitre.setLayoutY(75);
        ColorPicker CPCouleurFondTitre = new ColorPicker(Color.valueOf(couleurFondTitre));
        CPCouleurFondTitre.setLayoutX(250);
        CPCouleurFondTitre.setLayoutY(73);
        CPCouleurTitre.setOnAction((ActionEvent e) -> {
            String coul=CPCouleurTitre.getValue().toString().substring(2,8);
            couleurTitre="#"+coul;
            txtTitre.setTextFill(Color.valueOf(couleurTitre));
        });
        CPCouleurFondTitre.setOnAction((ActionEvent e) -> {
            String coul=CPCouleurFondTitre.getValue().toString().substring(2,8);
            couleurFondTitre="#"+coul;
            txtTitre.setStyle("-fx-background-color : "+couleurFondTitre);
        });
        Label lblChoixOpacite = new Label(rb.getString("interface.choixOpaciteTitre"));
        lblChoixOpacite.setLayoutX(30);
        lblChoixOpacite.setLayoutY(105);
        Slider SLOpacite = new Slider(0, 1, titreOpacite);
        SLOpacite.setLayoutX(250);
        SLOpacite.setLayoutY(105);
        SLOpacite.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {                
                titreOpacite=(double) newValue;
                System.out.println("valeur " +titreOpacite);
                txtTitre.setOpacity(titreOpacite);
            }
        });

        Label lblChoixTaille = new Label(rb.getString("interface.choixTailleTitre"));
        lblChoixTaille.setLayoutX(30);
        lblChoixTaille.setLayoutY(135);
        Slider SLTaille = new Slider(0, 100, titreTaille);
        SLTaille.setLayoutX(250);
        SLTaille.setLayoutY(135);
        SLTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                titreTaille=(double) newValue/100.d*IMVisualisation.getFitWidth();
                txtTitre.setMinWidth(titreTaille);
                txtTitre.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
            }
        });

        
        APTitre.getChildren().addAll(lblChoixPoliceTitre, choixPoliceTitre,
                lblChoixCouleurTitre, CPCouleurTitre,
                lblChoixCouleurFondTitre, CPCouleurFondTitre,
                lblChoixOpacite,SLOpacite,
                lblChoixTaille,SLTaille);
        choixPoliceTitre.setOnAction((ActionEvent e) -> {
            Font fonte1 = txtTitre.getFont();
            Optional response = Dialogs.create().showFontSelector(fonte1);
            String rep1 = response.toString();
            String[] police = rep1.split("\\[")[2].replace("]", "").replace(", ", ",").split(",");
            for (String element : police) {
                String carac = element.split("=")[0];
                String valeur = element.split("=")[1];
                switch (carac) {
                    case "family":
                        titrePoliceNom = valeur;
                        break;
                    case "style":
                        titrePoliceStyle = valeur;
                        break;
                    case "size":
                        titrePoliceTaille = valeur;
                        break;
                }
            }
            //Font fonte=new Font(titrePoliceNom.replace(" ", ""),Double.parseDouble(titrePoliceTaille));
            fonte1 = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
            txtTitre.setFont(fonte1);
            txtTitre.setPrefHeight(-1);
//                System.out.println("font: " + response+" => "+rep1+" Police : "+titrePoliceNom+" : "+titrePoliceStyle+" : "+titrePoliceTaille);
        }
        );

        double tailleInitialeTitre = APTitre.getPrefHeight();
        lblPanelTitre.setOnMouseClicked((MouseEvent me) -> {
            if (APTitre.isVisible()) {
                APTitre.setPrefHeight(10);
                APTitre.setMaxHeight(10);
                APTitre.setMinHeight(10);
                APTitre.setVisible(false);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APTitre.setPrefHeight(tailleInitialeTitre);
                APTitre.setMaxHeight(tailleInitialeTitre);
                APTitre.setMinHeight(tailleInitialeTitre);
                APTitre.setVisible(true);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        IVBtnPlusTitre.setOnMouseClicked((MouseEvent me) -> {
            if (APTitre.isVisible()) {
                APTitre.setPrefHeight(10);
                APTitre.setMaxHeight(10);
                APTitre.setMinHeight(10);
                APTitre.setVisible(false);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APTitre.setPrefHeight(tailleInitialeTitre);
                APTitre.setMaxHeight(tailleInitialeTitre);
                APTitre.setMinHeight(tailleInitialeTitre);
                APTitre.setVisible(true);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        AnchorPane APBarreModif = new AnchorPane();

        Label lblBarreBouton = new Label(rb.getString("interface.barreBoutons"));
        lblBarreBouton.setPrefWidth(VBOutils.getPrefWidth());
        lblBarreBouton.setStyle("-fx-background-color : #444");
        lblBarreBouton.setTextFill(Color.WHITE);
        lblBarreBouton.setPadding(new Insets(5));
        lblBarreBouton.setLayoutX(30);
        lblBarreBouton.setLayoutY(10);
        ImageView IVBtnPlus = new ImageView(new Image("file:" + "images/moins.png", 20, 20, true, true));
        IVBtnPlus.setLayoutX(VBOutils.getPrefWidth());
        IVBtnPlus.setLayoutY(13);
        double tailleInitiale = APBarreModif.getPrefHeight();

        lblBarreBouton.setOnMouseClicked((MouseEvent me) -> {
            if (APBarreModif.isVisible()) {
                APBarreModif.setPrefHeight(10);
                APBarreModif.setMaxHeight(10);
                APBarreModif.setMinHeight(10);
                APBarreModif.setVisible(false);
                IVBtnPlus.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APBarreModif.setPrefHeight(tailleInitiale);
                APBarreModif.setMaxHeight(tailleInitiale);
                APBarreModif.setMinHeight(tailleInitiale);
                APBarreModif.setVisible(true);
                IVBtnPlus.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        IVBtnPlus.setOnMouseClicked((MouseEvent me) -> {
            if (APBarreModif.isVisible()) {
                APBarreModif.setPrefHeight(10);
                APBarreModif.setMaxHeight(10);
                APBarreModif.setMinHeight(10);
                APBarreModif.setVisible(false);
                IVBtnPlus.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APBarreModif.setPrefHeight(tailleInitiale);
                APBarreModif.setMaxHeight(tailleInitiale);
                APBarreModif.setMinHeight(tailleInitiale);
                APBarreModif.setVisible(true);
                IVBtnPlus.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        ImageView IVBtnPlusHS = new ImageView(new Image("file:" + "images/moins.png", 20, 20, true, true));
        IVBtnPlusHS.setLayoutX(VBOutils.getPrefWidth());
        IVBtnPlusHS.setLayoutY(13);
        Label lblChoixHS = new Label(rb.getString("interface.choixHotspot"));
        lblChoixHS.setPrefWidth(VBOutils.getPrefWidth());
        lblChoixHS.setStyle("-fx-background-color : #444");
        lblChoixHS.setTextFill(Color.WHITE);
        lblChoixHS.setPadding(new Insets(5));
        lblChoixHS.setLayoutX(30);
        lblChoixHS.setLayoutY(10);
        lblChoixHS.setOnMouseClicked((MouseEvent me) -> {
            if (APHotSpots.isVisible()) {
                IVBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(10);
                APHotSpots.setMaxHeight(10);
                APHotSpots.setMinHeight(10);
                APHotSpots.setVisible(false);
            } else {
                IVBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(tailleHS);
                APHotSpots.setMaxHeight(tailleHS);
                APHotSpots.setMinHeight(tailleHS);
                APHotSpots.setVisible(true);
            }
        });
        IVBtnPlusHS.setOnMouseClicked((MouseEvent me) -> {
            if (APHotSpots.isVisible()) {
                IVBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(10);
                APHotSpots.setMaxHeight(10);
                APHotSpots.setMinHeight(10);
                APHotSpots.setVisible(false);
            } else {
                IVBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(tailleHS);
                APHotSpots.setMaxHeight(tailleHS);
                APHotSpots.setMinHeight(tailleHS);
                APHotSpots.setVisible(true);
            }
        });

        APBarreModif.getChildren().addAll(
                CBVisible, lblStyle, CBlisteStyle, lblPosit,
                RBTopLeft, RBTopCenter, RBTopRight,
                RBMiddleLeft, RBMiddleCenter, RBMiddleRight,
                RBBottomLeft, RBBottomCenter, RBBottomRight,
                dXSpinner, dYSpinner,
                lblVisibilite, CBDeplacements, CBZoom, CBOutils,
                CBFS, CBRotation, CBSouris);
        APTIT.getChildren().addAll(APTitre, lblPanelTitre, IVBtnPlusTitre);
        APBB.getChildren().addAll(APBarreModif, lblBarreBouton, IVBtnPlus);
        APHS.getChildren().addAll(lblChoixHS, IVBtnPlusHS, APHotSpots);
        VBOutils.getChildren().addAll(
                APTIT, APBB, APHS
        );

        CBlisteStyle.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    APVisualisation.getChildren().remove(HBbarreBoutons);
                    APVisualisation.getChildren().remove(IVHotSpot);
                    styleBarre = t1;
                    afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
                }
            }
        });

        dXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            dXBarre = new_value.doubleValue();
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        dYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            dYBarre = new_value.doubleValue();
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        grpImage.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpImage.getSelectedToggle() != null) {
                switch (grpImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        IMVisualisation.setImage(imageClaire);
                        break;
                    case "sombre":
                        IMVisualisation.setImage(imageSombre);
                        break;
                }
            }
        });
        grpPostBarre.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostBarre.getSelectedToggle() != null) {
                APVisualisation.getChildren().remove(HBbarreBoutons);
                APVisualisation.getChildren().remove(IVHotSpot);
                positionBarre = grpPostBarre.getSelectedToggle().getUserData().toString();
                afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
            }
        });
    }

}
