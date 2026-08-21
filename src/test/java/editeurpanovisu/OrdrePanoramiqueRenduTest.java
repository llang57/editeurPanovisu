package editeurpanovisu;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Vérifie que la liste réellement construite respecte la hauteur calculée.
 *
 * <p>C'est la divergence entre ces deux valeurs qui a causé l'issue #16 : la hauteur du
 * cadre était calculée en supposant 46 px par ligne, sans que cette hauteur soit imposée
 * à la liste. Les thèmes de la v3.x ramenaient la ligne à ~39 px, et le mécanisme de
 * virtualisation conservait le nombre de cellules de l'ancienne hypothèse.</p>
 *
 * <p>Ce test nécessite le toolkit JavaFX. Sur un environnement sans affichage
 * (intégration continue), il est <b>ignoré</b> plutôt qu'en échec.</p>
 */
@DisplayName("OrdrePanoramique — cohérence entre hauteur calculée et hauteur imposée")
class OrdrePanoramiqueRenduTest {

    private static boolean toolkitPret;

    /**
     * Démarre le toolkit JavaFX sans jamais échouer.
     *
     * <p>L'abandon est décidé dans la méthode de test et non ici : une assomption placée
     * dans un {@code @BeforeAll} avorte le conteneur entier, et Surefire ne comptabilise
     * alors ni test exécuté ni test ignoré. Le test disparaîtrait silencieusement du
     * rapport.</p>
     */
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

    @Test
    @DisplayName("régression #16 : la liste impose la hauteur de ligne servant au calcul")
    void listeImposeLaHauteurDeLigneUtiliseeParLeCalcul() throws Exception {
        assumeTrue(toolkitPret,
                "toolkit JavaFX indisponible (environnement sans affichage) — test ignoré");

        final int nbPanoramiques = 14;   // le cas rapporté dans l'issue

        FutureTask<double[]> mesure = new FutureTask<>(() -> {
            EditeurPanovisu.setPanoramiquesProjet(new Panoramique[100]);
            for (int i = 0; i < nbPanoramiques; i++) {
                Panoramique pano = new Panoramique();
                pano.setStrNomFichier("panos" + File.separator + "pano" + i + ".jpg");
                pano.setStrTitrePanoramique("Panoramique " + i);
                EditeurPanovisu.getPanoramiquesProjet()[i] = pano;
            }
            EditeurPanovisu.setiNombrePanoramiques(nbPanoramiques);

            OrdrePanoramique ordre = new OrdrePanoramique();
            ordre.creeListe();

            AnchorPane conteneur = ordre.getApListePanoramiques();
            ListView<?> liste = (ListView<?>) conteneur.getChildren().get(0);
            return new double[]{
                    ordre.getStrPanos().size(),
                    liste.getFixedCellSize(),
                    liste.getPrefHeight(),
                    conteneur.getPrefHeight()
            };
        });
        Platform.runLater(mesure);
        double[] r = mesure.get(30, TimeUnit.SECONDS);

        double nbElements = r[0];
        double hauteurLigneImposee = r[1];
        double hauteurListe = r[2];
        double hauteurConteneur = r[3];

        assertEquals(nbPanoramiques, nbElements, 0.0,
                "tous les panoramiques doivent figurer dans la liste");
        assertEquals(OrdrePanoramique.HAUTEUR_LIGNE, hauteurLigneImposee, 0.0,
                "la hauteur de ligne doit etre imposee via setFixedCellSize");
        assertEquals(OrdrePanoramique.hauteurListe(nbPanoramiques), hauteurListe, 0.0,
                "la liste doit adopter la hauteur calculee");
        assertEquals(hauteurListe, hauteurConteneur, 0.0,
                "le conteneur doit suivre la meme hauteur que la liste");

        // l'invariant : le cadre mesure un nombre entier de lignes reellement imposees
        double lignesVisibles = (hauteurListe - OrdrePanoramique.MARGE_BASSE) / hauteurLigneImposee;
        assertEquals(OrdrePanoramique.MAX_LIGNES_VISIBLES, lignesVisibles, 0.0,
                "hauteur calculee et hauteur rendue ont diverge : c'est exactement le defaut #16");
    }
}
