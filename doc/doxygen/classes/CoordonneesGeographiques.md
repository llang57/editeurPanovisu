# ⚠️ CoordonneesGeographiques

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\CoordonneesGeographiques.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 4/11 éléments documentés (36.4%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 4 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 2 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 5 |

## Propriétés (5)

### ⚫ `longitude` - Ligne 14

**Qualité :** Absente

**Déclaration :**
```java
private double latitude, longitude;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `latitude` - Ligne 30

**Qualité :** Absente

**Déclaration :**
```java
return latitude;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `longitude` - Ligne 44

**Qualité :** Absente

**Déclaration :**
```java
return longitude;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `degreDecimal` - Ligne 59

**Qualité :** Absente

**Déclaration :**
```java
return degreDecimal;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `signe` - Ligne 63

**Qualité :** Absente

**Déclaration :**
```java
String signe = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (6)

### 🟡 `getLatitude()` - Ligne 29

**Qualité :** Partielle

**Signature :**
```java
public double getLatitude() {
```

**Documentation actuelle :**
```java
/**
* @return the latitude
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setLatitude()` - Ligne 36

**Qualité :** Partielle

**Signature :**
```java
public void setLatitude(double latitude) {
```

**Documentation actuelle :**
```java
/**
* @param latitude the latitude to set
*/
```

**Tags présents :** @param

---

### 🟡 `getLongitude()` - Ligne 43

**Qualité :** Partielle

**Signature :**
```java
public double getLongitude() {
```

**Documentation actuelle :**
```java
/**
* @return the longitude
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setLongitude()` - Ligne 50

**Qualité :** Partielle

**Signature :**
```java
public void setLongitude(double longitude) {
```

**Documentation actuelle :**
```java
/**
* @param longitude the longitude to set
*/
```

**Tags présents :** @param

---

### ⚫ `fromDMS()` - Ligne 54

**Qualité :** Absente

**Signature :**
```java
public static double fromDMS(String degDMS) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `toDMS()` - Ligne 62

**Qualité :** Absente

**Signature :**
```java
public static String toDMS(double degDecimal) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

