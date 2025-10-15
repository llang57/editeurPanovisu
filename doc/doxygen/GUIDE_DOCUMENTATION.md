# Guide de documentation du code - EditeurPanovisu

Ce guide explique comment documenter progressivement le code Java et JavaScript du projet avec les standards Javadoc/JSDoc pour Doxygen.

## Table des matières

1. [Documentation Java (Javadoc)](#documentation-java)
2. [Documentation JavaScript (JSDoc)](#documentation-javascript)
3. [Tags Doxygen communs](#tags-doxygen)
4. [Exemples concrets](#exemples-concrets)
5. [Bonnes pratiques](#bonnes-pratiques)

---

## Documentation Java

### Classe Java

```java
package editeurpanovisu.modele;

import java.util.List;

/**
 * Représente une visite virtuelle 360°.
 * 
 * Cette classe gère l'ensemble des panoramas, hotspots et paramètres
 * d'une visite virtuelle Panovisu.
 * 
 * @author Louis Lang
 * @version 3.0.0
 * @since 1.0.0
 * @see Panorama
 * @see Hotspot
 */
public class Visite {
    
    /**
     * Titre de la visite.
     */
    private String titre;
    
    /**
     * Liste des panoramas de la visite.
     */
    private List<Panorama> panoramas;
    
    /**
     * Crée une nouvelle visite vide.
     */
    public Visite() {
        this.panoramas = new ArrayList<>();
    }
    
    /**
     * Crée une nouvelle visite avec un titre.
     * 
     * @param titre Le titre de la visite
     * @throws IllegalArgumentException Si le titre est null ou vide
     */
    public Visite(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        this.titre = titre;
        this.panoramas = new ArrayList<>();
    }
    
    /**
     * Ajoute un panorama à la visite.
     * 
     * @param panorama Le panorama à ajouter
     * @return true si l'ajout a réussi, false sinon
     * @throws NullPointerException Si panorama est null
     * @see #supprimerPanorama(Panorama)
     */
    public boolean ajouterPanorama(Panorama panorama) {
        if (panorama == null) {
            throw new NullPointerException("Le panorama ne peut pas être null");
        }
        return this.panoramas.add(panorama);
    }
    
    /**
     * Recherche un panorama par son identifiant.
     * 
     * @param id L'identifiant du panorama recherché
     * @return Le panorama trouvé, ou null si aucun panorama ne correspond
     */
    public Panorama rechercherPanorama(String id) {
        for (Panorama p : panoramas) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Calcule le nombre total de hotspots dans la visite.
     * 
     * @return Le nombre total de hotspots
     * @deprecated Utiliser {@link #getStatistiques()} à la place
     */
    @Deprecated
    public int compterHotspots() {
        int total = 0;
        for (Panorama p : panoramas) {
            total += p.getHotspots().size();
        }
        return total;
    }
    
    /**
     * Récupère le titre de la visite.
     * 
     * @return Le titre de la visite
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * Définit le titre de la visite.
     * 
     * @param titre Le nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }
}
```

### Interface Java

```java
package editeurpanovisu.controleur;

/**
 * Interface pour les contrôleurs gérant les événements utilisateur.
 * 
 * @author Louis Lang
 * @version 3.0.0
 * @since 2.0.0
 */
public interface IControleur {
    
    /**
     * Initialise le contrôleur.
     * 
     * Cette méthode est appelée une seule fois après la création du contrôleur.
     * 
     * @throws Exception Si l'initialisation échoue
     */
    void initialiser() throws Exception;
    
    /**
     * Rafraîchit l'affichage du contrôleur.
     */
    void rafraichir();
}
```

### Enumération Java

```java
package editeurpanovisu.modele;

/**
 * Types de hotspots disponibles dans Panovisu.
 * 
 * @author Louis Lang
 * @version 3.0.0
 */
public enum TypeHotspot {
    
    /**
     * Hotspot de navigation vers un autre panorama.
     */
    NAVIGATION,
    
    /**
     * Hotspot d'information avec texte et/ou image.
     */
    INFORMATION,
    
    /**
     * Hotspot de lien externe (URL).
     */
    LIEN_EXTERNE,
    
    /**
     * Hotspot vidéo (YouTube, Vimeo, etc.).
     */
    VIDEO,
    
    /**
     * Hotspot audio.
     */
    AUDIO;
    
    /**
     * Retourne une description lisible du type.
     * 
     * @return La description du type de hotspot
     */
    public String getDescription() {
        switch (this) {
            case NAVIGATION:
                return "Navigation";
            case INFORMATION:
                return "Information";
            case LIEN_EXTERNE:
                return "Lien externe";
            case VIDEO:
                return "Vidéo";
            case AUDIO:
                return "Audio";
            default:
                return "Inconnu";
        }
    }
}
```

---

## Documentation JavaScript

### Classe JavaScript (ES6)

```javascript
/**
 * Gestion des panoramas 360° avec Panovisu.
 * 
 * Cette classe initialise et contrôle l'affichage des panoramas sphériques
 * avec Three.js et gère les interactions utilisateur.
 * 
 * @class
 * @author Louis Lang
 * @version 3.0.0
 * @since 1.0.0
 */
class Panovisu {
    
    /**
     * Crée une instance Panovisu.
     * 
     * @constructor
     * @param {string} conteneurId - L'ID du conteneur HTML
     * @param {Object} options - Options de configuration
     * @param {boolean} [options.autoRotation=false] - Activer la rotation automatique
     * @param {number} [options.vitesseRotation=0.1] - Vitesse de rotation
     * @param {boolean} [options.controles=true] - Activer les contrôles utilisateur
     * @throws {Error} Si le conteneur n'existe pas
     * @example
     * const pano = new Panovisu('conteneur', {
     *     autoRotation: true,
     *     vitesseRotation: 0.2
     * });
     */
    constructor(conteneurId, options = {}) {
        /** @type {HTMLElement} */
        this.conteneur = document.getElementById(conteneurId);
        
        if (!this.conteneur) {
            throw new Error(`Conteneur ${conteneurId} introuvable`);
        }
        
        /** @type {Object} */
        this.options = Object.assign({
            autoRotation: false,
            vitesseRotation: 0.1,
            controles: true
        }, options);
        
        /** @type {THREE.Scene} */
        this.scene = null;
        
        /** @type {THREE.Camera} */
        this.camera = null;
        
        /** @type {THREE.WebGLRenderer} */
        this.renderer = null;
        
        this.initialiser();
    }
    
    /**
     * Initialise la scène Three.js.
     * 
     * @private
     * @returns {void}
     */
    initialiser() {
        this.creerScene();
        this.creerCamera();
        this.creerRenderer();
        this.demarrerAnimation();
    }
    
    /**
     * Charge un panorama depuis une URL.
     * 
     * @param {string} url - URL de l'image panoramique
     * @param {Function} [callback] - Fonction appelée après le chargement
     * @returns {Promise<void>} Promise résolue quand le panorama est chargé
     * @throws {Error} Si l'URL est invalide ou le chargement échoue
     * @example
     * pano.chargerPanorama('panoramas/entree.jpg')
     *     .then(() => console.log('Panorama chargé'))
     *     .catch(err => console.error(err));
     */
    async chargerPanorama(url, callback) {
        if (!url || typeof url !== 'string') {
            throw new Error('URL invalide');
        }
        
        return new Promise((resolve, reject) => {
            const loader = new THREE.TextureLoader();
            loader.load(
                url,
                (texture) => {
                    this.appliquerTexture(texture);
                    if (callback) callback();
                    resolve();
                },
                undefined,
                (error) => reject(error)
            );
        });
    }
    
    /**
     * Ajoute un hotspot au panorama.
     * 
     * @param {Object} hotspot - Données du hotspot
     * @param {string} hotspot.id - Identifiant unique
     * @param {number} hotspot.longitude - Longitude (en degrés)
     * @param {number} hotspot.latitude - Latitude (en degrés)
     * @param {string} hotspot.type - Type de hotspot
     * @param {string} [hotspot.titre] - Titre du hotspot
     * @param {string} [hotspot.description] - Description du hotspot
     * @returns {THREE.Object3D} L'objet 3D créé pour le hotspot
     * @see #supprimerHotspot
     */
    ajouterHotspot(hotspot) {
        const mesh = this.creerMeshHotspot(hotspot);
        mesh.userData = hotspot;
        this.scene.add(mesh);
        return mesh;
    }
    
    /**
     * Convertit des coordonnées sphériques en position 3D.
     * 
     * @private
     * @param {number} longitude - Longitude en degrés
     * @param {number} latitude - Latitude en degrés
     * @param {number} [rayon=500] - Rayon de la sphère
     * @returns {THREE.Vector3} Position 3D
     */
    spheriqueVersCartesien(longitude, latitude, rayon = 500) {
        const phi = THREE.MathUtils.degToRad(90 - latitude);
        const theta = THREE.MathUtils.degToRad(longitude);
        
        return new THREE.Vector3(
            rayon * Math.sin(phi) * Math.cos(theta),
            rayon * Math.cos(phi),
            rayon * Math.sin(phi) * Math.sin(theta)
        );
    }
}
```

### Fonctions JavaScript

```javascript
/**
 * Charge une visite depuis un fichier JSON.
 * 
 * @function
 * @async
 * @param {string} urlFichier - URL du fichier JSON
 * @returns {Promise<Object>} Les données de la visite
 * @throws {Error} Si le fichier n'existe pas ou est invalide
 * @example
 * chargerVisite('visites/musee.json')
 *     .then(visite => console.log(visite.titre))
 *     .catch(err => console.error('Erreur:', err));
 */
async function chargerVisite(urlFichier) {
    try {
        const response = await fetch(urlFichier);
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        throw new Error(`Impossible de charger ${urlFichier}: ${error.message}`);
    }
}

/**
 * Valide les coordonnées géographiques.
 * 
 * @function
 * @param {number} longitude - Longitude à valider
 * @param {number} latitude - Latitude à valider
 * @returns {boolean} true si les coordonnées sont valides
 * @example
 * if (validerCoordonnees(48.8566, 2.3522)) {
 *     console.log('Coordonnées de Paris valides');
 * }
 */
function validerCoordonnees(longitude, latitude) {
    return longitude >= -180 && longitude <= 180 &&
           latitude >= -90 && latitude <= 90;
}

/**
 * Formate une durée en secondes en format MM:SS.
 * 
 * @function
 * @param {number} secondes - Durée en secondes
 * @returns {string} Durée formatée
 * @example
 * formaterDuree(125); // Retourne "02:05"
 */
function formaterDuree(secondes) {
    const minutes = Math.floor(secondes / 60);
    const secs = Math.floor(secondes % 60);
    return `${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
}
```

---

## Tags Doxygen

### Tags principaux

| Tag | Description | Java | JavaScript |
|-----|-------------|------|------------|
| `@brief` | Description courte | ✓ | ✓ |
| `@param` | Paramètre de fonction | ✓ | ✓ |
| `@return` / `@returns` | Valeur de retour | ✓ | ✓ |
| `@throws` / `@exception` | Exception levée | ✓ | ✓ |
| `@see` | Référence croisée | ✓ | ✓ |
| `@since` | Version d'introduction | ✓ | ✓ |
| `@deprecated` | Élément obsolète | ✓ | ✓ |
| `@author` | Auteur | ✓ | ✓ |
| `@version` | Version | ✓ | ✓ |
| `@example` | Exemple de code | ✓ | ✓ |
| `@todo` | Tâche à faire | ✓ | ✓ |
| `@bug` | Bug connu | ✓ | ✓ |
| `@note` | Note importante | ✓ | ✓ |
| `@warning` | Avertissement | ✓ | ✓ |

### Tags JavaScript spécifiques

| Tag | Description |
|-----|-------------|
| `@class` | Marque une classe |
| `@constructor` | Marque un constructeur |
| `@function` | Marque une fonction |
| `@async` | Fonction asynchrone |
| `@private` | Membre privé |
| `@protected` | Membre protégé |
| `@type` | Type d'une variable |
| `@typedef` | Définition de type |
| `@callback` | Définition de callback |

---

## Exemples concrets

### Documentation d'un contrôleur JavaFX

```java
package editeurpanovisu.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Contrôleur pour l'édition des propriétés d'un hotspot.
 * 
 * Gère l'interface utilisateur permettant de modifier les attributs
 * d'un hotspot (position, titre, description, etc.).
 * 
 * @author Louis Lang
 * @version 3.0.0
 * @since 2.0.0
 */
public class HotspotController implements IControleur {
    
    /**
     * Champ de texte pour le titre du hotspot.
     */
    @FXML
    private TextField txtTitre;
    
    /**
     * Bouton de validation.
     */
    @FXML
    private Button btnValider;
    
    /**
     * Hotspot en cours d'édition.
     */
    private Hotspot hotspotCourant;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void initialiser() throws Exception {
        // Configuration des listeners
        configurerEvenements();
    }
    
    /**
     * Configure les événements de l'interface.
     * 
     * @private
     */
    private void configurerEvenements() {
        btnValider.setOnAction(e -> valider());
    }
    
    /**
     * Valide et enregistre les modifications.
     * 
     * @see #annuler()
     */
    @FXML
    private void valider() {
        if (validerChamps()) {
            sauvegarderModifications();
            fermerDialogue();
        }
    }
}
```

---

## Bonnes pratiques

### 1. Documentation progressive

Ne documentez pas tout d'un coup ! Priorités :

1. **Classes principales** : `Visite`, `Panorama`, `Hotspot`
2. **Interfaces publiques** : Méthodes publiques des classes
3. **Points d'entrée** : Constructeurs, méthodes d'initialisation
4. **Code complexe** : Algorithmes non triviaux
5. **Détails** : Méthodes privées, variables

### 2. Qualité vs quantité

**Bon :**
```java
/**
 * Calcule la distance entre deux panoramas en mètres.
 * 
 * @param p1 Premier panorama
 * @param p2 Deuxième panorama
 * @return Distance en mètres, ou -1 si les coordonnées sont invalides
 */
public double calculerDistance(Panorama p1, Panorama p2) {
    // ...
}
```

**Mauvais :**
```java
/**
 * Calcule.
 */
public double calculerDistance(Panorama p1, Panorama p2) {
    // ...
}
```

### 3. Cohérence

- Utilisez toujours la même langue (français)
- Suivez le même format pour les paramètres
- Documentez les exceptions possibles
- Ajoutez des exemples pour les cas complexes

### 4. Mise à jour

- Mettez à jour la documentation quand vous modifiez le code
- Marquez les éléments obsolètes avec `@deprecated`
- Ajoutez `@since` pour les nouvelles fonctionnalités

### 5. Exemples utiles

Ajoutez des exemples pour :
- Les méthodes avec plusieurs paramètres
- Les cas d'utilisation non évidents
- Les patterns de code recommandés

---

## Commandes rapides

```powershell
# Générer et ouvrir la documentation
.\generate-doc.ps1 -Open

# Nettoyer et régénérer
.\generate-doc.ps1 -Clean -Open

# Juste générer
.\generate-doc.ps1
```

---

## Ressources

- [Doxygen Manual](https://www.doxygen.nl/manual/index.html)
- [Javadoc Guide](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
- [JSDoc Guide](https://jsdoc.app/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html#s7-javadoc)
