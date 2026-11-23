package editeurpanovisu;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Le bouton ⚓ retire l'image et, si elle en a une, sa légende — y compris après réouverture
 * du projet, quand l'image a reçu un nouvel identifiant et une enveloppe {@code <div>}.
 *
 * <p>Exécute le script dans un vrai {@link WebEngine} (le moteur de l'éditeur HTML).
 * Sans affichage, les tests sont <b>ignorés</b> plutôt qu'en échec.</p>
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/discussions/18">discussion #18</a>
 */
@DisplayName("Éditeur HTML — suppression d'une image (⚓)")
class SuppressionImageTest {

    private static boolean toolkitPret;

    @BeforeAll
    static void demarreJavaFX() {
        try {
            CountDownLatch demarre = new CountDownLatch(1);
            Platform.startup(demarre::countDown);
            toolkitPret = demarre.await(15, TimeUnit.SECONDS);
        } catch (IllegalStateException dejaDemarre) {
            toolkitPret = true;
        } catch (Throwable indisponible) {
            toolkitPret = false;
        }
    }

    @BeforeEach
    void exigeToolkit() {
        assumeTrue(toolkitPret, "toolkit JavaFX indisponible (environnement sans affichage) — test ignoré");
    }

    /** Charge le HTML, exécute le script de suppression, rend {@code body.innerHTML}. */
    private static String apresSuppression(String html, String imageId) throws Exception {
        CompletableFuture<String> resultat = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                WebEngine moteur = new WebEngine();
                moteur.getLoadWorker().stateProperty().addListener((obs, ancien, etat) -> {
                    if (etat == Worker.State.SUCCEEDED) {
                        try {
                            moteur.executeScript(FragmentImageHTML.scriptSuppression(imageId));
                            resultat.complete((String) moteur.executeScript("document.body.innerHTML"));
                        } catch (Throwable t) {
                            resultat.completeExceptionally(t);
                        }
                    } else if (etat == Worker.State.FAILED) {
                        resultat.completeExceptionally(new IllegalStateException("chargement échoué"));
                    }
                });
                moteur.loadContent("<html><body>" + html + "</body></html>");
            } catch (Throwable t) {
                resultat.completeExceptionally(t);
            }
        });
        return resultat.get(20, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("image légendée : conteneur, légende et ancre disparaissent, le texte reste")
    void supprimeConteneurEtLegende() throws Exception {
        String html = "<p>avant</p>"
                + FragmentImageHTML.construire("img_1", "C:/x/a.jpg", 300, PositionImage.GAUCHE, "© Jorge")
                + "<p>après</p>";
        String corps = apresSuppression(html, "img_1");
        assertFalse(corps.contains(FragmentImageHTML.CLASSE_CONTENEUR), corps);
        assertFalse(corps.contains("Jorge"), corps);
        assertFalse(corps.contains("image-anchor"), corps);
        assertTrue(corps.contains("avant") && corps.contains("après"), corps);
    }

    @Test
    @DisplayName("après réouverture (ancres réinjectées, nouvel id) : le conteneur est retiré")
    void supprimeConteneurApresRechargement() throws Exception {
        // HTML tel qu'enregistré : ancres et data-image-id ont été retirés à la validation.
        String enregistre = "<p>texte <span class='pv-image-legendee' style='display:inline-block;width:300px;'>"
                + "<img width='300' src='file:////C:/x/a.jpg'/>"
                + "<span class='pv-legende' style='display:block;'>© Jorge</span></span> suite</p>";
        String rouvert = FragmentImageHTML.ajouteAncres(enregistre);
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("data-image-id=\"([^\"]+)\"").matcher(rouvert);
        assertTrue(m.find(), rouvert);
        String corps = apresSuppression(rouvert, m.group(1));
        assertFalse(corps.contains(FragmentImageHTML.CLASSE_CONTENEUR), corps);
        assertFalse(corps.contains("Jorge"), corps);
        assertTrue(corps.contains("<p>texte  suite</p>"), corps);
    }

    @Test
    @DisplayName("réouverture : l'image reste dans son conteneur, le paragraphe n'est pas coupé")
    void reouvertureGardeImageDansConteneur() throws Exception {
        String enregistre = "<p>texte <span class='pv-image-legendee' style='display:inline-block;width:300px;'>"
                + "<img width='300' src='x'/>"
                + "<span class='pv-legende' style='display:block;'>© Jorge</span></span> suite</p>";
        String rouvert = FragmentImageHTML.ajouteAncres(enregistre);
        assertTrue(rouvert.contains("image-anchor"), rouvert);
        assertFalse(rouvert.contains("<div"), rouvert);
        // id absent du document : rien n'est supprimé, on lit la structure telle que parsée.
        String corps = apresSuppression(rouvert, "absent");
        assertTrue(corps.matches("(?s)<p>texte <span class=\"pv-image-legendee\"[^>]*><button.*<img[^>]*><span class=\"pv-legende\".*</span></span> suite</p>"), corps);
    }

    @Test
    @DisplayName("image sans légende : seule l'image part, le paragraphe reste")
    void imageSansLegende() throws Exception {
        String corps = apresSuppression("<p>a<img data-image-id='img_3' src='x'/>b</p>", "img_3");
        assertFalse(corps.contains("<img"), corps);
        assertTrue(corps.contains("<p>ab</p>"), corps);
    }

    @Test
    @DisplayName("image sans légende insérée dans une légende : la voisine et sa légende restent")
    void imageImbriqueeNeRemontePas() throws Exception {
        String html = "<p><span class='pv-image-legendee' style='display:inline-block;'>"
                + "<img data-image-id='img_4' src='a'/>"
                + "<span class='pv-legende' style='display:block;'>© Jorge "
                + "<img data-image-id='img_5' src='b'/></span></span></p>";
        String corps = apresSuppression(html, "img_5");
        assertFalse(corps.contains("img_5"), corps);
        assertTrue(corps.contains("img_4"), corps);
        assertTrue(corps.contains("Jorge"), corps);
    }
}
