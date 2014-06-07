/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author LANG Laurent
 */
public class ConfigDialogController {

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
    private static Stage STConfig;
    private static AnchorPane myPane;
    private static Button btnAnnuler;
    private static Button btnSauvegarder;
    private static final String[] codesLangues = {"fr_FR", "en_EN", "de_DE"};
    private static final String[] langues = {"Francais", "English", "Deutsch"};

    private static ComboBox listeLangues;
    private static TextField txtRepert;

    public void afficheFenetre() throws IOException {
        String chLangueConfig = EditeurPanovisu.locale.getLanguage() + "_" + EditeurPanovisu.locale.getCountry();
        int codeL=0;
        for (int i = 0; i < codesLangues.length; i++) {
            if (codesLangues[i].equals(chLangueConfig)) {
                codeL = i;
            }
        }
        STConfig = new Stage(StageStyle.UTILITY);
        STConfig.initModality(Modality.APPLICATION_MODAL);
        STConfig.setResizable(false);
        myPane = new AnchorPane();
        myPane.setPrefWidth(600);
        myPane.setPrefHeight(400);
        Scene scene2 = new Scene(myPane);
        STConfig.setScene(scene2);
        STConfig.show();
        VBox fenetre = new VBox();
        Pane VBConfig = new Pane();
        VBConfig.setPrefSize(600, 360);
        Label lblType = new Label(rb.getString("config.langue"));
        lblType.setLayoutX(45);
        lblType.setLayoutY(25);
        listeLangues = new ComboBox();
        listeLangues.setLayoutX(190);
        listeLangues.setLayoutY(25);
        for (int i = 0; i < codesLangues.length; i++) {
            listeLangues.getItems().add(langues[i] + " : " + codesLangues[i]);
        }
        listeLangues.setValue(langues[codeL] + " : " + codesLangues[codeL]);
        Label lblRepert = new Label(rb.getString("config.choixRepert"));
        lblRepert.setPrefWidth(320);
        lblRepert.setLayoutX(45);
        lblRepert.setLayoutY(70);
        txtRepert = new TextField(EditeurPanovisu.repertoireProjet);
        txtRepert.setLayoutX(190);
        txtRepert.setLayoutY(110);
        txtRepert.setPrefWidth(300);
        Button btnChoixRepert = new Button("...");
        btnChoixRepert.setLayoutX(490);
        btnChoixRepert.setLayoutY(110);
        VBConfig.getChildren().addAll(lblType, listeLangues, lblRepert, txtRepert, btnChoixRepert);
        btnChoixRepert.setOnAction((ActionEvent e) -> {
            DirectoryChooser repertChoix = new DirectoryChooser();
            File repert = new File(EditeurPanovisu.repertoireProjet);
            repertChoix.setInitialDirectory(repert);
            File repertInitial = repertChoix.showDialog(null);
            txtRepert.setText(repertInitial.getAbsolutePath());
        });
        Pane Pboutons = new Pane();
        myPane.getChildren().add(fenetre);
        fenetre.getChildren().add(VBConfig);
        btnAnnuler = new Button(rb.getString("config.annuler"));
        btnSauvegarder = new Button(rb.getString("config.sauvegarder"));
        btnAnnuler.setLayoutX(326);
        btnAnnuler.setLayoutY(10);
        btnSauvegarder.setLayoutX(423);
        btnSauvegarder.setLayoutY(10);
        Pboutons.getChildren().addAll(btnAnnuler, btnSauvegarder);
        fenetre.getChildren().add(Pboutons);
        btnAnnuler.setOnAction((ActionEvent e) -> {
            STConfig.hide();
        });
        btnSauvegarder.setOnAction((ActionEvent e) -> {
            Dialogs.create()
                    .owner(null)
                    .title(rb.getString("config.titreDialogue"))
                    .masthead(rb.getString("config.masthead"))
                    .message(rb.getString("config.message"))
                    .showWarning();
            String contenuFichier = "langue=" + listeLangues.getValue().toString().split("_")[0].split(" : ")[1] + "\n";
            contenuFichier += "pays=" + listeLangues.getValue().toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + txtRepert.getText() + "\n";
            File fichConfig = new File(EditeurPanovisu.repertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
            fichConfig.setWritable(true);
            FileWriter fw = null;
            try {
                fw = new FileWriter(fichConfig);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(fichConfig.getAbsolutePath() + "\n" + contenuFichier);
            BufferedWriter bw = new BufferedWriter(fw);
            try {
                bw.write(contenuFichier);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            STConfig.hide();
        });
    }

}
