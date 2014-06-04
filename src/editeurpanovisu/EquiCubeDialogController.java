/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author LANG Laurent
 */
public class EquiCubeDialogController {

    private static Stage STEqui2Cube;
    private String typeTransformation;
    static private ListView listeFichier;

    static private Button btnAnnuler;
    static private Button btnValider;
    static private Button btnAjouteFichiers;
    static private Pane choixTypeFichier;

    final ToggleGroup grpTypeFichier = new ToggleGroup();
    public final static String EQUI2CUBE = "E2C";
    public final static String CUBE2QUI = "C2E";
    private File[] lstFichier;
    private static String repertFichier = EditeurPanovisu.repertoireProjet;

    private void annulerE2C() {
        System.out.println("Annuler");

        STEqui2Cube.hide();
    }

    private void traiteFichier(String nomFichier) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(EquiCubeDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Traite : " + nomFichier);
    }

    private void validerE2C() {
        if (lstFichier == null) {
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("trnsformation de fichiers")
                    .message("Vous n'avez pas choisi de fichiers")
                    .showError();
        } else {
            final Label lblTermine = new Label("Tâche en cours");
            lblTermine.setLayoutX(14);
            lblTermine.setLayoutY(140);
            choixTypeFichier.getChildren().add(lblTermine);
            new Thread(() -> {
                Platform.runLater(() -> {
                    for (int i = 0; i < listeFichier.getItems().size(); i++) {
                        String nomFich = (String) listeFichier.getItems().get(i);
                        traiteFichier(nomFich);
                    }
                    lblTermine.setText("Tâche terminée");
                });
            }).start();
        }
    }

    public void Equi2CubeDialogController() {
    }

    private File[] choixFichiers() {
        File[] lstFich = null;
        FileChooser repertChoix = new FileChooser();
        repertChoix.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("fichier JPEG (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("fichier BMP (*.bmp)", "*.b")
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
                        lstFich1[i] = file;
                        i++;
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

    public void afficheFenetre(String typeTransf) throws Exception {
        STEqui2Cube = new Stage(StageStyle.UTILITY);
        AnchorPane myPane = new AnchorPane();
        VBox fenetre = new VBox();
        HBox PChoix = new HBox();
        Pane choixFichier = new Pane();
        listeFichier = new ListView();
        btnAjouteFichiers = new Button("Ajouter des Fichiers");
        choixTypeFichier = new Pane();
        Label lblType = new Label("Type des Fichiers de sortie");
        RadioButton RBJpeg = new RadioButton("JPEG (.jpg)");
        RadioButton RBBmp = new RadioButton("BMP (.bmp)");
        Pane Pboutons = new Pane();
        btnAnnuler = new Button("Fermer la fenêtre");
        btnValider = new Button("Valider");

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
        IMType.setLayoutX(14);
        IMType.setLayoutY(200);
        choixTypeFichier.getChildren().add(IMType);
        STEqui2Cube.initModality(Modality.APPLICATION_MODAL);
        STEqui2Cube.setResizable(false);
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
        btnAnnuler.setLayoutX(386);
        btnAnnuler.setLayoutY(10);
        btnValider.setLayoutX(533);
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
        choixTypeFichier.getChildren().addAll(lblType, RBBmp, RBJpeg);
        Pboutons.getChildren().addAll(btnAnnuler, btnValider);
        fenetre.getChildren().addAll(PChoix, Pboutons);
        Scene scene2 = new Scene(myPane);
        STEqui2Cube.setScene(scene2);
        STEqui2Cube.show();
        btnAnnuler.setOnAction((ActionEvent e) -> {
            annulerE2C();
        });
        btnValider.setOnAction((ActionEvent e) -> {
            validerE2C();
        });
        btnAjouteFichiers.setOnAction((ActionEvent e) -> {
            lstFichier = choixFichiers();
            if (lstFichier != null) {
                System.out.println("nb fich : " + lstFichier.length);
                for (File lstFichier1 : lstFichier) {
                    String nomFich = lstFichier1.getAbsolutePath();
                    listeFichier.getItems().add(nomFich);
                }
            }
        });
    }

}
