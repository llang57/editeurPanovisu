/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getiPanoActuel;
import java.awt.Desktop;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import netscape.javascript.JSObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditeurHTML {

    protected transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété à écouter
     * @param listener Écouteur à ajouter
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Retire un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Déclenche un événement de changement de propriété
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur
     * @param newValue Nouvelle valeur
     */
    public void firePropertyChange(String propertyName, BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue != null && newValue != null && oldValue.compareTo(newValue) == 0) {
            return;
        }
        changeSupport.firePropertyChange(new PropertyChangeEvent(this, propertyName,
                oldValue, newValue));

    }

    private ResourceBundle rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
    private boolean bValide = false;
    private boolean bAnnule = false;

    private HotspotHTML hsHTML;
    private ToggleGroup tgPositionHTML;
    private ToggleButton tgbGauche;
    private ToggleButton tgbCentre;
    private ToggleButton tgbDroite;
    private Slider slOpacite = new Slider(0, 1, 0.7);

    private final File fileRep = new File("");
    private final String strAppPath = fileRep.getAbsolutePath();
    @SuppressWarnings("unused")
    private String strNomRepertHTML = strAppPath + "/pagesHTML";
    private Stage stEditeurHTML;
    private String strImages = "";
    private HTMLEditor heEditeurHTML;
    private Node nodeBarreIconesSuperieure;
    private Node nodeEditeurHTML;
    private ToolBar tbBarreIconesSuperieure;
    private WebView wvEditeurHTML;
    private WebEngine engEditeurHTML;
    private Button btnValide;
    private Button btnAnnule;
    private Button btnAjouteImage;
    private Button btnAjouteLien;
    private AnchorPane apDialog;
    private AnchorPane apEditeur;
    private AnchorPane apPrincipale;
    private String strNomFichierImage = "";
    @SuppressWarnings("unused")
    private boolean bDejaSauve = true;
    private ColorPicker cpCouleurHTML;
    private ColorPicker cpCouleurFond;
    private ColorPicker cpCouleurTexte;
    private String strThemePageHTML = "Défaut (simple)"; // Thème CSS de la page HTML
    public String strCouleurFond = "#FFFFFF";
    public String strCouleurTexte = "#000000";

    private final String jsCodeInsertHtml
            = "function insertHtmlAtCursor(html) {\n"
            + " var range, node;\n"
            + " if (window.getSelection && window.getSelection().getRangeAt) {\n"
            + " range = window.getSelection().getRangeAt(0);\n"
            + " node = range.createContextualFragment(html);\n"
            + " range.insertNode(node);\n"
            + " } else if (document.selection && document.selection.createRange) {\n"
            + " document.selection.createRange().pasteHTML(html);\n"
            + " }\n"
            + "};\n"
            + "insertHtmlAtCursor('####html####')";

    private static final String strSelectText
            = "(function getSelectionText() {\n"
            + " var text = \"\";\n"
            + " if (window.getSelection) {\n"
            + " text = window.getSelection().toString();\n"
            + " } else if (document.selection && document.selection.type != \"Control\") {\n"
            + " text = document.selection.createRange().text;\n"
            + " }\n"
            + " "
            + " return text;\n"
            + "})()";

    /**
     *
     * @param i
     * @return
     */
    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    /**
     *
     * @param strEntree
     * @param bEscapeSingleQuote
     * @param escapeForwardSlash
     * @return
     */
    private static String escapeJavaStyleString(String strEntree,
            boolean bEscapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder sbSortie = new StringBuilder("");
        if (strEntree == null) {
            return null;
        }
        int sz;
        sz = strEntree.length();
        for (int i = 0; i < sz; i++) {
            char chEntree = strEntree.charAt(i);
            if (chEntree > 0xfff) {
                sbSortie.append("\\u").append(hex(chEntree));
            } else if (chEntree > 0xff) {
                sbSortie.append("\\u0").append(hex(chEntree));
            } else if (chEntree > 0x7f) {
                sbSortie.append("\\u00").append(hex(chEntree));
            } else if (chEntree < 32) {
                switch (chEntree) {
                    case '\b':
                        sbSortie.append('\\');
                        sbSortie.append('b');
                        break;
                    case '\n':
                        sbSortie.append('\\');
                        sbSortie.append('n');
                        break;
                    case '\t':
                        sbSortie.append('\\');
                        sbSortie.append('t');
                        break;
                    case '\f':
                        sbSortie.append('\\');
                        sbSortie.append('f');
                        break;
                    case '\r':
                        sbSortie.append('\\');
                        sbSortie.append('r');
                        break;
                    default:
                        if (chEntree > 0xf) {
                            sbSortie.append("\\u00").append(hex(chEntree));
                        } else {
                            sbSortie.append("\\u000").append(hex(chEntree));
                        }
                        break;
                }
            } else {
                switch (chEntree) {
                    case '\'':
                        if (bEscapeSingleQuote) {
                            sbSortie.append('\\');
                        }
                        sbSortie.append('\'');
                        break;
                    case '"':
                        sbSortie.append('\\');
                        sbSortie.append('"');
                        break;
                    case '\\':
                        sbSortie.append('\\');
                        sbSortie.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            sbSortie.append('\\');
                        }
                        sbSortie.append('/');
                        break;
                    default:
                        sbSortie.append(chEntree);
                        break;
                }
            }
        }
        return sbSortie.toString();
    }

    static public void copieFichierRepertoire(String strFichier, String strRepertoire) throws FileNotFoundException, IOException {
        File fileFrom = new File(strFichier);
        File fileTo = new File(strRepertoire + File.separator + strFichier.substring(strFichier.lastIndexOf(File.separator) + 1));
        Files.copy(fileFrom.toPath(), fileTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Génère le CSS selon le thème choisi pour la page HTML
     */
    private String getCSSPourTheme(String theme, String couleurFond, String couleurTexte) {
        String css = "";
        
        switch (theme) {
            case "Bootstrap 5":
                css = "            @import url('https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css');\n"
                    + "            body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; padding: 20px; }\n";
                break;
                
            case "Tailwind CSS":
                css = "            @import url('https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css');\n"
                    + "            body { font-family: 'Inter', sans-serif; padding: 20px; }\n";
                break;
                
            // ===== THÈMES ATLANTAFX =====
            case "Primer Light (AtlantaFX)":
                css = "            body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; background-color: #ffffff; color: #24292f; padding: 20px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { color: #1f2328; font-weight: 600; border-bottom: 1px solid #d0d7de; padding-bottom: 0.3em; }\n"
                    + "            a { color: #0969da; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            case "Primer Dark (AtlantaFX)":
                css = "            body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; background-color: #0d1117; color: #c9d1d9; padding: 20px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { color: #f0f6fc; font-weight: 600; border-bottom: 1px solid #21262d; padding-bottom: 0.3em; }\n"
                    + "            a { color: #58a6ff; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            case "Nord Light (AtlantaFX)":
                css = "            body { font-family: 'Inter', 'Roboto', sans-serif; background-color: #eceff4; color: #2e3440; padding: 20px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { color: #5e81ac; font-weight: 600; margin: 1.5em 0 0.5em; }\n"
                    + "            a { color: #5e81ac; text-decoration: none; font-weight: 500; }\n"
                    + "            a:hover { color: #81a1c1; }\n";
                break;
                
            case "Nord Dark (AtlantaFX)":
                css = "            body { font-family: 'Inter', 'Roboto', sans-serif; background-color: #2e3440; color: #d8dee9; padding: 20px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { color: #88c0d0; font-weight: 600; margin: 1.5em 0 0.5em; }\n"
                    + "            a { color: #81a1c1; text-decoration: none; font-weight: 500; }\n"
                    + "            a:hover { color: #5e81ac; }\n"
                    + "            code { background-color: #3b4252; padding: 2px 6px; border-radius: 3px; }\n";
                break;
                
            case "Cupertino Light (AtlantaFX)":
                css = "            body { font-family: -apple-system, BlinkMacSystemFont, 'SF Pro Text', 'Helvetica Neue', sans-serif; background-color: #ffffff; color: #000000; padding: 20px; line-height: 1.5; }\n"
                    + "            h1, h2, h3 { color: #000000; font-weight: 600; letter-spacing: -0.02em; }\n"
                    + "            h1 { font-size: 2em; }\n"
                    + "            a { color: #007aff; text-decoration: none; }\n"
                    + "            a:hover { opacity: 0.8; }\n";
                break;
                
            case "Cupertino Dark (AtlantaFX)":
                css = "            body { font-family: -apple-system, BlinkMacSystemFont, 'SF Pro Text', 'Helvetica Neue', sans-serif; background-color: #1c1c1e; color: #ffffff; padding: 20px; line-height: 1.5; }\n"
                    + "            h1, h2, h3 { color: #ffffff; font-weight: 600; letter-spacing: -0.02em; }\n"
                    + "            h1 { font-size: 2em; }\n"
                    + "            a { color: #0a84ff; text-decoration: none; }\n"
                    + "            a:hover { opacity: 0.8; }\n";
                break;
                
            case "Dracula (AtlantaFX)":
                css = "            body { font-family: 'Fira Code', 'Consolas', 'Monaco', monospace; background-color: #282a36; color: #f8f8f2; padding: 20px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { color: #bd93f9; font-weight: 600; }\n"
                    + "            h1 { font-size: 2em; }\n"
                    + "            h2 { font-size: 1.5em; }\n"
                    + "            h3 { font-size: 1.2em; }\n"
                    + "            a { color: #8be9fd; text-decoration: none; font-weight: 500; }\n"
                    + "            a:hover { color: #50fa7b; }\n"
                    + "            code { background-color: #44475a; color: #50fa7b; padding: 2px 6px; border-radius: 3px; }\n";
                break;
                
            // ===== THÈMES MATERIALFX =====
            case "Material Light (MaterialFX)":
                css = "            body { font-family: 'Roboto', Arial, sans-serif; background-color: #fafafa; color: #212121; padding: 16px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { font-weight: 400; margin: 24px 0 16px; }\n"
                    + "            h1 { font-size: 2.5em; letter-spacing: -0.02em; color: #1976d2; }\n"
                    + "            h2 { font-size: 2em; color: #1976d2; }\n"
                    + "            h3 { font-size: 1.5em; color: #1976d2; }\n"
                    + "            p { margin: 0 0 16px; }\n"
                    + "            a { color: #1976d2; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            case "Material Dark (MaterialFX)":
                css = "            body { font-family: 'Roboto', Arial, sans-serif; background-color: #121212; color: #ffffff; padding: 16px; line-height: 1.6; }\n"
                    + "            h1, h2, h3 { font-weight: 400; margin: 24px 0 16px; }\n"
                    + "            h1 { font-size: 2.5em; letter-spacing: -0.02em; color: #90caf9; }\n"
                    + "            h2 { font-size: 2em; color: #90caf9; }\n"
                    + "            h3 { font-size: 1.5em; color: #90caf9; }\n"
                    + "            p { margin: 0 0 16px; }\n"
                    + "            a { color: #90caf9; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            // ===== THÈMES FLATLAF =====
            case "FlatLaf Light":
            case "FlatLaf IntelliJ":
                css = "            body { font-family: 'Inter', 'Segoe UI', sans-serif; background-color: #f2f2f2; color: #000000; padding: 20px; line-height: 1.5; }\n"
                    + "            h1, h2, h3 { color: #000000; font-weight: 500; }\n"
                    + "            a { color: #2470b3; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            case "FlatLaf Dark":
            case "FlatLaf Darcula":
                css = "            body { font-family: 'Inter', 'Segoe UI', sans-serif; background-color: #3c3f41; color: #bbbbbb; padding: 20px; line-height: 1.5; }\n"
                    + "            h1, h2, h3 { color: #ffffff; font-weight: 500; }\n"
                    + "            a { color: #589df6; text-decoration: none; }\n"
                    + "            a:hover { text-decoration: underline; }\n";
                break;
                
            default: // "Défaut (simple)"
                css = "            body{background-color : " + couleurFond + ";}\n"
                    + "            body{color : " + couleurTexte + ";}\n"
                    + "            h1,h2,h3,h4,h5{\n"
                    + "                font-size : 1em;\n"
                    + "                font-weight: bold;\n"
                    + "                color : #444;\n"
                    + "            }\n"
                    + "            h1 { font-size: 1.3em; }\n"
                    + "            h2 { font-size: 1.2em; }\n"
                    + "            h3 { font-size: 1.1em; }\n"
                    + "            a {\n"
                    + "               text-decoration : none;\n"
                    + "               font-weight : bold;\n"
                    + "               color : inherit;\n"
                    + "            }\n";
                break;
        }
        
        return css;
    }

    /**
     * Définit le template HTML de base pour l'éditeur
     * 
     * <p>Retourne une chaîne de template HTML vide qui peut être personnalisée.
     * Cette méthode sert de point d'extension pour définir des templates prédéfinis.</p>
     * 
     * @return Template HTML (actuellement vide)
     */
    public String setTemplate() {
        String strTemplate = "";
        return strTemplate;
    }

    /**
     * Affiche l'éditeur HTML dans une fenêtre modale
     * 
     * <p>Cette méthode crée et affiche une fenêtre d'édition HTML avec un éditeur riche (HTMLEditor).
     * La fenêtre inclut des contrôles pour :</p>
     * <ul>
     *   <li>L'édition du contenu HTML (texte enrichi, images, liens)</li>
     *   <li>Le choix de la position de la fenêtre HTML dans la visite</li>
     *   <li>La configuration des couleurs de fond et de texte</li>
     *   <li>Le réglage de l'opacité</li>
     * </ul>
     * 
     * @param largeur Largeur souhaitée de la fenêtre d'édition
     * @param hauteur Hauteur souhaitée de la fenêtre d'édition
     */
    @SuppressWarnings("unused")
    public void affiche(Number largeur, Number hauteur) {
        double diffHauteur = 340; // Augmenté pour tenir compte des nouvelles zones
        bDejaSauve = true;
        int iLargeur = (int) largeur;
        int iHauteur = (int) hauteur;
        stEditeurHTML = new Stage(StageStyle.UTILITY);
        stEditeurHTML.initModality(Modality.APPLICATION_MODAL);
        stEditeurHTML.setResizable(true);
        heEditeurHTML = new HTMLEditor();
        
        // Charger la couleur de texte sauvegardée ou utiliser la couleur par défaut
        String couleurTexteSauvegardee = getHsHTML().getStrCouleurTexteHTML();
        if (couleurTexteSauvegardee != null && !couleurTexteSauvegardee.isEmpty()) {
            strCouleurTexte = couleurTexteSauvegardee;
        }
        
        Color coulFond = Color.valueOf(strCouleurFond);
        double lum = coulFond.getBrightness();
        @SuppressWarnings("unused")
        String strCoul = "#000000";
        if (lum > 0.5) {
            strCoul = "#ffffff";
        }
        if (getHsHTML().getStrTexteHTML().equals("")) {
            String cssTheme = getCSSPourTheme(strThemePageHTML, strCouleurFond, strCouleurTexte);
            heEditeurHTML.setHtmlText("<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                    + "        <style>\n"
                    + "            *{\n"
                    + "                font-family: Verdana,Arial,sans-serif;                \n"
                    + "            }           \n"
                    + cssTheme
                    + "\n"
                    + "        </style>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "    </body>\n"
                    + "</html>\n"
                    + "");
        } else {
            // Ajouter les ancres aux images avant de charger le HTML
            String htmlAvecAncres = ajouterAncresAuxImages(getHsHTML().getStrTexteHTML());
            heEditeurHTML.setHtmlText(htmlAvecAncres);
        }
        Node nodeBarreIconesTop = heEditeurHTML.lookup(".top-toolbar");
        nodeBarreIconesSuperieure = heEditeurHTML.lookup(".bottom-toolbar");
        nodeEditeurHTML = heEditeurHTML.lookup(".web-view");
        strImages = strAppPath + File.separator + "images";

        // ========== NOUVELLE STRUCTURE AVEC LAYOUTS MODERNES ==========
        
        // VBox principale avec toutes les sections
        VBox vboxPrincipale = new VBox(10);
        vboxPrincipale.setPadding(new Insets(10));
        vboxPrincipale.setPrefSize(iLargeur, iHauteur);
        
        // === SECTION 1: Type de contenu (Lien externe / HTML interne) ===
        VBox vboxTypeContenu = new VBox(10);
        vboxTypeContenu.setPadding(new Insets(10));
        vboxTypeContenu.setStyle("-fx-background-color: -fx-background; -fx-border-color: -fx-box-border; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        ToggleGroup tgLien = new ToggleGroup();
        ToggleButton tgbLienExterieur = new ToggleButton(rbLocalisation.getString("editeurHTML.lienExterne"));
        tgbLienExterieur.setSelected(getHsHTML().isbLienExterieur());
        tgbLienExterieur.setToggleGroup(tgLien);
        tgbLienExterieur.setPrefWidth(150);

        TextField tfLienExterieur = new TextField(getHsHTML().getStrURLExterieure());
        tfLienExterieur.setPromptText("http://monsite.extension");
        tfLienExterieur.setPrefWidth(400);
        Tooltip tpLienExterieur = new Tooltip(rbLocalisation.getString("editeurHTML.urlComplete"));
        tfLienExterieur.setTooltip(tpLienExterieur);
        
        HBox hboxLienExterieur = new HBox(10, tgbLienExterieur, tfLienExterieur);
        hboxLienExterieur.setAlignment(Pos.CENTER_LEFT);
        
        ToggleButton tgbEditeurInterne = new ToggleButton(rbLocalisation.getString("editeurHTML.htmlInterne"));
        tgbEditeurInterne.setSelected(!getHsHTML().isbLienExterieur());
        tgbEditeurInterne.setToggleGroup(tgLien);
        tgbEditeurInterne.setPrefWidth(150);
        
        vboxTypeContenu.getChildren().addAll(hboxLienExterieur, tgbEditeurInterne);
        
        // === SECTION 2: Configuration de l'éditeur ===
        GridPane gridConfig = new GridPane();
        gridConfig.setHgap(10);
        gridConfig.setVgap(10);
        gridConfig.setPadding(new Insets(10));
        gridConfig.setStyle("-fx-background-color: -fx-background; -fx-border-color: -fx-box-border; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Ligne 1: Position
        Label lblPositionEditeur = new Label(rbLocalisation.getString("editeurHTML.position"));
        tgPositionHTML = new ToggleGroup();
        tgbGauche = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlGauche.png", 16, 16, true, true)));
        tgbCentre = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlCentre.png", 16, 16, true, true)));
        tgbDroite = new ToggleButton("", new ImageView(new Image("file:" + strImages + File.separator + "htmlDroite.png", 16, 16, true, true)));
        tgbGauche.setSelected(getHsHTML().getStrPositionHTML().equals("left"));
        tgbCentre.setSelected(getHsHTML().getStrPositionHTML().equals("center"));
        tgbDroite.setSelected(getHsHTML().getStrPositionHTML().equals("right"));
        tgbGauche.setUserData("left");
        tgbCentre.setUserData("center");
        tgbDroite.setUserData("right");
        tgbGauche.setToggleGroup(tgPositionHTML);
        tgbCentre.setToggleGroup(tgPositionHTML);
        tgbDroite.setToggleGroup(tgPositionHTML);
        HBox hboxPosition = new HBox(5, tgbGauche, tgbCentre, tgbDroite);
        
        // Ligne 1 (suite): Largeur
        Label lblLargeurEditeur = new Label(rbLocalisation.getString("editeurHTML.largeur"));
        Slider slLargeurEditeur = new Slider(400, iLargeur - 30, getHsHTML().getLargeurHTML());
        slLargeurEditeur.setPrefWidth(200);
        
        gridConfig.add(lblPositionEditeur, 0, 0);
        gridConfig.add(hboxPosition, 1, 0);
        gridConfig.add(lblLargeurEditeur, 2, 0);
        gridConfig.add(slLargeurEditeur, 3, 0);
        
        // Ligne 2: Couleur fond et opacité
        Label lblCouleur = new Label(rbLocalisation.getString("editeurHTML.couleur"));
        cpCouleurHTML = new ColorPicker(Color.web(getHsHTML().getStrCouleurHTML()));
        Label lblOpacite = new Label(rbLocalisation.getString("editeurHTML.opacite"));
        slOpacite = new Slider(0, 1, getHsHTML().getOpaciteHTML());
        slOpacite.setPrefWidth(200);
        
        gridConfig.add(lblCouleur, 0, 1);
        gridConfig.add(cpCouleurHTML, 1, 1);
        gridConfig.add(lblOpacite, 2, 1);
        gridConfig.add(slOpacite, 3, 1);
        
        // Ligne 3: Couleur fond et couleur texte
        Label lblCouleurFond = new Label(rbLocalisation.getString("editeurHTML.couleurFond"));
        cpCouleurFond = new ColorPicker(Color.web(strCouleurFond));
        Label lblCouleurTexte = new Label(rbLocalisation.getString("editeurHTML.couleurTexte"));
        cpCouleurTexte = new ColorPicker(Color.web(strCouleurTexte));
        
        gridConfig.add(lblCouleurFond, 0, 2);
        gridConfig.add(cpCouleurFond, 1, 2);
        gridConfig.add(lblCouleurTexte, 2, 2);
        gridConfig.add(cpCouleurTexte, 3, 2);
        
        // Ligne 4: Thème de la page HTML
        Label lblThemePage = new Label("Thème de la page :");
        ComboBox<String> cbThemePage = new ComboBox<>();
        cbThemePage.getItems().addAll(
            "Défaut (simple)",
            "Primer Light (AtlantaFX)",
            "Primer Dark (AtlantaFX)",
            "Nord Light (AtlantaFX)",
            "Nord Dark (AtlantaFX)",
            "Cupertino Light (AtlantaFX)",
            "Cupertino Dark (AtlantaFX)",
            "Dracula (AtlantaFX)",
            "Material Light (MaterialFX)",
            "Material Dark (MaterialFX)",
            "FlatLaf Light",
            "FlatLaf Dark",
            "FlatLaf IntelliJ",
            "FlatLaf Darcula",
            "Bootstrap 5",
            "Tailwind CSS"
        );
        // Charger le thème sauvegardé ou utiliser le thème par défaut
        String themeSauvegarde = getHsHTML().getStrThemeHTML();
        if (themeSauvegarde != null && !themeSauvegarde.isEmpty()) {
            strThemePageHTML = themeSauvegarde;
            cbThemePage.setValue(themeSauvegarde);
        } else {
            strThemePageHTML = "Défaut (simple)";
            cbThemePage.setValue("Défaut (simple)");
        }
        cbThemePage.setPrefWidth(200);
        
        gridConfig.add(lblThemePage, 0, 3);
        gridConfig.add(cbThemePage, 1, 3, 2, 1); // Span 2 colonnes pour avoir plus d'espace
        
        // === SECTION 3: Zone éditeur avec fond panoramique ===
        heEditeurHTML.setPrefHeight(iHauteur - diffHauteur);
        heEditeurHTML.setMinHeight(iHauteur - diffHauteur);
        heEditeurHTML.setMaxHeight(iHauteur - diffHauteur);
        heEditeurHTML.setPrefWidth(getHsHTML().getLargeurHTML());
        heEditeurHTML.setMaxWidth(getHsHTML().getLargeurHTML());
        heEditeurHTML.setMinWidth(getHsHTML().getLargeurHTML());
        
        // StackPane pour superposer fond + éditeur (fond gris moyen neutre)
        StackPane stackEditeur = new StackPane();
        stackEditeur.setStyle("-fx-background-color: #606060;"); // Gris moyen neutre
        ImageView ivFond = new ImageView(getPanoramiquesProjet()[getiPanoActuel()].getImgPanoramique());
        ivFond.setPreserveRatio(false);
        ivFond.fitWidthProperty().bind(stackEditeur.widthProperty());
        ivFond.fitHeightProperty().bind(stackEditeur.heightProperty());
        
        // Conteneur pour l'éditeur avec couleur de fond
        AnchorPane apEditeur2 = new AnchorPane(heEditeurHTML);
        int iRouge = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
        int iBleu = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
        int iVert = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
        String strCouleur = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getHsHTML().getOpaciteHTML() + ")";
        apEditeur2.setStyle("-fx-background-color : " + strCouleur + ";");
        apEditeur2.setPadding(new Insets(10));
        
        // Aligner l'éditeur selon la position choisie
        switch (getHsHTML().getStrPositionHTML()) {
            case "left":
                StackPane.setAlignment(apEditeur2, Pos.CENTER_LEFT);
                break;
            case "center":
                StackPane.setAlignment(apEditeur2, Pos.CENTER);
                break;
            case "right":
                StackPane.setAlignment(apEditeur2, Pos.CENTER_RIGHT);
                break;
        }
        
        stackEditeur.getChildren().addAll(ivFond, apEditeur2);
        VBox.setVgrow(stackEditeur, Priority.ALWAYS);
        
        if (getHsHTML().isbLienExterieur()) {
            heEditeurHTML.setDisable(true);
        }
        
        // === SECTION 4: Boutons de validation ===
        btnValide = new Button(rbLocalisation.getString("editeurHTML.valide"), new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        btnAnnule = new Button(rbLocalisation.getString("editeurHTML.annule"), new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValide.setPrefWidth(120);
        btnAnnule.setPrefWidth(120);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox hboxBoutons = new HBox(10, spacer, btnAnnule, btnValide);
        hboxBoutons.setPadding(new Insets(10));
        hboxBoutons.setAlignment(Pos.CENTER_RIGHT);
        
        // Assemblage final
        vboxPrincipale.getChildren().addAll(vboxTypeContenu, gridConfig, stackEditeur, hboxBoutons);
        
        // Dialog overlay (pour fenêtres de configuration)
        apDialog = new AnchorPane();
        apDialog.setVisible(false);
        
        StackPane stackPrincipale = new StackPane(vboxPrincipale, apDialog);
        
        Scene scPrincipale = new Scene(stackPrincipale, iLargeur, iHauteur);
        
        // Appliquer le style CSS clair pour le HTMLEditor
        try {
            String cssFile = new File(strAppPath + "/css/htmleditor-light.css").toURI().toString();
            scPrincipale.getStylesheets().add(cssFile);
            System.out.println("Style CSS HTMLEditor applique: " + cssFile);
        } catch (Exception e) {
            System.err.println("Erreur chargement CSS HTMLEditor: " + e.getMessage());
        }
        
        stEditeurHTML.setScene(scPrincipale);
        stEditeurHTML.show();
        
        // Garder les références pour compatibilité
        apEditeur = new AnchorPane(); // Dummy pour compatibilité
        apPrincipale = new AnchorPane(); // Dummy pour compatibilité
        
        // Styler la toolbar du haut (formatage de texte)
        if (nodeBarreIconesTop instanceof ToolBar) {
            ToolBar tbTop = (ToolBar) nodeBarreIconesTop;
            tbTop.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-border-color: #d0d0d0;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-padding: 8;" +
                "-fx-spacing: 4;"
            );
            
            // Styliser tous les éléments de la barre du haut
            tbTop.getItems().forEach(item -> {
                if (item instanceof Button) {
                    Button btn = (Button) item;
                    btn.setStyle(
                        "-fx-background-color: #e8e8e8;" +
                        "-fx-text-fill: #1a1a1a;" +
                        "-fx-border-color: #c0c0c0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;" +
                        "-fx-padding: 5 10 5 10;" +
                        "-fx-font-weight: normal;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 2, 0, 0, 1);"
                    );
                    
                    // Éclaircir l'icône si elle est sombre
                    if (btn.getGraphic() instanceof ImageView) {
                        ImageView iv = (ImageView) btn.getGraphic();
                        iv.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.5), 0, 0, 0, 0);");
                    }
                } else if (item instanceof ComboBox) {
                    item.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-text-fill: #1a1a1a;" +
                        "-fx-border-color: #b0b0b0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;"
                    );
                } else if (item instanceof ColorPicker) {
                    item.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-border-color: #b0b0b0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;"
                    );
                } else if (item instanceof Separator) {
                    item.setStyle("-fx-background-color: #d0d0d0;");
                }
            });
        }
        
        @SuppressWarnings("unused")
        int i = 0;
        if (nodeBarreIconesSuperieure instanceof ToolBar && nodeEditeurHTML instanceof WebView) {
            tbBarreIconesSuperieure = (ToolBar) nodeBarreIconesSuperieure;
            
            // === STYLISATION MODERNE DE LA BARRE D'ICÔNES ===
            // Style moderne avec fond blanc éclatant et bordures nettes
            tbBarreIconesSuperieure.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-border-color: #d0d0d0;" +
                "-fx-border-width: 0 0 2 0;" +
                "-fx-padding: 8;" +
                "-fx-spacing: 4;"
            );
            
            // Styliser tous les éléments de la barre pour une excellente visibilité
            tbBarreIconesSuperieure.getItems().forEach(item -> {
                if (item instanceof Button) {
                    Button btn = (Button) item;
                    btn.setStyle(
                        "-fx-background-color: #e8e8e8;" +
                        "-fx-text-fill: #1a1a1a;" +
                        "-fx-border-color: #c0c0c0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;" +
                        "-fx-padding: 5 10 5 10;" +
                        "-fx-font-weight: normal;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 2, 0, 0, 1);"
                    );
                    
                    // Éclaircir l'icône si elle est sombre (pour mieux voir sur fond clair)
                    if (btn.getGraphic() instanceof ImageView) {
                        ImageView iv = (ImageView) btn.getGraphic();
                        iv.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.5), 0, 0, 0, 0);");
                    }
                    
                    // Effet au survol
                    btn.setOnMouseEntered(e -> {
                        btn.setStyle(
                            "-fx-background-color: #d0d0d0;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-border-color: #a0a0a0;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 3;" +
                            "-fx-background-radius: 3;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-font-weight: normal;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);"
                        );
                    });
                    btn.setOnMouseExited(e -> {
                        btn.setStyle(
                            "-fx-background-color: #e8e8e8;" +
                            "-fx-text-fill: #1a1a1a;" +
                            "-fx-border-color: #c0c0c0;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 3;" +
                            "-fx-background-radius: 3;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-font-weight: normal;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 2, 0, 0, 1);"
                        );
                    });
                } else if (item instanceof ComboBox) {
                    item.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-text-fill: #1a1a1a;" +
                        "-fx-border-color: #b0b0b0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;"
                    );
                } else if (item instanceof ColorPicker) {
                    item.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-border-color: #b0b0b0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;"
                    );
                } else if (item instanceof Separator) {
                    item.setStyle("-fx-background-color: #d0d0d0;");
                }
            });
            
            wvEditeurHTML = (WebView) nodeEditeurHTML;
            wvEditeurHTML.setMaxHeight(heEditeurHTML.getPrefHeight() - 70);
            wvEditeurHTML.setMinHeight(heEditeurHTML.getPrefHeight() - 70);
            wvEditeurHTML.setPrefHeight(heEditeurHTML.getPrefHeight() - 70);
            
            // Forcer le fond blanc sur la WebView pour éviter la première ligne sombre
            wvEditeurHTML.setStyle("-fx-background-color: white;");
            
            engEditeurHTML = wvEditeurHTML.getEngine();

            // Ajouter gestionnaire de clic sur la WebView pour détecter les clics sur les ancres
            wvEditeurHTML.setOnMouseClicked(event -> {
                System.out.println("Clic detecte sur WebView: bouton=" + event.getButton() + ", x=" + event.getX() + ", y=" + event.getY());
                
                // Récupérer l'élément cliqué via JavaScript
                try {
                    String jsGetElement = 
                        "var x = " + event.getX() + ";" +
                        "var y = " + event.getY() + ";" +
                        "var elem = document.elementFromPoint(x, y);" +
                        "var result = {" +
                        "  tagName: elem ? elem.tagName : null," +
                        "  className: elem ? elem.className : null," +
                        "  imageId: elem ? elem.getAttribute('data-image-id') : null" +
                        "};" +
                        "JSON.stringify(result);";
                    
                    String resultJson = (String) engEditeurHTML.executeScript(jsGetElement);
                    System.out.println("Element clique: " + resultJson);
                    
                    // Parser le résultat JSON simplement
                    if (resultJson != null && resultJson.contains("\"className\":\"image-anchor\"")) {
                        // Extraire l'imageId
                        int idStart = resultJson.indexOf("\"imageId\":\"") + 11;
                        int idEnd = resultJson.indexOf("\"", idStart);
                        if (idStart > 11 && idEnd > idStart) {
                            String imageId = resultJson.substring(idStart, idEnd);
                            System.out.println("Clic sur ancre detecte! ImageId: " + imageId);
                            supprimerImageDepuisJS(imageId);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erreur detection element clique: " + e.getMessage());
                }
            });

            // Ajouter les ancres d'édition aux images existantes après le chargement du contenu
            engEditeurHTML.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    // Utiliser un Timeline pour ajouter un délai avant d'ajouter les listeners
                    @SuppressWarnings("unused")
                    javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(javafx.util.Duration.millis(500), e -> {
                            ajouterListenerAncresImages();
                        })
                    );
                    timeline.play();
                }
            });

            heEditeurHTML.setOnKeyPressed((evenement) -> {
                getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
                bDejaSauve = false;
            });
            engEditeurHTML.locationProperty().addListener((ov, ancValeur, nouvValeur) -> {
                try {
                    Desktop d = Desktop.getDesktop();
                    URI uriAdresse = new URI(nouvValeur);
                    d.browse(uriAdresse);
                    engEditeurHTML.getLoadWorker().cancel();
                    Platform.runLater(() -> {
                        heEditeurHTML.setHtmlText(getHsHTML().getStrTexteHTML());

                    });
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(EditeurHTML.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tbBarreIconesSuperieure.getItems().add(new Separator(Orientation.VERTICAL));
            ImageView ivAjouteImage = new ImageView(new Image("file:" + strImages + File.separator + "ajouteImage.png", 22, 16, true, true));
            btnAjouteImage = new Button("", ivAjouteImage);
            Tooltip tpAjouteImage = new Tooltip(rbLocalisation.getString("editeurHTML.ajouteImage"));
            btnAjouteImage.setTooltip(tpAjouteImage);
            tbBarreIconesSuperieure.getItems().add(btnAjouteImage);

            ImageView ivAjouteLien = new ImageView(new Image("file:" + strImages + File.separator + "lien.png", 16, 16, true, true));
            btnAjouteLien = new Button("", ivAjouteLien);
            Tooltip tpAjouteLien = new Tooltip(rbLocalisation.getString("editeurHTML.ajouteLien"));
            btnAjouteLien.setTooltip(tpAjouteLien);
            tbBarreIconesSuperieure.getItems().add(btnAjouteLien);

            // Bouton pour visualiser le code HTML
            Button btnVoirCodeHTML = new Button("Code HTML");
            btnVoirCodeHTML.setStyle(
                "-fx-background-color: #2196F3; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 5px 15px; " +
                "-fx-border-radius: 3px; " +
                "-fx-background-radius: 3px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 1);"
            );
            btnVoirCodeHTML.setOnMouseEntered(e -> 
                btnVoirCodeHTML.setStyle(
                    "-fx-background-color: #1976D2; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 5px 15px; " +
                    "-fx-border-radius: 3px; " +
                    "-fx-background-radius: 3px; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 1);"
                )
            );
            btnVoirCodeHTML.setOnMouseExited(e -> 
                btnVoirCodeHTML.setStyle(
                    "-fx-background-color: #2196F3; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 5px 15px; " +
                    "-fx-border-radius: 3px; " +
                    "-fx-background-radius: 3px; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0, 0, 1);"
                )
            );
            btnVoirCodeHTML.setOnAction(e -> afficherCodeHTML());
            Tooltip tpVoirCodeHTML = new Tooltip("Voir le code HTML source");
            btnVoirCodeHTML.setTooltip(tpVoirCodeHTML);
            tbBarreIconesSuperieure.getItems().add(btnVoirCodeHTML);

        }

        heEditeurHTML.requestFocus();
        tfLienExterieur.textProperty().addListener((ov, ancValeur, nouvValeur) -> {
            getHsHTML().setStrURLExterieure(nouvValeur);
        });

        stEditeurHTML.setOnCloseRequest((event) -> {
            quitteEditeurHTML(stEditeurHTML);
            event.consume();
        });
        cpCouleurHTML.setOnAction((evt) -> {
            String strCouleurHTML = cpCouleurHTML.getValue().toString().substring(2, 8);
            getHsHTML().setStrCouleurHTML(strCouleurHTML);
            int iRouge1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
            int iBleu1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
            int iVert1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
            String strCouleur1 = "rgba(" + iRouge1 + "," + iVert1 + "," + iBleu1 + "," + getHsHTML().getOpaciteHTML() + ")";
            apEditeur2.setStyle("-fx-background-color : " + strCouleur1 + ";");
        });

        cpCouleurFond.setOnAction((evt) -> {
            String strCouleurHTML = "#" + cpCouleurFond.getValue().toString().substring(2, 8);
            String strTextHTML = heEditeurHTML.getHtmlText();
            String strTrouve = "body\\{background-color : \\#......\\;\\}";
            if (strTextHTML.contains("body{background-color :")) {
                strTextHTML = strTextHTML.replaceAll(strTrouve, "body{background-color : " + strCouleurHTML + ";}");
            } else {
                strTextHTML = strTextHTML.replaceAll("</style>", "body{background-color : " + strCouleurHTML + ";}\n</style>");
            }
            heEditeurHTML.setHtmlText(strTextHTML);
            strCouleurFond = strCouleurHTML;
        });
        cpCouleurTexte.setOnAction((evt) -> {
            String strCouleurHTML = "#" + cpCouleurTexte.getValue().toString().substring(2, 8);
            String strTextHTML = heEditeurHTML.getHtmlText();
            String strTrouve = "body\\{color : \\#......\\;\\}";
            if (strTextHTML.contains("body{color :")) {
                strTextHTML = strTextHTML.replaceAll(strTrouve, "body{color : " + strCouleurHTML + ";}");
            } else {
                strTextHTML = strTextHTML.replaceAll("</style>", "body{color : " + strCouleurHTML + ";}\n</style>");
            }
            heEditeurHTML.setHtmlText(strTextHTML);
            strCouleurTexte = strCouleurHTML;
        });

        slOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                getHsHTML().setOpaciteHTML((double) newValue);
                int iRouge1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getRed() * 255.d);
                int iBleu1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getBlue() * 255.d);
                int iVert1 = (int) (Color.valueOf(getHsHTML().getStrCouleurHTML()).getGreen() * 255.d);
                String strCouleur1 = "rgba(" + iRouge1 + "," + iVert1 + "," + iBleu1 + "," + getHsHTML().getOpaciteHTML() + ")";
                apEditeur2.setStyle("-fx-background-color : " + strCouleur1 + ";");

            }
        });
        
        // Listener pour le changement de thème de la page HTML
        cbThemePage.setOnAction((evt) -> {
            strThemePageHTML = cbThemePage.getValue();
            // Sauvegarder le thème dans l'objet HotspotHTML
            getHsHTML().setStrThemeHTML(strThemePageHTML);
            getHsHTML().setStrCouleurTexteHTML(strCouleurTexte);
            
            // Récupérer le contenu actuel du body
            String currentHTML = heEditeurHTML.getHtmlText();
            
            // Extraire le contenu du body
            String bodyContent = "";
            int bodyStart = currentHTML.indexOf("<body");
            if (bodyStart != -1) {
                int bodyTagEnd = currentHTML.indexOf(">", bodyStart);
                int bodyEnd = currentHTML.indexOf("</body>", bodyTagEnd);
                if (bodyTagEnd != -1 && bodyEnd != -1) {
                    bodyContent = currentHTML.substring(bodyTagEnd + 1, bodyEnd).trim();
                }
            }
            
            // Générer le nouveau CSS
            String newCSS = getCSSPourTheme(strThemePageHTML, strCouleurFond, strCouleurTexte);
            
            // Reconstruire le HTML complet avec le nouveau style
            String newHTML = 
                "<html dir=\"ltr\">\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <style>\n" +
                "            *{\n" +
                "                font-family: Verdana,Arial,sans-serif;                \n" +
                "            }           \n" +
                newCSS +
                "\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body contenteditable=\"true\">\n" +
                bodyContent + "\n" +
                "    </body>\n" +
                "</html>\n";
            
            // Recharger le HTMLEditor avec le nouveau HTML
            heEditeurHTML.setHtmlText(newHTML);
        });
        
        tgbLienExterieur.setOnAction((evt) -> {
            tgbLienExterieur.setSelected(true);
            getHsHTML().setbLienExterieur(true);
        });
        tgbEditeurInterne.setOnAction((evt) -> {
            tgbEditeurInterne.setSelected(true);
            getHsHTML().setbLienExterieur(false);
        });

        tgPositionHTML.selectedToggleProperty()
                .addListener((ov, old_toggle, new_toggle) -> {
                    if (new_toggle != null) {
                        getHsHTML().setStrPositionHTML(tgPositionHTML.getSelectedToggle().getUserData().toString());
                        switch (getHsHTML().getStrPositionHTML()) {
                            case "left":
                                heEditeurHTML.setLayoutX(10);
                                break;
                            case "center":
                                heEditeurHTML.setLayoutX((stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth()) / 2);
                                break;
                            case "right":
                                heEditeurHTML.setLayoutX(stEditeurHTML.getWidth() - heEditeurHTML.getPrefWidth() - 35);
                                break;
                        }

                    }
                });

        tgLien.selectedToggleProperty()
                .addListener((ov, old_toggle, new_toggle) -> {
                    if (tgbLienExterieur.isSelected()) {
                        tfLienExterieur.setDisable(false);
                        heEditeurHTML.setDisable(true);
                        getHsHTML().setbLienExterieur(true);
                    } else {
                        tfLienExterieur.setDisable(true);
                        heEditeurHTML.setDisable(false);
                        getHsHTML().setbLienExterieur(false);
                    }
                }
                );

        btnAnnule.setOnAction((evenement) -> {
            quitteEditeurHTML(stEditeurHTML);
        }
        );
        btnValide.setOnAction((evenement) -> {
            // Récupérer le HTML et nettoyer les ancres d'édition et les attributs data-image-id
            String htmlContent = heEditeurHTML.getHtmlText();
            // Supprimer tous les boutons d'ancre d'édition
            htmlContent = htmlContent.replaceAll("<button[^>]*class=['\"]image-anchor['\"][^>]*>.*?</button>", "");
            // Supprimer les anciens spans d'ancre (au cas où)
            htmlContent = htmlContent.replaceAll("<span[^>]*class=['\"]image-anchor['\"][^>]*>.*?</span>", "");
            // Supprimer les conteneurs div non-editables (au cas où)
            htmlContent = htmlContent.replaceAll("<div[^>]*contenteditable=['\"]false['\"][^>]*>", "");
            htmlContent = htmlContent.replaceAll("</div>", "");
            // Supprimer les attributs data-image-id des images
            htmlContent = htmlContent.replaceAll("\\s*data-image-id=['\"][^'\"]*['\"]", "");
            // Nettoyage standard
            htmlContent = htmlContent.replace("[", "&lbrace;").replace("]", "&rbrace;").replace("contenteditable=\"true\"", "");
            
            getHsHTML().setStrTexteHTML(htmlContent);
            getHsHTML().setStrThemeHTML(strThemePageHTML);
            getHsHTML().setStrCouleurTexteHTML(strCouleurTexte);
            setbValide(true);
            stEditeurHTML.hide();
        });

        btnAjouteLien.setOnAction(
                (evenement) -> {
                    afficheConfigLien();
                }
        );

        btnAjouteImage.setOnAction(
                (evenement) -> {
                    afficheConfigImage();
                }
        );
        slLargeurEditeur.valueProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    heEditeurHTML.setPrefWidth((double) nouvValeur);
                    heEditeurHTML.setMaxWidth((double) nouvValeur);
                    heEditeurHTML.setMinWidth((double) nouvValeur);
                    getHsHTML().setLargeurHTML((double) nouvValeur);
                });

        stEditeurHTML.widthProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    slLargeurEditeur.setMax((double) nouvValeur - 60.d);
                    if (slLargeurEditeur.getValue() > (double) nouvValeur - 60.d) {
                        slLargeurEditeur.setValue((double) nouvValeur - 60.d);
                        heEditeurHTML.setPrefWidth((double) nouvValeur - 60.d);
                        heEditeurHTML.setMaxWidth((double) nouvValeur - 60.d);
                        heEditeurHTML.setMinWidth((double) nouvValeur - 60.d);
                    }
                    if (apDialog.isVisible()) {
                        apDialog.setLayoutX(((double) nouvValeur - apDialog.getPrefWidth()) / 2);
                    }
                });

        stEditeurHTML.heightProperty()
                .addListener((ov, ancValeur, nouvValeur) -> {
                    heEditeurHTML.setPrefHeight((double) nouvValeur - diffHauteur);
                    heEditeurHTML.setMaxHeight((double) nouvValeur - diffHauteur);
                    heEditeurHTML.setMinHeight((double) nouvValeur - diffHauteur);
                    if (wvEditeurHTML != null) {
                        wvEditeurHTML.setMaxHeight(heEditeurHTML.getPrefHeight() - 70);
                        wvEditeurHTML.setMinHeight(heEditeurHTML.getPrefHeight() - 70);
                        wvEditeurHTML.setPrefHeight(heEditeurHTML.getPrefHeight() - 70);
                    }
                    if (apDialog.isVisible()) {
                        apDialog.setLayoutY(((double) nouvValeur - apDialog.getPrefHeight()) / 2);
                    }
                });

    }

    private void quitteEditeurHTML(Stage stFenetre) {
        setbAnnule(true);
        stFenetre.hide();
    }

    @SuppressWarnings("unused")
    private void afficheConfigLien() {
        String strSelection = (String) engEditeurHTML.executeScript(strSelectText);
        List<String> strLstTarget = new ArrayList<>();
        strLstTarget.add("_self");
        strLstTarget.add("_blank");
        strLstTarget.add("_parent");
        ObservableList<String> strListeTarget = FXCollections.observableList(strLstTarget);
        int iLargeur = 400;
        int iHauteur = 200;

        apEditeur.setDisable(true);
        apDialog.setVisible(true);
        apDialog.getChildren().clear();
        apDialog.setMaxSize(iLargeur, iHauteur);
        apDialog.setMinSize(iLargeur, iHauteur);
        apDialog.setPrefSize(iLargeur, iHauteur);
        apDialog.setStyle("-fx-background-color : #ddd;"
                + "-fx-border-color: #aaa;"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 8, 0.0 , 0 , 8 );"
                + "-fx-border-width: 1px;"
        );
        apDialog.setLayoutX((apPrincipale.getPrefWidth() - apDialog.getPrefWidth()) / 2);
        apDialog.setLayoutY((apPrincipale.getPrefHeight() - apDialog.getPrefHeight()) / 2);
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("editeurHTML.ajouteLien"));
        lblBarrePersonnalisee.setMinWidth(iLargeur - 10);
        lblBarrePersonnalisee.setAlignment(Pos.CENTER);
        lblBarrePersonnalisee.setStyle("-fx-background-color : #777;");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setLayoutX(5);
        lblBarrePersonnalisee.setLayoutY(10);
        lblBarrePersonnalisee.setFont(Font.font(14));
        apDialog.getChildren().add(lblBarrePersonnalisee);
        Button btnValideLien = new Button(rbLocalisation.getString("editeurHTML.valide"), new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleLien = new Button(rbLocalisation.getString("editeurHTML.annule"), new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideLien.setPrefWidth(100);
        btnValideLien.setLayoutX(iLargeur - 130);
        btnValideLien.setLayoutY(iHauteur - 50);
        btnAnnuleLien.setPrefWidth(100);
        btnAnnuleLien.setLayoutX(iLargeur - 250);
        btnAnnuleLien.setLayoutY(iHauteur - 50);
        apDialog.getChildren().addAll(btnValideLien, btnAnnuleLien);
        Label lblTexteLien = new Label(rbLocalisation.getString("editeurHTML.texteLien"));
        lblTexteLien.setLayoutX(10);
        lblTexteLien.setLayoutY(40);
        Label lblUrlLien = new Label(rbLocalisation.getString("editeurHTML.url"));
        lblUrlLien.setLayoutX(10);
        lblUrlLien.setLayoutY(70);
        TextField tfTexteLien = new TextField(strSelection);
        tfTexteLien.setPrefWidth(200);
        tfTexteLien.setLayoutX(150);
        tfTexteLien.setLayoutY(40);
        TextField tfURLLien = new TextField("");
        tfURLLien.setPromptText("http://site.extension");

        tfURLLien.setPrefWidth(200);
        tfURLLien.setLayoutX(150);
        tfURLLien.setLayoutY(70);
        Label lblCibleLien = new Label(rbLocalisation.getString("editeurHTML.cibleLien"));
        lblCibleLien.setLayoutX(10);
        lblCibleLien.setLayoutY(100);

        ComboBox cbListeTarget = new ComboBox(strListeTarget);
        cbListeTarget.setLayoutX(150);
        cbListeTarget.setLayoutY(100);

        apDialog.getChildren().addAll(
                lblTexteLien, tfTexteLien,
                lblUrlLien, tfURLLien,
                lblCibleLien, cbListeTarget
        );
        btnValideLien.setOnAction((evenement) -> {
            String strAjouteLien = "<a href='" + tfURLLien.getText() + "' target='" + cbListeTarget.getPromptText() + "'>" + tfTexteLien.getText() + "</a>";
            engEditeurHTML.executeScript(jsCodeInsertHtml);
            getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
            getHsHTML().setStrTexteHTML(getHsHTML().getStrTexteHTML().replace("####html####" + strSelection, strAjouteLien).replace(strSelection + "####html####", strAjouteLien));
            heEditeurHTML.setHtmlText(getHsHTML().getStrTexteHTML());
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
            bDejaSauve = false;
        });
        btnAnnuleLien.setOnAction((evenement) -> {
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
        });
    }

    @SuppressWarnings("unused")
    private void afficheConfigImage() {
        int iHauteur = 700;
        int iLargeur = 900;
        apEditeur.setDisable(true);
        apDialog.setVisible(true);
        apDialog.getChildren().clear();

        // === STRUCTURE MODERNE AVEC VBOX ===
        VBox vboxDialogImage = new VBox(15);
        vboxDialogImage.setPadding(new Insets(20));
        vboxDialogImage.setMaxSize(iLargeur, iHauteur);
        vboxDialogImage.setMinSize(iLargeur, iHauteur);
        vboxDialogImage.setPrefSize(iLargeur, iHauteur);
        vboxDialogImage.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #b0b0b0;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.0, 0, 3);" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        
        // Titre
        Label lblTitre = new Label("Insertion d'image");
        lblTitre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        
        // === SECTION 1: Choix du fichier ===
        HBox hboxFichier = new HBox(10);
        hboxFichier.setAlignment(Pos.CENTER_LEFT);
        Label lblChoixFichierImage = new Label(rbLocalisation.getString("editeurHTML.choixImage"));
        lblChoixFichierImage.setPrefWidth(120);
        lblChoixFichierImage.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        TextField tfChoixFichierImage = new TextField("");
        tfChoixFichierImage.setPrefWidth(500);
        tfChoixFichierImage.setStyle("-fx-text-fill: #1a1a1a; -fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-radius: 3; -fx-background-radius: 3;");
        HBox.setHgrow(tfChoixFichierImage, Priority.ALWAYS);
        Button btnChoixFichierImage = new Button("...");
        btnChoixFichierImage.setPrefWidth(50);
        btnChoixFichierImage.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a1a;" +
            "-fx-border-color: #c0c0c0;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;" +
            "-fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 2, 0, 0, 1);"
        );
        hboxFichier.getChildren().addAll(lblChoixFichierImage, tfChoixFichierImage, btnChoixFichierImage);
        
        // === SECTION 2: Position de l'image ===
        HBox hboxPosition = new HBox(10);
        hboxPosition.setAlignment(Pos.CENTER_LEFT);
        Label lblPositionImage = new Label(rbLocalisation.getString("editeurHTML.position"));
        lblPositionImage.setPrefWidth(120);
        lblPositionImage.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        
        ToggleGroup tgPositionImage = new ToggleGroup();
        ImageView ivImageGauche = new ImageView(new Image("file:" + strImages + File.separator + "imageGauche.png", 16, 16, true, true));
        ImageView ivImageCentre = new ImageView(new Image("file:" + strImages + File.separator + "imageCentre.png", 16, 16, true, true));
        ImageView ivImageDroite = new ImageView(new Image("file:" + strImages + File.separator + "imageDroite.png", 16, 16, true, true));
        ImageView ivImageDansTexte = new ImageView(new Image("file:" + strImages + File.separator + "imageDansTexte.png", 16, 16, true, true));

        ToggleButton tgbPositionGauche = new ToggleButton("", ivImageGauche);
        ToggleButton tgbPositionCentre = new ToggleButton("", ivImageCentre);
        ToggleButton tgbPositionDroite = new ToggleButton("", ivImageDroite);
        ToggleButton tgbPositionTexte = new ToggleButton("", ivImageDansTexte);
        
        tgbPositionGauche.setTooltip(new Tooltip(rbLocalisation.getString("editeurHTML.gauche")));
        tgbPositionCentre.setTooltip(new Tooltip(rbLocalisation.getString("editeurHTML.centre")));
        tgbPositionDroite.setTooltip(new Tooltip(rbLocalisation.getString("editeurHTML.droite")));
        tgbPositionTexte.setTooltip(new Tooltip(rbLocalisation.getString("editeurHTML.texte")));

        tgbPositionGauche.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setToggleGroup(tgPositionImage);
        tgbPositionDroite.setToggleGroup(tgPositionImage);
        tgbPositionTexte.setToggleGroup(tgPositionImage);
        tgbPositionCentre.setSelected(true);
        
        // Style moderne pour les toggles non sélectionnés et sélectionnés
        String styleToggleNormal = 
            "-fx-background-color: white;" +
            "-fx-border-color: #c0c0c0;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;";
        
        String styleToggleSelected = 
            "-fx-background-color: #4CAF50;" +
            "-fx-border-color: #2E7D32;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;";
        
        // Appliquer le style initial et ajouter listeners
        ToggleButton[] toggles = {tgbPositionGauche, tgbPositionCentre, tgbPositionDroite, tgbPositionTexte};
        for (ToggleButton tgb : toggles) {
            tgb.setStyle(tgb.isSelected() ? styleToggleSelected : styleToggleNormal);
            tgb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                tgb.setStyle(newVal ? styleToggleSelected : styleToggleNormal);
            });
        }
        
        HBox hboxBoutonsPosition = new HBox(5);
        hboxBoutonsPosition.getChildren().addAll(tgbPositionGauche, tgbPositionCentre, tgbPositionDroite, tgbPositionTexte);
        hboxPosition.getChildren().addAll(lblPositionImage, hboxBoutonsPosition);
        
        // === SECTION 3: Taille de l'image ===
        HBox hboxTaille = new HBox(10);
        hboxTaille.setAlignment(Pos.CENTER_LEFT);
        Label lblLargeurImage = new Label(rbLocalisation.getString("editeurHTML.tailleImage"));
        lblLargeurImage.setPrefWidth(120);
        lblLargeurImage.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        Slider slLargeurImage = new Slider(0, 600, 300);
        slLargeurImage.setPrefWidth(500);
        slLargeurImage.setShowTickLabels(true);
        slLargeurImage.setShowTickMarks(true);
        slLargeurImage.setMajorTickUnit(100);
        slLargeurImage.setBlockIncrement(10);
        HBox.setHgrow(slLargeurImage, Priority.ALWAYS);
        Label lblValeurLargeur = new Label("300 px");
        lblValeurLargeur.setPrefWidth(70);
        lblValeurLargeur.setStyle("-fx-font-family: monospace; -fx-text-fill: #1a1a1a;");
        hboxTaille.getChildren().addAll(lblLargeurImage, slLargeurImage, lblValeurLargeur);
        
        // === SECTION 4: Prévisualisation ===
        ImageView ivImageInserer = new ImageView();
        ScrollPane spImageInserer = new ScrollPane(ivImageInserer);
        spImageInserer.setPrefSize(iLargeur - 40, 400);
        spImageInserer.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #d0d0d0; -fx-border-width: 1;");
        VBox.setVgrow(spImageInserer, Priority.ALWAYS);
        
        // === SECTION 5: Boutons ===
        Button btnValideImage = new Button(rbLocalisation.getString("editeurHTML.valide"), 
            new ImageView(new Image("file:" + strImages + File.separator + "valide.png")));
        Button btnAnnuleImage = new Button(rbLocalisation.getString("editeurHTML.annule"), 
            new ImageView(new Image("file:" + strImages + File.separator + "annule.png")));
        btnValideImage.setPrefWidth(120);
        btnAnnuleImage.setPrefWidth(120);
        
        // Style moderne pour les boutons - fond clair pour une meilleure lisibilité
        String styleButtonModerne = 
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a1a1a;" +
            "-fx-border-color: #c0c0c0;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 8 15 8 15;" +
            "-fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0, 0, 1);";
        
        btnValideImage.setStyle(styleButtonModerne);
        btnAnnuleImage.setStyle(styleButtonModerne);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox hboxBoutons = new HBox(10, spacer, btnAnnuleImage, btnValideImage);
        
        // Assemblage
        vboxDialogImage.getChildren().addAll(
            lblTitre,
            new Separator(),
            hboxFichier,
            hboxPosition,
            hboxTaille,
            spImageInserer,
            hboxBoutons
        );
        
        // Centrer le dialog
        vboxDialogImage.setLayoutX((stEditeurHTML.getWidth() - iLargeur) / 2);
        vboxDialogImage.setLayoutY((stEditeurHTML.getHeight() - iHauteur) / 2);
        
        apDialog.getChildren().add(vboxDialogImage);

        // === LISTENERS ===
        slLargeurImage.valueProperty().addListener((ov, ancValeur, nouvValeur) -> {
            ivImageInserer.setFitWidth((double) nouvValeur);
            lblValeurLargeur.setText(String.format("%d px", nouvValeur.intValue()));
        });
        
        btnChoixFichierImage.setOnAction((evenement) -> {
            FileChooser fcRepertChoix = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichiers image (*.jpg,*.png)", "*.jpg", "*.png");
            fcRepertChoix.getExtensionFilters().add(extFilter);
            File fileRepert = new File(strImages);
            fcRepertChoix.setInitialDirectory(fileRepert);
            File fileProjet = null;
            fileProjet = fcRepertChoix.showOpenDialog(stEditeurHTML);
            if (fileProjet != null) {
                strNomFichierImage = fileProjet.getAbsolutePath();
                tfChoixFichierImage.setText(strNomFichierImage);
                Image imgImageInserer = new Image("file:" + strNomFichierImage);
                double largeur = imgImageInserer.getWidth();
                @SuppressWarnings("unused")
                double hauteur = imgImageInserer.getHeight();
                slLargeurImage.setMax(largeur * 2);
                slLargeurImage.setValue(largeur);
                lblValeurLargeur.setText(String.format("%d px", (int)largeur));
                ivImageInserer.setImage(imgImageInserer);
                ivImageInserer.setFitWidth(largeur);
                ivImageInserer.setPreserveRatio(true);
                ivImageInserer.setSmooth(true);
            }
        });
        
        btnValideImage.setOnAction((evenement) -> {
            ImageEditeurHTML imageEdit = new ImageEditeurHTML();
            imageEdit.setStrImagePath(strNomFichierImage);
            
            // Générer un ID unique pour l'image
            String imageId = "img_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
            
            // Créer l'ancre d'édition (bouton circulaire avec juste l'icône)
            String ancreEdition = "<button class='image-anchor' data-image-id='" + imageId + "' "
                    + "type='button' "
                    + "style='display:inline-block; position:relative; "
                    + "margin:2px 5px; cursor:pointer; border:none; outline:none; "
                    + "background:#4CAF50; color:white; padding:8px 12px; "
                    + "border-radius:50%; font-size:18px; font-weight:bold; "
                    + "box-shadow: 0 2px 6px rgba(0,0,0,0.4); "
                    + "user-select:none; -webkit-user-select:none;' "
                    + "title='Cliquer pour supprimer cette image'>"
                    + "⚓"
                    + "</button>";
            
            String strAjouteImage = "";
            String imgTag = "";
            
            if (tgbPositionCentre.isSelected()) {
                imgTag = "<img data-image-id='" + imageId + "' width='" + (int) slLargeurImage.getValue()
                        + "' src='file:////" + strNomFichierImage + "'/>";
                strAjouteImage = "</p><p style='text-align : center;'>" + ancreEdition + imgTag + "</p><p>";
            }
            else if (tgbPositionGauche.isSelected()) {
                imgTag = "<img data-image-id='" + imageId + "' style='position=absolute;left: 0px;float:left;padding : 10px;padding-left : 0px;' width='" + (int) slLargeurImage.getValue()
                        + "' src='file:////" + strNomFichierImage + "'/>";
                strAjouteImage = ancreEdition + imgTag + "<br/>";
            }
            else if (tgbPositionDroite.isSelected()) {
                imgTag = "<img data-image-id='" + imageId + "' width='" + (int) slLargeurImage.getValue() + "' style='position=absolute;right: 0px;float:right;padding : 10px;padding-right : 0px;'"
                        + " src='file:////" + strNomFichierImage + "'/>";
                strAjouteImage = ancreEdition + imgTag + "<br/>";
            }
            else if (tgbPositionTexte.isSelected()) {
                imgTag = "<img data-image-id='" + imageId + "' width='" + (int) slLargeurImage.getValue() + "' style='padding : 10px;padding-left : 0px;'"
                        + " src='file:////" + strNomFichierImage + "'/>";
                strAjouteImage = ancreEdition + imgTag;
            }
            
            engEditeurHTML.executeScript(jsCodeInsertHtml.replace("####html####", escapeJavaStyleString(strAjouteImage, true, true)));
            getHsHTML().setStrTexteHTML(heEditeurHTML.getHtmlText());
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
            bDejaSauve = false;
            hsHTML.getImagesEditeur()[getHsHTML().getiNombreImages()] = imageEdit;
            getHsHTML().setiNombreImages(getHsHTML().getiNombreImages() + 1);
            
            // Ajouter un listener JavaScript pour détecter les clics sur les ancres
            ajouterListenerAncresImages();
        });
        
        btnAnnuleImage.setOnAction((evenement) -> {
            apDialog.setVisible(false);
            apEditeur.setDisable(false);
        });
    }

    /**
     * Masque les nœuds d'image correspondant au pattern
     * 
     * <p>Parcourt récursivement l'arbre des nœuds et masque les images
     * dont le nom correspond au pattern fourni.</p>
     * 
     * @param node Nœud racine à parcourir
     * @param imageNamePattern Pattern de nom d'image à rechercher
     * @param depth Profondeur actuelle dans l'arbre
     */
    public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int depth) {
        if (node instanceof ImageView) {
            ImageView imageView = (ImageView) node;
            // impl_getUrl() a été supprimé dans JavaFX 9+
            // Alternative : récupérer l'URL depuis userData ou utiliser toString()
            String url = imageView.getImage() != null ? imageView.getImage().getUrl() : null;
            if (url != null && imageNamePattern.matcher(url).matches()) {
                Node button = imageView.getParent().getParent();
                button.setVisible(false);
                button.setManaged(false);
            }
        }
        if (node instanceof Parent) {
            ((Parent) node).getChildrenUnmodifiable().stream().forEach((child) -> {
                hideImageNodesMatching(child, imageNamePattern, depth + 1);
            });
        }
    }

    /**
     * @return the bValide
     */
    public boolean isbValide() {

        return bValide;
    }

    /**
     * @param bValide the bValide to set
     */
    public void setbValide(boolean bValide) {
        boolean ancienneValeur = this.bValide;
        this.bValide = bValide;
        boolean nouvelleValeur = this.bValide;
        this.changeSupport.firePropertyChange("bValide", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the bAnnule
     */
    public boolean isbAnnule() {
        return bAnnule;
    }

    /**
     * @param bAnnule the bAnnule to set
     */
    public void setbAnnule(boolean bAnnule) {
        boolean ancienneValeur = this.bAnnule;
        this.bAnnule = bAnnule;
        boolean nouvelleValeur = this.bAnnule;
        this.changeSupport.firePropertyChange("bAnnule", ancienneValeur, nouvelleValeur);
    }

    /**
     * @return the hsHTML
     */
    public HotspotHTML getHsHTML() {
        return hsHTML;
    }

    /**
     * @param hsHTML the hsHTML to set
     */
    public void setHsHTML(HotspotHTML hsHTML) {
        this.hsHTML = hsHTML;
    }
    
    /**
     * Ajoute des ancres d'édition aux images existantes dans le HTML chargé
     * Cette méthode scanne toutes les balises img et leur ajoute une ancre d'édition
     */
    @SuppressWarnings("unused")
    private void ajouterAncresAuxImagesExistantes() {
        String jsScript = 
            "console.log('=== Début ajout ancres ===');" +
            "var images = document.querySelectorAll('img');" +
            "console.log('Nombre d\\'images trouvées: ' + images.length);" +
            "var imageCount = 0;" +
            "images.forEach(function(img) {" +
            "    console.log('Traitement image ' + imageCount + ': src=' + img.src);" +
            "    // Vérifier si l'image n'a pas déjà une ancre" +
            "    if (!img.hasAttribute('data-image-id')) {" +
            "        console.log('  -> Ajout ancre à l\\'image');" +
            "        // Générer un ID unique" +
            "        var imageId = 'img_' + Date.now() + '_' + Math.floor(Math.random() * 1000) + '_' + imageCount;" +
            "        imageCount++;" +
            "        img.setAttribute('data-image-id', imageId);" +
            "        " +
            "        // Créer l'ancre d'édition" +
            "        var anchor = document.createElement('span');" +
            "        anchor.className = 'image-anchor';" +
            "        anchor.setAttribute('data-image-id', imageId);" +
            "        anchor.setAttribute('contenteditable', 'false');" +
            "        anchor.setAttribute('title', 'Cliquer pour éditer ou supprimer cette image');" +
            "        anchor.style.cssText = 'display:inline-block; position:relative; z-index:1000; " +
            "            margin:5px 10px; cursor:pointer; " +
            "            background:#4CAF50; color:white; padding:5px 15px; " +
            "            border:2px solid #2E7D32; border-radius:5px; " +
            "            font-size:14px; font-weight:bold; text-decoration:none; " +
            "            box-shadow: 0 3px 6px rgba(0,0,0,0.3);';" +
            "        anchor.innerHTML = '⚓ Éditer/Supprimer image';" +
            "        " +
            "        // Insérer l'ancre après l'image" +
            "        if (img.nextSibling) {" +
            "            img.parentNode.insertBefore(anchor, img.nextSibling);" +
            "        } else {" +
            "            img.parentNode.appendChild(anchor);" +
            "        }" +
            "        console.log('  -> Ancre ajoutée avec ID: ' + imageId);" +
            "    } else {" +
            "        console.log('  -> Image a déjà un ID: ' + img.getAttribute('data-image-id'));" +
            "    }" +
            "});" +
            "console.log('=== Fin ajout ancres ===');" +
            "imageCount;"; // Retourner le nombre d'images traitées
        
        try {
            if (engEditeurHTML != null) {
                Object result = engEditeurHTML.executeScript(jsScript);
                System.out.println("Ancres ajoutées à " + result + " images");
            }
        } catch (Exception e) {
            // Si l'exécution échoue, on affiche l'erreur
            System.err.println("Erreur lors de l'ajout des ancres aux images existantes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Ajoute des ancres d'édition aux balises img dans le HTML
     * Cette méthode parse le HTML et insère une ancre après chaque balise img
     * @param htmlContent Le contenu HTML à traiter
     * @return Le HTML avec les ancres ajoutées
     */
    private String ajouterAncresAuxImages(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return htmlContent;
        }
        
        StringBuilder result = new StringBuilder();
        int pos = 0;
        int compteurImages = 0;
        
        // Pattern pour trouver les balises img (avec ou sans />)
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "<img\\s+([^>]*?)\\s*/?>", 
            java.util.regex.Pattern.CASE_INSENSITIVE | java.util.regex.Pattern.DOTALL
        );
        java.util.regex.Matcher matcher = pattern.matcher(htmlContent);
        
        while (matcher.find()) {
            // Ajouter le contenu avant l'image
            result.append(htmlContent.substring(pos, matcher.start()));
            
            String imgTag = matcher.group();
            String imgAttributes = matcher.group(1);
            
            // Vérifier si l'image a déjà un data-image-id
            if (!imgAttributes.contains("data-image-id")) {
                // Générer un ID unique
                long timestamp = System.currentTimeMillis();
                int random = (int)(Math.random() * 1000);
                String imageId = "img_" + timestamp + "_" + random + "_" + compteurImages;
                compteurImages++;
                
                // Reconstruire la balise img avec l'ID
                String newImgTag = "<img " + imgAttributes + " data-image-id=\"" + imageId + "\">";
                
                // Créer l'ancre d'édition (juste l'icône avec tooltip)
                String ancre = "<button class='image-anchor' data-image-id='" + imageId + "' "
                    + "type='button' "
                    + "style='display:inline-block; position:relative; "
                    + "margin:2px 5px; cursor:pointer; border:none; outline:none; "
                    + "background:#4CAF50; color:white; padding:8px 12px; "
                    + "border-radius:50%; font-size:18px; font-weight:bold; "
                    + "box-shadow: 0 2px 6px rgba(0,0,0,0.4); "
                    + "user-select:none; -webkit-user-select:none;' "
                    + "title='Cliquer pour supprimer cette image'>"
                    + "⚓"
                    + "</button>";
                
                // Envelopper l'ancre et l'image dans un div non-editable
                String conteneur = "<div contenteditable='false' style='display:inline-block; margin:5px;'>" 
                    + ancre + newImgTag + "</div>";
                
                // Ajouter le conteneur
                result.append(conteneur);
            } else {
                // L'image a déjà un ID, la garder telle quelle
                result.append(imgTag);
            }
            
            pos = matcher.end();
        }
        
        // Ajouter le reste du contenu
        result.append(htmlContent.substring(pos));
        
        System.out.println("Ancres ajoutées à " + compteurImages + " images dans le HTML");
        return result.toString();
    }
    
    /**
     * Méthode appelée depuis JavaScript pour supprimer une image
     * @param imageId L'ID de l'image à supprimer
     */
    public void supprimerImageDepuisJS(String imageId) {
        System.out.println("Demande de suppression image depuis JS: " + imageId);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'image");
            alert.setHeaderText("Voulez-vous supprimer cette image ?");
            alert.setContentText("Cette action est irréversible.");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String jsScript = 
                        "var img = document.querySelector('img[data-image-id=\"" + imageId + "\"]');" +
                        "var anchor = document.querySelector('.image-anchor[data-image-id=\"" + imageId + "\"]');" +
                        "if (anchor) { anchor.remove(); }" +
                        "if (img) { img.remove(); }" +
                        "console.log('Image supprimee: " + imageId + "');";
                    try {
                        engEditeurHTML.executeScript(jsScript);
                        System.out.println("Image supprimee: " + imageId);
                    } catch (Exception e) {
                        System.err.println("Erreur suppression image: " + e.getMessage());
                    }
                }
            });
        });
    }
    
    /**
     * Ajoute un listener JavaScript pour détecter les clics sur les ancres d'édition d'images
     * Utilise un bridge JavaFX pour communiquer avec Java
     */
    private void ajouterListenerAncresImages() {
        // Exposer l'objet Java à JavaScript via window
        try {
            netscape.javascript.JSObject window = (netscape.javascript.JSObject) engEditeurHTML.executeScript("window");
            window.setMember("javaApp", this);
            System.out.println("Bridge JavaFX->JavaScript installe");
        } catch (Exception e) {
            System.err.println("Erreur installation bridge: " + e.getMessage());
            e.printStackTrace();
        }
        
        String jsScript = 
            "console.log('=== Ajout listener ancres (boutons HTML natifs) ===');" +
            "var anchors = document.querySelectorAll('button.image-anchor');" +
            "console.log('Nombre boutons ancres trouves: ' + anchors.length);" +
            "" +
            "// Les boutons HTML répondent naturellement aux clics" +
            "anchors.forEach(function(anchor) {" +
            "    console.log('Installation listener sur bouton: ' + anchor.getAttribute('data-image-id'));" +
            "    " +
            "    // onclick devrait fonctionner sur un bouton HTML" +
            "    anchor.onclick = function(e) {" +
            "        console.log('CLICK DETECTE sur bouton ancre!');" +
            "        e.preventDefault();" +
            "        e.stopPropagation();" +
            "        var imageId = this.getAttribute('data-image-id');" +
            "        console.log('Appel Java pour suppression image: ' + imageId);" +
            "        try {" +
            "            window.javaApp.supprimerImageDepuisJS(imageId);" +
            "            console.log('Appel Java reussi');" +
            "        } catch(err) {" +
            "            console.error('Erreur appel Java: ' + err);" +
            "        }" +
            "        return false;" +
            "    };" +
            "    " +
            "    // Effets hover" +
            "    anchor.onmouseover = function() {" +
            "        console.log('Hover sur bouton ancre');" +
            "        this.style.background = '#388E3C';" +
            "        this.style.transform = 'scale(1.1)';" +
            "        this.style.transition = 'all 0.2s';" +
            "    };" +
            "    " +
            "    anchor.onmouseout = function() {" +
            "        this.style.background = '#4CAF50';" +
            "        this.style.transform = 'scale(1)';" +
            "    };" +
            "});" +
            "" +
            "console.log('=== Listeners installes sur boutons HTML ===');";
        
        try {
            if (engEditeurHTML != null) {
                Object result = engEditeurHTML.executeScript(jsScript);
                System.out.println("Listener ajoute aux boutons ancres. Resultat: " + result);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du listener d'ancres: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Formate le code HTML avec indentation pour une meilleure lisibilité
     */
    private String formaterHTML(String html) {
        StringBuilder formatted = new StringBuilder();
        int indentLevel = 0;
        String indent = "  "; // 2 espaces par niveau
        
        // Supprimer les espaces inutiles entre les balises
        html = html.replaceAll(">\\s+<", "><");
        
        // Découper en balises
        String[] parts = html.split("(?=<)|(?<=>)");
        
        for (String part : parts) {
            if (part.trim().isEmpty()) continue;
            
            // Balise fermante
            if (part.startsWith("</")) {
                indentLevel = Math.max(0, indentLevel - 1);
                formatted.append("\n");
                for (int i = 0; i < indentLevel; i++) {
                    formatted.append(indent);
                }
                formatted.append(part);
            }
            // Balise auto-fermante ou balise qui ne contient pas d'enfants
            else if (part.matches("<[^>]*/\\s*>") || part.matches("<(img|br|hr|input|meta|link)[^>]*>")) {
                formatted.append("\n");
                for (int i = 0; i < indentLevel; i++) {
                    formatted.append(indent);
                }
                formatted.append(part);
            }
            // Balise ouvrante
            else if (part.startsWith("<") && !part.startsWith("<!--")) {
                formatted.append("\n");
                for (int i = 0; i < indentLevel; i++) {
                    formatted.append(indent);
                }
                formatted.append(part);
                // Pas d'indentation pour les balises inline courtes
                if (!part.matches("<(span|a|strong|em|b|i|u)[^>]*>")) {
                    indentLevel++;
                }
            }
            // Commentaire
            else if (part.startsWith("<!--")) {
                formatted.append("\n");
                for (int i = 0; i < indentLevel; i++) {
                    formatted.append(indent);
                }
                formatted.append(part);
            }
            // Texte
            else {
                String text = part.trim();
                if (!text.isEmpty()) {
                    // Si le texte est long, on le met sur sa propre ligne
                    if (text.length() > 60) {
                        formatted.append("\n");
                        for (int i = 0; i < indentLevel; i++) {
                            formatted.append(indent);
                        }
                    }
                    formatted.append(text);
                }
            }
        }
        
        return formatted.toString().trim();
    }

    /**
     * Affiche le code HTML source dans une fenêtre de dialogue
     */
    @SuppressWarnings("unused")
    private void afficherCodeHTML() {
        Stage stDialog = new Stage();
        stDialog.initModality(Modality.APPLICATION_MODAL);
        stDialog.setTitle("Code HTML source");
        stDialog.setResizable(true);
        stDialog.setWidth(900);
        stDialog.setHeight(700);

        // Récupération et formatage du code HTML
        String codeHTML = heEditeurHTML.getHtmlText();
        String codeFormate = formaterHTML(codeHTML);

        // Zone de texte pour afficher le code
        TextArea taCode = new TextArea(codeFormate);
        taCode.setWrapText(true);
        taCode.setEditable(false);
        taCode.setStyle(
            "-fx-font-family: 'Consolas', 'Courier New', monospace; " +
            "-fx-font-size: 12px; " +
            "-fx-background-color: #f5f5f5; " +
            "-fx-text-fill: #333333; " +
            "-fx-control-inner-background: #f5f5f5;"
        );

        // Bouton Copier
        Button btnCopier = new Button("Copier dans le presse-papier");
        btnCopier.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 20px; " +
            "-fx-border-radius: 3px; " +
            "-fx-background-radius: 3px; " +
            "-fx-cursor: hand;"
        );
        btnCopier.setOnAction(e -> {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(codeFormate);
            clipboard.setContent(content);
            
            // Feedback visuel
            btnCopier.setText("✓ Copié !");
            btnCopier.setStyle(
                "-fx-background-color: #2196F3; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8px 20px; " +
                "-fx-border-radius: 3px; " +
                "-fx-background-radius: 3px; " +
                "-fx-cursor: hand;"
            );
            
            // Réinitialiser après 2 secondes
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(() -> {
                        btnCopier.setText("Copier dans le presse-papier");
                        btnCopier.setStyle(
                            "-fx-background-color: #4CAF50; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 8px 20px; " +
                            "-fx-border-radius: 3px; " +
                            "-fx-background-radius: 3px; " +
                            "-fx-cursor: hand;"
                        );
                    });
                } catch (InterruptedException ex) {
                    // Ignorer
                }
            }).start();
        });

        // Bouton Fermer
        Button btnFermer = new Button("Fermer");
        btnFermer.setStyle(
            "-fx-background-color: #757575; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8px 20px; " +
            "-fx-border-radius: 3px; " +
            "-fx-background-radius: 3px; " +
            "-fx-cursor: hand;"
        );
        btnFermer.setOnAction(e -> stDialog.close());

        // Layout des boutons
        HBox hbBoutons = new HBox(15);
        hbBoutons.setAlignment(Pos.CENTER);
        hbBoutons.setPadding(new Insets(10));
        hbBoutons.getChildren().addAll(btnCopier, btnFermer);

        // Layout principal
        VBox vbMain = new VBox(10);
        vbMain.setPadding(new Insets(10));
        VBox.setVgrow(taCode, Priority.ALWAYS);
        vbMain.getChildren().addAll(taCode, hbBoutons);

        Scene scene = new Scene(vbMain);
        stDialog.setScene(scene);
        stDialog.show();
    }

}
