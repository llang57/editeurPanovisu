# 🔶 CartePanoVisu

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\CartePanoVisu.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 14/24 éléments documentés (58.3%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 13 |
| 🟠 Minimale | 1 |
| ⚫ Absente | 0 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 10 |

## Propriétés (10)

### ⚫ `centreCarte` - Ligne 15

**Qualité :** Absente

**Déclaration :**
```java
private CoordonneesGeographiques centreCarte;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `facteurZoom` - Ligne 16

**Qualité :** Absente

**Déclaration :**
```java
private int facteurZoom;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreMarqueur` - Ligne 17

**Qualité :** Absente

**Déclaration :**
```java
private int iNombreMarqueur;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marqueurs` - Ligne 18

**Qualité :** Absente

**Déclaration :**
```java
private MarqueurGeolocalisation[] marqueurs = new MarqueurGeolocalisation[100];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTypeCarte` - Ligne 19

**Qualité :** Absente

**Déclaration :**
```java
private String strTypeCarte = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `centreCarte` - Ligne 62

**Qualité :** Absente

**Déclaration :**
```java
return centreCarte;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `facteurZoom` - Ligne 76

**Qualité :** Absente

**Déclaration :**
```java
return facteurZoom;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreMarqueur` - Ligne 90

**Qualité :** Absente

**Déclaration :**
```java
return iNombreMarqueur;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marqueurs` - Ligne 104

**Qualité :** Absente

**Déclaration :**
```java
return marqueurs;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTypeCarte` - Ligne 127

**Qualité :** Absente

**Déclaration :**
```java
return strTypeCarte;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (14)

### 🟠 `CartePanoVisu()` - Ligne 24

**Qualité :** Minimale

**Signature :**
```java
public CartePanoVisu() {
```

**Documentation actuelle :**
```java
/**
*
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟡 `CartePanoVisu()` - Ligne 39

**Qualité :** Partielle

**Signature :**
```java
public CartePanoVisu(int iNombreMarqueur, CoordonneesGeographiques coordonnee...
```

**Documentation actuelle :**
```java
/**
*
* @param iNombreMarqueur nombres de marqueurs de la carte
* @param coordonneesCentre coordonnées du centre de la carte
* @param strTypeCarte type de carte (OSM, Bing, Google)
* @param marqueurs les marqueurs de la carte
* @param facteurZoom niveau de zoom
*/
```

**Tags présents :** @param

**⚠️ Tags manquants :** @return

---

### 🟡 `ajouteMarqueur()` - Ligne 53

**Qualité :** Partielle

**Signature :**
```java
public void ajouteMarqueur(CoordonneesGeographiques coordonnees, String strXM...
```

**Documentation actuelle :**
```java
/**
*
* @param coordonnees Coordonnées géographiques du marqueur
* @param strXML fichier XML du pano
* @param strHTML chaine HTML
*/
```

**Tags présents :** @param

---

### 🟡 `getCentreCarte()` - Ligne 61

**Qualité :** Partielle

**Signature :**
```java
public CoordonneesGeographiques getCentreCarte() {
```

**Documentation actuelle :**
```java
/**
* @return the centreCarte
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setCentreCarte()` - Ligne 68

**Qualité :** Partielle

**Signature :**
```java
public void setCentreCarte(CoordonneesGeographiques centreCarte) {
```

**Documentation actuelle :**
```java
/**
* @param centreCarte the centreCarte to set
*/
```

**Tags présents :** @param

---

### 🟡 `getFacteurZoom()` - Ligne 75

**Qualité :** Partielle

**Signature :**
```java
public int getFacteurZoom() {
```

**Documentation actuelle :**
```java
/**
* @return the facteurZoom
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setFacteurZoom()` - Ligne 82

**Qualité :** Partielle

**Signature :**
```java
public void setFacteurZoom(int facteurZoom) {
```

**Documentation actuelle :**
```java
/**
* @param facteurZoom the facteurZoom to set
*/
```

**Tags présents :** @param

---

### 🟡 `getiNombreMarqueur()` - Ligne 89

**Qualité :** Partielle

**Signature :**
```java
public int getiNombreMarqueur() {
```

**Documentation actuelle :**
```java
/**
* @return the iNombreMarqueur
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setiNombreMarqueur()` - Ligne 96

**Qualité :** Partielle

**Signature :**
```java
public void setiNombreMarqueur(int iNombreMarqueur) {
```

**Documentation actuelle :**
```java
/**
* @param iNombreMarqueur the iNombreMarqueur to set
*/
```

**Tags présents :** @param

---

### 🟡 `getMarqueurs()` - Ligne 103

**Qualité :** Partielle

**Signature :**
```java
public MarqueurGeolocalisation[] getMarqueurs() {
```

**Documentation actuelle :**
```java
/**
* @return the marqueurs
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setMarqueurs()` - Ligne 110

**Qualité :** Partielle

**Signature :**
```java
public void setMarqueurs(MarqueurGeolocalisation[] marqueurs) {
```

**Documentation actuelle :**
```java
/**
* @param marqueurs the marqueurs to set
*/
```

**Tags présents :** @param

---

### 🟡 `setMarqueursI()` - Ligne 119

**Qualité :** Partielle

**Signature :**
```java
public void setMarqueursI(int i, MarqueurGeolocalisation marqueurs) {
```

**Documentation actuelle :**
```java
/**
*
* @param i numéro du marqueur
* @param marqueurs valeur du marqueur
*/
```

**Tags présents :** @param

---

### 🟡 `getStrTypeCarte()` - Ligne 126

**Qualité :** Partielle

**Signature :**
```java
public String getStrTypeCarte() {
```

**Documentation actuelle :**
```java
/**
* @return the strTypeCarte
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrTypeCarte()` - Ligne 133

**Qualité :** Partielle

**Signature :**
```java
public void setStrTypeCarte(String strTypeCarte) {
```

**Documentation actuelle :**
```java
/**
* @param strTypeCarte the strTypeCarte to set
*/
```

**Tags présents :** @param

---

