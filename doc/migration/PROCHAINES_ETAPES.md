# 🎯 PROCHAINES ÉTAPES - Migration editeurPanovisu

**Date :** 10 octobre 2025  
**Projet :** editeurPanovisu v2.0  
**Localisation :** `d:\developpement\java\editeurPanovisu`

---

## ✅ Ce qui a été fait

### 1. Fichiers de Configuration (3)

| Fichier | Description | Lignes |
|---------|-------------|--------|
| `pom.xml` | Configuration Maven complète | 280 |
| `.gitignore_maven` | Exclusions Git pour Maven | 80 |
| `build.ps1` | Script PowerShell de build | 250 |

**Détails pom.xml :**
- Java 25 configuré
- JavaFX 21.0.5 (controls, fxml, web, swing)
- JFXtras 17-r1
- Javalin 6.3.0 (serveur HTTP)
- Dépendances existantes à jour
- Plugins : compiler, javafx, shade, surefire, jpackage

### 2. Code Source Java (1)

| Fichier | Description | Lignes | État |
|---------|-------------|--------|------|
| `src/editeurpanovisu/util/LocalHTMLServer.java` | Serveur HTTP embarqué | 332 | ✅ Complet |

**Fonctionnalités :**
- Démarrage sur port dynamique
- Sert : aide/, pagesHTML/, diaporama/, templates/
- API : start(), stop(), getUrl(), isRunning()
- Shutdown hook automatique
- CORS activé
- Logging SLF4J

### 3. Pages HTML (1)

| Fichier | Description | Lignes | État |
|---------|-------------|--------|------|
| `pagesHTML/openstreetmap.html` | Carte Leaflet.js interactive | 400+ | ✅ Complet |

**Fonctionnalités :**
- 7 types de cartes OSM
- API JavaScript→Java complète
- Marqueurs, cercles, polylignes
- Events : click, marker, view change
- UI sélecteur de carte

### 4. Documentation (5)

| Document | Pages | État |
|----------|-------|------|
| `MIGRATION_JAVA25_PLAN.md` | 50 | ✅ Plan complet |
| `QUICKSTART_MIGRATION.md` | 20 | ✅ Guide rapide |
| `RESUME_MIGRATION.md` | 25 | ✅ Résumé exécutif |
| `README_V2.md` | 15 | ✅ README modernisé |
| `PROCHAINES_ETAPES.md` | Ce fichier | ✅ |

**Total documentation :** 110+ pages

---

## 📋 TODO - Actions Immédiates (Cette Semaine)

### Priorité 1 : Environnement (1-2 heures) 🔴

#### ✅ **Action 1.1 : Installer Java 25**

**Windows - Eclipse Temurin (recommandé) :**
```powershell
# Télécharger depuis https://adoptium.net/temurin/releases/?version=25
# Ou via Chocolatey:
choco install temurin25

# Vérifier
java -version
# Attendu: openjdk version "25" ou "25.x"
```

**Configuration variables d'environnement :**
```powershell
# Windows : Panneau de configuration > Système > Variables d'environnement

# 1. Variable système JAVA_HOME
JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-25

# 2. Ajouter au PATH
PATH = %JAVA_HOME%\bin;...
```

**Vérification finale :**
```powershell
java -version      # Java 25
javac -version     # javac 25
mvn --version      # Devrait afficher Java 25
```

#### ✅ **Action 1.2 : Créer la Branche Git**

```powershell
cd d:\developpement\java\editeurPanovisu

# Sauvegarder l'état Java 8
git tag v1.3-java8-backup

# Créer branche de migration
git checkout -b migration/java25-openstreetmap

# Ajouter les nouveaux fichiers
git add pom.xml
git add build.ps1
git add .gitignore_maven
git add src/editeurpanovisu/util/LocalHTMLServer.java
git add pagesHTML/openstreetmap.html
git add *.md

# Commit initial
git commit -m "chore: infrastructure migration Java 25

Fichiers ajoutés:
- pom.xml: configuration Maven Java 25 + JavaFX 21
- LocalHTMLServer.java: serveur HTTP embarqué (Javalin)
- openstreetmap.html: carte Leaflet.js interactive
- Documentation: MIGRATION_JAVA25_PLAN.md + QUICKSTART + RESUME
- build.ps1: script PowerShell de build

Stack technique:
- Java 25
- JavaFX 21.0.5
- Javalin 6.3.0
- Leaflet.js 1.9.4
- Maven 3.9+"
```

### Priorité 2 : Premier Build (30 minutes) 🟡

#### ✅ **Action 2.1 : Tester Maven**

```powershell
cd d:\developpement\java\editeurPanovisu

# Vérifier Maven avec Java 25
mvn --version
# Devrait afficher Java 25

# Premier build (va échouer, c'est normal)
mvn clean compile 2>&1 | Tee-Object -FilePath build_errors.log
```

**Erreurs attendues :**
- ✅ Imports JavaFX manquants → Normal, sera résolu progressivement
- ✅ APIs dépréciées (Date, File I/O) → À moderniser
- ✅ Incompatibilités syntaxe → À adapter

**Comptez les erreurs :**
```powershell
# Analyser les erreurs
Get-Content build_errors.log | Select-String "error:" | Measure-Object
```

#### ✅ **Action 2.2 : Analyser les Erreurs**

Créer un fichier de suivi :
```powershell
# Extraire les erreurs dans un fichier
Get-Content build_errors.log | Select-String "error:" > erreurs_compilation.txt

# Grouper par type
Get-Content erreurs_compilation.txt | Group-Object { $_ -replace '.*\[ERROR\] ', '' -replace ':.*', '' }
```

### Priorité 3 : Corrections Critiques (2-3 jours) 🟠

#### 🔄 **Action 3.1 : Intégrer LocalHTMLServer**

**Fichier : `src/editeurpanovisu/EditeurPanovisu.java`**

Ajouter au début de la classe :
```java
import editeurpanovisu.util.LocalHTMLServer;
```

Modifier la méthode `start()` :
```java
@Override
public void start(Stage primaryStage) throws Exception {
    // AJOUTER AU TOUT DÉBUT
    try {
        String baseDir = System.getProperty("user.dir");
        LocalHTMLServer.getInstance().start(baseDir);
        LocalHTMLServer.getInstance().registerShutdownHook();
        
        System.out.println("✅ Serveur HTTP: " + LocalHTMLServer.getInstance().getBaseUrl());
    } catch (Exception e) {
        System.err.println("❌ Erreur serveur HTTP: " + e.getMessage());
        e.printStackTrace();
    }
    
    // ... reste du code existant (ne rien supprimer)
}
```

Modifier la méthode `stop()` :
```java
@Override
public void stop() throws Exception {
    // AJOUTER AU DÉBUT
    try {
        LocalHTMLServer.getInstance().stop();
    } catch (Exception e) {
        System.err.println("Erreur arrêt serveur: " + e.getMessage());
    }
    
    // ... reste du code existant (ne rien supprimer)
    super.stop();
}
```

**Recompiler :**
```powershell
mvn clean compile
# Devrait avoir MOINS d'erreurs
```

#### 🔄 **Action 3.2 : Mettre à Jour AideDialogController**

**Fichier : `src/editeurpanovisu/AideDialogController.java`**

Chercher (ligne ~52) :
```java
weNavigateur.load("file:" + getStrRepertAppli() + File.separator + "aide/aide.html");
```

Remplacer par :
```java
try {
    String aideUrl = LocalHTMLServer.getInstance().getUrl("aide/aide.html");
    weNavigateur.load(aideUrl);
    System.out.println("📖 Aide chargée: " + aideUrl);
} catch (Exception e) {
    System.err.println("❌ Erreur chargement aide: " + e.getMessage());
    // Fallback
    weNavigateur.load("file:" + getStrRepertAppli() + File.separator + "aide/aide.html");
}
```

**Recompiler et tester :**
```powershell
mvn clean compile
# Si succès → tester
mvn javafx:run
```

**Test manuel :**
1. Application se lance ? ✅/❌
2. Console affiche "✅ Serveur HTTP: http://localhost:..." ? ✅/❌
3. Menu Aide → Ouvre la page ? ✅/❌

#### 🔄 **Action 3.3 : Créer NavigateurCarte Moderne**

**OPTION A - Réécriture complète (recommandé)**

Sauvegarder l'original :
```powershell
Copy-Item src\editeurpanovisu\NavigateurCarte.java src\editeurpanovisu\NavigateurCarte.java.java8
```

Remplacer par le code du fichier `QUICKSTART_MIGRATION.md` section "Corrections Prioritaires" → "1. Mise à Jour NavigateurCarte.java".

**OPTION B - Modification incrémentale**

Chercher :
```java
final URL urlGoogleMaps = getClass().getResource("openstreetmap.html");
webEngine.load(urlGoogleMaps.toExternalForm());
```

Remplacer par :
```java
try {
    String mapUrl = LocalHTMLServer.getInstance().getUrl("pagesHTML/openstreetmap.html");
    webEngine.setJavaScriptEnabled(true);
    
    webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
        if (newState == Worker.State.SUCCEEDED) {
            System.out.println("✅ Carte OSM chargée");
            // TODO: Ajouter pont JavaScript
        }
    });
    
    webEngine.load(mapUrl);
} catch (Exception e) {
    System.err.println("❌ Erreur chargement carte: " + e.getMessage());
}
```

---

## 📅 Planning Détaillé (3 Semaines)

### Semaine 1 : Infrastructure (5 jours)

| Jour | Tâches | Durée | État |
|------|--------|-------|------|
| **Lundi** | Installation Java 25 + config Maven | 2h | ⏳ |
| | Création branche Git | 30min | ⏳ |
| | Premier build + analyse erreurs | 1h | ⏳ |
| **Mardi** | Intégration LocalHTMLServer | 3h | ⏳ |
| | Tests serveur HTTP | 1h | ⏳ |
| **Mercredi** | Mise à jour AideDialogController | 2h | ⏳ |
| | Tests aide HTML | 1h | ⏳ |
| **Jeudi** | Réécriture NavigateurCarte | 4h | ⏳ |
| | Tests carte OSM | 2h | ⏳ |
| **Vendredi** | Tests d'intégration | 3h | ⏳ |
| | Documentation avancement | 1h | ⏳ |

### Semaine 2 : Migration Code Java (5 jours)

| Jour | Tâches | Durée | État |
|------|--------|-------|------|
| **Lundi** | APIs dépréciées (Date → LocalDateTime) | 4h | ⏳ |
| | File I/O → java.nio | 2h | ⏳ |
| **Mardi** | Syntaxe Java 25 (var, switch) | 3h | ⏳ |
| | Text blocks pour HTML | 2h | ⏳ |
| **Mercredi** | Records pour données immutables | 3h | ⏳ |
| | Optional pour éviter null | 2h | ⏳ |
| **Jeudi** | Google Maps → OSM (génération XML) | 4h | ⏳ |
| | CartePanoVisu.java | 2h | ⏳ |
| **Vendredi** | Tests compilation complète | 3h | ⏳ |
| | Correction bugs | 3h | ⏳ |

### Semaine 3 : Tests et Finalisation (5 jours)

| Jour | Tâches | Durée | État |
|------|--------|-------|------|
| **Lundi** | Tests unitaires | 4h | ⏳ |
| | Tests d'intégration | 2h | ⏳ |
| **Mardi** | Tests fonctionnels (checklist) | 5h | ⏳ |
| **Mercredi** | Tests compatibilité v1.3 | 3h | ⏳ |
| | Corrections bugs | 3h | ⏳ |
| **Jeudi** | Documentation utilisateur | 3h | ⏳ |
| | Notes de version | 2h | ⏳ |
| **Vendredi** | Build final | 2h | ⏳ |
| | Publication GitHub | 2h | ⏳ |
| | Célébration ! 🎉 | - | ⏳ |

---

## 🎯 Objectifs Mesurables

### Build
- [ ] `mvn clean compile` → 0 erreurs
- [ ] `mvn test` → Tous tests passent
- [ ] `mvn package` → JAR créé (<50 MB)
- [ ] `mvn javafx:run` → Application démarre

### Fonctionnel
- [ ] Serveur HTTP démarre automatiquement
- [ ] Aide s'ouvre via http://localhost
- [ ] Carte OpenStreetMap s'affiche
- [ ] Marqueurs ajoutables sur carte
- [ ] Export visite génère XML correct
- [ ] Visite affichable dans navigateur

### Performance
- [ ] Temps démarrage <10 secondes
- [ ] Chargement carte <3 secondes
- [ ] Pas de ralentissement interface
- [ ] Mémoire <500 MB au repos

### Qualité Code
- [ ] Warnings <20
- [ ] Pas de deprecated APIs
- [ ] JavaDoc à jour
- [ ] Code formaté (Google Java Style)

---

## 📊 Métriques de Suivi

### Créer un Tableau de Bord

**Fichier : `PROGRESS.md`**
```markdown
# Progression Migration Java 25

## Erreurs de Compilation
- **Jour 1 :** 150 erreurs
- **Jour 2 :** 120 erreurs
- **Jour 3 :** 80 erreurs
- ...
- **Objectif :** 0 erreurs

## Fichiers Migrés
- [x] EditeurPanovisu.java (partiel)
- [x] AideDialogController.java
- [ ] NavigateurCarte.java
- [ ] CartePanoVisu.java
- ...

## Tests
- Tests unitaires : 0/10 ✅
- Tests fonctionnels : 0/15 ✅
- Tests intégration : 0/5 ✅
```

### Commandes de Suivi

```powershell
# Compter erreurs compilation
mvn clean compile 2>&1 | Select-String "error:" | Measure-Object | Select-Object Count

# Compter warnings
mvn clean compile 2>&1 | Select-String "warning:" | Measure-Object | Select-Object Count

# Taille JAR
(Get-Item target\*.jar | Where-Object { $_.Name -notmatch "original" }).Length / 1MB

# Temps de build
Measure-Command { mvn clean package }
```

---

## 🆘 Ressources et Support

### Documentation Interne
1. `MIGRATION_JAVA25_PLAN.md` - Plan complet (1100 lignes)
2. `QUICKSTART_MIGRATION.md` - Guide rapide (500 lignes)
3. `RESUME_MIGRATION.md` - Résumé exécutif (600 lignes)
4. `README_V2.md` - README modernisé (400 lignes)

### Documentation Externe
- [JavaFX 21 Docs](https://openjfx.io/javadoc/21/)
- [Java 25 API](https://docs.oracle.com/en/java/javase/25/docs/api/)
- [Leaflet.js](https://leafletjs.com/reference.html)
- [Javalin](https://javalin.io/documentation)
- [Maven Guide](https://maven.apache.org/guides/index.html)

### Communauté
- [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
- [StackOverflow JavaFX](https://stackoverflow.com/questions/tagged/javafx)
- [OpenJFX Forum](https://openjfx.io/community/)

### En Cas de Blocage

**Option 1 : GitHub Issue**
```
Titre: [Migration v2.0] Description du problème

Corps:
- Erreur complète
- Fichier et ligne
- Java version
- Maven version
- Tentatives de résolution
```

**Option 2 : Rollback Temporaire**
```powershell
# Revenir à Java 8 temporairement
git checkout v1.3-java8-backup

# Ou revenir à main
git checkout main
```

---

## ✅ Checklist Finale de Validation

Avant de merger `migration/java25-openstreetmap` → `main` :

### Build ✅
- [ ] `mvn clean compile` → Succès
- [ ] `mvn test` → Tous passent
- [ ] `mvn package` → JAR créé
- [ ] Pas de warnings critiques

### Application ✅
- [ ] Lance sans erreur
- [ ] Interface normale
- [ ] Pas de ralentissement
- [ ] Mémoire acceptable

### Fonctionnalités ✅
- [ ] Aide HTML fonctionne
- [ ] Carte OSM fonctionne
- [ ] Export visite fonctionne
- [ ] Projets v1.3 s'ouvrent

### Documentation ✅
- [ ] README.md à jour
- [ ] CHANGELOG.md créé
- [ ] Guide migration complet
- [ ] Notes de version

### Git ✅
- [ ] Commits propres
- [ ] Branche à jour
- [ ] Tag v2.0.0 créé
- [ ] Push origin

---

## 🎉 Une Fois Terminé

### Publication

```powershell
# Merger
git checkout main
git merge migration/java25-openstreetmap

# Tag
git tag -a v2.0.0 -m "Release v2.0.0: Java 25 + JavaFX 21 + OpenStreetMap"

# Push
git push origin main
git push origin v2.0.0
```

### GitHub Release

1. Aller sur https://github.com/llang57/editeurPanovisu/releases
2. "Draft a new release"
3. Tag: `v2.0.0`
4. Title: `editeurPanovisu v2.0 - Java 25 + OpenStreetMap`
5. Description: Copier depuis CHANGELOG.md
6. Attacher: `editeurPanovisu-2.0.0.jar`
7. Publier

### Célébration 🎊

```
    _____ ____  _   _ _____  _____ _____ 
   /  __ \|  _ \| | | |_   _|/  ___/  ___|
   | /  \/| | | | | | | | |  \ `--.\ `--. 
   | |    | | | | | | | | |   `--. \`--. \
   | \__/\| |_| | |_| |_| |_ /\__/ /\__/ /
    \____/|____/ \___/ \___/ \____/\____/ 
    
    🎉 Migration Java 25 Terminée ! 🎉
```

---

**Bon courage pour la migration ! 🚀**

**Prochaine action immédiate :** Installer Java 25 (voir Action 1.1)
