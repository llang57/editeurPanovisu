# ⚙️ Configuration avancée

Configuration détaillée de PanoVisu pour les utilisateurs expérimentés.

## Table des matières

- [Configuration des clés API](#configuration-des-clés-api)
- [Fichier XML de configuration](#fichier-xml-de-configuration)
- [Serveur HTTP intégré](#serveur-http-intégré)
- [Optimisation des performances](#optimisation-des-performances)
- [Variables d'environnement](#variables-denvironnement)
- [Configuration Maven](#configuration-maven)

## Configuration des clés API

PanoVisu peut utiliser des services cartographiques commerciaux nécessitant des clés API.

### Création du fichier de configuration

1. **Copiez le fichier template** :
   ```bash
   cp api-keys.properties.template api-keys.properties
   ```

2. **Éditez** `api-keys.properties` :
   ```properties
   # Google Maps API (optionnel)
   google.maps.api.key=VOTRE_CLE_GOOGLE_MAPS
   
   # Bing Maps API (optionnel)
   bing.maps.api.key=VOTRE_CLE_BING_MAPS
   
   # OpenRouter AI (optionnel - fonctionnalités IA)
   openrouter.api.key=VOTRE_CLE_OPENROUTER
   ```

### Obtention des clés API

#### Google Maps

1. Rendez-vous sur [Google Cloud Console](https://console.cloud.google.com/)
2. Créez un nouveau projet
3. Activez **Maps JavaScript API**
4. Créez des identifiants → **Clé API**
5. (Recommandé) Restreignez la clé à votre domaine

**Prix** : 200$ de crédits gratuits par mois, puis facturation selon l'utilisation.

#### Bing Maps

1. Rendez-vous sur [Bing Maps Dev Center](https://www.bingmapsportal.com/)
2. Créez un compte (gratuit)
3. **My Account** → **Create or view keys**
4. Créez une nouvelle clé

**Prix** : Gratuit jusqu'à 125 000 transactions par an.

### Sécurité des clés API

⚠️ **Important** : 

- Le fichier `api-keys.properties` est **ignoré par Git** (`.gitignore`)
- **Ne commitez JAMAIS** vos clés API
- **Ne partagez PAS** ce fichier publiquement
- Utilisez des **restrictions de domaine** pour les clés web

Consultez [SECURITE_CLES_API.md](https://github.com/llang57/editeurPanovisu/blob/master/doc/SECURITE_CLES_API.md) pour plus de détails.

## Fichier XML de configuration

Le fichier `visite.xml` contient toute la configuration de votre visite virtuelle.

### Structure générale

```xml
<?xml version="1.0" encoding="UTF-8"?>
<visite>
    <generale>
        <titre>Ma Visite Virtuelle</titre>
        <description>Description de la visite</description>
        <auteur>Votre Nom</auteur>
    </generale>
    
    <panoramiques>
        <panoramique id="pano1" type="equirectangulaire">
            <nom>Salon</nom>
            <image>panos/salon.jpg</image>
            <vue_initiale>
                <fov>70</fov>
                <pan>0</pan>
                <tilt>0</tilt>
            </vue_initiale>
            <hotspots>
                <!-- Hotspots définis ici -->
            </hotspots>
        </panoramique>
    </panoramiques>
    
    <interface>
        <barre_navigation position="bottom" visible="true">
            <couleur>#3498db</couleur>
            <transparence>20</transparence>
        </barre_navigation>
    </interface>
</visite>
```

### Paramètres avancés

#### Limites de rotation

```xml
<limites>
    <horizontal min="-180" max="180" />
    <vertical min="-85" max="85" />
</limites>
```

#### Vitesse de rotation automatique

```xml
<rotation_auto>
    <vitesse>0.5</vitesse>
    <delay>5000</delay> <!-- millisecondes -->
</rotation_auto>
```

#### Qualité de rendu

```xml
<rendu>
    <antialias>true</antialias>
    <anisotropie>16</anisotropie>
    <precision>high</precision> <!-- low, medium, high -->
</rendu>
```

## Serveur HTTP intégré

Depuis la version 3.1, PanoVisu inclut un serveur HTTP intégré (`LocalHTTPServer`).

### Fonctionnement

Le serveur démarre automatiquement lors de l'ouverture d'une visite :

1. **Tentative** sur les ports 8080 à 8090
2. **Détection** du premier port disponible
3. **Démarrage** du serveur
4. **Ouverture** du navigateur sur `http://localhost:PORT`

### Configuration

Aucune configuration n'est nécessaire. Le serveur :

- ✅ Démarre automatiquement
- ✅ Détecte les ports libres
- ✅ Supporte tous les types MIME nécessaires
- ✅ Bascule sur `file://` en cas d'échec

### Désactivation (si nécessaire)

Si vous souhaitez désactiver le serveur HTTP :

```java
// Dans le code source
LocalHTTPServer.getInstance().setEnabled(false);
```

### Ports utilisés

Le serveur teste séquentiellement :
- 8080 (par défaut)
- 8081
- 8082
- ...
- 8090 (dernier essai)

Si tous les ports sont occupés, l'application bascule sur `file://` avec un avertissement.

## Optimisation des performances

### Mémoire Java

Allouez plus de mémoire pour les gros projets :

```bash
# 4 Go de RAM
java -Xmx4G -jar editeurPanovisu.jar

# 8 Go de RAM
java -Xmx8G -jar editeurPanovisu.jar
```

### Accélération graphique

#### Windows
```bash
java -Dprism.order=es2 -jar editeurPanovisu.jar
```

#### macOS
```bash
java -Dprism.order=es2,sw -jar editeurPanovisu.jar
```

#### Linux
```bash
java -Dprism.order=es2,sw -Dprism.forceGPU=true -jar editeurPanovisu.jar
```

### Désactivation des logs verbeux

```bash
java -Djavafx.verbose=false -jar editeurPanovisu.jar
```

### Optimisation des images

**Taille maximale recommandée** : 8192×4096 pixels

**Compression** :
```bash
# Avec ImageMagick
magick convert input.jpg -quality 85 -strip output.jpg

# Avec jpegoptim
jpegoptim --max=85 --strip-all input.jpg
```

**Format WebP** (meilleure compression) :
```bash
# Avec cwebp
cwebp -q 85 input.jpg -o output.webp
```

⚠️ **Note** : PanoVisu supporte WebP dans la version visualiseur, mais pas encore dans l'éditeur.

## Variables d'environnement

### JAVA_HOME

Définit le JDK à utiliser :

#### Windows
```powershell
setx JAVA_HOME "C:\Program Files\Java\jdk-25"
```

#### Linux/macOS
```bash
export JAVA_HOME=/path/to/jdk-25
export PATH="$JAVA_HOME/bin:$PATH"
```

### MAVEN_OPTS

Options pour Maven :

```bash
# Plus de mémoire pour Maven
export MAVEN_OPTS="-Xmx2G -XX:MaxMetaspaceSize=512m"

# Désactivation du téléchargement parallèle (si problèmes réseau)
export MAVEN_OPTS="$MAVEN_OPTS -Dmaven.artifact.threads=1"
```

### Proxy (derrière un firewall)

```bash
export MAVEN_OPTS="-Dhttp.proxyHost=proxy.example.com -Dhttp.proxyPort=8080 -Dhttps.proxyHost=proxy.example.com -Dhttps.proxyPort=8080"
```

## Configuration Maven

### Profils de build

Le fichier `pom.xml` contient plusieurs profils :

#### Production (par défaut)
```bash
mvn clean package
```

#### Développement (sans minification)
```bash
mvn clean package -P dev
```

#### Debug (avec symboles de débogage)
```bash
mvn clean package -P debug
```

### Skip des tests

Pour accélérer la compilation :

```bash
mvn clean package -DskipTests
```

### Build verbeux

Pour diagnostiquer les problèmes :

```bash
mvn clean package -X  # Mode debug Maven
mvn clean package -e  # Affichage des erreurs détaillées
```

### Cache Maven

**Emplacement** : `~/.m2/repository/`

**Nettoyage** :
```bash
# Supprimer tout le cache
rm -rf ~/.m2/repository

# Supprimer uniquement les dépendances JavaFX
rm -rf ~/.m2/repository/org/openjfx
```

### Configuration du nombre de builds

Le fichier `build.num` stocke le numéro de build auto-incrémenté.

**Format** :
```
#Build Number for ANT. Do not edit!
#Tue Oct 15 16:30:00 CEST 2025
build.number=2043
```

**Réinitialisation** :
```bash
echo "build.number=1" > build.num
```

## Configuration du visualiseur

### Fichier `panovisuInit.js`

Paramètres globaux du visualiseur :

```javascript
var config = {
    // Répertoire des assets
    basePath: './',
    
    // Fichier XML de configuration
    xmlFile: 'visite.xml',
    
    // Qualité de rendu WebGL
    antialiasing: true,
    anisotropy: 16,
    
    // Performance
    useWebGL2: true,
    powerPreference: 'high-performance',
    
    // Interface
    showControls: true,
    showFullscreen: true,
    showAutorotate: true,
    
    // Rotation automatique
    autoRotate: false,
    autoRotateSpeed: 0.5,
    autoRotateDelay: 5000
};
```

### CSS personnalisé

Créez `css/custom.css` pour personnaliser l'apparence :

```css
/* Barre de navigation */
.navbar {
    background: linear-gradient(to bottom, #2c3e50, #34495e) !important;
    border-radius: 10px;
}

/* Hotspots */
.hotspot {
    transition: transform 0.3s ease;
}

.hotspot:hover {
    transform: scale(1.2);
}

/* Infobulles */
.tooltip {
    background: rgba(0, 0, 0, 0.8);
    color: white;
    border-radius: 5px;
    padding: 10px;
    font-family: Arial, sans-serif;
}
```

## Dépannage avancé

### Logs détaillés

Activez les logs de débogage :

```bash
java -Djavafx.verbose=true \
     -Dprism.verbose=true \
     -Dprism.debug=true \
     -jar editeurPanovisu.jar 2>&1 | tee debug.log
```

### Problèmes de rendu JavaFX

Forcez le pipeline de rendu :

```bash
# Forcer le rendu logiciel (lent mais stable)
java -Dprism.order=sw -jar editeurPanovisu.jar

# Forcer OpenGL
java -Dprism.order=es2 -Dprism.vsync=false -jar editeurPanovisu.jar
```

### Problèmes de polices

Sur Linux, installez les polices nécessaires :

```bash
sudo apt install fontconfig fonts-dejavu fonts-liberation
```

## Ressources

- 📖 [Documentation API](https://llang57.github.io/editeurPanovisu/)
- 💬 [Discussions](https://github.com/llang57/editeurPanovisu/discussions)
- 🐛 [Issues](https://github.com/llang57/editeurPanovisu/issues)
- 📚 [Wiki](https://github.com/llang57/editeurPanovisu/wiki)
