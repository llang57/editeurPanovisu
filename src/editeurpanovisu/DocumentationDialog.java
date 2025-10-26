package editeurpanovisu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Dialogue pour afficher la documentation au format Markdown
 */
public class DocumentationDialog extends Stage {
    
    private final WebView webView;
    private final MarkdownViewer markdownViewer;
    private final ComboBox<DocumentItem> documentSelector;
    private final ResourceBundle bundle;
    
    /**
     * Enumération des documents disponibles
     */
    public enum DocumentType {
        AIDE("Aide Utilisateur", "aide/aide.md"),
        PRESENTATION("Présentation", "doc/PRESENTATION.md"),
        INSTALLATION_OLLAMA("Installation Ollama", "doc/INSTALLATION_OLLAMA.md");
        
        private final String titre;
        private final String chemin;
        
        DocumentType(String titre, String chemin) {
            this.titre = titre;
            this.chemin = chemin;
        }
        
    /**
     * Obtient le titre du document de documentation
     * 
     * @return Titre du document
     */
        public String getTitre() {
            return titre;
        }
        
    /**
     * Obtient le chemin vers le fichier de documentation
     * 
     * @return Chemin du fichier
     */
        public String getChemin() {
            return chemin;
        }
    }
    
    /**
     * Item de document pour le ComboBox
     */
    private static class DocumentItem {
        private final DocumentType type;
        
        public DocumentItem(DocumentType type) {
            this.type = type;
        }
        
        @Override
        public String toString() {
            return type.getTitre();
        }
        
    /**
     * Obtient le type de documentation (MARKDOWN, HTML, etc.)
     * 
     * @return Type de documentation
     */
        public DocumentType getType() {
            return type;
        }
    }
    
    /**
     * Constructeur
     */
    public DocumentationDialog() {
        this.markdownViewer = new MarkdownViewer();
        this.webView = new WebView();
        this.bundle = ResourceBundle.getBundle("editeurpanovisu/i18n/PanoVisu");
        
        // Sélecteur de documents
        documentSelector = new ComboBox<>();
        for (DocumentType type : DocumentType.values()) {
            documentSelector.getItems().add(new DocumentItem(type));
        }
        documentSelector.setValue(new DocumentItem(DocumentType.AIDE));
        documentSelector.setOnAction(e -> chargerDocumentSelectionne());
        
        // Boutons
        Button btnOuvrir = new Button("Ouvrir fichier...");
        btnOuvrir.setOnAction(e -> ouvrirFichierMarkdown());
        
        Button btnFermer = new Button("Fermer");
        btnFermer.setOnAction(e -> close());
        
        // Barre d'outils
        ToolBar toolBar = new ToolBar(
            new Label("Document : "),
            documentSelector,
            new Separator(),
            btnOuvrir
        );
        
        // Boutons en bas
        HBox boutonsBox = new HBox(10, btnFermer);
        boutonsBox.setPadding(new Insets(10));
        boutonsBox.setStyle("-fx-alignment: center-right;");
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(webView);
        root.setBottom(boutonsBox);
        
        // Configuration de la fenêtre
        setTitle("Documentation - EditeurPanovisu");
        setScene(new Scene(root, 1000, 700));
        
        // Charger le document par défaut
        chargerDocumentSelectionne();
    }
    
    /**
     * Charge le document sélectionné dans le ComboBox
     */
    private void chargerDocumentSelectionne() {
        DocumentItem item = documentSelector.getValue();
        if (item != null) {
            chargerDocument(item.getType());
        }
    }
    
    /**
     * Charge un document Markdown
     * 
     * @param type Type de document à charger
     */
    public void chargerDocument(DocumentType type) {
        try {
            File file = new File(type.getChemin());
            if (file.exists()) {
                markdownViewer.afficherFichierMarkdown(webView, type.getChemin());
            } else {
                afficherErreur("Fichier non trouvé : " + type.getChemin());
            }
        } catch (IOException e) {
            afficherErreur("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    
    /**
     * Ouvre un dialogue pour sélectionner un fichier Markdown
     */
    private void ouvrirFichierMarkdown() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier Markdown");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Fichiers Markdown", "*.md", "*.markdown"),
            new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        
        File selectedFile = fileChooser.showOpenDialog(this);
        if (selectedFile != null) {
            try {
                markdownViewer.afficherFichierMarkdown(webView, selectedFile.getAbsolutePath());
            } catch (IOException e) {
                afficherErreur("Erreur lors de la lecture du fichier : " + e.getMessage());
            }
        }
    }
    
    /**
     * Affiche un message d'erreur dans la WebView
     */
    private void afficherErreur(String message) {
        String markdown = "# ⚠️ Erreur\n\n" + message;
        markdownViewer.afficherMarkdown(webView, markdown);
    }
    
    /**
     * Méthode statique pour afficher le dialogue
     */
    public static void afficher() {
        DocumentationDialog dialog = new DocumentationDialog();
        dialog.show();
    }
    
    /**
     * Méthode statique pour afficher un document spécifique
     */
    public static void afficher(DocumentType type) {
        DocumentationDialog dialog = new DocumentationDialog();
        dialog.chargerDocument(type);
        dialog.show();
    }
    
    /**
     * Application de test
     */
    public static class TestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            Button btnDoc = new Button("Afficher Documentation");
            btnDoc.setOnAction(e -> DocumentationDialog.afficher());
            
            Scene scene = new Scene(new BorderPane(btnDoc), 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test Documentation");
            primaryStage.show();
        }
        
    /**
     * Point d'entrée pour tester la fenêtre de documentation
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */
        public static void main(String[] args) {
            launch(args);
        }
    }
}
