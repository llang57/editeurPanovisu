# 🔧 Version 3.1.0 - Correctif Critique de Case-Sensitivity Disponible

## ⚠️ Mise à jour CRITIQUE - Action requise

Chers utilisateurs de l'Éditeur Panovisu,

Nous venons de publier la **version 3.1.0** qui corrige un bug critique affectant le fonctionnement des visites virtuelles sur les **serveurs Linux**.

---

## 🐛 Problème corrigé

### Symptômes
Si vous avez exporté des visites avec les versions précédentes et que vous les hébergez sur un serveur Linux, vous avez probablement rencontré :
- ❌ Erreurs 404 dans la console du navigateur
- ❌ Interface de navigation non fonctionnelle
- ❌ Carte interactive ne s'affichant pas
- ❌ Fichiers `jquerymenu` et `openlayers` introuvables

### Cause
Le bug était dû à une **incohérence de casse** dans les noms de dossiers :
- Le code référençait : `jquerymenu` et `openlayers` (minuscules)
- Les dossiers créés étaient : `jqueryMenu` et `openLayers` (casse mixte)

Sur Windows (système insensible à la casse), tout fonctionnait. Sur Linux (sensible à la casse), les fichiers n'étaient pas trouvés.

### Solution
La version 3.1.0 corrige tous les chemins pour utiliser la bonne casse : **`jqueryMenu`** et **`openLayers`**.

---

## 📥 Installation

### Windows
Téléchargez et installez :
👉 [EditeurPanovisu-Setup-3.1.0.exe](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe)

### macOS
Téléchargez et installez :
👉 [EditeurPanovisu-3.1.0.dmg](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg)

### Linux
**Debian/Ubuntu** :
```bash
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb
sudo dpkg -i editeurpanovisu_3.1.0_amd64.deb
```

**Fedora/RHEL/CentOS** :
```bash
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm
sudo rpm -i editeurpanovisu-3.1.0.x86_64.rpm
```

---

## ⚡ ACTION REQUISE

### Pour les visites existantes hébergées sur Linux

**Vous DEVEZ ré-exporter toutes vos visites** avec la version 3.1.0 pour corriger les chemins :

1. ✅ Installer la version 3.1.0
2. ✅ Ouvrir chaque projet de visite virtuelle
3. ✅ Exporter à nouveau (Fichier > Exporter)
4. ✅ Remplacer les fichiers sur votre serveur

### Pour les nouvelles visites

Aucune action particulière, utilisez simplement la version 3.1.0.

---

## 📋 Détails techniques

**Fichiers modifiés :**
- `src/editeurpanovisu/EditeurPanovisu.java` (lignes 2675, 2681-2682)
- `panovisu/panovisuInit.js` (lignes 164-165)

**Build :** 2609  
**Date de release :** 16 octobre 2025  
**Commit :** `93ece95`

---

## 🔗 Liens utiles

- 📦 [Page de release complète](https://github.com/llang57/editeurPanovisu/releases/tag/v3.1.0)
- 📝 [Notes de version détaillées](https://github.com/llang57/editeurPanovisu/blob/master/RELEASE_NOTES_3.1.0.md)
- 🐛 [Signaler un problème](https://github.com/llang57/editeurPanovisu/issues)
- 💬 [Forum de discussion](https://github.com/llang57/editeurPanovisu/discussions)

---

## 💬 Questions ?

N'hésitez pas à poser vos questions dans cette discussion ou à ouvrir un ticket si vous rencontrez des problèmes.

**Merci d'utiliser l'Éditeur Panovisu !** 🎉
