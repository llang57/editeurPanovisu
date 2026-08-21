package editeurpanovisu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static editeurpanovisu.OrdrePanoramique.HAUTEUR_LIGNE;
import static editeurpanovisu.OrdrePanoramique.MARGE_BASSE;
import static editeurpanovisu.OrdrePanoramique.MAX_LIGNES_VISIBLES;
import static editeurpanovisu.OrdrePanoramique.hauteurListe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Vérifie le calcul de hauteur de la liste de tri des panoramiques.
 *
 * <p>Ne dépend d'aucune interface graphique : ces tests s'exécutent partout,
 * y compris sur un serveur d'intégration sans affichage.</p>
 *
 * @see <a href="https://github.com/llang57/editeurPanovisu/issues/16">issue #16</a>
 */
@DisplayName("OrdrePanoramique — calcul de la hauteur de liste")
class OrdrePanoramiqueDimensionTest {

    @Test
    @DisplayName("sous le plafond, la hauteur suit le nombre d'éléments")
    void hauteurSuitLeNombreDElements() {
        assertEquals(MARGE_BASSE, hauteurListe(0), "liste vide : seule la marge subsiste");
        assertEquals(HAUTEUR_LIGNE + MARGE_BASSE, hauteurListe(1));
        assertEquals(3 * HAUTEUR_LIGNE + MARGE_BASSE, hauteurListe(3));
        assertEquals(MAX_LIGNES_VISIBLES * HAUTEUR_LIGNE + MARGE_BASSE, hauteurListe(MAX_LIGNES_VISIBLES));
    }

    @Test
    @DisplayName("au-delà du plafond, la hauteur n'augmente plus")
    void hauteurPlafonnee() {
        double plafond = hauteurListe(MAX_LIGNES_VISIBLES);
        assertEquals(plafond, hauteurListe(MAX_LIGNES_VISIBLES + 1));
        assertEquals(plafond, hauteurListe(14), "le cas rapporte dans l'issue #16");
        assertEquals(plafond, hauteurListe(1000));
    }

    /**
     * Invariant central de l'issue #16.
     *
     * <p>Le cadre doit toujours mesurer un nombre entier de lignes, majoré de la marge.
     * Si ce n'est plus le cas, le mécanisme de virtualisation de JavaFX laisse une bande
     * vide en bas de liste et les derniers panoramiques deviennent inaccessibles.</p>
     */
    @Test
    @DisplayName("régression #16 : la hauteur vaut toujours un nombre entier de lignes")
    void hauteurToujoursUnNombreEntierDeLignes() {
        for (int nb = 0; nb <= MAX_LIGNES_VISIBLES; nb++) {
            double lignes = (hauteurListe(nb) - MARGE_BASSE) / HAUTEUR_LIGNE;
            assertEquals(nb, lignes, 0.0,
                    "pour " + nb + " elements, le cadre doit mesurer exactement " + nb + " lignes");
        }
        double lignesAuPlafond = (hauteurListe(50) - MARGE_BASSE) / HAUTEUR_LIGNE;
        assertEquals(MAX_LIGNES_VISIBLES, lignesAuPlafond, 0.0,
                "au plafond, le cadre doit mesurer exactement " + MAX_LIGNES_VISIBLES + " lignes");
    }

    @Test
    @DisplayName("la hauteur ne peut jamais être négative ni nulle")
    void hauteurToujoursPositive() {
        for (int nb = 0; nb <= 30; nb++) {
            assertTrue(hauteurListe(nb) > 0, "hauteur invalide pour " + nb + " elements");
        }
    }
}
