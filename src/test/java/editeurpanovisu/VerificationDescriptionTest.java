package editeurpanovisu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Vérifie le détecteur d'énoncés invérifiables dans les descriptions générées.
 *
 * <p>Sans interface graphique : ces tests s'exécutent en intégration continue.</p>
 */
@DisplayName("VerificationDescription — détection des énoncés à relire")
class VerificationDescriptionTest {

    private static boolean contient(List<VerificationDescription.Signalement> l, String extrait) {
        return l.stream().anyMatch(s -> s.extrait().equalsIgnoreCase(extrait));
    }

    @Test
    @DisplayName("une description sobre ne déclenche aucun signalement")
    void descriptionSobreNonSignalee() {
        String texte = "Le belvédère domine la commune de Lastours, dans le département de l'Aude. "
                     + "Le site s'inscrit dans un relief de collines.";
        assertTrue(VerificationDescription.verifie(texte, "Lastours, Aude, France").isEmpty(),
                "aucun énoncé invérifiable dans ce texte");
    }

    @Test
    @DisplayName("les dates et siècles inventés sont signalés")
    void datesSignalees() {
        var r = VerificationDescription.verifie("La forteresse fut érigée en 1132.", "Lastours, Aude");
        assertFalse(r.isEmpty());
        assertTrue(contient(r, "1132"), "le millésime doit être relevé");

        assertFalse(VerificationDescription.verifie("Un édifice du XIIe siècle.", "Lastours").isEmpty(),
                "le siècle doit être relevé");
    }

    @Test
    @DisplayName("les mesures chiffrées sont signalées")
    void mesuresSignalees() {
        var r = VerificationDescription.verifie("La tour culmine à 320 m et le site couvre 12 hectares.", "");
        assertEquals(2, r.size(), "les deux mesures doivent être relevées");
    }

    @Test
    @DisplayName("les distinctions patrimoniales sont signalées")
    void distinctionsSignalees() {
        assertFalse(VerificationDescription.verifie(
                "Le site est classé au patrimoine mondial de l'UNESCO.", "Lastours").isEmpty());
        assertFalse(VerificationDescription.verifie(
                "Il figure parmi les plus beaux villages de France.", "Lastours").isEmpty());
    }

    @Test
    @DisplayName("les superlatifs de notoriété sont signalés")
    void notorieteSignalee() {
        assertFalse(VerificationDescription.verifie(
                "Ce célèbre belvédère est incontournable.", "Lastours").isEmpty());
    }

    @Test
    @DisplayName("les formulations dubitatives sont signalées")
    void douteSignale() {
        assertFalse(VerificationDescription.verifie(
                "Il s'agit sans doute d'un ancien poste de guet.", "Lastours").isEmpty());
    }

    /**
     * Une donnée fournie par l'utilisateur n'est pas une invention du modèle : si le titre de
     * la visite contient déjà une date, la description a le droit de la reprendre.
     */
    @Test
    @DisplayName("ce qui figure dans le contexte fourni n'est pas signalé")
    void contexteFourniNonSignale() {
        String contexte = "Chateaux de Lastours 1132 - Le belvédère";
        assertTrue(VerificationDescription.verifie("Le site remonte à 1132.", contexte).isEmpty(),
                "la date vient de l'utilisateur, pas du modèle");
    }

    @Test
    @DisplayName("les entrées nulles ou vides sont tolérées")
    void entreesVides() {
        assertTrue(VerificationDescription.verifie(null, "x").isEmpty());
        assertTrue(VerificationDescription.verifie("   ", "x").isEmpty());
        assertTrue(VerificationDescription.verifie("Un texte neutre.", null).isEmpty());
    }
}
