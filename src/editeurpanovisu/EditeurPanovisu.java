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
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.Duration;
import jfxtras.labs.scene.control.BigDecimalField;
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

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * @return the strStyleCSS
     */
    public static String getStrStyleCSS() {
        return strStyleCSS;
    }

    /**
     * @param aStrStyleCSS the strStyleCSS to set
     */
    public static void setStrStyleCSS(String aStrStyleCSS) {
        strStyleCSS = aStrStyleCSS;
    }

    /**
     * @return the strCodesLanguesTraduction
     */
    public static String[] getStrCodesLanguesTraduction() {
        return strCodesLanguesTraduction;
    }

    /**
     * @return the strLanguesTraduction
     */
    public static String[] getStrLanguesTraduction() {
        return strLanguesTraduction;
    }

    /**
     * @return the strCurrentDir
     */
    public static String getStrCurrentDir() {
        return strCurrentDir;
    }

    /**
     * @param aStrCurrentDir the strCurrentDir to set
     */
    public static void setStrCurrentDir(String aStrCurrentDir) {
        strCurrentDir = aStrCurrentDir;
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * @param aLocale the locale to set
     */
    public static void setLocale(Locale aLocale) {
        locale = aLocale;
    }

    /**
     * @return the iNumHTML
     */
    public static int getiNumHTML() {
        return iNumHTML;
    }

    /**
     * @param aiNumHTML the iNumHTML to set
     */
    public static void setiNumHTML(int aiNumHTML) {
        iNumHTML = aiNumHTML;
    }

    /**
     * @return the panoramiquesProjet
     */
    public static Panoramique[] getPanoramiquesProjet() {
        return panoramiquesProjet;
    }

    /**
     * @param aPanoramiquesProjet the panoramiquesProjet to set
     */
    public static void setPanoramiquesProjet(Panoramique[] aPanoramiquesProjet) {
        panoramiquesProjet = aPanoramiquesProjet;
    }

    /**
     * @return the plans
     */
    public static Plan[] getPlans() {
        return plans;
    }

    /**
     * @param aPlans the plans to set
     */
    public static void setPlans(Plan[] aPlans) {
        plans = aPlans;
    }

    /**
     * @return the iNombrePanoramiques
     */
    public static int getiNombrePanoramiques() {
        return iNombrePanoramiques;
    }

    /**
     * @param aiNombrePanoramiques the iNombrePanoramiques to set
     */
    public static void setiNombrePanoramiques(int aiNombrePanoramiques) {
        iNombrePanoramiques = aiNombrePanoramiques;
    }

    /**
     * @return the iNombrePanoramiquesFichier
     */
    public static int getiNombrePanoramiquesFichier() {
        return iNombrePanoramiquesFichier;
    }

    /**
     * @param aiNombrePanoramiquesFichier the iNombrePanoramiquesFichier to set
     */
    public static void setiNombrePanoramiquesFichier(int aiNombrePanoramiquesFichier) {
        iNombrePanoramiquesFichier = aiNombrePanoramiquesFichier;
    }

    /**
     * @return the iNombrePlans
     */
    public static int getiNombrePlans() {
        return iNombrePlans;
    }

    /**
     * @param aiNombrePlans the iNombrePlans to set
     */
    public static void setiNombrePlans(int aiNombrePlans) {
        iNombrePlans = aiNombrePlans;
    }

    /**
     * @return the iPanoActuel
     */
    public static int getiPanoActuel() {
        return iPanoActuel;
    }

    /**
     * @param aiPanoActuel the iPanoActuel to set
     */
    public static void setiPanoActuel(int aiPanoActuel) {
        iPanoActuel = aiPanoActuel;
    }

    /**
     * @return the strPanoListeVignette
     */
    public static String getStrPanoListeVignette() {
        return strPanoListeVignette;
    }

    /**
     * @param aStrPanoListeVignette the strPanoListeVignette to set
     */
    public static void setStrPanoListeVignette(String aStrPanoListeVignette) {
        strPanoListeVignette = aStrPanoListeVignette;
    }

    /**
     * @return the tabPlan
     */
    public static Tab getTabPlan() {
        return tabPlan;
    }

    /**
     * @param aTabPlan the tabPlan to set
     */
    public static void setTabPlan(Tab aTabPlan) {
        tabPlan = aTabPlan;
    }

    /**
     * @return the strSystemeExploitation
     */
    public static String getStrSystemeExploitation() {
        return strSystemeExploitation;
    }

    /**
     * @param aStrSystemeExploitation the strSystemeExploitation to set
     */
    public static void setStrSystemeExploitation(String aStrSystemeExploitation) {
        strSystemeExploitation = aStrSystemeExploitation;
    }

    /**
     * @return the strTooltipStyle
     */
    public static String getStrTooltipStyle() {
        return strTooltipStyle;
    }

    /**
     * @return the strRepertAppli
     */
    public static String getStrRepertAppli() {
        return strRepertAppli;
    }

    /**
     * @param aStrRepertAppli the strRepertAppli to set
     */
    public static void setStrRepertAppli(String aStrRepertAppli) {
        strRepertAppli = aStrRepertAppli;
    }

    /**
     * @return the strRepertTemp
     */
    public static String getStrRepertTemp() {
        return strRepertTemp;
    }

    /**
     * @param aStrRepertTemp the strRepertTemp to set
     */
    public static void setStrRepertTemp(String aStrRepertTemp) {
        strRepertTemp = aStrRepertTemp;
    }

    /**
     * @return the strRepertPanos
     */
    public static String getStrRepertPanos() {
        return strRepertPanos;
    }

    /**
     * @param aStrRepertPanos the strRepertPanos to set
     */
    public static void setStrRepertPanos(String aStrRepertPanos) {
        strRepertPanos = aStrRepertPanos;
    }

    /**
     * @return the strRepertHSImages
     */
    public static String getStrRepertHSImages() {
        return strRepertHSImages;
    }

    /**
     * @param aStrRepertHSImages the strRepertHSImages to set
     */
    public static void setStrRepertHSImages(String aStrRepertHSImages) {
        strRepertHSImages = aStrRepertHSImages;
    }

    /**
     * @return the strRepertoireProjet
     */
    public static String getStrRepertoireProjet() {
        return strRepertoireProjet;
    }

    /**
     * @param aStrRepertoireProjet the strRepertoireProjet to set
     */
    public static void setStrRepertoireProjet(String aStrRepertoireProjet) {
        strRepertoireProjet = aStrRepertoireProjet;
    }

    /**
     * @return the bDejaSauve
     */
    public static boolean isbDejaSauve() {
        return bDejaSauve;
    }

    /**
     * @param abDejaSauve the bDejaSauve to set
     */
    public static void setbDejaSauve(boolean abDejaSauve) {
        bDejaSauve = abDejaSauve;
    }

    /**
     * @return the stPrincipal
     */
    public static Stage getStPrincipal() {
        return stPrincipal;
    }

    /**
     * @param aStPrincipal the stPrincipal to set
     */
    public static void setStPrincipal(Stage aStPrincipal) {
        stPrincipal = aStPrincipal;
    }

    /**
     * @return the gestionnaireInterface
     */
    public static GestionnaireInterfaceController getGestionnaireInterface() {
        return gestionnaireInterface;
    }

    /**
     * @param aGestionnaireInterface the gestionnaireInterface to set
     */
    public static void setGestionnaireInterface(GestionnaireInterfaceController aGestionnaireInterface) {
        gestionnaireInterface = aGestionnaireInterface;
    }

    /**
     * @return the gestionnairePlan
     */
    public static GestionnairePlanController getGestionnairePlan() {
        return gestionnairePlan;
    }

    /**
     * @param aGestionnairePlan the gestionnairePlan to set
     */
    public static void setGestionnairePlan(GestionnairePlanController aGestionnairePlan) {
        gestionnairePlan = aGestionnairePlan;
    }

    /**
     * @return the mniAjouterPlan
     */
    public static MenuItem getMniAjouterPlan() {
        return mniAjouterPlan;
    }

    /**
     * @param aMniAjouterPlan the mniAjouterPlan to set
     */
    public static void setMniAjouterPlan(MenuItem aMniAjouterPlan) {
        mniAjouterPlan = aMniAjouterPlan;
    }

    /**
     * @return the mniAffichagePlan
     */
    public static MenuItem getMniAffichagePlan() {
        return mniAffichagePlan;
    }

    /**
     * @param aMniAffichagePlan the mniAffichagePlan to set
     */
    public static void setMniAffichagePlan(MenuItem aMniAffichagePlan) {
        mniAffichagePlan = aMniAffichagePlan;
    }

    /**
     * @return the ivAjouterPlan
     */
    public static ImageView getIvAjouterPlan() {
        return ivAjouterPlan;
    }

    /**
     * @param aIvAjouterPlan the ivAjouterPlan to set
     */
    public static void setIvAjouterPlan(ImageView aIvAjouterPlan) {
        ivAjouterPlan = aIvAjouterPlan;
    }

    /**
     * @return the tabInterface
     */
    public static Tab getTabInterface() {
        return tabInterface;
    }

    /**
     * @param aTabInterface the tabInterface to set
     */
    public static void setTabInterface(Tab aTabInterface) {
        tabInterface = aTabInterface;
    }

    /**
     * @return the apAttends
     */
    public static AnchorPane getApAttends() {
        return apAttends;
    }

    /**
     * @param apAttends1 the apAttends to set
     */
    public static void setApAttends(AnchorPane apAttends1) {
        apAttends = apAttends1;
    }

    /**
     * @return the strBingAPIKey
     */
    public static String getStrBingAPIKey() {
        return strBingAPIKey;
    }

    /**
     * @param strBingAPIKey1
     */
    public static void setStrBingAPIKey(String strBingAPIKey1) {
        strBingAPIKey = strBingAPIKey1;
    }

    /**
     * @return the bAutoRotationDemarre
     */
    public static boolean isbAutoRotationDemarre() {
        return bAutoRotationDemarre;
    }

    /**
     * @param abAutoRotationDemarre the bAutoRotationDemarre to set
     */
    public static void setbAutoRotationDemarre(boolean abAutoRotationDemarre) {
        bAutoRotationDemarre = abAutoRotationDemarre;
    }

    /**
     * @return the iAutoRotationVitesse
     */
    public static int getiAutoRotationVitesse() {
        return iAutoRotationVitesse;
    }

    /**
     * @param aiAutoRotationVitesse the iAutoRotationVitesse to set
     */
    public static void setiAutoRotationVitesse(int aiAutoRotationVitesse) {
        iAutoRotationVitesse = aiAutoRotationVitesse;
    }

    /**
     * @return the bAutoTourDemarre
     */
    public static boolean isbAutoTourDemarre() {
        return bAutoTourDemarre;
    }

    /**
     * @param abAutoTourDemarre the bAutoTourDemarre to set
     */
    public static void setbAutoTourDemarre(boolean abAutoTourDemarre) {
        bAutoTourDemarre = abAutoTourDemarre;
    }

    /**
     * @return the iAutoTourVitesse
     */
    public static int getiAutoTourLimite() {
        return iAutoTourLimite;
    }

    /**
     * @param aiAutoTourLimite the iAutoTourVitesse to set
     */
    public static void setiAutoTourLimite(int aiAutoTourLimite) {
        iAutoTourLimite = aiAutoTourLimite;
    }

    /**
     * @return the strAutoTourType
     */
    public static String getStrAutoTourType() {
        return strAutoTourType;
    }

    /**
     * @param aStrAutoTourType the strAutoTourType to set
     */
    public static void setStrAutoTourType(String aStrAutoTourType) {
        strAutoTourType = aStrAutoTourType;
    }

    /**
     * @return the bPetitePlaneteDemarrage
     */
    public static boolean isbPetitePlaneteDemarrage() {
        return bPetitePlaneteDemarrage;
    }

    /**
     * @param abPetitePlaneteDemarrage the bPetitePlaneteDemarrage to set
     */
    public static void setbPetitePlaneteDemarrage(boolean abPetitePlaneteDemarrage) {
        bPetitePlaneteDemarrage = abPetitePlaneteDemarrage;
    }

    /**
     * @return the bIntroPetitePlanete
     */
    public static boolean isbIntroPetitePlanete() {
        return bIntroPetitePlanete;
    }

    /**
     * @param abIntroPetitePlanete the bIntroPetitePlanete to set
     */
    public static void setbIntroPetitePlanete(boolean abIntroPetitePlanete) {
        bIntroPetitePlanete = abIntroPetitePlanete;
    }

    /**
     * @return the poGeolocalisation
     */
    public static PaneOutil getPoGeolocalisation() {
        return poGeolocalisation;
    }

    /**
     * @param aPoGeolocalisation the poGeolocalisation to set
     */
    public static void setPoGeolocalisation(PaneOutil aPoGeolocalisation) {
        poGeolocalisation = aPoGeolocalisation;
    }

    /**
     * @return the bInternet
     */
    public static boolean isbInternet() {
        return bInternet;
    }

    /**
     * @param abInternet the bInternet to set
     */
    public static void setbInternet(boolean abInternet) {
        bInternet = abInternet;
    }

    private static class AncreForme extends Circle {

        private final DoubleProperty dpX, dpY;

        /**
         *
         * @param color
         * @param dpX
         * @param dpY
         */
        AncreForme(Color color, DoubleProperty dpX, DoubleProperty dpY) {
            super(dpX.get(), dpY.get(), 2);
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

        /**
         *
         */
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

        /**
         *
         */
        private class Delta {

            double x, y;
        }
    }

    /**
     * Définition de la langue locale par défaut fr_FR
     */
    private static CheckBox cbAutoRotationDemarrage;
    private static CheckBox cbIntroPetitePlanete;
    private static BigDecimalField bdfAutoRotationVitesse;
    private static CheckBox cbAutoTourDemarrage;

    private static BigDecimalField bdfAutoTourLimite;
    private static ComboBox cbAutoTourType;

    private static ComboBox cbAutoRotationVitesse;
    static private boolean bIntroPetitePlanete = false;
    static private boolean bAutoRotationDemarre = false;
    static private int iAutoRotationVitesse = 30;
    static private boolean bAutoTourDemarre = false;
    static private int iAutoTourLimite = 1;
    static private String strAutoTourType = "tours";
    static private boolean bPetitePlaneteDemarrage = true;
    static private PaneOutil poGeolocalisation;

    public static String strStyleCSS = "clair";
    private static final String[] strCodesLanguesTraduction = {"fr_FR", "en_EN", "de_DE"};
    private static final String[] strLanguesTraduction = {"Francais", "English", "Deutsch"};
    private static String strCurrentDir = "";
    private static Locale locale = new Locale("fr", "FR");
    private static ResourceBundle rbLocalisation;
    private static Label lblDragDrop;
    static private PopUpDialogController popUp;
    static private ImageView ivImagePanoramique;
    private static AnchorPane apVisuPano;
    private static Image imgPanoRetaille2;
    static private Label lblLong, lblLat;
    static private AnchorPane apPanneauPrincipal;
    static private AnchorPane apListeImagesPanoramiques;
    static private AnchorPane apVignettesPano;
    static private Rectangle rectVignettePano;
    final static private int iLargeurVignettes = 180;
    static private HBox hbCoordonnees;
    static private int iNumPoints = 0;
    static private int iNumImages = 0;
    static private boolean dejaCharge = false;
    private static int iNumHTML = 0;
    private static Panoramique[] panoramiquesProjet = new Panoramique[100];
    private static Plan[] plans = new Plan[20];
    private static int iNombrePanoramiques = 0;
    private static int iNombrePanoramiquesFichier = 0;
    static private ProgressBar pbarAvanceChargement;
    static public MenuBar mbarPrincipal;
    static public HBox hbBarreBouton;
    static public TabPane tpEnvironnement;
    static public AnchorPane apEnvironnement;
    private static int iNombrePlans = 0;
    private static int iPanoActuel = 0;
    static private File fileProjet;
    private static String strPanoListeVignette = "";
    static private Pane panePanoramique;
    static private VBox vbChoixPanoramique;
    static private VBox vbOutils;
    static private Tab tabVisite;
    static private AnchorPane apAttends;
    static private Label lblCharge;
    static private Label lblNiveaux;
    static private Tab tabInterface;
    private static Tab tabPlan;
    static private Scene scnPrincipale;
    static private ScrollPane spVuePanoramique;
    static private ScrollPane spPanneauOutils;
    static private double largeurMax;
    static private boolean bEstCharge = false;
    static private boolean bRepertSauveChoisi = false;
    static private String strPanoEntree = "";
    private static String strSystemeExploitation = "";
    private static final String strTooltipStyle = "";
    static private boolean bDragDrop = false;
    static private String strTitreVisite = "";
    private static final AnchorPane apVisuPanoramique = new AnchorPane();
    private static AnchorPane apParametresVisite;
    private static NavigateurPanoramique navigateurPanoramique;
    private static String strBingAPIKey = "";
    private static VBox vbVisuHotspots;
    private static AnchorPane apVisuHS;
    private static boolean bInternet;
    private static AnchorPane apOpenLayers;
    private static PaneOutil apHS1;
    /*
     Panel Creation Barre Navigation
     */
    private static AnchorPane apCreationBarre;
    static private AnchorPane apImgBarrePersonnalisee;
    static private AnchorPane apZoneBarrePersonnalisee;
    private static Button btnAjouteZone;
    static private Image imgBarrePersonnalisee;
    private static boolean bRecommenceZone = false;
    static private int iNombreZones;
    private static int iNombrePointsZone;
    private static double x1Zone, y1Zone;
    static private String strRepertBarrePersonnalisee = "";
    private static String strTypeZone = "";
    private static String strNomFichierShp = "";
    private static final ZoneTelecommande[] zones = new ZoneTelecommande[25];
    private static final double[] pointsPolyZone = new double[200];
    private static boolean bPleinEcranPanoramique = false;
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
    private static String strRepertAppli = "";
    private static String strRepertTemp = "";
    static private String strRepertPanos = "";
    static private String strRepertHSImages = "";
    /**
     * Répertoire de sauvegarde du projet
     */
    private static String strRepertoireProjet = "";
    /**
     * Répertoire du fichier de configuration
     */
    static public File fileRepertConfig;

    final static private ComboBox cbListeChoixPanoramique = new ComboBox();
    private static AnchorPane apListePanoTriable;
    private static final OrdrePanoramique ordPano = new OrdrePanoramique();
    static private Label lblChoixPanoramique;
    static private boolean bPanoCharge = false;
    static private String strPanoAffiche = "";
    private static boolean bDejaSauve = true;
    private static Stage stPrincipal;
    private static final String[] strHistoFichiers = new String[10];
    static private int nombreHistoFichiers = 0;
    static private File fileHistoFichiers;
    private static String strTexteHisto = "";
    public static String strNumVersion = "";
    private static int iHauteurInterface;
    private static int iLargeurInterface;
    public static NavigateurOpenLayers navigateurOpenLayers;

    private static GestionnaireInterfaceController gestionnaireInterface = new GestionnaireInterfaceController();
    private static GestionnairePlanController gestionnairePlan = new GestionnairePlanController();

    static private Menu mnuDerniersProjets;
    private static Menu mnuPanoramique;
    private static Menu mnuTransformation;
    private static Menu mnuModeles;
    private static MenuItem mniCube2EquiTransformation;
    private static MenuItem mniEqui2CubeTransformation;
    private static MenuItem mniOutilsBarre;
    private static MenuItem mniConfigTransformation;
    private static MenuItem mniChargerModele;
    private static MenuItem mniSauverModele;
    private static MenuItem mniAPropos;
    private static MenuItem mniAide;

    private static MenuItem mniNouveauProjet;
    private static MenuItem mniSauveProjet;
    private static MenuItem mniAjouterPano;
    private static MenuItem mniAjouterPlan;

    private static MenuItem mniFermerProjet;

    private static MenuItem mniSauveSousProjet;
    private static MenuItem mniVisiteGenere;
    private static MenuItem mniChargeProjet;

    private static MenuItem mniAffichageVisite;
    private static MenuItem mniAffichageInterface;
    private static MenuItem mniAffichagePlan;

    private static ImageView ivNouveauProjet;
    private static ImageView ivSauveProjet;
    private static ImageView ivChargeProjet;
    private static ImageView ivVisiteGenere;
    private static ImageView ivAjouterPano;
    private static ImageView ivAjouterPlan;
    private static ImageView ivCube2Equi;
    private static ImageView ivEqui2Cube;

    private static TextField tfLongitude;
    private static TextField tfLatitude;
    private static String strOrdrePanos;

    /**
     *
     * @throws IOException
     */
    private static void genereVisite() throws IOException {
        if (!bRepertSauveChoisi) {
            setStrRepertoireProjet(getStrCurrentDir());
        }
        if (!isbDejaSauve()) {
            projetSauve();
        }
        if (isbDejaSauve()) {
            String strHTMLRepert = getStrRepertTemp() + "/pagesHTML";
            File fileHTMLRepert = new File(strHTMLRepert);
            if (!fileHTMLRepert.exists()) {
                fileHTMLRepert.mkdirs();
            }
            deleteDirectory(getStrRepertTemp() + "/panovisu/images");
            File fileImagesRepert = new File(getStrRepertTemp() + "/panovisu/images");
            if (!fileImagesRepert.exists()) {
                fileImagesRepert.mkdirs();
            }
            File fileBoutonRepert = new File(getStrRepertTemp() + "/panovisu/images/navigation");
            if (!fileBoutonRepert.exists()) {
                fileBoutonRepert.mkdirs();
            }
            File fileBoussoleRepert = new File(getStrRepertTemp() + "/panovisu/images/boussoles");
            if (!fileBoussoleRepert.exists()) {
                fileBoussoleRepert.mkdirs();
            }
            copieDirectory(getStrRepertAppli() + File.separator + "panovisu/images/boussoles", fileBoussoleRepert.getAbsolutePath());
            File filePlanRepert = new File(getStrRepertTemp() + "/panovisu/images/plan");
            if (!filePlanRepert.exists()) {
                filePlanRepert.mkdirs();
            }
            copieDirectory(getStrRepertAppli() + File.separator + "panovisu/images/plan", filePlanRepert.getAbsolutePath());
            File fileReseauRepert = new File(getStrRepertTemp() + "/panovisu/images/reseaux");
            if (!fileReseauRepert.exists()) {
                fileReseauRepert.mkdirs();
            }
            copieDirectory(getStrRepertAppli() + File.separator + "panovisu/images/reseaux", fileReseauRepert.getAbsolutePath());
            File fileInterfaceRepert = new File(getStrRepertTemp() + "/panovisu/images/interface");
            if (!fileInterfaceRepert.exists()) {
                fileInterfaceRepert.mkdirs();
            }
            copieDirectory(getStrRepertAppli() + File.separator + "panovisu/images/interface", fileInterfaceRepert.getAbsolutePath());
            File fileMARepert = new File(getStrRepertTemp() + "/panovisu/images/MA");
            if (!fileMARepert.exists()) {
                fileMARepert.mkdirs();
            }
            File fileHotspotsRepert = new File(getStrRepertTemp() + "/panovisu/images/hotspots");
            if (!fileHotspotsRepert.exists()) {
                fileHotspotsRepert.mkdirs();
            }
            if (getGestionnaireInterface().getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                File fileTelecommandeRepert = new File(getStrRepertTemp() + "/panovisu/images/telecommande");
                if (!fileTelecommandeRepert.exists()) {
                    fileTelecommandeRepert.mkdirs();
                }
                ReadWriteImage.writePng(getGestionnaireInterface().getWiBarrePersonnaliseeCouleur(),
                        getStrRepertTemp() + "/panovisu/images/telecommande" + File.separator + "telecommande.png",
                        false, 0.f);
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "fs.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "fs2.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "souris.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "souris2.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "aide.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "info.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "rotation.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "zoomin.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
                copieFichierRepertoire(getStrRepertAppli() + "/theme/telecommandes" + File.separator + "zoomout.png",
                        getStrRepertTemp() + "/panovisu/images/telecommande");
            }
            copieFichierRepertoire(getStrRepertAppli() + File.separator + "panovisu" + File.separator + "images" + File.separator + "aide_souris.png",
                    getStrRepertTemp() + "/panovisu/images");
            copieFichierRepertoire(getStrRepertAppli() + File.separator + "panovisu" + File.separator + "images" + File.separator + "fermer.png",
                    getStrRepertTemp() + "/panovisu/images");
            copieFichierRepertoire(getStrRepertAppli() + File.separator + "panovisu" + File.separator + "images" + File.separator + "precedent.png",
                    getStrRepertTemp() + "/panovisu/images");
            copieFichierRepertoire(getStrRepertAppli() + File.separator + "panovisu" + File.separator + "images" + File.separator + "suivant.png",
                    getStrRepertTemp() + "/panovisu/images");
            for (int i = 0; i < getGestionnaireInterface().getiNombreImagesBouton() - 2; i++) {
                ReadWriteImage.writePng(getGestionnaireInterface().getWiNouveauxBoutons()[i],
                        fileBoutonRepert.getAbsolutePath() + File.separator + getGestionnaireInterface().getStrNomImagesBoutons()[i],
                        false, 0.f);
            }
            ReadWriteImage.writePng(getGestionnaireInterface().getWiNouveauxBoutons()[getGestionnaireInterface().getiNombreImagesBouton() - 2],
                    fileHotspotsRepert.getAbsolutePath() + File.separator + "hotspot.png",
                    false, 0.f);
            ReadWriteImage.writePng(getGestionnaireInterface().getWiNouveauxBoutons()[getGestionnaireInterface().getiNombreImagesBouton() - 1],
                    fileHotspotsRepert.getAbsolutePath() + File.separator + "hotspotImage.png",
                    false, 0.f);
            ReadWriteImage.writePng(getGestionnaireInterface().getWiNouveauxBoutons()[getGestionnaireInterface().getiNombreImagesBouton()],
                    fileHotspotsRepert.getAbsolutePath() + File.separator + "hotspotHTML.png",
                    false, 0.f);
            ReadWriteImage.writePng(getGestionnaireInterface().getWiNouveauxMasque(),
                    fileMARepert.getAbsolutePath() + File.separator + "MA.png",
                    false, 0.f);

            if (getGestionnaireInterface().isbAfficheCarte()) {
                File filemarqueursOL = new File(getStrRepertTemp() + "/panovisu/images/marqueursOL");
                if (!filemarqueursOL.exists()) {
                    filemarqueursOL.mkdirs();
                }
                copieFichierRepertoire(getStrRepertAppli() + File.separator + "theme" + File.separator + "marqueursOL" + File.separator + "marqueur.png", getStrRepertTemp() + File.separator + "panovisu/images/marqueursOL");
                copieFichierRepertoire(getStrRepertAppli() + File.separator + "theme" + File.separator + "marqueursOL" + File.separator + "home.png", getStrRepertTemp() + File.separator + "panovisu/images/marqueursOL");
                copieFichierRepertoire(getStrRepertAppli() + File.separator + "theme" + File.separator + "marqueursOL" + File.separator + "marqueurActif.png", getStrRepertTemp() + File.separator + "panovisu/images/marqueursOL");
            }
            if (getGestionnaireInterface().isbAfficheBoutonVisiteAuto()) {
                File filemarqueursAT = new File(getStrRepertTemp() + "/panovisu/images/visiteAutomatique");
                if (!filemarqueursAT.exists()) {
                    filemarqueursAT.mkdirs();
                }
                copieFichierRepertoire(getStrRepertAppli() + File.separator + "theme" + File.separator + "visiteAutomatique" + File.separator + "playAutoTour.png", getStrRepertTemp() + File.separator + "panovisu/images/visiteAutomatique");
                copieFichierRepertoire(getStrRepertAppli() + File.separator + "theme" + File.separator + "visiteAutomatique" + File.separator + "pauseAutoTour.png", getStrRepertTemp() + File.separator + "panovisu/images/visiteAutomatique");
            }
            File fileXMLRepert = new File(getStrRepertTemp() + File.separator + "xml");
            if (!fileXMLRepert.exists()) {
                fileXMLRepert.mkdirs();
            }
            File fileCSSRepert = new File(getStrRepertTemp() + File.separator + "css");
            if (!fileCSSRepert.exists()) {
                fileCSSRepert.mkdirs();
            }
            File fileJsRepert = new File(getStrRepertTemp() + File.separator + "js");
            if (!fileJsRepert.exists()) {
                fileJsRepert.mkdirs();
            }
            String strContenuFichier;
            File fileXML;
            String strChargeImages = "";
            int iPanoEntree = Integer.parseInt(ordPano.getStrPanos().get(0));
            strPanoEntree = getPanoramiquesProjet()[iPanoEntree].getStrNomFichier()
                    .substring(getPanoramiquesProjet()[iPanoEntree].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPanoEntree].getStrNomFichier().length())
                    .split("\\.")[0];
            for (int ii = 0; ii < ordPano.getStrPanos().size(); ii++) {
                int i = Integer.parseInt(ordPano.getStrPanos().get(ii));
                String strHTMLPanoRepert = strHTMLRepert + "/" + i;
                File fileHTMLPanoRepert = new File(strHTMLPanoRepert);
                if (!fileHTMLPanoRepert.exists()) {
                    fileHTMLPanoRepert.mkdirs();
                }
                String strFichierPano = "panos/" + getPanoramiquesProjet()[i].getStrNomFichier().substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length()).split("\\.")[0];
                if (getPanoramiquesProjet()[i].getStrTypePanoramique().equals(Panoramique.CUBE)) {
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
                String strIntroPetitePlanete = (isbIntroPetitePlanete()) ? "oui" : "non";
                String strAutorotation = (isbAutoRotationDemarre()) ? "oui" : "non";
                String strAutoTour = (isbAutoTourDemarre()) ? "oui" : "non";
                String strAutoTourBouton = (getGestionnaireInterface().isbAfficheBoutonVisiteAuto()) ? "oui" : "non";
                String strAffInfo = (getPanoramiquesProjet()[i].isAfficheInfo()) ? "oui" : "non";
                String strAffTitre = (getGestionnaireInterface().isbAfficheTitre()) ? "oui" : "non";
                double regX;
                double zN;
                if (getPanoramiquesProjet()[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
                    regX = Math.round(((getPanoramiquesProjet()[i].getRegardX() - 180) % 360) * 10) / 10;
                    zN = Math.round(((getPanoramiquesProjet()[i].getZeroNord() - 180) % 360) * 10) / 10;
                } else {
                    regX = Math.round(((getPanoramiquesProjet()[i].getRegardX() + 90) % 360) * 10) / 10;
                    zN = Math.round(((getPanoramiquesProjet()[i].getZeroNord() + 90) % 360) * 10) / 10;
                }
                int iRouge = (int) (Color.valueOf(getGestionnaireInterface().getStrCouleurDiaporama()).getRed() * 255.d);
                int iBleu = (int) (Color.valueOf(getGestionnaireInterface().getStrCouleurDiaporama()).getBlue() * 255.d);
                int iVert = (int) (Color.valueOf(getGestionnaireInterface().getStrCouleurDiaporama()).getGreen() * 255.d);
                String strCoulDiapo = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getGestionnaireInterface().getDiaporamaOpacite() + ")";

                Color coulTitre = Color.valueOf(getGestionnaireInterface().getStrCouleurFondTitre());
                iRouge = (int) (coulTitre.getRed() * 255.d);
                iBleu = (int) (coulTitre.getBlue() * 255.d);
                iVert = (int) (coulTitre.getGreen() * 255.d);
                String strCoulFondTitre = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getGestionnaireInterface().getTitreOpacite() + ")";

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
                        + "      titre=\"" + getPanoramiquesProjet()[i].getStrTitrePanoramique() + "\"\n"
                        + "      titrePolice=\"" + getGestionnaireInterface().getStrTitrePoliceNom() + "\"\n"
                        + "      titreTaillePolice=\"" + Math.round(Double.parseDouble(getGestionnaireInterface().getStrTitrePoliceTaille())) + "px\"\n"
                        + "      titreTaille=\"" + Math.round(getGestionnaireInterface().getTitreTaille()) + "%\"\n"
                        + "      titreFond=\"" + strCoulFondTitre + "\"\n"
                        + "      titreCouleur=\"" + getGestionnaireInterface().getStrCouleurTitre() + "\"\n"
                        + "      diaporamaCouleur=\"" + strCoulDiapo + "\"\n"
                        + "      type=\"" + getPanoramiquesProjet()[i].getStrTypePanoramique() + "\"\n"
                        + "      multiReso=\"oui\"\n"
                        + "      nombreNiveaux=\"" + getPanoramiquesProjet()[i].getNombreNiveaux() + "\"\n"
                        + "      zeroNord=\"" + zN + "\"\n"
                        + "      regardX=\"" + regX + "\"\n"
                        + "      regardY=\"" + Math.round(getPanoramiquesProjet()[i].getRegardY() * 10) / 10 + "\"\n"
                        + "      autorotation=\"" + strAutorotation + "\"\n"
                        + "      vitesseAR=\"" + getiAutoRotationVitesse() + "\"\n"
                        + "      autotour=\"" + strAutoTour + "\"\n"
                        + "      atBouton=\"" + strAutoTourBouton + "\"\n"
                        + "      atBoutonTaille=\"" + getGestionnaireInterface().getTailleBoutonVisiteAuto() + "\"\n"
                        + "      atBoutonPositionX=\"" + getGestionnaireInterface().getStrPositionXBoutonVisiteAuto() + "\"\n"
                        + "      atBoutonPositionY=\"" + getGestionnaireInterface().getStrPositionYBoutonVisiteAuto() + "\"\n"
                        + "      atBoutonOffsetX=\"" + getGestionnaireInterface().getOffsetXBoutonVisiteAuto() + "\"\n"
                        + "      atBoutonOffsetY=\"" + getGestionnaireInterface().getOffsetYBoutonVisiteAuto() + "\"\n"
                        + "      typeAT=\"" + getStrAutoTourType() + "\"\n"
                        + "      limiteAT=\"" + getiAutoTourLimite() + "\"\n"
                        + "      affinfo=\"" + strAffInfo + "\"\n"
                        + "      afftitre=\"" + strAffTitre + "\"\n";
                if (getPanoramiquesProjet()[i].getStrNomFichier()
                        .substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length())
                        .split("\\.")[0].equals(strPanoEntree) && isbPetitePlaneteDemarrage()) {
                    strContenuFichier += "      petitePlanete=\"" + strIntroPetitePlanete + "\"\n";
                }
                if (getPanoramiquesProjet()[i].getStrNomFichier()
                        .substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length())
                        .split("\\.")[0].equals(strPanoEntree) && getGestionnaireInterface().isbReplieDemarragePlan()) {
                    strContenuFichier += "      repliePlan=\"oui\"\n";
                }
                if (getPanoramiquesProjet()[i].getStrNomFichier()
                        .substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length())
                        .split("\\.")[0].equals(strPanoEntree) && getGestionnaireInterface().isbReplieDemarrageCarte()) {
                    strContenuFichier += "      replieCarte=\"oui\"\n";
                }
                if (getPanoramiquesProjet()[i].getStrNomFichier()
                        .substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length())
                        .split("\\.")[0].equals(strPanoEntree) && getGestionnaireInterface().isbReplieDemarrageVignettes()) {
                    strContenuFichier += "      replieVignettes=\"oui\"\n";
                }

                strContenuFichier += "   />\n";
                if (getGestionnaireInterface().getStrVisibiliteBarreClassique().equals("oui")) {
                    strContenuFichier
                            += "   <!--Définition de la Barre de navigation-->\n"
                            + "   <boutons \n"
                            + "      styleBoutons=\"navigation\"\n"
                            + "      couleur=\"rgba(255,255,255,0)\"\n"
                            + "      bordure=\"rgba(255,255,255,0)\"\n"
                            + "      deplacements=\"" + getGestionnaireInterface().getStrDeplacementsBarreClassique() + "\" \n"
                            + "      zoom=\"" + getGestionnaireInterface().getStrZoomBarreClassique() + "\" \n"
                            + "      outils=\"" + getGestionnaireInterface().getStrOutilsBarreClassique() + "\"\n"
                            + "      fs=\"" + getGestionnaireInterface().getStrPleinEcranBarreClassique() + "\" \n"
                            + "      souris=\"" + getGestionnaireInterface().getStrSourisBarreClassique() + "\" \n"
                            + "      rotation=\"" + getGestionnaireInterface().getStrRotationBarreClassique() + "\" \n"
                            + "      positionX=\"" + getGestionnaireInterface().getStrPositionBarreClassique().split(":")[1] + "\"\n"
                            + "      positionY=\"" + getGestionnaireInterface().getStrPositionBarreClassique().split(":")[0] + "\" \n"
                            + "      dX=\"" + getGestionnaireInterface().getOffsetXBarreClassique() + "\" \n"
                            + "      dY=\"" + getGestionnaireInterface().getOffsetYBarreClassique() + "\"\n"
                            + "      espacement=\"" + Math.round(getGestionnaireInterface().getEspacementBarreClassique()) + "\"\n"
                            + "      visible=\"" + getGestionnaireInterface().getStrVisibiliteBarreClassique() + "\"\n"
                            + "   />\n";
                }
                if (getGestionnaireInterface().getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                    strContenuFichier
                            += "<!--  Barre de Navigation Personnalisée -->\n\n"
                            + "    <telecommande\n"
                            + "        fs=\"" + getGestionnaireInterface().getStrPleinEcranBarrePersonnalisee() + "\" \n"
                            + "        souris=\"" + getGestionnaireInterface().getStrSourisBarrePersonnalisee() + "\" \n"
                            + "        rotation=\"" + getGestionnaireInterface().getStrRotationBarrePersonnalisee() + "\" \n"
                            + "        info=\"" + getGestionnaireInterface().getStrInfoBarrePersonnalisee() + "\"\n"
                            + "        aide=\"" + getGestionnaireInterface().getStrAideBarrePersonnalisee() + "\"\n"
                            + "        positionX=\"" + getGestionnaireInterface().getStrPositionBarrePersonnalisee().split(":")[1] + "\"\n"
                            + "        positionY=\"" + getGestionnaireInterface().getStrPositionBarrePersonnalisee().split(":")[0] + "\" \n"
                            + "        taille=\"" + getGestionnaireInterface().getTailleBarrePersonnalisee() + "\"\n"
                            + "        tailleBouton=\"" + getGestionnaireInterface().getTailleIconesBarrePersonnalisee() + "\"\n"
                            + "        dX=\"" + getGestionnaireInterface().getOffsetXBarrePersonnalisee() + "\" \n"
                            + "        dY=\"" + getGestionnaireInterface().getOffsetYBarrePersonnalisee() + "\"\n"
                            + "        lien1=\"" + getGestionnaireInterface().getStrLien1BarrePersonnalisee() + "\"\n"
                            + "        lien2=\"" + getGestionnaireInterface().getStrLien2BarrePersonnalisee() + "\"\n"
                            + "        visible=\"oui\">\n";
                    for (int ij = 0; ij < getGestionnaireInterface().getiNombreZonesBarrePersonnalisee(); ij++) {
                        strContenuFichier += "        <zoneNavPerso "
                                + "id=\"" + getGestionnaireInterface().getZonesBarrePersonnalisee()[ij].getStrIdZone() + "\" "
                                + "alt=\"\" title=\"\" "
                                + "shape=\"" + getGestionnaireInterface().getZonesBarrePersonnalisee()[ij].getStrTypeZone() + "\" "
                                + "coords=\"" + getGestionnaireInterface().getZonesBarrePersonnalisee()[ij].getStrCoordonneesZone() + "\""
                                + " />\n";
                    }
                    strContenuFichier += "    </telecommande>\n"
                            + "";
                }
                if (getGestionnaireInterface().isbAfficheBoussole()) {
                    String strAiguille = (getGestionnaireInterface().isbAiguilleMobileBoussole()) ? "oui" : "non";
                    strContenuFichier += "<!--  Boussole -->\n"
                            + "    <boussole \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + getGestionnaireInterface().getStrImageBoussole() + "\"\n"
                            + "        taille=\"" + getGestionnaireInterface().getTailleBoussole() + "\"\n"
                            + "        positionY=\"" + getGestionnaireInterface().getStrPositionBoussole().split(":")[0] + "\"\n"
                            + "        positionX=\"" + getGestionnaireInterface().getStrPositionBoussole().split(":")[1] + "\"\n"
                            + "        opacite=\"" + getGestionnaireInterface().getOpaciteBoussole() + "\"\n"
                            + "        dX=\"" + getGestionnaireInterface().getOffsetXBoussole() + "\"\n"
                            + "        dY=\"" + getGestionnaireInterface().getOffsetYBoussole() + "\"\n"
                            + "        aiguille=\"" + strAiguille + "\"\n"
                            + "    />\n";
                }
                if (getGestionnaireInterface().isbFenetreInfoPersonnalise()) {
                    String strImgInfoURL = "images/" + getGestionnaireInterface().getStrFenetreInfoImage().substring(getGestionnaireInterface().getStrFenetreInfoImage().lastIndexOf(File.separator) + 1,
                            getGestionnaireInterface().getStrFenetreInfoImage().length());
                    strContenuFichier += "<!--  Fenêtre info personnalisée -->\n"
                            + "    <fenetreInfo \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgInfoURL + "\"\n"
                            + "        taille=\"" + getGestionnaireInterface().getFenetreInfoTaille() + "\"\n"
                            + "        opacite=\"" + getGestionnaireInterface().getFenetreInfoOpacite() + "\"\n"
                            + "        dX=\"" + Math.round(getGestionnaireInterface().getFenetreInfoPosX() * 10) / 10 + "\"\n"
                            + "        dY=\"" + Math.round(getGestionnaireInterface().getFenetreInfoPosY() * 10) / 10 + "\"\n"
                            + "        URL=\"" + getGestionnaireInterface().getStrFenetreURL() + "\"\n"
                            + "        texteURL=\"" + getGestionnaireInterface().getStrFenetreTexteURL() + "\"\n"
                            + "        couleurURL=\"" + getGestionnaireInterface().getStrFenetreURLCouleur() + "\"\n"
                            + "        TailleURL=\"" + Math.round(getGestionnaireInterface().getFenetrePoliceTaille() * 10) / 10 + "\"\n"
                            + "        URLdX=\"" + Math.round(getGestionnaireInterface().getFenetreURLPosX() * 10) / 10 + "\"\n"
                            + "        URLdY=\"" + Math.round(getGestionnaireInterface().getFenetreURLPosY() * 10) / 10 + "\"\n"
                            + "    />\n";
                }
                if (getGestionnaireInterface().isbFenetreAidePersonnalise()) {
                    String strImgAideURL = "images/" + getGestionnaireInterface().getStrFenetreAideImage().substring(getGestionnaireInterface().getStrFenetreAideImage().lastIndexOf(File.separator) + 1,
                            getGestionnaireInterface().getStrFenetreAideImage().length());
                    strContenuFichier += "<!--  Fenêtre Aide personnalisée -->\n"
                            + "    <fenetreAide \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + strImgAideURL + "\"\n"
                            + "        taille=\"" + getGestionnaireInterface().getFenetreAideTaille() + "\"\n"
                            + "        opacite=\"" + getGestionnaireInterface().getFenetreAideOpacite() + "\"\n"
                            + "        dX=\"" + getGestionnaireInterface().getFenetreAidePosX() + "\"\n"
                            + "        dY=\"" + getGestionnaireInterface().getFenetreAidePosY() + "\"\n"
                            + "    />\n";
                }
                if (getGestionnaireInterface().isbAfficheMenuContextuel()) {
                    String strPrecSuiv = (getGestionnaireInterface().isbAffichePrecSuivMC()) ? "oui" : "non";
                    String strPlanet = (getGestionnaireInterface().isbAffichePlanetNormalMC()) ? "oui" : "non";
                    String strPers1 = (getGestionnaireInterface().isbAffichePersMC1()) ? "oui" : "non";
                    String strPers2 = (getGestionnaireInterface().isbAffichePersMC2()) ? "oui" : "non";
                    strContenuFichier += "<!--  MenuContextuel -->\n"
                            + "    <menuContextuel \n"
                            + "        affiche=\"oui\"\n"
                            + "        precSuiv=\"" + strPrecSuiv + "\"\n"
                            + "        planete=\"" + strPlanet + "\"\n"
                            + "        pers1=\"" + strPers1 + "\"\n"
                            + "        lib1=\"" + getGestionnaireInterface().getStrPersLib1() + "\"\n"
                            + "        url1=\"" + getGestionnaireInterface().getStrPersURL1() + "\"\n"
                            + "        pers2=\"" + strPers2 + "\"\n"
                            + "        lib2=\"" + getGestionnaireInterface().getStrPersLib2() + "\"\n"
                            + "        url2=\"" + getGestionnaireInterface().getStrPersURL2() + "\"\n"
                            + "    />\n";
                }

                int iPanoSuivant = (ii == ordPano.getStrPanos().size() - 1) ? 0 : ii + 1;
                int iPanoPrecedent = (ii == 0) ? ordPano.getStrPanos().size() - 1 : ii - 1;
                String strNomPano = getPanoramiquesProjet()[Integer.parseInt(ordPano.getStrPanos().get(iPanoSuivant))].getStrNomFichier();
                String strPanoSuivant = "xml/" + strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                strNomPano = getPanoramiquesProjet()[Integer.parseInt(ordPano.getStrPanos().get(iPanoPrecedent))].getStrNomFichier();
                String strPanoPrecedent = "xml/" + strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")) + ".xml";
                strContenuFichier += "<!--  Bouton Suivant Precedent -->\n"
                        + "    <suivantPrecedent\n"
                        + "        suivant=\"" + strPanoSuivant + "\"            \n"
                        + "        precedent=\"" + strPanoPrecedent + "\"            \n";
                if (getGestionnaireInterface().isbSuivantPrecedent()) {
                    strContenuFichier += "        afficheSuivantPrecedent=\"oui\"            \n";

                }
                strContenuFichier += "    />    \n"
                        + "";
                String strbAffichemasque = (getGestionnaireInterface().isbAfficheMasque()) ? "oui" : "non";
                String strNavigation = (getGestionnaireInterface().isbMasqueNavigation()) ? "oui" : "non";
                String strBoussole = (getGestionnaireInterface().isbMasqueBoussole()) ? "oui" : "non";
                String strTitre = (getGestionnaireInterface().isbMasqueTitre()) ? "oui" : "non";
                String strPlan = (getGestionnaireInterface().isbMasquePlan()) ? "oui" : "non";
                String strReseaux = (getGestionnaireInterface().isbMasqueReseaux()) ? "oui" : "non";
                String strVignettes = (getGestionnaireInterface().isbMasqueVignettes()) ? "oui" : "non";
                String strCombo = (getGestionnaireInterface().isbMasqueCombo()) ? "oui" : "non";
                String strSuivPrec = (getGestionnaireInterface().isbMasqueSuivPrec()) ? "oui" : "non";
                String strHotspots = (getGestionnaireInterface().isbMasqueHotspots()) ? "oui" : "non";
                strContenuFichier += "<!--  Bouton de Masquage -->\n"
                        + "    <marcheArret \n"
                        + "        affiche=\"" + strbAffichemasque + "\"\n"
                        + "        image=\"MA.png\"\n"
                        + "        taille=\"" + getGestionnaireInterface().getTailleMasque() + "\"\n"
                        + "        positionY=\"" + getGestionnaireInterface().getStrPositionMasque().split(":")[0] + "\"\n"
                        + "        positionX=\"" + getGestionnaireInterface().getStrPositionMasque().split(":")[1] + "\"\n"
                        + "        opacite=\"" + getGestionnaireInterface().getOpaciteMasque() + "\"\n"
                        + "        dX=\"" + getGestionnaireInterface().getdXMasque() + "\"\n"
                        + "        dY=\"" + getGestionnaireInterface().getdYMasque() + "\"\n"
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

                if (getGestionnaireInterface().isbAfficheReseauxSociaux()) {
                    String strTwitter = (getGestionnaireInterface().isbReseauxSociauxTwitter()) ? "oui" : "non";
                    String strGoogle = (getGestionnaireInterface().isbReseauxSociauxGoogle()) ? "oui" : "non";
                    String strFacebook = (getGestionnaireInterface().isbReseauxSociauxFacebook()) ? "oui" : "non";
                    String strEmail = (getGestionnaireInterface().isbReseauxSociauxEmail()) ? "oui" : "non";
                    strContenuFichier += "<!--  Réseaux Sociaux -->\n"
                            + "    <reseauxSociaux \n"
                            + "        affiche=\"oui\"\n"
                            + "        taille=\"" + getGestionnaireInterface().getTailleReseauxSociaux() + "\"\n"
                            + "        positionY=\"" + getGestionnaireInterface().getStrPositionReseauxSociaux().split(":")[0] + "\"\n"
                            + "        positionX=\"" + getGestionnaireInterface().getStrPositionReseauxSociaux().split(":")[1] + "\"\n"
                            + "        opacite=\"" + getGestionnaireInterface().getOpaciteReseauxSociaux() + "\"\n"
                            + "        dX=\"" + getGestionnaireInterface().getdXReseauxSociaux() + "\"\n"
                            + "        dY=\"" + getGestionnaireInterface().getdYReseauxSociaux() + "\"\n"
                            + "        twitter=\"" + strTwitter + "\"\n"
                            + "        google=\"" + strGoogle + "\"\n"
                            + "        facebook=\"" + strFacebook + "\"\n"
                            + "        email=\"" + strEmail + "\"\n"
                            + "    />\n";
                }
                if (getGestionnaireInterface().isbAfficheVignettes()) {
                    String strAfficheVignettes = (getGestionnaireInterface().isbAfficheVignettes()) ? "oui" : "non";
                    strContenuFichier += "<!-- Barre des vignettes -->"
                            + "    <vignettes \n"
                            + "        affiche=\"" + strAfficheVignettes + "\"\n"
                            + "        tailleImage=\"" + getGestionnaireInterface().getTailleImageVignettes() + "\"\n"
                            + "        fondCouleur=\"" + getGestionnaireInterface().getStrCouleurFondVignettes() + "\"\n"
                            + "        texteCouleur=\"" + getGestionnaireInterface().getStrCouleurTexteVignettes() + "\"\n"
                            + "        opacite=\"" + getGestionnaireInterface().getOpaciteVignettes() + "\"\n"
                            + "        position=\"" + getGestionnaireInterface().getStrPositionVignettes() + "\"\n"
                            + "    >\n";
                    for (int jj = 0; jj < ordPano.getStrPanos().size(); jj++) {
                        int j = Integer.parseInt(ordPano.getStrPanos().get(jj));
                        String strNomPan1 = getPanoramiquesProjet()[j].getStrNomFichier();
                        String strFichier = strNomPan1.substring(strNomPan1.lastIndexOf(File.separator) + 1, strNomPan1.lastIndexOf(".")) + "Vignette.jpg";
                        String strXML = strNomPan1.substring(strNomPan1.lastIndexOf(File.separator) + 1, strNomPan1.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(getPanoramiquesProjet()[j].getImgVignettePanoramique(),
                                getStrRepertTemp() + "/panos/" + strFichier, 1.0f, false, 0.0f);
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
                if (getGestionnaireInterface().isbAfficheComboMenu()) {
                    String strAfficheComboMenu = (getGestionnaireInterface().isbAfficheComboMenu()) ? "oui" : "non";
                    TextField tfTitreVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
                    String strTitreVisite = tfTitreVisite.getText();
                    strContenuFichier += "<!-- Barre des comboMenu -->"
                            + "    <comboMenu \n"
                            + "        affiche=\"" + strAfficheComboMenu + "\"\n"
                            + "        positionX=\"" + getGestionnaireInterface().getStrPositionXComboMenu() + "\"\n"
                            + "        positionY=\"" + getGestionnaireInterface().getStrPositionYComboMenu() + "\"\n"
                            + "        dX=\"" + getGestionnaireInterface().getOffsetXComboMenu() + "\"\n"
                            + "        dY=\"" + getGestionnaireInterface().getOffsetYComboMenu() + "\"\n"
                            + "    >\n";
                    for (int jj = 0; jj < ordPano.getStrPanos().size(); jj++) {
                        int j = Integer.parseInt(ordPano.getStrPanos().get(jj));
                        String strNomPan2 = getPanoramiquesProjet()[j].getStrNomFichier();
                        String strFichier = strNomPan2.substring(strNomPan2.lastIndexOf(File.separator) + 1, strNomPan2.lastIndexOf(".")) + "Vignette.jpg";
                        String strXML = strNomPan2.substring(strNomPan2.lastIndexOf(File.separator) + 1, strNomPan2.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(getPanoramiquesProjet()[j].getImgVignettePanoramique(),
                                getStrRepertTemp() + "/panos/" + strFichier, 1.0f, false, 0.0f);

                        strContenuFichier
                                += "        <imageComboMenu \n";
                        if (getGestionnaireInterface().isbAfficheComboMenuImages()) {
                            strContenuFichier
                                    += "            image=\"panos/" + strFichier + "\"\n";
                        }
                        if (i == j) {
                            strContenuFichier
                                    += "            selectionne=\"selected\"\n";
                        }
                        strContenuFichier
                                += "            xml=\"xml/" + strXML + "\"\n"
                                + "            titre=\"" + getPanoramiquesProjet()[j].getStrTitrePanoramique() + "\"\n"
                                + "            sousTitre=\"" + strTitreVisite + "\"\n"
                                + "        />\n";
                    }
                    strContenuFichier
                            += "    </comboMenu>       \n"
                            + "";

                }

                strContenuFichier += "    <!--Définition des hotspots-->  \n"
                        + "   <hotspots>\n";
                for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspots(); j++) {
                    HotSpot HS = getPanoramiquesProjet()[i].getHotspot(j);
                    double longit;
                    if (getPanoramiquesProjet()[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
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
                for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspotHTML(); j++) {
                    HotspotHTML HS = getPanoramiquesProjet()[i].getHotspotHTML(j);
                    String strUrlHTML;
                    if (!HS.isbLienExterieur()) {
                        HS.creeHTML(strHTMLPanoRepert + "/page" + j);
                        strUrlHTML = "pagesHTML/" + i + "/page" + j + "/index.html";
                    } else {
                        strUrlHTML = HS.getStrURLExterieure();
                    }
                    Color coulTitreHTML = Color.valueOf(HS.getStrCouleurHTML());
                    iRouge = (int) (coulTitreHTML.getRed() * 255.d);
                    iBleu = (int) (coulTitreHTML.getBlue() * 255.d);
                    iVert = (int) (coulTitreHTML.getGreen() * 255.d);
                    String strCoulHTML = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + Math.round(HS.getOpaciteHTML() * 100.d) / 100.d + ")";
                    double longit;
                    if (getPanoramiquesProjet()[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
                        longit = HS.getLongitude() - 180;
                    } else {
                        longit = HS.getLongitude() + 90;
                    }
                    String strAnime = (HS.isAnime()) ? "true" : "false";

                    strContenuFichier
                            += "      <point \n"
                            + "           type=\"html\"\n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspotHTML.png\"\n"
                            + "           url=\"" + strUrlHTML + "\"\n"
                            + "           info=\"" + HS.getStrInfo() + "\"\n"
                            + "           taille=\"" + Math.round(HS.getLargeurHTML()) + "px\"\n"
                            + "           position=\"" + HS.getStrPositionHTML() + "\"\n"
                            + "           couleur=\"" + strCoulHTML + "\"\n"
                            + "           anime=\"" + strAnime + "\"\n"
                            + "      />\n";
                }

                for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspotImage(); j++) {
                    HotspotImage HS = getPanoramiquesProjet()[i].getHotspotImage(j);
                    double longit;
                    if (getPanoramiquesProjet()[i].getStrTypePanoramique().equals(Panoramique.SPHERE)) {
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
                if (getGestionnaireInterface().getiNombreImagesFond() > 0) {
                    for (int k = 0; k < getGestionnaireInterface().getiNombreImagesFond(); k++) {
                        ImageFond imgFond = getGestionnaireInterface().getImagesFond()[k];
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
                if (getGestionnaireInterface().isbAffichePlan() && getPanoramiquesProjet()[i].isAffichePlan()) {
                    int iNumPlan = getPanoramiquesProjet()[i].getNumeroPlan();
                    Plan planPano = getPlans()[iNumPlan];
                    iRouge = (int) (getGestionnaireInterface().getCouleurFondPlan().getRed() * 255.d);
                    iBleu = (int) (getGestionnaireInterface().getCouleurFondPlan().getBlue() * 255.d);
                    iVert = (int) (getGestionnaireInterface().getCouleurFondPlan().getGreen() * 255.d);
                    String strAfficheRadar = (getGestionnaireInterface().isbAfficheRadar()) ? "oui" : "non";
                    String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getGestionnaireInterface().getOpacitePlan() + ")";
                    strContenuFichier
                            += "    <plan\n"
                            + "        affiche=\"oui\" \n"
                            + "        image=\"images/" + planPano.getStrImagePlan() + "\"\n"
                            + "        largeur=\"" + getGestionnaireInterface().getLargeurPlan() + "\"\n"
                            + "        position=\"" + getGestionnaireInterface().getStrPositionPlan() + "\"\n"
                            + "        couleurFond=\"" + strCoulFond + "\"\n"
                            + "        couleurTexte=\"#" + getGestionnaireInterface().getStrCouleurTextePlan() + "\"\n"
                            + "        nord=\"" + planPano.getDirectionNord() + "\"\n"
                            + "        boussolePosition=\"" + planPano.getStrPosition() + "\"\n"
                            + "        boussoleX=\"" + planPano.getPositionX() + "\"\n"
                            + "        boussoleY=\"" + planPano.getPositionX() + "\"\n"
                            + "        radarAffiche=\"" + strAfficheRadar + "\"\n"
                            + "        radarTaille=\"" + Math.round(getGestionnaireInterface().getTailleRadar()) + "\"\n"
                            + "        radarCouleurFond=\"#" + getGestionnaireInterface().getStrCouleurFondRadar() + "\"\n"
                            + "        radarCouleurLigne=\"#" + getGestionnaireInterface().getStrCouleurLigneRadar() + "\"\n"
                            + "        radarOpacite=\"" + Math.round(getGestionnaireInterface().getOpaciteRadar() * 100.d) / 100.d + "\"\n"
                            + "    >\n";
                    for (int iPoint = 0; iPoint < planPano.getNombreHotspots(); iPoint++) {
                        double posX = planPano.getHotspot(iPoint).getLongitude() * getGestionnaireInterface().getLargeurPlan();
                        double posY = planPano.getHotspot(iPoint).getLatitude()
                                * planPano.getHauteurPlan() * getGestionnaireInterface().getLargeurPlan() / planPano.getLargeurPlan();
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

                if (getGestionnaireInterface().isbAfficheCarte()) {
                    iRouge = (int) (getGestionnaireInterface().getCouleurFondCarte().getRed() * 255.d);
                    iBleu = (int) (getGestionnaireInterface().getCouleurFondCarte().getBlue() * 255.d);
                    iVert = (int) (getGestionnaireInterface().getCouleurFondCarte().getGreen() * 255.d);
                    String strAfficheRadarCarte = (getGestionnaireInterface().isbAfficheRadarCarte()) ? "oui" : "non";
                    String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getGestionnaireInterface().getOpaciteCarte() + ")";
                    CoordonneesGeographiques coords;
                    if (getGestionnaireInterface().getCoordCentreCarte() != null) {
                        coords = getGestionnaireInterface().getCoordCentreCarte();
                    } else {
                        coords = getGestionnaireInterface().navigateurCarteOL.recupereCoordonnees();
                    }
                    strContenuFichier
                            += "    <carte\n"
                            + "        affiche=\"oui\" \n"
                            + "        largeur=\"" + getGestionnaireInterface().getLargeurCarte() + "\"\n"
                            + "        hauteur=\"" + getGestionnaireInterface().getHauteurCarte() + "\"\n"
                            + "        zoom=\"" + (getGestionnaireInterface().getiFacteurZoomCarte() + 1) + "\"\n"
                            + "        coordCentreLong=\"" + coords.getLongitude() + "\"\n"
                            + "        coordCentreLat=\"" + coords.getLatitude() + "\"\n"
                            + "        nomLayer=\"" + getGestionnaireInterface().getStrNomLayers() + "\"\n"
                            + "        position=\"" + getGestionnaireInterface().getStrPositionCarte() + "\"\n"
                            + "        bingAPIKey=\"" + getStrBingAPIKey() + "\"\n"
                            + "        couleurFond=\"" + strCoulFond + "\"\n"
                            + "        couleurTexte=\"#" + getGestionnaireInterface().getStrCouleurTexteCarte() + "\"\n"
                            + "        radarCarteAffiche=\"" + strAfficheRadarCarte + "\"\n"
                            + "        radarCarteTaille=\"" + Math.round(getGestionnaireInterface().getTailleRadarCarte()) + "\"\n"
                            + "        radarCarteCouleurFond=\"#" + getGestionnaireInterface().getStrCouleurFondRadarCarte() + "\"\n"
                            + "        radarCarteCouleurLigne=\"#" + getGestionnaireInterface().getStrCouleurLigneRadarCarte() + "\"\n"
                            + "        radarCarteOpacite=\"" + Math.round(getGestionnaireInterface().getOpaciteRadarCarte() * 100.d) / 100.d + "\"\n"
                            + "    >\n";
                    for (int iPoint2 = 0; iPoint2 < getiNombrePanoramiques(); iPoint2++) {
                        if (getPanoramiquesProjet()[iPoint2].getMarqueurGeolocatisation() != null) {
                            double posX = getPanoramiquesProjet()[iPoint2].getMarqueurGeolocatisation().getLongitude();
                            double posY = getPanoramiquesProjet()[iPoint2].getMarqueurGeolocatisation().getLatitude();
                            String strFichierPano2 = getPanoramiquesProjet()[iPoint2]
                                    .getStrNomFichier().substring(getPanoramiquesProjet()[iPoint2].getStrNomFichier()
                                            .lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPoint2]
                                            .getStrNomFichier().length()).split("\\.")[0];
                            String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-weight:bold;font-size : 12px;'>"
                                    + getPanoramiquesProjet()[iPoint2].getStrTitrePanoramique()
                                    + "</span>";
                            strHTML = strHTML.replace("<", "&lt;").replace(">", "&gt;");
                            String strFichierXML = getPanoramiquesProjet()[iPoint2].getStrNomFichier().substring(getPanoramiquesProjet()[iPoint2].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPoint2].getStrNomFichier().length()).split("\\.")[0];
                            if (iPoint2 == i) {
                                strContenuFichier
                                        += "        <pointCarte\n"
                                        + "            positX=\"" + posX + "\"\n"
                                        + "            positY=\"" + posY + "\"\n"
                                        + "            xml=\"actif\"\n"
                                        + "            image=\"panos/" + strFichierXML + "Vignette.jpg\"\n"
                                        + "            html=\"" + strHTML + "\"\n"
                                        + "            />  \n";

                            } else {
                                strContenuFichier
                                        += "        <pointCarte\n"
                                        + "            positX=\"" + posX + "\"\n"
                                        + "            positY=\"" + posY + "\"\n"
                                        + "            xml=\"xml/" + strFichierXML + ".xml\"\n"
                                        + "            image=\"panos/" + strFichierXML + "Vignette.jpg\"\n"
                                        + "            html=\"" + strHTML + "\"\n"
                                        + "            />  \n";
                            }

                        }
                    }
                    strContenuFichier
                            += "    </carte>\n";
                }

                strContenuFichier += "</scene>\n";
                String strFichPano = getPanoramiquesProjet()[i].getStrNomFichier();
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
            String strFPano1 = "panos/niveau0/" + getPanoramiquesProjet()[0].getStrNomFichier().substring(getPanoramiquesProjet()[0].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[0].getStrNomFichier().length());

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
                    + "        <meta property=\"og:image\" content=\"" + strFPano1 + "\" />\n"
                    + "        <script type=\"text/javascript\" src=\"panovisu/libs/openLayers/OpenLayers.js\"></script>\n"
                    + "        <script type=\"text/javascript\" src=\"panovisu/libs/openLayers/OpenStreetMap.js\"></script>\n"
                    + "        <script type=\"text/javascript\" src=\"panovisu/libs/openLayers/gmap.js?v=3.5&sensor=false\"></script>\n"
                    + "        <style>\n"
                    + "            .olControlLayerSwitcher\n"
                    + "            {\n"
                    + "                font-family: Verdana,Arial,sans-serif!important;\n"
                    + "                margin-top: 3px;\n"
                    + "                margin-left: 3px;\n"
                    + "                margin-bottom: 3px;\n"
                    + "                font-size: 10pt!important;\n"
                    + "                color: white;\n"
                    + "                background-color: transparent;\n"
                    + "                z-index: 10000;\n"
                    + "                background-color: #999!important;\n"
                    + "                color : #ff0!important;\n"
                    + "                max-width: 180px!important;\n"
                    + "                opacity: 0.95!important;\n"
                    + "            }\n"
                    + "            .olControlLayerSwitcher .layersDiv\n"
                    + "            {\n"
                    + "                padding-top: 5px;\n"
                    + "                padding-left: 10px;\n"
                    + "                padding-bottom: 5px;\n"
                    + "                padding-right: 10px;\n"
                    + "                background-color: #aaa!important;\n"
                    + "                color : #fff!important;\n"
                    + "                font-family: Verdana,Arial,sans-serif!important;\n"
                    + "                font-size: 10pt!important;\n"
                    + "                font-weight: normal!important;\n"
                    + "                max-width: 180px!important;\n"
                    + "                overflow: hidden!important;\n"
                    + "                opacity: 0.95!important;\n"
                    + "            }        \n"
                    + "        </style>\n"
                    + ""
                    + "    </head>\n"
                    + "    <body style='background-color:#777'>\n"
                    + "        <header>\n"
                    + "\n"
                    + "        </header>\n"
                    + "        <article style=\"height : " + iHauteur + "px;\">\n"
                    + "            <div style='background-color : #777' id=\"pano\">\n"
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
                    + "                    langue : \"" + getLocale().toString() + "\",\n"
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
                    + "            });\n"
                    + "        </script>\n"
                    + "    </body>\n"
                    + "</html>\n";
            strFichierHTML = strFichierHTML.replace("PANO", strPanoEntree);
            File fileIndexHTML = new File(getStrRepertTemp() + File.separator + "index.html");
            fileIndexHTML.setWritable(true);
            OutputStreamWriter oswFichierHTML = new OutputStreamWriter(new FileOutputStream(fileIndexHTML), "UTF-8");
            try (BufferedWriter bwFichierHTML = new BufferedWriter(oswFichierHTML)) {
                bwFichierHTML.write(strFichierHTML);
            }
            DirectoryChooser dcRepertChoix = new DirectoryChooser();
            dcRepertChoix.setTitle("Choix du repertoire de sauvegarde de la visite");
            File fileRepert = new File(EditeurPanovisu.getStrRepertoireProjet());
            dcRepertChoix.setInitialDirectory(fileRepert);
            File fileRepertVisite = dcRepertChoix.showDialog(null);
            if (fileRepertVisite != null) {
                String strNomRepertVisite = fileRepertVisite.getAbsolutePath();
                copieDirectory(getStrRepertTemp(), strNomRepertVisite);
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
            }
        } else {
            Dialogs.create().title(rbLocalisation.getString("main.dialog.generationVisite"))
                    .message(rbLocalisation.getString("main.dialog.generationVisiteMessageErreur"))
                    .showError();

        }
    }

    /**
     *
     * @param strFichierImage
     * @param strRepertoire
     * @param iNumero
     * @param iTotal
     * @return
     */
    private static int iCreeNiveauxImageEqui(String strFichierImage, String strRepertoire, int iNumero, int iTotal) {
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
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(EditeurPanovisu.class
                                    .getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return iNombreNiveaux;
    }

    /**
     *
     * @param strFichierImage
     * @param strRepertoire
     * @param strFace
     * @return
     */
    private static int iCreeNiveauxImageCube(String strFichierImage, String strRepertoire, String strFace) {
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
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(EditeurPanovisu.class
                                    .getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return iNombreNiveaux;
    }

    /**
     *
     * @throws InterruptedException
     */
    private static void panoramiquesAjouter() throws InterruptedException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImage = new FileChooser.ExtensionFilter("Fichiers Image (JPG,BMP,TIFF)", "*.jpg", "*.bmp", "*.tif");
        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
        FileChooser.ExtensionFilter extFilterTiff = new FileChooser.ExtensionFilter("Fichiers TIFF (*.tif)", "*.tif");
        File fileRepert = new File(getStrCurrentDir() + File.separator);
        fileChooser.setInitialDirectory(fileRepert);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterTiff, extFilterBmp, extFilterImage);

        List<File> listFichiers = fileChooser.showOpenMultipleDialog(null);
        if (listFichiers != null) {
            getApAttends().setVisible(true);
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
            taskTraitementChargeFichiers = tskChargeListeFichiers(fileLstFich1, iNb);
            Thread thrChargeFichiers = new Thread(taskTraitementChargeFichiers);
            thrChargeFichiers.setDaemon(true);
            thrChargeFichiers.start();

        }

    }

    /**
     *
     * @param listFichiers
     * @param iNb
     * @return
     */
    public static Task tskChargeListeFichiers(final File[] listFichiers, int iNb) {
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
                    if (strExtension.equals("jpg") || strExtension.equals("bmp") || strExtension.equals("tif")) {
                        Image imgPano = null;
                        if (strExtension.equals("tif")) {
                            try {
                                imgPano = ReadWriteImage.readTiff(fileFichier.getAbsolutePath());

                            } catch (ImageReadException | IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class
                                        .getName()).log(Level.SEVERE, null, ex);
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

                    setbDejaSauve(false);
                    mniSauveProjet.setDisable(false);
                    setStrCurrentDir(fileFichier1.getParent());
                    File fileImageRepert = new File(getStrRepertTemp() + File.separator + "panos");

                    if (!fileImageRepert.exists()) {

                        fileImageRepert.mkdirs();
                    }
                    setStrRepertPanos(fileImageRepert.getAbsolutePath());
                    ajoutePanoramiqueProjet(fileFichier1.getPath(), strTypeFich1[ii], iNumP, fileLstFich.length);
                }
                return true;
            }

            @Override
            protected void succeeded() {
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                super.succeeded();
                cbListeChoixPanoramique.getItems().clear();
                apVignettesPano.getChildren().clear();
                for (int i = 0; i < getiNombrePanoramiques(); i++) {
                    String strFichierPano = getPanoramiquesProjet()[i].getStrNomFichier();
                    String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                    cbListeChoixPanoramique.getItems().add(strNomPano);
                    String strExtension = getPanoramiquesProjet()[i].getStrNomFichier().substring(getPanoramiquesProjet()[i].getStrNomFichier().length() - 3, getPanoramiquesProjet()[i].getStrNomFichier().length());
                    ImageView ivVignettePano = new ImageView(getPanoramiquesProjet()[i].getImgVignettePanoramique());
                    ivVignettePano.setPreserveRatio(true);
                    ivVignettePano.setFitWidth(iLargeurVignettes);
                    ivVignettePano.setLayoutX(5);
                    ivVignettePano.setLayoutY((iLargeurVignettes / 2 + 10) * i + 10);
                    ivVignettePano.setCursor(Cursor.HAND);
                    int iNumero = i;
                    ivVignettePano.setOnMouseClicked((e) -> {
                        affichePanoChoisit(iNumero);
                    });
                    ivVignettePano.setOnMouseDragEntered((e) -> {
                        ivVignettePano.setLayoutX(3);
                        ivVignettePano.setStyle(
                                "-fx-border-color : #fff;"
                                + "-fx-border-width : 2px;"
                                + "-fx-border-style :solid;");
                    });
                    ivVignettePano.setOnMouseDragExited((e) -> {
                        ivVignettePano.setLayoutX(5);
                        ivVignettePano.setStyle(
                                "-fx-border-color : rgba(0,0,0,0);"
                                + "-fx-border-width : 0px;"
                                + "-fx-border-style :none;");
                    });

                    apVignettesPano.getChildren().add(ivVignettePano);

                }
                apVignettesPano.getChildren().add(rectVignettePano);
                vbChoixPanoramique.setVisible(true);
                ivImagePanoramique.setImage(getPanoramiquesProjet()[getiPanoActuel()].getImgPanoramique());
                ivImagePanoramique.setSmooth(true);
                retireAffichageLigne();
                dejaCharge = false;
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                iNumPoints = 0;
                ajouteAffichageLignes();
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(getiPanoActuel()));
                affichePoV(getPanoramiquesProjet()[getiPanoActuel()].getRegardX(), getPanoramiquesProjet()[getiPanoActuel()].getRegardY());
                afficheNord(getPanoramiquesProjet()[getiPanoActuel()].getZeroNord());
                installeEvenements();
                ivVisiteGenere.setOpacity(1.0);
                ivVisiteGenere.setDisable(false);
                getGestionnaireInterface().rafraichit();
                affichePanoChoisit(getiPanoActuel());
                bPanoCharge = true;
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(getiPanoActuel()));
                getApAttends().setVisible(false);
                mbarPrincipal.setDisable(false);
                hbBarreBouton.setDisable(false);
                tpEnvironnement.setDisable(false);

                ordPano.ajouteNouveauxPanos();
                apListePanoTriable = ordPano.getApListePanoramiques();
                apListePanoTriable.setMaxHeight(apListePanoTriable.getPrefHeight());
                apListePanoTriable.setMinHeight(apListePanoTriable.getPrefHeight());
                apListePanoTriable.setVisible(true);
                apParametresVisite.getChildren().remove(apListePanoTriable);
                apParametresVisite.getChildren().add(apListePanoTriable);
                apListePanoTriable.setLayoutX(40);
                apListePanoTriable.setLayoutY(130);
                apParametresVisite.setPrefHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                if (apParametresVisite.isVisible()) {
                    apParametresVisite.setMinHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                    apParametresVisite.setMaxHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                }
                rafraichitListePano();

            }

        };
    }

    /**
     *
     */
    private static void planAjouter() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("Fichiers PNG (*.png)", "*.png");
        File repert = new File(getStrCurrentDir() + File.separator);
        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterPng, extFilterBmp);

        File fileFichierPlan = fileChooser.showOpenDialog(null);
        if (fileFichierPlan != null) {
            getPlans()[getiNombrePlans()] = new Plan();
            getPlans()[getiNombrePlans()].setStrImagePlan(fileFichierPlan.getName());
            getPlans()[getiNombrePlans()].setStrLienPlan(fileFichierPlan.getAbsolutePath());
            File fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
            if (!fileRepertoirePlan.exists()) {
                fileRepertoirePlan.mkdirs();
            }
            try {
                copieFichierRepertoire(fileFichierPlan.getAbsolutePath(), fileRepertoirePlan.getAbsolutePath());

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            getGestionnairePlan().ajouterPlan();
            setiNombrePlans(getiNombrePlans() + 1);
        }

    }

    /**
     *
     */
    private static void clickBtnValidePano() {
        TextField tfTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
        if (tfTitrePano != null) {
            getPanoramiquesProjet()[getiPanoActuel()].setStrTitrePanoramique(tfTitrePano.getText());
            Label lblTitre = (Label) apParametresVisite.lookup("#lvTitre" + iPanoActuel);
            if (lblTitre != null) {
                lblTitre.setText(tfTitrePano.getText().toUpperCase());
            }
            for (int i = 0; i < ordPano.getCellulesPanoramiques().size(); i++) {
                if (ordPano.getCellulesPanoramiques().get(i).getiNumPano() == iPanoActuel) {
                    ordPano.getCellulesPanoramiques().get(i).setStrTitrePanoramique(tfTitrePano.getText());
                }
            }
        }
    }

    /**
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private static void projetCharge() throws IOException, InterruptedException {
        if (!bRepertSauveChoisi) {
            setStrRepertoireProjet(getStrCurrentDir());
        }
        Action actReponse = null;
        Localization.setLocale(getLocale());
        if (!isbDejaSauve()) {
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
            getApAttends().setVisible(true);
            mbarPrincipal.setDisable(true);
            hbBarreBouton.setDisable(true);
            tpEnvironnement.setDisable(true);
            setbDejaSauve(true);
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File fileRepert = new File(getStrRepertoireProjet() + File.separator);
            fcRepertChoix.setInitialDirectory(fileRepert);
            fileProjet = null;
            fileProjet = fcRepertChoix.showOpenDialog(getStPrincipal());
            if (fileProjet != null) {
                getStPrincipal().setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                lblDragDrop.setVisible(false);
                setStrRepertoireProjet(fileProjet.getParent());
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                bRepertSauveChoisi = true;
                deleteDirectory(getStrRepertTemp());
                String strRepertPanovisu = getStrRepertTemp() + File.separator + "panovisu";
                File fileRptPanovisu = new File(strRepertPanovisu);
                fileRptPanovisu.mkdirs();
                copieDirectory(getStrRepertAppli() + File.separator + "panovisu", strRepertPanovisu);
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
                setiNumHTML(0);
                ivImagePanoramique.setImage(null);
                cbListeChoixPanoramique.getItems().clear();
                try {
                    String strTexte;
                    try (BufferedReader brFichierPVU = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        strTexte = "";
                        String strLigneTexte;
                        setiNombrePanoramiquesFichier(0);
                        while ((strLigneTexte = brFichierPVU.readLine()) != null) {
                            if (strLigneTexte.contains("Panoramique=>")) {
                                setiNombrePanoramiquesFichier(getiNombrePanoramiquesFichier() + 1);
                            }
                            strTexte += strLigneTexte + "\n";
                        }
                    }

                    Task taskAnalysePVU;
                    taskAnalysePVU = tskAnalyseFichierPVU(strTexte);
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

    /**
     *
     * @param nomFich
     */
    private static void ajouteFichierHisto(String nomFich) {
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

    /**
     * strNomFich@param nomFich
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private static void projetChargeNom(String strNomFich) throws IOException, InterruptedException {
        File fileProjet1 = new File(strNomFich);
        if (fileProjet1.exists()) {
            Action actReponse = null;
            Localization.setLocale(getLocale());
            if (!isbDejaSauve()) {
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
                getApAttends().setVisible(true);
                mbarPrincipal.setDisable(true);
                hbBarreBouton.setDisable(true);
                tpEnvironnement.setDisable(true);
                lblDragDrop.setVisible(false);
                setbDejaSauve(true);
                fileProjet = fileProjet1;
                ajouteFichierHisto(fileProjet.getAbsolutePath());
                getStPrincipal().setTitle("Panovisu  v" + strNumVersion + " : " + fileProjet.getAbsolutePath());
                setStrRepertoireProjet(fileProjet.getParent());
                bRepertSauveChoisi = true;
                deleteDirectory(getStrRepertTemp());
                String strRepertPanovisu = getStrRepertTemp() + File.separator + "panovisu";
                File fileRptPanovisu = new File(strRepertPanovisu);
                fileRptPanovisu.mkdirs();
                copieDirectory(getStrRepertAppli() + File.separator + "panovisu", strRepertPanovisu);
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
                setiNumHTML(0);

                ivImagePanoramique.setImage(null);
                cbListeChoixPanoramique.getItems().clear();
                try {
                    String strTexte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fileProjet), "UTF-8"))) {
                        strTexte = "";
                        String strLigneTexte;
                        setiNombrePanoramiquesFichier(0);
                        while ((strLigneTexte = br.readLine()) != null) {
                            if (strLigneTexte.contains("Panoramique=>")) {
                                setiNombrePanoramiquesFichier(getiNombrePanoramiquesFichier() + 1);
                            }
                            strTexte += strLigneTexte + "\n";
                        }
                    }
                    Task taskAnalysePVU;
                    taskAnalysePVU = tskAnalyseFichierPVU(strTexte);
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

    /**
     *
     * @throws IOException
     */
    private static void sauveFichierProjet() throws IOException {
        setStrRepertoireProjet(fileProjet.getParent());
        bRepertSauveChoisi = true;
        File fileFichierProjet = new File(fileProjet.getAbsolutePath());
        if (!fileFichierProjet.exists()) {
            fileFichierProjet.createNewFile();
        }
        setbDejaSauve(true);
        getStPrincipal().setTitle("Panovisu v" + strNumVersion + " : " + fileProjet.getAbsolutePath());

        String strContenuFichier = "";
        TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
        if (!tfVisite.getText().equals("")) {
            strContenuFichier += "[titreVisite=>" + tfVisite.getText() + "]\n";
        }
        if (!strPanoEntree.equals("")) {
            strContenuFichier += "[PanoEntree=>" + strPanoEntree + "]\n";
        }
        strContenuFichier += "[introLP=>" + isbIntroPetitePlanete() + "]\n";
        strContenuFichier += "[arDemarre=>" + isbAutoRotationDemarre() + "]\n";
        strContenuFichier += "[arVitesse=>" + getiAutoRotationVitesse() + "]\n";
        strContenuFichier += "[atDemarre=>" + isbAutoTourDemarre() + "]\n";
        strContenuFichier += "[atVitesse=>" + getiAutoTourLimite() + "]\n";
        strContenuFichier += "[atType=>" + getStrAutoTourType() + "]\n";
        strOrdrePanos = "";
        ordPano.getStrPanos().stream().forEach((strOrd) -> {
            strOrdrePanos += strOrd + ",";
        });
        strOrdrePanos = strOrdrePanos.substring(0, strOrdrePanos.length() - 1);
        strContenuFichier += "[OrdrePanos=>" + strOrdrePanos + "]\n";
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            strContenuFichier += "[Panoramique=>"
                    + "fichier:" + getPanoramiquesProjet()[i].getStrNomFichier()
                    + ";nb:" + getPanoramiquesProjet()[i].getNombreHotspots()
                    + ";nbImg:" + getPanoramiquesProjet()[i].getNombreHotspotImage()
                    + ";nbHTML:" + getPanoramiquesProjet()[i].getNombreHotspotHTML()
                    + ";titre:" + getPanoramiquesProjet()[i].getStrTitrePanoramique() + ""
                    + ";type:" + getPanoramiquesProjet()[i].getStrTypePanoramique()
                    + ";afficheInfo:" + getPanoramiquesProjet()[i].isAfficheInfo()
                    + ";afficheTitre:" + getPanoramiquesProjet()[i].isAfficheTitre()
                    + ";regardX:" + getPanoramiquesProjet()[i].getRegardX()
                    + ";regardY:" + getPanoramiquesProjet()[i].getRegardY()
                    + ";zeroNord:" + getPanoramiquesProjet()[i].getZeroNord()
                    + ";affichePlan:" + getPanoramiquesProjet()[i].isAffichePlan()
                    + ";numeroPlan:" + getPanoramiquesProjet()[i].getNumeroPlan();
            if (getPanoramiquesProjet()[i].getMarqueurGeolocatisation() != null) {
                strContenuFichier += ";longitudeGeo:" + getPanoramiquesProjet()[i].getMarqueurGeolocatisation().getLongitude()
                        + ";latitudeGeo:" + getPanoramiquesProjet()[i].getMarqueurGeolocatisation().getLatitude();
            }
            strContenuFichier += "]\n";
            for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspots(); j++) {
                HotSpot HS = getPanoramiquesProjet()[i].getHotspot(j);
                strContenuFichier += "   [hotspot==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";image:" + HS.getStrFichierImage()
                        + ";xml:" + HS.getStrFichierXML()
                        + ";info:" + HS.getStrInfo()
                        + ";anime:" + HS.isAnime()
                        + "]\n";
            }
            for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspotImage(); j++) {
                HotspotImage HS = getPanoramiquesProjet()[i].getHotspotImage(j);
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
            for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspotHTML(); j++) {
                HotspotHTML HS = getPanoramiquesProjet()[i].getHotspotHTML(j);
                strContenuFichier += "   [hotspotHTML==>"
                        + "longitude:" + HS.getLongitude()
                        + ";latitude:" + HS.getLatitude()
                        + ";lienExterieur:" + HS.isbLienExterieur()
                        + ";url:" + HS.getStrURLExterieure()
                        + ";info:" + HS.getStrInfo()
                        + ";anime:" + HS.isAnime()
                        + ";position:" + HS.getStrPositionHTML()
                        + ";couleur:" + HS.getStrCouleurHTML()
                        + ";opacite:" + HS.getOpaciteHTML()
                        + ";largeur:" + HS.getLargeurHTML()
                        + "]\n";
            }
        }
        strContenuFichier += "[Interface=>\n" + getGestionnaireInterface().strGetTemplate() + "]\n";
        strContenuFichier += getGestionnairePlan().getTemplate();
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            for (int j = 0; j < getPanoramiquesProjet()[i].getNombreHotspotHTML(); j++) {
                if (!panoramiquesProjet[i].getHotspotHTML(j).isbLienExterieur()) {
                    HotspotHTML HS = getPanoramiquesProjet()[i].getHotspotHTML(j);
                    strContenuFichier
                            += "[pageHTML=>"
                            + "numPano:" + i + "\n"
                            + "numHSHTML:" + j + "\n"
                            + "texteHTML:" + HS.getStrTexteHTML().replace("\n", "&nl").replace(":", "&dp").replace(";", "&pv") + "\n"
                            + "numImages:" + HS.getiNombreImages() + "\n";
                    for (int k = 0; k < HS.getiNombreImages(); k++) {
                        strContenuFichier
                                += "imagePath:" + HS.getImagesEditeur()[k].getStrImagePath() + "\n";

                    }
                    strContenuFichier += "]\n";
                }
            }
        }
        fileProjet.setWritable(true);
        OutputStreamWriter oswFichierPVU = new OutputStreamWriter(new FileOutputStream(fileProjet), "UTF-8");
        try (BufferedWriter bwFichierPVU = new BufferedWriter(oswFichierPVU)) {
            bwFichierPVU.write(strContenuFichier);
        }
        Dialogs.create().title(rbLocalisation.getString("main.dialog.sauveProjet"))
                .message(rbLocalisation.getString("main.dialog.sauveProjetMessage"))
                .showInformation();

    }

    /**
     *
     * @throws IOException
     */
    private static void sauveHistoFichiers() throws IOException {
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
            Logger.getLogger(ConfigDialogController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bwFichierHisto = new BufferedWriter(oswFichierHisto);
        try {
            bwFichierHisto.write(strContenuFichier);

        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bwFichierHisto.close();

        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @throws IOException
     */
    private static void projetSauve() throws IOException {
        if (!bRepertSauveChoisi) {
            setStrRepertoireProjet(getStrCurrentDir());
        }
        if (fileProjet == null) {
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(getStrRepertoireProjet() + File.separator);
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
    private static void projetSauveSous() throws IOException {
        if (!bRepertSauveChoisi) {
            setStrRepertoireProjet(getStrCurrentDir());
        }
        FileChooser fcRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
        fcRepertChoix.getExtensionFilters().add(extFilter);
        File fileRepert = new File(getStrRepertoireProjet() + File.separator);
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
    private static void aideapropos() {
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
    private static void projetsFermer() throws IOException {
        Action actReponse = null;
        Localization.setLocale(getLocale());
        if (!isbDejaSauve()) {
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
            deleteDirectory(getStrRepertTemp());
            File fileTemp = new File(getStrRepertTemp());
            fileTemp.delete();
            Platform.exit();
        }
    }

    /**
     *
     */
    private static void projetsNouveau() {
        Action actReponse = null;
        Localization.setLocale(getLocale());
        if (!isbDejaSauve()) {
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
            apVignettesPano.getChildren().clear();
            strPanoEntree = "";
            deleteDirectory(getStrRepertTemp());
            String strRepertPanovisu = getStrRepertTemp() + File.separator + "panovisu";
            File fileRptPanovisu = new File(strRepertPanovisu);
            fileRptPanovisu.mkdirs();
            copieDirectory(getStrRepertAppli() + File.separator + "panovisu", strRepertPanovisu);
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
            setPanoramiquesProjet(new Panoramique[50]);
            setiNombrePanoramiques(0);
            retireAffichageLigne();
            dejaCharge = false;
            retireAffichageHotSpots();
            retireAffichagePointsHotSpots();
            iNumPoints = 0;
            iNumImages = 0;
            setiNumHTML(0);
            getGestionnaireInterface().setiNombreImagesFond(0);
            getGestionnairePlan().setiPlanActuel(-1);
            ivImagePanoramique.setImage(null);
            cbListeChoixPanoramique.getItems().clear();
            lblDragDrop.setVisible(true);
            Node nodAncienNord = (Node) panePanoramique.lookup("#Nord");
            if (nodAncienNord != null) {
                panePanoramique.getChildren().remove(nodAncienNord);
            }
            Node nodAncienPoV = (Node) panePanoramique.lookup("#PoV");
            if (nodAncienPoV != null) {
                panePanoramique.getChildren().remove(nodAncienPoV);
            }

            getGestionnairePlan().getLblDragDropPlan().setVisible(true);
            setiNombrePlans(0);
            setPlans(new Plan[100]);
            getGestionnairePlan().creeInterface(iLargeurInterface, iHauteurInterface - 60);
            Pane panePanneauPlan = getGestionnairePlan().getPaneInterface();
            getMniAffichagePlan().setDisable(true);
            getTabPlan().setDisable(true);
            getTabPlan().setContent(panePanneauPlan);
            spVuePanoramique.setOnDragOver((DragEvent event) -> {
                Dragboard dbFichiersPanoramiques = event.getDragboard();
                if (dbFichiersPanoramiques.hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                } else {
                    event.consume();
                }
            });
            spVuePanoramique.setOnDragDropped((DragEvent event) -> {
                Platform.runLater(() -> {
                    getApAttends().setVisible(true);
                });
                Dragboard dbFichiersPanoramiques = event.getDragboard();
                boolean bSucces = false;
                if (dbFichiersPanoramiques.hasFiles()) {
                    getApAttends().setVisible(true);
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
                        taskChargeFichiers = tskChargeListeFichiers(fileList, iNb);
                        Thread thChargeFichiers = new Thread(taskChargeFichiers);
                        thChargeFichiers.setDaemon(true);
                        thChargeFichiers.start();
                    }

                }
                event.setDropCompleted(bSucces);
                event.consume();
            });
            apVignettesPano.getChildren().add(rectVignettePano);
            rectVignettePano.setVisible(false);
        }
    }

    /**
     *
     * @param iNombre
     * @return
     */
    private String strGenereChaineAleatoire(int iNombre) {
        String strChaine = "";
        String strChaine1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < iNombre; i++) {
            int iNumChar = (int) (Math.random() * 36.0f);
            strChaine += strChaine1.substring(iNumChar, iNumChar + 1);
        }
        return strChaine;
    }

    /**
     *
     * @param strEmplacement
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

    public static void rafraichitListePano() {
        apVignettesPano.getChildren().clear();
        cbListeChoixPanoramique.getItems().clear();
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            String strFichierPano = getPanoramiquesProjet()[i].getStrNomFichier();
            String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
            cbListeChoixPanoramique.getItems().add(strNomPano);
        }
        for (int ii = 0; ii < ordPano.getStrPanos().size(); ii++) {
            int i = Integer.parseInt(ordPano.getStrPanos().get(ii));
            ImageView ivVignettePano = new ImageView(getPanoramiquesProjet()[i].getImgVignettePanoramique());
            ivVignettePano.setPreserveRatio(true);
            ivVignettePano.setFitWidth(iLargeurVignettes);
            ivVignettePano.setLayoutX(5);
            ivVignettePano.setLayoutY((iLargeurVignettes / 2 + 10) * ii + 10);
            ivVignettePano.setCursor(Cursor.HAND);
            int iNumero = i;
            ivVignettePano.setOnMouseClicked((e) -> {
                affichePanoChoisit(iNumero);
            });
            ivVignettePano.setOnMouseDragEntered((e) -> {
                ivVignettePano.setLayoutX(3);
                ivVignettePano.setStyle(
                        "-fx-border-color : #fff;"
                        + "-fx-border-width : 2px;"
                        + "-fx-border-style :solid;");
            });
            ivVignettePano.setOnMouseDragExited((e) -> {
                ivVignettePano.setLayoutX(5);
                ivVignettePano.setStyle(
                        "-fx-border-color : rgba(0,0,0,0);"
                        + "-fx-border-width : 0px;"
                        + "-fx-border-style :none;");
            });
            apVignettesPano.getChildren().add(ivVignettePano);
        }
        int iPano = -1;
        int ii = 0;
        for (String stNumPano : ordPano.getStrPanos()) {
            if (Integer.parseInt(stNumPano) == getiPanoActuel()) {
                iPano = ii;
            }
            ii++;
        }
        rectVignettePano.setLayoutY((iLargeurVignettes / 2 + 10) * iPano + 10);
        rectVignettePano.setVisible(true);
        apVignettesPano.getChildren().add(rectVignettePano);
        cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(getiPanoActuel()));
    }

    /**
     *
     * @param strLigComplete
     * @return
     */
    public static Task tskAnalyseFichierPVU(final String strLigComplete) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                strPanoEntree = "";
                strOrdrePanos = "";
                setiNombrePanoramiques(0);
                String strLigneComplete = strLigComplete.replace("[", "|");
                String strLignes[] = strLigneComplete.split("\\|", 500);
                String strLigne;
                String[] strElementsLigne;
                String[] strTypeElement;
                int iNbHS = 0;
                int iNbImg = 0;
                int iNbHTML = 0;
                int iNbHsplan = 0;
                setiNombrePlans(-1);
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
                            if (strValeur.length == 1) {
                                strValeur[1] = "";
                            }
                            switch (strValeur[0]) {
                                case "fichier":
                                    String nmFich = strValeur[1];
                                    int iNumPano = getiNombrePanoramiques() + 1;
                                    Platform.runLater(() -> {
                                        lblCharge.setText("pano " + iNumPano + "/" + getiNombrePanoramiquesFichier() + " : " + nmFich);
                                        lblNiveaux.setText("");
                                        pbarAvanceChargement.setProgress((double) (iNumPano - 1) / (double) getiNombrePanoramiquesFichier());
                                    });
                                    File fileImgPano = new File(nmFich);
                                    File fileImageRepert = new File(getStrRepertTemp() + File.separator + "panos");

                                    if (!fileImageRepert.exists()) {
                                        fileImageRepert.mkdirs();
                                    }
                                    setStrRepertPanos(fileImageRepert.getAbsolutePath());
                                    String strFichierPano = fileImgPano.getPath();
                                    String strExtension = strFichierPano.substring(strFichierPano.length() - 3, strFichierPano.length());
                                    Image imgPano = null;
                                    if ("tif".equals(strExtension)) {
                                        try {
                                            imgPano = ReadWriteImage.readTiff(strFichierPano);

                                        } catch (IOException ex) {
                                            Logger.getLogger(EditeurPanovisu.class
                                                    .getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        imgPano = new Image("file:" + strFichierPano);
                                    }
                                    if (imgPano != null) {
                                        if (imgPano.getWidth() == imgPano.getHeight()) {
                                            ajoutePanoramiqueProjet(strValeur[1], Panoramique.CUBE, iNumPano, getiNombrePanoramiquesFichier());

                                        } else {
                                            ajoutePanoramiqueProjet(strValeur[1], Panoramique.SPHERE, iNumPano, getiNombrePanoramiquesFichier());
                                        }
                                    }
                                    break;

                                case "titre":
                                    if (!strValeur[1].equals("null")) {
                                        getPanoramiquesProjet()[getiPanoActuel()].setStrTitrePanoramique(strValeur[1]);
                                    } else {
                                        getPanoramiquesProjet()[getiPanoActuel()].setStrTitrePanoramique("");
                                    }
                                    break;
                                case "type":
                                    getPanoramiquesProjet()[getiPanoActuel()].setStrTypePanoramique(strValeur[1]);
                                    break;
                                case "afficheInfo":
                                    getPanoramiquesProjet()[getiPanoActuel()].setAfficheInfo(strValeur[1].equals("true"));
                                    break;
                                case "afficheTitre":
                                    getPanoramiquesProjet()[getiPanoActuel()].setAfficheTitre(strValeur[1].equals("true"));
                                    break;
                                case "nb":
                                    iNbHS = Integer.parseInt(strValeur[1]);
                                    break;
                                case "nbImg":
                                    iNbImg = Integer.parseInt(strValeur[1]);
                                    break;
                                case "nbHTML":
                                    iNbHTML = Integer.parseInt(strValeur[1]);
                                    break;
                                case "regardX":
                                    getPanoramiquesProjet()[getiPanoActuel()].setRegardX(Double.parseDouble(strValeur[1]));
                                    break;
                                case "regardY":
                                    getPanoramiquesProjet()[getiPanoActuel()].setRegardY(Double.parseDouble(strValeur[1]));
                                    break;
                                case "zeroNord":
                                    getPanoramiquesProjet()[getiPanoActuel()].setZeroNord(Double.parseDouble(strValeur[1]));
                                    break;
                                case "numeroPlan":
                                    getPanoramiquesProjet()[getiPanoActuel()].setNumeroPlan(Integer.parseInt(strValeur[1].replace(" ", "")));
                                    break;
                                case "longitudeGeo":
                                    getPanoramiquesProjet()[getiPanoActuel()].setMarqueurGeolocatisation(new CoordonneesGeographiques());
                                    getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation().setLongitude(Double.parseDouble(strValeur[1]));
                                    break;
                                case "latitudeGeo":
                                    getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation().setLatitude(Double.parseDouble(strValeur[1]));
                                    break;

                                case "affichePlan":
                                    getPanoramiquesProjet()[getiPanoActuel()].setAffichePlan(strValeur[1].equals("true"));
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
                                        HS.setAnime(strValeur[1].equals("true"));
                                        break;
                                }
                            }
                            getPanoramiquesProjet()[getiPanoActuel()].addHotspot(HS);
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
                                        File imageRepert = new File(getStrRepertTemp() + File.separator + "images");

                                        if (!imageRepert.exists()) {
                                            imageRepert.mkdirs();
                                        }
                                        setStrRepertPanos(imageRepert.getAbsolutePath());
                                        try {
                                            copieFichierRepertoire(imgPano.getPath(), getStrRepertPanos());

                                        } catch (IOException ex) {
                                            Logger.getLogger(EditeurPanovisu.class
                                                    .getName()).log(Level.SEVERE, null, ex);
                                        }

                                        break;
                                    case "anime":
                                        HS.setAnime(strValeur[1].equals("true"));
                                        break;
                                }
                            }
                            getPanoramiquesProjet()[getiPanoActuel()].addHotspotImage(HS);
                        }

                        for (int jj = 0; jj < iNbHTML; jj++) {
                            ikk++;
                            strLigne = strLignes[ikk];
                            strElementsLigne = strLigne.split(";", 10);
                            strTypeElement = strElementsLigne[0].split(">", 2);
                            strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                            strElementsLigne[0] = strTypeElement[1];
                            HotspotHTML HS = new HotspotHTML();
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
                                    case "lienExterieur":
                                        HS.setbLienExterieur(strValeur[1].equals("true"));
                                        break;
                                    case "url":
                                        HS.setStrURLExterieure(strValeur[1]);
                                        break;

                                    case "info":
                                        if ("null".equals(strValeur[1])) {
                                            HS.setStrInfo(null);
                                        } else {
                                            HS.setStrInfo(strValeur[1]);
                                        }
                                        break;
                                    case "anime":
                                        HS.setAnime(strValeur[1].equals("true"));
                                        break;
                                    case "position":
                                        HS.setStrPositionHTML(strValeur[1]);
                                        break;
                                    case "couleur":
                                        HS.setStrCouleurHTML(strValeur[1]);
                                        break;
                                    case "opacite":
                                        HS.setOpaciteHTML(Double.parseDouble(strValeur[1]));
                                        break;
                                    case "largeur":
                                        HS.setLargeurHTML(Double.parseDouble(strValeur[1]));
                                        break;

                                }
                            }
                            getPanoramiquesProjet()[getiPanoActuel()].addHotspotHTML(HS);
                        }

                    }

                    if ("Plan".equals(strTypeElement[0])) {
                        for (int i = 0; i < strElementsLigne.length; i++) {
                            strElementsLigne[i] = strElementsLigne[i].replace("]", "");
                            String[] strValeur = strElementsLigne[i].split(":", 2);
                            switch (strValeur[0]) {
                                case "lienImage":
                                    setiNombrePlans(getiNombrePlans() + 1);
                                    getPlans()[getiNombrePlans()] = new Plan();
                                    getPlans()[getiNombrePlans()].setStrLienPlan(strValeur[1]);
                                    File fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                                    if (!fileRepertoirePlan.exists()) {
                                        fileRepertoirePlan.mkdirs();
                                    }
                                    try {
                                        copieFichierRepertoire(getPlans()[getiNombrePlans()].getStrLienPlan(), fileRepertoirePlan.getAbsolutePath());

                                    } catch (IOException ex) {
                                        Logger.getLogger(EditeurPanovisu.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "image":
                                    getPlans()[getiNombrePlans()].setStrImagePlan(strValeur[1]);
                                    break;
                                case "titre":
                                    if (!strValeur[1].equals("'null'")) {
                                        getPlans()[getiNombrePlans()].setTitrePlan(strValeur[1]);
                                    } else {
                                        getPlans()[getiNombrePlans()].setTitrePlan("");
                                    }
                                    break;
                                case "nb":
                                    iNbHsplan = Integer.parseInt(strValeur[1]);
                                    break;
                                case "position":
                                    getPlans()[getiNombrePlans()].setStrPosition(strValeur[1]);
                                    break;
                                case "positionX":
                                    getPlans()[getiNombrePlans()].setPositionX(Double.parseDouble(strValeur[1]));
                                    break;
                                case "positionY":
                                    getPlans()[getiNombrePlans()].setPositionY(Double.parseDouble(strValeur[1]));
                                    break;
                                case "directionNord":
                                    getPlans()[getiNombrePlans()].setDirectionNord(Double.parseDouble(strValeur[1]));
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
                                        HS.setAnime(strValeur[1].equals("true"));
                                        break;
                                }
                            }
                            getPlans()[getiNombrePlans()].addHotspot(HS);
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
                        getGestionnaireInterface().setTemplate(strListTemplate);
                    }
                    if ("PanoEntree".equals(strTypeElement[0])) {
                        strPanoEntree = strElementsLigne[0].split("]")[0];
                    }
                    if ("introLP".equals(strTypeElement[0])) {
                        setbIntroPetitePlanete(strElementsLigne[0].split("]")[0].equals("true"));
                    }
                    if ("arDemarre".equals(strTypeElement[0])) {
                        setbAutoRotationDemarre(strElementsLigne[0].split("]")[0].equals("true"));
                    }
                    if ("arVitesse".equals(strTypeElement[0])) {
                        setiAutoRotationVitesse(Integer.parseInt(strElementsLigne[0].split("]")[0]));
                    }
                    if ("atDemarre".equals(strTypeElement[0])) {
                        setbAutoTourDemarre(strElementsLigne[0].split("]")[0].equals("true"));
                    }
                    if ("atVitesse".equals(strTypeElement[0])) {
                        setiAutoTourLimite(Integer.parseInt(strElementsLigne[0].split("]")[0]));
                    }
                    if ("atType".equals(strTypeElement[0])) {
                        setStrAutoTourType(strElementsLigne[0].split("]")[0]);
                    }
                    if ("OrdrePanos".equals(strTypeElement[0])) {
                        strOrdrePanos = strElementsLigne[0].split("]")[0];
                    }
                    if ("titreVisite".equals(strTypeElement[0])) {
                        strTitreVisite = strElementsLigne[0].split("]")[0];
                    }
                    if ("pageHTML".equals(strTypeElement[0])) {
                        String[] strLignesCommande = strElementsLigne[0].split("]")[0].split("\n");
                        int inPano = 0;
                        int inHSHTML = 0;
                        int inImage = 0;
                        for (String strLigneCommande : strLignesCommande) {
                            String strCommande = strLigneCommande.split(":", 2)[0];
                            String strVal = strLigneCommande.split(":", 2)[1].replace("&nl", "\n").replace("&pv", ";").replace("&dp", ":");
                            switch (strCommande) {
                                case "numPano":
                                    inPano = Integer.parseInt(strVal);
                                    break;
                                case "numHSHTML":
                                    inHSHTML = Integer.parseInt(strVal);
                                    break;
                                case "texteHTML":
                                    getPanoramiquesProjet()[inPano].getHotspotHTML(inHSHTML).setStrTexteHTML(strVal);
                                    break;
                                case "imagePath":
                                    ImageEditeurHTML imageEdit = new ImageEditeurHTML();
                                    imageEdit.setStrImagePath(strVal);
                                    panoramiquesProjet[inPano].getHotspotHTML(inHSHTML).getImagesEditeur()[inImage] = imageEdit;
                                    inImage++;
                                    getPanoramiquesProjet()[inPano].getHotspotHTML(inHSHTML).setiNombreImages(inImage);
                                    break;

                            }
                        }
                    }

                }
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();

                vbChoixPanoramique.setVisible(true);
                setiNombrePlans(getiNombrePlans() + 1);
                if (getiNombrePlans() != 0) {
                    int iNbPlans1 = getiNombrePlans();
                    for (int i = 0; i < iNbPlans1; i++) {
                        setiNombrePlans(i);
                        getGestionnairePlan().ajouterPlan();
                    }
                    setiNombrePlans(getiNombrePlans() + 1);
                }

                try {
                    getGestionnaireInterface().afficheTemplate();

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                if (apListePanoTriable != null) {
                    apParametresVisite.getChildren().remove(
                            apListePanoTriable
                    );
                }
                if (strOrdrePanos.equals((""))) {
                    ordPano.creeListe();
                } else {
                    ordPano.creeListe(strOrdrePanos);
                }
                apListePanoTriable = ordPano.getApListePanoramiques();
                apListePanoTriable.setLayoutX(40);
                apListePanoTriable.setLayoutY(130);
                apListePanoTriable.setVisible(true);
                apParametresVisite.setPrefHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                apParametresVisite.getChildren().add(
                        apListePanoTriable
                );
                if (apParametresVisite.isVisible()) {
                    apParametresVisite.setMinHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                    apParametresVisite.setMaxHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                }
                getGestionnaireInterface().rafraichit();
                setiPanoActuel(Integer.parseInt(ordPano.getStrPanos().get(0)));
                rafraichitListePano();
                affichePanoChoisit(getiPanoActuel());
                bPanoCharge = true;
                vbChoixPanoramique.setVisible(true);
                cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(0));
                TextField tfVisite = (TextField) vbChoixPanoramique.lookup("#titreVisite");
                tfVisite.setText(strTitreVisite);
                getApAttends().setVisible(false);
                mbarPrincipal.setDisable(false);
                hbBarreBouton.setDisable(false);
                tpEnvironnement.setDisable(false);
                cbIntroPetitePlanete.setSelected(isbIntroPetitePlanete());
                cbAutoRotationDemarrage.setSelected(isbAutoRotationDemarre());
                bdfAutoRotationVitesse.setNumber(new BigDecimal(getiAutoRotationVitesse()));
                switch (getiAutoRotationVitesse()) {
                    case 10:
                        cbAutoRotationVitesse.getSelectionModel().select(0);
                        break;
                    case 20:
                        cbAutoRotationVitesse.getSelectionModel().select(1);
                        break;
                    case 30:
                        cbAutoRotationVitesse.getSelectionModel().select(2);
                        break;
                    default:
                        cbAutoRotationVitesse.getSelectionModel().select(3);
                        break;
                }
                cbAutoTourDemarrage.setSelected(isbAutoTourDemarre());
                bdfAutoTourLimite.setNumber(new BigDecimal(getiAutoTourLimite()));
                switch (getStrAutoTourType()) {
                    case "tours":
                        cbAutoTourType.getSelectionModel().select(0);
                        break;
                    case "secondes":
                        cbAutoTourType.getSelectionModel().select(1);
                        break;
                }
                rafraichitListePano();
            }

        };
    }

    /**
     *
     * @throws IOException
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
                    String strRepert = getStrRepertAppli();
                    String strAPI = "";
                    while ((strTexte = FichierConfig.readLine()) != null) {
                        String tpe = strTexte.split("=")[0];
                        String valeur;
                        if (strTexte.split("=").length > 1) {
                            valeur = strTexte.split("=")[1];
                        } else {
                            valeur = "";
                        }
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
                            case "bingAPIKey":
                                strAPI = valeur;
                                break;
                            case "style":
                                setStrStyleCSS(valeur);
                                break;

                        }

                    }
                    setLocale(new Locale(strLangue, strPays));
                    File fileRepert1 = new File(strRepert);
                    if (fileRepert1.exists()) {
                        setStrRepertoireProjet(strRepert);
                        setStrCurrentDir(strRepert);
                        bRepertSauveChoisi = true;
                    } else {
                        setStrRepertoireProjet(getStrRepertAppli());
                        setStrCurrentDir(getStrRepertAppli());
                    }
                    setStrBingAPIKey(strAPI);
                    setUserAgentStylesheet("file:css/" + getStrStyleCSS() + ".css");
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            fileFichierConfig.createNewFile();
            setLocale(new Locale("fr", "FR"));
            setUserAgentStylesheet("file:css/clair.css");
            setStrRepertoireProjet(getStrRepertAppli());
            String strContenuFichier = "langue=" + getLocale().toString().split("_")[0] + "\n";
            strContenuFichier += "pays=" + getLocale().toString().split("_")[1] + "\n";
            strContenuFichier += "repert=" + getStrRepertoireProjet() + "\n";
            strContenuFichier += "style=clair\n";
            fileFichierConfig.setWritable(true);
            OutputStreamWriter oswFichierConfig = null;
            try {
                oswFichierConfig = new OutputStreamWriter(new FileOutputStream(fileFichierConfig), "UTF-8");

            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            BufferedWriter bwFichierConfig = new BufferedWriter(oswFichierConfig);
            try {
                bwFichierConfig.write(strContenuFichier);

            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bwFichierConfig.close();

            } catch (IOException ex) {
                Logger.getLogger(ConfigDialogController.class
                        .getName()).log(Level.SEVERE, null, ex);
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
    private static void retireAffichageHotSpots() {
        Pane paneLabels = (Pane) vbVisuHotspots.lookup("#labels");
        vbVisuHotspots.getChildren().remove(paneLabels);
        dejaCharge = false;
    }

    /**
     *
     */
    private static void retireAffichagePointsHotSpots() {
        for (int i = 0; i < iNumPoints; i++) {
            Node nodePoint = (Node) panePanoramique.lookup("#point" + i);
            panePanoramique.getChildren().remove(nodePoint);
        }
        for (int i = 0; i < iNumImages; i++) {
            Node nodePoint = (Node) panePanoramique.lookup("#img" + i);
            panePanoramique.getChildren().remove(nodePoint);
        }
        for (int i = 0; i < getiNumHTML(); i++) {
            Node nodePoint = (Node) panePanoramique.lookup("#html" + i);
            panePanoramique.getChildren().remove(nodePoint);
        }
    }

    /**
     *
     * @param iPanCourant
     * @return
     */
    private static String strListePano(int iPanCourant) {
        String strListe = "";
        if (getiNombrePanoramiques() == 0) {
            return null;
        } else {
            for (int i = 0; i < getiNombrePanoramiques(); i++) {
                if (i != iPanCourant) {
                    String strFichierPano = getPanoramiquesProjet()[i].getStrNomFichier();
                    String strNomPano = strFichierPano.substring(strFichierPano.lastIndexOf(File.separator) + 1, strFichierPano.length());
                    String[] strNPano = strNomPano.split("\\.");
                    strListe += strNPano[0];
                    if (i < getiNombrePanoramiques() - 1) {
                        strListe += ";";
                    }
                }
            }
            return strListe;
        }
    }

    /**
     *
     * @param iNumHS
     * @return
     */
    private static AnchorPane apAfficherListePanosVignettes(int iNumHS) {

        AnchorPane aplistePano = new AnchorPane();
        aplistePano.setOpacity(1);
        Pane paneFond = new Pane();
        paneFond.setStyle("-fx-background-color : #bbb;");
        paneFond.setPrefWidth(540);
        paneFond.setPrefHeight(((getiNombrePanoramiques() - 2) / 4 + 1) * 65 + 10);
        paneFond.setMinWidth(540);
        paneFond.setMinHeight(70);
        aplistePano.getChildren().add(paneFond);
        aplistePano.setStyle("-fx-backgroung-color : #bbb;");
        int ij = 0;
        ImageView[] ivPano;
        ivPano = new ImageView[getiNombrePanoramiques()];
        double xPos;
        double yPos;
        int iRow = 0;
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            int iNumeroPano = i;
            ivPano[ij] = new ImageView(getPanoramiquesProjet()[i].getImgVignettePanoramique());
            ivPano[ij].setFitWidth(120);
            ivPano[ij].setFitHeight(60);
            ivPano[ij].setSmooth(true);
            String strNomPano = getPanoramiquesProjet()[i].getStrNomFichier();
            int iCol = ij % 4;
            iRow = ij / 4;
            xPos = iCol * 130 + 25;
            yPos = iRow * 65 + 5;
            ivPano[ij].setLayoutX(xPos);
            ivPano[ij].setLayoutY(yPos);
            ivPano[ij].setCursor(Cursor.HAND);
            ivPano[ij].setStyle("-fx-background-color : #ccc;");
            Tooltip tltpPano = new Tooltip(strNomPano.substring(strNomPano.lastIndexOf(File.separator) + 1, strNomPano.lastIndexOf(".")));
            tltpPano.setStyle(getStrTooltipStyle());
            Tooltip.install(ivPano[ij], tltpPano);
            ivPano[ij].setOnMouseClicked((mouseEvent) -> {
                panePanoramique.setCursor(Cursor.CROSSHAIR);
                panePanoramique.setOnMouseClicked(
                        (me1) -> {
                            gereSourisPanoramique(me1);
                        }
                );
                setStrPanoListeVignette(strNomPano);
                if (getPanoramiquesProjet()[iNumeroPano].getStrTitrePanoramique() != null) {
                    String strTexteHS = getPanoramiquesProjet()[iNumeroPano].getStrTitrePanoramique();
                    TextField tfTxtHS = (TextField) vbOutils.lookup("#txtHS" + iNumHS);
                    tfTxtHS.setText(strTexteHS);
                }
                getPanoramiquesProjet()[getiPanoActuel()].getHotspot(iNumHS).setNumeroPano(iNumeroPano);
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
        ImageView ivClose = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/ferme.png", 20, 20, true, true));
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

            setStrPanoListeVignette("");
            aplistePano.setVisible(false);
            mouseEvent.consume();
        });
        aplistePano.setTranslateZ(2);
        return aplistePano;
    }

    /**
     *
     */
    private static void valideHS() {

        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspots(); i++) {
            ComboBox cbPanos = (ComboBox) vbOutils.lookup("#cbpano" + i);
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHS" + i);
            if (tfTexteHS != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspot(i).setStrInfo(tfTexteHS.getText());
            }
            if (cbPanos != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspot(i).setStrFichierXML(cbPanos.getValue() + ".xml");
            }
            CheckBox cbAnime = (CheckBox) vbOutils.lookup("#anime" + i);
            if (cbAnime != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspot(i).setAnime(cbAnime.isSelected());
            }
        }
        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotImage(); i++) {
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHSImage" + i);
            if (tfTexteHS != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(i).setStrInfo(tfTexteHS.getText());
            }
            CheckBox cbAnime = (CheckBox) vbOutils.lookup("#animeImage" + i);
            if (cbAnime != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(i).setAnime(cbAnime.isSelected());
            }
        }
        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotHTML(); i++) {
            TextField tfTexteHS = (TextField) vbOutils.lookup("#txtHSHTML" + i);
            if (tfTexteHS != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(i).setStrInfo(tfTexteHS.getText());
            }
            CheckBox cbAnime = (CheckBox) vbOutils.lookup("#animeHTML" + i);
            if (cbAnime != null) {
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(i).setAnime(cbAnime.isSelected());
            }
        }

    }

    /**
     *
     * @param strLstPano
     * @param iNumPano
     * @return
     */
    public static Pane paneAffichageHS(String strLstPano, int iNumPano) {

        Pane paneHotSpots = new Pane();
        paneHotSpots.setTranslateY(10);
        paneHotSpots.setTranslateX(10);
        VBox vbHotspots = new VBox(5);
        paneHotSpots.getChildren().add(vbHotspots);
        Label lblPoint;
        int io;
        for (io = 0; io < getPanoramiquesProjet()[iNumPano].getNombreHotspots(); io++) {
            Label lblSep = new Label(" ");
            Label lblSep1 = new Label(" ");
            VBox vbPanneauHS = new VBox();
            double deplacement = 0;
            vbPanneauHS.setLayoutX(deplacement);
            Pane paneHsPanoramique = new Pane(vbPanneauHS);
            paneHsPanoramique.setPrefHeight(300);
            paneHsPanoramique.setMinHeight(300);
            paneHsPanoramique.setMaxHeight(300);

            int iNum1 = io;
            Timeline timBouge = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
                Circle c1 = (Circle) panePanoramique.lookup("#point" + iNum1);
                if (c1 != null) {
                    if (c1.getFill() == Color.RED) {
                        c1.setFill(Color.YELLOW);
                        c1.setStroke(Color.RED);
                    } else {
                        c1.setFill(Color.RED);
                        c1.setStroke(Color.YELLOW);
                    }
                }
            }));
            timBouge.setCycleCount(Timeline.INDEFINITE);
            timBouge.pause();
            paneHsPanoramique.setOnMouseEntered((e) -> {
                timBouge.play();
            });
            paneHsPanoramique.setOnMouseExited((e) -> {
                timBouge.pause();
                Circle c1 = (Circle) panePanoramique.lookup("#point" + iNum1);
                if (c1 != null) {
                    c1.setFill(Color.YELLOW);
                    c1.setStroke(Color.RED);
                }
            });
            paneHsPanoramique.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3px;");
            paneHsPanoramique.setId("HS" + io);
            lblPoint = new Label("Point #" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setTranslateX(-deplacement);
            lblPoint.getStyleClass().add("titreOutil");
            Separator sepHotspots = new Separator(Orientation.HORIZONTAL);
            sepHotspots.setTranslateX(-deplacement);
            sepHotspots.setPrefWidth(321);
            sepHotspots.setTranslateX(2);
            paneHsPanoramique.setPrefWidth(325);
            vbPanneauHS.getChildren().addAll(lblPoint, sepHotspots);
            if (strLstPano != null) {

                Label lblLien = new Label(rbLocalisation.getString("main.panoramiqueDestination"));
                lblLien.setTranslateX(10);
                ComboBox cbDestPano = new ComboBox();
                String[] strListe = strLstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(strListe));
                cbDestPano.valueProperty().addListener((ov, t, t1) -> {
                    valideHS();
                    if (dejaCharge) {
                        dejaCharge = false;
                        retireAffichageHotSpots();
                        Pane affHS1 = paneAffichageHS(strListePano(iNumPano), iNumPano);
                        affHS1.setId("labels");
                        vbVisuHotspots.getChildren().add(affHS1);
                        apVisuHS.setPrefHeight(affHS1.getPrefHeight());
                    }
                });
                cbDestPano.setTranslateX(60);
                cbDestPano.setId("cbpano" + io);

                String strF1XML = getPanoramiquesProjet()[iNumPano].getHotspot(io).getStrFichierXML();
                if (strF1XML != null) {
                    cbDestPano.setValue(strF1XML.split("\\.")[0]);
                }
                int iNum = cbDestPano.getSelectionModel().getSelectedIndex();
                if (strF1XML != null) {
                    int iNum11 = -1;
                    for (int ii = 0; ii < getiNombrePanoramiques(); ii++) {
                        String strChaine = getPanoramiquesProjet()[ii].getStrNomFichier();
                        strChaine = strChaine.substring(strChaine.lastIndexOf("\\") + 1, strChaine.length());
                        strChaine = strChaine.split("\\.")[0];
                        if (strChaine.equals(strF1XML.split("\\.")[0])) {
                            iNum11 = ii;
                        }
                    }
                    if (iNum11 != -1) {
                        int iNumPan = iNum11;
                        ImageView ivAfficheVignettePano = new ImageView(getPanoramiquesProjet()[iNum11].getImgVignettePanoramique());
                        ivAfficheVignettePano.setPreserveRatio(true);
                        ivAfficheVignettePano.setFitWidth(300);
                        ivAfficheVignettePano.setLayoutY(10);
                        ivAfficheVignettePano.setCursor(Cursor.HAND);
                        ivAfficheVignettePano.setOnMouseClicked((e) -> {
                            affichePanoChoisit(iNumPan);
                        });
                        AnchorPane apVisuVignettePano = new AnchorPane(ivAfficheVignettePano);
                        apVisuVignettePano.setPrefHeight(170);
                        apVisuVignettePano.setTranslateX(10);
                        vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, apVisuVignettePano, lblSep);
                    } else {
                        vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
                    }
                } else {
                    vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, lblSep);
                }

            }
            Label lblTexteHS = new Label(rbLocalisation.getString("main.texteHotspot"));
            lblTexteHS.setTranslateX(10);
            TextField tfTexteHS = new TextField();
            if (getPanoramiquesProjet()[iNumPano].getHotspot(io).getStrInfo() != null) {
                tfTexteHS.setText(getPanoramiquesProjet()[iNumPano].getHotspot(io).getStrInfo());
            }
            tfTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            tfTexteHS.setId("txtHS" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            tfTexteHS.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, tfTexteHS, lblSep1);
            vbHotspots.getChildren().addAll(paneHsPanoramique, lblSep);
        }
        int iNbHS = io;
        int iTaillePane = io * 325;
        for (io = 0; io < getPanoramiquesProjet()[iNumPano].getNombreHotspotImage(); io++) {
            Label lblSep = new Label(" ");
            Label lblSep1 = new Label(" ");
            VBox vbPanneauHsImage = new VBox();
            Pane paneHsImage = new Pane(vbPanneauHsImage);
            int iNum = io;
            Timeline timBouge = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
                Circle c1 = (Circle) panePanoramique.lookup("#img" + iNum);
                if (c1 != null) {
                    if (c1.getFill() == Color.BLUE) {
                        c1.setFill(Color.YELLOW);
                        c1.setStroke(Color.BLUE);
                    } else {
                        c1.setFill(Color.BLUE);
                        c1.setStroke(Color.YELLOW);
                    }
                }
            }));
            timBouge.setCycleCount(Timeline.INDEFINITE);
            timBouge.pause();
            paneHsImage.setOnMouseEntered((e) -> {
                timBouge.play();
            });
            paneHsImage.setOnMouseExited((e) -> {
                Circle c1 = (Circle) panePanoramique.lookup("#img" + iNum);
                if (c1 != null) {

                    c1.setFill(Color.BLUE);
                    c1.setStroke(Color.YELLOW);
                }
                timBouge.pause();
            });
            paneHsImage.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3px;");
            paneHsImage.setId("HSImg" + io);
            lblPoint = new Label("Image #" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.getStyleClass().add("titreOutil");
            Separator sepHS = new Separator(Orientation.HORIZONTAL);
            sepHS.setPrefWidth(321);
            sepHS.setTranslateX(2);

            paneHsImage.setPrefWidth(325);
            vbPanneauHsImage.getChildren().addAll(lblPoint, sepHS);
            Label lblLien = new Label(rbLocalisation.getString("main.imageChoisie"));
            lblLien.setTranslateX(10);

            String strF1XML = getPanoramiquesProjet()[iNumPano].getHotspotImage(io).getStrLienImg();
            Image imgChoisie = new Image("file:" + getStrRepertTemp() + File.separator + "images" + File.separator + strF1XML);
            ImageView ivChoisie = new ImageView(imgChoisie);
            ivChoisie.setTranslateX(100);
            ivChoisie.setFitWidth(100);
            ivChoisie.setFitHeight(imgChoisie.getHeight() / imgChoisie.getWidth() * 100);

            vbPanneauHsImage.getChildren().addAll(lblLien, ivChoisie, lblSep);
            Label lblTexteHS = new Label(rbLocalisation.getString("main.texteHotspot"));
            lblTexteHS.setTranslateX(10);

            TextField tfTexteHS = new TextField();
            if (getPanoramiquesProjet()[iNumPano].getHotspotImage(io).getStrInfo() != null) {
                tfTexteHS.setText(getPanoramiquesProjet()[iNumPano].getHotspotImage(io).getStrInfo());
            }
            tfTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            tfTexteHS.setId("txtHSImage" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            tfTexteHS.setTranslateX(60);
            vbPanneauHsImage.getChildren().addAll(lblTexteHS, tfTexteHS, lblSep1);
            vbHotspots.getChildren().addAll(paneHsImage, lblSep);
            iTaillePane += 145 + ivChoisie.getFitHeight();
            paneHsImage.setPrefHeight(120 + ivChoisie.getFitHeight());
            paneHsImage.setMinHeight(120 + ivChoisie.getFitHeight());
            paneHsImage.setMaxHeight(120 + ivChoisie.getFitHeight());
        }
        for (io = 0; io < getPanoramiquesProjet()[iNumPano].getNombreHotspotHTML(); io++) {
            Label lblSep = new Label(" ");
            int iNum = io;
            VBox vbPanneauHS = new VBox();
            Pane paneHsHtml = new Pane(vbPanneauHS);
            Timeline timBouge = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
                Circle c1 = (Circle) panePanoramique.lookup("#html" + iNum);
                if (c1 != null) {

                    if (c1.getFill() == Color.DARKGREEN) {
                        c1.setFill(Color.YELLOWGREEN);
                        c1.setStroke(Color.DARKGREEN);
                    } else {
                        c1.setFill(Color.DARKGREEN);
                        c1.setStroke(Color.YELLOWGREEN);
                    }
                }
            }));
            timBouge.setCycleCount(Timeline.INDEFINITE);
            timBouge.pause();
            paneHsHtml.setOnMouseEntered((e) -> {
                timBouge.play();
            });
            paneHsHtml.setOnMouseExited((e) -> {
                timBouge.pause();
                Circle c1 = (Circle) panePanoramique.lookup("#html" + iNum);
                if (c1 != null) {
                    c1.setFill(Color.DARKGREEN);
                    c1.setStroke(Color.YELLOWGREEN);
                }
            });
            paneHsHtml.setStyle("-fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 3px;");
            paneHsHtml.setId("HSHTML" + io);
            lblPoint = new Label("Hotspot HTML #" + (io + 1));
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.getStyleClass().add("titreOutil");
            Separator sepHS = new Separator(Orientation.HORIZONTAL);
            sepHS.setPrefWidth(321);
            sepHS.setTranslateX(2);
            paneHsHtml.setPrefWidth(325);
            Label lblTexteHS = new Label(rbLocalisation.getString("main.texteHotspot"));
            lblTexteHS.setTranslateX(10);
            TextField tfTexteHS = new TextField();
            if (getPanoramiquesProjet()[iNumPano].getHotspot(io).getStrInfo() != null) {
                tfTexteHS.setText(getPanoramiquesProjet()[iNumPano].getHotspotHTML(io).getStrInfo());
            }
            tfTexteHS.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                valideHS();
            });

            tfTexteHS.setId("txtHSHTML" + io);
            tfTexteHS.setPrefSize(200, 25);
            tfTexteHS.setMaxSize(200, 20);
            tfTexteHS.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblPoint, sepHS, lblTexteHS, tfTexteHS);
            Button btnEditeHSHTML = new Button(rbLocalisation.getString("main.editeHTML"));
            btnEditeHSHTML.setPrefWidth(80);
            btnEditeHSHTML.setTranslateX(paneHsHtml.getPrefWidth() - btnEditeHSHTML.getPrefWidth() - 10);
            vbPanneauHS.getChildren().addAll(btnEditeHSHTML);
            btnEditeHSHTML
                    .setOnAction((e) -> {
                        EditeurHTML editHTML = new EditeurHTML();
                        HotspotHTML HS = getPanoramiquesProjet()[iNumPano].getHotspotHTML(iNum);
                        editHTML.setHsHTML(HS);
                        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                        int iHauteur = (int) tailleEcran.getHeight() - 100;
                        int iLargeur = (int) tailleEcran.getWidth() - 100;
                        editHTML.affiche(iLargeur, iHauteur);
                        editHTML.addPropertyChangeListener("bValide", (ev) -> {
                            if (ev.getNewValue().toString().equals("true")) {
                                getPanoramiquesProjet()[iNumPano].setHotspotHTML(editHTML.getHsHTML(), iNum);
                                dejaCharge = false;
                                retireAffichageHotSpots();
                                Pane affHS1 = paneAffichageHS(strListePano(iNumPano), iNumPano);
                                affHS1.setId("labels");
                                vbVisuHotspots.getChildren().add(affHS1);
                                apVisuHS.setPrefHeight(affHS1.getPrefHeight());
                            }
                        });

                    });

            vbHotspots.getChildren().addAll(paneHsHtml, lblSep);
            paneHsHtml.setPrefHeight(120);
            paneHsHtml.setMinHeight(120);
            paneHsHtml.setMaxHeight(120);
            iTaillePane += 145;
        }
        valideHS();
        iNbHS += io;
        dejaCharge = true;
        paneHotSpots.setPrefHeight(iTaillePane);
        paneHotSpots.setMinHeight(iTaillePane);
        paneHotSpots.setMaxHeight(iTaillePane);
        return paneHotSpots;
    }

    /**
     *
     */
    private static void ajouteAffichageHotspots() {
        Pane paneLabels = (Pane) vbVisuHotspots.lookup("#labels");
        vbVisuHotspots.getChildren().remove(paneLabels);
        dejaCharge = false;
        paneLabels = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
        paneLabels.setId("labels");
        vbVisuHotspots.getChildren().add(paneLabels);
        apVisuHS.setPrefHeight(paneLabels.getPrefHeight());
        iNumPoints = getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspots();
        iNumImages = getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotImage();
        setiNumHTML(getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotHTML());
        if (apHS1.getApOutil().isVisible()){
            PaneOutil.deplieReplie(apHS1.getApOutil(), apHS1.getIvBtnPlusOutil());
            PaneOutil.deplieReplie(apHS1.getApOutil(), apHS1.getIvBtnPlusOutil());
        }
    }

    /**
     *
     * @return
     */
    private static ScrollPane spAfficheLegende() {
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
        positionY = (spVuePanoramique.getPrefHeight() - apLegende.getPrefHeight() - 15);

        Circle circPoint = new Circle(30, 20, 5);
        circPoint.setFill(Color.YELLOW);
        circPoint.setStroke(Color.RED);
        circPoint.setCursor(Cursor.DEFAULT);
        Circle circPoint2 = new Circle(180, 20, 5);
        circPoint2.setFill(Color.BLUE);
        circPoint2.setStroke(Color.YELLOW);
        circPoint2.setCursor(Cursor.DEFAULT);
        Circle circPoint3 = new Circle(330, 20, 5);
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
        Label lblHSHTML = new Label(rbLocalisation.getString("main.legendeHSHTML"));
        Label lblPoV = new Label(rbLocalisation.getString("main.legendePoV"));
        Label lblNord = new Label(rbLocalisation.getString("main.legendeNord"));
        Line lineNord = new Line(500, 45, 500, 65);
        lineNord.setStroke(Color.RED);
        lineNord.setStrokeWidth(3);
        lblHS.setLayoutX(50);
        lblHS.setLayoutY(15);
        lblHSImage.setLayoutX(200);
        lblHSImage.setLayoutY(15);
        lblHSHTML.setLayoutX(350);
        lblHSHTML.setLayoutY(15);
        lblPoV.setLayoutX(520);
        lblPoV.setLayoutY(15);
        lblNord.setLayoutX(520);
        lblNord.setLayoutY(55);
        apLegende.getChildren().addAll(lblHS, circPoint, lblHSImage, circPoint2, lblHSHTML, circPoint3, lblPoV, polygonCroix, lblNord, lineNord);
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
    private static void afficheHS(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.YELLOW);
        circPoint.setStroke(Color.RED);
        circPoint.setId("point" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);

        Tooltip tltpHotSpot = new Tooltip("point #" + (i + 1));
        tltpHotSpot.setStyle(getStrTooltipStyle());
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
            getPanoramiquesProjet()[getiPanoActuel()].getHotspot(iNumeroPoint).setLatitude(lat);
            getPanoramiquesProjet()[getiPanoActuel()].getHotspot(iNumeroPoint).setLongitude(longit);
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
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePoint);

                for (int io = numeroPoint + 1; io < iNumPoints; io++) {
                    nodePoint = (Node) panePanoramique.lookup("#point" + Integer.toString(io));
                    nodePoint.setId("point" + Integer.toString(io - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                dejaCharge = false;
                retireAffichageHotSpots();
                iNumPoints--;
                getPanoramiquesProjet()[getiPanoActuel()].removeHotspot(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                mouseEvent1.consume();
                valideHS();
            } else {
                if (!bDragDrop) {
                    if (getiNombrePanoramiques() > 1) {
                        AnchorPane apListePanoVig = apAfficherListePanosVignettes(numeroPoint);
                        int iLargeurVignettes = 4;
                        if (getiNombrePanoramiques() < 4) {
                            iLargeurVignettes = getiNombrePanoramiques();
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
    private static void afficheHSImage(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.BLUE);
        circPoint.setStroke(Color.YELLOW);
        circPoint.setId("img" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);
        Tooltip tltpHSImage = new Tooltip("image #" + (i + 1));
        tltpHSImage.setStyle(getStrTooltipStyle());
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
            getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNumeroPoint).setLatitude(lat);
            getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNumeroPoint).setLongitude(longit);
            circPoint.setFill(Color.BLUE);
            circPoint.setStroke(Color.YELLOW);
            mouseEvent1.consume();

        });

        circPoint.setOnMouseClicked((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(3, strPoint.length());
            int iNum = Integer.parseInt(strPoint);
            Node nodePointImage;
            nodePointImage = (Node) panePanoramique.lookup("#img" + strPoint);

            if (mouseEvent1.isControlDown()) {
                valideHS();
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePointImage);

                for (int io = iNum + 1; io < iNumImages; io++) {
                    nodePointImage = (Node) panePanoramique.lookup("#img" + Integer.toString(io));
                    nodePointImage.setId("img" + Integer.toString(io - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                iNumImages--;
                getPanoramiquesProjet()[getiPanoActuel()].removeHotspotImage(iNum);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                mouseEvent1.consume();
            } else {
                if (!bDragDrop) {
                    File fileRepert;
                    if (getStrRepertHSImages().equals("")) {
                        fileRepert = new File(getStrCurrentDir() + File.separator);
                    } else {
                        fileRepert = new File(getStrRepertHSImages());
                    }
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

                    fileChooser.setInitialDirectory(fileRepert);
                    fileChooser.getExtensionFilters().addAll(extFilterImages);

                    File fileFichierImage = fileChooser.showOpenDialog(null);
                    if (fileFichierImage != null) {
                        setStrRepertHSImages(fileFichierImage.getParent());
                        HotspotImage HS = getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNum);
                        HS.setStrUrlImage(fileFichierImage.getAbsolutePath());
                        HS.setStrLienImg(fileFichierImage.getName());
                        HS.setStrInfo(fileFichierImage.getName().split("\\.")[0]);
                        File fileRepertImage = new File(getStrRepertTemp() + File.separator + "images");
                        if (!fileRepertImage.exists()) {
                            fileRepertImage.mkdirs();
                        }
                        try {
                            copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());

                        } catch (IOException ex) {
                            Logger.getLogger(EditeurPanovisu.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        getPanoramiquesProjet()[getiPanoActuel()].setHotspotImage(HS, iNum);
                        retireAffichageHotSpots();
                        Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                        affHS1.setId("labels");
                        vbOutils.getChildren().add(affHS1);
                    }
                } else {
                    bDragDrop = false;
                }
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
    private static void afficheHSHTML(int i, double longitude, double latitude) {
        double largeur = ivImagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + ivImagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle circPoint = new Circle(X, Y, 5);
        circPoint.setFill(Color.DARKGREEN);
        circPoint.setStroke(Color.YELLOWGREEN);
        circPoint.setId("html" + i);
        circPoint.setCursor(Cursor.DEFAULT);
        panePanoramique.getChildren().add(circPoint);
        Tooltip tltpHSImage = new Tooltip("HTML #" + (i + 1));
        tltpHSImage.setStyle(getStrTooltipStyle());
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
            getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNumeroPoint).setLatitude(lat);
            getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNumeroPoint).setLongitude(longit);
            circPoint.setFill(Color.DARKGREEN);
            circPoint.setStroke(Color.YELLOWGREEN);
            mouseEvent1.consume();

        });

        circPoint.setOnMouseClicked((mouseEvent1) -> {
            String strPoint = circPoint.getId();
            strPoint = strPoint.substring(4, strPoint.length());
            int iNum = Integer.parseInt(strPoint);
            Node nodePointImage;
            nodePointImage = (Node) panePanoramique.lookup("#html" + strPoint);

            if (mouseEvent1.isControlDown()) {
                valideHS();
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                panePanoramique.getChildren().remove(nodePointImage);

                for (int io = iNum + 1; io < iNumImages; io++) {
                    nodePointImage = (Node) panePanoramique.lookup("#html" + Integer.toString(io));
                    nodePointImage.setId("img" + Integer.toString(io - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                setiNumHTML(getiNumHTML() - 1);
                getPanoramiquesProjet()[getiPanoActuel()].removeHotspotHTML(iNum);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                mouseEvent1.consume();
            } else {
                if (!bDragDrop) {
                    EditeurHTML editHTML = new EditeurHTML();
                    HotspotHTML HS = getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNum);
                    editHTML.setHsHTML(HS);
                    Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                    int iHauteur = (int) tailleEcran.getHeight() - 100;
                    int iLargeur = (int) tailleEcran.getWidth() - 100;
                    editHTML.affiche(iLargeur, iHauteur);
                    editHTML.addPropertyChangeListener("bValide", (ev) -> {
                        if (ev.getNewValue().toString().equals("true")) {
                            getPanoramiquesProjet()[getiPanoActuel()].setHotspotHTML(editHTML.getHsHTML(), iNum);
                            dejaCharge = false;
                            retireAffichageHotSpots();
                            Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                            affHS1.setId("labels");
                            vbVisuHotspots.getChildren().add(affHS1);
                            apVisuHS.setPrefHeight(affHS1.getPrefHeight());
                        }
                    });
                } else {
                    bDragDrop = false;
                }
                mouseEvent1.consume();
            }

        });
    }

    /**
     *
     */
    private static void ajouteAffichagePointsHotspots() {

        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspots(); i++) {
            double longitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspot(i).getLongitude();
            double latitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private static void ajouteAffichagePointsHotspotsImage() {
        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotImage(); i++) {
            double longitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(i).getLongitude();
            double latitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(i).getLatitude();
            afficheHSImage(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private static void ajouteAffichagePointsHotspotsHTML() {
        for (int i = 0; i < getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspotHTML(); i++) {
            double longitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(i).getLongitude();
            double latitude = getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(i).getLatitude();
            afficheHSHTML(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private void sauveFichiers() throws IOException {
        if (!isbDejaSauve()) {
            projetSauve();
        }
    }

    /**
     *
     * @param X
     * @param Y
     */
    private static void panoChoixRegard(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            double largeur = ivImagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            double regardY = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            getPanoramiquesProjet()[getiPanoActuel()].setRegardX(regardX);
            getPanoramiquesProjet()[getiPanoActuel()].setRegardY(regardY);
            navigateurPanoramique.setLongitude(regardX - 180);
            navigateurPanoramique.setLatitude(regardY);
            navigateurPanoramique.affiche();

            affichePoV(regardX, regardY);
        }
    }

    /**
     *
     * @param X
     */
    private static void panoChoixNord(double X) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            double mouseX = X;
            double largeur = ivImagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            getPanoramiquesProjet()[getiPanoActuel()].setZeroNord(regardX);
            navigateurPanoramique.setPositNord(regardX - 180);
            navigateurPanoramique.affiche();
            afficheNord(regardX);
        }
    }

    /**
     *
     * @param longitude
     */
    private static void afficheNord(double longitude) {
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
            getPanoramiquesProjet()[getiPanoActuel()].setZeroNord(regardX);
            lineNord.setStroke(Color.RED);
            me1.consume();

        });

        panePanoramique.getChildren().add(lineNord);
    }

    /**
     *
     * @param longitude
     * @param latitude
     */
    private static void affichePoV(double longitude, double latitude) {
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
            getPanoramiquesProjet()[getiPanoActuel()].setRegardX(regardX);
            getPanoramiquesProjet()[getiPanoActuel()].setRegardY(regardY);
            plgPoV.setFill(Color.BLUEVIOLET);
            plgPoV.setStroke(Color.YELLOW);
            mouseEvent1.consume();

        });

        panePanoramique.getChildren().add(plgPoV);
    }

    /**
     *
     * @param X
     * @param Y
     */
    private static void panoMouseClic(double X, double Y) {

        if (getiNombrePanoramiques() > 1) {
            valideHS();
            setbDejaSauve(false);
            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
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
                tltpPoint.setStyle(getStrTooltipStyle());
                Tooltip.install(circPoint, tltpPoint);
                HotSpot HS = new HotSpot();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                getPanoramiquesProjet()[getiPanoActuel()].addHotspot(HS);
                retireAffichageHotSpots();
                dejaCharge = false;
                Pane paneAfficheHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                paneAfficheHS1.setId("labels");
                vbVisuHotspots.getChildren().add(paneAfficheHS1);
                apVisuHS.setPrefHeight(paneAfficheHS1.getPrefHeight());

                iNumPoints++;
                if (getiNombrePanoramiques() > 1) {

                    AnchorPane apListePanoVig = apAfficherListePanosVignettes(getPanoramiquesProjet()[getiPanoActuel()].getNombreHotspots() - 1);
                    int iLargeurVignettes = 4;
                    if (getiNombrePanoramiques() < 4) {
                        iLargeurVignettes = getiNombrePanoramiques();
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
                    getPanoramiquesProjet()[getiPanoActuel()].getHotspot(iNumeroPoint).setLatitude(lat);
                    getPanoramiquesProjet()[getiPanoActuel()].getHotspot(iNumeroPoint).setLongitude(longit);
                    circPoint.setFill(Color.YELLOW);
                    circPoint.setStroke(Color.RED);
                    mouseEvent1.consume();

                });

                circPoint.setOnMouseClicked((mouseEvent1) -> {
                    if (mouseEvent1.isControlDown()) {
                        setbDejaSauve(false);
                        getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
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
                        getPanoramiquesProjet()[getiPanoActuel()].removeHotspot(iNumeroPoint);
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
                            if (getiNombrePanoramiques() > 1) {
                                AnchorPane listePanoVig = apAfficherListePanosVignettes(iNumeroPoint);
                                int iLargeurVignettes = 4;
                                if (getiNombrePanoramiques() < 4) {
                                    iLargeurVignettes = getiNombrePanoramiques();
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

    /**
     *
     * @param X
     * @param Y
     */
    private static void panoAjouteImage(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            valideHS();
            setbDejaSauve(false);
            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
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
            tltpImage.setStyle(getStrTooltipStyle());
            Tooltip.install(circPoint, tltpImage);
            File fileRepert;
            if (getStrRepertHSImages().equals("")) {
                fileRepert = new File(getStrCurrentDir() + File.separator);
            } else {
                fileRepert = new File(getStrRepertHSImages());
            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                setStrRepertHSImages(fileFichierImage.getParent());
                iNumImages++;
                HotspotImage HS = new HotspotImage();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                HS.setStrUrlImage(fileFichierImage.getAbsolutePath());
                HS.setStrLienImg(fileFichierImage.getName());
                HS.setStrInfo(fileFichierImage.getName().split("\\.")[0]);
                File fileRepertImage = new File(getStrRepertTemp() + File.separator + "images");
                if (!fileRepertImage.exists()) {
                    fileRepertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());

                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                getPanoramiquesProjet()[getiPanoActuel()].addHotspotImage(HS);
                retireAffichageHotSpots();
                dejaCharge = false;
                Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                affHS1.setId("labels");
                vbVisuHotspots.getChildren().add(affHS1);
                apVisuHS.setPrefHeight(affHS1.getPrefHeight());

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
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNumeroPoint).setLatitude(lat);
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNumeroPoint).setLongitude(longit);
                circPoint.setFill(Color.BLUE);
                circPoint.setStroke(Color.YELLOW);
                mouseEvent1.consume();

            });

            circPoint.setOnMouseClicked((mouseEvent1) -> {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(3, strPoint.length());
                int iNum = Integer.parseInt(strPoint);
                if (mouseEvent1.isControlDown()) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    Node nodeImage;
                    nodeImage = (Node) panePanoramique.lookup("#img" + strPoint);
                    panePanoramique.getChildren().remove(nodeImage);

                    for (int io = iNum + 1; io < iNumImages; io++) {
                        nodeImage = (Node) panePanoramique.lookup("#img" + Integer.toString(io));
                        nodeImage.setId("img" + Integer.toString(io - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    iNumImages--;
                    getPanoramiquesProjet()[getiPanoActuel()].removeHotspotImage(iNum);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                } else {
                    if (!bDragDrop) {
                        File fileRepert1;
                        if (getStrRepertHSImages().equals("")) {
                            fileRepert1 = new File(getStrCurrentDir() + File.separator);
                        } else {
                            fileRepert1 = new File(getStrRepertHSImages());
                        }
                        FileChooser fileChooser1 = new FileChooser();
                        FileChooser.ExtensionFilter extFilterImages1 = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

                        fileChooser1.setInitialDirectory(fileRepert1);
                        fileChooser1.getExtensionFilters().addAll(extFilterImages1);

                        File fileFichierImage1 = fileChooser1.showOpenDialog(null);
                        if (fileFichierImage1 != null) {
                            setStrRepertHSImages(fileFichierImage1.getParent());
                            HotspotImage HS = getPanoramiquesProjet()[getiPanoActuel()].getHotspotImage(iNum);
                            HS.setStrUrlImage(fileFichierImage1.getAbsolutePath());
                            HS.setStrLienImg(fileFichierImage1.getName());
                            HS.setStrInfo(fileFichierImage1.getName().split("\\.")[0]);
                            File fileRepertImage = new File(getStrRepertTemp() + File.separator + "images");
                            if (!fileRepertImage.exists()) {
                                fileRepertImage.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(fileFichierImage1.getAbsolutePath(), fileRepertImage.getAbsolutePath());

                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                            getPanoramiquesProjet()[getiPanoActuel()].setHotspotImage(HS, iNum);
                            retireAffichageHotSpots();
                            dejaCharge = false;
                            Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                            affHS1.setId("labels");
                            vbVisuHotspots.getChildren().add(affHS1);
                            apVisuHS.setPrefHeight(affHS1.getPrefHeight());
                        }
                    } else {
                        bDragDrop = false;
                    }
                }
                valideHS();
                mouseEvent1.consume();
            });

        }
    }

    /**
     *
     * @param X
     * @param Y
     */
    private static void panoAjouteHTML(double X, double Y) {
        if (X > 0 && X < ivImagePanoramique.getFitWidth()) {
            valideHS();
            setbDejaSauve(false);
            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - panePanoramique.getLayoutY() - 109;
            double longitude, latitude;
            double largeur = ivImagePanoramique.getFitWidth();
            longitude = 360.0f * mouseX / largeur - 180;
            latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            Circle circPoint = new Circle(mouseX + ivImagePanoramique.getLayoutX(), mouseY, 5);
            circPoint.setFill(Color.DARKGREEN);
            circPoint.setStroke(Color.YELLOWGREEN);
            circPoint.setId("html" + getiNumHTML());
            circPoint.setCursor(Cursor.DEFAULT);
            panePanoramique.getChildren().add(circPoint);
            Tooltip tltpImage = new Tooltip("HTML n° " + (getiNumHTML() + 1));
            tltpImage.setStyle(getStrTooltipStyle());
            Tooltip.install(circPoint, tltpImage);
            EditeurHTML editHTML = new EditeurHTML();
            HotspotHTML HS = new HotspotHTML();
            editHTML.setHsHTML(HS);
            HS.setLongitude(longitude);
            HS.setLatitude(latitude);
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int iHauteur = (int) tailleEcran.getHeight() - 100;
            int iLargeur = (int) tailleEcran.getWidth() - 100;

            editHTML.affiche(iLargeur, iHauteur);

            editHTML.addPropertyChangeListener("bValide", (e) -> {
                if (e.getNewValue().toString().equals("true")) {
                    setiNumHTML(getiNumHTML() + 1);
                    getPanoramiquesProjet()[getiPanoActuel()].addHotspotHTML(editHTML.getHsHTML());
                    retireAffichageHotSpots();
                    dejaCharge = false;
                    Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                    affHS1.setId("labels");
                    vbOutils.getChildren().add(affHS1);
                }
            });
            editHTML.addPropertyChangeListener("bAnnule", (e) -> {
                if (e.getNewValue().toString().equals("true")) {
                    String strPoint = circPoint.getId();
                    strPoint = strPoint.substring(4, strPoint.length());
                    Node nodeImage = (Node) panePanoramique.lookup("#html" + strPoint);
                    panePanoramique.getChildren().remove(nodeImage);
                }
            });

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
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNumeroPoint).setLatitude(lat);
                getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNumeroPoint).setLongitude(longit);
                circPoint.setFill(Color.DARKGREEN);
                circPoint.setStroke(Color.YELLOWGREEN);
                mouseEvent1.consume();

            });

            circPoint.setOnMouseClicked((mouseEvent1) -> {
                String strPoint = circPoint.getId();
                strPoint = strPoint.substring(4, strPoint.length());
                int iNum = Integer.parseInt(strPoint);
                if (mouseEvent1.isControlDown()) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    Node nodeImage;
                    nodeImage = (Node) panePanoramique.lookup("#html" + strPoint);
                    panePanoramique.getChildren().remove(nodeImage);

                    for (int io = iNum + 1; io < getiNumHTML(); io++) {
                        nodeImage = (Node) panePanoramique.lookup("#html" + Integer.toString(io));
                        nodeImage.setId("html" + Integer.toString(io - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    setiNumHTML(getiNumHTML() - 1);
                    getPanoramiquesProjet()[getiPanoActuel()].removeHotspotHTML(iNum);
                    /**
                     * On les crée les nouvelles
                     */
                    ajouteAffichageHotspots();
                } else {
                    if (!bDragDrop) {
                        EditeurHTML editHTML1 = new EditeurHTML();
                        HotspotHTML HS1 = getPanoramiquesProjet()[getiPanoActuel()].getHotspotHTML(iNum);
                        editHTML1.setHsHTML(HS1);
                        Dimension tailleEcran1 = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                        int iHauteur1 = (int) tailleEcran1.getHeight() - 100;
                        int iLargeur1 = (int) tailleEcran1.getWidth() - 100;
                        editHTML1.affiche(iLargeur1, iHauteur1);
                        editHTML1.addPropertyChangeListener("bValide", (ev) -> {
                            if (ev.getNewValue().toString().equals("true")) {
                                getPanoramiquesProjet()[getiPanoActuel()].setHotspotHTML(editHTML1.getHsHTML(), iNum);
                                retireAffichageHotSpots();
                                dejaCharge = false;
                                Pane affHS1 = paneAffichageHS(strListePano(getiPanoActuel()), getiPanoActuel());
                                affHS1.setId("labels");
                                vbVisuHotspots.getChildren().add(affHS1);
                                apVisuHS.setPrefHeight(affHS1.getPrefHeight());
                            }
                        });
                    } else {
                        bDragDrop = false;
                    }
                    mouseEvent1.consume();
                }
                valideHS();
                mouseEvent1.consume();
            });

        }
    }

    /**
     *
     * @param mouseEvent
     */
    private static void gereSourisPanoramique(MouseEvent mouseEvent) {
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
                    if (getiNombrePanoramiques() > 1) {
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
                    lvMenuChoixTypeHotspot.setLayoutY(mouseEvent.getSceneY() - panePanoramique.getLayoutY() - 104);
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

                } else {
                    bDragDrop = false;
                }
            }
        }
    }

    /**
     *
     */
    private static void installeEvenements() {
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
        cbListeChoixPanoramique.valueProperty()
                .addListener((ov, ancienneValeur, nouvelleValeur) -> {
                    if (nouvelleValeur != null) {
                        if (!(nouvelleValeur.equals(strPanoAffiche))) {
                            valideHS();
                            strPanoAffiche = nouvelleValeur.toString();
                        }
                    }
                });

    }

    /**
     *
     */
    private static void ajouteAffichageLignes() {
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
    private static void retireAffichageLigne() {
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
    public static void affichePanoChoisit(int iNumPanochoisi) {
        ivImagePanoramique.setImage(getPanoramiquesProjet()[iNumPanochoisi].getImgPanoramique());
        Node nodeLegende = (Node) apPanneauPrincipal.lookup("#legende");
        nodeLegende.setVisible(true);
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        retireAffichageLigne();
        setiPanoActuel(iNumPanochoisi);

        iNumPoints = 0;
        if (navigateurPanoramique == null) {
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int iHauteur = (int) tailleEcran.getHeight() - 170;
            int iLargeur = (int) tailleEcran.getWidth() - 20;
            apVisuPanoramique.getChildren().clear();
            Pane paneFond = new Pane();
            paneFond.setPrefSize(24, 24);
            Image imgExpand = new Image("file:" + getStrRepertAppli() + "/images/expand.png", 32, 24, false, true);
            Image imgShrink = new Image("file:" + getStrRepertAppli() + "/images/shrink.png", 32, 24, false, true);
            ImageView ivExpShrk = new ImageView(imgExpand);
            paneFond.setLayoutX(10);
            paneFond.setLayoutY(10);
            paneFond.getChildren().add(ivExpShrk);
            navigateurPanoramique
                    = new NavigateurPanoramique(getPanoramiquesProjet()[iNumPanochoisi].getStrNomFichier(), 10.d, 10.d, 340, 200);
            apVisuPano = navigateurPanoramique.affichePano();
            navigateurPanoramique.addPropertyChangeListener("positNord", (e) -> {
                double longitude = Math.round(Double.parseDouble(e.getNewValue().toString()) * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                getPanoramiquesProjet()[getiPanoActuel()].setZeroNord(longitude);
                afficheNord(longitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixLatitude", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;

                getPanoramiquesProjet()[getiPanoActuel()].setRegardX(longitude);
                getPanoramiquesProjet()[getiPanoActuel()].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixLongitude", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                getPanoramiquesProjet()[getiPanoActuel()].setRegardX(longitude);
                getPanoramiquesProjet()[getiPanoActuel()].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.addPropertyChangeListener("choixFov", (e) -> {
                double latitude = Math.round(navigateurPanoramique.getChoixLatitude() * 10) / 10.d;
                double longitude = Math.round(navigateurPanoramique.getChoixLongitude() * 10) / 10.d - 180;
                longitude = longitude % 360;
                longitude = longitude < 0 ? longitude + 360 : longitude;
                longitude = longitude > 180 ? longitude - 360 : longitude;
                getPanoramiquesProjet()[getiPanoActuel()].setRegardX(longitude);
                getPanoramiquesProjet()[getiPanoActuel()].setRegardY(latitude);
                affichePoV(longitude, latitude);
            });
            navigateurPanoramique.setPositNord(getPanoramiquesProjet()[iNumPanochoisi].getZeroNord() - 180);
            navigateurPanoramique.setLongitude(getPanoramiquesProjet()[iNumPanochoisi].getRegardX() - 180);
            navigateurPanoramique.setLatitude(getPanoramiquesProjet()[iNumPanochoisi].getRegardY());
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
            navigateurPanoramique.setImagePanoramique(getPanoramiquesProjet()[iNumPanochoisi].getStrNomFichier(), getPanoramiquesProjet()[iNumPanochoisi].getImgVisuPanoramique());
            navigateurPanoramique.setPositNord(getPanoramiquesProjet()[iNumPanochoisi].getZeroNord() - 180);
            navigateurPanoramique.setLongitude(getPanoramiquesProjet()[iNumPanochoisi].getRegardX() - 180);
            navigateurPanoramique.setLatitude(getPanoramiquesProjet()[iNumPanochoisi].getRegardY());
            navigateurPanoramique.affiche();
        }
        if (getPanoramiquesProjet()[iNumPanochoisi].getMarqueurGeolocatisation() != null) {
            tfLatitude.setText(CoordonneesGeographiques.toDMS(getPanoramiquesProjet()[iNumPanochoisi].getMarqueurGeolocatisation().getLatitude()));
            tfLongitude.setText(CoordonneesGeographiques.toDMS(getPanoramiquesProjet()[iNumPanochoisi].getMarqueurGeolocatisation().getLongitude()));
            getPoGeolocalisation().setbValide(true);
        } else {
            if (isbInternet()) {
                tfLatitude.setText("");
                tfLongitude.setText("");
                getPoGeolocalisation().setbValide(false);
            }

        }
        ajouteAffichagePointsHotspots();
        ajouteAffichagePointsHotspotsImage();
        ajouteAffichagePointsHotspotsHTML();

        ajouteAffichageHotspots();
        affichePoV(getPanoramiquesProjet()[iNumPanochoisi].getRegardX(), getPanoramiquesProjet()[iNumPanochoisi].getRegardY());
        afficheNord(getPanoramiquesProjet()[iNumPanochoisi].getZeroNord());

        ajouteAffichageLignes();
        afficheInfoPano();
        cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(getiPanoActuel()));
        int iPano = -1;
        int ii = 0;
        for (String stNumPano : ordPano.getStrPanos()) {
            if (Integer.parseInt(stNumPano) == iNumPanochoisi) {
                iPano = ii;
            }
            ii++;
        }
        rectVignettePano.setLayoutY((iLargeurVignettes / 2 + 10) * iPano + 10);
        rectVignettePano.setVisible(true);
    }

    /**
     *
     */
    private static void afficheInfoPano() {
        TextField tfTitrePano = (TextField) scnPrincipale.lookup("#txttitrepano");
        if (getPanoramiquesProjet()[getiPanoActuel()].getStrTitrePanoramique() != null) {
            tfTitrePano.setText(getPanoramiquesProjet()[getiPanoActuel()].getStrTitrePanoramique());
        } else {
            tfTitrePano.setText("");
        }
    }

    /**
     *
     * @param imgRect
     * @param iRapport
     * @return
     */
    private static Image imgTransformationImage(Image imgRect, int iRapport) {
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
    private static void ajoutePanoramiqueProjet(String fichierPano, String typePano, int iNumero, int iTotal) throws InterruptedException {
        bPanoCharge = true;
        bEstCharge = true;
        Panoramique panoCree = new Panoramique();
        if (typePano.equals(Panoramique.SPHERE)) {
            panoCree.setStrNomFichier(fichierPano);
            String strNom = fichierPano.substring(0, fichierPano.length() - 4);
            int iNombreNiveaux = iCreeNiveauxImageEqui(fichierPano, getStrRepertPanos(), iNumero, iTotal);
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
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                imgPanoRetaille2 = new Image("file:" + fichierPano, 1200, 600, true, true, true);
                imgPano3 = new Image("file:" + fichierPano, 300, 150, true, true, true);
                panoCree.setImgVisuPanoramique(imgTransformationImage(new Image("file:" + fichierPano), 2));
            }
            panoCree.setImgPanoramique(imgPanoRetaille2);
            panoCree.setNombreNiveaux(iNombreNiveaux);
            panoCree.setImgVignettePanoramique(imgPano3);
            panoCree.setStrTypePanoramique(Panoramique.SPHERE);
        }
        if (typePano.equals(Panoramique.CUBE)) {
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
                    Logger.getLogger(EditeurPanovisu.class
                            .getName()).log(Level.SEVERE, null, ex);
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
            int iNombreNiveaux = iCreeNiveauxImageCube(strNom + "_u." + strExtension, getStrRepertPanos(), "dessus");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 4.d / 9.d) / (double) iTotal);
            });

            iCreeNiveauxImageCube(strNom + "_d." + strExtension, getStrRepertPanos(), "dessous");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 5.d / 9.d) / (double) iTotal);
            });
            iCreeNiveauxImageCube(strNom + "_l." + strExtension, getStrRepertPanos(), "gauche");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 2.d / 3.d) / (double) iTotal);
            });

            iCreeNiveauxImageCube(strNom + "_r." + strExtension, getStrRepertPanos(), "droite");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 7.d / 9.d) / (double) iTotal);
            });

            iCreeNiveauxImageCube(strNom + "_f." + strExtension, getStrRepertPanos(), "devant");
            Platform.runLater(() -> {
                pbarAvanceChargement.setProgress(((double) (iNumero - 1) + 8.d / 9.d) / (double) iTotal);
            });

            iCreeNiveauxImageCube(strNom + "_b." + strExtension, getStrRepertPanos(), "derrière");
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
        panoCree.setStrTitrePanoramique("");
        getPanoramiquesProjet()[getiNombrePanoramiques()] = panoCree;
        setiPanoActuel(getiNombrePanoramiques());
        setiNombrePanoramiques(getiNombrePanoramiques() + 1);
    }

    /**
     *
     */
    private static void transformationCube2Equi() {
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
    private static void transformationEqui2Cube() {
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
    private static void modeleSauver() throws IOException {
        File fileTemplate;
        FileChooser fcRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier Modèle panoVisu (*.tpl)", "*.tpl");
        fcRepertChoix.getExtensionFilters().add(extFilter);
        File fileRepert = new File(getStrRepertAppli() + File.separator + "templates");
        if (!fileRepert.exists()) {
            fileRepert.mkdirs();
        }
        fcRepertChoix.setInitialDirectory(fileRepert);
        fileTemplate = fcRepertChoix.showSaveDialog(null);

        if (fileTemplate != null) {
            String strContenuFichier = getGestionnaireInterface().strGetTemplate();
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
     * @throws IOException
     */
    private static void modeleCharger() throws IOException {
        FileChooser fileRepertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.tpl)", "*.tpl");
        fileRepertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(getStrRepertAppli() + File.separator + "templates");
        fileRepertChoix.setInitialDirectory(repert);
        File filetemplate = fileRepertChoix.showOpenDialog(getStPrincipal());
        if (filetemplate != null) {
            try {
                List<String> strListTemplate = new ArrayList<>();
                BufferedReader brTemplate = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filetemplate), "UTF-8"));

                String strLigneTexte;
                while ((strLigneTexte = brTemplate.readLine()) != null) {
                    strListTemplate.add(strLigneTexte);
                }
                getGestionnaireInterface().setbTemplate(true);
                getGestionnaireInterface().setTemplate(strListTemplate);
                getGestionnaireInterface().afficheTemplate();
                getGestionnaireInterface().setbTemplate(false);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     *
     * @param iNumZone
     * @param points
     * @return
     */
    private static ObservableList<AncreForme> olCreeAncresPourPolygone(int iNumZone,
            final ObservableList<Double> points) {
        ObservableList<AncreForme> olAnchors = FXCollections.observableArrayList();

        for (int i = 0; i < points.size(); i += 2) {
            final int idx = i;

            DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

            xProperty.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
                points.set(idx, (double) x);
                String chaine = "";
                chaine = points.stream().map((point) -> point.toString() + ",").reduce(chaine, String::concat);
                chaine = chaine.substring(0, chaine.length() - 1);
                zones[iNumZone].setStrCoordonneesZone(chaine);
            });

            yProperty.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
                points.set(idx + 1, (double) y);
                String chaine = "";
                chaine = points.stream().map((point) -> point.toString() + ",").reduce(chaine, String::concat);
                chaine = chaine.substring(0, chaine.length() - 1);
                zones[iNumZone].setStrCoordonneesZone(chaine);
            });

            olAnchors.add(new AncreForme(Color.GOLD, xProperty, yProperty));
        }

        return olAnchors;
    }

    /**
     *
     * @param iNumZone
     * @param cercle
     * @return
     */
    private static ObservableList<AncreForme> olCreeAncresPourCercle(int iNumZone,
            Circle cercle) {
        ObservableList<AncreForme> olAnchors = FXCollections.observableArrayList();

        DoubleProperty xProperty1 = new SimpleDoubleProperty(cercle.getCenterX());
        DoubleProperty yProperty1 = new SimpleDoubleProperty(cercle.getCenterY());
        olAnchors.add(new AncreForme(Color.GOLD, xProperty1, yProperty1));
        DoubleProperty xProperty2 = new SimpleDoubleProperty(cercle.getCenterX() + cercle.getRadius());
        DoubleProperty yProperty2 = new SimpleDoubleProperty(cercle.getCenterY());
        final AncreForme ancRayon = new AncreForme(Color.BLUEVIOLET, xProperty2, yProperty2);
        olAnchors.add(ancRayon);
        xProperty1.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double dX = (double) x - cercle.getCenterX();
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setCenterX((double) x);
            ancRayon.setCenterX(ancRayon.getCenterX() + dX);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);

        });

        yProperty1.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double dY = -cercle.getCenterY() + (double) y;
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setCenterY((double) y);
            ancRayon.setCenterY(ancRayon.getCenterY() + dY);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });
        xProperty2.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - (double) x, 2.d) + Math.pow(cercle.getCenterY() - yProperty2.get(), 2.d));
            cercle.setRadius(rayon);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });

        yProperty2.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double rayon = Math.sqrt(Math.pow(cercle.getCenterX() - xProperty2.get(), 2.d) + Math.pow(cercle.getCenterY() - (double) y, 2.d));
            cercle.setRadius(rayon);
            String chaine = Math.round(cercle.getCenterX() * 10) / 10 + "," + Math.round(cercle.getCenterY() * 10) / 10 + "," + Math.round(rayon * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });

        return olAnchors;
    }

    /**
     *
     * @param iNumZone
     * @param rect
     * @return
     */
    private static ObservableList<AncreForme> olCreeAncresPourRectangle(int iNumZone,
            Rectangle rect) {
        ObservableList<AncreForme> olAnchors = FXCollections.observableArrayList();

        DoubleProperty xProperty1 = new SimpleDoubleProperty(rect.getX());
        DoubleProperty yProperty1 = new SimpleDoubleProperty(rect.getY());
        DoubleProperty xProperty2 = new SimpleDoubleProperty(rect.getWidth() + rect.getX());
        DoubleProperty yProperty2 = new SimpleDoubleProperty(rect.getHeight() + rect.getY());
        olAnchors.add(new AncreForme(Color.GOLD, xProperty1, yProperty1));
        AncreForme ancrePoint2 = new AncreForme(Color.BLUEVIOLET, xProperty2, yProperty2);
        olAnchors.add(ancrePoint2);
        xProperty1.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            double dX = -rect.getX() + (double) x;
            rect.setX((double) x);
            ancrePoint2.setCenterX(ancrePoint2.getCenterX() + dX);
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });

        yProperty1.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            double dY = -rect.getY() + (double) y;
            rect.setY((double) y);
            ancrePoint2.setCenterY(ancrePoint2.getCenterY() + dY);
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });
        xProperty2.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x) -> {
            rect.setWidth((double) x - rect.getX());
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });

        yProperty2.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y) -> {
            rect.setHeight((double) y - rect.getY());
            String chaine = Math.round(rect.getX() * 10) / 10
                    + "," + Math.round(rect.getY() * 10) / 10
                    + "," + Math.round((rect.getX() + rect.getWidth()) * 10) / 10
                    + "," + Math.round((rect.getY() + rect.getHeight()) * 10) / 10;
            zones[iNumZone].setStrCoordonneesZone(chaine);
        });

        return olAnchors;
    }

    /**
     *
     * @param iLargeur
     * @param iHauteur
     * @param bMasqueZones
     * @param strIdZone
     * @param mouseEvent
     */
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
                }
                if (index != -1) {
                    cbTouchesBarre.getSelectionModel().select(index);
                }

                cbTouchesBarre.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {
                    if (nouvelleValeur != null) {
                        String strId = strCodeBarre[cbTouchesBarre.getSelectionModel().getSelectedIndex()];
                        zones[iNumeroZone].setStrIdZone(strId);
                    }
                });

                Label lblTypeBarre = new Label(zone.getStrTypeZone());
                lblTypeBarre.setLayoutX(20);
                lblTypeBarre.setLayoutY(40);
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
                        apImgBarrePersonnalisee.getChildren().addAll(olCreeAncresPourPolygone(iNumeroZone, poly.getPoints()));
                        break;
                    case "rect":
                        Rectangle rect = (Rectangle) apImgBarrePersonnalisee.lookup("#" + strIdZone);
                        rect.setFill(Color.rgb(255, 0, 0, 0.5));
                        rect.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(olCreeAncresPourRectangle(iNumeroZone, rect));
                        break;
                    case "circle":
                        Circle cercle = (Circle) apImgBarrePersonnalisee.lookup("#" + strIdZone);
                        cercle.setFill(Color.rgb(255, 0, 0, 0.5));
                        cercle.setStroke(Color.YELLOW);
                        apImgBarrePersonnalisee.getChildren().addAll(olCreeAncresPourCercle(iNumeroZone, cercle));
                        break;
                }

            }
        }

    }

    /**
     *
     * @param iLargeur
     * @param iHauteur
     * @param bMasqueZones
     */
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
        if ((iNombreZones > 0) && !bMasqueZones) {
            for (int i = 0; i < iNombreZones; i++) {
                ZoneTelecommande zone = zones[i];
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

    /**
     *
     * @param iLargeur
     * @param iHauteur
     * @param bMasqueZones
     */
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
        Button btnAnnuler = new Button(rbLocalisation.getString("main.annuler"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/annule.png")));
        Button btnValider = new Button(rbLocalisation.getString("main.valider"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/valide.png")));
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

        cbTouchesBarre.valueProperty().addListener((ov, strAncienneValeur, strNouvelleValeur) -> {
            if (strNouvelleValeur != null) {
                String strId = strCodeBarre[cbTouchesBarre.getSelectionModel().getSelectedIndex()];
                zone.setStrIdZone(strId);
            }
        });

        tgTypeZone.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (tgTypeZone.getSelectedToggle() != null) {
                strTypeZone = tgTypeZone.getSelectedToggle().getUserData().toString();
                zone.setStrTypeZone(strTypeZone);
            }
        });

    }

    /**
     *
     * @param fichShp
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("null")
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

    /**
     *
     * @param strNomFichierBarre
     */
    public static void creerEditerBarre(String strNomFichierBarre) {
        apCreationBarre.getChildren().clear();
        apCreationBarre.setStyle("-fx-background-color : -fx-base;"
                + "-fx-border-color: derive(-fx-base,10%);"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");

        AnchorPane apOutilsBarre = new AnchorPane();
        Button btnAnnulerBarre = new Button(rbLocalisation.getString("main.quitter"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/annule.png")));
        final Button btnSauverBarre = new Button(rbLocalisation.getString("main.sauver"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/sauveProjet.png", 24, 24, true, true, true)));
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        btnAjouteZone = new Button(rbLocalisation.getString("main.ajouteZone"), new ImageView(new Image("file:" + getStrRepertAppli() + "/images/btn+.png", 24, 24, true, true, true)));
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
                    iNombreZones = getGestionnaireInterface().lisFichierShp(strNomFichierShp, zones);
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
        btnChargeImage.setOnMouseClicked((t) -> {
            strRepertBarrePersonnalisee = getStrRepertAppli() + "/theme/telecommandes";
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
                        iNombreZones = getGestionnaireInterface().lisFichierShp(strNomFichierShp, zones);
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
                getGestionnaireInterface().chargeBarrePersonnalisee(strNomFichierBarre);

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
     * stPrimaryStage@param primaryStage
     *
     * @throws Exception
     */
    private void creeEnvironnement(Stage stPrimaryStage) throws Exception {
        creeEnvironnement(stPrimaryStage, 800, 600);
    }

    /**
     *
     * @param vbRacine
     * @param iTaille
     * @throws Exception
     */
    private static void creeMenu(VBox vbRacine, int iTaille) throws Exception {
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
        setMniAffichagePlan(new MenuItem(rbLocalisation.getString("main.tabPlan")));
        getMniAffichagePlan().setAccelerator(KeyCombination.keyCombination("Ctrl+3"));
        getMniAffichagePlan().setDisable(true);
        mnuAffichage.getItems().add(getMniAffichagePlan());
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
        setMniAjouterPlan(new MenuItem(rbLocalisation.getString("ajouterPlan")));
        getMniAjouterPlan().setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        mnuPanoramique.getItems().add(getMniAjouterPlan());
        getMniAjouterPlan().setDisable(true);
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
        ivNouveauProjet = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/nouveauProjet.png"));
        spBtnNouvprojet.setContent(ivNouveauProjet);
        Tooltip tltpNouveauProjet = new Tooltip(rbLocalisation.getString("nouveauProjet"));
        tltpNouveauProjet.setStyle(getStrTooltipStyle());
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
        ivChargeProjet = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/ouvrirProjet.png"));
        spBtnOuvrirProjet.setContent(ivChargeProjet);
        Tooltip tltpOuvrirProjet = new Tooltip(rbLocalisation.getString("ouvrirProjet"));
        tltpOuvrirProjet.setStyle(getStrTooltipStyle());
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
        ivSauveProjet = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/sauveProjet.png"));
        spBtnSauveProjet.setContent(ivSauveProjet);
        Tooltip tltpSauverProjet = new Tooltip(rbLocalisation.getString("sauverProjet"));
        tltpSauverProjet.setStyle(getStrTooltipStyle());
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
        ivAjouterPano = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/ajoutePanoramique.png"));
        spBtnAjoutePano.setContent(ivAjouterPano);
        Tooltip tltpAjouterPano = new Tooltip(rbLocalisation.getString("ajouterPanoramiques"));
        tltpAjouterPano.setStyle(getStrTooltipStyle());
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
        setIvAjouterPlan(new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/ajoutePlan.png")));
        spBtnAjoutePlan.setContent(getIvAjouterPlan());
        Tooltip tltpAjouterPlan = new Tooltip(rbLocalisation.getString("ajouterPlan"));
        tltpAjouterPlan.setStyle(getStrTooltipStyle());
        spBtnAjoutePlan.setTooltip(tltpAjouterPlan);
        hbBarreBouton.getChildren().add(spBtnAjoutePlan);
        getIvAjouterPlan().setDisable(true);
        getIvAjouterPlan().setOpacity(0.3);

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
        ivVisiteGenere = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/genereVisite.png"));
        spBtnGenereVisite.setContent(ivVisiteGenere);
        Tooltip tltpGenererVisite = new Tooltip(rbLocalisation.getString("genererVisite"));
        tltpGenererVisite.setStyle(getStrTooltipStyle());
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
        ivEqui2Cube = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/equi2cube.png"));
        spBtnEqui2Cube.setContent(ivEqui2Cube);
        Tooltip tltpEqui2Cube = new Tooltip(rbLocalisation.getString("outilsEqui2Cube"));
        tltpEqui2Cube.setStyle(getStrTooltipStyle());
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
        ivCube2Equi = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/cube2equi.png"));
        spBtnCube2Equi.setContent(ivCube2Equi);
        Tooltip tltpCube2Equi = new Tooltip(rbLocalisation.getString("outilsCube2Equi"));
        tltpCube2Equi.setStyle(getStrTooltipStyle());
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
        getMniAjouterPlan().setOnAction((e) -> {
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
        getMniAffichagePlan().setOnAction((e) -> {
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

    /**
     *
     */
    private static void retirePanoCourant() {
        Action actReponse = Dialogs.create()
                .owner(null)
                .title(rbLocalisation.getString("main.supprimerPano"))
                .message(rbLocalisation.getString("main.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (actReponse == Dialog.Actions.YES) {

            int iPanCourant = cbListeChoixPanoramique.getSelectionModel().getSelectedIndex();

            for (int i = iPanCourant; i < getiNombrePanoramiques() - 1; i++) {
                getPanoramiquesProjet()[i] = getPanoramiquesProjet()[i + 1];
            }
            cbListeChoixPanoramique.setValue(cbListeChoixPanoramique.getItems().get(0));
            affichePanoChoisit(0);
            ordPano.supprimerElement(iPanCourant);
            apListePanoTriable = ordPano.getApListePanoramiques();
            apListePanoTriable.setMaxHeight(apListePanoTriable.getPrefHeight());
            apListePanoTriable.setMinHeight(apListePanoTriable.getPrefHeight());
            apParametresVisite.setPrefHeight(120 + apListePanoTriable.getPrefHeight() + 20);
            if (apParametresVisite.isVisible()) {
                apParametresVisite.setMinHeight(120 + apListePanoTriable.getPrefHeight() + 20);
                apParametresVisite.setMaxHeight(120 + apListePanoTriable.getPrefHeight() + 20);
            }
            rafraichitListePano();
        }
    }

    /**
     *
     * @param stPrimaryStage
     * @param iLargeur
     * @param iHauteur
     * @throws Exception
     */
    private static void creeEnvironnement(Stage stPrimaryStage, int iLargeur, int iHauteur) throws Exception {
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
        tpEnvironnement.setMinHeight(iHauteur - 60);
        tpEnvironnement.setMaxHeight(iHauteur - 60);
        tpEnvironnement.setMinWidth(iLargeur);
        tpEnvironnement.setMaxWidth(iLargeur);
        Pane paneBarreStatus = new Pane();
        paneBarreStatus.setPrefSize(iLargeur + 20, 30);
        paneBarreStatus.setTranslateY(25);
        paneBarreStatus.setStyle("-fx-background-color:#c00;-fx-border-color:#aaa");
        tabVisite = new Tab();
        Pane paneVisualiseur;
        Pane panePlan;
        setTabInterface(new Tab());
        setTabPlan(new Tab());
        getGestionnaireInterface().creeInterface(iLargeur, iHauteur - 60);
        paneVisualiseur = getGestionnaireInterface().paneTabInterface;
        getGestionnairePlan().creeInterface(iLargeur, iHauteur - 60);
        panePlan = getGestionnairePlan().getPaneInterface();
        getTabInterface().setContent(paneVisualiseur);
        getTabPlan().setContent(panePlan);

        HBox hbEnvironnement = new HBox();
        TextField tfTitrePano;
        TextField tfTitreVisite;

        tpEnvironnement.getTabs().addAll(tabVisite, getTabInterface(), getTabPlan());
        tpEnvironnement.setSide(Side.TOP);
        tpEnvironnement.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            if (getGestionnaireInterface().navigateurCarteOL == null && isbInternet()) {
                getGestionnaireInterface().navigateurCarteOL = new NavigateurOpenLayersSeul();
                getGestionnaireInterface().apNavigateurCarte = getGestionnaireInterface().navigateurCarteOL.afficheNavigateurOpenLayer();
            }
            getGestionnaireInterface().rafraichit();
        });
        tabInterface.disableProperty().addListener((ov, av, nv) -> {
            if (!nv && getGestionnaireInterface().navigateurCarteOL == null && isbInternet()) {
                getGestionnaireInterface().navigateurCarteOL = new NavigateurOpenLayersSeul();
                getGestionnaireInterface().navigateurCarteOL.setBingApiKey(getStrBingAPIKey());
                getGestionnaireInterface().apNavigateurCarte = getGestionnaireInterface().navigateurCarteOL.afficheNavigateurOpenLayer();
            }
        });

        tabVisite.setText(rbLocalisation.getString("main.creationVisite"));
        tabVisite.setClosable(false);
        getTabInterface().setText(rbLocalisation.getString("main.creationInterface"));
        getTabInterface().setClosable(false);
        getTabPlan().setText(rbLocalisation.getString("main.tabPlan"));
        getTabPlan().setClosable(false);
        getTabPlan().setDisable(true);
        if (isbInternet()) {
            getTabInterface().setDisable(true);
        }
        tabVisite.setContent(hbEnvironnement);
        double largeur;
        String strLabelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-padding : 5px;  -fx-border : 1px solid #777;-fx-width : 100px;-fx-margin : 5px; ";

        scnPrincipale = new Scene(vbRacine, iLargeur, iHauteur, Color.rgb(221, 221, 221));
        vbRacine.setStyle("-fx-font-size : 9pt;-fx-font-family: Arial;");
        spPanneauOutils = new ScrollPane();
        spPanneauOutils.setId("panOutils");
        vbOutils = new VBox(-5);
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbChoixPanoramique = new VBox();
        vbChoixPanoramique.setId("choixPanoramique");
        double largeurOutil = vbOutils.getPrefWidth();

        AnchorPane apPanovisu = new AnchorPane();
        apPanovisu.setPrefHeight(118);
        apPanovisu.setMinHeight(118);
        apPanovisu.setMaxHeight(118);
        apPanovisu.setStyle("-fx-background-color : derive(-fx-base,-5%);");

        ImageView ivPanoVisu = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/panovisu.png", 83, 83, true, true));
        ivPanoVisu.setLayoutX(25);
        ivPanoVisu.setLayoutY(10);
        Label lblPanoVisu = new Label("panoVisu Vers. : " + strNumVersion);
        lblPanoVisu.setStyle("-fx-font-weight : bold;-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 1.2em;");
        lblPanoVisu.setLayoutX(118);
        lblPanoVisu.setLayoutY(25);
        Label lblPanoVisu2 = new Label("(c) Laurent LANG (2014)");
        lblPanoVisu2.setLayoutX(118);
        lblPanoVisu2.setLayoutY(55);
        lblPanoVisu2.setStyle("-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 0.8em;");
        Separator sepTitre = new Separator(Orientation.HORIZONTAL);
        sepTitre.setPrefWidth(380);
        sepTitre.setLayoutY(103);
        apPanovisu.getChildren().addAll(ivPanoVisu, lblPanoVisu, lblPanoVisu2, sepTitre);

        /*
         Paramètres de la visite
         */
        apParametresVisite = new AnchorPane();
        apParametresVisite.setLayoutY(40);
        Label lblTitreVisite = new Label(rbLocalisation.getString("main.titreVisite"));
        lblTitreVisite.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblTitreVisite.setLayoutX(10);
        lblTitreVisite.setLayoutY(5);

        tfTitreVisite = new TextField();
        tfTitreVisite.setId("titreVisite");
        tfTitreVisite.setPrefSize(200, 25);
        tfTitreVisite.setMaxSize(250, 25);
        tfTitreVisite.setLayoutX(60);
        tfTitreVisite.setLayoutY(25);
        cbIntroPetitePlanete = new CheckBox(rbLocalisation.getString("main.introPetitePlanete"));
        cbIntroPetitePlanete.setSelected(false);
        cbIntroPetitePlanete.setLayoutX(10);
        cbIntroPetitePlanete.setLayoutY(60);

        Label lblChoixPanoramiqueEntree = new Label(rbLocalisation.getString("main.panoEntree"));
        lblChoixPanoramiqueEntree.setStyle("-fx-font-size : 1em;");
        lblChoixPanoramiqueEntree.setLayoutX(10);
        lblChoixPanoramiqueEntree.setLayoutY(90);
        lblChoixPanoramiqueEntree.setMaxWidth(largeurOutils - 40);
        lblChoixPanoramiqueEntree.setPrefHeight(35);
        lblChoixPanoramiqueEntree.setWrapText(true);

        apParametresVisite.setPrefHeight(120);
        apParametresVisite.getChildren().addAll(
                lblTitreVisite, tfTitreVisite,
                cbIntroPetitePlanete,
                lblChoixPanoramiqueEntree
        );
        PaneOutil poParametresVisite = new PaneOutil(rbLocalisation.getString("main.parametresVisite"), apParametresVisite, largeurOutil);
        AnchorPane apPVIS = new AnchorPane(poParametresVisite.getApPaneOutil());
        poParametresVisite.setbValide(isbIntroPetitePlanete());
        AnchorPane apAutoRotation = new AnchorPane();
        apAutoRotation.setPrefHeight(240);
        apAutoRotation.setLayoutY(40);
        PaneOutil poAutoRotation = new PaneOutil(rbLocalisation.getString("main.autoTourRotation"), apAutoRotation, largeurOutil);
        AnchorPane apAR = new AnchorPane(poAutoRotation.getApPaneOutil());
        poAutoRotation.setbValide(isbAutoRotationDemarre() || isbAutoTourDemarre());

        cbAutoRotationDemarrage = new CheckBox(rbLocalisation.getString("main.autoRotationDemarrage"));
        cbAutoRotationDemarrage.setSelected(false);
        cbAutoRotationDemarrage.setLayoutX(10);
        cbAutoRotationDemarrage.setLayoutY(10);

        Label lblVitesse = new Label(rbLocalisation.getString("main.autoRotationVitesse"));
        lblVitesse.setLayoutX(10);
        lblVitesse.setLayoutY(40);
        cbAutoRotationVitesse = new ComboBox();
        cbAutoRotationVitesse.getItems().add(0, "10 " + rbLocalisation.getString("main.parTour"));
        cbAutoRotationVitesse.getItems().add(1, "20 " + rbLocalisation.getString("main.parTour"));
        cbAutoRotationVitesse.getItems().add(2, "30 " + rbLocalisation.getString("main.parTour"));
        cbAutoRotationVitesse.getItems().add(3, "Autre n " + rbLocalisation.getString("main.parTour"));
        cbAutoRotationVitesse.getSelectionModel().select(2);
        cbAutoRotationVitesse.setLayoutX(30);
        cbAutoRotationVitesse.setLayoutY(70);
        cbAutoRotationVitesse.setMaxWidth(170);
        bdfAutoRotationVitesse = new BigDecimalField(new BigDecimal(40));
        bdfAutoRotationVitesse.setDisable(true);
        bdfAutoRotationVitesse.setLayoutX(210);
        bdfAutoRotationVitesse.setLayoutY(70);
        bdfAutoRotationVitesse.setMaxWidth(70);
        lblVitesse.disableProperty().bind(cbAutoRotationDemarrage.selectedProperty().not());
        cbAutoRotationVitesse.disableProperty().bind(cbAutoRotationDemarrage.selectedProperty().not());
        //bdfAutoRotationVitesse.disableProperty().bind(cbAutoRotationDemarrage.selectedProperty().not());

        Label lblUnites = new Label(rbLocalisation.getString("main.parTour"));
        lblUnites.setLayoutX(290);
        lblUnites.setLayoutY(75);
        Separator spAutotour = new Separator(Orientation.HORIZONTAL);
        spAutotour.setLayoutX(0);
        spAutotour.setLayoutY(100);
        spAutotour.setMinWidth(380);

        cbAutoTourDemarrage = new CheckBox(rbLocalisation.getString("main.autoTour"));
        cbAutoTourDemarrage.setSelected(false);
        cbAutoTourDemarrage.setLayoutX(10);
        cbAutoTourDemarrage.setLayoutY(120);

        Label lblVitesseAutoTour = new Label(rbLocalisation.getString("main.autoTourChange"));
        lblVitesseAutoTour.setLayoutX(10);
        lblVitesseAutoTour.setLayoutY(180);

        cbAutoTourType = new ComboBox();
        cbAutoTourType.getItems().add(rbLocalisation.getString("main.nTours"));
        cbAutoTourType.getItems().add(rbLocalisation.getString("main.nSecondes"));
        cbAutoTourType.getSelectionModel().select(1);
        cbAutoTourType.setLayoutX(30);
        cbAutoTourType.setLayoutY(210);
        cbAutoTourType.setMaxWidth(140);
        bdfAutoTourLimite = new BigDecimalField(new BigDecimal(1));
        bdfAutoTourLimite.setLayoutX(240);
        bdfAutoTourLimite.setLayoutY(210);
        bdfAutoTourLimite.setMaxWidth(70);
        Label lblN = new Label("n=");
        lblN.setLayoutX(210);
        lblN.setLayoutY(215);
        cbAutoTourType.disableProperty().bind(cbAutoTourDemarrage.selectedProperty().not());
        bdfAutoTourLimite.disableProperty().bind(cbAutoTourDemarrage.selectedProperty().not());

        cbAutoRotationVitesse.getSelectionModel().selectedIndexProperty().addListener((ov, av, nv) -> {
            if (cbAutoRotationVitesse.getSelectionModel().getSelectedIndex() == 3) {
                bdfAutoRotationVitesse.setDisable(false);
                setiAutoRotationVitesse(bdfAutoRotationVitesse.getNumber().toBigInteger().intValue());
            } else {
                bdfAutoRotationVitesse.setDisable(true);
                setiAutoRotationVitesse(10 * (cbAutoRotationVitesse.getSelectionModel().getSelectedIndex() + 1));
            }
        });
        cbIntroPetitePlanete.selectedProperty().addListener((ov, av, nv) -> {
            setbIntroPetitePlanete(nv);
            poParametresVisite.setbValide(isbIntroPetitePlanete());
        });
        cbAutoRotationDemarrage.selectedProperty().addListener((ov, av, nv) -> {
            setbAutoRotationDemarre(nv);
            poAutoRotation.setbValide(isbAutoRotationDemarre() || isbAutoTourDemarre());
        });
        bdfAutoRotationVitesse.numberProperty().addListener((ov, av, nv) -> {
            setiAutoRotationVitesse(nv.toBigInteger().intValue());
        });

        cbAutoTourType.getSelectionModel().selectedIndexProperty().addListener((ov, av, nv) -> {
            if (cbAutoTourType.getSelectionModel().getSelectedIndex() == 0) {
                setStrAutoTourType("tours");
            } else {
                setStrAutoTourType("secondes");
            }
        });
        cbAutoTourDemarrage.selectedProperty().addListener((ov, av, nv) -> {
            setbAutoTourDemarre(nv);
            poAutoRotation.setbValide(isbAutoRotationDemarre() || isbAutoTourDemarre());
            getGestionnaireInterface().getApBtnVA().setDisable(!nv);
        });
        bdfAutoTourLimite.numberProperty().addListener((ov, av, nv) -> {
            setiAutoTourLimite(nv.toBigInteger().intValue());
        });

        apAutoRotation.getChildren().addAll(
                cbAutoRotationDemarrage,
                lblVitesse,
                cbAutoRotationVitesse, bdfAutoRotationVitesse, lblUnites,
                spAutotour,
                cbAutoTourDemarrage,
                lblVitesseAutoTour,
                cbAutoTourType, lblN, bdfAutoTourLimite
        );

        AnchorPane apParametresPano = new AnchorPane();
        apParametresPano.setPrefHeight(100);
        apParametresPano.setLayoutY(40);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/suppr.png", 24, 24, true, true));
        Button btnSupprimePano = new Button(rbLocalisation.getString("main.supprimePanoCourant"), ivSupprPanoramique);
        btnSupprimePano.setLayoutX(60);
        btnSupprimePano.setLayoutY(70);
        btnSupprimePano.setOnAction((e) -> {
            retirePanoCourant();
        });
        Label lblTitrePano = new Label(rbLocalisation.getString("main.titrePano"));
        lblTitrePano.setStyle("-fx-font-size : 1em;");
        lblTitrePano.setPadding(new Insets(5, 5, 5, 0));
        lblTitrePano.setLayoutX(10);
        lblTitrePano.setLayoutY(10);
        tfTitrePano = new TextField();
        tfTitrePano.setId("txttitrepano");
        tfTitrePano.setPrefSize(200, 25);
        tfTitrePano.setMaxSize(250, 25);
        tfTitrePano.setLayoutX(60);
        tfTitrePano.setLayoutY(40);

        tfTitrePano.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            clickBtnValidePano();
        });
        apParametresPano.getChildren().addAll(
                btnSupprimePano,
                lblTitrePano, tfTitrePano
        );
        AnchorPane apGEO = new AnchorPane();
        apOpenLayers = new AnchorPane();
        apOpenLayers.setVisible(false);

        AnchorPane apPPAN = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("main.parametresPano"), apParametresPano, largeurOutil).getApPaneOutil());
        if (isbInternet()) {
            navigateurOpenLayers = new NavigateurOpenLayers();
            navigateurOpenLayers.setBingApiKey(getStrBingAPIKey());
            tfLongitude = new TextField();
            tfLatitude = new TextField();
            apOpenLayers = navigateurOpenLayers.afficheNavigateurOpenLayer(tfLongitude, tfLatitude, true);
            apOpenLayers.setPrefSize(800, 600);
            Button btnGeolocalise = new Button(rbLocalisation.getString("main.geolocalisation"));

            btnGeolocalise.setLayoutX(10);
            btnGeolocalise.setLayoutY(25);
            btnGeolocalise.setPrefWidth(120);
            btnGeolocalise.setOnAction((e) -> {
                navigateurOpenLayers.retireMarqueur(0);
                if (navigateurOpenLayers.getBingApiKey().equals("")) {
                    navigateurOpenLayers.afficheCartesOpenlayer();
                } else {
                    navigateurOpenLayers.valideBingApiKey(navigateurOpenLayers.getBingApiKey());
                }
                if (panoramiquesProjet[getiPanoActuel()].getMarqueurGeolocatisation() != null) {
                    navigateurOpenLayers.allerCoordonnees(panoramiquesProjet[getiPanoActuel()].getMarqueurGeolocatisation(), 17);
                    navigateurOpenLayers.setMarqueur(panoramiquesProjet[getiPanoActuel()].getMarqueurGeolocatisation());
                    String strFichierPano = getPanoramiquesProjet()[getiPanoActuel()]
                            .getStrNomFichier().substring(getPanoramiquesProjet()[getiPanoActuel()].getStrNomFichier()
                                    .lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[getiPanoActuel()]
                                    .getStrNomFichier().length()).split("\\.")[0];
                    String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-weight:bold;font-size : 12px;'>"
                            + getPanoramiquesProjet()[getiPanoActuel()].getStrTitrePanoramique()
                            + "</span><br/>"
                            + "<span style='font-family : Verdana,Arial,sans-serif;bold;font-size : 10px;'>"
                            + strFichierPano
                            + "</span>";
                    strHTML = strHTML.replace("\\", "/");
                    navigateurOpenLayers.ajouteMarqueur(0, panoramiquesProjet[getiPanoActuel()].getMarqueurGeolocatisation(), strHTML);
                }
                apOpenLayers.setVisible(true);
            });
            tfLatitude.setLayoutX(140);
            tfLatitude.setLayoutY(10);
            tfLongitude.setLayoutX(140);
            tfLongitude.setLayoutY(40);
            apOpenLayers.setLayoutX(200);
            apOpenLayers.setLayoutY(150);
            apOpenLayers.setVisible(false);
            AnchorPane apGeolocalise = new AnchorPane();
            apGeolocalise.setPrefHeight(75);
            apGeolocalise.getChildren().addAll(btnGeolocalise, tfLatitude, tfLongitude);
            apGeolocalise.setLayoutX(10);
            apGeolocalise.setLayoutY(40);
            setPoGeolocalisation(new PaneOutil(rbLocalisation.getString("main.geolocalisation"), apGeolocalise, largeurOutil));
            apGEO = new AnchorPane(getPoGeolocalisation().getApPaneOutil());

            apOpenLayers.setLayoutX((iLargeur - apOpenLayers.getPrefWidth()) / 2);
            apOpenLayers.setLayoutY((iHauteur - apOpenLayers.getPrefHeight()) / 2);
            apOpenLayers.visibleProperty().addListener((ov, av, nv) -> {
                mbarPrincipal.setDisable(nv);
                hbBarreBouton.setDisable(nv);
                tpEnvironnement.setDisable(nv);

            });
        }
        apVisuPanoramique.setLayoutY(40);
        apVisuPanoramique.setPrefWidth(340);
        apVisuPanoramique.setPrefHeight(275);

        AnchorPane apVISU = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("main.visualisation"), apVisuPanoramique, largeurOutil).getApPaneOutil());

        vbVisuHotspots = new VBox();
        apVisuHS = new AnchorPane(vbVisuHotspots);
        apVisuHS.setLayoutY(40);
        apHS1=new PaneOutil(true, "Hotspots", apVisuHS, largeurOutil);
        
        AnchorPane apHS = new AnchorPane(apHS1.getApPaneOutil());

        vbChoixPanoramique.getChildren().addAll(
                apPVIS,
                apAR,
                apPPAN,
                apGEO,
                apVISU,
                apHS
        );
        vbChoixPanoramique.setSpacing(-5);
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
        /**
         *
         */
        spVuePanoramique.setPrefSize(iLargeur - largeurOutils - 20, iHauteur - 110);
        spVuePanoramique.setMaxSize(iLargeur - largeurOutils - 20, iHauteur - 110);
        spVuePanoramique.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spVuePanoramique.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spVuePanoramique.setTranslateY(5);
        /**
         *
         */
        AnchorPane apPanneauOutils = new AnchorPane();
        apPanneauOutils.getChildren().addAll(apPanovisu, spPanneauOutils);
        apPanneauOutils.setTranslateY(3);
        apPanneauOutils.setTranslateX(20);
        spPanneauOutils.setContent(vbOutils);
        spPanneauOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spPanneauOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spPanneauOutils.setPrefSize(largeurOutils, iHauteur - 230);
        spPanneauOutils.setMaxWidth(largeurOutils);
        spPanneauOutils.setMaxHeight(iHauteur - 230);
        spPanneauOutils.setLayoutY(118);
        spPanneauOutils.setLayoutX(0);
        /**
         *
         */
        panePanoramique.setCursor(Cursor.CROSSHAIR);
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.minHeight(iHauteur - 110);
        vbOutils.setLayoutX(3);
        lblLong.setPrefSize(100, 15);
        lblLat.setPrefSize(100, 15);
        lblLat.setTranslateX(50);
        apPanneauPrincipal.setPrefSize(iLargeur - largeurOutils - 20, iHauteur - 110);
        apListeImagesPanoramiques = new AnchorPane();
        apListeImagesPanoramiques.setPrefWidth(iLargeurVignettes + 40);
        apListeImagesPanoramiques.setMinWidth(iLargeurVignettes + 40);
        apListeImagesPanoramiques.setMaxWidth(iLargeurVignettes + 40);
        apListeImagesPanoramiques.setPrefHeight(iHauteur - 140);
        apListeImagesPanoramiques.setLayoutX(-iLargeurVignettes - 30);
        apListeImagesPanoramiques.setLayoutY(0);
        apListeImagesPanoramiques.setStyle("-fx-background-color :rgba(0,0,0,0);");
        apListeImagesPanoramiques.setOnMouseEntered((e) -> {
            apListeImagesPanoramiques.setLayoutX(0);
        });
        apListeImagesPanoramiques.setOnMouseExited((e) -> {
            apListeImagesPanoramiques.setLayoutX(-iLargeurVignettes - 30);
        });
        Label lblVignettes = new Label(rbLocalisation.getString("main.vignettes"));
        lblVignettes.setPrefSize(70, 20);
        lblVignettes.setTextAlignment(TextAlignment.CENTER);
        lblVignettes.setStyle("-fx-background-color:-fx-base;"
                + "-fx-border-color: derive(-fx-base,10%);"
                + "-fx-border-width: 1px;");
        lblVignettes.setTranslateX(-lblVignettes.getPrefWidth() / 2 + lblVignettes.getPrefHeight() / 2);
        lblVignettes.setTranslateY(lblVignettes.getPrefWidth() / 2 - lblVignettes.getPrefHeight() / 2);
        lblVignettes.setRotate(270);
        lblVignettes.setLayoutX(iLargeurVignettes + 30);
        apVignettesPano = new AnchorPane();
        apVignettesPano.setPrefWidth(iLargeurVignettes + 10);
        apVignettesPano.setMinHeight(iHauteur - 140);
        apVignettesPano.setStyle("-fx-background-color:-fx-base;");
        rectVignettePano = new Rectangle(0, 0, iLargeurVignettes, iLargeurVignettes / 2.d);
        rectVignettePano.setLayoutX(5);
        rectVignettePano.setLayoutY(10);
        rectVignettePano.setFill(Color.web("#fff", 0.5));
        rectVignettePano.setStroke(Color.WHITE);
        rectVignettePano.setStrokeWidth(2.0);
        rectVignettePano.setVisible(false);
        apVignettesPano.getChildren().add(rectVignettePano);
        ScrollPane spListeImagesPanoramiques = new ScrollPane(apVignettesPano);
        spListeImagesPanoramiques.setPrefWidth(iLargeurVignettes + 30);
        spListeImagesPanoramiques.setPrefHeight(iHauteur - 130);
        spListeImagesPanoramiques.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spListeImagesPanoramiques.setStyle("-fx-background-color:-fx-base;"
                + "-fx-border-color: derive(-fx-base,10%);"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.2) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");
        apListeImagesPanoramiques.getChildren().addAll(spListeImagesPanoramiques, lblVignettes);

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
        hbEnvironnement.getChildren().setAll(spVuePanoramique, apPanneauOutils);
        apEnvironnement = new AnchorPane();
        setApAttends(new AnchorPane());
        getApAttends().setPrefHeight(250);
        getApAttends().setPrefWidth(600);
        getApAttends().setMaxWidth(600);
        getApAttends().setStyle("-fx-background-color : -fx-base;"
                + "-fx-border-color: derive(-fx-base,10%);"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");
        getApAttends().setLayoutX((iLargeur - getApAttends().getPrefWidth()) / 2.d);
        getApAttends().setLayoutY((iHauteur - getApAttends().getPrefHeight()) / 2.d - 55);
        pbarAvanceChargement = new ProgressBar();
        pbarAvanceChargement.setPrefSize(400, 30);
        pbarAvanceChargement.setLayoutX((getApAttends().getPrefWidth() - pbarAvanceChargement.getPrefWidth()) / 2);
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
        getApAttends().getChildren().addAll(lblAttends, pbarAvanceChargement, lblCharge, lblNiveaux);
        getApAttends().setVisible(false);
        apEnvironnement.getChildren().addAll(tpEnvironnement, getApAttends());
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
        apPanneauPrincipal.getChildren().addAll(lblDragDrop, spAfficheLegende(), apListeImagesPanoramiques);
        apCreationBarre = new AnchorPane();
        apCreationBarre.setVisible(false);
        apEnvironnement.getChildren().addAll(apCreationBarre, apOpenLayers);
    }

    /**
     *
     * @param stPrimaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage stPrimaryStage) throws Exception {
        File fileRep = new File("");
        setbInternet(netIsAvailable());
        setStrCurrentDir(fileRep.getAbsolutePath());
        setStrRepertAppli(fileRep.getAbsolutePath());
        fileRepertConfig = new File(getStrRepertAppli() + File.separator + "configPV");
        if (!fileRepertConfig.exists()) {
            fileRepertConfig.mkdirs();
            setLocale(new Locale("fr", "FR"));
            setUserAgentStylesheet("file:css/clair.css");
            setStrRepertoireProjet(getStrRepertAppli());
        } else {
            lisFichierConfig();
        }
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", getLocale());
        setStPrincipal(stPrimaryStage);
        getStPrincipal().setResizable(false);
        getStPrincipal().setTitle("PanoVisu v" + strNumVersion);
        stPrimaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int iHauteur = (int) tailleEcran.getHeight() - 20;
        int iLargeur = (int) tailleEcran.getWidth() - 2;
        largeurMax = tailleEcran.getWidth() - 450.0d;
        creeEnvironnement(stPrimaryStage, iLargeur, iHauteur);

        File fileRepertTempFile = new File(getStrRepertAppli() + File.separator + "temp");
        setStrRepertTemp(fileRepertTempFile.getAbsolutePath());

        if (!fileRepertTempFile.exists()) {
            fileRepertTempFile.mkdirs();
        } else {
            deleteDirectory(getStrRepertTemp());
        }
        String strExtTemp = strGenereChaineAleatoire(20);
        setStrRepertTemp(getStrRepertTemp() + File.separator + "temp" + strExtTemp);
        fileRepertTempFile = new File(getStrRepertTemp());
        fileRepertTempFile.mkdirs();
        installeEvenements();
        projetsNouveau();
        stPrimaryStage.setOnCloseRequest((WindowEvent event) -> {
            Action actReponse = null;
            Localization.setLocale(getLocale());
            if (!isbDejaSauve()) {
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

                deleteDirectory(getStrRepertTemp());
                File fileTemp = new File(getStrRepertTemp());
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
        setStrSystemeExploitation(System.getProperty("os.name"));
        launch(args);
    }
}
