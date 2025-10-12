# 🔢 Système de Versioning Automatique

## Vue d'ensemble

PanoVisu utilise un système de versioning automatique qui incrémente le numéro de build à chaque compilation Maven.

### Format de version
```
PanoVisu 3.0-b1992
```

- **Version majeure** : `3.0`
- **Build number** : `1992` (auto-incrémenté)

---

## 📋 Fichiers impliqués

### 1. `build.num`
Fichier principal contenant le numéro de build actuel.

```properties
#Build Number - Auto-incremented by Maven
#sam. oct. 11 10:21:26 CEST 2025
build.number=1992
```

### 2. `src/project.properties`
Configuration du projet avec version et copyright.

```properties
# PanoVisu Project Properties
project.version=3.0
Application.buildnumber=1992
copyright.years=2014-2025
copyright.author=Laurent LANG
```

### 3. `src/editeurpanovisu/i18n/PanoVisu.properties`
Fichier de localisation contenant le build formaté pour l'affichage.

```properties
build.numero=1\u00a0992
```

Le `\u00a0` est un espace insécable Unicode pour un affichage correct : `1 992`

---

## ⚙️ Fonctionnement

### Incrémentation automatique

Le numéro de build est automatiquement incrémenté à **chaque compilation Maven** :

```bash
mvn clean compile    # Build number: 1992 → 1993
mvn compile          # Build number: 1993 → 1994
mvn package          # Build number: 1994 → 1995
mvn javafx:run       # Build number: 1995 → 1996
```

### Script d'incrémentation

Le script `increment-build.ps1` est exécuté automatiquement par Maven pendant la phase `initialize` (avant la compilation).

**Configuration dans `pom.xml`** :
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.1</version>
    <executions>
        <execution>
            <id>increment-build-number</id>
            <phase>initialize</phase>
            <goals>
                <goal>exec</goal>
            </goals>
            <configuration>
                <executable>powershell</executable>
                <arguments>
                    <argument>-ExecutionPolicy</argument>
                    <argument>Bypass</argument>
                    <argument>-File</argument>
                    <argument>${project.basedir}/increment-build.ps1</argument>
                </arguments>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 📦 Affichage dans l'application

### Titre de la fenêtre
```
PanoVisu v3.0
```

### Label d'information (coin supérieur gauche)
```
panoVisu Vers. : 3.0-b1992
Laurent LANG (2014-2025)
```

### Code source (`EditeurPanovisu.java`)

```java
// Ligne 10300 - Copyright mis à jour
Label lblPanoVisu2 = new Label("Laurent LANG (2014-2025)");

// Ligne 11130 - Construction de la version
strNumVersion = pack.getImplementationVersion() + "-b" + 
                rbLocalisation.getString("build.numero")
                    .replace(" ", "")
                    .replace(" ", "");

// Ligne 11136 - Titre de la fenêtre
getStPrincipal().setTitle("PanoVisu v" + strNumVersion.split("-")[0]);
```

---

## 🔧 Maintenance

### Modifier la version majeure

Pour passer à la version 4.0, modifier les fichiers suivants :

1. **`pom.xml`** :
   ```xml
   <version>4.0.0-SNAPSHOT</version>
   
   <manifestEntries>
       <Implementation-Version>4.0</Implementation-Version>
   </manifestEntries>
   ```

2. **`src/project.properties`** :
   ```properties
   project.version=4.0
   ```

### Réinitialiser le build number

Pour remettre à zéro (par exemple pour une nouvelle version majeure) :

1. Modifier `build.num` :
   ```properties
   build.number=1
   ```

2. Recompiler :
   ```bash
   mvn clean compile
   ```

---

## 🚨 Important

### Ne pas modifier manuellement

⚠️ **Ne modifiez JAMAIS manuellement** les fichiers suivants pendant le développement :

- `build.num` - Build number (auto-incrémenté)
- `src/editeurpanovisu/i18n/PanoVisu.properties` - Ligne `build.numero`
- `src/project.properties` - Ligne `Application.buildnumber`

Ces fichiers sont **mis à jour automatiquement** par le script `increment-build.ps1`.

### Git et versioning

Les fichiers de build **sont versionnés** dans Git pour garder une trace de l'évolution :

```gitignore
# Ces fichiers sont VERSIONNÉS (pas dans .gitignore)
build.num
src/project.properties
src/editeurpanovisu/i18n/PanoVisu.properties
```

Chaque commit inclura donc le nouveau numéro de build.

---

## 📊 Historique

| Version | Build Initial | Date | Changements |
|---------|--------------|------|-------------|
| **3.0** | 1990 | 11 oct 2025 | Migration Java 25, nouveau système de versioning |
| 2.0 | 2 | 2015 | Version NetBeans |

---

## 🛠️ Dépannage

### Le build ne s'incrémente pas

1. Vérifier que PowerShell est installé et accessible depuis Maven
2. Exécuter manuellement :
   ```bash
   powershell -ExecutionPolicy Bypass -File increment-build.ps1
   ```
3. Vérifier la sortie Maven pour les erreurs

### Build number incorrect dans l'application

1. Nettoyer et recompiler :
   ```bash
   mvn clean compile
   ```
2. Vérifier que `i18n/PanoVisu.properties` est bien copié dans `target/classes/`

### Version "null" affichée

Cela signifie que le MANIFEST.MF n'est pas correctement configuré.

Vérifier dans `pom.xml` :
```xml
<manifestEntries>
    <Implementation-Version>3.0</Implementation-Version>
</manifestEntries>
```

---

**Dernière mise à jour** : 11 octobre 2025  
**Version actuelle** : 3.0-b1992  
**Auteur** : Laurent LANG (2014-2025)
