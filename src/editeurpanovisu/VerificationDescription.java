package editeurpanovisu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Repère dans une description générée les affirmations qu'aucun modèle ne peut garantir.
 *
 * <p>Aucune formulation de prompt ne rend l'hallucination impossible : un modèle de langage
 * reste génératif. Les consignes et la température basse la raréfient ; cette classe la rend
 * <b>visible</b>, ce qui est la seule garantie réellement atteignable.</p>
 *
 * <p>La vérification est volontairement conservatrice et purement textuelle. Elle ne juge pas
 * de la véracité — elle signale les catégories d'énoncés que les consignes interdisent :
 * nombres, dates, distinctions patrimoniales, superlatifs de notoriété, marques de doute.
 * Un signalement n'est donc pas la preuve d'une erreur, mais un point à relire.</p>
 *
 * @author PanoVisu
 */
public final class VerificationDescription {

    /** Millésimes et siècles : « en 1789 », « au XIIe siècle ». */
    private static final Pattern DATES = Pattern.compile(
            "\\b(1[0-9]{3}|20[0-9]{2})\\b|\\b[IVXLCM]+(?:e|ème|ᵉ)\\s+si[èe]cle\\b",
            Pattern.CASE_INSENSITIVE);

    /** Mesures chiffrées : « 320 m », « 12 hectares », « 45 km ». */
    private static final Pattern MESURES = Pattern.compile(
            "\\b\\d+([.,]\\d+)?\\s*(m|km|cm|mm|m²|km²|ha|hectares?|m[eè]tres?|kilom[èe]tres?|"
            + "pieds?|[ée]tages?|habitants?|visiteurs?)\\b",
            Pattern.CASE_INSENSITIVE);

    /** Distinctions et protections, qu'un modèle attribue volontiers à tort. */
    private static final Pattern DISTINCTIONS = Pattern.compile(
            "\\b(unesco|patrimoine mondial|monument historique|class[ée]e?s?\\s+(au|aux|monument)|"
            + "inscrit[e]?s?\\s+au|label\\b|plus beaux? villages?|grand site)\\b",
            Pattern.CASE_INSENSITIVE);

    /** Superlatifs et qualificatifs de notoriété, interdits par les consignes. */
    private static final Pattern NOTORIETE = Pattern.compile(
            "\\b(c[ée]l[èe]bre|renomm[ée]e?|r[ée]put[ée]e?|incontournable|embl[ée]matique|"
            + "prestigieux|prestigieuse|mondialement|le plus beau|la plus belle|les plus beaux|"
            + "tr[èe]s pris[ée]|haut lieu)\\b",
            Pattern.CASE_INSENSITIVE);

    /** Marques de doute : la consigne demande d'omettre plutôt que de nuancer. */
    private static final Pattern DOUTE = Pattern.compile(
            "\\b(peut-[êe]tre|sans doute|probablement|il se pourrait|semble-t-il|vraisemblablement)\\b",
            Pattern.CASE_INSENSITIVE);

    /**
     * Mot commençant par une majuscule, avec ce qui le précède immédiatement.
     *
     * <p>Sert à repérer les noms propres : le groupe 1 capture le caractère précédent, ce qui
     * permet d'écarter les débuts de phrase, où la majuscule n'indique rien.</p>
     */
    private static final Pattern NOM_PROPRE = Pattern.compile(
            "([^.!?:\\n]\\s+)([A-ZÀÂÄÉÈÊËÎÏÔÖÙÛÜÇ][\\p{L}'-]{2,})");

    /**
     * Mots à majuscule qui ne sont pas des noms propres vérifiables.
     *
     * <p>Évite de signaler des tournures courantes plutôt que des lieux ou monuments.</p>
     */
    private static final java.util.Set<String> MOTS_COURANTS = java.util.Set.of(
            "les", "des", "aux", "cette", "ces", "leur", "leurs", "elle", "elles",
            "cependant", "toutefois", "ainsi", "depuis", "aujourd'hui");

    private VerificationDescription() {
    }

    /**
     * Un énoncé signalé dans une description.
     *
     * @param categorie Nature du signalement, en clair
     * @param extrait   Le fragment de texte concerné
     */
    public record Signalement(String categorie, String extrait) {
        @Override
        public String toString() {
            return categorie + " : « " + extrait + " »";
        }
    }

    /**
     * Analyse une description générée et renvoie les énoncés à relire.
     *
     * <p>Les éléments déjà présents dans le contexte fourni au modèle ne sont pas signalés :
     * une date figurant dans le titre de la visite est une donnée de l'utilisateur, pas une
     * invention du modèle.</p>
     *
     * @param description Texte produit par le modèle ; {@code null} est toléré
     * @param contexte    Informations transmises au modèle (titres, lieu géocodé), ou {@code null}
     * @return La liste des signalements, vide si rien n'est à relever
     */
    public static List<Signalement> verifie(String description, String contexte) {
        List<Signalement> signalements = new ArrayList<>();
        if (description == null || description.isBlank()) {
            return signalements;
        }
        String reference = contexte == null ? "" : contexte.toLowerCase();

        cherche(signalements, description, reference, DATES, "date ou siècle non vérifiable");
        cherche(signalements, description, reference, MESURES, "mesure chiffrée");
        cherche(signalements, description, reference, DISTINCTIONS, "distinction ou classement");
        cherche(signalements, description, reference, NOTORIETE, "superlatif de notoriété");
        cherche(signalements, description, reference, DOUTE, "formulation dubitative");
        chercheNomsPropres(signalements, description, reference);
        return signalements;
    }

    /**
     * Signale les noms propres absents du contexte fourni au modèle.
     *
     * <p>C'est la catégorie la plus utile et la plus difficile : lors d'un essai réel, un modèle
     * a situé les châteaux de Lastours « dans les gorges de la rivière Orb » alors que la rivière
     * qui les longe est l'Orbiel. Aucune règle de prompt n'avait empêché cette confusion, et
     * seules les catégories chiffrées la laissaient passer.</p>
     *
     * <p>La détection est délibérément large : un nom propre légitime mais non fourni sera
     * signalé lui aussi. Le signalement invite à relire, il n'affirme pas une erreur.</p>
     *
     * @param signalements Liste alimentée par la méthode
     * @param description  Texte analysé
     * @param reference    Contexte en minuscules
     */
    private static void chercheNomsPropres(List<Signalement> signalements, String description,
                                           String reference) {
        Matcher m = NOM_PROPRE.matcher(description);
        while (m.find()) {
            String mot = m.group(2);
            String minuscule = mot.toLowerCase();
            if (MOTS_COURANTS.contains(minuscule) || reference.contains(minuscule)) {
                continue;
            }
            signalements.add(new Signalement("nom propre absent du contexte fourni", mot));
        }
    }

    /**
     * Applique un motif et retient les correspondances absentes du contexte.
     *
     * @param signalements Liste alimentée par la méthode
     * @param description  Texte analysé
     * @param reference    Contexte en minuscules, servant à écarter les faux positifs
     * @param motif        Motif recherché
     * @param categorie    Libellé associé au motif
     */
    private static void cherche(List<Signalement> signalements, String description,
                                String reference, Pattern motif, String categorie) {
        Matcher m = motif.matcher(description);
        while (m.find()) {
            String extrait = m.group().trim();
            if (!reference.contains(extrait.toLowerCase())) {
                signalements.add(new Signalement(categorie, extrait));
            }
        }
    }

    /**
     * Écrit les signalements sur la sortie standard, pour la trace de génération.
     *
     * @param description Texte produit par le modèle
     * @param contexte    Informations transmises au modèle
     * @return La liste des signalements, afin de pouvoir aussi les exploiter dans l'interface
     */
    public static List<Signalement> verifieEtTrace(String description, String contexte) {
        List<Signalement> signalements = verifie(description, contexte);
        if (signalements.isEmpty()) {
            System.out.println("[IA] Vérification : aucun énoncé à relire.");
        } else {
            System.out.println("[IA] ⚠ Vérification : " + signalements.size()
                    + " énoncé(s) à relire avant publication —");
            for (Signalement s : signalements) {
                System.out.println("[IA]     • " + s);
            }
        }
        return signalements;
    }
}
