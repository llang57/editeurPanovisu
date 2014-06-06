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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author LANG Laurent
 */
public class EquiCubeDialogController {

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);

    private static Stage STEqui2Cube;
    private static AnchorPane myPane;
    private String typeTransformation;
    static private ListView listeFichier;
    static private Button btnAnnuler;
    static private Button btnValider;
    static private Button btnAjouteFichiers;
    static private Pane choixTypeFichier;
    static private boolean traitementEffectue = false;
    static private RadioButton RBJpeg;
    static private RadioButton RBBmp;
    static private CheckBox CBSharpen;
    static private Slider SLSharpen;
    static private Label LBLSharpen;

    final ToggleGroup grpTypeFichier = new ToggleGroup();
    public final static String EQUI2CUBE = "E2C";
    public final static String CUBE2QUI = "C2E";
    private File[] lstFichier;
    private static String repertFichier = EditeurPanovisu.repertoireProjet;

    /**
     *
     */
    private void annulerE2C() {
        Action reponse = null;

        if ((listeFichier.getItems().size() != 0) && (!traitementEffectue)) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Transformation d'images")
                    .masthead("vous n'avez pas traité vos images")
                    .message("Êtes vous sûr de vouloir quitter?")
                    .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                    .showConfirm();
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == null)) {
            STEqui2Cube.hide();
        }
    }

    /**
     *
     * @param nomFichier
     * @param j
     */
    private void traiteFichier(String nomFichier, int j) {
        listeFichier.getItems().remove(j);
        listeFichier.getItems().add(j, "traité : " + nomFichier);

        if (typeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
            String nomFich1 = nomFichier.substring(0, nomFichier.length() - 4);
            Image equiImage = new Image("file:" + nomFichier);

            Image[] facesCube = TransformationsPanoramique.equi2cube(equiImage, -1);
            for (int i = 0; i < 6; i++) {
                String suffixe = "";
                switch (i) {
                    case 0:
                        suffixe = "_f";
                        break;
                    case 1:
                        suffixe = "_b";
                        break;
                    case 2:
                        suffixe = "_r";
                        break;
                    case 3:
                        suffixe = "_l";
                        break;
                    case 4:
                        suffixe = "_u";
                        break;
                    case 5:
                        suffixe = "_d";
                        break;
                }
                try {
                    //ReadWriteImage.writeJpeg(facesCube[i], "c:/panoramiques/test/" + txtImage + "_cube" + suffixe + ".jpg", jpegQuality);
                    boolean sharpen = false;
                    if (CBSharpen.isSelected()) {
                        sharpen = true;
                    }

                    if (RBBmp.isSelected()) {
                        ReadWriteImage.writeBMP(facesCube[i], nomFich1 + "_cube" + suffixe + ".bmp",
                                sharpen, (float) Math.round(SLSharpen.getValue() * 20.f) / 20.f);
                    }
                    if (RBJpeg.isSelected()) {
                        float quality = 1.0f; //qualité jpeg à 100% : le moins de pertes possible
                        ReadWriteImage.writeJpeg(facesCube[i], nomFich1 + "_cube" + suffixe + ".jpg", quality,
                                sharpen, (float) Math.round(SLSharpen.getValue() * 20.f) / 20.f);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        if (typeTransformation.equals(EquiCubeDialogController.CUBE2QUI)) {
            String nom = nomFichier.substring(0, nomFichier.length() - 6);
            String extension = nomFichier.substring(nomFichier.length() - 3, nomFichier.length());
            Image top;
            Image bottom;
            Image left;
            Image right;
            Image front;
            Image behind;

            if (extension.equals("bmp")) {
                top = new Image("file:" + nom + "_u.bmp");
                bottom = new Image("file:" + nom + "_d.bmp");
                left = new Image("file:" + nom + "_l.bmp");
                right = new Image("file:" + nom + "_r.bmp");
                front = new Image("file:" + nom + "_f.bmp");
                behind = new Image("file:" + nom + "_b.bmp");
            } else {
                top = new Image("file:" + nom + "_u.jpg");
                bottom = new Image("file:" + nom + "_d.jpg");

                left = new Image("file:" + nom + "_l.jpg");
                right = new Image("file:" + nom + "_r.jpg");
                front = new Image("file:" + nom + "_f.jpg");
                behind = new Image("file:" + nom + "_b.jpg");
            }
            Image equiRectangulaire = TransformationsPanoramique.cube2rect(front, left, right, behind, top, bottom, -1);
            try {
                //ReadWriteImage.writeJpeg(facesCube[i], "c:/panoramiques/test/" + txtImage + "_cube" + suffixe + ".jpg", jpegQuality);
                boolean sharpen = false;
                if (CBSharpen.isSelected()) {
                    sharpen = true;
                }

                if (RBBmp.isSelected()) {
                    ReadWriteImage.writeBMP(equiRectangulaire, nom + "_sphere.bmp", sharpen, (float) Math.round(SLSharpen.getValue() * 20.f) / 20.f);
                }
                if (RBJpeg.isSelected()) {
                    float quality = 1.0f;
                    ReadWriteImage.writeJpeg(equiRectangulaire, nom + "_sphere.jpg", quality, sharpen, (float) Math.round(SLSharpen.getValue() * 20.f) / 20.f);
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
        if (lstFichier == null) {
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("transformation de fichiers")
                    .message("Vous n'avez pas choisi de fichiers")
                    .showError();
        } else {
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("transformation de fichiers")
                    .message("Attention le traitement que vous allez lancer peut durer plusieurs minutes \n"
                            + "pendant lesquelle le programme semblera ne plus répondre. "
                            + "Veuillez patienter jusqu'à la fin du traitement"
                            + "\n\nMerci")
                    .showWarning();
            final Label lblTermine = new Label();
            lblTermine.setText("Traitement en cours");
            lblTermine.setLayoutX(14);
            lblTermine.setLayoutY(180);
            choixTypeFichier.getChildren().add(lblTermine);
            for (int i = 0; i < listeFichier.getItems().size(); i++) {
                final int j = i;
                final String nomFich = (String) listeFichier.getItems().get(j);
                Platform.runLater(() -> {
                    traiteFichier(nomFich, j);
                });
            }
            lblTermine.setText("Traitement terminé");
            traitementEffectue = true;
        }
    }

    /**
     *
     */
    public void Equi2CubeDialogController() {
    }

    private File[] choixFichiers() {
        File[] lstFich = null;
        FileChooser repertChoix = new FileChooser();
        repertChoix.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("fichier JPEG (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("fichier BMP (*.bmp)", "*.bmp")
        );
        File repert = new File(repertFichier + File.separator);
        repertChoix.setInitialDirectory(repert);
        List<File> list = repertChoix.showOpenMultipleDialog(STEqui2Cube);
        int i = 0;
        boolean attention = false;
        if (list != null) {
            File[] lstFich1 = new File[list.size()];
            for (File file : list) {
                if (i == 0) {
                    repertFichier = file.getParent();
                }
                Image img = new Image("file:" + file.getAbsolutePath());
                if (typeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
                    if (img.getWidth() == 2 * img.getHeight()) {
                        lstFich1[i] = file;
                        i++;
                    } else {
                        attention = true;
                    }
                } else {
                    if (img.getWidth() == img.getHeight()) {
                        String nom = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                        boolean trouve = false;
                        for (int j = 0; j < i; j++) {
                            String nom1 = lstFich1[j].getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                            if (nom.equals(nom1)) {
                                trouve = true;
                            }
                        }
                        if (!trouve) {
                            lstFich1[i] = file;
                            i++;
                        }
                    } else {
                        attention = true;
                    }

                }
            }
            if (attention) {
                Dialogs.create().title("Editeur PanoVisu")
                        .masthead("trnsformation de fichiers")
                        .message("Attention au type des fichiers choisis")
                        .showError();

            }
            lstFich = new File[i];
            System.arraycopy(lstFich1, 0, lstFich, 0, i);
        }

        return lstFich;
    }

    /**
     *
     * @param typeTransf
     * @throws Exception
     */
    public void afficheFenetre(String typeTransf) throws Exception {
        STEqui2Cube = new Stage(StageStyle.UTILITY);
        STEqui2Cube.initModality(Modality.APPLICATION_MODAL);
        STEqui2Cube.setResizable(false);
        myPane = new AnchorPane();
        VBox fenetre = new VBox();
        HBox PChoix = new HBox();
        Pane choixFichier = new Pane();
        listeFichier = new ListView();
        btnAjouteFichiers = new Button("Ajouter des Fichiers");
        choixTypeFichier = new Pane();
        Label lblType = new Label("Type des Fichiers de sortie");
        RBJpeg = new RadioButton("JPEG (.jpg)");
        RBBmp = new RadioButton("BMP (.bmp)");
        CBSharpen = new CheckBox("Masque de netteté");
        SLSharpen = new Slider(0, 2, 0.2);
        LBLSharpen = new Label("0.20");
        Pane Pboutons = new Pane();
        btnAnnuler = new Button("Fermer la fenêtre");
        btnValider = new Button("Lancer le traitement");

        typeTransformation = typeTransf;
        Image imgTransf;
        if (typeTransf.equals(EquiCubeDialogController.EQUI2CUBE)) {
            STEqui2Cube.setTitle("Transformation d'équirectangulaire en faces de cube");
            imgTransf = new Image("file:" + EditeurPanovisu.repertAppli + File.separator + "images/equi2cube.png");
        } else {
            STEqui2Cube.setTitle("Transformation de faces de cube en équirectangulaire");
            imgTransf = new Image("file:" + EditeurPanovisu.repertAppli + File.separator + "images/cube2equi.png");
        }
        ImageView IMType = new ImageView(imgTransf);
        IMType.setLayoutX(35);
        IMType.setLayoutY(250);
        choixTypeFichier.getChildren().add(IMType);
        myPane.setPrefHeight(400);
        myPane.setPrefWidth(600);

        choixFichier.setPrefHeight(355);
        choixFichier.setPrefWidth(420);
        choixFichier.setStyle("-fx-background-color: #d0d0d0; -fx-border-color: #bbb;");
        choixTypeFichier.setPrefHeight(355);
        choixTypeFichier.setPrefWidth(180);
        choixTypeFichier.setStyle("-fx-background-color: #d0d0d0; -fx-border-color: #bbb;");
        PChoix.getChildren().addAll(choixFichier, choixTypeFichier);
        fenetre.setPrefHeight(400);
        fenetre.setPrefWidth(600);
        myPane.getChildren().add(fenetre);
        PChoix.setPrefHeight(355);
        PChoix.setPrefWidth(600);
        PChoix.setStyle("-fx-background-color: #d0d0d0;");
        Pboutons.setPrefHeight(45);
        Pboutons.setPrefWidth(600);
        Pboutons.setStyle("-fx-background-color: #d0d0d0;");
        btnAnnuler.setLayoutX(326);
        btnAnnuler.setLayoutY(10);
        btnValider.setLayoutX(463);
        btnValider.setLayoutY(10);
        listeFichier.setPrefHeight(290);
        listeFichier.setPrefWidth(380);
        listeFichier.setEditable(true);
        listeFichier.setLayoutX(14);
        listeFichier.setLayoutY(14);
        btnAjouteFichiers.setLayoutX(279);
        btnAjouteFichiers.setLayoutY(319);
        choixFichier.getChildren().addAll(listeFichier, btnAjouteFichiers);
        lblType.setLayoutX(14);
        lblType.setLayoutY(14);
        RBBmp.setLayoutX(43);
        RBBmp.setLayoutY(43);
        RBBmp.setSelected(true);
        RBBmp.setToggleGroup(grpTypeFichier);
        RBJpeg.setLayoutX(43);
        RBJpeg.setLayoutY(71);
        RBJpeg.setToggleGroup(grpTypeFichier);
        CBSharpen.setLayoutX(43);
        CBSharpen.setLayoutY(99);
        CBSharpen.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                SLSharpen.setDisable(false);
                LBLSharpen.setDisable(false);
            } else {
                SLSharpen.setDisable(true);
                LBLSharpen.setDisable(true);
            }
        });

        SLSharpen.setShowTickMarks(true);
        SLSharpen.setShowTickLabels(true);
        SLSharpen.setMajorTickUnit(0.5f);
        SLSharpen.setMinorTickCount(4);
        SLSharpen.setBlockIncrement(0.05f);
        SLSharpen.setSnapToTicks(true);
        SLSharpen.setLayoutX(23);
        SLSharpen.setLayoutY(120);
        SLSharpen.setTooltip(new Tooltip("Choisissez le niveau d'accentuation de l'image"));
        SLSharpen.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue == null) {
                LBLSharpen.setText("");
                return;
            }
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2); //arrondi à 2 chiffres apres la virgules
            df.setMinimumFractionDigits(2);
            df.setDecimalSeparatorAlwaysShown(true);

            LBLSharpen.setText(df.format(Math.round(newValue.floatValue() * 20.f) / 20.f) + "");
        });

        SLSharpen.setPrefWidth(120);
        SLSharpen.setDisable(true);
        LBLSharpen.setLayoutX(150);
        LBLSharpen.setLayoutY(120);
        LBLSharpen.setMinWidth(30);
        LBLSharpen.setMaxWidth(30);
        LBLSharpen.setTextAlignment(TextAlignment.RIGHT);
        LBLSharpen.setDisable(true);

        choixTypeFichier.getChildren().addAll(lblType, RBBmp, RBJpeg, CBSharpen, SLSharpen, LBLSharpen);
        Pboutons.getChildren().addAll(btnAnnuler, btnValider);
        fenetre.getChildren().addAll(PChoix, Pboutons);
        Scene scene2 = new Scene(myPane);
        STEqui2Cube.setScene(scene2);
        STEqui2Cube.show();

        btnAnnuler.setOnAction((ActionEvent e) -> {
            annulerE2C();
        });
        btnValider.setOnAction((ActionEvent e) -> {
            if (!traitementEffectue) {
                validerE2C();
            }
        });
        btnAjouteFichiers.setOnAction((ActionEvent e) -> {
            lstFichier = choixFichiers();
            if (lstFichier != null) {
                if (traitementEffectue) {
                    listeFichier.getItems().remove(0, listeFichier.getItems().size());
                    traitementEffectue = false;
                }
                for (File lstFichier1 : lstFichier) {
                    String nomFich = lstFichier1.getAbsolutePath();
                    listeFichier.getItems().add(nomFich);
                }
            }
        });
        myPane.setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            } else {
                event.consume();
            }
        });

        // Dropping over surface
        myPane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            File[] lstFich;
            lstFich = null;
            if (db.hasFiles()) {
                success = true;
                String[] filePath = new String[200];
                int i = 0;
                for (File file1 : db.getFiles()) {
                    filePath[i] = file1.getAbsolutePath();
                    i++;
                }
                int nb = i;
                i = 0;
                boolean attention = false;
                File[] lstFich1 = new File[filePath.length];
                for (int j = 0; j < nb; j++) {
                    String nomfich = filePath[j];
                    File file = new File(nomfich);
                    String extension = nomfich.substring(nomfich.lastIndexOf(".") + 1, nomfich.length()).toLowerCase();
                    if (extension.equals("bmp") || extension.equals("jpg")) {
                        if (i == 0) {
                            repertFichier = file.getParent();
                        }
                        Image img = new Image("file:" + file.getAbsolutePath());
                        if (typeTransformation.equals(EquiCubeDialogController.EQUI2CUBE)) {
                            if (img.getWidth() == 2 * img.getHeight()) {
                                lstFich1[i] = file;
                                i++;
                            } else {
                                attention = true;
                            }
                        } else {
                            if (img.getWidth() == img.getHeight()) {
                                String nom = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                                boolean trouve = false;
                                for (int k = 0; k < i; k++) {
                                    String nom1 = lstFich1[k].getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                                    if (nom.equals(nom1)) {
                                        trouve = true;
                                    }
                                }
                                if (!trouve) {
                                    lstFich1[i] = file;
                                    i++;
                                }
                            } else {
                                attention = true;
                            }

                        }
                    }
                }
                if (attention) {
                    Dialogs.create().title("Editeur PanoVisu")
                            .masthead("trnsformation de fichiers")
                            .message("Attention au type des fichiers choisis")
                            .showError();

                }
                lstFichier = new File[i];
                System.arraycopy(lstFich1, 0, lstFichier, 0, i);
            }
            if (lstFichier != null) {
                for (File lstFichier1 : lstFichier) {
                    String nomFich = lstFichier1.getAbsolutePath();
                    listeFichier.getItems().add(nomFich);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        }
        );

    }

}
