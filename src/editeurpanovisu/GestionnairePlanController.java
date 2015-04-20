/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.getGestionnaireInterface;
import static editeurpanovisu.EditeurPanovisu.getGestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getiDecalageMac;
import static editeurpanovisu.EditeurPanovisu.getPlans;
import static editeurpanovisu.EditeurPanovisu.getStPrincipal;
import static editeurpanovisu.EditeurPanovisu.getStrRepertAppli;
import static editeurpanovisu.EditeurPanovisu.getStrRepertTemp;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.getiNombrePlans;
import static editeurpanovisu.EditeurPanovisu.setbDejaSauve;
import static editeurpanovisu.EditeurPanovisu.setiNombrePlans;
import static editeurpanovisu.EditeurPanovisu.strNumVersion;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.util.Duration;
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

    private ResourceBundle rbLocalisation;
    private boolean bDejaCharge = false;
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
    final ToggleGroup tgPosBoussolePlan = new ToggleGroup();
    final ToggleGroup tgCBPano = new ToggleGroup();
    private Label lblDragDropPlan;
    private Pane paneInterface;
    private double positionNordPlan = 0;
    private String strPositionBoussolePlan = "top:right";
    private double positXBoussolePlan = 0;
    private double positYBoussolePlan = 0;
    private String strPlanListeVignette = "";
    private int iPlanActuel;
    private String strRepertImagePlan = "";
    private ImageView ivPlan;
    private Image imgPlan;
    private String strTooltipStyle = "";
    private Pane panePlan;
    private ComboBox<String> cbChoixPlan;
    private AnchorPane apConfigPlan;
    private ImageView ivNord;
    private Image imgBoussole;
    private int iNumPoints = 0;
    private ScrollPane spOutils;
    private boolean bDragDropPlan = false;
//    private Button btnValider;

    public GestionnairePlanController() {
        this.iPlanActuel = -1;
    }

    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < iNumPoints; i++) {
            Node nodePoint = (Node) panePlan.lookup("#point" + i);
            panePlan.getChildren().remove(nodePoint);
        }
    }

    private void valideHsplan() {
        for (int i = 0; i < getPlans()[getiPlanActuel()].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + i);
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHsplan" + i);
            if (tfTexteHS != null) {
                getPlans()[getiPlanActuel()].getHotspot(i).setStrInfo(tfTexteHS.getText());
            }
            if (cbx != null) {
                getPlans()[getiPlanActuel()].getHotspot(i).setStrFichierXML(cbx.getValue() + ".xml");
            }
        }
    }

    public String getTemplate() {
        String strContenuFichier = "";
        for (int i = 0; i < getiNombrePlans(); i++) {
            strContenuFichier += "[Plan=>"
                    + "lienImage:" + getPlans()[i].getStrLienPlan()
                    + ";image:" + getPlans()[i].getStrImagePlan()
                    + ";nb:" + getPlans()[i].getNombreHotspots()
                    + ";titre:" + getPlans()[i].getTitrePlan()
                    + ";directionNord:" + getPlans()[i].getDirectionNord()
                    + ";position:" + getPlans()[i].getStrPosition()
                    + ";positionX:" + getPlans()[i].getPositionX()
                    + ";positionY:" + getPlans()[i].getPositionY()
                    + "]\n";
            for (int ij = 0; ij < getPlans()[i].getNombreHotspots(); ij++) {
                HotSpot HS = getPlans()[i].getHotspot(ij);
                strContenuFichier += "   [hotspotPlan==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getStrFichierImage()
                        + ";xml:" + HS.getStrFichierXML()
                        + ";info:" + HS.getStrInfo()
                        + ";numeroPano:" + HS.getNumeroPano()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
        }
        return strContenuFichier;
    }

    /**
     *
     * @param i
     * @param posX
     * @param posY
     */
    private void afficheHS(int i, double posX, double posY) {
        double largeur = imgPlan.getWidth();
        double hauteur = imgPlan.getHeight();
        double X = posX * largeur;
        double Y = posY * hauteur;
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.YELLOW);
        circPoint.setStroke(Color.RED);
        circPoint.setId("point" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePlan.getChildren().add(circPoint);

        Tooltip tltpPoint = new Tooltip("point n° " + (i + 1));
        tltpPoint.setStyle(strTooltipStyle);
        Tooltip.install(circPoint, tltpPoint);
        circPoint.setOnDragDetected((me1) -> {
            circPoint.setFill(Color.RED);
            circPoint.setStroke(Color.YELLOW);
            bDragDropPlan = true;
            //System.out.println("d&d plan : " + dragDropPlan);
            me1.consume();

        });
        circPoint.setOnMouseDragged((me1) -> {
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();

            double mouseXX = X1 - panePlan.getLayoutX();
            double mouseYY = Y1 - panePlan.getLayoutY() - 110 - getiDecalageMac();
            circPoint.setCenterX(mouseXX);
            circPoint.setCenterY(mouseYY);
            me1.consume();

        });
        circPoint.setOnMouseReleased((me1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(5, strPoint.length());
            int iNumeroPoint = Integer.parseInt(strPoint);
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();
            double mouseXX = X1 - panePlan.getLayoutX();
            double mouseYY = Y1 - panePlan.getLayoutY() - 110 - getiDecalageMac();
            getPlans()[getiPlanActuel()].getHotspot(iNumeroPoint).setLongitude(mouseXX / largeur);
            getPlans()[getiPlanActuel()].getHotspot(iNumeroPoint).setLatitude(mouseYY / hauteur);
            circPoint.setFill(Color.YELLOW);
            circPoint.setStroke(Color.RED);
            me1.consume();
        });

        circPoint.setOnMouseClicked((me1) -> {
            double mouseX = me1.getSceneX();
            double mouseY = me1.getSceneY() - panePlan.getLayoutY() - 115;
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(5, strPoint.length());
            int iNumeroPoint = Integer.parseInt(strPoint);
            Node nodePoint;
            nodePoint = (Node) panePlan.lookup("#point" + strPoint);

            if (me1.isControlDown()) {
                valideHsplan();
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                panePlan.getChildren().remove(nodePoint);

                for (int io = iNumeroPoint + 1; io < iNumPoints; io++) {
                    nodePoint = (Node) panePlan.lookup("#point" + Integer.toString(io));
                    nodePoint.setId("point" + Integer.toString(io - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                iNumPoints--;
                getPlans()[getiPlanActuel()].removeHotspot(iNumeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                valideHsplan();
                me1.consume();
            } else {
                //System.out.println("d&d plan : " + dragDropPlan);
                if (!bDragDropPlan) {
                    valideHsplan();
                    if (getiNombrePanoramiques() > 1) {
                        int iLargeurVignettes = 4;
                        if (getiNombrePanoramiques() < 4) {
                            iLargeurVignettes = getiNombrePanoramiques();
                        }
                        AnchorPane listePanoVig = afficherListePanosVignettes(iNumeroPoint);
                        if (mouseX + iLargeurVignettes * 130 > panePlan.getWidth()) {
                            listePanoVig.setLayoutX(panePlan.getWidth() - iLargeurVignettes * 130);
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
        for (int i = 0; i < getPlans()[getiPlanActuel()].getNombreHotspots(); i++) {
            double longitude = getPlans()[getiPlanActuel()].getHotspot(i).getLongitude();
            double latitude = getPlans()[getiPlanActuel()].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     * @param strLstPano
     * @param iNumPano
     * @return
     */
    public Pane affichageHS(String strLstPano, int iNumPano) {

        Pane paneHotSpots = new Pane();
        VBox vbAffichageHotspots = new VBox();
        paneHotSpots.getChildren().add(vbAffichageHotspots);
        Label lblPoint;
        Label lblSep = new Label(" ");
        Label lblSep1 = new Label(" ");
        for (int io = 0; io < getPlans()[iNumPano].getNombreHotspots(); io++) {
            AnchorPane apPanneauHS = new AnchorPane();
            apPanneauHS.setId("HS" + io);
            int iNum1 = io;
            Timeline timBouge = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
                Circle c1 = (Circle) panePlan.lookup("#point" + iNum1);
                if (c1 != null) {
                    if (c1.getFill() == Color.RED) {
                        c1.setFill(Color.YELLOW);
                        c1.setStroke(Color.RED);
                    } else {
                        c1.setFill(Color.RED);
                        c1.setStroke(Color.YELLOW);
                    }
                }
            }));
            timBouge.setCycleCount(Timeline.INDEFINITE);
            timBouge.pause();
            apPanneauHS.setOnMouseEntered((e) -> {
                timBouge.play();
            });
            apPanneauHS.setOnMouseExited((e) -> {
                timBouge.pause();
                Circle c1 = (Circle) panePlan.lookup("#point" + iNum1);
                if (c1 != null) {
                    c1.setFill(Color.YELLOW);
                    c1.setStroke(Color.RED);
                }
            });

            lblPoint = new Label("Point n°" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.getStyleClass().add("titreOutil");
            lblPoint.setLayoutX(20);
            lblPoint.setLayoutY(10);
            Separator sep1 = new Separator(Orientation.HORIZONTAL);
            sep1.setPrefWidth(370);
            sep1.setLayoutY(40);
            //pannneauHS.setTranslateX(5);
            apPanneauHS.getChildren().addAll(lblPoint, sep1);
            if (strLstPano != null) {
                Label lblLien = new Label("Panoramique de destination");
                lblLien.setLayoutX(20);
                lblLien.setLayoutY(60);
                ComboBox cbDestPano = new ComboBox();
                String[] strListe = strLstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(strListe));
                cbDestPano.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String t, String t1) {
                        if (bDejaCharge) {
                            valideHsplan();
                            retireAffichageHotSpots();
                            ajouteAffichageHotspots();
                        }
                    }
                });

                cbDestPano.setId("cbplan" + io);
                cbDestPano.setLayoutX(60);
                cbDestPano.setLayoutY(90);
                String strF1XML = getPlans()[iNumPano].getHotspot(io).getStrFichierXML();
                if (strF1XML != null) {
                    cbDestPano.setValue(strF1XML.split("\\.")[0]);
                }
                if (strF1XML != null) {
                    int iNum11 = -1;
                    for (int ii = 0; ii < getiNombrePanoramiques(); ii++) {
                        String strChaine = getPanoramiquesProjet()[ii].getStrNomFichier();
                        strChaine = strChaine.substring(strChaine.lastIndexOf("\\") + 1, strChaine.length());
                        strChaine = strChaine.split("\\.")[0];
                        if (strChaine.equals(strF1XML.split("\\.")[0])) {
                            iNum11 = ii;
                        }
                    }
                    if (iNum11 != -1) {
                        int iNumPan = iNum11;
                        ImageView ivAfficheVignettePano = new ImageView(getPanoramiquesProjet()[iNum11].getImgVignettePanoramique());
                        ivAfficheVignettePano.setPreserveRatio(true);
                        ivAfficheVignettePano.setFitWidth(300);
                        ivAfficheVignettePano.setLayoutY(10);
                        AnchorPane apVisuVignettePano = new AnchorPane(ivAfficheVignettePano);
                        apVisuVignettePano.setPrefHeight(170);
                        apVisuVignettePano.setLayoutX(25);
                        apVisuVignettePano.setLayoutY(120);
                        apPanneauHS.getChildren().addAll(lblLien, cbDestPano, apVisuVignettePano, lblSep);
                    } else {
                        apPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
                    }
                } else {
                    apPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
                }

            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            lblTexteHS.setLayoutX(20);
            lblTexteHS.setLayoutY(290);

            TextField tfTexteHS = new TextField();
            tfTexteHS.setLayoutX(60);
            tfTexteHS.setLayoutY(320);
            tfTexteHS.textProperty().addListener((ov, oldValue, newValue) -> {
                valideHsplan();
            });

            if (getPlans()[iNumPano].getHotspot(io).getStrInfo() != null) {
                tfTexteHS.setText(getPlans()[iNumPano].getHotspot(io).getStrInfo());
            }
            tfTexteHS.setId("txtHsplan" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            apPanneauHS.getChildren().addAll(lblTexteHS, tfTexteHS, lblSep1);
            vbAffichageHotspots.getChildren().addAll(apPanneauHS, lblSep);
        }
        bDejaCharge = true;
        return paneHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane paneLable = affichageHS(listePano(), getiPlanActuel());
        paneLable.setId("labelConfigPlan");
        vbOutils.getChildren().add(paneLable);
        iNumPoints = getPlans()[getiPlanActuel()].getNombreHotspots();
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        bDejaCharge = false;
        Pane paneLabel = (Pane) vbOutils.lookup("#labelConfigPlan");
        vbOutils.getChildren().remove(paneLabel);
    }

    /**
     *
     */
    public void ajouterPlan() {
        cbChoixPlan.getItems().add(getiNombrePlans(), getPlans()[getiNombrePlans()].getStrImagePlan());
        afficherPlan(getiNombrePlans());
        cbChoixPlan.getSelectionModel().select(getiNombrePlans());
        getLblDragDropPlan().setVisible(false);
    }

    /**
     *
     * @param numeroPlan
     */
    public void afficherPlan(int numeroPlan) {
        setiPlanActuel(numeroPlan);
        String strTxtImage = getStrRepertTemp() + "/images/" + getPlans()[numeroPlan].getStrImagePlan();
        imgPlan = new Image("file:" + strTxtImage);
        if (imgPlan.getWidth() > 1000) {
            imgPlan = new Image("file:" + strTxtImage, 1000, -1, true, true);
        }
        if (imgPlan.getHeight() > hbInterface.getPrefHeight() - 100) {
            imgPlan = new Image("file:" + strTxtImage, -1, hbInterface.getPrefHeight() - 100, true, true);
        }
        getPlans()[getiPlanActuel()].setLargeurPlan(imgPlan.getWidth());
        getPlans()[getiPlanActuel()].setHauteurPlan(imgPlan.getHeight());
        ivPlan.setImage(imgPlan);
        panePlan.setLayoutX((apPlan.getPrefWidth() - imgPlan.getWidth()) / 2);
        panePlan.setPrefSize(imgPlan.getWidth(), imgPlan.getHeight());
        panePlan.setMinSize(imgPlan.getWidth(), imgPlan.getHeight());
        panePlan.setMaxSize(imgPlan.getWidth(), imgPlan.getHeight());
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
        if (!imgPlan.isError()) {
            String strPosX = getPlans()[getiPlanActuel()].getStrPosition().split(":")[1];
            String strPosY = getPlans()[getiPlanActuel()].getStrPosition().split(":")[0];
            double positionX = 0;
            double positionY = 0;
            switch (strPosX) {
                case "left":
                    positionX = ivPlan.getLayoutX() + getPlans()[getiPlanActuel()].getPositionX();
                    break;
                case "right":
                    positionX = ivPlan.getLayoutX() + imgPlan.getWidth() - imgBoussole.getWidth() - getPlans()[getiPlanActuel()].getPositionX();
                    break;
            }
            switch (strPosY) {
                case "top":
                    positionY = ivPlan.getLayoutY() + getPlans()[getiPlanActuel()].getPositionY();
                    break;
                case "bottom":
                    positionY = ivPlan.getLayoutY() + imgPlan.getHeight() - imgBoussole.getHeight() - getPlans()[getiPlanActuel()].getPositionY();
                    break;
            }
            ivNord.setLayoutX(positionX);
            ivNord.setLayoutY(positionY);
            ivNord.setRotate(getPlans()[getiPlanActuel()].getDirectionNord());
        }
    }

    /**
     *
     * @param numHS
     * @return
     */
    private AnchorPane afficherListePanosVignettes(int numHS) {

        AnchorPane apListePano = new AnchorPane();
        apListePano.setOpacity(1);
        Pane paneFond = new Pane();
        paneFond.setStyle("-fx-background-color : #bbb;");
        paneFond.setPrefWidth(540);
        paneFond.setPrefHeight(((getiNombrePanoramiques() - 2) / 4 + 1) * 65 + 10);
        paneFond.setMinWidth(540);
        paneFond.setMinHeight(70);
        apListePano.getChildren().add(paneFond);
        apListePano.setStyle("-fx-backgroung-color : #bbb;");
        int ij = 0;
        ImageView[] ivPano;
        ivPano = new ImageView[getiNombrePanoramiques()];
        double xPos;
        double yPos;
        int iRow = 0;
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            int iNumeroPano = i;
            ivPano[ij] = new ImageView(getPanoramiquesProjet()[i].getImgVignettePanoramique());
            ivPano[ij].setFitWidth(120);
            ivPano[ij].setFitHeight(60);
            ivPano[ij].setSmooth(true);
            String strNomPano = getPanoramiquesProjet()[i].getStrNomFichier();
            int iCol = ij % 4;
            iRow = ij / 4;
            xPos = iCol * 130 + 25;
            yPos = iRow * 65 + 5;
            ivPano[ij].setLayoutX(xPos);
            ivPano[ij].setLayoutY(yPos);
            ivPano[ij].setCursor(Cursor.HAND);
            ivPano[ij].setStyle("-fx-background-color : #ccc;");
            Tooltip tltpNomPano = new Tooltip(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
            tltpNomPano.setStyle(strTooltipStyle);
            Tooltip.install(ivPano[ij], tltpNomPano);
            ivPano[ij].setOnMouseClicked((me) -> {
                setStrPlanListeVignette(strNomPano);
                if (getPanoramiquesProjet()[iNumeroPano].getStrTitrePanoramique() != null) {
                    String strTexteHS = getPanoramiquesProjet()[iNumeroPano].getStrTitrePanoramique();
                    TextField tfHS = (TextField) vbOutils.lookup("#txtHsplan" + numHS);
                    tfHS.setText(strTexteHS);
                }
                getPlans()[getiPlanActuel()].getHotspot(numHS).setNumeroPano(iNumeroPano);
                ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + numHS);
                cbx.setValue(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
                apListePano.setVisible(false);
                valideHsplan();
                me.consume();
                retireAffichageHotSpots();
                ajouteAffichageHotspots();

            });
            apListePano.getChildren().add(ivPano[ij]);
            ij++;

        }
        int iTaille = (iRow + 1) * 65 + 5;
        apListePano.setPrefWidth(540);
        apListePano.setPrefHeight(iTaille);
        apListePano.setMinWidth(540);
        apListePano.setMinHeight(iTaille);
        ImageView ivClose = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/ferme.png", 20, 20, true, true));
        ivClose.setLayoutX(2);
        ivClose.setLayoutY(5);
        ivClose.setCursor(Cursor.HAND);
        apListePano.getChildren().add(ivClose);
        ivClose.setOnMouseClicked((me) -> {
            setStrPlanListeVignette("");
            apListePano.setVisible(false);
            me.consume();
        });
        apListePano.setTranslateZ(2);
        return apListePano;
    }

    /**
     *
     */
    public void afficheConfigPlan() {
        if (getiPlanActuel() != -1) {
            Label lblNordPlan = new Label(rbLocalisation.getString("plan.positionNordPlan"));
            lblNordPlan.setLayoutX(10);
            lblNordPlan.setLayoutY(10);
            slNordPlan = new Slider(0, 360.0, 0);
            slNordPlan.setLayoutX(200);
            slNordPlan.setLayoutY(10);
            Separator sepPlan = new Separator(Orientation.HORIZONTAL);
            sepPlan.setPrefHeight(10);
            sepPlan.setPrefWidth(350);
            sepPlan.setLayoutY(30);
            Label lblPositBoussolePlan = new Label(rbLocalisation.getString("plan.positionBoussolePlan"));
            lblPositBoussolePlan.setLayoutX(10);
            lblPositBoussolePlan.setLayoutY(50);
            rbBoussolePlanTopLeft = new RadioButton("");
            rbBoussolePlanTopLeft.setLayoutX(200);
            rbBoussolePlanTopLeft.setLayoutY(50);
            rbBoussolePlanTopLeft.setUserData("top:left");
            rbBoussolePlanTopLeft.setToggleGroup(tgPosBoussolePlan);
            rbBoussolePlanTopRight = new RadioButton("");
            rbBoussolePlanTopRight.setLayoutX(230);
            rbBoussolePlanTopRight.setLayoutY(50);
            rbBoussolePlanTopRight.setUserData("top:right");
            rbBoussolePlanTopRight.setToggleGroup(tgPosBoussolePlan);
            rbBoussolePlanBottomLeft = new RadioButton("");
            rbBoussolePlanBottomLeft.setLayoutX(200);
            rbBoussolePlanBottomLeft.setLayoutY(80);
            rbBoussolePlanBottomLeft.setUserData("bottom:left");
            rbBoussolePlanBottomLeft.setToggleGroup(tgPosBoussolePlan);
            rbBoussolePlanBottomRight = new RadioButton("");
            rbBoussolePlanBottomRight.setLayoutX(230);
            rbBoussolePlanBottomRight.setLayoutY(80);
            rbBoussolePlanBottomRight.setUserData("bottom:right");
            rbBoussolePlanBottomRight.setToggleGroup(tgPosBoussolePlan);

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

            bdfPositXBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
                getPlans()[getiPlanActuel()].setPositionX(new_value.doubleValue());
                afficheBoussole();
            });
            bdfPositYBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
                getPlans()[getiPlanActuel()].setPositionY(new_value.doubleValue());
                afficheBoussole();
            });
            tgPosBoussolePlan.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (tgPosBoussolePlan.getSelectedToggle() != null) {
                    getPlans()[getiPlanActuel()].setStrPosition(tgPosBoussolePlan.getSelectedToggle().getUserData().toString());
                    afficheBoussole();
                }
            });
            slNordPlan.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != null) {
                    double direction = (double) newValue;
                    getPlans()[getiPlanActuel()].setDirectionNord(direction);
                    afficheBoussole();
                }
            });
            slNordPlan.setValue(getPlans()[getiPlanActuel()].getDirectionNord());
            rbBoussolePlanTopLeft.setSelected(getPlans()[getiPlanActuel()].getStrPosition().equals("top:left"));
            rbBoussolePlanTopRight.setSelected(getPlans()[getiPlanActuel()].getStrPosition().equals("top:right"));
            rbBoussolePlanBottomLeft.setSelected(getPlans()[getiPlanActuel()].getStrPosition().equals("bottom:left"));
            rbBoussolePlanBottomRight.setSelected(getPlans()[getiPlanActuel()].getStrPosition().equals("bottom:right"));
            bdfPositXBoussole.setNumber(new BigDecimal(getPlans()[getiPlanActuel()].getPositionX()));
            bdfPositYBoussole.setNumber(new BigDecimal(getPlans()[getiPlanActuel()].getPositionY()));
            vbOutils.getChildren().remove(apConfigPlan);

            apConfigPlan = new AnchorPane();
            apConfigPlan.getChildren().addAll(
                    lblNordPlan, slNordPlan,
                    lblPositBoussolePlan, rbBoussolePlanTopLeft, rbBoussolePlanTopRight,
                    rbBoussolePlanBottomLeft, rbBoussolePlanBottomRight,
                    lblBoussoleDXSpinner, bdfPositXBoussole, lblBoussoleDYSpinner, bdfPositYBoussole
            );
            if (getiNombrePanoramiques() != 0) {
                CheckBox cbValide = new CheckBox(rbLocalisation.getString("plan.valideCB"));
                Separator sepConfig1 = new Separator(Orientation.HORIZONTAL);
                sepConfig1.setPrefSize(380, 20);
                sepConfig1.setLayoutY(125);
                apConfigPlan.getChildren().add(sepConfig1);
                Label lblPanoAffichePlan = new Label(rbLocalisation.getString("plan.panoAffichePlan"));
                lblPanoAffichePlan.setLayoutX(20);
                lblPanoAffichePlan.setLayoutY(160);
                apConfigPlan.getChildren().add(lblPanoAffichePlan);
                CheckBox[] cbPanoramiques = new CheckBox[getiNombrePanoramiques()];
                for (int i = 0; i < getiNombrePanoramiques(); i++) {
                    final int iNum = i;
                    cbPanoramiques[i] = new CheckBox();
                    final CheckBox cbF = cbPanoramiques[i];
                    cbPanoramiques[i].setId("cbPano" + i);
                    cbPanoramiques[i].setUserData(i);
                    cbPanoramiques[i].setLayoutX(30);
                    cbPanoramiques[i].setLayoutY(220 + i * 30);
                    cbPanoramiques[i].setText(getPanoramiquesProjet()[i].getStrNomFichier().substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length()));
                    if (getPanoramiquesProjet()[i].isAffichePlan() && getPanoramiquesProjet()[i].getNumeroPlan() == getiPlanActuel()) {
                        cbPanoramiques[i].setSelected(true);
                    }
                    if (getPanoramiquesProjet()[i].isAffichePlan() && getPanoramiquesProjet()[i].getNumeroPlan() != getiPlanActuel() && getPanoramiquesProjet()[i].getNumeroPlan() != -1) {
                        //System.out.println("numero plan : " + panoramiquesProjet[i].getNumeroPlan() + " nb : " + nombrePlans);
                        cbPanoramiques[i].setDisable(true);
                        if (getPanoramiquesProjet()[i].getNumeroPlan() < getiNombrePlans()) {
                            String nomPlan = getPlans()[getPanoramiquesProjet()[i].getNumeroPlan()].getStrImagePlan();
                            cbPanoramiques[i].setText(cbPanoramiques[i].getText() + " -> (" + nomPlan + ")");
                            Tooltip tTip = new Tooltip(nomPlan);
                            cbPanoramiques[i].setTooltip(tTip);
                        }
                    }
                    apConfigPlan.getChildren().add(cbPanoramiques[i]);
                    cbPanoramiques[i].setOnMouseClicked((event) -> {
                        boolean bSelectPlan = cbF.selectedProperty().get();
                        getPanoramiquesProjet()[iNum].setAffichePlan(bSelectPlan);
                        if (bSelectPlan) {
                            getPanoramiquesProjet()[iNum].setNumeroPlan(getiPlanActuel());
                        } else {
                            getPanoramiquesProjet()[iNum].setNumeroPlan(-1);
                        }
                        cbValide.setSelected(false);
                    });
                }
                cbValide.setLayoutX(30);
                cbValide.setLayoutY(190);
                cbValide.setTextFill(Color.BLUE);
                apConfigPlan.getChildren().add(cbValide);
                cbValide.setOnMouseClicked((event) -> {
                    boolean bSelectPlan = cbValide.selectedProperty().get();
                    for (int i = 0; i < getiNombrePanoramiques(); i++) {
                        CheckBox cbI = (CheckBox) apConfigPlan.lookup("#cbPano" + i);
                        if (cbI.isDisabled() != true) {
                            cbI.setSelected(bSelectPlan);
                            getPanoramiquesProjet()[i].setAffichePlan(bSelectPlan);
                            if (bSelectPlan) {
                                getPanoramiquesProjet()[i].setNumeroPlan(getiPlanActuel());
                            } else {
                                getPanoramiquesProjet()[i].setNumeroPlan(-1);
                            }
                        }
                    }
                });
            }
            Separator sepConfig2 = new Separator(Orientation.HORIZONTAL);
            sepConfig2.setPrefSize(380, 20);
            sepConfig2.setLayoutY(210 + getiNombrePanoramiques() * 30);
            apConfigPlan.getChildren().add(sepConfig2);
            vbOutils.getChildren().add(apConfigPlan);
        }
    }

    /**
     *
     * @return
     */
    private String listePano() {
        String strListe = "";
        if (getiNombrePanoramiques() == 0) {
            return null;
        } else {
            for (int i = 0; i < getiNombrePanoramiques(); i++) {
                String strFichierPano = getPanoramiquesProjet()[i].getStrNomFichier();
                String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                String[] strNPano = strNomPano.split("\\.");
                strListe += strNPano[0];
                if (i < getiNombrePanoramiques() - 1) {
                    strListe += ";";
                }
            }
            return strListe;
        }
    }

    /**
     *
     * @param X
     * @param Y
     */
    private void planMouseClic(double X, double Y) {
        if (getiNombrePanoramiques() > 0 && !bDragDropPlan) {

            valideHsplan();
            setbDejaSauve(false);
            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            double mouseX = X - panePlan.getLayoutX();
            double mouseY = Y - panePlan.getLayoutY() - 110 - getiDecalageMac();
            double posX, posY;
            double largeur = imgPlan.getWidth();
            double hauteur = imgPlan.getHeight();
            posX = mouseX;
            posY = mouseY;
            Circle circPoint = new Circle(mouseX, mouseY, 5);
            circPoint.setFill(Color.YELLOW);
            circPoint.setStroke(Color.RED);
            circPoint.setId("point" + iNumPoints);
            circPoint.setCursor(Cursor.DEFAULT);
            panePlan.getChildren().add(circPoint);
            Tooltip tltpPoint = new Tooltip("point n°" + (iNumPoints + 1));
            tltpPoint.setStyle(strTooltipStyle);
            Tooltip.install(circPoint, tltpPoint);
            HotSpot HS = new HotSpot();
            HS.setLongitude(posX / largeur);
            HS.setLatitude(posY / hauteur);
            getPlans()[getiPlanActuel()].addHotspot(HS);
            retireAffichageHotSpots();
            ajouteAffichageHotspots();
            AnchorPane apListePanoVig = afficherListePanosVignettes(getPlans()[getiPlanActuel()].getNombreHotspots() - 1);
            int iLargeurVignettes = 4;
            if (getiNombrePanoramiques() < 4) {
                iLargeurVignettes = getiNombrePanoramiques();
            }
            if (mouseX + iLargeurVignettes * 130 > panePlan.getWidth()) {
                apListePanoVig.setLayoutX(panePlan.getWidth() - iLargeurVignettes * 130);
            } else {
                apListePanoVig.setLayoutX(mouseX);
            }
            apListePanoVig.setLayoutY(mouseY);
            panePlan.getChildren().add(apListePanoVig);

            circPoint.setOnDragDetected((me1) -> {
                circPoint.setFill(Color.RED);
                circPoint.setStroke(Color.YELLOW);
                bDragDropPlan = true;
                //System.out.println("d&d plan : " + dragDropPlan);

                me1.consume();

            });
            circPoint.setOnMouseDragged((me1) -> {
                double X1 = me1.getSceneX();
                double Y1 = me1.getSceneY();

                double mouseXX = X1 - panePlan.getLayoutX();
                double mouseYY = Y1 - panePlan.getLayoutY() - 110 - getiDecalageMac();
                circPoint.setCenterX(mouseXX);
                circPoint.setCenterY(mouseYY);
                me1.consume();

            });
            circPoint.setOnMouseReleased((me1) -> {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(5, strPoint.length());
                int iNumeroPoint = Integer.parseInt(strPoint);
                double X1 = me1.getSceneX();
                double Y1 = me1.getSceneY();

                double mouseXX = X1 - panePlan.getLayoutX();
                double mouseYY = Y1 - panePlan.getLayoutY() - 110 - getiDecalageMac();
                getPlans()[getiPlanActuel()].getHotspot(iNumeroPoint).setLongitude(mouseXX / largeur);
                getPlans()[getiPlanActuel()].getHotspot(iNumeroPoint).setLatitude(mouseYY / hauteur);
                circPoint.setFill(Color.YELLOW);
                circPoint.setStroke(Color.RED);
                me1.consume();
            });

            circPoint.setOnMouseClicked((me1) -> {
                if (me1.isControlDown()) {
                    valideHsplan();
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(5, strPoint.length());
                    int iNumeroPoint = Integer.parseInt(strPoint);
                    Node nodePoint;
                    nodePoint = (Node) panePlan.lookup("#point" + strPoint);
                    panePlan.getChildren().remove(nodePoint);

                    for (int io = iNumeroPoint + 1; io < iNumPoints; io++) {
                        nodePoint = (Node) panePlan.lookup("#point" + Integer.toString(io));
                        nodePoint.setId("point" + Integer.toString(io - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */

                    retireAffichageHotSpots();
                    iNumPoints--;
                    getPlans()[getiPlanActuel()].removeHotspot(iNumeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                    valideHsplan();
                    me1.consume();
                } else {
                    //System.out.println("d&d plan : " + dragDropPlan);
                    if (!bDragDropPlan) {
                        valideHsplan();
                        String strPoint = circPoint.getId();
                        strPoint = strPoint.substring(5, strPoint.length());
                        int iNumeroPoint = Integer.parseInt(strPoint);
                        if (getiNombrePanoramiques() > 1) {
                            AnchorPane apListePanoVig1 = afficherListePanosVignettes(iNumeroPoint);
                            int iLargeurVignettes1 = 4;
                            if (getiNombrePanoramiques() < 4) {
                                iLargeurVignettes1 = getiNombrePanoramiques();
                            }
                            if (mouseX + iLargeurVignettes1 * 130 > panePlan.getWidth()) {
                                apListePanoVig1.setLayoutX(panePlan.getWidth() - iLargeurVignettes1 * 130);
                            } else {
                                apListePanoVig1.setLayoutX(mouseX);
                            }
                            apListePanoVig1.setLayoutY(mouseY);
                            panePlan.getChildren().add(apListePanoVig1);
                        }
                        me1.consume();

                    }
                }
            });
        } else {
            bDragDropPlan = false;
        }
    }

    private void retirePlanCourant() {

        Action actReponse = null;
        actReponse = Dialogs.create()
                .owner(null)
                .title(rbLocalisation.getString("plan.supprimerPlan"))
                .message(rbLocalisation.getString("plan.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (actReponse == Dialog.Actions.YES) {

            int iPlanCour = cbChoixPlan.getSelectionModel().getSelectedIndex();
            //System.out.println("Plan Courant : " + planCour);
            for (int i = iPlanCour; i < getiNombrePlans() - 1; i++) {
                getPlans()[i] = getPlans()[i + 1];
            }
            setiNombrePlans(getiNombrePlans() - 1);
            cbChoixPlan.getItems().clear();
            for (int i = 0; i < getiNombrePlans(); i++) {
                cbChoixPlan.getItems().add(getPlans()[i].getStrImagePlan());
            }
            cbChoixPlan.setValue(cbChoixPlan.getItems().get(0));
            for (int i = 0; i < getiNombrePanoramiques(); i++) {
                if (getPanoramiquesProjet()[i].getNumeroPlan() == iPlanCour) {
                    getPanoramiquesProjet()[i].setNumeroPlan(-1);
                    getPanoramiquesProjet()[i].setAffichePlan(false);
                }
                if (getPanoramiquesProjet()[i].getNumeroPlan() > iPlanCour) {
                    getPanoramiquesProjet()[i].setNumeroPlan(getPanoramiquesProjet()[i].getNumeroPlan() - 1);
                }
            }
            if (getiNombrePlans() > 0) {
                afficherPlan(0);
            } else {
                setiPlanActuel(-1);
            }
        }
    }

    private void gereSourisPlan(MouseEvent me) {
        if (me.getButton() == MouseButton.PRIMARY) {
            if (!(me.isControlDown()) && getiPlanActuel() != -1) {
                planMouseClic(me.getSceneX(), me.getSceneY());
            }
        }
    }

    /**
     *
     * @param iLargeur
     * @param iHauteur
     */
    public void creeInterface(int iLargeur, int iHauteur) {
        setStrRepertImagePlan(getStrRepertAppli() + File.separator + "theme/plan");
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
        setPaneInterface(new Pane());
        hbInterface = new HBox();
        vbOutils = new VBox();
//        AnchorPane apPanovisu = new AnchorPane();
//        apPanovisu.setPrefHeight(138);
//        apPanovisu.setMinHeight(138);
//        apPanovisu.setMaxHeight(138);
//        apPanovisu.setStyle("-fx-background-color : derive(-fx-base,-5%);");
//        ImageView ivPanoVisu = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/panovisu.png", 83, 83, true, true));
//        ivPanoVisu.setLayoutX(25);
//        ivPanoVisu.setLayoutY(10);
//        Label lblPanoVisu = new Label("panoVisu Vers. : " + strNumVersion);
//        lblPanoVisu.setStyle("-fx-font-weight : bold;-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 1.2em;");
//        lblPanoVisu.setLayoutX(118);
//        lblPanoVisu.setLayoutY(25);
//        Label lblPanoVisu2 = new Label("(c) Laurent LANG (2014)");
//        lblPanoVisu2.setLayoutX(118);
//        lblPanoVisu2.setLayoutY(55);
//        lblPanoVisu2.setStyle("-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 0.8em;");
//        Separator sepTitre = new Separator(Orientation.HORIZONTAL);
//        sepTitre.setPrefWidth(380);
//        sepTitre.setLayoutY(103);
//        apPanovisu.getChildren().addAll(ivPanoVisu, lblPanoVisu, lblPanoVisu2, sepTitre);
        hbInterface.setPrefWidth(iLargeur);
        hbInterface.setPrefHeight(iHauteur);
        AnchorPane apChoixPlan = new AnchorPane();

        double largeurOutils = 380;
        apPlan = new AnchorPane();
        apPlan.setPrefWidth(iLargeur - largeurOutils - 20);
        apPlan.setPrefHeight(iHauteur);
        ivPlan = new ImageView();
        String strImageBoussole = "file:" + getStrRepertImagePlan() + "/aiguillePlan.png";
        imgBoussole = new Image(strImageBoussole);
        ivNord = new ImageView(imgBoussole);
        panePlan = new Pane();
        panePlan.getChildren().addAll(ivPlan, ivNord);
        apPlan.setMaxWidth(iLargeur - largeurOutils - 50);
        apPlan.getChildren().add(panePlan);
        setLblDragDropPlan(new Label(rbLocalisation.getString("plan.dragDrop")));
        getLblDragDropPlan().setMinHeight(apPlan.getPrefHeight());
        getLblDragDropPlan().setMaxHeight(apPlan.getPrefHeight());
        getLblDragDropPlan().setMinWidth(apPlan.getPrefWidth());
        getLblDragDropPlan().setMaxWidth(apPlan.getPrefWidth());
        getLblDragDropPlan().setAlignment(Pos.CENTER);
        getLblDragDropPlan().setTextFill(Color.web("#c9c7c7"));
        getLblDragDropPlan().setTextAlignment(TextAlignment.CENTER);
        getLblDragDropPlan().setWrapText(true);
        getLblDragDropPlan().setStyle("-fx-font-size:72px");
        getLblDragDropPlan().setTranslateY(-100);
        apPlan.getChildren().add(getLblDragDropPlan());
        AnchorPane apOutils = new AnchorPane();
        apOutils.setPrefWidth(largeurOutils);
        apOutils.setMaxWidth(largeurOutils);
        apOutils.setTranslateY(3);
        apOutils.setTranslateX(20);
        spOutils = new ScrollPane(vbOutils);
        apOutils.getChildren().addAll(spOutils);
        spOutils.setId("spOutils");
        spOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        spOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spOutils.setMinHeight(iHauteur - 52 + getiDecalageMac());
        spOutils.setMaxHeight(iHauteur - 52 + getiDecalageMac());
        spOutils.setPrefWidth(largeurOutils);
        spOutils.setMinWidth(largeurOutils);
        spOutils.setMaxWidth(largeurOutils);
//        spOutils.setFitToWidth(true);
//        spOutils.setFitToHeight(true);
        spOutils.setLayoutY(-getiDecalageMac());

        vbOutils.setPrefWidth(largeurOutils - 30);
        vbOutils.setMaxWidth(largeurOutils - 30);
        //vbOutils.setMinHeight(height);
//        vbOutils.setStyle("-fx-background-color : #ccc;");
        hbInterface.getChildren().addAll(apPlan, apOutils);

        getPaneInterface().getChildren().add(hbInterface);
        Label lblChoixPlan = new Label(rbLocalisation.getString("plan.choixPlan"));
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
                    getGestionnaireInterface().affichePlan();
                }
            }
        });
        Pane paneFond = new Pane();
        paneFond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/suppr.png", 30, 30, true, true));
        paneFond.setLayoutX(300);
        paneFond.setLayoutY(40);
        Tooltip tltpSupprimePlan = new Tooltip(rbLocalisation.getString("plan.supprimePlan"));
        tltpSupprimePlan.setStyle(strTooltipStyle);
        Tooltip.install(paneFond, tltpSupprimePlan);
        paneFond.getChildren().add(ivSupprPanoramique);
        paneFond.setOnMouseClicked(
                (me) -> {
                    retirePlanCourant();
                }
        );
        apChoixPlan.getChildren().addAll(lblChoixPlan, cbChoixPlan, paneFond);
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
                (me) -> {
                    gereSourisPlan(me);
                }
        );
        apPlan.setOnDragOver((event) -> {
            Dragboard db = event.getDragboard();
            if (getGestionnaireInterface().isbAffichePlan()) {
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        apPlan.setOnDragDropped((event) -> {
            Dragboard dbImagePlan = event.getDragboard();
            boolean success = false;
            if (getGestionnaireInterface().isbAffichePlan()) {
                if (dbImagePlan.hasFiles()) {
                    success = true;
                    String filePath = null;
                    File[] fileListe = new File[100];
                    int i = 0;
                    for (File filePlan : dbImagePlan.getFiles()) {
                        filePath = filePlan.getAbsolutePath();
                        fileListe[i] = filePlan;
                        i++;
                    }
                    int nb = i;
                    for (int ijj = 0; ijj < nb; ijj++) {
                        File fichierPlan = fileListe[ijj];
                        String strExtension = fichierPlan.getName().split("\\.")[1].toLowerCase();
//                            if (extension.equals("bmp") || extension.equals("jpg")) {
                        if (strExtension.equals("jpg") || strExtension.equals("bmp") || strExtension.equals("png")) {

                            getPlans()[getiNombrePlans()] = new Plan();
                            getPlans()[getiNombrePlans()].setStrImagePlan(fichierPlan.getName());
                            getPlans()[getiNombrePlans()].setStrLienPlan(fichierPlan.getAbsolutePath());
                            File repertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(fichierPlan.getAbsolutePath(), repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            getGestionnairePlan().ajouterPlan();
                            setiNombrePlans(getiNombrePlans() + 1);
                        }
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }

    /**
     * @return the lblDragDropPlan
     */
    public Label getLblDragDropPlan() {
        return lblDragDropPlan;
    }

    /**
     * @param lblDragDropPlan the lblDragDropPlan to set
     */
    public void setLblDragDropPlan(Label lblDragDropPlan) {
        this.lblDragDropPlan = lblDragDropPlan;
    }

    /**
     * @return the paneInterface
     */
    public Pane getPaneInterface() {
        return paneInterface;
    }

    /**
     * @param paneInterface the paneInterface to set
     */
    public void setPaneInterface(Pane paneInterface) {
        this.paneInterface = paneInterface;
    }

    /**
     * @return the positionNordPlan
     */
    public double getPositionNordPlan() {
        return positionNordPlan;
    }

    /**
     * @param positionNordPlan the positionNordPlan to set
     */
    public void setPositionNordPlan(double positionNordPlan) {
        this.positionNordPlan = positionNordPlan;
    }

    /**
     * @return the strPositionBoussolePlan
     */
    public String getStrPositionBoussolePlan() {
        return strPositionBoussolePlan;
    }

    /**
     * @param strPositionBoussolePlan the strPositionBoussolePlan to set
     */
    public void setStrPositionBoussolePlan(String strPositionBoussolePlan) {
        this.strPositionBoussolePlan = strPositionBoussolePlan;
    }

    /**
     * @return the positXBoussolePlan
     */
    public double getPositXBoussolePlan() {
        return positXBoussolePlan;
    }

    /**
     * @param positXBoussolePlan the positXBoussolePlan to set
     */
    public void setPositXBoussolePlan(double positXBoussolePlan) {
        this.positXBoussolePlan = positXBoussolePlan;
    }

    /**
     * @return the positYBoussolePlan
     */
    public double getPositYBoussolePlan() {
        return positYBoussolePlan;
    }

    /**
     * @param positYBoussolePlan the positYBoussolePlan to set
     */
    public void setPositYBoussolePlan(double positYBoussolePlan) {
        this.positYBoussolePlan = positYBoussolePlan;
    }

    /**
     * @return the strPlanListeVignette
     */
    public String getStrPlanListeVignette() {
        return strPlanListeVignette;
    }

    /**
     * @param strPlanListeVignette the strPlanListeVignette to set
     */
    public void setStrPlanListeVignette(String strPlanListeVignette) {
        this.strPlanListeVignette = strPlanListeVignette;
    }

    /**
     * @return the iPlanActuel
     */
    public int getiPlanActuel() {
        return iPlanActuel;
    }

    /**
     * @param iPlanActuel the iPlanActuel to set
     */
    public void setiPlanActuel(int iPlanActuel) {
        this.iPlanActuel = iPlanActuel;
    }

    /**
     * @return the strRepertImagePlan
     */
    public String getStrRepertImagePlan() {
        return strRepertImagePlan;
    }

    /**
     * @param strRepertImagePlan the strRepertImagePlan to set
     */
    public void setStrRepertImagePlan(String strRepertImagePlan) {
        this.strRepertImagePlan = strRepertImagePlan;
    }
}
