# 🚀 Guide Rapide : Publier une Release GitHub

## 📦 Ce qui est prêt

✅ **Installeur Windows** : `target\installer\EditeurPanovisu-Setup-3.0.0.exe` (188 MB)  
✅ **SHA256 calculé** : `C527D56C0973C73DE3A7AA11C5C4BFA5F5DDB0687E85F7CD72E68EC6C3B47445`  
✅ **Notes de release** : `RELEASE_NOTES_v3.0.0.md`  
✅ **Workflow GitHub Actions** : `.github/workflows/build-release.yml`  
✅ **Build 2598** committé et pushé

---

## 🎯 Option 1 : Release manuelle (Windows uniquement)

### Étape 1 : Aller sur GitHub

1. Ouvrez : https://github.com/llang57/editeurPanovisu/releases/new
2. Connectez-vous si nécessaire

### Étape 2 : Remplir les champs

**Tag version** : `v3.0.0-build2598`

**Release title** : `EditeurPanovisu 3.0.0 - Build 2598`

**Description** (copier-coller) :

```markdown
## 🎉 EditeurPanovisu 3.0.0 - Build 2598

### ✨ Nouveautés

- **🧠 Cogito v2 Preview (671B)** : Nouveau modèle OpenRouter pour raisonnement avancé
- **9 modèles OpenRouter** premium disponibles
- **🔧 Fenêtre hotspot** : Dimensionnement adaptatif selon CSS
- **🗺️ Géolocalisation** : Leaflet.js + LocationIQ intégrés
- **🎨 Interface** : Thème AtlantaFX Dracula moderne

### 📥 Téléchargement Windows

**Fichier** : `EditeurPanovisu-Setup-3.0.0.exe` (188 MB)

**SHA256** : `C527D56C0973C73DE3A7AA11C5C4BFA5F5DDB0687E85F7CD72E68EC6C3B47445`

**Installation** :
1. Téléchargez le fichier `.exe`
2. Double-cliquez pour installer
3. Suivez l'assistant
4. Lancez via le raccourci Bureau

**Caractéristiques** :
- ✅ Runtime Java 25 embarqué
- ✅ Pas d'installation Java requise
- ✅ Installation sans admin
- ✅ Désinstallation propre

_(Installeurs macOS et Linux disponibles via GitHub Actions - voir ci-dessous)_

### 📋 Configuration requise

- **Windows** : Windows 10/11 64-bit
- **RAM** : 4 GB minimum, 8 GB recommandé
- **Disque** : 500 MB d'espace libre

### 📚 Documentation

- [Notes de release complètes](RELEASE_NOTES_v3.0.0.md)
- [Guide d'installation](doc/installation/INSTALLATION_UTILISATEUR.md)
- [Documentation complète](doc/README.md)

### 🙏 Remerciements

Merci à tous les contributeurs et testeurs !
```

### Étape 3 : Uploader l'installeur

1. Faites glisser `D:\developpement\java\editeurPanovisu\target\installer\EditeurPanovisu-Setup-3.0.0.exe` dans la zone "Attach binaries"
2. Ajoutez également `checksums.txt` si vous voulez

### Étape 4 : Publier

1. Décochez "Set as a pre-release" (c'est une version stable)
2. Cochez "Set as the latest release"
3. Cliquez sur **"Publish release"**

✅ **C'est fait !** La release est maintenant publique et téléchargeable.

---

## 🤖 Option 2 : Release automatique (Multi-plateforme)

Cette option créera automatiquement les installeurs Windows, macOS et Linux via GitHub Actions.

### Étape 1 : Créer et pousser un tag

```powershell
# Dans PowerShell (Windows)
cd D:\developpement\java\editeurPanovisu

# Créer le tag
git tag -a v3.0.0-build2598 -m "Release 3.0.0 Build 2598"

# Pousser le tag vers GitHub
git push origin v3.0.0-build2598
```

### Étape 2 : GitHub Actions démarre automatiquement

1. Allez sur : https://github.com/llang57/editeurPanovisu/actions
2. Vous verrez le workflow "🚀 Build Multi-Platform Installers" en cours
3. Attendez ~15-20 minutes que tous les builds se terminent

### Étape 3 : La release est créée automatiquement

Une fois le workflow terminé :
1. Allez sur : https://github.com/llang57/editeurPanovisu/releases
2. La release `v3.0.0-build2598` est créée avec tous les installeurs
3. Les checksums SHA256 sont calculés automatiquement

✅ **Avantages** :
- Installeurs pour **Windows**, **macOS** et **Linux**
- Build reproductible et automatique
- Checksums calculés automatiquement
- Notes de release générées

❌ **Inconvénient** :
- Nécessite d'attendre ~15-20 minutes

---

## 🔄 Option 3 : Déclencher manuellement le workflow

Si vous voulez les 3 installeurs mais que vous ne voulez pas créer de tag :

1. Allez sur : https://github.com/llang57/editeurPanovisu/actions
2. Cliquez sur "🚀 Build Multi-Platform Installers"
3. Cliquez sur "Run workflow"
4. Entrez la version : `3.0.0`
5. Cliquez sur "Run workflow" (vert)

**Note** : Cette option crée les installeurs mais PAS la release. Vous devrez télécharger les artifacts et créer la release manuellement.

---

## 📊 Vérification après publication

### Tester le téléchargement

1. Allez sur : https://github.com/llang57/editeurPanovisu/releases/latest
2. Téléchargez l'installeur Windows
3. Vérifiez le SHA256 :
   ```powershell
   Get-FileHash EditeurPanovisu-Setup-3.0.0.exe -Algorithm SHA256
   ```
4. Comparez avec : `C527D56C0973C73DE3A7AA11C5C4BFA5F5DDB0687E85F7CD72E68EC6C3B47445`

### Tester l'installation

1. Lancez l'installeur
2. Suivez l'assistant
3. Vérifiez que l'application se lance correctement
4. Testez les fonctionnalités principales

---

## 🐛 Dépannage

### "Release with this tag already exists"

Si vous avez déjà créé le tag :
```powershell
# Supprimer le tag local
git tag -d v3.0.0-build2598

# Supprimer le tag distant
git push origin :refs/tags/v3.0.0-build2598

# Recréer le tag
git tag -a v3.0.0-build2598 -m "Release 3.0.0 Build 2598"
git push origin v3.0.0-build2598
```

### "Cannot upload assets"

Si l'upload échoue :
1. Sauvegardez votre description de release
2. Supprimez la release (pas le tag)
3. Recréez la release avec le même tag
4. Réessayez l'upload

### "GitHub Actions workflow failed"

1. Allez sur l'onglet Actions
2. Cliquez sur le workflow échoué
3. Consultez les logs pour voir l'erreur
4. Corrigez le problème
5. Re-poussez le tag pour relancer

---

## 📝 Checklist finale

Avant de publier, vérifiez :

- [ ] ✅ Build 2598 compilé sans erreur
- [ ] ✅ Installeur Windows créé (188 MB)
- [ ] ✅ SHA256 calculé et vérifié
- [ ] ✅ Notes de release rédigées
- [ ] ✅ Documentation à jour
- [ ] ✅ Commits pushés sur GitHub
- [ ] ✅ Tag créé et poussé (si Option 2/3)
- [ ] ✅ Release publiée
- [ ] ✅ Installeur téléchargé et testé
- [ ] ✅ Application testée après installation

---

## 🎉 Félicitations !

Votre release est maintenant publique et disponible au téléchargement pour tous les utilisateurs !

**Liens utiles** :
- **Page de release** : https://github.com/llang57/editeurPanovisu/releases/latest
- **Lien de téléchargement direct** : https://github.com/llang57/editeurPanovisu/releases/download/v3.0.0-build2598/EditeurPanovisu-Setup-3.0.0.exe

Vous pouvez partager ces liens avec vos utilisateurs.

---

**Date** : 14 octobre 2025  
**Build** : 2598  
**Version** : 3.0.0
