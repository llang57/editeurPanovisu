# 🌐 Bienvenue sur le Wiki PanoVisu

**PanoVisu** est un éditeur open source pour créer des visites virtuelles et panoramiques 360° interactifs.

## 🚀 Accès rapide

- � **[Télécharger PanoVisu](https://github.com/llang57/editeurPanovisu/releases)** - Installateurs prêts à l'emploi (Windows, macOS, Linux)
- �📥 **[Installation](Installation)** - Guide d'installation et compilation depuis les sources
- 🎯 **[Démarrage rapide](Démarrage-rapide)** - Créez votre première visite en 10 minutes
- 📖 **[Guide utilisateur](Guide-utilisateur)** - Documentation complète des fonctionnalités
- ⚙️ **[Configuration avancée](Configuration-avancée)** - Clés API, paramètres XML, optimisation
- 💻 **[Documentation technique](Documentation-technique)** - Architecture et développement
- ❓ **[FAQ](FAQ)** - Questions fréquentes et solutions
- 📋 **[Changelog](Changelog)** - Historique des versions

## 📢 Dernières nouvelles

### Version 3.1.0 - Octobre 2025

### Mise à jour critique

**Important :** si vous hébergez des visites sur des serveurs Linux, veuillez lire :
- 🇫🇷 [Annonce v3.1.0](doc/DISCUSSION_V3.1.0.md) • [FAQ](doc/FAQ_V3.1.0.md) • [Guide de migration](doc/MIGRATION_GUIDE_V3.1.0.md)
- 🇬🇧 [Annonce v3.1.0](doc/DISCUSSION_V3.1.0_EN.md) • [FAQ](doc/FAQ_V3.1.0_EN.md) • [Migration Guide](doc/MIGRATION_GUIDE_V3.1.0_EN.md)

**Quelles sont les nouveautés ?** Correction critique du problème de sensibilité à la casse sur les serveurs Linux. Toutes les visites hébergées sur Linux doivent être réexportées avec la version 3.1.0.

---

**Nouvelle fonctionnalité majeure : Serveur HTTP intégré**

La version 3.1 introduit `LocalHTTPServer`, un serveur HTTP embarqué qui résout définitivement les problèmes CORS lors de l'ouverture des visites avec le protocole `file://`.

**Améliorations :**
- ✅ Lancement automatique du serveur HTTP lors de l'ouverture d'une visite
- ✅ Détection automatique des ports disponibles (8080-8090)
- ✅ Support complet des types MIME pour tous les assets
- ✅ Fallback automatique vers `file://` si le serveur ne peut démarrer

[📖 Lire les notes de version complètes](Changelog#version-310---octobre-2025)

## 🎯 Qu'est-ce que PanoVisu ?

PanoVisu est composé de deux éléments complémentaires :

### 1. 🖼️ Le Visualiseur HTML5/WebGL
- Technologie moderne et légère
- Compatible avec tous les navigateurs récents
- Affichage fluide des panoramiques 360°
- Support du tactile pour mobile/tablettes

### 2. 🎨 L'Éditeur Java/JavaFX
- Interface intuitive drag & drop
- Prévisualisation en temps réel
- Export en un clic
- Gestion complète des projets

## 🌟 Pourquoi choisir PanoVisu ?

| Avantage               | Description                                    |
| ---------------------- | ---------------------------------------------- |
| 🆓 **100% Gratuit**     | Aucun coût, aucune limitation, aucun filigrane |
| 🔓 **Open Source**      | Code source accessible et modifiable           |
| 🖥️ **Multi-plateforme** | Windows, macOS, Linux                          |
| 🌐 **Sans serveur**     | Visites autonomes hébergeables partout         |
| 🚀 **Moderne**          | HTML5, WebGL, Three.js                         |
| 🔧 **Extensible**       | Personnalisable à volonté                      |

## 📚 Ressources

- **Site web** : [lemondea360.fr/panovisu](https://lemondea360.fr/panovisu)
- **Dépôt GitHub** : [github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)
- **Documentation API** : [llang57.github.io/editeurPanovisu](https://llang57.github.io/editeurPanovisu/)
- **Issues** : [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
- **Discussions** : [GitHub Discussions](https://github.com/llang57/editeurPanovisu/discussions)

## 🤝 Contribuer

PanoVisu est un projet communautaire. Les contributions sont les bienvenues !

- 🐛 [Signaler un bug](https://github.com/llang57/editeurPanovisu/issues/new)
- 💡 [Proposer une fonctionnalité](https://github.com/llang57/editeurPanovisu/discussions)
- 🔀 [Soumettre une Pull Request](https://github.com/llang57/editeurPanovisu/pulls)

## 📜 Licence

Projet open source développé avec ❤️ par Laurent LANG  
© 2014-2026 - Communauté PanoVisu