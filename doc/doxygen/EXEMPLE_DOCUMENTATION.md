# Exemple de documentation Javadoc - EditeurPanovisu

Ce fichier contient un exemple concret de classe Java bien documentée selon les standards Javadoc/Doxygen.

## Exemple : Classe Panoramique

Voici comment la classe `Panoramique` devrait être documentée :

```java
package editeurpanovisu;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * Représente un panorama sphérique 360°.
 * 
 * Un panorama contient une image équirectangulaire, des coordonnées géographiques,
 * des paramètres de vue, et une collection de hotspots pour la navigation
 * et l'interaction.
 * 
 * <p>Cette classe gère à la fois les métadonnées du panorama (titre, description,
 * coordonnées GPS) et sa représentation visuelle dans l'interface utilisateur.</p>
 * 
 * <h3>Exemple d'utilisation :</h3>
 * <pre>{@code
 * Panoramique pano = new Panoramique();
 * pano.setImagePanoramique("panoramas/entree.jpg");
 * pano.setTitre("Hall d'entrée");
 * pano.setCoordonnees(new CoordonneesGeographiques(48.8566, 2.3522));
 * }</pre>
 * 
 * @author Louis Lang
 * @version 3.0.0
 * @since 1.0.0
 * @see HotSpot
 * @see CoordonneesGeographiques
 */
public class Panoramique {
    
    /**
     * Identifiant unique du panorama.
     * Utilisé pour les références croisées entre panoramas via les hotspots.
     */
    private String identifiant;
    
    /**
     * Titre du panorama affiché dans l'interface utilisateur.
     */
    private String titre;
    
    /**
     * Description détaillée du panorama.
     * Peut contenir du texte formaté HTML.
     */
    private String description;
    
    /**
     * Chemin vers l'image équirectangulaire du panorama.
     * L'image doit être au format JPEG ou PNG avec un ratio 2:1.
     */
    private String cheminImage;
    
    /**
     * Image chargée en mémoire.
     * Null si l'image n'a pas encore été chargée.
     */
    private Image image;
    
    /**
     * Liste des hotspots associés à ce panorama.
     * Les hotspots permettent la navigation et l'interaction.
     */
    private List<HotSpot> hotspots;
    
    /**
     * Coordonnées géographiques du panorama.
     * Null si le panorama n'a pas de coordonnées GPS.
     */
    private CoordonneesGeographiques coordonnees;
    
    /**
     * Angle de vue horizontal initial en degrés (0-360).
     * 0° = Nord, 90° = Est, 180° = Sud, 270° = Ouest.
     */
    private double angleVueInitial;
    
    /**
     * Angle d'inclinaison initial en degrés (-90 à +90).
     * 0° = Horizon, +90° = Zénith, -90° = Nadir.
     */
    private double angleInclinaisonInitial;
    
    /**
     * Niveau de zoom initial (0.0 à 100.0).
     * Valeur par défaut : 70.0 (champ de vue standard).
     */
    private double zoomInitial;
    
    /**
     * Indique si le panorama est le point de départ de la visite.
     */
    private boolean estPointDepart;
    
    /**
     * Constructeur par défaut.
     * Crée un panorama vide avec des valeurs par défaut.
     * 
     * <p>Les valeurs par défaut sont :</p>
     * <ul>
     *   <li>Identifiant : UUID généré automatiquement</li>
     *   <li>Angle de vue : 0° (Nord)</li>
     *   <li>Inclinaison : 0° (Horizon)</li>
     *   <li>Zoom : 70.0</li>
     *   <li>Point de départ : false</li>
     * </ul>
     */
    public Panoramique() {
        this.identifiant = java.util.UUID.randomUUID().toString();
        this.hotspots = new ArrayList<>();
        this.angleVueInitial = 0.0;
        this.angleInclinaisonInitial = 0.0;
        this.zoomInitial = 70.0;
        this.estPointDepart = false;
    }
    
    /**
     * Constructeur avec identifiant.
     * 
     * @param identifiant Identifiant unique du panorama
     * @throws IllegalArgumentException Si l'identifiant est null ou vide
     */
    public Panoramique(String identifiant) {
        this();
        if (identifiant == null || identifiant.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide");
        }
        this.identifiant = identifiant;
    }
    
    /**
     * Charge l'image du panorama depuis le chemin spécifié.
     * 
     * Cette méthode charge l'image en mémoire et valide son format
     * (ratio 2:1 attendu pour une équirectangulaire).
     * 
     * @param chemin Chemin vers l'image (relatif ou absolu)
     * @throws IllegalArgumentException Si le chemin est null ou vide
     * @throws java.io.IOException Si l'image ne peut pas être chargée
     * @see #getImage()
     * @see #setCheminImage(String)
     */
    public void chargerImage(String chemin) throws java.io.IOException {
        if (chemin == null || chemin.trim().isEmpty()) {
            throw new IllegalArgumentException("Le chemin de l'image ne peut pas être vide");
        }
        
        java.io.File fichier = new java.io.File(chemin);
        if (!fichier.exists()) {
            throw new java.io.IOException("L'image n'existe pas : " + chemin);
        }
        
        this.cheminImage = chemin;
        this.image = new Image(fichier.toURI().toString());
        
        // Validation du ratio
        double ratio = this.image.getWidth() / this.image.getHeight();
        if (Math.abs(ratio - 2.0) > 0.1) {
            System.err.println("Avertissement : l'image n'a pas un ratio 2:1 (ratio actuel : " + ratio + ")");
        }
    }
    
    /**
     * Ajoute un hotspot au panorama.
     * 
     * Le hotspot est ajouté à la fin de la liste. L'identifiant du hotspot
     * doit être unique parmi tous les hotspots du panorama.
     * 
     * @param hotspot Le hotspot à ajouter
     * @return true si l'ajout a réussi, false si un hotspot avec le même ID existe déjà
     * @throws NullPointerException Si hotspot est null
     * @see #supprimerHotspot(String)
     * @see #rechercherHotspot(String)
     */
    public boolean ajouterHotspot(HotSpot hotspot) {
        if (hotspot == null) {
            throw new NullPointerException("Le hotspot ne peut pas être null");
        }
        
        // Vérifier l'unicité de l'identifiant
        for (HotSpot h : hotspots) {
            if (h.getIdentifiant().equals(hotspot.getIdentifiant())) {
                return false;
            }
        }
        
        return hotspots.add(hotspot);
    }
    
    /**
     * Supprime un hotspot par son identifiant.
     * 
     * @param identifiantHotspot Identifiant du hotspot à supprimer
     * @return true si le hotspot a été supprimé, false s'il n'existe pas
     * @see #ajouterHotspot(HotSpot)
     */
    public boolean supprimerHotspot(String identifiantHotspot) {
        return hotspots.removeIf(h -> h.getIdentifiant().equals(identifiantHotspot));
    }
    
    /**
     * Recherche un hotspot par son identifiant.
     * 
     * @param identifiantHotspot Identifiant du hotspot recherché
     * @return Le hotspot trouvé, ou null si aucun hotspot ne correspond
     */
    public HotSpot rechercherHotspot(String identifiantHotspot) {
        for (HotSpot h : hotspots) {
            if (h.getIdentifiant().equals(identifiantHotspot)) {
                return h;
            }
        }
        return null;
    }
    
    /**
     * Définit les coordonnées géographiques du panorama.
     * 
     * Ces coordonnées sont utilisées pour positionner le panorama sur une carte
     * et permettre la géolocalisation.
     * 
     * @param latitude Latitude en degrés décimaux (-90 à +90)
     * @param longitude Longitude en degrés décimaux (-180 à +180)
     * @throws IllegalArgumentException Si les coordonnées sont invalides
     * @see CoordonneesGeographiques
     */
    public void setCoordonnees(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude invalide : " + latitude);
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude invalide : " + longitude);
        }
        this.coordonnees = new CoordonneesGeographiques(latitude, longitude);
    }
    
    /**
     * Définit la vue initiale du panorama.
     * 
     * Cette méthode configure tous les paramètres de vue en une seule fois.
     * 
     * @param angle Angle horizontal en degrés (0-360)
     * @param inclinaison Angle vertical en degrés (-90 à +90)
     * @param zoom Niveau de zoom (0-100)
     * @throws IllegalArgumentException Si les valeurs sont hors limites
     */
    public void setVueInitiale(double angle, double inclinaison, double zoom) {
        if (angle < 0 || angle > 360) {
            throw new IllegalArgumentException("Angle invalide : " + angle);
        }
        if (inclinaison < -90 || inclinaison > 90) {
            throw new IllegalArgumentException("Inclinaison invalide : " + inclinaison);
        }
        if (zoom < 0 || zoom > 100) {
            throw new IllegalArgumentException("Zoom invalide : " + zoom);
        }
        
        this.angleVueInitial = angle;
        this.angleInclinaisonInitial = inclinaison;
        this.zoomInitial = zoom;
    }
    
    /**
     * Valide les données du panorama.
     * 
     * Vérifie que toutes les données obligatoires sont présentes et cohérentes.
     * 
     * @return Liste des erreurs de validation (vide si tout est valide)
     */
    public List<String> valider() {
        List<String> erreurs = new ArrayList<>();
        
        if (identifiant == null || identifiant.trim().isEmpty()) {
            erreurs.add("Identifiant manquant");
        }
        
        if (titre == null || titre.trim().isEmpty()) {
            erreurs.add("Titre manquant");
        }
        
        if (cheminImage == null || cheminImage.trim().isEmpty()) {
            erreurs.add("Chemin de l'image manquant");
        } else {
            java.io.File fichier = new java.io.File(cheminImage);
            if (!fichier.exists()) {
                erreurs.add("L'image n'existe pas : " + cheminImage);
            }
        }
        
        return erreurs;
    }
    
    // ==================== Getters et Setters ====================
    
    /**
     * Récupère l'identifiant unique du panorama.
     * 
     * @return L'identifiant du panorama
     */
    public String getIdentifiant() {
        return identifiant;
    }
    
    /**
     * Définit l'identifiant du panorama.
     * 
     * @param identifiant Le nouvel identifiant
     * @throws IllegalArgumentException Si l'identifiant est null ou vide
     */
    public void setIdentifiant(String identifiant) {
        if (identifiant == null || identifiant.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide");
        }
        this.identifiant = identifiant;
    }
    
    /**
     * Récupère le titre du panorama.
     * 
     * @return Le titre du panorama
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * Définit le titre du panorama.
     * 
     * @param titre Le nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    /**
     * Récupère la description du panorama.
     * 
     * @return La description (peut contenir du HTML)
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Définit la description du panorama.
     * 
     * @param description La nouvelle description (peut contenir du HTML)
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Récupère le chemin de l'image du panorama.
     * 
     * @return Le chemin de l'image
     */
    public String getCheminImage() {
        return cheminImage;
    }
    
    /**
     * Définit le chemin de l'image du panorama.
     * 
     * Cette méthode ne charge pas l'image. Utilisez {@link #chargerImage(String)}
     * pour charger l'image en mémoire.
     * 
     * @param cheminImage Le nouveau chemin
     * @see #chargerImage(String)
     */
    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }
    
    /**
     * Récupère l'image chargée en mémoire.
     * 
     * @return L'image, ou null si elle n'a pas été chargée
     * @see #chargerImage(String)
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Récupère la liste des hotspots.
     * 
     * @return Liste non modifiable des hotspots
     */
    public List<HotSpot> getHotspots() {
        return new ArrayList<>(hotspots);
    }
    
    /**
     * Récupère les coordonnées géographiques.
     * 
     * @return Les coordonnées, ou null si non définies
     */
    public CoordonneesGeographiques getCoordonnees() {
        return coordonnees;
    }
    
    /**
     * Définit les coordonnées géographiques.
     * 
     * @param coordonnees Les nouvelles coordonnées
     */
    public void setCoordonnees(CoordonneesGeographiques coordonnees) {
        this.coordonnees = coordonnees;
    }
    
    /**
     * Récupère l'angle de vue initial.
     * 
     * @return Angle en degrés (0-360)
     */
    public double getAngleVueInitial() {
        return angleVueInitial;
    }
    
    /**
     * Définit l'angle de vue initial.
     * 
     * @param angleVueInitial Angle en degrés (0-360)
     * @throws IllegalArgumentException Si l'angle est hors limites
     */
    public void setAngleVueInitial(double angleVueInitial) {
        if (angleVueInitial < 0 || angleVueInitial > 360) {
            throw new IllegalArgumentException("Angle invalide : " + angleVueInitial);
        }
        this.angleVueInitial = angleVueInitial;
    }
    
    /**
     * Récupère l'angle d'inclinaison initial.
     * 
     * @return Angle en degrés (-90 à +90)
     */
    public double getAngleInclinaisonInitial() {
        return angleInclinaisonInitial;
    }
    
    /**
     * Définit l'angle d'inclinaison initial.
     * 
     * @param angleInclinaisonInitial Angle en degrés (-90 à +90)
     * @throws IllegalArgumentException Si l'angle est hors limites
     */
    public void setAngleInclinaisonInitial(double angleInclinaisonInitial) {
        if (angleInclinaisonInitial < -90 || angleInclinaisonInitial > 90) {
            throw new IllegalArgumentException("Inclinaison invalide : " + angleInclinaisonInitial);
        }
        this.angleInclinaisonInitial = angleInclinaisonInitial;
    }
    
    /**
     * Récupère le niveau de zoom initial.
     * 
     * @return Zoom (0-100)
     */
    public double getZoomInitial() {
        return zoomInitial;
    }
    
    /**
     * Définit le niveau de zoom initial.
     * 
     * @param zoomInitial Zoom (0-100)
     * @throws IllegalArgumentException Si le zoom est hors limites
     */
    public void setZoomInitial(double zoomInitial) {
        if (zoomInitial < 0 || zoomInitial > 100) {
            throw new IllegalArgumentException("Zoom invalide : " + zoomInitial);
        }
        this.zoomInitial = zoomInitial;
    }
    
    /**
     * Indique si ce panorama est le point de départ.
     * 
     * @return true si c'est le point de départ
     */
    public boolean isEstPointDepart() {
        return estPointDepart;
    }
    
    /**
     * Définit si ce panorama est le point de départ.
     * 
     * @param estPointDepart true pour en faire le point de départ
     */
    public void setEstPointDepart(boolean estPointDepart) {
        this.estPointDepart = estPointDepart;
    }
    
    /**
     * Représentation textuelle du panorama.
     * 
     * @return Chaîne décrivant le panorama
     */
    @Override
    public String toString() {
        return String.format("Panoramique[id=%s, titre=%s, hotspots=%d]",
                identifiant, titre, hotspots.size());
    }
}
```

## Points clés de la documentation

### 1. En-tête de classe complet
- Description claire et concise
- Exemple d'utilisation avec `@code`
- Tags `@author`, `@version`, `@since`
- Références croisées avec `@see`

### 2. Documentation des attributs
- Chaque attribut privé documenté
- Explication de son rôle et de ses contraintes

### 3. Documentation des constructeurs
- Description de l'initialisation
- Liste des valeurs par défaut
- Exceptions possibles avec `@throws`

### 4. Documentation des méthodes
- Description fonctionnelle
- Paramètres avec `@param`
- Valeur de retour avec `@return`
- Exceptions avec `@throws`
- Références croisées avec `@see`
- Exemples quand pertinent

### 5. Validation et robustesse
- Vérification des paramètres
- Exceptions explicites
- Messages d'erreur clairs

### 6. Getters/Setters documentés
- Description brève mais précise
- Contraintes sur les valeurs
- Références aux méthodes associées

## Prochaines étapes

1. **Documenter les classes principales** :
   - `EditeurPanovisu` (classe principale)
   - `HotSpot` et ses sous-classes
   - `NavigateurPanoramique`
   - Contrôleurs JavaFX

2. **Documenter les utilitaires** :
   - `CoordonneesGeographiques`
   - `ReadWriteImage`
   - `TextUtils`

3. **Documenter le code JavaScript** :
   - `panovisu.js` avec JSDoc
   - Fonctions d'initialisation
   - Gestion des événements

4. **Enrichir progressivement** :
   - Ajouter des `@example` aux méthodes complexes
   - Documenter les cas limites
   - Ajouter des `@todo` pour les améliorations futures

## Régénération de la documentation

Après chaque ajout de documentation :

```powershell
# Depuis la racine du projet
.\generate-doc.ps1 -Open

# Ou depuis doc/doxygen
cd doc\doxygen
doxygen Doxyfile
```

La documentation sera mise à jour automatiquement !
