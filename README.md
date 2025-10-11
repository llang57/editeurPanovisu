# 🌐 Éditeur PanoVisu

[![Version](https://img.shields.io/badge/version-3.0-blue.svg)](https://github.com/llang57/editeurPanovisu)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-19-green.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/license-Open%20Source-brightgreen.svg)](LICENSE)

> **Éditeur visuel pour la création de visites virtuelles et panoramiques 360° interactifs**

Créez facilement des visites virtuelles immersives en HTML5/WebGL sans aucune connaissance en programmation. PanoVisu combine puissance, simplicité et liberté pour offrir une solution complète et gratuite de création de visites virtuelles de qualité professionnelle.

## 📑 Table des matières

- [Qu'est-ce que PanoVisu ?](#quest-ce-que-panovisu-)
- [Fonctionnalités principales](#-fonctionnalités-principales)
- [Installation](#-installation)
- [Démarrage rapide](#-démarrage-rapide)
- [Technologies utilisées](#-technologies-utilisées)
- [Configuration des clés API](#-configuration-des-clés-api)
- [Compilation et développement](#-compilation-et-développement)
- [Documentation](#-documentation)
- [Compatibilité navigateurs](#-compatibilité-navigateurs)
- [Contribution](#-contribution)
- [Support et contact](#-support-et-contact)
- [Licence](#-licence)

## Qu'est-ce que PanoVisu ?

**PanoVisu** est un projet open source composé de deux éléments complémentaires :

1. **Un visualiseur HTML5/WebGL** - Technologie légère et moderne pour afficher des panoramiques 360° interactifs dans tous les navigateurs web
2. **Un éditeur visuel Java/JavaFX** - Application de bureau intuitive pour créer et configurer vos visites virtuelles

### 🎯 Pourquoi PanoVisu ?

- ✅ **100% gratuit et open source** - Aucune limitation, aucun filigrane
- ✅ **Multi-plateforme** - Windows, macOS, Linux
- ✅ **Sans serveur** - Générez des visites autonomes hébergeables partout
- ✅ **Technologie moderne** - HTML5, WebGL, Three.js
- ✅ **Extensible** - Code source ouvert, personnalisable à volonté
- ✅ **Professionnel** - Qualité comparable aux solutions commerciales

## 🚀 Fonctionnalités principales

### 📸 Types de panoramiques supportés
- Panoramiques **sphériques équirectangulaires** (standard 360°)
- Panoramiques en **faces de cube** (cubemap)
- Panoramiques **partiels** et **cylindriques**

### 🎨 Éléments interactifs
- **Hotspots de navigation** - Liens entre panoramiques
- **Hotspots d'images/photos** - Galeries photo intégrées
- **Hotspots HTML** - Contenu riche (vidéos, textes, liens externes)
- **Plans interactifs** - Carte 2D avec points d'intérêt et radar
- **Cartes géolocalisées** - OpenStreetMap, Google Maps, Bing Maps
- **Boussole dynamique** - Orientation en temps réel
- **Images de fond** - Splash screens, logos, bannières
- **Vignettes de navigation** - Aperçu visuel des panoramiques
- **Réseaux sociaux** - Partage Facebook, Twitter, email
- **Menu contextuel** - Actions personnalisables au clic droit

### 🎛️ Contrôles et navigation
- **Barres de navigation personnalisables** - Design adapté à votre charte graphique
- **Actionneurs JavaScript** - API pour contrôler la visite depuis votre code
- **Visite automatique** - Mode démo avec rotation automatique
- **Système de calques** - 10 niveaux pour organiser les éléments
- **Plein écran natif** - Immersion totale
- **Contrôles tactiles** - Support mobile et tablettes

### 🛠️ Éditeur graphique
- Interface intuitive **drag & drop**
- Prévisualisation en temps réel
- Gestion des transformations d'images (équirectangulaire ↔ cubemap)
- Éditeur HTML WYSIWYG pour les contenus riches
- Export en un clic vers HTML/XML

## 📥 Installation

### Prérequis

- **Java 25** (OpenJDK Temurin recommandé) - [Télécharger](https://adoptium.net/)
- **Maven 3.9+** (pour la compilation depuis les sources)
- **Système d'exploitation** : Windows 10/11, macOS 10.15+, Linux (toute distribution récente)

### Installation rapide

1. **Téléchargez la dernière version**
   ```bash
   git clone https://github.com/llang57/editeurPanovisu.git
   cd editeurPanovisu
   ```

2. **Compilez le projet**
   ```bash
   mvn clean package
   ```

3. **Lancez l'éditeur**
   ```bash
   mvn javafx:run
   ```

## 🎯 Démarrage rapide

### Créer votre première visite virtuelle

1. **Préparez vos images panoramiques**
   - Format recommandé : équirectangulaire (ratio 2:1)
   - Résolution conseillée : 8192×4096 pixels pour une qualité optimale
   - Outil recommandé : [Hugin](http://hugin.sourceforge.net/) (assemblage gratuit)

2. **Lancez l'éditeur PanoVisu**

3. **Créez un nouveau projet**
   - Fichier → Nouveau projet
   - Importez vos panoramiques
   - Placez-les sur un plan ou une carte

4. **Ajoutez de l'interactivité**
   - Créez des hotspots de navigation entre panoramiques
   - Ajoutez des informations contextuelles
   - Personnalisez l'interface

5. **Exportez et publiez**
   - Fichier → Exporter la visite
   - Copiez le dossier généré sur votre serveur web
   - Ouvrez `index.html` dans un navigateur

### Exemple de visite

Consultez les [exemples de visites](http://panovisu.fr/exemples) réalisées avec PanoVisu.

## 🔧 Technologies utilisées

### Frontend (Visualiseur)
- **HTML5** - Structure moderne
- **CSS3** - Styles et animations
- **JavaScript ES6+** - Logique interactive
- **Three.js** - Rendu WebGL 3D
- **OpenLayers 9** - Cartographie avancée
- **jQuery** - Manipulation DOM

### Backend (Éditeur)
- **Java 25** (OpenJDK Temurin)
- **JavaFX 19** - Interface graphique moderne
- **Maven 3.9** - Gestion des dépendances
- **Apache Commons** - Utilitaires
- **JAXB** - Serialisation XML

### Cartes et géolocalisation
- **OpenStreetMap** (par défaut, gratuit)
- **Google Maps API** (optionnel, nécessite une clé)
- **Bing Maps API** (optionnel, nécessite une clé)
- **Gluon Maps** - Alternative mobile

## 🔑 Configuration des clés API

Pour utiliser les services cartographiques commerciaux, configurez vos clés API :

1. **Copiez le fichier template**
   ```bash
   cp api-keys.properties.template api-keys.properties
   ```

2. **Éditez `api-keys.properties`**
   ```properties
   # Google Maps (optionnel)
   google.maps.api.key=VOTRE_CLE_GOOGLE_MAPS
   
   # Bing Maps (optionnel)
   bing.maps.api.key=VOTRE_CLE_BING_MAPS
   ```

3. **Obtenez vos clés**
   - [Google Maps Platform](https://developers.google.com/maps)
   - [Bing Maps Dev Center](https://www.bingmapsportal.com/)

⚠️ **Sécurité** : Le fichier `api-keys.properties` est ignoré par Git. Consultez [SECURITE_CLES_API.md](doc/SECURITE_CLES_API.md) pour plus d'informations.

## 🛠️ Compilation et développement

### Architecture du projet

```
editeurPanovisu/
├── src/                          # Code source Java
│   └── editeurpanovisu/         # Package principal
├── panovisu/                     # Visualiseur HTML5/JS
│   ├── panovisu.js              # Logique principale
│   ├── libs/                    # Bibliothèques (Three.js, etc.)
│   └── css/                     # Styles
├── theme/                        # Ressources visuelles
├── templates/                    # Modèles de projets
├── doc/                          # Documentation
├── pom.xml                       # Configuration Maven
├── build.num                     # Numéro de build auto-incrémenté
└── increment-build.ps1          # Script de versioning

```

### Commandes Maven

```bash
# Compilation complète
mvn clean compile

# Compilation + package JAR
mvn clean package

# Lancement de l'éditeur
mvn javafx:run

# Exécution des tests
mvn test

# Nettoyage du projet
mvn clean
```

### Système de versioning

Le projet utilise un **système d'auto-incrémentation** du numéro de build :

- **Format** : `3.0-b[numéro]` (ex: `3.0-b2039`)
- **Incrémentation automatique** à chaque compilation Maven
- **Script PowerShell** : `increment-build.ps1`
- **Documentation complète** : [VERSIONING.md](VERSIONING.md)

### Variables d'environnement

```bash
# Java 25 (requis)
JAVA_HOME=/path/to/java25

# Maven (optionnel si déjà dans PATH)
MAVEN_HOME=/path/to/maven
```

## 📚 Documentation

- **[VERSIONING.md](VERSIONING.md)** - Système de versioning et incrémentation automatique
- **[SECURITE_CLES_API.md](doc/SECURITE_CLES_API.md)** - Gestion sécurisée des clés API
- **[MIGRATION_JAVA25_PLAN.md](MIGRATION_JAVA25_PLAN.md)** - Guide de migration vers Java 25
- **[doc/travail/](doc/travail/)** - Journaux de développement et notes techniques

## 🌐 Compatibilité navigateurs

### Visualiseur (HTML5)

Le visualiseur PanoVisu fonctionne sur **tous les navigateurs modernes** supportant WebGL :

| Navigateur | Version minimale | WebGL | Notes |
|------------|------------------|-------|-------|
| Chrome | 90+ | ✅ Oui | Performance optimale |
| Firefox | 88+ | ✅ Oui | Performance optimale |
| Safari | 14+ | ✅ Oui | Excellent sur macOS/iOS |
| Edge | 90+ | ✅ Oui | Basé sur Chromium |
| Opera | 76+ | ✅ Oui | Compatible |
| Mobile Safari | iOS 14+ | ✅ Oui | Support tactile complet |
| Chrome Mobile | Android 10+ | ✅ Oui | Support tactile complet |

### Éditeur (Java)

| Système | Version | Java requis | Statut |
|---------|---------|-------------|--------|
| Windows | 10/11 64-bit | Java 25 | ✅ Testé |
| macOS | 10.15+ | Java 25 | ✅ Compatible |
| Linux | Toute distribution récente | Java 25 | ✅ Compatible |

## 🤝 Contribution

Les contributions sont les bienvenues ! PanoVisu est un projet communautaire.

### Comment contribuer ?

1. **Forkez le projet**
2. **Créez une branche** (`git checkout -b feature/amelioration`)
3. **Committez vos modifications** (`git commit -m 'Ajout d'une fonctionnalité'`)
4. **Poussez vers la branche** (`git push origin feature/amelioration`)
5. **Ouvrez une Pull Request**

### Signaler un bug

Utilisez le [système de tickets](http://panovisu.fr/hesk/) ou créez une [issue GitHub](https://github.com/llang57/editeurPanovisu/issues).

### Idées et suggestions

Toutes les idées sont bonnes à prendre ! N'hésitez pas à participer aux discussions.

## 💬 Support et contact

- **Site web** : [panovisu.fr](http://panovisu.fr)
- **Documentation** : [panovisu.fr/documentation](http://panovisu.fr/documentation)
- **Exemples** : [panovisu.fr/exemples](http://panovisu.fr/exemples)
- **Support** : [panovisu.fr/hesk](http://panovisu.fr/hesk/)
- **GitHub** : [github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)

## 📜 Licence

Ce projet est distribué sous licence **open source**. Le code source est librement accessible et modifiable.

### Philosophie du projet

PanoVisu a été créé en mars 2014 avec une vision simple : **rendre la création de visites virtuelles accessible à tous**.

> *"J'ai voulu PanoVisu entièrement libre et gratuit parce que je crois que la connaissance et les outils doivent être partagés. Il est aujourd'hui possible de réaliser des visites virtuelles professionnelles uniquement avec des logiciels libres (assemblage avec Hugin, création avec PanoVisu)."*

---

## 🌟 Remerciements

Merci à tous les contributeurs, testeurs et utilisateurs qui font vivre ce projet depuis 2014 !

Un merci particulier à :
- La communauté **Three.js** pour leur excellent framework WebGL
- Les contributeurs **OpenLayers** pour la cartographie
- L'écosystème **JavaFX** pour les outils d'interface moderne
- Tous les utilisateurs qui remontent leurs suggestions et bugs

---

**Développé avec ❤️ par Laurent LANG**  
© 2014-2025 - Projet open source communautaire
