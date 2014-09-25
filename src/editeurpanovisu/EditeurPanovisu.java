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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.shape.StrokeLineJoin;
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

    /**
     * Définition de la langue locale par défaut fr_FR
     */
    public static String styleCSS = "clair";

    public static final String[] codesLanguesTraduction = {"fr_FR", "en_EN", "de_DE"};
    public static final String[] languesTraduction = {"Francais", "English", "Deutsch"};
    public static Locale locale = new Locale("fr", "FR");
    private static ResourceBundle rb;
    private static Label lblDragDrop;
    static private PopUpDialogController popUp;
    static private ImageView imagePanoramique;
    private Image image2;
    static private Label lblLong, lblLat;
    static private AnchorPane panneau2;
    static private HBox coordonnees;
    static public String currentDir;
    static private int numPoints = 0;
    static private int numImages = 0;
    static private int numHTML = 0;
    static public Panoramique[] panoramiquesProjet = new Panoramique[100];
    static public Plan[] plans = new Plan[20];
    static public int nombrePanoramiques = 0;
    static public int nombrePanoramiquesFichier = 0;
    static private ProgressBar p1;
    static private MenuBar menuPrincipal;
    static private HBox barreBouton;
    static public int nombrePlans = 0;
    static private int panoActuel = 0;
    static private File fichProjet;
    static public String panoListeVignette;
    static private Pane pano;
    static private VBox paneChoixPanoramique;
    static private VBox outils;
    static private TabPane tabPaneEnvironnement;
    static private Tab tabVisite;
    public AnchorPane paneAttends;
    static private Label lblCharge;
    static private Label lblNiveaux;
    static private Tab tabInterface;
    static public Tab tabPlan;
    static private Scene scene;
    static private ScrollPane vuePanoramique;
    static private ScrollPane panneauOutils;
    static private double largeurMax;
    static private boolean estCharge = false;
    static private boolean repertSauveChoisi = false;
    static private String panoEntree = "";
    static public String systemeExploitation;
    public static final String tooltipStyle = "";
    static private boolean dragDrop = false;
    static private String titreVisite = "";

    /**
     * Répertoire de l'application
     */
    static public String repertAppli;
    static public String repertTemp;
    static private String repertPanos;
    static private String repertHSImages = "";
    /**
     * Répertoire de sauvegarde du projet
     */
    static public String repertoireProjet;
    /**
     * Répertoire du fichier de configuration
     */
    static public File repertConfig;

    final static private ComboBox listeChoixPanoramique = new ComboBox();
    final static private ComboBox listeChoixPanoramiqueEntree = new ComboBox();
    static private Label lblChoixPanoramique;
    static private boolean panoCharge = false;
    static private String panoAffiche = "";
    static public boolean dejaSauve = true;
    static public Stage stPrincipal;
    private static final String[] histoFichiers = new String[10];
    static private int nombreHistoFichiers = 0;
    static private File fichHistoFichiers;
    private String texteHisto;
    private static String numVersion;
    private static int hauteurInterface;
    private static int largeurInterface;

    static public GestionnaireInterfaceController gestionnaireInterface = new GestionnaireInterfaceController();
    static public GestionnairePlanController gestionnairePlan = new GestionnairePlanController();

    static private Menu derniersProjets;
    private Menu menuPanoramique;
    private Menu menuTransformation;
    private MenuItem cube2EquiTransformation;
    private MenuItem equi2CubeTransformation;
    private MenuItem configTransformation;
    private Menu menuModeles;
    private MenuItem chargerModele;
    private MenuItem sauverModele;

    private MenuItem aPropos;
    private MenuItem aide;
    private ImageView imgNouveauProjet;
    private ImageView imgSauveProjet;
    private ImageView imgChargeProjet;
    private ImageView imgVisiteGenere;
    private ImageView imgAjouterPano;
    public static ImageView imgAjouterPlan;
    private ImageView imgCube2Equi;
    private ImageView imgEqui2Cube;

    private MenuItem nouveauProjet;
    private MenuItem sauveProjet;
    private MenuItem ajouterPano;
    public static MenuItem ajouterPlan;

    private MenuItem fermerProjet;

    private MenuItem sauveSousProjet;
    private MenuItem visiteGenere;
    private MenuItem chargeProjet;

    private MenuItem affichageVisite;
    private MenuItem affichageInterface;
    public static MenuItem affichagePlan;

    private void genereVisite() throws IOException {
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        if (!dejaSauve) {
            projetSauve();
        }
        if (dejaSauve) {
            deleteDirectory(repertTemp + "/panovisu/images");
            File imagesRepert = new File(repertTemp + "/panovisu/images");
            if (!imagesRepert.exists()) {
                imagesRepert.mkdirs();
            }
            File boutonRepert = new File(repertTemp + "/panovisu/images/navigation");
            if (!boutonRepert.exists()) {
                boutonRepert.mkdirs();
            }
            File boussoleRepert = new File(repertTemp + "/panovisu/images/boussoles");
            if (!boussoleRepert.exists()) {
                boussoleRepert.mkdirs();
            }
            copieDirectory(repertAppli + File.separator + "panovisu/images/boussoles", boussoleRepert.getAbsolutePath());
            File planRepert = new File(repertTemp + "/panovisu/images/plan");
            if (!planRepert.exists()) {
                planRepert.mkdirs();
            }
            copieDirectory(repertAppli + File.separator + "panovisu/images/plan", planRepert.getAbsolutePath());
            File reseauRepert = new File(repertTemp + "/panovisu/images/reseaux");
            if (!reseauRepert.exists()) {
                reseauRepert.mkdirs();
            }
            copieDirectory(repertAppli + File.separator + "panovisu/images/reseaux", reseauRepert.getAbsolutePath());
            File interfaceRepert = new File(repertTemp + "/panovisu/images/interface");
            if (!interfaceRepert.exists()) {
                interfaceRepert.mkdirs();
            }
            copieDirectory(repertAppli + File.separator + "panovisu/images/interface", interfaceRepert.getAbsolutePath());
            File MARepert = new File(repertTemp + "/panovisu/images/MA");
            if (!MARepert.exists()) {
                MARepert.mkdirs();
            }
            File hotspotsRepert = new File(repertTemp + "/panovisu/images/hotspots");
            if (!hotspotsRepert.exists()) {
                hotspotsRepert.mkdirs();
            }
            copieFichierRepertoire(repertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "aide_souris.png",
                    repertTemp + "/panovisu/images");
            copieFichierRepertoire(repertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "fermer.png",
                    repertTemp + "/panovisu/images");
            copieFichierRepertoire(repertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "precedent.png",
                    repertTemp + "/panovisu/images");
            copieFichierRepertoire(repertAppli + File.separator + "panovisu" + File.separator + "images" + File.separator + "suivant.png",
                    repertTemp + "/panovisu/images");
            for (int i = 0; i < gestionnaireInterface.nombreImagesBouton - 1; i++) {
                ReadWriteImage.writePng(gestionnaireInterface.nouveauxBoutons[i],
                        boutonRepert.getAbsolutePath() + File.separator + gestionnaireInterface.nomImagesBoutons[i],
                        false, 0.f);
            }
            ReadWriteImage.writePng(gestionnaireInterface.nouveauxBoutons[gestionnaireInterface.nombreImagesBouton - 1],
                    hotspotsRepert.getAbsolutePath() + File.separator + "hotspot.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.nouveauxBoutons[gestionnaireInterface.nombreImagesBouton],
                    hotspotsRepert.getAbsolutePath() + File.separator + "hotspotImage.png",
                    false, 0.f);
            ReadWriteImage.writePng(gestionnaireInterface.nouveauxMasque,
                    MARepert.getAbsolutePath() + File.separator + "MA.png",
                    false, 0.f);

            File xmlRepert = new File(repertTemp + File.separator + "xml");
            if (!xmlRepert.exists()) {
                xmlRepert.mkdirs();
            }
            File cssRepert = new File(repertTemp + File.separator + "css");
            if (!cssRepert.exists()) {
                cssRepert.mkdirs();
            }
            File jsRepert = new File(repertTemp + File.separator + "js");
            if (!jsRepert.exists()) {
                jsRepert.mkdirs();
            }
            String contenuFichier;
            File xmlFile;
            String chargeImages = "";

            for (int i = 0; i < nombrePanoramiques; i++) {
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
                int rouge = (int) (Color.valueOf(gestionnaireInterface.couleurDiaporama).getRed() * 255.d);
                int bleu = (int) (Color.valueOf(gestionnaireInterface.couleurDiaporama).getBlue() * 255.d);
                int vert = (int) (Color.valueOf(gestionnaireInterface.couleurDiaporama).getGreen() * 255.d);
                String coulDiapo = "rgba(" + rouge + "," + vert + "," + bleu + "," + gestionnaireInterface.diaporamaOpacite + ")";

                Color coulTitre = Color.valueOf(gestionnaireInterface.couleurFondTitre);
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
                        + "      titrePolice=\"" + gestionnaireInterface.titrePoliceNom + "\"\n"
                        + "      titreTaillePolice=\"" + Math.round(Double.parseDouble(gestionnaireInterface.titrePoliceTaille)) + "px\"\n"
                        + "      titreTaille=\"" + Math.round(gestionnaireInterface.titreTaille) + "%\"\n"
                        + "      titreFond=\"" + coulFondTitre + "\"\n"
                        + "      titreCouleur=\"" + gestionnaireInterface.couleurTitre + "\"\n"
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
                        + "      deplacements=\"" + gestionnaireInterface.bDeplacementsBarreClassique + "\" \n"
                        + "      zoom=\"" + gestionnaireInterface.bZoomBarreClassique + "\" \n"
                        + "      outils=\"" + gestionnaireInterface.bOutilsBarreClassique + "\"\n"
                        + "      fs=\"" + gestionnaireInterface.bPleinEcranBarreClassique + "\" \n"
                        + "      souris=\"" + gestionnaireInterface.bSourisBarreClassique + "\" \n"
                        + "      rotation=\"" + gestionnaireInterface.bRotationBarreClassique + "\" \n"
                        + "      positionX=\"" + gestionnaireInterface.positionBarreClassique.split(":")[1] + "\"\n"
                        + "      positionY=\"" + gestionnaireInterface.positionBarreClassique.split(":")[0] + "\" \n"
                        + "      dX=\"" + gestionnaireInterface.offsetXBarreClassique + "\" \n"
                        + "      dY=\"" + gestionnaireInterface.offsetYBarreClassique + "\"\n"
                        + "      espacement=\"" + Math.round(gestionnaireInterface.espacementBarreClassique) + "\"\n"
                        + "      visible=\"" + gestionnaireInterface.bVisibiliteBarreClassique + "\"\n"
                        + "   />\n";
                if (gestionnaireInterface.bAfficheBoussole) {
                    String SAiguille = (gestionnaireInterface.bAiguilleMobileBoussole) ? "oui" : "non";
                    contenuFichier += "<!--  Boussole -->\n"
                            + "    <boussole \n"
                            + "        affiche=\"oui\"\n"
                            + "        image=\"" + gestionnaireInterface.imageBoussole + "\"\n"
                            + "        taille=\"" + gestionnaireInterface.tailleBoussole + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.positionBoussole.split(":")[0] + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.positionBoussole.split(":")[1] + "\"\n"
                            + "        opacite=\"" + gestionnaireInterface.opaciteBoussole + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.dXBoussole + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.dYBoussole + "\"\n"
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
                            + "        lib1=\"" + gestionnaireInterface.stPersLib1 + "\"\n"
                            + "        url1=\"" + gestionnaireInterface.stPersURL1 + "\"\n"
                            + "        pers2=\"" + SPers2 + "\"\n"
                            + "        lib2=\"" + gestionnaireInterface.stPersLib2 + "\"\n"
                            + "        url2=\"" + gestionnaireInterface.stPersURL2 + "\"\n"
                            + "    />\n";
                }
                if (gestionnaireInterface.bSuivantPrecedent) {

                    int panoPrecedent = (i == nombrePanoramiques - 1) ? 0 : i + 1;
                    int panoSuivant = (i == 0) ? nombrePanoramiques - 1 : i - 1;
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
                    for (int j = 0; j < nombrePanoramiques; j++) {
                        String nomPano = panoramiquesProjet[j].getNomFichier();
                        String nFichier = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String nXML = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getVignettePanoramique(),
                                repertTemp + "/panos/" + nFichier, 1.0f, false, 0.0f);
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
                    TextField titreV = (TextField) paneChoixPanoramique.lookup("#titreVisite");
                    String titreVis = titreV.getText();
                    contenuFichier += "<!-- Barre des comboMenu -->"
                            + "    <comboMenu \n"
                            + "        affiche=\"" + SAfficheComboMenu + "\"\n"
                            + "        positionX=\"" + gestionnaireInterface.positionXComboMenu + "\"\n"
                            + "        positionY=\"" + gestionnaireInterface.positionYComboMenu + "\"\n"
                            + "        dX=\"" + gestionnaireInterface.offsetXComboMenu + "\"\n"
                            + "        dY=\"" + gestionnaireInterface.offsetYComboMenu + "\"\n"
                            + "    >\n";
                    for (int j = 0; j < nombrePanoramiques; j++) {
                        String nomPano = panoramiquesProjet[j].getNomFichier();
                        String nFichier = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + "Vignette.jpg";
                        String nXML = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.lastIndexOf(".")) + ".xml";
                        ReadWriteImage.writeJpeg(panoramiquesProjet[j].getVignettePanoramique(),
                                repertTemp + "/panos/" + nFichier, 1.0f, false, 0.0f);

                        contenuFichier
                                += "        <imageComboMenu \n";
                        if (gestionnaireInterface.bAfficheComboMenuImages) {
                            contenuFichier
                                    += "            image=\"panos/" + nFichier + "\"\n";
                        }
                        if (i==j){
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
                if (gestionnaireInterface.nombreImagesFond > 0) {
                    for (int ii = 0; ii < gestionnaireInterface.nombreImagesFond; ii++) {
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
            TextField tfVisite = (TextField) paneChoixPanoramique.lookup("#titreVisite");
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
            if (panoEntree.equals("")) {
                fichierHTML = fichierHTML.replace("PANO",
                        panoramiquesProjet[0].getNomFichier().substring(panoramiquesProjet[0].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getNomFichier().length()).split("\\.")[0]);
            } else {
                fichierHTML = fichierHTML.replace("PANO", panoEntree);
            }
            File fichIndexHTML = new File(repertTemp + File.separator + "index.html");
            fichIndexHTML.setWritable(true);
            OutputStreamWriter fw1 = new OutputStreamWriter(new FileOutputStream(fichIndexHTML), "UTF-8");
            try (BufferedWriter bw1 = new BufferedWriter(fw1)) {
                bw1.write(fichierHTML);
            }
            DirectoryChooser repertChoix = new DirectoryChooser();
            repertChoix.setTitle("Choix du repertoire de sauvegarde de la visite");
            File repert = new File(EditeurPanovisu.repertoireProjet);
            repertChoix.setInitialDirectory(repert);
            File repertVisite = repertChoix.showDialog(null);

            String nomRepertVisite = repertVisite.getAbsolutePath();
            copieDirectory(repertTemp, nomRepertVisite);
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
        File repert = new File(currentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
//        fileChooser.getExtensionFilters().addAll(extFilterImage, extFilterJpeg, extFilterBmp);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterTiff, extFilterBmp, extFilterImage);

        List<File> list = fileChooser.showOpenMultipleDialog(null);
        if (list != null) {
            paneAttends.setVisible(true);
            menuPrincipal.setDisable(true);
            barreBouton.setDisable(true);
            p1.setProgress(-1);
            tabPaneEnvironnement.setDisable(true);
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
                        p1.setProgress((double) (numP - 1) / (double) lstFich.length);
                    });

                    dejaSauve = false;
                    sauveProjet.setDisable(false);
                    currentDir = file1.getParent();
                    File imageRepert = new File(repertTemp + File.separator + "panos");

                    if (!imageRepert.exists()) {

                        imageRepert.mkdirs();
                    }
                    repertPanos = imageRepert.getAbsolutePath();
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
                listeChoixPanoramique.getItems().clear();
                listeChoixPanoramiqueEntree.getItems().clear();
                for (int i = 0; i < nombrePanoramiques; i++) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    listeChoixPanoramique.getItems().add(nomPano);
                    listeChoixPanoramiqueEntree.getItems().add(nomPano);
                }
                //System.out.println("Fin ==> panoActuel : " + panoActuel + " nombre panoramiques " + nombrePanoramiques);
                paneChoixPanoramique.setVisible(true);
                imagePanoramique.setImage(panoramiquesProjet[panoActuel].getImagePanoramique());
                imagePanoramique.setSmooth(true);
                retireAffichageLigne();
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                numPoints = 0;
                ajouteAffichageLignes();
                listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(panoActuel));
                affichePoV(panoramiquesProjet[panoActuel].getLookAtX(), panoramiquesProjet[panoActuel].getLookAtY());
                afficheNord(panoramiquesProjet[panoActuel].getZeroNord());
                installeEvenements();

                imgVisiteGenere.setOpacity(
                        1.0);
                imgVisiteGenere.setDisable(
                        false);
                //System.out.println("Fini");

                gestionnaireInterface.rafraichit();
                affichePanoChoisit(panoActuel);
                panoCharge = true;
                listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(panoActuel));
                paneAttends.setVisible(false);
                menuPrincipal.setDisable(false);
                barreBouton.setDisable(false);
                tabPaneEnvironnement.setDisable(false);
            }

        };
    }

    private void planAjouter() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("Fichiers JPEG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterBmp = new FileChooser.ExtensionFilter("Fichiers BMP (*.bmp)", "*.bmp");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("Fichiers PNG (*.png)", "*.png");
        File repert = new File(currentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().addAll(extFilterJpeg, extFilterPng, extFilterBmp);

        File fichierPlan = fileChooser.showOpenDialog(null);
        if (fichierPlan != null) {
            plans[nombrePlans] = new Plan();
            plans[nombrePlans].setImagePlan(fichierPlan.getName());
            plans[nombrePlans].setLienPlan(fichierPlan.getAbsolutePath());
            File repertoirePlan = new File(repertTemp + File.separator + "images");
            if (!repertoirePlan.exists()) {
                repertoirePlan.mkdirs();
            }
            try {
                copieFichierRepertoire(fichierPlan.getAbsolutePath(), repertoirePlan.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
            gestionnairePlan.ajouterPlan();
            nombrePlans++;
        }

    }

    /**
     *
     */
    private void clickBtnValidePano() {
        TextField txtTitrePano = (TextField) scene.lookup("#txttitrepano");
//        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
//        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
//        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
        if (txtTitrePano != null) {
            panoramiquesProjet[panoActuel].setTitrePanoramique(txtTitrePano.getText());
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
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        Action reponse = null;
        Localization.setLocale(locale);
        if (!dejaSauve) {
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
            pano.getChildren().clear();
            pano.getChildren().add(imagePanoramique);
            paneAttends.setVisible(true);
            menuPrincipal.setDisable(true);
            barreBouton.setDisable(true);
            tabPaneEnvironnement.setDisable(true);
            dejaSauve = true;
            FileChooser repertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            repertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(repertoireProjet + File.separator);
            repertChoix.setInitialDirectory(repert);
            fichProjet = null;
            fichProjet = repertChoix.showOpenDialog(stPrincipal);
            if (fichProjet != null) {
                stPrincipal.setTitle("Panovisu v" + numVersion + " : " + fichProjet.getAbsolutePath());
                lblDragDrop.setVisible(false);
                repertoireProjet = fichProjet.getParent();
                ajouteFichierHisto(fichProjet.getAbsolutePath());
                repertSauveChoisi = true;
                deleteDirectory(repertTemp);
                String repertPanovisu = repertTemp + File.separator + "panovisu";
                File rptPanovisu = new File(repertPanovisu);
                rptPanovisu.mkdirs();
                copieDirectory(repertAppli + File.separator + "panovisu", repertPanovisu);
                menuPanoramique.setDisable(false);
                imgAjouterPano.setDisable(false);
                imgAjouterPano.setOpacity(1.0);
                imgSauveProjet.setDisable(false);
                imgSauveProjet.setOpacity(1.0);
                imgVisiteGenere.setDisable(false);
                imgVisiteGenere.setOpacity(1.0);

                paneChoixPanoramique.setVisible(false);

                sauveProjet.setDisable(false);
                sauveSousProjet.setDisable(false);
                visiteGenere.setDisable(false);
                numPoints = 0;
                numImages = 0;
                numHTML = 0;
                imagePanoramique.setImage(null);
                listeChoixPanoramique.getItems().clear();
                listeChoixPanoramiqueEntree.getItems().clear();
                try {
                    String texte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fichProjet), "UTF-8"))) {
                        texte = "";
                        String ligneTexte;
                        nombrePanoramiquesFichier = 0;
                        while ((ligneTexte = br.readLine()) != null) {
                            if (ligneTexte.contains("Panoramique=>")) {
                                nombrePanoramiquesFichier++;
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
            if (histoFichiers[i].equals(nomFich)) {
                trouve = i;
            }
        }
        if (trouve == -1) {
            for (int i = nombreHistoFichiers; i >= 0; i--) {
                if (i < 9) {
                    histoFichiers[i + 1] = histoFichiers[i];
                }
            }
            histoFichiers[0] = nomFich;
            if (nombreHistoFichiers < 10) {
                nombreHistoFichiers++;
            }
        } else {
            for (int i = trouve - 1; i >= 0; i--) {
                if (i < 9) {
                    histoFichiers[i + 1] = histoFichiers[i];
                }
            }
            histoFichiers[0] = nomFich;

        }
        derniersProjets.getItems().clear();
        for (int i = 0; i < nombreHistoFichiers; i++) {
            if (histoFichiers[i] != null) {
                MenuItem menuDerniersFichiers = new MenuItem(histoFichiers[i]);
                derniersProjets.getItems().add(menuDerniersFichiers);
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
            if (!dejaSauve) {
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
                pano.getChildren().clear();
                pano.getChildren().add(imagePanoramique);
                paneAttends.setVisible(true);
                menuPrincipal.setDisable(true);
                barreBouton.setDisable(true);
                tabPaneEnvironnement.setDisable(true);
                lblDragDrop.setVisible(false);
                dejaSauve = true;
                fichProjet = fichProjet1;
                ajouteFichierHisto(fichProjet.getAbsolutePath());
                stPrincipal.setTitle("Panovisu  v" + numVersion + " : " + fichProjet.getAbsolutePath());
                repertoireProjet = fichProjet.getParent();
                repertSauveChoisi = true;
                deleteDirectory(repertTemp);
                String repertPanovisu = repertTemp + File.separator + "panovisu";
                File rptPanovisu = new File(repertPanovisu);
                rptPanovisu.mkdirs();
                copieDirectory(repertAppli + File.separator + "panovisu", repertPanovisu);
                menuPanoramique.setDisable(false);
                imgAjouterPano.setDisable(false);
                imgAjouterPano.setOpacity(1.0);
                imgSauveProjet.setDisable(false);
                imgSauveProjet.setOpacity(1.0);
                imgVisiteGenere.setDisable(false);
                imgVisiteGenere.setOpacity(1.0);

                paneChoixPanoramique.setVisible(false);

                sauveProjet.setDisable(false);
                sauveSousProjet.setDisable(false);
                visiteGenere.setDisable(false);
                numPoints = 0;
                numImages = 0;
                numHTML = 0;

                imagePanoramique.setImage(null);
                listeChoixPanoramique.getItems().clear();
                listeChoixPanoramiqueEntree.getItems().clear();
                try {
                    String texte;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(fichProjet), "UTF-8"))) {
                        texte = "";
                        String ligneTexte;
                        nombrePanoramiquesFichier = 0;
                        while ((ligneTexte = br.readLine()) != null) {
                            if (ligneTexte.contains("Panoramique=>")) {
                                nombrePanoramiquesFichier++;
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
        repertoireProjet = fichProjet.getParent();
        repertSauveChoisi = true;
        File fichierProjet = new File(fichProjet.getAbsolutePath());
        if (!fichierProjet.exists()) {
            fichierProjet.createNewFile();
        }
        dejaSauve = true;
        stPrincipal.setTitle("Panovisu v" + numVersion + " : " + fichProjet.getAbsolutePath());

        String contenuFichier = "";
        TextField tfVisite = (TextField) paneChoixPanoramique.lookup("#titreVisite");
        if (!tfVisite.getText().equals("")) {
            contenuFichier += "[titreVisite=>" + tfVisite.getText() + "]\n";
        }
        if (!panoEntree.equals("")) {
            contenuFichier += "[PanoEntree=>" + panoEntree + "]\n";
        }
        for (int i = 0; i < nombrePanoramiques; i++) {
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
        fichProjet.setWritable(true);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fichProjet), "UTF-8");
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(contenuFichier);
        }
        Dialogs.create().title("Sauvegarde de fichier")
                .message("Votre fichier à bien été sauvegardé")
                .showInformation();

    }

    private void sauveHistoFichiers() throws IOException {
        File fichConfig = new File(EditeurPanovisu.repertConfig.getAbsolutePath() + File.separator + "derniersprojets.cfg");
        if (!fichConfig.exists()) {
            fichConfig.createNewFile();
        }
        fichConfig.setWritable(true);
        String contenuFichier = "";
        for (int i = 0; i < nombreHistoFichiers; i++) {
            contenuFichier += histoFichiers[i] + "\n";
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
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        if (fichProjet == null) {
            FileChooser repertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            repertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(repertoireProjet + File.separator);
            repertChoix.setInitialDirectory(repert);
            fichProjet = repertChoix.showSaveDialog(null);

        }
        if (fichProjet != null) {
            sauveFichierProjet();
            ajouteFichierHisto(fichProjet.getAbsolutePath());
        }
    }

    /**
     *
     * @throws IOException
     */
    private void projetSauveSous() throws IOException {
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        FileChooser repertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
        repertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(repertoireProjet + File.separator);
        repertChoix.setInitialDirectory(repert);
        fichProjet = repertChoix.showSaveDialog(null);
        if (fichProjet != null) {
            sauveFichierProjet();
            ajouteFichierHisto(fichProjet.getAbsolutePath());

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
        if (!dejaSauve) {
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
            deleteDirectory(repertTemp);
            File ftemp = new File(repertTemp);
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
        if (!dejaSauve) {
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
            panoEntree = "";
            deleteDirectory(repertTemp);
            String repertPanovisu = repertTemp + File.separator + "panovisu";
            File rptPanovisu = new File(repertPanovisu);
            rptPanovisu.mkdirs();
            copieDirectory(repertAppli + File.separator + "panovisu", repertPanovisu);
            menuPanoramique.setDisable(false);

            imgAjouterPano.setDisable(false);
            imgAjouterPano.setOpacity(1.0);
            imgSauveProjet.setDisable(false);
            imgSauveProjet.setOpacity(1.0);
            sauveProjet.setDisable(false);
            sauveSousProjet.setDisable(false);
            visiteGenere.setDisable(false);
            fichProjet = null;
            paneChoixPanoramique.setVisible(false);
            panoramiquesProjet = new Panoramique[50];
            nombrePanoramiques = 0;
            retireAffichageLigne();
            retireAffichageHotSpots();
            retireAffichagePointsHotSpots();
            numPoints = 0;
            numImages = 0;
            numHTML = 0;
            gestionnaireInterface.nombreImagesFond = 0;
            gestionnairePlan.planActuel = -1;
            imagePanoramique.setImage(null);
            listeChoixPanoramique.getItems().clear();
            listeChoixPanoramiqueEntree.getItems().clear();
            lblDragDrop.setVisible(true);
            Node ancNord = (Node) pano.lookup("#Nord");
            if (ancNord != null) {
                pano.getChildren().remove(ancNord);
            }
            Node ancPoV = (Node) pano.lookup("#PoV");
            if (ancPoV != null) {
                pano.getChildren().remove(ancPoV);
            }

            gestionnairePlan.lblDragDropPlan.setVisible(true);
            nombrePlans = 0;
            plans = new Plan[100];
            gestionnairePlan.creeInterface(largeurInterface, hauteurInterface - 60);
            Pane panneauPlan = gestionnairePlan.tabInterface;
            affichagePlan.setDisable(true);
            tabPlan.setDisable(true);
            tabPlan.setContent(panneauPlan);
            vuePanoramique.setOnDragOver((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                } else {
                    event.consume();
                }
            });

            // Dropping over surface
            vuePanoramique.setOnDragDropped((DragEvent event) -> {
                Platform.runLater(() -> {
                    paneAttends.setVisible(true);
                });
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    paneAttends.setVisible(true);
                    menuPrincipal.setDisable(true);
                    barreBouton.setDisable(true);
                    tabPaneEnvironnement.setDisable(true);
                    p1.setProgress(-1);
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
                panoEntree = "";
                nombrePanoramiques = 0;
                String ligneComplete = ligComplete.replace("[", "|");
                String lignes[] = ligneComplete.split("\\|", 500);
                String ligne;
                String[] elementsLigne;
                String[] typeElement;
                int nbHS = 0;
                int nbImg = 0;
                int nbHsplan = 0;
                nombrePlans = -1;
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
                                    int numPano = nombrePanoramiques + 1;
                                    Platform.runLater(() -> {
                                        lblCharge.setText("pano " + numPano + "/" + nombrePanoramiquesFichier + " : " + nmFich);
                                        lblNiveaux.setText("");
                                        p1.setProgress((double) (numPano - 1) / (double) nombrePanoramiquesFichier);
                                    });
                                    File imgPano = new File(nmFich);
                                    File imageRepert = new File(repertTemp + File.separator + "panos");

                                    if (!imageRepert.exists()) {
                                        imageRepert.mkdirs();
                                    }
                                    repertPanos = imageRepert.getAbsolutePath();
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
                                        panoramiquesProjet[panoActuel].setTitrePanoramique(valeur[1]);
                                    } else {
                                        panoramiquesProjet[panoActuel].setTitrePanoramique("");
                                    }
                                    break;
                                case "type":
                                    panoramiquesProjet[panoActuel].setTypePanoramique(valeur[1]);
                                    break;
                                case "afficheInfo":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[panoActuel].setAfficheInfo(true);
                                    } else {
                                        panoramiquesProjet[panoActuel].setAfficheInfo(false);
                                    }
                                    break;
                                case "afficheTitre":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[panoActuel].setAfficheTitre(true);
                                    } else {
                                        panoramiquesProjet[panoActuel].setAfficheTitre(false);
                                    }
                                    break;
                                case "nb":
                                    nbHS = Integer.parseInt(valeur[1]);
                                    break;
                                case "nbImg":
                                    nbImg = Integer.parseInt(valeur[1]);
                                    break;
                                case "regardX":
                                    panoramiquesProjet[panoActuel].setLookAtX(Double.parseDouble(valeur[1]));
                                    break;
                                case "regardY":
                                    panoramiquesProjet[panoActuel].setLookAtY(Double.parseDouble(valeur[1]));
                                    break;
                                case "zeroNord":
                                    panoramiquesProjet[panoActuel].setZeroNord(Double.parseDouble(valeur[1]));
                                    break;
                                case "numeroPlan":
                                    panoramiquesProjet[panoActuel].setNumeroPlan(Integer.parseInt(valeur[1].replace(" ", "")));
                                    break;
                                case "affichePlan":
                                    if (valeur[1].equals("true")) {
                                        panoramiquesProjet[panoActuel].setAffichePlan(true);
                                    } else {
                                        panoramiquesProjet[panoActuel].setAffichePlan(false);
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
                            panoramiquesProjet[panoActuel].addHotspot(HS);
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
                                        File imageRepert = new File(repertTemp + File.separator + "images");

                                        if (!imageRepert.exists()) {
                                            imageRepert.mkdirs();
                                        }
                                        repertPanos = imageRepert.getAbsolutePath();
                                        try {
                                            copieFichierRepertoire(imgPano.getPath(), repertPanos);
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
                            panoramiquesProjet[panoActuel].addHotspotImage(HS);
                        }

                    }

                    if ("Plan".equals(typeElement[0])) {
                        for (int i = 0; i < elementsLigne.length; i++) {
                            elementsLigne[i] = elementsLigne[i].replace("]", "");
                            String[] valeur = elementsLigne[i].split(":", 2);
                            //System.out.println(valeur[0] + "=" + valeur[1]);
                            switch (valeur[0]) {
                                case "lienImage":
                                    nombrePlans++;
                                    plans[nombrePlans] = new Plan();
                                    plans[nombrePlans].setLienPlan(valeur[1]);
                                    File repertoirePlan = new File(repertTemp + File.separator + "images");
                                    if (!repertoirePlan.exists()) {
                                        repertoirePlan.mkdirs();
                                    }
                                    try {
                                        copieFichierRepertoire(plans[nombrePlans].getLienPlan(), repertoirePlan.getAbsolutePath());
                                    } catch (IOException ex) {
                                        Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "image":
                                    plans[nombrePlans].setImagePlan(valeur[1]);
                                    break;
                                case "titre":
                                    if (!valeur[1].equals("'null'")) {
                                        plans[nombrePlans].setTitrePlan(valeur[1]);
                                    } else {
                                        plans[nombrePlans].setTitrePlan("");
                                    }
                                    break;
                                case "nb":
                                    nbHsplan = Integer.parseInt(valeur[1]);
                                    break;
                                case "position":
                                    plans[nombrePlans].setPosition(valeur[1]);
                                    break;
                                case "positionX":
                                    plans[nombrePlans].setPositionX(Double.parseDouble(valeur[1]));
                                    break;
                                case "positionY":
                                    plans[nombrePlans].setPositionY(Double.parseDouble(valeur[1]));
                                    break;
                                case "directionNord":
                                    plans[nombrePlans].setDirectionNord(Double.parseDouble(valeur[1]));
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
                            plans[nombrePlans].addHotspot(HS);
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
                        panoEntree = elementsLigne[0].split("]")[0];
                    }
                    if ("titreVisite".equals(typeElement[0])) {
                        titreVisite = elementsLigne[0].split("]")[0];
                    }

                }
                //System.out.println("Fini");
                return true;
            }

            @Override
            protected void succeeded() {
                //System.out.println("Succès");
                super.succeeded();
                if (!panoEntree.equals("")) {
                    listeChoixPanoramiqueEntree.setValue(panoEntree + ".jpg");
                }
                for (int i = 0; i < nombrePanoramiques; i++) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    listeChoixPanoramique.getItems().add(nomPano);
                    listeChoixPanoramiqueEntree.getItems().add(nomPano);
                }
                panoActuel = 0;
                paneChoixPanoramique.setVisible(true);

                imagePanoramique.setImage(panoramiquesProjet[panoActuel].getImagePanoramique());

                imagePanoramique.setSmooth(true);
                retireAffichageLigne();
                retireAffichageHotSpots();
                retireAffichagePointsHotSpots();
                numPoints = 0;
                ajouteAffichageLignes();
                panoActuel = nombrePanoramiques - 1;

                listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(panoActuel));
                affichePoV(panoramiquesProjet[panoActuel].getLookAtX(), panoramiquesProjet[panoActuel].getLookAtY());
                afficheNord(panoramiquesProjet[panoActuel].getZeroNord());

                for (int ii = 0; ii < nombrePanoramiques; ii++) {
                    Panoramique pano1 = panoramiquesProjet[ii];
                }
                nombrePlans++;
                if (nombrePlans != 0) {
                    int nbPlans1 = nombrePlans;
                    for (int i = 0; i < nbPlans1; i++) {
                        nombrePlans = i;
                        gestionnairePlan.ajouterPlan();
                    }
                    nombrePlans++;
                    gestionnairePlan.afficheConfigPlan();
                }

                gestionnaireInterface.afficheTemplate();
                gestionnaireInterface.rafraichit();
                panoActuel = 0;
                affichePanoChoisit(panoActuel);
                panoCharge = true;
                paneChoixPanoramique.setVisible(true);
                listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(0));
                TextField tfVisite = (TextField) paneChoixPanoramique.lookup("#titreVisite");
                tfVisite.setText(titreVisite);
                paneAttends.setVisible(false);
                menuPrincipal.setDisable(false);
                barreBouton.setDisable(false);
                tabPaneEnvironnement.setDisable(false);
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
        File fichConfig = new File(repertConfig.getAbsolutePath() + File.separator + "panovisu.cfg");
        if (fichConfig.exists()) {
            try {
                String texte;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(fichConfig), "UTF-8"))) {
                    String ligneTexte;
                    String langue = "fr";
                    String pays = "FR";
                    String repert = repertAppli;
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
                                styleCSS = valeur;
                                break;

                        }

                    }
                    locale = new Locale(langue, pays);
                    File repert1 = new File(repert);
                    if (repert1.exists()) {
                        repertoireProjet = repert;
                        currentDir = repert;
                        repertSauveChoisi = true;
                    } else {
                        repertoireProjet = repertAppli;
                        currentDir = repertAppli;
                    }
                    setUserAgentStylesheet("file:css/" + styleCSS + ".css");
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            fichConfig.createNewFile();
            locale = new Locale("fr", "FR");
            setUserAgentStylesheet("file:css/clair.css");
            repertoireProjet = repertAppli;
            String contenuFichier = "langue=" + locale.toString().split("_")[0] + "\n";
            contenuFichier += "pays=" + locale.toString().split("_")[1] + "\n";
            contenuFichier += "repert=" + repertoireProjet + "\n";
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
        Pane lbl = (Pane) outils.lookup("#labels");
        outils.getChildren().remove(lbl);
    }

    /**
     *
     */
    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < numPoints; i++) {
            Node pt = (Node) pano.lookup("#point" + i);
            pano.getChildren().remove(pt);
        }
        for (int i = 0; i < numImages; i++) {
            Node pt = (Node) pano.lookup("#img" + i);
            pano.getChildren().remove(pt);
        }
    }

    /**
     *
     * @param panCourant
     * @return
     */
    private String listePano(int panCourant) {
        String liste = "";
        if (nombrePanoramiques == 0) {
            return null;
        } else {
            for (int i = 0; i < nombrePanoramiques; i++) {
                if (i != panCourant) {
                    String fichierPano = panoramiquesProjet[i].getNomFichier();
                    String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
                    String[] nPano = nomPano.split("\\.");
                    liste += nPano[0];
                    if (i < nombrePanoramiques - 1) {
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
        fond.setPrefHeight(((nombrePanoramiques - 2) / 4 + 1) * 65 + 10);
        fond.setMinWidth(540);
        fond.setMinHeight(70);
        aplistePano.getChildren().add(fond);
        aplistePano.setStyle("-fx-backgroung-color : #bbb;");
        int j = 0;
        ImageView[] IVPano;
        IVPano = new ImageView[nombrePanoramiques];
        double xPos;
        double yPos;
        int row = 0;
        for (int i = 0; i < nombrePanoramiques; i++) {
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
            t.setStyle(tooltipStyle);
            Tooltip.install(IVPano[j], t);
            IVPano[j].setOnMouseClicked((MouseEvent me) -> {
                pano.setCursor(Cursor.CROSSHAIR);
                pano.setOnMouseClicked(
                        (MouseEvent me1) -> {
                            gereSourisPanoramique(me1);
                        }
                );
                panoListeVignette = nomPano;
                if (panoramiquesProjet[numeroPano].getTitrePanoramique() != null) {
                    String texteHS = panoramiquesProjet[numeroPano].getTitrePanoramique();
                    TextField txtHS = (TextField) outils.lookup("#txtHS" + numHS);
                    txtHS.setText(texteHS);
                }
                panoramiquesProjet[panoActuel].getHotspot(numHS).setNumeroPano(numeroPano);
                ComboBox cbx = (ComboBox) outils.lookup("#cbpano" + numHS);
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
        ImageView IVClose = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ferme.png", 20, 20, true, true));
        IVClose.setLayoutX(2);
        IVClose.setLayoutY(5);
        IVClose.setCursor(Cursor.HAND);
        aplistePano.getChildren().add(IVClose);
        IVClose.setOnMouseClicked((MouseEvent me) -> {
            pano.setCursor(Cursor.CROSSHAIR);
            pano.setOnMouseClicked(
                    (MouseEvent me1) -> {
                        gereSourisPanoramique(me1);
                    }
            );

            panoListeVignette = "";
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

        for (int i = 0; i < panoramiquesProjet[panoActuel].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) outils.lookup("#cbpano" + i);
            TextField txtTexteHS = (TextField) outils.lookup("#txtHS" + i);
            if (txtTexteHS != null) {
                panoramiquesProjet[panoActuel].getHotspot(i).setInfo(txtTexteHS.getText());
            }
            if (cbx != null) {
                panoramiquesProjet[panoActuel].getHotspot(i).setFichierXML(cbx.getValue() + ".xml");
            }
            CheckBox cbAnime = (CheckBox) outils.lookup("#anime" + i);
            if (cbAnime != null) {
                if (cbAnime.isSelected()) {
                    panoramiquesProjet[panoActuel].getHotspot(i).setAnime(true);
                } else {
                    panoramiquesProjet[panoActuel].getHotspot(i).setAnime(false);
                }
            }
        }
        for (int i = 0; i < panoramiquesProjet[panoActuel].getNombreHotspotImage(); i++) {
            TextField txtTexteHS = (TextField) outils.lookup("#txtHSImage" + i);
            if (txtTexteHS != null) {
                panoramiquesProjet[panoActuel].getHotspotImage(i).setInfo(txtTexteHS.getText());
            }
            CheckBox cbAnime = (CheckBox) outils.lookup("#animeImage" + i);
            if (cbAnime != null) {
                if (cbAnime.isSelected()) {
                    panoramiquesProjet[panoActuel].getHotspotImage(i).setAnime(true);
                } else {
                    panoramiquesProjet[panoActuel].getHotspotImage(i).setAnime(false);
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
            ImageView IMChoisie = new ImageView(new Image("file:" + repertTemp + File.separator + "images" + File.separator + f1XML, 100, -1, true, true));
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
        Pane lbl = affichageHS(listePano(panoActuel), panoActuel);
        lbl.setId("labels");
        outils.getChildren().add(lbl);
        numPoints = panoramiquesProjet[panoActuel].getNombreHotspots();
        numImages = panoramiquesProjet[panoActuel].getNombreHotspotImage();
        numHTML = panoramiquesProjet[panoActuel].getNombreHotspotHTML();
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
        positionY = (pano.getLayoutY() + pano.getPrefHeight() + 10);

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
        Label lblHS = new Label(rb.getString("main.legendeHS"));
        Label lblHSImage = new Label(rb.getString("main.legendeHSImage"));
        //Label lblHSHTML = new Label(rb.getString("main.legendeHSHTML"));
        Label lblPoV = new Label(rb.getString("main.legendePoV"));
        Label lblNord = new Label(rb.getString("main.legendeNord"));
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
        double largeur = imagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + imagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.YELLOW);
        point.setStroke(Color.RED);
        point.setId("point" + i);
        point.setCursor(Cursor.DEFAULT);
        pano.getChildren().add(point);

        Tooltip t = new Tooltip("point n° " + (i + 1));
        t.setStyle(tooltipStyle);
        Tooltip.install(point, t);
        point.setOnDragDetected((MouseEvent me1) -> {
            point.setFill(Color.RED);
            point.setStroke(Color.YELLOW);
            dragDrop = true;
            me1.consume();

        });
        point.setOnMouseDragged((MouseEvent me1) -> {
            double XX = me1.getX() - imagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > imagePanoramique.getFitWidth()) {
                XX = imagePanoramique.getFitWidth();
            }
            point.setCenterX(XX + imagePanoramique.getLayoutX());
            double YY = me1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > imagePanoramique.getFitHeight()) {
                YY = imagePanoramique.getFitHeight();
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
            double mouseX = X1 - imagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > imagePanoramique.getFitWidth()) {
                mouseX = imagePanoramique.getFitWidth();
            }

            double mouseY = Y1 - pano.getLayoutY() - 109;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > imagePanoramique.getFitHeight()) {
                mouseY = imagePanoramique.getFitHeight();
            }

            double longit, lat;
            double larg = imagePanoramique.getFitWidth();
            String chLong, chLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[panoActuel].getHotspot(numeroPoint).setLatitude(lat);
            panoramiquesProjet[panoActuel].getHotspot(numeroPoint).setLongitude(longit);
            point.setFill(Color.YELLOW);
            point.setStroke(Color.RED);
            me1.consume();

        });

        point.setOnMouseClicked((MouseEvent me1) -> {
            double mouseX = me1.getSceneX() - imagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > imagePanoramique.getFitWidth()) {
                mouseX = imagePanoramique.getFitWidth();
            }

            double mouseY = me1.getSceneY() - pano.getLayoutY() - 115;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > imagePanoramique.getFitHeight()) {
                mouseY = imagePanoramique.getFitHeight();
            }

            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) pano.lookup("#point" + chPoint);

            if (me1.isControlDown()) {
                dejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                pano.getChildren().remove(pt);

                for (int o = numeroPoint + 1; o < numPoints; o++) {
                    pt = (Node) pano.lookup("#point" + Integer.toString(o));
                    pt.setId("point" + Integer.toString(o - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                numPoints--;
                panoramiquesProjet[panoActuel].removeHotspot(numeroPoint);
                /**
                 * On les crée les nouvelles
                 */
                ajouteAffichageHotspots();
                me1.consume();
                valideHS();
            } else {
                if (!dragDrop) {
                    if (nombrePanoramiques > 1) {
                        AnchorPane listePanoVig = afficherListePanosVignettes(numeroPoint);
                        int largeurVignettes = 4;
                        if (nombrePanoramiques < 4) {
                            largeurVignettes = nombrePanoramiques;
                        }
                        if (mouseX + largeurVignettes * 130 > pano.getWidth()) {
                            listePanoVig.setLayoutX(pano.getWidth() - largeurVignettes * 130);
                        } else {
                            listePanoVig.setLayoutX(mouseX);
                        }
                        listePanoVig.setLayoutY(mouseY);
                        pano.getChildren().add(listePanoVig);
                    }
                } else {
                    dragDrop = false;
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
        double largeur = imagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + imagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.BLUE);
        point.setStroke(Color.YELLOW);
        point.setId("img" + i);
        point.setCursor(Cursor.DEFAULT);
        pano.getChildren().add(point);
        Tooltip t = new Tooltip("image n° " + (i + 1));
        t.setStyle(tooltipStyle);
        Tooltip.install(point, t);
        point.setOnDragDetected((MouseEvent me1) -> {
            point.setFill(Color.YELLOW);
            point.setStroke(Color.BLUE);
            dragDrop = true;
            me1.consume();

        });
        point.setOnMouseDragged((MouseEvent me1) -> {
            double XX = me1.getX() - imagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > imagePanoramique.getFitWidth()) {
                XX = imagePanoramique.getFitWidth();
            }
            point.setCenterX(XX + imagePanoramique.getLayoutX());
            double YY = me1.getY();
            if (YY < 0) {
                YY = 0;
            }
            if (YY > imagePanoramique.getFitHeight()) {
                YY = imagePanoramique.getFitHeight();
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
            double mouseX = X1 - imagePanoramique.getLayoutX();
            if (mouseX < 0) {
                mouseX = 0;
            }
            if (mouseX > imagePanoramique.getFitWidth()) {
                mouseX = imagePanoramique.getFitWidth();
            }
            double mouseY = Y1 - pano.getLayoutY() - 109;
            if (mouseY < 0) {
                mouseY = 0;
            }
            if (mouseY > imagePanoramique.getFitHeight()) {
                mouseY = imagePanoramique.getFitHeight();
            }

            double longit, lat;
            double larg = imagePanoramique.getFitWidth();
            String chLong, chLat;
            longit = 360.0f * mouseX / larg - 180;
            lat = 90.0d - 2.0f * mouseY / larg * 180.0f;
            panoramiquesProjet[panoActuel].getHotspotImage(numeroPoint).setLatitude(lat);
            panoramiquesProjet[panoActuel].getHotspotImage(numeroPoint).setLongitude(longit);
            point.setFill(Color.BLUE);
            point.setStroke(Color.YELLOW);
            me1.consume();

        });

        point.setOnMouseClicked((MouseEvent me1) -> {
            String chPoint = point.getId();
            chPoint = chPoint.substring(3, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            Node pt;
            pt = (Node) pano.lookup("#img" + chPoint);

            if (me1.isControlDown()) {
                valideHS();
                dejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                pano.getChildren().remove(pt);

                for (int o = numeroPoint + 1; o < numImages; o++) {
                    pt = (Node) pano.lookup("#img" + Integer.toString(o));
                    pt.setId("img" + Integer.toString(o - 1));
                }
                /**
                 * on retire les anciennes indication de HS
                 */
                retireAffichageHotSpots();
                numImages--;
                panoramiquesProjet[panoActuel].removeHotspotImage(numeroPoint);
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

        for (int i = 0; i < panoramiquesProjet[panoActuel].getNombreHotspots(); i++) {
            double longitude = panoramiquesProjet[panoActuel].getHotspot(i).getLongitude();
            double latitude = panoramiquesProjet[panoActuel].getHotspot(i).getLatitude();
            afficheHS(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private void ajouteAffichagePointsHotspotsImage() {
        for (int i = 0; i < panoramiquesProjet[panoActuel].getNombreHotspotImage(); i++) {
            double longitude = panoramiquesProjet[panoActuel].getHotspotImage(i).getLongitude();
            double latitude = panoramiquesProjet[panoActuel].getHotspotImage(i).getLatitude();
            afficheHSImage(i, longitude, latitude);
        }

    }

    /**
     *
     */
    private void sauveFichiers() throws IOException {
        if (!dejaSauve) {
            projetSauve();
        }
    }

    private void panoChoixRegard(double X, double Y) {
        if (X > 0 && X < imagePanoramique.getFitWidth()) {
            double mouseX = X;
            double mouseY = Y - pano.getLayoutY() - 109;
            double largeur = imagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            double regardY = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            panoramiquesProjet[panoActuel].setLookAtX(regardX);
            panoramiquesProjet[panoActuel].setLookAtY(regardY);
            affichePoV(regardX, regardY);
        }
    }

    private void panoChoixNord(double X) {
        if (X > 0 && X < imagePanoramique.getFitWidth()) {
            double mouseX = X;
            double largeur = imagePanoramique.getFitWidth();
            double regardX = 360.0f * mouseX / largeur - 180;
            panoramiquesProjet[panoActuel].setZeroNord(regardX);
            afficheNord(regardX);
        }
    }

    private void afficheNord(double longitude) {
        double largeur = imagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + imagePanoramique.getLayoutX();
        Node ancPoV = (Node) pano.lookup("#Nord");
        if (ancPoV != null) {
            pano.getChildren().remove(ancPoV);
        }
        Line ligne = new Line(0, 0, 0, imagePanoramique.getFitHeight());
        ligne.setCursor(Cursor.DEFAULT);

        ligne.setLayoutX(X);
        ligne.setStroke(Color.RED);
        ligne.setStrokeWidth(4);
        ligne.setId("Nord");
        ligne.setOnDragDetected((MouseEvent me1) -> {
            ligne.setStroke(Color.BLUEVIOLET);
            dragDrop = true;
            me1.consume();
        });
        ligne.setOnMouseDragged((MouseEvent me1) -> {

            double XX = me1.getSceneX() - imagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > imagePanoramique.getFitWidth()) {
                XX = imagePanoramique.getFitWidth();
            }
            ligne.setLayoutX(XX + imagePanoramique.getLayoutX());
            me1.consume();

        });
        ligne.setOnMouseReleased((MouseEvent me1) -> {
            double X1 = me1.getSceneX();
            double mouseX1 = X1 - imagePanoramique.getLayoutX();
            if (mouseX1 < 0) {
                mouseX1 = 0;
            }
            if (mouseX1 > imagePanoramique.getFitWidth()) {
                mouseX1 = imagePanoramique.getFitWidth();
            }
            double regardX = 360.0f * mouseX1 / largeur - 180;
            panoramiquesProjet[panoActuel].setZeroNord(regardX);
            ligne.setStroke(Color.RED);
            me1.consume();

        });

        pano.getChildren().add(ligne);
    }

    private void affichePoV(double longitude, double latitude) {
        double largeur = imagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d + imagePanoramique.getLayoutX();
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Node ancPoV = (Node) pano.lookup("#PoV");
        if (ancPoV != null) {
            pano.getChildren().remove(ancPoV);
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
            dragDrop = true;
            me1.consume();

        });
        polygon.setOnMouseDragged((MouseEvent me1) -> {

            double XX = me1.getSceneX() - imagePanoramique.getLayoutX();
            if (XX < 0) {
                XX = 0;
            }
            if (XX > imagePanoramique.getFitWidth()) {
                XX = imagePanoramique.getFitWidth();
            }
            polygon.setLayoutX(XX + imagePanoramique.getLayoutX());
            double YY = me1.getSceneY() - pano.getLayoutY() - 109;
            if (YY < 0) {
                YY = 0;
            }
            if (YY > imagePanoramique.getFitHeight()) {
                YY = imagePanoramique.getFitHeight();
            }
            polygon.setLayoutY(YY);

            me1.consume();

        });
        polygon.setOnMouseReleased((MouseEvent me1) -> {
            double X1 = me1.getSceneX();
            double Y1 = me1.getSceneY();
            double mouseX1 = X1 - imagePanoramique.getLayoutX();
            if (mouseX1 < 0) {
                mouseX1 = 0;
            }
            if (mouseX1 > imagePanoramique.getFitWidth()) {
                mouseX1 = imagePanoramique.getFitWidth();
            }
            double mouseY1 = Y1 - pano.getLayoutY() - 109;
            if (mouseY1 < 0) {
                mouseY1 = 0;
            }
            if (mouseY1 > imagePanoramique.getFitHeight()) {
                mouseY1 = imagePanoramique.getFitHeight();
            }
            double regardX = 360.0f * mouseX1 / largeur - 180;
            double regardY = 90.0d - 2.0f * mouseY1 / largeur * 180.0f;
            panoramiquesProjet[panoActuel].setLookAtX(regardX);
            panoramiquesProjet[panoActuel].setLookAtY(regardY);
            polygon.setFill(Color.BLUEVIOLET);
            polygon.setStroke(Color.YELLOW);
            me1.consume();

        });

        pano.getChildren().add(polygon);
    }

    private void panoMouseClic(double X, double Y) {

        if (nombrePanoramiques > 1) {
            valideHS();
            dejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - pano.getLayoutY() - 109;
            if (X > 0 && X < imagePanoramique.getFitWidth()) {
                double longitude, latitude;
                double largeur = imagePanoramique.getFitWidth();
                String chLong, chLat;
                longitude = 360.0f * mouseX / largeur - 180;
                latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                Circle point = new Circle(mouseX + imagePanoramique.getLayoutX(), mouseY, 5);
                point.setFill(Color.YELLOW);
                point.setStroke(Color.RED);
                point.setId("point" + numPoints);
                point.setCursor(Cursor.DEFAULT);
                pano.getChildren().add(point);
                Tooltip t = new Tooltip("point n°" + (numPoints + 1));
                t.setStyle(tooltipStyle);
                Tooltip.install(point, t);
                HotSpot HS = new HotSpot();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                panoramiquesProjet[panoActuel].addHotspot(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(listePano(panoActuel), panoActuel);
                affHS1.setId("labels");
                outils.getChildren().add(affHS1);

                numPoints++;
                if (nombrePanoramiques > 1) {

                    AnchorPane listePanoVig = afficherListePanosVignettes(panoramiquesProjet[panoActuel].getNombreHotspots() - 1);
                    int largeurVignettes = 4;
                    if (nombrePanoramiques < 4) {
                        largeurVignettes = nombrePanoramiques;
                    }
                    if (mouseX + largeurVignettes * 130 > pano.getWidth()) {
                        listePanoVig.setLayoutX(pano.getWidth() - largeurVignettes * 130);
                    } else {
                        listePanoVig.setLayoutX(mouseX);
                    }
                    listePanoVig.setLayoutY(mouseY);
                    pano.getChildren().add(listePanoVig);
                    valideHS();
                }
                point.setOnDragDetected((MouseEvent me1) -> {
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(5, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    Node pt;
                    pt = (Node) pano.lookup("#point" + chPoint);
                    point.setFill(Color.RED);
                    point.setStroke(Color.YELLOW);
                    dragDrop = true;
                    me1.consume();

                });
                point.setOnMouseDragged((MouseEvent me1) -> {
                    double XX = me1.getX() - imagePanoramique.getLayoutX();
                    if (XX < 0) {
                        XX = 0;
                    }
                    if (XX > imagePanoramique.getFitWidth()) {
                        XX = imagePanoramique.getFitWidth();
                    }
                    point.setCenterX(XX + imagePanoramique.getLayoutX());
                    double YY = me1.getY();
                    if (YY < 0) {
                        YY = 0;
                    }
                    if (YY > imagePanoramique.getFitHeight()) {
                        YY = imagePanoramique.getFitHeight();
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
                    double mouseX1 = X1 - imagePanoramique.getLayoutX();
                    if (mouseX1 < 0) {
                        mouseX1 = 0;
                    }
                    if (mouseX1 > imagePanoramique.getFitWidth()) {
                        mouseX1 = imagePanoramique.getFitWidth();
                    }
                    double mouseY1 = Y1 - pano.getLayoutY() - 109;
                    if (mouseY1 < 0) {
                        mouseY1 = 0;
                    }
                    if (mouseY1 > imagePanoramique.getFitHeight()) {
                        mouseY1 = imagePanoramique.getFitHeight();
                    }
                    double longit, lat;
                    double larg = imagePanoramique.getFitWidth();
                    longit = 360.0f * mouseX1 / larg - 180;
                    lat = 90.0d - 2.0f * mouseY1 / larg * 180.0f;
                    panoramiquesProjet[panoActuel].getHotspot(numeroPoint).setLatitude(lat);
                    panoramiquesProjet[panoActuel].getHotspot(numeroPoint).setLongitude(longit);
                    point.setFill(Color.YELLOW);
                    point.setStroke(Color.RED);
                    me1.consume();

                });

                point.setOnMouseClicked((MouseEvent me1) -> {
                    if (me1.isControlDown()) {
                        dejaSauve = false;
                        stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                        String chPoint = point.getId();
                        chPoint = chPoint.substring(5, chPoint.length());
                        int numeroPoint = Integer.parseInt(chPoint);
                        Node pt;
                        pt = (Node) pano.lookup("#point" + chPoint);
                        pano.getChildren().remove(pt);

                        for (int o = numeroPoint + 1; o < numPoints; o++) {
                            pt = (Node) pano.lookup("#point" + Integer.toString(o));
                            pt.setId("point" + Integer.toString(o - 1));
                        }
                        /**
                         * on retire les anciennes indication de HS
                         */
                        retireAffichageHotSpots();
                        numPoints--;
                        panoramiquesProjet[panoActuel].removeHotspot(numeroPoint);
                        /**
                         * On les crée les nouvelles
                         */
                        ajouteAffichageHotspots();
                        valideHS();
                        me1.consume();
                    } else {
                        if (!dragDrop) {
                            String chPoint = point.getId();
                            chPoint = chPoint.substring(5, chPoint.length());
                            int numeroPoint = Integer.parseInt(chPoint);
                            if (nombrePanoramiques > 1) {
                                AnchorPane listePanoVig = afficherListePanosVignettes(numeroPoint);
                                int largeurVignettes = 4;
                                if (nombrePanoramiques < 4) {
                                    largeurVignettes = nombrePanoramiques;
                                }
                                if (mouseX + largeurVignettes * 130 > pano.getWidth()) {
                                    listePanoVig.setLayoutX(pano.getWidth() - largeurVignettes * 130);
                                } else {
                                    listePanoVig.setLayoutX(mouseX);
                                }
                                listePanoVig.setLayoutY(mouseY);
                                pano.getChildren().add(listePanoVig);
                            }
                        } else {
                            dragDrop = false;
                        }
                        valideHS();
                        me1.consume();

                    }
                });
            }
        }
    }

    private void panoAjouteImage(double X, double Y) {
        if (X > 0 && X < imagePanoramique.getFitWidth()) {

            valideHS();
            dejaSauve = false;
            stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            double mouseX = X;
            double mouseY = Y - pano.getLayoutY() - 115;
            double longitude, latitude;
            double largeur = imagePanoramique.getFitWidth();
            String chLong, chLat;
            longitude = 360.0f * mouseX / largeur - 180;
            latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
            Circle point = new Circle(mouseX, mouseY, 5);
            point.setFill(Color.BLUE);
            point.setStroke(Color.YELLOW);
            point.setId("img" + numImages);
            point.setCursor(Cursor.DEFAULT);
            pano.getChildren().add(point);
            Tooltip t = new Tooltip("image n° " + (numImages + 1));
            t.setStyle(tooltipStyle);
            Tooltip.install(point, t);

//
            File repert;
            if (repertHSImages.equals("")) {
                repert = new File(currentDir + File.separator);
            } else {
                repert = new File(repertHSImages);
            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png)", "*.jpg", "*.bmp", "*.png");

            fileChooser.setInitialDirectory(repert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fichierImage = fileChooser.showOpenDialog(null);
            if (fichierImage != null) {
                repertHSImages = fichierImage.getParent();
                numImages++;
                HotspotImage HS = new HotspotImage();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                HS.setUrlImage(fichierImage.getAbsolutePath());
                HS.setLienImg(fichierImage.getName());
                HS.setInfo(fichierImage.getName().split("\\.")[0]);
                File repertImage = new File(repertTemp + File.separator + "images");
                if (!repertImage.exists()) {
                    repertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fichierImage.getAbsolutePath(), repertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                panoramiquesProjet[panoActuel].addHotspotImage(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(listePano(panoActuel), panoActuel);
                affHS1.setId("labels");
                outils.getChildren().add(affHS1);

            } else {
                String chPoint = point.getId();
                chPoint = chPoint.substring(3, chPoint.length());
                Node pt = (Node) pano.lookup("#img" + chPoint);
                pano.getChildren().remove(pt);
            }
            valideHS();
            point.setOnDragDetected((MouseEvent me1) -> {
                point.setFill(Color.YELLOW);
                point.setStroke(Color.BLUE);
                dragDrop = true;
                me1.consume();

            });
            point.setOnMouseDragged((MouseEvent me1) -> {
                double XX = me1.getX() - imagePanoramique.getLayoutX();
                if (XX < 0) {
                    XX = 0;
                }
                if (XX > imagePanoramique.getFitWidth()) {
                    XX = imagePanoramique.getFitWidth();
                }
                point.setCenterX(XX + imagePanoramique.getLayoutX());
                double YY = me1.getY();
                if (YY < 0) {
                    YY = 0;
                }
                if (YY > imagePanoramique.getFitHeight()) {
                    YY = imagePanoramique.getFitHeight();
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
                double mouseX1 = X1 - imagePanoramique.getLayoutX();
                if (mouseX1 < 0) {
                    mouseX1 = 0;
                }
                if (mouseX1 > imagePanoramique.getFitWidth()) {
                    mouseX1 = imagePanoramique.getFitWidth();
                }

                double mouseY1 = Y1 - pano.getLayoutY() - 109;
                if (mouseY1 < 0) {
                    mouseY1 = 0;
                }
                if (mouseY1 > imagePanoramique.getFitHeight()) {
                    mouseY1 = imagePanoramique.getFitHeight();
                }

                double longit, lat;
                double larg = imagePanoramique.getFitWidth();
                longit = 360.0f * mouseX1 / larg - 180;
                lat = 90.0d - 2.0f * mouseY1 / larg * 180.0f;
                panoramiquesProjet[panoActuel].getHotspotImage(numeroPoint).setLatitude(lat);
                panoramiquesProjet[panoActuel].getHotspotImage(numeroPoint).setLongitude(longit);
                point.setFill(Color.BLUE);
                point.setStroke(Color.YELLOW);
                me1.consume();

            });

            point.setOnMouseClicked((MouseEvent me1) -> {
                if (me1.isControlDown()) {
                    dejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    String chPoint = point.getId();
                    chPoint = chPoint.substring(3, chPoint.length());
                    int numeroPoint = Integer.parseInt(chPoint);
                    Node pt;
                    pt = (Node) pano.lookup("#img" + chPoint);
                    pano.getChildren().remove(pt);

                    for (int o = numeroPoint + 1; o < numImages; o++) {
                        pt = (Node) pano.lookup("#img" + Integer.toString(o));
                        pt.setId("img" + Integer.toString(o - 1));
                    }
                    /**
                     * on retire les anciennes indication de HS
                     */
                    retireAffichageHotSpots();
                    numImages--;
                    panoramiquesProjet[panoActuel].removeHotspotImage(numeroPoint);
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
                panoChoixNord(me.getSceneX() - imagePanoramique.getLayoutX());
                me.consume();
            } else if (me.isControlDown()) {
            } else {
                panoChoixRegard(me.getSceneX() - imagePanoramique.getLayoutX(), me.getSceneY());
                me.consume();
            }
        }
        if (me.getButton() == MouseButton.PRIMARY) {
            if (me.isShiftDown()) {
                if (!me.isControlDown()) {
                    if (!dragDrop) {
                        panoAjouteImage(me.getSceneX() - imagePanoramique.getLayoutX(), me.getSceneY());
                    } else {
                        dragDrop = false;
                    }
                } else {

                }
            } else if (!(me.isControlDown()) && estCharge) {
                if (!dragDrop) {
                    panoMouseClic(me.getSceneX() - imagePanoramique.getLayoutX(), me.getSceneY());
                } else {
                    dragDrop = false;
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
        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            double largeur = (double) newSceneWidth - 320.0d;
            vuePanoramique.setPrefWidth(largeur);
        });
        /**
         *
         */
        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
            vuePanoramique.setPrefHeight((double) newSceneHeight - 70.0d);
            panneauOutils.setPrefHeight((double) newSceneHeight - 70.0d);
        });

        /**
         *
         */
        pano.setOnMouseClicked(
                (MouseEvent me) -> {
                    gereSourisPanoramique(me);
                }
        );
        /**
         *
         */
        pano.setOnMouseMoved(
                (MouseEvent me) -> {
                    if (estCharge) {
                        double mouseX = me.getSceneX() - imagePanoramique.getLayoutX();
                        if (mouseX < 0) {
                            mouseX = 0;
                        }
                        if (mouseX > imagePanoramique.getFitWidth()) {
                            mouseX = imagePanoramique.getFitWidth();
                        }
                        double mouseY = me.getSceneY() - pano.getLayoutY() - 109;
                        if (mouseY < 0) {
                            mouseY = 0;
                        }
                        if (mouseY > imagePanoramique.getFitHeight()) {
                            mouseY = imagePanoramique.getFitHeight();
                        }

                        double longitude, latitude;
                        double largeur = imagePanoramique.getFitWidth() * pano.getScaleX();
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
        listeChoixPanoramiqueEntree.valueProperty()
                .addListener(new ChangeListenerImplEntree());
        listeChoixPanoramique.valueProperty()
                .addListener(new ChangeListenerImpl());

    }

    private class ChangeListenerImpl implements ChangeListener<String> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
            if (nouvelleValeur != null) {
                if (!(nouvelleValeur.equals(panoAffiche))) {
                    clickBtnValidePano();
                    valideHS();
                    panoAffiche = nouvelleValeur;
                    int numPanoChoisit = listeChoixPanoramique.getSelectionModel().getSelectedIndex();
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
                panoEntree = nouvelleValeur.split("\\.")[0];
            }

        }
    }

    private void ajouteAffichageLignes() {
        double largeur = imagePanoramique.getFitWidth();
        double hauteur = largeur / 2.0d;
        Line ligne;
        int x, y;
        int nl = 0;
        for (int i = -180; i < 180; i += 10) {
            x = (int) (largeur / 2.0f + largeur / 360.0f * (float) i + imagePanoramique.getLayoutX());
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
            pano.getChildren().add(ligne);
        }
        for (int i = -90; i < 90; i += 10) {
            y = (int) (hauteur / 2.0f + hauteur / 180.0f * (float) i);
            ligne = new Line(imagePanoramique.getLayoutX(), y, largeur + imagePanoramique.getLayoutX(), y);
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

            pano.getChildren().add(ligne);
        }

    }

    /**
     *
     */
    private void retireAffichageLigne() {
        int i = 0;
        Node lg;
        do {
            lg = (Node) pano.lookup("#ligne" + i);
            if (lg != null) {
                pano.getChildren().remove(lg);
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
        imagePanoramique.setImage(panoramiquesProjet[numPanochoisi].getImagePanoramique());
        Node legende = (Node) panneau2.lookup("#legende");
        legende.setVisible(true);
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        retireAffichageLigne();
        numPoints = 0;

        panoActuel = numPanochoisi;
        ajouteAffichageHotspots();
        affichePoV(panoramiquesProjet[numPanochoisi].getLookAtX(), panoramiquesProjet[numPanochoisi].getLookAtY());
        afficheNord(panoramiquesProjet[numPanochoisi].getZeroNord());
        ajouteAffichagePointsHotspots();
        ajouteAffichagePointsHotspotsImage();

        ajouteAffichageLignes();
        afficheInfoPano();
    }

    private void afficheInfoPano() {
        TextField txtTitrePano = (TextField) scene.lookup("#txttitrepano");
//        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
//        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
//        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
//        RadioButton radCube = (RadioButton) scene.lookup("#radcube");

        if (panoramiquesProjet[panoActuel].getTitrePanoramique() != null) {
            txtTitrePano.setText(panoramiquesProjet[panoActuel].getTitrePanoramique());
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
        panoCharge = true;
        estCharge = true;
        Panoramique panoCree = new Panoramique();
        //System.out.println("Type pano : " + typePano);
        if (typePano.equals(Panoramique.SPHERE)) {
            //System.out.println("sphere");
            panoCree.setNomFichier(fichierPano);
            String extension = fichierPano.substring(fichierPano.length() - 3, fichierPano.length());
            String nom = fichierPano.substring(0, fichierPano.length() - 4);
            int nombreNiveaux = creeNiveauxImageEqui(fichierPano, repertPanos);
            panoCree.setNomFichier(fichierPano);
            Image image3 = null;
            if ("tif".equals(extension)) {
                try {
                    image2 = ReadWriteImage.readTiff(fichierPano);
                    image2 = ReadWriteImage.resizeImage(image2, 1200, 600);
                    image3 = ReadWriteImage.readTiff(fichierPano);
                    image3 = ReadWriteImage.resizeImage(image3, 300, 150);
                } catch (ImageReadException | IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                image2 = new Image("file:" + fichierPano, 1200, 600, true, true, true);
                image3 = new Image("file:" + fichierPano, 300, 150, true, true, true);
            }
            panoCree.setNombreNiveaux(nombreNiveaux);
            panoCree.setVignettePanoramique(image3);
            panoCree.setImagePanoramique(image2);
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
            int nombreNiveaux = creeNiveauxImageCube(nom + "_u." + extension, repertPanos, "dessus");
            creeNiveauxImageCube(nom + "_d." + extension, repertPanos, "dessous");
            creeNiveauxImageCube(nom + "_l." + extension, repertPanos, "gauche");
            creeNiveauxImageCube(nom + "_r." + extension, repertPanos, "droite");
            creeNiveauxImageCube(nom + "_f." + extension, repertPanos, "devant");
            creeNiveauxImageCube(nom + "_b." + extension, repertPanos, "derrière");
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
        panoramiquesProjet[nombrePanoramiques] = panoCree;
        panoActuel = nombrePanoramiques;
        nombrePanoramiques++;
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
        File repert = new File(repertAppli + File.separator + "templates");
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
        File repert = new File(repertAppli + File.separator + "templates");
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
        menuPrincipal = new MenuBar();
        menuPrincipal.setMinHeight(25);
        menuPrincipal.setPrefHeight(29);
        menuPrincipal.setPrefWidth(3000);
        /* 
         Menu projets
         */
        Menu menuProjet = new Menu(rb.getString("projets"));
        menuPrincipal.getMenus().add(menuProjet);
        nouveauProjet = new MenuItem(rb.getString("nouveauProjet"));
        nouveauProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuProjet.getItems().add(nouveauProjet);
        chargeProjet = new MenuItem(rb.getString("ouvrirProjet"));
        chargeProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        menuProjet.getItems().add(chargeProjet);
        sauveProjet = new MenuItem(rb.getString("sauverProjet"));
        sauveProjet.setDisable(true);
        sauveProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuProjet.getItems().add(sauveProjet);
        sauveSousProjet = new MenuItem(rb.getString("sauverProjetSous"));
        sauveSousProjet.setDisable(true);
        sauveSousProjet.setAccelerator(KeyCombination.keyCombination("Shift+Ctrl+S"));
        menuProjet.getItems().add(sauveSousProjet);
        derniersProjets = new Menu(rb.getString("derniersProjets"));
//        derniersProjets.setDisable(true);
        menuProjet.getItems().add(derniersProjets);
        fichHistoFichiers = new File(repertConfig.getAbsolutePath() + File.separator + "derniersprojets.cfg");
        nombreHistoFichiers = 0;
        if (fichHistoFichiers.exists()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fichHistoFichiers), "UTF-8"))) {
                while ((texteHisto = br.readLine()) != null) {
                    MenuItem menuDerniersFichiers = new MenuItem(texteHisto);
                    derniersProjets.getItems().add(menuDerniersFichiers);
                    histoFichiers[nombreHistoFichiers] = texteHisto;
                    nombreHistoFichiers++;
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

        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        menuProjet.getItems().add(sep1);
        fermerProjet = new MenuItem(rb.getString("quitterApplication"));
        fermerProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuProjet.getItems().add(fermerProjet);
        /*
         Menu affichage
         */
        Menu menuAffichage = new Menu(rb.getString("affichage"));
        menuPrincipal.getMenus().add(menuAffichage);
        affichageVisite = new MenuItem(rb.getString("main.creationVisite"));
        affichageVisite.setAccelerator(KeyCombination.keyCombination("Ctrl+1"));
        menuAffichage.getItems().add(affichageVisite);
        affichageInterface = new MenuItem(rb.getString("main.creationInterface"));
        affichageInterface.setAccelerator(KeyCombination.keyCombination("Ctrl+2"));
        menuAffichage.getItems().add(affichageInterface);
        affichagePlan = new MenuItem(rb.getString("main.tabPlan"));
        affichagePlan.setAccelerator(KeyCombination.keyCombination("Ctrl+3"));
        affichagePlan.setDisable(true);
        menuAffichage.getItems().add(affichagePlan);
        SeparatorMenuItem sep3 = new SeparatorMenuItem();
        menuAffichage.getItems().add(sep3);
        configTransformation = new MenuItem(rb.getString("affichageConfiguration"));
        menuAffichage.getItems().add(configTransformation);

        /*
         Menu panoramiques
         */
        menuPanoramique = new Menu(rb.getString("panoramiques"));
        menuPanoramique.setDisable(true);
        menuPrincipal.getMenus().add(menuPanoramique);
        ajouterPano = new MenuItem(rb.getString("ajouterPanoramiques"));
        ajouterPano.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        menuPanoramique.getItems().add(ajouterPano);
        ajouterPlan = new MenuItem(rb.getString("ajouterPlan"));
        ajouterPlan.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        menuPanoramique.getItems().add(ajouterPlan);
        ajouterPlan.setDisable(true);
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        menuPanoramique.getItems().add(sep2);
        visiteGenere = new MenuItem(rb.getString("genererVisite"));
        visiteGenere.setDisable(true);
        visiteGenere.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        menuPanoramique.getItems().add(visiteGenere);
        /*
         Menu Modèles 
         */
        menuModeles = new Menu(rb.getString("menuModele"));
        menuPrincipal.getMenus().add(menuModeles);

        chargerModele = new MenuItem(rb.getString("modeleCharger"));
        menuModeles.getItems().add(chargerModele);

        sauverModele = new MenuItem(rb.getString("modeleSauver"));
        menuModeles.getItems().add(sauverModele);

        /*
         Menu transformations 
         */
        menuTransformation = new Menu(rb.getString("outils"));
        menuPrincipal.getMenus().add(menuTransformation);
        equi2CubeTransformation = new MenuItem(rb.getString("outilsEqui2Cube"));
        menuTransformation.getItems().add(equi2CubeTransformation);
        cube2EquiTransformation = new MenuItem(rb.getString("outilsCube2Equi"));
        menuTransformation.getItems().add(cube2EquiTransformation);

        /*
         Menu Aide
         */
        Menu menuAide = new Menu(rb.getString("aide"));
        menuPrincipal.getMenus().add(menuAide);
        aide = new MenuItem(rb.getString("aideAide"));
        aide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuAide.getItems().add(aide);
        SeparatorMenuItem sep4 = new SeparatorMenuItem();
        menuAide.getItems().add(sep4);
        aPropos = new MenuItem(rb.getString("aideAPropos"));
        menuAide.getItems().add(aPropos);
        /*
         barre de boutons 
         */
        barreBouton = new HBox();
        barreBouton.getStyleClass().add("menuBarreOutils1");

        barreBouton.setPrefHeight(50);
        barreBouton.setMinHeight(50);
        barreBouton.setPrefWidth(3000);
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
        imgNouveauProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/nouveauProjet.png"));
        spBtnNouvprojet.setContent(imgNouveauProjet);
        Tooltip t0 = new Tooltip(rb.getString("nouveauProjet"));
        t0.setStyle(tooltipStyle);
        spBtnNouvprojet.setTooltip(t0);
        barreBouton.getChildren().add(spBtnNouvprojet);
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
        imgChargeProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ouvrirProjet.png"));
        spBtnOuvrirProjet.setContent(imgChargeProjet);
        Tooltip t1 = new Tooltip(rb.getString("ouvrirProjet"));
        t1.setStyle(tooltipStyle);
        spBtnOuvrirProjet.setTooltip(t1);
        barreBouton.getChildren().add(spBtnOuvrirProjet);

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
        imgSauveProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/sauveProjet.png"));
        spBtnSauveProjet.setContent(imgSauveProjet);
        Tooltip t2 = new Tooltip(rb.getString("sauverProjet"));
        t2.setStyle(tooltipStyle);
        spBtnSauveProjet.setTooltip(t2);
        barreBouton.getChildren().add(spBtnSauveProjet);
        Separator sepImages = new Separator(Orientation.VERTICAL);
        sepImages.prefHeight(200);
        barreBouton.getChildren().add(sepImages);
        imgSauveProjet.setDisable(true);
        imgSauveProjet.setOpacity(0.3);
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
        imgAjouterPano = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ajoutePanoramique.png"));
        spBtnAjoutePano.setContent(imgAjouterPano);
        Tooltip t3 = new Tooltip(rb.getString("ajouterPanoramiques"));
        t3.setStyle(tooltipStyle);
        spBtnAjoutePano.setTooltip(t3);
        barreBouton.getChildren().add(spBtnAjoutePano);
        imgAjouterPano.setDisable(true);
        imgAjouterPano.setOpacity(0.3);

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
        imgAjouterPlan = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ajoutePlan.png"));
        spBtnAjoutePlan.setContent(imgAjouterPlan);
        Tooltip t31 = new Tooltip(rb.getString("ajouterPlan"));
        t31.setStyle(tooltipStyle);
        spBtnAjoutePlan.setTooltip(t31);
        barreBouton.getChildren().add(spBtnAjoutePlan);
        imgAjouterPlan.setDisable(true);
        imgAjouterPlan.setOpacity(0.3);

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
        imgVisiteGenere = new ImageView(new Image("file:" + repertAppli + File.separator + "images/genereVisite.png"));
        spBtnGenereVisite.setContent(imgVisiteGenere);
        Tooltip t4 = new Tooltip(rb.getString("genererVisite"));
        t4.setStyle(tooltipStyle);
        spBtnGenereVisite.setTooltip(t4);
        barreBouton.getChildren().add(spBtnGenereVisite);
        imgVisiteGenere.setDisable(true);
        imgVisiteGenere.setOpacity(0.3);
        Separator sepImages1 = new Separator(Orientation.VERTICAL);
        sepImages1.prefHeight(200);
        barreBouton.getChildren().add(sepImages1);
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
        imgEqui2Cube = new ImageView(new Image("file:" + repertAppli + File.separator + "images/equi2cube.png"));
        spBtnEqui2Cube.setContent(imgEqui2Cube);
        Tooltip t6 = new Tooltip(rb.getString("outilsEqui2Cube"));
        t6.setStyle(tooltipStyle);
        spBtnEqui2Cube.setTooltip(t6);
        barreBouton.getChildren().add(spBtnEqui2Cube);

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
        imgCube2Equi = new ImageView(new Image("file:" + repertAppli + File.separator + "images/cube2equi.png"));
        spBtnCube2Equi.setContent(imgCube2Equi);
        Tooltip t5 = new Tooltip(rb.getString("outilsCube2Equi"));
        t5.setStyle(tooltipStyle);
        spBtnCube2Equi.setTooltip(t5);
        barreBouton.getChildren().add(spBtnCube2Equi);

        myPane.getChildren().addAll(menuPrincipal, barreBouton);
        racine.getChildren().add(myPane);
        nouveauProjet.setOnAction((ActionEvent e) -> {
            projetsNouveau();
        });
        chargeProjet.setOnAction((ActionEvent e) -> {
            try {
                try {
                    projetCharge();
                } catch (InterruptedException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        sauveProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauve();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        sauveSousProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauveSous();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        visiteGenere.setOnAction((ActionEvent e) -> {
            try {
                genereVisite();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        fermerProjet.setOnAction((ActionEvent e) -> {
            try {
                projetsFermer();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ajouterPano.setOnAction((ActionEvent e) -> {
            try {
                panoramiquesAjouter();
            } catch (InterruptedException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ajouterPlan.setOnAction((ActionEvent e) -> {
            planAjouter();
        });
        aPropos.setOnAction((ActionEvent e) -> {
            aideapropos();
        });
        aide.setOnAction((ActionEvent e) -> {
            AideDialogController.affiche();
        });

        chargerModele.setOnAction((ActionEvent e) -> {
            try {
                modeleCharger();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        sauverModele.setOnAction((ActionEvent e) -> {
            try {
                modeleSauver();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });

        cube2EquiTransformation.setOnAction((ActionEvent e) -> {
            transformationCube2Equi();
        });

        equi2CubeTransformation.setOnAction((ActionEvent e) -> {
            transformationEqui2Cube();
        });
        affichageVisite.setOnAction((ActionEvent e) -> {
            tabPaneEnvironnement.getSelectionModel().select(0);
        });
        affichageInterface.setOnAction((ActionEvent e) -> {
            tabPaneEnvironnement.getSelectionModel().select(1);
        });
        affichagePlan.setOnAction((ActionEvent e) -> {
            if (!tabPlan.isDisabled()) {
                tabPaneEnvironnement.getSelectionModel().select(2);
            }
        });

        configTransformation.setOnAction((ActionEvent e) -> {
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
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
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
                .title(rb.getString("main.supprimerPano"))
                .message(rb.getString("main.etesVousSur"))
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        if (reponse == Dialog.Actions.YES) {

            int panCourant = listeChoixPanoramique.getSelectionModel().getSelectedIndex();
            for (int i = panCourant; i < nombrePanoramiques - 1; i++) {
                panoramiquesProjet[i] = panoramiquesProjet[i + 1];
            }
            int paneEntree = -1;
            nombrePanoramiques--;
            if (listeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex() != panCourant) {
                paneEntree = listeChoixPanoramiqueEntree.getSelectionModel().getSelectedIndex();
            }
            if (paneEntree > panCourant) {
                paneEntree--;
            }
            listeChoixPanoramique.getItems().clear();
            listeChoixPanoramiqueEntree.getItems().clear();
            for (int i = 0; i < nombrePanoramiques; i++) {
                String nomPano = panoramiquesProjet[i].getNomFichier();
                nomPano = nomPano.substring(nomPano.lastIndexOf(File.separator) + 1, nomPano.length());
                listeChoixPanoramique.getItems().add(nomPano);
                listeChoixPanoramiqueEntree.getItems().add(nomPano);
            }
            if (paneEntree != -1) {
                listeChoixPanoramiqueEntree.setValue(listeChoixPanoramique.getItems().get(paneEntree));
            }
            listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(0));
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

        hauteurInterface = height;
        largeurInterface = width;
        /**
         * Création des éléments constitutifs de l'écran
         */
        VBox root = new VBox();
        creeMenu(root, width);
        tabPaneEnvironnement = new TabPane();
//        tabPaneEnvironnement.setTranslateZ(5);
        tabPaneEnvironnement.setMinHeight(height - 60);
        tabPaneEnvironnement.setMaxHeight(height - 60);
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
        panneauPlan = gestionnairePlan.tabInterface;
        tabInterface.setContent(visualiseur);
        tabPlan.setContent(panneauPlan);

        HBox hbEnvironnement = new HBox();
        TextField txtTitrePano;
        TextField tfTitreVisite;
        RadioButton radSphere;
        RadioButton radCube;
        CheckBox chkAfficheTitre;
        CheckBox chkAfficheInfo;

        tabPaneEnvironnement.getTabs().addAll(tabVisite, tabInterface, tabPlan);
        //tabPaneEnvironnement.setTranslateY(80);
        tabPaneEnvironnement.setSide(Side.TOP);
        tabPaneEnvironnement.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            gestionnaireInterface.rafraichit();
        });

        tabVisite.setText(rb.getString("main.creationVisite"));
        tabVisite.setClosable(false);
        tabInterface.setText(rb.getString("main.creationInterface"));
        tabInterface.setClosable(false);
        tabPlan.setText(rb.getString("main.tabPlan"));
        tabPlan.setClosable(false);
        tabPlan.setDisable(true);
        tabVisite.setContent(hbEnvironnement);
        double largeur;
        String labelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-padding : 5px;  -fx-border : 1px solid #777;-fx-width : 100px;-fx-margin : 5px; ";

        scene = new Scene(root, width, height, Color.rgb(221, 221, 221));
//        if (systemeExploitation.indexOf("inux") != -1) {
//            root.setStyle("-fx-font-size : 7pt;-fx-font-family: sans-serif;");
//        } else {
        root.setStyle("-fx-font-size : 9pt;-fx-font-family: Arial;");
//        }
        panneauOutils = new ScrollPane();
        panneauOutils.setId("panOutils");
//        panneauOutils.setStyle("-fx-background-color : #ccc;");
        outils = new VBox();
        paneChoixPanoramique = new VBox();
        paneChoixPanoramique.setTranslateX(10);
        paneChoixPanoramique.setId("choixPanoramique");
        Label lblTitreVisite = new Label(rb.getString("main.titreVisite"));
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

        Label lblChoixPanoramiqueEntree = new Label(rb.getString("main.panoEntree"));
        lblChoixPanoramiqueEntree.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblChoixPanoramiqueEntree.setPadding(new Insets(15, 5, 5, 0));
        lblChoixPanoramiqueEntree.setMinWidth(largeurOutils - 20);
        lblChoixPanoramiqueEntree.setAlignment(Pos.CENTER);

        lblChoixPanoramique = new Label(rb.getString("main.panoAffiche"));
        lblChoixPanoramique.setStyle("-fx-font-size : 10pt;-fx-font-weight : bold;");
        lblChoixPanoramique.setPadding(new Insets(10, 5, 5, 0));
        lblChoixPanoramique.setMinWidth(largeurOutils - 20);
        lblChoixPanoramique.setAlignment(Pos.CENTER);

        Separator sepPano = new Separator(Orientation.HORIZONTAL);
        sepPano.setMinHeight(10);
        listeChoixPanoramique.setVisibleRowCount(10);
        listeChoixPanoramique.setTranslateX(60);
        Pane fond = new Pane();
        fond.setCursor(Cursor.HAND);
        ImageView ivSupprPanoramique = new ImageView(new Image("file:" + repertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
        fond.setTranslateX(340);
        fond.setTranslateY(-80);
        Tooltip t = new Tooltip(rb.getString("main.supprimePano"));
        t.setStyle(tooltipStyle);
        Tooltip.install(fond, t);
        fond.getChildren().add(ivSupprPanoramique);
        fond.setOnMouseClicked(
                (MouseEvent me) -> {
                    retirePanoCourant();
                }
        );

        listeChoixPanoramiqueEntree.setTranslateX(60);
        Separator sepInfo = new Separator(Orientation.HORIZONTAL);
        Label lblTitrePano = new Label(rb.getString("main.titrePano"));
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

        paneChoixPanoramique.getChildren().addAll(
                lblTitreVisite, tfTitreVisite,
                lblChoixPanoramiqueEntree, listeChoixPanoramiqueEntree, sepPano,
                lblChoixPanoramique, listeChoixPanoramique, fond,
                lblTitrePano, txtTitrePano, sepInfo
        );
        paneChoixPanoramique.setSpacing(10);
        /*
         à modifier pour afficher le panneau des derniers fichiers;        
         */
        //outils.getChildren().addAll(lastFiles, paneChoixPanoramique);

        outils.getChildren().addAll(paneChoixPanoramique);

        paneChoixPanoramique.setVisible(false);
        /*
         Création du panneau d'info du panoramique
         */

        vuePanoramique = new ScrollPane();

        coordonnees = new HBox();
        pano = new Pane();
        panneau2 = new AnchorPane();
        lblLong = new Label("");
        lblLat = new Label("");
        imagePanoramique = new ImageView();

        primaryStage.setScene(scene);
        //scene.getStylesheets().add("file:css/test.css");
        /**
         *
         */
        vuePanoramique.setPrefSize(width - largeurOutils - 20, height - 130);
        vuePanoramique.setMaxSize(width - largeurOutils - 20, height - 130);
        vuePanoramique.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vuePanoramique.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vuePanoramique.setTranslateY(5);

        //vuePanoramique.setStyle("-fx-background-color : #c00;");
        /**
         *
         */
        panneauOutils.setContent(outils);
        panneauOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        panneauOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        panneauOutils.setPrefSize(largeurOutils, height - 240);
        panneauOutils.setMaxWidth(largeurOutils);
        panneauOutils.setMaxHeight(height - 240);
        panneauOutils.setTranslateY(15);
        panneauOutils.setTranslateX(20);
//        panneauOutils.setStyle("-fx-background-color : #ccc;");
        /**
         *
         */
        pano.setCursor(Cursor.CROSSHAIR);
        outils.setPrefWidth(largeurOutils - 20);
//        outils.setStyle("-fx-background-color : #ccc;");
        outils.minHeight(height - 130);
        outils.setLayoutX(10);
//        lblLong.setStyle(labelStyle);
//        lblLat.setStyle(labelStyle);
        lblLong.setPrefSize(100, 15);
        lblLat.setPrefSize(100, 15);
        lblLat.setTranslateX(50);
//        panneau2.setStyle("-fx-background-color : #ddd;");
        panneau2.setPrefSize(width - largeurOutils - 20, height - 140);

        imagePanoramique.setCache(true);
        largeur = largeurMax - 60;
        imagePanoramique.setFitWidth(largeur);
        imagePanoramique.setFitHeight(largeur / 2.0d);
        imagePanoramique.setLayoutX((largeurMax - largeur) / 2.d);
        pano.getChildren().add(imagePanoramique);
        pano.setPrefSize(imagePanoramique.getFitWidth(), imagePanoramique.getFitHeight());
        pano.setMaxSize(imagePanoramique.getFitWidth(), imagePanoramique.getFitHeight());

        pano.setLayoutY(20);
        lblLong.setTranslateX(50);
        lblLat.setTranslateX(80);
        coordonnees.getChildren().setAll(lblLong, lblLat);
        vuePanoramique.setContent(panneau2);
        hbEnvironnement.getChildren().setAll(vuePanoramique, panneauOutils);
        AnchorPane paneEnv = new AnchorPane();
        paneAttends = new AnchorPane();
        paneAttends.setPrefHeight(250);
        paneAttends.setPrefWidth(600);
        paneAttends.setMaxWidth(600);
        paneAttends.setStyle("-fx-background-color : #ccc;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;");
        paneAttends.setLayoutX((width - paneAttends.getPrefWidth()) / 2.d);
        paneAttends.setLayoutY((height - paneAttends.getPrefHeight()) / 2.d - 55);
        p1 = new ProgressBar();
        p1.setPrefSize(400, 30);
        p1.setLayoutX((paneAttends.getPrefWidth() - p1.getPrefWidth()) / 2);
        p1.setLayoutY(70);
        Label lblAttends = new Label(rb.getString("main.attendsChargement"));
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
        paneAttends.getChildren().addAll(lblAttends, p1, lblCharge, lblNiveaux);
        paneAttends.setVisible(false);
        paneEnv.getChildren().addAll(tabPaneEnvironnement, paneAttends);
//        paneEnv.getChildren().addAll(tabPaneEnvironnement);
        root.getChildren().addAll(paneEnv);
        panneau2.getChildren().setAll(coordonnees, pano);
        primaryStage.show();
        popUp.affichePopup();
        lblDragDrop = new Label(rb.getString("main.dragDrop"));
        lblDragDrop.setMinHeight(vuePanoramique.getPrefHeight());
        lblDragDrop.setMaxHeight(vuePanoramique.getPrefHeight());
        lblDragDrop.setMinWidth(vuePanoramique.getPrefWidth());
        lblDragDrop.setMaxWidth(vuePanoramique.getPrefWidth());
        lblDragDrop.setAlignment(Pos.CENTER);
        lblDragDrop.setTextFill(Color.web("#c9c7c7"));
        lblDragDrop.setTextAlignment(TextAlignment.CENTER);
        lblDragDrop.setWrapText(true);
        lblDragDrop.setStyle("-fx-font-size:72px");
        lblDragDrop.setTranslateY(-100);
        panneau2.getChildren().addAll(lblDragDrop, afficheLegende());
    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        File rep = new File("");
        currentDir = rep.getAbsolutePath();
        repertAppli = rep.getAbsolutePath();
        repertConfig = new File(repertAppli + File.separator + "configPV");
        if (!repertConfig.exists()) {
            repertConfig.mkdirs();
            locale = new Locale("fr", "FR");
            setUserAgentStylesheet("file:css/clair.css");
            repertoireProjet = repertAppli;
        } else {
            lisFichierConfig();
        }
        rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", locale);
        stPrincipal = primaryStage;
        stPrincipal.setResizable(false);
        stPrincipal.setTitle("PanoVisu v" + numVersion);
        //AquaFx.style();
//        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int) tailleEcran.getHeight() - 20;
        int largeur = (int) tailleEcran.getWidth() - 20;
        largeurMax = tailleEcran.getWidth() - 450.0d;
        creeEnvironnement(primaryStage, largeur, hauteur);

        File repertTempFile = new File(repertAppli + File.separator + "temp");
        repertTemp = repertTempFile.getAbsolutePath();

        if (!repertTempFile.exists()) {
            repertTempFile.mkdirs();
        } else {
            deleteDirectory(repertTemp);
        }
        String extTemp = genereChaineAleatoire(20);
        repertTemp = repertTemp + File.separator + "temp" + extTemp;
        repertTempFile = new File(repertTemp);
        repertTempFile.mkdirs();
        installeEvenements();
        projetsNouveau();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Action reponse = null;
            Localization.setLocale(locale);
            if (!dejaSauve) {
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
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

                deleteDirectory(repertTemp);
                File ftemp = new File(repertTemp);
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
        numVersion = pack.getImplementationVersion();
        systemeExploitation = System.getProperty("os.name");
        //Properties properties = System.getProperties();
        //properties.list(//System.out);
        launch(args);
    }
}
