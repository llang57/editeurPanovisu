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
import static editeurpanovisu.EditeurPanovisu.getiDecalageMac;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiquesFichier;
import static editeurpanovisu.EditeurPanovisu.getiNombrePlans;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import static editeurpanovisu.EditeurPanovisu.hbBarreBouton;
import static editeurpanovisu.EditeurPanovisu.isbAutoTourDemarre;
import static editeurpanovisu.EditeurPanovisu.isbInternet;
import static editeurpanovisu.EditeurPanovisu.largeurOutils;
import static editeurpanovisu.EditeurPanovisu.mbarPrincipal;
import static editeurpanovisu.EditeurPanovisu.rafraichitAffichageHotSpots;
import static editeurpanovisu.EditeurPanovisu.setbDejaSauve;
import static editeurpanovisu.EditeurPanovisu.setiNombrePanoramiquesFichier;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import editeurpanovisu.BigDecimalField;

/**
 * Gestion de l'interface de visualition de la visite virtuelle
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    /**
     * Marge supérieure des premiers éléments dans les panels de configuration.
     * Utilisée pour espacer uniformément les composants de l'interface.
     */
    private static final double PANEL_TOP_MARGIN = 15.0;
    
    /**
     * Espacement vertical entre les éléments consécutifs dans les panels.
     * Appliqué entre chaque composant pour maintenir une disposition cohérente.
     */
    private static final double PANEL_ELEMENT_SPACING = 5.0;

    /**
     * Date de compilation du logiciel.
     * Utilisée pour l'affichage des informations de version.
     */
    public Date dtBuild;

    public GestionnaireInterfaceController() {
    }
    
    /**
     * Niveau du calque (z-order) pour l'affichage du titre du panoramique.
     * Valeur entre 1 et 20, où 20 est au premier plan.
     */
    private int iCalqueTitre = 1;
    
    /**
     * Niveau du calque pour la barre de navigation classique.
     * Détermine l'ordre d'affichage par rapport aux autres éléments.
     */
    private int iCalqueBarreClassique = 1;
    
    /**
     * Niveau du calque pour la barre de navigation personnalisée.
     * Permet de superposer correctement la barre custom avec les autres éléments.
     */
    private int iCalqueBarrePersonnalisee = 1;
    
    /**
     * Niveau du calque pour l'élément de masquage.
     * Contrôle la visibilité du masque par rapport aux autres composants.
     */
    private int iCalqueMasquage = 1;
    
    /**
     * Niveau du calque pour le bouton de visite automatique.
     * Positionne le contrôle de visite auto dans la hiérarchie visuelle.
     */
    private int iCalqueVisiteAuto = 1;
    
    /**
     * Niveau du calque pour les boutons de partage sur réseaux sociaux.
     * Détermine l'ordre d'affichage des icônes de partage.
     */
    private int iCalquePartage = 1;
    
    /**
     * Niveau du calque pour l'affichage du plan 2D.
     * Contrôle le positionnement du plan par rapport aux autres éléments.
     */
    private int iCalquePlan = 1;
    
    /**
     * Niveau du calque pour l'affichage de la carte géographique.
     * Gère la superposition de la carte avec les autres composants.
     */
    private int iCalqueCarte = 1;
    
    /**
     * Niveau du calque pour l'affichage de la boussole.
     * Positionne la boussole dans l'ordre des éléments visuels.
     */
    private int iCalqueBoussole = 1;
    
    /**
     * Niveau du calque pour l'affichage des vignettes de navigation.
     * Contrôle l'ordre d'affichage des miniatures de panoramiques.
     */
    private int iCalqueVignettes = 1;
    
    /**
     * Niveau du calque pour le menu de sélection des panoramiques.
     * Positionne le menu déroulant dans la hiérarchie d'affichage.
     */
    private int iCalqueMenuPanoramiques = 1;
    
    /**
     * Niveau du calque pour les boutons Suivant/Précédent.
     * Contrôle l'ordre d'affichage des contrôles de navigation séquentielle.
     */
    private int iCalqueSuivPrec = 1;
    
    /**
     * ComboBox pour sélectionner le niveau de calque des boutons Suivant/Précédent.
     */
    private ComboBox cbNiveauSuivPrec = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque du titre.
     */
    private ComboBox cbNiveauTitre = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque de la barre classique.
     */
    private ComboBox cbNiveauBarreClassique = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque de la barre personnalisée.
     */
    private ComboBox cbNiveauBarrePersonnalisee = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque de la boussole.
     */
    private ComboBox cbNiveauBoussole = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque du masque.
     */
    private ComboBox cbNiveauMasque = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque des boutons de partage.
     */
    private ComboBox cbNiveauPartage = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque des vignettes.
     */
    private ComboBox cbNiveauVignettes = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque du menu de panoramiques.
     */
    private ComboBox cbNiveauComboMenu = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque du plan.
     */
    private ComboBox cbNiveauPlan = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque de la carte.
     */
    private ComboBox cbNiveauCarte = new ComboBox();
    
    /**
     * ComboBox pour sélectionner le niveau de calque du bouton de visite auto.
     */
    private ComboBox cbNiveauVisiteAuto = new ComboBox();

    /**
     * CheckBox pour activer/désactiver la gestion des calques.
     */
    private CheckBox cbCalques;
    
    /**
     * Nombre maximum de niveaux de calques disponibles.
     */
    final private int iNombreCalques = 20;
    
    /**
     * Tableau de CheckBox pour activer/désactiver chaque niveau de calque.
     */
    private CheckBox cbNiveauxCalque[] = new CheckBox[iNombreCalques];
    
    /**
     * AnchorPane contenant l'interface de gestion des calques.
     */
    private AnchorPane apCalque;
    
    /**
     * Liste des noms des niveaux de calques disponibles.
     */
    private List<String> listeNiveau = new ArrayList<>();
    
    /**
     * Tableau des ImageView pour l'affichage des images de fond.
     * Capacité maximale de 20 images de fond.
     */
    private ImageView[] ivImageFond = new ImageView[20];


    /**
     * Filtre pour les fichiers d'image supportés (PNG, JPG, BMP, GIF).
     * Utilisé dans les dialogues de sélection de fichiers.
     */
    private final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp", ".gif"});
    
    /**
     * Filtre pour les fichiers PNG uniquement.
     * Utilisé pour les éléments nécessitant la transparence.
     */
    private final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    /**
     * Tableau contenant les images de fond configurées.
     * Capacité maximale de 50 images.
     */
    private ImageFond[] imagesFond = new ImageFond[50];
    
    /**
     * Nombre actuel d'images de fond dans le projet.
     */
    private int iNombreImagesFond = 0;
    
    /**
     * Indique si un template est en cours d'application.
     */
    private boolean bTemplate;
    
    /**
     * PaneOutil pour la gestion de l'interface des images de fond.
     */
    private PaneOutil poImageFond;
    
    /**
     * Type du hotspot sélectionné (navigation, image, HTML).
     */
    public static String strTypeHS = "";
    
    /**
     * Nom du fichier du hotspot de navigation sélectionné.
     */
    public static String strNomfichierHS = "";
    
    /**
     * Type du hotspot image sélectionné.
     */
    public static String strTypeHSImage = "";
    
    /**
     * Nom du fichier du hotspot image sélectionné.
     */
    public static String strNomfichierHSImage = "";
    
    /**
     * Type du hotspot HTML sélectionné.
     */
    public static String strTypeHSHTML = "";
    
    /**
     * Nom du fichier du hotspot HTML sélectionné.
     */
    public static String strNomfichierHSHTML = "";

    /**
     * Style par défaut pour les hotspots de navigation.
     */
    private String strStyleHotSpots = "hotspot.png";
    
    /**
     * Style par défaut pour les hotspots d'images.
     */
    private String strStyleHotSpotImages = "photo2.png";
    
    /**
     * Style par défaut pour les hotspots HTML.
     */
    private String strStyleHotSpotHTML = "html1.png";
    
    /**
     * ResourceBundle pour la localisation de l'interface.
     */
    private ResourceBundle rbLocalisation;
    
    /**
     * Décalage horizontal de la barre de navigation classique en pixels.
     */
    private double offsetXBarreClassique = 0;
    
    /**
     * Décalage vertical de la barre de navigation classique en pixels.
     */
    private double offsetYBarreClassique = 10;
    
    /**
     * Taille des boutons de la barre de navigation classique en pixels.
     */
    private double tailleBarreClassique = 26;
    
    /**
     * Espacement entre les boutons de la barre de navigation classique en pixels.
     */
    private double espacementBarreClassique = 4;
    
    /**
     * Style par défaut de la barre de navigation classique.
     */
    private final String strStyleDefautBarreClassique = "retina";
    
    /**
     * Position de la barre de navigation classique (format "vertical:horizontal").
     * Valeur par défaut: "bottom:center".
     */
    private String strPositionBarreClassique = "bottom:center";
    
    /**
     * Style actuellement appliqué à la barre de navigation classique.
     */
    private String styleBarreClassique = strStyleDefautBarreClassique;
    
    /**
     * Activation des boutons de déplacement dans la barre classique ("oui"/"non").
     */
    private String strDeplacementsBarreClassique = "oui";
    
    /**
     * Activation des boutons de zoom dans la barre classique ("oui"/"non").
     */
    private String strZoomBarreClassique = "oui";
    
    /**
     * Activation des boutons d'outils dans la barre classique ("oui"/"non").
     */
    private String strOutilsBarreClassique = "oui";
    
    /**
     * Activation des boutons de rotation dans la barre classique ("oui"/"non").
     */
    private String strRotationBarreClassique = "oui";
    
    /**
     * Activation du bouton plein écran dans la barre classique ("oui"/"non").
     */
    private String strPleinEcranBarreClassique = "oui";
    
    /**
     * Activation du bouton mode souris dans la barre classique ("oui"/"non").
     */
    private String strSourisBarreClassique = "oui";
    
    /**
     * Visibilité initiale de la barre classique ("oui"/"non").
     */
    private String strVisibiliteBarreClassique = "oui";
    
    /**
     * ComboBox pour sélectionner le style de la barre classique.
     */
    private ComboBox cblisteStyleBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en haut à gauche.
     */
    private RadioButton rbTopLeftBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en haut au centre.
     */
    private RadioButton rbTopCenterBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en haut à droite.
     */
    private RadioButton rbTopRightBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique au milieu à gauche.
     */
    private RadioButton rbMiddleLeftBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique au milieu à droite.
     */
    private RadioButton rbMiddleRightBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en bas à gauche.
     */
    private RadioButton rbBottomLeftBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en bas au centre.
     */
    private RadioButton rbBottomCenterBarreClassique;
    
    /**
     * RadioButton pour positionner la barre classique en bas à droite.
     */
    private RadioButton rbBottomRightBarreClassique;
    
    /**
     * CheckBox contrôlant la visibilité de la barre classique.
     */
    private CheckBox cbBarreClassiqueVisible;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de déplacement.
     */
    private CheckBox cbDeplacementsBarreClassique;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de zoom.
     */
    private CheckBox cbZoomBarreClassique;
    
    /**
     * CheckBox contrôlant l'affichage des boutons d'outils.
     */
    private CheckBox cbOutilsBarreClassique;
    
    /**
     * CheckBox contrôlant l'affichage du bouton plein écran.
     */
    private CheckBox cbFSBarreClassique;
    
    /**
     * CheckBox contrôlant l'affichage du bouton mode souris.
     */
    private CheckBox cbSourisBarreClassique;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de rotation.
     */
    private CheckBox cbRotationBarreClassique;
    
    /**
     * Slider pour ajuster l'espacement entre les boutons de la barre classique.
     */
    private Slider slEspacementBarreClassique;
    
    /**
     * Champ de saisie pour le décalage horizontal de la barre classique en pixels.
     */
    private BigDecimalField bdfOffsetXBarreClassique;
    
    /**
     * Champ de saisie pour le décalage vertical de la barre classique en pixels.
     */
    private BigDecimalField bdfOffsetYBarreClassique;
    
    /**
     * ColorPicker pour choisir la couleur de fond de la barre classique.
     */
    private ColorPicker cpCouleurBarreClassique;
    
    /**
     * Couleur de fond de la barre de navigation classique.
     * Valeur par défaut: HSB(180°, 39%, 50%).
     */
    private Color couleurBarreClassique = Color.hsb(180, 0.39, 0.5);

    /**
     * Indique si la couleur d'origine de la barre personnalisée est utilisée.
     */
    private boolean bCouleurOrigineBarrePersonnalisee = true;
    
    /**
     * Nombre de zones interactives définies dans la barre personnalisée.
     */
    private int iNombreZonesBarrePersonnalisee = 0;
    
    /**
     * Décalage horizontal de la barre personnalisée en pixels.
     */
    private double offsetXBarrePersonnalisee = 0;
    
    /**
     * Décalage vertical de la barre personnalisée en pixels.
     */
    private double offsetYBarrePersonnalisee = 0;
    
    /**
     * Taille de la barre personnalisée en pourcentage.
     */
    private double tailleBarrePersonnalisee = 100;
    
    /**
     * Taille des icônes dans la barre personnalisée en pixels.
     */
    private double tailleIconesBarrePersonnalisee = 40;
    
    /**
     * Opacité de la barre personnalisée (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteBarrePersonnalisee = 1.0;
    
    /**
     * Position de la barre personnalisée (format "vertical:horizontal").
     * Valeur par défaut: "bottom:right".
     */
    private String strPositionBarrePersonnalisee = "bottom:right";
    
    /**
     * Activation des boutons de déplacement dans la barre personnalisée ("oui"/"non").
     */
    private String strDeplacementsBarrePersonnalisee = "oui";
    
    /**
     * Activation des boutons de zoom dans la barre personnalisée ("oui"/"non").
     */
    private String strZoomBarrePersonnalisee = "oui";
    
    /**
     * Activation du bouton d'information dans la barre personnalisée ("oui"/"non").
     */
    private String strInfoBarrePersonnalisee = "oui";
    
    /**
     * Activation du bouton d'aide dans la barre personnalisée ("oui"/"non").
     */
    private String strAideBarrePersonnalisee = "oui";
    
    /**
     * Activation des boutons de rotation dans la barre personnalisée ("oui"/"non").
     */
    private String strRotationBarrePersonnalisee = "oui";
    
    /**
     * Activation du bouton plein écran dans la barre personnalisée ("oui"/"non").
     */
    private String strPleinEcranBarrePersonnalisee = "oui";
    
    /**
     * Activation du bouton mode souris dans la barre personnalisée ("oui"/"non").
     */
    private String strSourisBarrePersonnalisee = "oui";
    
    /**
     * Visibilité initiale de la barre personnalisée ("oui"/"non").
     */
    private String strVisibiliteBarrePersonnalisee = "non";
    
    /**
     * Chemin vers l'image de la barre de navigation personnalisée.
     */
    private String strLienImageBarrePersonnalisee = "";
    
    /**
     * Lien personnalisé 1 pour la barre personnalisée.
     */
    private String strLien1BarrePersonnalisee = "";
    
    /**
     * Lien personnalisé 2 pour la barre personnalisée.
     */
    private String strLien2BarrePersonnalisee = "";
    
    /**
     * Tableau des zones de télécommande de la barre personnalisée.
     * Capacité maximale de 50 zones.
     */
    private ZoneTelecommande[] zonesBarrePersonnalisee = new ZoneTelecommande[50];
    
    /**
     * RadioButton pour positionner la barre personnalisée en haut à gauche.
     */
    private RadioButton rbTopLeftBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée en haut au centre.
     */
    private RadioButton rbTopCenterBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée en haut à droite.
     */
    private RadioButton rbTopRightBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée au milieu à gauche.
     */
    private RadioButton rbMiddleLeftBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée au milieu à droite.
     */
    private RadioButton rbMiddleRightBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée en bas à gauche.
     */
    private RadioButton rbBottomLeftBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée en bas au centre.
     */
    private RadioButton rbBottomCenterBarrePersonnalisee;
    
    /**
     * RadioButton pour positionner la barre personnalisée en bas à droite.
     */
    private RadioButton rbBottomRightBarrePersonnalisee;
    
    /**
     * RadioButton pour utiliser la couleur d'origine de la barre personnalisée.
     */
    private RadioButton rbCouleurOrigineBarrePersonnalisee;
    
    /**
     * RadioButton pour utiliser une couleur personnalisée pour la barre.
     */
    private RadioButton rbCouleurPersBarrePersonnalisee;
    
    /**
     * ImageView affichant la barre de navigation personnalisée.
     */
    private ImageView ivBarrePersonnalisee;
    
    /**
     * Image PNG de la barre personnalisée.
     */
    private Image imgPngBarrePersonnalisee;
    
    /**
     * Image modifiable de la barre personnalisée avec couleur appliquée.
     */
    private WritableImage wiBarrePersonnaliseeCouleur;
    
    /**
     * AnchorPane pour l'affichage de la barre personnalisée.
     */
    private AnchorPane apAfficheBarrePersonnalisee;
    
    /**
     * CheckBox contrôlant la visibilité de la barre personnalisée.
     */
    private CheckBox cbBarrePersonnaliseeVisible;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de déplacement.
     */
    private CheckBox cbDeplacementsBarrePersonnalisee;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de zoom.
     */
    private CheckBox cbZoomBarrePersonnalisee;
    
    /**
     * CheckBox contrôlant l'affichage du bouton plein écran.
     */
    private CheckBox cbFSBarrePersonnalisee;
    
    /**
     * CheckBox contrôlant l'affichage du bouton mode souris.
     */
    private CheckBox cbSourisBarrePersonnalisee;
    
    /**
     * CheckBox contrôlant l'affichage des boutons de rotation.
     */
    private CheckBox cbRotationBarrePersonnalisee;
    
    /**
     * TextField pour spécifier le chemin de l'image de la barre personnalisée.
     */
    private TextField tfLienImageBarrePersonnalisee;
    
    /**
     * TextField pour le lien personnalisé 1.
     */
    private TextField tfLien1BarrePersonnalisee;
    
    /**
     * TextField pour le lien personnalisé 2.
     */
    private TextField tfLien2BarrePersonnalisee;
    
    /**
     * Slider pour ajuster la taille de la barre personnalisée.
     */
    private Slider sltailleBarrePersonnalisee;
    
    /**
     * Slider pour ajuster la taille des icônes de la barre personnalisée.
     */
    private Slider sltailleIconesBarrePersonnalisee;
    
    /**
     * Slider pour ajuster l'opacité de la barre personnalisée.
     */
    private Slider slOpaciteBarrePersonnalisee;
    
    /**
     * Champ de saisie pour le décalage horizontal de la barre personnalisée.
     */
    private BigDecimalField bdfOffsetXBarrePersonnalisee;
    
    /**
     * Champ de saisie pour le décalage vertical de la barre personnalisée.
     */
    private BigDecimalField bdfOffsetYBarrePersonnalisee;
    
    /**
     * ColorPicker pour la couleur de la barre personnalisée.
     */
    private ColorPicker cpCouleurBarrePersonnalisee;
    
    /**
     * Couleur de la barre de navigation personnalisée.
     */
    private Color couleurBarrePersonnalisee = Color.hsb(180, 0.39, 0.5);
    
    /**
     * ToggleGroup pour les RadioButton de position de la barre personnalisée.
     */
    final ToggleGroup grpPositionBarrePersonnalisee = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de choix de couleur de la barre personnalisée.
     */
    final ToggleGroup grpCouleurBarrePersonnalisee = new ToggleGroup();
    /**
     * Bouton pour éditer la barre de navigation personnalisée.
     */
    private Button btnEditerBarre;

    /**
     * Indique si le titre de la visite est affiché.
     */
    private boolean bAfficheTitre = true;
    
    /**
     * Nom de la police utilisée pour le titre.
     * Valeur par défaut: "Verdana".
     */
    private String strTitrePoliceNom = "Verdana";
    
    /**
     * Style de la police du titre (Regular, Bold, Italic, etc.).
     */
    private String strTitrePoliceStyle = "Regular";
    
    /**
     * Taille de la police du titre en points.
     */
    private String strTitrePoliceTaille = "12.0";
    
    /**
     * Couleur du texte du titre au format hexadécimal.
     * Valeur par défaut: "#ffffff" (blanc).
     */
    private String strCouleurTitre = "#ffffff";
    
    /**
     * Couleur de fond du titre au format hexadécimal.
     * Valeur par défaut: "#4e8080" (bleu-vert).
     */
    private String strCouleurFondTitre = "#4e8080";
    
    /**
     * Position horizontale du titre ("left", "center", "right").
     */
    private String strTitrePosition = "center";
    
    /**
     * Décalage vertical du titre en pixels.
     */
    private double titreDecalage = 10;
    
    /**
     * Indique si le titre de la visite s'affiche.
     */
    private boolean bTitreVisite = false;
    
    /**
     * Indique si le titre du panoramique actuel s'affiche.
     */
    private boolean bTitrePanoramique = true;
    
    /**
     * Indique si le titre s'adapte automatiquement à la largeur.
     */
    private boolean bTitreAdapte = false;
    
    /**
     * Opacité du fond du titre (0.0 = transparent, 1.0 = opaque).
     */
    private double titreOpacite = 0.8;
    
    /**
     * Taille du titre en pourcentage de la largeur de l'écran.
     */
    private double titreTaille = 100.0;

    /**
     * Indique si la description est affichée.
     */
    private boolean bAfficheDescription = false;
    
    /**
     * CheckBox pour activer/désactiver l'affichage de la description.
     */
    private CheckBox cbAfficheDescription;
    
    /**
     * PaneOutil pour gérer l'affichage de la description.
     */
    private PaneOutil poDescription;
    
    /**
     * Indique si un chargement est en cours (blocage des interactions).
     */
    private boolean bChargementEnCours = false;

    /**
     * Indique si la boussole est affichée.
     */
    private boolean bAfficheBoussole = false;
    
    /**
     * Nom du fichier image utilisé pour la boussole.
     * Valeur par défaut: "rose3.png".
     */
    private String strImageBoussole = "rose3.png";
    
    /**
     * Position de la boussole à l'écran (format "vertical:horizontal").
     * Valeur par défaut: "top:right".
     */
    private String strPositionBoussole = "top:right";
    
    /**
     * Décalage horizontal de la boussole en pixels.
     */
    private double offsetXBoussole = 20;
    
    /**
     * Décalage vertical de la boussole en pixels.
     */
    private double offsetYBoussole = 20;
    
    /**
     * Taille de la boussole en pixels.
     */
    private double tailleBoussole = 100;
    
    /**
     * Opacité de la boussole (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteBoussole = 0.8;
    
    /**
     * Indique si l'aiguille de la boussole est mobile (rotation selon l'angle de vue).
     */
    private boolean bAiguilleMobileBoussole = true;
    /**
     * Répertoire des images de fond.
     */
    private String strRepertImagesFond = "";
    
    /**
     * Répertoire de la barre de navigation personnalisée.
     */
    private String strRepertBarrePersonnalisee = "";
    
    /**
     * ImageView affichant l'image de la boussole.
     */
    private ImageView imgBoussole;
    
    /**
     * ImageView affichant l'aiguille de la boussole.
     */
    private ImageView imgAiguille;
    
    /**
     * Champ de saisie pour le décalage horizontal de la boussole en pixels.
     */
    private BigDecimalField bdfOffsetXBoussole;
    
    /**
     * Champ de saisie pour le décalage vertical de la boussole en pixels.
     */
    private BigDecimalField bdfOffsetYBoussole;
    
    /**
     * Slider pour ajuster la taille de la boussole.
     */
    private Slider slTailleBoussole;
    
    /**
     * Slider pour ajuster l'opacité de la boussole.
     */
    private Slider slOpaciteBoussole;
    
    /**
     * CheckBox pour activer l'aiguille mobile de la boussole.
     */
    private CheckBox cbAiguilleMobile;
    
    /**
     * CheckBox pour activer l'affichage de la boussole.
     */
    private CheckBox cbAfficheBoussole;
    
    /**
     * RadioButton pour positionner la boussole en haut à gauche.
     */
    private RadioButton rbBoussTopLeft;
    
    /**
     * RadioButton pour positionner la boussole en haut à droite.
     */
    private RadioButton rbBoussTopRight;
    
    /**
     * RadioButton pour positionner la boussole en bas à gauche.
     */
    private RadioButton rbBoussBottomLeft;
    
    /**
     * RadioButton pour positionner la boussole en bas à droite.
     */
    private RadioButton rbBoussBottomRight;

    /**
     * Indique si la fenêtre d'informations est affichée.
     */
    private boolean bAfficheFenetreInfo = false;
    
    /**
     * Indique si la fenêtre d'aide est affichée.
     */
    private boolean bAfficheFenetreAide = false;
    
    /**
     * Indique si une image personnalisée est utilisée pour la fenêtre d'informations.
     */
    private boolean bFenetreInfoPersonnalise = false;
    
    /**
     * Indique si une image personnalisée est utilisée pour la fenêtre d'aide.
     */
    private boolean bFenetreAidePersonnalise = false;
    
    /**
     * Taille de la fenêtre d'informations en pourcentage.
     */
    private double fenetreInfoTaille = 100.d;
    
    /**
     * Taille de la fenêtre d'aide en pourcentage.
     */
    private double fenetreAideTaille = 100.d;
    
    /**
     * Position horizontale de la fenêtre d'informations en pixels.
     */
    private double fenetreInfoPosX = 0.d;
    
    /**
     * Position verticale de la fenêtre d'informations en pixels.
     */
    private double fenetreInfoPosY = 0.d;
    
    /**
     * Position horizontale de la fenêtre d'aide en pixels.
     */
    private double fenetreAidePosX = 0.d;
    
    /**
     * Position verticale de la fenêtre d'aide en pixels.
     */
    private double fenetreAidePosY = 0.d;
    
    /**
     * Position horizontale alternative de la fenêtre d'informations en pixels.
     */
    private double fenetreInfoposX = 0.d;
    
    /**
     * Opacité de la fenêtre d'informations (0.0 = transparent, 1.0 = opaque).
     */
    private double fenetreInfoOpacite = 0.8;
    
    /**
     * Opacité de la fenêtre d'aide (0.0 = transparent, 1.0 = opaque).
     */
    private double fenetreAideOpacite = 0.8;
    
    /**
     * Taille de la police dans les fenêtres en points.
     */
    private double fenetrePoliceTaille = 10.d;
    
    /**
     * Position horizontale de l'URL dans la fenêtre en pixels.
     */
    private double fenetreURLPosX = 0.d;
    
    /**
     * Position verticale de l'URL dans la fenêtre en pixels.
     */
    private double fenetreURLPosY = 0.d;
    
    /**
     * Opacité du fond des fenêtres (0.0 = transparent, 1.0 = opaque).
     */
    private double fenetreOpaciteFond = 0.8;
    
    /**
     * Chemin de l'image personnalisée pour la fenêtre d'informations.
     */
    private String strFenetreInfoImage = "";
    
    /**
     * Chemin de l'image personnalisée pour la fenêtre d'aide.
     */
    private String strFenetreAideImage = "";
    
    /**
     * URL affichée dans la fenêtre.
     */
    private String strFenetreURL = "";
    
    /**
     * Texte associé à l'URL dans la fenêtre.
     */
    private String strFenetreTexteURL = "";
    
    /**
     * Texte de l'infobulle pour le lien URL.
     */
    private String strFenetreURLInfobulle = "";
    
    /**
     * Couleur du lien URL au format hexadécimal.
     * Valeur par défaut: "#FFFF00" (jaune).
     */
    private String strFenetreURLCouleur = "#FFFF00";
    
    /**
     * Police utilisée dans les fenêtres.
     * Valeur par défaut: "Verdana".
     */
    private String strFenetrePolice = "Verdana";
    
    /**
     * Couleur de fond des fenêtres au format hexadécimal.
     * Valeur par défaut: "#ffffff" (blanc).
     */
    private String strFenetreCouleurFond = "#ffffff";

    /**
     * CheckBox pour activer l'image personnalisée de la fenêtre d'informations.
     */
    private CheckBox cbFenetreInfoPersonnalise;
    
    /**
     * CheckBox pour activer l'image personnalisée de la fenêtre d'aide.
     */
    private CheckBox cbFenetreAidePersonnalise;
    
    /**
     * TextField pour spécifier l'image de la fenêtre d'informations.
     */
    private TextField tfFenetreInfoImage;
    
    /**
     * TextField pour spécifier l'image de la fenêtre d'aide.
     */
    private TextField tfFenetreAideImage;
    
    /**
     * Slider pour ajuster la taille de la fenêtre d'informations.
     */
    private Slider slFenetreInfoTaille;
    
    /**
     * Slider pour ajuster la taille de la fenêtre d'aide.
     */
    private Slider slFenetreAideTaille;
    
    /**
     * Champ de saisie pour la position X de la fenêtre d'informations.
     */
    private BigDecimalField bdfFenetreInfoPosX;
    
    /**
     * Champ de saisie pour la position Y de la fenêtre d'informations.
     */
    private BigDecimalField bdfFenetreInfoPosY;
    
    /**
     * Champ de saisie pour la position X de la fenêtre d'aide.
     */
    private BigDecimalField bdfFenetreAidePosX;
    
    /**
     * Champ de saisie pour la position Y de la fenêtre d'aide.
     */
    private BigDecimalField bdfFenetreAidePosY;
    
    /**
     * Slider pour ajuster l'opacité de la fenêtre d'informations.
     */
    private Slider slFenetreInfoOpacite;
    
    /**
     * Slider pour ajuster l'opacité de la fenêtre d'aide.
     */
    private Slider slFenetreAideOpacite;
    
    /**
     * TextField pour spécifier le texte du lien URL.
     */
    private TextField tfFenetreTexteURL;
    
    /**
     * TextField pour spécifier l'URL.
     */
    private TextField tfFenetreURL;
    
    /**
     * TextField pour spécifier l'infobulle de l'URL.
     */
    private TextField tfFenetreURLInfobulle;
    
    /**
     * ComboBox pour sélectionner la police des fenêtres.
     */
    private ComboBox tfFenetrePolice;
    
    /**
     * Slider pour ajuster la taille de la police des fenêtres.
     */
    private Slider slFenetrePoliceTaille;
    
    /**
     * Champ de saisie pour la position X de l'URL.
     */
    private BigDecimalField bdfFenetreURLPosX;
    
    /**
     * Champ de saisie pour la position Y de l'URL.
     */
    private BigDecimalField bdfFenetreURLPosY;
    
    /**
     * ColorPicker pour la couleur de fond des fenêtres.
     */
    private ColorPicker cpFenetreCouleurFond;
    
    /**
     * ColorPicker pour la couleur du lien URL.
     */
    private ColorPicker cpFenetreURLCouleur;

    /**
     * AnchorPane pour l'affichage de la fenêtre d'informations.
     */
    private final AnchorPane apFenetreAfficheInfo = new AnchorPane();
    
    /**
     * Label pour afficher le lien URL dans la fenêtre.
     */
    private final Label lblFenetreURL = new Label();
    
    /**
     * Répertoire des images de masquage.
     */
    private String strRepertMasques = "";
    
    /**
     * Indique si le bouton de masquage est affiché.
     */
    private boolean bAfficheMasque = false;
    
    /**
     * Nom du fichier image utilisé pour le bouton de masquage.
     * Valeur par défaut: "MA.png".
     */
    private String strImageMasque = "MA.png";
    
    /**
     * Position du bouton de masquage (format "vertical:horizontal").
     * Valeur par défaut: "top:right".
     */
    private String strPositionMasque = "top:right";
    
    /**
     * Décalage horizontal du bouton de masquage en pixels.
     */
    private double dXMasque = 20;
    
    /**
     * Décalage vertical du bouton de masquage en pixels.
     */
    private double dYMasque = 20;
    
    /**
     * Taille du bouton de masquage en pixels.
     */
    private double tailleMasque = 30;
    
    /**
     * Opacité du bouton de masquage (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteMasque = 0.8;
    
    /**
     * Indique si le masquage s'applique à la barre de navigation.
     */
    private boolean bMasqueNavigation = true;
    
    /**
     * Indique si le masquage s'applique à la boussole.
     */
    private boolean bMasqueBoussole = true;
    
    /**
     * Indique si le masquage s'applique au titre.
     */
    private boolean bMasqueTitre = true;
    
    /**
     * Indique si le masquage s'applique au plan.
     */
    private boolean bMasquePlan = true;
    
    /**
     * Indique si le masquage s'applique aux boutons de réseaux sociaux.
     */
    private boolean bMasqueReseaux = true;
    
    /**
     * Indique si le masquage s'applique aux vignettes.
     */
    private boolean bMasqueVignettes = true;
    
    /**
     * Indique si le masquage s'applique au menu combiné.
     */
    private boolean bMasqueCombo = true;
    
    /**
     * Indique si le masquage s'applique aux boutons suivant/précédent.
     */
    private boolean bMasqueSuivPrec = true;
    
    /**
     * Indique si le masquage s'applique aux hotspots.
     */
    private boolean bMasqueHotspots = true;
    
    /**
     * ImageView affichant l'icône de masquage.
     */
    private ImageView ivMasque;
    
    /**
     * Champ de saisie pour le décalage horizontal du masque.
     */
    private BigDecimalField bdfOffsetXMasque;
    
    /**
     * Champ de saisie pour le décalage vertical du masque.
     */
    private BigDecimalField bdfOffsetYMasque;
    
    /**
     * Slider pour ajuster la taille du bouton de masquage.
     */
    private Slider slTailleMasque;
    
    /**
     * Slider pour ajuster l'opacité du bouton de masquage.
     */
    private Slider slOpaciteMasque;
    
    /**
     * CheckBox pour activer l'affichage du bouton de masquage.
     */
    private CheckBox cbAfficheMasque;
    
    /**
     * CheckBox pour activer le masquage de la navigation.
     */
    private CheckBox cbMasqueNavigation;
    
    /**
     * CheckBox pour activer le masquage de la boussole.
     */
    private CheckBox cbMasqueBoussole;
    
    /**
     * CheckBox pour activer le masquage du titre.
     */
    private CheckBox cbMasqueTitre;
    
    /**
     * CheckBox pour activer le masquage du plan.
     */
    private CheckBox cbMasquePlan;
    
    /**
     * CheckBox pour activer le masquage des réseaux sociaux.
     */
    private CheckBox cbMasqueReseaux;
    
    /**
     * CheckBox pour activer le masquage des vignettes.
     */
    private CheckBox cbMasqueVignettes;
    
    /**
     * CheckBox pour activer le masquage du menu combiné.
     */
    private CheckBox cbMasqueCombo;
    
    /**
     * CheckBox pour activer le masquage des boutons suivant/précédent.
     */
    private CheckBox cbMasqueSuivPrec;
    
    /**
     * CheckBox pour activer le masquage des hotspots.
     */
    private CheckBox cbMasqueHotspots;
    
    /**
     * RadioButton pour positionner le masque en haut à gauche.
     */
    private RadioButton rbMasqueTopLeft;
    
    /**
     * RadioButton pour positionner le masque en haut à droite.
     */
    private RadioButton rbMasqueTopRight;
    
    /**
     * RadioButton pour positionner le masque en bas à gauche.
     */
    private RadioButton rbMasqueBottomLeft;
    
    /**
     * RadioButton pour positionner le masque en bas à droite.
     */
    private RadioButton rbMasqueBottomRight;

    /**
     * Répertoire des images pour les réseaux sociaux.
     */
    private String strRepertReseauxSociaux = "";
    
    /**
     * Indique si les boutons de réseaux sociaux sont affichés.
     */
    private boolean bAfficheReseauxSociaux = false;
    
    /**
     * Nom du fichier image pour le bouton Twitter/X.
     * Valeur par défaut: "twitter.png".
     */
    private String strImageReseauxSociauxTwitter = "twitter.png";
    
    /**
     * Nom du fichier image pour le bouton Meta (Facebook).
     * Valeur par défaut: "facebook.png".
     */
    private String strImageReseauxSociauxMeta = "facebook.png";
    
    /**
     * Nom du fichier image pour le bouton Email.
     * Valeur par défaut: "email.png".
     */
    private String strImageReseauxSociauxEmail = "email.png";
    
    /**
     * Position des boutons de réseaux sociaux (format "vertical:horizontal").
     * Valeur par défaut: "top:right".
     */
    private String strPositionReseauxSociaux = "top:right";
    
    /**
     * Décalage horizontal des boutons de réseaux sociaux en pixels.
     */
    private double dXReseauxSociaux = 20;
    
    /**
     * Décalage vertical des boutons de réseaux sociaux en pixels.
     */
    private double dYReseauxSociaux = 20;
    
    /**
     * Taille des boutons de réseaux sociaux en pixels.
     */
    private double tailleReseauxSociaux = 30;
    
    /**
     * Opacité des boutons de réseaux sociaux (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteReseauxSociaux = 0.8;
    
    /**
     * Indique si le bouton Twitter/X est activé.
     */
    private boolean bReseauxSociauxTwitter = true;
    
    /**
     * Indique si le bouton Meta (Facebook) est activé.
     */
    private boolean bReseauxSociauxMeta = true;
    
    /**
     * Indique si le bouton Email est activé.
     */
    private boolean bReseauxSociauxEmail = true;
    
    /**
     * ImageView affichant l'icône Twitter/X.
     */
    private ImageView ivTwitter;
    
    /**
     * ImageView affichant l'icône Meta (Facebook).
     */
    private ImageView ivMeta;
    
    /**
     * ImageView affichant l'icône Email.
     */
    private ImageView ivEmail;
    
    /**
     * Champ de saisie pour le décalage horizontal des réseaux sociaux.
     */
    private BigDecimalField bdfOffsetXReseauxSociaux;
    
    /**
     * Champ de saisie pour le décalage vertical des réseaux sociaux.
     */
    private BigDecimalField bdfOffsetYreseauxSociaux;
    
    /**
     * Slider pour ajuster la taille des boutons de réseaux sociaux.
     */
    private Slider slTailleReseauxSociaux;
    
    /**
     * Slider pour ajuster l'opacité des boutons de réseaux sociaux.
     */
    private Slider slOpaciteReseauxSociaux;
    
    /**
     * CheckBox pour activer l'affichage des réseaux sociaux.
     */
    private CheckBox cbAfficheReseauxSociaux;
    
    /**
     * CheckBox pour activer le bouton Twitter/X.
     */
    private CheckBox cbReseauxSociauxTwitter;
    
    /**
     * CheckBox pour activer le bouton Meta (Facebook).
     */
    private CheckBox cbReseauxSociauxMeta;
    
    /**
     * CheckBox pour activer le bouton Email.
     */
    private CheckBox cbReseauxSociauxEmail;
    
    /**
     * RadioButton pour positionner les réseaux sociaux en haut à gauche.
     */
    private RadioButton rbReseauxSociauxTopLeft;
    
    /**
     * RadioButton pour positionner les réseaux sociaux en haut à droite.
     */
    private RadioButton rbReseauxSociauxTopRight;
    
    /**
     * RadioButton pour positionner les réseaux sociaux en bas à gauche.
     */
    private RadioButton rbReseauxSociauxBottomLeft;
    
    /**
     * RadioButton pour positionner les réseaux sociaux en bas à droite.
     */
    private RadioButton rbReseauxSociauxBottomRight;
    
    /**
     * AnchorPane pour gérer l'affichage des vignettes.
     */
    private AnchorPane apVignettes;
    
    /**
     * AnchorPane pour la visualisation des vignettes.
     */
    private AnchorPane apVisuVignettes;
    
    /**
     * Indique si les vignettes sont affichées.
     */
    private boolean bAfficheVignettes = false;
    
    /**
     * Couleur de fond des vignettes au format hexadécimal.
     * Valeur par défaut: "#4e8080" (bleu-vert).
     */
    private String strCouleurFondVignettes = "#4e8080";
    
    /**
     * Couleur du texte des vignettes au format hexadécimal.
     * Valeur par défaut: "#ffffff" (blanc).
     */
    private String strCouleurTexteVignettes = "#ffffff";
    
    /**
     * Position des vignettes à l'écran ("left", "right", "bottom").
     */
    private String strPositionVignettes = "bottom";
    
    /**
     * Taille des images dans les vignettes en pixels.
     */
    private double tailleImageVignettes = 120;
    
    /**
     * Opacité des vignettes (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteVignettes = 0.8;
    
    /**
     * Slider pour ajuster l'opacité des vignettes.
     */
    private Slider slOpaciteVignettes;
    
    /**
     * Slider pour ajuster la taille des vignettes.
     */
    private Slider slTailleVignettes;
    
    /**
     * CheckBox pour activer l'affichage des vignettes.
     */
    private CheckBox cbAfficheVignettes;
    
    /**
     * RadioButton pour positionner les vignettes à gauche.
     */
    private RadioButton rbVignettesLeft;
    
    /**
     * RadioButton pour positionner les vignettes à droite.
     */
    private RadioButton rbVignettesRight;
    
    /**
     * RadioButton pour positionner les vignettes en bas.
     */
    private RadioButton rbVignettesBottom;
    
    /**
     * ColorPicker pour la couleur de fond des vignettes.
     */
    private ColorPicker cpCouleurFondVignettes;
    
    /**
     * ColorPicker pour la couleur du texte des vignettes.
     */
    private ColorPicker cpCouleurTexteVignettes;
    
    /**
     * Indique si les vignettes sont repliées au démarrage.
     */
    private boolean bReplieDemarrageVignettes = false;
    
    /**
     * CheckBox pour activer le repliement au démarrage.
     */
    private CheckBox cbReplieDemarrageVignettes;

    /**
     * AnchorPane pour gérer l'affichage du menu combiné.
     */
    private AnchorPane apComboMenu;
    
    /**
     * AnchorPane pour la visualisation du menu combiné.
     */
    private AnchorPane apVisuComboMenu;
    
    /**
     * Indique si le menu combiné est affiché.
     */
    private boolean bAfficheComboMenu = false;
    
    /**
     * Indique si les images sont affichées dans le menu combiné.
     */
    private boolean bAfficheComboMenuImages = true;
    
    /**
     * Position horizontale du menu combiné ("left", "right").
     */
    private String strPositionXComboMenu = "left";
    
    /**
     * Position verticale du menu combiné ("top", "bottom").
     */
    private String strPositionYComboMenu = "top";
    
    /**
     * Décalage horizontal du menu combiné en pixels.
     */
    private double offsetXComboMenu = 10;
    
    /**
     * Décalage vertical du menu combiné en pixels.
     */
    private double offsetYComboMenu = 10;
    
    /**
     * CheckBox pour activer l'affichage du menu combiné.
     */
    private CheckBox cbAfficheComboMenu;
    
    /**
     * CheckBox pour activer l'affichage des images dans le menu combiné.
     */
    private CheckBox cbAfficheComboMenuImages;
    
    /**
     * Champ de saisie pour le décalage horizontal du menu combiné.
     */
    private BigDecimalField bdfOffsetXComboMenu;
    
    /**
     * Champ de saisie pour le décalage vertical du menu combiné.
     */
    private BigDecimalField bdfOffsetYComboMenu;
    
    /**
     * RadioButton pour positionner le menu combiné en haut à gauche.
     */
    private RadioButton rbComboMenuTopLeft;
    
    /**
     * RadioButton pour positionner le menu combiné en haut au centre.
     */
    private RadioButton rbComboMenuTopCenter;
    
    /**
     * RadioButton pour positionner le menu combiné en haut à droite.
     */
    private RadioButton rbComboMenuTopRight;
    
    /**
     * RadioButton pour positionner le menu combiné en bas à gauche.
     */
    private RadioButton rbComboMenuBottomLeft;
    
    /**
     * RadioButton pour positionner le menu combiné en bas au centre.
     */
    private RadioButton rbComboMenuBottomCenter;
    
    /**
     * RadioButton pour positionner le menu combiné en bas à droite.
     */
    private RadioButton rbComboMenuBottomRight;
    
    /**
     * ScrollPane contenant les outils de configuration.
     */
    private ScrollPane spOutils;
    
    /**
     * AnchorPane pour la visualisation.
     */
    private AnchorPane apVis;

    /**
     * AnchorPane pour le bouton de visite automatique.
     */
    private AnchorPane apBoutonVisiteAuto;
    
    /**
     * AnchorPane pour la visualisation du bouton de visite automatique.
     */
    private AnchorPane apVisuBoutonVisiteAuto;
    
    /**
     * CheckBox pour activer l'affichage du bouton de visite automatique.
     */
    private CheckBox cbAfficheBoutonVisiteAuto;
    
    /**
     * Champ de saisie pour le décalage horizontal du bouton de visite auto.
     */
    private BigDecimalField bdfOffsetXBoutonVisiteAuto;
    
    /**
     * Champ de saisie pour le décalage vertical du bouton de visite auto.
     */
    private BigDecimalField bdfOffsetYBoutonVisiteAuto;
    
    /**
     * RadioButton pour positionner le bouton visite auto en haut à gauche.
     */
    private RadioButton rbBoutonVisiteAutoTopLeft;
    
    /**
     * RadioButton pour positionner le bouton visite auto en haut au centre.
     */
    private RadioButton rbBoutonVisiteAutoTopCenter;
    
    /**
     * RadioButton pour positionner le bouton visite auto en haut à droite.
     */
    private RadioButton rbBoutonVisiteAutoTopRight;
    
    /**
     * RadioButton pour positionner le bouton visite auto en bas à gauche.
     */
    private RadioButton rbBoutonVisiteAutoBottomLeft;
    
    /**
     * RadioButton pour positionner le bouton visite auto en bas au centre.
     */
    private RadioButton rbBoutonVisiteAutoBottomCenter;
    
    /**
     * RadioButton pour positionner le bouton visite auto en bas à droite.
     */
    private RadioButton rbBoutonVisiteAutoBottomRight;
    
    /**
     * Slider pour ajuster la taille du bouton de visite automatique.
     */
    private Slider slTailleBoutonVisiteAuto;
    
    /**
     * ImageView affichant l'icône du bouton de visite automatique.
     */
    private ImageView ivBtnVisiteAuto;
    
    /**
     * ToggleGroup pour les RadioButton de position du bouton visite auto.
     */
    private ToggleGroup tgPosBoutonVisiteAuto = new ToggleGroup();
    
    /**
     * AnchorPane contenant le bouton de visite automatique.
     */
    private AnchorPane apBtnVA;
    
    /**
     * Indique si le bouton de visite automatique est affiché.
     */
    private boolean bAfficheBoutonVisiteAuto = false;
    
    /**
     * Position horizontale du bouton de visite auto ("left", "right").
     */
    private String strPositionXBoutonVisiteAuto = "right";
    
    /**
     * Position verticale du bouton de visite auto ("top", "bottom").
     */
    private String strPositionYBoutonVisiteAuto = "top";
    
    /**
     * Décalage horizontal du bouton de visite auto en pixels.
     */
    private double offsetXBoutonVisiteAuto = 10;
    
    /**
     * Décalage vertical du bouton de visite auto en pixels.
     */
    private double offsetYBoutonVisiteAuto = 10;
    
    /**
     * Taille du bouton de visite automatique en pixels.
     */
    private double tailleBoutonVisiteAuto = 32;

    /**
     * AnchorPane pour la configuration du plan 2D.
     */
    private AnchorPane apPlan;
    
    /**
     * AnchorPane pour la visualisation du plan 2D.
     */
    private AnchorPane apVisuPlan;
    
    /**
     * Indique si le plan 2D est affiché.
     */
    private boolean bAffichePlan = false;
    
    /**
     * Position du plan à l'écran ("left", "right").
     */
    private String strPositionPlan = "left";
    
    /**
     * Largeur du plan en pixels.
     */
    private double largeurPlan = 200;
    
    /**
     * Couleur de fond du plan.
     */
    private Color couleurFondPlan = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur de fond du plan au format hexadécimal.
     */
    private String strCouleurFondPlan = couleurFondPlan.toString().substring(2, 8);
    
    /**
     * Opacité du plan (0.0 = transparent, 1.0 = opaque).
     */
    private double opacitePlan = 0.8;
    
    /**
     * Couleur du texte du plan.
     */
    private Color couleurTextePlan = Color.rgb(255, 255, 255);
    
    /**
     * Couleur du texte du plan au format hexadécimal.
     */
    private String strCouleurTextePlan = couleurTextePlan.toString().substring(2, 8);
    
    /**
     * Indique si le radar de position est affiché sur le plan.
     */
    private boolean bAfficheRadar = false;
    
    /**
     * Couleur de la ligne du radar.
     */
    private Color couleurLigneRadar = Color.rgb(255, 255, 0);
    
    /**
     * Couleur de la ligne du radar au format hexadécimal.
     */
    private String strCouleurLigneRadar = couleurLigneRadar.toString().substring(2, 8);
    
    /**
     * Couleur de fond du radar.
     */
    private Color couleurFondRadar = Color.rgb(200, 0, 0);
    
    /**
     * Couleur de fond du radar au format hexadécimal.
     */
    private String strCouleurFondRadar = couleurFondRadar.toString().substring(2, 8);
    
    /**
     * Taille du radar en pixels.
     */
    private double tailleRadar = 40;
    
    /**
     * Opacité du radar (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteRadar = 0.5;
    
    /**
     * Indique si le plan est replié au démarrage.
     */
    private boolean bReplieDemarragePlan = false;

    /**
     * Indique si l'ombre est affichée sur les infobulles.
     */
    public boolean bOmbreInfoBulle = true;
    
    /**
     * Couleur de fond des infobulles au format hexadécimal.
     */
    public String strCouleurFondInfoBulle = "#eee";
    
    /**
     * Couleur du texte des infobulles au format hexadécimal.
     */
    public String strCouleurTexteInfoBulle = "#444";
    
    /**
     * Couleur de la bordure des infobulles au format hexadécimal.
     */
    public String strCouleurBordureInfoBulle = "#0A0";
    
    /**
     * Police utilisée pour les infobulles.
     */
    public String strPoliceInfoBulle = "Verdana";
    
    /**
     * Taille de la police des infobulles en points.
     */
    public double taillePoliceInfoBulle = 16;
    
    /**
     * Opacité des infobulles (0.0 = transparent, 1.0 = opaque).
     */
    public double opaciteInfoBulle = 0.75;
    
    /**
     * Épaisseurs des bordures des infobulles (top, bottom, left, right) en pixels.
     */
    public int iTailleBordureTop = 1, iTailleBordureBottom = 1, iTailleBordureLeft = 5, iTailleBordureRight = 1;
    
    /**
     * Rayons d'arrondi des coins des infobulles (TL=TopLeft, TR=TopRight, BL=BottomLeft, BR=BottomRight).
     */
    public int iArrondiTL = 0, iArrondiTR = 5, iArrondiBL = 0, iArrondiBR = 5;

    /**
     * CheckBox pour activer l'ombre des infobulles.
     */
    private CheckBox cbOmbreInfoBulle;
    
    /**
     * ColorPicker pour la couleur de fond des infobulles.
     */
    private ColorPicker cpCouleurFondInfoBulle;
    
    /**
     * ColorPicker pour la couleur du texte des infobulles.
     */
    private ColorPicker cpCouleurTextInfoBulle;
    
    /**
     * ColorPicker pour la couleur de bordure des infobulles.
     */
    private ColorPicker cpCouleurBordureInfoBulle;
    
    /**
     * ComboBox pour sélectionner la police des infobulles.
     */
    private ComboBox cbListePoliceInfoBulle;
    
    /**
     * Slider pour ajuster la taille de la police des infobulles.
     */
    private Slider slTaillePoliceInfoBulle;
    
    /**
     * Slider pour ajuster l'opacité des infobulles.
     */
    private Slider slOpaciteInfoBulle;
    
    /**
     * Champs de saisie pour les épaisseurs des bordures des infobulles.
     */
    private BigDecimalField bdfTailleBordureTop, bdfTailleBordureBottom, bdfTailleBordureLeft, bdfTailleBordureRight;
    
    /**
     * Champs de saisie pour les rayons d'arrondi des coins des infobulles.
     */
    private BigDecimalField bdfArrondiTL, bdfArrondiTR, bdfArrondiBL, bdfArrondiBR;
    
    /**
     * Label de test pour prévisualiser le style des infobulles.
     */
    private Label lblInfoBulle = new Label("test infoBulle");

    /**
     * CheckBox pour activer l'affichage du plan.
     */
    private CheckBox cbAffichePlan;
    
    /**
     * Slider pour ajuster l'opacité du plan.
     */
    private Slider slOpacitePlan;
    
    /**
     * RadioButton pour positionner le plan à gauche.
     */
    private RadioButton rbPlanLeft;
    
    /**
     * RadioButton pour positionner le plan à droite.
     */
    private RadioButton rbPlanRight;
    
    /**
     * ColorPicker pour la couleur de fond du plan.
     */
    private ColorPicker cpCouleurFondPlan;
    
    /**
     * ColorPicker pour la couleur du texte du plan.
     */
    private ColorPicker cpCouleurTextePlan;
    
    /**
     * Slider pour ajuster la largeur du plan.
     */
    private Slider slLargeurPlan;
    
    /**
     * CheckBox pour activer l'affichage du radar sur le plan.
     */
    private CheckBox cbAfficheRadar;
    
    /**
     * ColorPicker pour la couleur de fond du radar.
     */
    private ColorPicker cpCouleurFondRadar;
    
    /**
     * ColorPicker pour la couleur de la ligne du radar.
     */
    private ColorPicker cpCouleurLigneRadar;
    
    /**
     * CheckBox pour activer le repliement du plan au démarrage.
     */
    private CheckBox cbReplieDemarragePlan;
    
    /**
     * Slider pour ajuster la taille du radar.
     */
    private Slider slTailleRadar;
    
    /**
     * Slider pour ajuster l'opacité du radar.
     */
    private Slider slOpaciteRadar;
    
    /**
     * AnchorPane pour la configuration de la carte géographique.
     */
    private AnchorPane apCarte;
    
    /**
     * AnchorPane pour la visualisation de la carte géographique.
     */
    private AnchorPane apVisuCarte;
    
    /**
     * Indique si la carte géographique est affichée.
     */
    private boolean bAfficheCarte = false;

    /**
     * Position de la carte à l'écran ("left", "right").
     */
    private String strPositionCarte = "left";
    
    /**
     * Largeur de la carte en pixels.
     */
    private double largeurCarte = 400;
    
    /**
     * Hauteur de la carte en pixels.
     */
    private double hauteurCarte = 300;
    
    /**
     * Couleur de fond de la carte.
     */
    private Color couleurFondCarte = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur de fond de la carte au format hexadécimal.
     */
    private String strCouleurFondCarte = couleurFondCarte.toString().substring(2, 8);
    
    /**
     * Opacité de la carte (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteCarte = 0.8;
    
    /**
     * Couleur du texte de la carte.
     */
    private Color couleurTexteCarte = Color.rgb(255, 255, 255);
    
    /**
     * Couleur du texte de la carte au format hexadécimal.
     */
    private String strCouleurTexteCarte = couleurTexteCarte.toString().substring(2, 8);
    
    /**
     * Indique si le radar de position est affiché sur la carte.
     */
    private boolean bAfficheRadarCarte = false;
    
    /**
     * Couleur de la ligne du radar sur la carte.
     */
    private Color couleurLigneRadarCarte = Color.rgb(200, 0, 0);
    
    /**
     * Couleur de la ligne du radar au format hexadécimal.
     */
    private String strCouleurLigneRadarCarte = couleurLigneRadarCarte.toString().substring(2, 8);
    
    /**
     * Couleur de fond du radar sur la carte.
     */
    private Color couleurFondRadarCarte = Color.rgb(200, 0, 0);
    
    /**
     * Couleur de fond du radar au format hexadécimal.
     */
    private String strCouleurFondRadarCarte = couleurFondRadarCarte.toString().substring(2, 8);
    
    /**
     * Taille du radar en mètres (x3 par rapport à 20m).
     */
    private double tailleRadarCarte = 60;
    
    /**
     * Opacité du radar sur la carte (0.0 = transparent, 1.0 = opaque).
     */
    private double opaciteRadarCarte = 0.4;
    
    /**
     * Coordonnées géographiques du centre de la carte.
     */
    private CoordonneesGeographiques coordCentreCarte;
    
    /**
     * Facteur de zoom initial de la carte (1-20).
     */
    private int iFacteurZoomCarte = 14;
    
    /**
     * Angle du radar sur la carte en degrés.
     */
    private double angleRadarCarte = 45.d;
    
    /**
     * Ouverture angulaire du radar en degrés.
     */
    private double ouvertureRadarCarte = 35.d;
    
    /**
     * Nom du provider de tuiles cartographiques.
     */
    private String strNomLayers = "OpenStreetMap";
    
    /**
     * Indique si la carte est repliée au démarrage.
     */
    private boolean bReplieDemarrageCarte = false;

    /**
     * CheckBox pour activer l'affichage de la carte.
     */
    private CheckBox cbAfficheCarte;
    
    /**
     * Slider pour ajuster l'opacité de la carte.
     */
    private Slider slOpaciteCarte;
    
    /**
     * RadioButton pour positionner la carte à gauche.
     */
    private RadioButton rbCarteLeft;
    
    /**
     * RadioButton pour positionner la carte à droite.
     */
    private RadioButton rbCarteRight;
    
    /**
     * ColorPicker pour la couleur de fond de la carte.
     */
    private ColorPicker cpCouleurFondCarte;
    
    /**
     * ColorPicker pour la couleur du texte de la carte.
     */
    private ColorPicker cpCouleurTexteCarte;
    
    /**
     * Slider pour ajuster la largeur de la carte.
     */
    private Slider slLargeurCarte;
    
    /**
     * Slider pour ajuster la hauteur de la carte.
     */
    private Slider slHauteurCarte;
    
    /**
     * Slider pour ajuster le niveau de zoom de la carte.
     */
    private Slider slZoomCarte;
    
    /**
     * CheckBox pour activer l'affichage du radar sur la carte.
     */
    private CheckBox cbAfficheRadarCarte;
    
    /**
     * ColorPicker pour la couleur de fond du radar de la carte.
     */
    private ColorPicker cpCouleurFondRadarCarte;
    
    /**
     * ColorPicker pour la couleur de ligne du radar de la carte.
     */
    private ColorPicker cpCouleurLigneRadarCarte;
    
    /**
     * Slider pour ajuster la taille du radar de la carte.
     */
    private Slider slTailleRadarCarte;
    
    /**
     * Slider pour ajuster l'opacité du radar de la carte.
     */
    private Slider slOpaciteRadarCarte;
    
    /**
     * Navigateur de carte OpenLayers.
     */
    public NavigateurCarte navigateurCarteOL = null;
    
    /**
     * Flag pour éviter les re-configurations multiples de la carte.
     */
    private boolean carteEnCoursDeChargement = false;
    
    /**
     * CheckBox pour activer le repliement de la carte au démarrage.
     */
    private CheckBox cbReplieDemarrageCarte;

    /**
     * AnchorPane pour les images de fond.
     */
    private AnchorPane apImageFond;

    /**
     * AnchorPane pour la configuration du menu contextuel.
     */
    private AnchorPane apMenuContextuel;
    
    /**
     * AnchorPane pour la visualisation du menu contextuel.
     */
    private AnchorPane apVisuMenuContextuel;
    
    /**
     * Indique si le menu contextuel est affiché.
     */
    private boolean bAfficheMenuContextuel = false;
    
    /**
     * Indique si les options Précédent/Suivant sont affichées dans le menu contextuel.
     */
    private boolean bAffichePrecSuivMC = true;
    
    /**
     * Indique si les options Planet/Normal sont affichées dans le menu contextuel.
     */
    private boolean bAffichePlanetNormalMC = true;
    
    /**
     * Indique si l'option personnalisée 1 est affichée dans le menu contextuel.
     */
    private boolean bAffichePersMC1 = false;
    
    /**
     * Libellé de l'option personnalisée 1 du menu contextuel.
     */
    private String strPersLib1 = "";
    
    /**
     * URL de l'option personnalisée 1 du menu contextuel.
     */
    private String strPersURL1 = "";
    
    /**
     * Indique si l'option personnalisée 2 est affichée dans le menu contextuel.
     */
    private boolean bAffichePersMC2 = false;
    
    /**
     * Libellé de l'option personnalisée 2 du menu contextuel.
     */
    private String strPersLib2 = "";
    
    /**
     * URL de l'option personnalisée 2 du menu contextuel.
     */
    private String strPersURL2 = "";

    /**
     * CheckBox pour activer l'affichage du menu contextuel.
     */
    private CheckBox cbAfficheMenuContextuel;
    
    /**
     * CheckBox pour afficher les options Précédent/Suivant dans le menu contextuel.
     */
    private CheckBox cbAffichePrecSuivMC;
    
    /**
     * CheckBox pour afficher les options Planet/Normal dans le menu contextuel.
     */
    private CheckBox cbAffichePlanetNormalMC;
    
    /**
     * CheckBox pour activer l'option personnalisée 1 du menu contextuel.
     */
    private CheckBox cbAffichePersMC1;
    
    /**
     * CheckBox pour activer l'option personnalisée 2 du menu contextuel.
     */
    private CheckBox cbAffichePersMC2;
    
    /**
     * TextField pour le libellé de l'option personnalisée 1.
     */
    private TextField tfPersLib1;
    
    /**
     * TextField pour l'URL de l'option personnalisée 1.
     */
    private TextField tfPersURL1;
    
    /**
     * TextField pour le libellé de l'option personnalisée 2.
     */
    private TextField tfPersLib2;
    
    /**
     * TextField pour l'URL de l'option personnalisée 2.
     */
    private TextField tfPersURL2;

    /**
     * Pane principal de l'onglet Interface.
     */
    public Pane paneTabInterface;
    
    /**
     * HBox contenant l'interface principale (outils + visualisation).
     */
    private HBox hbInterface;
    
    /**
     * AnchorPane pour la visualisation du panoramique.
     */
    private AnchorPane apVisualisation;
    
    /**
     * VBox contenant les outils de configuration.
     */
    private VBox vbOutils;
    
    /**
     * RadioButton pour sélectionner l'image claire.
     */
    private RadioButton rbClair;
    
    /**
     * RadioButton pour sélectionner l'image sombre.
     */
    private RadioButton rbSombre;
    
    /**
     * RadioButton pour sélectionner une image personnalisée.
     */
    private RadioButton rbPerso;
    
    /**
     * ComboBox pour sélectionner l'image de fond.
     */
    private ComboBox cbImage;
    
    /**
     * ImageView pour la visualisation du panoramique.
     */
    private ImageView ivVisualisation;
    
    /**
     * ToggleGroup pour les RadioButton de sélection d'image.
     */
    final ToggleGroup tgImage = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position de la barre classique.
     */
    final ToggleGroup tgPositionBarreClassique = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position de la boussole.
     */
    final ToggleGroup tgPosBouss = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position du masque.
     */
    final ToggleGroup tgPosMasque = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position des réseaux sociaux.
     */
    final ToggleGroup tgPosReseauxSociaux = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position des vignettes.
     */
    final ToggleGroup tgPosVignettes = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position du menu combiné.
     */
    final ToggleGroup tgPosComboMenu = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position du plan.
     */
    final ToggleGroup tgPosPlan = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position de la carte.
     */
    final ToggleGroup tgPosCarte = new ToggleGroup();
    
    /**
     * ToggleGroup pour les RadioButton de position du titre.
     */
    final ToggleGroup tgPosTitre = new ToggleGroup();
    
    /**
     * Image de fond claire par défaut.
     */
    private Image imgClaire;
    
    /**
     * Image de fond sombre par défaut.
     */
    private Image imgSombre;
    
    /**
     * HBox contenant la barre de boutons.
     */
    private HBox hbbarreBoutons;
    
    /**
     * HBox contenant les outils de navigation.
     */
    private HBox hbOutils;
    
    /**
     * Label pour le texte du titre (ligne 1).
     */
    private Label lblTxtTitre;
    
    /**
     * Label pour le texte du titre (ligne 2).
     */
    private Label lblTxtTitre2;
    
    /**
     * ImageView pour l'icône d'information.
     */
    private ImageView ivInfo;
    
    /**
     * ImageView pour l'icône d'aide.
     */
    private ImageView ivAide;
    
    /**
     * ImageView pour l'icône de rotation automatique.
     */
    private ImageView ivAutoRotation;
    
    /**
     * ImageView pour l'icône du mode souris (état 1).
     */
    private ImageView ivModeSouris;
    
    /**
     * ImageView pour l'icône du mode souris (état 2).
     */
    private ImageView ivModeSouris2;
    
    /**
     * ImageView pour l'icône plein écran (état 1).
     */
    private ImageView ivPleinEcran;
    
    /**
     * ImageView pour l'icône plein écran (état 2).
     */
    private ImageView ivPleinEcran2;
    
    /**
     * HBox contenant les boutons de zoom.
     */
    private HBox hbZoom;
    
    /**
     * ImageView pour l'icône de zoom plus.
     */
    private ImageView ivZoomPlus;
    
    /**
     * ImageView pour l'icône de zoom moins.
     */
    private ImageView ivZoomMoins;
    
    /**
     * HBox contenant les boutons de déplacement.
     */
    private HBox hbDeplacements;
    
    /**
     * ImageView pour l'icône de déplacement vers le haut.
     */
    private ImageView ivHaut;
    
    /**
     * ImageView pour l'icône de déplacement vers le bas.
     */
    private ImageView ivBas;
    
    /**
     * ImageView pour l'icône de déplacement vers la gauche.
     */
    private ImageView ivGauche;
    
    /**
     * ImageView pour l'icône de déplacement vers la droite.
     */
    private ImageView ivDroite;
    
    /**
     * ImageView pour l'icône de hotspot panoramique.
     */
    private ImageView ivHotSpotPanoramique;
    
    /**
     * ImageView pour l'icône de hotspot image.
     */
    private ImageView ivHotSpotImage;
    
    /**
     * ImageView pour l'icône de hotspot HTML.
     */
    private ImageView ivHotSpotHTML;
    
    /**
     * ImageView pour la vignette de configuration des hotspots panoramiques.
     */
    private ImageView ivVignettePanoConfig;
    
    /**
     * ImageView pour la vignette de configuration des hotspots images.
     */
    private ImageView ivVignetteImageConfig;
    
    /**
     * ImageView pour la vignette de configuration des hotspots HTML.
     */
    private ImageView ivVignetteHTMLConfig;
    
    /**
     * AnchorPane pour le panel de configuration des hotspots panoramiques.
     */
    private AnchorPane apHotSpots1;
    
    /**
     * AnchorPane pour le panel de configuration des hotspots images.
     */
    private AnchorPane apHotSpots2;
    
    /**
     * AnchorPane pour le panel de configuration des hotspots HTML.
     */
    private AnchorPane apHotSpots3;
    
    /**
     * Conteneur pour la vignette de hotspot panoramique.
     */
    private Pane paneVignettePano;
    
    /**
     * Conteneur pour la vignette de hotspot image.
     */
    private Pane paneVignetteImage;
    
    /**
     * Conteneur pour la vignette de hotspot HTML.
     */
    private Pane paneVignetteHTML;

    /**
     * Répertoire des boutons principaux.
     */
    private String strRepertBoutonsPrincipal = "";
    
    /**
     * Répertoire des icônes de hotspots de navigation.
     */
    private String strRepertHotSpots = "";
    
    /**
     * Répertoire des icônes de hotspots photo.
     */
    private String strRepertHotSpotsPhoto = "";
    
    /**
     * Répertoire des icônes de hotspots HTML.
     */
    private String strRepertHotSpotsHTML = "";
    
    /**
     * Répertoire des images de boussoles.
     */
    private String strRepertBoussoles = "";
    
    /**
     * CheckBox pour activer les boutons Suivant/Précédent.
     */
    private CheckBox cbSuivantPrecedent;
    
    /**
     * ImageView pour l'icône du bouton Suivant.
     */
    private ImageView imgSuivant;
    
    /**
     * ImageView pour l'icône du bouton Précédent.
     */
    private ImageView imgPrecedent;
    
    /**
     * Pane de fond pour le bouton Suivant.
     */
    private Pane paneFondSuivant;
    
    /**
     * Pane de fond pour le bouton Précédent.
     */
    private Pane paneFondPrecedent;
    
    /**
     * Indique si les boutons Suivant/Précédent sont affichés.
     */
    private boolean bSuivantPrecedent;
    
    /**
     * CheckBox pour activer l'affichage du titre.
     */
    private CheckBox cbAfficheTitre;
    
    /**
     * CheckBox pour afficher le titre de la visite.
     */
    private CheckBox cbTitreVisite;
    
    /**
     * CheckBox pour afficher le titre du panoramique.
     */
    private CheckBox cbTitrePanoramique;
    
    /**
     * CheckBox pour adapter automatiquement le titre à la largeur.
     */
    private CheckBox cbTitreAdapte;
    
    /**
     * Champ de saisie pour le décalage vertical du titre.
     */
    private BigDecimalField bdfTitreDecalage;
    
    /**
     * RadioButton pour aligner le titre à gauche.
     */
    private RadioButton rbLeftTitre;
    
    /**
     * RadioButton pour centrer le titre.
     */
    private RadioButton rbCenterTitre;
    
    /**
     * RadioButton pour aligner le titre à droite.
     */
    private RadioButton rbRightTitre;

    /**
     * ColorPicker pour la couleur de fond du titre.
     */
    private ColorPicker cpCouleurFondTitre;
    
    /**
     * ColorPicker pour la couleur du texte du titre.
     */
    private ColorPicker cpCouleurTitre;
    
    /**
     * ComboBox pour sélectionner la police du titre.
     */
    private ComboBox cbListePolicesTitre;
    
    /**
     * Slider pour ajuster la taille de la police du titre.
     */
    private Slider slTaillePoliceTitre;
    
    /**
     * Slider pour ajuster l'opacité du fond du titre.
     */
    private Slider slOpaciteTitre;
    
    /**
     * Slider pour ajuster la taille du titre en pourcentage.
     */
    private Slider slTailleTitre;
    
    /**
     * ColorPicker pour la couleur de fond du thème.
     */
    private ColorPicker cpCouleurFondTheme;
    
    /**
     * ColorPicker pour la couleur du texte du thème.
     */
    private ColorPicker cpCouleurTexteTheme;
    
    /**
     * Slider pour ajuster l'opacité du thème.
     */
    private Slider slOpaciteTheme;
    
    /**
     * ComboBox pour sélectionner la police du thème.
     */
    private ComboBox cbPoliceTheme;
    
    /**
     * ColorPicker pour la couleur des hotspots panoramiques.
     */
    private ColorPicker cpCouleurHotspotsPanoramique;
    
    /**
     * ColorPicker pour la couleur des hotspots photo.
     */
    private ColorPicker cpCouleurHotspotsPhoto;
    
    /**
     * ColorPicker pour la couleur des hotspots HTML.
     */
    private ColorPicker cpCouleurHotspotsHTML;
    
    /**
     * ColorPicker pour la couleur des masques.
     */
    private ColorPicker cpCouleurMasques;
    
    /**
     * Couleur des hotspots de navigation.
     */
    private Color couleurHotspots = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur des hotspots photo.
     */
    private Color couleurHotspotsPhoto = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur des hotspots HTML.
     */
    private Color couleurHotspotsHTML = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Taille par défaut des hotspots panoramiques en pixels.
     */
    private int iTailleHotspotsPanoramique = 25;
    
    /**
     * Taille par défaut des hotspots images en pixels.
     */
    private int iTailleHotspotsImage = 25;
    
    /**
     * Taille par défaut des hotspots HTML en pixels.
     */
    private int iTailleHotspotsHTML = 25;
    
    /**
     * Slider pour ajuster la taille des hotspots panoramiques.
     */
    private Slider slTailleHotspotsPanoramique;
    
    /**
     * Slider pour ajuster la taille des hotspots images.
     */
    private Slider slTailleHotspotsImage;
    
    /**
     * Slider pour ajuster la taille des hotspots HTML.
     */
    private Slider slTailleHotspotsHTML;
    
    /**
     * Retourne la largeur réelle de l'image de visualisation affichée
     * @return largeur en pixels
     */
    private double getVisualisationWidth() {
        if (ivVisualisation == null) return 0;
        return ivVisualisation.getBoundsInParent().getWidth();
    }
    
    /**
     * Retourne la hauteur réelle de l'image de visualisation affichée
     * @return hauteur en pixels
     */
    private double getVisualisationHeight() {
        if (ivVisualisation == null) return 0;
        return ivVisualisation.getBoundsInParent().getHeight();
    }
    
    /**
     * Indique si les hotspots panoramiques sont animés par défaut.
     */
    private boolean bHotspotsPanoAnimesDefaut = false;
    
    /**
     * Indique si les hotspots photo sont animés par défaut.
     */
    private boolean bHotspotsPhotoAnimesDefaut = false;
    
    /**
     * Indique si les hotspots HTML sont animés par défaut.
     */
    private boolean bHotspotsHTMLAnimesDefaut = false;
    
    /**
     * Type d'animation par défaut pour les hotspots panoramiques.
     */
    private String strTypeAnimationPanoDefaut = "none";
    
    /**
     * Type d'animation par défaut pour les hotspots photo.
     */
    private String strTypeAnimationPhotoDefaut = "none";
    
    /**
     * Type d'animation par défaut pour les hotspots HTML.
     */
    private String strTypeAnimationHTMLDefaut = "none";
    
    /**
     * Indique si les hotspots panoramiques sont agrandis au survol par défaut.
     */
    private boolean bHotspotsPanoAgrandisDefaut = false;
    
    /**
     * Indique si les hotspots photo sont agrandis au survol par défaut.
     */
    private boolean bHotspotsPhotoAgrandisDefaut = false;
    
    /**
     * Indique si les hotspots HTML sont agrandis au survol par défaut.
     */
    private boolean bHotspotsHTMLAgrandisDefaut = false;
    
    /**
     * ComboBox pour sélectionner le type d'animation des hotspots panoramiques.
     */
    private ComboBox<String> cbTypeAnimationPanoDefaut;
    
    /**
     * ComboBox pour sélectionner le type d'animation des hotspots photo.
     */
    private ComboBox<String> cbTypeAnimationPhotoDefaut;
    
    /**
     * ComboBox pour sélectionner le type d'animation des hotspots HTML.
     */
    private ComboBox<String> cbTypeAnimationHTMLDefaut;
    
    /**
     * Animation en cours pour les hotspots panoramiques (pour pouvoir l'arrêter).
     */
    private javafx.animation.Animation animationPanoEnCours = null;
    
    /**
     * Animation en cours pour les hotspots photo (pour pouvoir l'arrêter).
     */
    private javafx.animation.Animation animationPhotoEnCours = null;
    
    /**
     * Animation en cours pour les hotspots HTML (pour pouvoir l'arrêter).
     */
    private javafx.animation.Animation animationHTMLEnCours = null;
    
    /**
     * CheckBox pour activer l'agrandissement des hotspots panoramiques au survol.
     */
    private CheckBox cbHotspotsPanoAgrandisDefaut;
    
    /**
     * CheckBox pour activer l'agrandissement des hotspots photo au survol.
     */
    private CheckBox cbHotspotsPhotoAgrandisDefaut;
    
    /**
     * CheckBox pour activer l'agrandissement des hotspots HTML au survol.
     */
    private CheckBox cbHotspotsHTMLAgrandisDefaut;
    
    /**
     * Couleur des masques.
     */
    private Color couleurMasque = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur de fond du thème.
     */
    private Color couleurFondTheme = Color.hsb(180, 0.39, 0.5);
    
    /**
     * Couleur du texte du thème (constante).
     */
    final private Color couleurTexteTheme = Color.valueOf("white");
    
    /**
     * Opacité du thème (constante).
     */
    final private double opaciteTheme = 0.8;
    
    /**
     * Tableau des images des boutons chargées.
     * Capacité maximale de 50 images.
     */
    private Image[] imgBoutons = new Image[50];
    
    /**
     * Tableau des noms des fichiers d'images de boutons.
     * Capacité maximale de 25 noms.
     */
    private String[] strNomImagesBoutons = new String[25];
    
    /**
     * Tableau des PixelReader pour lire les pixels des boutons originaux.
     */
    private PixelReader[] prLisBoutons = new PixelReader[25];
    
    /**
     * Tableau des nouvelles images de boutons modifiées.
     */
    private WritableImage[] wiNouveauxBoutons = new WritableImage[25];
    
    /**
     * Tableau des PixelWriter pour écrire les pixels des nouveaux boutons.
     */
    private PixelWriter[] pwNouveauxBoutons = new PixelWriter[25];
    
    /**
     * Nombre d'images de boutons chargées.
     */
    private int iNombreImagesBouton = 0;
    
    /**
     * Image du masque original.
     */
    private Image imgMasque;
    
    /**
     * PixelReader pour lire les pixels du masque original.
     */
    private PixelReader prLisMasque;
    
    /**
     * Nouvelle image du masque modifiée.
     */
    private WritableImage wiNouveauxMasque;
    
    /**
     * PixelWriter pour écrire les pixels du nouveau masque.
     */
    private PixelWriter pwNouveauxMasque;

    /**
     *
     * @param strStyleBarre style de la barre
     * @param strHotSpot fichier des hotspots
     * @param strMA fichier du bouton de masquage
     */
    private void chargeBarre(String strStyleBarre, String strHotSpot, String strMA) {
        File fileRepertBarre = new File(strRepertBoutonsPrincipal + File.separator + strStyleBarre);
        
        // Vérifier que le répertoire existe
        if (!fileRepertBarre.exists()) {
            System.err.println("❌ Répertoire de barre introuvable : " + fileRepertBarre.getAbsolutePath());
            return;
        }
        
        File[] fileRepertoires = fileRepertBarre.listFiles(IMAGE_FILTER);
        
        // Vérifier que des fichiers ont été trouvés
        if (fileRepertoires == null || fileRepertoires.length == 0) {
            System.err.println("❌ Aucun fichier d'image trouvé dans : " + fileRepertBarre.getAbsolutePath());
            System.err.println("   Contenu du répertoire :");
            File[] allFiles = fileRepertBarre.listFiles();
            if (allFiles != null) {
                for (File f : allFiles) {
                    System.err.println("     - " + f.getName());
                }
            }
            return;
        }
        
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
        
        // Initialiser les vignettes pour les panels de configuration (copies indépendantes)
        if (strTypeHS.equals("gif")) {
            ivVignettePanoConfig = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strHotSpot));
        } else {
            ivVignettePanoConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
        }
        ivVignettePanoConfig.setSmooth(true);
        
        if (strTypeHSImage.equals("gif")) {
            ivVignetteImageConfig = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + getStrStyleHotSpotImages()));
        } else {
            ivVignetteImageConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
        }
        ivVignetteImageConfig.setSmooth(true);
        
        if (strTypeHSHTML.equals("gif")) {
            ivVignetteHTMLConfig = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + getStrStyleHotSpotHTML()));
        } else {
            ivVignetteHTMLConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        }
        ivVignetteHTMLConfig.setSmooth(true);
        
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
        
        // Mettre à jour les vignettes après la coloration
        if (!strTypeHS.equals("gif")) {
            ivVignettePanoConfig.setImage(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
        }
        if (!strTypeHSImage.equals("gif")) {
            ivVignetteImageConfig.setImage(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
        }
        if (!strTypeHSHTML.equals("gif")) {
            ivVignetteHTMLConfig.setImage(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        }
    }

    /**
     *
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
     */
    private void changeCouleurBarrePersonnalisee(double couleurFinale, double sat, double bright) {
        if (imgPngBarrePersonnalisee == null) {
            return; // Image non chargée, on ne fait rien
        }
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
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
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
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
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
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
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
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
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
     * @param couleurFinale couleur
     * @param sat saturation
     * @param bright luminosité
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
     * Applique une animation de prévisualisation au hotspot
     * @param imageView Le hotspot à animer
     * @param animationType Le type d'animation
     * @param hotspotType Le type de hotspot ("pano", "photo", "html")
     */
    private void previsualiserAnimation(ImageView imageView, String animationType, String hotspotType) {
        // Arrêter l'animation en cours pour ce hotspot
        javafx.animation.Animation animationEnCours = null;
        switch (hotspotType) {
            case "pano":
                if (animationPanoEnCours != null) {
                    animationPanoEnCours.stop();
                    animationPanoEnCours = null;
                }
                break;
            case "photo":
                if (animationPhotoEnCours != null) {
                    animationPhotoEnCours.stop();
                    animationPhotoEnCours = null;
                }
                break;
            case "html":
                if (animationHTMLEnCours != null) {
                    animationHTMLEnCours.stop();
                    animationHTMLEnCours = null;
                }
                break;
        }
        
        // Réinitialiser les transformations
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        imageView.setRotate(0);
        imageView.setScaleX(1);
        imageView.setScaleY(1);
        imageView.setOpacity(1);
        
        // Attendre un petit moment pour s'assurer que les animations sont bien arrêtées
        if (animationType == null || animationType.equals("none")) {
            return;
        }
        
        javafx.animation.Animation nouvelleAnimation = null;
        
        // Créer de nouvelles transitions pour chaque animation (pas de réutilisation)
        switch (animationType) {
            case "pulse":
                ScaleTransition stPulse = new ScaleTransition(Duration.millis(1000), imageView);
                stPulse.setFromX(1.0);
                stPulse.setFromY(1.0);
                stPulse.setToX(1.3);
                stPulse.setToY(1.3);
                stPulse.setAutoReverse(true);
                stPulse.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = stPulse;
                break;
                
            case "rotation":
                RotateTransition rtRotation = new RotateTransition(Duration.millis(2000), imageView);
                rtRotation.setByAngle(360);
                rtRotation.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = rtRotation;
                break;
                
            case "bounce":
                TranslateTransition ttBounce = new TranslateTransition(Duration.millis(1000), imageView);
                ttBounce.setFromY(0);
                ttBounce.setToY(-20);
                ttBounce.setAutoReverse(true);
                ttBounce.setCycleCount(Timeline.INDEFINITE);
                ttBounce.setInterpolator(Interpolator.EASE_OUT);
                nouvelleAnimation = ttBounce;
                break;
                
            case "swing":
                RotateTransition rtSwing = new RotateTransition(Duration.millis(1000), imageView);
                rtSwing.setFromAngle(-15);
                rtSwing.setToAngle(15);
                rtSwing.setAutoReverse(true);
                rtSwing.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = rtSwing;
                break;
                
            case "glow":
                FadeTransition ftGlow = new FadeTransition(Duration.millis(1000), imageView);
                ftGlow.setFromValue(1.0);
                ftGlow.setToValue(0.4);
                ftGlow.setAutoReverse(true);
                ftGlow.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = ftGlow;
                break;
                
            case "heartbeat":
                ScaleTransition st1 = new ScaleTransition(Duration.millis(200), imageView);
                st1.setToX(1.3);
                st1.setToY(1.3);
                ScaleTransition st2 = new ScaleTransition(Duration.millis(200), imageView);
                st2.setToX(1.0);
                st2.setToY(1.0);
                ScaleTransition st3 = new ScaleTransition(Duration.millis(200), imageView);
                st3.setToX(1.3);
                st3.setToY(1.3);
                ScaleTransition st4 = new ScaleTransition(Duration.millis(200), imageView);
                st4.setToX(1.0);
                st4.setToY(1.0);
                SequentialTransition seqHeartbeat = new SequentialTransition(st1, st2, st3, st4);
                seqHeartbeat.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = seqHeartbeat;
                break;
                
            case "shake":
                TranslateTransition tt1 = new TranslateTransition(Duration.millis(100), imageView);
                tt1.setToX(-10);
                TranslateTransition tt2 = new TranslateTransition(Duration.millis(100), imageView);
                tt2.setToX(10);
                TranslateTransition tt3 = new TranslateTransition(Duration.millis(100), imageView);
                tt3.setToX(-10);
                TranslateTransition tt4 = new TranslateTransition(Duration.millis(100), imageView);
                tt4.setToX(10);
                TranslateTransition tt5 = new TranslateTransition(Duration.millis(100), imageView);
                tt5.setToX(0);
                SequentialTransition seqShake = new SequentialTransition(tt1, tt2, tt3, tt4, tt5);
                seqShake.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = seqShake;
                break;
                
            case "flip":
                RotateTransition rtFlip = new RotateTransition(Duration.millis(1000), imageView);
                rtFlip.setAxis(javafx.geometry.Point3D.ZERO.add(0, 1, 0));
                rtFlip.setByAngle(360);
                rtFlip.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = rtFlip;
                break;
                
            case "wobble":
                RotateTransition rtWobble1 = new RotateTransition(Duration.millis(100), imageView);
                rtWobble1.setToAngle(-5);
                RotateTransition rtWobble2 = new RotateTransition(Duration.millis(100), imageView);
                rtWobble2.setToAngle(5);
                RotateTransition rtWobble3 = new RotateTransition(Duration.millis(100), imageView);
                rtWobble3.setToAngle(-5);
                RotateTransition rtWobble4 = new RotateTransition(Duration.millis(100), imageView);
                rtWobble4.setToAngle(5);
                RotateTransition rtWobble5 = new RotateTransition(Duration.millis(100), imageView);
                rtWobble5.setToAngle(0);
                SequentialTransition seqWobble = new SequentialTransition(rtWobble1, rtWobble2, rtWobble3, rtWobble4, rtWobble5);
                seqWobble.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = seqWobble;
                break;
                
            case "tada":
                ScaleTransition stTada = new ScaleTransition(Duration.millis(1000), imageView);
                stTada.setToX(1.2);
                stTada.setToY(1.2);
                stTada.setAutoReverse(true);
                RotateTransition rtTada = new RotateTransition(Duration.millis(1000), imageView);
                rtTada.setFromAngle(-3);
                rtTada.setToAngle(3);
                rtTada.setAutoReverse(true);
                rtTada.setCycleCount(6);
                ParallelTransition parTada = new ParallelTransition(stTada, rtTada);
                parTada.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = parTada;
                break;
                
            case "flash":
                FadeTransition ftFlash = new FadeTransition(Duration.millis(500), imageView);
                ftFlash.setFromValue(1.0);
                ftFlash.setToValue(0.0);
                ftFlash.setAutoReverse(true);
                ftFlash.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = ftFlash;
                break;
                
            case "rubber":
                ScaleTransition stRubberX = new ScaleTransition(Duration.millis(1000), imageView);
                stRubberX.setFromX(1.0);
                stRubberX.setToX(1.25);
                stRubberX.setAutoReverse(true);
                ScaleTransition stRubberY = new ScaleTransition(Duration.millis(1000), imageView);
                stRubberY.setFromY(1.0);
                stRubberY.setToY(0.75);
                stRubberY.setAutoReverse(true);
                ParallelTransition parRubber = new ParallelTransition(stRubberX, stRubberY);
                parRubber.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = parRubber;
                break;
                
            case "jello":
                RotateTransition rtJello = new RotateTransition(Duration.millis(1000), imageView);
                rtJello.setFromAngle(-12.5);
                rtJello.setToAngle(12.5);
                rtJello.setAutoReverse(true);
                rtJello.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = rtJello;
                break;
                
            case "neon":
                FadeTransition ftNeon = new FadeTransition(Duration.millis(500), imageView);
                ftNeon.setFromValue(1.0);
                ftNeon.setToValue(0.2);
                ftNeon.setAutoReverse(true);
                ftNeon.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = ftNeon;
                break;
                
            case "float":
                TranslateTransition ttFloat = new TranslateTransition(Duration.millis(2000), imageView);
                ttFloat.setFromY(0);
                ttFloat.setToY(-15);
                ttFloat.setAutoReverse(true);
                ttFloat.setCycleCount(Timeline.INDEFINITE);
                ttFloat.setInterpolator(Interpolator.EASE_BOTH);
                nouvelleAnimation = ttFloat;
                break;
                
            case "desaturation":
                // La désaturation n'est pas facilement réalisable en JavaFX sans effet CSS
                // On fait un effet de fade pour simuler
                FadeTransition ftDesat = new FadeTransition(Duration.millis(1000), imageView);
                ftDesat.setFromValue(1.0);
                ftDesat.setToValue(0.5);
                ftDesat.setAutoReverse(true);
                ftDesat.setCycleCount(Timeline.INDEFINITE);
                nouvelleAnimation = ftDesat;
                break;
        }
        
        // Démarrer la nouvelle animation et la stocker
        if (nouvelleAnimation != null) {
            switch (hotspotType) {
                case "pano":
                    animationPanoEnCours = nouvelleAnimation;
                    break;
                case "photo":
                    animationPhotoEnCours = nouvelleAnimation;
                    break;
                case "html":
                    animationHTMLEnCours = nouvelleAnimation;
                    break;
            }
            nouvelleAnimation.play();
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
                posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getOffsetYBoussole();
                break;
        }
        switch (getStrPositionVignettes()) {
            case "bottom":
                if (strPositYBoussole.equals("bottom")) {
                    posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                }
                break;
            case "left":
                if (strPositXBoussole.equals("left")) {
                    posX = ivVisualisation.getLayoutX() + getOffsetXBoussole() + apVisuVignettes.getPrefWidth();
                }
                break;
            case "right":
                if (strPositXBoussole.equals("right")) {
                    posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
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
     * @param index numéro du panoramique à afficher
     */
    private void afficheImage(int index) {
        Image imgAffiche = getPanoramiquesProjet()[index].getImgPanoramique();
        
        // Calculer les dimensions du container
        double largeurContainer = getVisualisationWidth();
        double hauteurContainer = getVisualisationHeight();
        
        // Calculer le ratio du container et de l'image
        double ratioContainer = largeurContainer / hauteurContainer;
        double ratioImage = imgAffiche.getWidth() / imgAffiche.getHeight();
        
        // Calculer les dimensions du viewport pour extraire la bonne portion de l'image
        double largeurViewport, hauteurViewport, offsetX, offsetY;
        
        if (ratioImage > ratioContainer) {
            // Image plus large que le container : on extrait une portion centrée en largeur
            hauteurViewport = imgAffiche.getHeight();
            largeurViewport = hauteurViewport * ratioContainer;
            offsetX = (imgAffiche.getWidth() - largeurViewport) / 2;
            offsetY = 0;
        } else {
            // Image plus haute que le container : on extrait une portion centrée en hauteur
            largeurViewport = imgAffiche.getWidth();
            hauteurViewport = largeurViewport / ratioContainer;
            offsetX = 0;
            offsetY = (imgAffiche.getHeight() - hauteurViewport) / 2;
        }
        
        // Définir le viewport (zone extraite de l'image source)
        Rectangle2D r2dVue = new Rectangle2D(offsetX, offsetY, largeurViewport, hauteurViewport);
        ivVisualisation.setViewport(r2dVue);
        ivVisualisation.setImage(imgAffiche);
        
        // Adapter l'affichage aux dimensions exactes du container
        ivVisualisation.setFitWidth(largeurContainer);
        ivVisualisation.setFitHeight(hauteurContainer);
        ivVisualisation.setPreserveRatio(false); // On gère le ratio via le viewport
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
                posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getdXMasque() - ivMasque.getFitWidth();
                break;
        }
        switch (strPositYMasque) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - ivMasque.getFitHeight() - getdYMasque();
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
        ivMeta.setVisible(isbAfficheReseauxSociaux());
        ivEmail.setVisible(isbAfficheReseauxSociaux());
        ivTwitter.setFitWidth(getTailleReseauxSociaux());
        ivTwitter.setFitHeight(getTailleReseauxSociaux());
        ivTwitter.setOpacity(getOpaciteReseauxSociaux());
        ivTwitter.setSmooth(true);
        ivTwitter.setVisible(false);
        ivMeta.setFitWidth(getTailleReseauxSociaux());
        ivMeta.setFitHeight(getTailleReseauxSociaux());
        ivMeta.setOpacity(getOpaciteReseauxSociaux());
        ivMeta.setSmooth(true);
        ivMeta.setVisible(false);
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
                if (isbReseauxSociauxMeta() && isbAfficheReseauxSociaux()) {
                    ivMeta.setLayoutX(posX);
                    ivMeta.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }

                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getdXReseauxSociaux() - ivEmail.getFitWidth();
                dX = -(ivEmail.getFitWidth() + 5);
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxMeta() && isbAfficheReseauxSociaux()) {
                    ivMeta.setLayoutX(posX);
                    ivMeta.setVisible(true);
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
                posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - ivEmail.getFitHeight() - getdYReseauxSociaux();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getdYReseauxSociaux();
                break;
        }
        ivTwitter.setLayoutY(posY);
        ivMeta.setLayoutY(posY);
        ivEmail.setLayoutY(posY);
    }

    /**
     * Affiche la fenêtre d'information du projet
     * 
     * <p>Affiche une fenêtre modale centrée contenant :</p>
     * <ul>
     *   <li>Une image personnalisée ou l'image par défaut (infoDefaut.jpg)</li>
     *   <li>Un texte URL configurable en position et style</li>
     *   <li>Taille, opacité et position ajustables</li>
     * </ul>
     * 
     * @see #afficheFenetreAide()
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
                apFenetreAfficheInfo.setLayoutX((getVisualisationWidth() - ivFenetreInfo.getFitWidth()) / 2 + getFenetreInfoPosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((getVisualisationHeight() - ivFenetreInfo.getFitHeight()) / 2 + getFenetreInfoPosY() + ivVisualisation.getLayoutY());
                lblFenetreURL.setText(getStrFenetreTexteURL());
                lblFenetreURL.applyCss(); // Remplace impl_processCSS(true) depuis JavaFX 9+
                lblFenetreURL.setStyle("-fx-font-size:" + Math.round(getFenetrePoliceTaille() * 10) / 10 + "px;-fx-font-family: \"Arial\";");
                lblFenetreURL.setTextFill(Color.valueOf(getStrFenetreURLCouleur()));
                apFenetreAfficheInfo.getChildren().addAll(ivFenetreInfo);
                double URLPosX = (getVisualisationWidth() - lblFenetreURL.prefWidth(-1)) / 2 + getFenetreURLPosX() + ivVisualisation.getLayoutX();
                double URLPosY = (getVisualisationHeight() - lblFenetreURL.prefHeight(-1)) / 2 + getFenetreURLPosY() + ivVisualisation.getLayoutY();
                lblFenetreURL.relocate(URLPosX, URLPosY);
            } else {
                Image imgFenetreInfo = new Image("file:" + getStrRepertAppli() + File.separator + "images" + File.separator + "infoDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((getVisualisationWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((getVisualisationHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
            lblFenetreURL.setVisible(false);
        }
    }

    /**
     * Affiche la fenêtre d'aide du projet
     * 
     * <p>Affiche une fenêtre modale centrée contenant :</p>
     * <ul>
     *   <li>Une image personnalisée ou l'image par défaut (aideDefaut.jpg)</li>
     *   <li>Taille, opacité et position ajustables</li>
     * </ul>
     * 
     * @see #afficheFenetreInfo()
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
                apFenetreAfficheInfo.setLayoutX((getVisualisationWidth() - ivFenetreAide.getFitWidth()) / 2 + getFenetreAidePosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((getVisualisationHeight() - ivFenetreAide.getFitHeight()) / 2 + getFenetreAidePosY() + ivVisualisation.getLayoutY());

            } else {
                Image imgFenetreInfo = new Image("file:" + getStrRepertAppli() + File.separator + "images/aideDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((getVisualisationWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((getVisualisationHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
        }

    }

    /**
     * Méthode privée pour mettre à jour les marqueurs et le radar sur la carte
     * Appelée soit par le callback onMapReady, soit directement si la carte existe déjà
     */
    private void miseAJourMarqueursEtRadarCarte() {
        System.out.println("═════════════════════════════════════════════");
        System.out.println("🗺️ DÉBUT miseAJourMarqueursEtRadarCarte()");
        System.out.println("   navigateurCarteOL = " + navigateurCarteOL);
        System.out.println("   isbDebut() = " + (navigateurCarteOL != null ? navigateurCarteOL.isbDebut() : "null"));
        System.out.println("   Nombre de panoramiques = " + getiNombrePanoramiques());
        
        if (navigateurCarteOL == null || !navigateurCarteOL.isbDebut()) {
            System.err.println("⚠️ NavigateurCarte pas prêt pour la mise à jour");
            System.out.println("═════════════════════════════════════════════");
            return;
        }
        
        // Vérifier que l'objet JavaScript 'map' est bien initialisé
        try {
            Object mapExists = navigateurCarteOL.getWebEngine().executeScript(
                "(function() { return typeof map !== 'undefined' && map !== null; })()"
            );
            System.out.println("   🗺️ JavaScript map exists: " + mapExists);
            
            if (mapExists == null || !(Boolean) mapExists) {
                System.err.println("⚠️ L'objet JavaScript 'map' n'est pas encore initialisé, nouvelle tentative dans 500ms...");
                // Réessayer après un court délai
                javafx.application.Platform.runLater(() -> {
                    try {
                        Thread.sleep(500);
                        miseAJourMarqueursEtRadarCarte();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la vérification de l'objet map: " + e.getMessage());
            return;
        }
        
        System.out.println("🗺️ Mise à jour des marqueurs et radar sur la carte");
        
        try {
            // Retirer tous les marqueurs existants
            navigateurCarteOL.retireMarqueurs();
            boolean hasMarkers = false;
            
            // Ajouter les marqueurs pour tous les panoramiques géolocalisés
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
                    hasMarkers = true;
                }
            }
            
            // Centrer sur le centre de carte configuré ou sur le premier marqueur
            if (getCoordCentreCarte() != null) {
                navigateurCarteOL.allerCoordonnees(getCoordCentreCarte(), iFacteurZoomCarte);
            } else if (hasMarkers && getiNombrePanoramiques() > 0) {
                // Centrer sur le premier panoramique qui a des coordonnées
                for (int ii = 0; ii < getiNombrePanoramiques(); ii++) {
                    CoordonneesGeographiques coord = getPanoramiquesProjet()[ii].getMarqueurGeolocatisation();
                    if (coord != null) {
                        navigateurCarteOL.allerCoordonnees(coord, iFacteurZoomCarte);
                        break;
                    }
                }
            }
            
            System.out.println("✅ Marqueurs ajoutés et carte centrée");
            
            // Afficher le radar si nécessaire
            if (bAfficheRadarCarte) {
                CoordonneesGeographiques coords;
                if (getCoordCentreCarte() != null) {
                    coords = getCoordCentreCarte();
                } else {
                    coords = navigateurCarteOL.recupereCoordonnees();
                }
                
                if (getiNombrePanoramiques() > 0) {
                    if (getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation() != null) {
                        coords = getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation();
                    }
                }
                
                navigateurCarteOL.afficheRadar(
                    coords, 
                    angleRadarCarte, 
                    ouvertureRadarCarte, 
                    getTailleRadarCarte(), 
                    "#" + getStrCouleurLigneRadarCarte(), 
                    "#" + getStrCouleurFondRadarCarte(), 
                    getOpaciteRadarCarte()
                );
                System.out.println("✅ Radar affiché");
            }
            
            System.out.println("✅ Mise à jour de la carte terminée");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour de la carte: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Met à jour uniquement le radar sur la carte sans toucher aux marqueurs ni au centrage
     */
    private void miseAJourRadarSeul() {
        if (navigateurCarteOL == null || !navigateurCarteOL.isbDebut()) {
            return;
        }
        
        if (!bAfficheRadarCarte) {
            // Si radar désactivé, le retirer
            navigateurCarteOL.retireRadar();
            return;
        }
        
        try {
            // Déterminer les coordonnées du radar
            CoordonneesGeographiques coords;
            if (getCoordCentreCarte() != null) {
                coords = getCoordCentreCarte();
            } else {
                coords = navigateurCarteOL.recupereCoordonnees();
            }
            
            if (getiNombrePanoramiques() > 0) {
                if (getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation() != null) {
                    coords = getPanoramiquesProjet()[getiPanoActuel()].getMarqueurGeolocatisation();
                }
            }
            
            // Afficher le radar avec les nouveaux paramètres
            navigateurCarteOL.afficheRadar(
                coords, 
                angleRadarCarte, 
                ouvertureRadarCarte, 
                getTailleRadarCarte(), 
                "#" + getStrCouleurLigneRadarCarte(), 
                "#" + getStrCouleurFondRadarCarte(), 
                getOpaciteRadarCarte()
            );
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour du radar: " + e.getMessage());
        }
    }

    /**
     * Affiche la carte interactive avec marqueurs et radar
     * 
     * <p>Crée ou met à jour l'affichage de la carte géographique avec :</p>
     * <ul>
     *   <li>NavigateurCarte (OpenLayers/Leaflet) avec tuiles cartographiques</li>
     *   <li>Marqueurs de géolocalisation pour chaque panorama</li>
     *   <li>Radar de direction avec angle et ouverture configurables</li>
     *   <li>Zoom et centrage automatiques sur les panoramas</li>
     * </ul>
     * 
     * @see NavigateurCarte
     * @see #affichePlan()
     * @see #setbAfficheCarte(boolean)
     */
    public void afficheCarte() {
        apVisuCarte.getChildren().clear();
        if (isbAfficheCarte()) {
            boolean isNewNavigateur = (navigateurCarteOL == null);
            
            // Initialiser navigateurCarteOL si nécessaire
            if (isNewNavigateur && isbInternet()) {
                System.out.println("🆕 Création d'un nouveau NavigateurCarte");
                navigateurCarteOL = new NavigateurCarte();
                carteEnCoursDeChargement = true;
                
                // Définir un callback pour ajouter les marqueurs APRÈS le chargement de la carte
                navigateurCarteOL.setOnMapReady(() -> {
                    System.out.println("🎯 Callback onMapReady: Mise à jour de la carte");
                    try {
                        // Attendre que JavaScript ait le temps d'initialiser la carte Leaflet
                        Thread.sleep(1000);
                        carteEnCoursDeChargement = false;
                        miseAJourMarqueursEtRadarCarte();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } else if (!isNewNavigateur && navigateurCarteOL != null) {
                // Si la carte existe déjà, mettre à jour si elle est prête
                boolean isReady = navigateurCarteOL.isbDebut();
                System.out.println("🔄 NavigateurCarte existe déjà (instance: " + navigateurCarteOL.hashCode() + ")");
                System.out.println("   Carte prête? " + isReady);
                
                if (isReady) {
                    // Carte prête, mise à jour immédiate
                    javafx.application.Platform.runLater(() -> {
                        try {
                            Thread.sleep(100);
                            miseAJourMarqueursEtRadarCarte();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else if (!carteEnCoursDeChargement) {
                    // Carte pas encore prête ET aucun callback en attente, configurer le callback UNE SEULE FOIS
                    System.out.println("   ⏳ Carte pas encore chargée, configuration du callback");
                    carteEnCoursDeChargement = true;
                    navigateurCarteOL.setOnMapReady(() -> {
                        System.out.println("🎯 Callback onMapReady (re-enregistré): Mise à jour de la carte");
                        try {
                            Thread.sleep(1000);
                            carteEnCoursDeChargement = false;
                            miseAJourMarqueursEtRadarCarte();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Callback déjà en attente, ne rien faire
                    System.out.println("   ⏸️ Callback déjà en attente, pas de re-configuration");
                }
            } else if (!isbInternet()) {
                System.out.println("⚠️ Pas de connexion Internet - carte désactivée");
            }
            Label lblTitreCarte = new Label("Carte");
            lblTitreCarte.setPrefSize(160, 30);
            lblTitreCarte.setAlignment(Pos.CENTER);
            lblTitreCarte.setTranslateX(-lblTitreCarte.getPrefWidth() / 2 + lblTitreCarte.getPrefHeight() / 2);
            lblTitreCarte.setTranslateY(lblTitreCarte.getPrefWidth() / 2 - lblTitreCarte.getPrefHeight() / 2);
            double marge = 10.d;
            AnchorPane apVisuCarte2 = new AnchorPane();
            
            // Si pas d'Internet, créer une image de fallback au lieu de la carte
            if (!isbInternet() || navigateurCarteOL == null) {
                // Image de fallback avec texte explicatif
                Rectangle fallbackRect = new Rectangle(largeurCarte, hauteurCarte);
                fallbackRect.setFill(Color.web("#e0e0e0"));
                fallbackRect.setStroke(Color.web("#999999"));
                fallbackRect.setStrokeWidth(2);
                
                Label lblFallback = new Label("Carte non disponible\n(Pas de connexion Internet)\n\nLes dimensions et l'apparence\npeuvent être configurées");
                lblFallback.setAlignment(Pos.CENTER);
                lblFallback.setTextAlignment(TextAlignment.CENTER);
                lblFallback.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
                lblFallback.setPrefSize(largeurCarte, hauteurCarte);
                lblFallback.setLayoutX(marge);
                lblFallback.setLayoutY(marge);
                
                fallbackRect.setLayoutX(marge);
                fallbackRect.setLayoutY(marge);
                
                apVisuCarte2.getChildren().addAll(fallbackRect, lblFallback);
            } else {
                // Carte normale avec Internet
                apVisuCarte2.getChildren().add(navigateurCarteOL);
                navigateurCarteOL.setPrefSize(largeurCarte, hauteurCarte);
                navigateurCarteOL.setMinSize(largeurCarte, hauteurCarte);
                navigateurCarteOL.setMaxSize(largeurCarte, hauteurCarte);
                navigateurCarteOL.setLayoutX(marge);
                navigateurCarteOL.setLayoutY(marge);
                
                // Forcer le recalcul de la taille de la carte Leaflet après un court délai
                // pour s'assurer que le DOM est prêt
                javafx.application.Platform.runLater(() -> {
                    try {
                        Thread.sleep(200); // Attendre que la carte soit chargée
                        navigateurCarteOL.invalidateMapSize();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            
            apVisuCarte2.setPrefSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
            apVisuCarte.setPrefSize(largeurCarte + marge * 2 + 30, hauteurCarte + marge * 2);
            apVisuCarte2.setMinSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
            apVisuCarte2.setMaxSize(largeurCarte + marge * 2, hauteurCarte + marge * 2);
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
            lblTitreCarte.setStyle("-fx-text-fill : #" + couleurTexteCarte.toString().substring(2, 8) + ";-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpaciteCarte() + ");-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");
            //lblTitreCarte.setTextFill(couleurTexteCarte);
            switch (getStrPositionCarte()) {
                case "left":
                    positionX = ivVisualisation.getLayoutX();
                    lblTitreCarte.setLayoutX(apVisuCarte2.getPrefWidth());
                    lblTitreCarte.setRotate(270);
                    break;
                case "right":
                    positionX = ivVisualisation.getLayoutX() + getVisualisationWidth() - apVisuCarte.getPrefWidth();
                    lblTitreCarte.setRotate(90);
                    lblTitreCarte.setLayoutX(0);
                    apVisuCarte2.setLayoutX(30);
                    break;
            }
            apVisuCarte.setLayoutX(positionX);
            apVisuCarte.setLayoutY(positionY);
            // Note: L'affichage des marqueurs et du radar est maintenant géré par miseAJourMarqueursEtRadarCarte()
            // qui est appelée soit dans le callback onMapReady (nouvelle carte), soit directement (carte existante)
            apVisuCarte.getChildren().addAll(apVisuCarte2, lblTitreCarte);

        }
        apVisuCarte.setVisible(isbAfficheCarte());
    }

    /**
     * Affiche le plan 2D avec hotspots et boussole
     * 
     * <p>Affiche un plan d'étage ou de site avec :</p>
     * <ul>
     *   <li>Image de plan personnalisée</li>
     *   <li>Hotspots cliquables pour navigation entre panoramas</li>
     *   <li>Boussole de direction (optionnelle)</li>
     *   <li>Position configurable (haut/bas/gauche/droite)</li>
     *   <li>Titre et dimensions ajustables</li>
     * </ul>
     * 
     * @see #afficheCarte()
     * @see GestionnairePlanController
     * @see #setbAffichePlan(boolean)
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
            lblTitrePlan.setStyle("-fx-text-fill : #" + couleurTextePlan.toString().substring(2, 8) + ";-fx-background-color : rgba(" + rouge + "," + vert + "," + bleu + "," + getOpaciteCarte() + ");-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");
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
                    positionX = ivVisualisation.getLayoutX() + getVisualisationWidth() - apVisuPlan.getPrefWidth();
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
     * Affiche le bouton de visite automatique
     * 
     * <p>Affiche un bouton permettant de démarrer/arrêter la visite automatique
     * qui parcourt automatiquement les panoramas avec un délai configurable.</p>
     * 
     * <p>Caractéristiques :</p>
     * <ul>
     *   <li>Position configurable dans la visualisation</li>
     *   <li>Taille ajustable</li>
     *   <li>Icône personnalisable</li>
     *   <li>Visible uniquement si la visite auto est démarrée</li>
     * </ul>
     * 
     * @see #setbAfficheBoutonVisiteAuto(boolean)
     * @see #setTailleBoutonVisiteAuto(double)
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
            ivBtnVisiteAuto.setFitWidth(tailleBoutonVisiteAuto);
            double posX = 0, posY = 0;
            switch (getStrPositionXBoutonVisiteAuto()) {
                case "left":
                    posX = ivVisualisation.getLayoutX() + getOffsetXBoutonVisiteAuto();
                    break;
                case "center":
                    posX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - apVisuBoutonVisiteAuto.getPrefWidth()) / 2 + getOffsetXBoutonVisiteAuto();
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - apVisuBoutonVisiteAuto.getPrefWidth() - getOffsetXBoutonVisiteAuto();
                    break;
            }
            switch (getStrPositionYBoutonVisiteAuto()) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + getOffsetYBoutonVisiteAuto();
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - apVisuBoutonVisiteAuto.getPrefHeight() - getOffsetYBoutonVisiteAuto();
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
                    posX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - apVisuComboMenu.getPrefWidth()) / 2 + getOffsetXComboMenu();
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - apVisuComboMenu.getPrefWidth() - getOffsetXComboMenu();
                    break;
            }
            switch (getStrPositionYComboMenu()) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + getOffsetYComboMenu();
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - apVisuComboMenu.getPrefHeight() - getOffsetYComboMenu();
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
        lblTxtTitre.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond1);
        //lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        lblTxtTitre2.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond1);
        //lblTxtTitre2.setTextFill(Color.valueOf(getStrCouleurTitre()));
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
            double taille = (double) getTitreTaille() / 100.d * getVisualisationWidth();
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

                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - lblTxtTitre.getPrefWidth()) / 2);
                lblTxtTitre2.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - lblTxtTitre2.getPrefWidth()) / 2);
                lblTxtTitre2.setAlignment(Pos.CENTER);
                break;
            case "right":
                lblTxtTitre.setAlignment(Pos.CENTER_RIGHT);
                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - lblTxtTitre.getPrefWidth()));
                lblTxtTitre2.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - lblTxtTitre.getPrefWidth()));
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
        paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - paneFondPrecedent.getPrefWidth()));
        String strPositVert = getStrPositionBarreClassique().split(":")[0];
        String strPositHor = getStrPositionBarreClassique().split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (strPositVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + getOffsetYBarreClassique();
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + getVisualisationHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique();
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (getVisualisationHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - getOffsetYBarreClassique();
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + getVisualisationWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique();
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique();
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - hbbarreBoutons.getPrefWidth()) / 2 + getOffsetXBarreClassique();
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
                posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
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
            //lblTitreVignettes.setTextFill(Color.valueOf(getStrCouleurTexteVignettes()));
            lblTitreVignettes.setStyle("-fx-text-fill : " + getStrCouleurTexteVignettes() + ";-fx-background-color : " + getStrCouleurFondVignettes() + ";-fx-background-radius : 0 0 5 5;-fx-font-family : Verdana,Arial,sans-serif;");

            switch (getStrPositionVignettes()) {
                case "bottom":
                    lblTitreVignettes.setStyle("-fx-text-fill : " + getStrCouleurTexteVignettes() + ";-fx-background-color : " + getStrCouleurFondVignettes() + ";-fx-background-radius : 5 5 0 0;-fx-font-family : Verdana,Arial,sans-serif;");
                    apVisuVignettes2.setPrefSize(getVisualisationWidth(), getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes2.setMinSize(getVisualisationWidth(), getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes2.setLayoutX(0);
                    apVisuVignettes2.setLayoutY(30);
                    lblTitreVignettes.setLayoutX(apVisuVignettes2.getPrefWidth() - lblTitreVignettes.getPrefWidth());
                    lblTitreVignettes.setLayoutY(0);
                    apVisuVignettes.setPrefSize(getVisualisationWidth(), getTailleImageVignettes() / 2 + 40);
                    apVisuVignettes.setMinSize(getVisualisationWidth(), getTailleImageVignettes() / 2 + 40);
                    // Positionner les vignettes exactement alignées avec le bord gauche de l'image
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + getVisualisationHeight() - apVisuVignettes.getPrefHeight());
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + getVisualisationHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    if (strPositYBoussole.equals("bottom")) {
                        posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    lblTitreVignettes.setTranslateX(-lblTitreVignettes.getPrefWidth() / 2 + lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setTranslateY(lblTitreVignettes.getPrefWidth() / 2 - lblTitreVignettes.getPrefHeight() / 2);
                    lblTitreVignettes.setRotate(270);
                    if (isbAfficheTitre()) {
                        if (lblTxtTitre2.isVisible()) {
                            apVisuVignettes.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                        }
                    } else {
                        apVisuVignettes.setPrefHeight(getVisualisationHeight());
                        apVisuVignettes.setMinHeight(getVisualisationHeight());
                        apVisuVignettes2.setPrefHeight(getVisualisationHeight());
                        apVisuVignettes2.setMinHeight(getVisualisationHeight());
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
                            apVisuVignettes.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                            apVisuVignettes2.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight() - lblTxtTitre2.getHeight());
                        } else {
                            apVisuVignettes.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setPrefHeight(getVisualisationHeight() - lblTxtTitre.getHeight());
                            apVisuVignettes2.setMinHeight(getVisualisationHeight() - lblTxtTitre.getHeight());

                        }
                    } else {
                        apVisuVignettes.setPrefHeight(getVisualisationHeight());
                        apVisuVignettes.setMinHeight(getVisualisationHeight());
                        apVisuVignettes2.setPrefHeight(getVisualisationHeight());
                        apVisuVignettes2.setMinHeight(getVisualisationHeight());
                    }
                    apVisuVignettes.setPrefWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setMinWidth(getTailleImageVignettes() + 40);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX() + getVisualisationWidth() - apVisuVignettes.getPrefWidth());
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
                    paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - paneFondPrecedent.getPrefWidth()) - apVisuVignettes.getPrefWidth());
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + getVisualisationWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    if (strPositXBoussole.equals("right")) {
                        posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
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
     * @param nb nombre de zones de la barre
     * @return numéro de la zone
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
     * Affiche la barre d'outils personnalisée
     * 
     * <p>Affiche une barre d'outils créée à partir d'une image avec zones cliquables
     * définies dans un fichier SHP (shapefile HTML). Permet une personnalisation
     * complète de l'interface de navigation.</p>
     * 
     * <p>Fonctionnalités :</p>
     * <ul>
     *   <li>Image de barre personnalisée avec zones réactives</li>
     *   <li>Colorisation dynamique de l'image (HSB)</li>
     *   <li>Position et taille configurables</li>
     *   <li>Zones cliquables définies par coordonnées</li>
     *   <li>Actions liées à chaque zone (navigation, zoom, etc.)</li>
     * </ul>
     * 
     * @see #chargeBarrePersonnalisee(String)
     * @see #afficheBarreClassique(String, double, double, double, String, String, double)
     * @see ZoneTelecommande
     */
    public void afficheBarrePersonnalisee() {
        apAfficheBarrePersonnalisee.getChildren().clear();
        if (!tfLienImageBarrePersonnalisee.getText().equals("")) {
            if (getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                changeCouleurBarrePersonnalisee(couleurBarrePersonnalisee.getHue(), couleurBarrePersonnalisee.getSaturation(), couleurBarrePersonnalisee.getBrightness());
                
                // Vérifier que l'image a bien été créée
                if (getWiBarrePersonnaliseeCouleur() == null) {
                    return; // Image non disponible, on ne peut pas afficher
                }
                
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
                        LY = ivVisualisation.getLayoutY() + getVisualisationHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - getOffsetYBarrePersonnalisee() - ajoutY + hauteur / 2.d;
                        break;
                    case "middle":
                        LY = ivVisualisation.getLayoutY() + (getVisualisationHeight() - apAfficheBarrePersonnalisee.getPrefHeight()) / 2.d - getOffsetYBarrePersonnalisee();
                        break;
                }

                switch (strPositHor) {
                    case "right":
                        LX = ivVisualisation.getLayoutX() + getVisualisationWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - getOffsetXBarrePersonnalisee() - ajoutX + largeur / 2.d;
                        break;
                    case "left":
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarrePersonnalisee() + ajoutX - largeur / 2.d;
                        break;
                    case "center":
                        LX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - apAfficheBarrePersonnalisee.getPrefWidth()) / 2 + getOffsetXBarrePersonnalisee();
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
                // Appliquer l'opacité à la barre personnalisée
                apAfficheBarrePersonnalisee.setOpacity(getOpaciteBarrePersonnalisee());
            }
        }
    }

    /**
     * Lit et parse un fichier SHP (Shapefile HTML) pour la barre personnalisée
     * 
     * <p>Charge un fichier de définition de zones cliquables au format SHP
     * qui définit les zones interactives d'une barre d'outils personnalisée.</p>
     * 
     * <p>Le fichier SHP contient pour chaque zone :</p>
     * <ul>
     *   <li>ID de la zone (ex: "telUp", "telZoomPlus", "area-1")</li>
     *   <li>Type de forme (rectangle, circle, polygon)</li>
     *   <li>Coordonnées de la zone</li>
     *   <li>Actions associées</li>
     * </ul>
     * 
     * @param strNomFichier Chemin du fichier SHP à charger
     * @param zonesBarre Tableau de zones à remplir avec les données
     * @return Nombre de zones chargées, ou -1 en cas d'erreur
     * @throws UnsupportedEncodingException Si l'encodage du fichier n'est pas supporté
     * @throws IOException Si erreur de lecture du fichier
     * @see ZoneTelecommande
     * @see #chargeBarrePersonnalisee(String)
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
     * Ouvre un dialogue pour choisir une barre personnalisée
     * 
     * <p>Affiche un FileChooser permettant de sélectionner un fichier SHP/PNG
     * pour définir une barre d'outils personnalisée. Les fichiers doivent être
     * dans le répertoire <code>theme/telecommandes</code>.</p>
     * 
     * <p>Les deux fichiers requis :</p>
     * <ul>
     *   <li>Fichier .shp : Définition des zones cliquables</li>
     *   <li>Fichier .png : Image de la barre</li>
     * </ul>
     * 
     * @throws IOException Si erreur de lecture des fichiers
     * @see #chargeBarrePersonnalisee(String)
     * @see #lisFichierShp(String, ZoneTelecommande[])
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
     * Charge une barre personnalisée depuis un fichier
     * 
     * <p>Charge les fichiers SHP et PNG d'une barre personnalisée
     * à partir d'un nom de fichier de base (sans extension).</p>
     * 
     * @param strNomFichier Nom de fichier de base (sera complété avec .shp et .png)
     * @throws IOException Si erreur de lecture des fichiers
     * @see #choixBarrePersonnalisee()
     * @see #lisFichierShp(String, ZoneTelecommande[])
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
     * pannel des niveaux de calque
     *
     * @return pannel des niveaux de calque
     */
    private AnchorPane apAfficheCalques() {
        AnchorPane apCalques = new AnchorPane();
        AnchorPane apCalques1 = new AnchorPane();

        apCalques1.setPrefWidth(190);
        apCalques1.setMinWidth(190);
        apCalques1.setMaxWidth(190);
        apCalques1.setPrefHeight(620);
        apCalques1.setLayoutX(-150);
        apCalques1.setLayoutY(0);
        apCalques1.setStyle("-fx-background-color :rgba(0,0,0,0);");
        apCalques1.setOnMouseEntered((e) -> {
            apCalques1.setLayoutX(0);
        });
        apCalques1.setOnMouseExited((e) -> {
            apCalques1.setLayoutX(-150);
        });
        apCalques1.getChildren().add(apCalques);

        apCalques.setPrefSize(150, 600);
        apCalques.setMinSize(150, 600);
        apCalques.setMaxSize(150, 600);
        apCalques.setStyle("-fx-background-color:-fx-base;");
        Label lblCalque = new Label(rbLocalisation.getString("interface.calques"));
        lblCalque.setPrefSize(70, 20);
        lblCalque.setTextAlignment(TextAlignment.CENTER);
        lblCalque.setStyle("-fx-background-color:-fx-base;"
                + "-fx-border-color: derive(-fx-base,10%);"
                + "-fx-border-width: 1px;");
        lblCalque.setTranslateX(-lblCalque.getPrefWidth() / 2 + lblCalque.getPrefHeight() / 2);
        lblCalque.setTranslateY(lblCalque.getPrefWidth() / 2 - lblCalque.getPrefHeight() / 2);
        lblCalque.setRotate(270);
        lblCalque.setLayoutX(150);
        cbCalques = new CheckBox(rbLocalisation.getString("interface.calquesTous"));
        cbCalques.setLayoutX(0);
        cbCalques.setLayoutY(35);
        cbCalques.setSelected(true);
        cbCalques.setPrefWidth(150);
        cbCalques.selectedProperty().addListener((ov, old_val, new_val) -> {
            for (int i = 0; i < iNombreCalques; i++) {
                if (!cbNiveauxCalque[i].isDisabled()) {
                    cbNiveauxCalque[i].setSelected(new_val);
                }
            }
            reOrdonneElementsCalque();
        });
        cbCalques.getStyleClass().add("calque");

        apCalques.getChildren().addAll(lblCalque, cbCalques);
        for (int i = 0; i < iNombreCalques; i++) {
            cbNiveauxCalque[i] = new CheckBox(rbLocalisation.getString("interface.calque") + " " + (i + 1));
            cbNiveauxCalque[i].setLayoutX(0);
            cbNiveauxCalque[i].setLayoutY(70 + i * 25);
            cbNiveauxCalque[i].setSelected(true);
            cbNiveauxCalque[i].getStyleClass().add("calque");
            cbNiveauxCalque[i].setPrefWidth(150);
            apCalques.getChildren().addAll(cbNiveauxCalque[i]);
            cbNiveauxCalque[i].selectedProperty().addListener((ov, old_val, new_val) -> {
                reOrdonneElementsCalque();
            });
        }
        return apCalques1;
    }

    /**
     * Réordonne les éléments d'interface selon les calques
     * 
     * <p>Gère l'ordre d'affichage (z-order) des éléments d'interface selon
     * la configuration des calques. Les éléments sont retirés puis rajoutés
     * dans l'ordre des calques actifs.</p>
     * 
     * <p>Éléments réordonnés :</p>
     * <ul>
     *   <li>Titres et descriptions</li>
     *   <li>Réseaux sociaux (Twitter, Meta, Email)</li>
     *   <li>Masques et vignettes</li>
     *   <li>Boussole et aiguille</li>
     *   <li>Barres d'outils (personnalisée et classique)</li>
     *   <li>Carte et plan</li>
     *   <li>Bouton visite auto</li>
     * </ul>
     * 
     * @see #creeInterface(int, int)
     */
    public void reOrdonneElementsCalque() {
        apVisualisation.getChildren().remove(lblTxtTitre);
        apVisualisation.getChildren().remove(lblTxtTitre2);
        apVisualisation.getChildren().remove(ivTwitter);
        apVisualisation.getChildren().remove(ivMeta);
        apVisualisation.getChildren().remove(ivEmail);
        apVisualisation.getChildren().remove(ivMasque);
        apVisualisation.getChildren().remove(apVisuComboMenu);
        apVisualisation.getChildren().remove(apVisuVignettes);
        apVisualisation.getChildren().remove(imgBoussole);
        apVisualisation.getChildren().remove(imgAiguille);
        apVisualisation.getChildren().remove(apAfficheBarrePersonnalisee);
        apVisualisation.getChildren().remove(hbbarreBoutons);
        apVisualisation.getChildren().remove(apVisuPlan);
        apVisualisation.getChildren().remove(apVisuCarte);
        apVisualisation.getChildren().remove(apVisuBoutonVisiteAuto);
        apVisualisation.getChildren().remove(paneFondSuivant);
        apVisualisation.getChildren().remove(paneFondPrecedent);
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            apVisualisation.getChildren().remove(ivImageFond[i]);
        }
        Boolean[] bCalques = new Boolean[20];
        for (int ii = 1; ii <= iNombreCalques; ii++) {
            bCalques[ii - 1]
                    = (getiCalqueTitre() == ii) || (getiCalquePartage() == ii) || (getiCalqueMasquage() == ii)
                    || (getiCalqueMenuPanoramiques() == ii) || (getiCalqueVignettes() == ii) || (getiCalqueBoussole() == ii)
                    || (getiCalqueBarrePersonnalisee() == ii) || (getiCalqueBarreClassique() == ii) || (getiCalquePlan() == ii)
                    || (getiCalqueCarte() == ii) || (getiCalqueVisiteAuto() == ii) || (getiCalqueSuivPrec() == ii);
            for (int i1 = 0; i1 < getiNombreImagesFond(); i1++) {
                bCalques[ii - 1] = bCalques[ii - 1] || (getImagesFond()[i1].getiCalqueImage() == ii);
            }
            if (cbNiveauxCalque[ii - 1].isSelected()) {
                if (getiCalqueTitre() == ii) {
                    if (lblTxtTitre != null) {
                        apVisualisation.getChildren().add(lblTxtTitre);
                    }
                    if (lblTxtTitre2 != null) {
                        apVisualisation.getChildren().add(lblTxtTitre2);
                    }
                }
                if (getiCalquePartage() == ii) {
                    if (ivTwitter != null) {
                        apVisualisation.getChildren().add(ivTwitter);
                    }
                    if (ivMeta != null) {
                        apVisualisation.getChildren().add(ivMeta);
                    }
                    if (ivEmail != null) {
                        apVisualisation.getChildren().add(ivEmail);
                    }
                }
                if (getiCalqueMasquage() == ii) {
                    if (ivMasque != null) {
                        apVisualisation.getChildren().add(ivMasque);
                    }

                }
                if (getiCalqueMenuPanoramiques() == ii) {
                    if (apVisuComboMenu != null) {
                        apVisualisation.getChildren().add(apVisuComboMenu);
                    }

                }
                if (getiCalqueVignettes() == ii) {
                    if (apVisuVignettes != null) {
                        apVisualisation.getChildren().add(apVisuVignettes);
                    }

                }
                if (getiCalqueBoussole() == ii) {
                    if (imgBoussole != null) {
                        apVisualisation.getChildren().add(imgBoussole);
                    }
                    if (imgAiguille != null) {
                        apVisualisation.getChildren().add(imgAiguille);
                    }

                }
                if (getiCalqueBarrePersonnalisee() == ii) {
                    if (apAfficheBarrePersonnalisee != null) {
                        apVisualisation.getChildren().add(apAfficheBarrePersonnalisee);
                    }

                }
                if (getiCalqueBarreClassique() == ii) {
                    if (hbbarreBoutons != null) {
                        apVisualisation.getChildren().add(hbbarreBoutons);
                    }

                }
                if (getiCalquePlan() == ii) {
                    if (apVisuPlan != null) {
                        apVisualisation.getChildren().add(apVisuPlan);
                    }

                }
                if (getiCalqueCarte() == ii) {
                    if (apVisuCarte != null) {
                        apVisualisation.getChildren().add(apVisuCarte);
                    }

                }
                if (getiCalqueVisiteAuto() == ii) {
                    if (apBoutonVisiteAuto != null) {
                        apVisualisation.getChildren().add(apVisuBoutonVisiteAuto);
                    }
                }
                if (getiCalqueSuivPrec() == ii) {
                    if (apBoutonVisiteAuto != null) {
                        apVisualisation.getChildren().add(paneFondSuivant);
                        apVisualisation.getChildren().add(paneFondPrecedent);
                    }
                }
                for (int i = 0; i < getiNombreImagesFond(); i++) {
                    if (getImagesFond()[i].getiCalqueImage() == ii) {
                        if (ivImageFond[i] != null) {
                            apVisualisation.getChildren().add(ivImageFond[i]);
                        }
                    }
                }

            }
        }
        for (int ii1 = 0; ii1 < iNombreCalques; ii1++) {
            cbNiveauxCalque[ii1].setDisable(!bCalques[ii1]);
        }
    }

    /**
     * Affiche la barre d'outils classique avec boutons de navigation
     * 
     * <p>Crée et affiche une barre d'outils avec boutons standard pour :</p>
     * <ul>
     *   <li>Déplacements (haut, bas, gauche, droite)</li>
     *   <li>Zoom (plus, moins)</li>
     *   <li>Outils (info, aide, plan, carte)</li>
     *   <li>Rotation (360°)</li>
     *   <li>Mode souris et plein écran</li>
     * </ul>
     * 
     * @param strPosition Position ("haut", "bas", "gauche", "droite")
     * @param dX Décalage horizontal en pixels
     * @param dY Décalage vertical en pixels
     * @param taille Taille des icônes en pixels
     * @param strStyleBoutons Style des boutons (chemin vers dossier d'icônes)
     * @param strStyleHS Style des hotspots
     * @param espacement Espacement entre les boutons en pixels
     * @see #afficheBarrePersonnalisee()
     */
    public void afficheBarreClassique(String strPosition, double dX, double dY, double taille, String strStyleBoutons, String strStyleHS, double espacement) {
        String strRepertBoutons = "file:" + strRepertBoutonsPrincipal + File.separator + strStyleBoutons;
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation);
        if (getiNombreImagesFond() > 0) {
            for (int i = 0; i < getiNombreImagesFond(); i++) {
                ivImageFond[i] = new ImageView(getImagesFond()[i].getImgFond());
                ivImageFond[i].setId("imageFond" + i);
                ivImageFond[i].setFitWidth(getImagesFond()[i].getTailleX());
                ivImageFond[i].setFitHeight(getImagesFond()[i].getTailleY());
                double posX = 0, posY = 0;
                switch (getImagesFond()[i].getStrPosX()) {
                    case "left":
                        posX = getImagesFond()[i].getOffsetX() + ivVisualisation.getLayoutX();
                        break;
                    case "center":
                        posX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - getImagesFond()[i].getTailleX()) / 2 + getImagesFond()[i].getOffsetX();
                        break;
                    case "right":
                        posX = ivVisualisation.getLayoutX() + getVisualisationWidth() - getImagesFond()[i].getOffsetX() - getImagesFond()[i].getTailleX();
                        break;
                }
                switch (getImagesFond()[i].getStrPosY()) {
                    case "top":
                        posY = getImagesFond()[i].getOffsetY() + ivVisualisation.getLayoutY();
                        break;
                    case "middle":
                        posY = ivVisualisation.getLayoutY() + (getVisualisationHeight() - getImagesFond()[i].getTailleY()) / 2 + getImagesFond()[i].getOffsetY();
                        break;
                    case "bottom":
                        posY = ivVisualisation.getLayoutY() + getVisualisationHeight() - getImagesFond()[i].getOffsetY() - getImagesFond()[i].getTailleY();
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
                ivImageFond[i].setLayoutX(posX);
                ivImageFond[i].setLayoutY(posY);
                ivImageFond[i].setOpacity(getImagesFond()[i].getOpacite());
                apVisualisation.getChildren().add(ivImageFond[i]);
            }
        }
        apVisualisation.getChildren().addAll(lblTxtTitre, lblTxtTitre2, imgBoussole, imgAiguille, ivTwitter, ivMeta, ivEmail, apVisuVignettes, apVisuComboMenu, paneFondSuivant, paneFondPrecedent);
        lblTxtTitre.setVisible(isbAfficheTitre());
        chargeBarre(strStyleBoutons, strStyleHS, getStrImageMasque());
        afficheMasque();
        hbbarreBoutons = new HBox(espacement);
        hbbarreBoutons.setId("barreBoutons");
        hbbarreBoutons.setVisible(getStrVisibiliteBarreClassique().equals("oui"));
        ivHotSpotPanoramique.setFitWidth(30);
        ivHotSpotPanoramique.setPreserveRatio(true);
        ivHotSpotPanoramique.setLayoutX(700);
        ivHotSpotPanoramique.setLayoutY(260);
        Tooltip tltpHSPano = new Tooltip("Hotspot panoramique");
        tltpHSPano.setStyle(getStrTooltipStyle());
        Tooltip.install(ivHotSpotPanoramique, tltpHSPano);
        ivHotSpotImage.setFitWidth(30);
        ivHotSpotImage.setPreserveRatio(true);
        ivHotSpotImage.setLayoutX(820);
        ivHotSpotImage.setLayoutY(260);
        Tooltip tltpHSImage = new Tooltip("Hotspot Photo");
        tltpHSImage.setStyle(getStrTooltipStyle());
        Tooltip.install(ivHotSpotImage, tltpHSImage);
        ivHotSpotHTML.setFitWidth(30);
        ivHotSpotHTML.setPreserveRatio(true);
        ivHotSpotHTML.setLayoutX(940);
        ivHotSpotHTML.setLayoutY(260);
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
        apVisualisation.getChildren().addAll(hbbarreBoutons, ivHotSpotPanoramique, ivHotSpotImage, ivHotSpotHTML, apFenetreAfficheInfo, lblFenetreURL, apAfficheBarrePersonnalisee, apVisuPlan, apVisuCarte, apVisuBoutonVisiteAuto);
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
                LY = ivVisualisation.getLayoutY() + getVisualisationHeight() - hbbarreBoutons.getPrefHeight() - dY;
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (getVisualisationHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - dY;
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + getVisualisationWidth() - hbbarreBoutons.getPrefWidth() - dX;
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + dX;
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (getVisualisationWidth() - hbbarreBoutons.getPrefWidth()) / 2 + dX;
                break;
        }
        if (isbAfficheVignettes()) {
            switch (getStrPositionVignettes()) {
                case "bottom":
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + getVisualisationHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (strPositHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + getVisualisationWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
        ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
        ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
        ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        reOrdonneElementsCalque();

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
     * Retourne le template d'interface actuellement sélectionné
     * 
     * <p>Le template définit la configuration complète de l'interface
     * (positions, couleurs, barres, éléments visibles, etc.).</p>
     * 
     * @return Nom du template sélectionné
     * @see #setTemplate(List)
     * @see #afficheTemplate()
     */
    public String strGetTemplate() {
        StringBuilder sb = new StringBuilder(8192);  // Pré-allocation pour performance
        CoordonneesGeographiques coords;
         if (getCoordCentreCarte() != null) {
             coords = getCoordCentreCarte();
         } else {
             if (navigateurCarteOL != null) {
                 coords = navigateurCarteOL.recupereCoordonnees();
             } else {
                 coords = new CoordonneesGeographiques();
             }
         }
        sb.append("styleBarre=").append(getStyleBarreClassique()).append("\n")
                .append("couleurBoutons=").append(couleurBarreClassique.toString().substring(2, 8)).append("\n")
                .append("couleurHotspots=").append(couleurHotspots.toString().substring(2, 8)).append("\n")
                .append("couleurHotspotsPhoto=").append(couleurHotspotsPhoto.toString().substring(2, 8)).append("\n")
                .append("couleurHotspotsHTML=").append(couleurHotspotsHTML.toString().substring(2, 8)).append("\n")
                .append("tailleHotspots=").append(getiTailleHotspotsPanoramique()).append("\n")
                .append("tailleHotspotsPhoto=").append(getiTailleHotspotsImage()).append("\n")
                .append("tailleHotspotsHTML=").append(getiTailleHotspotsHTML()).append("\n")
                .append("couleurMasque=").append(couleurMasque.toString().substring(2, 8)).append("\n")
                .append("styleHotspots=").append(getStrStyleHotSpots()).append("\n")
                .append("styleHotspotImages=").append(getStrStyleHotSpotImages()).append("\n")
                .append("styleHotspotHTML=").append(getStrStyleHotSpotHTML()).append("\n")
                .append("position=").append(getStrPositionBarreClassique()).append("\n")
                .append("dX=").append(Math.round(getOffsetXBarreClassique())).append("\n")
                .append("dY=").append(Math.round(getOffsetYBarreClassique())).append("\n")
                .append("visible=").append(getStrVisibiliteBarreClassique()).append("\n")
                .append("suivantPrecedent=").append(isbSuivantPrecedent()).append("\n")
                .append("deplacement=").append(getStrDeplacementsBarreClassique()).append("\n")
                .append("zoom=").append(getStrZoomBarreClassique()).append("\n")
                .append("outils=").append(getStrOutilsBarreClassique()).append("\n")
                .append("rotation=").append(getStrRotationBarreClassique()).append("\n")
                .append("FS=").append(getStrPleinEcranBarreClassique()).append("\n")
                .append("souris=").append(getStrSourisBarreClassique()).append("\n")
                .append("espacementBoutons=").append(getEspacementBarreClassique()).append("\n")
                .append("bCouleurOrigineBarrePersonnalisee=").append(isbCouleurOrigineBarrePersonnalisee()).append("\n")
                .append("nombreZonesBarrePersonnalisee=").append(getiNombreZonesBarrePersonnalisee()).append("\n")
                .append("offsetXBarrePersonnalisee=").append(getOffsetXBarrePersonnalisee()).append("\n")
                .append("offsetYBarrePersonnalisee=").append(getOffsetYBarrePersonnalisee()).append("\n")
                .append("tailleBarrePersonnalisee=").append(getTailleBarrePersonnalisee()).append("\n")
                .append("tailleIconesBarrePersonnalisee=").append(getTailleIconesBarrePersonnalisee()).append("\n")
                .append("strPositionBarrePersonnalisee=").append(getStrPositionBarrePersonnalisee()).append("\n")
                .append("strDeplacementsBarrePersonnalisee=").append(getStrDeplacementsBarrePersonnalisee()).append("\n")
                .append("strZoomBarrePersonnalisee=").append(getStrZoomBarrePersonnalisee()).append("\n")
                .append("strInfoBarrePersonnalisee=").append(getStrInfoBarrePersonnalisee()).append("\n")
                .append("strAideBarrePersonnalisee=").append(getStrAideBarrePersonnalisee()).append("\n")
                .append("strRotationBarrePersonnalisee=").append(getStrRotationBarrePersonnalisee()).append("\n")
                .append("strPleinEcranBarrePersonnalisee=").append(getStrPleinEcranBarrePersonnalisee()).append("\n")
                .append("strSourisBarrePersonnalisee=").append(getStrSourisBarrePersonnalisee()).append("\n")
                .append("strVisibiliteBarrePersonnalisee=").append(getStrVisibiliteBarrePersonnalisee()).append("\n")
                .append("strLienImageBarrePersonnalisee=").append(getStrLienImageBarrePersonnalisee()).append("\n")
                .append("strLien1BarrePersonnalisee=").append(getStrLien1BarrePersonnalisee()).append("\n")
                .append("strLien2BarrePersonnalisee=").append(getStrLien1BarrePersonnalisee()).append("\n")
                .append("couleurBarrePersonnalisee=").append(couleurBarrePersonnalisee).append("\n")
                .append("opaciteBarrePersonnalisee=").append(getOpaciteBarrePersonnalisee()).append("\n")
                .append("afficheTitre=").append(isbAfficheTitre()).append("\n")
                .append("afficheTitreVisite=").append(isbTitreVisite()).append("\n")
                .append("afficheTitrePanoramique=").append(isbTitrePanoramique()).append("\n")
                .append("titreAdapte=").append(isbTitreAdapte()).append("\n")
                .append("afficheDescription=").append(isbAfficheDescription()).append("\n")
                .append("titrePosition=").append(getStrTitrePosition()).append("\n")
                .append("titreDecalage=").append(Math.round(getTitreDecalage() * 100.d) / 100.d).append("\n")
                .append("titrePolice=").append(getStrTitrePoliceNom()).append("\n")
                .append("titrePoliceTaille=").append(getStrTitrePoliceTaille()).append("\n")
                .append("titreOpacite=").append(Math.round(getTitreOpacite() * 100.d) / 100.d).append("\n")
                .append("titreTaille=").append(getTitreTaille()).append("\n")
                .append("titreCouleur=").append(getStrCouleurTitre()).append("\n")
                .append("titreFondCouleur=").append(getStrCouleurFondTitre()).append("\n")
                .append("bOmbreIB=").append(bOmbreInfoBulle).append("\n")
                .append("couleurTexteIB=").append(strCouleurTexteInfoBulle).append("\n")
                .append("couleurFondIB=").append(strCouleurFondInfoBulle).append("\n")
                .append("couleurBordureIB=").append(strCouleurBordureInfoBulle).append("\n")
                .append("policeIB=").append(strPoliceInfoBulle).append("\n")
                .append("taillePoliceIB=").append(taillePoliceInfoBulle).append("\n")
                .append("opaciteIB=").append(opaciteInfoBulle).append("\n")
                .append("bordureIBLeft=").append(iTailleBordureLeft).append("\n")
                .append("bordureIBRight=").append(iTailleBordureRight).append("\n")
                .append("bordureIBTop=").append(iTailleBordureTop).append("\n")
                .append("bordureIBBottom=").append(iTailleBordureBottom).append("\n")
                .append("arrondiIBTL=").append(iArrondiTL).append("\n")
                .append("arrondiIBTR=").append(iArrondiTR).append("\n")
                .append("arrondiIBBL=").append(iArrondiBL).append("\n")
                .append("arrondiIBBR=").append(iArrondiBR).append("\n")
                .append("bFenetreInfoPersonnalise=").append(isbFenetreInfoPersonnalise()).append("\n")
                .append("bFenetreAidePersonnalise=").append(isbFenetreAidePersonnalise()).append("\n")
                .append("strFenetreInfoImage =").append(getStrFenetreInfoImage()).append("\n")
                .append("strFenetreAideImage=").append(getStrFenetreAideImage()).append("\n")
                .append("fenetreInfoTaille=").append(getFenetreInfoTaille()).append("\n")
                .append("fenetreAideTaille=").append(getFenetreAideTaille()).append("\n")
                .append("fenetreInfoPosX=").append(getFenetreInfoPosX()).append("\n")
                .append("fenetreInfoPosY=").append(getFenetreInfoPosY()).append("\n")
                .append("fenetreAidePosX=").append(getFenetreAidePosX()).append("\n")
                .append("fenetreAidePosY=").append(getFenetreAidePosY()).append("\n")
                .append("fenetreInfoOpacite=").append(getFenetreInfoOpacite()).append("\n")
                .append("fenetreAideOpacite=").append(getFenetreAideOpacite()).append("\n")
                .append("strFenetreURL=").append(getStrFenetreURL()).append("\n")
                .append("strFenetreTexteURL=").append(getStrFenetreTexteURL()).append("\n")
                .append("strFenetreURLInfobulle=").append(getStrFenetreURLInfobulle()).append("\n")
                .append("strFenetreURLCouleur=").append(getStrFenetreURLCouleur()).append("\n")
                .append("strFenetrePolice=").append(getStrFenetrePolice()).append("\n")
                .append("fenetrePoliceTaille=").append(getFenetrePoliceTaille()).append("\n")
                .append("fenetreURLPosX=").append(getFenetreURLPosX()).append("\n")
                .append("fenetreURLPosY=").append(getFenetreURLPosY()).append("\n")
                .append("strFenetreCouleurFond=").append(getStrFenetreCouleurFond()).append("\n")
                .append("fenetreOpaciteFond=").append(getFenetreOpaciteFond()).append("\n")
                .append("afficheBoussole=").append(isbAfficheBoussole()).append("\n")
                .append("imageBoussole=").append(getStrImageBoussole()).append("\n")
                .append("tailleBoussole=").append(Math.round(getTailleBoussole() * 10.d) / 10.d).append("\n")
                .append("positionBoussole=").append(getStrPositionBoussole()).append("\n")
                .append("dXBoussole=").append(Math.round(getOffsetXBoussole())).append("\n")
                .append("dYBoussole=").append(Math.round(getOffsetYBoussole())).append("\n")
                .append("opaciteBoussole=").append(Math.round(getOpaciteBoussole() * 100.d) / 100.d).append("\n")
                .append("aiguilleMobile=").append(isbAiguilleMobileBoussole()).append("\n")
                .append("afficheMasque=").append(isbAfficheMasque()).append("\n")
                .append("imageMasque=").append(getStrImageMasque()).append("\n")
                .append("tailleMasque=").append(Math.round(getTailleMasque() * 10.d) / 10.d).append("\n")
                .append("positionMasque=").append(getStrPositionMasque()).append("\n")
                .append("dXMasque=").append(Math.round(getdXMasque())).append("\n")
                .append("dYMasque=").append(Math.round(getdYMasque())).append("\n")
                .append("opaciteMasque=").append(Math.round(getOpaciteMasque() * 100.d) / 100.d).append("\n")
                .append("masqueNavigation=").append(isbMasqueNavigation()).append("\n")
                .append("masqueBoussole=").append(isbMasqueBoussole()).append("\n")
                .append("masqueTitre=").append(isbMasqueTitre()).append("\n")
                .append("masquePlan=").append(isbMasquePlan()).append("\n")
                .append("masqueReseaux=").append(isbMasqueReseaux()).append("\n")
                .append("masqueVignettes=").append(isbMasqueVignettes()).append("\n")
                .append("masqueCombo=").append(isbMasqueCombo()).append("\n")
                .append("masqueSuivPrec=").append(isbMasqueSuivPrec()).append("\n")
                .append("masqueHotspots=").append(isbMasqueHotspots()).append("\n")
                .append("afficheReseauxSociaux=").append(isbAfficheReseauxSociaux()).append("\n")
                .append("tailleReseauxSociaux=").append(Math.round(getTailleReseauxSociaux() * 10.d) / 10.d).append("\n")
                .append("positionReseauxSociaux=").append(getStrPositionReseauxSociaux()).append("\n")
                .append("dXReseauxSociaux=").append(Math.round(getdXReseauxSociaux())).append("\n")
                .append("dYReseauxSociaux=").append(Math.round(getdYReseauxSociaux())).append("\n")
                .append("opaciteReseauxSociaux=").append(Math.round(getOpaciteReseauxSociaux() * 100.d) / 100.d).append("\n")
                .append("masqueTwitter=").append(isbReseauxSociauxTwitter()).append("\n")
                .append("masqueMeta=").append(isbReseauxSociauxMeta()).append("\n")
                .append("masqueEmail=").append(isbReseauxSociauxEmail()).append("\n")
                .append("afficheVignettes=").append(isbAfficheVignettes()).append("\n")
                .append("positionVignettes=").append(getStrPositionVignettes()).append("\n")
                .append("opaciteVignettes=").append(Math.round(getOpaciteVignettes() * 100.d) / 100.d).append("\n")
                .append("tailleImageVignettes=").append(Math.round(getTailleImageVignettes())).append("\n")
                .append("couleurFondVignettes=").append(getStrCouleurFondVignettes()).append("\n")
                .append("couleurTexteVignettes=").append(getStrCouleurTexteVignettes()).append("\n")
                .append("replieDemarrageVignettes=").append(isbReplieDemarrageVignettes()).append("\n")
                .append("bAfficheComboMenu=").append(isbAfficheComboMenu()).append("\n")
                .append("bAfficheComboMenuImages=").append(isbAfficheComboMenuImages()).append("\n")
                .append("positionXComboMenu=").append(getStrPositionXComboMenu()).append("\n")
                .append("positionYComboMenu=").append(getStrPositionYComboMenu()).append("\n")
                .append("offsetXComboMenu=").append(getOffsetXComboMenu()).append("\n")
                .append("offsetYComboMenu=").append(getOffsetYComboMenu()).append("\n")
                .append("bAfficheBoutonVisiteAuto=").append(isbAfficheBoutonVisiteAuto()).append("\n")
                .append("tailleBoutonVisiteAuto=").append(getTailleBoutonVisiteAuto()).append("\n")
                .append("positionXBoutonVisiteAuto=").append(getStrPositionXBoutonVisiteAuto()).append("\n")
                .append("positionYBoutonVisiteAuto=").append(getStrPositionYBoutonVisiteAuto()).append("\n")
                .append("offsetXBoutonVisiteAuto=").append(getOffsetXBoutonVisiteAuto()).append("\n")
                .append("offsetYBoutonVisiteAuto=").append(getOffsetYBoutonVisiteAuto()).append("\n")
                .append("affichePlan=").append(isbAffichePlan()).append("\n")
                .append("positionPlan=").append(getStrPositionPlan()).append("\n")
                .append("opacitePlan=").append(Math.round(getOpacitePlan() * 100.d) / 100.d).append("\n")
                .append("largeurPlan=").append(Math.round(getLargeurPlan())).append("\n")
                .append("couleurFondPlan=").append(getStrCouleurFondPlan()).append("\n")
                .append("couleurTextePlan=").append(getStrCouleurTextePlan()).append("\n")
                .append("afficheRadar=").append(isbAfficheRadar()).append("\n")
                .append("opaciteRadar=").append(Math.round(getOpaciteRadar() * 100.d) / 100.d).append("\n")
                .append("tailleRadar=").append(Math.round(getTailleRadar())).append("\n")
                .append("couleurFondRadar=").append(getStrCouleurFondRadar()).append("\n")
                .append("couleurLigneRadar=").append(getStrCouleurLigneRadar()).append("\n")
                .append("replieDemarragePlan=").append(isbReplieDemarragePlan()).append("\n")
                .append("afficheCarte=").append(isbAfficheCarte()).append("\n")
                .append("nomLayer=").append(getStrNomLayers()).append("\n")
                .append("centreLongitude=").append(coords.getLongitude()).append("\n")
                .append("centreLatitude=").append(coords.getLatitude()).append("\n")
                .append("positionCarte=").append(getStrPositionCarte()).append("\n")
                .append("opaciteCarte=").append(Math.round(getOpaciteCarte() * 100.d) / 100.d).append("\n")
                .append("largeurCarte=").append(Math.round(getLargeurCarte())).append("\n")
                .append("hauteurCarte=").append(Math.round(getHauteurCarte())).append("\n")
                .append("zoomCarte=").append(getiFacteurZoomCarte()).append("\n")
                .append("couleurFondCarte=").append(getStrCouleurFondCarte()).append("\n")
                .append("couleurTexteCarte=").append(getStrCouleurTexteCarte()).append("\n")
                .append("afficheRadarCarte=").append(isbAfficheRadarCarte()).append("\n")
                .append("opaciteRadarCarte=").append(Math.round(getOpaciteRadarCarte() * 100.d) / 100.d).append("\n")
                .append("tailleRadarCarte=").append(Math.round(getTailleRadarCarte())).append("\n")
                .append("couleurFondRadarCarte=").append(getStrCouleurFondRadarCarte()).append("\n")
                .append("couleurLigneRadarCarte=").append(getStrCouleurLigneRadarCarte()).append("\n")
                .append("replieDemarrageCarte=").append(isbReplieDemarrageCarte()).append("\n")
                .append("afficheMenuContextuel=").append(isbAfficheMenuContextuel()).append("\n")
                .append("affichePrecSuivMC=").append(isbAffichePrecSuivMC()).append("\n")
                .append("affichePlaneteNormalMC=").append(isbAffichePlanetNormalMC()).append("\n")
                .append("affichePersMC1=").append(isbAffichePersMC1()).append("\n")
                .append("affichePersMC2=").append(isbAffichePersMC2()).append("\n")
                .append("txtPersLib1=").append(getStrPersLib1()).append("\n")
                .append("txtPersLib2=").append(getStrPersLib2()).append("\n")
                .append("txtPersURL1=").append(getStrPersURL1()).append("\n")
                .append("txtPersURL2=").append(getStrPersURL2()).append("\n")
                .append("calqueTitre=").append(getiCalqueTitre()).append("\n")
                .append("calqueBarreClassique=").append(getiCalqueBarreClassique()).append("\n")
                .append("calqueBarrePersonnalisee=").append(getiCalqueBarrePersonnalisee()).append("\n")
                .append("calqueMasquage=").append(getiCalqueMasquage()).append("\n")
                .append("calquePartage=").append(getiCalquePartage()).append("\n")
                .append("calqueVisiteAuto=").append(getiCalqueVisiteAuto()).append("\n")
                .append("calquePlan=").append(getiCalquePlan()).append("\n")
                .append("calqueCarte=").append(getiCalqueCarte()).append("\n")
                .append("calqueSuivPrec=").append(getiCalqueSuivPrec()).append("\n")
                .append("calqueBoussole=").append(getiCalqueBoussole()).append("\n")
                .append("calqueVignettes=").append(getiCalqueVignettes()).append("\n")
                .append("calqueMenuPanoramiques=").append(getiCalqueMenuPanoramiques()).append("\n")
                .append("nombreImagesFond=").append(getiNombreImagesFond()).append("\n");
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            sb.append("<");
            sb.append("image=").append(getImagesFond()[i].getStrFichierImage()).append("#");
            sb.append("posX=").append(getImagesFond()[i].getStrPosX()).append("#");
            sb.append("posY=").append(getImagesFond()[i].getStrPosY()).append("#");
            sb.append("offsetX=").append(getImagesFond()[i].getOffsetX()).append("#");
            sb.append("offsetY=").append(getImagesFond()[i].getOffsetY()).append("#");
            sb.append("tailleX=").append(getImagesFond()[i].getTailleX()).append("#");
            sb.append("tailleY=").append(getImagesFond()[i].getTailleY()).append("#");
            sb.append("opacite=").append(getImagesFond()[i].getOpacite()).append("#");
            sb.append("masquable=").append(getImagesFond()[i].isMasquable()).append("#");
            sb.append("type=").append(getImagesFond()[i].getStrType()).append("#");
            sb.append("numDiapo=").append(getImagesFond()[i].getiNumDiapo()).append("#");
            sb.append("cible=").append(getImagesFond()[i].getStrCible()).append("#");
            sb.append("url=").append(getImagesFond()[i].getStrUrl()).append("#");
            sb.append("infobulle=").append(getImagesFond()[i].getStrInfobulle().replace(";", "&pv").replace(":", "&dp").replace("#", "&ds")).append("#");
            sb.append("calque=").append(getImagesFond()[i].getiCalqueImage()).append("#");
            sb.append(">\n");
        }

        return sb.toString();
    }

    /**
     * Applique un template d'interface à partir d'une liste de paramètres
     * 
     * <p>Charge et applique une configuration complète de l'interface depuis
     * une liste de paramètres (généralement lue depuis un fichier .properties).</p>
     * 
     * <p>Le template configure :</p>
     * <ul>
     *   <li>Titres, descriptions, couleurs</li>
     *   <li>Barres d'outils (classique et personnalisée)</li>
     *   <li>Masques, boussoles, vignettes</li>
     *   <li>Carte, plan, réseaux sociaux</li>
     *   <li>Calques et ordre d'affichage</li>
     *   <li>Images de fond avec positions et opacités</li>
     * </ul>
     * 
     * @param strTemplate Liste des paramètres du template
     * @see #strGetTemplate()
     * @see #afficheTemplate()
     */
    public void setTemplate(List<String> strTemplate) {
        setbAfficheBoussole(false);
        setbAfficheMasque(false);
        setbAfficheVignettes(false);
        setbAfficheReseauxSociaux(false);
        setiCalqueTitre(1);
        setiCalqueBarreClassique(1);
        setiCalqueBarrePersonnalisee(1);
        setiCalqueMasquage(1);
        setiCalquePartage(1);
        setiCalqueVisiteAuto(1);
        setiCalquePlan(1);
        setiCalqueCarte(1);
        setiCalqueBoussole(1);
        setiCalqueVignettes(1);
        setiCalqueMenuPanoramiques(1);
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
                            fileRepertoirePlan = new File(getStrRepertTemp() + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            File imgPano = new File(getImagesFond()[getiNombreImagesFond()].getStrFichierImage());
                            String strFich = imgPano.getPath().substring(imgPano.getPath().lastIndexOf(File.separator));
                            File fNouvFichier = new File(
                                    getStrRepertTemp()
                                    + File.separator
                                    + "images"
                                    + File.separator
                                    + strFich
                            );
                            try {
                                if (imgPano.exists()) {
                                    copieFichierRepertoire(imgPano.getPath(), fileRepertoirePlan.getAbsolutePath());
                                } else {
                                    String strExtension = imgPano.getPath().substring(imgPano.getPath().lastIndexOf(".") + 1);
                                    String nomFichier
                                            = getStrRepertAppli()
                                            + File.separator
                                            + "images"
                                            + File.separator
                                            + "imagePasTrouvee2."
                                            + strExtension;
                                    copieFichierRepertoire(nomFichier, fileRepertoirePlan.getAbsolutePath());
                                    File fFichier = new File(getStrRepertTemp()
                                            + File.separator
                                            + "images"
                                            + File.separator
                                            + "imagePasTrouvee2."
                                            + strExtension);
                                    fFichier.renameTo(fNouvFichier);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            getImagesFond()[getiNombreImagesFond()].setImgFond(new Image("file:" + fNouvFichier.getAbsolutePath()));
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
                            getImagesFond()[getiNombreImagesFond()].setStrInfobulle(strValeur.replace("&pv", ";").replace("&dp", ":").replace("&ds", "#"));
                            break;
                        case "type":
                            getImagesFond()[getiNombreImagesFond()].setStrType(strValeur);
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
                        case "numDiapo":
                            getImagesFond()[getiNombreImagesFond()].setiNumDiapo(Integer.parseInt(strValeur));
                            break;
                        case "calque":
                            getImagesFond()[getiNombreImagesFond()].setiCalqueImage(Integer.parseInt(strValeur));
                            break;
                        case "cible":
                            getImagesFond()[getiNombreImagesFond()].setStrCible(strValeur);
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
                        break;
                    case "opaciteBarrePersonnalisee":
                        setOpaciteBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
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
                    case "afficheDescription":
                        setbAfficheDescription(strValeur.equals("true"));
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
                    case "bOmbreIB":
                        bOmbreInfoBulle = strValeur.equals("true");
                        break;
                    case "couleurTexteIB":
                        strCouleurTexteInfoBulle = strValeur;
                        break;
                    case "couleurFondIB":
                        strCouleurFondInfoBulle = strValeur;
                        break;
                    case "couleurBordureIB":
                        strCouleurBordureInfoBulle = strValeur;
                        break;
                    case "policeIB":
                        strPoliceInfoBulle = strValeur;
                        break;
                    case "taillePoliceIB":
                        taillePoliceInfoBulle = Double.parseDouble(strValeur);
                        break;
                    case "opaciteIB":
                        opaciteInfoBulle = Double.parseDouble(strValeur);
                        break;
                    case "bordureIBLeft":
                        iTailleBordureLeft = Integer.parseInt(strValeur);
                        break;
                    case "bordureIBRight":
                        iTailleBordureRight = Integer.parseInt(strValeur);
                        break;
                    case "bordureIBTop":
                        iTailleBordureTop = Integer.parseInt(strValeur);
                        break;
                    case "bordureIBBottom":
                        iTailleBordureBottom = Integer.parseInt(strValeur);
                        break;
                    case "arrondiIBTL":
                        iArrondiTL = Integer.parseInt(strValeur);
                        break;
                    case "arrondiIBTR":
                        iArrondiTR = Integer.parseInt(strValeur);
                        break;
                    case "arrondiIBBL":
                        iArrondiBL = Integer.parseInt(strValeur);
                        break;
                    case "arrondiIBBR":
                        iArrondiBR = Integer.parseInt(strValeur);
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
                    case "masqueMeta":
                        setbReseauxSociauxMeta(strValeur.equals("true"));
                        break;
                    case "masqueGoogle":
                        // Ancien paramètre, redirigé vers Meta
                        setbReseauxSociauxMeta(strValeur.equals("true"));
                        break;
                    case "masqueFacebook":
                        // Ancien paramètre, redirigé vers Meta
                        setbReseauxSociauxMeta(strValeur.equals("true"));
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
                    case "calqueTitre":
                        setiCalqueTitre(Integer.parseInt(strValeur));
                        break;
                    case "calqueBarreClassique":
                        setiCalqueBarreClassique(Integer.parseInt(strValeur));
                        break;
                    case "calqueBarrePersonnalisee":
                        setiCalqueBarrePersonnalisee(Integer.parseInt(strValeur));
                        break;
                    case "calqueMasquage":
                        setiCalqueMasquage(Integer.parseInt(strValeur));
                        break;
                    case "calquePartage":
                        setiCalquePartage(Integer.parseInt(strValeur));
                        break;
                    case "calqueVisiteAuto":
                        setiCalqueVisiteAuto(Integer.parseInt(strValeur));
                        break;
                    case "calquePlan":
                        setiCalquePlan(Integer.parseInt(strValeur));
                        break;
                    case "calqueCarte":
                        setiCalqueCarte(Integer.parseInt(strValeur));
                        break;
                    case "calqueBoussole":
                        setiCalqueBoussole(Integer.parseInt(strValeur));
                        break;
                    case "calqueVignettes":
                        setiCalqueVignettes(Integer.parseInt(strValeur));
                        break;
                    case "calqueSuivPrec":
                        setiCalqueSuivPrec(Integer.parseInt(strValeur));
                        break;
                    case "calqueMenuPanoramiques":
                        setiCalqueMenuPanoramiques(Integer.parseInt(strValeur));
                        break;

                }
            }
        }
    }

    /**
     * Affiche l'interface selon le template actuel
     * 
     * <p>Applique visuellement le template configuré en créant et positionnant
     * tous les éléments d'interface :</p>
     * <ul>
     *   <li>Titres avec police, couleur, taille personnalisés</li>
     *   <li>Infobulles avec style configuré</li>
     *   <li>Barres d'outils (classique ou personnalisée)</li>
     *   <li>Carte, plan, boussole, vignettes</li>
     *   <li>Masques, réseaux sociaux, bouton visite auto</li>
     *   <li>Images de fond avec opacités</li>
     *   <li>Calques dans le bon ordre (z-order)</li>
     * </ul>
     * 
     * @throws IOException Si erreur de chargement des ressources
     * @see #setTemplate(List)
     * @see #strGetTemplate()
     * @see #reOrdonneElementsCalque()
     */
    public void afficheTemplate() throws IOException {
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation, lblTxtTitre, lblTxtTitre2, imgBoussole, imgAiguille, ivTwitter, ivMeta, ivEmail, apVisuVignettes, apVisuComboMenu, apVisuBoutonVisiteAuto, apVisuPlan, apVisuCarte, ivMasque, apFenetreAfficheInfo, lblFenetreURL, apAfficheBarrePersonnalisee);

        //lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        Color couleur = Color.valueOf(getStrCouleurFondTitre());
        int iRouge = (int) (couleur.getRed() * 255.d);
        int iBleu = (int) (couleur.getBlue() * 255.d);
        int iVert = (int) (couleur.getGreen() * 255.d);
        String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond);
        double taille = (double) getTitreTaille() / 100.d * getVisualisationWidth();
        lblTxtTitre.setMinWidth(taille);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - lblTxtTitre.getMinWidth()) / 2);
        Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte1);
        lblTxtTitre.setPrefHeight(-1);
        lblTxtTitre.setVisible(isbAfficheTitre());
        fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()) * 3.d / 4.d);
        lblTxtTitre2.setFont(fonte1);
        lblTxtTitre2.setPrefHeight(-1);
        lblTxtTitre2.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond);

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
        cbOmbreInfoBulle.setSelected(bOmbreInfoBulle);
        cpCouleurTextInfoBulle.setValue(Color.valueOf(strCouleurTexteInfoBulle));
        cpCouleurFondInfoBulle.setValue(Color.valueOf(strCouleurFondInfoBulle));
        cpCouleurBordureInfoBulle.setValue(Color.valueOf(strCouleurBordureInfoBulle));
        cbListePoliceInfoBulle.setValue(strPoliceInfoBulle);
        slTaillePoliceInfoBulle.setValue(taillePoliceInfoBulle);
        slOpaciteInfoBulle.setValue(opaciteInfoBulle);
        bdfTailleBordureLeft.setNumber(new BigDecimal(iTailleBordureLeft));
        bdfTailleBordureRight.setNumber(new BigDecimal(iTailleBordureRight));
        bdfTailleBordureTop.setNumber(new BigDecimal(iTailleBordureTop));
        bdfTailleBordureBottom.setNumber(new BigDecimal(iTailleBordureBottom));
        bdfArrondiBL.setNumber(new BigDecimal(iArrondiBL));
        bdfArrondiBR.setNumber(new BigDecimal(iArrondiBR));
        bdfArrondiTL.setNumber(new BigDecimal(iArrondiTL));
        bdfArrondiTR.setNumber(new BigDecimal(iArrondiTR));
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
            slOpaciteBarrePersonnalisee.setValue(getOpaciteBarrePersonnalisee());
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
        cbReseauxSociauxMeta.setSelected(isbReseauxSociauxMeta());
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
        cbAfficheRadarCarte.setSelected(isbAfficheRadarCarte());
        slOpaciteRadarCarte.setValue(getOpaciteRadarCarte());
        slTailleRadarCarte.setValue(getTailleRadarCarte());
        cpCouleurFondRadarCarte.setValue(getCouleurFondRadarCarte());
        cpCouleurLigneRadarCarte.setValue(getCouleurLigneRadarCarte());
        if (isbInternet() && navigateurCarteOL != null) {
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
        afficheFenetreAide();
        afficheFenetreInfo();
        afficheComboMenu();
        afficheBoutonVisiteAuto();
        poImageFond.setbValide(getiNombreImagesFond() > 0);
        ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
        ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
        ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        cbNiveauSuivPrec.getSelectionModel().select(getiCalqueSuivPrec() - 1);
        cbNiveauTitre.getSelectionModel().select(getiCalqueTitre() - 1);
        cbNiveauBarreClassique.getSelectionModel().select(getiCalqueBarreClassique() - 1);
        cbNiveauBarrePersonnalisee.getSelectionModel().select(getiCalqueBarrePersonnalisee() - 1);
        cbNiveauBoussole.getSelectionModel().select(getiCalqueBoussole() - 1);
        cbNiveauMasque.getSelectionModel().select(getiCalqueMasquage() - 1);
        cbNiveauPartage.getSelectionModel().select(getiCalquePartage() - 1);
        cbNiveauVignettes.getSelectionModel().select(getiCalqueVignettes() - 1);
        cbNiveauComboMenu.getSelectionModel().select(getiCalqueMenuPanoramiques() - 1);
        cbNiveauPlan.getSelectionModel().select(getiCalquePlan() - 1);
        cbNiveauCarte.getSelectionModel().select(getiCalqueCarte() - 1);
        cbNiveauVisiteAuto.getSelectionModel().select(getiCalqueVisiteAuto() - 1);
        reOrdonneElementsCalque();
        setbDejaSauve(true);
    }

    /**
     * Rafraîchit l'affichage complet de l'interface utilisateur.
     * 
     * Cette méthode centrale met à jour tous les éléments visuels de l'interface
     * pour refléter l'état actuel du projet :
     * <ul>
     * <li>Recharge la liste des panoramiques dans le ComboBox</li>
     * <li>Actualise la barre de navigation classique</li>
     * <li>Met à jour la boussole, les masques et les réseaux sociaux</li>
     * <li>Rafraîchit le plan, les vignettes et le menu contextuel</li>
     * <li>Synchronise la carte avec les marqueurs géolocalisés</li>
     * <li>Met à jour le bouton de visite automatique</li>
     * </ul>
     * 
     * Cette méthode doit être appelée après toute modification de la structure
     * du projet ou des paramètres d'affichage.
     * 
     * @see #afficheBarreClassique(String, int, int, int, String, String, double)
     * @see #afficheCarte()
     * @see #affichePlan()
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
        if (isbInternet() && navigateurCarteOL != null) {
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
            }
            afficheCarte();
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

    private void afficheImagesFondInterface() {
        afficheImagesFondInterface(false);
    }

    /**
     *
     */
    private void afficheImagesFondInterface(boolean bNouvelle) {

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
        double hauteurPanel = 500;
        apImageFond.setPrefHeight(getiNombreImagesFond() * (hauteurPanel + 10) + 100);
        apImageFond.setMinHeight(getiNombreImagesFond() * (hauteurPanel + 10) + 100);
        apImageFond.setMaxHeight(getiNombreImagesFond() * (hauteurPanel + 10) + 100);
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            int ij = i;
            ToggleGroup tgType = new ToggleGroup();
            ToggleGroup tgLien = new ToggleGroup();

            AnchorPane apImagesFond = new AnchorPane();
            apImagesFond.setPrefWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMinWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMaxWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setPrefHeight(hauteurPanel);
            apImagesFond.setStyle("-fx-border-color : #666666; -fx-border-width : 1px; -fx-border-style :solid;");
            apImagesFond.setLayoutY(i * (hauteurPanel + 10) + 60);
            ImageView ivImageFond = new ImageView(getImagesFond()[i].getImgFond());
            ivImageFond.setPreserveRatio(true);
            if (getImagesFond()[i].getImgFond().getWidth() > getImagesFond()[i].getImgFond().getHeight()) {
                ivImageFond.setFitWidth(100);
            } else {
                ivImageFond.setFitHeight(100);
            }
            ComboBox cbNiveauImageFond = new ComboBox();
            listeNiveau.stream().forEach((nomNiveau) -> {
                cbNiveauImageFond.getItems().add(nomNiveau);
            });
            cbNiveauImageFond.getSelectionModel().select(getiCalqueBarreClassique() - 1);
            cbNiveauImageFond.setLayoutX(vbOutils.getPrefWidth() - 130);
            cbNiveauImageFond.setLayoutY(10);
            cbNiveauImageFond.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
                getImagesFond()[ij].setiCalqueImage((int) new_val + 1);
                reOrdonneElementsCalque();
            });

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

            int iPosX = 160;
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
            lblPosit.setLayoutX(135);
            lblPosit.setLayoutY(10);
            CheckBox cbMasquableImageFond = new CheckBox(rbLocalisation.getString("interface.masquableImageFond"));
            cbMasquableImageFond.setLayoutX(150);
            cbMasquableImageFond.setLayoutY(90);
            cbMasquableImageFond.setSelected(getImagesFond()[i].isMasquable());
            Button btnModifieImage = new Button(rbLocalisation.getString("interface.changeImage"));
            btnModifieImage.setLayoutX(50);
            btnModifieImage.setLayoutY(120);
            btnModifieImage.setPrefSize(120, 20);
            Button btnSupprimeImage = new Button(rbLocalisation.getString("interface.supprimeImage"));
            btnSupprimeImage.setLayoutX(180);
            btnSupprimeImage.setLayoutY(120);
            btnSupprimeImage.setPrefSize(120, 20);
            Label lblOffsetXImageFond = new Label("dX ");
            lblOffsetXImageFond.setLayoutX(25);
            lblOffsetXImageFond.setLayoutY(155);
            Label lblOffsetYImageFond = new Label("dY ");
            lblOffsetYImageFond.setLayoutX(175);
            lblOffsetYImageFond.setLayoutY(155);
            BigDecimalField bdfOffsetXImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetX()));
            bdfOffsetXImageFond.setLayoutX(50);
            bdfOffsetXImageFond.setLayoutY(150);
            bdfOffsetXImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetXImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetXImageFond.setMaxWidth(100);
            BigDecimalField bdfOffsetYImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetY()));
            bdfOffsetYImageFond.setLayoutX(200);
            bdfOffsetYImageFond.setLayoutY(150);
            bdfOffsetYImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetYImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetYImageFond.setMaxWidth(100);

            Label lblOpaciteImageFond = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
            lblOpaciteImageFond.setLayoutX(10);
            lblOpaciteImageFond.setLayoutY(190);
            Slider slOpaciteImageFond = new Slider(0, 1.0, getImagesFond()[i].getOpacite());
            slOpaciteImageFond.setLayoutX(120);
            slOpaciteImageFond.setLayoutY(190);
            Label lblTailleImageFond = new Label(rbLocalisation.getString("interface.tailleVignettes"));
            lblTailleImageFond.setLayoutX(10);
            lblTailleImageFond.setLayoutY(220);
            double echelle = getImagesFond()[i].getTailleX() / getImagesFond()[i].getImgFond().getWidth();
            Slider slTailleImageFond = new Slider(0.1, 2.0, echelle);
            slTailleImageFond.setLayoutX(120);
            slTailleImageFond.setLayoutY(220);
            RadioButton rbHTML = new RadioButton("HTML");
            rbHTML.setToggleGroup(tgType);
            rbHTML.setLayoutX(10);
            rbHTML.setLayoutY(280);
            rbHTML.setSelected(getImagesFond()[i].getStrType().equals("html"));
            rbHTML.setUserData("html");

            Label lblUrlImageFond = new Label("url");
            lblUrlImageFond.setLayoutX(10);
            lblUrlImageFond.setLayoutY(312);
            TextField tfUrlImageFond = new TextField(getImagesFond()[i].getStrUrl());
            tfUrlImageFond.setPrefHeight(20);
            tfUrlImageFond.setPrefWidth(200);
            tfUrlImageFond.setLayoutX(120);
            tfUrlImageFond.setLayoutY(310);
            Label lblInfobulleImageFond = new Label(rbLocalisation.getString("interface.infobulle"));
            lblInfobulleImageFond.setLayoutX(10);
            lblInfobulleImageFond.setLayoutY(252);
            TextField tfInfobulleImageFond = new TextField(getImagesFond()[i].getStrInfobulle());
            tfInfobulleImageFond.setPrefHeight(20);
            tfInfobulleImageFond.setPrefWidth(200);
            tfInfobulleImageFond.setLayoutX(120);
            tfInfobulleImageFond.setLayoutY(250);
            tfUrlImageFond.disableProperty().bind(rbHTML.selectedProperty().not());
            //tfInfobulleImageFond.disableProperty().bind(rbHTML.selectedProperty().not());
            RadioButton rbDiapo = new RadioButton("Diaporama");
            rbDiapo.setToggleGroup(tgType);
            rbDiapo.setLayoutX(10);
            rbDiapo.setLayoutY(340);
            rbDiapo.setSelected(getImagesFond()[i].getStrType().equals("diapo"));
            rbDiapo.setUserData("diapo");
            ComboBox cbListeDiapos = new ComboBox();
            cbListeDiapos.setLayoutX(120);
            cbListeDiapos.setLayoutY(370);
            for (int ii = 0; ii < EditeurPanovisu.getiNombreDiapo(); ii++) {
                cbListeDiapos.getItems().add(EditeurPanovisu.diaporamas[ii].getStrNomDiaporama());
            }
            cbListeDiapos.getSelectionModel().select(getImagesFond()[i].getiNumDiapo());
            RadioButton rbAucun = new RadioButton("Image décorative");
            rbAucun.setToggleGroup(tgType);
            rbAucun.setLayoutX(10);
            rbAucun.setLayoutY(400);
            rbAucun.setSelected(getImagesFond()[i].getStrType().equals("aucun"));
            rbAucun.setUserData("aucun");
            Label lblCible = new Label(rbLocalisation.getString("interface.lienCible"));
            lblCible.setLayoutX(10);
            lblCible.setLayoutY(430);
            RadioButton rbInterne = new RadioButton(rbLocalisation.getString("interface.lienInterne"));
            rbInterne.setToggleGroup(tgLien);
            rbInterne.setLayoutX(60);
            rbInterne.setLayoutY(460);
            rbInterne.setSelected(getImagesFond()[i].getStrCible().equals("interne"));
            rbInterne.setUserData("interne");
            RadioButton rbPage = new RadioButton(rbLocalisation.getString("interface.lienPage"));
            rbPage.setToggleGroup(tgLien);
            rbPage.setLayoutX(60);
            rbPage.setLayoutY(490);
            rbPage.setSelected(getImagesFond()[i].getStrCible().equals("page"));
            rbPage.setUserData("page");

            apImagesFond.getChildren().addAll(
                    ivImageFond,
                    lblPosit, cbNiveauImageFond,
                    rbImageFondTopLeft, rbImageFondTopCenter, rbImageFondTopRight,
                    rbImageFondMiddleLeft, rbImageFondMiddleCenter, rbImageFondMiddleRight,
                    rbImageFondBottomLeft, rbImageFondBottomCenter, rbImageFondBottomRight,
                    cbMasquableImageFond,
                    btnModifieImage, btnSupprimeImage,
                    lblOffsetXImageFond, bdfOffsetXImageFond, lblOffsetYImageFond, bdfOffsetYImageFond,
                    lblOpaciteImageFond, slOpaciteImageFond,
                    lblTailleImageFond, slTailleImageFond,
                    rbHTML,
                    lblUrlImageFond, tfUrlImageFond,
                    lblInfobulleImageFond, tfInfobulleImageFond,
                    rbDiapo,
                    cbListeDiapos,
                    rbAucun,
                    lblCible,
                    rbInterne,
                    rbPage
            );
            btnModifieImage.setOnMouseClicked(
                    (me) -> {
                        if (getiNombrePanoramiques() != 0) {
                            setbDejaSauve(false);
                            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                        }

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

                            getImagesFond()[ij].setStrFichierImage(fileFichierImage.getAbsolutePath());
                            Image imgFond = new Image("file:" + fileFichierImage.getAbsolutePath());
                            getImagesFond()[ij].setImgFond(imgFond);
                            getImagesFond()[ij].setTailleX((int) imgFond.getWidth());
                            getImagesFond()[ij].setTailleY((int) imgFond.getHeight());
                            afficheImagesFondInterface();
                        }

                    }
            );

            btnSupprimeImage.setOnMouseClicked(
                    (me) -> {
                        ButtonType buttonTypeOui = new ButtonType(rbLocalisation.getString("main.oui"));
                        ButtonType buttonTypeNon = new ButtonType(rbLocalisation.getString("main.non"));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(rbLocalisation.getString("interface.supprimeImage"));
                        alert.setHeaderText(null);
                        alert.setContentText(rbLocalisation.getString("diapo.supprimerImageTexte"));
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);
                        Optional<ButtonType> actReponse = alert.showAndWait();

                        if (actReponse.get() == buttonTypeOui) {
                            if (getiNombrePanoramiques() != 0) {
                                setbDejaSauve(false);
                                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                            }

                            retireImageFond(ij);
                        }
                    }
            );
            tgLien.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                if (tgLien.getSelectedToggle() != null) {
                    getImagesFond()[ij].setStrCible(tgLien.getSelectedToggle().getUserData().toString());
                }
            });

            tgType.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                if (tgType.getSelectedToggle() != null) {
                    getImagesFond()[ij].setStrType(tgType.getSelectedToggle().getUserData().toString());
                    switch (tgType.getSelectedToggle().getUserData().toString()) {
                        case "diapo":
                            getImagesFond()[ij].setStrUrl("diaporama/diapo" + cbListeDiapos.getSelectionModel().getSelectedIndex() + ".html");
                            getImagesFond()[ij].setiNumDiapo(cbListeDiapos.getSelectionModel().getSelectedIndex());
                            break;
                        case "html":
                            getImagesFond()[ij].setStrUrl(tfUrlImageFond.getText());
                            break;
                        case "aucun":
                            getImagesFond()[ij].setStrUrl("");
                            break;
                        default:
                            break;
                    }
                }
            }
            );

            cbListeDiapos.getSelectionModel().selectedIndexProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                getImagesFond()[ij].setStrUrl("diaporama/diapo" + cbListeDiapos.getSelectionModel().getSelectedIndex() + ".html");
                getImagesFond()[ij].setiNumDiapo(cbListeDiapos.getSelectionModel().getSelectedIndex());
            });

            tgPosition.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (tgPosition.getSelectedToggle() != null) {
                    if (getiNombrePanoramiques() != 0) {
                        setbDejaSauve(false);
                        getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                    }

                    String positImageFond = tgPosition.getSelectedToggle().getUserData().toString();
                    getImagesFond()[ij].setStrPosX(positImageFond.split(":")[1]);
                    getImagesFond()[ij].setStrPosY(positImageFond.split(":")[0]);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });

            bdfOffsetXImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                getImagesFond()[ij].setOffsetX(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });

            bdfOffsetYImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                getImagesFond()[ij].setOffsetY(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });

            slOpaciteImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                if (newValue != null) {
                    double opac = (double) newValue;
                    getImagesFond()[ij].setOpacite(opac);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });

            slTailleImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                if (newValue != null) {
                    double taille = (double) newValue;
                    getImagesFond()[ij].setTailleX((int) (getImagesFond()[ij].getImgFond().getWidth() * taille));
                    getImagesFond()[ij].setTailleY((int) (getImagesFond()[ij].getImgFond().getHeight() * taille));
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });

            tfUrlImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                String txt = newValue;
                getImagesFond()[ij].setStrUrl(txt);
            });

            tfInfobulleImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                String txt = newValue;
                getImagesFond()[ij].setStrInfobulle(txt);
            });

            cbMasquableImageFond.selectedProperty().addListener((ov, old_val, new_val) -> {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }

                if (new_val != null) {
                    getImagesFond()[ij].setMasquable(new_val);
                }
            });

            apImageFond.getChildren().add(apImagesFond);
        }

        if (bNouvelle) {
            getSpOutils().setVvalue(getSpOutils().getVvalue() + 500);
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
                afficheImagesFondInterface(true);
            }
        }
        poImageFond.setbValide(getiNombreImagesFond() > 0);
    }

    /**
     * Applique le style CSS personnalisé à l'infobulle (label).
     * 
     * Construit et applique une chaîne de style CSS complète pour l'infobulle
     * en fonction des propriétés configurées :
     * <ul>
     * <li>Couleurs : fond, bordure, texte</li>
     * <li>Dimensions : tailles de bordure (top, right, bottom, left)</li>
     * <li>Arrondis : rayons des coins (TL, TR, BR, BL)</li>
     * <li>Typographie : police et taille de police</li>
     * <li>Opacité générale</li>
     * </ul>
     * 
     * Cette méthode doit être appelée après toute modification des propriétés
     * visuelles de l'infobulle pour que les changements soient visibles.
     * 
     * @see #lblInfoBulle
     */
    public void styleInfoBulle() {
        String strStyle = "-fx-margin : 10px;-fx-background-color: " + strCouleurFondInfoBulle + ";"
                + "-fx-border-width: " + iTailleBordureTop + "px " + iTailleBordureRight + "px "
                + iTailleBordureBottom + "px " + iTailleBordureLeft + "px ;"
                + "-fx-border-color: " + strCouleurBordureInfoBulle + ";"
                + "-fx-padding: 3px 10px;"
                + "-fx-opacity: " + opaciteInfoBulle + ";"
                + "-fx-text-fill : " + strCouleurTexteInfoBulle + ";"
                + "-fx-font-family: \"" + strPoliceInfoBulle + "\",Arial,sans-serif;  "
                + "-fx-border-radius: " + iArrondiTL + "px  " + iArrondiTR
                + "px " + iArrondiBR + "px " + iArrondiBL + "px;  "
                + "-fx-font-size : " + taillePoliceInfoBulle + "px;";
        lblInfoBulle.setStyle(strStyle);
    }

    /**
     * Crée et initialise l'interface graphique principale de l'éditeur.
     * 
     * Cette méthode fondamentale construit toute l'interface utilisateur en :
     * <ul>
     * <li>Initialisant les listes de polices et les ressources de localisation</li>
     * <li>Définissant les répertoires des thèmes (navigation, hotspots, boussoles, masques, réseaux sociaux)</li>
     * <li>Chargeant les images et icônes (visite auto, navigation, luminosité)</li>
     * <li>Créant les labels de titres avec leurs styles (polices, couleurs, opacité)</li>
     * <li>Construisant la structure de panneaux (visualisation, outils, interface)</li>
     * <li>Configurant les dimensions et la disposition des composants</li>
     * <li>Initialisant les ScrollPanes et les conteneurs principaux</li>
     * </ul>
     * 
     * Cette méthode est appelée une seule fois au démarrage de l'application
     * pour construire l'interface de base avant le chargement d'un projet.
     * 
     * @param iLargeur largeur totale de l'interface en pixels
     * @param iHauteur hauteur totale de l'interface en pixels
     * 
     * @see #apVisualisation
     * @see #vbOutils
     * @see #paneTabInterface
     */
    public void creeInterface(int iLargeur, int iHauteur) {
        List<String> strLstPolices = new ArrayList<>();
        strLstPolices.add("Arial");
        strLstPolices.add("Arial Black");
        strLstPolices.add("Comic Sans MS");
        strLstPolices.add("Courrier New");
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
        ivBtnVisiteAuto = new ImageView(new Image("file:" + getStrRepertAppli() + File.separator + "images/playAutoTour.png"));
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

        lblTxtTitre.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond);
        lblTxtTitre2.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulFond);


        paneTabInterface = new Pane();
        hbInterface = new HBox();
        hbInterface.setPrefWidth(iLargeur);
        hbInterface.setPrefHeight(iHauteur);
        paneTabInterface.getChildren().add(hbInterface);
        setApVis(new AnchorPane());
        getApVis().setPrefWidth(iLargeur - largeurOutils - 20);
        getApVis().setMaxWidth(iLargeur - largeurOutils - 20);
        getApVis().setMinWidth(iLargeur - largeurOutils - 20);
        getApVis().setPrefHeight(iHauteur);

        apVisualisation = new AnchorPane();
        getApVis().getChildren().add(apVisualisation);
        apVisualisation.setPrefWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setMaxWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setMinWidth(iLargeur - largeurOutils - 20);
        apVisualisation.setPrefHeight(iHauteur);
        vbOutils = new VBox(-5);
        vbOutils.setLayoutX(5);
        AnchorPane apOutils = new AnchorPane();
        apOutils.setPrefWidth(largeurOutils);
        apOutils.setMaxWidth(largeurOutils);
        apOutils.setTranslateY(3);
        apOutils.setTranslateX(20);
        setSpOutils(new ScrollPane(vbOutils));
        apOutils.getChildren().addAll(getSpOutils());
        getSpOutils().setId("spOutils");
        getSpOutils().setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        getSpOutils().setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        getSpOutils().setPrefHeight(iHauteur - 52 - getiDecalageMac());
        getSpOutils().setMinHeight(iHauteur - 52 - getiDecalageMac());
        getSpOutils().setMaxHeight(iHauteur - 52 - getiDecalageMac());
        getSpOutils().setPrefWidth(largeurOutils);
        getSpOutils().setMinWidth(largeurOutils);
        getSpOutils().setFitToWidth(true);
        getSpOutils().setFitToHeight(true);

        getSpOutils().setLayoutY(-getiDecalageMac());
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        apCalque = apAfficheCalques();
        getApVis().getChildren().add(apCalque);

        hbInterface.getChildren().addAll(getApVis(), apOutils);
        /*
         * ***************************************************************
         *     Panneau de visualisation de l'interface
         * ***************************************************************
         */
        // Calcul dynamique de la taille en fonction de la fenêtre (90% de la largeur disponible)
        double tailleMax = apVisualisation.getPrefWidth() * 0.9;
        double hauteurMax = iHauteur * 0.85; // 85% de la hauteur de la fenêtre
        
        ivVisualisation = new ImageView(imgClaire);
        ivVisualisation.setPreserveRatio(true); // Garde le ratio de l'image
        
        // Adapter la taille pour remplir au maximum l'espace disponible
        ivVisualisation.setFitWidth(tailleMax);
        
        // Si l'image est trop haute, on limite par la hauteur
        double hauteurCalculee = ivVisualisation.getImage().getHeight() * (tailleMax / ivVisualisation.getImage().getWidth());
        if (hauteurCalculee > hauteurMax) {
            ivVisualisation.setFitHeight(hauteurMax);
            ivVisualisation.setFitWidth(0); // Laisser le ratio calculer la largeur
        }
        
        ivVisualisation.setSmooth(true);
        double LX = (apVisualisation.getPrefWidth() - ivVisualisation.getBoundsInParent().getWidth()) / 2;
        ivVisualisation.setLayoutX(LX);
        ivVisualisation.setLayoutY(20);
        lblTxtTitre.setMinSize(ivVisualisation.getBoundsInParent().getWidth(), 30);
        lblTxtTitre.setPadding(new Insets(5));
        lblTxtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        lblTxtTitre.setAlignment(Pos.CENTER);
        //lblTxtTitre.setTextFill(Color.WHITE);
        lblTxtTitre.setLayoutY(20);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getBoundsInParent().getWidth() - lblTxtTitre.getMinWidth()) / 2);
        Color col1 = Color.valueOf(getStrCouleurFondTitre());
        double iR1 = (int) (col1.getRed() * 255.d);
        double iB1 = (int) (col1.getBlue() * 255.d);
        double iV1 = (int) (col1.getGreen() * 255.d);
        String strCoulF1 = "rgba(" + iR1 + "," + iV1 + "," + iB1 + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulF1);
        lblTxtTitre2.setStyle("-fx-text-fill : " + getStrCouleurTitre() + ";-fx-background-color : " + strCoulF1);

        lblTxtTitre2.setVisible(false);
        rbClair = new RadioButton("Image claire");
        rbSombre = new RadioButton("Image Sombre");
        rbPerso = new RadioButton("");
        cbImage = new ComboBox();
        cbImage.setDisable(true);
        double positRB = ivVisualisation.getBoundsInParent().getHeight() + 30;
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
        ivMeta = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxMeta()));
        ivEmail = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxEmail()));
        apVisuVignettes = new AnchorPane();
        apVisuPlan = new AnchorPane();
        apVisuCarte = new AnchorPane();
        apVisuComboMenu = new AnchorPane();
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().add(ivVisualisation);
        apVisualisation.getChildren().addAll(lblTxtTitre, imgBoussole, imgAiguille, ivMasque, ivTwitter, ivMeta, ivEmail, apVisuVignettes, apVisuComboMenu, paneFondSuivant, paneFondPrecedent, apAfficheBarrePersonnalisee, apVisuBoutonVisiteAuto);
        paneFondPrecedent.setPrefWidth(64);
        paneFondPrecedent.setPrefHeight(64);
        paneFondSuivant.setPrefWidth(64);
        paneFondSuivant.setPrefHeight(64);
        paneFondPrecedent.setMaxWidth(64);
        paneFondPrecedent.setMaxHeight(64);
        paneFondSuivant.setMaxWidth(64);
        paneFondSuivant.setMaxHeight(64);
        paneFondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        paneFondPrecedent.setLayoutY(ivVisualisation.getLayoutY() + (getVisualisationHeight() - paneFondPrecedent.getPrefHeight()) / 2);
        paneFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (getVisualisationWidth() - paneFondPrecedent.getPrefWidth()));
        paneFondSuivant.setLayoutY(ivVisualisation.getLayoutY() + (getVisualisationHeight() - paneFondSuivant.getPrefHeight()) / 2);
        paneFondSuivant.setVisible(isbSuivantPrecedent());
        paneFondPrecedent.setVisible(isbSuivantPrecedent());
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
        for (int i = 0; i < iNombreCalques; i++) {
            listeNiveau.add(rbLocalisation.getString("interface.calque") + " " + (i + 1));
        }

        cpCouleurFondTheme = new ColorPicker(getCouleurFondTheme());
        cpCouleurTexteTheme = new ColorPicker(couleurTexteTheme);
        slOpaciteTheme = new Slider(0, 1, getOpaciteTheme());
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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauSuivPrec.getItems().add(nomNiveau);
        });
        cbNiveauSuivPrec.getSelectionModel().select(getiCalqueSuivPrec() - 1);
        cbNiveauSuivPrec.setLayoutX(largeur - 110);
        cbNiveauSuivPrec.setLayoutY(135);
        cbNiveauSuivPrec.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueSuivPrec((int) new_val + 1);
            reOrdonneElementsCalque();
        });
        cbNiveauSuivPrec.disableProperty().bind(cbSuivantPrecedent.selectedProperty().not());
        apParametresTheme.setPrefHeight(160);
        apParametresTheme.getChildren().addAll(
                lblCouleurFondTheme, cpCouleurFondTheme,
                lblCouleurTexteTheme, cpCouleurTexteTheme,
                lblOpaciteTheme, slOpaciteTheme,
                lblPoliceTheme, cbPoliceTheme,
                cbSuivantPrecedent, cbNiveauSuivPrec
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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauTitre.getItems().add(nomNiveau);
        });
        cbNiveauTitre.getSelectionModel().select(getiCalqueTitre() - 1);
        cbNiveauTitre.setLayoutX(largeur - 110);
        cbNiveauTitre.setLayoutY(10);
        cbNiveauTitre.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueTitre((int) new_val + 1);
            reOrdonneElementsCalque();
        });
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
        cbNiveauTitre.disableProperty().bind(cbAfficheTitre.selectedProperty().not());
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
                apTit1, cbNiveauTitre, cbAfficheTitre
        );
        
        /*
         * *****************************************
         *     Panel Description 
         * ****************************************
         */
        AnchorPane apDescription = new AnchorPane();
        apDescription.setPrefHeight(60);
        poDescription = new PaneOutil(rbLocalisation.getString("interface.description"), apDescription, largeur);
        AnchorPane apDESC = new AnchorPane(poDescription.getApPaneOutil());
        
        cbAfficheDescription = new CheckBox(rbLocalisation.getString("interface.afficheDescriptionChargement"));
        cbAfficheDescription.setSelected(isbAfficheDescription());
        cbAfficheDescription.setLayoutX(10);
        cbAfficheDescription.setLayoutY(15);
        cbAfficheDescription.selectedProperty().addListener((ov, old_val, new_val) -> {
            // Ignore les changements pendant le chargement des données
            if (!bChargementEnCours && getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                // Met à jour l'objet Panoramique
                getPanoramiquesProjet()[getiPanoActuel()].setAffDescription(new_val);
            }
            if (!bChargementEnCours) {
                setbAfficheDescription(new_val);
                poDescription.setbValide(new_val);
            }
        });
        poDescription.setbValide(isbAfficheDescription());
        
        apDescription.getChildren().addAll(cbAfficheDescription);
        
        /*
         * *****************************************
         *     Panel Infobulles 
         * ****************************************
         */
        AnchorPane apInfoBulle = new AnchorPane();
        apInfoBulle.setPrefHeight(550);
        PaneOutil poInfoBulle = new PaneOutil(true, rbLocalisation.getString("interface.styleInfoBulle"), apInfoBulle, largeur);
        AnchorPane apIB = new AnchorPane(poInfoBulle.getApPaneOutil());
        lblInfoBulle.setLayoutX(30);
        lblInfoBulle.setLayoutY(10);
        styleInfoBulle();
        cbOmbreInfoBulle = new CheckBox(rbLocalisation.getString("interface.ombreInfoBulle"));
        cbOmbreInfoBulle.setSelected(bOmbreInfoBulle);
        cbOmbreInfoBulle.setLayoutX(10);
        cbOmbreInfoBulle.setLayoutY(60);
        cbListePoliceInfoBulle = new ComboBox(strListePolices);
        cbListePoliceInfoBulle.setValue(strPoliceInfoBulle);
        cbListePoliceInfoBulle.setLayoutX(200);
        cbListePoliceInfoBulle.setLayoutY(87);
        cbListePoliceInfoBulle.setMaxWidth(135);

        Label lblChoixPoliceInfoBulle = new Label(rbLocalisation.getString("interface.choixPolice"));
        lblChoixPoliceInfoBulle.setLayoutX(10);
        lblChoixPoliceInfoBulle.setLayoutY(90);
        Label lblChoixTailleInfoBulle = new Label(rbLocalisation.getString("interface.choixTaillePolice"));
        lblChoixTailleInfoBulle.setLayoutX(10);
        lblChoixTailleInfoBulle.setLayoutY(120);
        slTaillePoliceInfoBulle = new Slider(8, 24, taillePoliceInfoBulle);
        slTaillePoliceInfoBulle.setLayoutX(200);
        slTaillePoliceInfoBulle.setLayoutY(120);

        Label lblChoixCouleurInfoBulle = new Label(rbLocalisation.getString("interface.choixCouleur"));
        lblChoixCouleurInfoBulle.setLayoutX(10);
        lblChoixCouleurInfoBulle.setLayoutY(150);
        cpCouleurTextInfoBulle = new ColorPicker(Color.valueOf(strCouleurTexteInfoBulle));
        cpCouleurTextInfoBulle.setLayoutX(180);
        cpCouleurTextInfoBulle.setLayoutY(148);
        Label lblChoixCouleurFondInfoBulle = new Label(rbLocalisation.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondInfoBulle.setLayoutX(10);
        lblChoixCouleurFondInfoBulle.setLayoutY(180);
        cpCouleurFondInfoBulle = new ColorPicker(Color.valueOf(strCouleurFondInfoBulle));
        cpCouleurFondInfoBulle.setLayoutX(180);
        cpCouleurFondInfoBulle.setLayoutY(178);
        Label lblChoixCouleurBordureInfoBulle = new Label(rbLocalisation.getString("interface.choixCouleurBordure"));
        lblChoixCouleurBordureInfoBulle.setLayoutX(10);
        lblChoixCouleurBordureInfoBulle.setLayoutY(210);
        cpCouleurBordureInfoBulle = new ColorPicker(Color.valueOf(strCouleurBordureInfoBulle));
        cpCouleurBordureInfoBulle.setLayoutX(180);
        cpCouleurBordureInfoBulle.setLayoutY(208);
        Label lblOpaciteInfoBulle = new Label(rbLocalisation.getString("interface.opaciteInfoBulle"));
        lblOpaciteInfoBulle.setLayoutX(10);
        lblOpaciteInfoBulle.setLayoutY(240);
        slOpaciteInfoBulle = new Slider(0, 1.0, opaciteInfoBulle);
        slOpaciteInfoBulle.setLayoutX(200);
        slOpaciteInfoBulle.setLayoutY(240);

        Label lblTailleBordureInfoBulle = new Label(rbLocalisation.getString("interface.tailleBordure"));
        lblTailleBordureInfoBulle.setLayoutX(10);
        lblTailleBordureInfoBulle.setLayoutY(270);
        Rectangle rectTaille = new Rectangle(0, 0, 150, 40);
        rectTaille.setLayoutX(93);
        rectTaille.setLayoutY(334);
        rectTaille.setFill(Color.WHITE);
        rectTaille.setStroke(Color.BLACK);
        rectTaille.setStrokeWidth(1.0);
        Label lblRayonBordureInfoBulle = new Label(rbLocalisation.getString("interface.rayonBordure"));
        lblRayonBordureInfoBulle.setLayoutX(10);
        lblRayonBordureInfoBulle.setLayoutY(430);
        Rectangle rectRayon = new Rectangle(0, 0, 150, 40);
        rectRayon.setLayoutX(93);
        rectRayon.setLayoutY(470);
        rectRayon.setFill(Color.WHITE);
        rectRayon.setStroke(Color.BLACK);

        bdfTailleBordureTop = new BigDecimalField(new BigDecimal(iTailleBordureTop));
        bdfTailleBordureTop.setLayoutX(130);
        bdfTailleBordureTop.setLayoutY(300);
        bdfTailleBordureTop.setMaxValue(new BigDecimal(20));
        bdfTailleBordureTop.setMinValue(new BigDecimal(0));
        bdfTailleBordureTop.setMaxWidth(70);
        bdfTailleBordureBottom = new BigDecimalField(new BigDecimal(iTailleBordureBottom));
        bdfTailleBordureBottom.setLayoutX(130);
        bdfTailleBordureBottom.setLayoutY(380);
        bdfTailleBordureBottom.setMaxValue(new BigDecimal(20));
        bdfTailleBordureBottom.setMinValue(new BigDecimal(0));
        bdfTailleBordureBottom.setMaxWidth(70);
        bdfTailleBordureLeft = new BigDecimalField(new BigDecimal(iTailleBordureLeft));
        bdfTailleBordureLeft.setLayoutX(10);
        bdfTailleBordureLeft.setLayoutY(340);
        bdfTailleBordureLeft.setMaxValue(new BigDecimal(20));
        bdfTailleBordureLeft.setMinValue(new BigDecimal(0));
        bdfTailleBordureLeft.setMaxWidth(70);
        bdfTailleBordureRight = new BigDecimalField(new BigDecimal(iTailleBordureRight));
        bdfTailleBordureRight.setLayoutX(250);
        bdfTailleBordureRight.setLayoutY(340);
        bdfTailleBordureRight.setMaxValue(new BigDecimal(20));
        bdfTailleBordureRight.setMinValue(new BigDecimal(0));
        bdfTailleBordureRight.setMaxWidth(70);

        bdfArrondiTL = new BigDecimalField(new BigDecimal(iArrondiTL));
        bdfArrondiTL.setLayoutX(10);
        bdfArrondiTL.setLayoutY(460);
        bdfArrondiTL.setMaxValue(new BigDecimal(20));
        bdfArrondiTL.setMinValue(new BigDecimal(0));
        bdfArrondiTL.setMaxWidth(70);

        bdfArrondiBL = new BigDecimalField(new BigDecimal(iArrondiBL));
        bdfArrondiBL.setLayoutX(10);
        bdfArrondiBL.setLayoutY(500);
        bdfArrondiBL.setMaxValue(new BigDecimal(20));
        bdfArrondiBL.setMinValue(new BigDecimal(0));
        bdfArrondiBL.setMaxWidth(70);

        bdfArrondiTR = new BigDecimalField(new BigDecimal(iArrondiTR));
        bdfArrondiTR.setLayoutX(250);
        bdfArrondiTR.setLayoutY(460);
        bdfArrondiTR.setMaxValue(new BigDecimal(20));
        bdfArrondiTR.setMinValue(new BigDecimal(0));
        bdfArrondiTR.setMaxWidth(70);

        bdfArrondiBR = new BigDecimalField(new BigDecimal(iArrondiBR));
        bdfArrondiBR.setLayoutX(250);
        bdfArrondiBR.setLayoutY(500);
        bdfArrondiBR.setMaxValue(new BigDecimal(20));
        bdfArrondiBR.setMinValue(new BigDecimal(0));
        bdfArrondiBR.setMaxWidth(70);

        apInfoBulle.getChildren().addAll(lblInfoBulle,
                cbOmbreInfoBulle,
                lblChoixPoliceInfoBulle, cbListePoliceInfoBulle,
                lblChoixTailleInfoBulle, slTaillePoliceInfoBulle,
                lblChoixCouleurInfoBulle, cpCouleurTextInfoBulle,
                lblChoixCouleurFondInfoBulle, cpCouleurFondInfoBulle,
                lblChoixCouleurBordureInfoBulle, cpCouleurBordureInfoBulle,
                lblOpaciteInfoBulle, slOpaciteInfoBulle,
                lblTailleBordureInfoBulle, rectTaille,
                bdfTailleBordureTop, bdfTailleBordureBottom, bdfTailleBordureLeft, bdfTailleBordureRight,
                lblRayonBordureInfoBulle, rectRayon,
                bdfArrondiTL, bdfArrondiBL, bdfArrondiTR, bdfArrondiBR
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
         * *************************************************
         *     Panel HotSpots Panoramiques
         * *************************************************
        
         */
        int iIconesParLigne = 6;  // Réduit de 9 à 6 pour l'espacement
        
        // AnchorPane pour les icônes seulement
        AnchorPane apIconesHS1Content = new AnchorPane();
        // Calcul correct du nombre de lignes : arrondi supérieur de (nombre / par ligne)
        int iNombreLignes = (int) Math.ceil((double) iNombreHotSpots / iIconesParLigne);
        apIconesHS1Content.setPrefHeight(45.d * iNombreLignes + 50);
        apIconesHS1Content.setPadding(new Insets(PANEL_TOP_MARGIN, 5, 5, 15));  // Espacement haut + décalage gauche
        
        // Déterminer la couleur de fond selon le thème
        boolean estThemeSombre = ThemeManager.getCurrentTheme().isDark();
        String couleurFond = estThemeSombre ? "#3a3a3a" : "#e8e8e8";  // Plus clair si sombre, plus sombre si clair
        apIconesHS1Content.setStyle("-fx-background-color: " + couleurFond + "; -fx-background-radius: 5px;");
        
        int i = 0;
        double xHS;
        double yHS = 25;
        for (String strNomImage : strListeHotSpots) {
            String strExtension = strNomImage.substring(strNomImage.length() - 3, strNomImage.length());
            Pane paneFond = new Pane();
            ivHotspots[i] = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomImage, -1, 30, true, true, true));

            int iCol = i % iIconesParLigne;
            int iRow = i / iIconesParLigne;
            xHS = iCol * 50 + 5;  // Espacement horizontal
            yHS = iRow * 45 + 25;  // Espacement vertical
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                // Ne supprimer que l'ImageView panoramique qui va être recréée
                apVisualisation.getChildren().remove(ivHotSpotPanoramique);
                setStrStyleHotSpots(strNomImage);
                strTypeHS = strExtension.toLowerCase();
                cpCouleurHotspotsPanoramique.setDisable(strTypeHS.equals("gif"));

                strNomfichierHS = strNomImage;
                // Ne pas appeler afficheBarreClassique qui ajoute les ImageViews, juste recharger les images
                chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());

                changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                
                // Recréer l'ImageView avec l'image colorée (pour apVisualisation)
                if (!strTypeHS.equals("gif")) {
                    ivHotSpotPanoramique = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
                    ivHotSpotPanoramique.setSmooth(true);
                } else {
                    ivHotSpotPanoramique = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomImage));
                    ivHotSpotPanoramique.setSmooth(true);
                }
                ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
                ivHotSpotPanoramique.setPreserveRatio(true);
                ivHotSpotPanoramique.setLayoutX(700);
                ivHotSpotPanoramique.setLayoutY(260);
                apVisualisation.getChildren().add(ivHotSpotPanoramique);
                
                // Recréer la vignette du panel de configuration
                paneVignettePano.getChildren().clear();
                if (!strTypeHS.equals("gif")) {
                    ivVignettePanoConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
                } else {
                    ivVignettePanoConfig = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomImage));
                }
                ivVignettePanoConfig.setSmooth(true);
                ivVignettePanoConfig.setPreserveRatio(true);
                ivVignettePanoConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
                paneVignettePano.getChildren().add(ivVignettePanoConfig);
                
                // Remettre la barre de boutons
                apVisualisation.getChildren().add(hbbarreBoutons);
                
                // Réappliquer l'animation
                previsualiserAnimation(ivVignettePanoConfig, strTypeAnimationPanoDefaut, "pano");
            });
            paneFond.getChildren().add(ivHotspots[i]);
            apIconesHS1Content.getChildren().add(paneFond);
            i++;
        }
        
        // ScrollPane pour limiter la hauteur et éviter que le contenu passe derrière les autres contrôles
        ScrollPane spIconesHS1 = new ScrollPane(apIconesHS1Content);
        spIconesHS1.setLayoutX(10);
        spIconesHS1.setLayoutY(10);
        spIconesHS1.setPrefWidth(largeur - 20);
        spIconesHS1.setMaxHeight(200);  // Hauteur maximale pour éviter le débordement
        spIconesHS1.setFitToWidth(true);
        spIconesHS1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spIconesHS1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        // AnchorPane principal contenant les icônes et les contrôles
        // Pas de hauteur fixe - s'adapte au contenu
        apHotSpots1 = new AnchorPane();
        
        apHotSpots1.getChildren().add(spIconesHS1);
        
        // Conteneur dédié pour la vignette de prévisualisation
        paneVignettePano = new Pane();
        paneVignettePano.setLayoutX(250);
        paneVignettePano.setLayoutY(305);
        paneVignettePano.setPrefWidth(60);
        paneVignettePano.setPrefHeight(60);
        
        // Vignette de prévisualisation de l'icône sélectionnée (pour ce panel)
        ivVignettePanoConfig.setPreserveRatio(true);
        ivVignettePanoConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
        paneVignettePano.getChildren().add(ivVignettePanoConfig);
        
        // Contrôles en dessous du ScrollPane (toujours visibles)
        cpCouleurHotspotsPanoramique = new ColorPicker(couleurHotspots);
        Label lblCouleurHotspot = new Label(rbLocalisation.getString("interface.couleurHS"));
        lblCouleurHotspot.setLayoutX(20);
        lblCouleurHotspot.setLayoutY(220);
        cpCouleurHotspotsPanoramique.setLayoutX(200);
        cpCouleurHotspotsPanoramique.setLayoutY(220);
        Label lblTailleHotspots = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspots.setLayoutX(20);
        lblTailleHotspots.setLayoutY(260);
        slTailleHotspotsPanoramique = new Slider(15, 100, getiTailleHotspotsPanoramique());
        slTailleHotspotsPanoramique.setLayoutX(200);
        slTailleHotspotsPanoramique.setLayoutY(260);
        
        // ComboBox pour le type d'animation des nouveaux hotspots panoramiques
        Label lblTypeAnimationPano = new Label(rbLocalisation.getString("main.typeAnimation"));
        lblTypeAnimationPano.setLayoutX(20);
        lblTypeAnimationPano.setLayoutY(300);
        
        cbTypeAnimationPanoDefaut = new ComboBox<>();
        cbTypeAnimationPanoDefaut.setLayoutX(20);
        cbTypeAnimationPanoDefaut.setLayoutY(325);
        cbTypeAnimationPanoDefaut.setPrefWidth(160);
        cbTypeAnimationPanoDefaut.getItems().addAll(
            "none", "pulse", "rotation", "desaturation", "bounce", "swing", "glow",
            "heartbeat", "shake", "flip", "wobble", "tada", "flash", "rubber", "jello", "neon", "float"
        );
        cbTypeAnimationPanoDefaut.setValue(strTypeAnimationPanoDefaut);
        cbTypeAnimationPanoDefaut.valueProperty().addListener((obs, oldVal, newVal) -> {
            strTypeAnimationPanoDefaut = newVal;
            previsualiserAnimation(ivVignettePanoConfig, newVal, "pano");
        });
        
        cbHotspotsPanoAgrandisDefaut = new CheckBox(rbLocalisation.getString("main.agrandissementSurvol"));
        cbHotspotsPanoAgrandisDefaut.setLayoutX(20);
        cbHotspotsPanoAgrandisDefaut.setLayoutY(360);
        cbHotspotsPanoAgrandisDefaut.setSelected(bHotspotsPanoAgrandisDefaut);
        
        // Bouton pour appliquer à tous les hotspots panoramiques existants
        Button btnAppliquerTousPano = new Button(rbLocalisation.getString("main.appliquerATous"));
        btnAppliquerTousPano.setLayoutX(20);
        btnAppliquerTousPano.setLayoutY(390);
        btnAppliquerTousPano.setPrefWidth(300);
        btnAppliquerTousPano.setMaxWidth(300);
        btnAppliquerTousPano.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                for (int iPano = 0; iPano < getiNombrePanoramiques(); iPano++) {
                    for (int iHS = 0; iHS < getPanoramiquesProjet()[iPano].getNombreHotspots(); iHS++) {
                        // Appliquer l'icône source et le nom de fichier
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setStrNomIconeSource(strNomfichierHS);
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setImgIconeSource(
                            getImgBoutons()[getiNombreImagesBouton() - 2]
                        );
                        // Appliquer la couleur (format: hue;saturation;brightness)
                        String strCouleur = couleurHotspots.getHue() + ";" + 
                                           couleurHotspots.getSaturation() + ";" + 
                                           couleurHotspots.getBrightness();
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setStrCouleurPerso(strCouleur);
                        // Appliquer la taille
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setTailleHotspot(
                            (int) slTailleHotspotsPanoramique.getValue()
                        );
                        // Appliquer l'animation
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setStrTypeAnimation(strTypeAnimationPanoDefaut);
                        // Appliquer l'agrandissement au survol
                        getPanoramiquesProjet()[iPano].getHotspot(iHS).setAgranditSurvol(cbHotspotsPanoAgrandisDefaut.isSelected());
                    }
                }
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                rafraichitAffichageHotSpots();
            }
        });
        
        apHotSpots1.getChildren().addAll(
                paneVignettePano,
                lblCouleurHotspot, cpCouleurHotspotsPanoramique,
                lblTailleHotspots, slTailleHotspotsPanoramique,
                lblTypeAnimationPano, cbTypeAnimationPanoDefaut, cbHotspotsPanoAgrandisDefaut, btnAppliquerTousPano
        );
        
        AnchorPane apHS1 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSPanoramique"), apHotSpots1, largeur).getApPaneOutil());
        /*
         * *************************************************
         *     Panel HotSpots Photos
         * *************************************************
        
         */
        // AnchorPane pour les icônes seulement
        AnchorPane apIconesHS2Content = new AnchorPane();
        // Calcul correct du nombre de lignes : arrondi supérieur de (nombre / par ligne)
        int iNombreLignesPhoto = (int) Math.ceil((double) iNombreHotSpotsPhoto / iIconesParLigne);
        apIconesHS2Content.setPrefHeight(45.d * iNombreLignesPhoto + 50);
        apIconesHS2Content.setPadding(new Insets(PANEL_TOP_MARGIN, 5, 5, 15));  // Espacement haut + décalage gauche
        
        // Déterminer la couleur de fond selon le thème
        boolean estThemeSombre2 = ThemeManager.getCurrentTheme().isDark();
        String couleurFond2 = estThemeSombre2 ? "#3a3a3a" : "#e8e8e8";
        apIconesHS2Content.setStyle("-fx-background-color: " + couleurFond2 + "; -fx-background-radius: 5px;");
        
        i = 0;
        for (String strNomImage : strListeHotSpotsPhoto) {
            String strExtension = strNomImage.substring(strNomImage.length() - 3, strNomImage.length());
            Pane paneFond = new Pane();
            ivHotspotsPhoto[i] = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol = i % iIconesParLigne;
            int iRow = i / iIconesParLigne;
            xHS = iCol * 50 + 5;
            yHS = (iRow) * 45 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                // Ne supprimer que l'ImageView Image qui va être recréée
                apVisualisation.getChildren().remove(ivHotSpotImage);
                strTypeHSImage = strExtension.toLowerCase();
                cpCouleurHotspotsPhoto.setDisable(strTypeHSImage.equals("gif"));
                strNomfichierHSImage = strNomImage;
                setStrStyleHotSpotImages(strNomImage);
                // Ne pas appeler afficheBarreClassique qui ajoute les ImageViews, juste recharger les images
                chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
                
                changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
                // Recréer l'ImageView avec l'image colorée (pour apVisualisation)
                if (!strTypeHSImage.equals("gif")) {
                    ivHotSpotImage = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
                    ivHotSpotImage.setSmooth(true);
                } else {
                    ivHotSpotImage = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomImage));
                    ivHotSpotImage.setSmooth(true);
                }
                ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
                ivHotSpotImage.setPreserveRatio(true);
                ivHotSpotImage.setLayoutX(820);
                ivHotSpotImage.setLayoutY(260);
                apVisualisation.getChildren().add(ivHotSpotImage);
                
                // Recréer la vignette du panel de configuration
                paneVignetteImage.getChildren().clear();
                if (!strTypeHSImage.equals("gif")) {
                    ivVignetteImageConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
                } else {
                    ivVignetteImageConfig = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomImage));
                }
                ivVignetteImageConfig.setSmooth(true);
                ivVignetteImageConfig.setPreserveRatio(true);
                ivVignetteImageConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
                paneVignetteImage.getChildren().add(ivVignetteImageConfig);
                
                // Remettre la barre de boutons
                apVisualisation.getChildren().add(hbbarreBoutons);
                
                // Réappliquer l'animation
                previsualiserAnimation(ivVignetteImageConfig, strTypeAnimationPhotoDefaut, "photo");
            });
            paneFond.getChildren().add(ivHotspotsPhoto[i]);
            apIconesHS2Content.getChildren().add(paneFond);
            i++;
        }
        
        // ScrollPane pour limiter la hauteur et éviter que le contenu passe derrière les autres contrôles
        ScrollPane spIconesHS2 = new ScrollPane(apIconesHS2Content);
        spIconesHS2.setLayoutX(10);
        spIconesHS2.setLayoutY(10);
        spIconesHS2.setPrefWidth(largeur - 20);
        spIconesHS2.setMaxHeight(200);  // Hauteur maximale pour éviter le débordement
        spIconesHS2.setFitToWidth(true);
        spIconesHS2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spIconesHS2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        // AnchorPane principal contenant les icônes et les contrôles
        // Pas de hauteur fixe - s'adapte au contenu
        apHotSpots2 = new AnchorPane();
        
        apHotSpots2.getChildren().add(spIconesHS2);
        
        // Conteneur dédié pour la vignette de prévisualisation
        paneVignetteImage = new Pane();
        paneVignetteImage.setLayoutX(250);
        paneVignetteImage.setLayoutY(305);
        paneVignetteImage.setPrefWidth(60);
        paneVignetteImage.setPrefHeight(60);
        
        // Vignette de prévisualisation de l'icône sélectionnée (pour ce panel)
        ivVignetteImageConfig.setPreserveRatio(true);
        ivVignetteImageConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
        paneVignetteImage.getChildren().add(ivVignetteImageConfig);
        
        // Contrôles en dessous du ScrollPane (toujours visibles)
        cpCouleurHotspotsPhoto = new ColorPicker(couleurHotspotsPhoto);
        Label lblCouleurHotspotPhoto = new Label(rbLocalisation.getString("interface.couleurHSPhoto"));
        lblCouleurHotspotPhoto.setLayoutX(20);
        lblCouleurHotspotPhoto.setLayoutY(220);
        cpCouleurHotspotsPhoto.setLayoutX(200);
        cpCouleurHotspotsPhoto.setLayoutY(220);
        Label lblTailleHotspotsImage = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspotsImage.setLayoutX(20);
        lblTailleHotspotsImage.setLayoutY(260);
        slTailleHotspotsImage = new Slider(15, 100, getiTailleHotspotsImage());
        slTailleHotspotsImage.setLayoutX(200);
        slTailleHotspotsImage.setLayoutY(260);
        
        // ComboBox pour le type d'animation des nouveaux hotspots photos
        Label lblTypeAnimationPhoto = new Label(rbLocalisation.getString("main.typeAnimation"));
        lblTypeAnimationPhoto.setLayoutX(20);
        lblTypeAnimationPhoto.setLayoutY(300);
        
        cbTypeAnimationPhotoDefaut = new ComboBox<>();
        cbTypeAnimationPhotoDefaut.setLayoutX(20);
        cbTypeAnimationPhotoDefaut.setLayoutY(325);
        cbTypeAnimationPhotoDefaut.setPrefWidth(160);
        cbTypeAnimationPhotoDefaut.getItems().addAll(
            "none", "pulse", "rotation", "desaturation", "bounce", "swing", "glow",
            "heartbeat", "shake", "flip", "wobble", "tada", "flash", "rubber", "jello", "neon", "float"
        );
        cbTypeAnimationPhotoDefaut.setValue(strTypeAnimationPhotoDefaut);
        cbTypeAnimationPhotoDefaut.valueProperty().addListener((obs, oldVal, newVal) -> {
            strTypeAnimationPhotoDefaut = newVal;
            previsualiserAnimation(ivVignetteImageConfig, newVal, "photo");
        });
        
        cbHotspotsPhotoAgrandisDefaut = new CheckBox(rbLocalisation.getString("main.agrandissementSurvol"));
        cbHotspotsPhotoAgrandisDefaut.setLayoutX(20);
        cbHotspotsPhotoAgrandisDefaut.setLayoutY(360);
        cbHotspotsPhotoAgrandisDefaut.setSelected(bHotspotsPhotoAgrandisDefaut);
        
        // Bouton pour appliquer à tous les hotspots photos existants
        Button btnAppliquerTousPhoto = new Button(rbLocalisation.getString("main.appliquerATous"));
        btnAppliquerTousPhoto.setLayoutX(20);
        btnAppliquerTousPhoto.setLayoutY(390);
        btnAppliquerTousPhoto.setPrefWidth(300);
        btnAppliquerTousPhoto.setMaxWidth(300);
        btnAppliquerTousPhoto.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                for (int iPano = 0; iPano < getiNombrePanoramiques(); iPano++) {
                    for (int iHS = 0; iHS < getPanoramiquesProjet()[iPano].getNombreHotspotImage(); iHS++) {
                        // Appliquer l'icône source et le nom de fichier
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setStrNomIconeSource(strNomfichierHSImage);
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setImgIconeSource(
                            getImgBoutons()[getiNombreImagesBouton() - 1]
                        );
                        // Appliquer la couleur (format: hue;saturation;brightness)
                        String strCouleur = couleurHotspotsPhoto.getHue() + ";" + 
                                           couleurHotspotsPhoto.getSaturation() + ";" + 
                                           couleurHotspotsPhoto.getBrightness();
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setStrCouleurPerso(strCouleur);
                        // Appliquer la taille
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setTailleHotspot(
                            (int) slTailleHotspotsImage.getValue()
                        );
                        // Appliquer l'animation
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setStrTypeAnimation(strTypeAnimationPhotoDefaut);
                        // Appliquer l'agrandissement au survol
                        getPanoramiquesProjet()[iPano].getHotspotImage(iHS).setAgranditSurvol(cbHotspotsPhotoAgrandisDefaut.isSelected());
                    }
                }
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                rafraichitAffichageHotSpots();
            }
        });
        
        apHotSpots2.getChildren().addAll(
                paneVignetteImage,
                lblCouleurHotspotPhoto, cpCouleurHotspotsPhoto,
                lblTailleHotspotsImage, slTailleHotspotsImage,
                lblTypeAnimationPhoto, cbTypeAnimationPhotoDefaut, cbHotspotsPhotoAgrandisDefaut, btnAppliquerTousPhoto
        );
        
        AnchorPane apHS2 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSPhoto"), apHotSpots2, largeur).getApPaneOutil());
        /*
         * *************************************************
         *     Panel HotSpots HTML
         * *************************************************
        
         */
        // AnchorPane pour les icônes seulement
        AnchorPane apIconesHS3Content = new AnchorPane();
        // Calcul correct du nombre de lignes : arrondi supérieur de (nombre / par ligne)
        int iNombreLignesHTML = (int) Math.ceil((double) iNombreHotSpotsHTML / iIconesParLigne);
        apIconesHS3Content.setPrefHeight(45.d * iNombreLignesHTML + 50);
        apIconesHS3Content.setPadding(new Insets(PANEL_TOP_MARGIN, 5, 5, 15));  // Espacement haut + décalage gauche
        
        // Déterminer la couleur de fond selon le thème
        boolean estThemeSombre3 = ThemeManager.getCurrentTheme().isDark();
        String couleurFond3 = estThemeSombre3 ? "#3a3a3a" : "#e8e8e8";
        apIconesHS3Content.setStyle("-fx-background-color: " + couleurFond3 + "; -fx-background-radius: 5px;");
        
        i = 0;
        for (String strNomHTML : strListeHotSpotsHTML) {
            String strExtension = strNomHTML.substring(strNomHTML.length() - 3, strNomHTML.length());
            Pane paneFond = new Pane();
            ivHotspotsHTML[i] = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomHTML, -1, 30, true, true, true));
            int iCol = i % iIconesParLigne;
            int iRow = i / iIconesParLigne;
            xHS = iCol * 50 + 5;
            yHS = (iRow) * 45 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                // Ne supprimer que l'ImageView HTML qui va être recréée
                apVisualisation.getChildren().remove(ivHotSpotHTML);
                strTypeHSHTML = strExtension.toLowerCase();
                cpCouleurHotspotsHTML.setDisable(strTypeHSHTML.equals("gif"));
                strNomfichierHSHTML = strNomHTML;
                setStrStyleHotSpotHTML(strNomHTML);
                // Ne pas appeler afficheBarreClassique qui ajoute les ImageViews, juste recharger les images
                chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
                
                changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
                // Recréer l'ImageView avec l'image colorée (pour apVisualisation)
                if (!strTypeHSHTML.equals("gif")) {
                    ivHotSpotHTML = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
                    ivHotSpotHTML.setSmooth(true);
                } else {
                    ivHotSpotHTML = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomHTML));
                    ivHotSpotHTML.setSmooth(true);
                }
                ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
                ivHotSpotHTML.setPreserveRatio(true);
                ivHotSpotHTML.setLayoutX(940);
                ivHotSpotHTML.setLayoutY(260);
                apVisualisation.getChildren().add(ivHotSpotHTML);
                
                // Recréer la vignette du panel de configuration
                paneVignetteHTML.getChildren().clear();
                if (!strTypeHSHTML.equals("gif")) {
                    ivVignetteHTMLConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
                } else {
                    ivVignetteHTMLConfig = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomHTML));
                }
                ivVignetteHTMLConfig.setSmooth(true);
                ivVignetteHTMLConfig.setPreserveRatio(true);
                ivVignetteHTMLConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
                paneVignetteHTML.getChildren().add(ivVignetteHTMLConfig);
                
                // Remettre la barre de boutons
                apVisualisation.getChildren().add(hbbarreBoutons);
                
                // Réappliquer l'animation
                previsualiserAnimation(ivVignetteHTMLConfig, strTypeAnimationHTMLDefaut, "html");
            });
            paneFond.getChildren().add(ivHotspotsHTML[i]);
            apIconesHS3Content.getChildren().add(paneFond);
            i++;
        }
        
        // ScrollPane pour limiter la hauteur et éviter que le contenu passe derrière les autres contrôles
        ScrollPane spIconesHS3 = new ScrollPane(apIconesHS3Content);
        spIconesHS3.setLayoutX(10);
        spIconesHS3.setLayoutY(10);
        spIconesHS3.setPrefWidth(largeur - 20);
        spIconesHS3.setMaxHeight(200);  // Hauteur maximale pour éviter le débordement
        spIconesHS3.setFitToWidth(true);
        spIconesHS3.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spIconesHS3.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        // AnchorPane principal contenant les icônes et les contrôles
        // Pas de hauteur fixe - s'adapte au contenu
        apHotSpots3 = new AnchorPane();
        
        apHotSpots3.getChildren().add(spIconesHS3);
        
        // Conteneur dédié pour la vignette de prévisualisation
        paneVignetteHTML = new Pane();
        paneVignetteHTML.setLayoutX(250);
        paneVignetteHTML.setLayoutY(305);
        paneVignetteHTML.setPrefWidth(60);
        paneVignetteHTML.setPrefHeight(60);
        
        // Vignette de prévisualisation de l'icône sélectionnée (pour ce panel)
        ivVignetteHTMLConfig.setPreserveRatio(true);
        ivVignetteHTMLConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
        paneVignetteHTML.getChildren().add(ivVignetteHTMLConfig);
        
        // Contrôles en dessous du ScrollPane (toujours visibles)
        cpCouleurHotspotsHTML = new ColorPicker(couleurHotspotsHTML);
        Label lblCouleurHotspotHTML = new Label(rbLocalisation.getString("interface.couleurHSHTML"));
        lblCouleurHotspotHTML.setLayoutX(20);
        lblCouleurHotspotHTML.setLayoutY(220);
        cpCouleurHotspotsHTML.setLayoutX(200);
        cpCouleurHotspotsHTML.setLayoutY(220);
        Label lblTailleHotspotsHTML = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleHotspotsHTML.setLayoutX(20);
        lblTailleHotspotsHTML.setLayoutY(260);
        slTailleHotspotsHTML = new Slider(15, 100, getiTailleHotspotsHTML());
        slTailleHotspotsHTML.setLayoutX(200);
        slTailleHotspotsHTML.setLayoutY(260);
        
        // ComboBox pour le type d'animation des nouveaux hotspots HTML
        Label lblTypeAnimationHTML = new Label(rbLocalisation.getString("main.typeAnimation"));
        lblTypeAnimationHTML.setLayoutX(20);
        lblTypeAnimationHTML.setLayoutY(300);
        
        cbTypeAnimationHTMLDefaut = new ComboBox<>();
        cbTypeAnimationHTMLDefaut.setLayoutX(20);
        cbTypeAnimationHTMLDefaut.setLayoutY(325);
        cbTypeAnimationHTMLDefaut.setPrefWidth(160);
        cbTypeAnimationHTMLDefaut.getItems().addAll(
            "none", "pulse", "rotation", "desaturation", "bounce", "swing", "glow",
            "heartbeat", "shake", "flip", "wobble", "tada", "flash", "rubber", "jello", "neon", "float"
        );
        cbTypeAnimationHTMLDefaut.setValue(strTypeAnimationHTMLDefaut);
        cbTypeAnimationHTMLDefaut.valueProperty().addListener((obs, oldVal, newVal) -> {
            strTypeAnimationHTMLDefaut = newVal;
            previsualiserAnimation(ivVignetteHTMLConfig, newVal, "html");
        });
        
        cbHotspotsHTMLAgrandisDefaut = new CheckBox(rbLocalisation.getString("main.agrandissementSurvol"));
        cbHotspotsHTMLAgrandisDefaut.setLayoutX(20);
        cbHotspotsHTMLAgrandisDefaut.setLayoutY(360);
        cbHotspotsHTMLAgrandisDefaut.setSelected(bHotspotsHTMLAgrandisDefaut);
        
        // Bouton pour appliquer à tous les hotspots HTML existants
        Button btnAppliquerTousHTML = new Button(rbLocalisation.getString("main.appliquerATous"));
        btnAppliquerTousHTML.setLayoutX(20);
        btnAppliquerTousHTML.setLayoutY(390);
        btnAppliquerTousHTML.setPrefWidth(300);
        btnAppliquerTousHTML.setMaxWidth(300);
        btnAppliquerTousHTML.setOnAction((e) -> {
            if (getiNombrePanoramiques() != 0) {
                for (int iPano = 0; iPano < getiNombrePanoramiques(); iPano++) {
                    for (int iHS = 0; iHS < getPanoramiquesProjet()[iPano].getNombreHotspotHTML(); iHS++) {
                        // Appliquer l'icône source et le nom de fichier
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setStrNomIconeSource(strNomfichierHSHTML);
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setImgIconeSource(
                            getImgBoutons()[getiNombreImagesBouton()]
                        );
                        // Appliquer la couleur (format: hue;saturation;brightness)
                        String strCouleur = couleurHotspotsHTML.getHue() + ";" + 
                                           couleurHotspotsHTML.getSaturation() + ";" + 
                                           couleurHotspotsHTML.getBrightness();
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setStrCouleurPerso(strCouleur);
                        // Appliquer la taille
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setTailleHotspot(
                            (int) slTailleHotspotsHTML.getValue()
                        );
                        // Appliquer l'animation
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setStrTypeAnimation(strTypeAnimationHTMLDefaut);
                        // Appliquer l'agrandissement au survol
                        getPanoramiquesProjet()[iPano].getHotspotHTML(iHS).setAgranditSurvol(cbHotspotsHTMLAgrandisDefaut.isSelected());
                    }
                }
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                rafraichitAffichageHotSpots();
            }
        });
        
        apHotSpots3.getChildren().addAll(
                paneVignetteHTML,
                lblCouleurHotspotHTML, cpCouleurHotspotsHTML,
                lblTailleHotspotsHTML, slTailleHotspotsHTML,
                lblTypeAnimationHTML, cbTypeAnimationHTMLDefaut, cbHotspotsHTMLAgrandisDefaut, btnAppliquerTousHTML
        );
        
        AnchorPane apHS3 = new AnchorPane(new PaneOutil(true, rbLocalisation.getString("interface.HSHTML"), apHotSpots3, largeur).getApPaneOutil());


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
        cbBarreClassiqueVisible.setLayoutY(PANEL_TOP_MARGIN);
        cbBarreClassiqueVisible.setSelected(true);
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauBarreClassique.getItems().add(nomNiveau);
        });
        cbNiveauBarreClassique.getSelectionModel().select(getiCalqueBarreClassique() - 1);
        cbNiveauBarreClassique.setLayoutX(largeur - 110);
        cbNiveauBarreClassique.setLayoutY(PANEL_TOP_MARGIN);
        cbNiveauBarreClassique.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueBarreClassique((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauBarreClassique.disableProperty().bind(cbBarreClassiqueVisible.selectedProperty().not());

        apBarreClassique.getChildren().addAll(
                apBarreClass,
                cbBarreClassiqueVisible,
                cbNiveauBarreClassique
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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauBarrePersonnalisee.getItems().add(nomNiveau);
        });
        cbNiveauBarrePersonnalisee.getSelectionModel().select(getiCalqueBarrePersonnalisee() - 1);
        cbNiveauBarrePersonnalisee.setLayoutX(largeur - 110);
        cbNiveauBarrePersonnalisee.setLayoutY(10);
        cbNiveauBarrePersonnalisee.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueBarrePersonnalisee((int) new_val + 1);
            reOrdonneElementsCalque();
        });

        poPers.setbValide(false);
        btnEditerBarre = new Button(rbLocalisation.getString("interface.barrePresonnaliseeEditer"));
        btnEditerBarre.setLayoutX(300);
        btnEditerBarre.setLayoutY(100);
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

        Label lblOpaciteBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeOpacite"));
        lblOpaciteBarrePersonnalisee.setLayoutX(20);
        lblOpaciteBarrePersonnalisee.setLayoutY(410);
        slOpaciteBarrePersonnalisee = new Slider(0.1, 1.0, getOpaciteBarrePersonnalisee());
        slOpaciteBarrePersonnalisee.setLayoutX(150);
        slOpaciteBarrePersonnalisee.setLayoutY(410);

        rbCouleurOrigineBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurOrigine"));
        rbCouleurOrigineBarrePersonnalisee.setLayoutX(20);
        rbCouleurOrigineBarrePersonnalisee.setLayoutY(440);
        rbCouleurOrigineBarrePersonnalisee.setSelected(true);
        rbCouleurOrigineBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurOrigineBarrePersonnalisee.setUserData(true);

        rbCouleurPersBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurPersonnalisee"));
        rbCouleurPersBarrePersonnalisee.setLayoutX(20);
        rbCouleurPersBarrePersonnalisee.setLayoutY(470);
        rbCouleurPersBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurPersBarrePersonnalisee.setUserData(false);

        cpCouleurBarrePersonnalisee = new ColorPicker(couleurBarrePersonnalisee);
        cpCouleurBarrePersonnalisee.setLayoutX(180);
        cpCouleurBarrePersonnalisee.setLayoutY(465);
        cpCouleurBarrePersonnalisee.setDisable(true);
        cbDeplacementsBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.deplacementsVisible"));
        cbZoomBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.zoomVisible"));
        cbSourisBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsSouris"));
        cbRotationBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsRotation"));
        cbFSBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsFS"));
        Label lblVisibiliteBarrePersonnalisee = new Label(rbLocalisation.getString("interface.visibilite"));
        lblVisibiliteBarrePersonnalisee.setLayoutX(20);
        lblVisibiliteBarrePersonnalisee.setLayoutY(510);

        cbDeplacementsBarrePersonnalisee.setLayoutX(100);
        cbDeplacementsBarrePersonnalisee.setLayoutY(530);
        cbDeplacementsBarrePersonnalisee.setSelected(true);
        cbZoomBarrePersonnalisee.setLayoutX(100);
        cbZoomBarrePersonnalisee.setLayoutY(550);
        cbZoomBarrePersonnalisee.setSelected(true);
        cbFSBarrePersonnalisee.setLayoutX(150);
        cbFSBarrePersonnalisee.setLayoutY(590);
        cbFSBarrePersonnalisee.setSelected(true);
        cbRotationBarrePersonnalisee.setLayoutX(150);
        cbRotationBarrePersonnalisee.setLayoutY(610);
        cbRotationBarrePersonnalisee.setSelected(true);
        cbSourisBarrePersonnalisee.setLayoutX(150);
        cbSourisBarrePersonnalisee.setLayoutY(630);
        cbSourisBarrePersonnalisee.setSelected(true);
        Label lblLien1BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "1");
        lblLien1BarrePersonnalisee.setLayoutX(20);
        lblLien1BarrePersonnalisee.setLayoutY(660);
        tfLien1BarrePersonnalisee = new TextField("");
        tfLien1BarrePersonnalisee.setPrefWidth(200);
        tfLien1BarrePersonnalisee.setLayoutX(100);
        tfLien1BarrePersonnalisee.setLayoutY(660);
        tfLien1BarrePersonnalisee.setDisable(true);
        Label lblLien2BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "2");
        lblLien2BarrePersonnalisee.setLayoutX(20);
        lblLien2BarrePersonnalisee.setLayoutY(690);
        tfLien2BarrePersonnalisee = new TextField("");
        tfLien2BarrePersonnalisee.setPrefWidth(200);
        tfLien2BarrePersonnalisee.setLayoutX(100);
        tfLien2BarrePersonnalisee.setLayoutY(690);
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
                lblOpaciteBarrePersonnalisee, slOpaciteBarrePersonnalisee,
                rbCouleurOrigineBarrePersonnalisee,
                rbCouleurPersBarrePersonnalisee, cpCouleurBarrePersonnalisee,
                lblVisibiliteBarrePersonnalisee, cbDeplacementsBarrePersonnalisee, cbZoomBarrePersonnalisee,
                cbFSBarrePersonnalisee, cbRotationBarrePersonnalisee, cbSourisBarrePersonnalisee,
                lblLien1BarrePersonnalisee, tfLien1BarrePersonnalisee,
                lblLien2BarrePersonnalisee, tfLien2BarrePersonnalisee
        );
        cbNiveauBarrePersonnalisee.disableProperty().bind(cbBarrePersonnaliseeVisible.selectedProperty().not());
        apBarrePersonnalisee.getChildren().addAll(
                apBarrePers, cbBarrePersonnaliseeVisible, cbNiveauBarrePersonnalisee
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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauBoussole.getItems().add(nomNiveau);
        });
        cbNiveauBoussole.getSelectionModel().select(getiCalqueBoussole() - 1);
        cbNiveauBoussole.setLayoutX(largeur - 110);
        cbNiveauBoussole.setLayoutY(10);
        cbNiveauBoussole.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueBoussole((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauBoussole.disableProperty().bind(cbAfficheBoussole.selectedProperty().not());
        apBoussole.getChildren().addAll(apBouss1, cbAfficheBoussole, cbNiveauBoussole);

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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauMasque.getItems().add(nomNiveau);
        });
        cbNiveauMasque.getSelectionModel().select(getiCalqueMasquage() - 1);
        cbNiveauMasque.setLayoutX(largeur - 110);
        cbNiveauMasque.setLayoutY(10);
        cbNiveauMasque.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueMasquage((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauMasque.disableProperty().bind(cbAfficheMasque.selectedProperty().not());
        apMasque.getChildren().addAll(apMasq1, cbAfficheMasque, cbNiveauMasque);

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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauPartage.getItems().add(nomNiveau);
        });
        cbNiveauPartage.getSelectionModel().select(getiCalquePartage() - 1);
        cbNiveauPartage.setLayoutX(largeur - 110);
        cbNiveauPartage.setLayoutY(10);
        cbNiveauPartage.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalquePartage((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbReseauxSociauxMeta = new CheckBox("Meta");
        cbReseauxSociauxMeta.setLayoutX(60);
        cbReseauxSociauxMeta.setLayoutY(250 + iBasImages);
        cbReseauxSociauxMeta.setSelected(true);

        cbReseauxSociauxEmail = new CheckBox("Email");
        cbReseauxSociauxEmail.setLayoutX(60);
        cbReseauxSociauxEmail.setLayoutY(280 + iBasImages);
        cbReseauxSociauxEmail.setSelected(true);

        apReseau.getChildren().addAll(
                lblTailleReseauxSociaux, slTailleReseauxSociaux,
                lblOpaciteReseauxSociaux, slOpaciteReseauxSociaux,
                cbReseauxSociauxTwitter, cbReseauxSociauxMeta, cbReseauxSociauxEmail
        );
        apReseau.disableProperty().bind(cbAfficheReseauxSociaux.selectedProperty().not());
        cbNiveauPartage.disableProperty().bind(cbAfficheReseauxSociaux.selectedProperty().not());
        apReseauxSociaux.getChildren().addAll(apReseau, cbAfficheReseauxSociaux, cbNiveauPartage);

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
        cbAfficheVignettes.setLayoutY(PANEL_TOP_MARGIN);
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauVignettes.getItems().add(nomNiveau);
        });
        cbNiveauVignettes.getSelectionModel().select(getiCalqueVignettes() - 1);
        cbNiveauVignettes.setLayoutX(largeur - 110);
        cbNiveauVignettes.setLayoutY(PANEL_TOP_MARGIN);
        cbNiveauVignettes.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueVignettes((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauVignettes.disableProperty().bind(cbAfficheVignettes.selectedProperty().not());
        apVignettes.getChildren().addAll(apVig1, cbAfficheVignettes, cbNiveauVignettes);

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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauComboMenu.getItems().add(nomNiveau);
        });
        cbNiveauComboMenu.getSelectionModel().select(getiCalqueMenuPanoramiques() - 1);
        cbNiveauComboMenu.setLayoutX(largeur - 110);
        cbNiveauComboMenu.setLayoutY(10);
        cbNiveauComboMenu.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueMenuPanoramiques((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauComboMenu.disableProperty().bind(cbAfficheComboMenu.selectedProperty().not());
        apComboMenu.getChildren().addAll(apCombo1, cbAfficheComboMenu, cbNiveauComboMenu);

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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauPlan.getItems().add(nomNiveau);
        });
        cbNiveauPlan.getSelectionModel().select(getiCalquePlan() - 1);
        cbNiveauPlan.setLayoutX(largeur - 110);
        cbNiveauPlan.setLayoutY(10);
        cbNiveauPlan.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalquePlan((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauPlan.disableProperty().bind(cbAffichePlan.selectedProperty().not());
        apPlan.getChildren().addAll(
                apPlan1, cbAffichePlan, cbNiveauPlan
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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauCarte.getItems().add(nomNiveau);
        });
        cbNiveauCarte.getSelectionModel().select(getiCalqueCarte() - 1);
        cbNiveauCarte.setLayoutX(largeur - 110);
        cbNiveauCarte.setLayoutY(10);
        cbNiveauCarte.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setiCalqueCarte((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        slTailleRadarCarte = new Slider(0, 240, getTailleRadarCarte()); // Max 240m (x3 par rapport à 80m)
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
        cbNiveauCarte.disableProperty().bind(cbAfficheCarte.selectedProperty().not());
        apCarte.getChildren().addAll(
                apCarte1, cbAfficheCarte, cbNiveauCarte
        );


        /*
         * ********************************************
         *     Panel Image Fond
         * ********************************************
         */
        apImageFond = new AnchorPane();
        poImageFond = new PaneOutil(rbLocalisation.getString("interface.imageFond"), apImageFond, largeur);
        AnchorPane apIF = new AnchorPane(poImageFond.getApPaneOutil());
        apIF.setPrefHeight(250);
        poImageFond.setbValide(false);
        apImageFond.setLayoutY(40);
        apImageFond.setPrefHeight(120);
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
        apImageFond.setPrefHeight(120);
        apImageFond.prefHeightProperty().addListener((ov, old_val, new_val) -> {
            apIF.setPrefHeight(apImageFond.getPrefHeight() + 250);
        });

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
        listeNiveau.stream().forEach((nomNiveau) -> {
            cbNiveauVisiteAuto.getItems().add(nomNiveau);
        });
        cbNiveauVisiteAuto.getSelectionModel().select(getiCalqueVisiteAuto() - 1);
        cbNiveauVisiteAuto.setLayoutX(largeur - 110);
        cbNiveauVisiteAuto.setLayoutY(10);
        cbNiveauVisiteAuto.getSelectionModel().selectedIndexProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }

            setiCalqueVisiteAuto((int) new_val + 1);
            reOrdonneElementsCalque();
        });

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
        cbNiveauVisiteAuto.disableProperty().bind(getCbAfficheBoutonVisiteAuto().selectedProperty().not());
        apBoutonVisiteAuto.getChildren().addAll(apBtnVisiteAuto1, getCbAfficheBoutonVisiteAuto(), cbNiveauVisiteAuto);

        /*
         * ******************************************************
         *     Ajouts des pannels dans la barre d'outils
         * ******************************************************
         */
        if (!isbInternet()) {
            apCARTE.setDisable(true);
        }
        vbOutils.getChildren().addAll(apTHEME,
                apIB,
                apHS1,
                apHS2,
                apHS3,
                apTIT,
                apDESC,
                getApBtnVA(),
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
            reOrdonneElementsCalque();
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
            setCouleurFondTheme(cpCouleurFondTheme.getValue());
            cpCouleurFondTitre.setValue(getCouleurFondTheme());
            cpCouleurFondPlan.setValue(getCouleurFondTheme());
            cpCouleurFondCarte.setValue(getCouleurFondTheme());
            cpCouleurHotspotsPanoramique.setValue(getCouleurFondTheme());
            cpCouleurHotspotsPhoto.setValue(getCouleurFondTheme());
            cpCouleurHotspotsHTML.setValue(getCouleurFondTheme());
            cpCouleurBarreClassique.setValue(getCouleurFondTheme());
            cpCouleurMasques.setValue(getCouleurFondTheme());
            cpCouleurBarrePersonnalisee.setValue(getCouleurFondTheme());
            cpCouleurFondVignettes.setValue(getCouleurFondTheme());
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
            
            // Retirer l'ancienne ImageView de apVisualisation
            apVisualisation.getChildren().remove(ivHotSpotPanoramique);
            
            // Recréer l'ImageView pour apVisualisation avec la nouvelle couleur
            if (!strTypeHS.equals("gif")) {
                ivHotSpotPanoramique = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
                ivHotSpotPanoramique.setSmooth(true);
            } else {
                ivHotSpotPanoramique = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomfichierHS));
                ivHotSpotPanoramique.setSmooth(true);
            }
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotPanoramique.setPreserveRatio(true);
            ivHotSpotPanoramique.setLayoutX(700);
            ivHotSpotPanoramique.setLayoutY(260);
            apVisualisation.getChildren().add(ivHotSpotPanoramique);
            
            // Recréer la vignette avec la nouvelle couleur
            paneVignettePano.getChildren().clear();
            if (!strTypeHS.equals("gif")) {
                ivVignettePanoConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 2]);
            } else {
                ivVignettePanoConfig = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomfichierHS));
            }
            ivVignettePanoConfig.setSmooth(true);
            ivVignettePanoConfig.setPreserveRatio(true);
            ivVignettePanoConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
            paneVignettePano.getChildren().add(ivVignettePanoConfig);
            
            // Réappliquer l'animation
            previsualiserAnimation(ivVignettePanoConfig, strTypeAnimationPanoDefaut, "pano");
            
            // Mettre à jour les tailles des autres ImageViews
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
            
            // Retirer l'ancienne ImageView de apVisualisation
            apVisualisation.getChildren().remove(ivHotSpotImage);
            
            // Recréer l'ImageView pour apVisualisation avec la nouvelle couleur
            if (!strTypeHSImage.equals("gif")) {
                ivHotSpotImage = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
                ivHotSpotImage.setSmooth(true);
            } else {
                ivHotSpotImage = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomfichierHSImage));
                ivHotSpotImage.setSmooth(true);
            }
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
            ivHotSpotImage.setPreserveRatio(true);
            ivHotSpotImage.setLayoutX(820);
            ivHotSpotImage.setLayoutY(260);
            apVisualisation.getChildren().add(ivHotSpotImage);
            
            // Recréer la vignette avec la nouvelle couleur
            paneVignetteImage.getChildren().clear();
            if (!strTypeHSImage.equals("gif")) {
                ivVignetteImageConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton() - 1]);
            } else {
                ivVignetteImageConfig = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomfichierHSImage));
            }
            ivVignetteImageConfig.setSmooth(true);
            ivVignetteImageConfig.setPreserveRatio(true);
            ivVignetteImageConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
            paneVignetteImage.getChildren().add(ivVignetteImageConfig);
            
            // Réappliquer l'animation
            previsualiserAnimation(ivVignetteImageConfig, strTypeAnimationPhotoDefaut, "photo");
            
            // Mettre à jour les tailles des autres ImageViews
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
        });

        cpCouleurHotspotsHTML.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            couleurHotspotsHTML = cpCouleurHotspotsHTML.getValue();
            changeCouleurHSHTML(couleurHotspotsHTML.getHue(), couleurHotspotsHTML.getSaturation(), couleurHotspotsHTML.getBrightness());
            
            // Retirer l'ancienne ImageView de apVisualisation
            apVisualisation.getChildren().remove(ivHotSpotHTML);
            
            // Recréer l'ImageView pour apVisualisation avec la nouvelle couleur
            if (!strTypeHSHTML.equals("gif")) {
                ivHotSpotHTML = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
                ivHotSpotHTML.setSmooth(true);
            } else {
                ivHotSpotHTML = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomfichierHSHTML));
                ivHotSpotHTML.setSmooth(true);
            }
            ivHotSpotHTML.setFitWidth(iTailleHotspotsHTML);
            ivHotSpotHTML.setPreserveRatio(true);
            ivHotSpotHTML.setLayoutX(940);
            ivHotSpotHTML.setLayoutY(260);
            apVisualisation.getChildren().add(ivHotSpotHTML);
            
            // Recréer la vignette avec la nouvelle couleur
            paneVignetteHTML.getChildren().clear();
            if (!strTypeHSHTML.equals("gif")) {
                ivVignetteHTMLConfig = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
            } else {
                ivVignetteHTMLConfig = new ImageView(new Image("file:" + strRepertHotSpotsHTML + File.separator + strNomfichierHSHTML));
            }
            ivVignetteHTMLConfig.setSmooth(true);
            ivVignetteHTMLConfig.setPreserveRatio(true);
            ivVignetteHTMLConfig.setFitWidth(48); // Taille fixe pour la vignette du panel
            paneVignetteHTML.getChildren().add(ivVignetteHTMLConfig);
            
            // Réappliquer l'animation
            previsualiserAnimation(ivVignetteHTMLConfig, strTypeAnimationHTMLDefaut, "html");
            
            // Mettre à jour les tailles des autres ImageViews
            ivHotSpotPanoramique.setFitWidth(iTailleHotspotsPanoramique);
            ivHotSpotImage.setFitWidth(iTailleHotspotsImage);
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
        
        // Listeners pour les options par défaut des hotspots
        cbHotspotsPanoAgrandisDefaut.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbHotspotsPanoAgrandisDefaut(new_val);
        });
        
        cbHotspotsPhotoAgrandisDefaut.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbHotspotsPhotoAgrandisDefaut(new_val);
        });
        
        cbHotspotsHTMLAgrandisDefaut.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            setbHotspotsHTMLAgrandisDefaut(new_val);
        });
        
        slTailleHotspotsPanoramique.valueProperty().addListener((ov, old_val, new_val) -> {
            setbDejaSauve(false);
            getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
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
         Listeners InfoBulle
         */
        cbOmbreInfoBulle.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            bOmbreInfoBulle = new_val;
        });

        cpCouleurTextInfoBulle.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurTextInfoBulle.getValue().toString().substring(2, 8);
            strCouleurTexteInfoBulle = "#" + strCoul;
            styleInfoBulle();
        });
        cpCouleurFondInfoBulle.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurFondInfoBulle.getValue().toString().substring(2, 8);
            strCouleurFondInfoBulle = "#" + strCoul;
            styleInfoBulle();
        });
        cpCouleurBordureInfoBulle.valueProperty().addListener((ov, av, nv) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurBordureInfoBulle.getValue().toString().substring(2, 8);
            strCouleurBordureInfoBulle = "#" + strCoul;
            styleInfoBulle();
        });

        cbListePoliceInfoBulle.valueProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                strPoliceInfoBulle = new_val.toString();
                styleInfoBulle();
            }
        });
        slTaillePoliceInfoBulle.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                taillePoliceInfoBulle = (double) newValue;
                styleInfoBulle();
            }
        });
        slOpaciteInfoBulle.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                opaciteInfoBulle = (double) newValue;
                styleInfoBulle();
            }
        });

        bdfTailleBordureTop.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iTailleBordureTop = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfTailleBordureBottom.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iTailleBordureBottom = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfTailleBordureLeft.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iTailleBordureLeft = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfTailleBordureRight.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iTailleBordureRight = new_value.intValue();
                styleInfoBulle();
            }
        });

        bdfArrondiTL.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iArrondiTL = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfArrondiBL.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iArrondiBL = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfArrondiTR.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iArrondiTR = new_value.intValue();
                styleInfoBulle();
            }
        });
        bdfArrondiBR.numberProperty().addListener((ov, old_value, new_value) -> {
            if (new_value != null) {
                if (getiNombrePanoramiques() != 0) {
                    setbDejaSauve(false);
                    getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
                }
                iArrondiBR = new_value.intValue();
                styleInfoBulle();
            }
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
        slOpaciteBarrePersonnalisee.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opacite = (double) newValue;
                setOpaciteBarrePersonnalisee(opacite);
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
        cbReseauxSociauxMeta.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (getiNombrePanoramiques() != 0) {
                setbDejaSauve(false);
                getStPrincipal().setTitle(getStPrincipal().getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxMeta((boolean) new_val);
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
                    if (navigateurCarteOL != null) {
                        navigateurCarteOL.choixZoom(iFacteurZoomCarte);
                    }
                }
            }
        });
        btnChoixCentreCarte.setOnAction((e) -> {
            if (isbInternet() && navigateurCarteOL != null) {
                setCoordCentreCarte(navigateurCarteOL.recupereCoordonnees());
            }
        });
        btnRecentreCarte.setOnAction((e) -> {
            if (isbInternet() && navigateurCarteOL != null) {
                if (getCoordCentreCarte() != null) {
                    navigateurCarteOL.allerCoordonnees(getCoordCentreCarte(), iFacteurZoomCarte);
                }
            }
        });

        btnChoixCarte.setOnAction((e) -> {
            if (isbInternet() && navigateurCarteOL != null) {
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
            if (isbInternet() && navigateurCarteOL != null) {
                navigateurCarteOL.allerAdresse(tfAdresseCarte.getText(), iFacteurZoomCarte);
            }
        });
        tfAdresseCarte.setOnKeyPressed((e) -> {
            if (isbInternet() && navigateurCarteOL != null) {
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
                    miseAJourRadarSeul();
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
                    miseAJourRadarSeul();
                }
            }
        });
        slAngleRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (newValue != null) {
                    double angle = (double) newValue;
                    angleRadarCarte = angle;
                    miseAJourRadarSeul();
                }
            }
        });
        slOuvertureRadarCarte.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isbInternet()) {
                if (newValue != null) {
                    double angle = (double) newValue;
                    ouvertureRadarCarte = angle;
                    miseAJourRadarSeul();
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
                miseAJourRadarSeul();
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
                miseAJourRadarSeul();
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
     * Retourne le décalage horizontal de la barre classique
     * 
     * @return Offset X en pixels depuis la position de référence
     * @see #setOffsetXBarreClassique(double)
     */
    public double getOffsetXBarreClassique() {
        return offsetXBarreClassique;
    }

    /**
     * Définit le décalage horizontal de la barre classique
     * 
     * @param offsetXBarreClassique Offset X en pixels
     * @see #getOffsetXBarreClassique()
     */
    public void setOffsetXBarreClassique(double offsetXBarreClassique) {
        this.offsetXBarreClassique = offsetXBarreClassique;
    }

    /**
     * Retourne le décalage vertical de la barre classique
     * 
     * @return Offset Y en pixels depuis la position de référence
     * @see #setOffsetYBarreClassique(double)
     */
    public double getOffsetYBarreClassique() {
        return offsetYBarreClassique;
    }

    /**
     * Définit le décalage vertical de la barre classique
     * 
     * @param offsetYBarreClassique Offset Y en pixels
     * @see #getOffsetYBarreClassique()
     */
    public void setOffsetYBarreClassique(double offsetYBarreClassique) {
        this.offsetYBarreClassique = offsetYBarreClassique;
    }

    /**
     * Retourne la taille des icônes de la barre classique
     * 
     * @return Taille en pixels (largeur = hauteur)
     * @see #setTailleBarreClassique(double)
     */
    public double getTailleBarreClassique() {
        return tailleBarreClassique;
    }

    /**
     * Définit la taille des icônes de la barre classique
     * 
     * @param tailleBarreClassique Taille en pixels
     * @see #getTailleBarreClassique()
     */
    public void setTailleBarreClassique(double tailleBarreClassique) {
        this.tailleBarreClassique = tailleBarreClassique;
    }

    /**
     * Retourne l'espacement entre les icônes de la barre classique
     * 
     * @return Espacement en pixels
     * @see #setEspacementBarreClassique(double)
     */
    public double getEspacementBarreClassique() {
        return espacementBarreClassique;
    }

    /**
     * Définit l'espacement entre les icônes de la barre classique
     * 
     * @param espacementBarreClassique Espacement en pixels
     * @see #getEspacementBarreClassique()
     */
    public void setEspacementBarreClassique(double espacementBarreClassique) {
        this.espacementBarreClassique = espacementBarreClassique;
    }

    /**
     * Retourne le style par défaut de la barre classique
     * 
     * @return Nom du style par défaut
     * @see #getStyleBarreClassique()
     */
    public String getStrStyleDefautBarreClassique() {
        return strStyleDefautBarreClassique;
    }

    /**
     * Retourne la position de la barre classique
     * 
     * @return Position ("haut", "bas", "gauche", "droite")
     * @see #setStrPositionBarreClassique(String)
     */
    public String getStrPositionBarreClassique() {
        return strPositionBarreClassique;
    }

    /**
     * Définit la position de la barre classique
     * 
     * @param strPositionBarreClassique Position ("haut", "bas", "gauche", "droite")
     * @see #getStrPositionBarreClassique()
     */
    public void setStrPositionBarreClassique(String strPositionBarreClassique) {
        this.strPositionBarreClassique = strPositionBarreClassique;
    }

    /**
     * Retourne le style actuel de la barre classique
     * 
     * @return Nom du style appliqué
     * @see #setStyleBarreClassique(String)
     */
    public String getStyleBarreClassique() {
        return styleBarreClassique;
    }

    /**
     * Définit le style de la barre classique
     * 
     * @param styleBarreClassique Nom du style à appliquer
     * @see #getStyleBarreClassique()
     */
    public void setStyleBarreClassique(String styleBarreClassique) {
        this.styleBarreClassique = styleBarreClassique;
    }

    /**
     * Indique si les déplacements sont affichés dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrDeplacementsBarreClassique(String)
     */
    public String getStrDeplacementsBarreClassique() {
        return strDeplacementsBarreClassique;
    }

    /**
     * Définit l'affichage des boutons de déplacement
     * 
     * @param strDeplacementsBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrDeplacementsBarreClassique()
     */
    public void setStrDeplacementsBarreClassique(String strDeplacementsBarreClassique) {
        this.strDeplacementsBarreClassique = strDeplacementsBarreClassique;
    }

    /**
     * Indique si le zoom est affiché dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrZoomBarreClassique(String)
     */
    public String getStrZoomBarreClassique() {
        return strZoomBarreClassique;
    }

    /**
     * Définit l'affichage des boutons de zoom
     * 
     * @param strZoomBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrZoomBarreClassique()
     */
    public void setStrZoomBarreClassique(String strZoomBarreClassique) {
        this.strZoomBarreClassique = strZoomBarreClassique;
    }

    /**
     * Indique si les outils sont affichés dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrOutilsBarreClassique(String)
     */
    public String getStrOutilsBarreClassique() {
        return strOutilsBarreClassique;
    }

    /**
     * Définit l'affichage des boutons d'outils (info, aide, etc.)
     * 
     * @param strOutilsBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrOutilsBarreClassique()
     */
    public void setStrOutilsBarreClassique(String strOutilsBarreClassique) {
        this.strOutilsBarreClassique = strOutilsBarreClassique;
    }

    /**
     * Indique si la rotation est affichée dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrRotationBarreClassique(String)
     */
    public String getStrRotationBarreClassique() {
        return strRotationBarreClassique;
    }

    /**
     * Définit l'affichage du bouton de rotation
     * 
     * @param strRotationBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrRotationBarreClassique()
     */
    public void setStrRotationBarreClassique(String strRotationBarreClassique) {
        this.strRotationBarreClassique = strRotationBarreClassique;
    }

    /**
     * Indique si le plein écran est affiché dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrPleinEcranBarreClassique(String)
     */
    public String getStrPleinEcranBarreClassique() {
        return strPleinEcranBarreClassique;
    }

    /**
     * Définit l'affichage du bouton plein écran
     * 
     * @param strPleinEcranBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrPleinEcranBarreClassique()
     */
    public void setStrPleinEcranBarreClassique(String strPleinEcranBarreClassique) {
        this.strPleinEcranBarreClassique = strPleinEcranBarreClassique;
    }

    /**
     * Indique si le mode souris est affiché dans la barre classique
     * 
     * @return "oui" ou "non"
     * @see #setStrSourisBarreClassique(String)
     */
    public String getStrSourisBarreClassique() {
        return strSourisBarreClassique;
    }

    /**
     * Définit l'affichage du bouton de mode souris
     * 
     * @param strSourisBarreClassique "oui" pour afficher, "non" pour masquer
     * @see #getStrSourisBarreClassique()
     */
    public void setStrSourisBarreClassique(String strSourisBarreClassique) {
        this.strSourisBarreClassique = strSourisBarreClassique;
    }

    /**
     * Retourne le mode de visibilité de la barre classique
     * 
     * @return "oui" (toujours visible), "non" (masquée), ou "survol" (visible au survol)
     * @see #setStrVisibiliteBarreClassique(String)
     */
    public String getStrVisibiliteBarreClassique() {
        return strVisibiliteBarreClassique;
    }

    /**
     * Définit le mode de visibilité de la barre classique
     * 
     * @param strVisibiliteBarreClassique "oui", "non", ou "survol"
     * @see #getStrVisibiliteBarreClassique()
     */
    public void setStrVisibiliteBarreClassique(String strVisibiliteBarreClassique) {
        this.strVisibiliteBarreClassique = strVisibiliteBarreClassique;
    }

    /**
     * Indique si la barre personnalisée conserve ses couleurs d'origine
     * 
     * @return true pour conserver les couleurs, false pour appliquer une teinte
     * @see #setbCouleurOrigineBarrePersonnalisee(boolean)
     */
    public boolean isbCouleurOrigineBarrePersonnalisee() {
        return bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * Définit si la barre personnalisée conserve ses couleurs d'origine
     * 
     * @param bCouleurOrigineBarrePersonnalisee true pour couleurs d'origine, false pour teinte
     * @see #isbCouleurOrigineBarrePersonnalisee()
     */
    public void setbCouleurOrigineBarrePersonnalisee(boolean bCouleurOrigineBarrePersonnalisee) {
        this.bCouleurOrigineBarrePersonnalisee = bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * Retourne le nombre de zones cliquables de la barre personnalisée
     * 
     * @return Nombre de zones définies dans le fichier SHP
     * @see #setiNombreZonesBarrePersonnalisee(int)
     * @see #lisFichierShp(String, ZoneTelecommande[])
     */
    public int getiNombreZonesBarrePersonnalisee() {
        return iNombreZonesBarrePersonnalisee;
    }

    /**
     * Définit le nombre de zones cliquables de la barre personnalisée
     * 
     * @param iNombreZonesBarrePersonnalisee Nombre de zones
     * @see #getiNombreZonesBarrePersonnalisee()
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
     * Obtient la taille (hauteur) de la barre de navigation personnalisée en pixels.
     * 
     * @return la taille de la barre personnalisée (valeur typique : 32-128 pixels)
     * @see #setTailleBarrePersonnalisee(double)
     */
    public double getTailleBarrePersonnalisee() {
        return tailleBarrePersonnalisee;
    }

    /**
     * Définit la taille (hauteur) de la barre de navigation personnalisée en pixels.
     * 
     * @param tailleBarrePersonnalisee la nouvelle taille en pixels (recommandé : 32-128)
     * @see #getTailleBarrePersonnalisee()
     */
    public void setTailleBarrePersonnalisee(double tailleBarrePersonnalisee) {
        this.tailleBarrePersonnalisee = tailleBarrePersonnalisee;
    }

    /**
     * Obtient la taille des icônes affichées dans la barre personnalisée.
     * 
     * @return la taille des icônes en pixels (valeur typique : 24-64 pixels)
     * @see #setTailleIconesBarrePersonnalisee(double)
     */
    public double getTailleIconesBarrePersonnalisee() {
        return tailleIconesBarrePersonnalisee;
    }

    /**
     * Définit la taille des icônes affichées dans la barre personnalisée.
     * 
     * @param tailleIconesBarrePersonnalisee la nouvelle taille des icônes en pixels (recommandé : 24-64)
     * @see #getTailleIconesBarrePersonnalisee()
     */
    public void setTailleIconesBarrePersonnalisee(double tailleIconesBarrePersonnalisee) {
        this.tailleIconesBarrePersonnalisee = tailleIconesBarrePersonnalisee;
    }

    /**
     * Obtient l'opacité de la barre de navigation personnalisée.
     * 
     * @return l'opacité (0.0 = transparent, 1.0 = opaque)
     * @see #setOpaciteBarrePersonnalisee(double)
     */
    public double getOpaciteBarrePersonnalisee() {
        return opaciteBarrePersonnalisee;
    }

    /**
     * Définit l'opacité de la barre de navigation personnalisée.
     * 
     * @param opaciteBarrePersonnalisee la nouvelle opacité (0.0 à 1.0)
     * @see #getOpaciteBarrePersonnalisee()
     */
    public void setOpaciteBarrePersonnalisee(double opaciteBarrePersonnalisee) {
        this.opaciteBarrePersonnalisee = opaciteBarrePersonnalisee;
    }

    /**
     * Obtient la position de la barre de navigation personnalisée sur l'écran.
     * 
     * @return la position ("top", "bottom", "left", "right")
     * @see #setStrPositionBarrePersonnalisee(String)
     */
    public String getStrPositionBarrePersonnalisee() {
        return strPositionBarrePersonnalisee;
    }

    /**
     * Définit la position de la barre de navigation personnalisée sur l'écran.
     * 
     * @param strPositionBarrePersonnalisee la nouvelle position ("top", "bottom", "left", "right")
     * @see #getStrPositionBarrePersonnalisee()
     */
    public void setStrPositionBarrePersonnalisee(String strPositionBarrePersonnalisee) {
        this.strPositionBarrePersonnalisee = strPositionBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée aux boutons de déplacement.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrDeplacementsBarrePersonnalisee(String)
     */
    public String getStrDeplacementsBarrePersonnalisee() {
        return strDeplacementsBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée aux boutons de déplacement.
     * 
     * @param strDeplacementsBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrDeplacementsBarrePersonnalisee()
     */
    public void setStrDeplacementsBarrePersonnalisee(String strDeplacementsBarrePersonnalisee) {
        this.strDeplacementsBarrePersonnalisee = strDeplacementsBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée aux boutons de zoom.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrZoomBarrePersonnalisee(String)
     */
    public String getStrZoomBarrePersonnalisee() {
        return strZoomBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée aux boutons de zoom.
     * 
     * @param strZoomBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrZoomBarrePersonnalisee()
     */
    public void setStrZoomBarrePersonnalisee(String strZoomBarrePersonnalisee) {
        this.strZoomBarrePersonnalisee = strZoomBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée au bouton d'information.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrInfoBarrePersonnalisee(String)
     */
    public String getStrInfoBarrePersonnalisee() {
        return strInfoBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée au bouton d'information.
     * 
     * @param strInfoBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrInfoBarrePersonnalisee()
     */
    public void setStrInfoBarrePersonnalisee(String strInfoBarrePersonnalisee) {
        this.strInfoBarrePersonnalisee = strInfoBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée au bouton d'aide.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrAideBarrePersonnalisee(String)
     */
    public String getStrAideBarrePersonnalisee() {
        return strAideBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée au bouton d'aide.
     * 
     * @param strAideBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrAideBarrePersonnalisee()
     */
    public void setStrAideBarrePersonnalisee(String strAideBarrePersonnalisee) {
        this.strAideBarrePersonnalisee = strAideBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée aux boutons de rotation.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrRotationBarrePersonnalisee(String)
     */
    public String getStrRotationBarrePersonnalisee() {
        return strRotationBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée aux boutons de rotation.
     * 
     * @param strRotationBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrRotationBarrePersonnalisee()
     */
    public void setStrRotationBarrePersonnalisee(String strRotationBarrePersonnalisee) {
        this.strRotationBarrePersonnalisee = strRotationBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée au bouton plein écran.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrPleinEcranBarrePersonnalisee(String)
     */
    public String getStrPleinEcranBarrePersonnalisee() {
        return strPleinEcranBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée au bouton plein écran.
     * 
     * @param strPleinEcranBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrPleinEcranBarrePersonnalisee()
     */
    public void setStrPleinEcranBarrePersonnalisee(String strPleinEcranBarrePersonnalisee) {
        this.strPleinEcranBarrePersonnalisee = strPleinEcranBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée au bouton de la souris.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrSourisBarrePersonnalisee(String)
     */
    public String getStrSourisBarrePersonnalisee() {
        return strSourisBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée au bouton de la souris.
     * 
     * @param strSourisBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrSourisBarrePersonnalisee()
     */
    public void setStrSourisBarrePersonnalisee(String strSourisBarrePersonnalisee) {
        this.strSourisBarrePersonnalisee = strSourisBarrePersonnalisee;
    }

    /**
     * Obtient la zone de la barre personnalisée affectée au bouton de visibilité.
     * 
     * @return le numéro de zone (commence à 0) ou null si non défini
     * @see #setStrVisibiliteBarrePersonnalisee(String)
     */
    public String getStrVisibiliteBarrePersonnalisee() {
        return strVisibiliteBarrePersonnalisee;
    }

    /**
     * Définit la zone de la barre personnalisée affectée au bouton de visibilité.
     * 
     * @param strVisibiliteBarrePersonnalisee le numéro de zone (0 à n-1)
     * @see #getStrVisibiliteBarrePersonnalisee()
     */
    public void setStrVisibiliteBarrePersonnalisee(String strVisibiliteBarrePersonnalisee) {
        this.strVisibiliteBarrePersonnalisee = strVisibiliteBarrePersonnalisee;
    }

    /**
     * Obtient le chemin vers l'image PNG de la barre personnalisée.
     * 
     * @return le chemin relatif ou absolu du fichier image (.png)
     * @see #setStrLienImageBarrePersonnalisee(String)
     */
    public String getStrLienImageBarrePersonnalisee() {
        return strLienImageBarrePersonnalisee;
    }

    /**
     * Définit le chemin vers l'image PNG de la barre personnalisée.
     * 
     * @param strLienImageBarrePersonnalisee le chemin du fichier image (.png)
     * @see #getStrLienImageBarrePersonnalisee()
     */
    public void setStrLienImageBarrePersonnalisee(String strLienImageBarrePersonnalisee) {
        this.strLienImageBarrePersonnalisee = strLienImageBarrePersonnalisee;
    }

    /**
     * Obtient le chemin vers le fichier SHP (shapefile) définissant les zones de la barre.
     * 
     * @return le chemin relatif ou absolu du fichier shapefile (.shp)
     * @see #setStrLien1BarrePersonnalisee(String)
     */
    public String getStrLien1BarrePersonnalisee() {
        return strLien1BarrePersonnalisee;
    }

    /**
     * Définit le chemin vers le fichier SHP (shapefile) définissant les zones de la barre.
     * 
     * @param strLien1BarrePersonnalisee le chemin du fichier shapefile (.shp)
     * @see #getStrLien1BarrePersonnalisee()
     */
    public void setStrLien1BarrePersonnalisee(String strLien1BarrePersonnalisee) {
        this.strLien1BarrePersonnalisee = strLien1BarrePersonnalisee;
    }

    /**
     * Obtient le second lien associé à la barre personnalisée.
     * Ce lien peut être utilisé pour des ressources additionnelles ou configurations.
     * 
     * @return le chemin ou URL du second lien
     * @see #setStrLien2BarrePersonnalisee(String)
     */
    public String getStrLien2BarrePersonnalisee() {
        return strLien2BarrePersonnalisee;
    }

    /**
     * Définit le second lien associé à la barre personnalisée.
     * 
     * @param strLien2BarrePersonnalisee le chemin ou URL du second lien
     * @see #getStrLien2BarrePersonnalisee()
     */
    public void setStrLien2BarrePersonnalisee(String strLien2BarrePersonnalisee) {
        this.strLien2BarrePersonnalisee = strLien2BarrePersonnalisee;
    }

    /**
     * Obtient l'image de la barre personnalisée avec les couleurs appliquées.
     * Cette image contient le rendu final de la barre avec toutes les modifications visuelles.
     * 
     * @return l'image modifiable (WritableImage) de la barre avec couleurs
     * @see #setWiBarrePersonnaliseeCouleur(WritableImage)
     */
    public WritableImage getWiBarrePersonnaliseeCouleur() {
        return wiBarrePersonnaliseeCouleur;
    }

    /**
     * Définit l'image de la barre personnalisée avec les couleurs appliquées.
     * 
     * @param wiBarrePersonnaliseeCouleur l'image modifiable de la barre avec couleurs
     * @see #getWiBarrePersonnaliseeCouleur()
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
     * @return the bAfficheDescription
     */
    public boolean isbAfficheDescription() {
        return bAfficheDescription;
    }

    /**
     * @param bAfficheDescription the bAfficheDescription to set
     */
    public void setbAfficheDescription(boolean bAfficheDescription) {
        this.bAfficheDescription = bAfficheDescription;
    }

    /**
     * @return the cbAfficheDescription checkbox
     */
    public CheckBox getCbAfficheDescription() {
        return cbAfficheDescription;
    }

    /**
     * @return the poDescription panel
     */
    public PaneOutil getPoDescription() {
        return poDescription;
    }

    /**
     * Active ou désactive le mode chargement pour éviter les mises à jour intempestives
     * @param bChargement true si on est en train de charger des données
     */
    public void setbChargementEnCours(boolean bChargement) {
        this.bChargementEnCours = bChargement;
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
     * Obtient le style de la police utilisée pour le titre du panoramique.
     * 
     * @return le style CSS de la police (ex: "normal", "italic", "bold")
     * @see #setStrTitrePoliceStyle(String)
     */
    public String getStrTitrePoliceStyle() {
        return strTitrePoliceStyle;
    }

    /**
     * Définit le style de la police utilisée pour le titre du panoramique.
     * 
     * @param strTitrePoliceStyle le style CSS ("normal", "italic", "bold", etc.)
     * @see #getStrTitrePoliceStyle()
     */
    public void setStrTitrePoliceStyle(String strTitrePoliceStyle) {
        this.strTitrePoliceStyle = strTitrePoliceStyle;
    }

    /**
     * Obtient la taille de la police utilisée pour le titre du panoramique.
     * 
     * @return la taille de police sous forme de chaîne (ex: "24", "32")
     * @see #setStrTitrePoliceTaille(String)
     */
    public String getStrTitrePoliceTaille() {
        return strTitrePoliceTaille;
    }

    /**
     * Définit la taille de la police utilisée pour le titre du panoramique.
     * 
     * @param strTitrePoliceTaille la taille en pixels sous forme de chaîne (ex: "24", "32")
     * @see #getStrTitrePoliceTaille()
     */
    public void setStrTitrePoliceTaille(String strTitrePoliceTaille) {
        this.strTitrePoliceTaille = strTitrePoliceTaille;
    }

    /**
     * Obtient la couleur du texte du titre du panoramique.
     * 
     * @return la couleur au format CSS (ex: "#FFFFFF", "rgb(255,255,255)")
     * @see #setStrCouleurTitre(String)
     */
    public String getStrCouleurTitre() {
        return strCouleurTitre;
    }

    /**
     * Définit la couleur du texte du titre du panoramique.
     * 
     * @param strCouleurTitre la couleur au format CSS (hex, rgb, ou nom)
     * @see #getStrCouleurTitre()
     */
    public void setStrCouleurTitre(String strCouleurTitre) {
        this.strCouleurTitre = strCouleurTitre;
    }

    /**
     * Obtient la couleur de fond du titre du panoramique.
     * 
     * @return la couleur de fond au format CSS (ex: "#000000", "rgba(0,0,0,0.8)")
     * @see #setStrCouleurFondTitre(String)
     */
    public String getStrCouleurFondTitre() {
        return strCouleurFondTitre;
    }

    /**
     * Définit la couleur de fond du titre du panoramique.
     * 
     * @param strCouleurFondTitre la couleur de fond au format CSS (hex, rgb, rgba, ou nom)
     * @see #getStrCouleurFondTitre()
     */
    public void setStrCouleurFondTitre(String strCouleurFondTitre) {
        this.strCouleurFondTitre = strCouleurFondTitre;
    }

    /**
     * Obtient l'opacité du fond du titre du panoramique.
     * 
     * @return l'opacité du fond (0.0 = transparent, 1.0 = opaque)
     * @see #setTitreOpacite(double)
     */
    public double getTitreOpacite() {
        return titreOpacite;
    }

    /**
     * Définit l'opacité du fond du titre du panoramique.
     * 
     * @param titreOpacite l'opacité (0.0 à 1.0)
     * @see #getTitreOpacite()
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
     * Indique si la boussole est affichée dans la visualisation.
     * 
     * @return true si la boussole est visible, false sinon
     */
    public boolean isbAfficheBoussole() {
        return bAfficheBoussole;
    }

    /**
     * Active ou désactive l'affichage de la boussole dans la visualisation.
     * 
     * @param bAfficheBoussole true pour afficher la boussole, false pour la masquer
     */
    public void setbAfficheBoussole(boolean bAfficheBoussole) {
        this.bAfficheBoussole = bAfficheBoussole;
    }

    /**
     * Retourne le nom du fichier image de la boussole utilisée.
     * 
     * @return Le nom du fichier image de la boussole (ex: "boussole1.png")
     */
    public String getStrImageBoussole() {
        return strImageBoussole;
    }

    /**
     * Définit l'image de la boussole à afficher.
     * 
     * @param strImageBoussole Le nom du fichier image de la boussole depuis le répertoire des thèmes
     */
    public void setStrImageBoussole(String strImageBoussole) {
        this.strImageBoussole = strImageBoussole;
    }

    /**
     * Retourne la position de la boussole dans l'interface.
     * 
     * @return Position de la boussole : "HG" (haut-gauche), "HD" (haut-droite), 
     *         "BG" (bas-gauche), "BD" (bas-droite), ou "ABS" (position absolue)
     */
    public String getStrPositionBoussole() {
        return strPositionBoussole;
    }

    /**
     * Définit la position de la boussole dans l'interface.
     * 
     * @param strPositionBoussole Position : "HG", "HD", "BG", "BD" ou "ABS"
     */
    public void setStrPositionBoussole(String strPositionBoussole) {
        this.strPositionBoussole = strPositionBoussole;
    }

    /**
     * Retourne le décalage horizontal de la boussole par rapport à sa position de référence.
     * 
     * @return Le décalage X en pixels
     */
    public double getOffsetXBoussole() {
        return offsetXBoussole;
    }

    /**
     * Définit le décalage horizontal de la boussole.
     * 
     * @param offsetXBoussole Le décalage X en pixels (peut être négatif)
     */
    public void setOffsetXBoussole(double offsetXBoussole) {
        this.offsetXBoussole = offsetXBoussole;
    }

    /**
     * Retourne le décalage vertical de la boussole par rapport à sa position de référence.
     * 
     * @return Le décalage Y en pixels
     */
    public double getOffsetYBoussole() {
        return offsetYBoussole;
    }

    /**
     * Définit le décalage vertical de la boussole.
     * 
     * @param offsetYBoussole Le décalage Y en pixels (peut être négatif)
     */
    public void setOffsetYBoussole(double offsetYBoussole) {
        this.offsetYBoussole = offsetYBoussole;
    }

    /**
     * Retourne la taille de la boussole en pixels.
     * 
     * @return La taille de la boussole (largeur et hauteur)
     */
    public double getTailleBoussole() {
        return tailleBoussole;
    }

    /**
     * Définit la taille de la boussole.
     * 
     * @param tailleBoussole La taille en pixels (valeur positive)
     */
    public void setTailleBoussole(double tailleBoussole) {
        this.tailleBoussole = tailleBoussole;
    }

    /**
     * Retourne l'opacité de la boussole.
     * 
     * @return L'opacité (0.0 = transparente, 1.0 = opaque)
     */
    public double getOpaciteBoussole() {
        return opaciteBoussole;
    }

    /**
     * Définit l'opacité de la boussole.
     * 
     * @param opaciteBoussole L'opacité (0.0 à 1.0)
     */
    public void setOpaciteBoussole(double opaciteBoussole) {
        this.opaciteBoussole = opaciteBoussole;
    }

    /**
     * Indique si l'aiguille de la boussole doit tourner avec la vue.
     * 
     * @return true si l'aiguille est mobile et suit l'orientation de la vue, false si elle reste fixe
     */
    public boolean isbAiguilleMobileBoussole() {
        return bAiguilleMobileBoussole;
    }

    /**
     * Active ou désactive la rotation de l'aiguille de la boussole.
     * 
     * @param bAiguilleMobileBoussole true pour une aiguille mobile, false pour une aiguille fixe
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
     * Indique si le masque est affiché dans la visualisation.
     * 
     * @return true si le masque est visible, false sinon
     */
    public boolean isbAfficheMasque() {
        return bAfficheMasque;
    }

    /**
     * Active ou désactive l'affichage du masque.
     * 
     * @param bAfficheMasque true pour afficher le masque, false pour le masquer
     */
    public void setbAfficheMasque(boolean bAfficheMasque) {
        this.bAfficheMasque = bAfficheMasque;
    }

    /**
     * Retourne le nom du fichier image du masque utilisé.
     * 
     * @return Le nom du fichier image du masque (ex: "masque1.png")
     */
    public String getStrImageMasque() {
        return strImageMasque;
    }

    /**
     * Définit l'image du masque à afficher.
     * 
     * @param strImageMasque Le nom du fichier image du masque depuis le répertoire des thèmes
     */
    public void setStrImageMasque(String strImageMasque) {
        this.strImageMasque = strImageMasque;
    }

    /**
     * Retourne la position du masque dans l'interface.
     * 
     * @return Position du masque : "HG" (haut-gauche), "HD" (haut-droite), 
     *         "BG" (bas-gauche), "BD" (bas-droite), ou "ABS" (position absolue)
     */
    public String getStrPositionMasque() {
        return strPositionMasque;
    }

    /**
     * Définit la position du masque dans l'interface.
     * 
     * @param strPositionMasque Position : "HG", "HD", "BG", "BD" ou "ABS"
     */
    public void setStrPositionMasque(String strPositionMasque) {
        this.strPositionMasque = strPositionMasque;
    }

    /**
     * Retourne le décalage horizontal du masque par rapport à sa position de référence.
     * 
     * @return Le décalage X en pixels
     */
    public double getdXMasque() {
        return dXMasque;
    }

    /**
     * Définit le décalage horizontal du masque.
     * 
     * @param dXMasque Le décalage X en pixels (peut être négatif)
     */
    public void setdXMasque(double dXMasque) {
        this.dXMasque = dXMasque;
    }

    /**
     * Retourne le décalage vertical du masque par rapport à sa position de référence.
     * 
     * @return Le décalage Y en pixels
     */
    public double getdYMasque() {
        return dYMasque;
    }

    /**
     * Définit le décalage vertical du masque.
     * 
     * @param dYMasque Le décalage Y en pixels (peut être négatif)
     */
    public void setdYMasque(double dYMasque) {
        this.dYMasque = dYMasque;
    }

    /**
     * Retourne la taille du masque en pixels.
     * 
     * @return La taille du masque (largeur)
     */
    public double getTailleMasque() {
        return tailleMasque;
    }

    /**
     * Définit la taille du masque.
     * 
     * @param tailleMasque La taille en pixels (valeur positive)
     */
    public void setTailleMasque(double tailleMasque) {
        this.tailleMasque = tailleMasque;
    }

    /**
     * Retourne l'opacité du masque.
     * 
     * @return L'opacité (0.0 = transparent, 1.0 = opaque)
     */
    public double getOpaciteMasque() {
        return opaciteMasque;
    }

    /**
     * Définit l'opacité du masque.
     * 
     * @param opaciteMasque L'opacité (0.0 à 1.0)
     */
    public void setOpaciteMasque(double opaciteMasque) {
        this.opaciteMasque = opaciteMasque;
    }

    /**
     * Indique si le masque masque la barre de navigation.
     * 
     * @return true si la navigation est masquée par le masque, false sinon
     */
    public boolean isbMasqueNavigation() {
        return bMasqueNavigation;
    }

    /**
     * Active ou désactive le masquage de la barre de navigation.
     * 
     * @param bMasqueNavigation true pour masquer la navigation, false pour la laisser visible
     */
    public void setbMasqueNavigation(boolean bMasqueNavigation) {
        this.bMasqueNavigation = bMasqueNavigation;
    }

    /**
     * Indique si le masque masque la boussole.
     * 
     * @return true si la boussole est masquée par le masque, false sinon
     */
    public boolean isbMasqueBoussole() {
        return bMasqueBoussole;
    }

    /**
     * Active ou désactive le masquage de la boussole.
     * 
     * @param bMasqueBoussole true pour masquer la boussole, false pour la laisser visible
     */
    public void setbMasqueBoussole(boolean bMasqueBoussole) {
        this.bMasqueBoussole = bMasqueBoussole;
    }

    /**
     * Indique si le masque masque le titre.
     * 
     * @return true si le titre est masqué par le masque, false sinon
     */
    public boolean isbMasqueTitre() {
        return bMasqueTitre;
    }

    /**
     * Active ou désactive le masquage du titre.
     * 
     * @param bMasqueTitre true pour masquer le titre, false pour le laisser visible
     */
    public void setbMasqueTitre(boolean bMasqueTitre) {
        this.bMasqueTitre = bMasqueTitre;
    }

    /**
     * Indique si le masque masque le plan.
     * 
     * @return true si le plan est masqué par le masque, false sinon
     */
    public boolean isbMasquePlan() {
        return bMasquePlan;
    }

    /**
     * Active ou désactive le masquage du plan.
     * 
     * @param bMasquePlan true pour masquer le plan, false pour le laisser visible
     */
    public void setbMasquePlan(boolean bMasquePlan) {
        this.bMasquePlan = bMasquePlan;
    }

    /**
     * Indique si le masque masque les réseaux sociaux.
     * 
     * @return true si les réseaux sociaux sont masqués par le masque, false sinon
     */
    public boolean isbMasqueReseaux() {
        return bMasqueReseaux;
    }

    /**
     * Active ou désactive le masquage des réseaux sociaux.
     * 
     * @param bMasqueReseaux true pour masquer les réseaux sociaux, false pour les laisser visibles
     */
    public void setbMasqueReseaux(boolean bMasqueReseaux) {
        this.bMasqueReseaux = bMasqueReseaux;
    }

    /**
     * Indique si le masque masque les vignettes.
     * 
     * @return true si les vignettes sont masquées par le masque, false sinon
     */
    public boolean isbMasqueVignettes() {
        return bMasqueVignettes;
    }

    /**
     * Active ou désactive le masquage des vignettes.
     * 
     * @param bMasqueVignettes true pour masquer les vignettes, false pour les laisser visibles
     */
    public void setbMasqueVignettes(boolean bMasqueVignettes) {
        this.bMasqueVignettes = bMasqueVignettes;
    }

    /**
     * Indique si le masque masque le menu déroulant (combo).
     * 
     * @return true si le combo est masqué par le masque, false sinon
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
     * Indique si les boutons de réseaux sociaux sont affichés.
     * 
     * @return true si les réseaux sociaux sont visibles, false sinon
     */
    public boolean isbAfficheReseauxSociaux() {
        return bAfficheReseauxSociaux;
    }

    /**
     * Active ou désactive l'affichage des boutons de réseaux sociaux.
     * 
     * @param bAfficheReseauxSociaux true pour afficher les réseaux sociaux, false pour les masquer
     */
    public void setbAfficheReseauxSociaux(boolean bAfficheReseauxSociaux) {
        this.bAfficheReseauxSociaux = bAfficheReseauxSociaux;
    }

    /**
     * Retourne le nom du fichier image pour le bouton Twitter/X.
     * 
     * @return Le nom du fichier image Twitter (ex: "twitter.png")
     */
    public String getStrImageReseauxSociauxTwitter() {
        return strImageReseauxSociauxTwitter;
    }

    /**
     * Définit l'image pour le bouton Twitter/X.
     * 
     * @param strImageReseauxSociauxTwitter Le nom du fichier image depuis le répertoire des thèmes
     */
    public void setStrImageReseauxSociauxTwitter(String strImageReseauxSociauxTwitter) {
        this.strImageReseauxSociauxTwitter = strImageReseauxSociauxTwitter;
    }

    /**
     * Retourne le nom du fichier image pour le bouton Facebook/Meta.
     * 
     * @return Le nom du fichier image Meta (ex: "facebook.png")
     */
    public String getStrImageReseauxSociauxMeta() {
        return strImageReseauxSociauxMeta;
    }

    /**
     * Définit l'image pour le bouton Facebook/Meta.
     * 
     * @param strImageReseauxSociauxMeta Le nom du fichier image depuis le répertoire des thèmes
     */
    public void setStrImageReseauxSociauxMeta(String strImageReseauxSociauxMeta) {
        this.strImageReseauxSociauxMeta = strImageReseauxSociauxMeta;
    }

    /**
     * Retourne le nom du fichier image pour le bouton Email.
     * 
     * @return Le nom du fichier image Email (ex: "email.png")
     */
    public String getStrImageReseauxSociauxEmail() {
        return strImageReseauxSociauxEmail;
    }

    /**
     * Définit l'image pour le bouton Email.
     * 
     * @param strImageReseauxSociauxEmail Le nom du fichier image depuis le répertoire des thèmes
     */
    public void setStrImageReseauxSociauxEmail(String strImageReseauxSociauxEmail) {
        this.strImageReseauxSociauxEmail = strImageReseauxSociauxEmail;
    }

    /**
     * Retourne la position des boutons de réseaux sociaux dans l'interface.
     * 
     * @return Position : "HG" (haut-gauche), "HD" (haut-droite), "BG" (bas-gauche), 
     *         "BD" (bas-droite), ou "ABS" (position absolue)
     */
    public String getStrPositionReseauxSociaux() {
        return strPositionReseauxSociaux;
    }

    /**
     * Définit la position des boutons de réseaux sociaux.
     * 
     * @param strPositionReseauxSociaux Position : "HG", "HD", "BG", "BD" ou "ABS"
     */
    public void setStrPositionReseauxSociaux(String strPositionReseauxSociaux) {
        this.strPositionReseauxSociaux = strPositionReseauxSociaux;
    }

    /**
     * Retourne le décalage horizontal des réseaux sociaux par rapport à leur position de référence.
     * 
     * @return Le décalage X en pixels
     */
    public double getdXReseauxSociaux() {
        return dXReseauxSociaux;
    }

    /**
     * Définit le décalage horizontal des réseaux sociaux.
     * 
     * @param dXReseauxSociaux Le décalage X en pixels (peut être négatif)
     */
    public void setdXReseauxSociaux(double dXReseauxSociaux) {
        this.dXReseauxSociaux = dXReseauxSociaux;
    }

    /**
     * Retourne le décalage vertical des réseaux sociaux par rapport à leur position de référence.
     * 
     * @return Le décalage Y en pixels
     */
    public double getdYReseauxSociaux() {
        return dYReseauxSociaux;
    }

    /**
     * Définit le décalage vertical des réseaux sociaux.
     * 
     * @param dYReseauxSociaux Le décalage Y en pixels (peut être négatif)
     */
    public void setdYReseauxSociaux(double dYReseauxSociaux) {
        this.dYReseauxSociaux = dYReseauxSociaux;
    }

    /**
     * Retourne la taille des boutons de réseaux sociaux.
     * 
     * @return La taille en pixels (largeur et hauteur)
     */
    public double getTailleReseauxSociaux() {
        return tailleReseauxSociaux;
    }

    /**
     * Définit la taille des boutons de réseaux sociaux.
     * 
     * @param tailleReseauxSociaux La taille en pixels (valeur positive)
     */
    public void setTailleReseauxSociaux(double tailleReseauxSociaux) {
        this.tailleReseauxSociaux = tailleReseauxSociaux;
    }

    /**
     * Retourne l'opacité des boutons de réseaux sociaux.
     * 
     * @return L'opacité (0.0 = transparents, 1.0 = opaques)
     */
    public double getOpaciteReseauxSociaux() {
        return opaciteReseauxSociaux;
    }

    /**
     * Définit l'opacité des boutons de réseaux sociaux.
     * 
     * @param opaciteReseauxSociaux L'opacité (0.0 à 1.0)
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
     * @return the bReseauxSociauxMeta
     */
    public boolean isbReseauxSociauxMeta() {
        return bReseauxSociauxMeta;
    }

    /**
     * @param bReseauxSociauxMeta the bReseauxSociauxMeta to set
     */
    public void setbReseauxSociauxMeta(boolean bReseauxSociauxMeta) {
        this.bReseauxSociauxMeta = bReseauxSociauxMeta;
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
     * Indique si les vignettes de navigation sont affichées.
     * 
     * @return true si les vignettes sont visibles, false sinon
     */
    public boolean isbAfficheVignettes() {
        return bAfficheVignettes;
    }

    /**
     * Active ou désactive l'affichage des vignettes de navigation.
     * 
     * @param bAfficheVignettes true pour afficher les vignettes, false pour les masquer
     */
    public void setbAfficheVignettes(boolean bAfficheVignettes) {
        this.bAfficheVignettes = bAfficheVignettes;
    }

    /**
     * Retourne la couleur de fond des vignettes au format hexadécimal.
     * 
     * @return La couleur au format "#RRGGBB"
     */
    public String getStrCouleurFondVignettes() {
        return strCouleurFondVignettes;
    }

    /**
     * Définit la couleur de fond des vignettes.
     * 
     * @param strCouleurFondVignettes La couleur au format "#RRGGBB"
     */
    public void setStrCouleurFondVignettes(String strCouleurFondVignettes) {
        this.strCouleurFondVignettes = strCouleurFondVignettes;
    }

    /**
     * Retourne la couleur du texte des vignettes au format hexadécimal.
     * 
     * @return La couleur au format "#RRGGBB"
     */
    public String getStrCouleurTexteVignettes() {
        return strCouleurTexteVignettes;
    }

    /**
     * Définit la couleur du texte des vignettes.
     * 
     * @param strCouleurTexteVignettes La couleur au format "#RRGGBB"
     */
    public void setStrCouleurTexteVignettes(String strCouleurTexteVignettes) {
        this.strCouleurTexteVignettes = strCouleurTexteVignettes;
    }

    /**
     * Retourne la position des vignettes dans l'interface.
     * 
     * @return Position : "HG" (haut-gauche), "HD" (haut-droite), "BG" (bas-gauche), 
     *         "BD" (bas-droite), ou "ABS" (position absolue)
     */
    public String getStrPositionVignettes() {
        return strPositionVignettes;
    }

    /**
     * Définit la position des vignettes.
     * 
     * @param strPositionVignettes Position : "HG", "HD", "BG", "BD" ou "ABS"
     */
    public void setStrPositionVignettes(String strPositionVignettes) {
        this.strPositionVignettes = strPositionVignettes;
    }

    /**
     * Retourne la taille des images dans les vignettes.
     * 
     * @return La taille en pixels (largeur et hauteur)
     */
    public double getTailleImageVignettes() {
        return tailleImageVignettes;
    }

    /**
     * Définit la taille des images dans les vignettes.
     * 
     * @param tailleImageVignettes La taille en pixels (valeur positive)
     */
    public void setTailleImageVignettes(double tailleImageVignettes) {
        this.tailleImageVignettes = tailleImageVignettes;
    }

    /**
     * Retourne l'opacité des vignettes.
     * 
     * @return L'opacité (0.0 = transparentes, 1.0 = opaques)
     */
    public double getOpaciteVignettes() {
        return opaciteVignettes;
    }

    /**
     * Définit l'opacité des vignettes.
     * 
     * @param opaciteVignettes L'opacité (0.0 à 1.0)
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
     * @return booleen definissant si la carte est repliée au démarrage de la
     * visite
     */
    public boolean isbReplieDemarrageCarte() {
        return bReplieDemarrageCarte;
    }

    /**
     * @param bReplieDemarrageCarte definit si la carte est repliée au démarrage
     * de la visite
     */
    public void setbReplieDemarrageCarte(boolean bReplieDemarrageCarte) {
        this.bReplieDemarrageCarte = bReplieDemarrageCarte;
    }

    /**
     * @return booleen definissant si le plan est repliée au démarrage de la
     * visite
     */
    public boolean isbReplieDemarragePlan() {
        return bReplieDemarragePlan;
    }

    /**
     * @param bReplieDemarragePlan definit si le plan est repliée au démarrage
     * de la visite
     */
    public void setbReplieDemarragePlan(boolean bReplieDemarragePlan) {
        this.bReplieDemarragePlan = bReplieDemarragePlan;
    }

    /**
     * @return booleen definissant si les vignettes est repliée au démarrage de
     * la visite
     */
    public boolean isbReplieDemarrageVignettes() {
        return bReplieDemarrageVignettes;
    }

    /**
     * @param bReplieDemarrageVignettes definit si les vignettes est repliée au
     * démarrage de la visite
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

    /**
     * @return the bHotspotsPanoAnimesDefaut
     */
    public boolean isbHotspotsPanoAnimesDefaut() {
        return bHotspotsPanoAnimesDefaut;
    }

    /**
     * @param bHotspotsPanoAnimesDefaut the bHotspotsPanoAnimesDefaut to set
     */
    public void setbHotspotsPanoAnimesDefaut(boolean bHotspotsPanoAnimesDefaut) {
        this.bHotspotsPanoAnimesDefaut = bHotspotsPanoAnimesDefaut;
    }

    /**
     * @return the bHotspotsPhotoAnimesDefaut
     */
    public boolean isbHotspotsPhotoAnimesDefaut() {
        return bHotspotsPhotoAnimesDefaut;
    }

    /**
     * @param bHotspotsPhotoAnimesDefaut the bHotspotsPhotoAnimesDefaut to set
     */
    public void setbHotspotsPhotoAnimesDefaut(boolean bHotspotsPhotoAnimesDefaut) {
        this.bHotspotsPhotoAnimesDefaut = bHotspotsPhotoAnimesDefaut;
    }

    /**
     * @return the bHotspotsHTMLAnimesDefaut
     */
    public boolean isbHotspotsHTMLAnimesDefaut() {
        return bHotspotsHTMLAnimesDefaut;
    }

    /**
     * @param bHotspotsHTMLAnimesDefaut the bHotspotsHTMLAnimesDefaut to set
     */
    public void setbHotspotsHTMLAnimesDefaut(boolean bHotspotsHTMLAnimesDefaut) {
        this.bHotspotsHTMLAnimesDefaut = bHotspotsHTMLAnimesDefaut;
    }

    /**
     * @return le type d'animation par défaut pour les hotspots panoramiques
     */
    public String getStrTypeAnimationPanoDefaut() {
        return strTypeAnimationPanoDefaut;
    }

    /**
     * @param strTypeAnimationPanoDefaut le type d'animation par défaut à définir
     */
    public void setStrTypeAnimationPanoDefaut(String strTypeAnimationPanoDefaut) {
        this.strTypeAnimationPanoDefaut = strTypeAnimationPanoDefaut;
    }

    /**
     * @return le type d'animation par défaut pour les hotspots photo
     */
    public String getStrTypeAnimationPhotoDefaut() {
        return strTypeAnimationPhotoDefaut;
    }

    /**
     * @param strTypeAnimationPhotoDefaut le type d'animation par défaut à définir
     */
    public void setStrTypeAnimationPhotoDefaut(String strTypeAnimationPhotoDefaut) {
        this.strTypeAnimationPhotoDefaut = strTypeAnimationPhotoDefaut;
    }

    /**
     * @return le type d'animation par défaut pour les hotspots HTML
     */
    public String getStrTypeAnimationHTMLDefaut() {
        return strTypeAnimationHTMLDefaut;
    }

    /**
     * @param strTypeAnimationHTMLDefaut le type d'animation par défaut à définir
     */
    public void setStrTypeAnimationHTMLDefaut(String strTypeAnimationHTMLDefaut) {
        this.strTypeAnimationHTMLDefaut = strTypeAnimationHTMLDefaut;
    }

    /**
     * @return the bHotspotsPanoAgrandisDefaut
     */
    public boolean isbHotspotsPanoAgrandisDefaut() {
        return bHotspotsPanoAgrandisDefaut;
    }

    /**
     * @param bHotspotsPanoAgrandisDefaut the bHotspotsPanoAgrandisDefaut to set
     */
    public void setbHotspotsPanoAgrandisDefaut(boolean bHotspotsPanoAgrandisDefaut) {
        this.bHotspotsPanoAgrandisDefaut = bHotspotsPanoAgrandisDefaut;
    }

    /**
     * @return the bHotspotsPhotoAgrandisDefaut
     */
    public boolean isbHotspotsPhotoAgrandisDefaut() {
        return bHotspotsPhotoAgrandisDefaut;
    }

    /**
     * @param bHotspotsPhotoAgrandisDefaut the bHotspotsPhotoAgrandisDefaut to set
     */
    public void setbHotspotsPhotoAgrandisDefaut(boolean bHotspotsPhotoAgrandisDefaut) {
        this.bHotspotsPhotoAgrandisDefaut = bHotspotsPhotoAgrandisDefaut;
    }

    /**
     * @return the bHotspotsHTMLAgrandisDefaut
     */
    public boolean isbHotspotsHTMLAgrandisDefaut() {
        return bHotspotsHTMLAgrandisDefaut;
    }

    /**
     * @param bHotspotsHTMLAgrandisDefaut the bHotspotsHTMLAgrandisDefaut to set
     */
    public void setbHotspotsHTMLAgrandisDefaut(boolean bHotspotsHTMLAgrandisDefaut) {
        this.bHotspotsHTMLAgrandisDefaut = bHotspotsHTMLAgrandisDefaut;
    }

    /**
     * @return the couleurFondTheme
     */
    public Color getCouleurFondTheme() {
        return couleurFondTheme;
    }

    /**
     * @param couleurFondTheme the couleurFondTheme to set
     */
    public void setCouleurFondTheme(Color couleurFondTheme) {
        this.couleurFondTheme = couleurFondTheme;
    }

    /**
     * @return la couleur des hotspots panoramiques
     */
    public Color getCouleurHotspots() {
        return couleurHotspots;
    }

    /**
     * @return la couleur des hotspots photo
     */
    public Color getCouleurHotspotsPhoto() {
        return couleurHotspotsPhoto;
    }

    /**
     * @return la couleur des hotspots HTML
     */
    public Color getCouleurHotspotsHTML() {
        return couleurHotspotsHTML;
    }

    /**
     * @return the opaciteTheme
     */
    public double getOpaciteTheme() {
        return opaciteTheme;
    }

    /**
     * @return the iCalqueTitre
     */
    public int getiCalqueTitre() {
        return iCalqueTitre;
    }

    /**
     * @param iCalqueTitre the iCalqueTitre to set
     */
    public void setiCalqueTitre(int iCalqueTitre) {
        this.iCalqueTitre = iCalqueTitre;
    }

    /**
     * @return the iCalqueBarreClassique
     */
    public int getiCalqueBarreClassique() {
        return iCalqueBarreClassique;
    }

    /**
     * @param iCalqueBarreClassique the iCalqueBarreClassique to set
     */
    public void setiCalqueBarreClassique(int iCalqueBarreClassique) {
        this.iCalqueBarreClassique = iCalqueBarreClassique;
    }

    /**
     * @return the iCalqueBarrePersonnalisée
     */
    public int getiCalqueBarrePersonnalisee() {
        return iCalqueBarrePersonnalisee;
    }

    /**
     * @param iCalqueBarrePersonnalisee the iCalqueBarrePersonnalisee to set
     */
    public void setiCalqueBarrePersonnalisee(int iCalqueBarrePersonnalisee) {
        this.iCalqueBarrePersonnalisee = iCalqueBarrePersonnalisee;
    }

    /**
     * @return the iCalqueMasquage
     */
    public int getiCalqueMasquage() {
        return iCalqueMasquage;
    }

    /**
     * @param iCalqueMasquage the iCalqueMasquage to set
     */
    public void setiCalqueMasquage(int iCalqueMasquage) {
        this.iCalqueMasquage = iCalqueMasquage;
    }

    /**
     * @return the iCalqueVisiteAuto
     */
    public int getiCalqueVisiteAuto() {
        return iCalqueVisiteAuto;
    }

    /**
     * @param iCalqueVisiteAuto the iCalqueVisiteAuto to set
     */
    public void setiCalqueVisiteAuto(int iCalqueVisiteAuto) {
        this.iCalqueVisiteAuto = iCalqueVisiteAuto;
    }

    /**
     * @return the iCalquePartage
     */
    public int getiCalquePartage() {
        return iCalquePartage;
    }

    /**
     * @param iCalquePartage the iCalquePartage to set
     */
    public void setiCalquePartage(int iCalquePartage) {
        this.iCalquePartage = iCalquePartage;
    }

    /**
     * @return the iCalquePlan
     */
    public int getiCalquePlan() {
        return iCalquePlan;
    }

    /**
     * @param iCalquePlan the iCalquePlan to set
     */
    public void setiCalquePlan(int iCalquePlan) {
        this.iCalquePlan = iCalquePlan;
    }

    /**
     * @return the iCalqueCarte
     */
    public int getiCalqueCarte() {
        return iCalqueCarte;
    }

    /**
     * @param iCalqueCarte the iCalqueCarte to set
     */
    public void setiCalqueCarte(int iCalqueCarte) {
        this.iCalqueCarte = iCalqueCarte;
    }

    /**
     * @return the iCalqueBoussole
     */
    public int getiCalqueBoussole() {
        return iCalqueBoussole;
    }

    /**
     * @param iCalqueBoussole the iCalqueBoussole to set
     */
    public void setiCalqueBoussole(int iCalqueBoussole) {
        this.iCalqueBoussole = iCalqueBoussole;
    }

    /**
     * @return the iCalqueVignettes
     */
    public int getiCalqueVignettes() {
        return iCalqueVignettes;
    }

    /**
     * @param iCalqueVignettes the iCalqueVignettes to set
     */
    public void setiCalqueVignettes(int iCalqueVignettes) {
        this.iCalqueVignettes = iCalqueVignettes;
    }

    /**
     * @return the iCalqueMenuPanoramiques
     */
    public int getiCalqueMenuPanoramiques() {
        return iCalqueMenuPanoramiques;
    }

    /**
     * @param iCalqueMenuPanoramiques the iCalqueMenuPanoramiques to set
     */
    public void setiCalqueMenuPanoramiques(int iCalqueMenuPanoramiques) {
        this.iCalqueMenuPanoramiques = iCalqueMenuPanoramiques;
    }

    /**
     * @return the iCalqueSuivPrec
     */
    public int getiCalqueSuivPrec() {
        return iCalqueSuivPrec;
    }

    /**
     * @param iCalqueSuivPrec the iCalqueSuivPrec to set
     */
    public void setiCalqueSuivPrec(int iCalqueSuivPrec) {
        this.iCalqueSuivPrec = iCalqueSuivPrec;
    }

    /**
     * @return the spOutils
     */
    public ScrollPane getSpOutils() {
        return spOutils;
    }

    /**
     * @param spOutils the spOutils to set
     */
    public void setSpOutils(ScrollPane spOutils) {
        this.spOutils = spOutils;
    }

    /**
     * @return the apVis
     */
    public AnchorPane getApVis() {
        return apVis;
    }

    /**
     * @param apVis the apVis to set
     */
    public void setApVis(AnchorPane apVis) {
        this.apVis = apVis;
    }
    
    /**
     * Met à jour les couleurs de fond des panneaux d'icônes de hotspots
     * selon le thème actuel (clair ou sombre)
     */
    public void mettreAJourCouleursHotspots() {
        // Déterminer la couleur de fond selon le thème
        boolean estThemeSombre = ThemeManager.getCurrentTheme().isDark();
        String couleurFond = estThemeSombre ? "#3a3a3a" : "#e8e8e8";
        String style = "-fx-background-color: " + couleurFond + "; -fx-background-radius: 5px;";
        
        // Mettre à jour les trois panneaux d'icônes de hotspots
        if (getSpOutils() != null && getSpOutils().getContent() != null) {
            VBox vbOutils = (VBox) getSpOutils().getContent();
            
            // Parcourir les enfants pour trouver les panneaux de hotspots
            for (javafx.scene.Node node : vbOutils.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane ap = (AnchorPane) node;
                    
                    // Chercher les PaneOutil qui contiennent les hotspots
                    for (javafx.scene.Node child : ap.getChildren()) {
                        if (child instanceof AnchorPane) {
                            AnchorPane apChild = (AnchorPane) child;
                            
                            // Chercher les ScrollPane d'icônes
                            for (javafx.scene.Node scrollNode : apChild.getChildren()) {
                                if (scrollNode instanceof ScrollPane) {
                                    ScrollPane sp = (ScrollPane) scrollNode;
                                    
                                    // Vérifier si c'est bien un ScrollPane d'icônes de hotspots
                                    // (ils ont une hauteur fixe de 200px)
                                    if (sp.getPrefHeight() == 200 && sp.getContent() instanceof AnchorPane) {
                                        AnchorPane apIcones = (AnchorPane) sp.getContent();
                                        apIcones.setStyle(style);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
