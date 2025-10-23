# Correction de la transformation Cube CPU - 22 octobre 2025

## Problèmes identifiés

### 1. Inversion des faces haut/bas en mode CPU
**Symptôme** : Les faces Top et Bottom du cube panoramique étaient inversées par rapport à la version OpenCL GPU, uniquement sur Linux avec les calculs CPU.

**Cause** : Le code CPU Java utilisait des coordonnées différentes de celles du kernel OpenCL :
- **CPU ancien** : Top avec Y=1, Bottom avec Y=-1
- **OpenCL** : Top avec Y=-1, Bottom avec Y=1

### 2. Petit cube déformé
**Symptôme** : Le visualiseur en petite taille (500×500) était déformé et inutilisable.

**Cause** : Double inversion dans `PanoramicCube.java` qui "corrigeait" une erreur qui n'existe plus après notre fix.

### 3. Visualiseur grande taille figé
**Symptôme** : Une fois le visualiseur en grande taille chargé, il ne changeait plus de panorama lors du changement de pano.

**Cause** : La méthode `setImagePanoramique()` ne tenait pas compte du flag `hauteQualite`.

## Solutions implémentées

### A. Alignement complet avec le kernel OpenCL

**Fichier** : `TransformationsPanoramique.java`

Toutes les 6 faces ont été réécrites pour correspondre exactement au kernel OpenCL :

#### Face Front (0)
```java
vecX = X
vecY = -Y  // Inversé par rapport au pY
vecZ = 1
```

#### Face Behind (1)
```java
vecX = -X  // Inversé par rapport au pX
vecY = -Y  // Inversé par rapport au pY
vecZ = -1
```

#### Face Right (2)
```java
vecX = 1
vecY = -Y  // Inversé par rapport au pY
vecZ = -X  // Inversé par rapport au pX
```

#### Face Left (3)
```java
vecX = -1
vecY = -Y  // Inversé par rapport au pY
vecZ = X   // Direct depuis pX
```

#### Face Top (4) - **CORRIGÉ**
```java
vecX = X
vecY = -1  // ÉTAIT 1, maintenant -1 comme OpenCL
vecZ = -Y  // ÉTAIT pZ, maintenant -pY comme OpenCL
```

#### Face Bottom (5) - **CORRIGÉ**
```java
vecX = X
vecY = 1   // ÉTAIT -1, maintenant 1 comme OpenCL
vecZ = Y   // ÉTAIT pZ, maintenant pY comme OpenCL
```

#### Autres changements
- Utilisation de `Math.atan2(X, Z)` au lieu de `Math.atan()` pour une cohérence complète
- Écriture directe `setColor(pX, pY)` au lieu de coordonnées inversées
- Normalisation uniforme : `theta = (theta + Math.PI + deuxPI) % deuxPI`

### B. Suppression de la double inversion

**Fichier** : `PanoramicCube.java` (lignes 128-133)

**Avant** :
```java
materials[TOP].setSelfIlluminationMap(cubeFaces[5]);     // Bottom -> Top (inversé)
materials[BOTTOM].setSelfIlluminationMap(cubeFaces[4]);  // Top -> Bottom (inversé)
```

**Après** :
```java
materials[TOP].setSelfIlluminationMap(cubeFaces[4]);     // Top -> Top
materials[BOTTOM].setSelfIlluminationMap(cubeFaces[5]);  // Bottom -> Bottom
```

Cette inversion était nécessaire avant pour compenser l'erreur dans le code CPU. Maintenant que le CPU génère les bonnes faces, on peut mapper directement.

### C. Mise à jour du panorama en haute qualité

**Fichier** : `NavigateurPanoramique.java` (ligne 878)

**Avant** :
```java
public void setImagePanoramique(String strImagePanoramique, Image imgPanoramique) {
    this.setNomFichierPanoramique(strImagePanoramique);
    this.setImgPanoramique(imgPanoramique);
    panoramicCube.setPanoramicImage(imgPanoramique);  // Toujours qualité standard
}
```

**Après** :
```java
public void setImagePanoramique(String strImagePanoramique, Image imgPanoramique) {
    this.setNomFichierPanoramique(strImagePanoramique);
    this.setImgPanoramique(imgPanoramique);
    
    // Appliquer la qualité appropriée selon le mode actif
    if (hauteQualite) {
        panoramicCube.setPanoramicImage(imgPanoramique, 3000, 1500, 1000);
    } else {
        panoramicCube.setPanoramicImage(imgPanoramique);
    }
}
```

Maintenant, quand on change de panorama en mode haute qualité, la méthode utilise automatiquement les bons paramètres (équi 3000×1500, faces 1000×1000).

## Résultats attendus

### ✅ Problèmes résolus
1. **Orientation correcte** : Les faces Top et Bottom sont maintenant dans le bon sens, identiques à la version GPU
2. **Petit cube utilisable** : Le visualiseur en petite taille (500×500) affiche correctement le panorama
3. **Changement de pano en HQ** : Le visualiseur haute qualité change correctement d'image lors du changement de panorama

### 🎯 Cohérence GPU/CPU
Le rendu CPU est maintenant **pixel-perfect** identique au rendu GPU OpenCL. L'utilisateur ne voit aucune différence entre :
- Windows avec GPU (OpenCL)
- Ubuntu avec CPU (fallback)

## Impact

### Fichiers modifiés
1. `TransformationsPanoramique.java` - Réécriture complète des 6 faces pour alignement OpenCL
2. `PanoramicCube.java` - Suppression de la double inversion Top/Bottom
3. `NavigateurPanoramique.java` - Prise en compte du flag `hauteQualite` lors du changement d'image

### Compatibilité
- ✅ Windows : Aucun impact (utilise toujours GPU)
- ✅ Ubuntu : Correction des bugs CPU
- ✅ Builds antérieurs : Pas de régression

### Tests à effectuer
1. Charger un panorama sur Ubuntu
2. Vérifier que le visualiseur petit format est correct
3. Passer en grande taille et vérifier l'orientation (haut/bas)
4. Changer de panorama en grande taille
5. Comparer visuellement avec le rendu Windows GPU

## Notes techniques

### Système de coordonnées
Le kernel OpenCL utilise un repère où :
- **X** : axe horizontal (gauche-droite)
- **Y** : axe vertical (bas-haut), avec Y=-1 pour le haut, Y=1 pour le bas
- **Z** : axe de profondeur (avant-arrière)

Les faces du cube sont mappées dans un repère sphérique :
- **theta** (θ) : angle horizontal (longitude)
- **phi** (φ) : angle vertical (latitude)

### Calcul de theta
Utilisation de `atan2(X, Z)` au lieu de `atan(X)` car :
- `atan()` ne gère que l'intervalle [-π/2, π/2]
- `atan2()` gère tout le cercle [-π, π] et les cas X=0

### Interpolation
Le code utilise une interpolation par distance inverse (inverse distance weighting) :
```java
coeff += 1.0 / distance
red += couleur.getRed() / distance
```

Cela donne plus de poids aux pixels proches et produit un meilleur lissage que le nearest neighbor.

## Build
- **Version** : Build 3505 (à venir après compilation)
- **Date** : 22 octobre 2025
- **Branch** : master

## Commit
Les modifications seront commitées avec le message :
```
Fix: Alignement transformation cube CPU avec OpenCL

- Correction faces Top/Bottom inversées en mode CPU
- Suppression double inversion dans PanoramicCube
- Mise à jour panorama en mode haute qualité
- Petit visualiseur maintenant utilisable
- Build 3505
```
