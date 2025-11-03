package editeurpanovisu;

import editeurpanovisu.config.ModelConfig;
import editeurpanovisu.config.ModelConfig.ModelEntry;
import editeurpanovisu.config.ModelConfigManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur pour la fenêtre de configuration des modèles IA
 */
public class ModelsConfigController {
    
    @FXML private TabPane tabPane;
    
    // OpenRouter controls
    @FXML private CheckBox chkVerifyOpenRouter;
    @FXML private Button btnRefreshOpenRouter;
    @FXML private TableView<ModelEntryWrapper> tableOpenRouter;
    @FXML private TableColumn<ModelEntryWrapper, Boolean> colOpenRouterEnabled;
    @FXML private TableColumn<ModelEntryWrapper, Integer> colOpenRouterPriority;
    @FXML private TableColumn<ModelEntryWrapper, String> colOpenRouterId;
    @FXML private TableColumn<ModelEntryWrapper, String> colOpenRouterName;
    @FXML private TableColumn<ModelEntryWrapper, String> colOpenRouterPrice;
    @FXML private TableColumn<ModelEntryWrapper, String> colOpenRouterQuality;
    @FXML private TableColumn<ModelEntryWrapper, String> colOpenRouterAvailable;
    @FXML private Button btnAddOpenRouter;
    @FXML private Button btnRemoveOpenRouter;
    @FXML private Label lblOpenRouterStatus;
    
    // Ollama controls
    @FXML private CheckBox chkVerifyOllama;
    @FXML private Button btnRefreshOllama;
    @FXML private TextField txtOllamaUrl;
    @FXML private TableView<ModelEntryWrapper> tableOllama;
    @FXML private TableColumn<ModelEntryWrapper, Boolean> colOllamaEnabled;
    @FXML private TableColumn<ModelEntryWrapper, Integer> colOllamaPriority;
    @FXML private TableColumn<ModelEntryWrapper, String> colOllamaId;
    @FXML private TableColumn<ModelEntryWrapper, String> colOllamaName;
    @FXML private TableColumn<ModelEntryWrapper, String> colOllamaSize;
    @FXML private TableColumn<ModelEntryWrapper, String> colOllamaQuality;
    @FXML private TableColumn<ModelEntryWrapper, String> colOllamaAvailable;
    @FXML private Button btnAddOllama;
    @FXML private Button btnRemoveOllama;
    @FXML private Label lblOllamaStatus;
    
    // Action buttons
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    
    private ModelConfigManager configManager;
    private ModelConfig openRouterConfig;
    private ModelConfig ollamaConfig;
    
    private ObservableList<ModelEntryWrapper> openRouterData = FXCollections.observableArrayList();
    private ObservableList<ModelEntryWrapper> ollamaData = FXCollections.observableArrayList();
    
    /**
     * Initialisation du contrôleur
     */
    @FXML
    public void initialize() {
        configManager = OllamaService.getConfigManager();
        
        // Charger les configurations
        openRouterConfig = configManager.getOpenRouterConfig();
        ollamaConfig = configManager.getOllamaConfig();
        
        if (openRouterConfig == null) {
            openRouterConfig = configManager.loadOpenRouterConfig();
        }
        if (ollamaConfig == null) {
            ollamaConfig = configManager.loadOllamaConfig();
        }
        
        // Initialiser les tables
        setupOpenRouterTable();
        setupOllamaTable();
        
        // Charger les données
        loadOpenRouterData();
        loadOllamaData();
        
        // Configurer les actions
        setupActions();
    }
    
    /**
     * Configuration de la table OpenRouter
     */
    private void setupOpenRouterTable() {
        tableOpenRouter.setEditable(true);
        
        // Colonne Enabled (checkbox)
        colOpenRouterEnabled.setCellValueFactory(param -> param.getValue().enabledProperty());
        colOpenRouterEnabled.setCellFactory(CheckBoxTableCell.forTableColumn(colOpenRouterEnabled));
        
        // Colonne Priority (éditable)
        colOpenRouterPriority.setCellValueFactory(param -> param.getValue().priorityProperty().asObject());
        colOpenRouterPriority.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colOpenRouterPriority.setOnEditCommit(event -> {
            event.getRowValue().setPriority(event.getNewValue());
        });
        
        // Colonnes en lecture seule
        colOpenRouterId.setCellValueFactory(param -> param.getValue().idProperty());
        colOpenRouterName.setCellValueFactory(param -> param.getValue().nameProperty());
        colOpenRouterPrice.setCellValueFactory(param -> param.getValue().priceProperty());
        colOpenRouterQuality.setCellValueFactory(param -> param.getValue().qualityProperty());
        colOpenRouterAvailable.setCellValueFactory(param -> param.getValue().availableProperty());
        
        tableOpenRouter.setItems(openRouterData);
        
        // Activer le drag & drop pour réordonner
        setupDragAndDrop(tableOpenRouter, openRouterData);
    }
    
    /**
     * Configuration de la table Ollama
     */
    private void setupOllamaTable() {
        tableOllama.setEditable(true);
        
        // Colonne Enabled (checkbox)
        colOllamaEnabled.setCellValueFactory(param -> param.getValue().enabledProperty());
        colOllamaEnabled.setCellFactory(CheckBoxTableCell.forTableColumn(colOllamaEnabled));
        
        // Colonne Priority (éditable)
        colOllamaPriority.setCellValueFactory(param -> param.getValue().priorityProperty().asObject());
        colOllamaPriority.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colOllamaPriority.setOnEditCommit(event -> {
            event.getRowValue().setPriority(event.getNewValue());
        });
        
        // Colonnes en lecture seule
        colOllamaId.setCellValueFactory(param -> param.getValue().idProperty());
        colOllamaName.setCellValueFactory(param -> param.getValue().nameProperty());
        colOllamaSize.setCellValueFactory(param -> param.getValue().sizeProperty());
        colOllamaQuality.setCellValueFactory(param -> param.getValue().qualityProperty());
        colOllamaAvailable.setCellValueFactory(param -> param.getValue().availableProperty());
        
        tableOllama.setItems(ollamaData);
        
        // Activer le drag & drop pour réordonner
        setupDragAndDrop(tableOllama, ollamaData);
    }
    
    /**
     * Configure le drag & drop pour réordonner les lignes d'une table
     */
    private void setupDragAndDrop(TableView<ModelEntryWrapper> table, ObservableList<ModelEntryWrapper> data) {
        table.setRowFactory(tv -> {
            TableRow<ModelEntryWrapper> row = new TableRow<>();
            
            // Gestion du drag (début du glissement)
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(index.toString());
                    db.setContent(cc);
                    event.consume();
                }
            });
            
            // Gestion du drop over (survol pendant le glissement)
            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    if (row.getIndex() != Integer.parseInt(db.getString())) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });
            
            // Gestion du drop entered (entrée dans la zone)
            row.setOnDragEntered(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    row.setOpacity(0.3);
                }
            });
            
            // Gestion du drop exited (sortie de la zone)
            row.setOnDragExited(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    row.setOpacity(1);
                }
            });
            
            // Gestion du drop (dépôt final)
            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    int draggedIndex = Integer.parseInt(db.getString());
                    ModelEntryWrapper draggedItem = data.remove(draggedIndex);
                    
                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = data.size();
                    } else {
                        dropIndex = row.getIndex();
                    }
                    
                    data.add(dropIndex, draggedItem);
                    
                    // Mettre à jour les priorités
                    updatePriorities(data);
                    
                    event.setDropCompleted(true);
                    table.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            
            // Gestion de la fin du drag
            row.setOnDragDone(event -> {
                event.consume();
            });
            
            return row;
        });
    }
    
    /**
     * Met à jour les numéros de priorité après réordonnancement
     */
    private void updatePriorities(ObservableList<ModelEntryWrapper> data) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setPriority(i + 1);
        }
    }
    
    /**
     * Chargement des données OpenRouter
     */
    private void loadOpenRouterData() {
        openRouterData.clear();
        
        if (openRouterConfig != null && openRouterConfig.getModels() != null) {
            chkVerifyOpenRouter.setSelected(openRouterConfig.isVerifyAtStartup());
            
            for (ModelEntry entry : openRouterConfig.getModels()) {
                openRouterData.add(new ModelEntryWrapper(entry));
            }
            
            lblOpenRouterStatus.setText(openRouterData.size() + " modèles configurés");
        }
    }
    
    /**
     * Chargement des données Ollama
     */
    private void loadOllamaData() {
        ollamaData.clear();
        
        if (ollamaConfig != null && ollamaConfig.getModels() != null) {
            chkVerifyOllama.setSelected(ollamaConfig.isVerifyAtStartup());
            
            String serverUrl = ollamaConfig.getServerUrl();
            if (serverUrl != null && !serverUrl.isEmpty()) {
                txtOllamaUrl.setText(serverUrl);
            }
            
            for (ModelEntry entry : ollamaConfig.getModels()) {
                ollamaData.add(new ModelEntryWrapper(entry));
            }
            
            lblOllamaStatus.setText(ollamaData.size() + " modèles configurés");
        }
    }
    
    /**
     * Configuration des actions des boutons
     */
    private void setupActions() {
        // Boutons de vérification
        btnRefreshOpenRouter.setOnAction(e -> refreshOpenRouter());
        btnRefreshOllama.setOnAction(e -> refreshOllama());
        
        // Boutons d'ajout/suppression
        btnAddOpenRouter.setOnAction(e -> addOpenRouterModel());
        btnRemoveOpenRouter.setOnAction(e -> removeOpenRouterModel());
        btnAddOllama.setOnAction(e -> addOllamaModel());
        btnRemoveOllama.setOnAction(e -> removeOllamaModel());
        
        // Boutons d'action
        btnSave.setOnAction(e -> saveAndClose());
        btnCancel.setOnAction(e -> cancel());
    }
    
    /**
     * Rafraîchir la disponibilité des modèles OpenRouter
     */
    private void refreshOpenRouter() {
        lblOpenRouterStatus.setText("🔄 Vérification en cours...");
        btnRefreshOpenRouter.setDisable(true);
        
        new Thread(() -> {
            try {
                // Sauvegarder temporairement les modifications
                updateConfigFromTable();
                
                // Vérifier via l'API
                String apiKey = System.getProperty("openrouter.token", "");
                if (apiKey.isEmpty()) {
                    // Essayer de lire depuis api-keys.properties
                    try (var stream = getClass().getClassLoader().getResourceAsStream("api-keys.properties")) {
                        if (stream != null) {
                            java.util.Properties props = new java.util.Properties();
                            props.load(stream);
                            apiKey = props.getProperty("openrouter.api.key", "").trim();
                        }
                    }
                }
                
                if (!apiKey.isEmpty()) {
                    configManager.verifyOpenRouterModels(apiKey);
                    
                    // Recharger les données pour afficher les statuts
                    javafx.application.Platform.runLater(() -> {
                        loadOpenRouterData();
                        long available = openRouterData.stream()
                            .filter(w -> w.getEntry().isAvailable() && w.getEntry().isEnabled())
                            .count();
                        lblOpenRouterStatus.setText("✓ " + available + "/" + openRouterData.size() + " modèles disponibles");
                        btnRefreshOpenRouter.setDisable(false);
                    });
                } else {
                    javafx.application.Platform.runLater(() -> {
                        lblOpenRouterStatus.setText("⚠️ Token API manquant");
                        btnRefreshOpenRouter.setDisable(false);
                    });
                }
            } catch (Exception ex) {
                javafx.application.Platform.runLater(() -> {
                    lblOpenRouterStatus.setText("✗ Erreur: " + ex.getMessage());
                    btnRefreshOpenRouter.setDisable(false);
                });
            }
        }).start();
    }
    
    /**
     * Rafraîchir la disponibilité des modèles Ollama
     */
    private void refreshOllama() {
        lblOllamaStatus.setText("🔄 Vérification en cours...");
        btnRefreshOllama.setDisable(true);
        
        new Thread(() -> {
            try {
                // Sauvegarder temporairement les modifications
                updateConfigFromTable();
                
                // Vérifier via l'API Ollama
                configManager.verifyOllamaModels();
                
                // Recharger les données pour afficher les statuts
                javafx.application.Platform.runLater(() -> {
                    loadOllamaData();
                    long available = ollamaData.stream()
                        .filter(w -> w.getEntry().isAvailable() && w.getEntry().isEnabled())
                        .count();
                    lblOllamaStatus.setText("✓ " + available + "/" + ollamaData.size() + " modèles disponibles");
                    btnRefreshOllama.setDisable(false);
                });
            } catch (Exception ex) {
                javafx.application.Platform.runLater(() -> {
                    lblOllamaStatus.setText("✗ Erreur: " + ex.getMessage());
                    btnRefreshOllama.setDisable(false);
                });
            }
        }).start();
    }
    
    /**
     * Ajouter un modèle OpenRouter
     */
    private void addOpenRouterModel() {
        // Créer un dialogue personnalisé avec plusieurs champs
        Dialog<ModelEntry> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un modèle OpenRouter");
        dialog.setHeaderText("Nouveau modèle OpenRouter Cloud");
        
        // Boutons
        ButtonType btnAjouter = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAjouter, ButtonType.CANCEL);
        
        // Conteneur principal
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        
        // Lien cliquable
        javafx.scene.control.Hyperlink linkModels = new javafx.scene.control.Hyperlink("🔗 Consultez la liste des modèles disponibles sur OpenRouter");
        linkModels.setOnAction(e -> openURL("https://openrouter.ai/models"));
        vbox.getChildren().add(linkModels);
        
        // Créer le formulaire
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 150, 10, 10));
        
        TextField txtId = new TextField();
        txtId.setPromptText("Ex: google/gemini-2.5-flash");
        TextField txtName = new TextField();
        txtName.setPromptText("Ex: Gemini 2.5 Flash");
        TextField txtDescription = new TextField();
        txtDescription.setPromptText("Ex: Modèle rapide et performant");
        TextField txtPrice = new TextField("0.0");
        txtPrice.setPromptText("Prix par 1M tokens (0.0 pour gratuit)");
        ComboBox<Integer> cbQuality = new ComboBox<>();
        cbQuality.getItems().addAll(1, 2, 3, 4, 5);
        cbQuality.setValue(3);
        
        grid.add(new Label("ID du modèle:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nom d'affichage:"), 0, 1);
        grid.add(txtName, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(txtDescription, 1, 2);
        grid.add(new Label("Prix ($/1M tokens):"), 0, 3);
        grid.add(txtPrice, 1, 3);
        grid.add(new Label("Qualité (★):"), 0, 4);
        grid.add(cbQuality, 1, 4);
        
        // Info supplémentaire
        Label lblInfo = new Label("💡 L'ID doit correspondre exactement au modèle OpenRouter\n" +
                "   Format: fournisseur/nom-modele (ex: anthropic/claude-3.5-sonnet)");
        lblInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
        grid.add(lblInfo, 0, 5, 2, 1);
        
        vbox.getChildren().add(grid);
        dialog.getDialogPane().setContent(vbox);
        
        // Demander le focus sur le premier champ
        javafx.application.Platform.runLater(() -> txtId.requestFocus());
        
        // Convertir le résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAjouter) {
                String id = txtId.getText().trim();
                if (!id.isEmpty()) {
                    ModelEntry newEntry = new ModelEntry();
                    newEntry.setId(id);
                    newEntry.setDisplayName(txtName.getText().trim().isEmpty() ? id : txtName.getText().trim());
                    newEntry.setDescription(txtDescription.getText().trim().isEmpty() ? "Modèle personnalisé" : txtDescription.getText().trim());
                    try {
                        newEntry.setPrice(Double.parseDouble(txtPrice.getText().trim()));
                    } catch (NumberFormatException e) {
                        newEntry.setPrice(0.0);
                    }
                    newEntry.setQuality(cbQuality.getValue());
                    newEntry.setPriority(openRouterData.size() + 1);
                    newEntry.setEnabled(true);
                    return newEntry;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(newEntry -> {
            openRouterData.add(new ModelEntryWrapper(newEntry));
            lblOpenRouterStatus.setText(openRouterData.size() + " modèles configurés");
        });
    }
    
    /**
     * Supprimer un modèle OpenRouter
     */
    private void removeOpenRouterModel() {
        ModelEntryWrapper selected = tableOpenRouter.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Supprimer le modèle");
            confirm.setHeaderText("Confirmer la suppression");
            confirm.setContentText("Supprimer le modèle: " + selected.getEntry().getDisplayName() + " ?");
            
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    openRouterData.remove(selected);
                    lblOpenRouterStatus.setText(openRouterData.size() + " modèles configurés");
                }
            });
        }
    }
    
    /**
     * Ajouter un modèle Ollama
     */
    private void addOllamaModel() {
        // Créer un dialogue personnalisé avec plusieurs champs
        Dialog<ModelEntry> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un modèle Ollama");
        dialog.setHeaderText("Nouveau modèle Ollama Local");
        
        // Boutons
        ButtonType btnAjouter = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAjouter, ButtonType.CANCEL);
        
        // Conteneur principal
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        
        // Lien cliquable
        javafx.scene.control.Hyperlink linkLibrary = new javafx.scene.control.Hyperlink("🔗 Consultez la bibliothèque de modèles Ollama");
        linkLibrary.setOnAction(e -> openURL("https://ollama.ai/library"));
        
        // Info commande
        Label lblCommand = new Label("💡 Pour installer: ollama pull <nom-modele>");
        lblCommand.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");
        
        vbox.getChildren().addAll(linkLibrary, lblCommand);
        
        // Créer le formulaire
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(10, 150, 10, 10));
        
        TextField txtId = new TextField();
        txtId.setPromptText("Ex: llama3.1:latest ou mistral-nemo");
        TextField txtName = new TextField();
        txtName.setPromptText("Ex: Llama 3.1");
        TextField txtDescription = new TextField();
        txtDescription.setPromptText("Ex: Modèle Meta open-source");
        TextField txtSize = new TextField();
        txtSize.setPromptText("Ex: 7GB ou 13GB");
        ComboBox<Integer> cbQuality = new ComboBox<>();
        cbQuality.getItems().addAll(1, 2, 3, 4, 5);
        cbQuality.setValue(3);
        
        grid.add(new Label("ID du modèle:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nom d'affichage:"), 0, 1);
        grid.add(txtName, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(txtDescription, 1, 2);
        grid.add(new Label("Taille:"), 0, 3);
        grid.add(txtSize, 1, 3);
        grid.add(new Label("Qualité (★):"), 0, 4);
        grid.add(cbQuality, 1, 4);
        
        // Info supplémentaire
        Label lblInfo = new Label("💡 L'ID doit correspondre exactement au modèle installé\n" +
                "   Vérifiez avec: ollama list");
        lblInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
        grid.add(lblInfo, 0, 5, 2, 1);
        
        vbox.getChildren().add(grid);
        dialog.getDialogPane().setContent(vbox);
        
        // Demander le focus sur le premier champ
        javafx.application.Platform.runLater(() -> txtId.requestFocus());
        
        // Convertir le résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAjouter) {
                String id = txtId.getText().trim();
                if (!id.isEmpty()) {
                    ModelEntry newEntry = new ModelEntry();
                    newEntry.setId(id);
                    newEntry.setDisplayName(txtName.getText().trim().isEmpty() ? id : txtName.getText().trim());
                    newEntry.setDescription(txtDescription.getText().trim().isEmpty() ? "Modèle personnalisé" : txtDescription.getText().trim());
                    newEntry.setSize(txtSize.getText().trim().isEmpty() ? "?" : txtSize.getText().trim());
                    newEntry.setQuality(cbQuality.getValue());
                    newEntry.setPriority(ollamaData.size() + 1);
                    newEntry.setEnabled(true);
                    return newEntry;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(newEntry -> {
            ollamaData.add(new ModelEntryWrapper(newEntry));
            lblOllamaStatus.setText(ollamaData.size() + " modèles configurés");
        });
    }
    
    /**
     * Supprimer un modèle Ollama
     */
    private void removeOllamaModel() {
        ModelEntryWrapper selected = tableOllama.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Supprimer le modèle");
            confirm.setHeaderText("Confirmer la suppression");
            confirm.setContentText("Supprimer le modèle: " + selected.getEntry().getDisplayName() + " ?");
            
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ollamaData.remove(selected);
                    lblOllamaStatus.setText(ollamaData.size() + " modèles configurés");
                }
            });
        }
    }
    
    /**
     * Met à jour les configurations depuis les tables
     */
    private void updateConfigFromTable() {
        // OpenRouter
        openRouterConfig.setVerifyAtStartup(chkVerifyOpenRouter.isSelected());
        openRouterConfig.getModels().clear();
        for (ModelEntryWrapper wrapper : openRouterData) {
            openRouterConfig.getModels().add(wrapper.getEntry());
        }
        
        // Ollama
        ollamaConfig.setVerifyAtStartup(chkVerifyOllama.isSelected());
        ollamaConfig.setServerUrl(txtOllamaUrl.getText().trim());
        ollamaConfig.getModels().clear();
        for (ModelEntryWrapper wrapper : ollamaData) {
            ollamaConfig.getModels().add(wrapper.getEntry());
        }
    }
    
    /**
     * Sauvegarder et fermer
     */
    private void saveAndClose() {
        try {
            // Mettre à jour les configs
            updateConfigFromTable();
            
            // Sauvegarder dans les fichiers JSON
            configManager.saveOpenRouterConfig(openRouterConfig);
            configManager.saveOllamaConfig(ollamaConfig);
            
            // Afficher confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sauvegarde réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les configurations ont été sauvegardées.\nRelancez l'application pour appliquer les changements.");
            alert.showAndWait();
            
            // Fermer la fenêtre
            closeWindow();
            
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de sauvegarde");
            alert.setHeaderText("Impossible de sauvegarder");
            alert.setContentText("Erreur: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Annuler et fermer
     */
    private void cancel() {
        closeWindow();
    }
    
    /**
     * Fermer la fenêtre
     */
    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Ouvre une URL dans le navigateur par défaut
     * Compatible Linux/Windows/Mac
     */
    private void openURL(String url) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("[URL] Ouverture de: " + url);
            System.out.println("[URL] OS détecté: " + os);
            
            if (os.contains("linux")) {
                // Linux : essayer plusieurs méthodes
                boolean opened = false;
                
                // Méthode 1 : Chercher un navigateur commun
                String[] browsers = {"firefox", "google-chrome", "chromium", "brave-browser", "vivaldi", "opera"};
                for (String browser : browsers) {
                    try {
                        Process checkProcess = new ProcessBuilder("which", browser).start();
                        checkProcess.waitFor();
                        if (checkProcess.exitValue() == 0) {
                            System.out.println("[URL] Navigateur trouvé: " + browser);
                            new ProcessBuilder(browser, url).start();
                            opened = true;
                            System.out.println("[URL] ✓ Navigateur " + browser + " lancé");
                            break;
                        }
                    } catch (Exception e) {
                        // Essayer le suivant
                    }
                }
                
                // Méthode 2 : Si aucun navigateur trouvé, essayer xdg-open
                if (!opened) {
                    System.out.println("[URL] Fallback sur xdg-open");
                    ProcessBuilder pb = new ProcessBuilder("xdg-open", url);
                    pb.inheritIO(); // Hériter des flux d'entrée/sortie pour voir les erreurs
                    Process process = pb.start();
                    
                    // Attendre un peu pour voir si ça marche
                    Thread.sleep(1000);
                    if (!process.isAlive()) {
                        int exitCode = process.exitValue();
                        if (exitCode != 0) {
                            System.err.println("[URL] xdg-open a échoué avec le code: " + exitCode);
                            throw new Exception("Impossible d'ouvrir le navigateur");
                        }
                    }
                    System.out.println("[URL] ✓ xdg-open lancé");
                }
                
            } else if (os.contains("mac")) {
                // macOS : utiliser open
                System.out.println("[URL] Utilisation de open");
                new ProcessBuilder("open", url).start();
            } else if (os.contains("win")) {
                // Windows : utiliser cmd /c start
                System.out.println("[URL] Utilisation de cmd /c start");
                new ProcessBuilder("cmd", "/c", "start", url).start();
            } else {
                // Fallback : essayer Desktop.browse()
                System.out.println("[URL] Plateforme non reconnue, fallback Desktop.browse()");
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                } else {
                    throw new Exception("Plateforme non supportée: " + os);
                }
            }
        } catch (Exception e) {
            System.err.println("[URL] Erreur lors de l'ouverture: " + e.getMessage());
            e.printStackTrace();
            // Afficher un dialogue avec l'URL à copier
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ouvrir le lien");
                alert.setHeaderText("Impossible d'ouvrir automatiquement le navigateur");
                alert.setContentText("Copiez ce lien dans votre navigateur:\n\n" + url);
                
                // Ajouter un bouton pour copier l'URL
                javafx.scene.layout.VBox content = new javafx.scene.layout.VBox(10);
                content.setPadding(new javafx.geometry.Insets(10));
                
                Label lblUrl = new Label(url);
                lblUrl.setStyle("-fx-font-family: monospace; -fx-background-color: #f0f0f0; -fx-padding: 5px;");
                lblUrl.setWrapText(true);
                
                Button btnCopy = new Button("📋 Copier le lien");
                btnCopy.setOnAction(ev -> {
                    javafx.scene.input.ClipboardContent clipContent = new javafx.scene.input.ClipboardContent();
                    clipContent.putString(url);
                    javafx.scene.input.Clipboard.getSystemClipboard().setContent(clipContent);
                    btnCopy.setText("✓ Copié !");
                });
                
                content.getChildren().addAll(
                    new Label("Le navigateur n'a pas pu être ouvert automatiquement."),
                    new Label("Copiez ce lien :"),
                    lblUrl,
                    btnCopy
                );
                
                alert.getDialogPane().setContent(content);
                alert.showAndWait();
            });
        }
    }
    
    /**
     * Wrapper pour ModelEntry avec JavaFX Properties
     */
    public static class ModelEntryWrapper {
        private final ModelEntry entry;
        private final SimpleBooleanProperty enabled;
        private final SimpleIntegerProperty priority;
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty price;
        private final SimpleStringProperty size;
        private final SimpleStringProperty quality;
        private final SimpleStringProperty available;
        
        public ModelEntryWrapper(ModelEntry entry) {
            this.entry = entry;
            this.enabled = new SimpleBooleanProperty(entry.isEnabled());
            this.priority = new SimpleIntegerProperty(entry.getPriority());
            this.id = new SimpleStringProperty(entry.getId());
            this.name = new SimpleStringProperty(entry.getDisplayName());
            
            // Prix ou taille selon le type
            if (entry.getPrice() > 0) {
                this.price = new SimpleStringProperty(String.format("%.3f", entry.getPrice()));
            } else {
                this.price = new SimpleStringProperty("GRATUIT");
            }
            
            if (entry.getSize() != null && !entry.getSize().isEmpty()) {
                this.size = new SimpleStringProperty(entry.getSize());
            } else {
                this.size = new SimpleStringProperty("-");
            }
            
            // Qualité en étoiles
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < entry.getQuality(); i++) {
                stars.append("★");
            }
            this.quality = new SimpleStringProperty(stars.toString());
            
            // Disponibilité
            this.available = new SimpleStringProperty(entry.isAvailable() ? "✓" : "✗");
            
            // Lier les changements
            this.enabled.addListener((obs, oldVal, newVal) -> entry.setEnabled(newVal));
            this.priority.addListener((obs, oldVal, newVal) -> entry.setPriority(newVal.intValue()));
        }
        
        public ModelEntry getEntry() { return entry; }
        
        public SimpleBooleanProperty enabledProperty() { return enabled; }
        public SimpleIntegerProperty priorityProperty() { return priority; }
        public SimpleStringProperty idProperty() { return id; }
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleStringProperty priceProperty() { return price; }
        public SimpleStringProperty sizeProperty() { return size; }
        public SimpleStringProperty qualityProperty() { return quality; }
        public SimpleStringProperty availableProperty() { return available; }
        
        public void setPriority(int priority) {
            this.priority.set(priority);
        }
    }
}
