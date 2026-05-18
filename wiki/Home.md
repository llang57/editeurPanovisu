# 🌐 Bienvenue sur le Wiki PanoVisu

**PanoVisu** est un éditeur open source pour créer des visites virtuelles et panoramiques 360° interactifs.

## 🚀 Accès rapide

- ➤ **[Télécharger PanoVisu](https://github.com/llang57/editeurPanovisu/releases)** - Installateurs prêts à l'emploi ([Windows], [macOS], [Linux])
- ➤ **[Installation](Installation)** - Guide d'installation et compilation depuis les sources
- ➤ **[Démarrage rapide](Démarrage-rapide)** - Créez votre première visite en 10 minutes
- ➤ **[Guide utilisateur](Guide-utilisateur)** - Documentation complète des fonctionnalités
- ➤ **[Configuration avancée](Configuration-avancée)** - Clés API, paramètres XML, optimisation
- ➤ **[Documentation technique](Documentation-technique)** - Architecture et développement
- ➤ **[FAQ](FAQ)** - Questions fréquentes et solutions
- ➤ **[Changelog](Changelog)** - Historique des versions

## 📢 Dernières nouvelles

### Version 3.4.8 - Mai 2026

**Mise à jour majeure : Révolution IA (Ollama & OpenRouter), Rendu 3D Avancé et Thèmes Modernes**

La version 3.4.8 d'EditeurPanovisu apporte innovations et améliorations majeures pour simplifier l'édition de vos visites virtuelles 360° grâce à l'Intelligence Artificielle et augmenter la fidélité de rendu :

**Améliorations majeures :**
- 🧠 **IA Cloud & Locale intégrée** : Assistant d'écriture intelligent pour la description des panoramas via OpenRouter (Claude 3.5, Gemini 3.1, GPT-5) et Ollama (Local : Qwen 3.5, Phi-4, DeepSeek R1).
- ⚙️ **Modèles IA Dynamiques** : Chargement et coûts des modèles gérés dynamiquement à partir de fichiers JSON configurables (`openrouter-models.json` et `ollama-models.json`).
- 🛡️ **Prompt Système Strict Anti-Hallucination** : Implémentation d'un ensemble de directives rigoureuses pour des textes générés neutres, exacts et sans aucune formulation artificielle.
- 📐 **Rendu Panoramic Cube 3D** : Remplacement des rendus classiques par un maillage triangulaire complet à 6 faces pour un rendu sans aucune déformation.
- 💻 **Accélération Matérielle (OpenCL)** : Activation du support GPU sous Windows (cartes NVIDIA/AMD récentes) pour des performances de rendu instantanées.
- 🎨 **Interface Moderne AtlantaFX** : Intégration de thèmes modernes Clair et Sombre, personnalisables et respectueux de la fatigue oculaire.
- 📖 **Aide Utilisateur Améliorée (F1)** : Lecteur d'aide interne fluide doté de liens d'ancrage fonctionnels et d'un bouton flottant de retour en haut.

[📖 Lire les notes de version v3.4.8](RELEASE_NOTES_v3.4.8.md)

## 🎯 Qu'est-ce que PanoVisu ?

PanoVisu est composé de deux éléments complémentaires :

### 1. Le Visualiseur HTML5/WebGL
- Technologie moderne, légère et performante
- Rendu 3D Panoramic Cube ultra-réaliste
- Compatible avec tous les navigateurs récents
- Affichage fluide des panoramiques 360°
- Support du tactile pour mobiles et tablettes

### 2. L'Éditeur Java/JavaFX
- Interface intuitive moderne avec thèmes AtlantaFX (Clair/Sombre)
- Assistant d'écriture intelligent assisté par IA (Ollama & OpenRouter)
- Accélération matérielle OpenCL (GPU) pour un traitement d'image rapide
- Prévisualisation 3D en temps réel
- Export en un clic de la visite autonome
- Gestion complète des projets et métadonnées

## 🌟 Pourquoi choisir PanoVisu ?

| Avantage | Description |
|----------|-------------|
| 🆓 **100% Gratuit** | Aucun coût, aucune limitation, aucun filigrane |
| 🔓 **Open Source** | Code source accessible et modifiable |
| 🖥️ **Multi-plateforme** | Windows, macOS, Linux |
| 🌐 **Sans serveur** | Visites autonomes hébergeables partout |
| 🚀 **Moderne** | HTML5, WebGL, Three.js |
| 🔧 **Extensible** | Personnalisable à volonté |

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
