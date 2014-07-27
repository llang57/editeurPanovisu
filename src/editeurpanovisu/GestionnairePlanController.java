/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.nombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.plans;
import static editeurpanovisu.EditeurPanovisu.repertAppli;
import static editeurpanovisu.EditeurPanovisu.repertTemp;
import static editeurpanovisu.EditeurPanovisu.stPrincipal;
import static editeurpanovisu.EditeurPanovisu.dejaSauve;
import static editeurpanovisu.EditeurPanovisu.gestionnaireInterface;
import static editeurpanovisu.EditeurPanovisu.nombrePlans;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 *
 * @author llang
 */
public class GestionnairePlanController {

    private final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});
    private final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    private ResourceBundle rb;
    public Pane tabInterface;
    private HBox HBInterface;
    private AnchorPane APPlan;
    private VBox VBOutils;
    private Slider SLnordPlan;
    private BigDecimalField BDFPositXBoussole;
    private BigDecimalField BDFPositYBoussole;
    private RadioButton RBBoussolePlanTopLeft;
    private RadioButton RBBoussolePlanTopRight;
    private RadioButton RBBoussolePlanBottomLeft;
    private RadioButton RBBoussolePlanBottomRight;
    final ToggleGroup grpPosBoussolePlan = new ToggleGroup();
    final ToggleGroup grpCBPano = new ToggleGroup();
    public double positionNordPlan = 0;
    public String positionBoussolePlan = "top:right";
    public double positXBoussolePlan = 0;
    public double positYBoussolePlan = 0;
    public String planListeVignette;
    private ImageView IVPlan;
    private Image imagePlan;
    public int planActuel = -1;
    private String tooltipStyle = "";
    private Pane panePlan;
    private ComboBox<String> CBChoixPlan;
    private AnchorPane APConfigPlan;
    public String repertImagePlan;
    private ImageView IVNord;
    private Image imgBoussole;
    private int numPoints = 0;
    private ScrollPane SPOutils;
    private Button btnValider;

    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < numPoints; i++) {
            Node pt = (Node) panePlan.lookup("#point" + i);
            panePlan.getChildren().remove(pt);
        }
    }

    private void valideHSPlan() {
        btnValider.setStyle("-fx-text-fill : #00AA00");
        btnValider.setText("Hotspots Validés");

        for (int i = 0; i < plans[planActuel].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) VBOutils.lookup("#cbplan" + i);
            TextArea txtTexteHS = (TextArea) VBOutils.lookup("#txtHSPlan" + i);
            plans[planActuel].getHotspot(i).setInfo(txtTexteHS.getText());
            plans[planActuel].getHotspot(i).setFichierXML(cbx.getValue() + ".xml");
        }
    }

    public String getTemplate() {
        String contenuFichier = "";
        for (int i = 0; i < nombrePlans; i++) {
            contenuFichier += "[Plan=>"
                    + "lienImage:" + plans[i].getLienPlan()
                    + ";image:" + plans[i].getImagePlan()
                    + ";nb:" + plans[i].getNombreHotspots()
                    + ";titre:" + plans[i].getTitrePlan()
                    + ";directionNord:" + plans[i].getDirectionNord()
                    + ";position:" + plans[i].getPosition()
                    + ";positionX:" + plans[i].getPositionX()
                    + ";positionY:" + plans[i].getPositionY()
                    + "]\n";
            for (int j = 0; j < plans[i].getNombreHotspots(); j++) {
                HotSpot HS = plans[i].getHotspot(j);
                contenuFichier += "   [hotspotPlan==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getFichierImage()
                        + ";xml:" + HS.getFichierXML()
                        + ";info:" + HS.getInfo()
                        + ";numeroPano:" + HS.getNumeroPano()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
        }
        return contenuFichier;
    }

    /**
     *
     * @param i
     * @param posX
     * @param posY
     */
    private void afficheHS(int i, double posX, double posY) {
        double largeur = imagePlan.getWidth();
        double hauteur = imagePlan.getHeight();
        double X = posX * largeur;
        double Y = posY * hauteur;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.YELLOW);
        point.setStroke(Color.RED);
        point.setId("point" + i);
        point.setCursor(Cursor.DEFAULT);
        panePlan.getChildren().add(point);

        Tooltip t = new Tooltip("point n° " + (i + 1));
        t.setStyle(tooltipStyle);
        Tooltip.install(point, t);

        point.setOnMouseClicked((MouseEvent me1) -> {
            btnValider.setStyle("-fx-text-fill : #DD0000");
            btnValider.setText("Valider les Hotspots");
            double mouseX = me1.getSceneX();
            double mouseY = me1.getSceneY() - panePlan.getLayoutY() - 115;
            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) panePlan.lookup("#point" + chPoint);

            if (me1.isControlDown()) {
                valideHSPlan();
                dejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePlan.getChildren().remove(pt);

                for (int o = numeroPoint + 1; o < numPoints; o++) {
                    pt = (Node) panePlan.lookup("#point" + Integer.toString(o));
                    pt.setId("point" + Integer.toString(o - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                numPoints--;
                plans[planActuel].removeHotspot(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                me1.consume();
            } else {
                valideHSPlan();
                if (nombrePanoramiques > 1) {
                    int largeurVignettes = 4;
                    if (nombrePanoramiques < 4) {
                        largeurVignettes = nombrePanoramiques;
                    }
                    AnchorPane listePanoVig = afficherListePanosVignettes(numeroPoint);
                    if (mouseX + largeurVignettes * 130 > panePlan.getWidth()) {
                        listePanoVig.setLayoutX(panePlan.getWidth() - largeurVignettes * 130);
                    } else {
                        listePanoVig.setLayoutX(mouseX);
                    }
                    listePanoVig.setLayoutY(mouseY);
                    panePlan.getChildren().add(listePanoVig);
                }
                me1.consume();

            }

        });
    }

    /**
     *
     */
    private void ajouteAffichagePointsHotspots() {
        for (int i = 0; i < plans[planActuel].getNombreHotspots(); i++) {
            double longitude = plans[planActuel].getHotspot(i).getLongitude();
            double latitude = plans[planActuel].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     * @param lstPano
     * @param numPano
     * @return
     */
    public Pane affichageHS(String lstPano, int numPano) {

        Pane panneauHotSpots = new Pane();
        VBox vb1 = new VBox();
        panneauHotSpots.getChildren().add(vb1);
        Label lblPoint;
        Label sep = new Label(" ");
        Label sep1 = new Label(" ");
        for (int o = 0; o < plans[numPano].getNombreHotspots(); o++) {
            AnchorPane vbPanneauHS = new AnchorPane();

            vbPanneauHS.setId("HS" + o);
            lblPoint = new Label("Point n°" + (o + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setStyle("-fx-background-color : #333;");
            lblPoint.setTextFill(Color.WHITE);
            lblPoint.setLayoutX(20);
            lblPoint.setLayoutY(10);
            Separator sp = new Separator(Orientation.HORIZONTAL);
            sp.setPrefWidth(370);
            sp.setLayoutY(40);
            //pannneauHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sp);
            if (lstPano != null) {
                Label lblLien = new Label("Panoramique de destination");
                lblLien.setLayoutX(20);
                lblLien.setLayoutY(60);
                ComboBox cbDestPano = new ComboBox();
                String[] liste = lstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(liste));
                cbDestPano.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String t, String t1) {
                        btnValider.setStyle("-fx-text-fill : #DD0000");
                        btnValider.setText("Valider les Hotspots");
                    }
                });

                cbDestPano.setId("cbplan" + o);
                cbDestPano.setLayoutX(60);
                cbDestPano.setLayoutY(90);
                String f1XML = plans[numPano].getHotspot(o).getFichierXML();
                if (f1XML != null) {
                    cbDestPano.setValue(f1XML.split("\\.")[0]);
                }
                vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, sep);
            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            lblTexteHS.setLayoutX(20);
            lblTexteHS.setLayoutY(120);

            TextArea txtTexteHS = new TextArea();
            txtTexteHS.setLayoutX(60);
            txtTexteHS.setLayoutY(150);
            txtTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                btnValider.setStyle("-fx-text-fill : #DD0000");
                btnValider.setText("Valider les Hotspots");
            });

            if (plans[numPano].getHotspot(o).getInfo() != null) {
                txtTexteHS.setText(plans[numPano].getHotspot(o).getInfo());
            }
            txtTexteHS.setId("txtHSPlan" + o);
            txtTexteHS.setPrefSize(200, 25);
            txtTexteHS.setMaxSize(200, 20);
            vbPanneauHS.getChildren().addAll(lblTexteHS, txtTexteHS, sep1);
            vb1.getChildren().addAll(vbPanneauHS, sep);
        }

        btnValider = new Button("HotSpots Validés");
        btnValider.setStyle("-fx-text-fill : #00AA00");
        btnValider.setTranslateX(200);
        btnValider.setTranslateY(5);
        btnValider.setPadding(new Insets(7));
        btnValider.setOnAction((ActionEvent e) -> {
            valideHSPlan();
        });
        vb1.getChildren().addAll(btnValider, sep1);
        return panneauHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane lbl = affichageHS(listePano(), planActuel);
        lbl.setId("labelConfigPlan");
        VBOutils.getChildren().add(lbl);
        numPoints = plans[planActuel].getNombreHotspots();
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        Pane lbl = (Pane) VBOutils.lookup("#labelConfigPlan");
        VBOutils.getChildren().remove(lbl);
    }
/**
 * 
 */
    public void ajouterPlan() {
        CBChoixPlan.getItems().add(nombrePlans, plans[nombrePlans].getImagePlan());
        afficherPlan(nombrePlans);
        CBChoixPlan.getSelectionModel().select(nombrePlans);
    }
/**
 * 
 * @param numeroPlan 
 */
    public void afficherPlan(int numeroPlan) {
        planActuel = numeroPlan;
        String txtImage = repertTemp + "/images/" + plans[numeroPlan].getImagePlan();
        imagePlan = new Image("file:" + txtImage);
        plans[planActuel].setLargeurPlan(imagePlan.getWidth());
        plans[planActuel].setHauteurPlan(imagePlan.getHeight());
        IVPlan.setImage(imagePlan);
        panePlan.setLayoutX((APPlan.getPrefWidth() - imagePlan.getWidth()) / 2);
        panePlan.setPrefSize(imagePlan.getWidth(), imagePlan.getHeight());
        panePlan.setMinSize(imagePlan.getWidth(), imagePlan.getHeight());
        panePlan.setMaxSize(imagePlan.getWidth(), imagePlan.getHeight());
        IVPlan.setLayoutY(0);
        panePlan.setCursor(Cursor.CROSSHAIR);
        IVPlan.setTranslateZ(0);
        IVNord.setTranslateZ(1);
        afficheConfigPlan();
        afficheBoussole();
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        ajouteAffichageHotspots();
        ajouteAffichagePointsHotspots();
    }
/**
 * 
 */
    private void afficheBoussole() {
        if (!imagePlan.isError()) {
            String posX = plans[planActuel].getPosition().split(":")[1];
            String posY = plans[planActuel].getPosition().split(":")[0];
            double positionX = 0;
            double positionY = 0;
            switch (posX) {
                case "left":
                    positionX = IVPlan.getLayoutX() + plans[planActuel].getPositionX();
                    break;
                case "right":
                    positionX = IVPlan.getLayoutX() + imagePlan.getWidth() - imgBoussole.getWidth() - plans[planActuel].getPositionX();
                    break;
            }
            switch (posY) {
                case "top":
                    positionY = IVPlan.getLayoutY() + plans[planActuel].getPositionY();
                    break;
                case "bottom":
                    positionY = IVPlan.getLayoutY() + imagePlan.getHeight() - imgBoussole.getHeight() - plans[planActuel].getPositionY();
                    break;
            }
            IVNord.setLayoutX(positionX);
            IVNord.setLayoutY(positionY);
            IVNord.setRotate(plans[planActuel].getDirectionNord());
        }
    }

    /**
     *
     * @param numHS
     * @return
     */
    private AnchorPane afficherListePanosVignettes(int numHS) {

        AnchorPane APlistePano = new AnchorPane();
        APlistePano.setOpacity(1);
        Pane fond = new Pane();
        fond.setStyle("-fx-background-color : #bbb;");
        fond.setPrefWidth(540);
        fond.setPrefHeight(((nombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        fond.setMinWidth(540);
        fond.setMinHeight(70);
        APlistePano.getChildren().add(fond);
        APlistePano.setStyle("-fx-backgroung-color : #bbb;");
        int j = 0;
        ImageView[] IVPano;
        IVPano = new ImageView[nombrePanoramiques];
        double xPos;
        double yPos;
        int row = 0;
        for (int i = 0; i < nombrePanoramiques; i++) {
            int numeroPano = i;
            IVPano[j] = new ImageView(panoramiquesProjet[i].getVignettePanoramique());
            IVPano[j].setFitWidth(120);
            IVPano[j].setFitHeight(60);
            IVPano[j].setSmooth(true);
            String nomPano = panoramiquesProjet[i].getNomFichier();
            int col = j % 4;
            row = j / 4;
            xPos = col * 130 + 25;
            yPos = row * 65 + 5;
            IVPano[j].setLayoutX(xPos);
            IVPano[j].setLayoutY(yPos);
            IVPano[j].setCursor(Cursor.HAND);
            IVPano[j].setStyle("-fx-background-color : #ccc;");
            Tooltip t = new Tooltip(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
            t.setStyle(tooltipStyle);
            Tooltip.install(IVPano[j], t);
            IVPano[j].setOnMouseClicked((MouseEvent me) -> {
                planListeVignette = nomPano;
                if (panoramiquesProjet[numeroPano].getTitrePanoramique() != null) {
                    String texteHS = panoramiquesProjet[numeroPano].getTitrePanoramique();
                    TextArea txtHS = (TextArea) VBOutils.lookup("#txtHSPlan" + numHS);
                    txtHS.setText(texteHS);
                }
                btnValider.setStyle("-fx-text-fill : #DD0000");
                btnValider.setText("Valider les Hotspots");
                plans[planActuel].getHotspot(numHS).setNumeroPano(numeroPano);
                ComboBox cbx = (ComboBox) VBOutils.lookup("#cbplan" + numHS);
                cbx.setValue(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
                APlistePano.setVisible(false);
                me.consume();
            });
            APlistePano.getChildren().add(IVPano[j]);
            j++;

        }
        int taille = (row + 1) * 65 + 5;
        APlistePano.setPrefWidth(540);
        APlistePano.setPrefHeight(taille);
        APlistePano.setMinWidth(540);
        APlistePano.setMinHeight(taille);
        ImageView IVClose = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
        IVClose.setLayoutX(2);
        IVClose.setLayoutY(5);
        IVClose.setCursor(Cursor.HAND);
        APlistePano.getChildren().add(IVClose);
        IVClose.setOnMouseClicked((MouseEvent me) -> {
            planListeVignette = "";
            APlistePano.setVisible(false);
            me.consume();
        });
        APlistePano.setTranslateZ(2);
        return APlistePano;
    }

    /**
     *
     */
    private void afficheConfigPlan() {
        Label lblNordPlan = new Label(rb.getString("plan.positionNordPlan"));
        lblNordPlan.setLayoutX(10);
        lblNordPlan.setLayoutY(10);
        SLnordPlan = new Slider(0, 360.0, 0);
        SLnordPlan.setLayoutX(200);
        SLnordPlan.setLayoutY(10);
        Separator sepPlan = new Separator(Orientation.HORIZONTAL);
        sepPlan.setPrefHeight(10);
        sepPlan.setPrefWidth(350);
        sepPlan.setLayoutY(30);
        Label lblPositBoussolePlan = new Label(rb.getString("plan.positionBoussolePlan"));
        lblPositBoussolePlan.setLayoutX(10);
        lblPositBoussolePlan.setLayoutY(50);
        RBBoussolePlanTopLeft = new RadioButton("");
        RBBoussolePlanTopLeft.setLayoutX(200);
        RBBoussolePlanTopLeft.setLayoutY(50);
        RBBoussolePlanTopLeft.setUserData("top:left");
        RBBoussolePlanTopLeft.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanTopRight = new RadioButton("");
        RBBoussolePlanTopRight.setLayoutX(230);
        RBBoussolePlanTopRight.setLayoutY(50);
        RBBoussolePlanTopRight.setUserData("top:right");
        RBBoussolePlanTopRight.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanBottomLeft = new RadioButton("");
        RBBoussolePlanBottomLeft.setLayoutX(200);
        RBBoussolePlanBottomLeft.setLayoutY(80);
        RBBoussolePlanBottomLeft.setUserData("bottom:left");
        RBBoussolePlanBottomLeft.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanBottomRight = new RadioButton("");
        RBBoussolePlanBottomRight.setLayoutX(230);
        RBBoussolePlanBottomRight.setLayoutY(80);
        RBBoussolePlanBottomRight.setUserData("bottom:right");
        RBBoussolePlanBottomRight.setToggleGroup(grpPosBoussolePlan);

        Label lblBoussoleDXSpinner = new Label("dX :");
        lblBoussoleDXSpinner.setLayoutX(25);
        lblBoussoleDXSpinner.setLayoutY(110);
        Label lblBoussoleDYSpinner = new Label("dY :");
        lblBoussoleDYSpinner.setLayoutX(175);
        lblBoussoleDYSpinner.setLayoutY(110);
        BDFPositXBoussole = new BigDecimalField();
        BDFPositXBoussole.setLayoutX(50);
        BDFPositXBoussole.setLayoutY(105);
        BDFPositXBoussole.setMaxValue(new BigDecimal(200));
        BDFPositXBoussole.setMinValue(new BigDecimal(0));
        BDFPositXBoussole.setMaxWidth(100);
        BDFPositYBoussole = new BigDecimalField();
        BDFPositYBoussole.setLayoutX(200);
        BDFPositYBoussole.setLayoutY(105);
        BDFPositYBoussole.setMaxValue(new BigDecimal(200));
        BDFPositYBoussole.setMinValue(new BigDecimal(0));
        BDFPositYBoussole.setMaxWidth(100);

        BDFPositXBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            plans[planActuel].setPositionX(new_value.doubleValue());
            afficheBoussole();
        });
        BDFPositYBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            plans[planActuel].setPositionY(new_value.doubleValue());
            afficheBoussole();
        });
        grpPosBoussolePlan.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosBoussolePlan.getSelectedToggle() != null) {
                plans[planActuel].setPosition(grpPosBoussolePlan.getSelectedToggle().getUserData().toString());
                afficheBoussole();
            }
        });
        SLnordPlan.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double direction = (double) newValue;
                plans[planActuel].setDirectionNord(direction);
                afficheBoussole();
            }
        });
        SLnordPlan.setValue(plans[planActuel].getDirectionNord());
        RBBoussolePlanTopLeft.setSelected(plans[planActuel].getPosition().equals("top:left"));
        RBBoussolePlanTopRight.setSelected(plans[planActuel].getPosition().equals("top:right"));
        RBBoussolePlanBottomLeft.setSelected(plans[planActuel].getPosition().equals("bottom:left"));
        RBBoussolePlanBottomRight.setSelected(plans[planActuel].getPosition().equals("bottom:right"));
        BDFPositXBoussole.setNumber(new BigDecimal(plans[planActuel].getPositionX()));
        BDFPositYBoussole.setNumber(new BigDecimal(plans[planActuel].getPositionY()));
        VBOutils.getChildren().remove(APConfigPlan);

        APConfigPlan = new AnchorPane();
        APConfigPlan.getChildren().addAll(
                lblNordPlan, SLnordPlan,
                lblPositBoussolePlan, RBBoussolePlanTopLeft, RBBoussolePlanTopRight,
                RBBoussolePlanBottomLeft, RBBoussolePlanBottomRight,
                lblBoussoleDXSpinner, BDFPositXBoussole, lblBoussoleDYSpinner, BDFPositYBoussole
        );
        if (nombrePanoramiques != 0) {
            Separator sepConfig1 = new Separator(Orientation.HORIZONTAL);
            sepConfig1.setPrefSize(380, 20);
            sepConfig1.setLayoutY(125);
            APConfigPlan.getChildren().add(sepConfig1);
            Label lblPanoAffichePlan = new Label(rb.getString("plan.panoAffichePlan"));
            lblPanoAffichePlan.setLayoutX(20);
            lblPanoAffichePlan.setLayoutY(160);
            APConfigPlan.getChildren().add(lblPanoAffichePlan);
            CheckBox[] CBPanoramiques = new CheckBox[nombrePanoramiques];
            for (int i = 0; i < nombrePanoramiques; i++) {
                final int num = i;
                CBPanoramiques[i] = new CheckBox();
                CBPanoramiques[i].setUserData(i);
                CBPanoramiques[i].setLayoutX(30);
                CBPanoramiques[i].setLayoutY(190 + i * 30);
                CBPanoramiques[i].setText(panoramiquesProjet[i].getNomFichier());
                if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() == planActuel) {
                    CBPanoramiques[i].setSelected(true);
                }
                if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() != planActuel) {
                    CBPanoramiques[i].setDisable(true);
                }
                APConfigPlan.getChildren().add(CBPanoramiques[i]);
                CBPanoramiques[i].selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    panoramiquesProjet[num].setAffichePlan(new_val);
                    panoramiquesProjet[num].setNumeroPlan(planActuel);
                });
            }
        }
        Separator sepConfig2 = new Separator(Orientation.HORIZONTAL);
        sepConfig2.setPrefSize(380, 20);
        sepConfig2.setLayoutY(210 + nombrePanoramiques * 30);
        APConfigPlan.getChildren().add(sepConfig2);
        VBOutils.getChildren().add(APConfigPlan);
    }

    /**
     *
     * @return
     */
    private String listePano() {
        String liste = "";
        if (nombrePanoramiques == 0) {
            return null;
        } else {
            for (int i = 0; i < nombrePanoramiques; i++) {
                String fichierPano = panoramiquesProjet[i].getNomFichier();
                String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                String[] nPano = nomPano.split("\\.");
                liste += nPano[0];
                if (i < nombrePanoramiques - 1) {
                    liste += ";";
                }
            }
            return liste;
        }
    }

    /**
     *
     * @param X
     * @param Y
     */
    private void planMouseClic(double X, double Y) {
        if (nombrePanoramiques > 0) {

            valideHSPlan();
            dejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X - panePlan.getLayoutX();
            double mouseY = Y - panePlan.getLayoutY() - 110;
            double posX, posY;
            double largeur = imagePlan.getWidth();
            double hauteur = imagePlan.getHeight();
            posX = mouseX;
            posY = mouseY;
            Circle point = new Circle(mouseX, mouseY, 5);
            point.setFill(Color.YELLOW);
            point.setStroke(Color.RED);
            point.setId("point" + numPoints);
            point.setCursor(Cursor.DEFAULT);
            panePlan.getChildren().add(point);
            Tooltip t = new Tooltip("point n°" + (numPoints + 1));
            t.setStyle(tooltipStyle);
            Tooltip.install(point, t);
            HotSpot HS = new HotSpot();
            HS.setLongitude(posX / largeur);
            HS.setLatitude(posY / hauteur);
            plans[planActuel].addHotspot(HS);
            retireAffichageHotSpots();
            ajouteAffichageHotspots();
//        Pane affHS1 = affichageHS(listePano(), planActuel);
//        affHS1.setId("labelConfigPlan");
//        VBOutils.getChildren().add(affHS1);
//
            //numPoints++;
                AnchorPane listePanoVig = afficherListePanosVignettes(plans[planActuel].getNombreHotspots() - 1);
                int largeurVignettes = 4;
                if (nombrePanoramiques < 4) {
                    largeurVignettes = nombrePanoramiques;
                }
                if (mouseX + largeurVignettes * 130 > panePlan.getWidth()) {
                    listePanoVig.setLayoutX(panePlan.getWidth() - largeurVignettes * 130);
                } else {
                    listePanoVig.setLayoutX(mouseX);
                }
                listePanoVig.setLayoutY(mouseY);
                panePlan.getChildren().add(listePanoVig);
            point.setOnMouseClicked((MouseEvent me1) -> {
                if (me1.isControlDown()) {
                    valideHSPlan();
                    dejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(5, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    Node pt;
                    pt = (Node) panePlan.lookup("#point" + chPoint);
                    panePlan.getChildren().remove(pt);

                    for (int o = numeroPoint + 1; o < numPoints; o++) {
                        pt = (Node) panePlan.lookup("#point" + Integer.toString(o));
                        pt.setId("point" + Integer.toString(o - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    btnValider.setStyle("-fx-text-fill : #DD0000");
                    btnValider.setText("Valider les Hotspots");

                    retireAffichageHotSpots();
                    numPoints--;
                    plans[planActuel].removeHotspot(numeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                    me1.consume();
                } else {
                    valideHSPlan();
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(5, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    if (nombrePanoramiques > 1) {
                        AnchorPane listePanoVig1 = afficherListePanosVignettes(numeroPoint);
                        int largeurVignettes1 = 4;
                        if (nombrePanoramiques < 4) {
                            largeurVignettes1 = nombrePanoramiques;
                        }
                        if (mouseX + largeurVignettes1 * 130 > panePlan.getWidth()) {
                            listePanoVig1.setLayoutX(panePlan.getWidth() - largeurVignettes1 * 130);
                        } else {
                            listePanoVig1.setLayoutX(mouseX);
                        }
                        listePanoVig1.setLayoutY(mouseY);
                        panePlan.getChildren().add(listePanoVig1);
                    }
                    me1.consume();

                }
            });
        }
    }

    private void gereSourisPlan(MouseEvent me) {
        if (me.getButton() == MouseButton.PRIMARY) {
            if (!(me.isControlDown()) && planActuel != -1) {
                planMouseClic(me.getSceneX(), me.getSceneY());
            }
        }
    }

    /**
     *
     * @param width
     * @param height
     */
    public void creeInterface(int width, int height) {
        repertImagePlan = repertAppli + File.separator + "theme/plan";;
        rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        tabInterface = new Pane();
        HBInterface = new HBox();
        VBOutils = new VBox();
        HBInterface.setPrefWidth(width);
        HBInterface.setPrefHeight(height);
        AnchorPane APChoixPlan = new AnchorPane();

        double largeurOutils = 380;
        APPlan = new AnchorPane();
        APPlan.setPrefWidth(width - largeurOutils);
        APPlan.setPrefHeight(height);
        IVPlan = new ImageView();
        String imageBoussole = "file:" + repertImagePlan + "/aiguillePlan.png";
        imgBoussole = new Image(imageBoussole);
        IVNord = new ImageView(imgBoussole);
        panePlan = new Pane();
        panePlan.getChildren().addAll(IVPlan, IVNord);
        APPlan.setMaxWidth(width - largeurOutils - 20);
        APPlan.getChildren().add(panePlan);

        SPOutils = new ScrollPane();
        SPOutils.setContent(VBOutils);
        SPOutils.setId("spOutils");
        SPOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        SPOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPOutils.setPrefSize(largeurOutils, height - 100);
        SPOutils.setMaxWidth(largeurOutils);
        SPOutils.setMinWidth(largeurOutils);
        SPOutils.setMaxHeight(height - 100);
        SPOutils.setTranslateY(15);
        SPOutils.setTranslateX(20);

        VBOutils.setPrefWidth(largeurOutils - 20);
        VBOutils.setMaxWidth(largeurOutils - 20);
        //VBOutils.setMinHeight(height);
//        VBOutils.setStyle("-fx-background-color : #ccc;");
        HBInterface.getChildren().addAll(APPlan, SPOutils);

        tabInterface.getChildren().add(HBInterface);
        Label lblChoixPlan = new Label(rb.getString("plan.choixPlan"));
        lblChoixPlan.setLayoutX(20);
        lblChoixPlan.setLayoutY(10);
        CBChoixPlan = new ComboBox();
        CBChoixPlan.setLayoutX(110);
        CBChoixPlan.setLayoutY(40);
        CBChoixPlan.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                afficherPlan(CBChoixPlan.getSelectionModel().getSelectedIndex());
                gestionnaireInterface.affichePlan();
            }
        });
        APChoixPlan.getChildren().addAll(lblChoixPlan, CBChoixPlan);
        Separator sepChoix = new Separator(Orientation.HORIZONTAL);
        sepChoix.setPrefSize(largeurOutils + 50, 20);
        APConfigPlan = new AnchorPane();
        APConfigPlan.setId("configPlan");
        VBOutils.getChildren().addAll(
                APChoixPlan,
                sepChoix,
                APConfigPlan
        );
        panePlan.setOnMouseClicked(
                (MouseEvent me) -> {
                    gereSourisPlan(me);
                }
        );
    }
}
