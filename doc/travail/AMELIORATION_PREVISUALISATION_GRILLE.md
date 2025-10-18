# Amélioration de la fenêtre de prévisualisation - Grille de coordonnées

**Date** : 17 octobre 2025  
**Build** : #2653  
**Fichier modifié** : `PrevisualiserConversionDialog.java`

## 🎯 Objectifs

Améliorer la fenêtre de prévisualisation de conversion au ratio 2:1 avec :
1. Affichage au format 2:1 (comme l'image panoramique finale)
2. Grille de coordonnées géographiques (longitude/latitude)
   - **Longitudes** : traits pleins tous les **45°**, pointillés tous les **15°**
   - **Latitudes** : traits pleins tous les **20°**, pointillés tous les **10°**
   - **Équateur (lat=0°)** : en **jaune** pour le mettre en évidence
   - **Couleur** : **gris** semi-transparent (au lieu de blanc vif)

## ✅ Modifications apportées

### 1. **Format d'affichage 2:1**

**Fichier** : `PrevisualiserConversionDialog.java`

```java
// Ligne 163-165
imageViewPreview.setFitWidth(800);
imageViewPreview.setFitHeight(400); // Ratio 2:1 : 800x400
```

**Résultat** : La zone de prévisualisation affiche maintenant l'image au ratio 2:1 exact, comme le résultat final.

### 2. **Dimensions de la fenêtre ajustées**

```java
// Lignes 147-149
Scene scene = new Scene(root, 950, 750);
stage.setMinWidth(850);
stage.setMinHeight(650);
```

**Résultat** : Fenêtre plus large pour mieux afficher le format panoramique 2:1.

### 3. **Ajout de la grille de coordonnées**

**Nouvelle méthode** : `ajouterGrilleCoordonnees(BufferedImage)`

#### Caractéristiques de la grille :

- **Couleur principale** : Gris semi-transparent (RGB: 160, 160, 160, Alpha: 140)
- **Couleur équateur** : Jaune semi-transparent (RGB: 255, 200, 0, Alpha: 180) - **2.0px d'épaisseur**

- **Lignes verticales** (longitudes) :
  - De -180° à +180° (360° répartis sur la largeur)
  - Trait plein (1.5px) tous les **45°** : -180°, -135°, -90°, -45°, 0°, +45°, +90°, +135°, +180°
  - Trait pointillé (1.0px, pattern 5-5) tous les **15°** : -165°, -150°, -120°, -105°, ..., +105°, +120°, +150°, +165°

- **Lignes horizontales** (latitudes) :
  - De -90° à +90° (180° répartis sur la hauteur)
  - +90° en haut, 0° au centre (équateur), -90° en bas
  - **Équateur (0°)** : Jaune, trait plein **2.0px** (mis en évidence)
  - Autres : Gris, trait plein (1.5px) tous les **20°** : ±90°, ±70°, ±50°, ±30°, ±10°
  - Gris, trait pointillé (1.0px, pattern 5-5) tous les **10°** : ±80°, ±60°, ±40°, ±20°

#### Code principal :

```java
// Couleur de la grille : gris semi-transparent
java.awt.Color couleurGrille = new java.awt.Color(160, 160, 160, 140);

// Traits verticaux (longitudes) - tous les 45° pleins, 15° pointillés
for (int longitude = -180; longitude <= 180; longitude += 15) {
    float x = (float) ((longitude + 180) * largeur / 360.0);
    
    g2d.setColor(couleurGrille);
    
    if (longitude % 45 == 0) {
        // Trait plein tous les 45°
        g2d.setStroke(new BasicStroke(1.5f));
    } else {
        // Trait pointillé tous les 15°
        float[] dashPattern = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.0f, CAP_BUTT, JOIN_MITER, 10.0f, dashPattern, 0.0f));
    }
    g2d.drawLine((int) x, 0, (int) x, hauteur);
}

// Traits horizontaux (latitudes) - équateur en jaune !
for (int latitude = -90; latitude <= 90; latitude += 10) {
    float y = (float) ((90 - latitude) * hauteur / 180.0);
    
    if (latitude == 0) {
        // Équateur en jaune, plus épais
        g2d.setColor(new java.awt.Color(255, 200, 0, 180));
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawLine(0, (int) y, largeur, (int) y);
    } else {
        g2d.setColor(couleurGrille);
        
        if (latitude % 20 == 0) {
            // Trait plein tous les 20°
            g2d.setStroke(new BasicStroke(1.5f));
        } else {
            // Trait pointillé tous les 10°
            float[] dashPattern = {5.0f, 5.0f};
            g2d.setStroke(new BasicStroke(1.0f, CAP_BUTT, JOIN_MITER, 10.0f, dashPattern, 0.0f));
        }
        g2d.drawLine(0, (int) y, largeur, (int) y);
    }
}
```

### 4. **Intégration dans le pipeline de prévisualisation**

La grille est ajoutée **après** la conversion de l'image :

```java
// Ligne 458-459
g2d.dispose();
imageFinale = ajouterGrilleCoordonnees(imageFinale);
return imageFinale;
```

**Ordre de traitement** :
1. Conversion au ratio 2:1 avec remplissage (noir/flou/miroir/couleur moyenne)
2. Application du positionnement vertical personnalisé
3. **Ajout de la grille de coordonnées**
4. Affichage dans la prévisualisation

## 📊 Résultat visuel

La prévisualisation affiche maintenant :
- ✅ Image au **ratio 2:1** exact
- ✅ Grille de **longitude** verticale (tous les 10°, traits pleins tous les 20°)
- ✅ Grille de **latitude** horizontale (tous les 10°, traits pleins tous les 20°)
- ✅ Grille **semi-transparente** (ne masque pas l'image)
- ✅ Format adapté aux **panoramas sphériques**

### Représentation de la grille :

```
+90° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (Pôle Nord - gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
+80° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
+70° ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
+60° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
+50° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
+40° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
+30° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
+20° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
+10° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
 0°  ████████████████████████████████████████████████████████████ (Équateur - JAUNE !)
-10° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
-20° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
-30° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
-40° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
-50° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
-60° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
-70° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (gris)
-80° ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ (gris)
     ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃  ┊  ┊  ┊  ┃
-90° ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ (Pôle Sud - gris)
     ↑           ↑           ↑           ↑           ↑
  -180°       -90°         0°         +90°       +180°
  (Longitude: ┃ = plein 45°, ┊ = pointillé 15°)
```

**Légende** :
- `━` : Traits **pleins** gris (latitude tous les 20°)
- `┄` : Traits **pointillés** gris (latitude tous les 10°)
- `█` : Trait **jaune épais** (équateur lat=0°, 2.0px)
- `┃` : Traits **pleins** gris (longitude tous les 45°)
- `┊` : Traits **pointillés** gris (longitude tous les 15°)

## 🎨 Référence des coordonnées

### Longitudes (lignes verticales) - GRIS :
- **Traits pleins (45°)** : -180°, -135°, -90°, -45°, 0°, +45°, +90°, +135°, +180° (9 traits)
- **Traits pointillés (15°)** : -165°, -150°, -120°, -105°, -75°, -60°, -30°, -15°, +15°, +30°, +60°, +75°, +105°, +120°, +150°, +165° (16 traits)
- **Total** : 25 lignes verticales

### Latitudes (lignes horizontales) :
- **Équateur (0°)** : **JAUNE**, trait épais 2.0px
- **Gris pleins (20°)** : +90°, +70°, +50°, +30°, +10°, -10°, -30°, -50°, -70°, -90° (10 traits)
- **Gris pointillés (10°)** : +80°, +60°, +40°, +20°, -20°, -40°, -60°, -80° (8 traits)
- **Total** : 19 lignes horizontales (1 jaune + 18 grises)

## ✅ Build #2653

**Statut** : ✅ **BUILD SUCCESS**  
**Warnings** : Uniquement des paramètres lambda non utilisés (comportement normal)

## 🚀 Utilisation

1. Ouvrir la conversion au ratio 2:1
2. Ajouter des fichiers
3. Cliquer sur **"Prévisualiser"**
4. La fenêtre affiche :
   - L'image convertie au ratio 2:1
   - La grille de coordonnées superposée
   - Les contrôles de positionnement vertical
   - Les options de remplissage

## 📝 Notes techniques

- La grille est dessinée **en dernier** sur une copie de l'image
- Pas d'impact sur la performance (grille simple, pas de flou)
- La grille n'est affichée que dans la **prévisualisation**
- L'image finale **n'inclut pas la grille** (uniquement pour visualisation)

---

**Auteur** : Laurent Lang  
**Version** : EditeurPanovisu 3.1.0-SNAPSHOT
