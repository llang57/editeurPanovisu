# 🌐 Éditeur PanoVisu

[![Version](https://img.shields.io/badge/version-3.4.14-blue.svg)](https://github.com/llang57/editeurPanovisu/releases)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-19-green.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/license-GPL--3.0-brightgreen.svg)](LICENSE)
[![Javadoc](https://img.shields.io/badge/docs-Javadoc-blue.svg)](https://llang57.github.io/editeurPanovisu/)
[![Wiki](https://img.shields.io/badge/docs-Wiki-blue.svg)](https://github.com/llang57/editeurPanovisu/wiki)
[![Downloads](https://img.shields.io/github/downloads/llang57/editeurPanovisu/total.svg)](https://github.com/llang57/editeurPanovisu/releases)
[![Stars](https://img.shields.io/github/stars/llang57/editeurPanovisu.svg)](https://github.com/llang57/editeurPanovisu/stargazers)

<div align="center">

**🎯 Éditeur visuel pour la création de visites virtuelles et panoramiques 360° interactifs**

Créez facilement des visites virtuelles immersives en HTML5/WebGL sans aucune connaissance en programmation.  
PanoVisu combine puissance, simplicité et liberté pour offrir une solution complète et gratuite.

[📦 Télécharger](https://github.com/llang57/editeurPanovisu/releases) • [📚 Documentation](https://github.com/llang57/editeurPanovisu/wiki) • [🌐 Site Web](https://lemondea360.fr/panovisu) • [💬 Discussions](https://github.com/llang57/editeurPanovisu/discussions)

**Languages:** [🇫🇷 Français](README.md) • [🇬🇧 English](README_EN.md)

</div>

---

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
- [Quoi de neuf](#-quoi-de-neuf)
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
- **Hotspots de navigation** - Liens entre panoramiques avec transitions en fondu enchaîné
  - 16 animations différentes (bounce, pulse, flash, shake, swing, tada, etc.)
  - Couleurs personnalisables par hotspot (HSB)
  - Agrandissement au survol configurable
- **Hotspots d'images/photos** - Galeries photo intégrées
- **Hotspots de diaporama** - Visualiseur HTML5 moderne avec animations complètes
  - Contrôles complets (play/pause, navigation, plein écran, miniatures)
  - Animations cohérentes avec les autres types de hotspots
  - Comportement pause/lecture intelligent
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
- **Éditeur HTML WYSIWYG intégré** : Création de contenus riches sans code HTML
- **Création de diaporamas modernisée** : Interface épurée avec gestion des transitions
- **Barres de navigation personnalisées** : Création visuelle avec zones cliquables
- **Adaptation thématique** : Toutes les fenêtres suivent le thème choisi (clair/sombre)
- Export en un clic vers HTML/XML

### ⚡ Accélération GPU (OpenCL)
- **Traitement matériel** des images : conversion équirectangulaire ↔ cube, redimensionnement et génération des niveaux de détail
- **Gains mesurés** : chargement des visites 3,4× plus rapide (15 s → 4,5 s), affichage 10× plus rapide, redimensionnement par lot 1,7× plus rapide
- **Qualité supérieure** : interpolations Bicubic et Lanczos3 en remplacement du plus proche voisin, sans crénelage
- **Repli automatique sur le processeur** si aucun GPU compatible OpenCL n'est disponible (NVIDIA, AMD, Intel)

### 🗺️ Cartographie et géolocalisation
- **Carte Leaflet** intégrée pour situer chaque panoramique
- **Marqueurs déplaçables** à la souris, avec mise à jour immédiate des coordonnées
- **Radar de champ de vision** réglable de 0 à 240 m
- **Recherche d'adresse** via OpenStreetMap, et géocodage inverse LocationIQ pour enrichir les descriptions

### 🤖 Génération de descriptions par IA
- **Ollama** en local, gratuit et sans envoi de données, ou **OpenRouter** en ligne (Claude, Gemini, GPT)
- **Catalogue de modèles modifiable**, du gratuit au premium, avec tarifs indicatifs
- **Descriptions relues automatiquement** : dates, mesures, distinctions, superlatifs et noms propres non fournis sont signalés

### 🎨 Thèmes de l'interface
- **27 thèmes** : 9 AtlantaFX (Primer, Nord, Cupertino, Dracula), 2 MaterialFX, 2 FlatLaf, 2 personnalisés, 2 acidulés, 2 modernes et 8 minimalistes (bleu, vert, rouge, mauve, en clair et foncé)
- **Icônes adaptatives** : les icônes SVG prennent automatiquement la couleur du thème actif
- **Lisibilité en thème sombre** : contrôles de formulaire éclaircis et bordures contrastées

## 📥 Installation

### 🚀 Installation rapide (Recommandé)

**Des installateurs prêts à l'emploi sont disponibles !** Ils incluent Java 25 et toutes les dépendances.

➡️ **[Télécharger depuis GitHub Releases](https://github.com/llang57/editeurPanovisu/releases)**

| Système | Fichier | Taille |
|---------|---------|--------|
| 🪟 **Windows** | `EditeurPanovisu-[version].exe` | ~200 MB |
| 🍎 **macOS** | `EditeurPanovisu-[version].dmg` | ~200 MB |
| 🐧 **Linux** | `.deb` ou `.rpm` | ~200 MB |

**Aucune installation de Java requise** - Tout est inclus ! ✅

### 🛠️ Installation depuis les sources (Développeurs)

<details>
<summary>Cliquez pour voir les instructions de compilation</summary>

#### Prérequis

- **Java 25** (OpenJDK Temurin recommandé) - [Télécharger](https://adoptium.net/)
- **Maven 3.9+**
- **Système d'exploitation** : Windows 10/11, macOS 10.15+, Linux

#### Étapes

1. **Clonez le dépôt**
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
   # Ou directement :
   java -jar target/editeurPanovisu.jar
   ```

📖 **Documentation complète** : [Guide d'installation](https://github.com/llang57/editeurPanovisu/wiki/Installation)

</details>

## 🎯 Démarrage rapide

### Créer votre première visite en 10 minutes ! ⏱️

1. **📥 Téléchargez et installez** PanoVisu depuis [Releases](https://github.com/llang57/editeurPanovisu/releases)

2. **📸 Préparez vos images panoramiques**
   - Format : équirectangulaire (ratio 2:1) ou cubemap
   - Résolution : 8192×4096 px recommandée
   - Outil gratuit : [Hugin](http://hugin.sourceforge.net/)

3. **🆕 Créez un nouveau projet**
   - Fichier → Nouveau projet
   - Importez vos panoramiques (drag & drop)

4. **🔗 Ajoutez de l'interactivité**
   - Créez des hotspots de navigation
   - Ajoutez des galeries photos
   - Insérez du contenu HTML

5. **🎨 Personnalisez l'interface**
   - Barre de navigation
   - Couleurs et thèmes
   - Logo et splash screen

6. **🚀 Exportez et publiez**
   - Fichier → Exporter la visite
   - Test local automatique (serveur HTTP intégré)
   - Hébergez sur votre serveur ou GitHub Pages

📖 **Tutoriel complet** : [Démarrage rapide](https://github.com/llang57/editeurPanovisu/wiki/Démarrage-rapide)

### 🎬 Exemples de visites

Découvrez des exemples réalisés avec PanoVisu : [lemondea360.fr/panovisu](https://lemondea360.fr/panovisu)

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
- **Gson** - Configuration JSON des modèles IA
- **Format `.pvu`** - Format texte propre au projet (ni XML, ni JAXB)

### Cartes et géolocalisation
- **OpenStreetMap** (par défaut, gratuit)
- **Google Maps API** (optionnel, nécessite une clé)
- **Bing Maps API** (optionnel, nécessite une clé)
- **Gluon Maps** - Alternative mobile

### 🤖 Génération de descriptions
- **Ollama** (local, gratuit) - Modèles exécutés sur votre machine, aucune donnée envoyée
- **OpenRouter** (en ligne, optionnel) - Accès unifié à Claude, Gemini, GPT et autres
- **LocationIQ** (optionnel, gratuit jusqu'à 5 000 requêtes/jour) - Géocodage inverse ; améliore nettement la précision des descriptions générées

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

# Exécution de la suite de tests JUnit
mvn test

# Nettoyage du projet
mvn clean
```

### Système de versioning

Le projet utilise un **système d'auto-incrémentation** du numéro de build :

- **Format** : `3.4.14` accompagné d'un numéro de build auto-incrémenté (ex. build 3748)
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

## 🆕 Quoi de neuf

**Version actuelle : 3.4.14** — fiabilité de la génération de descriptions par IA.

- Les catalogues de modèles sont enfin livrés avec l'application : ils manquaient à toute installation, laissant la liste de modèles vide.
- Modèles et tarifs remis à jour ; la génération locale, jusque-là impossible sur une machine ordinaire, fonctionne.
- Descriptions plus sobres et relues automatiquement : dates, mesures, distinctions, superlatifs et noms propres non fournis sont signalés.

> ⚠️ Aucune consigne ne rend l'hallucination impossible. Le dispositif la raréfie et la signale ; une relecture reste nécessaire. Renseigner une clé LocationIQ améliore la précision plus sûrement que n'importe quelle consigne.

📖 **Historique complet** : [Changelog](https://github.com/llang57/editeurPanovisu/wiki/Changelog) · [Notes de version](https://github.com/llang57/editeurPanovisu/releases)

---

## 🤝 Contribution

Les contributions sont les bienvenues ! PanoVisu est un projet communautaire.

### Comment contribuer ?

1. **Forkez le projet**
2. **Créez une branche** (`git checkout -b feature/amelioration`)
3. **Committez vos modifications** (`git commit -m 'Ajout d'une fonctionnalité'`)
4. **Poussez vers la branche** (`git push origin feature/amelioration`)
5. **Ouvrez une Pull Request**

### Signaler un bug

Créez une [issue GitHub](https://github.com/llang57/editeurPanovisu/issues) pour signaler un problème.

### Idées et suggestions

Toutes les idées sont bonnes à prendre ! N'hésitez pas à participer aux discussions.

## 💬 Support et contact

- **Site web** : [lemondea360.fr/panovisu](https://lemondea360.fr/panovisu)
- **Documentation** : [GitHub Wiki](https://github.com/llang57/editeurPanovisu/wiki)
- **Issues** : [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
- **Discussions** : [GitHub Discussions](https://github.com/llang57/editeurPanovisu/discussions)
- **Dépôt** : [github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)

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
© 2014-2026 - Projet open source communautaire
