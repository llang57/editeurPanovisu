# ❓ FAQ - Foire aux questions

Réponses aux questions les plus fréquentes sur PanoVisu.

## Table des matières

- [Général](#général)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Images et panoramiques](#images-et-panoramiques)
- [Hotspots](#hotspots)
- [Export et publication](#export-et-publication)
- [Problèmes courants](#problèmes-courants)
- [Licence et contribution](#licence-et-contribution)

## Général

### Qu'est-ce que PanoVisu ?

PanoVisu est un **logiciel libre** permettant de créer et visualiser des **visites virtuelles 360°**. Il se compose de :

1. **Un éditeur** (Java/JavaFX) pour créer les visites
2. **Un visualiseur** (HTML5/WebGL) pour les afficher dans les navigateurs

### PanoVisu est-il gratuit ?

**Oui, 100% gratuit et open source !** 

- Licence : **GPL-3.0**
- Pas de version premium
- Pas de limitation de fonctionnalités
- Pas de watermark
- Code source disponible sur [GitHub](https://github.com/llang57/editeurPanovisu)

### Quelle est la différence avec d'autres solutions ?

| Critère | PanoVisu | Solutions commerciales | Google Tour Creator (fermé) |
|---------|----------|----------------------|---------------------------|
| **Prix** | Gratuit | 50-500€/mois | Gratuit (fermé en 2021) |
| **Open Source** | ✅ Oui | ❌ Non | ❌ Non |
| **Installation locale** | ✅ Oui | ⚠️ Parfois | ❌ Cloud uniquement |
| **Pas d'abonnement** | ✅ Oui | ❌ Non | ✅ Oui |
| **Hébergement libre** | ✅ Oui | ❌ Limité | ❌ Google uniquement |
| **Pas de watermark** | ✅ Oui | ⚠️ Selon l'offre | ✅ Oui |

### Sur quelles plateformes PanoVisu fonctionne-t-il ?

**Éditeur** :
- ✅ Windows 10/11 (64-bit)
- ✅ macOS 11+ (Intel et Apple Silicon)
- ✅ Linux (Ubuntu, Debian, Fedora, etc.)

**Visualiseur** (navigateurs) :
- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Safari 15+
- ✅ Edge 90+
- ❌ Internet Explorer (obsolète)

## Installation

### Comment installer PanoVisu ?

**🚀 Installation simple (Recommandé)** : Téléchargez les installateurs prêts à l'emploi depuis [GitHub Releases](https://github.com/llang57/editeurPanovisu/releases).

**Les installateurs incluent :**
- ✅ Java 25 intégré
- ✅ Toutes les dépendances
- ✅ Aucune compilation nécessaire
- ✅ Disponibles pour Windows (.exe), macOS (.dmg) et Linux (.deb, .rpm)

Consultez le [Guide d'installation](Installation.md) pour plus de détails.

### Dois-je installer Java ?

**Non** si vous utilisez les installateurs officiels (recommandé). Java 25 est déjà inclus.

**Oui** uniquement si vous compilez depuis les sources pour le développement.

### Dois-je installer Maven ?

**Non** pour l'utilisation normale avec les installateurs.

**Oui** uniquement si vous compilez depuis les sources.

**Installation** :
```bash
# macOS
brew install maven

# Linux (Debian/Ubuntu)
sudo apt install maven

# Windows
# Télécharger depuis https://maven.apache.org/download.cgi
```

### L'éditeur ne se lance pas, que faire ?

**Vérifications** :

1. **Java installé** ?
   ```bash
   java -version
   ```

2. **Variable JAVA_HOME définie** ?
   ```bash
   echo $JAVA_HOME  # Linux/macOS
   echo %JAVA_HOME%  # Windows
   ```

3. **Version suffisante** (Java 25+) ?

4. **Mémoire suffisante** (4 Go RAM minimum) ?
   ```bash
   java -Xmx4G -jar editeurPanovisu.jar
   ```

Consultez le [Guide d'installation](Installation.md) pour plus de détails.

### L'éditeur est lent, comment l'optimiser ?

**Solutions** :

1. **Allouer plus de mémoire** :
   ```bash
   java -Xmx8G -jar editeurPanovisu.jar
   ```

2. **Activer l'accélération graphique** :
   ```bash
   java -Dprism.order=es2 -jar editeurPanovisu.jar
   ```

3. **Désactiver les effets** (si problèmes graphiques) :
   ```bash
   java -Dprism.order=sw -jar editeurPanovisu.jar
   ```

4. **Réduire la taille des images** (8192×4096 max recommandé)

## Utilisation

### Comment créer ma première visite virtuelle ?

Consultez le [Guide de démarrage rapide](Démarrage-rapide.md) pour un tutoriel complet en 10 minutes.

**Étapes résumées** :
1. Préparer vos images panoramiques
2. Créer un nouveau projet
3. Importer les panoramiques
4. Ajouter des hotspots de navigation
5. Personnaliser l'interface
6. Exporter la visite

### Combien de panoramiques puis-je ajouter ?

**Aucune limite théorique**, mais considérez :

- **Performance** : Au-delà de 50-100 panoramiques, le chargement peut être lent
- **Taille** : Chaque panoramique = 5-20 Mo (selon résolution)
- **Organisation** : Structurer en sous-projets si trop nombreux

**Recommandation** : 10-30 panoramiques par projet pour une expérience optimale.

### Puis-je modifier une visite après export ?

**Oui !** Rouvrez le projet dans l'éditeur :

1. **Fichier** → **Ouvrir un projet**
2. Sélectionner le fichier `.panovis` (dans le dossier du projet)
3. Modifier
4. **Fichier** → **Enregistrer**
5. Ré-exporter

### Comment sauvegarder mon travail ?

**Automatiquement** : PanoVisu sauvegarde à chaque modification.

**Manuellement** :
- **Ctrl+S** (Windows/Linux)
- **Cmd+S** (macOS)
- Menu **Fichier** → **Enregistrer**

**Sauvegarde de sécurité** : Copiez le dossier complet du projet.

## Images et panoramiques

### Quels formats d'images sont supportés ?

**Formats acceptés** :
- ✅ **JPEG** (`.jpg`, `.jpeg`) - Recommandé
- ✅ **PNG** (`.png`) - Plus lourd, inutile pour panoramiques
- ❌ **WebP** - Pas encore supporté (prévu v3.2)
- ❌ **HEIC** - Convertir en JPEG

### Quelle résolution d'image recommandez-vous ?

| Type de panoramique | Résolution recommandée | Résolution minimale |
|---------------------|----------------------|-------------------|
| Equirectangulaire | **8192×4096** | 4096×2048 |
| Cubemap (6 faces) | **4096×4096** par face | 2048×2048 par face |

**Pourquoi** :
- Meilleure qualité de zoom
- Moins de pixellisation
- Expérience immersive

**Mais** : Fichiers plus lourds (5-20 Mo/image).

### Comment créer des images panoramiques ?

**Options** :

1. **Caméra 360°** (recommandé) :
   - Ricoh Theta Z1
   - Insta360 One X2
   - GoPro MAX

2. **Assemblage manuel** (avec trépied) :
   - Prendre 20-30 photos qui se chevauchent
   - Assembler avec **Hugin** (gratuit) ou **PTGui** (payant)

3. **Smartphone** :
   - Google Street View (Android/iOS)
   - Cardboard Camera (Android)

4. **Modélisation 3D** :
   - Blender → Render 360° Equirectangular

### Mes panoramiques s'affichent mal, pourquoi ?

**Problèmes courants** :

1. **Image non equirectangulaire** :
   - Ratio doit être **2:1** (largeur = 2× hauteur)
   - Exemple : 8192×4096, 6000×3000, etc.

2. **Faces de cubemap mal nommées** :
   - Doivent contenir : `f`, `r`, `b`, `l`, `u`, `d`
   - Exemple : `salon_f.jpg`, `salon_r.jpg`, etc.

3. **Image trop lourde** :
   - Compresser avec ImageMagick :
   ```bash
   magick convert input.jpg -quality 85 -strip output.jpg
   ```

4. **Rotation incorrecte** :
   - Ajuster dans l'éditeur (angles pan/tilt)

### Comment réduire la taille de mes images ?

**Avec ImageMagick** (gratuit) :

```bash
# Installation
brew install imagemagick  # macOS
sudo apt install imagemagick  # Linux

# Compression JPEG (qualité 85%)
magick convert input.jpg -quality 85 -strip output.jpg

# Redimensionnement
magick convert input.jpg -resize 8192x4096 -quality 85 output.jpg

# Batch (tous les JPEG d'un dossier)
magick mogrify -path output/ -quality 85 -strip *.jpg
```

## Hotspots

### Quels types de hotspots existent ?

| Type | Icône | Fonction |
|------|-------|---------|
| **Navigation** | 🔵 | Changer de panoramique |
| **Image** | 🖼️ | Afficher une photo (galerie) |
| **HTML** | 📄 | Afficher contenu personnalisé |
| **Vidéo** | 🎬 | Intégrer YouTube, Vimeo, HTML5 |

### Comment ajouter une vidéo dans un hotspot HTML ?

**Exemple avec YouTube** :

1. Créer un hotspot **HTML**
2. Dans l'éditeur WYSIWYG, cliquer sur **Source** (mode code)
3. Coller :
   ```html
   <iframe width="560" height="315" 
       src="https://www.youtube.com/embed/VIDEO_ID" 
       frameborder="0" allowfullscreen>
   </iframe>
   ```
4. Remplacer `VIDEO_ID` par l'ID de votre vidéo

**Exemple avec vidéo locale** :

```html
<video width="640" height="480" controls>
    <source src="videos/ma-video.mp4" type="video/mp4">
    Votre navigateur ne supporte pas la vidéo HTML5.
</video>
```

### Puis-je personnaliser l'apparence des hotspots ?

**Oui !** Deux méthodes :

1. **Via l'éditeur** :
   - Sélectionner le hotspot
   - Panneau **Propriétés** → **Apparence**
   - Choisir forme, couleur, taille, icône

2. **Via CSS personnalisé** :
   ```css
   /* Fichier css/custom.css */
   .hotspot {
       background: rgba(255, 0, 0, 0.8);
       border: 3px solid white;
       border-radius: 50%;
       transition: transform 0.3s;
   }
   
   .hotspot:hover {
       transform: scale(1.3);
   }
   ```

### Comment supprimer un hotspot ?

**Méthodes** :

1. **Clic droit** sur le hotspot → **Supprimer**
2. **Sélectionner** le hotspot → **Touche Suppr**
3. **Panneau Hotspots** (liste) → **Icône poubelle**

**Raccourci** : **Suppr** ou **Delete**

## Export et publication

### Comment tester ma visite avant de la publier ?

**Depuis la version 3.1** : Le serveur HTTP intégré (**LocalHTTPServer**) démarre automatiquement.

**Fonctionnement** :
1. **Exporter** la visite
2. L'éditeur **lance automatiquement** un serveur local (port 8080-8090)
3. Le navigateur **s'ouvre** sur `http://localhost:8080`

**Alternative manuelle** (si serveur désactivé) :
```bash
# Avec Python 3
cd /chemin/vers/export
python -m http.server 8000

# Avec PHP
php -S localhost:8000
```

### Où héberger ma visite virtuelle ?

**Options** :

| Solution | Gratuit | Facile | Performance | Limites |
|----------|---------|--------|------------|---------|
| **GitHub Pages** | ✅ | ⚠️ Moyen | ⚠️ Moyen | 1 Go, fichiers < 100 Mo |
| **Netlify** | ✅ | ✅ Facile | ✅ Excellent | 100 Go trafic/mois |
| **Vercel** | ✅ | ✅ Facile | ✅ Excellent | 100 Go trafic/mois |
| **FTP perso** | ⚠️ Payant | ✅ Facile | ✅ Selon hébergeur | Selon forfait |

**Recommandation** : **Netlify** ou **Vercel** pour la simplicité et les performances.

### Erreur CORS lors de l'ouverture en local (file://)

**Problème** : Les navigateurs bloquent les requêtes entre fichiers locaux pour des raisons de sécurité.

**Solutions** :

1. **Utiliser LocalHTTPServer** (v3.1+) :
   - Démarrage automatique lors de l'export
   - Ports 8080 à 8090

2. **Lancer un serveur HTTP local** :
   ```bash
   # Python 3
   python -m http.server 8000
   
   # PHP
   php -S localhost:8000
   ```

3. **Désactiver la sécurité** (DÉCONSEILLÉ) :
   ```bash
   # Chrome (temporaire pour tests)
   chrome.exe --disable-web-security --user-data-dir="C:/temp/chrome"
   ```

### Comment optimiser le chargement de ma visite ?

**Optimisations** :

1. **Compresser les images** :
   ```bash
   magick mogrify -quality 85 -strip *.jpg
   ```

2. **Activer la minification** :
   - ✅ Cocher **Minifier JavaScript** lors de l'export

3. **Activer Gzip** sur le serveur :
   ```apache
   # Fichier .htaccess
   <IfModule mod_deflate.c>
       AddOutputFilterByType DEFLATE text/html text/css text/javascript application/javascript application/json application/xml
   </IfModule>
   ```

4. **Utiliser un CDN** (si hébergement perso) :
   - Cloudflare (gratuit)
   - AWS CloudFront

5. **Lazy loading** des panoramiques :
   - Les panoramiques non visibles ne sont chargés qu'au clic

### Ma visite ne fonctionne pas sur smartphone

**Vérifications** :

1. **HTTPS activé** ?
   - Les fonctionnalités WebGL/DeviceOrientation nécessitent HTTPS

2. **Images trop lourdes** ?
   - Limiter à 8192×4096 max
   - Compresser à qualité 80-85%

3. **Trop de panoramiques** ?
   - Limiter à 20-30 pour mobile

4. **JavaScript minifié** ?
   - Réduire le poids du code

**Test** : Utiliser l'outil de Chrome **Lighthouse** pour diagnostiquer.

## Problèmes courants

### "JavaFX runtime components are missing"

**Cause** : JavaFX n'est pas inclus dans le JDK installé.

**Solution** : Maven gère automatiquement JavaFX.

**Vérification** :
```bash
mvn clean package
```

Si l'erreur persiste :
```bash
rm -rf ~/.m2/repository/org/openjfx
mvn clean package -U
```

### "Port 8080 already in use"

**Cause** : Le port 8080 est utilisé par un autre programme (Skype, autres serveurs).

**Solution** : LocalHTTPServer (v3.1+) détecte automatiquement les ports libres (8080-8090).

**Alternative manuelle** :
```bash
# Trouver le processus sur le port 8080
# Windows
netstat -ano | findstr :8080

# Linux/macOS
lsof -i :8080

# Tuer le processus (remplacer PID)
kill -9 PID
```

### Mes modifications ne s'affichent pas après export

**Causes possibles** :

1. **Cache navigateur** :
   - **Ctrl+F5** (Windows/Linux)
   - **Cmd+Shift+R** (macOS)

2. **Mauvais dossier d'export** :
   - Vérifier que vous ouvrez le bon dossier

3. **Fichier XML non régénéré** :
   - Supprimer `visite.xml` dans le dossier d'export
   - Ré-exporter

### L'éditeur plante lors de l'import d'images

**Causes possibles** :

1. **Mémoire insuffisante** :
   ```bash
   java -Xmx8G -jar editeurPanovisu.jar
   ```

2. **Image corrompue** :
   - Vérifier l'intégrité avec :
   ```bash
   magick identify input.jpg
   ```

3. **Format non supporté** :
   - Convertir en JPEG :
   ```bash
   magick convert input.heic output.jpg
   ```

### Hotspots invisibles dans le visualiseur

**Vérifications** :

1. **Position correcte** ?
   - Vérifier coordonnées (latitude/longitude pour sphère)

2. **Couleur** trop proche du panoramique ?
   - Changer la couleur du hotspot

3. **Taille** trop petite ?
   - Augmenter la taille dans les propriétés

4. **Fichier XML corrompu** ?
   - Ré-exporter le projet

## Licence et contribution

### Puis-je utiliser PanoVisu pour un projet commercial ?

**Oui !** Licence **GPL-3.0** autorise l'usage commercial.

**Conditions** :
- ✅ Usage commercial autorisé
- ✅ Modification autorisée
- ✅ Distribution autorisée
- ⚠️ Si vous distribuez une version modifiée, le code source doit rester ouvert (GPL)

**Pas de restrictions** sur les visites virtuelles créées (elles vous appartiennent).

### Comment contribuer au projet ?

**Plusieurs façons** :

1. **Signaler des bugs** :
   - [Créer une issue](https://github.com/llang57/editeurPanovisu/issues/new)

2. **Proposer des fonctionnalités** :
   - [Discussions](https://github.com/llang57/editeurPanovisu/discussions)

3. **Contribuer au code** :
   - Fork → Branche → Développement → Pull Request
   - Voir [Documentation technique](Documentation-technique.md)

4. **Améliorer la documentation** :
   - Corriger les fautes
   - Ajouter des exemples
   - Traduire

5. **Partager vos créations** :
   - Poster dans [Discussions](https://github.com/llang57/editeurPanovisu/discussions)

### Où obtenir de l'aide ?

**Ressources** :

- 📖 **Wiki** : [github.com/llang57/editeurPanovisu/wiki](https://github.com/llang57/editeurPanovisu/wiki)
- 💬 **Discussions** : [github.com/llang57/editeurPanovisu/discussions](https://github.com/llang57/editeurPanovisu/discussions)
- 🐛 **Issues** : [github.com/llang57/editeurPanovisu/issues](https://github.com/llang57/editeurPanovisu/issues)
- 🌐 **Site web** : [lemondea360.fr/panovisu](https://lemondea360.fr/panovisu)

**Avant de poser une question** :
1. Consultez cette FAQ
2. Recherchez dans les issues existantes
3. Vérifiez les discussions

## Questions non résolues ?

Si votre question n'est pas dans cette FAQ :

1. **Recherchez** dans les [Discussions](https://github.com/llang57/editeurPanovisu/discussions)
2. **Créez** une nouvelle discussion
3. **Décrivez** votre problème avec :
   - Version de PanoVisu
   - Système d'exploitation
   - Étapes pour reproduire
   - Captures d'écran si pertinent

L'équipe et la communauté vous répondront rapidement ! 🚀
