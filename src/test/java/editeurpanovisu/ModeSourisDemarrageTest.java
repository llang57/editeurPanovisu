package editeurpanovisu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Vérifie les valeurs du mode souris au démarrage, telles qu'écrites dans le modèle
 * d'interface (.pvu) et dans l'attribut {@code modeSouris} du XML exporté.
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/discussions/20">discussion #20</a>
 */
@DisplayName("ModeSourisDemarrage — valeurs exportées et lecture")
class ModeSourisDemarrageTest {

    @Test
    @DisplayName("les valeurs exportées sont celles lues par panovisu.js")
    void valeursExportees() {
        assertEquals("classique", ModeSourisDemarrage.CLASSIQUE.getValeur());
        assertEquals("streetview", ModeSourisDemarrage.STREETVIEW.getValeur());
    }

    @Test
    @DisplayName("une valeur connue est relue, sans tenir compte de la casse ni des espaces")
    void lectureValeurConnue() {
        assertEquals(ModeSourisDemarrage.STREETVIEW, ModeSourisDemarrage.depuisValeur("streetview"));
        assertEquals(ModeSourisDemarrage.STREETVIEW, ModeSourisDemarrage.depuisValeur(" StreetView "));
        assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur("classique"));
    }

    @Test
    @DisplayName("une valeur absente ou inconnue (ancien projet, .pvu édité) donne Classique")
    void valeurInconnueOuAbsenteDonneClassique() {
        assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur(null));
        assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur(""));
        assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur("n'importe quoi"));
    }
}
