# ❌ TestImageResizeInteractive

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `/home/llang/developpement/java/editeurPanovisu/src/editeurpanovisu/gpu/TestImageResizeInteractive.java`

**Documentation de la classe :** ✅ Oui (7 lignes)

**Progression :** 1/8 éléments documentés (12.5%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 1 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 5 |

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
int targetWidth = srcWidth * 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `targetHeight` - Ligne 61

**Qualité :** Absente

**Déclaration :**
```java
int targetHeight = srcHeight * 2;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (6)

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

### ⚫ `showResults()` - Ligne 87

**Qualité :** Absente

**Signature :**
```java
private void showResults(Stage stage, Image source, Image result, String titl...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### ⚫ `VBox()` - Ligne 111

**Qualité :** Absente

**Signature :**
```java
new VBox(5, new Label("Source"), srcView),
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `VBox()` - Ligne 112

**Qualité :** Absente

**Signature :**
```java
new VBox(5, new Label("Résultat"), resView)
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `showError()` - Ligne 123

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

### 🟢 `main()` - Ligne 138

**Qualité :** Complète

**Signature :**
```java
public static void main(String[] args) {
```

**Documentation actuelle :**
```java
/**
* Point d'entrée pour tester interactivement le redimensionnement d'images GPU
*
* @param args Arguments de la ligne de commande (non utilisés)
*/
```

**Tags présents :** @param

---

