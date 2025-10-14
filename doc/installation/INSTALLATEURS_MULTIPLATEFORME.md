# 🚀 Création d'installateurs multi-plateformes avec jpackage

## 📋 Vue d'ensemble

Ce guide explique comment créer des installateurs natifs pour **Windows**, **macOS** et **Linux** en utilisant `jpackage` (intégré à Java 25).

**Version actuelle** : Build 2598 (14 octobre 2025)

---

## 🎯 Prérequis par plateforme

### Windows

✅ **Déjà configuré !** Votre système actuel fonctionne.

- **Java 25** (OpenJDK Temurin-25+36) ✅
- **Maven 3.9.9** ✅
- **Inno Setup 6.5.4** (pour créer l'installeur `.exe`) ✅
- **Script PowerShell** : `build-installer.ps1` ✅

**Taille installeur** : ~168 MB

### macOS

- **Java 25 JDK** (télécharger depuis [Adoptium](https://adoptium.net/))
- **Maven 3.9.9+**
- **Xcode Command Line Tools** (pour signer l'application)
  ```bash
  xcode-select --install
  ```

**Formats disponibles** :
- `.dmg` : Image disque (drag & drop)
- `.pkg` : Installeur macOS natif

### Linux

- **Java 25 JDK**
- **Maven 3.9.9+**
- **fakeroot** (pour créer `.deb`)
  ```bash
  sudo apt-get install fakeroot
  ```
- **rpmbuild** (pour créer `.rpm`)
  ```bash
  sudo yum install rpm-build  # RedHat/CentOS/Fedora
  sudo zypper install rpm-build  # openSUSE
  ```

**Formats disponibles** :
- `.deb` : Debian/Ubuntu
- `.rpm` : RedHat/CentOS/Fedora

---

## 🔧 Configuration Maven (pom.xml)

Votre `pom.xml` actuel contient déjà la configuration de base. Voici les ajouts nécessaires pour le multi-plateforme :

### Configuration jpackage plugin

```xml
<build>
    <plugins>
        <!-- Plugin jpackage pour créer des installateurs natifs -->
        <plugin>
            <groupId>org.panteleyev</groupId>
            <artifactId>jpackage-maven-plugin</artifactId>
            <version>1.6.5</version>
            <configuration>
                <name>EditeurPanovisu</name>
                <appVersion>3.0.0</appVersion>
                <vendor>PanoVisu</vendor>
                <destination>target/installer</destination>
                
                <!-- Runtime Java embarqué -->
                <runtimeImage>${java.home}</runtimeImage>
                
                <!-- Module path et mainClass -->
                <modulePath>${project.build.directory}/jmods</modulePath>
                <mainJar>${project.artifactId}-${project.version}.jar</mainJar>
                <mainClass>editeurpanovisu.EditeurPanovisu</mainClass>
                
                <!-- Icônes -->
                <icon>images/panovisu.${icon.extension}</icon>
                
                <!-- JVM Options -->
                <javaOptions>
                    <option>-Xmx2048m</option>
                    <option>-Dfile.encoding=UTF-8</option>
                </javaOptions>
            </configuration>
            
            <executions>
                <!-- Windows -->
                <execution>
                    <id>win</id>
                    <configuration>
                        <type>MSI</type>  <!-- ou EXE -->
                        <winMenu>true</winMenu>
                        <winDirChooser>true</winDirChooser>
                        <winShortcut>true</winShortcut>
                        <winPerUserInstall>false</winPerUserInstall>
                    </configuration>
                </execution>
                
                <!-- macOS -->
                <execution>
                    <id>mac</id>
                    <configuration>
                        <type>DMG</type>  <!-- ou PKG -->
                        <macPackageName>EditeurPanovisu</macPackageName>
                        <macPackageIdentifier>fr.panovisu.editeur</macPackageIdentifier>
                    </configuration>
                </execution>
                
                <!-- Linux -->
                <execution>
                    <id>linux</id>
                    <configuration>
                        <type>DEB</type>  <!-- ou RPM -->
                        <linuxShortcut>true</linuxShortcut>
                        <linuxMenuGroup>Graphics</linuxMenuGroup>
                        <linuxPackageName>editeurpanovisu</linuxPackageName>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

---

## 🪟 Windows (Méthode actuelle - Inno Setup)

### Votre workflow actuel ✅

```powershell
# Exécuter le script PowerShell
.\build-installer.ps1
```

**Ce script fait** :
1. `mvn clean package` (compile et package le JAR)
2. `jpackage --type app-image` (crée l'app-image avec runtime Java)
3. Copie les fichiers nécessaires
4. `iscc installer.iss` (compile l'installeur Inno Setup)

**Résultat** : `target\installer\EditeurPanovisu-Setup-3.0.0.exe` (~168 MB)

### Alternative : jpackage MSI/EXE natif

Si vous souhaitez utiliser jpackage seul sans Inno Setup :

```powershell
# Build du projet
mvn clean package

# Création de l'installeur Windows (MSI)
jpackage `
  --type msi `
  --name EditeurPanovisu `
  --app-version 3.0.0 `
  --vendor PanoVisu `
  --icon images/panovisu.ico `
  --input target `
  --main-jar editeurPanovisu-3.0.0-SNAPSHOT.jar `
  --main-class editeurpanovisu.EditeurPanovisu `
  --runtime-image "C:\Program Files\Java\jdk-25" `
  --dest target/installer `
  --win-dir-chooser `
  --win-menu `
  --win-shortcut `
  --java-options "-Xmx2048m" `
  --java-options "-Dfile.encoding=UTF-8"
```

**Avantage Inno Setup** : Plus de contrôle, customisation avancée  
**Avantage jpackage MSI** : Plus simple, pas de dépendance externe

---

## 🍎 macOS

### Prérequis spécifiques

1. **Avoir accès à un Mac** (ou VM macOS)
2. **Installer Java 25 et Maven** :
   ```bash
   # Via Homebrew
   brew install openjdk@25
   brew install maven
   
   # Configurer JAVA_HOME
   export JAVA_HOME=/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home
   ```

3. **Préparer l'icône macOS** :
   - Format : `.icns` (pas `.ico`)
   - Outil : [Image2Icon](https://img2icons.com/) ou `iconutil`
   - Placer dans `images/panovisu.icns`

### Script de build macOS

Créez `build-installer-mac.sh` :

```bash
#!/bin/bash
set -e

echo "=== Build EditeurPanovisu pour macOS ==="

# Variables
APP_VERSION="3.0.0"
APP_NAME="EditeurPanovisu"
BUILD_DIR="target"
INSTALLER_DIR="$BUILD_DIR/installer"

# 1. Clean et package
echo "➤ Compilation du projet..."
mvn clean package

# 2. Création du .dmg
echo "➤ Création de l'image disque (.dmg)..."
jpackage \
  --type dmg \
  --name "$APP_NAME" \
  --app-version "$APP_VERSION" \
  --vendor "PanoVisu" \
  --icon "images/panovisu.icns" \
  --input "$BUILD_DIR" \
  --main-jar "editeurPanovisu-3.0.0-SNAPSHOT.jar" \
  --main-class "editeurpanovisu.EditeurPanovisu" \
  --runtime-image "$JAVA_HOME" \
  --dest "$INSTALLER_DIR" \
  --mac-package-name "$APP_NAME" \
  --mac-package-identifier "fr.panovisu.editeur" \
  --java-options "-Xmx2048m" \
  --java-options "-Dfile.encoding=UTF-8" \
  --verbose

echo "✅ Installeur macOS créé : $INSTALLER_DIR/$APP_NAME-$APP_VERSION.dmg"
echo "Taille: $(du -h "$INSTALLER_DIR/$APP_NAME-$APP_VERSION.dmg" | cut -f1)"
```

### Exécution

```bash
# Rendre exécutable
chmod +x build-installer-mac.sh

# Exécuter
./build-installer-mac.sh
```

**Résultat** : `target/installer/EditeurPanovisu-3.0.0.dmg` (~200 MB)

### Signature de l'application (optionnel mais recommandé)

Pour distribuer hors App Store, il faut signer :

```bash
# Créer un certificat de développeur Apple
# 1. Inscrivez-vous au Apple Developer Program (99$/an)
# 2. Créez un certificat dans Xcode

# Signer l'app
codesign --force --deep --sign "Developer ID Application: Votre Nom" \
  target/installer/EditeurPanovisu.app

# Vérifier la signature
codesign --verify --deep --strict target/installer/EditeurPanovisu.app
```

---

## 🐧 Linux

### Debian/Ubuntu (.deb)

Créez `build-installer-linux.sh` :

```bash
#!/bin/bash
set -e

echo "=== Build EditeurPanovisu pour Linux (Debian/Ubuntu) ==="

# Variables
APP_VERSION="3.0.0"
APP_NAME="editeurpanovisu"
BUILD_DIR="target"
INSTALLER_DIR="$BUILD_DIR/installer"

# 1. Clean et package
echo "➤ Compilation du projet..."
mvn clean package

# 2. Création du .deb
echo "➤ Création du package Debian (.deb)..."
jpackage \
  --type deb \
  --name "$APP_NAME" \
  --app-version "$APP_VERSION" \
  --vendor "PanoVisu" \
  --icon "images/panovisu.png" \
  --input "$BUILD_DIR" \
  --main-jar "editeurPanovisu-3.0.0-SNAPSHOT.jar" \
  --main-class "editeurpanovisu.EditeurPanovisu" \
  --runtime-image "$JAVA_HOME" \
  --dest "$INSTALLER_DIR" \
  --linux-package-name "$APP_NAME" \
  --linux-app-category "Graphics" \
  --linux-shortcut \
  --linux-menu-group "Graphics" \
  --java-options "-Xmx2048m" \
  --java-options "-Dfile.encoding=UTF-8" \
  --verbose

echo "✅ Installeur Linux créé : $INSTALLER_DIR/${APP_NAME}_${APP_VERSION}-1_amd64.deb"
echo "Taille: $(du -h "$INSTALLER_DIR/${APP_NAME}_${APP_VERSION}-1_amd64.deb" | cut -f1)"
```

**Installation** :
```bash
sudo dpkg -i target/installer/editeurpanovisu_3.0.0-1_amd64.deb
```

### RedHat/CentOS/Fedora (.rpm)

Modifiez le script pour utiliser `--type rpm` :

```bash
jpackage \
  --type rpm \
  --name "$APP_NAME" \
  # ... (mêmes options)
```

**Installation** :
```bash
sudo rpm -i target/installer/editeurpanovisu-3.0.0-1.x86_64.rpm
```

---

## 📦 GitHub Releases - Publication

### 1. Créer une release sur GitHub

#### Via l'interface web

1. Allez sur : https://github.com/llang57/editeurPanovisu/releases
2. Cliquez sur **"Draft a new release"**
3. **Tag version** : `v3.0.0-build2598`
4. **Release title** : `EditeurPanovisu 3.0.0 - Build 2598`
5. **Description** :
   ```markdown
   ## 🎉 EditeurPanovisu 3.0.0 - Build 2598
   
   ### ✨ Nouveautés
   
   #### 🧠 Système IA amélioré
   - **9 modèles OpenRouter** premium disponibles
   - **Cogito v2 Preview** (671B paramètres) ajouté
   - **DeepSeek-R1 32B** pour raisonnement avancé
   - Toggle Online/Offline (🌐/🔸)
   - Gestion d'erreur intelligente
   
   #### 🎨 Interface modernisée
   - Thème AtlantaFX Dracula
   - Fenêtre hotspot adaptée aux nouveaux CSS
   - Meilleure lisibilité
   
   #### 🗺️ Géolocalisation
   - Leaflet.js intégré
   - LocationIQ pour recherche d'adresse
   - OpenStreetMap + ESRI ArcGIS
   
   ### 📥 Téléchargements
   
   | Plateforme | Fichier | Taille | SHA256 |
   |------------|---------|--------|--------|
   | 🪟 Windows 10/11 | `EditeurPanovisu-Setup-3.0.0.exe` | 168 MB | `abc123...` |
   | 🍎 macOS 11+ | `EditeurPanovisu-3.0.0.dmg` | 200 MB | `def456...` |
   | 🐧 Linux (Debian/Ubuntu) | `editeurpanovisu_3.0.0-1_amd64.deb` | 180 MB | `ghi789...` |
   | 🐧 Linux (RedHat/Fedora) | `editeurpanovisu-3.0.0-1.x86_64.rpm` | 180 MB | `jkl012...` |
   
   ### 📋 Configuration requise
   
   - **Windows** : Windows 10/11 64-bit
   - **macOS** : macOS 11 Big Sur ou supérieur
   - **Linux** : Debian 11+, Ubuntu 20.04+, Fedora 35+
   - **RAM** : 4 GB minimum, 8 GB recommandé
   - **Disque** : 500 MB d'espace libre
   
   ### 🔧 Installation
   
   #### Windows
   1. Téléchargez `EditeurPanovisu-Setup-3.0.0.exe`
   2. Double-cliquez pour installer
   3. Suivez l'assistant d'installation
   
   #### macOS
   1. Téléchargez `EditeurPanovisu-3.0.0.dmg`
   2. Ouvrez le fichier `.dmg`
   3. Glissez l'icône vers Applications
   
   #### Linux
   ```bash
   # Debian/Ubuntu
   sudo dpkg -i editeurpanovisu_3.0.0-1_amd64.deb
   
   # RedHat/Fedora
   sudo rpm -i editeurpanovisu-3.0.0-1.x86_64.rpm
   ```
   
   ### 📚 Documentation
   
   - [Guide d'installation](doc/installation/INSTALLATION_UTILISATEUR.md)
   - [Documentation système IA](doc/guides/SYSTEME_IA_COMPLET.md)
   - [Configuration API](doc/guides/CONFIGURATION_API_KEYS.md)
   
   ### 🐛 Bugs connus
   
   Aucun bug critique connu dans cette version.
   
   ### 🙏 Remerciements
   
   Merci à tous les contributeurs et testeurs !
   ```

6. **Glissez-déposez** vos installateurs dans la zone "Attach binaries"
7. Cliquez sur **"Publish release"**

#### Via GitHub CLI (gh)

```bash
# Installer GitHub CLI
# Windows: winget install GitHub.cli
# macOS: brew install gh
# Linux: voir https://cli.github.com/

# Authentification
gh auth login

# Créer une release avec fichiers
gh release create v3.0.0-build2598 \
  --title "EditeurPanovisu 3.0.0 - Build 2598" \
  --notes-file release-notes.md \
  target/installer/EditeurPanovisu-Setup-3.0.0.exe \
  target/installer/EditeurPanovisu-3.0.0.dmg \
  target/installer/editeurpanovisu_3.0.0-1_amd64.deb \
  target/installer/editeurpanovisu-3.0.0-1.x86_64.rpm
```

### 2. Calculer les checksums SHA256

Pour la sécurité, fournissez les checksums :

```powershell
# Windows (PowerShell)
Get-FileHash target\installer\EditeurPanovisu-Setup-3.0.0.exe -Algorithm SHA256 | Format-List
```

```bash
# macOS/Linux
sha256sum target/installer/EditeurPanovisu-3.0.0.dmg
sha256sum target/installer/editeurpanovisu_3.0.0-1_amd64.deb
sha256sum target/installer/editeurpanovisu-3.0.0-1.x86_64.rpm
```

Créez un fichier `checksums.txt` :

```
abc123... EditeurPanovisu-Setup-3.0.0.exe
def456... EditeurPanovisu-3.0.0.dmg
ghi789... editeurpanovisu_3.0.0-1_amd64.deb
jkl012... editeurpanovisu-3.0.0-1.x86_64.rpm
```

---

## 🤖 Automatisation avec GitHub Actions

Créez `.github/workflows/build-installers.yml` :

```yaml
name: Build Multi-Platform Installers

on:
  push:
    tags:
      - 'v*'

jobs:
  build-windows:
    runs-on: windows-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Setup Java 25
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '25'
      
      - name: Build with Maven
        run: mvn clean package
      
      - name: Create Windows Installer
        run: .\build-installer.ps1
      
      - name: Upload Windows Installer
        uses: actions/upload-artifact@v4
        with:
          name: windows-installer
          path: target/installer/*.exe

  build-macos:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Setup Java 25
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '25'
      
      - name: Build with Maven
        run: mvn clean package
      
      - name: Create macOS Installer
        run: ./build-installer-mac.sh
      
      - name: Upload macOS Installer
        uses: actions/upload-artifact@v4
        with:
          name: macos-installer
          path: target/installer/*.dmg

  build-linux:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Setup Java 25
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '25'
      
      - name: Install fakeroot
        run: sudo apt-get install -y fakeroot
      
      - name: Build with Maven
        run: mvn clean package
      
      - name: Create Linux Installers
        run: ./build-installer-linux.sh
      
      - name: Upload Linux Installers
        uses: actions/upload-artifact@v4
        with:
          name: linux-installers
          path: target/installer/*.deb

  create-release:
    needs: [build-windows, build-macos, build-linux]
    runs-on: ubuntu-latest
    steps:
      - name: Download all artifacts
        uses: actions/download-artifact@v4
      
      - name: Create Release
        uses: softprops/action-gh-release@v1
        with:
          files: |
            windows-installer/*
            macos-installer/*
            linux-installers/*
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

### Déclencher le workflow

```bash
# Créer un tag
git tag -a v3.0.0-build2598 -m "Release 3.0.0 Build 2598"

# Pousser le tag
git push origin v3.0.0-build2598
```

GitHub Actions construira automatiquement les 3 installateurs et créera une release !

---

## 📊 Comparaison des tailles

| Plateforme | Format | Taille estimée | Runtime inclus |
|------------|--------|----------------|----------------|
| Windows | `.exe` (Inno Setup) | ~168 MB | ✅ Java 25 |
| Windows | `.msi` (jpackage) | ~180 MB | ✅ Java 25 |
| macOS | `.dmg` | ~200 MB | ✅ Java 25 |
| macOS | `.pkg` | ~200 MB | ✅ Java 25 |
| Linux | `.deb` | ~180 MB | ✅ Java 25 |
| Linux | `.rpm` | ~180 MB | ✅ Java 25 |

---

## 🔐 Signature des applications

### Windows (Authenticode)

Nécessite un certificat de signature de code (~300€/an).

```powershell
signtool sign /f certificat.pfx /p motdepasse /t http://timestamp.digicert.com target/installer/EditeurPanovisu-Setup-3.0.0.exe
```

### macOS (Apple Developer)

Nécessite un compte Apple Developer (99$/an).

```bash
# Signer l'app
codesign --force --deep --sign "Developer ID Application: Votre Nom" EditeurPanovisu.app

# Notariser (validation Apple)
xcrun altool --notarize-app --file EditeurPanovisu.dmg --primary-bundle-id fr.panovisu.editeur
```

### Linux

Signature GPG pour les dépôts APT/YUM.

```bash
# Créer une clé GPG
gpg --gen-key

# Signer le package
dpkg-sig --sign builder editeurpanovisu_3.0.0-1_amd64.deb
```

---

## 📝 Checklist de release

Avant de publier une release :

- [ ] ✅ Code compilé sans erreur
- [ ] ✅ Tests passés
- [ ] ✅ Version incrémentée dans `pom.xml`
- [ ] ✅ `CHANGELOG.md` mis à jour
- [ ] ✅ Documentation à jour
- [ ] ✅ Installeurs créés pour les 3 plateformes
- [ ] ✅ Checksums SHA256 calculés
- [ ] ✅ Release notes rédigées
- [ ] ✅ Tag Git créé et poussé
- [ ] ✅ GitHub Release publiée
- [ ] ✅ Annonce faite (si applicable)

---

## 🆘 Dépannage

### "jpackage command not found"

```bash
# Vérifier que JAVA_HOME pointe vers un JDK 25+
echo $JAVA_HOME
java --version
```

### "Icon file not found"

Assurez-vous d'avoir les bonnes icônes :
- **Windows** : `images/panovisu.ico` (format ICO)
- **macOS** : `images/panovisu.icns` (format ICNS)
- **Linux** : `images/panovisu.png` (format PNG, 512x512px minimum)

### "Cannot create .deb package"

```bash
# Installer fakeroot
sudo apt-get install fakeroot
```

### Installeur trop volumineux

Le runtime Java embarqué représente ~150 MB. Options :
1. **Accepter la taille** (facilité d'installation)
2. **Créer un installeur "léger"** qui télécharge Java séparément
3. **Utiliser jlink** pour créer un runtime custom minimal

---

## 📚 Ressources

- **jpackage** : https://docs.oracle.com/en/java/javase/25/docs/specs/man/jpackage.html
- **Maven jpackage plugin** : https://github.com/petr-panteleyev/jpackage-maven-plugin
- **Inno Setup** : https://jrsoftware.org/isinfo.php
- **GitHub Releases** : https://docs.github.com/en/repositories/releasing-projects-on-github
- **GitHub Actions** : https://docs.github.com/en/actions

---

**Auteur** : GitHub Copilot  
**Date** : 14 octobre 2025  
**Build** : 2598
