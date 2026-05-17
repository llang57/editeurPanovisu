# 🌐 Éditeur PanoVisu

[![Version](https://img.shields.io/badge/version-3.4.2-blue.svg)](https://github.com/llang57/editeurPanovisu/releases)
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

## 🚀 Version 3.4.2 - Corrections GPU et stabilité

**Quoi de neuf ?**

### 🐛 Corrections critiques (Build 3684+)

**Détection GPU NVIDIA/AMD sous Windows** 🎮
- ✅ **RTX 5070 Ti et cartes récentes** : Correction de la détection OpenCL sur Windows
- ✅ **Toutes marques supportées** : NVIDIA CUDA, AMD, Intel — plus seulement les plateformes Mesa/Linux
- ✅ **Système de priorité** : NVIDIA > AMD > rusticl > Intel pour sélection automatique du meilleur GPU

**Stabilité import panoramiques** 📸
- ✅ **Plus de blocage à la 19e image** : Suppression du pré-calcul mémoire à l'import (fix issue #12)
- ✅ **Import illimité** : Projets avec 37+ panoramiques fonctionnent sans saturation mémoire
- ✅ **Calcul à la demande** : Les faces cube sont générées uniquement lors de l'affichage

---

## 🚀 Version 3.3.3 - Support Linux Portable Simplifié

**Quoi de neuf ?**

### 🐧 Distribution Linux Portable (Build 3597+ - 23 oct. 2025)

**Archive portable autonome** 📦
- **ZIP et TAR.GZ** : Archives complètes sans dépendances système
- **Installation ultra-simple** : Extraire, `chmod +x`, lancer
- **Documentation triple format** :
  - `INSTALLATION.md` : Guide Markdown complet avec tous les détails
  - `INSTALLATION.txt` : Version texte pour terminaux (80 colonnes)
  - `INSTALLATION.html` : Version web stylisée avec CSS moderne
- **Script bash intelligent** :
  - Détection automatique Java et version (recommande Java 25+)
  - Configuration JavaFX 3D optimale (PRISM_FORCEGL, PRISM_ORDER)
  - Messages d'erreur clairs avec solutions
- **Structure propre** : Fichiers Windows (.bat/.vbs) automatiquement exclus
- **Guide installation** : Voir [doc/install/INSTALLATION.md](doc/install/INSTALLATION.md)

**Corrections critiques Linux** 🐛
- ✅ **Cache panoramas** : Correction utilisation cache (700ms → <50ms)
- ✅ **Blocage modal** : Fenêtre configuration ne se bloque plus (Platform.runLater + show())
- ✅ **NullPointerException** : Protection contre crashes au rechargement (images non chargées)
- ✅ **Build Maven** : Toutes les classes incluses dans le JAR (93/93 classes)

### 🎯 Visualiseur Panoramique 3D Amélioré (Build 3417 - 20 oct. 2025)
- **📱 Interface modernisée avec icônes** :
  - Remplacement des boutons texte par des **icônes PNG intuitives** (home, photo, boussole, œil)
  - **Adaptation automatique au thème** : icônes blanches pour thèmes sombres, noires pour thèmes clairs
  - **Tooltips informatifs** : aide contextuelle au survol de chaque bouton
  - Boutons avec **effet de survol** : opacité 0.6 → 1.0 pour retour visuel
  - **Positionnement optimisé** : labels en haut, visualiseur 3D au centre, 5 boutons en bas
- **🖼️ Mode plein écran haute résolution** :
  - **Fenêtre popup dédiée** (1200×780) au lieu du plein écran système
  - **Résolution doublée** : charge l'image originale sans réduction (iRapport=1 au lieu de 2)
  - **Qualité cube 4× supérieure** : faces de **1000×1000 pixels** (au lieu de 500×500)
  - Image équirectangulaire intermédiaire de **3000×1500** (au lieu de 1500×750)
  - **Préservation parfaite** : image 8192×4096 → rendu 8192×4096 (vs 4096×1024 en mode normal)
  - **Mode haute qualité configurable** : flag activable pour affichages haute résolution
- **🎨 Gestion des erreurs améliorée** :
  - Vérification de l'existence des fichiers avant chargement
  - **Logs détaillés** : dimensions d'image, chemins de fichiers, performances GPU
  - **Espacement optimal** : hauteur calculée avec marge de 100px pour éviter les boutons coupés
- **⚡ Performances GPU** :
  - **Redimensionnement bicubique** : 8192×4096 → 3000×1500 en ~350ms
  - **Conversion Equi→Cube** : génération 6 faces 1000×1000 en ~125ms
  - Traitement total < 500ms pour qualité maximale

### 🗺️ Refonte Carte et Géolocalisation (Builds 3376-3416)
- **🌐 Migration vers Leaflet** :
  - Remplacement de NavigateurCarteGluon par **NavigateurCarte** (Leaflet pur)
  - Architecture **lazy loading** avec callback `onMapReady` pour éviter bugs JavaFX
  - Chargement HTML via `load()` préservant les chemins relatifs des ressources
  - Injection dynamique de la clé API LocationIQ après initialisation
- **📍 API complète de gestion** :
  - **Marqueurs draggables** : déplaçables à la souris avec mise à jour automatique
  - **Radar (champ de vision)** : taille configurable 0-240m (×3 vs ancien 0-80m)
  - **Géocodage Nominatim** : recherche d'adresse avec résultats OpenStreetMap
  - **Méthodes** : `ajouteMarqueur()`, `retireMarqueurs()`, `allerCoordonnees()`, `afficheRadar()`
- **🔧 Corrections critiques** :
  - Inversion longitude/latitude corrigée dans constructeur `CoordonneesGeographiques`
  - Distinction entre `recupereCoordonnees(0)` (marqueur) et `recupereCoordonnees()` (centre)
  - Callback asynchrone `setOnMapReady()` pour éviter "texture is null" (bug JavaFX 19)
  - Méthode `miseAJourRadarSeul()` pour optimiser les mises à jour partielles

### 🎨 Interface et Visualisation (Builds 3376-3416)
- **📐 Adaptation taille viewport** :
  - Calcul intelligent du ratio container/image pour extraction correcte
  - Remplacement des dimensions hardcodées par `getVisualisationWidth()`/`getVisualisationHeight()`
  - Viewport ajusté dynamiquement selon dimensions réelles
- **📱 Réseaux sociaux modernisés** :
  - Remplacement Google+/Facebook par **Meta** (plateforme unifiée)
  - Suppression références obsolètes à Google+
  - Interface épurée avec 3 options : Twitter, Meta, Email
- **🎛️ Barre personnalisée enrichie** :
  - **Opacité configurable** : slider 0-1 avec effet hover (opacité → 1.0 au survol)
  - Télécommande et barre de navigation avec transparence réglable
  - Amélioration visibilité tout en préservant l'immersion

### 🛡️ Corrections et Stabilité (Builds 3376-3416)
- **🖼️ Protection images invalides** :
  - Vérification `BufferedImage` avant écriture JPEG (diaporama)
  - Skip des images corrompues au lieu de crasher l'application
  - Message d'avertissement dans la console avec nom du fichier
- **🎯 Positionnement UI** :
  - Correction décalages éléments interface (labels, boutons, contrôles)
  - Espacement constant via `PANEL_TOP_MARGIN` et `PANEL_ELEMENT_SPACING`
  - Architecture en 3 zones pour géolocalisation : Header / Carte / Panneau contrôles
- **⏱️ Gestion asynchrone** :
  - Chargement carte en lazy loading (au premier clic géolocalisation)
  - Flag `carteEnCoursDeChargement` pour éviter re-configurations multiples
  - Listeners d'état WebEngine pour synchronisation Java/JavaScript

### ⚡ Accélération GPU (OpenCL)
- **🎮 Traitement GPU** : Accélération matérielle pour toutes les opérations de traitement d'images
  - **Transformations panoramiques** : Conversion Équirectangulaire ↔ Cube **3.3× plus rapide**
  - **Redimensionnement d'images** : Algorithmes Bicubic et Lanczos3 haute qualité sur GPU
  - **Affichage des visites** : Rendu panoramique **10× plus rapide**
  - **Niveaux de détail (LOD)** : Génération des niveaux progressifs accélérée
  - **Fallback automatique** : Bascule sur CPU si GPU indisponible
- **📊 Gains de performance** :
  - Chargement des visites panoramiques : **3.4× plus rapide** (15s → 4.5s)
  - Redimensionnement batch : **1.7× plus rapide**
  - Affichage à l'écran : **10× plus rapide** (1000ms → 100ms)
  - Qualité visuelle : Bicubic/Lanczos3 élimine le crénelage
- **🎨 Qualité d'image améliorée** :
  - Interpolation Bicubic remplace Nearest Neighbor
  - Interpolation Lanczos3 pour agrandissements ×2+
  - Réduction d'aliasing et meilleur anti-crénelage

### 🎨 Interface Modernisée et Visualiseur Diaporama
- **�️ Éditeur d'interface modernisé** : Refonte complète des fenêtres de création/édition
  - **Interface création diaporama** : Design épuré avec gestion thématique automatique
  - **Éditeur HTML intégré** : WYSIWYG JavaFX moderne pour contenus riches (hotspots HTML)
  - **Création de barres personnalisées** : Interface intuitive avec drag & drop et prévisualisation temps réel
  - **Theme-Aware Design** : Toutes les fenêtres s'adaptent automatiquement au thème (clair/sombre)
  - **Corrections ergonomiques** : Redimensionnement optimal, boutons correctement positionnés
  - **Cohérence visuelle** : Suppression des couleurs hardcodées, utilisation des variables de thème
- **�📽️ Visualiseur diaporama HTML5 moderne** : Remplacement complet du visualiseur obsolète Supersized (jQuery 2012)
  - **Design Material Design** : Interface élégante avec glassmorphism et animations fluides
  - **Barre de progression visuelle** : Suivi temps réel avec animation de remplissage
  - **Navigation intuitive** : Boutons, flèches directionnelles, clavier, miniatures cliquables
  - **Indicateurs de position** : Dots avec effet de survol et compteur (X/Total)
  - **Contrôles complets** : Play/Pause, Précédent/Suivant, Plein écran, Miniatures
  - **Mode miniatures** : Galerie avec survol et sélection directe
  - **Masquage auto** : Contrôles disparaissent après 3s d'inactivité (réapparaissent au survol)
  - **Responsive** : Adaptation automatique mobile/tablette/desktop
  - **Léger et performant** : ~20 KB vs ~150 KB (ancien), code HTML5/CSS3/JavaScript pur
- **🎭 Animations hotspots diaporama** : 
  - Cohérence avec hotspots photo : animations "blink", "pulse", "rotation", etc.
  - Configuration depuis l'éditeur avec prévisualisation
- **⏸️ Comportement pause intelligent** :
  - État pause/lecture respecté lors de la navigation manuelle
  - Changement d'image en pause = reste en pause (action volontaire requise)
  - Seul le bouton Play/Pause modifie explicitement l'état de lecture

### 🎨 Système de thèmes enrichi (24 thèmes disponibles)
- **🎯 Nouveaux thèmes minimalistes** : 8 thèmes flat design professionnels
  - **4 palettes de couleurs** : Bleu 🔷🔹, Vert 🟢🟩, Rouge 🔴🟥, Mauve 💜🟪
  - **2 variantes par palette** : Clair et Foncé pour chaque couleur
  - **Design flat sobre** : Interface épurée sans effets excessifs, focus sur la lisibilité
  - **Couleurs d'accent harmonieuses** : Cohérence visuelle dans toute l'application
- **🎨 Icônes colorées dynamiques** : Les icônes SVG prennent automatiquement la couleur du thème
  - Icônes Glyph/Ikonli colorées via CSS
  - Icônes SVG avec remplacement intelligent de `currentColor` et `fill="white"`
  - Conversion dynamique en PNG avec Apache Batik
- **🌓 Visibilité optimisée thèmes sombres** :
  - Contrôles de formulaire éclaircis (checkboxes, radio buttons, sliders, spinners, progressbars)
  - Bordures plus contrastées et épaissies pour meilleure identification
  - Texte des menus toujours blanc (normal, hover, dropdown ouvert)
- **📦 Collection complète de 24 thèmes** :
  - 9 thèmes AtlantaFX (Primer, Nord, Cupertino, Dracula)
  - 2 thèmes MaterialFX (Light, Dark)
  - 2 thèmes FlatLaf (Light/IntelliJ, Dark/Darcula)
  - 2 thèmes legacy personnalisés (Clair, Foncé)
  - 2 thèmes acidulés (Clair 🌸, Foncé 🌌)
  - 2 thèmes modernes (Clair 🌿, Foncé 🌃)
  - 8 thèmes minimalistes (Bleu/Vert/Rouge/Mauve × Clair/Foncé)
- **🔄 Application universelle** : Fenêtre d'accueil FXML intégrée au système de thèmes

### 🔧 Architecture technique
- Support OpenCL 1.2+ (NVIDIA CUDA, AMD ROCm, Intel compatible)
- Auto-routing intelligent GPU/CPU selon taille d'image
- Gestion robuste des colorspaces (CMYK, YCbCr, RGB)
- Documentation technique complète (1200+ lignes)

---

## 🔧 Version 3.2.0 - Personnalisation avancée et transitions fluides

**Quoi de neuf ?**

### ✨ Nouvelles fonctionnalités
- **🎨 Personnalisation individuelle des hotspots** : 
  - **16 animations différentes** : bounce, pulse, flash, shake, swing, tada, wobble, jello, heartbeat, rubberBand, rotate, flip, zoomIn, zoomOut, fadeIn, slideIn
  - **Couleurs personnalisées** : Choisissez une couleur unique pour chaque hotspot avec le sélecteur de couleur HSB
  - **Icônes personnalisées** : Remplacez l'icône par défaut de chaque hotspot par une image de votre choix, avec transformation de couleur automatique
  - **Agrandissement au survol** : Effet de zoom configurable pour chaque hotspot
  - **Persistance complète** : Les icônes et couleurs personnalisées sont sauvegardées dans les projets PVU
  - Configuration complète depuis l'interface graphique avec prévisualisation en temps réel
- **🌊 Fondu enchaîné WebGL** : Transitions fluides et élégantes entre panoramiques avec effet de fondu enchaîné (2 secondes) utilisant WebGL et shaders pour une expérience visuelle professionnelle
- **📦 Export ZIP** : Exportez vos visites directement en archive ZIP pour un partage simplifié
- **🖼️ Redimensionnement d'images** : Nouvel outil de redimensionnement et compression des images panoramiques
- **📐 Conversion ratio 2:1** : Améliorations du positionnement des icônes dans l'interface

### 🔧 Améliorations techniques
- **Architecture robuste** : Validation des dimensions d'images et gestion d'erreurs renforcée
- **Parsing amélioré** : Gestion sûre des champs vides dans les fichiers PVU
- **UI optimisée** : Panneaux de hotspots élargis (+30px) pour une meilleure ergonomie

### ⚠️ Important - Migration depuis v3.0.0

Si vous hébergez des visites sur des serveurs Linux, notez que la **v3.1.0** a corrigé un problème critique de case-sensitivity. Les visites créées avec v3.0.0 et hébergées sur Linux doivent être ré-exportées avec v3.1.0 ou supérieure.

### 📢 Discussions GitHub
- 🇫🇷 [📢 Annonce v3.2.0](https://github.com/llang57/editeurPanovisu/discussions) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/8) • [🚀 Guide migration](https://github.com/llang57/editeurPanovisu/discussions/10)
- 🇬🇧 [📢 v3.2.0 Announcement](https://github.com/llang57/editeurPanovisu/discussions) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/9) • [🚀 Guide](https://github.com/llang57/editeurPanovisu/discussions/11)

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
