package editeurpanovisu;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Fragment HTML inséré par la boîte « Insertion d'image » de l'éditeur HTML.
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/discussions/18">discussion #18</a>
 */
@DisplayName("Éditeur HTML — fragment image et légende")
class FragmentImageHTMLTest {

    private static final String ID = "img_1";
    private static final String CHEMIN = "C:/x/a.jpg";
    private static final int LARGEUR = 300;

    /** Ancre recopiée littéralement de EditeurHTML (avant extraction). */
    private static final String ANCRE = "<button class='image-anchor' data-image-id='" + ID + "' "
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

    private static final String IMG_NUE = "<img data-image-id='" + ID + "' width='" + LARGEUR
            + "' src='file:////" + CHEMIN + "'/>";

    private static final String STYLE_LEGENDE =
            "<span class='pv-legende' style='display:block;text-align:center;font-style:italic;font-size:0.85em;'>";

    /** Sortie de EditeurHTML.java:1524-1543 avant extraction, recopiée littéralement. */
    private static String ancienFragment(PositionImage position) {
        return switch (position) {
            case CENTRE -> "</p><p style='text-align : center;'>" + ANCRE
                    + "<img data-image-id='" + ID + "' width='" + LARGEUR
                    + "' src='file:////" + CHEMIN + "'/>" + "</p><p>";
            case GAUCHE -> ANCRE
                    + "<img data-image-id='" + ID + "' style='position=absolute;left: 0px;float:left;padding : 10px;padding-left : 0px;' width='" + LARGEUR
                    + "' src='file:////" + CHEMIN + "'/>" + "<br/>";
            case DROITE -> ANCRE
                    + "<img data-image-id='" + ID + "' width='" + LARGEUR + "' style='position=absolute;right: 0px;float:right;padding : 10px;padding-right : 0px;'"
                    + " src='file:////" + CHEMIN + "'/>" + "<br/>";
            case TEXTE -> ANCRE
                    + "<img data-image-id='" + ID + "' width='" + LARGEUR + "' style='padding : 10px;padding-left : 0px;'"
                    + " src='file:////" + CHEMIN + "'/>";
        };
    }

    static Stream<Arguments> positionsEtLegendesVides() {
        return Stream.of(PositionImage.values())
                .flatMap(p -> Stream.of(Arguments.of(p, null), Arguments.of(p, ""), Arguments.of(p, "   ")));
    }

    @ParameterizedTest(name = "{0}, légende [{1}]")
    @MethodSource("positionsEtLegendesVides")
    @DisplayName("sans légende, le fragment est identique à l'ancien code")
    void sansLegendeIdentique(PositionImage position, String legende) {
        assertEquals(ancienFragment(position), FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, position, legende));
    }

    @Test
    @DisplayName("l'ancre est inchangée")
    void ancreInchangee() {
        assertEquals(ANCRE, FragmentImageHTML.ancre(ID));
    }

    @Test
    @DisplayName("gauche : le conteneur flotte, l'image ne flotte plus")
    void gaucheAvecLegende() {
        assertEquals(ANCRE
                + "<span class='pv-image-legendee' style='display:inline-block;float:left;width:300px;margin:0 10px 10px 0;'>"
                + IMG_NUE + STYLE_LEGENDE + "© Jorge</span></span><br/>",
                FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, PositionImage.GAUCHE, "© Jorge"));
    }

    @Test
    @DisplayName("droite : le conteneur flotte à droite")
    void droiteAvecLegende() {
        assertEquals(ANCRE
                + "<span class='pv-image-legendee' style='display:inline-block;float:right;width:300px;margin:0 0 10px 10px;'>"
                + IMG_NUE + STYLE_LEGENDE + "© Jorge</span></span><br/>",
                FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, PositionImage.DROITE, "© Jorge"));
    }

    @Test
    @DisplayName("centre : l'enveloppe de paragraphe centré est conservée")
    void centreAvecLegende() {
        assertEquals("</p><p style='text-align : center;'>" + ANCRE
                + "<span class='pv-image-legendee' style='display:inline-block;width:300px;'>"
                + IMG_NUE + STYLE_LEGENDE + "© Jorge</span></span></p><p>",
                FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, PositionImage.CENTRE, "© Jorge"));
    }

    @Test
    @DisplayName("texte : en ligne, sans balise bloc qui couperait le paragraphe")
    void texteAvecLegende() {
        String fragment = FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, PositionImage.TEXTE, "© Jorge");
        assertEquals(ANCRE
                + "<span class='pv-image-legendee' style='display:inline-block;width:300px;vertical-align:bottom;margin:0 10px 0 0;'>"
                + IMG_NUE + STYLE_LEGENDE + "© Jorge</span></span>", fragment);
        assertFalse(fragment.contains("<figure"), fragment);
        assertFalse(fragment.contains("<div"), fragment);
        assertFalse(fragment.contains("<p"), fragment);
    }

    @Test
    @DisplayName("la légende est rognée et échappée HTML")
    void legendeEchappee() {
        String fragment = FragmentImageHTML.construire(ID, CHEMIN, LARGEUR, PositionImage.GAUCHE,
                "  <b>\"Tom\" & l'été>  ");
        assertTrue(fragment.contains(STYLE_LEGENDE + "&lt;b&gt;&quot;Tom&quot; &amp; l&#39;été&gt;</span>"), fragment);
    }
}
