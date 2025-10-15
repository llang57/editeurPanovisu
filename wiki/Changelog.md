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

## [3.1.0] - 2025-01-15

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

## [3.0.0] - 2024-11-20

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

### ⚠️ Breaking Changes

- **Incompatibilité** avec les projets créés dans la version 2.x
- **Migration nécessaire** (outil fourni : `scripts/migrate-v2-to-v3.sh`)
- **Nouvelle structure** de fichiers XML

---

## [2.5.1] - 2024-03-15

### 🔧 Améliorations

- Amélioration de la compatibilité avec les navigateurs modernes
- Optimisation du chargement des panoramiques

### 🐛 Corrections

- Correction d'un bug d'affichage sur Safari
- Résolution de problèmes de CORS
- Fix de l'export sur Windows

---

## [2.5.0] - 2023-12-10

### 🎉 Nouveautés

- Support des panoramiques cubiques (cubemap)
- Amélioration de la cartographie OpenStreetMap
- Ajout de nouveaux thèmes visuels

### 🔧 Améliorations

- Performance du rendu WebGL
- Gestion des limites de rotation
- Interface de configuration des hotspots

### 🐛 Corrections

- Correction de l'export FTP
- Résolution de bugs mineurs

---

## [2.4.0] - 2023-09-05

### 🎉 Nouveautés

- Intégration Google Maps et Bing Maps
- Plan interactif avec markers cliquables
- Rotation automatique configurable

### 🔧 Améliorations

- Amélioration de l'éditeur de texte
- Optimisation de la taille des exports
- Support de fichiers de grande taille

### 🐛 Corrections

- Fix de l'encodage des caractères spéciaux
- Résolution de problèmes de performances

---

## [2.3.0] - 2023-06-20

### 🎉 Nouveautés

- Support des hotspots vidéo
- Amélioration du diaporama automatique
- Export vers GitHub Pages

### 🔧 Améliorations

- Interface utilisateur modernisée
- Meilleure gestion des erreurs
- Documentation enrichie

---

## [2.2.0] - 2023-03-12

### 🎉 Nouveautés

- Hotspots de navigation améliorés
- Support des galeries d'images
- Personnalisation avancée de l'interface

### 🐛 Corrections

- Correction de bugs d'affichage
- Amélioration de la stabilité

---

## [2.1.0] - 2022-12-08

### 🎉 Nouveautés

- Barre de navigation personnalisable
- Support de l'audio de fond
- Écran de démarrage (splash screen)

### 🔧 Améliorations

- Performance de chargement
- Compatibilité mobile

---

## [2.0.0] - 2022-09-15

### 🎉 Nouveautés majeures

- Réécriture complète du visualiseur
- Passage à Three.js pour le rendu WebGL
- Support des panoramiques haute résolution (8K+)
- Interface utilisateur responsive

### 🔧 Améliorations

- Amélioration des performances
- Meilleure compatibilité navigateurs
- Documentation complète

### ⚠️ Breaking Changes

- Nouvelle structure de fichiers
- Incompatibilité avec les projets 1.x

---

## [1.5.0] - 2022-05-10

### 🎉 Nouveautés

- Support de la cartographie
- Hotspots HTML personnalisables
- Export FTP intégré

---

## [1.4.0] - 2022-02-20

### 🎉 Nouveautés

- Amélioration de l'éditeur
- Support de nouveaux formats d'images
- Prévisualisation en temps réel

---

## [1.3.0] - 2021-11-15

### 🎉 Nouveautés

- Hotspots de navigation
- Configuration de l'interface
- Export HTML5

---

## [1.2.0] - 2021-08-10

### 🎉 Nouveautés

- Support des panoramiques equirectangulaires
- Éditeur de projets
- Visualiseur HTML5/WebGL

---

## [1.1.0] - 2021-05-05

### 🎉 Nouveautés

- Interface graphique JavaFX
- Import de panoramiques
- Export HTML

---

## [1.0.0] - 2021-02-01

### 🎉 Première version publique

- Visualiseur de panoramiques 360°
- Support equirectangulaire
- Navigation basique
- Export HTML

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

### Version 3.2.0 (Q2 2025)
- [ ] Support du format WebP
- [ ] Amélioration de l'éditeur WYSIWYG
- [ ] Système de plugins
- [ ] Internationalisation (anglais, espagnol)

### Version 3.3.0 (Q3 2025)
- [ ] Export vers A-Frame
- [ ] Support de la réalité virtuelle (VR)
- [ ] Amélioration des performances mobile
- [ ] Templates de visites pré-configurés

### Version 4.0.0 (2026)
- [ ] Éditeur web complet
- [ ] Collaboration en temps réel
- [ ] Cloud storage optionnel
- [ ] Application mobile (Android/iOS)

---

## Liens utiles

- 🌐 [Site web officiel](https://lemondea360.fr/panovisu)
- 📖 [Documentation](https://github.com/llang57/editeurPanovisu/wiki)
- 🐛 [Rapporter un bug](https://github.com/llang57/editeurPanovisu/issues/new)
- 💬 [Discussions](https://github.com/llang57/editeurPanovisu/discussions)
- 📦 [Téléchargements](https://github.com/llang57/editeurPanovisu/releases)

---

**[Retour au Wiki](Home.md)** | **[Documentation technique](Documentation-technique.md)** | **[FAQ](FAQ.md)**
