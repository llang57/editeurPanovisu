# 🚀 Notes de version - EditeurPanovisu v3.4.4

**Date de release** : 18 mai 2026  
**Build** : 3696+  
**Priorité** : 🎲 Rendu 3D panoramique par cube + 🔧 Stabilité et corrections

---

## ✨ Nouveautés majeures

### 🎲 Rendu panoramique par cube (PanoramicCube)

**Remplacement de la sphère par un cube 3D pour le visualiseur intégré**

- Nouvelle classe `PanoramicCube.java` : rendu des panoramas équirectangulaires sur 6 faces d'un cube JavaFX 3D
- Utilisation de `Box` + `PhongMaterial` avec `selfIlluminationMap` pour un rendu sans ombres parasites
- Conversion automatique equirectangulaire → 6 faces via `TransformationsPanoramique.equi2cubeAuto()`
- Redimensionnement GPU (Bicubic via OpenCL) avant découpage, avec fallback CPU
- Méthode `setCubeFaces()` pour l'utilisation du cache pré-calculé (évite les recalculs)
- Orientation corrigée par rotation initiale de 180° sur l'axe Y

**Intégration dans NavigateurPanoramique**
- `panoramicCube` remplace l'ancienne sphère comme objet 3D principal
- Mapping correct des faces : Front, Back, Left, Right, Top, Bottom avec inversion Top/Bottom pour l'affichage

---

## 🔧 Corrections et améliorations

### Lancement Maven
- ✅ Correction du bug `NoClassDefFoundError: editeurpanovisu/PanoramicCube` lors du lancement via `mvn clean package javafx:run`
- ✅ Commande correcte documentée : `mvn clean compile javafx:run` (sans phase `package`)
- ✅ La phase `package` (shade JAR) interférait avec le classpath de `javafx:run` en mode développement

### CI/CD et build
- ✅ Détection dynamique de la version dans `create-linux-portable.sh` (plus de version codée en dur)
- ✅ Détection dynamique de la version dans `build-installer.ps1`
- ✅ Correction du profil `-Pportable` pour le packaging Linux dans le workflow CI
- ✅ Checklist versioning ajoutée dans `CLAUDE.md` pour éviter les oublis lors des releases

### Documentation
- ✅ `CLAUDE.md` enrichi avec la checklist des 4 fichiers à mettre à jour lors d'un bump de version
- ✅ Workflow de release mis à jour avec les notes de v3.4.2

---

## 📦 Fichiers créés

### Code source
- `src/editeurpanovisu/PanoramicCube.java` — Cube 3D panoramique (274 lignes)

### Documentation
- `RELEASE_NOTES_v3.4.4.md` — Ce fichier

---

## 📦 Fichiers modifiés

- `src/editeurpanovisu/NavigateurPanoramique.java` — Intégration de `PanoramicCube` (ligne 188)
- `src/editeurpanovisu/EditeurPanovisu.java` — Corrections diverses
- `build-installer.ps1` — Détection dynamique de version
- `create-linux-portable.sh` — Détection dynamique de version
- `.github/workflows/` — Correction CI Linux
- `CLAUDE.md` — Documentation mise à jour

---

## 🐛 Problèmes connus et solutions

### NoClassDefFoundError: PanoramicCube
**Symptôme** : L'application crash lors de l'ouverture d'un panoramique avec `NoClassDefFoundError`

**Cause** : Lancement via `mvn clean package javafx:run` — le shade plugin remplace le JAR final, et `javafx:run` utilise ce JAR au lieu de `target/classes`

**Solution** : Utiliser exclusivement :
```bash
mvn clean compile javafx:run
```

### JavaFX 3D non disponible
**Symptôme** : Le cube panoramique n'apparaît pas (écran noir)

**Cause** : `Platform.isSupported(ConditionalFeature.SCENE3D)` retourne `false`

**Solution** : Vérifier que les drivers GPU (DirectX sous Windows) sont à jour et que la JVM a accès au rendu 3D

---

## 📊 Statistiques de la release

- **Lignes de code ajoutées** : ~350 (PanoramicCube)
- **Fichiers créés** : 1
- **Fichiers modifiés** : 8
- **Builds depuis v3.4.2** : 3680 → 3696 (16 builds)

---

## 🔗 Liens utiles

- **Dépôt GitHub** : https://github.com/llang57/editeurPanovisu
- **Guide d'installation Linux** : voir `INSTALLATION_LINUX.md`

---

**Note** : Cette version nécessite Java 25 ou supérieur avec `--enable-preview`.
