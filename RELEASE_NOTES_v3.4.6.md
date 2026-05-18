# 🚀 Notes de version — EditeurPanovisu v3.4.6

**Date de release** : 18 mai 2026  
**Build** : 3720+  
**Priorité** : 🎲 Rendu panoramique cube corrigé · 🔧 Stabilité · ✨ Qualité plein écran

---

## ✨ Nouveautés v3.4.6

### 🎲 Visualiseur panoramique cube — corrections majeures

**Rendu 3D entièrement corrigé (`PanoramicCube.java`)**

- ✅ **TriangleMesh à UV complet** : remplacement du `Box` JavaFX par un maillage triangulaire manuel — chaque face du cube reçoit la totalité de la texture (0,0)→(1,1) sans distorsion ni découpage
- ✅ **Faces haut/bas corrigées** : inversion Top/Bottom résolue (l'axe Y JavaFX est inversé par rapport à la convention `equi2cubeAuto`) — les faces sol et plafond s'affichent maintenant correctement
- ✅ **Miroir gauche/droite corrigé** : les faces TOP et BOTTOM ne sont plus inversées horizontalement — l'ordre des vertices a été corrigé pour aligner U=0 avec la gauche caméra
- ✅ **CullFace.NONE** appliqué sur toutes les faces pour garantir la visibilité depuis l'intérieur du cube

**Source d'image équirectangulaire — bug critique corrigé**

- ✅ **Image Mercator éliminée du pipeline cube** : `getImgVisuPanoramique()` est une projection Mercator (utilisée pour l'affichage plat). Elle était incorrectement passée à `equi2cubeAuto()` (qui attend une équirectangulaire), causant une compression du contenu vers la partie supérieure du cube
- ✅ **Fallback corrigé dans `affichePano()`** : utilise désormais `panoramique.getImgPanoramique()` (équirectangulaire 1200×600) au lieu de l'image Mercator
- ✅ **Fallback corrigé dans `setImagePanoramique(3 args)`** : même correction appliquée pour les changements de panorama (vue principale, 2e chargement et suivants)
- ✅ **Cohérence premier/second chargement** : le premier chargement utilisait le cache (correct), les chargements suivants utilisaient l'image Mercator (incorrect) — problème résolu

### 🖼️ Qualité plein écran améliorée

- ✅ **Cache grande résolution : 1000 → 2000 px/face** : précalculé depuis l'image source pleine résolution via `equi2cubeAuto(imgPanoSource, 2000)`
- ✅ **Fallback plein écran : image originale pleine résolution** : quand le cache n'est pas disponible en mode `hauteQualite`, l'image originale est rechargée depuis le fichier (`getStrNomFichier()`) pour calculer des faces 2000×2000 — la qualité n'est plus limitée à l'équirectangulaire 1200×600

---

## ✨ Nouveautés v3.4.4

### 🎲 Rendu panoramique cube (PanoramicCube) — introduction

- Nouvelle classe `PanoramicCube.java` : visualiseur 3D des panoramas équirectangulaires sur un cube JavaFX
- Conversion automatique equirectangulaire → 6 faces via `TransformationsPanoramique.equi2cubeAuto()`
- Cache pré-calculé par résolution (petite 500×500, grande 1000×1000) pour éviter les recalculs
- Orientation corrigée par rotation initiale de 180° sur l'axe Y
- Intégration dans `NavigateurPanoramique` avec support des deux modes qualité

### 🔧 Build et déploiement

- ✅ Correction `NoClassDefFoundError: editeurpanovisu/PanoramicCube` (packaging Maven)
- ✅ Commande correcte documentée : `mvn clean compile javafx:run`
- ✅ Détection dynamique de la version dans `create-linux-portable.sh` et `build-installer.ps1`
- ✅ Correction du profil `-Pportable` pour le packaging Linux
- ✅ Workflow CI mis à jour : lecture dynamique des notes de release depuis les fichiers Markdown

---

## ✨ Nouveautés v3.4.2

### 🎮 Détection GPU NVIDIA/AMD sous Windows

- ✅ **RTX 5070 Ti et cartes récentes** : la plateforme OpenCL NVIDIA CUDA est maintenant détectée sous Windows
- ✅ **Toutes marques supportées** : NVIDIA > AMD > rusticl (Mesa) > Intel — sélection automatique du meilleur GPU
- ✅ **Diagnostic amélioré** : les plateformes détectées sont listées au démarrage dans la console

### 📸 Stabilité import panoramiques

- ✅ **Blocage à la 19e image corrigé** : suppression du pré-calcul mémoire excessif à l'import ([issue #12](https://github.com/llang57/editeurPanovisu/issues/12))
- ✅ **Import sans limite pratique** : projets avec 37+ panoramiques fonctionnent sans saturation mémoire
- ✅ **Calcul à la demande** : les faces cube sont générées uniquement lors de l'affichage du panoramique

---

## ✨ Nouveautés v3.4.0

### 🤖 Configuration dynamique des modèles IA

- ✅ Interface de configuration OpenRouter et Ollama entièrement repensée
- ✅ Sélection automatique du meilleur modèle disponible
- ✅ Support multi-modèles : OpenRouter (cloud) + Ollama (local)
- ✅ Vérification de disponibilité des modèles au démarrage

---

## 📦 Téléchargements

| Plateforme | Fichier |
|---|---|
| Windows | `EditeurPanovisu-Setup-3.4.6.exe` |
| macOS | `EditeurPanovisu-3.4.6.dmg` |
| Linux (zip) | `EditeurPanovisu-Linux-Portable-3.4.6.zip` |
| Linux (tar.gz) | `EditeurPanovisu-Linux-Portable-3.4.6.tar.gz` |

> ⚠️ Java runtime inclus — aucune installation Java requise.

### Linux
```bash
unzip EditeurPanovisu-Linux-Portable-3.4.6.zip
cd EditeurPanovisu-Linux-Portable-3.4.6
chmod +x lancer-editeur-panovisu.sh
./lancer-editeur-panovisu.sh
```

---

## 📋 Configuration requise

- **OS** : Windows 10/11 · macOS 11+ · Linux (Debian 11+, Ubuntu 20.04+)
- **RAM** : 4 GB minimum, 8 GB recommandé
- **GPU** : Optionnel — OpenCL 1.2+ pour accélération (NVIDIA, AMD, Intel)
- **Java** : inclus dans le package (Java 25)

---

## 📋 Historique des versions

| Version | Points clés |
|---|---|
| **v3.4.6** | Correction rendu cube (miroir, Mercator, qualité plein écran 2000px) |
| **v3.4.4** | Introduction PanoramicCube, corrections build Maven |
| **v3.4.2** | Détection GPU NVIDIA/AMD, stabilité import 19+ panoramiques |
| **v3.4.0** | Configuration dynamique modèles IA (OpenRouter, Ollama) |
| v3.3.3 | Support Linux portable + accélération GPU OpenCL |
| v3.2.0 | Personnalisation avancée des hotspots (16 animations, couleurs) |
| v3.1.0 | Correction critique case-sensitivity serveurs Linux |
| v3.0.0 | Intelligence artificielle (Ollama), descriptions automatiques |

---

## 🔐 Intégrité

Checksums SHA256 disponibles dans `checksums.txt`.

---

## 🙏 Remerciements

- Merci à Jorge ([@PhotoDidacte](https://github.com/PhotoDidacte)) pour le rapport de l'[issue #12](https://github.com/llang57/editeurPanovisu/issues/12)
- Merci aux utilisateurs pour leurs retours sur le rendu panoramique cube

---

## 🔗 Liens utiles

- **Dépôt GitHub** : https://github.com/llang57/editeurPanovisu
- **Documentation OpenRouter** : https://openrouter.ai/models
- **Documentation Ollama** : https://ollama.ai/library

---

**Note** : Cette version nécessite Java 25 ou supérieur avec `--enable-preview`.
