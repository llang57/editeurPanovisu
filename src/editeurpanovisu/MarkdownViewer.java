package editeurpanovisu;

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
    
    /**
     * Constructeur avec support des extensions GFM (GitHub Flavored Markdown)
     */
    public MarkdownViewer() {
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
     * Remplace les émojis courants par des icônes Font Awesome
     * JavaFX WebView sous Windows ne supporte pas bien les émojis Unicode
     * 
     * @param html Contenu HTML avec émojis
     * @return HTML avec émojis remplacés par des icônes Font Awesome
     */
    private String remplacerEmojis(String html) {
        // Remplacer les émojis par des badges textuels colorés
        // JavaFX WebView a des limitations avec les polices web, on utilise des badges simples
        return html
            // Emojis de la documentation aide
            .replace("📚", "<span class='emoji-badge emoji-doc' title='Documentation'>[DOC]</span>")
            .replace("📖", "<span class='emoji-badge emoji-doc' title='Livre'>[LIVRE]</span>")
            .replace("📋", "<span class='emoji-badge emoji-note' title='Liste'>[LISTE]</span>")
            .replace("📞", "<span class='emoji-badge emoji-contact' title='Contact'>[CONTACT]</span>")
            .replace("📦", "<span class='emoji-badge emoji-package' title='Paquet'>[PAQUET]</span>")
            .replace("🎯", "<span class='emoji-badge emoji-target' title='Objectif'>[OBJECTIF]</span>")
            .replace("✅", "<span class='emoji-badge emoji-check' title='OK'>[OK]</span>")
            .replace("❌", "<span class='emoji-badge emoji-cross' title='Erreur'>[ERREUR]</span>")
            .replace("🔧", "<span class='emoji-badge emoji-tools' title='Outils'>[OUTILS]</span>")
            .replace("🛠️", "<span class='emoji-badge emoji-tools' title='Outils'>[OUTILS]</span>")
            .replace("🚀", "<span class='emoji-badge emoji-rocket' title='Déploiement'>[DEPLOIEMENT]</span>")
            .replace("⚠️", "<span class='emoji-badge emoji-warning' title='Attention'>[ATTENTION]</span>")
            .replace("⚠", "<span class='emoji-badge emoji-warning' title='Attention'>[ATTENTION]</span>")
            .replace("🐛", "<span class='emoji-badge emoji-bug' title='Bogue'>[BOGUE]</span>")
            .replace("🔗", "<span class='emoji-badge emoji-link' title='Lien'>[LIEN]</span>")
            .replace("📝", "<span class='emoji-badge emoji-note' title='Note'>[NOTE]</span>")
            .replace("💻", "<span class='emoji-badge emoji-dev' title='Développement'>[DEV]</span>")
            .replace("🏗️", "<span class='emoji-badge emoji-build' title='Construction'>[CONSTRUCTION]</span>")
            .replace("🏗", "<span class='emoji-badge emoji-build' title='Construction'>[CONSTRUCTION]</span>")
            .replace("🗺️", "<span class='emoji-badge emoji-map' title='Carte'>[CARTE]</span>")
            .replace("🗺", "<span class='emoji-badge emoji-map' title='Carte'>[CARTE]</span>")
            .replace("🔒", "<span class='emoji-badge emoji-lock' title='Sécurité'>[SECURITE]</span>")
            .replace("💡", "<span class='emoji-badge emoji-idea' title='Idée'>[IDEE]</span>")
            .replace("🔑", "<span class='emoji-badge emoji-key' title='Clé'>[CLE]</span>")
            .replace("🔄", "<span class='emoji-badge emoji-refresh' title='Synchronisation'>[SYNC]</span>")
            .replace("📂", "<span class='emoji-badge emoji-folder' title='Dossier'>[DOSSIER]</span>")
            // Emojis supplémentaires de la présentation
            .replace("🌍", "<span class='emoji-badge emoji-globe' title='Monde'>[MONDE]</span>")
            .replace("🤝", "<span class='emoji-badge emoji-handshake' title='Partenariat'>[PARTENARIAT]</span>")
            .replace("👥", "<span class='emoji-badge emoji-users' title='Communauté'>[COMMUNAUTE]</span>")
            .replace("📊", "<span class='emoji-badge emoji-chart' title='Statistiques'>[STATS]</span>")
            // Emojis de la documentation Ollama
            .replace("🤖", "<span class='emoji-badge emoji-robot' title='Robot'>[IA]</span>")
            .replace("💰", "<span class='emoji-badge emoji-money' title='Gratuit'>[GRATUIT]</span>")
            .replace("ℹ️", "<span class='emoji-badge emoji-info' title='Info'>[INFO]</span>")
            .replace("ℹ", "<span class='emoji-badge emoji-info' title='Info'>[INFO]</span>")
            .replace("⭐", "<span class='emoji-badge emoji-star' title='Étoile'>[*]</span>")
            .replace("🏷️", "<span class='emoji-badge emoji-tag' title='Étiquette'>[TAG]</span>")
            .replace("🏷", "<span class='emoji-badge emoji-tag' title='Étiquette'>[TAG]</span>")
            .replace("✏️", "<span class='emoji-badge emoji-pencil' title='Modifier'>[EDIT]</span>")
            .replace("✏", "<span class='emoji-badge emoji-pencil' title='Modifier'>[EDIT]</span>")
            .replace("💾", "<span class='emoji-badge emoji-save' title='Sauvegarder'>[SAVE]</span>")
            .replace("⏱️", "<span class='emoji-badge emoji-timer' title='Patience'>[TIMER]</span>")
            .replace("⏱", "<span class='emoji-badge emoji-timer' title='Patience'>[TIMER]</span>")
            .replace("✨", "<span class='emoji-badge emoji-sparkles' title='Magie'>[MAGIC]</span>")
            .replace("💬", "<span class='emoji-badge emoji-comment' title='Forum'>[FORUM]</span>")
            .replace("🎥", "<span class='emoji-badge emoji-video' title='Vidéo'>[VIDEO]</span>")
            .replace("🆘", "<span class='emoji-badge emoji-sos' title='Aide'>[SOS]</span>")
            .replace("🎉", "<span class='emoji-badge emoji-party' title='Succès'>[SUCCESS]</span>")
            .replace("❓", "<span class='emoji-badge emoji-question' title='Question'>[?]</span>")
            .replace("🌐", "<span class='emoji-badge emoji-world' title='Internet'>[INTERNET]</span>")
            .replace("📧", "<span class='emoji-badge emoji-email' title='Email'>[EMAIL]</span>");
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
                """ + baseTag + """
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
                    
                    /* Badges pour remplacer les émojis */
                    .emoji-badge {
                        display: inline-block;
                        padding: 2px 6px;
                        margin: 0 2px;
                        border-radius: 3px;
                        font-size: 0.75em;
                        font-weight: bold;
                        font-family: 'Consolas', 'Monaco', monospace;
                        vertical-align: middle;
                        white-space: nowrap;
                        color: white;
                    }
                    
                    /* Badges spécifiques par type */
                    .emoji-check { background-color: #28a745; }  /* Vert - Succès */
                    .emoji-cross { background-color: #dc3545; }  /* Rouge - Erreur */
                    .emoji-warning { background-color: #ffc107; color: #333; } /* Orange - Attention */
                    .emoji-doc { background-color: #6f42c1; }    /* Violet - Documentation */
                    .emoji-package { background-color: #6c757d; } /* Gris - Package */
                    .emoji-target { background-color: #007bff; }  /* Bleu - Objectif */
                    .emoji-tools { background-color: #17a2b8; }   /* Cyan - Outils */
                    .emoji-rocket { background-color: #fd7e14; }  /* Orange vif - Déploiement */
                    .emoji-bug { background-color: #e83e8c; }     /* Rose - Bug */
                    .emoji-note { background-color: #20c997; }    /* Vert menthe - Note */
                    .emoji-dev { background-color: #343a40; }     /* Noir - Dev */
                    .emoji-build { background-color: #795548; }   /* Marron - Build */
                    .emoji-map { background-color: #4caf50; }     /* Vert - Carte */
                    .emoji-lock { background-color: #f44336; }    /* Rouge foncé - Sécurité */
                    .emoji-idea { background-color: #ffeb3b; color: #333; } /* Jaune - Idée */
                    .emoji-key { background-color: #9c27b0; }     /* Violet foncé - Clé */
                    .emoji-refresh { background-color: #03a9f4; } /* Bleu clair - Sync */
                    .emoji-folder { background-color: #607d8b; }  /* Gris bleu - Dossier */
                    .emoji-contact { background-color: #2196f3; } /* Bleu - Contact */
                    .emoji-globe { background-color: #00897b; }   /* Vert-bleu - Monde */
                    .emoji-handshake { background-color: #ffa726; } /* Orange clair - Partenariat */
                    .emoji-users { background-color: #8e24aa; }   /* Violet - Communauté */
                    .emoji-chart { background-color: #3f51b5; }   /* Bleu indigo - Statistiques */
                    .emoji-link { background-color: #0288d1; }    /* Bleu - Lien */
                    .emoji-robot { background-color: #455a64; }   /* Gris foncé - Robot/IA */
                    .emoji-money { background-color: #4caf50; }   /* Vert - Gratuit */
                    .emoji-info { background-color: #2196f3; }    /* Bleu - Info */
                    .emoji-star { background-color: #ffb300; color: #333; } /* Or - Étoile */
                    .emoji-tag { background-color: #9e9e9e; }     /* Gris - Tag */
                    .emoji-pencil { background-color: #ff9800; }  /* Orange - Modifier */
                    .emoji-save { background-color: #00bcd4; }    /* Cyan - Sauvegarder */
                    .emoji-timer { background-color: #9c27b0; }   /* Violet - Timer */
                    .emoji-sparkles { background-color: #e91e63; } /* Rose - Magie */
                    .emoji-comment { background-color: #4caf50; } /* Vert - Forum */
                    .emoji-video { background-color: #f44336; }   /* Rouge - Vidéo */
                    .emoji-sos { background-color: #d32f2f; }     /* Rouge foncé - SOS */
                    .emoji-party { background-color: #ff4081; }   /* Rose vif - Succès */
                    .emoji-question { background-color: #ff9800; } /* Orange - Question */
                    .emoji-world { background-color: #2196f3; }   /* Bleu - Internet */
                    .emoji-email { background-color: #4caf50; }   /* Vert - Email */
                    
                    /* Badges et éléments spéciaux */
                    .badge {
                        display: inline-block;
                        padding: 0.25em 0.4em;
                        font-size: 75%;
                        font-weight: 700;
                        line-height: 1;
                        text-align: center;
                        white-space: nowrap;
                        vertical-align: baseline;
                        border-radius: 0.25rem;
                    }
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
