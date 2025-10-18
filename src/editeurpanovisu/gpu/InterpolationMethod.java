package editeurpanovisu.gpu;

/**
 * Méthodes d'interpolation disponibles pour le redimensionnement d'images.
 * Chaque méthode offre un compromis différent entre qualité et performance.
 * 
 * @author PanoVisu Team
 * @version 3.2.0
 */
public enum InterpolationMethod {
    /**
     * Plus proche voisin (Nearest Neighbor)
     * - Qualité: ⭐☆☆☆☆ (pixelisé, effet "blocky")
     * - Vitesse: ⭐⭐⭐⭐⭐ (très rapide)
     * - Usage: Pixel art, sprites, prévisualisation rapide
     */
    NEAREST("Nearest Neighbor", "Plus proche voisin"),
    
    /**
     * Interpolation bilinéaire
     * - Qualité: ⭐⭐⭐☆☆ (lissé, perte de netteté)
     * - Vitesse: ⭐⭐⭐⭐☆ (rapide)
     * - Usage: Agrandissements modestes (×1.5 à ×2)
     */
    BILINEAR("Bilinear", "Bilinéaire"),
    
    /**
     * Interpolation bicubique (Catmull-Rom)
     * - Qualité: ⭐⭐⭐⭐☆ (excellent, préserve les détails)
     * - Vitesse: ⭐⭐⭐☆☆ (moyen, idéal pour GPU)
     * - Usage: Standard pour photos et panoramas (×2 à ×4)
     * - RECOMMANDÉ pour la plupart des cas
     */
    BICUBIC("Bicubic", "Bicubique"),
    
    /**
     * Interpolation Lanczos3
     * - Qualité: ⭐⭐⭐⭐⭐ (excellente netteté)
     * - Vitesse: ⭐⭐☆☆☆ (lent, optimal avec GPU)
     * - Usage: Agrandissements critiques (×4+), impression haute qualité
     * - Attention: Peut produire des artefacts "ringing" légers
     */
    LANCZOS3("Lanczos3", "Lanczos3");
    
    private final String nameEN;
    private final String nameFR;
    
    InterpolationMethod(String nameEN, String nameFR) {
        this.nameEN = nameEN;
        this.nameFR = nameFR;
    }
    
    /**
     * Obtient le nom en anglais
     * @return Nom anglais de la méthode
     */
    public String getNameEN() {
        return nameEN;
    }
    
    /**
     * Obtient le nom en français
     * @return Nom français de la méthode
     */
    public String getNameFR() {
        return nameFR;
    }
    
    /**
     * Obtient le nom localisé selon la langue système
     * @return Nom localisé
     */
    public String getLocalizedName() {
        // Détection simple de la locale
        String language = System.getProperty("user.language", "en");
        return language.startsWith("fr") ? nameFR : nameEN;
    }
    
    @Override
    public String toString() {
        return getLocalizedName();
    }
}
