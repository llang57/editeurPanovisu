# Documentation Doxygen - EditeurPanovisu

Ce répertoire contient la configuration Doxygen pour générer la documentation technique du projet EditeurPanovisu.

## Prérequis

- **Doxygen** : Télécharger depuis [doxygen.nl](https://www.doxygen.nl/download.html)
- **Graphviz** (optionnel, pour les diagrammes) : [graphviz.org](https://graphviz.org/download/)

### Installation sur Windows

```powershell
# Avec Chocolatey
choco install doxygen.install graphviz

# Ou avec Scoop
scoop install doxygen graphviz
```

### Installation sur Linux

```bash
# Debian/Ubuntu
sudo apt-get install doxygen graphviz

# Fedora/RedHat
sudo dnf install doxygen graphviz
```

### Installation sur macOS

```bash
# Avec Homebrew
brew install doxygen graphviz
```

## Génération de la documentation

### Depuis le répertoire doxygen

```powershell
cd doc\doxygen
doxygen Doxyfile
```

### Depuis la racine du projet

```powershell
doxygen doc\doxygen\Doxyfile
```

## Consultation de la documentation

Une fois générée, ouvrez le fichier :

```
doc/doxygen/html/index.html
```

## Configuration

Le fichier `Doxyfile` est configuré pour :

- **Langage de sortie** : Français
- **Sources analysées** :
  - `src/editeurpanovisu/` (code Java)
  - `panovisu/` (code JavaScript, incluant `panovisu.js`)
- **Formats générés** : HTML uniquement (LaTeX désactivé par défaut)
- **Extraction** : Tous les membres (public, privé, package)
- **Exclusions** : 
  - Répertoires `test/` et `target/`
  - Fichiers JavaScript minifiés (`*.min.js`)
- **Images** : Incluses depuis `aide/images/` et `images/`
- **Page principale** : `README.md` du projet

## Enrichissement progressif

### Commentaires Javadoc pour Java

```java
/**
 * Description de la classe.
 * 
 * @author Votre Nom
 * @version 3.0.0
 * @since 1.0.0
 */
public class MaClasse {
    
    /**
     * Description de la méthode.
     * 
     * @param param1 Description du paramètre 1
     * @param param2 Description du paramètre 2
     * @return Description de la valeur de retour
     * @throws Exception Description de l'exception
     */
    public String maMethode(int param1, String param2) throws Exception {
        // ...
    }
}
```

### Commentaires JSDoc pour JavaScript

```javascript
/**
 * Description de la fonction.
 * 
 * @function
 * @param {string} param1 - Description du paramètre 1
 * @param {number} param2 - Description du paramètre 2
 * @returns {boolean} Description de la valeur de retour
 * @example
 * // Exemple d'utilisation
 * maFonction("test", 42);
 */
function maFonction(param1, param2) {
    // ...
}

/**
 * Classe représentant un point.
 * @class
 */
class Point {
    /**
     * Crée un point.
     * @constructor
     * @param {number} x - Coordonnée X
     * @param {number} y - Coordonnée Y
     */
    constructor(x, y) {
        /** @type {number} */
        this.x = x;
        /** @type {number} */
        this.y = y;
    }
}
```

## Tags Doxygen utiles

- `@brief` : Description courte
- `@details` : Description détaillée
- `@param` : Paramètre de fonction/méthode
- `@return` / `@returns` : Valeur de retour
- `@throws` / `@exception` : Exception levée
- `@see` : Référence croisée
- `@todo` : Tâche à faire
- `@bug` : Bug connu
- `@deprecated` : Élément obsolète
- `@since` : Version d'introduction
- `@author` : Auteur
- `@version` : Version
- `@example` : Exemple de code

## Personnalisation

Pour personnaliser la documentation :

1. **Modifier le logo** : Définir `PROJECT_LOGO` dans le Doxyfile
2. **Changer le thème** : Créer un CSS personnalisé avec `HTML_EXTRA_STYLESHEET`
3. **Activer les diagrammes** : Définir `HAVE_DOT = YES` (nécessite Graphviz)
4. **Générer le PDF** : Définir `GENERATE_LATEX = YES` puis compiler le LaTeX

## Exemple de script de génération

Créez un fichier `generate-doc.ps1` à la racine :

```powershell
# Génération de la documentation Doxygen
Write-Host "Génération de la documentation Doxygen..." -ForegroundColor Cyan

# Vérifier que Doxygen est installé
if (!(Get-Command doxygen -ErrorAction SilentlyContinue)) {
    Write-Host "Erreur : Doxygen n'est pas installé !" -ForegroundColor Red
    Write-Host "Installez-le avec : choco install doxygen.install" -ForegroundColor Yellow
    exit 1
}

# Générer la documentation
cd doc\doxygen
doxygen Doxyfile

# Ouvrir dans le navigateur
if ($?) {
    Write-Host "Documentation générée avec succès !" -ForegroundColor Green
    Write-Host "Ouverture dans le navigateur..." -ForegroundColor Cyan
    Start-Process "html\index.html"
} else {
    Write-Host "Erreur lors de la génération !" -ForegroundColor Red
    exit 1
}
```

## Fichiers générés (exclus du Git)

Les fichiers suivants sont générés et exclus du contrôle de version :

- `html/` : Documentation HTML
- `latex/` : Documentation LaTeX (si activée)
- `xml/` : Documentation XML (si activée)
- `man/` : Pages man (si activées)
- `rtf/` : Documentation RTF (si activée)
- `*.log` : Fichiers de log Doxygen

## Ressources

- [Documentation Doxygen](https://www.doxygen.nl/manual/index.html)
- [Guide JSDoc](https://jsdoc.app/)
- [Guide Javadoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)
