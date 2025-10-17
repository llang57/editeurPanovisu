/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package editeurpanovisu;

import atlantafx.base.theme.Styles;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * Contrôleur pour le dialogue de conversion d'images au ratio 2:1.
 * 
 * <p>Ce contrôleur gère l'interface et la logique pour convertir des images
 * au format équirectangulaire 2:1 (largeur = 2 × hauteur), idéal pour les
 * panoramas 360°.</p>
 * 
 * <p>Fonctionnalités principales :</p>
 * <ul>
 * <li>Glisser-déposer de fichiers images</li>
 * <li>Conversion automatique au ratio 2:1</li>
 * <li>Options de remplissage : Noir, Flou, Miroir</li>
 * <li>Choix du format de sortie (JPEG, PNG, WEBP)</li>
 * <li>Contrôle de qualité pour JPEG</li>
 * <li>Trois modes de sauvegarde :
 *   <ul>
 *   <li>Répertoire d'origine avec suffixe</li>
 *   <li>Répertoire d'origine avec écrasement</li>
 *   <li>Répertoire personnalisé</li>
 *   </ul>
 * </li>
 * </ul>
 *
 * @author Laurent Lang
 * @version 3.1.0
 */
public class ConversionRatio2to1DialogController {
    
    // ========== PROPRIÉTÉS DE L'INTERFACE ==========
    
    /**
     * Stage principal du dialogue.
     */
    private Stage stConversion;
    
    /**
     * Panneau racine de l'interface.
     */
    private AnchorPane apConversion;
    
    /**
     * Zone de drag & drop pour ajouter des fichiers.
     */
    private VBox vbZoneDragDrop;
    
    /**
     * Liste des fichiers à traiter.
     */
    private ListView<File> lvListeFichiers;
    
    /**
     * Liste observable des fichiers.
     */
    private ObservableList<File> olFichiers;
    
    /**
     * RadioButton pour le format JPEG.
     */
    private RadioButton rbJpeg;
    
    /**
     * RadioButton pour le format PNG.
     */
    private RadioButton rbPng;
    
    /**
     * RadioButton pour le format WEBP.
     */
    private RadioButton rbWebp;
    
    /**
     * ToggleGroup pour les formats de sortie.
     */
    private ToggleGroup tgFormatSortie;
    
    /**
     * Slider pour la qualité JPEG (0-100%).
     */
    private Slider slQualiteJpeg;
    
    /**
     * Label affichant la valeur du slider de qualité.
     */
    private Label lblQualiteValeur;
    
    /**
     * RadioButton pour le remplissage noir.
     */
    private RadioButton rbRemplissageNoir;
    
    /**
     * RadioButton pour le remplissage flou.
     */
    private RadioButton rbRemplissageFlou;
    
    /**
     * RadioButton pour le remplissage miroir.
     */
    private RadioButton rbRemplissageMiroir;
    
    /**
     * RadioButton pour le remplissage par couleur moyenne.
     */
    private RadioButton rbRemplissageCouleurMoyenne;
    
    /**
     * ToggleGroup pour les types de remplissage.
     */
    private ToggleGroup tgRemplissage;
    
    /**
     * RadioButton pour sauvegarder dans le répertoire d'origine.
     */
    private RadioButton rbRepertoireOrigine;
    
    /**
     * RadioButton pour sauvegarder dans un répertoire personnalisé.
     */
    private RadioButton rbRepertoirePersonnalise;
    
    /**
     * ToggleGroup pour le mode de répertoire de sortie.
     */
    private ToggleGroup tgRepertoireSortie;
    
    /**
     * CheckBox pour écraser les fichiers d'origine.
     */
    private CheckBox cbEcraserOrigine;
    
    /**
     * TextField pour le suffixe des fichiers.
     */
    private TextField tfSuffixe;
    
    /**
     * TextField pour le chemin du répertoire personnalisé.
     */
    private TextField tfRepertoirePersonnalise;
    
    /**
     * Bouton pour parcourir les répertoires.
     */
    private Button btnParcourir;
    
    /**
     * Bouton pour ajouter des fichiers.
     */
    private Button btnAjouter;
    
    /**
     * Bouton pour supprimer des fichiers.
     */
    private Button btnSupprimer;
    
    /**
     * Bouton pour prévisualiser la conversion.
     */
    private Button btnPrevisualiser;
    
    /**
     * Bouton pour valider et lancer le traitement.
     */
    private Button btnValider;
    
    /**
     * Bouton pour annuler et fermer le dialogue.
     */
    private Button btnAnnuler;
    
    /**
     * Barre de progression du traitement.
     */
    private ProgressBar pbProgression;
    
    /**
     * Label affichant l'état du traitement.
     */
    private Label lblEtat;
    
    /**
     * Bundle de ressources pour la localisation.
     */
    private ResourceBundle rbLocalisation;
    
    // ========== VARIABLES DE TRAITEMENT ==========
    
    /**
     * Nombre de fichiers traités.
     */
    private int iNombreFichiersTraites = 0;
    
    /**
     * Nombre total de fichiers à traiter.
     */
    private int iNombreFichiersTotal = 0;
    
    /**
     * Indique si un traitement est en cours.
     */
    private boolean bTraitementEnCours = false;
    
    /**
     * Map pour stocker les paramètres individuels de chaque fichier.
     * Clé: chemin absolu du fichier
     * Valeur: tableau [positionVerticale (0.0-1.0), typeRemplissage (0-3)]
     */
    private java.util.Map<String, double[]> parametresIndividuels = new java.util.HashMap<>();

    /**
     * Constructeur par défaut.
     * Initialise le contrôleur sans créer l'interface.
     */
    public ConversionRatio2to1DialogController() {
        // Constructeur vide - l'interface sera créée par afficheFenetre()
    }

    /**
     * Affiche la fenêtre de dialogue de conversion au ratio 2:1.
     * 
     * <p>Crée et configure tous les composants de l'interface graphique :</p>
     * <ul>
     * <li>Zone de drag & drop pour ajouter des images</li>
     * <li>Liste des fichiers à traiter</li>
     * <li>Options de remplissage (noir, flou, miroir)</li>
     * <li>Sélection du format de sortie</li>
     * <li>Contrôle de qualité JPEG</li>
     * <li>Boutons d'action (valider, annuler, ajouter, supprimer)</li>
     * </ul>
     *
     * @throws Exception si une erreur survient lors de la création de l'interface
     */
    public void afficheFenetre() throws Exception {
        // Chargement du bundle de localisation
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu");
        
        // Création de la fenêtre principale
        stConversion = new Stage();
        stConversion.setResizable(false);
        stConversion.initModality(Modality.APPLICATION_MODAL);
        stConversion.initOwner(EditeurPanovisu.getStPrincipal());
        stConversion.setTitle(rbLocalisation.getString("outilsConversionRatio2to1"));

        // Création du panneau racine
        apConversion = new AnchorPane();
        apConversion.setPrefSize(800, 600);
        apConversion.setStyle("-fx-background-color: -color-bg-default;");

        // Conteneur principal vertical
        VBox vbPrincipal = new VBox(15);
        vbPrincipal.setPadding(new Insets(20));
        vbPrincipal.setPrefWidth(800);
        
        // ========== ZONE DE DRAG & DROP ==========
        creerZoneDragDrop();
        vbPrincipal.getChildren().add(vbZoneDragDrop);
        
        // ========== LISTE DES FICHIERS ==========
        VBox vbListeFichiers = creerZoneListeFichiers();
        vbPrincipal.getChildren().add(vbListeFichiers);
        
        // ========== OPTIONS DE CONVERSION ==========
        HBox hbOptions = creerZoneOptions();
        vbPrincipal.getChildren().add(hbOptions);
        
        // ========== RÉPERTOIRE DE SORTIE ==========
        VBox vbRepertoireSortie = creerZoneRepertoireSortie();
        vbPrincipal.getChildren().add(vbRepertoireSortie);
        
        // ========== BARRE DE PROGRESSION ==========
        VBox vbProgression = creerZoneProgression();
        vbPrincipal.getChildren().add(vbProgression);
        
        // ========== BOUTONS D'ACTION ==========
        HBox hbBoutons = creerBoutonsAction();
        vbPrincipal.getChildren().add(hbBoutons);
        
        // Ajout du conteneur principal au panneau racine
        apConversion.getChildren().add(vbPrincipal);
        AnchorPane.setTopAnchor(vbPrincipal, 0.0);
        AnchorPane.setLeftAnchor(vbPrincipal, 0.0);
        AnchorPane.setRightAnchor(vbPrincipal, 0.0);
        
        // Configuration de la scène
        Scene scene = new Scene(apConversion);
        stConversion.setScene(scene);
        
        // Affichage de la fenêtre
        stConversion.show();
    }

    /**
     * Crée la zone de drag & drop pour ajouter des images.
     * 
     * <p>Cette zone permet à l'utilisateur de glisser-déposer des fichiers
     * d'images directement dans l'application.</p>
     */
    private void creerZoneDragDrop() {
        vbZoneDragDrop = new VBox(10);
        vbZoneDragDrop.setAlignment(Pos.CENTER);
        vbZoneDragDrop.setPrefHeight(100);
        vbZoneDragDrop.setStyle(
            "-fx-border-color: -color-accent-emphasis; " +
            "-fx-border-width: 2; " +
            "-fx-border-style: dashed; " +
            "-fx-border-radius: 5; " +
            "-fx-background-color: -color-bg-subtle; " +
            "-fx-background-radius: 5;"
        );
        
        // Icône de drag & drop
        ImageView ivDragDrop = new ImageView();
        try {
            ivDragDrop = EditeurPanovisu.loadSvgIcon("upload", 48);
            ivDragDrop.setOpacity(0.6);
        } catch (Exception e) {
            Logger.getLogger(ConversionRatio2to1DialogController.class.getName())
                    .log(Level.SEVERE, "Erreur chargement icône upload", e);
        }
        
        // Texte d'instruction
        Label lblInstruction = new Label(rbLocalisation.getString("conversion.dragDropImages"));
        lblInstruction.setStyle("-fx-font-size: 14px; -fx-text-fill: -color-fg-muted;");
        
        vbZoneDragDrop.getChildren().addAll(ivDragDrop, lblInstruction);
        
        // Gestion du drag & drop
        configurerDragDrop();
    }
    
    /**
     * Configure les gestionnaires d'événements pour le drag & drop.
     */
    private void configurerDragDrop() {
        vbZoneDragDrop.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
                vbZoneDragDrop.setStyle(
                    "-fx-border-color: -color-accent-emphasis; " +
                    "-fx-border-width: 3; " +
                    "-fx-border-style: solid; " +
                    "-fx-border-radius: 5; " +
                    "-fx-background-color: -color-accent-subtle; " +
                    "-fx-background-radius: 5;"
                );
            }
            event.consume();
        });
        
        vbZoneDragDrop.setOnDragExited((DragEvent event) -> {
            vbZoneDragDrop.setStyle(
                "-fx-border-color: -color-accent-emphasis; " +
                "-fx-border-width: 2; " +
                "-fx-border-style: dashed; " +
                "-fx-border-radius: 5; " +
                "-fx-background-color: -color-bg-subtle; " +
                "-fx-background-radius: 5;"
            );
            event.consume();
        });
        
        vbZoneDragDrop.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasFiles()) {
                ajouterFichiers(db.getFiles());
                success = true;
            }
            
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
    /**
     * Crée la zone d'affichage de la liste des fichiers.
     *
     * @return VBox contenant la liste des fichiers
     */
    private VBox creerZoneListeFichiers() {
        VBox vbListe = new VBox(10);
        
        // Label titre
        Label lblTitre = new Label(rbLocalisation.getString("conversion.fichiersATraiter"));
        lblTitre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // ListView des fichiers
        olFichiers = FXCollections.observableArrayList();
        lvListeFichiers = new ListView<>(olFichiers);
        lvListeFichiers.setPrefHeight(150);
        
        // Configuration du rendu des cellules
        lvListeFichiers.setCellFactory(param -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty || file == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    
                    // Nom du fichier
                    Label lblNom = new Label(file.getName());
                    lblNom.setStyle("-fx-font-weight: bold;");
                    
                    // Taille du fichier
                    Label lblTaille = new Label("(" + formatTailleFichier(file.length()) + ")");
                    lblTaille.setStyle("-fx-text-fill: -color-fg-muted;");
                    
                    hbox.getChildren().addAll(lblNom, lblTaille);
                    setGraphic(hbox);
                }
            }
        });
        
        // Boutons d'action
        HBox hbBoutonsListe = new HBox(10);
        hbBoutonsListe.setAlignment(Pos.CENTER_LEFT);
        
        btnAjouter = new Button(rbLocalisation.getString("conversion.ajouterFichiers"));
        btnAjouter.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS);
        btnAjouter.setOnAction(e -> ajouterFichiersManuel());
        
        btnSupprimer = new Button(rbLocalisation.getString("conversion.supprimerSelection"));
        btnSupprimer.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.DANGER);
        btnSupprimer.setOnAction(e -> supprimerSelection());
        btnSupprimer.setDisable(true);
        
        btnPrevisualiser = new Button(rbLocalisation.getString("conversion.previsualisation"));
        btnPrevisualiser.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.SUCCESS);
        btnPrevisualiser.setOnAction(e -> previsualiserSelection());
        
        hbBoutonsListe.getChildren().addAll(btnAjouter, btnSupprimer, btnPrevisualiser);
        
        // Activation du bouton supprimer quand un élément est sélectionné
        lvListeFichiers.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> btnSupprimer.setDisable(newVal == null)
        );
        
        vbListe.getChildren().addAll(lblTitre, lvListeFichiers, hbBoutonsListe);
        return vbListe;
    }
    
    /**
     * Crée la zone des options de conversion.
     *
     * @return HBox contenant les options
     */
    private HBox creerZoneOptions() {
        HBox hbOptions = new HBox(20);
        hbOptions.setAlignment(Pos.TOP_LEFT);
        
        // ========== OPTIONS DE REMPLISSAGE ==========
        VBox vbRemplissage = new VBox(10);
        
        Label lblRemplissage = new Label(rbLocalisation.getString("conversion.typeRemplissage"));
        lblRemplissage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        tgRemplissage = new ToggleGroup();
        
        rbRemplissageNoir = new RadioButton(rbLocalisation.getString("conversion.remplissageNoir"));
        rbRemplissageNoir.setToggleGroup(tgRemplissage);
        rbRemplissageNoir.setSelected(true);
        
        rbRemplissageFlou = new RadioButton(rbLocalisation.getString("conversion.remplissageFlou"));
        rbRemplissageFlou.setToggleGroup(tgRemplissage);
        
        rbRemplissageMiroir = new RadioButton(rbLocalisation.getString("conversion.remplissageMiroir"));
        rbRemplissageMiroir.setToggleGroup(tgRemplissage);
        
        rbRemplissageCouleurMoyenne = new RadioButton(rbLocalisation.getString("conversion.remplissageCouleurMoyenne"));
        rbRemplissageCouleurMoyenne.setToggleGroup(tgRemplissage);
        
        vbRemplissage.getChildren().addAll(lblRemplissage, rbRemplissageNoir, rbRemplissageFlou, 
                                           rbRemplissageMiroir, rbRemplissageCouleurMoyenne);
        
        // ========== FORMAT DE SORTIE ==========
        VBox vbFormat = new VBox(10);
        
        Label lblFormat = new Label(rbLocalisation.getString("conversion.formatSortie"));
        lblFormat.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        tgFormatSortie = new ToggleGroup();
        
        rbJpeg = new RadioButton("JPEG");
        rbJpeg.setToggleGroup(tgFormatSortie);
        rbJpeg.setSelected(true);
        
        rbPng = new RadioButton("PNG");
        rbPng.setToggleGroup(tgFormatSortie);
        
        rbWebp = new RadioButton("WEBP");
        rbWebp.setToggleGroup(tgFormatSortie);
        
        // Slider de qualité JPEG
        HBox hbQualite = new HBox(10);
        hbQualite.setAlignment(Pos.CENTER_LEFT);
        
        Label lblQualite = new Label(rbLocalisation.getString("conversion.qualiteJpeg") + " :");
        slQualiteJpeg = new Slider(0, 100, 85);
        slQualiteJpeg.setPrefWidth(150);
        slQualiteJpeg.setShowTickMarks(true);
        slQualiteJpeg.setShowTickLabels(true);
        slQualiteJpeg.setMajorTickUnit(25);
        
        lblQualiteValeur = new Label("85%");
        lblQualiteValeur.setStyle("-fx-font-weight: bold;");
        
        slQualiteJpeg.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblQualiteValeur.setText(String.format("%d%%", newVal.intValue()));
        });
        
        hbQualite.getChildren().addAll(lblQualite, slQualiteJpeg, lblQualiteValeur);
        
        // Désactiver le slider si JPEG n'est pas sélectionné
        slQualiteJpeg.setDisable(!rbJpeg.isSelected());
        lblQualite.setDisable(!rbJpeg.isSelected());
        
        tgFormatSortie.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isJpeg = newVal == rbJpeg;
            slQualiteJpeg.setDisable(!isJpeg);
            lblQualite.setDisable(!isJpeg);
        });
        
        vbFormat.getChildren().addAll(lblFormat, rbJpeg, rbPng, rbWebp, hbQualite);
        
        hbOptions.getChildren().addAll(vbRemplissage, vbFormat);
        return hbOptions;
    }
    
    /**
     * Crée la zone de sélection du répertoire de sortie.
     *
     * @return VBox contenant les options de sortie
     */
    private VBox creerZoneRepertoireSortie() {
        VBox vbSortie = new VBox(10);
        
        Label lblTitre = new Label(rbLocalisation.getString("conversion.optionsSauvegarde"));
        lblTitre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        tgRepertoireSortie = new ToggleGroup();
        
        // Option 1 : Répertoire d'origine
        rbRepertoireOrigine = new RadioButton(rbLocalisation.getString("conversion.repertoireOrigine"));
        rbRepertoireOrigine.setToggleGroup(tgRepertoireSortie);
        rbRepertoireOrigine.setSelected(true);
        
        // Sous-options pour répertoire d'origine
        VBox vbSousOptionsOrigine = new VBox(10);
        vbSousOptionsOrigine.setPadding(new Insets(0, 0, 0, 30));
        
        cbEcraserOrigine = new CheckBox(rbLocalisation.getString("conversion.ecraserOrigine"));
        
        HBox hbSuffixe = new HBox(10);
        hbSuffixe.setAlignment(Pos.CENTER_LEFT);
        Label lblSuffixe = new Label(rbLocalisation.getString("conversion.suffixe") + " :");
        tfSuffixe = new TextField("_2to1");
        tfSuffixe.setPrefWidth(150);
        hbSuffixe.getChildren().addAll(lblSuffixe, tfSuffixe);
        
        // Désactiver le suffixe si on écrase
        tfSuffixe.setDisable(cbEcraserOrigine.isSelected());
        cbEcraserOrigine.selectedProperty().addListener((obs, oldVal, newVal) -> {
            tfSuffixe.setDisable(newVal);
        });
        
        vbSousOptionsOrigine.getChildren().addAll(cbEcraserOrigine, hbSuffixe);
        
        // Option 2 : Répertoire personnalisé
        rbRepertoirePersonnalise = new RadioButton(rbLocalisation.getString("conversion.repertoirePersonnalise"));
        rbRepertoirePersonnalise.setToggleGroup(tgRepertoireSortie);
        
        HBox hbRepertoirePerso = new HBox(10);
        hbRepertoirePerso.setPadding(new Insets(0, 0, 0, 30));
        hbRepertoirePerso.setAlignment(Pos.CENTER_LEFT);
        
        tfRepertoirePersonnalise = new TextField();
        tfRepertoirePersonnalise.setPrefWidth(400);
        tfRepertoirePersonnalise.setPromptText(rbLocalisation.getString("conversion.choisirRepertoire"));
        tfRepertoirePersonnalise.setDisable(true);
        
        btnParcourir = new Button(rbLocalisation.getString("conversion.parcourir"));
        btnParcourir.setDisable(true);
        btnParcourir.setOnAction(e -> parcourirRepertoire());
        
        hbRepertoirePerso.getChildren().addAll(tfRepertoirePersonnalise, btnParcourir);
        
        // Activer/désactiver les champs selon le choix
        tgRepertoireSortie.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPerso = newVal == rbRepertoirePersonnalise;
            tfRepertoirePersonnalise.setDisable(!isPerso);
            btnParcourir.setDisable(!isPerso);
            
            boolean isOrigine = newVal == rbRepertoireOrigine;
            cbEcraserOrigine.setDisable(!isOrigine);
            tfSuffixe.setDisable(!isOrigine || cbEcraserOrigine.isSelected());
        });
        
        vbSortie.getChildren().addAll(
            lblTitre, 
            rbRepertoireOrigine, 
            vbSousOptionsOrigine,
            rbRepertoirePersonnalise,
            hbRepertoirePerso
        );
        
        return vbSortie;
    }
    
    /**
     * Crée la zone de progression du traitement.
     *
     * @return VBox contenant la barre de progression
     */
    private VBox creerZoneProgression() {
        VBox vbProgression = new VBox(5);
        
        pbProgression = new ProgressBar(0);
        pbProgression.setPrefWidth(760);
        pbProgression.setVisible(false);
        
        lblEtat = new Label();
        lblEtat.setStyle("-fx-font-size: 12px; -fx-text-fill: -color-fg-muted;");
        lblEtat.setVisible(false);
        
        vbProgression.getChildren().addAll(pbProgression, lblEtat);
        return vbProgression;
    }
    
    /**
     * Crée les boutons d'action (Valider, Annuler).
     *
     * @return HBox contenant les boutons
     */
    private HBox creerBoutonsAction() {
        HBox hbBoutons = new HBox(10);
        hbBoutons.setAlignment(Pos.CENTER_RIGHT);
        
        btnValider = new Button(rbLocalisation.getString("conversion.valider"));
        btnValider.getStyleClass().addAll(Styles.ACCENT);
        btnValider.setDefaultButton(true);
        btnValider.setOnAction(e -> lancerTraitement());
        
        btnAnnuler = new Button(rbLocalisation.getString("conversion.annuler"));
        btnAnnuler.setCancelButton(true);
        btnAnnuler.setOnAction(e -> fermerFenetre());
        
        hbBoutons.getChildren().addAll(btnValider, btnAnnuler);
        return hbBoutons;
    }
    
    /**
     * Prévisualise la conversion pour le fichier sélectionné.
     */
    private void previsualiserSelection() {
        File fichierSelectionne = lvListeFichiers.getSelectionModel().getSelectedItem();
        
        if (fichierSelectionne == null) {
            if (olFichiers.isEmpty()) {
                afficherErreur(rbLocalisation.getString("conversion.erreurAucunFichier"));
                return;
            }
            fichierSelectionne = olFichiers.get(0);
        }
        
        // Récupérer les paramètres individuels existants pour ce fichier
        String cheminFichier = fichierSelectionne.getAbsolutePath();
        double[] parametresExistants = parametresIndividuels.get(cheminFichier);
        
        String typeRemplissage = "noir";
        double positionVerticale = 0.5;
        
        if (parametresExistants != null) {
            // Charger les paramètres sauvegardés
            positionVerticale = parametresExistants[0];
            int typeRemplissageIndex = (int) parametresExistants[1];
            
            switch (typeRemplissageIndex) {
                case 0: typeRemplissage = "noir"; break;
                case 1: typeRemplissage = "flou"; break;
                case 2: typeRemplissage = "miroir"; break;
                case 3: typeRemplissage = "couleurMoyenne"; break;
            }
        } else {
            // Utiliser les paramètres globaux actuels
            if (rbRemplissageFlou.isSelected()) {
                typeRemplissage = "flou";
            } else if (rbRemplissageMiroir.isSelected()) {
                typeRemplissage = "miroir";
            } else if (rbRemplissageCouleurMoyenne.isSelected()) {
                typeRemplissage = "couleurMoyenne";
            }
        }
        
        // Ouvrir le dialogue de prévisualisation avec les paramètres chargés
        PrevisualiserConversionDialog dialog = new PrevisualiserConversionDialog(
            fichierSelectionne, typeRemplissage
        );
        dialog.setPositionVerticale(positionVerticale);
        
        if (dialog.afficher()) {
            // L'utilisateur a validé, sauvegarder les paramètres individuels
            typeRemplissage = dialog.getTypeRemplissage();
            positionVerticale = dialog.getPositionVerticale();
            
            // Convertir le type de remplissage en index
            int typeRemplissageIndex = 0;
            switch (typeRemplissage.toLowerCase()) {
                case "flou": typeRemplissageIndex = 1; break;
                case "miroir": typeRemplissageIndex = 2; break;
                case "couleurmoyenne": typeRemplissageIndex = 3; break;
                default: typeRemplissageIndex = 0; // noir
            }
            
            // Sauvegarder dans la map
            parametresIndividuels.put(cheminFichier, new double[] {positionVerticale, typeRemplissageIndex});
        }
    }
    
    // ========== MÉTHODES DE GESTION DES FICHIERS ==========
    
    /**
     * Ajoute des fichiers à la liste après validation.
     *
     * @param fichiers Liste de fichiers à ajouter
     */
    private void ajouterFichiers(List<File> fichiers) {
        for (File file : fichiers) {
            if (estImageValide(file) && !olFichiers.contains(file)) {
                olFichiers.add(file);
                
                // Mémoriser le répertoire
                EditeurPanovisu.setStrDernierRepertoireImages(file.getParent());
            }
        }
    }
    
    /**
     * Ouvre un dialogue pour ajouter des fichiers manuellement.
     */
    private void ajouterFichiersManuel() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle(rbLocalisation.getString("conversion.choisirImages"));
        
        // Filtres d'extensions
        fileChooser.getExtensionFilters().addAll(
            new javafx.stage.FileChooser.ExtensionFilter(
                rbLocalisation.getString("conversion.imagesSupporte"), 
                "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif", "*.webp"
            ),
            new javafx.stage.FileChooser.ExtensionFilter(
                rbLocalisation.getString("conversion.tousLesFichiers"), 
                "*.*"
            )
        );
        
        // Répertoire initial
        String dernierRepertoire = EditeurPanovisu.getStrDernierRepertoireImages();
        if (dernierRepertoire != null && !dernierRepertoire.isEmpty()) {
            File dirInitial = new File(dernierRepertoire);
            if (dirInitial.exists() && dirInitial.isDirectory()) {
                fileChooser.setInitialDirectory(dirInitial);
            }
        }
        
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stConversion);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            ajouterFichiers(selectedFiles);
        }
    }
    
    /**
     * Supprime les fichiers sélectionnés de la liste.
     */
    private void supprimerSelection() {
        File selectedFile = lvListeFichiers.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            olFichiers.remove(selectedFile);
        }
    }
    
    /**
     * Vérifie si un fichier est une image valide.
     *
     * @param file Fichier à vérifier
     * @return true si le fichier est une image valide
     */
    private boolean estImageValide(File file) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        
        String nom = file.getName().toLowerCase();
        return nom.endsWith(".jpg") || nom.endsWith(".jpeg") || 
               nom.endsWith(".png") || nom.endsWith(".bmp") ||
               nom.endsWith(".gif") || nom.endsWith(".webp");
    }
    
    /**
     * Ouvre un dialogue pour choisir un répertoire personnalisé.
     */
    private void parcourirRepertoire() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle(rbLocalisation.getString("conversion.choisirRepertoire"));
        
        File selectedDir = dirChooser.showDialog(stConversion);
        if (selectedDir != null) {
            tfRepertoirePersonnalise.setText(selectedDir.getAbsolutePath());
        }
    }
    
    // ========== MÉTHODES DE TRAITEMENT ==========
    
    /**
     * Lance le traitement de conversion pour tous les fichiers.
     */
    private void lancerTraitement() {
        // Validation
        if (olFichiers.isEmpty()) {
            afficherErreur(rbLocalisation.getString("conversion.erreurAucunFichier"));
            return;
        }
        
        if (rbRepertoirePersonnalise.isSelected() && tfRepertoirePersonnalise.getText().trim().isEmpty()) {
            afficherErreur(rbLocalisation.getString("conversion.erreurRepertoireVide"));
            return;
        }
        
        // Désactiver les contrôles
        desactiverControles();
        
        // Afficher la progression
        pbProgression.setVisible(true);
        lblEtat.setVisible(true);
        pbProgression.setProgress(0);
        
        // Lancer le traitement dans un thread séparé
        Thread threadTraitement = new Thread(() -> {
            bTraitementEnCours = true;
            iNombreFichiersTotal = olFichiers.size();
            iNombreFichiersTraites = 0;
            
            for (File fichier : olFichiers) {
                try {
                    // Mise à jour de l'interface
                    int numeroFichier = iNombreFichiersTraites + 1;
                    Platform.runLater(() -> {
                        lblEtat.setText(String.format(
                            rbLocalisation.getString("conversion.traitement"),
                            numeroFichier, iNombreFichiersTotal, fichier.getName()
                        ));
                    });
                    
                    // Traiter l'image
                    traiterImage(fichier);
                    
                    iNombreFichiersTraites++;
                    
                    // Mise à jour de la barre de progression
                    Platform.runLater(() -> {
                        pbProgression.setProgress((double) iNombreFichiersTraites / iNombreFichiersTotal);
                    });
                    
                } catch (Exception e) {
                    Logger.getLogger(ConversionRatio2to1DialogController.class.getName())
                            .log(Level.SEVERE, "Erreur traitement image: " + fichier.getName(), e);
                    
                    Platform.runLater(() -> {
                        afficherErreur(String.format(
                            rbLocalisation.getString("conversion.erreurTraitement"),
                            fichier.getName(), e.getMessage()
                        ));
                    });
                }
            }
            
            // Traitement terminé
            bTraitementEnCours = false;
            Platform.runLater(() -> {
                lblEtat.setText(rbLocalisation.getString("conversion.traitementTermine"));
                afficherInformation(String.format(
                    rbLocalisation.getString("conversion.successTraitement"),
                    iNombreFichiersTraites, iNombreFichiersTotal
                ));
                fermerFenetre();
            });
        });
        
        threadTraitement.setDaemon(true);
        threadTraitement.start();
    }
    
    /**
     * Traite (convertit au ratio 2:1) une image selon les paramètres définis.
     * 
     * @param fichierSource Fichier image source à traiter
     * @throws IOException En cas d'erreur de lecture/écriture
     */
    private void traiterImage(File fichierSource) throws IOException {
        // Lire l'image source
        BufferedImage imageOriginale = ImageIO.read(fichierSource);
        if (imageOriginale == null) {
            throw new IOException("Impossible de lire l'image : " + fichierSource.getName());
        }
        
        // Vérifier si des paramètres individuels existent pour ce fichier
        String cheminFichier = fichierSource.getAbsolutePath();
        double[] parametres = parametresIndividuels.get(cheminFichier);
        
        BufferedImage imageConvertie;
        if (parametres != null) {
            // Utiliser les paramètres individuels
            double positionVerticale = parametres[0];
            int typeRemplissageIndex = (int) parametres[1];
            imageConvertie = convertirRatio2to1(imageOriginale, positionVerticale, typeRemplissageIndex);
        } else {
            // Utiliser les paramètres globaux
            imageConvertie = convertirRatio2to1(imageOriginale);
        }
        
        // Libérer la référence à l'image originale
        imageOriginale.flush();
        imageOriginale = null;
        
        // Déterminer le fichier de sortie
        File fichierSortie = determinerFichierSortie(fichierSource);
        
        // Déterminer le format de sortie
        String format = getFormatSortie();
        
        // Si on écrase le fichier source, utiliser un fichier temporaire
        boolean ecrasementFichierSource = fichierSortie.getAbsolutePath().equals(fichierSource.getAbsolutePath());
        File fichierTemp = null;
        
        if (ecrasementFichierSource) {
            fichierTemp = new File(fichierSortie.getParent(), 
                                  "temp_" + System.currentTimeMillis() + "_" + fichierSortie.getName());
            sauvegarderImage(imageConvertie, fichierTemp, format);
            
            if (!fichierSource.delete()) {
                fichierTemp.delete();
                throw new IOException("Impossible de supprimer le fichier original : " + fichierSource.getName());
            }
            if (!fichierTemp.renameTo(fichierSortie)) {
                throw new IOException("Impossible de renommer le fichier temporaire");
            }
        } else {
            sauvegarderImage(imageConvertie, fichierSortie, format);
        }
    }
    
    /**
     * Convertit une image au ratio 2:1 en ajoutant du padding selon le type choisi.
     *
     * @param imageSource Image source à convertir
     * @return Image convertie au ratio 2:1
     */
    private BufferedImage convertirRatio2to1(BufferedImage imageSource) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        double ratioSource = (double) largeurSource / hauteurSource;
        
        // Ratio cible : 2:1
        final double RATIO_CIBLE = 2.0;
        
        int largeurFinale, hauteurFinale;
        
        if (Math.abs(ratioSource - RATIO_CIBLE) < 0.01) {
            // Image déjà au ratio 2:1
            return imageSource;
        }
        
        // Calculer les dimensions finales pour maximiser l'image source
        if (ratioSource < RATIO_CIBLE) {
            // Image trop haute → ajouter du padding horizontal
            hauteurFinale = hauteurSource;
            largeurFinale = (int) Math.round(hauteurFinale * RATIO_CIBLE);
        } else {
            // Image trop large → ajouter du padding vertical
            largeurFinale = largeurSource;
            hauteurFinale = (int) Math.round(largeurFinale / RATIO_CIBLE);
        }
        
        // Créer l'image finale
        BufferedImage imageFinale = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageFinale.createGraphics();
        
        // Configuration pour une qualité optimale
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Appliquer le remplissage selon le type choisi
        if (rbRemplissageNoir.isSelected()) {
            appliquerRemplissageNoir(g2d, imageSource, largeurFinale, hauteurFinale, 0.5);
        } else if (rbRemplissageFlou.isSelected()) {
            appliquerRemplissageFlou(g2d, imageSource, largeurFinale, hauteurFinale, 0.5);
        } else if (rbRemplissageMiroir.isSelected()) {
            appliquerRemplissageMiroir(g2d, imageSource, largeurFinale, hauteurFinale, 0.5);
        } else if (rbRemplissageCouleurMoyenne.isSelected()) {
            appliquerRemplissageCouleurMoyenne(g2d, imageSource, largeurFinale, hauteurFinale, 0.5);
        }
        
        g2d.dispose();
        return imageFinale;
    }
    
    /**
     * Convertit une image au ratio 2:1 avec des paramètres individuels.
     *
     * @param imageSource Image source à convertir
     * @param positionVerticale Position verticale (0.0 = haut, 0.5 = centre, 1.0 = bas)
     * @param typeRemplissageIndex Type de remplissage (0=noir, 1=flou, 2=miroir, 3=couleurMoyenne)
     * @return Image convertie au ratio 2:1
     */
    private BufferedImage convertirRatio2to1(BufferedImage imageSource, double positionVerticale, int typeRemplissageIndex) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        double ratioSource = (double) largeurSource / hauteurSource;
        
        // Ratio cible : 2:1
        final double RATIO_CIBLE = 2.0;
        
        int largeurFinale, hauteurFinale;
        
        if (Math.abs(ratioSource - RATIO_CIBLE) < 0.01) {
            // Image déjà au ratio 2:1
            return imageSource;
        }
        
        // Calculer les dimensions finales pour maximiser l'image source
        if (ratioSource < RATIO_CIBLE) {
            // Image trop haute → ajouter du padding horizontal
            hauteurFinale = hauteurSource;
            largeurFinale = (int) Math.round(hauteurFinale * RATIO_CIBLE);
        } else {
            // Image trop large → ajouter du padding vertical
            largeurFinale = largeurSource;
            hauteurFinale = (int) Math.round(largeurFinale / RATIO_CIBLE);
        }
        
        // Créer l'image finale
        BufferedImage imageFinale = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageFinale.createGraphics();
        
        // Configuration pour une qualité optimale
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Appliquer le remplissage selon le type choisi avec la position verticale personnalisée
        switch (typeRemplissageIndex) {
            case 0: // Noir
                appliquerRemplissageNoir(g2d, imageSource, largeurFinale, hauteurFinale, positionVerticale);
                break;
            case 1: // Flou
                appliquerRemplissageFlou(g2d, imageSource, largeurFinale, hauteurFinale, positionVerticale);
                break;
            case 2: // Miroir
                appliquerRemplissageMiroir(g2d, imageSource, largeurFinale, hauteurFinale, positionVerticale);
                break;
            case 3: // Couleur moyenne
                appliquerRemplissageCouleurMoyenne(g2d, imageSource, largeurFinale, hauteurFinale, positionVerticale);
                break;
        }
        
        g2d.dispose();
        return imageFinale;
    }
    
    /**
     * Applique un remplissage noir autour de l'image.
     *
     * @param positionVerticale Position verticale (0.0 = haut, 0.5 = centre, 1.0 = bas)
     */
    private void appliquerRemplissageNoir(Graphics2D g2d, BufferedImage imageSource, 
                                          int largeurFinale, int hauteurFinale, double positionVerticale) {
        // Remplir en noir
        g2d.setColor(java.awt.Color.BLACK);
        g2d.fillRect(0, 0, largeurFinale, hauteurFinale);
        
        // Positionner l'image source
        int x = (largeurFinale - imageSource.getWidth()) / 2;
        int y = calculerPositionY(hauteurFinale, imageSource.getHeight(), positionVerticale);
        g2d.drawImage(imageSource, x, y, null);
    }
    
    /**
     * Calcule la position Y en fonction de la position verticale choisie.
     */
    private int calculerPositionY(int hauteurFinale, int hauteurSource, double positionVerticale) {
        int espaceDispo = hauteurFinale - hauteurSource;
        return (int) (espaceDispo * positionVerticale);
    }
    
    /**
     * Applique un remplissage flou très prononcé (image source étirée et fortement floutée en arrière-plan).
     *
     * @param positionVerticale Position verticale (0.0 = haut, 0.5 = centre, 1.0 = bas)
     */
    private void appliquerRemplissageFlou(Graphics2D g2d, BufferedImage imageSource, 
                                          int largeurFinale, int hauteurFinale, double positionVerticale) {
        // Créer l'image de fond floutée
        BufferedImage imageFond = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
        Graphics2D gFond = imageFond.createGraphics();
        
        // Étirer l'image source sur tout le fond
        gFond.drawImage(imageSource, 0, 0, largeurFinale, hauteurFinale, null);
        gFond.dispose();
        
        // Appliquer un flou gaussien TRÈS FORT avec un kernel 9×9
        float[] matrix9x9 = new float[81];
        int kernelSize = 9;
        float sigma = 5.0f; // Augmenter sigma pour un flou plus fort
        float sum = 0.0f;
        int center = kernelSize / 2;
        
        // Générer un kernel gaussien 9×9
        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                int dx = x - center;
                int dy = y - center;
                float value = (float) Math.exp(-(dx*dx + dy*dy) / (2*sigma*sigma));
                matrix9x9[y * kernelSize + x] = value;
                sum += value;
            }
        }
        
        // Normaliser le kernel
        for (int i = 0; i < matrix9x9.length; i++) {
            matrix9x9[i] /= sum;
        }
        
        Kernel kernel = new Kernel(kernelSize, kernelSize, matrix9x9);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        
        // Appliquer le flou 15 fois pour un effet TRÈS prononcé
        BufferedImage temp = imageFond;
        for (int i = 0; i < 15; i++) {
            BufferedImage temp2 = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
            temp2 = op.filter(temp, temp2);
            temp = temp2;
        }
        
        // Dessiner le fond flouté avec une légère transparence
        g2d.drawImage(temp, 0, 0, null);
        
        // Dessiner l'image source par-dessus avec la position verticale personnalisée
        int x = (largeurFinale - imageSource.getWidth()) / 2;
        int y = calculerPositionY(hauteurFinale, imageSource.getHeight(), positionVerticale);
        g2d.drawImage(imageSource, x, y, null);
    }
    
    /**
     * Applique un remplissage par couleur moyenne des bords.
     * Calcule la couleur moyenne des 10 dernières lignes/colonnes de chaque bord.
     *
     * @param positionVerticale Position verticale (0.0 = haut, 0.5 = centre, 1.0 = bas)
     */
    private void appliquerRemplissageCouleurMoyenne(Graphics2D g2d, BufferedImage imageSource, 
                                                    int largeurFinale, int hauteurFinale, double positionVerticale) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        
        // Positionner l'image source selon la position verticale
        int xCentre = (largeurFinale - largeurSource) / 2;
        int yCentre = calculerPositionY(hauteurFinale, hauteurSource, positionVerticale);
        
        // Nombre de lignes/colonnes à analyser pour la couleur moyenne
        int nbLignesAnalyse = Math.min(10, Math.min(largeurSource, hauteurSource) / 4);
        
        // Calculer et appliquer la couleur moyenne pour chaque bord
        if (yCentre > 0) {
            // Bande supérieure - couleur moyenne des premières lignes
            java.awt.Color couleurHaut = calculerCouleurMoyenne(imageSource, 0, 0, 
                                                                largeurSource, nbLignesAnalyse);
            g2d.setColor(couleurHaut);
            g2d.fillRect(0, 0, largeurFinale, yCentre);
            
            // Bande inférieure - couleur moyenne des dernières lignes
            java.awt.Color couleurBas = calculerCouleurMoyenne(imageSource, 0, 
                                                               hauteurSource - nbLignesAnalyse,
                                                               largeurSource, nbLignesAnalyse);
            g2d.setColor(couleurBas);
            g2d.fillRect(0, yCentre + hauteurSource, largeurFinale, hauteurFinale - yCentre - hauteurSource);
        }
        
        if (xCentre > 0) {
            // Bande gauche - couleur moyenne des premières colonnes
            java.awt.Color couleurGauche = calculerCouleurMoyenne(imageSource, 0, 0, 
                                                                  nbLignesAnalyse, hauteurSource);
            g2d.setColor(couleurGauche);
            g2d.fillRect(0, yCentre, xCentre, hauteurSource);
            
            // Bande droite - couleur moyenne des dernières colonnes
            java.awt.Color couleurDroite = calculerCouleurMoyenne(imageSource, 
                                                                  largeurSource - nbLignesAnalyse, 0,
                                                                  nbLignesAnalyse, hauteurSource);
            g2d.setColor(couleurDroite);
            g2d.fillRect(xCentre + largeurSource, yCentre, largeurFinale - xCentre - largeurSource, hauteurSource);
        }
        
        // Dessiner l'image source au centre
        g2d.drawImage(imageSource, xCentre, yCentre, null);
    }
    
    /**
     * Calcule la couleur moyenne d'une zone rectangulaire de l'image.
     *
     * @param image Image source
     * @param x Position X de la zone
     * @param y Position Y de la zone
     * @param largeur Largeur de la zone
     * @param hauteur Hauteur de la zone
     * @return Couleur moyenne de la zone
     */
    private java.awt.Color calculerCouleurMoyenne(BufferedImage image, int x, int y, 
                                                  int largeur, int hauteur) {
        long sumR = 0, sumG = 0, sumB = 0;
        int count = 0;
        
        int xMax = Math.min(x + largeur, image.getWidth());
        int yMax = Math.min(y + hauteur, image.getHeight());
        
        for (int py = y; py < yMax; py++) {
            for (int px = x; px < xMax; px++) {
                int rgb = image.getRGB(px, py);
                sumR += (rgb >> 16) & 0xFF;
                sumG += (rgb >> 8) & 0xFF;
                sumB += rgb & 0xFF;
                count++;
            }
        }
        
        if (count == 0) {
            return java.awt.Color.BLACK;
        }
        
        return new java.awt.Color(
            (int)(sumR / count),
            (int)(sumG / count),
            (int)(sumB / count)
        );
    }
    
    /**
     * Applique un remplissage miroir (image source reflétée sur les bords).
     *
     * @param positionVerticale Position verticale (0.0 = haut, 0.5 = centre, 1.0 = bas)
     */
    private void appliquerRemplissageMiroir(Graphics2D g2d, BufferedImage imageSource, 
                                            int largeurFinale, int hauteurFinale, double positionVerticale) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        
        // Positionner l'image source selon la position verticale
        int xCentre = (largeurFinale - largeurSource) / 2;
        int yCentre = calculerPositionY(hauteurFinale, hauteurSource, positionVerticale);
        
        // Dessiner les bandes miroir si nécessaire
        if (xCentre > 0) {
            // Bande gauche (miroir horizontal inversé)
            g2d.drawImage(imageSource, 0, yCentre, xCentre, yCentre + hauteurSource,
                         xCentre, 0, 0, hauteurSource, null);
            
            // Bande droite (miroir horizontal inversé)
            g2d.drawImage(imageSource, xCentre + largeurSource, yCentre, largeurFinale, yCentre + hauteurSource,
                         largeurSource, 0, largeurSource - xCentre, hauteurSource, null);
        }
        
        if (yCentre > 0) {
            // Bande haut (miroir vertical inversé)
            g2d.drawImage(imageSource, xCentre, 0, xCentre + largeurSource, yCentre,
                         0, yCentre, largeurSource, 0, null);
            
            // Bande bas (miroir vertical inversé)
            g2d.drawImage(imageSource, xCentre, yCentre + hauteurSource, xCentre + largeurSource, hauteurFinale,
                         0, hauteurSource, largeurSource, hauteurSource - yCentre, null);
        }
        
        // Dessiner l'image source au centre
        g2d.drawImage(imageSource, xCentre, yCentre, null);
    }
    
    /**
     * Détermine le fichier de sortie selon le mode de sauvegarde sélectionné.
     *
     * @param fichierSource Fichier source
     * @return Fichier de sortie calculé
     */
    private File determinerFichierSortie(File fichierSource) {
        String nomBase = fichierSource.getName();
        int pointIndex = nomBase.lastIndexOf('.');
        if (pointIndex > 0) {
            nomBase = nomBase.substring(0, pointIndex);
        }
        
        String format = getFormatSortie();
        String extension;
        if ("JPEG".equals(format)) {
            extension = ".jpg";
        } else {
            extension = "." + format.toLowerCase();
        }
        
        if (rbRepertoirePersonnalise.isSelected()) {
            // Répertoire personnalisé
            String repertoire = tfRepertoirePersonnalise.getText();
            return new File(repertoire, nomBase + extension);
        } else {
            // Répertoire d'origine
            String repertoire = fichierSource.getParent();
            
            if (cbEcraserOrigine.isSelected()) {
                // Écraser l'original
                return new File(repertoire, nomBase + extension);
            } else {
                // Ajouter un suffixe
                String suffixe = tfSuffixe.getText();
                return new File(repertoire, nomBase + suffixe + extension);
            }
        }
    }
    
    /**
     * Retourne le format de sortie sélectionné.
     *
     * @return Format ("JPEG", "PNG", ou "WEBP")
     */
    private String getFormatSortie() {
        RadioButton selectionne = (RadioButton) tgFormatSortie.getSelectedToggle();
        if (selectionne == rbJpeg) {
            return "JPEG";
        } else if (selectionne == rbPng) {
            return "PNG";
        } else if (selectionne == rbWebp) {
            return "WEBP";
        }
        return "JPEG";
    }
    
    /**
     * Sauvegarde une image dans le format spécifié avec compression.
     * 
     * @param image Image à sauvegarder
     * @param fichierSortie Fichier de destination
     * @param format Format de sortie ("JPEG", "PNG", "WEBP")
     * @throws IOException En cas d'erreur d'écriture
     */
    private void sauvegarderImage(BufferedImage image, File fichierSortie, String format) throws IOException {
        // Créer le répertoire parent si nécessaire
        File parent = fichierSortie.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        
        if ("JPEG".equals(format)) {
            // Sauvegarde JPEG avec qualité personnalisée
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) {
                throw new IOException("Aucun writer JPEG disponible");
            }
            
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                float qualite = (float) slQualiteJpeg.getValue() / 100f;
                param.setCompressionQuality(qualite);
            }
            
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(fichierSortie)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                writer.dispose();
            }
        } else {
            // Sauvegarde PNG ou WEBP
            ImageIO.write(image, format.toLowerCase(), fichierSortie);
        }
    }
    
    // ========== MÉTHODES UTILITAIRES ==========
    
    /**
     * Formate la taille d'un fichier de manière lisible.
     * 
     * @param taille Taille en octets
     * @return Chaîne formatée
     */
    private String formatTailleFichier(long taille) {
        if (taille < 1024) {
            return taille + " o";
        } else if (taille < 1024 * 1024) {
            return String.format("%.1f Ko", taille / 1024.0);
        } else if (taille < 1024 * 1024 * 1024) {
            return String.format("%.1f Mo", taille / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f Go", taille / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Désactive tous les contrôles pendant le traitement.
     */
    private void desactiverControles() {
        lvListeFichiers.setDisable(true);
        btnAjouter.setDisable(true);
        btnSupprimer.setDisable(true);
        rbRemplissageNoir.setDisable(true);
        rbRemplissageFlou.setDisable(true);
        rbRemplissageMiroir.setDisable(true);
        rbJpeg.setDisable(true);
        rbPng.setDisable(true);
        rbWebp.setDisable(true);
        slQualiteJpeg.setDisable(true);
        rbRepertoireOrigine.setDisable(true);
        rbRepertoirePersonnalise.setDisable(true);
        cbEcraserOrigine.setDisable(true);
        tfSuffixe.setDisable(true);
        tfRepertoirePersonnalise.setDisable(true);
        btnParcourir.setDisable(true);
        btnValider.setDisable(true);
    }
    
    /**
     * Affiche une boîte de dialogue d'erreur.
     *
     * @param message Message d'erreur
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stConversion);
        alert.setTitle(rbLocalisation.getString("conversion.erreur"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Affiche une boîte de dialogue d'information.
     *
     * @param message Message d'information
     */
    private void afficherInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stConversion);
        alert.setTitle(rbLocalisation.getString("conversion.information"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Ferme la fenêtre de dialogue.
     */
    private void fermerFenetre() {
        if (stConversion != null) {
            stConversion.close();
        }
    }
}
