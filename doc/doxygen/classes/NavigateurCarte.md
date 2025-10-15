# 🔶 NavigateurCarte

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurCarte.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 7/10 éléments documentés (70.0%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 4 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 0 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 3 |

## Propriétés (3)

### ⚫ `css` - Ligne 133

**Qualité :** Absente

**Déclaration :**
```java
return css;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `webView` - Ligne 149

**Qualité :** Absente

**Déclaration :**
```java
return webView;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `webEngine` - Ligne 163

**Qualité :** Absente

**Déclaration :**
```java
return webEngine;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (7)

### 🟠 `replaceImageUrlsWithBase64()` - Ligne 114

**Qualité :** Minimale

**Signature :**
```java
private String replaceImageUrlsWithBase64(String css) {
```

**Documentation actuelle :**
```java
/**
* Remplace les URLs d'images dans le CSS par des données base64
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `encodeImageToBase64()` - Ligne 139

**Qualité :** Minimale

**Signature :**
```java
private String encodeImageToBase64(String imagePath) throws java.io.IOExcepti...
```

**Documentation actuelle :**
```java
/**
* Encode une image en base64
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟡 `getWebView()` - Ligne 148

**Qualité :** Partielle

**Signature :**
```java
public WebView getWebView() {
```

**Documentation actuelle :**
```java
/**
* @return the webView
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setWebView()` - Ligne 155

**Qualité :** Partielle

**Signature :**
```java
public void setWebView(WebView webView) {
```

**Documentation actuelle :**
```java
/**
* @param webView the webView to set
*/
```

**Tags présents :** @param

---

### 🟡 `getWebEngine()` - Ligne 162

**Qualité :** Partielle

**Signature :**
```java
public WebEngine getWebEngine() {
```

**Documentation actuelle :**
```java
/**
* @return the webEngine
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setWebEngine()` - Ligne 169

**Qualité :** Partielle

**Signature :**
```java
public void setWebEngine(WebEngine webEngine) {
```

**Documentation actuelle :**
```java
/**
* @param webEngine the webEngine to set
*/
```

**Tags présents :** @param

---

### 🟠 `invalidateMapSize()` - Ligne 176

**Qualité :** Minimale

**Signature :**
```java
public void invalidateMapSize() {
```

**Documentation actuelle :**
```java
/**
* Force le recalcul de la taille de la carte Leaflet
*/
```

**⚠️ Tags manquants :** @param

---

