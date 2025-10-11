# Plan de Migration : editeurPanovisu
## De Java 8 + JavaFX 8 vers Java 25 + OpenStreetMap

**Date de création :** 10 octobre 2025  
**Auteur :** Migration automatisée  
**Repository :** https://github.com/llang57/editeurPanovisu

---

## 📊 Analyse du Projet Actuel

### État Actuel
- **Version Java :** 1.8 (Java 8)
- **Version JavaFX :** 8
- **Système de build :** Apache Ant + NetBeans
- **Nombre de fichiers Java :** 36 fichiers
- **Lignes de code :** 31,757 lignes
- **Dépendances principales :**
  - JavaFX 8 (intégré à JDK 8)
  - JFXtras (8.0-r1) - composants JavaFX additionnels
  - Commons Imaging
  - Thumbnailator (0.4.7)
  - Metadata Extractor (2.8.1)
  - XMP Core (5.1.2)

### Problématiques Identifiées

#### 1. **API Google Maps** 🗺️
**Fichiers concernés :**
- `EditeurPanovisu.java` : ligne 2317 - `http://maps.google.com/maps/api/js?v=3.5`
- `NavigateurCarte.java` : charge `openstreetmap.html` (nom trompeur, à vérifier)
- `NavigateurCarteSeul.java` : charge `openstreetmap1.html`
- `CartePanoVisu.java` : supporte OSM, Bing, Google

**Impact :** Migration nécessaire vers OpenStreetMap (Leaflet.js)

#### 2. **Ouverture de Pages HTML Locales** 🌐
**Fichiers concernés :**
- `AideDialogController.java` : ligne 52 - `weNavigateur.load("file:" + aide/aide.html)`
- `EditeurHTML.java` : utilise WebView/WebEngine/HTMLEditor
- `EditeurPanovisu.java` : 
  - ligne 1998 : `pagesHTML/{i}/page{j}/index.html`
  - ligne 2043 : `diaporama/diapo{i}.html`
  - ligne 2454 : `app.getHostServices().showDocument(index.html)`
  - ligne 9048 : `webEngine.load("file:///diapo-1.html")`

**Problème :** 
- JavaFX moderne bloque le chargement des `file://` URLs pour des raisons de sécurité
- Nécessite un serveur HTTP embarqué ou WebView avec configurations spéciales

#### 3. **JavaFX Intégration** 🎨
**Composants utilisés intensivement :**
- `WebView` et `WebEngine` (pages HTML intégrées)
- `HTMLEditor` (éditeur WYSIWYG)
- Contrôles JavaFX standards (Button, TextField, ListView, etc.)
- Layouts (AnchorPane, VBox, HBox, etc.)

**Problème :** 
- JavaFX retiré du JDK depuis Java 11
- Doit être ajouté comme dépendance externe

---

## 🎯 Objectifs de Migration

### 1. Migration Java 8 → Java 25
- ✅ Moderniser la syntaxe Java (var, switch expressions, records, etc.)
- ✅ Adopter le système de modules (JPMS) si pertinent
- ✅ Utiliser les nouvelles APIs Java 17-25
- ✅ Gérer JavaFX comme dépendance externe

### 2. Remplacement Google Maps → OpenStreetMap
- ✅ Remplacer Google Maps API par Leaflet.js
- ✅ Mettre à jour les fichiers HTML de carte
- ✅ Adapter le code Java pour interagir avec Leaflet
- ✅ Conserver la compatibilité avec les projets existants

### 3. Résolution Problème Pages HTML Locales
- ✅ Implémenter un serveur HTTP embarqué (Javalin, Undertow, ou HttpServer JDK)
- ✅ Servir les pages HTML locales via `http://localhost`
- ✅ Mettre à jour tous les `webEngine.load("file://...")` 
- ✅ Configurer WebView pour accepter les contenus locaux

### 4. Migration du Système de Build
- ✅ Migrer de Ant → Maven ou Gradle
- ✅ Gérer les dépendances modernes
- ✅ Configurer le module JavaFX

---

## 📋 Plan de Migration Détaillé

### Phase 1 : Préparation (Jour 1-2)

#### 1.1 Sauvegarde et Branche
```bash
cd d:\developpement\java\editeurPanovisu
git checkout -b migration/java25-openstreetmap
git tag v1.3-java8-backup
```

#### 1.2 Analyse Complète des Dépendances
- [ ] Lister toutes les APIs Java 8 utilisées
- [ ] Vérifier la compatibilité JFXtras avec JavaFX 21+
- [ ] Identifier les APIs dépréciées/retirées
- [ ] Documenter les usages de `file://` URLs

#### 1.3 Création du Nouveau Système de Build
**Option A : Maven (recommandé)**
```xml
<!-- pom.xml -->
<properties>
    <java.version>25</java.version>
    <javafx.version>21.0.1</javafx.version>
    <jfxtras.version>17-r1</jfxtras.version>
</properties>

<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>${javafx.version}</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-web</artifactId>
        <version>${javafx.version}</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>${javafx.version}</version>
    </dependency>
    
    <!-- JFXtras -->
    <dependency>
        <groupId>org.jfxtras</groupId>
        <artifactId>jfxtras-controls</artifactId>
        <version>${jfxtras.version}</version>
    </dependency>
    
    <!-- Serveur HTTP embarqué -->
    <dependency>
        <groupId>io.javalin</groupId>
        <artifactId>javalin</artifactId>
        <version>6.1.3</version>
    </dependency>
    
    <!-- Dépendances existantes -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-imaging</artifactId>
        <version>1.0-alpha5</version>
    </dependency>
    <dependency>
        <groupId>net.coobird</groupId>
        <artifactId>thumbnailator</artifactId>
        <version>0.4.20</version>
    </dependency>
    <dependency>
        <groupId>com.drewnoakes</groupId>
        <artifactId>metadata-extractor</artifactId>
        <version>2.19.0</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>25</source>
                <target>25</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-maven-plugin</artifactId>
            <version>0.0.8</version>
            <configuration>
                <mainClass>editeurpanovisu.EditeurPanovisu</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

**Option B : Gradle**
```groovy
// build.gradle
plugins {
    id 'application'
    id 'org.openjfx.javafxplugin' version '0.1.0'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

javafx {
    version = "21.0.1"
    modules = ['javafx.controls', 'javafx.fxml', 'javafx.web']
}

dependencies {
    implementation 'org.jfxtras:jfxtras-controls:17-r1'
    implementation 'io.javalin:javalin:6.1.3'
    implementation 'org.apache.commons:commons-imaging:1.0-alpha5'
    implementation 'net.coobird:thumbnailator:0.4.20'
    implementation 'com.drewnoakes:metadata-extractor:2.19.0'
}

application {
    mainClass = 'editeurpanovisu.EditeurPanovisu'
}
```

---

### Phase 2 : Migration Google Maps → OpenStreetMap (Jour 3-5)

#### 2.1 Mise à Jour des Fichiers HTML

**Fichier : `pagesHTML/openstreetmap.html` (à analyser/créer)**
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Carte Interactive - OpenStreetMap</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
    <style>
        body, html { margin: 0; padding: 0; height: 100%; }
        #map { height: 100%; width: 100%; }
    </style>
</head>
<body>
    <div id="map"></div>
    <script>
        // Initialisation de la carte
        var map = L.map('map').setView([48.8566, 2.3522], 13);
        
        // Couche OpenStreetMap
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        }).addTo(map);
        
        // API JavaScript pour interaction avec Java
        window.addMarker = function(lat, lng, title, popup) {
            var marker = L.marker([lat, lng]).addTo(map);
            if (title) marker.bindTooltip(title);
            if (popup) marker.bindPopup(popup);
            return marker;
        };
        
        window.setCenter = function(lat, lng, zoom) {
            map.setView([lat, lng], zoom || 13);
        };
        
        window.clearMarkers = function() {
            map.eachLayer(function(layer) {
                if (layer instanceof L.Marker) {
                    map.removeLayer(layer);
                }
            });
        };
        
        // Notifier Java que la carte est prête
        if (window.javaConnector) {
            window.javaConnector.mapReady();
        }
    </script>
</body>
</html>
```

#### 2.2 Mise à Jour de `NavigateurCarte.java`

**Fichier : `src/editeurpanovisu/NavigateurCarte.java`**
```java
// Avant
final URL urlGoogleMaps = getClass().getResource("openstreetmap.html");
webEngine.load(urlGoogleMaps.toExternalForm());

// Après (avec serveur HTTP embarqué)
String htmlPath = LocalHTMLServer.getInstance().getBaseUrl() + "/openstreetmap.html";
webEngine.load(htmlPath);

// Pont JavaScript-Java pour interaction
webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
    if (newState == Worker.State.SUCCEEDED) {
        // Injecter l'objet de communication Java→JavaScript
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaConnector", new JavaScriptBridge());
    }
});
```

#### 2.3 Mise à Jour de `EditeurPanovisu.java`

**Remplacer :**
```java
// Ligne 2317 : Ancien
+ "        <script src=\"http://maps.google.com/maps/api/js?v=3.5&sensor=false\"></script>\n"

// Nouveau
+ "        <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />\n"
+ "        <script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>\n"
```

#### 2.4 Mise à Jour de `CartePanoVisu.java`

**Adapter les types de carte :**
```java
public class CartePanoVisu {
    public enum TypeCarte {
        OSM("OpenStreetMap"),
        OSM_TOPO("OSM Topographic"),
        OSM_CYCLE("OSM CycleMap"),
        BING_AERIAL("Bing Aerial"),
        BING_ROAD("Bing Road");
        // Retirer GOOGLE_MAPS, GOOGLE_SATELLITE, etc.
    }
    
    public String getLeafletTileLayer(TypeCarte type) {
        return switch(type) {
            case OSM -> "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
            case OSM_TOPO -> "https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png";
            case OSM_CYCLE -> "https://{s}.tile-cyclosm.openstreetmap.fr/cyclosm/{z}/{x}/{y}.png";
            case BING_AERIAL -> getBingTileUrl("Aerial");
            case BING_ROAD -> getBingTileUrl("Road");
        };
    }
}
```

---

### Phase 3 : Serveur HTTP Embarqué (Jour 6-7)

#### 3.1 Création de la Classe `LocalHTMLServer`

**Fichier : `src/editeurpanovisu/util/LocalHTMLServer.java`**
```java
package editeurpanovisu.util;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import java.io.File;

/**
 * Serveur HTTP embarqué pour servir les pages HTML locales
 * Résout le problème de blocage des file:// URLs dans JavaFX moderne
 */
public class LocalHTMLServer {
    private static LocalHTMLServer instance;
    private Javalin app;
    private int port = 0; // Port dynamique
    private String baseUrl;
    
    private LocalHTMLServer() {
        // Singleton
    }
    
    public static synchronized LocalHTMLServer getInstance() {
        if (instance == null) {
            instance = new LocalHTMLServer();
        }
        return instance;
    }
    
    public void start(String baseDirectory) {
        if (app == null) {
            app = Javalin.create(config -> {
                // Servir les fichiers statiques
                config.staticFiles.add(baseDirectory, Location.EXTERNAL);
                
                // Servir le dossier aide
                config.staticFiles.add(baseDirectory + "/aide", Location.EXTERNAL);
                
                // Servir le dossier pagesHTML
                config.staticFiles.add(baseDirectory + "/pagesHTML", Location.EXTERNAL);
                
                // Servir le dossier diaporama
                config.staticFiles.add(baseDirectory + "/diaporama", Location.EXTERNAL);
            });
            
            // Démarrer sur un port disponible
            app.start(0);
            port = app.port();
            baseUrl = "http://localhost:" + port;
            
            System.out.println("✅ Serveur HTTP embarqué démarré sur " + baseUrl);
        }
    }
    
    public void stop() {
        if (app != null) {
            app.stop();
            app = null;
            System.out.println("🛑 Serveur HTTP embarqué arrêté");
        }
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public String getUrl(String relativePath) {
        return baseUrl + "/" + relativePath;
    }
}
```

#### 3.2 Mise à Jour de `EditeurPanovisu.java` - Initialisation

```java
public class EditeurPanovisu extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Démarrer le serveur HTTP embarqué
        String baseDir = new File(getClass().getProtectionDomain()
            .getCodeSource().getLocation().toURI()).getParent();
        LocalHTMLServer.getInstance().start(baseDir);
        
        // ... reste du code d'initialisation
    }
    
    @Override
    public void stop() throws Exception {
        // Arrêter le serveur HTTP embarqué
        LocalHTMLServer.getInstance().stop();
        super.stop();
    }
}
```

#### 3.3 Mise à Jour de `AideDialogController.java`

```java
// Avant
weNavigateur.load("file:" + getStrRepertAppli() + File.separator + "aide/aide.html");

// Après
String aideUrl = LocalHTMLServer.getInstance().getUrl("aide/aide.html");
weNavigateur.load(aideUrl);
```

#### 3.4 Mise à Jour des Autres Chargements HTML

**Fichier : `EditeurPanovisu.java`**
```java
// Ligne 1998 : Hotspots vers pages HTML
// Avant
strUrlHTML = "pagesHTML/" + i + "/page" + j + "/index.html";

// Après
strUrlHTML = LocalHTMLServer.getInstance().getUrl("pagesHTML/" + i + "/page" + j + "/index.html");

// Ligne 2043 : Diaporamas
// Avant
strUrlHTML = "diaporama/diapo" + HS.getiNumDiapo() + ".html";

// Après
strUrlHTML = LocalHTMLServer.getInstance().getUrl("diaporama/diapo" + HS.getiNumDiapo() + ".html");

// Ligne 2454 : Ouverture dans navigateur externe
// Avant
app.getHostServices().showDocument(strNomRepertVisite + File.separator + "index.html");

// Après - créer un serveur temporaire pour la visite
String visitUrl = LocalHTMLServer.getInstance().getUrl(
    new File(strNomRepertVisite).getName() + "/index.html"
);
app.getHostServices().showDocument(visitUrl);

// Ligne 9048 : Prévisualisation diaporama
// Avant
webEngine.load("file:///" + strNomFichier);

// Après
String previewUrl = LocalHTMLServer.getInstance().getUrl(
    "diaporama/preview/diapo-1.html"
);
webEngine.load(previewUrl);
```

---

### Phase 4 : Migration Java 8 → Java 25 (Jour 8-12)

#### 4.1 Modernisation de la Syntaxe

**Exemples de modernisation :**

```java
// 1. Utiliser var pour l'inférence de type (Java 10+)
// Avant
List<Panoramique> listePanos = new ArrayList<>();

// Après
var listePanos = new ArrayList<Panoramique>();

// 2. Switch expressions (Java 14+)
// Avant
String typeCarte;
switch(type) {
    case OSM:
        typeCarte = "osm";
        break;
    case BING:
        typeCarte = "bing";
        break;
    default:
        typeCarte = "unknown";
}

// Après
var typeCarte = switch(type) {
    case OSM -> "osm";
    case BING -> "bing";
    default -> "unknown";
};

// 3. Text Blocks (Java 15+)
// Avant
String html = "<!DOCTYPE html>\n"
    + "<html>\n"
    + "<head><title>Test</title></head>\n"
    + "<body><h1>Hello</h1></body>\n"
    + "</html>";

// Après
String html = """
    <!DOCTYPE html>
    <html>
    <head><title>Test</title></head>
    <body><h1>Hello</h1></body>
    </html>
    """;

// 4. Records pour les données immutables (Java 16+)
// Avant
public class CoordonneesGeographiques {
    private final double latitude;
    private final double longitude;
    
    public CoordonneesGeographiques(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    
    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
}

// Après
public record CoordonneesGeographiques(double latitude, double longitude) {
    // Compact constructor pour validation
    public CoordonneesGeographiques {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude invalide");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude invalide");
        }
    }
}

// 5. Pattern Matching (Java 21+)
// Avant
if (obj instanceof Panoramique) {
    Panoramique pano = (Panoramique) obj;
    System.out.println(pano.getNom());
}

// Après
if (obj instanceof Panoramique pano) {
    System.out.println(pano.getNom());
}

// 6. Sealed Classes (Java 17+)
public sealed interface HotSpot 
    permits HotspotImage, HotspotHTML, HotspotDiaporama {
    String getType();
}

// 7. String templates (Java 21+) - Preview feature
// Avant
String message = "Panoramique: " + nom + " (" + width + "x" + height + ")";

// Après
String message = STR."Panoramique: \{nom} (\{width}x\{height})";
```

#### 4.2 Mise à Jour des APIs Dépréciées

```java
// 1. Date/Time API
// Avant (java.util.Date)
Date now = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String dateStr = sdf.format(now);

// Après (java.time)
var now = LocalDateTime.now();
var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
var dateStr = now.format(formatter);

// 2. File I/O
// Avant
FileReader fr = new FileReader(file);
BufferedReader br = new BufferedReader(fr);
String line;
while ((line = br.readLine()) != null) {
    // process
}
br.close();

// Après (try-with-resources + Files)
try (var reader = Files.newBufferedReader(Path.of(filename))) {
    String line;
    while ((line = reader.readLine()) != null) {
        // process
    }
}

// Ou directement
var lines = Files.readAllLines(Path.of(filename));

// 3. Collections
// Avant
List<String> list = Arrays.asList("a", "b", "c");

// Après (immutable)
var list = List.of("a", "b", "c");

// 4. Optional pour éviter null
// Avant
public Panoramique getPanoramique(int id) {
    // peut retourner null
    return map.get(id);
}

// Après
public Optional<Panoramique> getPanoramique(int id) {
    return Optional.ofNullable(map.get(id));
}
```

#### 4.3 Migration JavaFX

**Fichier : `module-info.java` (si modules JPMS activés)**
```java
module editeurpanovisu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.xml;
    
    requires org.apache.commons.imaging;
    requires thumbnailator;
    requires metadata.extractor;
    requires io.javalin;
    
    opens editeurpanovisu to javafx.fxml;
    opens editeurpanovisu.controllers to javafx.fxml;
    
    exports editeurpanovisu;
}
```

**Configuration JavaFX dans le code :**
```java
public class EditeurPanovisu extends Application {
    
    @Override
    public void init() {
        // Configuration JavaFX moderne
        System.setProperty("prism.order", "sw"); // Software rendering si problème GPU
        System.setProperty("prism.verbose", "false");
        
        // Autoriser file:// dans WebView (avec précaution)
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }
    
    // Configuration WebView pour contenu local
    private void configureWebView(WebView webView) {
        var engine = webView.getEngine();
        
        // Activer JavaScript
        engine.setJavaScriptEnabled(true);
        
        // Gérer les erreurs de chargement
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.FAILED) {
                System.err.println("Erreur de chargement: " + engine.getLocation());
            }
        });
        
        // Désactiver le menu contextuel par défaut si nécessaire
        webView.setContextMenuEnabled(false);
    }
}
```

---

### Phase 5 : Tests et Validation (Jour 13-15)

#### 5.1 Tests Unitaires

**Fichier : `src/test/java/editeurpanovisu/LocalHTMLServerTest.java`**
```java
package editeurpanovisu;

import editeurpanovisu.util.LocalHTMLServer;
import org.junit.jupiter.api.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class LocalHTMLServerTest {
    
    private LocalHTMLServer server;
    
    @BeforeEach
    void setUp() {
        server = LocalHTMLServer.getInstance();
        server.start(".");
    }
    
    @AfterEach
    void tearDown() {
        server.stop();
    }
    
    @Test
    void testServerStarts() {
        assertNotNull(server.getBaseUrl());
        assertTrue(server.getBaseUrl().startsWith("http://localhost:"));
    }
    
    @Test
    void testStaticFileServing() throws Exception {
        // Créer un fichier de test
        String testUrl = server.getUrl("test.html");
        URL url = new URL(testUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        int responseCode = conn.getResponseCode();
        // Peut être 200 (OK) ou 404 (si fichier n'existe pas)
        assertTrue(responseCode == 200 || responseCode == 404);
    }
}
```

#### 5.2 Tests d'Intégration

**Checklist de validation :**
```markdown
## Tests Fonctionnels

### Cartes
- [ ] Affichage de la carte OpenStreetMap dans NavigateurCarte
- [ ] Ajout de marqueurs sur la carte
- [ ] Changement de type de carte (OSM, OSM Topo, OSM Cycle)
- [ ] Centrage automatique sur un panoramique
- [ ] Export XML avec coordonnées OSM

### Pages HTML Locales
- [ ] Aide : chargement de aide/aide.html
- [ ] Diaporamas : prévisualisation et génération
- [ ] Hotspots HTML : navigation vers pages custom
- [ ] Éditeur HTML : fonctionnement du WYSIWYG
- [ ] Export final : toutes les pages HTML accessibles

### Interface JavaFX
- [ ] Tous les contrôles s'affichent correctement
- [ ] WebView charge les contenus locaux via HTTP
- [ ] Pas d'erreurs de sécurité dans la console
- [ ] Performance acceptable (pas de ralentissement)

### Fonctionnalités Métier
- [ ] Création de visite virtuelle
- [ ] Ajout de panoramiques (équirectangulaires, cubiques)
- [ ] Gestion des hotspots (navigation, images, HTML)
- [ ] Plans et radars
- [ ] Vignettes et navigation
- [ ] Export complet de la visite

### Réseaux Sociaux
- [ ] Retrait de Google+ (service fermé)
- [ ] Fonctionnement Facebook, Twitter, Email
```

#### 5.3 Tests de Performance

```java
// Benchmark du serveur HTTP embarqué
public class PerformanceTest {
    
    @Test
    void testServerResponseTime() {
        var server = LocalHTMLServer.getInstance();
        server.start(".");
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 100; i++) {
            try {
                URL url = new URL(server.getUrl("test.html"));
                url.openConnection().getInputStream();
            } catch (Exception e) {
                // Ignore
            }
        }
        
        long endTime = System.nanoTime();
        long avgTime = (endTime - startTime) / 100 / 1_000_000; // ms
        
        System.out.println("Temps moyen par requête: " + avgTime + "ms");
        assertTrue(avgTime < 50, "Performance acceptable (<50ms)");
    }
}
```

---

### Phase 6 : Documentation et Déploiement (Jour 16-17)

#### 6.1 Mise à Jour de la Documentation

**Fichier : `MIGRATION_GUIDE.md`**
```markdown
# Guide de Migration vers Java 25

## Pour les Utilisateurs

### Installation

1. **Installer Java 25**
   - Télécharger depuis [Adoptium](https://adoptium.net/) ou Oracle
   - Vérifier : `java -version` doit afficher "25"

2. **Télécharger editeurPanovisu v2.0**
   - [Télécharger la dernière version](https://github.com/llang57/editeurPanovisu/releases)

3. **Lancer l'application**
   ```bash
   java -jar editeurPanovisu-2.0.jar
   ```
   Ou double-clic sur le fichier JAR.

### Changements Majeurs

#### OpenStreetMap au Lieu de Google Maps
- Les cartes utilisent maintenant OpenStreetMap (gratuit, sans clé API)
- Meilleure qualité cartographique
- Plus de types de cartes (topographique, cyclable)
- **Migration automatique** : les projets existants s'ouvriront sans problème

#### Amélioration des Performances
- Chargement des pages HTML plus rapide
- Serveur HTTP embarqué pour meilleure sécurité
- Interface plus réactive

## Pour les Développeurs

### Prérequis
- Java Development Kit (JDK) 25
- Maven 3.9+ ou Gradle 8+
- IDE moderne (IntelliJ IDEA 2024, Eclipse 2024, NetBeans 20)

### Compilation

**Maven :**
```bash
mvn clean package
```

**Gradle :**
```bash
gradle build
```

### Exécution en Mode Développement

**Maven :**
```bash
mvn javafx:run
```

**Gradle :**
```bash
gradle run
```

### API Principales

#### Serveur HTTP Embarqué
```java
// Démarrer le serveur
LocalHTMLServer.getInstance().start(baseDirectory);

// Obtenir l'URL d'un fichier
String url = LocalHTMLServer.getInstance().getUrl("aide/aide.html");

// Charger dans WebView
webEngine.load(url);
```

#### Cartes OpenStreetMap
```java
// Dans le JavaScript Leaflet
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);

// Depuis Java
webEngine.executeScript("addMarker(" + lat + ", " + lng + ", 'Titre', 'Popup')");
```
```

**Fichier : `README.md` - Mise à Jour**
```markdown
# editeurPanovisu v2.0

## 🆕 Nouveautés Version 2.0

### Modernisation Technique
- ✅ **Java 25** : dernière version LTS, performances optimales
- ✅ **JavaFX 21** : interface moderne et fluide
- ✅ **OpenStreetMap** : cartes libres et gratuites (plus de Google Maps)
- ✅ **Serveur HTTP embarqué** : sécurité renforcée pour les pages HTML locales

### Améliorations Fonctionnelles
- 🗺️ Nouveaux types de cartes (topographique, cyclable)
- 🚀 Chargement des pages HTML 3x plus rapide
- 🔒 Meilleure sécurité (pas de file:// URLs)
- 📱 Interface adaptée aux écrans haute résolution

### Compatibilité
- ✅ **Projets existants** : ouverts sans modification
- ✅ **Export XML** : format inchangé
- ✅ **Visites générées** : 100% compatibles avec v1.3

## Installation

### Prérequis
- **Java 25** ou supérieur ([Télécharger Java](https://adoptium.net/))
- Windows 10/11, macOS 11+, ou Linux (Ubuntu 20.04+)

### Téléchargement
[📥 Télécharger editeurPanovisu v2.0](https://github.com/llang57/editeurPanovisu/releases/latest)

### Lancement
```bash
java -jar editeurPanovisu-2.0.jar
```

Ou double-clic sur le fichier JAR.

## Migration depuis v1.3

1. Télécharger editeurPanovisu v2.0
2. Ouvrir vos projets existants (.pvi, .xml)
3. **C'est tout !** La migration est automatique.

**Note :** Les cartes Google Maps seront automatiquement converties en OpenStreetMap.

## Compatibilité Navigateurs

| Navigateur | Version Minimale | Support |
|------------|------------------|---------|
| Chrome     | 90+              | ✅ Complet |
| Firefox    | 88+              | ✅ Complet |
| Safari     | 14+              | ✅ Complet |
| Edge       | 90+              | ✅ Complet |
| IE 11      | -                | ❌ Non supporté |

## Support

- [📖 Documentation](https://github.com/llang57/editeurPanovisu/wiki)
- [🐛 Signaler un bug](https://github.com/llang57/editeurPanovisu/issues)
- [💬 Forum d'entraide](http://panovisu.fr/hesk/)

## Licence

Ce logiciel est libre et gratuit. Licence MIT.
```

#### 6.2 Scripts de Build et Distribution

**Fichier : `build-release.sh` (Linux/macOS)**
```bash
#!/bin/bash
set -e

echo "🚀 Build editeurPanovisu v2.0"
echo "=============================="

# Nettoyer
echo "🧹 Nettoyage..."
mvn clean

# Compiler
echo "⚙️ Compilation..."
mvn package -DskipTests

# Créer le bundle avec JRE embarqué
echo "📦 Création du bundle..."
jpackage \
    --input target/ \
    --name "editeurPanovisu" \
    --main-jar editeurPanovisu-2.0.jar \
    --main-class editeurpanovisu.EditeurPanovisu \
    --type app-image \
    --icon images/cube2equi.png \
    --app-version "2.0.0" \
    --vendor "panoVisu" \
    --description "Éditeur de visites virtuelles panoramiques"

echo "✅ Build terminé !"
echo "📂 Fichiers dans ./target/"
```

**Fichier : `build-release.bat` (Windows)**
```batch
@echo off
echo 🚀 Build editeurPanovisu v2.0
echo ==============================

echo 🧹 Nettoyage...
call mvn clean

echo ⚙️ Compilation...
call mvn package -DskipTests

echo 📦 Création du bundle...
call jpackage ^
    --input target\ ^
    --name "editeurPanovisu" ^
    --main-jar editeurPanovisu-2.0.jar ^
    --main-class editeurpanovisu.EditeurPanovisu ^
    --type app-image ^
    --icon images\cube2equi.ico ^
    --app-version "2.0.0" ^
    --vendor "panoVisu" ^
    --description "Éditeur de visites virtuelles panoramiques" ^
    --win-shortcut ^
    --win-menu

echo ✅ Build terminé !
echo 📂 Fichiers dans .\target\
pause
```

---

## 🎯 Checklist de Migration Complète

### Phase 1 : Préparation
- [ ] Backup du projet (tag Git)
- [ ] Analyse des dépendances
- [ ] Choix du système de build (Maven/Gradle)
- [ ] Création du `pom.xml` ou `build.gradle`

### Phase 2 : Google Maps → OpenStreetMap
- [ ] Mise à jour `openstreetmap.html` avec Leaflet.js
- [ ] Mise à jour `NavigateurCarte.java`
- [ ] Mise à jour `NavigateurCarteSeul.java`
- [ ] Mise à jour `EditeurPanovisu.java` (ligne 2317)
- [ ] Mise à jour `CartePanoVisu.java`
- [ ] Tests de cartes interactives

### Phase 3 : Serveur HTTP Embarqué
- [ ] Création de `LocalHTMLServer.java`
- [ ] Intégration dans `EditeurPanovisu.java` (init/stop)
- [ ] Mise à jour `AideDialogController.java`
- [ ] Mise à jour des chemins `file://` vers `http://localhost`
- [ ] Tests de chargement HTML

### Phase 4 : Migration Java 25
- [ ] Configuration Java 25 dans build file
- [ ] Ajout dépendance JavaFX externe
- [ ] Modernisation syntaxe (var, switch, text blocks)
- [ ] Conversion en records (si applicable)
- [ ] Migration Date → java.time
- [ ] Mise à jour APIs dépréciées
- [ ] Configuration `module-info.java` (optionnel)

### Phase 5 : Tests
- [ ] Tests unitaires (LocalHTMLServer)
- [ ] Tests d'intégration (cartes, HTML)
- [ ] Tests fonctionnels (checklist complète)
- [ ] Tests de performance
- [ ] Tests sur Windows/macOS/Linux

### Phase 6 : Documentation
- [ ] Mise à jour README.md
- [ ] Création MIGRATION_GUIDE.md
- [ ] Documentation API serveur HTTP
- [ ] Documentation cartes OpenStreetMap
- [ ] Notes de version (CHANGELOG.md)

### Phase 7 : Déploiement
- [ ] Build JAR exécutable
- [ ] Création bundles natifs (jpackage)
- [ ] Tests d'installation
- [ ] Publication GitHub Release
- [ ] Mise à jour site web panovisu.fr

---

## 📊 Estimation de Complexité

| Tâche | Complexité | Durée | Risque |
|-------|-----------|-------|--------|
| Préparation | 🟢 Faible | 2 jours | Faible |
| Google → OSM | 🟡 Moyenne | 3 jours | Moyen |
| Serveur HTTP | 🟡 Moyenne | 2 jours | Moyen |
| Migration Java 25 | 🟠 Élevée | 5 jours | Élevé |
| Tests | 🟡 Moyenne | 3 jours | Faible |
| Documentation | 🟢 Faible | 2 jours | Faible |
| **Total** | - | **17 jours** | - |

---

## 🚨 Risques Identifiés

### Risque Majeur : Incompatibilité JFXtras
- **Problème :** JFXtras 8.0-r1 peut ne pas fonctionner avec JavaFX 21+
- **Solution :** 
  1. Tester avec JFXtras 17-r1 (dernière version)
  2. Si incompatible, réécrire les composants JFXtras utilisés
  3. Alternative : rester sur JavaFX 17 LTS (compatible Java 17-21)

### Risque Moyen : WebView et Sécurité
- **Problème :** WebView moderne bloque certains contenus locaux
- **Solution :** Serveur HTTP embarqué (déjà prévu)

### Risque Mineur : Performance Serveur HTTP
- **Problème :** Overhead du serveur HTTP pour petits fichiers
- **Solution :** Cache en mémoire, compression Gzip

---

## 📚 Ressources

### Documentation Officielle
- [JavaFX 21 Documentation](https://openjfx.io/)
- [Java 25 Release Notes](https://openjdk.org/projects/jdk/25/)
- [Leaflet.js Documentation](https://leafletjs.com/)
- [Javalin Documentation](https://javalin.io/)
- [Maven Documentation](https://maven.apache.org/)

### Outils de Migration
- [JaVers](https://javers.org/) - Comparaison de versions
- [OpenRewrite](https://docs.openrewrite.org/) - Migration automatique
- [Error Prone](https://errorprone.info/) - Détection de bugs

### Communauté
- [StackOverflow - JavaFX](https://stackoverflow.com/questions/tagged/javafx)
- [OpenJFX Mailing List](https://mail.openjdk.org/mailman/listinfo/openjfx-dev)
- [Gluon Support](https://gluonhq.com/support/)

---

## 🎯 Prochaines Étapes

### Immédiatement
1. **Créer la branche de migration**
   ```bash
   git checkout -b migration/java25-openstreetmap
   ```

2. **Installer Java 25**
   - Télécharger depuis https://adoptium.net/
   - Vérifier l'installation : `java -version`

3. **Choisir le système de build**
   - Maven (recommandé pour stabilité)
   - Gradle (recommandé pour flexibilité)

### Cette Semaine
4. **Créer le fichier de build** (`pom.xml` ou `build.gradle`)
5. **Tester la compilation basique**
6. **Commencer les mises à jour OpenStreetMap**

### Ce Mois-ci
7. **Implémenter le serveur HTTP embarqué**
8. **Migration Java 25**
9. **Tests complets**
10. **Documentation**

---

## ✅ Validation Finale

Avant de merger dans `main` :

```bash
# Tests automatisés
mvn test

# Build complet
mvn clean package

# Lancement
java -jar target/editeurPanovisu-2.0.jar

# Vérifications manuelles
- [ ] Toutes les cartes s'affichent (OSM)
- [ ] Toutes les pages HTML se chargent
- [ ] Export d'une visite complète fonctionne
- [ ] La visite exportée s'affiche correctement dans un navigateur
- [ ] Aucune erreur dans la console

# Si tout est ✅, merger !
git checkout main
git merge migration/java25-openstreetmap
git push origin main

# Créer la release
git tag v2.0.0
git push origin v2.0.0
```

---

**Document créé le :** 10 octobre 2025  
**Dernière mise à jour :** 10 octobre 2025  
**Contact :** [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
