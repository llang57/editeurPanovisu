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

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getStrRepertAppli;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author llang
 */
public final class NavigateurPanoramique {

    /**
     * Capture l'écran actuel du panoramique et sauvegarde en image
     * 
     * <p>Génère une vignette du point de vue actuel du panoramique.</p>
     */
    public void captureEcran() {
        WritableImage image = sscPanorama.snapshot(new SnapshotParameters(), null);
        try {
            String nomFichier = getPanoramiquesProjet()[getiPanoActuel()].getStrNomFichier();
            getPanoramiquesProjet()[getiPanoActuel()].setImgVignettePanoramique(image);
            String nomVignette = nomFichier.substring(0, nomFichier.lastIndexOf(".")) + "Vignette.jpg";
            Image img1 = ReadWriteImage.resizeImage(image, 300, 150);
            ReadWriteImage.writeJpeg(img1, nomVignette, 1, false, 0.05f);
            setiChangeVignette();
        } catch (IOException ex) {
            Logger.getLogger(NavigateurPanoramique.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Capture une image de l'écran du panorama pour les hotspots
     * 
     * <p>Crée une capture d'écran de la zone de visualisation panoramique actuelle.
     * Cette image est utilisée comme aperçu miniature pour les hotspots permettant
     * de visualiser la direction pointée.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * NavigateurPanoramique nav = new NavigateurPanoramique();
     * Image apercu = nav.captureEcranHS();
     * hotspot.setImageApercu(apercu);
     * }</pre>
     * 
     * @return Image capturée de la vue panoramique actuelle
     * @see javafx.scene.SubScene#snapshot(javafx.scene.SnapshotParameters, javafx.scene.image.WritableImage)
     */
    public Image captureEcranHS() {
        return sscPanorama.snapshot(new SnapshotParameters(), null);
    }

    protected transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * <p>Permet d'écouter les modifications d'une propriété spécifique du navigateur.
     * Utilisé notamment pour surveiller les changements de longitude et latitude.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * navigateur.addPropertyChangeListener("longitude", evt -> {
     *     System.out.println("Nouvelle longitude: " + evt.getNewValue());
     * });
     * }</pre>
     * 
     * @param propertyName Nom de la propriété à écouter ("longitude", "latitude", etc.)
     * @param listener Écouteur qui sera notifié des changements
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Retire un écouteur de changement de propriété
     * 
     * <p>Supprime un écouteur précédemment ajouté pour une propriété spécifique.
     * L'écouteur ne recevra plus de notifications pour cette propriété.</p>
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Déclenche un événement de changement de propriété
     * 
     * <p>Notifie tous les écouteurs enregistrés qu'une propriété a changé.
     * Si les valeurs ancienne et nouvelle sont identiques, aucun événement n'est déclenché.</p>
     * 
     * <p><strong>Exemple d'utilisation interne :</strong></p>
     * <pre>{@code
     * BigDecimal ancienneLongitude = this.longitude;
     * this.longitude = nouvelleLongitude;
     * firePropertyChange("longitude", ancienneLongitude, nouvelleLongitude);
     * }</pre>
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur (BigDecimal)
     * @param newValue Nouvelle valeur (BigDecimal)
     * @see java.beans.PropertyChangeSupport#firePropertyChange(java.beans.PropertyChangeEvent)
     */
    public void firePropertyChange(String propertyName, BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue != null && newValue != null && oldValue.compareTo(newValue) == 0) {
            return;
        }
        changeSupport.firePropertyChange(new PropertyChangeEvent(this, propertyName,
                oldValue, newValue));

    }
    private double maxFov = 35;  // Limité à 35° (70° pour la caméra avec *2) pour éviter l'inversion
    private double minFov = 1;   // 1° pour permettre un zoom important
    private boolean bChoixHotSpot = false;
    private double anchorX, anchorY,
            latitude = 0, longitude = 0, fov = 35, positNord = 32,
            choixLongitude = 0, choixLatitude = 0, choixFov = 50,
            largeurImage = 340, hauteurImage = 200, positX = 100, positY = 100,
            bougeX, bougeY,
            ancLongitude = 0, ancLatitude = 0, ancFov = 70;
    private final double rapportDegToRad = Math.PI / 180.d;
    private PerspectiveCamera camera1;
    private SubScene sscPanorama;
    private Label lblNord, lblSud, lblEst, lblOuest, lblNordEst, lblSudOuest, lblSudEst, lblNordOuest;
    private AnchorPane apNord, apPanorama;
    private boolean bMouvement = false;
    private final BigDecimalField bdfLong = new BigDecimalField(new BigDecimal(0)),
            bdfLat = new BigDecimalField(new BigDecimal(0)),
            bdfFOV = new BigDecimalField(new BigDecimal(35));
    private final Label lblLongValue = new Label("0.00");
    private final Label lblLatValue = new Label("0.00");
    private final Label lblFovValue = new Label("35.00");
    private String nomFichierPanoramique = "";
    private int iChangeVignette = 0;
    private Image imgPanoramique;
    private Button btnChoixNord, btnChoixVue, btnChoixVignette, btnPleinEcran;
    private boolean bPleinEcran = false;
    private boolean hauteQualite = false; // Flag pour activer le rendu haute qualité
    private final PanoramicCube panoramicCube = new PanoramicCube();
    private final Group root = new Group(panoramicCube);
    private ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
    private Image imgVignetteHS;

    public NavigateurPanoramique(Image imgFichierPanoramique, double positX, double positY, double largeur, double hauteur, boolean bVignettes) {
        this.positX = positX;
        this.positY = positY;
        this.largeurImage = largeur;
        this.hauteurImage = hauteur;
        this.setImgPanoramique(imgFichierPanoramique);
        this.bChoixHotSpot = bVignettes;
        //this.setNomFichierPanoramique(nomFichierPanoramique);
    }

    public NavigateurPanoramique(Image imgFichierPanoramique, double positX, double positY, double largeur, double hauteur) {
        this.positX = positX;
        this.positY = positY;
        this.largeurImage = largeur;
        this.hauteurImage = hauteur;
        this.setImgPanoramique(imgFichierPanoramique);
        //this.setNomFichierPanoramique(nomFichierPanoramique);
    }

    /**
     * Transforme une image rectangulaire en projection équirectangulaire
     * 
     * @param imgRect Image rectangulaire source
     * @return Image transformée en équirectangulaire
     */
    public static Image imgTransformationImage(Image imgRect) {
        return imgTransformationImage(imgRect, 2);
    }

    public static Image imgTransformationImage(Image imgRect, int iRapport) {
        int iLargeur = (int) imgRect.getWidth() / iRapport;
        int iHauteur = iLargeur / 2 / iRapport;
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
        perspectiveCamera.setFieldOfView(getFov() * 2);
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

    /**
     * Affiche le navigateur panoramique dans l'interface
     * 
     * <p>Initialise et rend visible le composant de navigation 360°.</p>
     */
    public void affiche() {
        setLongitude((getLongitude()) % 360);
        setLongitude(getLongitude() < 0 ? getLongitude() + 360 : getLongitude());
        setLongitude(getLongitude() > 180 ? getLongitude() - 360 : getLongitude());
        if (getLatitude() > 90) {
            setLatitude(90);
        }
        if (getLatitude() < -90) {
            setLatitude(-90);
        }
        if (getFov() > getMaxFov()) {
            setFov(getMaxFov());
        }
        if (getFov() < getMinFov()) {
            setFov(getMinFov());
        }
        if (Platform.isSupported(ConditionalFeature.SCENE3D)) {

            double hfov = getFov() * hauteurImage / largeurImage;
            apNord.getChildren().clear();
            camera1.getTransforms().clear();
            Rotate ry = new Rotate(getLongitude(), Rotate.Y_AXIS);
            camera1.getTransforms().add(ry);
            Rotate rx = new Rotate(getLatitude(), Rotate.X_AXIS);
            camera1.getTransforms().add(rx);
            camera1.setFieldOfView(getFov() * 2);
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
                Color couleur = Color.WHITE;
                double hautTrait = 15;
                if (i % 5 == 0) {
                    hautTrait = 10;
                }
                if (i % 10 == 0) {
                    hautTrait = 5;
                }
                if (i % 45 == 0) {
                    hautTrait = 15;
                    couleur = Color.GREEN;
                }
                if (i % 90 == 0) {
                    hautTrait = 15;
                    couleur = Color.RED;
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
        }
        bdfLong.setNumber(new BigDecimal(getLongitude()));
        bdfLat.setNumber(new BigDecimal(getLatitude()));
        bdfFOV.setNumber(new BigDecimal(getFov()));
        
        // Mise à jour des labels d'affichage
        lblLongValue.setText(String.format("Long: %.2f°", getLongitude()));
        lblLatValue.setText(String.format("Lat: %.2f°", getLatitude()));
        lblFovValue.setText(String.format("FOV: %.2f°", getFov()));
    }

    /**
     * Redimensionne le navigateur panoramique
     * 
     * @param largeur Nouvelle largeur en pixels
     * @param hauteur Nouvelle hauteur en pixels
     */
    public void changeTaille(double largeur, double hauteur) {
        hauteurImage = hauteur;
        largeurImage = largeur;
        reaffiche();
    }

    private void reaffiche() {
        apPanorama.getChildren().clear();
        if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
            panoramicCube.setRotationAxis(Rotate.Y_AXIS);
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
            bdfLong.setPrefWidth((largeurImage) / 3);
            bdfLat.setPrefWidth((largeurImage) / 3);
            bdfFOV.setPrefWidth((largeurImage) / 3);
            bdfLong.setMaxWidth((largeurImage) / 3);
            bdfLat.setMaxWidth((largeurImage) / 3);
            bdfFOV.setMaxWidth((largeurImage) / 3);
            bdfLong.setMinWidth((largeurImage) / 3);
            bdfLat.setMinWidth((largeurImage) / 3);
            bdfFOV.setMinWidth((largeurImage) / 3);
            bdfLong.setPrefHeight(25);
            bdfLat.setPrefHeight(25);
            bdfFOV.setPrefHeight(25);
            // BigDecimalField masqués (on utilise les Labels à la place)
            bdfLong.setVisible(false);
            bdfLat.setVisible(false);
            bdfFOV.setVisible(false);
            bdfLong.setLayoutX(positX);
            bdfLong.setLayoutY(positY);
            bdfLat.setLayoutX(positX + (largeurImage) / 3);
            bdfLat.setLayoutY(positY);
            bdfFOV.setLayoutX(positX + 2 * (largeurImage) / 3);
            bdfFOV.setLayoutY(positY);
            
            // Labels d'affichage des coordonnées (style moderne flat design 2025)
            String labelStyleModerne = "-fx-background-color: rgba(255, 255, 255, 0.96); "
                    + "-fx-text-fill: #1A1A1A; "
                    + "-fx-padding: 4 8 4 8; "
                    + "-fx-font-size: 11px; "
                    + "-fx-font-weight: 500; "
                    + "-fx-font-family: 'Segoe UI', 'Roboto', system-ui; "
                    + "-fx-border-color: rgba(0, 0, 0, 0.10); "
                    + "-fx-border-width: 1; "
                    + "-fx-background-radius: 4; "
                    + "-fx-border-radius: 4; "
                    + "-fx-alignment: center; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.12), 2, 0, 0, 1);";
            
            // Largeur identique pour les 3 labels avec espacement
            double labelWidth = (largeurImage - 20) / 3;  // 20px d'espacement total (3 x 5px + marges)
            double espacementLabels = 5;
            
            lblLongValue.setStyle(labelStyleModerne);
            lblLatValue.setStyle(labelStyleModerne);
            lblFovValue.setStyle(labelStyleModerne);
            
            lblLongValue.setPrefWidth(labelWidth);
            lblLatValue.setPrefWidth(labelWidth);
            lblFovValue.setPrefWidth(labelWidth);
            
            lblLongValue.setMinWidth(labelWidth);
            lblLatValue.setMinWidth(labelWidth);
            lblFovValue.setMinWidth(labelWidth);
            
            lblLongValue.setMaxWidth(labelWidth);
            lblLatValue.setMaxWidth(labelWidth);
            lblFovValue.setMaxWidth(labelWidth);
            
            // Position Y plus haute pour éviter le chevauchement avec le visualiseur
            double labelY = positY - 5;  // Remonté de 8px (était à positY + 3)
            
            lblLongValue.setLayoutX(positX + 5);
            lblLongValue.setLayoutY(labelY);
            lblLatValue.setLayoutX(positX + 5 + labelWidth + espacementLabels);
            lblLatValue.setLayoutY(labelY);
            lblFovValue.setLayoutX(positX + 5 + 2 * (labelWidth + espacementLabels));
            lblFovValue.setLayoutY(labelY);
            
            bdfLong.setMinValue(new BigDecimal(-181));
            bdfLong.setMaxValue(new BigDecimal(181));
            bdfLat.setMinValue(new BigDecimal(-90));
            bdfLat.setMaxValue(new BigDecimal(90));
            bdfFOV.setMinValue(new BigDecimal(getMinFov()));
            bdfFOV.setMaxValue(new BigDecimal(getMaxFov()));
            apPanorama.getChildren().add(sscPanorama);
            apPanorama.setPrefWidth(largeurImage + 2 * positX);
            apPanorama.setMinWidth(largeurImage + 2 * positX);
            apPanorama.setMaxWidth(largeurImage + 2 * positX);
            // Hauteur = labels (30px en haut) + viewer (hauteurImage) + espace (5px) + boutons (32px) + marge (positY)
            apPanorama.setPrefHeight(hauteurImage + 2 * positY + 70);
            apPanorama.setMinHeight(hauteurImage + 2 * positY + 70);
            apPanorama.setMaxHeight(hauteurImage + 2 * positY + 70);
            //apPanorama.setStyle("-fx-background-color : #ccc;");
            apPanorama.getChildren().addAll(apNord, bdfLong, bdfLat, bdfFOV, lblLongValue, lblLatValue, lblFovValue);
            sscPanorama.setFocusTraversable(true);

            // Détection du thème pour choisir les bonnes icônes
            boolean estThemeSombre = ThemeManager.getCurrentTheme().isDark();
            String suffixeIcone = estThemeSombre ? "_bl.png" : ".png";
            
            // Création des boutons avec icônes PNG adaptées au thème et tooltips
            // Bouton Home - Retour à la vue initiale
            Button btnHome = new Button("", new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/home" + suffixeIcone, 30, 30, true, true)));
            btnHome.setTooltip(new Tooltip(rbLocalisation.getString("navigateur.retourVueInitiale")));
            btnHome.setOnMouseClicked(
                    (me) -> {
                        setLongitude(getChoixLongitude());
                        setLatitude(getChoixLatitude());
                        setFov(getChoixFov());
                        affiche();
                    }
            );

            // Bouton Vignette - Capture d'écran (icône PNG)
            btnChoixVignette = new Button("", new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/photo" + suffixeIcone, 30, 30, true, true)));
            btnChoixVignette.setTooltip(new Tooltip(rbLocalisation.getString("navigateur.capturerVignette")));
            
            // Bouton Nord - Définir le Nord (icône PNG)
            btnChoixNord = new Button("", new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/boussole" + suffixeIcone, 30, 30, true, true)));
            btnChoixNord.setTooltip(new Tooltip(rbLocalisation.getString("navigateur.nord")));
            
            // Bouton Vue - Point de vue par défaut (icône PNG)
            btnChoixVue = new Button("", new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/oeil" + suffixeIcone, 30, 30, true, true)));
            btnChoixVue.setTooltip(new Tooltip(rbLocalisation.getString("navigateur.choixPOV")));
            
            // Bouton Plein écran
            btnPleinEcran = new Button("", new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/expand" + suffixeIcone, 30, 30, true, true)));
            btnPleinEcran.setTooltip(new Tooltip(rbLocalisation.getString("navigateur.pleinEcran")));
            
            // Dimensions uniformes pour tous les boutons (carrés agrandis)
            int btnSize = 40;
            int btnHeight = 40;
            
            btnHome.setPrefWidth(btnSize);
            btnHome.setPrefHeight(btnHeight);
            btnHome.setMinWidth(btnSize);
            btnHome.setMaxWidth(btnSize);
            btnHome.setMinHeight(btnHeight);
            btnHome.setMaxHeight(btnHeight);
            
            btnChoixVignette.setPrefWidth(btnSize);
            btnChoixVignette.setPrefHeight(btnHeight);
            btnChoixVignette.setMinWidth(btnSize);
            btnChoixVignette.setMaxWidth(btnSize);
            btnChoixVignette.setMinHeight(btnHeight);
            btnChoixVignette.setMaxHeight(btnHeight);
            
            btnChoixNord.setPrefWidth(btnSize);
            btnChoixNord.setPrefHeight(btnHeight);
            btnChoixNord.setMinWidth(btnSize);
            btnChoixNord.setMaxWidth(btnSize);
            btnChoixNord.setMinHeight(btnHeight);
            btnChoixNord.setMaxHeight(btnHeight);
            
            btnChoixVue.setPrefWidth(btnSize);
            btnChoixVue.setPrefHeight(btnHeight);
            btnChoixVue.setMinWidth(btnSize);
            btnChoixVue.setMaxWidth(btnSize);
            btnChoixVue.setMinHeight(btnHeight);
            btnChoixVue.setMaxHeight(btnHeight);
            
            btnPleinEcran.setPrefWidth(btnSize);
            btnPleinEcran.setPrefHeight(btnHeight);
            btnPleinEcran.setMinWidth(btnSize);
            btnPleinEcran.setMaxWidth(btnSize);
            btnPleinEcran.setMinHeight(btnHeight);
            btnPleinEcran.setMaxHeight(btnHeight);
            
            // Style des boutons adapté au thème (clair/sombre)
            // Utilise la variable estThemeSombre déjà déclarée plus haut
            
            String btnStyle = estThemeSombre
                    ? "-fx-background-color: rgba(40, 40, 40, 0.9); "
                    + "-fx-border-color: rgba(255, 255, 255, 0.3); "
                    + "-fx-border-width: 1px; "
                    + "-fx-border-radius: 4px; "
                    + "-fx-background-radius: 4px; "
                    + "-fx-padding: 4px; "
                    + "-fx-cursor: hand;"
                    : "-fx-background-color: rgba(255, 255, 255, 0.9); "
                    + "-fx-border-color: rgba(0, 0, 0, 0.2); "
                    + "-fx-border-width: 1px; "
                    + "-fx-border-radius: 4px; "
                    + "-fx-background-radius: 4px; "
                    + "-fx-padding: 4px; "
                    + "-fx-cursor: hand;";
            
            String btnHoverStyle = estThemeSombre
                    ? "-fx-background-color: rgba(60, 60, 60, 0.95); "
                    + "-fx-border-color: rgba(255, 255, 255, 0.4); "
                    + "-fx-border-width: 1px; "
                    + "-fx-border-radius: 4px; "
                    + "-fx-background-radius: 4px; "
                    + "-fx-padding: 4px; "
                    + "-fx-cursor: hand;"
                    : "-fx-background-color: rgba(230, 230, 230, 0.95); "
                    + "-fx-border-color: rgba(0, 0, 0, 0.3); "
                    + "-fx-border-width: 1px; "
                    + "-fx-border-radius: 4px; "
                    + "-fx-background-radius: 4px; "
                    + "-fx-padding: 4px; "
                    + "-fx-cursor: hand;";
            
            // Appliquer le style à tous les boutons
            btnHome.setStyle(btnStyle);
            btnHome.setOnMouseEntered(e -> btnHome.setStyle(btnHoverStyle));
            btnHome.setOnMouseExited(e -> btnHome.setStyle(btnStyle));
            
            btnChoixVignette.setStyle(btnStyle);
            btnChoixVignette.setOnMouseEntered(e -> btnChoixVignette.setStyle(btnHoverStyle));
            btnChoixVignette.setOnMouseExited(e -> btnChoixVignette.setStyle(btnStyle));
            
            btnChoixNord.setStyle(btnStyle);
            btnChoixNord.setOnMouseEntered(e -> btnChoixNord.setStyle(btnHoverStyle));
            btnChoixNord.setOnMouseExited(e -> btnChoixNord.setStyle(btnStyle));
            
            btnChoixVue.setStyle(btnStyle);
            btnChoixVue.setOnMouseEntered(e -> btnChoixVue.setStyle(btnHoverStyle));
            btnChoixVue.setOnMouseExited(e -> btnChoixVue.setStyle(btnStyle));
            
            btnPleinEcran.setStyle(btnStyle);
            btnPleinEcran.setOnMouseEntered(e -> btnPleinEcran.setStyle(btnHoverStyle));
            btnPleinEcran.setOnMouseExited(e -> btnPleinEcran.setStyle(btnStyle));
            
            if (isbChoixHotSpot()) {
                btnChoixVignette.setPrefWidth(0);
                btnChoixVignette.setVisible(false);
                btnChoixNord.setPrefWidth(0);
                btnChoixNord.setVisible(false);
            }
            btnChoixVignette.setOnMouseClicked(
                    (me) -> {
                        this.captureEcran();
                    }
            );

            // Positionnement des 5 boutons de gauche à droite avec espacement de 5px
            double btnY = sscPanorama.getLayoutY() + sscPanorama.getHeight() + 5;
            double espacementBoutons = 5;
            double btnStartX = positX + 5;
            
            btnHome.setLayoutX(btnStartX);
            btnHome.setLayoutY(btnY);
            
            btnChoixVignette.setLayoutX(btnHome.getLayoutX() + btnSize + espacementBoutons);
            btnChoixVignette.setLayoutY(btnY);
            
            btnChoixNord.setLayoutX(btnChoixVignette.getLayoutX() + btnSize + espacementBoutons);
            btnChoixNord.setLayoutY(btnY);
            
            btnChoixVue.setLayoutX(btnChoixNord.getLayoutX() + btnSize + espacementBoutons);
            btnChoixVue.setLayoutY(btnY);
            
            btnPleinEcran.setLayoutX(btnChoixVue.getLayoutX() + btnSize + espacementBoutons);
            btnPleinEcran.setLayoutY(btnY);
            
            // Ajouter les 5 boutons
            apPanorama.getChildren().addAll(btnChoixVignette, btnChoixNord, btnChoixVue, btnPleinEcran, btnHome);
            camera1 = addCamera(sscPanorama);
            affiche();
            sscPanorama.setOnScroll((event) -> {
                double facteurEchelle = event.getDeltaY() < 0 ? 1.1d : 1.d / 1.1d;
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
                setChoixLongitude(getLongitude());
                setChoixLatitude(getLatitude());
                setChoixFov(getFov());
                setImgVignetteHS(captureEcranHS());
            });
            btnPleinEcran.setOnAction((event) -> {
                // Déclencher le changement d'état plein écran via PropertyChange
                boolean ancienneValeur = this.bPleinEcran;
                this.bPleinEcran = !this.bPleinEcran;
                this.changeSupport.firePropertyChange("pleinEcran", ancienneValeur, this.bPleinEcran);
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
                    setLongitude(bdfLong.getNumber().doubleValue());
                    ancLongitude = getLongitude();
                    affiche();
                }
            });
            bdfLat.numberProperty().addListener((e) -> {
                if (bdfLat.getNumber().doubleValue() != ancLatitude) {
                    setLatitude(bdfLat.getNumber().doubleValue());
                    ancLatitude = getLatitude();
                    affiche();
                }
            });
            bdfFOV.numberProperty().addListener((e) -> {
                if (bdfFOV.getNumber().doubleValue() != ancFov) {
                    setFov(bdfFOV.getNumber().doubleValue());
                    ancFov = getFov();
                    affiche();
                }
            });
        } else {
            // Diagnostic détaillé du support JavaFX
            System.err.println("❌ SCENE3D non supporté - Diagnostic JavaFX:");
            System.err.println("   OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
            System.err.println("   JavaFX Version: " + System.getProperty("javafx.version", "N/A"));
            System.err.println("   SCENE3D: " + Platform.isSupported(ConditionalFeature.SCENE3D));
            System.err.println("   GRAPHICS: " + Platform.isSupported(ConditionalFeature.GRAPHICS));
            System.err.println("   CONTROLS: " + Platform.isSupported(ConditionalFeature.CONTROLS));
            System.err.println("   FXML: " + Platform.isSupported(ConditionalFeature.FXML));
            System.err.println("   WEB: " + Platform.isSupported(ConditionalFeature.WEB));
            System.err.println("\n💡 Solutions possibles sur Linux:");
            System.err.println("   1. Installer Mesa OpenGL: sudo apt install libgl1-mesa-glx libgl1-mesa-dri");
            System.err.println("   2. Vérifier les pilotes GPU: sudo ubuntu-drivers list");
            System.err.println("   3. Variables d'environnement:");
            System.err.println("      export PRISM_VERBOSE=true");
            System.err.println("      export PRISM_FORCEGL=true");

            String messageDiagnostic = rbLocalisation.getString("visu.nonSupporte") + "\n\n" +
                    "JavaFX 3D (SCENE3D) n'est pas disponible.\n" +
                    "OS: " + System.getProperty("os.name") + "\n\n" +
                    "Sur Linux, installez:\n" +
                    "sudo apt install libgl1-mesa-glx libgl1-mesa-dri\n\n" +
                    "Puis relancez avec:\n" +
                    "PRISM_FORCEGL=true java -jar ...";
            
            Label lblPanorama = new Label(messageDiagnostic);
            lblPanorama.setStyle("-fx-text-fill : #FF3333;-fx-font-size : 12px;-fx-font-family: monospace;");
            lblPanorama.setAlignment(Pos.CENTER);
            lblPanorama.setWrapText(true);
            lblPanorama.setPrefWidth(largeurImage + 2 * positX);
            lblPanorama.setMinWidth(largeurImage + 2 * positX);
            lblPanorama.setMaxWidth(largeurImage + 2 * positX);
            lblPanorama.setPrefHeight(hauteurImage + 2 * positY + 60);
            lblPanorama.setMinHeight(hauteurImage + 2 * positY + 60);
            lblPanorama.setMaxHeight(hauteurImage + 2 * positY + 60);
            bdfLong.setPrefWidth((largeurImage) / 3);
            bdfLat.setPrefWidth((largeurImage) / 3);
            bdfFOV.setPrefWidth((largeurImage) / 3);
            bdfLong.setMaxWidth((largeurImage) / 3);
            bdfLat.setMaxWidth((largeurImage) / 3);
            bdfFOV.setMaxWidth((largeurImage) / 3);
            bdfLong.setMinWidth((largeurImage) / 3);
            bdfLat.setMinWidth((largeurImage) / 3);
            bdfFOV.setMinWidth((largeurImage) / 3);
            bdfLong.setPrefHeight(25);
            bdfLat.setPrefHeight(25);
            bdfFOV.setPrefHeight(25);
            bdfLong.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-font-size: 12px;");
            bdfLat.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-font-size: 12px;");
            bdfFOV.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-font-size: 12px;");
            bdfLong.setLayoutX(positX);
            bdfLong.setLayoutY(positY);
            bdfLat.setLayoutX(positX + (largeurImage) / 3);
            bdfLat.setLayoutY(positY);
            bdfFOV.setLayoutX(positX + 2 * (largeurImage) / 3);
            bdfFOV.setLayoutY(positY);
            bdfLong.setMinValue(new BigDecimal(-181));
            bdfLong.setMaxValue(new BigDecimal(181));
            bdfLat.setMinValue(new BigDecimal(-90));
            bdfLat.setMaxValue(new BigDecimal(90));
            bdfFOV.setMinValue(new BigDecimal(getMinFov()));
            bdfFOV.setMaxValue(new BigDecimal(getMaxFov()));
            bdfLong.setNumber(new BigDecimal(0));
            bdfLat.setNumber(new BigDecimal(0));
            bdfFOV.setNumber(new BigDecimal(45));

            apPanorama.setPrefWidth(largeurImage + 2 * positX);
            apPanorama.setMinWidth(largeurImage + 2 * positX);
            apPanorama.setMaxWidth(largeurImage + 2 * positX);
            apPanorama.setPrefHeight(hauteurImage + 2 * positY + 60);
            apPanorama.setMinHeight(hauteurImage + 2 * positY + 60);
            apPanorama.setMaxHeight(hauteurImage + 2 * positY + 60);
            apPanorama.getChildren().addAll(lblPanorama, bdfLong, bdfLat, bdfFOV);
            ancLongitude = bdfLong.getNumber().doubleValue();
            ancLatitude = bdfLat.getNumber().doubleValue();
            ancFov = bdfFOV.getNumber().doubleValue();
            bdfLong.setOnMouseClicked((e) -> {
                if (bdfLong.getNumber().doubleValue() != ancLongitude) {
                    setLongitude(bdfLong.getNumber().doubleValue());
                    affiche();
                    ancLongitude = getLongitude();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
            bdfLong.setOnKeyPressed((e) -> {
                if (bdfLong.getNumber().doubleValue() != ancLongitude) {
                    setLongitude(bdfLong.getNumber().doubleValue());
                    affiche();
                    ancLongitude = getLongitude();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
            bdfLat.setOnMouseClicked((e) -> {
                if (bdfLat.getNumber().doubleValue() != ancLatitude) {
                    setLatitude(bdfLat.getNumber().doubleValue());
                    affiche();
                    ancLatitude = getLatitude();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
            bdfLat.setOnKeyPressed((e) -> {
                if (bdfLat.getNumber().doubleValue() != ancLatitude) {
                    setLatitude(bdfLat.getNumber().doubleValue());
                    affiche();
                    ancLatitude = getLatitude();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
            bdfFOV.setOnMouseClicked((e) -> {
                if (bdfFOV.getNumber().doubleValue() != ancFov) {
                    setFov(bdfFOV.getNumber().doubleValue());
                    affiche();
                    ancFov = getFov();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
            bdfFOV.setOnKeyPressed((e) -> {
                if (bdfFOV.getNumber().doubleValue() != ancFov) {
                    setFov(bdfFOV.getNumber().doubleValue());
                    affiche();
                    ancFov = getFov();
                    setChoixLongitude(getLongitude());
                    setChoixLatitude(getLatitude());
                    setChoixFov(getFov());
                }
            });
        }
    }

    /**
     * Affiche le panorama dans un conteneur AnchorPane
     * 
     * <p>Crée et retourne un composant d'interface contenant le panorama affiché
     * sans cache pré-calculé. Cette méthode est équivalente à appeler
     * {@code affichePano(null)}.</p>
     * 
     * @return AnchorPane contenant le panorama à afficher
     * @see #affichePano(Panoramique)
     */
    public AnchorPane affichePano() {
        return affichePano(null);
    }

    /**
     * Affiche le panorama avec cache des cubes pré-calculés si disponible
     * 
     * @param panoramique Objet Panoramique contenant le cache (peut être null)
     * @return AnchorPane contenant le panorama affiché
     */
    public AnchorPane affichePano(Panoramique panoramique) {
        long startTotal = System.currentTimeMillis();
        
        apPanorama = new AnchorPane();
        apPanorama.setStyle("-fx-background-color :-fx-background");
        if (Platform.isSupported(ConditionalFeature.SCENE3D)) {

            // Utiliser le cache si disponible
            Image[] cubeFaces = null;
            if (panoramique != null) {
                if (hauteQualite && panoramique.getCubeFacesGrandeResolution() != null) {
                    cubeFaces = panoramique.getCubeFacesGrandeResolution();
                    System.out.println("   ⚡ Utilisation du cache 1000×1000 (affichePano)");
                } else if (!hauteQualite && panoramique.getCubeFacesPetiteResolution() != null) {
                    cubeFaces = panoramique.getCubeFacesPetiteResolution();
                    System.out.println("   ⚡ Utilisation du cache 500×500 (affichePano)");
                }
            }
            
            long startApply = System.currentTimeMillis();
            if (cubeFaces != null) {
                panoramicCube.setCubeFaces(cubeFaces);
            } else {
                // Fallback : recalcul si pas de cache
                if (panoramique == null) {
                    System.out.println("   ⚠️ Pas d'objet Panoramique fourni, recalcul des faces");
                } else {
                    System.out.println("   ⚠️ Pas de cache, recalcul des faces");
                }
                
                if (hauteQualite) {
                    panoramicCube.setPanoramicImage(this.getImgPanoramique(), 3000, 1500, 1000);
                } else {
                    panoramicCube.setPanoramicImage(this.getImgPanoramique());
                }
            }
            long endApply = System.currentTimeMillis();
            System.out.println("   ⏱️ Application des textures: " + (endApply - startApply) + " ms");
            
            long startSubScene = System.currentTimeMillis();
            sscPanorama = new SubScene(root, largeurImage, hauteurImage);
            long endSubScene = System.currentTimeMillis();
            System.out.println("   ⏱️ Création SubScene: " + (endSubScene - startSubScene) + " ms");
        }

        long startReaffiche = System.currentTimeMillis();
        reaffiche();
        long endReaffiche = System.currentTimeMillis();
        System.out.println("   ⏱️ reaffiche(): " + (endReaffiche - startReaffiche) + " ms");

        long endTotal = System.currentTimeMillis();
        System.out.println("   ⏱️ TOTAL affichePano(): " + (endTotal - startTotal) + " ms");
        
        return apPanorama;

    }

    /**
     * Définit l'image panoramique à afficher
     * 
     * @param strImagePanoramique Chemin vers le fichier image
     * @param iRapport Rapport de redimensionnement
     */
    public void setNomImagePanoramique(String strImagePanoramique, int iRapport) {
        this.setNomFichierPanoramique(strImagePanoramique);
        panoramicCube.setPanoramicImage(this.getImgPanoramique());
    }

    /**
     * Définit l'image panoramique à afficher
     * 
     * <p>Configure le navigateur avec une nouvelle image panoramique équirectangulaire.
     * L'image est appliquée selon le mode de qualité actif (haute ou basse résolution).
     * En mode haute qualité, l'image est projetée sur un cube de 3000×1500 pixels
     * avec une résolution de 1000 pixels par face.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * Image imgPano = new Image("file:///chemin/vers/panorama.jpg");
     * navigateur.setImagePanoramique("panorama.jpg", imgPano);
     * }</pre>
     * 
     * @param strImagePanoramique Chemin du fichier image (utilisé pour référence)
     * @param imgPanoramique Image panoramique équirectangulaire à afficher
     * 
     * @see #setImagePanoramique(String, Image, Panoramique)
     * @see #setHauteQualite(boolean)
     */
    public void setImagePanoramique(String strImagePanoramique, Image imgPanoramique) {
        System.out.println("📸 NavigateurPanoramique.setImagePanoramique() appelé");
        System.out.println("   📄 Fichier: " + strImagePanoramique);
        System.out.println("   📏 Image: " + (imgPanoramique != null ? (int)imgPanoramique.getWidth() + "×" + (int)imgPanoramique.getHeight() : "null"));
        System.out.println("   🎨 Mode haute qualité: " + hauteQualite);
        
        this.setNomFichierPanoramique(strImagePanoramique);
        this.setImgPanoramique(imgPanoramique);
        
        // Appliquer la qualité appropriée selon le mode actif
        if (hauteQualite) {
            panoramicCube.setPanoramicImage(imgPanoramique, 3000, 1500, 1000);
        } else {
            panoramicCube.setPanoramicImage(imgPanoramique);
        }
    }

    /**
     * Applique une image panoramique avec cubes pré-calculés (optimisation)
     * Utilise le cache si disponible, sinon recalcule
     * 
     * @param strImagePanoramique Nom du fichier panoramique
     * @param imgPanoramique Image panoramique source
     * @param panoramique Objet Panoramique contenant le cache des cubes
     */
    public void setImagePanoramique(String strImagePanoramique, Image imgPanoramique, Panoramique panoramique) {
        System.out.println("📸 NavigateurPanoramique.setImagePanoramique() avec cache appelé");
        System.out.println("   📄 Fichier: " + strImagePanoramique);
        System.out.println("   🎨 Mode haute qualité: " + hauteQualite);
        
        this.setNomFichierPanoramique(strImagePanoramique);
        this.setImgPanoramique(imgPanoramique);
        
        // Utiliser le cache si disponible
        Image[] cubeFaces = null;
        if (hauteQualite && panoramique.getCubeFacesGrandeResolution() != null) {
            cubeFaces = panoramique.getCubeFacesGrandeResolution();
            System.out.println("   ⚡ Utilisation du cache 1000×1000");
        } else if (!hauteQualite && panoramique.getCubeFacesPetiteResolution() != null) {
            cubeFaces = panoramique.getCubeFacesPetiteResolution();
            System.out.println("   ⚡ Utilisation du cache 500×500");
        }
        
        if (cubeFaces != null) {
            panoramicCube.setCubeFaces(cubeFaces);
        } else {
            // Fallback : recalcul si pas de cache
            System.out.println("   ⚠️ Pas de cache, recalcul des faces");
            if (hauteQualite) {
                panoramicCube.setPanoramicImage(imgPanoramique, 3000, 1500, 1000);
            } else {
                panoramicCube.setPanoramicImage(imgPanoramique);
            }
        }
    }

    /**
     * Active le mode haute qualité pour le rendu panoramique.
     * Doit être appelé AVANT affichePano() pour être pris en compte.
     * Utilise des paramètres optimisés pour les grandes résolutions d'affichage :
     * - Image équirectangulaire intermédiaire : 3000x1500
     * - Faces du cube : 1000x1000
     */
    public void setHauteQualite(boolean hauteQualite) {
        this.hauteQualite = hauteQualite;
    }
    
    /**
     * Définit l'image panoramique avec une qualité haute résolution
     * Utilise des paramètres optimisés pour les grandes résolutions d'affichage :
     * - Image équirectangulaire intermédiaire : 3000x1500
     * - Faces du cube : 1000x1000
     * 
     * @param imgPanoramique L'image panoramique haute résolution
     */
    public void setImagePanoraMiqueHauteQualite(Image imgPanoramique) {
        this.hauteQualite = true;
        this.setImgPanoramique(imgPanoramique);
        panoramicCube.setPanoramicImage(imgPanoramique, 3000, 1500, 1000);
    }

    /**
     * Retourne la latitude actuelle de la vue panoramique
     * 
     * <p>La latitude représente l'angle vertical de visualisation dans le panorama.
     * Valeur comprise entre -90° (vers le bas) et +90° (vers le haut).</p>
     * 
     * @return Latitude en degrés, où 0° = horizon, +90° = zénith, -90° = nadir
     * @see #setLatitude(double)
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude de la vue panoramique
     * 
     * <p>Modifie l'angle vertical de visualisation. Les valeurs sont automatiquement
     * limitées entre -90° et +90° lors de l'affichage. Cette modification déclenche
     * un événement PropertyChange permettant d'observer le changement.</p>
     * 
     * @param latitude Nouvelle latitude en degrés (-90° à +90°)
     * @see #getLatitude()
     * @see #affiche()
     */
    public void setLatitude(double latitude) {
        double ancienneValeur = this.latitude;
        this.latitude = latitude;
        double nouvelleValeur = this.latitude;
        this.changeSupport.firePropertyChange("latitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne la longitude actuelle de la vue panoramique
     * 
     * <p>La longitude représente l'angle horizontal de visualisation dans le panorama.
     * Valeur normalisée entre -180° et +180° lors de l'affichage.</p>
     * 
     * @return Longitude en degrés, où 0° = centre, valeurs négatives = gauche, positives = droite
     * @see #setLongitude(double)
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude de la vue panoramique
     * 
     * <p>Modifie l'angle horizontal de visualisation. La valeur est automatiquement
     * normalisée entre -180° et +180° lors de l'affichage pour assurer la continuité
     * de la rotation. Cette modification déclenche un événement PropertyChange.</p>
     * 
     * @param longitude Nouvelle longitude en degrés (normalisée automatiquement)
     * @see #getLongitude()
     * @see #affiche()
     */
    public void setLongitude(double longitude) {
        double ancienneValeur = this.longitude;
        this.longitude = longitude;
        double nouvelleValeur = this.longitude;
        this.changeSupport.firePropertyChange("longitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne le champ de vision (Field of View) actuel
     * 
     * <p>Le FOV détermine l'angle de vision horizontal de la caméra panoramique.
     * Plus la valeur est petite, plus le zoom est important (effet téléobjectif).
     * Plus la valeur est grande, plus le champ est large (effet grand-angle).</p>
     * 
     * @return FOV en degrés, limité entre minFov et maxFov
     * @see #setFov(double)
     * @see #getMinFov()
     * @see #getMaxFov()
     */
    public double getFov() {
        return fov;
    }

    /**
     * Définit le champ de vision (Field of View)
     * 
     * <p>Modifie le niveau de zoom de la vue panoramique en ajustant l'angle
     * de vision de la caméra. La valeur est automatiquement limitée entre
     * {@code minFov} et {@code maxFov} pour garantir une visualisation correcte.
     * Un FOV petit correspond à un zoom important (vision télescopique), tandis
     * qu'un FOV grand offre une vue panoramique large.</p>
     * 
     * <p><strong>Exemples typiques :</strong></p>
     * <ul>
     *   <li>FOV 120° : Vue très large (grand-angle)</li>
     *   <li>FOV  90° : Vue normale</li>
     *   <li>FOV  30° : Vue zoomée (téléobjectif)</li>
     * </ul>
     * 
     * @param fov Nouveau champ de vision en degrés (sera limité à [minFov, maxFov])
     * @see #getFov()
     * @see #setMinFov(double)
     * @see #setMaxFov(double)
     */
    public void setFov(double fov) {
        double ancienneValeur = this.fov;
        // Limiter le FOV pour éviter l'inversion de la caméra
        this.fov = Math.max(getMinFov(), Math.min(fov, getMaxFov()));
        double nouvelleValeur = this.fov;
        this.changeSupport.firePropertyChange("fov", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne la position angulaire du nord géographique
     * 
     * <p>Définit l'orientation du panorama par rapport au nord géographique.
     * Cette valeur permet d'afficher correctement les points cardinaux (N, S, E, O)
     * dans l'interface de navigation.</p>
     * 
     * @return Position du nord en degrés (0-360°)
     * @see #setPositNord(double)
     */
    public double getPositNord() {
        return positNord;
    }

    /**
     * Définit la position angulaire du nord géographique
     * 
     * <p>Configure l'orientation du panorama pour aligner les points cardinaux.
     * Déclenche un événement PropertyChange pour mettre à jour l'affichage.</p>
     * 
     * @param positNord Position du nord en degrés (0-360°)
     * @see #getPositNord()
     */
    public void setPositNord(double positNord) {
        double ancienneValeur = this.positNord;
        this.positNord = positNord;
        double nouvelleValeur = this.positNord;
        this.changeSupport.firePropertyChange("positNord", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne le nom du fichier image panoramique
     * 
     * @return Chemin complet ou nom du fichier panoramique chargé
     * @see #setNomFichierPanoramique(String)
     */
    public String getNomFichierPanoramique() {
        return nomFichierPanoramique;
    }

    /**
     * Définit le nom du fichier image panoramique
     * 
     * @param nomFichierPanoramique Chemin ou nom du fichier à afficher
     * @see #getNomFichierPanoramique()
     */
    public void setNomFichierPanoramique(String nomFichierPanoramique) {
        this.nomFichierPanoramique = nomFichierPanoramique;
    }

    /**
     * Retourne la longitude choisie par l'utilisateur pour un hotspot
     * 
     * <p>Cette valeur stocke la longitude sélectionnée lors de la création
     * ou modification d'un hotspot dans le panorama.</p>
     * 
     * @return Longitude de choix en degrés
     * @see #setChoixLongitude(double)
     */
    public double getChoixLongitude() {
        return choixLongitude;
    }

    /**
     * Définit la longitude choisie pour un hotspot
     * 
     * <p>Enregistre la position horizontale sélectionnée lors de l'interaction
     * utilisateur. Déclenche un événement PropertyChange.</p>
     * 
     * @param choixLongitude Longitude de choix en degrés
     * @see #getChoixLongitude()
     */
    public void setChoixLongitude(double choixLongitude) {
        double ancienneValeur = this.choixLongitude;
        this.choixLongitude = choixLongitude;
        double nouvelleValeur = this.choixLongitude;
        this.changeSupport.firePropertyChange("choixLongitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne la latitude choisie par l'utilisateur pour un hotspot
     * 
     * <p>Cette valeur stocke la latitude sélectionnée lors de la création
     * ou modification d'un hotspot dans le panorama.</p>
     * 
     * @return Latitude de choix en degrés (-90° à +90°)
     * @see #setChoixLatitude(double)
     */
    public double getChoixLatitude() {
        return choixLatitude;
    }

    /**
     * Définit la latitude choisie pour un hotspot
     * 
     * <p>Enregistre la position verticale sélectionnée lors de l'interaction
     * utilisateur. Déclenche un événement PropertyChange.</p>
     * 
     * @param choixLatitude Latitude de choix en degrés (-90° à +90°)
     * @see #getChoixLatitude()
     */
    public void setChoixLatitude(double choixLatitude) {
        double ancienneValeur = this.choixLatitude;
        this.choixLatitude = choixLatitude;
        double nouvelleValeur = this.choixLatitude;
        this.changeSupport.firePropertyChange("choixLatitude", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne le FOV choisi par l'utilisateur pour un hotspot
     * 
     * <p>Cette valeur stocke le champ de vision sélectionné lors de la création
     * ou modification d'un hotspot, permettant de mémoriser le niveau de zoom.</p>
     * 
     * @return FOV de choix en degrés
     * @see #setChoixFov(double)
     */
    public double getChoixFov() {
        return choixFov;
    }

    /**
     * Définit le FOV choisi pour un hotspot
     * 
     * <p>Enregistre le champ de vision sélectionné lors de l'interaction utilisateur.
     * Déclenche un événement PropertyChange.</p>
     * 
     * @param choixFov FOV de choix en degrés
     * @see #getChoixFov()
     */
    public void setChoixFov(double choixFov) {
        double ancienneValeur = this.choixFov;
        this.choixFov = choixFov;
        double nouvelleValeur = this.choixFov;
        this.changeSupport.firePropertyChange("choixFov", ancienneValeur, nouvelleValeur);
    }

    /**
     * Retourne l'image panoramique actuellement chargée
     * 
     * @return Image équirectangulaire du panorama, ou null si aucune image chargée
     * @see #setImgPanoramique(Image)
     */
    public Image getImgPanoramique() {
        return this.imgPanoramique;
    }

    /**
     * Définit l'image panoramique à afficher
     * 
     * <p>Charge une nouvelle image équirectangulaire dans le navigateur.</p>
     * 
     * @param imgPanoramique Image panoramique à afficher
     * @see #getImgPanoramique()
     */
    public void setImgPanoramique(Image imgPanoramique) {
        this.imgPanoramique = imgPanoramique;
    }

    /**
     * Retourne le champ de vision maximum autorisé
     * 
     * <p>Définit la limite supérieure du zoom (vue la plus large).
     * Limité à 120° maximum pour éviter les déformations excessives.</p>
     * 
     * @return FOV maximum en degrés (≤ 120°)
     * @see #setMaxFov(double)
     * @see #getFov()
     */
    public double getMaxFov() {
        return maxFov;
    }

    /**
     * Définit le champ de vision maximum autorisé
     * 
     * <p>Configure la limite supérieure du zoom. La valeur est automatiquement
     * plafonnée à 120° pour garantir une visualisation correcte.</p>
     * 
     * @param maxFov FOV maximum souhaité en degrés (sera limité à 120°)
     * @see #getMaxFov()
     */
    public void setMaxFov(double maxFov) {
        // Accepter la valeur de l'utilisateur (limite raisonnable à 120°)
        this.maxFov = Math.min(maxFov, 120);
    }

    /**
     * Retourne le champ de vision minimum autorisé
     * 
     * <p>Définit la limite inférieure du zoom (vue la plus rapprochée/zoomée).
     * Limité à 1° minimum pour éviter les problèmes de rendu.</p>
     * 
     * @return FOV minimum en degrés (≥ 1°)
     * @see #setMinFov(double)
     * @see #getFov()
     */
    public double getMinFov() {
        return minFov;
    }

    /**
     * Définit le champ de vision minimum autorisé
     * 
     * <p>Configure la limite inférieure du zoom. La valeur est automatiquement
     * limitée à 1° minimum pour garantir une visualisation stable.</p>
     * 
     * @param minFov FOV minimum souhaité en degrés (sera limité à ≥ 1°)
     * @see #getMinFov()
     */
    public void setMinFov(double minFov) {
        // Accepter la valeur de l'utilisateur (limite raisonnable à 1° minimum)
        this.minFov = Math.max(minFov, 1);
    }

    /**
     * Retourne l'indicateur de changement de vignette
     * 
     * <p>Valeur binaire (0 ou 1) qui alterne à chaque changement de vignette,
     * permettant aux observateurs de détecter les modifications.</p>
     * 
     * @return Indicateur de changement (0 ou 1)
     * @see #setiChangeVignette()
     */
    public int getiChangeVignette() {
        return iChangeVignette;
    }

    /**
     * Bascule l'indicateur de changement de vignette
     * 
     * <p>Inverse la valeur (0→1 ou 1→0) et déclenche un événement PropertyChange
     * "changeVignette" pour notifier les observateurs qu'une nouvelle vignette
     * a été capturée.</p>
     * 
     * @see #getiChangeVignette()
     */
    public void setiChangeVignette() {
        int ancienneValeur = this.iChangeVignette;
        this.iChangeVignette = 1 - iChangeVignette;
        int nouvelleValeur = this.iChangeVignette;
        this.changeSupport.firePropertyChange("changeVignette", ancienneValeur, nouvelleValeur);
    }

    /**
     * Indique si le mode de choix de hotspot est actif
     * 
     * <p>En mode choix de hotspot, les clics dans le panorama enregistrent
     * des positions pour créer ou modifier des hotspots.</p>
     * 
     * @return true si le mode choix de hotspot est actif, false sinon
     * @see #setbChoixHotSpot(boolean)
     */
    public boolean isbChoixHotSpot() {
        return bChoixHotSpot;
    }

    /**
     * Active ou désactive le mode de choix de hotspot
     * 
     * <p>Permet de basculer entre le mode navigation normal et le mode
     * sélection de position pour les hotspots.</p>
     * 
     * @param bChoixHotSpot true pour activer le mode choix, false pour le désactiver
     * @see #isbChoixHotSpot()
     */
    public void setbChoixHotSpot(boolean bChoixHotSpot) {
        this.bChoixHotSpot = bChoixHotSpot;
    }

    /**
     * Retourne l'image vignette associée au hotspot
     * 
     * <p>Vignette capturée lors de la création du hotspot, affichée comme
     * aperçu de la direction pointée.</p>
     * 
     * @return Image vignette du hotspot, ou null si aucune
     * @see #setImgVignetteHS(Image)
     * @see #captureEcranHS()
     */
    public Image getImgVignetteHS() {
        return imgVignetteHS;
    }

    /**
     * Définit l'image vignette du hotspot
     * 
     * <p>Associe une image d'aperçu au hotspot en cours de création ou modification.</p>
     * 
     * @param imgVignetteHS Image vignette à associer
     * @see #getImgVignetteHS()
     */
    public void setImgVignetteHS(Image imgVignetteHS) {
        this.imgVignetteHS = imgVignetteHS;
    }

    /**
     * Retourne le bouton de basculement plein écran
     * 
     * @return Bouton UI permettant de passer en mode plein écran
     * @see #isbPleinEcran()
     */
    public Button getBtnPleinEcran() {
        return btnPleinEcran;
    }

    /**
     * Indique si le navigateur est en mode plein écran
     * 
     * @return true si en plein écran, false sinon
     * @see #setbPleinEcran(boolean)
     */
    public boolean isbPleinEcran() {
        return bPleinEcran;
    }

    /**
     * Active ou désactive le mode plein écran
     * 
     * @param bPleinEcran true pour activer le plein écran, false pour le désactiver
     * @see #isbPleinEcran()
     */
    public void setbPleinEcran(boolean bPleinEcran) {
        this.bPleinEcran = bPleinEcran;
    }
}
