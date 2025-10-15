# 📥 Installation de PanoVisu

Ce guide vous accompagne dans l'installation de l'éditeur PanoVisu.

## 🚀 Installation rapide (Recommandé)

**Des installateurs prêts à l'emploi sont disponibles !** Ils incluent Java 25 et toutes les dépendances.

### Téléchargement

➡️ **[Télécharger depuis GitHub Releases](https://github.com/llang57/editeurPanovisu/releases)**

Choisissez l'installateur correspondant à votre système :
- **Windows** : `EditeurPanovisu-[version].exe`
- **macOS** : `EditeurPanovisu-[version].dmg`
- **Linux** : `EditeurPanovisu-[version].deb` (Debian/Ubuntu) ou `.rpm` (Fedora/RedHat)

### Installation

#### Windows
1. Téléchargez le fichier `.exe`
2. Double-cliquez pour lancer l'installation
3. Suivez l'assistant d'installation
4. Lancez PanoVisu depuis le menu Démarrer

#### macOS
1. Téléchargez le fichier `.dmg`
2. Ouvrez le fichier téléchargé
3. Glissez l'icône PanoVisu dans Applications
4. Lancez depuis le Launchpad ou Applications

#### Linux (Debian/Ubuntu)
```bash
# Téléchargez le fichier .deb puis :
sudo dpkg -i EditeurPanovisu-[version].deb
sudo apt-get install -f  # Résout les dépendances
```

#### Linux (Fedora/RedHat)
```bash
# Téléchargez le fichier .rpm puis :
sudo rpm -i EditeurPanovisu-[version].rpm
```

---

## 🛠️ Installation depuis les sources (Développeurs)

Si vous souhaitez compiler PanoVisu depuis les sources (pour contribuer ou personnaliser).

## Table des matières

- [Prérequis système](#prérequis-système)
- [Installation de Java](#installation-de-java)
- [Installation de Maven](#installation-de-maven)
- [Téléchargement de PanoVisu](#téléchargement-de-panovisu)
- [Compilation du projet](#compilation-du-projet)
- [Lancement de l'éditeur](#lancement-de-léditeur)
- [Résolution des problèmes](#résolution-des-problèmes)

## Prérequis système

### Configuration minimale
- **Processeur** : Dual-core 2 GHz ou supérieur
- **Mémoire RAM** : 4 Go minimum (8 Go recommandé)
- **Espace disque** : 500 Mo pour l'application + espace pour vos projets
- **Carte graphique** : Support OpenGL 2.0 ou supérieur
- **Système d'exploitation** : 
  - Windows 10/11 (64-bit)
  - macOS 10.15 (Catalina) ou supérieur
  - Linux (distribution récente avec support Java)

### Configuration recommandée
- **Processeur** : Quad-core 3 GHz ou supérieur
- **Mémoire RAM** : 16 Go
- **Carte graphique** : GPU dédié avec support OpenGL 3.0+
- **Écran** : Résolution Full HD (1920×1080) minimum

## Installation de Java

PanoVisu nécessite **Java 25** (OpenJDK Temurin recommandé).

### Windows

1. **Téléchargez Java 25**
   - Rendez-vous sur [Adoptium.net](https://adoptium.net/)
   - Sélectionnez **Java 25** (LTS)
   - Choisissez **Windows x64**
   - Téléchargez l'**installateur MSI**

2. **Installez Java**
   - Exécutez le fichier `.msi` téléchargé
   - Suivez l'assistant d'installation
   - ✅ Cochez "Set JAVA_HOME variable"
   - ✅ Cochez "Add to PATH"

3. **Vérifiez l'installation**
   ```powershell
   java -version
   ```
   Résultat attendu :
   ```
   openjdk version "25" 2025-09-16
   OpenJDK Runtime Environment Temurin-25+36
   ```

### macOS

1. **Avec Homebrew** (recommandé)
   ```bash
   brew install openjdk@25
   ```

2. **Configuration du PATH**
   Ajoutez à votre `~/.zshrc` ou `~/.bash_profile` :
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 25)
   export PATH="$JAVA_HOME/bin:$PATH"
   ```

3. **Rechargez la configuration**
   ```bash
   source ~/.zshrc  # ou source ~/.bash_profile
   ```

4. **Vérifiez l'installation**
   ```bash
   java -version
   ```

### Linux (Ubuntu/Debian)

1. **Installation**
   ```bash
   sudo apt update
   sudo apt install openjdk-25-jdk
   ```

2. **Configuration de JAVA_HOME**
   Ajoutez à votre `~/.bashrc` :
   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64
   export PATH="$JAVA_HOME/bin:$PATH"
   ```

3. **Rechargez la configuration**
   ```bash
   source ~/.bashrc
   ```

4. **Vérifiez l'installation**
   ```bash
   java -version
   ```

## Installation de Maven

Maven est nécessaire pour compiler PanoVisu depuis les sources.

### Windows

1. **Téléchargez Maven**
   - Rendez-vous sur [maven.apache.org](https://maven.apache.org/download.cgi)
   - Téléchargez le **Binary zip archive**

2. **Extrayez l'archive**
   - Extrayez dans `C:\Program Files\Apache\Maven`

3. **Configurez les variables d'environnement**
   - Ouvrez **Paramètres système avancés** → **Variables d'environnement**
   - Créez `MAVEN_HOME` : `C:\Program Files\Apache\Maven\apache-maven-3.9.x`
   - Ajoutez à `Path` : `%MAVEN_HOME%\bin`

4. **Vérifiez l'installation**
   ```powershell
   mvn -version
   ```

### macOS

```bash
brew install maven
mvn -version
```

### Linux (Ubuntu/Debian)

```bash
sudo apt install maven
mvn -version
```

## Téléchargement de PanoVisu

### Option 1 : Clone Git (recommandé)

```bash
git clone https://github.com/llang57/editeurPanovisu.git
cd editeurPanovisu
```

### Option 2 : Téléchargement ZIP

1. Allez sur [github.com/llang57/editeurPanovisu](https://github.com/llang57/editeurPanovisu)
2. Cliquez sur **Code** → **Download ZIP**
3. Extrayez l'archive
4. Ouvrez un terminal dans le dossier extrait

## Compilation du projet

### Première compilation

```bash
# Compilation complète
mvn clean package
```

Cette commande va :
- ✅ Télécharger toutes les dépendances
- ✅ Compiler le code source
- ✅ Générer le fichier JAR
- ✅ Incrémenter automatiquement le numéro de build

**Durée** : 2-5 minutes lors de la première compilation (téléchargement des dépendances).

### Compilations suivantes

```bash
# Compilation rapide
mvn clean compile
```

**Durée** : 30 secondes à 1 minute.

## Lancement de l'éditeur

### Avec Maven (recommandé pour le développement)

```bash
mvn javafx:run
```

### Avec le JAR généré

```bash
java -jar target/editeurPanovisu-3.1.0-SNAPSHOT.jar
```

### Script de lancement

#### Windows
Double-cliquez sur `Lancer_EditeurPanovisu.bat`

#### Linux/macOS
```bash
chmod +x launch.sh
./launch.sh
```

## Résolution des problèmes

### ❌ "Java not found" ou "JAVA_HOME not set"

**Solution** :
1. Vérifiez que Java est installé : `java -version`
2. Configurez JAVA_HOME correctement (voir sections ci-dessus)
3. Redémarrez votre terminal ou IDE

### ❌ "mvn: command not found"

**Solution** :
1. Vérifiez que Maven est installé : `mvn -version`
2. Ajoutez Maven au PATH (voir sections ci-dessus)
3. Redémarrez votre terminal

### ❌ Erreur de compilation Maven

**Solution** :
```bash
# Nettoyez le cache Maven
mvn clean

# Forcez la mise à jour des dépendances
mvn clean package -U
```

### ❌ "JavaFX components are missing"

**Solution** :
Les dépendances JavaFX sont gérées automatiquement par Maven. Si l'erreur persiste :

```bash
# Supprimez le cache Maven local
rm -rf ~/.m2/repository/org/openjfx

# Recompilez
mvn clean package
```

### ❌ L'application ne démarre pas sous macOS

**Solution** :
macOS peut bloquer les applications non signées.

1. Ouvrez **Préférences Système** → **Sécurité et confidentialité**
2. Cliquez sur **Ouvrir quand même** pour autoriser PanoVisu

### ❌ Problèmes de performances / Interface lente

**Solutions** :
1. Allouez plus de mémoire à Java :
   ```bash
   java -Xmx4G -jar target/editeurPanovisu-3.1.0-SNAPSHOT.jar
   ```

2. Activez l'accélération matérielle :
   ```bash
   java -Dprism.order=es2 -jar target/editeurPanovisu-3.1.0-SNAPSHOT.jar
   ```

### ❌ Erreur "Port already in use" lors de l'ouverture d'une visite

Le serveur HTTP intégré (LocalHTTPServer) ne trouve pas de port disponible.

**Solution** :
- Le serveur teste automatiquement les ports 8080 à 8090
- Libérez l'un de ces ports ou l'application basculera automatiquement sur `file://`

## Prochaines étapes

✅ Installation terminée ? Consultez le **[Démarrage rapide](Démarrage-rapide)** pour créer votre première visite virtuelle !

## Besoin d'aide ?

- 💬 [Discussions GitHub](https://github.com/llang57/editeurPanovisu/discussions)
- 🐛 [Signaler un problème](https://github.com/llang57/editeurPanovisu/issues)
- 📖 [FAQ](FAQ)
