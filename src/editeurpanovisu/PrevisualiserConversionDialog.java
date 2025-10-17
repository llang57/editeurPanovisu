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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Dialogue de prévisualisation pour la conversion au ratio 2:1.
 * 
 * <p>Permet de visualiser l'effet de la conversion avant traitement et d'ajuster
 * le positionnement vertical de l'image source.</p>
 * 
 * @author Laurent Lang
 * @version 3.1.0
 */
public class PrevisualiserConversionDialog {
    
    private Stage stage;
    private ImageView imageViewPreview;
    private BufferedImage imageSource;
    private File fichierSource;
    private ResourceBundle rbLocalisation;
    
    // Options de conversion
    private String typeRemplissage = "noir";
    private double positionVerticale = 0.5; // 0.0 = haut, 0.5 = centre, 1.0 = bas
    
    // Composants UI
    private Slider sliderPosition;
    private Label lblPositionValeur;
    private RadioButton rbNoir, rbFlou, rbMiroir, rbCouleurMoyenne;
    private ProgressIndicator piChargement;
    private VBox vbContenu;
    
    // Thread de prévisualisation
    private Thread threadPrevisualisation;
    
    // Résultat
    private boolean valide = false;
    
    /**
     * Constructeur.
     * 
     * @param fichier Fichier image à prévisualiser
     * @param typeRemplissageInitial Type de remplissage initial
     */
    public PrevisualiserConversionDialog(File fichier, String typeRemplissageInitial) {
        this.fichierSource = fichier;
        this.typeRemplissage = typeRemplissageInitial;
        this.rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu");
    }
    
    /**
     * Affiche le dialogue et attend la réponse de l'utilisateur.
     * 
     * @return true si l'utilisateur a validé, false sinon
     */
    public boolean afficher() {
        try {
            // Charger l'image source complète
            BufferedImage imageComplete = ImageIO.read(fichierSource);
            if (imageComplete == null) {
                return false;
            }
            
            // Créer une vignette pour la prévisualisation (max 800px de largeur)
            imageSource = creerVignette(imageComplete, 800);
            
            // Libérer l'image complète de la mémoire
            imageComplete.flush();
            imageComplete = null;
            
            // Créer et configurer le stage
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(EditeurPanovisu.getStPrincipal());
            stage.setTitle(rbLocalisation.getString("conversion.previsualisation") + " - " + fichierSource.getName());
            stage.setResizable(true);
            
            // Créer l'interface
            creerInterface();
            
            // Générer la prévisualisation initiale
            genererPrevisualisation();
            
            // Afficher et attendre
            stage.showAndWait();
            
            return valide;
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    /**
     * Crée l'interface du dialogue.
     */
    private void creerInterface() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: -color-bg-default;");
        
        // Titre
        Label lblTitre = new Label(rbLocalisation.getString("conversion.previsualisation"));
        lblTitre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label lblFichier = new Label(fichierSource.getName());
        lblFichier.setStyle("-fx-font-size: 12px; -fx-text-fill: -color-fg-muted;");
        
        VBox vbHeader = new VBox(5, lblTitre, lblFichier);
        
        // Zone de prévisualisation
        ScrollPane scrollPane = creerZonePrevisualisation();
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        // Contrôles de positionnement
        VBox vbControles = creerControles();
        
        // Boutons d'action
        HBox hbBoutons = creerBoutons();
        
        root.getChildren().addAll(vbHeader, scrollPane, vbControles, hbBoutons);
        
        Scene scene = new Scene(root, 950, 850);
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(750);
    }
    
    /**
     * Crée la zone de prévisualisation avec l'image.
     */
    private ScrollPane creerZonePrevisualisation() {
        vbContenu = new VBox(10);
        vbContenu.setAlignment(Pos.CENTER);
        vbContenu.setStyle(
            "-fx-background-color: -color-bg-subtle; " +
            "-fx-padding: 20;"
        );
        
        // ImageView pour la prévisualisation (ratio 2:1)
        imageViewPreview = new ImageView();
        imageViewPreview.setPreserveRatio(true);
        imageViewPreview.setFitWidth(800);
        imageViewPreview.setFitHeight(400); // Ratio 2:1 : 800x400
        
        // Indicateur de chargement
        piChargement = new ProgressIndicator();
        piChargement.setVisible(true);
        
        vbContenu.getChildren().addAll(piChargement);
        
        ScrollPane scrollPane = new ScrollPane(vbContenu);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(550);
        
        return scrollPane;
    }
    
    /**
     * Crée les contrôles de positionnement et de type de remplissage.
     */
    private VBox creerControles() {
        VBox vbControles = new VBox(15);
        vbControles.setStyle("-fx-background-color: -color-bg-default; -fx-padding: 10;");
        
        // Positionnement vertical
        Label lblPosition = new Label(rbLocalisation.getString("conversion.positionnementVertical"));
        lblPosition.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox hbSlider = new HBox(10);
        hbSlider.setAlignment(Pos.CENTER_LEFT);
        
        Label lblHaut = new Label(rbLocalisation.getString("conversion.haut"));
        Label lblCentre = new Label(rbLocalisation.getString("conversion.centre"));
        Label lblBas = new Label(rbLocalisation.getString("conversion.bas"));
        
        sliderPosition = new Slider(0.0, 1.0, positionVerticale);
        sliderPosition.setPrefWidth(300);
        sliderPosition.setShowTickMarks(true);
        sliderPosition.setShowTickLabels(false);
        sliderPosition.setMajorTickUnit(0.5);
        sliderPosition.setMinorTickCount(4);
        sliderPosition.setBlockIncrement(0.1);
        HBox.setHgrow(sliderPosition, Priority.ALWAYS);
        
        lblPositionValeur = new Label(String.format("%d%%", (int)(positionVerticale * 100)));
        lblPositionValeur.setStyle("-fx-font-weight: bold; -fx-min-width: 50px;");
        
        sliderPosition.valueProperty().addListener((obs, oldVal, newVal) -> {
            positionVerticale = newVal.doubleValue();
            lblPositionValeur.setText(String.format("%d%%", (int)(positionVerticale * 100)));
            genererPrevisualisation();
        });
        
        hbSlider.getChildren().addAll(lblHaut, sliderPosition, lblBas, lblPositionValeur);
        
        // Type de remplissage
        Label lblType = new Label(rbLocalisation.getString("conversion.typeRemplissage"));
        lblType.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        ToggleGroup tgRemplissage = new ToggleGroup();
        
        rbNoir = new RadioButton(rbLocalisation.getString("conversion.remplissageNoir"));
        rbNoir.setToggleGroup(tgRemplissage);
        rbNoir.setUserData("noir");
        
        rbFlou = new RadioButton(rbLocalisation.getString("conversion.remplissageFlou"));
        rbFlou.setToggleGroup(tgRemplissage);
        rbFlou.setUserData("flou");
        
        rbMiroir = new RadioButton(rbLocalisation.getString("conversion.remplissageMiroir"));
        rbMiroir.setToggleGroup(tgRemplissage);
        rbMiroir.setUserData("miroir");
        
        rbCouleurMoyenne = new RadioButton(rbLocalisation.getString("conversion.remplissageCouleurMoyenne"));
        rbCouleurMoyenne.setToggleGroup(tgRemplissage);
        rbCouleurMoyenne.setUserData("couleurMoyenne");
        
        // Sélectionner le type initial
        switch (typeRemplissage.toLowerCase()) {
            case "flou":
                rbFlou.setSelected(true);
                break;
            case "miroir":
                rbMiroir.setSelected(true);
                break;
            case "couleurmoyenne":
                rbCouleurMoyenne.setSelected(true);
                break;
            default:
                rbNoir.setSelected(true);
        }
        
        tgRemplissage.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                typeRemplissage = (String) newVal.getUserData();
                genererPrevisualisation();
            }
        });
        
        HBox hbRadios = new HBox(15, rbNoir, rbFlou, rbMiroir, rbCouleurMoyenne);
        
        vbControles.getChildren().addAll(
            lblPosition, hbSlider,
            new Separator(),
            lblType, hbRadios
        );
        
        return vbControles;
    }
    
    /**
     * Crée les boutons d'action.
     */
    private HBox creerBoutons() {
        HBox hbBoutons = new HBox(10);
        hbBoutons.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnRegenerer = new Button(rbLocalisation.getString("conversion.regenerer"));
        btnRegenerer.getStyleClass().addAll(Styles.BUTTON_OUTLINED);
        btnRegenerer.setOnAction(e -> genererPrevisualisation());
        
        Button btnAnnuler = new Button(rbLocalisation.getString("conversion.annuler"));
        btnAnnuler.setCancelButton(true);
        btnAnnuler.setOnAction(e -> {
            valide = false;
            stage.close();
        });
        
        Button btnValider = new Button(rbLocalisation.getString("conversion.valider"));
        btnValider.getStyleClass().addAll(Styles.ACCENT);
        btnValider.setDefaultButton(true);
        btnValider.setOnAction(e -> {
            valide = true;
            stage.close();
        });
        
        hbBoutons.getChildren().addAll(btnRegenerer, btnAnnuler, btnValider);
        return hbBoutons;
    }
    
    /**
     * Crée une vignette de l'image pour la prévisualisation.
     * Réduit la taille de l'image pour éviter les problèmes de mémoire.
     * 
     * @param imageOriginale L'image source à réduire
     * @param largeurMax La largeur maximale de la vignette
     * @return Une vignette de l'image
     */
    private BufferedImage creerVignette(BufferedImage imageOriginale, int largeurMax) {
        int largeurSource = imageOriginale.getWidth();
        int hauteurSource = imageOriginale.getHeight();
        
        // Si l'image est déjà petite, la retourner telle quelle
        if (largeurSource <= largeurMax) {
            return imageOriginale;
        }
        
        // Calculer les nouvelles dimensions en gardant le ratio
        int nouvelleLargeur = largeurMax;
        int nouvelleHauteur = (int) ((double) hauteurSource * largeurMax / largeurSource);
        
        // Créer la vignette avec interpolation bicubique
        BufferedImage vignette = new BufferedImage(
            nouvelleLargeur, 
            nouvelleHauteur, 
            BufferedImage.TYPE_INT_RGB
        );
        
        Graphics2D g2d = vignette.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(imageOriginale, 0, 0, nouvelleLargeur, nouvelleHauteur, null);
        g2d.dispose();
        
        return vignette;
    }
    
    /**
     * Génère la prévisualisation de la conversion.
     */
    private void genererPrevisualisation() {
        piChargement.setVisible(true);
        imageViewPreview.setVisible(false);
        
        // Annuler le thread précédent s'il existe
        if (threadPrevisualisation != null && threadPrevisualisation.isAlive()) {
            threadPrevisualisation.interrupt();
        }
        
        // Exécuter dans un thread séparé pour ne pas bloquer l'UI
        threadPrevisualisation = new Thread(() -> {
            BufferedImage imageConvertie = null;
            try {
                imageConvertie = convertirAvecOptions(imageSource);
                
                // Vérifier si le thread a été interrompu
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                
                // Convertir en JavaFX Image
                Image fxImage = SwingFXUtils.toFXImage(imageConvertie, null);
                
                // Libérer la mémoire
                imageConvertie.flush();
                
                // Mettre à jour l'UI dans le thread JavaFX
                Platform.runLater(() -> {
                    imageViewPreview.setImage(fxImage);
                    vbContenu.getChildren().clear();
                    vbContenu.getChildren().add(imageViewPreview);
                    imageViewPreview.setVisible(true);
                    piChargement.setVisible(false);
                });
                
            } catch (Exception ex) {
                if (!Thread.currentThread().isInterrupted()) {
                    Platform.runLater(() -> {
                        piChargement.setVisible(false);
                        Label lblErreur = new Label("Erreur : " + ex.getMessage());
                        lblErreur.setStyle("-fx-text-fill: red;");
                        vbContenu.getChildren().add(lblErreur);
                    });
                }
            } finally {
                // Libérer la mémoire
                if (imageConvertie != null) {
                    imageConvertie.flush();
                    imageConvertie = null;
                }
                System.gc();
            }
        });
        threadPrevisualisation.setDaemon(true);
        threadPrevisualisation.start();
    }
    
    /**
     * Convertit l'image au ratio 2:1 avec les options actuelles.
     */
    private BufferedImage convertirAvecOptions(BufferedImage imageSource) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        double ratioSource = (double) largeurSource / hauteurSource;
        
        final double RATIO_CIBLE = 2.0;
        
        int largeurFinale, hauteurFinale;
        
        if (Math.abs(ratioSource - RATIO_CIBLE) < 0.01) {
            return imageSource;
        }
        
        // Calculer les dimensions finales
        if (ratioSource < RATIO_CIBLE) {
            hauteurFinale = hauteurSource;
            largeurFinale = (int) Math.round(hauteurFinale * RATIO_CIBLE);
        } else {
            largeurFinale = largeurSource;
            hauteurFinale = (int) Math.round(largeurFinale / RATIO_CIBLE);
        }
        
        // Créer l'image finale
        BufferedImage imageFinale = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageFinale.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Appliquer le remplissage selon le type
        switch (typeRemplissage.toLowerCase()) {
            case "noir":
                appliquerRemplissageNoir(g2d, imageSource, largeurFinale, hauteurFinale);
                break;
            case "flou":
                appliquerRemplissageFlou(g2d, imageSource, largeurFinale, hauteurFinale);
                break;
            case "miroir":
                appliquerRemplissageMiroir(g2d, imageSource, largeurFinale, hauteurFinale);
                break;
            case "couleurmoyenne":
                appliquerRemplissageCouleurMoyenne(g2d, imageSource, largeurFinale, hauteurFinale);
                break;
        }
        
        g2d.dispose();
        
        // Ajouter la grille de coordonnées
        imageFinale = ajouterGrilleCoordonnees(imageFinale);
        
        return imageFinale;
    }
    
    /**
     * Ajoute une grille de coordonnées sur l'image (longitude/latitude).
     * Longitudes : traits pleins tous les 45°, pointillés tous les 15°.
     * Latitudes : traits pleins tous les 20°, pointillés tous les 10°.
     * Équateur (lat=0°) : en jaune pour le mettre en évidence.
     * 
     * @param image Image source
     * @return Image avec grille superposée
     */
    private BufferedImage ajouterGrilleCoordonnees(BufferedImage image) {
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        
        // Créer une copie de l'image pour dessiner dessus
        BufferedImage imageAvecGrille = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageAvecGrille.createGraphics();
        
        // Dessiner l'image de base
        g2d.drawImage(image, 0, 0, null);
        
        // Configuration pour la grille
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Couleur de la grille : gris semi-transparent (au lieu de blanc)
        java.awt.Color couleurGrille = new java.awt.Color(160, 160, 160, 140);
        
        // Dessiner les lignes verticales (longitudes)
        // 360° de longitude répartis sur la largeur
        // Traits pleins tous les 45°, pointillés tous les 15°
        for (int longitude = -180; longitude <= 180; longitude += 15) {
            float x = (float) ((longitude + 180) * largeur / 360.0);
            
            g2d.setColor(couleurGrille);
            
            if (longitude % 45 == 0) {
                // Trait plein tous les 45° (au lieu de 20°)
                g2d.setStroke(new java.awt.BasicStroke(1.5f));
                g2d.drawLine((int) x, 0, (int) x, hauteur);
            } else {
                // Trait pointillé tous les 15° (au lieu de 10°)
                float[] dashPattern = {5.0f, 5.0f};
                g2d.setStroke(new java.awt.BasicStroke(1.0f, 
                    java.awt.BasicStroke.CAP_BUTT, 
                    java.awt.BasicStroke.JOIN_MITER, 
                    10.0f, dashPattern, 0.0f));
                g2d.drawLine((int) x, 0, (int) x, hauteur);
            }
        }
        
        // Dessiner les lignes horizontales (latitudes)
        // 180° de latitude répartis sur la hauteur (de +90° en haut à -90° en bas)
        for (int latitude = -90; latitude <= 90; latitude += 10) {
            float y = (float) ((90 - latitude) * hauteur / 180.0);
            
            // L'équateur (latitude 0°) en jaune
            if (latitude == 0) {
                g2d.setColor(new java.awt.Color(255, 200, 0, 180)); // Jaune semi-transparent
                g2d.setStroke(new java.awt.BasicStroke(2.0f)); // Plus épais
                g2d.drawLine(0, (int) y, largeur, (int) y);
            } else {
                g2d.setColor(couleurGrille);
                
                if (latitude % 20 == 0) {
                    // Trait plein tous les 20°
                    g2d.setStroke(new java.awt.BasicStroke(1.5f));
                    g2d.drawLine(0, (int) y, largeur, (int) y);
                } else {
                    // Trait pointillé tous les 10°
                    float[] dashPattern = {5.0f, 5.0f};
                    g2d.setStroke(new java.awt.BasicStroke(1.0f, 
                        java.awt.BasicStroke.CAP_BUTT, 
                        java.awt.BasicStroke.JOIN_MITER, 
                        10.0f, dashPattern, 0.0f));
                    g2d.drawLine(0, (int) y, largeur, (int) y);
                }
            }
        }
        
        g2d.dispose();
        return imageAvecGrille;
    }
    
    /**
     * Calcule la position Y selon le positionnement vertical choisi.
     */
    private int calculerPositionY(int hauteurFinale, int hauteurSource) {
        int espaceDisponible = hauteurFinale - hauteurSource;
        return (int) Math.round(espaceDisponible * positionVerticale);
    }
    
    /**
     * Applique un remplissage noir.
     */
    private void appliquerRemplissageNoir(Graphics2D g2d, BufferedImage imageSource, 
                                          int largeurFinale, int hauteurFinale) {
        g2d.setColor(java.awt.Color.BLACK);
        g2d.fillRect(0, 0, largeurFinale, hauteurFinale);
        
        int x = (largeurFinale - imageSource.getWidth()) / 2;
        int y = calculerPositionY(hauteurFinale, imageSource.getHeight());
        g2d.drawImage(imageSource, x, y, null);
    }
    
    /**
     * Applique un remplissage flou très prononcé.
     */
    private void appliquerRemplissageFlou(Graphics2D g2d, BufferedImage imageSource, 
                                          int largeurFinale, int hauteurFinale) {
        BufferedImage imageFond = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
        Graphics2D gFond = imageFond.createGraphics();
        gFond.drawImage(imageSource, 0, 0, largeurFinale, hauteurFinale, null);
        gFond.dispose();
        
        // Kernel gaussien 9×9
        float[] matrix9x9 = new float[81];
        int kernelSize = 9;
        float sigma = 5.0f;
        float sum = 0.0f;
        int center = kernelSize / 2;
        
        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                int dx = x - center;
                int dy = y - center;
                float value = (float) Math.exp(-(dx*dx + dy*dy) / (2*sigma*sigma));
                matrix9x9[y * kernelSize + x] = value;
                sum += value;
            }
        }
        
        for (int i = 0; i < matrix9x9.length; i++) {
            matrix9x9[i] /= sum;
        }
        
        Kernel kernel = new Kernel(kernelSize, kernelSize, matrix9x9);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        
        BufferedImage temp = imageFond;
        for (int i = 0; i < 10; i++) {  // Réduire à 10 itérations pour la prévisualisation (plus rapide)
            BufferedImage temp2 = new BufferedImage(largeurFinale, hauteurFinale, BufferedImage.TYPE_INT_RGB);
            temp2 = op.filter(temp, temp2);
            temp = temp2;
        }
        
        g2d.drawImage(temp, 0, 0, null);
        
        int x = (largeurFinale - imageSource.getWidth()) / 2;
        int y = calculerPositionY(hauteurFinale, imageSource.getHeight());
        g2d.drawImage(imageSource, x, y, null);
    }
    
    /**
     * Applique un remplissage miroir.
     */
    private void appliquerRemplissageMiroir(Graphics2D g2d, BufferedImage imageSource, 
                                            int largeurFinale, int hauteurFinale) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        
        int xCentre = (largeurFinale - largeurSource) / 2;
        int yCentre = calculerPositionY(hauteurFinale, hauteurSource);
        
        if (xCentre > 0) {
            g2d.drawImage(imageSource, 0, yCentre, xCentre, yCentre + hauteurSource,
                         xCentre, 0, 0, hauteurSource, null);
            g2d.drawImage(imageSource, xCentre + largeurSource, yCentre, largeurFinale, yCentre + hauteurSource,
                         largeurSource, 0, largeurSource - xCentre, hauteurSource, null);
        }
        
        if (yCentre > 0) {
            g2d.drawImage(imageSource, xCentre, 0, xCentre + largeurSource, yCentre,
                         0, yCentre, largeurSource, 0, null);
        }
        
        int yBas = yCentre + hauteurSource;
        if (yBas < hauteurFinale) {
            g2d.drawImage(imageSource, xCentre, yBas, xCentre + largeurSource, hauteurFinale,
                         0, hauteurSource, largeurSource, hauteurSource - (hauteurFinale - yBas), null);
        }
        
        g2d.drawImage(imageSource, xCentre, yCentre, null);
    }
    
    /**
     * Applique un remplissage par couleur moyenne.
     */
    private void appliquerRemplissageCouleurMoyenne(Graphics2D g2d, BufferedImage imageSource, 
                                                    int largeurFinale, int hauteurFinale) {
        int largeurSource = imageSource.getWidth();
        int hauteurSource = imageSource.getHeight();
        
        int xCentre = (largeurFinale - largeurSource) / 2;
        int yCentre = calculerPositionY(hauteurFinale, hauteurSource);
        
        int nbLignesAnalyse = Math.min(10, Math.min(largeurSource, hauteurSource) / 4);
        
        if (yCentre > 0) {
            java.awt.Color couleurHaut = calculerCouleurMoyenne(imageSource, 0, 0, 
                                                                largeurSource, nbLignesAnalyse);
            g2d.setColor(couleurHaut);
            g2d.fillRect(0, 0, largeurFinale, yCentre);
        }
        
        int yBas = yCentre + hauteurSource;
        if (yBas < hauteurFinale) {
            java.awt.Color couleurBas = calculerCouleurMoyenne(imageSource, 0, 
                                                               hauteurSource - nbLignesAnalyse,
                                                               largeurSource, nbLignesAnalyse);
            g2d.setColor(couleurBas);
            g2d.fillRect(0, yBas, largeurFinale, hauteurFinale - yBas);
        }
        
        if (xCentre > 0) {
            java.awt.Color couleurGauche = calculerCouleurMoyenne(imageSource, 0, 0, 
                                                                  nbLignesAnalyse, hauteurSource);
            g2d.setColor(couleurGauche);
            g2d.fillRect(0, yCentre, xCentre, hauteurSource);
            
            java.awt.Color couleurDroite = calculerCouleurMoyenne(imageSource, 
                                                                  largeurSource - nbLignesAnalyse, 0,
                                                                  nbLignesAnalyse, hauteurSource);
            g2d.setColor(couleurDroite);
            g2d.fillRect(xCentre + largeurSource, yCentre, largeurFinale - xCentre - largeurSource, hauteurSource);
        }
        
        g2d.drawImage(imageSource, xCentre, yCentre, null);
    }
    
    /**
     * Calcule la couleur moyenne d'une zone.
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
     * Retourne la position verticale choisie (0.0 à 1.0).
     */
    public double getPositionVerticale() {
        return positionVerticale;
    }
    
    /**
     * Définit la position verticale initiale (0.0 à 1.0).
     */
    public void setPositionVerticale(double position) {
        this.positionVerticale = Math.max(0.0, Math.min(1.0, position));
    }
    
    /**
     * Retourne le type de remplissage choisi.
     */
    public String getTypeRemplissage() {
        return typeRemplissage;
    }
}
