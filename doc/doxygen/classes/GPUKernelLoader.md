# ⚠️ GPUKernelLoader

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `/home/llang/developpement/java/editeurPanovisu/src/editeurpanovisu/gpu/GPUKernelLoader.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 5/15 éléments documentés (33.3%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 5 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 7 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 3 |

## Propriétés (3)

### ⚫ `resourcePath` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
String resourcePath = "/editeurpanovisu/gpu/" + kernelName;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `source` - Ligne 45

**Qualité :** Absente

**Déclaration :**
```java
return source;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `e` - Ligne 49

**Qualité :** Absente

**Déclaration :**
```java
throw e;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (12)

### 🟢 `loadKernel()` - Ligne 27

**Qualité :** Complète

**Signature :**
```java
public static String loadKernel(String kernelName) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Charge un kernel OpenCL depuis les ressources
*
* @param kernelName Nom du fichier kernel (sans le chemin, avec .cl)
* @return Code source du kernel
* @throws Exception Si le kernel ne peut pas être chargé
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `Exception()` - Ligne 33

**Qualité :** Absente

**Signature :**
```java
throw new Exception("Kernel non trouvé: " + resourcePath);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 37

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(is, StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 41

**Qualité :** Absente

**Signature :**
```java
throw new Exception("Kernel vide: " + resourcePath);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadEqui2CubeKernel()` - Ligne 58

**Qualité :** Complète

**Signature :**
```java
public static String loadEqui2CubeKernel() throws Exception {
```

**Documentation actuelle :**
```java
/**
* Charge le kernel equi2cube
* @return Code source du kernel
* @throws Exception Si le kernel ne peut pas être chargé
*/
```

**Tags présents :** @return, @throws

---

### ⚫ `loadKernel()` - Ligne 59

**Qualité :** Absente

**Signature :**
```java
return loadKernel("equi2cube.cl");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadCube2EquiKernel()` - Ligne 67

**Qualité :** Complète

**Signature :**
```java
public static String loadCube2EquiKernel() throws Exception {
```

**Documentation actuelle :**
```java
/**
* Charge le kernel cube2equi
* @return Code source du kernel
* @throws Exception Si le kernel ne peut pas être chargé
*/
```

**Tags présents :** @return, @throws

---

### ⚫ `loadKernel()` - Ligne 68

**Qualité :** Absente

**Signature :**
```java
return loadKernel("cube2equi.cl");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadBicubicKernel()` - Ligne 76

**Qualité :** Complète

**Signature :**
```java
public static String loadBicubicKernel() throws Exception {
```

**Documentation actuelle :**
```java
/**
* Charge le kernel de redimensionnement bicubique
* @return Code source du kernel
* @throws Exception Si le kernel ne peut pas être chargé
*/
```

**Tags présents :** @return, @throws

---

### ⚫ `loadKernel()` - Ligne 77

**Qualité :** Absente

**Signature :**
```java
return loadKernel("resize_bicubic.cl");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `loadLanczosKernel()` - Ligne 85

**Qualité :** Complète

**Signature :**
```java
public static String loadLanczosKernel() throws Exception {
```

**Documentation actuelle :**
```java
/**
* Charge le kernel de redimensionnement Lanczos3
* @return Code source du kernel
* @throws Exception Si le kernel ne peut pas être chargé
*/
```

**Tags présents :** @return, @throws

---

### ⚫ `loadKernel()` - Ligne 86

**Qualité :** Absente

**Signature :**
```java
return loadKernel("resize_lanczos3.cl");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

