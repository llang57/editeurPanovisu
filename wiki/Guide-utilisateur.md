# 📖 Guide utilisateur

Documentation complète des fonctionnalités de PanoVisu.

## Table des matières

- [Interface de l'éditeur](#interface-de-léditeur)
- [Gestion des projets](#gestion-des-projets)
- [Types de panoramiques](#types-de-panoramiques)
- [Hotspots et interactivité](#hotspots-et-interactivité)
- [Plan interactif et Cartographie](#plan-interactif-et-cartographie)
- [Assistant d'écriture intelligent (IA)](#assistant-décriture-intelligent-ia)
- [Accélération Matérielle (OpenCL)](#accélération-matérielle-opencl)
- [Export et publication](#export-et-publication)
- [Raccourcis clavier](#raccourcis-clavier)

---

## Interface de l'éditeur

L'interface d'EditeurPanovisu repose sur une architecture JavaFX moderne stylisée grâce aux thèmes premium **AtlantaFX**. L'utilisateur peut facilement basculer entre le **Thème Clair** et le **Thème Sombre** pour travailler dans le meilleur confort visuel possible.

### Vue d'ensemble

L'interface de PanoVisu est organisée en plusieurs panneaux :
1. **Barre de menus** - Accès aux fonctions de projet, de configuration et d'import/export.
2. **Barre d'outils** - Raccourcis rapides pour ajouter des panoramiques, lier des scènes et lancer la prévisualisation.
3. **Liste des panoramiques (Vignettes)** - Liste interactive de toutes les scènes de la visite avec des indicateurs de statut.
4. **Zone de prévisualisation (Visualiseur 3D)** - Rendu 3D interactif en temps réel utilisant notre technologie de projection cubique à maillage triangulaire sans distorsion.
5. **Panneau latéral de propriétés** - Configuration des hotspots, orientation du nord, point de vue par défaut, et assistant d'écriture intelligent assisté par IA.
6. **Console de logs** - Suivi des opérations en arrière-plan (chargement OpenCL, requêtes IA, démarrage du serveur HTTP).

---

## Gestion des projets

Les projets PanoVisu sont enregistrés sous forme de fichiers XML auto-contenus décrivant l'ensemble de la structure de la visite virtuelle (panoramiques, hotspots, liaisons, thèmes d'interface et positionnement).

- **Nouveau projet** : Initialise une visite vide.
- **Ajout de panoramiques** : Permet d'importer vos fichiers images (équirectangulaires standard JPEG, TIFF ou PNG).
- **Sauvegarde** : Enregistre le fichier `.panovisu` et génère automatiquement les métadonnées requises.
- **Réinitialisation** : Vide la mémoire et nettoie les textures 3D en un clic pour libérer les ressources système.

---

## Types de panoramiques

PanoVisu prend en charge le format le plus répandu pour les photos 360° : la projection **équirectangulaire (sphérique)**.

- **Rendu Panoramic Cube 3D** : Lors de la prévisualisation et de l'export, l'éditeur projette l'image sphérique sur 6 faces d'un cube 3D.
- **Maillage Triangulaire Optimisé** : Le visualiseur utilise un maillage triangulaire à haute résolution (2000px de côté pour chaque face) qui garantit la continuité des textures et élimine toute sensation d'étirement ou de raccord visible sur les bords haut/bas.

---

## Hotspots et interactivité

Les **hotspots** (points d'intérêt) sont le cœur de l'interactivité de vos visites virtuelles.

- **Création d'une liaison** : Effectuez un simple **clic gauche** sur la zone de prévisualisation 3D pour placer un nouveau hotspot, puis sélectionnez le panoramique cible.
- **Lien vers des médias externes** : Raccourci **Shift+clic gauche** pour associer un hotspot à une image pop-up locale.
- **Suppression d'un point** : **Ctrl+clic gauche** (ou Cmd+clic sur macOS) sur un point pour le retirer instantanément.
- **Boussole et Orientation** : Ajustez la position du nord sur chaque panoramique avec le raccourci **Shift + clic droit** pour faire correspondre l'orientation géographique entre les scènes liées.
- **Point de vue d'entrée** : Définissez la direction de départ du regard par défaut lors du chargement de la scène d'un simple **clic droit** au centre de la zone visée.

---

## Plan interactif et Cartographie

PanoVisu permet aux utilisateurs de s'orienter facilement grâce à des aides de positionnement :

- **Plan au sol (2D)** : Importation d'une image de plan (cadastre, plan de musée) sur laquelle vous déposez des pastilles de position interactives indiquant le panoramique actif et le cône de vision orienté en temps réel.
- **Cartographie GPS (OpenStreetMap)** : Intégration du composant JMapViewer permettant le géoréférencement précis de vos scènes et l'affichage d'une carte OpenStreetMap interactive synchronisée avec le panoramique visité.

---

## Assistant d'écriture intelligent (IA)

PanoVisu intègre un panneau complet de configuration IA (`Ctrl+M`) pour automatiser la rédaction des descriptions textuelles et historiques de vos panoramiques.

- **IA Locale (Ollama)** : Idéal pour travailler hors-ligne et gratuitement. Supporte les modèles open source de pointe comme `Qwen 3.5` (excellent en géographie/toponymie), `Phi-4`, `DeepSeek R1` et `Mistral Nemo`.
- **IA Cloud (OpenRouter)** : Accès aux modèles d'IA les plus avancés du marché (Claude 3.5/4.6, Gemini 3.1, GPT-5, DeepSeek V4).
- **Architecture Dynamique JSON** : Les spécifications des modèles (coûts, tailles, qualité) sont chargées en temps réel à partir des fichiers modifiables `ollama-models.json` et `openrouter-models.json` situés dans le répertoire de configuration.
- **Prompt Système Anti-Hallucination** : Un prompt rigoureux encadre la génération des descriptions pour empêcher l'IA d'inventer des faits historiques, des dates ou des métriques non confirmés. Si les données certifiées manquent, le système demande à l'IA d'utiliser des descriptions visuelles et géographiques génériques réelles.

---

## Accélération Matérielle (OpenCL)

Pour garantir une fluidité parfaite et traiter l'assemblage et les transformations de textures 3D en haute résolution sans ralentissement :
- **Support Multi-GPU** : EditeurPanovisu intègre une bibliothèque OpenCL capable de détecter et d'exploiter les cartes graphiques dédiées (NVIDIA GeForce, AMD Radeon, etc.) sous Windows et Linux.
- **Traitement parallèle** : Les calculs lourds de projection de pixels équirectangulaires vers des faces cubiques sont parallélisés directement sur le processeur graphique (GPU), libérant ainsi le processeur principal (CPU).

---

## Export et publication

L'export de PanoVisu compile tous les assets en une visite virtuelle autonome, sans dépendance externe :
- **Serveur HTTP Local Intégré** : Pour contourner les restrictions de sécurité CORS strictes des navigateurs (qui bloquent le protocole standard `file://` local), l'éditeur intègre un serveur Web léger (`LocalHTTPServer`) démarrant automatiquement lors de la prévisualisation pour servir vos fichiers locaux en toute sécurité.
- **Détection de port libre** : Scanne automatiquement les ports `8080-8090` pour éviter tout conflit d'application.
- **Zéro dépendance serveur** : Le dossier d'export peut être hébergé sur n'importe quel hébergeur web basique (Apache, Nginx, GitHub Pages) par simple copier-coller du dossier HTML5 généré.

---

## Raccourcis clavier

L'éditeur propose plusieurs raccourcis pratiques pour accélérer votre flux de travail :

| Raccourci clavier | Action associée |
|-------------------|-----------------|
| **Ctrl + A** (ou **Cmd+A**) | Importer/Ajouter un panoramique au projet |
| **Ctrl + S** (ou **Cmd+S**) | Sauvegarder le projet actif |
| **Ctrl + O** (ou **Cmd+O**) | Ouvrir un projet existant |
| **Ctrl + M** (ou **Cmd+M**) | Ouvrir le panneau de configuration de l'IA |
| **F1** | Ouvrir ce guide d'aide utilisateur interactif |
| **Clic gauche** | Ajouter un hotspot de liaison dans le visualiseur 3D |
| **Shift + Clic gauche** | Lier un hotspot à une image pop-up |
| **Ctrl + Clic gauche** | Supprimer le hotspot cliqué |
| **Clic droit** | Définir le point de vue par défaut (direction d'entrée) |
| **Shift + Clic droit** | Définir l'orientation du Nord géographique |

---

**📝 Note** : Pour contribuer à cette documentation ou au développement général, consultez le [dépôt GitHub](https://github.com/llang57/editeurPanovisu).
