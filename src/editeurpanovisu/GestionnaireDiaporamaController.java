/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getStrCurrentDir;
import static editeurpanovisu.EditeurPanovisu.getStrRepertAppli;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import editeurpanovisu.BigDecimalField;

/**
 *
 * @author laure_000
 */
public final class GestionnaireDiaporamaController {

    /**
     * @return the diaporama
     */
    public Diaporama getDiaporama() {
        return diaporama;
    }

    /**
     * @param aDiaporama the diaporama to set
     */
    public void setDiaporama(Diaporama aDiaporama) {
        diaporama = aDiaporama;
    }

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

    private static final ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());

    private static Diaporama diaporama = null;
    private ListView listImage = new ListView();
    public Button btnAnnulerDiaporama = new Button(rbLocalisation.getString("main.annuler"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/annule.png")));
    public final Button btnSauverDiaporama = new Button(rbLocalisation.getString("main.valider"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/valide.png", 24, 24, true, true, true)));
    public AnchorPane apDiaporama = null;
    private boolean bDisabled = true;
    private AnchorPane apDiapo1;
    public boolean diapoSauve = true;
    private ImageView ivImage;
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public void initDiaporama() {
        if (apDiaporama != null) {
            apDiaporama.getChildren().clear();
        } else {
            apDiaporama = new AnchorPane();

        }
        ivImage = new ImageView();
        final int iLargeur = 900;
        final int iHauteur = 650;
        apDiapo1 = new AnchorPane();
        apDiapo1.setPrefWidth(iLargeur);
        apDiapo1.setMinWidth(iLargeur);
        apDiapo1.setMaxWidth(iLargeur);
        apDiapo1.setPrefHeight(iHauteur - 50);
        apDiapo1.setMinHeight(iHauteur - 50);
        apDiapo1.setMaxHeight(iHauteur - 50);
        apDiaporama.setPrefWidth(iLargeur);
        apDiaporama.setMinWidth(iLargeur);
        apDiaporama.setMaxWidth(iLargeur);
        apDiaporama.setPrefHeight(iHauteur);
        apDiaporama.setMinHeight(iHauteur);
        apDiaporama.setMaxHeight(iHauteur);
        apDiaporama.setLayoutY(80);

        // === PANNEAU GAUCHE: Liste des images ===
        Label lblTitreImages = new Label(rbLocalisation.getString("diapo.listeImages"));
        lblTitreImages.setLayoutX(30);
        lblTitreImages.setLayoutY(15);
        
        listImage.getItems().clear();
        listImage.setLayoutX(30);
        listImage.setLayoutY(45);
        listImage.setPrefSize(280, 320);

        Button btnAjouteImage = new Button(rbLocalisation.getString("diapo.ajouteImage"));
        btnAjouteImage.setLayoutX(30);
        btnAjouteImage.setLayoutY(375);
        btnAjouteImage.setPrefSize(135, 35);
        
        Button btnSupprimeImage = new Button(rbLocalisation.getString("diapo.supprimeImage"));
        btnSupprimeImage.setLayoutX(175);
        btnSupprimeImage.setLayoutY(375);
        btnSupprimeImage.setPrefSize(135, 35);

        // === PANNEAU DROIT: Prévisualisation ===
        Label lblTitrePreview = new Label(rbLocalisation.getString("diapo.previsualisation"));
        lblTitrePreview.setLayoutX(350);
        lblTitrePreview.setLayoutY(15);
        
        AnchorPane apImage = new AnchorPane();
        apImage.setLayoutX(350);
        apImage.setLayoutY(45);
        apImage.setPrefSize(500, 320);

        Label lblLibImage = new Label(rbLocalisation.getString("diapo.libelleImage"));
        lblLibImage.setLayoutX(350);
        lblLibImage.setLayoutY(380);

        TextField tfLibImage = new TextField();
        tfLibImage.setMinSize(400, 30);
        tfLibImage.setPrefSize(400, 30);
        tfLibImage.setMaxSize(400, 30);
        tfLibImage.setLayoutX(450);
        tfLibImage.setLayoutY(375);
        // === PARAMETRES DU DIAPORAMA ===
        double debutOutils = 430;
        
        Label lblTitreParams = new Label(rbLocalisation.getString("diapo.parametres"));
        lblTitreParams.setLayoutX(30);
        lblTitreParams.setLayoutY(debutOutils - 10);
        
        Label lblCouleurFondDiapo = new Label(rbLocalisation.getString("diapo.couleurFond"));
        lblCouleurFondDiapo.setLayoutX(30);
        lblCouleurFondDiapo.setLayoutY(debutOutils + 25);
        ColorPicker cpCouleurFondDiapo = new ColorPicker();
        cpCouleurFondDiapo.setLayoutX(200);
        cpCouleurFondDiapo.setLayoutY(debutOutils + 20);
        cpCouleurFondDiapo.setPrefWidth(150);
        Label lblOpaciteDiapo = new Label(rbLocalisation.getString("diapo.opacite"));
        lblOpaciteDiapo.setLayoutX(390);
        lblOpaciteDiapo.setLayoutY(debutOutils + 25);
        
        Slider slOpaciteDiapo = new Slider(0.0, 1.0, 0.5);
        slOpaciteDiapo.setLayoutX(480);
        slOpaciteDiapo.setLayoutY(debutOutils + 25);
        slOpaciteDiapo.setPrefWidth(180);
        slOpaciteDiapo.setShowTickMarks(true);
        slOpaciteDiapo.setShowTickLabels(false);

        Label lblDelaiDiapo = new Label(rbLocalisation.getString("diapo.delai"));
        lblDelaiDiapo.setLayoutX(680);
        lblDelaiDiapo.setLayoutY(debutOutils + 25);
        
        BigDecimalField bdfDelaiDiapo = new BigDecimalField(BigDecimal.valueOf(3));
        bdfDelaiDiapo.setLayoutX(740);
        bdfDelaiDiapo.setLayoutY(debutOutils + 25);
        bdfDelaiDiapo.setPrefWidth(110);

        // === BOUTON VISUALISATION ===
        Button btnVisualiseDiapo = new Button(rbLocalisation.getString("diapo.visualise"));
        btnVisualiseDiapo.setLayoutX(30);
        btnVisualiseDiapo.setLayoutY(debutOutils + 65);
        btnVisualiseDiapo.setPrefSize(820, 45);

        // === BOUTONS ANNULER / VALIDER ===
        btnAnnulerDiaporama.setPrefSize(150, 40);
        btnAnnulerDiaporama.setLayoutX(iLargeur - 330);
        btnAnnulerDiaporama.setLayoutY(iHauteur - 50);
        btnSauverDiaporama.setPrefSize(150, 40);
        btnSauverDiaporama.setLayoutX(iLargeur - 170);
        btnSauverDiaporama.setLayoutY(iHauteur - 50);
        apDiapo1.getChildren().addAll(
                lblTitreImages, listImage,
                btnAjouteImage, btnSupprimeImage,
                lblTitrePreview, apImage,
                lblLibImage, tfLibImage,
                lblTitreParams,
                lblCouleurFondDiapo, cpCouleurFondDiapo,
                lblOpaciteDiapo, slOpaciteDiapo,
                lblDelaiDiapo, bdfDelaiDiapo,
                btnVisualiseDiapo
        );
        apDiapo1.setDisable(isbDisabled());
        apDiaporama.getChildren().addAll(
                apDiapo1,
                btnAnnulerDiaporama, btnSauverDiaporama
        );
        if (getDiaporama() != null) {
            for (int i = 0; i < getDiaporama().getiNombreImages(); i++) {
                listImage.getItems().add(getDiaporama().getStrFichiers(i));
            }
            cpCouleurFondDiapo.setValue(Color.valueOf(getDiaporama().getStrCouleurFondDiaporama()));
            apImage.setStyle("-fx-background-color: " + getDiaporama().getStrCouleurFondDiaporama() + ";" +
                "-fx-border-color: #34495e;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

            slOpaciteDiapo.setValue(getDiaporama().getOpaciteDiaporama());
            bdfDelaiDiapo.setNumber(BigDecimal.valueOf(getDiaporama().getDelaiDiaporama()));
        }

 
        btnAnnulerDiaporama.setOnMouseClicked((me) -> {
            boolean ancienneValeur = false;
            boolean nouvelleValeur = true;
            this.changeSupport.firePropertyChange("annuleDiapo", ancienneValeur, nouvelleValeur);
        }
        );
        btnVisualiseDiapo.setOnMouseClicked((me) -> {
            boolean ancienneValeur = false;
            boolean nouvelleValeur = true;
            this.changeSupport.firePropertyChange("visualiseDiapo", ancienneValeur, nouvelleValeur);
        });

        btnSauverDiaporama.setOnMouseClicked(
                (me) -> {
                    boolean ancienneValeur = false;
                    boolean nouvelleValeur = true;
                    this.changeSupport.firePropertyChange("valideDiapo", ancienneValeur, nouvelleValeur);
                }
        );

        cpCouleurFondDiapo.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                diapoSauve = false;
                getDiaporama().setStrCouleurFondDiaporama("#" + newValue.toString().substring(2, 8));
                apImage.setStyle("-fx-background-color: " + getDiaporama().getStrCouleurFondDiaporama() + ";" +
                    "-fx-border-color: #34495e;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 8;" +
                    "-fx-background-radius: 8;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
            }
        });

        slOpaciteDiapo.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                diapoSauve = false;
                getDiaporama().setOpaciteDiaporama(newValue.doubleValue());
            }
        });

        bdfDelaiDiapo.numberProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                diapoSauve = false;
                getDiaporama().setDelaiDiaporama(newValue.intValue());
            }
        });
        btnSupprimeImage.setOnMouseClicked(
                (me) -> {
                    ButtonType reponse = null;
                    ButtonType buttonTypeOui = new ButtonType(rbLocalisation.getString("main.oui"));
                    ButtonType buttonTypeNon = new ButtonType(rbLocalisation.getString("main.non"));
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(rbLocalisation.getString("diapo.supprimerImage"));
                    alert.setHeaderText(null);
                    alert.setContentText(rbLocalisation.getString("diapo.supprimerImageTexte"));
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);
                    Optional<ButtonType> actReponse = alert.showAndWait();
                    reponse = actReponse.get();
                    if (reponse == buttonTypeOui) {
                        int iImage = listImage.getSelectionModel().getSelectedIndex();
                        for (int i = iImage; i < getDiaporama().getiNombreImages() - 1; i++) {
                            getDiaporama().setStrFichiers(getDiaporama().getStrFichiers(i + 1), i);
                            getDiaporama().setStrFichiersImage(getDiaporama().getStrFichiersImage(i + 1), i);
                            getDiaporama().setStrLibellesImages(getDiaporama().getStrLibellesImages(i + 1), i);
                        }
                        getDiaporama().setiNombreImages(getDiaporama().getiNombreImages() - 1);
                        listImage.getItems().clear();
                        for (int i = 0; i < getDiaporama().getiNombreImages(); i++) {
                            listImage.getItems().add(getDiaporama().getStrFichiers(i));
                        }
                        apImage.getChildren().clear();
                    }

                });
        btnAjouteImage.setOnMouseClicked(
                (me) -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterImage = new FileChooser.ExtensionFilter("Fichiers Image (JPG,BMP,TIFF)", "*.jpg", "*.bmp", "*.tif");
                    FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
                    FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
                    FileChooser.ExtensionFilter extFilterTiff = new FileChooser.ExtensionFilter("Fichiers TIFF (*.tif)", "*.tif");
                    File fileRepert = new File(getStrCurrentDir() + File.separator);
                    fileChooser.setInitialDirectory(fileRepert);
                    fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterTiff, extFilterBmp, extFilterImage);

                    List<File> listFichiers = fileChooser.showOpenMultipleDialog(null);
                    if (listFichiers != null) {
                        diapoSauve = false;
                        int i = diaporama.getiNombreImages();
                        for (File fileFichier : listFichiers) {
                            diaporama.setStrFichiersImage(fileFichier.toString(), i);
                            String strFichier
                            = fileFichier.toString().substring(
                                    fileFichier.toString().lastIndexOf(File.separator) + 1,
                                    fileFichier.toString().length()
                            );
                            listImage.getItems().add(strFichier);
                            diaporama.setStrFichiers(strFichier, i);
                            diaporama.setStrLibellesImages("", i);
                            diaporama.setiNombreImages(diaporama.getiNombreImages() + 1);
                            i++;
                        }

                    }
                });
        listImage.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if ((int) newValue != -1) {
                tfLibImage.setText(getDiaporama().getStrLibellesImages((int) newValue));
                apImage.getChildren().clear();
                Image imgDiapo = new Image("file:" + getDiaporama().getStrFichiersImage((int) newValue));
                double largImg = imgDiapo.getWidth();
                double hautImg = imgDiapo.getHeight();
                ivImage = new ImageView(imgDiapo);
                ivImage.setPreserveRatio(true);
                ivImage.setLayoutX(0);
                ivImage.setLayoutY(0);

                if (hautImg >= largImg) {
                    ivImage.setFitHeight(300);
                    double largImg1 = largImg * 300 / hautImg;
                    if (largImg1 > 450) {
                        ivImage.setFitHeight(450);
                        double hautImg1 = hautImg * 450 / largImg;
                        ivImage.setLayoutY(150 - hautImg1 / 2);
                    } else {
                        ivImage.setLayoutX(225 - largImg1 / 2);
                    }
                } else {
                    ivImage.setFitWidth(450);
                    double hautImg1 = hautImg * 450 / largImg;
                    if (hautImg1 > 300) {
                        ivImage.setFitHeight(300);
                        double largImg1 = largImg * 300 / hautImg;
                        ivImage.setLayoutX(225 - largImg1 / 2);
                    } else {
                        ivImage.setLayoutY(150 - hautImg1 / 2);
                    }
                }
                apImage.getChildren().add(ivImage);
            }
        });

        tfLibImage.textProperty().addListener((ov, oldValue, newValue) -> {
            int iImage = listImage.getSelectionModel().getSelectedIndex();
            getDiaporama().setStrLibellesImages(newValue, iImage);
        });

    }

    public void reInit(Diaporama nouveauDiapo) {
        setDiaporama((Diaporama) nouveauDiapo.clone());
        initDiaporama();
    }

    /**
     * @return the bDisabled
     */
    public boolean isbDisabled() {
        return bDisabled;
    }

    /**
     * @param bDisabled the bDisabled to set
     */
    public void setbDisabled(boolean bDisabled) {
        apDiapo1.setDisable(bDisabled);
        this.bDisabled = bDisabled;
    }

}
