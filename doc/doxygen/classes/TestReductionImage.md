# ❌ TestReductionImage

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `/home/llang/developpement/java/editeurPanovisu/src/editeurpanovisu/gpu/TestReductionImage.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 1/6 éléments documentés (16.7%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 1 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 3 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 2 |

## Propriétés (2)

### ⚫ `targetWidth` - Ligne 60

**Qualité :** Absente

**Déclaration :**
```java
int targetWidth = srcWidth / 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `targetHeight` - Ligne 61

**Qualité :** Absente

**Déclaration :**
```java
int targetHeight = srcHeight / 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (4)

### ⚫ `start()` - Ligne 24

**Qualité :** Absente

**Signature :**
```java
public void start(Stage primaryStage) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### ⚫ `showComparison()` - Ligne 132

**Qualité :** Absente

**Signature :**
```java
private void showComparison(Stage stage, Image source,
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### ⚫ `showError()` - Ligne 228

**Qualité :** Absente

**Signature :**
```java
private void showError(Stage stage, String message) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### 🟢 `main()` - Ligne 244

**Qualité :** Complète

**Signature :**
```java
public static void main(String[] args) {
```

**Documentation actuelle :**
```java
/**
* Point d'entrée pour tester la réduction d'images avec GPU
*
* @param args Arguments de la ligne de commande (non utilisés)
*/
```

**Tags présents :** @param

---

