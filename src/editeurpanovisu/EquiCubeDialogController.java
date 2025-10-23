/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.commons.imaging.ImagingException;

/**
 * Controleur pour l'affichage des transformations cube / Equi
 *
 * @author LANG Laurent
 */
public class EquiCubeDialogController {

    private static final ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());

    private static Stage stTransformations;
    private static AnchorPane apTransformations;
    private String strTypeTransformation = "";
    static private final ListView<String> lvListeFichier = new ListView<String>();
    public static final ProgressBar pbBarreImage = new ProgressBar();
    static private Button btnAnnuler;
    static private Button btnValider;
    static private Button btnAjouteFichiers;
    static private Pane paneChoixTypeFichier;
    static private boolean bTraitementEffectue = false;
    static private RadioButton rbJpeg;
    static private RadioButton rbBmp;
    static private RadioButton rbTiff;
    static private CheckBox cbSharpen;
    static private Slider slSharpen;
    static private Label lblSharpen;
    static private ProgressBar pbBarreAvancement;
    static private Label lblTermine = new Label();
    private Label lblDragDropE2C;

    final ToggleGroup tgTypeFichier = new ToggleGroup();
    /**
     * Constante de type de transfrormation Equi / Cube
     */
    public final static String EQUI2CUBE = "E2C";
    /**
     * Constante de type de transfrormation Cube / Equi
     */
    public final static String CUBE2QUI = "C2E";
    private File[] fileLstFichier;
    private static String strRepertFichier = EditeurPanovisu.getStrRepertoireProjet();

    /**
     *
     */
    private void annulerE2C() {

        ButtonType reponse = null;
        ButtonType buttonTypeOui = new ButtonType(rbLocalisation.getString("main.oui"));
        ButtonType buttonTypeNon = new ButtonType(rbLocalisation.getString("main.non"));
        if ((lvListeFichier.getItems().size() != 0) && (!bTraitementEffectue)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(rbLocalisation.getString("transformation.traiteImages"));
            alert.setHeaderText(null);
            alert.setContentText(rbLocalisation.getString("transformation.traiteImagesQuitte"));
            alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);
            Optional<ButtonType> actReponse = alert.showAndWait();
            reponse = actReponse.get();

        }
        if ((reponse == buttonTypeOui) || (reponse == null)) {
            bTraitementEffectue = false;
            stTransformations.hide();
        }
    }

    /**
     *
     * @param nomFichier
     * @param j
     * @throws InterruptedException Exception interruption
     */
    private void traiteFichier(String nomFichier, int j) throws InterruptedException {
        if (strTypeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
            String strNomFich1 = nomFichier.substring(0, nomFichier.length() - 4);
            String strExtension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1, nomFichier.length()).toLowerCase();
            Image imgEquiImage = null;
            if (!"tif".equals(strExtension)) {
                imgEquiImage = new Image("file:" + nomFichier);
            } else {
                try {
                    imgEquiImage = ReadWriteImage.readTiff(nomFichier);
                } catch (ImagingException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image[] imgFacesCube = TransformationsPanoramique.equi2cubeAuto(imgEquiImage, -1);
            for (int i = 0; i < 6; i++) {
                String strSuffixe = "";
                int faceIndex = i;
                switch (i) {
                    case 0:
                        strSuffixe = "_f";
                        break;
                    case 1:
                        strSuffixe = "_b";
                        break;
                    case 2:
                        strSuffixe = "_r";
                        break;
                    case 3:
                        strSuffixe = "_l";
                        break;
                    case 4:
                        strSuffixe = "_u";
                        faceIndex = 5;  // Sauver Bottom comme Up
                        break;
                    case 5:
                        strSuffixe = "_d";
                        faceIndex = 4;  // Sauver Top comme Down
                        break;
                }
                try {
                    //ReadWriteImage.writeJpeg(facesCube[i], "c:/panoramiques/test/" + txtImage + "_cube" + suffixe + ".jpg", jpegQuality);
                    boolean bSharpen = false;
                    if (cbSharpen.isSelected()) {
                        bSharpen = true;
                    }

                    if (rbBmp.isSelected()) {
                        ReadWriteImage.writeBMP(imgFacesCube[faceIndex], strNomFich1 + "_cube" + strSuffixe + ".bmp",
                                bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                    }
                    if (rbTiff.isSelected()) {
                        try {
                            ReadWriteImage.writeTiff(imgFacesCube[faceIndex], strNomFich1 + "_cube" + strSuffixe + ".tif",
                                    bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                        } catch (ImagingException ex) {
                            Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (rbJpeg.isSelected()) {
                        float quality = 1.0f; //qualité jpeg à 100% : le moins de pertes possible
                        ReadWriteImage.writeJpeg(imgFacesCube[faceIndex], strNomFich1 + "_cube" + strSuffixe + ".jpg", quality,
                                bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        if (strTypeTransformation.equals(EquiCubeDialogController.CUBE2QUI)) {
            String strNom = nomFichier.substring(0, nomFichier.length() - 6);
            String strExtension = nomFichier.substring(nomFichier.length() - 3, nomFichier.length());
            Image imgTop = null;
            Image imgBottom = null;
            Image imgLeft = null;
            Image imgRight = null;
            Image imgFront = null;
            Image imgBehind = null;

            if (strExtension.equals("bmp")) {
                imgTop = new Image("file:" + strNom + "_u.bmp");
                imgBottom = new Image("file:" + strNom + "_d.bmp");
                imgLeft = new Image("file:" + strNom + "_l.bmp");
                imgRight = new Image("file:" + strNom + "_r.bmp");
                imgFront = new Image("file:" + strNom + "_f.bmp");
                imgBehind = new Image("file:" + strNom + "_b.bmp");
            }
            if (strExtension.equals("jpg")) {
                imgTop = new Image("file:" + strNom + "_u.jpg");
                imgBottom = new Image("file:" + strNom + "_d.jpg");
                imgLeft = new Image("file:" + strNom + "_l.jpg");
                imgRight = new Image("file:" + strNom + "_r.jpg");
                imgFront = new Image("file:" + strNom + "_f.jpg");
                imgBehind = new Image("file:" + strNom + "_b.jpg");
            }
            if (strExtension.equals("tif")) {
                try {
                    imgTop = ReadWriteImage.readTiff(strNom + "_u.tif");
                    imgBottom = ReadWriteImage.readTiff(strNom + "_d.tif");
                    imgLeft = ReadWriteImage.readTiff(strNom + "_l.tif");
                    imgRight = ReadWriteImage.readTiff(strNom + "_r.tif");
                    imgFront = ReadWriteImage.readTiff(strNom + "_f.tif");
                    imgBehind = ReadWriteImage.readTiff(strNom + "_b.tif");
                } catch (ImagingException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Image imgEquiRectangulaire = TransformationsPanoramique.cube2rect(imgFront, imgLeft, imgRight, imgBehind, imgTop, imgBottom, -1);
            try {
                //ReadWriteImage.writeJpeg(facesCube[i], "c:/panoramiques/test/" + txtImage + "_cube" + suffixe + ".jpg", jpegQuality);
                boolean bSharpen = false;
                if (cbSharpen.isSelected()) {
                    bSharpen = true;
                }

                if (rbBmp.isSelected()) {
                    EditeurPanovisu.setStrTypeFichierTransf("bmp");
                    ReadWriteImage.writeBMP(imgEquiRectangulaire, strNom + "_sphere.bmp", bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                }
                if (rbTiff.isSelected()) {
                    EditeurPanovisu.setStrTypeFichierTransf("tif");
                    try {
                        ReadWriteImage.writeTiff(imgEquiRectangulaire, strNom + "_sphere.tif", bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                    } catch (ImagingException ex) {
                        Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rbJpeg.isSelected()) {
                    EditeurPanovisu.setStrTypeFichierTransf("jpg");
                    float quality = 1.0f;
                    ReadWriteImage.writeJpeg(imgEquiRectangulaire, strNom + "_sphere.jpg", quality, bSharpen, (float) Math.round(slSharpen.getValue() * 20.f) / 20.f);
                }
            } catch (IOException ex) {
                Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     *
     */
    private void validerE2C() {
        if (fileLstFichier == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(rbLocalisation.getString("transformation.traiteImages"));
            alert.setHeaderText(null);
            alert.setContentText(rbLocalisation.getString("transformation.traiteImagesPasFichiers"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(rbLocalisation.getString("transformation.traiteImages"));
            alert.setHeaderText(null);
            alert.setContentText(rbLocalisation.getString("transformation.traiteImagesMessage"));
            alert.showAndWait();

            lblTermine = new Label();
            lblTermine.setText("Traitement en cours");
            lblTermine.setLayoutX(24);
            lblTermine.setLayoutY(250);
            paneChoixTypeFichier.getChildren().add(lblTermine);
            pbBarreAvancement.setId("bar");
            lblTermine.setId("lblTermine");
            pbBarreAvancement.setVisible(true);
            pbBarreImage.setVisible(true);
            Task<Boolean> taskTraitement;
            taskTraitement = tskTraitement();
            pbBarreAvancement.progressProperty().unbind();
            pbBarreImage.setProgress(0.001);
            pbBarreAvancement.setProgress(0.001);
            pbBarreAvancement.progressProperty().bind(taskTraitement.progressProperty());
            lblTermine.textProperty().unbind();
            lblTermine.textProperty().bind(taskTraitement.messageProperty());
            Thread thrTraitement = new Thread(taskTraitement);
            thrTraitement.setDaemon(true);
            thrTraitement.start();
        }
    }

    public Task<Boolean> tskTraitement() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                apTransformations.setCursor(Cursor.WAIT);
                Platform.runLater(() -> {
                    for (int ij = 0; ij < lvListeFichier.getItems().size(); ij++) {
                        String strNomFich1 = (String) lvListeFichier.getItems().get(ij);
                        lvListeFichier.getItems().set(ij, "A Traiter => " + strNomFich1);
                    }
                });
                updateProgress(0.0001f, lvListeFichier.getItems().size());
                for (int i1 = 0; i1 < lvListeFichier.getItems().size(); i1++) {
                    Thread.sleep(200);
                    updateMessage("Traitement en cours " + (i1 + 1) + "/" + lvListeFichier.getItems().size());
                    String strNomFich = ((String) lvListeFichier.getItems().get(i1)).split("> ")[1];
                    final int ii = i1;
                    Platform.runLater(() -> {
                        lvListeFichier.getItems().set(ii, "Traitement en cours => " + strNomFich);
                    });
                    traiteFichier(strNomFich, i1);
                    Thread.sleep(100);

                    updateProgress(i1 + 1, lvListeFichier.getItems().size());
                    Platform.runLater(() -> {
                        lvListeFichier.getItems().set(ii, "Traité => " + strNomFich);
                    });
                    Thread.sleep(100);
                }
                apTransformations.setCursor(Cursor.DEFAULT);
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                pbBarreAvancement.progressProperty().unbind();
                lblTermine.textProperty().unbind();
                lblTermine.setText("Traitement Terminé");
                pbBarreAvancement.setProgress(0.001d);
                pbBarreAvancement.setVisible(false);
                pbBarreImage.setVisible(false);
                bTraitementEffectue = true;
                lblDragDropE2C.setVisible(true);
            }

        };
    }

    /**
     *
     * @return liste des fichiers
     */
    private File[] choixFichiers() {
        File[] fileLstFich = null;
        FileChooser fcRepertChoix = new FileChooser();
        fcRepertChoix.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("fichier Image (*.jpg|*.bmp|*.tif)", "*.jpg", "*.bmp", "*.tif")
        );
        File fileRepert = new File(strRepertFichier + File.separator);
        fcRepertChoix.setInitialDirectory(fileRepert);
        List<File> fileListe = fcRepertChoix.showOpenMultipleDialog(stTransformations);
        int i = 0;
        boolean bAttention = false;
        if (fileListe != null) {
            File[] fileLstFich1 = new File[fileListe.size()];
            for (File fileLst : fileListe) {
                if (i == 0) {
                    strRepertFichier = fileLst.getParent();
                }
                String strNomFich = fileLst.getAbsolutePath();
                String strExtension;
                strExtension = strNomFich.substring(strNomFich.lastIndexOf(".") + 1, strNomFich.length()).toLowerCase();
                Image img = null;
                if (!strExtension.equals("tif")) {
                    img = new Image("file:" + strNomFich);
                } else {
                    try {
                        img = ReadWriteImage.readTiff(strNomFich);
                    } catch (IOException ex) {
                        Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (strTypeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
                    if (img.getWidth() == 2 * img.getHeight()) {
                        fileLstFich1[i] = fileLst;
                        i++;
                    } else {
                        bAttention = true;
                    }
                } else {
                    if (img.getWidth() == img.getHeight()) {
                        String strNom = fileLst.getAbsolutePath().substring(0, fileLst.getAbsolutePath().length() - 6);
                        boolean bTrouve = false;
                        for (int j = 0; j < i; j++) {
                            String strNom1 = fileLstFich1[j].getAbsolutePath().substring(0, fileLst.getAbsolutePath().length() - 6);
                            if (strNom.equals(strNom1)) {
                                bTrouve = true;
                            }
                        }
                        if (!bTrouve) {
                            fileLstFich1[i] = fileLst;
                            i++;
                        }
                    } else {
                        bAttention = true;
                    }

                }
            }
            if (bAttention) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(rbLocalisation.getString("transformation.traiteImages"));
                alert.setHeaderText(null);
                alert.setContentText(rbLocalisation.getString("transformation.traiteImagesType"));
                alert.showAndWait();
            }
            fileLstFich = new File[i];
            System.arraycopy(fileLstFich1, 0, fileLstFich, 0, i);
            lblDragDropE2C.setVisible(false);
        }

        return fileLstFich;
    }

    /**
     * Colorisation de la ListView des images à transformer
     *
     * @author LANG Laurent
     */
    static class ListeTransformationCouleur extends ListCell<String> {

        @Override
        public void updateItem(String strItem, boolean bEmpty) {
            super.updateItem(strItem, bEmpty);
            if (strItem != null) {
                int iLongueur = strItem.split(" => ").length;
                setTextFill(Color.BLACK);
                String strPre = "";
                String strTexte = strItem;
                if (iLongueur > 1) {
                    String[] strTxt = strItem.split(" => ");
                    switch (strTxt[0]) {
                        case "A Traiter":
                            setTextFill(Color.RED);
                            break;
                        case "Traitement en cours":
                            setTextFill(Color.BLUE);
                            break;
                        case "Traité":
                            setTextFill(Color.GREEN);
                            break;
                    }
                    strPre = strTxt[0] + " => ";
                    strTexte = strTxt[1];
                }
                strTexte = strTexte.substring(strTexte.lastIndexOf(File.separator) + 1, strTexte.length());
                setText(strPre + strTexte);
            }
        }
    }

    /**
     *
     * @param strTypeTransf
     * @throws Exception Exceptions
     */
    @SuppressWarnings("unused")
    public void afficheFenetre(String strTypeTransf) throws Exception {
        lvListeFichier.getItems().clear();
        stTransformations = new Stage(StageStyle.UTILITY);
        apTransformations = new AnchorPane();
        stTransformations.initModality(Modality.APPLICATION_MODAL);
        stTransformations.setResizable(true);
        //apTransformations.setStyle("-fx-background-color : #434343ff;");

        // === Architecture simplifiée avec VBox/HBox ===
        VBox vbFenetre = new VBox(15); // 15px d'espacement entre les éléments
        vbFenetre.setPadding(new javafx.geometry.Insets(15, 15, 15, 15)); // 15px de padding tout autour
        
        // Panneau horizontal principal (liste fichiers + options)
        HBox hbChoix = new HBox(15); // 15px d'espacement entre colonnes
        HBox.setHgrow(hbChoix, javafx.scene.layout.Priority.ALWAYS);
        
        // === COLONNE GAUCHE : Liste des fichiers ===
        VBox vbFichiers = new VBox(10);
        vbFichiers.setPadding(new javafx.geometry.Insets(10));
        vbFichiers.getStyleClass().add("dialog-content-pane");
        vbFichiers.setPrefWidth(420);
        VBox.setVgrow(vbFichiers, javafx.scene.layout.Priority.ALWAYS);
        
        lvListeFichier.setPrefHeight(250);
        lvListeFichier.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(lvListeFichier, javafx.scene.layout.Priority.ALWAYS);
        
        btnAjouteFichiers = new Button("Ajouter des Fichiers");
        btnAjouteFichiers.setPrefWidth(180);
        
        vbFichiers.getChildren().addAll(lvListeFichier, btnAjouteFichiers);
        
        // === COLONNE DROITE : Options ===
        VBox vbOptions = new VBox(10);
        vbOptions.setPadding(new javafx.geometry.Insets(10));
        vbOptions.getStyleClass().add("dialog-content-pane");
        vbOptions.setPrefWidth(200);
        
        // Assigner vbOptions à la variable statique pour compatibilité avec le code existant
        paneChoixTypeFichier = vbOptions;
        
        Label lblType = new Label("Type des Fichiers de sortie");
        lblType.setWrapText(true);
        lblType.setStyle("-fx-font-weight: bold;");
        
        rbJpeg = new RadioButton("JPEG (.jpg)");
        rbBmp = new RadioButton("BMP (.bmp)");
        rbTiff = new RadioButton("TIFF (.tif)");
        
        cbSharpen = new CheckBox("Masque de netteté");
        cbSharpen.setSelected(EditeurPanovisu.isbNetteteTransf());
        
        slSharpen = new Slider(0, 2, EditeurPanovisu.getNiveauNetteteTransf());
        lblSharpen = new Label();
        double lbl = (Math.round(EditeurPanovisu.getNiveauNetteteTransf() * 20.d) / 20.d);
        lblSharpen.setText(lbl + "");
        slSharpen.setDisable(!EditeurPanovisu.isbNetteteTransf());
        lblSharpen.setDisable(!EditeurPanovisu.isbNetteteTransf());
        
        // === PANNEAU BOUTONS BAS ===
        HBox hbBoutons = new HBox(15);
        hbBoutons.setPadding(new javafx.geometry.Insets(10));
        hbBoutons.setAlignment(javafx.geometry.Pos.CENTER);
        
        btnAnnuler = new Button("Fermer la fenêtre");
        btnAnnuler.setPrefWidth(150);
        btnValider = new Button("Lancer le traitement");
        btnValider.setPrefWidth(180);

        strTypeTransformation = strTypeTransf;
        Image imgTransf;
        if (strTypeTransf.equals(EquiCubeDialogController.EQUI2CUBE)) {
            stTransformations.setTitle("Transformation d'équirectangulaire en faces de cube");
            imgTransf = new Image("file:" + EditeurPanovisu.getStrRepertAppli() + File.separator + "images/equi2cube.png");
        } else {
            stTransformations.setTitle("Transformation de faces de cube en équirectangulaire");
            imgTransf = new Image("file:" + EditeurPanovisu.getStrRepertAppli() + File.separator + "images/cube2equi.png");
        }
        // Label drag & drop pour la liste de fichiers
        if (strTypeTransf.equals(EquiCubeDialogController.EQUI2CUBE)) {
            lblDragDropE2C = new Label(rbLocalisation.getString("transformation.dragDropE2C"));
        } else {
            lblDragDropE2C = new Label(rbLocalisation.getString("transformation.dragDropC2E"));
        }
        lblDragDropE2C.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lblDragDropE2C.setAlignment(Pos.CENTER);
        lblDragDropE2C.setTextFill(Color.web("#c9c7c7"));
        lblDragDropE2C.setTextAlignment(TextAlignment.CENTER);
        lblDragDropE2C.setWrapText(true);
        lblDragDropE2C.setStyle("-fx-font-size: 24px; -fx-background-color: rgba(128,128,128,0.1);");
        
        // Ajout du label drag & drop en superposition avec la liste
        javafx.scene.layout.StackPane stackListeFichiers = new javafx.scene.layout.StackPane();
        stackListeFichiers.getChildren().addAll(lvListeFichier, lblDragDropE2C);
        VBox.setVgrow(stackListeFichiers, javafx.scene.layout.Priority.ALWAYS);
        vbFichiers.getChildren().clear();
        vbFichiers.getChildren().addAll(stackListeFichiers, btnAjouteFichiers);
        
        // Configuration des RadioButtons (Type de fichier)
        rbBmp.setUserData("bmp");
        rbJpeg.setUserData("jpg");
        rbTiff.setUserData("tif");
        
        if (EditeurPanovisu.getStrTypeFichierTransf().equals("bmp")) {
            rbBmp.setSelected(true);
        } else if (EditeurPanovisu.getStrTypeFichierTransf().equals("jpg")) {
            rbJpeg.setSelected(true);
        } else if (EditeurPanovisu.getStrTypeFichierTransf().equals("tif")) {
            rbTiff.setSelected(true);
        }
        
        rbBmp.setToggleGroup(tgTypeFichier);
        rbJpeg.setToggleGroup(tgTypeFichier);
        rbTiff.setToggleGroup(tgTypeFichier);
        
        tgTypeFichier.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (new_toggle != null) {
                EditeurPanovisu.setStrTypeFichierTransf(tgTypeFichier.getSelectedToggle().getUserData().toString());
            }
        });
        
        // Configuration du Slider (Masque de netteté)
        cbSharpen.selectedProperty().addListener((ov, old_val, new_val) -> {
            slSharpen.setDisable(!new_val);
            lblSharpen.setDisable(!new_val);
            EditeurPanovisu.setbNetteteTransf(new_val);
        });
        
        slSharpen.setShowTickMarks(true);
        slSharpen.setShowTickLabels(true);
        slSharpen.setMajorTickUnit(0.5f);
        slSharpen.setMinorTickCount(4);
        slSharpen.setBlockIncrement(0.05f);
        slSharpen.setSnapToTicks(true);
        slSharpen.setPrefWidth(180);
        slSharpen.setTooltip(new Tooltip("Choisissez le niveau d'accentuation de l'image"));
        slSharpen.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                lblSharpen.setText("");
                return;
            }
            DecimalFormat dfArrondi = new DecimalFormat();
            dfArrondi.setMaximumFractionDigits(2);
            dfArrondi.setMinimumFractionDigits(2);
            dfArrondi.setDecimalSeparatorAlwaysShown(true);
            lblSharpen.setText(dfArrondi.format(Math.round(newValue.floatValue() * 20.f) / 20.f) + "");
            EditeurPanovisu.setNiveauNetteteTransf(newValue.doubleValue());
        });
        
        HBox hbSlider = new HBox(10);
        hbSlider.setAlignment(Pos.CENTER_LEFT);
        hbSlider.getChildren().addAll(slSharpen, lblSharpen);
        
        // Barres de progression
        pbBarreImage.setStyle("-fx-accent: #0000bb;");
        pbBarreImage.setVisible(false);
        pbBarreImage.setMaxWidth(Double.MAX_VALUE);
        
        pbBarreAvancement = new ProgressBar();
        pbBarreAvancement.setStyle("-fx-accent: #00bb00;");
        pbBarreAvancement.setVisible(false);
        pbBarreAvancement.setMaxWidth(Double.MAX_VALUE);
        
        // Image du type de transformation
        ImageView ivTypeTransfert = new ImageView(imgTransf);
        ivTypeTransfert.setFitWidth(150);
        ivTypeTransfert.setPreserveRatio(true);
        
        // Assemblage de la colonne OPTIONS
        vbOptions.getChildren().addAll(
            lblType,
            rbBmp,
            rbJpeg,
            rbTiff,
            new javafx.scene.layout.Region(), // Séparateur
            cbSharpen,
            hbSlider,
            new javafx.scene.layout.Region(), // Séparateur
            pbBarreImage,
            pbBarreAvancement,
            new javafx.scene.layout.Region(), // Séparateur flexible
            ivTypeTransfert
        );
        // Faire en sorte que l'image soit poussée vers le bas
        VBox.setVgrow(vbOptions.getChildren().get(vbOptions.getChildren().size() - 2), javafx.scene.layout.Priority.ALWAYS);
        
        // Assemblage final
        hbChoix.getChildren().addAll(vbFichiers, vbOptions);
        hbBoutons.getChildren().addAll(btnAnnuler, btnValider);
        vbFenetre.getChildren().addAll(hbChoix, hbBoutons);
        
        apTransformations.setPrefSize(650, 420);
        apTransformations.setMaxSize(650, 420);
        apTransformations.getChildren().add(vbFenetre);
        
        Scene scnTransformations = new Scene(apTransformations);
        // Appliquer le thème actuel au dialogue
        editeurpanovisu.ThemeManager.applyTheme(scnTransformations, editeurpanovisu.ThemeManager.getCurrentTheme());
        
        // Appliquer le style de fond approprié à la fenêtre principale
        apTransformations.getStyleClass().add("dialog-content-pane");
        stTransformations.setScene(scnTransformations);
        stTransformations.show();

        btnAnnuler.setOnAction((e) -> {
            annulerE2C();
        });
        btnValider.setOnAction((e) -> {
            if (!bTraitementEffectue) {
                validerE2C();
            }
        });
        btnAjouteFichiers.setOnAction((e) -> {
            lblTermine.setText("");
            fileLstFichier = choixFichiers();
            if (fileLstFichier != null) {
                if (bTraitementEffectue) {
                    lvListeFichier.getItems().clear();
                    bTraitementEffectue = false;
                }
                for (File fileLstFichier1 : fileLstFichier) {
                    String strNomFich = fileLstFichier1.getAbsolutePath();
                    lvListeFichier.getItems().add(strNomFich);
                }
            }
        });
        lvListeFichier.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ListeTransformationCouleur();
            }
        });
        apTransformations.setOnDragOver((event) -> {
            Dragboard dbFichiersTransformation = event.getDragboard();
            if (dbFichiersTransformation.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            } else {
                event.consume();
            }
        });
        // Listeners simplifiés pour le redimensionnement (sauvegarde des préférences)
        stTransformations.widthProperty().addListener((obs, oldVal, newVal) -> {
            EditeurPanovisu.setLargeurE2C(newVal.doubleValue());
        });
        
        stTransformations.heightProperty().addListener((obs, oldVal, newVal) -> {
            EditeurPanovisu.setHauteurE2C(newVal.doubleValue());
        });
        
        stTransformations.setMaxWidth(EditeurPanovisu.getLargeurE2C());
        stTransformations.setMaxHeight(EditeurPanovisu.getHauteurE2C());
        apTransformations.setOnDragDropped((event) -> {
            Dragboard dbFichiersTransformation = event.getDragboard();
            boolean bSucces = false;
            @SuppressWarnings("unused")
            File[] fileLstFich;
            fileLstFich = null;
            if (dbFichiersTransformation.hasFiles()) {
                lblTermine.setText("");
                bSucces = true;
                String[] stringFichiersPath = new String[200];
                int i = 0;
                for (File file1 : dbFichiersTransformation.getFiles()) {
                    stringFichiersPath[i] = file1.getAbsolutePath();
                    i++;
                }
                int iNb = i;
                i = 0;
                boolean bAttention = false;
                File[] fileLstFich1 = new File[stringFichiersPath.length];
                for (int j = 0; j < iNb; j++) {

                    String strNomfich = stringFichiersPath[j];
                    File fileTransf = new File(strNomfich);
                    String strExtension = strNomfich.substring(strNomfich.lastIndexOf(".") + 1, strNomfich.length()).toLowerCase();
                    if (strExtension.equals("bmp") || strExtension.equals("jpg") || strExtension.equals("tif")) {
                        if (i == 0) {
                            strRepertFichier = fileTransf.getParent();
                        }
                        Image img = null;
                        if (strExtension != "tif") {
                            img = new Image("file:" + fileTransf.getAbsolutePath());
                        } else {
                            try {
                                img = ReadWriteImage.readTiff(strNomfich);
                            } catch (ImagingException ex) {
                                Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (strTypeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
                            if (img.getWidth() == 2 * img.getHeight()) {
                                fileLstFich1[i] = fileTransf;
                                i++;
                            } else {
                                bAttention = true;
                            }
                        } else {
                            if (img.getWidth() == img.getHeight()) {
                                String strNom = fileTransf.getAbsolutePath().substring(0, fileTransf.getAbsolutePath().length() - 6);
                                boolean bTrouve = false;
                                for (int ik = 0; ik < i; ik++) {
                                    String strNom1 = fileLstFich1[ik].getAbsolutePath().substring(0, fileTransf.getAbsolutePath().length() - 6);
                                    if (strNom.equals(strNom1)) {
                                        bTrouve = true;
                                    }
                                }
                                if (!bTrouve) {
                                    fileLstFich1[i] = fileTransf;
                                    i++;
                                }
                            } else {
                                bAttention = true;
                            }

                        }
                    }
                }
                if (bAttention) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(rbLocalisation.getString("transformation.traiteImages"));
                    alert.setHeaderText(null);
                    alert.setContentText(rbLocalisation.getString("transformation.traiteImagesType"));
                    alert.showAndWait();
                }
                fileLstFichier = new File[i];
                System.arraycopy(fileLstFich1, 0, fileLstFichier, 0, i);
            }
            if (fileLstFichier != null) {
                if (bTraitementEffectue) {
                    lvListeFichier.getItems().clear();
                    bTraitementEffectue = false;
                }
                for (File lstFichier1 : fileLstFichier) {
                    String nomFich = lstFichier1.getAbsolutePath();
                    lvListeFichier.getItems().add(nomFich);
                }
            }
            lblDragDropE2C.setVisible(false);
            event.setDropCompleted(bSucces);
            event.consume();
        }
        );

    }

}
