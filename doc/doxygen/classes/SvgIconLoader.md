# ⚠️ SvgIconLoader

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\util\SvgIconLoader.java`

**Documentation de la classe :** ✅ Oui (13 lignes)

**Progression :** 11/25 éléments documentés (44.0%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 6 |
| 🟡 Partielle | 2 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 10 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 4 |

## Propriétés (4)

### ⚫ `SVG_PATH` - Ligne 33

**Qualité :** Absente

**Déclaration :**
```java
private static final String SVG_PATH = "images/svg/";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `baseAppPath` - Ligne 34

**Qualité :** Absente

**Déclaration :**
```java
private static String baseAppPath = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `svgPath` - Ligne 131

**Qualité :** Absente

**Déclaration :**
```java
String svgPath = baseAppPath + File.separator + SVG_PATH + iconName + ".svg";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `pngPath` - Ligne 239

**Qualité :** Absente

**Déclaration :**
```java
String pngPath = "file:" + baseAppPath + File.separator + "images" + File.sep...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (21)

### 🟡 `setBaseAppPath()` - Ligne 43

**Qualité :** Partielle

**Signature :**
```java
public static void setBaseAppPath(String path) {
```

**Documentation actuelle :**
```java
/**
* Définit le chemin de base de l'application
* @param path Chemin absolu vers le répertoire de l'application
*/
```

**Tags présents :** @param

---

### 🟠 `clearCache()` - Ligne 50

**Qualité :** Minimale

**Signature :**
```java
public static void clearCache() {
```

**Documentation actuelle :**
```java
/**
* Vide le cache des icônes (utile lors d'un changement de thème)
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `getThemeColor()` - Ligne 60

**Qualité :** Partielle

**Signature :**
```java
private static Color getThemeColor() {
```

**Documentation actuelle :**
```java
/**
* Détecte la couleur appropriée selon le thème actif (clair ou foncé)
* Fonctionne avec tous les thèmes : AtlantaFX, MaterialFX, FlatLaf et thèmes personnalisés
* Couleurs spéciales pour Dracula et Darcula (mauve caractéristique)
* @return Couleur adaptée au thème
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟢 `loadSvgIcon()` - Ligne 116

**Qualité :** Complète

**Signature :**
```java
public static ImageView loadSvgIcon(String iconName, int width, int height, C...
```

**Documentation actuelle :**
```java
/**
* Charge une icône SVG et la convertit en ImageView avec dimensions personnalisées
* @param iconName Nom du fichier SVG (sans extension)
* @param width Largeur en pixels
* @param height Hauteur en pixels
* @param color Couleur de l'icône (optionnel, null pour détecter automatiquement selon le thème)
* @return ImageView contenant l'icône
*/
```

**Tags présents :** @param, @return

---

### ⚫ `ImageView()` - Ligne 127

**Qualité :** Absente

**Signature :**
```java
return new ImageView(iconCache.get(cacheKey));
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `loadFallbackPng()` - Ligne 136

**Qualité :** Absente

**Signature :**
```java
return loadFallbackPng(iconName, width);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `ImageView()` - Ligne 161

**Qualité :** Absente

**Signature :**
```java
return new ImageView(fxImage);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `loadFallbackPng()` - Ligne 166

**Qualité :** Absente

**Signature :**
```java
return loadFallbackPng(iconName, width);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadSvgIcon()` - Ligne 177

**Qualité :** Complète

**Signature :**
```java
public static ImageView loadSvgIcon(String iconName, int size, Color color) {
```

**Documentation actuelle :**
```java
/**
* Charge une icône SVG et la convertit en ImageView (version carrée)
* @param iconName Nom du fichier SVG (sans extension)
* @param size Taille en pixels (carré)
* @param color Couleur de l'icône (optionnel, null pour détecter automatiquement selon le thème)
* @return ImageView contenant l'icône
*/
```

**Tags présents :** @param, @return

---

### ⚫ `loadSvgIcon()` - Ligne 178

**Qualité :** Absente

**Signature :**
```java
return loadSvgIcon(iconName, size, size, color);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `convertSvgToPng()` - Ligne 188

**Qualité :** Complète

**Signature :**
```java
private static BufferedImage convertSvgToPng(String svgContent, int width, in...
```

**Documentation actuelle :**
```java
/**
* Convertit un SVG en BufferedImage PNG avec Batik (dimensions personnalisées)
* @param svgContent Contenu du fichier SVG
* @param width Largeur de sortie en pixels
* @param height Hauteur de sortie en pixels
* @return BufferedImage du PNG
*/
```

**Tags présents :** @param, @return

---

### 🟢 `convertSvgToPng()` - Ligne 218

**Qualité :** Complète

**Signature :**
```java
private static BufferedImage convertSvgToPng(String svgContent, int size) thr...
```

**Documentation actuelle :**
```java
/**
* Convertit un SVG en BufferedImage PNG avec Batik (version carrée)
* @param svgContent Contenu du fichier SVG
* @param size Taille de sortie en pixels (carré)
* @return BufferedImage du PNG
*/
```

**Tags présents :** @param, @return

---

### ⚫ `convertSvgToPng()` - Ligne 219

**Qualité :** Absente

**Signature :**
```java
return convertSvgToPng(svgContent, size, size);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadSvgIcon()` - Ligne 228

**Qualité :** Complète

**Signature :**
```java
public static ImageView loadSvgIcon(String iconName, int size) {
```

**Documentation actuelle :**
```java
/**
* Charge une icône SVG avec la couleur par défaut
* @param iconName Nom du fichier SVG
* @param size Taille en pixels
* @return ImageView contenant l'icône
*/
```

**Tags présents :** @param, @return

---

### ⚫ `loadSvgIcon()` - Ligne 229

**Qualité :** Absente

**Signature :**
```java
return loadSvgIcon(iconName, size, null);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `loadFallbackPng()` - Ligne 235

**Qualité :** Minimale

**Signature :**
```java
private static ImageView loadFallbackPng(String iconName, int size) {
```

**Documentation actuelle :**
```java
/**
* Fallback: charge l'icône PNG si le SVG n'est pas disponible
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `ImageView()` - Ligne 241

**Qualité :** Absente

**Signature :**
```java
return new ImageView(image);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `ImageView()` - Ligne 245

**Qualité :** Absente

**Signature :**
```java
return new ImageView();
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `mapSvgToPng()` - Ligne 252

**Qualité :** Minimale

**Signature :**
```java
private static String mapSvgToPng(String svgName) {
```

**Documentation actuelle :**
```java
/**
* Mappe les noms de fichiers SVG vers les noms PNG existants
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟢 `loadThemedIcon()` - Ligne 287

**Qualité :** Complète

**Signature :**
```java
public static ImageView loadThemedIcon(String iconName, int size, boolean isD...
```

**Documentation actuelle :**
```java
/**
* Crée un ImageView avec couleur adaptée au thème
* @param iconName Nom de l'icône
* @param size Taille
* @param isDarkTheme True si thème sombre
* @return ImageView colorisée
*/
```

**Tags présents :** @param, @return

---

### ⚫ `loadSvgIcon()` - Ligne 289

**Qualité :** Absente

**Signature :**
```java
return loadSvgIcon(iconName, size, iconColor);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

