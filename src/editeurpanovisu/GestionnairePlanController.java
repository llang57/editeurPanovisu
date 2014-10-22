/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.bDejaSauve;
import static editeurpanovisu.EditeurPanovisu.iNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.gestionnaireInterface;
import static editeurpanovisu.EditeurPanovisu.gestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.iNombrePlans;
import static editeurpanovisu.EditeurPanovisu.plans;
import static editeurpanovisu.EditeurPanovisu.strRepertAppli;
import static editeurpanovisu.EditeurPanovisu.strRepertTemp;
import static editeurpanovisu.EditeurPanovisu.stPrincipal;
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
    private String strPlanListeVignette;
    private int iPlanActuel;
    private String strRepertImagePlan;
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
        for (int i = 0; i < plans[getiPlanActuel()].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + i);
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHsplan" + i);
            if (tfTexteHS != null) {
                plans[getiPlanActuel()].getHotspot(i).setStrInfo(tfTexteHS.getText());
            }
            if (cbx != null) {
                plans[getiPlanActuel()].getHotspot(i).setStrFichierXML(cbx.getValue() + ".xml");
            }
        }
    }

    public String getTemplate() {
        String strContenuFichier = "";
        for (int i = 0; i < iNombrePlans; i++) {
            strContenuFichier += "[Plan=>"
                    + "lienImage:" + plans[i].getStrLienPlan()
                    + ";image:" + plans[i].getStrImagePlan()
                    + ";nb:" + plans[i].getNombreHotspots()
                    + ";titre:" + plans[i].getTitrePlan()
                    + ";directionNord:" + plans[i].getDirectionNord()
                    + ";position:" + plans[i].getStrPosition()
                    + ";positionX:" + plans[i].getPositionX()
                    + ";positionY:" + plans[i].getPositionY()
                    + "]\n";
            for (int ij = 0; ij < plans[i].getNombreHotspots(); ij++) {
                HotSpot HS = plans[i].getHotspot(ij);
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
            double mouseYY = Y1 - panePlan.getLayoutY() - 110;
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
            double mouseYY = Y1 - panePlan.getLayoutY() - 110;
            plans[getiPlanActuel()].getHotspot(iNumeroPoint).setLongitude(mouseXX / largeur);
            plans[getiPlanActuel()].getHotspot(iNumeroPoint).setLatitude(mouseYY / hauteur);
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
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
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
                plans[getiPlanActuel()].removeHotspot(iNumeroPoint);
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
                    if (iNombrePanoramiques > 1) {
                        int iLargeurVignettes = 4;
                        if (iNombrePanoramiques < 4) {
                            iLargeurVignettes = iNombrePanoramiques;
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
        for (int i = 0; i < plans[getiPlanActuel()].getNombreHotspots(); i++) {
            double longitude = plans[getiPlanActuel()].getHotspot(i).getLongitude();
            double latitude = plans[getiPlanActuel()].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     * @param lstPano
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
        for (int io = 0; io < plans[iNumPano].getNombreHotspots(); io++) {
            AnchorPane apPanneauHS = new AnchorPane();

            apPanneauHS.setId("HS" + io);
            lblPoint = new Label("Point n°" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setStyle("-fx-background-color : #333;");
            lblPoint.setTextFill(Color.WHITE);
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
                        valideHsplan();
                    }
                });

                cbDestPano.setId("cbplan" + io);
                cbDestPano.setLayoutX(60);
                cbDestPano.setLayoutY(90);
                String strF1XML = plans[iNumPano].getHotspot(io).getStrFichierXML();
                if (strF1XML != null) {
                    cbDestPano.setValue(strF1XML.split("\\.")[0]);
                }
                apPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            lblTexteHS.setLayoutX(20);
            lblTexteHS.setLayoutY(120);

            TextField tfTexteHS = new TextField();
            tfTexteHS.setLayoutX(60);
            tfTexteHS.setLayoutY(150);
            tfTexteHS.textProperty().addListener(( ov,  oldValue,newValue) -> {
                valideHsplan();
            });

            if (plans[iNumPano].getHotspot(io).getStrInfo() != null) {
                tfTexteHS.setText(plans[iNumPano].getHotspot(io).getStrInfo());
            }
            tfTexteHS.setId("txtHsplan" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            apPanneauHS.getChildren().addAll(lblTexteHS, tfTexteHS, lblSep1);
            vbAffichageHotspots.getChildren().addAll(apPanneauHS, lblSep);
        }

        return paneHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane paneLable = affichageHS(listePano(), getiPlanActuel());
        paneLable.setId("labelConfigPlan");
        vbOutils.getChildren().add(paneLable);
        iNumPoints = plans[getiPlanActuel()].getNombreHotspots();
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        Pane paneLabel = (Pane) vbOutils.lookup("#labelConfigPlan");
        vbOutils.getChildren().remove(paneLabel);
    }

    /**
     *
     */
    public void ajouterPlan() {
        cbChoixPlan.getItems().add(iNombrePlans, plans[iNombrePlans].getStrImagePlan());
        afficherPlan(iNombrePlans);
        cbChoixPlan.getSelectionModel().select(iNombrePlans);
        getLblDragDropPlan().setVisible(false);
    }

    /**
     *
     * @param numeroPlan
     */
    public void afficherPlan(int numeroPlan) {
        setiPlanActuel(numeroPlan);
        String strTxtImage = strRepertTemp + "/images/" + plans[numeroPlan].getStrImagePlan();
        imgPlan = new Image("file:" + strTxtImage);
        if (imgPlan.getWidth() > 1000) {
            imgPlan = new Image("file:" + strTxtImage, 1000, -1, true, true);
        }
        if (imgPlan.getHeight() > hbInterface.getPrefHeight() - 100) {
            imgPlan = new Image("file:" + strTxtImage, -1, hbInterface.getPrefHeight() - 100, true, true);
        }
        plans[getiPlanActuel()].setLargeurPlan(imgPlan.getWidth());
        plans[getiPlanActuel()].setHauteurPlan(imgPlan.getHeight());
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
            String strPosX = plans[getiPlanActuel()].getStrPosition().split(":")[1];
            String strPosY = plans[getiPlanActuel()].getStrPosition().split(":")[0];
            double positionX = 0;
            double positionY = 0;
            switch (strPosX) {
                case "left":
                    positionX = ivPlan.getLayoutX() + plans[getiPlanActuel()].getPositionX();
                    break;
                case "right":
                    positionX = ivPlan.getLayoutX() + imgPlan.getWidth() - imgBoussole.getWidth() - plans[getiPlanActuel()].getPositionX();
                    break;
            }
            switch (strPosY) {
                case "top":
                    positionY = ivPlan.getLayoutY() + plans[getiPlanActuel()].getPositionY();
                    break;
                case "bottom":
                    positionY = ivPlan.getLayoutY() + imgPlan.getHeight() - imgBoussole.getHeight() - plans[getiPlanActuel()].getPositionY();
                    break;
            }
            ivNord.setLayoutX(positionX);
            ivNord.setLayoutY(positionY);
            ivNord.setRotate(plans[getiPlanActuel()].getDirectionNord());
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
        paneFond.setPrefHeight(((iNombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        paneFond.setMinWidth(540);
        paneFond.setMinHeight(70);
        apListePano.getChildren().add(paneFond);
        apListePano.setStyle("-fx-backgroung-color : #bbb;");
        int ij = 0;
        ImageView[] ivPano;
        ivPano = new ImageView[iNombrePanoramiques];
        double xPos;
        double yPos;
        int iRow = 0;
        for (int i = 0; i < iNombrePanoramiques; i++) {
            int iNumeroPano = i;
            ivPano[ij] = new ImageView(panoramiquesProjet[i].getImgVignettePanoramique());
            ivPano[ij].setFitWidth(120);
            ivPano[ij].setFitHeight(60);
            ivPano[ij].setSmooth(true);
            String strNomPano = panoramiquesProjet[i].getStrNomFichier();
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
                if (panoramiquesProjet[iNumeroPano].getStrTitrePanoramique() != null) {
                    String strTexteHS = panoramiquesProjet[iNumeroPano].getStrTitrePanoramique();
                    TextField tfHS = (TextField) vbOutils.lookup("#txtHsplan" + numHS);
                    tfHS.setText(strTexteHS);
                }
                plans[getiPlanActuel()].getHotspot(numHS).setNumeroPano(iNumeroPano);
                ComboBox cbx = (ComboBox) vbOutils.lookup("#cbplan" + numHS);
                cbx.setValue(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
                apListePano.setVisible(false);
                valideHsplan();
                me.consume();
            });
            apListePano.getChildren().add(ivPano[ij]);
            ij++;

        }
        int iTaille = (iRow + 1) * 65 + 5;
        apListePano.setPrefWidth(540);
        apListePano.setPrefHeight(iTaille);
        apListePano.setMinWidth(540);
        apListePano.setMinHeight(iTaille);
        ImageView ivClose = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
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

            bdfPositXBoussole.numberProperty().addListener(( ov,  old_value,new_value) -> {
                plans[getiPlanActuel()].setPositionX(new_value.doubleValue());
                afficheBoussole();
            });
            bdfPositYBoussole.numberProperty().addListener(( ov,  old_value,new_value) -> {
                plans[getiPlanActuel()].setPositionY(new_value.doubleValue());
                afficheBoussole();
            });
            tgPosBoussolePlan.selectedToggleProperty().addListener((  ov, old_toggle, new_toggle) -> {
                if (tgPosBoussolePlan.getSelectedToggle() != null) {
                    plans[getiPlanActuel()].setStrPosition(tgPosBoussolePlan.getSelectedToggle().getUserData().toString());
                    afficheBoussole();
                }
            });
            slNordPlan.valueProperty().addListener((observableValue,oldValue,newValue) -> {
                if (newValue != null) {
                    double direction = (double) newValue;
                    plans[getiPlanActuel()].setDirectionNord(direction);
                    afficheBoussole();
                }
            });
            slNordPlan.setValue(plans[getiPlanActuel()].getDirectionNord());
            rbBoussolePlanTopLeft.setSelected(plans[getiPlanActuel()].getStrPosition().equals("top:left"));
            rbBoussolePlanTopRight.setSelected(plans[getiPlanActuel()].getStrPosition().equals("top:right"));
            rbBoussolePlanBottomLeft.setSelected(plans[getiPlanActuel()].getStrPosition().equals("bottom:left"));
            rbBoussolePlanBottomRight.setSelected(plans[getiPlanActuel()].getStrPosition().equals("bottom:right"));
            bdfPositXBoussole.setNumber(new BigDecimal(plans[getiPlanActuel()].getPositionX()));
            bdfPositYBoussole.setNumber(new BigDecimal(plans[getiPlanActuel()].getPositionY()));
            vbOutils.getChildren().remove(apConfigPlan);

            apConfigPlan = new AnchorPane();
            apConfigPlan.getChildren().addAll(
                    lblNordPlan, slNordPlan,
                    lblPositBoussolePlan, rbBoussolePlanTopLeft, rbBoussolePlanTopRight,
                    rbBoussolePlanBottomLeft, rbBoussolePlanBottomRight,
                    lblBoussoleDXSpinner, bdfPositXBoussole, lblBoussoleDYSpinner, bdfPositYBoussole
            );
            if (iNombrePanoramiques != 0) {
                CheckBox cbValide = new CheckBox(rbLocalisation.getString("plan.valideCB"));
                Separator sepConfig1 = new Separator(Orientation.HORIZONTAL);
                sepConfig1.setPrefSize(380, 20);
                sepConfig1.setLayoutY(125);
                apConfigPlan.getChildren().add(sepConfig1);
                Label lblPanoAffichePlan = new Label(rbLocalisation.getString("plan.panoAffichePlan"));
                lblPanoAffichePlan.setLayoutX(20);
                lblPanoAffichePlan.setLayoutY(160);
                apConfigPlan.getChildren().add(lblPanoAffichePlan);
                CheckBox[] cbPanoramiques = new CheckBox[iNombrePanoramiques];
                for (int i = 0; i < iNombrePanoramiques; i++) {
                    final int iNum = i;
                    cbPanoramiques[i] = new CheckBox();
                    final CheckBox cbF = cbPanoramiques[i];
                    cbPanoramiques[i].setId("cbPano" + i);
                    cbPanoramiques[i].setUserData(i);
                    cbPanoramiques[i].setLayoutX(30);
                    cbPanoramiques[i].setLayoutY(220 + i * 30);
                    cbPanoramiques[i].setText(panoramiquesProjet[i].getStrNomFichier().substring(panoramiquesProjet[i].getStrNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[i].getStrNomFichier().length()));
                    if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() == getiPlanActuel()) {
                        cbPanoramiques[i].setSelected(true);
                    }
                    if (panoramiquesProjet[i].isAffichePlan() && panoramiquesProjet[i].getNumeroPlan() != getiPlanActuel() && panoramiquesProjet[i].getNumeroPlan() != -1) {
                        //System.out.println("numero plan : " + panoramiquesProjet[i].getNumeroPlan() + " nb : " + nombrePlans);
                        cbPanoramiques[i].setDisable(true);
                        if (panoramiquesProjet[i].getNumeroPlan() < iNombrePlans) {
                            String nomPlan = plans[panoramiquesProjet[i].getNumeroPlan()].getStrImagePlan();
                            cbPanoramiques[i].setText(cbPanoramiques[i].getText() + " -> (" + nomPlan + ")");
                            Tooltip tTip = new Tooltip(nomPlan);
                            cbPanoramiques[i].setTooltip(tTip);
                        }
                    }
                    apConfigPlan.getChildren().add(cbPanoramiques[i]);
                    cbPanoramiques[i].setOnMouseClicked((event) -> {
                        boolean bSelectPlan = cbF.selectedProperty().get();
                        panoramiquesProjet[iNum].setAffichePlan(bSelectPlan);
                        if (bSelectPlan) {
                            panoramiquesProjet[iNum].setNumeroPlan(getiPlanActuel());
                        } else {
                            panoramiquesProjet[iNum].setNumeroPlan(-1);
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
                    for (int i = 0; i < iNombrePanoramiques; i++) {
                        CheckBox cbI = (CheckBox) apConfigPlan.lookup("#cbPano" + i);
                        if (cbI.isDisabled() != true) {
                            cbI.setSelected(bSelectPlan);
                            panoramiquesProjet[i].setAffichePlan(bSelectPlan);
                            if (bSelectPlan) {
                                panoramiquesProjet[i].setNumeroPlan(getiPlanActuel());
                            } else {
                                panoramiquesProjet[i].setNumeroPlan(-1);
                            }
                        }
                    }
                });
            }
            Separator sepConfig2 = new Separator(Orientation.HORIZONTAL);
            sepConfig2.setPrefSize(380, 20);
            sepConfig2.setLayoutY(210 + iNombrePanoramiques * 30);
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
        if (iNombrePanoramiques == 0) {
            return null;
        } else {
            for (int i = 0; i < iNombrePanoramiques; i++) {
                String strFichierPano = panoramiquesProjet[i].getStrNomFichier();
                String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                String[] strNPano = strNomPano.split("\\.");
                strListe += strNPano[0];
                if (i < iNombrePanoramiques - 1) {
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
        if (iNombrePanoramiques > 0 && !bDragDropPlan) {

            valideHsplan();
            bDejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X - panePlan.getLayoutX();
            double mouseY = Y - panePlan.getLayoutY() - 110;
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
            plans[getiPlanActuel()].addHotspot(HS);
            retireAffichageHotSpots();
            ajouteAffichageHotspots();
            AnchorPane apListePanoVig = afficherListePanosVignettes(plans[getiPlanActuel()].getNombreHotspots() - 1);
            int iLargeurVignettes = 4;
            if (iNombrePanoramiques < 4) {
                iLargeurVignettes = iNombrePanoramiques;
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
                double mouseYY = Y1 - panePlan.getLayoutY() - 110;
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
                double mouseYY = Y1 - panePlan.getLayoutY() - 110;
                plans[getiPlanActuel()].getHotspot(iNumeroPoint).setLongitude(mouseXX / largeur);
                plans[getiPlanActuel()].getHotspot(iNumeroPoint).setLatitude(mouseYY / hauteur);
                circPoint.setFill(Color.YELLOW);
                circPoint.setStroke(Color.RED);
                me1.consume();
            });

            circPoint.setOnMouseClicked((me1) -> {
                if (me1.isControlDown()) {
                    valideHsplan();
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
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
                    plans[getiPlanActuel()].removeHotspot(iNumeroPoint);
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
                        if (iNombrePanoramiques > 1) {
                            AnchorPane apListePanoVig1 = afficherListePanosVignettes(iNumeroPoint);
                            int iLargeurVignettes1 = 4;
                            if (iNombrePanoramiques < 4) {
                                iLargeurVignettes1 = iNombrePanoramiques;
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
        }
        else{
            bDragDropPlan=false;
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
            for (int i = iPlanCour; i < iNombrePlans - 1; i++) {
                plans[i] = plans[i + 1];
            }
            iNombrePlans--;
            cbChoixPlan.getItems().clear();
            for (int i = 0; i < iNombrePlans; i++) {
                cbChoixPlan.getItems().add(plans[i].getStrImagePlan());
            }
            cbChoixPlan.setValue(cbChoixPlan.getItems().get(0));
            for (int i = 0; i < iNombrePanoramiques; i++) {
                if (panoramiquesProjet[i].getNumeroPlan() == iPlanCour) {
                    panoramiquesProjet[i].setNumeroPlan(-1);
                    panoramiquesProjet[i].setAffichePlan(false);
                }
                if (panoramiquesProjet[i].getNumeroPlan() > iPlanCour) {
                    panoramiquesProjet[i].setNumeroPlan(panoramiquesProjet[i].getNumeroPlan() - 1);
                }
            }
            if (iNombrePlans > 0) {
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
     * @paramiLargeurwidth
     * @param iHauteur
     */
    public void creeInterface(int iLargeur, int iHauteur) {
        setStrRepertImagePlan(strRepertAppli + File.separator + "theme/plan");
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        setPaneInterface(new Pane());
        hbInterface = new HBox();
        vbOutils = new VBox();
        hbInterface.setPrefWidth(iLargeur);
        hbInterface.setPrefHeight(iHauteur);
        AnchorPane apChoixPlan = new AnchorPane();

        double largeurOutils = 380;
        apPlan = new AnchorPane();
        apPlan.setPrefWidth(iLargeur - largeurOutils);
        apPlan.setPrefHeight(iHauteur);
        ivPlan = new ImageView();
        String strImageBoussole = "file:" + getStrRepertImagePlan() + "/aiguillePlan.png";
        imgBoussole = new Image(strImageBoussole);
        ivNord = new ImageView(imgBoussole);
        panePlan = new Pane();
        panePlan.getChildren().addAll(ivPlan, ivNord);
        apPlan.setMaxWidth(iLargeur - largeurOutils - 20);
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

        spOutils = new ScrollPane();
        spOutils.setContent(vbOutils);
        spOutils.setId("spOutils");
        spOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spOutils.setPrefSize(largeurOutils, iHauteur - 100);
        spOutils.setMaxWidth(largeurOutils);
        spOutils.setMinWidth(largeurOutils);
        spOutils.setMaxHeight(iHauteur - 100);
        spOutils.setTranslateY(15);
        spOutils.setTranslateX(20);

        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        //vbOutils.setMinHeight(height);
//        vbOutils.setStyle("-fx-background-color : #ccc;");
        hbInterface.getChildren().addAll(apPlan, spOutils);

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
                    gestionnaireInterface.affichePlan();
                }
            }
        });
        Pane paneFond = new Pane();
        paneFond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
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
            if (gestionnaireInterface.isbAffichePlan()) {
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
            if (gestionnaireInterface.isbAffichePlan()) {
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

                            plans[iNombrePlans] = new Plan();
                            plans[iNombrePlans].setStrImagePlan(fichierPlan.getName());
                            plans[iNombrePlans].setStrLienPlan(fichierPlan.getAbsolutePath());
                            File repertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(fichierPlan.getAbsolutePath(), repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            gestionnairePlan.ajouterPlan();
                            iNombrePlans++;
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
