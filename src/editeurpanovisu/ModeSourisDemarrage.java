package editeurpanovisu;

/**
 * Mode de déplacement à la souris actif au chargement de la visite exportée.
 *
 * <p>La valeur est enregistrée dans le modèle d'interface (clé {@code modeSourisDemarrage=})
 * et exportée dans l'attribut {@code modeSouris} de la balise {@code <pano>}, lu par
 * {@code panovisu.js} :</p>
 * <ul>
 *   <li>{@link #CLASSIQUE} — glisser règle une vitesse de rotation ({@code iMode = 1}),
 *       comportement historique ;</li>
 *   <li>{@link #STREETVIEW} — glisser saisit l'image ({@code iMode = 0}).</li>
 * </ul>
 * <p>Sur écran tactile, le viewer force toujours le mode StreetView.</p>
 */
public enum ModeSourisDemarrage {
    CLASSIQUE("classique"),
    STREETVIEW("streetview");

    private final String strValeur;

    ModeSourisDemarrage(String strValeur) {
        this.strValeur = strValeur;
    }

    /**
     * @return la valeur écrite dans le modèle d'interface et dans le XML exporté
     */
    public String getValeur() {
        return strValeur;
    }

    /**
     * Relit une valeur du modèle d'interface.
     *
     * @param strValeur valeur lue (peut être {@code null} pour un projet antérieur)
     * @return le mode correspondant, {@link #CLASSIQUE} si la valeur est absente ou inconnue
     */
    public static ModeSourisDemarrage depuisValeur(String strValeur) {
        if (strValeur != null) {
            String strNormalisee = strValeur.trim();
            for (ModeSourisDemarrage mode : values()) {
                if (mode.strValeur.equalsIgnoreCase(strNormalisee)) {
                    return mode;
                }
            }
        }
        return CLASSIQUE;
    }
}
