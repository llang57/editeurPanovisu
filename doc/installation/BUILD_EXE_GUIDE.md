# 🚀 Guide de Création de l'Exécutable Windows

Ce guide explique comment créer un fichier `.exe` autonome pour **EditeurPanovisu** qui inclut Java et toutes les dépendances.

## 📋 Prérequis

### 1. **Java Development Kit (JDK) 25**
- ✅ Déjà installé (vous utilisez Java 25)
- Vérifier: `java -version`

### 2. **Apache Maven**
- ✅ Déjà installé
- Vérifier: `mvn -version`

### 3. **WiX Toolset** (Requis pour les .exe Windows)
- ⚠️ **À installer si ce n'est pas déjà fait**
- Télécharger: https://wixtoolset.org/releases/
- Version recommandée: WiX Toolset 3.14 ou supérieure
- Après installation, **redémarrer PowerShell** pour que le PATH soit mis à jour

## 🎯 Méthode Rapide (Recommandée)

### Option 1: Utiliser le script automatisé

```powershell
# Depuis le dossier du projet
.\build-exe.ps1
```

Le script va:
1. ✅ Compiler le projet
2. ✅ Créer le JAR avec dépendances
3. ✅ Générer l'exécutable Windows
4. ✅ Afficher l'emplacement du fichier

### Option 2: Build complet avec nettoyage

```powershell
.\build-exe.ps1 -Clean
```

## 🔧 Méthode Manuelle (Étape par étape)

### Étape 1: Compiler et créer le JAR

```powershell
mvn clean package -DskipTests
```

**Résultat**: `target/editeurPanovisu-2.0.0-SNAPSHOT.jar`

### Étape 2: Créer l'exécutable Windows

```powershell
mvn jpackage:jpackage
```

**Résultat**: `target/dist/EditeurPanovisu-2.0.0.exe`

## 📦 Résultat Final

Après le build, vous trouverez:

```
target/
  └── dist/
      └── EditeurPanovisu-2.0.0.exe  (~ 200 MB)
```

### Caractéristiques de l'exécutable:

- ✅ **Totalement autonome** : inclut Java 25 et toutes les dépendances
- ✅ **Double-clic pour lancer** : aucune installation Java requise sur la machine cible
- ✅ **Icône personnalisée** : logo PanoVisu
- ✅ **Raccourci Menu Démarrer** : s'ajoute automatiquement
- ✅ **Association de fichiers** : peut être configurée pour .pvu

## 🎨 Personnalisation

### Changer l'icône

Remplacez le fichier: `images/panovisu.ico`

### Modifier les paramètres

Éditez `pom.xml`, section `jpackage-maven-plugin`:

```xml
<configuration>
    <name>EditeurPanovisu</name>         <!-- Nom de l'exe -->
    <appVersion>2.0.0</appVersion>       <!-- Version -->
    <vendor>PanoVisu</vendor>            <!-- Éditeur -->
    <icon>images/panovisu.ico</icon>     <!-- Icône -->
    ...
</configuration>
```

## ⚠️ Dépannage

### Erreur: "jpackage not found"

**Solution**: Assurez-vous d'utiliser un JDK 14+ (pas juste un JRE)

```powershell
# Vérifier la version Java
java -version

# Doit afficher "openjdk version 25" ou similaire
```

### Erreur: "Cannot find WiX tools"

**Solution**: Installez WiX Toolset

1. Téléchargez: https://wixtoolset.org/releases/
2. Installez `wix314.exe` (ou version supérieure)
3. **Redémarrez PowerShell**
4. Relancez le build

### Erreur: "Module not found"

**Solution**: Le projet doit être compilé d'abord

```powershell
mvn clean compile
mvn package
```

### L'exécutable est très gros (200+ MB)

**Normal**: L'exécutable contient:
- JRE Java complet (~80 MB)
- JavaFX (~60 MB)
- Votre application et dépendances (~40 MB)
- Ressources (images, CSS, JS) (~20 MB)

**Pour réduire la taille**, utilisez **jlink** pour créer un JRE minimal (option 3 avancée).

## 🚀 Distribution

Une fois l'exécutable créé:

1. **Testez-le** sur votre machine
2. **Testez-le** sur une machine **sans Java installé**
3. Distribuez `EditeurPanovisu-2.0.0.exe`

### Méthodes de distribution:

- 📧 **Email** (si < 25 MB - compresser avec 7zip)
- ☁️ **Cloud** (Google Drive, Dropbox, OneDrive)
- 🌐 **Site web** (téléchargement direct)
- 💿 **Clé USB**

## 🔄 Créer un Installeur MSI (Optionnel)

Pour créer un vrai installeur Windows avec désinstallation:

1. Modifiez `pom.xml`:
   ```xml
   <type>MSI</type>  <!-- Au lieu de EXE -->
   ```

2. Relancez le build:
   ```powershell
   mvn jpackage:jpackage
   ```

3. Résultat: `target/dist/EditeurPanovisu-2.0.0.msi`

## 📝 Notes

- Le build prend **2-5 minutes** selon votre machine
- L'exécutable fonctionne sur **Windows 10/11** (64-bit)
- Compatible avec les machines **sans Java installé**
- Les ressources (images, CSS, JS) sont embarquées

## 🎯 Commandes Utiles

```powershell
# Build rapide
.\build-exe.ps1

# Build complet avec nettoyage
.\build-exe.ps1 -Clean

# Build manuel étape par étape
mvn clean compile
mvn package -DskipTests
mvn jpackage:jpackage

# Vérifier le contenu du JAR
jar -tf target/editeurPanovisu-2.0.0-SNAPSHOT.jar | more

# Lancer l'exécutable
.\target\dist\EditeurPanovisu-2.0.0.exe
```

## 📚 Ressources

- Documentation jpackage: https://docs.oracle.com/en/java/javase/21/jpackage/
- WiX Toolset: https://wixtoolset.org/
- Maven jpackage plugin: https://github.com/petr-panteleyev/jpackage-maven-plugin

---

**Bon build ! 🎉**
