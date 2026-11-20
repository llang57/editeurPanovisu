package editeurpanovisu;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Le mode souris au démarrage survit à l'aller-retour du modèle d'interface
 * ({@code strGetTemplate()} → {@code setTemplate()}), et un modèle qui ne le mentionne pas
 * (projet antérieur) revient à Classique.
 *
 * <p>Le contrôleur déclare des contrôles JavaFX en champs : il faut le toolkit. Sans
 * affichage, les tests sont <b>ignorés</b> plutôt qu'en échec (même motif que
 * {@link OrdrePanoramiqueRenduTest}).</p>
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/discussions/20">discussion #20</a>
 */
@DisplayName("Modèle d'interface — mode souris au démarrage")
class ModeSourisModeleInterfaceTest {

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

    @Test
    @DisplayName("par défaut, un nouveau projet démarre en Classique")
    void defautClassique() {
        assertEquals(ModeSourisDemarrage.CLASSIQUE, new GestionnaireInterfaceController().getModeSourisDemarrage());
    }

    @Test
    @DisplayName("StreetView est écrit dans le modèle puis relu")
    void allerRetourStreetView() {
        GestionnaireInterfaceController source = new GestionnaireInterfaceController();
        source.setModeSourisDemarrage(ModeSourisDemarrage.STREETVIEW);
        String strModele = source.strGetTemplate();
        assertTrue(strModele.contains("modeSourisDemarrage=streetview\n"), strModele);

        GestionnaireInterfaceController cible = new GestionnaireInterfaceController();
        cible.setTemplate(Arrays.asList(strModele.split("\n")));
        assertEquals(ModeSourisDemarrage.STREETVIEW, cible.getModeSourisDemarrage());
    }

    @Test
    @DisplayName("un modèle sans la clé (ancien projet) remet Classique, même après un projet StreetView")
    void modeleSansCleRemetClassique() {
        GestionnaireInterfaceController gi = new GestionnaireInterfaceController();
        gi.setModeSourisDemarrage(ModeSourisDemarrage.STREETVIEW);
        gi.setTemplate(List.of("souris=oui"));
        assertEquals(ModeSourisDemarrage.CLASSIQUE, gi.getModeSourisDemarrage());
    }

    @Test
    @DisplayName("le sélecteur porte un nom accessible localisé (lecteurs d'écran)")
    void selecteurNomAccessible() {
        ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", Locale.FRENCH);
        ComboBox<ModeSourisDemarrage> cb = GestionnaireInterfaceController.creeComboModeSouris(rb, ModeSourisDemarrage.STREETVIEW);
        assertEquals("Mode souris au démarrage", cb.getAccessibleText());
        assertEquals(ModeSourisDemarrage.STREETVIEW, cb.getValue());
    }
}
