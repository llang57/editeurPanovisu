# 🚀 Guide de Démarrage Rapide - Migration Java 25

## Prérequis Installation

### 1. Installer Java 25

**Windows:**
```powershell
# Télécharger depuis Adoptium (Eclipse Temurin)
# https://adoptium.net/temurin/releases/?version=25

# Vérifier l'installation
java -version
# Devrait afficher: openjdk version "25"
```

**macOS:**
```bash
brew install openjdk@25
java -version
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-25-jdk
java -version
```

### 2. Installer Maven (recommandé)

**Windows:**
```powershell
# Via Chocolatey
choco install maven

# Ou télécharger depuis https://maven.apache.org/download.cgi
# Ajouter au PATH: C:\Program Files\apache-maven-3.9.x\bin
```

**macOS:**
```bash
brew install maven
```

**Linux:**
```bash
sudo apt install maven
```

**Vérifier:**
```bash
mvn -version
# Apache Maven 3.9.x
```

## 🛠️ Compilation Initiale

### Étape 1 : Préparer le Projet

```powershell
# Naviguer vers le projet
cd d:\developpement\java\editeurPanovisu

# Créer une branche de migration
git checkout -b migration/java25-openstreetmap

# Sauvegarder l'état Java 8
git tag v1.3-java8-backup
```

### Étape 2 : Premier Build Maven

```powershell
# Nettoyer et compiler
mvn clean compile

# Si erreurs de compilation, ne pas paniquer!
# Les erreurs attendues à ce stade:
# - Imports manquants (JavaFX)
# - APIs dépréciées
# - Incompatibilités syntaxe Java 8 vs 25
```

### Étape 3 : Corriger les Erreurs de Compilation

**Problème 1 : JavaFX imports**
```java
// Si erreur: "package javafx.scene.control does not exist"
// Solution: vérifier que pom.xml contient les dépendances JavaFX

// Les imports devraient fonctionner automatiquement avec Maven
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.*;
```

**Problème 2 : APIs dépréciées**
```java
// Erreur: "Date() constructor is deprecated"
// Avant:
Date date = new Date();

// Après:
LocalDateTime date = LocalDateTime.now();
```

### Étape 4 : Build Complet

```powershell
# Compiler + packager
mvn clean package

# Si succès, vous obtenez:
# target/editeurPanovisu-2.0.0-SNAPSHOT.jar
```

## 🧪 Tests Initiaux

### Test 1 : Lancer l'Application

```powershell
# Via Maven
mvn javafx:run

# Ou directement
java -jar target/editeurPanovisu-2.0.0-SNAPSHOT.jar
```

**Attendu:**
- ✅ Fenêtre JavaFX s'ouvre
- ✅ Interface s'affiche correctement
- ⚠️ Cartes Google Maps peuvent ne pas fonctionner (normal)
- ⚠️ Pages HTML locales peuvent être bloquées (normal)

### Test 2 : Serveur HTTP Embarqué

Ajouter ce code dans `EditeurPanovisu.java` :

```java
import editeurpanovisu.util.LocalHTMLServer;

@Override
public void start(Stage primaryStage) throws Exception {
    // AVANT le reste du code d'initialisation
    
    // Démarrer le serveur HTTP
    String baseDir = System.getProperty("user.dir");
    LocalHTMLServer.getInstance().start(baseDir);
    LocalHTMLServer.getInstance().registerShutdownHook();
    
    System.out.println("✅ Serveur HTTP: " + LocalHTMLServer.getInstance().getBaseUrl());
    
    // ... reste du code existant
}
```

Recompiler et relancer:
```powershell
mvn clean package
mvn javafx:run
```

**Vérifier dans la console:**
```
✅ Serveur HTTP démarré avec succès sur http://localhost:xxxxx
   - Aide: http://localhost:xxxxx/aide/aide.html
   - PagesHTML: http://localhost:xxxxx/pagesHTML/
   - Diaporama: http://localhost:xxxxx/diaporama/
```

### Test 3 : Carte OpenStreetMap

Ouvrir le fichier créé dans un navigateur:
```
file:///d:/developpement/java/editeurPanovisu/pagesHTML/openstreetmap.html
```

**Attendu:**
- ✅ Carte OSM s'affiche
- ✅ Contrôles de type de carte fonctionnent
- ✅ Zoom/pan réactifs

## 🔧 Corrections Prioritaires

### 1. Mise à Jour `NavigateurCarte.java`

**Fichier:** `src/editeurpanovisu/NavigateurCarte.java`

```java
// AVANT (Java 8)
package editeurpanovisu;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;

public class NavigateurCarte {
    private WebView webView;
    private WebEngine webEngine;
    
    public NavigateurCarte(WebView webView) {
        this.webView = webView;
        this.webEngine = webView.getEngine();
        
        // Ancien: charge depuis les ressources
        final URL urlGoogleMaps = getClass().getResource("openstreetmap.html");
        webEngine.load(urlGoogleMaps.toExternalForm());
    }
}

// APRÈS (Java 25)
package editeurpanovisu;

import editeurpanovisu.util.LocalHTMLServer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;
import netscape.javascript.JSObject;

public class NavigateurCarte {
    private WebView webView;
    private WebEngine webEngine;
    
    public NavigateurCarte(WebView webView) {
        this.webView = webView;
        this.webEngine = webView.getEngine();
        
        // Nouveau: charge via serveur HTTP
        String mapUrl = LocalHTMLServer.getInstance().getUrl("pagesHTML/openstreetmap.html");
        
        // Activer JavaScript
        webEngine.setJavaScriptEnabled(true);
        
        // Pont Java↔JavaScript
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", new JavaScriptBridge());
                System.out.println("✅ Carte OSM chargée et connectée");
            }
        });
        
        webEngine.load(mapUrl);
    }
    
    // Classe pour communication JavaScript→Java
    public class JavaScriptBridge {
        public void mapReady() {
            System.out.println("📍 Carte prête");
        }
        
        public void onMapClick(double lat, double lng) {
            System.out.println("🖱️ Clic carte: " + lat + ", " + lng);
            // Appeler les handlers Java
        }
        
        public void onMarkerClick(double lat, double lng, int markerId) {
            System.out.println("📍 Clic marqueur " + markerId + ": " + lat + ", " + lng);
        }
        
        public void onViewChanged(double lat, double lng, int zoom) {
            System.out.println("🗺️ Vue changée: " + lat + ", " + lng + " (zoom " + zoom + ")");
        }
    }
    
    // API Java pour contrôler la carte
    public void addMarker(double lat, double lng, String title, String popup) {
        String script = String.format(
            "addMarker(%f, %f, '%s', '%s')", 
            lat, lng, 
            escapeJavaScript(title), 
            escapeJavaScript(popup)
        );
        webEngine.executeScript(script);
    }
    
    public void setCenter(double lat, double lng, int zoom) {
        webEngine.executeScript(String.format("setCenter(%f, %f, %d)", lat, lng, zoom));
    }
    
    public void clearMarkers() {
        webEngine.executeScript("clearMarkers()");
    }
    
    public void setMapType(String type) {
        webEngine.executeScript("setMapType('" + type + "')");
    }
    
    private String escapeJavaScript(String text) {
        if (text == null) return "";
        return text.replace("'", "\\'")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
}
```

### 2. Mise à Jour `AideDialogController.java`

**Fichier:** `src/editeurpanovisu/AideDialogController.java`

```java
// AVANT
weNavigateur.load("file:" + getStrRepertAppli() + File.separator + "aide/aide.html");

// APRÈS
String aideUrl = LocalHTMLServer.getInstance().getUrl("aide/aide.html");
weNavigateur.load(aideUrl);
System.out.println("📖 Aide chargée: " + aideUrl);
```

### 3. Mise à Jour Génération Visites

**Fichier:** `src/editeurpanovisu/EditeurPanovisu.java`

Chercher les occurrences de génération HTML et remplacer les références Google Maps:

```java
// LIGNE ~2317 : Remplacer
// AVANT
+ "        <script src=\"http://maps.google.com/maps/api/js?v=3.5&sensor=false\"></script>\n"

// APRÈS
+ "        <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />\n"
+ "        <script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>\n"
```

## 📝 Checklist de Validation

Après chaque modification, tester:

```powershell
# Recompiler
mvn clean compile

# Si succès → tester
mvn javafx:run

# Vérifier dans l'application:
□ Interface s'affiche correctement
□ Aucune erreur dans la console
□ Menu Aide s'ouvre (http://localhost:...)
□ Carte s'affiche si disponible
```

## 🐛 Dépannage Courant

### Erreur : "module java.base does not export sun.misc"
```powershell
# Solution: ajouter au pom.xml
<compilerArgs>
    <arg>--add-exports</arg>
    <arg>java.base/sun.misc=ALL-UNNAMED</arg>
</compilerArgs>
```

### Erreur : "JavaFX runtime components are missing"
```powershell
# Vérifier que le plugin javafx-maven-plugin est bien configuré
mvn javafx:run  # Au lieu de java -jar
```

### Erreur : "Port already in use"
```java
// Le serveur HTTP est déjà démarré
// Solution: redémarrer l'application ou changer le port

// Dans LocalHTMLServer.java, ligne ~95:
app.start(8080);  // Port fixe au lieu de 0 (auto)
```

### Erreur : "CORS policy" dans la console du navigateur
```java
// Déjà géré dans LocalHTMLServer.java
// Vérifier que la config CORS est présente (ligne ~68)
config.bundledPlugins.enableCors(cors -> {
    cors.addRule(it -> it.anyHost());
});
```

## 📚 Ressources Utiles

### Documentation
- [JavaFX 21 Docs](https://openjfx.io/javadoc/21/)
- [Leaflet.js API](https://leafletjs.com/reference.html)
- [Javalin Docs](https://javalin.io/documentation)
- [Maven Guide](https://maven.apache.org/guides/)

### Outils de Développement
- **IntelliJ IDEA 2024** (recommandé pour Java 25)
- **Scene Builder** (pour éditer les fichiers FXML)
- **GitKraken** (pour gérer Git visuellement)

### Commandes Maven Utiles
```powershell
mvn clean                    # Nettoyer
mvn compile                  # Compiler
mvn test                     # Tests unitaires
mvn package                  # Créer le JAR
mvn javafx:run              # Lancer l'app
mvn dependency:tree         # Voir les dépendances
mvn versions:display-plugin-updates  # Vérifier les mises à jour
```

## ⏭️ Prochaines Étapes

1. ✅ **Compilation réussie** → Passer aux corrections de code
2. ✅ **Serveur HTTP fonctionne** → Mettre à jour les chargements HTML
3. ✅ **Carte OSM s'affiche** → Adapter NavigateurCarte
4. ⏳ **Tests complets** → Suivre le plan de migration complet

Référez-vous à `MIGRATION_JAVA25_PLAN.md` pour le plan détaillé.

---

**🆘 Support**
- [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
- [Documentation complète](./MIGRATION_JAVA25_PLAN.md)
