# ⚠️ BuildNumberIncrementer

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\BuildNumberIncrementer.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 5/14 éléments documentés (35.7%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 5 |
| ⚫ Absente | 1 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 8 |

## Propriétés (8)

### ⚫ `BUILD_NUM_FILE` - Ligne 16

**Qualité :** Absente

**Déclaration :**
```java
private static final String BUILD_NUM_FILE = "build.num";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `I18N_PROPERTIES_FILE` - Ligne 17

**Qualité :** Absente

**Déclaration :**
```java
private static final String I18N_PROPERTIES_FILE = "src/editeurpanovisu/i18n/...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `PROJECT_PROPERTIES_FILE` - Ligne 18

**Qualité :** Absente

**Déclaration :**
```java
private static final String PROJECT_PROPERTIES_FILE = "src/project.properties";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `newBuild` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
int newBuild = currentBuild + 1;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `1990` - Ligne 53

**Qualité :** Absente

**Déclaration :**
```java
return 1990;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `found` - Ligne 98

**Qualité :** Absente

**Déclaration :**
```java
boolean found = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `found` - Ligne 136

**Qualité :** Absente

**Déclaration :**
```java
boolean found = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `buildStr` - Ligne 169

**Qualité :** Absente

**Déclaration :**
```java
return buildStr;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (6)

### ⚫ `main()` - Ligne 20

**Qualité :** Absente

**Signature :**
```java
public static void main(String[] args) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### 🟠 `readBuildNumber()` - Ligne 49

**Qualité :** Minimale

**Signature :**
```java
private static int readBuildNumber() throws IOException {
```

**Documentation actuelle :**
```java
/**
* Lit le numéro de build depuis build.num
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `updateBuildNumFile()` - Ligne 68

**Qualité :** Minimale

**Signature :**
```java
private static void updateBuildNumFile(int buildNumber) throws IOException {
```

**Documentation actuelle :**
```java
/**
* Met à jour le fichier build.num
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `updateI18nProperties()` - Ligne 87

**Qualité :** Minimale

**Signature :**
```java
private static void updateI18nProperties(int buildNumber) throws IOException {
```

**Documentation actuelle :**
```java
/**
* Met à jour le fichier i18n/PanoVisu.properties
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `updateProjectProperties()` - Ligne 125

**Qualité :** Minimale

**Signature :**
```java
private static void updateProjectProperties(int buildNumber) throws IOExcepti...
```

**Documentation actuelle :**
```java
/**
* Met à jour le fichier project.properties
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `formatBuildNumber()` - Ligne 166

**Qualité :** Minimale

**Signature :**
```java
private static String formatBuildNumber(int buildNumber) {
```

**Documentation actuelle :**
```java
/**
* Formate le numéro de build avec espace insécable
* Exemple : 1990 -> "1\u00a0990", 2000 -> "2\u00a0000"
*/
```

**⚠️ Tags manquants :** @param, @return

---

