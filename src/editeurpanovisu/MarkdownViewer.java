package editeurpanovisu;

import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Classe utilitaire pour afficher des fichiers Markdown dans une WebView JavaFX.
 * Utilise CommonMark-Java avec support des extensions GitHub Flavored Markdown (tables, strikethrough).
 */
public class MarkdownViewer {
    
    private final Parser parser;
    private final HtmlRenderer renderer;
    private static boolean fontesChargees = false;
    
    /**
     * Constructeur avec support des extensions GFM (GitHub Flavored Markdown)
     */
    public MarkdownViewer() {
        // Charger les polices personnalisées (une seule fois)
        if (!fontesChargees) {
            chargerFontes();
            fontesChargees = true;
        }
        
        // Extensions pour les tables et le texte barré
        List<Extension> extensions = Arrays.asList(
            TablesExtension.create(),
            StrikethroughExtension.create()
        );
        
        this.parser = Parser.builder()
                .extensions(extensions)
                .build();
        
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }
    
    /**
     * Charge les polices personnalisées pour le WebView
     * Wingdings doit être chargée explicitement car WebView ne peut pas accéder aux polices système Windows
     */
    private void chargerFontes() {
        try {
            // Charger Wingdings depuis les ressources
            String wingdingsPath = MarkdownViewer.class.getResource("fonts/wingdings.ttf").toExternalForm();
            Font.loadFont(wingdingsPath, 10);
            System.out.println("✅ Police Wingdings chargée avec succès pour WebView");
        } catch (Exception e) {
            System.err.println("⚠️ Impossible de charger Wingdings : " + e.getMessage());
            System.err.println("⚠️ Assurez-vous que src/editeurpanovisu/fonts/wingdings.ttf existe");
            System.err.println("⚠️ Copiez C:\\Windows\\Fonts\\wingding.ttf vers src/editeurpanovisu/fonts/wingdings.ttf");
        }
    }
    
    /**
     * Retourne l'URL de la police Wingdings pour @font-face CSS
     * @return URL de la police ou chaîne vide si non trouvée
     */
    private String getWingdingsFontUrl() {
        try {
            return MarkdownViewer.class.getResource("fonts/wingdings.ttf").toExternalForm();
        } catch (Exception e) {
            System.err.println("⚠️ URL Wingdings non disponible : " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Génère le CSS @font-face pour charger Wingdings dans le WebView
     * @return Code CSS @font-face ou chaîne vide si police non disponible
     */
    private String genererFontFaceCSS() {
        String fontUrl = getWingdingsFontUrl();
        if (fontUrl.isEmpty()) {
            return "";
        }
        return """
                <style>
                    /* Chargement de la police Wingdings pour les symboles */
                    @font-face {
                        font-family: 'Wingdings';
                        src: url('%s') format('truetype');
                        font-weight: normal;
                        font-style: normal;
                    }
                </style>
                """.formatted(fontUrl);
    }
    
    /**
     * Affiche un fichier Markdown dans une WebView
     * 
     * @param webView La WebView dans laquelle afficher le contenu
     * @param markdownFilePath Chemin vers le fichier Markdown
     * @throws IOException Si le fichier ne peut pas être lu
     */
    public void afficherFichierMarkdown(WebView webView, String markdownFilePath) throws IOException {
        Path fichier = Path.of(markdownFilePath).toAbsolutePath();
        String markdown = Files.readString(fichier, StandardCharsets.UTF_8);
        
        // Convertir en HTML
        String html = convertirMarkdownEnHtml(markdown);
        
        // Obtenir l'URL de base pour les chemins relatifs
        Path dossierParent = fichier.getParent();
        String baseUrl = dossierParent.toUri().toString();
        
        // Envelopper avec l'URL de base
        String htmlComplet = envelopperDansHtml(html, baseUrl);
        
        // Charger le contenu
        webView.getEngine().loadContent(htmlComplet, "text/html");
    }
    
    /**
     * Affiche du contenu Markdown dans une WebView
     * 
     * @param webView La WebView dans laquelle afficher le contenu
     * @param markdownContent Contenu Markdown à afficher
     */
    public void afficherMarkdown(WebView webView, String markdownContent) {
        String html = convertirMarkdownEnHtml(markdownContent);
        String htmlComplet = envelopperDansHtml(html);
        webView.getEngine().loadContent(htmlComplet);
    }
    
    /**
     * Convertit du Markdown en HTML
     * 
     * @param markdown Contenu Markdown
     * @return HTML généré
     */
    public String convertirMarkdownEnHtml(String markdown) {
        // Convertir le Markdown en HTML
        String html = renderer.render(parser.parse(markdown));
        
        // Remplacer les émojis par leur équivalent texte pour améliorer la compatibilité WebView
        html = remplacerEmojis(html);
        
        return html;
    }
    
    /**
     * Remplace les émojis courants par des badges visuels stylisés
     * Affiche uniquement l'icône, le texte apparaît au survol (tooltip)
     * 
     * @param html Contenu HTML avec émojis
     * @return HTML avec émojis remplacés par des icônes avec tooltips
     */
    private String remplacerEmojis(String html) {
        // Format: <span class='badge badge-xxx' title='TEXTE'>&#XXXX;</span>
        // Seule l'icône est visible, le texte apparaît au survol
        return html
            // Documentation
            .replace("📚", "<span class='badge badge-doc' title='Documentation'>&#128214;</span>")
            .replace("📖", "<span class='badge badge-doc' title='Livre'>&#128214;</span>")
            .replace("📋", "<span class='badge badge-note' title='Liste'>&#128203;</span>")
            .replace("📞", "<span class='badge badge-contact' title='Contact'>&#9742;</span>")
            .replace("📦", "<span class='badge badge-package' title='Paquet'>&#128230;</span>")
            .replace("🎯", "<span class='badge badge-target' title='Objectif'>&#9679;</span>")
            .replace("✅", "<span class='badge badge-check' title='Validé'>&#10004;</span>")
            .replace("❌", "<span class='badge badge-cross' title='Erreur'>&#10006;</span>")
            .replace("🔧", "<span class='badge badge-tools' title='Outils'>&#9881;</span>")
            .replace("🛠️", "<span class='badge badge-tools' title='Outils'>&#9881;</span>")
            .replace("🛠", "<span class='badge badge-tools' title='Outils'>&#9881;</span>")
            .replace("🚀", "<span class='badge badge-rocket' title='Déploiement'>&#9654;</span>")
            .replace("⚠️", "<span class='badge badge-warning' title='Attention'>&#9888;</span>")
            .replace("⚠", "<span class='badge badge-warning' title='Attention'>&#9888;</span>")
            .replace("🐛", "<span class='badge badge-bug' title='Bug'>&#128027;</span>")
            .replace("🔗", "<span class='badge badge-link' title='Lien'>&#128279;</span>")
            .replace("📝", "<span class='badge badge-note' title='Note'>&#128221;</span>")
            .replace("💻", "<span class='badge badge-dev' title='Développement'>&#128187;</span>")
            .replace("🏗️", "<span class='badge badge-build' title='Construction'>&#127959;</span>")
            .replace("🏗", "<span class='badge badge-build' title='Construction'>&#127959;</span>")
            .replace("🗺️", "<span class='badge badge-map' title='Carte'>&#128506;</span>")
            .replace("🗺", "<span class='badge badge-map' title='Carte'>&#128506;</span>")
            .replace("🔒", "<span class='badge badge-lock' title='Sécurité'>&#128274;</span>")
            .replace("💡", "<span class='badge badge-idea' title='Idée'>&#128161;</span>")
            .replace("🔑", "<span class='badge badge-key' title='Clé'>&#128273;</span>")
            .replace("🔄", "<span class='badge badge-refresh' title='Synchronisation'>&#128260;</span>")
            .replace("📂", "<span class='badge badge-folder' title='Dossier'>&#128193;</span>")
            .replace("📁", "<span class='badge badge-folder' title='Dossier'>&#128193;</span>")
            // Emojis supplémentaires de la présentation
            .replace("🌍", "<span class='badge badge-globe' title='Monde'>&#127758;</span>")
            .replace("🌐", "<span class='badge badge-world' title='Web'>&#127760;</span>")
            .replace("🤝", "<span class='badge badge-handshake' title='Partenariat'>&#129309;</span>")
            .replace("👥", "<span class='badge badge-users' title='Communauté'>&#128101;</span>")
            .replace("📊", "<span class='badge badge-chart' title='Statistiques'>&#128202;</span>")
            // Emojis de la documentation Ollama
            .replace("🤖", "<span class='badge badge-robot' title='Intelligence Artificielle'>&#129302;</span>")
            .replace("💰", "<span class='badge badge-money' title='Gratuit'>&#128176;</span>")
            .replace("ℹ️", "<span class='badge badge-info' title='Information'>&#8505;</span>")
            .replace("ℹ", "<span class='badge badge-info' title='Information'>&#8505;</span>")
            .replace("⭐", "<span class='badge badge-star' title='Favori'>&#11088;</span>")
            .replace("🏷️", "<span class='badge badge-tag' title='Tag'>&#127991;</span>")
            .replace("🏷", "<span class='badge badge-tag' title='Tag'>&#127991;</span>")
            .replace("✏️", "<span class='badge badge-pencil' title='Éditer'>&#9998;</span>")
            .replace("✏", "<span class='badge badge-pencil' title='Éditer'>&#9998;</span>")
            .replace("💾", "<span class='badge badge-save' title='Sauvegarder'>&#128190;</span>")
            .replace("⏱️", "<span class='badge badge-timer' title='Chronomètre'>&#9201;</span>")
            .replace("⏱", "<span class='badge badge-timer' title='Chronomètre'>&#9201;</span>")
            .replace("✨", "<span class='badge badge-sparkles' title='Magie'>&#10024;</span>")
            .replace("💬", "<span class='badge badge-comment' title='Forum'>&#128172;</span>")
            .replace("🎥", "<span class='badge badge-video' title='Vidéo'>&#127909;</span>")
            .replace("🆘", "<span class='badge badge-sos' title='Aide'>&#128629;</span>")
            .replace("🎉", "<span class='badge badge-party' title='Succès'>&#127881;</span>")
            .replace("❓", "<span class='badge badge-question' title='Question'>&#10067;</span>")
            .replace("📧", "<span class='badge badge-email' title='Email'>&#128231;</span>")
            // Emojis de aide.md (nouveaux)
            .replace("🎨", "<span class='badge badge-art' title='Design'>&#127912;</span>")
            .replace("📍", "<span class='badge badge-pin' title='Position'>&#128205;</span>")
            .replace("🔤", "<span class='badge badge-text' title='Texte'>&#128228;</span>")
            .replace("🌫️", "<span class='badge badge-cloud' title='Opacité'>&#9729;</span>")
            .replace("🌫", "<span class='badge badge-cloud' title='Opacité'>&#9729;</span>")
            .replace("🖌️", "<span class='badge badge-brush' title='Pinceau'>&#128396;</span>")
            .replace("🖌", "<span class='badge badge-brush' title='Pinceau'>&#128396;</span>")
            .replace("🔘", "<span class='badge badge-radio' title='Option'>&#128280;</span>")
            .replace("☑️", "<span class='badge badge-checkbox' title='Case à cocher'>&#9745;</span>")
            .replace("☑", "<span class='badge badge-checkbox' title='Case à cocher'>&#9745;</span>")
            .replace("🏞️", "<span class='badge badge-panorama' title='Panorama'>&#127966;</span>")
            .replace("🏞", "<span class='badge badge-panorama' title='Panorama'>&#127966;</span>")
            .replace("🖼️", "<span class='badge badge-picture' title='Image'>&#128444;</span>")
            .replace("🖼", "<span class='badge badge-picture' title='Image'>&#128444;</span>")
            .replace("📽️", "<span class='badge badge-projector' title='Diaporama'>&#128253;</span>")
            .replace("📽", "<span class='badge badge-projector' title='Diaporama'>&#128253;</span>")
            .replace("🔵", "<span class='badge badge-blue' title='Bleu'>&#128309;</span>")
            .replace("🟠", "<span class='badge badge-orange' title='Orange'>&#128992;</span>")
            .replace("🟣", "<span class='badge badge-purple' title='Violet'>&#128995;</span>")
            .replace("🧭", "<span class='badge badge-compass' title='Boussole'>&#129517;</span>")
            .replace("👁️", "<span class='badge badge-eye' title='Œil'>&#128065;</span>")
            .replace("👁", "<span class='badge badge-eye' title='Œil'>&#128065;</span>")
            .replace("📱", "<span class='badge badge-mobile' title='Mobile'>&#128241;</span>")
            .replace("🏨", "<span class='badge badge-hotel' title='Hôtel'>&#127976;</span>")
            .replace("🏡", "<span class='badge badge-house' title='Maison'>&#127969;</span>")
            .replace("🏙️", "<span class='badge badge-city' title='Ville'>&#127961;</span>")
            .replace("🏙", "<span class='badge badge-city' title='Ville'>&#127961;</span>")
            .replace("🏘️", "<span class='badge badge-houses' title='Quartier'>&#127960;</span>")
            .replace("🏘", "<span class='badge badge-houses' title='Quartier'>&#127960;</span>")
            .replace("🏛️", "<span class='badge badge-monument' title='Monument'>&#127963;</span>")
            .replace("🏛", "<span class='badge badge-monument' title='Monument'>&#127963;</span>")
            .replace("🏰", "<span class='badge badge-castle' title='Château'>&#127984;</span>")
            .replace("🏰️", "<span class='badge badge-castle' title='Château'>&#127984;</span>")
            .replace("⏱️", "<span class='badge badge-stopwatch' title='Chronomètre'>&#9201;</span>")
            .replace("⏱", "<span class='badge badge-stopwatch' title='Chronomètre'>&#9201;</span>")
            .replace("▶️", "<span class='badge badge-play' title='Lecture'>&#9654;</span>")
            .replace("▶", "<span class='badge badge-play' title='Lecture'>&#9654;</span>")
            .replace("🚪", "<span class='badge badge-door' title='Porte'>&#128682;</span>")
            .replace("➕", "<span class='badge badge-plus' title='Ajouter'>&#10133;</span>")
            .replace("↔️", "<span class='badge badge-arrows' title='Flèches'>&#8596;</span>")
            .replace("↔", "<span class='badge badge-arrows' title='Flèches'>&#8596;</span>")
            .replace("📏", "<span class='badge badge-ruler' title='Règle'>&#128207;</span>")
            .replace("📐", "<span class='badge badge-triangle' title='Triangle'>&#128208;</span>")
            .replace("🔺", "<span class='badge badge-triangle-up' title='Triangle'>&#128314;</span>")
            .replace("🔲", "<span class='badge badge-square' title='Carré'>&#128306;</span>")
            .replace("🎭", "<span class='badge badge-theater' title='Théâtre'>&#127917;</span>")
            .replace("🎬", "<span class='badge badge-clapper' title='Action'>&#127916;</span>")
            .replace("👴", "<span class='badge badge-elder' title='Accessibilité'>&#128116;</span>")
            .replace("🖱️", "<span class='badge badge-mouse' title='Souris'>&#128433;</span>")
            .replace("🖱", "<span class='badge badge-mouse' title='Souris'>&#128433;</span>")
            .replace("🖵", "<span class='badge badge-fullscreen' title='Plein écran'>&#128441;</span>")
            .replace("📘", "<span class='badge badge-book-blue' title='Livre'>&#128216;</span>")
            .replace("🐦", "<span class='badge badge-bird' title='Twitter'>&#128038;</span>")
            .replace("🛒", "<span class='badge badge-cart' title='Boutique'>&#128722;</span>")
            .replace("📷", "<span class='badge badge-camera' title='Photo'>&#128247;</span>")
            .replace("📸", "<span class='badge badge-camera' title='Photo'>&#128248;</span>")
            .replace("📄", "<span class='badge badge-page' title='Document'>&#128196;</span>")
            .replace("🏠", "<span class='badge badge-home' title='Accueil'>&#127968;</span>")
            .replace("🌳", "<span class='badge badge-tree' title='Arbre'>&#127795;</span>")
            .replace("🆓", "<span class='badge badge-free' title='Gratuit'>&#127345;</span>")
            .replace("©️", "<span class='badge badge-copyright' title='Copyright'>&#169;</span>")
            .replace("©", "<span class='badge badge-copyright' title='Copyright'>&#169;</span>")
            .replace("🏆", "<span class='badge badge-trophy' title='Trophée'>&#127942;</span>")
            .replace("🔖", "<span class='badge badge-bookmark' title='Signet'>&#128278;</span>")
            .replace("🎛️", "<span class='badge badge-controls' title='Contrôles'>&#127899;</span>")
            .replace("🎛", "<span class='badge badge-controls' title='Contrôles'>&#127899;</span>");
    }
    
    /**
     * Enveloppe le HTML dans une structure complète avec CSS
     * 
     * @param bodyHtml Contenu HTML du body
     * @return HTML complet avec head et styles
     */
    private String envelopperDansHtml(String bodyHtml) {
        return envelopperDansHtml(bodyHtml, null);
    }
    
    /**
     * Enveloppe le HTML dans une structure complète avec CSS et URL de base
     * 
     * @param bodyHtml Contenu HTML du body
     * @param baseUrl URL de base pour résoudre les chemins relatifs (peut être null)
     * @return HTML complet avec head et styles
     */
    private String envelopperDansHtml(String bodyHtml, String baseUrl) {
        String baseTag = (baseUrl != null) ? "<base href=\"" + baseUrl + "\">" : "";
        
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                """ + baseTag + 
                genererFontFaceCSS() + """
                <style>
                    body {
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji', sans-serif;
                        line-height: 1.6;
                        color: #333;
                        max-width: 900px;
                        margin: 0 auto;
                        padding: 20px;
                        background: #ffffff;
                    }
                    
                    h1, h2, h3, h4, h5, h6 {
                        margin-top: 24px;
                        margin-bottom: 16px;
                        font-weight: 600;
                        line-height: 1.25;
                        color: #1a1a1a;
                    }
                    
                    h1 {
                        font-size: 2em;
                        border-bottom: 1px solid #e1e4e8;
                        padding-bottom: 0.3em;
                    }
                    
                    h2 {
                        font-size: 1.5em;
                        border-bottom: 1px solid #e1e4e8;
                        padding-bottom: 0.3em;
                    }
                    
                    h3 { font-size: 1.25em; }
                    h4 { font-size: 1em; }
                    h5 { font-size: 0.875em; }
                    h6 { font-size: 0.85em; color: #6a737d; }
                    
                    p {
                        margin-top: 0;
                        margin-bottom: 16px;
                    }
                    
                    a {
                        color: #0366d6;
                        text-decoration: none;
                    }
                    
                    a:hover {
                        text-decoration: underline;
                    }
                    
                    code {
                        background-color: rgba(27, 31, 35, 0.05);
                        border-radius: 3px;
                        font-size: 85%;
                        margin: 0;
                        padding: 0.2em 0.4em;
                        font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
                    }
                    
                    pre {
                        background-color: #f6f8fa;
                        border-radius: 6px;
                        padding: 16px;
                        overflow-x: auto;
                        line-height: 1.45;
                        margin-bottom: 16px;
                    }
                    
                    pre code {
                        background-color: transparent;
                        border: 0;
                        display: inline;
                        line-height: inherit;
                        margin: 0;
                        padding: 0;
                        word-wrap: normal;
                    }
                    
                    blockquote {
                        border-left: 4px solid #dfe2e5;
                        color: #6a737d;
                        padding: 0 1em;
                        margin: 0 0 16px 0;
                    }
                    
                    ul, ol {
                        margin-top: 0;
                        margin-bottom: 16px;
                        padding-left: 2em;
                    }
                    
                    li {
                        margin-top: 0.25em;
                    }
                    
                    table {
                        border-collapse: collapse;
                        width: 100%;
                        margin-bottom: 16px;
                        overflow: auto;
                    }
                    
                    table th {
                        font-weight: 600;
                        background-color: #f6f8fa;
                        padding: 6px 13px;
                        border: 1px solid #dfe2e5;
                    }
                    
                    table td {
                        padding: 6px 13px;
                        border: 1px solid #dfe2e5;
                    }
                    
                    table tr {
                        background-color: #ffffff;
                        border-top: 1px solid #c6cbd1;
                    }
                    
                    table tr:nth-child(2n) {
                        background-color: #f6f8fa;
                    }
                    
                    hr {
                        height: 0.25em;
                        padding: 0;
                        margin: 24px 0;
                        background-color: #e1e4e8;
                        border: 0;
                    }
                    
                    img {
                        max-width: 100%;
                        height: auto;
                        border-radius: 6px;
                    }
                    
                    /* Support du strikethrough */
                    del {
                        text-decoration: line-through;
                        color: #6a737d;
                    }
                    
                    /* Badges visuels pour les indicateurs - Icônes avec tooltips */
                    .badge {
                        display: inline-block;
                        padding: 4px 8px;
                        margin: 0 2px;
                        border-radius: 4px;
                        font-size: 1em;
                        font-weight: normal;
                        font-family: 'Segoe UI', Arial, sans-serif;
                        white-space: nowrap;
                        vertical-align: middle;
                        line-height: 1.4;
                        cursor: help;
                        transition: transform 0.2s ease, box-shadow 0.2s ease;
                    }
                    
                    /* Effet au survol */
                    .badge:hover {
                        transform: scale(1.15);
                        box-shadow: 0 2px 8px rgba(0,0,0,0.2);
                    }
                    
                    /* Symboles Wingdings pour icônes fiables */
                    .wi {
                        font-family: 'Wingdings', 'Webdings', 'Segoe UI Symbol', sans-serif;
                        font-size: 1.1em;
                        display: inline-block;
                        margin-right: 2px;
                    }
                    
                    /* Couleurs par catégorie sémantique */
                    .badge-check { background: #28a745; color: #fff; }    /* Vert - Succès */
                    .badge-cross { background: #dc3545; color: #fff; }    /* Rouge - Erreur */
                    .badge-warning { background: #ffc107; color: #333; }  /* Orange - Attention */
                    .badge-info { background: #17a2b8; color: #fff; }     /* Cyan - Information */
                    .badge-robot { background: #546e7a; color: #fff; }    /* Gris - IA/Robot */
                    .badge-doc { background: #6f42c1; color: #fff; }      /* Violet - Documentation */
                    .badge-package { background: #795548; color: #fff; }  /* Marron - Package */
                    .badge-target { background: #dc3545; color: #fff; }   /* Rouge - Objectif */
                    .badge-tools { background: #607d8b; color: #fff; }    /* Gris bleu - Outils */
                    .badge-rocket { background: #ff6f00; color: #fff; }   /* Orange vif - Déploiement */
                    .badge-bug { background: #e91e63; color: #fff; }      /* Rose - Bug */
                    .badge-note { background: #00897b; color: #fff; }     /* Vert-bleu - Note */
                    .badge-dev { background: #37474f; color: #fff; }      /* Gris foncé - Dev */
                    .badge-build { background: #8d6e63; color: #fff; }    /* Marron - Build */
                    .badge-map { background: #43a047; color: #fff; }      /* Vert - Carte */
                    .badge-lock { background: #d32f2f; color: #fff; }     /* Rouge foncé - Sécurité */
                    .badge-idea { background: #ffd600; color: #333; }     /* Jaune - Idée */
                    .badge-key { background: #7b1fa2; color: #fff; }      /* Violet foncé - Clé */
                    .badge-refresh { background: #0288d1; color: #fff; }  /* Bleu clair - Sync */
                    .badge-folder { background: #ffb300; color: #333; }   /* Jaune/Orange - Dossier */
                    .badge-contact { background: #1976d2; color: #fff; }  /* Bleu - Contact */
                    .badge-world { background: #1976d2; color: #fff; }    /* Bleu - Internet */
                    .badge-handshake { background: #ff6f00; color: #fff; } /* Orange - Partenariat */
                    .badge-users { background: #5e35b1; color: #fff; }    /* Violet - Communauté */
                    .badge-chart { background: #3949ab; color: #fff; }    /* Bleu indigo - Statistiques */
                    .badge-link { background: #0277bd; color: #fff; }     /* Bleu - Lien */
                    .badge-money { background: #388e3c; color: #fff; }    /* Vert - Gratuit */
                    .badge-star { background: #f57f17; color: #fff; }     /* Or - Étoile */
                    .badge-tag { background: #757575; color: #fff; }      /* Gris - Tag */
                    .badge-pencil { background: #f57c00; color: #fff; }   /* Orange - Modifier */
                    .badge-save { background: #0097a7; color: #fff; }     /* Cyan - Sauvegarder */
                    .badge-timer { background: #6a1b9a; color: #fff; }    /* Violet - Timer */
                    .badge-sparkles { background: #c2185b; color: #fff; } /* Rose - Magie */
                    .badge-comment { background: #388e3c; color: #fff; }  /* Vert - Forum */
                    .badge-video { background: #c62828; color: #fff; }    /* Rouge - Vidéo */
                    .badge-sos { background: #b71c1c; color: #fff; }      /* Rouge foncé - SOS */
                    .badge-party { background: #ad1457; color: #fff; }    /* Rose vif - Succès */
                    .badge-question { background: #ef6c00; color: #fff; } /* Orange - Question */
                    .badge-email { background: #43a047; color: #fff; }    /* Vert - Email */
                    .badge-art { background: #8e24aa; color: #fff; }      /* Violet - Design */
                    .badge-pin { background: #d32f2f; color: #fff; }      /* Rouge - Position */
                    .badge-text { background: #424242; color: #fff; }     /* Gris foncé - Texte */
                    .badge-cloud { background: #90a4ae; color: #fff; }    /* Gris clair - Opacité */
                    .badge-brush { background: #6a1b9a; color: #fff; }    /* Violet - Pinceau */
                    .badge-radio { background: #1976d2; color: #fff; }    /* Bleu - Option radio */
                    .badge-checkbox { background: #388e3c; color: #fff; } /* Vert - Checkbox */
                    .badge-panorama { background: #00897b; color: #fff; } /* Vert-bleu - Panoramique */
                    .badge-picture { background: #ef6c00; color: #fff; }  /* Orange - Image */
                    .badge-projector { background: #5e35b1; color: #fff; } /* Violet - Diaporama */
                    .badge-blue { background: #1976d2; color: #fff; }     /* Bleu */
                    .badge-orange { background: #f57c00; color: #fff; }   /* Orange */
                    .badge-purple { background: #7b1fa2; color: #fff; }   /* Violet */
                    .badge-compass { background: #d32f2f; color: #fff; }  /* Rouge - Boussole */
                    .badge-eye { background: #424242; color: #fff; }      /* Gris foncé - Œil */
                    .badge-mobile { background: #424242; color: #fff; }   /* Gris foncé - Mobile */
                    .badge-hotel { background: #1976d2; color: #fff; }    /* Bleu - Hôtel */
                    .badge-house { background: #f57c00; color: #fff; }    /* Orange - Maison */
                    .badge-city { background: #424242; color: #fff; }     /* Gris foncé - Ville */
                    .badge-houses { background: #8d6e63; color: #fff; }   /* Marron - Quartier */
                    .badge-monument { background: #8d6e63; color: #fff; } /* Marron - Monument */
                    .badge-castle { background: #5e35b1; color: #fff; }   /* Violet - Château */
                    .badge-stopwatch { background: #6a1b9a; color: #fff; } /* Violet - Chronomètre */
                    .badge-play { background: #388e3c; color: #fff; }     /* Vert - Lecture */
                    .badge-door { background: #795548; color: #fff; }     /* Marron - Porte */
                    .badge-plus { background: #388e3c; color: #fff; }     /* Vert - Ajouter */
                    .badge-arrows { background: #1976d2; color: #fff; }   /* Bleu - Flèches */
                    .badge-ruler { background: #ff6f00; color: #fff; }    /* Orange - Règle */
                    .badge-triangle { background: #f57c00; color: #fff; } /* Orange - Triangle */
                    .badge-triangle-up { background: #1976d2; color: #fff; } /* Bleu - Triangle */
                    .badge-square { background: #424242; color: #fff; }   /* Gris - Carré */
                    .badge-theater { background: #8e24aa; color: #fff; }  /* Violet - Théâtre */
                    .badge-clapper { background: #424242; color: #fff; }  /* Gris foncé - Clap */
                    .badge-elder { background: #795548; color: #fff; }    /* Marron - Accessibilité */
                    .badge-mouse { background: #424242; color: #fff; }    /* Gris foncé - Souris */
                    .badge-fullscreen { background: #1976d2; color: #fff; } /* Bleu - Plein écran */
                    .badge-book-blue { background: #1976d2; color: #fff; } /* Bleu - Livre */
                    .badge-bird { background: #1da1f2; color: #fff; }     /* Bleu Twitter */
                    .badge-cart { background: #ff6f00; color: #fff; }     /* Orange - Boutique */
                    .badge-camera { background: #424242; color: #fff; }   /* Gris foncé - Photo */
                    .badge-page { background: #757575; color: #fff; }     /* Gris - Document */
                    .badge-home { background: #f57c00; color: #fff; }     /* Orange - Accueil */
                    .badge-tree { background: #388e3c; color: #fff; }     /* Vert - Arbre */
                    .badge-free { background: #388e3c; color: #fff; }     /* Vert - Gratuit */
                    .badge-copyright { background: #424242; color: #fff; } /* Gris - Copyright */
                    .badge-trophy { background: #ffd600; color: #333; }   /* Or - Trophée */
                    .badge-bookmark { background: #1976d2; color: #fff; } /* Bleu - Signet */
                    .badge-controls { background: #424242; color: #fff; } /* Gris foncé - Contrôles */
                </style>
            </head>
            <body>
            """ + bodyHtml + """
            </body>
            </html>
            """;
    }
    
    /**
     * Exemple d'utilisation
     */
    public static void main(String[] args) {
        String markdown = """
            # Titre Principal
            
            Ceci est un **texte en gras** et *en italique*.
            
            ## Sous-titre
            
            - Item 1
            - Item 2
            - ~~Item barré~~
            
            ### Code
            
            ```java
            public class Hello {
                public static void main(String[] args) {
                    System.out.println("Hello World!");
                }
            }
            ```
            
            ### Tableau
            
            | Colonne 1 | Colonne 2 | Colonne 3 |
            |-----------|-----------|-----------|
            | Valeur 1  | Valeur 2  | Valeur 3  |
            | Valeur 4  | Valeur 5  | Valeur 6  |
            
            ### Citation
            
            > Ceci est une citation
            > sur plusieurs lignes
            
            [Lien vers GitHub](https://github.com)
            """;
        
        MarkdownViewer viewer = new MarkdownViewer();
        String html = viewer.convertirMarkdownEnHtml(markdown);
        System.out.println(html);
    }
    
}
