# ⚠️ PanoramicCube

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\PanoramicCube.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 4/18 éléments documentés (22.2%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 1 |
| 🟡 Partielle | 2 |
| 🟠 Minimale | 1 |
| ⚫ Absente | 1 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 13 |

## Propriétés (13)

### ⚫ `CUBE_SIZE` - Ligne 22

**Qualité :** Absente

**Déclaration :**
```java
private static final double CUBE_SIZE = 400;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `faces` - Ligne 23

**Qualité :** Absente

**Déclaration :**
```java
private final Box[] faces = new Box[6];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `materials` - Ligne 24

**Qualité :** Absente

**Déclaration :**
```java
private final PhongMaterial[] materials = new PhongMaterial[6];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `FRONT` - Ligne 27

**Qualité :** Absente

**Déclaration :**
```java
private static final int FRONT = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `BACK` - Ligne 28

**Qualité :** Absente

**Déclaration :**
```java
private static final int BACK = 1;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `LEFT` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
private static final int LEFT = 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `RIGHT` - Ligne 30

**Qualité :** Absente

**Déclaration :**
```java
private static final int RIGHT = 3;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `TOP` - Ligne 31

**Qualité :** Absente

**Déclaration :**
```java
private static final int TOP = 4;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `BOTTOM` - Ligne 32

**Qualité :** Absente

**Déclaration :**
```java
private static final int BOTTOM = 5;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `halfSize` - Ligne 45

**Qualité :** Absente

**Déclaration :**
```java
double halfSize = CUBE_SIZE / 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `source` - Ligne 143

**Qualité :** Absente

**Déclaration :**
```java
return source;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `resized` - Ligne 169

**Qualité :** Absente

**Déclaration :**
```java
return resized;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `CUBE_SIZE` - Ligne 177

**Qualité :** Absente

**Déclaration :**
```java
return CUBE_SIZE;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (5)

### ⚫ `PanoramicCube()` - Ligne 34

**Qualité :** Absente

**Signature :**
```java
public PanoramicCube() {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `createCubeFaces()` - Ligne 44

**Qualité :** Minimale

**Signature :**
```java
private void createCubeFaces() {
```

**Documentation actuelle :**
```java
/**
* Crée les 6 faces du cube
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `setPanoramicImage()` - Ligne 90

**Qualité :** Partielle

**Signature :**
```java
public void setPanoramicImage(Image panoramicImage) {
```

**Documentation actuelle :**
```java
/**
* Applique une image panoramique équirectangulaire sur le cube
* Utilise TransformationsPanoramique pour découper l'image en 6 faces
*
* @param panoramicImage L'image panoramique équirectangulaire
*/
```

**Tags présents :** @param

---

### 🟢 `resizeToEquirectangular()` - Ligne 137

**Qualité :** Complète

**Signature :**
```java
private Image resizeToEquirectangular(Image source, int targetWidth, int targ...
```

**Documentation actuelle :**
```java
/**
* Redimensionne une image au format équirectangulaire avec ratio 2:1
*
* @param source Image source
* @param targetWidth Largeur cible
* @param targetHeight Hauteur cible (devrait être targetWidth/2 pour ratio 2:1)
* @return Image redimensionnée au ratio 2:1
*/
```

**Tags présents :** @param, @return

---

### 🟡 `getCubeSize()` - Ligne 176

**Qualité :** Partielle

**Signature :**
```java
public double getCubeSize() {
```

**Documentation actuelle :**
```java
/**
* Obtient la taille du cube
* @return La taille du cube
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

