/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;


import java.io.BufferedReader;
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
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private static ComboBox<String> cbOpenRouterModel;
    private static ComboBox<String> cbOllamaModel;
    private static CheckBox chkGPUEnabled;

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
        apConfigDialog.setPrefHeight(780); // Augmenté pour la section GPU (680 -> 780)
        Scene sceneConfigDialog = new Scene(apConfigDialog);
        stConfigDialog.setScene(sceneConfigDialog);
        VBox vbFenetre = new VBox();
        Pane paneConfig = new Pane();
        paneConfig.setPrefSize(600, 700); // Augmenté pour la section GPU (600 -> 700)
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
        
        // ===== Modèles IA =====
        Label lblTitreModeles = new Label("═══ Modèles IA pour descriptions ═══");
        lblTitreModeles.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblTitreModeles.setLayoutX(45);
        lblTitreModeles.setLayoutY(390);
        
        // OpenRouter (priorité 1)
        Label lblOpenRouterModel = new Label("OpenRouter (GPT-5, Claude) - Priorité 1");
        lblOpenRouterModel.setPrefWidth(320);
        lblOpenRouterModel.setLayoutX(45);
        lblOpenRouterModel.setLayoutY(420);
        cbOpenRouterModel = new ComboBox<>();
        cbOpenRouterModel.setLayoutX(45);
        cbOpenRouterModel.setLayoutY(445);
        cbOpenRouterModel.setPrefWidth(500);
        // Configurer la police pour supporter les émojis
        cbOpenRouterModel.setStyle("-fx-font-family: 'Segoe UI Emoji', 'Noto Color Emoji', 'Apple Color Emoji', sans-serif; -fx-font-size: 13px;");
        // Charger les modèles OpenRouter disponibles avec emoji
        for (String model : OllamaService.getModelesOpenRouterDisponibles()) {
            String displayName = ajouterEmojiModele(model);
            cbOpenRouterModel.getItems().add(displayName);
        }
        // Sélectionner le modèle configuré
        String currentOpenRouterModel = OllamaService.getOpenRouterModel();
        String displayCurrentModel = ajouterEmojiModele(currentOpenRouterModel);
        cbOpenRouterModel.setValue(displayCurrentModel);
        
        // Ollama (fallback)
        Label lblOllamaModel = new Label("Ollama (local) - Fallback");
        lblOllamaModel.setPrefWidth(320);
        lblOllamaModel.setLayoutX(45);
        lblOllamaModel.setLayoutY(480);
        cbOllamaModel = new ComboBox<>();
        cbOllamaModel.setLayoutX(45);
        cbOllamaModel.setLayoutY(505);
        cbOllamaModel.setPrefWidth(500);
        // Configurer la police pour supporter les émojis
        cbOllamaModel.setStyle("-fx-font-family: 'Segoe UI Emoji', 'Noto Color Emoji', 'Apple Color Emoji', sans-serif; -fx-font-size: 13px;");
        // Charger les modèles Ollama installés avec emoji
        java.util.List<String> modelesOllama = OllamaService.getModelesOllamaDisponibles();
        if (modelesOllama.isEmpty()) {
            cbOllamaModel.getItems().add("❌ (Ollama non installé ou aucun modèle)");
            cbOllamaModel.setDisable(true);
        } else {
            for (String model : modelesOllama) {
                String displayName = ajouterEmojiModele(model);
                cbOllamaModel.getItems().add(displayName);
            }
            // Sélectionner le modèle configuré
            String currentOllamaModel = OllamaService.getOllamaModel();
            String displayCurrentOllama = ajouterEmojiModele(currentOllamaModel);
            if (cbOllamaModel.getItems().contains(displayCurrentOllama)) {
                cbOllamaModel.setValue(displayCurrentOllama);
            } else if (!cbOllamaModel.getItems().isEmpty()) {
                cbOllamaModel.setValue(cbOllamaModel.getItems().get(0));
            }
        }
        
        // Info bulle modèles
        Label lblInfoModeles = new Label("💡 Les modèles sont sauvegardés dans preferences.cfg");
        lblInfoModeles.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        lblInfoModeles.setLayoutX(45);
        lblInfoModeles.setLayoutY(540);
        
        // Section GPU
        Label lblTitreGPU = new Label("🎮 " + rbLocalisation.getString("config.gpu.titre"));
        lblTitreGPU.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        lblTitreGPU.setLayoutX(45);
        lblTitreGPU.setLayoutY(570);
        
        chkGPUEnabled = new CheckBox(rbLocalisation.getString("config.gpu.activer"));
        chkGPUEnabled.setLayoutX(45);
        chkGPUEnabled.setLayoutY(600);
        
        // Charger l'état actuel du GPU
        chkGPUEnabled.setSelected(editeurpanovisu.gpu.GPUManager.getInstance().isGPUEnabled());
        
        // Afficher les informations GPU si disponible
        Label lblInfoGPU = new Label();
        lblInfoGPU.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
        lblInfoGPU.setLayoutX(45);
        lblInfoGPU.setLayoutY(625);
        lblInfoGPU.setMaxWidth(500);
        lblInfoGPU.setWrapText(true);
        
        editeurpanovisu.gpu.GPUManager gpuMgr = editeurpanovisu.gpu.GPUManager.getInstance();
        if (gpuMgr.isGPUAvailable()) {
            lblInfoGPU.setText("✅ " + gpuMgr.getGPUInfo());
        } else {
            lblInfoGPU.setText("⚠️ " + rbLocalisation.getString("config.gpu.nonDisponible"));
            chkGPUEnabled.setDisable(true);
        }

        paneConfig.getChildren().addAll(
                lblType, cbListeLangues,
                lblRepert, tfRepert, btnChoixRepert,
                lblTitreAPI,
                lblLocationIQKey, tfLocationIQKey,
                lblHuggingFaceKey, tfHuggingFaceKey,
                lblOpenRouterKey, tfOpenRouterKey,
                lblInfoAPI,
                lblTitreModeles,
                lblOpenRouterModel, cbOpenRouterModel,
                lblOllamaModel, cbOllamaModel,
                lblInfoModeles,
                lblTitreGPU, chkGPUEnabled, lblInfoGPU
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
            
            // Sauvegarder les modèles IA sélectionnés dans preferences.cfg (enlever les emoji)
            if (cbOpenRouterModel.getValue() != null && !cbOpenRouterModel.getValue().isEmpty()) {
                String modelNameOnly = extraireNomModele(cbOpenRouterModel.getValue());
                OllamaService.setOpenRouterModel(modelNameOnly);
            }
            if (cbOllamaModel.getValue() != null && !cbOllamaModel.getValue().isEmpty() && 
                !cbOllamaModel.getValue().contains("non installé")) {
                String modelNameOnly = extraireNomModele(cbOllamaModel.getValue());
                OllamaService.setOllamaModel(modelNameOnly);
            }
            
            try {
                saveModelsPreferences();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Sauvegarder et appliquer la préférence GPU
            boolean gpuEnabled = chkGPUEnabled.isSelected();
            editeurpanovisu.gpu.GPUManager.getInstance().setGPUEnabled(gpuEnabled);
            System.out.println("[Config] GPU " + (gpuEnabled ? "activé" : "désactivé"));
            
            try {
                EditeurPanovisu.sauvePreferences();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Fermer d'abord le dialog pour éviter les blocages modaux sous Linux
            stConfigDialog.hide();
            
            // PUIS afficher l'alerte de confirmation (non bloquant)
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rbLocalisation.getString("config.titreDialogue"));
                alert.setHeaderText(rbLocalisation.getString("config.masthead"));
                alert.setContentText(rbLocalisation.getString("config.message"));
                alert.show(); // show() au lieu de showAndWait() pour ne pas bloquer
            });
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
    
    /**
     * Sauvegarde les modèles IA sélectionnés dans preferences.cfg
     */
    private void saveModelsPreferences() throws IOException {
        File filePreferences = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + 
                                         File.separator + "preferences.cfg");
        
        // Lire les préférences existantes
        StringBuilder existingContent = new StringBuilder();
        if (filePreferences.exists()) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePreferences), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Ignorer les anciennes lignes de modèles pour éviter les doublons
                    if (!line.startsWith("openrouterModel=") && !line.startsWith("ollamaModel=")) {
                        existingContent.append(line).append("\n");
                    }
                }
            }
        }
        
        // Ajouter les nouveaux paramètres de modèles
        String openRouterModel = OllamaService.getOpenRouterModel();
        String ollamaModel = OllamaService.getOllamaModel();
        
        if (openRouterModel != null && !openRouterModel.isEmpty()) {
            existingContent.append("openrouterModel=").append(openRouterModel).append("\n");
        }
        if (ollamaModel != null && !ollamaModel.isEmpty()) {
            existingContent.append("ollamaModel=").append(ollamaModel).append("\n");
        }
        
        // Réécrire le fichier
        filePreferences.setWritable(true);
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(filePreferences), "UTF-8");
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(existingContent.toString());
        }
        
        System.out.println("💾 Modèles IA sauvegardés dans preferences.cfg");
        System.out.println("  - OpenRouter: " + openRouterModel);
        System.out.println("  - Ollama: " + ollamaModel);
    }
    
    /**
     * Ajoute un emoji visuel devant le nom du modèle pour l'interface
     * @param modelName Nom du modèle (ex: "openai/gpt-4o")
     * @return Nom avec emoji, prix et qualité (ex: "🌍 GPT-4o ($6.25/M) ⭐⭐⭐⭐⭐")
     */
    private static String ajouterEmojiModele(String modelName) {
        if (modelName == null || modelName.isEmpty()) {
            return modelName;
        }
        
        // Modèles OpenRouter - Nov 2025 (avec prix et qualité)
        if (modelName.equals("google/gemini-2.0-flash-exp:free") || modelName.equals("google/gemini-2.0-flash-exp")) {
            return "[GRATUIT] Gemini 2.0 Flash ★★★★";
        } else if (modelName.equals("google/gemini-2.5-flash")) {
            return "[0.07€] Gemini 2.5 Flash ★★★★";
        } else if (modelName.equals("anthropic/claude-3.5-sonnet")) {
            return "[8.30€] Claude 3.5 Sonnet ★★★★★";
        } else if (modelName.equals("openai/gpt-4o")) {
            return "[5.75€] GPT-4o ★★★★★";
        } else if (modelName.equals("openai/gpt-4o-mini")) {
            return "[0.35€] GPT-4o Mini ★★★★";
        } else if (modelName.equals("anthropic/claude-3-haiku")) {
            return "[0.69€] Claude 3 Haiku ★★★";
        } else if (modelName.equals("google/gemini-2.5-pro")) {
            return "[2.88€] Gemini 2.5 Pro ★★★★★";
        } else if (modelName.equals("mistralai/mistral-nemo")) {
            return "[0.14€] Mistral Nemo ★★★★";
        } else if (modelName.equals("anthropic/claude-3-opus")) {
            return "[41.40€] Claude 3 Opus ★★★★★";
        } else if (modelName.equals("openai/gpt-4-turbo")) {
            return "[18.40€] GPT-4 Turbo ★★★★★";
        }
        // Anciens modèles (compatibilité)
        else if (modelName.equals("anthropic/claude-sonnet-4.5")) {
            return "[8.30€] Claude Sonnet 4.5 ★★★★★";
        } else if (modelName.equals("anthropic/claude-3.5-sonnet:20241022")) {
            return "[8.30€] Claude 3.5 Sonnet Oct ★★★★★";
        } else if (modelName.equals("google/gemini-pro")) {
            return "[GRATUIT] Gemini Pro ★★★★";
        } else if (modelName.equals("meta-llama/llama-3.1-8b-instruct")) {
            return "[GRATUIT] Llama 3.1 8B ★★★";
        } else if (modelName.equals("deepcogito/cogito-v2-preview-deepseek-671b")) {
            return "Cogito v2 (671B) ★★★★";
        } else if (modelName.equals("openai/gpt-oss-120b")) {
            return "[0.04€] GPT-OSS-120B ★★★";
        } 
        // Modèles Ollama locaux (avec taille et qualité)
        else if (modelName.contains("mistral-nemo")) {
            return "[7GB] Mistral Nemo - Créatif & précis ★★★★★";
        } else if (modelName.contains("deepseek-r1")) {
            return "[5.2GB] DeepSeek-R1 - Raisonnement ★★★★";
        } else if (modelName.contains("qwen2.5")) {
            return "[4.7GB] Qwen 2.5 - Excellent faits ★★★★★";
        } else if (modelName.contains("llama3.1")) {
            return "[4.9GB] Llama 3.1 - Très fiable ★★★★★";
        } else if (modelName.contains("gemma2")) {
            return "[5.4GB] Gemma 2 - Google ★★★★";
        } else if (modelName.contains("phi3")) {
            return "[2.2GB] Phi 3 - Compact MS ★★★★";
        } else if (modelName.contains("llama3.2")) {
            return "[2GB] Llama 3.2 - Léger ★★★";
        } else if (modelName.contains("mistral") && !modelName.contains("nemo")) {
            return "[4.4GB] Mistral - Classique ★★★★";
        } else if (modelName.contains("codellama")) {
            return "[3.8GB] CodeLlama - Code ★★★";
        }
        
        // Par défaut : retourner le nom tel quel
        return modelName;
    }
    
    /**
     * Extrait le nom du modèle sans l'emoji, prix et qualité
     * @param displayName Nom affiché avec emoji (ex: "🌍 GPT-4o ($6.25/M) ⭐⭐⭐⭐⭐")
     * @return Nom technique du modèle (ex: "openai/gpt-4o")
     */
    private static String extraireNomModele(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return displayName;
        }
        
        // Mapping inverse - Nouveaux modèles (Nov 2025)
        if (displayName.contains("Gemini 2.0 Flash")) {
            return "google/gemini-2.0-flash-exp:free";
        } else if (displayName.contains("Gemini 2.5 Flash")) {
            return "google/gemini-2.5-flash";
        } else if (displayName.contains("Claude 3.5 Sonnet") && !displayName.contains("Oct")) {
            return "anthropic/claude-3.5-sonnet";
        } else if (displayName.contains("GPT-4o Mini")) {
            return "openai/gpt-4o-mini";
        } else if (displayName.contains("GPT-4o") && !displayName.contains("Mini")) {
            return "openai/gpt-4o";
        } else if (displayName.contains("Claude 3 Haiku")) {
            return "anthropic/claude-3-haiku";
        } else if (displayName.contains("Gemini 2.5 Pro")) {
            return "google/gemini-2.5-pro";
        } else if (displayName.contains("Mistral Nemo") && !displayName.contains("[7GB]")) {
            return "mistralai/mistral-nemo";  // OpenRouter
        } else if (displayName.contains("Claude 3 Opus")) {
            return "anthropic/claude-3-opus";
        } else if (displayName.contains("GPT-4 Turbo")) {
            return "openai/gpt-4-turbo";
        }
        // Anciens modèles (compatibilité)
        else if (displayName.contains("Claude Sonnet 4.5")) {
            return "anthropic/claude-sonnet-4.5";
        } else if (displayName.contains("Claude 3.5 Sonnet Oct")) {
            return "anthropic/claude-3.5-sonnet:20241022";
        } else if (displayName.contains("Gemini Pro")) {
            return "google/gemini-pro";
        } else if (displayName.contains("Llama 3.1 8B")) {
            return "meta-llama/llama-3.1-8b-instruct";
        } else if (displayName.contains("Cogito v2")) {
            return "deepcogito/cogito-v2-preview-deepseek-671b";
        } else if (displayName.contains("GPT-OSS-120B")) {
            return "openai/gpt-oss-120b";
        }
        // Ollama locaux
        else if (displayName.contains("Mistral Nemo")) {
            return "mistral-nemo";
        } else if (displayName.contains("DeepSeek-R1")) {
            return "deepseek-r1";
        } else if (displayName.contains("Qwen 2.5")) {
            return "qwen2.5";
        } else if (displayName.contains("Llama 3.1") && !displayName.contains("8B")) {
            return "llama3.1";
        } else if (displayName.contains("Llama 3.2")) {
            return "llama3.2";
        } else if (displayName.contains("Gemma 2")) {
            return "gemma2";
        } else if (displayName.contains("Phi 3")) {
            return "phi3";
        } else if (displayName.contains("Mistral") && displayName.contains("Classique")) {
            return "mistral";
        } else if (displayName.contains("CodeLlama")) {
            return "codellama";
        }
        
        // Si pas de correspondance, enlever juste l'emoji et les infos entre parenthèses
        return displayName.replaceFirst("^[^a-zA-Z0-9]+\\s*", "").split("\\s+\\(")[0].trim();
    }

}
