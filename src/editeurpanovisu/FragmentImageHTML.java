package editeurpanovisu;

/**
 * Construit le fragment HTML qu'insère l'éditeur HTML pour une image : ancre d'édition ⚓,
 * image et, si elle est fournie, légende affichée sous l'image.
 *
 * <p>Sans légende, le fragment est celui produit depuis toujours (projets existants inchangés).
 * Avec légende, l'image et sa légende sont réunies dans un conteneur
 * {@code <span class='pv-image-legendee'>} en {@code inline-block} qui porte la position :
 * un {@code <span>} est permis dans un paragraphe, alors qu'un {@code <figure>} y fermerait
 * le {@code <p>} ouvert et couperait la phrase en position « Texte ».</p>
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/discussions/18">discussion #18</a>
 */
public final class FragmentImageHTML {

    /** Classe CSS du conteneur image + légende. */
    public static final String CLASSE_CONTENEUR = "pv-image-legendee";

    private static final String STYLE_LEGENDE =
            "display:block;text-align:center;font-style:italic;font-size:0.85em;";

    private FragmentImageHTML() {
    }

    /**
     * Ancre d'édition (bouton ⚓) placée devant l'image ; retirée à la validation.
     *
     * @param imageId identifiant partagé par l'ancre et l'image
     * @return la balise {@code <button class='image-anchor'>}
     */
    public static String ancre(String imageId) {
        return "<button class='image-anchor' data-image-id='" + imageId + "' "
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
    }

    /**
     * Fragment complet à insérer à la position du curseur.
     *
     * @param imageId     identifiant de l'image (ancre et {@code data-image-id})
     * @param cheminImage chemin absolu du fichier, sans préfixe {@code file:}
     * @param largeur     largeur affichée en pixels
     * @param position    position choisie dans la boîte de dialogue
     * @param legende     texte affiché sous l'image ; {@code null} ou blanc = pas de légende
     * @return le fragment HTML
     */
    public static String construire(String imageId, String cheminImage, int largeur,
                                    PositionImage position, String legende) {
        String strAncre = ancre(imageId);
        String strLegende = legende == null ? "" : legende.trim();

        if (strLegende.isEmpty()) {
            return switch (position) {
                case CENTRE -> "</p><p style='text-align : center;'>" + strAncre
                        + "<img data-image-id='" + imageId + "' width='" + largeur
                        + "' src='file:////" + cheminImage + "'/>" + "</p><p>";
                case GAUCHE -> strAncre
                        + "<img data-image-id='" + imageId + "' style='position=absolute;left: 0px;float:left;padding : 10px;padding-left : 0px;' width='" + largeur
                        + "' src='file:////" + cheminImage + "'/>" + "<br/>";
                case DROITE -> strAncre
                        + "<img data-image-id='" + imageId + "' width='" + largeur + "' style='position=absolute;right: 0px;float:right;padding : 10px;padding-right : 0px;'"
                        + " src='file:////" + cheminImage + "'/>" + "<br/>";
                case TEXTE -> strAncre
                        + "<img data-image-id='" + imageId + "' width='" + largeur + "' style='padding : 10px;padding-left : 0px;'"
                        + " src='file:////" + cheminImage + "'/>";
            };
        }

        String strStylePosition = switch (position) {
            case GAUCHE -> "display:inline-block;float:left;width:" + largeur + "px;margin:0 10px 10px 0;";
            case DROITE -> "display:inline-block;float:right;width:" + largeur + "px;margin:0 0 10px 10px;";
            case CENTRE -> "display:inline-block;width:" + largeur + "px;";
            case TEXTE -> "display:inline-block;width:" + largeur + "px;vertical-align:bottom;margin:0 10px 0 0;";
        };
        String strConteneur = "<span class='" + CLASSE_CONTENEUR + "' style='" + strStylePosition + "'>"
                + "<img data-image-id='" + imageId + "' width='" + largeur + "' src='file:////" + cheminImage + "'/>"
                + "<span class='pv-legende' style='" + STYLE_LEGENDE + "'>" + echappe(strLegende) + "</span>"
                + "</span>";

        return switch (position) {
            case CENTRE -> "</p><p style='text-align : center;'>" + strAncre + strConteneur + "</p><p>";
            case GAUCHE, DROITE -> strAncre + strConteneur + "<br/>";
            case TEXTE -> strAncre + strConteneur;
        };
    }

    /**
     * Réinjecte une ancre ⚓ devant chaque image d'un HTML enregistré (ancres et
     * {@code data-image-id} sont retirés à la validation).
     *
     * <p>L'ancre et l'image sont insérées telles quelles, sans enveloppe {@code <div>} :
     * le parseur HTML fermerait le {@code <p>} et le conteneur de légende ouverts,
     * détachant l'image de sa légende et coupant le paragraphe.</p>
     *
     * @param html HTML enregistré du hotspot
     * @return le HTML avec une ancre devant chaque image qui n'en a pas
     */
    public static String ajouteAncres(String html) {
        if (html == null || html.isEmpty()) {
            return html;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "<img\\s+([^>]*?)\\s*/?>",
                java.util.regex.Pattern.CASE_INSENSITIVE | java.util.regex.Pattern.DOTALL).matcher(html);
        StringBuilder sb = new StringBuilder();
        int iPos = 0;
        int iCompteur = 0;
        while (m.find()) {
            sb.append(html, iPos, m.start());
            String strAttributs = m.group(1);
            if (strAttributs.contains("data-image-id")) {
                sb.append(m.group());
            } else {
                String strId = "img_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000) + "_" + iCompteur++;
                sb.append(ancre(strId)).append("<img ").append(strAttributs)
                        .append(" data-image-id=\"").append(strId).append("\">");
            }
            iPos = m.end();
        }
        sb.append(html.substring(iPos));
        return sb.toString();
    }

    /**
     * Nettoie le HTML de l'éditeur avant enregistrement : retire les ancres ⚓, les anciennes
     * enveloppes {@code <div contenteditable=false>} et les {@code data-image-id}, puis encode
     * les crochets, qui délimitent les blocs du fichier {@code .pvu}.
     *
     * @param html HTML lu dans l'éditeur
     * @return HTML à enregistrer dans le hotspot
     */
    public static String nettoieHTML(String html) {
        String strHtml = html;
        strHtml = strHtml.replaceAll("<button[^>]*class=['\"]image-anchor['\"][^>]*>.*?</button>", "");
        strHtml = strHtml.replaceAll("<span[^>]*class=['\"]image-anchor['\"][^>]*>.*?</span>", "");
        strHtml = strHtml.replaceAll("<div[^>]*contenteditable=['\"]false['\"][^>]*>", "");
        strHtml = strHtml.replaceAll("</div>", "");
        strHtml = strHtml.replaceAll("\\s*data-image-id=['\"][^'\"]*['\"]", "");
        // &#91;/&#93; = crochets ([ ]) ; &lbrace;/&rbrace; étaient des accolades ({ }).
        return strHtml.replace("[", "&#91;").replace("]", "&#93;").replace("contenteditable=\"true\"", "");
    }

    /**
     * Script exécuté dans l'éditeur quand on clique sur l'ancre ⚓ : retire l'ancre, puis le
     * conteneur image + légende s'il existe, sinon l'image seule.
     *
     * @param imageId identifiant de l'image (attribut {@code data-image-id})
     * @return le code JavaScript
     */
    public static String scriptSuppression(String imageId) {
        return "(function(){"
                + "var img = document.querySelector('img[data-image-id=\"" + imageId + "\"]');"
                + "var anchor = document.querySelector('.image-anchor[data-image-id=\"" + imageId + "\"]');"
                + "if (anchor) { anchor.remove(); }"
                + "if (img) {"
                // Parent direct seulement : une image insérée dans une légende ne doit pas
                // emporter l'image voisine et sa légende.
                + " var parent = img.parentElement;"
                + " (parent && parent.classList.contains('" + CLASSE_CONTENEUR + "') ? parent : img).remove();"
                + "}"
                + "})();";
    }

    /**
     * Échappe un texte pour l'insérer comme contenu HTML (y compris entre apostrophes).
     *
     * @param texte texte brut
     * @return texte échappé
     */
    static String echappe(String texte) {
        StringBuilder sb = new StringBuilder(texte.length());
        for (char c : texte.toCharArray()) {
            switch (c) {
                case '&' -> sb.append("&amp;");
                case '<' -> sb.append("&lt;");
                case '>' -> sb.append("&gt;");
                case '"' -> sb.append("&quot;");
                case '\'' -> sb.append("&#39;");
                default -> sb.append(c);
            }
        }
        return sb.toString();
    }
}
