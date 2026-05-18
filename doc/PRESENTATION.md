# EditeurPanovisu - Éditeur de Visites Virtuelles

## Présentation

**EditeurPanovisu** est un éditeur de visites virtuelles panoramiques 360° permettant de créer des visites interactives complètes sans nécessiter de compétences en programmation.

![Version](https://img.shields.io/badge/version-3.4.8-blue)
![Java](https://img.shields.io/badge/Java-25-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-19.0.2.1-green)
![License](https://img.shields.io/badge/license-Libre-brightgreen)

---

## Caractéristiques Principales

### Création de Visites Virtuelles
- ✔ Support des panoramiques équirectangulaires et cubiques
- ✔ Liaisons interactives entre panoramiques (hotspots)
- ✔ Point de vue d'entrée configurable pour chaque panoramique
- ✔ Orientation de la boussole (direction du nord)
- ✔ Navigation intuitive entre les scènes

### Interface Utilisateur
- ✔ Éditeur WYSIWYG ("What You See Is What You Get")
- ✔ Personnalisation complète de l'interface (couleurs, polices, positions)
- ✔ Modèles d'interface sauvegardables et réutilisables
- ✔ Aperçu en temps réel des modifications
- ✔ Interface multilingue (Français, Anglais, Allemand)

### Cartographie Intégrée
- ✔ Intégration OpenStreetMap
- ✔ Cartes ESRI ArcGIS (World Street Map, World Imagery, World Topo)
- ✔ Positionnement GPS des panoramiques
- ✔ Plan interactif de la visite
- ✔ Géolocalisation avec JMapViewer

### [IA] Intelligence Artificielle & Génération de Descriptions (Nouveauté v3.4.x)
- ✔ Génération automatique de descriptions de haute qualité pour chaque panoramique
- ✔ Utilisation de modèles d'IA locaux avec **Ollama** (Qwen 3.5, Phi-4, DeepSeek R1, Mistral Nemo)
- ✔ Utilisation de modèles d'IA distants avec **OpenRouter** (Gemini 3.1, Claude 4.6, GPT-5.5)
- ✔ Système de secours (fallback) gratuit et performant avec **Hugging Face**
- ✔ Règles "zéro-hallucination" strictes intégrées au prompt système (fondées sur le titre et la géolocalisation)
- ✔ Panneau de configuration et d'activation dynamique des modèles (`Ctrl+M`) fondé sur des fichiers JSON

### Éléments Interactifs
- ✔ Hotspots personnalisables (forme, couleur, texte)
- ✔ Liens vers d'autres panoramiques
- ✔ Liens vers des images externes
- ✔ Boussole interactive
- ✔ Vignettes de navigation
- ✔ Partage sur réseaux sociaux (Facebook, Twitter, LinkedIn, Pinterest)

---

## Technologies Utilisées

### Environnement de Développement
- **Java** : OpenJDK 25 (Temurin-25+36-LTS)
- **JavaFX** : 19.0.2.1 (interface graphique)
- **Maven** : 3.9.9 (gestion des dépendances et build)

### Bibliothèques Principales
- **CommonMark-Java** 0.22.0 : Parsing et rendu Markdown (GFM)
  - `commonmark` : Parser Markdown de base
  - `commonmark-ext-gfm-tables` : Support des tableaux
  - `commonmark-ext-gfm-strikethrough` : Support du texte barré
- **JMapViewer** 2.18 : Composant de carte OpenStreetMap
- **Three.js** : Rendu WebGL des panoramiques 360°
- **OpenLayers** : Gestion des cartes interactives
- **Leaflet** : Alternative légère pour les cartes

### Outils de Build
- **Inno Setup** 6.5.4 : Création d'installateur Windows
- **jpackage** : Empaquetage natif Java (intégré au JDK)

---

## Architecture Technique

### Structure du Projet
```
editeurPanovisu/
├── src/                          # Code source Java
│   └── editeurpanovisu/
│       ├── EditeurPanovisu.java  # Classe principale
│       ├── MarkdownViewer.java   # Visualiseur Markdown
│       ├── DocumentationDialog.java # Fenêtre de documentation
│       ├── NavigateurCarte.java  # Gestion des cartes
│       └── i18n/                 # Fichiers de localisation
├── aide/                         # Documentation utilisateur
│   ├── aide.md                   # Guide utilisateur principal
│   └── images/                   # Captures d'écran
├── doc/                          # Documentation technique
├── panovisu/                     # Bibliothèque JavaScript
│   ├── panovisu.js               # Moteur de visite virtuelle
│   └── libs/                     # Bibliothèques tierces
├── theme/                        # Thèmes et ressources visuelles
└── target/                       # Build artifacts
```

### Formats de Fichiers
- **Projet** : `.pvu` (XML)
- **Panoramiques** : `.jpg`, `.png` (équirectangulaires ou faces de cube)
- **Configuration** : `.cfg` (fichiers de configuration utilisateur) et `api-keys.properties` (clés API privées)
- **Modèles IA** : `ollama-models.json` et `openrouter-models.json` (modèles IA dynamiques et configurables)
- **Modèles d'interface** : `.tpl` (templates d'interface)
- **Export** : HTML5 + JavaScript (visite complète autonome)

---

## Génération des Visites

### Format de Sortie
Les visites générées sont des applications **HTML5 100% autonomes** :
- ✔ Aucun serveur requis (fonctionne en local ou sur serveur web)
- ✔ Compatible tous navigateurs modernes
- ✔ Responsive (adapté mobile et desktop)
- ✔ Technologie WebGL pour le rendu 3D
- ✔ Fallback CSS3D si WebGL indisponible

### Structure d'une Visite Générée
```
visite/
├── index.html                    # Page d'entrée
├── panovisu/                     # Moteur JavaScript
│   ├── panovisu.js
│   ├── panovisuInit.js
│   ├── css/                      # Styles
│   ├── libs/                     # Bibliothèques (Three.js, etc.)
│   └── i18n/                     # Traductions
├── images/                       # Panoramiques et ressources
└── visite.xml                    # Configuration de la visite
```

---

## Fonctionnalités Avancées

### Outils de Transformation
- **Cube → Équirectangulaire** : Conversion 6 faces vers panorama 360°
- **Équirectangulaire → Cube** : Découpage panorama en 6 faces
- **Redimensionnement** : Optimisation des images pour le web

### Édition d'Interface
- **Barre de titre** : Police, taille, couleur, position
- **Barre de navigation** : Zoom, rotation, plein écran
- **Hotspots** : Formes (rond, carré, personnalisé), couleurs
- **Boussole** : Style, taille, position
- **Bouton de masquage** : Contrôle de visibilité des éléments UI
- **Vignettes** : Aperçus des panoramiques disponibles

### Configuration Projet
- **Informations générales** : Titre, auteur, description
- **Géolocalisation** : Coordonnées GPS
- **Droits** : Licence, copyright
- **Réseaux sociaux** : Métadonnées Open Graph
- **Analytics** : Support Google Analytics (optionnel)

---

## État Actuel du Développement

### ✔ Fonctionnalités Complètes
- Migration Java 25 terminée
- Suppression dépendances Google Maps/Bing
- Intégration OpenStreetMap/ESRI (migration vers Nominatim en cours)
- Lecteur Markdown intégré (F1)
- Documentation utilisateur complète avec badges textuels
- Intégration complète de l'IA (Ollama local, OpenRouter cloud, Hugging Face secours)
- Prompt système anti-hallucination ultra-robuste pour la génération de descriptions
- Panneau de gestion et d'ordonnancement dynamique des modèles d'IA (Ctrl+M)
- Rendu 3D de panoramique cube avec maillage triangulaire complet sans distorsions
- Détection et support officiel de l'accélération GPU OpenCL sous Windows
- Intégration du thème moderne AtlantaFX (Primer Light/Dark)
- Scripts de build et d'installation d'environnement automatisés (install-java-maven.ps1)

### 🔄 En Cours
- Finalisation de la documentation technique enrichie
- Optimisations mineures de l'interface utilisateur

###  Prochaines Étapes
- Migration OpenLayers vers version récente
- Support des panoramiques HDR
- Export en format Web moderne (Progressive Web App)
- Éditeur de parcours guidés
- Mode présentation automatique

---

## Configuration Système Requise

### Pour Développer
- **OS** : Windows 10/11, Linux, macOS
- **JDK** : OpenJDK 25 (Temurin recommandé)
- **RAM** : 4 Go minimum, 8 Go recommandé
- **Espace disque** : 500 Mo pour l'IDE + dépendances

### Pour Utiliser
- **OS** : Windows 10/11, Linux, macOS
- **RAM** : 2 Go minimum
- **Espace disque** : 200 Mo pour l'application
- **Navigateur** : Chrome, Firefox, Edge, Safari (pour visualiser les visites)

---

## Ressources

### Documentation
- **Guide Utilisateur** : `aide/aide.md` (accessible via F1)
- **Documentation Technique** : `doc/` (guides d'installation, build, migration)
- **Présentation** : Ce document

### Dépôt
- **GitHub** : [https://github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)
- **Branche principale** : `master`
- **Licence** : Libre (à préciser)

---

## Historique des Versions

### Version 3.4.8 (Mai 2026) - Version Actuelle
- [IA] **Intelligence Artificielle Dynamique** :
  - Intégration dynamique des modèles Ollama (Qwen 3.5, Phi-4, DeepSeek R1, Mistral Nemo)
  - Intégration dynamique des modèles OpenRouter (Gemini 3.1 Flash/Pro, Claude 4.6, GPT-5.5)
  - Support de secours avec Hugging Face
  - Panel complet de configuration `Ctrl+M` fondé sur des fichiers JSON
  - Refonte complète du prompt système anti-hallucination pour des descriptions 100% fiables et factuelles
- [UI] **Améliorations de l'Interface & Aide** :
  - Intégration du thème moderne AtlantaFX (`Primer Light`, `Primer Dark`)
  - Chargement et gestion robuste des polices (ex: Wingdings) dans la WebView de l'aide
  - Nettoyage des en-têtes et des caractères complexes pour une compatibilité d'affichage Windows maximale
- [3D] **Modernisation 3D & Performances** :
  - Rendu 3D Panoramique Cube avec maillage triangulaire complet (PanoramicCube.java) pour supprimer toutes distortions haut/bas
  - Support de la haute qualité plein écran à 2000px par face
  - Détection automatique et accélération GPU OpenCL sous Windows
  - Script d'installation automatisée de l'environnement de développement complet (`install-java-maven.ps1`)

### Version 3.0.0 (Octobre 2025)
-  **Migration Java 25** : Passage à OpenJDK 25 (Temurin-25+36-LTS)
- 🗺️ **Remplacement Google Maps/Bing** : Migration vers OpenStreetMap/ESRI
  - Suppression des dépendances propriétaires
  - Intégration JMapViewer 2.18
  - Support ESRI ArcGIS REST API (World Street Map, Imagery, Topo)
- 📚 **Lecteur Markdown intégré** : CommonMark-Java 0.22.0 avec GFM
  - Support des tableaux et texte barré
  - Conversion aide HTML vers Markdown
  - Badges textuels pour compatibilité Windows
- 🌍 **Support multilingue amélioré** : Français, Anglais, Allemand
-  **Nettoyage et modernisation** :
  - Suppression Google+ (fermé en 2019)
  - Suppression des clés API propriétaires
  - Refactorisation du code
-  **Build automatisé** :
  - Inno Setup 6.5.4 pour installateur Windows
  - Numérotation automatique des builds
  - Scripts PowerShell de build
-  **Documentation complète** :
  - Guide utilisateur avec captures d'écran
  - Document de présentation technique
  - Accès rapide via F1

### Version 2.0.0 (Non publiée)
- Version intermédiaire jamais finalisée
- Tentatives de modernisation partielles

### Version 1.x (Historique)
- Versions antérieures avec Java 8
- Support Google Maps avec clé API
- Interface JavaFX de base
- Build manuel

---

## Contribution

Le projet est hébergé sur **GitHub** et est actuellement en développement actif. Les contributions sont les bienvenues !

### 🔗 Dépôt GitHub
**[https://github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)**

### Comment Contribuer

#### 🐛 Signaler un Bug
1. Vérifiez que le bug n'a pas déjà été signalé dans les [Issues](https://github.com/llang57/editeurPanovisu/issues)
2. Créez une nouvelle issue avec :
   - Description détaillée du problème
   - Étapes pour reproduire
   - Captures d'écran si pertinent
   - Version de l'application et OS utilisé

#### ★ Proposer une Fonctionnalité
1. Ouvrez une issue avec le tag `enhancement`
2. Décrivez la fonctionnalité souhaitée et son utilité
3. Discutez de la faisabilité avec la communauté

####  Soumettre du Code
1. **Fork** le projet
2. Créez une branche pour votre fonctionnalité : `git checkout -b feature/ma-fonctionnalite`
3. Commitez vos changements : `git commit -m "feat: ajout de ma fonctionnalité"`
4. Poussez vers votre fork : `git push origin feature/ma-fonctionnalite`
5. Ouvrez une **Pull Request** détaillée

#### 📚 Améliorer la Documentation
- Documentation utilisateur : `aide/aide.md`
- Documentation technique : `doc/`
- Commentaires dans le code source
- README et guides d'installation

### Convention de Commits
Nous utilisons les préfixes suivants pour les messages de commit :
- `feat:` Nouvelle fonctionnalité
- `fix:` Correction de bug
- `docs:` Modification de documentation
- `style:` Formatage, point-virgules manquants, etc.
- `refactor:` Refactorisation du code
- `test:` Ajout ou modification de tests
- `chore:` Maintenance (build, dépendances, etc.)

### Code de Conduite
- Soyez respectueux et constructif
- Acceptez les critiques constructives
- Concentrez-vous sur ce qui est le mieux pour le projet
- Aidez les nouveaux contributeurs

---

## Notes Techniques

### Particularités JavaFX WebView
- Moteur WebKit intégré (version liée à JavaFX 19.0.2.1)
- Support limité des émojis Unicode sous Windows
- Solution : Badges textuels pour la documentation ([OK], [ERREUR], [ATTENTION])

### Gestion des Cartes
- Ancien : Google Maps API (nécessitait clé API)
- Actuel : OpenStreetMap (libre, sans clé) + ESRI ArcGIS REST
- Géocodage : Migration vers Nominatim (en cours)

### Build et Déploiement
- Numérotation automatique à chaque build
- Génération installateur Windows (.exe)
- Package application complète avec JRE embarqué

---

**Dernière mise à jour** : 18 mai 2026  
**Version du document** : 3.4.8  
**Auteur** : Laurent Lang
