/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getScnPrincipale;
import static editeurpanovisu.EditeurPanovisu.getStrStyleCSS;
import static editeurpanovisu.EditeurPanovisu.setStrStyleCSS;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Affichage de la fenêtre de configuration
 * 
 * @author LANG Laurent
 */
public class ConfigDialogController {

    private static final ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
    private static Stage stConfigDialog;
    private static AnchorPane apConfigDialog;
    private static Button btnAnnuler;
    private static Button btnSauvegarder;
    private static final String[] strCodesLangues = EditeurPanovisu.getStrCodesLanguesTraduction();
    private static final String[] strLangues = EditeurPanovisu.getStrLanguesTraduction();
    final ToggleGroup tgStyle = new ToggleGroup();
    private static Label lblChoixStyle;
    private static RadioButton rbClair;
    private static RadioButton rbFonce;

    private static ComboBox cbListeLangues;
    private static TextField tfRepert;
    private static TextField tfBingAPIKey;

    /**
     *
     * @throws IOException Exception d'entrée sortie 
     */
    public void afficheFenetre() throws IOException {
        String strLangueConfig = EditeurPanovisu.getLocale().getLanguage() + "_" + EditeurPanovisu.getLocale().getCountry();
        int iCodeL = 0;
        for (int i = 0; i < strCodesLangues.length; i++) {
            if (strCodesLangues[i].equals(strLangueConfig)) {
                iCodeL = i;
            }
        }
        stConfigDialog = new Stage(StageStyle.UTILITY);
        stConfigDialog.initModality(Modality.APPLICATION_MODAL);
        stConfigDialog.setResizable(false);
        apConfigDialog = new AnchorPane();
        apConfigDialog.setPrefWidth(600);
        apConfigDialog.setPrefHeight(400);
        Scene sceneConfigDialog = new Scene(apConfigDialog);
        stConfigDialog.setScene(sceneConfigDialog);
        VBox vbFenetre = new VBox();
        Pane paneConfig = new Pane();
        paneConfig.setPrefSize(600, 360);
        Label lblType = new Label(rbLocalisation.getString("config.langue"));
        lblType.setLayoutX(45);
        lblType.setLayoutY(25);
        cbListeLangues = new ComboBox();
        cbListeLangues.setLayoutX(190);
        cbListeLangues.setLayoutY(25);
        for (int i = 0; i < strCodesLangues.length; i++) {
            cbListeLangues.getItems().add(strLangues[i] + " : " + strCodesLangues[i]);
        }
        cbListeLangues.setValue(strLangues[iCodeL] + " : " + strCodesLangues[iCodeL]);
        Label lblRepert = new Label(rbLocalisation.getString("config.choixRepert"));
        lblRepert.setPrefWidth(320);
        lblRepert.setLayoutX(45);
        lblRepert.setLayoutY(70);
        tfRepert = new TextField(EditeurPanovisu.getStrRepertoireProjet());
        tfRepert.setLayoutX(190);
        tfRepert.setLayoutY(110);
        tfRepert.setPrefWidth(300);
        Label lblBingAPIKey = new Label("Bing API key");
        lblBingAPIKey.setPrefWidth(320);
        lblBingAPIKey.setLayoutX(45);
        lblBingAPIKey.setLayoutY(140);
        tfBingAPIKey = new TextField(EditeurPanovisu.getStrBingAPIKey());
        tfBingAPIKey.setLayoutX(190);
        tfBingAPIKey.setLayoutY(170);
        tfBingAPIKey.setPrefWidth(300);

        Button btnChoixRepert = new Button("...");
        btnChoixRepert.setLayoutX(490);
        btnChoixRepert.setLayoutY(110);
        lblChoixStyle = new Label(rbLocalisation.getString("config.choixStyle"));
        lblChoixStyle.setLayoutX(45);
        lblChoixStyle.setLayoutY(200);
        rbClair = new RadioButton(rbLocalisation.getString("config.styleClair"));
        rbFonce = new RadioButton(rbLocalisation.getString("config.styleFonce"));
        rbClair.setLayoutX(190);
        rbClair.setLayoutY(230);
        rbFonce.setLayoutX(190);
        rbFonce.setLayoutY(250);
        rbClair.setToggleGroup(tgStyle);
        rbClair.setUserData("clair");
        rbFonce.setUserData("fonce");
        rbFonce.setToggleGroup(tgStyle);
        if (getStrStyleCSS().equals("clair")) {
            rbClair.setSelected(true);
        } else {
            rbFonce.setSelected(true);
        }
        tgStyle.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (tgStyle.getSelectedToggle() != null) {
                //setUserAgentStylesheet("file:css/" + tgStyle.getSelectedToggle().getUserData().toString() + ".css");
                    File f = new File("css/" + tgStyle.getSelectedToggle().getUserData().toString() + ".css");
                    getScnPrincipale().getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

            }
        });

        paneConfig.getChildren().addAll(
                lblType, cbListeLangues,
                lblRepert, tfRepert, btnChoixRepert,
                lblBingAPIKey, tfBingAPIKey,
                lblChoixStyle, rbClair, rbFonce
        );
        btnChoixRepert.setOnAction((ActionEvent e) -> {
            DirectoryChooser repertChoix = new DirectoryChooser();
            File fileRepert = new File(EditeurPanovisu.getStrRepertoireProjet());
            repertChoix.setInitialDirectory(fileRepert);
            File repertInitial = repertChoix.showDialog(null);
            tfRepert.setText(repertInitial.getAbsolutePath());
        });
        Pane paneBoutons = new Pane();
        apConfigDialog.getChildren().add(vbFenetre);
        vbFenetre.getChildren().add(paneConfig);
        btnAnnuler = new Button(rbLocalisation.getString("config.annuler"));
        btnSauvegarder = new Button(rbLocalisation.getString("config.sauvegarder"));
        btnAnnuler.setLayoutY(10);
        btnSauvegarder.setLayoutY(10);
        btnSauvegarder.setPrefWidth(200);
        btnAnnuler.setPrefWidth(100);
        paneBoutons.getChildren().addAll(btnAnnuler, btnSauvegarder);
        stConfigDialog.show();
//        btnSauvegarder.setAlignment(Pos.CENTER);
//        btnAnnuler.setAlignment(Pos.CENTER);

        btnSauvegarder.setLayoutX(paneConfig.getPrefWidth() - btnSauvegarder.getPrefWidth() - 20);
        btnAnnuler.setLayoutX(paneConfig.getPrefWidth() - btnSauvegarder.getPrefWidth() - btnAnnuler.getPrefWidth() - 40);

        vbFenetre.getChildren().add(paneBoutons);
        btnAnnuler.setOnAction((ActionEvent e) -> {
            stConfigDialog.hide();
                    File f = new File("css/" + getStrStyleCSS() + ".css");
                    getScnPrincipale().getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
            //setUserAgentStylesheet("file:css/" + getStrStyleCSS() + ".css");
        });
        btnSauvegarder.setOnAction((ActionEvent e) -> {
            setStrStyleCSS(tgStyle.getSelectedToggle().getUserData().toString());
            
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(rbLocalisation.getString("config.titreDialogue"));
            alert.setHeaderText(rbLocalisation.getString("config.masthead"));
            alert.setContentText(rbLocalisation.getString("config.message"));
            alert.showAndWait();

            String contenuFichier = "langue=" + cbListeLangues.getValue().toString().split("_")[0].split(" : ")[1] + "\n";
            contenuFichier += "pays=" + cbListeLangues.getValue().toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + tfRepert.getText() + "\n";
            contenuFichier += "bingAPIKey=" + tfBingAPIKey.getText() + "\n";
            contenuFichier += "style=" + tgStyle.getSelectedToggle().getUserData().toString() + "\n";
            File fichConfig = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
            fichConfig.setWritable(true);
            FileWriter fwFichierConfig = null;
            try {
                fwFichierConfig = new FileWriter(fichConfig);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedWriter bwFichierConfig = new BufferedWriter(fwFichierConfig);
            try {
                bwFichierConfig.write(contenuFichier);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bwFichierConfig.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stConfigDialog.hide();
            EditeurPanovisu.setStrBingAPIKey(tfBingAPIKey.getText());
            EditeurPanovisu.navigateurOpenLayers.setBingApiKey(tfBingAPIKey.getText());
            EditeurPanovisu.navigateurOpenLayers.valideBingApiKey(tfBingAPIKey.getText());
            EditeurPanovisu.getGestionnaireInterface().navigateurCarteOL.setBingApiKey(tfBingAPIKey.getText());
            EditeurPanovisu.getGestionnaireInterface().navigateurCarteOL.valideBingApiKey(tfBingAPIKey.getText());
        });
    }

}
