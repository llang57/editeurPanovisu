# 📋 Changelog - Historique des versions

Toutes les modifications notables de PanoVisu sont documentées dans ce fichier.

Le format est basé sur [Keep a Changelog](https://keepachangelog.com/fr/1.0.0/),
et ce projet adhère au [Semantic Versioning](https://semver.org/lang/fr/).

## [Non publié]

### En cours de développement
- Support du format d'image WebP
- Amélioration de l'éditeur WYSIWYG
- Système de plugins pour extensions
- Internationalisation complète (anglais, espagnol)

---

## [3.4.8] - 2026-05-18

### 🎉 Nouveautés

#### Assistant d'écriture IA (Cloud & Local)
- **Intégration d'Ollama & OpenRouter** : Rédaction automatique de descriptions de panoramiques par l'IA.
- **Modèles IA Dynamiques** : Configuration des modèles, coûts et tailles entièrement déportée dans des fichiers JSON modifiables (`ollama-models.json` et `openrouter-models.json`).
- **Prompt Système Anti-Hallucination** : Implémentation d'instructions strictes interdisant l'invention de faits pour garantir la sobriété et l'exactitude des textes.
- **Thèmes premium AtlantaFX** : Ajout de Thèmes Modernes Clair et Sombre sophistiqués pour l'éditeur JavaFX.

### 🔧 Améliorations

#### Visualiseur d'aide robuste (F1)
- **Chargement local sécurisé** : Écriture dynamique d'un fichier HTML temporaire pour permettre la navigation fonctionnelle par ancres relatives (`#`) au sein du composant WebView de l'aide.
- **Slugification française customisée** : Fournisseur d'attributs HTML sur mesure gérant l'accentuation Unicode, la suppression propre des apostrophes et la redirection des ancres courtes.
- **Bouton de retour en haut de page** : Bouton flottant réactif et dynamique avec remontée fluide ("smooth scroll") animée au scroll de l'aide.
- **Mojibake cleanups** : Remplacement universel de tous les caractères emojis 4 octets complexes dans l'aide par des entités Unicode 2 octets sécurisées et des balises explicites (`[Linux]`, `★`, `✓`, `⚠`).

### 🐛 Corrections

- **Correctif de l'installeur Windows** : Résolution du bug d'interpolation dans le lanceur PowerShell `build-installer.ps1` empêchant le chargement du fichier JAR de release.

---

## [3.4.6] - 2026-05-18

### 🎉 Nouveautés

#### Visualiseur Panoramique Cube 3D amélioré
- **Maillage Triangulaire complet** : Remplacement du composant Box par un rendu géométrique en 6 faces triangulaires corrigé (haut/bas, inversion miroir).
- **Textures Plein Écran** : Définition de la résolution de prévisualisation à 2000px/face de cube pour une immersion sans pixelisation.
- **Support des projections** : Rendu des scènes équirectangulaires standard sans aucune courbure résiduelle.

---

## [3.4.4] - 2026-05-18

### 🎉 Nouveautés

#### Moteur 3D Panoramic Cube
- **PanoramicCube.java** : Introduction initiale du module de découpage et de projection en boîte 3D à 6 faces.
- **Stabilité de Build** : Correction du crash de classpath Maven lors de l'exécution et de la cohabitation des modules JavaFX.
- **CI/CD automatisé** : Intégration de la détection dynamique de version dans les workflows d'assemblage Linux et Windows.

---

## [3.4.2] - 2026-05-17

### 🔧 Améliorations

#### Accélération Matérielle GPU
- **Détection automatique OpenCL** : Support de l'accélération matérielle multi-GPU sous Windows pour cartes graphiques modernes (NVIDIA/AMD).
- **Optimisation mémoire** : Correction des fuites mémoire et fuites de handles lors du chargement simultané de plus de 50 panoramiques (issue #12).

---

## [3.4.0] - 2025-11-03

### 🎉 Nouveautés

#### Gestion d'Interface IA
- **Panneau de Configuration IA** : Ajout d'une fenêtre de paramètres complète (`Ctrl+M`) pour activer, désactiver et ordonner les modèles d'écriture.
- **Fichiers de préférences** : Intégration des fichiers de clés API locales et de configuration de prompts.

---

## [3.2.0] - 2025-10-17

### 🔧 Améliorations

#### Performance & Stabilité
- **JavaFX WebEngine** : Optimisations du rendu CSS pour le visualiseur.
- **Optimisation Maven** : Réduction du temps de build et mise à jour vers Maven 3.9.16.
- **Internationalisation** : Traduction espagnole et anglaise étendue dans les properties de PanoVisu.

---

## [3.1.0] - 2025-10-15

### 🎉 Nouveautés

#### LocalHTTPServer intégré
- **Serveur HTTP automatique** pour tester les visites en local
- **Détection intelligente** des ports (8080-8090)
- **Lancement automatique** du navigateur après export
- Plus besoin de Python/PHP pour tester localement

#### Interface utilisateur
- Nouveau thème sombre moderne
- Amélioration de la barre de navigation
- Icônes Font Awesome intégrées
- Support des emojis dans les textes

#### Performance
- Optimisation du chargement des panoramiques
- Réduction de l'utilisation mémoire (jusqu'à 30%)
- Amélioration du temps de compilation Maven

### 🔧 Améliorations

- Meilleure gestion des erreurs lors de l'import d'images
- Messages d'erreur plus explicites
- Documentation technique complète (Doxygen)
- Wiki GitHub avec guides détaillés

### 🐛 Corrections

- Correction de l'encodage UTF-8 dans les fichiers JavaScript
- Résolution des problèmes de CORS en local
- Correction de l'affichage des hotspots sur certains navigateurs
- Fix des conflits de ports avec d'autres applications

### 🔒 Sécurité

- Amélioration de la gestion des clés API
- Fichier `api-keys.properties.template` pour éviter les commits accidentels
- Documentation sur la sécurité des clés API

### 📚 Documentation

- Création du Wiki GitHub complet
- Guides d'installation multi-plateforme (Windows, macOS, Linux)
- Tutoriel de démarrage rapide (10 minutes)
- FAQ complète
- Documentation technique pour développeurs

---

## [3.0.0] - 2025-10-14

### 🎉 Nouveautés majeures

#### Modernisation technologique
- **Passage à Java 25** (depuis Java 8)
- **Mise à jour JavaFX 19** (depuis JavaFX 8)
- **Maven** pour la gestion des dépendances
- Architecture modulaire
- **Installateurs autonomes** incluant Java et toutes les dépendances (Windows, macOS, Linux)

#### Nouvelle interface
- Interface utilisateur complètement redessinée
- Thèmes clair et sombre
- Panneau de propriétés contextuel
- Amélioration de l'ergonomie générale

#### Hotspots HTML enrichis
- Éditeur WYSIWYG pour le contenu HTML
- Support des vidéos YouTube/Vimeo
- Galeries d'images
- Formatage de texte avancé

#### Export amélioré
- Minification automatique du JavaScript
- Optimisation des assets
- Génération de thumbnails
- Support de plusieurs formats d'export

### 🔧 Améliorations

- Gestion améliorée des projets multi-panoramiques
- Import/export de configurations
- Prévisualisation en temps réel
- Raccourcis clavier configurables

### 🐛 Corrections

- Résolution de nombreux bugs d'affichage
- Amélioration de la stabilité générale
- Correction des fuites mémoire

---

## [1.3.0] - 2015-09-14

### 🔧 Améliorations
- **Éditeur HTML** : Correction de dysfonctionnements dans l'éditeur de texte riche.
- **Éditeur de plan** : Résolution de bogues lors du positionnement et de l'édition des plans interactifs.

---

## [1.2.8] - 2015-05-18

### 🔧 Améliorations
- **Multirésolution** : Optimisation importante et corrections sur le découpage des faces de cube en multirésolution.
- **Stabilité** : Corrections de plusieurs bogues mineurs signalés lors de l'utilisation.

---

## [1.2.6] - 2015-04-29

### 🎉 Nouveautés
- **Internationalisation** : Ajout du support complet pour la langue portugaise.
- **Compatibilité macOS** : Intégration de correctifs spécifiques pour assurer le bon fonctionnement sur Mac OS X.

### 🔧 Améliorations
- **Multirésolution** : Intégration de la découpe des faces de cube dans la bibliothèque `panovisu.js`.

---

## [1.2.4] - 2015-03-09

### 🎉 Nouveautés
- **Réseau** : Ajout d'un test automatique de la connexion Internet au démarrage.

### 🔧 Améliorations
- **Corrections** : Résolution de divers bogues d'affichage et de réactivité.

---

## [1.2.0] - 2014-12-05

### 🎉 Nouveautés
- **Hotspots HTML** : Intégration d'un éditeur HTML complet dans l'application pour enrichir le contenu des points d'intérêt.
- **Cartographie interactive** : Prise en charge des cartes OpenStreetMap, Google Maps et Bing Maps avec géolocalisation directe des panoramiques.
- **Navigateur panoramique** : Ajout d'un panneau latéral interactif sous forme de liste de scènes triable.
- **Barres de navigation** : Implémentation des premières barres de navigation personnalisables.
- **Ergonomie** : Ajout d'écrans de chargement dynamiques, d'écrans d'aide et d'info configurables.

---

## [Dépot Initial] - 2014-05-19

### 🎉 Lancement du projet PanoVisu
- **Premier commit officiel** sur le dépôt Git.
- **Création du visualiseur de panoramiques 360°** et de l'éditeur de visites virtuelles (sous Java 8 / JavaFX 8).
- Support initial des projections équirectangulaires sphériques.
- Génération et exportation autonome des visites virtuelles au format HTML5.

---

## Légende

- 🎉 **Nouveautés** : Nouvelles fonctionnalités
- 🔧 **Améliorations** : Optimisations et améliorations
- 🐛 **Corrections** : Corrections de bugs
- 🔒 **Sécurité** : Correctifs de sécurité
- 📚 **Documentation** : Mises à jour de la documentation
- ⚠️ **Breaking Changes** : Changements incompatibles avec les versions précédentes

---

## Contributeurs

Merci à tous les contributeurs qui ont participé à ce projet ! 🙏

- **llang57** - Auteur principal et mainteneur
- **Communauté GitHub** - Rapports de bugs, suggestions, tests

### Comment contribuer ?

Consultez le [Guide de contribution](Documentation-technique.md#contribution) pour plus de détails.

---

## Roadmap (Prévisions)

Les prochaines étapes majeures de développement pour PanoVisu sont planifiées ainsi :

### Version 3.5.0 (Q3 2026)
- [ ] **Support du format d'image WebP** : Pour des panoramiques ultra-légers à qualité constante.
- [ ] **Génération d'images complémentaires par IA** : Intégration de modèles de génération d'images (Stable Diffusion / Flux via OpenRouter) pour enrichir visuellement les visites.
- [ ] **Amélioration de l'éditeur WYSIWYG** : Édition encore plus visuelle des fenêtres d'information et infobulles.
- [ ] **Système de plugins** : Possibilité pour les développeurs tiers d'étendre les fonctionnalités d'export.

### Version 3.6.0 (Q4 2026)
- [ ] **Réalité Virtuelle (VR) native** : Support complet des casques VR (Meta Quest, Apple Vision Pro) via WebXR.
- [ ] **Export vers A-Frame** : Intégration optionnelle d'A-Frame pour des scènes 3D immersives avancées.
- [ ] **Optimisation automatique des textures** : Compression à la volée des images équirectangulaires lors de l'export pour le mobile.

### Version 4.0.0 (2027)
- [ ] **Éditeur Web complet** : Version Saas/Web d'EditeurPanovisu utilisable directement depuis le navigateur.
- [ ] **Collaboration en temps réel** : Édition partagée à plusieurs de vos projets de visites virtuelles.
- [ ] **Stockage Cloud Optionnel** : Hébergement sécurisé en un clic des visites générées sur une infrastructure Cloud.
- [ ] **Application mobile compagnon (Android/iOS)** : Pour capturer, assembler et publier une visite directement depuis son smartphone.

---

## Liens utiles

- 🌐 [Site web officiel](https://lemondea360.fr/panovisu)
- 📖 [Documentation](https://github.com/llang57/editeurPanovisu/wiki)
- 🐛 [Rapporter un bug](https://github.com/llang57/editeurPanovisu/issues/new)
- 💬 [Discussions](https://github.com/llang57/editeurPanovisu/discussions)
- 📦 [Téléchargements](https://github.com/llang57/editeurPanovisu/releases)

---

**[Retour au Wiki](Home.md)** | **[Documentation technique](Documentation-technique.md)** | **[FAQ](FAQ.md)**
