# ⚠️ ImageResizeGPU

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `/home/llang/developpement/java/editeurPanovisu/src/editeurpanovisu/gpu/ImageResizeGPU.java`

**Documentation de la classe :** ✅ Oui (13 lignes)

**Progression :** 9/33 éléments documentés (27.3%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 1 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 7 |
| ⚫ Absente | 5 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 1 |
| ⚫ Absente | 19 |

## Propriétés (20)

### ⚫ `bicubicProgram` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
private static cl_program bicubicProgram = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `lanczosProgram` - Ligne 30

**Qualité :** Absente

**Déclaration :**
```java
private static cl_program lanczosProgram = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bicubicKernel` - Ligne 31

**Qualité :** Absente

**Déclaration :**
```java
private static cl_kernel bicubicKernel = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `lanczosKernel` - Ligne 32

**Qualité :** Absente

**Déclaration :**
```java
private static cl_kernel lanczosKernel = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### 🟠 `GPU_THRESHOLD_MEGAPIXELS` - Ligne 38

**Qualité :** Minimale

**Déclaration :**
```java
private static final int GPU_THRESHOLD_MEGAPIXELS = 2; // 2MP = 1920×1080 env...
```

**Documentation actuelle :**
```java
/**
* Seuil de taille d'image pour utiliser le GPU (en mégapixels)
* En dessous de ce seuil, le CPU est plus rapide à cause de l'overhead GPU
*/
```

**⚠️ Tags manquants :** @see

---

### ⚫ `source` - Ligne 61

**Qualité :** Absente

**Déclaration :**
```java
return source;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `result` - Ligne 89

**Qualité :** Absente

**Déclaration :**
```java
return result;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `result` - Ligne 106

**Qualité :** Absente

**Déclaration :**
```java
return result;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `kernel` - Ligne 150

**Qualité :** Absente

**Déclaration :**
```java
cl_kernel kernel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `globalWorkSize` - Ligne 166

**Qualité :** Absente

**Déclaration :**
```java
long[] globalWorkSize = new long[]{targetWidth, targetHeight};
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `source` - Ligne 226

**Qualité :** Absente

**Déclaration :**
```java
return source;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `logSize` - Ligne 249

**Qualité :** Absente

**Déclaration :**
```java
long[] logSize = new long[1];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `logSize` - Ligne 275

**Qualité :** Absente

**Déclaration :**
```java
long[] logSize = new long[1];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 299

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `localX` - Ligne 303

**Qualité :** Absente

**Déclaration :**
```java
int localX = 16;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `localY` - Ligne 304

**Qualité :** Absente

**Déclaration :**
```java
int localY = 16;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 320

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `blue` - Ligne 345

**Qualité :** Absente

**Déclaration :**
```java
int blue = argb & 0xFF;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `buffer` - Ligne 356

**Qualité :** Absente

**Déclaration :**
```java
return buffer;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `image` - Ligne 381

**Qualité :** Absente

**Déclaration :**
```java
return image;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (13)

### 🟢 `resizeAuto()` - Ligne 50

**Qualité :** Complète

**Signature :**
```java
public static Image resizeAuto(Image source, int targetWidth, int targetHeight,
```

**Documentation actuelle :**
```java
/**
* Redimensionne une image avec routage automatique GPU/CPU
*
* @param source Image source
* @param targetWidth Largeur cible
* @param targetHeight Hauteur cible
* @param method Méthode d'interpolation
* @return Image redimensionnée
* @throws InterruptedException Si l'opération est interrompue
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `IllegalArgumentException()` - Ligne 53

**Qualité :** Absente

**Signature :**
```java
throw new IllegalArgumentException("L'image source ne peut pas être null");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `resizeGPU()` - Ligne 112

**Qualité :** Minimale

**Signature :**
```java
private static Image resizeGPU(Image source, int targetWidth, int targetHeight,
```

**Documentation actuelle :**
```java
/**
* Redimensionnement via GPU OpenCL
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `RuntimeException()` - Ligne 117

**Qualité :** Absente

**Signature :**
```java
throw new RuntimeException("GPU non disponible");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `byteBufferToImage()` - Ligne 202

**Qualité :** Absente

**Signature :**
```java
return byteBufferToImage(dstBuffer, targetWidth, targetHeight);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `resizeCPU()` - Ligne 209

**Qualité :** Minimale

**Signature :**
```java
private static Image resizeCPU(Image source, int targetWidth, int targetHeight,
```

**Documentation actuelle :**
```java
/**
* Redimensionnement via CPU (fallback)
* Utilise la bibliothèque Thumbnails directement pour éviter la récursion
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `initializeKernels()` - Ligne 233

**Qualité :** Minimale

**Signature :**
```java
private static void initializeKernels() throws Exception {
```

**Documentation actuelle :**
```java
/**
* Initialise les kernels OpenCL (compilation à la demande)
*/
```

**⚠️ Tags manquants :** @param

---

### ⚫ `RuntimeException()` - Ligne 255

**Qualité :** Absente

**Signature :**
```java
throw new RuntimeException("Erreur compilation kernel bicubique:\n" + new Str...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `RuntimeException()` - Ligne 281

**Qualité :** Absente

**Signature :**
```java
throw new RuntimeException("Erreur compilation kernel Lanczos:\n" + new Strin...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `calculateOptimalLocalWorkSize()` - Ligne 293

**Qualité :** Minimale

**Signature :**
```java
private static long[] calculateOptimalLocalWorkSize(int width, int height) {
```

**Documentation actuelle :**
```java
/**
* Calcule la taille optimale du local work size pour le GPU
* Pour les très grandes images, retourne null (laisse OpenCL décider)
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `imageToByteBuffer()` - Ligne 330

**Qualité :** Minimale

**Signature :**
```java
private static ByteBuffer imageToByteBuffer(Image image) {
```

**Documentation actuelle :**
```java
/**
* Convertit une Image JavaFX en ByteBuffer pour OpenCL (format RGBA)
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `byteBufferToImage()` - Ligne 362

**Qualité :** Minimale

**Signature :**
```java
private static Image byteBufferToImage(ByteBuffer buffer, int width, int heig...
```

**Documentation actuelle :**
```java
/**
* Convertit un ByteBuffer OpenCL (format RGBA) en Image JavaFX
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `cleanup()` - Ligne 387

**Qualité :** Minimale

**Signature :**
```java
public static void cleanup() {
```

**Documentation actuelle :**
```java
/**
* Libère les ressources OpenCL (à appeler lors de la fermeture de l'application)
*/
```

**⚠️ Tags manquants :** @param

---

