# 🔶 Panoramique

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\Panoramique.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 81/145 éléments documentés (55.9%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 4 |
| 🟡 Partielle | 75 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 0 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 2 |
| ⚫ Absente | 64 |

## Propriétés (66)

### 🟠 `SPHERE` - Ligne 20

**Qualité :** Minimale

**Déclaration :**
```java
public static final String SPHERE = "sphere";
```

**Documentation actuelle :**
```java
/**
* Constante de type de panoramique : Sphere
*/
```

**⚠️ Tags manquants :** @see

---

### 🟠 `CUBE` - Ligne 25

**Qualité :** Minimale

**Déclaration :**
```java
public static final String CUBE = "cube";
```

**Documentation actuelle :**
```java
/**
* Constante de type de panoramique : Sphere
*/
```

**⚠️ Tags manquants :** @see

---

### ⚫ `hotspots` - Ligne 27

**Qualité :** Absente

**Déclaration :**
```java
private final HotSpot[] hotspots = new HotSpot[100];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imageHotspot` - Ligne 28

**Qualité :** Absente

**Déclaration :**
```java
private final HotspotImage[] imageHotspot = new HotspotImage[100];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `HTMLHotspot` - Ligne 29

**Qualité :** Absente

**Déclaration :**
```java
private final HotspotHTML[] HTMLHotspot = new HotspotHTML[100];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `diapoHotspot` - Ligne 30

**Qualité :** Absente

**Déclaration :**
```java
private final HotspotDiaporama[] diapoHotspot = new HotspotDiaporama[100];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTitrePanoramique` - Ligne 31

**Qualité :** Absente

**Déclaration :**
```java
private String strTitrePanoramique="";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strNomFichier` - Ligne 32

**Qualité :** Absente

**Déclaration :**
```java
private String strNomFichier="";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `regardX` - Ligne 33

**Qualité :** Absente

**Déclaration :**
```java
private double regardX = 0.d;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `regardY` - Ligne 34

**Qualité :** Absente

**Déclaration :**
```java
private double regardY = 0.d;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `champVisuel` - Ligne 35

**Qualité :** Absente

**Déclaration :**
```java
private double champVisuel=75.d;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `minLat` - Ligne 36

**Qualité :** Absente

**Déclaration :**
```java
private double minLat=-1000;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `maxLat` - Ligne 37

**Qualité :** Absente

**Déclaration :**
```java
private double maxLat=1000;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `fovMax` - Ligne 38

**Qualité :** Absente

**Déclaration :**
```java
private double fovMax=70;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `fovMin` - Ligne 39

**Qualité :** Absente

**Déclaration :**
```java
private double fovMin=12;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bMinLat` - Ligne 40

**Qualité :** Absente

**Déclaration :**
```java
private boolean bMinLat=false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bMaxLat` - Ligne 41

**Qualité :** Absente

**Déclaration :**
```java
private boolean bMaxLat=false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoramique` - Ligne 42

**Qualité :** Absente

**Déclaration :**
```java
private Image imgPanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgVisuPanoramique` - Ligne 43

**Qualité :** Absente

**Déclaration :**
```java
private Image imgVisuPanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgVignettePanoramique` - Ligne 44

**Qualité :** Absente

**Déclaration :**
```java
private Image imgVignettePanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoRect` - Ligne 45

**Qualité :** Absente

**Déclaration :**
```java
private Image imgPanoRect;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoRectListe` - Ligne 46

**Qualité :** Absente

**Déclaration :**
```java
private Image imgPanoRectListe;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgCubeEqui` - Ligne 47

**Qualité :** Absente

**Déclaration :**
```java
private Image imgCubeEqui;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspots` - Ligne 48

**Qualité :** Absente

**Déclaration :**
```java
private int iNombreHotspots = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotImage` - Ligne 49

**Qualité :** Absente

**Déclaration :**
```java
private int iNombreHotspotImage = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotHTML` - Ligne 50

**Qualité :** Absente

**Déclaration :**
```java
private int iNombreHotspotHTML = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotDiapo` - Ligne 51

**Qualité :** Absente

**Déclaration :**
```java
private int iNombreHotspotDiapo = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTypePanoramique` - Ligne 52

**Qualité :** Absente

**Déclaration :**
```java
private String strTypePanoramique="";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAfficheTitre` - Ligne 53

**Qualité :** Absente

**Déclaration :**
```java
private boolean bAfficheTitre;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAffDescription` - Ligne 54

**Qualité :** Absente

**Déclaration :**
```java
private boolean bAffDescription;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAfficheInfo` - Ligne 55

**Qualité :** Absente

**Déclaration :**
```java
private boolean bAfficheInfo;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAffichePlan` - Ligne 56

**Qualité :** Absente

**Déclaration :**
```java
private boolean bAffichePlan;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNumeroPlan` - Ligne 57

**Qualité :** Absente

**Déclaration :**
```java
private int iNumeroPlan = -1;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `zeroNord` - Ligne 58

**Qualité :** Absente

**Déclaration :**
```java
private double zeroNord = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `nombreNiveaux` - Ligne 59

**Qualité :** Absente

**Déclaration :**
```java
private double nombreNiveaux = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marqueurGeolocatisation` - Ligne 60

**Qualité :** Absente

**Déclaration :**
```java
private CoordonneesGeographiques marqueurGeolocatisation=null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strDescriptionIA` - Ligne 61

**Qualité :** Absente

**Déclaration :**
```java
private String strDescriptionIA = "";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAffichePlan` - Ligne 79

**Qualité :** Absente

**Déclaration :**
```java
return bAffichePlan;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strNomFichier` - Ligne 93

**Qualité :** Absente

**Déclaration :**
```java
return strNomFichier;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `regardX` - Ligne 107

**Qualité :** Absente

**Déclaration :**
```java
return regardX;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `regardY` - Ligne 121

**Qualité :** Absente

**Déclaration :**
```java
return regardY;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoramique` - Ligne 284

**Qualité :** Absente

**Déclaration :**
```java
return imgPanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspots` - Ligne 298

**Qualité :** Absente

**Déclaration :**
```java
return iNombreHotspots;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTypePanoramique` - Ligne 312

**Qualité :** Absente

**Déclaration :**
```java
return strTypePanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAfficheTitre` - Ligne 326

**Qualité :** Absente

**Déclaration :**
```java
return bAfficheTitre;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAffDescription` - Ligne 340

**Qualité :** Absente

**Déclaration :**
```java
return bAffDescription;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bAfficheInfo` - Ligne 354

**Qualité :** Absente

**Déclaration :**
```java
return bAfficheInfo;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `strTitrePanoramique` - Ligne 368

**Qualité :** Absente

**Déclaration :**
```java
return strTitrePanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgVignettePanoramique` - Ligne 382

**Qualité :** Absente

**Déclaration :**
```java
return imgVignettePanoramique;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `zeroNord` - Ligne 396

**Qualité :** Absente

**Déclaration :**
```java
return zeroNord;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `nombreNiveaux` - Ligne 410

**Qualité :** Absente

**Déclaration :**
```java
return nombreNiveaux;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotImage` - Ligne 424

**Qualité :** Absente

**Déclaration :**
```java
return iNombreHotspotImage;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotHTML` - Ligne 438

**Qualité :** Absente

**Déclaration :**
```java
return iNombreHotspotHTML;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNumeroPlan` - Ligne 452

**Qualité :** Absente

**Déclaration :**
```java
return iNumeroPlan;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marqueurGeolocatisation` - Ligne 480

**Qualité :** Absente

**Déclaration :**
```java
return marqueurGeolocatisation;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `champVisuel` - Ligne 494

**Qualité :** Absente

**Déclaration :**
```java
return champVisuel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoRect` - Ligne 508

**Qualité :** Absente

**Déclaration :**
```java
return imgPanoRect;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `minLat` - Ligne 522

**Qualité :** Absente

**Déclaration :**
```java
return minLat;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `maxLat` - Ligne 536

**Qualité :** Absente

**Déclaration :**
```java
return maxLat;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bMinLat` - Ligne 550

**Qualité :** Absente

**Déclaration :**
```java
return bMinLat;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bMaxLat` - Ligne 564

**Qualité :** Absente

**Déclaration :**
```java
return bMaxLat;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iNombreHotspotDiapo` - Ligne 578

**Qualité :** Absente

**Déclaration :**
```java
return iNombreHotspotDiapo;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgPanoRectListe` - Ligne 592

**Qualité :** Absente

**Déclaration :**
```java
return imgPanoRectListe;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `imgCubeEqui` - Ligne 606

**Qualité :** Absente

**Déclaration :**
```java
return imgCubeEqui;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `fovMax` - Ligne 620

**Qualité :** Absente

**Déclaration :**
```java
return fovMax;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `fovMin` - Ligne 634

**Qualité :** Absente

**Déclaration :**
```java
return fovMin;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (79)

### 🟡 `Panoramique()` - Ligne 67

**Qualité :** Partielle

**Signature :**
```java
public void Panoramique(String strFichier) {
```

**Documentation actuelle :**
```java
/**
*
* @param strFichier
*/
```

**Tags présents :** @param

---

### 🟡 `isAffichePlan()` - Ligne 78

**Qualité :** Partielle

**Signature :**
```java
public  boolean isAffichePlan() {
```

**Documentation actuelle :**
```java
/**
* @return the affichePlan
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setAffichePlan()` - Ligne 85

**Qualité :** Partielle

**Signature :**
```java
public  void setAffichePlan(boolean bAffichePlan) {
```

**Documentation actuelle :**
```java
/**
* @param bAffichePlan the affichePlan to set
*/
```

**Tags présents :** @param

---

### 🟡 `getStrNomFichier()` - Ligne 92

**Qualité :** Partielle

**Signature :**
```java
public String getStrNomFichier() {
```

**Documentation actuelle :**
```java
/**
* @return the nomFichier
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrNomFichier()` - Ligne 99

**Qualité :** Partielle

**Signature :**
```java
public void setStrNomFichier(String strNomFichier) {
```

**Documentation actuelle :**
```java
/**
* @param strNomFichier the nomFichier to set
*/
```

**Tags présents :** @param

---

### 🟡 `getRegardX()` - Ligne 106

**Qualité :** Partielle

**Signature :**
```java
public double getRegardX() {
```

**Documentation actuelle :**
```java
/**
* @return the lookAtX
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setRegardX()` - Ligne 113

**Qualité :** Partielle

**Signature :**
```java
public void setRegardX(double lookAtX) {
```

**Documentation actuelle :**
```java
/**
* @param lookAtX the lookAtX to set
*/
```

**Tags présents :** @param

---

### 🟡 `getRegardY()` - Ligne 120

**Qualité :** Partielle

**Signature :**
```java
public double getRegardY() {
```

**Documentation actuelle :**
```java
/**
* @return the lookAtY
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setRegardY()` - Ligne 127

**Qualité :** Partielle

**Signature :**
```java
public void setRegardY(double lookAtY) {
```

**Documentation actuelle :**
```java
/**
* @param lookAtY the lookAtY to set
*/
```

**Tags présents :** @param

---

### 🟢 `getHotspot()` - Ligne 135

**Qualité :** Complète

**Signature :**
```java
public HotSpot getHotspot(int i) {
```

**Documentation actuelle :**
```java
/**
* @param i
* @return le hotspot numero i
*/
```

**Tags présents :** @param, @return

---

### 🟡 `setHotspot()` - Ligne 143

**Qualité :** Partielle

**Signature :**
```java
public void setHotspot(HotSpot hotspot, int i) {
```

**Documentation actuelle :**
```java
/**
* @param hotspot the hotspots to set
* @param i
*/
```

**Tags présents :** @param

---

### 🟡 `addHotspot()` - Ligne 151

**Qualité :** Partielle

**Signature :**
```java
public void addHotspot(HotSpot hotspot) {
```

**Documentation actuelle :**
```java
/**
*
* @param hotspot
*/
```

**Tags présents :** @param

---

### 🟡 `removeHotspot()` - Ligne 160

**Qualité :** Partielle

**Signature :**
```java
public void removeHotspot(int num) {
```

**Documentation actuelle :**
```java
/**
*
* @param num
*/
```

**Tags présents :** @param

---

### 🟢 `getHotspotImage()` - Ligne 172

**Qualité :** Complète

**Signature :**
```java
public HotspotImage getHotspotImage(int i) {
```

**Documentation actuelle :**
```java
/**
* @param i
* @return le hotspot numero i
*/
```

**Tags présents :** @param, @return

---

### 🟡 `setHotspotImage()` - Ligne 180

**Qualité :** Partielle

**Signature :**
```java
public void setHotspotImage(HotspotImage hotspot, int i) {
```

**Documentation actuelle :**
```java
/**
* @param hotspot the hotspots to set
* @param i
*/
```

**Tags présents :** @param

---

### 🟡 `addHotspotImage()` - Ligne 188

**Qualité :** Partielle

**Signature :**
```java
public void addHotspotImage(HotspotImage hotspot) {
```

**Documentation actuelle :**
```java
/**
*
* @param hotspot
*/
```

**Tags présents :** @param

---

### 🟡 `removeHotspotImage()` - Ligne 197

**Qualité :** Partielle

**Signature :**
```java
public void removeHotspotImage(int num) {
```

**Documentation actuelle :**
```java
/**
*
* @param num
*/
```

**Tags présents :** @param

---

### 🟢 `getHotspotHTML()` - Ligne 209

**Qualité :** Complète

**Signature :**
```java
public HotspotHTML getHotspotHTML(int i) {
```

**Documentation actuelle :**
```java
/**
* @param i
* @return le hotspot numero i
*/
```

**Tags présents :** @param, @return

---

### 🟡 `setHotspotHTML()` - Ligne 217

**Qualité :** Partielle

**Signature :**
```java
public void setHotspotHTML(HotspotHTML hotspot, int i) {
```

**Documentation actuelle :**
```java
/**
* @param hotspot the hotspots to set
* @param i
*/
```

**Tags présents :** @param

---

### 🟡 `addHotspotHTML()` - Ligne 225

**Qualité :** Partielle

**Signature :**
```java
public void addHotspotHTML(HotspotHTML hotspot) {
```

**Documentation actuelle :**
```java
/**
*
* @param hotspot
*/
```

**Tags présents :** @param

---

### 🟡 `removeHotspotHTML()` - Ligne 234

**Qualité :** Partielle

**Signature :**
```java
public void removeHotspotHTML(int num) {
```

**Documentation actuelle :**
```java
/**
*
* @param num
*/
```

**Tags présents :** @param

---

### 🟢 `getHotspotDiapo()` - Ligne 246

**Qualité :** Complète

**Signature :**
```java
public HotspotDiaporama getHotspotDiapo(int i) {
```

**Documentation actuelle :**
```java
/**
* @param i
* @return le hotspot numero i
*/
```

**Tags présents :** @param, @return

---

### 🟡 `setHotspotDiapo()` - Ligne 254

**Qualité :** Partielle

**Signature :**
```java
public void setHotspotDiapo(HotspotDiaporama hotspot, int i) {
```

**Documentation actuelle :**
```java
/**
* @param hotspot the hotspots to set
* @param i
*/
```

**Tags présents :** @param

---

### 🟡 `addHotspotDiapo()` - Ligne 262

**Qualité :** Partielle

**Signature :**
```java
public void addHotspotDiapo(HotspotDiaporama hotspot) {
```

**Documentation actuelle :**
```java
/**
*
* @param hotspot
*/
```

**Tags présents :** @param

---

### 🟡 `removeHotspotdiapo()` - Ligne 271

**Qualité :** Partielle

**Signature :**
```java
public void removeHotspotdiapo(int num) {
```

**Documentation actuelle :**
```java
/**
*
* @param num
*/
```

**Tags présents :** @param

---

### 🟡 `getImgPanoramique()` - Ligne 283

**Qualité :** Partielle

**Signature :**
```java
public Image getImgPanoramique() {
```

**Documentation actuelle :**
```java
/**
* @return the imagePanoramique
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgPanoramique()` - Ligne 290

**Qualité :** Partielle

**Signature :**
```java
public void setImgPanoramique(Image imgPanoramique) {
```

**Documentation actuelle :**
```java
/**
* @param imgPanoramique the imagePanoramique to set
*/
```

**Tags présents :** @param

---

### 🟡 `getNombreHotspots()` - Ligne 297

**Qualité :** Partielle

**Signature :**
```java
public int getNombreHotspots() {
```

**Documentation actuelle :**
```java
/**
* @return the nombreHotspots
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNombreHotspots()` - Ligne 304

**Qualité :** Partielle

**Signature :**
```java
public void setNombreHotspots(int iNombreHotspots) {
```

**Documentation actuelle :**
```java
/**
* @param iNombreHotspots the nombreHotspots to set
*/
```

**Tags présents :** @param

---

### 🟡 `getStrTypePanoramique()` - Ligne 311

**Qualité :** Partielle

**Signature :**
```java
public String getStrTypePanoramique() {
```

**Documentation actuelle :**
```java
/**
* @return the typePanoramique
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrTypePanoramique()` - Ligne 318

**Qualité :** Partielle

**Signature :**
```java
public void setStrTypePanoramique(String strTypePanoramique) {
```

**Documentation actuelle :**
```java
/**
* @param strTypePanoramique the typePanoramique to set
*/
```

**Tags présents :** @param

---

### 🟡 `isAfficheTitre()` - Ligne 325

**Qualité :** Partielle

**Signature :**
```java
public boolean isAfficheTitre() {
```

**Documentation actuelle :**
```java
/**
* @return the afficheTitre
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setAfficheTitre()` - Ligne 332

**Qualité :** Partielle

**Signature :**
```java
public void setAfficheTitre(boolean bAfficheTitre) {
```

**Documentation actuelle :**
```java
/**
* @param bAfficheTitre the afficheTitre to set
*/
```

**Tags présents :** @param

---

### 🟡 `isAffDescription()` - Ligne 339

**Qualité :** Partielle

**Signature :**
```java
public boolean isAffDescription() {
```

**Documentation actuelle :**
```java
/**
* @return the affDescription
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setAffDescription()` - Ligne 346

**Qualité :** Partielle

**Signature :**
```java
public void setAffDescription(boolean bAffDescription) {
```

**Documentation actuelle :**
```java
/**
* @param bAffDescription the affDescription to set
*/
```

**Tags présents :** @param

---

### 🟡 `isAfficheInfo()` - Ligne 353

**Qualité :** Partielle

**Signature :**
```java
public boolean isAfficheInfo() {
```

**Documentation actuelle :**
```java
/**
* @return the afficheInfo
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setAfficheInfo()` - Ligne 360

**Qualité :** Partielle

**Signature :**
```java
public void setAfficheInfo(boolean bAfficheInfo) {
```

**Documentation actuelle :**
```java
/**
* @param bAfficheInfo the afficheInfo to set
*/
```

**Tags présents :** @param

---

### 🟡 `getStrTitrePanoramique()` - Ligne 367

**Qualité :** Partielle

**Signature :**
```java
public String getStrTitrePanoramique() {
```

**Documentation actuelle :**
```java
/**
* @return the titrePanoramique
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrTitrePanoramique()` - Ligne 374

**Qualité :** Partielle

**Signature :**
```java
public void setStrTitrePanoramique(String strTitrePanoramique) {
```

**Documentation actuelle :**
```java
/**
* @param strTitrePanoramique the titrePanoramique to set
*/
```

**Tags présents :** @param

---

### 🟡 `getImgVignettePanoramique()` - Ligne 381

**Qualité :** Partielle

**Signature :**
```java
public Image getImgVignettePanoramique() {
```

**Documentation actuelle :**
```java
/**
* @return the vignettePanoramique
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgVignettePanoramique()` - Ligne 388

**Qualité :** Partielle

**Signature :**
```java
public void setImgVignettePanoramique(Image imgVignettePanoramique) {
```

**Documentation actuelle :**
```java
/**
* @param imgVignettePanoramique the vignettePanoramique to set
*/
```

**Tags présents :** @param

---

### 🟡 `getZeroNord()` - Ligne 395

**Qualité :** Partielle

**Signature :**
```java
public double getZeroNord() {
```

**Documentation actuelle :**
```java
/**
* @return the zeroNord
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setZeroNord()` - Ligne 402

**Qualité :** Partielle

**Signature :**
```java
public void setZeroNord(double zeroNord) {
```

**Documentation actuelle :**
```java
/**
* @param zeroNord the zeroNord to set
*/
```

**Tags présents :** @param

---

### 🟡 `getNombreNiveaux()` - Ligne 409

**Qualité :** Partielle

**Signature :**
```java
public double getNombreNiveaux() {
```

**Documentation actuelle :**
```java
/**
* @return the nombreNiveaux
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNombreNiveaux()` - Ligne 416

**Qualité :** Partielle

**Signature :**
```java
public void setNombreNiveaux(double nombreNiveaux) {
```

**Documentation actuelle :**
```java
/**
* @param nombreNiveaux the nombreNiveaux to set
*/
```

**Tags présents :** @param

---

### 🟡 `getNombreHotspotImage()` - Ligne 423

**Qualité :** Partielle

**Signature :**
```java
public int getNombreHotspotImage() {
```

**Documentation actuelle :**
```java
/**
* @return the nombreHotspotImage
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNombreHotspotImage()` - Ligne 430

**Qualité :** Partielle

**Signature :**
```java
public void setNombreHotspotImage(int iNombreHotspotImage) {
```

**Documentation actuelle :**
```java
/**
* @param iNombreHotspotImage the nombreHotspotImage to set
*/
```

**Tags présents :** @param

---

### 🟡 `getNombreHotspotHTML()` - Ligne 437

**Qualité :** Partielle

**Signature :**
```java
public int getNombreHotspotHTML() {
```

**Documentation actuelle :**
```java
/**
* @return the nombreHotspotHTML
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNombreHotspotHTML()` - Ligne 444

**Qualité :** Partielle

**Signature :**
```java
public void setNombreHotspotHTML(int iNombreHotspotHTML) {
```

**Documentation actuelle :**
```java
/**
* @param iNombreHotspotHTML the nombreHotspotHTML to set
*/
```

**Tags présents :** @param

---

### 🟡 `getNumeroPlan()` - Ligne 451

**Qualité :** Partielle

**Signature :**
```java
public int getNumeroPlan() {
```

**Documentation actuelle :**
```java
/**
* @return the numeroPlan
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setNumeroPlan()` - Ligne 458

**Qualité :** Partielle

**Signature :**
```java
public void setNumeroPlan(int iNumeroPlan) {
```

**Documentation actuelle :**
```java
/**
* @param iNumeroPlan the numeroPlan to set
*/
```

**Tags présents :** @param

---

### 🟡 `getImgVisuPanoramique()` - Ligne 465

**Qualité :** Partielle

**Signature :**
```java
public Image getImgVisuPanoramique() {
```

**Documentation actuelle :**
```java
/**
* @return the imgVisuPanoramique
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgVisuPanoramique()` - Ligne 472

**Qualité :** Partielle

**Signature :**
```java
public void setImgVisuPanoramique(Image imgVisuPanoramique) {
```

**Documentation actuelle :**
```java
/**
* @param imgVisuPanoramique the imgVisuPanoramique to set
*/
```

**Tags présents :** @param

---

### 🟡 `getMarqueurGeolocatisation()` - Ligne 479

**Qualité :** Partielle

**Signature :**
```java
public CoordonneesGeographiques getMarqueurGeolocatisation() {
```

**Documentation actuelle :**
```java
/**
* @return the marqueurGeolocatisation
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setMarqueurGeolocatisation()` - Ligne 486

**Qualité :** Partielle

**Signature :**
```java
public void setMarqueurGeolocatisation(CoordonneesGeographiques marqueurGeolo...
```

**Documentation actuelle :**
```java
/**
* @param marqueurGeolocatisation the marqueurGeolocatisation to set
*/
```

**Tags présents :** @param

---

### 🟡 `getChampVisuel()` - Ligne 493

**Qualité :** Partielle

**Signature :**
```java
public double getChampVisuel() {
```

**Documentation actuelle :**
```java
/**
* @return the champVisuel
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setChampVisuel()` - Ligne 500

**Qualité :** Partielle

**Signature :**
```java
public void setChampVisuel(double champVisuel) {
```

**Documentation actuelle :**
```java
/**
* @param champVisuel the champVisuel to set
*/
```

**Tags présents :** @param

---

### 🟡 `getImgPanoRect()` - Ligne 507

**Qualité :** Partielle

**Signature :**
```java
public Image getImgPanoRect() {
```

**Documentation actuelle :**
```java
/**
* @return the imgPanoRect
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgPanoRect()` - Ligne 514

**Qualité :** Partielle

**Signature :**
```java
public void setImgPanoRect(Image imgPanoRect) {
```

**Documentation actuelle :**
```java
/**
* @param imgPanoRect the imgPanoRect to set
*/
```

**Tags présents :** @param

---

### 🟡 `getMinLat()` - Ligne 521

**Qualité :** Partielle

**Signature :**
```java
public double getMinLat() {
```

**Documentation actuelle :**
```java
/**
* @return the minLat
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setMinLat()` - Ligne 528

**Qualité :** Partielle

**Signature :**
```java
public void setMinLat(double minLat) {
```

**Documentation actuelle :**
```java
/**
* @param minLat the minLat to set
*/
```

**Tags présents :** @param

---

### 🟡 `getMaxLat()` - Ligne 535

**Qualité :** Partielle

**Signature :**
```java
public double getMaxLat() {
```

**Documentation actuelle :**
```java
/**
* @return the maxLat
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setMaxLat()` - Ligne 542

**Qualité :** Partielle

**Signature :**
```java
public void setMaxLat(double maxLat) {
```

**Documentation actuelle :**
```java
/**
* @param maxLat the maxLat to set
*/
```

**Tags présents :** @param

---

### 🟡 `isbMinLat()` - Ligne 549

**Qualité :** Partielle

**Signature :**
```java
public boolean isbMinLat() {
```

**Documentation actuelle :**
```java
/**
* @return the bMinLat
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setbMinLat()` - Ligne 556

**Qualité :** Partielle

**Signature :**
```java
public void setbMinLat(boolean bMinLat) {
```

**Documentation actuelle :**
```java
/**
* @param bMinLat the bMinLat to set
*/
```

**Tags présents :** @param

---

### 🟡 `isbMaxLat()` - Ligne 563

**Qualité :** Partielle

**Signature :**
```java
public boolean isbMaxLat() {
```

**Documentation actuelle :**
```java
/**
* @return the bMaxLat
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setbMaxLat()` - Ligne 570

**Qualité :** Partielle

**Signature :**
```java
public void setbMaxLat(boolean bMaxLat) {
```

**Documentation actuelle :**
```java
/**
* @param bMaxLat the bMaxLat to set
*/
```

**Tags présents :** @param

---

### 🟡 `getiNombreHotspotDiapo()` - Ligne 577

**Qualité :** Partielle

**Signature :**
```java
public int getiNombreHotspotDiapo() {
```

**Documentation actuelle :**
```java
/**
* @return the iNombreHotspotDiapo
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setiNombreHotspotDiapo()` - Ligne 584

**Qualité :** Partielle

**Signature :**
```java
public void setiNombreHotspotDiapo(int iNombreHotspotDiapo) {
```

**Documentation actuelle :**
```java
/**
* @param iNombreHotspotDiapo the iNombreHotspotDiapo to set
*/
```

**Tags présents :** @param

---

### 🟡 `getImgPanoRectListe()` - Ligne 591

**Qualité :** Partielle

**Signature :**
```java
public Image getImgPanoRectListe() {
```

**Documentation actuelle :**
```java
/**
* @return the imgPanoRectListe
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgPanoRectListe()` - Ligne 598

**Qualité :** Partielle

**Signature :**
```java
public void setImgPanoRectListe(Image imgPanoRectListe) {
```

**Documentation actuelle :**
```java
/**
* @param imgPanoRectListe the imgPanoRectListe to set
*/
```

**Tags présents :** @param

---

### 🟡 `getImgCubeEqui()` - Ligne 605

**Qualité :** Partielle

**Signature :**
```java
public Image getImgCubeEqui() {
```

**Documentation actuelle :**
```java
/**
* @return the imgCubeEqui
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setImgCubeEqui()` - Ligne 612

**Qualité :** Partielle

**Signature :**
```java
public void setImgCubeEqui(Image imgCubeEqui) {
```

**Documentation actuelle :**
```java
/**
* @param imgCubeEqui the imgCubeEqui to set
*/
```

**Tags présents :** @param

---

### 🟡 `getFovMax()` - Ligne 619

**Qualité :** Partielle

**Signature :**
```java
public double getFovMax() {
```

**Documentation actuelle :**
```java
/**
* @return the fovMax
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setFovMax()` - Ligne 626

**Qualité :** Partielle

**Signature :**
```java
public void setFovMax(double fovMax) {
```

**Documentation actuelle :**
```java
/**
* @param fovMax the fovMax to set
*/
```

**Tags présents :** @param

---

### 🟡 `getFovMin()` - Ligne 633

**Qualité :** Partielle

**Signature :**
```java
public double getFovMin() {
```

**Documentation actuelle :**
```java
/**
* @return the fovMin
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setFovMin()` - Ligne 640

**Qualité :** Partielle

**Signature :**
```java
public void setFovMin(double fovMin) {
```

**Documentation actuelle :**
```java
/**
* @param fovMin the fovMin to set
*/
```

**Tags présents :** @param

---

### 🟡 `getStrDescriptionIA()` - Ligne 647

**Qualité :** Partielle

**Signature :**
```java
public String getStrDescriptionIA() {
```

**Documentation actuelle :**
```java
/**
* @return the strDescriptionIA
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setStrDescriptionIA()` - Ligne 654

**Qualité :** Partielle

**Signature :**
```java
public void setStrDescriptionIA(String strDescriptionIA) {
```

**Documentation actuelle :**
```java
/**
* @param strDescriptionIA the strDescriptionIA to set
*/
```

**Tags présents :** @param

---

