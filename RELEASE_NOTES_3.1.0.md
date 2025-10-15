# 🔧 EditeurPanovisu v3.1.0 - Correctif Critique Case-Sensitivity

**Date de release** : 15 octobre 2025  
**Build** : 2609  
**Priorité** : ⚠️ **CRITIQUE** - Mise à jour fortement recommandée

---

## 🚨 Problème Résolu

### Bug critique de case-sensitivity (Windows vs Linux)

**Symptômes** :
- Les visites virtuelles exportées fonctionnaient sur Windows mais **échouaient sur serveurs Linux/Unix**
- Erreurs 404 pour les fichiers JavaScript (OpenLayers, jQuery Context Menu)
- Messages d'erreur dans la console : `ERR_ABORTED 404` pour `jquerymenu/` et `openlayers/`

**Cause** :
- Les chemins générés utilisaient la mauvaise casse : `jquerymenu` au lieu de `jqueryMenu`, `openlayers` au lieu de `openLayers`
- Windows (NTFS) est insensible à la casse → ✅ fonctionnait
- Linux (ext4) est sensible à la casse → ❌ fichiers introuvables

**Solution** :
- Correction des chemins dans `EditeurPanovisu.java` (lignes 2675, 2681-2682)
- Correction des chemins dans `panovisuInit.js` (lignes 164-165)
- Les dossiers corrects sont : `panovisu/libs/jqueryMenu/` et `panovisu/libs/openLayers/`

---

## 📋 Changements Détaillés

### Corrections de Code
- ✅ `EditeurPanovisu.java` : Correction casse CSS `jqueryMenu`
- ✅ `EditeurPanovisu.java` : Correction casse JS `openLayers`
- ✅ `panovisuInit.js` : Correction includes dynamiques `jqueryMenu`

### Fichiers Modifiés
```
src/editeurpanovisu/EditeurPanovisu.java    (3 lignes modifiées)
panovisu/panovisuInit.js                    (2 lignes modifiées)
```

---

## 📦 Installateurs

Les installateurs **incluent Java 25 et toutes les dépendances**. Aucune installation Java requise pour les utilisateurs finaux.

### Windows
- **Fichier** : `EditeurPanovisu-Setup-3.1.0.exe` (187 MB)
- **Installation** : AppData\Local\EditeurPanovisu (pas de droits admin requis)
- **Raccourcis** : Bureau + Menu Démarrer

### macOS
- **Fichier** : `EditeurPanovisu-3.1.0.dmg`
- **Compatible** : macOS 10.14+ (Intel & Apple Silicon)

### Linux
- **Fichier** : `EditeurPanovisu-3.1.0.deb` (Debian/Ubuntu)
- **Fichier** : `EditeurPanovisu-3.1.0.rpm` (RedHat/Fedora)

---

## ⚡ Actions Requises (IMPORTANT)

### Pour tous les utilisateurs :

1. **Télécharger et installer** la version 3.1.0
2. **Ré-exporter TOUTES vos visites existantes** avec cette nouvelle version
3. **Re-uploader** les nouvelles visites sur votre serveur de production

> ⚠️ **Les visites exportées avec les versions antérieures ne fonctionneront PAS correctement sur serveurs Linux !**

### Vérification après mise à jour :

```bash
# Vérifiez que vos fichiers ont la bonne casse :
ls -la panovisu/libs/jqueryMenu/   # ← M majuscule
ls -la panovisu/libs/openLayers/   # ← L majuscule
```

---

## 🔍 Détails Techniques

### Environnement de Développement
- **Java** : OpenJDK 25 (Eclipse Temurin)
- **JavaFX** : 19.0.2.1
- **Maven** : 3.9.9
- **Build** : 2609

### Compatibilité
- ✅ Windows 10/11 (64-bit)
- ✅ macOS 10.14+ (Intel & Apple Silicon)
- ✅ Linux (Ubuntu 20.04+, Fedora 35+)

### Serveurs Web Testés
- ✅ Apache 2.4 (Linux)
- ✅ Nginx (Linux)
- ✅ Wamp/Xampp (Windows)

---

## 📚 Documentation

- **Site Web** : https://lemondea360.fr/panovisu
- **Wiki** : https://github.com/llang57/editeurPanovisu/wiki
- **Documentation API** : https://llang57.github.io/editeurPanovisu
- **Support** : GitHub Issues

---

## 🙏 Remerciements

Merci à tous les utilisateurs qui ont signalé ce problème critique de déploiement !

---

## 📜 Historique des Versions

### v3.1.0 (15 octobre 2025) - **CETTE VERSION**
- 🔧 Fix critique : Case-sensitivity jqueryMenu/openLayers

### v3.0.0 (11 octobre 2025)
- 🎨 Nouvelle interface AtlantaFX Dracula
- 🤖 Intégration IA (OpenRouter, HuggingFace, Ollama)
- 🗺️ Géolocalisation OpenStreetMap/Leaflet
- 📝 Markdown/Emoji dans descriptions

---

**Checksums** (à venir après upload des installateurs)
```
SHA256(EditeurPanovisu-Setup-3.1.0.exe) = [à générer]
SHA256(EditeurPanovisu-3.1.0.dmg)       = [à générer]
SHA256(EditeurPanovisu-3.1.0.deb)       = [à générer]
```
