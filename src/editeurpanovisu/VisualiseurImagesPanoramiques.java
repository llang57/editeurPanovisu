/**
 *
 * @author llang
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import editeurpanovisu.BigDecimalField;
import editeurpanovisu.gpu.ImageResizeGPU;
import editeurpanovisu.gpu.InterpolationMethod;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llang
 */
public final class VisualiseurImagesPanoramiques {

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

    private double anchorX, anchorY,
            latitude, longitude, fov = 70, positNord = 32,
            choixLongitude = 0, choixLatitude = 0, choixFov = 0,
            largeurImage = 340, hauteurImage = 200, positX = 100, positY = 100,
            bougeX, bougeY,
            ancLongitude = 0, ancLatitude = 0, ancFov = 70,
            minFov = 12, maxFov = 70;
    private final double rapportDegToRad = Math.PI / 180.d;
    private PerspectiveCamera camera1;
    private SubScene sscPanorama;
    private Label lblNord, lblSud, lblEst, lblOuest, lblNordEst, lblSudOuest, lblSudEst, lblNordOuest;
    private AnchorPane apNord, apPanorama;
    private boolean bMouvement = false;
    private final BigDecimalField bdfLong = new BigDecimalField(new BigDecimal(0)),
            bdfLat = new BigDecimalField(new BigDecimal(0)),
            bdfFOV = new BigDecimalField(new BigDecimal(70));
    private String nomFichierPanoramique = "";
    private Button btnChoixNord, btnChoixVue;
    private final Sphere spPanorama = new Sphere(200, 50);
    private final Group root = new Group(spPanorama);
    private final PhongMaterial phmPanorama = new PhongMaterial();
    private ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());


    public VisualiseurImagesPanoramiques(String nomFichierPanoramique, double positX, double positY, double largeur, double hauteur) {
        this.positX = positX;
        this.positY = positY;
        this.largeurImage = largeur;
        this.hauteurImage = hauteur;
        this.setNomFichierPanoramique(nomFichierPanoramique);
    }

    public static Image imgTransformationImage(Image imgRect) {
        return imgTransformationImage(imgRect, 2);
    }

    public static Image imgTransformationImage(Image imgRect, int iRapport) {
        int iLargeur = (int) imgRect.getWidth() / iRapport;
        int iHauteur = iLargeur / 2 / iRapport;
        
        try {
            // Tentative GPU Bicubic pour affichage visite (plus rapide)
            Image resized = ImageResizeGPU.resizeAuto(
                imgRect,
                iLargeur,
                iHauteur,
                InterpolationMethod.BICUBIC
            );
            Logger.getLogger(VisualiseurImagesPanoramiques.class.getName()).log(Level.FINE, 
                String.format("imgTransformationImage GPU: %dx%d → %dx%d (Bicubic)",
                    (int) imgRect.getWidth(), (int) imgRect.getHeight(),
                    iLargeur, iHauteur)
            );
            return resized;
            
        } catch (Exception e) {
            // Fallback CPU : boucle pixel par pixel (transformation Mercator)
            Logger.getLogger(VisualiseurImagesPanoramiques.class.getName()).log(Level.WARNING, 
                "Erreur GPU imgTransformationImage, fallback CPU: " + e.getMessage()
            );
            return imgTransformationImageCPU(imgRect, iRapport, iLargeur, iHauteur);
        }
    }
    
    /**
     * Version CPU de la transformation Mercator (fallback)
     */
    private static Image imgTransformationImageCPU(Image imgRect, int iRapport, int iLargeur, int iHauteur) {
        WritableImage imgMercator = new WritableImage(iLargeur, iHauteur);
        PixelReader prRect = imgRect.getPixelReader();
        PixelWriter pwMercator = imgMercator.getPixelWriter();
        for (int i = 0; i < iLargeur; i++) {
            for (int j = 0; j < iHauteur; j++) {
                //double phi = 2 * Math.atan(Math.exp(2.d * Math.PI / largeur * (largeur / 4.d - (double) j))) - Math.PI / 2.d;
                double phi = Math.asin(2.d * (iHauteur / 2.d - j) / iHauteur);
                int y2 = (int) (iHauteur * iRapport * (0.5d - phi / Math.PI));
                if (y2 >= iHauteur * iRapport) {
                    y2 = iHauteur * iRapport - 1;
                }
                Color clPixel = prRect.getColor(i * iRapport, y2 * iRapport);
                pwMercator.setColor(i, j, clPixel);
            }
        }
        return imgMercator;
    }

    private PerspectiveCamera addCamera(SubScene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
        scene.setCamera(perspectiveCamera);
        perspectiveCamera.setFarClip(10000);
        perspectiveCamera.setNearClip(0.1);
        perspectiveCamera.setFieldOfView(getFov());
        perspectiveCamera.setVerticalFieldOfView(false);
        return perspectiveCamera;
    }

    /**
     * 
     * @param angleDeg
     * @return 
     */
    private double degToRad(double angleDeg) {
        return angleDeg * rapportDegToRad;
    }

    public void affiche() {
        double hfov = getFov() * hauteurImage / largeurImage;
        apNord.getChildren().clear();
        setLongitude((getLongitude()) % 360);
        setLongitude(getLongitude() < 0 ? getLongitude() + 360 : getLongitude());
        setLongitude(getLongitude() > 180 ? getLongitude() - 360 : getLongitude());
        if (getLatitude() + hfov / 2 > 80) {
            setLatitude(80 - hfov / 2);
        }
        if (getLatitude() - hfov / 2 < -80) {
            setLatitude(-80 + hfov / 2);
        }
        if (getFov() > getMaxFov()) {
            setFov(getMaxFov());
        }
        if (getFov() < getMinFov()) {
            setFov(getMinFov());
        }
        camera1.getTransforms().clear();
        Rotate ry = new Rotate(getLongitude(), Rotate.Y_AXIS);
        camera1.getTransforms().add(ry);
        Rotate rx = new Rotate(getLatitude(), Rotate.X_AXIS);
        camera1.getTransforms().add(rx);
        camera1.setFieldOfView(getFov());
        double rayon = sscPanorama.getWidth() / 2.d / Math.sin(getFov() / 2.d * rapportDegToRad);
        double positionNord;
        double angle = getPositNord() - getLongitude();
        positionNord = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblNord.getPrefWidth() / 2;
        lblNord.setLayoutX(positionNord);
        lblNord.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionSud;
        angle = getPositNord() - getLongitude() + 180;
        positionSud = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblSud.getPrefWidth() / 2;
        lblSud.setLayoutX(positionSud);
        lblSud.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionEst;
        angle = getPositNord() - getLongitude() + 90;
        positionEst = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblEst.getPrefWidth() / 2;
        lblEst.setLayoutX(positionEst);
        lblEst.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionOuest;
        angle = getPositNord() - getLongitude() - 90;
        positionOuest = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblOuest.getPrefWidth() / 2;
        lblOuest.setLayoutX(positionOuest);
        lblOuest.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionNordEst;
        angle = getPositNord() + 45 - getLongitude();
        positionNordEst = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblNordEst.getPrefWidth() / 2;
        lblNordEst.setLayoutX(positionNordEst);
        lblNordEst.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionNordOuest;
        angle = getPositNord() - getLongitude() - 45;
        positionNordOuest = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblNordOuest.getPrefWidth() / 2;
        lblNordOuest.setLayoutX(positionNordOuest);
        lblNordOuest.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionSudEst;
        angle = getPositNord() - getLongitude() + 135;
        positionSudEst = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblSudEst.getPrefWidth() / 2;
        lblSudEst.setLayoutX(positionSudEst);
        lblSudEst.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        double positionSudOuest;
        angle = getPositNord() - getLongitude() - 135;
        positionSudOuest = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad) - lblSudOuest.getPrefWidth() / 2;
        lblSudOuest.setLayoutX(positionSudOuest);
        lblSudOuest.setVisible(Math.cos(angle * rapportDegToRad) > 0);
        apNord.getChildren().addAll(lblNord, lblEst, lblSud, lblOuest, lblNordEst, lblNordOuest, lblSudEst, lblSudOuest);
        int iNombreTrait = 360;
        double positionTrait;
        for (int i = 0; i < iNombreTrait; i++) {
            angle = i + getPositNord() - getLongitude();
            positionTrait = sscPanorama.getWidth() / 2.d + rayon * Math.sin((angle) * rapportDegToRad);
            Color couleur=Color.WHITE;
            double hautTrait = 15;
            if (i % 5 == 0) {
                hautTrait = 10;
            }
            if (i % 10 == 0) {
                hautTrait = 5;
            }
            if (i%45==0){
                hautTrait=15;
                couleur=Color.GREEN;
            }
            if (i%90==0){
                hautTrait=15;
                couleur=Color.RED;
            }

            Line ligneTrait = new Line(positionTrait, hautTrait, positionTrait, 45);
            ligneTrait.setVisible(Math.cos(angle * rapportDegToRad) > 0);
            ligneTrait.setStroke(couleur);
            apNord.getChildren().add(ligneTrait);
        }
        Line ligneCentre = new Line(apNord.getPrefWidth() / 2, 0, apNord.getPrefWidth() / 2, 40);
        ligneCentre.setStroke(Color.RED);
        ligneCentre.setStrokeWidth(3);
        apNord.getChildren().add(ligneCentre);
        bdfLong.setNumber(new BigDecimal(longitude));
        bdfLat.setNumber(new BigDecimal(latitude));
        bdfFOV.setNumber(new BigDecimal(fov));
    }

    public void changeTaille(double largeur, double hauteur) {
        hauteurImage = hauteur;
        largeurImage = largeur;
        reaffiche();
    }

    private void reaffiche() {
        apPanorama.getChildren().clear();
        spPanorama.setRotationAxis(Rotate.Y_AXIS);
        sscPanorama.setWidth(largeurImage);
        sscPanorama.setHeight(hauteurImage);
        apNord = new AnchorPane();
        apNord.setStyle("-fx-background-color:rgba(0,0,0,0.5);");
        apNord.setPrefWidth(largeurImage);
        apNord.setPrefHeight(30);
        apNord.setLayoutX(positX);
        apNord.setLayoutY(positY + 30);
        lblNord = new Label("N");
        lblNord.setTextFill(Color.WHITE);
        lblNord.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblNord.setLayoutY(1);
        lblNord.setPrefWidth(30);
        lblNord.setAlignment(Pos.CENTER);
        lblSud = new Label("S");
        lblSud.setTextFill(Color.WHITE);
        lblSud.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblSud.setLayoutY(1);
        lblSud.setPrefWidth(30);
        lblSud.setAlignment(Pos.CENTER);
        lblEst = new Label("E");
        lblEst.setTextFill(Color.WHITE);
        lblEst.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblEst.setLayoutY(1);
        lblEst.setPrefWidth(30);
        lblEst.setAlignment(Pos.CENTER);
        lblOuest = new Label("O");
        lblOuest.setTextFill(Color.WHITE);
        lblOuest.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblOuest.setLayoutY(1);
        lblOuest.setPrefWidth(30);
        lblOuest.setAlignment(Pos.CENTER);
        lblNordEst = new Label("NE");
        lblNordEst.setTextFill(Color.WHITE);
        lblNordEst.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblNordEst.setLayoutY(1);
        lblNordEst.setPrefWidth(30);
        lblNordEst.setAlignment(Pos.CENTER);
        lblNordOuest = new Label("NO");
        lblNordOuest.setTextFill(Color.WHITE);
        lblNordOuest.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblNordOuest.setLayoutY(1);
        lblNordOuest.setPrefWidth(30);
        lblNordOuest.setAlignment(Pos.CENTER);
        lblSudEst = new Label("SE");
        lblSudEst.setTextFill(Color.WHITE);
        lblSudEst.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblSudEst.setLayoutY(1);
        lblSudEst.setPrefWidth(30);
        lblSudEst.setAlignment(Pos.CENTER);
        lblSudOuest = new Label("SO");
        lblSudOuest.setTextFill(Color.WHITE);
        lblSudOuest.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        lblSudOuest.setLayoutY(1);
        lblSudOuest.setPrefWidth(30);
        lblSudOuest.setAlignment(Pos.CENTER);
        apNord.getChildren().addAll(lblNord, lblEst, lblSud, lblOuest, lblNordEst, lblNordOuest, lblSudEst, lblSudOuest);
        apNord.setClip(new Rectangle(largeurImage, 20));
        sscPanorama.setLayoutX(positX);
        sscPanorama.setLayoutY(positY + 30);
        bdfLong.setPrefWidth((largeurImage - 30) / 3 - 10);
        bdfLat.setPrefWidth((largeurImage - 30) / 3 - 10);
        bdfFOV.setPrefWidth((largeurImage - 30) / 3 - 10);
        bdfLong.setMaxWidth((largeurImage - 30) / 3 - 10);
        bdfLat.setMaxWidth((largeurImage - 30) / 3 - 10);
        bdfFOV.setMaxWidth((largeurImage - 30) / 3 - 10);
        bdfLong.setMinWidth((largeurImage - 30) / 3 - 10);
        bdfLat.setMinWidth((largeurImage - 30) / 3 - 10);
        bdfFOV.setMinWidth((largeurImage - 30) / 3 - 10);
        bdfLong.setLayoutX(positX + 40);
        bdfLong.setLayoutY(positY);
        bdfLat.setLayoutX(positX + 40 + (largeurImage - 30) / 3);
        bdfLat.setLayoutY(positY);
        bdfFOV.setLayoutX(positX + 40 + 2 * (largeurImage - 30) / 3);
        bdfFOV.setLayoutY(positY);
        bdfLong.setMinValue(new BigDecimal(-181));
        bdfLong.setMaxValue(new BigDecimal(181));
        bdfLat.setMinValue(new BigDecimal(-90));
        bdfLat.setMaxValue(new BigDecimal(90));
        bdfFOV.setMaxValue(new BigDecimal(25));
        bdfFOV.setMaxValue(new BigDecimal(105));
        apPanorama.getChildren().add(sscPanorama);
        apPanorama.setPrefWidth(largeurImage + 2 * positX);
        apPanorama.setMinWidth(largeurImage + 2 * positX);
        apPanorama.setMaxWidth(largeurImage + 2 * positX);
        apPanorama.setPrefHeight(hauteurImage + 2 * positY + 60);
        apPanorama.setMinHeight(hauteurImage + 2 * positY + 60);
        apPanorama.setMaxHeight(hauteurImage + 2 * positY + 60);
        apPanorama.getChildren().addAll(apNord, bdfLong, bdfLat, bdfFOV);
        sscPanorama.setFocusTraversable(true);
        btnChoixNord = new Button(rbLocalisation.getString("navigateur.nord"));
        btnChoixNord.setPrefWidth(80);
        btnChoixNord.setLayoutX(sscPanorama.getLayoutX() + sscPanorama.getWidth() - btnChoixNord.getPrefWidth() - 5);
        btnChoixNord.setLayoutY(sscPanorama.getLayoutY() + sscPanorama.getHeight() - btnChoixNord.getPrefHeight() + 5);
        btnChoixVue = new Button(rbLocalisation.getString("navigateur.choixPOV"));
        btnChoixVue.setPrefWidth(80);
        btnChoixVue.setLayoutX(btnChoixNord.getLayoutX() - btnChoixVue.getPrefWidth() - 5);
        btnChoixVue.setLayoutY(sscPanorama.getLayoutY() + sscPanorama.getHeight() - btnChoixNord.getPrefHeight() + 5);
        apPanorama.getChildren().addAll(btnChoixNord, btnChoixVue);
        camera1 = addCamera(sscPanorama);
        affiche();
        sscPanorama.setOnScroll((event) -> {
            double facteurEchelle = event.getDeltaY() > 0 ? 1.1d : 1.d / 1.1d;
            setFov(getFov() * facteurEchelle);
            affiche();
            event.consume();
        });
        Timeline timBouge = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent event) -> {
            setLongitude(getLongitude() + (-anchorX + bougeX) / 100);
            setLatitude(getLatitude() - (-anchorY + bougeY) / 100);
            affiche();
        }));
        timBouge.setCycleCount(Timeline.INDEFINITE);
        timBouge.pause();
        btnChoixNord.setOnAction((event) -> {
            setPositNord(getLongitude());
            affiche();
        });
        btnChoixVue.setOnAction((event) -> {
            setChoixLongitude(longitude);
            setChoixLatitude(latitude);
            setChoixFov(fov);
        });
        sscPanorama.setOnMousePressed((event) -> {
            if (!bMouvement) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                bougeX = event.getSceneX();
                bougeY = event.getSceneY();
                bMouvement = true;
                timBouge.play();
            }
        });
        sscPanorama.setOnMouseReleased((event) -> {
            bMouvement = false;
            timBouge.pause();
        });

        sscPanorama.setOnMouseDragged((event) -> {
            bougeX = event.getSceneX();
            bougeY = event.getSceneY();
            affiche();
        });
        sscPanorama.setOnKeyPressed((event) -> {
            String code = event.getCode().toString();
            switch (code) {
                case "RIGHT":
                    setLongitude(getLongitude() + 0.5);
                    break;
                case "LEFT":
                    setLongitude(getLongitude() - 0.5);
                    break;
                case "UP":
                    setLatitude(getLatitude() + 0.5);
                    break;
                case "DOWN":
                    setLatitude(getLatitude() - 0.5);
                    break;
                case "ADD":
                    setFov(getFov() / 1.1);
                    break;
                case "SUBTRACT":
                    setFov(getFov() * 1.1);
                    break;
            }
            affiche();

        });
        bdfLong.numberProperty().addListener((e) -> {
            if (bdfLong.getNumber().doubleValue() != ancLongitude) {
                longitude = bdfLong.getNumber().doubleValue();
                ancLongitude = longitude;
                affiche();
            }
        });
        bdfLat.numberProperty().addListener((e) -> {
            if (bdfLat.getNumber().doubleValue() != ancLatitude) {
                latitude = bdfLat.getNumber().doubleValue();
                ancLatitude = latitude;
                affiche();
            }
        });
        bdfFOV.numberProperty().addListener((e) -> {
            if (bdfFOV.getNumber().doubleValue() != ancFov) {
                fov = bdfFOV.getNumber().doubleValue();
                ancFov = fov;
                affiche();
            }
        });

    }

    public AnchorPane affichePano() {
        apPanorama = new AnchorPane();
        apPanorama.setStyle("-fx-background-color :-fx-background");
        Image imgRect = new Image("file:" + getNomFichierPanoramique());
        Image imgTransf = imgTransformationImage(imgRect);
        phmPanorama.setDiffuseMap(imgTransf);
        phmPanorama.setSpecularMap(imgTransf);

        spPanorama.setCullFace(CullFace.FRONT);
        spPanorama.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        spPanorama.setMaterial(phmPanorama);
        sscPanorama = new SubScene(root, largeurImage, hauteurImage);

        reaffiche();
        return apPanorama;

    }

    public void setNomImagePanoramique(String strImagePanoramique, int iRapport) {
        this.setNomFichierPanoramique(strImagePanoramique);
        Image imgRect = new Image("file:" + getNomFichierPanoramique());
        Image imgTransf = imgTransformationImage(imgRect,iRapport);
        phmPanorama.setDiffuseMap(imgTransf);
        phmPanorama.setSpecularMap(imgTransf);
    }

    public void setImagePanoramique(String strImagePanoramique, Image imgPanoramique) {
        this.setNomFichierPanoramique(strImagePanoramique);
        phmPanorama.setDiffuseMap(imgPanoramique);
        phmPanorama.setSpecularMap(imgPanoramique);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        double ancienneValeur = this.latitude;
        this.latitude = latitude;
        double nouvelleValeur = this.latitude;
        this.changeSupport.firePropertyChange("latitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        double ancienneValeur = this.longitude;
        this.longitude = longitude;
        double nouvelleValeur = this.longitude;
        this.changeSupport.firePropertyChange("longitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the fov
     */
    public double getFov() {
        return fov;
    }

    /**
     * @param fov the fov to set
     */
    public void setFov(double fov) {
        double ancienneValeur = this.fov;
        this.fov = fov;
        double nouvelleValeur = this.fov;
        this.changeSupport.firePropertyChange("fov", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the positNord
     */
    public double getPositNord() {
        return positNord;
    }

    /**
     * @param positNord the positNord to set
     */
    public void setPositNord(double positNord) {
        double ancienneValeur = this.positNord;
        this.positNord = positNord;
        double nouvelleValeur = this.positNord;
        this.changeSupport.firePropertyChange("positNord", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the nomFichierPanoramique
     */
    public String getNomFichierPanoramique() {
        return nomFichierPanoramique;
    }

    /**
     * @param nomFichierPanoramique the nomFichierPanoramique to set
     */
    public void setNomFichierPanoramique(String nomFichierPanoramique) {
        this.nomFichierPanoramique = nomFichierPanoramique;
    }

    /**
     * @return the choixLongitude
     */
    public double getChoixLongitude() {
        return choixLongitude;
    }

    /**
     * @param choixLongitude the choixLongitude to set
     */
    public void setChoixLongitude(double choixLongitude) {
        double ancienneValeur = this.choixLongitude;
        this.choixLongitude = choixLongitude;
        double nouvelleValeur = this.choixLongitude;
        this.changeSupport.firePropertyChange("choixLongitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the choixLatitude
     */
    public double getChoixLatitude() {
        return choixLatitude;
    }

    /**
     * @param choixLatitude the choixLatitude to set
     */
    public void setChoixLatitude(double choixLatitude) {
        double ancienneValeur = this.choixLatitude;
        this.choixLatitude = choixLatitude;
        double nouvelleValeur = this.choixLatitude;
        this.changeSupport.firePropertyChange("choixLatitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the choixFov
     */
    public double getChoixFov() {
        return choixFov;
    }

    /**
     * @param choixFov the choixFov to set
     */
    public void setChoixFov(double choixFov) {
        double ancienneValeur = this.choixFov;
        this.choixFov = choixFov;
        double nouvelleValeur = this.choixFov;
        this.changeSupport.firePropertyChange("choixFov", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the maxFov
     */
    public double getMaxFov() {
        return maxFov;
    }

    /**
     * @param maxFov the maxFov to set
     */
    public void setMaxFov(double maxFov) {
        this.maxFov = maxFov;
    }

    /**
     * @return the minFov
     */
    public double getMinFov() {
        return minFov;
    }

    /**
     * @param minFov the minFov to set
     */
    public void setMinFov(double minFov) {
        this.minFov = minFov;
    }

}
