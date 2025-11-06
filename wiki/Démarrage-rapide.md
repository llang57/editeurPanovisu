# 🎯 Démarrage rapide

Créez votre première visite virtuelle en 10 minutes !

## Table des matières

- [Avant de commencer](#avant-de-commencer)
- [Étape 1 : Préparer vos images](#étape-1--préparer-vos-images)
- [Étape 2 : Créer un nouveau projet](#étape-2--créer-un-nouveau-projet)
- [Étape 3 : Importer vos panoramiques](#étape-3--importer-vos-panoramiques)
- [Étape 4 : Ajouter de l'interactivité](#étape-4--ajouter-de-linteractivité)
- [Étape 5 : Personnaliser l'interface](#étape-5--personnaliser-linterface)
- [Étape 6 : Exporter et publier](#étape-6--exporter-et-publier)
- [Prochaines étapes](#prochaines-étapes)

## Avant de commencer

Assurez-vous d'avoir :
- ✅ [Téléchargé et installé PanoVisu](https://github.com/llang57/editeurPanovisu/releases) ou [compilé depuis les sources](Installation)
- ✅ Préparé quelques images panoramiques 360°
- ✅ 10 minutes de temps disponible

## Étape 1 : Préparer vos images

### Formats supportés

PanoVisu accepte deux types de panoramiques :

#### 1. Panoramiques équirectangulaires (recommandé)
- **Format** : Image unique avec ratio 2:1
- **Résolution conseillée** : 8192×4096 pixels (haute qualité)
- **Minimum acceptable** : 4096×2048 pixels
- **Formats de fichier** : JPG, PNG

#### 2. Panoramiques cubiques (cubemap)
- **Format** : 6 images séparées (faces du cube)
- **Résolution** : Chaque face doit avoir la même taille (ex: 2048×2048)
- **Noms des faces** : `f.jpg`, `r.jpg`, `b.jpg`, `l.jpg`, `u.jpg`, `d.jpg`
  - f = front (devant)
  - r = right (droite)
  - b = back (derrière)
  - l = left (gauche)
  - u = up (haut)
  - d = down (bas)

### Où obtenir des panoramiques ?

**Option 1 : Créer les vôtres**
- Appareil photo avec mode panoramique
- Application smartphone (Google Street View, etc.)
- Assemblage avec [Hugin](http://hugin.sourceforge.net/) (gratuit)

**Option 2 : Télécharger des exemples**
- [Flickr - Équirectangulaires](https://www.flickr.com/groups/equirectangular/)
- [Wikimedia Commons](https://commons.wikimedia.org/wiki/Category:360%C2%B0_panoramas)

### Optimisation des images

Pour de meilleures performances :

```bash
# Réduire la taille sans perte de qualité visible
# Avec ImageMagick (gratuit)
magick convert panorama.jpg -quality 85 -resize 8192x4096 panorama_optimized.jpg
```

**Règles d'or** :
- 📏 **Résolution** : Ne pas dépasser 8192×4096 (limite WebGL)
- 💾 **Poids** : Viser 2-5 Mo par panoramique
- 🎨 **Format** : JPG avec qualité 80-85%

## Étape 2 : Créer un nouveau projet

### Lancement de l'éditeur

```bash
mvn javafx:run
```

ou double-cliquez sur `Lancer_EditeurPanovisu.bat` (Windows)

### Création du projet

1. **Menu** : `Fichier` → `Nouveau projet`

2. **Configuration initiale** :
   - **Nom du projet** : Entrez un nom descriptif (ex: "Visite Maison")
   - **Dossier de destination** : Choisissez où sauvegarder le projet
   - **Type de projet** :
     - ✅ **Visite virtuelle** (plusieurs panoramiques reliés)
     - ⭕ **Panoramique unique** (un seul panorama)

3. Cliquez sur **Créer**

### Structure du projet créé

```
MonProjet/
├── MonProjet.xml          # Configuration de la visite
├── panos/                 # Vos panoramiques
├── images/                # Images auxiliaires (hotspots, etc.)
├── css/                   # Styles personnalisés (optionnel)
└── xml/                   # Fichiers de configuration
```

## Étape 3 : Importer vos panoramiques

### Méthode 1 : Drag & Drop (recommandé)

1. Ouvrez votre explorateur de fichiers
2. Sélectionnez vos images panoramiques
3. **Glissez-déposez** les images dans la zone de l'éditeur PanoVisu

### Méthode 2 : Menu Import

1. **Menu** : `Fichier` → `Importer des panoramiques`
2. Sélectionnez vos images
3. Cliquez sur **Ouvrir**

### Configuration de chaque panoramique

Pour chaque panoramique ajouté, configurez :

#### Paramètres de base
- **Nom** : Nom affiché dans l'interface (ex: "Salon")
- **Description** : Texte descriptif (optionnel)
- **Type** : Équirectangulaire ou Cubique

#### Paramètres d'affichage
- **Panoramique de départ** : ✅ Cochez pour le panoramique initial
- **Champ de vision initial** : 70° (par défaut)
- **Angle de vue initial** :
  - **Pan** : Rotation horizontale (0° = face à vous)
  - **Tilt** : Rotation verticale (0° = horizon)

#### Limites de rotation (optionnel)
- **Min/Max Horizontal** : Limiter la rotation gauche/droite
- **Min/Max Vertical** : Limiter la rotation haut/bas

💡 **Astuce** : Laissez les limites vides pour une rotation complète 360°.

## Étape 4 : Ajouter de l'interactivité

### Hotspots de navigation

Les hotspots permettent de naviguer entre les panoramiques.

1. **Sélectionnez** un panoramique dans la liste
2. **Cliquez** sur l'icône **Hotspot de navigation** 🎯
3. **Placez** le hotspot dans le panoramique (là où l'utilisateur cliquera)
4. **Configurez** :
   - **Destination** : Choisissez le panoramique cible
   - **Info-bulle** : Texte affiché au survol (ex: "Aller dans la cuisine")
   - **Forme** : Cercle, carré, flèche
   - **Taille** : Ajustez la taille du hotspot

5. **Aperçu** : Cliquez sur 👁️ pour tester

### Hotspots d'images

Affichez des photos en popup.

1. **Icône** : Hotspot d'image 🖼️
2. **Placez** dans le panoramique
3. **Configurez** :
   - **Image** : Sélectionnez la photo à afficher
   - **Titre** : Titre de la popup
   - **Description** : Texte explicatif

### Hotspots HTML

Affichez du contenu riche (texte formaté, vidéos, liens).

1. **Icône** : Hotspot HTML 📝
2. **Placez** dans le panoramique
3. **Éditeur WYSIWYG** :
   - Rédigez votre contenu
   - Formatage : gras, italique, listes, etc.
   - Insérez des liens, vidéos YouTube, etc.

Exemple de code HTML :
```html
<h2>Bienvenue dans le salon</h2>
<p>Cette pièce de 30m² offre une vue panoramique sur le jardin.</p>
<iframe width="560" height="315" 
  src="https://www.youtube.com/embed/VIDEO_ID">
</iframe>
```

### Raccourcis clavier utiles

| Touche | Action |
|--------|--------|
| **Ctrl + N** | Nouveau hotspot de navigation |
| **Ctrl + I** | Nouveau hotspot d'image |
| **Ctrl + H** | Nouveau hotspot HTML |
| **Suppr** | Supprimer le hotspot sélectionné |
| **Ctrl + D** | Dupliquer le hotspot sélectionné |
| **Flèches** | Déplacer précisément le hotspot |

## Étape 5 : Personnaliser l'interface

### Barre de navigation

1. **Menu** : `Outils` → `Personnaliser la barre de navigation`
2. **Options** :
   - **Position** : Haut, bas, gauche, droite
   - **Couleur de fond** : Code hexa (ex: `#3498db`)
   - **Transparence** : 0 (opaque) à 100 (transparent)
   - **Boutons visibles** :
     - ✅ Plein écran
     - ✅ Rotation automatique
     - ✅ Plan/Carte
     - ✅ Informations

### Titre et description

1. **Menu** : `Projet` → `Propriétés`
2. **Remplissez** :
   - **Titre principal** : Nom de votre visite
   - **Sous-titre** : Description courte
   - **Auteur** : Votre nom
   - **Copyright** : Texte de copyright

### Image de chargement (splash screen)

1. **Menu** : `Projet` → `Écran de chargement`
2. **Sélectionnez** une image (logo, photo d'intro)
3. **Configurez** :
   - **Durée minimale** : 2-3 secondes
   - **Effet de transition** : Fondu, glissement, etc.

### Musique d'ambiance (optionnel)

1. **Menu** : `Projet` → `Audio`
2. **Ajoutez** un fichier MP3
3. **Options** :
   - ✅ Lecture automatique
   - ✅ Boucle
   - Volume : 50%

⚠️ **Note** : Les navigateurs modernes bloquent la lecture automatique du son. L'utilisateur devra cliquer pour démarrer la musique.

## Étape 6 : Exporter et publier

### Export de la visite

1. **Menu** : `Fichier` → `Générer la visite virtuelle`

2. **Configuration de l'export** :
   - **Dossier de destination** : Où créer le dossier de la visite
   - **Nom du dossier** : Ex: `VisiteMaison`
   - **Options** :
     - ✅ **Copier les images** : Inclure tous les panoramiques
     - ✅ **Minifier le JavaScript** : Réduire la taille des fichiers
     - ✅ **Générer le fichier XML** : Configuration de la visite

3. **Cliquez** sur **Générer**

### Structure de la visite générée

```
VisiteMaison/
├── index.html             # Point d'entrée (à ouvrir)
├── visite.xml             # Configuration
├── panos/                 # Panoramiques
├── images/                # Hotspots, logos
├── panovisu/              # Moteur de visualisation
│   ├── panovisu.js
│   ├── libs/              # Three.js, etc.
│   └── css/
└── css/                   # Styles personnalisés
```

### Test en local

**Avec le serveur HTTP intégré** (v3.1+) :

1. Dans l'éditeur, **cliquez** sur 🌐 **Ouvrir la visite**
2. Le serveur démarre automatiquement
3. Votre navigateur s'ouvre sur `http://localhost:8080`

**Alternative manuelle** :

```bash
# Python 3
cd VisiteMaison
python -m http.server 8000

# Puis ouvrez : http://localhost:8000
```

⚠️ **Important** : N'ouvrez PAS `index.html` directement (double-clic). Le protocole `file://` cause des erreurs CORS.

### Publication sur le web

#### Option 1 : Hébergement classique (FTP)

1. **Connectez-vous** à votre hébergeur web via FTP (FileZilla, etc.)
2. **Uploadez** tout le contenu du dossier `VisiteMaison/`
3. **Accédez** à `https://votresite.com/VisiteMaison/`

#### Option 2 : GitHub Pages (gratuit)

1. **Créez** un dépôt GitHub public
2. **Uploadez** votre visite
3. **Activez** GitHub Pages dans Settings
4. **URL** : `https://votreusername.github.io/nom-depot/`

#### Option 3 : Netlify / Vercel (gratuit)

1. **Créez** un compte sur [Netlify](https://netlify.com) ou [Vercel](https://vercel.com)
2. **Drag & drop** votre dossier de visite
3. **URL automatique** : `https://votre-visite.netlify.app`

### Optimisation pour le web

**Compression des images** :
```bash
# Avec ImageMagick
for file in panos/*.jpg; do
  magick convert "$file" -quality 85 "$file"
done
```

**Activation de la compression Gzip** (`.htaccess` pour Apache) :
```apache
<IfModule mod_deflate.c>
  AddOutputFilterByType DEFLATE text/html text/css text/javascript application/javascript application/json application/xml
</IfModule>
```

## Prochaines étapes

✅ **Visite créée et publiée ?** Bravo ! Explorez maintenant :

- 📖 **[Guide utilisateur](Guide-utilisateur)** - Fonctionnalités avancées
- 🗺️ **[Ajouter un plan interactif](Guide-utilisateur#plan-interactif)**
- 🌍 **[Intégrer une carte géolocalisée](Guide-utilisateur#cartographie)**
- 🎨 **[Personnaliser les thèmes](Guide-utilisateur#thèmes-visuels)**
- ⚙️ **[Configuration avancée](Configuration-avancée)**

## Exemples et inspiration

Consultez des [exemples de visites](https://lemondea360.fr/visites) réalisées avec PanoVisu.

## Besoin d'aide ?

- 💬 [Discussions GitHub](https://github.com/llang57/editeurPanovisu/discussions)
- 🐛 [Signaler un problème](https://github.com/llang57/editeurPanovisu/issues)
- ❓ [FAQ](FAQ)
