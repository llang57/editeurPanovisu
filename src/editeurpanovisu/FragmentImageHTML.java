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
