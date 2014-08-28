/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.dejaSauve;
import static editeurpanovisu.EditeurPanovisu.nombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.repertAppli;
import static editeurpanovisu.EditeurPanovisu.gestionnaireInterface;
import static editeurpanovisu.EditeurPanovisu.gestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.nombrePlans;
import static editeurpanovisu.EditeurPanovisu.plans;
import static editeurpanovisu.EditeurPanovisu.repertAppli;
import static editeurpanovisu.EditeurPanovisu.repertTemp;
import static editeurpanovisu.EditeurPanovisu.stPrincipal;
import static editeurpanovisu.EditeurPanovisu.tooltipStyle;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import jfxtras.labs.scene.control.BigDecimalField;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

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
    public Label lblDragDropPlan;
    public Pane tabInterface;
    private HBox hbInterface;
    private AnchorPane apPlan;
    private VBox vbOutils;
    private Slider slNordPlan;
    private BigDecimalField bdfPositXBoussole;
    private BigDecimalField bdfPositYBoussole;
    private RadioButton rbBoussolePlanTopLeft;
    private RadioButton rbBoussolePlanTopRight;
    private RadioButton rbBoussolePlanBottomLeft;
    private RadioButton rbBoussolePlanBottomRight;
    final ToggleGroup grpPosBoussolePlan = new ToggleGroup();
    final ToggleGroup grpcbPano = new ToggleGroup();
    public double positionNordPlan = 0;
    public String positionBoussolePlan = "top:right";
    public double positXBoussolePlan = 0;
    public double positYBoussolePlan = 0;
    public String planListeVignette;
    private ImageView ivPlan;
    private Image imagePlan;
    public int planActuel = -1;
    private String tooltipStyle = "";
    private Pane panePlan;
    private ComboBox<String> cbChoixPlan;
    private AnchorPane apConfigPlan;
    public String repertImagePlan;
    private ImageView ivNord;
    private Image imgBoussole;
    private int numPoints = 0;
    private ScrollPane spOutils;
    private boolean dragDropPlan = false;
//    private Button btnValider;

    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < numPoints; i++) {
            Node pt = (Node) panePlan.lookup("#point" + i);
            panePlan.getChildren().remove(pt);
        }
    }

    private void valideHsplan() {
        for (int i = 0; i < plans[planActuel].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + i);
            TextArea txtTexteHS = (TextArea) vbOutils.lookup("#txtHsplan" + i);
            if (txtTexteHS != null) {
                plans[planActuel].getHotspot(i).setInfo(txtTexteHS.getText());
            }
            if (cbx != null) {
                plans[planActuel].getHotspot(i).setFichierXML(cbx.getValue() + ".xml");
            }
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
        point.setOnDragDetected((MouseEvent me1) -> {
            point.setFill(Color.RED);
            point.setStroke(Color.YELLOW);
            dragDropPlan = true;
            System.out.println("d&d plan : " + dragDropPlan);
            me1.consume();

        });
        point.setOnMouseDragged((MouseEvent me1) -> {
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();

            double mouseXX = X1 - panePlan.getLayoutX();
            double mouseYY = Y1 - panePlan.getLayoutY() - 110;
            point.setCenterX(mouseXX);
            point.setCenterY(mouseYY);
            me1.consume();

        });
        point.setOnMouseReleased((MouseEvent me1) -> {
            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();

            double mouseXX = X1 - panePlan.getLayoutX();
            double mouseYY = Y1 - panePlan.getLayoutY() - 110;
            plans[planActuel].getHotspot(numeroPoint).setLongitude(mouseXX / largeur);
            plans[planActuel].getHotspot(numeroPoint).setLatitude(mouseYY / hauteur);
            point.setFill(Color.YELLOW);
            point.setStroke(Color.RED);
            me1.consume();
        });

        point.setOnMouseClicked((MouseEvent me1) -> {
            double mouseX = me1.getSceneX();
            double mouseY = me1.getSceneY() - panePlan.getLayoutY() - 115;
            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) panePlan.lookup("#point" + chPoint);

            if (me1.isControlDown()) {
                valideHsplan();
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
                valideHsplan();
                me1.consume();
            } else {
                System.out.println("d&d plan : " + dragDropPlan);
                if (!dragDropPlan) {
                    valideHsplan();
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
                    valideHsplan();
                    me1.consume();
                }
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
                        valideHsplan();
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
                valideHsplan();
            });

            if (plans[numPano].getHotspot(o).getInfo() != null) {
                txtTexteHS.setText(plans[numPano].getHotspot(o).getInfo());
            }
            txtTexteHS.setId("txtHsplan" + o);
            txtTexteHS.setPrefSize(200, 25);
            txtTexteHS.setMaxSize(200, 20);
            vbPanneauHS.getChildren().addAll(lblTexteHS, txtTexteHS, sep1);
            vb1.getChildren().addAll(vbPanneauHS, sep);
        }

        return panneauHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane lbl = affichageHS(listePano(), planActuel);
        lbl.setId("labelConfigPlan");
        vbOutils.getChildren().add(lbl);
        numPoints = plans[planActuel].getNombreHotspots();
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        Pane lbl = (Pane) vbOutils.lookup("#labelConfigPlan");
        vbOutils.getChildren().remove(lbl);
    }

    /**
     *
     */
    public void ajouterPlan() {
        cbChoixPlan.getItems().add(nombrePlans, plans[nombrePlans].getImagePlan());
        afficherPlan(nombrePlans);
        cbChoixPlan.getSelectionModel().select(nombrePlans);
        lblDragDropPlan.setVisible(false);
    }

    /**
     *
     * @param numeroPlan
     */
    public void afficherPlan(int numeroPlan) {
        planActuel = numeroPlan;
        String txtImage = repertTemp + "/images/" + plans[numeroPlan].getImagePlan();
        imagePlan = new Image("file:" + txtImage);
        if (imagePlan.getWidth() > 1000) {
            imagePlan = new Image("file:" + txtImage, 1000, -1, true, true);
        }
        if (imagePlan.getHeight() > hbInterface.getPrefHeight() - 100) {
            imagePlan = new Image("file:" + txtImage, -1, hbInterface.getPrefHeight() - 100, true, true);
        }
        plans[planActuel].setLargeurPlan(imagePlan.getWidth());
        plans[planActuel].setHauteurPlan(imagePlan.getHeight());
        ivPlan.setImage(imagePlan);
        panePlan.setLayoutX((apPlan.getPrefWidth() - imagePlan.getWidth()) / 2);
        panePlan.setPrefSize(imagePlan.getWidth(), imagePlan.getHeight());
        panePlan.setMinSize(imagePlan.getWidth(), imagePlan.getHeight());
        panePlan.setMaxSize(imagePlan.getWidth(), imagePlan.getHeight());
        ivPlan.setLayoutY(0);
        panePlan.setCursor(Cursor.CROSSHAIR);
        ivPlan.setTranslateZ(0);
        ivNord.setTranslateZ(1);
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
                    positionX = ivPlan.getLayoutX() + plans[planActuel].getPositionX();
                    break;
                case "right":
                    positionX = ivPlan.getLayoutX() + imagePlan.getWidth() - imgBoussole.getWidth() - plans[planActuel].getPositionX();
                    break;
            }
            switch (posY) {
                case "top":
                    positionY = ivPlan.getLayoutY() + plans[planActuel].getPositionY();
                    break;
                case "bottom":
                    positionY = ivPlan.getLayoutY() + imagePlan.getHeight() - imgBoussole.getHeight() - plans[planActuel].getPositionY();
                    break;
            }
            ivNord.setLayoutX(positionX);
            ivNord.setLayoutY(positionY);
            ivNord.setRotate(plans[planActuel].getDirectionNord());
        }
    }

    /**
     *
     * @param numHS
     * @return
     */
    private AnchorPane afficherListePanosVignettes(int numHS) {

        AnchorPane aplistePano = new AnchorPane();
        aplistePano.setOpacity(1);
        Pane fond = new Pane();
        fond.setStyle("-fx-background-color : #bbb;");
        fond.setPrefWidth(540);
        fond.setPrefHeight(((nombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        fond.setMinWidth(540);
        fond.setMinHeight(70);
        aplistePano.getChildren().add(fond);
        aplistePano.setStyle("-fx-backgroung-color : #bbb;");
        int j = 0;
        ImageView[] ivPano;
        ivPano = new ImageView[nombrePanoramiques];
        double xPos;
        double yPos;
        int row = 0;
        for (int i = 0; i < nombrePanoramiques; i++) {
            int numeroPano = i;
            ivPano[j] = new ImageView(panoramiquesProjet[i].getVignettePanoramique());
            ivPano[j].setFitWidth(120);
            ivPano[j].setFitHeight(60);
            ivPano[j].setSmooth(true);
            String nomPano = panoramiquesProjet[i].getNomFichier();
            int col = j % 4;
            row = j / 4;
            xPos = col * 130 + 25;
            yPos = row * 65 + 5;
            ivPano[j].setLayoutX(xPos);
            ivPano[j].setLayoutY(yPos);
            ivPano[j].setCursor(Cursor.HAND);
            ivPano[j].setStyle("-fx-background-color : #ccc;");
            Tooltip t = new Tooltip(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
            t.setStyle(tooltipStyle);
            Tooltip.install(ivPano[j], t);
            ivPano[j].setOnMouseClicked((MouseEvent me) -> {
                planListeVignette = nomPano;
                if (panoramiquesProjet[numeroPano].getTitrePanoramique() != null) {
                    String texteHS = panoramiquesProjet[numeroPano].getTitrePanoramique();
                    TextArea txtHS = (TextArea) vbOutils.lookup("#txtHsplan" + numHS);
                    txtHS.setText(texteHS);
                }
                plans[planActuel].getHotspot(numHS).setNumeroPano(numeroPano);
                ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + numHS);
                cbx.setValue(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
                aplistePano.setVisible(false);
                valideHsplan();
                me.consume();
            });
            aplistePano.getChildren().add(ivPano[j]);
            j++;

        }
        int taille = (row + 1) * 65 + 5;
        aplistePano.setPrefWidth(540);
        aplistePano.setPrefHeight(taille);
        aplistePano.setMinWidth(540);
        aplistePano.setMinHeight(taille);
        ImageView ivClose = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
        ivClose.setLayoutX(2);
        ivClose.setLayoutY(5);
        ivClose.setCursor(Cursor.HAND);
        aplistePano.getChildren().add(ivClose);
        ivClose.setOnMouseClicked((MouseEvent me) -> {
            planListeVignette = "";
            aplistePano.setVisible(false);
            me.consume();
        });
        aplistePano.setTranslateZ(2);
        return aplistePano;
    }

    /**
     *
     */
    public void afficheConfigPlan() {
        if (planActuel != -1) {
            Label lblNordPlan = new Label(rb.getString("plan.positionNordPlan"));
            lblNordPlan.setLayoutX(10);
            lblNordPlan.setLayoutY(10);
            slNordPlan = new Slider(0, 360.0, 0);
            slNordPlan.setLayoutX(200);
            slNordPlan.setLayoutY(10);
            Separator sepPlan = new Separator(Orientation.HORIZONTAL);
            sepPlan.setPrefHeight(10);
            sepPlan.setPrefWidth(350);
            sepPlan.setLayoutY(30);
            Label lblPositBoussolePlan = new Label(rb.getString("plan.positionBoussolePlan"));
            lblPositBoussolePlan.setLayoutX(10);
            lblPositBoussolePlan.setLayoutY(50);
            rbBoussolePlanTopLeft = new RadioButton("");
            rbBoussolePlanTopLeft.setLayoutX(200);
            rbBoussolePlanTopLeft.setLayoutY(50);
            rbBoussolePlanTopLeft.setUserData("top:left");
            rbBoussolePlanTopLeft.setToggleGroup(grpPosBoussolePlan);
            rbBoussolePlanTopRight = new RadioButton("");
            rbBoussolePlanTopRight.setLayoutX(230);
            rbBoussolePlanTopRight.setLayoutY(50);
            rbBoussolePlanTopRight.setUserData("top:right");
            rbBoussolePlanTopRight.setToggleGroup(grpPosBoussolePlan);
            rbBoussolePlanBottomLeft = new RadioButton("");
            rbBoussolePlanBottomLeft.setLayoutX(200);
            rbBoussolePlanBottomLeft.setLayoutY(80);
            rbBoussolePlanBottomLeft.setUserData("bottom:left");
            rbBoussolePlanBottomLeft.setToggleGroup(grpPosBoussolePlan);
            rbBoussolePlanBottomRight = new RadioButton("");
            rbBoussolePlanBottomRight.setLayoutX(230);
            rbBoussolePlanBottomRight.setLayoutY(80);
            rbBoussolePlanBottomRight.setUserData("bottom:right");
            rbBoussolePlanBottomRight.setToggleGroup(grpPosBoussolePlan);

            Label lblBoussoleDXSpinner = new Label("dX :");
            lblBoussoleDXSpinner.setLayoutX(25);
            lblBoussoleDXSpinner.setLayoutY(110);
            Label lblBoussoleDYSpinner = new Label("dY :");
            lblBoussoleDYSpinner.setLayoutX(175);
            lblBoussoleDYSpinner.setLayoutY(110);
            bdfPositXBoussole = new BigDecimalField();
            bdfPositXBoussole.setLayoutX(50);
            bdfPositXBoussole.setLayoutY(105);
            bdfPositXBoussole.setMaxValue(new BigDecimal(200));
            bdfPositXBoussole.setMinValue(new BigDecimal(0));
            bdfPositXBoussole.setMaxWidth(100);
            bdfPositYBoussole = new BigDecimalField();
            bdfPositYBoussole.setLayoutX(200);
            bdfPositYBoussole.setLayoutY(105);
            bdfPositYBoussole.setMaxValue(new BigDecimal(200));
            bdfPositYBoussole.setMinValue(new BigDecimal(0));
            bdfPositYBoussole.setMaxWidth(100);

            bdfPositXBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
                plans[planActuel].setPositionX(new_value.doubleValue());
                afficheBoussole();
            });
            bdfPositYBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
                plans[planActuel].setPositionY(new_value.doubleValue());
                afficheBoussole();
            });
            grpPosBoussolePlan.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                if (grpPosBoussolePlan.getSelectedToggle() != null) {
                    plans[planActuel].setPosition(grpPosBoussolePlan.getSelectedToggle().getUserData().toString());
                    afficheBoussole();
                }
            });
            slNordPlan.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                if (newValue != null) {
                    double direction = (double) newValue;
                    plans[planActuel].setDirectionNord(direction);
                    afficheBoussole();
                }
            });
            slNordPlan.setValue(plans[planActuel].getDirectionNord());
            rbBoussolePlanTopLeft.setSelected(plans[planActuel].getPosition().equals("top:left"));
            rbBoussolePlanTopRight.setSelected(plans[planActuel].getPosition().equals("top:right"));
            rbBoussolePlanBottomLeft.setSelected(plans[planActuel].getPosition().equals("bottom:left"));
            rbBoussolePlanBottomRight.setSelected(plans[planActuel].getPosition().equals("bottom:right"));
            bdfPositXBoussole.setNumber(new BigDecimal(plans[planActuel].getPositionX()));
            bdfPositYBoussole.setNumber(new BigDecimal(plans[planActuel].getPositionY()));
            vbOutils.getChildren().remove(apConfigPlan);

            apConfigPlan = new AnchorPane();
            apConfigPlan.getChildren().addAll(
                    lblNordPlan, slNordPlan,
                    lblPositBoussolePlan, rbBoussolePlanTopLeft, rbBoussolePlanTopRight,
                    rbBoussolePlanBottomLeft, rbBoussolePlanBottomRight,
                    lblBoussoleDXSpinner, bdfPositXBoussole, lblBoussoleDYSpinner, bdfPositYBoussole
            );
            if (nombrePanoramiques != 0) {
                CheckBox cbValide = new CheckBox(rb.getString("plan.valideCB"));
                Separator sepConfig1 = new Separator(Orientation.HORIZONTAL);
                sepConfig1.setPrefSize(380, 20);
                sepConfig1.setLayoutY(125);
                apConfigPlan.getChildren().add(sepConfig1);
                Label lblPanoAffichePlan = new Label(rb.getString("plan.panoAffichePlan"));
                lblPanoAffichePlan.setLayoutX(20);
                lblPanoAffichePlan.setLayoutY(160);
                apConfigPlan.getChildren().add(lblPanoAffichePlan);
                CheckBox[] cbPanoramiques = new CheckBox[nombrePanoramiques];
                for (int i = 0; i < nombrePanoramiques; i++) {
                    final int num = i;
                    cbPanoramiques[i] = new CheckBox();
                    final CheckBox cbF = cbPanoramiques[i];
                    cbPanoramiques[i].setId("cbPano" + i);
                    cbPanoramiques[i].setUserData(i);
                    cbPanoramiques[i].setLayoutX(30);
                    cbPanoramiques[i].setLayoutY(220 + i * 30);
                    cbPanoramiques[i].setText(panoramiquesProjet[i].getNomFichier().substring(panoramiquesProjet[i].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[i].getNomFichier().length()));
                    if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() == planActuel) {
                        cbPanoramiques[i].setSelected(true);
                    }
                    if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() != planActuel && panoramiquesProjet[i].getNumeroPlan() != -1) {
                        System.out.println("numero plan : " + panoramiquesProjet[i].getNumeroPlan() + " nb : " + nombrePlans);
                        cbPanoramiques[i].setDisable(true);
                        if (panoramiquesProjet[i].getNumeroPlan() < nombrePlans) {
                            String nomPlan = plans[panoramiquesProjet[i].getNumeroPlan()].getImagePlan();
                            cbPanoramiques[i].setText(cbPanoramiques[i].getText() + " -> (" + nomPlan + ")");
                            Tooltip tTip = new Tooltip(nomPlan);
                            cbPanoramiques[i].setTooltip(tTip);
                        }
                    }
                    apConfigPlan.getChildren().add(cbPanoramiques[i]);
                    cbPanoramiques[i].setOnMouseClicked((MouseEvent event) -> {
                        boolean new_val = cbF.selectedProperty().get();
                        panoramiquesProjet[num].setAffichePlan(new_val);
                        if (new_val) {
                            panoramiquesProjet[num].setNumeroPlan(planActuel);
                        } else {
                            panoramiquesProjet[num].setNumeroPlan(-1);
                        }
                        cbValide.setSelected(false);
                    });
                }
                cbValide.setLayoutX(30);
                cbValide.setLayoutY(190);
                cbValide.setTextFill(Color.BLUE);
                apConfigPlan.getChildren().add(cbValide);
                cbValide.setOnMouseClicked((MouseEvent event) -> {
                    boolean new_val = cbValide.selectedProperty().get();
                    for (int i = 0; i < nombrePanoramiques; i++) {
                        CheckBox cbI = (CheckBox) apConfigPlan.lookup("#cbPano" + i);
                        if (cbI.isDisabled() != true) {
                            cbI.setSelected(new_val);
                            panoramiquesProjet[i].setAffichePlan(new_val);
                            if (new_val) {
                                panoramiquesProjet[i].setNumeroPlan(planActuel);
                            } else {
                                panoramiquesProjet[i].setNumeroPlan(-1);
                            }
                        }
                    }
                });
            }
            Separator sepConfig2 = new Separator(Orientation.HORIZONTAL);
            sepConfig2.setPrefSize(380, 20);
            sepConfig2.setLayoutY(210 + nombrePanoramiques * 30);
            apConfigPlan.getChildren().add(sepConfig2);
            vbOutils.getChildren().add(apConfigPlan);
        }
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
        if (nombrePanoramiques > 0 && !dragDropPlan) {

            valideHsplan();
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
//        vbOutils.getChildren().add(affHS1);
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

            point.setOnDragDetected((MouseEvent me1) -> {
                point.setFill(Color.RED);
                point.setStroke(Color.YELLOW);
                dragDropPlan = true;
                System.out.println("d&d plan : " + dragDropPlan);

                me1.consume();

            });
            point.setOnMouseDragged((MouseEvent me1) -> {
                double X1 = me1.getSceneX();
                double Y1 = me1.getSceneY();

                double mouseXX = X1 - panePlan.getLayoutX();
                double mouseYY = Y1 - panePlan.getLayoutY() - 110;
                point.setCenterX(mouseXX);
                point.setCenterY(mouseYY);
                me1.consume();

            });
            point.setOnMouseReleased((MouseEvent me1) -> {
                String chPoint = point.getId();
                chPoint = chPoint.substring(5, chPoint.length());
                int numeroPoint = Integer.parseInt(chPoint);
                double X1 = me1.getSceneX();
                double Y1 = me1.getSceneY();

                double mouseXX = X1 - panePlan.getLayoutX();
                double mouseYY = Y1 - panePlan.getLayoutY() - 110;
                plans[planActuel].getHotspot(numeroPoint).setLongitude(mouseXX / largeur);
                plans[planActuel].getHotspot(numeroPoint).setLatitude(mouseYY / hauteur);
                point.setFill(Color.YELLOW);
                point.setStroke(Color.RED);
                me1.consume();
            });

            point.setOnMouseClicked((MouseEvent me1) -> {
                if (me1.isControlDown()) {
                    valideHsplan();
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

                    retireAffichageHotSpots();
                    numPoints--;
                    plans[planActuel].removeHotspot(numeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                    valideHsplan();
                    me1.consume();
                } else {
                    System.out.println("d&d plan : " + dragDropPlan);
                    if (!dragDropPlan) {
                        valideHsplan();
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
                }
            });
        }
        else{
            dragDropPlan=false;
        }
    }

    private void retirePlanCourant() {

        Action reponse = null;
        reponse = Dialogs.create()
                .owner(null)
                .title(rb.getString("plan.supprimerPlan"))
                .message(rb.getString("plan.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (reponse == Dialog.Actions.YES) {

            int planCour = cbChoixPlan.getSelectionModel().getSelectedIndex();
            System.out.println("Plan Courant : " + planCour);
            for (int i = planCour; i < nombrePlans - 1; i++) {
                plans[i] = plans[i + 1];
            }
            nombrePlans--;
            cbChoixPlan.getItems().clear();
            for (int i = 0; i < nombrePlans; i++) {
                cbChoixPlan.getItems().add(plans[i].getImagePlan());
            }
            cbChoixPlan.setValue(cbChoixPlan.getItems().get(0));
            for (int i = 0; i < nombrePanoramiques; i++) {
                if (panoramiquesProjet[i].getNumeroPlan() == planCour) {
                    panoramiquesProjet[i].setNumeroPlan(-1);
                    panoramiquesProjet[i].setAffichePlan(false);
                }
                if (panoramiquesProjet[i].getNumeroPlan() > planCour) {
                    panoramiquesProjet[i].setNumeroPlan(panoramiquesProjet[i].getNumeroPlan() - 1);
                }
            }
            if (nombrePlans > 0) {
                afficherPlan(0);
            } else {
                planActuel = -1;
            }
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
        repertImagePlan = repertAppli + File.separator + "theme/plan";
        rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        tabInterface = new Pane();
        hbInterface = new HBox();
        vbOutils = new VBox();
        hbInterface.setPrefWidth(width);
        hbInterface.setPrefHeight(height);
        AnchorPane apChoixPlan = new AnchorPane();

        double largeurOutils = 380;
        apPlan = new AnchorPane();
        apPlan.setPrefWidth(width - largeurOutils);
        apPlan.setPrefHeight(height);
        ivPlan = new ImageView();
        String imageBoussole = "file:" + repertImagePlan + "/aiguillePlan.png";
        imgBoussole = new Image(imageBoussole);
        ivNord = new ImageView(imgBoussole);
        panePlan = new Pane();
        panePlan.getChildren().addAll(ivPlan, ivNord);
        apPlan.setMaxWidth(width - largeurOutils - 20);
        apPlan.getChildren().add(panePlan);
        lblDragDropPlan = new Label(rb.getString("plan.dragDrop"));
        lblDragDropPlan.setMinHeight(apPlan.getPrefHeight());
        lblDragDropPlan.setMaxHeight(apPlan.getPrefHeight());
        lblDragDropPlan.setMinWidth(apPlan.getPrefWidth());
        lblDragDropPlan.setMaxWidth(apPlan.getPrefWidth());
        lblDragDropPlan.setAlignment(Pos.CENTER);
        lblDragDropPlan.setTextFill(Color.web("#c9c7c7"));
        lblDragDropPlan.setTextAlignment(TextAlignment.CENTER);
        lblDragDropPlan.setWrapText(true);
        lblDragDropPlan.setStyle("-fx-font-size:72px");
        lblDragDropPlan.setTranslateY(-100);
        apPlan.getChildren().add(lblDragDropPlan);

        spOutils = new ScrollPane();
        spOutils.setContent(vbOutils);
        spOutils.setId("spOutils");
        spOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spOutils.setPrefSize(largeurOutils, height - 100);
        spOutils.setMaxWidth(largeurOutils);
        spOutils.setMinWidth(largeurOutils);
        spOutils.setMaxHeight(height - 100);
        spOutils.setTranslateY(15);
        spOutils.setTranslateX(20);

        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        //vbOutils.setMinHeight(height);
//        vbOutils.setStyle("-fx-background-color : #ccc;");
        hbInterface.getChildren().addAll(apPlan, spOutils);

        tabInterface.getChildren().add(hbInterface);
        Label lblChoixPlan = new Label(rb.getString("plan.choixPlan"));
        lblChoixPlan.setLayoutX(20);
        lblChoixPlan.setLayoutY(10);
        cbChoixPlan = new ComboBox();
        cbChoixPlan.setLayoutX(70);
        cbChoixPlan.setLayoutY(40);
        cbChoixPlan.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (cbChoixPlan.getSelectionModel().getSelectedIndex() != -1) {
                    afficherPlan(cbChoixPlan.getSelectionModel().getSelectedIndex());
                    gestionnaireInterface.affichePlan();
                }
            }
        });
        Pane fond = new Pane();
        fond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + repertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
        fond.setLayoutX(300);
        fond.setLayoutY(40);
        Tooltip t = new Tooltip(rb.getString("plan.supprimePlan"));
        t.setStyle(tooltipStyle);
        Tooltip.install(fond, t);
        fond.getChildren().add(ivSupprPanoramique);
        fond.setOnMouseClicked(
                (MouseEvent me) -> {
                    retirePlanCourant();
                }
        );
        apChoixPlan.getChildren().addAll(lblChoixPlan, cbChoixPlan, fond);
        Separator sepChoix = new Separator(Orientation.HORIZONTAL);
        sepChoix.setPrefSize(largeurOutils + 50, 20);
        apConfigPlan = new AnchorPane();
        apConfigPlan.setId("configPlan");
        vbOutils.getChildren().addAll(
                apChoixPlan,
                sepChoix,
                apConfigPlan
        );
        panePlan.setOnMouseClicked(
                (MouseEvent me) -> {
                    gereSourisPlan(me);
                }
        );
        apPlan.setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (gestionnaireInterface.bAffichePlan) {
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        apPlan.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (gestionnaireInterface.bAffichePlan) {
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    File[] list = new File[100];
                    int i = 0;
                    for (File file1 : db.getFiles()) {
                        filePath = file1.getAbsolutePath();
                        list[i] = file1;
                        i++;
                    }
                    int nb = i;
                    for (int jj = 0; jj < nb; jj++) {
                        File fichierPlan = list[jj];
                        String extension = fichierPlan.getName().split("\\.")[1].toLowerCase();
//                            if (extension.equals("bmp") || extension.equals("jpg")) {
                        if (extension.equals("jpg") || extension.equals("bmp") || extension.equals("png")) {

                            plans[nombrePlans] = new Plan();
                            plans[nombrePlans].setImagePlan(fichierPlan.getName());
                            plans[nombrePlans].setLienPlan(fichierPlan.getAbsolutePath());
                            File repertoirePlan = new File(repertTemp + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(fichierPlan.getAbsolutePath(), repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            gestionnairePlan.ajouterPlan();
                            nombrePlans++;
                        }
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }
}
