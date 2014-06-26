/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.nombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.repertAppli;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    private static final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});
    private static final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    private static ResourceBundle rb;
    /**
     *
     */
    public static final String styleDefautBarreNavigation = "retina";
    /**
     *
     */
    public static final String styleHotSpotDefaut = "hotspot.png";
    /**
     *
     */
    public static String positionBarre = "bottom:center";
    /**
     *
     */
    public static String styleHotSpots = styleHotSpotDefaut;

    /**
     *
     */
    public static String styleBarre = styleDefautBarreNavigation;

    /**
     *
     */
    public static double dXBarre = 0;

    /**
     *
     */
    public static double dYBarre = 10;

    /**
     *
     */
    public static double tailleBarre = 30;

    /**
     *
     */
    public static String toggleBarreVisibilite = "oui";

    /**
     *
     */
    public static String toggleBarreDeplacements = "oui";

    /**
     *
     */
    public static String toggleBarreZoom = "oui";

    /**
     *
     */
    public static String toggleBarreOutils = "oui";

    /**
     *
     */
    public static String toggleBoutonInfo = "oui";

    /**
     *
     */
    public static String toggleBoutonAide = "oui";

    /**
     *
     */
    public static String toggleBoutonRotation = "oui";

    /**
     *
     */
    public static String toggleBoutonFS = "oui";

    /**
     *
     */
    public static String toggleBoutonSouris = "oui";

    /**
     *
     */
    public static String titrePoliceNom = "Verdana";
    public static String titrePoliceStyle = "Regular";
    public static String titrePoliceTaille = "12.0";
    public static String couleurTitre = "#ffffff";
    public static String couleurFondTitre = "#000000";
    public static Double titreOpacite = 0.8;
    public static Double titreTaille = 100.0;

    /**
     *
     */
    public static boolean bAfficheBoussole = false;
    public static String imageBoussole = "rose3.png";
    public static String positionBoussole = "top:right";
    public static double dXBoussole = 20;
    public static double dYBoussole = 20;
    public static double tailleBoussole = 100;
    public static double opaciteBoussole = 0.8;
    public static boolean bAiguilleMobileBoussole = true;
    private static ImageView imgBoussole;
    private static ImageView imgAiguille;
    private static BigDecimalField boussDXSpinner;
    private static BigDecimalField boussDYSpinner;
    private static Slider SLTailleBoussole;
    private static Slider SLOpaciteBoussole;
    private static CheckBox CBAiguilleMobile;
    private static CheckBox CBAfficheBoussole;
    private static RadioButton RBBoussTopLeft;
    private static RadioButton RBBoussTopRight;
    private static RadioButton RBBoussBottomLeft;
    private static RadioButton RBBoussBottomRight;

    /**
     *
     */
    private static String repertMasques;
    public static boolean bAfficheMasque = false;
    public static String imageMasque = "MA.png";
    public static String positionMasque = "top:right";
    public static double dXMasque = 20;
    public static double dYMasque = 20;
    public static double tailleMasque = 30;
    public static double opaciteMasque = 0.8;
    public static boolean bMasqueNavigation = true;
    public static boolean bMasqueBoussole = true;
    public static boolean bMasqueTitre = true;
    public static boolean bMasquePlan = true;
    public static boolean bMasqueReseaux = true;
    public static boolean bMasqueVignettes = true;
    private static ImageView IVMasque;
    private static BigDecimalField masqueDXSpinner;
    private static BigDecimalField masqueDYSpinner;
    private static Slider SLTailleMasque;
    private static Slider SLOpaciteMasque;
    private static CheckBox CBAfficheMasque;
    private static CheckBox CBMasqueNavigation;
    private static CheckBox CBMasqueBoussole;
    private static CheckBox CBMasqueTitre;
    private static CheckBox CBMasquePlan;
    private static CheckBox CBMasqueReseaux;
    private static CheckBox CBMasqueVignettes;
    private static RadioButton RBMasqueTopLeft;
    private static RadioButton RBMasqueTopRight;
    private static RadioButton RBMasqueBottomLeft;
    private static RadioButton RBMasqueBottomRight;

    /**
     *
     */
    private static String repertReseauxSociaux;
    public static boolean bAfficheReseauxSociaux = false;
    public static String imageReseauxSociauxTwitter = "twitter.png";
    public static String imageReseauxSociauxGoogle = "google.png";
    public static String imageReseauxSociauxFacebook = "facebook.png";
    public static String imageReseauxSociauxEmail = "email.png";
    public static String positionReseauxSociaux = "top:right";
    public static double dXReseauxSociaux = 20;
    public static double dYReseauxSociaux = 20;
    public static double tailleReseauxSociaux = 30;
    public static double opaciteReseauxSociaux = 0.8;
    public static boolean bReseauxSociauxTwitter = true;
    public static boolean bReseauxSociauxGoogle = true;
    public static boolean bReseauxSociauxFacebook = true;
    public static boolean bReseauxSociauxEmail = true;
    private static ImageView imgTwitter;
    private static ImageView imgGoogle;
    private static ImageView imgFacebook;
    private static ImageView imgEmail;
    private static BigDecimalField reseauxSociauxDXSpinner;
    private static BigDecimalField reseauxSociauxDYSpinner;
    private static Slider SLTailleReseauxSociaux;
    private static Slider SLOpaciteReseauxSociaux;
    private static CheckBox CBAfficheReseauxSociaux;
    private static CheckBox CBReseauxSociauxTwitter;
    private static CheckBox CBReseauxSociauxGoogle;
    private static CheckBox CBReseauxSociauxFacebook;
    private static CheckBox CBReseauxSociauxEmail;
    private static RadioButton RBReseauxSociauxTopLeft;
    private static RadioButton RBReseauxSociauxTopRight;
    private static RadioButton RBReseauxSociauxBottomLeft;
    private static RadioButton RBReseauxSociauxBottomRight;
    /*
     *
     */
    private static AnchorPane APVignettes;
    private static AnchorPane APVisuVignettes;
    public static boolean bAfficheVignettes = false;
    public static String couleurFondVignettes = "#ffffff";
    public static String positionVignettes = "bottom";
    public static double tailleImageVignettes = 120;
    public static double opaciteVignettes = 0.8;
    private static Slider SLOpaciteVignettes;
    private static Slider SLTailleVignettes;
    private static CheckBox CBAfficheVignettes;
    private static RadioButton RBVignettesLeft;
    private static RadioButton RBVignettesRight;
    private static RadioButton RBVignettesBottom;
    private static ColorPicker CPCouleurFondVignettes;

    public Pane tabInterface;
    private static HBox HBInterface;
    private static AnchorPane APVisualisation;
    private static VBox VBOutils;
    private static RadioButton RBClair;
    private static RadioButton RBSombre;
    private static ImageView IMVisualisation;
    final ToggleGroup grpImage = new ToggleGroup();
    final ToggleGroup grpPostBarre = new ToggleGroup();
    final ToggleGroup grpPostBouss = new ToggleGroup();
    final ToggleGroup grpPostMasque = new ToggleGroup();
    final ToggleGroup grpPostReseauxSociaux = new ToggleGroup();
    final ToggleGroup grpPostVignettes = new ToggleGroup();
    private static Image imageClaire;
    private static Image imageSombre;
    private static HBox HBbarreBoutons;
    private static HBox HBOutils;
    private static Label txtTitre;
    private static ImageView IVInfo;
    private static ImageView IVAide;
    private static ImageView IVAutoRotation;
    private static ImageView IVModeSouris;
    private static ImageView IVPleinEcran;
    private static HBox HBZoom;
    private static ImageView IVZoomPlus;
    private static ImageView IVZoomMoins;
    private static HBox HBDeplacements;
    private static ImageView IVHaut;
    private static ImageView IVBas;
    private static ImageView IVGauche;
    private static ImageView IVDroite;
    private static ImageView IVHotSpot;
    private static ComboBox CBlisteStyle;

    private static String repertBoutonsPrincipal;
    private static String repertHotSpots;
    private static String repertBoussoles;
    private static RadioButton RBTopLeft;
    private static RadioButton RBTopCenter;
    private static RadioButton RBTopRight;
    private static RadioButton RBMiddleLeft;
    private static RadioButton RBMiddleCenter;
    private static RadioButton RBMiddleRight;
    private static RadioButton RBBottomLeft;
    private static RadioButton RBBottomCenter;
    private static RadioButton RBBottomRight;
    private static CheckBox CBVisible;
    private static CheckBox CBDeplacements;
    private static CheckBox CBZoom;
    private static CheckBox CBOutils;
    private static CheckBox CBFS;
    private static CheckBox CBSouris;
    private static CheckBox CBRotation;
    private static CheckBox CBSuivantPrecedent;
    private static ImageView imgSuivant;
    private static ImageView imgPrecedent;
    private static VBox fondSuivant;
    private static VBox fondPrecedent;
    public static boolean bSuivantPrecedent;
    private static BigDecimalField dXSpinner;
    private static BigDecimalField dYSpinner;
    private static ColorPicker CPCouleurFondTitre;
    private static ColorPicker CPCouleurTitre;
    private static ComboBox CBListePolices;
    private static Slider SLTaillePolice;
    private static Slider SLOpacite;
    private static Slider SLTaille;
    private static ColorPicker CPCouleurTheme;
    private static ColorPicker CPCouleurHotspots;
    private static ColorPicker CPCouleurBoutons;
    private static ColorPicker CPCouleurMasques;
    private static Color couleurHotspots = Color.hsb(120, 1.0, 1.0);
    private static Color couleurBoutons = Color.hsb(120, 1.0, 1.0);
    private static Color couleurMasque = Color.hsb(120, 1.0, 1.0);
    private static Color couleurTheme = Color.hsb(120, 1.0, 1.0);
    public static Image[] imageBoutons = new Image[25];
    public static String[] nomImagesBoutons = new String[25];
    public static PixelReader[] lisBoutons = new PixelReader[25];
    public static WritableImage[] nouveauxBoutons = new WritableImage[25];
    public static PixelWriter[] PWnouveauxBoutons = new PixelWriter[25];
    public static int nombreImagesBouton = 0;
    public static Image imgMasque;
    public static PixelReader lisMasque;
    public static WritableImage nouveauxMasque;
    public static PixelWriter PWnouveauxMasque;

    private void chargeBarre(String styleBarre, String strHotSpot, String strMA) {
//        System.out.println("Masque " + strMA + " => HS " + strHotSpot);

        File repertBarre = new File(repertBoutonsPrincipal + File.separator + styleBarre);
        File[] Repertoires = repertBarre.listFiles(IMAGE_FILTER);
        int i = 0;
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomFich = repert.getName();
                String nomFichComplet = repert.getAbsolutePath();
//                System.out.println("nom Fichier " + nomFichComplet + " =>" + nomFich);
                imageBoutons[i] = new Image("file:" + nomFichComplet);
                lisBoutons[i] = imageBoutons[i].getPixelReader();
                int width = (int) imageBoutons[i].getWidth();
                int height = (int) imageBoutons[i].getHeight();
                nouveauxBoutons[i] = new WritableImage(width, height);
                PWnouveauxBoutons[i] = nouveauxBoutons[i].getPixelWriter();
                switch (nomFich) {
                    case "aide.png":
                        IVAide = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "info.png":
                        IVInfo = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "haut.png":
                        IVHaut = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "bas.png":
                        IVBas = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "droite.png":
                        IVDroite = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "gauche.png":
                        IVGauche = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "fs.png":
                        IVPleinEcran = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "rotation.png":
                        IVAutoRotation = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "souris.png":
                        IVModeSouris = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "zoomin.png":
                        IVZoomPlus = new ImageView(nouveauxBoutons[i]);
                        break;
                    case "zoomout.png":
                        IVZoomMoins = new ImageView(nouveauxBoutons[i]);
                        break;
                }
                nomImagesBoutons[i] = nomFich;
                i++;
            }
        }
        nombreImagesBouton = i;
        imageBoutons[nombreImagesBouton] = new Image("file:" + repertHotSpots + File.separator + strHotSpot);
        lisBoutons[nombreImagesBouton] = imageBoutons[nombreImagesBouton].getPixelReader();
        int width = (int) imageBoutons[nombreImagesBouton].getWidth();
        int height = (int) imageBoutons[nombreImagesBouton].getHeight();
        nouveauxBoutons[nombreImagesBouton] = new WritableImage(width, height);
        PWnouveauxBoutons[nombreImagesBouton] = nouveauxBoutons[nombreImagesBouton].getPixelWriter();
        IVHotSpot = new ImageView(nouveauxBoutons[nombreImagesBouton]);
//        System.out.println("file:" + repertMasques + File.separator + strMA + " nombreImagesBouton= " + nombreImagesBouton);
        imgMasque = new Image("file:" + repertMasques + File.separator + strMA);
//        imageBoutons[nombreImagesBouton+1] = new Image("file:D:\\github\\editeurPanovisu\\theme\\MA\\MA.png");

        lisMasque = imgMasque.getPixelReader();
        width = (int) imgMasque.getWidth();
        height = (int) imgMasque.getHeight();
        nouveauxMasque = new WritableImage(width, height);
        PWnouveauxMasque = nouveauxMasque.getPixelWriter();
        IVMasque = new ImageView(nouveauxMasque);

        changeCouleurBarre(couleurBoutons.getHue(), couleurBoutons.getSaturation(), couleurBoutons.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());

    }

    private void sauveBarre() {

    }

    private void changeCouleurBarre(double couleurFinale, double sat, double bright) {
        for (int i = 0; i < nombreImagesBouton; i++) {
            for (int y = 0; y < imageBoutons[i].getHeight(); y++) {
                for (int x = 0; x < imageBoutons[i].getWidth(); x++) {
                    Color color = lisBoutons[i].getColor(x, y);
                    double brightness = color.getBrightness();
                    //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if (saturation < 0.2) {
                        couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                    } else {
                        couleur = Color.hsb(couleurFinale, saturation * 0.4 + sat * 0.6, brightness * 0.4 + bright * 0.6, opacity);
                    }
                    PWnouveauxBoutons[i].setColor(x, y, couleur);
                }
            }
        }

    }

    private void changeCouleurMasque(double couleurFinale, double sat, double bright) {
//        System.out.println(" haut : "+imageBouttons[nombreImagesBouton + 1].getHeight()+"  long : "+imageBouttons[nombreImagesBouton + 1].getWidth());
        for (int y = 0; y < imgMasque.getHeight(); y++) {
            for (int x = 0; x < imgMasque.getWidth(); x++) {
                Color color = lisMasque.getColor(x, y);
                double brightness = color.getBrightness();
                //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    couleur = Color.hsb(couleurFinale, sat, brightness * 0.5 + bright * 0.5, opacity);
                } else {
                    if (saturation > 0.05) {
                        couleur = Color.hsb(couleurFinale, saturation * 0.5 + sat * 0.5, brightness * 0.5 + bright * 0.5, opacity);
                    } else {
                        couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                    }
                }
                PWnouveauxMasque.setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurHS(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < imageBoutons[nombreImagesBouton].getHeight(); y++) {
            for (int x = 0; x < imageBoutons[nombreImagesBouton].getWidth(); x++) {
                Color color = lisBoutons[nombreImagesBouton].getColor(x, y);
                double brightness = color.getBrightness();
                //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    couleur = Color.hsb(couleurFinale, sat, brightness * 0.5 + bright * 0.5, opacity);
                } else {
                    if (saturation > 0.05) {
                        couleur = Color.hsb(couleurFinale, saturation * 0.5 + sat * 0.5, brightness * 0.5 + bright * 0.5, opacity);
                    } else {
                        couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                    }
                }
                PWnouveauxBoutons[nombreImagesBouton].setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurTitre(String coul) {
        couleurFondTitre = "#" + coul;
        txtTitre.setStyle("-fx-background-color : " + couleurFondTitre);
    }

    private void changeCouleurVignettes(String coul) {
        couleurFondVignettes = "#" + coul;
        afficheVignettes();
    }

    /**
     *
     */
    private void afficheBoussole() {
        imgAiguille.setVisible(bAfficheBoussole);
        imgBoussole.setVisible(bAfficheBoussole);

        imgAiguille.setFitWidth(tailleBoussole / 5);
        imgAiguille.setFitHeight(tailleBoussole);
        imgAiguille.setOpacity(opaciteBoussole);
        imgAiguille.setSmooth(true);

        imgBoussole.setImage(new Image("file:" + repertBoussoles + File.separator + imageBoussole));
        imgBoussole.setFitWidth(tailleBoussole);
        imgBoussole.setFitHeight(tailleBoussole);
        imgBoussole.setOpacity(opaciteBoussole);
        imgBoussole.setSmooth(true);
        String positXBoussole = positionBoussole.split(":")[1];
        String positYBoussole = positionBoussole.split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (positXBoussole) {
            case "left":
                posX = IMVisualisation.getLayoutX() + dXBoussole;
                break;
            case "right":
                posX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - dXBoussole - imgBoussole.getFitWidth();
                break;
        }
        switch (positYBoussole) {
            case "bottom":
                posY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - imgBoussole.getFitHeight() - dYBoussole;
                break;
            case "top":
                posY = IMVisualisation.getLayoutY() + dYBoussole;
                break;
        }
//        System.out.println(positionBoussole + " posX:" + posX + ", posY:" + posY);
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);
        imgAiguille.setOpacity(opaciteBoussole);
        imgAiguille.setVisible(bAfficheBoussole);

        imgBoussole.setOpacity(opaciteBoussole);
        imgBoussole.setVisible(bAfficheBoussole);
    }

    /**
     *
     */
    private void afficheMasque() {
        APVisualisation.getChildren().remove(IVMasque);
        APVisualisation.getChildren().add(IVMasque);
        IVMasque.setVisible(bAfficheMasque);
        IVMasque.setFitWidth(tailleMasque);
        IVMasque.setFitHeight(tailleMasque);
        IVMasque.setOpacity(opaciteMasque);
//        System.out.println("file:" + repertMasques + File.separator + imageMasque+"taille Masque :"+IVMasque.getFitWidth());
//        System.out.println("file:" + repertMasques + File.separator + imageMasque+"taille Masque :"+IVMasque.getFitWidth()+bAfficheMasque);
//        IVMasque.setSmooth(true);
        String positXMasque = positionMasque.split(":")[1];
        String positYMasque = positionMasque.split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (positXMasque) {
            case "left":
                posX = IMVisualisation.getLayoutX() + dXMasque;
                break;
            case "right":
                posX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - dXMasque - IVMasque.getFitWidth();
                break;
        }
        switch (positYMasque) {
            case "bottom":
                posY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - IVMasque.getFitHeight() - dYMasque;
                break;
            case "top":
                posY = IMVisualisation.getLayoutY() + dYMasque;
                break;
        }
//        System.out.println(positionMasque + " posX:" + posX + ", posY:" + posY);
        IVMasque.setLayoutX(posX);
        IVMasque.setLayoutY(posY);
//
    }

    /**
     *
     */
    private void afficheReseauxSociaux() {
        imgTwitter.setVisible(bAfficheReseauxSociaux);
        imgGoogle.setVisible(bAfficheReseauxSociaux);
        imgFacebook.setVisible(bAfficheReseauxSociaux);
        imgEmail.setVisible(bAfficheReseauxSociaux);
//        System.out.println(repertReseauxSociaux + imageReseauxSociauxTwitter);
        imgTwitter.setFitWidth(tailleReseauxSociaux);
        imgTwitter.setFitHeight(tailleReseauxSociaux);
        imgTwitter.setOpacity(opaciteReseauxSociaux);
        imgTwitter.setSmooth(true);
        imgTwitter.setVisible(false);
        imgGoogle.setFitWidth(tailleReseauxSociaux);
        imgGoogle.setFitHeight(tailleReseauxSociaux);
        imgGoogle.setOpacity(opaciteReseauxSociaux);
        imgGoogle.setSmooth(true);
        imgGoogle.setVisible(false);
        imgFacebook.setFitWidth(tailleReseauxSociaux);
        imgFacebook.setFitHeight(tailleReseauxSociaux);
        imgFacebook.setOpacity(opaciteReseauxSociaux);
        imgFacebook.setSmooth(true);
        imgFacebook.setVisible(false);
        imgEmail.setFitWidth(tailleReseauxSociaux);
        imgEmail.setFitHeight(tailleReseauxSociaux);
        imgEmail.setOpacity(opaciteReseauxSociaux);
        imgEmail.setSmooth(true);
        imgEmail.setVisible(false);
        String positXReseauxSociaux = positionReseauxSociaux.split(":")[1];
        String positYReseauxSociaux = positionReseauxSociaux.split(":")[0];
        double posX = 0;
        double posY = 0;
        double dX;
        switch (positXReseauxSociaux) {
            case "left":
                posX = IMVisualisation.getLayoutX() + dXReseauxSociaux;
                dX = imgEmail.getFitWidth() + 5;
                if (bReseauxSociauxTwitter && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgTwitter.setLayoutX(posX);
                    imgTwitter.setVisible(true);
                    posX += dX;

                }
                if (bReseauxSociauxGoogle && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgGoogle.setLayoutX(posX);
                    imgGoogle.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxFacebook && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgFacebook.setLayoutX(posX);
                    imgFacebook.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxEmail && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgEmail.setLayoutX(posX);
                    imgEmail.setVisible(true);
                    posX += dX;
                }

                break;
            case "right":
                posX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - dXReseauxSociaux - imgEmail.getFitWidth();
                dX = -(imgEmail.getFitWidth() + 5);
                if (bReseauxSociauxEmail && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgEmail.setLayoutX(posX);
                    imgEmail.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxFacebook && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgFacebook.setLayoutX(posX);
                    imgFacebook.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxGoogle && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgGoogle.setLayoutX(posX);
                    imgGoogle.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxTwitter && bAfficheReseauxSociaux) {
//                    System.out.println("posX " + posX + positXReseauxSociaux);
                    imgTwitter.setLayoutX(posX);
                    imgTwitter.setVisible(true);
                    posX += dX;
                }
                break;
        }
        switch (positYReseauxSociaux) {
            case "bottom":
                posY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - imgEmail.getFitHeight() - dYReseauxSociaux;
                break;
            case "top":
                posY = IMVisualisation.getLayoutY() + dYReseauxSociaux;
                break;
        }
//        System.out.println(positionReseauxSociaux + " posX:" + posX + ", posY:" + posY);
        imgTwitter.setLayoutY(posY);
        imgGoogle.setLayoutY(posY);
        imgFacebook.setLayoutY(posY);
        imgEmail.setLayoutY(posY);
    }

    /**
     *
     */
    private void afficheVignettes() {
            fondPrecedent.setLayoutX(IMVisualisation.getLayoutX());
            fondSuivant.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()));
        APVisuVignettes.setVisible(bAfficheVignettes);
        if (bAfficheVignettes) {
            ImageView[] imgVignettes = new ImageView[nombrePanoramiques];
            APVisuVignettes.getChildren().clear();
            APVisuVignettes.setOpacity(opaciteVignettes);
            APVisuVignettes.setStyle("-fx-background-color : " + couleurFondVignettes);
            switch (positionVignettes) {
                case "bottom":
                    APVisuVignettes.setPrefHeight(tailleImageVignettes / 2 + 10);
                    APVisuVignettes.setPrefWidth(IMVisualisation.getFitWidth());
                    APVisuVignettes.setMinHeight(tailleImageVignettes / 2 + 10);
                    APVisuVignettes.setMinWidth(IMVisualisation.getFitWidth());
                    APVisuVignettes.setLayoutX(IMVisualisation.getLayoutX());
                    APVisuVignettes.setLayoutY(IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - APVisuVignettes.getPrefHeight());
                    break;
                case "left":
                    APVisuVignettes.setPrefHeight(IMVisualisation.getFitHeight() - txtTitre.getHeight());
                    APVisuVignettes.setPrefWidth(tailleImageVignettes + 10);
                    APVisuVignettes.setMinHeight(IMVisualisation.getFitHeight() - txtTitre.getHeight());
                    APVisuVignettes.setMinWidth(tailleImageVignettes + 10);
                    APVisuVignettes.setLayoutX(IMVisualisation.getLayoutX());
                    APVisuVignettes.setLayoutY(IMVisualisation.getLayoutY() + txtTitre.getHeight());
                    fondPrecedent.setLayoutX(IMVisualisation.getLayoutX() + APVisuVignettes.getPrefWidth());
                    break;
                case "right":
                    APVisuVignettes.setPrefHeight(IMVisualisation.getFitHeight() - txtTitre.getHeight());
                    APVisuVignettes.setPrefWidth(tailleImageVignettes + 10);
                    APVisuVignettes.setMinHeight(IMVisualisation.getFitHeight() - txtTitre.getHeight());
                    APVisuVignettes.setMinWidth(tailleImageVignettes + 10);
                    APVisuVignettes.setLayoutX(IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - APVisuVignettes.getPrefWidth());
                    APVisuVignettes.setLayoutY(IMVisualisation.getLayoutY() + txtTitre.getHeight());
                    fondSuivant.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()) - APVisuVignettes.getPrefWidth());
                    break;
            }
            int maxVignettes = 5;
            int nombre = (nombrePanoramiques > maxVignettes) ? maxVignettes : nombrePanoramiques;
            for (int i = 0; i < nombre; i++) {
                imgVignettes[i] = new ImageView(panoramiquesProjet[i].getVignettePanoramique());
                imgVignettes[i].setFitWidth(tailleImageVignettes);
                imgVignettes[i].setFitHeight(tailleImageVignettes / 2);
                switch (positionVignettes) {
                    case "bottom":
                        imgVignettes[i].setLayoutX((tailleImageVignettes + 10) * i + 5);
                        imgVignettes[i].setLayoutY(5);
                        break;
                    case "left":
                        imgVignettes[i].setLayoutY((tailleImageVignettes / 2 + 10) * i + 5);
                        imgVignettes[i].setLayoutX(5);
                        break;
                    case "right":
                        imgVignettes[i].setLayoutY((tailleImageVignettes / 2 + 10) * i + 5);
                        imgVignettes[i].setLayoutX(5);
                        break;
                }
                APVisuVignettes.getChildren().add(imgVignettes[i]);
            }
        }

    }

    /**
     *
     * @param position
     * @param dX
     * @param dY
     * @param taille
     * @param styleBoutons
     * @param styleHS
     */
    public void afficheBouton(String position, double dX, double dY, double taille, String styleBoutons, String styleHS) {
        String repertBoutons = "file:" + repertBoutonsPrincipal + File.separator + styleBoutons;
        APVisualisation.getChildren().clear();
        APVisualisation.getChildren().addAll(RBClair, RBSombre, IMVisualisation, txtTitre, imgBoussole, imgAiguille, imgTwitter, imgGoogle, imgFacebook, imgEmail, APVisuVignettes, fondSuivant, fondPrecedent);
        chargeBarre(styleBoutons, styleHS, imageMasque);
        HBbarreBoutons = new HBox();
        HBbarreBoutons.setId("barreBoutons");
        HBbarreBoutons.setVisible(toggleBarreVisibilite.equals("oui"));
        IVHotSpot.setFitWidth(30);
        IVHotSpot.setPreserveRatio(true);
        IVHotSpot.setLayoutX(510);
        IVHotSpot.setLayoutY(310);
        int nombreBoutons = 11;
        if (toggleBarreDeplacements.equals("non")) {
            nombreBoutons -= 4;
        }
        if (toggleBarreZoom.equals("non")) {
            nombreBoutons -= 2;
        }
        if (toggleBarreOutils.equals("non")) {
            nombreBoutons -= 5;
        } else {
            if (toggleBoutonFS.equals("non")) {
                nombreBoutons -= 1;
            }
            if (toggleBoutonRotation.equals("non")) {
                nombreBoutons -= 1;
            }
            if (toggleBoutonSouris.equals("non")) {
                nombreBoutons -= 1;
            }
        }
        double tailleBarre = (double) nombreBoutons * (double) taille;
        HBbarreBoutons.setPrefWidth(tailleBarre);
        HBbarreBoutons.setPrefHeight((double) taille);
        HBbarreBoutons.setMinWidth(tailleBarre);
        HBbarreBoutons.setMinHeight((double) taille);
        HBbarreBoutons.setMaxWidth(tailleBarre);
        HBbarreBoutons.setMaxHeight((double) taille);
        HBDeplacements = new HBox();
        HBZoom = new HBox();
        HBOutils = new HBox();

        HBbarreBoutons.getChildren().addAll(HBDeplacements, HBZoom, HBOutils);
        if (toggleBarreDeplacements.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBDeplacements);
        }
        if (toggleBarreZoom.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBZoom);
        }
        if (toggleBarreOutils.equals("non")) {
            HBbarreBoutons.getChildren().remove(HBOutils);
        }
        APVisualisation.getChildren().addAll(HBbarreBoutons, IVHotSpot);
//        ImgHaut = new Image(repertBoutons + File.separator + "haut.png");
//        ImgBas = new Image(repertBoutons + File.separator + "bas.png");
//        ImgGauche = new Image(repertBoutons + File.separator + "gauche.png");
//        ImgDroite = new Image(repertBoutons + File.separator + "droite.png");
//        ImgZoomPlus = new Image(repertBoutons + File.separator + "zoomin.png");
//        ImgZoomMoins = new Image(repertBoutons + File.separator + "zoomout.png");
//        ImgInfo = new Image(repertBoutons + File.separator + "info.png");
//        ImgAide = new Image(repertBoutons + File.separator + "aide.png");
//        ImgModeSouris = new Image(repertBoutons + File.separator + "souris.png");
//        ImgPleinEcran = new Image(repertBoutons + File.separator + "fs.png");
//        ImgAutoRotation = new Image(repertBoutons + File.separator + "rotation.png");
//        IVHaut = new ImageView(ImgHaut);
        IVHaut.setFitWidth(taille);
        IVHaut.setFitHeight(taille);
//        IVBas = new ImageView(ImgBas);
        IVBas.setFitWidth(taille);
        IVBas.setFitHeight(taille);
//        IVGauche = new ImageView(ImgGauche);
        IVGauche.setFitWidth(taille);
        IVGauche.setFitHeight(taille);
//        IVDroite = new ImageView(ImgDroite);
        IVDroite.setFitWidth(taille);
        IVDroite.setFitHeight(taille);
//        IVZoomPlus = new ImageView(ImgZoomPlus);
        IVZoomPlus.setFitWidth(taille);
        IVZoomPlus.setFitHeight(taille);
//        IVZoomMoins = new ImageView(ImgZoomMoins);
        IVZoomMoins.setFitWidth(taille);
        IVZoomMoins.setFitHeight(taille);
//        IVInfo = new ImageView(ImgInfo);
        IVInfo.setFitWidth(taille);
        IVInfo.setFitHeight(taille);
//        IVPleinEcran = new ImageView(ImgPleinEcran);
        IVPleinEcran.setFitWidth(taille);
        IVPleinEcran.setFitHeight(taille);
//        IVModeSouris = new ImageView(ImgModeSouris);
        IVModeSouris.setFitWidth(taille);
        IVModeSouris.setFitHeight(taille);
//        IVAide = new ImageView(ImgAide);
        IVAide.setFitWidth(taille);
        IVAide.setFitHeight(taille);
//        IVAutoRotation = new ImageView(ImgAutoRotation);
        IVAutoRotation.setFitWidth(taille);
        IVAutoRotation.setFitHeight(taille);
        String positVert = position.split(":")[0];
        String positHor = position.split(":")[1];
        HBDeplacements.getChildren().addAll(IVGauche, IVHaut, IVBas, IVDroite);
        HBZoom.getChildren().addAll(IVZoomPlus, IVZoomMoins);
        HBOutils.getChildren().addAll(IVPleinEcran, IVModeSouris, IVAutoRotation, IVInfo, IVAide);
        if (toggleBoutonFS.equals("non")) {
            HBOutils.getChildren().remove(IVPleinEcran);
        }
        if (toggleBoutonSouris.equals("non")) {
            HBOutils.getChildren().remove(IVModeSouris);
        }
        if (toggleBoutonRotation.equals("non")) {
            HBOutils.getChildren().remove(IVAutoRotation);
        }

        double LX = 0;
        double LY = 0;
        switch (positVert) {
            case "top":
                LY = IMVisualisation.getLayoutY() + dY;
                break;
            case "bottom":
                LY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight() - dY;
                break;
            case "middle":
                LY = IMVisualisation.getLayoutY() + (IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight()) / 2.d - dY;
                break;
        }

        switch (positHor) {
            case "right":
                LX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth() - dX;
                break;
            case "left":
                LX = IMVisualisation.getLayoutX() + dX;
                break;
            case "center":
                LX = IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth()) / 2 + dX;
                break;
        }
        HBbarreBoutons.setLayoutX(LX);
        HBbarreBoutons.setLayoutY(LY);
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> listerStyle(String repertoire) {
        ArrayList<String> liste = new ArrayList<String>();
        File[] Repertoires = new File(repertoire).listFiles();
        for (File repert : Repertoires) {
            if (repert.isDirectory()) {
                String nomRepert = repert.getName();
                if (!nomRepert.equals("hotspots")) {
                    liste.add(nomRepert);
                }
            }
        }
        return liste;
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> listerHotSpots(String repertoire) {
        ArrayList<String> liste = new ArrayList<>();
        File[] Repertoires = new File(repertoire).listFiles(IMAGE_FILTER);
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomRepert = repert.getName();
                liste.add(nomRepert);
            }
        }
        return liste;
    }

    private ArrayList<String> listerBoussoles(String repertoire) {
        ArrayList<String> liste = new ArrayList<>();
        File[] Repertoires = new File(repertoire).listFiles(PNG_FILTER);
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomRepert = repert.getName();
                if (nomRepert.contains("rose")) {
                    liste.add(nomRepert);
                }
            }
        }
        return liste;
    }

    private ArrayList<String> listerMasques(String repertoire) {
        ArrayList<String> liste = new ArrayList<>();
        File[] Repertoires = new File(repertoire).listFiles(PNG_FILTER);
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomRepert = repert.getName();
                liste.add(nomRepert);
            }
        }
        return liste;
    }

    /**
     *
     * @return
     */
    public String getTemplate() {
        String contenuFichier = "";
        contenuFichier
                += "styleBarre=" + styleBarre + "\n"
                + "couleurTheme=" + couleurTheme.toString().substring(2, 8) + "\n"
                + "couleurBoutons=" + couleurBoutons.toString().substring(2, 8) + "\n"
                + "couleurHotspots=" + couleurHotspots.toString().substring(2, 8) + "\n"
                + "couleurMasque=" + couleurMasque.toString().substring(2, 8) + "\n"
                + "styleHotspots=" + styleHotSpots + "\n"
                + "position=" + positionBarre + "\n"
                + "dX=" + Math.round(dXBarre) + "\n"
                + "dY=" + Math.round(dYBarre) + "\n"
                + "visible=" + toggleBarreVisibilite + "\n"
                + "deplacement=" + toggleBarreDeplacements + "\n"
                + "zoom=" + toggleBarreZoom + "\n"
                + "outils=" + toggleBarreOutils + "\n"
                + "rotation=" + toggleBoutonRotation + "\n"
                + "FS=" + toggleBoutonFS + "\n"
                + "souris=" + toggleBoutonSouris + "\n"
                + "titrePolice=" + titrePoliceNom + "\n"
                + "titrePoliceTaille=" + titrePoliceTaille + "\n"
                + "titreOpacite=" + Math.round(titreOpacite * 100.d) / 100.d + "\n"
                + "titreTaille=" + titreTaille + "\n"
                + "titreCouleur=" + couleurTitre + "\n"
                + "titreFondCouleur=" + couleurFondTitre + "\n"
                + "afficheBoussole=" + bAfficheBoussole + "\n"
                + "imageBoussole=" + imageBoussole + "\n"
                + "tailleBoussole=" + Math.round(tailleBoussole * 10.d) / 10.d + "\n"
                + "positionBoussole=" + positionBoussole + "\n"
                + "dXBoussole=" + Math.round(dXBoussole) + "\n"
                + "dYBoussole=" + Math.round(dYBoussole) + "\n"
                + "opaciteBoussole=" + Math.round(opaciteBoussole * 100.d) / 100.d + "\n"
                + "aiguilleMobile=" + bAiguilleMobileBoussole + "\n"
                + "afficheMasque=" + bAfficheMasque + "\n"
                + "imageMasque=" + imageMasque + "\n"
                + "tailleMasque=" + Math.round(tailleMasque * 10.d) / 10.d + "\n"
                + "positionMasque=" + positionMasque + "\n"
                + "dXMasque=" + Math.round(dXMasque) + "\n"
                + "dYMasque=" + Math.round(dYMasque) + "\n"
                + "opaciteMasque=" + Math.round(opaciteMasque * 100.d) / 100.d + "\n"
                + "masqueNavigation=" + bMasqueNavigation + "\n"
                + "masqueBoussole=" + bMasqueBoussole + "\n"
                + "masqueTitre=" + bMasqueTitre + "\n"
                + "masquePlan=" + bMasquePlan + "\n"
                + "masqueReseaux=" + bMasqueReseaux + "\n"
                + "masqueVignettes=" + bMasqueVignettes + "\n"
                + "afficheReseauxSociaux=" + bAfficheReseauxSociaux + "\n"
                + "tailleReseauxSociaux=" + Math.round(tailleReseauxSociaux * 10.d) / 10.d + "\n"
                + "positionReseauxSociaux=" + positionReseauxSociaux + "\n"
                + "dXReseauxSociaux=" + Math.round(dXReseauxSociaux) + "\n"
                + "dYReseauxSociaux=" + Math.round(dYReseauxSociaux) + "\n"
                + "opaciteReseauxSociaux=" + Math.round(opaciteReseauxSociaux * 100.d) / 100.d + "\n"
                + "masqueTwitter=" + bReseauxSociauxTwitter + "\n"
                + "masqueGoogle=" + bReseauxSociauxGoogle + "\n"
                + "masqueFacebook=" + bReseauxSociauxFacebook + "\n"
                + "masqueEmail=" + bReseauxSociauxEmail + "\n"
                + "afficheVignettes=" + bAfficheVignettes + "\n"
                + "positionVignettes=" + positionVignettes + "\n"
                + "opaciteVignettes=" + Math.round(opaciteVignettes * 100.d) / 100.d + "\n"
                + "tailleImageVignettes=" + Math.round(tailleImageVignettes) + "\n"
                + "couleurFondVignettes=" + couleurFondVignettes + "\n";
        return contenuFichier;
    }

    /**
     *
     * @param templ
     */
    public void setTemplate(List<String> templ) {
        bAfficheBoussole = false;
        bAfficheMasque = false;
        bAfficheVignettes = false;
        bAfficheReseauxSociaux = false;
        APVisualisation.getChildren().clear();
        APVisualisation.getChildren().addAll(RBClair, RBSombre, IMVisualisation, txtTitre, imgBoussole, imgAiguille, imgTwitter, imgGoogle, imgFacebook, imgEmail, APVisuVignettes);

        for (String chaine : templ) {
            String variable = chaine.split("=")[0];
            String valeur = chaine.split("=")[1];
            switch (variable) {
                case "couleurTheme":
                    couleurTheme = Color.web(valeur);
                    break;
                case "couleurBoutons":
                    couleurBoutons = Color.web(valeur);
                    break;
                case "couleurHotspots":
                    couleurHotspots = Color.web(valeur);
                    break;
                case "couleurMasque":
                    couleurMasque = Color.web(valeur);
                    break;

                case "styleBarre":
                    styleBarre = valeur;
                    CBlisteStyle.setValue(valeur);
                    break;
                case "styleHotspots":
                    styleHotSpots = valeur;
                    break;
                case "position":
                    positionBarre = valeur;
                    switch (valeur) {
                        case "top:left":
                            RBTopLeft.setSelected(true);
                            break;
                        case "top:center":
                            RBTopCenter.setSelected(true);
                            break;
                        case "top:right":
                            RBTopRight.setSelected(true);
                            break;
                        case "middle:left":
                            RBMiddleLeft.setSelected(true);
                            break;
                        case "middle:center":
                            RBMiddleCenter.setSelected(true);
                            break;
                        case "middle:right":
                            RBMiddleRight.setSelected(true);
                            break;
                        case "bottom:left":
                            RBBottomLeft.setSelected(true);
                            break;
                        case "bottom:center":
                            RBBottomCenter.setSelected(true);
                            break;
                        case "bottom:right":
                            RBBottomRight.setSelected(true);
                            break;
                    }

                    break;
                case "dX":
                    dXBarre = Double.parseDouble(valeur);
                    dXSpinner.setNumber(new BigDecimal(dXBarre));
                    break;
                case "dY":
                    dYBarre = Double.parseDouble(valeur);
                    dYSpinner.setNumber(new BigDecimal(dYBarre));
                    break;
                case "visible":
                    toggleBarreVisibilite = valeur;
                    if (valeur.equals("oui")) {
                        CBVisible.setSelected(true);
                    } else {
                        CBVisible.setSelected(false);
                    }
                    break;
                case "deplacement":
                    toggleBarreDeplacements = valeur;
                    if (valeur.equals("oui")) {
                        CBDeplacements.setSelected(true);
                    } else {
                        CBDeplacements.setSelected(false);
                    }
                    break;
                case "zoom":
                    toggleBarreZoom = valeur;
                    if (valeur.equals("oui")) {
                        CBZoom.setSelected(true);
                    } else {
                        CBZoom.setSelected(false);
                    }
                    break;
                case "outils":
                    toggleBarreOutils = valeur;
                    if (valeur.equals("oui")) {
                        CBOutils.setSelected(true);
                    } else {
                        CBOutils.setSelected(false);
                    }
                    break;
                case "rotation":
                    toggleBoutonRotation = valeur;
                    if (valeur.equals("oui")) {
                        CBRotation.setSelected(true);
                    } else {
                        CBRotation.setSelected(false);
                    }
                    break;
                case "FS":
                    toggleBoutonFS = valeur;
                    if (valeur.equals("oui")) {
                        CBFS.setSelected(true);
                    } else {
                        CBFS.setSelected(false);
                    }
                    break;
                case "souris":
                    toggleBoutonSouris = valeur;
                    if (valeur.equals("oui")) {
                        CBSouris.setSelected(true);
                    } else {
                        CBSouris.setSelected(false);
                    }
                    break;
                case "titrePolice":
                    titrePoliceNom = valeur;
                    break;
                case "titrePoliceTaille":
                    titrePoliceTaille = valeur;
                    break;
                case "titreOpacite":
                    titreOpacite = Double.parseDouble(valeur);
                    break;
                case "titreTaille":
                    titreTaille = Double.parseDouble(valeur);
                    break;
                case "titreCouleur":
                    couleurTitre = valeur;
                    break;
                case "titreFondCouleur":
                    couleurFondTitre = valeur;
                    break;
                case "afficheBoussole":
                    bAfficheBoussole = (valeur.equals("true"));
                    break;
                case "imageBoussole":
                    imageBoussole = valeur;
                    break;
                case "tailleBoussole":
                    tailleBoussole = Double.parseDouble(valeur);
                    break;
                case "positionBoussole":
                    positionBoussole = valeur;
                    break;
                case "dXBoussole":
                    dXBoussole = Double.parseDouble(valeur);
                    break;
                case "dYBoussole":
                    dYBoussole = Double.parseDouble(valeur);
                    break;
                case "opaciteBoussole":
                    opaciteBoussole = Double.parseDouble(valeur);
                    break;
                case "aiguilleMobile":
                    bAiguilleMobileBoussole = (valeur.equals("true"));
                    break;
                case "afficheMasque":
                    bAfficheMasque = (valeur.equals("true"));
                    break;
                case "imageMasque":
                    imageMasque = valeur;
                    break;
                case "tailleMasque":
                    tailleMasque = Double.parseDouble(valeur);
                    break;
                case "positionMasque":
                    positionMasque = valeur;
                    break;
                case "dXMasque":
                    dXMasque = Double.parseDouble(valeur);
                    break;
                case "dYMasque":
                    dYMasque = Double.parseDouble(valeur);
                    break;
                case "opaciteMasque":
                    opaciteMasque = Double.parseDouble(valeur);
                    break;
                case "masqueNavigation":
                    bMasqueNavigation = (valeur.equals("true"));
                    break;
                case "masqueBoussole":
                    bMasqueBoussole = (valeur.equals("true"));
                    break;
                case "masqueTitre":
                    bMasqueTitre = (valeur.equals("true"));
                    break;
                case "masquePlan":
                    bMasquePlan = (valeur.equals("true"));
                    break;
                case "masqueReseaux":
                    bMasqueReseaux = (valeur.equals("true"));
                    break;
                case "masqueVignettes":
                    bMasqueVignettes = (valeur.equals("true"));
                    break;
                case "afficheReseauxSociaux":
                    bAfficheReseauxSociaux = (valeur.equals("true"));
                    break;
                case "tailleReseauxSociaux":
                    tailleReseauxSociaux = Double.parseDouble(valeur);
                    break;
                case "positionReseauxSociaux":
                    positionReseauxSociaux = valeur;
                    break;
                case "dXReseauxSociaux":
                    dXReseauxSociaux = Double.parseDouble(valeur);
                    break;
                case "dYReseauxSociaux":
                    dYReseauxSociaux = Double.parseDouble(valeur);
                    break;
                case "opaciteReseauxSociaux":
                    opaciteReseauxSociaux = Double.parseDouble(valeur);
                    break;
                case "masqueTwitter":
                    bReseauxSociauxTwitter = (valeur.equals("true"));
                    break;
                case "masqueGoogle":
                    bReseauxSociauxGoogle = (valeur.equals("true"));
                    break;
                case "masqueFacebook":
                    bReseauxSociauxFacebook = (valeur.equals("true"));
                    break;
                case "masqueEmail":
                    bReseauxSociauxEmail = (valeur.equals("true"));
                    break;
                case "afficheVignettes":
                    bAfficheVignettes = (valeur.equals("true"));
                    break;
                case "positionVignettes":
                    positionVignettes = valeur;
                    break;
                case "opaciteVignettes":
                    opaciteVignettes = Double.parseDouble(valeur);
                    break;
                case "tailleImageVignettes":
                    tailleImageVignettes = Double.parseDouble(valeur);
                    break;
                case "couleurFondVignettes":
                    couleurFondVignettes = valeur;
                    break;

            }
        }

        txtTitre.setTextFill(Color.valueOf(couleurTitre));
        txtTitre.setStyle("-fx-background-color : " + couleurFondTitre);
        txtTitre.setOpacity(titreOpacite);
        double taille = (double) titreTaille / 100.d * IMVisualisation.getFitWidth();
        txtTitre.setMinWidth(taille);
        txtTitre.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
        Font fonte1 = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
        txtTitre.setFont(fonte1);
        txtTitre.setPrefHeight(-1);
        CPCouleurTitre.setValue(Color.valueOf(couleurTitre));
        CPCouleurFondTitre.setValue(Color.valueOf(couleurFondTitre));
        CPCouleurBoutons.setValue(couleurBoutons);
        CPCouleurTheme.setValue(couleurTheme);
        CPCouleurMasques.setValue(couleurMasque);
        CPCouleurHotspots.setValue(couleurHotspots);
        CBListePolices.setValue(titrePoliceNom);
        SLOpacite.setValue(titreOpacite);
        SLTaillePolice.setValue(Double.parseDouble(titrePoliceTaille));
        SLTaille.setValue(titreTaille);
        APVisualisation.getChildren().remove(HBbarreBoutons);
        APVisualisation.getChildren().remove(IVHotSpot);
        SLTailleBoussole.setValue(tailleBoussole);
        SLOpaciteBoussole.setValue(opaciteBoussole);
        boussDXSpinner.setNumber(new BigDecimal(dXBoussole));
        boussDYSpinner.setNumber(new BigDecimal(dYBoussole));
        CBAiguilleMobile.setSelected(bAiguilleMobileBoussole);
        CBAfficheBoussole.setSelected(bAfficheBoussole);
        RBBoussTopLeft.setSelected(positionBoussole.equals("top:left"));
        RBBoussBottomLeft.setSelected(positionBoussole.equals("bottom:left"));
        RBBoussTopRight.setSelected(positionBoussole.equals("top:right"));
        RBBoussBottomRight.setSelected(positionBoussole.equals("bottom:right"));
        SLTailleMasque.setValue(tailleMasque);
        SLOpaciteMasque.setValue(opaciteMasque);
        masqueDXSpinner.setNumber(new BigDecimal(dXMasque));
        masqueDYSpinner.setNumber(new BigDecimal(dYMasque));
        CBMasqueNavigation.setSelected(bMasqueNavigation);
        CBMasqueBoussole.setSelected(bMasqueBoussole);
        CBMasqueTitre.setSelected(bMasqueTitre);
        CBMasquePlan.setSelected(bMasquePlan);
        CBMasqueReseaux.setSelected(bMasqueReseaux);
        CBMasqueVignettes.setSelected(bMasqueVignettes);
        CBAfficheMasque.setSelected(bAfficheMasque);
        RBMasqueTopLeft.setSelected(positionMasque.equals("top:left"));
        RBMasqueBottomLeft.setSelected(positionMasque.equals("bottom:left"));
        RBMasqueTopRight.setSelected(positionMasque.equals("top:right"));
        RBMasqueBottomRight.setSelected(positionMasque.equals("bottom:right"));
        SLTailleReseauxSociaux.setValue(tailleReseauxSociaux);
        SLOpaciteReseauxSociaux.setValue(opaciteReseauxSociaux);
        reseauxSociauxDXSpinner.setNumber(new BigDecimal(dXReseauxSociaux));
        reseauxSociauxDYSpinner.setNumber(new BigDecimal(dYReseauxSociaux));
        CBReseauxSociauxTwitter.setSelected(bReseauxSociauxTwitter);
        CBReseauxSociauxGoogle.setSelected(bReseauxSociauxGoogle);
        CBReseauxSociauxFacebook.setSelected(bReseauxSociauxFacebook);
        CBReseauxSociauxEmail.setSelected(bReseauxSociauxEmail);
        CBAfficheReseauxSociaux.setSelected(bAfficheReseauxSociaux);
        RBReseauxSociauxTopLeft.setSelected(positionReseauxSociaux.equals("top:left"));
        RBReseauxSociauxBottomLeft.setSelected(positionReseauxSociaux.equals("bottom:left"));
        RBReseauxSociauxTopRight.setSelected(positionReseauxSociaux.equals("top:right"));
        RBReseauxSociauxBottomRight.setSelected(positionReseauxSociaux.equals("bottom:right"));
        CBAfficheVignettes.setSelected(bAfficheVignettes);
        SLOpaciteVignettes.setValue(opaciteVignettes);
        SLTailleVignettes.setValue(tailleImageVignettes);
        RBVignettesLeft.setSelected(positionReseauxSociaux.equals("left"));
        RBVignettesRight.setSelected(positionReseauxSociaux.equals("right"));
        RBVignettesBottom.setSelected(positionReseauxSociaux.equals("bottom"));
        CPCouleurFondVignettes.setValue(Color.valueOf(couleurFondVignettes));
        //chargeBarre(styleBarre, styleHotSpots, imageMasque);
        afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        afficheVignettes();
        changeCouleurBarre(couleurBoutons.getHue(), couleurBoutons.getSaturation(), couleurBoutons.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
    }

    /**
     *
     * @param width
     * @param height
     */
    public void creeInterface(int width, int height) {
        List<String> lstPolices = new ArrayList<String>();
        lstPolices.add("Arial");
        lstPolices.add("Arial Black");
        lstPolices.add("Comic Sans MS");
        lstPolices.add("Couurier New");
        lstPolices.add("Lucida Sans Typewriter");
        lstPolices.add("Segoe Print");
        lstPolices.add("Tahoma");
        lstPolices.add("Times New Roman");
        lstPolices.add("Verdana");
        ObservableList<String> listePolices = FXCollections.observableList(lstPolices);
//        System.out.println(EditeurPanovisu.locale);
        rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        repertBoutonsPrincipal = repertAppli + File.separator + "theme/barreNavigation";
        repertHotSpots = repertAppli + File.separator + "theme/hotspots";
        repertBoussoles = repertAppli + File.separator + "theme/boussoles";
        repertMasques = repertAppli + File.separator + "theme/MA";
        repertReseauxSociaux = repertAppli + File.separator + "theme/reseaux";
        chargeBarre(styleBarre, styleHotSpots, imageMasque);
        ArrayList<String> listeStyles = listerStyle(repertBoutonsPrincipal);
        ArrayList<String> listeHotSpots = listerHotSpots(repertHotSpots);
        ArrayList<String> listeBoussoles = listerBoussoles(repertBoussoles);
        ArrayList<String> listeMasques = listerMasques(repertMasques);
//        listeBoussoles.stream().forEach((bous) -> {
//            System.out.println(bous);
//        });
        int nombreHotSpots = listeHotSpots.size();
        ImageView[] IVHotspots = new ImageView[nombreHotSpots];
        imageClaire = new Image("file:" + repertAppli + File.separator + "images/claire.jpg");
        imageSombre = new Image("file:" + repertAppli + File.separator + "images/sombre.jpg");
        imgSuivant = new ImageView(new Image("file:" + repertAppli + File.separator + "panovisu/images/suivant.png"));
        imgPrecedent = new ImageView(new Image("file:" + repertAppli + File.separator + "panovisu/images/precedent.png"));
        fondSuivant = new VBox(imgSuivant);
        fondPrecedent = new VBox(imgPrecedent);
        fondSuivant.setStyle("-fx-background-color : black;");
        fondSuivant.setOpacity(0.5);
        fondPrecedent.setStyle("-fx-background-color : black;");
        fondPrecedent.setOpacity(0.5);

        txtTitre = new Label(rb.getString("interface.titre"));
        Font fonte = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
        txtTitre.setFont(fonte);
        double largeurOutils = 380;

        tabInterface = new Pane();
        HBInterface = new HBox();
        HBInterface.setPrefWidth(width);
        HBInterface.setPrefHeight(height);
        tabInterface.getChildren().add(HBInterface);
        APVisualisation = new AnchorPane();
        APVisualisation.setPrefWidth(width - largeurOutils);
        APVisualisation.setPrefHeight(height);
        VBOutils = new VBox();
        ScrollPane SPOutils = new ScrollPane(VBOutils);
        SPOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        SPOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPOutils.setMaxHeight(height - 100);
        SPOutils.setFitToWidth(true);
        SPOutils.setFitToHeight(true);
        SPOutils.setPrefWidth(largeurOutils);
        SPOutils.setMaxWidth(largeurOutils);
        SPOutils.setTranslateY(15);
        VBOutils.setPrefWidth(largeurOutils - 20);
        VBOutils.setMaxWidth(largeurOutils - 20);
        //VBOutils.setMinHeight(height);
        VBOutils.setStyle("-fx-background-color : #ccc;");
        HBInterface.getChildren().addAll(APVisualisation, SPOutils);
        /*
         * ***************************************************************
         *     Panneau de visualisation de l'interface
         * ***************************************************************
         */
        double tailleMax = APVisualisation.getPrefWidth();
        if (tailleMax > 1200) {
            tailleMax = 1200;
        }
        IMVisualisation = new ImageView(imageClaire);
        IMVisualisation.setFitWidth(tailleMax);
        IMVisualisation.setFitHeight(tailleMax * 2.d / 3.d);
        IMVisualisation.setSmooth(true);
        double LX = (APVisualisation.getPrefWidth() - IMVisualisation.getFitWidth()) / 2;
        IMVisualisation.setLayoutX(LX);
        IMVisualisation.setLayoutY(20);
        txtTitre.setMinSize(500, 30);
        txtTitre.setPadding(new Insets(5));
        txtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        txtTitre.setAlignment(Pos.CENTER);
        txtTitre.setOpacity(titreOpacite);
        txtTitre.setTextFill(Color.WHITE);
        txtTitre.setLayoutY(20);
        txtTitre.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
        RBClair = new RadioButton("Image claire");
        RBSombre = new RadioButton("Image Sombre");
        double positRB = IMVisualisation.getFitHeight() + 30;
        RBClair.setToggleGroup(grpImage);
        RBSombre.setToggleGroup(grpImage);
        APVisualisation.getChildren().addAll(RBClair, RBSombre);
        RBClair.setLayoutX(LX + 40);
        RBClair.setLayoutY(positRB);
        RBClair.setSelected(true);
        RBSombre.setLayoutX(LX + 180);
        RBSombre.setLayoutY(positRB);
        RBClair.setUserData("claire");
        RBSombre.setUserData("sombre");
        imgBoussole = new ImageView(new Image("file:" + repertBoussoles + File.separator + imageBoussole));
        imgAiguille = new ImageView("file:" + repertBoussoles + File.separator + "aiguille.png");
        APVisualisation.getChildren().add(IMVisualisation);
        imgTwitter = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxTwitter));
        imgGoogle = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxGoogle));
        imgFacebook = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxFacebook));
        imgEmail = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxEmail));
        APVisuVignettes = new AnchorPane();
        APVisualisation.getChildren().addAll(txtTitre, imgBoussole, imgAiguille, IVMasque, imgTwitter, imgGoogle, imgFacebook, imgEmail, APVisuVignettes, fondSuivant, fondPrecedent);
        fondPrecedent.setPrefWidth(64);
        fondPrecedent.setPrefHeight(64);
        fondSuivant.setPrefWidth(64);
        fondSuivant.setPrefHeight(64);
        fondPrecedent.setMaxWidth(64);
        fondPrecedent.setMaxHeight(64);
        fondSuivant.setMaxWidth(64);
        fondSuivant.setMaxHeight(64);
        fondPrecedent.setLayoutX(IMVisualisation.getLayoutX());
        fondPrecedent.setLayoutY(IMVisualisation.getLayoutY() + (IMVisualisation.getFitHeight() - fondPrecedent.getPrefHeight()) / 2);
        fondSuivant.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()));
        fondSuivant.setLayoutY(IMVisualisation.getLayoutY() + (IMVisualisation.getFitHeight() - fondSuivant.getPrefHeight()) / 2);
        fondSuivant.setVisible(bSuivantPrecedent);
        fondPrecedent.setVisible(bSuivantPrecedent);

        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();

        afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);

        /*
         * *****************************************
         *     Pannels d'outils 
         * *****************************************
         */
        AnchorPane APCoulTheme = new AnchorPane();
        CPCouleurTheme = new ColorPicker(couleurTheme);
        String coul2 = CPCouleurTheme.getValue().toString().substring(2, 8);
        changeCouleurTitre(coul2);
        changeCouleurVignettes(coul2);
        Label lblCouleurTheme = new Label(rb.getString("interface.couleurTheme"));
        lblCouleurTheme.setLayoutX(20);
        lblCouleurTheme.setLayoutY(20);
        CPCouleurTheme.setLayoutX(150);
        CPCouleurTheme.setLayoutY(20);
        APCoulTheme.setPrefHeight(40);
        APCoulTheme.setMinHeight(40);
        APCoulTheme.getChildren().addAll(lblCouleurTheme, CPCouleurTheme);
        CPCouleurTheme.setOnAction((ActionEvent e) -> {
            couleurTheme = CPCouleurTheme.getValue();
            String coul1 = CPCouleurTheme.getValue().toString().substring(2, 8);
            couleurHotspots = couleurTheme;
            couleurBoutons = couleurTheme;
            couleurMasque = couleurTheme;
            changeCouleurTitre(coul1);
            changeCouleurVignettes(coul1);
            //changeCouleur(hue);
            CPCouleurFondTitre.setValue(couleurTheme);
            CPCouleurHotspots.setValue(couleurTheme);
            CPCouleurBoutons.setValue(couleurTheme);
            CPCouleurMasques.setValue(couleurTheme);
            CPCouleurFondVignettes.setValue(Color.hsb(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness()));
            changeCouleurBarre(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurHS(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurMasque(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());

        });

        AnchorPane APBB = new AnchorPane();
        AnchorPane APHS = new AnchorPane();
        AnchorPane APTIT = new AnchorPane();
        AnchorPane APBOUSS = new AnchorPane();
        AnchorPane APMASQ = new AnchorPane();
        AnchorPane APRS = new AnchorPane();
        AnchorPane APVIG = new AnchorPane();

        /*
         * *****************************************
         *     Panel Titre 
         * ****************************************
         */
        AnchorPane APTitre = new AnchorPane();
        APTitre.setLayoutY(40);
        APTitre.setLayoutX(10);
        APTitre.setPrefHeight(200);
        Label lblPanelTitre = new Label(rb.getString("interface.styleTitre"));
        lblPanelTitre.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelTitre.setStyle("-fx-background-color : #444");
        lblPanelTitre.setTextFill(Color.WHITE);
        lblPanelTitre.setPadding(new Insets(5));
        lblPanelTitre.setLayoutX(10);
        lblPanelTitre.setLayoutY(10);
        ImageView IVBtnPlusTitre = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusTitre.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusTitre.setLayoutY(13);
        CBListePolices = new ComboBox(listePolices);
        CBListePolices.setValue(titrePoliceNom);
        CBListePolices.setLayoutX(180);
        CBListePolices.setLayoutY(12);
        CBListePolices.setMaxWidth(135);
        CBListePolices.valueProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        if (new_val != null) {
                            titrePoliceNom = new_val;
                            Font fonte1 = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
                            txtTitre.setFont(fonte1);
                            txtTitre.setPrefHeight(-1);
                            afficheVignettes();
                        }
                    }
                });

        Label lblChoixPoliceTitre = new Label(rb.getString("interface.choixPolice"));
        lblChoixPoliceTitre.setLayoutX(10);
        lblChoixPoliceTitre.setLayoutY(15);
        Label lblChoixTailleTitre = new Label(rb.getString("interface.choixTaillePolice"));
        lblChoixTailleTitre.setLayoutX(10);
        lblChoixTailleTitre.setLayoutY(45);
        SLTaillePolice = new Slider(8, 72, 12);
        SLTaillePolice.setLayoutX(180);
        SLTaillePolice.setLayoutY(45);
        SLTaillePolice.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                titrePoliceTaille = Integer.toString((int) Math.round(taille));
                Font fonte1 = Font.font(titrePoliceNom, Double.parseDouble(titrePoliceTaille));
                txtTitre.setFont(fonte1);
                txtTitre.setPrefHeight(-1);
                afficheVignettes();
            }
        });

        Label lblChoixCouleurTitre = new Label(rb.getString("interface.choixCouleur"));
        lblChoixCouleurTitre.setLayoutX(10);
        lblChoixCouleurTitre.setLayoutY(75);
        CPCouleurTitre = new ColorPicker(Color.valueOf(couleurTitre));
        CPCouleurTitre.setLayoutX(180);
        CPCouleurTitre.setLayoutY(73);
        Label lblChoixCouleurFondTitre = new Label(rb.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondTitre.setLayoutX(10);
        lblChoixCouleurFondTitre.setLayoutY(105);
        CPCouleurFondTitre = new ColorPicker(Color.valueOf(couleurFondTitre));
        CPCouleurFondTitre.setLayoutX(180);
        CPCouleurFondTitre.setLayoutY(103);
        CPCouleurTitre.setOnAction((ActionEvent e) -> {
            String coul = CPCouleurTitre.getValue().toString().substring(2, 8);
            couleurTitre = "#" + coul;
            txtTitre.setTextFill(Color.valueOf(couleurTitre));
        });
        CPCouleurFondTitre.setOnAction((ActionEvent e) -> {
            String coul = CPCouleurFondTitre.getValue().toString().substring(2, 8);
            couleurFondTitre = "#" + coul;
            txtTitre.setStyle("-fx-background-color : " + couleurFondTitre);
        });
        Label lblChoixOpacite = new Label(rb.getString("interface.choixOpaciteTitre"));
        lblChoixOpacite.setLayoutX(10);
        lblChoixOpacite.setLayoutY(135);
        SLOpacite = new Slider(0, 1, titreOpacite);
        SLOpacite.setLayoutX(180);
        SLOpacite.setLayoutY(135);
        SLOpacite.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                titreOpacite = (double) newValue;
//                System.out.println("valeur " + titreOpacite);
                txtTitre.setOpacity(titreOpacite);
            }
        });

        Label lblChoixTaille = new Label(rb.getString("interface.choixTailleTitre"));
        lblChoixTaille.setLayoutX(10);
        lblChoixTaille.setLayoutY(165);
        SLTaille = new Slider(0, 100, titreTaille);
        SLTaille.setLayoutX(180);
        SLTaille.setLayoutY(165);
        SLTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                titreTaille = (double) newValue;
                double taille = (double) titreTaille / 100.d * IMVisualisation.getFitWidth();
                txtTitre.setMinWidth(taille);
                txtTitre.setLayoutX(IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
            }
        });

        APTitre.getChildren().addAll(lblChoixPoliceTitre, CBListePolices,
                lblChoixTailleTitre, SLTaillePolice,
                lblChoixCouleurTitre, CPCouleurTitre,
                lblChoixCouleurFondTitre, CPCouleurFondTitre,
                lblChoixOpacite, SLOpacite,
                lblChoixTaille, SLTaille);
        double tailleInitialeTitre = APTitre.getPrefHeight();
        APTitre.setPrefHeight(0);
        APTitre.setMaxHeight(0);
        APTitre.setMinHeight(0);
        APTitre.setVisible(false);

        lblPanelTitre.setOnMouseClicked((MouseEvent me) -> {
            if (APTitre.isVisible()) {
                APTitre.setPrefHeight(0);
                APTitre.setMaxHeight(0);
                APTitre.setMinHeight(0);
                APTitre.setVisible(false);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APTitre.setPrefHeight(tailleInitialeTitre);
                APTitre.setMaxHeight(tailleInitialeTitre);
                APTitre.setMinHeight(tailleInitialeTitre);
                APTitre.setVisible(true);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        IVBtnPlusTitre.setOnMouseClicked((MouseEvent me) -> {
            if (APTitre.isVisible()) {
                APTitre.setPrefHeight(0);
                APTitre.setMaxHeight(0);
                APTitre.setMinHeight(0);
                APTitre.setVisible(false);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APTitre.setPrefHeight(tailleInitialeTitre);
                APTitre.setMaxHeight(tailleInitialeTitre);
                APTitre.setMinHeight(tailleInitialeTitre);
                APTitre.setVisible(true);
                IVBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        /*
         * *************************************************
         *     Panel HotSpots 
         * *************************************************
         */
        AnchorPane APHotSpots = new AnchorPane();
        ImageView IVBtnPlusHS = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusHS.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusHS.setLayoutY(13);
        Label lblChoixHS = new Label(rb.getString("interface.choixHotspot"));
        lblChoixHS.setPrefWidth(VBOutils.getPrefWidth());
        lblChoixHS.setStyle("-fx-background-color : #444");
        lblChoixHS.setTextFill(Color.WHITE);
        lblChoixHS.setPadding(new Insets(5));
        lblChoixHS.setLayoutX(10);
        lblChoixHS.setLayoutY(10);

        double tailleHS = 35.d * (int) (nombreHotSpots / 6 + 1) + 40;
        APHotSpots.setPrefHeight(tailleHS);
        APHotSpots.setLayoutX(50);
        APHotSpots.setLayoutY(40);
        APHotSpots.setStyle("-fx-background-color : #fff");
        APHotSpots.setPadding(new Insets(5));
        int i = 0;
        double xHS;
        double yHS;
        for (String nomImage : listeHotSpots) {
            IVHotspots[i] = new ImageView(new Image("file:" + repertHotSpots + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 6;
            int row = i / 6;
            xHS = col * 40 + 5;
            yHS = row * 35 + 5;
            IVHotspots[i].setLayoutX(xHS);
            IVHotspots[i].setLayoutY(yHS);
            IVHotspots[i].setStyle("-fx-background-color : #ccc");
            IVHotspots[i].setOnMouseClicked((MouseEvent me) -> {
                APVisualisation.getChildren().remove(HBbarreBoutons);
                APVisualisation.getChildren().remove(IVHotSpot);
                styleHotSpots = nomImage;
                afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);

            });
            APHotSpots.getChildren().add(IVHotspots[i]);
            i++;

        }
        CPCouleurHotspots = new ColorPicker(couleurHotspots);
        CPCouleurHotspots.setOnAction((ActionEvent e) -> {
            couleurHotspots = CPCouleurHotspots.getValue();
            changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        });
        Label lblCouleurHotspot = new Label(rb.getString("interface.couleurHS"));
        lblCouleurHotspot.setLayoutX(20);
        lblCouleurHotspot.setLayoutY(tailleHS - 20);
        CPCouleurHotspots.setLayoutX(200);
        CPCouleurHotspots.setLayoutY(tailleHS - 20);
        APHotSpots.getChildren().addAll(lblCouleurHotspot, CPCouleurHotspots);
        APHotSpots.setPrefHeight(0);
        APHotSpots.setMaxHeight(0);
        APHotSpots.setMinHeight(0);
        APHotSpots.setVisible(false);

        lblChoixHS.setOnMouseClicked((MouseEvent me) -> {
            if (APHotSpots.isVisible()) {
                IVBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(0);
                APHotSpots.setMaxHeight(0);
                APHotSpots.setMinHeight(0);
                APHotSpots.setVisible(false);
            } else {
                IVBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(tailleHS);
                APHotSpots.setMaxHeight(tailleHS);
                APHotSpots.setMinHeight(tailleHS);
                APHotSpots.setVisible(true);
            }
        });
        IVBtnPlusHS.setOnMouseClicked((MouseEvent me) -> {
            if (APHotSpots.isVisible()) {
                IVBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(0);
                APHotSpots.setMaxHeight(0);
                APHotSpots.setMinHeight(0);
                APHotSpots.setVisible(false);
            } else {
                IVBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APHotSpots.setPrefHeight(tailleHS);
                APHotSpots.setMaxHeight(tailleHS);
                APHotSpots.setMinHeight(tailleHS);
                APHotSpots.setVisible(true);
            }
        });

        /*        
         * ***************************************
         *     Panel Barre Bouton 
         * ***************************************
         */
        AnchorPane APBarreModif = new AnchorPane();
        APBarreModif.setLayoutY(40);
        APBarreModif.setPrefHeight(390);
        APBarreModif.setMinWidth(VBOutils.getPrefWidth() - 20);

        CBVisible = new CheckBox(rb.getString("interface.barreVisible"));
        CBVisible.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreVisibilite = "oui";
            } else {
                toggleBarreVisibilite = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBVisible.setLayoutX(10);
        CBVisible.setLayoutY(5);
        CBVisible.setSelected(true);
        CBSuivantPrecedent = new CheckBox(rb.getString("interface.SuivantPrecedent"));
        CBSuivantPrecedent.setLayoutX(10);
        CBSuivantPrecedent.setLayoutY(30);
        CBSuivantPrecedent.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bSuivantPrecedent = new_val;
            fondSuivant.setVisible(bSuivantPrecedent);
            fondPrecedent.setVisible(bSuivantPrecedent);
        });
        Label lblStyle = new Label(rb.getString("interface.style"));
        lblStyle.setLayoutX(10);
        lblStyle.setLayoutY(60);

        CBlisteStyle = new ComboBox();
        listeStyles.stream().forEach((nomStyle) -> {
            CBlisteStyle.getItems().add(nomStyle);
        });
        CBlisteStyle.setLayoutX(150);
        CBlisteStyle.setLayoutY(70);
        CBlisteStyle.setValue(styleBarre);
        CPCouleurBoutons = new ColorPicker(couleurBoutons);
        CPCouleurBoutons.setOnAction((ActionEvent e) -> {
            couleurBoutons = CPCouleurBoutons.getValue();
            changeCouleurBarre(couleurBoutons.getHue(), couleurBoutons.getSaturation(), couleurBoutons.getBrightness());
        });
        Label lblCouleurBouton = new Label(rb.getString("interface.couleurBarre"));
        lblCouleurBouton.setLayoutX(20);
        lblCouleurBouton.setLayoutY(110);
        CPCouleurBoutons.setLayoutX(150);
        CPCouleurBoutons.setLayoutY(105);

        RBTopLeft = new RadioButton();
        RBTopCenter = new RadioButton();
        RBTopRight = new RadioButton();
        RBMiddleLeft = new RadioButton();
        RBMiddleCenter = new RadioButton();
        RBMiddleRight = new RadioButton();
        RBBottomLeft = new RadioButton();
        RBBottomCenter = new RadioButton();
        RBBottomRight = new RadioButton();

        RBTopLeft.setUserData("top:left");
        RBTopCenter.setUserData("top:center");
        RBTopRight.setUserData("top:right");
        RBMiddleLeft.setUserData("middle:left");
        RBMiddleCenter.setUserData("middle:center");
        RBMiddleRight.setUserData("middle:right");
        RBBottomLeft.setUserData("bottom:left");
        RBBottomCenter.setUserData("bottom:center");
        RBBottomRight.setUserData("bottom:right");

        RBTopLeft.setToggleGroup(grpPostBarre);
        RBTopCenter.setToggleGroup(grpPostBarre);
        RBTopRight.setToggleGroup(grpPostBarre);
        RBMiddleLeft.setToggleGroup(grpPostBarre);
        RBMiddleCenter.setToggleGroup(grpPostBarre);
        RBMiddleRight.setToggleGroup(grpPostBarre);
        RBBottomLeft.setToggleGroup(grpPostBarre);
        RBBottomCenter.setToggleGroup(grpPostBarre);
        RBBottomRight.setToggleGroup(grpPostBarre);

        int posX = 250;
        int posY = 140;

        RBTopLeft.setLayoutX(posX);
        RBTopCenter.setLayoutX(posX + 20);
        RBTopRight.setLayoutX(posX + 40);
        RBTopLeft.setLayoutY(posY);
        RBTopCenter.setLayoutY(posY);
        RBTopRight.setLayoutY(posY);

        RBMiddleLeft.setLayoutX(posX);
        RBMiddleCenter.setLayoutX(posX + 20);
        RBMiddleRight.setLayoutX(posX + 40);
        RBMiddleLeft.setLayoutY(posY + 20);
        RBMiddleCenter.setLayoutY(posY + 20);
        RBMiddleRight.setLayoutY(posY + 20);

        RBBottomLeft.setLayoutX(posX);
        RBBottomCenter.setLayoutX(posX + 20);
        RBBottomRight.setLayoutX(posX + 40);
        RBBottomLeft.setLayoutY(posY + 40);
        RBBottomCenter.setLayoutY(posY + 40);
        RBBottomRight.setLayoutY(posY + 40);
        RBBottomCenter.setSelected(true);
        Label lblPosit = new Label(rb.getString("interface.position"));
        lblPosit.setLayoutX(10);
        lblPosit.setLayoutY(140);

        Label lblDXSpinner = new Label("dX :");
        lblDXSpinner.setLayoutX(25);
        lblDXSpinner.setLayoutY(205);
        Label lblDYSpinner = new Label("dY :");
        lblDYSpinner.setLayoutX(175);
        lblDYSpinner.setLayoutY(205);
        dXSpinner = new BigDecimalField(new BigDecimal(dXBarre));
        dXSpinner.setLayoutX(50);
        dXSpinner.setLayoutY(200);
        dXSpinner.setMaxValue(new BigDecimal(2000));
        dXSpinner.setMinValue(new BigDecimal(-2000));
        dXSpinner.setMaxWidth(100);
        dYSpinner = new BigDecimalField(new BigDecimal(dYBarre));
        dYSpinner.setLayoutX(200);
        dYSpinner.setLayoutY(200);
        dYSpinner.setMaxValue(new BigDecimal(2000));
        dYSpinner.setMinValue(new BigDecimal(-2000));
        dYSpinner.setMaxWidth(100);

        CBDeplacements = new CheckBox(rb.getString("interface.deplacementsVisible"));
        CBDeplacements.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreDeplacements = "oui";
            } else {
                toggleBarreDeplacements = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBZoom = new CheckBox(rb.getString("interface.zoomVisible"));
        CBZoom.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreZoom = "oui";
            } else {
                toggleBarreZoom = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBOutils = new CheckBox(rb.getString("interface.outilsVisible"));
        CBOutils.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBarreOutils = "oui";
            } else {
                toggleBarreOutils = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBSouris = new CheckBox(rb.getString("interface.outilsSouris"));
        CBSouris.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonSouris = "oui";
            } else {
                toggleBoutonSouris = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBRotation = new CheckBox(rb.getString("interface.outilsRotation"));
        CBRotation.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonRotation = "oui";
            } else {
                toggleBoutonRotation = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        CBFS = new CheckBox(rb.getString("interface.outilsFS"));
        CBFS.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                toggleBoutonFS = "oui";
            } else {
                toggleBoutonFS = "non";
            }
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        Label lblVisibilite = new Label(rb.getString("interface.visibilite"));
        lblVisibilite.setLayoutX(10);
        lblVisibilite.setLayoutY(240);

        CBDeplacements.setLayoutX(100);
        CBDeplacements.setLayoutY(260);
        CBDeplacements.setSelected(true);
        CBZoom.setLayoutX(100);
        CBZoom.setLayoutY(280);
        CBZoom.setSelected(true);
        CBOutils.setLayoutX(100);
        CBOutils.setLayoutY(300);
        CBOutils.setSelected(true);
        CBFS.setLayoutX(150);
        CBFS.setLayoutY(320);
        CBFS.setSelected(true);
        CBRotation.setLayoutX(150);
        CBRotation.setLayoutY(340);
        CBRotation.setSelected(true);
        CBSouris.setLayoutX(150);
        CBSouris.setLayoutY(360);
        CBSouris.setSelected(true);

        Label lblBarreBouton = new Label(rb.getString("interface.barreBoutons"));
        lblBarreBouton.setPrefWidth(VBOutils.getPrefWidth());
        lblBarreBouton.setStyle("-fx-background-color : #444");
        lblBarreBouton.setTextFill(Color.WHITE);
        lblBarreBouton.setPadding(new Insets(5));
        lblBarreBouton.setLayoutX(10);
        lblBarreBouton.setLayoutY(10);
        ImageView IVBtnPlus = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlus.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlus.setLayoutY(13);
        double tailleInitiale = APBarreModif.getPrefHeight();
        APBarreModif.setPrefHeight(0);
        APBarreModif.setMaxHeight(0);
        APBarreModif.setMinHeight(0);
        APBarreModif.setVisible(false);

        lblBarreBouton.setOnMouseClicked((MouseEvent me) -> {
            if (APBarreModif.isVisible()) {
                APBarreModif.setPrefHeight(0);
                APBarreModif.setMaxHeight(0);
                APBarreModif.setMinHeight(0);
                APBarreModif.setVisible(false);
                IVBtnPlus.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APBarreModif.setPrefHeight(tailleInitiale);
                APBarreModif.setMaxHeight(tailleInitiale);
                APBarreModif.setMinHeight(tailleInitiale);
                APBarreModif.setVisible(true);
                IVBtnPlus.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        IVBtnPlus.setOnMouseClicked((MouseEvent me) -> {
            if (APBarreModif.isVisible()) {
                APBarreModif.setPrefHeight(0);
                APBarreModif.setMaxHeight(0);
                APBarreModif.setMinHeight(0);
                APBarreModif.setVisible(false);
                IVBtnPlus.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                APBarreModif.setPrefHeight(tailleInitiale);
                APBarreModif.setMaxHeight(tailleInitiale);
                APBarreModif.setMinHeight(tailleInitiale);
                APBarreModif.setVisible(true);
                IVBtnPlus.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        APBarreModif.getChildren().addAll(
                CBVisible, CBSuivantPrecedent, lblStyle, CBlisteStyle,
                lblCouleurBouton, CPCouleurBoutons,
                lblPosit,
                RBTopLeft, RBTopCenter, RBTopRight,
                RBMiddleLeft, RBMiddleCenter, RBMiddleRight,
                RBBottomLeft, RBBottomCenter, RBBottomRight,
                lblDXSpinner, dXSpinner, lblDYSpinner, dYSpinner,
                lblVisibilite, CBDeplacements, CBZoom, CBOutils,
                CBFS, CBRotation, CBSouris);

        /*
         * ********************************************
         *      Panel Boussole 
         * ********************************************
         */
        AnchorPane APBoussole = new AnchorPane();

        APBoussole.setLayoutY(40);
        APBoussole.setPrefHeight(340);
        APBoussole.setMinWidth(VBOutils.getPrefWidth() - 20);
        Double tailleBouss = APBoussole.getPrefHeight();
        CBAfficheBoussole = new CheckBox(rb.getString("interface.affichageBoussole"));
        CBAfficheBoussole.setLayoutX(10);
        CBAfficheBoussole.setLayoutY(10);
        APBoussole.getChildren().add(CBAfficheBoussole);
        CBAfficheBoussole.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheBoussole = new_val;
                afficheBoussole();
            }
        });
        Label lblPanelBoussole = new Label(rb.getString("interface.boussole"));
        lblPanelBoussole.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelBoussole.setStyle("-fx-background-color : #444");
        lblPanelBoussole.setTextFill(Color.WHITE);
        lblPanelBoussole.setPadding(new Insets(5));
        lblPanelBoussole.setLayoutX(10);
        lblPanelBoussole.setLayoutY(10);
        ImageView IVBtnPlusBouss = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusBouss.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusBouss.setLayoutY(13);
        Label lblChoixBoussole = new Label(rb.getString("interface.choixImgBoussole"));
        lblChoixBoussole.setLayoutX(10);
        lblChoixBoussole.setLayoutY(40);
        APBoussole.getChildren().add(lblChoixBoussole);

        int nombreBoussoles = listeBoussoles.size();
        ImageView[] IVBoussoles = new ImageView[nombreBoussoles];
        i = 0;
        for (String nomImage : listeBoussoles) {
            IVBoussoles[i] = new ImageView(new Image("file:" + repertBoussoles + File.separator + nomImage, -1, 60, true, true, true));
            int col = i % 3;
            int row = i / 3;
            xHS = col * 70 + 95;
            yHS = row * 70 + 60;
            IVBoussoles[i].setLayoutX(xHS);
            IVBoussoles[i].setLayoutY(yHS);
            IVBoussoles[i].setUserData(nomImage);
            IVBoussoles[i].setStyle("-fx-background-color : #ccc");
            IVBoussoles[i].setOnMouseClicked((MouseEvent me) -> {
                imageBoussole = (String) ((ImageView) me.getSource()).getUserData();
                afficheBoussole();
            });
            APBoussole.getChildren().add(IVBoussoles[i]);
            i++;

        }
        Label lblPositBoussole = new Label(rb.getString("interface.choixPositBoussole"));
        lblPositBoussole.setLayoutX(10);
        lblPositBoussole.setLayoutY(160);
        APBoussole.getChildren().add(lblPositBoussole);

        RBBoussTopLeft = new RadioButton();
        RBBoussTopRight = new RadioButton();
        RBBoussBottomLeft = new RadioButton();
        RBBoussBottomRight = new RadioButton();

        RBBoussTopLeft.setUserData("top:left");
        RBBoussTopRight.setUserData("top:right");
        RBBoussBottomLeft.setUserData("bottom:left");
        RBBoussBottomRight.setUserData("bottom:right");

        RBBoussTopLeft.setToggleGroup(grpPostBouss);
        RBBoussTopRight.setToggleGroup(grpPostBouss);
        RBBoussBottomLeft.setToggleGroup(grpPostBouss);
        RBBoussBottomRight.setToggleGroup(grpPostBouss);

        posX = 200;
        posY = 160;

        RBBoussTopLeft.setLayoutX(posX);
        RBBoussTopRight.setLayoutX(posX + 20);
        RBBoussTopLeft.setLayoutY(posY);
        RBBoussTopRight.setLayoutY(posY);

        RBBoussBottomLeft.setLayoutX(posX);
        RBBoussBottomRight.setLayoutX(posX + 20);
        RBBoussBottomLeft.setLayoutY(posY + 20);
        RBBoussBottomRight.setLayoutY(posY + 20);
        APBoussole.getChildren().addAll(
                RBBoussTopLeft, RBBoussTopRight,
                RBBoussBottomLeft, RBBoussBottomRight
        );
        Label lblBoussDXSpinner = new Label("dX :");
        lblBoussDXSpinner.setLayoutX(25);
        lblBoussDXSpinner.setLayoutY(210);
        Label lblBoussDYSpinner = new Label("dY :");
        lblBoussDYSpinner.setLayoutX(175);
        lblBoussDYSpinner.setLayoutY(210);
        boussDXSpinner = new BigDecimalField(new BigDecimal(dXBarre));
        boussDXSpinner.setLayoutX(50);
        boussDXSpinner.setLayoutY(205);
        boussDXSpinner.setMaxValue(new BigDecimal(2000));
        boussDXSpinner.setMinValue(new BigDecimal(0));
        boussDXSpinner.setNumber(new BigDecimal(20));
        boussDXSpinner.setMaxWidth(100);
        boussDYSpinner = new BigDecimalField(new BigDecimal(dYBarre));
        boussDYSpinner.setLayoutX(200);
        boussDYSpinner.setLayoutY(205);
        boussDYSpinner.setMaxValue(new BigDecimal(2000));
        boussDYSpinner.setMinValue(new BigDecimal(0));
        boussDYSpinner.setNumber(new BigDecimal(20));
        boussDYSpinner.setMaxWidth(100);
        APBoussole.getChildren().addAll(
                lblBoussDXSpinner, boussDXSpinner,
                lblBoussDYSpinner, boussDYSpinner
        );
        Label lblTailleBouss = new Label(rb.getString("interface.tailleBoussole"));
        lblTailleBouss.setLayoutX(10);
        lblTailleBouss.setLayoutY(245);
        SLTailleBoussole = new Slider(50, 200, 100);
        SLTailleBoussole.setLayoutX(200);
        SLTailleBoussole.setLayoutY(245);
        Label lblOpaciteBouss = new Label(rb.getString("interface.opaciteBoussole"));
        lblOpaciteBouss.setLayoutX(10);
        lblOpaciteBouss.setLayoutY(275);
        SLOpaciteBoussole = new Slider(0, 1.0, 0.8);
        SLOpaciteBoussole.setLayoutX(200);
        SLOpaciteBoussole.setLayoutY(275);
        CBAiguilleMobile = new CheckBox(rb.getString("interface.aiguilleMobile"));
        CBAiguilleMobile.setLayoutX(10);
        CBAiguilleMobile.setLayoutY(305);
        CBAiguilleMobile.setSelected(true);
        CBAiguilleMobile.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAiguilleMobileBoussole = new_val;
            }
        });

        APBoussole.getChildren().addAll(
                lblTailleBouss, SLTailleBoussole,
                lblOpaciteBouss, SLOpaciteBoussole,
                CBAiguilleMobile
        );
        APBoussole.setPrefHeight(0);
        APBoussole.setMaxHeight(0);
        APBoussole.setMinHeight(0);
        APBoussole.setVisible(false);

        lblPanelBoussole.setOnMouseClicked((MouseEvent me) -> {
            if (APBoussole.isVisible()) {
                IVBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APBoussole.setPrefHeight(0);
                APBoussole.setMaxHeight(0);
                APBoussole.setMinHeight(0);
                APBoussole.setVisible(false);
            } else {
                IVBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APBoussole.setPrefHeight(tailleBouss);
                APBoussole.setMaxHeight(tailleBouss);
                APBoussole.setMinHeight(tailleBouss);
                APBoussole.setVisible(true);
            }
        });
        IVBtnPlusBouss.setOnMouseClicked((MouseEvent me) -> {
            if (APBoussole.isVisible()) {
                IVBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APBoussole.setPrefHeight(0);
                APBoussole.setMaxHeight(0);
                APBoussole.setMinHeight(0);
                APBoussole.setVisible(false);
            } else {
                IVBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APBoussole.setPrefHeight(tailleBouss);
                APBoussole.setMaxHeight(tailleBouss);
                APBoussole.setMinHeight(tailleBouss);
                APBoussole.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Masque 
         * ********************************************
         */
        AnchorPane APMasque = new AnchorPane();

        APMasque.setLayoutY(40);
        APMasque.setPrefHeight(430);
        APMasque.setMinWidth(VBOutils.getPrefWidth() - 20);
        Double taillePanelMasque = APMasque.getPrefHeight();
        CBAfficheMasque = new CheckBox(rb.getString("interface.affichageMasque"));
        CBAfficheMasque.setLayoutX(10);
        CBAfficheMasque.setLayoutY(10);
        APMasque.getChildren().add(CBAfficheMasque);
        CBAfficheMasque.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheMasque = new_val;
                changeCouleurMasque(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                afficheMasque();
            }
        });
        Label lblPanelMasque = new Label(rb.getString("interface.masque"));
        lblPanelMasque.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelMasque.setStyle("-fx-background-color : #444");
        lblPanelMasque.setTextFill(Color.WHITE);
        lblPanelMasque.setPadding(new Insets(5));
        lblPanelMasque.setLayoutX(10);
        lblPanelMasque.setLayoutY(10);
        ImageView IVBtnPlusMasque = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusMasque.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusMasque.setLayoutY(13);
        Label lblChoixMasque = new Label(rb.getString("interface.choixImgMasque"));
        lblChoixMasque.setLayoutX(10);
        lblChoixMasque.setLayoutY(40);
        APMasque.getChildren().add(lblChoixMasque);

        int nombreMasques = listeMasques.size();
        ImageView[] IVMasques = new ImageView[nombreMasques];
        i = 0;
        for (String nomImage : listeMasques) {
            IVMasques[i] = new ImageView(new Image("file:" + repertMasques + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 4;
            int row = i / 4;
            xHS = col * 35 + 15;
            yHS = row * 35 + 60;
            IVMasques[i].setLayoutX(xHS);
            IVMasques[i].setLayoutY(yHS);
            IVMasques[i].setUserData(nomImage);
            IVMasques[i].setStyle("-fx-background-color : #ccc");
            IVMasques[i].setOnMouseClicked((MouseEvent me) -> {
                imageMasque = (String) ((ImageView) me.getSource()).getUserData();
                APVisualisation.getChildren().remove(IVMasque);
                chargeBarre(styleBarre, styleHotSpots, imageMasque);
                afficheMasque();
            });
            APMasque.getChildren().add(IVMasques[i]);
            i++;

        }
        CPCouleurMasques = new ColorPicker(couleurMasque);
        CPCouleurMasques.setOnAction((ActionEvent e) -> {
            couleurMasque = CPCouleurMasques.getValue();
            changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        });
        Label lblCouleurMasque = new Label(rb.getString("interface.couleurBarre"));
        lblCouleurMasque.setLayoutX(150);
        lblCouleurMasque.setLayoutY(40);
        CPCouleurMasques.setLayoutX(170);
        CPCouleurMasques.setLayoutY(60);
        APMasque.getChildren().addAll(lblCouleurMasque, CPCouleurMasques);

        Label lblPositMasque = new Label(rb.getString("interface.choixPositMasque"));
        lblPositMasque.setLayoutX(10);
        int basImages = ((i - 1) / 4 + 1) * 35;
        lblPositMasque.setLayoutY(70 + basImages);
        APMasque.getChildren().add(lblPositMasque);

        RBMasqueTopLeft = new RadioButton();
        RBMasqueTopRight = new RadioButton();
        RBMasqueBottomLeft = new RadioButton();
        RBMasqueBottomRight = new RadioButton();

        RBMasqueTopLeft.setUserData("top:left");
        RBMasqueTopRight.setUserData("top:right");
        RBMasqueBottomLeft.setUserData("bottom:left");
        RBMasqueBottomRight.setUserData("bottom:right");

        RBMasqueTopLeft.setToggleGroup(grpPostMasque);
        RBMasqueTopRight.setToggleGroup(grpPostMasque);
        RBMasqueBottomLeft.setToggleGroup(grpPostMasque);
        RBMasqueBottomRight.setToggleGroup(grpPostMasque);

        posX = 200;
        posY = 70 + basImages;

        RBMasqueTopLeft.setLayoutX(posX);
        RBMasqueTopRight.setLayoutX(posX + 20);
        RBMasqueTopLeft.setLayoutY(posY);
        RBMasqueTopRight.setLayoutY(posY);

        RBMasqueBottomLeft.setLayoutX(posX);
        RBMasqueBottomRight.setLayoutX(posX + 20);
        RBMasqueBottomLeft.setLayoutY(posY + 20);
        RBMasqueBottomRight.setLayoutY(posY + 20);
        APMasque.getChildren().addAll(
                RBMasqueTopLeft, RBMasqueTopRight,
                RBMasqueBottomLeft, RBMasqueBottomRight
        );
        Label lblMasqueDXSpinner = new Label("dX :");
        lblMasqueDXSpinner.setLayoutX(25);
        lblMasqueDXSpinner.setLayoutY(128 + basImages);
        Label lblMasqueDYSpinner = new Label("dY :");
        lblMasqueDYSpinner.setLayoutX(175);
        lblMasqueDYSpinner.setLayoutY(128 + basImages);
        masqueDXSpinner = new BigDecimalField(new BigDecimal(dXBarre));
        masqueDXSpinner.setLayoutX(50);
        masqueDXSpinner.setLayoutY(123 + basImages);
        masqueDXSpinner.setMaxValue(new BigDecimal(2000));
        masqueDXSpinner.setMinValue(new BigDecimal(0));
        masqueDXSpinner.setNumber(new BigDecimal(20));
        masqueDXSpinner.setMaxWidth(100);
        masqueDYSpinner = new BigDecimalField(new BigDecimal(dYBarre));
        masqueDYSpinner.setLayoutX(200);
        masqueDYSpinner.setLayoutY(123 + basImages);
        masqueDYSpinner.setMaxValue(new BigDecimal(2000));
        masqueDYSpinner.setMinValue(new BigDecimal(0));
        masqueDYSpinner.setNumber(new BigDecimal(20));
        masqueDYSpinner.setMaxWidth(100);
        APMasque.getChildren().addAll(
                lblMasqueDXSpinner, masqueDXSpinner,
                lblMasqueDYSpinner, masqueDYSpinner
        );
        Label lblTailleMasque = new Label(rb.getString("interface.tailleMasque"));
        lblTailleMasque.setLayoutX(10);
        lblTailleMasque.setLayoutY(160 + basImages);
        SLTailleMasque = new Slider(15, 60, 30);
        SLTailleMasque.setLayoutX(200);
        SLTailleMasque.setLayoutY(160 + basImages);
        Label lblOpaciteMasque = new Label(rb.getString("interface.opaciteMasque"));
        lblOpaciteMasque.setLayoutX(10);
        lblOpaciteMasque.setLayoutY(190 + basImages);
        SLOpaciteMasque = new Slider(0, 1.0, 0.8);
        SLOpaciteMasque.setLayoutX(200);
        SLOpaciteMasque.setLayoutY(190 + basImages);
        CBMasqueNavigation = new CheckBox(rb.getString("interface.masqueNavigation"));
        CBMasqueNavigation.setLayoutX(60);
        CBMasqueNavigation.setLayoutY(220 + basImages);
        CBMasqueNavigation.setSelected(true);
        CBMasqueNavigation.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueNavigation = new_val;
            }
        });
        CBMasqueBoussole = new CheckBox(rb.getString("interface.masqueBoussole"));
        CBMasqueBoussole.setLayoutX(60);
        CBMasqueBoussole.setLayoutY(250 + basImages);
        CBMasqueBoussole.setSelected(true);
        CBMasqueBoussole.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueBoussole = new_val;
            }
        });
        CBMasqueTitre = new CheckBox(rb.getString("interface.masqueTitre"));
        CBMasqueTitre.setLayoutX(60);
        CBMasqueTitre.setLayoutY(280 + basImages);
        CBMasqueTitre.setSelected(true);
        CBMasqueTitre.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueTitre = new_val;
            }
        });
        CBMasquePlan = new CheckBox(rb.getString("interface.masquePlan"));
        CBMasquePlan.setLayoutX(60);
        CBMasquePlan.setLayoutY(310 + basImages);
        CBMasquePlan.setSelected(true);
        CBMasquePlan.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasquePlan = new_val;
            }
        });
        CBMasqueReseaux = new CheckBox(rb.getString("interface.masqueReseaux"));
        CBMasqueReseaux.setLayoutX(60);
        CBMasqueReseaux.setLayoutY(340 + basImages);
        CBMasqueReseaux.setSelected(true);
        CBMasqueReseaux.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueReseaux = new_val;
            }
        });
        CBMasqueVignettes = new CheckBox(rb.getString("interface.masqueVignettes"));
        CBMasqueVignettes.setLayoutX(60);
        CBMasqueVignettes.setLayoutY(370 + basImages);
        CBMasqueVignettes.setSelected(true);
        CBMasqueVignettes.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueVignettes = new_val;
            }
        });

        APMasque.getChildren().addAll(
                lblTailleMasque, SLTailleMasque,
                lblOpaciteMasque, SLOpaciteMasque,
                CBMasqueNavigation, CBMasqueBoussole, CBMasqueTitre, CBMasquePlan, CBMasqueReseaux, CBMasqueVignettes
        );
        APMasque.setPrefHeight(0);
        APMasque.setMaxHeight(0);
        APMasque.setMinHeight(0);
        APMasque.setVisible(false);

        lblPanelMasque.setOnMouseClicked((MouseEvent me) -> {
            if (APMasque.isVisible()) {
                IVBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APMasque.setPrefHeight(0);
                APMasque.setMaxHeight(0);
                APMasque.setMinHeight(0);
                APMasque.setVisible(false);
            } else {
                IVBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APMasque.setPrefHeight(taillePanelMasque);
                APMasque.setMaxHeight(taillePanelMasque);
                APMasque.setMinHeight(taillePanelMasque);
                APMasque.setVisible(true);
            }
        });
        IVBtnPlusMasque.setOnMouseClicked((MouseEvent me) -> {
            if (APMasque.isVisible()) {
                IVBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APMasque.setPrefHeight(0);
                APMasque.setMaxHeight(0);
                APMasque.setMinHeight(0);
                APMasque.setVisible(false);
            } else {
                IVBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APMasque.setPrefHeight(taillePanelMasque);
                APMasque.setMaxHeight(taillePanelMasque);
                APMasque.setMinHeight(taillePanelMasque);
                APMasque.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel ReseauxSociaux 
         * ********************************************
         */
        AnchorPane APReseauxSociaux = new AnchorPane();

        APReseauxSociaux.setLayoutY(40);
        APReseauxSociaux.setPrefHeight(380);
        APReseauxSociaux.setMinWidth(VBOutils.getPrefWidth() - 20);
        Double taillePanelReseauxSociaux = APReseauxSociaux.getPrefHeight();
        CBAfficheReseauxSociaux = new CheckBox(rb.getString("interface.affichageReseauxSociaux"));
        CBAfficheReseauxSociaux.setLayoutX(10);
        CBAfficheReseauxSociaux.setLayoutY(10);
        APReseauxSociaux.getChildren().add(CBAfficheReseauxSociaux);
        CBAfficheReseauxSociaux.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheReseauxSociaux = new_val;
                afficheReseauxSociaux();
            }
        });
        Label lblPanelReseauxSociaux = new Label(rb.getString("interface.reseauxSociaux"));
        lblPanelReseauxSociaux.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelReseauxSociaux.setStyle("-fx-background-color : #444");
        lblPanelReseauxSociaux.setTextFill(Color.WHITE);
        lblPanelReseauxSociaux.setPadding(new Insets(5));
        lblPanelReseauxSociaux.setLayoutX(10);
        lblPanelReseauxSociaux.setLayoutY(10);
        ImageView IVBtnPlusReseauxSociaux = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusReseauxSociaux.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusReseauxSociaux.setLayoutY(13);
        Label lblPositReseauxSociaux = new Label(rb.getString("interface.choixPositReseauxSociaux"));
        lblPositReseauxSociaux.setLayoutX(10);
        basImages = -30;
        lblPositReseauxSociaux.setLayoutY(70 + basImages);
        APReseauxSociaux.getChildren().add(lblPositReseauxSociaux);

        RBReseauxSociauxTopLeft = new RadioButton();
        RBReseauxSociauxTopRight = new RadioButton();
        RBReseauxSociauxBottomLeft = new RadioButton();
        RBReseauxSociauxBottomRight = new RadioButton();

        RBReseauxSociauxTopLeft.setUserData("top:left");
        RBReseauxSociauxTopRight.setUserData("top:right");
        RBReseauxSociauxBottomLeft.setUserData("bottom:left");
        RBReseauxSociauxBottomRight.setUserData("bottom:right");

        RBReseauxSociauxTopLeft.setToggleGroup(grpPostReseauxSociaux);
        RBReseauxSociauxTopRight.setToggleGroup(grpPostReseauxSociaux);
        RBReseauxSociauxBottomLeft.setToggleGroup(grpPostReseauxSociaux);
        RBReseauxSociauxBottomRight.setToggleGroup(grpPostReseauxSociaux);

        posX = 200;
        posY = 70 + basImages;

        RBReseauxSociauxTopLeft.setLayoutX(posX);
        RBReseauxSociauxTopRight.setLayoutX(posX + 20);
        RBReseauxSociauxTopLeft.setLayoutY(posY);
        RBReseauxSociauxTopRight.setLayoutY(posY);

        RBReseauxSociauxBottomLeft.setLayoutX(posX);
        RBReseauxSociauxBottomRight.setLayoutX(posX + 20);
        RBReseauxSociauxBottomLeft.setLayoutY(posY + 20);
        RBReseauxSociauxBottomRight.setLayoutY(posY + 20);
        APReseauxSociaux.getChildren().addAll(
                RBReseauxSociauxTopLeft, RBReseauxSociauxTopRight,
                RBReseauxSociauxBottomLeft, RBReseauxSociauxBottomRight
        );
        Label lblReseauxSociauxDXSpinner = new Label("dX :");
        lblReseauxSociauxDXSpinner.setLayoutX(25);
        lblReseauxSociauxDXSpinner.setLayoutY(128 + basImages);
        Label lblReseauxSociauxDYSpinner = new Label("dY :");
        lblReseauxSociauxDYSpinner.setLayoutX(175);
        lblReseauxSociauxDYSpinner.setLayoutY(128 + basImages);
        reseauxSociauxDXSpinner = new BigDecimalField(new BigDecimal(dXBarre));
        reseauxSociauxDXSpinner.setLayoutX(50);
        reseauxSociauxDXSpinner.setLayoutY(123 + basImages);
        reseauxSociauxDXSpinner.setMaxValue(new BigDecimal(2000));
        reseauxSociauxDXSpinner.setMinValue(new BigDecimal(0));
        reseauxSociauxDXSpinner.setNumber(new BigDecimal(20));
        reseauxSociauxDXSpinner.setMaxWidth(100);
        reseauxSociauxDYSpinner = new BigDecimalField(new BigDecimal(dYBarre));
        reseauxSociauxDYSpinner.setLayoutX(200);
        reseauxSociauxDYSpinner.setLayoutY(123 + basImages);
        reseauxSociauxDYSpinner.setMaxValue(new BigDecimal(2000));
        reseauxSociauxDYSpinner.setMinValue(new BigDecimal(0));
        reseauxSociauxDYSpinner.setNumber(new BigDecimal(20));
        reseauxSociauxDYSpinner.setMaxWidth(100);
        APReseauxSociaux.getChildren().addAll(
                lblReseauxSociauxDXSpinner, reseauxSociauxDXSpinner,
                lblReseauxSociauxDYSpinner, reseauxSociauxDYSpinner
        );
        Label lblTailleReseauxSociaux = new Label(rb.getString("interface.tailleReseauxSociaux"));
        lblTailleReseauxSociaux.setLayoutX(10);
        lblTailleReseauxSociaux.setLayoutY(160 + basImages);
        SLTailleReseauxSociaux = new Slider(15, 60, 30);
        SLTailleReseauxSociaux.setLayoutX(200);
        SLTailleReseauxSociaux.setLayoutY(160 + basImages);
        Label lblOpaciteReseauxSociaux = new Label(rb.getString("interface.opaciteReseauxSociaux"));
        lblOpaciteReseauxSociaux.setLayoutX(10);
        lblOpaciteReseauxSociaux.setLayoutY(190 + basImages);
        SLOpaciteReseauxSociaux = new Slider(0, 1.0, 0.8);
        SLOpaciteReseauxSociaux.setLayoutX(200);
        SLOpaciteReseauxSociaux.setLayoutY(190 + basImages);
        CBReseauxSociauxTwitter = new CheckBox("Twitter");
        CBReseauxSociauxTwitter.setLayoutX(60);
        CBReseauxSociauxTwitter.setLayoutY(220 + basImages);
        CBReseauxSociauxTwitter.setSelected(true);
        CBReseauxSociauxTwitter.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxTwitter = new_val;
                afficheReseauxSociaux();
            }
        });
        CBReseauxSociauxGoogle = new CheckBox("Google+");
        CBReseauxSociauxGoogle.setLayoutX(60);
        CBReseauxSociauxGoogle.setLayoutY(250 + basImages);
        CBReseauxSociauxGoogle.setSelected(true);
        CBReseauxSociauxGoogle.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxGoogle = new_val;
                afficheReseauxSociaux();
            }
        });
        CBReseauxSociauxFacebook = new CheckBox("Facebook");
        CBReseauxSociauxFacebook.setLayoutX(60);
        CBReseauxSociauxFacebook.setLayoutY(280 + basImages);
        CBReseauxSociauxFacebook.setSelected(true);
        CBReseauxSociauxFacebook.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxFacebook = new_val;
                afficheReseauxSociaux();
            }
        });
        CBReseauxSociauxEmail = new CheckBox("Email");
        CBReseauxSociauxEmail.setLayoutX(60);
        CBReseauxSociauxEmail.setLayoutY(310 + basImages);
        CBReseauxSociauxEmail.setSelected(true);
        CBReseauxSociauxEmail.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxEmail = new_val;
                afficheReseauxSociaux();
            }
        });

        APReseauxSociaux.getChildren().addAll(
                lblTailleReseauxSociaux, SLTailleReseauxSociaux,
                lblOpaciteReseauxSociaux, SLOpaciteReseauxSociaux,
                CBReseauxSociauxTwitter, CBReseauxSociauxGoogle, CBReseauxSociauxFacebook, CBReseauxSociauxEmail
        );
        APReseauxSociaux.setPrefHeight(0);
        APReseauxSociaux.setMaxHeight(0);
        APReseauxSociaux.setMinHeight(0);
        APReseauxSociaux.setVisible(false);

        lblPanelReseauxSociaux.setOnMouseClicked((MouseEvent me) -> {
            if (APReseauxSociaux.isVisible()) {
                IVBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APReseauxSociaux.setPrefHeight(0);
                APReseauxSociaux.setMaxHeight(0);
                APReseauxSociaux.setMinHeight(0);
                APReseauxSociaux.setVisible(false);
            } else {
                IVBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setVisible(true);
            }
        });
        IVBtnPlusReseauxSociaux.setOnMouseClicked((MouseEvent me) -> {
            if (APReseauxSociaux.isVisible()) {
                IVBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APReseauxSociaux.setPrefHeight(0);
                APReseauxSociaux.setMaxHeight(0);
                APReseauxSociaux.setMinHeight(0);
                APReseauxSociaux.setVisible(false);
            } else {
                IVBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                APReseauxSociaux.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Vignettes 
         * ********************************************
         */
        AnchorPane APVignettes = new AnchorPane();

        APVignettes.setLayoutY(40);
        APVignettes.setPrefHeight(380);
        APVignettes.setMinWidth(VBOutils.getPrefWidth() - 20);
        Double taillePanelVignettes = APVignettes.getPrefHeight();
        CBAfficheVignettes = new CheckBox(rb.getString("interface.affichageVignettes"));
        CBAfficheVignettes.setLayoutX(10);
        CBAfficheVignettes.setLayoutY(10);
        APVignettes.getChildren().add(CBAfficheVignettes);
        CBAfficheVignettes.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheVignettes = new_val;
                afficheVignettes();
            }
        });
        Label lblPanelVignettes = new Label(rb.getString("interface.vignettes"));
        lblPanelVignettes.setPrefWidth(VBOutils.getPrefWidth());
        lblPanelVignettes.setStyle("-fx-background-color : #444");
        lblPanelVignettes.setTextFill(Color.WHITE);
        lblPanelVignettes.setPadding(new Insets(5));
        lblPanelVignettes.setLayoutX(10);
        lblPanelVignettes.setLayoutY(10);
        ImageView IVBtnPlusVignettes = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        IVBtnPlusVignettes.setLayoutX(VBOutils.getPrefWidth() - 20);
        IVBtnPlusVignettes.setLayoutY(13);

        Label lblChoixCouleurFondVignettes = new Label(rb.getString("interface.choixCouleurFondVignettes"));
        lblChoixCouleurFondVignettes.setLayoutX(10);
        lblChoixCouleurFondVignettes.setLayoutY(85);
        CPCouleurFondVignettes = new ColorPicker(Color.valueOf(couleurFondTitre));
        CPCouleurFondVignettes.setLayoutX(200);
        CPCouleurFondVignettes.setLayoutY(83);
        CPCouleurFondVignettes.setOnAction((ActionEvent e) -> {
            String coul = CPCouleurFondVignettes.getValue().toString().substring(2, 8);
            couleurFondVignettes = "#" + coul;
            afficheVignettes();
        });
        APVignettes.getChildren().addAll(lblChoixCouleurFondVignettes, CPCouleurFondVignettes);
        Label lblPositVignettes = new Label(rb.getString("interface.choixPositVignettes"));
        lblPositVignettes.setLayoutX(10);
        basImages = -30;
        lblPositVignettes.setLayoutY(70 + basImages);
        APVignettes.getChildren().add(lblPositVignettes);

        RBVignettesLeft = new RadioButton();
        RBVignettesRight = new RadioButton();
        RBVignettesBottom = new RadioButton();

        RBVignettesLeft.setUserData("left");
        RBVignettesRight.setUserData("right");
        RBVignettesBottom.setUserData("bottom");

        RBVignettesLeft.setToggleGroup(grpPostVignettes);
        RBVignettesRight.setToggleGroup(grpPostVignettes);
        RBVignettesBottom.setToggleGroup(grpPostVignettes);

        posX = 200;
        posY = 70 + basImages;

        RBVignettesLeft.setLayoutX(posX);
        RBVignettesRight.setLayoutX(posX + 40);
        RBVignettesLeft.setLayoutY(posY);
        RBVignettesRight.setLayoutY(posY);

        RBVignettesBottom.setLayoutX(posX + 20);
        RBVignettesBottom.setLayoutY(posY + 20);
        APVignettes.getChildren().addAll(
                RBVignettesLeft, RBVignettesRight,
                RBVignettesBottom
        );
        Label lblTailleVignettes = new Label(rb.getString("interface.tailleVignettes"));
        lblTailleVignettes.setLayoutX(10);
        lblTailleVignettes.setLayoutY(160 + basImages);
        SLTailleVignettes = new Slider(50, 300, 120);
        SLTailleVignettes.setLayoutX(200);
        SLTailleVignettes.setLayoutY(160 + basImages);
        Label lblOpaciteVignettes = new Label(rb.getString("interface.opaciteVignettes"));
        lblOpaciteVignettes.setLayoutX(10);
        lblOpaciteVignettes.setLayoutY(190 + basImages);
        SLOpaciteVignettes = new Slider(0, 1.0, 0.8);
        SLOpaciteVignettes.setLayoutX(200);
        SLOpaciteVignettes.setLayoutY(190 + basImages);

        APVignettes.getChildren().addAll(
                lblTailleVignettes, SLTailleVignettes,
                lblOpaciteVignettes, SLOpaciteVignettes
        );
        APVignettes.setPrefHeight(0);
        APVignettes.setMaxHeight(0);
        APVignettes.setMinHeight(0);
        APVignettes.setVisible(false);

        lblPanelVignettes.setOnMouseClicked((MouseEvent me) -> {
            if (APVignettes.isVisible()) {
                IVBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APVignettes.setPrefHeight(0);
                APVignettes.setMaxHeight(0);
                APVignettes.setMinHeight(0);
                APVignettes.setVisible(false);
            } else {
                IVBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APVignettes.setPrefHeight(taillePanelVignettes);
                APVignettes.setMaxHeight(taillePanelVignettes);
                APVignettes.setMinHeight(taillePanelVignettes);
                APVignettes.setVisible(true);
            }
        });
        IVBtnPlusVignettes.setOnMouseClicked((MouseEvent me) -> {
            if (APVignettes.isVisible()) {
                IVBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                APVignettes.setPrefHeight(0);
                APVignettes.setMaxHeight(0);
                APVignettes.setMinHeight(0);
                APVignettes.setVisible(false);
            } else {
                IVBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                APVignettes.setPrefHeight(taillePanelVignettes);
                APVignettes.setMaxHeight(taillePanelVignettes);
                APVignettes.setMinHeight(taillePanelVignettes);
                APVignettes.setVisible(true);
            }
        });

        /*
         * *****************************************************
         * Style des Pannels
         * *****************************************************
         */
        String styleAP = "-fx-background-color : #ccc;";
        APBoussole.setStyle(styleAP);
        APBarreModif.setStyle(styleAP);
        APTitre.setStyle(styleAP);
        APHotSpots.setStyle(styleAP);
        APMasque.setStyle(styleAP);
        APReseauxSociaux.setStyle(styleAP);
        APBoussole.setLayoutX(20);
        APBarreModif.setLayoutX(20);
        APTitre.setLayoutX(20);
        APHotSpots.setLayoutX(20);
        APMasque.setLayoutX(20);
        APReseauxSociaux.setLayoutX(20);
        /*
         * *******************************************************
         *     Ajout des Elements dans les Pannels
         * *******************************************************
         */
        APTIT.getChildren().addAll(APTitre, lblPanelTitre, IVBtnPlusTitre);
        APBB.getChildren().addAll(APBarreModif, lblBarreBouton, IVBtnPlus);
        APHS.getChildren().addAll(lblChoixHS, IVBtnPlusHS, APHotSpots);
        APBOUSS.getChildren().addAll(APBoussole, lblPanelBoussole, IVBtnPlusBouss);
        APMASQ.getChildren().addAll(APMasque, lblPanelMasque, IVBtnPlusMasque);
        APRS.getChildren().addAll(APReseauxSociaux, lblPanelReseauxSociaux, IVBtnPlusReseauxSociaux);
        APVIG.getChildren().addAll(APVignettes, lblPanelVignettes, IVBtnPlusVignettes);

        /*
         * ******************************************************
         *     Ajouts des pannels dans la barre d'outils
         * ******************************************************
         */
        VBOutils.getChildren().addAll(
                APCoulTheme, APTIT, APBB, APHS, APBOUSS, APMASQ, APRS, APVIG
        );

        /*
         * *******************************************************
         *     Ajout des ecouteurs sur les différents éléments
         * ******************************************************
         */
        CBlisteStyle.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    APVisualisation.getChildren().remove(HBbarreBoutons);
                    APVisualisation.getChildren().remove(IVHotSpot);
                    styleBarre = t1;
                    afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
                }
            }
        });

        dXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            dXBarre = new_value.doubleValue();
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        dYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            APVisualisation.getChildren().remove(HBbarreBoutons);
            APVisualisation.getChildren().remove(IVHotSpot);
            dYBarre = new_value.doubleValue();
            afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
        });
        grpImage.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpImage.getSelectedToggle() != null) {
                switch (grpImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        IMVisualisation.setImage(imageClaire);
                        break;
                    case "sombre":
                        IMVisualisation.setImage(imageSombre);
                        break;
                }
            }
        });
        /*
         Barre de boutons
         */
        grpPostBarre.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostBarre.getSelectedToggle() != null) {
                APVisualisation.getChildren().remove(HBbarreBoutons);
                APVisualisation.getChildren().remove(IVHotSpot);
                positionBarre = grpPostBarre.getSelectedToggle().getUserData().toString();
                afficheBouton(positionBarre, dXBarre, dYBarre, tailleBarre, styleBarre, styleHotSpots);
            }
        });
        /*
         Boussole
         */
        grpPostBouss.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostBouss.getSelectedToggle() != null) {
                positionBoussole = grpPostBouss.getSelectedToggle().getUserData().toString();
                afficheBoussole();
            }
        });
        boussDXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dXBoussole = new_value.doubleValue();
            afficheBoussole();
        });
        boussDYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dYBoussole = new_value.doubleValue();
            afficheBoussole();
        });
        SLTailleBoussole.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleBoussole = taille;
                afficheBoussole();
            }
        });
        /*
         Bouton de Masquage
         */
        SLOpaciteBoussole.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteBoussole = opac;
                afficheBoussole();
            }
        });

        grpPostMasque.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostMasque.getSelectedToggle() != null) {
                positionMasque = grpPostMasque.getSelectedToggle().getUserData().toString();
                afficheMasque();
            }
        });
        masqueDXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dXMasque = new_value.doubleValue();
            afficheMasque();
        });
        masqueDYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dYMasque = new_value.doubleValue();
            afficheMasque();
        });
        SLTailleMasque.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleMasque = taille;
                afficheMasque();
            }
        });
        SLOpaciteMasque.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteMasque = opac;
                afficheMasque();
            }
        });
        /*
         Reseaux Sociaux
         */
        reseauxSociauxDXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dXReseauxSociaux = new_value.doubleValue();
            afficheReseauxSociaux();
        });
        reseauxSociauxDYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dYReseauxSociaux = new_value.doubleValue();
            afficheReseauxSociaux();
        });

        grpPostReseauxSociaux.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostReseauxSociaux.getSelectedToggle() != null) {
                positionReseauxSociaux = grpPostReseauxSociaux.getSelectedToggle().getUserData().toString();
                afficheReseauxSociaux();
            }
        });
        SLTailleReseauxSociaux.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleReseauxSociaux = taille;
                afficheReseauxSociaux();
            }
        });
        SLOpaciteReseauxSociaux.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteReseauxSociaux = opac;
                afficheReseauxSociaux();
            }
        });

        grpPostVignettes.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPostVignettes.getSelectedToggle() != null) {
                positionVignettes = grpPostVignettes.getSelectedToggle().getUserData().toString();
                afficheVignettes();
            }
        });
        SLTailleVignettes.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleImageVignettes = taille;
                afficheVignettes();
            }
        });
        SLOpaciteVignettes.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteVignettes = opac;
                afficheVignettes();
            }
        });

    }

}
