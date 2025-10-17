/*
 * Copyright (C) 2025 LANG Laurent
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package editeurpanovisu;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Contrôleur pour l'interface de redimensionnement et compression d'images.
 * 
 * <p>Cette classe gère une fenêtre de dialogue permettant de :</p>
 * <ul>
 * <li>Charger des images par drag & drop ou via FileChooser</li>
 * <li>Redimensionner les images avec ou sans conservation du ratio</li>
 * <li>Compresser les images avec différents niveaux de qualité</li>
 * <li>Convertir entre différents formats (JPEG, PNG, WEBP)</li>
 * <li>Traiter les images par lots</li>
 * </ul>
 *
 * @author LANG Laurent
 * @version 3.1.0
 * @since 2025-10-16
 */
public class RedimensionnementImagesDialogController {

    /**
     * Bundle de ressources pour la localisation (français/anglais)
     */
    private static final ResourceBundle rbLocalisation = ResourceBundle.getBundle(
            "editeurpanovisu.i18n.PanoVisu", 
            EditeurPanovisu.getLocale()
    );

    /**
     * Fenêtre principale du dialogue
     */
    private Stage stRedimensionnement;
    
    /**
     * Panneau racine de l'interface
     */
    private AnchorPane apRedimensionnement;
    
    /**
     * Liste des fichiers à traiter
     */
    private final ListView<File> lvListeFichiers = new ListView<>();
    
    /**
     * Liste des fichiers sélectionnés
     */
    private List<File> listeFichiersImages = new ArrayList<>();
    
    /**
     * Bouton pour valider et lancer le traitement
     */
    private Button btnValider;
    
    /**
     * Bouton pour annuler et fermer la fenêtre
     */
    private Button btnAnnuler;
    
    /**
     * Bouton pour ajouter des fichiers via FileChooser
     */
    private Button btnAjouterFichiers;
    
    /**
     * Bouton pour supprimer les fichiers sélectionnés de la liste
     */
    private Button btnSupprimerSelection;
    
    /**
     * Bouton pour vider toute la liste
     */
    private Button btnViderListe;
    
    /**
     * Champ de texte pour la largeur cible
     */
    private TextField tfLargeur;
    
    /**
     * Champ de texte pour la hauteur cible
     */
    private TextField tfHauteur;
    
    /**
     * Case à cocher pour conserver le ratio d'aspect
     */
    private CheckBox cbConserverRatio;
    
    /**
     * Groupe de boutons radio pour le choix du format de sortie
     */
    private final ToggleGroup tgFormatSortie = new ToggleGroup();
    
    /**
     * Bouton radio pour le format JPEG
     */
    private RadioButton rbJpeg;
    
    /**
     * Bouton radio pour le format PNG
     */
    private RadioButton rbPng;
    
    /**
     * Bouton radio pour le format WEBP
     */
    private RadioButton rbWebp;
    
    /**
     * Slider pour ajuster la qualité de compression JPEG
     */
    private Slider slQualiteJpeg;
    
    /**
     * Label affichant la valeur actuelle de la qualité JPEG
     */
    private Label lblQualiteJpeg;
    
    /**
     * TextField affichant le répertoire de sortie sélectionné
     */
    private TextField tfRepertoireSortie;
    
    /**
     * Bouton pour choisir le répertoire de sortie
     */
    private Button btnChoisirRepertoire;
    
    /**
     * Répertoire de sortie pour les fichiers traités
     */
    private File repertoireSortie;
    
    /**
     * Case à cocher pour créer un sous-dossier avec la date/heure
     */
    private CheckBox cbCreerSousDossier;
    
    /**
     * Case à cocher pour écraser les fichiers d'origine
     */
    private CheckBox cbEcraserOrigine;
    
    /**
     * TextField pour le suffixe à ajouter aux noms de fichiers
     */
    private TextField tfSuffixe;
    
    /**
     * Groupe de boutons radio pour le mode de sauvegarde
     */
    private final ToggleGroup tgModeSauvegarde = new ToggleGroup();
    
    /**
     * Bouton radio : sauvegarder dans le répertoire d'origine
     */
    private RadioButton rbRepertoireOrigine;
    
    /**
     * Bouton radio : sauvegarder dans un répertoire personnalisé
     */
    private RadioButton rbRepertoirePersonnalise;
    
    /**
     * Barre de progression pour le traitement des images
     */
    private ProgressBar pbProgression;
    
    /**
     * Label indiquant le statut du traitement
     */
    private Label lblStatut;
    
    /**
     * Zone de drag & drop pour ajouter des images
     */
    private VBox vbZoneDragDrop;
    
    /**
     * Répertoire par défaut pour le FileChooser
     */
    private static String strRepertoireDefaut = EditeurPanovisu.getStrRepertoireProjet();
    
    /**
     * Indicateur de traitement en cours
     */
    private boolean bTraitementEnCours = false;

    /**
     * Constructeur par défaut.
     * Initialise le contrôleur sans créer l'interface.
     */
    public RedimensionnementImagesDialogController() {
        // Constructeur vide - l'interface sera créée par afficheFenetre()
    }

    /**
     * Affiche la fenêtre de dialogue de redimensionnement d'images.
     * 
     * <p>Crée et configure tous les composants de l'interface graphique :</p>
     * <ul>
     * <li>Zone de drag & drop pour ajouter des images</li>
     * <li>Liste des fichiers à traiter</li>
     * <li>Options de redimensionnement (largeur, hauteur, ratio)</li>
     * <li>Sélection du format de sortie</li>
     * <li>Contrôle de qualité JPEG</li>
     * <li>Boutons d'action (valider, annuler, ajouter, supprimer)</li>
     * </ul>
     *
     * @throws Exception si une erreur survient lors de la création de l'interface
     */
    public void afficheFenetre() throws Exception {
        // Création de la fenêtre principale
        stRedimensionnement = new Stage();
        stRedimensionnement.setResizable(false);
        stRedimensionnement.initModality(Modality.APPLICATION_MODAL);
        stRedimensionnement.initOwner(EditeurPanovisu.getStPrincipal());
        stRedimensionnement.setTitle(rbLocalisation.getString("outilsRedimensionnement"));

        // Création du panneau racine
        apRedimensionnement = new AnchorPane();
        apRedimensionnement.setPrefSize(800, 650);
        apRedimensionnement.setStyle("-fx-background-color: -color-bg-default;");

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
        
        // ========== OPTIONS DE REDIMENSIONNEMENT ==========
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
        apRedimensionnement.getChildren().add(vbPrincipal);
        AnchorPane.setTopAnchor(vbPrincipal, 0.0);
        AnchorPane.setLeftAnchor(vbPrincipal, 0.0);
        AnchorPane.setRightAnchor(vbPrincipal, 0.0);
        
        // Configuration de la scène
        Scene scene = new Scene(apRedimensionnement);
        stRedimensionnement.setScene(scene);
        
        // Affichage de la fenêtre
        stRedimensionnement.show();
    }

    /**
     * Crée la zone de drag & drop pour ajouter des images.
     * 
     * <p>Cette zone permet à l'utilisateur de glisser-déposer des fichiers
     * d'images directement dans l'application. Elle affiche des instructions
     * visuelles et change d'apparence lors du survol avec des fichiers.</p>
     *
     * @return VBox contenant la zone de drag & drop
     */
    private void creerZoneDragDrop() {
        vbZoneDragDrop = new VBox(10);
        vbZoneDragDrop.setAlignment(Pos.CENTER);
        vbZoneDragDrop.setPrefHeight(120);
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
            Logger.getLogger(RedimensionnementImagesDialogController.class.getName())
                    .log(Level.WARNING, "Impossible de charger l'icône upload", e);
        }
        
        // Label d'instructions
        Label lblInstructions = new Label(rbLocalisation.getString("dragDropImages"));
        lblInstructions.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblInstructions.setStyle("-fx-text-fill: -color-fg-muted;");
        
        Label lblOu = new Label(rbLocalisation.getString("ouCliquez"));
        lblOu.setStyle("-fx-text-fill: -color-fg-muted;");
        
        vbZoneDragDrop.getChildren().addAll(ivDragDrop, lblInstructions, lblOu);
        
        // ===== Configuration du drag & drop =====
        configurerDragDrop();
        
        // ===== Clic pour ouvrir le FileChooser =====
        vbZoneDragDrop.setOnMouseClicked(event -> ouvrirFileChooser());
        vbZoneDragDrop.setCursor(javafx.scene.Cursor.HAND);
    }

    /**
     * Configure les gestionnaires d'événements pour le drag & drop.
     * 
     * <p>Gère les différents états du drag & drop :</p>
     * <ul>
     * <li>Survol avec des fichiers (DRAG_OVER)</li>
     * <li>Sortie de la zone (DRAG_EXITED)</li>
     * <li>Dépôt des fichiers (DRAG_DROPPED)</li>
     * </ul>
     */
    private void configurerDragDrop() {
        // Accepter les fichiers glissés
        vbZoneDragDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != vbZoneDragDrop && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
                // Changement visuel lors du survol
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
        
        // Restaurer l'apparence normale quand on sort de la zone
        vbZoneDragDrop.setOnDragExited(event -> {
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
        
        // Gérer le dépôt des fichiers
        vbZoneDragDrop.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasFiles()) {
                ajouterFichiers(db.getFiles());
                success = true;
            }
            
            event.setDropCompleted(success);
            event.consume();
            
            // Restaurer l'apparence normale
            vbZoneDragDrop.setStyle(
                "-fx-border-color: -color-accent-emphasis; " +
                "-fx-border-width: 2; " +
                "-fx-border-style: dashed; " +
                "-fx-border-radius: 5; " +
                "-fx-background-color: -color-bg-subtle; " +
                "-fx-background-radius: 5;"
            );
        });
    }

    /**
     * Crée la zone contenant la liste des fichiers à traiter.
     * 
     * <p>Cette zone affiche :</p>
     * <ul>
     * <li>Un titre "Fichiers à traiter"</li>
     * <li>Une ListView avec les fichiers sélectionnés</li>
     * <li>Des boutons pour gérer la liste (ajouter, supprimer, vider)</li>
     * </ul>
     *
     * @return VBox contenant la liste des fichiers et ses contrôles
     */
    private VBox creerZoneListeFichiers() {
        VBox vbZone = new VBox(10);
        
        // Titre
        Label lblTitre = new Label(rbLocalisation.getString("fichiersATraiter"));
        lblTitre.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        // Configuration de la ListView
        lvListeFichiers.setPrefHeight(150);
        lvListeFichiers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Personnalisation de l'affichage des cellules
        lvListeFichiers.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> param) {
                return new ListCell<File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Afficher le nom du fichier avec une icône
                            HBox hbox = new HBox(10);
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            
                            try {
                                ImageView iv = EditeurPanovisu.loadSvgIcon("image", 16);
                                hbox.getChildren().add(iv);
                            } catch (Exception e) {
                                // Ignorer si l'icône ne peut pas être chargée
                            }
                            
                            Label lblNom = new Label(item.getName());
                            Label lblTaille = new Label(" (" + formatTailleFichier(item.length()) + ")");
                            lblTaille.setStyle("-fx-text-fill: -color-fg-muted;");
                            
                            hbox.getChildren().addAll(lblNom, lblTaille);
                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };
            }
        });
        
        // Boutons de gestion de la liste
        HBox hbBoutonsListe = new HBox(10);
        hbBoutonsListe.setAlignment(Pos.CENTER_LEFT);
        
        btnAjouterFichiers = new Button(rbLocalisation.getString("ajouterFichiers"));
        btnAjouterFichiers.setOnAction(e -> ouvrirFileChooser());
        
        btnSupprimerSelection = new Button(rbLocalisation.getString("supprimerSelection"));
        btnSupprimerSelection.setOnAction(e -> supprimerSelection());
        btnSupprimerSelection.setDisable(true);
        
        btnViderListe = new Button(rbLocalisation.getString("viderListe"));
        btnViderListe.setOnAction(e -> viderListe());
        btnViderListe.setDisable(true);
        
        // Activer/désactiver les boutons selon la sélection
        lvListeFichiers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            btnSupprimerSelection.setDisable(newVal == null);
        });
        
        hbBoutonsListe.getChildren().addAll(btnAjouterFichiers, btnSupprimerSelection, btnViderListe);
        
        vbZone.getChildren().addAll(lblTitre, lvListeFichiers, hbBoutonsListe);
        return vbZone;
    }

    /**
     * Crée la zone des options de redimensionnement et de format.
     * 
     * <p>Cette zone contient :</p>
     * <ul>
     * <li>Options de dimensions (largeur, hauteur, conservation du ratio)</li>
     * <li>Sélection du format de sortie (JPEG, PNG, WEBP)</li>
     * <li>Contrôle de qualité pour la compression JPEG</li>
     * </ul>
     *
     * @return HBox contenant toutes les options
     */
    private HBox creerZoneOptions() {
        HBox hbPrincipal = new HBox(20);
        hbPrincipal.setAlignment(Pos.TOP_LEFT);
        
        // ===== COLONNE 1: Dimensions =====
        VBox vbDimensions = new VBox(10);
        vbDimensions.setPrefWidth(250);
        
        Label lblTitreDimensions = new Label(rbLocalisation.getString("dimensions"));
        lblTitreDimensions.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        // Largeur
        HBox hbLargeur = new HBox(10);
        hbLargeur.setAlignment(Pos.CENTER_LEFT);
        Label lblLargeur = new Label(rbLocalisation.getString("largeur") + " :");
        lblLargeur.setPrefWidth(80);
        tfLargeur = new TextField("1920");
        tfLargeur.setPrefWidth(100);
        Label lblPxLargeur = new Label("px");
        hbLargeur.getChildren().addAll(lblLargeur, tfLargeur, lblPxLargeur);
        
        // Hauteur
        HBox hbHauteur = new HBox(10);
        hbHauteur.setAlignment(Pos.CENTER_LEFT);
        Label lblHauteur = new Label(rbLocalisation.getString("hauteur") + " :");
        lblHauteur.setPrefWidth(80);
        tfHauteur = new TextField("1080");
        tfHauteur.setPrefWidth(100);
        Label lblPxHauteur = new Label("px");
        hbHauteur.getChildren().addAll(lblHauteur, tfHauteur, lblPxHauteur);
        
        // Case à cocher pour conserver le ratio
        cbConserverRatio = new CheckBox(rbLocalisation.getString("conserverRatio"));
        cbConserverRatio.setSelected(true);
        
        // Listener pour recalculer automatiquement les dimensions
        cbConserverRatio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tfHauteur.setDisable(true);
            } else {
                tfHauteur.setDisable(false);
            }
        });
        tfHauteur.setDisable(true);
        
        vbDimensions.getChildren().addAll(lblTitreDimensions, hbLargeur, hbHauteur, cbConserverRatio);
        
        // ===== COLONNE 2: Format de sortie =====
        VBox vbFormat = new VBox(10);
        vbFormat.setPrefWidth(200);
        
        Label lblTitreFormat = new Label(rbLocalisation.getString("formatSortie"));
        lblTitreFormat.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        rbJpeg = new RadioButton("JPEG");
        rbJpeg.setToggleGroup(tgFormatSortie);
        rbJpeg.setSelected(true);
        
        rbPng = new RadioButton("PNG");
        rbPng.setToggleGroup(tgFormatSortie);
        
        rbWebp = new RadioButton("WEBP");
        rbWebp.setToggleGroup(tgFormatSortie);
        
        vbFormat.getChildren().addAll(lblTitreFormat, rbJpeg, rbPng, rbWebp);
        
        // ===== COLONNE 3: Qualité JPEG =====
        VBox vbQualite = new VBox(10);
        vbQualite.setPrefWidth(300);
        
        Label lblTitreQualite = new Label(rbLocalisation.getString("qualiteJpeg"));
        lblTitreQualite.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        slQualiteJpeg = new Slider(0, 100, 85);
        slQualiteJpeg.setShowTickLabels(true);
        slQualiteJpeg.setShowTickMarks(true);
        slQualiteJpeg.setMajorTickUnit(25);
        slQualiteJpeg.setBlockIncrement(5);
        
        lblQualiteJpeg = new Label("85%");
        lblQualiteJpeg.setStyle("-fx-font-weight: bold;");
        
        slQualiteJpeg.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblQualiteJpeg.setText(String.format("%.0f%%", newVal.doubleValue()));
        });
        
        // Désactiver le slider de qualité si on ne choisit pas JPEG
        tgFormatSortie.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isJpeg = newVal == rbJpeg;
            slQualiteJpeg.setDisable(!isJpeg);
            lblQualiteJpeg.setDisable(!isJpeg);
        });
        
        HBox hbQualiteValeur = new HBox(10);
        hbQualiteValeur.setAlignment(Pos.CENTER_LEFT);
        hbQualiteValeur.getChildren().addAll(slQualiteJpeg, lblQualiteJpeg);
        
        vbQualite.getChildren().addAll(lblTitreQualite, hbQualiteValeur);
        
        hbPrincipal.getChildren().addAll(vbDimensions, vbFormat, vbQualite);
        return hbPrincipal;
    }

    /**
     * Crée la zone de sélection du mode de sauvegarde et répertoire de sortie.
     * 
     * <p>Cette zone permet de choisir :</p>
     * <ul>
     * <li>Sauvegarder dans le répertoire d'origine avec option d'écrasement</li>
     * <li>Sauvegarder dans un répertoire personnalisé</li>
     * <li>Ajouter un suffixe aux noms de fichiers</li>
     * <li>Créer optionnellement un sous-dossier avec la date/heure</li>
     * </ul>
     *
     * @return VBox contenant les contrôles du mode de sauvegarde
     */
    private VBox creerZoneRepertoireSortie() {
        VBox vbZone = new VBox(15);
        vbZone.setPadding(new Insets(10, 0, 0, 0));
        
        // Titre principal
        Label lblTitre = new Label(rbLocalisation.getString("optionsSauvegarde"));
        lblTitre.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        // ===== MODE 1: Répertoire d'origine =====
        rbRepertoireOrigine = new RadioButton(rbLocalisation.getString("sauverRepertoireOrigine"));
        rbRepertoireOrigine.setToggleGroup(tgModeSauvegarde);
        rbRepertoireOrigine.setSelected(true);
        
        // Sous-options pour le répertoire d'origine
        VBox vbOptionsOrigine = new VBox(8);
        vbOptionsOrigine.setPadding(new Insets(5, 0, 0, 30));
        
        // Case à cocher : Écraser les fichiers d'origine
        cbEcraserOrigine = new CheckBox(rbLocalisation.getString("ecraserFichiersOrigine"));
        cbEcraserOrigine.setTooltip(new Tooltip(rbLocalisation.getString("tooltipEcraser")));
        
        // Ligne avec suffixe
        HBox hbSuffixe = new HBox(10);
        hbSuffixe.setAlignment(Pos.CENTER_LEFT);
        Label lblSuffixe = new Label(rbLocalisation.getString("suffixeNomFichier") + " :");
        tfSuffixe = new TextField("_redim");
        tfSuffixe.setPrefWidth(150);
        tfSuffixe.setTooltip(new Tooltip(rbLocalisation.getString("tooltipSuffixe")));
        hbSuffixe.getChildren().addAll(lblSuffixe, tfSuffixe);
        
        // Gestion de l'activation/désactivation du suffixe
        cbEcraserOrigine.selectedProperty().addListener((obs, oldVal, newVal) -> {
            tfSuffixe.setDisable(newVal);
            lblSuffixe.setDisable(newVal);
        });
        
        vbOptionsOrigine.getChildren().addAll(cbEcraserOrigine, hbSuffixe);
        
        // ===== MODE 2: Répertoire personnalisé =====
        rbRepertoirePersonnalise = new RadioButton(rbLocalisation.getString("sauverRepertoirePersonnalise"));
        rbRepertoirePersonnalise.setToggleGroup(tgModeSauvegarde);
        
        // Sous-options pour le répertoire personnalisé
        VBox vbOptionsPersonnalise = new VBox(8);
        vbOptionsPersonnalise.setPadding(new Insets(5, 0, 0, 30));
        
        // Ligne avec le chemin et le bouton
        HBox hbRepertoire = new HBox(10);
        hbRepertoire.setAlignment(Pos.CENTER_LEFT);
        
        tfRepertoireSortie = new TextField();
        tfRepertoireSortie.setEditable(false);
        tfRepertoireSortie.setPrefWidth(520);
        tfRepertoireSortie.setPromptText(rbLocalisation.getString("aucunRepertoireSelectionne"));
        tfRepertoireSortie.setDisable(true);
        
        btnChoisirRepertoire = new Button(rbLocalisation.getString("choisir"));
        btnChoisirRepertoire.setPrefWidth(100);
        btnChoisirRepertoire.setOnAction(e -> choisirRepertoireSortie());
        btnChoisirRepertoire.setDisable(true);
        
        hbRepertoire.getChildren().addAll(tfRepertoireSortie, btnChoisirRepertoire);
        
        // Case à cocher pour créer un sous-dossier
        cbCreerSousDossier = new CheckBox(rbLocalisation.getString("creerSousDossierDate"));
        cbCreerSousDossier.setSelected(true);
        cbCreerSousDossier.setTooltip(new Tooltip(rbLocalisation.getString("tooltipSousDossier")));
        cbCreerSousDossier.setDisable(true);
        
        vbOptionsPersonnalise.getChildren().addAll(hbRepertoire, cbCreerSousDossier);
        
        // ===== Gestion de l'activation/désactivation des options =====
        tgModeSauvegarde.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPersonnalise = (newVal == rbRepertoirePersonnalise);
            
            // Activer/désactiver les options du répertoire d'origine
            cbEcraserOrigine.setDisable(isPersonnalise);
            if (!isPersonnalise && !cbEcraserOrigine.isSelected()) {
                tfSuffixe.setDisable(false);
                lblSuffixe.setDisable(false);
            } else {
                tfSuffixe.setDisable(true);
                lblSuffixe.setDisable(true);
            }
            
            // Activer/désactiver les options du répertoire personnalisé
            tfRepertoireSortie.setDisable(!isPersonnalise);
            btnChoisirRepertoire.setDisable(!isPersonnalise);
            cbCreerSousDossier.setDisable(!isPersonnalise);
        });
        
        vbZone.getChildren().addAll(
            lblTitre,
            rbRepertoireOrigine,
            vbOptionsOrigine,
            rbRepertoirePersonnalise,
            vbOptionsPersonnalise
        );
        
        return vbZone;
    }

    /**
     * Crée la zone de progression du traitement.
     * 
     * <p>Affiche une barre de progression et un label de statut
     * pour informer l'utilisateur de l'avancement du traitement.</p>
     *
     * @return VBox contenant la barre de progression et le label de statut
     */
    private VBox creerZoneProgression() {
        VBox vbZone = new VBox(10);
        
        pbProgression = new ProgressBar(0);
        pbProgression.setPrefWidth(760);
        pbProgression.setVisible(false);
        
        lblStatut = new Label("");
        lblStatut.setStyle("-fx-text-fill: -color-fg-muted;");
        
        vbZone.getChildren().addAll(pbProgression, lblStatut);
        return vbZone;
    }

    /**
     * Crée les boutons d'action (Valider, Annuler).
     * 
     * <p>Configure les boutons principaux et leurs gestionnaires d'événements :</p>
     * <ul>
     * <li>Bouton "Valider" : lance le traitement des images</li>
     * <li>Bouton "Annuler" : ferme la fenêtre</li>
     * </ul>
     *
     * @return HBox contenant les boutons d'action
     */
    private HBox creerBoutonsAction() {
        HBox hbBoutons = new HBox(15);
        hbBoutons.setAlignment(Pos.CENTER_RIGHT);
        
        btnValider = new Button(rbLocalisation.getString("valider"));
        btnValider.setPrefWidth(120);
        btnValider.setDefaultButton(true);
        btnValider.setDisable(true);
        btnValider.setOnAction(e -> lancerTraitement());
        
        btnAnnuler = new Button(rbLocalisation.getString("annuler"));
        btnAnnuler.setPrefWidth(120);
        btnAnnuler.setCancelButton(true);
        btnAnnuler.setOnAction(e -> annuler());
        
        hbBoutons.getChildren().addAll(btnValider, btnAnnuler);
        return hbBoutons;
    }

    /**
     * Ouvre un FileChooser pour sélectionner des fichiers d'images.
     * 
     * <p>Permet de sélectionner plusieurs fichiers d'images avec les extensions
     * suivantes : JPG, JPEG, PNG, BMP, GIF, WEBP.</p>
     */
    private void ouvrirFileChooser() {
        FileChooser fc = new FileChooser();
        fc.setTitle(rbLocalisation.getString("selectionnerImages"));
        
        // Utiliser le dernier répertoire d'images mémorisé
        String dernierRepertoire = EditeurPanovisu.getStrDernierRepertoireImages();
        if (dernierRepertoire != null && !dernierRepertoire.isEmpty()) {
            File repImages = new File(dernierRepertoire);
            if (repImages.exists() && repImages.isDirectory()) {
                fc.setInitialDirectory(repImages);
            } else {
                fc.setInitialDirectory(new File(strRepertoireDefaut));
            }
        } else {
            fc.setInitialDirectory(new File(strRepertoireDefaut));
        }
        
        // Filtres d'extensions
        FileChooser.ExtensionFilter filtreImages = new FileChooser.ExtensionFilter(
                rbLocalisation.getString("fichiersImages"),
                "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif", "*.webp"
        );
        fc.getExtensionFilters().add(filtreImages);
        
        List<File> fichiers = fc.showOpenMultipleDialog(stRedimensionnement);
        if (fichiers != null && !fichiers.isEmpty()) {
            // Mémoriser ce répertoire pour la prochaine fois
            String repertoireImages = fichiers.get(0).getParent();
            EditeurPanovisu.setStrDernierRepertoireImages(repertoireImages);
            strRepertoireDefaut = repertoireImages;
            
            // Sauvegarder les préférences
            try {
                EditeurPanovisu.sauvePreferences();
            } catch (IOException ex) {
                Logger.getLogger(RedimensionnementImagesDialogController.class.getName())
                        .log(Level.SEVERE, "Erreur lors de la sauvegarde des préférences", ex);
            }
            
            ajouterFichiers(fichiers);
        }
    }

    /**
     * Ouvre un DirectoryChooser pour sélectionner le répertoire de sortie.
     * 
     * <p>Le répertoire sélectionné sera utilisé pour sauvegarder les images
     * traitées. Si l'option "créer un sous-dossier" est activée, un sous-dossier
     * avec la date et l'heure sera créé dans ce répertoire.</p>
     */
    private void choisirRepertoireSortie() {
        javafx.stage.DirectoryChooser dc = new javafx.stage.DirectoryChooser();
        dc.setTitle(rbLocalisation.getString("choisirRepertoireSortie"));
        
        // Définir le répertoire initial
        if (repertoireSortie != null && repertoireSortie.exists()) {
            dc.setInitialDirectory(repertoireSortie);
        } else if (!strRepertoireDefaut.isEmpty()) {
            File repDefaut = new File(strRepertoireDefaut);
            if (repDefaut.exists() && repDefaut.isDirectory()) {
                dc.setInitialDirectory(repDefaut);
            }
        }
        
        File selectedDirectory = dc.showDialog(stRedimensionnement);
        if (selectedDirectory != null) {
            repertoireSortie = selectedDirectory;
            tfRepertoireSortie.setText(selectedDirectory.getAbsolutePath());
            
            // Mettre à jour le statut si des fichiers sont chargés
            if (!listeFichiersImages.isEmpty()) {
                lblStatut.setText(String.format(
                    rbLocalisation.getString("pretATraiter"),
                    listeFichiersImages.size(),
                    selectedDirectory.getName()
                ));
            }
        }
    }

    /**
     * Ajoute des fichiers à la liste de traitement.
     * 
     * <p>Filtre les fichiers pour ne conserver que les images valides
     * et évite les doublons.</p>
     *
     * @param fichiers Liste des fichiers à ajouter
     */
    private void ajouterFichiers(List<File> fichiers) {
        for (File fichier : fichiers) {
            // Vérifier que c'est une image et qu'elle n'est pas déjà dans la liste
            if (estImageValide(fichier) && !listeFichiersImages.contains(fichier)) {
                listeFichiersImages.add(fichier);
                lvListeFichiers.getItems().add(fichier);
            }
        }
        
        // Activer/désactiver les boutons selon l'état de la liste
        btnValider.setDisable(listeFichiersImages.isEmpty());
        btnViderListe.setDisable(listeFichiersImages.isEmpty());
        
        // Mettre à jour le statut
        if (!listeFichiersImages.isEmpty()) {
            lblStatut.setText(String.format(
                rbLocalisation.getString("nbFichiersATraiter"),
                listeFichiersImages.size()
            ));
        }
    }

    /**
     * Vérifie si un fichier est une image valide.
     * 
     * <p>Vérifie l'extension du fichier et son existence.</p>
     *
     * @param fichier Fichier à vérifier
     * @return true si le fichier est une image valide, false sinon
     */
    private boolean estImageValide(File fichier) {
        if (fichier == null || !fichier.exists() || !fichier.isFile()) {
            return false;
        }
        
        String nom = fichier.getName().toLowerCase();
        return nom.endsWith(".jpg") || nom.endsWith(".jpeg") || 
               nom.endsWith(".png") || nom.endsWith(".bmp") ||
               nom.endsWith(".gif") || nom.endsWith(".webp");
    }

    /**
     * Supprime les fichiers sélectionnés de la liste.
     */
    private void supprimerSelection() {
        List<File> selection = new ArrayList<>(lvListeFichiers.getSelectionModel().getSelectedItems());
        listeFichiersImages.removeAll(selection);
        lvListeFichiers.getItems().removeAll(selection);
        
        btnValider.setDisable(listeFichiersImages.isEmpty());
        btnViderListe.setDisable(listeFichiersImages.isEmpty());
        
        if (listeFichiersImages.isEmpty()) {
            lblStatut.setText("");
        } else {
            lblStatut.setText(String.format(
                rbLocalisation.getString("nbFichiersATraiter"),
                listeFichiersImages.size()
            ));
        }
    }

    /**
     * Vide complètement la liste des fichiers.
     */
    private void viderListe() {
        listeFichiersImages.clear();
        lvListeFichiers.getItems().clear();
        btnValider.setDisable(true);
        btnViderListe.setDisable(true);
        lblStatut.setText("");
    }

    /**
     * Lance le traitement des images.
     * 
     * <p>Valide les paramètres, crée une tâche de traitement en arrière-plan
     * et affiche la progression.</p>
     */
    private void lancerTraitement() {
        // Validation des paramètres
        if (!validerParametres()) {
            return;
        }
        
        // TODO: Implémenter le traitement réel des images
        // Pour l'instant, simulation avec une barre de progression
        
        bTraitementEnCours = true;
        pbProgression.setVisible(true);
        pbProgression.setProgress(0);
        
        // Désactiver les contrôles pendant le traitement
        btnValider.setDisable(true);
        btnAjouterFichiers.setDisable(true);
        btnSupprimerSelection.setDisable(true);
        btnViderListe.setDisable(true);
        vbZoneDragDrop.setDisable(true);
        
        lblStatut.setText(rbLocalisation.getString("traitementEnCours"));
        
        // Simulation de traitement (à remplacer par le traitement réel)
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int total = listeFichiersImages.size();
                for (int i = 0; i < total; i++) {
                    final int index = i;
                    Platform.runLater(() -> {
                        lblStatut.setText(String.format(
                            rbLocalisation.getString("traitementImage"),
                            index + 1,
                            total,
                            listeFichiersImages.get(index).getName()
                        ));
                    });
                    
                    // Traitement réel de l'image
                    File fichierSource = listeFichiersImages.get(i);
                    traiterImage(fichierSource);
                    
                    updateProgress(i + 1, total);
                }
                return null;
            }
        };
        
        pbProgression.progressProperty().bind(task.progressProperty());
        
        task.setOnSucceeded(event -> {
            lblStatut.setText(rbLocalisation.getString("traitementTermine"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbLocalisation.getString("outilsRedimensionnement"));
            alert.setHeaderText(rbLocalisation.getString("traitementReussi"));
            alert.setContentText(String.format(
                rbLocalisation.getString("nbImagesTraitees"),
                listeFichiersImages.size()
            ));
            alert.showAndWait();
            
            fermerFenetre();
        });
        
        task.setOnFailed(event -> {
            lblStatut.setText(rbLocalisation.getString("traitementEchec"));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbLocalisation.getString("erreur"));
            alert.setHeaderText(rbLocalisation.getString("traitementEchec"));
            alert.setContentText(task.getException().getMessage());
            alert.showAndWait();
            
            // Réactiver les contrôles
            btnValider.setDisable(false);
            btnAjouterFichiers.setDisable(false);
            btnViderListe.setDisable(false);
            vbZoneDragDrop.setDisable(false);
            bTraitementEnCours = false;
        });
        
        new Thread(task).start();
    }

    /**
     * Valide les paramètres de redimensionnement.
     * 
     * <p>Vérifie que :</p>
     * <ul>
     * <li>Des fichiers ont été sélectionnés</li>
     * <li>Les dimensions sont valides (nombres entiers positifs)</li>
     * <li>Un format de sortie a été choisi</li>
     * </ul>
     *
     * @return true si tous les paramètres sont valides, false sinon
     */
    private boolean validerParametres() {
        // Vérifier qu'il y a des fichiers
        if (listeFichiersImages.isEmpty()) {
            afficherErreur(rbLocalisation.getString("erreurAucunFichier"));
            return false;
        }
        
        // Valider la largeur
        try {
            int largeur = Integer.parseInt(tfLargeur.getText());
            if (largeur <= 0) {
                afficherErreur(rbLocalisation.getString("erreurLargeurInvalide"));
                return false;
            }
        } catch (NumberFormatException e) {
            afficherErreur(rbLocalisation.getString("erreurLargeurInvalide"));
            return false;
        }
        
        // Valider la hauteur si le ratio n'est pas conservé
        if (!cbConserverRatio.isSelected()) {
            try {
                int hauteur = Integer.parseInt(tfHauteur.getText());
                if (hauteur <= 0) {
                    afficherErreur(rbLocalisation.getString("erreurHauteurInvalide"));
                    return false;
                }
            } catch (NumberFormatException e) {
                afficherErreur(rbLocalisation.getString("erreurHauteurInvalide"));
                return false;
            }
        }
        
        // Vérifier qu'un format a été sélectionné
        if (tgFormatSortie.getSelectedToggle() == null) {
            afficherErreur(rbLocalisation.getString("erreurAucunFormat"));
            return false;
        }
        
        // Vérifier la configuration du répertoire de sortie
        if (rbRepertoirePersonnalise.isSelected()) {
            // Si répertoire personnalisé, il doit être sélectionné
            if (repertoireSortie == null || !repertoireSortie.exists() || !repertoireSortie.isDirectory()) {
                afficherErreur(rbLocalisation.getString("erreurAucunRepertoire"));
                return false;
            }
        } else {
            // Si répertoire d'origine et pas d'écrasement, vérifier le suffixe
            if (!cbEcraserOrigine.isSelected()) {
                String suffixe = tfSuffixe.getText().trim();
                if (suffixe.isEmpty()) {
                    afficherErreur(rbLocalisation.getString("erreurSuffixeVide"));
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Affiche un message d'erreur.
     *
     * @param message Message d'erreur à afficher
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rbLocalisation.getString("erreur"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Annule et ferme la fenêtre.
     * 
     * <p>Demande confirmation si un traitement est en cours.</p>
     */
    private void annuler() {
        if (bTraitementEnCours) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(rbLocalisation.getString("confirmation"));
            alert.setHeaderText(rbLocalisation.getString("traitementEnCours"));
            alert.setContentText(rbLocalisation.getString("confirmerAnnulation"));
            
            ButtonType btnOui = new ButtonType(rbLocalisation.getString("main.oui"));
            ButtonType btnNon = new ButtonType(rbLocalisation.getString("main.non"));
            alert.getButtonTypes().setAll(btnOui, btnNon);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == btnOui) {
                    fermerFenetre();
                }
            });
        } else {
            fermerFenetre();
        }
    }

    /**
     * Ferme la fenêtre de dialogue.
     */
    private void fermerFenetre() {
        if (stRedimensionnement != null) {
            stRedimensionnement.close();
        }
    }

    /**
     * Formate la taille d'un fichier de manière lisible.
     * 
     * <p>Convertit les octets en Ko, Mo, ou Go selon la taille.</p>
     *
     * @param taille Taille en octets
     * @return Chaîne formatée (ex: "2.5 Mo")
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
     * Traite (redimensionne et compresse) une image selon les paramètres définis.
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
        
        // Récupérer les dimensions cibles
        int largeurCible = Integer.parseInt(tfLargeur.getText());
        int hauteurCible = Integer.parseInt(tfHauteur.getText());
        
        // Calculer les dimensions finales en respectant le ratio si nécessaire
        int largeurFinale = largeurCible;
        int hauteurFinale = hauteurCible;
        
        if (cbConserverRatio.isSelected()) {
            // Conserver le ratio d'aspect de l'image originale
            double ratioOriginal = (double) imageOriginale.getWidth() / imageOriginale.getHeight();
            
            // Calculer la hauteur en fonction de la largeur pour conserver le ratio
            hauteurFinale = (int) Math.round(largeurCible / ratioOriginal);
        }
        
        // Redimensionner l'image
        BufferedImage imageRedimensionnee = redimensionnerImage(imageOriginale, largeurFinale, hauteurFinale);
        
        // Libérer la référence à l'image originale pour libérer les ressources
        imageOriginale.flush();
        imageOriginale = null;
        
        // Déterminer le fichier de sortie selon le mode de sauvegarde
        File fichierSortie = determinerFichierSortie(fichierSource);
        
        // Déterminer le format de sortie
        String format = getFormatSortie();
        
        // Si on écrase le fichier source, utiliser un fichier temporaire
        boolean ecrasementFichierSource = fichierSortie.getAbsolutePath().equals(fichierSource.getAbsolutePath());
        File fichierTemp = null;
        
        if (ecrasementFichierSource) {
            // Créer un fichier temporaire dans le même répertoire
            fichierTemp = new File(fichierSortie.getParent(), 
                                  "temp_" + System.currentTimeMillis() + "_" + fichierSortie.getName());
            sauvegarderImage(imageRedimensionnee, fichierTemp, format);
            
            // Supprimer l'original et renommer le temporaire
            if (!fichierSource.delete()) {
                fichierTemp.delete();
                throw new IOException("Impossible de supprimer le fichier original : " + fichierSource.getName());
            }
            if (!fichierTemp.renameTo(fichierSortie)) {
                throw new IOException("Impossible de renommer le fichier temporaire");
            }
        } else {
            // Sauvegarde directe si ce n'est pas un écrasement
            sauvegarderImage(imageRedimensionnee, fichierSortie, format);
        }
    }
    
    /**
     * Redimensionne une image aux dimensions spécifiées avec une qualité optimale.
     * 
     * @param imageSource Image source à redimensionner
     * @param largeur Largeur cible
     * @param hauteur Hauteur cible
     * @return Image redimensionnée
     */
    private BufferedImage redimensionnerImage(BufferedImage imageSource, int largeur, int hauteur) {
        BufferedImage imageRedimensionnee = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageRedimensionnee.createGraphics();
        
        // Configuration pour une qualité optimale
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(imageSource, 0, 0, largeur, hauteur, null);
        g2d.dispose();
        
        return imageRedimensionnee;
    }
    
    /**
     * Détermine le fichier de sortie selon le mode de sauvegarde sélectionné.
     * 
     * @param fichierSource Fichier source
     * @return Fichier de sortie
     */
    private File determinerFichierSortie(File fichierSource) {
        String nomFichier = fichierSource.getName();
        String nomSansExtension = nomFichier.substring(0, nomFichier.lastIndexOf('.'));
        String format = getFormatSortie();
        
        // Utiliser l'extension appropriée selon le format
        String extension;
        if ("JPEG".equals(format)) {
            extension = ".jpg";  // Utiliser .jpg au lieu de .jpeg
        } else {
            extension = "." + format.toLowerCase();
        }
        
        if (rbRepertoirePersonnalise.isSelected()) {
            // Mode : répertoire personnalisé
            return new File(repertoireSortie, nomSansExtension + extension);
        } else {
            // Mode : répertoire d'origine
            File repertoireOrigine = fichierSource.getParentFile();
            
            if (cbEcraserOrigine.isSelected()) {
                // Écraser le fichier d'origine
                return new File(repertoireOrigine, nomSansExtension + extension);
            } else {
                // Ajouter un suffixe
                String suffixe = tfSuffixe.getText().trim();
                return new File(repertoireOrigine, nomSansExtension + suffixe + extension);
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
        return "JPEG"; // Par défaut
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
            // Sauvegarde PNG ou WEBP (sans compression personnalisée pour l'instant)
            ImageIO.write(image, format.toLowerCase(), fichierSortie);
        }
    }
}

