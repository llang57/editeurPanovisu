/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.styleCSS;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author LANG Laurent
 */
public class ConfigDialogController {

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
    private static Stage stConfig;
    private static AnchorPane myPane;
    private static Button btnAnnuler;
    private static Button btnSauvegarder;
    private static final String[] codesLangues = EditeurPanovisu.codesLanguesTraduction;
    private static final String[] langues = EditeurPanovisu.languesTraduction;
    final ToggleGroup grpStyle = new ToggleGroup();
    private static Label lblChoixStyle;
    private static RadioButton rbClair;
    private static RadioButton rbFonce;

    private static ComboBox listeLangues;
    private static TextField txtRepert;

    /**
     *
     * @throws IOException
     */
    public void afficheFenetre() throws IOException {
        String chLangueConfig = EditeurPanovisu.locale.getLanguage() + "_" + EditeurPanovisu.locale.getCountry();
        int codeL = 0;
        for (int i = 0; i < codesLangues.length; i++) {
            if (codesLangues[i].equals(chLangueConfig)) {
                codeL = i;
            }
        }
        stConfig = new Stage(StageStyle.UTILITY);
        stConfig.initModality(Modality.APPLICATION_MODAL);
        stConfig.setResizable(false);
        myPane = new AnchorPane();
        myPane.setPrefWidth(600);
        myPane.setPrefHeight(400);
        Scene scene2 = new Scene(myPane);
        stConfig.setScene(scene2);
        stConfig.show();
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
        lblChoixStyle = new Label(rb.getString("config.choixStyle"));
        lblChoixStyle.setLayoutX(45);
        lblChoixStyle.setLayoutY(150);
        rbClair = new RadioButton(rb.getString("config.styleClair"));
        rbFonce = new RadioButton(rb.getString("config.styleFonce"));
        rbClair.setLayoutX(190);
        rbClair.setLayoutY(180);
        rbFonce.setLayoutX(190);
        rbFonce.setLayoutY(200);
        rbClair.setToggleGroup(grpStyle);
        rbClair.setUserData("clair");
        rbFonce.setUserData("fonce");
        rbFonce.setToggleGroup(grpStyle);
        if (styleCSS.equals("clair")) {
            rbClair.setSelected(true);
        } else {
            rbFonce.setSelected(true);
        }
        grpStyle.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpStyle.getSelectedToggle() != null) {
                setUserAgentStylesheet("file:css/" + grpStyle.getSelectedToggle().getUserData().toString() + ".css");

            }
        });

        VBConfig.getChildren().addAll(
                lblType, listeLangues,
                lblRepert, txtRepert, btnChoixRepert,
                lblChoixStyle, rbClair, rbFonce
        );
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
            stConfig.hide();
            setUserAgentStylesheet("file:css/" + styleCSS + ".css");
        });
        btnSauvegarder.setOnAction((ActionEvent e) -> {
            styleCSS = grpStyle.getSelectedToggle().getUserData().toString();
            Dialogs.create()
                    .style(DialogStyle.CROSS_PLATFORM_DARK)
                    .owner(null)
                    .title(rb.getString("config.titreDialogue"))
                    .masthead(rb.getString("config.masthead"))
                    .message(rb.getString("config.message"))
                    .showWarning();
            String contenuFichier = "langue=" + listeLangues.getValue().toString().split("_")[0].split(" : ")[1] + "\n";
            contenuFichier += "pays=" + listeLangues.getValue().toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + txtRepert.getText() + "\n";
            contenuFichier += "style=" + grpStyle.getSelectedToggle().getUserData().toString() + "\n";
            File fichConfig = new File(EditeurPanovisu.repertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
            fichConfig.setWritable(true);
            FileWriter fw = null;
            try {
                fw = new FileWriter(fichConfig);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            stConfig.hide();
        });
    }

}
