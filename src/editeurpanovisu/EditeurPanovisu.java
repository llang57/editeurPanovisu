/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import impl.org.controlsfx.i18n.Localization;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.ToolTipManager;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author LANG Laurent
 */
public class EditeurPanovisu extends Application {

    static private popUpHandler popUp;
    static private ImageView imagePanoramique;
    private Image image2;
    static private Label lblLong, lblLat;
    static private VBox panneau2;
    static private HBox coordonnees;
    static private String currentDir;
    static private int numPoints = 0;
    static private Panoramique[] panoramiquesProjet = new Panoramique[50];
    static private int nombrePanoramiques = 0;
    static private int panoActuel = 0;
    static private File fichProjet;

    static private Pane pano;
    static private VBox paneChoixPanoramique;
    static private VBox outils;
    static private File file;
    static private Scene scene;
    static private ScrollPane vuePanoramique;
    static private ScrollPane panneauOutils;
    static private double largeurMax;
    static private boolean estCharge = false;
    static private boolean repertSauveChoisi = false;
    static public String repertAppli;
    static private String repertTemp;
    static private String repertPanos;
    static public String repertoireProjet;
    final static private ComboBox listeChoixPanoramique = new ComboBox();
    static private Label lblChoixPanoramique;
    static private boolean panoCharge = false;
    static private String panoAffiche = "";
    static private boolean dejaSauve = true;
    static private Stage stPrincipal;
    static private String[] histoFichiers;
    static private File fichHistoFichiers;
    static private String txtRepertConfig;
    static private Button valideChargeDerniersFichiers;

    static private Menu derniersProjets;
    private Menu menuPanoramique;
    private Menu menuTransformation;
    private MenuItem cube2EquiTransformation;
    private MenuItem equi2CubeTransformation;

    private MenuItem aPropos;
    private MenuItem aide;
    private ImageView imgNouveauProjet;
    private ImageView imgSauveProjet;
    private ImageView imgChargeProjet;
    private ImageView imgVisiteGenere;
    private ImageView imgAjouterPano;
    private ImageView imgCube2Equi;
    private ImageView imgEqui2Cube;

    private MenuItem nouveauProjet;
    private MenuItem sauveProjet;
    private MenuItem ajouterPano;

    private MenuItem fermerProjet;

    private MenuItem sauveSousProjet;
    private MenuItem visiteGenere;
    private MenuItem chargeProjet;

    private void genereVisite() throws IOException {
        System.out.println("Génère la visite");
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        if (!dejaSauve) {
            projetSauve();
        }
        if (dejaSauve) {
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

            for (int i = 0; i < nombrePanoramiques; i++) {
                String fPano = "panos/" + panoramiquesProjet[i].getNomFichier().substring(panoramiquesProjet[i].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[i].getNomFichier().length()).split("\\.")[0];
                String affInfo = (panoramiquesProjet[i].isAfficheInfo()) ? "oui" : "non";
                String affTitre = (panoramiquesProjet[i].isAfficheTitre()) ? "oui" : "non";
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
                        + "      type=\"" + panoramiquesProjet[i].getTypePanoramique() + "\"\n"
                        + "      affinfo=\"" + affInfo + "\"\n"
                        + "      afftitre=\"" + affTitre + "\"\n"
                        + "   />\n"
                        + "   <!--Définition de la Barre de navigation-->\n"
                        + "   <boutons \n"
                        + "      styleBoutons=\"retinavert\"\n"
                        + "      couleur=\"rgba(255,255,255,0)\"\n"
                        + "      bordure=\"rgba(255,255,255,0)\"\n"
                        + "      deplacements=\"oui\" \n"
                        + "      zoom=\"non\" \n"
                        + "      outils=\"oui\"\n"
                        + "      fs=\"oui\" \n"
                        + "      souris=\"oui\" \n"
                        + "      rotation=\"oui\" \n"
                        + "      positionX=\"center\"\n"
                        + "      positionY=\"bottom\" \n"
                        + "      dX=\"0\" \n"
                        + "      dY=\"10\"\n"
                        + "      visible=\"oui\"\n"
                        + "   />\n"
                        + "    <!--Définition des hotspots-->  \n"
                        + "   <hotspots>\n";
                for (int j = 0; j < panoramiquesProjet[i].getNombreHotspots(); j++) {
                    HotSpot HS = panoramiquesProjet[i].getHotspot(j);
                    double longit = HS.getLongitude() - 180;
                    String txtAnime = (HS.isAnime()) ? "true" : "false";
                    contenuFichier
                            += "      <point \n"
                            + "           long=\"" + longit + "\"\n"
                            + "           lat=\"" + HS.getLatitude() + "\"\n"
                            + "           image=\"panovisu/images/hotspots/hotspotvert.png\"\n"
                            + "           xml=\"xml/" + HS.getFichierXML() + "\"\n"
                            + "           info=\"" + HS.getInfo() + "\"\n"
                            + "           anime=\"" + txtAnime + "\"\n"
                            + "      />\n";
                }
                contenuFichier += "   </hotspots>\n"
                        + "</scene>\n";
                String fichierPano = panoramiquesProjet[i].getNomFichier();
                String nomXMLFile = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length()).split("\\.")[0] + ".xml";
                xmlFile = new File(xmlRepert + File.separator + nomXMLFile);
                xmlFile.setWritable(true);
                FileWriter fw = new FileWriter(xmlFile);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(contenuFichier);
                }
            }
            Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int hauteur = (int) tailleEcran.getHeight() - 200;
            String fichierHTML = "<!DOCTYPE html>\n"
                    + "<html lang=\"fr\">\n"
                    + "    <head>\n"
                    + "        <title>Panovisu - visualiseur 100% html5 (three.js)</title>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <header>\n"
                    + "\n"
                    + "        </header>\n"
                    + "        <article>\n"
                    + "            <div id=\"pano\">\n"
                    + "            </div>\n"
                    + "        </article style=\"height : " + hauteur + "px;\">\n"
                    + "        <script src=\"panovisu/panovisuInit.js\"></script>\n"
                    + "        <script src=\"panovisu/panovisu.js\"></script>\n"
                    + "        <script>\n"
                    + "                $(window).resize(function(){\n"
                    + "                    $(\"article\").height($(window).height()-10);\n"
                    + "                })\n"
                    + "\n"
                    + "            $(function() {\n"
                    + "                $(\"article\").height($(window).height()-10);\n"
                    + "                ajoutePano({\n"
                    + "                    panoramique: \"pano\",\n"
                    + "                    fenX: \"100%\",\n"
                    + "                    fenY: \"100%\",\n"
                    + "                    xml: \"xml/PANO.xml\"\n"
                    + "                });\n"
                    + "            });\n"
                    + "        </script>\n"
                    + "        <footer>\n"
                    + "            Page de test - Panovisu\n"
                    + "        </footer>\n"
                    + "    </body>\n"
                    + "</html>\n";
            fichierHTML = fichierHTML.replace("PANO",
                    panoramiquesProjet[0].getNomFichier().substring(panoramiquesProjet[0].getNomFichier().lastIndexOf(File.separator) + 1, panoramiquesProjet[0].getNomFichier().length()).split("\\.")[0]);
            File fichIndexHTML = new File(repertTemp + File.separator + "index.html");
            fichIndexHTML.setWritable(true);
            FileWriter fw1 = new FileWriter(fichIndexHTML);
            try (BufferedWriter bw1 = new BufferedWriter(fw1)) {
                bw1.write(fichierHTML);
            }

            File repertVisite = new File(repertoireProjet + File.separator + "visite");
            if (!repertVisite.exists()) {
                repertVisite.mkdirs();
            }

            String nomRepertVisite = repertVisite.getAbsolutePath();
            System.out.println("Repertoire de la visite :" + nomRepertVisite);
            copieDirectory(repertTemp, nomRepertVisite);
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("Génération de la visite")
                    .message("Votre visite a bien été généré dans le répertoire : " + nomRepertVisite)
                    .showInformation();
        } else {
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("Génération de la visite")
                    .message("Votre n'a pu être générée, votre fichier n'étant pas sauvegardé")
                    .showError();

        }
    }

    /**
     *
     * @param event
     */
    private void panoramiquesAjouter() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPEG Files (*.jpg)", "*.jpg");

        File repert = new File(currentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().add(extFilter);

        List<File> list = fileChooser.showOpenMultipleDialog(null);
        if (list != null) {
            for (File file : list) {
                dejaSauve = false;
                sauveProjet.setDisable(false);
                currentDir = file.getParent();
                File imageRepert = new File(repertTemp + File.separator + "panos");

                if (!imageRepert.exists()) {
                    imageRepert.mkdirs();
                }
                repertPanos = imageRepert.getAbsolutePath();
                try {
                    copieFichierRepertoire(file.getPath(), repertPanos);
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                affichePano(file.getPath());
            }
            installeEvenements();
            imgVisiteGenere.setOpacity(1.0);
            imgVisiteGenere.setDisable(false);

        }

    }

    /**
     *
     */
    private void clickBtnValidePano() {
        TextArea txtTitrePano = (TextArea) scene.lookup("#txttitrepano");
        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
        RadioButton radCube = (RadioButton) scene.lookup("#radcube");
        System.out.println("Pano Actuel " + panoActuel + "  " + txtTitrePano.getText());
        panoramiquesProjet[panoActuel].setTitrePanoramique(txtTitrePano.getText());
        panoramiquesProjet[panoActuel].setAfficheInfo(chkAfficheInfo.isSelected());
        panoramiquesProjet[panoActuel].setAfficheTitre(chkAfficheTitre.isSelected());
        if (radSphere.isSelected()) {
            panoramiquesProjet[panoActuel].setTypePanoramique(Panoramique.SPHERE);

        } else {
            panoramiquesProjet[panoActuel].setTypePanoramique(Panoramique.CUBE);
        }
    }

    /**
     *
     */
    private void projetCharge() throws IOException {
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        Action reponse = null;
        Localization.setLocale(new Locale("fr", "FR"));
        if (!dejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Charge un Projet")
                    .masthead("vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Réponse" + reponse);
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {

            FileChooser repertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
            repertChoix.getExtensionFilters().add(extFilter);
            File repert = new File(repertoireProjet + File.separator);
            repertChoix.setInitialDirectory(repert);
            fichProjet = null;
            fichProjet = repertChoix.showOpenDialog(stPrincipal);
            if (fichProjet != null) {
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
                imagePanoramique.setImage(null);
                listeChoixPanoramique.getItems().clear();
                FileReader fr;
                try {
                    fr = new FileReader(fichProjet);
                    String texte;
                    try (BufferedReader br = new BufferedReader(fr)) {
                        texte = "";
                        String ligneTexte;
                        while ((ligneTexte = br.readLine()) != null) {
                            texte += ligneTexte;
                        }
                    }
                    System.out.println(texte);
                    analyseLigne(texte);

                    panoActuel = 0;
                    affichePanoChoisit(panoActuel);
                    panoCharge = true;
                    paneChoixPanoramique.setVisible(true);
                    listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(0));
                    for (int ii = 0; ii < nombrePanoramiques; ii++) {
                        Panoramique pano1 = panoramiquesProjet[ii];
                        System.out.println(nombrePanoramiques + " pano n°" + ii + " fichier : " + pano1.getNomFichier());

                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
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
            repertoireProjet = fichProjet.getParent();
            repertSauveChoisi = true;
            File fichierProjet = new File(fichProjet.getAbsolutePath());
            if (!fichierProjet.exists()) {
                fichierProjet.createNewFile();
            }
            dejaSauve = true;
            String contenuFichier = "";
            for (int i = 0; i < nombrePanoramiques; i++) {
                contenuFichier += "[Panoramique=>"
                        + "fichier:" + panoramiquesProjet[i].getNomFichier()
                        + ";nb:" + panoramiquesProjet[i].getNombreHotspots()
                        + ";titre:" + panoramiquesProjet[i].getTitrePanoramique() + ""
                        + ";type:" + panoramiquesProjet[i].getTypePanoramique()
                        + ";afficheInfo:" + panoramiquesProjet[i].isAfficheInfo()
                        + ";afficheTitre:" + panoramiquesProjet[i].isAfficheTitre()
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
            }
            fichProjet.setWritable(true);
            FileWriter fw = new FileWriter(fichProjet);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contenuFichier);
            }
            Dialogs.create().title("Editeur PanoVisu")
                    .masthead("Sauvegarde de fichier")
                    .message("Votre fichier à bien été sauvegardé")
                    .showInformation();
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

            repertoireProjet = fichProjet.getParent();
            repertSauveChoisi = true;
            File fichierProjet = new File(fichProjet.getAbsolutePath());
            if (!fichierProjet.exists()) {
                fichierProjet.createNewFile();
            }
            dejaSauve = true;
            String contenuFichier = "";
            for (int i = 0; i < nombrePanoramiques; i++) {
                contenuFichier += "[Panoramique=>"
                        + "fichier:" + panoramiquesProjet[i].getNomFichier()
                        + ";nb:" + panoramiquesProjet[i].getNombreHotspots()
                        + ";titre:" + panoramiquesProjet[i].getTitrePanoramique() + ""
                        + ";type:" + panoramiquesProjet[i].getTypePanoramique()
                        + ";afficheInfo:" + panoramiquesProjet[i].isAfficheInfo()
                        + ";afficheTitre:" + panoramiquesProjet[i].isAfficheTitre()
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
            }
            System.out.println(contenuFichier);
            fichProjet.setWritable(true);
            FileWriter fw = new FileWriter(fichProjet);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(contenuFichier);
            }
        }

    }

    /**
     *
     */
    private void aideAPropos() {
        try {
            popUp.affichePopup();
        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void projetsFermer() {
        Action reponse = null;
        Localization.setLocale(new Locale("fr", "FR"));
        if (!dejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Quitter l'application")
                    .masthead("vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
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
        Localization.setLocale(new Locale("fr", "FR"));
        if (!dejaSauve) {
            reponse = Dialogs.create()
                    .owner(null)
                    .title("Nouveau Projet")
                    .masthead("vous n'avez pas sauvegardé votre projet")
                    .message("Voulez vous le sauver ?")
                    .showConfirm();

        }
        if (reponse == Dialog.Actions.YES) {
            try {
                projetSauve();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
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
            imagePanoramique.setImage(null);
            listeChoixPanoramique.getItems().clear();
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
     * @param emplacement
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

    private void analyseLigne(String ligneComplete) {
        nombrePanoramiques = 0;
        ligneComplete = ligneComplete.replace("[", "|");
        System.out.println("ligne complete : " + ligneComplete);
        String lignes[] = ligneComplete.split("\\|", 500);
        String ligne;
        String[] elementsLigne;
        String[] typeElement;
        int nbHS = 0;
        for (int kk = 1; kk < lignes.length; kk++) {
            ligne = lignes[kk];
            System.out.println("ligne : " + ligne);
            elementsLigne = ligne.split(";", 10);
            typeElement = elementsLigne[0].split(">", 2);
            typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
            elementsLigne[0] = typeElement[1];
            System.out.println("type Element " + typeElement[0]);
            if ("Panoramique".equals(typeElement[0])) {

                for (int i = 0; i < elementsLigne.length; i++) {
                    elementsLigne[i] = elementsLigne[i].replace("]", "");
                    String[] valeur = elementsLigne[i].split(":", 2);
                    System.out.println("Type " + valeur[0] + " : " + valeur[1]);

                    switch (valeur[0]) {
                        case "fichier":
                            String nmFich = valeur[1];
                            File imgPano = new File(nmFich);
                            File imageRepert = new File(repertTemp + File.separator + "panos");

                            if (!imageRepert.exists()) {
                                imageRepert.mkdirs();
                            }
                            repertPanos = imageRepert.getAbsolutePath();
                            try {
                                copieFichierRepertoire(imgPano.getPath(), repertPanos);
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            affichePano(valeur[1]);
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
                        default:
                            break;
                    }
                }
                for (int jj = 0; jj < nbHS; jj++) {
                    kk++;
                    ligne = lignes[kk];
                    System.out.println("ligne : " + ligne);
                    elementsLigne = ligne.split(";", 10);
                    typeElement = elementsLigne[0].split(">", 2);
                    typeElement[0] = typeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                    elementsLigne[0] = typeElement[1];
                    System.out.println("type Element " + typeElement[0]);

                    HotSpot HS = new HotSpot();
                    for (int i = 0; i < elementsLigne.length; i++) {
                        elementsLigne[i] = elementsLigne[i].replace("]", "");
                        String[] valeur = elementsLigne[i].split(":", 2);
                        System.out.println("Type " + valeur[0] + " : " + valeur[1]);
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
            }
        }

        for (int ii = 0; ii < nombrePanoramiques; ii++) {
            Panoramique pano1 = panoramiquesProjet[ii];
            System.out.println(nombrePanoramiques + " pano n°" + ii + " fichier : " + pano1.getNomFichier());
        }
    }

    /**
     *
     */
    private void lisFichierConfig() {
//        MenuItem menuItem=new MenuItem("test");
//        menuItem.setOnAction(null);
//        derniersProjets.getItems().addAll(menuItem);
//        derniersProjets.setDisable(false);
    }

    /**
     *
     * @param emplacement
     * @param repertoire
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

    /**
     *
     * @param fichier
     * @param repertoire
     * @throws FileNotFoundException
     * @throws IOException
     */
    static private void copieFichierRepertoire(String fichier, String repertoire) throws FileNotFoundException, IOException {
        String fichier1 = fichier.substring(fichier.lastIndexOf(File.separator) + 1);
        InputStream in = new FileInputStream(fichier);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(repertoire + File.separator + fichier1));
        byte[] buf = new byte[256 * 1024];
        int n;
        while ((n = in.read(buf, 0, buf.length)) > 0) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
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
            System.out.println("liste des panos : " + liste);
            return liste;
        }
    }

    /**
     *
     */
    /**
     *
     */
    private void valideHS() {
        for (int i = 0; i < panoramiquesProjet[panoActuel].getNombreHotspots(); i++) {
            ComboBox cbx = (ComboBox) scene.lookup("#cbpano" + i);
            TextArea txtTexteHS = (TextArea) scene.lookup("#txttextehs" + i);
            panoramiquesProjet[panoActuel].getHotspot(i).setInfo(txtTexteHS.getText());
            System.out.print("choix : HS n°" + i + " => " + cbx.getValue() + ".xml");
            panoramiquesProjet[panoActuel].getHotspot(i).setFichierXML(cbx.getValue() + ".xml");
            CheckBox cbAnime = (CheckBox) scene.lookup("#anime" + i);
            if (cbAnime.isSelected()) {
                panoramiquesProjet[panoActuel].getHotspot(i).setAnime(true);
                System.out.println(" anime : true");
            } else {
                panoramiquesProjet[panoActuel].getHotspot(i).setAnime(false);
                System.out.println(" anime : false");
            }
        }

    }

    public Pane affichageHS(String lstPano, int numPano) {

        Pane panneauHotSpots = new Pane();
        panneauHotSpots.setTranslateY(10);
        VBox vb1 = new VBox(5);
        panneauHotSpots.getChildren().add(vb1);
        Label lbl;
        Label lblPoint;
        Label sep = new Label(" ");
        Label sep1 = new Label(" ");
        for (int o = 0; o < panoramiquesProjet[numPano].getNombreHotspots(); o++) {
            VBox vbPanneauHS = new VBox();
            Pane pannneauHS = new Pane(vbPanneauHS);
            panneauHotSpots.setId("HS" + o);
            String chLong1, chLat1;

            chLong1 = "Long : " + String.format("%.1f", panoramiquesProjet[numPano].getHotspot(o).getLongitude());
            chLat1 = "Lat : " + String.format("%.1f", panoramiquesProjet[numPano].getHotspot(o).getLatitude());
            lbl = new Label(chLong1 + "        " + chLat1);
            lbl.setPadding(new Insets(5, 10, 5, 5));
            lblPoint = new Label("Point n°" + o);
            lblPoint.setPadding(new Insets(5, 10, 5, 5));
            lblPoint.setStyle("-fx-background-color : #333;");
            lblPoint.setTextFill(Color.WHITE);
            Separator sp = new Separator(Orientation.HORIZONTAL);
            sp.setStyle("-fx-border-color : #777;");
            sp.setPrefWidth(300);
            pannneauHS.setStyle("-fx-border-color :#777;-fx-border-radius : 3px;-fx-background-color : #ccc;");

            pannneauHS.setPrefWidth(300);
            pannneauHS.setTranslateX(5);
            vbPanneauHS.getChildren().addAll(lblPoint, sp, lbl);
            if (lstPano != null) {
                Label lblLien = new Label("Panoramique de destination");
                ComboBox cbDestPano = new ComboBox();
                String[] liste = lstPano.split(";");
                cbDestPano.getItems().addAll(Arrays.asList(liste));
                cbDestPano.setTranslateX(60);
                cbDestPano.setId("cbpano" + o);
                String f1XML = panoramiquesProjet[numPano].getHotspot(o).getFichierXML();
                if (f1XML != null) {
                    cbDestPano.setValue(f1XML.split("\\.")[0]);
                }
                vbPanneauHS.getChildren().addAll(lblLien, cbDestPano, sep);
            }
            Label lblTexteHS = new Label("Texte du Hotspot");
            TextArea txtTexteHS = new TextArea();
            if (panoramiquesProjet[numPano].getHotspot(o).getInfo() != null) {
                txtTexteHS.setText(panoramiquesProjet[numPano].getHotspot(o).getInfo());
            }
            txtTexteHS.setId("txttextehs" + o);
            txtTexteHS.setPrefSize(200, 25);
            txtTexteHS.setMaxSize(200, 20);
            txtTexteHS.setTranslateX(60);
            CheckBox cbAnime = new CheckBox("HostSpot Animé");
            cbAnime.setId("anime" + o);
            if (panoramiquesProjet[numPano].getHotspot(o).isAnime()) {
                cbAnime.setSelected(true);
            }
            cbAnime.setPadding(new Insets(5));
            cbAnime.setTranslateX(60);
            vbPanneauHS.getChildren().addAll(lblTexteHS, txtTexteHS, cbAnime, sep1);
            vb1.getChildren().addAll(pannneauHS, sep);
        }
        Button btnValider = new Button("Valide HotSpots");
        btnValider.setTranslateX(200);
        btnValider.setTranslateY(5);
        btnValider.setPadding(new Insets(7));
        btnValider.setOnAction((ActionEvent e) -> {
            valideHS();
        });
        vb1.getChildren().addAll(btnValider, sep1);
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
    }

    /**
     *
     * @param i
     * @param longitude
     * @param latitude
     */
    private void afficheHS(int i, double longitude, double latitude) {
        double largeur = imagePanoramique.getFitWidth();
        double X = (longitude + 180.0d) * largeur / 360.0d;
        double Y = (90.0d - latitude) * largeur / 360.0d;
        Circle point = new Circle(X, Y, 5);
        point.setFill(Color.YELLOW);
        point.setStroke(Color.RED);
        point.setId("point" + i);
        point.setCursor(Cursor.DEFAULT);
        pano.getChildren().add(point);
        Tooltip.install(point, new Tooltip("point n° " + i));

        point.setOnMouseClicked((MouseEvent me1) -> {
            dejaSauve = false;
            String chPoint = point.getId();
            chPoint = chPoint.substring(5, chPoint.length());
            int numeroPoint = Integer.parseInt(chPoint);
            if (me1.isControlDown()) {
                Node pt = (Node) pano.lookup("#point" + chPoint);
                pano.getChildren().remove(pt);
            }
            for (int o = numeroPoint + 1; o < numPoints; o++) {
                Node pt = (Node) pano.lookup("#point" + Integer.toString(o));
                pt.setId("point" + Integer.toString(o - 1));
            }
            /**
             * on retire les anciennes indication de HS
             */
            retireAffichageHotSpots();
            numPoints--;
            panoramiquesProjet[panoActuel].removeHotspot(numeroPoint);
            ajouteAffichageHotspots();
            /**
             * On les crée les nouvelles
             */

            me1.consume();
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
    private void sauveFichiers() throws IOException {
        if (!dejaSauve) {
            projetSauve();
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
        pano.setOnMouseClicked((MouseEvent me) -> {
            if (!(me.isControlDown()) && estCharge) {
                valideHS();
                dejaSauve = false;
                double mouseX = me.getSceneX();
                double mouseY = me.getSceneY() - pano.getLayoutY() - 115;
                double longitude, latitude;
                double largeur = imagePanoramique.getFitWidth();
                String chLong, chLat;
                longitude = 360.0f * mouseX / largeur - 180;
                latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                Circle point = new Circle(mouseX, mouseY, 5);
                point.setFill(Color.YELLOW);
                point.setStroke(Color.RED);
                point.setId("point" + numPoints);
                point.setCursor(Cursor.DEFAULT);
                pano.getChildren().add(point);
                Tooltip.install(point, new Tooltip("point n°" + numPoints));
                HotSpot HS = new HotSpot();
                HS.setLongitude(longitude);
                HS.setLatitude(latitude);
                panoramiquesProjet[panoActuel].addHotspot(HS);
                retireAffichageHotSpots();
                Pane affHS1 = affichageHS(listePano(panoActuel), panoActuel);
                affHS1.setId("labels");
                outils.getChildren().add(affHS1);

                numPoints++;
                point.setOnMouseClicked((MouseEvent me1) -> {
                    if (me1.isControlDown()) {
                        valideHS();
                        dejaSauve = false;
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
                        ajouteAffichageHotspots();
                        /**
                         * On les crée les nouvelles
                         */
                    }
                    me1.consume();
                });
            }
        });
        /**
         *
         */
        pano.setOnMouseMoved((MouseEvent me) -> {
            if (estCharge) {
                double mouseX = me.getSceneX();
                double mouseY = me.getSceneY() - pano.getLayoutY() - 115;
                double longitude, latitude;
                double largeur = imagePanoramique.getFitWidth() * pano.getScaleX();
                longitude = 360.0f * mouseX / largeur - 180;
                latitude = 90.0d - 2.0f * mouseY / largeur * 180.0f;
                String chLong = "Long : " + String.format("%.1f", longitude);
                String chLat = "Lat : " + String.format("%.1f", latitude);
                lblLong.setText(chLong);
                lblLat.setText(chLat);
            }
        });
        /*
        
         */
        listeChoixPanoramique.valueProperty().addListener(new ChangeListenerImpl());

    }

    private class ChangeListenerImpl implements ChangeListener<String> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
            System.out.println("nouvelle valeur" + nouvelleValeur);
            if (nouvelleValeur != null) {
                if (panoCharge) {
                    panoCharge = false;
                    panoAffiche = nouvelleValeur;
                } else {
                    if (!(nouvelleValeur.equals(panoAffiche))) {
                        clickBtnValidePano();
                        valideHS();

                        panoAffiche = nouvelleValeur;
                        int numPanoChoisit = listeChoixPanoramique.getSelectionModel().getSelectedIndex();
                        System.out.println("nb : " + nombrePanoramiques + " =>Pano " + panoAffiche + "index : " + numPanoChoisit + " fichier :"
                                + panoramiquesProjet[numPanoChoisit].getNomFichier());
                        affichePanoChoisit(numPanoChoisit);
                    }
                }
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
            x = (int) (largeur / 2.0f + largeur / 360.0f * (float) i);
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
            ligne = new Line(0, y, largeur, y);
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
        retireAffichagePointsHotSpots();
        retireAffichageHotSpots();
        retireAffichageLigne();
        numPoints = 0;

        panoActuel = numPanochoisi;
        ajouteAffichageHotspots();
        ajouteAffichagePointsHotspots();
        ajouteAffichageLignes();
        afficheInfoPano();
    }

    private void afficheInfoPano() {
        System.out.println(panoramiquesProjet[panoActuel].getTitrePanoramique() + "affiche Info" + panoramiquesProjet[panoActuel].isAfficheInfo() + " Affiche titre : " + panoramiquesProjet[panoActuel].isAfficheTitre());
        TextArea txtTitrePano = (TextArea) scene.lookup("#txttitrepano");
        CheckBox chkAfficheInfo = (CheckBox) scene.lookup("#chkafficheinfo");
        CheckBox chkAfficheTitre = (CheckBox) scene.lookup("#chkaffichetitre");
        RadioButton radSphere = (RadioButton) scene.lookup("#radsphere");
        RadioButton radCube = (RadioButton) scene.lookup("#radcube");

        if (panoramiquesProjet[panoActuel].getTitrePanoramique() != null) {
            txtTitrePano.setText(panoramiquesProjet[panoActuel].getTitrePanoramique());
        }
        chkAfficheInfo.setSelected(panoramiquesProjet[panoActuel].isAfficheInfo());
        chkAfficheTitre.setSelected(panoramiquesProjet[panoActuel].isAfficheTitre());
        if (panoramiquesProjet[panoActuel].getTypePanoramique().equals(Panoramique.SPHERE)) {
            radSphere.setSelected(true);
            radCube.setSelected(false);
        } else {
            radCube.setSelected(true);
            radSphere.setSelected(false);
        }

    }

    /**
     *
     * @param fichierPano
     */
    @SuppressWarnings("empty-statement")
    private void affichePano(String fichierPano) {
        String nomPano = fichierPano.substring(fichierPano.lastIndexOf(File.separator) + 1, fichierPano.length());
        panoCharge = true;
        listeChoixPanoramique.getItems().add(nomPano);
        paneChoixPanoramique.setVisible(true);
        estCharge = true;
        Panoramique panoCree = new Panoramique();
        panoCree.setNomFichier(fichierPano);
        image2 = new Image("file:" + fichierPano, 0, 0, true, true);
        panoCree.setImagePanoramique(image2);
        panoCree.setLookAtX(0.0d);
        panoCree.setLookAtY(0.0d);
        panoCree.setTypePanoramique(Panoramique.SPHERE);
        panoCree.setAfficheInfo(true);
        panoCree.setAfficheTitre(true);
        imagePanoramique.setImage(panoCree.getImagePanoramique());
        retireAffichageLigne();
        retireAffichageHotSpots();
        retireAffichagePointsHotSpots();
        numPoints = 0;
        ajouteAffichageLignes();
        panoramiquesProjet[nombrePanoramiques] = panoCree;
        panoActuel = nombrePanoramiques;
        listeChoixPanoramique.setValue(listeChoixPanoramique.getItems().get(nombrePanoramiques));
        nombrePanoramiques++;

    }

    /**
     * 
     */
    private void transformationCube2Equi(){
        EquiCubeDialogController C2EController=new EquiCubeDialogController();
        try {
            C2EController.afficheFenetre(EquiCubeDialogController.CUBE2QUI);
        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    /**
     * 
     */
    private void transformationEqui2Cube(){
        EquiCubeDialogController E2CController=new EquiCubeDialogController();
        try {
            E2CController.afficheFenetre(EquiCubeDialogController.EQUI2CUBE);
        } catch (Exception ex) {
            Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
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
    private void creeMenu(Stage primaryStage, VBox racine, int taille) throws Exception {
        //Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("menuPrincipal.fxml"));
        VBox myPane = new VBox();
        myPane.setPrefHeight(80);
        myPane.setPrefWidth(3000);
        MenuBar menuPrincipal = new MenuBar();
        menuPrincipal.setMinHeight(21);
        menuPrincipal.setPrefHeight(29);
        menuPrincipal.setPrefWidth(3000);
        /*
         Menu projets
         */
        Menu menuProjet = new Menu("Projets");
        menuPrincipal.getMenus().add(menuProjet);
        nouveauProjet = new MenuItem("Nouveau Projet");
        nouveauProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuProjet.getItems().add(nouveauProjet);
        chargeProjet = new MenuItem("Ouvrir un Projet");
        chargeProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        menuProjet.getItems().add(chargeProjet);
        sauveProjet = new MenuItem("Enregistrer le Projet");
        sauveProjet.setDisable(true);
        sauveProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuProjet.getItems().add(sauveProjet);
        sauveSousProjet = new MenuItem("Enregistrer le Projet Sous");
        sauveSousProjet.setDisable(true);
        sauveSousProjet.setAccelerator(KeyCombination.keyCombination("Shift+Ctrl+S"));
        menuProjet.getItems().add(sauveSousProjet);
        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        menuProjet.getItems().add(sep1);
        visiteGenere = new MenuItem("Générer la visite");
        visiteGenere.setDisable(true);
        visiteGenere.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        menuProjet.getItems().add(visiteGenere);
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        menuProjet.getItems().add(sep2);
        fermerProjet = new MenuItem("Quitter l'application");
        fermerProjet.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuProjet.getItems().add(fermerProjet);
        /*
         Menu panoramiques
         */
        menuPanoramique = new Menu("Panoramiques");
        menuPanoramique.setDisable(true);
        menuPrincipal.getMenus().add(menuPanoramique);
        ajouterPano = new MenuItem("Ajouter un panoramique ...");
        ajouterPano.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        menuPanoramique.getItems().add(ajouterPano);
        /*
         Menu transformations 
         */
        menuTransformation = new Menu("Transformations");
        menuPrincipal.getMenus().add(menuTransformation);

        cube2EquiTransformation = new MenuItem("Faces de Cube -> Equi");
        menuTransformation.getItems().add(cube2EquiTransformation);
        equi2CubeTransformation = new MenuItem("Equi -> Faces de Cube");
        menuTransformation.getItems().add(equi2CubeTransformation);

        /*
         Menu Aide
         */
        Menu menuAide = new Menu("Aide");
        menuPrincipal.getMenus().add(menuAide);
        aide = new MenuItem("Aide");
        aide.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        menuAide.getItems().add(aide);
        SeparatorMenuItem sep3 = new SeparatorMenuItem();
        menuAide.getItems().add(sep3);
        aPropos = new MenuItem("A Propos ...");
        menuAide.getItems().add(aPropos);
        /*
         barre de boutons 
         */
        String couleurBouton = "-fx-background-color:#f7f7f7;";
        HBox barreBouton = new HBox();
        barreBouton.setPrefHeight(50);
        barreBouton.setPrefWidth(3000);
        /*
         Bouton nouveau Projet
         */
        ScrollPane SPBtnNouvprojet = new ScrollPane();
        SPBtnNouvprojet.setStyle(couleurBouton);
        SPBtnNouvprojet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnNouvprojet.setPrefHeight(35);
        SPBtnNouvprojet.setPrefWidth(35);

        HBox.setMargin(SPBtnNouvprojet, new Insets(5, 15, 0, 15));
        imgNouveauProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/nouveauProjet.png"));
        SPBtnNouvprojet.setContent(imgNouveauProjet);
        SPBtnNouvprojet.setTooltip(new Tooltip("Crée un nouveau projet"));
        barreBouton.getChildren().add(SPBtnNouvprojet);
        /*
         Bouton ouvrir Projet
         */
        ScrollPane SPBtnOuvrirProjet = new ScrollPane();
        SPBtnOuvrirProjet.setStyle(couleurBouton);
        SPBtnOuvrirProjet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnOuvrirProjet.setPrefHeight(35);
        SPBtnOuvrirProjet.setPrefWidth(35);

        HBox.setMargin(SPBtnOuvrirProjet, new Insets(5, 15, 0, 0));
        imgChargeProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ouvrirProjet.png"));
        SPBtnOuvrirProjet.setContent(imgChargeProjet);
        SPBtnOuvrirProjet.setTooltip(new Tooltip("Ouvrir un projet"));
        barreBouton.getChildren().add(SPBtnOuvrirProjet);

        /*
         Bouton sauve Projet
         */
        ScrollPane SPBtnSauveProjet = new ScrollPane();
        SPBtnSauveProjet.setStyle(couleurBouton);
        SPBtnSauveProjet.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnSauveProjet.setPrefHeight(35);
        SPBtnSauveProjet.setPrefWidth(35);

        HBox.setMargin(SPBtnSauveProjet, new Insets(5, 15, 0, 0));
        imgSauveProjet = new ImageView(new Image("file:" + repertAppli + File.separator + "images/sauveProjet.png"));
        SPBtnSauveProjet.setContent(imgSauveProjet);
        SPBtnSauveProjet.setTooltip(new Tooltip("Sauve le projet"));
        barreBouton.getChildren().add(SPBtnSauveProjet);
        Separator sepImages = new Separator(Orientation.VERTICAL);
        sepImages.prefHeight(200);
        barreBouton.getChildren().add(sepImages);
        imgSauveProjet.setDisable(true);
        imgSauveProjet.setOpacity(0.3);
        /*
         Bouton Ajoute Panoramique
         */
        ScrollPane SPBtnAjoutePano = new ScrollPane();
        SPBtnAjoutePano.setStyle(couleurBouton);
        SPBtnAjoutePano.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnAjoutePano.setPrefHeight(35);
        SPBtnAjoutePano.setPrefWidth(35);

        HBox.setMargin(SPBtnAjoutePano, new Insets(5, 15, 0, 15));
        imgAjouterPano = new ImageView(new Image("file:" + repertAppli + File.separator + "images/ajoutePanoramique.png"));
        SPBtnAjoutePano.setContent(imgAjouterPano);
        SPBtnAjoutePano.setTooltip(new Tooltip("Ajout de panoramiques"));
        barreBouton.getChildren().add(SPBtnAjoutePano);
        imgAjouterPano.setDisable(true);
        imgAjouterPano.setOpacity(0.3);


        /*
         Bouton Génère
         */
        ScrollPane SPBtnGenereVisite = new ScrollPane();
        SPBtnGenereVisite.setStyle(couleurBouton);
        SPBtnGenereVisite.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnGenereVisite.setPrefHeight(35);
        SPBtnGenereVisite.setPrefWidth(70);

        HBox.setMargin(SPBtnGenereVisite, new Insets(5, 15, 0, 0));
        imgVisiteGenere = new ImageView(new Image("file:" + repertAppli + File.separator + "images/genereVisite.png"));
        SPBtnGenereVisite.setContent(imgVisiteGenere);
        SPBtnGenereVisite.setTooltip(new Tooltip("Générer la visite"));
        barreBouton.getChildren().add(SPBtnGenereVisite);
        imgVisiteGenere.setDisable(true);
        imgVisiteGenere.setOpacity(0.3);
        Separator sepImages1 = new Separator(Orientation.VERTICAL);
        sepImages1.prefHeight(200);
        barreBouton.getChildren().add(sepImages1);

        /*
         Bouton faces de cube -> equi
         */
        ScrollPane SPBtnCube2Equi = new ScrollPane();
        SPBtnCube2Equi.setStyle(couleurBouton);
        SPBtnCube2Equi.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnCube2Equi.setPrefHeight(35);
        SPBtnCube2Equi.setPrefWidth(109);

        HBox.setMargin(SPBtnCube2Equi, new Insets(5, 25, 0, 250));
        imgCube2Equi = new ImageView(new Image("file:" + repertAppli + File.separator + "images/cube2equi.png"));
        SPBtnCube2Equi.setContent(imgCube2Equi);
        SPBtnCube2Equi.setTooltip(new Tooltip("Faces de cube ==> Equirectangulaire"));
        barreBouton.getChildren().add(SPBtnCube2Equi);
        /*
         Bouton equi -> faces de  Cube
         */
        ScrollPane SPBtnEqui2Cube = new ScrollPane();
        SPBtnEqui2Cube.setStyle(couleurBouton);
        SPBtnEqui2Cube.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SPBtnEqui2Cube.setPrefHeight(35);
        SPBtnEqui2Cube.setPrefWidth(109);

        HBox.setMargin(SPBtnEqui2Cube, new Insets(5, 15, 0, 0));
        imgEqui2Cube = new ImageView(new Image("file:" + repertAppli + File.separator + "images/equi2cube.png"));
        SPBtnEqui2Cube.setContent(imgEqui2Cube);
        SPBtnEqui2Cube.setTooltip(new Tooltip("Equirectangulaire ==> Faces de cube"));
        barreBouton.getChildren().add(SPBtnEqui2Cube);

        myPane.getChildren().addAll(menuPrincipal, barreBouton);
        racine.getChildren().add(myPane);
        File repertConfig = new File(repertAppli + File.separator + "configPV");
        txtRepertConfig = repertConfig.getAbsolutePath();
        if (!repertConfig.exists()) {
            repertConfig.mkdirs();
        } else {
            lisFichierConfig();
        }
        nouveauProjet.setOnAction((ActionEvent e) -> {
            projetsNouveau();
        });
        chargeProjet.setOnAction((ActionEvent e) -> {
            try {
                projetCharge();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        sauveProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauve();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        sauveSousProjet.setOnAction((ActionEvent e) -> {
            try {
                projetSauveSous();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        visiteGenere.setOnAction((ActionEvent e) -> {
            try {
                genereVisite();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fermerProjet.setOnAction((ActionEvent e) -> {
            projetsFermer();
        });
        ajouterPano.setOnAction((ActionEvent e) -> {
            panoramiquesAjouter();
        });
        aPropos.setOnAction((ActionEvent e) -> {
            aideAPropos();
        });

        equi2CubeTransformation.setOnAction((ActionEvent e) -> {
            transformationEqui2Cube();
        });

       cube2EquiTransformation.setOnAction((ActionEvent e) -> {
            transformationCube2Equi();
        });

        imgNouveauProjet.setOnMouseClicked((MouseEvent t) -> {
            projetsNouveau();
        });
        imgChargeProjet.setOnMouseClicked((MouseEvent t) -> {
            try {
                projetCharge();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imgSauveProjet.setOnMouseClicked((MouseEvent t) -> {
            try {
                projetSauve();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imgAjouterPano.setOnMouseClicked((MouseEvent t) -> {
            panoramiquesAjouter();
        });
        imgVisiteGenere.setOnMouseClicked((MouseEvent t) -> {
            try {
                genereVisite();
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imgEqui2Cube.setOnMouseClicked((MouseEvent t) -> {
            transformationEqui2Cube();
        });
        imgCube2Equi.setOnMouseClicked((MouseEvent t) -> {
            transformationCube2Equi();
        });

    }

    /**
     *
     * @param primaryStage
     * @param width
     * @param height
     * @throws Exception
     */
    private void creeEnvironnement(Stage primaryStage, int width, int height) throws Exception {
        popUp = new popUpHandler();
        /**
         * Création des éléments constitutifs de l'écran
         */
        VBox root = new VBox();
        creeMenu(primaryStage, root, width);
        TabPane tabPaneEnvironnement = new TabPane();
        tabPaneEnvironnement.setTranslateZ(5);
        tabPaneEnvironnement.setMinHeight(height - 140);
        tabPaneEnvironnement.setMaxHeight(height - 140);
        ScrollPane lastFiles = new ScrollPane();
        lastFiles.setPrefSize(310, 200);
        VBox lstDerniersFichiers = new VBox();
        Label lblDerniersFichiers = new Label("liste des derniers Projets");
        ListView listDerniersFichiers = new ListView();
        listDerniersFichiers.setPrefSize(300, 140);
        listDerniersFichiers.setId("listderniersfichiers");
        valideChargeDerniersFichiers = new Button("Charge");
        valideChargeDerniersFichiers.setDisable(true);
        valideChargeDerniersFichiers.setPadding(new Insets(7));
        valideChargeDerniersFichiers.setTranslateX(200);
        valideChargeDerniersFichiers.setId("validechargederniersfichiers");
        valideChargeDerniersFichiers.setOnAction((ActionEvent e) -> {
            ListView lv = (ListView) scene.lookup("#listderniersfichiers");
            String nomFich;
            nomFich = (String) lv.getSelectionModel().getSelectedItem();
            System.out.println(nomFich);
            //File fichProjet1=new File(nomFich);
        });
        listDerniersFichiers.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        if (new_val != null) {
                            Button valide = (Button) scene.lookup("#validechargederniersfichiers");
                            valide.setDisable(false);
                            System.out.println(new_val);
                        }
                    }
                });

        lstDerniersFichiers.getChildren().addAll(lblDerniersFichiers, listDerniersFichiers, valideChargeDerniersFichiers);
        lastFiles.setContent(lstDerniersFichiers);
        Pane barreStatus = new Pane();
        barreStatus.setPrefSize(width + 20, 30);
        barreStatus.setTranslateY(25);
        barreStatus.setStyle("-fx-background-color:#ccc;-fx-border-color:#aaa");
        Tab tabVisite = new Tab();
        Tab tabInterface = new Tab();
        HBox hbEnvironnement = new HBox();
        Pane visualiseur = new Pane();
        TextArea txtTitrePano;
        RadioButton radSphere;
        RadioButton radCube;
        CheckBox chkAfficheTitre;
        CheckBox chkAfficheInfo;
        Button btnValidePano;

        tabPaneEnvironnement.getTabs().addAll(tabVisite, tabInterface);
        //tabPaneEnvironnement.setTranslateY(80);
        tabPaneEnvironnement.setSide(Side.TOP);
        tabVisite.setText("Gestion des panoramiques");
        tabVisite.setClosable(false);
        tabInterface.setText("Création de l'nterface");
        tabInterface.setClosable(false);
        tabVisite.setContent(hbEnvironnement);
        visualiseur.setPrefSize(500, 1000);
        tabInterface.setContent(visualiseur);
        double largeur;
        String labelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-padding : 5px;  -fx-border : 1px solid #777;-fx-width : 100px;-fx-margin : 5px; ";

        scene = new Scene(root, width, height, Color.rgb(221, 221, 221));
        panneauOutils = new ScrollPane();
        panneauOutils.setStyle("-fx-background-color : #ccc;");
        outils = new VBox();
        paneChoixPanoramique = new VBox();
        paneChoixPanoramique.setId("choixPanoramique");
        lblChoixPanoramique = new Label("Choix du panoramique");
        listeChoixPanoramique.setVisibleRowCount(10);
        paneChoixPanoramique.getChildren().addAll(lblChoixPanoramique, listeChoixPanoramique);
        /*
         à modifier pour afficher le panneau des derniers fichiers;        
         */
        //outils.getChildren().addAll(lastFiles, paneChoixPanoramique);

        outils.getChildren().addAll(paneChoixPanoramique);

        paneChoixPanoramique.setVisible(false);
        /*
         Création du panneau d'info du panoramique
         */
        VBox vbInfoPano = new VBox(5);
        vbInfoPano.setPrefSize(310, 270);
        Pane myAnchorPane = new Pane(vbInfoPano);
        Separator sepInfo = new Separator(Orientation.HORIZONTAL);
        sepInfo.setTranslateY(5);
        Separator sepInfo1 = new Separator(Orientation.HORIZONTAL);
        sepInfo1.setTranslateY(10);
        Separator sepInfo2 = new Separator(Orientation.HORIZONTAL);
        sepInfo2.setTranslateY(20);
        Label lblTitrePano = new Label("Titre du panoramique");
        lblTitrePano.setPadding(new Insets(30, 5, 5, 5));
        txtTitrePano = new TextArea();
        txtTitrePano.setId("txttitrepano");
        txtTitrePano.setPrefSize(200, 25);
        Label lblTypePano = new Label("Type de panoramique");
        lblTypePano.setPadding(new Insets(10, 5, 5, 5));
        radSphere = new RadioButton(Panoramique.SPHERE);
        radSphere.setSelected(true);
        radSphere.setId("radsphere");
        radCube = new RadioButton(Panoramique.CUBE);
        radCube.setSelected(false);
        radCube.setId("radcube");
        radCube.setOnMouseClicked((MouseEvent me) -> {
            RadioButton radSphere1 = (RadioButton) scene.lookup("#radsphere");
            radSphere1.setSelected(false);
        });
        radSphere.setOnMouseClicked((MouseEvent me) -> {
            RadioButton radCube1 = (RadioButton) scene.lookup("#radcube");
            radCube1.setSelected(false);
        });
        HBox hbTypePano = new HBox(radSphere, radCube);
        hbTypePano.setTranslateX(60);
        chkAfficheTitre = new CheckBox("Affiche Titre");
        chkAfficheTitre.setId("chkaffichetitre");
        chkAfficheInfo = new CheckBox("Affiche écran d'information");
        chkAfficheInfo.setId("chkafficheinfo");
        chkAfficheInfo.setTranslateX(60);
        chkAfficheTitre.setTranslateX(60);
        chkAfficheTitre.setTranslateY(10);
        chkAfficheInfo.setTranslateY(15);

        btnValidePano = new Button("Valide Infos");
        btnValidePano.setTranslateY(25);
        btnValidePano.setTranslateX(200);
        btnValidePano.setPadding(new Insets(7));
        btnValidePano.setOnAction((ActionEvent e) -> {
            clickBtnValidePano();
        });

        vbInfoPano.getChildren().addAll(sepInfo, lblTitrePano, txtTitrePano, sepInfo1, lblTypePano, hbTypePano, chkAfficheTitre, chkAfficheInfo, sepInfo2, btnValidePano);

        paneChoixPanoramique.getChildren().add(myAnchorPane);

        vuePanoramique = new ScrollPane();

        coordonnees = new HBox();
        pano = new Pane();
        panneau2 = new VBox();
        lblLong = new Label("");
        lblLat = new Label("");
        imagePanoramique = new ImageView();

        primaryStage.setScene(scene);

        /**
         *
         */
        vuePanoramique.setPrefSize(width - 330, height - 130);
        vuePanoramique.setMaxSize(width - 330, height - 130);
        vuePanoramique.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vuePanoramique.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        vuePanoramique.setTranslateY(5);
        //vuePanoramique.setStyle("-fx-background-color : #c00;");
        /**
         *
         */
        panneauOutils.setContent(outils);
        panneauOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        panneauOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        panneauOutils.setPrefSize(320, height - 130);
        panneauOutils.setMaxSize(320, height - 130);
        panneauOutils.setTranslateY(5);
        panneauOutils.setStyle("-fx-background-color : #ccc;");
        /**
         *
         */
        pano.setCursor(Cursor.CROSSHAIR);
        outils.setPrefWidth(310);
        outils.setStyle("-fx-background-color : #ccc;");
        outils.minHeight(height - 130);
        lblLong.setStyle(labelStyle);
        lblLat.setStyle(labelStyle);
        lblLong.setPrefSize(100, 15);
        lblLat.setPrefSize(100, 15);
        lblLat.setTranslateX(50);
        panneau2.setStyle("-fx-background-color : #ddd;");
        panneau2.setPrefSize(width - 330, height - 140);

        imagePanoramique.setCache(true);
        if (largeurMax < 1200) {
            largeur = largeurMax;
        } else {
            largeur = 1200;
        }
        imagePanoramique.setFitWidth(largeur);
        imagePanoramique.setFitHeight(largeur / 2.0d);
        pano.getChildren().add(imagePanoramique);
        pano.setPrefSize(imagePanoramique.getFitWidth(), imagePanoramique.getFitHeight());
        pano.setMaxSize(imagePanoramique.getFitWidth(), imagePanoramique.getFitHeight());
        coordonnees.getChildren().setAll(lblLong, lblLat);
        vuePanoramique.setContent(panneau2);
        hbEnvironnement.getChildren().setAll(vuePanoramique, panneauOutils);
        root.getChildren().addAll(tabPaneEnvironnement, barreStatus);
        panneau2.getChildren().setAll(coordonnees, pano);
        primaryStage.show();
        popUp.affichePopup();
        ToolTipManager ttManager;
        ttManager = ToolTipManager.sharedInstance();
        ttManager.setInitialDelay(10);
        ttManager.setReshowDelay(10);
        ttManager.setDismissDelay(10);
    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        File rep = new File("");
        repertAppli = rep.getAbsolutePath();
        repertoireProjet=repertAppli;
        stPrincipal = primaryStage;
        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int) tailleEcran.getHeight() - 20;
        int largeur = (int) tailleEcran.getWidth() - 20;
        largeurMax = tailleEcran.getWidth() - 320.0d;
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
        currentDir = rep.getAbsolutePath();
        installeEvenements();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Action reponse = null;
            Localization.setLocale(new Locale("fr", "FR"));
            if (!dejaSauve) {
                reponse = Dialogs.create()
                        .owner(null)
                        .title("Quitte l'éditeur")
                        .masthead("ATTENTION !!! vous n'avez pas sauvegardé votre projet")
                        .message("Voulez vous le sauver ?")
                        .showConfirm();
            }
            if (reponse == Dialog.Actions.YES) {
                try {
                    projetSauve();
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Réponse" + reponse);
            if ((reponse == Dialog.Actions.YES) || (reponse == Dialog.Actions.NO) || (reponse == null)) {
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
        launch(args);
    }

}
