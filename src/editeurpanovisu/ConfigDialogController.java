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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
     * @param modelName Nom du modèle (ex: "openai/gpt-5")
     * @return Nom avec emoji (ex: "⭐ GPT-5")
     */
    private static String ajouterEmojiModele(String modelName) {
        if (modelName == null || modelName.isEmpty()) {
            return modelName;
        }
        
        // Modèles OpenRouter (GPT-5 retiré - instable)
        if (modelName.equals("anthropic/claude-sonnet-4.5")) {
            return "⭐ Claude Sonnet 4.5 (Anthropic)";
        } else if (modelName.equals("anthropic/claude-3-opus")) {
            return "🔷 Claude 3 Opus (Anthropic)";
        } else if (modelName.equals("anthropic/claude-3.5-sonnet:20241022")) {
            return "📅 Claude 3.5 Sonnet (oct 2024)";
        } else if (modelName.equals("mistralai/mistral-nemo")) {
            return "🇫🇷 Mistral Nemo (Mistral AI)";
        } else if (modelName.equals("deepcogito/cogito-v2-preview-deepseek-671b")) {
            return "🧠 Cogito v2 Preview (671B)";
        } else if (modelName.equals("openai/gpt-oss-120b")) {
            return "💰 GPT-OSS-120B (Open Source)";
        } else if (modelName.equals("openai/gpt-4-turbo")) {
            return "🌍 GPT-4 Turbo (OpenAI)";
        } else if (modelName.equals("google/gemini-pro")) {
            return "🆓 Gemini Pro (Google)";
        } else if (modelName.equals("meta-llama/llama-3.1-8b-instruct")) {
            return "🆓 Llama 3.1 8B (Meta)";
        } 
        // Modèles Ollama locaux
        else if (modelName.contains("deepseek-r1")) {
            return "🔸 DeepSeek-R1";
        } else if (modelName.equals("mistral-nemo")) {
            return "🔸 Mistral Nemo";
        } else if (modelName.equals("qwen2.5")) {
            return "🔸 Qwen 2.5";
        } else if (modelName.equals("llama3.1")) {
            return "🔸 Llama 3.1";
        } else if (modelName.equals("gemma2")) {
            return "🔸 Gemma 2";
        }
        
        // Par défaut : ajouter un emoji générique
        return "🤖 " + modelName;
    }
    
    /**
     * Extrait le nom du modèle sans l'emoji et la description
     * @param displayName Nom affiché avec emoji (ex: "⭐ GPT-5 (OpenAI)")
     * @return Nom technique du modèle (ex: "openai/gpt-5")
     */
    private static String extraireNomModele(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return displayName;
        }
        
        // Mapping inverse (GPT-5 retiré)
        if (displayName.contains("Claude Sonnet 4.5")) {
            return "anthropic/claude-sonnet-4.5";
        } else if (displayName.contains("Claude 3 Opus")) {
            return "anthropic/claude-3-opus";
        } else if (displayName.contains("Claude 3.5 Sonnet") && displayName.contains("oct 2024")) {
            return "anthropic/claude-3.5-sonnet:20241022";
        } else if (displayName.contains("Mistral Nemo") && displayName.contains("Mistral AI")) {
            return "mistralai/mistral-nemo";
        } else if (displayName.contains("Cogito v2 Preview")) {
            return "deepcogito/cogito-v2-preview-deepseek-671b";
        } else if (displayName.contains("GPT-OSS-120B")) {
            return "openai/gpt-oss-120b";
        } else if (displayName.contains("GPT-4 Turbo")) {
            return "openai/gpt-4-turbo";
        } else if (displayName.contains("Gemini Pro")) {
            return "google/gemini-pro";
        } else if (displayName.contains("Llama 3.1 8B")) {
            return "meta-llama/llama-3.1-8b-instruct";
        }
        // Ollama
        else if (displayName.contains("DeepSeek-R1")) {
            // Extraire la version exacte (ex: deepseek-r1:70b)
            if (displayName.contains(":")) {
                // Si le nom complet avec version est dans displayName, le retourner
                return displayName.replaceFirst("^[^a-z]+\\s*", "").split("\\s+")[0];
            }
            return "deepseek-r1:70b"; // Version par défaut
        } else if (displayName.contains("Mistral Nemo")) {
            return "mistral-nemo";
        } else if (displayName.contains("Qwen 2.5")) {
            return "qwen2.5";
        } else if (displayName.contains("Llama 3.1") && !displayName.contains("8B")) {
            return "llama3.1";
        } else if (displayName.contains("Gemma 2")) {
            return "gemma2";
        }
        
        // Si pas de correspondance, enlever juste l'emoji au début
        return displayName.replaceFirst("^[^a-zA-Z0-9]+\\s*", "");
    }

}
