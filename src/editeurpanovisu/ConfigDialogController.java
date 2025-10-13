/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    private static ComboBox cbListeLangues;
    private static TextField tfRepert;
    private static TextField tfLocationIQKey;
    private static TextField tfHuggingFaceKey;
    private static TextField tfOpenRouterKey;

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
        // Charger les clés API depuis api-keys.properties
        Properties apiKeys = loadApiKeys();
        
        stConfigDialog = new Stage(StageStyle.UTILITY);
        stConfigDialog.initModality(Modality.APPLICATION_MODAL);
        stConfigDialog.setResizable(false);
        apConfigDialog = new AnchorPane();
        apConfigDialog.setPrefWidth(600);
        apConfigDialog.setPrefHeight(590); // Augmenté de 550 à 590 pour éviter que les boutons soient coupés
        Scene sceneConfigDialog = new Scene(apConfigDialog);
        stConfigDialog.setScene(sceneConfigDialog);
        VBox vbFenetre = new VBox();
        Pane paneConfig = new Pane();
        paneConfig.setPrefSize(600, 510);
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
        
        Button btnChoixRepert = new Button("...");
        btnChoixRepert.setLayoutX(490);
        btnChoixRepert.setLayoutY(110);
        
        // ===== Clés API =====
        Label lblTitreAPI = new Label("═══ Clés API ═══");
        lblTitreAPI.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblTitreAPI.setLayoutX(45);
        lblTitreAPI.setLayoutY(150);
        
        // LocationIQ
        Label lblLocationIQKey = new Label("LocationIQ API Key");
        lblLocationIQKey.setPrefWidth(320);
        lblLocationIQKey.setLayoutX(45);
        lblLocationIQKey.setLayoutY(180);
        tfLocationIQKey = new TextField(apiKeys.getProperty("locationiq.api.key", ""));
        tfLocationIQKey.setLayoutX(45);
        tfLocationIQKey.setLayoutY(205);
        tfLocationIQKey.setPrefWidth(500);
        tfLocationIQKey.setPromptText("pk.xxxxx...");
        
        // Hugging Face
        Label lblHuggingFaceKey = new Label("Hugging Face API Key");
        lblHuggingFaceKey.setPrefWidth(320);
        lblHuggingFaceKey.setLayoutX(45);
        lblHuggingFaceKey.setLayoutY(240);
        tfHuggingFaceKey = new TextField(apiKeys.getProperty("huggingface.api.key", ""));
        tfHuggingFaceKey.setLayoutX(45);
        tfHuggingFaceKey.setLayoutY(265);
        tfHuggingFaceKey.setPrefWidth(500);
        tfHuggingFaceKey.setPromptText("hf_xxxxx...");
        
        // OpenRouter
        Label lblOpenRouterKey = new Label("OpenRouter API Key");
        lblOpenRouterKey.setPrefWidth(320);
        lblOpenRouterKey.setLayoutX(45);
        lblOpenRouterKey.setLayoutY(300);
        tfOpenRouterKey = new TextField(apiKeys.getProperty("openrouter.api.key", ""));
        tfOpenRouterKey.setLayoutX(45);
        tfOpenRouterKey.setLayoutY(325);
        tfOpenRouterKey.setPrefWidth(500);
        tfOpenRouterKey.setPromptText("sk-or-v1-xxxxx...");
        
        // Info bulle
        Label lblInfoAPI = new Label("💡 Les clés API sont sauvegardées dans api-keys.properties");
        lblInfoAPI.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        lblInfoAPI.setLayoutX(45);
        lblInfoAPI.setLayoutY(360);

        paneConfig.getChildren().addAll(
                lblType, cbListeLangues,
                lblRepert, tfRepert, btnChoixRepert,
                lblTitreAPI,
                lblLocationIQKey, tfLocationIQKey,
                lblHuggingFaceKey, tfHuggingFaceKey,
                lblOpenRouterKey, tfOpenRouterKey,
                lblInfoAPI
        );
        btnChoixRepert.setOnAction((ActionEvent e) -> {
            DirectoryChooser repertChoix = new DirectoryChooser();
            File fileRepert = new File(EditeurPanovisu.getStrRepertoireProjet());
            repertChoix.setInitialDirectory(fileRepert);
            File repertInitial = repertChoix.showDialog(null);
            tfRepert.setText(repertInitial.getAbsolutePath());
        });
        Pane paneBoutons = new Pane();
        paneBoutons.setPrefHeight(60); // Ajout de hauteur pour espacement en bas
        apConfigDialog.getChildren().add(vbFenetre);
        vbFenetre.getChildren().add(paneConfig);
        btnAnnuler = new Button(rbLocalisation.getString("config.annuler"));
        btnSauvegarder = new Button(rbLocalisation.getString("config.sauvegarder"));
        btnAnnuler.setLayoutY(20); // Augmenté de 10 à 20 pour plus d'espace en haut
        btnSauvegarder.setLayoutY(20); // Augmenté de 10 à 20 pour plus d'espace en haut
        btnSauvegarder.setPrefWidth(200);
        btnAnnuler.setPrefWidth(100);
        paneBoutons.getChildren().addAll(btnAnnuler, btnSauvegarder);
        stConfigDialog.show();
        btnSauvegarder.setLayoutX(paneConfig.getPrefWidth() - btnSauvegarder.getPrefWidth() - 20);
        btnAnnuler.setLayoutX(paneConfig.getPrefWidth() - btnSauvegarder.getPrefWidth() - btnAnnuler.getPrefWidth() - 40);

        vbFenetre.getChildren().add(paneBoutons);
        btnAnnuler.setOnAction((ActionEvent e) -> {
            stConfigDialog.hide();
        });
        btnSauvegarder.setOnAction((ActionEvent e) -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(rbLocalisation.getString("config.titreDialogue"));
            alert.setHeaderText(rbLocalisation.getString("config.masthead"));
            alert.setContentText(rbLocalisation.getString("config.message"));
            alert.showAndWait();

            // Sauvegarder la configuration panovisu.cfg
            String contenuFichier = "langue=" + cbListeLangues.getValue().toString().split("_")[0].split(" : ")[1] + "\n";
            contenuFichier += "pays=" + cbListeLangues.getValue().toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + tfRepert.getText() + "\n";
            File fichConfig = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
            fichConfig.setWritable(true);
            OutputStreamWriter oswFichierConfig = null;
            try {
                oswFichierConfig = new OutputStreamWriter(new FileOutputStream(fichConfig), "UTF-8");
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedWriter bwFichierConfig = new BufferedWriter(oswFichierConfig);
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
            
            // Sauvegarder les clés API dans api-keys.properties
            saveApiKeys(tfLocationIQKey.getText(), tfHuggingFaceKey.getText(), tfOpenRouterKey.getText());
            
            stConfigDialog.hide();
        });
    }
    
    /**
     * Charge les clés API depuis le fichier api-keys.properties
     * @return Properties contenant les clés API
     */
    private Properties loadApiKeys() {
        Properties props = new Properties();
        File apiKeysFile = new File("api-keys.properties");
        
        if (apiKeysFile.exists()) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(apiKeysFile), "UTF-8")) {
                props.load(reader);
                System.out.println("📖 Clés API chargées depuis api-keys.properties");
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("⚠️ Erreur lors du chargement de api-keys.properties");
            }
        } else {
            System.out.println("⚠️ Fichier api-keys.properties non trouvé");
        }
        
        return props;
    }
    
    /**
     * Sauvegarde les clés API dans le fichier api-keys.properties
     * @param locationIQKey Clé LocationIQ
     * @param huggingFaceKey Clé Hugging Face
     * @param openRouterKey Clé OpenRouter
     */
    private void saveApiKeys(String locationIQKey, String huggingFaceKey, String openRouterKey) {
        File apiKeysFile = new File("api-keys.properties");
        Properties props = new Properties();
        
        // Charger les propriétés existantes pour préserver les commentaires et autres clés
        if (apiKeysFile.exists()) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(apiKeysFile), "UTF-8")) {
                props.load(reader);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Mettre à jour les clés
        props.setProperty("locationiq.api.key", locationIQKey);
        props.setProperty("huggingface.api.key", huggingFaceKey);
        props.setProperty("openrouter.api.key", openRouterKey);
        
        // Sauvegarder
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(apiKeysFile), "UTF-8")) {
            props.store(writer, "Clés API - Modifié depuis les préférences");
            System.out.println("✅ Clés API sauvegardées dans api-keys.properties");
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("❌ Erreur lors de la sauvegarde de api-keys.properties");
        }
    }

}
