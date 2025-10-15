# 🔶 BigDecimalField

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\BigDecimalField.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 11/16 éléments documentés (68.8%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 8 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 0 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 5 |

## Propriétés (5)

### ⚫ `format` - Ligne 22

**Qualité :** Absente

**Déclaration :**
```java
private final DecimalFormat format;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `minValue` - Ligne 23

**Qualité :** Absente

**Déclaration :**
```java
private BigDecimal minValue = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `maxValue` - Ligne 24

**Qualité :** Absente

**Déclaration :**
```java
private BigDecimal maxValue = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `number` - Ligne 110

**Qualité :** Absente

**Déclaration :**
```java
return number;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `format` - Ligne 161

**Qualité :** Absente

**Déclaration :**
```java
return format;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (11)

### 🟠 `BigDecimalField()` - Ligne 30

**Qualité :** Minimale

**Signature :**
```java
public BigDecimalField() {
```

**Documentation actuelle :**
```java
/**
* Constructeur par défaut
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟡 `BigDecimalField()` - Ligne 38

**Qualité :** Partielle

**Signature :**
```java
public BigDecimalField(DecimalFormat format) {
```

**Documentation actuelle :**
```java
/**
* Constructeur avec format personnalisé
* @param format Format décimal à utiliser
*/
```

**Tags présents :** @param

**⚠️ Tags manquants :** @return

---

### 🟡 `BigDecimalField()` - Ligne 48

**Qualité :** Partielle

**Signature :**
```java
public BigDecimalField(BigDecimal initialValue) {
```

**Documentation actuelle :**
```java
/**
* Constructeur avec valeur initiale
* @param initialValue Valeur initiale
*/
```

**Tags présents :** @param

**⚠️ Tags manquants :** @return

---

### 🟠 `setupTextField()` - Ligne 56

**Qualité :** Minimale

**Signature :**
```java
private void setupTextField() {
```

**Documentation actuelle :**
```java
/**
* Configure le TextField avec validation
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `validateAndFormat()` - Ligne 78

**Qualité :** Minimale

**Signature :**
```java
private void validateAndFormat() {
```

**Documentation actuelle :**
```java
/**
* Valide et formate la valeur
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `numberProperty()` - Ligne 109

**Qualité :** Partielle

**Signature :**
```java
public ObjectProperty<BigDecimal> numberProperty() {
```

**Documentation actuelle :**
```java
/**
* Obtient la propriété du nombre
* @return La propriété ObjectProperty
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getNumber()` - Ligne 117

**Qualité :** Partielle

**Signature :**
```java
public BigDecimal getNumber() {
```

**Documentation actuelle :**
```java
/**
* Obtient la valeur BigDecimal
* @return La valeur ou null si invalide
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNumber()` - Ligne 125

**Qualité :** Partielle

**Signature :**
```java
public void setNumber(BigDecimal value) {
```

**Documentation actuelle :**
```java
/**
* Définit la valeur BigDecimal
* @param value La nouvelle valeur
*/
```

**Tags présents :** @param

---

### 🟡 `setMinValue()` - Ligne 144

**Qualité :** Partielle

**Signature :**
```java
public void setMinValue(BigDecimal minValue) {
```

**Documentation actuelle :**
```java
/**
* Définit la valeur minimale
* @param minValue Valeur minimale
*/
```

**Tags présents :** @param

---

### 🟡 `setMaxValue()` - Ligne 152

**Qualité :** Partielle

**Signature :**
```java
public void setMaxValue(BigDecimal maxValue) {
```

**Documentation actuelle :**
```java
/**
* Définit la valeur maximale
* @param maxValue Valeur maximale
*/
```

**Tags présents :** @param

---

### 🟡 `getFormat()` - Ligne 160

**Qualité :** Partielle

**Signature :**
```java
public DecimalFormat getFormat() {
```

**Documentation actuelle :**
```java
/**
* Obtient le format utilisé
* @return Le format décimal
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

