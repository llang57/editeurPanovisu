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
import javafx.scene.control.ListView;
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
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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

        private final DoubleProperty dpX, dpY;

        AncreForme(Color color, DoubleProperty dpX, DoubleProperty dpY) {
            super(dpX.get(), dpY.get(), 2);
            //System.out.println("anchor ==>" + x.get() + "," + y.get());
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            setCursor(Cursor.HAND);

            this.dpX = dpX;
            this.dpY = dpY;

            dpX.bind(centerXProperty());
            dpY.bind(centerYProperty());
            enableDrag();
        }

        private void enableDrag() {
            final Delta dragDelta = new Delta();

            setOnMousePressed((mouseEvent) -> {
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            });

            setOnMouseReleased((mouseEvent) -> {
                getScene().setCursor(Cursor.HAND);
            });

            setOnMouseDragged((mouseEvent) -> {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setCenterY(newY);
                }
            });

            setOnMouseEntered((mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            });

            setOnMouseExited((mouseEvent) -> {
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
    private AnchorPane apVisuPano;
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
    private AnchorPane apVisuPanoramique = new AnchorPane();
    private AfficheNavigateurPanoramique navigateurPanoramique;
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
    private boolean bPleinEcranPanoramique = false;
    private static final String[] strTouchesBarre = {
        "Haut/Up", "Droite/Right", "Bas/Down", "Gauche/Left",
        "Zoom +", "Zoom -", "Info", "Aide/Help",
        "Rotation", "mode Souris/Mouse", "Plein Ecran/Fullscreen",
        "Lien/Link 1", "Lien/Link 2",
        "zone 1", "zone 2", "zone 3", "zone 4", "zone 5",
        "zone 6", "zone 7", "zone 8", "zone 9", "zone 10"
    };
    private static final String[] strCodeBarre = {
        "telUp", "telRight", "telDown", "telLeft",
        "telZoomPlus", "telZoomMoins", "telInfo", "telAide",
        "telRotation", "telSouris", "telFS",
        "telLien-1", "telLien-2",
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
            File fileImagesRepert = new File(strRepertTemp + "/panovisu/images");
            if (!fileImagesRepert.exists()) {
                fileImagesRepert.mkdirs();
            }
            File fileBoutonRepert = new File(strRepertTemp + "/panovisu/images/navigation");
            if (!fileBoutonRepert.exists()) {
                fileBoutonRepert.mkdirs();
            }
            File fileBoussoleRepert = new File(strRepertTemp + "/panovisu/images/boussoles");
            if (!fileBoussoleRepert.exists()) {
                fileBoussoleRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/boussoles", fileBoussoleRepert.getAbsolutePath());
            File filePlanRepert = new File(strRepertTemp + "/panovisu/images/plan");
            if (!filePlanRepert.exists()) {
                filePlanRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/plan", filePlanRepert.getAbsolutePath());
            File fileReseauRepert = new File(strRepertTemp + "/panovisu/images/reseaux");
            if (!fileReseauRepert.exists()) {
                fileReseauRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/reseaux", fileReseauRepert.getAbsolutePath());
            File fileInterfaceRepert = new File(strRepertTemp + "/panovisu/images/interface");
            if (!fileInterfaceRepert.exists()) {
                fileInterfaceRepert.mkdirs();
            }
            copieDirectory(strRepertAppli + File.separator + "panovisu/images/interface", fileInterfaceRepert.getAbsolutePath());
            File fileMARepert = new File(strRepertTemp + "/panovisu/images/MA");
            if (!fileMARepert.exists()) {
                fileMARepert.mkdirs();
            }
            File fileHotspotsRepert = new File(strRepertTemp + "/panovisu/images/hotspots");
            if (!fileHotspotsRepert.exists()) {
                fileHotspotsRepert.mkdirs();
            }
            if (gestionnaireInterface.getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                File fileTelecommandeRepert = new File(strRepertTemp + "/panovisu/images/telecommande");
                if (!fileTelecommandeRepert.exists()) {
                    fileTelecommandeRepert.mkdirs();
                }
                ReadWriteImage.writePng(gestionnaireInterface.getWiBarrePersonnaliseeCouleur(),
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
            for (int i = 0; i < gestionnaireInterface.getiNombreImagesBouton() - 1; i++) {
                ReadWriteImage.writePng(gestionnaireInterface.getWiNouveauxBoutons()[i],
                        fileBoutonRepert.getAbsolutePath() + File.separator + gestionnaireInterface.getStrNomImagesBoutons()[i],
                        false, 0.f);
            }
            ReadWriteImage.writePng(gestionnaireInterface.getWiNouveauxBoutons()[gestionnaireInterface.getiNombreImagesBouton() - 1],
                    fileHotspotsRepert.getAbsolutePath() + File.separator + "hotspot.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.getWiNouveauxBoutons()[gestionnaireInterface.getiNombreImagesBouton()],
                    fileHotspotsRepert.getAbsolutePath() + File.separator + "hotspotImage.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.getWiNouveauxMasque(),
                    fileMARepert.getAbsolutePath() + File.separator + "MA.png",
                    false, 0.f);

            File fileXMLRepert = new File(strRepertTemp + File.separator + "xml");
            if (!fileXMLRepert.exists()) {
                fileXMLRepert.mkdirs();
            }
            File fileCSSRepert = new File(strRepertTemp + File.separator + "css");
            if (!fileCSSRepert.exists()) {
                fileCSSRepert.mkdirs();
            }
            File fileJsRepert = new File(strRepertTemp + File.separator + "js");
            if (!fileJsRepert.exists()) {
                fileJsRepert.mkdirs();
            }
            String strContenuFichier;
            File fileXML;
            String strChargeImages = "";

            for (int i = 0; i < iNombrePanoramiques; i++) {
                String strFichierPano = "panos/" + panoramiquesProjet[i].getStrNomFichier().substring(panoramiquesProjet[i].getStrNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[i].getStrNomFichier().length()).split("\\.")[0];
                if (panoramiquesProjet[i].getStrTypePanoramique().equals(Panoramique.CUBE)) {
                    strFichierPano = strFichierPano.substring(0, strFichierPano.length() - 2);
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_f.jpg\"\n";
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_b.jpg\"\n";
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_u.jpg\"\n";
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_d.jpg\"\n";
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_r.jpg\"\n";
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + "_l.jpg\"\n";
                } else {
                    strChargeImages += "                images[" + i + "]=\"" + strFichierPano + ".jpg\"\n";
                }
                String strAffInfo = (panoramiquesProjet[i].isAfficheInfo()) ? "oui" : "non";
                String strAffTitre = (gestionnaireInterface.isbAfficheTitre()) ? "oui" : "non";
                double regX;
                double zN;
                if (panoramiquesProjet[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
                    regX = Math.round(((panoramiquesProjet[i].getRegardX() - 180) % 360) * 10) / 10;
                    zN = Math.round(((panoramiquesProjet[i].getZeroNord() - 180) % 360) * 10) / 10;
                } else {
                    regX = Math.round(((panoramiquesProjet[i].getRegardX() + 90) % 360) * 10) / 10;
                    zN = Math.round(((panoramiquesProjet[i].getZeroNord() + 90) % 360) * 10) / 10;
                }
                int iRouge = (int) (Color.valueOf(gestionnaireInterface.getStrCouleurDiaporama()).getRed() * 255.d);
                int iBleu = (int) (Color.valueOf(gestionnaireInterface.getStrCouleurDiaporama()).getBlue() * 255.d);
                int iVert = (int) (Color.valueOf(gestionnaireInterface.getStrCouleurDiaporama()).getGreen() * 255.d);
                String strCoulDiapo = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + gestionnaireInterface.getDiaporamaOpacite() + ")";

                Color coulTitre = Color.valueOf(gestionnaireInterface.getStrCouleurFondTitre());
                iRouge = (int) (coulTitre.getRed() * 255.d);
                iBleu = (int) (coulTitre.getBlue() * 255.d);
                iVert = (int) (coulTitre.getGreen() * 255.d);
                String strCoulFondTitre = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + gestionnaireInterface.getTitreOpacite() + ")";

                strContenuFichier = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!--\n"
                        + "     Visite générée par l'éditeur panoVisu \n"
                        + "\n"
                        + "             Création L.LANG      le monde à 360°  : http://lemondea360.fr\n"
                        + "-->\n"
                        + "\n"
                        + "\n"
                        + "<scene>\n"
                        + "   <pano \n"
                        + "      image=\"" + strFichierPano + "\"\n"
                        + "      titre=\"" + panoramiquesProjet[i].getStrTitrePanoramique() + "\"\n"
                        + "      titrePolice=\"" + gestionnaireInterface.getStrTitrePoliceNom() + "\"\n"
                        + "      titreTaillePolice=\"" + Math.round(Double.parseDouble(gestionnaireInterface.getStrTitrePoliceTaille())) + "px\"\n"
                        + "      titreTaille=\"" + Math.round(gestionnaireInterface.getTitreTaille()) + "%\"\n"
                        + "      titreFond=\"" + strCoulFondTitre + "\"\n"
                        + "      titreCouleur=\"" + gestionnaireInterface.getStrCouleurTitre() + "\"\n"
                        //                        + "      titreOpacite=\"" + gestionnaireInterface.titreOpacite + "\"\n"
                        + "      diaporamaCouleur=\"" + strCoulDiapo + "\"\n"
                        + "      type=\"" + panoramiquesProjet[i].getStrTypePanoramique() + "\"\n"
                        + "      multiReso=\"oui\"\n"
                        + "      nombreNiveaux=\"" + panoramiquesProjet[i].getNombreNiveaux() + "\"\n"
                        + "      zeroNord=\"" + zN + "\"\n"
                        + "      regardX=\"" + regX + "\"\n"
                        + "      regardY=\"" + Math.round(panoramiquesProjet[i].getRegardY() * 10) / 10 + "\"\n"
                        + "      affinfo=\"" + strAffInfo + "\"\n"
                        + "      afftitre=\"" + strAffTitre + "\"\n"
                        + "   />\n";
                if (gestionnaireInterface.getStrVisibiliteBarreClassique().equals("oui")) {
                    strContenuFichier
                            += "   <!--Définition de la Barre de navigation-->\n"
                            + "   <boutons \n"
                            + "      styleBoutons=\"navigation\"\n"
                            + "      couleur=\"rgba(255,255,255,0)\"\n"
                            + "      bordure=\"rgba(255,255,255,0)\"\n"
                            + "      deplacements=\"" + gestionnaireInterface.getStrDeplacementsBarreClassique() + "\" \n"
                            + "      zoom=\"" + gestionnaireInterface.getStrZoomBarreClassique() + "\" \n"
                            + "      outils=\"" + gestionnaireInterface.getStrOutilsBarreClassique() + "\"\n"
                            + "      fs=\"" + gestionnaireInterface.getStrPleinEcranBarreClassique() + "\" \n"
                            + "      souris=\"" + gestionnaireInterface.getStrSourisBarreClassique() + "\" \n"
                            + "      rotation=\"" + gestionnaireInterface.getStrRotationBarreClassique() + "\" \n"
                            + "      positionX=\"" + gestionnaireInterface.getStrPositionBarreClassique().split(":")[1] + "\"\n"
                            + "      positionY=\"" + gestionnaireInterface.getStrPositionBarreClassique().split(":")[0] + "\" \n"
                            + "      dX=\"" + gestionnaireInterface.getOffsetXBarreClassique() + "\" \n"
                            + "      dY=\"" + gestionnaireInterface.getOffsetYBarreClassique() + "\"\n"
                            + "      espacement=\"" + Math.round(gestionnaireInterface.getEspacementBarreClassique()) + "\"\n"
                            + "      visible=\"" + gestionnaireInterface.getStrVisibiliteBarreClassique() + "\"\n"
                            + "   />\n";
                }
                if (gestionnaireInterface.getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                    strContenuFichier
                            += "<!--  Barre de Navigation Personnalisée -->\n\n"
                            + "    <telecommande\n"
                            + "        fs=\"" + gestionnaireInterface.getStrPleinEcranBarrePersonnalisee() + "\" \n"
                            + "        souris=\"" + gestionnaireInterface.getStrSourisBarrePersonnalisee() + "\" \n"
                            + "        rotation=\"" + gestionnaireInterface.getStrRotationBarrePersonnalisee() + "\" \n"
                            + "        info=\"" + gestionnaireInterface.getStrInfoBarrePersonnalisee() + "\"\n"
                            + "        aide=\"" + gestionnaireInterface.getStrAideBarrePersonnalisee() + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.getStrPositionBarrePersonnalisee().split(":")[1] + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.getStrPositionBarrePersonnalisee().split(":")[0] + "\" \n"
                            + "        taille=\"" + gestionnaireInterface.getTailleBarrePersonnalisee() + "\"\n"
                            + "        tailleBouton=\"" + gestionnaireInterface.getTailleIconesBarrePersonnalisee() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getOffsetXBarrePersonnalisee() + "\" \n"
                            + "        dY=\"" + gestionnaireInterface.getOffsetYBarrePersonnalisee() + "\"\n"
                            + "        lien1=\"" + gestionnaireInterface.getStrLien1BarrePersonnalisee() + "\"\n"
                            + "        lien2=\"" + gestionnaireInterface.getStrLien2BarrePersonnalisee() + "\"\n"
                            + "        visible=\"oui\">\n";
                    for (int ij = 0; ij < gestionnaireInterface.getiNombreZonesBarrePersonnalisee(); ij++) {
                        strContenuFichier += "        <zoneNavPerso "
                                + "id=\"" + gestionnaireInterface.getZonesBarrePersonnalisee()[ij].getStrIdZone() + "\" "
                                + "alt=\"\" title=\"\" "
                                + "shape=\"" + gestionnaireInterface.getZonesBarrePersonnalisee()[ij].getStrTypeZone() + "\" "
                                + "coords=\"" + gestionnaireInterface.getZonesBarrePersonnalisee()[ij].getStrCoordonneesZone() + "\""
                                + " />\n";
                    }
                    strContenuFichier += "    </telecommande>\n"
                            + "";
                }
                if (gestionnaireInterface.isbAfficheBoussole()) {
                    String strAiguille = (gestionnaireInterface.isbAiguilleMobileBoussole()) ? "oui" : "non";
                    strContenuFichier += "<!--  Boussole -->\n"
                            + "    <boussole \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + gestionnaireInterface.getStrImageBoussole() + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.getTailleBoussole() + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.getStrPositionBoussole().split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.getStrPositionBoussole().split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getOpaciteBoussole() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getOffsetXBoussole() + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.getOffsetYBoussole() + "\"\n"
                            + "        aiguille=\"" + strAiguille + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.isbFenetreInfoPersonnalise()) {
                    String strImgInfoURL = "images/" + gestionnaireInterface.getStrFenetreInfoImage().substring(
                            gestionnaireInterface.getStrFenetreInfoImage().lastIndexOf(File.separator) + 1,
                            gestionnaireInterface.getStrFenetreInfoImage().length());
                    strContenuFichier += "<!--  Fenêtre info personnalisée -->\n"
                            + "    <fenetreInfo \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgInfoURL + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.getFenetreInfoTaille() + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getFenetreInfoOpacite() + "\"\n"
                            + "        dX=\"" + Math.round(gestionnaireInterface.getFenetreInfoPosX() * 10) / 10 + "\"\n"
                            + "        dY=\"" + Math.round(gestionnaireInterface.getFenetreInfoPosY() * 10) / 10 + "\"\n"
                            + "        URL=\"" + gestionnaireInterface.getStrFenetreURL() + "\"\n"
                            + "        texteURL=\"" + gestionnaireInterface.getStrFenetreTexteURL() + "\"\n"
                            + "        couleurURL=\"" + gestionnaireInterface.getStrFenetreURLCouleur() + "\"\n"
                            + "        TailleURL=\"" + Math.round(gestionnaireInterface.getFenetrePoliceTaille() * 10) / 10 + "\"\n"
                            + "        URLdX=\"" + Math.round(gestionnaireInterface.getFenetreURLPosX() * 10) / 10 + "\"\n"
                            + "        URLdY=\"" + Math.round(gestionnaireInterface.getFenetreURLPosY() * 10) / 10 + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.isbFenetreAidePersonnalise()) {
                    String strImgAideURL = "images/" + gestionnaireInterface.getStrFenetreAideImage().substring(
                            gestionnaireInterface.getStrFenetreAideImage().lastIndexOf(File.separator) + 1,
                            gestionnaireInterface.getStrFenetreAideImage().length());
                    strContenuFichier += "<!--  Fenêtre Aide personnalisée -->\n"
                            + "    <fenetreAide \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgAideURL + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.getFenetreAideTaille() + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getFenetreAideOpacite() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getFenetreAidePosX() + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.getFenetreAidePosY() + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.isbAfficheMenuContextuel()) {
                    String strPrecSuiv = (gestionnaireInterface.isbAffichePrecSuivMC()) ? "oui" : "non";
                    String strPlanet = (gestionnaireInterface.isbAffichePlanetNormalMC()) ? "oui" : "non";
                    String strPers1 = (gestionnaireInterface.isbAffichePersMC1()) ? "oui" : "non";
                    String strPers2 = (gestionnaireInterface.isbAffichePersMC2()) ? "oui" : "non";
                    strContenuFichier += "<!--  MenuContextuel -->\n"
                            + "    <menuContextuel \n"
                            + "        affiche=\"oui\"\n"
                            + "        precSuiv=\"" + strPrecSuiv + "\"\n"
                            + "        planete=\"" + strPlanet + "\"\n"
                            + "        pers1=\"" + strPers1 + "\"\n"
                            + "        lib1=\"" + gestionnaireInterface.getStrPersLib1() + "\"\n"
                            + "        url1=\"" + gestionnaireInterface.getStrPersURL1() + "\"\n"
                            + "        pers2=\"" + strPers2 + "\"\n"
                            + "        lib2=\"" + gestionnaireInterface.getStrPersLib2() + "\"\n"
                            + "        url2=\"" + gestionnaireInterface.getStrPersURL2() + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.isbSuivantPrecedent()) {

                    int iPanoSuivant = (i == iNombrePanoramiques - 1) ? 0 : i + 1;
                    int iPanoPrecedent = (i == 0) ? iNombrePanoramiques - 1 : i - 1;
                    String strNomPano = panoramiquesProjet[iPanoSuivant].getStrNomFichier();
                    String strPanoSuivant = "xml/" + strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                    strNomPano = panoramiquesProjet[iPanoPrecedent].getStrNomFichier();
                    String strPanoPrecedent = "xml/" + strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                    strContenuFichier += "<!--  Bouton Suivant Precedent -->\n"
                            + "    <suivantPrecedent\n"
                            + "        suivant=\"" + strPanoSuivant + "\"            \n"
                            + "        precedent=\"" + strPanoPrecedent + "\"            \n"
                            + "    />    \n"
                            + "";
                }
                if (gestionnaireInterface.isbAfficheMasque()) {
                    String strNavigation = (gestionnaireInterface.isbMasqueNavigation()) ? "oui" : "non";
                    String strBoussole = (gestionnaireInterface.isbMasqueBoussole()) ? "oui" : "non";
                    String strTitre = (gestionnaireInterface.isbMasqueTitre()) ? "oui" : "non";
                    String strPlan = (gestionnaireInterface.isbMasquePlan()) ? "oui" : "non";
                    String strReseaux = (gestionnaireInterface.isbMasqueReseaux()) ? "oui" : "non";
                    String strVignettes = (gestionnaireInterface.isbMasqueVignettes()) ? "oui" : "non";
                    String strCombo = (gestionnaireInterface.isbMasqueCombo()) ? "oui" : "non";
                    String strSuivPrec = (gestionnaireInterface.isbMasqueSuivPrec()) ? "oui" : "non";
                    String strHotspots = (gestionnaireInterface.isbMasqueHotspots()) ? "oui" : "non";
                    strContenuFichier += "<!--  Bouton de Masquage -->\n"
                            + "    <marcheArret \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"MA.png\"\n"
                            + "        taille=\"" + gestionnaireInterface.getTailleMasque() + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.getStrPositionMasque().split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.getStrPositionMasque().split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getOpaciteMasque() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getdXMasque() + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.getdYMasque() + "\"\n"
                            + "        navigation=\"" + strNavigation + "\"\n"
                            + "        boussole=\"" + strBoussole + "\"\n"
                            + "        titre=\"" + strTitre + "\"\n"
                            + "        plan=\"" + strPlan + "\"\n"
                            + "        reseaux=\"" + strReseaux + "\"\n"
                            + "        vignettes=\"" + strVignettes + "\"\n"
                            + "        combo=\"" + strCombo + "\"\n"
                            + "        suivPrec=\"" + strSuivPrec + "\"\n"
                            + "        hotspots=\"" + strHotspots + "\"\n"
                            + "    />\n";
                }

                if (gestionnaireInterface.isbAfficheReseauxSociaux()) {
                    String strTwitter = (gestionnaireInterface.isbReseauxSociauxTwitter()) ? "oui" : "non";
                    String strGoogle = (gestionnaireInterface.isbReseauxSociauxGoogle()) ? "oui" : "non";
                    String strFacebook = (gestionnaireInterface.isbReseauxSociauxFacebook()) ? "oui" : "non";
                    String strEmail = (gestionnaireInterface.isbReseauxSociauxEmail()) ? "oui" : "non";
                    strContenuFichier += "<!--  Réseaux Sociaux -->\n"
                            + "    <reseauxSociaux \n"
                            + "        affiche=\"oui\"\n"
                            + "        taille=\"" + gestionnaireInterface.getTailleReseauxSociaux() + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.getStrPositionReseauxSociaux().split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.getStrPositionReseauxSociaux().split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getOpaciteReseauxSociaux() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getdXReseauxSociaux() + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.getdYReseauxSociaux() + "\"\n"
                            + "        twitter=\"" + strTwitter + "\"\n"
                            + "        google=\"" + strGoogle + "\"\n"
                            + "        facebook=\"" + strFacebook + "\"\n"
                            + "        email=\"" + strEmail + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.isbAfficheVignettes()) {
                    String strAfficheVignettes = (gestionnaireInterface.isbAfficheVignettes()) ? "oui" : "non";
                    strContenuFichier += "<!-- Barre des vignettes -->"
                            + "    <vignettes \n"
                            + "        affiche=\"" + strAfficheVignettes + "\"\n"
                            + "        tailleImage=\"" + gestionnaireInterface.getTailleImageVignettes() + "\"\n"
                            + "        fondCouleur=\"" + gestionnaireInterface.getStrCouleurFondVignettes() + "\"\n"
                            + "        texteCouleur=\"" + gestionnaireInterface.getStrCouleurTexteVignettes() + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.getOpaciteVignettes() + "\"\n"
                            + "        position=\"" + gestionnaireInterface.getStrPositionVignettes() + "\"\n"
                            + "    >\n";
                    for (int j = 0; j < iNombrePanoramiques; j++) {
                        String strNomPano = panoramiquesProjet[j].getStrNomFichier();
                        String strFichier = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String strXML = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getImgVignettePanoramique(),
                                strRepertTemp + "/panos/" + strFichier, 1.0f, false, 0.0f);
                        strContenuFichier
                                += "        <imageVignette \n"
                                + "            image=\"panos/" + strFichier + "\"\n"
                                + "            xml=\"xml/" + strXML + "\"\n"
                                + "        />\n";
                    }
                    strContenuFichier
                            += "    </vignettes>       \n"
                            + "";

                }
                if (gestionnaireInterface.isbAfficheComboMenu()) {
                    String strAfficheComboMenu = (gestionnaireInterface.isbAfficheComboMenu()) ? "oui" : "non";
                    TextField tfTitreVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
                    String strTitreVisite = tfTitreVisite.getText();
                    strContenuFichier += "<!-- Barre des comboMenu -->"
                            + "    <comboMenu \n"
                            + "        affiche=\"" + strAfficheComboMenu + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.getStrPositionXComboMenu() + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.getStrPositionYComboMenu() + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.getOffsetXComboMenu() + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.getOffsetYComboMenu() + "\"\n"
                            + "    >\n";
                    for (int j = 0; j < iNombrePanoramiques; j++) {
                        String strNomPano = panoramiquesProjet[j].getStrNomFichier();
                        String strFichier = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String strXML = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getImgVignettePanoramique(),
                                strRepertTemp + "/panos/" + strFichier, 1.0f, false, 0.0f);

                        strContenuFichier
                                += "        <imageComboMenu \n";
                        if (gestionnaireInterface.isbAfficheComboMenuImages()) {
                            strContenuFichier
                                    += "            image=\"panos/" + strFichier + "\"\n";
                        }
                        if (i == j) {
                            strContenuFichier
                                    += "            selectionne=\"selected\"\n";
                        }
                        strContenuFichier
                                += "            xml=\"xml/" + strXML + "\"\n"
                                + "            titre=\"" + panoramiquesProjet[j].getStrTitrePanoramique() + "\"\n"
                                + "            sousTitre=\"" + strTitreVisite + "\"\n"
                                + "        />\n";
                    }
                    strContenuFichier
                            += "    </comboMenu>       \n"
                            + "";

                }

                strContenuFichier += "    <!--Définition des hotspots-->  \n"
                        + "   <hotspots>\n";
                for (int j = 0; j < panoramiquesProjet[i].getNombreHotspots(); j++) {
                    HotSpot HS = panoramiquesProjet[i].getHotspot(j);
                    double longit;
                    if (panoramiquesProjet[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
                        longit = HS.getLongitude() - 180;
                    } else {
                        longit = HS.getLongitude() + 90;
                    }
                    String strAnime = (HS.isAnime()) ? "true" : "false";
                    strContenuFichier
                            += "      <point \n"
                            + "           type=\"panoramique\"\n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspot.png\"\n"
                            + "           xml=\"xml/" + HS.getStrFichierXML() + "\"\n"
                            + "           info=\"" + HS.getStrInfo() + "\"\n"
                            + "           anime=\"" + strAnime + "\"\n"
                            + "      />\n";
                }
                for (int j = 0; j < panoramiquesProjet[i].getNombreHotspotImage(); j++) {
                    HotspotImage HS = panoramiquesProjet[i].getHotspotImage(j);
                    double longit;
                    if (panoramiquesProjet[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
                        longit = HS.getLongitude() - 180;
                    } else {
                        longit = HS.getLongitude() + 90;
                    }
                    String strAnime = (HS.isAnime()) ? "true" : "false";
                    strContenuFichier
                            += "      <point \n"
                            + "           type=\"image\"\n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspotImage.png\"\n"
                            + "           img=\"images/" + HS.getStrLienImg() + "\"\n"
                            + "           info=\"" + HS.getStrInfo() + "\"\n"
                            + "           anime=\"" + strAnime + "\"\n"
                            + "      />\n";
                }
                strContenuFichier += "   </hotspots>\n";
                strContenuFichier += "\n"
                        + "<!-- Définition des images de fond -->\n"
                        + "\n";
                if (gestionnaireInterface.getiNombreImagesFond() > 0) {
                    for (int ii = 0; ii < gestionnaireInterface.getiNombreImagesFond(); ii++) {
                        ImageFond imgFond = gestionnaireInterface.getImagesFond()[ii];
                        String strImgFond = "images/" + imgFond.getStrFichierImage().substring(
                                imgFond.getStrFichierImage().lastIndexOf(File.separator) + 1,
                                imgFond.getStrFichierImage().length());
                        String strMasquable = (imgFond.isMasquable()) ? "oui" : "non";

                        strContenuFichier += "    <imageFond\n"
                                + "        fichier=\"" + strImgFond + "\"\n"
                                + "        posX=\"" + imgFond.getStrPosX() + "\" \n"
                                + "        posY=\"" + imgFond.getStrPosY() + "\" \n"
                                + "        offsetX=\"" + imgFond.getOffsetX() + "\" \n"
                                + "        offsetY=\"" + imgFond.getOffsetY() + "\" \n"
                                + "        tailleX=\"" + imgFond.getTailleX() + "\" \n"
                                + "        tailleY=\"" + imgFond.getTailleY() + "\" \n"
                                + "        opacite=\"" + imgFond.getOpacite() + "\" \n"
                                + "        masquable=\"" + strMasquable + "\" \n"
                                + "        url=\"" + imgFond.getStrUrl() + "\" \n"
                                + "        infobulle=\"" + imgFond.getStrInfobulle() + "\" \n"
                                + "    />\n"
                                + "";
                    }
                }
                if (gestionnaireInterface.isbAffichePlan() && panoramiquesProjet[i].isAffichePlan()) {
                    int iNumPlan = panoramiquesProjet[i].getNumeroPlan();
                    Plan planPano = plans[iNumPlan];
                    iRouge = (int) (gestionnaireInterface.getCouleurFondPlan().getRed() * 255.d);
                    iBleu = (int) (gestionnaireInterface.getCouleurFondPlan().getBlue() * 255.d);
                    iVert = (int) (gestionnaireInterface.getCouleurFondPlan().getGreen() * 255.d);
                    String strAfficheRadar = (gestionnaireInterface.isbAfficheRadar()) ? "oui" : "non";
                    String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + gestionnaireInterface.getOpacitePlan() + ")";
                    strContenuFichier
                            += "    <plan\n"
                            + "        affiche=\"oui\" \n"
                            + "        image=\"images/" + planPano.getStrImagePlan() + "\"\n"
                            + "        largeur=\"" + gestionnaireInterface.getLargeurPlan() + "\"\n"
                            + "        position=\"" + gestionnaireInterface.getStrPositionPlan() + "\"\n"
                            + "        couleurFond=\"" + strCoulFond + "\"\n"
                            + "        couleurTexte=\"#" + gestionnaireInterface.getStrCouleurTextePlan() + "\"\n"
                            + "        nord=\"" + planPano.getDirectionNord() + "\"\n"
                            + "        boussolePosition=\"" + planPano.getStrPosition() + "\"\n"
                            + "        boussoleX=\"" + planPano.getPositionX() + "\"\n"
                            + "        boussoleY=\"" + planPano.getPositionX() + "\"\n"
                            + "        radarAffiche=\"" + strAfficheRadar + "\"\n"
                            + "        radarTaille=\"" + Math.round(gestionnaireInterface.getTailleRadar()) + "\"\n"
                            + "        radarCouleurFond=\"#" + gestionnaireInterface.getStrCouleurFondRadar() + "\"\n"
                            + "        radarCouleurLigne=\"#" + gestionnaireInterface.getStrCouleurLigneRadar() + "\"\n"
                            + "        radarOpacite=\"" + Math.round(gestionnaireInterface.getOpaciteRadar() * 100.d) / 100.d + "\"\n"
                            + "    >\n";
                    for (int iPoint = 0; iPoint < planPano.getNombreHotspots(); iPoint++) {
                        double posX = planPano.getHotspot(iPoint).getLongitude() * gestionnaireInterface.getLargeurPlan();
                        double posY = planPano.getHotspot(iPoint).getLatitude()
                                * planPano.getHauteurPlan() * gestionnaireInterface.getLargeurPlan() / planPano.getLargeurPlan();
                        if (planPano.getHotspot(iPoint).getNumeroPano() == i) {
                            strContenuFichier
                                    += "        <pointPlan\n"
                                    + "            positX=\"" + posX + "\"\n"
                                    + "            positY=\"" + posY + "\"\n"
                                    + "            xml=\"actif\"\n"
                                    + "            />  \n";

                        } else {
                            strContenuFichier
                                    += "        <pointPlan\n"
                                    + "            positX=\"" + posX + "\"\n"
                                    + "            positY=\"" + posY + "\"\n"
                                    + "            xml=\"xml/" + planPano.getHotspot(iPoint).getStrFichierXML() + "\"\n"
                                    + "            texte=\"" + planPano.getHotspot(iPoint).getStrInfo() + "\"\n"
                                    + "            />  \n";
                        }
                    }
                    strContenuFichier
                            += "    </plan>\n";
                }

                strContenuFichier += "</scene>\n";
                String strFichPano = panoramiquesProjet[i].getStrNomFichier();
                String strNomXMLFile = strFichPano.substring(strFichPano.lastIndexOf(File.separator) + 1, strFichPano.length()).split("\\.")[0] + ".xml";
                fileXML = new File(fileXMLRepert + File.separator + strNomXMLFile);
                fileXML.setWritable(true);
                OutputStreamWriter oswFichierXML = new OutputStreamWriter(new FileOutputStream(fileXML), "UTF-8");
                try (BufferedWriter bwFichierXML = new BufferedWriter(oswFichierXML)) {
                    bwFichierXML.write(strContenuFichier);
                }
            }
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int iHauteur = (int) tailleEcran.getHeight() - 200;
            String strTitreVis = "Panovisu - visualiseur 100% html5 (three.js)";
            TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
            if (!tfVisite.getText().equals("")) {
                strTitreVis = tfVisite.getText() + " - " + strTitreVis;
            }
            String strFPano1 = "panos/niveau0/" + panoramiquesProjet[0].getStrNomFichier().substring(panoramiquesProjet[0].getStrNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getStrNomFichier().length());

            String strFichierHTML = "<!DOCTYPE html>\n"
                    + "<html lang=\"fr\">\n"
                    + "    <head>\n"
                    + "        <title>" + strTitreVis + "</title>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\">\n"
                    + "        <link rel=\"stylesheet\" media=\"screen\" href=\"panovisu/libs/jqueryMenu/jquery.contextMenu.css\" type=\"text/css\"/>\n"
                    + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"panovisu/css/msdropdown/dd.css\" />\n"
                    + "        <meta property=\"og:title\" content=\"" + strTitreVis + "\" />\n"
                    + "        <meta property=\"og:description\" content=\"Une page créée avec panoVisu Editeur : 100% Libre 100% HTML5\" />\n"
                    + "        <meta property=\"og:image\" content=\"" + strFPano1 + "\" />"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <header>\n"
                    + "\n"
                    + "        </header>\n"
                    + "        <article style=\"height : " + iHauteur + "px;\">\n"
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
                strFichierHTML = strFichierHTML.replace("PANO",
                        panoramiquesProjet[0].getStrNomFichier().substring(panoramiquesProjet[0].getStrNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getStrNomFichier().length()).split("\\.")[0]);
            } else {
                strFichierHTML = strFichierHTML.replace("PANO", strPanoEntree);
            }
            File fileIndexHTML = new File(strRepertTemp + File.separator + "index.html");
            fileIndexHTML.setWritable(true);
            OutputStreamWriter oswFichierHTML = new OutputStreamWriter(new FileOutputStream(fileIndexHTML), "UTF-8");
            try (BufferedWriter bwFichierHTML = new BufferedWriter(oswFichierHTML)) {
                bwFichierHTML.write(strFichierHTML);
            }
            DirectoryChooser dcRepertChoix = new DirectoryChooser();
            dcRepertChoix.setTitle("Choix du repertoire de sauvegarde de la visite");
            File fileRepert = new File(EditeurPanovisu.strRepertoireProjet);
            dcRepertChoix.setInitialDirectory(fileRepert);
            File fileRepertVisite = dcRepertChoix.showDialog(null);

            String strNomRepertVisite = fileRepertVisite.getAbsolutePath();
            copieDirectory(strRepertTemp, strNomRepertVisite);
            Dialogs.create().title(rbLocalisation.getString("main.dialog.generationVisite"))
                    .message(rbLocalisation.getString("main.dialog.generationVisiteMessage") + strNomRepertVisite)
                    .showInformation();
            if (Desktop.isDesktopSupported()) {
                if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop dskNavigateurVisite = Desktop.getDesktop();
                    File fileIndex = new File(strNomRepertVisite + File.separator + "index.html");
                    dskNavigateurVisite.browse(fileIndex.toURI());
                }
            }
        } else {
            Dialogs.create().title(rbLocalisation.getString("main.dialog.generationVisite"))
                    .message(rbLocalisation.getString("main.dialog.generationVisiteMessageErreur"))
                    .showError();

        }
    }

    private int creeNiveauxImageEqui(String strFichierImage, String strRepertoire, int iNumero, int iTotal) {
        Platform.runLater(() -> {
            lblNiveaux.setText("Création niveau principal");
        });

        double tailleNiv0 = 512.d;
        String strExtension = strFichierImage.substring(strFichierImage.length() - 3, strFichierImage.length());
        Image imgPano = null;
        if (strExtension.equals("tif")) {
            try {
                imgPano = ReadWriteImage.readTiff(strFichierImage);
                if (imgPano.getWidth() > 8000) {
                    imgPano = ReadWriteImage.resizeImage(imgPano, 8000, 4000);
                }
            } catch (ImageReadException | IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            imgPano = new Image("file:" + strFichierImage);
            if (imgPano.getWidth() > 8000) {
                imgPano = new Image("file:" + strFichierImage, 8000, 4000, true, true);
            }
        }
        String strNomPano = strFichierImage.substring(strFichierImage.lastIndexOf(File.separator) + 1, strFichierImage.length()).split("\\.")[0] + ".jpg";
        String strFicImage = strRepertoire + File.separator + strNomPano;
        try {
            ReadWriteImage.writeJpeg(imgPano, strFicImage, 0.85f, true, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }
        int iNombreNiveaux = 0;
        if (imgPano != null) {
            double tailleImage = imgPano.getWidth();
            strFichierImage = strFicImage;
            iNombreNiveaux = (int) (Math.log(tailleImage / tailleNiv0) / Math.log(2.d)) + 1;
            int iNbNiv = iNombreNiveaux;
            for (int i = 0; i < iNombreNiveaux; i++) {
                int ii = i;
                int iNombreNiv2 = iNombreNiveaux;
                Platform.runLater(() -> {
                    lblNiveaux.setText("Création niveau " + ii + "/" + (iNbNiv - 1));
                    pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 1.d / 3.d + 1.d / 3.d * ((double) ii) / iNombreNiv2) / (double) iTotal);
                });
                try {
                    double tailleNiveau = tailleImage * Math.pow(2.d, i) / Math.pow(2.d, iNombreNiveaux);
                    if (strExtension.equals("tif")) {
                        try {
                            imgPano = ReadWriteImage.readTiff(strFichierImage);
                            imgPano = ReadWriteImage.resizeImage(imgPano, (int) tailleNiveau, (int) tailleNiveau / 2);
                        } catch (ImageReadException ex) {
                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        imgPano = new Image("file:" + strFichierImage, Math.round(tailleNiveau * 2.d) / 2.d, Math.round(tailleNiveau / 2.d), true, true);
                    }
                    String strRepNiveau = strRepertoire + File.separator + "niveau" + i;
                    File fileRepert = new File(strRepNiveau);
                    if (!fileRepert.exists()) {
                        fileRepert.mkdirs();
                    }
                    strNomPano = strFichierImage.substring(strFichierImage.lastIndexOf(File.separator) + 1, strFichierImage.length());
                    strNomPano = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.length()).split("\\.")[0] + ".jpg";
                    strFicImage = strRepNiveau + File.separator + strNomPano;
                    ReadWriteImage.writeJpeg(imgPano, strFicImage, 0.7f, true, 0.1f);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return iNombreNiveaux;
    }

    private int creeNiveauxImageCube(String strFichierImage, String strRepertoire, String strFace) {
        Platform.runLater(() -> {
            lblNiveaux.setText("Création face : " + strFace + " niveau principal");
        });

        double tailleNiv0 = 256.d;
        String strExtension = strFichierImage.substring(strFichierImage.length() - 3, strFichierImage.length());
        Image imgPano = null;
        if (strExtension.equals("tif")) {
            try {
                imgPano = ReadWriteImage.readTiff(strFichierImage);
                if (imgPano.getWidth() > 4096) {
                    imgPano = ReadWriteImage.resizeImage(imgPano, 4096, 4096);
                }
            } catch (ImageReadException | IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            imgPano = new Image("file:" + strFichierImage);
            if (imgPano.getWidth() > 4096) {
                imgPano = new Image("file:" + strFichierImage, 4096, 4096, true, true);
            }
        }
        String strNomPano = strFichierImage.substring(strFichierImage.lastIndexOf(File.separator) + 1, strFichierImage.length());
        strNomPano = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.length()).split("\\.")[0] + ".jpg";
        String strFicImage = strRepertoire + File.separator + strNomPano;
        try {
            ReadWriteImage.writeJpeg(imgPano, strFicImage, 0.85f, true, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }
        int iNombreNiveaux = 0;
        if (imgPano != null) {
            double tailleImage = imgPano.getWidth();
            iNombreNiveaux = (int) (Math.log(tailleImage / tailleNiv0) / Math.log(2.d)) + 1;
            int nbNiv = iNombreNiveaux;
            for (int i = 0; i < iNombreNiveaux; i++) {
                try {
                    int ii = i;
                    Platform.runLater(() -> {
                        lblNiveaux.setText("Création face : " + strFace + " niveau " + ii + "/" + (nbNiv - 1));
                    });

                    double tailleNiveau = tailleImage * Math.pow(2.d, i) / Math.pow(2.d, iNombreNiveaux);
                    if (strExtension.equals("tif")) {
                        try {
                            imgPano = ReadWriteImage.readTiff(strFichierImage);
                            imgPano = ReadWriteImage.resizeImage(imgPano, (int) tailleNiveau, (int) tailleNiveau);
                        } catch (ImageReadException ex) {
                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        imgPano = new Image("file:" + strFichierImage, tailleNiveau, tailleNiveau, true, true);
                    }

                    String strRepNiveau = strRepertoire + File.separator + "niveau" + i;
                    File fileRepert = new File(strRepNiveau);
                    if (!fileRepert.exists()) {
                        fileRepert.mkdirs();
                    }
                    strNomPano = strFichierImage.substring(strFichierImage.lastIndexOf(File.separator) + 1, strFichierImage.length());
                    strNomPano = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.length()).split("\\.")[0] + ".jpg";
                    strFicImage = strRepNiveau + File.separator + strNomPano;
                    ReadWriteImage.writeJpeg(imgPano, strFicImage, 0.7f, true, 0.1f);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return iNombreNiveaux;
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
        File fileRepert = new File(strCurrentDir + File.separator);
        fileChooser.setInitialDirectory(fileRepert);
//        fileChooser.getExtensionFilters().addAll(extFilterImage, extFilterJpeg, extFilterBmp);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterTiff, extFilterBmp, extFilterImage);

        List<File> listFichiers = fileChooser.showOpenMultipleDialog(null);
        if (listFichiers != null) {
            apAttends.setVisible(true);
            mbarPrincipal.setDisable(true);
            hbBarreBouton.setDisable(true);
            pbarAvanceChargement.setProgress(-1);
            tpEnvironnement.setDisable(true);
            int i = 0;
            File[] fileLstFich1 = new File[listFichiers.size()];
            String[] typeFich1 = new String[listFichiers.size()];
            for (File fileFichier : listFichiers) {
                fileLstFich1[i] = fileFichier;
                i++;
            }
            int iNb = i;
            lblDragDrop.setVisible(false);
            Task taskTraitementChargeFichiers;
            taskTraitementChargeFichiers = chargeListeFichiers(fileLstFich1, iNb);
            Thread thrChargeFichiers = new Thread(taskTraitementChargeFichiers);
            thrChargeFichiers.setDaemon(true);
            thrChargeFichiers.start();

        }

    }

    public Task chargeListeFichiers(final File[] listFichiers, int iNb) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int i = 0;
                File[] fileLstFich1 = new File[listFichiers.length];
                String[] strTypeFich1 = new String[listFichiers.length];
                for (int jj = 0; jj < iNb; jj++) {
                    File fileFichier = listFichiers[jj];
                    String strNomfich = fileFichier.getAbsolutePath();
                    String strExtension = strNomfich.substring(strNomfich.lastIndexOf(".") + 1, strNomfich.length()).toLowerCase();
//                            if (extension.equals("bmp") || extension.equals("jpg")) {
                    if (strExtension.equals("jpg") || strExtension.equals("bmp") || strExtension.equals("tif")) {
                        Image imgPano = null;
                        if (strExtension.equals("tif")) {
                            try {
                                imgPano = ReadWriteImage.readTiff(fileFichier.getAbsolutePath());
                            } catch (ImageReadException | IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            imgPano = new Image("file:" + fileFichier.getAbsolutePath());
                        }
                        strTypeFich1[i] = "";
                        if (imgPano != null) {
                            if (imgPano.getWidth() == 2 * imgPano.getHeight()) {
                                fileLstFich1[i] = fileFichier;
                                strTypeFich1[i] = Panoramique.SPHERE;
                                i++;
                            }
                            if (imgPano.getWidth() == imgPano.getHeight()) {
                                String strNom = fileFichier.getAbsolutePath().substring(0, fileFichier.getAbsolutePath().length() - 6);
                                boolean bTrouveFichier = false;
                                for (int j = 0; j < i; j++) {
                                    String strNom1 = "";
                                    if (fileLstFich1[j].getAbsolutePath().length() == fileFichier.getAbsolutePath().length()) {
                                        strNom1 = fileLstFich1[j].getAbsolutePath().substring(0, fileFichier.getAbsolutePath().length() - 6);
                                    }
                                    if (strNom.equals(strNom1)) {
                                        bTrouveFichier = true;
                                    }
                                }
                                if (!bTrouveFichier) {
                                    fileLstFich1[i] = fileFichier;
                                    strTypeFich1[i] = Panoramique.CUBE;
                                    i++;
                                }
                            }
                        }

                    }
                }
                File[] fileLstFich = new File[i];
                System.arraycopy(fileLstFich1, 0, fileLstFich, 0, i);

                for (int ii = 0; ii < fileLstFich.length; ii++) {
                    File fileFichier1 = fileLstFich[ii];
                    int iNumP = ii + 1;
                    Platform.runLater(() -> {
                        lblCharge.setText("pano " + iNumP + "/" + fileLstFich.length + " : " + fileFichier1.getPath());
                        lblNiveaux.setText("");
                        pbarAvanceChargement.setProgress((double) (iNumP - 1) / (double) fileLstFich.length);
                    });

                    bDejaSauve = false;
                    mniSauveProjet.setDisable(false);
                    strCurrentDir = fileFichier1.getParent();
                    File fileImageRepert = new File(strRepertTemp + File.separator + "panos");

                    if (!fileImageRepert.exists()) {

                        fileImageRepert.mkdirs();
                    }
                    strRepertPanos = fileImageRepert.getAbsolutePath();
                    ajoutePanoramiqueProjet(fileFichier1.getPath(), strTypeFich1[ii], iNumP, fileLstFich.length);
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
                    String strFichierPano = panoramiquesProjet[i].getStrNomFichier();
                    String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                    cbListeChoixPanoramique.getItems().add(strNomPano);
                    cbListeChoixPanoramiqueEntree.getItems().add(strNomPano);
                    String strExtension = panoramiquesProjet[i].getStrNomFichier().substring(panoramiquesProjet[i].getStrNomFichier().length() - 3, panoramiquesProjet[i].getStrNomFichier().length());
//                    if ("tif".equals(strExtension)) {
//                        try {
//                            panoramiquesProjet[i].setImgVisuPanoramique(
//                                    AfficheNavigateurPanoramique.imgTransformationImage(ReadWriteImage.readTiff(panoramiquesProjet[i].getStrNomFichier())));
//                        } catch (ImageReadException | IOException ex) {
//                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    } else {
//
//                        panoramiquesProjet[i].setImgVisuPanoramique(
//                                AfficheNavigateurPanoramique.imgTransformationImage(new Image("file:" + panoramiquesProjet[i].getStrNomFichier()), 2));
//                    }
                }
                //System.out.println("Fin ==> panoActuel : " + panoActuel + " nombre panoramiques " + nombrePanoramiques);
                vbChoixPanoramique.setVisible(true);
                ivImagePanoramique.setImage(panoramiquesProjet[iPanoActuel].getImgPanoramique());
                ivImagePanoramique.setSmooth(true);
                retireAffichageLigne();
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                iNumPoints = 0;
                ajouteAffichageLignes();
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(iPanoActuel));
                affichePoV(panoramiquesProjet[iPanoActuel].getRegardX(), panoramiquesProjet[iPanoActuel].getRegardY());
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

        File fileFichierPlan = fileChooser.showOpenDialog(null);
        if (fileFichierPlan != null) {
            plans[iNombrePlans] = new Plan();
            plans[iNombrePlans].setStrImagePlan(fileFichierPlan.getName());
            plans[iNombrePlans].setStrLienPlan(fileFichierPlan.getAbsolutePath());
            File fileRepertoirePlan = new File(strRepertTemp + File.separator + "images");
            if (!fileRepertoirePlan.exists()) {
                fileRepertoirePlan.mkdirs();
            }
            try {
                copieFichierRepertoire(fileFichierPlan.getAbsolutePath(), fileRepertoirePlan.getAbsolutePath());
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
        TextField tfTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
        if (tfTitrePano != null) {
            panoramiquesProjet[iPanoActuel].setStrTitrePanoramique(tfTitrePano.getText());
        }
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
        Action actReponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            actReponse = Dialogs.create()
                    .owner(null)
                    .title(rbLocalisation.getString("main.dialog.chargeProjet"))
                    .message(rbLocalisation.getString("main.dialog.chargeProjetMessage"))
                    .showConfirm();

        }
        if (actReponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((actReponse == Dialog.Actions.YES) || (actReponse == Dialog.Actions.NO) || (actReponse == null)) {
            panePanoramique.getChildren().clear();
            panePanoramique.getChildren().add(ivImagePanoramique);
            apAttends.setVisible(true);
            mbarPrincipal.setDisable(true);
            hbBarreBouton.setDisable(true);
            tpEnvironnement.setDisable(true);
            bDejaSauve = true;
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File fileRepert = new File(strRepertoireProjet + File.separator);
            fcRepertChoix.setInitialDirectory(fileRepert);
            fileProjet = null;
            fileProjet = fcRepertChoix.showOpenDialog(stPrincipal);
            if (fileProjet != null) {
                stPrincipal.setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                lblDragDrop.setVisible(false);
                strRepertoireProjet = fileProjet.getParent();
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                bRepertSauveChoisi = true;
                deleteDirectory(strRepertTemp);
                String strRepertPanovisu = strRepertTemp + File.separator + "panovisu";
                File fileRptPanovisu = new File(strRepertPanovisu);
                fileRptPanovisu.mkdirs();
                copieDirectory(strRepertAppli + File.separator + "panovisu", strRepertPanovisu);
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
                    String strTexte;
                    try (BufferedReader brFichierPVU = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        strTexte = "";
                        String strLigneTexte;
                        iNombrePanoramiquesFichier = 0;
                        while ((strLigneTexte = brFichierPVU.readLine()) != null) {
                            if (strLigneTexte.contains("Panoramique=>")) {
                                iNombrePanoramiquesFichier++;
                                //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                            }
                            strTexte += strLigneTexte + "\n";
                        }
                    }

                    Task taskAnalysePVU;
                    taskAnalysePVU = analyseFichierPVU(strTexte);
                    Thread thrAnalysePVU = new Thread(taskAnalysePVU);
                    thrAnalysePVU.setDaemon(true);
                    thrAnalysePVU.start();

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
        int iTrouve = -1;
        for (int i = 0; i < nombreHistoFichiers; i++) {
            if (strHistoFichiers[i].equals(nomFich)) {
                iTrouve = i;
            }
        }
        if (iTrouve == -1) {
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
            for (int i = iTrouve - 1; i >= 0; i--) {
                if (i < 9) {
                    strHistoFichiers[i + 1] = strHistoFichiers[i];
                }
            }
            strHistoFichiers[0] = nomFich;

        }
        mnuDerniersProjets.getItems().clear();
        for (int i = 0; i < nombreHistoFichiers; i++) {
            if (strHistoFichiers[i] != null) {
                MenuItem mniDerniersFichiers = new MenuItem(strHistoFichiers[i]);
                mnuDerniersProjets.getItems().add(mniDerniersFichiers);
                mniDerniersFichiers.setOnAction((e) -> {
                    MenuItem mniFichier = (MenuItem) e.getSource();

                    try {
                        try {
                            projetChargeNom(mniFichier.getText());
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
     * strNomFich@param nomFich
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void projetChargeNom(String strNomFich) throws IOException, InterruptedException {
        File fileProjet1 = new File(strNomFich);
        if (fileProjet1.exists()) {
            Action actReponse = null;
            Localization.setLocale(locale);
            if (!bDejaSauve) {
                actReponse = Dialogs.create()
                        .owner(null)
                        .title(rbLocalisation.getString("main.dialog.chargeProjet"))
                        .message(rbLocalisation.getString("main.dialog.chargeProjetMessage"))
                        .showConfirm();

            }
            if (actReponse == Dialog.Actions.YES) {
                try {
                    projetSauve();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((actReponse == Dialog.Actions.YES) || (actReponse == Dialog.Actions.NO) || (actReponse == null)) {
                panePanoramique.getChildren().clear();
                panePanoramique.getChildren().add(ivImagePanoramique);
                apAttends.setVisible(true);
                mbarPrincipal.setDisable(true);
                hbBarreBouton.setDisable(true);
                tpEnvironnement.setDisable(true);
                lblDragDrop.setVisible(false);
                bDejaSauve = true;
                fileProjet = fileProjet1;
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                stPrincipal.setTitle("Panovisu  v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                strRepertoireProjet = fileProjet.getParent();
                bRepertSauveChoisi = true;
                deleteDirectory(strRepertTemp);
                String strRepertPanovisu = strRepertTemp + File.separator + "panovisu";
                File fileRptPanovisu = new File(strRepertPanovisu);
                fileRptPanovisu.mkdirs();
                copieDirectory(strRepertAppli + File.separator + "panovisu", strRepertPanovisu);
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
                    String strTexte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        strTexte = "";
                        String strLigneTexte;
                        iNombrePanoramiquesFichier = 0;
                        while ((strLigneTexte = br.readLine()) != null) {
                            if (strLigneTexte.contains("Panoramique=>")) {
                                iNombrePanoramiquesFichier++;
                                //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                            }
                            strTexte += strLigneTexte + "\n";
                        }
                    }
                    Task taskAnalysePVU;
                    taskAnalysePVU = analyseFichierPVU(strTexte);
                    Thread thrAnalysePVU = new Thread(taskAnalysePVU);
                    thrAnalysePVU.setDaemon(true);
                    thrAnalysePVU.start();

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
        File fileFichierProjet = new File(fileProjet.getAbsolutePath());
        if (!fileFichierProjet.exists()) {
            fileFichierProjet.createNewFile();
        }
        bDejaSauve = true;
        stPrincipal.setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());

        String strContenuFichier = "";
        TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
        if (!tfVisite.getText().equals("")) {
            strContenuFichier += "[titreVisite=>" + tfVisite.getText() + "]\n";
        }
        if (!strPanoEntree.equals("")) {
            strContenuFichier += "[PanoEntree=>" + strPanoEntree + "]\n";
        }
        for (int i = 0; i < iNombrePanoramiques; i++) {
            strContenuFichier += "[Panoramique=>"
                    + "fichier:" + panoramiquesProjet[i].getStrNomFichier()
                    + ";nb:" + panoramiquesProjet[i].getNombreHotspots()
                    + ";nbImg:" + panoramiquesProjet[i].getNombreHotspotImage()
                    + ";titre:" + panoramiquesProjet[i].getStrTitrePanoramique() + ""
                    + ";type:" + panoramiquesProjet[i].getStrTypePanoramique()
                    + ";afficheInfo:" + panoramiquesProjet[i].isAfficheInfo()
                    + ";afficheTitre:" + panoramiquesProjet[i].isAfficheTitre()
                    + ";regardX:" + panoramiquesProjet[i].getRegardX()
                    + ";regardY:" + panoramiquesProjet[i].getRegardY()
                    + ";zeroNord:" + panoramiquesProjet[i].getZeroNord()
                    + ";affichePlan:" + panoramiquesProjet[i].isAffichePlan()
                    + ";numeroPlan:" + panoramiquesProjet[i].getNumeroPlan()
                    + "]\n";
            for (int j = 0; j < panoramiquesProjet[i].getNombreHotspots(); j++) {
                HotSpot HS = panoramiquesProjet[i].getHotspot(j);
                strContenuFichier += "   [hotspot==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getStrFichierImage()
                        + ";xml:" + HS.getStrFichierXML()
                        + ";info:" + HS.getStrInfo()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
            for (int j = 0; j < panoramiquesProjet[i].getNombreHotspotImage(); j++) {
                HotspotImage HS = panoramiquesProjet[i].getHotspotImage(j);
                strContenuFichier += "   [hotspotImage==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getStrFichierImage()
                        + ";img:" + HS.getStrLienImg()
                        + ";urlImg:" + HS.getStrUrlImage()
                        + ";info:" + HS.getStrInfo()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
        }
        strContenuFichier += "[Interface=>\n" + gestionnaireInterface.strGetTemplate() + "]\n";
        strContenuFichier += gestionnairePlan.getTemplate();
        fileProjet.setWritable(true);
        OutputStreamWriter oswFichierPVU = new OutputStreamWriter(new FileOutputStream(fileProjet), "UTF-8");
        try (BufferedWriter bwFichierPVU = new BufferedWriter(oswFichierPVU)) {
            bwFichierPVU.write(strContenuFichier);
        }
        Dialogs.create().title(rbLocalisation.getString("main.dialog.sauveProjet"))
                .message(rbLocalisation.getString("main.dialog.sauveProjetMessage"))
                .showInformation();

    }

    private void sauveHistoFichiers() throws IOException {
        File fileFichConfig = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + File.separator + "derniersprojets.cfg");
        if (!fileFichConfig.exists()) {
            fileFichConfig.createNewFile();
        }
        fileFichConfig.setWritable(true);
        String strContenuFichier = "";
        for (int i = 0; i < nombreHistoFichiers; i++) {
            strContenuFichier += strHistoFichiers[i] + "\n";
        }
        OutputStreamWriter oswFichierHisto = null;
        try {
            oswFichierHisto = new OutputStreamWriter(new FileOutputStream(fileFichConfig), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bwFichierHisto = new BufferedWriter(oswFichierHisto);
        try {
            bwFichierHisto.write(strContenuFichier);
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bwFichierHisto.close();
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
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(strRepertoireProjet + File.separator);
            fcRepertChoix.setInitialDirectory(repert);
            fileProjet = fcRepertChoix.showSaveDialog(null);

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
        FileChooser fcRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
        fcRepertChoix.getExtensionFilters().add(extFilter);
        File fileRepert = new File(strRepertoireProjet + File.separator);
        fcRepertChoix.setInitialDirectory(fileRepert);
        fileProjet = fcRepertChoix.showSaveDialog(null);
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
        Action actReponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            actReponse = Dialogs.create()
                    .owner(null)
                    .title(rbLocalisation.getString("main.dialog.quitterApplication"))
                    .message(rbLocalisation.getString("main.dialog.chargeProjetMessage"))
                    .showConfirm();

        }
        if (actReponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((actReponse == Dialog.Actions.YES) || (actReponse == Dialog.Actions.NO) || (actReponse == null)) {
            sauveHistoFichiers();
            deleteDirectory(strRepertTemp);
            File fileTemp = new File(strRepertTemp);
            fileTemp.delete();
            Platform.exit();
        }
    }

    /**
     *
     */
    private void projetsNouveau() {
        Action actReponse = null;
        Localization.setLocale(locale);
        if (!bDejaSauve) {
            actReponse = Dialogs.create()
                    .owner(null)
                    .title(rbLocalisation.getString("main.dialog.nouveauProjet"))
                    .message(rbLocalisation.getString("main.dialog.chargeProjetMessage"))
                    .showConfirm();

        }
        if (actReponse == Dialog.Actions.YES) {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((actReponse == Dialog.Actions.YES) || (actReponse == Dialog.Actions.NO) || (actReponse == null)) {
            strPanoEntree = "";
            deleteDirectory(strRepertTemp);
            String strRepertPanovisu = strRepertTemp + File.separator + "panovisu";
            File fileRptPanovisu = new File(strRepertPanovisu);
            fileRptPanovisu.mkdirs();
            copieDirectory(strRepertAppli + File.separator + "panovisu", strRepertPanovisu);
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
            gestionnaireInterface.setiNombreImagesFond(0);
            gestionnairePlan.setiPlanActuel(-1);
            ivImagePanoramique.setImage(null);
            cbListeChoixPanoramique.getItems().clear();
            cbListeChoixPanoramiqueEntree.getItems().clear();
            lblDragDrop.setVisible(true);
            Node nodAncienNord = (Node) panePanoramique.lookup("#Nord");
            if (nodAncienNord != null) {
                panePanoramique.getChildren().remove(nodAncienNord);
            }
            Node nodAncienPoV = (Node) panePanoramique.lookup("#PoV");
            if (nodAncienPoV != null) {
                panePanoramique.getChildren().remove(nodAncienPoV);
            }

            gestionnairePlan.getLblDragDropPlan().setVisible(true);
            iNombrePlans = 0;
            plans = new Plan[100];
            gestionnairePlan.creeInterface(iLargeurInterface, iHauteurInterface - 60);
            Pane panePanneauPlan = gestionnairePlan.getPaneInterface();
            mniAffichagePlan.setDisable(true);
            tabPlan.setDisable(true);
            tabPlan.setContent(panePanneauPlan);
            spVuePanoramique.setOnDragOver((DragEvent event) -> {
                Dragboard dbFichiersPanoramiques = event.getDragboard();
                if (dbFichiersPanoramiques.hasFiles()) {
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
                Dragboard dbFichiersPanoramiques = event.getDragboard();
                boolean bSucces = false;
                if (dbFichiersPanoramiques.hasFiles()) {
                    apAttends.setVisible(true);
                    mbarPrincipal.setDisable(true);
                    hbBarreBouton.setDisable(true);
                    tpEnvironnement.setDisable(true);
                    pbarAvanceChargement.setProgress(-1);
                    bSucces = true;
                    String strFichierPath = null;
                    File[] fileList = new File[100];
                    int i = 0;
                    for (File filePano : dbFichiersPanoramiques.getFiles()) {
                        strFichierPath = filePano.getAbsolutePath();
                        fileList[i] = filePano;
                        i++;
                    }
                    int iNb = i;
                    if (fileList != null) {
                        lblDragDrop.setVisible(false);
                        Task taskChargeFichiers;
                        taskChargeFichiers = chargeListeFichiers(fileList, iNb);
                        Thread thChargeFichiers = new Thread(taskChargeFichiers);
                        thChargeFichiers.setDaemon(true);
                        thChargeFichiers.start();
                    }

                }
                event.setDropCompleted(bSucces);
                event.consume();
            });

        }
    }

    /**
     *
     * @param nb
     * @return chaine de caractères aléatoire
     */
    private String genereChaineAleatoire(int iNombre) {
        String strChaine = "";
        String strChaine1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < iNombre; i++) {
            int iNumChar = (int) (Math.random() * 36.0f);
            strChaine += strChaine1.substring(iNumChar, iNumChar + 1);
        }
        return strChaine;
    }

    /**
     * strEmplacement@param emplacement répertoire à effacer
     */
    static public void deleteDirectory(String strEmplacement) {
        File path = new File(strEmplacement);
        if (path.exists()) {
            File[] fileFichiers = path.listFiles();
            for (File fileFichier : fileFichiers) {
                if (fileFichier.isDirectory()) {
                    deleteDirectory(fileFichier.getAbsolutePath());
                }
                fileFichier.delete();
            }
        }
    }

    public Task analyseFichierPVU(final String strLigComplete) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                strPanoEntree = "";
                iNombrePanoramiques = 0;
                String strLigneComplete = strLigComplete.replace("[", "|");
                String strLignes[] = strLigneComplete.split("\\|", 500);
                String strLigne;
                String[] strElementsLigne;
                String[] strTypeElement;
                int iNbHS = 0;
                int iNbImg = 0;
                int iNbHsplan = 0;
                iNombrePlans = -1;
                for (int ikk = 1; ikk < strLignes.length; ikk++) {
                    strLigne = strLignes[ikk];
                    strElementsLigne = strLigne.split(";", 25);
                    strTypeElement = strElementsLigne[0].split(">", 2);
                    strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                    strElementsLigne[0] = strTypeElement[1];
                    if ("Panoramique".equals(strTypeElement[0])) {
                        for (int i = 0; i < strElementsLigne.length; i++) {
                            strElementsLigne[i] = strElementsLigne[i].replace("]", "").replace("\n", "");
                            String[] strValeur = strElementsLigne[i].split(":", 2);
                            //System.out.println(valeur[0] + "=" + valeur[1]);

                            switch (strValeur[0]) {
                                case "fichier":
                                    String nmFich = strValeur[1];
                                    int iNumPano = iNombrePanoramiques + 1;
                                    Platform.runLater(() -> {
                                        lblCharge.setText("pano " + iNumPano + "/" + iNombrePanoramiquesFichier + " : " + nmFich);
                                        lblNiveaux.setText("");
                                        pbarAvanceChargement.setProgress((double) (iNumPano - 1) / (double) iNombrePanoramiquesFichier);
                                    });
                                    File fileImgPano = new File(nmFich);
                                    File fileImageRepert = new File(strRepertTemp + File.separator + "panos");

                                    if (!fileImageRepert.exists()) {
                                        fileImageRepert.mkdirs();
                                    }
                                    strRepertPanos = fileImageRepert.getAbsolutePath();
                                    String strFichierPano = fileImgPano.getPath();
                                    String strExtension = strFichierPano.substring(strFichierPano.length() - 3, strFichierPano.length());
                                    Image imgPano = null;
                                    if ("tif".equals(strExtension)) {
                                        try {
                                            imgPano = ReadWriteImage.readTiff(strFichierPano);
                                        } catch (IOException ex) {
                                            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        imgPano = new Image("file:" + strFichierPano);
                                    }
                                    if (imgPano != null) {
                                        if (imgPano.getWidth() == imgPano.getHeight()) {
                                            ajoutePanoramiqueProjet(strValeur[1], Panoramique.CUBE, iNumPano, iNombrePanoramiquesFichier);

                                        } else {
                                            ajoutePanoramiqueProjet(strValeur[1], Panoramique.SPHERE, iNumPano, iNombrePanoramiquesFichier);
                                        }
                                    }
                                    break;

                                case "titre":
                                    //System.out.println("titre pano(" + iPanoActuel + ") : " + strValeur[1]);
                                    if (!strValeur[1].equals("'null'")) {
                                        panoramiquesProjet[iPanoActuel].setStrTitrePanoramique(strValeur[1]);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setStrTitrePanoramique("");
                                    }
                                    break;
                                case "type":
                                    panoramiquesProjet[iPanoActuel].setStrTypePanoramique(strValeur[1]);
                                    break;
                                case "afficheInfo":
                                    if (strValeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAfficheInfo(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAfficheInfo(false);
                                    }
                                    break;
                                case "afficheTitre":
                                    if (strValeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAfficheTitre(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAfficheTitre(false);
                                    }
                                    break;
                                case "nb":
                                    iNbHS = Integer.parseInt(strValeur[1]);
                                    break;
                                case "nbImg":
                                    iNbImg = Integer.parseInt(strValeur[1]);
                                    break;
                                case "regardX":
                                    panoramiquesProjet[iPanoActuel].setRegardX(Double.parseDouble(strValeur[1]));
                                    break;
                                case "regardY":
                                    panoramiquesProjet[iPanoActuel].setRegardY(Double.parseDouble(strValeur[1]));
                                    break;
                                case "zeroNord":
                                    panoramiquesProjet[iPanoActuel].setZeroNord(Double.parseDouble(strValeur[1]));
                                    break;
                                case "numeroPlan":
                                    panoramiquesProjet[iPanoActuel].setNumeroPlan(Integer.parseInt(strValeur[1].replace(" ", "")));
                                    break;
                                case "affichePlan":
                                    if (strValeur[1].equals("true")) {
                                        panoramiquesProjet[iPanoActuel].setAffichePlan(true);
                                    } else {
                                        panoramiquesProjet[iPanoActuel].setAffichePlan(false);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        for (int jj = 0; jj < iNbHS; jj++) {
                            ikk++;
                            strLigne = strLignes[ikk];
                            strElementsLigne = strLigne.split(";", 10);
                            strTypeElement = strElementsLigne[0].split(">", 2);
                            strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            strElementsLigne[0] = strTypeElement[1];
                            HotSpot HS = new HotSpot();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "");
                                String[] strValeur = strElementsLigne[i].split(":", 2);

                                switch (strValeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrFichierImage(null);
                                        } else {
                                            HS.setStrFichierImage(strValeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrInfo(null);
                                        } else {
                                            HS.setStrInfo(strValeur[1]);
                                        }
                                        break;
                                    case "xml":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrFichierXML(null);
                                        } else {
                                            HS.setStrFichierXML(strValeur[1]);
                                        }
                                        break;
                                    case "anime":
                                        if (strValeur[1].equals("true")) {
                                            HS.setAnime(true);
                                        } else {
                                            HS.setAnime(false);
                                        }
                                        break;
                                }
                            }
                            panoramiquesProjet[iPanoActuel].addHotspot(HS);
                        }

                        for (int jj = 0; jj < iNbImg; jj++) {
                            ikk++;
                            strLigne = strLignes[ikk];
                            strElementsLigne = strLigne.split(";", 10);
                            strTypeElement = strElementsLigne[0].split(">", 2);
                            strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            strElementsLigne[0] = strTypeElement[1];
                            HotspotImage HS = new HotspotImage();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "");
                                String[] strValeur = strElementsLigne[i].split(":", 2);
                                switch (strValeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrFichierImage(null);
                                        } else {
                                            HS.setStrFichierImage(strValeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrInfo(null);
                                        } else {
                                            HS.setStrInfo(strValeur[1]);
                                        }
                                        break;
                                    case "img":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrLienImg(null);
                                        } else {
                                            HS.setStrLienImg(strValeur[1]);
                                        }
                                        break;
                                    case "urlImg":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrUrlImage(null);
                                        } else {
                                            HS.setStrUrlImage(strValeur[1]);
                                        }
                                        String nmFich = strValeur[1];
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
                                        if (strValeur[1].equals("true")) {
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

                    if ("Plan".equals(strTypeElement[0])) {
                        for (int i = 0; i < strElementsLigne.length; i++) {
                            strElementsLigne[i] = strElementsLigne[i].replace("]", "");
                            String[] strValeur = strElementsLigne[i].split(":", 2);
                            //System.out.println(valeur[0] + "=" + valeur[1]);
                            switch (strValeur[0]) {
                                case "lienImage":
                                    iNombrePlans++;
                                    plans[iNombrePlans] = new Plan();
                                    plans[iNombrePlans].setStrLienPlan(strValeur[1]);
                                    File fileRepertoirePlan = new File(strRepertTemp + File.separator + "images");
                                    if (!fileRepertoirePlan.exists()) {
                                        fileRepertoirePlan.mkdirs();
                                    }
                                    try {
                                        copieFichierRepertoire(plans[iNombrePlans].getStrLienPlan(), fileRepertoirePlan.getAbsolutePath());
                                    } catch (IOException ex) {
                                        Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "image":
                                    plans[iNombrePlans].setStrImagePlan(strValeur[1]);
                                    break;
                                case "titre":
                                    if (!strValeur[1].equals("'null'")) {
                                        plans[iNombrePlans].setTitrePlan(strValeur[1]);
                                    } else {
                                        plans[iNombrePlans].setTitrePlan("");
                                    }
                                    break;
                                case "nb":
                                    iNbHsplan = Integer.parseInt(strValeur[1]);
                                    break;
                                case "position":
                                    plans[iNombrePlans].setStrPosition(strValeur[1]);
                                    break;
                                case "positionX":
                                    plans[iNombrePlans].setPositionX(Double.parseDouble(strValeur[1]));
                                    break;
                                case "positionY":
                                    plans[iNombrePlans].setPositionY(Double.parseDouble(strValeur[1]));
                                    break;
                                case "directionNord":
                                    plans[iNombrePlans].setDirectionNord(Double.parseDouble(strValeur[1]));
                                    break;
                                default:
                                    break;
                            }
                        }
                        for (int jj = 0; jj < iNbHsplan; jj++) {
                            ikk++;
                            strLigne = strLignes[ikk];
                            strElementsLigne = strLigne.split(";", 15);
                            strTypeElement = strElementsLigne[0].split(">", 2);
                            strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            strElementsLigne[0] = strTypeElement[1];
                            HotSpot HS = new HotSpot();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "");
                                String[] strValeur = strElementsLigne[i].split(":", 2);
                                //System.out.println(valeur[0] + "=" + valeur[1]);
                                switch (strValeur[0]) {
                                    case "longitude":
                                        HS.setLongitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "latitude":
                                        HS.setLatitude(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "image":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrFichierImage(null);
                                        } else {
                                            HS.setStrFichierImage(strValeur[1]);
                                        }
                                        break;
                                    case "info":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrInfo(null);
                                        } else {
                                            HS.setStrInfo(strValeur[1]);
                                        }
                                        break;
                                    case "xml":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrFichierXML(null);
                                        } else {
                                            HS.setStrFichierXML(strValeur[1]);
                                        }
                                        break;
                                    case "numeroPano":
                                        HS.setNumeroPano(Integer.parseInt(strValeur[1].replace("\n", "").replace(" ", "")));
                                        break;
                                    case "anime":
                                        if (strValeur[1].equals("true")) {
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
                    if ("Interface".equals(strTypeElement[0])) {
                        String[] strElts = strElementsLigne[0].replace("]", "").split("\n");
                        List<String> strListTemplate = new ArrayList<>();
                        for (String strTexte : strElts) {
                            if (!strTexte.equals("")) {
                                strListTemplate.add(strTexte);
                            }
                        }
                        gestionnaireInterface.setTemplate(strListTemplate);
                    }
                    if ("PanoEntree".equals(strTypeElement[0])) {
                        strPanoEntree = strElementsLigne[0].split("]")[0];
                    }
                    if ("titreVisite".equals(strTypeElement[0])) {
                        strTitreVisite = strElementsLigne[0].split("]")[0];
                    }

                }
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                if (!strPanoEntree.equals("")) {
                    cbListeChoixPanoramiqueEntree.setValue(strPanoEntree + ".jpg");
                }
                for (int i = 0; i < iNombrePanoramiques; i++) {
                    //System.out.println("Titre(" + i + ") : " + panoramiquesProjet[i].getStrTitrePanoramique());
                    String strFichierPano = panoramiquesProjet[i].getStrNomFichier();
                    String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                    cbListeChoixPanoramique.getItems().add(strNomPano);
                    cbListeChoixPanoramiqueEntree.getItems().add(strNomPano);
                }
                iPanoActuel = 0;
                vbChoixPanoramique.setVisible(true);
                iNombrePlans++;
                if (iNombrePlans != 0) {
                    int iNbPlans1 = iNombrePlans;
                    for (int i = 0; i < iNbPlans1; i++) {
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
        File fileFichierConfig = new File(fileRepertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
        if (fileFichierConfig.exists()) {
            try {
                String strTexte;
                try (BufferedReader FichierConfig = new BufferedReader(new InputStreamReader(
                        new FileInputStream(fileFichierConfig), "UTF-8"))) {
                    String strLangue = "fr";
                    String strPays = "FR";
                    String strRepert = strRepertAppli;
                    while ((strTexte = FichierConfig.readLine()) != null) {
                        String tpe = strTexte.split("=")[0];
                        String valeur = strTexte.split("=")[1];
                        switch (tpe) {
                            case "langue":
                                strLangue = valeur;
                                break;
                            case "pays":
                                strPays = valeur;
                                break;
                            case "repert":
                                strRepert = valeur;
                                break;
                            case "style":
                                strStyleCSS = valeur;
                                break;

                        }

                    }
                    locale = new Locale(strLangue, strPays);
                    File fileRepert1 = new File(strRepert);
                    if (fileRepert1.exists()) {
                        strRepertoireProjet = strRepert;
                        strCurrentDir = strRepert;
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
            fileFichierConfig.createNewFile();
            locale = new Locale("fr", "FR");
            setUserAgentStylesheet("file:css/clair.css");
            strRepertoireProjet = strRepertAppli;
            String strContenuFichier = "langue=" + locale.toString().split("_")[0] + "\n";
            strContenuFichier += "pays=" + locale.toString().split("_")[1] + "\n";
            strContenuFichier += "repert=" + strRepertoireProjet + "\n";
            strContenuFichier += "style=clair\n";
            fileFichierConfig.setWritable(true);
            OutputStreamWriter oswFichierConfig = null;
            try {
                oswFichierConfig = new OutputStreamWriter(new FileOutputStream(fileFichierConfig), "UTF-8");
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedWriter bwFichierConfig = new BufferedWriter(oswFichierConfig);
            try {
                bwFichierConfig.write(strContenuFichier);
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bwFichierConfig.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     *
     * @param strEmplacement répertoire origine
     * @param strRepertoire répertoire cible
     */
    static public void copieDirectory(String strEmplacement, String strRepertoire) {
        File fileRepert2 = new File(strRepertoire);
        if (!fileRepert2.exists()) {
            fileRepert2.mkdirs();
        }
        File filePath = new File(strEmplacement);
        if (filePath.exists()) {
            File[] fileFichiers = filePath.listFiles();
            for (File fileFichier : fileFichiers) {
                try {
                    if (fileFichier.isDirectory()) {
                        String strRep1 = fileFichier.getAbsolutePath().substring(fileFichier.getAbsolutePath().lastIndexOf(File.separator) + 1);
                        strRep1 = strRepertoire + File.separator + strRep1;
                        copieDirectory(fileFichier.getAbsolutePath(), strRep1);
                    } else {
                        copieFichierRepertoire(fileFichier.getAbsolutePath(), strRepertoire);

                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @param strFichier
     * @param strRepertoire
     * @throws FileNotFoundException
     * @throws IOException
     */
    static public void copieFichierRepertoire(String strFichier, String strRepertoire) throws FileNotFoundException, IOException {
        File fileFrom = new File(strFichier);
        File fileTo = new File(strRepertoire + File.separator + strFichier.substring(strFichier.lastIndexOf(File.separator) + 1));
        Files.copy(fileFrom.toPath(), fileTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     *
     */
    private void retireAffichageHotSpots() {
        Pane paneLabels = (Pane) vbOutils.lookup("#labels");
        vbOutils.getChildren().remove(paneLabels);
    }

    /**
     *
     */
    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < iNumPoints; i++) {
            Node nodePoint = (Node) panePanoramique.lookup("#point" + i);
            panePanoramique.getChildren().remove(nodePoint);
        }
        for (int i = 0; i < iNumImages; i++) {
            Node nodePoint = (Node) panePanoramique.lookup("#img" + i);
            panePanoramique.getChildren().remove(nodePoint);
        }
    }

    /**
     *
     * @param iPanCourant
     * @return
     */
    private String strListePano(int iPanCourant) {
        String strListe = "";
        if (iNombrePanoramiques == 0) {
            return null;
        } else {
            for (int i = 0; i < iNombrePanoramiques; i++) {
                if (i != iPanCourant) {
                    String strFichierPano = panoramiquesProjet[i].getStrNomFichier();
                    String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                    String[] strNPano = strNomPano.split("\\.");
                    strListe += strNPano[0];
                    if (i < iNombrePanoramiques - 1) {
                        strListe += ";";
                    }
                }
            }
            return strListe;
        }
    }

    private AnchorPane apAfficherListePanosVignettes(int iNumHS) {

        AnchorPane aplistePano = new AnchorPane();
        aplistePano.setOpacity(1);
        Pane paneFond = new Pane();
        paneFond.setStyle("-fx-background-color : #bbb;");
        paneFond.setPrefWidth(540);
        paneFond.setPrefHeight(((iNombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        paneFond.setMinWidth(540);
        paneFond.setMinHeight(70);
        aplistePano.getChildren().add(paneFond);
        aplistePano.setStyle("-fx-backgroung-color : #bbb;");
        int ij = 0;
        ImageView[] ivPano;
        ivPano = new ImageView[iNombrePanoramiques];
        double xPos;
        double yPos;
        int iRow = 0;
        for (int i = 0; i < iNombrePanoramiques; i++) {
            int iNumeroPano = i;
            ivPano[ij] = new ImageView(panoramiquesProjet[i].getImgVignettePanoramique());
            ivPano[ij].setFitWidth(120);
            ivPano[ij].setFitHeight(60);
            ivPano[ij].setSmooth(true);
            String strNomPano = panoramiquesProjet[i].getStrNomFichier();
            int iCol = ij % 4;
            iRow = ij / 4;
            xPos = iCol * 130 + 25;
            yPos = iRow * 65 + 5;
            ivPano[ij].setLayoutX(xPos);
            ivPano[ij].setLayoutY(yPos);
            ivPano[ij].setCursor(Cursor.HAND);
            ivPano[ij].setStyle("-fx-background-color : #ccc;");
            Tooltip tltpPano = new Tooltip(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
            tltpPano.setStyle(strTooltipStyle);
            Tooltip.install(ivPano[ij], tltpPano);
            ivPano[ij].setOnMouseClicked((mouseEvent) -> {
                panePanoramique.setCursor(Cursor.CROSSHAIR);
                panePanoramique.setOnMouseClicked(
                        (me1) -> {
                            gereSourisPanoramique(me1);
                        }
                );
                strPanoListeVignette = strNomPano;
                if (panoramiquesProjet[iNumeroPano].getStrTitrePanoramique() != null) {
                    String strTexteHS = panoramiquesProjet[iNumeroPano].getStrTitrePanoramique();
                    TextField tfTxtHS = (TextField) vbOutils.lookup("#txtHS" + iNumHS);
                    tfTxtHS.setText(strTexteHS);
                }
                panoramiquesProjet[iPanoActuel].getHotspot(iNumHS).setNumeroPano(iNumeroPano);
                ComboBox cbPanos = (ComboBox) vbOutils.lookup("#cbpano" + iNumHS);
                cbPanos.setValue(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
                aplistePano.setVisible(false);
                mouseEvent.consume();
            });
            aplistePano.getChildren().add(ivPano[ij]);
            ij++;

        }
        int iTaille = (iRow + 1) * 65 + 5;
        aplistePano.setPrefWidth(540);
        aplistePano.setPrefHeight(iTaille);
        aplistePano.setMinWidth(540);
        aplistePano.setMinHeight(iTaille);
        ImageView ivClose = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
        ivClose.setLayoutX(2);
        ivClose.setLayoutY(5);
        ivClose.setCursor(Cursor.HAND);
        aplistePano.getChildren().add(ivClose);
        ivClose.setOnMouseClicked((mouseEvent) -> {
            panePanoramique.setCursor(Cursor.CROSSHAIR);
            panePanoramique.setOnMouseClicked(
                    (mouseEvent1) -> {
                        gereSourisPanoramique(mouseEvent1);
                    }
            );

            strPanoListeVignette = "";
            aplistePano.setVisible(false);
            mouseEvent.consume();
        });
        aplistePano.setTranslateZ(2);
        return aplistePano;
    }

    /**
     *
     */
    private void valideHS() {

        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspots(); i++) {
            ComboBox cbPanos = (ComboBox) vbOutils.lookup("#cbpano" + i);
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHS" + i);
            if (tfTexteHS != null) {
                panoramiquesProjet[iPanoActuel].getHotspot(i).setStrInfo(tfTexteHS.getText());
            }
            if (cbPanos != null) {
                panoramiquesProjet[iPanoActuel].getHotspot(i).setStrFichierXML(cbPanos.getValue() + ".xml");
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
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHSImage" + i);
            if (tfTexteHS != null) {
                panoramiquesProjet[iPanoActuel].getHotspotImage(i).setStrInfo(tfTexteHS.getText());
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
     * @param strLstPano
     * @param iNumPano
     * @return
     */
    public Pane affichageHS(String strLstPano, int iNumPano) {

        Pane paneHotSpots = new Pane();
        paneHotSpots.setTranslateY(10);
        paneHotSpots.setTranslateX(30);
        VBox vbHotspots = new VBox(5);
        paneHotSpots.getChildren().add(vbHotspots);
        Label lblPoint;
        Label lblSep = new Label(" ");
        Label lblSep1 = new Label(" ");
        int io;
        for (io = 0; io < panoramiquesProjet[iNumPano].getNombreHotspots(); io++) {
            VBox vbPanneauHS = new VBox();
            double deplacement = 20;
            vbPanneauHS.setLayoutX(deplacement);
            Pane paneHS = new Pane(vbPanneauHS);
            paneHS.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3;");
            paneHotSpots.setId("HS" + io);
            lblPoint = new Label("Point n°" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setTranslateX(-deplacement);
            lblPoint.setStyle("-fx-background-color : #333;");
            lblPoint.setTextFill(Color.WHITE);
            Separator sepHotspots = new Separator(Orientation.HORIZONTAL);
            sepHotspots.setTranslateX(-deplacement);
            sepHotspots.setPrefWidth(300);

            paneHS.setPrefWidth(300);
            paneHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sepHotspots);
            if (strLstPano != null) {
                Label lblLien = new Label("Panoramique de destination");
                ComboBox cbDestPano = new ComboBox();
                String[] strListe = strLstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(strListe));
                cbDestPano.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String t, String t1) {
                        valideHS();
                    }
                });
                cbDestPano.setTranslateX(60);
                cbDestPano.setId("cbpano" + io);

                String strF1XML = panoramiquesProjet[iNumPano].getHotspot(io).getStrFichierXML();
                if (strF1XML != null) {
                    cbDestPano.setValue(strF1XML.split("\\.")[0]);
                }
                int iNum = cbDestPano.getSelectionModel().getSelectedIndex();
                vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            TextField tfTexteHS = new TextField();
            if (panoramiquesProjet[iNumPano].getHotspot(io).getStrInfo() != null) {
                tfTexteHS.setText(panoramiquesProjet[iNumPano].getHotspot(io).getStrInfo());
            }
            tfTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            tfTexteHS.setId("txtHS" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            tfTexteHS.setTranslateX(60);
            CheckBox cbAnime = new CheckBox("HostSpot Animé");
            cbAnime.setId("anime" + io);
            cbAnime.selectedProperty().addListener((final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) -> {
                valideHS();
            });
            if (panoramiquesProjet[iNumPano].getHotspot(io).isAnime()) {
                cbAnime.setSelected(true);
            }
            cbAnime.setPadding(new Insets(5));
            cbAnime.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, tfTexteHS, cbAnime, lblSep1);
            vbHotspots.getChildren().addAll(paneHS, lblSep);
        }
        int iNbHS = io;
        for (io = 0; io < panoramiquesProjet[iNumPano].getNombreHotspotImage(); io++) {
            VBox vbPanneauHS = new VBox();
            Pane paneHS = new Pane(vbPanneauHS);
            paneHS.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3;");
            paneHotSpots.setId("HSImg" + io);
            lblPoint = new Label("Image n°" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setStyle("-fx-background-color : #666;");
            lblPoint.setTextFill(Color.WHITE);
            Separator sepHS = new Separator(Orientation.HORIZONTAL);
            sepHS.setPrefWidth(300);

            paneHS.setPrefWidth(300);
            paneHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sepHS);
            Label lblLien = new Label("Image choisie :");
            String strF1XML = panoramiquesProjet[iNumPano].getHotspotImage(io).getStrLienImg();
            ImageView ivChoisie = new ImageView(new Image("file:" + strRepertTemp + File.separator + "images" + File.separator + strF1XML, 100, -1, true, true));
            ivChoisie.setTranslateX(100);
            vbPanneauHS.getChildren().addAll(lblLien, ivChoisie, lblSep);
            Label lblTexteHS = new Label("Texte du Hotspot");
            TextField tfTexteHS = new TextField();
            if (panoramiquesProjet[iNumPano].getHotspotImage(io).getStrInfo() != null) {
                tfTexteHS.setText(panoramiquesProjet[iNumPano].getHotspotImage(io).getStrInfo());
            }
            tfTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            tfTexteHS.setId("txtHSImage" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            tfTexteHS.setTranslateX(60);
            CheckBox cbAnime = new CheckBox("HostSpot Animé");
            cbAnime.setId("animeImage" + io);
            cbAnime.selectedProperty().addListener((final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) -> {
                valideHS();
            });
            if (panoramiquesProjet[iNumPano].getHotspotImage(io).isAnime()) {
                cbAnime.setSelected(true);
            }
            cbAnime.setPadding(new Insets(5));
            cbAnime.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, tfTexteHS, cbAnime, lblSep1);
            vbHotspots.getChildren().addAll(paneHS, lblSep);
        }
        valideHS();
        iNbHS += io;
//        if (nbHS == 0) {
//        } else {
//            btnValider.setVisible(true);
//        }
        return paneHotSpots;
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane paneLabels = affichageHS(strListePano(iPanoActuel), iPanoActuel);
        paneLabels.setId("labels");
        vbOutils.getChildren().add(paneLabels);
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

        Circle circPoint = new Circle(30, 20, 5);
        circPoint.setFill(Color.YELLOW);
        circPoint.setStroke(Color.RED);
        circPoint.setCursor(Cursor.DEFAULT);
        Circle circPoint2 = new Circle(30, 60, 5);
        circPoint2.setFill(Color.BLUE);
        circPoint2.setStroke(Color.YELLOW);
        circPoint2.setCursor(Cursor.DEFAULT);
        Circle circPoint3 = new Circle(30, 100, 5);
        circPoint3.setFill(Color.GREEN);
        circPoint3.setStroke(Color.YELLOW);
        circPoint3.setCursor(Cursor.DEFAULT);
        Polygon polygonCroix = new Polygon();
        polygonCroix.getPoints().addAll(new Double[]{
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
        polygonCroix.setStrokeLineJoin(StrokeLineJoin.MITER);
        polygonCroix.setFill(Color.BLUEVIOLET);
        polygonCroix.setStroke(Color.YELLOW);
        polygonCroix.setId("PoV");
        polygonCroix.setLayoutX(500);
        polygonCroix.setLayoutY(20);
        Label lblHS = new Label(rbLocalisation.getString("main.legendeHS"));
        Label lblHSImage = new Label(rbLocalisation.getString("main.legendeHSImage"));
        //Label lblHSHTML = new Label(rb.getString("main.legendeHSHTML"));
        Label lblPoV = new Label(rbLocalisation.getString("main.legendePoV"));
        Label lblNord = new Label(rbLocalisation.getString("main.legendeNord"));
        Line lineNord = new Line(500, 45, 500, 65);
        lineNord.setStroke(Color.RED);
        lineNord.setStrokeWidth(3);
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
        apLegende.getChildren().addAll(lblHS, circPoint, lblHSImage, circPoint2, lblPoV, polygonCroix, lblNord, lineNord);
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
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.YELLOW);
        circPoint.setStroke(Color.RED);
        circPoint.setId("point" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);

        Tooltip tltpHotSpot = new Tooltip("point n° " + (i + 1));
        tltpHotSpot.setStyle(strTooltipStyle);
        Tooltip.install(circPoint, tltpHotSpot);
        circPoint.setOnDragDetected((mouseEvent1) -> {
            circPoint.setFill(Color.RED);
            circPoint.setStroke(Color.YELLOW);
            bDragDrop = true;
            mouseEvent1.consume();

        });
        circPoint.setOnMouseDragged((mouseEvent1) -> {
            double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
            double YY = mouseEvent1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            circPoint.setCenterY(YY);
            mouseEvent1.consume();

        });
        circPoint.setOnMouseReleased((mouseEvent1) -> {
            String strChPoint = circPoint.getId();
            strChPoint = strChPoint.substring(5, strChPoint.length());
            int iNumeroPoint = Integer.parseInt(strChPoint);
            double X1 = mouseEvent1.getSceneX();
            double Y1 = mouseEvent1.getSceneY();
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
            String strLong, strLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[iPanoActuel].getHotspot(iNumeroPoint).setLatitude(lat);
            panoramiquesProjet[iPanoActuel].getHotspot(iNumeroPoint).setLongitude(longit);
            circPoint.setFill(Color.YELLOW);
            circPoint.setStroke(Color.RED);
            mouseEvent1.consume();

        });

        circPoint.setOnMouseClicked((mouseEvent1) -> {
            double mouseX = mouseEvent1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > ivImagePanoramique.getFitWidth()) {
                mouseX = ivImagePanoramique.getFitWidth();
            }

            double mouseY = mouseEvent1.getSceneY() - panePanoramique.getLayoutY() - 115;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > ivImagePanoramique.getFitHeight()) {
                mouseY = ivImagePanoramique.getFitHeight();
            }

            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(5, strPoint.length());
            int numeroPoint = Integer.parseInt(strPoint);
            Node nodePoint;
            nodePoint = (Node) panePanoramique.lookup("#point" + strPoint);

            if (mouseEvent1.isControlDown()) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePoint);

                for (int io = numeroPoint + 1; io < iNumPoints; io++) {
                    nodePoint = (Node) panePanoramique.lookup("#point" + Integer.toString(io));
                    nodePoint.setId("point" + Integer.toString(io - 1));
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
                mouseEvent1.consume();
                valideHS();
            } else {
                if (!bDragDrop) {
                    if (iNombrePanoramiques > 1) {
                        AnchorPane apListePanoVig = apAfficherListePanosVignettes(numeroPoint);
                        int iLargeurVignettes = 4;
                        if (iNombrePanoramiques < 4) {
                            iLargeurVignettes = iNombrePanoramiques;
                        }
                        if (mouseX + iLargeurVignettes * 130 > panePanoramique.getWidth()) {
                            apListePanoVig.setLayoutX(panePanoramique.getWidth() - iLargeurVignettes * 130);
                        } else {
                            apListePanoVig.setLayoutX(mouseX);
                        }
                        apListePanoVig.setLayoutY(mouseY);
                        panePanoramique.getChildren().add(apListePanoVig);
                    }
                } else {
                    bDragDrop = false;
                }
                valideHS();
                mouseEvent1.consume();

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
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.BLUE);
        circPoint.setStroke(Color.YELLOW);
        circPoint.setId("img" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);
        Tooltip tltpHSImage = new Tooltip("image n° " + (i + 1));
        tltpHSImage.setStyle(strTooltipStyle);
        Tooltip.install(circPoint, tltpHSImage);
        circPoint.setOnDragDetected((mouseEvent1) -> {
            circPoint.setFill(Color.YELLOW);
            circPoint.setStroke(Color.BLUE);
            bDragDrop = true;
            mouseEvent1.consume();

        });
        circPoint.setOnMouseDragged((mouseEvent1) -> {
            double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
            double YY = mouseEvent1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            circPoint.setCenterY(YY);

            mouseEvent1.consume();

        });
        circPoint.setOnMouseReleased((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(3, strPoint.length());
            int iNumeroPoint = Integer.parseInt(strPoint);
            double X1 = mouseEvent1.getSceneX();
            double Y1 = mouseEvent1.getSceneY();
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
            String strLong, strLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLatitude(lat);
            panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLongitude(longit);
            circPoint.setFill(Color.BLUE);
            circPoint.setStroke(Color.YELLOW);
            mouseEvent1.consume();

        });

        circPoint.setOnMouseClicked((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(3, strPoint.length());
            int numeroPoint = Integer.parseInt(strPoint);
            Node nodePointImage;
            nodePointImage = (Node) panePanoramique.lookup("#img" + strPoint);

            if (mouseEvent1.isControlDown()) {
                valideHS();
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePointImage);

                for (int io = numeroPoint + 1; io < iNumImages; io++) {
                    nodePointImage = (Node) panePanoramique.lookup("#img" + Integer.toString(io));
                    nodePointImage.setId("img" + Integer.toString(io - 1));
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
                mouseEvent1.consume();
            } else {
                mouseEvent1.consume();
            }

        });
    }    
/**
 * 
 * @param i
 * @param longitude
 * @param latitude 
 */   
   private void afficheHSHTML(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.DARKGREEN);
        circPoint.setStroke(Color.YELLOWGREEN);
        circPoint.setId("html" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);
        Tooltip tltpHSImage = new Tooltip("HTML n° " + (i + 1));
        tltpHSImage.setStyle(strTooltipStyle);
        Tooltip.install(circPoint, tltpHSImage);
        circPoint.setOnDragDetected((mouseEvent1) -> {
            circPoint.setFill(Color.YELLOWGREEN);
            circPoint.setStroke(Color.DARKGREEN);
            bDragDrop = true;
            mouseEvent1.consume();

        });
        circPoint.setOnMouseDragged((mouseEvent1) -> {
            double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
            double YY = mouseEvent1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            circPoint.setCenterY(YY);

            mouseEvent1.consume();

        });
        circPoint.setOnMouseReleased((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(4, strPoint.length());
            int iNumeroPoint = Integer.parseInt(strPoint);
            double X1 = mouseEvent1.getSceneX();
            double Y1 = mouseEvent1.getSceneY();
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
            String strLong, strLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLatitude(lat);
            panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLongitude(longit);
            circPoint.setFill(Color.DARKGREEN);
            circPoint.setStroke(Color.YELLOWGREEN);
            mouseEvent1.consume();

        });

        circPoint.setOnMouseClicked((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(4, strPoint.length());
            int numeroPoint = Integer.parseInt(strPoint);
            Node nodePointImage;
            nodePointImage = (Node) panePanoramique.lookup("#html" + strPoint);

            if (mouseEvent1.isControlDown()) {
                valideHS();
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePointImage);

                for (int io = numeroPoint + 1; io < iNumImages; io++) {
                    nodePointImage = (Node) panePanoramique.lookup("#html" + Integer.toString(io));
                    nodePointImage.setId("img" + Integer.toString(io - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                iNumHTML--;
                panoramiquesProjet[iPanoActuel].removeHotspotHTML(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                mouseEvent1.consume();
            } else {
                mouseEvent1.consume();
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
    private void ajouteAffichagePointsHotspotsHTML() {
        for (int i = 0; i < panoramiquesProjet[iPanoActuel].getNombreHotspotHTML(); i++) {
            double longitude = panoramiquesProjet[iPanoActuel].getHotspotHTML(i).getLongitude();
            double latitude = panoramiquesProjet[iPanoActuel].getHotspotHTML(i).getLatitude();
            afficheHSHTML(i, longitude, latitude);
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
            panoramiquesProjet[iPanoActuel].setRegardX(regardX);
            panoramiquesProjet[iPanoActuel].setRegardY(regardY);
            navigateurPanoramique.setLongitude(regardX - 180);
            navigateurPanoramique.setLatitude(regardY);
            navigateurPanoramique.affiche();

            affichePoV(regardX, regardY);
        }
    }

    private void panoChoixNord(double X) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            double mouseX = X;
            double largeur = ivImagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            panoramiquesProjet[iPanoActuel].setZeroNord(regardX);
            navigateurPanoramique.setPositNord(regardX - 180);
            navigateurPanoramique.affiche();
            afficheNord(regardX);
        }
    }

    private void afficheNord(double longitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        Node nodeAncienNord = (Node) panePanoramique.lookup("#Nord");
        if (nodeAncienNord != null) {
            panePanoramique.getChildren().remove(nodeAncienNord);
        }
        Line lineNord = new Line(0, 0, 0, ivImagePanoramique.getFitHeight());
        lineNord.setCursor(Cursor.DEFAULT);

        lineNord.setLayoutX(X);
        lineNord.setStroke(Color.RED);
        lineNord.setStrokeWidth(4);
        lineNord.setId("Nord");
        lineNord.setOnDragDetected((mouseEvent1) -> {
            lineNord.setStroke(Color.BLUEVIOLET);
            bDragDrop = true;
            mouseEvent1.consume();
        });
        lineNord.setOnMouseDragged((me1) -> {

            double XX = me1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            lineNord.setLayoutX(XX + ivImagePanoramique.getLayoutX());
            me1.consume();

        });
        lineNord.setOnMouseReleased((me1) -> {
            double X1 = me1.getSceneX();
            double mouseX1 = X1 - ivImagePanoramique.getLayoutX();
            if (mouseX1 < 0) {
                mouseX1 = 0;
            }
            if (mouseX1 > ivImagePanoramique.getFitWidth()) {
                mouseX1 = ivImagePanoramique.getFitWidth();
            }
            double regardX = 360.0f * mouseX1 / largeur - 180;
            navigateurPanoramique.setPositNord(regardX - 180);
            navigateurPanoramique.affiche();
            panoramiquesProjet[iPanoActuel].setZeroNord(regardX);
            lineNord.setStroke(Color.RED);
            me1.consume();

        });

        panePanoramique.getChildren().add(lineNord);
    }

    private void affichePoV(double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Node nodeAncienPoV = (Node) panePanoramique.lookup("#PoV");
        if (nodeAncienPoV != null) {
            panePanoramique.getChildren().remove(nodeAncienPoV);
        }
        Polygon plgPoV = new Polygon();
        plgPoV.getPoints().addAll(new Double[]{
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
        plgPoV.setStrokeLineJoin(StrokeLineJoin.MITER);
        plgPoV.setFill(Color.BLUEVIOLET);
        plgPoV.setStroke(Color.YELLOW);
        plgPoV.setId("PoV");
        plgPoV.setLayoutX(X);
        plgPoV.setLayoutY(Y);
        plgPoV.setCursor(Cursor.DEFAULT);
        plgPoV.setOnDragDetected((mouseEvent1) -> {
            plgPoV.setFill(Color.YELLOW);
            plgPoV.setStroke(Color.BLUEVIOLET);
            bDragDrop = true;
            mouseEvent1.consume();

        });
        plgPoV.setOnMouseDragged((mouseEvent1) -> {

            double XX = mouseEvent1.getSceneX() - ivImagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > ivImagePanoramique.getFitWidth()) {
                XX = ivImagePanoramique.getFitWidth();
            }
            plgPoV.setLayoutX(XX + ivImagePanoramique.getLayoutX());
            double YY = mouseEvent1.getSceneY() - panePanoramique.getLayoutY() - 109;
            if (YY < 0) {
                YY = 0;
            }
            if (YY > ivImagePanoramique.getFitHeight()) {
                YY = ivImagePanoramique.getFitHeight();
            }
            plgPoV.setLayoutY(YY);

            mouseEvent1.consume();

        });
        plgPoV.setOnMouseReleased((mouseEvent1) -> {
            double X1 = mouseEvent1.getSceneX();
            double Y1 = mouseEvent1.getSceneY();
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
            navigateurPanoramique.setLongitude(regardX - 180);
            navigateurPanoramique.setLatitude(regardY);
            navigateurPanoramique.affiche();
            panoramiquesProjet[iPanoActuel].setRegardX(regardX);
            panoramiquesProjet[iPanoActuel].setRegardY(regardY);
            plgPoV.setFill(Color.BLUEVIOLET);
            plgPoV.setStroke(Color.YELLOW);
            mouseEvent1.consume();

        });

        panePanoramique.getChildren().add(plgPoV);
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
                String strLong, strLat;
                longitude = 360.0f * mouseX / largeur - 180;
                latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                Circle circPoint = new Circle(mouseX + ivImagePanoramique.getLayoutX(), mouseY, 5);
                circPoint.setFill(Color.YELLOW);
                circPoint.setStroke(Color.RED);
                circPoint.setId("point" + iNumPoints);
                circPoint.setCursor(Cursor.DEFAULT);
                panePanoramique.getChildren().add(circPoint);
                Tooltip tltpPoint = new Tooltip("point n°" + (iNumPoints + 1));
                tltpPoint.setStyle(strTooltipStyle);
                Tooltip.install(circPoint, tltpPoint);
                HotSpot HS = new HotSpot();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                panoramiquesProjet[iPanoActuel].addHotspot(HS);
                retireAffichageHotSpots();
                Pane paneAfficheHS1 = affichageHS(strListePano(iPanoActuel), iPanoActuel);
                paneAfficheHS1.setId("labels");
                vbOutils.getChildren().add(paneAfficheHS1);

                iNumPoints++;
                if (iNombrePanoramiques > 1) {

                    AnchorPane apListePanoVig = apAfficherListePanosVignettes(panoramiquesProjet[iPanoActuel].getNombreHotspots() - 1);
                    int iLargeurVignettes = 4;
                    if (iNombrePanoramiques < 4) {
                        iLargeurVignettes = iNombrePanoramiques;
                    }
                    if (mouseX + iLargeurVignettes * 130 > panePanoramique.getWidth()) {
                        apListePanoVig.setLayoutX(panePanoramique.getWidth() - iLargeurVignettes * 130);
                    } else {
                        apListePanoVig.setLayoutX(mouseX);
                    }
                    apListePanoVig.setLayoutY(mouseY);
                    panePanoramique.getChildren().add(apListePanoVig);
                    valideHS();
                }
                circPoint.setOnDragDetected((mouseEvent1) -> {
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(5, strPoint.length());
                    int numeroPoint = Integer.parseInt(strPoint);
                    Node nodePoint;
                    nodePoint = (Node) panePanoramique.lookup("#point" + strPoint);
                    circPoint.setFill(Color.RED);
                    circPoint.setStroke(Color.YELLOW);
                    bDragDrop = true;
                    mouseEvent1.consume();

                });
                circPoint.setOnMouseDragged((mouseEvent1) -> {
                    double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
                    if (XX < 0) {
                        XX = 0;
                    }
                    if (XX > ivImagePanoramique.getFitWidth()) {
                        XX = ivImagePanoramique.getFitWidth();
                    }
                    circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
                    double YY = mouseEvent1.getY();
                    if (YY < 0) {
                        YY = 0;
                    }
                    if (YY > ivImagePanoramique.getFitHeight()) {
                        YY = ivImagePanoramique.getFitHeight();
                    }
                    circPoint.setCenterY(YY);

                    mouseEvent1.consume();

                });
                circPoint.setOnMouseReleased((mouseEvent1) -> {
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(5, strPoint.length());
                    int iNumeroPoint = Integer.parseInt(strPoint);
                    double X1 = mouseEvent1.getSceneX();
                    double Y1 = mouseEvent1.getSceneY();
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
                    panoramiquesProjet[iPanoActuel].getHotspot(iNumeroPoint).setLatitude(lat);
                    panoramiquesProjet[iPanoActuel].getHotspot(iNumeroPoint).setLongitude(longit);
                    circPoint.setFill(Color.YELLOW);
                    circPoint.setStroke(Color.RED);
                    mouseEvent1.consume();

                });

                circPoint.setOnMouseClicked((mouseEvent1) -> {
                    if (mouseEvent1.isControlDown()) {
                        bDejaSauve = false;
                        stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                        String strPoint = circPoint.getId();
                        strPoint = strPoint.substring(5, strPoint.length());
                        int iNumeroPoint = Integer.parseInt(strPoint);
                        Node nodePoint;
                        nodePoint = (Node) panePanoramique.lookup("#point" + strPoint);
                        panePanoramique.getChildren().remove(nodePoint);

                        for (int io = iNumeroPoint + 1; io < iNumPoints; io++) {
                            nodePoint = (Node) panePanoramique.lookup("#point" + Integer.toString(io));
                            nodePoint.setId("point" + Integer.toString(io - 1));
                        }
                        /**
                         * on retire les anciennes indication de HS
                         */
                        retireAffichageHotSpots();
                        iNumPoints--;
                        panoramiquesProjet[iPanoActuel].removeHotspot(iNumeroPoint);
                        /**
                         * On les crée les nouvelles
                         */
                        ajouteAffichageHotspots();
                        valideHS();
                        mouseEvent1.consume();
                    } else {
                        if (!bDragDrop) {
                            String strPoint = circPoint.getId();
                            strPoint = strPoint.substring(5, strPoint.length());
                            int iNumeroPoint = Integer.parseInt(strPoint);
                            if (iNombrePanoramiques > 1) {
                                AnchorPane listePanoVig = apAfficherListePanosVignettes(iNumeroPoint);
                                int iLargeurVignettes = 4;
                                if (iNombrePanoramiques < 4) {
                                    iLargeurVignettes = iNombrePanoramiques;
                                }
                                if (mouseX + iLargeurVignettes * 130 > panePanoramique.getWidth()) {
                                    listePanoVig.setLayoutX(panePanoramique.getWidth() - iLargeurVignettes * 130);
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
                        mouseEvent1.consume();

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
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            double longitude, latitude;
            double largeur = ivImagePanoramique.getFitWidth();
            longitude = 360.0f * mouseX / largeur - 180;
            latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            Circle circPoint = new Circle(mouseX + ivImagePanoramique.getLayoutX(), mouseY, 5);
            circPoint.setFill(Color.BLUE);
            circPoint.setStroke(Color.YELLOW);
            circPoint.setId("img" + iNumImages);
            circPoint.setCursor(Cursor.DEFAULT);
            panePanoramique.getChildren().add(circPoint);
            Tooltip tltpImage = new Tooltip("image n° " + (iNumImages + 1));
            tltpImage.setStyle(strTooltipStyle);
            Tooltip.install(circPoint, tltpImage);

//
            File fileRepert;
            if (strRepertHSImages.equals("")) {
                fileRepert = new File(strCurrentDir + File.separator);
            } else {
                fileRepert = new File(strRepertHSImages);
            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                strRepertHSImages = fileFichierImage.getParent();
                iNumImages++;
                HotspotImage HS = new HotspotImage();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                HS.setStrUrlImage(fileFichierImage.getAbsolutePath());
                HS.setStrLienImg(fileFichierImage.getName());
                HS.setStrInfo(fileFichierImage.getName().split("\\.")[0]);
                File fileRepertImage = new File(strRepertTemp + File.separator + "images");
                if (!fileRepertImage.exists()) {
                    fileRepertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                panoramiquesProjet[iPanoActuel].addHotspotImage(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(strListePano(iPanoActuel), iPanoActuel);
                affHS1.setId("labels");
                vbOutils.getChildren().add(affHS1);

            } else {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(3, strPoint.length());
                Node nodeImage = (Node) panePanoramique.lookup("#img" + strPoint);
                panePanoramique.getChildren().remove(nodeImage);
            }
            valideHS();
            circPoint.setOnDragDetected((mouseEvent1) -> {
                circPoint.setFill(Color.YELLOW);
                circPoint.setStroke(Color.BLUE);
                bDragDrop = true;
                mouseEvent1.consume();

            });
            circPoint.setOnMouseDragged((mouseEvent1) -> {
                double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
                if (XX < 0) {
                    XX = 0;
                }
                if (XX > ivImagePanoramique.getFitWidth()) {
                    XX = ivImagePanoramique.getFitWidth();
                }
                circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
                double YY = mouseEvent1.getY();
                if (YY < 0) {
                    YY = 0;
                }
                if (YY > ivImagePanoramique.getFitHeight()) {
                    YY = ivImagePanoramique.getFitHeight();
                }
                circPoint.setCenterY(YY);

                mouseEvent1.consume();

            });
            circPoint.setOnMouseReleased((mouseEvent1) -> {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(3, strPoint.length());
                int iNumeroPoint = Integer.parseInt(strPoint);
                double X1 = mouseEvent1.getSceneX();
                double Y1 = mouseEvent1.getSceneY();
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
                panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLatitude(lat);
                panoramiquesProjet[iPanoActuel].getHotspotImage(iNumeroPoint).setLongitude(longit);
                circPoint.setFill(Color.BLUE);
                circPoint.setStroke(Color.YELLOW);
                mouseEvent1.consume();

            });

            circPoint.setOnMouseClicked((mouseEvent1) -> {
                if (mouseEvent1.isControlDown()) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(3, strPoint.length());
                    int iNumeroPoint = Integer.parseInt(strPoint);
                    Node nodeImage;
                    nodeImage = (Node) panePanoramique.lookup("#img" + strPoint);
                    panePanoramique.getChildren().remove(nodeImage);

                    for (int io = iNumeroPoint + 1; io < iNumImages; io++) {
                        nodeImage = (Node) panePanoramique.lookup("#img" + Integer.toString(io));
                        nodeImage.setId("img" + Integer.toString(io - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    iNumImages--;
                    panoramiquesProjet[iPanoActuel].removeHotspotImage(iNumeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                }
                valideHS();
                mouseEvent1.consume();
            });

        }
    }
    private void panoAjouteHTML(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            valideHS();
            bDejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            double longitude, latitude;
            double largeur = ivImagePanoramique.getFitWidth();
            longitude = 360.0f * mouseX / largeur - 180;
            latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            Circle circPoint = new Circle(mouseX + ivImagePanoramique.getLayoutX(), mouseY, 5);
            circPoint.setFill(Color.DARKGREEN);
            circPoint.setStroke(Color.YELLOWGREEN);
            circPoint.setId("html" + iNumHTML);
            circPoint.setCursor(Cursor.DEFAULT);
            panePanoramique.getChildren().add(circPoint);
            Tooltip tltpImage = new Tooltip("HTML n° " + (iNumHTML + 1));
            tltpImage.setStyle(strTooltipStyle);
            Tooltip.install(circPoint, tltpImage);

//
            File fileRepert;
            if (strRepertHSImages.equals("")) {
                fileRepert = new File(strCurrentDir + File.separator);
            } else {
                fileRepert = new File(strRepertHSImages);
            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                strRepertHSImages = fileFichierImage.getParent();
                iNumHTML++;
                HotspotHTML HS = new HotspotHTML();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
//                HS.setStrUrlImage(fileFichierImage.getAbsolutePath());
//                HS.setStrLienImg(fileFichierImage.getName());
                HS.setStrInfo(fileFichierImage.getName().split("\\.")[0]);
                File fileRepertImage = new File(strRepertTemp + File.separator + "images");
                if (!fileRepertImage.exists()) {
                    fileRepertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                panoramiquesProjet[iPanoActuel].addHotspotHTML(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(strListePano(iPanoActuel), iPanoActuel);
                affHS1.setId("labels");
                vbOutils.getChildren().add(affHS1);

            } else {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(4, strPoint.length());
                Node nodeImage = (Node) panePanoramique.lookup("#HTML" + strPoint);
                panePanoramique.getChildren().remove(nodeImage);
            }
            valideHS();
            circPoint.setOnDragDetected((mouseEvent1) -> {
                circPoint.setFill(Color.YELLOWGREEN);
                circPoint.setStroke(Color.DARKGREEN);
                bDragDrop = true;
                mouseEvent1.consume();

            });
            circPoint.setOnMouseDragged((mouseEvent1) -> {
                double XX = mouseEvent1.getX() - ivImagePanoramique.getLayoutX();
                if (XX < 0) {
                    XX = 0;
                }
                if (XX > ivImagePanoramique.getFitWidth()) {
                    XX = ivImagePanoramique.getFitWidth();
                }
                circPoint.setCenterX(XX + ivImagePanoramique.getLayoutX());
                double YY = mouseEvent1.getY();
                if (YY < 0) {
                    YY = 0;
                }
                if (YY > ivImagePanoramique.getFitHeight()) {
                    YY = ivImagePanoramique.getFitHeight();
                }
                circPoint.setCenterY(YY);

                mouseEvent1.consume();

            });
            circPoint.setOnMouseReleased((mouseEvent1) -> {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(4, strPoint.length());
                int iNumeroPoint = Integer.parseInt(strPoint);
                double X1 = mouseEvent1.getSceneX();
                double Y1 = mouseEvent1.getSceneY();
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
                panoramiquesProjet[iPanoActuel].getHotspotHTML(iNumeroPoint).setLatitude(lat);
                panoramiquesProjet[iPanoActuel].getHotspotHTML(iNumeroPoint).setLongitude(longit);
                circPoint.setFill(Color.DARKGREEN);
                circPoint.setStroke(Color.YELLOWGREEN);
                mouseEvent1.consume();

            });

            circPoint.setOnMouseClicked((mouseEvent1) -> {
                if (mouseEvent1.isControlDown()) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(4, strPoint.length());
                    int iNumeroPoint = Integer.parseInt(strPoint);
                    Node nodeImage;
                    nodeImage = (Node) panePanoramique.lookup("#html" + strPoint);
                    panePanoramique.getChildren().remove(nodeImage);

                    for (int io = iNumeroPoint + 1; io < iNumHTML; io++) {
                        nodeImage = (Node) panePanoramique.lookup("#html" + Integer.toString(io));
                        nodeImage.setId("html" + Integer.toString(io - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    iNumHTML--;
                    panoramiquesProjet[iPanoActuel].removeHotspotHTML(iNumeroPoint);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                }
                valideHS();
                mouseEvent1.consume();
            });

        }
    }

    private void gereSourisPanoramique(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (mouseEvent.isShiftDown()) {
                panoChoixNord(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX());
                mouseEvent.consume();
            } else if (mouseEvent.isControlDown()) {
            } else {
                panoChoixRegard(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
                mouseEvent.consume();
            }
        }
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (!(mouseEvent.isControlDown()) && bEstCharge) {
                if (!bDragDrop) {
                    panePanoramique.setCursor(Cursor.DEFAULT);
                    panePanoramique.setOnMouseClicked(
                            (me) -> {
                            }
                    );
                    Circle c1 = new Circle(mouseEvent.getSceneX(), mouseEvent.getSceneY() - panePanoramique.getLayoutY() - 109, 3);
                    panePanoramique.getChildren().add(c1);
                    ListView<String> lvMenuChoixTypeHotspot = new ListView<>();
                    lvMenuChoixTypeHotspot.setMaxHeight(62);
                    lvMenuChoixTypeHotspot.setPrefHeight(62);
                    lvMenuChoixTypeHotspot.setMinHeight(62);
                    if (iNombrePanoramiques > 1) {
                        lvMenuChoixTypeHotspot.getItems().add("Panoramique");
                        lvMenuChoixTypeHotspot.setMaxHeight(80);
                        lvMenuChoixTypeHotspot.setMinHeight(80);
                        lvMenuChoixTypeHotspot.setPrefHeight(80);
                    }
                    lvMenuChoixTypeHotspot.setPrefWidth(120);
                    lvMenuChoixTypeHotspot.getItems().add("Image");
                    lvMenuChoixTypeHotspot.getItems().add("HTML");
                    lvMenuChoixTypeHotspot.getItems().add("Annuler");
                    lvMenuChoixTypeHotspot.setCursor(Cursor.DEFAULT);
                    lvMenuChoixTypeHotspot.setLayoutX(mouseEvent.getSceneX());
                    lvMenuChoixTypeHotspot.setLayoutY(mouseEvent.getSceneY() - panePanoramique.getLayoutY()- 104);
                    panePanoramique.getChildren().add(lvMenuChoixTypeHotspot);
                    lvMenuChoixTypeHotspot.getSelectionModel().selectedItemProperty().addListener((ov, ancValeur, nouvValeur) -> {
                        panePanoramique.getChildren().remove(lvMenuChoixTypeHotspot);
                        panePanoramique.getChildren().remove(c1);

                        switch (nouvValeur) {
                            case "Panoramique":
                                panoMouseClic(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
                                break;
                            case "Image":
                                panoAjouteImage(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
                                break;
                            case "HTML":
                                panoAjouteHTML(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
                                break;
                            case "Annuler":
                                //panoAjouteImage(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
                                break;

                        }
                        panePanoramique.setCursor(Cursor.CROSSHAIR);
                        panePanoramique.setOnMouseClicked(
                                (me) -> {
                                    gereSourisPanoramique(me);
                                }
                        );

                    }
                    );

                    //panoMouseClic(mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX(), mouseEvent.getSceneY());
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
                (mouseEvent) -> {
                    gereSourisPanoramique(mouseEvent);
                }
        );
        /**
         *
         */
        panePanoramique.setOnMouseMoved(
                (mouseEvent) -> {
                    if (bEstCharge) {
                        double mouseX = mouseEvent.getSceneX() - ivImagePanoramique.getLayoutX();
                        if (mouseX < 0) {
                            mouseX = 0;
                        }
                        if (mouseX > ivImagePanoramique.getFitWidth()) {
                            mouseX = ivImagePanoramique.getFitWidth();
                        }
                        double mouseY = mouseEvent.getSceneY() - panePanoramique.getLayoutY() - 109;
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
                        String strLong = "Long : " + String.format("%.1f", longitude);
                        String strLat = "Lat : " + String.format("%.1f", latitude);
                        lblLong.setText(strLong);
                        lblLat.setText(strLat);
                    }
                }
        );
        /*
        
         */
        cbListeChoixPanoramiqueEntree.valueProperty()
                .addListener((ov, ancienneValeur, nouvelleValeur) -> {
                    if ((nouvelleValeur != null) && (!nouvelleValeur.equals(""))) {
                        strPanoEntree = nouvelleValeur.toString().split("\\.")[0];
                    }

                }
                );

        cbListeChoixPanoramique.valueProperty()
                .addListener((ov, ancienneValeur, nouvelleValeur) -> {
                    if (nouvelleValeur != null) {
                        if (!(nouvelleValeur.equals(strPanoAffiche))) {
                            clickBtnValidePano();
                            valideHS();
                            strPanoAffiche = nouvelleValeur.toString();
                            int numPanoChoisit = cbListeChoixPanoramique.getSelectionModel().getSelectedIndex();
                            affichePanoChoisit(numPanoChoisit);
                        }
                    }
                });

    }

    private void ajouteAffichageLignes() {
        double largeur = ivImagePanoramique.getFitWidth();
        double hauteur = largeur / 2.0d;
        Line lineCoordonnees;
        int iX, iY;
        int iNl = 0;
        for (int i = -180; i < 180; i += 10) {
            iX = (int) (largeur / 2.0f + largeur / 360.0f * (float) i + ivImagePanoramique.getLayoutX());
            lineCoordonnees = new Line(iX, 0, iX, hauteur);
            lineCoordonnees.setId("ligne" + iNl);
            iNl++;
            lineCoordonnees.setStroke(Color.ORANGE);
            if (i == 0) {
                lineCoordonnees.setStroke(Color.WHITE);
                lineCoordonnees.setStrokeWidth(0.5);
            } else {
                if ((i % 20) == 0) {
                    lineCoordonnees.setStroke(Color.WHITE);
                    lineCoordonnees.setStrokeWidth(0.25);
                } else {
                    lineCoordonnees.setStroke(Color.GRAY);
                    lineCoordonnees.setStrokeWidth(0.25);
                }
            }
            panePanoramique.getChildren().add(lineCoordonnees);
        }
        for (int i = -90; i < 90; i += 10) {
            iY = (int) (hauteur / 2.0f + hauteur / 180.0f * (float) i);
            lineCoordonnees = new Line(ivImagePanoramique.getLayoutX(), iY, largeur + ivImagePanoramique.getLayoutX(), iY);
            lineCoordonnees.setId("ligne" + iNl);
            iNl++;
            if (i == 0) {
                lineCoordonnees.setStroke(Color.WHITE);
                lineCoordonnees.setStrokeWidth(0.5);
            } else {
                if ((i % 20) == 0) {
                    lineCoordonnees.setStroke(Color.WHITE);
                    lineCoordonnees.setStrokeWidth(0.25);
                } else {
                    lineCoordonnees.setStroke(Color.GRAY);
                    lineCoordonnees.setStrokeWidth(0.25);
                }
            }

            panePanoramique.getChildren().add(lineCoordonnees);
        }

    }

    /**
     *
     */
    private void retireAffichageLigne() {
        int i = 0;
        Node nodeLigne;
        do {
            nodeLigne = (Node) panePanoramique.lookup("#ligne" + i);
            if (nodeLigne != null) {
                panePanoramique.getChildren().remove(nodeLigne);
            }
            i++;
        } while (nodeLigne != null);
    }

    /**
     *
     * @param iNumPanochoisi
     */
    @SuppressWarnings("empty-statement")
    private void affichePanoChoisit(int iNumPanochoisi) {
        ivImagePanoramique.setImage(panoramiquesProjet[iNumPanochoisi].getImgPanoramique());
        Node nodeLegende = (Node) apPanneauPrincipal.lookup("#legende");
        nodeLegende.setVisible(true);
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        retireAffichageLigne();
        iPanoActuel = iNumPanochoisi;

        iNumPoints = 0;
        if (navigateurPanoramique == null) {
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int iHauteur = (int) tailleEcran.getHeight() - 170;
            int iLargeur = (int) tailleEcran.getWidth() - 20;
            apVisuPanoramique.getChildren().clear();
            Pane paneFond = new Pane();
            paneFond.setPrefSize(24, 24);
            //paneFond.setStyle("-fx-background-color : #fff;");
            Image imgExpand = new Image("file:" + strRepertAppli + "/images/expand.png", 32, 24, false, true);
            Image imgShrink = new Image("file:" + strRepertAppli + "/images/shrink.png", 32, 24, false, true);
            ImageView ivExpShrk = new ImageView(imgExpand);
            paneFond.setLayoutX(10);
            paneFond.setLayoutY(10);
            paneFond.getChildren().add(ivExpShrk);
            navigateurPanoramique
                    = new AfficheNavigateurPanoramique(panoramiquesProjet[iNumPanochoisi].getStrNomFichier(), 10.d, 10.d, 340, 200);
            apVisuPano = navigateurPanoramique.affichePano();
            navigateurPanoramique.addPropertyChangeListener("positNord", (e) -> {
                double longitude = Math.round(Double.parseDouble(e.getNewValue().toString()) * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                panoramiquesProjet[iPanoActuel].setZeroNord(longitude);
                afficheNord(longitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixLatitude", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;

                panoramiquesProjet[iPanoActuel].setRegardX(longitude);
                panoramiquesProjet[iPanoActuel].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixLongitude", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                panoramiquesProjet[iPanoActuel].setRegardX(longitude);
                panoramiquesProjet[iPanoActuel].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixFov", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                panoramiquesProjet[iPanoActuel].setRegardX(longitude);
                panoramiquesProjet[iPanoActuel].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.setPositNord(panoramiquesProjet[iNumPanochoisi].getZeroNord() - 180);
            navigateurPanoramique.setLongitude(panoramiquesProjet[iNumPanochoisi].getRegardX() - 180);
            navigateurPanoramique.setLatitude(panoramiquesProjet[iNumPanochoisi].getRegardY());
            apVisuPano.getChildren().add(paneFond);
            apVisuPanoramique.getChildren().addAll(apVisuPano);

            paneFond.setOnMouseClicked((ev) -> {
                if (bPleinEcranPanoramique) {
                    navigateurPanoramique.changeTaille(340, 200);
                    apVisuPano.getChildren().add(paneFond);
                    ivExpShrk.setImage(imgExpand);
                    apEnvironnement.getChildren().remove(apVisuPano);
                    apVisuPanoramique.getChildren().add(apVisuPano);
                    bPleinEcranPanoramique = false;
                    mbarPrincipal.setDisable(false);
                    hbBarreBouton.setDisable(false);
                    tpEnvironnement.setDisable(false);

                } else {
                    bPleinEcranPanoramique = true;
                    navigateurPanoramique.changeTaille(iLargeur, iHauteur);
                    ivExpShrk.setImage(imgShrink);
                    apVisuPano.getChildren().add(paneFond);
                    apVisuPanoramique.getChildren().remove(apVisuPano);
                    apEnvironnement.getChildren().add(apVisuPano);
                    mbarPrincipal.setDisable(true);
                    hbBarreBouton.setDisable(true);
                    tpEnvironnement.setDisable(true);

                }
            });
            navigateurPanoramique.affiche();
        } else {
            navigateurPanoramique.setImagePanoramique(panoramiquesProjet[iNumPanochoisi].getStrNomFichier(), panoramiquesProjet[iNumPanochoisi].getImgVisuPanoramique());
            navigateurPanoramique.setPositNord(panoramiquesProjet[iNumPanochoisi].getZeroNord() - 180);
            navigateurPanoramique.setLongitude(panoramiquesProjet[iNumPanochoisi].getRegardX() - 180);
            navigateurPanoramique.setLatitude(panoramiquesProjet[iNumPanochoisi].getRegardY());
            navigateurPanoramique.affiche();
        }
        ajouteAffichageHotspots();
        affichePoV(panoramiquesProjet[iNumPanochoisi].getRegardX(), panoramiquesProjet[iNumPanochoisi].getRegardY());
        afficheNord(panoramiquesProjet[iNumPanochoisi].getZeroNord());
        ajouteAffichagePointsHotspots();
        ajouteAffichagePointsHotspotsImage();

        ajouteAffichageLignes();
        afficheInfoPano();
    }

    private void afficheVueChoisie() {

//        fov = Math.round(navigateurPanoramique.getChoixFov() * 10) / 10.d;
//        tfLatitude.setText("lat : " + latitude);
//        tfLongitude.setText("lon : " + longitude);
//        tfFov.setText("fov : " + fov);
    }

    private void afficheInfoPano() {
        TextField tfTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
        //System.out.println("Titre(" + iPanoActuel + ") : " + panoramiquesProjet[iPanoActuel].getStrTitrePanoramique());
        if (panoramiquesProjet[iPanoActuel].getStrTitrePanoramique() != null) {
            tfTitrePano.setText(panoramiquesProjet[iPanoActuel].getStrTitrePanoramique());
        } else {
            tfTitrePano.setText("");
        }
    }

    private Image imgTransformationImage(Image imgRect, int iRapport) {
        int iLargeur = (int) imgRect.getWidth() / iRapport;
        int iHauteur = iLargeur / 2 / iRapport;
        WritableImage imgMercator = new WritableImage(iLargeur, iHauteur);
        PixelReader prRect = imgRect.getPixelReader();
        PixelWriter pwMercator = imgMercator.getPixelWriter();
        for (int i = 0; i < iLargeur; i++) {
            for (int j = 0; j < iHauteur; j++) {
                double phi = Math.asin(2.d * (iHauteur / 2.d - j) / iHauteur);
                int y2 = (int) (iHauteur * iRapport * (0.5d - phi / Math.PI));
                if (y2 >= iHauteur * iRapport) {
                    y2 = iHauteur * iRapport - 1;
                }
                Color clPixel = prRect.getColor(i * iRapport, y2 * iRapport);
                pwMercator.setColor(i, j, clPixel);
            }
        }
        return imgMercator;
    }

    /**
     *
     * @param fichierPano
     */
    @SuppressWarnings("empty-statement")
    private void ajoutePanoramiqueProjet(String fichierPano, String typePano, int iNumero, int iTotal) throws InterruptedException {
        bPanoCharge = true;
        bEstCharge = true;
        Panoramique panoCree = new Panoramique();
        //System.out.println("Type pano : " + typePano);
        if (typePano.equals(Panoramique.SPHERE)) {
            //System.out.println("sphere : " + fichierPano);
            panoCree.setStrNomFichier(fichierPano);
            String strNom = fichierPano.substring(0, fichierPano.length() - 4);
            int iNombreNiveaux = creeNiveauxImageEqui(fichierPano, strRepertPanos, iNumero, iTotal);
            Platform.runLater(() -> {
                lblNiveaux.setText("Création visualisation panoramique");
                pbarAvanceChargement.setProgress((double) (iNumero - 1) / (double) iTotal + 0.666 / iTotal);
            });

            panoCree.setStrNomFichier(fichierPano);
            Image imgPano3 = null;
            String strExtension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
            if ("tif".equals(strExtension)) {
                try {
                    imgPanoRetaille2 = ReadWriteImage.readTiff(fichierPano);
                    imgPanoRetaille2 = ReadWriteImage.resizeImage(imgPanoRetaille2, 1200, 600);
                    imgPano3 = ReadWriteImage.readTiff(fichierPano);
                    imgPano3 = ReadWriteImage.resizeImage(imgPano3, 300, 150);
                    panoCree.setImgVisuPanoramique(imgTransformationImage(ReadWriteImage.readTiff(fichierPano), 2));
                } catch (ImageReadException | IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                imgPanoRetaille2 = new Image("file:" + fichierPano, 1200, 600, true, true, true);
                imgPano3 = new Image("file:" + fichierPano, 300, 150, true, true, true);
                panoCree.setImgVisuPanoramique(imgTransformationImage(new Image("file:" + fichierPano), 2));
            }
            //Image imgVisuPano=imgTransformationImage(imgPanoRetaille2, 1);
            //panoCree.setImgVisuPanoramique(imgVisuPano);
            panoCree.setImgPanoramique(imgPanoRetaille2);
            panoCree.setNombreNiveaux(iNombreNiveaux);
            panoCree.setImgVignettePanoramique(imgPano3);
            panoCree.setStrTypePanoramique(Panoramique.SPHERE);
        }
        if (typePano.equals(Panoramique.CUBE)) {
            //System.out.println("sphere");
            panoCree.setStrNomFichier(fichierPano);
            panoCree.setStrNomFichier(fichierPano);
            String strNom = fichierPano.substring(0, fichierPano.length() - 6);
            String strExtension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
            Image imgTop = null;
            Image imgBottom = null;
            Image imgLeft = null;
            Image imgRight = null;
            Image imgFront = null;
            Image imgBehind = null;
            if (strExtension.equals("bmp")) {
                imgTop = new Image("file:" + strNom + "_u.bmp");
                imgBottom = new Image("file:" + strNom + "_d.bmp");
                imgLeft = new Image("file:" + strNom + "_l.bmp");
                imgRight = new Image("file:" + strNom + "_r.bmp");
                imgFront = new Image("file:" + strNom + "_f.bmp");
                imgBehind = new Image("file:" + strNom + "_b.bmp");
            }
            if (strExtension.equals("jpg")) {
                imgTop = new Image("file:" + strNom + "_u.jpg");
                imgBottom = new Image("file:" + strNom + "_d.jpg");
                imgLeft = new Image("file:" + strNom + "_l.jpg");
                imgRight = new Image("file:" + strNom + "_r.jpg");
                imgFront = new Image("file:" + strNom + "_f.jpg");
                imgBehind = new Image("file:" + strNom + "_b.jpg");
            }
            if (strExtension.equals("tif")) {
                try {
                    imgTop = ReadWriteImage.readTiff(strNom + "_u.tif");
                    imgBottom = ReadWriteImage.readTiff(strNom + "_d.tif");
                    imgLeft = ReadWriteImage.readTiff(strNom + "_l.tif");
                    imgRight = ReadWriteImage.readTiff(strNom + "_r.tif");
                    imgFront = ReadWriteImage.readTiff(strNom + "_f.tif");
                    imgBehind = ReadWriteImage.readTiff(strNom + "_b.tif");
                } catch (ImageReadException | IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image imgEquiRectangulaire = TransformationsPanoramique.cube2rect(imgFront, imgLeft, imgRight, imgBehind, imgTop, imgBottom, 1200);
            Image imgEquiRectangulaire2 = TransformationsPanoramique.cube2rect(imgFront, imgLeft, imgRight, imgBehind, imgTop, imgBottom, 2400);
            Image imgVignetteEquiRectangulaire = TransformationsPanoramique.cube2rect(imgFront, imgLeft, imgRight, imgBehind, imgTop, imgBottom, 300);
            panoCree.setImgPanoramique(imgEquiRectangulaire);
            Platform.runLater(() -> {
                lblNiveaux.setText("Création visualisation panoramique");
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 1.d / 3.d) / iTotal);
            });

            panoCree.setImgVisuPanoramique(imgTransformationImage(imgEquiRectangulaire2, 1));
            int iNombreNiveaux = creeNiveauxImageCube(strNom + "_u." + strExtension, strRepertPanos, "dessus");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 4.d / 9.d) / (double) iTotal);
            });

            creeNiveauxImageCube(strNom + "_d." + strExtension, strRepertPanos, "dessous");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 5.d / 9.d) / (double) iTotal);
            });
            creeNiveauxImageCube(strNom + "_l." + strExtension, strRepertPanos, "gauche");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 2.d / 3.d) / (double) iTotal);
            });

            creeNiveauxImageCube(strNom + "_r." + strExtension, strRepertPanos, "droite");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 7.d / 9.d) / (double) iTotal);
            });

            creeNiveauxImageCube(strNom + "_f." + strExtension, strRepertPanos, "devant");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 8.d / 9.d) / (double) iTotal);
            });

            creeNiveauxImageCube(strNom + "_b." + strExtension, strRepertPanos, "derrière");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero)) / (double) iTotal);
            });

            panoCree.setNombreNiveaux(iNombreNiveaux);

            panoCree.setImgVignettePanoramique(imgVignetteEquiRectangulaire);
            panoCree.setStrTypePanoramique(Panoramique.CUBE);
        }

        panoCree.setRegardX(0.0d);
        panoCree.setRegardY(0.0d);
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
        File fileTemplate;
        FileChooser fcRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier Modèle panoVisu (*.tpl)", "*.tpl");
        fcRepertChoix.getExtensionFilters().add(extFilter);
        File fileRepert = new File(strRepertAppli + File.separator + "templates");
        if (!fileRepert.exists()) {
            fileRepert.mkdirs();
        }
        fcRepertChoix.setInitialDirectory(fileRepert);
        fileTemplate = fcRepertChoix.showSaveDialog(null);

        if (fileTemplate != null) {
            String strContenuFichier = gestionnaireInterface.strGetTemplate();
            fileTemplate.setWritable(true);
            OutputStreamWriter oswTemplate = new OutputStreamWriter(new FileOutputStream(fileTemplate), "UTF-8");
            try (BufferedWriter bwTemplate = new BufferedWriter(oswTemplate)) {
                bwTemplate.write(strContenuFichier);
            }
            Dialogs.create().title(rbLocalisation.getString("main.dialog.sauveModele"))
                    .message(rbLocalisation.getString("main.dialog.sauveModeleMessage"))
                    .showInformation();
        }

    }

    /**
     *
     */
    private void modeleCharger() throws IOException {
        FileChooser fileRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.tpl)", "*.tpl");
        fileRepertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(strRepertAppli + File.separator + "templates");
        fileRepertChoix.setInitialDirectory(repert);
        File filetemplate = fileRepertChoix.showOpenDialog(stPrincipal);
        if (filetemplate != null) {
            try {
                List<String> strListTemplate = new ArrayList<>();
                BufferedReader brTemplate = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filetemplate), "UTF-8"));

                String strLigneTexte;
                while ((strLigneTexte = brTemplate.readLine()) != null) {
                    strListTemplate.add(strLigneTexte);
                }
                //System.out.println(texte);
                gestionnaireInterface.setTemplate(strListTemplate);
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

    private static void choixZone(int iLargeur, int iHauteur, boolean bMasqueZones, String strIdZone, MouseEvent mouseEvent) {
        ComboBox cbTouchesBarre = new ComboBox();
        cbTouchesBarre.getItems().clear();
        for (int i = 0; i < strTouchesBarre.length; i++) {
            cbTouchesBarre.getItems().add(i, strTouchesBarre[i]);
        }
        cbTouchesBarre.setLayoutX(200);
        cbTouchesBarre.setLayoutX(40);

        final int iNumeroZone = Integer.parseInt(strIdZone.split("-")[1]);
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                for (int ij = iNumeroZone; ij < iNombreZones - 1; ij++) {
                    zones[ij] = zones[ij + 1];
                }
                iNombreZones--;
                afficheBarrePersonnalisee(iLargeur, iHauteur, bMasqueZones);
            } else {
                afficheBarrePersonnalisee(iLargeur, iHauteur, bMasqueZones);
                apZoneBarrePersonnalisee.getChildren().clear();
                apZoneBarrePersonnalisee.getChildren().add(cbTouchesBarre);
                ZoneTelecommande zone = zones[iNumeroZone];
                int index = -1;
                for (int ij = 0; ij < strCodeBarre.length; ij++) {
                    if (strCodeBarre[ij].equals(zone.getStrIdZone())) {
                        index = ij;
                    }
                    //System.out.println(strCodeBarre[ij] + " " + zone.getStrIdZone() + " index :" + index);
                }
                if (index != -1) {
                    cbTouchesBarre.getSelectionModel().select(index);
                }

                cbTouchesBarre.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                        if (nouvelleValeur != null) {
                            String strId = strCodeBarre[cbTouchesBarre.getSelectionModel().getSelectedIndex()];
                            zones[iNumeroZone].setStrIdZone(strId);
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
                        Polygon poly = (Polygon) apImgBarrePersonnalisee.lookup("#" + strIdZone);
                        poly.setFill(Color.rgb(255, 0, 0, 0.5));
                        poly.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourPolygone(iNumeroZone, poly.getPoints()));
                        break;
                    case "rect":
                        Rectangle rect = (Rectangle) apImgBarrePersonnalisee.lookup("#" + strIdZone);
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        rect.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourRectangle(iNumeroZone, rect));
                        break;
                    case "circle":
                        Circle cercle = (Circle) apImgBarrePersonnalisee.lookup("#" + strIdZone);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(creeAncresPourCercle(iNumeroZone, cercle));
                        break;
                }

            }
        }

    }

    private static void afficheBarrePersonnalisee(int iLargeur, int iHauteur, boolean bMasqueZones) {
        apImgBarrePersonnalisee.getChildren().clear();
        apZoneBarrePersonnalisee.getChildren().clear();
        ImageView ivBarrePersonnalisee = new ImageView(imgBarrePersonnalisee);
        apImgBarrePersonnalisee.getChildren().add(ivBarrePersonnalisee);
        apImgBarrePersonnalisee.setPrefWidth(imgBarrePersonnalisee.getWidth());
        apImgBarrePersonnalisee.setPrefHeight(imgBarrePersonnalisee.getHeight());
        apImgBarrePersonnalisee.setCursor(Cursor.CROSSHAIR);
        apImgBarrePersonnalisee.setLayoutX((iLargeur - 300 - apImgBarrePersonnalisee.getPrefWidth()) / 2.d);
        apImgBarrePersonnalisee.setLayoutY((iHauteur - apImgBarrePersonnalisee.getPrefHeight()) / 2.d);
        //System.out.println("nombre Zones :" + nombreZones);
        if ((iNombreZones > 0) && !bMasqueZones) {
            for (int i = 0; i < iNombreZones; i++) {
                ZoneTelecommande zone = zones[i];
                //System.out.println(i + " => " + zone.getStrTypeZone() + " " + zone.getStrCoordonneesZone());
                String[] strPoints = zone.getStrCoordonneesZone().split(",");
                double[] points = new double[strPoints.length];
                for (int ij = 0; ij < strPoints.length; ij++) {
                    points[ij] = Double.parseDouble(strPoints[ij]);
                }
                final String strIdZone = zone.getStrTypeZone() + "-" + i;

                switch (zone.getStrTypeZone()) {
                    case "circle":
                        Circle cercle = new Circle(points[0], points[1], points[2]);
                        cercle.setFill(Color.rgb(255, 255, 0, 0.5));
                        cercle.setStroke(Color.FORESTGREEN);
                        cercle.setCursor(Cursor.DEFAULT);
                        apImgBarrePersonnalisee.getChildren().add(cercle);
                        cercle.setId(strIdZone);
                        cercle.setOnMouseClicked((t) -> {
                            choixZone(iLargeur, iHauteur, bMasqueZones, strIdZone, t);
                            t.consume();
                        });
                        break;
                    case "rect":
                        double largRect = points[2] - points[0];
                        double hautRect = points[3] - points[1];
                        Rectangle rect = new Rectangle(points[0], points[1], largRect, hautRect);
                        rect.setFill(Color.rgb(255, 255, 0, 0.5));
                        rect.setStroke(Color.FORESTGREEN);
                        rect.setCursor(Cursor.DEFAULT);
                        apImgBarrePersonnalisee.getChildren().add(rect);
                        rect.setId(strIdZone);
                        rect.setOnMouseClicked((t) -> {
                            choixZone(iLargeur, iHauteur, bMasqueZones, strIdZone, t);
                            t.consume();
                        });
                        break;
                    case "poly":
                        Polygon poly = new Polygon(points);
                        poly.setFill(Color.rgb(255, 255, 0, 0.5));
                        poly.setStroke(Color.FORESTGREEN);
                        poly.setStrokeWidth(3);
                        poly.setCursor(Cursor.DEFAULT);
                        poly.setStrokeLineCap(StrokeLineCap.ROUND);
                        poly.setId(strIdZone);
                        apImgBarrePersonnalisee.getChildren().add(poly);
                        poly.setOnMouseClicked((t) -> {
                            choixZone(iLargeur, iHauteur, bMasqueZones, strIdZone, t);
                            t.consume();
                        });
                        break;
                }
            }
        }
    }

    private static void ajouterZone(int iLargeur, int iHauteur, boolean bMasqueZones) {
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

        afficheBarrePersonnalisee(iLargeur, iHauteur, bMasqueZones);
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
        apCreeZone.setOnMouseClicked((t) -> {
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

        apCreeZone.setOnMouseMoved((t) -> {
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

        btnValider.setOnMouseClicked((t) -> {
            if (strTypeZone.equals("poly")) {
                String strChaine = "";
                for (int i = 0; i < iNombrePointsZone; i++) {
                    if (i != 0) {
                        strChaine += ",";
                    }
                    strChaine += Math.round(pointsPolyZone[i * 2] * 10) / 10 + "," + Math.round(pointsPolyZone[i * 2 + 1] * 10) / 10;
                }
                zone.setStrCoordonneesZone(strChaine);

            }
            zones[iNombreZones] = zone;
            iNombreZones++;
            afficheBarrePersonnalisee(iLargeur, iHauteur, bMasqueZones);
            btnAjouteZone.setDisable(false);
        });

        btnAnnuler.setOnMouseClicked((t) -> {
            afficheBarrePersonnalisee(iLargeur, iHauteur, bMasqueZones);
            btnAjouteZone.setDisable(false);
        });

        cbTouchesBarre.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String strAncienneValeur, String strNouvelleValeur) {
                if (strNouvelleValeur != null) {
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
        OutputStreamWriter oswFichierBarre = null;
        try {
            String strZones = "";
            for (int i = 0; i < iNombreZones; i++) {
                ZoneTelecommande zone = zones[i];
                strZones += "[area=>id:" + zone.getStrIdZone() + ";";
                strZones += "shape:" + zone.getStrTypeZone() + ";";
                strZones += "coords:" + zone.getStrCoordonneesZone() + "]\n";
            }
            File fileIndexHTML = new File(fichShp);
            if (!fileIndexHTML.exists()) {
                fileIndexHTML.createNewFile();
            }
            fileIndexHTML.setWritable(true);
            oswFichierBarre = new OutputStreamWriter(new FileOutputStream(fileIndexHTML), "UTF-8");
            try (BufferedWriter bwFichierBarre = new BufferedWriter(oswFichierBarre)) {
                bwFichierBarre.write(strZones);

            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oswFichierBarre.close();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void creerEditerBarre(String strNomFichierBarre) {
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

        int iLargeurEcran = (int) tailleEcran.getWidth();
        int iHauteurEcran = (int) tailleEcran.getHeight() - 100;
        final int iLargeur = 1200;
        final int iHauteur = 600;
        mbarPrincipal.setDisable(true);
        hbBarreBouton.setDisable(true);
        tpEnvironnement.setDisable(true);
        apCreationBarre.setPrefWidth(iLargeur);
        apCreationBarre.setMinWidth(iLargeur);
        apCreationBarre.setMaxWidth(iLargeur);
        apCreationBarre.setPrefHeight(iHauteur);
        apCreationBarre.setMinHeight(iHauteur);
        apCreationBarre.setMaxHeight(iHauteur);
        apCreationBarre.setLayoutX((iLargeurEcran - iLargeur) / 2);
        apCreationBarre.setLayoutY((iHauteurEcran - iHauteur) / 2);
        apCreationBarre.setVisible(true);
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("main.creeBarrePersonnalisee"));
        lblBarrePersonnalisee.setMinWidth(iLargeur - 10);
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
        apOutilsBarre.setPrefHeight(iHauteur - 50);
        apOutilsBarre.setMinHeight(iHauteur - 50);
        apOutilsBarre.setMaxHeight(iHauteur - 50);
        apOutilsBarre.setLayoutX(iLargeur - 302);
        apOutilsBarre.setLayoutY(50);

        apOutilsBarre.setStyle("-fx-background-color : -fx-background;-fx-border-width : 1px;-fx-border-color : transparent transparent transparent -fx-outer-border;");
        btnAnnulerBarre.setPrefWidth(120);
        btnAnnulerBarre.setLayoutX(30);
        btnAnnulerBarre.setLayoutY(iHauteur - 90);
        btnSauverBarre.setPrefWidth(120);
        btnSauverBarre.setLayoutX(160);
        btnSauverBarre.setLayoutY(iHauteur - 90);
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
        if (!strNomFichierBarre.equals("")) {
            lblChargeImage.setVisible(false);
            tfChargeImage.setVisible(false);
            btnChargeImage.setVisible(false);
            String strNomFichier = strNomFichierBarre;
            strNomFichier = strNomFichier.substring(0, strNomFichier.length() - 4);
            strNomFichierShp = strNomFichier + ".shp";
            String strNomFichierPng = strNomFichier + ".png";
            File fichPng = new File(strNomFichierPng);
            if (fichPng.exists()) {
                try {
                    btnAjouteZone.setDisable(false);
                    imgBarrePersonnalisee = new Image("file:" + fichPng);
                    iNombreZones = gestionnaireInterface.lisFichierShp(strNomFichierShp, zones);
                    btnSauverBarre.setDisable(false);
                    if (iNombreZones > 0) {
                        cbMasqueZones.setDisable(false);
                    }
                    afficheBarrePersonnalisee(iLargeur, iHauteur, false);

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        btnAnnulerBarre.setOnMouseClicked((t) -> {
            mbarPrincipal.setDisable(false);
            hbBarreBouton.setDisable(false);
            tpEnvironnement.setDisable(false);
            apCreationBarre.setVisible(false);
        });

        btnAjouteZone.setOnMouseClicked((t) -> {
            btnAjouteZone.setDisable(true);
            ajouterZone(iLargeur, iHauteur, false);
        });

//        apCreationBarre.setOnMouseClicked((t) -> {
//            afficheBarrePersonnalisee(largeur, hauteur, cbMasqueZones.isSelected());
//            t.consume();
//        });
        btnChargeImage.setOnMouseClicked((t) -> {
            strRepertBarrePersonnalisee = strRepertAppli + "/theme/telecommandes";
            File fileRepert;
            fileRepert = new File(strRepertBarrePersonnalisee);
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter efShpFilter = new FileChooser.ExtensionFilter("Fichiers barre personnalisée (SHP)", "*.shp", "*.png");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(efShpFilter);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                String strNomFichier = fileFichierImage.getAbsolutePath();
                strNomFichier = strNomFichier.substring(0, strNomFichier.length() - 4);
                strNomFichierShp = strNomFichier + ".shp";
                String strNomFichierPng = strNomFichier + ".png";
                File fileFichierPng = new File(strNomFichierPng);
                if (fileFichierPng.exists()) {
                    try {
                        btnAjouteZone.setDisable(false);
                        imgBarrePersonnalisee = new Image("file:" + fileFichierPng);
                        iNombreZones = gestionnaireInterface.lisFichierShp(strNomFichierShp, zones);
                        btnSauverBarre.setDisable(false);
                        if (iNombreZones > 0) {
                            cbMasqueZones.setDisable(false);
                        }
                        afficheBarrePersonnalisee(iLargeur, iHauteur, false);

                    } catch (IOException ex) {
                        Logger.getLogger(EditeurPanovisu.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnSauverBarre.setOnMouseClicked((t) -> {
            try {
                sauverBarre(strNomFichierShp);
                gestionnaireInterface.chargeBarrePersonnalisee(strNomFichierBarre);

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        cbMasqueZones.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            afficheBarrePersonnalisee(iLargeur, iHauteur, new_val);
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
     * @param iTaille
     * @throws Exception
     */
    private void creeMenu(VBox vbRacine, int iTaille) throws Exception {
        //Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("menuPrincipal.fxml"));
        VBox vbMonPanneau = new VBox();
        vbMonPanneau.setPrefHeight(80);
        vbMonPanneau.setPrefWidth(3000);
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
            try (BufferedReader brHistoFichiers = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileHistoFichiers), "UTF-8"))) {
                while ((strTexteHisto = brHistoFichiers.readLine()) != null) {
                    MenuItem menuDerniersFichiers = new MenuItem(strTexteHisto);
                    mnuDerniersProjets.getItems().add(menuDerniersFichiers);
                    strHistoFichiers[nombreHistoFichiers] = strTexteHisto;
                    nombreHistoFichiers++;
                    menuDerniersFichiers.setOnAction((e) -> {
                        MenuItem mniSousMenu = (MenuItem) e.getSource();
                        try {
                            try {
                                projetChargeNom(mniSousMenu.getText());

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
        Menu mnuAide = new Menu(rbLocalisation.getString("aide"));
        mbarPrincipal.getMenus().add(mnuAide);
        mniAide = new MenuItem(rbLocalisation.getString("aideAide"));
        mniAide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        mnuAide.getItems().add(mniAide);
        SeparatorMenuItem sep4 = new SeparatorMenuItem();
        mnuAide.getItems().add(sep4);
        mniAPropos = new MenuItem(rbLocalisation.getString("aideAPropos"));
        mnuAide.getItems().add(mniAPropos);
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
        Tooltip tltpNouveauProjet = new Tooltip(rbLocalisation.getString("nouveauProjet"));
        tltpNouveauProjet.setStyle(strTooltipStyle);
        spBtnNouvprojet.setTooltip(tltpNouveauProjet);
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
        Tooltip tltpOuvrirProjet = new Tooltip(rbLocalisation.getString("ouvrirProjet"));
        tltpOuvrirProjet.setStyle(strTooltipStyle);
        spBtnOuvrirProjet.setTooltip(tltpOuvrirProjet);
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
        Tooltip tltpSauverProjet = new Tooltip(rbLocalisation.getString("sauverProjet"));
        tltpSauverProjet.setStyle(strTooltipStyle);
        spBtnSauveProjet.setTooltip(tltpSauverProjet);
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
        Tooltip tltpAjouterPano = new Tooltip(rbLocalisation.getString("ajouterPanoramiques"));
        tltpAjouterPano.setStyle(strTooltipStyle);
        spBtnAjoutePano.setTooltip(tltpAjouterPano);
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
        Tooltip tltpAjouterPlan = new Tooltip(rbLocalisation.getString("ajouterPlan"));
        tltpAjouterPlan.setStyle(strTooltipStyle);
        spBtnAjoutePlan.setTooltip(tltpAjouterPlan);
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
        Tooltip tltpGenererVisite = new Tooltip(rbLocalisation.getString("genererVisite"));
        tltpGenererVisite.setStyle(strTooltipStyle);
        spBtnGenereVisite.setTooltip(tltpGenererVisite);
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
        Tooltip tltpEqui2Cube = new Tooltip(rbLocalisation.getString("outilsEqui2Cube"));
        tltpEqui2Cube.setStyle(strTooltipStyle);
        spBtnEqui2Cube.setTooltip(tltpEqui2Cube);
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
        Tooltip tltpCube2Equi = new Tooltip(rbLocalisation.getString("outilsCube2Equi"));
        tltpCube2Equi.setStyle(strTooltipStyle);
        spBtnCube2Equi.setTooltip(tltpCube2Equi);
        hbBarreBouton.getChildren().add(spBtnCube2Equi);

        vbMonPanneau.getChildren().addAll(mbarPrincipal, hbBarreBouton);
        vbRacine.getChildren().add(vbMonPanneau);
        mniNouveauProjet.setOnAction((e) -> {
            projetsNouveau();
        });
        mniChargeProjet.setOnAction((e) -> {
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
        mniSauveProjet.setOnAction((e) -> {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniSauveSousProjet.setOnAction((e) -> {
            try {
                projetSauveSous();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniVisiteGenere.setOnAction((e) -> {
            try {
                genereVisite();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniFermerProjet.setOnAction((e) -> {
            try {
                projetsFermer();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniAjouterPano.setOnAction((e) -> {
            try {
                panoramiquesAjouter();

            } catch (InterruptedException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        mniAjouterPlan.setOnAction((e) -> {
            planAjouter();
        });
        mniAPropos.setOnAction((e) -> {
            aideapropos();
        });
        mniAide.setOnAction((e) -> {
            AideDialogController.affiche();
        });

        mniChargerModele.setOnAction((e) -> {
            try {
                modeleCharger();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        mniSauverModele.setOnAction((e) -> {
            try {
                modeleSauver();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        mniCube2EquiTransformation.setOnAction((e) -> {
            transformationCube2Equi();
        });

        mniEqui2CubeTransformation.setOnAction((e) -> {
            transformationEqui2Cube();
        });

        mniOutilsBarre.setOnAction((e) -> {
            creerEditerBarre("");
        });

        mniAffichageVisite.setOnAction((e) -> {
            tpEnvironnement.getSelectionModel().select(0);
        });
        mniAffichageInterface.setOnAction((e) -> {
            tpEnvironnement.getSelectionModel().select(1);
        });
        mniAffichagePlan.setOnAction((e) -> {
            if (!tabPlan.isDisabled()) {
                tpEnvironnement.getSelectionModel().select(2);
            }
        });

        mniConfigTransformation.setOnAction((e) -> {
            try {
                ConfigDialogController cfg = new ConfigDialogController();
                cfg.afficheFenetre();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        spBtnNouvprojet.setOnMouseClicked((t) -> {
            projetsNouveau();
        });
        spBtnOuvrirProjet.setOnMouseClicked((t) -> {
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
        spBtnSauveProjet.setOnMouseClicked((t) -> {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnAjoutePano.setOnMouseClicked((t) -> {
            try {
                panoramiquesAjouter();

            } catch (InterruptedException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnAjoutePlan.setOnMouseClicked((t) -> {
            planAjouter();
        });
        spBtnGenereVisite.setOnMouseClicked((t) -> {
            try {
                genereVisite();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        spBtnEqui2Cube.setOnMouseClicked((t) -> {
            transformationEqui2Cube();
        });
        spBtnCube2Equi.setOnMouseClicked((t) -> {
            transformationCube2Equi();
        });

    }

    private void retirePanoCourant() {
        Action actReponse = Dialogs.create()
                .owner(null)
                .title(rbLocalisation.getString("main.supprimerPano"))
                .message(rbLocalisation.getString("main.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (actReponse == Dialog.Actions.YES) {

            int iPanCourant = cbListeChoixPanoramique.getSelectionModel().getSelectedIndex();
            for (int i = iPanCourant; i < iNombrePanoramiques - 1; i++) {
                panoramiquesProjet[i] = panoramiquesProjet[i + 1];
            }
            int iPaneEntree = -1;
            iNombrePanoramiques--;
            if (cbListeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex() != iPanCourant) {
                iPaneEntree = cbListeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex();
            }
            if (iPaneEntree > iPanCourant) {
                iPaneEntree--;
            }
            cbListeChoixPanoramique.getItems().clear();
            cbListeChoixPanoramiqueEntree.getItems().clear();
            for (int i = 0; i < iNombrePanoramiques; i++) {
                String strNomPano = panoramiquesProjet[i].getStrNomFichier();
                strNomPano = strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.length());
                cbListeChoixPanoramique.getItems().add(strNomPano);
                cbListeChoixPanoramiqueEntree.getItems().add(strNomPano);
            }
            if (iPaneEntree != -1) {
                cbListeChoixPanoramiqueEntree.setValue(cbListeChoixPanoramique.getItems().get(iPaneEntree));
            }
            cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(0));
            affichePanoChoisit(0);
            //System.out.println("pano courant : " + panCourant);
        }
    }

    /**
     *
     * @param primaryStage
     * @param iLargeur
     * @param iHauteur
     * @throws Exception
     */
    private void creeEnvironnement(Stage stPrimaryStage, int iLargeur, int iHauteur) throws Exception {
        popUp = new PopUpDialogController();
        stPrimaryStage.setMaximized(true);
        double largeurOutils = 380;

        iHauteurInterface = iHauteur;
        iLargeurInterface = iLargeur;
        /**
         * Création des éléments constitutifs de l'écran
         */
        VBox vbRacine = new VBox();
        creeMenu(vbRacine, iLargeur);
        tpEnvironnement = new TabPane();
//        tabPaneEnvironnement.setTranslateZ(5);
        tpEnvironnement.setMinHeight(iHauteur - 60);
        tpEnvironnement.setMaxHeight(iHauteur - 60);
        Pane paneBarreStatus = new Pane();
        paneBarreStatus.setPrefSize(iLargeur + 20, 30);
        paneBarreStatus.setTranslateY(25);
        paneBarreStatus.setStyle("-fx-background-color:#c00;-fx-border-color:#aaa");
        tabVisite = new Tab();
        Pane paneVisualiseur;
        Pane panePlan;
        tabInterface = new Tab();
        tabPlan = new Tab();
        gestionnaireInterface.creeInterface(iLargeur, iHauteur - 60);
        paneVisualiseur = gestionnaireInterface.paneTabInterface;
        gestionnairePlan.creeInterface(iLargeur, iHauteur - 60);
        panePlan = gestionnairePlan.getPaneInterface();
        tabInterface.setContent(paneVisualiseur);
        tabPlan.setContent(panePlan);

        HBox hbEnvironnement = new HBox();
        TextField tfTitrePano;
        TextField tfTitreVisite;

        tpEnvironnement.getTabs().addAll(tabVisite, tabInterface, tabPlan);
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
        String strLabelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-padding : 5px;  -fx-border : 1px solid #777;-fx-width : 100px;-fx-margin : 5px; ";

        scnPrincipale = new Scene(vbRacine, iLargeur, iHauteur, Color.rgb(221, 221, 221));
//        if (systemeExploitation.indexOf("inux") != -1) {
//            root.setStyle("-fx-font-size : 7pt;-fx-font-family: sans-serif;");
//        } else {
        vbRacine.setStyle("-fx-font-size : 9pt;-fx-font-family: Arial;");
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
        Pane paneFond = new Pane();
        paneFond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
        paneFond.setTranslateX(340);
        paneFond.setTranslateY(-80);
        Tooltip tltpSupprimePano = new Tooltip(rbLocalisation.getString("main.supprimePano"));
        tltpSupprimePano.setStyle(strTooltipStyle);
        Tooltip.install(paneFond, tltpSupprimePano);
        paneFond.getChildren().add(ivSupprPanoramique);
        paneFond.setOnMouseClicked(
                (me) -> {
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
        tfTitrePano = new TextField();
        tfTitrePano.setId("txttitrepano");
        tfTitrePano.setPrefSize(200, 25);
        tfTitrePano.setMaxSize(340, 25);
        tfTitrePano.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            clickBtnValidePano();
        });
        apVisuPanoramique.setPrefWidth(340);
        apVisuPanoramique.setPrefHeight(260);
        vbChoixPanoramique.getChildren().addAll(
                lblTitreVisite, tfTitreVisite,
                lblChoixPanoramiqueEntree, cbListeChoixPanoramiqueEntree, sepPano,
                lblChoixPanoramique, cbListeChoixPanoramique, paneFond,
                apVisuPanoramique,
                lblTitrePano, tfTitrePano, sepInfo
        );
        vbChoixPanoramique.setSpacing(5);
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

        stPrimaryStage.setScene(scnPrincipale);
        //scene.getStylesheets().add("file:css/test.css");
        /**
         *
         */
        spVuePanoramique.setPrefSize(iLargeur - largeurOutils - 20, iHauteur - 130);
        spVuePanoramique.setMaxSize(iLargeur - largeurOutils - 20, iHauteur - 130);
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
        spPanneauOutils.setPrefSize(largeurOutils, iHauteur - 240);
        spPanneauOutils.setMaxWidth(largeurOutils);
        spPanneauOutils.setMaxHeight(iHauteur - 240);
        spPanneauOutils.setTranslateY(15);
        spPanneauOutils.setTranslateX(20);
//        panneauOutils.setStyle("-fx-background-color : #ccc;");
        /**
         *
         */
        panePanoramique.setCursor(Cursor.CROSSHAIR);
        vbOutils.setPrefWidth(largeurOutils - 20);
//        outils.setStyle("-fx-background-color : #ccc;");
        vbOutils.minHeight(iHauteur - 130);
        vbOutils.setLayoutX(10);
//        lblLong.setStyle(labelStyle);
//        lblLat.setStyle(labelStyle);
        lblLong.setPrefSize(100, 15);
        lblLat.setPrefSize(100, 15);
        lblLat.setTranslateX(50);
//        panneau2.setStyle("-fx-background-color : #ddd;");
        apPanneauPrincipal.setPrefSize(iLargeur - largeurOutils - 20, iHauteur - 140);

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
        apAttends.setLayoutX((iLargeur - apAttends.getPrefWidth()) / 2.d);
        apAttends.setLayoutY((iHauteur - apAttends.getPrefHeight()) / 2.d - 55);
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
        vbRacine.getChildren().addAll(apEnvironnement);
        apPanneauPrincipal.getChildren().setAll(hbCoordonnees, panePanoramique);
        stPrimaryStage.show();
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
    public void start(Stage stPrimaryStage) throws Exception {
        File fileRep = new File("");
        strCurrentDir = fileRep.getAbsolutePath();
        strRepertAppli = fileRep.getAbsolutePath();
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
        stPrincipal = stPrimaryStage;
        stPrincipal.setResizable(false);
        stPrincipal.setTitle("PanoVisu v" + strNumVersion);
        //AquaFx.style();
//        setUserAgentStylesheet(STYLESHEET_MODENA);
        stPrimaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int iHauteur = (int) tailleEcran.getHeight() - 20;
        int iLargeur = (int) tailleEcran.getWidth() - 20;
        largeurMax = tailleEcran.getWidth() - 450.0d;
        creeEnvironnement(stPrimaryStage, iLargeur, iHauteur);

        File fileRepertTempFile = new File(strRepertAppli + File.separator + "temp");
        strRepertTemp = fileRepertTempFile.getAbsolutePath();

        if (!fileRepertTempFile.exists()) {
            fileRepertTempFile.mkdirs();
        } else {
            deleteDirectory(strRepertTemp);
        }
        String strExtTemp = genereChaineAleatoire(20);
        strRepertTemp = strRepertTemp + File.separator + "temp" + strExtTemp;
        fileRepertTempFile = new File(strRepertTemp);
        fileRepertTempFile.mkdirs();
        installeEvenements();
        projetsNouveau();
        stPrimaryStage.setOnCloseRequest((WindowEvent event) -> {
            Action actReponse = null;
            Localization.setLocale(locale);
            if (!bDejaSauve) {
                actReponse = Dialogs.create()
                        .owner(null)
                        .title(rbLocalisation.getString("main.dialog.quitterApplication"))
                        .message(rbLocalisation.getString("main.dialog.chargeProjetMessage"))
                        .actions(Dialog.Actions.YES, Dialog.Actions.NO, Dialog.Actions.CANCEL)
                        .showWarning();
            }
            if (actReponse == Dialog.Actions.YES) {
                try {
                    projetSauve();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((actReponse == Dialog.Actions.YES) || (actReponse == Dialog.Actions.NO) || (actReponse == null)) {
                try {
                    sauveHistoFichiers();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                deleteDirectory(strRepertTemp);
                File fileTemp = new File(strRepertTemp);
                fileTemp.delete();
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
        launch(args);
    }
}
