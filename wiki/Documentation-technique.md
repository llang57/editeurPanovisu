# 🔧 Documentation technique

Documentation pour les développeurs souhaitant contribuer à PanoVisu ou comprendre son architecture.

## Table des matières

- [Architecture du projet](#architecture-du-projet)
- [Organisation du code](#organisation-du-code)
- [Technologies utilisées](#technologies-utilisées)
- [Cycle de développement](#cycle-de-développement)
- [Standards de code](#standards-de-code)
- [Contribution](#contribution)
- [API Reference](#api-reference)

## Architecture du projet

PanoVisu est composé de deux parties principales :

### 1. Éditeur (Java/JavaFX)

**Rôle** : Interface de création et édition de visites virtuelles

**Stack technique** :
- Java 25 (OpenJDK)
- JavaFX 19 (interface graphique)
- Maven (gestion de dépendances)

**Fonctionnalités** :
- Importation de panoramiques (equirectangulaires, cubemap)
- Gestion de projets multi-panoramiques
- Éditeur WYSIWYG de hotspots
- Configuration de l'interface utilisateur
- Export vers visualiseur HTML5

### 2. Visualiseur (HTML5/JavaScript/WebGL)

**Rôle** : Affichage des visites virtuelles dans le navigateur

**Stack technique** :
- HTML5
- JavaScript (ES6+)
- WebGL (Three.js)
- OpenLayers (cartographie)

**Fonctionnalités** :
- Affichage panoramique 360° (WebGL)
- Navigation entre panoramiques
- Hotspots interactifs
- Plan interactif
- Cartographie (OpenStreetMap, Google Maps, Bing Maps)
- Diaporama automatique

## Organisation du code

### Structure des répertoires

```
editeurPanovisu/
│
├── src/
│   ├── editeurpanovisu/        # Code source Java
│   │   ├── controller/         # Contrôleurs JavaFX
│   │   ├── model/              # Modèles de données
│   │   ├── view/               # Vues FXML
│   │   ├── util/               # Utilitaires
│   │   └── Main.java           # Point d'entrée
│   │
│   └── test/                   # Tests unitaires
│
├── panovisu/                   # Visualiseur HTML5
│   ├── panovisu.js             # Code principal
│   ├── panovisuInit.js         # Initialisation
│   ├── libs/                   # Bibliothèques tierces
│   │   ├── three.js
│   │   ├── openlayers/
│   │   └── jquery/
│   ├── css/                    # Styles
│   └── images/                 # Assets
│
├── theme/                      # Thèmes visuels
│   ├── barreNavigation/
│   ├── boussoles/
│   ├── hotspots/
│   └── interface/
│
├── doc/                        # Documentation
│   ├── architecture/           # Diagrammes
│   ├── guides/                 # Guides développeur
│   └── README.md
│
└── pom.xml                     # Configuration Maven
```

### Modules principaux

#### Éditeur Java

| Package | Responsabilité |
|---------|---------------|
| `controller` | Contrôleurs MVC pour l'interface |
| `model` | Classes métier (Panoramique, Hotspot, Project) |
| `view` | Fichiers FXML (layouts JavaFX) |
| `util` | Utilitaires (XML, fichiers, images) |
| `server` | LocalHTTPServer (serveur web intégré) |

#### Visualiseur JavaScript

| Fichier | Responsabilité |
|---------|---------------|
| `panovisu.js` | Moteur principal, rendu WebGL |
| `panovisuInit.js` | Configuration, chargement XML |
| `libs/three.js` | Bibliothèque 3D WebGL |
| `libs/openlayers/` | Cartographie interactive |

## Technologies utilisées

### Backend (Éditeur)

#### Java & JavaFX

- **Java 25** : Version LTS récente avec améliorations de performance
- **JavaFX 19** : Framework d'interface graphique moderne
  - FXML pour les layouts
  - CSS pour le styling
  - WebView pour l'intégration HTML

#### Bibliothèques Maven

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>19</version>
    </dependency>
    
    <!-- JSON -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20231013</version>
    </dependency>
    
    <!-- XML -->
    <dependency>
        <groupId>org.jdom</groupId>
        <artifactId>jdom2</artifactId>
        <version>2.0.6.1</version>
    </dependency>
    
    <!-- Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>
</dependencies>
```

### Frontend (Visualiseur)

#### JavaScript & WebGL

- **Three.js** : Rendu 3D WebGL
  - PanoramaSphere : Géométrie sphérique
  - WebGLRenderer : Moteur de rendu
  - OrbitControls : Contrôles de caméra

- **OpenLayers** : Cartographie interactive
  - TileLayer : Tuiles OpenStreetMap
  - VectorLayer : Marqueurs et polygones
  - Interactions : Zoom, pan, popup

#### Compatibilité navigateurs

| Navigateur | Version minimale | Notes |
|------------|-----------------|-------|
| Chrome | 90+ | Recommandé |
| Firefox | 88+ | Support complet |
| Safari | 15+ | WebGL peut nécessiter activation |
| Edge | 90+ | Basé sur Chromium |
| IE | ❌ Non supporté | Trop ancien |

## Cycle de développement

### Configuration de l'environnement

1. **Prérequis** :
   ```bash
   java -version  # Java 25
   mvn -version   # Maven 3.8+
   git --version  # Git 2.x
   ```

2. **Clone du dépôt** :
   ```bash
   git clone https://github.com/llang57/editeurPanovisu.git
   cd editeurPanovisu
   ```

3. **Import dans IDE** :
   - **IntelliJ IDEA** : Ouvrir `pom.xml` comme projet Maven
   - **Eclipse** : Import → Maven → Existing Maven Project
   - **VS Code** : Ouvrir le dossier avec l'extension Java

### Build et exécution

#### Compilation

```bash
# Build complet
mvn clean package

# Build rapide (sans tests)
mvn clean package -DskipTests

# Build avec profil debug
mvn clean package -P debug
```

#### Lancement

```bash
# Via Maven
mvn javafx:run

# Via JAR
java -jar target/editeurPanovisu.jar

# Avec options de mémoire
java -Xmx4G -jar target/editeurPanovisu.jar
```

#### Tests

```bash
# Tous les tests
mvn test

# Test spécifique
mvn test -Dtest=HotspotTest

# Tests avec couverture (JaCoCo)
mvn clean test jacoco:report
```

### Workflow Git

#### Branches

- `master` : Version stable, déployée
- `develop` : Développement en cours
- `feature/xxx` : Nouvelles fonctionnalités
- `bugfix/xxx` : Corrections de bugs
- `release/x.x.x` : Préparation de releases

#### Commits

**Format** : `type(scope): message`

```bash
# Exemples
git commit -m "feat(hotspot): Ajout hotspots vidéo HTML5"
git commit -m "fix(export): Correction encodage UTF-8"
git commit -m "docs(wiki): Mise à jour guide installation"
```

**Types** :
- `feat` : Nouvelle fonctionnalité
- `fix` : Correction de bug
- `docs` : Documentation
- `style` : Formatage (sans changement de code)
- `refactor` : Refactoring
- `test` : Ajout/modification de tests
- `chore` : Tâches de maintenance

#### Pull Requests

1. **Créer une branche** :
   ```bash
   git checkout -b feature/ma-fonctionnalite
   ```

2. **Développer et commiter** :
   ```bash
   git add .
   git commit -m "feat: Ma nouvelle fonctionnalité"
   ```

3. **Pusher** :
   ```bash
   git push origin feature/ma-fonctionnalite
   ```

4. **Créer une PR** sur GitHub avec :
   - Description claire
   - Captures d'écran (si UI)
   - Tests ajoutés
   - Documentation mise à jour

## Standards de code

### Java

#### Conventions de nommage

```java
// Classes : PascalCase
public class PanoramiqueController { }

// Méthodes : camelCase
public void ajouterHotspot() { }

// Variables : camelCase
private String nomPanoramique;

// Constantes : SNAKE_CASE
private static final int MAX_HOTSPOTS = 100;
```

#### Javadoc

```java
/**
 * Ajoute un hotspot au panoramique.
 * 
 * @param hotspot Le hotspot à ajouter
 * @param position Position dans la liste (null pour fin)
 * @return true si l'ajout a réussi
 * @throws IllegalArgumentException si hotspot est null
 */
public boolean ajouterHotspot(Hotspot hotspot, Integer position) {
    // ...
}
```

#### Bonnes pratiques

- ✅ Utiliser les streams Java quand c'est pertinent
- ✅ Try-with-resources pour les I/O
- ✅ Préférer composition à héritage
- ✅ Immuabilité quand possible (`final`)
- ✅ Validation des entrées

```java
// Exemple : Validation et immutabilité
public final class Hotspot {
    private final String id;
    private final Point2D position;
    
    public Hotspot(String id, Point2D position) {
        this.id = Objects.requireNonNull(id, "ID ne peut pas être null");
        this.position = Objects.requireNonNull(position, "Position ne peut pas être null");
    }
    
    // Getters uniquement (pas de setters)
    public String getId() { return id; }
    public Point2D getPosition() { return position; }
}
```

### JavaScript

#### Style

```javascript
// Utiliser const/let (jamais var)
const HOTSPOT_SIZE = 50;
let currentPano = null;

// Arrow functions
const loadPanorama = (id) => {
    return panoramas.find(p => p.id === id);
};

// Destructuring
const { x, y, z } = camera.position;

// Template literals
console.log(`Chargement du panoramique ${pano.name}`);
```

#### Documentation JSDoc

```javascript
/**
 * Charge un panoramique et l'affiche.
 * 
 * @param {string} panoId - Identifiant du panoramique
 * @param {Object} [options] - Options de chargement
 * @param {number} [options.fov=70] - Champ de vision initial
 * @param {boolean} [options.animate=true] - Animation de transition
 * @returns {Promise<Panoramique>} Le panoramique chargé
 */
async function loadPanorama(panoId, options = {}) {
    // ...
}
```

## Contribution

### Comment contribuer

1. **Fork** le projet sur GitHub
2. **Clone** votre fork localement
3. **Créer une branche** pour votre fonctionnalité
4. **Développer** en suivant les standards
5. **Tester** votre code
6. **Commiter** avec des messages clairs
7. **Pusher** vers votre fork
8. **Créer une Pull Request**

### Checklist avant PR

- [ ] Code compile sans erreurs
- [ ] Tests passent (`mvn test`)
- [ ] Pas d'avertissements critiques
- [ ] Code formaté selon les standards
- [ ] Javadoc/JSDoc à jour
- [ ] Documentation utilisateur mise à jour (si nécessaire)
- [ ] CHANGELOG.md mis à jour
- [ ] Captures d'écran ajoutées (si changements UI)

### Domaines de contribution

#### Fonctionnalités prioritaires

- [ ] Support de nouveaux formats d'images (HDR, WebP)
- [ ] Amélioration de l'éditeur WYSIWYG
- [ ] Plugin system pour extensions
- [ ] Export vers nouveaux formats (A-Frame, Krpano)
- [ ] Internationalisation complète (i18n)

#### Bugs connus

Consultez les [Issues GitHub](https://github.com/llang57/editeurPanovisu/issues) avec les labels :
- `good first issue` : Bons points d'entrée pour débutants
- `help wanted` : Aide bienvenue
- `bug` : Bugs confirmés

## API Reference

### API Java (Éditeur)

#### Classe `Panoramique`

```java
public class Panoramique {
    public String getId();
    public String getNom();
    public TypePanoramique getType();
    public List<Hotspot> getHotspots();
    
    public void ajouterHotspot(Hotspot hotspot);
    public void supprimerHotspot(String hotspotId);
    public Hotspot getHotspot(String id);
}
```

#### Classe `Hotspot`

```java
public class Hotspot {
    public enum Type { NAVIGATION, IMAGE, HTML, VIDEO }
    
    public String getId();
    public Type getType();
    public Point2D getPosition();
    public String getTooltip();
    public Map<String, Object> getProperties();
}
```

#### Interface `ProjectManager`

```java
public interface ProjectManager {
    void createProject(String name, File location);
    void openProject(File projectFile);
    void saveProject();
    void closeProject();
    
    Project getCurrentProject();
    void export(File destination, ExportOptions options);
}
```

### API JavaScript (Visualiseur)

#### Objet `PanoVisu`

```javascript
// Initialisation
const panovisu = new PanoVisu({
    container: 'pano-container',
    xmlFile: 'visite.xml',
    autoRotate: false
});

// Méthodes principales
panovisu.loadPanorama(panoId);
panovisu.addHotspot(hotspotData);
panovisu.setFOV(fov);
panovisu.setPanTilt(pan, tilt);
panovisu.enableAutoRotate(speed);
```

#### Événements

```javascript
// Chargement de panoramique
panovisu.on('panoramaLoaded', (pano) => {
    console.log('Panoramique chargé:', pano.id);
});

// Clic sur hotspot
panovisu.on('hotspotClick', (hotspot) => {
    console.log('Hotspot cliqué:', hotspot.id);
});

// Changement de vue
panovisu.on('viewChanged', ({ pan, tilt, fov }) => {
    console.log(`Vue: ${pan}°, ${tilt}°, FOV: ${fov}°`);
});
```

## Déploiement

### Génération de la documentation API

```bash
# Javadoc
mvn javadoc:javadoc

# Doxygen (documentation complète)
doxygen Doxyfile

# JSDoc
jsdoc panovisu/panovisu.js -d docs/jsdoc
```

### Construction de l'installeur Windows

```powershell
# Avec JPackage
.\build-installer.ps1
```

**Sortie** : `target/jpackage/EditeurPanovisu-[version].exe`

### Release GitHub

1. **Incrémenter la version** dans `pom.xml`
2. **Mettre à jour** `CHANGELOG.md`
3. **Créer un tag** :
   ```bash
   git tag -a v3.1.0 -m "Version 3.1.0 - LocalHTTPServer"
   git push origin v3.1.0
   ```
4. **Créer une release** sur GitHub :
   - Titre : `Version 3.1.0`
   - Description : Copier depuis CHANGELOG
   - Ajouter le JAR et l'installeur Windows

## Ressources développeur

- 📖 [API JavaDoc](https://llang57.github.io/editeurPanovisu/)
- 📐 [Diagrammes d'architecture](https://github.com/llang57/editeurPanovisu/tree/master/doc/architecture)
- 💬 [Discussions développeurs](https://github.com/llang57/editeurPanovisu/discussions/categories/development)
- 🐛 [Issues](https://github.com/llang57/editeurPanovisu/issues)
- 📝 [Project board](https://github.com/llang57/editeurPanovisu/projects)

## Licence

Ce projet est sous licence **GPL-3.0**. Voir le fichier [LICENSE](https://github.com/llang57/editeurPanovisu/blob/master/LICENSE) pour plus de détails.

Toute contribution est automatiquement sous la même licence.
