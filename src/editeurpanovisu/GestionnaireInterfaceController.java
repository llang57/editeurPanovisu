/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.mniAffichagePlan;
import static editeurpanovisu.EditeurPanovisu.mniAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.strCurrentDir;
import static editeurpanovisu.EditeurPanovisu.gestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.ivAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.iNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.iNombrePanoramiquesFichier;
import static editeurpanovisu.EditeurPanovisu.iNombrePlans;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.plans;
import static editeurpanovisu.EditeurPanovisu.strRepertAppli;
import static editeurpanovisu.EditeurPanovisu.strRepertTemp;
import static editeurpanovisu.EditeurPanovisu.tabPlan;
import static editeurpanovisu.EditeurPanovisu.strTooltipStyle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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

    private final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});
    private final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    public ImageFond[] imagesFond = new ImageFond[50];
    public int iNombreImagesFond = 0;

    private ResourceBundle rbLocalisation;
    public String strStyleHotSpots = "hotspot.png";
    public String strStyleHotSpotImages = "photo2.png";
    /*
     Variables barre de navigation classique 
     */
    public double offsetXBarreClassique = 0;
    public double offsetYBarreClassique = 10;
    public double tailleBarreClassique = 26;
    public double espacementBarreClassique = 4;
    public final String styleDefautBarreClassique = "retina";
    public String strPositionBarreClassique = "bottom:center";
    public String styleBarreClassique = styleDefautBarreClassique;
    public String strDeplacementsBarreClassique = "oui";
    public String strZoomBarreClassique = "oui";
    public String strOutilsBarreClassique = "oui";
    public String strRotationBarreClassique = "oui";
    public String strPleinEcranBarreClassique = "oui";
    public String strSourisBarreClassique = "oui";
    public String strVisibiliteBarreClassique = "oui";
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
     Variables barre de navigation classique 
     */
    public boolean bCouleurOrigineBarrePersonnalisee = true;
    public int iNombreZonesBarrePersonnalisee = 0;
    public double offsetXBarrePersonnalisee = 0;
    public double offsetYBarrePersonnalisee = 0;
    public double tailleBarrePersonnalisee = 100;
    public double tailleIconesBarrePersonnalisee = 40;
    public String strPositionBarrePersonnalisee = "bottom:right";
    public String strDeplacementsBarrePersonnalisee = "oui";
    public String strZoomBarrePersonnalisee = "oui";
    public String strInfoBarrePersonnalisee = "oui";
    public String strAideBarrePersonnalisee = "oui";
    public String strRotationBarrePersonnalisee = "oui";
    public String strPleinEcranBarrePersonnalisee = "oui";
    public String strSourisBarrePersonnalisee = "oui";
    public String strVisibiliteBarrePersonnalisee = "non";
    public String strLienImageBarrePersonnalisee = "";
    public String strLien1BarrePersonnalisee = "";
    public String strLien2BarrePersonnalisee = "";
    public ZoneTelecommande[] zonesBarrePersonnalisee = new ZoneTelecommande[50];
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
    public WritableImage wiBarrePersonnaliseeCouleur;
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

    public boolean bAfficheTitre = true;
    public String strTitrePoliceNom = "Verdana";
    public String strTitrePoliceStyle = "Regular";
    public String strTitrePoliceTaille = "12.0";
    public String strCouleurTitre = "#ffffff";
    public String strCouleurFondTitre = "#000000";
    public Double titreOpacite = 0.8;
    public Double titreTaille = 100.0;

    public String strCouleurDiaporama = "#000000";
    public Double diaporamaOpacite = 0.8;
    private ColorPicker cpCouleurDiaporama;
    private Slider slOpaciteDiaporama;
    private boolean bVisualiseDiaporama = false;
    private AnchorPane apAfficheDiapo;
    private ImageView ivDiapo;

    /**
     *
     */
    public boolean bAfficheBoussole = false;
    public String strImageBoussole = "rose3.png";
    public String strPositionBoussole = "top:right";
    private String strRepertImagesFond = "";
    private String strRepertBarrePersonnalisee;
    public double offsetXBoussole = 20;
    public double offsetYBoussole = 20;
    public double tailleBoussole = 100;
    public double opaciteBoussole = 0.8;
    public boolean bAiguilleMobileBoussole = true;
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
    public boolean bFenetreInfoPersonnalise = false;
    public boolean bFenetreAidePersonnalise = false;
    public double fenetreInfoTaille = 100.d;
    public double fenetreAideTaille = 100.d;
    public double fenetreInfoPosX = 0.d;
    public double fenetreInfoPosY = 0.d;
    public double fenetreAidePosX = 0.d;
    public double fenetreAidePosY = 0.d;
    public double fenetreInfoposX = 0.d;
    public double fenetreInfoOpacite = 1.d;
    public double fenetreAideOpacite = 1.d;
    public double fenetrePoliceTaille = 10.d;
    public double fenetreURLPosX = 0.d;
    public double fenetreURLPosY = 0.d;
    public double fenetreOpaciteFond = 0.d;
    public String strFenetreInfoImage = "";
    public String strFenetreAideImage = "";
    public String strFenetreURL = "";
    public String strFenetreTexteURL = "";
    public String strFenetreURLInfobulle = "";
    public String strFenetreURLCouleur = "#FFFF00";
    public String strFenetrePolice = "Verdana";
    public String strFenetreCouleurFond = "#ffffff";

    private CheckBox cbFenetreInfoPersonnalise;
    private CheckBox cbFenetreAidePersonnalise;
    private TextField txtFenetreInfoImage;
    private TextField txtFenetreAideImage;
    private Slider slFenetreInfoTaille;
    private Slider slFenetreAideTaille;
    private BigDecimalField bdfFenetreInfoPosX;
    private BigDecimalField bdfFenetreInfoPosY;
    private BigDecimalField bdfFenetreAidePosX;
    private BigDecimalField bdfFenetreAidePosY;
    private Slider slFenetreInfoOpacite;
    private Slider slFenetreAideOpacite;
    private TextField txtFenetreTexteURL;
    private TextField txtFenetreURL;
    private TextField txtFenetreURLInfobulle;
    private ComboBox cbFenetrePolice;
    private Slider slFenetrePoliceTaille;
    private BigDecimalField bdfFenetreURLPosX;
    private BigDecimalField bdfFenetreURLPosY;
    private ColorPicker cpFenetreCouleurFond;
    private ColorPicker cpFenetreURLCouleur;
    private Slider slFenetreOpaciteFond;

    private final AnchorPane apFenetreAfficheInfo = new AnchorPane();
    private final Label lblFenetreURL = new Label();
    /**
     *
     */
    private String repertMasques;
    public boolean bAfficheMasque = false;
    public String imageMasque = "MA.png";
    public String positionMasque = "top:right";
    public double dXMasque = 20;
    public double dYMasque = 20;
    public double tailleMasque = 30;
    public double opaciteMasque = 0.8;
    public boolean bMasqueNavigation = true;
    public boolean bMasqueBoussole = true;
    public boolean bMasqueTitre = true;
    public boolean bMasquePlan = true;
    public boolean bMasqueReseaux = true;
    public boolean bMasqueVignettes = true;
    private ImageView ivMasque;
    private BigDecimalField masqueDXSpinner;
    private BigDecimalField masqueDYSpinner;
    private Slider slTailleMasque;
    private Slider slOpaciteMasque;
    private CheckBox cbAfficheMasque;
    private CheckBox cbMasqueNavigation;
    private CheckBox cbMasqueBoussole;
    private CheckBox cbMasqueTitre;
    private CheckBox cbMasquePlan;
    private CheckBox cbMasqueReseaux;
    private CheckBox cbMasqueVignettes;
    private RadioButton rbMasqueTopLeft;
    private RadioButton rbMasqueTopRight;
    private RadioButton rbMasqueBottomLeft;
    private RadioButton rbMasqueBottomRight;

    /**
     *
     */
    private String repertReseauxSociaux;
    public boolean bAfficheReseauxSociaux = false;
    public String imageReseauxSociauxTwitter = "twitter.png";
    public String imageReseauxSociauxGoogle = "google.png";
    public String imageReseauxSociauxFacebook = "facebook.png";
    public String imageReseauxSociauxEmail = "email.png";
    public String positionReseauxSociaux = "top:right";
    public double dXReseauxSociaux = 20;
    public double dYReseauxSociaux = 20;
    public double tailleReseauxSociaux = 30;
    public double opaciteReseauxSociaux = 0.8;
    public boolean bReseauxSociauxTwitter = true;
    public boolean bReseauxSociauxGoogle = true;
    public boolean bReseauxSociauxFacebook = true;
    public boolean bReseauxSociauxEmail = true;
    private ImageView imgTwitter;
    private ImageView imgGoogle;
    private ImageView imgFacebook;
    private ImageView imgEmail;
    private BigDecimalField reseauxSociauxDXSpinner;
    private BigDecimalField reseauxSociauxDYSpinner;
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
    public boolean bAfficheVignettes = false;
    public String couleurFondVignettes = "#ffffff";
    public String couleurTexteVignettes = "#000000";
    public String positionVignettes = "bottom";
    public double tailleImageVignettes = 120;
    public double opaciteVignettes = 0.8;
    private Slider slOpaciteVignettes;
    private Slider slTailleVignettes;
    private CheckBox cbAfficheVignettes;
    private RadioButton rbVignettesLeft;
    private RadioButton rbVignettesRight;
    private RadioButton rbVignettesBottom;
    private ColorPicker cpCouleurFondVignettes;
    private ColorPicker cpCouleurTexteVignettes;
    /*
     *   variables ComboMenu
     */
    private AnchorPane apComboMenu;
    private AnchorPane apVisuComboMenu;
    public boolean bAfficheComboMenu = false;
    public boolean bAfficheComboMenuImages = true;
    public String positionXComboMenu = "left";
    public String positionYComboMenu = "top";
    public double offsetXComboMenu = 10,
            offsetYComboMenu = 10;
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
     Variable du plan
     */
    private AnchorPane apPlan;
    private AnchorPane apVisuPlan;
    public boolean bAffichePlan = false;
    public String positionPlan = "left";
    public double largeurPlan = 200;
    public Color couleurFondPlan = Color.hsb(180, 0.39, 0.5);
    public String txtCouleurFondPlan = couleurFondPlan.toString().substring(2, 8);
    public double opacitePlan = 0.8;
    public Color couleurTextePlan = Color.rgb(255, 255, 255);
    public String txtCouleurTextePlan = couleurTextePlan.toString().substring(2, 8);
    public boolean bAfficheRadar = false;
    public Color couleurLigneRadar = Color.rgb(255, 255, 0);
    public String txtCouleurLigneRadar = couleurLigneRadar.toString().substring(2, 8);
    public Color couleurFondRadar = Color.rgb(200, 0, 0);
    public String txtCouleurFondRadar = couleurFondRadar.toString().substring(2, 8);
    public double tailleRadar = 40;
    public double opaciteRadar = 0.8;

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
    private Slider slTailleRadar;
    private Slider slOpaciteRadar;
    /*
     Variables Images Fond
     */

    private AnchorPane apImageFond;
    private Double taillePanelImageFond;

    /*
     Variable du MenuContextuel
     */
    private AnchorPane apMenuContextuel;
    private AnchorPane apVisuMenuContextuel;
    public boolean bAfficheMenuContextuel = false;
    public boolean bAffichePrecSuivMC = true;
    public boolean bAffichePlanetNormalMC = true;
    public boolean bAffichePersMC1 = false;
    public String strPersLib1 = "";
    public String strPersURL1 = "";
    public boolean bAffichePersMC2 = false;
    public String strPersLib2 = "";
    public String strPersURL2 = "";

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

    public Pane tabInterface;
    private HBox hbInterface;
    private AnchorPane apVisualisation;
    private VBox vbOutils;
    private RadioButton rbClair;
    private RadioButton rbSombre;
    private RadioButton rbPerso;
    private ComboBox cbImage;
    private ImageView ivVisualisation;
    final ToggleGroup grpImage = new ToggleGroup();
    final ToggleGroup grpPositionBarreClassique = new ToggleGroup();
    final ToggleGroup grpPosBouss = new ToggleGroup();
    final ToggleGroup grpPosMasque = new ToggleGroup();
    final ToggleGroup grpPosReseauxSociaux = new ToggleGroup();
    final ToggleGroup grpPosVignettes = new ToggleGroup();
    final ToggleGroup grpPosComboMenu = new ToggleGroup();
    final ToggleGroup grpPosPlan = new ToggleGroup();
    private Image imageClaire;
    private Image imageSombre;
    private HBox hbbarreBoutons;
    private HBox hbOutils;
    private Label txtTitre;
    private ImageView ivInfo;
    private ImageView ivAide;
    private ImageView ivAutoRotation;
    private ImageView ivModeSouris;
    private ImageView ivModeSouris2;
    private ImageView ivPleinFenetre;
    private ImageView ivPleinFenetre2;
    private HBox hbZoom;
    private ImageView ivZoomPlus;
    private ImageView ivZoomMoins;
    private HBox hbDeplacements;
    private ImageView ivHaut;
    private ImageView ivBas;
    private ImageView ivGauche;
    private ImageView ivDroite;
    private ImageView ivHotSpot;
    private ImageView ivHotSpotImage;

    private String repertBoutonsPrincipal;
    private String repertHotSpots;
    private String repertHotSpotsPhoto;
    private String repertBoussoles;
    private CheckBox cbSuivantPrecedent;
    private ImageView imgSuivant;
    private ImageView imgPrecedent;
    private VBox fondSuivant;
    private VBox fondPrecedent;
    public boolean bSuivantPrecedent;
    private CheckBox cbAfficheTitre;
    private ColorPicker cpCouleurFondTitre;
    private ColorPicker cpCouleurTitre;
    private ComboBox cbListePolices;
    private Slider slTaillePolice;
    private Slider slOpacite;
    private Slider slTaille;
    private ColorPicker cpCouleurTheme;
    private ColorPicker cpCouleurHotspots;
    private ColorPicker cpCouleurHotspotsPhoto;
    private ColorPicker cpCouleurMasques;
    private Color couleurHotspots = Color.hsb(180, 0.39, 0.5);
    private Color couleurHotspotsPhoto = Color.hsb(180, 0.39, 0.5);
    private Color couleurMasque = Color.hsb(180, 0.39, 0.5);
    private Color couleurTheme = Color.hsb(180, 0.39, 0.5);
    public Image[] imageBoutons = new Image[25];
    public String[] nomImagesBoutons = new String[25];
    public PixelReader[] prLisBoutons = new PixelReader[25];
    public WritableImage[] wiNouveauxBoutons = new WritableImage[25];
    public PixelWriter[] pwNouveauxBoutons = new PixelWriter[25];
    public int iNombreImagesBouton = 0;
    public Image imgMasque;
    public PixelReader prLisMasque;
    public WritableImage wiNouveauxMasque;
    public PixelWriter pwNouveauxMasque;

    private void chargeBarre(String styleBarre, String strHotSpot, String strMA) {
        File repertBarre = new File(repertBoutonsPrincipal + File.separator + styleBarre);
        File[] Repertoires = repertBarre.listFiles(IMAGE_FILTER);
        int i = 0;
        for (File repert : Repertoires) {
            if (!repert.isDirectory()) {
                String nomFich = repert.getName();
                String nomFichComplet = repert.getAbsolutePath();
                imageBoutons[i] = new Image("file:" + nomFichComplet);
                prLisBoutons[i] = imageBoutons[i].getPixelReader();
                int width = (int) imageBoutons[i].getWidth();
                int height = (int) imageBoutons[i].getHeight();
                wiNouveauxBoutons[i] = new WritableImage(width, height);
                pwNouveauxBoutons[i] = wiNouveauxBoutons[i].getPixelWriter();
                switch (nomFich) {
                    case "aide.png":
                        ivAide = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "info.png":
                        ivInfo = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "haut.png":
                        ivHaut = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "bas.png":
                        ivBas = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "droite.png":
                        ivDroite = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "gauche.png":
                        ivGauche = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "fs.png":
                        ivPleinFenetre = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "fs2.png":
                        ivPleinFenetre2 = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "rotation.png":
                        ivAutoRotation = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "souris.png":
                        ivModeSouris = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "souris2.png":
                        ivModeSouris2 = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "zoomin.png":
                        ivZoomPlus = new ImageView(wiNouveauxBoutons[i]);
                        break;
                    case "zoomout.png":
                        ivZoomMoins = new ImageView(wiNouveauxBoutons[i]);
                        break;
                }
                nomImagesBoutons[i] = nomFich;
                i++;
            }
        }
        iNombreImagesBouton = i;
        imageBoutons[iNombreImagesBouton] = new Image("file:" + repertHotSpots + File.separator + strHotSpot);
        prLisBoutons[iNombreImagesBouton] = imageBoutons[iNombreImagesBouton].getPixelReader();
        int width = (int) imageBoutons[iNombreImagesBouton].getWidth();
        int height = (int) imageBoutons[iNombreImagesBouton].getHeight();
        wiNouveauxBoutons[iNombreImagesBouton] = new WritableImage(width, height);
        pwNouveauxBoutons[iNombreImagesBouton] = wiNouveauxBoutons[iNombreImagesBouton].getPixelWriter();
        ivHotSpot = new ImageView(wiNouveauxBoutons[iNombreImagesBouton]);
        iNombreImagesBouton = i + 1;
        imageBoutons[iNombreImagesBouton] = new Image("file:" + repertHotSpotsPhoto + File.separator + strStyleHotSpotImages);
        prLisBoutons[iNombreImagesBouton] = imageBoutons[iNombreImagesBouton].getPixelReader();
        width = (int) imageBoutons[iNombreImagesBouton].getWidth();
        height = (int) imageBoutons[iNombreImagesBouton].getHeight();
        wiNouveauxBoutons[iNombreImagesBouton] = new WritableImage(width, height);
        pwNouveauxBoutons[iNombreImagesBouton] = wiNouveauxBoutons[iNombreImagesBouton].getPixelWriter();
        ivHotSpotImage = new ImageView(wiNouveauxBoutons[iNombreImagesBouton]);

        imgMasque = new Image("file:" + repertMasques + File.separator + strMA);

        prLisMasque = imgMasque.getPixelReader();
        width = (int) imgMasque.getWidth();
        height = (int) imgMasque.getHeight();
        wiNouveauxMasque = new WritableImage(width, height);
        pwNouveauxMasque = wiNouveauxMasque.getPixelWriter();
        ivMasque = new ImageView(wiNouveauxMasque);

        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
    }

    private void sauveBarre() {

    }

    private void changeCouleurBarrePersonnalisee(double couleurFinale, double sat, double bright) {
        PixelReader prBarrePersonnalisee;
        prBarrePersonnalisee = imgPngBarrePersonnalisee.getPixelReader();
        wiBarrePersonnaliseeCouleur = new WritableImage((int) imgPngBarrePersonnalisee.getWidth(), (int) imgPngBarrePersonnalisee.getHeight());
        PixelWriter pwBarrePersonnaliseeCouleur = wiBarrePersonnaliseeCouleur.getPixelWriter();
        if (bCouleurOrigineBarrePersonnalisee) {
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

    private void changeCouleurBarreClassique(double couleurFinale, double sat, double bright) {
        for (int i = 0; i < iNombreImagesBouton - 1; i++) {
            for (int y = 0; y < imageBoutons[i].getHeight(); y++) {
                for (int x = 0; x < imageBoutons[i].getWidth(); x++) {
                    Color color = prLisBoutons[i].getColor(x, y);
                    double brightness = color.getBrightness();
                    //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
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
                    pwNouveauxBoutons[i].setColor(x, y, couleur);
                }
            }
        }

    }

    private void changeCouleurMasque(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < imgMasque.getHeight(); y++) {
            for (int x = 0; x < imgMasque.getWidth(); x++) {
                Color color = prLisMasque.getColor(x, y);
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
                pwNouveauxMasque.setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurHS(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < imageBoutons[iNombreImagesBouton - 1].getHeight(); y++) {
            for (int x = 0; x < imageBoutons[iNombreImagesBouton - 1].getWidth(); x++) {
                Color color = prLisBoutons[iNombreImagesBouton - 1].getColor(x, y);
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
                pwNouveauxBoutons[iNombreImagesBouton - 1].setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurHSPhoto(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < imageBoutons[iNombreImagesBouton].getHeight(); y++) {
            for (int x = 0; x < imageBoutons[iNombreImagesBouton].getWidth(); x++) {
                Color color = prLisBoutons[iNombreImagesBouton].getColor(x, y);
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
                pwNouveauxBoutons[iNombreImagesBouton].setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurTitre(String coul) {
        strCouleurFondTitre = "#" + coul;
        txtTitre.setStyle("-fx-background-color : " + strCouleurFondTitre);
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

        imgBoussole.setImage(new Image("file:" + repertBoussoles + File.separator + strImageBoussole));
        imgBoussole.setFitWidth(tailleBoussole);
        imgBoussole.setFitHeight(tailleBoussole);
        imgBoussole.setOpacity(opaciteBoussole);
        imgBoussole.setSmooth(true);
        String positXBoussole = strPositionBoussole.split(":")[1];
        String positYBoussole = strPositionBoussole.split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (positXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + offsetXBoussole;
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - offsetXBoussole - imgBoussole.getFitWidth();
                break;
        }
        switch (positYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - offsetYBoussole;
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + offsetYBoussole;
                break;
        }
        switch (positionVignettes) {
            case "bottom":
                if (positYBoussole.equals("bottom")) {
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - offsetYBoussole - apVisuVignettes.getPrefHeight();
                }
                break;
            case "left":
                if (positXBoussole.equals("left")) {
                    posX = ivVisualisation.getLayoutX() + offsetXBoussole + apVisuVignettes.getPrefWidth();
                }
                break;
            case "right":
                if (positXBoussole.equals("right")) {
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - offsetXBoussole - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                }
                break;
        }
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);
        imgAiguille.setOpacity(opaciteBoussole);
        imgAiguille.setVisible(bAfficheBoussole);

        imgBoussole.setOpacity(opaciteBoussole);
        imgBoussole.setVisible(bAfficheBoussole);
    }

    private void afficheImage(int index) {
        Image imgAffiche = panoramiquesProjet[index].getImagePanoramique();
        Rectangle2D viewportRect = new Rectangle2D(200, 0, 800, 600);
        ivVisualisation.setViewport(viewportRect);
        ivVisualisation.setImage(imgAffiche);
    }

    /**
     *
     */
    private void afficheMasque() {
        apVisualisation.getChildren().remove(ivMasque);
        apVisualisation.getChildren().add(ivMasque);
        ivMasque.setVisible(bAfficheMasque);
        ivMasque.setFitWidth(tailleMasque);
        ivMasque.setFitHeight(tailleMasque);
        ivMasque.setOpacity(opaciteMasque);
//        ivMasque.setSmooth(true);
        String positXMasque = positionMasque.split(":")[1];
        String positYMasque = positionMasque.split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (positXMasque) {
            case "left":
                posX = ivVisualisation.getLayoutX() + dXMasque;
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - dXMasque - ivMasque.getFitWidth();
                break;
        }
        switch (positYMasque) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - ivMasque.getFitHeight() - dYMasque;
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + dYMasque;
                break;
        }
        ivMasque.setLayoutX(posX);
        ivMasque.setLayoutY(posY);
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
        double posX;
        double posY = 0;
        double dX;
        switch (positXReseauxSociaux) {
            case "left":
                posX = ivVisualisation.getLayoutX() + dXReseauxSociaux;
                dX = imgEmail.getFitWidth() + 5;
                if (bReseauxSociauxTwitter && bAfficheReseauxSociaux) {
                    imgTwitter.setLayoutX(posX);
                    imgTwitter.setVisible(true);
                    posX += dX;

                }
                if (bReseauxSociauxGoogle && bAfficheReseauxSociaux) {
                    imgGoogle.setLayoutX(posX);
                    imgGoogle.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxFacebook && bAfficheReseauxSociaux) {
                    imgFacebook.setLayoutX(posX);
                    imgFacebook.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxEmail && bAfficheReseauxSociaux) {
                    imgEmail.setLayoutX(posX);
                    imgEmail.setVisible(true);
                    posX += dX;
                }

                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - dXReseauxSociaux - imgEmail.getFitWidth();
                dX = -(imgEmail.getFitWidth() + 5);
                if (bReseauxSociauxEmail && bAfficheReseauxSociaux) {
                    imgEmail.setLayoutX(posX);
                    imgEmail.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxFacebook && bAfficheReseauxSociaux) {
                    imgFacebook.setLayoutX(posX);
                    imgFacebook.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxGoogle && bAfficheReseauxSociaux) {
                    imgGoogle.setLayoutX(posX);
                    imgGoogle.setVisible(true);
                    posX += dX;
                }
                if (bReseauxSociauxTwitter && bAfficheReseauxSociaux) {
                    imgTwitter.setLayoutX(posX);
                    imgTwitter.setVisible(true);
                    posX += dX;
                }
                break;
        }
        switch (positYReseauxSociaux) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgEmail.getFitHeight() - dYReseauxSociaux;
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + dYReseauxSociaux;
                break;
        }
        imgTwitter.setLayoutY(posY);
        imgGoogle.setLayoutY(posY);
        imgFacebook.setLayoutY(posY);
        imgEmail.setLayoutY(posY);
    }

    public void afficheDiaporama() {
        apAfficheDiapo.getChildren().clear();
        apAfficheDiapo.setOpacity(diaporamaOpacite);
        apAfficheDiapo.setStyle("-fx-background-color : " + strCouleurDiaporama);
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
            if (bFenetreInfoPersonnalise) {
                Image imgFenetreInfo = new Image("file:" + strFenetreInfoImage);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setFitWidth(largeurInfo * fenetreInfoTaille / 100);
                ivFenetreInfo.setFitHeight(hauteurInfo * fenetreInfoTaille / 100);
                ivFenetreInfo.setPreserveRatio(true);
                ivFenetreInfo.setOpacity(fenetreInfoOpacite);
                Font fonte1 = new Font("Arial", 12);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreInfo.getFitWidth()) / 2 + fenetreInfoPosX + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreInfo.getFitHeight()) / 2 + fenetreInfoPosY + ivVisualisation.getLayoutY());
                lblFenetreURL.setText(strFenetreTexteURL);
                lblFenetreURL.impl_processCSS(true);
                lblFenetreURL.setStyle("-fx-font-size:" + Math.round(fenetrePoliceTaille * 10) / 10 + "px;-fx-font-family: \"Arial\";");
                lblFenetreURL.setTextFill(Color.valueOf(strFenetreURLCouleur));
                apFenetreAfficheInfo.getChildren().addAll(ivFenetreInfo);
                double URLPosX = (ivVisualisation.getFitWidth() - lblFenetreURL.prefWidth(-1)) / 2 + fenetreURLPosX + ivVisualisation.getLayoutX();
                double URLPosY = (ivVisualisation.getFitHeight() - lblFenetreURL.prefHeight(-1)) / 2 + fenetreURLPosY + ivVisualisation.getLayoutY();
                lblFenetreURL.relocate(URLPosX, URLPosY);
            } else {
                Image imgFenetreInfo = new Image("file:" + strRepertAppli + File.separator + "images" + File.separator + "infoDefaut.jpg");
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
            if (bFenetreAidePersonnalise) {
                Image imgFenetreAide = new Image("file:" + strFenetreAideImage);
                double largeurAide = imgFenetreAide.getWidth();
                double hauteurAide = imgFenetreAide.getHeight();
                ImageView ivFenetreAide = new ImageView(imgFenetreAide);
                ivFenetreAide.setFitWidth(largeurAide * fenetreAideTaille / 100);
                ivFenetreAide.setFitHeight(hauteurAide * fenetreAideTaille / 100);
                ivFenetreAide.setPreserveRatio(true);
                ivFenetreAide.setOpacity(fenetreAideOpacite);
                apFenetreAfficheInfo.getChildren().add(ivFenetreAide);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreAide.getFitWidth()) / 2 + fenetreAidePosX + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreAide.getFitHeight()) / 2 + fenetreAidePosY + ivVisualisation.getLayoutY());

            } else {
                Image imgFenetreInfo = new Image("file:" + strRepertAppli + File.separator + "images/aideDefaut.jpg");
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

    public void affichePlan() {

        apVisuPlan.setVisible(bAffichePlan);
        if (bAffichePlan) {
            double marge = 10.d;
            apVisuPlan.getChildren().clear();
            ImageView ivHSPlanActif = new ImageView(new Image("file:" + strRepertAppli + "/theme/plan/pointActif.png", 12, 12, true, true));
            ImageView ivHSPlan = new ImageView(new Image("file:" + strRepertAppli + "/theme/plan/point.png", 12, 12, true, true));
            Image imgPlan;
            if (iNombrePlans > 0) {
                String fichier = strRepertTemp + "/images/" + plans[gestionnairePlan.planActuel].getImagePlan();
                imgPlan = new Image(
                        "file:" + fichier,
                        largeurPlan, -1, true, true
                );
            } else {
                imgPlan = new Image(
                        "file:" + strRepertAppli + "/theme/plan/planDefaut.jpg",
                        largeurPlan, -1, true, true
                );

            }
            ImageView ivimgPlan = new ImageView(imgPlan);
            apVisuPlan.setPrefSize(imgPlan.getWidth() + marge * 2, imgPlan.getHeight() + marge * 2);
            apVisuPlan.setStyle("-fx-background-color : #" + txtCouleurFondPlan);
            ivimgPlan.setLayoutX(marge);
            ivimgPlan.setLayoutY(marge);
            apVisuPlan.getChildren().addAll(ivimgPlan, ivHSPlan);

            apVisuPlan.setOpacity(opacitePlan);
            double positionX = 0;
            double positionY;
            if (bAfficheTitre) {
                positionY = ivVisualisation.getLayoutY() + txtTitre.getHeight();
            } else {
                positionY = ivVisualisation.getLayoutY();
            }
            switch (positionPlan) {
                case "left":
                    positionX = ivVisualisation.getLayoutX();
                    break;
                case "right":
                    positionX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuPlan.getPrefWidth();
                    break;
            }
            ivHSPlan.setLayoutX(80);
            ivHSPlan.setLayoutY(40);
            ivHSPlanActif.setLayoutX(30);
            ivHSPlanActif.setLayoutY(90);
            if (bAfficheRadar) {
                Arc arcRadar = new Arc(36, 96, tailleRadar, tailleRadar, -55, 70);
                arcRadar.setType(ArcType.ROUND);
                arcRadar.setFill(couleurFondRadar);
                arcRadar.setStroke(couleurLigneRadar);
                arcRadar.setOpacity(opaciteRadar);
                apVisuPlan.getChildren().addAll(arcRadar, ivHSPlanActif);
            } else {
                apVisuPlan.getChildren().add(ivHSPlanActif);
            }
            apVisuPlan.setLayoutX(positionX);
            apVisuPlan.setLayoutY(positionY);
            if (iNombrePlans > 0) {

                String repertImagePlan = strRepertAppli + File.separator + "theme/plan";
                String imageBoussole1 = "file:" + repertImagePlan + "/aiguillePlan.png";
                Image imgBoussole1 = new Image(imageBoussole1);
                ImageView ivNord = new ImageView(imgBoussole1);

                String positX = plans[gestionnairePlan.planActuel].getPosition().split(":")[1];
                String positY = plans[gestionnairePlan.planActuel].getPosition().split(":")[0];
                positionX = 0;
                positionY = 0;
                switch (positX) {
                    case "left":
                        positionX = ivimgPlan.getLayoutX() + plans[gestionnairePlan.planActuel].getPositionX();
                        break;
                    case "right":
                        positionX = ivimgPlan.getLayoutX() + imgPlan.getWidth() - imgBoussole1.getWidth() - plans[gestionnairePlan.planActuel].getPositionX();
                        break;
                }
                switch (positY) {
                    case "top":
                        positionY = ivimgPlan.getLayoutY() + plans[gestionnairePlan.planActuel].getPositionY();
                        break;
                    case "bottom":
                        positionY = ivimgPlan.getLayoutY() + imgPlan.getHeight() - imgBoussole1.getHeight() - plans[gestionnairePlan.planActuel].getPositionY();
                        break;
                }
                ivNord.setLayoutX(positionX);
                ivNord.setLayoutY(positionY);
                ivNord.setRotate(plans[gestionnairePlan.planActuel].getDirectionNord());
                apVisuPlan.getChildren().add(ivNord);
            }
        }
    }

    private void afficheComboMenu() {
        apVisuComboMenu.getChildren().clear();
        if (bAfficheComboMenu) {
            apVisuComboMenu.setPrefWidth(302);
            apVisuComboMenu.setPrefHeight(50);
            ImageView ivImageMenu;
            if (bAfficheComboMenuImages) {
                ivImageMenu = new ImageView(new Image("file:" + strRepertAppli + "/images/menuAvecImage.jpg"));
            } else {
                ivImageMenu = new ImageView(new Image("file:" + strRepertAppli + "/images/menuSansImage.jpg"));
            }
            apVisuComboMenu.getChildren().add(ivImageMenu);
            double posX = 0, posY = 0;
            switch (positionXComboMenu) {
                case "left":
                    posX = ivVisualisation.getLayoutX() + offsetXComboMenu;
                    break;
                case "center":
                    posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth()) / 2 + offsetXComboMenu;
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth() - offsetXComboMenu;
                    break;
            }
            switch (positionYComboMenu) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + offsetYComboMenu;
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuComboMenu.getPrefHeight() - offsetYComboMenu;
                    break;
            }
            apVisuComboMenu.setLayoutX(posX);
            apVisuComboMenu.setLayoutY(posY);
        }
    }

    /**
     *
     */
    private void afficheVignettes() {
        fondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        fondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()));
        String positVert = strPositionBarreClassique.split(":")[0];
        String positHor = strPositionBarreClassique.split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (positVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + offsetYBarreClassique;
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - offsetYBarreClassique;
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - offsetYBarreClassique;
                break;
        }

        switch (positHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - offsetXBarreClassique;
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + offsetXBarreClassique;
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth()) / 2 + offsetXBarreClassique;
                break;
        }

        String positXBoussole = strPositionBoussole.split(":")[1];
        String positYBoussole = strPositionBoussole.split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (positXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + offsetXBoussole;
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - offsetXBoussole - imgBoussole.getFitWidth();
                break;
        }
        switch (positYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - offsetYBoussole;
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + offsetYBoussole;
                break;
        }

        apVisuVignettes.setVisible(bAfficheVignettes);
        if (bAfficheVignettes) {
            ImageView[] imgVignettes = new ImageView[iNombrePanoramiques];
            apVisuVignettes.getChildren().clear();
            apVisuVignettes.setOpacity(opaciteVignettes);
            apVisuVignettes.setStyle("-fx-background-color : " + couleurFondVignettes);
            switch (positionVignettes) {
                case "bottom":
                    apVisuVignettes.setPrefHeight(tailleImageVignettes / 2 + 10);
                    apVisuVignettes.setPrefWidth(ivVisualisation.getFitWidth());
                    apVisuVignettes.setMinHeight(tailleImageVignettes / 2 + 10);
                    apVisuVignettes.setMinWidth(ivVisualisation.getFitWidth());
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuVignettes.getPrefHeight());
                    if (positVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - offsetYBarreClassique - apVisuVignettes.getPrefHeight();
                    }
                    if (positYBoussole.equals("bottom")) {
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - offsetYBoussole - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (bAfficheTitre) {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - txtTitre.getHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - txtTitre.getHeight());
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(tailleImageVignettes + 10);
                    apVisuVignettes.setMinWidth(tailleImageVignettes + 10);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    if (bAfficheTitre) {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + txtTitre.getHeight());
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    fondPrecedent.setLayoutX(ivVisualisation.getLayoutX() + apVisuVignettes.getPrefWidth());
                    if (positHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + offsetXBarreClassique + apVisuVignettes.getPrefWidth();
                    }
                    if (positXBoussole.equals("left")) {
                        posX = ivVisualisation.getLayoutX() + offsetXBoussole + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (bAfficheTitre) {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - txtTitre.getHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - txtTitre.getHeight());
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(tailleImageVignettes + 10);
                    apVisuVignettes.setMinWidth(tailleImageVignettes + 10);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuVignettes.getPrefWidth());
                    if (bAfficheTitre) {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + txtTitre.getHeight());
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    fondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()) - apVisuVignettes.getPrefWidth());
                    if (positHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - offsetXBarreClassique - apVisuVignettes.getPrefWidth();
                    }
                    if (positXBoussole.equals("right")) {
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - offsetXBoussole - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
            int maxVignettes = 5;
            int nombre = (iNombrePanoramiques > maxVignettes) ? maxVignettes : iNombrePanoramiques;
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
                apVisuVignettes.getChildren().add(imgVignettes[i]);
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);

    }

    private int chercheZone(int nb) {
        int numero = -1;
        String strZone = "area-" + nb;
        for (int i = 0; i < iNombreZonesBarrePersonnalisee; i++) {
            System.out.println(i + "==>" + iNombreZonesBarrePersonnalisee);
            if (zonesBarrePersonnalisee[i].getStrIdZone().equals(strZone)) {
                numero = i;
            }
        }
        return numero;
    }

    public void afficheBarrePersonnalisee() {
        apAfficheBarrePersonnalisee.getChildren().clear();
        if (!tfLienImageBarrePersonnalisee.getText().equals("")) {
            if (strVisibiliteBarrePersonnalisee.equals("oui")) {
                changeCouleurBarrePersonnalisee(couleurBarrePersonnalisee.getHue(), couleurBarrePersonnalisee.getSaturation(), couleurBarrePersonnalisee.getBrightness());
                ivBarrePersonnalisee.setImage(wiBarrePersonnaliseeCouleur);
                ivBarrePersonnalisee.setPreserveRatio(true);
                ivBarrePersonnalisee.setSmooth(true);
                double hauteur = wiBarrePersonnaliseeCouleur.getHeight();
                double largeur = wiBarrePersonnaliseeCouleur.getWidth();
                if (largeur > hauteur) {
                    ivBarrePersonnalisee.setFitWidth(150);
                } else {
                    ivBarrePersonnalisee.setFitHeight(150);
                }
                ImageView ivAfficheBarrePersonnalisee = new ImageView(wiBarrePersonnaliseeCouleur);
                ivAfficheBarrePersonnalisee.setSmooth(true);

                apAfficheBarrePersonnalisee.getChildren().add(ivAfficheBarrePersonnalisee);
                apAfficheBarrePersonnalisee.setPrefHeight(hauteur);
                apAfficheBarrePersonnalisee.setPrefWidth(largeur);
                apAfficheBarrePersonnalisee.setScaleX(tailleBarrePersonnalisee / 100.d);
                apAfficheBarrePersonnalisee.setScaleY(tailleBarrePersonnalisee / 100.d);
                String positVert = strPositionBarrePersonnalisee.split(":")[0];
                String positHor = strPositionBarrePersonnalisee.split(":")[1];
                double ajoutX = (largeur / 2.d) * tailleBarrePersonnalisee / 100.d;
                double ajoutY = (hauteur / 2.d) * tailleBarrePersonnalisee / 100.d;
                double LX = 0;
                double LY = 0;
                switch (positVert) {
                    case "top":
                        LY = ivVisualisation.getLayoutY() + offsetYBarrePersonnalisee + ajoutY - hauteur / 2.d;
                        break;
                    case "bottom":
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - offsetYBarrePersonnalisee - ajoutY + hauteur / 2.d;
                        break;
                    case "middle":
                        LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight()) / 2.d - offsetYBarrePersonnalisee;
                        break;
                }

                switch (positHor) {
                    case "right":
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - offsetXBarrePersonnalisee - ajoutX + largeur / 2.d;
                        break;
                    case "left":
                        LX = ivVisualisation.getLayoutX() + offsetXBarrePersonnalisee + ajoutX - largeur / 2.d;
                        break;
                    case "center":
                        LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth()) / 2 + offsetXBarrePersonnalisee;
                        break;
                }
                if (bAfficheVignettes) {
                    switch (positionVignettes) {
                        case "bottom":
                            if (positVert.equals("bottom")) {
                                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - offsetYBarreClassique - apVisuVignettes.getPrefHeight() + ajoutY - hauteur / 2.d;
                            }
                            break;
                        case "left":
                            if (positHor.equals("left")) {
                                LX = ivVisualisation.getLayoutX() + offsetXBarreClassique + apVisuVignettes.getPrefWidth() + ajoutX - largeur / 2.d;
                            }
                            break;
                        case "right":
                            if (positHor.equals("right")) {
                                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - offsetXBarreClassique - apVisuVignettes.getPrefWidth() - ajoutX + largeur / 2.d;
                            }
                            break;
                    }
                }
                apAfficheBarrePersonnalisee.setLayoutX(LX);
                apAfficheBarrePersonnalisee.setLayoutY(LY);
                Circle[] cercles = new Circle[50];
                int zone = 1;
                if (strInfoBarrePersonnalisee.equals("oui")) {
                    int num = chercheZone(zone);
                    if (num != -1) {
                        ImageView ivInfoBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/info.png"));
                        ivInfoBarrePersonnalisee.setFitWidth(tailleIconesBarrePersonnalisee);
                        ivInfoBarrePersonnalisee.setFitHeight(tailleIconesBarrePersonnalisee);
                        ivInfoBarrePersonnalisee.setLayoutX(zonesBarrePersonnalisee[num].getCentre().getX() - tailleIconesBarrePersonnalisee / 2);
                        ivInfoBarrePersonnalisee.setLayoutY(zonesBarrePersonnalisee[num].getCentre().getY() - tailleIconesBarrePersonnalisee / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivInfoBarrePersonnalisee);
                    }
                    zone++;
                }
                if (strAideBarrePersonnalisee.equals("oui")) {
                    int num = chercheZone(zone);
                    if (num != -1) {
                        ImageView ivAideBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/aide.png"));
                        ivAideBarrePersonnalisee.setFitWidth(tailleIconesBarrePersonnalisee);
                        ivAideBarrePersonnalisee.setFitHeight(tailleIconesBarrePersonnalisee);
                        ivAideBarrePersonnalisee.setLayoutX(zonesBarrePersonnalisee[num].getCentre().getX() - tailleIconesBarrePersonnalisee / 2);
                        ivAideBarrePersonnalisee.setLayoutY(zonesBarrePersonnalisee[num].getCentre().getY() - tailleIconesBarrePersonnalisee / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivAideBarrePersonnalisee);
                    }
                    zone++;
                }
                if (strRotationBarrePersonnalisee.equals("oui")) {
                    int num = chercheZone(zone);
                    if (num != -1) {
                        ImageView ivRotationBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/rotation.png"));
                        ivRotationBarrePersonnalisee.setFitWidth(tailleIconesBarrePersonnalisee);
                        ivRotationBarrePersonnalisee.setFitHeight(tailleIconesBarrePersonnalisee);
                        ivRotationBarrePersonnalisee.setLayoutX(zonesBarrePersonnalisee[num].getCentre().getX() - tailleIconesBarrePersonnalisee / 2);
                        ivRotationBarrePersonnalisee.setLayoutY(zonesBarrePersonnalisee[num].getCentre().getY() - tailleIconesBarrePersonnalisee / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivRotationBarrePersonnalisee);
                    }
                    zone++;
                }
                if (strSourisBarrePersonnalisee.equals("oui")) {
                    int num = chercheZone(zone);
                    if (num != -1) {
                        ImageView ivSourisBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/souris.png"));
                        ivSourisBarrePersonnalisee.setFitWidth(tailleIconesBarrePersonnalisee);
                        ivSourisBarrePersonnalisee.setFitHeight(tailleIconesBarrePersonnalisee);
                        ivSourisBarrePersonnalisee.setLayoutX(zonesBarrePersonnalisee[num].getCentre().getX() - tailleIconesBarrePersonnalisee / 2);
                        ivSourisBarrePersonnalisee.setLayoutY(zonesBarrePersonnalisee[num].getCentre().getY() - tailleIconesBarrePersonnalisee / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivSourisBarrePersonnalisee);
                    }
                    zone++;
                }
                if (strPleinEcranBarrePersonnalisee.equals("oui")) {
                    int num = chercheZone(zone);
                    if (num != -1) {
                        ImageView ivFSBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/fs.png"));
                        ivFSBarrePersonnalisee.setFitWidth(tailleIconesBarrePersonnalisee);
                        ivFSBarrePersonnalisee.setFitHeight(tailleIconesBarrePersonnalisee);
                        ivFSBarrePersonnalisee.setLayoutX(zonesBarrePersonnalisee[num].getCentre().getX() - tailleIconesBarrePersonnalisee / 2);
                        ivFSBarrePersonnalisee.setLayoutY(zonesBarrePersonnalisee[num].getCentre().getY() - tailleIconesBarrePersonnalisee / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivFSBarrePersonnalisee);
                    }
                    zone++;
                }
            }
        }
    }

    public int lisFichierShp(String strNomFichier, ZoneTelecommande[] zonesBarre) throws UnsupportedEncodingException, IOException {
        try {
            File fileFichier = new File(strNomFichier);
            if (fileFichier.exists()) {
                int nombreZonesBarre;
                String strTexte;
                try (BufferedReader brLisFichier = new BufferedReader(new InputStreamReader(
                        new FileInputStream(strNomFichier), "UTF-8"))) {
                    strTexte = "";
                    String strLigneTexte;
                    iNombrePanoramiquesFichier = 0;
                    while ((strLigneTexte = brLisFichier.readLine()) != null) {
                        if (strLigneTexte.contains("Panoramique=>")) {
                            iNombrePanoramiquesFichier++;
                            //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                        }
                        strTexte += strLigneTexte + "\n";
                    }
                    brLisFichier.close();
                    cbDeplacementsBarrePersonnalisee.setSelected(true);
                    cbDeplacementsBarrePersonnalisee.setDisable(false);
                    strDeplacementsBarrePersonnalisee = "oui";
                    cbZoomBarrePersonnalisee.setSelected(true);
                    cbZoomBarrePersonnalisee.setDisable(false);
                    strZoomBarrePersonnalisee = "oui";
                    cbRotationBarrePersonnalisee.setSelected(true);
                    cbRotationBarrePersonnalisee.setDisable(false);
                    strRotationBarrePersonnalisee = "oui";
                    cbSourisBarrePersonnalisee.setSelected(true);
                    cbSourisBarrePersonnalisee.setDisable(false);
                    strSourisBarrePersonnalisee = "oui";
                    cbFSBarrePersonnalisee.setSelected(true);
                    cbFSBarrePersonnalisee.setDisable(false);
                    strPleinEcranBarrePersonnalisee = "oui";
                    strInfoBarrePersonnalisee = "oui";
                    strAideBarrePersonnalisee = "oui";
                    tfLien1BarrePersonnalisee.setDisable(true);
                    tfLien2BarrePersonnalisee.setDisable(true);
                    String strLigneComplete = strTexte.replace("[", "|");
                    String strLignes[] = strLigneComplete.split("\\|", 500);
                    String strLigne;
                    String[] strElementsLigne;
                    String[] strTypeElement;
                    int nbHS = 0;
                    int nbImg = 0;
                    int nbHsplan = 0;
                    nombreZonesBarre = 0;
                    for (int kk = 1; kk < strLignes.length; kk++) {
                        strLigne = strLignes[kk];
                        strElementsLigne = strLigne.split(";", 25);
                        strTypeElement = strElementsLigne[0].split(">", 2);
                        strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                        strElementsLigne[0] = strTypeElement[1];
                        if ("area".equals(strTypeElement[0])) {
                            zonesBarre[nombreZonesBarre] = new ZoneTelecommande();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "").replace("\n", "");
                                String[] valeur = strElementsLigne[i].split(":", 2);
                                //System.out.println(valeur[0] + "=" + valeur[1]);

                                switch (valeur[0]) {
                                    case "id":
                                        zonesBarre[nombreZonesBarre].setStrIdZone(valeur[1]);
                                        break;
                                    case "shape":
                                        zonesBarre[nombreZonesBarre].setStrTypeZone(valeur[1]);
                                        break;
                                    case "coords":
                                        zonesBarre[nombreZonesBarre].setStrCoordonneesZone(valeur[1]);
                                        break;
                                }
                            }
                            zonesBarre[nombreZonesBarre].calculeCentre();
                            //System.out.println("id : " + zonesBarre[nombreZonesBarre].getStrIdZone()
                            // + ", shape : " + zonesBarre[nombreZonesBarre].getStrTypeZone()
                            // + ", coord : " + zonesBarre[nombreZonesBarre].getStrCoordonneesZone()
                            // + ", centre : " + zonesBarre[nombreZonesBarre].getCentre().getX()
                            // + ";" + zonesBarre[nombreZonesBarre].getCentre().getY()
                            //);
                            switch (zonesBarre[nombreZonesBarre].getStrIdZone()) {
                                case "telUp":
                                case "telDown":
                                case "telRight":
                                case "telLeft":
                                    cbDeplacementsBarrePersonnalisee.setSelected(false);
                                    cbDeplacementsBarrePersonnalisee.setDisable(true);
                                    strDeplacementsBarrePersonnalisee = "non";
                                    break;
                                case "telZoomMoins":
                                case "telZoomPlus":
                                    cbZoomBarrePersonnalisee.setSelected(false);
                                    cbZoomBarrePersonnalisee.setDisable(true);
                                    strZoomBarrePersonnalisee = "non";
                                    break;
                                case "telInfo":
                                    strInfoBarrePersonnalisee = "non";
                                    break;
                                case "telAide":
                                    strAideBarrePersonnalisee = "non";
                                    break;
                                case "telFS":
                                    cbFSBarrePersonnalisee.setSelected(false);
                                    cbFSBarrePersonnalisee.setDisable(true);
                                    strPleinEcranBarrePersonnalisee = "non";
                                    break;
                                case "telSouris":
                                    cbSourisBarrePersonnalisee.setSelected(false);
                                    cbSourisBarrePersonnalisee.setDisable(true);
                                    strSourisBarrePersonnalisee = "non";
                                    break;
                                case "telRotation":
                                    cbRotationBarrePersonnalisee.setSelected(false);
                                    cbRotationBarrePersonnalisee.setDisable(true);
                                    strRotationBarrePersonnalisee = "non";
                                    break;
                                case "telLien-1":
                                    tfLien1BarrePersonnalisee.setDisable(false);
                                    break;
                                case "telLien-2":
                                    tfLien2BarrePersonnalisee.setDisable(false);
                                    break;
                            }
                            nombreZonesBarre++;
                        }

                    }
                }
                return nombreZonesBarre;
            } else {
                return -1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public void choixBarrePersonnalisee() throws IOException {
        strRepertBarrePersonnalisee = strRepertAppli + "/theme/telecommandes";
        File fileRepert;
        fileRepert = new File(strRepertBarrePersonnalisee);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter efShpFilter = new FileChooser.ExtensionFilter("Fichiers barre personnalisée (SHP)", "*.shp", "*.png");

        fileChooser.setInitialDirectory(fileRepert);
        fileChooser.getExtensionFilters().addAll(efShpFilter);

        File fichierImage = fileChooser.showOpenDialog(null);
        if (fichierImage != null) {
            String nomFichier = fichierImage.getAbsolutePath();
            nomFichier = nomFichier.substring(0, nomFichier.length() - 4);
            String nomFichierShp = nomFichier + ".shp";
            String nomFichierPng = nomFichier + ".png";
            File fichShp = new File(nomFichierShp);
            File fichPng = new File(nomFichierPng);
            if (fichShp.exists() && fichPng.exists()) {
                iNombreZonesBarrePersonnalisee = lisFichierShp(nomFichierShp, zonesBarrePersonnalisee);
                if (iNombreZonesBarrePersonnalisee != -1) {
                    btnEditerBarre.setDisable(false);
                    strLienImageBarrePersonnalisee = nomFichierPng;
                    tfLienImageBarrePersonnalisee.setText(nomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + nomFichierPng);
                    afficheBarrePersonnalisee();
                }
            }
        }
    }

    public void chargeBarrePersonnalisee(String nomFichier) throws IOException {
        if (nomFichier.length() > 4) {
            nomFichier = nomFichier.substring(0, nomFichier.length() - 4);
            String nomFichierShp = nomFichier + ".shp";
            String nomFichierPng = nomFichier + ".png";
            //System.out.println("Image : " + nomFichierPng);
            File fichShp = new File(nomFichierShp);
            File fichPng = new File(nomFichierPng);
            if (fichShp.exists() && fichPng.exists()) {
                iNombreZonesBarrePersonnalisee = lisFichierShp(nomFichierShp, zonesBarrePersonnalisee);
                if (iNombreZonesBarrePersonnalisee != -1) {
                    btnEditerBarre.setDisable(false);
                    tfLienImageBarrePersonnalisee.setText(nomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + nomFichierPng);
                    afficheBarrePersonnalisee();
                }
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
     * @param espacement
     */
    public void afficheBarreClassique(String position, double dX, double dY, double taille, String styleBoutons, String styleHS, double espacement) {
        String repertBoutons = "file:" + repertBoutonsPrincipal + File.separator + styleBoutons;
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation);
        if (iNombreImagesFond > 0) {
            for (int i = 0; i < iNombreImagesFond; i++) {
                ImageView ivImageFond = new ImageView(imagesFond[i].getImgFond());
                ivImageFond.setFitWidth(imagesFond[i].getTailleX());
                ivImageFond.setFitHeight(imagesFond[i].getTailleY());
                double posX = 0, posY = 0;
                switch (imagesFond[i].getPosX()) {
                    case "left":
                        posX = imagesFond[i].getOffsetX() + ivVisualisation.getLayoutX();
                        break;
                    case "center":
                        posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - imagesFond[i].getTailleX()) / 2 + imagesFond[i].getOffsetX();
                        break;
                    case "right":
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - imagesFond[i].getOffsetX() - imagesFond[i].getTailleX();
                        break;
                }
                switch (imagesFond[i].getPosY()) {
                    case "top":
                        posY = imagesFond[i].getOffsetY() + ivVisualisation.getLayoutY();
                        break;
                    case "middle":
                        posY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - imagesFond[i].getTailleY()) / 2 + imagesFond[i].getOffsetY();
                        break;
                    case "bottom":
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imagesFond[i].getOffsetY() - imagesFond[i].getTailleY();
                        break;
                }
                if (bAfficheVignettes) {
                    switch (positionVignettes) {
                        case "bottom":
                            if (imagesFond[i].getPosY().equals("bottom")) {
                                posY -= (tailleImageVignettes / 2 + 6);
                            }
                            break;
                        case "left":
                            if (imagesFond[i].getPosX().equals("left")) {
                                posX += tailleImageVignettes + 10;
                            }
                            break;
                        case "right":
                            if (imagesFond[i].getPosX().equals("right")) {
                                posX -= (tailleImageVignettes + 10);
                            }
                            break;
                    }
                }
                ivImageFond.setLayoutX(posX);
                ivImageFond.setLayoutY(posY);
                ivImageFond.setOpacity(imagesFond[i].getOpacite());
                apVisualisation.getChildren().add(ivImageFond);
            }
        }
        apVisualisation.getChildren().addAll(txtTitre, imgBoussole, imgAiguille, imgTwitter, imgGoogle, imgFacebook, imgEmail, apVisuVignettes, apVisuComboMenu, apVisuPlan, fondSuivant, fondPrecedent);
        txtTitre.setVisible(bAfficheTitre);
        chargeBarre(styleBoutons, styleHS, imageMasque);
        afficheMasque();
        hbbarreBoutons = new HBox(espacement);
        hbbarreBoutons.setId("barreBoutons");
        hbbarreBoutons.setVisible(strVisibiliteBarreClassique.equals("oui"));
        hbbarreBoutons.setTranslateZ(1);
        ivHotSpot.setFitWidth(30);
        ivHotSpot.setPreserveRatio(true);
        ivHotSpot.setLayoutX(510);
        ivHotSpot.setLayoutY(310);
        ivHotSpot.setTranslateZ(1);
        Tooltip t = new Tooltip("Hotspot panoramique");
        t.setStyle(strTooltipStyle);
        Tooltip.install(ivHotSpot, t);
        ivHotSpotImage.setFitWidth(30);
        ivHotSpotImage.setPreserveRatio(true);
        ivHotSpotImage.setLayoutX(560);
        ivHotSpotImage.setLayoutY(310);
        ivHotSpotImage.setTranslateZ(1);
        Tooltip t1 = new Tooltip("Hotspot Photo");
        t1.setStyle(strTooltipStyle);
        Tooltip.install(ivHotSpotImage, t1);
        int nombreBoutons = 11;
        if (strDeplacementsBarreClassique.equals("non")) {
            nombreBoutons -= 4;
        }
        if (strZoomBarreClassique.equals("non")) {
            nombreBoutons -= 2;
        }
        if (strOutilsBarreClassique.equals("non")) {
            nombreBoutons -= 5;
        } else {
            if (strPleinEcranBarreClassique.equals("non")) {
                nombreBoutons -= 1;
            }
            if (strRotationBarreClassique.equals("non")) {
                nombreBoutons -= 1;
            }
            if (strSourisBarreClassique.equals("non")) {
                nombreBoutons -= 1;
            }
        }
        double tailleBarre1 = (double) nombreBoutons * ((double) taille + espacement);
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
        if (strDeplacementsBarreClassique.equals("non")) {
            hbbarreBoutons.getChildren().remove(hbDeplacements);
        }
        if (strZoomBarreClassique.equals("non")) {
            hbbarreBoutons.getChildren().remove(hbZoom);
        }
        if (strOutilsBarreClassique.equals("non")) {
            hbbarreBoutons.getChildren().remove(hbOutils);
        }
        apVisualisation.getChildren().addAll(hbbarreBoutons, ivHotSpot, ivHotSpotImage, apAfficheDiapo, apFenetreAfficheInfo, lblFenetreURL, ivDiapo, apAfficheBarrePersonnalisee);
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
        ivPleinFenetre.setFitWidth(taille);
        ivPleinFenetre.setFitHeight(taille);
        ivModeSouris.setFitWidth(taille);
        ivModeSouris.setFitHeight(taille);
        ivAide.setFitWidth(taille);
        ivAide.setFitHeight(taille);
        ivAutoRotation.setFitWidth(taille);
        ivAutoRotation.setFitHeight(taille);
        hbDeplacements.getChildren().addAll(ivGauche, ivHaut, ivBas, ivDroite);
        hbZoom.getChildren().addAll(ivZoomPlus, ivZoomMoins);
        hbOutils.getChildren().addAll(ivPleinFenetre, ivModeSouris, ivAutoRotation, ivInfo, ivAide);
        if (strPleinEcranBarreClassique.equals("non")) {
            hbOutils.getChildren().remove(ivPleinFenetre);
        }
        if (strSourisBarreClassique.equals("non")) {
            hbOutils.getChildren().remove(ivModeSouris);
        }
        if (strRotationBarreClassique.equals("non")) {
            hbOutils.getChildren().remove(ivAutoRotation);
        }
        String positVert = position.split(":")[0];
        String positHor = position.split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (positVert) {
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

        switch (positHor) {
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
        if (bAfficheVignettes) {
            switch (positionVignettes) {
                case "bottom":
                    if (positVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - offsetYBarreClassique - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (positHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + offsetXBarreClassique + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (positHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - offsetXBarreClassique - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> listerStyle(String repertoire) {
        ArrayList<String> liste = new ArrayList<>();
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
                += "styleBarre=" + styleBarreClassique + "\n"
                + "couleurTheme=" + couleurTheme.toString().substring(2, 8) + "\n"
                + "couleurBoutons=" + couleurBarreClassique.toString().substring(2, 8) + "\n"
                + "couleurHotspots=" + couleurHotspots.toString().substring(2, 8) + "\n"
                + "couleurHotspotsPhoto=" + couleurHotspotsPhoto.toString().substring(2, 8) + "\n"
                + "couleurMasque=" + couleurMasque.toString().substring(2, 8) + "\n"
                + "styleHotspots=" + strStyleHotSpots + "\n"
                + "styleHotspotImages=" + strStyleHotSpotImages + "\n"
                + "position=" + strPositionBarreClassique + "\n"
                + "dX=" + Math.round(offsetXBarreClassique) + "\n"
                + "dY=" + Math.round(offsetYBarreClassique) + "\n"
                + "visible=" + strVisibiliteBarreClassique + "\n"
                + "suivantPrecedent=" + bSuivantPrecedent + "\n"
                + "deplacement=" + strDeplacementsBarreClassique + "\n"
                + "zoom=" + strZoomBarreClassique + "\n"
                + "outils=" + strOutilsBarreClassique + "\n"
                + "rotation=" + strRotationBarreClassique + "\n"
                + "FS=" + strPleinEcranBarreClassique + "\n"
                + "souris=" + strSourisBarreClassique + "\n"
                + "espacementBoutons=" + espacementBarreClassique + "\n"
                + "bCouleurOrigineBarrePersonnalisee=" + bCouleurOrigineBarrePersonnalisee + "\n"
                + "nombreZonesBarrePersonnalisee=" + iNombreZonesBarrePersonnalisee + "\n"
                + "offsetXBarrePersonnalisee=" + offsetXBarrePersonnalisee + "\n"
                + "offsetYBarrePersonnalisee=" + offsetYBarrePersonnalisee + "\n"
                + "tailleBarrePersonnalisee=" + tailleBarrePersonnalisee + "\n"
                + "tailleIconesBarrePersonnalisee=" + tailleIconesBarrePersonnalisee + "\n"
                + "strPositionBarrePersonnalisee=" + strPositionBarrePersonnalisee + "\n"
                + "strDeplacementsBarrePersonnalisee=" + strDeplacementsBarrePersonnalisee + "\n"
                + "strZoomBarrePersonnalisee=" + strZoomBarrePersonnalisee + "\n"
                + "strInfoBarrePersonnalisee=" + strInfoBarrePersonnalisee + "\n"
                + "strAideBarrePersonnalisee=" + strAideBarrePersonnalisee + "\n"
                + "strRotationBarrePersonnalisee=" + strRotationBarrePersonnalisee + "\n"
                + "strPleinEcranBarrePersonnalisee=" + strPleinEcranBarrePersonnalisee + "\n"
                + "strSourisBarrePersonnalisee=" + strSourisBarrePersonnalisee + "\n"
                + "strVisibiliteBarrePersonnalisee=" + strVisibiliteBarrePersonnalisee + "\n"
                + "strLienImageBarrePersonnalisee=" + strLienImageBarrePersonnalisee + "\n"
                + "strLien1BarrePersonnalisee=" + strLien1BarrePersonnalisee + "\n"
                + "strLien2BarrePersonnalisee=" + strLien1BarrePersonnalisee + "\n"
                + "couleurBarrePersonnalisee=" + couleurBarrePersonnalisee + "\n"
                + "afficheTitre=" + bAfficheTitre + "\n"
                + "titrePolice=" + strTitrePoliceNom + "\n"
                + "titrePoliceTaille=" + strTitrePoliceTaille + "\n"
                + "titreOpacite=" + Math.round(titreOpacite * 100.d) / 100.d + "\n"
                + "titreTaille=" + titreTaille + "\n"
                + "titreCouleur=" + strCouleurTitre + "\n"
                + "titreFondCouleur=" + strCouleurFondTitre + "\n"
                + "bFenetreInfoPersonnalise=" + bFenetreInfoPersonnalise + "\n"
                + "bFenetreAidePersonnalise=" + bFenetreAidePersonnalise + "\n"
                + "strFenetreInfoImage =" + strFenetreInfoImage + "\n"
                + "strFenetreAideImage=" + strFenetreAideImage + "\n"
                + "fenetreInfoTaille=" + fenetreInfoTaille + "\n"
                + "fenetreAideTaille=" + fenetreAideTaille + "\n"
                + "fenetreInfoPosX=" + fenetreInfoPosX + "\n"
                + "fenetreInfoPosY=" + fenetreInfoPosY + "\n"
                + "fenetreAidePosX=" + fenetreAidePosX + "\n"
                + "fenetreAidePosY=" + fenetreAidePosY + "\n"
                + "fenetreInfoOpacite=" + fenetreInfoOpacite + "\n"
                + "fenetreAideOpacite=" + fenetreAideOpacite + "\n"
                + "strFenetreURL=" + strFenetreURL + "\n"
                + "strFenetreTexteURL=" + strFenetreTexteURL + "\n"
                + "strFenetreURLInfobulle=" + strFenetreURLInfobulle + "\n"
                + "strFenetreURLCouleur=" + strFenetreURLCouleur + "\n"
                + "strFenetrePolice=" + strFenetrePolice + "\n"
                + "fenetrePoliceTaille=" + fenetrePoliceTaille + "\n"
                + "fenetreURLPosX=" + fenetreURLPosX + "\n"
                + "fenetreURLPosY=" + fenetreURLPosY + "\n"
                + "strFenetreCouleurFond=" + strFenetreCouleurFond + "\n"
                + "fenetreOpaciteFond=" + fenetreOpaciteFond + "\n"
                + "diaporamaOpacite=" + Math.round(diaporamaOpacite * 100.d) / 100.d + "\n"
                + "diaporamaCouleur=" + strCouleurDiaporama + "\n"
                + "afficheBoussole=" + bAfficheBoussole + "\n"
                + "imageBoussole=" + strImageBoussole + "\n"
                + "tailleBoussole=" + Math.round(tailleBoussole * 10.d) / 10.d + "\n"
                + "positionBoussole=" + strPositionBoussole + "\n"
                + "dXBoussole=" + Math.round(offsetXBoussole) + "\n"
                + "dYBoussole=" + Math.round(offsetYBoussole) + "\n"
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
                + "couleurFondVignettes=" + couleurFondVignettes + "\n"
                + "couleurTexteVignettes=" + couleurTexteVignettes + "\n"
                + "bAfficheComboMenu=" + bAfficheComboMenu + "\n"
                + "bAfficheComboMenuImages=" + bAfficheComboMenuImages + "\n"
                + "positionXComboMenu=" + positionXComboMenu + "\n"
                + "positionYComboMenu=" + positionYComboMenu + "\n"
                + "offsetXComboMenu=" + offsetXComboMenu + "\n"
                + "offsetYComboMenu=" + offsetYComboMenu + "\n"
                + "affichePlan=" + bAffichePlan + "\n"
                + "positionPlan=" + positionPlan + "\n"
                + "opacitePlan=" + Math.round(opacitePlan * 100.d) / 100.d + "\n"
                + "largeurPlan=" + Math.round(largeurPlan) + "\n"
                + "couleurFondPlan=" + txtCouleurFondPlan + "\n"
                + "couleurTextePlan=" + txtCouleurTextePlan + "\n"
                + "afficheRadar=" + bAfficheRadar + "\n"
                + "opaciteRadar=" + Math.round(opaciteRadar * 100.d) / 100.d + "\n"
                + "tailleRadar=" + Math.round(tailleRadar) + "\n"
                + "couleurFondRadar=" + txtCouleurFondRadar + "\n"
                + "couleurLigneRadar=" + txtCouleurLigneRadar + "\n"
                + "afficheMenuContextuel=" + bAfficheMenuContextuel + "\n"
                + "affichePrecSuivMC=" + bAffichePrecSuivMC + "\n"
                + "affichePlaneteNormalMC=" + bAffichePlanetNormalMC + "\n"
                + "affichePersMC1=" + bAffichePersMC1 + "\n"
                + "affichePersMC2=" + bAffichePersMC2 + "\n"
                + "txtPersLib1=" + strPersLib1 + "\n"
                + "txtPersLib2=" + strPersLib2 + "\n"
                + "txtPersURL1=" + strPersURL1 + "\n"
                + "txtPersURL2=" + strPersURL2 + "\n"
                + "nombreImagesFond=" + iNombreImagesFond + "\n";
        for (int i = 0; i < iNombreImagesFond; i++) {
            contenuFichier += "<";
            contenuFichier += "image=" + imagesFond[i].getFichierImage() + "#";
            contenuFichier += "posX=" + imagesFond[i].getPosX() + "#";
            contenuFichier += "posY=" + imagesFond[i].getPosY() + "#";
            contenuFichier += "offsetX=" + imagesFond[i].getOffsetX() + "#";
            contenuFichier += "offsetY=" + imagesFond[i].getOffsetY() + "#";
            contenuFichier += "tailleX=" + imagesFond[i].getTailleX() + "#";
            contenuFichier += "tailleY=" + imagesFond[i].getTailleY() + "#";
            contenuFichier += "opacite=" + imagesFond[i].getOpacite() + "#";
            contenuFichier += "masquable=" + imagesFond[i].isMasquable() + "#";
            contenuFichier += "url=" + imagesFond[i].getUrl() + "#";
            contenuFichier += "infobulle=" + imagesFond[i].getInfobulle() + "#";
            contenuFichier += ">\n";
        }

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
        iNombreImagesFond = 0;
        File repertoirePlan;

        for (String chaine : templ) {
            if (chaine.split("image=").length > 1) {
                imagesFond[iNombreImagesFond] = new ImageFond();

                chaine = chaine.replace("<", "");
                chaine = chaine.replace(">", "");
                //System.out.println("chaine : " + chaine);
                String[] elements = chaine.split("#");
                for (String ch1 : elements) {
                    String variable = ch1.split("=")[0];
                    String valeur = "";
                    if (ch1.split("=").length > 1) {
                        valeur = ch1.split("=")[1];
                    }
                    //System.out.println(variable + "=" + valeur);
                    switch (variable) {
                        case "image":
                            imagesFond[iNombreImagesFond].setFichierImage(valeur);
                            imagesFond[iNombreImagesFond].setImgFond(new Image("file:" + valeur));
                            repertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(imagesFond[iNombreImagesFond].getFichierImage(), repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "posX":
                            imagesFond[iNombreImagesFond].setPosX(valeur);
                            break;
                        case "posY":
                            imagesFond[iNombreImagesFond].setPosY(valeur);
                            break;
                        case "url":
                            imagesFond[iNombreImagesFond].setUrl(valeur);
                            break;
                        case "infobulle":
                            imagesFond[iNombreImagesFond].setInfobulle(valeur);
                            break;
                        case "offsetX":
                            imagesFond[iNombreImagesFond].setOffsetX(Double.parseDouble(valeur));
                            break;
                        case "offsetY":
                            imagesFond[iNombreImagesFond].setOffsetY(Double.parseDouble(valeur));
                            break;
                        case "opacite":
                            imagesFond[iNombreImagesFond].setOpacite(Double.parseDouble(valeur));
                            break;
                        case "masquable":
                            imagesFond[iNombreImagesFond].setMasquable(valeur.equals("true"));
                            break;
                        case "tailleX":
                            imagesFond[iNombreImagesFond].setTailleX(Integer.parseInt(valeur));
                            break;
                        case "tailleY":
                            imagesFond[iNombreImagesFond].setTailleY(Integer.parseInt(valeur));
                            break;
                    }

                }
                iNombreImagesFond++;
            } else {
                String variable = chaine.split("=")[0];
                String valeur = "";
                if (chaine.split("=").length > 1) {
                    valeur = chaine.split("=")[1];
                }
                //System.out.println(variable + "=" + valeur);
                switch (variable) {
                    case "couleurTheme":
                        couleurTheme = Color.web(valeur);
                        break;
                    case "couleurBoutons":
                        couleurBarreClassique = Color.web(valeur);
                        break;
                    case "couleurHotspots":
                        couleurHotspots = Color.web(valeur);
                        break;
                    case "couleurHotspotsPhoto":
                        couleurHotspotsPhoto = Color.web(valeur);
                        break;
                    case "couleurMasque":
                        couleurMasque = Color.web(valeur);
                        break;

                    case "styleBarre":
                        styleBarreClassique = valeur;
                        break;
                    case "suivantPrecedent":
                        bSuivantPrecedent = (valeur.equals("true"));
                        break;

                    case "styleHotspots":
                        strStyleHotSpots = valeur;
                        break;
                    case "styleHotspotImages":
                        strStyleHotSpotImages = valeur;
                        break;
                    case "position":
                        strPositionBarreClassique = valeur;

                        break;
                    case "dX":
                        offsetXBarreClassique = Double.parseDouble(valeur);
                        break;
                    case "dY":
                        offsetYBarreClassique = Double.parseDouble(valeur);
                        break;
                    case "visible":
                        strVisibiliteBarreClassique = valeur;
                        break;
                    case "deplacement":
                        strDeplacementsBarreClassique = valeur;
                        break;
                    case "zoom":
                        strZoomBarreClassique = valeur;
                        break;
                    case "outils":
                        strOutilsBarreClassique = valeur;
                        break;
                    case "rotation":
                        strRotationBarreClassique = valeur;
                        break;
                    case "FS":
                        strPleinEcranBarreClassique = valeur;
                        break;
                    case "souris":
                        strSourisBarreClassique = valeur;
                        break;
                    case "espacementBoutons":
                        espacementBarreClassique = Double.parseDouble(valeur);
                        break;
                    case "bCouleurOrigineBarrePersonnalisee":
                        bCouleurOrigineBarrePersonnalisee = valeur.equals("true");
                        break;
                    case "nombreZonesBarrePersonnalisee":
                        iNombreZonesBarrePersonnalisee = Integer.parseInt(valeur);
                        break;
                    case "offsetXBarrePersonnalisee":
                        offsetXBarrePersonnalisee = Double.parseDouble(valeur);
                        break;
                    case "offsetYBarrePersonnalisee":
                        offsetYBarrePersonnalisee = Double.parseDouble(valeur);
                        break;
                    case "tailleBarrePersonnalisee":
                        tailleBarrePersonnalisee = Double.parseDouble(valeur);
                        break;
                    case "tailleIconesBarrePersonnalisee":
                        tailleIconesBarrePersonnalisee = Double.parseDouble(valeur);
                        break;
                    case "strPositionBarrePersonnalisee":
                        strPositionBarrePersonnalisee = valeur;
                        break;
                    case "strDeplacementsBarrePersonnalisee":
                        strDeplacementsBarrePersonnalisee = valeur;
                        break;
                    case "strZoomBarrePersonnalisee":
                        strZoomBarrePersonnalisee = valeur;
                        break;
                    case "strInfoBarrePersonnalisee":
                        strInfoBarrePersonnalisee = valeur;
                        break;
                    case "strAideBarrePersonnalisee":
                        strAideBarrePersonnalisee = valeur;
                        break;
                    case "strRotationBarrePersonnalisee":
                        strRotationBarrePersonnalisee = valeur;
                        break;
                    case "strPleinEcranBarrePersonnalisee":
                        strPleinEcranBarrePersonnalisee = valeur;
                        break;
                    case "strSourisBarrePersonnalisee":
                        strSourisBarrePersonnalisee = valeur;
                        break;
                    case "strVisibiliteBarrePersonnalisee":
                        strVisibiliteBarrePersonnalisee = valeur;
                        break;
                    case "strLienImageBarrePersonnalisee":
                        strLienImageBarrePersonnalisee = valeur;
                        break;
                    case "strLien1BarrePersonnalisee":
                        strLien1BarrePersonnalisee = valeur;
                        break;
                    case "strLien2BarrePersonnalisee":
                        strLien2BarrePersonnalisee = valeur;
                        break;
                    case "couleurBarrePersonnalisee":
                        couleurBarrePersonnalisee = Color.web(valeur);
                    case "afficheTitre":
                        bAfficheTitre = valeur.equals("true");
                        break;
                    case "titrePolice":
                        strTitrePoliceNom = valeur;
                        break;
                    case "titrePoliceTaille":
                        strTitrePoliceTaille = valeur;
                        break;
                    case "titreOpacite":
                        titreOpacite = Double.parseDouble(valeur);
                        break;
                    case "titreTaille":
                        titreTaille = Double.parseDouble(valeur);
                        break;
                    case "titreCouleur":
                        strCouleurTitre = valeur;
                        break;
                    case "titreFondCouleur":
                        strCouleurFondTitre = valeur;
                        break;
                    case "bFenetreInfoPersonnalise":
                        bFenetreInfoPersonnalise = valeur.equals("true");
                        break;
                    case "bFenetreAidePersonnalise":
                        bFenetreAidePersonnalise = valeur.equals("true");
                        break;
                    case "strFenetreInfoImage ":
                        strFenetreInfoImage = valeur;
                        if (!strFenetreInfoImage.equals("")) {
                            repertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(strFenetreInfoImage, repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    case "strFenetreAideImage":
                        strFenetreAideImage = valeur;
                        if (!strFenetreAideImage.equals("")) {
                            repertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!repertoirePlan.exists()) {
                                repertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(strFenetreAideImage, repertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "fenetreInfoTaille":
                        fenetreInfoTaille = Double.parseDouble(valeur);
                        break;
                    case "fenetreAideTaille":
                        fenetreAideTaille = Double.parseDouble(valeur);
                        break;
                    case "fenetreInfoPosX":
                        fenetreInfoPosX = Double.parseDouble(valeur);
                        break;
                    case "fenetreInfoPosY":
                        fenetreInfoPosY = Double.parseDouble(valeur);
                        break;
                    case "fenetreAidePosX":
                        fenetreAidePosX = Double.parseDouble(valeur);
                        break;
                    case "fenetreAidePosY":
                        fenetreAidePosY = Double.parseDouble(valeur);
                        break;
                    case "fenetreInfoOpacite":
                        fenetreInfoOpacite = Double.parseDouble(valeur);
                        break;
                    case "fenetreAideOpacite":
                        fenetreAideOpacite = Double.parseDouble(valeur);
                        break;
                    case "strFenetreURL":
                        strFenetreURL = valeur;
                        break;
                    case "strFenetreTexteURL":
                        strFenetreTexteURL = valeur;
                        break;
                    case "strFenetreURLInfobulle":
                        strFenetreURLInfobulle = valeur;
                        break;
                    case "strFenetreURLCouleur":
                        strFenetreURLCouleur = valeur;
                        break;
                    case "strFenetrePolice":
                        strFenetrePolice = valeur;
                        break;
                    case "fenetrePoliceTaille":
                        fenetrePoliceTaille = Double.parseDouble(valeur);
                        break;
                    case "fenetreURLPosX":
                        fenetreURLPosX = Double.parseDouble(valeur);
                        break;
                    case "fenetreURLPosY":
                        fenetreURLPosY = Double.parseDouble(valeur);
                        break;
                    case "strFenetreCouleurFond":
                        strFenetreCouleurFond = valeur;
                        break;
                    case "fenetreOpaciteFond":
                        fenetreOpaciteFond = Double.parseDouble(valeur);
                        break;
                    case "diaporamaOpacite":
                        diaporamaOpacite = Double.parseDouble(valeur);
                        break;
                    case "diaporamaCouleur":
                        strCouleurDiaporama = valeur;
                        break;
                    case "afficheBoussole":
                        bAfficheBoussole = (valeur.equals("true"));
                        break;
                    case "imageBoussole":
                        strImageBoussole = valeur;
                        break;
                    case "tailleBoussole":
                        tailleBoussole = Double.parseDouble(valeur);
                        break;
                    case "positionBoussole":
                        strPositionBoussole = valeur;
                        break;
                    case "dXBoussole":
                        offsetXBoussole = Double.parseDouble(valeur);
                        break;
                    case "dYBoussole":
                        offsetYBoussole = Double.parseDouble(valeur);
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
                    case "couleurTexteVignettes":
                        couleurTexteVignettes = valeur;
                        break;
                    case "bAfficheComboMenu":
                        bAfficheComboMenu = valeur.equals("true");
                        break;
                    case "bAfficheComboMenuImages":
                        bAfficheComboMenuImages = valeur.equals("true");
                        break;
                    case "positionXComboMenu":
                        positionXComboMenu = valeur;
                        break;
                    case "positionYComboMenu":
                        positionYComboMenu = valeur;
                        break;
                    case "offsetXComboMenu":
                        offsetXComboMenu = Double.parseDouble(valeur);
                        break;
                    case "offsetYComboMenu":
                        offsetYComboMenu = Double.parseDouble(valeur);
                        break;
                    case "affichePlan":
                        bAffichePlan = valeur.equals("true");
                        break;
                    case "positionPlan":
                        positionPlan = valeur;
                        break;
                    case "opacitePlan":
                        opacitePlan = Double.parseDouble(valeur);
                        break;
                    case "largeurPlan":
                        largeurPlan = Double.parseDouble(valeur);
                        break;
                    case "couleurFondPlan":
                        txtCouleurFondPlan = valeur;
                        couleurFondPlan = Color.valueOf(txtCouleurFondPlan);
                        break;
                    case "couleurTextePlan":
                        txtCouleurTextePlan = valeur;
                        couleurTextePlan = Color.valueOf(txtCouleurTextePlan);
                        break;
                    case "afficheRadar":
                        bAfficheRadar = valeur.equals("true");
                        break;
                    case "opaciteRadar":
                        opaciteRadar = Double.parseDouble(valeur);
                        break;
                    case "tailleRadar":
                        tailleRadar = Double.parseDouble(valeur);
                        break;
                    case "couleurFondRadar":
                        txtCouleurFondRadar = valeur;
                        couleurFondRadar = Color.valueOf(txtCouleurFondRadar);
                        break;
                    case "couleurLigneRadar":
                        txtCouleurLigneRadar = valeur;
                        couleurLigneRadar = Color.valueOf(txtCouleurLigneRadar);
                        break;
                    case "afficheMenuContextuel":
                        bAfficheMenuContextuel = valeur.equals("true");
                        break;
                    case "affichePrecSuivMC":
                        bAffichePrecSuivMC = valeur.equals("true");
                        break;
                    case "affichePlaneteNormalMC":
                        bAffichePlanetNormalMC = valeur.equals("true");
                        break;
                    case "affichePersMC1":
                        bAffichePersMC1 = valeur.equals("true");
                        break;
                    case "affichePersMC2":
                        bAffichePersMC2 = valeur.equals("true");
                        break;
                    case "txtPersLib1":
                        strPersLib1 = valeur;
                        break;
                    case "txtPersLib2":
                        strPersLib2 = valeur;
                        break;
                    case "txtPersURL1":
                        strPersURL1 = valeur;
                        break;
                    case "txtPersURL2":
                        strPersURL2 = valeur;
                        break;

                }
            }
        }
    }

    public void afficheTemplate() throws IOException {
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation, txtTitre, imgBoussole, imgAiguille, imgTwitter, imgGoogle, imgFacebook, imgEmail, apVisuVignettes, apVisuComboMenu, apVisuPlan, ivMasque, apAfficheDiapo, apFenetreAfficheInfo, lblFenetreURL, ivDiapo, apAfficheBarrePersonnalisee);

        txtTitre.setTextFill(Color.valueOf(strCouleurTitre));
        Color couleur = Color.valueOf(strCouleurFondTitre);
        int rouge = (int) (couleur.getRed() * 255.d);
        int bleu = (int) (couleur.getBlue() * 255.d);
        int vert = (int) (couleur.getGreen() * 255.d);
        String coulFond = "rgba(" + rouge + "," + vert + "," + bleu + "," + titreOpacite + ")";
        txtTitre.setStyle("-fx-background-color : " + coulFond);
        double taille = (double) titreTaille / 100.d * ivVisualisation.getFitWidth();
        txtTitre.setMinWidth(taille);
        txtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
        Font fonte1 = Font.font(strTitrePoliceNom, Double.parseDouble(strTitrePoliceTaille));
        txtTitre.setFont(fonte1);
        txtTitre.setPrefHeight(-1);
        txtTitre.setVisible(bAfficheTitre);
        cbAfficheTitre.setSelected(bAfficheTitre);
        cpCouleurTitre.setValue(Color.valueOf(strCouleurTitre));
        cpCouleurFondTitre.setValue(Color.valueOf(strCouleurFondTitre));
        cpCouleurBarreClassique.setValue(couleurBarreClassique);
        cpCouleurTheme.setValue(couleurTheme);
        cpCouleurMasques.setValue(couleurMasque);
        cpCouleurHotspots.setValue(couleurHotspots);
        cpCouleurHotspotsPhoto.setValue(couleurHotspotsPhoto);
        cbListePolices.setValue(strTitrePoliceNom);
        slOpacite.setValue(titreOpacite);
        cblisteStyleBarreClassique.setValue(styleBarreClassique);
        bdfOffsetXBarreClassique.setNumber(new BigDecimal(offsetXBarreClassique));
        bdfOffsetYBarreClassique.setNumber(new BigDecimal(offsetYBarreClassique));
        if (strVisibiliteBarreClassique.equals("oui")) {
            cbBarreClassiqueVisible.setSelected(true);
        } else {
            cbBarreClassiqueVisible.setSelected(false);
        }
        if (strDeplacementsBarreClassique.equals("oui")) {
            cbDeplacementsBarreClassique.setSelected(true);
        } else {
            cbDeplacementsBarreClassique.setSelected(false);
        }
        if (strZoomBarreClassique.equals("oui")) {
            cbZoomBarreClassique.setSelected(true);
        } else {
            cbZoomBarreClassique.setSelected(false);
        }
        if (strOutilsBarreClassique.equals("oui")) {
            cbOutilsBarreClassique.setSelected(true);
        } else {
            cbOutilsBarreClassique.setSelected(false);
        }
        switch (strPositionBarreClassique) {
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

        if (strRotationBarreClassique.equals("oui")) {
            cbRotationBarreClassique.setSelected(true);
        } else {
            cbRotationBarreClassique.setSelected(false);
        }
        if (strPleinEcranBarreClassique.equals("oui")) {
            cbFSBarreClassique.setSelected(true);
        } else {
            cbFSBarreClassique.setSelected(false);
        }
        if (strSourisBarreClassique.equals("oui")) {
            cbSourisBarreClassique.setSelected(true);
        } else {
            cbSourisBarreClassique.setSelected(false);
        }
        if (strVisibiliteBarrePersonnalisee.equals("oui")) {
            chargeBarrePersonnalisee(strLienImageBarrePersonnalisee);

            rbCouleurOrigineBarrePersonnalisee.setSelected(bCouleurOrigineBarrePersonnalisee);
            rbCouleurPersBarrePersonnalisee.setSelected(!bCouleurOrigineBarrePersonnalisee);

            bdfOffsetXBarrePersonnalisee.setNumber(new BigDecimal(offsetXBarrePersonnalisee));
            bdfOffsetYBarrePersonnalisee.setNumber(new BigDecimal(offsetYBarrePersonnalisee));
            sltailleBarrePersonnalisee.setValue(tailleBarrePersonnalisee);
            sltailleIconesBarrePersonnalisee.setValue(tailleIconesBarrePersonnalisee);
            switch (strPositionBarrePersonnalisee) {
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
            cbDeplacementsBarrePersonnalisee.setSelected(strDeplacementsBarrePersonnalisee.equals("oui"));
            cbZoomBarrePersonnalisee.setSelected(strZoomBarrePersonnalisee.equals("oui"));
            cbRotationBarrePersonnalisee.setSelected(strRotationBarrePersonnalisee.equals("oui"));
            cbFSBarrePersonnalisee.setSelected(strPleinEcranBarrePersonnalisee.equals("oui"));
            cbSourisBarrePersonnalisee.setSelected(strSourisBarrePersonnalisee.equals("oui"));
            cbBarrePersonnaliseeVisible.setSelected(strVisibiliteBarrePersonnalisee.equals("oui"));
            cpCouleurBarrePersonnalisee.setValue(couleurBarrePersonnalisee);
            tfLienImageBarrePersonnalisee.setText(strLienImageBarrePersonnalisee);
            tfLien1BarrePersonnalisee.setText(strLien1BarrePersonnalisee);
            tfLien2BarrePersonnalisee.setText(strLien2BarrePersonnalisee);
        }
        cbFenetreInfoPersonnalise.setSelected(bFenetreInfoPersonnalise);
        cbFenetreAidePersonnalise.setSelected(bFenetreAidePersonnalise);
        txtFenetreInfoImage.setText(strFenetreInfoImage);
        txtFenetreAideImage.setText(strFenetreAideImage);
        slFenetreInfoTaille.setValue(fenetreInfoTaille);
        slFenetreAideTaille.setValue(fenetreAideTaille);
        bdfFenetreInfoPosX.setNumber(new BigDecimal(fenetreInfoPosX));
        bdfFenetreInfoPosY.setNumber(new BigDecimal(fenetreInfoPosY));
        bdfFenetreAidePosX.setNumber(new BigDecimal(fenetreAidePosX));
        bdfFenetreAidePosY.setNumber(new BigDecimal(fenetreAidePosY));
        slFenetreInfoOpacite.setValue(fenetreInfoOpacite);
        slFenetreAideOpacite.setValue(fenetreAideOpacite);
        txtFenetreTexteURL.setText(strFenetreTexteURL);
        txtFenetreURL.setText(strFenetreURL);
        slFenetrePoliceTaille.setValue(fenetrePoliceTaille);
        bdfFenetreURLPosX.setNumber(new BigDecimal(fenetreURLPosX));
        bdfFenetreURLPosY.setNumber(new BigDecimal(fenetreURLPosY));
        bdfFenetreURLPosX.setNumber(new BigDecimal(fenetreURLPosX));
        bdfFenetreURLPosY.setNumber(new BigDecimal(fenetreURLPosY));
        cpFenetreURLCouleur.setValue(Color.valueOf(strFenetreURLCouleur));
        cbSuivantPrecedent.setSelected(bSuivantPrecedent);
        fondSuivant.setVisible(bSuivantPrecedent);
        fondPrecedent.setVisible(bSuivantPrecedent);
        slTaillePolice.setValue(Double.parseDouble(strTitrePoliceTaille));
        slEspacementBarreClassique.setValue(espacementBarreClassique);
        slTaille.setValue(titreTaille);
        apVisualisation.getChildren().remove(hbbarreBoutons);
        apVisualisation.getChildren().remove(ivHotSpot);
        apVisualisation.getChildren().remove(ivHotSpotImage);
        slTailleBoussole.setValue(tailleBoussole);
        slOpaciteBoussole.setValue(opaciteBoussole);
        bdfOffsetXBoussole.setNumber(new BigDecimal(offsetXBoussole));
        bdfOffsetYBoussole.setNumber(new BigDecimal(offsetYBoussole));
        cbAiguilleMobile.setSelected(bAiguilleMobileBoussole);
        cbAfficheBoussole.setSelected(bAfficheBoussole);
        rbBoussTopLeft.setSelected(strPositionBoussole.equals("top:left"));
        rbBoussBottomLeft.setSelected(strPositionBoussole.equals("bottom:left"));
        rbBoussTopRight.setSelected(strPositionBoussole.equals("top:right"));
        rbBoussBottomRight.setSelected(strPositionBoussole.equals("bottom:right"));
        slTailleMasque.setValue(tailleMasque);
        slOpaciteMasque.setValue(opaciteMasque);
        masqueDXSpinner.setNumber(new BigDecimal(dXMasque));
        masqueDYSpinner.setNumber(new BigDecimal(dYMasque));
        cbMasqueNavigation.setSelected(bMasqueNavigation);
        cbMasqueBoussole.setSelected(bMasqueBoussole);
        cbMasqueTitre.setSelected(bMasqueTitre);
        cbMasquePlan.setSelected(bMasquePlan);
        cbMasqueReseaux.setSelected(bMasqueReseaux);
        cbMasqueVignettes.setSelected(bMasqueVignettes);
        cbAfficheMasque.setSelected(bAfficheMasque);
        rbMasqueTopLeft.setSelected(positionMasque.equals("top:left"));
        rbMasqueBottomLeft.setSelected(positionMasque.equals("bottom:left"));
        rbMasqueTopRight.setSelected(positionMasque.equals("top:right"));
        rbMasqueBottomRight.setSelected(positionMasque.equals("bottom:right"));
        slTailleReseauxSociaux.setValue(tailleReseauxSociaux);
        slOpaciteReseauxSociaux.setValue(opaciteReseauxSociaux);
        reseauxSociauxDXSpinner.setNumber(new BigDecimal(dXReseauxSociaux));
        reseauxSociauxDYSpinner.setNumber(new BigDecimal(dYReseauxSociaux));
        cbReseauxSociauxTwitter.setSelected(bReseauxSociauxTwitter);
        cbReseauxSociauxGoogle.setSelected(bReseauxSociauxGoogle);
        cbReseauxSociauxFacebook.setSelected(bReseauxSociauxFacebook);
        cbReseauxSociauxEmail.setSelected(bReseauxSociauxEmail);
        cbAfficheReseauxSociaux.setSelected(bAfficheReseauxSociaux);
        rbReseauxSociauxTopLeft.setSelected(positionReseauxSociaux.equals("top:left"));
        rbReseauxSociauxBottomLeft.setSelected(positionReseauxSociaux.equals("bottom:left"));
        rbReseauxSociauxTopRight.setSelected(positionReseauxSociaux.equals("top:right"));
        rbReseauxSociauxBottomRight.setSelected(positionReseauxSociaux.equals("bottom:right"));
        cbAfficheVignettes.setSelected(bAfficheVignettes);
        slOpaciteVignettes.setValue(opaciteVignettes);
        slTailleVignettes.setValue(tailleImageVignettes);
        rbVignettesLeft.setSelected(positionVignettes.equals("left"));
        rbVignettesRight.setSelected(positionVignettes.equals("right"));
        rbVignettesBottom.setSelected(positionVignettes.equals("bottom"));
        cpCouleurFondVignettes.setValue(Color.valueOf(couleurFondVignettes));
        cpCouleurTexteVignettes.setValue(Color.valueOf(couleurTexteVignettes));
        cbAfficheComboMenu.setSelected(bAfficheComboMenu);
        cbAfficheComboMenuImages.setSelected(bAfficheComboMenuImages);
        String posit = positionYComboMenu + ":" + positionXComboMenu;
        switch (posit) {
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
        bdfOffsetXComboMenu.setNumber(new BigDecimal(offsetXComboMenu));
        bdfOffsetYComboMenu.setNumber(new BigDecimal(offsetYComboMenu));
        cbAffichePlan.setSelected(bAffichePlan);
        slOpacitePlan.setValue(opacitePlan);
        slLargeurPlan.setValue(largeurPlan);
        rbPlanLeft.setSelected(positionPlan.equals("left"));
        rbPlanRight.setSelected(positionPlan.equals("right"));
        cpCouleurFondPlan.setValue(couleurFondPlan);
        cpCouleurTextePlan.setValue(couleurTextePlan);
        cpCouleurDiaporama.setValue(Color.valueOf(strCouleurDiaporama));
        slOpaciteDiaporama.setValue(diaporamaOpacite);
        if (bAffichePlan) {
            tabPlan.setDisable(!bAffichePlan);
            mniAffichagePlan.setDisable(!bAffichePlan);
            ivAjouterPlan.setDisable(!bAffichePlan);
            mniAjouterPlan.setDisable(!bAffichePlan);
            if (bAffichePlan) {
                ivAjouterPlan.setOpacity(1.0);
            } else {
                ivAjouterPlan.setOpacity(0.3);
            }

        }
        cbAfficheRadar.setSelected(bAfficheRadar);
        slOpaciteRadar.setValue(opaciteRadar);
        slTailleRadar.setValue(tailleRadar);
        cpCouleurFondRadar.setValue(couleurFondRadar);
        cpCouleurLigneRadar.setValue(couleurLigneRadar);
        cbAfficheMenuContextuel.setSelected(bAfficheMenuContextuel);
        cbAffichePrecSuivMC.setSelected(bAffichePrecSuivMC);
        cbAffichePlanetNormalMC.setSelected(bAffichePlanetNormalMC);
        cbAffichePersMC1.setSelected(bAffichePersMC1);
        cbAffichePersMC2.setSelected(bAffichePersMC2);
        tfPersLib1.setText(strPersLib1);
        tfPersLib2.setText(strPersLib2);
        tfPersURL1.setText(strPersURL1);
        tfPersURL2.setText(strPersURL2);
        afficheImagesFondInterface();
        afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        afficheVignettes();
        affichePlan();
        afficheDiaporama();
        afficheFenetreAide();
        afficheFenetreInfo();
    }

    public void rafraichit() {

        cbImage.getItems().clear();
        if (iNombrePanoramiques > 0) {
            rbPerso.setDisable(false);
            String imgAffiche = "";
            if (cbImage.getSelectionModel().getSelectedItem() != null) {
                imgAffiche = cbImage.getSelectionModel().getSelectedItem().toString();
            }
            for (int i = 0; i < iNombrePanoramiques; i++) {
                String nomImage = panoramiquesProjet[i].getNomFichier().substring(
                        panoramiquesProjet[i].getNomFichier().lastIndexOf(File.separator) + 1,
                        panoramiquesProjet[i].getNomFichier().length()
                );
                cbImage.getItems().add(i, nomImage);
            }
            cbImage.setValue(imgAffiche);
        }

        afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        affichePlan();
        afficheVignettes();
    }

    private void retireImageFond(int numImage) {
        for (int i = numImage; i < iNombreImagesFond - 1; i++) {
            imagesFond[i] = imagesFond[i + 1];
        }
        iNombreImagesFond--;
        afficheImagesFondInterface();
        //System.out.println("Retire l'image " + numImage + " Nombre Images : " + nombreImagesFond);
    }

    private void afficheImagesFondInterface() {

        apImageFond.getChildren().clear();
        Image imgAjoute = new Image("file:" + strRepertAppli + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (MouseEvent me) -> {
                    ajoutImageFond();
                }
        );

        double hauteurPanel = 280;
        taillePanelImageFond = (iNombreImagesFond) * (hauteurPanel + 10) + 60;
        apImageFond.setPrefHeight(taillePanelImageFond);
        for (int i = 0; i < iNombreImagesFond; i++) {
            int j = i;
            AnchorPane apImagesFond = new AnchorPane();
            apImagesFond.setPrefWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMinWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMaxWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setPrefHeight(hauteurPanel);
            apImagesFond.setStyle("-fx-border-color : #666666; -fx-border-width : 1px; -fx-border-style :solid;");
            apImagesFond.setLayoutY(i * (hauteurPanel + 10) + 60);
            Pane fond1 = new Pane();
            fond1.setCursor(Cursor.HAND);
            ImageView ivAjouteImageFond1 = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
            fond1.setLayoutX(vbOutils.getPrefWidth() - 50);
            fond1.setLayoutY(0);
            Tooltip t1 = new Tooltip(rbLocalisation.getString("interface.imageFondRetire"));
            t1.setStyle(strTooltipStyle);
            Tooltip.install(fond1, t1);
            fond1.getChildren().add(ivAjouteImageFond1);
            fond1.setOnMouseClicked(
                    (MouseEvent me) -> {
                        retireImageFond(j);
                    }
            );
            ImageView ivImageFond = new ImageView(imagesFond[i].getImgFond());
            ivImageFond.setPreserveRatio(true);
            if (imagesFond[i].getImgFond().getWidth() > imagesFond[i].getImgFond().getHeight()) {
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
            String posit = imagesFond[i].getPosY() + ":" + imagesFond[i].getPosX();
            switch (posit) {
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

            int posX = 175;
            int posY = 30;

            rbImageFondTopLeft.setLayoutX(posX);
            rbImageFondTopCenter.setLayoutX(posX + 20);
            rbImageFondTopRight.setLayoutX(posX + 40);
            rbImageFondTopLeft.setLayoutY(posY);
            rbImageFondTopCenter.setLayoutY(posY);
            rbImageFondTopRight.setLayoutY(posY);

            rbImageFondMiddleLeft.setLayoutX(posX);
            rbImageFondMiddleCenter.setLayoutX(posX + 20);
            rbImageFondMiddleRight.setLayoutX(posX + 40);
            rbImageFondMiddleLeft.setLayoutY(posY + 20);
            rbImageFondMiddleCenter.setLayoutY(posY + 20);
            rbImageFondMiddleRight.setLayoutY(posY + 20);

            rbImageFondBottomLeft.setLayoutX(posX);
            rbImageFondBottomCenter.setLayoutX(posX + 20);
            rbImageFondBottomRight.setLayoutX(posX + 40);
            rbImageFondBottomLeft.setLayoutY(posY + 40);
            rbImageFondBottomCenter.setLayoutY(posY + 40);
            rbImageFondBottomRight.setLayoutY(posY + 40);
            Label lblPosit = new Label(rbLocalisation.getString("interface.positionImageFond"));
            lblPosit.setLayoutX(150);
            lblPosit.setLayoutY(10);
            Label lblDXSpinner = new Label("dX ");
            lblDXSpinner.setLayoutX(25);
            lblDXSpinner.setLayoutY(125);
            Label lblDYSpinner = new Label("dY ");
            lblDYSpinner.setLayoutX(175);
            lblDYSpinner.setLayoutY(125);
            BigDecimalField dXSpinnerImageFond = new BigDecimalField(new BigDecimal(imagesFond[i].getOffsetX()));
            dXSpinnerImageFond.setLayoutX(50);
            dXSpinnerImageFond.setLayoutY(120);
            dXSpinnerImageFond.setMaxValue(new BigDecimal(2000));
            dXSpinnerImageFond.setMinValue(new BigDecimal(-2000));
            dXSpinnerImageFond.setMaxWidth(100);
            BigDecimalField dYSpinnerImageFond = new BigDecimalField(new BigDecimal(imagesFond[i].getOffsetY()));
            dYSpinnerImageFond.setLayoutX(200);
            dYSpinnerImageFond.setLayoutY(120);
            dYSpinnerImageFond.setMaxValue(new BigDecimal(2000));
            dYSpinnerImageFond.setMinValue(new BigDecimal(-2000));
            dYSpinnerImageFond.setMaxWidth(100);
            CheckBox cbMasquableImageFond = new CheckBox(rbLocalisation.getString("interface.masquableImageFond"));
            cbMasquableImageFond.setLayoutX(150);
            cbMasquableImageFond.setLayoutY(90);
            cbMasquableImageFond.setSelected(imagesFond[i].isMasquable());

            Label lblOpaciteImageFond = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
            lblOpaciteImageFond.setLayoutX(10);
            lblOpaciteImageFond.setLayoutY(160);
            Slider slOpaciteImageFond = new Slider(0, 1.0, imagesFond[i].getOpacite());
            slOpaciteImageFond.setLayoutX(120);
            slOpaciteImageFond.setLayoutY(160);
            Label lblTailleImageFond = new Label(rbLocalisation.getString("interface.tailleVignettes"));
            lblTailleImageFond.setLayoutX(10);
            lblTailleImageFond.setLayoutY(190);
            double echelle = imagesFond[i].getTailleX() / imagesFond[i].getImgFond().getWidth();
            Slider slTailleImageFond = new Slider(0.1, 2.0, echelle);
            slTailleImageFond.setLayoutX(120);
            slTailleImageFond.setLayoutY(190);
            Label lblUrlImageFond = new Label("url");
            lblUrlImageFond.setLayoutX(10);
            lblUrlImageFond.setLayoutY(222);
            TextField txtUrlImageFond = new TextField(imagesFond[i].getUrl());
            txtUrlImageFond.setPrefHeight(20);
            txtUrlImageFond.setPrefWidth(200);
            txtUrlImageFond.setLayoutX(120);
            txtUrlImageFond.setLayoutY(220);
            Label lblInfobulleImageFond = new Label(rbLocalisation.getString("interface.infobulle"));
            lblInfobulleImageFond.setLayoutX(10);
            lblInfobulleImageFond.setLayoutY(252);
            TextField txtInfobulleImageFond = new TextField(imagesFond[i].getInfobulle());
            txtInfobulleImageFond.setPrefHeight(20);
            txtInfobulleImageFond.setPrefWidth(200);
            txtInfobulleImageFond.setLayoutX(120);
            txtInfobulleImageFond.setLayoutY(250);

            apImagesFond.getChildren().addAll(ivImageFond, fond1,
                    lblPosit,
                    rbImageFondTopLeft, rbImageFondTopCenter, rbImageFondTopRight,
                    rbImageFondMiddleLeft, rbImageFondMiddleCenter, rbImageFondMiddleRight,
                    rbImageFondBottomLeft, rbImageFondBottomCenter, rbImageFondBottomRight,
                    cbMasquableImageFond,
                    lblDXSpinner, dXSpinnerImageFond, lblDYSpinner, dYSpinnerImageFond,
                    lblOpaciteImageFond, slOpaciteImageFond,
                    lblTailleImageFond, slTailleImageFond,
                    lblUrlImageFond, txtUrlImageFond,
                    lblInfobulleImageFond, txtInfobulleImageFond
            );

            tgPosition.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                if (tgPosition.getSelectedToggle() != null) {
                    String positImageFond = tgPosition.getSelectedToggle().getUserData().toString();
                    imagesFond[j].setPosX(positImageFond.split(":")[1]);
                    imagesFond[j].setPosY(positImageFond.split(":")[0]);
                    //System.out.println("Image n° " + j + " position : " + positImageFond);
                    afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique
                    );
                }
            });
            dXSpinnerImageFond.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
                imagesFond[j].setOffsetX(new_value.doubleValue());
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            });
            dYSpinnerImageFond.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
                imagesFond[j].setOffsetY(new_value.doubleValue());
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            });
            slOpaciteImageFond.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                if (newValue != null) {
                    double opac = (double) newValue;
                    imagesFond[j].setOpacite(opac);
                    afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                }
            });
            slTailleImageFond.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                if (newValue != null) {
                    double taille = (double) newValue;
                    imagesFond[j].setTailleX((int) (imagesFond[j].getImgFond().getWidth() * taille));
                    imagesFond[j].setTailleY((int) (imagesFond[j].getImgFond().getHeight() * taille));
                    afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                }
            });
            txtUrlImageFond.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                String txt = newValue;
                imagesFond[j].setUrl(txt);
            });
            txtInfobulleImageFond.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                String txt = newValue;
                imagesFond[j].setInfobulle(txt);
            });
            cbMasquableImageFond.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                if (new_val != null) {
                    imagesFond[j].setMasquable(new_val);
                }
            });

            apImageFond.getChildren().add(apImagesFond);
        }
        afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
    }

    private String ajoutFenetreImage() {
        File repert;
        if (strRepertImagesFond.equals("")) {
            repert = new File(strCurrentDir + File.separator);
        } else {
            repert = new File(strRepertImagesFond);
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().addAll(extFilterImages);

        File fichierImage = fileChooser.showOpenDialog(null);
        if (fichierImage != null) {
            strRepertImagesFond = fichierImage.getParent();
            File repertImage = new File(strRepertTemp + File.separator + "images");
            if (!repertImage.exists()) {
                repertImage.mkdirs();
            }
            try {
                copieFichierRepertoire(fichierImage.getAbsolutePath(), repertImage.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }

            return fichierImage.getAbsolutePath();
        } else {
            return "";
        }

    }

    private void ajoutImageFond() {
        if (iNombreImagesFond < 20) {
            File repert;
            if (strRepertImagesFond.equals("")) {
                repert = new File(strCurrentDir + File.separator);
            } else {
                repert = new File(strRepertImagesFond);
            }

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

            fileChooser.setInitialDirectory(repert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fichierImage = fileChooser.showOpenDialog(null);
            if (fichierImage != null) {
                strRepertImagesFond = fichierImage.getParent();
                File repertImage = new File(strRepertTemp + File.separator + "images");
                if (!repertImage.exists()) {
                    repertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fichierImage.getAbsolutePath(), repertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

                imagesFond[iNombreImagesFond] = new ImageFond();
                imagesFond[iNombreImagesFond].setFichierImage(fichierImage.getAbsolutePath());
                Image imgFond = new Image("file:" + fichierImage.getAbsolutePath());
                imagesFond[iNombreImagesFond].setImgFond(imgFond);
                imagesFond[iNombreImagesFond].setTailleX((int) imgFond.getWidth());
                imagesFond[iNombreImagesFond].setTailleY((int) imgFond.getHeight());
                //System.out.println("Ajoute ImageFond Taille " + imagesFond[nombreImagesFond].getTailleX()
                // + "x" + imagesFond[nombreImagesFond].getTailleY());
                iNombreImagesFond++;
                afficheImagesFondInterface();
            }
        }
    }

    /**
     *
     * @param width
     * @param height
     */
    public void creeInterface(int width, int height) {
        List<String> lstPolices = new ArrayList<>();
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
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        repertBoutonsPrincipal = strRepertAppli + File.separator + "theme/barreNavigation";
        repertHotSpots = strRepertAppli + File.separator + "theme/hotspots";
        repertHotSpotsPhoto = strRepertAppli + File.separator + "theme/photos";
        repertBoussoles = strRepertAppli + File.separator + "theme/boussoles";
        repertMasques = strRepertAppli + File.separator + "theme/MA";
        repertReseauxSociaux = strRepertAppli + File.separator + "theme/reseaux";
        chargeBarre(styleBarreClassique, strStyleHotSpots, imageMasque);
        ArrayList<String> listeStyles = listerStyle(repertBoutonsPrincipal);
        ArrayList<String> listeHotSpots = listerHotSpots(repertHotSpots);
        ArrayList<String> listeHotSpotsPhoto = listerHotSpots(repertHotSpotsPhoto);
        ArrayList<String> listeBoussoles = listerBoussoles(repertBoussoles);
        ArrayList<String> listeMasques = listerMasques(repertMasques);
        int nombreHotSpots = listeHotSpots.size();
        ImageView[] ivHotspots = new ImageView[nombreHotSpots];
        int nombreHotSpotsPhoto = listeHotSpotsPhoto.size();
        ImageView[] ivHotspotsPhoto = new ImageView[nombreHotSpotsPhoto];
        imageClaire = new Image("file:" + strRepertAppli + File.separator + "images/claire.jpg");
        imageSombre = new Image("file:" + strRepertAppli + File.separator + "images/sombre.jpg");
        imgSuivant = new ImageView(new Image("file:" + strRepertAppli + File.separator + "panovisu/images/suivant.png"));
        imgPrecedent = new ImageView(new Image("file:" + strRepertAppli + File.separator + "panovisu/images/precedent.png"));
        fondSuivant = new VBox(imgSuivant);
        fondPrecedent = new VBox(imgPrecedent);
        fondSuivant.setStyle("-fx-background-color : black;");
        fondSuivant.setOpacity(0.5);
        fondPrecedent.setStyle("-fx-background-color : black;");
        fondPrecedent.setOpacity(0.5);

        txtTitre = new Label(rbLocalisation.getString("interface.titre"));
        Font fonte = Font.font(strTitrePoliceNom, Double.parseDouble(strTitrePoliceTaille));
        txtTitre.setFont(fonte);
        double largeurOutils = 380;

        tabInterface = new Pane();
        hbInterface = new HBox();
        hbInterface.setPrefWidth(width);
        hbInterface.setPrefHeight(height);
        tabInterface.getChildren().add(hbInterface);
        apVisualisation = new AnchorPane();
        apVisualisation.setPrefWidth(width - largeurOutils);
        apVisualisation.setMaxWidth(width - largeurOutils);
        apVisualisation.setMinWidth(width - largeurOutils);
        apVisualisation.setPrefHeight(height);
        vbOutils = new VBox();
        ScrollPane SPOutils = new ScrollPane(vbOutils);
        SPOutils.setId("spOutils");
        SPOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        SPOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPOutils.setMaxHeight(height - 100);
        SPOutils.setFitToWidth(true);
        SPOutils.setFitToHeight(true);
        SPOutils.setPrefWidth(largeurOutils);
        SPOutils.setMaxWidth(largeurOutils);
        SPOutils.setTranslateY(15);
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        //vbOutils.setMinHeight(height);
//        vbOutils.setStyle("-fx-background-color : #ccc;");
        hbInterface.getChildren().addAll(apVisualisation, SPOutils);
        /*
         * ***************************************************************
         *     Panneau de visualisation de l'interface
         * ***************************************************************
         */
        double tailleMax = apVisualisation.getPrefWidth() - 40;
        if (tailleMax > 1200) {
            tailleMax = 1200;
        }
        ivVisualisation = new ImageView(imageClaire);
        ivVisualisation.setFitWidth(tailleMax);
        if (tailleMax * 2.d / 3.d > height - 100) {
            ivVisualisation.setFitHeight(height - 100);
        } else {
            ivVisualisation.setFitHeight(tailleMax * 2.d / 3.d);
        }
        ivVisualisation.setSmooth(true);
        double LX = (apVisualisation.getPrefWidth() - ivVisualisation.getFitWidth()) / 2;
        ivVisualisation.setLayoutX(LX);
        ivVisualisation.setLayoutY(20);
        txtTitre.setMinSize(tailleMax, 30);
        txtTitre.setPadding(new Insets(5));
        txtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        txtTitre.setAlignment(Pos.CENTER);
        txtTitre.setOpacity(titreOpacite);
        txtTitre.setTextFill(Color.WHITE);
        txtTitre.setLayoutY(20);
        txtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
        rbClair = new RadioButton("Image claire");
        rbSombre = new RadioButton("Image Sombre");
        rbPerso = new RadioButton("");
        cbImage = new ComboBox();
        cbImage.setDisable(true);
        double positrb = ivVisualisation.getFitHeight() + 30;
        rbClair.setToggleGroup(grpImage);
        rbSombre.setToggleGroup(grpImage);
        rbPerso.setToggleGroup(grpImage);
        apVisualisation.getChildren().addAll(rbClair, rbSombre);
        rbClair.setLayoutX(LX + 40);
        rbClair.setLayoutY(positrb);
        rbClair.setSelected(true);
        rbSombre.setLayoutX(LX + 180);
        rbSombre.setLayoutY(positrb);
        rbPerso.setLayoutX(LX + 320);
        rbPerso.setLayoutY(positrb);
        cbImage.setLayoutX(LX + 350);
        cbImage.setLayoutY(positrb - 3);
        rbPerso.setDisable(true);
        rbClair.setUserData("claire");
        rbSombre.setUserData("sombre");
        rbPerso.setUserData("perso");
        imgBoussole = new ImageView(new Image("file:" + repertBoussoles + File.separator + strImageBoussole));
        imgAiguille = new ImageView("file:" + repertBoussoles + File.separator + "aiguille.png");
        apAfficheBarrePersonnalisee = new AnchorPane();
        apAfficheBarrePersonnalisee.setBackground(Background.EMPTY);
        apAfficheBarrePersonnalisee.setLayoutX(ivVisualisation.getLayoutX());
        apAfficheBarrePersonnalisee.setLayoutY(ivVisualisation.getLayoutY());
        imgTwitter = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxTwitter));
        imgGoogle = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxGoogle));
        imgFacebook = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxFacebook));
        imgEmail = new ImageView(new Image("file:" + repertReseauxSociaux + File.separator + imageReseauxSociauxEmail));
        apVisuVignettes = new AnchorPane();
        apVisuPlan = new AnchorPane();
        apVisuComboMenu = new AnchorPane();
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().add(ivVisualisation);
        apVisualisation.getChildren().addAll(txtTitre, imgBoussole, imgAiguille, ivMasque, imgTwitter, imgGoogle, imgFacebook, imgEmail, apVisuVignettes, apVisuComboMenu, fondSuivant, fondPrecedent, apAfficheBarrePersonnalisee);
        fondPrecedent.setPrefWidth(64);
        fondPrecedent.setPrefHeight(64);
        fondSuivant.setPrefWidth(64);
        fondSuivant.setPrefHeight(64);
        fondPrecedent.setMaxWidth(64);
        fondPrecedent.setMaxHeight(64);
        fondSuivant.setMaxWidth(64);
        fondSuivant.setMaxHeight(64);
        fondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        fondPrecedent.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - fondPrecedent.getPrefHeight()) / 2);
        fondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - fondPrecedent.getPrefWidth()));
        fondSuivant.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - fondSuivant.getPrefHeight()) / 2);
        fondSuivant.setVisible(bSuivantPrecedent);
        fondPrecedent.setVisible(bSuivantPrecedent);
        apAfficheDiapo = new AnchorPane();
        apAfficheDiapo.setPrefWidth(ivVisualisation.getFitWidth());
        apAfficheDiapo.setPrefHeight(ivVisualisation.getFitHeight());
        apAfficheDiapo.setLayoutX(LX);
        apAfficheDiapo.setLayoutY(20);
        apAfficheDiapo.setTranslateZ(10);
        apAfficheDiapo.setVisible(false);
        ivDiapo = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/testImage.png"));
        ivDiapo.setPreserveRatio(false);
        ivDiapo.setFitHeight(ivVisualisation.getFitHeight() * 0.8);
        ivDiapo.setFitWidth(ivVisualisation.getFitWidth() * 0.8);
        ivDiapo.setLayoutX((ivVisualisation.getFitWidth() - ivDiapo.getFitWidth()) / 2 + LX);
        ivDiapo.setLayoutY((ivVisualisation.getFitHeight() - ivDiapo.getFitHeight()) / 2 + 20);
        ivDiapo.setVisible(false);
        apVisualisation.getChildren().addAll(apAfficheDiapo, ivDiapo);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();

        afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);

        /*
         * *****************************************
         *     Pannels d'outils 
         * *****************************************
         */
        AnchorPane apCoulTheme = new AnchorPane();
        cpCouleurTheme = new ColorPicker(couleurTheme);
        String coul2 = cpCouleurTheme.getValue().toString().substring(2, 8);
        changeCouleurTitre(coul2);
        changeCouleurVignettes(coul2);
        Label lblCouleurTheme = new Label(rbLocalisation.getString("interface.couleurTheme"));
        lblCouleurTheme.setLayoutX(20);
        lblCouleurTheme.setLayoutY(20);
        cpCouleurTheme.setLayoutX(150);
        cpCouleurTheme.setLayoutY(20);
        apCoulTheme.setPrefHeight(40);
        apCoulTheme.setMinHeight(40);
        apCoulTheme.getChildren().addAll(lblCouleurTheme, cpCouleurTheme);

        AnchorPane apCLASS = new AnchorPane();
        AnchorPane apPERS = new AnchorPane();
        AnchorPane apHS = new AnchorPane();
        AnchorPane apTIT = new AnchorPane();
        AnchorPane apECR = new AnchorPane();
        AnchorPane apDIA = new AnchorPane();
        AnchorPane apBOUSS = new AnchorPane();
        AnchorPane apMASQ = new AnchorPane();
        AnchorPane apRS = new AnchorPane();
        AnchorPane apVIG = new AnchorPane();
        AnchorPane apCBM = new AnchorPane();
        AnchorPane apPLAN = new AnchorPane();
        AnchorPane apIF = new AnchorPane();
        AnchorPane apMC = new AnchorPane();

        /*
         * *****************************************
         *     Panel Titre 
         * ****************************************
         */
        AnchorPane apTitre = new AnchorPane();
        apTitre.setLayoutY(40);
        apTitre.setLayoutX(10);
        apTitre.setPrefHeight(230);
        Label lblPanelTitre = new Label(rbLocalisation.getString("interface.styleTitre"));
        lblPanelTitre.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelTitre.setStyle("-fx-background-color : #666");
        lblPanelTitre.setTextFill(Color.WHITE);
        lblPanelTitre.setPadding(new Insets(5));
        lblPanelTitre.setLayoutX(10);
        lblPanelTitre.setLayoutY(10);
        ImageView ivBtnPlusTitre = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusTitre.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusTitre.setLayoutY(11);
        cbAfficheTitre = new CheckBox(rbLocalisation.getString("interface.afficheTitre"));
        cbAfficheTitre.setSelected(bAfficheTitre);
        cbAfficheTitre.setLayoutX(10);
        cbAfficheTitre.setLayoutY(15);

        cbListePolices = new ComboBox(listePolices);
        cbListePolices.setValue(strTitrePoliceNom);
        cbListePolices.setLayoutX(180);
        cbListePolices.setLayoutY(42);
        cbListePolices.setMaxWidth(135);

        Label lblChoixPoliceTitre = new Label(rbLocalisation.getString("interface.choixPolice"));
        lblChoixPoliceTitre.setLayoutX(10);
        lblChoixPoliceTitre.setLayoutY(45);
        Label lblChoixTailleTitre = new Label(rbLocalisation.getString("interface.choixTaillePolice"));
        lblChoixTailleTitre.setLayoutX(10);
        lblChoixTailleTitre.setLayoutY(75);
        slTaillePolice = new Slider(8, 72, 12);
        slTaillePolice.setLayoutX(180);
        slTaillePolice.setLayoutY(75);

        Label lblChoixCouleurTitre = new Label(rbLocalisation.getString("interface.choixCouleur"));
        lblChoixCouleurTitre.setLayoutX(10);
        lblChoixCouleurTitre.setLayoutY(105);
        cpCouleurTitre = new ColorPicker(Color.valueOf(strCouleurTitre));
        cpCouleurTitre.setLayoutX(180);
        cpCouleurTitre.setLayoutY(103);
        Label lblChoixCouleurFondTitre = new Label(rbLocalisation.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondTitre.setLayoutX(10);
        lblChoixCouleurFondTitre.setLayoutY(135);
        cpCouleurFondTitre = new ColorPicker(Color.valueOf(strCouleurFondTitre));
        cpCouleurFondTitre.setLayoutX(180);
        cpCouleurFondTitre.setLayoutY(133);

        Label lblChoixOpacite = new Label(rbLocalisation.getString("interface.choixOpaciteTitre"));
        lblChoixOpacite.setLayoutX(10);
        lblChoixOpacite.setLayoutY(165);
        slOpacite = new Slider(0, 1, titreOpacite);
        slOpacite.setLayoutX(180);
        slOpacite.setLayoutY(165);
        Label lblChoixTaille = new Label(rbLocalisation.getString("interface.choixTailleTitre"));
        lblChoixTaille.setLayoutX(10);
        lblChoixTaille.setLayoutY(195);
        slTaille = new Slider(0, 100, titreTaille);
        slTaille.setLayoutX(180);
        slTaille.setLayoutY(195);

        apTitre.getChildren().addAll(
                cbAfficheTitre,
                lblChoixPoliceTitre, cbListePolices,
                lblChoixTailleTitre, slTaillePolice,
                lblChoixCouleurTitre, cpCouleurTitre,
                lblChoixCouleurFondTitre, cpCouleurFondTitre,
                lblChoixOpacite, slOpacite,
                lblChoixTaille, slTaille);
        double tailleInitialeTitre = apTitre.getPrefHeight();
        apTitre.setPrefHeight(0);
        apTitre.setMaxHeight(0);
        apTitre.setMinHeight(0);
        apTitre.setVisible(false);

        lblPanelTitre.setOnMouseClicked((MouseEvent me) -> {
            if (apTitre.isVisible()) {
                apTitre.setPrefHeight(0);
                apTitre.setMaxHeight(0);
                apTitre.setMinHeight(0);
                apTitre.setVisible(false);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apTitre.setPrefHeight(tailleInitialeTitre);
                apTitre.setMaxHeight(tailleInitialeTitre);
                apTitre.setMinHeight(tailleInitialeTitre);
                apTitre.setVisible(true);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusTitre.setOnMouseClicked((MouseEvent me) -> {
            if (apTitre.isVisible()) {
                apTitre.setPrefHeight(0);
                apTitre.setMaxHeight(0);
                apTitre.setMinHeight(0);
                apTitre.setVisible(false);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apTitre.setPrefHeight(tailleInitialeTitre);
                apTitre.setMaxHeight(tailleInitialeTitre);
                apTitre.setMinHeight(tailleInitialeTitre);
                apTitre.setVisible(true);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        /*
         * *****************************************
         *     Panel Fenetre 
         * ****************************************
         */
        apFenetreAfficheInfo.setVisible(false);
        lblFenetreURL.setVisible(false);
        //apVisualisation.getChildren().add(apFenetreAfficheInfo);
        AnchorPane apFenetre = new AnchorPane();
        apFenetre.setLayoutY(40);
        apFenetre.setLayoutX(10);
        apFenetre.setPrefHeight(660);
        Label lblPanelFenetre = new Label(rbLocalisation.getString("interface.styleFenetre"));
        lblPanelFenetre.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelFenetre.setStyle("-fx-background-color : #666");
        lblPanelFenetre.setTextFill(Color.WHITE);
        lblPanelFenetre.setPadding(new Insets(5));
        lblPanelFenetre.setLayoutX(10);
        lblPanelFenetre.setLayoutY(10);
        ImageView ivBtnPlusFenetre = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusFenetre.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusFenetre.setLayoutY(11);
        CheckBox cbAfficheFenetreInfo = new CheckBox(rbLocalisation.getString("interface.afficheFenetreInfo"));
        cbAfficheFenetreInfo.setSelected(bAfficheFenetreInfo);
        cbAfficheFenetreInfo.setLayoutX(10);
        cbAfficheFenetreInfo.setLayoutY(15);
        cbFenetreInfoPersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreInfoPersonnalise"));
        cbFenetreInfoPersonnalise.setSelected(bFenetreInfoPersonnalise);
        cbFenetreInfoPersonnalise.setLayoutX(10);
        cbFenetreInfoPersonnalise.setLayoutY(45);
        AnchorPane apFenetreInfoPers = new AnchorPane();
        apFenetreInfoPers.setLayoutY(75);
        apFenetreInfoPers.setDisable(!bFenetreInfoPersonnalise);
        Line ligne1 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne1.setStrokeWidth(0.2);

        Label lblFenetreInfoImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreInfoImage.setLayoutX(20);
        lblFenetreInfoImage.setLayoutY(5);
        txtFenetreInfoImage = new TextField();
        txtFenetreInfoImage.setLayoutX(40);
        txtFenetreInfoImage.setLayoutY(25);
        txtFenetreInfoImage.setPrefWidth(250);
        Button btnFenetreInfo = new Button("...");
        btnFenetreInfo.setLayoutX(300);
        btnFenetreInfo.setLayoutY(25);
        Label lblFenetreInfoTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreInfoTaille.setLayoutX(20);
        lblFenetreInfoTaille.setLayoutY(55);
        slFenetreInfoTaille = new Slider(25, 200, fenetreInfoTaille);
        slFenetreInfoTaille.setLayoutX(140);
        slFenetreInfoTaille.setLayoutY(55);
        Label lblFenetreInfoOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreInfoOpacite.setLayoutX(20);
        lblFenetreInfoOpacite.setLayoutY(85);
        slFenetreInfoOpacite = new Slider(0, 1, fenetreInfoOpacite);
        slFenetreInfoOpacite.setLayoutX(140);
        slFenetreInfoOpacite.setLayoutY(85);
        Label lblFenetreInfoPosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreInfoPosX.setLayoutX(20);
        lblFenetreInfoPosX.setLayoutY(120);
        bdfFenetreInfoPosX = new BigDecimalField(BigDecimal.valueOf(fenetreInfoPosX));
        bdfFenetreInfoPosX.setLayoutX(100);
        bdfFenetreInfoPosX.setLayoutY(115);
        bdfFenetreInfoPosX.setPrefWidth(60);
        Label lblFenetreInfoPosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreInfoPosY.setLayoutX(180);
        lblFenetreInfoPosY.setLayoutY(120);
        bdfFenetreInfoPosY = new BigDecimalField(BigDecimal.valueOf(fenetreInfoPosY));
        bdfFenetreInfoPosY.setLayoutX(260);
        bdfFenetreInfoPosY.setLayoutY(115);
        bdfFenetreInfoPosY.setPrefWidth(60);
        Label lblFenetreInfoURL = new Label("URL");
        lblFenetreInfoURL.setLayoutX(20);
        lblFenetreInfoURL.setLayoutY(170);
        txtFenetreURL = new TextField();
        txtFenetreURL.setLayoutX(120);
        txtFenetreURL.setLayoutY(165);
        txtFenetreURL.setPrefWidth(210);
        Label lblFenetreInfoTexteURL = new Label(rbLocalisation.getString("interface.fenetreLibelleURL"));
        lblFenetreInfoTexteURL.setLayoutX(20);
        lblFenetreInfoTexteURL.setLayoutY(200);
        txtFenetreTexteURL = new TextField();
        txtFenetreTexteURL.setLayoutX(120);
        txtFenetreTexteURL.setLayoutY(195);
        txtFenetreTexteURL.setPrefWidth(210);

        Label lblFenetrePoliceTaille = new Label(rbLocalisation.getString("interface.fenetrePoliceTaille"));
        lblFenetrePoliceTaille.setLayoutX(20);
        lblFenetrePoliceTaille.setLayoutY(230);
        slFenetrePoliceTaille = new Slider(7, 48, fenetrePoliceTaille);
        slFenetrePoliceTaille.setLayoutX(140);
        slFenetrePoliceTaille.setLayoutY(230);

        Label lblFenetreURLCouleur = new Label(rbLocalisation.getString("interface.fenetreURLChoixCouleur"));
        lblFenetreURLCouleur.setLayoutX(20);
        lblFenetreURLCouleur.setLayoutY(260);
        cpFenetreURLCouleur = new ColorPicker(Color.valueOf(strFenetreURLCouleur));
        cpFenetreURLCouleur.setLayoutX(200);
        cpFenetreURLCouleur.setLayoutY(256);

        Label lblFenetreURLPosX = new Label(rbLocalisation.getString("interface.fenetrePosX") + " URL");
        lblFenetreURLPosX.setLayoutX(20);
        lblFenetreURLPosX.setLayoutY(295);

        bdfFenetreURLPosX = new BigDecimalField(BigDecimal.valueOf(fenetreInfoPosX));
        bdfFenetreURLPosX.setLayoutX(110);
        bdfFenetreURLPosX.setLayoutY(290);
        bdfFenetreURLPosX.setPrefWidth(60);
        Label lblFenetreURLPosY = new Label(rbLocalisation.getString("interface.fenetrePosY") + " URL");
        lblFenetreURLPosY.setLayoutX(185);
        lblFenetreURLPosY.setLayoutY(295);
        bdfFenetreURLPosY = new BigDecimalField(BigDecimal.valueOf(fenetreInfoPosY));
        bdfFenetreURLPosY.setLayoutX(275);
        bdfFenetreURLPosY.setLayoutY(290);
        bdfFenetreURLPosY.setPrefWidth(60);

        Line ligne2 = new Line(0, 320, vbOutils.getPrefWidth(), 320);
        ligne2.setStrokeWidth(0.2);

        apFenetreInfoPers.getChildren().addAll(
                ligne1,
                lblFenetreInfoImage, txtFenetreInfoImage, btnFenetreInfo,
                lblFenetreInfoTaille, slFenetreInfoTaille,
                lblFenetreInfoOpacite, slFenetreInfoOpacite,
                lblFenetreInfoPosX, bdfFenetreInfoPosX,
                lblFenetreInfoPosY, bdfFenetreInfoPosY,
                lblFenetreInfoURL, txtFenetreURL,
                lblFenetreInfoTexteURL, txtFenetreTexteURL,
                lblFenetrePoliceTaille, slFenetrePoliceTaille,
                lblFenetreURLCouleur, cpFenetreURLCouleur,
                lblFenetreURLPosX, bdfFenetreURLPosX,
                lblFenetreURLPosY, bdfFenetreURLPosY,
                ligne2
        );

        CheckBox cbAfficheFenetreAide = new CheckBox(rbLocalisation.getString("interface.afficheFenetreAide"));
        cbAfficheFenetreAide.setSelected(bAfficheFenetreAide);
        cbAfficheFenetreAide.setLayoutX(10);
        cbAfficheFenetreAide.setLayoutY(400);

        cbFenetreAidePersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreAidePersonnalise"));
        cbFenetreAidePersonnalise.setSelected(bFenetreAidePersonnalise);
        cbFenetreAidePersonnalise.setLayoutX(10);
        cbFenetreAidePersonnalise.setLayoutY(430);
        AnchorPane apFenetreAidePers = new AnchorPane();
        apFenetreAidePers.setLayoutY(460);
        apFenetreAidePers.setDisable(!bFenetreAidePersonnalise);
        Line ligne3 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne3.setStrokeWidth(0.2);

        Label lblFenetreAideImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreAideImage.setLayoutX(20);
        lblFenetreAideImage.setLayoutY(5);
        txtFenetreAideImage = new TextField();
        txtFenetreAideImage.setLayoutX(40);
        txtFenetreAideImage.setLayoutY(25);
        txtFenetreAideImage.setPrefWidth(250);
        Button btnFenetreAide = new Button("...");
        btnFenetreAide.setLayoutX(300);
        btnFenetreAide.setLayoutY(25);
        Label lblFenetreAideTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreAideTaille.setLayoutX(20);
        lblFenetreAideTaille.setLayoutY(55);
        slFenetreAideTaille = new Slider(50, 200, fenetreAideTaille);
        slFenetreAideTaille.setLayoutX(140);
        slFenetreAideTaille.setLayoutY(55);
        Label lblFenetreAideOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreAideOpacite.setLayoutX(20);
        lblFenetreAideOpacite.setLayoutY(85);
        slFenetreAideOpacite = new Slider(0, 1, fenetreAideOpacite);
        slFenetreAideOpacite.setLayoutX(140);
        slFenetreAideOpacite.setLayoutY(85);
        Label lblFenetreAidePosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreAidePosX.setLayoutX(20);
        lblFenetreAidePosX.setLayoutY(120);
        bdfFenetreAidePosX = new BigDecimalField(BigDecimal.valueOf(fenetreAidePosX));
        bdfFenetreAidePosX.setLayoutX(100);
        bdfFenetreAidePosX.setLayoutY(115);
        bdfFenetreAidePosX.setPrefWidth(60);
        Label lblFenetreAidePosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreAidePosY.setLayoutX(180);
        lblFenetreAidePosY.setLayoutY(120);
        bdfFenetreAidePosY = new BigDecimalField(BigDecimal.valueOf(fenetreAidePosY));
        bdfFenetreAidePosY.setLayoutX(260);
        bdfFenetreAidePosY.setLayoutY(115);
        bdfFenetreAidePosY.setPrefWidth(60);

        Line ligne4 = new Line(0, 150, vbOutils.getPrefWidth(), 150);
        ligne4.setStrokeWidth(0.2);

        apFenetreAidePers.getChildren().addAll(
                ligne3,
                lblFenetreAideImage, txtFenetreAideImage, btnFenetreAide,
                lblFenetreAideTaille, slFenetreAideTaille,
                lblFenetreAideOpacite, slFenetreAideOpacite,
                lblFenetreAidePosX, bdfFenetreAidePosX,
                lblFenetreAidePosY, bdfFenetreAidePosY,
                ligne4
        );

        apFenetre.getChildren().addAll(
                cbAfficheFenetreInfo,
                cbFenetreInfoPersonnalise,
                apFenetreInfoPers,
                cbAfficheFenetreAide,
                cbFenetreAidePersonnalise,
                apFenetreAidePers
        );
        double tailleInitialeFenetre = apFenetre.getPrefHeight();
        apFenetre.setPrefHeight(0);
        apFenetre.setMaxHeight(0);
        apFenetre.setMinHeight(0);
        apFenetre.setVisible(false);

        lblPanelFenetre.setOnMouseClicked((MouseEvent me) -> {
            if (apFenetre.isVisible()) {
                apFenetre.setPrefHeight(0);
                apFenetre.setMaxHeight(0);
                apFenetre.setMinHeight(0);
                apFenetre.setVisible(false);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(false);
            } else {
                apFenetre.setPrefHeight(tailleInitialeFenetre);
                apFenetre.setMaxHeight(tailleInitialeFenetre);
                apFenetre.setMinHeight(tailleInitialeFenetre);
                apFenetre.setVisible(true);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(true);
            }
        });
        ivBtnPlusFenetre.setOnMouseClicked((MouseEvent me) -> {
            if (apFenetre.isVisible()) {
                apFenetre.setPrefHeight(0);
                apFenetre.setMaxHeight(0);
                apFenetre.setMinHeight(0);
                apFenetre.setVisible(false);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(false);
            } else {
                apFenetre.setPrefHeight(tailleInitialeFenetre);
                apFenetre.setMaxHeight(tailleInitialeFenetre);
                apFenetre.setMinHeight(tailleInitialeFenetre);
                apFenetre.setVisible(true);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(true);
            }
        });

        /*
         * *****************************************
         *     Panel Diaporama
         * ****************************************
         */
        AnchorPane apDiapo = new AnchorPane();
        apDiapo.setLayoutY(40);
        apDiapo.setLayoutX(10);
        apDiapo.setPrefHeight(100);
        Label lblDiaporama = new Label(rbLocalisation.getString("interface.diaporama"));
        lblDiaporama.setPrefWidth(vbOutils.getPrefWidth());
        lblDiaporama.setStyle("-fx-background-color : #666");
        lblDiaporama.setTextFill(Color.WHITE);
        lblDiaporama.setPadding(new Insets(5));
        lblDiaporama.setLayoutX(10);
        lblDiaporama.setLayoutY(10);
        ImageView ivBtnDiaporama = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnDiaporama.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnDiaporama.setLayoutY(11);
        CheckBox cbVisualiseDiapo = new CheckBox(rbLocalisation.getString("interface.visualiseDiaporama"));
        cbVisualiseDiapo.setLayoutX(10);
        cbVisualiseDiapo.setLayoutY(10);
        cbVisualiseDiapo.setSelected(false);
        Label lblChoixCouleurDiaporama = new Label(rbLocalisation.getString("interface.choixCouleurDiaporama"));
        lblChoixCouleurDiaporama.setLayoutX(10);
        lblChoixCouleurDiaporama.setLayoutY(40);
        cpCouleurDiaporama = new ColorPicker(Color.valueOf(strCouleurDiaporama));
        cpCouleurDiaporama.setLayoutX(180);
        cpCouleurDiaporama.setLayoutY(38);

        Label lblChoixOpaciteDiaporama = new Label(rbLocalisation.getString("interface.choixOpaciteDiaporama"));
        lblChoixOpaciteDiaporama.setLayoutX(10);
        lblChoixOpaciteDiaporama.setLayoutY(70);
        slOpaciteDiaporama = new Slider(0, 1, diaporamaOpacite);
        slOpaciteDiaporama.setLayoutX(180);
        slOpaciteDiaporama.setLayoutY(70);

        apDiapo.getChildren().addAll(
                cbVisualiseDiapo,
                lblChoixCouleurDiaporama, cpCouleurDiaporama,
                lblChoixOpaciteDiaporama, slOpaciteDiaporama
        );
        double tailleInitialeDiaporama = apDiapo.getPrefHeight();
        apDiapo.setPrefHeight(0);
        apDiapo.setMaxHeight(0);
        apDiapo.setMinHeight(0);
        apDiapo.setVisible(false);

        lblDiaporama.setOnMouseClicked((MouseEvent me) -> {
            if (apDiapo.isVisible()) {
                apDiapo.setPrefHeight(0);
                apDiapo.setMaxHeight(0);
                apDiapo.setMinHeight(0);
                apDiapo.setVisible(false);
                ivBtnDiaporama.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(false);
            } else {
                apDiapo.setPrefHeight(tailleInitialeDiaporama);
                apDiapo.setMaxHeight(tailleInitialeDiaporama);
                apDiapo.setMinHeight(tailleInitialeDiaporama);
                apDiapo.setVisible(true);
                ivBtnDiaporama.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(true);
            }
        });
        ivBtnDiaporama.setOnMouseClicked((MouseEvent me) -> {
            if (apDiapo.isVisible()) {
                apDiapo.setPrefHeight(0);
                apDiapo.setMaxHeight(0);
                apDiapo.setMinHeight(0);
                apDiapo.setVisible(false);
                ivBtnDiaporama.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(false);

            } else {
                apDiapo.setPrefHeight(tailleInitialeDiaporama);
                apDiapo.setMaxHeight(tailleInitialeDiaporama);
                apDiapo.setMinHeight(tailleInitialeDiaporama);
                apDiapo.setVisible(true);
                ivBtnDiaporama.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(true);
            }
        });

        /*
         * *************************************************
         *     Panel HotSpots 
         * *************************************************
         */
        AnchorPane apHotSpots = new AnchorPane();
        ImageView ivBtnPlusHS = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusHS.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusHS.setLayoutY(11);
        Label lblChoixHS = new Label(rbLocalisation.getString("interface.choixHotspot"));
        lblChoixHS.setPrefWidth(vbOutils.getPrefWidth());
        lblChoixHS.setStyle("-fx-background-color : #666");
        lblChoixHS.setTextFill(Color.WHITE);
        lblChoixHS.setPadding(new Insets(5));
        lblChoixHS.setLayoutX(10);
        lblChoixHS.setLayoutY(10);

        double tailleHS = 35.d * ((int) (nombreHotSpots / 9 + 1) + (int) (nombreHotSpotsPhoto / 6 + 1)) + 100;
        apHotSpots.setPrefHeight(tailleHS);
        apHotSpots.setLayoutX(50);
        apHotSpots.setLayoutY(40);
        apHotSpots.setStyle("-fx-background-color : #fff");
        apHotSpots.setPadding(new Insets(5));
        int i = 0;
        double xHS;
        double yHS = 25;
        Label lblHSPanoramique = new Label(rbLocalisation.getString("interface.HSPanoramique"));
        lblHSPanoramique.setLayoutY(5);
        lblHSPanoramique.setLayoutX(20);
        Label lblHSPhoto = new Label(rbLocalisation.getString("interface.HSPhoto"));
        lblHSPhoto.setLayoutY((int) (nombreHotSpots / 6 + 1) * 35 + 45);
        lblHSPhoto.setLayoutX(20);
        apHotSpots.getChildren().addAll(lblHSPanoramique, lblHSPhoto);
        for (String nomImage : listeHotSpots) {
            Pane fond = new Pane();
            ivHotspots[i] = new ImageView(new Image("file:" + repertHotSpots + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 9;
            int row = i / 9;
            xHS = col * 40 + 5;
            yHS = row * 35 + 25;
            fond.setLayoutX(xHS);
            fond.setLayoutY(yHS);
            fond.setStyle("-fx-background-color : #ccc");
            fond.setOnMouseClicked((MouseEvent me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpot);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                strStyleHotSpots = nomImage;
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);

            });
            fond.getChildren().add(ivHotspots[i]);
            apHotSpots.getChildren().add(fond);
            i++;

        }
        cpCouleurHotspots = new ColorPicker(couleurHotspots);
        Label lblCouleurHotspot = new Label(rbLocalisation.getString("interface.couleurHS"));
        lblCouleurHotspot.setLayoutX(20);
        lblCouleurHotspot.setLayoutY(yHS + 50);
        cpCouleurHotspots.setLayoutX(200);
        cpCouleurHotspots.setLayoutY(yHS + 50);
        apHotSpots.getChildren().addAll(lblCouleurHotspot, cpCouleurHotspots);
        i = 0;
        for (String nomImage : listeHotSpotsPhoto) {
            Pane fond = new Pane();
            ivHotspotsPhoto[i] = new ImageView(new Image("file:" + repertHotSpotsPhoto + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 9;
            int row = i / 9;
            xHS = col * 40 + 5;
            yHS = (row + (int) (nombreHotSpots / 9 + 1)) * 35 + 65;
            fond.setLayoutX(xHS);
            fond.setLayoutY(yHS);
            fond.setStyle("-fx-background-color : #ccc");
            fond.setOnMouseClicked((MouseEvent me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpot);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                strStyleHotSpotImages = nomImage;
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            });
            fond.getChildren().add(ivHotspotsPhoto[i]);
            apHotSpots.getChildren().add(fond);
            i++;

        }
        cpCouleurHotspotsPhoto = new ColorPicker(couleurHotspotsPhoto);
        Label lblCouleurHotspotPhoto = new Label(rbLocalisation.getString("interface.couleurHSPhoto"));
        lblCouleurHotspotPhoto.setLayoutX(20);
        lblCouleurHotspotPhoto.setLayoutY(tailleHS - 20);
        cpCouleurHotspotsPhoto.setLayoutX(200);
        cpCouleurHotspotsPhoto.setLayoutY(tailleHS - 20);
        apHotSpots.getChildren().addAll(lblCouleurHotspotPhoto, cpCouleurHotspotsPhoto);
        apHotSpots.setPrefHeight(0);
        apHotSpots.setMaxHeight(0);
        apHotSpots.setMinHeight(0);
        apHotSpots.setVisible(false);

        lblChoixHS.setOnMouseClicked((MouseEvent me) -> {
            if (apHotSpots.isVisible()) {
                ivBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(0);
                apHotSpots.setMaxHeight(0);
                apHotSpots.setMinHeight(0);
                apHotSpots.setVisible(false);
            } else {
                ivBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(tailleHS);
                apHotSpots.setMaxHeight(tailleHS);
                apHotSpots.setMinHeight(tailleHS);
                apHotSpots.setVisible(true);
            }
        });
        ivBtnPlusHS.setOnMouseClicked((MouseEvent me) -> {
            if (apHotSpots.isVisible()) {
                ivBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(0);
                apHotSpots.setMaxHeight(0);
                apHotSpots.setMinHeight(0);
                apHotSpots.setVisible(false);
            } else {
                ivBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(tailleHS);
                apHotSpots.setMaxHeight(tailleHS);
                apHotSpots.setMinHeight(tailleHS);
                apHotSpots.setVisible(true);
            }
        });

        /*        
         * ***************************************
         *     Panel Barre Navigation Classique 
         * ***************************************
         */
        AnchorPane apBarreClassique = new AnchorPane();
        apBarreClassique.setLayoutY(40);
        apBarreClassique.setPrefHeight(420);
        apBarreClassique.setMinWidth(vbOutils.getPrefWidth() - 20);

        cbBarreClassiqueVisible = new CheckBox(rbLocalisation.getString("interface.barreVisible"));
        cbBarreClassiqueVisible.setLayoutX(10);
        cbBarreClassiqueVisible.setLayoutY(5);
        cbBarreClassiqueVisible.setSelected(true);
        cbSuivantPrecedent = new CheckBox(rbLocalisation.getString("interface.SuivantPrecedent"));
        cbSuivantPrecedent.setLayoutX(10);
        cbSuivantPrecedent.setLayoutY(30);
        Label lblStyleBarreClassique = new Label(rbLocalisation.getString("interface.style"));
        lblStyleBarreClassique.setLayoutX(10);
        lblStyleBarreClassique.setLayoutY(60);

        cblisteStyleBarreClassique = new ComboBox();
        listeStyles.stream().forEach((nomStyle) -> {
            cblisteStyleBarreClassique.getItems().add(nomStyle);
        });
        cblisteStyleBarreClassique.setLayoutX(150);
        cblisteStyleBarreClassique.setLayoutY(70);
        cblisteStyleBarreClassique.setValue(styleBarreClassique);
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

        rbTopLeftBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbTopCenterBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbTopRightBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbMiddleLeftBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbMiddleRightBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbBottomLeftBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbBottomCenterBarreClassique.setToggleGroup(grpPositionBarreClassique);
        rbBottomRightBarreClassique.setToggleGroup(grpPositionBarreClassique);

        int posX = 250;
        int posY = 140;

        rbTopLeftBarreClassique.setLayoutX(posX);
        rbTopCenterBarreClassique.setLayoutX(posX + 20);
        rbTopRightBarreClassique.setLayoutX(posX + 40);
        rbTopLeftBarreClassique.setLayoutY(posY);
        rbTopCenterBarreClassique.setLayoutY(posY);
        rbTopRightBarreClassique.setLayoutY(posY);

        rbMiddleLeftBarreClassique.setLayoutX(posX);
        rbMiddleRightBarreClassique.setLayoutX(posX + 40);
        rbMiddleLeftBarreClassique.setLayoutY(posY + 20);
        rbMiddleRightBarreClassique.setLayoutY(posY + 20);

        rbBottomLeftBarreClassique.setLayoutX(posX);
        rbBottomCenterBarreClassique.setLayoutX(posX + 20);
        rbBottomRightBarreClassique.setLayoutX(posX + 40);
        rbBottomLeftBarreClassique.setLayoutY(posY + 40);
        rbBottomCenterBarreClassique.setLayoutY(posY + 40);
        rbBottomRightBarreClassique.setLayoutY(posY + 40);
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
        bdfOffsetXBarreClassique = new BigDecimalField(new BigDecimal(offsetXBarreClassique));
        bdfOffsetXBarreClassique.setLayoutX(50);
        bdfOffsetXBarreClassique.setLayoutY(200);
        bdfOffsetXBarreClassique.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarreClassique.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarreClassique.setMaxWidth(100);
        bdfOffsetYBarreClassique = new BigDecimalField(new BigDecimal(offsetYBarreClassique));
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

        Label lblBarreClassique = new Label(rbLocalisation.getString("interface.barreBoutons"));
        lblBarreClassique.setPrefWidth(vbOutils.getPrefWidth());
        lblBarreClassique.setStyle("-fx-background-color : #666");
        lblBarreClassique.setTextFill(Color.WHITE);
        lblBarreClassique.setPadding(new Insets(5));
        lblBarreClassique.setLayoutX(10);
        lblBarreClassique.setLayoutY(10);
        ImageView ivBtnPlusBarreClassique = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBarreClassique.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBarreClassique.setLayoutY(11);
        apBarreClassique.getChildren().addAll(
                cbBarreClassiqueVisible, cbSuivantPrecedent, lblStyleBarreClassique, cblisteStyleBarreClassique,
                lblCouleurBarreClassique, cpCouleurBarreClassique,
                lblPositionBarreClassique,
                rbTopLeftBarreClassique, rbTopCenterBarreClassique, rbTopRightBarreClassique,
                rbMiddleLeftBarreClassique, rbMiddleRightBarreClassique,
                rbBottomLeftBarreClassique, rbBottomCenterBarreClassique, rbBottomRightBarreClassique,
                lblOffsetXBarreClassique, bdfOffsetXBarreClassique, lblOffsetYBarreClassique, bdfOffsetYBarreClassique,
                lblEspacementBarreClassique, slEspacementBarreClassique,
                lblVisibiliteBarreClassique, cbDeplacementsBarreClassique, cbZoomBarreClassique, cbOutilsBarreClassique,
                cbFSBarreClassique, cbRotationBarreClassique, cbSourisBarreClassique);

        double tailleInitiale = apBarreClassique.getPrefHeight();
        apBarreClassique.setPrefHeight(0);
        apBarreClassique.setMaxHeight(0);
        apBarreClassique.setMinHeight(0);
        apBarreClassique.setVisible(false);

        lblBarreClassique.setOnMouseClicked((MouseEvent me) -> {
            if (apBarreClassique.isVisible()) {
                apBarreClassique.setPrefHeight(0);
                apBarreClassique.setMaxHeight(0);
                apBarreClassique.setMinHeight(0);
                apBarreClassique.setVisible(false);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarreClassique.setPrefHeight(tailleInitiale);
                apBarreClassique.setMaxHeight(tailleInitiale);
                apBarreClassique.setMinHeight(tailleInitiale);
                apBarreClassique.setVisible(true);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusBarreClassique.setOnMouseClicked((MouseEvent me) -> {
            if (apBarreClassique.isVisible()) {
                apBarreClassique.setPrefHeight(0);
                apBarreClassique.setMaxHeight(0);
                apBarreClassique.setMinHeight(0);
                apBarreClassique.setVisible(false);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarreClassique.setPrefHeight(tailleInitiale);
                apBarreClassique.setMaxHeight(tailleInitiale);
                apBarreClassique.setMinHeight(tailleInitiale);
                apBarreClassique.setVisible(true);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        /*        
         * ***************************************
         *     Panel Barre Navigation Personnalisee 
         * ***************************************
         */
        AnchorPane apBarrePersonnalisee = new AnchorPane();
        apBarrePersonnalisee.setLayoutY(40);
        apBarrePersonnalisee.setPrefHeight(680);
        apBarrePersonnalisee.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbBarrePersonnaliseeVisible = new CheckBox(rbLocalisation.getString("interface.barrePersonnaliseeVisible"));
        cbBarrePersonnaliseeVisible.setLayoutX(20);
        cbBarrePersonnaliseeVisible.setLayoutY(10);
        cbBarrePersonnaliseeVisible.setSelected(false);
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
        Pane pImageBarrePersonnalisee = new Pane(ivBarrePersonnalisee);
        pImageBarrePersonnalisee.setLayoutX(110);
        pImageBarrePersonnalisee.setLayoutY(70);
        pImageBarrePersonnalisee.setPrefHeight(150);
        pImageBarrePersonnalisee.setPrefWidth(150);
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

        int pos1X = 250;
        int pos1Y = 240;

        rbTopLeftBarrePersonnalisee.setLayoutX(pos1X);
        rbTopCenterBarrePersonnalisee.setLayoutX(pos1X + 20);
        rbTopRightBarrePersonnalisee.setLayoutX(pos1X + 40);
        rbTopLeftBarrePersonnalisee.setLayoutY(pos1Y);
        rbTopCenterBarrePersonnalisee.setLayoutY(pos1Y);
        rbTopRightBarrePersonnalisee.setLayoutY(pos1Y);

        rbMiddleLeftBarrePersonnalisee.setLayoutX(pos1X);
        rbMiddleRightBarrePersonnalisee.setLayoutX(pos1X + 40);
        rbMiddleLeftBarrePersonnalisee.setLayoutY(pos1Y + 20);
        rbMiddleRightBarrePersonnalisee.setLayoutY(pos1Y + 20);

        rbBottomLeftBarrePersonnalisee.setLayoutX(pos1X);
        rbBottomCenterBarrePersonnalisee.setLayoutX(pos1X + 20);
        rbBottomRightBarrePersonnalisee.setLayoutX(pos1X + 40);
        rbBottomLeftBarrePersonnalisee.setLayoutY(pos1Y + 40);
        rbBottomCenterBarrePersonnalisee.setLayoutY(pos1Y + 40);
        rbBottomRightBarrePersonnalisee.setLayoutY(pos1Y + 40);
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
        bdfOffsetXBarrePersonnalisee = new BigDecimalField(new BigDecimal(offsetXBarrePersonnalisee));
        bdfOffsetXBarrePersonnalisee.setLayoutX(50);
        bdfOffsetXBarrePersonnalisee.setLayoutY(310);
        bdfOffsetXBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarrePersonnalisee.setMaxWidth(100);
        bdfOffsetYBarrePersonnalisee = new BigDecimalField(new BigDecimal(offsetYBarrePersonnalisee));
        bdfOffsetYBarrePersonnalisee.setLayoutX(200);
        bdfOffsetYBarrePersonnalisee.setLayoutY(310);
        bdfOffsetYBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetYBarrePersonnalisee.setMaxWidth(100);
        Label lblTailleBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTaille"));
        lblTailleBarrePersonnalisee.setLayoutX(20);
        lblTailleBarrePersonnalisee.setLayoutY(350);
        sltailleBarrePersonnalisee = new Slider(10, 200, tailleBarrePersonnalisee);
        sltailleBarrePersonnalisee.setLayoutX(150);
        sltailleBarrePersonnalisee.setLayoutY(350);

        Label lblTailleBarrePersonnaliseeIcones = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTailleIcones"));
        lblTailleBarrePersonnaliseeIcones.setLayoutX(20);
        lblTailleBarrePersonnaliseeIcones.setLayoutY(380);
        sltailleIconesBarrePersonnalisee = new Slider(10, 60, tailleIconesBarrePersonnalisee);
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
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barreBoutonsPersonalisee"));
        lblBarrePersonnalisee.setPrefWidth(vbOutils.getPrefWidth());
        lblBarrePersonnalisee.setStyle("-fx-background-color : #666");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setPadding(new Insets(5));
        lblBarrePersonnalisee.setLayoutX(10);
        lblBarrePersonnalisee.setLayoutY(10);
        ImageView ivBtnPlusBarrePersonnalisee = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBarrePersonnalisee.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBarrePersonnalisee.setLayoutY(11);
        apBarrePersonnalisee.getChildren().addAll(
                cbBarrePersonnaliseeVisible, btnEditerBarre,
                lblLienBarrePersonnalisee, tfLienImageBarrePersonnalisee, btnLienBarrePersonnalisee,
                pImageBarrePersonnalisee,
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

        double tailleInitiale1 = apBarrePersonnalisee.getPrefHeight();
        apBarrePersonnalisee.setPrefHeight(0);
        apBarrePersonnalisee.setMaxHeight(0);
        apBarrePersonnalisee.setMinHeight(0);
        apBarrePersonnalisee.setVisible(false);

        lblBarrePersonnalisee.setOnMouseClicked((MouseEvent me) -> {
            if (apBarrePersonnalisee.isVisible()) {
                apBarrePersonnalisee.setPrefHeight(0);
                apBarrePersonnalisee.setMaxHeight(0);
                apBarrePersonnalisee.setMinHeight(0);
                apBarrePersonnalisee.setVisible(false);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarrePersonnalisee.setPrefHeight(tailleInitiale1);
                apBarrePersonnalisee.setMaxHeight(tailleInitiale1);
                apBarrePersonnalisee.setMinHeight(tailleInitiale1);
                apBarrePersonnalisee.setVisible(true);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusBarrePersonnalisee.setOnMouseClicked((MouseEvent me) -> {
            if (apBarrePersonnalisee.isVisible()) {
                apBarrePersonnalisee.setPrefHeight(0);
                apBarrePersonnalisee.setMaxHeight(0);
                apBarrePersonnalisee.setMinHeight(0);
                apBarrePersonnalisee.setVisible(false);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarrePersonnalisee.setPrefHeight(tailleInitiale1);
                apBarrePersonnalisee.setMaxHeight(tailleInitiale1);
                apBarrePersonnalisee.setMinHeight(tailleInitiale1);
                apBarrePersonnalisee.setVisible(true);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        /*
         * ********************************************
         *      Panel Boussole 
         * ********************************************
         */
        AnchorPane apBoussole = new AnchorPane();

        apBoussole.setLayoutY(40);
        apBoussole.setPrefHeight(340);
        apBoussole.setMinWidth(vbOutils.getPrefWidth() - 20);
        Double tailleBouss = apBoussole.getPrefHeight();
        cbAfficheBoussole = new CheckBox(rbLocalisation.getString("interface.affichageBoussole"));
        cbAfficheBoussole.setLayoutX(10);
        cbAfficheBoussole.setLayoutY(10);
        apBoussole.getChildren().add(cbAfficheBoussole);
        Label lblPanelBoussole = new Label(rbLocalisation.getString("interface.boussole"));
        lblPanelBoussole.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelBoussole.setStyle("-fx-background-color : #666");
        lblPanelBoussole.setTextFill(Color.WHITE);
        lblPanelBoussole.setPadding(new Insets(5));
        lblPanelBoussole.setLayoutX(10);
        lblPanelBoussole.setLayoutY(10);
        ImageView ivBtnPlusBouss = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBouss.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBouss.setLayoutY(11);
        Label lblChoixBoussole = new Label(rbLocalisation.getString("interface.choixImgBoussole"));
        lblChoixBoussole.setLayoutX(10);
        lblChoixBoussole.setLayoutY(40);
        apBoussole.getChildren().add(lblChoixBoussole);

        int nombreBoussoles = listeBoussoles.size();
        ImageView[] ivBoussoles = new ImageView[nombreBoussoles];
        i = 0;
        for (String nomImage : listeBoussoles) {
            Pane fond = new Pane();
            ivBoussoles[i] = new ImageView(new Image("file:" + repertBoussoles + File.separator + nomImage, -1, 60, true, true, true));
            int col = i % 3;
            int row = i / 3;
            xHS = col * 70 + 95;
            yHS = row * 70 + 60;
            fond.setLayoutX(xHS);
            fond.setLayoutY(yHS);
            fond.setPrefHeight(60);
            fond.setPrefWidth(60);
            fond.setOpacity(1);
            fond.setUserData(nomImage);
            fond.setStyle("-fx-background-color : rgba(255,255,255,0)");
            fond.setOnMouseClicked((MouseEvent me) -> {
                strImageBoussole = (String) ((Pane) me.getSource()).getUserData();
                afficheBoussole();
            });
            fond.getChildren().add(ivBoussoles[i]);

            apBoussole.getChildren().add(fond);
            i++;

        }
        Label lblPositBoussole = new Label(rbLocalisation.getString("interface.choixPositBoussole"));
        lblPositBoussole.setLayoutX(10);
        lblPositBoussole.setLayoutY(160);
        apBoussole.getChildren().add(lblPositBoussole);

        rbBoussTopLeft = new RadioButton();
        rbBoussTopRight = new RadioButton();
        rbBoussBottomLeft = new RadioButton();
        rbBoussBottomRight = new RadioButton();

        rbBoussTopLeft.setUserData("top:left");
        rbBoussTopRight.setUserData("top:right");
        rbBoussBottomLeft.setUserData("bottom:left");
        rbBoussBottomRight.setUserData("bottom:right");

        rbBoussTopLeft.setToggleGroup(grpPosBouss);
        rbBoussTopRight.setToggleGroup(grpPosBouss);
        rbBoussBottomLeft.setToggleGroup(grpPosBouss);
        rbBoussBottomRight.setToggleGroup(grpPosBouss);

        posX = 200;
        posY = 160;

        rbBoussTopLeft.setLayoutX(posX);
        rbBoussTopRight.setLayoutX(posX + 20);
        rbBoussTopLeft.setLayoutY(posY);
        rbBoussTopRight.setLayoutY(posY);

        rbBoussBottomLeft.setLayoutX(posX);
        rbBoussBottomRight.setLayoutX(posX + 20);
        rbBoussBottomLeft.setLayoutY(posY + 20);
        rbBoussBottomRight.setLayoutY(posY + 20);
        apBoussole.getChildren().addAll(
                rbBoussTopLeft, rbBoussTopRight,
                rbBoussBottomLeft, rbBoussBottomRight
        );
        Label lblBoussDXSpinner = new Label("dX :");
        lblBoussDXSpinner.setLayoutX(25);
        lblBoussDXSpinner.setLayoutY(210);
        Label lblBoussDYSpinner = new Label("dY :");
        lblBoussDYSpinner.setLayoutX(175);
        lblBoussDYSpinner.setLayoutY(210);
        bdfOffsetXBoussole = new BigDecimalField(new BigDecimal(offsetXBarreClassique));
        bdfOffsetXBoussole.setLayoutX(50);
        bdfOffsetXBoussole.setLayoutY(205);
        bdfOffsetXBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetXBoussole.setNumber(new BigDecimal(20));
        bdfOffsetXBoussole.setMaxWidth(100);
        bdfOffsetYBoussole = new BigDecimalField(new BigDecimal(offsetYBarreClassique));
        bdfOffsetYBoussole.setLayoutX(200);
        bdfOffsetYBoussole.setLayoutY(205);
        bdfOffsetYBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetYBoussole.setNumber(new BigDecimal(20));
        bdfOffsetYBoussole.setMaxWidth(100);
        apBoussole.getChildren().addAll(lblBoussDXSpinner, bdfOffsetXBoussole,
                lblBoussDYSpinner, bdfOffsetYBoussole
        );
        Label lblTailleBouss = new Label(rbLocalisation.getString("interface.tailleBoussole"));
        lblTailleBouss.setLayoutX(10);
        lblTailleBouss.setLayoutY(245);
        slTailleBoussole = new Slider(50, 200, 100);
        slTailleBoussole.setLayoutX(200);
        slTailleBoussole.setLayoutY(245);
        Label lblOpaciteBouss = new Label(rbLocalisation.getString("interface.opaciteBoussole"));
        lblOpaciteBouss.setLayoutX(10);
        lblOpaciteBouss.setLayoutY(275);
        slOpaciteBoussole = new Slider(0, 1.0, 0.8);
        slOpaciteBoussole.setLayoutX(200);
        slOpaciteBoussole.setLayoutY(275);
        cbAiguilleMobile = new CheckBox(rbLocalisation.getString("interface.aiguilleMobile"));
        cbAiguilleMobile.setLayoutX(10);
        cbAiguilleMobile.setLayoutY(305);
        cbAiguilleMobile.setSelected(true);

        apBoussole.getChildren().addAll(
                lblTailleBouss, slTailleBoussole,
                lblOpaciteBouss, slOpaciteBoussole,
                cbAiguilleMobile
        );
        apBoussole.setPrefHeight(0);
        apBoussole.setMaxHeight(0);
        apBoussole.setMinHeight(0);
        apBoussole.setVisible(false);

        lblPanelBoussole.setOnMouseClicked((MouseEvent me) -> {
            if (apBoussole.isVisible()) {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apBoussole.setPrefHeight(0);
                apBoussole.setMaxHeight(0);
                apBoussole.setMinHeight(0);
                apBoussole.setVisible(false);
            } else {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apBoussole.setPrefHeight(tailleBouss);
                apBoussole.setMaxHeight(tailleBouss);
                apBoussole.setMinHeight(tailleBouss);
                apBoussole.setVisible(true);
            }
        });
        ivBtnPlusBouss.setOnMouseClicked((MouseEvent me) -> {
            if (apBoussole.isVisible()) {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apBoussole.setPrefHeight(0);
                apBoussole.setMaxHeight(0);
                apBoussole.setMinHeight(0);
                apBoussole.setVisible(false);
            } else {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apBoussole.setPrefHeight(tailleBouss);
                apBoussole.setMaxHeight(tailleBouss);
                apBoussole.setMinHeight(tailleBouss);
                apBoussole.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Masque 
         * ********************************************
         */
        AnchorPane apMasque = new AnchorPane();

        apMasque.setLayoutY(40);
        apMasque.setPrefHeight(430);
        apMasque.setMinWidth(vbOutils.getPrefWidth() - 20);
        Double taillePanelMasque = apMasque.getPrefHeight();
        cbAfficheMasque = new CheckBox(rbLocalisation.getString("interface.affichageMasque"));
        cbAfficheMasque.setLayoutX(10);
        cbAfficheMasque.setLayoutY(10);
        apMasque.getChildren().add(cbAfficheMasque);
        Label lblPanelMasque = new Label(rbLocalisation.getString("interface.masque"));
        lblPanelMasque.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelMasque.setStyle("-fx-background-color : #666");
        lblPanelMasque.setTextFill(Color.WHITE);
        lblPanelMasque.setPadding(new Insets(5));
        lblPanelMasque.setLayoutX(10);
        lblPanelMasque.setLayoutY(10);
        ImageView ivBtnPlusMasque = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusMasque.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusMasque.setLayoutY(11);
        Label lblChoixMasque = new Label(rbLocalisation.getString("interface.choixImgMasque"));
        lblChoixMasque.setLayoutX(10);
        lblChoixMasque.setLayoutY(40);
        apMasque.getChildren().add(lblChoixMasque);

        int nombreMasques = listeMasques.size();
        ImageView[] ivMasques = new ImageView[nombreMasques];
        i = 0;
        for (String nomImage : listeMasques) {
            ivMasques[i] = new ImageView(new Image("file:" + repertMasques + File.separator + nomImage, -1, 30, true, true, true));
            int col = i % 4;
            int row = i / 4;
            xHS = col * 35 + 15;
            yHS = row * 35 + 60;
            ivMasques[i].setLayoutX(xHS);
            ivMasques[i].setLayoutY(yHS);
            ivMasques[i].setUserData(nomImage);
            ivMasques[i].setStyle("-fx-background-color : #ccc");
            ivMasques[i].setOnMouseClicked((MouseEvent me) -> {
                imageMasque = (String) ((ImageView) me.getSource()).getUserData();
                apVisualisation.getChildren().remove(ivMasque);
                chargeBarre(styleBarreClassique, strStyleHotSpots, imageMasque);
                afficheMasque();
            });
            apMasque.getChildren().add(ivMasques[i]);
            i++;

        }
        cpCouleurMasques = new ColorPicker(couleurMasque);
        Label lblCouleurMasque = new Label(rbLocalisation.getString("interface.couleurMasque"));
        lblCouleurMasque.setLayoutX(170);
        lblCouleurMasque.setLayoutY(40);
        cpCouleurMasques.setLayoutX(180);
        cpCouleurMasques.setLayoutY(60);
        apMasque.getChildren().addAll(lblCouleurMasque, cpCouleurMasques);

        Label lblPositMasque = new Label(rbLocalisation.getString("interface.choixPositMasque"));
        lblPositMasque.setLayoutX(10);
        int basImages = ((i - 1) / 4 + 1) * 35;
        lblPositMasque.setLayoutY(70 + basImages);
        apMasque.getChildren().add(lblPositMasque);

        rbMasqueTopLeft = new RadioButton();
        rbMasqueTopRight = new RadioButton();
        rbMasqueBottomLeft = new RadioButton();
        rbMasqueBottomRight = new RadioButton();

        rbMasqueTopLeft.setUserData("top:left");
        rbMasqueTopRight.setUserData("top:right");
        rbMasqueBottomLeft.setUserData("bottom:left");
        rbMasqueBottomRight.setUserData("bottom:right");

        rbMasqueTopLeft.setToggleGroup(grpPosMasque);
        rbMasqueTopRight.setToggleGroup(grpPosMasque);
        rbMasqueBottomLeft.setToggleGroup(grpPosMasque);
        rbMasqueBottomRight.setToggleGroup(grpPosMasque);

        posX = 200;
        posY = 70 + basImages;

        rbMasqueTopLeft.setLayoutX(posX);
        rbMasqueTopRight.setLayoutX(posX + 20);
        rbMasqueTopLeft.setLayoutY(posY);
        rbMasqueTopRight.setLayoutY(posY);

        rbMasqueBottomLeft.setLayoutX(posX);
        rbMasqueBottomRight.setLayoutX(posX + 20);
        rbMasqueBottomLeft.setLayoutY(posY + 20);
        rbMasqueBottomRight.setLayoutY(posY + 20);
        apMasque.getChildren().addAll(
                rbMasqueTopLeft, rbMasqueTopRight,
                rbMasqueBottomLeft, rbMasqueBottomRight
        );
        Label lblMasqueDXSpinner = new Label("dX :");
        lblMasqueDXSpinner.setLayoutX(25);
        lblMasqueDXSpinner.setLayoutY(128 + basImages);
        Label lblMasqueDYSpinner = new Label("dY :");
        lblMasqueDYSpinner.setLayoutX(175);
        lblMasqueDYSpinner.setLayoutY(128 + basImages);
        masqueDXSpinner = new BigDecimalField(new BigDecimal(offsetXBarreClassique));
        masqueDXSpinner.setLayoutX(50);
        masqueDXSpinner.setLayoutY(123 + basImages);
        masqueDXSpinner.setMaxValue(new BigDecimal(2000));
        masqueDXSpinner.setMinValue(new BigDecimal(0));
        masqueDXSpinner.setNumber(new BigDecimal(20));
        masqueDXSpinner.setMaxWidth(100);
        masqueDYSpinner = new BigDecimalField(new BigDecimal(offsetYBarreClassique));
        masqueDYSpinner.setLayoutX(200);
        masqueDYSpinner.setLayoutY(123 + basImages);
        masqueDYSpinner.setMaxValue(new BigDecimal(2000));
        masqueDYSpinner.setMinValue(new BigDecimal(0));
        masqueDYSpinner.setNumber(new BigDecimal(20));
        masqueDYSpinner.setMaxWidth(100);
        apMasque.getChildren().addAll(
                lblMasqueDXSpinner, masqueDXSpinner,
                lblMasqueDYSpinner, masqueDYSpinner
        );
        Label lblTailleMasque = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleMasque.setLayoutX(10);
        lblTailleMasque.setLayoutY(160 + basImages);
        slTailleMasque = new Slider(15, 60, 30);
        slTailleMasque.setLayoutX(200);
        slTailleMasque.setLayoutY(160 + basImages);
        Label lblOpaciteMasque = new Label(rbLocalisation.getString("interface.opaciteMasque"));
        lblOpaciteMasque.setLayoutX(10);
        lblOpaciteMasque.setLayoutY(190 + basImages);
        slOpaciteMasque = new Slider(0, 1.0, 0.8);
        slOpaciteMasque.setLayoutX(200);
        slOpaciteMasque.setLayoutY(190 + basImages);
        cbMasqueNavigation = new CheckBox(rbLocalisation.getString("interface.masqueNavigation"));
        cbMasqueNavigation.setLayoutX(60);
        cbMasqueNavigation.setLayoutY(220 + basImages);
        cbMasqueNavigation.setSelected(true);
        cbMasqueBoussole = new CheckBox(rbLocalisation.getString("interface.masqueBoussole"));
        cbMasqueBoussole.setLayoutX(60);
        cbMasqueBoussole.setLayoutY(250 + basImages);
        cbMasqueBoussole.setSelected(true);
        cbMasqueTitre = new CheckBox(rbLocalisation.getString("interface.masqueTitre"));
        cbMasqueTitre.setLayoutX(60);
        cbMasqueTitre.setLayoutY(280 + basImages);
        cbMasqueTitre.setSelected(true);
        cbMasquePlan = new CheckBox(rbLocalisation.getString("interface.masquePlan"));
        cbMasquePlan.setLayoutX(60);
        cbMasquePlan.setLayoutY(310 + basImages);
        cbMasquePlan.setSelected(true);
        cbMasqueReseaux = new CheckBox(rbLocalisation.getString("interface.masqueReseaux"));
        cbMasqueReseaux.setLayoutX(60);
        cbMasqueReseaux.setLayoutY(340 + basImages);
        cbMasqueReseaux.setSelected(true);
        cbMasqueReseaux.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueReseaux = new_val;
            }
        });
        cbMasqueVignettes = new CheckBox(rbLocalisation.getString("interface.masqueVignettes"));
        cbMasqueVignettes.setLayoutX(60);
        cbMasqueVignettes.setLayoutY(370 + basImages);
        cbMasqueVignettes.setSelected(true);

        apMasque.getChildren().addAll(
                lblTailleMasque, slTailleMasque,
                lblOpaciteMasque, slOpaciteMasque,
                cbMasqueNavigation, cbMasqueBoussole, cbMasqueTitre, cbMasquePlan, cbMasqueReseaux, cbMasqueVignettes
        );
        apMasque.setPrefHeight(0);
        apMasque.setMaxHeight(0);
        apMasque.setMinHeight(0);
        apMasque.setVisible(false);

        lblPanelMasque.setOnMouseClicked((MouseEvent me) -> {
            if (apMasque.isVisible()) {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMasque.setPrefHeight(0);
                apMasque.setMaxHeight(0);
                apMasque.setMinHeight(0);
                apMasque.setVisible(false);
            } else {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMasque.setPrefHeight(taillePanelMasque);
                apMasque.setMaxHeight(taillePanelMasque);
                apMasque.setMinHeight(taillePanelMasque);
                apMasque.setVisible(true);
            }
        });
        ivBtnPlusMasque.setOnMouseClicked((MouseEvent me) -> {
            if (apMasque.isVisible()) {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMasque.setPrefHeight(0);
                apMasque.setMaxHeight(0);
                apMasque.setMinHeight(0);
                apMasque.setVisible(false);
            } else {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMasque.setPrefHeight(taillePanelMasque);
                apMasque.setMaxHeight(taillePanelMasque);
                apMasque.setMinHeight(taillePanelMasque);
                apMasque.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel ReseauxSociaux 
         * ********************************************
         */
        AnchorPane apReseauxSociaux = new AnchorPane();

        apReseauxSociaux.setLayoutY(40);
        apReseauxSociaux.setPrefHeight(310);
        apReseauxSociaux.setMinWidth(vbOutils.getPrefWidth() - 20);
        Double taillePanelReseauxSociaux = apReseauxSociaux.getPrefHeight();
        cbAfficheReseauxSociaux = new CheckBox(rbLocalisation.getString("interface.affichageReseauxSociaux"));
        cbAfficheReseauxSociaux.setLayoutX(10);
        cbAfficheReseauxSociaux.setLayoutY(10);
        apReseauxSociaux.getChildren().add(cbAfficheReseauxSociaux);
        Label lblPanelReseauxSociaux = new Label(rbLocalisation.getString("interface.reseauxSociaux"));
        lblPanelReseauxSociaux.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelReseauxSociaux.setStyle("-fx-background-color : #666");
        lblPanelReseauxSociaux.setTextFill(Color.WHITE);
        lblPanelReseauxSociaux.setPadding(new Insets(5));
        lblPanelReseauxSociaux.setLayoutX(10);
        lblPanelReseauxSociaux.setLayoutY(10);
        ImageView ivBtnPlusReseauxSociaux = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusReseauxSociaux.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusReseauxSociaux.setLayoutY(11);
        Label lblPositReseauxSociaux = new Label(rbLocalisation.getString("interface.choixPositReseauxSociaux"));
        lblPositReseauxSociaux.setLayoutX(10);
        basImages = -30;
        lblPositReseauxSociaux.setLayoutY(70 + basImages);
        apReseauxSociaux.getChildren().add(lblPositReseauxSociaux);

        rbReseauxSociauxTopLeft = new RadioButton();
        rbReseauxSociauxTopRight = new RadioButton();
        rbReseauxSociauxBottomLeft = new RadioButton();
        rbReseauxSociauxBottomRight = new RadioButton();

        rbReseauxSociauxTopLeft.setUserData("top:left");
        rbReseauxSociauxTopRight.setUserData("top:right");
        rbReseauxSociauxBottomLeft.setUserData("bottom:left");
        rbReseauxSociauxBottomRight.setUserData("bottom:right");

        rbReseauxSociauxTopLeft.setToggleGroup(grpPosReseauxSociaux);
        rbReseauxSociauxTopRight.setToggleGroup(grpPosReseauxSociaux);
        rbReseauxSociauxBottomLeft.setToggleGroup(grpPosReseauxSociaux);
        rbReseauxSociauxBottomRight.setToggleGroup(grpPosReseauxSociaux);

        posX = 200;
        posY = 70 + basImages;

        rbReseauxSociauxTopLeft.setLayoutX(posX);
        rbReseauxSociauxTopRight.setLayoutX(posX + 20);
        rbReseauxSociauxTopLeft.setLayoutY(posY);
        rbReseauxSociauxTopRight.setLayoutY(posY);

        rbReseauxSociauxBottomLeft.setLayoutX(posX);
        rbReseauxSociauxBottomRight.setLayoutX(posX + 20);
        rbReseauxSociauxBottomLeft.setLayoutY(posY + 20);
        rbReseauxSociauxBottomRight.setLayoutY(posY + 20);
        apReseauxSociaux.getChildren().addAll(
                rbReseauxSociauxTopLeft, rbReseauxSociauxTopRight,
                rbReseauxSociauxBottomLeft, rbReseauxSociauxBottomRight
        );
        Label lblReseauxSociauxDXSpinner = new Label("dX :");
        lblReseauxSociauxDXSpinner.setLayoutX(25);
        lblReseauxSociauxDXSpinner.setLayoutY(128 + basImages);
        Label lblReseauxSociauxDYSpinner = new Label("dY :");
        lblReseauxSociauxDYSpinner.setLayoutX(175);
        lblReseauxSociauxDYSpinner.setLayoutY(128 + basImages);
        reseauxSociauxDXSpinner = new BigDecimalField(new BigDecimal(offsetXBarreClassique));
        reseauxSociauxDXSpinner.setLayoutX(50);
        reseauxSociauxDXSpinner.setLayoutY(123 + basImages);
        reseauxSociauxDXSpinner.setMaxValue(new BigDecimal(2000));
        reseauxSociauxDXSpinner.setMinValue(new BigDecimal(0));
        reseauxSociauxDXSpinner.setNumber(new BigDecimal(20));
        reseauxSociauxDXSpinner.setMaxWidth(100);
        reseauxSociauxDYSpinner = new BigDecimalField(new BigDecimal(offsetYBarreClassique));
        reseauxSociauxDYSpinner.setLayoutX(200);
        reseauxSociauxDYSpinner.setLayoutY(123 + basImages);
        reseauxSociauxDYSpinner.setMaxValue(new BigDecimal(2000));
        reseauxSociauxDYSpinner.setMinValue(new BigDecimal(0));
        reseauxSociauxDYSpinner.setNumber(new BigDecimal(20));
        reseauxSociauxDYSpinner.setMaxWidth(100);
        apReseauxSociaux.getChildren().addAll(
                lblReseauxSociauxDXSpinner, reseauxSociauxDXSpinner,
                lblReseauxSociauxDYSpinner, reseauxSociauxDYSpinner
        );
        Label lblTailleReseauxSociaux = new Label(rbLocalisation.getString("interface.tailleReseauxSociaux"));
        lblTailleReseauxSociaux.setLayoutX(10);
        lblTailleReseauxSociaux.setLayoutY(160 + basImages);
        slTailleReseauxSociaux = new Slider(15, 60, 30);
        slTailleReseauxSociaux.setLayoutX(200);
        slTailleReseauxSociaux.setLayoutY(160 + basImages);
        Label lblOpaciteReseauxSociaux = new Label(rbLocalisation.getString("interface.opaciteReseauxSociaux"));
        lblOpaciteReseauxSociaux.setLayoutX(10);
        lblOpaciteReseauxSociaux.setLayoutY(190 + basImages);
        slOpaciteReseauxSociaux = new Slider(0, 1.0, 0.8);
        slOpaciteReseauxSociaux.setLayoutX(200);
        slOpaciteReseauxSociaux.setLayoutY(190 + basImages);
        cbReseauxSociauxTwitter = new CheckBox("Twitter");
        cbReseauxSociauxTwitter.setLayoutX(60);
        cbReseauxSociauxTwitter.setLayoutY(220 + basImages);
        cbReseauxSociauxTwitter.setSelected(true);
        cbReseauxSociauxGoogle = new CheckBox("Google+");
        cbReseauxSociauxGoogle.setLayoutX(60);
        cbReseauxSociauxGoogle.setLayoutY(250 + basImages);
        cbReseauxSociauxGoogle.setSelected(true);
        cbReseauxSociauxFacebook = new CheckBox("Facebook");
        cbReseauxSociauxFacebook.setLayoutX(60);
        cbReseauxSociauxFacebook.setLayoutY(280 + basImages);
        cbReseauxSociauxFacebook.setSelected(true);

        cbReseauxSociauxEmail = new CheckBox("Email");
        cbReseauxSociauxEmail.setLayoutX(60);
        cbReseauxSociauxEmail.setLayoutY(310 + basImages);
        cbReseauxSociauxEmail.setSelected(true);

        apReseauxSociaux.getChildren().addAll(
                lblTailleReseauxSociaux, slTailleReseauxSociaux,
                lblOpaciteReseauxSociaux, slOpaciteReseauxSociaux,
                cbReseauxSociauxTwitter, cbReseauxSociauxGoogle, cbReseauxSociauxFacebook, cbReseauxSociauxEmail
        );
        apReseauxSociaux.setPrefHeight(0);
        apReseauxSociaux.setMaxHeight(0);
        apReseauxSociaux.setMinHeight(0);
        apReseauxSociaux.setVisible(false);

        lblPanelReseauxSociaux.setOnMouseClicked((MouseEvent me) -> {
            if (apReseauxSociaux.isVisible()) {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(0);
                apReseauxSociaux.setMaxHeight(0);
                apReseauxSociaux.setMinHeight(0);
                apReseauxSociaux.setVisible(false);
            } else {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setVisible(true);
            }
        });
        ivBtnPlusReseauxSociaux.setOnMouseClicked((MouseEvent me) -> {
            if (apReseauxSociaux.isVisible()) {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(0);
                apReseauxSociaux.setMaxHeight(0);
                apReseauxSociaux.setMinHeight(0);
                apReseauxSociaux.setVisible(false);
            } else {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Vignettes 
         * ********************************************
         */
        apVignettes = new AnchorPane();

        apVignettes.setLayoutY(40);
        apVignettes.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheVignettes = new CheckBox(rbLocalisation.getString("interface.affichageVignettes"));
        cbAfficheVignettes.setLayoutX(10);
        cbAfficheVignettes.setLayoutY(10);
        apVignettes.getChildren().add(cbAfficheVignettes);
        Label lblPanelVignettes = new Label(rbLocalisation.getString("interface.vignettes"));
        lblPanelVignettes.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelVignettes.setStyle("-fx-background-color : #666");
        lblPanelVignettes.setTextFill(Color.WHITE);
        lblPanelVignettes.setPadding(new Insets(5));
        lblPanelVignettes.setLayoutX(10);
        lblPanelVignettes.setLayoutY(10);
        ImageView ivBtnPlusVignettes = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusVignettes.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusVignettes.setLayoutY(11);

        Label lblChoixCouleurFondVignettes = new Label(rbLocalisation.getString("interface.choixCouleurFondVignettes"));
        lblChoixCouleurFondVignettes.setLayoutX(10);
        lblChoixCouleurFondVignettes.setLayoutY(85);
        cpCouleurFondVignettes = new ColorPicker(Color.valueOf(couleurFondVignettes));
        cpCouleurFondVignettes.setLayoutX(200);
        cpCouleurFondVignettes.setLayoutY(83);
        apVignettes.getChildren().addAll(lblChoixCouleurFondVignettes, cpCouleurFondVignettes);
        Label lblChoixCouleurTexteVignettes = new Label(rbLocalisation.getString("interface.choixCouleurTexteVignettes"));
        lblChoixCouleurTexteVignettes.setLayoutX(10);
        lblChoixCouleurTexteVignettes.setLayoutY(115);
        cpCouleurTexteVignettes = new ColorPicker(Color.valueOf(couleurTexteVignettes));
        cpCouleurTexteVignettes.setLayoutX(200);
        cpCouleurTexteVignettes.setLayoutY(113);
        apVignettes.getChildren().addAll(lblChoixCouleurTexteVignettes, cpCouleurTexteVignettes);
        Label lblPositVignettes = new Label(rbLocalisation.getString("interface.choixPositVignettes"));
        lblPositVignettes.setLayoutX(10);
        basImages = -30;
        lblPositVignettes.setLayoutY(70 + basImages);
        apVignettes.getChildren().add(lblPositVignettes);

        rbVignettesLeft = new RadioButton();
        rbVignettesRight = new RadioButton();
        rbVignettesBottom = new RadioButton();

        rbVignettesLeft.setUserData("left");
        rbVignettesRight.setUserData("right");
        rbVignettesBottom.setUserData("bottom");

        rbVignettesLeft.setToggleGroup(grpPosVignettes);
        rbVignettesRight.setToggleGroup(grpPosVignettes);
        rbVignettesBottom.setToggleGroup(grpPosVignettes);

        posX = 200;
        posY = 70 + basImages;

        rbVignettesLeft.setLayoutX(posX);
        rbVignettesRight.setLayoutX(posX + 40);
        rbVignettesLeft.setLayoutY(posY);
        rbVignettesRight.setLayoutY(posY);

        rbVignettesBottom.setLayoutX(posX + 20);
        rbVignettesBottom.setLayoutY(posY + 20);
        apVignettes.getChildren().addAll(
                rbVignettesLeft, rbVignettesRight,
                rbVignettesBottom
        );
        Label lblTailleVignettes = new Label(rbLocalisation.getString("interface.tailleVignettes"));
        lblTailleVignettes.setLayoutX(10);
        lblTailleVignettes.setLayoutY(175 + basImages);
        slTailleVignettes = new Slider(50, 300, 120);
        slTailleVignettes.setLayoutX(200);
        slTailleVignettes.setLayoutY(175 + basImages);
        Label lblOpaciteVignettes = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
        lblOpaciteVignettes.setLayoutX(10);
        lblOpaciteVignettes.setLayoutY(205 + basImages);
        slOpaciteVignettes = new Slider(0, 1.0, 0.8);
        slOpaciteVignettes.setLayoutX(200);
        slOpaciteVignettes.setLayoutY(205 + basImages);

        apVignettes.getChildren().addAll(
                lblTailleVignettes, slTailleVignettes,
                lblOpaciteVignettes, slOpaciteVignettes
        );
        apVignettes.setPrefHeight(235 + basImages);
        Double taillePanelVignettes = apVignettes.getPrefHeight();
        apVignettes.setPrefHeight(0);
        apVignettes.setMaxHeight(0);
        apVignettes.setMinHeight(0);
        apVignettes.setVisible(false);

        lblPanelVignettes.setOnMouseClicked((MouseEvent me) -> {
            if (apVignettes.isVisible()) {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apVignettes.setPrefHeight(0);
                apVignettes.setMaxHeight(0);
                apVignettes.setMinHeight(0);
                apVignettes.setVisible(false);
            } else {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apVignettes.setPrefHeight(taillePanelVignettes);
                apVignettes.setMaxHeight(taillePanelVignettes);
                apVignettes.setMinHeight(taillePanelVignettes);
                apVignettes.setVisible(true);
            }
        });
        ivBtnPlusVignettes.setOnMouseClicked((MouseEvent me) -> {
            if (apVignettes.isVisible()) {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apVignettes.setPrefHeight(0);
                apVignettes.setMaxHeight(0);
                apVignettes.setMinHeight(0);
                apVignettes.setVisible(false);
            } else {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apVignettes.setPrefHeight(taillePanelVignettes);
                apVignettes.setMaxHeight(taillePanelVignettes);
                apVignettes.setMinHeight(taillePanelVignettes);
                apVignettes.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel ComboMenu 
         * ********************************************
         */
        apComboMenu = new AnchorPane();
        apComboMenu.setLayoutY(40);
        apComboMenu.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheComboMenu = new CheckBox(rbLocalisation.getString("interface.affichageComboMenu"));
        cbAfficheComboMenu.setLayoutX(10);
        cbAfficheComboMenu.setLayoutY(10);
        apComboMenu.getChildren().add(cbAfficheComboMenu);
        cbAfficheComboMenuImages = new CheckBox(rbLocalisation.getString("interface.affichageComboMenuImages"));
        cbAfficheComboMenuImages.setLayoutX(10);
        cbAfficheComboMenuImages.setLayoutY(40);
        cbAfficheComboMenuImages.setSelected(bAfficheComboMenuImages);
        apComboMenu.getChildren().add(cbAfficheComboMenuImages);
        Label lblPanelComboMenu = new Label(rbLocalisation.getString("interface.comboMenu"));
        lblPanelComboMenu.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelComboMenu.setStyle("-fx-background-color : #666");
        lblPanelComboMenu.setTextFill(Color.WHITE);
        lblPanelComboMenu.setPadding(new Insets(5));
        lblPanelComboMenu.setLayoutX(10);
        lblPanelComboMenu.setLayoutY(10);
        ImageView ivBtnPlusComboMenu = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusComboMenu.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusComboMenu.setLayoutY(11);

        Label lblPositComboMenu = new Label(rbLocalisation.getString("interface.choixPositComboMenu"));
        lblPositComboMenu.setLayoutX(10);
        lblPositComboMenu.setLayoutY(90);
        apComboMenu.getChildren().add(lblPositComboMenu);

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

        rbComboMenuTopLeft.setToggleGroup(grpPosComboMenu);
        rbComboMenuTopCenter.setToggleGroup(grpPosComboMenu);
        rbComboMenuTopRight.setToggleGroup(grpPosComboMenu);
        rbComboMenuBottomLeft.setToggleGroup(grpPosComboMenu);
        rbComboMenuBottomCenter.setToggleGroup(grpPosComboMenu);
        rbComboMenuBottomRight.setToggleGroup(grpPosComboMenu);

        posX = 200;
        posY = 70;

        rbComboMenuTopLeft.setLayoutX(posX);
        rbComboMenuTopCenter.setLayoutX(posX + 20);
        rbComboMenuTopRight.setLayoutX(posX + 40);
        rbComboMenuTopLeft.setLayoutY(posY);
        rbComboMenuTopCenter.setLayoutY(posY);
        rbComboMenuTopRight.setLayoutY(posY);

        rbComboMenuBottomLeft.setLayoutX(posX);
        rbComboMenuBottomCenter.setLayoutX(posX + 20);
        rbComboMenuBottomRight.setLayoutX(posX + 40);
        rbComboMenuBottomLeft.setLayoutY(posY + 40);
        rbComboMenuBottomCenter.setLayoutY(posY + 40);
        rbComboMenuBottomRight.setLayoutY(posY + 40);
        apComboMenu.getChildren().addAll(
                rbComboMenuTopLeft, rbComboMenuTopCenter, rbComboMenuTopRight,
                rbComboMenuBottomLeft, rbComboMenuBottomCenter, rbComboMenuBottomRight
        );

        Label lblOffsetXComboMenu = new Label("dX :");
        lblOffsetXComboMenu.setLayoutX(25);
        lblOffsetXComboMenu.setLayoutY(148);
        Label lblOffsetYComboMenu = new Label("dY :");
        lblOffsetYComboMenu.setLayoutX(175);
        lblOffsetYComboMenu.setLayoutY(148);
        bdfOffsetXComboMenu = new BigDecimalField(new BigDecimal(offsetXComboMenu));
        bdfOffsetXComboMenu.setLayoutX(50);
        bdfOffsetXComboMenu.setLayoutY(143);
        bdfOffsetXComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetXComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetXComboMenu.setMaxWidth(100);
        bdfOffsetYComboMenu = new BigDecimalField(new BigDecimal(offsetYComboMenu));
        bdfOffsetYComboMenu.setLayoutX(200);
        bdfOffsetYComboMenu.setLayoutY(143);
        bdfOffsetYComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetYComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetYComboMenu.setMaxWidth(100);
        apComboMenu.getChildren().addAll(
                lblOffsetXComboMenu, bdfOffsetXComboMenu,
                lblOffsetYComboMenu, bdfOffsetYComboMenu
        );

        apComboMenu.setPrefHeight(235);
        Double taillePanelComboMenu = apComboMenu.getPrefHeight();
        apComboMenu.setPrefHeight(0);
        apComboMenu.setMaxHeight(0);
        apComboMenu.setMinHeight(0);
        apComboMenu.setVisible(false);

        lblPanelComboMenu.setOnMouseClicked((MouseEvent me) -> {
            if (apComboMenu.isVisible()) {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(0);
                apComboMenu.setMaxHeight(0);
                apComboMenu.setMinHeight(0);
                apComboMenu.setVisible(false);
            } else {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(taillePanelComboMenu);
                apComboMenu.setMaxHeight(taillePanelComboMenu);
                apComboMenu.setMinHeight(taillePanelComboMenu);
                apComboMenu.setVisible(true);
            }
        });
        ivBtnPlusComboMenu.setOnMouseClicked((MouseEvent me) -> {
            if (apComboMenu.isVisible()) {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(0);
                apComboMenu.setMaxHeight(0);
                apComboMenu.setMinHeight(0);
                apComboMenu.setVisible(false);
            } else {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(taillePanelComboMenu);
                apComboMenu.setMaxHeight(taillePanelComboMenu);
                apComboMenu.setMinHeight(taillePanelComboMenu);
                apComboMenu.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Plan
         * ********************************************
         */
        apPlan = new AnchorPane();

        apPlan.setLayoutY(40);
        apPlan.setPrefHeight(340);
        apPlan.setMinWidth(vbOutils.getPrefWidth() - 20);
        Double taillePanelPlan = apPlan.getPrefHeight();
        cbAffichePlan = new CheckBox(rbLocalisation.getString("interface.affichagePlan"));
        cbAffichePlan.setLayoutX(10);
        cbAffichePlan.setLayoutY(10);
        apPlan.getChildren().add(cbAffichePlan);
        Label lblPanelPlan = new Label(rbLocalisation.getString("interface.plan"));
        lblPanelPlan.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelPlan.setStyle("-fx-background-color : #666");
        lblPanelPlan.setTextFill(Color.WHITE);
        lblPanelPlan.setPadding(new Insets(5));
        lblPanelPlan.setLayoutX(10);
        lblPanelPlan.setLayoutY(10);
        ImageView ivBtnPlusPlan = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusPlan.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusPlan.setLayoutY(11);
        Label lblLargeurPlan = new Label(rbLocalisation.getString("interface.largeurPlan"));
        lblLargeurPlan.setLayoutX(10);
        lblLargeurPlan.setLayoutY(40);
        slLargeurPlan = new Slider(200, 800, 300);
        slLargeurPlan.setLayoutX(200);
        slLargeurPlan.setLayoutY(40);
        Label lblPositPlan = new Label(rbLocalisation.getString("interface.positionPlan"));
        lblPositPlan.setLayoutX(10);
        lblPositPlan.setLayoutY(70);
        rbPlanLeft = new RadioButton("");
        rbPlanLeft.setLayoutX(200);
        rbPlanLeft.setLayoutY(70);
        rbPlanLeft.setUserData("left");
        rbPlanLeft.setToggleGroup(grpPosPlan);
        rbPlanRight = new RadioButton("");
        rbPlanRight.setLayoutX(230);
        rbPlanRight.setLayoutY(70);
        rbPlanRight.setUserData("right");
        rbPlanRight.setToggleGroup(grpPosPlan);
        Label lblCouleurFondPlan = new Label(rbLocalisation.getString("interface.couleurFondPlan"));
        lblCouleurFondPlan.setLayoutX(10);
        lblCouleurFondPlan.setLayoutY(100);
        cpCouleurFondPlan = new ColorPicker(couleurFondPlan);
        cpCouleurFondPlan.setLayoutX(200);
        cpCouleurFondPlan.setLayoutY(95);
        Label lblCouleurTextePlan = new Label(rbLocalisation.getString("interface.couleurTextePlan"));
        lblCouleurTextePlan.setLayoutX(10);
        lblCouleurTextePlan.setLayoutY(130);
        cpCouleurTextePlan = new ColorPicker(couleurTextePlan);
        cpCouleurTextePlan.setLayoutX(200);
        cpCouleurTextePlan.setLayoutY(125);
        Label lblOpacitePlan = new Label(rbLocalisation.getString("interface.opacitePlan"));
        lblOpacitePlan.setLayoutX(10);
        lblOpacitePlan.setLayoutY(160);
        slOpacitePlan = new Slider(0, 1.0, 0.8);
        slOpacitePlan.setLayoutX(200);
        slOpacitePlan.setLayoutY(160);
        cbAfficheRadar = new CheckBox(rbLocalisation.getString("interface.afficheRadar"));
        cbAfficheRadar.setLayoutX(10);
        cbAfficheRadar.setLayoutY(190);

        Label lblTailleRadar = new Label(rbLocalisation.getString("interface.tailleRadar"));
        lblTailleRadar.setLayoutX(10);
        lblTailleRadar.setLayoutY(220);
        slTailleRadar = new Slider(0, 80, tailleRadar);
        slTailleRadar.setLayoutX(200);
        slTailleRadar.setLayoutY(220);
        Label lblOpaciteRadar = new Label(rbLocalisation.getString("interface.opaciteRadar"));
        lblOpaciteRadar.setLayoutX(10);
        lblOpaciteRadar.setLayoutY(250);
        slOpaciteRadar = new Slider(0, 1, 0.8);
        slOpaciteRadar.setLayoutX(200);
        slOpaciteRadar.setLayoutY(250);
        Label lblCouleurFondRadar = new Label(rbLocalisation.getString("interface.couleurFondRadar"));
        lblCouleurFondRadar.setLayoutX(10);
        lblCouleurFondRadar.setLayoutY(280);
        cpCouleurFondRadar = new ColorPicker(couleurFondRadar);
        cpCouleurFondRadar.setLayoutX(200);
        cpCouleurFondRadar.setLayoutY(280);
        Label lblCouleurLigneRadar = new Label(rbLocalisation.getString("interface.couleurLigneRadar"));
        lblCouleurLigneRadar.setLayoutX(10);
        lblCouleurLigneRadar.setLayoutY(310);
        cpCouleurLigneRadar = new ColorPicker(couleurLigneRadar);
        cpCouleurLigneRadar.setLayoutX(200);
        cpCouleurLigneRadar.setLayoutY(310);
        apPlan.getChildren().addAll(
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

        apPlan.setPrefHeight(0);
        apPlan.setMaxHeight(0);
        apPlan.setMinHeight(0);
        apPlan.setVisible(false);
        lblPanelPlan.setOnMouseClicked((MouseEvent me) -> {
            if (apPlan.isVisible()) {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apPlan.setPrefHeight(0);
                apPlan.setMaxHeight(0);
                apPlan.setMinHeight(0);
                apPlan.setVisible(false);
            } else {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apPlan.setPrefHeight(taillePanelPlan);
                apPlan.setMaxHeight(taillePanelPlan);
                apPlan.setMinHeight(taillePanelPlan);
                apPlan.setVisible(true);
            }
        });
        ivBtnPlusPlan.setOnMouseClicked((MouseEvent me) -> {
            if (apPlan.isVisible()) {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apPlan.setPrefHeight(0);
                apPlan.setMaxHeight(0);
                apPlan.setMinHeight(0);
                apPlan.setVisible(false);
            } else {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apPlan.setPrefHeight(taillePanelPlan);
                apPlan.setMaxHeight(taillePanelPlan);
                apPlan.setMinHeight(taillePanelPlan);
                apPlan.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Image Fond
         * ********************************************
         */
        apImageFond = new AnchorPane();

        apImageFond.setLayoutY(40);
        apImageFond.setPrefHeight(60);
        apImageFond.setMinWidth(vbOutils.getPrefWidth() - 20);
        taillePanelImageFond = apImageFond.getPrefHeight();
        Label lblPanelImageFond = new Label(rbLocalisation.getString("interface.imageFond"));
        lblPanelImageFond.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelImageFond.setStyle("-fx-background-color : #666");
        lblPanelImageFond.setTextFill(Color.WHITE);
        lblPanelImageFond.setPadding(new Insets(5));
        lblPanelImageFond.setLayoutX(10);
        lblPanelImageFond.setLayoutY(10);
        ImageView ivBtnPlusImageFond = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusImageFond.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusImageFond.setLayoutY(11);
        apImageFond.getChildren().addAll();
        Image imgAjoute = new Image("file:" + strRepertAppli + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (MouseEvent me) -> {
                    ajoutImageFond();
                }
        );
        apImageFond.setPrefHeight(0);
        apImageFond.setMaxHeight(0);
        apImageFond.setMinHeight(0);
        apImageFond.setVisible(false);
        lblPanelImageFond.setOnMouseClicked((MouseEvent me) -> {
            if (apImageFond.isVisible()) {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apImageFond.setPrefHeight(0);
                apImageFond.setMaxHeight(0);
                apImageFond.setMinHeight(0);
                apImageFond.setVisible(false);
            } else {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apImageFond.setPrefHeight(taillePanelImageFond);
                apImageFond.setMaxHeight(taillePanelImageFond);
                apImageFond.setMinHeight(taillePanelImageFond);
                apImageFond.setVisible(true);
            }
        });
        ivBtnPlusImageFond.setOnMouseClicked((MouseEvent me) -> {
            if (apImageFond.isVisible()) {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apImageFond.setPrefHeight(0);
                apImageFond.setMaxHeight(0);
                apImageFond.setMinHeight(0);
                apImageFond.setVisible(false);
            } else {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apImageFond.setPrefHeight(taillePanelImageFond);
                apImageFond.setMaxHeight(taillePanelImageFond);
                apImageFond.setMinHeight(taillePanelImageFond);
                apImageFond.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Menu contextuel
         * ********************************************
         */
        apMenuContextuel = new AnchorPane();

        apMenuContextuel.setLayoutY(40);
        apMenuContextuel.setPrefHeight(280);
        apMenuContextuel.setMinWidth(vbOutils.getPrefWidth() - 20);
        Double taillePanelMenuContextuel = apMenuContextuel.getPrefHeight();
        Label lblPanelMenuContextuel = new Label(rbLocalisation.getString("interface.menuContextuel"));
        lblPanelMenuContextuel.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelMenuContextuel.setStyle("-fx-background-color : #666");
        lblPanelMenuContextuel.setTextFill(Color.WHITE);
        lblPanelMenuContextuel.setPadding(new Insets(5));
        lblPanelMenuContextuel.setLayoutX(10);
        lblPanelMenuContextuel.setLayoutY(10);
        ImageView ivBtnPlusMenuContextuel = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusMenuContextuel.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusMenuContextuel.setLayoutY(11);
        cbAfficheMenuContextuel = new CheckBox(rbLocalisation.getString("interface.affichageMenuContextuel"));
        cbAfficheMenuContextuel.setLayoutX(10);
        cbAfficheMenuContextuel.setLayoutY(10);
        cbAfficheMenuContextuel.setSelected(bAfficheMenuContextuel);
        cbAffichePrecSuivMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelSuivPrec"));
        cbAffichePrecSuivMC.setLayoutX(10);
        cbAffichePrecSuivMC.setLayoutY(40);
        cbAffichePrecSuivMC.setSelected(bAffichePrecSuivMC);
        cbAffichePlanetNormalMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelPlaneteNormal"));
        cbAffichePlanetNormalMC.setLayoutX(10);
        cbAffichePlanetNormalMC.setLayoutY(70);
        cbAffichePlanetNormalMC.setSelected(bAffichePlanetNormalMC);
        cbAffichePersMC1 = new CheckBox(rbLocalisation.getString("interface.menuContextuelPers1"));
        cbAffichePersMC1.setLayoutX(10);
        cbAffichePersMC1.setLayoutY(100);
        cbAffichePersMC1.setSelected(bAffichePersMC1);
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
        apMenuContextuel.getChildren().addAll(
                cbAfficheMenuContextuel,
                cbAffichePrecSuivMC,
                cbAffichePlanetNormalMC,
                cbAffichePersMC1,
                lblPersLib1, tfPersLib1,
                lblPersUrl1, tfPersURL1,
                cbAffichePersMC2,
                lblPersLib2, tfPersLib2,
                lblPersUrl2, tfPersURL2
        );

        apMenuContextuel.setPrefHeight(0);
        apMenuContextuel.setMaxHeight(0);
        apMenuContextuel.setMinHeight(0);
        apMenuContextuel.setVisible(false);
        lblPanelMenuContextuel.setOnMouseClicked((MouseEvent me) -> {
            if (apMenuContextuel.isVisible()) {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(0);
                apMenuContextuel.setMaxHeight(0);
                apMenuContextuel.setMinHeight(0);
                apMenuContextuel.setVisible(false);
            } else {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMaxHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMinHeight(taillePanelMenuContextuel);
                apMenuContextuel.setVisible(true);
            }
        });
        ivBtnPlusMenuContextuel.setOnMouseClicked((MouseEvent me) -> {
            if (apMenuContextuel.isVisible()) {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(0);
                apMenuContextuel.setMaxHeight(0);
                apMenuContextuel.setMinHeight(0);
                apMenuContextuel.setVisible(false);
            } else {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMaxHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMinHeight(taillePanelMenuContextuel);
                apMenuContextuel.setVisible(true);
            }
        });

        /*
         * *****************************************************
         * Style des Pannels
         * *****************************************************
         */
//        String styleap = "-fx-background-color : #ccc;";
        String styleap = "";
        apBoussole.setStyle(styleap);
        apBarreClassique.setStyle(styleap);
        apTitre.setStyle(styleap);
        apDiapo.setStyle(styleap);
        apHotSpots.setStyle(styleap);
        apMasque.setStyle(styleap);
        apReseauxSociaux.setStyle(styleap);
        apVignettes.setStyle(styleap);
        apComboMenu.setStyle(styleap);
        apPlan.setStyle(styleap);
        apMenuContextuel.setStyle(styleap);
        apImageFond.setStyle(styleap);
        apBoussole.setLayoutX(20);
        apBarreClassique.setLayoutX(20);
        apTitre.setLayoutX(20);
        apDiapo.setLayoutX(20);
        apHotSpots.setLayoutX(20);
        apMasque.setLayoutX(20);
        apReseauxSociaux.setLayoutX(20);
        apVignettes.setLayoutX(20);
        apComboMenu.setLayoutX(20);
        apPlan.setLayoutX(20);
        apMenuContextuel.setLayoutX(20);
        apImageFond.setLayoutX(20);
        /*
         * *******************************************************
         *     Ajout des Elements dans les Pannels
         * *******************************************************
         */
        apTIT.getChildren().addAll(apTitre, lblPanelTitre, ivBtnPlusTitre);
        apECR.getChildren().addAll(apFenetre, lblPanelFenetre, ivBtnPlusFenetre);
        apDIA.getChildren().addAll(apDiapo, lblDiaporama, ivBtnDiaporama);
        apCLASS.getChildren().addAll(apBarreClassique, lblBarreClassique, ivBtnPlusBarreClassique);
        apPERS.getChildren().addAll(apBarrePersonnalisee, lblBarrePersonnalisee, ivBtnPlusBarrePersonnalisee);
        apHS.getChildren().addAll(lblChoixHS, apHotSpots, ivBtnPlusHS);
        apBOUSS.getChildren().addAll(apBoussole, lblPanelBoussole, ivBtnPlusBouss);
        apMASQ.getChildren().addAll(apMasque, lblPanelMasque, ivBtnPlusMasque);
        apRS.getChildren().addAll(apReseauxSociaux, lblPanelReseauxSociaux, ivBtnPlusReseauxSociaux);
        apVIG.getChildren().addAll(apVignettes, lblPanelVignettes, ivBtnPlusVignettes);
        apCBM.getChildren().addAll(apComboMenu, lblPanelComboMenu, ivBtnPlusComboMenu);
        apPLAN.getChildren().addAll(apPlan, lblPanelPlan, ivBtnPlusPlan);
        apIF.getChildren().addAll(apImageFond, lblPanelImageFond, ivBtnPlusImageFond);
        apMC.getChildren().addAll(apMenuContextuel, lblPanelMenuContextuel, ivBtnPlusMenuContextuel);

        /*
         * ******************************************************
         *     Ajouts des pannels dans la barre d'outils
         * ******************************************************
         */
        vbOutils.getChildren().addAll(
                apCoulTheme,
                apTIT,
                apECR,
                apDIA,
                apCLASS,
                apPERS,
                apPLAN,
                apHS,
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
        grpImage.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpImage.getSelectedToggle() != null) {
                cbImage.setDisable(true);
                Rectangle2D viewportRect;
                switch (grpImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        viewportRect = new Rectangle2D(0, 0, imageClaire.getWidth(), imageClaire.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imageClaire);
                        break;
                    case "sombre":
                        viewportRect = new Rectangle2D(0, 0, imageSombre.getWidth(), imageSombre.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imageSombre);
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

        cbImage.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                int index = cbImage.getSelectionModel().getSelectedIndex();
                if (index != -1) {
                    afficheImage(index);
                }
            }
        });

        /*
         Listeners Couleur Thème
         */
        cpCouleurTheme.setOnAction((ActionEvent e) -> {
            couleurTheme = cpCouleurTheme.getValue();
            String coul1 = cpCouleurTheme.getValue().toString().substring(2, 8);
            couleurHotspots = couleurTheme;
            couleurBarreClassique = couleurTheme;
            couleurMasque = couleurTheme;
            couleurFondPlan = couleurTheme;
            couleurBarrePersonnalisee = couleurTheme;
            txtCouleurFondPlan = couleurFondPlan.toString().substring(2, 8);
            affichePlan();
            changeCouleurTitre(coul1);
            changeCouleurVignettes(coul1);
            //changeCouleur(hue);
            cpCouleurFondTitre.setValue(couleurTheme);
            cpCouleurFondPlan.setValue(couleurTheme);
            cpCouleurHotspots.setValue(couleurTheme);
            cpCouleurBarreClassique.setValue(couleurTheme);
            cpCouleurMasques.setValue(couleurTheme);
            cpCouleurBarrePersonnalisee.setValue(couleurTheme);
            cpCouleurFondVignettes.setValue(Color.hsb(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness()));
            changeCouleurBarreClassique(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurHS(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurHSPhoto(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurMasque(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            afficheBarrePersonnalisee();
        });

        /*
         Listeners HotSpots
         */
        cpCouleurHotspots.setOnAction((ActionEvent e) -> {
            couleurHotspots = cpCouleurHotspots.getValue();
            changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        });
        cpCouleurHotspotsPhoto.setOnAction((ActionEvent e) -> {
            couleurHotspotsPhoto = cpCouleurHotspotsPhoto.getValue();
            changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        });

        /*
         Listeners Titre
         */
        cbListePolices.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                if (new_val != null) {
                    strTitrePoliceNom = new_val;
                    Font fonte1 = Font.font(strTitrePoliceNom, Double.parseDouble(strTitrePoliceTaille));
                    txtTitre.setFont(fonte1);
                    txtTitre.setPrefHeight(-1);
                    afficheVignettes();
                    affichePlan();
                }
            }
        });
        cbAfficheTitre.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAfficheTitre = new_val;
            apVisualisation.getChildren().remove(hbbarreBoutons);
            apVisualisation.getChildren().remove(ivHotSpot);
            apVisualisation.getChildren().remove(ivHotSpotImage);
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            afficheVignettes();
            affichePlan();
        });

        slTaillePolice.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                strTitrePoliceTaille = Integer.toString((int) Math.round(taille));
                Font fonte1 = Font.font(strTitrePoliceNom, Double.parseDouble(strTitrePoliceTaille));
                txtTitre.setFont(fonte1);
                txtTitre.setPrefHeight(-1);
                afficheVignettes();
                affichePlan();
            }
        });
        slOpacite.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                titreOpacite = (double) newValue;
                String coul = cpCouleurFondTitre.getValue().toString().substring(2, 8);
                Color couleur = cpCouleurFondTitre.getValue();
                int rouge = (int) (couleur.getRed() * 255.d);
                int bleu = (int) (couleur.getBlue() * 255.d);
                int vert = (int) (couleur.getGreen() * 255.d);
                String coulFond = "rgba(" + rouge + "," + vert + "," + bleu + "," + titreOpacite + ")";
                txtTitre.setStyle("-fx-background-color : " + coulFond);
            }
        });
        slTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                titreTaille = (double) newValue;
                double taille = (double) titreTaille / 100.d * ivVisualisation.getFitWidth();
                txtTitre.setMinWidth(taille);
                txtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - txtTitre.getMinWidth()) / 2);
            }
        });
        cpCouleurTitre.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurTitre.getValue().toString().substring(2, 8);
            strCouleurTitre = "#" + coul;
            txtTitre.setTextFill(Color.valueOf(strCouleurTitre));
        });
        cpCouleurFondTitre.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurFondTitre.getValue().toString().substring(2, 8);
            Color couleur = cpCouleurFondTitre.getValue();
            strCouleurFondTitre = "#" + coul;
            int rouge = (int) (couleur.getRed() * 255.d);
            int bleu = (int) (couleur.getBlue() * 255.d);
            int vert = (int) (couleur.getGreen() * 255.d);
            String coulFond = "rgba(" + rouge + "," + vert + "," + bleu + "," + titreOpacite + ")";

            txtTitre.setStyle("-fx-background-color : " + coulFond);
        });
        /*
         Listeners Fenêtres
         */
        cbFenetreInfoPersonnalise.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bFenetreInfoPersonnalise = new_val;
            apFenetreInfoPers.setDisable(!bFenetreInfoPersonnalise);
            afficheFenetreInfo();
        });

        cbFenetreAidePersonnalise.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bFenetreAidePersonnalise = new_val;
            apFenetreAidePers.setDisable(!bFenetreAidePersonnalise);
            afficheFenetreAide();
        });

        cbAfficheFenetreInfo.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAfficheFenetreInfo = new_val;
            if (new_val == true) {
                cbAfficheFenetreAide.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreInfo();
        });

        cbAfficheFenetreAide.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAfficheFenetreAide = new_val;
            if (new_val == true) {
                cbAfficheFenetreInfo.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreAide();
        });

        btnFenetreInfo.setOnMouseClicked(
                (MouseEvent me) -> {
                    strFenetreInfoImage = ajoutFenetreImage();
                    if (!strFenetreInfoImage.equals("")) {
                        txtFenetreInfoImage.setText(strFenetreInfoImage);
                    }
                    afficheFenetreInfo();
                }
        );

        btnFenetreAide.setOnMouseClicked(
                (MouseEvent me) -> {
                    strFenetreAideImage = ajoutFenetreImage();
                    if (!strFenetreAideImage.equals("")) {
                        txtFenetreAideImage.setText(strFenetreAideImage);
                    }
                    afficheFenetreAide();
                }
        );
        slFenetreInfoTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                fenetreInfoTaille = taille;
                afficheFenetreInfo();
            }
        });
        slFenetreInfoOpacite.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                fenetreInfoOpacite = opac;
                afficheFenetreInfo();
            }
        });
        bdfFenetreInfoPosX.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreInfoPosX = new_value.doubleValue();
            afficheFenetreInfo();
        });
        bdfFenetreInfoPosY.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreInfoPosY = new_value.doubleValue();
            afficheFenetreInfo();
        });

        txtFenetreURL.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strFenetreURL = newValue;
        });

        txtFenetreTexteURL.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strFenetreTexteURL = newValue;
            afficheFenetreInfo();
        });

        slFenetrePoliceTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = Math.round((double) newValue * 10.d) / 10.d;
                fenetrePoliceTaille = taille;
                afficheFenetreInfo();
            }
        });

        cpFenetreURLCouleur.setOnAction((ActionEvent e) -> {
            strFenetreURLCouleur = "#" + cpFenetreURLCouleur.getValue().toString().substring(2, 8);
            afficheFenetreInfo();
        });

        bdfFenetreURLPosX.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreURLPosX = new_value.doubleValue();
            afficheFenetreInfo();
        });

        bdfFenetreURLPosY.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreURLPosY = new_value.doubleValue();
            afficheFenetreInfo();
        });
        slFenetreAideTaille.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                fenetreAideTaille = taille;
                afficheFenetreAide();
            }
        });
        slFenetreAideOpacite.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                fenetreAideOpacite = opac;
                afficheFenetreAide();
            }
        });

        bdfFenetreAidePosX.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreAidePosX = new_value.doubleValue();
            afficheFenetreAide();
        });
        bdfFenetreAidePosY.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            fenetreAidePosY = new_value.doubleValue();
            afficheFenetreAide();
        });


        /*
         Listeners Diaporama
         */
        slOpaciteDiaporama.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                diaporamaOpacite = (double) newValue;
                afficheDiaporama();
            }
        });

        cpCouleurDiaporama.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurDiaporama.getValue().toString().substring(2, 8);
            strCouleurDiaporama = "#" + coul;
            afficheDiaporama();
        });

        cbVisualiseDiapo.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
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
        slEspacementBarreClassique.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            espacementBarreClassique = (double) newValue;
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });

        cblisteStyleBarreClassique.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    styleBarreClassique = t1;
                    afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                }
            }
        });

        bdfOffsetXBarreClassique.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetXBarreClassique = new_value.doubleValue();
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        bdfOffsetYBarreClassique.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetYBarreClassique = new_value.doubleValue();
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });

        grpPositionBarreClassique.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPositionBarreClassique.getSelectedToggle() != null) {
                strPositionBarreClassique = grpPositionBarreClassique.getSelectedToggle().getUserData().toString();
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            }
        });
        cbBarreClassiqueVisible.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strVisibiliteBarreClassique = "oui";
                cbBarrePersonnaliseeVisible.setSelected(false);
            } else {
                strVisibiliteBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbSuivantPrecedent.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bSuivantPrecedent = new_val;
            fondSuivant.setVisible(bSuivantPrecedent);
            fondPrecedent.setVisible(bSuivantPrecedent);
        });
        cpCouleurBarreClassique.setOnAction((ActionEvent e) -> {
            couleurBarreClassique = cpCouleurBarreClassique.getValue();
            changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        });
        cbDeplacementsBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strDeplacementsBarreClassique = "oui";
            } else {
                strDeplacementsBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbZoomBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strZoomBarreClassique = "oui";
            } else {
                strZoomBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbOutilsBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strOutilsBarreClassique = "oui";
            } else {
                strOutilsBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbSourisBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strSourisBarreClassique = "oui";
            } else {
                strSourisBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbRotationBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strRotationBarreClassique = "oui";
            } else {
                strRotationBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        cbFSBarreClassique.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strPleinEcranBarreClassique = "oui";
            } else {
                strPleinEcranBarreClassique = "non";
            }
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
        });
        /*
         Listeners Barre de navigation Personnalisee
         */

        bdfOffsetXBarrePersonnalisee.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetXBarrePersonnalisee = new_value.doubleValue();
            afficheBarrePersonnalisee();
        });
        bdfOffsetYBarrePersonnalisee.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetYBarrePersonnalisee = new_value.doubleValue();
            afficheBarrePersonnalisee();
        });

        grpCouleurBarrePersonnalisee.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpCouleurBarrePersonnalisee.getSelectedToggle() != null) {
                bCouleurOrigineBarrePersonnalisee = (boolean) grpCouleurBarrePersonnalisee.getSelectedToggle().getUserData();
                cpCouleurBarrePersonnalisee.setDisable(bCouleurOrigineBarrePersonnalisee);
                afficheBarrePersonnalisee();
            }
        });

        grpPositionBarrePersonnalisee.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPositionBarrePersonnalisee.getSelectedToggle() != null) {
                strPositionBarrePersonnalisee = grpPositionBarrePersonnalisee.getSelectedToggle().getUserData().toString();
                afficheBarrePersonnalisee();
            }
        });
        cbBarrePersonnaliseeVisible.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strVisibiliteBarrePersonnalisee = "oui";
                cbBarreClassiqueVisible.setSelected(false);
            } else {
                strVisibiliteBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        cbSuivantPrecedent.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bSuivantPrecedent = new_val;
            fondSuivant.setVisible(bSuivantPrecedent);
            fondPrecedent.setVisible(bSuivantPrecedent);
        });
        cpCouleurBarrePersonnalisee.setOnAction((ActionEvent e) -> {
            couleurBarrePersonnalisee = cpCouleurBarrePersonnalisee.getValue();
            afficheBarrePersonnalisee();
        });
        cbDeplacementsBarrePersonnalisee.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strDeplacementsBarrePersonnalisee = "oui";
            } else {
                strDeplacementsBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        cbZoomBarrePersonnalisee.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strZoomBarrePersonnalisee = "oui";
            } else {
                strZoomBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        cbSourisBarrePersonnalisee.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strSourisBarrePersonnalisee = "oui";
            } else {
                strSourisBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        cbRotationBarrePersonnalisee.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strRotationBarrePersonnalisee = "oui";
            } else {
                strRotationBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        cbFSBarrePersonnalisee.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                strPleinEcranBarrePersonnalisee = "oui";
            } else {
                strPleinEcranBarrePersonnalisee = "non";
            }
            afficheBarrePersonnalisee();
        });
        sltailleBarrePersonnalisee.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleBarrePersonnalisee = taille;
                afficheBarrePersonnalisee();
            }
        });
        sltailleIconesBarrePersonnalisee.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleIconesBarrePersonnalisee = taille;
                afficheBarrePersonnalisee();
            }
        });
        btnLienBarrePersonnalisee.setOnMouseClicked(
                (MouseEvent me) -> {
                    try {
                        choixBarrePersonnalisee();
                    } catch (IOException ex) {
                        Logger.getLogger(GestionnaireInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        btnEditerBarre.setOnMouseClicked(
                (MouseEvent me) -> {
                    EditeurPanovisu.creerEditerBarre(tfLienImageBarrePersonnalisee.getText());
                }
        );
        tfLien1BarrePersonnalisee.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strLien1BarrePersonnalisee = tfLien1BarrePersonnalisee.getText();
        });
        tfLien2BarrePersonnalisee.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strLien2BarrePersonnalisee = tfLien2BarrePersonnalisee.getText();
        });


        /*
         Listeners Boussole
         */
        grpPosBouss.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosBouss.getSelectedToggle() != null) {
                strPositionBoussole = grpPosBouss.getSelectedToggle().getUserData().toString();
                afficheBoussole();
            }
        });
        bdfOffsetXBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetXBoussole = new_value.doubleValue();
            afficheBoussole();
        });
        bdfOffsetYBoussole.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetYBoussole = new_value.doubleValue();
            afficheBoussole();
        });
        slTailleBoussole.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleBoussole = taille;
                afficheBoussole();
            }
        });
        cbAfficheBoussole.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheBoussole = new_val;
                afficheBoussole();
            }
        });
        cbAiguilleMobile.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAiguilleMobileBoussole = new_val;
            }
        });

        /*
         Listeners Bouton de Masquage
         */
        slOpaciteBoussole.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteBoussole = opac;
                afficheBoussole();
            }
        });

        grpPosMasque.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosMasque.getSelectedToggle() != null) {
                positionMasque = grpPosMasque.getSelectedToggle().getUserData().toString();
                apVisualisation.getChildren().remove(ivMasque);
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
        slTailleMasque.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleMasque = taille;
                afficheMasque();
            }
        });
        slOpaciteMasque.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteMasque = opac;
                afficheMasque();
            }
        });
        cbMasqueNavigation.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueNavigation = new_val;
            }
        });
        cpCouleurMasques.setOnAction((ActionEvent e) -> {
            couleurMasque = cpCouleurMasques.getValue();
            changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        });
        cbAfficheMasque.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheMasque = new_val;
                changeCouleurMasque(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                afficheMasque();
            }
        });
        cbMasqueBoussole.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueBoussole = new_val;
            }
        });
        cbMasqueTitre.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueTitre = new_val;
            }
        });
        cbMasquePlan.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasquePlan = new_val;
            }
        });
        cbMasqueVignettes.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bMasqueVignettes = new_val;
            }
        });

        /*
         Listeners Reseaux Sociaux
         */
        reseauxSociauxDXSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dXReseauxSociaux = new_value.doubleValue();
            afficheReseauxSociaux();
        });

        reseauxSociauxDYSpinner.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            dYReseauxSociaux = new_value.doubleValue();
            afficheReseauxSociaux();
        });

        grpPosReseauxSociaux.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosReseauxSociaux.getSelectedToggle() != null) {
                positionReseauxSociaux = grpPosReseauxSociaux.getSelectedToggle().getUserData().toString();
                afficheReseauxSociaux();
            }
        });
        slTailleReseauxSociaux.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleReseauxSociaux = taille;
                afficheReseauxSociaux();
            }
        });
        slOpaciteReseauxSociaux.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteReseauxSociaux = opac;
                afficheReseauxSociaux();
            }
        });
        cbAfficheReseauxSociaux.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheReseauxSociaux = new_val;
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxTwitter.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxTwitter = new_val;
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxGoogle.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxGoogle = new_val;
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxFacebook.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxFacebook = new_val;
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxEmail.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bReseauxSociauxEmail = new_val;
                afficheReseauxSociaux();
            }
        });

        /*
         Listeners Vignettes
         */
        grpPosVignettes.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosVignettes.getSelectedToggle() != null) {
                positionVignettes = grpPosVignettes.getSelectedToggle().getUserData().toString();
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                afficheVignettes();
            }
        });

        slTailleVignettes.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleImageVignettes = taille;
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                afficheVignettes();
            }
        });

        slOpaciteVignettes.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opaciteVignettes = opac;
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                afficheVignettes();
            }
        });

        cbAfficheVignettes.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheVignettes = new_val;
                afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
                afficheVignettes();
            }
        });

        cpCouleurFondVignettes.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurFondVignettes.getValue().toString().substring(2, 8);
            couleurFondVignettes = "#" + coul;
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            afficheVignettes();
        });
        cpCouleurTexteVignettes.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurTexteVignettes.getValue().toString().substring(2, 8);
            couleurTexteVignettes = "#" + coul;
            afficheBarreClassique(strPositionBarreClassique, offsetXBarreClassique, offsetYBarreClassique, tailleBarreClassique, styleBarreClassique, strStyleHotSpots, espacementBarreClassique);
            afficheVignettes();
        });

        /*
         Listeners ComboMenu
         */
        grpPosComboMenu.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosComboMenu.getSelectedToggle() != null) {
                positionXComboMenu = grpPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[1];
                positionYComboMenu = grpPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[0];
                afficheComboMenu();
            }
        });

        cbAfficheComboMenu.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheComboMenu = new_val;
                afficheComboMenu();
            }
        });
        cbAfficheComboMenuImages.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheComboMenuImages = new_val;
                afficheComboMenu();
            }
        });

        bdfOffsetXComboMenu.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetXComboMenu = new_value.doubleValue();
            afficheComboMenu();
        });

        bdfOffsetYComboMenu.numberProperty().addListener((ObservableValue<? extends BigDecimal> ov, BigDecimal old_value, BigDecimal new_value) -> {
            offsetYComboMenu = new_value.doubleValue();
            afficheComboMenu();
        });


        /*
         Listeners Plan
         */
        cbAffichePlan.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAffichePlan = new_val;
                tabPlan.setDisable(!bAffichePlan);
                mniAffichagePlan.setDisable(!bAffichePlan);
                ivAjouterPlan.setDisable(!bAffichePlan);
                mniAjouterPlan.setDisable(!bAffichePlan);
                if (new_val) {
                    ivAjouterPlan.setOpacity(1.0);
                } else {
                    ivAjouterPlan.setOpacity(0.3);
                }
                affichePlan();
            }
        });
        cbAfficheRadar.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val != null) {
                bAfficheRadar = new_val;
                affichePlan();
            }
        });

        grpPosPlan.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpPosPlan.getSelectedToggle() != null) {
                positionPlan = grpPosPlan.getSelectedToggle().getUserData().toString();
                affichePlan();
            }
        });
        slLargeurPlan.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                largeurPlan = taille;
                lblLargeurPlan.setText(rbLocalisation.getString("interface.largeurPlan") + " (" + Math.round(taille) + "px )");
                affichePlan();
            }
        });
        slOpacitePlan.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opac = (double) newValue;
                opacitePlan = opac;
                affichePlan();
            }
        });

        cpCouleurFondPlan.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurFondPlan.getValue().toString().substring(2, 8);
            couleurFondPlan = cpCouleurFondPlan.getValue();
            txtCouleurFondPlan = coul;
            affichePlan();
        });
        cpCouleurTextePlan.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurTextePlan.getValue().toString().substring(2, 8);
            couleurTextePlan = cpCouleurTextePlan.getValue();
            txtCouleurTextePlan = coul;
            affichePlan();
        });
        slTailleRadar.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double taille = (double) newValue;
                tailleRadar = taille;
                affichePlan();
            }
        });
        slOpaciteRadar.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            if (newValue != null) {
                double opacite = (double) newValue;
                opaciteRadar = opacite;
                affichePlan();
            }
        });
        cpCouleurFondRadar.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurFondRadar.getValue().toString().substring(2, 8);
            couleurFondRadar = cpCouleurFondRadar.getValue();
            txtCouleurFondRadar = coul;
            affichePlan();
        });
        cpCouleurLigneRadar.setOnAction((ActionEvent e) -> {
            String coul = cpCouleurLigneRadar.getValue().toString().substring(2, 8);
            couleurLigneRadar = cpCouleurLigneRadar.getValue();
            txtCouleurLigneRadar = coul;
            affichePlan();
        });

        /*
         Listeners Menu Contextuel
         */
        cbAfficheMenuContextuel.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAfficheMenuContextuel = new_val;
        });
        cbAffichePrecSuivMC.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAffichePrecSuivMC = new_val;
        });
        cbAffichePlanetNormalMC.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAffichePlanetNormalMC = new_val;
        });
        cbAffichePersMC1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAffichePersMC1 = new_val;
            if (bAffichePersMC1) {
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
        cbAffichePersMC2.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            bAffichePersMC2 = new_val;
            if (bAffichePersMC2) {
                tfPersLib2.setDisable(false);
                tfPersURL2.setDisable(false);
            } else {
                tfPersLib2.setDisable(true);
                tfPersURL2.setDisable(true);
            }
        });
        tfPersLib1.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strPersLib1 = tfPersLib1.getText();
        });
        tfPersLib2.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strPersLib2 = tfPersLib2.getText();
        });
        tfPersURL1.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strPersURL1 = tfPersURL1.getText();
        });
        tfPersURL2.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            strPersURL2 = tfPersURL2.getText();
        });

    }

}
