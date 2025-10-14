# EditeurPanovisu v3.0.0 - Build 2598

## 🎉 Notes de Release

Date : 14 octobre 2025

---

## ✨ Nouveautés

### 🧠 Intelligence Artificielle

- **Cogito v2 Preview (671B paramètres)** : Nouveau modèle OpenRouter pour raisonnement avancé
- **9 modèles OpenRouter premium** disponibles :
  - ⭐ Claude Sonnet 4.5
  - 🔷 Claude 3 Opus
  - 📅 Claude 3.5 Sonnet
  - 🇫🇷 Mistral Nemo
  - 🧠 Cogito v2 Preview (671B) **NOUVEAU**
  - 💰 GPT OSS 120B
  - 🌍 GPT-4 Turbo
  - 🆓 Gemini Pro
  - 🆓 Llama 3.1 8B
- Toggle Online/Offline (🌐/🔸) pour basculer entre OpenRouter et Ollama
- Gestion d'erreur intelligente avec fallback automatique

### 🎨 Interface

- **Thème AtlantaFX Dracula** moderne et élégant
- **Fenêtre hotspot** : Dimensionnement adaptatif selon CSS
  - Hauteur cellule optimisée (36px)
  - Largeur adaptée (150px)
  - Élimination scrollbar inutile
- Meilleure lisibilité globale

### 🗺️ Géolocalisation

- **Leaflet.js** intégré pour cartes interactives
- **LocationIQ** pour recherche d'adresse
- **OpenStreetMap + ESRI ArcGIS** pour fond de carte
- Carte interactive dans l'éditeur

### 🔧 Améliorations techniques

- Migration **DeepSeek-R1 70B → 32B** (plus stable)
- Build automatisé avec GitHub Actions
- Documentation complète

---

## 📥 Téléchargements

### 🪟 Windows 10/11 (64-bit)

**Fichier** : `EditeurPanovisu-Setup-3.0.0.exe`  
**Taille** : 187.84 MB  
**SHA256** : `C527D56C0973C73DE3A7AA11C5C4BFA5F5DDB0687E85F7CD72E68EC6C3B47445`

#### Installation
1. Téléchargez le fichier `.exe`
2. Double-cliquez pour lancer l'installation
3. Suivez l'assistant (installation dans `%LOCALAPPDATA%\EditeurPanovisu`)
4. Lancez via le raccourci Bureau ou Menu Démarrer

**Caractéristiques** :
- ✅ Runtime Java 25 embarqué
- ✅ Aucune installation Java requise
- ✅ Pas de droits administrateur nécessaires
- ✅ Désinstallation propre via Panneau de configuration

---

### 🍎 macOS 11+ (Big Sur ou supérieur)

**Fichier** : `EditeurPanovisu-3.0.0.dmg`  
**Taille** : ~200 MB  
**SHA256** : _(sera calculé par GitHub Actions)_

#### Installation
1. Téléchargez le fichier `.dmg`
2. Ouvrez-le (double-clic)
3. Glissez l'icône **EditeurPanovisu** vers le dossier **Applications**
4. Lancez depuis le Launchpad ou Applications

**Note** : Si macOS bloque l'ouverture (app non signée), faites :
- Clic droit → Ouvrir
- Confirmez "Ouvrir quand même"

---

### 🐧 Linux

#### Debian/Ubuntu (.deb)

**Fichier** : `editeurpanovisu_3.0.0-1_amd64.deb`  
**Taille** : ~180 MB  
**SHA256** : _(sera calculé par GitHub Actions)_

**Installation** :
```bash
# Téléchargez le fichier .deb puis :
sudo dpkg -i editeurpanovisu_3.0.0-1_amd64.deb

# Ou avec apt (résout les dépendances)
sudo apt install ./editeurpanovisu_3.0.0-1_amd64.deb
```

**Lancement** :
```bash
editeurpanovisu
# ou depuis le menu Applications > Graphisme
```

**Désinstallation** :
```bash
sudo apt remove editeurpanovisu
```

---

#### RedHat/Fedora (.rpm)

**Fichier** : `editeurpanovisu-3.0.0-1.x86_64.rpm`  
**Taille** : ~180 MB  
**SHA256** : _(sera calculé par GitHub Actions)_

**Installation** :
```bash
# Téléchargez le fichier .rpm puis :
sudo rpm -i editeurpanovisu-3.0.0-1.x86_64.rpm

# Ou avec dnf (Fedora)
sudo dnf install ./editeurpanovisu-3.0.0-1.x86_64.rpm

# Ou avec yum (RedHat/CentOS)
sudo yum install ./editeurpanovisu-3.0.0-1.x86_64.rpm
```

**Lancement** :
```bash
editeurpanovisu
# ou depuis le menu Applications > Graphisme
```

**Désinstallation** :
```bash
sudo rpm -e editeurpanovisu
# ou
sudo dnf remove editeurpanovisu
```

---

## 📋 Configuration requise

| Plateforme | Minimum | Recommandé |
|------------|---------|------------|
| **Windows** | Windows 10 64-bit | Windows 11 |
| **macOS** | macOS 11 Big Sur | macOS 14 Sonoma |
| **Linux** | Debian 11 / Ubuntu 20.04 / Fedora 35 | Debian 12 / Ubuntu 24.04 / Fedora 41 |
| **RAM** | 4 GB | 8 GB |
| **Disque** | 500 MB libres | 1 GB libre |
| **Résolution** | 1280x720 | 1920x1080 |

**⚠️ Important** : Java runtime **inclus** dans tous les installeurs. Aucune installation Java séparée requise !

---

## 🔐 Vérification d'intégrité

### Windows

```powershell
# PowerShell
Get-FileHash EditeurPanovisu-Setup-3.0.0.exe -Algorithm SHA256
```

**Hash attendu** : `C527D56C0973C73DE3A7AA11C5C4BFA5F5DDB0687E85F7CD72E68EC6C3B47445`

### macOS/Linux

```bash
sha256sum EditeurPanovisu-3.0.0.dmg
# ou
sha256sum editeurpanovisu_3.0.0-1_amd64.deb
sha256sum editeurpanovisu-3.0.0-1.x86_64.rpm
```

---

## 🐛 Bugs connus

Aucun bug critique connu dans cette version.

Si vous rencontrez un problème :
1. Consultez la [documentation](doc/README.md)
2. Vérifiez les [issues existantes](https://github.com/llang57/editeurPanovisu/issues)
3. Créez une nouvelle issue si nécessaire

---

## 📚 Documentation

- **[Guide d'installation complet](doc/installation/INSTALLATION_UTILISATEUR.md)**
- **[Documentation système IA](doc/guides/SYSTEME_IA_COMPLET.md)**
- **[Configuration API](doc/guides/CONFIGURATION_API_KEYS.md)**
- **[Guide géolocalisation](doc/geolocalisation/GUIDE_GEOLOCALISATION.md)**
- **[Installateurs multi-plateformes](doc/installation/INSTALLATEURS_MULTIPLATEFORME.md)**

---

## 🔄 Mise à jour depuis version précédente

Si vous avez déjà une version installée :

### Windows
1. Désinstallez l'ancienne version (Panneau de configuration)
2. Installez la nouvelle version
3. Vos projets et préférences sont préservés

### macOS
1. Glissez l'ancienne app vers la Corbeille
2. Installez la nouvelle version
3. Vos données dans `~/Library/Application Support/EditeurPanovisu` sont préservées

### Linux
```bash
# Debian/Ubuntu
sudo apt upgrade ./editeurpanovisu_3.0.0-1_amd64.deb

# RedHat/Fedora
sudo dnf upgrade ./editeurpanovisu-3.0.0-1.x86_64.rpm
```

---

## 🛠️ Build depuis les sources

Si vous préférez compiler depuis les sources :

```bash
# Cloner le dépôt
git clone https://github.com/llang57/editeurPanovisu.git
cd editeurPanovisu

# Compiler avec Maven
mvn clean package

# Créer l'installeur Windows
.\build-installer.ps1

# Créer l'installeur macOS (sur Mac)
./build-installer-mac.sh

# Créer l'installeur Linux (sur Linux)
./build-installer-linux.sh
```

**Prérequis** :
- Java 25 JDK
- Maven 3.9+
- (Windows) Inno Setup 6.5+
- (macOS) Xcode Command Line Tools
- (Linux) fakeroot, rpm-build

---

## 🙏 Remerciements

Merci à tous les contributeurs et testeurs qui ont participé à cette version !

Un remerciement spécial aux projets open-source utilisés :
- JavaFX & AtlantaFX
- Leaflet.js
- OpenStreetMap & LocationIQ
- OpenRouter & Ollama
- Three.js
- Et tous les autres...

---

## 📝 Changelog complet

### Build 2598 (14 octobre 2025)

**Ajouts** :
- ➕ Cogito v2 Preview (671B) dans OpenRouter
- ➕ Documentation multi-plateforme complète
- ➕ Workflow GitHub Actions automatique

**Corrections** :
- 🔧 Fenêtre hotspot : cellHeight 28→36px, padding 4→10px
- 🔧 Largeur fenêtre hotspot : 120→150px
- 🔧 Calcul hauteur dynamique selon nombre d'items

**Améliorations** :
- ⚡ Migration DeepSeek-R1 32B (plus stable)
- 📚 Documentation enrichie (guides, debug, installation)
- 🎨 Thème Dracula optimisé

---

## 📞 Contact & Support

- **GitHub** : https://github.com/llang57/editeurPanovisu
- **Issues** : https://github.com/llang57/editeurPanovisu/issues
- **Discussions** : https://github.com/llang57/editeurPanovisu/discussions

---

**Version** : 3.0.0  
**Build** : 2598  
**Date** : 14 octobre 2025  
**Licence** : Voir [LICENSE](LICENSE)
