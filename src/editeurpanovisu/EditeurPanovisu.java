/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import impl.org.controlsfx.i18n.Localization;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.imaging.ImageReadException;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * Editeur de visites virtuelles
 *
 * @author LANG Laurent
 */
public class EditeurPanovisu extends Application {

    private static class AncreForme extends Circle {

        private final DoubleProperty x, y;

        AncreForme(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 2);
            //System.out.println("anchor ==>" + x.get() + "," + y.get());
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            setCursor(Cursor.HAND);

            this.x = x;
            this.y = y;

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        private void enableDrag() {
            final Delta dragDelta = new Delta();

            setOnMousePressed((MouseEvent mouseEvent) -> {
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            });

            setOnMouseReleased((MouseEvent mouseEvent) -> {
                getScene().setCursor(Cursor.HAND);
            });

            setOnMouseDragged((MouseEvent mouseEvent) -> {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setCenterY(newY);
                }
            });

            setOnMouseEntered((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            });

            setOnMouseExited((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            });
        }

        private class Delta {

            double x, y;
        }
    }

    /**
     * Définition de la langue locale par défaut fr_FR
     */
    public static String strStyleCSS = "clair";
    public static final String[] strCodesLanguesTraduction = {"fr_FR", "en_EN", "de_DE"};
    public static final String[] strLanguesTraduction = {"Francais", "English", "Deutsch"};
    static public String strCurrentDir;
    public static Locale locale = new Locale("fr", "FR");
    private static ResourceBundle rbLocalisation;
    private static Label lblDragDrop;
    static private PopUpDialogController popUp;
    static private ImageView ivImagePanoramique;
    private Image imgPanoRetaille2;
    static private Label lblLong, lblLat;
    static private AnchorPane apPanneauPrincipal;
    static private HBox hbCoordonnees;
    static private int iNumPoints = 0;
    static private int iNumImages = 0;
    static private int iNumHTML = 0;
    static public Panoramique[] panoramiquesProjet = new Panoramique[100];
    static public Plan[] plans = new Plan[20];
    static public int iNombrePanoramiques = 0;
    static public int iNombrePanoramiquesFichier = 0;
    static private ProgressBar pbarAvanceChargement;
    static private MenuBar mbarPrincipal;
    static private HBox hbBarreBouton;
    static public int iNombrePlans = 0;
    static private int iPanoActuel = 0;
    static private File fileProjet;
    static public String strPanoListeVignette;
    static private Pane panePanoramique;
    static private VBox vbChoixPanoramique;
    static private VBox vbOutils;
    static private TabPane tpEnvironnement;
    static private Tab tabVisite;
    public AnchorPane apAttends;
    static private Label lblCharge;
    static private Label lblNiveaux;
    static private Tab tabInterface;
    static public Tab tabPlan;
    static private Scene scnPrincipale;
    static private ScrollPane spVuePanoramique;
    static private ScrollPane spPanneauOutils;
    static private double largeurMax;
    static private boolean bEstCharge = false;
    static private boolean bRepertSauveChoisi = false;
    static private String strPanoEntree = "";
    static public String strSystemeExploitation;
    public static final String strTooltipStyle = "";
    static private boolean bDragDrop = false;
    static private String strTitreVisite = "";
    /*
     Panel Creation Barre Navigation
     */
    private static AnchorPane apCreationBarre;
    static private AnchorPane apEnvironnement;
    static private AnchorPane apImgBarrePersonnalisee;
    static private AnchorPane apZoneBarrePersonnalisee;
    private static Button btnAjouteZone;
    static private Image imgBarrePersonnalisee;
    private static boolean bRecommenceZone = false;
    static private int iNombreZones;
    private static int iNombrePointsZone;
    private static double x1Zone, y1Zone;
    static private String strRepertBarrePersonnalisee;
    private static String strTypeZone;
    private static String strNomFichierShp;
    private static final ZoneTelecommande[] zones = new ZoneTelecommande[25];
    private static final double[] pointsPolyZone = new double[200];
    private static final String[] strTouchesBarre = {
        "Haut/Up", "Droite/Right", "Bas/Down", "Gauche/Left",
        "Zoom +", "Zoom -", "Info", "Aide/Help",
        "Rotation", "mode Souris/Mouse", "Plein Ecran/Fullscreen",
        "Lien/Link 1","Lien/Link 2",
        "zone 1", "zone 2", "zone 3", "zone 4", "zone 5",
        "zone 6", "zone 7", "zone 8", "zone 9", "zone 10"
    };
    private static final String[] strCodeBarre = {
        "telUp", "telRight", "telDown", "telLeft",
        "telZoomPlus", "telZoomMoins", "telInfo", "telAide",
        "telRotation", "telSouris", "telFS",
        "telLien-1","telLien-2",
        "area-1", "area-2", "area-3", "area-4", "area-5",
        "area-6", "area-7", "area-8", "area-9", "area-10"
    };

    /**
     * Répertoires de l'application
     */
    static public String strRepertAppli;
    static public String strRepertTemp;
    static private String strRepertPanos;
    static private String strRepertHSImages = "";
    /**
     * Répertoire de sauvegarde du projet
     */
    static public String strRepertoireProjet;
    /**
     * Répertoire du fichier de configuration
     */
    static public File fileRepertConfig;

    final static private ComboBox cbListeChoixPanoramique = new ComboBox();
    final static private ComboBox cbListeChoixPanoramiqueEntree = new ComboBox();
    static private Label lblChoixPanoramique;
    static private boolean bPanoCharge = false;
    static private String strPanoAffiche = "";
    static public boolean bDejaSauve = true;
    static public Stage stPrincipal;
    private static final String[] strHistoFichiers = new String[10];
    static private int nombreHistoFichiers = 0;
    static private File fileHistoFichiers;
    private String strTexteHisto;
    private static String strNumVersion;
    private static int iHauteurInterface;
    private static int iLargeurInterface;

    static public GestionnaireInterfaceController gestionnaireInterface = new GestionnaireInterfaceController();
    static public GestionnairePlanController gestionnairePlan = new GestionnairePlanController();

    static private Menu mnuDerniersProjets;
    private Menu mnuPanoramique;
    private Menu mnuTransformation;
    private Menu mnuModeles;
    private MenuItem mniCube2EquiTransformation;
    private MenuItem mniEqui2CubeTransformation;
    private MenuItem mniOutilsBarre;
    private MenuItem mniConfigTransformation;
    private MenuItem mniChargerModele;
    private MenuItem mniSauverModele;
    private MenuItem mniAPropos;
    private MenuItem mniAide;

    private MenuItem mniNouveauProjet;
    private MenuItem mniSauveProjet;
    private MenuItem mniAjouterPano;
    public static MenuItem mniAjouterPlan;

    private MenuItem mniFermerProjet;

    private MenuItem mniSauveSousProjet;
    private MenuItem mniVisiteGenere;
    private MenuItem mniChargeProjet;

    private MenuItem mniAffichageVisite;
    private MenuItem mniAffichageInterface;
    public static MenuItem mniAffichagePlan;

    private ImageView ivNouveauProjet;
    private ImageView ivSauveProjet;
    private ImageView ivChargeProjet;
    private ImageView ivVisiteGenere;
    private ImageView ivAjouterPano;
    public static ImageView ivAjouterPlan;
    private ImageView ivCube2Equi;
    private ImageView ivEqui2Cube;

    private void genereVisite() throws IOException {
        if (!bRepertSauveChoisi) {
            strRepertoireProjet = strCurrentDir;
        }
        if (!bDejaSauve) {
            projetSauve();
        }
        if (bDejaSauve) {
            deleteDirectory(strRepertTemp + "/panovisu/images");
            File imagesRepert = new File(strRepertTemp + "/panovisu/images");
            if (!imagesRepert.exists()) {
                imagesRepert.mkdirs();
            }
            File boutonRepert = new File(strRepertTemp + "/panovisu/images/navigation");
            if (!boutonRepert.exists()) {
                boutonRepert.mkdirs();
            }
            File boussoleRepert = new File(strRepertTemp + "/panovisu/images/boussoles");
            if (!boussoleRepert.exists()) {
                boussoleRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/boussoles", boussoleRepert.getAbsolutePath());
            File planRepert = new File(strRepertTemp + "/panovisu/images/plan");
            if (!planRepert.exists()) {
                planRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/plan", planRepert.getAbsolutePath());
            File reseauRepert = new File(strRepertTemp + "/panovisu/images/reseaux");
            if (!reseauRepert.exists()) {
                reseauRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/reseaux", reseauRepert.getAbsolutePath());
            File interfaceRepert = new File(strRepertTemp + "/panovisu/images/interface");
            if (!interfaceRepert.exists()) {
                interfaceRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/interface", interfaceRepert.getAbsolutePath());
            File MARepert = new File(strRepertTemp + "/panovisu/images/MA");
            if (!MARepert.exists()) {
                MARepert.mkdirs();
            }
            File hotspotsRepert = new File(strRepertTemp + "/panovisu/images/hotspots");
            if (!hotspotsRepert.exists()) {
                hotspotsRepert.mkdirs();
            }
            if (gestionnaireInterface.strVisibiliteBarrePersonnalisee.equals("oui")) {
                File telecommandeRepert = new File(strRepertTemp + "/panovisu/images/telecommande");
                if (!telecommandeRepert.exists()) {
                    telecommandeRepert.mkdirs();
                }
                ReadWriteImage.writePng(gestionnaireInterface.wiBarrePersonnaliseeCouleur,
                        strRepertTemp + "/panovisu/images/telecommande" + File.separator + "telecommande.png",
                        false, 0.f);
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "fs.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "fs2.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "souris.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "souris2.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "aide.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "info.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "rotation.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "zoomin.png",
                        strRepertTemp + "/panovisu/images/telecommande");
                copieFichierRepertoire(strRepertAppli + "/theme/telecommandes" + File.separator + "zoomout.png",
                        strRepertTemp + "/panovisu/images/telecommande");
            }
            copieFichierRepertoire(strRepertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "aide_souris.png",
                    strRepertTemp + "/panovisu/images");
            copieFichierRepertoire(strRepertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "fermer.png",
                    strRepertTemp + "/panovisu/images");
            copieFichierRepertoire(strRepertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "precedent.png",
                    strRepertTemp + "/panovisu/images");
            copieFichierRepertoire(strRepertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "suivant.png",
                    strRepertTemp + "/panovisu/images");
            for (int i = 0; i < gestionnaireInterface.iNombreImagesBouton - 1; i++) {
                ReadWriteImage.writePng(gestionnaireInterface.wiNouveauxBoutons[i],
                        boutonRepert.getAbsolutePath() + File.separator + gestionnaireInterface.nomImagesBoutons[i],
                        false, 0.f);
            }
            ReadWriteImage.writePng(gestionnaireInterface.wiNouveauxBoutons[gestionnaireInterface.iNombreImagesBouton - 1],
                    hotspotsRepert.getAbsolutePath() + File.separator + "hotspot.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.wiNouveauxBoutons[gestionnaireInterface.iNombreImagesBouton],
                    hotspotsRepert.getAbsolutePath() + File.separator + "hotspotImage.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.wiNouveauxMasque,
                    MARepert.getAbsolutePath() + File.separator + "MA.png",
                    false, 0.f);

            File xmlRepert = new File(strRepertTemp + File.separator + "xml");
            if (!xmlRepert.exists()) {
                xmlRepert.mkdirs();
            }
            File cssRepert = new File(strRepertTemp + File.separator + "css");
            if (!cssRepert.exists()) {
                cssRepert.mkdirs();
            }
            File jsRepert = new File(strRepertTemp + File.separator + "js");
            if (!jsRepert.exists()) {
                jsRepert.mkdirs();
            }
            String contenuFichier;
            File xmlFile;
            String chargeImages = "";

            for (int i = 0; i < iNombrePanoramiques; i++) {
                String fPano = "panos/" + panoramiquesProjet[i].getNomFichier().substring(panoramiquesProjet[i].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[i].getNomFichier().length()).split("\\.")[0];
                if (panoramiquesProjet[i].getTypePanoramique().equals(Panoramique.CUBE)) {
                    fPano = fPano.substring(0, fPano.length() - 2);
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_f.jpg\"\n";
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_b.jpg\"\n";
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_u.jpg\"\n";
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_d.jpg\"\n";
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_r.jpg\"\n";
                    chargeImages += "                images[" + i + "]=\"" + fPano + "_l.jpg\"\n";
                } else {
                    chargeImages += "                images[" + i + "]=\"" + fPano + ".jpg\"\n";
                }
                String affInfo = (panoramiquesProjet[i].isAfficheInfo()) ? "oui" : "non";
                String affTitre = (gestionnaireInterface.bAfficheTitre) ? "oui" : "non";
                double regX;
                double zN;
                if (panoramiquesProjet[i].getTypePanoramique().equals(Panoramique.SPHERE)) {
                    regX = Math.round(((panoramiquesProjet[i].getLookAtX() - 180) % 360) * 10) / 10;
                    zN = Math.round(((panoramiquesProjet[i].getZeroNord() - 180) % 360) * 10) / 10;
                } else {
                    regX = Math.round(((panoramiquesProjet[i].getLookAtX() + 90) % 360) * 10) / 10;
                    zN = Math.round(((panoramiquesProjet[i].getZeroNord() + 90) % 360) * 10) / 10;
                }
                int rouge = (int) (Color.valueOf(gestionnaireInterface.strCouleurDiaporama).getRed() * 255.d);
                int bleu = (int) (Color.valueOf(gestionnaireInterface.strCouleurDiaporama).getBlue() * 255.d);
                int vert = (int) (Color.valueOf(gestionnaireInterface.strCouleurDiaporama).getGreen() * 255.d);
                String coulDiapo = "rgba(" + rouge + "," + vert + "," + bleu + "," + gestionnaireInterface.diaporamaOpacite + ")";

                Color coulTitre = Color.valueOf(gestionnaireInterface.strCouleurFondTitre);
                rouge = (int) (coulTitre.getRed() * 255.d);
                bleu = (int) (coulTitre.getBlue() * 255.d);
                vert = (int) (coulTitre.getGreen() * 255.d);
                String coulFondTitre = "rgba(" + rouge + "," + vert + "," + bleu + "," + gestionnaireInterface.titreOpacite + ")";

                contenuFichier = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!--\n"
                        + "     Visite générée par l'éditeur panoVisu \n"
                        + "\n"
                        + "             Création L.LANG      le monde à 360°  : http://lemondea360.fr\n"
                        + "-->\n"
                        + "\n"
                        + "\n"
                        + "<scene>\n"
                        + "   <pano \n"
                        + "      image=\"" + fPano + "\"\n"
                        + "      titre=\"" + panoramiquesProjet[i].getTitrePanoramique() + "\"\n"
                        + "      titrePolice=\"" + gestionnaireInterface.strTitrePoliceNom + "\"\n"
                        + "      titreTaillePolice=\"" + Math.round(Double.parseDouble(gestionnaireInterface.strTitrePoliceTaille)) + "px\"\n"
                        + "      titreTaille=\"" + Math.round(gestionnaireInterface.titreTaille) + "%\"\n"
                        + "      titreFond=\"" + coulFondTitre + "\"\n"
                        + "      titreCouleur=\"" + gestionnaireInterface.strCouleurTitre + "\"\n"
                        //                        + "      titreOpacite=\"" + gestionnaireInterface.titreOpacite + "\"\n"
                        + "      diaporamaCouleur=\"" + coulDiapo + "\"\n"
                        + "      type=\"" + panoramiquesProjet[i].getTypePanoramique() + "\"\n"
                        + "      multiReso=\"oui\"\n"
                        + "      nombreNiveaux=\"" + panoramiquesProjet[i].getNombreNiveaux() + "\"\n"
                        + "      zeroNord=\"" + zN + "\"\n"
                        + "      regardX=\"" + regX + "\"\n"
                        + "      regardY=\"" + Math.round(panoramiquesProjet[i].getLookAtY() * 10) / 10 + "\"\n"
                        + "      affinfo=\"" + affInfo + "\"\n"
                        + "      afftitre=\"" + affTitre + "\"\n"
                        + "   />\n"
                        + "   <!--Définition de la Barre de navigation-->\n"
                        + "   <boutons \n"
                        + "      styleBoutons=\"navigation\"\n"
                        + "      couleur=\"rgba(255,255,255,0)\"\n"
                        + "      bordure=\"rgba(255,255,255,0)\"\n"
                        + "      deplacements=\"" + gestionnaireInterface.strDeplacementsBarreClassique + "\" \n"
                        + "      zoom=\"" + gestionnaireInterface.strZoomBarreClassique + "\" \n"
                        + "      outils=\"" + gestionnaireInterface.strOutilsBarreClassique + "\"\n"
                        + "      fs=\"" + gestionnaireInterface.strPleinEcranBarreClassique + "\" \n"
                        + "      souris=\"" + gestionnaireInterface.strSourisBarreClassique + "\" \n"
                        + "      rotation=\"" + gestionnaireInterface.strRotationBarreClassique + "\" \n"
                        + "      positionX=\"" + gestionnaireInterface.strPositionBarreClassique.split(":")[1] + "\"\n"
                        + "      positionY=\"" + gestionnaireInterface.strPositionBarreClassique.split(":")[0] + "\" \n"
                        + "      dX=\"" + gestionnaireInterface.offsetXBarreClassique + "\" \n"
                        + "      dY=\"" + gestionnaireInterface.offsetYBarreClassique + "\"\n"
                        + "      espacement=\"" + Math.round(gestionnaireInterface.espacementBarreClassique) + "\"\n"
                        + "      visible=\"" + gestionnaireInterface.strVisibiliteBarreClassique + "\"\n"
                        + "   />\n";
                if (gestionnaireInterface.strVisibiliteBarrePersonnalisee.equals("oui")) {
                    contenuFichier += "<!--  Barre de Navigation Personnalisée -->\n\n"
                            + "    <telecommande\n"
                            + "        fs=\"" + gestionnaireInterface.strPleinEcranBarrePersonnalisee + "\" \n"
                            + "        souris=\"" + gestionnaireInterface.strSourisBarrePersonnalisee + "\" \n"
                            + "        rotation=\"" + gestionnaireInterface.strRotationBarrePersonnalisee + "\" \n"
                            + "        info=\"" + gestionnaireInterface.strInfoBarrePersonnalisee + "\"\n"
                            + "        aide=\"" + gestionnaireInterface.strAideBarrePersonnalisee + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.strPositionBarrePersonnalisee.split(":")[1] + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.strPositionBarrePersonnalisee.split(":")[0] + "\" \n"
                            + "        taille=\"" + gestionnaireInterface.tailleBarrePersonnalisee + "\"\n"
                            + "        tailleBouton=\"" + gestionnaireInterface.tailleIconesBarrePersonnalisee + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.offsetXBarrePersonnalisee + "\" \n"
                            + "        dY=\"" + gestionnaireInterface.offsetYBarrePersonnalisee + "\"\n"
                            + "        lien1=\"" + gestionnaireInterface.strLien1BarrePersonnalisee + "\"\n"
                            + "        lien2=\"" + gestionnaireInterface.strLien2BarrePersonnalisee + "\"\n"
                            + "        visible=\"oui\">\n";
                    for (int ij = 0; ij < gestionnaireInterface.iNombreZonesBarrePersonnalisee; ij++) {
                        contenuFichier += "        <zoneNavPerso "
                                + "id=\"" + gestionnaireInterface.zonesBarrePersonnalisee[ij].getStrIdZone() + "\" "
                                + "alt=\"\" title=\"\" "
                                + "shape=\"" + gestionnaireInterface.zonesBarrePersonnalisee[ij].getStrTypeZone() + "\" "
                                + "coords=\"" + gestionnaireInterface.zonesBarrePersonnalisee[ij].getStrCoordonneesZone() + "\""
                                + " />\n";
                    }
                    contenuFichier += "    </telecommande>\n"
                            + "";
                }
                if (gestionnaireInterface.bAfficheBoussole) {
                    String SAiguille = (gestionnaireInterface.bAiguilleMobileBoussole) ? "oui" : "non";
                    contenuFichier += "<!--  Boussole -->\n"
                            + "    <boussole \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + gestionnaireInterface.strImageBoussole + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.tailleBoussole + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.strPositionBoussole.split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.strPositionBoussole.split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.opaciteBoussole + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.offsetXBoussole + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.offsetYBoussole + "\"\n"
                            + "        aiguille=\"" + SAiguille + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bFenetreInfoPersonnalise) {
                    String strImgInfoURL = "images/" + gestionnaireInterface.strFenetreInfoImage.substring(
                            gestionnaireInterface.strFenetreInfoImage.lastIndexOf(File.separator) + 1,
                            gestionnaireInterface.strFenetreInfoImage.length());
                    contenuFichier += "<!--  Fenêtre info personnalisée -->\n"
                            + "    <fenetreInfo \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgInfoURL + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.fenetreInfoTaille + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.fenetreInfoOpacite + "\"\n"
                            + "        dX=\"" + Math.round(gestionnaireInterface.fenetreInfoPosX * 10) / 10 + "\"\n"
                            + "        dY=\"" + Math.round(gestionnaireInterface.fenetreInfoPosY * 10) / 10 + "\"\n"
                            + "        URL=\"" + gestionnaireInterface.strFenetreURL + "\"\n"
                            + "        texteURL=\"" + gestionnaireInterface.strFenetreTexteURL + "\"\n"
                            + "        couleurURL=\"" + gestionnaireInterface.strFenetreURLCouleur + "\"\n"
                            + "        TailleURL=\"" + Math.round(gestionnaireInterface.fenetrePoliceTaille * 10) / 10 + "\"\n"
                            + "        URLdX=\"" + Math.round(gestionnaireInterface.fenetreURLPosX * 10) / 10 + "\"\n"
                            + "        URLdY=\"" + Math.round(gestionnaireInterface.fenetreURLPosY * 10) / 10 + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bFenetreAidePersonnalise) {
                    String strImgAideURL = "images/" + gestionnaireInterface.strFenetreAideImage.substring(
                            gestionnaireInterface.strFenetreAideImage.lastIndexOf(File.separator) + 1,
                            gestionnaireInterface.strFenetreAideImage.length());
                    contenuFichier += "<!--  Fenêtre Aide personnalisée -->\n"
                            + "    <fenetreAide \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgAideURL + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.fenetreAideTaille + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.fenetreAideOpacite + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.fenetreAidePosX + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.fenetreAidePosY + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bAfficheMenuContextuel) {
                    String SPrecSuiv = (gestionnaireInterface.bAffichePrecSuivMC) ? "oui" : "non";
                    String SPlanet = (gestionnaireInterface.bAffichePlanetNormalMC) ? "oui" : "non";
                    String SPers1 = (gestionnaireInterface.bAffichePersMC1) ? "oui" : "non";
                    String SPers2 = (gestionnaireInterface.bAffichePersMC2) ? "oui" : "non";
                    contenuFichier += "<!--  MenuContextuel -->\n"
                            + "    <menuContextuel \n"
                            + "        affiche=\"oui\"\n"
                            + "        precSuiv=\"" + SPrecSuiv + "\"\n"
                            + "        planete=\"" + SPlanet + "\"\n"
                            + "        pers1=\"" + SPers1 + "\"\n"
                            + "        lib1=\"" + gestionnaireInterface.strPersLib1 + "\"\n"
                            + "        url1=\"" + gestionnaireInterface.strPersURL1 + "\"\n"
                            + "        pers2=\"" + SPers2 + "\"\n"
                            + "        lib2=\"" + gestionnaireInterface.strPersLib2 + "\"\n"
                            + "        url2=\"" + gestionnaireInterface.strPersURL2 + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bSuivantPrecedent) {

                    int panoPrecedent = (i == iNombrePanoramiques - 1) ? 0 : i + 1;
                    int panoSuivant = (i == 0) ? iNombrePanoramiques - 1 : i - 1;
                    String nomPano = panoramiquesProjet[panoSuivant].getNomFichier();
                    String strPanoSuivant = "xml/" + nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                    nomPano = panoramiquesProjet[panoPrecedent].getNomFichier();
                    String strPanoPrecedent = "xml/" + nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                    contenuFichier += "<!--  Bouton Suivant Precedent -->\n"
                            + "    <suivantPrecedent\n"
                            + "        suivant=\"" + strPanoSuivant + "\"            \n"
                            + "        precedent=\"" + strPanoPrecedent + "\"            \n"
                            + "    />    \n"
                            + "";
                }
                if (gestionnaireInterface.bAfficheMasque) {
                    String SNavigation = (gestionnaireInterface.bMasqueNavigation) ? "oui" : "non";
                    String SBoussole = (gestionnaireInterface.bMasqueBoussole) ? "oui" : "non";
                    String STitre = (gestionnaireInterface.bMasqueTitre) ? "oui" : "non";
                    String splan = (gestionnaireInterface.bMasquePlan) ? "oui" : "non";
                    String SReseaux = (gestionnaireInterface.bMasqueReseaux) ? "oui" : "non";
                    String SVignettes = (gestionnaireInterface.bMasqueVignettes) ? "oui" : "non";
                    contenuFichier += "<!--  Bouton de Masquage -->\n"
                            + "    <marcheArret \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"MA.png\"\n"
                            + "        taille=\"" + gestionnaireInterface.tailleMasque + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.positionMasque.split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.positionMasque.split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.opaciteMasque + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.dXMasque + "\"\n"
                            + "        dy=\"" + gestionnaireInterface.dYMasque + "\"\n"
                            + "        navigation=\"" + SNavigation + "\"\n"
                            + "        boussole=\"" + SBoussole + "\"\n"
                            + "        titre=\"" + STitre + "\"\n"
                            + "        plan=\"" + splan + "\"\n"
                            + "        reseaux=\"" + SReseaux + "\"\n"
                            + "        vignettes=\"" + SVignettes + "\"\n"
                            + "    />\n";
                }

                if (gestionnaireInterface.bAfficheReseauxSociaux) {
                    String STwitter = (gestionnaireInterface.bReseauxSociauxTwitter) ? "oui" : "non";
                    String SGoogle = (gestionnaireInterface.bReseauxSociauxGoogle) ? "oui" : "non";
                    String SFacebook = (gestionnaireInterface.bReseauxSociauxFacebook) ? "oui" : "non";
                    String SEmail = (gestionnaireInterface.bReseauxSociauxEmail) ? "oui" : "non";
                    contenuFichier += "<!--  Réseaux Sociaux -->\n"
                            + "    <reseauxSociaux \n"
                            + "        affiche=\"oui\"\n"
                            + "        taille=\"" + gestionnaireInterface.tailleReseauxSociaux + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.positionReseauxSociaux.split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.positionReseauxSociaux.split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.opaciteReseauxSociaux + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.dXReseauxSociaux + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.dYReseauxSociaux + "\"\n"
                            + "        twitter=\"" + STwitter + "\"\n"
                            + "        google=\"" + SGoogle + "\"\n"
                            + "        facebook=\"" + SFacebook + "\"\n"
                            + "        email=\"" + SEmail + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bAfficheVignettes) {
                    String SAfficheVignettes = (gestionnaireInterface.bAfficheVignettes) ? "oui" : "non";
                    contenuFichier += "<!-- Barre des vignettes -->"
                            + "    <vignettes \n"
                            + "        affiche=\"" + SAfficheVignettes + "\"\n"
                            + "        tailleImage=\"" + gestionnaireInterface.tailleImageVignettes + "\"\n"
                            + "        fondCouleur=\"" + gestionnaireInterface.couleurFondVignettes + "\"\n"
                            + "        texteCouleur=\"" + gestionnaireInterface.couleurTexteVignettes + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.opaciteVignettes + "\"\n"
                            + "        position=\"" + gestionnaireInterface.positionVignettes + "\"\n"
                            + "    >\n";
                    for (int j = 0; j < iNombrePanoramiques; j++) {
                        String nomPano = panoramiquesProjet[j].getNomFichier();
                        String nFichier = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String nXML = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getVignettePanoramique(),
                                strRepertTemp + "/panos/" + nFichier, 1.0f, false, 0.0f);
                        contenuFichier
                                += "        <imageVignette \n"
                                + "            image=\"panos/" + nFichier + "\"\n"
                                + "            xml=\"xml/" + nXML + "\"\n"
                                + "        />\n";
                    }
                    contenuFichier
                            += "    </vignettes>       \n"
                            + "";

                }
                if (gestionnaireInterface.bAfficheComboMenu) {
                    String SAfficheComboMenu = (gestionnaireInterface.bAfficheComboMenu) ? "oui" : "non";
                    TextField titreV = (TextField) vbChoixPanoramique.lookup("#titreVisite");
                    String titreVis = titreV.getText();
                    contenuFichier += "<!-- Barre des comboMenu -->"
                            + "    <comboMenu \n"
                            + "        affiche=\"" + SAfficheComboMenu + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.positionXComboMenu + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.positionYComboMenu + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.offsetXComboMenu + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.offsetYComboMenu + "\"\n"
                            + "    >\n";
                    for (int j = 0; j < iNombrePanoramiques; j++) {
                        String nomPano = panoramiquesProjet[j].getNomFichier();
                        String nFichier = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String nXML = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getVignettePanoramique(),
                                strRepertTemp + "/panos/" + nFichier, 1.0f, false, 0.0f);

                        contenuFichier
                                += "        <imageComboMenu \n";
                        if (gestionnaireInterface.bAfficheComboMenuImages) {
                            contenuFichier
                                    += "            image=\"panos/" + nFichier + "\"\n";
                        }
                        if (i == j) {
                            contenuFichier
                                    += "            selectionne=\"selected\"\n";
                        }
                        contenuFichier
                                += "            xml=\"xml/" + nXML + "\"\n"
                                + "            titre=\"" + panoramiquesProjet[j].getTitrePanoramique() + "\"\n"
                                + "            sousTitre=\"" + titreVis + "\"\n"
                                + "        />\n";
                    }
                    contenuFichier
                            += "    </comboMenu>       \n"
                            + "";

                }

                contenuFichier += "    <!--Définition des hotspots-->  \n"
                        + "   <hotspots>\n";
                for (int j = 0; j < panoramiquesProjet[i].getNombreHotspots(); j++) {
                    HotSpot HS = panoramiquesProjet[i].getHotspot(j);
                    double longit;
                    if (panoramiquesProjet[i].getTypePanoramique().equals(Panoramique.SPHERE)) {
                        longit = HS.getLongitude() - 180;
                    } else {
                        longit = HS.getLongitude() + 90;
                    }
                    String txtAnime = (HS.isAnime()) ? "true" : "false";
                    contenuFichier
                            += "      <point \n"
                            + "           type=\"panoramique\"\n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspot.png\"\n"
                            + "           xml=\"xml/" + HS.getFichierXML() + "\"\n"
                            + "           info=\"" + HS.getInfo() + "\"\n"
                            + "           anime=\"" + txtAnime + "\"\n"
                            + "      />\n";
                }
                for (int j = 0; j < panoramiquesProjet[i].getNombreHotspotImage(); j++) {
                    HotspotImage HS = panoramiquesProjet[i].getHotspotImage(j);
                    double longit;
                    if (panoramiquesProjet[i].getTypePanoramique().equals(Panoramique.SPHERE)) {
                        longit = HS.getLongitude() - 180;
                    } else {
                        longit = HS.getLongitude() + 90;
                    }
                    String txtAnime = (HS.isAnime()) ? "true" : "false";
                    contenuFichier
                            += "      <point \n"
                            + "           type=\"image\"\n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspotImage.png\"\n"
                            + "           img=\"images/" + HS.getLienImg() + "\"\n"
                            + "           info=\"" + HS.getInfo() + "\"\n"
                            + "           anime=\"" + txtAnime + "\"\n"
                            + "      />\n";
                }
                contenuFichier += "   </hotspots>\n";
                contenuFichier += "\n"
                        + "<!-- Définition des images de fond -->\n"
                        + "\n";
                if (gestionnaireInterface.iNombreImagesFond > 0) {
                    for (int ii = 0; ii < gestionnaireInterface.iNombreImagesFond; ii++) {
                        ImageFond imgFond = gestionnaireInterface.imagesFond[ii];
                        String strImgFond = "images/" + imgFond.getFichierImage().substring(
                                imgFond.getFichierImage().lastIndexOf(File.separator) + 1,
                                imgFond.getFichierImage().length());
                        String SMasquable = (imgFond.isMasquable()) ? "oui" : "non";

                        contenuFichier += "    <imageFond\n"
                                + "        fichier=\"" + strImgFond + "\"\n"
                                + "        posX=\"" + imgFond.getPosX() + "\" \n"
                                + "        posY=\"" + imgFond.getPosY() + "\" \n"
                                + "        offsetX=\"" + imgFond.getOffsetX() + "\" \n"
                                + "        offsetY=\"" + imgFond.getOffsetY() + "\" \n"
                                + "        tailleX=\"" + imgFond.getTailleX() + "\" \n"
                                + "        tailleY=\"" + imgFond.getTailleY() + "\" \n"
                                + "        opacite=\"" + imgFond.getOpacite() + "\" \n"
                                + "        masquable=\"" + SMasquable + "\" \n"
                                + "        url=\"" + imgFond.getUrl() + "\" \n"
                                + "        infobulle=\"" + imgFond.getInfobulle() + "\" \n"
                                + "    />\n"
                                + "";
                    }
                }
                if (gestionnaireInterface.bAffichePlan && panoramiquesProjet[i].isAffichePlan()) {
                    int numPlan = panoramiquesProjet[i].getNumeroPlan();
                    Plan planPano = plans[numPlan];
                    rouge = (int) (gestionnaireInterface.couleurFondPlan.getRed() * 255.d);
                    bleu = (int) (gestionnaireInterface.couleurFondPlan.getBlue() * 255.d);
                    vert = (int) (gestionnaireInterface.couleurFondPlan.getGreen() * 255.d);
                    String SAfficheRadar = (gestionnaireInterface.bAfficheRadar) ? "oui" : "non";
                    String coulFond = "rgba(" + rouge + "," + vert + "," + bleu + "," + gestionnaireInterface.opacitePlan + ")";
                    contenuFichier
                            += "    <plan\n"
                            + "        affiche=\"oui\" \n"
                            + "        image=\"images/" + planPano.getImagePlan() + "\"\n"
                            + "        largeur=\"" + gestionnaireInterface.largeurPlan + "\"\n"
                            + "        position=\"" + gestionnaireInterface.positionPlan + "\"\n"
                            + "        couleurFond=\"" + coulFond + "\"\n"
                            + "        couleurTexte=\"#" + gestionnaireInterface.txtCouleurTextePlan + "\"\n"
                            + "        nord=\"" + planPano.getDirectionNord() + "\"\n"
                            + "        boussolePosition=\"" + planPano.getPosition() + "\"\n"
                            + "        boussoleX=\"" + planPano.getPositionX() + "\"\n"
                            + "        boussoleY=\"" + planPano.getPositionX() + "\"\n"
                            + "        radarAffiche=\"" + SAfficheRadar + "\"\n"
                            + "        radarTaille=\"" + Math.round(gestionnaireInterface.tailleRadar) + "\"\n"
                            + "        radarCouleurFond=\"#" + gestionnaireInterface.txtCouleurFondRadar + "\"\n"
                            + "        radarCouleurLigne=\"#" + gestionnaireInterface.txtCouleurLigneRadar + "\"\n"
                            + "        radarOpacite=\"" + Math.round(gestionnaireInterface.opaciteRadar * 100.d) / 100.d + "\"\n"
                            + "    >\n";
                    for (int iPoint = 0; iPoint < planPano.getNombreHotspots(); iPoint++) {
                        double posX = planPano.getHotspot(iPoint).getLongitude() * gestionnaireInterface.largeurPlan;
                        double posY = planPano.getHotspot(iPoint).getLatitude()
                                * planPano.getHauteurPlan() * gestionnaireInterface.largeurPlan / planPano.getLargeurPlan();
                        if (planPano.getHotspot(iPoint).getNumeroPano() == i) {
                            contenuFichier
                                    += "        <pointPlan\n"
                                    + "            positX=\"" + posX + "\"\n"
                                    + "            positY=\"" + posY + "\"\n"
                                    + "            xml=\"actif\"\n"
                                    + "            />  \n";

                        } else {
                            contenuFichier
                                    += "        <pointPlan\n"
                                    + "            positX=\"" + posX + "\"\n"
                                    + "            positY=\"" + posY + "\"\n"
                                    + "            xml=\"xml/" + planPano.getHotspot(iPoint).getFichierXML() + "\"\n"
                                    + "            texte=\"" + planPano.getHotspot(iPoint).getInfo() + "\"\n"
                                    + "            />  \n";
                        }
                    }
                    contenuFichier
                            += "    </plan>\n";
                }

                contenuFichier += "</scene>\n";
                String fichierPano = panoramiquesProjet[i].getNomFichier();
                String nomXMLFile = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length()).split("\\.")[0] + ".xml";
                xmlFile = new File(xmlRepert + File.separator + nomXMLFile);
                xmlFile.setWritable(true);
                OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(xmlFile), "UTF-8");
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(contenuFichier);
                }
            }
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int hauteur = (int) tailleEcran.getHeight() - 200;
            String titreVis = "Panovisu - visualiseur 100% html5 (three.js)";
            TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
            if (!tfVisite.getText().equals("")) {
                titreVis = tfVisite.getText() + " - " + titreVis;
            }
            String fPano1 = "panos/niveau0/" + panoramiquesProjet[0].getNomFichier().substring(panoramiquesProjet[0].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getNomFichier().length());

            String fichierHTML = "<!DOCTYPE html>\n"
                    + "<html lang=\"fr\">\n"
                    + "    <head>\n"
                    + "        <title>" + titreVis + "</title>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\">\n"
                    + "        <link rel=\"stylesheet\" media=\"screen\" href=\"panovisu/libs/jqueryMenu/jquery.contextMenu.css\" type=\"text/css\"/>\n"
                    + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"panovisu/css/msdropdown/dd.css\" />\n"
                    + "        <meta property=\"og:title\" content=\"" + titreVis + "\" />\n"
                    + "        <meta property=\"og:description\" content=\"Une page créée avec panoVisu Editeur : 100% Libre 100% HTML5\" />\n"
                    + "        <meta property=\"og:image\" content=\"" + fPano1 + "\" />"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <header>\n"
                    + "\n"
                    + "        </header>\n"
                    + "        <article style=\"height : " + hauteur + "px;\">\n"
                    + "            <div id=\"pano\">\n"
                    + "            </div>\n"
                    + "        </article>\n"
                    + "        <script src=\"panovisu/panovisuInit.js\"></script>\n"
                    + "        <script src=\"panovisu/panovisu.js\"></script>\n"
                    + "        <script>\n"
                    + "\n"
                    + "            $(function() {\n"
                    + "                $(window).resize(function(){\n"
                    + "                    $(\"article\").height($(window).height()-10);\n"
                    + "                    $(\"#pano\").height($(window).height()-10);\n"
                    + "                })\n"
                    + "                $(\"article\").height($(window).height()-10);\n"
                    + "                $(\"#pano\").height($(window).height()-10);\n"
                    + "                ajoutePano({\n"
                    + "                    langue : \"" + locale.toString() + "\",\n"
                    + "                    panoramique: \"pano\",\n"
                    + "                    minFOV: 35,\n"
                    + "                    maxFOV: 120,\n"
                    + "                    fenX: \"100%\",\n"
                    + "                    fenY: \"100%\",\n"
                    + "                    xml: \"xml/PANO.xml\"\n"
                    + "                });\n"
                    + "                $(\".reseauSocial-twitter\").on(\"click\", function() {\n"
                    + "                    window.open(\n"
                    + "                            \"https://twitter.com/share?url=\" + document.location.href\n"
                    + "                            );\n"
                    + "                    return false;\n"
                    + "                });\n"
                    + "                $(\".reseauSocial-fb\").on(\"click\", function() {\n"
                    + "                    window.open(\n"
                    + "                            \"http://www.facebook.com/share.php?u=\" + document.location.href\n"
                    + "                            );\n"
                    + "                    return false;\n"
                    + "                });\n"
                    + "                $(\".reseauSocial-google\").on(\"click\", function() {\n"
                    + "                    window.open(\n"
                    + "                            \"https://plus.google.com/share?url=\" + document.location.href + \"&amp;hl=fr\"\n"
                    + "                            );\n"
                    + "                    return false;\n"
                    + "                });\n"
                    + "                $(\".reseauSocial-email\").attr(\"href\",\"mailto:?body=\" + document.location.href + \"&amp;hl=fr\");\n"
                    //                    + "                images=new Array();\n"
                    //                    + chargeImages
                    //                    + "                prechargeImages(images); \n \n"
                    + "            });\n"
                    + "        </script>\n"
                    + "    </body>\n"
                    + "</html>\n";
            if (strPanoEntree.equals("")) {
                fichierHTML = fichierHTML.replace("PANO",
                        panoramiquesProjet[0].getNomFichier().substring(panoramiquesProjet[0].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getNomFichier().length()).split("\\.")[0]);
            } else {
                fichierHTML = fichierHTML.replace("PANO", strPanoEntree);
            }
            File fichIndexHTML = new File(strRepertTemp + File.separator + "index.html");
            fichIndexHTML.setWritable(true);
            OutputStreamWriter fw1 = new OutputStreamWriter(new FileOutputStream(fichIndexHTML), "UTF-8");
            try (BufferedWriter bw1 = new BufferedWriter(fw1)) {
                bw1.write(fichierHTML);
            }
            DirectoryChooser repertChoix = new DirectoryChooser();
            repertChoix.setTitle("Choix du repertoire de sauvegarde de la visite");
            File repert = new File(EditeurPanovisu.strRepertoireProjet);
            repertChoix.setInitialDirectory(repert);
            File repertVisite = repertChoix.showDialog(null);

            String nomRepertVisite = repertVisite.getAbsolutePath();
            copieDirectory(strRepertTemp, nomRepertVisite);
            Dialogs.create().title("Génération de la visite")
                    .message("Votre visite a bien été généré dans le répertoire : " + nomRepertVisite)
                    .showInformation();
            if (Desktop.isDesktopSupported()) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop dt = Desktop.getDesktop();
                    File fIndex = new File(nomRepertVisite + File.separator + "index.html");
                    dt.browse(fIndex.toURI());
                }
            }
        } else {
            Dialogs.create().title("Génération de la visite")
                    .message("Votre visite n'a pu être générée, votre fichier n'étant pas sauvegardé")
                    .showError();

        }
    }

    private int creeNiveauxImageEqui(String fichierImage, String repertoire) {
        Platform.runLater(() -> {
            lblNiveaux.setText("Création niveau principal");
        });

        double tailleNiv0 = 512.d;
        String extension = fichierImage.substring(fichierImage.length() - 3, fichierImage.length());
        Image img = null;
        if (extension.equals("tif")) {
            try {
                img = ReadWriteImage.readTiff(fichierImage);
                if (img.getWidth() > 8000) {
                    img = ReadWriteImage.resizeImage(img, 8000, 4000);
                }
            } catch (ImageReadException | IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            img = new Image("file:" + fichierImage);
            if (img.getWidth() > 8000) {
                img = new Image("file:" + fichierImage, 8000, 4000, true, true);
            }
        }
        String nomPano = fichierImage.substring(fichierImage.lastIndexOf(File.separator) + 1, fichierImage.length()).split("\\.")[0] + ".jpg";
        String ficImage = repertoire + File.separator + nomPano;
        try {
            ReadWriteImage.writeJpeg(img, ficImage, 0.85f, true, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }
        int nombreNiveaux = 0;
        if (img != null) {
            double tailleImage = img.getWidth();
            fichierImage = ficImage;
            nombreNiveaux = (int) (Math.log(tailleImage / tailleNiv0) / Math.log(2.d)) + 1;
            int nbNiv = nombreNiveaux;
            for (int i = 0; i < nombreNiveaux; i++) {
                int ii = i;
                Platform.runLater(() -> {
                    lblNiveaux.setText("Création niveau " + ii + "/" + (nbNiv - 1));
                });
                try {
                    double tailleNiveau = tailleImage * Math.pow(2.d, i) / Math.pow(2.d, nombreNiveaux);
                    if (extension.equals("tif")) {
                        try {
                            img = ReadWriteImage.readTiff(fichierImage);
                            img = ReadWriteImage.resizeImage(img, (int) tailleNiveau, (int) tailleNiveau / 2);
                        } catch (ImageReadException ex) {
                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        img = new Image("file:" + fichierImage, Math.round(tailleNiveau * 2.d) / 2.d, Math.round(tailleNiveau / 2.d), true, true);
                    }
                    String repNiveau = repertoire + File.separator + "niveau" + i;
                    File fRepert = new File(repNiveau);
                    if (!fRepert.exists()) {
                        fRepert.mkdirs();
                    }
                    nomPano = fichierImage.substring(fichierImage.lastIndexOf(File.separator) + 1, fichierImage.length());
                    nomPano = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.length()).split("\\.")[0] + ".jpg";
                    ficImage = repNiveau + File.separator + nomPano;
                    ReadWriteImage.writeJpeg(img, ficImage, 0.7f, true, 0.1f);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return nombreNiveaux;
    }

    private int creeNiveauxImageCube(String fichierImage, String repertoire, String face) {
        Platform.runLater(() -> {
            lblNiveaux.setText("Création face : " + face + " niveau principal");
        });

        double tailleNiv0 = 256.d;
        String extension = fichierImage.substring(fichierImage.length() - 3, fichierImage.length());
        Image img = null;
        if (extension.equals("tif")) {
            try {
                img = ReadWriteImage.readTiff(fichierImage);
                if (img.getWidth() > 4096) {
                    img = ReadWriteImage.resizeImage(img, 4096, 4096);
                }
            } catch (ImageReadException | IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            img = new Image("file:" + fichierImage);
            if (img.getWidth() > 4096) {
                img = new Image("file:" + fichierImage, 4096, 4096, true, true);
            }
        }
        String nomPano = fichierImage.substring(fichierImage.lastIndexOf(File.separator) + 1, fichierImage.length());
        nomPano = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.length()).split("\\.")[0] + ".jpg";
        String ficImage = repertoire + File.separator + nomPano;
        try {
            ReadWriteImage.writeJpeg(img, ficImage, 0.85f, true, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }
        int nombreNiveaux = 0;
        if (img != null) {
            double tailleImage = img.getWidth();
            nombreNiveaux = (int) (Math.log(tailleImage / tailleNiv0) / Math.log(2.d)) + 1;
            int nbNiv = nombreNiveaux;
            for (int i = 0; i < nombreNiveaux; i++) {
                try {
                    int ii = i;
                    Platform.runLater(() -> {
                        lblNiveaux.setText("Création face : " + face + " niveau " + ii + "/" + (nbNiv - 1));
                    });

                    double tailleNiveau = tailleImage * Math.pow(2.d, i) / Math.pow(2.d, nombreNiveaux);
                    if (extension.equals("tif")) {
                        try {
                            img = ReadWriteImage.readTiff(fichierImage);
                            img = ReadWriteImage.resizeImage(img, (int) tailleNiveau, (int) tailleNiveau);
                        } catch (ImageReadException ex) {
                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        img = new Image("file:" + fichierImage, tailleNiveau, tailleNiveau, true, true);
                    }

                    String repNiveau = repertoire + File.separator + "niveau" + i;
                    File fRepert = new File(repNiveau);
                    if (!fRepert.exists()) {
                        fRepert.mkdirs();
                    }
                    nomPano = fichierImage.substring(fichierImage.lastIndexOf(File.separator) + 1, fichierImage.length());
                    nomPano = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.length()).split("\\.")[0] + ".jpg";
                    ficImage = repNiveau + File.separator + nomPano;
                    ReadWriteImage.writeJpeg(img, ficImage, 0.7f, true, 0.1f);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return nombreNiveaux;
    }

    /**
     *
     * @throws InterruptedException
     */
    private void panoramiquesAjouter() throws InterruptedException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImage = new FileChooser.ExtensionFilter("Fichiers Image (JPG,BMP,TIFF)", "*.jpg", "*.bmp", "*.tif");
        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
        FileChooser.ExtensionFilter extFilterTiff = new FileChooser.ExtensionFilter("Fichiers TIFF (*.tif)", "*.tif");
        File repert = new File(strCurrentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
//        fileChooser.getExtensionFilters().addAll(extFilterImage, extFilterJpeg, extFilterBmp);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterTiff, extFilterBmp, extFilterImage);

        List<File> list = fileChooser.showOpenMultipleDialog(null);
        if (list != null) {
            apAttends.setVisible(true);
            mbarPrincipal.setDisable(true);
            hbBarreBouton.setDisable(true);
            pbarAvanceChargement.setProgress(-1);
            tpEnvironnement.setDisable(true);
            int i = 0;
            File[] lstFich1 = new File[list.size()];
            String[] typeFich1 = new String[list.size()];
            for (File file : list) {
                lstFich1[i] = file;
                i++;
            }
            int nb = i;
            lblDragDrop.setVisible(false);
            Task traitementTask;
            traitementTask = chargeListeFichiers(lstFich1, nb);
            Thread th1 = new Thread(traitementTask);
            th1.setDaemon(true);
            th1.start();

        }

    }

    public Task chargeListeFichiers(final File[] list, int nb) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int i = 0;
                File[] lstFich1 = new File[list.length];
                String[] typeFich1 = new String[list.length];
                for (int jj = 0; jj < nb; jj++) {
                    File file = list[jj];
                    String nomfich = file.getAbsolutePath();
                    String extension = nomfich.substring(nomfich.lastIndexOf(".") + 1, nomfich.length()).toLowerCase();
//                            if (extension.equals("bmp") || extension.equals("jpg")) {
                    if (extension.equals("jpg") || extension.equals("bmp") || extension.equals("tif")) {
                        Image img = null;
                        if (extension.equals("tif")) {
                            try {
                                img = ReadWriteImage.readTiff(file.getAbsolutePath());
                            } catch (ImageReadException | IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            img = new Image("file:" + file.getAbsolutePath());
                        }
                        typeFich1[i] = "";
                        if (img != null) {
                            if (img.getWidth() == 2 * img.getHeight()) {
                                lstFich1[i] = file;
                                typeFich1[i] = Panoramique.SPHERE;
                                i++;
                            }
                            if (img.getWidth() == img.getHeight()) {
                                String nom = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                                boolean trouve = false;
                                for (int j = 0; j < i; j++) {
                                    String nom1 = "";
                                    if (lstFich1[j].getAbsolutePath().length() == file.getAbsolutePath().length()) {
                                        nom1 = lstFich1[j].getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6);
                                    }
                                    if (nom.equals(nom1)) {
                                        trouve = true;
                                    }
                                }
                                if (!trouve) {
                                    lstFich1[i] = file;
                                    typeFich1[i] = Panoramique.CUBE;
                                    i++;
                                }
                            }
                        }

                    }
                }
                File[] lstFich = new File[i];
                System.arraycopy(lstFich1, 0, lstFich, 0, i);

                for (int ii = 0; ii < lstFich.length; ii++) {
                    File file1 = lstFich[ii];
                    int numP = ii + 1;
                    Platform.runLater(() -> {
                        lblCharge.setText("pano " + numP + "/" + lstFich.length + " : " + file1.getPath());
                        lblNiveaux.setText("");
                        pbarAvanceChargement.setProgress((double) (numP - 1) / (double) lstFich.length);
                    });

                    bDejaSauve = false;
                    mniSauveProjet.setDisable(false);
                    strCurrentDir = file1.getParent();
                    File imageRepert = new File(strRepertTemp + File.separator + "panos");

                    if (!imageRepert.exists()) {

                        imageRepert.mkdirs();
                    }
                    strRepertPanos = imageRepert.getAbsolutePath();
//                try {
//                    if (typeFich1[ii].equals(Panoramique.SPHERE)) {
//                        copieFichierRepertoire(file1.getPath(), repertPanos);
//                    } else {
//                        String nom = file1.getAbsolutePath().substring(0, file1.getAbsolutePath().length() - 6);
//                        copieFichierRepertoire(nom + "_u.jpg", repertPanos);
//                        copieFichierRepertoire(nom + "_d.jpg", repertPanos);
//                        copieFichierRepertoire(nom + "_f.jpg", repertPanos);
//                        copieFichierRepertoire(nom + "_b.jpg", repertPanos);
//                        copieFichierRepertoire(nom + "_r.jpg", repertPanos);
//                        copieFichierRepertoire(nom + "_l.jpg", repertPanos);
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
//                }

                    ajoutePanoramiqueProjet(file1.getPath(), typeFich1[ii]);
                    //System.out.println("panoActuel : " + panoActuel + " nombre panoramiques " + nombrePanoramiques);
                }

                return true;
            }

            @Override
            protected void succeeded() {
                //System.out.println("Succès");
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                super.succeeded();
                cbListeChoixPanoramique.getItems().clear();
                cbListeChoixPanoramiqueEntree.getItems().clear();
                for (int i = 0; i < iNombrePanoramiques; i++) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    cbListeChoixPanoramique.getItems().add(nomPano);
                    cbListeChoixPanoramiqueEntree.getItems().add(nomPano);
                }
                //System.out.println("Fin ==> panoActuel : " + panoActuel + " nombre panoramiques " + nombrePanoramiques);
                vbChoixPanoramique.setVisible(true);
                ivImagePanoramique.setImage(panoramiquesProjet[iPanoActuel].getImagePanoramique());
                ivImagePanoramique.setSmooth(true);
                retireAffichageLigne();
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                iNumPoints = 0;
                ajouteAffichageLignes();
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(iPanoActuel));
                affichePoV(panoramiquesProjet[iPanoActuel].getLookAtX(), panoramiquesProjet[iPanoActuel].getLookAtY());
                afficheNord(panoramiquesProjet[iPanoActuel].getZeroNord());
                installeEvenements();

                ivVisiteGenere.setOpacity(
                        1.0);
                ivVisiteGenere.setDisable(
                        false);
                //System.out.println("Fini");

                gestionnaireInterface.rafraichit();
                affichePanoChoisit(iPanoActuel);
                bPanoCharge = true;
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(iPanoActuel));
                apAttends.setVisible(false);
                mbarPrincipal.setDisable(false);
                hbBarreBouton.setDisable(false);
                tpEnvironnement.setDisable(false);
            }

        };
    }

    private void planAjouter() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("Fichiers PNG (*.png)", "*.png");
        File repert = new File(strCurrentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterPng, extFilterBmp);

        File fichierPlan = fileChooser.showOpenDialog(null);
        if (fichierPlan != null) {
            plans[iNombrePlans] = new Plan();
            plans[iNombrePlans].setImagePlan(fichierPlan.getName());
            plans[iNombrePlans].setLienPlan(fichierPlan.getAbsolutePath());
            File repertoirePlan = new File(strRepertTemp + File.separator + "images");
            if (!repertoirePlan.exists()) {
                repertoirePlan.mkdirs();
            }
            try {
                copieFichierRepertoire(fichierPlan.getAbsolutePath(), repertoirePlan.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
            gestionnairePlan.ajouterPlan();
            iNombrePlans++;
        }

    }

    /**
     *
     */
    private void clickBtnValidePano() {
        TextField txtTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
//        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
//        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
//        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
        if (txtTitrePano != null) {
            panoramiquesProjet[iPanoActuel].setTitrePanoramique(txtTitrePano.getText());
        }
//        panoramiquesProjet[panoActuel].setAfficheInfo(chkAfficheInfo.isSelected());
//        panoramiquesProjet[panoActuel].setAfficheTitre(chkAfficheTitre.isSelected());
//        if (radSphere.isSelected()) {
//            panoramiquesProjet[panoActuel].setTypePanoramique(Panoramique.SPHERE);
//
//        } else {
//            panoramiquesProjet[panoActuel].setTypePanoramique(Panoramique.CUBE);
//        }
    }

    /**
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void projetCharge() throws IOException, InterruptedException {
        if (!bRepertSauveChoisi) {
            strRepertoireProjet = strCurrentDir;
        }
        Action reponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Charge un Projet")
                    .masthead("Vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
            panePanoramique.getChildren().clear();
            panePanoramique.getChildren().add(ivImagePanoramique);
            apAttends.setVisible(true);
            mbarPrincipal.setDisable(true);
            hbBarreBouton.setDisable(true);
            tpEnvironnement.setDisable(true);
            bDejaSauve = true;
            FileChooser repertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            repertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(strRepertoireProjet + File.separator);
            repertChoix.setInitialDirectory(repert);
            fileProjet = null;
            fileProjet = repertChoix.showOpenDialog(stPrincipal);
            if (fileProjet != null) {
                stPrincipal.setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                lblDragDrop.setVisible(false);
                strRepertoireProjet = fileProjet.getParent();
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                bRepertSauveChoisi = true;
                deleteDirectory(strRepertTemp);
                String repertPanovisu = strRepertTemp + File.separator + "panovisu";
                File rptPanovisu = new File(repertPanovisu);
                rptPanovisu.mkdirs();
                copieDirectory(strRepertAppli + File.separator + "panovisu", repertPanovisu);
                mnuPanoramique.setDisable(false);
                ivAjouterPano.setDisable(false);
                ivAjouterPano.setOpacity(1.0);
                ivSauveProjet.setDisable(false);
                ivSauveProjet.setOpacity(1.0);
                ivVisiteGenere.setDisable(false);
                ivVisiteGenere.setOpacity(1.0);

                vbChoixPanoramique.setVisible(false);

                mniSauveProjet.setDisable(false);
                mniSauveSousProjet.setDisable(false);
                mniVisiteGenere.setDisable(false);
                iNumPoints = 0;
                iNumImages = 0;
                iNumHTML = 0;
                ivImagePanoramique.setImage(null);
                cbListeChoixPanoramique.getItems().clear();
                cbListeChoixPanoramiqueEntree.getItems().clear();
                try {
                    String texte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        texte = "";
                        String ligneTexte;
                        iNombrePanoramiquesFichier = 0;
                        while ((ligneTexte = br.readLine()) != null) {
                            if (ligneTexte.contains("Panoramique=>")) {
                                iNombrePanoramiquesFichier++;
                                //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                            }
                            texte += ligneTexte + "\n";
                        }
                    }

                    Task traitementTask;
                    traitementTask = analyseLigne(texte);
                    Thread th1 = new Thread(traitementTask);
                    th1.setDaemon(true);
                    th1.start();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void ajouteFichierHisto(String nomFich) {
        if (nombreHistoFichiers > 10) {
            nombreHistoFichiers = 10;
        }
        int trouve = -1;
        for (int i = 0; i < nombreHistoFichiers; i++) {
            if (strHistoFichiers[i].equals(nomFich)) {
                trouve = i;
            }
        }
        if (trouve == -1) {
            for (int i = nombreHistoFichiers; i >= 0; i--) {
                if (i < 9) {
                    strHistoFichiers[i + 1] = strHistoFichiers[i];
                }
            }
            strHistoFichiers[0] = nomFich;
            if (nombreHistoFichiers < 10) {
                nombreHistoFichiers++;
            }
        } else {
            for (int i = trouve - 1; i >= 0; i--) {
                if (i < 9) {
                    strHistoFichiers[i + 1] = strHistoFichiers[i];
                }
            }
            strHistoFichiers[0] = nomFich;

        }
        mnuDerniersProjets.getItems().clear();
        for (int i = 0; i < nombreHistoFichiers; i++) {
            if (strHistoFichiers[i] != null) {
                MenuItem menuDerniersFichiers = new MenuItem(strHistoFichiers[i]);
                mnuDerniersProjets.getItems().add(menuDerniersFichiers);
                menuDerniersFichiers.setOnAction((ActionEvent e) -> {
                    MenuItem mnu = (MenuItem) e.getSource();

                    try {
                        try {
                            projetChargeNom(mnu.getText());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }

    }

    /**
     *
     * @param nomFich
     * @throws IOException
     * @throws InterruptedException
     */
    private void projetChargeNom(String nomFich) throws IOException, InterruptedException {
        File fichProjet1 = new File(nomFich);
        if (fichProjet1.exists()) {
            Action reponse = null;
            Localization.setLocale(locale);
            if (!bDejaSauve) {
                reponse = Dialogs.create()
                        .owner(null)
                        .title("Charge un Projet")
                        .masthead("Vous n'avez pas sauvegardé votre projet")
                        .message("Voulez vous le sauver ?")
                        .showConfirm();

            }
            if (reponse == Dialog.Actions.YES) {
                try {
                    projetSauve();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
                panePanoramique.getChildren().clear();
                panePanoramique.getChildren().add(ivImagePanoramique);
                apAttends.setVisible(true);
                mbarPrincipal.setDisable(true);
                hbBarreBouton.setDisable(true);
                tpEnvironnement.setDisable(true);
                lblDragDrop.setVisible(false);
                bDejaSauve = true;
                fileProjet = fichProjet1;
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                stPrincipal.setTitle("Panovisu  v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                strRepertoireProjet = fileProjet.getParent();
                bRepertSauveChoisi = true;
                deleteDirectory(strRepertTemp);
                String repertPanovisu = strRepertTemp + File.separator + "panovisu";
                File rptPanovisu = new File(repertPanovisu);
                rptPanovisu.mkdirs();
                copieDirectory(strRepertAppli + File.separator + "panovisu", repertPanovisu);
                mnuPanoramique.setDisable(false);
                ivAjouterPano.setDisable(false);
                ivAjouterPano.setOpacity(1.0);
                ivSauveProjet.setDisable(false);
                ivSauveProjet.setOpacity(1.0);
                ivVisiteGenere.setDisable(false);
                ivVisiteGenere.setOpacity(1.0);

                vbChoixPanoramique.setVisible(false);

                mniSauveProjet.setDisable(false);
                mniSauveSousProjet.setDisable(false);
                mniVisiteGenere.setDisable(false);
                iNumPoints = 0;
                iNumImages = 0;
                iNumHTML = 0;

                ivImagePanoramique.setImage(null);
                cbListeChoixPanoramique.getItems().clear();
                cbListeChoixPanoramiqueEntree.getItems().clear();
                try {
                    String texte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        texte = "";
                        String ligneTexte;
                        iNombrePanoramiquesFichier = 0;
                        while ((ligneTexte = br.readLine()) != null) {
                            if (ligneTexte.contains("Panoramique=>")) {
                                iNombrePanoramiquesFichier++;
                                //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                            }
                            texte += ligneTexte + "\n";
                        }
                    }
                    Task traitementTask;
                    traitementTask = analyseLigne(texte);
                    Thread th1 = new Thread(traitementTask);
                    th1.setDaemon(true);
                    th1.start();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sauveFichierProjet() throws IOException {
        strRepertoireProjet = fileProjet.getParent();
        bRepertSauveChoisi = true;
        File fichierProjet = new File(fileProjet.getAbsolutePath());
        if (!fichierProjet.exists()) {
            fichierProjet.createNewFile();
        }
        bDejaSauve = true;
        stPrincipal.setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());

        String contenuFichier = "";
        TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
        if (!tfVisite.getText().equals("")) {
            contenuFichier += "[titreVisite=>" + tfVisite.getText() + "]\n";
        }
        if (!strPanoEntree.equals("")) {
            contenuFichier += "[PanoEntree=>" + strPanoEntree + "]\n";
        }
        for (int i = 0; i < iNombrePanoramiques; i++) {
            contenuFichier += "[Panoramique=>"
                    + "fichier:" + panoramiquesProjet[i].getNomFichier()
                    + ";nb:" + panoramiquesProjet[i].getNombreHotspots()
                    + ";nbImg:" + panoramiquesProjet[i].getNombreHotspotImage()
                    + ";titre:" + panoramiquesProjet[i].getTitrePanoramique() + ""
                    + ";type:" + panoramiquesProjet[i].getTypePanoramique()
                    + ";afficheInfo:" + panoramiquesProjet[i].isAfficheInfo()
                    + ";afficheTitre:" + panoramiquesProjet[i].isAfficheTitre()
                    + ";regardX:" + panoramiquesProjet[i].getLookAtX()
                    + ";regardY:" + panoramiquesProjet[i].getLookAtY()
                    + ";zeroNord:" + panoramiquesProjet[i].getZeroNord()
                    + ";affichePlan:" + panoramiquesProjet[i].isAffichePlan()
                    + ";numeroPlan:" + panoramiquesProjet[i].getNumeroPlan()
                    + "]\n";
            for (int j = 0; j < panoramiquesProjet[i].getNombreHotspots(); j++) {
                HotSpot HS = panoramiquesProjet[i].getHotspot(j);
                contenuFichier += "   [hotspot==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getFichierImage()
                        + ";xml:" + HS.getFichierXML()
                        + ";info:" + HS.getInfo()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
            for (int j = 0; j < panoramiquesProjet[i].getNombreHotspotImage(); j++) {
                HotspotImage HS = panoramiquesProjet[i].getHotspotImage(j);
                contenuFichier += "   [hotspotImage==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getFichierImage()
                        + ";img:" + HS.getLienImg()
                        + ";urlImg:" + HS.getUrlImage()
                        + ";info:" + HS.getInfo()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
        }
        contenuFichier += "[Interface=>\n" + gestionnaireInterface.getTemplate() + "]\n";
        contenuFichier += gestionnairePlan.getTemplate();
        fileProjet.setWritable(true);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fileProjet), "UTF-8");
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(contenuFichier);
        }
        Dialogs.create().title("Sauvegarde de fichier")
                .message("Votre fichier à bien été sauvegardé")
                .showInformation();

    }

    private void sauveHistoFichiers() throws IOException {
        File fichConfig = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + File.separator + "derniersprojets.cfg");
        if (!fichConfig.exists()) {
            fichConfig.createNewFile();
        }
        fichConfig.setWritable(true);
        String contenuFichier = "";
        for (int i = 0; i < nombreHistoFichiers; i++) {
            contenuFichier += strHistoFichiers[i] + "\n";
        }
        OutputStreamWriter fw = null;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(fichConfig), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(contenuFichier);
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @throws IOException
     */
    private void projetSauve() throws IOException {
        if (!bRepertSauveChoisi) {
            strRepertoireProjet = strCurrentDir;
        }
        if (fileProjet == null) {
            FileChooser repertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            repertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(strRepertoireProjet + File.separator);
            repertChoix.setInitialDirectory(repert);
            fileProjet = repertChoix.showSaveDialog(null);

        }
        if (fileProjet != null) {
            sauveFichierProjet();
            ajouteFichierHisto(fileProjet.getAbsolutePath());
        }
    }

    /**
     *
     * @throws IOException
     */
    private void projetSauveSous() throws IOException {
        if (!bRepertSauveChoisi) {
            strRepertoireProjet = strCurrentDir;
        }
        FileChooser repertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
        repertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(strRepertoireProjet + File.separator);
        repertChoix.setInitialDirectory(repert);
        fileProjet = repertChoix.showSaveDialog(null);
        if (fileProjet != null) {
            sauveFichierProjet();
            ajouteFichierHisto(fileProjet.getAbsolutePath());

        }

    }

    /**
     *
     */
    private void aideapropos() {
        try {
            popUp.affichePopup();

        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void projetsFermer() throws IOException {
        Action reponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Quitter l'application")
                    .masthead("Vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
            sauveHistoFichiers();
            deleteDirectory(strRepertTemp);
            File ftemp = new File(strRepertTemp);
            ftemp.delete();
            Platform.exit();
        }
    }

    /**
     *
     */
    private void projetsNouveau() {
        Action reponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Nouveau projet")
                    .masthead("Vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
            strPanoEntree = "";
            deleteDirectory(strRepertTemp);
            String repertPanovisu = strRepertTemp + File.separator + "panovisu";
            File rptPanovisu = new File(repertPanovisu);
            rptPanovisu.mkdirs();
            copieDirectory(strRepertAppli + File.separator + "panovisu", repertPanovisu);
            mnuPanoramique.setDisable(false);

            ivAjouterPano.setDisable(false);
            ivAjouterPano.setOpacity(1.0);
            ivSauveProjet.setDisable(false);
            ivSauveProjet.setOpacity(1.0);
            mniSauveProjet.setDisable(false);
            mniSauveSousProjet.setDisable(false);
            mniVisiteGenere.setDisable(false);
            fileProjet = null;
            vbChoixPanoramique.setVisible(false);
            panoramiquesProjet = new Panoramique[50];
            iNombrePanoramiques = 0;
            retireAffichageLigne();
            retireAffichageHotSpots();
            retireAffichagePointsHotSpots();
            iNumPoints = 0;
            iNumImages = 0;
            iNumHTML = 0;
            gestionnaireInterface.iNombreImagesFond = 0;
            gestionnairePlan.planActuel = -1;
            ivImagePanoramique.setImage(null);
            cbListeChoixPanoramique.getItems().clear();
            cbListeChoixPanoramiqueEntree.getItems().clear();
            lblDragDrop.setVisible(true);
            Node ancNord = (Node) panePanoramique.lookup("#Nord");
            if (ancNord != null) {
                panePanoramique.getChildren().remove(ancNord);
            }
            Node ancPoV = (Node) panePanoramique.lookup("#PoV");
            if (ancPoV != null) {
                panePanoramique.getChildren().remove(ancPoV);
            }

            gestionnairePlan.lblDragDropPlan.setVisible(true);
            iNombrePlans = 0;
            plans = new Plan[100];
            gestionnairePlan.creeInterface(iLargeurInterface, iHauteurInterface - 60);
            Pane panneauPlan = gestionnairePlan.paneInterface;
            mniAffichagePlan.setDisable(true);
            tabPlan.setDisable(true);
            tabPlan.setContent(panneauPlan);
            spVuePanoramique.setOnDragOver((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                } else {
                    event.consume();
                }
            });

            // Dropping over surface
            spVuePanoramique.setOnDragDropped((DragEvent event) -> {
                Platform.runLater(() -> {
                    apAttends.setVisible(true);
                });
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    apAttends.setVisible(true);
                    mbarPrincipal.setDisable(true);
                    hbBarreBouton.setDisable(true);
                    tpEnvironnement.setDisable(true);
                    pbarAvanceChargement.setProgress(-1);
                    success = true;
                    String filePath = null;
                    File[] list = new File[100];
                    int i = 0;
                    for (File file1 : db.getFiles()) {
                        filePath = file1.getAbsolutePath();
                        list[i] = file1;
                        i++;
                    }
                    int nb = i;
                    if (list != null) {
                        lblDragDrop.setVisible(false);
                        Task traitementTask;
                        traitementTask = chargeListeFichiers(list, nb);
                        Thread th1 = new Thread(traitementTask);
                        th1.setDaemon(true);
                        th1.start();
                    }

                }
                event.setDropCompleted(success);
                event.consume();
            });

        }
    }

    /**
     *
     * @param nb
     * @return chaine de caractères aléatoire
     */
    private String genereChaineAleatoire(Number nb) {
        int nombre = (int) nb;
        String chaine = "";
        String chaine1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < nombre; i++) {
            int numChar = (int) (Math.random() * 36.0f);
            chaine += chaine1.substring(numChar, numChar + 1);
        }
        return chaine;
    }

    /**
     *
     * @param emplacement répertoire à effacer
     */
    static public void deleteDirectory(String emplacement) {
        File path = new File(emplacement);
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    deleteDirectory(file1.getAbsolutePath());
                }
                file1.delete();
            }
        }
    }

//    public Task traitement() {
//        return new Task() {
//            @Override
//            protected Object call() throws Exception {
//                myPane.setCursor(Cursor.WAIT);
//                Platform.runLater(() -> {
//                    //System.out.println(listeFichier.getCellFactory());
//                    for (int j = 0; j < listeFichier.getItems().size(); j++) {
//                        String nomFich1 = (String) listeFichier.getItems().get(j);
//                        listeFichier.getItems().set(j, "A Traiter => " + nomFich1);
//                    }
//                });
//                Thread.sleep(200);
//
//                ////System.out.println(listeFichier.getItems().size() + " Lancement ");
//                updateProgress(0.0001f, listeFichier.getItems().size());
//                for (int i1 = 0; i1 < listeFichier.getItems().size(); i1++) {
//                    updateMessage("Traitement en cours " + (i1 + 1) + "/" + listeFichier.getItems().size());
//                    String nomFich = ((String) listeFichier.getItems().get(i1)).split("> ")[1];
//                    final int ii = i1;
//                    Platform.runLater(() -> {
//                        listeFichier.getItems().set(ii, "Traitement en cours => " + nomFich);
//                    });
//                    Thread.sleep(100);
//                    //System.out.println("Début");
//                    traiteFichier(nomFich, i1);
//                    updateProgress(i1 + 1, listeFichier.getItems().size());
//                    //System.out.println("Fin");
//                    Platform.runLater(() -> {
//                        listeFichier.getItems().set(ii, "Traité => " + nomFich);
//                    });
//                    Thread.sleep(100);
//                }
//                myPane.setCursor(Cursor.DEFAULT);
//                return true;
//            }
    public Task analyseLigne(final String ligComplete) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                strPanoEntree = "";
                iNombrePanoramiques = 0;
                String ligneComplete = ligComplete.replace("[", "|");
                String lignes[] = ligneComplete.split("\\|", 500);
                String ligne;
                String[] elementsLigne;
                String[] typeElement;
                int nbHS = 0;
                int nbImg = 0;
                int nbHsplan = 0;
                iNombrePlans = -1;
                for (int kk = 1; kk < lignes.length; kk++) {
                    ligne = lignes[kk];
                    elementsLigne = ligne.split(";", 25);
                    typeElement = elementsLigne[0].split(">", 2);
                    typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                    elementsLigne[0] = typeElement[1];
                    if ("Panoramique".equals(typeElement[0])) {
                        for (int i = 0; i < elementsLigne.length; i++) {
                            elementsLigne[i] = elementsLigne[i].replace("]", "").replace("\n", "");
                            String[] valeur = elementsLigne[i].split(":", 2);
                            //System.out.println(valeur[0] + "=" + valeur[1]);

                            switch (valeur[0]) {
                                case "fichier":
                                    String nmFich = valeur[1];
                                    int numPano = iNombrePanoramiques + 1;
                                    Platform.runLater(() -> {
                                        lblCharge.setText("pano " + numPano + "/" + iNombrePanoramiquesFichier + " : " + nmFich);
                                        lblNiveaux.setText("");
                                        pbarAvanceChargement.setProgress((double) (numPano - 1) / (double) iNombrePanoramiquesFichier);
                                    });
                                    File imgPano = new File(nmFich);
                                    File imageRepert = new File(strRepertTemp + File.separator + "panos");

                                    if (!imageRepert.exists()) {
                                        imageRepert.mkdirs();
                                    }
                                    strRepertPanos = imageRepert.getAbsolutePath();
                                    String fichierPano = imgPano.getPath();
                                    String extension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
                                    Image img = null;
                                    if ("tif".equals(extension)) {
                                        try {
                                            img = ReadWriteImage.readTiff(fichierPano);
                                        } catch (IOException ex) {
                                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        img = new Image("file:" + fichierPano);
                                    }

//                            try {
//                                copieFichierRepertoire(imgPano.getPath(), repertPanos);
//
//                            } catch (IOException ex) {
//                                Logger.getLogger(EditeurPanovisu.class
//                                        .getName()).log(Level.SEVERE, null, ex);
//                            }
                                    //System.out.println("avant ajoutePanoramique");
                                    if (img != null) {
                                        if (img.getWidth() == img.getHeight()) {
                                            ajoutePanoramiqueProjet(valeur[1], Panoramique.CUBE);

                                        } else {
                                            ajoutePanoramiqueProjet(valeur[1], Panoramique.SPHERE);
                                        }
                                    }
                                    //System.out.println("après ajoutePanoramique");

                                    break;

                                case "titre":
                                    if (!valeur[1].equals("'null'")) {
                                        panoramiquesProjet[iPanoActuel].setTitrePanoramique(valeur[1]);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setTitrePanoramique("");
                                    }
                                    break;
                                case "type":
                                    panoramiquesProjet[iPanoActuel].setTypePanoramique(valeur[1]);
                                    break;
                                case "afficheInfo":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAfficheInfo(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAfficheInfo(false);
                                    }
                                    break;
                                case "afficheTitre":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAfficheTitre(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAfficheTitre(false);
                                    }
                                    break;
                                case "nb":
                                    nbHS = Integer.parseInt(valeur[1]);
                                    break;
                                case "nbImg":
                                    nbImg = Integer.parseInt(valeur[1]);
                                    break;
                                case "regardX":
                                    panoramiquesProjet[iPanoActuel].setLookAtX(Double.parseDouble(valeur[1]));
                                    break;
                                case "regardY":
                                    panoramiquesProjet[iPanoActuel].setLookAtY(Double.parseDouble(valeur[1]));
                                    break;
                                case "zeroNord":
                                    panoramiquesProjet[iPanoActuel].setZeroNord(Double.parseDouble(valeur[1]));
                                    break;
                                case "numeroPlan":
                                    panoramiquesProjet[iPanoActuel].setNumeroPlan(Integer.parseInt(valeur[1].replace(" ", "")));
                                    break;
                                case "affichePlan":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAffichePlan(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAffichePlan(false);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        for (int jj = 0; jj < nbHS; jj++) {
                            kk++;
                            ligne = lignes[kk];
                            elementsLigne = ligne.split(";", 10);
                            typeElement = elementsLigne[0].split(">", 2);
                            typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            elementsLigne[0] = typeElement[1];
                            HotSpot HS = new HotSpot();
                            for (int i = 0; i < elementsLigne.length; i++) {
                                elementsLigne[i] = elementsLigne[i].replace("]", "");
                                String[] valeur = elementsLigne[i].split(":", 2);

                                switch (valeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(valeur[1])) {
                                            HS.setFichierImage(null);
                                        } else {
                                            HS.setFichierImage(valeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(valeur[1])) {
                                            HS.setInfo(null);
                                        } else {
                                            HS.setInfo(valeur[1]);
                                        }
                                        break;
                                    case "xml":
                                        if ("null".equals(valeur[1])) {
                                            HS.setFichierXML(null);
                                        } else {
                                            HS.setFichierXML(valeur[1]);
                                        }
                                        break;
                                    case "anime":
                                        if (valeur[1].equals("true")) {
                                            HS.setAnime(true);
                                        } else {
                                            HS.setAnime(false);
                                        }
                                        break;
                                }
                            }
                            panoramiquesProjet[iPanoActuel].addHotspot(HS);
                        }

                        for (int jj = 0; jj < nbImg; jj++) {
                            kk++;
                            ligne = lignes[kk];
                            elementsLigne = ligne.split(";", 10);
                            typeElement = elementsLigne[0].split(">", 2);
                            typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            elementsLigne[0] = typeElement[1];
                            HotspotImage HS = new HotspotImage();
                            for (int i = 0; i < elementsLigne.length; i++) {
                                elementsLigne[i] = elementsLigne[i].replace("]", "");
                                String[] valeur = elementsLigne[i].split(":", 2);
                                switch (valeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(valeur[1])) {
                                            HS.setFichierImage(null);
                                        } else {
                                            HS.setFichierImage(valeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(valeur[1])) {
                                            HS.setInfo(null);
                                        } else {
                                            HS.setInfo(valeur[1]);
                                        }
                                        break;
                                    case "img":
                                        if ("null".equals(valeur[1])) {
                                            HS.setLienImg(null);
                                        } else {
                                            HS.setLienImg(valeur[1]);
                                        }
                                        break;
                                    case "urlImg":
                                        if ("null".equals(valeur[1])) {
                                            HS.setUrlImage(null);
                                        } else {
                                            HS.setUrlImage(valeur[1]);
                                        }
                                        String nmFich = valeur[1];
                                        File imgPano = new File(nmFich);
                                        File imageRepert = new File(strRepertTemp + File.separator + "images");

                                        if (!imageRepert.exists()) {
                                            imageRepert.mkdirs();
                                        }
                                        strRepertPanos = imageRepert.getAbsolutePath();
                                        try {
                                            copieFichierRepertoire(imgPano.getPath(), strRepertPanos);
                                        } catch (IOException ex) {
                                            Logger.getLogger(EditeurPanovisu.class
                                                    .getName()).log(Level.SEVERE, null, ex);
                                        }

                                        break;
                                    case "anime":
                                        if (valeur[1].equals("true")) {
                                            HS.setAnime(true);
                                        } else {
                                            HS.setAnime(false);
                                        }
                                        break;
                                }
                            }
                            panoramiquesProjet[iPanoActuel].addHotspotImage(HS);
                        }

                    }

                    if ("Plan".equals(typeElement[0])) {
                        for (int i = 0; i < elementsLigne.length; i++) {
                            elementsLigne[i] = elementsLigne[i].replace("]", "");
                            String[] valeur = elementsLigne[i].split(":", 2);
                            //System.out.println(valeur[0] + "=" + valeur[1]);
                            switch (valeur[0]) {
                                case "lienImage":
                                    iNombrePlans++;
                                    plans[iNombrePlans] = new Plan();
                                    plans[iNombrePlans].setLienPlan(valeur[1]);
                                    File repertoirePlan = new File(strRepertTemp + File.separator + "images");
                                    if (!repertoirePlan.exists()) {
                                        repertoirePlan.mkdirs();
                                    }
                                    try {
                                        copieFichierRepertoire(plans[iNombrePlans].getLienPlan(), repertoirePlan.getAbsolutePath());
                                    } catch (IOException ex) {
                                        Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "image":
                                    plans[iNombrePlans].setImagePlan(valeur[1]);
                                    break;
                                case "titre":
                                    if (!valeur[1].equals("'null'")) {
                                        plans[iNombrePlans].setTitrePlan(valeur[1]);
                                    } else {
                                        plans[iNombrePlans].setTitrePlan("");
                                    }
                                    break;
                                case "nb":
                                    nbHsplan = Integer.parseInt(valeur[1]);
                                    break;
                                case "position":
                                    plans[iNombrePlans].setPosition(valeur[1]);
                                    break;
                                case "positionX":
                                    plans[iNombrePlans].setPositionX(Double.parseDouble(valeur[1]));
                                    break;
                                case "positionY":
                                    plans[iNombrePlans].setPositionY(Double.parseDouble(valeur[1]));
                                    break;
                                case "directionNord":
                                    plans[iNombrePlans].setDirectionNord(Double.parseDouble(valeur[1]));
                                    break;
                                default:
                                    break;
                            }
                        }
                        for (int jj = 0; jj < nbHsplan; jj++) {
                            kk++;
                            ligne = lignes[kk];
                            elementsLigne = ligne.split(";", 15);
                            typeElement = elementsLigne[0].split(">", 2);
                            typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            elementsLigne[0] = typeElement[1];
                            HotSpot HS = new HotSpot();
                            for (int i = 0; i < elementsLigne.length; i++) {
                                elementsLigne[i] = elementsLigne[i].replace("]", "");
                                String[] valeur = elementsLigne[i].split(":", 2);
                                //System.out.println(valeur[0] + "=" + valeur[1]);
                                switch (valeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(valeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(valeur[1])) {
                                            HS.setFichierImage(null);
                                        } else {
                                            HS.setFichierImage(valeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(valeur[1])) {
                                            HS.setInfo(null);
                                        } else {
                                            HS.setInfo(valeur[1]);
                                        }
                                        break;
                                    case "xml":
                                        if ("null".equals(valeur[1])) {
                                            HS.setFichierXML(null);
                                        } else {
                                            HS.setFichierXML(valeur[1]);
                                        }
                                        break;
                                    case "numeroPano":
                                        HS.setNumeroPano(Integer.parseInt(valeur[1].replace("\n", "").replace(" ", "")));
                                        break;
                                    case "anime":
                                        if (valeur[1].equals("true")) {
                                            HS.setAnime(true);
                                        } else {
                                            HS.setAnime(false);
                                        }
                                        break;
                                }
                            }
                            plans[iNombrePlans].addHotspot(HS);
                        }
                    }
                    if ("Interface".equals(typeElement[0])) {
                        String[] elts = elementsLigne[0].replace("]", "").split("\n");
                        List<String> listeTemplate = new ArrayList<>();
                        for (String texte : elts) {
                            if (!texte.equals("")) {
                                listeTemplate.add(texte);
                            }
                        }
                        gestionnaireInterface.setTemplate(listeTemplate);
                    }
                    if ("PanoEntree".equals(typeElement[0])) {
                        strPanoEntree = elementsLigne[0].split("]")[0];
                    }
                    if ("titreVisite".equals(typeElement[0])) {
                        strTitreVisite = elementsLigne[0].split("]")[0];
                    }

                }
                //System.out.println("Fini");
                return true;
            }

            @Override
            protected void succeeded() {
                //System.out.println("Succès");
                super.succeeded();
                if (!strPanoEntree.equals("")) {
                    cbListeChoixPanoramiqueEntree.setValue(strPanoEntree + ".jpg");
                }
                for (int i = 0; i < iNombrePanoramiques; i++) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    cbListeChoixPanoramique.getItems().add(nomPano);
                    cbListeChoixPanoramiqueEntree.getItems().add(nomPano);
                }
                iPanoActuel = 0;
                vbChoixPanoramique.setVisible(true);

                ivImagePanoramique.setImage(panoramiquesProjet[iPanoActuel].getImagePanoramique());

                ivImagePanoramique.setSmooth(true);
                retireAffichageLigne();
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                iNumPoints = 0;
                ajouteAffichageLignes();
                iPanoActuel = iNombrePanoramiques - 1;

                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(iPanoActuel));
                affichePoV(panoramiquesProjet[iPanoActuel].getLookAtX(), panoramiquesProjet[iPanoActuel].getLookAtY());
                afficheNord(panoramiquesProjet[iPanoActuel].getZeroNord());

                for (int ii = 0; ii < iNombrePanoramiques; ii++) {
                    Panoramique pano1 = panoramiquesProjet[ii];
                }
                iNombrePlans++;
                if (iNombrePlans != 0) {
                    int nbPlans1 = iNombrePlans;
                    for (int i = 0; i < nbPlans1; i++) {
                        iNombrePlans = i;
                        gestionnairePlan.ajouterPlan();
                    }
                    iNombrePlans++;
                    gestionnairePlan.afficheConfigPlan();
                }

                try {
                    gestionnaireInterface.afficheTemplate();
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                gestionnaireInterface.rafraichit();
                iPanoActuel = 0;
                affichePanoChoisit(iPanoActuel);
                bPanoCharge = true;
                vbChoixPanoramique.setVisible(true);
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(0));
                TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
                tfVisite.setText(strTitreVisite);
                apAttends.setVisible(false);
                mbarPrincipal.setDisable(false);
                hbBarreBouton.setDisable(false);
                tpEnvironnement.setDisable(false);
            }

        };
    }

    /**
     *
     */
    private void lisFichierConfig() throws IOException {
//        MenuItem menuItem=new MenuItem("test");
//        menuItem.setOnAction(null);
//        derniersProjets.getItems().addAll(menuItem);
//        derniersProjets.setDisable(false);
        File fichConfig = new File(fileRepertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
        if (fichConfig.exists()) {
            try {
                String texte;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(fichConfig), "UTF-8"))) {
                    String ligneTexte;
                    String langue = "fr";
                    String pays = "FR";
                    String repert = strRepertAppli;
                    while ((texte = br.readLine()) != null) {
                        String tpe = texte.split("=")[0];
                        String valeur = texte.split("=")[1];
                        switch (tpe) {
                            case "langue":
                                langue = valeur;
                                break;
                            case "pays":
                                pays = valeur;
                                break;
                            case "repert":
                                repert = valeur;
                                break;
                            case "style":
                                strStyleCSS = valeur;
                                break;

                        }

                    }
                    locale = new Locale(langue, pays);
                    File repert1 = new File(repert);
                    if (repert1.exists()) {
                        strRepertoireProjet = repert;
                        strCurrentDir = repert;
                        bRepertSauveChoisi = true;
                    } else {
                        strRepertoireProjet = strRepertAppli;
                        strCurrentDir = strRepertAppli;
                    }
                    setUserAgentStylesheet("file:css/" + strStyleCSS + ".css");
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            fichConfig.createNewFile();
            locale = new Locale("fr", "FR");
            setUserAgentStylesheet("file:css/clair.css");
            strRepertoireProjet = strRepertAppli;
            String contenuFichier = "langue=" + locale.toString().split("_")[0] + "\n";
            contenuFichier += "pays=" + locale.toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + strRepertoireProjet + "\n";
            contenuFichier += "style=clair\n";
            fichConfig.setWritable(true);
            OutputStreamWriter fw = null;
            try {
                fw = new OutputStreamWriter(new FileOutputStream(fichConfig), "UTF-8");
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedWriter bw = new BufferedWriter(fw);
            try {
                bw.write(contenuFichier);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     *
     * @param emplacement répertoire origine
     * @param repertoire répertoire cible
     */
    static public void copieDirectory(String emplacement, String repertoire) {
        File repert2 = new File(repertoire);
        if (!repert2.exists()) {
            repert2.mkdirs();
        }
        File path = new File(emplacement);
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file1 : files) {
                try {
                    if (file1.isDirectory()) {
                        String rep1 = file1.getAbsolutePath().substring(file1.getAbsolutePath().lastIndexOf(File.separator) + 1);
                        rep1 = repertoire + File.separator + rep1;
                        copieDirectory(file1.getAbsolutePath(), rep1);
                    } else {
                        copieFichierRepertoire(file1.getAbsolutePath(), repertoire);

                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//    /**
//     *
//     * @param emplacement répertoire origine
//     * @param repertoire répertoire cible
//     */
//    static public void copieDirectorysaufImage(String emplacement, String repertoire) {
//        File repert2 = new File(repertoire);
//        if (!repert2.exists()) {
//            repert2.mkdirs();
//        }
//        File path = new File(emplacement);
//        if (path.exists()) {
//            File[] files = path.listFiles();
//            for (File file1 : files) {
//                try {
//                    if (file1.isDirectory()) {
//                        if (!file1.getName().equals("images")) {
//                            String rep1 = file1.getAbsolutePath().substring(file1.getAbsolutePath().lastIndexOf(File.separator) + 1);
//                            rep1 = repertoire + File.separator + rep1;
//                            copieDirectorysaufImage(file1.getAbsolutePath(), rep1);
//                        }
//                    } else {
//                        copieFichierRepertoire(file1.getAbsolutePath(), repertoire);
//
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(EditeurPanovisu.class
//                            .getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
    /**
     *
     * @param fichier
     * @param repertoire
     * @throws FileNotFoundException
     * @throws IOException
     */
    static public void copieFichierRepertoire(String fichier, String repertoire) throws FileNotFoundException, IOException {
        File from = new File(fichier);
        File to = new File(repertoire + File.separator + fichier.substring(fichier.lastIndexOf(File.separator) + 1));
        Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        Pane lbl = (Pane) vbOutils.lookup("#labels");
        vbOutils.getChildren().remove(lbl);
    }

    /**
     *
     */
    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < iNumPoints; i++) {
            Node pt = (Node) panePanoramique.lookup("#point" + i);
            panePanoramique.getChildren().remove(pt);
        }
        for (int i = 0; i < iNumImages; i++) {
            Node pt = (Node) panePanoramique.lookup("#img" + i);
            panePanoramique.getChildren().remove(pt);
        }
    }

    /**
     *
     * @param panCourant
     * @return
     */
    private String listePano(int panCourant) {
        String liste = "";
        if (iNombrePanoramiques == 0) {
            return null;
        } else {
            for (int i = 0; i < iNombrePanoramiques; i++) {
                if (i != panCourant) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    String[] nPano = nomPano.split("\\.");
                    liste += nPano[0];
                    if (i < iNombrePanoramiques - 1) {
                        liste += ";";
                    }
                }
            }
            return liste;
        }
    }

    private AnchorPane afficherListePanosVignettes(int numHS) {

        AnchorPane aplistePano = new AnchorPane();
        aplistePano.setOpacity(1);
        Pane fond = new Pane();
        fond.setStyle("-fx-background-color : #bbb;");
        fond.setPrefWidth(540);
        fond.setPrefHeight(((iNombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        fond.setMinWidth(540);
        fond.setMinHeight(70);
        aplistePano.getChildren().add(fond);
        aplistePano.setStyle("-fx-backgroung-color : #bbb;");
        int j = 0;
        ImageView[] IVPano;
        IVPano = new ImageView[iNombrePanoramiques];
        double xPos;
        double yPos;
        int row = 0;
        for (int i = 0; i < iNombrePanoramiques; i++) {
            int numeroPano = i;
            IVPano[j] = new ImageView(panoramiquesProjet[i].getVignettePanoramique());
            IVPano[j].setFitWidth(120);
            IVPano[j].setFitHeight(60);
            IVPano[j].setSmooth(true);
            String nomPano = panoramiquesProjet[i].getNomFichier();
            int col = j % 4;
            row = j / 4;
            xPos = col * 130 + 25;
            yPos = row * 65 + 5;
            IVPano[j].setLayoutX(xPos);
            IVPano[j].setLayoutY(yPos);
            IVPano[j].setCursor(Cursor.HAND);
            IVPano[j].setStyle("-fx-background-color : #ccc;");
            Tooltip t = new Tooltip(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
            t.setStyle(strTooltipStyle);
            Tooltip.install(IVPano[j], t);
            IVPano[j].setOnMouseClicked((MouseEvent me) -> {
                panePanoramique.setCursor(Cursor.CROSSHAIR);
                panePanoramique.setOnMouseClicked(
                        (MouseEvent me1) -> {
                            gereSourisPanoramique(me1);
                        }
                );
                strPanoListeVignette = nomPano;
                if (panoramiquesProjet[numeroPano].getTitrePanoramique() != null) {
                    String texteHS = panoramiquesProjet[numeroPano].getTitrePanoramique();
                    TextField txtHS = (TextField) vbOutils.lookup("#txtHS" + numHS);
                    txtHS.setText(texteHS);
                }
                panoramiquesProjet[iPanoActuel].getHotspot(numHS).setNumeroPano(numeroPano);
                ComboBox cbx = (ComboBox) vbOutils.lookup("#cbpano" + numHS);
                cbx.setValue(nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")));
                aplistePano.setVisible(false);
                me.consume();
            });
            aplistePano.getChildren().add(IVPano[j]);
            j++;

        }
        int taille = (row + 1) * 65 + 5;
        aplistePano.setPrefWidth(540);
        aplistePano.setPrefHeight(taille);
        aplistePano.setMinWidth(540);
        aplistePano.setMinHeight(taille);
        ImageView IVClose = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
        IVClose.setLayoutX(2);
        IVClose.setLayoutY(5);
        IVClose.setCursor(Cursor.HAND);
        aplistePano.getChildren().add(IVClose);
        IVClose.setOnMouseClicked((MouseEvent me) -> {
            panePanoramique.setCursor(Cursor.CROSSHAIR);
            panePanoramique.setOnMouseClicked(
                    (MouseEvent me1) -> {
                        gereSourisPanoramique(me1);
                    }
            );

            strPanoListeVignette = "";
            aplistePano.setVisible(false);
            me.consume();
        });
        aplistePano.setTranslateZ(2);
        return aplistePano;
    }

    /**
     *
     */
    private void valideHS() {

        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) vbOutils.lookup("#cbpano" + i);
            TextField txtTexteHS = (TextField) vbOutils.lookup("#txtHS" + i);
            if (txtTexteHS != null) {
                panoramiquesProjet[iPanoActuel].getHotspot(i).setInfo(txtTexteHS.getText());
            }
            if (cbx != null) {
                panoramiquesProjet[iPanoActuel].getHotspot(i).setFichierXML(cbx.getValue() + ".xml");
            }
            CheckBox cbAnime = (CheckBox) vbOutils.lookup("#anime" + i);
            if (cbAnime != null) {
                if (cbAnime.isSelected()) {
                    panoramiquesProjet[iPanoActuel].getHotspot(i).setAnime(true);
                } else {
                    panoramiquesProjet[iPanoActuel].getHotspot(i).setAnime(false);
                }
            }
        }
        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspotImage(); i++) {
            TextField txtTexteHS = (TextField) vbOutils.lookup("#txtHSImage" + i);
            if (txtTexteHS != null) {
                panoramiquesProjet[iPanoActuel].getHotspotImage(i).setInfo(txtTexteHS.getText());
            }
            CheckBox cbAnime = (CheckBox) vbOutils.lookup("#animeImage" + i);
            if (cbAnime != null) {
                if (cbAnime.isSelected()) {
                    panoramiquesProjet[iPanoActuel].getHotspotImage(i).setAnime(true);
                } else {
                    panoramiquesProjet[iPanoActuel].getHotspotImage(i).setAnime(false);
                }
            }
        }

    }

    /**
     *
     * @param lstPano
     * @param numPano
     * @return
     */
    public Pane affichageHS(String lstPano, int numPano) {

        Pane panneauHotSpots = new Pane();
        panneauHotSpots.setTranslateY(10);
        panneauHotSpots.setTranslateX(30);
        VBox vb1 = new VBox(5);
        panneauHotSpots.getChildren().add(vb1);
        Label lblPoint;
        Label sep = new Label(" ");
        Label sep1 = new Label(" ");
        int o;
        for (o = 0; o < panoramiquesProjet[numPano].getNombreHotspots(); o++) {
            VBox vbPanneauHS = new VBox();
            double deplacement = 20;
            vbPanneauHS.setLayoutX(deplacement);
            Pane pannneauHS = new Pane(vbPanneauHS);
            pannneauHS.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3;");
            panneauHotSpots.setId("HS" + o);
            lblPoint = new Label("Point n°" + (o + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setTranslateX(-deplacement);
            lblPoint.setStyle("-fx-background-color : #333;");
            lblPoint.setTextFill(Color.WHITE);
            Separator sp = new Separator(Orientation.HORIZONTAL);
            sp.setTranslateX(-deplacement);
            sp.setPrefWidth(300);

            pannneauHS.setPrefWidth(300);
            pannneauHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sp);
            if (lstPano != null) {
                Label lblLien = new Label("Panoramique de destination");
                ComboBox cbDestPano = new ComboBox();
                String[] liste = lstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(liste));
                cbDestPano.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String t, String t1) {
                        valideHS();
                    }
                });
                cbDestPano.setTranslateX(60);
                cbDestPano.setId("cbpano" + o);

                String f1XML = panoramiquesProjet[numPano].getHotspot(o).getFichierXML();
                if (f1XML != null) {
                    cbDestPano.setValue(f1XML.split("\\.")[0]);
                }
                int num = cbDestPano.getSelectionModel().getSelectedIndex();
                vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, sep);
            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            TextField txtTexteHS = new TextField();
            if (panoramiquesProjet[numPano].getHotspot(o).getInfo() != null) {
                txtTexteHS.setText(panoramiquesProjet[numPano].getHotspot(o).getInfo());
            }
            txtTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            txtTexteHS.setId("txtHS" + o);
            txtTexteHS.setPrefSize(200, 25);
            txtTexteHS.setMaxSize(200, 20);
            txtTexteHS.setTranslateX(60);
            CheckBox cbAnime = new CheckBox("HostSpot Animé");
            cbAnime.setId("anime" + o);
            cbAnime.selectedProperty().addListener((final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) -> {
                valideHS();
            });
            if (panoramiquesProjet[numPano].getHotspot(o).isAnime()) {
                cbAnime.setSelected(true);
            }
            cbAnime.setPadding(new Insets(5));
            cbAnime.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, txtTexteHS, cbAnime, sep1);
            vb1.getChildren().addAll(pannneauHS, sep);
        }
        int nbHS = o;
        for (o = 0; o < panoramiquesProjet[numPano].getNombreHotspotImage(); o++) {
            VBox vbPanneauHS = new VBox();
            Pane pannneauHS = new Pane(vbPanneauHS);
            pannneauHS.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3;");
            panneauHotSpots.setId("HSImg" + o);
            lblPoint = new Label("Image n°" + (o + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setStyle("-fx-background-color : #666;");
            lblPoint.setTextFill(Color.WHITE);
            Separator sp = new Separator(Orientation.HORIZONTAL);
            sp.setPrefWidth(300);

            pannneauHS.setPrefWidth(300);
            pannneauHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sp);
            Label lblLien = new Label("Image choisie :");
            String f1XML = panoramiquesProjet[numPano].getHotspotImage(o).getLienImg();
            ImageView IMChoisie = new ImageView(new Image("file:" + strRepertTemp + File.separator + "images" + File.separator + f1XML, 100, -1, true, true));
            IMChoisie.setTranslateX(100);
            vbPanneauHS.getChildren().addAll(lblLien, IMChoisie, sep);
            Label lblTexteHS = new Label("Texte du Hotspot");
            TextField txtTexteHS = new TextField();
            if (panoramiquesProjet[numPano].getHotspotImage(o).getInfo() != null) {
                txtTexteHS.setText(panoramiquesProjet[numPano].getHotspotImage(o).getInfo());
            }
            txtTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            txtTexteHS.setId("txtHSImage" + o);
            txtTexteHS.setPrefSize(200, 25);
            txtTexteHS.setMaxSize(200, 20);
            txtTexteHS.setTranslateX(60);
            CheckBox cbAnime = new CheckBox("HostSpot Animé");
            cbAnime.setId("animeImage" + o);
            cbAnime.selectedProperty().addListener((final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) -> {
                valideHS();
            });
            if (panoramiquesProjet[numPano].getHotspotImage(o).isAnime()) {
                cbAnime.setSelected(true);
            }
            cbAnime.setPadding(new Insets(5));
            cbAnime.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, txtTexteHS, cbAnime, sep1);
            vb1.getChildren().addAll(pannneauHS, sep);
        }
        valideHS();
        nbHS += o;
//        if (nbHS == 0) {
//        } else {
//            btnValider.setVisible(true);
//        }
        return panneauHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane lbl = affichageHS(listePano(iPanoActuel), iPanoActuel);
        lbl.setId("labels");
        vbOutils.getChildren().add(lbl);
        iNumPoints = panoramiquesProjet[iPanoActuel].getNombreHotspots();
        iNumImages = panoramiquesProjet[iPanoActuel].getNombreHotspotImage();
        iNumHTML = panoramiquesProjet[iPanoActuel].getNombreHotspotHTML();
    }

    private ScrollPane afficheLegende() {
        double positionX;
        double positionY;
        AnchorPane apLegende = new AnchorPane();
        ScrollPane spLegende = new ScrollPane(apLegende);
        spLegende.getStyleClass().add("legendePane");

        apLegende.setMinWidth(1000);
        apLegende.setMinHeight(150);
        apLegende.setPrefWidth(1000);
        apLegende.setPrefHeight(150);
        apLegende.setMaxWidth(1000);
        apLegende.setMaxHeight(150);
        positionY = (panePanoramique.getLayoutY() + panePanoramique.getPrefHeight() + 10);

        Circle point = new Circle(30, 20, 5);
        point.setFill(Color.YELLOW);
        point.setStroke(Color.RED);
        point.setCursor(Cursor.DEFAULT);
        Circle point2 = new Circle(30, 60, 5);
        point2.setFill(Color.BLUE);
        point2.setStroke(Color.YELLOW);
        point2.setCursor(Cursor.DEFAULT);
        Circle point3 = new Circle(30, 100, 5);
        point3.setFill(Color.GREEN);
        point3.setStroke(Color.YELLOW);
        point3.setCursor(Cursor.DEFAULT);
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
            15.0, 2.0,
            2.0, 2.0,
            2.0, 15.0,
            -2.0, 15.0,
            -2.0, 2.0,
            -15.0, 2.0,
            -15.0, -2.0,
            -2.0, -2.0,
            -2.0, -15.0,
            2.0, -15.0,
            2.0, -2.0,
            15.0, -2.0
        });
        polygon.setStrokeLineJoin(StrokeLineJoin.MITER);
        polygon.setFill(Color.BLUEVIOLET);
        polygon.setStroke(Color.YELLOW);
        polygon.setId("PoV");
        polygon.setLayoutX(500);
        polygon.setLayoutY(20);
        Label lblHS = new Label(rbLocalisation.getString("main.legendeHS"));
        Label lblHSImage = new Label(rbLocalisation.getString("main.legendeHSImage"));
        //Label lblHSHTML = new Label(rb.getString("main.legendeHSHTML"));
        Label lblPoV = new Label(rbLocalisation.getString("main.legendePoV"));
        Label lblNord = new Label(rbLocalisation.getString("main.legendeNord"));
        Line ligneNord = new Line(500, 45, 500, 65);
        ligneNord.setStroke(Color.RED);
        ligneNord.setStrokeWidth(3);
        lblHS.setLayoutX(50);
        lblHS.setLayoutY(10);
        lblHSImage.setLayoutX(50);
        lblHSImage.setLayoutY(50);
        //lblHSHTML.setLayoutX(50);
        //lblHSHTML.setLayoutY(90);
        lblPoV.setLayoutX(520);
        lblPoV.setLayoutY(10);
        lblNord.setLayoutX(520);
        lblNord.setLayoutY(50);
//        apLegende.getChildren().addAll(lblHS, point, lblHSImage, point2, lblHSHTML, point3, lblPoV, polygon, lblNord, ligneNord);
        apLegende.getChildren().addAll(lblHS, point, lblHSImage, point2, lblPoV, polygon, lblNord, ligneNord);
        apLegende.setId("legende");
        apLegende.setVisible(true);
        if (largeurMax - 50 < 1004) {
            spLegende.setPrefWidth(largeurMax - 50);
            spLegende.setMaxWidth(largeurMax - 50);
            positionX = 25;
        } else {
            spLegende.setPrefWidth(1004);
            spLegende.setMaxWidth(1004);
            positionX = (largeurMax - 1004) / 2.d;
        }
        spLegende.setLayoutX(positionX);
        spLegende.setLayoutY(positionY);
        spLegende.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spLegende.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return spLegende;
    }

    /**
     *
     * @param i
     * @param longitude
     * @param latitude
     */
    private void afficheHS(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.YELLOW);
        point.setStroke(Color.RED);
        point.setId("point" + i);
        point.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(point);

        Tooltip t = new Tooltip("point n° " + (i + 1));
        t.setStyle(strTooltipStyle);
        Tooltip.install(point, t);
        point.setOnDragDetected((MouseEvent me1) -> {
            point.setFill(Color.RED);
            point.setStroke(Color.YELLOW);
            bDragDrop = true;
            me1.consume();

        });
        point.setOnMouseDragged((MouseEvent me1) -> {
            double XX = me1.getX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            point.setCenterX(XX + ivImagePanoramique.getLayoutX());
            double YY = me1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            point.setCenterY(YY);
            me1.consume();

        });
        point.setOnMouseReleased((MouseEvent me1) -> {
            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();
            double mouseX = X1 - ivImagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > ivImagePanoramique.getFitWidth()) {
                mouseX = ivImagePanoramique.getFitWidth();
            }

            double mouseY = Y1 - panePanoramique.getLayoutY() - 109;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > ivImagePanoramique.getFitHeight()) {
                mouseY = ivImagePanoramique.getFitHeight();
            }

            double longit, lat;
            double larg = ivImagePanoramique.getFitWidth();
            String chLong, chLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[iPanoActuel].getHotspot(numeroPoint).setLatitude(lat);
            panoramiquesProjet[iPanoActuel].getHotspot(numeroPoint).setLongitude(longit);
            point.setFill(Color.YELLOW);
            point.setStroke(Color.RED);
            me1.consume();

        });

        point.setOnMouseClicked((MouseEvent me1) -> {
            double mouseX = me1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > ivImagePanoramique.getFitWidth()) {
                mouseX = ivImagePanoramique.getFitWidth();
            }

            double mouseY = me1.getSceneY() - panePanoramique.getLayoutY() - 115;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > ivImagePanoramique.getFitHeight()) {
                mouseY = ivImagePanoramique.getFitHeight();
            }

            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) panePanoramique.lookup("#point" + chPoint);

            if (me1.isControlDown()) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(pt);

                for (int o = numeroPoint + 1; o < iNumPoints; o++) {
                    pt = (Node) panePanoramique.lookup("#point" + Integer.toString(o));
                    pt.setId("point" + Integer.toString(o - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                iNumPoints--;
                panoramiquesProjet[iPanoActuel].removeHotspot(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                me1.consume();
                valideHS();
            } else {
                if (!bDragDrop) {
                    if (iNombrePanoramiques > 1) {
                        AnchorPane listePanoVig = afficherListePanosVignettes(numeroPoint);
                        int largeurVignettes = 4;
                        if (iNombrePanoramiques < 4) {
                            largeurVignettes = iNombrePanoramiques;
                        }
                        if (mouseX + largeurVignettes * 130 > panePanoramique.getWidth()) {
                            listePanoVig.setLayoutX(panePanoramique.getWidth() - largeurVignettes * 130);
                        } else {
                            listePanoVig.setLayoutX(mouseX);
                        }
                        listePanoVig.setLayoutY(mouseY);
                        panePanoramique.getChildren().add(listePanoVig);
                    }
                } else {
                    bDragDrop = false;
                }
                valideHS();
                me1.consume();

            }

        });
    }

    /**
     *
     * @param i
     * @param longitude
     * @param latitude
     */
    private void afficheHSImage(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.BLUE);
        point.setStroke(Color.YELLOW);
        point.setId("img" + i);
        point.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(point);
        Tooltip t = new Tooltip("image n° " + (i + 1));
        t.setStyle(strTooltipStyle);
        Tooltip.install(point, t);
        point.setOnDragDetected((MouseEvent me1) -> {
            point.setFill(Color.YELLOW);
            point.setStroke(Color.BLUE);
            bDragDrop = true;
            me1.consume();

        });
        point.setOnMouseDragged((MouseEvent me1) -> {
            double XX = me1.getX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            point.setCenterX(XX + ivImagePanoramique.getLayoutX());
            double YY = me1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            point.setCenterY(YY);

            me1.consume();

        });
        point.setOnMouseReleased((MouseEvent me1) -> {
            String chPoint = point.getId();
            chPoint = chPoint.substring(3, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();
            double mouseX = X1 - ivImagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > ivImagePanoramique.getFitWidth()) {
                mouseX = ivImagePanoramique.getFitWidth();
            }
            double mouseY = Y1 - panePanoramique.getLayoutY() - 109;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > ivImagePanoramique.getFitHeight()) {
                mouseY = ivImagePanoramique.getFitHeight();
            }

            double longit, lat;
            double larg = ivImagePanoramique.getFitWidth();
            String chLong, chLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[iPanoActuel].getHotspotImage(numeroPoint).setLatitude(lat);
            panoramiquesProjet[iPanoActuel].getHotspotImage(numeroPoint).setLongitude(longit);
            point.setFill(Color.BLUE);
            point.setStroke(Color.YELLOW);
            me1.consume();

        });

        point.setOnMouseClicked((MouseEvent me1) -> {
            String chPoint = point.getId();
            chPoint = chPoint.substring(3, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) panePanoramique.lookup("#img" + chPoint);

            if (me1.isControlDown()) {
                valideHS();
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(pt);

                for (int o = numeroPoint + 1; o < iNumImages; o++) {
                    pt = (Node) panePanoramique.lookup("#img" + Integer.toString(o));
                    pt.setId("img" + Integer.toString(o - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                iNumImages--;
                panoramiquesProjet[iPanoActuel].removeHotspotImage(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                me1.consume();
            } else {
                me1.consume();
            }

        });
    }

    /**
     *
     */
    private void ajouteAffichagePointsHotspots() {

        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspots(); i++) {
            double longitude = panoramiquesProjet[iPanoActuel].getHotspot(i).getLongitude();
            double latitude = panoramiquesProjet[iPanoActuel].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private void ajouteAffichagePointsHotspotsImage() {
        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspotImage(); i++) {
            double longitude = panoramiquesProjet[iPanoActuel].getHotspotImage(i).getLongitude();
            double latitude = panoramiquesProjet[iPanoActuel].getHotspotImage(i).getLatitude();
            afficheHSImage(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private void sauveFichiers() throws IOException {
        if (!bDejaSauve) {
            projetSauve();
        }
    }

    private void panoChoixRegard(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            double largeur = ivImagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            double regardY = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            panoramiquesProjet[iPanoActuel].setLookAtX(regardX);
            panoramiquesProjet[iPanoActuel].setLookAtY(regardY);
            affichePoV(regardX, regardY);
        }
    }

    private void panoChoixNord(double X) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            double mouseX = X;
            double largeur = ivImagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            panoramiquesProjet[iPanoActuel].setZeroNord(regardX);
            afficheNord(regardX);
        }
    }

    private void afficheNord(double longitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        Node ancPoV = (Node) panePanoramique.lookup("#Nord");
        if (ancPoV != null) {
            panePanoramique.getChildren().remove(ancPoV);
        }
        Line ligne = new Line(0, 0, 0, ivImagePanoramique.getFitHeight());
        ligne.setCursor(Cursor.DEFAULT);

        ligne.setLayoutX(X);
        ligne.setStroke(Color.RED);
        ligne.setStrokeWidth(4);
        ligne.setId("Nord");
        ligne.setOnDragDetected((MouseEvent me1) -> {
            ligne.setStroke(Color.BLUEVIOLET);
            bDragDrop = true;
            me1.consume();
        });
        ligne.setOnMouseDragged((MouseEvent me1) -> {

            double XX = me1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            ligne.setLayoutX(XX + ivImagePanoramique.getLayoutX());
            me1.consume();

        });
        ligne.setOnMouseReleased((MouseEvent me1) -> {
            double X1 = me1.getSceneX();
            double mouseX1 = X1 - ivImagePanoramique.getLayoutX();
            if (mouseX1 < 0) {
                mouseX1 = 0;
            }
            if (mouseX1 > ivImagePanoramique.getFitWidth()) {
                mouseX1 = ivImagePanoramique.getFitWidth();
            }
            double regardX = 360.0f * mouseX1 / largeur - 180;
            panoramiquesProjet[iPanoActuel].setZeroNord(regardX);
            ligne.setStroke(Color.RED);
            me1.consume();

        });

        panePanoramique.getChildren().add(ligne);
    }

    private void affichePoV(double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Node ancPoV = (Node) panePanoramique.lookup("#PoV");
        if (ancPoV != null) {
            panePanoramique.getChildren().remove(ancPoV);
        }
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
            20.0, 2.0,
            2.0, 2.0,
            2.0, 20.0,
            -2.0, 20.0,
            -2.0, 2.0,
            -20.0, 2.0,
            -20.0, -2.0,
            -2.0, -2.0,
            -2.0, -20.0,
            2.0, -20.0,
            2.0, -2.0,
            20.0, -2.0
        });
        polygon.setStrokeLineJoin(StrokeLineJoin.MITER);
        polygon.setFill(Color.BLUEVIOLET);
        polygon.setStroke(Color.YELLOW);
        polygon.setId("PoV");
        polygon.setLayoutX(X);
        polygon.setLayoutY(Y);
        polygon.setCursor(Cursor.DEFAULT);
        polygon.setOnDragDetected((MouseEvent me1) -> {
            polygon.setFill(Color.YELLOW);
            polygon.setStroke(Color.BLUEVIOLET);
            bDragDrop = true;
            me1.consume();

        });
        polygon.setOnMouseDragged((MouseEvent me1) -> {

            double XX = me1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            polygon.setLayoutX(XX + ivImagePanoramique.getLayoutX());
            double YY = me1.getSceneY() - panePanoramique.getLayoutY() - 109;
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            polygon.setLayoutY(YY);

            me1.consume();

        });
        polygon.setOnMouseReleased((MouseEvent me1) -> {
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();
            double mouseX1 = X1 - ivImagePanoramique.getLayoutX();
            if (mouseX1 < 0) {
                mouseX1 = 0;
            }
            if (mouseX1 > ivImagePanoramique.getFitWidth()) {
                mouseX1 = ivImagePanoramique.getFitWidth();
            }
            double mouseY1 = Y1 - panePanoramique.getLayoutY() - 109;
            if (mouseY1 < 0) {
                mouseY1 = 0;
            }
            if (mouseY1 > ivImagePanoramique.getFitHeight()) {
                mouseY1 = ivImagePanoramique.getFitHeight();
            }
            double regardX = 360.0f * mouseX1 / largeur - 180;
            double regardY = 90.0d - 2.0f * mouseY1 / largeur * 180.0f;
            panoramiquesProjet[iPanoActuel].setLookAtX(regardX);
            panoramiquesProjet[iPanoActuel].setLookAtY(regardY);
            polygon.setFill(Color.BLUEVIOLET);
            polygon.setStroke(Color.YELLOW);
            me1.consume();

        });

        panePanoramique.getChildren().add(polygon);
    }

    private void panoMouseClic(double X, double Y) {

        if (iNombrePanoramiques > 1) {
            valideHS();
            bDejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
                double longitude, latitude;
                double largeur = ivImagePanoramique.getFitWidth();
                String chLong, chLat;
                longitude = 360.0f * mouseX / largeur - 180;
                latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                Circle point = new Circle(mouseX + ivImagePanoramique.getLayoutX(), mouseY, 5);
                point.setFill(Color.YELLOW);
                point.setStroke(Color.RED);
                point.setId("point" + iNumPoints);
                point.setCursor(Cursor.DEFAULT);
                panePanoramique.getChildren().add(point);
                Tooltip t = new Tooltip("point n°" + (iNumPoints + 1));
                t.setStyle(strTooltipStyle);
                Tooltip.install(point, t);
                HotSpot HS = new HotSpot();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                panoramiquesProjet[iPanoActuel].addHotspot(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(listePano(iPanoActuel), iPanoActuel);
                affHS1.setId("labels");
                vbOutils.getChildren().add(affHS1);

                iNumPoints++;
                if (iNombrePanoramiques > 1) {

                    AnchorPane listePanoVig = afficherListePanosVignettes(panoramiquesProjet[iPanoActuel].getNombreHotspots() - 1);
                    int largeurVignettes = 4;
                    if (iNombrePanoramiques < 4) {
                        largeurVignettes = iNombrePanoramiques;
                    }
                    if (mouseX + largeurVignettes * 130 > panePanoramique.getWidth()) {
                        listePanoVig.setLayoutX(panePanoramique.getWidth() - largeurVignettes * 130);
                    } else {
                        listePanoVig.setLayoutX(mouseX);
                    }
                    listePanoVig.setLayoutY(mouseY);
                    panePanoramique.getChildren().add(listePanoVig);
                    valideHS();
                }
                point.setOnDragDetected((MouseEvent me1) -> {
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(5, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    Node pt;
                    pt = (Node) panePanoramique.lookup("#point" + chPoint);
                    point.setFill(Color.RED);
                    point.setStroke(Color.YELLOW);
                    bDragDrop = true;
                    me1.consume();

                });
                point.setOnMouseDragged((MouseEvent me1) -> {
                    double XX = me1.getX() - ivImagePanoramique.getLayoutX();
                    if (XX < 0) {
                        XX = 0;
                    }
                    if (XX > ivImagePanoramique.getFitWidth()) {
                        XX = ivImagePanoramique.getFitWidth();
                    }
                    point.setCenterX(XX + ivImagePanoramique.getLayoutX());
                    double YY = me1.getY();
                    if (YY < 0) {
                        YY = 0;
                    }
                    if (YY > ivImagePanoramique.getFitHeight()) {
                        YY = ivImagePanoramique.getFitHeight();
                    }
                    point.setCenterY(YY);

                    me1.consume();

                });
                point.setOnMouseReleased((MouseEvent me1) -> {
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(5, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    double X1 = me1.getSceneX();
                    double Y1 = me1.getSceneY();
                    double mouseX1 = X1 - ivImagePanoramique.getLayoutX();
                    if (mouseX1 < 0) {
                        mouseX1 = 0;
                    }
                    if (mouseX1 > ivImagePanoramique.getFitWidth()) {
                        mouseX1 = ivImagePanoramique.getFitWidth();
                    }
                    double mouseY1 = Y1 - panePanoramique.getLayoutY() - 109;
                    if (mouseY1 < 0) {
                        mouseY1 = 0;
                    }
                    if (mouseY1 > ivImagePanoramique.getFitHeight()) {
                        mouseY1 = ivImagePanoramique.getFitHeight();
                    }
                    double longit, lat;
                    double larg = ivImagePanoramique.getFitWidth();
                    longit = 360.0f * mouseX1 / larg - 180;
                    lat = 90.0d - 2.0f * mouseY1 / larg * 180.0f;
                    panoramiquesProjet[iPanoActuel].getHotspot(numeroPoint).setLatitude(lat);
                    panoramiquesProjet[iPanoActuel].getHotspot(numeroPoint).setLongitude(longit);
                    point.setFill(Color.YELLOW);
                    point.setStroke(Color.RED);
                    me1.consume();

                });

                point.setOnMouseClicked((MouseEvent me1) -> {
                    if (me1.isControlDown()) {
                        bDejaSauve = false;
                        stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                        String chPoint = point.getId();
                        chPoint = chPoint.substring(5, chPoint.length());
                        int numeroPoint = Integer.parseInt(chPoint);
                        Node pt;
                        pt = (Node) panePanoramique.lookup("#point" + chPoint);
                        panePanoramique.getChildren().remove(pt);

                        for (int o = numeroPoint + 1; o < iNumPoints; o++) {
                            pt = (Node) panePanoramique.lookup("#point" + Integer.toString(o));
                            pt.setId("point" + Integer.toString(o - 1));
                        }
                        /**
                         * on retire les anciennes indication de HS
                         */
                        retireAffichageHotSpots();
                        iNumPoints--;
                        panoramiquesProjet[iPanoActuel].removeHotspot(numeroPoint);
                        /**
                         * On les crée les nouvelles
                         */
                        ajouteAffichageHotspots();
                        valideHS();
                        me1.consume();
                    } else {
                        if (!bDragDrop) {
                            String chPoint = point.getId();
                            chPoint = chPoint.substring(5, chPoint.length());
                            int numeroPoint = Integer.parseInt(chPoint);
                            if (iNombrePanoramiques > 1) {
                                AnchorPane listePanoVig = afficherListePanosVignettes(numeroPoint);
                                int largeurVignettes = 4;
                                if (iNombrePanoramiques < 4) {
                                    largeurVignettes = iNombrePanoramiques;
                                }
                                if (mouseX + largeurVignettes * 130 > panePanoramique.getWidth()) {
                                    listePanoVig.setLayoutX(panePanoramique.getWidth() - largeurVignettes * 130);
                                } else {
                                    listePanoVig.setLayoutX(mouseX);
                                }
                                listePanoVig.setLayoutY(mouseY);
                                panePanoramique.getChildren().add(listePanoVig);
                            }
                        } else {
                            bDragDrop = false;
                        }
                        valideHS();
                        me1.consume();

                    }
                });
            }
        }
    }

    private void panoAjouteImage(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {

            valideHS();
            bDejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 115;
            double longitude, latitude;
            double largeur = ivImagePanoramique.getFitWidth();
            String chLong, chLat;
            longitude = 360.0f * mouseX / largeur - 180;
            latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            Circle point = new Circle(mouseX, mouseY, 5);
            point.setFill(Color.BLUE);
            point.setStroke(Color.YELLOW);
            point.setId("img" + iNumImages);
            point.setCursor(Cursor.DEFAULT);
            panePanoramique.getChildren().add(point);
            Tooltip t = new Tooltip("image n° " + (iNumImages + 1));
            t.setStyle(strTooltipStyle);
            Tooltip.install(point, t);

//
            File repert;
            if (strRepertHSImages.equals("")) {
                repert = new File(strCurrentDir + File.separator);
            } else {
                repert = new File(strRepertHSImages);
            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

            fileChooser.setInitialDirectory(repert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fichierImage = fileChooser.showOpenDialog(null);
            if (fichierImage != null) {
                strRepertHSImages = fichierImage.getParent();
                iNumImages++;
                HotspotImage HS = new HotspotImage();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                HS.setUrlImage(fichierImage.getAbsolutePath());
                HS.setLienImg(fichierImage.getName());
                HS.setInfo(fichierImage.getName().split("\\.")[0]);
                File repertImage = new File(strRepertTemp + File.separator + "images");
                if (!repertImage.exists()) {
                    repertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fichierImage.getAbsolutePath(), repertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                panoramiquesProjet[iPanoActuel].addHotspotImage(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(listePano(iPanoActuel), iPanoActuel);
                affHS1.setId("labels");
                vbOutils.getChildren().add(affHS1);

            } else {
                String chPoint = point.getId();
                chPoint = chPoint.substring(3, chPoint.length());
                Node pt = (Node) panePanoramique.lookup("#img" + chPoint);
                panePanoramique.getChildren().remove(pt);
            }
            valideHS();
            point.setOnDragDetected((MouseEvent me1) -> {
                point.setFill(Color.YELLOW);
                point.setStroke(Color.BLUE);
                bDragDrop = true;
                me1.consume();

            });
            point.setOnMouseDragged((MouseEvent me1) -> {
                double XX = me1.getX() - ivImagePanoramique.getLayoutX();
                if (XX < 0) {
                    XX = 0;
                }
                if (XX > ivImagePanoramique.getFitWidth()) {
                    XX = ivImagePanoramique.getFitWidth();
                }
                point.setCenterX(XX + ivImagePanoramique.getLayoutX());
                double YY = me1.getY();
                if (YY < 0) {
                    YY = 0;
                }
                if (YY > ivImagePanoramique.getFitHeight()) {
                    YY = ivImagePanoramique.getFitHeight();
                }
                point.setCenterY(YY);

                me1.consume();

            });
            point.setOnMouseReleased((MouseEvent me1) -> {
                String chPoint = point.getId();
                chPoint = chPoint.substring(3, chPoint.length());
                int numeroPoint = Integer.parseInt(chPoint);
                double X1 = me1.getSceneX();
                double Y1 = me1.getSceneY();
                double mouseX1 = X1 - ivImagePanoramique.getLayoutX();
                if (mouseX1 < 0) {
                    mouseX1 = 0;
                }
                if (mouseX1 > ivImagePanoramique.getFitWidth()) {
                    mouseX1 = ivImagePanoramique.getFitWidth();
                }

                double mouseY1 = Y1 - panePanoramique.getLayoutY() - 109;
                if (mouseY1 < 0) {
                    mouseY1 = 0;
                }
                if (mouseY1 > ivImagePanoramique.getFitHeight()) {
                    mouseY1 = ivImagePanoramique.getFitHeight();
                }

                double longit, lat;
                double larg = ivImagePanoramique.getFitWidth();
                longit = 360.0f * mouseX1 / larg - 180;
                lat = 90.0d - 2.0f * mouseY1 / larg * 180.0f;
                panoramiquesProjet[iPanoActuel].getHotspotImage(numeroPoint).setLatitude(lat);
                panoramiquesProjet[iPanoActuel].getHotspotImage(numeroPoint).setLongitude(longit);
                point.setFill(Color.BLUE);
                point.setStroke(Color.YELLOW);
                me1.consume();

            });

            point.setOnMouseClicked((MouseEvent me1) -> {
                if (me1.isControlDown()) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(3, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    Node pt;
                    pt = (Node) panePanoramique.lookup("#img" + chPoint);
                    panePanoramique.getChildren().remove(pt);

                    for (int o = numeroPoint + 1; o < iNumImages; o++) {
                        pt = (Node) panePanoramique.lookup("#img" + Integer.toString(o));
                        pt.setId("img" + Integer.toString(o - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    iNumImages--;
                    panoramiquesProjet[iPanoActuel].removeHotspotImage(numeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                }
                valideHS();
                me1.consume();
            });

        }
    }

    private void gereSourisPanoramique(MouseEvent me) {
        if (me.getButton() == MouseButton.SECONDARY) {
            if (me.isShiftDown()) {
                panoChoixNord(me.getSceneX() - ivImagePanoramique.getLayoutX());
                me.consume();
            } else if (me.isControlDown()) {
            } else {
                panoChoixRegard(me.getSceneX() - ivImagePanoramique.getLayoutX(), me.getSceneY());
                me.consume();
            }
        }
        if (me.getButton() == MouseButton.PRIMARY) {
            if (me.isShiftDown()) {
                if (!me.isControlDown()) {
                    if (!bDragDrop) {
                        panoAjouteImage(me.getSceneX() - ivImagePanoramique.getLayoutX(), me.getSceneY());
                    } else {
                        bDragDrop = false;
                    }
                } else {

                }
            } else if (!(me.isControlDown()) && bEstCharge) {
                if (!bDragDrop) {
                    panoMouseClic(me.getSceneX() - ivImagePanoramique.getLayoutX(), me.getSceneY());
                } else {
                    bDragDrop = false;
                }
            }
        }
    }

    /**
     *
     */
    private void installeEvenements() {
        /**
         *
         */
        scnPrincipale.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            double largeur = (double) newSceneWidth - 320.0d;
            spVuePanoramique.setPrefWidth(largeur);
        });
        /**
         *
         */
        scnPrincipale.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
            spVuePanoramique.setPrefHeight((double) newSceneHeight - 70.0d);
            spPanneauOutils.setPrefHeight((double) newSceneHeight - 70.0d);
        });

        /**
         *
         */
        panePanoramique.setOnMouseClicked(
                (MouseEvent me) -> {
                    gereSourisPanoramique(me);
                }
        );
        /**
         *
         */
        panePanoramique.setOnMouseMoved(
                (MouseEvent me) -> {
                    if (bEstCharge) {
                        double mouseX = me.getSceneX() - ivImagePanoramique.getLayoutX();
                        if (mouseX < 0) {
                            mouseX = 0;
                        }
                        if (mouseX > ivImagePanoramique.getFitWidth()) {
                            mouseX = ivImagePanoramique.getFitWidth();
                        }
                        double mouseY = me.getSceneY() - panePanoramique.getLayoutY() - 109;
                        if (mouseY < 0) {
                            mouseY = 0;
                        }
                        if (mouseY > ivImagePanoramique.getFitHeight()) {
                            mouseY = ivImagePanoramique.getFitHeight();
                        }

                        double longitude, latitude;
                        double largeur = ivImagePanoramique.getFitWidth() * panePanoramique.getScaleX();
                        longitude = 360.0f * mouseX / largeur - 180;
                        latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                        String chLong = "Long : " + String.format("%.1f", longitude);
                        String chLat = "Lat : " + String.format("%.1f", latitude);
                        lblLong.setText(chLong);
                        lblLat.setText(chLat);
                    }
                }
        );
        /*
        
         */
        cbListeChoixPanoramiqueEntree.valueProperty()
                .addListener(new ChangeListenerImplEntree());
        cbListeChoixPanoramique.valueProperty()
                .addListener(new ChangeListenerImpl());

    }

    private class ChangeListenerImpl implements ChangeListener<String> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
            if (nouvelleValeur != null) {
                if (!(nouvelleValeur.equals(strPanoAffiche))) {
                    clickBtnValidePano();
                    valideHS();
                    strPanoAffiche = nouvelleValeur;
                    int numPanoChoisit = cbListeChoixPanoramique.getSelectionModel().getSelectedIndex();
                    affichePanoChoisit(numPanoChoisit);
                }
            }

        }
    }

    private class ChangeListenerImplEntree implements ChangeListener<String> {

        public ChangeListenerImplEntree() {
        }

        @Override
        public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
            if ((nouvelleValeur != null) && (!nouvelleValeur.equals(""))) {
                strPanoEntree = nouvelleValeur.split("\\.")[0];
            }

        }
    }

    private void ajouteAffichageLignes() {
        double largeur = ivImagePanoramique.getFitWidth();
        double hauteur = largeur / 2.0d;
        Line ligne;
        int x, y;
        int nl = 0;
        for (int i = -180; i < 180; i += 10) {
            x = (int) (largeur / 2.0f + largeur / 360.0f * (float) i + ivImagePanoramique.getLayoutX());
            ligne = new Line(x, 0, x, hauteur);
            ligne.setId("ligne" + nl);
            nl++;
            ligne.setStroke(Color.ORANGE);
            if (i == 0) {
                ligne.setStroke(Color.WHITE);
                ligne.setStrokeWidth(0.5);
            } else {
                if ((i % 20) == 0) {
                    ligne.setStroke(Color.WHITE);
                    ligne.setStrokeWidth(0.25);
                } else {
                    ligne.setStroke(Color.GRAY);
                    ligne.setStrokeWidth(0.25);
                }
            }
            panePanoramique.getChildren().add(ligne);
        }
        for (int i = -90; i < 90; i += 10) {
            y = (int) (hauteur / 2.0f + hauteur / 180.0f * (float) i);
            ligne = new Line(ivImagePanoramique.getLayoutX(), y, largeur + ivImagePanoramique.getLayoutX(), y);
            ligne.setId("ligne" + nl);
            nl++;
            if (i == 0) {
                ligne.setStroke(Color.WHITE);
                ligne.setStrokeWidth(0.5);
            } else {
                if ((i % 20) == 0) {
                    ligne.setStroke(Color.WHITE);
                    ligne.setStrokeWidth(0.25);
                } else {
                    ligne.setStroke(Color.GRAY);
                    ligne.setStrokeWidth(0.25);
                }
            }

            panePanoramique.getChildren().add(ligne);
        }

    }

    /**
     *
     */
    private void retireAffichageLigne() {
        int i = 0;
        Node lg;
        do {
            lg = (Node) panePanoramique.lookup("#ligne" + i);
            if (lg != null) {
                panePanoramique.getChildren().remove(lg);
            }
            i++;
        } while (lg != null);
    }

    /**
     *
     * @param numPanochoisi
     */
    @SuppressWarnings("empty-statement")
    private void affichePanoChoisit(int numPanochoisi) {
        ivImagePanoramique.setImage(panoramiquesProjet[numPanochoisi].getImagePanoramique());
        Node legende = (Node) apPanneauPrincipal.lookup("#legende");
        legende.setVisible(true);
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        retireAffichageLigne();
        iNumPoints = 0;

        iPanoActuel = numPanochoisi;
        ajouteAffichageHotspots();
        affichePoV(panoramiquesProjet[numPanochoisi].getLookAtX(), panoramiquesProjet[numPanochoisi].getLookAtY());
        afficheNord(panoramiquesProjet[numPanochoisi].getZeroNord());
        ajouteAffichagePointsHotspots();
        ajouteAffichagePointsHotspotsImage();

        ajouteAffichageLignes();
        afficheInfoPano();
    }

    private void afficheInfoPano() {
        TextField txtTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
//        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
//        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
//        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
//        RadioButton radCube = (RadioButton) scene.lookup("#radcube");

        if (panoramiquesProjet[iPanoActuel].getTitrePanoramique() != null) {
            txtTitrePano.setText(panoramiquesProjet[iPanoActuel].getTitrePanoramique());
        } else {
            txtTitrePano.setText("");
        }
//        chkAfficheInfo.setSelected(panoramiquesProjet[panoActuel].isAfficheInfo());
//        chkAfficheTitre.setSelected(panoramiquesProjet[panoActuel].isAfficheTitre());
//        if (panoramiquesProjet[panoActuel].getTypePanoramique().equals(Panoramique.SPHERE)) {
//            radSphere.setSelected(true);
//            radCube.setSelected(false);
//        } else {
//            radCube.setSelected(true);
//            radSphere.setSelected(false);
//        }

    }

    /**
     *
     * @param fichierPano
     */
    @SuppressWarnings("empty-statement")
    private void ajoutePanoramiqueProjet(String fichierPano, String typePano) throws InterruptedException {
        bPanoCharge = true;
        bEstCharge = true;
        Panoramique panoCree = new Panoramique();
        //System.out.println("Type pano : " + typePano);
        if (typePano.equals(Panoramique.SPHERE)) {
            //System.out.println("sphere");
            panoCree.setNomFichier(fichierPano);
            String extension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
            String nom = fichierPano.substring(0, fichierPano.length() - 4);
            int nombreNiveaux = creeNiveauxImageEqui(fichierPano, strRepertPanos);
            panoCree.setNomFichier(fichierPano);
            Image image3 = null;
            if ("tif".equals(extension)) {
                try {
                    imgPanoRetaille2 = ReadWriteImage.readTiff(fichierPano);
                    imgPanoRetaille2 = ReadWriteImage.resizeImage(imgPanoRetaille2, 1200, 600);
                    image3 = ReadWriteImage.readTiff(fichierPano);
                    image3 = ReadWriteImage.resizeImage(image3, 300, 150);
                } catch (ImageReadException | IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                imgPanoRetaille2 = new Image("file:" + fichierPano, 1200, 600, true, true, true);
                image3 = new Image("file:" + fichierPano, 300, 150, true, true, true);
            }
            panoCree.setNombreNiveaux(nombreNiveaux);
            panoCree.setVignettePanoramique(image3);
            panoCree.setImagePanoramique(imgPanoRetaille2);
            panoCree.setTypePanoramique(Panoramique.SPHERE);
        }
        if (typePano.equals(Panoramique.CUBE)) {
            //System.out.println("sphere");
            panoCree.setNomFichier(fichierPano);
            panoCree.setNomFichier(fichierPano);
            String nom = fichierPano.substring(0, fichierPano.length() - 6);
            String extension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
            Image top = null;
            Image bottom = null;
            Image left = null;
            Image right = null;
            Image front = null;
            Image behind = null;
            if (extension.equals("bmp")) {
                top = new Image("file:" + nom + "_u.bmp");
                bottom = new Image("file:" + nom + "_d.bmp");
                left = new Image("file:" + nom + "_l.bmp");
                right = new Image("file:" + nom + "_r.bmp");
                front = new Image("file:" + nom + "_f.bmp");
                behind = new Image("file:" + nom + "_b.bmp");
            }
            if (extension.equals("jpg")) {
                top = new Image("file:" + nom + "_u.jpg");
                bottom = new Image("file:" + nom + "_d.jpg");
                left = new Image("file:" + nom + "_l.jpg");
                right = new Image("file:" + nom + "_r.jpg");
                front = new Image("file:" + nom + "_f.jpg");
                behind = new Image("file:" + nom + "_b.jpg");
            }
            if (extension.equals("tif")) {
                try {
                    top = ReadWriteImage.readTiff(nom + "_u.tif");
                    bottom = ReadWriteImage.readTiff(nom + "_d.tif");
                    left = ReadWriteImage.readTiff(nom + "_l.tif");
                    right = ReadWriteImage.readTiff(nom + "_r.tif");
                    front = ReadWriteImage.readTiff(nom + "_f.tif");
                    behind = ReadWriteImage.readTiff(nom + "_b.tif");
                } catch (ImageReadException | IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int nombreNiveaux = creeNiveauxImageCube(nom + "_u." + extension, strRepertPanos, "dessus");
            creeNiveauxImageCube(nom + "_d." + extension, strRepertPanos, "dessous");
            creeNiveauxImageCube(nom + "_l." + extension, strRepertPanos, "gauche");
            creeNiveauxImageCube(nom + "_r." + extension, strRepertPanos, "droite");
            creeNiveauxImageCube(nom + "_f." + extension, strRepertPanos, "devant");
            creeNiveauxImageCube(nom + "_b." + extension, strRepertPanos, "derrière");
            panoCree.setNombreNiveaux(nombreNiveaux);

            Image equiRectangulaire = TransformationsPanoramique.cube2rect(front, left, right, behind, top, bottom, 1200);
            Image vignetteEquiRectangulaire = TransformationsPanoramique.cube2rect(front, left, right, behind, top, bottom, 300);
            panoCree.setImagePanoramique(equiRectangulaire);
            panoCree.setVignettePanoramique(vignetteEquiRectangulaire);
            panoCree.setTypePanoramique(Panoramique.CUBE);
        }

        panoCree.setLookAtX(0.0d);
        panoCree.setLookAtY(0.0d);
        panoCree.setAfficheInfo(true);
        panoCree.setAfficheTitre(true);
        panoramiquesProjet[iNombrePanoramiques] = panoCree;
        iPanoActuel = iNombrePanoramiques;
        iNombrePanoramiques++;
        //System.out.println("Fin traitement - Début Affichage");
    }

    /**
     *
     */
    private void transformationCube2Equi() {
        EquiCubeDialogController C2EController = new EquiCubeDialogController();
        try {
            C2EController.afficheFenetre(EquiCubeDialogController.CUBE2QUI);

        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void transformationEqui2Cube() {
        EquiCubeDialogController E2CController = new EquiCubeDialogController();
        try {
            E2CController.afficheFenetre(EquiCubeDialogController.EQUI2CUBE);

        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void modeleSauver() throws IOException {
        File fichTemplate;
        FileChooser repertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier Modèle panoVisu (*.tpl)", "*.tpl");
        repertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(strRepertAppli + File.separator + "templates");
        if (!repert.exists()) {
            repert.mkdirs();
        }
        repertChoix.setInitialDirectory(repert);
        fichTemplate = repertChoix.showSaveDialog(null);

        if (fichTemplate != null) {
            String contenuFichier = gestionnaireInterface.getTemplate();
            fichTemplate.setWritable(true);
            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fichTemplate), "UTF-8");
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contenuFichier);
            }
            Dialogs.create().title("Sauvegarde du fichier de Modèle")
                    .message("Votre modèle à bien été sauvegardé")
                    .showInformation();
        }

    }

    /**
     *
     */
    private void modeleCharger() throws IOException {
        FileChooser repertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.tpl)", "*.tpl");
        repertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(strRepertAppli + File.separator + "templates");
        repertChoix.setInitialDirectory(repert);
        File fichtemplate = repertChoix.showOpenDialog(stPrincipal);
        if (fichtemplate != null) {
            try {
                List<String> texte = new ArrayList<>();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(fichtemplate), "UTF-8"));

                String ligneTexte;
                while ((ligneTexte = br.readLine()) != null) {
                    texte.add(ligneTexte);
                }
                //System.out.println(texte);
                gestionnaireInterface.setTemplate(texte);
                gestionnaireInterface.afficheTemplate();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private static ObservableList<AncreForme> creeAncresPourPolygone(int numZone,
            final ObservableList<Double> points) {
        ObservableList<AncreForme> anchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i += 2) {
            final int idx = i;

            DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

            xProperty.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
                points.set(idx, (double) x);
                String chaine = "";
                for (Double point : points) {
                    chaine += point.toString() + ",";
                }
                chaine = chaine.substring(0, chaine.length() - 1);
                zones[numZone].setStrCoordonneesZone(chaine);
            });

            yProperty.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
                points.set(idx + 1, (double) y);
                String chaine = "";
                for (Double point : points) {
                    chaine += point.toString() + ",";
                }
                chaine = chaine.substring(0, chaine.length() - 1);
                zones[numZone].setStrCoordonneesZone(chaine);
            });

            anchors.add(new AncreForme(Color.GOLD, xProperty, yProperty));
        }

        return anchors;
    }

    private static ObservableList<AncreForme> creeAncresPourCercle(int numZone,
            Circle cercle) {
        ObservableList<AncreForme> anchors = FXCollections.observableArrayList();

        DoubleProperty xProperty1 = new SimpleDoubleProperty(cercle.getCenterX());
        DoubleProperty yProperty1 = new SimpleDoubleProperty(cercle.getCenterY());
        anchors.add(new AncreForme(Color.GOLD, xProperty1, yProperty1));
        DoubleProperty xProperty2 = new SimpleDoubleProperty(cercle.getCenterX() + cercle.getRadius());
        DoubleProperty yProperty2 = new SimpleDoubleProperty(cercle.getCenterY());
        final AncreForme ancRayon = new AncreForme(Color.BLUEVIOLET, xProperty2, yProperty2);
        anchors.add(ancRayon);

        //System.out.println("==>" + xProperty1 + "," + yProperty1);
        xProperty1.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double dX = (double) x - cercle.getCenterX();
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setCenterX((double) x);
            ancRayon.setCenterX(ancRayon.getCenterX() + dX);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);

        });

        yProperty1.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double dY = -cercle.getCenterY() + (double) y;
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setCenterY((double) y);
            ancRayon.setCenterY(ancRayon.getCenterY() + dY);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        //System.out.println("==>" + xProperty2 + "," + yProperty2);
        xProperty2.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - (double) x, 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setRadius(rayon);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        yProperty2.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - (double) y, 2.d));
            cercle.setRadius(rayon);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        return anchors;
    }

    private static ObservableList<AncreForme> creeAncresPourRectangle(int numZone,
            Rectangle rect) {
        ObservableList<AncreForme> anchors = FXCollections.observableArrayList();

        DoubleProperty xProperty1 = new SimpleDoubleProperty(rect.getX());
        DoubleProperty yProperty1 = new SimpleDoubleProperty(rect.getY());
        DoubleProperty xProperty2 = new SimpleDoubleProperty(rect.getWidth() + rect.getX());
        DoubleProperty yProperty2 = new SimpleDoubleProperty(rect.getHeight() + rect.getY());
        anchors.add(new AncreForme(Color.GOLD, xProperty1, yProperty1));
        AncreForme ancrePoint2 = new AncreForme(Color.BLUEVIOLET, xProperty2, yProperty2);
        anchors.add(ancrePoint2);

        //System.out.println("==>" + xProperty1 + "," + yProperty1);
        xProperty1.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double dX = -rect.getX() + (double) x;
            rect.setX((double) x);
            ancrePoint2.setCenterX(ancrePoint2.getCenterX() + dX);
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        yProperty1.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double dY = -rect.getY() + (double) y;
            rect.setY((double) y);
            ancrePoint2.setCenterY(ancrePoint2.getCenterY() + dY);
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        //System.out.println("==>" + xProperty2 + "," + yProperty2);
        xProperty2.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            rect.setWidth((double) x - rect.getX());
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        yProperty2.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            rect.setHeight((double) y - rect.getY());
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[numZone].setStrCoordonneesZone(chaine);
        });

        return anchors;
    }

    private static void choixZone(int largeur, int hauteur, boolean bMasqueZones, String idZone, MouseEvent t) {
        ComboBox cbTouchesBarre = new ComboBox();
        cbTouchesBarre.getItems().clear();
        for (int i = 0; i < strTouchesBarre.length; i++) {
            cbTouchesBarre.getItems().add(i, strTouchesBarre[i]);
        }
        cbTouchesBarre.setLayoutX(200);
        cbTouchesBarre.setLayoutX(40);

        final int numeroZone = Integer.parseInt(idZone.split("-")[1]);
        if (t.getButton().equals(MouseButton.PRIMARY)) {
            if (t.getClickCount() == 2) {
                for (int j = numeroZone; j < iNombreZones - 1; j++) {
                    zones[j] = zones[j + 1];
                }
                iNombreZones--;
                afficheBarrePersonnalisee(largeur, hauteur, bMasqueZones);
            } else {
                afficheBarrePersonnalisee(largeur, hauteur, bMasqueZones);
                apZoneBarrePersonnalisee.getChildren().clear();
                apZoneBarrePersonnalisee.getChildren().add(cbTouchesBarre);
                ZoneTelecommande zone = zones[numeroZone];
                int index = -1;
                for (int j = 0; j < strCodeBarre.length; j++) {
                    if (strCodeBarre[j].equals(zone.getStrIdZone())) {
                        index = j;
                    }
                    System.out.println(strCodeBarre[j] + " " + zone.getStrIdZone() + " index :" + index);
                }
                if (index != -1) {
                    cbTouchesBarre.getSelectionModel().select(index);
                }

                cbTouchesBarre.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                        if (nouvelleValeur != null) {
                            String strId = strCodeBarre[cbTouchesBarre.getSelectionModel().getSelectedIndex()];
                            zones[numeroZone].setStrIdZone(strId);
                        }
                    }
                });

                Label lblTypeBarre = new Label(zone.getStrTypeZone());
                lblTypeBarre.setLayoutX(20);
                lblTypeBarre.setLayoutY(40);

//                Label lblIdBarre = new Label(zone.getStrIdZone());
//                lblIdBarre.setLayoutX(20);
//                lblIdBarre.setLayoutY(10);
                Label lblCoordsBarre = new Label(zone.getStrCoordonneesZone());
                lblCoordsBarre.setLayoutX(20);
                lblCoordsBarre.setLayoutY(70);
                lblCoordsBarre.setPrefWidth(260);
                lblCoordsBarre.setMaxWidth(260);
                lblCoordsBarre.setWrapText(true);
                apZoneBarrePersonnalisee.getChildren()
                        .addAll(lblTypeBarre, lblCoordsBarre);
                switch (zone.getStrTypeZone()) {
                    case "poly":
                        Polygon poly = (Polygon) apImgBarrePersonnalisee.lookup("#" + idZone);
                        poly.setFill(Color.rgb(255, 0, 0, 0.5));
                        poly.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourPolygone(numeroZone, poly.getPoints()));
                        break;
                    case "rect":
                        Rectangle rect = (Rectangle) apImgBarrePersonnalisee.lookup("#" + idZone);
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        rect.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourRectangle(numeroZone, rect));
                        break;
                    case "circle":
                        Circle cercle = (Circle) apImgBarrePersonnalisee.lookup("#" + idZone);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourCercle(numeroZone, cercle));
                        break;
                }

            }
        }

    }

    private static void afficheBarrePersonnalisee(int largeur, int hauteur, boolean bMasqueZones) {
        apImgBarrePersonnalisee.getChildren().clear();
        apZoneBarrePersonnalisee.getChildren().clear();
        ImageView ivBarrePersonnalisee = new ImageView(imgBarrePersonnalisee);
        apImgBarrePersonnalisee.getChildren().add(ivBarrePersonnalisee);
        apImgBarrePersonnalisee.setPrefWidth(imgBarrePersonnalisee.getWidth());
        apImgBarrePersonnalisee.setPrefHeight(imgBarrePersonnalisee.getHeight());
        apImgBarrePersonnalisee.setCursor(Cursor.CROSSHAIR);
        apImgBarrePersonnalisee.setLayoutX((largeur - 300 - apImgBarrePersonnalisee.getPrefWidth()) / 2.d);
        apImgBarrePersonnalisee.setLayoutY((hauteur - apImgBarrePersonnalisee.getPrefHeight()) / 2.d);
        //System.out.println("nombre Zones :" + nombreZones);
        if ((iNombreZones > 0) && !bMasqueZones) {
            for (int i = 0; i < iNombreZones; i++) {
                ZoneTelecommande zone = zones[i];
                //System.out.println(i + " => " + zone.getStrTypeZone() + " " + zone.getStrCoordonneesZone());
                String[] strPoints = zone.getStrCoordonneesZone().split(",");
                double[] points = new double[strPoints.length];
                for (int j = 0; j < strPoints.length; j++) {
                    points[j] = Double.parseDouble(strPoints[j]);
                }
                final String idZone = zone.getStrTypeZone() + "-" + i;

                switch (zone.getStrTypeZone()) {
                    case "circle":
                        Circle cercle = new Circle(points[0], points[1], points[2]);
                        cercle.setFill(Color.rgb(255, 255, 0, 0.5));
                        cercle.setStroke(Color.FORESTGREEN);
                        cercle.setCursor(Cursor.DEFAULT);
                        //System.out.println("Cercle : " + points[0] + "," + points[1] + " r= " + points[2]);
                        apImgBarrePersonnalisee.getChildren().add(cercle);
                        cercle.setId(idZone);
                        cercle.setOnMouseClicked((MouseEvent t) -> {
                            choixZone(largeur, hauteur, bMasqueZones, idZone, t);
                            t.consume();
                        });
                        break;
                    case "rect":
                        double largRect = points[2] - points[0];
                        double hautRect = points[3] - points[1];
                        //System.out.println("rectangle : " + points[0] + "," + points[1] + " jxh= " + largRect + " x " + hautRect);
                        Rectangle rect = new Rectangle(points[0], points[1], largRect, hautRect);
                        rect.setFill(Color.rgb(255, 255, 0, 0.5));
                        rect.setStroke(Color.FORESTGREEN);
                        rect.setCursor(Cursor.DEFAULT);
                        rect.setId("rect-" + i);
                        apImgBarrePersonnalisee.getChildren().add(rect);
                        rect.setId(idZone);
                        rect.setOnMouseClicked((MouseEvent t) -> {
                            choixZone(largeur, hauteur, bMasqueZones, idZone, t);
                            t.consume();
                        });
                        break;
                    case "poly":
                        Polygon poly = new Polygon(points);
                        poly.setFill(Color.rgb(255, 255, 0, 0.5));
                        poly.setStroke(Color.FORESTGREEN);
                        poly.setStrokeWidth(3);
                        poly.setId("poly-" + i);
                        poly.setCursor(Cursor.DEFAULT);
                        poly.setStrokeLineCap(StrokeLineCap.ROUND);
                        apImgBarrePersonnalisee.getChildren().add(poly);
                        final String idPoly = "poly-" + i;
                        poly.setId(idPoly);
                        poly.setOnMouseClicked((MouseEvent t) -> {
                            choixZone(largeur, hauteur, bMasqueZones, idZone, t);
                            t.consume();
                        });
                        break;
                }
            }
        }
    }

    private static void ajouterZone(int largeur, int hauteur, boolean bMasqueZones) {
        if (iNombreZones == -1) {
            iNombreZones = 0;
        }
        final ZoneTelecommande zone = new ZoneTelecommande();
        strTypeZone = "poly";
        zone.setStrTypeZone(strTypeZone);
        iNombrePointsZone = 0;
        bRecommenceZone = false;
        apZoneBarrePersonnalisee.getChildren().clear();
        Button btnAnnuler = new Button(rbLocalisation.getString("main.annuler"), new ImageView(new Image("file:" + strRepertAppli + "/images/annule.png")));
        Button btnValider = new Button(rbLocalisation.getString("main.valider"), new ImageView(new Image("file:" + strRepertAppli + "/images/valide.png")));
        btnValider.setLayoutX(180);
        btnValider.setLayoutY(170);
        btnAnnuler.setLayoutX(80);
        btnAnnuler.setLayoutY(170);
        ToggleGroup tgTypeZone = new ToggleGroup();
        Label lblTypeZone = new Label(rbLocalisation.getString("main.typeZone"));
        lblTypeZone.setLayoutX(20);
        lblTypeZone.setLayoutY(10);

        RadioButton rbCercleZone = new RadioButton(rbLocalisation.getString("main.cercle"));
        rbCercleZone.setLayoutX(20);
        rbCercleZone.setLayoutY(40);
        rbCercleZone.setUserData("circle");
        rbCercleZone.setToggleGroup(tgTypeZone);
        RadioButton rbRectZone = new RadioButton(rbLocalisation.getString("main.rectangle"));
        rbRectZone.setLayoutX(120);
        rbRectZone.setLayoutY(40);
        rbRectZone.setUserData("rect");
        rbRectZone.setToggleGroup(tgTypeZone);
        RadioButton rbPolyZone = new RadioButton(rbLocalisation.getString("main.polygone"));
        rbPolyZone.setLayoutX(220);
        rbPolyZone.setLayoutY(40);
        rbPolyZone.setUserData("poly");
        rbPolyZone.setToggleGroup(tgTypeZone);
        rbPolyZone.setSelected(true);
        ComboBox cbTouchesBarre = new ComboBox();
        cbTouchesBarre.getItems().clear();
        for (int i = 0; i < strTouchesBarre.length; i++) {
            cbTouchesBarre.getItems().add(i, strTouchesBarre[i]);
        }
        cbTouchesBarre.setLayoutX(50);
        cbTouchesBarre.setLayoutY(110);

        afficheBarrePersonnalisee(largeur, hauteur, bMasqueZones);
        AnchorPane apCreeZone = new AnchorPane();
        apCreeZone.setStyle("-fx-background-color : rgba(0,0,0,0.1)");
        apCreeZone.setPrefWidth(imgBarrePersonnalisee.getWidth());
        apCreeZone.setPrefHeight(imgBarrePersonnalisee.getHeight());
        apCreeZone.setCursor(Cursor.CROSSHAIR);
        apImgBarrePersonnalisee.getChildren().add(apCreeZone);
        apZoneBarrePersonnalisee.getChildren().addAll(
                lblTypeZone,
                rbCercleZone, rbRectZone, rbPolyZone,
                cbTouchesBarre,
                btnAnnuler, btnValider
        );
        apCreeZone.setOnMouseClicked((MouseEvent t) -> {
            rbCercleZone.setDisable(true);
            rbRectZone.setDisable(true);
            rbPolyZone.setDisable(true);
            iNombrePointsZone++;
            switch (strTypeZone) {
                case "rect":
                    if (iNombrePointsZone == 1) {
                        apCreeZone.getChildren().clear();
                        x1Zone = t.getX();
                        y1Zone = t.getY();
                        Circle cercle = new Circle(t.getX(), t.getY(), 4);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(cercle);
                    }
                    if (iNombrePointsZone == 2) {
                        apCreeZone.getChildren().clear();
                        Rectangle rect = new Rectangle(x1Zone, y1Zone, t.getX() - x1Zone, t.getY() - y1Zone);
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        rect.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(rect);
                        String chaine
                                = Math.round(x1Zone * 10) / 10 + "," + Math.round(y1Zone * 10) / 10 + ","
                                + Math.round(t.getX() * 10) / 10 + "," + Math.round(t.getY() * 10) / 10;
                        zone.setStrCoordonneesZone(chaine);
                        iNombrePointsZone = 0;
                    }
                    break;
                case "circle":
                    if (iNombrePointsZone == 1) {
                        apCreeZone.getChildren().clear();
                        x1Zone = t.getX();
                        y1Zone = t.getY();
                        Circle cercle = new Circle(t.getX(), t.getY(), 4);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(cercle);
                    }
                    if (iNombrePointsZone == 2) {
                        apCreeZone.getChildren().clear();
                        double rayon = Math.sqrt(Math.pow(x1Zone - t.getX(), 2.d) + Math.pow(y1Zone - t.getY(), 2.d));
                        Circle cercle = new Circle(x1Zone, y1Zone, rayon);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(cercle);
                        String chaine
                                = Math.round(x1Zone * 10) / 10 + "," + Math.round(y1Zone * 10) / 10 + ","
                                + Math.round(rayon * 10) / 10;
                        zone.setStrCoordonneesZone(chaine);
                        iNombrePointsZone = 0;
                    }
                    break;
                case "poly":
                    if (bRecommenceZone) {
                        bRecommenceZone = false;
                        iNombrePointsZone = 1;
                    }
                    if (iNombrePointsZone == 1) {
                        apCreeZone.getChildren().clear();
                        x1Zone = t.getX();
                        y1Zone = t.getY();
                        Circle cercle = new Circle(t.getX(), t.getY(), 4);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(cercle);
                        pointsPolyZone[(iNombrePointsZone - 1) * 2] = t.getX();
                        pointsPolyZone[(iNombrePointsZone - 1) * 2 + 1] = t.getY();
                    }
                    if (iNombrePointsZone == 2) {
                        apCreeZone.getChildren().clear();
                        Line ligne = new Line(x1Zone, y1Zone, t.getX(), t.getY());
                        ligne.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(ligne);
                        pointsPolyZone[(iNombrePointsZone - 1) * 2] = t.getX();
                        pointsPolyZone[(iNombrePointsZone - 1) * 2 + 1] = t.getY();
                    }
                    if (iNombrePointsZone > 2) {
                        pointsPolyZone[(iNombrePointsZone - 1) * 2] = t.getX();
                        pointsPolyZone[(iNombrePointsZone - 1) * 2 + 1] = t.getY();
                        apCreeZone.getChildren().clear();
                        Polygon poly = new Polygon();
                        for (int i = 0; i < iNombrePointsZone; i++) {
                            poly.getPoints().addAll(pointsPolyZone[i * 2], pointsPolyZone[i * 2 + 1]);
                        }
                        poly.setFill(Color.rgb(255, 0, 0, 0.5));
                        poly.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(poly);
                    }
                    if (t.getClickCount() == 2) {
                        String chaine = "";
                        for (int i = 0; i < iNombrePointsZone; i++) {
                            if (i != 0) {
                                chaine += ",";
                            }
                            chaine += Math.round(pointsPolyZone[i * 2] * 10) / 10 + "," + Math.round(pointsPolyZone[i * 2 + 1] * 10) / 10;
                        }
                        zone.setStrCoordonneesZone(chaine);
                        bRecommenceZone = true;
                    }
                    break;
            }
        });

        apCreeZone.setOnMouseMoved((MouseEvent t) -> {
            switch (strTypeZone) {
                case "rect":
                    if (iNombrePointsZone == 1) {
                        apCreeZone.getChildren().clear();
                        Rectangle rect = new Rectangle(x1Zone, y1Zone, t.getX() - x1Zone, t.getY() - y1Zone);
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        rect.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(rect);
                    }
                    break;
                case "circle":
                    if (iNombrePointsZone == 1) {
                        apCreeZone.getChildren().clear();
                        double rayon = Math.sqrt(Math.pow(x1Zone - t.getX(), 2.d) + Math.pow(y1Zone - t.getY(), 2.d));
                        Circle cercle = new Circle(x1Zone, y1Zone, rayon);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apCreeZone.getChildren().add(cercle);
                    }
                    break;
                case "poly":
                    if (!bRecommenceZone) {
                        if (iNombrePointsZone == 1) {
                            apCreeZone.getChildren().clear();
                            Line ligne = new Line(x1Zone, y1Zone, t.getX(), t.getY());
                            ligne.setStroke(Color.YELLOW);
                            apCreeZone.getChildren().add(ligne);
                        }
                        if (iNombrePointsZone > 1) {
                            apCreeZone.getChildren().clear();
                            Polygon poly = new Polygon();
                            for (int i = 0; i < iNombrePointsZone; i++) {
                                poly.getPoints().addAll(pointsPolyZone[i * 2], pointsPolyZone[i * 2 + 1]);
                            }
                            poly.getPoints().addAll(t.getX(), t.getY());
                            poly.setFill(Color.rgb(255, 0, 0, 0.5));
                            poly.setStroke(Color.YELLOW);
                            apCreeZone.getChildren().add(poly);
                        }
                    }
                    break;
            }
        });

        btnValider.setOnMouseClicked((MouseEvent t) -> {
            if (strTypeZone.equals("poly")) {
                String chaine = "";
                for (int i = 0; i < iNombrePointsZone; i++) {
                    if (i != 0) {
                        chaine += ",";
                    }
                    chaine += Math.round(pointsPolyZone[i * 2] * 10) / 10 + "," + Math.round(pointsPolyZone[i * 2 + 1] * 10) / 10;
                }
                zone.setStrCoordonneesZone(chaine);

            }
            zones[iNombreZones] = zone;
            iNombreZones++;
            afficheBarrePersonnalisee(largeur, hauteur, bMasqueZones);
            btnAjouteZone.setDisable(false);
        });

        btnAnnuler.setOnMouseClicked((MouseEvent t) -> {
            afficheBarrePersonnalisee(largeur, hauteur, bMasqueZones);
            btnAjouteZone.setDisable(false);
        });

        cbTouchesBarre.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                if (nouvelleValeur != null) {
                    String strId = strCodeBarre[cbTouchesBarre.getSelectionModel().getSelectedIndex()];
                    zone.setStrIdZone(strId);
                }
            }
        });

        tgTypeZone.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (tgTypeZone.getSelectedToggle() != null) {
                strTypeZone = tgTypeZone.getSelectedToggle().getUserData().toString();
                zone.setStrTypeZone(strTypeZone);
            }
        });

    }

    private static void sauverBarre(String fichShp) throws FileNotFoundException, IOException {
        OutputStreamWriter fw1 = null;
        try {
            String strZones = "";
            for (int i = 0; i < iNombreZones; i++) {
                ZoneTelecommande zone = zones[i];
                strZones += "[area=>id:" + zone.getStrIdZone() + ";";
                strZones += "shape:" + zone.getStrTypeZone() + ";";
                strZones += "coords:" + zone.getStrCoordonneesZone() + "]\n";
            }
            File fichIndexHTML = new File(fichShp);
            if (!fichIndexHTML.exists()) {
                fichIndexHTML.createNewFile();
            }
            fichIndexHTML.setWritable(true);
            fw1 = new OutputStreamWriter(new FileOutputStream(fichIndexHTML), "UTF-8");
            try (BufferedWriter bw1 = new BufferedWriter(fw1)) {
                bw1.write(strZones);

            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw1.close();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void creerEditerBarre(String nomFichierBarre) {
        apCreationBarre.getChildren().clear();
        apCreationBarre.setStyle("-fx-background-color : #ccc;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");

        AnchorPane apOutilsBarre = new AnchorPane();
        Button btnAnnulerBarre = new Button(rbLocalisation.getString("main.quitter"), new ImageView(new Image("file:" + strRepertAppli + "/images/annule.png")));
        final Button btnSauverBarre = new Button(rbLocalisation.getString("main.sauver"), new ImageView(new Image("file:" + strRepertAppli + "/images/sauveProjet.png", 24, 24, true, true, true)));
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        btnAjouteZone = new Button(rbLocalisation.getString("main.ajouteZone"), new ImageView(new Image("file:" + strRepertAppli + "/images/btn+.png", 24, 24, true, true, true)));
        apImgBarrePersonnalisee = new AnchorPane();
        apImgBarrePersonnalisee.getChildren().clear();
        apZoneBarrePersonnalisee = new AnchorPane();
        apZoneBarrePersonnalisee.getChildren().clear();
        apZoneBarrePersonnalisee.setLayoutX(0);
        apZoneBarrePersonnalisee.setLayoutY(150);
        apZoneBarrePersonnalisee.setPrefWidth(300);
        apZoneBarrePersonnalisee.setPrefHeight(200);

        int largeurEcran = (int) tailleEcran.getWidth();
        int hauteurEcran = (int) tailleEcran.getHeight() - 100;
        final int largeur = 1200;
        final int hauteur = 600;
        mbarPrincipal.setDisable(true);
        hbBarreBouton.setDisable(true);
        tpEnvironnement.setDisable(true);
        apCreationBarre.setPrefWidth(largeur);
        apCreationBarre.setMinWidth(largeur);
        apCreationBarre.setMaxWidth(largeur);
        apCreationBarre.setPrefHeight(hauteur);
        apCreationBarre.setMinHeight(hauteur);
        apCreationBarre.setMaxHeight(hauteur);
        apCreationBarre.setLayoutX((largeurEcran - largeur) / 2);
        apCreationBarre.setLayoutY((hauteurEcran - hauteur) / 2);
        apCreationBarre.setVisible(true);
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("main.creeBarrePersonnalisee"));
        lblBarrePersonnalisee.setMinWidth(largeur - 10);
        lblBarrePersonnalisee.setAlignment(Pos.CENTER);
        lblBarrePersonnalisee.setStyle("-fx-background-color : #777;");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setLayoutX(5);
        lblBarrePersonnalisee.setLayoutY(10);
        lblBarrePersonnalisee.setFont(Font.font(14));
        apCreationBarre.getChildren().add(lblBarrePersonnalisee);
        //apCreationBarre.setStyle("-fx-background-color : -fx-background;-fx-border-size : 1px;-fx-border-color : -fx-outer-border");
        apOutilsBarre.setPrefWidth(300);
        apOutilsBarre.setMinWidth(300);
        apOutilsBarre.setMaxWidth(300);
        apOutilsBarre.setPrefHeight(hauteur - 50);
        apOutilsBarre.setMinHeight(hauteur - 50);
        apOutilsBarre.setMaxHeight(hauteur - 50);
        apOutilsBarre.setLayoutX(largeur - 302);
        apOutilsBarre.setLayoutY(50);

        apOutilsBarre.setStyle("-fx-background-color : -fx-background;-fx-border-width : 1px;-fx-border-color : transparent transparent transparent -fx-outer-border;");
        btnAnnulerBarre.setPrefWidth(120);
        btnAnnulerBarre.setLayoutX(30);
        btnAnnulerBarre.setLayoutY(hauteur - 90);
        btnSauverBarre.setPrefWidth(120);
        btnSauverBarre.setLayoutX(160);
        btnSauverBarre.setLayoutY(hauteur - 90);
        btnSauverBarre.setDisable(true);
        Label lblChargeImage = new Label(rbLocalisation.getString("main.chargeImage"));
        lblChargeImage.setLayoutX(20);
        lblChargeImage.setLayoutY(10);
        TextField tfChargeImage = new TextField("");
        tfChargeImage.setDisable(true);
        tfChargeImage.setPrefWidth(200);
        tfChargeImage.setLayoutX(50);
        tfChargeImage.setLayoutY(40);
        Button btnChargeImage = new Button("...");
        btnChargeImage.setLayoutX(260);
        btnChargeImage.setLayoutY(40);
        final CheckBox cbMasqueZones = new CheckBox(rbLocalisation.getString("main.masqueZones"));
        cbMasqueZones.setDisable(true);
        cbMasqueZones.setLayoutX(20);
        cbMasqueZones.setLayoutY(70);
        btnAjouteZone.setLayoutX(130);
        btnAjouteZone.setLayoutY(110);
        btnAjouteZone.setDisable(true);
        apOutilsBarre.getChildren().addAll(
                lblChargeImage,
                tfChargeImage, btnChargeImage,
                cbMasqueZones,
                btnAjouteZone,
                apZoneBarrePersonnalisee,
                btnAnnulerBarre, btnSauverBarre
        );

        apCreationBarre.getChildren().addAll(apImgBarrePersonnalisee, apOutilsBarre);
        if (!nomFichierBarre.equals("")) {
            lblChargeImage.setVisible(false);
            tfChargeImage.setVisible(false);
            btnChargeImage.setVisible(false);
            String nomFichier = nomFichierBarre;
            nomFichier = nomFichier.substring(0, nomFichier.length() - 4);
            strNomFichierShp = nomFichier + ".shp";
            String nomFichierPng = nomFichier + ".png";
            File fichPng = new File(nomFichierPng);
            if (fichPng.exists()) {
                try {
                    btnAjouteZone.setDisable(false);
                    imgBarrePersonnalisee = new Image("file:" + fichPng);
                    iNombreZones = gestionnaireInterface.lisFichierShp(strNomFichierShp, zones);
                    btnSauverBarre.setDisable(false);
                    if (iNombreZones > 0) {
                        cbMasqueZones.setDisable(false);
                    }
                    afficheBarrePersonnalisee(largeur, hauteur, false);

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        btnAnnulerBarre.setOnMouseClicked((MouseEvent t) -> {
            mbarPrincipal.setDisable(false);
            hbBarreBouton.setDisable(false);
            tpEnvironnement.setDisable(false);
            apCreationBarre.setVisible(false);
        });

        btnAjouteZone.setOnMouseClicked((MouseEvent t) -> {
            btnAjouteZone.setDisable(true);
            ajouterZone(largeur, hauteur, false);
        });

//        apCreationBarre.setOnMouseClicked((MouseEvent t) -> {
//            afficheBarrePersonnalisee(largeur, hauteur, cbMasqueZones.isSelected());
//            t.consume();
//        });
        btnChargeImage.setOnMouseClicked((MouseEvent t) -> {
            strRepertBarrePersonnalisee = strRepertAppli + "/theme/telecommandes";
            File repert;
            repert = new File(strRepertBarrePersonnalisee);
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter efShpFilter = new FileChooser.ExtensionFilter("Fichiers barre personnalisée (SHP)", "*.shp", "*.png");

            fileChooser.setInitialDirectory(repert);
            fileChooser.getExtensionFilters().addAll(efShpFilter);

            File fichierImage = fileChooser.showOpenDialog(null);
            if (fichierImage != null) {
                String nomFichier = fichierImage.getAbsolutePath();
                nomFichier = nomFichier.substring(0, nomFichier.length() - 4);
                strNomFichierShp = nomFichier + ".shp";
                String nomFichierPng = nomFichier + ".png";
                File fichPng = new File(nomFichierPng);
                if (fichPng.exists()) {
                    try {
                        btnAjouteZone.setDisable(false);
                        imgBarrePersonnalisee = new Image("file:" + fichPng);
                        iNombreZones = gestionnaireInterface.lisFichierShp(strNomFichierShp, zones);
                        btnSauverBarre.setDisable(false);
                        if (iNombreZones > 0) {
                            cbMasqueZones.setDisable(false);
                        }
                        afficheBarrePersonnalisee(largeur, hauteur, false);

                    } catch (IOException ex) {
                        Logger.getLogger(EditeurPanovisu.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnSauverBarre.setOnMouseClicked((MouseEvent t) -> {
            try {
                sauverBarre(strNomFichierShp);
                gestionnaireInterface.chargeBarrePersonnalisee(nomFichierBarre);

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        cbMasqueZones.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            afficheBarrePersonnalisee(largeur, hauteur, new_val);
        });

    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    private void creeEnvironnement(Stage primaryStage) throws Exception {
        creeEnvironnement(primaryStage, 800, 600);
    }

    /**
     *
     * @param primaryStage
     * @param racine
     * @param taille
     * @throws Exception
     */
    private void creeMenu(VBox racine, int taille) throws Exception {
        //Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("menuPrincipal.fxml"));
        VBox myPane = new VBox();
        myPane.setPrefHeight(80);
        myPane.setPrefWidth(3000);
        mbarPrincipal = new MenuBar();
        mbarPrincipal.setMinHeight(25);
        mbarPrincipal.setPrefHeight(29);
        mbarPrincipal.setPrefWidth(3000);
        /* 
         Menu projets
         */
        Menu mnuProjet = new Menu(rbLocalisation.getString("projets"));
        mbarPrincipal.getMenus().add(mnuProjet);
        mniNouveauProjet = new MenuItem(rbLocalisation.getString("nouveauProjet"));
        mniNouveauProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mnuProjet.getItems().add(mniNouveauProjet);
        mniChargeProjet = new MenuItem(rbLocalisation.getString("ouvrirProjet"));
        mniChargeProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mnuProjet.getItems().add(mniChargeProjet);
        mniSauveProjet = new MenuItem(rbLocalisation.getString("sauverProjet"));
        mniSauveProjet.setDisable(true);
        mniSauveProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        mnuProjet.getItems().add(mniSauveProjet);
        mniSauveSousProjet = new MenuItem(rbLocalisation.getString("sauverProjetSous"));
        mniSauveSousProjet.setDisable(true);
        mniSauveSousProjet.setAccelerator(KeyCombination.keyCombination("Shift+Ctrl+S"));
        mnuProjet.getItems().add(mniSauveSousProjet);
        mnuDerniersProjets = new Menu(rbLocalisation.getString("derniersProjets"));
//        derniersProjets.setDisable(true);
        mnuProjet.getItems().add(mnuDerniersProjets);
        fileHistoFichiers = new File(fileRepertConfig.getAbsolutePath() + File.separator + "derniersprojets.cfg");
        nombreHistoFichiers = 0;
        if (fileHistoFichiers.exists()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileHistoFichiers), "UTF-8"))) {
                while ((strTexteHisto = br.readLine()) != null) {
                    MenuItem menuDerniersFichiers = new MenuItem(strTexteHisto);
                    mnuDerniersProjets.getItems().add(menuDerniersFichiers);
                    strHistoFichiers[nombreHistoFichiers] = strTexteHisto;
                    nombreHistoFichiers++;
                    menuDerniersFichiers.setOnAction((ActionEvent e) -> {
                        MenuItem mnu = (MenuItem) e.getSource();
                        try {
                            try {
                                projetChargeNom(mnu.getText());

                            } catch (InterruptedException ex) {
                                Logger.getLogger(EditeurPanovisu.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(EditeurPanovisu.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }
            }
        }

        SeparatorMenuItem sepMenu1 = new SeparatorMenuItem();
        mnuProjet.getItems().add(sepMenu1);
        mniFermerProjet = new MenuItem(rbLocalisation.getString("quitterApplication"));
        mniFermerProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        mnuProjet.getItems().add(mniFermerProjet);
        /*
         Menu affichage
         */
        Menu mnuAffichage = new Menu(rbLocalisation.getString("affichage"));
        mbarPrincipal.getMenus().add(mnuAffichage);
        mniAffichageVisite = new MenuItem(rbLocalisation.getString("main.creationVisite"));
        mniAffichageVisite.setAccelerator(KeyCombination.keyCombination("Ctrl+1"));
        mnuAffichage.getItems().add(mniAffichageVisite);
        mniAffichageInterface = new MenuItem(rbLocalisation.getString("main.creationInterface"));
        mniAffichageInterface.setAccelerator(KeyCombination.keyCombination("Ctrl+2"));
        mnuAffichage.getItems().add(mniAffichageInterface);
        mniAffichagePlan = new MenuItem(rbLocalisation.getString("main.tabPlan"));
        mniAffichagePlan.setAccelerator(KeyCombination.keyCombination("Ctrl+3"));
        mniAffichagePlan.setDisable(true);
        mnuAffichage.getItems().add(mniAffichagePlan);
        SeparatorMenuItem sep3 = new SeparatorMenuItem();
        mnuAffichage.getItems().add(sep3);
        mniConfigTransformation = new MenuItem(rbLocalisation.getString("affichageConfiguration"));
        mnuAffichage.getItems().add(mniConfigTransformation);

        /*
         Menu panoramiques
         */
        mnuPanoramique = new Menu(rbLocalisation.getString("panoramiques"));
        mnuPanoramique.setDisable(true);
        mbarPrincipal.getMenus().add(mnuPanoramique);
        mniAjouterPano = new MenuItem(rbLocalisation.getString("ajouterPanoramiques"));
        mniAjouterPano.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        mnuPanoramique.getItems().add(mniAjouterPano);
        mniAjouterPlan = new MenuItem(rbLocalisation.getString("ajouterPlan"));
        mniAjouterPlan.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        mnuPanoramique.getItems().add(mniAjouterPlan);
        mniAjouterPlan.setDisable(true);
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        mnuPanoramique.getItems().add(sep2);
        mniVisiteGenere = new MenuItem(rbLocalisation.getString("genererVisite"));
        mniVisiteGenere.setDisable(true);
        mniVisiteGenere.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        mnuPanoramique.getItems().add(mniVisiteGenere);
        /*
         Menu Modèles 
         */
        mnuModeles = new Menu(rbLocalisation.getString("menuModele"));
        mbarPrincipal.getMenus().add(mnuModeles);

        mniChargerModele = new MenuItem(rbLocalisation.getString("modeleCharger"));
        mnuModeles.getItems().add(mniChargerModele);

        mniSauverModele = new MenuItem(rbLocalisation.getString("modeleSauver"));
        mnuModeles.getItems().add(mniSauverModele);

        /*
         Menu transformations 
         */
        mnuTransformation = new Menu(rbLocalisation.getString("outils"));
        mbarPrincipal.getMenus().add(mnuTransformation);
        mniEqui2CubeTransformation = new MenuItem(rbLocalisation.getString("outilsEqui2Cube"));
        mnuTransformation.getItems().add(mniEqui2CubeTransformation);
        mniCube2EquiTransformation = new MenuItem(rbLocalisation.getString("outilsCube2Equi"));
        mnuTransformation.getItems().add(mniCube2EquiTransformation);
        SeparatorMenuItem sep6 = new SeparatorMenuItem();
        mnuTransformation.getItems().add(sep6);
        mniOutilsBarre = new MenuItem(rbLocalisation.getString("outilsBarre"));
        mniOutilsBarre.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
        mnuTransformation.getItems().add(mniOutilsBarre);

        /*
         Menu Aide
         */
        Menu menuAide = new Menu(rbLocalisation.getString("aide"));
        mbarPrincipal.getMenus().add(menuAide);
        mniAide = new MenuItem(rbLocalisation.getString("aideAide"));
        mniAide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuAide.getItems().add(mniAide);
        SeparatorMenuItem sep4 = new SeparatorMenuItem();
        menuAide.getItems().add(sep4);
        mniAPropos = new MenuItem(rbLocalisation.getString("aideAPropos"));
        menuAide.getItems().add(mniAPropos);
        /*
         barre de boutons 
         */
        hbBarreBouton = new HBox();
        hbBarreBouton.getStyleClass().add("menuBarreOutils1");

        hbBarreBouton.setPrefHeight(50);
        hbBarreBouton.setMinHeight(50);
        hbBarreBouton.setPrefWidth(3000);
        /*
         Bouton nouveau Projet
         */
        ScrollPane spBtnNouvprojet = new ScrollPane();
        spBtnNouvprojet.getStyleClass().add("menuBarreOutils");
        spBtnNouvprojet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnNouvprojet.setPrefHeight(35);
        spBtnNouvprojet.setMaxHeight(35);
        spBtnNouvprojet.setPadding(new Insets(2));
        spBtnNouvprojet.setPrefWidth(35);

        HBox.setMargin(spBtnNouvprojet, new Insets(5, 15, 0, 15));
        ivNouveauProjet = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/nouveauProjet.png"));
        spBtnNouvprojet.setContent(ivNouveauProjet);
        Tooltip t0 = new Tooltip(rbLocalisation.getString("nouveauProjet"));
        t0.setStyle(strTooltipStyle);
        spBtnNouvprojet.setTooltip(t0);
        hbBarreBouton.getChildren().add(spBtnNouvprojet);
        /*
         Bouton ouvrir Projet
         */
        ScrollPane spBtnOuvrirProjet = new ScrollPane();
        spBtnOuvrirProjet.getStyleClass().add("menuBarreOutils");
        spBtnOuvrirProjet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnOuvrirProjet.setPrefHeight(35);
        spBtnOuvrirProjet.setMaxHeight(35);
        spBtnOuvrirProjet.setPadding(new Insets(2));
        spBtnOuvrirProjet.setPrefWidth(35);

        HBox.setMargin(spBtnOuvrirProjet, new Insets(5, 15, 0, 0));
        ivChargeProjet = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ouvrirProjet.png"));
        spBtnOuvrirProjet.setContent(ivChargeProjet);
        Tooltip t1 = new Tooltip(rbLocalisation.getString("ouvrirProjet"));
        t1.setStyle(strTooltipStyle);
        spBtnOuvrirProjet.setTooltip(t1);
        hbBarreBouton.getChildren().add(spBtnOuvrirProjet);

        /*
         Bouton sauve Projet
         */
        ScrollPane spBtnSauveProjet = new ScrollPane();
        spBtnSauveProjet.getStyleClass().add("menuBarreOutils");
        spBtnSauveProjet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnSauveProjet.setPrefHeight(35);
        spBtnSauveProjet.setMaxHeight(35);
        spBtnSauveProjet.setPadding(new Insets(2));
        spBtnSauveProjet.setPrefWidth(35);

        HBox.setMargin(spBtnSauveProjet, new Insets(5, 15, 0, 0));
        ivSauveProjet = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/sauveProjet.png"));
        spBtnSauveProjet.setContent(ivSauveProjet);
        Tooltip t2 = new Tooltip(rbLocalisation.getString("sauverProjet"));
        t2.setStyle(strTooltipStyle);
        spBtnSauveProjet.setTooltip(t2);
        hbBarreBouton.getChildren().add(spBtnSauveProjet);
        Separator sepImages = new Separator(Orientation.VERTICAL);
        sepImages.prefHeight(200);
        hbBarreBouton.getChildren().add(sepImages);
        ivSauveProjet.setDisable(true);
        ivSauveProjet.setOpacity(0.3);
        /*
         Bouton Ajoute Panoramique
         */
        ScrollPane spBtnAjoutePano = new ScrollPane();
        spBtnAjoutePano.getStyleClass().add("menuBarreOutils");
        spBtnAjoutePano.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnAjoutePano.setPrefHeight(35);
        spBtnAjoutePano.setMaxHeight(35);
        spBtnAjoutePano.setPadding(new Insets(2));
        spBtnAjoutePano.setPrefWidth(35);

        HBox.setMargin(spBtnAjoutePano, new Insets(5, 15, 0, 15));
        ivAjouterPano = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ajoutePanoramique.png"));
        spBtnAjoutePano.setContent(ivAjouterPano);
        Tooltip t3 = new Tooltip(rbLocalisation.getString("ajouterPanoramiques"));
        t3.setStyle(strTooltipStyle);
        spBtnAjoutePano.setTooltip(t3);
        hbBarreBouton.getChildren().add(spBtnAjoutePano);
        ivAjouterPano.setDisable(true);
        ivAjouterPano.setOpacity(0.3);

        /*
         Bouton Ajoute Panoramique
         */
        ScrollPane spBtnAjoutePlan = new ScrollPane();
        spBtnAjoutePlan.getStyleClass().add("menuBarreOutils");
        spBtnAjoutePlan.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnAjoutePlan.setPrefHeight(35);
        spBtnAjoutePlan.setMaxHeight(35);
        spBtnAjoutePlan.setPadding(new Insets(2));
        spBtnAjoutePlan.setPrefWidth(35);

        HBox.setMargin(spBtnAjoutePlan, new Insets(5, 15, 0, 15));
        ivAjouterPlan = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ajoutePlan.png"));
        spBtnAjoutePlan.setContent(ivAjouterPlan);
        Tooltip t31 = new Tooltip(rbLocalisation.getString("ajouterPlan"));
        t31.setStyle(strTooltipStyle);
        spBtnAjoutePlan.setTooltip(t31);
        hbBarreBouton.getChildren().add(spBtnAjoutePlan);
        ivAjouterPlan.setDisable(true);
        ivAjouterPlan.setOpacity(0.3);

        /*
         Bouton Génère
         */
        ScrollPane spBtnGenereVisite = new ScrollPane();
        spBtnGenereVisite.getStyleClass().add("menuBarreOutils");
        spBtnGenereVisite.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnGenereVisite.setPrefHeight(35);
        spBtnGenereVisite.setMaxHeight(35);
        spBtnGenereVisite.setPadding(new Insets(2));
        spBtnGenereVisite.setPrefWidth(70);

        HBox.setMargin(spBtnGenereVisite, new Insets(5, 15, 0, 0));
        ivVisiteGenere = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/genereVisite.png"));
        spBtnGenereVisite.setContent(ivVisiteGenere);
        Tooltip t4 = new Tooltip(rbLocalisation.getString("genererVisite"));
        t4.setStyle(strTooltipStyle);
        spBtnGenereVisite.setTooltip(t4);
        hbBarreBouton.getChildren().add(spBtnGenereVisite);
        ivVisiteGenere.setDisable(true);
        ivVisiteGenere.setOpacity(0.3);
        Separator sepImages1 = new Separator(Orientation.VERTICAL);
        sepImages1.prefHeight(200);
        hbBarreBouton.getChildren().add(sepImages1);
        /*
         Bouton equi -> faces de  Cube
         */
        ScrollPane spBtnEqui2Cube = new ScrollPane();
        spBtnEqui2Cube.getStyleClass().add("menuBarreOutils");
        spBtnEqui2Cube.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnEqui2Cube.setPrefHeight(35);
        spBtnEqui2Cube.setMaxHeight(35);
        spBtnEqui2Cube.setPadding(new Insets(2));
        spBtnEqui2Cube.setPrefWidth(109);

        HBox.setMargin(spBtnEqui2Cube, new Insets(5, 15, 0, 250));
        ivEqui2Cube = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/equi2cube.png"));
        spBtnEqui2Cube.setContent(ivEqui2Cube);
        Tooltip t6 = new Tooltip(rbLocalisation.getString("outilsEqui2Cube"));
        t6.setStyle(strTooltipStyle);
        spBtnEqui2Cube.setTooltip(t6);
        hbBarreBouton.getChildren().add(spBtnEqui2Cube);

        /*
         Bouton faces de cube -> equi
         */
        ScrollPane spBtnCube2Equi = new ScrollPane();
        spBtnCube2Equi.getStyleClass().add("menuBarreOutils");
        spBtnCube2Equi.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spBtnCube2Equi.setPrefHeight(35);
        spBtnCube2Equi.setMaxHeight(35);
        spBtnCube2Equi.setPadding(new Insets(2));
        spBtnCube2Equi.setPrefWidth(109);

        HBox.setMargin(spBtnCube2Equi, new Insets(5, 25, 0, 0));
        ivCube2Equi = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/cube2equi.png"));
        spBtnCube2Equi.setContent(ivCube2Equi);
        Tooltip t5 = new Tooltip(rbLocalisation.getString("outilsCube2Equi"));
        t5.setStyle(strTooltipStyle);
        spBtnCube2Equi.setTooltip(t5);
        hbBarreBouton.getChildren().add(spBtnCube2Equi);

        myPane.getChildren().addAll(mbarPrincipal, hbBarreBouton);
        racine.getChildren().add(myPane);
        mniNouveauProjet.setOnAction((ActionEvent e) -> {
            projetsNouveau();
        });
        mniChargeProjet.setOnAction((ActionEvent e) -> {
            try {
                try {
                    projetCharge();

                } catch (InterruptedException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniSauveProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniSauveSousProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauveSous();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniVisiteGenere.setOnAction((ActionEvent e) -> {
            try {
                genereVisite();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniFermerProjet.setOnAction((ActionEvent e) -> {
            try {
                projetsFermer();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniAjouterPano.setOnAction((ActionEvent e) -> {
            try {
                panoramiquesAjouter();

            } catch (InterruptedException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniAjouterPlan.setOnAction((ActionEvent e) -> {
            planAjouter();
        });
        mniAPropos.setOnAction((ActionEvent e) -> {
            aideapropos();
        });
        mniAide.setOnAction((ActionEvent e) -> {
            AideDialogController.affiche();
        });

        mniChargerModele.setOnAction((ActionEvent e) -> {
            try {
                modeleCharger();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        mniSauverModele.setOnAction((ActionEvent e) -> {
            try {
                modeleSauver();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        mniCube2EquiTransformation.setOnAction((ActionEvent e) -> {
            transformationCube2Equi();
        });

        mniEqui2CubeTransformation.setOnAction((ActionEvent e) -> {
            transformationEqui2Cube();
        });

        mniOutilsBarre.setOnAction((ActionEvent e) -> {
            creerEditerBarre("");
        });

        mniAffichageVisite.setOnAction((ActionEvent e) -> {
            tpEnvironnement.getSelectionModel().select(0);
        });
        mniAffichageInterface.setOnAction((ActionEvent e) -> {
            tpEnvironnement.getSelectionModel().select(1);
        });
        mniAffichagePlan.setOnAction((ActionEvent e) -> {
            if (!tabPlan.isDisabled()) {
                tpEnvironnement.getSelectionModel().select(2);
            }
        });

        mniConfigTransformation.setOnAction((ActionEvent e) -> {
            try {
                ConfigDialogController cfg = new ConfigDialogController();
                cfg.afficheFenetre();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        spBtnNouvprojet.setOnMouseClicked((MouseEvent t) -> {
            projetsNouveau();
        });
        spBtnOuvrirProjet.setOnMouseClicked((MouseEvent t) -> {
            try {
                try {
                    projetCharge();

                } catch (InterruptedException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnSauveProjet.setOnMouseClicked((MouseEvent t) -> {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnAjoutePano.setOnMouseClicked((MouseEvent t) -> {
            try {
                panoramiquesAjouter();

            } catch (InterruptedException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnAjoutePlan.setOnMouseClicked((MouseEvent t) -> {
            planAjouter();
        });
        spBtnGenereVisite.setOnMouseClicked((MouseEvent t) -> {
            try {
                genereVisite();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnEqui2Cube.setOnMouseClicked((MouseEvent t) -> {
            transformationEqui2Cube();
        });
        spBtnCube2Equi.setOnMouseClicked((MouseEvent t) -> {
            transformationCube2Equi();
        });

    }

    private void retirePanoCourant() {
        Action reponse = Dialogs.create()
                .owner(null)
                .title(rbLocalisation.getString("main.supprimerPano"))
                .message(rbLocalisation.getString("main.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (reponse == Dialog.Actions.YES) {

            int panCourant = cbListeChoixPanoramique.getSelectionModel().getSelectedIndex();
            for (int i = panCourant; i < iNombrePanoramiques - 1; i++) {
                panoramiquesProjet[i] = panoramiquesProjet[i + 1];
            }
            int paneEntree = -1;
            iNombrePanoramiques--;
            if (cbListeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex() != panCourant) {
                paneEntree = cbListeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex();
            }
            if (paneEntree > panCourant) {
                paneEntree--;
            }
            cbListeChoixPanoramique.getItems().clear();
            cbListeChoixPanoramiqueEntree.getItems().clear();
            for (int i = 0; i < iNombrePanoramiques; i++) {
                String nomPano = panoramiquesProjet[i].getNomFichier();
                nomPano = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.length());
                cbListeChoixPanoramique.getItems().add(nomPano);
                cbListeChoixPanoramiqueEntree.getItems().add(nomPano);
            }
            if (paneEntree != -1) {
                cbListeChoixPanoramiqueEntree.setValue(cbListeChoixPanoramique.getItems().get(paneEntree));
            }
            cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(0));
            affichePanoChoisit(0);
            //System.out.println("pano courant : " + panCourant);
        }
    }

    /**
     *
     * @param primaryStage
     * @param width
     * @param height
     * @throws Exception
     */
    private void creeEnvironnement(Stage primaryStage, int width, int height) throws Exception {
        popUp = new PopUpDialogController();
        primaryStage.setMaximized(true);
        double largeurOutils = 380;

        iHauteurInterface = height;
        iLargeurInterface = width;
        /**
         * Création des éléments constitutifs de l'écran
         */
        VBox root = new VBox();
        creeMenu(root, width);
        tpEnvironnement = new TabPane();
//        tabPaneEnvironnement.setTranslateZ(5);
        tpEnvironnement.setMinHeight(height - 60);
        tpEnvironnement.setMaxHeight(height - 60);
        Pane barreStatus = new Pane();
        barreStatus.setPrefSize(width + 20, 30);
        barreStatus.setTranslateY(25);
        barreStatus.setStyle("-fx-background-color:#c00;-fx-border-color:#aaa");
        tabVisite = new Tab();
        Pane visualiseur;
        Pane panneauPlan;
        tabInterface = new Tab();
        tabPlan = new Tab();
        gestionnaireInterface.creeInterface(width, height - 60);
        visualiseur = gestionnaireInterface.tabInterface;
        gestionnairePlan.creeInterface(width, height - 60);
        panneauPlan = gestionnairePlan.paneInterface;
        tabInterface.setContent(visualiseur);
        tabPlan.setContent(panneauPlan);

        HBox hbEnvironnement = new HBox();
        TextField txtTitrePano;
        TextField tfTitreVisite;
        RadioButton radSphere;
        RadioButton radCube;
        CheckBox chkAfficheTitre;
        CheckBox chkAfficheInfo;

        tpEnvironnement.getTabs().addAll(tabVisite, tabInterface, tabPlan);
        //tabPaneEnvironnement.setTranslateY(80);
        tpEnvironnement.setSide(Side.TOP);
        tpEnvironnement.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            gestionnaireInterface.rafraichit();
        });

        tabVisite.setText(rbLocalisation.getString("main.creationVisite"));
        tabVisite.setClosable(false);
        tabInterface.setText(rbLocalisation.getString("main.creationInterface"));
        tabInterface.setClosable(false);
        tabPlan.setText(rbLocalisation.getString("main.tabPlan"));
        tabPlan.setClosable(false);
        tabPlan.setDisable(true);
        tabVisite.setContent(hbEnvironnement);
        double largeur;
        String labelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-padding : 5px;  -fx-border : 1px solid #777;-fx-width : 100px;-fx-margin : 5px; ";

        scnPrincipale = new Scene(root, width, height, Color.rgb(221, 221, 221));
//        if (systemeExploitation.indexOf("inux") != -1) {
//            root.setStyle("-fx-font-size : 7pt;-fx-font-family: sans-serif;");
//        } else {
        root.setStyle("-fx-font-size : 9pt;-fx-font-family: Arial;");
//        }
        spPanneauOutils = new ScrollPane();
        spPanneauOutils.setId("panOutils");
//        panneauOutils.setStyle("-fx-background-color : #ccc;");
        vbOutils = new VBox();
        vbChoixPanoramique = new VBox();
        vbChoixPanoramique.setTranslateX(10);
        vbChoixPanoramique.setId("choixPanoramique");
        Label lblTitreVisite = new Label(rbLocalisation.getString("main.titreVisite"));
        lblTitreVisite.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblTitreVisite.setPadding(new Insets(15, 5, 5, 0));
        lblTitreVisite.setMinWidth(largeurOutils - 20);
        lblTitreVisite.setAlignment(Pos.CENTER);

        tfTitreVisite = new TextField();
        tfTitreVisite.setId("titreVisite");
        tfTitreVisite.setPrefSize(200, 25);
        tfTitreVisite.setMaxSize(340, 25);

        Separator sepTitre = new Separator(Orientation.HORIZONTAL);
        sepTitre.setMinHeight(10);

        Label lblChoixPanoramiqueEntree = new Label(rbLocalisation.getString("main.panoEntree"));
        lblChoixPanoramiqueEntree.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblChoixPanoramiqueEntree.setPadding(new Insets(15, 5, 5, 0));
        lblChoixPanoramiqueEntree.setMinWidth(largeurOutils - 20);
        lblChoixPanoramiqueEntree.setAlignment(Pos.CENTER);

        lblChoixPanoramique = new Label(rbLocalisation.getString("main.panoAffiche"));
        lblChoixPanoramique.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblChoixPanoramique.setPadding(new Insets(10, 5, 5, 0));
        lblChoixPanoramique.setMinWidth(largeurOutils - 20);
        lblChoixPanoramique.setAlignment(Pos.CENTER);

        Separator sepPano = new Separator(Orientation.HORIZONTAL);
        sepPano.setMinHeight(10);
        cbListeChoixPanoramique.setVisibleRowCount(10);
        cbListeChoixPanoramique.setTranslateX(60);
        Pane fond = new Pane();
        fond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
        fond.setTranslateX(340);
        fond.setTranslateY(-80);
        Tooltip t = new Tooltip(rbLocalisation.getString("main.supprimePano"));
        t.setStyle(strTooltipStyle);
        Tooltip.install(fond, t);
        fond.getChildren().add(ivSupprPanoramique);
        fond.setOnMouseClicked(
                (MouseEvent me) -> {
                    retirePanoCourant();
                }
        );

        cbListeChoixPanoramiqueEntree.setTranslateX(60);
        Separator sepInfo = new Separator(Orientation.HORIZONTAL);
        Label lblTitrePano = new Label(rbLocalisation.getString("main.titrePano"));
        lblTitrePano.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblTitrePano.setPadding(new Insets(5, 5, 5, 0));
        lblTitrePano.setMinWidth(largeurOutils - 20);
        lblTitrePano.setAlignment(Pos.CENTER);
        txtTitrePano = new TextField();
        txtTitrePano.setId("txttitrepano");
        txtTitrePano.setPrefSize(200, 25);
        txtTitrePano.setMaxSize(340, 25);
        txtTitrePano.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            clickBtnValidePano();
        });

        vbChoixPanoramique.getChildren().addAll(
                lblTitreVisite, tfTitreVisite,
                lblChoixPanoramiqueEntree, cbListeChoixPanoramiqueEntree, sepPano,
                lblChoixPanoramique, cbListeChoixPanoramique, fond,
                lblTitrePano, txtTitrePano, sepInfo
        );
        vbChoixPanoramique.setSpacing(10);
        /*
         à modifier pour afficher le panneau des derniers fichiers;        
         */
        //outils.getChildren().addAll(lastFiles, paneChoixPanoramique);

        vbOutils.getChildren().addAll(vbChoixPanoramique);

        vbChoixPanoramique.setVisible(false);
        /*
         Création du panneau d'info du panoramique
         */

        spVuePanoramique = new ScrollPane();

        hbCoordonnees = new HBox();
        panePanoramique = new Pane();
        apPanneauPrincipal = new AnchorPane();
        lblLong = new Label("");
        lblLat = new Label("");
        ivImagePanoramique = new ImageView();

        primaryStage.setScene(scnPrincipale);
        //scene.getStylesheets().add("file:css/test.css");
        /**
         *
         */
        spVuePanoramique.setPrefSize(width - largeurOutils - 20, height - 130);
        spVuePanoramique.setMaxSize(width - largeurOutils - 20, height - 130);
        spVuePanoramique.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spVuePanoramique.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spVuePanoramique.setTranslateY(5);

        //vuePanoramique.setStyle("-fx-background-color : #c00;");
        /**
         *
         */
        spPanneauOutils.setContent(vbOutils);
        spPanneauOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spPanneauOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spPanneauOutils.setPrefSize(largeurOutils, height - 240);
        spPanneauOutils.setMaxWidth(largeurOutils);
        spPanneauOutils.setMaxHeight(height - 240);
        spPanneauOutils.setTranslateY(15);
        spPanneauOutils.setTranslateX(20);
//        panneauOutils.setStyle("-fx-background-color : #ccc;");
        /**
         *
         */
        panePanoramique.setCursor(Cursor.CROSSHAIR);
        vbOutils.setPrefWidth(largeurOutils - 20);
//        outils.setStyle("-fx-background-color : #ccc;");
        vbOutils.minHeight(height - 130);
        vbOutils.setLayoutX(10);
//        lblLong.setStyle(labelStyle);
//        lblLat.setStyle(labelStyle);
        lblLong.setPrefSize(100, 15);
        lblLat.setPrefSize(100, 15);
        lblLat.setTranslateX(50);
//        panneau2.setStyle("-fx-background-color : #ddd;");
        apPanneauPrincipal.setPrefSize(width - largeurOutils - 20, height - 140);

        ivImagePanoramique.setCache(true);
        largeur = largeurMax - 60;
        ivImagePanoramique.setFitWidth(largeur);
        ivImagePanoramique.setFitHeight(largeur / 2.0d);
        ivImagePanoramique.setLayoutX((largeurMax - largeur) / 2.d);
        panePanoramique.getChildren().add(ivImagePanoramique);
        panePanoramique.setPrefSize(ivImagePanoramique.getFitWidth(), ivImagePanoramique.getFitHeight());
        panePanoramique.setMaxSize(ivImagePanoramique.getFitWidth(), ivImagePanoramique.getFitHeight());

        panePanoramique.setLayoutY(20);
        lblLong.setTranslateX(50);
        lblLat.setTranslateX(80);
        hbCoordonnees.getChildren().setAll(lblLong, lblLat);
        spVuePanoramique.setContent(apPanneauPrincipal);
        hbEnvironnement.getChildren().setAll(spVuePanoramique, spPanneauOutils);
        apEnvironnement = new AnchorPane();
        apAttends = new AnchorPane();
        apAttends.setPrefHeight(250);
        apAttends.setPrefWidth(600);
        apAttends.setMaxWidth(600);
        apAttends.setStyle("-fx-background-color : #ccc;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");
        apAttends.setLayoutX((width - apAttends.getPrefWidth()) / 2.d);
        apAttends.setLayoutY((height - apAttends.getPrefHeight()) / 2.d - 55);
        pbarAvanceChargement = new ProgressBar();
        pbarAvanceChargement.setPrefSize(400, 30);
        pbarAvanceChargement.setLayoutX((apAttends.getPrefWidth() - pbarAvanceChargement.getPrefWidth()) / 2);
        pbarAvanceChargement.setLayoutY(70);
        Label lblAttends = new Label(rbLocalisation.getString("main.attendsChargement"));
        lblAttends.setMinWidth(600);
        lblAttends.setAlignment(Pos.CENTER);
        lblAttends.setStyle("-fx-background-color : #777;");
        lblAttends.setTextFill(Color.WHITE);
        lblAttends.setLayoutY(5);
        lblAttends.setFont(Font.font(14));
        lblCharge = new Label();
        lblCharge.setMinWidth(600);
        lblCharge.setLayoutY(150);
        lblCharge.setAlignment(Pos.CENTER);
        lblNiveaux = new Label();
        lblNiveaux.setMinWidth(600);
        lblNiveaux.setLayoutY(180);
        lblNiveaux.setAlignment(Pos.CENTER);
        apAttends.getChildren().addAll(lblAttends, pbarAvanceChargement, lblCharge, lblNiveaux);
        apAttends.setVisible(false);
        apEnvironnement.getChildren().addAll(tpEnvironnement, apAttends);
//        paneEnv.getChildren().addAll(tabPaneEnvironnement);
        root.getChildren().addAll(apEnvironnement);
        apPanneauPrincipal.getChildren().setAll(hbCoordonnees, panePanoramique);
        primaryStage.show();
        popUp.affichePopup();
        lblDragDrop = new Label(rbLocalisation.getString("main.dragDrop"));
        lblDragDrop.setMinHeight(spVuePanoramique.getPrefHeight());
        lblDragDrop.setMaxHeight(spVuePanoramique.getPrefHeight());
        lblDragDrop.setMinWidth(spVuePanoramique.getPrefWidth());
        lblDragDrop.setMaxWidth(spVuePanoramique.getPrefWidth());
        lblDragDrop.setAlignment(Pos.CENTER);
        lblDragDrop.setTextFill(Color.web("#c9c7c7"));
        lblDragDrop.setTextAlignment(TextAlignment.CENTER);
        lblDragDrop.setWrapText(true);
        lblDragDrop.setStyle("-fx-font-size:72px");
        lblDragDrop.setTranslateY(-100);
        apPanneauPrincipal.getChildren().addAll(lblDragDrop, afficheLegende());
        apCreationBarre = new AnchorPane();
        apCreationBarre.setVisible(false);
        apEnvironnement.getChildren().add(apCreationBarre);
    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        File rep = new File("");
        strCurrentDir = rep.getAbsolutePath();
        strRepertAppli = rep.getAbsolutePath();
        fileRepertConfig = new File(strRepertAppli + File.separator + "configPV");
        if (!fileRepertConfig.exists()) {
            fileRepertConfig.mkdirs();
            locale = new Locale("fr", "FR");
            setUserAgentStylesheet("file:css/clair.css");
            strRepertoireProjet = strRepertAppli;
        } else {
            lisFichierConfig();
        }
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", locale);
        stPrincipal = primaryStage;
        stPrincipal.setResizable(false);
        stPrincipal.setTitle("PanoVisu v" + strNumVersion);
        //AquaFx.style();
//        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int) tailleEcran.getHeight() - 20;
        int largeur = (int) tailleEcran.getWidth() - 20;
        largeurMax = tailleEcran.getWidth() - 450.0d;
        creeEnvironnement(primaryStage, largeur, hauteur);

        File repertTempFile = new File(strRepertAppli + File.separator + "temp");
        strRepertTemp = repertTempFile.getAbsolutePath();

        if (!repertTempFile.exists()) {
            repertTempFile.mkdirs();
        } else {
            deleteDirectory(strRepertTemp);
        }
        String extTemp = genereChaineAleatoire(20);
        strRepertTemp = strRepertTemp + File.separator + "temp" + extTemp;
        repertTempFile = new File(strRepertTemp);
        repertTempFile.mkdirs();
        installeEvenements();
        projetsNouveau();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Action reponse = null;
            Localization.setLocale(locale);
            if (!bDejaSauve) {
                reponse = Dialogs.create()
                        .owner(null)
                        .title("Quitter l'éditeur")
                        .masthead("ATTENTION ! Vous n'avez pas sauvegardé votre projet")
                        .message("Voulez vous le sauver ?")
                        .actions(Dialog.Actions.YES, Dialog.Actions.NO, Dialog.Actions.CANCEL)
                        .showWarning();
            }
            if (reponse == Dialog.Actions.YES) {
                try {
                    projetSauve();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
                try {
                    sauveHistoFichiers();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                deleteDirectory(strRepertTemp);
                File ftemp = new File(strRepertTemp);
                ftemp.delete();
            } else {
                event.consume();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Package pack = Package.getPackage("editeurpanovisu");
        System.setProperty("file.encoding", "UTF-8");
        strNumVersion = pack.getImplementationVersion();
        strSystemeExploitation = System.getProperty("os.name");
        //Properties properties = System.getProperties();
        //properties.list(//System.out);
        launch(args);
    }
}
