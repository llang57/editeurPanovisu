package editeurpanovisu;

/**
 * Position d'une image insérée dans un hotspot HTML (boîte « Insertion d'image »).
 */
public enum PositionImage {
    /** Flottante à gauche, le texte l'entoure à droite. */
    GAUCHE,
    /** Seule sur sa ligne, centrée. */
    CENTRE,
    /** Flottante à droite, le texte l'entoure à gauche. */
    DROITE,
    /** Au fil du texte, comme un caractère. */
    TEXTE
}
