# 🔶 NavigateurOpenLayersSeul

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurOpenLayersSeul.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** JavaApplication2

**Progression :** 28/43 éléments documentés (65.1%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 23 |
| 🟠 Minimale | 5 |
| ⚫ Absente | 1 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 14 |

## Propriétés (14)

### ⚫ `marqueur` - Ligne 22

**Qualité :** Absente

**Déclaration :**
```java
private CoordonneesGeographiques marqueur;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `navigateurCarteSeul` - Ligne 23

**Qualité :** Absente

**Déclaration :**
```java
private NavigateurCarteSeul navigateurCarteSeul;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bDebut` - Ligne 24

**Qualité :** Absente

**Déclaration :**
```java
private boolean bDebut = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strCartesOpenLayers` - Ligne 25

**Qualité :** Absente

**Déclaration :**
```java
private String[] strCartesOpenLayers;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strCartoActive` - Ligne 28

**Qualité :** Absente

**Déclaration :**
```java
private String strCartoActive = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bingApiKey` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
private String bingApiKey = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marqueur` - Ligne 36

**Qualité :** Absente

**Déclaration :**
```java
return marqueur;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `coordonnees` - Ligne 66

**Qualité :** Absente

**Déclaration :**
```java
return coordonnees;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bCarteChoisie` - Ligne 192

**Qualité :** Absente

**Déclaration :**
```java
boolean bCarteChoisie = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `apOpenLayers` - Ligne 259

**Qualité :** Absente

**Déclaration :**
```java
return apOpenLayers;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strCartoActive` - Ligne 266

**Qualité :** Absente

**Déclaration :**
```java
return strCartoActive;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `apChoixCartographie` - Ligne 280

**Qualité :** Absente

**Déclaration :**
```java
return apChoixCartographie;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bingApiKey` - Ligne 294

**Qualité :** Absente

**Déclaration :**
```java
return bingApiKey;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bDebut` - Ligne 308

**Qualité :** Absente

**Déclaration :**
```java
return bDebut;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (29)

### 🟡 `getMarqueur()` - Ligne 35

**Qualité :** Partielle

**Signature :**
```java
public CoordonneesGeographiques getMarqueur() {
```

**Documentation actuelle :**
```java
/**
* @return the marqueur
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setMarqueur()` - Ligne 42

**Qualité :** Partielle

**Signature :**
```java
public void setMarqueur(CoordonneesGeographiques marqueur) {
```

**Documentation actuelle :**
```java
/**
* @param marqueur the marqueur to set
*/
```

**Tags présents :** @param

---

### 🟡 `allerCoordonnees()` - Ligne 51

**Qualité :** Partielle

**Signature :**
```java
public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacte...
```

**Documentation actuelle :**
```java
/**
*
* @param coordonnees
* @param iFacteurZoom
*/
```

**Tags présents :** @param

---

### 🟠 `recupereCoordonnees()` - Ligne 61

**Qualité :** Minimale

**Signature :**
```java
public CoordonneesGeographiques recupereCoordonnees() {
```

**Documentation actuelle :**
```java
/**
*
* @return
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `retireMarqueur()` - Ligne 73

**Qualité :** Partielle

**Signature :**
```java
public void retireMarqueur(int iNumeroMarqueur) {
```

**Documentation actuelle :**
```java
/**
*
* @param iNumeroMarqueur
*/
```

**Tags présents :** @param

---

### 🟠 `retireMarqueurs()` - Ligne 80

**Qualité :** Minimale

**Signature :**
```java
public void retireMarqueurs() {
```

**Documentation actuelle :**
```java
/**
*
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `ajouteMarqueur()` - Ligne 90

**Qualité :** Partielle

**Signature :**
```java
public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coor...
```

**Documentation actuelle :**
```java
/**
*
* @param iNumeroMarqueur
* @param coordMarqueur
* @param strHTML
*/
```

**Tags présents :** @param

---

### 🟡 `allerAdresse()` - Ligne 101

**Qualité :** Partielle

**Signature :**
```java
public void allerAdresse(String strAdresse, int iFacteurZoom) {
```

**Documentation actuelle :**
```java
/**
*
* @param strAdresse
* @param iFacteurZoom
*/
```

**Tags présents :** @param

---

### 🟡 `choixZoom()` - Ligne 109

**Qualité :** Partielle

**Signature :**
```java
public void choixZoom(int iFacteurZoom) {
```

**Documentation actuelle :**
```java
/**
*
* @param iFacteurZoom
*/
```

**Tags présents :** @param

---

### 🟡 `valideBingApiKey()` - Ligne 117

**Qualité :** Partielle

**Signature :**
```java
public void valideBingApiKey(String bingApiKey) {
```

**Documentation actuelle :**
```java
/**
*
* @param bingApiKey
*/
```

**Tags présents :** @param

---

### 🟠 `recupereCartographiesOpenLayers()` - Ligne 135

**Qualité :** Minimale

**Signature :**
```java
public String recupereCartographiesOpenLayers() {
```

**Documentation actuelle :**
```java
/**
*
* @return
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### ⚫ `retireRadar()` - Ligne 139

**Qualité :** Absente

**Signature :**
```java
public void retireRadar() {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### 🟡 `afficheRadar()` - Ligne 153

**Qualité :** Partielle

**Signature :**
```java
public void afficheRadar(
```

**Documentation actuelle :**
```java
/**
*
* @param centre
* @param angle
* @param ouverture
* @param rayon
* @param strCouleurFond
* @param strCouleurLigne
* @param opacite
*/
```

**Tags présents :** @param

---

### 🟡 `changeCarte()` - Ligne 180

**Qualité :** Partielle

**Signature :**
```java
public void changeCarte(String strCarto) {
```

**Documentation actuelle :**
```java
/**
*
* @param strCarto
*/
```

**Tags présents :** @param

---

### 🟠 `afficheCartesOpenlayer()` - Ligne 187

**Qualité :** Minimale

**Signature :**
```java
public void afficheCartesOpenlayer() {
```

**Documentation actuelle :**
```java
/**
*
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `afficheNavigateurOpenLayer()` - Ligne 212

**Qualité :** Minimale

**Signature :**
```java
public AnchorPane afficheNavigateurOpenLayer() {
```

**Documentation actuelle :**
```java
/**
*
* @return
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getStrCartoActive()` - Ligne 265

**Qualité :** Partielle

**Signature :**
```java
public String getStrCartoActive() {
```

**Documentation actuelle :**
```java
/**
* @return the strCartoActive
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrCartoActive()` - Ligne 272

**Qualité :** Partielle

**Signature :**
```java
public void setStrCartoActive(String strCartoActive) {
```

**Documentation actuelle :**
```java
/**
* @param strCartoActive the strCartoActive to set
*/
```

**Tags présents :** @param

---

### 🟡 `getApChoixCartographie()` - Ligne 279

**Qualité :** Partielle

**Signature :**
```java
public AnchorPane getApChoixCartographie() {
```

**Documentation actuelle :**
```java
/**
* @return the apChoixCartographie
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setApChoixCartographie()` - Ligne 286

**Qualité :** Partielle

**Signature :**
```java
public void setApChoixCartographie(AnchorPane apChoixCartographie) {
```

**Documentation actuelle :**
```java
/**
* @param apChoixCartographie the apChoixCartographie to set
*/
```

**Tags présents :** @param

---

### 🟡 `getBingApiKey()` - Ligne 293

**Qualité :** Partielle

**Signature :**
```java
public String getBingApiKey() {
```

**Documentation actuelle :**
```java
/**
* @return the bingApiKey
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setBingApiKey()` - Ligne 300

**Qualité :** Partielle

**Signature :**
```java
public void setBingApiKey(String bingApiKey) {
```

**Documentation actuelle :**
```java
/**
* @param bingApiKey the bingApiKey to set
*/
```

**Tags présents :** @param

---

### 🟡 `isbDebut()` - Ligne 307

**Qualité :** Partielle

**Signature :**
```java
public boolean isbDebut() {
```

**Documentation actuelle :**
```java
/**
* @return the bDebut
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setbDebut()` - Ligne 314

**Qualité :** Partielle

**Signature :**
```java
public void setbDebut(boolean bDebut) {
```

**Documentation actuelle :**
```java
/**
* @param bDebut the bDebut to set
*/
```

**Tags présents :** @param

---

### 🟡 `adresseInconnue()` - Ligne 327

**Qualité :** Partielle

**Signature :**
```java
public void adresseInconnue(String msg) {
```

**Documentation actuelle :**
```java
/**
*
* @param msg
*/
```

**Tags présents :** @param

---

### 🟡 `adresseTrouvee()` - Ligne 336

**Qualité :** Partielle

**Signature :**
```java
public void adresseTrouvee(double lon, double lat) {
```

**Documentation actuelle :**
```java
/**
*
* @param lon
* @param lat
*/
```

**Tags présents :** @param

---

### 🟡 `afficheChaine()` - Ligne 346

**Qualité :** Partielle

**Signature :**
```java
public void afficheChaine(String strChaine) {
```

**Documentation actuelle :**
```java
/**
*
* @param strChaine
*/
```

**Tags présents :** @param

---

### 🟡 `zoom()` - Ligne 355

**Qualité :** Partielle

**Signature :**
```java
public void zoom(int iZoom) {
```

**Documentation actuelle :**
```java
/**
*
* @param iZoom
*/
```

**Tags présents :** @param

---

### 🟡 `changeLayer()` - Ligne 363

**Qualité :** Partielle

**Signature :**
```java
public void changeLayer(String strNomLayer) {
```

**Documentation actuelle :**
```java
/**
*
* @param strNomLayer
*/
```

**Tags présents :** @param

---

