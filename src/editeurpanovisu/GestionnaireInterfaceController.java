/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.apEnvironnement;
import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.getGestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.getIvAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.getMniAffichagePlan;
import static editeurpanovisu.EditeurPanovisu.getMniAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getPlans;
import static editeurpanovisu.EditeurPanovisu.getStPrincipal;
import static editeurpanovisu.EditeurPanovisu.getStrCurrentDir;
import static editeurpanovisu.EditeurPanovisu.getStrRepertAppli;
import static editeurpanovisu.EditeurPanovisu.getStrRepertTemp;
import static editeurpanovisu.EditeurPanovisu.getStrTooltipStyle;
import static editeurpanovisu.EditeurPanovisu.getTabPlan;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiquesFichier;
import static editeurpanovisu.EditeurPanovisu.getiNombrePlans;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import static editeurpanovisu.EditeurPanovisu.hbBarreBouton;
import static editeurpanovisu.EditeurPanovisu.isbAutoTourDemarre;
import static editeurpanovisu.EditeurPanovisu.mbarPrincipal;
import static editeurpanovisu.EditeurPanovisu.setbDejaSauve;
import static editeurpanovisu.EditeurPanovisu.setiNombrePanoramiquesFichier;
import static editeurpanovisu.EditeurPanovisu.isbInternet;
import static editeurpanovisu.EditeurPanovisu.getiDecalageMac;
import static editeurpanovisu.EditeurPanovisu.tpEnvironnement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 * Gestion de l'interface de visualition de la visite virtuelle
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    public Date dtBuild;

    public GestionnaireInterfaceController() {
    }

    private final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp", ".gif"});
    private final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    private ImageFond[] imagesFond = new ImageFond[50];
    private int iNombreImagesFond = 0;
    private boolean bTemplate;
    private PaneOutil poImageFond;
    public static String strTypeHS = "";
    public static String strNomfichierHS = "";
    public static String strTypeHSImage = "";
    public static String strNomfichierHSImage = "";
    public static String strTypeHSHTML = "";
    public static String strNomfichierHSHTML = "";

    private String strStyleHotSpots = "hotspot.png";
    private String strStyleHotSpotImages = "photo2.png";
    private String strStyleHotSpotHTML = "html1.png";
    private ResourceBundle rbLocalisation;
    /*
     Variables barre de navigation classique 
     */
    private double offsetXBarreClassique = 0;
    private double offsetYBarreClassique = 10;
    private double tailleBarreClassique = 26;
    private double espacementBarreClassique = 4;
    private final String strStyleDefautBarreClassique = "retina";
    private String strPositionBarreClassique = "bottom:center";
    private String styleBarreClassique = strStyleDefautBarreClassique;
    private String strDeplacementsBarreClassique = "oui";
    private String strZoomBarreClassique = "oui";
    private String strOutilsBarreClassique = "oui";
    private String strRotationBarreClassique = "oui";
    private String strPleinEcranBarreClassique = "oui";
    private String strSourisBarreClassique = "oui";
    private String strVisibiliteBarreClassique = "oui";
    private ComboBox cblisteStyleBarreClassique;
    private RadioButton rbTopLeftBarreClassique;
    private RadioButton rbTopCenterBarreClassique;
    private RadioButton rbTopRightBarreClassique;
    private RadioButton rbMiddleLeftBarreClassique;
    private RadioButton rbMiddleRightBarreClassique;
    private RadioButton rbBottomLeftBarreClassique;
    private RadioButton rbBottomCenterBarreClassique;
    private RadioButton rbBottomRightBarreClassique;
    private CheckBox cbBarreClassiqueVisible;
    private CheckBox cbDeplacementsBarreClassique;
    private CheckBox cbZoomBarreClassique;
    private CheckBox cbOutilsBarreClassique;
    private CheckBox cbFSBarreClassique;
    private CheckBox cbSourisBarreClassique;
    private CheckBox cbRotationBarreClassique;
    private Slider slEspacementBarreClassique;
    private BigDecimalField bdfOffsetXBarreClassique;
    private BigDecimalField bdfOffsetYBarreClassique;
    private ColorPicker cpCouleurBarreClassique;
    private Color couleurBarreClassique = Color.hsb(180, 0.39, 0.5);

    /*
     Variables barre de navigation Personnalisée 
     */
    private boolean bCouleurOrigineBarrePersonnalisee = true;
    private int iNombreZonesBarrePersonnalisee = 0;
    private double offsetXBarrePersonnalisee = 0;
    private double offsetYBarrePersonnalisee = 0;
    private double tailleBarrePersonnalisee = 100;
    private double tailleIconesBarrePersonnalisee = 40;
    private String strPositionBarrePersonnalisee = "bottom:right";
    private String strDeplacementsBarrePersonnalisee = "oui";
    private String strZoomBarrePersonnalisee = "oui";
    private String strInfoBarrePersonnalisee = "oui";
    private String strAideBarrePersonnalisee = "oui";
    private String strRotationBarrePersonnalisee = "oui";
    private String strPleinEcranBarrePersonnalisee = "oui";
    private String strSourisBarrePersonnalisee = "oui";
    private String strVisibiliteBarrePersonnalisee = "non";
    private String strLienImageBarrePersonnalisee = "";
    private String strLien1BarrePersonnalisee = "";
    private String strLien2BarrePersonnalisee = "";
    private ZoneTelecommande[] zonesBarrePersonnalisee = new ZoneTelecommande[50];
    private RadioButton rbTopLeftBarrePersonnalisee;
    private RadioButton rbTopCenterBarrePersonnalisee;
    private RadioButton rbTopRightBarrePersonnalisee;
    private RadioButton rbMiddleLeftBarrePersonnalisee;
    private RadioButton rbMiddleRightBarrePersonnalisee;
    private RadioButton rbBottomLeftBarrePersonnalisee;
    private RadioButton rbBottomCenterBarrePersonnalisee;
    private RadioButton rbBottomRightBarrePersonnalisee;
    private RadioButton rbCouleurOrigineBarrePersonnalisee;
    private RadioButton rbCouleurPersBarrePersonnalisee;
    private ImageView ivBarrePersonnalisee;
    private Image imgPngBarrePersonnalisee;
    private WritableImage wiBarrePersonnaliseeCouleur;
    private AnchorPane apAfficheBarrePersonnalisee;
    private CheckBox cbBarrePersonnaliseeVisible;
    private CheckBox cbDeplacementsBarrePersonnalisee;
    private CheckBox cbZoomBarrePersonnalisee;
    private CheckBox cbFSBarrePersonnalisee;
    private CheckBox cbSourisBarrePersonnalisee;
    private CheckBox cbRotationBarrePersonnalisee;
    private TextField tfLienImageBarrePersonnalisee;
    private TextField tfLien1BarrePersonnalisee;
    private TextField tfLien2BarrePersonnalisee;
    private Slider sltailleBarrePersonnalisee;
    private Slider sltailleIconesBarrePersonnalisee;
    private BigDecimalField bdfOffsetXBarrePersonnalisee;
    private BigDecimalField bdfOffsetYBarrePersonnalisee;
    private ColorPicker cpCouleurBarrePersonnalisee;
    private Color couleurBarrePersonnalisee = Color.hsb(180, 0.39, 0.5);
    final ToggleGroup grpPositionBarrePersonnalisee = new ToggleGroup();
    final ToggleGroup grpCouleurBarrePersonnalisee = new ToggleGroup();
    private Button btnEditerBarre;

    private boolean bAfficheTitre = true;
    private String strTitrePoliceNom = "Verdana";
    private String strTitrePoliceStyle = "Regular";
    private String strTitrePoliceTaille = "12.0";
    private String strCouleurTitre = "#ffffff";
    private String strCouleurFondTitre = "#4e8080";
    private String strTitrePosition = "center";
    private double titreDecalage = 10;
    private boolean bTitreVisite = false;
    private boolean bTitrePanoramique = true;
    private boolean bTitreAdapte = false;
    private double titreOpacite = 0.8;
    private double titreTaille = 100.0;

    private String strCouleurDiaporama = "#4e8080";
    private double diaporamaOpacite = 0.8;
    private ColorPicker cpCouleurDiaporama;
    private Slider slOpaciteDiaporama;
    private boolean bVisualiseDiaporama = false;
    private AnchorPane apAfficheDiapo;
    private ImageView ivDiapo;

    /**
     * Variables boussole
     */
    private boolean bAfficheBoussole = false;
    private String strImageBoussole = "rose3.png";
    private String strPositionBoussole = "top:right";
    private double offsetXBoussole = 20;
    private double offsetYBoussole = 20;
    private double tailleBoussole = 100;
    private double opaciteBoussole = 0.8;
    private boolean bAiguilleMobileBoussole = true;
    private String strRepertImagesFond = "";
    private String strRepertBarrePersonnalisee = "";
    private ImageView imgBoussole;
    private ImageView imgAiguille;
    private BigDecimalField bdfOffsetXBoussole;
    private BigDecimalField bdfOffsetYBoussole;
    private Slider slTailleBoussole;
    private Slider slOpaciteBoussole;
    private CheckBox cbAiguilleMobile;
    private CheckBox cbAfficheBoussole;
    private RadioButton rbBoussTopLeft;
    private RadioButton rbBoussTopRight;
    private RadioButton rbBoussBottomLeft;
    private RadioButton rbBoussBottomRight;

    /**
     * Variables Fenetre Info/Aide
     */
    private boolean bAfficheFenetreInfo = false;
    private boolean bAfficheFenetreAide = false;
    private boolean bFenetreInfoPersonnalise = false;
    private boolean bFenetreAidePersonnalise = false;
    private double fenetreInfoTaille = 100.d;
    private double fenetreAideTaille = 100.d;
    private double fenetreInfoPosX = 0.d;
    private double fenetreInfoPosY = 0.d;
    private double fenetreAidePosX = 0.d;
    private double fenetreAidePosY = 0.d;
    private double fenetreInfoposX = 0.d;
    private double fenetreInfoOpacite = 0.8;
    private double fenetreAideOpacite = 0.8;
    private double fenetrePoliceTaille = 10.d;
    private double fenetreURLPosX = 0.d;
    private double fenetreURLPosY = 0.d;
    private double fenetreOpaciteFond = 0.8;
    private String strFenetreInfoImage = "";
    private String strFenetreAideImage = "";
    private String strFenetreURL = "";
    private String strFenetreTexteURL = "";
    private String strFenetreURLInfobulle = "";
    private String strFenetreURLCouleur = "#FFFF00";
    private String strFenetrePolice = "Verdana";
    private String strFenetreCouleurFond = "#ffffff";

    private CheckBox cbFenetreInfoPersonnalise;
    private CheckBox cbFenetreAidePersonnalise;
    private TextField tfFenetreInfoImage;
    private TextField tfFenetreAideImage;
    private Slider slFenetreInfoTaille;
    private Slider slFenetreAideTaille;
    private BigDecimalField bdfFenetreInfoPosX;
    private BigDecimalField bdfFenetreInfoPosY;
    private BigDecimalField bdfFenetreAidePosX;
    private BigDecimalField bdfFenetreAidePosY;
    private Slider slFenetreInfoOpacite;
    private Slider slFenetreAideOpacite;
    private TextField tfFenetreTexteURL;
    private TextField tfFenetreURL;
    private TextField tfFenetreURLInfobulle;
    private ComboBox tfFenetrePolice;
    private Slider slFenetrePoliceTaille;
    private BigDecimalField bdfFenetreURLPosX;
    private BigDecimalField bdfFenetreURLPosY;
    private ColorPicker cpFenetreCouleurFond;
    private ColorPicker cpFenetreURLCouleur;

    private final AnchorPane apFenetreAfficheInfo = new AnchorPane();
    private final Label lblFenetreURL = new Label();
    /**
     *
     */
    private String strRepertMasques = "";
    private boolean bAfficheMasque = false;
    private String strImageMasque = "MA.png";
    private String strPositionMasque = "top:right";
    private double dXMasque = 20;
    private double dYMasque = 20;
    private double tailleMasque = 30;
    private double opaciteMasque = 0.8;
    private boolean bMasqueNavigation = true;
    private boolean bMasqueBoussole = true;
    private boolean bMasqueTitre = true;
    private boolean bMasquePlan = true;
    private boolean bMasqueReseaux = true;
    private boolean bMasqueVignettes = true;
    private boolean bMasqueCombo = true;
    private boolean bMasqueSuivPrec = true;
    private boolean bMasqueHotspots = true;
    private ImageView ivMasque;
    private BigDecimalField bdfOffsetXMasque;
    private BigDecimalField bdfOffsetYMasque;
    private Slider slTailleMasque;
    private Slider slOpaciteMasque;
    private CheckBox cbAfficheMasque;
    private CheckBox cbMasqueNavigation;
    private CheckBox cbMasqueBoussole;
    private CheckBox cbMasqueTitre;
    private CheckBox cbMasquePlan;
    private CheckBox cbMasqueReseaux;
    private CheckBox cbMasqueVignettes;
    private CheckBox cbMasqueCombo;
    private CheckBox cbMasqueSuivPrec;
    private CheckBox cbMasqueHotspots;
    private RadioButton rbMasqueTopLeft;
    private RadioButton rbMasqueTopRight;
    private RadioButton rbMasqueBottomLeft;
    private RadioButton rbMasqueBottomRight;

    /**
     *
     */
    private String strRepertReseauxSociaux = "";
    private boolean bAfficheReseauxSociaux = false;
    private String strImageReseauxSociauxTwitter = "twitter.png";
    private String strImageReseauxSociauxGoogle = "google.png";
    private String strImageReseauxSociauxFacebook = "facebook.png";
    private String strImageReseauxSociauxEmail = "email.png";
    private String strPositionReseauxSociaux = "top:right";
    private double dXReseauxSociaux = 20;
    private double dYReseauxSociaux = 20;
    private double tailleReseauxSociaux = 30;
    private double opaciteReseauxSociaux = 0.8;
    private boolean bReseauxSociauxTwitter = true;
    private boolean bReseauxSociauxGoogle = true;
    private boolean bReseauxSociauxFacebook = true;
    private boolean bReseauxSociauxEmail = true;
    private ImageView ivTwitter;
    private ImageView ivGoogle;
    private ImageView ivFacebook;
    private ImageView ivEmail;
    private BigDecimalField bdfOffsetXReseauxSociaux;
    private BigDecimalField bdfOffsetYreseauxSociaux;
    private Slider slTailleReseauxSociaux;
    private Slider slOpaciteReseauxSociaux;
    private CheckBox cbAfficheReseauxSociaux;
    private CheckBox cbReseauxSociauxTwitter;
    private CheckBox cbReseauxSociauxGoogle;
    private CheckBox cbReseauxSociauxFacebook;
    private CheckBox cbReseauxSociauxEmail;
    private RadioButton rbReseauxSociauxTopLeft;
    private RadioButton rbReseauxSociauxTopRight;
    private RadioButton rbReseauxSociauxBottomLeft;
    private RadioButton rbReseauxSociauxBottomRight;
    /*
     *   variables Vignettes
     */
    private AnchorPane apVignettes;
    private AnchorPane apVisuVignettes;
    private boolean bAfficheVignettes = false;
    private String strCouleurFondVignettes = "#4e8080";
    private String strCouleurTexteVignettes = "#ffffff";
    private String strPositionVignettes = "bottom";
    private double tailleImageVignettes = 120;
    private double opaciteVignettes = 0.8;
    private Slider slOpaciteVignettes;
    private Slider slTailleVignettes;
    private CheckBox cbAfficheVignettes;
    private RadioButton rbVignettesLeft;
    private RadioButton rbVignettesRight;
    private RadioButton rbVignettesBottom;
    private ColorPicker cpCouleurFondVignettes;
    private ColorPicker cpCouleurTexteVignettes;
    private boolean bReplieDemarrageVignettes = false;
    private CheckBox cbReplieDemarrageVignettes;

    /*
     *   variables ComboMenu
     */
    private AnchorPane apComboMenu;
    private AnchorPane apVisuComboMenu;
    private boolean bAfficheComboMenu = false;
    private boolean bAfficheComboMenuImages = true;
    private String strPositionXComboMenu = "left";
    private String strPositionYComboMenu = "top";
    private double offsetXComboMenu = 10;
    private double offsetYComboMenu = 10;
    private CheckBox cbAfficheComboMenu;
    private CheckBox cbAfficheComboMenuImages;
    private BigDecimalField bdfOffsetXComboMenu;
    private BigDecimalField bdfOffsetYComboMenu;
    private RadioButton rbComboMenuTopLeft;
    private RadioButton rbComboMenuTopCenter;
    private RadioButton rbComboMenuTopRight;
    private RadioButton rbComboMenuBottomLeft;
    private RadioButton rbComboMenuBottomCenter;
    private RadioButton rbComboMenuBottomRight;
    /*
     *   variables Bouton Visite Automatique
     */
    private AnchorPane apBoutonVisiteAuto;
    private AnchorPane apVisuBoutonVisiteAuto;
    private CheckBox cbAfficheBoutonVisiteAuto;
    private BigDecimalField bdfOffsetXBoutonVisiteAuto;
    private BigDecimalField bdfOffsetYBoutonVisiteAuto;
    private RadioButton rbBoutonVisiteAutoTopLeft;
    private RadioButton rbBoutonVisiteAutoTopCenter;
    private RadioButton rbBoutonVisiteAutoTopRight;
    private RadioButton rbBoutonVisiteAutoBottomLeft;
    private RadioButton rbBoutonVisiteAutoBottomCenter;
    private RadioButton rbBoutonVisiteAutoBottomRight;
    private Slider slTailleBoutonVisiteAuto;
    private ImageView ivBtnVisiteAuto;
    private ToggleGroup tgPosBoutonVisiteAuto = new ToggleGroup();
    private AnchorPane apBtnVA;
    private boolean bAfficheBoutonVisiteAuto = false;
    private String strPositionXBoutonVisiteAuto = "right";
    private String strPositionYBoutonVisiteAuto = "top";
    private double offsetXBoutonVisiteAuto = 10;
    private double offsetYBoutonVisiteAuto = 10;
    private double tailleBoutonVisiteAuto = 32;

    /*
     Variable du plan
     */
    private AnchorPane apPlan;
    private AnchorPane apVisuPlan;
    private boolean bAffichePlan = false;
    private String strPositionPlan = "left";
    private double largeurPlan = 200;
    private Color couleurFondPlan = Color.hsb(180, 0.39, 0.5);
    private String strCouleurFondPlan = couleurFondPlan.toString().substring(2, 8);
    private double opacitePlan = 0.8;
    private Color couleurTextePlan = Color.rgb(255, 255, 255);
    private String strCouleurTextePlan = couleurTextePlan.toString().substring(2, 8);
    private boolean bAfficheRadar = false;
    private Color couleurLigneRadar = Color.rgb(255, 255, 0);
    private String strCouleurLigneRadar = couleurLigneRadar.toString().substring(2, 8);
    private Color couleurFondRadar = Color.rgb(200, 0, 0);
    private String strCouleurFondRadar = couleurFondRadar.toString().substring(2, 8);
    private double tailleRadar = 40;
    private double opaciteRadar = 0.5;
    private boolean bReplieDemarragePlan = false;

    /*
     Eléments de l'onglet plan
     */
    private CheckBox cbAffichePlan;
    private Slider slOpacitePlan;
    private RadioButton rbPlanLeft;
    private RadioButton rbPlanRight;
    private ColorPicker cpCouleurFondPlan;
    private ColorPicker cpCouleurTextePlan;
    private Slider slLargeurPlan;
    private CheckBox cbAfficheRadar;
    private ColorPicker cpCouleurFondRadar;
    private ColorPicker cpCouleurLigneRadar;
    private CheckBox cbReplieDemarragePlan;
    private Slider slTailleRadar;
    private Slider slOpaciteRadar;
    /*
     Variable de la Carte
     */
    private AnchorPane apCarte;
    private AnchorPane apVisuCarte;
    private boolean bAfficheCarte = false;

    private String strPositionCarte = "left";
    private double largeurCarte = 400;
    private double hauteurCarte = 300;
    private Color couleurFondCarte = Color.hsb(180, 0.39, 0.5);
    private String strCouleurFondCarte = couleurFondCarte.toString().substring(2, 8);
    private double opaciteCarte = 0.8;
    private Color couleurTexteCarte = Color.rgb(255, 255, 255);
    private String strCouleurTexteCarte = couleurTexteCarte.toString().substring(2, 8);
    private boolean bAfficheRadarCarte = false;
    private Color couleurLigneRadarCarte = Color.rgb(200, 0, 0);
    private String strCouleurLigneRadarCarte = couleurLigneRadarCarte.toString().substring(2, 8);
    private Color couleurFondRadarCarte = Color.rgb(200, 0, 0);
    private String strCouleurFondRadarCarte = couleurFondRadarCarte.toString().substring(2, 8);
    private double tailleRadarCarte = 20;
    private double opaciteRadarCarte = 0.4;
    private CoordonneesGeographiques coordCentreCarte;
    private int iFacteurZoomCarte = 14;
    private double angleRadarCarte = 45.d;
    private double ouvertureRadarCarte = 35.d;
    private String strNomLayers = "OpenStreetMap";
    private boolean bReplieDemarrageCarte = false;

    /*
     Eléments de l'onglet Carte
     */
    private CheckBox cbAfficheCarte;
    private Slider slOpaciteCarte;
    private RadioButton rbCarteLeft;
    private RadioButton rbCarteRight;
    private ColorPicker cpCouleurFondCarte;
    private ColorPicker cpCouleurTexteCarte;
    private Slider slLargeurCarte;
    private Slider slHauteurCarte;
    private Slider slZoomCarte;
    private CheckBox cbAfficheRadarCarte;
    private ColorPicker cpCouleurFondRadarCarte;
    private ColorPicker cpCouleurLigneRadarCarte;
    private Slider slTailleRadarCarte;
    private Slider slOpaciteRadarCarte;
    public NavigateurOpenLayersSeul navigateurCarteOL = null;
    public AnchorPane apNavigateurCarte;
    private CheckBox cbReplieDemarrageCarte;

    /*
     Variables Images Fond
     */
    private AnchorPane apImageFond;

    /*
     Variable du MenuContextuel
     */
    private AnchorPane apMenuContextuel;
    private AnchorPane apVisuMenuContextuel;
    private boolean bAfficheMenuContextuel = false;
    private boolean bAffichePrecSuivMC = true;
    private boolean bAffichePlanetNormalMC = true;
    private boolean bAffichePersMC1 = false;
    private String strPersLib1 = "";
    private String strPersURL1 = "";
    private boolean bAffichePersMC2 = false;
    private String strPersLib2 = "";
    private String strPersURL2 = "";

    /*
     Eléments de l'onglet MenuContextuel
     */
    private CheckBox cbAfficheMenuContextuel;
    private CheckBox cbAffichePrecSuivMC;
    private CheckBox cbAffichePlanetNormalMC;
    private CheckBox cbAffichePersMC1;
    private CheckBox cbAffichePersMC2;
    private TextField tfPersLib1;
    private TextField tfPersURL1;
    private TextField tfPersLib2;
    private TextField tfPersURL2;

    public Pane paneTabInterface;
    private HBox hbInterface;
    private AnchorPane apVisualisation;
    private VBox vbOutils;
    private RadioButton rbClair;
    private RadioButton rbSombre;
    private RadioButton rbPerso;
    private ComboBox cbImage;
    private ImageView ivVisualisation;
    final ToggleGroup tgImage = new ToggleGroup();
    final ToggleGroup tgPositionBarreClassique = new ToggleGroup();
    final ToggleGroup tgPosBouss = new ToggleGroup();
    final ToggleGroup tgPosMasque = new ToggleGroup();
    final ToggleGroup tgPosReseauxSociaux = new ToggleGroup();
    final ToggleGroup tgPosVignettes = new ToggleGroup();
    final ToggleGroup tgPosComboMenu = new ToggleGroup();
    final ToggleGroup tgPosPlan = new ToggleGroup();
    final ToggleGroup tgPosCarte = new ToggleGroup();
    final ToggleGroup tgPosTitre = new ToggleGroup();
    private Image imgClaire;
    private Image imgSombre;
    private HBox hbbarreBoutons;
    private HBox hbOutils;
    private Label lblTxtTitre;
    private Label lblTxtTitre2;
    private ImageView ivInfo;
    private ImageView ivAide;
    private ImageView ivAutoRotation;
    private ImageView ivModeSouris;
    private ImageView ivModeSouris2;
    private ImageView ivPleinEcran;
    private ImageView ivPleinEcran2;
    private HBox hbZoom;
    private ImageView ivZoomPlus;
    private ImageView ivZoomMoins;
    private HBox hbDeplacements;
    private ImageView ivHaut;
    private ImageView ivBas;
    private ImageView ivGauche;
    private ImageView ivDroite;
    private ImageView ivHotSpotPanoramique;
    private ImageView ivHotSpotImage;
    private ImageView ivHotSpotHTML;

    private String strRepertBoutonsPrincipal = "";
    private String strRepertHotSpots = "";
    private String strRepertHotSpotsPhoto = "";
    private String strRepertHotSpotsHTML = "";
    private String strRepertBoussoles = "";
    private CheckBox cbSuivantPrecedent;
    private ImageView imgSuivant;
    private ImageView imgPrecedent;
    private Pane paneFondSuivant;
    private Pane paneFondPrecedent;
    private boolean bSuivantPrecedent;
    private CheckBox cbAfficheTitre;
    private CheckBox cbTitreVisite;
    private CheckBox cbTitrePanoramique;
    private CheckBox cbTitreAdapte;
    private BigDecimalField bdfTitreDecalage;
    private RadioButton rbLeftTitre;
    private RadioButton rbCenterTitre;
    private RadioButton rbRightTitre;

    private ColorPicker cpCouleurFondTitre;
    private ColorPicker cpCouleurTitre;
    private ComboBox cbListePolicesTitre;
    private Slider slTaillePoliceTitre;
    private Slider slOpaciteTitre;
    private Slider slTailleTitre;
    private ColorPicker cpCouleurFondTheme;
    private ColorPicker cpCouleurTexteTheme;
    private Slider slOpaciteTheme;
    private ComboBox cbPoliceTheme;
    private ColorPicker cpCouleurHotspotsPanoramique;
    private ColorPicker cpCouleurHotspotsPhoto;
    private ColorPicker cpCouleurHotspotsHTML;
    private ColorPicker cpCouleurMasques;
    private Color couleurHotspots = Color.hsb(180, 0.39, 0.5);
    private Color couleurHotspotsPhoto = Color.hsb(180, 0.39, 0.5);
    private Color couleurHotspotsHTML = Color.hsb(180, 0.39, 0.5);
    private int iTailleHotspotsPanoramique = 25;
    private int iTailleHotspotsImage = 25;
    private int iTailleHotspotsHTML = 25;
    private Slider slTailleHotspotsPanoramique;
    private Slider slTailleHotspotsImage;
    private Slider slTailleHotspotsHTML;
    private Color couleurMasque = Color.hsb(180, 0.39, 0.5);
    private Color couleurFondTheme = Color.hsb(180, 0.39, 0.5);
    final private Color couleurTexteTheme = Color.valueOf("white");
    final private double opaciteTheme = 0.8;
    private Image[] imgBoutons = new Image[50];
    private String[] strNomImagesBoutons = new String[25];
    private PixelReader[] prLisBoutons = new PixelReader[25];
    private WritableImage[] wiNouveauxBoutons = new WritableImage[25];
    private PixelWriter[] pwNouveauxBoutons = new PixelWriter[25];
    private int iNombreImagesBouton = 0;
    private Image imgMasque;
    private PixelReader prLisMasque;
    private WritableImage wiNouveauxMasque;
    private PixelWriter pwNouveauxMasque;

    /**
     *
     * @param strStyleBarre
     * @param strHotSpot
     * @param strMA
     */
    private void chargeBarre(String strStyleBarre, String strHotSpot, String strMA) {
        File fileRepertBarre = new File(strRepertBoutonsPrincipal + File.separator + strStyleBarre);
        File[] fileRepertoires = fileRepertBarre.listFiles(IMAGE_FILTER);
        int i = 0;
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomFich = fileRepert.getName();
                String strNomFichComplet = fileRepert.getAbsolutePath();
                getImgBoutons()[i] = new Image("file:" + strNomFichComplet);
                getPrLisBoutons()[i] = getImgBoutons()[i].getPixelReader();
                int iLargeur = (int) getImgBoutons()[i].getWidth();
                int iHauteur = (int) getImgBoutons()[i].getHeight();
                getWiNouveauxBoutons()[i] = new WritableImage(iLargeur, iHauteur);
                getPwNouveauxBoutons()[i] = getWiNouveauxBoutons()[i].getPixelWriter();
                switch (strNomFich) {
                    case "aide.png":
                        ivAide = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "info.png":
                        ivInfo = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "haut.png":
                        ivHaut = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "bas.png":
                        ivBas = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "droite.png":
                        ivDroite = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "gauche.png":
                        ivGauche = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "fs.png":
                        ivPleinEcran = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "fs2.png":
                        ivPleinEcran2 = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "rotation.png":
                        ivAutoRotation = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "souris.png":
                        ivModeSouris = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "souris2.png":
                        ivModeSouris2 = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "zoomin.png":
                        ivZoomPlus = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "zoomout.png":
                        ivZoomMoins = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                }
                getStrNomImagesBoutons()[i] = strNomFich;
                i++;
            }
        }
        setiNombreImagesBouton(i);
        getImgBoutons()[getiNombreImagesBouton()] = new Image("file:" + strRepertHotSpots + File.separator + strHotSpot);
        getPrLisBoutons()[getiNombreImagesBouton()] = getImgBoutons()[getiNombreImagesBouton()].getPixelReader();
        int iLargeur = (int) getImgBoutons()[getiNombreImagesBouton()].getWidth();
        int iHauteur = (int) getImgBoutons()[getiNombreImagesBouton()].getHeight();
        getWiNouveauxBoutons()[getiNombreImagesBouton()] = new WritableImage(iLargeur, iHauteur);
        getPwNouveauxBoutons()[getiNombreImagesBouton()] = getWiNouveauxBoutons()[getiNombreImagesBouton()].getPixelWriter();
        if (strTypeHS.equals("gif")) {
            ivHotSpotPanoramique = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strHotSpot));
        } else {
            ivHotSpotPanoramique = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        }
        ivHotSpotPanoramique.setSmooth(true);
        setiNombreImagesBouton(i + 1);
        getImgBoutons()[getiNombreImagesBouton()] = new Image("file:" + strRepertHotSpotsPhoto + File.separator + getStrStyleHotSpotImages());
        getPrLisBoutons()[getiNombreImagesBouton()] = getImgBoutons()[getiNombreImagesBouton()].getPixelReader();
        iLargeur = (int) getImgBoutons()[getiNombreImagesBouton()].getWidth();
        iHauteur = (int) getImgBoutons()[getiNombreImagesBouton()].getHeight();
        getWiNouveauxBoutons()[getiNombreImagesBouton()] = new WritableImage(iLargeur, iHauteur);
        getPwNouveauxBoutons()[getiNombreImagesBouton()] = getWiNouveauxBoutons()[getiNombreImagesBouton()].getPixelWriter();
        if (strTypeHSImage.equals("gif")) {
            ivHotSpotImage = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + getStrStyleHotSpotImages()));
        } else {
            ivHotSpotImage = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        }
        ivHotSpotImage.setSmooth(true);
        setiNombreImagesBouton(i + 2);
        getImgBoutons()[getiNombreImagesBouton()] = new Image("file:" + strRepertHotSpotsHTML + File.separator + getStrStyleHotSpotHTML());
        getPrLisBoutons()[getiNombreImagesBouton()] = getImgBoutons()[getiNombreImagesBouton()].getPixelReader();
        iLargeur = (int) getImgBoutons()[getiNombreImagesBouton()].getWidth();
        iHauteur = (int) getImgBoutons()[getiNombreImagesBouton()].getHeight();
        getWiNouveauxBoutons()[getiNombreImagesBouton()] = new WritableImage(iLargeur, iHauteur);
        getPwNouveauxBoutons()[getiNombreImagesBouton()] = getWiNouveauxBoutons()[getiNombreImagesBouton()].getPixelWriter();
        if (strTypeHSHTML.equals("gif")) {
            ivHotSpotHTML = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + getStrStyleHotSpotHTML()));
        } else {
            ivHotSpotHTML = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        }
        ivHotSpotHTML.setSmooth(true);
        setImgMasque(new Image("file:" + strRepertMasques + File.separator + strMA));

        setPrLisMasque(getImgMasque().getPixelReader());
        iLargeur = (int) getImgMasque().getWidth();
        iHauteur = (int) getImgMasque().getHeight();
        setWiNouveauxMasque(new WritableImage(iLargeur, iHauteur));
        setPwNouveauxMasque(getWiNouveauxMasque().getPixelWriter());
        ivMasque = new ImageView(getWiNouveauxMasque());

        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurBarrePersonnalisee(double couleurFinale, double sat, double bright) {
        PixelReader prBarrePersonnalisee;
        prBarrePersonnalisee = imgPngBarrePersonnalisee.getPixelReader();
        setWiBarrePersonnaliseeCouleur(new WritableImage((int) imgPngBarrePersonnalisee.getWidth(), (int) imgPngBarrePersonnalisee.getHeight()));
        PixelWriter pwBarrePersonnaliseeCouleur = getWiBarrePersonnaliseeCouleur().getPixelWriter();
        if (isbCouleurOrigineBarrePersonnalisee()) {
            for (int y = 0; y < imgPngBarrePersonnalisee.getHeight(); y++) {
                for (int x = 0; x < imgPngBarrePersonnalisee.getWidth(); x++) {
                    Color color = prBarrePersonnalisee.getColor(x, y);
                    double brightness = color.getBrightness();
                    double hue = color.getHue();  //getHue() return 0.0-360.0
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    couleur = Color.hsb(hue, sat, bright, opacity);
                    pwBarrePersonnaliseeCouleur.setColor(x, y, prBarrePersonnalisee.getColor(x, y));
                }
            }

        } else {
            for (int y = 0; y < imgPngBarrePersonnalisee.getHeight(); y++) {
                for (int x = 0; x < imgPngBarrePersonnalisee.getWidth(); x++) {
                    Color color = prBarrePersonnalisee.getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    double bright1;
                    double sat1;
                    if (sat < 0.05) {
                        couleur = Color.hsb(couleurFinale, sat, (brightness + bright) / 2.d, opacity);
                    } else {
                        if (brightness > 0.9 || brightness < 0.1) {
                            bright1 = brightness;
                        } else {
                            bright1 = (brightness * 2.d + bright) / 3.d;
                        }
                        if (saturation < 0.35) {
                            sat1 = saturation;
                        } else {
                            sat1 = (saturation + sat) / 2.d;
                        }
                        couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                    }
                    pwBarrePersonnaliseeCouleur.setColor(x, y, couleur);
                }
            }

        }

    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurBarreClassique(double couleurFinale, double sat, double bright) {
        for (int i = 0; i < getiNombreImagesBouton() - 2; i++) {
            for (int y = 0; y < getImgBoutons()[i].getHeight(); y++) {
                for (int x = 0; x < getImgBoutons()[i].getWidth(); x++) {
                    Color color = getPrLisBoutons()[i].getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if (sat < 0.1) {
                        couleur = Color.hsb(couleurFinale, sat, bright, opacity);
                    } else {
                        if (saturation < 0.2) {
                            couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                        } else {
                            couleur = Color.hsb(couleurFinale, saturation * 0.4 + sat * 0.6, brightness * 0.4 + bright * 0.6, opacity);
                        }
                    }
                    getPwNouveauxBoutons()[i].setColor(x, y, couleur);
                }
            }
        }

    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurMasque(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < getImgMasque().getHeight(); y++) {
            for (int x = 0; x < getImgMasque().getWidth(); x++) {
                Color color = getPrLisMasque().getColor(x, y);
                double brightness = color.getBrightness();
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    double bright1;
                    if (brightness > 0.1 && brightness < 0.9) {
                        bright1 = brightness * 0.5 + bright * 0.5;
                    } else {
                        bright1 = brightness;
                    }
                    couleur = Color.hsb(couleurFinale, sat, bright1, opacity);
                } else {
                    double sat1;
                    double bright1;
                    if (saturation < 0.3 || (brightness < 0.2 || brightness > 0.8)) {
                        sat1 = saturation;
                        bright1 = brightness;
                    } else {
                        sat1 = saturation * (1 - saturation + sat);
                        double exp = Math.exp(-4 * Math.pow(2 * brightness - 1, 2));
                        bright1 = bright * exp + brightness * (1 - exp);
                    }
                    couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                }
                getPwNouveauxMasque().setColor(x, y, couleur);
            }
        }
    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurHS(double couleurFinale, double sat, double bright) {
        if (!strTypeHS.equals("gif")) {
            for (int y = 0; y < getImgBoutons()[getiNombreImagesBouton() - 2].getHeight(); y++) {
                for (int x = 0; x < getImgBoutons()[getiNombreImagesBouton() - 2].getWidth(); x++) {
                    Color color = getPrLisBoutons()[getiNombreImagesBouton() - 2].getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if ((sat < 0.02) && (saturation > 0.05)) {
                        double bright1;
                        if (brightness > 0.1 && brightness < 0.9) {
                            bright1 = brightness * 0.5 + bright * 0.5;
                        } else {
                            bright1 = brightness;
                        }
                        couleur = Color.hsb(couleurFinale, sat, bright1, opacity);
                    } else {
                        double sat1;
                        double bright1;
                        if (saturation < 0.3 || (brightness < 0.2 || brightness > 0.8)) {
                            sat1 = saturation;
                            bright1 = brightness;
                        } else {
                            sat1 = saturation * (1 - saturation + sat);
                            double exp = Math.exp(-4 * Math.pow(2 * brightness - 1, 2));
                            bright1 = bright * exp + brightness * (1 - exp);
                        }
                        couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                    }
                    getPwNouveauxBoutons()[getiNombreImagesBouton() - 2].setColor(x, y, couleur);
                }
            }
        }
    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurHSPhoto(double couleurFinale, double sat, double bright) {
        if (!strTypeHSImage.equals("gif")) {
            for (int y = 0; y < getImgBoutons()[getiNombreImagesBouton() - 1].getHeight(); y++) {
                for (int x = 0; x < getImgBoutons()[getiNombreImagesBouton() - 1].getWidth(); x++) {
                    Color color = getPrLisBoutons()[getiNombreImagesBouton() - 1].getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if ((sat < 0.02) && (saturation > 0.05)) {
                        double bright1;
                        if (brightness > 0.1 && brightness < 0.9) {
                            bright1 = brightness * 0.5 + bright * 0.5;
                        } else {
                            bright1 = brightness;
                        }
                        couleur = Color.hsb(couleurFinale, sat, bright1, opacity);
                    } else {
                        double sat1;
                        double bright1;
                        if (saturation < 0.3 || (brightness < 0.2 || brightness > 0.8)) {
                            sat1 = saturation;
                            bright1 = brightness;
                        } else {
                            sat1 = saturation * (1 - saturation + sat);
                            double exp = Math.exp(-4 * Math.pow(2 * brightness - 1, 2));
                            bright1 = bright * exp + brightness * (1 - exp);
                        }
                        couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                    }
                    getPwNouveauxBoutons()[getiNombreImagesBouton() - 1].setColor(x, y, couleur);
                }
            }
        }
    }

    /**
     *
     * @param couleurFinale
     * @param sat
     * @param bright
     */
    private void changeCouleurHSHTML(double couleurFinale, double sat, double bright) {
        if (!strTypeHSHTML.equals("gif")) {
            for (int y = 0; y < getImgBoutons()[getiNombreImagesBouton()].getHeight(); y++) {
                for (int x = 0; x < getImgBoutons()[getiNombreImagesBouton()].getWidth(); x++) {
                    Color color = getPrLisBoutons()[getiNombreImagesBouton()].getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if ((sat < 0.02) && (saturation > 0.05)) {
                        double bright1;
                        if (brightness > 0.1 && brightness < 0.9) {
                            bright1 = brightness * 0.5 + bright * 0.5;
                        } else {
                            bright1 = brightness;
                        }
                        couleur = Color.hsb(couleurFinale, sat, bright1, opacity);
                    } else {
                        double sat1;
                        double bright1;
                        if (saturation < 0.3 || (brightness < 0.2 || brightness > 0.8)) {
                            sat1 = saturation;
                            bright1 = brightness;
                        } else {
                            sat1 = saturation * (1 - saturation + sat);
                            double exp = Math.exp(-4 * Math.pow(2 * brightness - 1, 2));
                            bright1 = bright * exp + brightness * (1 - exp);
                        }
                        couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                    }
                    getPwNouveauxBoutons()[getiNombreImagesBouton()].setColor(x, y, couleur);
                }
            }
        }
    }

    /**
     *
     */
    private void afficheBoussole() {
        imgAiguille.setVisible(isbAfficheBoussole());
        imgBoussole.setVisible(isbAfficheBoussole());

        imgAiguille.setFitWidth(getTailleBoussole() / 5);
        imgAiguille.setFitHeight(getTailleBoussole());
        imgAiguille.setOpacity(getOpaciteBoussole());
        imgAiguille.setSmooth(true);

        imgBoussole.setImage(new Image("file:" + strRepertBoussoles + File.separator + getStrImageBoussole()));
        imgBoussole.setFitWidth(getTailleBoussole());
        imgBoussole.setFitHeight(getTailleBoussole());
        imgBoussole.setOpacity(getOpaciteBoussole());
        imgBoussole.setSmooth(true);
        String strPositXBoussole = getStrPositionBoussole().split(":")[1];
        String strPositYBoussole = getStrPositionBoussole().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getOffsetXBoussole();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getOffsetYBoussole();
                break;
        }
        switch (getStrPositionVignettes()) {
            case "bottom":
                if (strPositYBoussole.equals("bottom")) {
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                }
                break;
            case "left":
                if (strPositXBoussole.equals("left")) {
                    posX = ivVisualisation.getLayoutX() + getOffsetXBoussole() + apVisuVignettes.getPrefWidth();
                }
                break;
            case "right":
                if (strPositXBoussole.equals("right")) {
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                }
                break;
        }
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);
        imgAiguille.setOpacity(getOpaciteBoussole());
        imgAiguille.setVisible(isbAfficheBoussole());

        imgBoussole.setOpacity(getOpaciteBoussole());
        imgBoussole.setVisible(isbAfficheBoussole());
    }

    /**
     *
     * @param index
     */
    private void afficheImage(int index) {
        Image imgAffiche = getPanoramiquesProjet()[index].getImgPanoramique();
        Rectangle2D r2dVue = new Rectangle2D(200, 0, 800, 600);
        ivVisualisation.setViewport(r2dVue);
        ivVisualisation.setImage(imgAffiche);
    }

    /**
     *
     */
    private void afficheMasque() {
        apVisualisation.getChildren().remove(ivMasque);
        apVisualisation.getChildren().add(ivMasque);
        ivMasque.setVisible(isbAfficheMasque());
        ivMasque.setFitWidth(getTailleMasque());
        ivMasque.setFitHeight(getTailleMasque());
        ivMasque.setOpacity(getOpaciteMasque());
        String strPositXMasque = getStrPositionMasque().split(":")[1];
        String strPositYMasque = getStrPositionMasque().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXMasque) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getdXMasque();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getdXMasque() - ivMasque.getFitWidth();
                break;
        }
        switch (strPositYMasque) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - ivMasque.getFitHeight() - getdYMasque();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getdYMasque();
                break;
        }
        ivMasque.setLayoutX(posX);
        ivMasque.setLayoutY(posY);
    }

    /**
     *
     */
    private void afficheReseauxSociaux() {
        ivTwitter.setVisible(isbAfficheReseauxSociaux());
        ivGoogle.setVisible(isbAfficheReseauxSociaux());
        ivFacebook.setVisible(isbAfficheReseauxSociaux());
        ivEmail.setVisible(isbAfficheReseauxSociaux());
        ivTwitter.setFitWidth(getTailleReseauxSociaux());
        ivTwitter.setFitHeight(getTailleReseauxSociaux());
        ivTwitter.setOpacity(getOpaciteReseauxSociaux());
        ivTwitter.setSmooth(true);
        ivTwitter.setVisible(false);
        ivGoogle.setFitWidth(getTailleReseauxSociaux());
        ivGoogle.setFitHeight(getTailleReseauxSociaux());
        ivGoogle.setOpacity(getOpaciteReseauxSociaux());
        ivGoogle.setSmooth(true);
        ivGoogle.setVisible(false);
        ivFacebook.setFitWidth(getTailleReseauxSociaux());
        ivFacebook.setFitHeight(getTailleReseauxSociaux());
        ivFacebook.setOpacity(getOpaciteReseauxSociaux());
        ivFacebook.setSmooth(true);
        ivFacebook.setVisible(false);
        ivEmail.setFitWidth(getTailleReseauxSociaux());
        ivEmail.setFitHeight(getTailleReseauxSociaux());
        ivEmail.setOpacity(getOpaciteReseauxSociaux());
        ivEmail.setSmooth(true);
        ivEmail.setVisible(false);
        String strPositXReseauxSociaux = getStrPositionReseauxSociaux().split(":")[1];
        String strPositYReseauxSociaux = getStrPositionReseauxSociaux().split(":")[0];
        double posX;
        double posY = 0;
        double dX;
        switch (strPositXReseauxSociaux) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getdXReseauxSociaux();
                dX = ivEmail.getFitWidth() + 5;
                if (isbReseauxSociauxTwitter() && isbAfficheReseauxSociaux()) {
                    ivTwitter.setLayoutX(posX);
                    ivTwitter.setVisible(true);
                    posX += dX;

                }
                if (isbReseauxSociauxGoogle() && isbAfficheReseauxSociaux()) {
                    ivGoogle.setLayoutX(posX);
                    ivGoogle.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxFacebook() && isbAfficheReseauxSociaux()) {
                    ivFacebook.setLayoutX(posX);
                    ivFacebook.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }

                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getdXReseauxSociaux() - ivEmail.getFitWidth();
                dX = -(ivEmail.getFitWidth() + 5);
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxFacebook() && isbAfficheReseauxSociaux()) {
                    ivFacebook.setLayoutX(posX);
                    ivFacebook.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxGoogle() && isbAfficheReseauxSociaux()) {
                    ivGoogle.setLayoutX(posX);
                    ivGoogle.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxTwitter() && isbAfficheReseauxSociaux()) {
                    ivTwitter.setLayoutX(posX);
                    ivTwitter.setVisible(true);
                    posX += dX;
                }
                break;
        }
        switch (strPositYReseauxSociaux) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - ivEmail.getFitHeight() - getdYReseauxSociaux();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getdYReseauxSociaux();
                break;
        }
        ivTwitter.setLayoutY(posY);
        ivGoogle.setLayoutY(posY);
        ivFacebook.setLayoutY(posY);
        ivEmail.setLayoutY(posY);
    }

    /**
     *
     */
    public void afficheDiaporama() {
        apAfficheDiapo.getChildren().clear();
        apAfficheDiapo.setOpacity(getDiaporamaOpacite());
        apAfficheDiapo.setStyle("-fx-background-color : " + getStrCouleurDiaporama());
        apAfficheDiapo.setVisible(bVisualiseDiaporama);
        ivDiapo.setVisible(bVisualiseDiaporama);
    }

    /**
     *
     */
    public void afficheFenetreInfo() {
        if (bAfficheFenetreInfo) {
            apFenetreAfficheInfo.setVisible(true);
            lblFenetreURL.setVisible(true);
            apFenetreAfficheInfo.getChildren().clear();
            if (isbFenetreInfoPersonnalise()) {
                Image imgFenetreInfo = new Image("file:" + getStrFenetreInfoImage());
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setFitWidth(largeurInfo * getFenetreInfoTaille() / 100);
                ivFenetreInfo.setFitHeight(hauteurInfo * getFenetreInfoTaille() / 100);
                ivFenetreInfo.setPreserveRatio(true);
                ivFenetreInfo.setOpacity(getFenetreInfoOpacite());
                Font fonte1 = new Font("Arial", 12);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreInfo.getFitWidth()) / 2 + getFenetreInfoPosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreInfo.getFitHeight()) / 2 + getFenetreInfoPosY() + ivVisualisation.getLayoutY());
                lblFenetreURL.setText(getStrFenetreTexteURL());
                lblFenetreURL.impl_processCSS(true);
                lblFenetreURL.setStyle("-fx-font-size:" + Math.round(getFenetrePoliceTaille() * 10) / 10 + "px;-fx-font-family: \"Arial\";");
                lblFenetreURL.setTextFill(Color.valueOf(getStrFenetreURLCouleur()));
                apFenetreAfficheInfo.getChildren().addAll(ivFenetreInfo);
                double URLPosX = (ivVisualisation.getFitWidth() - lblFenetreURL.prefWidth(-1)) / 2 + getFenetreURLPosX() + ivVisualisation.getLayoutX();
                double URLPosY = (ivVisualisation.getFitHeight() - lblFenetreURL.prefHeight(-1)) / 2 + getFenetreURLPosY() + ivVisualisation.getLayoutY();
                lblFenetreURL.relocate(URLPosX, URLPosY);
            } else {
                Image imgFenetreInfo = new Image("file:" + getStrRepertAppli() + File.separator + "images" + File.separator + "infoDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
            lblFenetreURL.setVisible(false);
        }
    }

    /**
     *
     */
    public void afficheFenetreAide() {
        if (bAfficheFenetreAide) {
            apFenetreAfficheInfo.setVisible(true);
            apFenetreAfficheInfo.getChildren().clear();
            if (isbFenetreAidePersonnalise()) {
                Image imgFenetreAide = new Image("file:" + getStrFenetreAideImage());
                double largeurAide = imgFenetreAide.getWidth();
                double hauteurAide = imgFenetreAide.getHeight();
                ImageView ivFenetreAide = new ImageView(imgFenetreAide);
                ivFenetreAide.setFitWidth(largeurAide * getFenetreAideTaille() / 100);
                ivFenetreAide.setFitHeight(hauteurAide * getFenetreAideTaille() / 100);
                ivFenetreAide.setPreserveRatio(true);
                ivFenetreAide.setOpacity(getFenetreAideOpacite());
                apFenetreAfficheInfo.getChildren().add(ivFenetreAide);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreAide.getFitWidth()) / 2 + getFenetreAidePosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreAide.getFitHeight()) / 2 + getFenetreAidePosY() + ivVisualisation.getLayoutY());

            } else {
                Image imgFenetreInfo = new Image("file:" + getStrRepertAppli() + File.separator + "images/aideDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
        }

    }

    /**
     *
     */
    public void afficheCarte() {
        apVisuCarte.getChildren().clear();
        if (isbAfficheCarte()) {
            Label lblTitreCarte = new Label("Carte");
            lblTitreCarte.setPrefSize(160, 30);
            lblTitreCarte.setAlignment(Pos.CENTER);
            lblTitreCarte.setTranslateX(-lblTitreCarte.getPrefWidth() / 2 + lblTitreCarte.getPrefHeight() / 2);
            lblTitreCarte.setTranslateY(lblTitreCarte.getPrefWidth() / 2 - lblTitreCarte.getPrefHeight() / 2);
            double marge = 10.d;
            AnchorPane apVisuCarte2 = new AnchorPane();
            apVisuCarte2.getChildren().add(apNavigateurCarte);
            apVisuCarte2.setPrefSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
            apVisuCarte.setPrefSize(largeurCarte + marge * 2 + 30, hauteurCarte + marge * 2);
            apVisuCarte2.setMinSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
            apVisuCarte2.setMaxSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
            apNavigateurCarte.setPrefSize(largeurCarte, hauteurCarte);
            apNavigateurCarte.setMinSize(largeurCarte, hauteurCarte);
            apNavigateurCarte.setMaxSize(largeurCarte, hauteurCarte);
            apNavigateurCarte.setLayoutX(marge);
            apNavigateurCarte.setLayoutY(marge);
            int rouge, vert, bleu;
            rouge = (int) Math.round(getCouleurFondCarte().getRed() * 255);
            vert = (int) Math.round(getCouleurFondCarte().getGreen() * 255);
            bleu = (int) Math.round(getCouleurFondCarte().getBlue() * 255);

            apVisuCarte2.setStyle("-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpaciteCarte() + ");");
            double positionX = 0;
            double positionY;
            if (isbAfficheTitre()) {
                if (lblTxtTitre2.isVisible()) {
                    positionY = ivVisualisation.getLayoutY() + lblTxtTitre.getHeight() + lblTxtTitre2.getHeight();
                } else {
                    positionY = ivVisualisation.getLayoutY() + lblTxtTitre.getHeight();
                }
            } else {
                positionY = ivVisualisation.getLayoutY();
            }
            lblTitreCarte.setStyle("-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpaciteCarte() + ");-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");
            lblTitreCarte.setTextFill(couleurTexteCarte);
            switch (getStrPositionCarte()) {
                case "left":
                    positionX = ivVisualisation.getLayoutX();
                    lblTitreCarte.setLayoutX(apVisuCarte2.getPrefWidth());
                    lblTitreCarte.setRotate(270);
                    break;
                case "right":
                    positionX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuCarte.getPrefWidth();
                    lblTitreCarte.setRotate(90);
                    lblTitreCarte.setLayoutX(0);
                    apVisuCarte2.setLayoutX(30);
                    break;
            }
            apVisuCarte.setLayoutX(positionX);
            apVisuCarte.setLayoutY(positionY);
            if (bAfficheRadarCarte) {
                CoordonneesGeographiques coords;
                if (getCoordCentreCarte() != null) {
                    coords = getCoordCentreCarte();
                } else {
                    coords = navigateurCarteOL.recupereCoordonnees();
                }
                if (getiNombrePanoramiques() > 0) {
                    if (getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation() != null) {
                        navigateurCarteOL.afficheRadar(
                                getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation(), angleRadarCarte, ouvertureRadarCarte, getTailleRadarCarte(), "#" + getStrCouleurLigneRadarCarte(), "#" + getStrCouleurFondRadarCarte(), getOpaciteRadarCarte()
                        );
                    } else {
                        navigateurCarteOL.afficheRadar(
                                coords, angleRadarCarte, ouvertureRadarCarte, getTailleRadarCarte(), "#" + getStrCouleurLigneRadarCarte(), "#" + getStrCouleurFondRadarCarte(), getOpaciteRadarCarte()
                        );
                    }
                } else {
                    navigateurCarteOL.afficheRadar(
                            coords, angleRadarCarte, ouvertureRadarCarte, getTailleRadarCarte(), "#" + getStrCouleurLigneRadarCarte(), "#" + getStrCouleurFondRadarCarte(), getOpaciteRadarCarte()
                    );
                }

            }
            apVisuCarte.getChildren().addAll(apVisuCarte2, lblTitreCarte);

        }
        apVisuCarte.setVisible(isbAfficheCarte());
    }

    /**
     *
     */
    public void affichePlan() {
        apVisuPlan.getChildren().clear();
        if (isbAffichePlan()) {
            Label lblTitrePlan = new Label("Plan");
            lblTitrePlan.setPrefSize(160, 30);
            lblTitrePlan.setAlignment(Pos.CENTER);
            lblTitrePlan.setTranslateX(-lblTitrePlan.getPrefWidth() / 2 + lblTitrePlan.getPrefHeight() / 2);
            lblTitrePlan.setTranslateY(lblTitrePlan.getPrefWidth() / 2 - lblTitrePlan.getPrefHeight() / 2);
            AnchorPane apVisuPlan2 = new AnchorPane();

            double marge = 10.d;
            ImageView ivHSPlanActif = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/plan/pointActif.png", 12, 12, true, true));
            ImageView ivHSPlan = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/plan/point.png", 12, 12, true, true));
            Image imgPlan;
            if (getiNombrePlans() > 0) {
                String strFichier = getStrRepertTemp() + "/images/" + getPlans()[getGestionnairePlan().getiPlanActuel()].getStrImagePlan();
                imgPlan = new Image(
                        "file:" + strFichier, getLargeurPlan(), -1, true, true
                );
            } else {
                imgPlan = new Image(
                        "file:" + getStrRepertAppli() + "/theme/plan/planDefaut.jpg", getLargeurPlan(), -1, true, true
                );

            }
            ImageView ivImgPlan = new ImageView(imgPlan);
            apVisuPlan.setPrefSize(imgPlan.getWidth() + marge * 2 + 30, imgPlan.getHeight() + marge * 2);
            apVisuPlan.setMaxSize(imgPlan.getWidth() + marge * 2 + 30, imgPlan.getHeight() + marge * 2);
            apVisuPlan.setMinSize(imgPlan.getWidth() + marge * 2 + 30, imgPlan.getHeight() + marge * 2);
            apVisuPlan2.setPrefSize(imgPlan.getWidth() + marge * 2, imgPlan.getHeight() + marge * 2);
            apVisuPlan2.setMinSize(imgPlan.getWidth() + marge * 2, imgPlan.getHeight() + marge * 2);
            apVisuPlan2.setMaxSize(imgPlan.getWidth() + marge * 2, imgPlan.getHeight() + marge * 2);
            int rouge, vert, bleu;
            rouge = (int) Math.round(getCouleurFondPlan().getRed() * 255);
            vert = (int) Math.round(getCouleurFondPlan().getGreen() * 255);
            bleu = (int) Math.round(getCouleurFondPlan().getBlue() * 255);

            apVisuPlan2.setStyle("-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpacitePlan() + ");");
            ivImgPlan.setLayoutX(marge);
            ivImgPlan.setLayoutY(marge);
            apVisuPlan2.getChildren().addAll(ivImgPlan, ivHSPlan);
            lblTitrePlan.setStyle("-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpaciteCarte() + ");-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");
            lblTitrePlan.setTextFill(couleurTextePlan);

            double positionX = 0;
            double positionY;
            if (isbAfficheTitre()) {
                if (lblTxtTitre2.isVisible()) {
                    positionY = ivVisualisation.getLayoutY() + lblTxtTitre.getHeight() + lblTxtTitre2.getHeight();
                } else {
                    positionY = ivVisualisation.getLayoutY() + lblTxtTitre.getHeight();
                }
            } else {
                positionY = ivVisualisation.getLayoutY();
            }
            switch (getStrPositionPlan()) {
                case "left":
                    positionX = ivVisualisation.getLayoutX();
                    lblTitrePlan.setLayoutX(apVisuPlan2.getPrefWidth());
                    lblTitrePlan.setRotate(270);
                    break;
                case "right":
                    positionX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuPlan.getPrefWidth();
                    lblTitrePlan.setRotate(90);
                    lblTitrePlan.setLayoutX(0);
                    apVisuPlan2.setLayoutX(30);
                    break;
            }
            apVisuPlan.setLayoutX(positionX);
            apVisuPlan.setLayoutY(positionY);

            ivHSPlan.setLayoutX(80);
            ivHSPlan.setLayoutY(40);
            ivHSPlanActif.setLayoutX(30);
            ivHSPlanActif.setLayoutY(90);
            if (isbAfficheRadar()) {
                Arc arcRadar = new Arc(36, 96, getTailleRadar(), getTailleRadar(), -55, 70);
                arcRadar.setType(ArcType.ROUND);
                arcRadar.setFill(getCouleurFondRadar());
                arcRadar.setStroke(getCouleurLigneRadar());
                arcRadar.setOpacity(getOpaciteRadar());
                apVisuPlan2.getChildren().addAll(arcRadar, ivHSPlanActif);
            } else {
                apVisuPlan2.getChildren().add(ivHSPlanActif);
            }
            if (getiNombrePlans() > 0) {

                String strRepertImagePlan = getStrRepertAppli() + File.separator + "theme/plan";
                String strImageBoussole1 = "file:" + strRepertImagePlan + "/aiguillePlan.png";
                Image imgBoussole1 = new Image(strImageBoussole1);
                ImageView ivNord = new ImageView(imgBoussole1);

                String strPositX = getPlans()[getGestionnairePlan().getiPlanActuel()].getStrPosition().split(":")[1];
                String strPositY = getPlans()[getGestionnairePlan().getiPlanActuel()].getStrPosition().split(":")[0];
                positionX = 0;
                positionY = 0;
                switch (strPositX) {
                    case "left":
                        positionX = ivImgPlan.getLayoutX() + getPlans()[getGestionnairePlan().getiPlanActuel()].getPositionX();
                        break;
                    case "right":
                        positionX = ivImgPlan.getLayoutX() + imgPlan.getWidth() - imgBoussole1.getWidth() - getPlans()[getGestionnairePlan().getiPlanActuel()].getPositionX();
                        break;
                }
                switch (strPositY) {
                    case "top":
                        positionY = ivImgPlan.getLayoutY() + getPlans()[getGestionnairePlan().getiPlanActuel()].getPositionY();
                        break;
                    case "bottom":
                        positionY = ivImgPlan.getLayoutY() + imgPlan.getHeight() - imgBoussole1.getHeight() - getPlans()[getGestionnairePlan().getiPlanActuel()].getPositionY();
                        break;
                }
                ivNord.setLayoutX(positionX);
                ivNord.setLayoutY(positionY);
                ivNord.setRotate(getPlans()[getGestionnairePlan().getiPlanActuel()].getDirectionNord());
                apVisuPlan2.getChildren().add(ivNord);
            }
            apVisuPlan.getChildren().addAll(apVisuPlan2, lblTitrePlan);
        }
        apVisuPlan.setVisible(isbAffichePlan());

    }

    /**
     *
     */
    public void afficheBoutonVisiteAuto() {
        apVisualisation.getChildren().remove(apVisuBoutonVisiteAuto);
        apVisuBoutonVisiteAuto.getChildren().clear();
        apVisualisation.getChildren().add(apVisuBoutonVisiteAuto);
        if (isbAfficheBoutonVisiteAuto() && isbAutoTourDemarre()) {
            apVisuBoutonVisiteAuto.setVisible(true);
            apVisuBoutonVisiteAuto.getChildren().add(ivBtnVisiteAuto);
            apVisuBoutonVisiteAuto.setPrefWidth(tailleBoutonVisiteAuto);
            apVisuBoutonVisiteAuto.setPrefHeight(tailleBoutonVisiteAuto);
            apVisuBoutonVisiteAuto.setTranslateZ(4);
            ivBtnVisiteAuto.setFitWidth(tailleBoutonVisiteAuto);
            double posX = 0, posY = 0;
            switch (getStrPositionXBoutonVisiteAuto()) {
                case "left":
                    posX = ivVisualisation.getLayoutX() + getOffsetXBoutonVisiteAuto();
                    break;
                case "center":
                    posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apVisuBoutonVisiteAuto.getPrefWidth()) / 2 + getOffsetXBoutonVisiteAuto();
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuBoutonVisiteAuto.getPrefWidth() - getOffsetXBoutonVisiteAuto();
                    break;
            }
            switch (getStrPositionYBoutonVisiteAuto()) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + getOffsetYBoutonVisiteAuto();
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuBoutonVisiteAuto.getPrefHeight() - getOffsetYBoutonVisiteAuto();
                    break;
            }
            apVisuBoutonVisiteAuto.setLayoutX(posX);
            apVisuBoutonVisiteAuto.setLayoutY(posY);

        }

    }

    /**
     *
     */
    private void afficheComboMenu() {
        apVisuComboMenu.getChildren().clear();
        if (isbAfficheComboMenu()) {
            apVisuComboMenu.setPrefWidth(302);
            apVisuComboMenu.setPrefHeight(50);
            ImageView ivImageMenu;
            if (isbAfficheComboMenuImages()) {
                ivImageMenu = new ImageView(new Image("file:" + getStrRepertAppli() + "/images/menuAvecImage.jpg"));
            } else {
                ivImageMenu = new ImageView(new Image("file:" + getStrRepertAppli() + "/images/menuSansImage.jpg"));
            }
            apVisuComboMenu.getChildren().add(ivImageMenu);
            double posX = 0, posY = 0;
            switch (getStrPositionXComboMenu()) {
                case "left":
                    posX = ivVisualisation.getLayoutX() + getOffsetXComboMenu();
                    break;
                case "center":
                    posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth()) / 2 + getOffsetXComboMenu();
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth() - getOffsetXComboMenu();
                    break;
            }
            switch (getStrPositionYComboMenu()) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + getOffsetYComboMenu();
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuComboMenu.getPrefHeight() - getOffsetYComboMenu();
                    break;
            }
            apVisuComboMenu.setLayoutX(posX);
            apVisuComboMenu.setLayoutY(posY);
        }
    }

    /**
     *
     */
    private void afficheTitre() {
        Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte1);
        lblTxtTitre.setPrefHeight(-1);

        fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()) * 3.d / 4.d);
        lblTxtTitre2.setFont(fonte1);
        lblTxtTitre2.setPrefHeight(-1);
        lblTxtTitre.setPadding(new Insets(5, 5, 5, 5));
        lblTxtTitre2.setPadding(new Insets(5, 5, 5, 5));

        Color coul1 = cpCouleurFondTitre.getValue();
        double iRouge1 = (int) (coul1.getRed() * 255.d);
        double iBleu1 = (int) (coul1.getBlue() * 255.d);
        double iVert1 = (int) (coul1.getGreen() * 255.d);
        String strCoulFond1 = "rgba(" + iRouge1 + "," + iVert1 + "," + iBleu1 + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond1);
        lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        lblTxtTitre2.setStyle("-fx-background-color : " + strCoulFond1);
        lblTxtTitre2.setTextFill(Color.valueOf(getStrCouleurTitre()));
        if (isbTitreVisite()) {
            lblTxtTitre.setText(rbLocalisation.getString("main.titreVisite"));
        } else {
            lblTxtTitre.setText(rbLocalisation.getString("main.titrePano"));
        }
        if (!isbTitreVisite() && !isbTitrePanoramique()) {
            lblTxtTitre.setVisible(false);
        }
        if (isbTitreVisite() && isbTitrePanoramique() && isbAfficheTitre()) {
            lblTxtTitre2.setVisible(true);
            lblTxtTitre2.setLayoutY(lblTxtTitre.getLayoutY() + lblTxtTitre.getHeight());
            lblTxtTitre2.setLayoutX(lblTxtTitre.getLayoutX());
            lblTxtTitre2.setPrefHeight(-1);
        } else {
            lblTxtTitre2.setVisible(false);
            lblTxtTitre2.setPrefHeight(0);
        }
        double tailleTitre = 0;
        if (!bTitreAdapte) {
            double taille = (double) getTitreTaille() / 100.d * ivVisualisation.getFitWidth();
            lblTxtTitre.setPrefWidth(taille);
            lblTxtTitre.setMinWidth(taille);
            lblTxtTitre2.setPrefWidth(taille);
        } else {
            double tailleTitre2 = 0;
            tailleTitre = TextUtils.computeTextWidth(lblTxtTitre.getFont(), lblTxtTitre.getText(), 0.0D) + 15;

            if (lblTxtTitre2.isVisible()) {
                tailleTitre2 = TextUtils.computeTextWidth(lblTxtTitre.getFont(), lblTxtTitre.getText(), 0.0D) + 15;
            }
            if (tailleTitre2 > tailleTitre) {
                tailleTitre = tailleTitre2;
            }
            tailleTitre += getTitreDecalage();
            lblTxtTitre.setPrefWidth(tailleTitre);
            lblTxtTitre2.setPrefWidth(tailleTitre);
            lblTxtTitre.setMinWidth(0);
        }
        switch (getStrTitrePosition()) {
            case "left":
                lblTxtTitre.setAlignment(Pos.CENTER_LEFT);
                lblTxtTitre2.setAlignment(Pos.CENTER_LEFT);

                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX());
                lblTxtTitre2.setLayoutX(ivVisualisation.getLayoutX());

                lblTxtTitre.setPadding(new Insets(5, 5, 5, getTitreDecalage()));
                lblTxtTitre2.setPadding(new Insets(5, 5, 5, getTitreDecalage()));
                break;
            case "center":
                lblTxtTitre.setAlignment(Pos.CENTER);

                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getPrefWidth()) / 2);
                lblTxtTitre2.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre2.getPrefWidth()) / 2);
                lblTxtTitre2.setAlignment(Pos.CENTER);
                break;
            case "right":
                lblTxtTitre.setAlignment(Pos.CENTER_RIGHT);
                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getPrefWidth()));
                lblTxtTitre2.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getPrefWidth()));
                lblTxtTitre.setPadding(new Insets(5, getTitreDecalage(), 5, 5));
                lblTxtTitre2.setPadding(new Insets(5, getTitreDecalage(), 5, 5));
                lblTxtTitre2.setAlignment(Pos.CENTER_RIGHT);
                break;
        }
        afficheVignettes();
        affichePlan();
        afficheCarte();
    }

    /**
     *
     */
    private void afficheVignettes() {
        paneFondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - paneFondPrecedent.getPrefWidth()));
        String strPositVert = getStrPositionBarreClassique().split(":")[0];
        String strPositHor = getStrPositionBarreClassique().split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (strPositVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + getOffsetYBarreClassique();
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique();
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - getOffsetYBarreClassique();
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique();
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique();
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth()) / 2 + getOffsetXBarreClassique();
                break;
        }

        String strPositXBoussole = getStrPositionBoussole().split(":")[1];
        String strPositYBoussole = getStrPositionBoussole().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getOffsetXBoussole();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getOffsetYBoussole();
                break;
        }

        apVisuVignettes.setVisible(isbAfficheVignettes());
        apVisuVignettes.getChildren().clear();
        if (isbAfficheVignettes()) {
            Label lblTitreVignettes = new Label("Vignettes");
            lblTitreVignettes.setPrefSize(160, 30);
            lblTitreVignettes.setAlignment(Pos.CENTER);
            AnchorPane apVisuVignettes2 = new AnchorPane();
            ImageView[] ivVignettes = new ImageView[getiNombrePanoramiques()];
            apVisuVignettes2.setOpacity(getOpaciteVignettes());
            apVisuVignettes2.setStyle("-fx-background-color : " + getStrCouleurFondVignettes());
            lblTitreVignettes.setOpacity(getOpaciteVignettes());
            lblTitreVignettes.setTextFill(Color.valueOf(strCouleurTexteVignettes));
            lblTitreVignettes.setStyle("-fx-background-color : " + getStrCouleurFondVignettes() + ";-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");

            switch (getStrPositionVignettes()) {
                case "bottom":
                    lblTitreVignettes.setStyle("-fx-background-color : " + getStrCouleurFondVignettes() + ";-fx-background-radius : 5 5 0 0;-fx-font-family : Verdana,Arial,sans-serif;");
                    apVisuVignettes2.setPrefSize(ivVisualisation.getFitWidth(), getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes2.setMinSize(ivVisualisation.getFitWidth(), getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes2.setLayoutX(0);
                    apVisuVignettes2.setLayoutY(30);
                    lblTitreVignettes.setLayoutX(apVisuVignettes2.getPrefWidth() - lblTitreVignettes.getPrefWidth());
                    lblTitreVignettes.setLayoutY(0);
                    apVisuVignettes.setPrefSize(ivVisualisation.getFitWidth(), getTailleImageVignettes() / 2 + 40);
                    apVisuVignettes.setMinSize(ivVisualisation.getFitWidth(), getTailleImageVignettes() / 2 + 40);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuVignettes.getPrefHeight());
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    if (strPositYBoussole.equals("bottom")) {
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    lblTitreVignettes.setTranslateX(-lblTitreVignettes.getPrefWidth() / 2 + lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setTranslateY(lblTitreVignettes.getPrefWidth() / 2 - lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setRotate(270);
                    if (isbAfficheTitre()) {
                        if (lblTxtTitre2.isVisible()) {
                            apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                        }
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setMinWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    apVisuVignettes2.setPrefWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes2.setMinWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes2.setLayoutX(0);
                    lblTitreVignettes.setLayoutX(apVisuVignettes2.getPrefWidth());
                    if (isbAfficheTitre()) {
                        if (lblTxtTitre2.isVisible()) {
                            apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight() + lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight());
                        }
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    paneFondPrecedent.setLayoutX(ivVisualisation.getLayoutX() + apVisuVignettes.getPrefWidth());
                    if (strPositHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth();
                    }
                    if (strPositXBoussole.equals("left")) {
                        posX = ivVisualisation.getLayoutX() + getOffsetXBoussole() + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    lblTitreVignettes.setTranslateX(-lblTitreVignettes.getPrefWidth() / 2 + lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setTranslateY(lblTitreVignettes.getPrefWidth() / 2 - lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setRotate(90);
                    if (isbAfficheTitre()) {
                        if (lblTxtTitre2.isVisible()) {
                            apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());

                        }
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes2.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes2.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setMinWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuVignettes.getPrefWidth());
                    apVisuVignettes2.setPrefWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes2.setMinWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes2.setLayoutX(30);
                    lblTitreVignettes.setLayoutX(0);
                    if (isbAfficheTitre()) {
                        if (lblTxtTitre2.isVisible()) {
                            apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight() + lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight());
                        }
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - paneFondPrecedent.getPrefWidth()) - apVisuVignettes.getPrefWidth());
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    if (strPositXBoussole.equals("right")) {
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
            int iMaxVignettes = 5;
            int iNombre = (getiNombrePanoramiques() > iMaxVignettes) ? iMaxVignettes : getiNombrePanoramiques();
            for (int i = 0; i < iNombre; i++) {
                ivVignettes[i] = new ImageView(getPanoramiquesProjet()[i].getImgVignettePanoramique());
                ivVignettes[i].setFitWidth(getTailleImageVignettes());
                ivVignettes[i].setFitHeight(getTailleImageVignettes() / 2);
                switch (getStrPositionVignettes()) {
                    case "bottom":
                        ivVignettes[i].setLayoutX((getTailleImageVignettes() + 10) * i + 5);
                        ivVignettes[i].setLayoutY(5);
                        break;
                    case "left":
                        ivVignettes[i].setLayoutY((getTailleImageVignettes() / 2 + 10) * i + 5);
                        ivVignettes[i].setLayoutX(5);
                        break;
                    case "right":
                        ivVignettes[i].setLayoutY((getTailleImageVignettes() / 2 + 10) * i + 5);
                        ivVignettes[i].setLayoutX(5);
                        break;
                }
                apVisuVignettes2.getChildren().add(ivVignettes[i]);
            }
            apVisuVignettes.getChildren().addAll(apVisuVignettes2, lblTitreVignettes);
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);
        afficheBarrePersonnalisee();
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());

    }

    /**
     *
     * @param nb
     * @return
     */
    private int chercheZone(int nb) {
        int iNumero = -1;
        String strZone = "area-" + nb;
        for (int i = 0; i < getiNombreZonesBarrePersonnalisee(); i++) {
            if (getZonesBarrePersonnalisee()[i].getStrIdZone().equals(strZone)) {
                iNumero = i;
            }
        }
        return iNumero;
    }

    /**
     *
     */
    public void afficheBarrePersonnalisee() {
        apAfficheBarrePersonnalisee.getChildren().clear();
        if (!tfLienImageBarrePersonnalisee.getText().equals("")) {
            if (getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                changeCouleurBarrePersonnalisee(couleurBarrePersonnalisee.getHue(), couleurBarrePersonnalisee.getSaturation(), couleurBarrePersonnalisee.getBrightness());
                ivBarrePersonnalisee.setImage(getWiBarrePersonnaliseeCouleur());
                ivBarrePersonnalisee.setPreserveRatio(true);
                ivBarrePersonnalisee.setSmooth(true);
                double hauteur = getWiBarrePersonnaliseeCouleur().getHeight();
                double largeur = getWiBarrePersonnaliseeCouleur().getWidth();
                if (largeur > hauteur) {
                    ivBarrePersonnalisee.setFitWidth(150);
                } else {
                    ivBarrePersonnalisee.setFitHeight(150);
                }
                ImageView ivAfficheBarrePersonnalisee = new ImageView(getWiBarrePersonnaliseeCouleur());
                ivAfficheBarrePersonnalisee.setSmooth(true);

                apAfficheBarrePersonnalisee.getChildren().add(ivAfficheBarrePersonnalisee);
                apAfficheBarrePersonnalisee.setPrefHeight(hauteur);
                apAfficheBarrePersonnalisee.setPrefWidth(largeur);
                apAfficheBarrePersonnalisee.setScaleX(getTailleBarrePersonnalisee() / 100.d);
                apAfficheBarrePersonnalisee.setScaleY(getTailleBarrePersonnalisee() / 100.d);
                String strPositVert = getStrPositionBarrePersonnalisee().split(":")[0];
                String strPositHor = getStrPositionBarrePersonnalisee().split(":")[1];
                double ajoutX = (largeur / 2.d) * getTailleBarrePersonnalisee() / 100.d;
                double ajoutY = (hauteur / 2.d) * getTailleBarrePersonnalisee() / 100.d;
                double LX = 0;
                double LY = 0;
                switch (strPositVert) {
                    case "top":
                        LY = ivVisualisation.getLayoutY() + getOffsetYBarrePersonnalisee() + ajoutY - hauteur / 2.d;
                        break;
                    case "bottom":
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - getOffsetYBarrePersonnalisee() - ajoutY + hauteur / 2.d;
                        break;
                    case "middle":
                        LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight()) / 2.d - getOffsetYBarrePersonnalisee();
                        break;
                }

                switch (strPositHor) {
                    case "right":
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - getOffsetXBarrePersonnalisee() - ajoutX + largeur / 2.d;
                        break;
                    case "left":
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarrePersonnalisee() + ajoutX - largeur / 2.d;
                        break;
                    case "center":
                        LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth()) / 2 + getOffsetXBarrePersonnalisee();
                        break;
                }
                if (isbAfficheVignettes()) {
                    switch (getStrPositionVignettes()) {
                        case "bottom":
                            if (strPositVert.equals("bottom")) {
                                LY -= apVisuVignettes.getPrefHeight();
                                if (!strPositHor.equals("right")) {
                                    LY += 30;
                                }
                            }
                            break;
                        case "left":
                            if (strPositHor.equals("left")) {
                                LX += apVisuVignettes.getPrefWidth();
                            }
                            break;
                        case "right":
                            if (strPositHor.equals("right")) {
                                LX -= apVisuVignettes.getPrefWidth();
                            }
                            break;
                    }
                }
                apAfficheBarrePersonnalisee.setLayoutX(LX);
                apAfficheBarrePersonnalisee.setLayoutY(LY);
                Circle[] cercles = new Circle[50];
                int zone = 1;
                if (getStrInfoBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivInfoBarrePersonnalisee = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/telecommandes/info.png"));
                        ivInfoBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivInfoBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivInfoBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivInfoBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivInfoBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrAideBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivAideBarrePersonnalisee = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/telecommandes/aide.png"));
                        ivAideBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivAideBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivAideBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivAideBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivAideBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrRotationBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivRotationBarrePersonnalisee = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/telecommandes/rotation.png"));
                        ivRotationBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivRotationBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivRotationBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivRotationBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivRotationBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrSourisBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivSourisBarrePersonnalisee = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/telecommandes/souris.png"));
                        ivSourisBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivSourisBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivSourisBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivSourisBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivSourisBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrPleinEcranBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivFSBarrePersonnalisee = new ImageView(new Image("file:" + getStrRepertAppli() + "/theme/telecommandes/fs.png"));
                        ivFSBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivFSBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivFSBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivFSBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivFSBarrePersonnalisee);
                    }
                    zone++;
                }
            }
        }
    }

    /**
     *
     * @param strNomFichier
     * @param zonesBarre
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public int lisFichierShp(String strNomFichier, ZoneTelecommande[] zonesBarre) throws UnsupportedEncodingException, IOException {
        try {
            File fileFichier = new File(strNomFichier);
            if (fileFichier.exists()) {
                int iNombreZonesBarre;
                String strTexte;
                try (BufferedReader brLisFichier = new BufferedReader(new InputStreamReader(
                        new FileInputStream(strNomFichier), "UTF-8"))) {
                    strTexte = "";
                    String strLigneTexte;
                    setiNombrePanoramiquesFichier(0);
                    while ((strLigneTexte = brLisFichier.readLine()) != null) {
                        if (strLigneTexte.contains("Panoramique=>")) {
                            setiNombrePanoramiquesFichier(getiNombrePanoramiquesFichier() + 1);
                        }
                        strTexte += strLigneTexte + "\n";
                    }
                    brLisFichier.close();
                    cbDeplacementsBarrePersonnalisee.setSelected(true);
                    cbDeplacementsBarrePersonnalisee.setDisable(false);
                    setStrDeplacementsBarrePersonnalisee("oui");
                    cbZoomBarrePersonnalisee.setSelected(true);
                    cbZoomBarrePersonnalisee.setDisable(false);
                    setStrZoomBarrePersonnalisee("oui");
                    cbRotationBarrePersonnalisee.setSelected(true);
                    cbRotationBarrePersonnalisee.setDisable(false);
                    setStrRotationBarrePersonnalisee("oui");
                    cbSourisBarrePersonnalisee.setSelected(true);
                    cbSourisBarrePersonnalisee.setDisable(false);
                    setStrSourisBarrePersonnalisee("oui");
                    cbFSBarrePersonnalisee.setSelected(true);
                    cbFSBarrePersonnalisee.setDisable(false);
                    setStrPleinEcranBarrePersonnalisee("oui");
                    setStrInfoBarrePersonnalisee("oui");
                    setStrAideBarrePersonnalisee("oui");
                    tfLien1BarrePersonnalisee.setDisable(true);
                    tfLien2BarrePersonnalisee.setDisable(true);
                    String strLigneComplete = strTexte.replace("[", "|");
                    String strLignes[] = strLigneComplete.split("\\|", 500);
                    String strLigne;
                    String[] strElementsLigne;
                    String[] strTypeElement;
                    iNombreZonesBarre = 0;
                    for (int ikk = 1; ikk < strLignes.length; ikk++) {
                        strLigne = strLignes[ikk];
                        strElementsLigne = strLigne.split(";", 25);
                        strTypeElement = strElementsLigne[0].split(">", 2);
                        strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                        strElementsLigne[0] = strTypeElement[1];
                        if ("area".equals(strTypeElement[0])) {
                            zonesBarre[iNombreZonesBarre] = new ZoneTelecommande();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "").replace("\n", "");
                                String[] strValeur = strElementsLigne[i].split(":", 2);
                                switch (strValeur[0]) {
                                    case "id":
                                        zonesBarre[iNombreZonesBarre].setStrIdZone(strValeur[1]);
                                        break;
                                    case "shape":
                                        zonesBarre[iNombreZonesBarre].setStrTypeZone(strValeur[1]);
                                        break;
                                    case "coords":
                                        zonesBarre[iNombreZonesBarre].setStrCoordonneesZone(strValeur[1]);
                                        break;
                                }
                            }
                            zonesBarre[iNombreZonesBarre].calculeCentre();
                            switch (zonesBarre[iNombreZonesBarre].getStrIdZone()) {
                                case "telUp":
                                case "telDown":
                                case "telRight":
                                case "telLeft":
                                    cbDeplacementsBarrePersonnalisee.setSelected(false);
                                    cbDeplacementsBarrePersonnalisee.setDisable(true);
                                    setStrDeplacementsBarrePersonnalisee("non");
                                    break;
                                case "telZoomMoins":
                                case "telZoomPlus":
                                    cbZoomBarrePersonnalisee.setSelected(false);
                                    cbZoomBarrePersonnalisee.setDisable(true);
                                    setStrZoomBarrePersonnalisee("non");
                                    break;
                                case "telInfo":
                                    setStrInfoBarrePersonnalisee("non");
                                    break;
                                case "telAide":
                                    setStrAideBarrePersonnalisee("non");
                                    break;
                                case "telFS":
                                    cbFSBarrePersonnalisee.setSelected(false);
                                    cbFSBarrePersonnalisee.setDisable(true);
                                    setStrPleinEcranBarrePersonnalisee("non");
                                    break;
                                case "telSouris":
                                    cbSourisBarrePersonnalisee.setSelected(false);
                                    cbSourisBarrePersonnalisee.setDisable(true);
                                    setStrSourisBarrePersonnalisee("non");
                                    break;
                                case "telRotation":
                                    cbRotationBarrePersonnalisee.setSelected(false);
                                    cbRotationBarrePersonnalisee.setDisable(true);
                                    setStrRotationBarrePersonnalisee("non");
                                    break;
                                case "telLien-1":
                                    tfLien1BarrePersonnalisee.setDisable(false);
                                    break;
                                case "telLien-2":
                                    tfLien2BarrePersonnalisee.setDisable(false);
                                    break;
                            }
                            iNombreZonesBarre++;
                        }

                    }
                }
                return iNombreZonesBarre;
            } else {
                return -1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    /**
     *
     * @throws IOException
     */
    public void choixBarrePersonnalisee() throws IOException {
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
            String strNomFichierShp = strNomFichier + ".shp";
            String strNomFichierPng = strNomFichier + ".png";
            File fileFichShp = new File(strNomFichierShp);
            File fileFichPng = new File(strNomFichierPng);
            if (fileFichShp.exists() && fileFichPng.exists()) {
                setiNombreZonesBarrePersonnalisee(lisFichierShp(strNomFichierShp, getZonesBarrePersonnalisee()));
                if (getiNombreZonesBarrePersonnalisee() != -1) {
                    btnEditerBarre.setDisable(false);
                    setStrLienImageBarrePersonnalisee(strNomFichierPng);
                    tfLienImageBarrePersonnalisee.setText(strNomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + strNomFichierPng);
                    afficheBarrePersonnalisee();
                }
            }
        }
    }

    /**
     *
     * @param strNomFichier
     * @throws IOException
     */
    public void chargeBarrePersonnalisee(String strNomFichier) throws IOException {
        if (strNomFichier.length() > 4) {
            strNomFichier = strNomFichier.substring(0, strNomFichier.length() - 4);
            String strNomFichierShp = strNomFichier + ".shp";
            String strNomFichierPng = strNomFichier + ".png";
            File fileFichShp = new File(strNomFichierShp);
            File fileFichPng = new File(strNomFichierPng);
            if (fileFichShp.exists() && fileFichPng.exists()) {
                setiNombreZonesBarrePersonnalisee(lisFichierShp(strNomFichierShp, getZonesBarrePersonnalisee()));
                if (getiNombreZonesBarrePersonnalisee() != -1) {
                    btnEditerBarre.setDisable(false);
                    tfLienImageBarrePersonnalisee.setText(strNomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + strNomFichierPng);
                    afficheBarrePersonnalisee();
                }
            }
        }
    }

    /**
     *
     * @param strPosition
     * @param dX
     * @param dY
     * @param taille
     * @param strStyleBoutons
     * @param strStyleHS
     * @param espacement
     */
    public void afficheBarreClassique(String strPosition, double dX, double dY, double taille, String strStyleBoutons, String strStyleHS, double espacement) {
        String strRepertBoutons = "file:" + strRepertBoutonsPrincipal + File.separator + strStyleBoutons;
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation);
        if (getiNombreImagesFond() > 0) {
            for (int i = 0; i < getiNombreImagesFond(); i++) {
                ImageView ivImageFond = new ImageView(getImagesFond()[i].getImgFond());
                ivImageFond.setFitWidth(getImagesFond()[i].getTailleX());
                ivImageFond.setFitHeight(getImagesFond()[i].getTailleY());
                double posX = 0, posY = 0;
                switch (getImagesFond()[i].getStrPosX()) {
                    case "left":
                        posX = getImagesFond()[i].getOffsetX() + ivVisualisation.getLayoutX();
                        break;
                    case "center":
                        posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - getImagesFond()[i].getTailleX()) / 2 + getImagesFond()[i].getOffsetX();
                        break;
                    case "right":
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getImagesFond()[i].getOffsetX() - getImagesFond()[i].getTailleX();
                        break;
                }
                switch (getImagesFond()[i].getStrPosY()) {
                    case "top":
                        posY = getImagesFond()[i].getOffsetY() + ivVisualisation.getLayoutY();
                        break;
                    case "middle":
                        posY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - getImagesFond()[i].getTailleY()) / 2 + getImagesFond()[i].getOffsetY();
                        break;
                    case "bottom":
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - getImagesFond()[i].getOffsetY() - getImagesFond()[i].getTailleY();
                        break;
                }
                if (isbAfficheVignettes()) {
                    switch (getStrPositionVignettes()) {
                        case "bottom":
                            if (getImagesFond()[i].getStrPosY().equals("bottom")) {
                                posY -= (getTailleImageVignettes() / 2 + 6);
                            }
                            break;
                        case "left":
                            if (getImagesFond()[i].getStrPosX().equals("left")) {
                                posX += getTailleImageVignettes() + 10;
                            }
                            break;
                        case "right":
                            if (getImagesFond()[i].getStrPosX().equals("right")) {
                                posX -= (getTailleImageVignettes() + 10);
                            }
                            break;
                    }
                }
                ivImageFond.setLayoutX(posX);
                ivImageFond.setLayoutY(posY);
                ivImageFond.setOpacity(getImagesFond()[i].getOpacite());
                apVisualisation.getChildren().add(ivImageFond);
            }
        }
        apVisualisation.getChildren().addAll(lblTxtTitre, lblTxtTitre2, imgBoussole, imgAiguille, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, paneFondSuivant, paneFondPrecedent);
        lblTxtTitre.setVisible(isbAfficheTitre());
        chargeBarre(strStyleBoutons, strStyleHS, getStrImageMasque());
        afficheMasque();
        hbbarreBoutons = new HBox(espacement);
        hbbarreBoutons.setId("barreBoutons");
        hbbarreBoutons.setVisible(getStrVisibiliteBarreClassique().equals("oui"));
        hbbarreBoutons.setTranslateZ(1);
        ivHotSpotPanoramique.setFitWidth(30);
        ivHotSpotPanoramique.setPreserveRatio(true);
        ivHotSpotPanoramique.setLayoutX(510);
        ivHotSpotPanoramique.setLayoutY(310);
        ivHotSpotPanoramique.setTranslateZ(1);
        Tooltip tltpHSPano = new Tooltip("Hotspot panoramique");
        tltpHSPano.setStyle(getStrTooltipStyle());
        Tooltip.install(ivHotSpotPanoramique, tltpHSPano);
        ivHotSpotImage.setFitWidth(30);
        ivHotSpotImage.setPreserveRatio(true);
        ivHotSpotImage.setLayoutX(620);
        ivHotSpotImage.setLayoutY(310);
        ivHotSpotImage.setTranslateZ(1);
        Tooltip tltpHSImage = new Tooltip("Hotspot Photo");
        tltpHSImage.setStyle(getStrTooltipStyle());
        Tooltip.install(ivHotSpotImage, tltpHSImage);
        ivHotSpotHTML.setFitWidth(30);
        ivHotSpotHTML.setPreserveRatio(true);
        ivHotSpotHTML.setLayoutX(730);
        ivHotSpotHTML.setLayoutY(310);
        ivHotSpotHTML.setTranslateZ(1);
        Tooltip tltpHSHTML = new Tooltip("Hotspot HTML");
        tltpHSHTML.setStyle(getStrTooltipStyle());
        Tooltip.install(ivHotSpotHTML, tltpHSHTML);
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
        int iNombreBoutons = 11;
        if (getStrDeplacementsBarreClassique().equals("non")) {
            iNombreBoutons -= 4;
        }
        if (getStrZoomBarreClassique().equals("non")) {
            iNombreBoutons -= 2;
        }
        if (getStrOutilsBarreClassique().equals("non")) {
            iNombreBoutons -= 5;
        } else {
            if (getStrPleinEcranBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
            if (getStrRotationBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
            if (getStrSourisBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
        }
        double tailleBarre1 = (double) iNombreBoutons * ((double) taille + espacement);
        hbbarreBoutons.setPrefWidth(tailleBarre1);
        hbbarreBoutons.setPrefHeight((double) taille);
        hbbarreBoutons.setMinWidth(tailleBarre1);
        hbbarreBoutons.setMinHeight((double) taille);
        hbbarreBoutons.setMaxWidth(tailleBarre1);
        hbbarreBoutons.setMaxHeight((double) taille);
        hbDeplacements = new HBox(espacement);
        hbZoom = new HBox(espacement);
        hbOutils = new HBox(espacement);
        hbbarreBoutons.getChildren().addAll(hbDeplacements, hbZoom, hbOutils);
        if (getStrDeplacementsBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbDeplacements);
        }
        if (getStrZoomBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbZoom);
        }
        if (getStrOutilsBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbOutils);
        }
        apVisualisation.getChildren().addAll(hbbarreBoutons, ivHotSpotPanoramique, ivHotSpotImage, ivHotSpotHTML, apFenetreAfficheInfo, lblFenetreURL, apAfficheBarrePersonnalisee, apVisuPlan, apVisuCarte, apAfficheDiapo, ivDiapo, apVisuBoutonVisiteAuto);
        ivHaut.setFitWidth(taille);
        ivHaut.setFitHeight(taille);
        ivBas.setFitWidth(taille);
        ivBas.setFitHeight(taille);
        ivGauche.setFitWidth(taille);
        ivGauche.setFitHeight(taille);
        ivDroite.setFitWidth(taille);
        ivDroite.setFitHeight(taille);
        ivZoomPlus.setFitWidth(taille);
        ivZoomPlus.setFitHeight(taille);
        ivZoomMoins.setFitWidth(taille);
        ivZoomMoins.setFitHeight(taille);
        ivInfo.setFitWidth(taille);
        ivInfo.setFitHeight(taille);
        ivPleinEcran.setFitWidth(taille);
        ivPleinEcran.setFitHeight(taille);
        ivModeSouris.setFitWidth(taille);
        ivModeSouris.setFitHeight(taille);
        ivAide.setFitWidth(taille);
        ivAide.setFitHeight(taille);
        ivAutoRotation.setFitWidth(taille);
        ivAutoRotation.setFitHeight(taille);
        hbDeplacements.getChildren().addAll(ivGauche, ivHaut, ivBas, ivDroite);
        hbZoom.getChildren().addAll(ivZoomPlus, ivZoomMoins);
        hbOutils.getChildren().addAll(ivPleinEcran, ivModeSouris, ivAutoRotation, ivInfo, ivAide);
        if (getStrPleinEcranBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivPleinEcran);
        }
        if (getStrSourisBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivModeSouris);
        }
        if (getStrRotationBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivAutoRotation);
        }
        String strPositVert = strPosition.split(":")[0];
        String strPositHor = strPosition.split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (strPositVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + dY;
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - dY;
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - dY;
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - dX;
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + dX;
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth()) / 2 + dX;
                break;
        }
        if (isbAfficheVignettes()) {
            switch (getStrPositionVignettes()) {
                case "bottom":
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (strPositHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
        ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
        ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
        ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);

    }

    /**
     *
     * @param strRepertoire
     * @return
     */
    private ArrayList<String> strListerStyle(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles();
        for (File fileRepert : fileRepertoires) {
            if (fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                if (!strNomRepert.equals("hotspots")) {
                    strListe.add(strNomRepert);
                }
            }
        }
        return strListe;
    }

    /**
     *
     * @param strRepertoire
     * @return
     */
    private ArrayList<String> strListerHotSpots(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(IMAGE_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                strListe.add(strNomRepert);
            }
        }
        return strListe;
    }

    /**
     *
     * @param strRepertoire
     * @return
     */
    private ArrayList<String> strListerBoussoles(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(PNG_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                if (strNomRepert.contains("rose")) {
                    strListe.add(strNomRepert);
                }
            }
        }
        return strListe;
    }

    /**
     *
     * @param strRepertoire
     * @return
     */
    private ArrayList<String> strListerMasques(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(PNG_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                strListe.add(strNomRepert);
            }
        }
        return strListe;
    }

    /**
     *
     * @return
     */
    public String strGetTemplate() {
        String strContenuFichier = "";
        CoordonneesGeographiques coords;
        if (getCoordCentreCarte() != null) {
            coords = getCoordCentreCarte();
        } else {
            coords = navigateurCarteOL.recupereCoordonnees();
        }
        strContenuFichier
                += "styleBarre=" + getStyleBarreClassique() + "\n"
                + "couleurBoutons=" + couleurBarreClassique.toString().substring(2, 8) + "\n"
                + "couleurHotspots=" + couleurHotspots.toString().substring(2, 8) + "\n"
                + "couleurHotspotsPhoto=" + couleurHotspotsPhoto.toString().substring(2, 8) + "\n"
                + "couleurHotspotsHTML=" + couleurHotspotsHTML.toString().substring(2, 8) + "\n"
                + "tailleHotspots=" + getiTailleHotspotsPanoramique() + "\n"
                + "tailleHotspotsPhoto=" + getiTailleHotspotsImage() + "\n"
                + "tailleHotspotsHTML=" + getiTailleHotspotsHTML() + "\n"
                + "couleurMasque=" + couleurMasque.toString().substring(2, 8) + "\n"
                + "styleHotspots=" + getStrStyleHotSpots() + "\n"
                + "styleHotspotImages=" + getStrStyleHotSpotImages() + "\n"
                + "styleHotspotHTML=" + getStrStyleHotSpotHTML() + "\n"
                + "position=" + getStrPositionBarreClassique() + "\n"
                + "dX=" + Math.round(getOffsetXBarreClassique()) + "\n"
                + "dY=" + Math.round(getOffsetYBarreClassique()) + "\n"
                + "visible=" + getStrVisibiliteBarreClassique() + "\n"
                + "suivantPrecedent=" + isbSuivantPrecedent() + "\n"
                + "deplacement=" + getStrDeplacementsBarreClassique() + "\n"
                + "zoom=" + getStrZoomBarreClassique() + "\n"
                + "outils=" + getStrOutilsBarreClassique() + "\n"
                + "rotation=" + getStrRotationBarreClassique() + "\n"
                + "FS=" + getStrPleinEcranBarreClassique() + "\n"
                + "souris=" + getStrSourisBarreClassique() + "\n"
                + "espacementBoutons=" + getEspacementBarreClassique() + "\n"
                + "bCouleurOrigineBarrePersonnalisee=" + isbCouleurOrigineBarrePersonnalisee() + "\n"
                + "nombreZonesBarrePersonnalisee=" + getiNombreZonesBarrePersonnalisee() + "\n"
                + "offsetXBarrePersonnalisee=" + getOffsetXBarrePersonnalisee() + "\n"
                + "offsetYBarrePersonnalisee=" + getOffsetYBarrePersonnalisee() + "\n"
                + "tailleBarrePersonnalisee=" + getTailleBarrePersonnalisee() + "\n"
                + "tailleIconesBarrePersonnalisee=" + getTailleIconesBarrePersonnalisee() + "\n"
                + "strPositionBarrePersonnalisee=" + getStrPositionBarrePersonnalisee() + "\n"
                + "strDeplacementsBarrePersonnalisee=" + getStrDeplacementsBarrePersonnalisee() + "\n"
                + "strZoomBarrePersonnalisee=" + getStrZoomBarrePersonnalisee() + "\n"
                + "strInfoBarrePersonnalisee=" + getStrInfoBarrePersonnalisee() + "\n"
                + "strAideBarrePersonnalisee=" + getStrAideBarrePersonnalisee() + "\n"
                + "strRotationBarrePersonnalisee=" + getStrRotationBarrePersonnalisee() + "\n"
                + "strPleinEcranBarrePersonnalisee=" + getStrPleinEcranBarrePersonnalisee() + "\n"
                + "strSourisBarrePersonnalisee=" + getStrSourisBarrePersonnalisee() + "\n"
                + "strVisibiliteBarrePersonnalisee=" + getStrVisibiliteBarrePersonnalisee() + "\n"
                + "strLienImageBarrePersonnalisee=" + getStrLienImageBarrePersonnalisee() + "\n"
                + "strLien1BarrePersonnalisee=" + getStrLien1BarrePersonnalisee() + "\n"
                + "strLien2BarrePersonnalisee=" + getStrLien1BarrePersonnalisee() + "\n"
                + "couleurBarrePersonnalisee=" + couleurBarrePersonnalisee + "\n"
                + "afficheTitre=" + isbAfficheTitre() + "\n"
                + "afficheTitreVisite=" + isbTitreVisite() + "\n"
                + "afficheTitrePanoramique=" + isbTitrePanoramique() + "\n"
                + "titreAdapte=" + isbTitreAdapte() + "\n"
                + "titrePosition=" + getStrTitrePosition() + "\n"
                + "titreDecalage=" + Math.round(getTitreDecalage() * 100.d) / 100.d + "\n"
                + "titrePolice=" + getStrTitrePoliceNom() + "\n"
                + "titrePoliceTaille=" + getStrTitrePoliceTaille() + "\n"
                + "titreOpacite=" + Math.round(getTitreOpacite() * 100.d) / 100.d + "\n"
                + "titreTaille=" + getTitreTaille() + "\n"
                + "titreCouleur=" + getStrCouleurTitre() + "\n"
                + "titreFondCouleur=" + getStrCouleurFondTitre() + "\n"
                + "bFenetreInfoPersonnalise=" + isbFenetreInfoPersonnalise() + "\n"
                + "bFenetreAidePersonnalise=" + isbFenetreAidePersonnalise() + "\n"
                + "strFenetreInfoImage =" + getStrFenetreInfoImage() + "\n"
                + "strFenetreAideImage=" + getStrFenetreAideImage() + "\n"
                + "fenetreInfoTaille=" + getFenetreInfoTaille() + "\n"
                + "fenetreAideTaille=" + getFenetreAideTaille() + "\n"
                + "fenetreInfoPosX=" + getFenetreInfoPosX() + "\n"
                + "fenetreInfoPosY=" + getFenetreInfoPosY() + "\n"
                + "fenetreAidePosX=" + getFenetreAidePosX() + "\n"
                + "fenetreAidePosY=" + getFenetreAidePosY() + "\n"
                + "fenetreInfoOpacite=" + getFenetreInfoOpacite() + "\n"
                + "fenetreAideOpacite=" + getFenetreAideOpacite() + "\n"
                + "strFenetreURL=" + getStrFenetreURL() + "\n"
                + "strFenetreTexteURL=" + getStrFenetreTexteURL() + "\n"
                + "strFenetreURLInfobulle=" + getStrFenetreURLInfobulle() + "\n"
                + "strFenetreURLCouleur=" + getStrFenetreURLCouleur() + "\n"
                + "strFenetrePolice=" + getStrFenetrePolice() + "\n"
                + "fenetrePoliceTaille=" + getFenetrePoliceTaille() + "\n"
                + "fenetreURLPosX=" + getFenetreURLPosX() + "\n"
                + "fenetreURLPosY=" + getFenetreURLPosY() + "\n"
                + "strFenetreCouleurFond=" + getStrFenetreCouleurFond() + "\n"
                + "fenetreOpaciteFond=" + getFenetreOpaciteFond() + "\n"
                + "diaporamaOpacite=" + Math.round(getDiaporamaOpacite() * 100.d) / 100.d + "\n"
                + "diaporamaCouleur=" + getStrCouleurDiaporama() + "\n"
                + "afficheBoussole=" + isbAfficheBoussole() + "\n"
                + "imageBoussole=" + getStrImageBoussole() + "\n"
                + "tailleBoussole=" + Math.round(getTailleBoussole() * 10.d) / 10.d + "\n"
                + "positionBoussole=" + getStrPositionBoussole() + "\n"
                + "dXBoussole=" + Math.round(getOffsetXBoussole()) + "\n"
                + "dYBoussole=" + Math.round(getOffsetYBoussole()) + "\n"
                + "opaciteBoussole=" + Math.round(getOpaciteBoussole() * 100.d) / 100.d + "\n"
                + "aiguilleMobile=" + isbAiguilleMobileBoussole() + "\n"
                + "afficheMasque=" + isbAfficheMasque() + "\n"
                + "imageMasque=" + getStrImageMasque() + "\n"
                + "tailleMasque=" + Math.round(getTailleMasque() * 10.d) / 10.d + "\n"
                + "positionMasque=" + getStrPositionMasque() + "\n"
                + "dXMasque=" + Math.round(getdXMasque()) + "\n"
                + "dYMasque=" + Math.round(getdYMasque()) + "\n"
                + "opaciteMasque=" + Math.round(getOpaciteMasque() * 100.d) / 100.d + "\n"
                + "masqueNavigation=" + isbMasqueNavigation() + "\n"
                + "masqueBoussole=" + isbMasqueBoussole() + "\n"
                + "masqueTitre=" + isbMasqueTitre() + "\n"
                + "masquePlan=" + isbMasquePlan() + "\n"
                + "masqueReseaux=" + isbMasqueReseaux() + "\n"
                + "masqueVignettes=" + isbMasqueVignettes() + "\n"
                + "masqueCombo=" + isbMasqueCombo() + "\n"
                + "masqueSuivPrec=" + isbMasqueSuivPrec() + "\n"
                + "masqueHotspots=" + isbMasqueHotspots() + "\n"
                + "afficheReseauxSociaux=" + isbAfficheReseauxSociaux() + "\n"
                + "tailleReseauxSociaux=" + Math.round(getTailleReseauxSociaux() * 10.d) / 10.d + "\n"
                + "positionReseauxSociaux=" + getStrPositionReseauxSociaux() + "\n"
                + "dXReseauxSociaux=" + Math.round(getdXReseauxSociaux()) + "\n"
                + "dYReseauxSociaux=" + Math.round(getdYReseauxSociaux()) + "\n"
                + "opaciteReseauxSociaux=" + Math.round(getOpaciteReseauxSociaux() * 100.d) / 100.d + "\n"
                + "masqueTwitter=" + isbReseauxSociauxTwitter() + "\n"
                + "masqueGoogle=" + isbReseauxSociauxGoogle() + "\n"
                + "masqueFacebook=" + isbReseauxSociauxFacebook() + "\n"
                + "masqueEmail=" + isbReseauxSociauxEmail() + "\n"
                + "afficheVignettes=" + isbAfficheVignettes() + "\n"
                + "positionVignettes=" + getStrPositionVignettes() + "\n"
                + "opaciteVignettes=" + Math.round(getOpaciteVignettes() * 100.d) / 100.d + "\n"
                + "tailleImageVignettes=" + Math.round(getTailleImageVignettes()) + "\n"
                + "couleurFondVignettes=" + getStrCouleurFondVignettes() + "\n"
                + "couleurTexteVignettes=" + getStrCouleurTexteVignettes() + "\n"
                + "replieDemarrageVignettes=" + isbReplieDemarrageVignettes() + "\n"
                + "bAfficheComboMenu=" + isbAfficheComboMenu() + "\n"
                + "bAfficheComboMenuImages=" + isbAfficheComboMenuImages() + "\n"
                + "positionXComboMenu=" + getStrPositionXComboMenu() + "\n"
                + "positionYComboMenu=" + getStrPositionYComboMenu() + "\n"
                + "offsetXComboMenu=" + getOffsetXComboMenu() + "\n"
                + "offsetYComboMenu=" + getOffsetYComboMenu() + "\n"
                + "bAfficheBoutonVisiteAuto=" + isbAfficheBoutonVisiteAuto() + "\n"
                + "tailleBoutonVisiteAuto=" + getTailleBoutonVisiteAuto() + "\n"
                + "positionXBoutonVisiteAuto=" + getStrPositionXBoutonVisiteAuto() + "\n"
                + "positionYBoutonVisiteAuto=" + getStrPositionYBoutonVisiteAuto() + "\n"
                + "offsetXBoutonVisiteAuto=" + getOffsetXBoutonVisiteAuto() + "\n"
                + "offsetYBoutonVisiteAuto=" + getOffsetYBoutonVisiteAuto() + "\n"
                + "affichePlan=" + isbAffichePlan() + "\n"
                + "positionPlan=" + getStrPositionPlan() + "\n"
                + "opacitePlan=" + Math.round(getOpacitePlan() * 100.d) / 100.d + "\n"
                + "largeurPlan=" + Math.round(getLargeurPlan()) + "\n"
                + "couleurFondPlan=" + getStrCouleurFondPlan() + "\n"
                + "couleurTextePlan=" + getStrCouleurTextePlan() + "\n"
                + "afficheRadar=" + isbAfficheRadar() + "\n"
                + "opaciteRadar=" + Math.round(getOpaciteRadar() * 100.d) / 100.d + "\n"
                + "tailleRadar=" + Math.round(getTailleRadar()) + "\n"
                + "couleurFondRadar=" + getStrCouleurFondRadar() + "\n"
                + "couleurLigneRadar=" + getStrCouleurLigneRadar() + "\n"
                + "replieDemarragePlan=" + isbReplieDemarragePlan() + "\n"
                + "afficheCarte=" + isbAfficheCarte() + "\n"
                + "nomLayer=" + getStrNomLayers() + "\n"
                + "centreLongitude=" + coords.getLongitude() + "\n"
                + "centreLatitude=" + coords.getLatitude() + "\n"
                + "positionCarte=" + getStrPositionCarte() + "\n"
                + "opaciteCarte=" + Math.round(getOpaciteCarte() * 100.d) / 100.d + "\n"
                + "largeurCarte=" + Math.round(getLargeurCarte()) + "\n"
                + "hauteurCarte=" + Math.round(getHauteurCarte()) + "\n"
                + "zoomCarte=" + getiFacteurZoomCarte() + "\n"
                + "couleurFondCarte=" + getStrCouleurFondCarte() + "\n"
                + "couleurTexteCarte=" + getStrCouleurTexteCarte() + "\n"
                + "afficheRadarCarte=" + isbAfficheRadarCarte() + "\n"
                + "opaciteRadarCarte=" + Math.round(getOpaciteRadarCarte() * 100.d) / 100.d + "\n"
                + "tailleRadarCarte=" + Math.round(getTailleRadarCarte()) + "\n"
                + "couleurFondRadarCarte=" + getStrCouleurFondRadarCarte() + "\n"
                + "couleurLigneRadarCarte=" + getStrCouleurLigneRadarCarte() + "\n"
                + "replieDemarrageCarte=" + isbReplieDemarrageCarte() + "\n"
                + "afficheMenuContextuel=" + isbAfficheMenuContextuel() + "\n"
                + "affichePrecSuivMC=" + isbAffichePrecSuivMC() + "\n"
                + "affichePlaneteNormalMC=" + isbAffichePlanetNormalMC() + "\n"
                + "affichePersMC1=" + isbAffichePersMC1() + "\n"
                + "affichePersMC2=" + isbAffichePersMC2() + "\n"
                + "txtPersLib1=" + getStrPersLib1() + "\n"
                + "txtPersLib2=" + getStrPersLib2() + "\n"
                + "txtPersURL1=" + getStrPersURL1() + "\n"
                + "txtPersURL2=" + getStrPersURL2() + "\n"
                + "nombreImagesFond=" + getiNombreImagesFond() + "\n";
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            strContenuFichier += "<";
            strContenuFichier += "image=" + getImagesFond()[i].getStrFichierImage() + "#";
            strContenuFichier += "posX=" + getImagesFond()[i].getStrPosX() + "#";
            strContenuFichier += "posY=" + getImagesFond()[i].getStrPosY() + "#";
            strContenuFichier += "offsetX=" + getImagesFond()[i].getOffsetX() + "#";
            strContenuFichier += "offsetY=" + getImagesFond()[i].getOffsetY() + "#";
            strContenuFichier += "tailleX=" + getImagesFond()[i].getTailleX() + "#";
            strContenuFichier += "tailleY=" + getImagesFond()[i].getTailleY() + "#";
            strContenuFichier += "opacite=" + getImagesFond()[i].getOpacite() + "#";
            strContenuFichier += "masquable=" + getImagesFond()[i].isMasquable() + "#";
            strContenuFichier += "url=" + getImagesFond()[i].getStrUrl() + "#";
            strContenuFichier += "infobulle=" + getImagesFond()[i].getStrInfobulle() + "#";
            strContenuFichier += ">\n";
        }

        return strContenuFichier;
    }

    /**
     *
     * @param strTemplate
     */
    public void setTemplate(List<String> strTemplate) {
        setbAfficheBoussole(false);
        setbAfficheMasque(false);
        setbAfficheVignettes(false);
        setbAfficheReseauxSociaux(false);
        setiNombreImagesFond(0);
        File fileRepertoirePlan;

        for (String strChaine : strTemplate) {
            if (strChaine.split("image=").length > 1) {
                getImagesFond()[getiNombreImagesFond()] = new ImageFond();

                strChaine = strChaine.replace("<", "");
                strChaine = strChaine.replace(">", "");
                String[] strElements = strChaine.split("#");
                for (String strTexte1 : strElements) {
                    String strVariable = strTexte1.split("=")[0];
                    String strValeur = "";
                    if (strTexte1.split("=").length > 1) {
                        strValeur = strTexte1.split("=")[1];
                    }
                    switch (strVariable) {
                        case "image":
                            getImagesFond()[getiNombreImagesFond()].setStrFichierImage(strValeur);
                            getImagesFond()[getiNombreImagesFond()].setImgFond(new Image("file:" + strValeur));
                            fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getImagesFond()[getiNombreImagesFond()].getStrFichierImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "posX":
                            getImagesFond()[getiNombreImagesFond()].setStrPosX(strValeur);
                            break;
                        case "posY":
                            getImagesFond()[getiNombreImagesFond()].setStrPosY(strValeur);
                            break;
                        case "url":
                            getImagesFond()[getiNombreImagesFond()].setStrUrl(strValeur);
                            break;
                        case "infobulle":
                            getImagesFond()[getiNombreImagesFond()].setStrInfobulle(strValeur);
                            break;
                        case "offsetX":
                            getImagesFond()[getiNombreImagesFond()].setOffsetX(Double.parseDouble(strValeur));
                            break;
                        case "offsetY":
                            getImagesFond()[getiNombreImagesFond()].setOffsetY(Double.parseDouble(strValeur));
                            break;
                        case "opacite":
                            getImagesFond()[getiNombreImagesFond()].setOpacite(Double.parseDouble(strValeur));
                            break;
                        case "masquable":
                            getImagesFond()[getiNombreImagesFond()].setMasquable(strValeur.equals("true"));
                            break;
                        case "tailleX":
                            getImagesFond()[getiNombreImagesFond()].setTailleX(Integer.parseInt(strValeur));
                            break;
                        case "tailleY":
                            getImagesFond()[getiNombreImagesFond()].setTailleY(Integer.parseInt(strValeur));
                            break;
                    }

                }
                setiNombreImagesFond(getiNombreImagesFond() + 1);
            } else {
                String strVariable = strChaine.split("=")[0];
                String strValeur = "";
                if (strChaine.split("=").length > 1) {
                    strValeur = strChaine.split("=")[1];
                }
                switch (strVariable) {
                    case "couleurTheme":
                        break;
                    case "couleurBoutons":
                        couleurBarreClassique = Color.web(strValeur);
                        break;
                    case "couleurHotspots":
                        couleurHotspots = Color.web(strValeur);
                        break;
                    case "couleurHotspotsPhoto":
                        couleurHotspotsPhoto = Color.web(strValeur);
                        break;
                    case "couleurHotspotsHTML":
                        couleurHotspotsHTML = Color.web(strValeur);
                        break;
                    case "tailleHotspots":
                        setiTailleHotspotsPanoramique(Integer.parseInt(strValeur));
                        break;
                    case "tailleHotspotsPhoto":
                        setiTailleHotspotsImage(Integer.parseInt(strValeur));
                        break;
                    case "tailleHotspotsHTML":
                        setiTailleHotspotsHTML(Integer.parseInt(strValeur));
                        break;
                    case "couleurMasque":
                        couleurMasque = Color.web(strValeur);
                        break;

                    case "styleBarre":
                        setStyleBarreClassique(strValeur);
                        break;
                    case "suivantPrecedent":
                        setbSuivantPrecedent(strValeur.equals("true"));
                        break;

                    case "styleHotspots":
                        String strExtension = strValeur.substring(strValeur.length() - 3, strValeur.length());
                        strTypeHS = strExtension.toLowerCase();
                        strNomfichierHS = strValeur;
                        setStrStyleHotSpots(strValeur);
                        break;
                    case "styleHotspotImages":
                        String strExtension1 = strValeur.substring(strValeur.length() - 3, strValeur.length());
                        strTypeHSImage = strExtension1.toLowerCase();
                        strNomfichierHSImage = strValeur;
                        setStrStyleHotSpotImages(strValeur);
                        break;
                    case "styleHotspotHTML":
                        String strExtension2 = strValeur.substring(strValeur.length() - 3, strValeur.length());
                        strTypeHSHTML = strExtension2.toLowerCase();
                        strNomfichierHSHTML = strValeur;
                        setStrStyleHotSpotHTML(strValeur);
                        break;
                    case "position":
                        setStrPositionBarreClassique(strValeur);

                        break;
                    case "dX":
                        setOffsetXBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "dY":
                        setOffsetYBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "visible":
                        setStrVisibiliteBarreClassique(strValeur);
                        break;
                    case "deplacement":
                        setStrDeplacementsBarreClassique(strValeur);
                        break;
                    case "zoom":
                        setStrZoomBarreClassique(strValeur);
                        break;
                    case "outils":
                        setStrOutilsBarreClassique(strValeur);
                        break;
                    case "rotation":
                        setStrRotationBarreClassique(strValeur);
                        break;
                    case "FS":
                        setStrPleinEcranBarreClassique(strValeur);
                        break;
                    case "souris":
                        setStrSourisBarreClassique(strValeur);
                        break;
                    case "espacementBoutons":
                        setEspacementBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "bCouleurOrigineBarrePersonnalisee":
                        setbCouleurOrigineBarrePersonnalisee(strValeur.equals("true"));
                        break;
                    case "nombreZonesBarrePersonnalisee":
                        setiNombreZonesBarrePersonnalisee(Integer.parseInt(strValeur));
                        break;
                    case "offsetXBarrePersonnalisee":
                        setOffsetXBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "offsetYBarrePersonnalisee":
                        setOffsetYBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "tailleBarrePersonnalisee":
                        setTailleBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "tailleIconesBarrePersonnalisee":
                        setTailleIconesBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "strPositionBarrePersonnalisee":
                        setStrPositionBarrePersonnalisee(strValeur);
                        break;
                    case "strDeplacementsBarrePersonnalisee":
                        setStrDeplacementsBarrePersonnalisee(strValeur);
                        break;
                    case "strZoomBarrePersonnalisee":
                        setStrZoomBarrePersonnalisee(strValeur);
                        break;
                    case "strInfoBarrePersonnalisee":
                        setStrInfoBarrePersonnalisee(strValeur);
                        break;
                    case "strAideBarrePersonnalisee":
                        setStrAideBarrePersonnalisee(strValeur);
                        break;
                    case "strRotationBarrePersonnalisee":
                        setStrRotationBarrePersonnalisee(strValeur);
                        break;
                    case "strPleinEcranBarrePersonnalisee":
                        setStrPleinEcranBarrePersonnalisee(strValeur);
                        break;
                    case "strSourisBarrePersonnalisee":
                        setStrSourisBarrePersonnalisee(strValeur);
                        break;
                    case "strVisibiliteBarrePersonnalisee":
                        setStrVisibiliteBarrePersonnalisee(strValeur);
                        break;
                    case "strLienImageBarrePersonnalisee":
                        setStrLienImageBarrePersonnalisee(strValeur);
                        break;
                    case "strLien1BarrePersonnalisee":
                        setStrLien1BarrePersonnalisee(strValeur);
                        break;
                    case "strLien2BarrePersonnalisee":
                        setStrLien2BarrePersonnalisee(strValeur);
                        break;
                    case "couleurBarrePersonnalisee":
                        couleurBarrePersonnalisee = Color.web(strValeur);
                    case "afficheTitre":
                        setbAfficheTitre(strValeur.equals("true"));
                        break;
                    case "afficheTitreVisite":
                        setbTitreVisite(strValeur.equals("true"));
                        break;
                    case "afficheTitrePanoramique":
                        setbTitrePanoramique(strValeur.equals("true"));
                        break;
                    case "titreAdapte":
                        setbTitreAdapte(strValeur.equals("true"));
                        break;
                    case "titrePosition":
                        setStrTitrePosition(strValeur);
                        break;
                    case "titreDecalage":
                        setTitreDecalage(Double.parseDouble(strValeur));
                        break;

                    case "titrePolice":
                        setStrTitrePoliceNom(strValeur);
                        break;
                    case "titrePoliceTaille":
                        setStrTitrePoliceTaille(strValeur);
                        break;
                    case "titreOpacite":
                        setTitreOpacite(Double.parseDouble(strValeur));
                        break;
                    case "titreTaille":
                        setTitreTaille(Double.parseDouble(strValeur));
                        break;
                    case "titreCouleur":
                        setStrCouleurTitre(strValeur);
                        break;
                    case "titreFondCouleur":
                        setStrCouleurFondTitre(strValeur);
                        break;
                    case "bFenetreInfoPersonnalise":
                        setbFenetreInfoPersonnalise(strValeur.equals("true"));
                        break;
                    case "bFenetreAidePersonnalise":
                        setbFenetreAidePersonnalise(strValeur.equals("true"));
                        break;
                    case "strFenetreInfoImage ":
                        setStrFenetreInfoImage(strValeur);
                        if (!strFenetreInfoImage.equals("")) {
                            fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getStrFenetreInfoImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    case "strFenetreAideImage":
                        setStrFenetreAideImage(strValeur);
                        if (!strFenetreAideImage.equals("")) {
                            fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getStrFenetreAideImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "fenetreInfoTaille":
                        setFenetreInfoTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAideTaille":
                        setFenetreAideTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoPosX":
                        setFenetreInfoPosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoPosY":
                        setFenetreInfoPosY(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAidePosX":
                        setFenetreAidePosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAidePosY":
                        setFenetreAidePosY(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoOpacite":
                        setFenetreInfoOpacite(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAideOpacite":
                        setFenetreAideOpacite(Double.parseDouble(strValeur));
                        break;
                    case "strFenetreURL":
                        setStrFenetreURL(strValeur);
                        break;
                    case "strFenetreTexteURL":
                        setStrFenetreTexteURL(strValeur);
                        break;
                    case "strFenetreURLInfobulle":
                        setStrFenetreURLInfobulle(strValeur);
                        break;
                    case "strFenetreURLCouleur":
                        setStrFenetreURLCouleur(strValeur);
                        break;
                    case "strFenetrePolice":
                        setStrFenetrePolice(strValeur);
                        break;
                    case "fenetrePoliceTaille":
                        setFenetrePoliceTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreURLPosX":
                        setFenetreURLPosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreURLPosY":
                        setFenetreURLPosY(Double.parseDouble(strValeur));
                        break;
                    case "strFenetreCouleurFond":
                        setStrFenetreCouleurFond(strValeur);
                        break;
                    case "fenetreOpaciteFond":
                        setFenetreOpaciteFond(Double.parseDouble(strValeur));
                        break;
                    case "diaporamaOpacite":
                        setDiaporamaOpacite(Double.parseDouble(strValeur));
                        break;
                    case "diaporamaCouleur":
                        setStrCouleurDiaporama(strValeur);
                        break;
                    case "afficheBoussole":
                        setbAfficheBoussole(strValeur.equals("true"));
                        break;
                    case "imageBoussole":
                        setStrImageBoussole(strValeur);
                        break;
                    case "tailleBoussole":
                        setTailleBoussole(Double.parseDouble(strValeur));
                        break;
                    case "positionBoussole":
                        setStrPositionBoussole(strValeur);
                        break;
                    case "dXBoussole":
                        setOffsetXBoussole(Double.parseDouble(strValeur));
                        break;
                    case "dYBoussole":
                        setOffsetYBoussole(Double.parseDouble(strValeur));
                        break;
                    case "opaciteBoussole":
                        setOpaciteBoussole(Double.parseDouble(strValeur));
                        break;
                    case "aiguilleMobile":
                        setbAiguilleMobileBoussole(strValeur.equals("true"));
                        break;
                    case "afficheMasque":
                        setbAfficheMasque(strValeur.equals("true"));
                        break;
                    case "imageMasque":
                        setStrImageMasque(strValeur);
                        break;
                    case "tailleMasque":
                        setTailleMasque(Double.parseDouble(strValeur));
                        break;
                    case "positionMasque":
                        setStrPositionMasque(strValeur);
                        break;
                    case "dXMasque":
                        setdXMasque(Double.parseDouble(strValeur));
                        break;
                    case "dYMasque":
                        setdYMasque(Double.parseDouble(strValeur));
                        break;
                    case "opaciteMasque":
                        setOpaciteMasque(Double.parseDouble(strValeur));
                        break;
                    case "masqueNavigation":
                        setbMasqueNavigation(strValeur.equals("true"));
                        break;
                    case "masqueBoussole":
                        setbMasqueBoussole(strValeur.equals("true"));
                        break;
                    case "masqueTitre":
                        setbMasqueTitre(strValeur.equals("true"));
                        break;
                    case "masquePlan":
                        setbMasquePlan(strValeur.equals("true"));
                        break;
                    case "masqueReseaux":
                        setbMasqueReseaux(strValeur.equals("true"));
                        break;
                    case "masqueVignettes":
                        setbMasqueVignettes(strValeur.equals("true"));
                        break;
                    case "masqueCombo":
                        setbMasqueCombo(strValeur.equals("true"));
                        break;
                    case "masqueSuivPrec":
                        setbMasqueSuivPrec(strValeur.equals("true"));
                        break;
                    case "masqueHotspots":
                        setbMasqueHotspots(strValeur.equals("true"));
                        break;
                    case "afficheReseauxSociaux":
                        setbAfficheReseauxSociaux(strValeur.equals("true"));
                        break;
                    case "tailleReseauxSociaux":
                        setTailleReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "positionReseauxSociaux":
                        setStrPositionReseauxSociaux(strValeur);
                        break;
                    case "dXReseauxSociaux":
                        setdXReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "dYReseauxSociaux":
                        setdYReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "opaciteReseauxSociaux":
                        setOpaciteReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "masqueTwitter":
                        setbReseauxSociauxTwitter(strValeur.equals("true"));
                        break;
                    case "masqueGoogle":
                        setbReseauxSociauxGoogle(strValeur.equals("true"));
                        break;
                    case "masqueFacebook":
                        setbReseauxSociauxFacebook(strValeur.equals("true"));
                        break;
                    case "masqueEmail":
                        setbReseauxSociauxEmail(strValeur.equals("true"));
                        break;
                    case "afficheVignettes":
                        setbAfficheVignettes(strValeur.equals("true"));
                        break;
                    case "positionVignettes":
                        setStrPositionVignettes(strValeur);
                        break;
                    case "opaciteVignettes":
                        setOpaciteVignettes(Double.parseDouble(strValeur));
                        break;
                    case "tailleImageVignettes":
                        setTailleImageVignettes(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondVignettes":
                        setStrCouleurFondVignettes(strValeur);
                        break;
                    case "couleurTexteVignettes":
                        setStrCouleurTexteVignettes(strValeur);
                        break;
                    case "replieDemarrageVignettes":
                        setbReplieDemarrageVignettes(strValeur.equals("true"));
                        break;

                    case "bAfficheComboMenu":
                        setbAfficheComboMenu(strValeur.equals("true"));
                        break;
                    case "bAfficheComboMenuImages":
                        setbAfficheComboMenuImages(strValeur.equals("true"));
                        break;
                    case "positionXComboMenu":
                        setStrPositionXComboMenu(strValeur);
                        break;
                    case "positionYComboMenu":
                        setStrPositionYComboMenu(strValeur);
                        break;
                    case "offsetXComboMenu":
                        setOffsetXComboMenu(Double.parseDouble(strValeur));
                        break;
                    case "offsetYComboMenu":
                        setOffsetYComboMenu(Double.parseDouble(strValeur));
                        break;
                    case "bAfficheBoutonVisiteAuto":
                        setbAfficheBoutonVisiteAuto(strValeur.equals("true"));
                        break;
                    case "positionXBoutonVisiteAuto":
                        setStrPositionXBoutonVisiteAuto(strValeur);
                        break;
                    case "tailleBoutonVisiteAuto":
                        setTailleBoutonVisiteAuto(Double.parseDouble(strValeur));
                        break;
                    case "positionYBoutonVisiteAuto":
                        setStrPositionYBoutonVisiteAuto(strValeur);
                        break;
                    case "offsetXBoutonVisiteAuto":
                        setOffsetXBoutonVisiteAuto(Double.parseDouble(strValeur));
                        break;
                    case "offsetYBoutonVisiteAuto":
                        setOffsetYBoutonVisiteAuto(Double.parseDouble(strValeur));
                        break;
                    case "affichePlan":
                        setbAffichePlan(strValeur.equals("true"));
                        break;
                    case "positionPlan":
                        setStrPositionPlan(strValeur);
                        break;
                    case "opacitePlan":
                        setOpacitePlan(Double.parseDouble(strValeur));
                        break;
                    case "largeurPlan":
                        setLargeurPlan(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondPlan":
                        setStrCouleurFondPlan(strValeur);
                        setCouleurFondPlan(Color.valueOf(getStrCouleurFondPlan()));
                        break;
                    case "couleurTextePlan":
                        setStrCouleurTextePlan(strValeur);
                        setCouleurTextePlan(Color.valueOf(getStrCouleurTextePlan()));
                        break;
                    case "afficheRadar":
                        setbAfficheRadar(strValeur.equals("true"));
                        break;
                    case "opaciteRadar":
                        setOpaciteRadar(Double.parseDouble(strValeur));
                        break;
                    case "tailleRadar":
                        setTailleRadar(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondRadar":
                        setStrCouleurFondRadar(strValeur);
                        setCouleurFondRadar(Color.valueOf(getStrCouleurFondRadar()));
                        break;
                    case "couleurLigneRadar":
                        setStrCouleurLigneRadar(strValeur);
                        setCouleurLigneRadar(Color.valueOf(getStrCouleurLigneRadar()));
                        break;
                    case "replieDemarragePlan":
                        setbReplieDemarragePlan(strValeur.equals("true"));
                        break;
                    case "afficheCarte":
                        setbAfficheCarte(strValeur.equals("true"));
                        break;
                    case "nomLayer":
                        setStrNomLayers(strValeur);
                        break;
                    case "centreLongitude":
                        if (!isbTemplate()) {
                            setCoordCentreCarte(new CoordonneesGeographiques());
                            getCoordCentreCarte().setLongitude(Double.parseDouble(strValeur));
                        }
                        break;
                    case "centreLatitude":
                        if (!isbTemplate()) {
                            getCoordCentreCarte().setLatitude(Double.parseDouble(strValeur));
                        }
                        break;
                    case "positionCarte":
                        setStrPositionCarte(strValeur);
                        break;
                    case "opaciteCarte":
                        setOpaciteCarte(Double.parseDouble(strValeur));
                        break;
                    case "largeurCarte":
                        setLargeurCarte(Double.parseDouble(strValeur));
                        break;
                    case "hauteurCarte":
                        setHauteurCarte(Double.parseDouble(strValeur));
                        break;
                    case "zoomCarte":
                        if (!isbTemplate()) {
                            setiFacteurZoomCarte(Integer.parseInt(strValeur) + 1);
                        }
                        break;
                    case "couleurFondCarte":
                        setStrCouleurFondCarte(strValeur);
                        setCouleurFondCarte(Color.valueOf(strValeur));
                        break;
                    case "couleurTexteCarte":
                        setStrCouleurTexteCarte(strValeur);
                        setCouleurTexteCarte(Color.valueOf(strValeur));
                        break;
                    case "afficheRadarCarte":
                        setbAfficheRadarCarte(strValeur.equals("true"));
                        break;
                    case "opaciteRadarCarte":
                        setOpaciteRadarCarte(Double.parseDouble(strValeur));
                        break;
                    case "tailleRadarCarte":
                        setTailleRadarCarte(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondRadarCarte":
                        setStrCouleurFondRadarCarte(strValeur);
                        setCouleurFondRadarCarte(Color.valueOf(strValeur));
                        break;
                    case "couleurLigneRadarCarte":
                        setStrCouleurLigneRadarCarte(strValeur);
                        setCouleurLigneRadarCarte(Color.valueOf(strValeur));
                        break;
                    case "replieDemarrageCarte":
                        setbReplieDemarrageCarte(strValeur.equals("true"));
                        break;

                    case "afficheMenuContextuel":
                        setbAfficheMenuContextuel(strValeur.equals("true"));
                        break;
                    case "affichePrecSuivMC":
                        setbAffichePrecSuivMC(strValeur.equals("true"));
                        break;
                    case "affichePlaneteNormalMC":
                        setbAffichePlanetNormalMC(strValeur.equals("true"));
                        break;
                    case "affichePersMC1":
                        setbAffichePersMC1(strValeur.equals("true"));
                        break;
                    case "affichePersMC2":
                        setbAffichePersMC2(strValeur.equals("true"));
                        break;
                    case "txtPersLib1":
                        setStrPersLib1(strValeur);
                        break;
                    case "txtPersLib2":
                        setStrPersLib2(strValeur);
                        break;
                    case "txtPersURL1":
                        setStrPersURL1(strValeur);
                        break;
                    case "txtPersURL2":
                        setStrPersURL2(strValeur);
                        break;

                }
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    public void afficheTemplate() throws IOException {
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation, lblTxtTitre, lblTxtTitre2, imgBoussole, imgAiguille, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, apVisuBoutonVisiteAuto, apVisuPlan, apVisuCarte, ivMasque, apFenetreAfficheInfo, lblFenetreURL, apAfficheBarrePersonnalisee, apAfficheDiapo, ivDiapo);

        lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        Color couleur = Color.valueOf(getStrCouleurFondTitre());
        int iRouge = (int) (couleur.getRed() * 255.d);
        int iBleu = (int) (couleur.getBlue() * 255.d);
        int iVert = (int) (couleur.getGreen() * 255.d);
        String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond);
        double taille = (double) getTitreTaille() / 100.d * ivVisualisation.getFitWidth();
        lblTxtTitre.setMinWidth(taille);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getMinWidth()) / 2);
        Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte1);
        lblTxtTitre.setPrefHeight(-1);
        lblTxtTitre.setVisible(isbAfficheTitre());
        fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()) * 3.d / 4.d);
        lblTxtTitre2.setFont(fonte1);
        lblTxtTitre2.setPrefHeight(-1);
        cbTitreVisite.setSelected(isbTitreVisite());
        cbTitrePanoramique.setSelected(isbTitrePanoramique());
        cbTitreAdapte.setSelected(isbTitreAdapte());
        bdfTitreDecalage.setNumber(new BigDecimal(getTitreDecalage()));
        switch (getStrTitrePosition()) {
            case "left":
                rbLeftTitre.setSelected(true);
                break;
            case "center":
                rbCenterTitre.setSelected(true);
                break;
            case "right":
                rbRightTitre.setSelected(true);
                break;
        }
        cbAfficheTitre.setSelected(isbAfficheTitre());
        cpCouleurTitre.setValue(Color.valueOf(getStrCouleurTitre()));
        cpCouleurFondTitre.setValue(Color.valueOf(getStrCouleurFondTitre()));
        cpCouleurBarreClassique.setValue(couleurBarreClassique);
        cpCouleurMasques.setValue(couleurMasque);
        cpCouleurHotspotsPanoramique.setValue(couleurHotspots);
        cpCouleurHotspotsPhoto.setValue(couleurHotspotsPhoto);
        slTailleHotspotsPanoramique.setValue(getiTailleHotspotsPanoramique());
        slTailleHotspotsImage.setValue(getiTailleHotspotsImage());
        slTailleHotspotsHTML.setValue(getiTailleHotspotsHTML());

        cbListePolicesTitre.setValue(getStrTitrePoliceNom());
        slOpaciteTitre.setValue(getTitreOpacite());
        cblisteStyleBarreClassique.setValue(getStyleBarreClassique());
        bdfOffsetXBarreClassique.setNumber(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetYBarreClassique.setNumber(new BigDecimal(getOffsetYBarreClassique()));
        if (getStrVisibiliteBarreClassique().equals("oui")) {
            cbBarreClassiqueVisible.setSelected(true);
        } else {
            cbBarreClassiqueVisible.setSelected(false);
        }
        if (getStrDeplacementsBarreClassique().equals("oui")) {
            cbDeplacementsBarreClassique.setSelected(true);
        } else {
            cbDeplacementsBarreClassique.setSelected(false);
        }
        if (getStrZoomBarreClassique().equals("oui")) {
            cbZoomBarreClassique.setSelected(true);
        } else {
            cbZoomBarreClassique.setSelected(false);
        }
        if (getStrOutilsBarreClassique().equals("oui")) {
            cbOutilsBarreClassique.setSelected(true);
        } else {
            cbOutilsBarreClassique.setSelected(false);
        }
        switch (getStrPositionBarreClassique()) {
            case "top:left":
                rbTopLeftBarreClassique.setSelected(true);
                break;
            case "top:center":
                rbTopCenterBarreClassique.setSelected(true);
                break;
            case "top:right":
                rbTopRightBarreClassique.setSelected(true);
                break;
            case "middle:left":
                rbMiddleLeftBarreClassique.setSelected(true);
                break;
            case "middle:right":
                rbMiddleRightBarreClassique.setSelected(true);
                break;
            case "bottom:left":
                rbBottomLeftBarreClassique.setSelected(true);
                break;
            case "bottom:center":
                rbBottomCenterBarreClassique.setSelected(true);
                break;
            case "bottom:right":
                rbBottomRightBarreClassique.setSelected(true);
                break;
        }

        if (getStrRotationBarreClassique().equals("oui")) {
            cbRotationBarreClassique.setSelected(true);
        } else {
            cbRotationBarreClassique.setSelected(false);
        }
        if (getStrPleinEcranBarreClassique().equals("oui")) {
            cbFSBarreClassique.setSelected(true);
        } else {
            cbFSBarreClassique.setSelected(false);
        }
        if (getStrSourisBarreClassique().equals("oui")) {
            cbSourisBarreClassique.setSelected(true);
        } else {
            cbSourisBarreClassique.setSelected(false);
        }
        if (getStrVisibiliteBarrePersonnalisee().equals("oui")) {
            chargeBarrePersonnalisee(getStrLienImageBarrePersonnalisee());

            rbCouleurOrigineBarrePersonnalisee.setSelected(isbCouleurOrigineBarrePersonnalisee());
            rbCouleurPersBarrePersonnalisee.setSelected(!isbCouleurOrigineBarrePersonnalisee());

            bdfOffsetXBarrePersonnalisee.setNumber(new BigDecimal(getOffsetXBarrePersonnalisee()));
            bdfOffsetYBarrePersonnalisee.setNumber(new BigDecimal(getOffsetYBarrePersonnalisee()));
            sltailleBarrePersonnalisee.setValue(getTailleBarrePersonnalisee());
            sltailleIconesBarrePersonnalisee.setValue(getTailleIconesBarrePersonnalisee());
            switch (getStrPositionBarrePersonnalisee()) {
                case "top:left":
                    rbTopLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "top:center":
                    rbTopCenterBarrePersonnalisee.setSelected(true);
                    break;
                case "top:right":
                    rbTopRightBarrePersonnalisee.setSelected(true);
                    break;
                case "middle:left":
                    rbMiddleLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "middle:right":
                    rbMiddleRightBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:left":
                    rbBottomLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:center":
                    rbBottomCenterBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:right":
                    rbBottomRightBarrePersonnalisee.setSelected(true);
                    break;

            }
            cbDeplacementsBarrePersonnalisee.setSelected(getStrDeplacementsBarrePersonnalisee().equals("oui"));
            cbZoomBarrePersonnalisee.setSelected(getStrZoomBarrePersonnalisee().equals("oui"));
            cbRotationBarrePersonnalisee.setSelected(getStrRotationBarrePersonnalisee().equals("oui"));
            cbFSBarrePersonnalisee.setSelected(getStrPleinEcranBarrePersonnalisee().equals("oui"));
            cbSourisBarrePersonnalisee.setSelected(getStrSourisBarrePersonnalisee().equals("oui"));
            cbBarrePersonnaliseeVisible.setSelected(getStrVisibiliteBarrePersonnalisee().equals("oui"));
            cpCouleurBarrePersonnalisee.setValue(couleurBarrePersonnalisee);
            tfLienImageBarrePersonnalisee.setText(getStrLienImageBarrePersonnalisee());
            tfLien1BarrePersonnalisee.setText(getStrLien1BarrePersonnalisee());
            tfLien2BarrePersonnalisee.setText(getStrLien2BarrePersonnalisee());
        }
        cbFenetreInfoPersonnalise.setSelected(isbFenetreInfoPersonnalise());
        cbFenetreAidePersonnalise.setSelected(isbFenetreAidePersonnalise());
        tfFenetreInfoImage.setText(getStrFenetreInfoImage());
        tfFenetreAideImage.setText(getStrFenetreAideImage());
        slFenetreInfoTaille.setValue(getFenetreInfoTaille());
        slFenetreAideTaille.setValue(getFenetreAideTaille());
        bdfFenetreInfoPosX.setNumber(new BigDecimal(getFenetreInfoPosX()));
        bdfFenetreInfoPosY.setNumber(new BigDecimal(getFenetreInfoPosY()));
        bdfFenetreAidePosX.setNumber(new BigDecimal(getFenetreAidePosX()));
        bdfFenetreAidePosY.setNumber(new BigDecimal(getFenetreAidePosY()));
        slFenetreInfoOpacite.setValue(getFenetreInfoOpacite());
        slFenetreAideOpacite.setValue(getFenetreAideOpacite());
        tfFenetreTexteURL.setText(getStrFenetreTexteURL());
        tfFenetreURL.setText(getStrFenetreURL());
        slFenetrePoliceTaille.setValue(getFenetrePoliceTaille());
        bdfFenetreURLPosX.setNumber(new BigDecimal(getFenetreURLPosX()));
        bdfFenetreURLPosY.setNumber(new BigDecimal(getFenetreURLPosY()));
        bdfFenetreURLPosX.setNumber(new BigDecimal(getFenetreURLPosX()));
        bdfFenetreURLPosY.setNumber(new BigDecimal(getFenetreURLPosY()));
        cpFenetreURLCouleur.setValue(Color.valueOf(getStrFenetreURLCouleur()));
        cbSuivantPrecedent.setSelected(isbSuivantPrecedent());
        paneFondSuivant.setVisible(isbSuivantPrecedent());
        paneFondPrecedent.setVisible(isbSuivantPrecedent());
        slTaillePoliceTitre.setValue(Double.parseDouble(getStrTitrePoliceTaille()));
        slEspacementBarreClassique.setValue(getEspacementBarreClassique());
        slTailleTitre.setValue(getTitreTaille());
        apVisualisation.getChildren().remove(hbbarreBoutons);
        apVisualisation.getChildren().remove(ivHotSpotPanoramique);
        apVisualisation.getChildren().remove(ivHotSpotImage);
        apVisualisation.getChildren().remove(ivHotSpotHTML);
        slTailleBoussole.setValue(getTailleBoussole());
        slOpaciteBoussole.setValue(getOpaciteBoussole());
        bdfOffsetXBoussole.setNumber(new BigDecimal(getOffsetXBoussole()));
        bdfOffsetYBoussole.setNumber(new BigDecimal(getOffsetYBoussole()));
        cbAiguilleMobile.setSelected(isbAiguilleMobileBoussole());
        cbAfficheBoussole.setSelected(isbAfficheBoussole());
        rbBoussTopLeft.setSelected(getStrPositionBoussole().equals("top:left"));
        rbBoussBottomLeft.setSelected(getStrPositionBoussole().equals("bottom:left"));
        rbBoussTopRight.setSelected(getStrPositionBoussole().equals("top:right"));
        rbBoussBottomRight.setSelected(getStrPositionBoussole().equals("bottom:right"));
        slTailleMasque.setValue(getTailleMasque());
        slOpaciteMasque.setValue(getOpaciteMasque());
        bdfOffsetXMasque.setNumber(new BigDecimal(getdXMasque()));
        bdfOffsetYMasque.setNumber(new BigDecimal(getdYMasque()));
        cbMasqueNavigation.setSelected(isbMasqueNavigation());
        cbMasqueBoussole.setSelected(isbMasqueBoussole());
        cbMasqueTitre.setSelected(isbMasqueTitre());
        cbMasquePlan.setSelected(isbMasquePlan());
        cbMasqueReseaux.setSelected(isbMasqueReseaux());
        cbMasqueVignettes.setSelected(isbMasqueVignettes());
        cbMasqueCombo.setSelected(isbMasqueCombo());
        cbMasqueSuivPrec.setSelected(isbMasqueSuivPrec());
        cbMasqueHotspots.setSelected(isbMasqueHotspots());
        cbAfficheMasque.setSelected(isbAfficheMasque());
        rbMasqueTopLeft.setSelected(getStrPositionMasque().equals("top:left"));
        rbMasqueBottomLeft.setSelected(getStrPositionMasque().equals("bottom:left"));
        rbMasqueTopRight.setSelected(getStrPositionMasque().equals("top:right"));
        rbMasqueBottomRight.setSelected(getStrPositionMasque().equals("bottom:right"));
        slTailleReseauxSociaux.setValue(getTailleReseauxSociaux());
        slOpaciteReseauxSociaux.setValue(getOpaciteReseauxSociaux());
        bdfOffsetXReseauxSociaux.setNumber(new BigDecimal(getdXReseauxSociaux()));
        bdfOffsetYreseauxSociaux.setNumber(new BigDecimal(getdYReseauxSociaux()));
        cbReseauxSociauxTwitter.setSelected(isbReseauxSociauxTwitter());
        cbReseauxSociauxGoogle.setSelected(isbReseauxSociauxGoogle());
        cbReseauxSociauxFacebook.setSelected(isbReseauxSociauxFacebook());
        cbReseauxSociauxEmail.setSelected(isbReseauxSociauxEmail());
        cbAfficheReseauxSociaux.setSelected(isbAfficheReseauxSociaux());
        rbReseauxSociauxTopLeft.setSelected(getStrPositionReseauxSociaux().equals("top:left"));
        rbReseauxSociauxBottomLeft.setSelected(getStrPositionReseauxSociaux().equals("bottom:left"));
        rbReseauxSociauxTopRight.setSelected(getStrPositionReseauxSociaux().equals("top:right"));
        rbReseauxSociauxBottomRight.setSelected(getStrPositionReseauxSociaux().equals("bottom:right"));
        cbAfficheVignettes.setSelected(isbAfficheVignettes());
        slOpaciteVignettes.setValue(getOpaciteVignettes());
        slTailleVignettes.setValue(getTailleImageVignettes());
        rbVignettesLeft.setSelected(getStrPositionVignettes().equals("left"));
        rbVignettesRight.setSelected(getStrPositionVignettes().equals("right"));
        rbVignettesBottom.setSelected(getStrPositionVignettes().equals("bottom"));
        cpCouleurFondVignettes.setValue(Color.valueOf(getStrCouleurFondVignettes()));
        cpCouleurTexteVignettes.setValue(Color.valueOf(getStrCouleurTexteVignettes()));
        cbReplieDemarrageVignettes.setSelected(isbReplieDemarrageVignettes());
        cbAfficheComboMenu.setSelected(isbAfficheComboMenu());
        cbAfficheComboMenuImages.setSelected(isbAfficheComboMenuImages());
        String strPosit = getStrPositionYComboMenu() + ":" + getStrPositionXComboMenu();
        switch (strPosit) {
            case "top:left":
                rbComboMenuTopLeft.setSelected(true);
                break;
            case "top:center":
                rbComboMenuTopCenter.setSelected(true);
                break;
            case "top:right":
                rbComboMenuTopRight.setSelected(true);
                break;
            case "bottom:left":
                rbComboMenuBottomLeft.setSelected(true);
                break;
            case "bottom:center":
                rbComboMenuBottomCenter.setSelected(true);
                break;
            case "bottom:right":
                rbComboMenuBottomRight.setSelected(true);
                break;
        }
        bdfOffsetXComboMenu.setNumber(new BigDecimal(getOffsetXComboMenu()));
        bdfOffsetYComboMenu.setNumber(new BigDecimal(getOffsetYComboMenu()));
        getCbAfficheBoutonVisiteAuto().setSelected(isbAfficheBoutonVisiteAuto());
        String strPosit1 = getStrPositionYBoutonVisiteAuto() + ":" + getStrPositionXBoutonVisiteAuto();
        switch (strPosit1) {
            case "top:left":
                rbBoutonVisiteAutoTopLeft.setSelected(true);
                break;
            case "top:center":
                rbBoutonVisiteAutoTopCenter.setSelected(true);
                break;
            case "top:right":
                rbBoutonVisiteAutoTopRight.setSelected(true);
                break;
            case "bottom:left":
                rbBoutonVisiteAutoBottomLeft.setSelected(true);
                break;
            case "bottom:center":
                rbBoutonVisiteAutoBottomCenter.setSelected(true);
                break;
            case "bottom:right":
                rbBoutonVisiteAutoBottomRight.setSelected(true);
                break;
        }
        bdfOffsetXBoutonVisiteAuto.setNumber(new BigDecimal(getOffsetXBoutonVisiteAuto()));
        bdfOffsetYBoutonVisiteAuto.setNumber(new BigDecimal(getOffsetYBoutonVisiteAuto()));
        slTailleBoutonVisiteAuto.setValue(getTailleBoutonVisiteAuto());
        cbAffichePlan.setSelected(isbAffichePlan());
        slOpacitePlan.setValue(getOpacitePlan());
        slLargeurPlan.setValue(getLargeurPlan());
        rbPlanLeft.setSelected(getStrPositionPlan().equals("left"));
        rbPlanRight.setSelected(getStrPositionPlan().equals("right"));
        cpCouleurFondPlan.setValue(getCouleurFondPlan());
        cpCouleurTextePlan.setValue(getCouleurTextePlan());
        cpCouleurDiaporama.setValue(Color.valueOf(getStrCouleurDiaporama()));
        slOpaciteDiaporama.setValue(getDiaporamaOpacite());
        if (isbAffichePlan()) {
            getTabPlan().setDisable(!isbAffichePlan());
            getMniAffichagePlan().setDisable(!isbAffichePlan());
            getIvAjouterPlan().setDisable(!isbAffichePlan());
            getMniAjouterPlan().setDisable(!isbAffichePlan());
            if (isbAffichePlan()) {
                getIvAjouterPlan().setOpacity(1.0);
            } else {
                getIvAjouterPlan().setOpacity(0.3);
            }

        }
        cbAfficheRadar.setSelected(isbAfficheRadar());
        slOpaciteRadar.setValue(getOpaciteRadar());
        slTailleRadar.setValue(getTailleRadar());
        cpCouleurFondRadar.setValue(getCouleurFondRadar());
        cpCouleurLigneRadar.setValue(getCouleurLigneRadar());
        cbReplieDemarragePlan.setSelected(isbReplieDemarragePlan());
        cbAfficheCarte.setSelected(isbAfficheCarte());
        slOpaciteCarte.setValue(getOpaciteCarte());
        slLargeurCarte.setValue(getLargeurCarte());
        slHauteurCarte.setValue(getHauteurCarte());
        slZoomCarte.setValue(getiFacteurZoomCarte());
        rbCarteLeft.setSelected(getStrPositionCarte().equals("left"));
        rbCarteRight.setSelected(getStrPositionCarte().equals("right"));
        cpCouleurFondCarte.setValue(getCouleurFondCarte());
        cpCouleurTexteCarte.setValue(getCouleurTexteCarte());
        cpCouleurDiaporama.setValue(Color.valueOf(getStrCouleurDiaporama()));
        slOpaciteDiaporama.setValue(getDiaporamaOpacite());
        cbAfficheRadarCarte.setSelected(isbAfficheRadarCarte());
        slOpaciteRadarCarte.setValue(getOpaciteRadarCarte());
        slTailleRadarCarte.setValue(getTailleRadarCarte());
        cpCouleurFondRadarCarte.setValue(getCouleurFondRadarCarte());
        cpCouleurLigneRadarCarte.setValue(getCouleurLigneRadarCarte());
        if (isbInternet()) {
            navigateurCarteOL.changeCarte(getStrNomLayers());
        }
        cbReplieDemarrageCarte.setSelected(isbReplieDemarrageCarte());
        cbAfficheMenuContextuel.setSelected(isbAfficheMenuContextuel());
        cbAffichePrecSuivMC.setSelected(isbAffichePrecSuivMC());
        cbAffichePlanetNormalMC.setSelected(isbAffichePlanetNormalMC());
        cbAffichePersMC1.setSelected(isbAffichePersMC1());
        cbAffichePersMC2.setSelected(isbAffichePersMC2());
        tfPersLib1.setText(getStrPersLib1());
        tfPersLib2.setText(getStrPersLib2());
        tfPersURL1.setText(getStrPersURL1());
        tfPersURL2.setText(getStrPersURL2());
        afficheImagesFondInterface();
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
        afficheVignettes();
        affichePlan();
        afficheCarte();
        afficheDiaporama();
        afficheFenetreAide();
        afficheFenetreInfo();
        afficheComboMenu();
        afficheBoutonVisiteAuto();
        poImageFond.setbValide(getiNombreImagesFond() > 0);
        ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
        ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
        ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
    }

    /**
     *
     */
    public void rafraichit() {

        cbImage.getItems().clear();
        if (getiNombrePanoramiques() > 0) {
            rbPerso.setDisable(false);
            String strImgAffiche = "";
            if (cbImage.getSelectionModel().getSelectedItem() != null) {
                strImgAffiche = cbImage.getSelectionModel().getSelectedItem().toString();
            }
            for (int i = 0; i < getiNombrePanoramiques(); i++) {
                String strNomImage = getPanoramiquesProjet()[i].getStrNomFichier().substring(
                        getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1,
                        getPanoramiquesProjet()[i].getStrNomFichier().length()
                );
                cbImage.getItems().add(i, strNomImage);
            }
            cbImage.setValue(strImgAffiche);
        }
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        affichePlan();
        afficheVignettes();
        afficheComboMenu();
        afficheBoutonVisiteAuto();
        if (isbInternet()) {
            if (navigateurCarteOL.isbDebut()) {
                navigateurCarteOL.retireMarqueurs();
                for (int ii = 0; ii < getiNombrePanoramiques(); ii++) {
                    CoordonneesGeographiques coord = getPanoramiquesProjet()[ii].getMarqueurGeolocatisation();
                    if (coord != null) {
                        String strFichierPano = getPanoramiquesProjet()[ii]
                                .getStrNomFichier().substring(getPanoramiquesProjet()[ii].getStrNomFichier()
                                        .lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[ii]
                                        .getStrNomFichier().length()).split("\\.")[0];
                        String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-weight:bold;font-size : 12px;'>"
                                + getPanoramiquesProjet()[ii].getStrTitrePanoramique()
                                + "</span><br/>"
                                + "<span style='font-family : Verdana,Arial,sans-serif;bold;font-size : 10px;'>"
                                + strFichierPano
                                + "</span>";
                        strHTML = strHTML.replace("\\", "/");
                        navigateurCarteOL.ajouteMarqueur(ii, coord, strHTML);
                        navigateurCarteOL.allerCoordonnees(coord, iFacteurZoomCarte);
                    }
                }
                if (getCoordCentreCarte() != null) {
                    navigateurCarteOL.allerCoordonnees(getCoordCentreCarte(), iFacteurZoomCarte);
                }
                afficheCarte();
            }
        }

    }

    /**
     *
     * @param iNumImage
     */
    private void retireImageFond(int iNumImage) {
        for (int i = iNumImage; i < getiNombreImagesFond() - 1; i++) {
            getImagesFond()[i] = getImagesFond()[i + 1];
        }
        setiNombreImagesFond(getiNombreImagesFond() - 1);
        poImageFond.setbValide(getiNombreImagesFond() > 0);
        afficheImagesFondInterface();
    }

    /**
     *
     */
    private void afficheImagesFondInterface() {

        apImageFond.getChildren().clear();
        Image imgAjoute = new Image("file:" + getStrRepertAppli() + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (me) -> {
                    ajoutImageFond();
                }
        );

        double hauteurPanel = 280;
        apImageFond.setPrefHeight(getiNombreImagesFond() * (hauteurPanel + 10) + 60);
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            int ij = i;
            AnchorPane apImagesFond = new AnchorPane();
            apImagesFond.setPrefWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMinWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMaxWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setPrefHeight(hauteurPanel);
            apImagesFond.setStyle("-fx-border-color : #666666; -fx-border-width : 1px; -fx-border-style :solid;");
            apImagesFond.setLayoutY(i * (hauteurPanel + 10) + 60);
            Pane paneFond1 = new Pane();
            paneFond1.setCursor(Cursor.HAND);
            ImageView ivAjouteImageFond1 = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/suppr.png", 30, 30, true, true));
            paneFond1.setLayoutX(vbOutils.getPrefWidth() - 50);
            paneFond1.setLayoutY(0);
            Tooltip tltpRetireImageFond = new Tooltip(rbLocalisation.getString("interface.imageFondRetire"));
            tltpRetireImageFond.setStyle(getStrTooltipStyle());
            Tooltip.install(paneFond1, tltpRetireImageFond);
            paneFond1.getChildren().add(ivAjouteImageFond1);
            paneFond1.setOnMouseClicked(
                    (me) -> {
                        retireImageFond(ij);
                    }
            );
            ImageView ivImageFond = new ImageView(getImagesFond()[i].getImgFond());
            ivImageFond.setPreserveRatio(true);
            if (getImagesFond()[i].getImgFond().getWidth() > getImagesFond()[i].getImgFond().getHeight()) {
                ivImageFond.setFitWidth(100);
            } else {
                ivImageFond.setFitHeight(100);
            }
            ivImageFond.setLayoutX(10);
            ivImageFond.setLayoutY(10);
            ToggleGroup tgPosition = new ToggleGroup();
            RadioButton rbImageFondTopLeft = new RadioButton();
            RadioButton rbImageFondTopCenter = new RadioButton();
            RadioButton rbImageFondTopRight = new RadioButton();
            RadioButton rbImageFondMiddleLeft = new RadioButton();
            RadioButton rbImageFondMiddleCenter = new RadioButton();
            RadioButton rbImageFondMiddleRight = new RadioButton();
            RadioButton rbImageFondBottomLeft = new RadioButton();
            RadioButton rbImageFondBottomCenter = new RadioButton();
            RadioButton rbImageFondBottomRight = new RadioButton();

            rbImageFondTopLeft.setUserData("top:left");
            rbImageFondTopCenter.setUserData("top:center");
            rbImageFondTopRight.setUserData("top:right");
            rbImageFondMiddleLeft.setUserData("middle:left");
            rbImageFondMiddleCenter.setUserData("middle:center");
            rbImageFondMiddleRight.setUserData("middle:right");
            rbImageFondBottomLeft.setUserData("bottom:left");
            rbImageFondBottomCenter.setUserData("bottom:center");
            rbImageFondBottomRight.setUserData("bottom:right");

            rbImageFondTopLeft.setToggleGroup(tgPosition);
            rbImageFondTopCenter.setToggleGroup(tgPosition);
            rbImageFondTopRight.setToggleGroup(tgPosition);
            rbImageFondMiddleLeft.setToggleGroup(tgPosition);
            rbImageFondMiddleCenter.setToggleGroup(tgPosition);
            rbImageFondMiddleRight.setToggleGroup(tgPosition);
            rbImageFondBottomLeft.setToggleGroup(tgPosition);
            rbImageFondBottomCenter.setToggleGroup(tgPosition);
            rbImageFondBottomRight.setToggleGroup(tgPosition);
            String strPosit = getImagesFond()[i].getStrPosY() + ":" + getImagesFond()[i].getStrPosX();
            switch (strPosit) {
                case "top:left":
                    rbImageFondTopLeft.setSelected(true);
                    break;
                case "top:center":
                    rbImageFondTopCenter.setSelected(true);
                    break;
                case "top:right":
                    rbImageFondTopRight.setSelected(true);
                    break;
                case "middle:left":
                    rbImageFondMiddleLeft.setSelected(true);
                    break;
                case "middle:center":
                    rbImageFondMiddleCenter.setSelected(true);
                    break;
                case "middle:right":
                    rbImageFondMiddleRight.setSelected(true);
                    break;
                case "bottom:left":
                    rbImageFondBottomLeft.setSelected(true);
                    break;
                case "bottom:center":
                    rbImageFondBottomCenter.setSelected(true);
                    break;
                case "bottom:right":
                    rbImageFondBottomRight.setSelected(true);
                    break;
            }

            int iPosX = 175;
            int iPosY = 30;

            rbImageFondTopLeft.setLayoutX(iPosX);
            rbImageFondTopCenter.setLayoutX(iPosX + 20);
            rbImageFondTopRight.setLayoutX(iPosX + 40);
            rbImageFondTopLeft.setLayoutY(iPosY);
            rbImageFondTopCenter.setLayoutY(iPosY);
            rbImageFondTopRight.setLayoutY(iPosY);

            rbImageFondMiddleLeft.setLayoutX(iPosX);
            rbImageFondMiddleCenter.setLayoutX(iPosX + 20);
            rbImageFondMiddleRight.setLayoutX(iPosX + 40);
            rbImageFondMiddleLeft.setLayoutY(iPosY + 20);
            rbImageFondMiddleCenter.setLayoutY(iPosY + 20);
            rbImageFondMiddleRight.setLayoutY(iPosY + 20);

            rbImageFondBottomLeft.setLayoutX(iPosX);
            rbImageFondBottomCenter.setLayoutX(iPosX + 20);
            rbImageFondBottomRight.setLayoutX(iPosX + 40);
            rbImageFondBottomLeft.setLayoutY(iPosY + 40);
            rbImageFondBottomCenter.setLayoutY(iPosY + 40);
            rbImageFondBottomRight.setLayoutY(iPosY + 40);
            Label lblPosit = new Label(rbLocalisation.getString("interface.positionImageFond"));
            lblPosit.setLayoutX(150);
            lblPosit.setLayoutY(10);
            Label lblOffsetXImageFond = new Label("dX ");
            lblOffsetXImageFond.setLayoutX(25);
            lblOffsetXImageFond.setLayoutY(125);
            Label lblOffsetYImageFond = new Label("dY ");
            lblOffsetYImageFond.setLayoutX(175);
            lblOffsetYImageFond.setLayoutY(125);
            BigDecimalField bdfOffsetXImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetX()));
            bdfOffsetXImageFond.setLayoutX(50);
            bdfOffsetXImageFond.setLayoutY(120);
            bdfOffsetXImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetXImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetXImageFond.setMaxWidth(100);
            BigDecimalField bdfOffsetYImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetY()));
            bdfOffsetYImageFond.setLayoutX(200);
            bdfOffsetYImageFond.setLayoutY(120);
            bdfOffsetYImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetYImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetYImageFond.setMaxWidth(100);
            CheckBox cbMasquableImageFond = new CheckBox(rbLocalisation.getString("interface.masquableImageFond"));
            cbMasquableImageFond.setLayoutX(150);
            cbMasquableImageFond.setLayoutY(90);
            cbMasquableImageFond.setSelected(getImagesFond()[i].isMasquable());

            Label lblOpaciteImageFond = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
            lblOpaciteImageFond.setLayoutX(10);
            lblOpaciteImageFond.setLayoutY(160);
            Slider slOpaciteImageFond = new Slider(0, 1.0, getImagesFond()[i].getOpacite());
            slOpaciteImageFond.setLayoutX(120);
            slOpaciteImageFond.setLayoutY(160);
            Label lblTailleImageFond = new Label(rbLocalisation.getString("interface.tailleVignettes"));
            lblTailleImageFond.setLayoutX(10);
            lblTailleImageFond.setLayoutY(190);
            double echelle = getImagesFond()[i].getTailleX() / getImagesFond()[i].getImgFond().getWidth();
            Slider slTailleImageFond = new Slider(0.1, 2.0, echelle);
            slTailleImageFond.setLayoutX(120);
            slTailleImageFond.setLayoutY(190);
            Label lblUrlImageFond = new Label("url");
            lblUrlImageFond.setLayoutX(10);
            lblUrlImageFond.setLayoutY(222);
            TextField tfUrlImageFond = new TextField(getImagesFond()[i].getStrUrl());
            tfUrlImageFond.setPrefHeight(20);
            tfUrlImageFond.setPrefWidth(200);
            tfUrlImageFond.setLayoutX(120);
            tfUrlImageFond.setLayoutY(220);
            Label lblInfobulleImageFond = new Label(rbLocalisation.getString("interface.infobulle"));
            lblInfobulleImageFond.setLayoutX(10);
            lblInfobulleImageFond.setLayoutY(252);
            TextField tfInfobulleImageFond = new TextField(getImagesFond()[i].getStrInfobulle());
            tfInfobulleImageFond.setPrefHeight(20);
            tfInfobulleImageFond.setPrefWidth(200);
            tfInfobulleImageFond.setLayoutX(120);
            tfInfobulleImageFond.setLayoutY(250);

            apImagesFond.getChildren().addAll(ivImageFond, paneFond1,
                    lblPosit,
                    rbImageFondTopLeft, rbImageFondTopCenter, rbImageFondTopRight,
                    rbImageFondMiddleLeft, rbImageFondMiddleCenter, rbImageFondMiddleRight,
                    rbImageFondBottomLeft, rbImageFondBottomCenter, rbImageFondBottomRight,
                    cbMasquableImageFond,
                    lblOffsetXImageFond, bdfOffsetXImageFond, lblOffsetYImageFond, bdfOffsetYImageFond,
                    lblOpaciteImageFond, slOpaciteImageFond,
                    lblTailleImageFond, slTailleImageFond,
                    lblUrlImageFond, tfUrlImageFond,
                    lblInfobulleImageFond, tfInfobulleImageFond
            );

            tgPosition.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (tgPosition.getSelectedToggle() != null) {
                    String positImageFond = tgPosition.getSelectedToggle().getUserData().toString();
                    getImagesFond()[ij].setStrPosX(positImageFond.split(":")[1]);
                    getImagesFond()[ij].setStrPosY(positImageFond.split(":")[0]);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            bdfOffsetXImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                getImagesFond()[ij].setOffsetX(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });
            bdfOffsetYImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                getImagesFond()[ij].setOffsetY(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });
            slOpaciteImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue != null) {
                    double opac = (double) newValue;
                    getImagesFond()[ij].setOpacite(opac);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            slTailleImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue != null) {
                    double taille = (double) newValue;
                    getImagesFond()[ij].setTailleX((int) (getImagesFond()[ij].getImgFond().getWidth() * taille));
                    getImagesFond()[ij].setTailleY((int) (getImagesFond()[ij].getImgFond().getHeight() * taille));
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            tfUrlImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                String txt = newValue;
                getImagesFond()[ij].setStrUrl(txt);
            });
            tfInfobulleImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                String txt = newValue;
                getImagesFond()[ij].setStrInfobulle(txt);
            });
            cbMasquableImageFond.selectedProperty().addListener((ov, old_val, new_val) -> {
                if (new_val != null) {
                    getImagesFond()[ij].setMasquable(new_val);
                }
            });

            apImageFond.getChildren().add(apImagesFond);
        }
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
    }

    /**
     *
     * @return
     */
    private String ajoutFenetreImage() {
        File fileRepert;
        if (strRepertImagesFond.equals("")) {
            fileRepert = new File(getStrCurrentDir() + File.separator);
        } else {
            fileRepert = new File(strRepertImagesFond);
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

        fileChooser.setInitialDirectory(fileRepert);
        fileChooser.getExtensionFilters().addAll(extFilterImages);

        File fichierImage = fileChooser.showOpenDialog(null);
        if (fichierImage != null) {
            strRepertImagesFond = fichierImage.getParent();
            File fileRepertImage = new File(getStrRepertTemp() + File.separator + "images");
            if (!fileRepertImage.exists()) {
                fileRepertImage.mkdirs();
            }
            try {
                copieFichierRepertoire(fichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }

            return fichierImage.getAbsolutePath();
        } else {
            return "";
        }

    }

    /**
     *
     */
    private void ajoutImageFond() {
        if (getiNombreImagesFond() < 20) {

            File fileRepert;
            if (strRepertImagesFond.equals("")) {
                fileRepert = new File(getStrCurrentDir() + File.separator);
            } else {
                fileRepert = new File(strRepertImagesFond);
            }

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                strRepertImagesFond = fileFichierImage.getParent();
                File fileRepertImage = new File(getStrRepertTemp() + File.separator + "images");
                if (!fileRepertImage.exists()) {
                    fileRepertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

                getImagesFond()[getiNombreImagesFond()] = new ImageFond();
                getImagesFond()[getiNombreImagesFond()].setStrFichierImage(fileFichierImage.getAbsolutePath());
                Image imgFond = new Image("file:" + fileFichierImage.getAbsolutePath());
                getImagesFond()[getiNombreImagesFond()].setImgFond(imgFond);
                getImagesFond()[getiNombreImagesFond()].setTailleX((int) imgFond.getWidth());
                getImagesFond()[getiNombreImagesFond()].setTailleY((int) imgFond.getHeight());
                setiNombreImagesFond(getiNombreImagesFond() + 1);
                afficheImagesFondInterface();
            }
        }
        poImageFond.setbValide(getiNombreImagesFond() > 0);
    }

    /**
     *
     * @param iLargeur
     * @param iHauteur
     */
    public void creeInterface(int iLargeur, int iHauteur) {
        List<String> strLstPolices = new ArrayList<>();
        strLstPolices.add("Arial");
        strLstPolices.add("Arial Black");
        strLstPolices.add("Comic Sans MS");
        strLstPolices.add("Couurier New");
        strLstPolices.add("Lucida Sans Typewriter");
        strLstPolices.add("Segoe Print");
        strLstPolices.add("Tahoma");
        strLstPolices.add("Times New Roman");
        strLstPolices.add("Verdana");
        ObservableList<String> strListePolices = FXCollections.observableList(strLstPolices);
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
        strRepertBoutonsPrincipal = getStrRepertAppli() + File.separator + "theme/barreNavigation";
        strRepertHotSpots = getStrRepertAppli() + File.separator + "theme/hotspots";
        strRepertHotSpotsPhoto = getStrRepertAppli() + File.separator + "theme/photos";
        strRepertHotSpotsHTML = getStrRepertAppli() + File.separator + "theme/html";
        strRepertBoussoles = getStrRepertAppli() + File.separator + "theme/boussoles";
        strRepertMasques = getStrRepertAppli() + File.separator + "theme/MA";
        strRepertReseauxSociaux = getStrRepertAppli() + File.separator + "theme/reseaux";
        ivBtnVisiteAuto = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/playAutoTour.jpg"));
        ivBtnVisiteAuto.setFitWidth(32);
        ivBtnVisiteAuto.setPreserveRatio(true);
        apVisuBoutonVisiteAuto = new AnchorPane();
        apVisuBoutonVisiteAuto.setPrefWidth(32);
        apVisuBoutonVisiteAuto.setPrefHeight(32);
        //apVisuBoutonVisiteAuto.setStyle("-fx-background-color : #777");
        chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
        ArrayList<String> strListeStyles = strListerStyle(strRepertBoutonsPrincipal);
        ArrayList<String> strListeHotSpots = strListerHotSpots(strRepertHotSpots);
        ArrayList<String> strListeHotSpotsPhoto = strListerHotSpots(strRepertHotSpotsPhoto);
        ArrayList<String> strListeHotSpotsHTML = strListerHotSpots(strRepertHotSpotsHTML);
        ArrayList<String> strListeBoussoles = strListerBoussoles(strRepertBoussoles);
        ArrayList<String> strListeMasques = strListerMasques(strRepertMasques);
        int iNombreHotSpots = strListeHotSpots.size();
        ImageView[] ivHotspots = new ImageView[iNombreHotSpots];
        int iNombreHotSpotsPhoto = strListeHotSpotsPhoto.size();
        int iNombreHotSpotsHTML = strListeHotSpotsHTML.size();
        ImageView[] ivHotspotsPhoto = new ImageView[iNombreHotSpotsPhoto];
        ImageView[] ivHotspotsHTML = new ImageView[iNombreHotSpotsHTML];
        imgClaire = new Image("file:" + getStrRepertAppli() + File.separator + "images/claire.jpg");
        imgSombre = new Image("file:" + getStrRepertAppli() + File.separator + "images/sombre.jpg");
        imgSuivant = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "panovisu/images/suivant.png"));
        imgPrecedent = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "panovisu/images/precedent.png"));
        ivBtnVisiteAuto = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/playAutoTour.png"));
        ivBtnVisiteAuto.setFitWidth(32);
        ivBtnVisiteAuto.setPreserveRatio(true);
        paneFondSuivant = new Pane(imgSuivant);
        paneFondPrecedent = new Pane(imgPrecedent);
        paneFondSuivant.setStyle("-fx-background-color : black;-fx-background-radius : 5 0 0 5;");
        paneFondSuivant.setOpacity(0.5);
        paneFondPrecedent.setStyle("-fx-background-color : black;-fx-background-radius : 0 5 5 0;");
        paneFondPrecedent.setOpacity(0.5);

        lblTxtTitre = new Label(rbLocalisation.getString("main.titrePano"));
        lblTxtTitre2 = new Label(rbLocalisation.getString("main.titrePano"));
        Font fonte = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte);
        fonte = Font.font(getStrTitrePoliceNom(), (Double.parseDouble(getStrTitrePoliceTaille()) * 3.d / 4.d));
        lblTxtTitre2.setFont(fonte);
        Color couleur = Color.valueOf(getStrCouleurFondTitre());
        int iRouge = (int) (couleur.getRed() * 255.d);
        int iBleu = (int) (couleur.getBlue() * 255.d);
        int iVert = (int) (couleur.getGreen() * 255.d);
        String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";

        lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond);
        lblTxtTitre2.setStyle("-fx-background-color : " + strCoulFond);

        double largeurOutils = 380;

        paneTabInterface = new Pane();
        hbInterface = new HBox();
        hbInterface.setPrefWidth(iLargeur);
        hbInterface.setPrefHeight(iHauteur);
        paneTabInterface.getChildren().add(hbInterface);
        apVisualisation = new AnchorPane();
        apVisualisation.setPrefWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setMaxWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setMinWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setPrefHeight(iHauteur);
        vbOutils = new VBox(-5);
//        AnchorPane apPanovisu = new AnchorPane();
//        apPanovisu.setPrefHeight(118);
//        apPanovisu.setMinHeight(118);
//        apPanovisu.setMaxHeight(118);
//        apPanovisu.setStyle("-fx-background-color : derive(-fx-base,-5%);");
//
//        ImageView ivPanoVisu = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/panovisu.png", 83, 83, true, true));
//        ivPanoVisu.setLayoutX(25);
//        ivPanoVisu.setLayoutY(10);
//        Label lblPanoVisu = new Label("panoVisu Vers. : " + strNumVersion);
//        lblPanoVisu.setStyle("-fx-font-weight : bold;-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 1.2em;");
//        lblPanoVisu.setLayoutX(118);
//        lblPanoVisu.setLayoutY(25);
//        Label lblPanoVisu2 = new Label("(c) Laurent LANG (2014-2015)");
//        lblPanoVisu2.setLayoutX(118);
//        lblPanoVisu2.setLayoutY(55);
//        lblPanoVisu2.setStyle("-fx-font-family : Verdana,Arial,sans-serif;-fx-font-size : 0.8em;");
//        Separator sepTitre = new Separator(Orientation.HORIZONTAL);
//        sepTitre.setPrefWidth(380);
//        sepTitre.setLayoutY(103);
//        apPanovisu.getChildren().addAll(ivPanoVisu, lblPanoVisu, lblPanoVisu2, sepTitre);
        vbOutils.setLayoutX(5);
        AnchorPane apOutils = new AnchorPane();
        apOutils.setPrefWidth(largeurOutils);
        apOutils.setMaxWidth(largeurOutils);
        apOutils.setTranslateY(3);
        apOutils.setTranslateX(20);
        ScrollPane spOutils = new ScrollPane(vbOutils);
        apOutils.getChildren().addAll(spOutils);
        spOutils.setId("spOutils");
        spOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spOutils.setMinHeight(iHauteur - 52 + getiDecalageMac());
        spOutils.setMaxHeight(iHauteur - 52 + getiDecalageMac());
        spOutils.setPrefWidth(largeurOutils);
        spOutils.setMinWidth(largeurOutils);
        spOutils.setFitToWidth(true);
        spOutils.setFitToHeight(true);

        spOutils.setLayoutY(-getiDecalageMac());
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        hbInterface.getChildren().addAll(apVisualisation, apOutils);
        /*
         * ***************************************************************
         *     Panneau de visualisation de l'interface
         * ***************************************************************
         */
        double tailleMax = apVisualisation.getPrefWidth() - 40;
        if (tailleMax > 1200) {
            tailleMax = 1200;
        }
        ivVisualisation = new ImageView(imgClaire);
        ivVisualisation.setFitWidth(tailleMax);
        if (tailleMax * 2.d / 3.d > iHauteur - 100) {
            ivVisualisation.setFitHeight(iHauteur - 100);
        } else {
            ivVisualisation.setFitHeight(tailleMax * 2.d / 3.d);
        }
        ivVisualisation.setSmooth(true);
        double LX = (apVisualisation.getPrefWidth() - ivVisualisation.getFitWidth()) / 2;
        ivVisualisation.setLayoutX(LX);
        ivVisualisation.setLayoutY(20);
        lblTxtTitre.setMinSize(tailleMax, 30);
        lblTxtTitre.setPadding(new Insets(5));
        lblTxtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        lblTxtTitre.setAlignment(Pos.CENTER);
        lblTxtTitre.setTextFill(Color.WHITE);
        lblTxtTitre.setLayoutY(20);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getMinWidth()) / 2);
        Color col1 = Color.valueOf(getStrCouleurFondTitre());
        double iR1 = (int) (col1.getRed() * 255.d);
        double iB1 = (int) (col1.getBlue() * 255.d);
        double iV1 = (int) (col1.getGreen() * 255.d);
        String strCoulF1 = "rgba(" + iR1 + "," + iV1 + "," + iB1 + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-background-color : " + strCoulF1);
        lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        lblTxtTitre2.setStyle("-fx-background-color : " + strCoulF1);
        lblTxtTitre2.setTextFill(Color.valueOf(getStrCouleurTitre()));
        lblTxtTitre2.setVisible(false);
        rbClair = new RadioButton("Image claire");
        rbSombre = new RadioButton("Image Sombre");
        rbPerso = new RadioButton("");
        cbImage = new ComboBox();
        cbImage.setDisable(true);
        double positRB = ivVisualisation.getFitHeight() + 30;
        rbClair.setToggleGroup(tgImage);
        rbSombre.setToggleGroup(tgImage);
        rbPerso.setToggleGroup(tgImage);
        apVisualisation.getChildren().addAll(rbClair, rbSombre);
        rbClair.setLayoutX(LX + 40);
        rbClair.setLayoutY(positRB);
        rbClair.setSelected(true);
        rbSombre.setLayoutX(LX + 180);
        rbSombre.setLayoutY(positRB);
        rbPerso.setLayoutX(LX + 320);
        rbPerso.setLayoutY(positRB);
        cbImage.setLayoutX(LX + 350);
        cbImage.setLayoutY(positRB - 3);
        rbPerso.setDisable(true);
        rbClair.setUserData("claire");
        rbSombre.setUserData("sombre");
        rbPerso.setUserData("perso");
        imgBoussole = new ImageView(new Image("file:" + strRepertBoussoles + File.separator + getStrImageBoussole()));
        imgAiguille = new ImageView("file:" + strRepertBoussoles + File.separator + "aiguille.png");
        apAfficheBarrePersonnalisee = new AnchorPane();
        apAfficheBarrePersonnalisee.setBackground(Background.EMPTY);
        apAfficheBarrePersonnalisee.setLayoutX(ivVisualisation.getLayoutX());
        apAfficheBarrePersonnalisee.setLayoutY(ivVisualisation.getLayoutY());
        ivTwitter = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxTwitter()));
        ivGoogle = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxGoogle()));
        ivFacebook = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxFacebook()));
        ivEmail = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxEmail()));
        apVisuVignettes = new AnchorPane();
        apVisuPlan = new AnchorPane();
        apVisuCarte = new AnchorPane();
        apVisuCarte.setTranslateZ(2);
        apVisuPlan.setTranslateZ(2);
        apVisuComboMenu = new AnchorPane();
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().add(ivVisualisation);
        apVisualisation.getChildren().addAll(lblTxtTitre, imgBoussole, imgAiguille, ivMasque, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, paneFondSuivant, paneFondPrecedent, apAfficheBarrePersonnalisee, apVisuBoutonVisiteAuto);
        paneFondPrecedent.setPrefWidth(64);
        paneFondPrecedent.setPrefHeight(64);
        paneFondSuivant.setPrefWidth(64);
        paneFondSuivant.setPrefHeight(64);
        paneFondPrecedent.setMaxWidth(64);
        paneFondPrecedent.setMaxHeight(64);
        paneFondSuivant.setMaxWidth(64);
        paneFondSuivant.setMaxHeight(64);
        paneFondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        paneFondPrecedent.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - paneFondPrecedent.getPrefHeight()) / 2);
        paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - paneFondPrecedent.getPrefWidth()));
        paneFondSuivant.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - paneFondSuivant.getPrefHeight()) / 2);
        paneFondSuivant.setVisible(isbSuivantPrecedent());
        paneFondPrecedent.setVisible(isbSuivantPrecedent());
        apAfficheDiapo = new AnchorPane();
        apAfficheDiapo.setTranslateZ(3);
        apAfficheDiapo.setPrefWidth(ivVisualisation.getFitWidth());
        apAfficheDiapo.setPrefHeight(ivVisualisation.getFitHeight());
        apAfficheDiapo.setLayoutX(LX);
        apAfficheDiapo.setLayoutY(20);
        apAfficheDiapo.setTranslateZ(10);
        apAfficheDiapo.setVisible(false);
        ivDiapo = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/testImage.png"));
        ivDiapo.setPreserveRatio(false);
        ivDiapo.setTranslateZ(4);
        ivDiapo.setFitHeight(ivVisualisation.getFitHeight() * 0.8);
        ivDiapo.setFitWidth(ivVisualisation.getFitWidth() * 0.8);
        ivDiapo.setLayoutX((ivVisualisation.getFitWidth() - ivDiapo.getFitWidth()) / 2 + LX);
        ivDiapo.setLayoutY((ivVisualisation.getFitHeight() - ivDiapo.getFitHeight()) / 2 + 20);
        ivDiapo.setVisible(false);
        apVisualisation.getChildren().addAll(apAfficheDiapo, ivDiapo);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();

        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        double largeur = vbOutils.getPrefWidth();

        /*
         * *****************************************
         *     Pannels d'outils 
         * *****************************************
         */
        AnchorPane apParametresTheme = new AnchorPane();
        AnchorPane apTHEME = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.theme"), apParametresTheme, largeur, true).getApPaneOutil());

        cpCouleurFondTheme = new ColorPicker(couleurFondTheme);
        cpCouleurTexteTheme = new ColorPicker(couleurTexteTheme);
        slOpaciteTheme = new Slider(0, 1, opaciteTheme);
        Label lblCouleurFondTheme = new Label(rbLocalisation.getString("interface.couleurFondTheme"));
        lblCouleurFondTheme.setLayoutX(10);
        lblCouleurFondTheme.setLayoutY(10);
        cpCouleurFondTheme.setLayoutX(200);
        cpCouleurFondTheme.setLayoutY(10);
        Label lblCouleurTexteTheme = new Label(rbLocalisation.getString("interface.couleurTexteTheme"));
        lblCouleurTexteTheme.setLayoutX(10);
        lblCouleurTexteTheme.setLayoutY(40);
        cpCouleurTexteTheme.setLayoutX(200);
        cpCouleurTexteTheme.setLayoutY(40);
        Label lblOpaciteTheme = new Label(rbLocalisation.getString("interface.opaciteTheme"));
        lblOpaciteTheme.setLayoutX(10);
        lblOpaciteTheme.setLayoutY(70);
        slOpaciteTheme.setLayoutX(200);
        slOpaciteTheme.setLayoutY(70);
        Label lblPoliceTheme = new Label(rbLocalisation.getString("interface.policeTheme"));
        lblPoliceTheme.setLayoutX(10);
        lblPoliceTheme.setLayoutY(100);
        cbPoliceTheme = new ComboBox(strListePolices);
        cbPoliceTheme.setValue(getStrTitrePoliceNom());
        cbPoliceTheme.setLayoutX(200);
        cbPoliceTheme.setLayoutY(100);
        cbPoliceTheme.setMaxWidth(135);
        cbSuivantPrecedent = new CheckBox(rbLocalisation.getString("interface.SuivantPrecedent"));
        cbSuivantPrecedent.setLayoutX(10);
        cbSuivantPrecedent.setLayoutY(135);
        apParametresTheme.setPrefHeight(160);
        apParametresTheme.getChildren().addAll(
                lblCouleurFondTheme, cpCouleurFondTheme,
                lblCouleurTexteTheme, cpCouleurTexteTheme,
                lblOpaciteTheme, slOpaciteTheme,
                lblPoliceTheme, cbPoliceTheme,
                cbSuivantPrecedent
        );

        /*
         * *****************************************
         *     Panel Titre 
         * ****************************************
         */
        AnchorPane apTitre = new AnchorPane();
        apTitre.setPrefHeight(380);
        PaneOutil poTitre = new PaneOutil(rbLocalisation.getString("interface.styleTitre"), apTitre, largeur);
        AnchorPane apTIT = new AnchorPane(poTitre.getApPaneOutil());
        cbAfficheTitre = new CheckBox(rbLocalisation.getString("interface.afficheTitre"));
        cbAfficheTitre.setSelected(isbAfficheTitre());
        cbAfficheTitre.setLayoutX(10);
        cbAfficheTitre.setLayoutY(15);
        poTitre.setbValide(isbAfficheTitre());
        cbTitreVisite = new CheckBox(rbLocalisation.getString("main.titreVisite"));
        cbTitrePanoramique = new CheckBox(rbLocalisation.getString("main.titrePano"));
        cbTitreVisite.setLayoutX(10);
        cbTitreVisite.setLayoutY(0);
        cbTitreVisite.setSelected(bTitreVisite);
        cbTitrePanoramique.setLayoutX(10);
        cbTitrePanoramique.setLayoutY(30);
        cbTitrePanoramique.setSelected(bTitrePanoramique);
        Label lblPosTitre = new Label(rbLocalisation.getString("editeurHTML.position"));
        lblPosTitre.setLayoutX(10);
        lblPosTitre.setLayoutY(60);
        rbLeftTitre = new RadioButton();
        rbCenterTitre = new RadioButton();
        rbRightTitre = new RadioButton();
        rbLeftTitre.setUserData("left");
        rbCenterTitre.setUserData("center");
        rbRightTitre.setUserData("right");

        rbLeftTitre.setToggleGroup(tgPosTitre);
        rbCenterTitre.setToggleGroup(tgPosTitre);
        rbRightTitre.setToggleGroup(tgPosTitre);
        int iPosX1 = 180;
        int iPosY1 = 60;
        rbLeftTitre.setLayoutX(iPosX1);
        rbCenterTitre.setLayoutX(iPosX1 + 20);
        rbRightTitre.setLayoutX(iPosX1 + 40);
        rbLeftTitre.setLayoutY(iPosY1);
        rbCenterTitre.setLayoutY(iPosY1);
        rbRightTitre.setLayoutY(iPosY1);

        rbCenterTitre.setSelected(true);
        Label lblDecalageTitre = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblDecalageTitre.setLayoutX(10);
        lblDecalageTitre.setLayoutY(90);
        bdfTitreDecalage = new BigDecimalField(new BigDecimal(getTitreDecalage()));
        bdfTitreDecalage.setLayoutX(180);
        bdfTitreDecalage.setLayoutY(87);
        bdfTitreDecalage.setMaxValue(new BigDecimal(1000));
        bdfTitreDecalage.setMinValue(new BigDecimal(0));
        bdfTitreDecalage.setMaxWidth(100);

        cbListePolicesTitre = new ComboBox(strListePolices);
        cbListePolicesTitre.setValue(getStrTitrePoliceNom());
        cbListePolicesTitre.setLayoutX(180);
        cbListePolicesTitre.setLayoutY(117);
        cbListePolicesTitre.setMaxWidth(135);

        Label lblChoixPoliceTitre = new Label(rbLocalisation.getString("interface.choixPolice"));
        lblChoixPoliceTitre.setLayoutX(10);
        lblChoixPoliceTitre.setLayoutY(120);
        Label lblChoixTailleTitre = new Label(rbLocalisation.getString("interface.choixTaillePolice"));
        lblChoixTailleTitre.setLayoutX(10);
        lblChoixTailleTitre.setLayoutY(150);
        slTaillePoliceTitre = new Slider(8, 72, 12);
        slTaillePoliceTitre.setLayoutX(180);
        slTaillePoliceTitre.setLayoutY(150);

        Label lblChoixCouleurTitre = new Label(rbLocalisation.getString("interface.choixCouleur"));
        lblChoixCouleurTitre.setLayoutX(10);
        lblChoixCouleurTitre.setLayoutY(180);
        cpCouleurTitre = new ColorPicker(Color.valueOf(getStrCouleurTitre()));
        cpCouleurTitre.setLayoutX(180);
        cpCouleurTitre.setLayoutY(178);
        Label lblChoixCouleurFondTitre = new Label(rbLocalisation.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondTitre.setLayoutX(10);
        lblChoixCouleurFondTitre.setLayoutY(210);
        cpCouleurFondTitre = new ColorPicker(Color.valueOf(getStrCouleurFondTitre()));
        cpCouleurFondTitre.setLayoutX(180);
        cpCouleurFondTitre.setLayoutY(208);

        Label lblChoixOpacite = new Label(rbLocalisation.getString("interface.choixOpaciteTitre"));
        lblChoixOpacite.setLayoutX(10);
        lblChoixOpacite.setLayoutY(240);
        slOpaciteTitre = new Slider(0, 1, getTitreOpacite());
        slOpaciteTitre.setLayoutX(180);
        slOpaciteTitre.setLayoutY(240);
        Label lblChoixTaille = new Label(rbLocalisation.getString("interface.choixTailleTitre"));
        lblChoixTaille.setLayoutX(10);
        lblChoixTaille.setLayoutY(270);
        slTailleTitre = new Slider(5, 100, getTitreTaille());
        slTailleTitre.setLayoutX(180);
        slTailleTitre.setLayoutY(270);
        cbTitreAdapte = new CheckBox(rbLocalisation.getString("interface.choixTitreAdapte"));
        cbTitreAdapte.setLayoutX(80);
        cbTitreAdapte.setLayoutY(300);
        AnchorPane apTit1 = new AnchorPane();
        apTit1.setLayoutY(45);
        apTit1.disableProperty().bind(cbAfficheTitre.selectedProperty().not());
        apTit1.getChildren().addAll(
                cbTitreVisite, cbTitrePanoramique,
                lblPosTitre, rbLeftTitre, rbCenterTitre, rbRightTitre,
                lblDecalageTitre, bdfTitreDecalage,
                lblChoixPoliceTitre, cbListePolicesTitre,
                lblChoixTailleTitre, slTaillePoliceTitre,
                lblChoixCouleurTitre, cpCouleurTitre,
                lblChoixCouleurFondTitre, cpCouleurFondTitre,
                lblChoixOpacite, slOpaciteTitre,
                lblChoixTaille, slTailleTitre,
                cbTitreAdapte
        );
        apTitre.getChildren().addAll(
                apTit1, cbAfficheTitre
        );
        /*
         * *****************************************
         *     Panel Fenetre Info
         * ****************************************
         */
        apFenetreAfficheInfo.setVisible(false);
        lblFenetreURL.setVisible(false);
        AnchorPane apFenetreInfo = new AnchorPane();
        apFenetreInfo.setPrefHeight(370);
        PaneOutil poEcran1 = new PaneOutil(rbLocalisation.getString("interface.styleFenetreInfo"), apFenetreInfo, largeur);
        AnchorPane apECR1 = new AnchorPane(poEcran1.getApPaneOutil());

        CheckBox cbAfficheFenetreInfo = new CheckBox(rbLocalisation.getString("interface.afficheFenetreInfo"));
        cbAfficheFenetreInfo.setSelected(bAfficheFenetreInfo);
        cbAfficheFenetreInfo.setLayoutX(10);
        cbAfficheFenetreInfo.setLayoutY(15);
        cbAfficheFenetreInfo.setVisible(false);
        cbFenetreInfoPersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreInfoPersonnalise"));
        cbFenetreInfoPersonnalise.setSelected(isbFenetreInfoPersonnalise());
        cbFenetreInfoPersonnalise.setLayoutX(10);
        cbFenetreInfoPersonnalise.setLayoutY(15);
        poEcran1.setbValide(isbFenetreAidePersonnalise() || isbFenetreInfoPersonnalise());
        AnchorPane apFenetreInfoPers = new AnchorPane();
        apFenetreInfoPers.setLayoutY(45);
        apFenetreInfoPers.setDisable(!isbFenetreInfoPersonnalise());
        Line ligne1 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne1.setStrokeWidth(0.2);

        Label lblFenetreInfoImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreInfoImage.setLayoutX(20);
        lblFenetreInfoImage.setLayoutY(5);
        tfFenetreInfoImage = new TextField();
        tfFenetreInfoImage.setLayoutX(40);
        tfFenetreInfoImage.setLayoutY(25);
        tfFenetreInfoImage.setPrefWidth(250);
        Button btnFenetreInfo = new Button("...");
        btnFenetreInfo.setLayoutX(300);
        btnFenetreInfo.setLayoutY(25);
        Label lblFenetreInfoTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreInfoTaille.setLayoutX(20);
        lblFenetreInfoTaille.setLayoutY(55);
        slFenetreInfoTaille = new Slider(25, 200, getFenetreInfoTaille());
        slFenetreInfoTaille.setLayoutX(140);
        slFenetreInfoTaille.setLayoutY(55);
        Label lblFenetreInfoOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreInfoOpacite.setLayoutX(20);
        lblFenetreInfoOpacite.setLayoutY(85);
        slFenetreInfoOpacite = new Slider(0, 1, getFenetreInfoOpacite());
        slFenetreInfoOpacite.setLayoutX(140);
        slFenetreInfoOpacite.setLayoutY(85);
        Label lblFenetreInfoPosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreInfoPosX.setLayoutX(20);
        lblFenetreInfoPosX.setLayoutY(120);
        bdfFenetreInfoPosX = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosX()));
        bdfFenetreInfoPosX.setLayoutX(100);
        bdfFenetreInfoPosX.setLayoutY(115);
        bdfFenetreInfoPosX.setPrefWidth(60);
        Label lblFenetreInfoPosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreInfoPosY.setLayoutX(180);
        lblFenetreInfoPosY.setLayoutY(120);
        bdfFenetreInfoPosY = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosY()));
        bdfFenetreInfoPosY.setLayoutX(260);
        bdfFenetreInfoPosY.setLayoutY(115);
        bdfFenetreInfoPosY.setPrefWidth(60);
        Label lblFenetreInfoURL = new Label("URL");
        lblFenetreInfoURL.setLayoutX(20);
        lblFenetreInfoURL.setLayoutY(170);
        tfFenetreURL = new TextField();
        tfFenetreURL.setLayoutX(120);
        tfFenetreURL.setLayoutY(165);
        tfFenetreURL.setPrefWidth(210);
        Label lblFenetreInfoTexteURL = new Label(rbLocalisation.getString("interface.fenetreLibelleURL"));
        lblFenetreInfoTexteURL.setLayoutX(20);
        lblFenetreInfoTexteURL.setLayoutY(200);
        tfFenetreTexteURL = new TextField();
        tfFenetreTexteURL.setLayoutX(120);
        tfFenetreTexteURL.setLayoutY(195);
        tfFenetreTexteURL.setPrefWidth(210);

        Label lblFenetrePoliceTaille = new Label(rbLocalisation.getString("interface.fenetrePoliceTaille"));
        lblFenetrePoliceTaille.setLayoutX(20);
        lblFenetrePoliceTaille.setLayoutY(230);
        slFenetrePoliceTaille = new Slider(7, 48, getFenetrePoliceTaille());
        slFenetrePoliceTaille.setLayoutX(140);
        slFenetrePoliceTaille.setLayoutY(230);

        Label lblFenetreURLCouleur = new Label(rbLocalisation.getString("interface.fenetreURLChoixCouleur"));
        lblFenetreURLCouleur.setLayoutX(20);
        lblFenetreURLCouleur.setLayoutY(260);
        cpFenetreURLCouleur = new ColorPicker(Color.valueOf(getStrFenetreURLCouleur()));
        cpFenetreURLCouleur.setLayoutX(200);
        cpFenetreURLCouleur.setLayoutY(256);

        Label lblFenetreURLPosX = new Label(rbLocalisation.getString("interface.fenetrePosX") + " URL");
        lblFenetreURLPosX.setLayoutX(20);
        lblFenetreURLPosX.setLayoutY(295);

        bdfFenetreURLPosX = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosX()));
        bdfFenetreURLPosX.setLayoutX(110);
        bdfFenetreURLPosX.setLayoutY(290);
        bdfFenetreURLPosX.setPrefWidth(60);
        Label lblFenetreURLPosY = new Label(rbLocalisation.getString("interface.fenetrePosY") + " URL");
        lblFenetreURLPosY.setLayoutX(185);
        lblFenetreURLPosY.setLayoutY(295);
        bdfFenetreURLPosY = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosY()));
        bdfFenetreURLPosY.setLayoutX(275);
        bdfFenetreURLPosY.setLayoutY(290);
        bdfFenetreURLPosY.setPrefWidth(60);

        apFenetreInfoPers.getChildren().addAll(
                ligne1,
                lblFenetreInfoImage, tfFenetreInfoImage, btnFenetreInfo,
                lblFenetreInfoTaille, slFenetreInfoTaille,
                lblFenetreInfoOpacite, slFenetreInfoOpacite,
                lblFenetreInfoPosX, bdfFenetreInfoPosX,
                lblFenetreInfoPosY, bdfFenetreInfoPosY,
                lblFenetreInfoURL, tfFenetreURL,
                lblFenetreInfoTexteURL, tfFenetreTexteURL,
                lblFenetrePoliceTaille, slFenetrePoliceTaille,
                lblFenetreURLCouleur, cpFenetreURLCouleur,
                lblFenetreURLPosX, bdfFenetreURLPosX,
                lblFenetreURLPosY, bdfFenetreURLPosY
        );

        apFenetreInfo.getChildren().addAll(
                cbAfficheFenetreInfo,
                cbFenetreInfoPersonnalise,
                apFenetreInfoPers
        );
        apFenetreInfo.visibleProperty().addListener((ov, av, nv) -> {
            cbAfficheFenetreInfo.setSelected(nv);
        });
        /*
         * *****************************************
         *     Panel Fenetre Aide
         * ****************************************
         */
        apFenetreAfficheInfo.setVisible(false);
        AnchorPane apFenetreAide = new AnchorPane();
        apFenetreAide.setPrefHeight(200);
        PaneOutil poEcran2 = new PaneOutil(rbLocalisation.getString("interface.styleFenetreAide"), apFenetreAide, largeur);
        AnchorPane apECR2 = new AnchorPane(poEcran2.getApPaneOutil());

        CheckBox cbAfficheFenetreAide = new CheckBox(rbLocalisation.getString("interface.afficheFenetreAide"));
        cbAfficheFenetreAide.setSelected(bAfficheFenetreAide);
        cbAfficheFenetreAide.setLayoutX(10);
        cbAfficheFenetreAide.setLayoutY(300);
        cbAfficheFenetreAide.setVisible(false);

        cbFenetreAidePersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreAidePersonnalise"));
        cbFenetreAidePersonnalise.setSelected(isbFenetreAidePersonnalise());
        cbFenetreAidePersonnalise.setLayoutX(10);
        cbFenetreAidePersonnalise.setLayoutY(15);
        AnchorPane apFenetreAidePers = new AnchorPane();
        apFenetreAidePers.setLayoutY(45);
        apFenetreAidePers.setDisable(!isbFenetreAidePersonnalise());
        Line ligne3 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne3.setStrokeWidth(0.2);

        Label lblFenetreAideImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreAideImage.setLayoutX(20);
        lblFenetreAideImage.setLayoutY(5);
        tfFenetreAideImage = new TextField();
        tfFenetreAideImage.setLayoutX(40);
        tfFenetreAideImage.setLayoutY(25);
        tfFenetreAideImage.setPrefWidth(250);
        Button btnFenetreAide = new Button("...");
        btnFenetreAide.setLayoutX(300);
        btnFenetreAide.setLayoutY(25);
        Label lblFenetreAideTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreAideTaille.setLayoutX(20);
        lblFenetreAideTaille.setLayoutY(55);
        slFenetreAideTaille = new Slider(50, 200, getFenetreAideTaille());
        slFenetreAideTaille.setLayoutX(140);
        slFenetreAideTaille.setLayoutY(55);
        Label lblFenetreAideOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreAideOpacite.setLayoutX(20);
        lblFenetreAideOpacite.setLayoutY(85);
        slFenetreAideOpacite = new Slider(0, 1, getFenetreAideOpacite());
        slFenetreAideOpacite.setLayoutX(140);
        slFenetreAideOpacite.setLayoutY(85);
        Label lblFenetreAidePosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreAidePosX.setLayoutX(20);
        lblFenetreAidePosX.setLayoutY(120);
        bdfFenetreAidePosX = new BigDecimalField(BigDecimal.valueOf(getFenetreAidePosX()));
        bdfFenetreAidePosX.setLayoutX(100);
        bdfFenetreAidePosX.setLayoutY(115);
        bdfFenetreAidePosX.setPrefWidth(60);
        Label lblFenetreAidePosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreAidePosY.setLayoutX(180);
        lblFenetreAidePosY.setLayoutY(120);
        bdfFenetreAidePosY = new BigDecimalField(BigDecimal.valueOf(getFenetreAidePosY()));
        bdfFenetreAidePosY.setLayoutX(260);
        bdfFenetreAidePosY.setLayoutY(115);
        bdfFenetreAidePosY.setPrefWidth(60);

        Line ligne4 = new Line(0, 150, vbOutils.getPrefWidth(), 150);
        ligne4.setStrokeWidth(0.2);

        apFenetreAidePers.getChildren().addAll(
                ligne3,
                lblFenetreAideImage, tfFenetreAideImage, btnFenetreAide,
                lblFenetreAideTaille, slFenetreAideTaille,
                lblFenetreAideOpacite, slFenetreAideOpacite,
                lblFenetreAidePosX, bdfFenetreAidePosX,
                lblFenetreAidePosY, bdfFenetreAidePosY,
                ligne4
        );

        apFenetreAide.getChildren().addAll(
                cbAfficheFenetreAide,
                cbFenetreAidePersonnalise,
                apFenetreAidePers
        );
        apFenetreAide.visibleProperty().addListener((ov, av, nv) -> {
            cbAfficheFenetreAide.setSelected(nv);
        });
        /*
         * *****************************************
         *     Panel Diaporama
         * ****************************************
         */
        AnchorPane apDiapo = new AnchorPane();
        AnchorPane apDIA = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.diaporama"), apDiapo, largeur).getApPaneOutil());
        apDiapo.setLayoutY(40);
        apDiapo.setLayoutX(10);
        apDiapo.setPrefHeight(100);
        CheckBox cbVisualiseDiapo = new CheckBox(rbLocalisation.getString("interface.visualiseDiaporama"));
        cbVisualiseDiapo.setLayoutX(10);
        cbVisualiseDiapo.setLayoutY(10);
        cbVisualiseDiapo.setSelected(false);
        Label lblChoixCouleurDiaporama = new Label(rbLocalisation.getString("interface.choixCouleurDiaporama"));
        lblChoixCouleurDiaporama.setLayoutX(10);
        lblChoixCouleurDiaporama.setLayoutY(40);
        cpCouleurDiaporama = new ColorPicker(Color.valueOf(getStrCouleurDiaporama()));
        cpCouleurDiaporama.setLayoutX(180);
        cpCouleurDiaporama.setLayoutY(38);

        Label lblChoixOpaciteDiaporama = new Label(rbLocalisation.getString("interface.choixOpaciteDiaporama"));
        lblChoixOpaciteDiaporama.setLayoutX(10);
        lblChoixOpaciteDiaporama.setLayoutY(70);
        slOpaciteDiaporama = new Slider(0, 1, getDiaporamaOpacite());
        slOpaciteDiaporama.setLayoutX(180);
        slOpaciteDiaporama.setLayoutY(70);

        apDiapo.getChildren().addAll(
                cbVisualiseDiapo,
                lblChoixCouleurDiaporama, cpCouleurDiaporama,
                lblChoixOpaciteDiaporama, slOpaciteDiaporama
        );

        apDiapo.visibleProperty().addListener((ov, av, nv) -> {
            cbVisualiseDiapo.setSelected(nv);
        });

        /*
         * *************************************************
         *     Panels HotSpots 
         * *************************************************
         */
        /*
         * *************************************************
         *     Panel HotSpots Panoramiques
         * *************************************************
        
         */
        AnchorPane apHotSpots1 = new AnchorPane();
        AnchorPane apHS1 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSPanoramique"), apHotSpots1, largeur).getApPaneOutil());

        apHotSpots1.setPrefHeight(35.d * ((int) (iNombreHotSpots / 9 + 1)) + 140);
        apHotSpots1.setLayoutX(10);
        apHotSpots1.setLayoutY(40);
        //apHotSpots.setStyle("-fx-background-color : #fff;-fx-font-variant: small-caps;");
        apHotSpots1.setPadding(new Insets(5));
        int i = 0;
        double xHS;
        double yHS = 25;
        for (String strNomImage : strListeHotSpots) {
            String strExtension = strNomImage.substring(strNomImage.length() - 3, strNomImage.length());
            Pane paneFond = new Pane();
            ivHotspots[i] = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomImage, -1, 30, true, true, true));

            int iCol = i % 9;
            int iRow = i / 9;
            xHS = iCol * 40 + 5;
            yHS = iRow * 35 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpotPanoramique);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                apVisualisation.getChildren().remove(ivHotSpotHTML);
                setStrStyleHotSpots(strNomImage);
                strTypeHS = strExtension.toLowerCase();
                cpCouleurHotspotsPanoramique.setDisable(strTypeHS.equals("gif"));

                strNomfichierHS = strNomImage;
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());

                changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());

                changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
                changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
                ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
                ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
                ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
            });
            paneFond.getChildren().add(ivHotspots[i]);
            apHotSpots1.getChildren().add(paneFond);
            i++;

        }
        cpCouleurHotspotsPanoramique = new ColorPicker(couleurHotspots);
        Label lblCouleurHotspot = new Label(rbLocalisation.getString("interface.couleurHS"));
        lblCouleurHotspot.setLayoutX(20);
        lblCouleurHotspot.setLayoutY(yHS + 50);
        cpCouleurHotspotsPanoramique.setLayoutX(200);
        cpCouleurHotspotsPanoramique.setLayoutY(yHS + 50);
        Label lblTailleHotspots = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspots.setLayoutX(20);
        lblTailleHotspots.setLayoutY(yHS + 90);
        slTailleHotspotsPanoramique = new Slider(15, 100, getiTailleHotspotsPanoramique());
        slTailleHotspotsPanoramique.setLayoutX(200);
        slTailleHotspotsPanoramique.setLayoutY(yHS + 90);
        apHotSpots1.getChildren().addAll(
                lblCouleurHotspot, cpCouleurHotspotsPanoramique,
                lblTailleHotspots, slTailleHotspotsPanoramique
        );
        /*
         * *************************************************
         *     Panel HotSpots Photos
         * *************************************************
        
         */
        AnchorPane apHotSpots2 = new AnchorPane();
        AnchorPane apHS2 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSPhoto"), apHotSpots2, largeur).getApPaneOutil());

        apHotSpots2.setPrefHeight(35.d * (int) (iNombreHotSpotsPhoto / 9 + 1) + 120);
        apHotSpots2.setLayoutX(10);
        apHotSpots2.setLayoutY(40);
        //apHotSpots.setStyle("-fx-background-color : #fff;-fx-font-variant: small-caps;");
        apHotSpots2.setPadding(new Insets(5));
        i = 0;
        for (String strNomImage : strListeHotSpotsPhoto) {
            String strExtension = strNomImage.substring(strNomImage.length() - 3, strNomImage.length());
            Pane paneFond = new Pane();
            ivHotspotsPhoto[i] = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol = i % 9;
            int iRow = i / 9;
            xHS = iCol * 40 + 5;
            yHS = (iRow) * 35 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpotPanoramique);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                apVisualisation.getChildren().remove(ivHotSpotHTML);
                strTypeHSImage = strExtension.toLowerCase();
                cpCouleurHotspotsPhoto.setDisable(strTypeHSImage.equals("gif"));
                strNomfichierHSImage = strNomImage;
                setStrStyleHotSpotImages(strNomImage);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
                changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
                ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
                ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
                ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
            });
            paneFond.getChildren().add(ivHotspotsPhoto[i]);
            apHotSpots2.getChildren().add(paneFond);
            i++;

        }
        cpCouleurHotspotsPhoto = new ColorPicker(couleurHotspotsPhoto);
        Label lblCouleurHotspotPhoto = new Label(rbLocalisation.getString("interface.couleurHSPhoto"));
        lblCouleurHotspotPhoto.setLayoutX(20);
        lblCouleurHotspotPhoto.setLayoutY(yHS + 50);
        cpCouleurHotspotsPhoto.setLayoutX(200);
        cpCouleurHotspotsPhoto.setLayoutY(yHS + 50);
        Label lblTailleHotspotsImage = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspotsImage.setLayoutX(20);
        lblTailleHotspotsImage.setLayoutY(yHS + 90);
        slTailleHotspotsImage = new Slider(15, 100, getiTailleHotspotsImage());
        slTailleHotspotsImage.setLayoutX(200);
        slTailleHotspotsImage.setLayoutY(yHS + 90);
        apHotSpots2.getChildren().addAll(
                lblCouleurHotspotPhoto, cpCouleurHotspotsPhoto,
                lblTailleHotspotsImage, slTailleHotspotsImage
        );
        /*
         * *************************************************
         *     Panel HotSpots Panoramiques
         * *************************************************
        
         */
        AnchorPane apHotSpots3 = new AnchorPane();
        AnchorPane apHS3 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSHTML"), apHotSpots3, largeur).getApPaneOutil());

        apHotSpots3.setPrefHeight(35.d * ((int) (iNombreHotSpotsHTML / 9 + 1)) + 140);
        apHotSpots3.setLayoutX(10);
        apHotSpots3.setLayoutY(40);
        //apHotSpots.setStyle("-fx-background-color : #fff;-fx-font-variant: small-caps;");
        apHotSpots3.setPadding(new Insets(5));
        i = 0;
        for (String strNomHTML : strListeHotSpotsHTML) {
            String strExtension = strNomHTML.substring(strNomHTML.length() - 3, strNomHTML.length());
            Pane paneFond = new Pane();
            ivHotspotsHTML[i] = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomHTML, -1, 30, true, true, true));
            int iCol = i % 9;
            int iRow = i / 9;
            xHS = iCol * 40 + 5;
            yHS = (iRow) * 35 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpotPanoramique);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                apVisualisation.getChildren().remove(ivHotSpotHTML);
                strTypeHSHTML = strExtension.toLowerCase();
                cpCouleurHotspotsHTML.setDisable(strTypeHSHTML.equals("gif"));
                strNomfichierHSHTML = strNomHTML;
                setStrStyleHotSpotHTML(strNomHTML);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
                changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
                ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
                ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
                ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
            });
            paneFond.getChildren().add(ivHotspotsHTML[i]);
            apHotSpots3.getChildren().add(paneFond);
            i++;

        }
        cpCouleurHotspotsHTML = new ColorPicker(couleurHotspotsHTML);
        Label lblCouleurHotspotHTML = new Label(rbLocalisation.getString("interface.couleurHSHTML"));
        lblCouleurHotspotHTML.setLayoutX(20);
        lblCouleurHotspotHTML.setLayoutY(yHS + 50);
        cpCouleurHotspotsHTML.setLayoutX(200);
        cpCouleurHotspotsHTML.setLayoutY(yHS + 50);
        Label lblTailleHotspotsHTML = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspotsHTML.setLayoutX(20);
        lblTailleHotspotsHTML.setLayoutY(yHS + 90);
        slTailleHotspotsHTML = new Slider(15, 100, getiTailleHotspotsHTML());
        slTailleHotspotsHTML.setLayoutX(200);
        slTailleHotspotsHTML.setLayoutY(yHS + 90);
        apHotSpots3.getChildren().addAll(
                lblCouleurHotspotHTML, cpCouleurHotspotsHTML,
                lblTailleHotspotsHTML, slTailleHotspotsHTML
        );


        /*        
         * ***************************************
         *     Panel Barre Navigation Classique 
         * ***************************************
         */
        AnchorPane apBarreClassique = new AnchorPane();
        PaneOutil poClass = new PaneOutil(rbLocalisation.getString("interface.barreBoutons"), apBarreClassique, largeur);
        AnchorPane apCLASS = new AnchorPane(poClass.getApPaneOutil());
        apBarreClassique.setLayoutY(40);
        apBarreClassique.setPrefHeight(420);
        apBarreClassique.setMinWidth(vbOutils.getPrefWidth() - 20);

        cbBarreClassiqueVisible = new CheckBox(rbLocalisation.getString("interface.barreVisible"));
        cbBarreClassiqueVisible.setLayoutX(10);
        cbBarreClassiqueVisible.setLayoutY(5);
        cbBarreClassiqueVisible.setSelected(true);
        poClass.setbValide(true);
        Label lblStyleBarreClassique = new Label(rbLocalisation.getString("interface.style"));
        lblStyleBarreClassique.setLayoutX(10);
        lblStyleBarreClassique.setLayoutY(60);

        cblisteStyleBarreClassique = new ComboBox();
        strListeStyles.stream().forEach((nomStyle) -> {
            cblisteStyleBarreClassique.getItems().add(nomStyle);
        });
        cblisteStyleBarreClassique.setLayoutX(150);
        cblisteStyleBarreClassique.setLayoutY(70);
        cblisteStyleBarreClassique.setValue(getStyleBarreClassique());
        cpCouleurBarreClassique = new ColorPicker(couleurBarreClassique);
        Label lblCouleurBarreClassique = new Label(rbLocalisation.getString("interface.couleurBarre"));
        lblCouleurBarreClassique.setLayoutX(20);
        lblCouleurBarreClassique.setLayoutY(110);
        cpCouleurBarreClassique.setLayoutX(150);
        cpCouleurBarreClassique.setLayoutY(105);

        rbTopLeftBarreClassique = new RadioButton();
        rbTopCenterBarreClassique = new RadioButton();
        rbTopRightBarreClassique = new RadioButton();
        rbMiddleLeftBarreClassique = new RadioButton();
        rbMiddleRightBarreClassique = new RadioButton();
        rbBottomLeftBarreClassique = new RadioButton();
        rbBottomCenterBarreClassique = new RadioButton();
        rbBottomRightBarreClassique = new RadioButton();

        rbTopLeftBarreClassique.setUserData("top:left");
        rbTopCenterBarreClassique.setUserData("top:center");
        rbTopRightBarreClassique.setUserData("top:right");
        rbMiddleLeftBarreClassique.setUserData("middle:left");
        rbMiddleRightBarreClassique.setUserData("middle:right");
        rbBottomLeftBarreClassique.setUserData("bottom:left");
        rbBottomCenterBarreClassique.setUserData("bottom:center");
        rbBottomRightBarreClassique.setUserData("bottom:right");

        rbTopLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbTopCenterBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbTopRightBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbMiddleLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbMiddleRightBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomCenterBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomRightBarreClassique.setToggleGroup(tgPositionBarreClassique);

        int iPosX = 250;
        int iPosY = 140;

        rbTopLeftBarreClassique.setLayoutX(iPosX);
        rbTopCenterBarreClassique.setLayoutX(iPosX + 20);
        rbTopRightBarreClassique.setLayoutX(iPosX + 40);
        rbTopLeftBarreClassique.setLayoutY(iPosY);
        rbTopCenterBarreClassique.setLayoutY(iPosY);
        rbTopRightBarreClassique.setLayoutY(iPosY);

        rbMiddleLeftBarreClassique.setLayoutX(iPosX);
        rbMiddleRightBarreClassique.setLayoutX(iPosX + 40);
        rbMiddleLeftBarreClassique.setLayoutY(iPosY + 20);
        rbMiddleRightBarreClassique.setLayoutY(iPosY + 20);

        rbBottomLeftBarreClassique.setLayoutX(iPosX);
        rbBottomCenterBarreClassique.setLayoutX(iPosX + 20);
        rbBottomRightBarreClassique.setLayoutX(iPosX + 40);
        rbBottomLeftBarreClassique.setLayoutY(iPosY + 40);
        rbBottomCenterBarreClassique.setLayoutY(iPosY + 40);
        rbBottomRightBarreClassique.setLayoutY(iPosY + 40);
        rbBottomCenterBarreClassique.setSelected(true);
        Label lblPositionBarreClassique = new Label(rbLocalisation.getString("interface.position"));
        lblPositionBarreClassique.setLayoutX(10);
        lblPositionBarreClassique.setLayoutY(140);

        Label lblOffsetXBarreClassique = new Label("dX :");
        lblOffsetXBarreClassique.setLayoutX(25);
        lblOffsetXBarreClassique.setLayoutY(205);
        Label lblOffsetYBarreClassique = new Label("dY :");
        lblOffsetYBarreClassique.setLayoutX(175);
        lblOffsetYBarreClassique.setLayoutY(205);
        bdfOffsetXBarreClassique = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXBarreClassique.setLayoutX(50);
        bdfOffsetXBarreClassique.setLayoutY(200);
        bdfOffsetXBarreClassique.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarreClassique.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarreClassique.setMaxWidth(100);
        bdfOffsetYBarreClassique = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYBarreClassique.setLayoutX(200);
        bdfOffsetYBarreClassique.setLayoutY(200);
        bdfOffsetYBarreClassique.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBarreClassique.setMinValue(new BigDecimal(-2000));
        bdfOffsetYBarreClassique.setMaxWidth(100);

        Label lblEspacementBarreClassique = new Label(rbLocalisation.getString("interface.espacementBoutons"));
        lblEspacementBarreClassique.setLayoutX(10);
        lblEspacementBarreClassique.setLayoutY(240);
        slEspacementBarreClassique = new Slider(4, 25, 4);
        slEspacementBarreClassique.setLayoutX(170);
        slEspacementBarreClassique.setLayoutY(240);

        cbDeplacementsBarreClassique = new CheckBox(rbLocalisation.getString("interface.deplacementsVisible"));
        cbZoomBarreClassique = new CheckBox(rbLocalisation.getString("interface.zoomVisible"));
        cbOutilsBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsVisible"));
        cbSourisBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsSouris"));
        cbRotationBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsRotation"));
        cbFSBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsFS"));
        Label lblVisibiliteBarreClassique = new Label(rbLocalisation.getString("interface.visibilite"));
        lblVisibiliteBarreClassique.setLayoutX(10);
        lblVisibiliteBarreClassique.setLayoutY(270);

        cbDeplacementsBarreClassique.setLayoutX(100);
        cbDeplacementsBarreClassique.setLayoutY(290);
        cbDeplacementsBarreClassique.setSelected(true);
        cbZoomBarreClassique.setLayoutX(100);
        cbZoomBarreClassique.setLayoutY(310);
        cbZoomBarreClassique.setSelected(true);
        cbOutilsBarreClassique.setLayoutX(100);
        cbOutilsBarreClassique.setLayoutY(330);
        cbOutilsBarreClassique.setSelected(true);
        cbFSBarreClassique.setLayoutX(150);
        cbFSBarreClassique.setLayoutY(350);
        cbFSBarreClassique.setSelected(true);
        cbRotationBarreClassique.setLayoutX(150);
        cbRotationBarreClassique.setLayoutY(370);
        cbRotationBarreClassique.setSelected(true);
        cbSourisBarreClassique.setLayoutX(150);
        cbSourisBarreClassique.setLayoutY(390);
        cbSourisBarreClassique.setSelected(true);
        AnchorPane apBarreClass = new AnchorPane();
        apBarreClass.disableProperty().bind(cbBarreClassiqueVisible.selectedProperty().not());
        apBarreClass.getChildren().addAll(
                lblStyleBarreClassique, cblisteStyleBarreClassique,
                lblCouleurBarreClassique, cpCouleurBarreClassique,
                lblPositionBarreClassique,
                rbTopLeftBarreClassique, rbTopCenterBarreClassique, rbTopRightBarreClassique,
                rbMiddleLeftBarreClassique, rbMiddleRightBarreClassique,
                rbBottomLeftBarreClassique, rbBottomCenterBarreClassique, rbBottomRightBarreClassique,
                lblOffsetXBarreClassique, bdfOffsetXBarreClassique, lblOffsetYBarreClassique, bdfOffsetYBarreClassique,
                lblEspacementBarreClassique, slEspacementBarreClassique,
                lblVisibiliteBarreClassique, cbDeplacementsBarreClassique, cbZoomBarreClassique, cbOutilsBarreClassique,
                cbFSBarreClassique, cbRotationBarreClassique, cbSourisBarreClassique);
        apBarreClassique.getChildren().addAll(
                apBarreClass,
                cbBarreClassiqueVisible
        );


        /*        
         * ***************************************
         *     Panel Barre Navigation Personnalisee 
         * ***************************************
         */
        AnchorPane apBarrePersonnalisee = new AnchorPane();
        PaneOutil poPers = new PaneOutil(rbLocalisation.getString("interface.barreBoutonsPersonalisee"), apBarrePersonnalisee, largeur);
        AnchorPane apPERS = new AnchorPane(poPers.getApPaneOutil());
        apBarrePersonnalisee.setLayoutY(40);
        apBarrePersonnalisee.setPrefHeight(680);
        apBarrePersonnalisee.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbBarrePersonnaliseeVisible = new CheckBox(rbLocalisation.getString("interface.barrePersonnaliseeVisible"));
        cbBarrePersonnaliseeVisible.setLayoutX(20);
        cbBarrePersonnaliseeVisible.setLayoutY(10);
        cbBarrePersonnaliseeVisible.setSelected(false);
        poPers.setbValide(false);
        btnEditerBarre = new Button(rbLocalisation.getString("interface.barrePresonnaliseeEditer"));
        btnEditerBarre.setLayoutX(300);
        btnEditerBarre.setLayoutY(10);
        btnEditerBarre.setDisable(true);
        Label lblLienBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeLien"));
        lblLienBarrePersonnalisee.setLayoutX(20);
        lblLienBarrePersonnalisee.setLayoutY(45);
        tfLienImageBarrePersonnalisee = new TextField();
        tfLienImageBarrePersonnalisee.setLayoutX(100);
        tfLienImageBarrePersonnalisee.setLayoutY(40);
        tfLienImageBarrePersonnalisee.setPrefWidth(200);
        tfLienImageBarrePersonnalisee.setDisable(true);
        Button btnLienBarrePersonnalisee = new Button("...");
        btnLienBarrePersonnalisee.setLayoutX(310);
        btnLienBarrePersonnalisee.setLayoutY(40);

        ivBarrePersonnalisee = new ImageView();
        Pane paneImageBarrePersonnalisee = new Pane(ivBarrePersonnalisee);
        paneImageBarrePersonnalisee.setLayoutX(110);
        paneImageBarrePersonnalisee.setLayoutY(70);
        paneImageBarrePersonnalisee.setPrefHeight(150);
        paneImageBarrePersonnalisee.setPrefWidth(150);
        rbTopLeftBarrePersonnalisee = new RadioButton();
        rbTopCenterBarrePersonnalisee = new RadioButton();
        rbTopRightBarrePersonnalisee = new RadioButton();
        rbMiddleLeftBarrePersonnalisee = new RadioButton();
        rbMiddleRightBarrePersonnalisee = new RadioButton();
        rbBottomLeftBarrePersonnalisee = new RadioButton();
        rbBottomCenterBarrePersonnalisee = new RadioButton();
        rbBottomRightBarrePersonnalisee = new RadioButton();

        rbTopLeftBarrePersonnalisee.setUserData("top:left");
        rbTopCenterBarrePersonnalisee.setUserData("top:center");
        rbTopRightBarrePersonnalisee.setUserData("top:right");
        rbMiddleLeftBarrePersonnalisee.setUserData("middle:left");
        rbMiddleRightBarrePersonnalisee.setUserData("middle:right");
        rbBottomLeftBarrePersonnalisee.setUserData("bottom:left");
        rbBottomCenterBarrePersonnalisee.setUserData("bottom:center");
        rbBottomRightBarrePersonnalisee.setUserData("bottom:right");

        rbTopLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbTopCenterBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbTopRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbMiddleLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbMiddleRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomCenterBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);

        int iPos1X = 250;
        int iPos1Y = 240;

        rbTopLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbTopCenterBarrePersonnalisee.setLayoutX(iPos1X + 20);
        rbTopRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbTopLeftBarrePersonnalisee.setLayoutY(iPos1Y);
        rbTopCenterBarrePersonnalisee.setLayoutY(iPos1Y);
        rbTopRightBarrePersonnalisee.setLayoutY(iPos1Y);

        rbMiddleLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbMiddleRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbMiddleLeftBarrePersonnalisee.setLayoutY(iPos1Y + 20);
        rbMiddleRightBarrePersonnalisee.setLayoutY(iPos1Y + 20);

        rbBottomLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbBottomCenterBarrePersonnalisee.setLayoutX(iPos1X + 20);
        rbBottomRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbBottomLeftBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomCenterBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomRightBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomRightBarrePersonnalisee.setSelected(true);
        Label lblPositionBarrePersonnalisee = new Label(rbLocalisation.getString("interface.position"));
        lblPositionBarrePersonnalisee.setLayoutX(20);
        lblPositionBarrePersonnalisee.setLayoutY(250);

        Label lblOffsetXBarrePersonnalisee = new Label("dX :");
        lblOffsetXBarrePersonnalisee.setLayoutX(25);
        lblOffsetXBarrePersonnalisee.setLayoutY(315);
        Label lblOffsetYBarrePersonnalisee = new Label("dY :");
        lblOffsetYBarrePersonnalisee.setLayoutX(175);
        lblOffsetYBarrePersonnalisee.setLayoutY(315);
        bdfOffsetXBarrePersonnalisee = new BigDecimalField(new BigDecimal(getOffsetXBarrePersonnalisee()));
        bdfOffsetXBarrePersonnalisee.setLayoutX(50);
        bdfOffsetXBarrePersonnalisee.setLayoutY(310);
        bdfOffsetXBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarrePersonnalisee.setMaxWidth(100);
        bdfOffsetYBarrePersonnalisee = new BigDecimalField(new BigDecimal(getOffsetYBarrePersonnalisee()));
        bdfOffsetYBarrePersonnalisee.setLayoutX(200);
        bdfOffsetYBarrePersonnalisee.setLayoutY(310);
        bdfOffsetYBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetYBarrePersonnalisee.setMaxWidth(100);
        Label lblTailleBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTaille"));
        lblTailleBarrePersonnalisee.setLayoutX(20);
        lblTailleBarrePersonnalisee.setLayoutY(350);
        sltailleBarrePersonnalisee = new Slider(10, 200, getTailleBarrePersonnalisee());
        sltailleBarrePersonnalisee.setLayoutX(150);
        sltailleBarrePersonnalisee.setLayoutY(350);

        Label lblTailleBarrePersonnaliseeIcones = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTailleIcones"));
        lblTailleBarrePersonnaliseeIcones.setLayoutX(20);
        lblTailleBarrePersonnaliseeIcones.setLayoutY(380);
        sltailleIconesBarrePersonnalisee = new Slider(10, 60, getTailleIconesBarrePersonnalisee());
        sltailleIconesBarrePersonnalisee.setLayoutX(150);
        sltailleIconesBarrePersonnalisee.setLayoutY(380);

        rbCouleurOrigineBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurOrigine"));
        rbCouleurOrigineBarrePersonnalisee.setLayoutX(20);
        rbCouleurOrigineBarrePersonnalisee.setLayoutY(410);
        rbCouleurOrigineBarrePersonnalisee.setSelected(true);
        rbCouleurOrigineBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurOrigineBarrePersonnalisee.setUserData(true);

        rbCouleurPersBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurPersonnalisee"));
        rbCouleurPersBarrePersonnalisee.setLayoutX(20);
        rbCouleurPersBarrePersonnalisee.setLayoutY(440);
        rbCouleurPersBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurPersBarrePersonnalisee.setUserData(false);

        cpCouleurBarrePersonnalisee = new ColorPicker(couleurBarrePersonnalisee);
        cpCouleurBarrePersonnalisee.setLayoutX(180);
        cpCouleurBarrePersonnalisee.setLayoutY(435);
        cpCouleurBarrePersonnalisee.setDisable(true);
        cbDeplacementsBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.deplacementsVisible"));
        cbZoomBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.zoomVisible"));
        cbSourisBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsSouris"));
        cbRotationBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsRotation"));
        cbFSBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsFS"));
        Label lblVisibiliteBarrePersonnalisee = new Label(rbLocalisation.getString("interface.visibilite"));
        lblVisibiliteBarrePersonnalisee.setLayoutX(20);
        lblVisibiliteBarrePersonnalisee.setLayoutY(480);

        cbDeplacementsBarrePersonnalisee.setLayoutX(100);
        cbDeplacementsBarrePersonnalisee.setLayoutY(500);
        cbDeplacementsBarrePersonnalisee.setSelected(true);
        cbZoomBarrePersonnalisee.setLayoutX(100);
        cbZoomBarrePersonnalisee.setLayoutY(520);
        cbZoomBarrePersonnalisee.setSelected(true);
        cbFSBarrePersonnalisee.setLayoutX(150);
        cbFSBarrePersonnalisee.setLayoutY(560);
        cbFSBarrePersonnalisee.setSelected(true);
        cbRotationBarrePersonnalisee.setLayoutX(150);
        cbRotationBarrePersonnalisee.setLayoutY(580);
        cbRotationBarrePersonnalisee.setSelected(true);
        cbSourisBarrePersonnalisee.setLayoutX(150);
        cbSourisBarrePersonnalisee.setLayoutY(600);
        cbSourisBarrePersonnalisee.setSelected(true);
        Label lblLien1BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "1");
        lblLien1BarrePersonnalisee.setLayoutX(20);
        lblLien1BarrePersonnalisee.setLayoutY(630);
        tfLien1BarrePersonnalisee = new TextField("");
        tfLien1BarrePersonnalisee.setPrefWidth(200);
        tfLien1BarrePersonnalisee.setLayoutX(100);
        tfLien1BarrePersonnalisee.setLayoutY(630);
        tfLien1BarrePersonnalisee.setDisable(true);
        Label lblLien2BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "2");
        lblLien2BarrePersonnalisee.setLayoutX(20);
        lblLien2BarrePersonnalisee.setLayoutY(660);
        tfLien2BarrePersonnalisee = new TextField("");
        tfLien2BarrePersonnalisee.setPrefWidth(200);
        tfLien2BarrePersonnalisee.setLayoutX(100);
        tfLien2BarrePersonnalisee.setLayoutY(660);
        tfLien2BarrePersonnalisee.setDisable(true);
        AnchorPane apBarrePers = new AnchorPane();
        apBarrePers.disableProperty().bind(cbBarrePersonnaliseeVisible.selectedProperty().not());
        apBarrePers.getChildren().addAll(
                btnEditerBarre,
                lblLienBarrePersonnalisee, tfLienImageBarrePersonnalisee, btnLienBarrePersonnalisee,
                paneImageBarrePersonnalisee,
                lblPositionBarrePersonnalisee,
                rbTopLeftBarrePersonnalisee, rbTopCenterBarrePersonnalisee, rbTopRightBarrePersonnalisee,
                rbMiddleLeftBarrePersonnalisee, rbMiddleRightBarrePersonnalisee,
                rbBottomLeftBarrePersonnalisee, rbBottomCenterBarrePersonnalisee, rbBottomRightBarrePersonnalisee,
                lblOffsetXBarrePersonnalisee, bdfOffsetXBarrePersonnalisee, lblOffsetYBarrePersonnalisee, bdfOffsetYBarrePersonnalisee,
                lblTailleBarrePersonnalisee, sltailleBarrePersonnalisee,
                lblTailleBarrePersonnaliseeIcones, sltailleIconesBarrePersonnalisee,
                rbCouleurOrigineBarrePersonnalisee,
                rbCouleurPersBarrePersonnalisee, cpCouleurBarrePersonnalisee,
                lblVisibiliteBarrePersonnalisee, cbDeplacementsBarrePersonnalisee, cbZoomBarrePersonnalisee,
                cbFSBarrePersonnalisee, cbRotationBarrePersonnalisee, cbSourisBarrePersonnalisee,
                lblLien1BarrePersonnalisee, tfLien1BarrePersonnalisee,
                lblLien2BarrePersonnalisee, tfLien2BarrePersonnalisee
        );
        apBarrePersonnalisee.getChildren().addAll(
                apBarrePers, cbBarrePersonnaliseeVisible
        );

        /*
         * ********************************************
         *      Panel Boussole 
         * ********************************************
         */
        AnchorPane apBoussole = new AnchorPane();
        PaneOutil poBouss = new PaneOutil(rbLocalisation.getString("interface.boussole"), apBoussole, largeur);
        AnchorPane apBOUSS = new AnchorPane(poBouss.getApPaneOutil());

        apBoussole.setLayoutY(40);
        apBoussole.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheBoussole = new CheckBox(rbLocalisation.getString("interface.affichageBoussole"));
        cbAfficheBoussole.setLayoutX(10);
        cbAfficheBoussole.setLayoutY(10);
        cbAfficheBoussole.setSelected(isbAfficheBoussole());
        poBouss.setbValide(false);

        AnchorPane apBouss1 = new AnchorPane();
        Label lblChoixBoussole = new Label(rbLocalisation.getString("interface.choixImgBoussole"));
        lblChoixBoussole.setLayoutX(10);
        lblChoixBoussole.setLayoutY(40);

        apBouss1.getChildren().add(lblChoixBoussole);

        int iNombreBoussoles = strListeBoussoles.size();
        ImageView[] ivBoussoles = new ImageView[iNombreBoussoles];
        i = 0;
        int iCol;
        int iRow;
        for (String strNomImage : strListeBoussoles) {
            Pane paneFond = new Pane();
            ivBoussoles[i] = new ImageView(new Image("file:" + strRepertBoussoles + File.separator + strNomImage, -1, 50, true, true, true));
            iCol = i % 4;
            iRow = i / 4;
            xHS = iCol * 60 + 95;
            yHS = iRow * 60 + 60;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setPrefHeight(60);
            paneFond.setPrefWidth(60);
            paneFond.setOpacity(1);
            paneFond.setUserData(strNomImage);
            paneFond.setStyle("-fx-background-color : rgba(255,255,255,0)");
            paneFond.setOnMouseClicked((me) -> {
                setStrImageBoussole((String) ((Pane) me.getSource()).getUserData());
                afficheBoussole();
            });
            paneFond.getChildren().add(ivBoussoles[i]);

            apBouss1.getChildren().add(paneFond);
            i++;

        }
        yHS += 60;
        Label lblPositBoussole = new Label(rbLocalisation.getString("interface.choixPositBoussole"));
        lblPositBoussole.setLayoutX(10);
        lblPositBoussole.setLayoutY(yHS);
        apBouss1.getChildren().add(lblPositBoussole);

        rbBoussTopLeft = new RadioButton();
        rbBoussTopRight = new RadioButton();
        rbBoussBottomLeft = new RadioButton();
        rbBoussBottomRight = new RadioButton();

        rbBoussTopLeft.setUserData("top:left");
        rbBoussTopRight.setUserData("top:right");
        rbBoussBottomLeft.setUserData("bottom:left");
        rbBoussBottomRight.setUserData("bottom:right");

        rbBoussTopLeft.setToggleGroup(tgPosBouss);
        rbBoussTopRight.setToggleGroup(tgPosBouss);
        rbBoussBottomLeft.setToggleGroup(tgPosBouss);
        rbBoussBottomRight.setToggleGroup(tgPosBouss);

        iPosX = 200;
        iPosY = (int) yHS;

        rbBoussTopLeft.setLayoutX(iPosX);
        rbBoussTopRight.setLayoutX(iPosX + 20);
        rbBoussTopLeft.setLayoutY(iPosY);
        rbBoussTopRight.setLayoutY(iPosY);

        rbBoussBottomLeft.setLayoutX(iPosX);
        rbBoussBottomRight.setLayoutX(iPosX + 20);
        rbBoussBottomLeft.setLayoutY(iPosY + 20);
        rbBoussBottomRight.setLayoutY(iPosY + 20);
        apBouss1.getChildren().addAll(
                rbBoussTopLeft, rbBoussTopRight,
                rbBoussBottomLeft, rbBoussBottomRight
        );
        Label lblBoussDXSpinner = new Label("dX :");
        lblBoussDXSpinner.setLayoutX(25);
        lblBoussDXSpinner.setLayoutY(yHS + 50);
        Label lblBoussDYSpinner = new Label("dY :");
        lblBoussDYSpinner.setLayoutX(175);
        lblBoussDYSpinner.setLayoutY(yHS + 50);
        bdfOffsetXBoussole = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXBoussole.setLayoutX(50);
        bdfOffsetXBoussole.setLayoutY(yHS + 45);
        bdfOffsetXBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetXBoussole.setNumber(new BigDecimal(20));
        bdfOffsetXBoussole.setMaxWidth(100);
        bdfOffsetYBoussole = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYBoussole.setLayoutX(200);
        bdfOffsetYBoussole.setLayoutY(yHS + 45);
        bdfOffsetYBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetYBoussole.setNumber(new BigDecimal(20));
        bdfOffsetYBoussole.setMaxWidth(100);
        apBouss1.getChildren().addAll(lblBoussDXSpinner, bdfOffsetXBoussole,
                lblBoussDYSpinner, bdfOffsetYBoussole
        );
        Label lblTailleBouss = new Label(rbLocalisation.getString("interface.tailleBoussole"));
        lblTailleBouss.setLayoutX(10);
        lblTailleBouss.setLayoutY(yHS + 85);
        slTailleBoussole = new Slider(50, 200, 100);
        slTailleBoussole.setLayoutX(200);
        slTailleBoussole.setLayoutY(yHS + 85);
        Label lblOpaciteBouss = new Label(rbLocalisation.getString("interface.opaciteBoussole"));
        lblOpaciteBouss.setLayoutX(10);
        lblOpaciteBouss.setLayoutY(yHS + 115);
        slOpaciteBoussole = new Slider(0, 1.0, 0.8);
        slOpaciteBoussole.setLayoutX(200);
        slOpaciteBoussole.setLayoutY(yHS + 115);
        cbAiguilleMobile = new CheckBox(rbLocalisation.getString("interface.aiguilleMobile"));
        cbAiguilleMobile.setLayoutX(10);
        cbAiguilleMobile.setLayoutY(yHS + 145);
        cbAiguilleMobile.setSelected(true);
        apBoussole.setPrefHeight(yHS + 180);

        apBouss1.getChildren().addAll(
                lblTailleBouss, slTailleBoussole,
                lblOpaciteBouss, slOpaciteBoussole,
                cbAiguilleMobile
        );
        apBouss1.disableProperty().bind(cbAfficheBoussole.selectedProperty().not());
        apBoussole.getChildren().addAll(apBouss1, cbAfficheBoussole);

        /*
         * ********************************************
         *     Panel Masque 
         * ********************************************
         */
        AnchorPane apMasque = new AnchorPane();
        PaneOutil poMasque = new PaneOutil(rbLocalisation.getString("interface.masque"), apMasque, largeur);
        AnchorPane apMASQ = new AnchorPane(poMasque.getApPaneOutil());

        apMasque.setLayoutY(40);
        apMasque.setPrefHeight(590);
        apMasque.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheMasque = new CheckBox(rbLocalisation.getString("interface.affichageMasque"));
        cbAfficheMasque.setLayoutX(10);
        cbAfficheMasque.setLayoutY(10);
        poMasque.setbValide(isbAfficheMasque());
        AnchorPane apMasq1 = new AnchorPane();
        Label lblChoixMasque = new Label(rbLocalisation.getString("interface.choixImgMasque"));
        lblChoixMasque.setLayoutX(10);
        lblChoixMasque.setLayoutY(40);
        apMasq1.getChildren().add(lblChoixMasque);

        int iNombreMasques = strListeMasques.size();
        ImageView[] ivMasques = new ImageView[iNombreMasques];
        i = 0;
        for (String strNomImage : strListeMasques) {
            Pane paneFond = new Pane();
            ivMasques[i] = new ImageView(new Image("file:" + strRepertMasques + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol1 = i % 4;
            int iRow1 = i / 4;
            xHS = iCol1 * 35 + 15;
            yHS = iRow1 * 35 + 60;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setPrefHeight(30);
            paneFond.setPrefWidth(30);
            paneFond.setOpacity(1);
            paneFond.setUserData(strNomImage);
            paneFond.setStyle("-fx-background-color : rgba(255,255,255,0)");
            paneFond.setOnMouseClicked((me) -> {
                setStrImageMasque((String) ((Pane) me.getSource()).getUserData());
                apVisualisation.getChildren().remove(ivMasque);
                chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
                afficheMasque();
            });
            paneFond.getChildren().add(ivMasques[i]);
            apMasq1.getChildren().add(paneFond);
            i++;

        }
        cpCouleurMasques = new ColorPicker(couleurMasque);
        Label lblCouleurMasque = new Label(rbLocalisation.getString("interface.couleurMasque"));
        lblCouleurMasque.setLayoutX(170);
        lblCouleurMasque.setLayoutY(40);
        cpCouleurMasques.setLayoutX(180);
        cpCouleurMasques.setLayoutY(60);
        apMasq1.getChildren().addAll(lblCouleurMasque, cpCouleurMasques);

        Label lblPositMasque = new Label(rbLocalisation.getString("interface.choixPositMasque"));
        lblPositMasque.setLayoutX(10);
        int iBasImages = ((i - 1) / 4 + 1) * 35;
        lblPositMasque.setLayoutY(70 + iBasImages);
        apMasq1.getChildren().add(lblPositMasque);

        rbMasqueTopLeft = new RadioButton();
        rbMasqueTopRight = new RadioButton();
        rbMasqueBottomLeft = new RadioButton();
        rbMasqueBottomRight = new RadioButton();

        rbMasqueTopLeft.setUserData("top:left");
        rbMasqueTopRight.setUserData("top:right");
        rbMasqueBottomLeft.setUserData("bottom:left");
        rbMasqueBottomRight.setUserData("bottom:right");

        rbMasqueTopLeft.setToggleGroup(tgPosMasque);
        rbMasqueTopRight.setToggleGroup(tgPosMasque);
        rbMasqueBottomLeft.setToggleGroup(tgPosMasque);
        rbMasqueBottomRight.setToggleGroup(tgPosMasque);

        iPosX = 200;
        iPosY = 70 + iBasImages;

        rbMasqueTopLeft.setLayoutX(iPosX);
        rbMasqueTopRight.setLayoutX(iPosX + 20);
        rbMasqueTopLeft.setLayoutY(iPosY);
        rbMasqueTopRight.setLayoutY(iPosY);

        rbMasqueBottomLeft.setLayoutX(iPosX);
        rbMasqueBottomRight.setLayoutX(iPosX + 20);
        rbMasqueBottomLeft.setLayoutY(iPosY + 20);
        rbMasqueBottomRight.setLayoutY(iPosY + 20);
        apMasq1.getChildren().addAll(
                rbMasqueTopLeft, rbMasqueTopRight,
                rbMasqueBottomLeft, rbMasqueBottomRight
        );
        Label lblMasqueDXSpinner = new Label("dX :");
        lblMasqueDXSpinner.setLayoutX(25);
        lblMasqueDXSpinner.setLayoutY(128 + iBasImages);
        Label lblMasqueDYSpinner = new Label("dY :");
        lblMasqueDYSpinner.setLayoutX(175);
        lblMasqueDYSpinner.setLayoutY(128 + iBasImages);
        bdfOffsetXMasque = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXMasque.setLayoutX(50);
        bdfOffsetXMasque.setLayoutY(123 + iBasImages);
        bdfOffsetXMasque.setMaxValue(new BigDecimal(2000));
        bdfOffsetXMasque.setMinValue(new BigDecimal(0));
        bdfOffsetXMasque.setNumber(new BigDecimal(20));
        bdfOffsetXMasque.setMaxWidth(100);
        bdfOffsetYMasque = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYMasque.setLayoutX(200);
        bdfOffsetYMasque.setLayoutY(123 + iBasImages);
        bdfOffsetYMasque.setMaxValue(new BigDecimal(2000));
        bdfOffsetYMasque.setMinValue(new BigDecimal(0));
        bdfOffsetYMasque.setNumber(new BigDecimal(20));
        bdfOffsetYMasque.setMaxWidth(100);
        apMasq1.getChildren().addAll(
                lblMasqueDXSpinner, bdfOffsetXMasque,
                lblMasqueDYSpinner, bdfOffsetYMasque
        );
        Label lblTailleMasque = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleMasque.setLayoutX(10);
        lblTailleMasque.setLayoutY(160 + iBasImages);
        slTailleMasque = new Slider(15, 60, 30);
        slTailleMasque.setLayoutX(200);
        slTailleMasque.setLayoutY(160 + iBasImages);
        Label lblOpaciteMasque = new Label(rbLocalisation.getString("interface.opaciteMasque"));
        lblOpaciteMasque.setLayoutX(10);
        lblOpaciteMasque.setLayoutY(190 + iBasImages);
        slOpaciteMasque = new Slider(0, 1.0, 0.8);
        slOpaciteMasque.setLayoutX(200);
        slOpaciteMasque.setLayoutY(190 + iBasImages);
        cbMasqueNavigation = new CheckBox(rbLocalisation.getString("interface.masqueNavigation"));
        cbMasqueNavigation.setLayoutX(60);
        cbMasqueNavigation.setLayoutY(220 + iBasImages);
        cbMasqueNavigation.setSelected(true);
        cbMasqueBoussole = new CheckBox(rbLocalisation.getString("interface.masqueBoussole"));
        cbMasqueBoussole.setLayoutX(60);
        cbMasqueBoussole.setLayoutY(250 + iBasImages);
        cbMasqueBoussole.setSelected(true);
        cbMasqueTitre = new CheckBox(rbLocalisation.getString("interface.masqueTitre"));
        cbMasqueTitre.setLayoutX(60);
        cbMasqueTitre.setLayoutY(280 + iBasImages);
        cbMasqueTitre.setSelected(true);
        cbMasquePlan = new CheckBox(rbLocalisation.getString("interface.masquePlan"));
        cbMasquePlan.setLayoutX(60);
        cbMasquePlan.setLayoutY(310 + iBasImages);
        cbMasquePlan.setSelected(true);
        cbMasqueReseaux = new CheckBox(rbLocalisation.getString("interface.masqueReseaux"));
        cbMasqueReseaux.setLayoutX(60);
        cbMasqueReseaux.setLayoutY(340 + iBasImages);
        cbMasqueReseaux.setSelected(true);
        cbMasqueReseaux.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                setbMasqueReseaux((boolean) new_val);
            }
        });
        cbMasqueVignettes = new CheckBox(rbLocalisation.getString("interface.masqueVignettes"));
        cbMasqueVignettes.setLayoutX(60);
        cbMasqueVignettes.setLayoutY(370 + iBasImages);
        cbMasqueVignettes.setSelected(true);
        cbMasqueCombo = new CheckBox(rbLocalisation.getString("interface.masqueCombo"));
        cbMasqueCombo.setLayoutX(60);
        cbMasqueCombo.setLayoutY(400 + iBasImages);
        cbMasqueCombo.setSelected(true);

        cbMasqueSuivPrec = new CheckBox(rbLocalisation.getString("interface.masqueSuivPrec"));
        cbMasqueSuivPrec.setLayoutX(60);
        cbMasqueSuivPrec.setLayoutY(430 + iBasImages);
        cbMasqueSuivPrec.setSelected(true);
        cbMasqueHotspots = new CheckBox(rbLocalisation.getString("interface.masqueHotspots"));
        cbMasqueHotspots.setLayoutX(60);
        cbMasqueHotspots.setLayoutY(460 + iBasImages);
        cbMasqueHotspots.setSelected(true);

        apMasq1.getChildren().addAll(
                lblTailleMasque, slTailleMasque,
                lblOpaciteMasque, slOpaciteMasque,
                cbMasqueNavigation,
                cbMasqueBoussole,
                cbMasqueTitre,
                cbMasquePlan,
                cbMasqueReseaux,
                cbMasqueVignettes,
                cbMasqueCombo,
                cbMasqueSuivPrec,
                cbMasqueHotspots
        );
        apMasq1.disableProperty().bind(cbAfficheMasque.selectedProperty().not());
        apMasque.getChildren().addAll(apMasq1, cbAfficheMasque);

        /*
         * ********************************************
         *     Panel ReseauxSociaux 
         * ********************************************
         */
        AnchorPane apReseauxSociaux = new AnchorPane();
        PaneOutil poReseaux = new PaneOutil(rbLocalisation.getString("interface.reseauxSociaux"), apReseauxSociaux, largeur);
        AnchorPane apRS = new AnchorPane(poReseaux.getApPaneOutil());

        apReseauxSociaux.setLayoutY(40);
        apReseauxSociaux.setPrefHeight(310);
        apReseauxSociaux.setMinWidth(vbOutils.getPrefWidth() - 20);
        AnchorPane apReseau = new AnchorPane();
        cbAfficheReseauxSociaux = new CheckBox(rbLocalisation.getString("interface.affichageReseauxSociaux"));
        cbAfficheReseauxSociaux.setLayoutX(10);
        cbAfficheReseauxSociaux.setLayoutY(10);
        poReseaux.setbValide(isbAfficheReseauxSociaux());
        Label lblPositReseauxSociaux = new Label(rbLocalisation.getString("interface.choixPositReseauxSociaux"));
        lblPositReseauxSociaux.setLayoutX(10);
        iBasImages = -30;
        lblPositReseauxSociaux.setLayoutY(70 + iBasImages);
        apReseau.getChildren().add(lblPositReseauxSociaux);

        rbReseauxSociauxTopLeft = new RadioButton();
        rbReseauxSociauxTopRight = new RadioButton();
        rbReseauxSociauxBottomLeft = new RadioButton();
        rbReseauxSociauxBottomRight = new RadioButton();

        rbReseauxSociauxTopLeft.setUserData("top:left");
        rbReseauxSociauxTopRight.setUserData("top:right");
        rbReseauxSociauxBottomLeft.setUserData("bottom:left");
        rbReseauxSociauxBottomRight.setUserData("bottom:right");

        rbReseauxSociauxTopLeft.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxTopRight.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxBottomLeft.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxBottomRight.setToggleGroup(tgPosReseauxSociaux);

        iPosX = 200;
        iPosY = 70 + iBasImages;

        rbReseauxSociauxTopLeft.setLayoutX(iPosX);
        rbReseauxSociauxTopRight.setLayoutX(iPosX + 20);
        rbReseauxSociauxTopLeft.setLayoutY(iPosY);
        rbReseauxSociauxTopRight.setLayoutY(iPosY);

        rbReseauxSociauxBottomLeft.setLayoutX(iPosX);
        rbReseauxSociauxBottomRight.setLayoutX(iPosX + 20);
        rbReseauxSociauxBottomLeft.setLayoutY(iPosY + 20);
        rbReseauxSociauxBottomRight.setLayoutY(iPosY + 20);
        apReseau.getChildren().addAll(
                rbReseauxSociauxTopLeft, rbReseauxSociauxTopRight,
                rbReseauxSociauxBottomLeft, rbReseauxSociauxBottomRight
        );
        Label lblReseauxSociauxDXSpinner = new Label("dX :");
        lblReseauxSociauxDXSpinner.setLayoutX(25);
        lblReseauxSociauxDXSpinner.setLayoutY(128 + iBasImages);
        Label lblReseauxSociauxDYSpinner = new Label("dY :");
        lblReseauxSociauxDYSpinner.setLayoutX(175);
        lblReseauxSociauxDYSpinner.setLayoutY(128 + iBasImages);
        bdfOffsetXReseauxSociaux = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXReseauxSociaux.setLayoutX(50);
        bdfOffsetXReseauxSociaux.setLayoutY(123 + iBasImages);
        bdfOffsetXReseauxSociaux.setMaxValue(new BigDecimal(2000));
        bdfOffsetXReseauxSociaux.setMinValue(new BigDecimal(0));
        bdfOffsetXReseauxSociaux.setNumber(new BigDecimal(20));
        bdfOffsetXReseauxSociaux.setMaxWidth(100);
        bdfOffsetYreseauxSociaux = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYreseauxSociaux.setLayoutX(200);
        bdfOffsetYreseauxSociaux.setLayoutY(123 + iBasImages);
        bdfOffsetYreseauxSociaux.setMaxValue(new BigDecimal(2000));
        bdfOffsetYreseauxSociaux.setMinValue(new BigDecimal(0));
        bdfOffsetYreseauxSociaux.setNumber(new BigDecimal(20));
        bdfOffsetYreseauxSociaux.setMaxWidth(100);
        apReseau.getChildren().addAll(
                lblReseauxSociauxDXSpinner, bdfOffsetXReseauxSociaux,
                lblReseauxSociauxDYSpinner, bdfOffsetYreseauxSociaux
        );
        Label lblTailleReseauxSociaux = new Label(rbLocalisation.getString("interface.tailleReseauxSociaux"));
        lblTailleReseauxSociaux.setLayoutX(10);
        lblTailleReseauxSociaux.setLayoutY(160 + iBasImages);
        slTailleReseauxSociaux = new Slider(15, 60, 30);
        slTailleReseauxSociaux.setLayoutX(200);
        slTailleReseauxSociaux.setLayoutY(160 + iBasImages);
        Label lblOpaciteReseauxSociaux = new Label(rbLocalisation.getString("interface.opaciteReseauxSociaux"));
        lblOpaciteReseauxSociaux.setLayoutX(10);
        lblOpaciteReseauxSociaux.setLayoutY(190 + iBasImages);
        slOpaciteReseauxSociaux = new Slider(0, 1.0, 0.8);
        slOpaciteReseauxSociaux.setLayoutX(200);
        slOpaciteReseauxSociaux.setLayoutY(190 + iBasImages);
        cbReseauxSociauxTwitter = new CheckBox("Twitter");
        cbReseauxSociauxTwitter.setLayoutX(60);
        cbReseauxSociauxTwitter.setLayoutY(220 + iBasImages);
        cbReseauxSociauxTwitter.setSelected(true);
        cbReseauxSociauxGoogle = new CheckBox("Google+");
        cbReseauxSociauxGoogle.setLayoutX(60);
        cbReseauxSociauxGoogle.setLayoutY(250 + iBasImages);
        cbReseauxSociauxGoogle.setSelected(true);
        cbReseauxSociauxFacebook = new CheckBox("Facebook");
        cbReseauxSociauxFacebook.setLayoutX(60);
        cbReseauxSociauxFacebook.setLayoutY(280 + iBasImages);
        cbReseauxSociauxFacebook.setSelected(true);

        cbReseauxSociauxEmail = new CheckBox("Email");
        cbReseauxSociauxEmail.setLayoutX(60);
        cbReseauxSociauxEmail.setLayoutY(310 + iBasImages);
        cbReseauxSociauxEmail.setSelected(true);

        apReseau.getChildren().addAll(
                lblTailleReseauxSociaux, slTailleReseauxSociaux,
                lblOpaciteReseauxSociaux, slOpaciteReseauxSociaux,
                cbReseauxSociauxTwitter, cbReseauxSociauxGoogle, cbReseauxSociauxFacebook, cbReseauxSociauxEmail
        );
        apReseau.disableProperty().bind(cbAfficheReseauxSociaux.selectedProperty().not());
        apReseauxSociaux.getChildren().addAll(apReseau, cbAfficheReseauxSociaux);

        /*
         * ********************************************
         *     Panel Vignettes 
         * ********************************************
         */
        apVignettes = new AnchorPane();
        PaneOutil poVignettes = new PaneOutil(rbLocalisation.getString("interface.vignettes"), apVignettes, largeur);
        AnchorPane apVIG = new AnchorPane(poVignettes.getApPaneOutil());
        AnchorPane apVig1 = new AnchorPane();
        apVignettes.setLayoutY(40);
        apVignettes.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheVignettes = new CheckBox(rbLocalisation.getString("interface.affichageVignettes"));
        cbAfficheVignettes.setLayoutX(10);
        cbAfficheVignettes.setLayoutY(10);
        poVignettes.setbValide(isbAfficheVignettes());
        cbReplieDemarrageVignettes = new CheckBox(rbLocalisation.getString("interface.replie"));
        cbReplieDemarrageVignettes.setLayoutX(10);
        cbReplieDemarrageVignettes.setLayoutY(40);
        cbReplieDemarrageVignettes.setSelected(false);
        Separator sepVignettes = new Separator();
        sepVignettes.setPrefWidth(360);
        sepVignettes.setLayoutX(5);
        sepVignettes.setLayoutY(70);
        apVig1.getChildren().addAll(cbReplieDemarrageVignettes, sepVignettes);
        Label lblChoixCouleurFondVignettes = new Label(rbLocalisation.getString("interface.choixCouleurFondVignettes"));
        lblChoixCouleurFondVignettes.setLayoutX(10);
        lblChoixCouleurFondVignettes.setLayoutY(135);
        cpCouleurFondVignettes = new ColorPicker(Color.valueOf(getStrCouleurFondVignettes()));
        cpCouleurFondVignettes.setLayoutX(200);
        cpCouleurFondVignettes.setLayoutY(133);
        apVig1.getChildren().addAll(lblChoixCouleurFondVignettes, cpCouleurFondVignettes);
        Label lblChoixCouleurTexteVignettes = new Label(rbLocalisation.getString("interface.choixCouleurTexteVignettes"));
        lblChoixCouleurTexteVignettes.setLayoutX(10);
        lblChoixCouleurTexteVignettes.setLayoutY(165);
        cpCouleurTexteVignettes = new ColorPicker(Color.valueOf(getStrCouleurTexteVignettes()));
        cpCouleurTexteVignettes.setLayoutX(200);
        cpCouleurTexteVignettes.setLayoutY(163);
        apVig1.getChildren().addAll(lblChoixCouleurTexteVignettes, cpCouleurTexteVignettes);
        Label lblPositVignettes = new Label(rbLocalisation.getString("interface.choixPositVignettes"));
        lblPositVignettes.setLayoutX(10);
        lblPositVignettes.setLayoutY(90);
        apVig1.getChildren().add(lblPositVignettes);

        rbVignettesLeft = new RadioButton();
        rbVignettesRight = new RadioButton();
        rbVignettesBottom = new RadioButton();

        rbVignettesLeft.setUserData("left");
        rbVignettesRight.setUserData("right");
        rbVignettesBottom.setUserData("bottom");

        rbVignettesLeft.setToggleGroup(tgPosVignettes);
        rbVignettesRight.setToggleGroup(tgPosVignettes);
        rbVignettesBottom.setToggleGroup(tgPosVignettes);

        iPosX = 200;
        iPosY = 90;

        rbVignettesLeft.setLayoutX(iPosX);
        rbVignettesRight.setLayoutX(iPosX + 40);
        rbVignettesLeft.setLayoutY(iPosY);
        rbVignettesRight.setLayoutY(iPosY);

        rbVignettesBottom.setLayoutX(iPosX + 20);
        rbVignettesBottom.setLayoutY(iPosY + 20);
        apVig1.getChildren().addAll(
                rbVignettesLeft, rbVignettesRight,
                rbVignettesBottom
        );
        Label lblTailleVignettes = new Label(rbLocalisation.getString("interface.tailleVignettes"));
        lblTailleVignettes.setLayoutX(10);
        lblTailleVignettes.setLayoutY(195);
        slTailleVignettes = new Slider(50, 300, 120);
        slTailleVignettes.setLayoutX(200);
        slTailleVignettes.setLayoutY(195);
        Label lblOpaciteVignettes = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
        lblOpaciteVignettes.setLayoutX(10);
        lblOpaciteVignettes.setLayoutY(225);
        slOpaciteVignettes = new Slider(0, 1.0, 0.8);
        slOpaciteVignettes.setLayoutX(200);
        slOpaciteVignettes.setLayoutY(225);
        apVig1.getChildren().addAll(
                lblTailleVignettes, slTailleVignettes,
                lblOpaciteVignettes, slOpaciteVignettes
        );
        apVignettes.setPrefHeight(255);
        apVig1.disableProperty().bind(cbAfficheVignettes.selectedProperty().not());

        apVignettes.getChildren().addAll(apVig1, cbAfficheVignettes);

        /*
         * ********************************************
         *     Panel ComboMenu 
         * ********************************************
         */
        apComboMenu = new AnchorPane();
        PaneOutil poComboMenu = new PaneOutil(rbLocalisation.getString("interface.comboMenu"), apComboMenu, largeur);
        AnchorPane apCBM = new AnchorPane(poComboMenu.getApPaneOutil());
        apComboMenu.setLayoutY(40);
        apComboMenu.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheComboMenu = new CheckBox(rbLocalisation.getString("interface.affichageComboMenu"));
        cbAfficheComboMenu.setLayoutX(10);
        cbAfficheComboMenu.setLayoutY(10);
        poComboMenu.setbValide(isbAfficheComboMenu());
        AnchorPane apCombo1 = new AnchorPane();
        cbAfficheComboMenuImages = new CheckBox(rbLocalisation.getString("interface.affichageComboMenuImages"));
        cbAfficheComboMenuImages.setLayoutX(10);
        cbAfficheComboMenuImages.setLayoutY(40);
        cbAfficheComboMenuImages.setSelected(isbAfficheComboMenuImages());
        apCombo1.getChildren().add(cbAfficheComboMenuImages);

        Label lblPositComboMenu = new Label(rbLocalisation.getString("interface.choixPositComboMenu"));
        lblPositComboMenu.setLayoutX(10);
        lblPositComboMenu.setLayoutY(90);
        apCombo1.getChildren().add(lblPositComboMenu);

        rbComboMenuTopLeft = new RadioButton();
        rbComboMenuTopCenter = new RadioButton();
        rbComboMenuTopRight = new RadioButton();
        rbComboMenuBottomLeft = new RadioButton();
        rbComboMenuBottomCenter = new RadioButton();
        rbComboMenuBottomRight = new RadioButton();

        rbComboMenuTopLeft.setUserData("top:left");
        rbComboMenuTopCenter.setUserData("top:center");
        rbComboMenuTopRight.setUserData("top:right");
        rbComboMenuBottomLeft.setUserData("bottom:left");
        rbComboMenuBottomCenter.setUserData("bottom:center");
        rbComboMenuBottomRight.setUserData("bottom:right");

        rbComboMenuTopLeft.setToggleGroup(tgPosComboMenu);
        rbComboMenuTopCenter.setToggleGroup(tgPosComboMenu);
        rbComboMenuTopRight.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomLeft.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomCenter.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomRight.setToggleGroup(tgPosComboMenu);

        iPosX = 200;
        iPosY = 70;

        rbComboMenuTopLeft.setLayoutX(iPosX);
        rbComboMenuTopCenter.setLayoutX(iPosX + 20);
        rbComboMenuTopRight.setLayoutX(iPosX + 40);
        rbComboMenuTopLeft.setLayoutY(iPosY);
        rbComboMenuTopCenter.setLayoutY(iPosY);
        rbComboMenuTopRight.setLayoutY(iPosY);

        rbComboMenuBottomLeft.setLayoutX(iPosX);
        rbComboMenuBottomCenter.setLayoutX(iPosX + 20);
        rbComboMenuBottomRight.setLayoutX(iPosX + 40);
        rbComboMenuBottomLeft.setLayoutY(iPosY + 40);
        rbComboMenuBottomCenter.setLayoutY(iPosY + 40);
        rbComboMenuBottomRight.setLayoutY(iPosY + 40);
        apCombo1.getChildren().addAll(
                rbComboMenuTopLeft, rbComboMenuTopCenter, rbComboMenuTopRight,
                rbComboMenuBottomLeft, rbComboMenuBottomCenter, rbComboMenuBottomRight
        );

        Label lblOffsetXComboMenu = new Label("dX :");
        lblOffsetXComboMenu.setLayoutX(25);
        lblOffsetXComboMenu.setLayoutY(148);
        Label lblOffsetYComboMenu = new Label("dY :");
        lblOffsetYComboMenu.setLayoutX(175);
        lblOffsetYComboMenu.setLayoutY(148);
        bdfOffsetXComboMenu = new BigDecimalField(new BigDecimal(getOffsetXComboMenu()));
        bdfOffsetXComboMenu.setLayoutX(50);
        bdfOffsetXComboMenu.setLayoutY(143);
        bdfOffsetXComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetXComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetXComboMenu.setMaxWidth(100);
        bdfOffsetYComboMenu = new BigDecimalField(new BigDecimal(getOffsetYComboMenu()));
        bdfOffsetYComboMenu.setLayoutX(200);
        bdfOffsetYComboMenu.setLayoutY(143);
        bdfOffsetYComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetYComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetYComboMenu.setMaxWidth(100);
        apCombo1.getChildren().addAll(
                lblOffsetXComboMenu, bdfOffsetXComboMenu,
                lblOffsetYComboMenu, bdfOffsetYComboMenu
        );

        apComboMenu.setPrefHeight(235);
        apCombo1.disableProperty().bind(cbAfficheComboMenu.selectedProperty().not());
        apComboMenu.getChildren().addAll(apCombo1, cbAfficheComboMenu);

        /*
         * ********************************************
         *     Panel Plan
         * ********************************************
         */
        apPlan = new AnchorPane();
        PaneOutil opPlan = new PaneOutil(rbLocalisation.getString("interface.plan"), apPlan, largeur);
        AnchorPane apPLAN = new AnchorPane(opPlan.getApPaneOutil());

        apPlan.setLayoutY(40);
        apPlan.setPrefHeight(390);
        apPlan.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAffichePlan = new CheckBox(rbLocalisation.getString("interface.affichagePlan"));
        cbAffichePlan.setLayoutX(10);
        cbAffichePlan.setLayoutY(10);
        opPlan.setbValide(isbAffichePlan());
        AnchorPane apPlan1 = new AnchorPane();
        cbReplieDemarragePlan = new CheckBox(rbLocalisation.getString("interface.replie"));
        cbReplieDemarragePlan.setLayoutX(10);
        cbReplieDemarragePlan.setLayoutY(40);
        cbReplieDemarragePlan.setSelected(false);
        Separator sepPlan = new Separator();
        sepPlan.setPrefWidth(360);
        sepPlan.setLayoutX(5);
        sepPlan.setLayoutY(70);
        Label lblLargeurPlan = new Label(rbLocalisation.getString("interface.largeurPlan"));
        lblLargeurPlan.setLayoutX(10);
        lblLargeurPlan.setLayoutY(90);
        slLargeurPlan = new Slider(200, 800, 300);
        slLargeurPlan.setLayoutX(200);
        slLargeurPlan.setLayoutY(90);
        Label lblPositPlan = new Label(rbLocalisation.getString("interface.positionPlan"));
        lblPositPlan.setLayoutX(10);
        lblPositPlan.setLayoutY(120);
        rbPlanLeft = new RadioButton("");
        rbPlanLeft.setLayoutX(200);
        rbPlanLeft.setLayoutY(120);
        rbPlanLeft.setUserData("left");
        rbPlanLeft.setToggleGroup(tgPosPlan);
        rbPlanRight = new RadioButton("");
        rbPlanRight.setLayoutX(230);
        rbPlanRight.setLayoutY(120);
        rbPlanRight.setUserData("right");
        rbPlanRight.setToggleGroup(tgPosPlan);
        Label lblCouleurFondPlan = new Label(rbLocalisation.getString("interface.couleurFondPlan"));
        lblCouleurFondPlan.setLayoutX(10);
        lblCouleurFondPlan.setLayoutY(150);
        cpCouleurFondPlan = new ColorPicker(getCouleurFondPlan());
        cpCouleurFondPlan.setLayoutX(200);
        cpCouleurFondPlan.setLayoutY(145);
        Label lblCouleurTextePlan = new Label(rbLocalisation.getString("interface.couleurTextePlan"));
        lblCouleurTextePlan.setLayoutX(10);
        lblCouleurTextePlan.setLayoutY(180);
        cpCouleurTextePlan = new ColorPicker(getCouleurTextePlan());
        cpCouleurTextePlan.setLayoutX(200);
        cpCouleurTextePlan.setLayoutY(175);
        Label lblOpacitePlan = new Label(rbLocalisation.getString("interface.opacitePlan"));
        lblOpacitePlan.setLayoutX(10);
        lblOpacitePlan.setLayoutY(210);
        slOpacitePlan = new Slider(0, 1.0, 0.8);
        slOpacitePlan.setLayoutX(200);
        slOpacitePlan.setLayoutY(210);
        cbAfficheRadar = new CheckBox(rbLocalisation.getString("interface.afficheRadar"));
        cbAfficheRadar.setLayoutX(10);
        cbAfficheRadar.setLayoutY(240);

        Label lblTailleRadar = new Label(rbLocalisation.getString("interface.tailleRadar"));
        lblTailleRadar.setLayoutX(10);
        lblTailleRadar.setLayoutY(270);
        slTailleRadar = new Slider(0, 80, getTailleRadar());
        slTailleRadar.setLayoutX(200);
        slTailleRadar.setLayoutY(270);
        Label lblOpaciteRadar = new Label(rbLocalisation.getString("interface.opaciteRadar"));
        lblOpaciteRadar.setLayoutX(10);
        lblOpaciteRadar.setLayoutY(300);
        slOpaciteRadar = new Slider(0, 1, 0.8);
        slOpaciteRadar.setLayoutX(200);
        slOpaciteRadar.setLayoutY(300);
        Label lblCouleurFondRadar = new Label(rbLocalisation.getString("interface.couleurFondRadar"));
        lblCouleurFondRadar.setLayoutX(10);
        lblCouleurFondRadar.setLayoutY(330);
        cpCouleurFondRadar = new ColorPicker(getCouleurFondRadar());
        cpCouleurFondRadar.setLayoutX(200);
        cpCouleurFondRadar.setLayoutY(330);
        Label lblCouleurLigneRadar = new Label(rbLocalisation.getString("interface.couleurLigneRadar"));
        lblCouleurLigneRadar.setLayoutX(10);
        lblCouleurLigneRadar.setLayoutY(360);
        cpCouleurLigneRadar = new ColorPicker(getCouleurLigneRadar());
        cpCouleurLigneRadar.setLayoutX(200);
        cpCouleurLigneRadar.setLayoutY(360);
        apPlan1.disableProperty().bind(cbAffichePlan.selectedProperty().not());
        apPlan1.getChildren().addAll(
                cbReplieDemarragePlan, sepPlan,
                lblLargeurPlan, slLargeurPlan,
                lblPositPlan, rbPlanLeft, rbPlanRight,
                lblCouleurFondPlan, cpCouleurFondPlan,
                lblCouleurTextePlan, cpCouleurTextePlan,
                lblOpacitePlan, slOpacitePlan,
                cbAfficheRadar,
                lblTailleRadar, slTailleRadar,
                lblOpaciteRadar, slOpaciteRadar,
                lblCouleurFondRadar, cpCouleurFondRadar,
                lblCouleurLigneRadar, cpCouleurLigneRadar
        );
        apPlan.getChildren().addAll(
                apPlan1, cbAffichePlan
        );


        /*
         * ********************************************
         *     Panel Carte
         * ********************************************
         */
        apCarte = new AnchorPane();
        PaneOutil poCarte = new PaneOutil(rbLocalisation.getString("interface.carte"), apCarte, largeur);
        AnchorPane apCARTE = new AnchorPane(poCarte.getApPaneOutil());

        apCarte.setLayoutY(40);
        apCarte.setPrefHeight(620);
        apCarte.setMinWidth(vbOutils.getPrefWidth() - 20);
        AnchorPane apCarte1 = new AnchorPane();
        cbAfficheCarte = new CheckBox(rbLocalisation.getString("interface.affichageCarte"));
        cbAfficheCarte.setLayoutX(10);
        cbAfficheCarte.setLayoutY(10);
        poCarte.setbValide(isbAfficheCarte());
        cbReplieDemarrageCarte = new CheckBox(rbLocalisation.getString("interface.replie"));
        cbReplieDemarrageCarte.setLayoutX(10);
        cbReplieDemarrageCarte.setLayoutY(40);
        cbReplieDemarrageCarte.setSelected(false);
        Separator sepCarte = new Separator();
        sepCarte.setPrefWidth(360);
        sepCarte.setLayoutX(5);
        sepCarte.setLayoutY(70);

        Button btnChoixCentreCarte = new Button(rbLocalisation.getString("interface.choixCentreCarte"));
        btnChoixCentreCarte.setLayoutX(10);
        btnChoixCentreCarte.setLayoutY(90);
        btnChoixCentreCarte.setPrefWidth(105);
        Button btnRecentreCarte = new Button(rbLocalisation.getString("interface.recentreCarte"));
        btnRecentreCarte.setLayoutX(125);
        btnRecentreCarte.setLayoutY(90);
        btnRecentreCarte.setPrefWidth(105);
        Button btnChoixCarte = new Button(rbLocalisation.getString("interface.choixCarte"));
        btnChoixCarte.setLayoutX(240);
        btnChoixCarte.setLayoutY(90);
        btnChoixCarte.setPrefWidth(105);

        TextField tfAdresseCarte = new TextField("");
        tfAdresseCarte.setLayoutX(10);
        tfAdresseCarte.setLayoutY(120);
        tfAdresseCarte.setPrefWidth(220);

        Button btnRechercheAdresse = new Button(rbLocalisation.getString("interface.chercheAdresse"), new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/loupe.png", -1, 16, true, true)));
        btnRechercheAdresse.setLayoutX(240);
        btnRechercheAdresse.setLayoutY(120);
        Label lblZoomCarte = new Label(rbLocalisation.getString("interface.zoomCarte"));
        lblZoomCarte.setLayoutX(10);
        lblZoomCarte.setLayoutY(160);
        setSlZoomCarte(new Slider(1, 20, getiFacteurZoomCarte()));
        slZoomCarte.setMajorTickUnit(1.d);
        slZoomCarte.setMinorTickCount(0);
        slZoomCarte.setSnapToTicks(true);
        slZoomCarte.setLayoutX(200);
        slZoomCarte.setLayoutY(160);
        Separator sepCarte2 = new Separator();
        sepCarte2.setPrefWidth(360);
        sepCarte2.setLayoutX(5);
        sepCarte2.setLayoutY(190);

        Label lblLargeurCarte = new Label(rbLocalisation.getString("interface.largeurCarte"));
        lblLargeurCarte.setLayoutX(10);
        lblLargeurCarte.setLayoutY(210);
        slLargeurCarte = new Slider(200, 1200, largeurCarte);
        slLargeurCarte.setLayoutX(200);
        slLargeurCarte.setLayoutY(210);
        Label lblHauteurCarte = new Label(rbLocalisation.getString("interface.hauteurCarte"));
        lblHauteurCarte.setLayoutX(10);
        lblHauteurCarte.setLayoutY(240);
        slHauteurCarte = new Slider(200, 1200, getHauteurCarte());
        slHauteurCarte.setLayoutX(200);
        slHauteurCarte.setLayoutY(240);
        Label lblPositCarte = new Label(rbLocalisation.getString("interface.positionCarte"));
        lblPositCarte.setLayoutX(10);
        lblPositCarte.setLayoutY(270);
        rbCarteLeft = new RadioButton("");
        rbCarteLeft.setLayoutX(200);
        rbCarteLeft.setLayoutY(270);
        rbCarteLeft.setUserData("left");
        rbCarteLeft.setSelected(getStrPositionCarte().equals("left"));
        rbCarteLeft.setToggleGroup(tgPosCarte);
        rbCarteRight = new RadioButton("");
        rbCarteRight.setLayoutX(230);
        rbCarteRight.setLayoutY(270);
        rbCarteRight.setUserData("right");
        rbCarteRight.setSelected(getStrPositionCarte().equals("right"));
        rbCarteRight.setToggleGroup(tgPosCarte);
        Label lblCouleurFondCarte = new Label(rbLocalisation.getString("interface.couleurFondCarte"));
        lblCouleurFondCarte.setLayoutX(10);
        lblCouleurFondCarte.setLayoutY(300);
        cpCouleurFondCarte = new ColorPicker(getCouleurFondCarte());
        cpCouleurFondCarte.setLayoutX(200);
        cpCouleurFondCarte.setLayoutY(295);
        Label lblCouleurTexteCarte = new Label(rbLocalisation.getString("interface.couleurTexteCarte"));
        lblCouleurTexteCarte.setLayoutX(10);
        lblCouleurTexteCarte.setLayoutY(330);
        cpCouleurTexteCarte = new ColorPicker(getCouleurTexteCarte());
        cpCouleurTexteCarte.setLayoutX(200);
        cpCouleurTexteCarte.setLayoutY(325);
        Label lblOpaciteCarte = new Label(rbLocalisation.getString("interface.opaciteCarte"));
        lblOpaciteCarte.setLayoutX(10);
        lblOpaciteCarte.setLayoutY(360);
        slOpaciteCarte = new Slider(0, 1.0, 0.8);
        slOpaciteCarte.setLayoutX(200);
        slOpaciteCarte.setLayoutY(360);
        Separator sepCarte3 = new Separator();
        sepCarte3.setPrefWidth(360);
        sepCarte3.setLayoutX(5);
        sepCarte3.setLayoutY(390);

        cbAfficheRadarCarte = new CheckBox(rbLocalisation.getString("interface.afficheRadarCarte"));
        cbAfficheRadarCarte.setLayoutX(10);
        cbAfficheRadarCarte.setLayoutY(410);

        Label lblTailleRadarCarte = new Label(rbLocalisation.getString("interface.tailleRadarCarte"));
        lblTailleRadarCarte.setLayoutX(10);
        lblTailleRadarCarte.setLayoutY(440);
        slTailleRadarCarte = new Slider(0, 80, getTailleRadarCarte());
        slTailleRadarCarte.setLayoutX(200);
        slTailleRadarCarte.setLayoutY(440);
        Label lblOpaciteRadarCarte = new Label(rbLocalisation.getString("interface.opaciteRadarCarte"));
        lblOpaciteRadarCarte.setLayoutX(10);
        lblOpaciteRadarCarte.setLayoutY(470);
        slOpaciteRadarCarte = new Slider(0, 1, 0.8);
        slOpaciteRadarCarte.setLayoutX(200);
        slOpaciteRadarCarte.setLayoutY(470);
        Label lblCouleurFondRadarCarte = new Label(rbLocalisation.getString("interface.couleurFondRadarCarte"));
        lblCouleurFondRadarCarte.setLayoutX(10);
        lblCouleurFondRadarCarte.setLayoutY(500);
        cpCouleurFondRadarCarte = new ColorPicker(getCouleurFondRadarCarte());
        cpCouleurFondRadarCarte.setLayoutX(200);
        cpCouleurFondRadarCarte.setLayoutY(500);
        Label lblCouleurLigneRadarCarte = new Label(rbLocalisation.getString("interface.couleurLigneRadarCarte"));
        lblCouleurLigneRadarCarte.setLayoutX(10);
        lblCouleurLigneRadarCarte.setLayoutY(530);
        cpCouleurLigneRadarCarte = new ColorPicker(getCouleurLigneRadarCarte());
        cpCouleurLigneRadarCarte.setLayoutX(200);
        cpCouleurLigneRadarCarte.setLayoutY(530);
        Label lblAngleRadarCarte = new Label("Angle Radar");
        lblAngleRadarCarte.setLayoutX(10);
        lblAngleRadarCarte.setLayoutY(560);
        Slider slAngleRadarCarte = new Slider(0, 720, 45);
        slAngleRadarCarte.setLayoutX(200);
        slAngleRadarCarte.setLayoutY(560);
        Label lblOuvertureRadarCarte = new Label("Angle Radar");
        lblOuvertureRadarCarte.setLayoutX(10);
        lblOuvertureRadarCarte.setLayoutY(590);
        Slider slOuvertureRadarCarte = new Slider(0, 100, 35);
        slOuvertureRadarCarte.setLayoutX(200);
        slOuvertureRadarCarte.setLayoutY(590);
        apCarte1.disableProperty().bind(cbAfficheCarte.selectedProperty().not());
        apCarte1.getChildren().addAll(
                cbReplieDemarrageCarte, sepCarte,
                btnChoixCentreCarte, btnRecentreCarte, btnChoixCarte,
                tfAdresseCarte, btnRechercheAdresse,
                lblZoomCarte, slZoomCarte,
                sepCarte2,
                lblHauteurCarte, slHauteurCarte,
                lblLargeurCarte, slLargeurCarte,
                lblPositCarte, rbCarteLeft, rbCarteRight,
                lblCouleurFondCarte, cpCouleurFondCarte,
                lblCouleurTexteCarte, cpCouleurTexteCarte,
                lblOpaciteCarte, slOpaciteCarte,
                sepCarte3,
                cbAfficheRadarCarte,
                lblTailleRadarCarte, slTailleRadarCarte,
                lblOpaciteRadarCarte, slOpaciteRadarCarte,
                lblCouleurFondRadarCarte, cpCouleurFondRadarCarte,
                lblCouleurLigneRadarCarte, cpCouleurLigneRadarCarte,
                lblAngleRadarCarte, slAngleRadarCarte,
                lblOuvertureRadarCarte, slOuvertureRadarCarte
        );
        apCarte.getChildren().addAll(
                apCarte1, cbAfficheCarte
        );


        /*
         * ********************************************
         *     Panel Image Fond
         * ********************************************
         */
        apImageFond = new AnchorPane();
        poImageFond = new PaneOutil(rbLocalisation.getString("interface.imageFond"), apImageFond, largeur);
        AnchorPane apIF = new AnchorPane(poImageFond.getApPaneOutil());
        poImageFond.setbValide(false);
        apImageFond.setLayoutY(40);
        apImageFond.setPrefHeight(60);
        apImageFond.setMinWidth(vbOutils.getPrefWidth() - 20);
        apImageFond.getChildren().addAll();
        Image imgAjoute = new Image("file:" + getStrRepertAppli() + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (me) -> {
                    ajoutImageFond();
                }
        );

        /*
         * ********************************************
         *     Panel Menu contextuel
         * ********************************************
         */
        apMenuContextuel = new AnchorPane();
        PaneOutil poMenuContextuel = new PaneOutil(rbLocalisation.getString("interface.menuContextuel"), apMenuContextuel, largeur);
        AnchorPane apMC = new AnchorPane(poMenuContextuel.getApPaneOutil());

        apMenuContextuel.setLayoutY(40);
        apMenuContextuel.setPrefHeight(280);
        apMenuContextuel.setMinWidth(vbOutils.getPrefWidth() - 20);
        AnchorPane apContext1 = new AnchorPane();
        cbAfficheMenuContextuel = new CheckBox(rbLocalisation.getString("interface.affichageMenuContextuel"));
        cbAfficheMenuContextuel.setLayoutX(10);
        cbAfficheMenuContextuel.setLayoutY(10);
        cbAfficheMenuContextuel.setSelected(isbAfficheMenuContextuel());
        poMenuContextuel.setbValide(isbAfficheMenuContextuel());
        cbAffichePrecSuivMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelSuivPrec"));
        cbAffichePrecSuivMC.setLayoutX(10);
        cbAffichePrecSuivMC.setLayoutY(40);
        cbAffichePrecSuivMC.setSelected(isbAffichePrecSuivMC());
        cbAffichePlanetNormalMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelPlaneteNormal"));
        cbAffichePlanetNormalMC.setLayoutX(10);
        cbAffichePlanetNormalMC.setLayoutY(70);
        cbAffichePlanetNormalMC.setSelected(isbAffichePlanetNormalMC());
        cbAffichePersMC1 = new CheckBox(rbLocalisation.getString("interface.menuContextuelPers1"));
        cbAffichePersMC1.setLayoutX(10);
        cbAffichePersMC1.setLayoutY(100);
        cbAffichePersMC1.setSelected(isbAffichePersMC1());
        Label lblPersLib1 = new Label(rbLocalisation.getString("interface.menuContextuelLib"));
        lblPersLib1.setLayoutX(10);
        lblPersLib1.setLayoutY(130);
        lblPersLib1.setDisable(true);
        tfPersLib1 = new TextField();
        tfPersLib1.setLayoutX(120);
        tfPersLib1.setLayoutY(130);
        tfPersLib1.setPrefSize(220, 20);
        tfPersLib1.setDisable(true);
        Label lblPersUrl1 = new Label(rbLocalisation.getString("interface.menuContextuelURL"));
        lblPersUrl1.setLayoutX(10);
        lblPersUrl1.setLayoutY(160);
        lblPersUrl1.setDisable(true);
        tfPersURL1 = new TextField();
        tfPersURL1.setLayoutX(120);
        tfPersURL1.setLayoutY(160);
        tfPersURL1.setPrefSize(220, 20);
        tfPersURL1.setDisable(true);
        cbAffichePersMC2 = new CheckBox(rbLocalisation.getString("interface.menuContextuelPers2"));
        cbAffichePersMC2.setLayoutX(10);
        cbAffichePersMC2.setLayoutY(190);
        cbAffichePersMC2.setDisable(true);
        Label lblPersLib2 = new Label(rbLocalisation.getString("interface.menuContextuelLib"));
        lblPersLib2.setLayoutX(10);
        lblPersLib2.setLayoutY(220);
        lblPersLib2.setDisable(true);
        tfPersLib2 = new TextField();
        tfPersLib2.setLayoutX(120);
        tfPersLib2.setLayoutY(220);
        tfPersLib2.setPrefSize(220, 20);
        tfPersLib2.setDisable(true);
        Label lblPersUrl2 = new Label(rbLocalisation.getString("interface.menuContextuelURL"));
        lblPersUrl2.setLayoutX(10);
        lblPersUrl2.setLayoutY(250);
        lblPersUrl2.setDisable(true);
        tfPersURL2 = new TextField();
        tfPersURL2.setLayoutX(120);
        tfPersURL2.setLayoutY(250);
        tfPersURL2.setPrefSize(220, 20);
        tfPersURL2.setDisable(true);
        apContext1.disableProperty().bind(cbAfficheMenuContextuel.selectedProperty().not());
        apContext1.getChildren().addAll(
                cbAffichePrecSuivMC,
                cbAffichePlanetNormalMC,
                cbAffichePersMC1,
                lblPersLib1, tfPersLib1,
                lblPersUrl1, tfPersURL1,
                cbAffichePersMC2,
                lblPersLib2, tfPersLib2,
                lblPersUrl2, tfPersURL2
        );
        apMenuContextuel.getChildren().addAll(
                apContext1, cbAfficheMenuContextuel
        );

        /*
         * ********************************************
         *     Panel BoutonVisiteAuto 
         * ********************************************
         */
        apBoutonVisiteAuto = new AnchorPane();
        PaneOutil poBoutonVisiteAuto = new PaneOutil(rbLocalisation.getString("interface.boutonVisiteAuto"), apBoutonVisiteAuto, largeur);
        setApBtnVA(new AnchorPane(poBoutonVisiteAuto.getApPaneOutil()));
        getApBtnVA().setDisable(true);
        apBoutonVisiteAuto.setLayoutY(40);
        apBoutonVisiteAuto.setMinWidth(vbOutils.getPrefWidth() - 20);
        setCbAfficheBoutonVisiteAuto(new CheckBox(rbLocalisation.getString("interface.affichageBoutonVisiteAuto")));
        getCbAfficheBoutonVisiteAuto().setLayoutX(10);
        getCbAfficheBoutonVisiteAuto().setLayoutY(10);
        poBoutonVisiteAuto.setbValide(isbAfficheBoutonVisiteAuto());
        AnchorPane apBtnVisiteAuto1 = new AnchorPane();

        Label lblTailleBoutonVisiteAuto = new Label(rbLocalisation.getString("interface.choixTailleBoutonVisiteAuto"));
        lblTailleBoutonVisiteAuto.setLayoutX(10);
        lblTailleBoutonVisiteAuto.setLayoutY(40);
        slTailleBoutonVisiteAuto = new Slider(10, 128, 32);
        slTailleBoutonVisiteAuto.setLayoutX(150);
        slTailleBoutonVisiteAuto.setLayoutY(40);
        Label lblPositBoutonVisiteAuto = new Label(rbLocalisation.getString("interface.choixPositBoutonVisiteAuto"));
        lblPositBoutonVisiteAuto.setLayoutX(10);
        lblPositBoutonVisiteAuto.setLayoutY(90);

        rbBoutonVisiteAutoTopLeft = new RadioButton();
        rbBoutonVisiteAutoTopCenter = new RadioButton();
        rbBoutonVisiteAutoTopRight = new RadioButton();
        rbBoutonVisiteAutoBottomLeft = new RadioButton();
        rbBoutonVisiteAutoBottomCenter = new RadioButton();
        rbBoutonVisiteAutoBottomRight = new RadioButton();

        rbBoutonVisiteAutoTopLeft.setUserData("top:left");
        rbBoutonVisiteAutoTopCenter.setUserData("top:center");
        rbBoutonVisiteAutoTopRight.setUserData("top:right");
        rbBoutonVisiteAutoBottomLeft.setUserData("bottom:left");
        rbBoutonVisiteAutoBottomCenter.setUserData("bottom:center");
        rbBoutonVisiteAutoBottomRight.setUserData("bottom:right");

        rbBoutonVisiteAutoTopLeft.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoTopCenter.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoTopRight.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoBottomLeft.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoBottomCenter.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoBottomRight.setToggleGroup(tgPosBoutonVisiteAuto);
        rbBoutonVisiteAutoTopRight.setSelected(true);

        iPosX = 200;
        iPosY = 70;

        rbBoutonVisiteAutoTopLeft.setLayoutX(iPosX);
        rbBoutonVisiteAutoTopCenter.setLayoutX(iPosX + 20);
        rbBoutonVisiteAutoTopRight.setLayoutX(iPosX + 40);
        rbBoutonVisiteAutoTopLeft.setLayoutY(iPosY);
        rbBoutonVisiteAutoTopCenter.setLayoutY(iPosY);
        rbBoutonVisiteAutoTopRight.setLayoutY(iPosY);

        rbBoutonVisiteAutoBottomLeft.setLayoutX(iPosX);
        rbBoutonVisiteAutoBottomCenter.setLayoutX(iPosX + 20);
        rbBoutonVisiteAutoBottomRight.setLayoutX(iPosX + 40);
        rbBoutonVisiteAutoBottomLeft.setLayoutY(iPosY + 40);
        rbBoutonVisiteAutoBottomCenter.setLayoutY(iPosY + 40);
        rbBoutonVisiteAutoBottomRight.setLayoutY(iPosY + 40);

        Label lblOffsetXBoutonVisiteAuto = new Label("dX :");
        lblOffsetXBoutonVisiteAuto.setLayoutX(25);
        lblOffsetXBoutonVisiteAuto.setLayoutY(148);
        Label lblOffsetYBoutonVisiteAuto = new Label("dY :");
        lblOffsetYBoutonVisiteAuto.setLayoutX(175);
        lblOffsetYBoutonVisiteAuto.setLayoutY(148);
        bdfOffsetXBoutonVisiteAuto = new BigDecimalField(new BigDecimal(getOffsetXBoutonVisiteAuto()));
        bdfOffsetXBoutonVisiteAuto.setLayoutX(50);
        bdfOffsetXBoutonVisiteAuto.setLayoutY(143);
        bdfOffsetXBoutonVisiteAuto.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBoutonVisiteAuto.setMinValue(new BigDecimal(0));
        bdfOffsetXBoutonVisiteAuto.setMaxWidth(100);
        bdfOffsetYBoutonVisiteAuto = new BigDecimalField(new BigDecimal(getOffsetYBoutonVisiteAuto()));
        bdfOffsetYBoutonVisiteAuto.setLayoutX(200);
        bdfOffsetYBoutonVisiteAuto.setLayoutY(143);
        bdfOffsetYBoutonVisiteAuto.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBoutonVisiteAuto.setMinValue(new BigDecimal(0));
        bdfOffsetYBoutonVisiteAuto.setMaxWidth(100);
        apBtnVisiteAuto1.getChildren().addAll(
                lblPositBoutonVisiteAuto,
                lblTailleBoutonVisiteAuto, slTailleBoutonVisiteAuto,
                rbBoutonVisiteAutoTopLeft, rbBoutonVisiteAutoTopCenter, rbBoutonVisiteAutoTopRight,
                rbBoutonVisiteAutoBottomLeft, rbBoutonVisiteAutoBottomCenter, rbBoutonVisiteAutoBottomRight,
                lblOffsetXBoutonVisiteAuto, bdfOffsetXBoutonVisiteAuto,
                lblOffsetYBoutonVisiteAuto, bdfOffsetYBoutonVisiteAuto
        );

        apBoutonVisiteAuto.setPrefHeight(235);
        apBtnVisiteAuto1.disableProperty().bind(getCbAfficheBoutonVisiteAuto().selectedProperty().not());
        apBoutonVisiteAuto.getChildren().addAll(apBtnVisiteAuto1, getCbAfficheBoutonVisiteAuto());

        /*
         * ******************************************************
         *     Ajouts des pannels dans la barre d'outils
         * ******************************************************
         */
        if (!isbInternet()) {
            apCARTE.setDisable(true);
        }
        vbOutils.getChildren().addAll(apTHEME,
                apHS1,
                apHS2,
                apHS3,
                apDIA,
                apTIT, getApBtnVA(),
                apECR1,
                apECR2,
                apCLASS,
                apPERS,
                apPLAN,
                apCARTE,
                apBOUSS,
                apMASQ,
                apRS,
                apVIG,
                apCBM,
                apMC,
                apIF
        );

        /*
         * *******************************************************
         *     Ajout des ecouteurs sur les différents éléments
         * ******************************************************
         */
        tgImage.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgImage.getSelectedToggle() != null) {
                cbImage.setDisable(true);
                Rectangle2D viewportRect;
                switch (tgImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        viewportRect = new Rectangle2D(0, 0, imgClaire.getWidth(), imgClaire.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imgClaire);
                        break;
                    case "sombre":
                        viewportRect = new Rectangle2D(0, 0, imgSombre.getWidth(), imgSombre.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imgSombre);
                        break;
                    case "perso":
                        cbImage.setDisable(false);
                        int index = cbImage.getSelectionModel().getSelectedIndex();
                        if (index != -1) {
                            afficheImage(index);
                        }
                        break;
                }
            }
        });

        cbImage.valueProperty().addListener((ov, t, t1) -> {
            int index = cbImage.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                afficheImage(index);
            }
        });

        /*
         Listeners Couleur Thème
         */
        cpCouleurFondTheme.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurFondTheme = cpCouleurFondTheme.getValue();
            cpCouleurFondTitre.setValue(couleurFondTheme);
            cpCouleurFondPlan.setValue(couleurFondTheme);
            cpCouleurFondCarte.setValue(couleurFondTheme);
            cpCouleurHotspotsPanoramique.setValue(couleurFondTheme);
            cpCouleurHotspotsPhoto.setValue(couleurFondTheme);
            cpCouleurHotspotsHTML.setValue(couleurFondTheme);
            cpCouleurBarreClassique.setValue(couleurFondTheme);
            cpCouleurMasques.setValue(couleurFondTheme);
            cpCouleurBarrePersonnalisee.setValue(couleurFondTheme);
            cpCouleurFondVignettes.setValue(couleurFondTheme);
            cpCouleurDiaporama.setValue(couleurFondTheme);
        });

        cpCouleurTexteTheme.valueProperty().addListener((ov, av, nv) -> {
            cpCouleurTitre.setValue(cpCouleurTexteTheme.getValue());
            cpCouleurTextePlan.setValue(cpCouleurTexteTheme.getValue());
            cpCouleurTexteCarte.setValue(cpCouleurTexteTheme.getValue());
            cpCouleurTexteVignettes.setValue(cpCouleurTexteTheme.getValue());
        });

        slOpaciteTheme.valueProperty().addListener((ov, av, nv) -> {
            double opacite = (double) nv;
            slOpaciteTitre.setValue(opacite);
            slOpaciteCarte.setValue(opacite);
            slOpacitePlan.setValue(opacite);
            slOpaciteVignettes.setValue(opacite);
            slOpaciteDiaporama.setValue(opacite);
            slFenetreInfoOpacite.setValue(opacite);
            slFenetreAideOpacite.setValue(opacite);
        });

        cbPoliceTheme.valueProperty().addListener((ov, av, nv) -> {
            cbListePolicesTitre.setValue(nv);
        });

        cbSuivantPrecedent.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbSuivantPrecedent((boolean) new_val);
            if (!new_val) {
                cbAffichePrecSuivMC.setSelected(new_val);
            }
            paneFondSuivant.setVisible(isbSuivantPrecedent());
            paneFondPrecedent.setVisible(isbSuivantPrecedent());
        });

        /*
         Listeners HotSpots
         */
        cpCouleurHotspotsPanoramique.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }

            couleurHotspots = cpCouleurHotspotsPanoramique.getValue();
            changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });
        cpCouleurHotspotsPhoto.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurHotspotsPhoto = cpCouleurHotspotsPhoto.getValue();
            changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });

        cpCouleurHotspotsHTML.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurHotspotsHTML = cpCouleurHotspotsHTML.getValue();
            changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });
        slTailleHotspotsHTML.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiTailleHotspotsHTML((int) slTailleHotspotsHTML.getValue());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });
        slTailleHotspotsImage.valueProperty().addListener((ov, av, nv) -> {
             if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
           setiTailleHotspotsImage((int) slTailleHotspotsImage.getValue());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });
        slTailleHotspotsPanoramique.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiTailleHotspotsPanoramique((int) slTailleHotspotsPanoramique.getValue());
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);

        });

        /*
         Listeners Titre
         */
        cbListePolicesTitre.valueProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setStrTitrePoliceNom(new_val.toString());
                afficheTitre();
            }
        });
        cbAfficheTitre.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            poTitre.setbValide(new_val);
            setbAfficheTitre((boolean) new_val);
            apVisualisation.getChildren().remove(hbbarreBoutons);
            apVisualisation.getChildren().remove(ivHotSpotPanoramique);
            apVisualisation.getChildren().remove(ivHotSpotImage);
            apVisualisation.getChildren().remove(ivHotSpotHTML);
            afficheTitre();
        });

        cbTitreVisite.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbTitreVisite(new_val);
            afficheTitre();
        });
        cbTitrePanoramique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbTitrePanoramique(new_val);
            afficheTitre();
        });
        cbTitreAdapte.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbTitreAdapte(new_val);
            slTailleTitre.setDisable(new_val);
            afficheTitre();
        });
        bdfTitreDecalage.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setTitreDecalage(new_value.doubleValue());
            afficheTitre();
        });

        tgPosTitre.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgPosTitre.getSelectedToggle() != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setStrTitrePosition(tgPosTitre.getSelectedToggle().getUserData().toString());
                afficheTitre();
            }
        });

        slTaillePoliceTitre.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                double taille = (double) newValue;
                setStrTitrePoliceTaille(Integer.toString((int) Math.round(taille)));
                afficheTitre();
            }
        });
        slOpaciteTitre.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setTitreOpacite((double) newValue);
                afficheTitre();
            }
        });

        slTailleTitre.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setTitreTaille((double) newValue);
                afficheTitre();
            }
        });

        cpCouleurTitre.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurTitre.getValue().toString().substring(2, 8);
            setStrCouleurTitre("#" + strCoul);
            afficheTitre();
        });

        cpCouleurFondTitre.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCouleur = cpCouleurFondTitre.getValue().toString().substring(2, 8);
            setStrCouleurFondTitre("#" + strCouleur);
            afficheTitre();
        });
        /*
         Listeners Fenêtres
         */
        cbFenetreInfoPersonnalise.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbFenetreInfoPersonnalise(new_val);
            poEcran1.setbValide(isbFenetreInfoPersonnalise());
            apFenetreInfoPers.setDisable(!isbFenetreInfoPersonnalise());
            afficheFenetreInfo();
        });

        cbFenetreAidePersonnalise.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbFenetreAidePersonnalise(new_val);
            poEcran2.setbValide(isbFenetreAidePersonnalise());
            apFenetreAidePers.setDisable(!isbFenetreAidePersonnalise());
            afficheFenetreAide();
        });

        cbAfficheFenetreInfo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            bAfficheFenetreInfo = new_val;
            if (new_val == true) {
                cbAfficheFenetreAide.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreInfo();
        });

        cbAfficheFenetreAide.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            bAfficheFenetreAide = new_val;
            if (new_val == true) {
                cbAfficheFenetreInfo.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreAide();
        });

        btnFenetreInfo.setOnMouseClicked((me) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrFenetreInfoImage(ajoutFenetreImage());
            if (!strFenetreInfoImage.equals("")) {
                tfFenetreInfoImage.setText(getStrFenetreInfoImage());
            }
            afficheFenetreInfo();
        }
        );

        btnFenetreAide.setOnMouseClicked((me) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrFenetreAideImage(ajoutFenetreImage());
            if (!strFenetreAideImage.equals("")) {
                tfFenetreAideImage.setText(getStrFenetreAideImage());
            }
            afficheFenetreAide();
        }
        );
        slFenetreInfoTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setFenetreInfoTaille(taille);
                afficheFenetreInfo();
            }
        });
        slFenetreInfoOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setFenetreInfoOpacite(opac);
                afficheFenetreInfo();
            }
        });
        bdfFenetreInfoPosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreInfoPosX(new_value.doubleValue());
            afficheFenetreInfo();
        });
        bdfFenetreInfoPosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreInfoPosY(new_value.doubleValue());
            afficheFenetreInfo();
        });

        tfFenetreURL.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrFenetreURL(newValue);
        });

        tfFenetreTexteURL.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrFenetreTexteURL(newValue);
            afficheFenetreInfo();
        });

        slFenetrePoliceTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                double taille = Math.round((double) newValue * 10.d) / 10.d;
                setFenetrePoliceTaille(taille);
                afficheFenetreInfo();
            }
        });

        cpFenetreURLCouleur.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrFenetreURLCouleur("#" + cpFenetreURLCouleur.getValue().toString().substring(2, 8));
            afficheFenetreInfo();
        });

        bdfFenetreURLPosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreURLPosX(new_value.doubleValue());
            afficheFenetreInfo();
        });

        bdfFenetreURLPosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreURLPosY(new_value.doubleValue());
            afficheFenetreInfo();
        });
        slFenetreAideTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                double taille = (double) newValue;
                setFenetreAideTaille(taille);
                afficheFenetreAide();
            }
        });
        slFenetreAideOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                double opac = (double) newValue;
                setFenetreAideOpacite(opac);
                afficheFenetreAide();
            }
        });

        bdfFenetreAidePosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreAidePosX(new_value.doubleValue());
            afficheFenetreAide();
        });
        bdfFenetreAidePosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setFenetreAidePosY(new_value.doubleValue());
            afficheFenetreAide();
        });


        /*
         Listeners Diaporama
         */
        slOpaciteDiaporama.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setDiaporamaOpacite((double) newValue);
                afficheDiaporama();
            }
        });

        cpCouleurDiaporama.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurDiaporama.getValue().toString().substring(2, 8);
            setStrCouleurDiaporama("#" + strCoul);
            afficheDiaporama();
        });

        cbVisualiseDiapo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            bVisualiseDiaporama = new_val;
            afficheDiaporama();
            if (new_val == true) {
                cbAfficheFenetreInfo.setSelected(false);
                cbAfficheFenetreAide.setSelected(false);
            }
        });
        /*
         Listeners Barre de navigation Classique
         */
        slEspacementBarreClassique.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setEspacementBarreClassique((double) newValue);
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });

        cblisteStyleBarreClassique.valueProperty().addListener((ov, t, t1) -> {
            if (t1 != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setStyleBarreClassique(t1.toString());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            }
        });

        bdfOffsetXBarreClassique.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }

            setOffsetXBarreClassique(new_value.doubleValue());
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        bdfOffsetYBarreClassique.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetYBarreClassique(new_value.doubleValue());
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });

        tgPositionBarreClassique.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgPositionBarreClassique.getSelectedToggle() != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setStrPositionBarreClassique(tgPositionBarreClassique.getSelectedToggle().getUserData().toString());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            }
        });
        cbBarreClassiqueVisible.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            poClass.setbValide(new_val);
            if (new_val) {
                setStrVisibiliteBarreClassique("oui");
                cbBarrePersonnaliseeVisible.setSelected(false);
            } else {
                setStrVisibiliteBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });

        cpCouleurBarreClassique.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurBarreClassique = cpCouleurBarreClassique.getValue();
            changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        });
        cbDeplacementsBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrDeplacementsBarreClassique("oui");
            } else {
                setStrDeplacementsBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbZoomBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrZoomBarreClassique("oui");
            } else {
                setStrZoomBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbOutilsBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrOutilsBarreClassique("oui");
            } else {
                setStrOutilsBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbSourisBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrSourisBarreClassique("oui");
            } else {
                setStrSourisBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbRotationBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrRotationBarreClassique("oui");
            } else {
                setStrRotationBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbFSBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrPleinEcranBarreClassique("oui");
            } else {
                setStrPleinEcranBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        /*
         Listeners Barre de navigation Personnalisee
         */

        bdfOffsetXBarrePersonnalisee.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetXBarrePersonnalisee(new_value.doubleValue());
            afficheBarrePersonnalisee();
        });
        bdfOffsetYBarrePersonnalisee.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetYBarrePersonnalisee(new_value.doubleValue());
            afficheBarrePersonnalisee();
        });

        grpCouleurBarrePersonnalisee.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (grpCouleurBarrePersonnalisee.getSelectedToggle() != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setbCouleurOrigineBarrePersonnalisee((boolean) grpCouleurBarrePersonnalisee.getSelectedToggle().getUserData());
                cpCouleurBarrePersonnalisee.setDisable(isbCouleurOrigineBarrePersonnalisee());
                afficheBarrePersonnalisee();
            }
        });

        grpPositionBarrePersonnalisee.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (grpPositionBarrePersonnalisee.getSelectedToggle() != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                setStrPositionBarrePersonnalisee(grpPositionBarrePersonnalisee.getSelectedToggle().getUserData().toString());
                afficheBarrePersonnalisee();
            }
        });
        cbBarrePersonnaliseeVisible.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            poPers.setbValide(new_val);
            if (new_val) {
                setStrVisibiliteBarrePersonnalisee("oui");
                cbBarreClassiqueVisible.setSelected(false);
            } else {
                setStrVisibiliteBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cpCouleurBarrePersonnalisee.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurBarrePersonnalisee = cpCouleurBarrePersonnalisee.getValue();
            afficheBarrePersonnalisee();
        });
        cbDeplacementsBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrDeplacementsBarrePersonnalisee("oui");
            } else {
                setStrDeplacementsBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbZoomBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrZoomBarrePersonnalisee("oui");
            } else {
                setStrZoomBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbSourisBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrSourisBarrePersonnalisee("oui");
            } else {
                setStrSourisBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbRotationBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrRotationBarrePersonnalisee("oui");
            } else {
                setStrRotationBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbFSBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrPleinEcranBarrePersonnalisee("oui");
            } else {
                setStrPleinEcranBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        sltailleBarrePersonnalisee.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleBarrePersonnalisee(taille);
                afficheBarrePersonnalisee();
            }
        });
        sltailleIconesBarrePersonnalisee.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleIconesBarrePersonnalisee(taille);
                afficheBarrePersonnalisee();
            }
        });
        btnLienBarrePersonnalisee.setOnMouseClicked(
                (me) -> {
                    if (getiNombrePanoramiques() != 0) {
                        setbDejaSauve(false);
                        getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    }
                    try {
                        choixBarrePersonnalisee();
                    } catch (IOException ex) {
                        Logger.getLogger(GestionnaireInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        btnEditerBarre.setOnMouseClicked(
                (me) -> {
                    if (getiNombrePanoramiques() != 0) {
                        setbDejaSauve(false);
                        getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    }
                    EditeurPanovisu.creerEditerBarre(tfLienImageBarrePersonnalisee.getText());
                }
        );
        tfLien1BarrePersonnalisee.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrLien1BarrePersonnalisee(tfLien1BarrePersonnalisee.getText());
        });
        tfLien2BarrePersonnalisee.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrLien2BarrePersonnalisee(tfLien2BarrePersonnalisee.getText());
        });


        /*
         Listeners Boussole
         */
        tgPosBouss.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosBouss.getSelectedToggle() != null) {
                setStrPositionBoussole(tgPosBouss.getSelectedToggle().getUserData().toString());
                afficheBoussole();
            }
        });
        bdfOffsetXBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetXBoussole(new_value.doubleValue());
            afficheBoussole();
        });
        bdfOffsetYBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetYBoussole(new_value.doubleValue());
            afficheBoussole();
        });
        slTailleBoussole.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleBoussole(taille);
                afficheBoussole();
            }
        });
        cbAfficheBoussole.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                poBouss.setbValide(new_val);
                setbAfficheBoussole((boolean) new_val);
                afficheBoussole();
            }
        });
        cbAiguilleMobile.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAiguilleMobileBoussole((boolean) new_val);
            }
        });

        /*
         Listeners Bouton de Masquage
         */
        slOpaciteBoussole.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteBoussole(opac);
                afficheBoussole();
            }
        });

        tgPosMasque.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosMasque.getSelectedToggle() != null) {
                setStrPositionMasque(tgPosMasque.getSelectedToggle().getUserData().toString());
                apVisualisation.getChildren().remove(ivMasque);
                afficheMasque();
            }
        });
        bdfOffsetXMasque.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setdXMasque(new_value.doubleValue());
            afficheMasque();
        });
        bdfOffsetYMasque.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setdYMasque(new_value.doubleValue());
            afficheMasque();
        });
        slTailleMasque.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleMasque(taille);
                afficheMasque();
            }
        });
        slOpaciteMasque.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteMasque(opac);
                afficheMasque();
            }
        });
        cbMasqueNavigation.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueNavigation((boolean) new_val);
            }
        });
        cpCouleurMasques.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurMasque = cpCouleurMasques.getValue();
            changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        });
        cbAfficheMasque.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                poMasque.setbValide(new_val);
                setbAfficheMasque((boolean) new_val);
                changeCouleurMasque(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                afficheMasque();
            }
        });
        cbMasqueBoussole.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueBoussole((boolean) new_val);
            }
        });
        cbMasqueTitre.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueTitre((boolean) new_val);
            }
        });
        cbMasquePlan.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasquePlan((boolean) new_val);
            }
        });
        cbMasqueVignettes.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueVignettes((boolean) new_val);
            }
        });
        cbMasqueCombo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueCombo((boolean) new_val);
            }
        });
        cbMasqueSuivPrec.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueSuivPrec((boolean) new_val);
            }
        });
        cbMasqueHotspots.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueHotspots((boolean) new_val);
            }
        });

        /*
         Listeners Reseaux Sociaux
         */
        bdfOffsetXReseauxSociaux.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setdXReseauxSociaux(new_value.doubleValue());
            afficheReseauxSociaux();
        });

        bdfOffsetYreseauxSociaux.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setdYReseauxSociaux(new_value.doubleValue());
            afficheReseauxSociaux();
        });

        tgPosReseauxSociaux.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosReseauxSociaux.getSelectedToggle() != null) {
                setStrPositionReseauxSociaux(tgPosReseauxSociaux.getSelectedToggle().getUserData().toString());
                afficheReseauxSociaux();
            }
        });
        slTailleReseauxSociaux.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleReseauxSociaux(taille);
                afficheReseauxSociaux();
            }
        });
        slOpaciteReseauxSociaux.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteReseauxSociaux(opac);
                afficheReseauxSociaux();
            }
        });
        cbAfficheReseauxSociaux.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                poReseaux.setbValide(new_val);
                setbAfficheReseauxSociaux(new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxTwitter.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxTwitter((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxGoogle.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxGoogle((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxFacebook.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxFacebook((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxEmail.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxEmail((boolean) new_val);
                afficheReseauxSociaux();
            }
        });

        /*
         Listeners Vignettes
         */
        tgPosVignettes.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosVignettes.getSelectedToggle() != null) {
                setStrPositionVignettes(tgPosVignettes.getSelectedToggle().getUserData().toString());
                afficheVignettes();
            }
        });

        slTailleVignettes.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleImageVignettes(taille);
                afficheVignettes();
            }
        });

        slOpaciteVignettes.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteVignettes(opac);
                afficheVignettes();
            }
        });

        cbAfficheVignettes.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheVignettes(new_val);
                poVignettes.setbValide(isbAfficheVignettes());
                afficheVignettes();
            }
        });

        cpCouleurFondVignettes.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String coul = cpCouleurFondVignettes.getValue().toString().substring(2, 8);
            setStrCouleurFondVignettes("#" + coul);
            afficheVignettes();
        });
        cpCouleurTexteVignettes.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String coul = cpCouleurTexteVignettes.getValue().toString().substring(2, 8);
            setStrCouleurTexteVignettes("#" + coul);
            afficheVignettes();
        });

        cbReplieDemarrageVignettes.selectedProperty().addListener((ov, av, nv) -> {
            setbReplieDemarrageVignettes(nv);
        });


        /*
         Listeners ComboMenu
         */
        tgPosComboMenu.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosComboMenu.getSelectedToggle() != null) {
                setStrPositionXComboMenu(tgPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[1]);
                setStrPositionYComboMenu(tgPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[0]);
                afficheComboMenu();
            }
        });

        cbAfficheComboMenu.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheComboMenu(new_val);
                poComboMenu.setbValide(isbAfficheComboMenu());
                afficheComboMenu();
            }
        });
        cbAfficheComboMenuImages.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheComboMenuImages((boolean) new_val);
                afficheComboMenu();
            }
        });

        bdfOffsetXComboMenu.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetXComboMenu(new_value.doubleValue());
            afficheComboMenu();
        });

        bdfOffsetYComboMenu.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetYComboMenu(new_value.doubleValue());
            afficheComboMenu();
        });
        /*
         Listeners BoutonVisiteAuto
         */
        tgPosBoutonVisiteAuto.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosBoutonVisiteAuto.getSelectedToggle() != null) {
                setStrPositionXBoutonVisiteAuto(tgPosBoutonVisiteAuto.getSelectedToggle().getUserData().toString().split(":")[1]);
                setStrPositionYBoutonVisiteAuto(tgPosBoutonVisiteAuto.getSelectedToggle().getUserData().toString().split(":")[0]);
                afficheBoutonVisiteAuto();
            }
        });

        slTailleBoutonVisiteAuto.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleBoutonVisiteAuto(taille);
                afficheFenetreInfo();
                afficheBoutonVisiteAuto();
            }
        });

        getCbAfficheBoutonVisiteAuto().selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheBoutonVisiteAuto(new_val);
                poBoutonVisiteAuto.setbValide(isbAfficheBoutonVisiteAuto());
                afficheBoutonVisiteAuto();
            }
        });

        bdfOffsetXBoutonVisiteAuto.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetXBoutonVisiteAuto(new_value.doubleValue());
            afficheBoutonVisiteAuto();
        });

        bdfOffsetYBoutonVisiteAuto.numberProperty().addListener((ov, old_value, new_value) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setOffsetYBoutonVisiteAuto(new_value.doubleValue());
            afficheBoutonVisiteAuto();
        });


        /*
         Listeners Plan
         */
        cbAffichePlan.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                if (new_val) {
                    cbAfficheCarte.setSelected(false);
                }
                setbAffichePlan(new_val);
                opPlan.setbValide(isbAffichePlan());
                getTabPlan().setDisable(!isbAffichePlan());
                getMniAffichagePlan().setDisable(!isbAffichePlan());
                getIvAjouterPlan().setDisable(!isbAffichePlan());
                getMniAjouterPlan().setDisable(!isbAffichePlan());
                if (new_val) {
                    getIvAjouterPlan().setOpacity(1.0);
                } else {
                    getIvAjouterPlan().setOpacity(0.3);
                }
                affichePlan();
            }
        });
        cbAfficheRadar.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheRadar((boolean) new_val);
                affichePlan();
            }
        });

        tgPosPlan.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (tgPosPlan.getSelectedToggle() != null) {
                setStrPositionPlan(tgPosPlan.getSelectedToggle().getUserData().toString());
                affichePlan();
            }
        });
        slLargeurPlan.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setLargeurPlan(taille);
                lblLargeurPlan.setText(rbLocalisation.getString("interface.largeurPlan") + " (" + Math.round(taille) + "px )");
                affichePlan();
            }
        });
        slOpacitePlan.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpacitePlan(opac);
                affichePlan();
            }
        });

        cpCouleurFondPlan.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurFondPlan.getValue().toString().substring(2, 8);
            setCouleurFondPlan(cpCouleurFondPlan.getValue());
            setStrCouleurFondPlan(strCoul);
            affichePlan();
        });
        cpCouleurTextePlan.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurTextePlan.getValue().toString().substring(2, 8);
            setCouleurTextePlan(cpCouleurTextePlan.getValue());
            setStrCouleurTextePlan(strCoul);
            affichePlan();
        });
        slTailleRadar.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleRadar(taille);
                affichePlan();
            }
        });
        slOpaciteRadar.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opacite = (double) newValue;
                setOpaciteRadar(opacite);
                affichePlan();
            }
        });
        cpCouleurFondRadar.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurFondRadar.getValue().toString().substring(2, 8);
            setCouleurFondRadar(cpCouleurFondRadar.getValue());
            setStrCouleurFondRadar(strCoul);
            affichePlan();
        });
        cpCouleurLigneRadar.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurLigneRadar.getValue().toString().substring(2, 8);
            setCouleurLigneRadar(cpCouleurLigneRadar.getValue());
            setStrCouleurLigneRadar(strCoul);
            affichePlan();
        });
        cbReplieDemarragePlan.selectedProperty().addListener((ov, av, nv) -> {
            setbReplieDemarragePlan(nv);
        });
        /*
         Listeners Carte
         */
        cbAfficheCarte.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (new_val != null) {
                    if ((boolean) new_val) {
                        cbAffichePlan.setSelected(false);

                        navigateurCarteOL.retireMarqueurs();
                        for (int ii = 0; ii < getiNombrePanoramiques(); ii++) {
                            CoordonneesGeographiques coord = getPanoramiquesProjet()[ii].getMarqueurGeolocatisation();
                            if (coord != null) {
                                String strFichierPano = getPanoramiquesProjet()[ii]
                                        .getStrNomFichier().substring(getPanoramiquesProjet()[ii].getStrNomFichier()
                                                .lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[ii]
                                                .getStrNomFichier().length()).split("\\.")[0];
                                String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-weight:bold;font-size : 12px;'>"
                                        + getPanoramiquesProjet()[ii].getStrTitrePanoramique()
                                        + "</span><br/>"
                                        + "<span style='font-family : Verdana,Arial,sans-serif;bold;font-size : 10px;'>"
                                        + strFichierPano
                                        + "</span>";
                                strHTML = strHTML.replace("\\", "/");
                                navigateurCarteOL.ajouteMarqueur(ii, coord, strHTML);
                                navigateurCarteOL.allerCoordonnees(coord, iFacteurZoomCarte);
                            }
                        }
                        if (getCoordCentreCarte() != null) {
                            navigateurCarteOL.allerCoordonnees(getCoordCentreCarte(), iFacteurZoomCarte);
                        }
                    }
                    setbAfficheCarte(new_val);
                    poCarte.setbValide(isbAfficheCarte());
                    afficheCarte();
                }
            }
        });

        cbAfficheRadarCarte.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (new_val != null) {
                    if (!new_val) {
                        navigateurCarteOL.retireRadar();
                    }
                    setbAfficheRadarCarte((boolean) new_val);
                    afficheCarte();
                }
            }
        });

        slZoomCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    iFacteurZoomCarte = (int) Math.round((double) newValue);
                    navigateurCarteOL.choixZoom(iFacteurZoomCarte);
                }
            }
        });
        btnChoixCentreCarte.setOnAction((e) -> {
            if (isbInternet()) {
                setCoordCentreCarte(navigateurCarteOL.recupereCoordonnees());
            }
        });
        btnRecentreCarte.setOnAction((e) -> {
            if (isbInternet()) {
                if (getCoordCentreCarte() != null) {
                    navigateurCarteOL.allerCoordonnees(getCoordCentreCarte(), iFacteurZoomCarte);
                }
            }
        });

        btnChoixCarte.setOnAction((e) -> {
            if (isbInternet()) {
                AnchorPane apOpenLayers = new AnchorPane();
                navigateurCarteOL.afficheCartesOpenlayer();
                apOpenLayers.setPrefSize(240, navigateurCarteOL.getApChoixCartographie().getPrefHeight() + 10);
                navigateurCarteOL.getApChoixCartographie().setLayoutY(10);
                apOpenLayers.setStyle("-fx-background-color : -fx-base;"
                        + "-fx-border-color: derive(-fx-base,10%);"
                        + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                        + "-fx-border-width: 1px;");
                apFenetreAide.getChildren().addAll(apOpenLayers);
                Button btnFerme = new Button("X");
                btnFerme.setLayoutY(10);
                btnFerme.setLayoutX(205);
                apOpenLayers.getChildren().addAll(navigateurCarteOL.getApChoixCartographie(), btnFerme);
                apOpenLayers.setLayoutX((iLargeur - apOpenLayers.getPrefWidth()) / 2);
                apOpenLayers.setLayoutY((iHauteur - apOpenLayers.getPrefHeight()) / 2);
                apEnvironnement.getChildren().add(apOpenLayers);
                mbarPrincipal.setDisable(true);
                hbBarreBouton.setDisable(true);
                tpEnvironnement.setDisable(true);
                btnFerme.setOnAction((ev) -> {
                    apEnvironnement.getChildren().remove(apOpenLayers);
                    mbarPrincipal.setDisable(false);
                    hbBarreBouton.setDisable(false);
                    tpEnvironnement.setDisable(false);
                });
            }
        });
        btnRechercheAdresse.setOnAction((e) -> {
            if (isbInternet()) {
                navigateurCarteOL.allerAdresse(tfAdresseCarte.getText(), iFacteurZoomCarte);
            }
        });
        tfAdresseCarte.setOnKeyPressed((e) -> {
            if (isbInternet()) {
                if (e.getCode() == KeyCode.ENTER) {
                    navigateurCarteOL.allerAdresse(tfAdresseCarte.getText(), 17);
                }
            }
        });

        tgPosCarte.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (tgPosCarte.getSelectedToggle() != null) {
                    setStrPositionCarte(tgPosCarte.getSelectedToggle().getUserData().toString());
                    afficheCarte();
                }
            }
        });
        slLargeurCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    double taille = (double) newValue;
                    setLargeurCarte(taille);
                    lblLargeurCarte.setText(rbLocalisation.getString("interface.largeurCarte") + " (" + Math.round(taille) + "px)");
                    afficheCarte();
                }
            }
        });
        slHauteurCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    double taille = (double) newValue;
                    setHauteurCarte(taille);
                    lblHauteurCarte.setText(rbLocalisation.getString("interface.hauteurCarte") + " (" + Math.round(taille) + "px)");
                    afficheCarte();
                }
            }
        });
        slZoomCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    int taille = (int) Math.round((double) newValue);
                    setiFacteurZoomCarte(taille);
                    lblZoomCarte.setText(rbLocalisation.getString("interface.zoomCarte") + " (" + Math.round(taille) + ")");
                    afficheCarte();
                }
            }
        });
        slOpaciteCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    double opac = (double) newValue;
                    setOpaciteCarte(opac);
                    afficheCarte();
                }
            }
        });

        cpCouleurFondCarte.valueProperty().addListener((ov, av, nv) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                String strCoul = cpCouleurFondCarte.getValue().toString().substring(2, 8);
                setCouleurFondCarte(cpCouleurFondCarte.getValue());
                setStrCouleurFondCarte(strCoul);
                afficheCarte();
            }
        });
        cpCouleurTexteCarte.valueProperty().addListener((ov, av, nv) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                String strCoul = cpCouleurTexteCarte.getValue().toString().substring(2, 8);
                setCouleurTexteCarte(cpCouleurTexteCarte.getValue());
                setStrCouleurTexteCarte(strCoul);
                afficheCarte();
            }
        });
        slTailleRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    double taille = (double) newValue;
                    setTailleRadarCarte(taille);
                    afficheCarte();
                }
            }
        });
        slOpaciteRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (newValue != null) {
                    double opacite = (double) newValue;
                    setOpaciteRadarCarte(opacite);
                    afficheCarte();
                }
            }
        });
        slAngleRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (newValue != null) {
                    double angle = (double) newValue;
                    angleRadarCarte = angle;
                    afficheCarte();
                }
            }
        });
        slOuvertureRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (newValue != null) {
                    double angle = (double) newValue;
                    ouvertureRadarCarte = angle;
                    afficheCarte();
                }
            }
        });
        cpCouleurFondRadarCarte.setOnAction((e) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                String strCoul = cpCouleurFondRadarCarte.getValue().toString().substring(2, 8);
                setCouleurFondRadarCarte(cpCouleurFondRadarCarte.getValue());
                setStrCouleurFondRadarCarte(strCoul);
                afficheCarte();
            }
        });
        cpCouleurLigneRadarCarte.setOnAction((e) -> {
            if (isbInternet()) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                String strCoul = cpCouleurLigneRadarCarte.getValue().toString().substring(2, 8);
                setCouleurLigneRadarCarte(cpCouleurLigneRadarCarte.getValue());
                setStrCouleurLigneRadarCarte(strCoul);
                afficheCarte();
            }
        });
        cbReplieDemarrageCarte.selectedProperty().addListener((ov, av, nv) -> {
            if (isbInternet()) {
                setbReplieDemarrageCarte(nv);
            }
        });

        /*
         Listeners Menu Contextuel
         */
        cbAfficheMenuContextuel.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            poMenuContextuel.setbValide(new_val);
            setbAfficheMenuContextuel(new_val);
        });
        cbAffichePrecSuivMC.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbAffichePrecSuivMC(new_val);
        });
        cbAffichePlanetNormalMC.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbAffichePlanetNormalMC((boolean) new_val);
        });
        cbAffichePersMC1.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbAffichePersMC1((boolean) new_val);
            if (isbAffichePersMC1()) {
                tfPersLib1.setDisable(false);
                tfPersURL1.setDisable(false);
                cbAffichePersMC2.setDisable(false);
            } else {
                tfPersLib1.setDisable(true);
                tfPersURL1.setDisable(true);
                cbAffichePersMC2.setSelected(false);
                cbAffichePersMC2.setDisable(true);
            }
        });
        cbAffichePersMC2.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbAffichePersMC2((boolean) new_val);
            if (isbAffichePersMC2()) {
                tfPersLib2.setDisable(false);
                tfPersURL2.setDisable(false);
            } else {
                tfPersLib2.setDisable(true);
                tfPersURL2.setDisable(true);
            }
        });
        tfPersLib1.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrPersLib1(tfPersLib1.getText());
        });
        tfPersLib2.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrPersLib2(tfPersLib2.getText());
        });
        tfPersURL1.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrPersURL1(tfPersURL1.getText());
        });
        tfPersURL2.textProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setStrPersURL2(tfPersURL2.getText());
        });

    }

    /**
     * @return the offsetXBarreClassique
     */
    public double getOffsetXBarreClassique() {
        return offsetXBarreClassique;
    }

    /**
     * @param offsetXBarreClassique the offsetXBarreClassique to set
     */
    public void setOffsetXBarreClassique(double offsetXBarreClassique) {
        this.offsetXBarreClassique = offsetXBarreClassique;
    }

    /**
     * @return the offsetYBarreClassique
     */
    public double getOffsetYBarreClassique() {
        return offsetYBarreClassique;
    }

    /**
     * @param offsetYBarreClassique the offsetYBarreClassique to set
     */
    public void setOffsetYBarreClassique(double offsetYBarreClassique) {
        this.offsetYBarreClassique = offsetYBarreClassique;
    }

    /**
     * @return the tailleBarreClassique
     */
    public double getTailleBarreClassique() {
        return tailleBarreClassique;
    }

    /**
     * @param tailleBarreClassique the tailleBarreClassique to set
     */
    public void setTailleBarreClassique(double tailleBarreClassique) {
        this.tailleBarreClassique = tailleBarreClassique;
    }

    /**
     * @return the espacementBarreClassique
     */
    public double getEspacementBarreClassique() {
        return espacementBarreClassique;
    }

    /**
     * @param espacementBarreClassique the espacementBarreClassique to set
     */
    public void setEspacementBarreClassique(double espacementBarreClassique) {
        this.espacementBarreClassique = espacementBarreClassique;
    }

    /**
     * @return the strStyleDefautBarreClassique
     */
    public String getStrStyleDefautBarreClassique() {
        return strStyleDefautBarreClassique;
    }

    /**
     * @return the strPositionBarreClassique
     */
    public String getStrPositionBarreClassique() {
        return strPositionBarreClassique;
    }

    /**
     * @param strPositionBarreClassique the strPositionBarreClassique to set
     */
    public void setStrPositionBarreClassique(String strPositionBarreClassique) {
        this.strPositionBarreClassique = strPositionBarreClassique;
    }

    /**
     * @return the styleBarreClassique
     */
    public String getStyleBarreClassique() {
        return styleBarreClassique;
    }

    /**
     * @param styleBarreClassique the styleBarreClassique to set
     */
    public void setStyleBarreClassique(String styleBarreClassique) {
        this.styleBarreClassique = styleBarreClassique;
    }

    /**
     * @return the strDeplacementsBarreClassique
     */
    public String getStrDeplacementsBarreClassique() {
        return strDeplacementsBarreClassique;
    }

    /**
     * @param strDeplacementsBarreClassique the strDeplacementsBarreClassique to
     * set
     */
    public void setStrDeplacementsBarreClassique(String strDeplacementsBarreClassique) {
        this.strDeplacementsBarreClassique = strDeplacementsBarreClassique;
    }

    /**
     * @return the strZoomBarreClassique
     */
    public String getStrZoomBarreClassique() {
        return strZoomBarreClassique;
    }

    /**
     * @param strZoomBarreClassique the strZoomBarreClassique to set
     */
    public void setStrZoomBarreClassique(String strZoomBarreClassique) {
        this.strZoomBarreClassique = strZoomBarreClassique;
    }

    /**
     * @return the strOutilsBarreClassique
     */
    public String getStrOutilsBarreClassique() {
        return strOutilsBarreClassique;
    }

    /**
     * @param strOutilsBarreClassique the strOutilsBarreClassique to set
     */
    public void setStrOutilsBarreClassique(String strOutilsBarreClassique) {
        this.strOutilsBarreClassique = strOutilsBarreClassique;
    }

    /**
     * @return the strRotationBarreClassique
     */
    public String getStrRotationBarreClassique() {
        return strRotationBarreClassique;
    }

    /**
     * @param strRotationBarreClassique the strRotationBarreClassique to set
     */
    public void setStrRotationBarreClassique(String strRotationBarreClassique) {
        this.strRotationBarreClassique = strRotationBarreClassique;
    }

    /**
     * @return the strPleinEcranBarreClassique
     */
    public String getStrPleinEcranBarreClassique() {
        return strPleinEcranBarreClassique;
    }

    /**
     * @param strPleinEcranBarreClassique the strPleinEcranBarreClassique to set
     */
    public void setStrPleinEcranBarreClassique(String strPleinEcranBarreClassique) {
        this.strPleinEcranBarreClassique = strPleinEcranBarreClassique;
    }

    /**
     * @return the strSourisBarreClassique
     */
    public String getStrSourisBarreClassique() {
        return strSourisBarreClassique;
    }

    /**
     * @param strSourisBarreClassique the strSourisBarreClassique to set
     */
    public void setStrSourisBarreClassique(String strSourisBarreClassique) {
        this.strSourisBarreClassique = strSourisBarreClassique;
    }

    /**
     * @return the strVisibiliteBarreClassique
     */
    public String getStrVisibiliteBarreClassique() {
        return strVisibiliteBarreClassique;
    }

    /**
     * @param strVisibiliteBarreClassique the strVisibiliteBarreClassique to set
     */
    public void setStrVisibiliteBarreClassique(String strVisibiliteBarreClassique) {
        this.strVisibiliteBarreClassique = strVisibiliteBarreClassique;
    }

    /**
     * @return the bCouleurOrigineBarrePersonnalisee
     */
    public boolean isbCouleurOrigineBarrePersonnalisee() {
        return bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * @param bCouleurOrigineBarrePersonnalisee the
     * bCouleurOrigineBarrePersonnalisee to set
     */
    public void setbCouleurOrigineBarrePersonnalisee(boolean bCouleurOrigineBarrePersonnalisee) {
        this.bCouleurOrigineBarrePersonnalisee = bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * @return the iNombreZonesBarrePersonnalisee
     */
    public int getiNombreZonesBarrePersonnalisee() {
        return iNombreZonesBarrePersonnalisee;
    }

    /**
     * @param iNombreZonesBarrePersonnalisee the iNombreZonesBarrePersonnalisee
     * to set
     */
    public void setiNombreZonesBarrePersonnalisee(int iNombreZonesBarrePersonnalisee) {
        this.iNombreZonesBarrePersonnalisee = iNombreZonesBarrePersonnalisee;
    }

    /**
     * @return the offsetXBarrePersonnalisee
     */
    public double getOffsetXBarrePersonnalisee() {
        return offsetXBarrePersonnalisee;
    }

    /**
     * @param offsetXBarrePersonnalisee the offsetXBarrePersonnalisee to set
     */
    public void setOffsetXBarrePersonnalisee(double offsetXBarrePersonnalisee) {
        this.offsetXBarrePersonnalisee = offsetXBarrePersonnalisee;
    }

    /**
     * @return the offsetYBarrePersonnalisee
     */
    public double getOffsetYBarrePersonnalisee() {
        return offsetYBarrePersonnalisee;
    }

    /**
     * @param offsetYBarrePersonnalisee the offsetYBarrePersonnalisee to set
     */
    public void setOffsetYBarrePersonnalisee(double offsetYBarrePersonnalisee) {
        this.offsetYBarrePersonnalisee = offsetYBarrePersonnalisee;
    }

    /**
     * @return the tailleBarrePersonnalisee
     */
    public double getTailleBarrePersonnalisee() {
        return tailleBarrePersonnalisee;
    }

    /**
     * @param tailleBarrePersonnalisee the tailleBarrePersonnalisee to set
     */
    public void setTailleBarrePersonnalisee(double tailleBarrePersonnalisee) {
        this.tailleBarrePersonnalisee = tailleBarrePersonnalisee;
    }

    /**
     * @return the tailleIconesBarrePersonnalisee
     */
    public double getTailleIconesBarrePersonnalisee() {
        return tailleIconesBarrePersonnalisee;
    }

    /**
     * @param tailleIconesBarrePersonnalisee the tailleIconesBarrePersonnalisee
     * to set
     */
    public void setTailleIconesBarrePersonnalisee(double tailleIconesBarrePersonnalisee) {
        this.tailleIconesBarrePersonnalisee = tailleIconesBarrePersonnalisee;
    }

    /**
     * @return the strPositionBarrePersonnalisee
     */
    public String getStrPositionBarrePersonnalisee() {
        return strPositionBarrePersonnalisee;
    }

    /**
     * @param strPositionBarrePersonnalisee the strPositionBarrePersonnalisee to
     * set
     */
    public void setStrPositionBarrePersonnalisee(String strPositionBarrePersonnalisee) {
        this.strPositionBarrePersonnalisee = strPositionBarrePersonnalisee;
    }

    /**
     * @return the strDeplacementsBarrePersonnalisee
     */
    public String getStrDeplacementsBarrePersonnalisee() {
        return strDeplacementsBarrePersonnalisee;
    }

    /**
     * @param strDeplacementsBarrePersonnalisee the
     * strDeplacementsBarrePersonnalisee to set
     */
    public void setStrDeplacementsBarrePersonnalisee(String strDeplacementsBarrePersonnalisee) {
        this.strDeplacementsBarrePersonnalisee = strDeplacementsBarrePersonnalisee;
    }

    /**
     * @return the strZoomBarrePersonnalisee
     */
    public String getStrZoomBarrePersonnalisee() {
        return strZoomBarrePersonnalisee;
    }

    /**
     * @param strZoomBarrePersonnalisee the strZoomBarrePersonnalisee to set
     */
    public void setStrZoomBarrePersonnalisee(String strZoomBarrePersonnalisee) {
        this.strZoomBarrePersonnalisee = strZoomBarrePersonnalisee;
    }

    /**
     * @return the strInfoBarrePersonnalisee
     */
    public String getStrInfoBarrePersonnalisee() {
        return strInfoBarrePersonnalisee;
    }

    /**
     * @param strInfoBarrePersonnalisee the strInfoBarrePersonnalisee to set
     */
    public void setStrInfoBarrePersonnalisee(String strInfoBarrePersonnalisee) {
        this.strInfoBarrePersonnalisee = strInfoBarrePersonnalisee;
    }

    /**
     * @return the strAideBarrePersonnalisee
     */
    public String getStrAideBarrePersonnalisee() {
        return strAideBarrePersonnalisee;
    }

    /**
     * @param strAideBarrePersonnalisee the strAideBarrePersonnalisee to set
     */
    public void setStrAideBarrePersonnalisee(String strAideBarrePersonnalisee) {
        this.strAideBarrePersonnalisee = strAideBarrePersonnalisee;
    }

    /**
     * @return the strRotationBarrePersonnalisee
     */
    public String getStrRotationBarrePersonnalisee() {
        return strRotationBarrePersonnalisee;
    }

    /**
     * @param strRotationBarrePersonnalisee the strRotationBarrePersonnalisee to
     * set
     */
    public void setStrRotationBarrePersonnalisee(String strRotationBarrePersonnalisee) {
        this.strRotationBarrePersonnalisee = strRotationBarrePersonnalisee;
    }

    /**
     * @return the strPleinEcranBarrePersonnalisee
     */
    public String getStrPleinEcranBarrePersonnalisee() {
        return strPleinEcranBarrePersonnalisee;
    }

    /**
     * @param strPleinEcranBarrePersonnalisee the
     * strPleinEcranBarrePersonnalisee to set
     */
    public void setStrPleinEcranBarrePersonnalisee(String strPleinEcranBarrePersonnalisee) {
        this.strPleinEcranBarrePersonnalisee = strPleinEcranBarrePersonnalisee;
    }

    /**
     * @return the strSourisBarrePersonnalisee
     */
    public String getStrSourisBarrePersonnalisee() {
        return strSourisBarrePersonnalisee;
    }

    /**
     * @param strSourisBarrePersonnalisee the strSourisBarrePersonnalisee to set
     */
    public void setStrSourisBarrePersonnalisee(String strSourisBarrePersonnalisee) {
        this.strSourisBarrePersonnalisee = strSourisBarrePersonnalisee;
    }

    /**
     * @return the strVisibiliteBarrePersonnalisee
     */
    public String getStrVisibiliteBarrePersonnalisee() {
        return strVisibiliteBarrePersonnalisee;
    }

    /**
     * @param strVisibiliteBarrePersonnalisee the
     * strVisibiliteBarrePersonnalisee to set
     */
    public void setStrVisibiliteBarrePersonnalisee(String strVisibiliteBarrePersonnalisee) {
        this.strVisibiliteBarrePersonnalisee = strVisibiliteBarrePersonnalisee;
    }

    /**
     * @return the strLienImageBarrePersonnalisee
     */
    public String getStrLienImageBarrePersonnalisee() {
        return strLienImageBarrePersonnalisee;
    }

    /**
     * @param strLienImageBarrePersonnalisee the strLienImageBarrePersonnalisee
     * to set
     */
    public void setStrLienImageBarrePersonnalisee(String strLienImageBarrePersonnalisee) {
        this.strLienImageBarrePersonnalisee = strLienImageBarrePersonnalisee;
    }

    /**
     * @return the strLien1BarrePersonnalisee
     */
    public String getStrLien1BarrePersonnalisee() {
        return strLien1BarrePersonnalisee;
    }

    /**
     * @param strLien1BarrePersonnalisee the strLien1BarrePersonnalisee to set
     */
    public void setStrLien1BarrePersonnalisee(String strLien1BarrePersonnalisee) {
        this.strLien1BarrePersonnalisee = strLien1BarrePersonnalisee;
    }

    /**
     * @return the strLien2BarrePersonnalisee
     */
    public String getStrLien2BarrePersonnalisee() {
        return strLien2BarrePersonnalisee;
    }

    /**
     * @param strLien2BarrePersonnalisee the strLien2BarrePersonnalisee to set
     */
    public void setStrLien2BarrePersonnalisee(String strLien2BarrePersonnalisee) {
        this.strLien2BarrePersonnalisee = strLien2BarrePersonnalisee;
    }

    /**
     * @return the wiBarrePersonnaliseeCouleur
     */
    public WritableImage getWiBarrePersonnaliseeCouleur() {
        return wiBarrePersonnaliseeCouleur;
    }

    /**
     * @param wiBarrePersonnaliseeCouleur the wiBarrePersonnaliseeCouleur to set
     */
    public void setWiBarrePersonnaliseeCouleur(WritableImage wiBarrePersonnaliseeCouleur) {
        this.wiBarrePersonnaliseeCouleur = wiBarrePersonnaliseeCouleur;
    }

    /**
     * @return the bAfficheTitre
     */
    public boolean isbAfficheTitre() {
        return bAfficheTitre;
    }

    /**
     * @param bAfficheTitre the bAfficheTitre to set
     */
    public void setbAfficheTitre(boolean bAfficheTitre) {
        this.bAfficheTitre = bAfficheTitre;
    }

    /**
     * @return the strTitrePoliceNom
     */
    public String getStrTitrePoliceNom() {
        return strTitrePoliceNom;
    }

    /**
     * @param strTitrePoliceNom the strTitrePoliceNom to set
     */
    public void setStrTitrePoliceNom(String strTitrePoliceNom) {
        this.strTitrePoliceNom = strTitrePoliceNom;
    }

    /**
     * @return the strTitrePoliceStyle
     */
    public String getStrTitrePoliceStyle() {
        return strTitrePoliceStyle;
    }

    /**
     * @param strTitrePoliceStyle the strTitrePoliceStyle to set
     */
    public void setStrTitrePoliceStyle(String strTitrePoliceStyle) {
        this.strTitrePoliceStyle = strTitrePoliceStyle;
    }

    /**
     * @return the strTitrePoliceTaille
     */
    public String getStrTitrePoliceTaille() {
        return strTitrePoliceTaille;
    }

    /**
     * @param strTitrePoliceTaille the strTitrePoliceTaille to set
     */
    public void setStrTitrePoliceTaille(String strTitrePoliceTaille) {
        this.strTitrePoliceTaille = strTitrePoliceTaille;
    }

    /**
     * @return the strCouleurTitre
     */
    public String getStrCouleurTitre() {
        return strCouleurTitre;
    }

    /**
     * @param strCouleurTitre the strCouleurTitre to set
     */
    public void setStrCouleurTitre(String strCouleurTitre) {
        this.strCouleurTitre = strCouleurTitre;
    }

    /**
     * @return the strCouleurFondTitre
     */
    public String getStrCouleurFondTitre() {
        return strCouleurFondTitre;
    }

    /**
     * @param strCouleurFondTitre the strCouleurFondTitre to set
     */
    public void setStrCouleurFondTitre(String strCouleurFondTitre) {
        this.strCouleurFondTitre = strCouleurFondTitre;
    }

    /**
     * @return the titreOpacite
     */
    public double getTitreOpacite() {
        return titreOpacite;
    }

    /**
     * @param titreOpacite the titreOpacite to set
     */
    public void setTitreOpacite(double titreOpacite) {
        this.titreOpacite = titreOpacite;
    }

    /**
     * @return the titreTaille
     */
    public double getTitreTaille() {
        return titreTaille;
    }

    /**
     * @param titreTaille the titreTaille to set
     */
    public void setTitreTaille(double titreTaille) {
        this.titreTaille = titreTaille;
    }

    /**
     * @return the strCouleurDiaporama
     */
    public String getStrCouleurDiaporama() {
        return strCouleurDiaporama;
    }

    /**
     * @param strCouleurDiaporama the strCouleurDiaporama to set
     */
    public void setStrCouleurDiaporama(String strCouleurDiaporama) {
        this.strCouleurDiaporama = strCouleurDiaporama;
    }

    /**
     * @return the diaporamaOpacite
     */
    public double getDiaporamaOpacite() {
        return diaporamaOpacite;
    }

    /**
     * @param diaporamaOpacite the diaporamaOpacite to set
     */
    public void setDiaporamaOpacite(double diaporamaOpacite) {
        this.diaporamaOpacite = diaporamaOpacite;
    }

    /**
     * @return the bAfficheBoussole
     */
    public boolean isbAfficheBoussole() {
        return bAfficheBoussole;
    }

    /**
     * @param bAfficheBoussole the bAfficheBoussole to set
     */
    public void setbAfficheBoussole(boolean bAfficheBoussole) {
        this.bAfficheBoussole = bAfficheBoussole;
    }

    /**
     * @return the strImageBoussole
     */
    public String getStrImageBoussole() {
        return strImageBoussole;
    }

    /**
     * @param strImageBoussole the strImageBoussole to set
     */
    public void setStrImageBoussole(String strImageBoussole) {
        this.strImageBoussole = strImageBoussole;
    }

    /**
     * @return the strPositionBoussole
     */
    public String getStrPositionBoussole() {
        return strPositionBoussole;
    }

    /**
     * @param strPositionBoussole the strPositionBoussole to set
     */
    public void setStrPositionBoussole(String strPositionBoussole) {
        this.strPositionBoussole = strPositionBoussole;
    }

    /**
     * @return the offsetXBoussole
     */
    public double getOffsetXBoussole() {
        return offsetXBoussole;
    }

    /**
     * @param offsetXBoussole the offsetXBoussole to set
     */
    public void setOffsetXBoussole(double offsetXBoussole) {
        this.offsetXBoussole = offsetXBoussole;
    }

    /**
     * @return the offsetYBoussole
     */
    public double getOffsetYBoussole() {
        return offsetYBoussole;
    }

    /**
     * @param offsetYBoussole the offsetYBoussole to set
     */
    public void setOffsetYBoussole(double offsetYBoussole) {
        this.offsetYBoussole = offsetYBoussole;
    }

    /**
     * @return the tailleBoussole
     */
    public double getTailleBoussole() {
        return tailleBoussole;
    }

    /**
     * @param tailleBoussole the tailleBoussole to set
     */
    public void setTailleBoussole(double tailleBoussole) {
        this.tailleBoussole = tailleBoussole;
    }

    /**
     * @return the opaciteBoussole
     */
    public double getOpaciteBoussole() {
        return opaciteBoussole;
    }

    /**
     * @param opaciteBoussole the opaciteBoussole to set
     */
    public void setOpaciteBoussole(double opaciteBoussole) {
        this.opaciteBoussole = opaciteBoussole;
    }

    /**
     * @return the bAiguilleMobileBoussole
     */
    public boolean isbAiguilleMobileBoussole() {
        return bAiguilleMobileBoussole;
    }

    /**
     * @param bAiguilleMobileBoussole the bAiguilleMobileBoussole to set
     */
    public void setbAiguilleMobileBoussole(boolean bAiguilleMobileBoussole) {
        this.bAiguilleMobileBoussole = bAiguilleMobileBoussole;
    }

    /**
     * @return the bFenetreInfoPersonnalise
     */
    public boolean isbFenetreInfoPersonnalise() {
        return bFenetreInfoPersonnalise;
    }

    /**
     * @param bFenetreInfoPersonnalise the bFenetreInfoPersonnalise to set
     */
    public void setbFenetreInfoPersonnalise(boolean bFenetreInfoPersonnalise) {
        this.bFenetreInfoPersonnalise = bFenetreInfoPersonnalise;
    }

    /**
     * @return the bFenetreAidePersonnalise
     */
    public boolean isbFenetreAidePersonnalise() {
        return bFenetreAidePersonnalise;
    }

    /**
     * @param bFenetreAidePersonnalise the bFenetreAidePersonnalise to set
     */
    public void setbFenetreAidePersonnalise(boolean bFenetreAidePersonnalise) {
        this.bFenetreAidePersonnalise = bFenetreAidePersonnalise;
    }

    /**
     * @return the fenetreInfoTaille
     */
    public double getFenetreInfoTaille() {
        return fenetreInfoTaille;
    }

    /**
     * @param fenetreInfoTaille the fenetreInfoTaille to set
     */
    public void setFenetreInfoTaille(double fenetreInfoTaille) {
        this.fenetreInfoTaille = fenetreInfoTaille;
    }

    /**
     * @return the fenetreAideTaille
     */
    public double getFenetreAideTaille() {
        return fenetreAideTaille;
    }

    /**
     * @param fenetreAideTaille the fenetreAideTaille to set
     */
    public void setFenetreAideTaille(double fenetreAideTaille) {
        this.fenetreAideTaille = fenetreAideTaille;
    }

    /**
     * @return the fenetreInfoPosX
     */
    public double getFenetreInfoPosX() {
        return fenetreInfoPosX;
    }

    /**
     * @param fenetreInfoPosX the fenetreInfoPosX to set
     */
    public void setFenetreInfoPosX(double fenetreInfoPosX) {
        this.fenetreInfoPosX = fenetreInfoPosX;
    }

    /**
     * @return the fenetreInfoPosY
     */
    public double getFenetreInfoPosY() {
        return fenetreInfoPosY;
    }

    /**
     * @param fenetreInfoPosY the fenetreInfoPosY to set
     */
    public void setFenetreInfoPosY(double fenetreInfoPosY) {
        this.fenetreInfoPosY = fenetreInfoPosY;
    }

    /**
     * @return the fenetreAidePosX
     */
    public double getFenetreAidePosX() {
        return fenetreAidePosX;
    }

    /**
     * @param fenetreAidePosX the fenetreAidePosX to set
     */
    public void setFenetreAidePosX(double fenetreAidePosX) {
        this.fenetreAidePosX = fenetreAidePosX;
    }

    /**
     * @return the fenetreAidePosY
     */
    public double getFenetreAidePosY() {
        return fenetreAidePosY;
    }

    /**
     * @param fenetreAidePosY the fenetreAidePosY to set
     */
    public void setFenetreAidePosY(double fenetreAidePosY) {
        this.fenetreAidePosY = fenetreAidePosY;
    }

    /**
     * @return the fenetreInfoposX
     */
    public double getFenetreInfoposX() {
        return fenetreInfoposX;
    }

    /**
     * @param fenetreInfoposX the fenetreInfoposX to set
     */
    public void setFenetreInfoposX(double fenetreInfoposX) {
        this.fenetreInfoposX = fenetreInfoposX;
    }

    /**
     * @return the fenetreInfoOpacite
     */
    public double getFenetreInfoOpacite() {
        return fenetreInfoOpacite;
    }

    /**
     * @param fenetreInfoOpacite the fenetreInfoOpacite to set
     */
    public void setFenetreInfoOpacite(double fenetreInfoOpacite) {
        this.fenetreInfoOpacite = fenetreInfoOpacite;
    }

    /**
     * @return the fenetreAideOpacite
     */
    public double getFenetreAideOpacite() {
        return fenetreAideOpacite;
    }

    /**
     * @param fenetreAideOpacite the fenetreAideOpacite to set
     */
    public void setFenetreAideOpacite(double fenetreAideOpacite) {
        this.fenetreAideOpacite = fenetreAideOpacite;
    }

    /**
     * @return the fenetrePoliceTaille
     */
    public double getFenetrePoliceTaille() {
        return fenetrePoliceTaille;
    }

    /**
     * @param fenetrePoliceTaille the fenetrePoliceTaille to set
     */
    public void setFenetrePoliceTaille(double fenetrePoliceTaille) {
        this.fenetrePoliceTaille = fenetrePoliceTaille;
    }

    /**
     * @return the fenetreURLPosX
     */
    public double getFenetreURLPosX() {
        return fenetreURLPosX;
    }

    /**
     * @param fenetreURLPosX the fenetreURLPosX to set
     */
    public void setFenetreURLPosX(double fenetreURLPosX) {
        this.fenetreURLPosX = fenetreURLPosX;
    }

    /**
     * @return the fenetreURLPosY
     */
    public double getFenetreURLPosY() {
        return fenetreURLPosY;
    }

    /**
     * @param fenetreURLPosY the fenetreURLPosY to set
     */
    public void setFenetreURLPosY(double fenetreURLPosY) {
        this.fenetreURLPosY = fenetreURLPosY;
    }

    /**
     * @return the fenetreOpaciteFond
     */
    public double getFenetreOpaciteFond() {
        return fenetreOpaciteFond;
    }

    /**
     * @param fenetreOpaciteFond the fenetreOpaciteFond to set
     */
    public void setFenetreOpaciteFond(double fenetreOpaciteFond) {
        this.fenetreOpaciteFond = fenetreOpaciteFond;
    }

    /**
     * @return the strFenetreInfoImage
     */
    public String getStrFenetreInfoImage() {
        return strFenetreInfoImage;
    }

    /**
     * @param strFenetreInfoImage the strFenetreInfoImage to set
     */
    public void setStrFenetreInfoImage(String strFenetreInfoImage) {
        this.strFenetreInfoImage = strFenetreInfoImage;
    }

    /**
     * @return the strFenetreAideImage
     */
    public String getStrFenetreAideImage() {
        return strFenetreAideImage;
    }

    /**
     * @param strFenetreAideImage the strFenetreAideImage to set
     */
    public void setStrFenetreAideImage(String strFenetreAideImage) {
        this.strFenetreAideImage = strFenetreAideImage;
    }

    /**
     * @return the strFenetreURL
     */
    public String getStrFenetreURL() {
        return strFenetreURL;
    }

    /**
     * @param strFenetreURL the strFenetreURL to set
     */
    public void setStrFenetreURL(String strFenetreURL) {
        this.strFenetreURL = strFenetreURL;
    }

    /**
     * @return the strFenetreTexteURL
     */
    public String getStrFenetreTexteURL() {
        return strFenetreTexteURL;
    }

    /**
     * @param strFenetreTexteURL the strFenetreTexteURL to set
     */
    public void setStrFenetreTexteURL(String strFenetreTexteURL) {
        this.strFenetreTexteURL = strFenetreTexteURL;
    }

    /**
     * @return the strFenetreURLInfobulle
     */
    public String getStrFenetreURLInfobulle() {
        return strFenetreURLInfobulle;
    }

    /**
     * @param strFenetreURLInfobulle the strFenetreURLInfobulle to set
     */
    public void setStrFenetreURLInfobulle(String strFenetreURLInfobulle) {
        this.strFenetreURLInfobulle = strFenetreURLInfobulle;
    }

    /**
     * @return the strFenetreURLCouleur
     */
    public String getStrFenetreURLCouleur() {
        return strFenetreURLCouleur;
    }

    /**
     * @param strFenetreURLCouleur the strFenetreURLCouleur to set
     */
    public void setStrFenetreURLCouleur(String strFenetreURLCouleur) {
        this.strFenetreURLCouleur = strFenetreURLCouleur;
    }

    /**
     * @return the strFenetrePolice
     */
    public String getStrFenetrePolice() {
        return strFenetrePolice;
    }

    /**
     * @param strFenetrePolice the strFenetrePolice to set
     */
    public void setStrFenetrePolice(String strFenetrePolice) {
        this.strFenetrePolice = strFenetrePolice;
    }

    /**
     * @return the strFenetreCouleurFond
     */
    public String getStrFenetreCouleurFond() {
        return strFenetreCouleurFond;
    }

    /**
     * @param strFenetreCouleurFond the strFenetreCouleurFond to set
     */
    public void setStrFenetreCouleurFond(String strFenetreCouleurFond) {
        this.strFenetreCouleurFond = strFenetreCouleurFond;
    }

    /**
     * @return the bAfficheMasque
     */
    public boolean isbAfficheMasque() {
        return bAfficheMasque;
    }

    /**
     * @param bAfficheMasque the bAfficheMasque to set
     */
    public void setbAfficheMasque(boolean bAfficheMasque) {
        this.bAfficheMasque = bAfficheMasque;
    }

    /**
     * @return the strImageMasque
     */
    public String getStrImageMasque() {
        return strImageMasque;
    }

    /**
     * @param strImageMasque the strImageMasque to set
     */
    public void setStrImageMasque(String strImageMasque) {
        this.strImageMasque = strImageMasque;
    }

    /**
     * @return the strPositionMasque
     */
    public String getStrPositionMasque() {
        return strPositionMasque;
    }

    /**
     * @param strPositionMasque the strPositionMasque to set
     */
    public void setStrPositionMasque(String strPositionMasque) {
        this.strPositionMasque = strPositionMasque;
    }

    /**
     * @return the dXMasque
     */
    public double getdXMasque() {
        return dXMasque;
    }

    /**
     * @param dXMasque the dXMasque to set
     */
    public void setdXMasque(double dXMasque) {
        this.dXMasque = dXMasque;
    }

    /**
     * @return the dYMasque
     */
    public double getdYMasque() {
        return dYMasque;
    }

    /**
     * @param dYMasque the dYMasque to set
     */
    public void setdYMasque(double dYMasque) {
        this.dYMasque = dYMasque;
    }

    /**
     * @return the tailleMasque
     */
    public double getTailleMasque() {
        return tailleMasque;
    }

    /**
     * @param tailleMasque the tailleMasque to set
     */
    public void setTailleMasque(double tailleMasque) {
        this.tailleMasque = tailleMasque;
    }

    /**
     * @return the opaciteMasque
     */
    public double getOpaciteMasque() {
        return opaciteMasque;
    }

    /**
     * @param opaciteMasque the opaciteMasque to set
     */
    public void setOpaciteMasque(double opaciteMasque) {
        this.opaciteMasque = opaciteMasque;
    }

    /**
     * @return the bMasqueNavigation
     */
    public boolean isbMasqueNavigation() {
        return bMasqueNavigation;
    }

    /**
     * @param bMasqueNavigation the bMasqueNavigation to set
     */
    public void setbMasqueNavigation(boolean bMasqueNavigation) {
        this.bMasqueNavigation = bMasqueNavigation;
    }

    /**
     * @return the bMasqueBoussole
     */
    public boolean isbMasqueBoussole() {
        return bMasqueBoussole;
    }

    /**
     * @param bMasqueBoussole the bMasqueBoussole to set
     */
    public void setbMasqueBoussole(boolean bMasqueBoussole) {
        this.bMasqueBoussole = bMasqueBoussole;
    }

    /**
     * @return the bMasqueTitre
     */
    public boolean isbMasqueTitre() {
        return bMasqueTitre;
    }

    /**
     * @param bMasqueTitre the bMasqueTitre to set
     */
    public void setbMasqueTitre(boolean bMasqueTitre) {
        this.bMasqueTitre = bMasqueTitre;
    }

    /**
     * @return the bMasquePlan
     */
    public boolean isbMasquePlan() {
        return bMasquePlan;
    }

    /**
     * @param bMasquePlan the bMasquePlan to set
     */
    public void setbMasquePlan(boolean bMasquePlan) {
        this.bMasquePlan = bMasquePlan;
    }

    /**
     * @return the bMasqueReseaux
     */
    public boolean isbMasqueReseaux() {
        return bMasqueReseaux;
    }

    /**
     * @param bMasqueReseaux the bMasqueReseaux to set
     */
    public void setbMasqueReseaux(boolean bMasqueReseaux) {
        this.bMasqueReseaux = bMasqueReseaux;
    }

    /**
     * @return the bMasqueVignettes
     */
    public boolean isbMasqueVignettes() {
        return bMasqueVignettes;
    }

    /**
     * @param bMasqueVignettes the bMasqueVignettes to set
     */
    public void setbMasqueVignettes(boolean bMasqueVignettes) {
        this.bMasqueVignettes = bMasqueVignettes;
    }

    /**
     * @return the bMasqueCombo
     */
    public boolean isbMasqueCombo() {
        return bMasqueCombo;
    }

    /**
     * @param bMasqueCombo the bMasqueCombo to set
     */
    public void setbMasqueCombo(boolean bMasqueCombo) {
        this.bMasqueCombo = bMasqueCombo;
    }

    /**
     * @return the bMasqueSuivPrec
     */
    public boolean isbMasqueSuivPrec() {
        return bMasqueSuivPrec;
    }

    /**
     * @param bMasqueSuivPrec the bMasqueSuivPrec to set
     */
    public void setbMasqueSuivPrec(boolean bMasqueSuivPrec) {
        this.bMasqueSuivPrec = bMasqueSuivPrec;
    }

    /**
     * @return the bMasqueHotspots
     */
    public boolean isbMasqueHotspots() {
        return bMasqueHotspots;
    }

    /**
     * @param bMasqueHotspots the bMasqueHotspots to set
     */
    public void setbMasqueHotspots(boolean bMasqueHotspots) {
        this.bMasqueHotspots = bMasqueHotspots;
    }

    /**
     * @return the bAfficheReseauxSociaux
     */
    public boolean isbAfficheReseauxSociaux() {
        return bAfficheReseauxSociaux;
    }

    /**
     * @param bAfficheReseauxSociaux the bAfficheReseauxSociaux to set
     */
    public void setbAfficheReseauxSociaux(boolean bAfficheReseauxSociaux) {
        this.bAfficheReseauxSociaux = bAfficheReseauxSociaux;
    }

    /**
     * @return the strImageReseauxSociauxTwitter
     */
    public String getStrImageReseauxSociauxTwitter() {
        return strImageReseauxSociauxTwitter;
    }

    /**
     * @param strImageReseauxSociauxTwitter the strImageReseauxSociauxTwitter to
     * set
     */
    public void setStrImageReseauxSociauxTwitter(String strImageReseauxSociauxTwitter) {
        this.strImageReseauxSociauxTwitter = strImageReseauxSociauxTwitter;
    }

    /**
     * @return the strImageReseauxSociauxGoogle
     */
    public String getStrImageReseauxSociauxGoogle() {
        return strImageReseauxSociauxGoogle;
    }

    /**
     * @param strImageReseauxSociauxGoogle the strImageReseauxSociauxGoogle to
     * set
     */
    public void setStrImageReseauxSociauxGoogle(String strImageReseauxSociauxGoogle) {
        this.strImageReseauxSociauxGoogle = strImageReseauxSociauxGoogle;
    }

    /**
     * @return the strImageReseauxSociauxFacebook
     */
    public String getStrImageReseauxSociauxFacebook() {
        return strImageReseauxSociauxFacebook;
    }

    /**
     * @param strImageReseauxSociauxFacebook the strImageReseauxSociauxFacebook
     * to set
     */
    public void setStrImageReseauxSociauxFacebook(String strImageReseauxSociauxFacebook) {
        this.strImageReseauxSociauxFacebook = strImageReseauxSociauxFacebook;
    }

    /**
     * @return the strImageReseauxSociauxEmail
     */
    public String getStrImageReseauxSociauxEmail() {
        return strImageReseauxSociauxEmail;
    }

    /**
     * @param strImageReseauxSociauxEmail the strImageReseauxSociauxEmail to set
     */
    public void setStrImageReseauxSociauxEmail(String strImageReseauxSociauxEmail) {
        this.strImageReseauxSociauxEmail = strImageReseauxSociauxEmail;
    }

    /**
     * @return the strPositionReseauxSociaux
     */
    public String getStrPositionReseauxSociaux() {
        return strPositionReseauxSociaux;
    }

    /**
     * @param strPositionReseauxSociaux the strPositionReseauxSociaux to set
     */
    public void setStrPositionReseauxSociaux(String strPositionReseauxSociaux) {
        this.strPositionReseauxSociaux = strPositionReseauxSociaux;
    }

    /**
     * @return the dXReseauxSociaux
     */
    public double getdXReseauxSociaux() {
        return dXReseauxSociaux;
    }

    /**
     * @param dXReseauxSociaux the dXReseauxSociaux to set
     */
    public void setdXReseauxSociaux(double dXReseauxSociaux) {
        this.dXReseauxSociaux = dXReseauxSociaux;
    }

    /**
     * @return the dYReseauxSociaux
     */
    public double getdYReseauxSociaux() {
        return dYReseauxSociaux;
    }

    /**
     * @param dYReseauxSociaux the dYReseauxSociaux to set
     */
    public void setdYReseauxSociaux(double dYReseauxSociaux) {
        this.dYReseauxSociaux = dYReseauxSociaux;
    }

    /**
     * @return the tailleReseauxSociaux
     */
    public double getTailleReseauxSociaux() {
        return tailleReseauxSociaux;
    }

    /**
     * @param tailleReseauxSociaux the tailleReseauxSociaux to set
     */
    public void setTailleReseauxSociaux(double tailleReseauxSociaux) {
        this.tailleReseauxSociaux = tailleReseauxSociaux;
    }

    /**
     * @return the opaciteReseauxSociaux
     */
    public double getOpaciteReseauxSociaux() {
        return opaciteReseauxSociaux;
    }

    /**
     * @param opaciteReseauxSociaux the opaciteReseauxSociaux to set
     */
    public void setOpaciteReseauxSociaux(double opaciteReseauxSociaux) {
        this.opaciteReseauxSociaux = opaciteReseauxSociaux;
    }

    /**
     * @return the bReseauxSociauxTwitter
     */
    public boolean isbReseauxSociauxTwitter() {
        return bReseauxSociauxTwitter;
    }

    /**
     * @param bReseauxSociauxTwitter the bReseauxSociauxTwitter to set
     */
    public void setbReseauxSociauxTwitter(boolean bReseauxSociauxTwitter) {
        this.bReseauxSociauxTwitter = bReseauxSociauxTwitter;
    }

    /**
     * @return the bReseauxSociauxGoogle
     */
    public boolean isbReseauxSociauxGoogle() {
        return bReseauxSociauxGoogle;
    }

    /**
     * @param bReseauxSociauxGoogle the bReseauxSociauxGoogle to set
     */
    public void setbReseauxSociauxGoogle(boolean bReseauxSociauxGoogle) {
        this.bReseauxSociauxGoogle = bReseauxSociauxGoogle;
    }

    /**
     * @return the bReseauxSociauxFacebook
     */
    public boolean isbReseauxSociauxFacebook() {
        return bReseauxSociauxFacebook;
    }

    /**
     * @param bReseauxSociauxFacebook the bReseauxSociauxFacebook to set
     */
    public void setbReseauxSociauxFacebook(boolean bReseauxSociauxFacebook) {
        this.bReseauxSociauxFacebook = bReseauxSociauxFacebook;
    }

    /**
     * @return the bReseauxSociauxEmail
     */
    public boolean isbReseauxSociauxEmail() {
        return bReseauxSociauxEmail;
    }

    /**
     * @param bReseauxSociauxEmail the bReseauxSociauxEmail to set
     */
    public void setbReseauxSociauxEmail(boolean bReseauxSociauxEmail) {
        this.bReseauxSociauxEmail = bReseauxSociauxEmail;
    }

    /**
     * @return the bAfficheVignettes
     */
    public boolean isbAfficheVignettes() {
        return bAfficheVignettes;
    }

    /**
     * @param bAfficheVignettes the bAfficheVignettes to set
     */
    public void setbAfficheVignettes(boolean bAfficheVignettes) {
        this.bAfficheVignettes = bAfficheVignettes;
    }

    /**
     * @return the strCouleurFondVignettes
     */
    public String getStrCouleurFondVignettes() {
        return strCouleurFondVignettes;
    }

    /**
     * @param strCouleurFondVignettes the strCouleurFondVignettes to set
     */
    public void setStrCouleurFondVignettes(String strCouleurFondVignettes) {
        this.strCouleurFondVignettes = strCouleurFondVignettes;
    }

    /**
     * @return the strCouleurTexteVignettes
     */
    public String getStrCouleurTexteVignettes() {
        return strCouleurTexteVignettes;
    }

    /**
     * @param strCouleurTexteVignettes the strCouleurTexteVignettes to set
     */
    public void setStrCouleurTexteVignettes(String strCouleurTexteVignettes) {
        this.strCouleurTexteVignettes = strCouleurTexteVignettes;
    }

    /**
     * @return the strPositionVignettes
     */
    public String getStrPositionVignettes() {
        return strPositionVignettes;
    }

    /**
     * @param strPositionVignettes the strPositionVignettes to set
     */
    public void setStrPositionVignettes(String strPositionVignettes) {
        this.strPositionVignettes = strPositionVignettes;
    }

    /**
     * @return the tailleImageVignettes
     */
    public double getTailleImageVignettes() {
        return tailleImageVignettes;
    }

    /**
     * @param tailleImageVignettes the tailleImageVignettes to set
     */
    public void setTailleImageVignettes(double tailleImageVignettes) {
        this.tailleImageVignettes = tailleImageVignettes;
    }

    /**
     * @return the opaciteVignettes
     */
    public double getOpaciteVignettes() {
        return opaciteVignettes;
    }

    /**
     * @param opaciteVignettes the opaciteVignettes to set
     */
    public void setOpaciteVignettes(double opaciteVignettes) {
        this.opaciteVignettes = opaciteVignettes;
    }

    /**
     * @return the bAfficheComboMenu
     */
    public boolean isbAfficheComboMenu() {
        return bAfficheComboMenu;
    }

    /**
     * @param bAfficheComboMenu the bAfficheComboMenu to set
     */
    public void setbAfficheComboMenu(boolean bAfficheComboMenu) {
        this.bAfficheComboMenu = bAfficheComboMenu;
    }

    /**
     * @return the bAfficheComboMenuImages
     */
    public boolean isbAfficheComboMenuImages() {
        return bAfficheComboMenuImages;
    }

    /**
     * @param bAfficheComboMenuImages the bAfficheComboMenuImages to set
     */
    public void setbAfficheComboMenuImages(boolean bAfficheComboMenuImages) {
        this.bAfficheComboMenuImages = bAfficheComboMenuImages;
    }

    /**
     * @return the strPositionXComboMenu
     */
    public String getStrPositionXComboMenu() {
        return strPositionXComboMenu;
    }

    /**
     * @param strPositionXComboMenu the strPositionXComboMenu to set
     */
    public void setStrPositionXComboMenu(String strPositionXComboMenu) {
        this.strPositionXComboMenu = strPositionXComboMenu;
    }

    /**
     * @return the strPositionYComboMenu
     */
    public String getStrPositionYComboMenu() {
        return strPositionYComboMenu;
    }

    /**
     * @param strPositionYComboMenu the strPositionYComboMenu to set
     */
    public void setStrPositionYComboMenu(String strPositionYComboMenu) {
        this.strPositionYComboMenu = strPositionYComboMenu;
    }

    /**
     * @return the offsetXComboMenu
     */
    public double getOffsetXComboMenu() {
        return offsetXComboMenu;
    }

    /**
     * @param offsetXComboMenu the offsetXComboMenu to set
     */
    public void setOffsetXComboMenu(double offsetXComboMenu) {
        this.offsetXComboMenu = offsetXComboMenu;
    }

    /**
     * @return the offsetYComboMenu
     */
    public double getOffsetYComboMenu() {
        return offsetYComboMenu;
    }

    /**
     * @param offsetYComboMenu the offsetYComboMenu to set
     */
    public void setOffsetYComboMenu(double offsetYComboMenu) {
        this.offsetYComboMenu = offsetYComboMenu;
    }

    /**
     * @return the bAffichePlan
     */
    public boolean isbAffichePlan() {
        return bAffichePlan;
    }

    /**
     * @param bAffichePlan the bAffichePlan to set
     */
    public void setbAffichePlan(boolean bAffichePlan) {
        this.bAffichePlan = bAffichePlan;
    }

    /**
     * @return the strPositionPlan
     */
    public String getStrPositionPlan() {
        return strPositionPlan;
    }

    /**
     * @param strPositionPlan the strPositionPlan to set
     */
    public void setStrPositionPlan(String strPositionPlan) {
        this.strPositionPlan = strPositionPlan;
    }

    /**
     * @return the largeurPlan
     */
    public double getLargeurPlan() {
        return largeurPlan;
    }

    /**
     * @param largeurPlan the largeurPlan to set
     */
    public void setLargeurPlan(double largeurPlan) {
        this.largeurPlan = largeurPlan;
    }

    /**
     * @return the couleurFondPlan
     */
    public Color getCouleurFondPlan() {
        return couleurFondPlan;
    }

    /**
     * @param couleurFondPlan the couleurFondPlan to set
     */
    public void setCouleurFondPlan(Color couleurFondPlan) {
        this.couleurFondPlan = couleurFondPlan;
    }

    /**
     * @return the strCouleurFondPlan
     */
    public String getStrCouleurFondPlan() {
        return strCouleurFondPlan;
    }

    /**
     * @param strCouleurFondPlan the strCouleurFondPlan to set
     */
    public void setStrCouleurFondPlan(String strCouleurFondPlan) {
        this.strCouleurFondPlan = strCouleurFondPlan;
    }

    /**
     * @return the opacitePlan
     */
    public double getOpacitePlan() {
        return opacitePlan;
    }

    /**
     * @param opacitePlan the opacitePlan to set
     */
    public void setOpacitePlan(double opacitePlan) {
        this.opacitePlan = opacitePlan;
    }

    /**
     * @return the couleurTextePlan
     */
    public Color getCouleurTextePlan() {
        return couleurTextePlan;
    }

    /**
     * @param couleurTextePlan the couleurTextePlan to set
     */
    public void setCouleurTextePlan(Color couleurTextePlan) {
        this.couleurTextePlan = couleurTextePlan;
    }

    /**
     * @return the strCouleurTextePlan
     */
    public String getStrCouleurTextePlan() {
        return strCouleurTextePlan;
    }

    /**
     * @param strCouleurTextePlan the strCouleurTextePlan to set
     */
    public void setStrCouleurTextePlan(String strCouleurTextePlan) {
        this.strCouleurTextePlan = strCouleurTextePlan;
    }

    /**
     * @return the bAfficheRadar
     */
    public boolean isbAfficheRadar() {
        return bAfficheRadar;
    }

    /**
     * @param bAfficheRadar the bAfficheRadar to set
     */
    public void setbAfficheRadar(boolean bAfficheRadar) {
        this.bAfficheRadar = bAfficheRadar;
    }

    /**
     * @return the couleurLigneRadar
     */
    public Color getCouleurLigneRadar() {
        return couleurLigneRadar;
    }

    /**
     * @param couleurLigneRadar the couleurLigneRadar to set
     */
    public void setCouleurLigneRadar(Color couleurLigneRadar) {
        this.couleurLigneRadar = couleurLigneRadar;
    }

    /**
     * @return the strCouleurLigneRadar
     */
    public String getStrCouleurLigneRadar() {
        return strCouleurLigneRadar;
    }

    /**
     * @param strCouleurLigneRadar the strCouleurLigneRadar to set
     */
    public void setStrCouleurLigneRadar(String strCouleurLigneRadar) {
        this.strCouleurLigneRadar = strCouleurLigneRadar;
    }

    /**
     * @return the couleurFondRadar
     */
    public Color getCouleurFondRadar() {
        return couleurFondRadar;
    }

    /**
     * @param couleurFondRadar the couleurFondRadar to set
     */
    public void setCouleurFondRadar(Color couleurFondRadar) {
        this.couleurFondRadar = couleurFondRadar;
    }

    /**
     * @return the strCouleurFondRadar
     */
    public String getStrCouleurFondRadar() {
        return strCouleurFondRadar;
    }

    /**
     * @param strCouleurFondRadar the strCouleurFondRadar to set
     */
    public void setStrCouleurFondRadar(String strCouleurFondRadar) {
        this.strCouleurFondRadar = strCouleurFondRadar;
    }

    /**
     * @return the tailleRadar
     */
    public double getTailleRadar() {
        return tailleRadar;
    }

    /**
     * @param tailleRadar the tailleRadar to set
     */
    public void setTailleRadar(double tailleRadar) {
        this.tailleRadar = tailleRadar;
    }

    /**
     * @return the opaciteRadar
     */
    public double getOpaciteRadar() {
        return opaciteRadar;
    }

    /**
     * @param opaciteRadar the opaciteRadar to set
     */
    public void setOpaciteRadar(double opaciteRadar) {
        this.opaciteRadar = opaciteRadar;
    }

    /**
     * @return the bAfficheMenuContextuel
     */
    public boolean isbAfficheMenuContextuel() {
        return bAfficheMenuContextuel;
    }

    /**
     * @param bAfficheMenuContextuel the bAfficheMenuContextuel to set
     */
    public void setbAfficheMenuContextuel(boolean bAfficheMenuContextuel) {
        this.bAfficheMenuContextuel = bAfficheMenuContextuel;
    }

    /**
     * @return the bAffichePrecSuivMC
     */
    public boolean isbAffichePrecSuivMC() {
        return bAffichePrecSuivMC;
    }

    /**
     * @param bAffichePrecSuivMC the bAffichePrecSuivMC to set
     */
    public void setbAffichePrecSuivMC(boolean bAffichePrecSuivMC) {
        this.bAffichePrecSuivMC = bAffichePrecSuivMC;
    }

    /**
     * @return the bAffichePlanetNormalMC
     */
    public boolean isbAffichePlanetNormalMC() {
        return bAffichePlanetNormalMC;
    }

    /**
     * @param bAffichePlanetNormalMC the bAffichePlanetNormalMC to set
     */
    public void setbAffichePlanetNormalMC(boolean bAffichePlanetNormalMC) {
        this.bAffichePlanetNormalMC = bAffichePlanetNormalMC;
    }

    /**
     * @return the bAffichePersMC1
     */
    public boolean isbAffichePersMC1() {
        return bAffichePersMC1;
    }

    /**
     * @param bAffichePersMC1 the bAffichePersMC1 to set
     */
    public void setbAffichePersMC1(boolean bAffichePersMC1) {
        this.bAffichePersMC1 = bAffichePersMC1;
    }

    /**
     * @return the strPersLib1
     */
    public String getStrPersLib1() {
        return strPersLib1;
    }

    /**
     * @param strPersLib1 the strPersLib1 to set
     */
    public void setStrPersLib1(String strPersLib1) {
        this.strPersLib1 = strPersLib1;
    }

    /**
     * @return the strPersURL1
     */
    public String getStrPersURL1() {
        return strPersURL1;
    }

    /**
     * @param strPersURL1 the strPersURL1 to set
     */
    public void setStrPersURL1(String strPersURL1) {
        this.strPersURL1 = strPersURL1;
    }

    /**
     * @return the bAffichePersMC2
     */
    public boolean isbAffichePersMC2() {
        return bAffichePersMC2;
    }

    /**
     * @param bAffichePersMC2 the bAffichePersMC2 to set
     */
    public void setbAffichePersMC2(boolean bAffichePersMC2) {
        this.bAffichePersMC2 = bAffichePersMC2;
    }

    /**
     * @return the strPersLib2
     */
    public String getStrPersLib2() {
        return strPersLib2;
    }

    /**
     * @param strPersLib2 the strPersLib2 to set
     */
    public void setStrPersLib2(String strPersLib2) {
        this.strPersLib2 = strPersLib2;
    }

    /**
     * @return the strPersURL2
     */
    public String getStrPersURL2() {
        return strPersURL2;
    }

    /**
     * @param strPersURL2 the strPersURL2 to set
     */
    public void setStrPersURL2(String strPersURL2) {
        this.strPersURL2 = strPersURL2;
    }

    /**
     * @return the bSuivantPrecedent
     */
    public boolean isbSuivantPrecedent() {
        return bSuivantPrecedent;
    }

    /**
     * @param bSuivantPrecedent the bSuivantPrecedent to set
     */
    public void setbSuivantPrecedent(boolean bSuivantPrecedent) {
        this.bSuivantPrecedent = bSuivantPrecedent;
    }

    /**
     * @return the imgBoutons
     */
    public Image[] getImgBoutons() {
        return imgBoutons;
    }

    /**
     * @param imgBoutons the imgBoutons to set
     */
    public void setImgBoutons(Image[] imgBoutons) {
        this.imgBoutons = imgBoutons;
    }

    /**
     * @return the strNomImagesBoutons
     */
    public String[] getStrNomImagesBoutons() {
        return strNomImagesBoutons;
    }

    /**
     * @param strNomImagesBoutons the strNomImagesBoutons to set
     */
    public void setStrNomImagesBoutons(String[] strNomImagesBoutons) {
        this.strNomImagesBoutons = strNomImagesBoutons;
    }

    /**
     * @return the prLisBoutons
     */
    public PixelReader[] getPrLisBoutons() {
        return prLisBoutons;
    }

    /**
     * @param prLisBoutons the prLisBoutons to set
     */
    public void setPrLisBoutons(PixelReader[] prLisBoutons) {
        this.prLisBoutons = prLisBoutons;
    }

    /**
     * @return the wiNouveauxBoutons
     */
    public WritableImage[] getWiNouveauxBoutons() {
        return wiNouveauxBoutons;
    }

    /**
     * @param wiNouveauxBoutons the wiNouveauxBoutons to set
     */
    public void setWiNouveauxBoutons(WritableImage[] wiNouveauxBoutons) {
        this.wiNouveauxBoutons = wiNouveauxBoutons;
    }

    /**
     * @return the pwNouveauxBoutons
     */
    public PixelWriter[] getPwNouveauxBoutons() {
        return pwNouveauxBoutons;
    }

    /**
     * @param pwNouveauxBoutons the pwNouveauxBoutons to set
     */
    public void setPwNouveauxBoutons(PixelWriter[] pwNouveauxBoutons) {
        this.pwNouveauxBoutons = pwNouveauxBoutons;
    }

    /**
     * @return the iNombreImagesBouton
     */
    public int getiNombreImagesBouton() {
        return iNombreImagesBouton;
    }

    /**
     * @param iNombreImagesBouton the iNombreImagesBouton to set
     */
    public void setiNombreImagesBouton(int iNombreImagesBouton) {
        this.iNombreImagesBouton = iNombreImagesBouton;
    }

    /**
     * @return the imgMasque
     */
    public Image getImgMasque() {
        return imgMasque;
    }

    /**
     * @param imgMasque the imgMasque to set
     */
    public void setImgMasque(Image imgMasque) {
        this.imgMasque = imgMasque;
    }

    /**
     * @return the prLisMasque
     */
    public PixelReader getPrLisMasque() {
        return prLisMasque;
    }

    /**
     * @param prLisMasque the prLisMasque to set
     */
    public void setPrLisMasque(PixelReader prLisMasque) {
        this.prLisMasque = prLisMasque;
    }

    /**
     * @return the wiNouveauxMasque
     */
    public WritableImage getWiNouveauxMasque() {
        return wiNouveauxMasque;
    }

    /**
     * @param wiNouveauxMasque the wiNouveauxMasque to set
     */
    public void setWiNouveauxMasque(WritableImage wiNouveauxMasque) {
        this.wiNouveauxMasque = wiNouveauxMasque;
    }

    /**
     * @return the pwNouveauxMasque
     */
    public PixelWriter getPwNouveauxMasque() {
        return pwNouveauxMasque;
    }

    /**
     * @param pwNouveauxMasque the pwNouveauxMasque to set
     */
    public void setPwNouveauxMasque(PixelWriter pwNouveauxMasque) {
        this.pwNouveauxMasque = pwNouveauxMasque;
    }

    /**
     * @return the imagesFond
     */
    public ImageFond[] getImagesFond() {
        return imagesFond;
    }

    /**
     * @param imagesFond the imagesFond to set
     */
    public void setImagesFond(ImageFond[] imagesFond) {
        this.imagesFond = imagesFond;
    }

    /**
     * @return the iNombreImagesFond
     */
    public int getiNombreImagesFond() {
        return iNombreImagesFond;
    }

    /**
     * @param iNombreImagesFond the iNombreImagesFond to set
     */
    public void setiNombreImagesFond(int iNombreImagesFond) {
        this.iNombreImagesFond = iNombreImagesFond;
    }

    /**
     * @return the strStyleHotSpots
     */
    public String getStrStyleHotSpots() {
        return strStyleHotSpots;
    }

    /**
     * @param strStyleHotSpots the strStyleHotSpots to set
     */
    public void setStrStyleHotSpots(String strStyleHotSpots) {
        this.strStyleHotSpots = strStyleHotSpots;
    }

    /**
     * @return the strStyleHotSpotImages
     */
    public String getStrStyleHotSpotImages() {
        return strStyleHotSpotImages;
    }

    /**
     * @param strStyleHotSpotImages the strStyleHotSpotImages to set
     */
    public void setStrStyleHotSpotImages(String strStyleHotSpotImages) {
        this.strStyleHotSpotImages = strStyleHotSpotImages;
    }

    /**
     * @return the zonesBarrePersonnalisee
     */
    public ZoneTelecommande[] getZonesBarrePersonnalisee() {
        return zonesBarrePersonnalisee;
    }

    /**
     * @param zonesBarrePersonnalisee the zonesBarrePersonnalisee to set
     */
    public void setZonesBarrePersonnalisee(ZoneTelecommande[] zonesBarrePersonnalisee) {
        this.zonesBarrePersonnalisee = zonesBarrePersonnalisee;
    }

    /**
     * @return the strStyleHotSpotHTML
     */
    public String getStrStyleHotSpotHTML() {
        return strStyleHotSpotHTML;
    }

    /**
     * @param strStyleHotSpotHTML the strStyleHotSpotHTML to set
     */
    public void setStrStyleHotSpotHTML(String strStyleHotSpotHTML) {
        this.strStyleHotSpotHTML = strStyleHotSpotHTML;
    }

    /**
     * @return the bAfficheCarte
     */
    public boolean isbAfficheCarte() {
        return bAfficheCarte;
    }

    /**
     * @param bAfficheCarte the bAfficheCarte to set
     */
    public void setbAfficheCarte(boolean bAfficheCarte) {
        this.bAfficheCarte = bAfficheCarte;
    }

    /**
     * @return the strPositionCarte
     */
    public String getStrPositionCarte() {
        return strPositionCarte;
    }

    /**
     * @param strPositionCarte the strPositionCarte to set
     */
    public void setStrPositionCarte(String strPositionCarte) {
        this.strPositionCarte = strPositionCarte;
    }

    /**
     * @return the largeurCarte
     */
    public double getLargeurCarte() {
        return largeurCarte;
    }

    /**
     * @param largeurCarte the largeurCarte to set
     */
    public void setLargeurCarte(double largeurCarte) {
        this.largeurCarte = largeurCarte;
    }

    /**
     * @return the couleurFondCarte
     */
    public Color getCouleurFondCarte() {
        return couleurFondCarte;
    }

    /**
     * @param couleurFondCarte the couleurFondCarte to set
     */
    public void setCouleurFondCarte(Color couleurFondCarte) {
        this.couleurFondCarte = couleurFondCarte;
    }

    /**
     * @return the strCouleurFondCarte
     */
    public String getStrCouleurFondCarte() {
        return strCouleurFondCarte;
    }

    /**
     * @param strCouleurFondCarte the strCouleurFondCarte to set
     */
    public void setStrCouleurFondCarte(String strCouleurFondCarte) {
        this.strCouleurFondCarte = strCouleurFondCarte;
    }

    /**
     * @return the opaciteCarte
     */
    public double getOpaciteCarte() {
        return opaciteCarte;
    }

    /**
     * @param opaciteCarte the opaciteCarte to set
     */
    public void setOpaciteCarte(double opaciteCarte) {
        this.opaciteCarte = opaciteCarte;
    }

    /**
     * @return the couleurTexteCarte
     */
    public Color getCouleurTexteCarte() {
        return couleurTexteCarte;
    }

    /**
     * @param couleurTexteCarte the couleurTexteCarte to set
     */
    public void setCouleurTexteCarte(Color couleurTexteCarte) {
        this.couleurTexteCarte = couleurTexteCarte;
    }

    /**
     * @return the strCouleurTexteCarte
     */
    public String getStrCouleurTexteCarte() {
        return strCouleurTexteCarte;
    }

    /**
     * @param strCouleurTexteCarte the strCouleurTexteCarte to set
     */
    public void setStrCouleurTexteCarte(String strCouleurTexteCarte) {
        this.strCouleurTexteCarte = strCouleurTexteCarte;
    }

    /**
     * @return the bAfficheRadarCarte
     */
    public boolean isbAfficheRadarCarte() {
        return bAfficheRadarCarte;
    }

    /**
     * @param bAfficheRadarCarte the bAfficheRadarCarte to set
     */
    public void setbAfficheRadarCarte(boolean bAfficheRadarCarte) {
        this.bAfficheRadarCarte = bAfficheRadarCarte;
    }

    /**
     * @return the couleurLigneRadarCarte
     */
    public Color getCouleurLigneRadarCarte() {
        return couleurLigneRadarCarte;
    }

    /**
     * @param couleurLigneRadarCarte the couleurLigneRadarCarte to set
     */
    public void setCouleurLigneRadarCarte(Color couleurLigneRadarCarte) {
        this.couleurLigneRadarCarte = couleurLigneRadarCarte;
    }

    /**
     * @return the strCouleurLigneRadarCarte
     */
    public String getStrCouleurLigneRadarCarte() {
        return strCouleurLigneRadarCarte;
    }

    /**
     * @param strCouleurLigneRadarCarte the strCouleurLigneRadarCarte to set
     */
    public void setStrCouleurLigneRadarCarte(String strCouleurLigneRadarCarte) {
        this.strCouleurLigneRadarCarte = strCouleurLigneRadarCarte;
    }

    /**
     * @return the couleurFondRadarCarte
     */
    public Color getCouleurFondRadarCarte() {
        return couleurFondRadarCarte;
    }

    /**
     * @param couleurFondRadarCarte the couleurFondRadarCarte to set
     */
    public void setCouleurFondRadarCarte(Color couleurFondRadarCarte) {
        this.couleurFondRadarCarte = couleurFondRadarCarte;
    }

    /**
     * @return the strCouleurFondRadarCarte
     */
    public String getStrCouleurFondRadarCarte() {
        return strCouleurFondRadarCarte;
    }

    /**
     * @param strCouleurFondRadarCarte the strCouleurFondRadarCarte to set
     */
    public void setStrCouleurFondRadarCarte(String strCouleurFondRadarCarte) {
        this.strCouleurFondRadarCarte = strCouleurFondRadarCarte;
    }

    /**
     * @return the tailleRadarCarte
     */
    public double getTailleRadarCarte() {
        return tailleRadarCarte;
    }

    /**
     * @param tailleRadarCarte the tailleRadarCarte to set
     */
    public void setTailleRadarCarte(double tailleRadarCarte) {
        this.tailleRadarCarte = tailleRadarCarte;
    }

    /**
     * @return the opaciteRadarCarte
     */
    public double getOpaciteRadarCarte() {
        return opaciteRadarCarte;
    }

    /**
     * @param opaciteRadarCarte the opaciteRadarCarte to set
     */
    public void setOpaciteRadarCarte(double opaciteRadarCarte) {
        this.opaciteRadarCarte = opaciteRadarCarte;
    }

    /**
     * @return the hauteurCarte
     */
    public double getHauteurCarte() {
        return hauteurCarte;
    }

    /**
     * @param hauteurCarte the hauteurCarte to set
     */
    public void setHauteurCarte(double hauteurCarte) {
        this.hauteurCarte = hauteurCarte;
    }

    /**
     * @return the iFacteurZoomCarte
     */
    public int getiFacteurZoomCarte() {
        return iFacteurZoomCarte;
    }

    /**
     * @param iFacteurZoomCarte the iFacteurZoomCarte to set
     */
    public void setiFacteurZoomCarte(int iFacteurZoomCarte) {
        this.iFacteurZoomCarte = iFacteurZoomCarte;
    }

    /**
     * @return the coordCentreCarte
     */
    public CoordonneesGeographiques getCoordCentreCarte() {
        return coordCentreCarte;
    }

    /**
     * @param coordCentreCarte the coordCentreCarte to set
     */
    public void setCoordCentreCarte(CoordonneesGeographiques coordCentreCarte) {
        this.coordCentreCarte = coordCentreCarte;
    }

    /**
     * @return the slZoomCarte
     */
    public Slider getSlZoomCarte() {
        return slZoomCarte;
    }

    /**
     * @param slZoomCarte the slZoomCarte to set
     */
    public void setSlZoomCarte(Slider slZoomCarte) {
        this.slZoomCarte = slZoomCarte;
    }

    /**
     * @return the strNomLayers
     */
    public String getStrNomLayers() {
        return strNomLayers;
    }

    /**
     * @param strNomLayers the strNomLayers to set
     */
    public void setStrNomLayers(String strNomLayers) {
        this.strNomLayers = strNomLayers;
    }

    /**
     * @return the bReplieDemmarage
     */
    public boolean isbReplieDemarrageCarte() {
        return bReplieDemarrageCarte;
    }

    /**
     * @param bReplieDemarrageCarte
     */
    public void setbReplieDemarrageCarte(boolean bReplieDemarrageCarte) {
        this.bReplieDemarrageCarte = bReplieDemarrageCarte;
    }

    /**
     * @return the cbReplieDemarragePlan
     */
    public boolean isbReplieDemarragePlan() {
        return bReplieDemarragePlan;
    }

    /**
     * @param bReplieDemarragePlan the bReplieDemmaragePlan to set
     */
    public void setbReplieDemarragePlan(boolean bReplieDemarragePlan) {
        this.bReplieDemarragePlan = bReplieDemarragePlan;
    }

    /**
     * @return the bReplieDemarrageVignettes
     */
    public boolean isbReplieDemarrageVignettes() {
        return bReplieDemarrageVignettes;
    }

    /**
     * @param bReplieDemarrageVignettes the bReplieDemarrageVignettes to set
     */
    public void setbReplieDemarrageVignettes(boolean bReplieDemarrageVignettes) {
        this.bReplieDemarrageVignettes = bReplieDemarrageVignettes;
    }

    /**
     * @return the bTemplate
     */
    public boolean isbTemplate() {
        return bTemplate;
    }

    /**
     * @param bTemplate the bTemplate to set
     */
    public void setbTemplate(boolean bTemplate) {
        this.bTemplate = bTemplate;
    }

    /**
     * @return the bAfficheBoutonVisiteAuto
     */
    public boolean isbAfficheBoutonVisiteAuto() {
        return bAfficheBoutonVisiteAuto;
    }

    /**
     * @param bAfficheBoutonVisiteAuto the bAfficheBoutonVisiteAuto to set
     */
    public void setbAfficheBoutonVisiteAuto(boolean bAfficheBoutonVisiteAuto) {
        this.bAfficheBoutonVisiteAuto = bAfficheBoutonVisiteAuto;
    }

    /**
     * @return the strPositionXBoutonVisiteAuto
     */
    public String getStrPositionXBoutonVisiteAuto() {
        return strPositionXBoutonVisiteAuto;
    }

    /**
     * @param strPositionXBoutonVisiteAuto the strPositionXBoutonVisiteAuto to
     * set
     */
    public void setStrPositionXBoutonVisiteAuto(String strPositionXBoutonVisiteAuto) {
        this.strPositionXBoutonVisiteAuto = strPositionXBoutonVisiteAuto;
    }

    /**
     * @return the strPositionYBoutonVisiteAuto
     */
    public String getStrPositionYBoutonVisiteAuto() {
        return strPositionYBoutonVisiteAuto;
    }

    /**
     * @param strPositionYBoutonVisiteAuto the strPositionYBoutonVisiteAuto to
     * set
     */
    public void setStrPositionYBoutonVisiteAuto(String strPositionYBoutonVisiteAuto) {
        this.strPositionYBoutonVisiteAuto = strPositionYBoutonVisiteAuto;
    }

    /**
     * @return the offsetXBoutonVisiteAuto
     */
    public double getOffsetXBoutonVisiteAuto() {
        return offsetXBoutonVisiteAuto;
    }

    /**
     * @param offsetXBoutonVisiteAuto the offsetXBoutonVisiteAuto to set
     */
    public void setOffsetXBoutonVisiteAuto(double offsetXBoutonVisiteAuto) {
        this.offsetXBoutonVisiteAuto = offsetXBoutonVisiteAuto;
    }

    /**
     * @return the offsetYBoutonVisiteAuto
     */
    public double getOffsetYBoutonVisiteAuto() {
        return offsetYBoutonVisiteAuto;
    }

    /**
     * @param offsetYBoutonVisiteAuto the offsetYBoutonVisiteAuto to set
     */
    public void setOffsetYBoutonVisiteAuto(double offsetYBoutonVisiteAuto) {
        this.offsetYBoutonVisiteAuto = offsetYBoutonVisiteAuto;
    }

    /**
     * @return the cbAfficheBoutonVisiteAuto
     */
    public CheckBox getCbAfficheBoutonVisiteAuto() {
        return cbAfficheBoutonVisiteAuto;
    }

    /**
     * @param cbAfficheBoutonVisiteAuto the cbAfficheBoutonVisiteAuto to set
     */
    public void setCbAfficheBoutonVisiteAuto(CheckBox cbAfficheBoutonVisiteAuto) {
        this.cbAfficheBoutonVisiteAuto = cbAfficheBoutonVisiteAuto;
    }

    /**
     * @return the apBtnVA
     */
    public AnchorPane getApBtnVA() {
        return apBtnVA;
    }

    /**
     * @param apBtnVA the apBtnVA to set
     */
    public void setApBtnVA(AnchorPane apBtnVA) {
        this.apBtnVA = apBtnVA;
    }

    /**
     * @return the tailleBoutonVisiteAuto
     */
    public double getTailleBoutonVisiteAuto() {
        return tailleBoutonVisiteAuto;
    }

    /**
     * @param tailleBoutonVisiteAuto the tailleBoutonVisiteAuto to set
     */
    public void setTailleBoutonVisiteAuto(double tailleBoutonVisiteAuto) {
        this.tailleBoutonVisiteAuto = tailleBoutonVisiteAuto;
    }

    /**
     * @return the strTitrePosition
     */
    public String getStrTitrePosition() {
        return strTitrePosition;
    }

    /**
     * @param strTitrePosition the strTitrePosition to set
     */
    public void setStrTitrePosition(String strTitrePosition) {
        this.strTitrePosition = strTitrePosition;
    }

    /**
     * @return the titreDecalage
     */
    public double getTitreDecalage() {
        return titreDecalage;
    }

    /**
     * @param titreDecalage the titreDecalage to set
     */
    public void setTitreDecalage(double titreDecalage) {
        this.titreDecalage = titreDecalage;
    }

    /**
     * @return the bTitreVisite
     */
    public boolean isbTitreVisite() {
        return bTitreVisite;
    }

    /**
     * @param bTitreVisite the bTitreVisite to set
     */
    public void setbTitreVisite(boolean bTitreVisite) {
        this.bTitreVisite = bTitreVisite;
    }

    /**
     * @return the bTitrePanoramique
     */
    public boolean isbTitrePanoramique() {
        return bTitrePanoramique;
    }

    /**
     * @param bTitrePanoramique the bTitrePanoramique to set
     */
    public void setbTitrePanoramique(boolean bTitrePanoramique) {
        this.bTitrePanoramique = bTitrePanoramique;
    }

    /**
     * @return the bTitreAdapte
     */
    public boolean isbTitreAdapte() {
        return bTitreAdapte;
    }

    /**
     * @param bTitreAdapte the bTitreAdapte to set
     */
    public void setbTitreAdapte(boolean bTitreAdapte) {
        this.bTitreAdapte = bTitreAdapte;
    }

    /**
     * @return the iTailleHotspotsPanoramique
     */
    public int getiTailleHotspotsPanoramique() {
        return iTailleHotspotsPanoramique;
    }

    /**
     * @param iTailleHotspotsPanoramique the iTailleHotspotsPanoramique to set
     */
    public void setiTailleHotspotsPanoramique(int iTailleHotspotsPanoramique) {
        this.iTailleHotspotsPanoramique = iTailleHotspotsPanoramique;
    }

    /**
     * @return the iTailleHotspotsImage
     */
    public int getiTailleHotspotsImage() {
        return iTailleHotspotsImage;
    }

    /**
     * @param iTailleHotspotsImage the iTailleHotspotsImage to set
     */
    public void setiTailleHotspotsImage(int iTailleHotspotsImage) {
        this.iTailleHotspotsImage = iTailleHotspotsImage;
    }

    /**
     * @return the iTailleHotspotsHTML
     */
    public int getiTailleHotspotsHTML() {
        return iTailleHotspotsHTML;
    }

    /**
     * @param iTailleHotspotsHTML the iTailleHotspotsHTML to set
     */
    public void setiTailleHotspotsHTML(int iTailleHotspotsHTML) {
        this.iTailleHotspotsHTML = iTailleHotspotsHTML;
    }
}
