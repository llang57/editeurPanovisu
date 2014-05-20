/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import impl.org.controlsfx.i18n.Localization;
import java.util.Locale;

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
    static private Panoramique[] panoramiquesProjet=new Panoramique[50];
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
    static private String repertAppli;
    static private String repertTemp;
    static private String repertPanos;
    static private String repertoireProjet;
    final static private ComboBox listeChoixPanoramique = new ComboBox();
    static private Label lblChoixPanoramique;
    static private boolean panoCharge = false;
    static private String panoAffiche = "";
    static private boolean dejaSauve = true;
    static private Stage stPrincipal;

    @FXML
    private Menu menuPanoramique;

    @FXML
    private MenuItem aPropos;
    @FXML
    private ImageView imgNouveauProjet;
    @FXML
    private ImageView imgSauveProjet;
    @FXML
    private ImageView imgChargeProjet;
    @FXML
    private ImageView imgAjouterPano;

    @FXML
    private MenuItem nouveauProjet;
    @FXML
    private MenuItem sauveProjet;
    @FXML
    private MenuItem ajouterPano;

    @FXML
    private MenuItem fermerProjet;
    @FXML
    private TextArea txtTitrePano;
    @FXML
    private RadioButton radSphere;
    @FXML
    private RadioButton radCube;
    @FXML
    private CheckBox chkAfficheTitre;
    @FXML
    private CheckBox chkAfficheInfo;
    @FXML
    private Button btnValidePano;

    @FXML
    private MenuItem sauveSousProjet;
    @FXML
    private MenuItem chargeProjet;

    /**
     *
     * @param event
     */
    @FXML
    private void panoramiquesAjouter() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPEG Files (*.jpg)", "*.jpg");

        File repert = new File(currentDir + File.separator);
        fileChooser.setInitialDirectory(repert);
        fileChooser.getExtensionFilters().add(extFilter);

        file = fileChooser.showOpenDialog(null);
        if (file != null) {
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
            installeEvenements();
        }
    }

    /**
     *
     */
    @FXML
    private void clickBtnValidePano() {
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
    @FXML
    private void clickRadSphere() {
        radCube.setSelected(false);
    }

    /**
     *
     */
    @FXML
    private void clickRadCube() {
        radSphere.setSelected(false);
    }

    /**
     *
     */
    @FXML
    private void projetCharge() throws IOException {
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        Action reponse = null;
        Localization.setLocale(Locale.FRENCH);
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
                paneChoixPanoramique.setVisible(false);
                numPoints = 0;
                imagePanoramique.setImage(null);
                listeChoixPanoramique.getItems().clear();
                FileReader fr;
                try {
                    fr = new FileReader(fichProjet);
                    BufferedReader br = new BufferedReader(fr);
                    String texte = "";
                    String ligneTexte;
                    while ((ligneTexte = br.readLine()) != null) {
                        texte += ligneTexte;
                    }
                    br.close();
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
    @FXML
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
            repertoireProjet = file.getParent();
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
                        + ";titre:'" + panoramiquesProjet[i].getTitrePanoramique() + "'"
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
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenuFichier);
            bw.close();
        }
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    private void projetSauveSous() throws IOException {
        if (!repertSauveChoisi) {
            repertoireProjet = currentDir;
        }
        FileChooser repertChoix = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichier panoVisu (*.pvu)", "*.pvu");
        repertChoix.getExtensionFilters().add(extFilter);
        File repert = new File(repertoireProjet + File.separator);
        repertChoix.setInitialDirectory(repert);
        File fichProjet = repertChoix.showSaveDialog(null);
        if (fichProjet != null) {
            repertoireProjet = file.getParent();
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
                        + ";titre:'" + panoramiquesProjet[i].getTitrePanoramique() + "'"
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
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenuFichier);
            bw.close();
        }

    }

    /**
     *
     */
    @FXML
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
    @FXML
    private void projetsFermer() {
        Action reponse = null;
        Localization.setLocale(Locale.FRENCH);
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
    @FXML
    private void projetsNouveau() {
        Action reponse = null;
        Localization.setLocale(Locale.FRENCH);
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
                            affichePano(valeur[1]);
                            break;
                        case "titre":
                            panoramiquesProjet[panoActuel].setTitrePanoramique(valeur[1]);
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
        InputStream in = new FileInputStream(fichier);;
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

    private void retireAffichagePointsHotSpots() {
        for (int i = 0; i < numPoints; i++) {
            Node pt = (Node) pano.lookup("#point" + i);
            pano.getChildren().remove(pt);
        }
    }

    /**
     *
     */
    private void ajouteAffichageHotspots() {
        Pane lbl = panoramiquesProjet[panoActuel].getAffichageHS();
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
                dejaSauve = false;
                double mouseX = me.getSceneX();
                double mouseY = me.getSceneY() - pano.getLayoutY() - 80;
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
                Pane affHS1 = panoramiquesProjet[panoActuel].getAffichageHS();
                affHS1.setId("labels");
                outils.getChildren().add(affHS1);

                numPoints++;
                point.setOnMouseClicked((MouseEvent me1) -> {
                    if (me1.isControlDown()) {
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
                double mouseY = me.getSceneY() - pano.getLayoutY() - 80;
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
        listeChoixPanoramique.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                System.out.println("nouvelle valeur" + nouvelleValeur);
                if (nouvelleValeur != null) {
                    if (panoCharge) {
                        panoCharge = false;
                        panoAffiche = nouvelleValeur;
                    } else {
                        if (!(nouvelleValeur.equals(panoAffiche))) {
                            panoAffiche = nouvelleValeur;
                            int numPanoChoisit = listeChoixPanoramique.getSelectionModel().getSelectedIndex();
                            System.out.println("nb : " + nombrePanoramiques + " =>Pano " + panoAffiche + "index : " + numPanoChoisit + " fichier :"
                                    + panoramiquesProjet[numPanoChoisit].getNomFichier());
                            affichePanoChoisit(numPanoChoisit);
                        }
                    }
                }

            }
        });

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
            y = (int) (hauteur / 2.0f + hauteur / 180.0f * (float) i);;
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
    private void creeMenu(Stage primaryStage, Group racine, int taille) throws Exception {
        Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("menuPrincipal.fxml"));
        racine.getChildren().add(myPane);

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
        Group root = new Group();
        TabPane tabPaneEnvironnement = new TabPane();
        Tab tabVisite = new Tab();
        Tab tabInterface = new Tab();
        HBox hbEnvironnement = new HBox();
        Pane visualiseur = new Pane();

        tabPaneEnvironnement.getTabs().addAll(tabVisite, tabInterface);
        tabPaneEnvironnement.setTranslateY(45);
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
        outils = new VBox();
        paneChoixPanoramique = new VBox();
        paneChoixPanoramique.setId("choixPanoramique");
        lblChoixPanoramique = new Label("Choix du panoramique");
        listeChoixPanoramique.setVisibleRowCount(10);
        paneChoixPanoramique.getChildren().addAll(lblChoixPanoramique, listeChoixPanoramique);
        outils.getChildren().addAll(paneChoixPanoramique);

        paneChoixPanoramique.setVisible(false);
        Pane myAnchorPane = (Pane) FXMLLoader.load(getClass().getResource("panoInfo.fxml"));
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
        panneauOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        panneauOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        panneauOutils.setPrefSize(320, height - 130);
        panneauOutils.setMaxSize(320, height - 130);
        panneauOutils.setTranslateY(5);
        panneauOutils.setStyle("-fx-background-color : #ccc;");
        /**
         *
         */
        pano.setCursor(Cursor.CROSSHAIR);
        outils.setPrefSize(310, height - 140);
        outils.setStyle("-fx-background-color : #ccc;");
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
        root.getChildren().add(tabPaneEnvironnement);
        panneau2.getChildren().setAll(coordonnees, pano);
        creeMenu(primaryStage, root, width);
        primaryStage.show();
        popUp.affichePopup();
        ToolTipManager ttManager;
        ttManager = null;
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
        stPrincipal = primaryStage;
        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setMaximized(true);
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int) tailleEcran.getHeight() - 20;
        int largeur = (int) tailleEcran.getWidth() - 20;
        largeurMax = tailleEcran.getWidth() - 320.0d;
        creeEnvironnement(primaryStage, largeur, hauteur);
        File rep = new File("");
        repertAppli = rep.getAbsolutePath();

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
        currentDir = "c:";
        installeEvenements();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            try {
                sauveFichiers();

            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            deleteDirectory(repertTemp);
            File ftemp = new File(repertTemp);
            ftemp.delete();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
