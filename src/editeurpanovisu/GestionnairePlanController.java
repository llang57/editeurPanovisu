/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.nombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.repertAppli;
import static editeurpanovisu.EditeurPanovisu.tooltipStyle;
import java.io.File;
import java.math.BigDecimal;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 *
 * @author llang
 */
public class GestionnairePlanController {

    private static final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});
    private static final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    private static ResourceBundle rb;
    public Pane tabInterface;
    private static HBox HBInterface;
    private static AnchorPane APPlan;
    private static VBox VBOutils;
    private static Slider SLnordPlan;
    private static BigDecimalField BDFPositXBoussole;
    private static BigDecimalField BDFPositYBoussole;
    private static RadioButton RBBoussolePlanTopLeft;
    private static RadioButton RBBoussolePlanTopRight;
    private static RadioButton RBBoussolePlanBottomLeft;
    private static RadioButton RBBoussolePlanBottomRight;
    final ToggleGroup grpPosBoussolePlan = new ToggleGroup();
    public static double positionNordPlan = 0;
    public static String positionBoussolePlan = "top:right";
    public static double positXBoussolePlan = 0;
    public static double positYBoussolePlan = 0;
    static public String panoListeVignette;

    public void ajouterPlan(int numeroPlan) {

    }

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
        IVPano = new ImageView[nombrePanoramiques - 1];
        double xPos;
        double yPos;

        for (int i = 0; i < nombrePanoramiques; i++) {
            int numeroPano = i;
            IVPano[j] = new ImageView(panoramiquesProjet[i].getVignettePanoramique());
            IVPano[j].setFitWidth(120);
            IVPano[j].setFitHeight(60);
            IVPano[j].setSmooth(true);
            String nomPano = panoramiquesProjet[i].getNomFichier();
            int col = j % 4;
            int row = j / 4;
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
                panoListeVignette = nomPano;
                if (panoramiquesProjet[numeroPano].getTitrePanoramique() != null) {
                    String texteHS = panoramiquesProjet[numeroPano].getTitrePanoramique();
                }
                APlistePano.setVisible(false);
                me.consume();
            });
            APlistePano.getChildren().add(IVPano[j]);
            j++;

        }
        int taille = ((j + 1) / 4 + 1) * 65 + 5;
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
            panoListeVignette = "";
            APlistePano.setVisible(false);
            me.consume();
        });
        return APlistePano;
    }

    private void afficheConfigPlan() {
        Label lblNordPlan = new Label(rb.getString("interface.positionNordPlan"));
        lblNordPlan.setLayoutX(10);
        lblNordPlan.setLayoutY(190);
        SLnordPlan = new Slider(0, 360.0, 0);
        SLnordPlan.setLayoutX(200);
        SLnordPlan.setLayoutY(190);
        Separator sepPlan = new Separator(Orientation.HORIZONTAL);
        sepPlan.setPrefHeight(10);
        sepPlan.setPrefWidth(350);
        sepPlan.setLayoutY(220);
        Label lblPositBoussolePlan = new Label(rb.getString("interface.positionBoussolePlan"));
        lblPositBoussolePlan.setLayoutX(10);
        lblPositBoussolePlan.setLayoutY(230);
        RBBoussolePlanTopLeft = new RadioButton("");
        RBBoussolePlanTopLeft.setLayoutX(200);
        RBBoussolePlanTopLeft.setLayoutY(230);
        RBBoussolePlanTopLeft.setUserData("top:left");
        RBBoussolePlanTopLeft.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanTopRight = new RadioButton("");
        RBBoussolePlanTopRight.setLayoutX(230);
        RBBoussolePlanTopRight.setLayoutY(230);
        RBBoussolePlanTopRight.setUserData("top:right");
        RBBoussolePlanTopRight.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanBottomLeft = new RadioButton("");
        RBBoussolePlanBottomLeft.setLayoutX(200);
        RBBoussolePlanBottomLeft.setLayoutY(260);
        RBBoussolePlanBottomLeft.setUserData("bottom:left");
        RBBoussolePlanBottomLeft.setToggleGroup(grpPosBoussolePlan);
        RBBoussolePlanBottomRight = new RadioButton("");
        RBBoussolePlanBottomRight.setLayoutX(230);
        RBBoussolePlanBottomRight.setLayoutY(260);
        RBBoussolePlanBottomRight.setUserData("bottom:right");
        RBBoussolePlanBottomRight.setToggleGroup(grpPosBoussolePlan);
        Label lblBoussoleDXSpinner = new Label("dX :");
        lblBoussoleDXSpinner.setLayoutX(25);
        lblBoussoleDXSpinner.setLayoutY(290);
        Label lblBoussoleDYSpinner = new Label("dY :");
        lblBoussoleDYSpinner.setLayoutX(175);
        lblBoussoleDYSpinner.setLayoutY(290);
        BDFPositXBoussole = new BigDecimalField(new BigDecimal(positXBoussolePlan));
        BDFPositXBoussole.setLayoutX(50);
        BDFPositXBoussole.setLayoutY(285);
        BDFPositXBoussole.setMaxValue(new BigDecimal(200));
        BDFPositXBoussole.setMinValue(new BigDecimal(0));
        BDFPositXBoussole.setMaxWidth(100);
        BDFPositYBoussole = new BigDecimalField(new BigDecimal(positYBoussolePlan));
        BDFPositYBoussole.setLayoutX(200);
        BDFPositYBoussole.setLayoutY(285);
        BDFPositYBoussole.setMaxValue(new BigDecimal(200));
        BDFPositYBoussole.setMinValue(new BigDecimal(0));
        BDFPositYBoussole.setMaxWidth(100);
        BDFPositXBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            positXBoussolePlan = new_value.doubleValue();
        });
        BDFPositYBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            positYBoussolePlan = new_value.doubleValue();
        });
        grpPosBoussolePlan.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosBoussolePlan.getSelectedToggle() != null) {
                positionBoussolePlan = grpPosBoussolePlan.getSelectedToggle().getUserData().toString();
            }
        });

    }

    /**
     *
     * @param width
     * @param height
     */
    public void creeInterface(int width, int height) {
        rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        tabInterface = new Pane();
        HBInterface = new HBox();
        VBOutils = new VBox();
        HBInterface.setPrefWidth(width);
        HBInterface.setPrefHeight(height);

        double largeurOutils = 380;
        APPlan = new AnchorPane();
        APPlan.setPrefWidth(width - largeurOutils);
        APPlan.setPrefHeight(height);

        ScrollPane SPOutils = new ScrollPane(VBOutils);
        SPOutils.setId("spOutils");
        SPOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        SPOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPOutils.setMaxHeight(height - 100);
        SPOutils.setFitToWidth(true);
        SPOutils.setFitToHeight(true);
        SPOutils.setPrefWidth(largeurOutils);
        SPOutils.setMaxWidth(largeurOutils);
        SPOutils.setTranslateY(15);
        VBOutils.setPrefWidth(largeurOutils - 20);
        VBOutils.setMaxWidth(largeurOutils - 20);
        //VBOutils.setMinHeight(height);
//        VBOutils.setStyle("-fx-background-color : #ccc;");
        HBInterface.getChildren().addAll(APPlan, SPOutils);

        tabInterface.getChildren().add(HBInterface);

//        VBOutils.getChildren().addAll(
//                APCoulTheme, APTIT, APBB, APPL, APHS, APBOUSS, APMASQ, APRS, APVIG
//        );
    }

}
