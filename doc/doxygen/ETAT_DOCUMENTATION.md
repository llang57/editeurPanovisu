# 📊 Rapport d'analyse Javadoc - EditeurPanovisu

**Date de génération :** 15/10/2025 14:50:59

## 📈 Statistiques globales

| Élément | Total | Documentés | Non documentés | % Documentés |
|---------|-------|------------|----------------|---------------|
| **Classes** | 54 | 49 | 5 | 90.7% |
| **Méthodes** | 1529 | 1301 | 228 | 85.1% |
| **Propriétés** | 2341 | 36 | 2305 | 1.5% |
| **TOTAL** | 3924 | 1386 | 2538 | **35.3%** |

### 📊 Progression globale

```
[█████████████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░] 35.3%
```

## 🏆 Top 5 - Classes les mieux documentées

🥇 **ApiKeysConfig** : 84.6% (11/13 éléments)
🥈 **NavigateurCarte** : 72.7% (8/11 éléments)
🥉 **NavigateurCarteSeul** : 71.4% (5/7 éléments)
⭐ **BigDecimalField** : 70.6% (12/17 éléments)
⭐ **ImageEditeurHTML** : 66.7% (4/6 éléments)

## ⚠️ Top 5 - Classes nécessitant le plus de documentation

1. **GestionnaireInterfaceController** : 839 éléments non documentés (36.0% complété)
2. **EditeurPanovisu** : 549 éléments non documentés (29.6% complété)
3. **OllamaService** : 86 éléments non documentés (25.9% complété)
4. **GestionnairePlanController** : 85 éléments non documentés (29.8% complété)
5. **NavigateurPanoramique** : 75 éléments non documentés (28.6% complété)

## 📋 Détail par classe

---

### ❌ `AideDialogController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\AideDialogController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 0/5 éléments documentés (0.0%)

#### Propriétés (4)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `stAide` | 33 | ❌ | `private static Stage stAide;` |
| `apAideDialog` | 34 | ❌ | `private static AnchorPane apAideDialog;` |
| `btnAnnuler` | 35 | ❌ | `private static Button btnAnnuler;` |
| `sceneAideDialog` | 36 | ❌ | `private static Scene sceneAideDialog;` |

#### Méthodes (1)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `affiche` | 38 | ❌ | `public static void affiche() {` |

---

### ✅ `ApiKeysConfig`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ApiKeysConfig.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 10/12 éléments documentés (83.3%)

#### Propriétés (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `CONFIG_FILE` | 16 | ❌ | `private static final String CONFIG_FILE = "api-keys.properties";` |
| `properties` | 17 | ❌ | `private static Properties properties = null;` |

#### Méthodes (10)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `loadProperties` | 22 | ✅ | `private static void loadProperties() {` |
| `getLocationIQApiKey` | 51 | ✅ | `public static String getLocationIQApiKey() {` |
| `hasLocationIQApiKey` | 60 | ✅ | `public static boolean hasLocationIQApiKey() {` |
| `getHuggingFaceApiKey` | 69 | ✅ | `public static String getHuggingFaceApiKey() {` |
| `getHuggingFaceModel` | 78 | ✅ | `public static String getHuggingFaceModel() {` |
| `hasHuggingFaceApiKey` | 87 | ✅ | `public static boolean hasHuggingFaceApiKey() {` |
| `getOpenRouterApiKey` | 96 | ✅ | `public static String getOpenRouterApiKey() {` |
| `getOpenRouterModel` | 105 | ✅ | `public static String getOpenRouterModel() {` |
| `hasOpenRouterApiKey` | 114 | ✅ | `public static boolean hasOpenRouterApiKey() {` |
| `getProperty` | 125 | ✅ | `public static String getProperty(String key, String defaultValue) {` |

---

### 🔶 `BigDecimalField`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\BigDecimalField.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 11/16 éléments documentés (68.8%)

#### Propriétés (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `format` | 22 | ❌ | `private final DecimalFormat format;` |
| `minValue` | 23 | ❌ | `private BigDecimal minValue = null;` |
| `maxValue` | 24 | ❌ | `private BigDecimal maxValue = null;` |
| `number` | 110 | ❌ | `return number;` |
| `format` | 161 | ❌ | `return format;` |

#### Méthodes (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `BigDecimalField` | 30 | ✅ | `public BigDecimalField() {` |
| `BigDecimalField` | 38 | ✅ | `public BigDecimalField(DecimalFormat format) {` |
| `BigDecimalField` | 48 | ✅ | `public BigDecimalField(BigDecimal initialValue) {` |
| `setupTextField` | 56 | ✅ | `private void setupTextField() {` |
| `validateAndFormat` | 78 | ✅ | `private void validateAndFormat() {` |
| `numberProperty` | 109 | ✅ | `public ObjectProperty<BigDecimal> numberProperty() {` |
| `getNumber` | 117 | ✅ | `public BigDecimal getNumber() {` |
| `setNumber` | 125 | ✅ | `public void setNumber(BigDecimal value) {` |
| `setMinValue` | 144 | ✅ | `public void setMinValue(BigDecimal minValue) {` |
| `setMaxValue` | 152 | ✅ | `public void setMaxValue(BigDecimal maxValue) {` |
| `getFormat` | 160 | ✅ | `public DecimalFormat getFormat() {` |

---

### ⚠️ `BuildNumberIncrementer`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\BuildNumberIncrementer.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 5/14 éléments documentés (35.7%)

#### Propriétés (8)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `BUILD_NUM_FILE` | 16 | ❌ | `private static final String BUILD_NUM_FILE = "build.num";` |
| `I18N_PROPERTIES_FILE` | 17 | ❌ | `private static final String I18N_PROPERTIES_FILE = "src/editeurpanovisu/i18n/...` |
| `PROJECT_PROPERTIES_FILE` | 18 | ❌ | `private static final String PROJECT_PROPERTIES_FILE = "src/project.properties";` |
| `newBuild` | 29 | ❌ | `int newBuild = currentBuild + 1;` |
| `1990` | 53 | ❌ | `return 1990;` |
| `found` | 98 | ❌ | `boolean found = false;` |
| `found` | 136 | ❌ | `boolean found = false;` |
| `buildStr` | 169 | ❌ | `return buildStr;` |

#### Méthodes (6)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `main` | 20 | ❌ | `public static void main(String[] args) {` |
| `readBuildNumber` | 49 | ✅ | `private static int readBuildNumber() throws IOException {` |
| `updateBuildNumFile` | 68 | ✅ | `private static void updateBuildNumFile(int buildNumber) throws IOException {` |
| `updateI18nProperties` | 87 | ✅ | `private static void updateI18nProperties(int buildNumber) throws IOException {` |
| `updateProjectProperties` | 125 | ✅ | `private static void updateProjectProperties(int buildNumber) throws IOExcepti...` |
| `formatBuildNumber` | 166 | ✅ | `private static String formatBuildNumber(int buildNumber) {` |

---

### 🔶 `CartePanoVisu`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\CartePanoVisu.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 14/24 éléments documentés (58.3%)

#### Propriétés (10)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `centreCarte` | 15 | ❌ | `private CoordonneesGeographiques centreCarte;` |
| `facteurZoom` | 16 | ❌ | `private int facteurZoom;` |
| `iNombreMarqueur` | 17 | ❌ | `private int iNombreMarqueur;` |
| `marqueurs` | 18 | ❌ | `private MarqueurGeolocalisation[] marqueurs = new MarqueurGeolocalisation[100];` |
| `strTypeCarte` | 19 | ❌ | `private String strTypeCarte = "";` |
| `centreCarte` | 62 | ❌ | `return centreCarte;` |
| `facteurZoom` | 76 | ❌ | `return facteurZoom;` |
| `iNombreMarqueur` | 90 | ❌ | `return iNombreMarqueur;` |
| `marqueurs` | 104 | ❌ | `return marqueurs;` |
| `strTypeCarte` | 127 | ❌ | `return strTypeCarte;` |

#### Méthodes (14)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `CartePanoVisu` | 24 | ✅ | `public CartePanoVisu() {` |
| `CartePanoVisu` | 39 | ✅ | `public CartePanoVisu(int iNombreMarqueur, CoordonneesGeographiques coordonnee...` |
| `ajouteMarqueur` | 53 | ✅ | `public void ajouteMarqueur(CoordonneesGeographiques coordonnees, String strXM...` |
| `getCentreCarte` | 61 | ✅ | `public CoordonneesGeographiques getCentreCarte() {` |
| `setCentreCarte` | 68 | ✅ | `public void setCentreCarte(CoordonneesGeographiques centreCarte) {` |
| `getFacteurZoom` | 75 | ✅ | `public int getFacteurZoom() {` |
| `setFacteurZoom` | 82 | ✅ | `public void setFacteurZoom(int facteurZoom) {` |
| `getiNombreMarqueur` | 89 | ✅ | `public int getiNombreMarqueur() {` |
| `setiNombreMarqueur` | 96 | ✅ | `public void setiNombreMarqueur(int iNombreMarqueur) {` |
| `getMarqueurs` | 103 | ✅ | `public MarqueurGeolocalisation[] getMarqueurs() {` |
| `setMarqueurs` | 110 | ✅ | `public void setMarqueurs(MarqueurGeolocalisation[] marqueurs) {` |
| `setMarqueursI` | 119 | ✅ | `public void setMarqueursI(int i, MarqueurGeolocalisation marqueurs) {` |
| `getStrTypeCarte` | 126 | ✅ | `public String getStrTypeCarte() {` |
| `setStrTypeCarte` | 133 | ✅ | `public void setStrTypeCarte(String strTypeCarte) {` |

---

### ⚠️ `ConfigDialogController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ConfigDialogController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 6/27 éléments documentés (22.2%)

#### Propriétés (17)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `stConfigDialog` | 46 | ❌ | `private static Stage stConfigDialog;` |
| `apConfigDialog` | 47 | ❌ | `private static AnchorPane apConfigDialog;` |
| `btnAnnuler` | 48 | ❌ | `private static Button btnAnnuler;` |
| `btnSauvegarder` | 49 | ❌ | `private static Button btnSauvegarder;` |
| `cbListeLangues` | 53 | ❌ | `private static ComboBox cbListeLangues;` |
| `tfRepert` | 54 | ❌ | `private static TextField tfRepert;` |
| `tfLocationIQKey` | 55 | ❌ | `private static TextField tfLocationIQKey;` |
| `tfHuggingFaceKey` | 56 | ❌ | `private static TextField tfHuggingFaceKey;` |
| `tfOpenRouterKey` | 57 | ❌ | `private static TextField tfOpenRouterKey;` |
| `cbOpenRouterModel` | 58 | ❌ | `private static ComboBox<String> cbOpenRouterModel;` |
| `cbOllamaModel` | 59 | ❌ | `private static ComboBox<String> cbOllamaModel;` |
| `iCodeL` | 67 | ❌ | `int iCodeL = 0;` |
| `oswFichierConfig` | 267 | ❌ | `OutputStreamWriter oswFichierConfig = null;` |
| `props` | 329 | ❌ | `return props;` |
| `line` | 378 | ❌ | `String line;` |
| `modelName` | 419 | ❌ | `return modelName;` |
| `displayName` | 466 | ❌ | `return displayName;` |

#### Méthodes (10)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `afficheFenetre` | 65 | ✅ | `public void afficheFenetre() throws IOException {` |
| `loadApiKeys` | 313 | ✅ | `private Properties loadApiKeys() {` |
| `saveApiKeys` | 338 | ✅ | `private void saveApiKeys(String locationIQKey, String huggingFaceKey, String ...` |
| `saveModelsPreferences` | 369 | ✅ | `private void saveModelsPreferences() throws IOException {` |
| `InputStreamReader` | 377 | ❌ | `new InputStreamReader(new FileInputStream(filePreferences), "UTF-8"))) {` |
| `FileOutputStream` | 402 | ❌ | `new FileOutputStream(filePreferences), "UTF-8");` |
| `ajouterEmojiModele` | 417 | ✅ | `private static String ajouterEmojiModele(String modelName) {` |
| `if` | 443 | ❌ | `else if (modelName.contains("deepseek-r1")) {` |
| `extraireNomModele` | 464 | ✅ | `private static String extraireNomModele(String displayName) {` |
| `if` | 490 | ❌ | `else if (displayName.contains("DeepSeek-R1")) {` |

---

### ⚠️ `CoordonneesGeographiques`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\CoordonneesGeographiques.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 4/11 éléments documentés (36.4%)

#### Propriétés (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `longitude` | 14 | ❌ | `private double latitude, longitude;` |
| `latitude` | 30 | ❌ | `return latitude;` |
| `longitude` | 44 | ❌ | `return longitude;` |
| `degreDecimal` | 59 | ❌ | `return degreDecimal;` |
| `signe` | 63 | ❌ | `String signe = "";` |

#### Méthodes (6)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getLatitude` | 29 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 36 | ✅ | `public void setLatitude(double latitude) {` |
| `getLongitude` | 43 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 50 | ✅ | `public void setLongitude(double longitude) {` |
| `fromDMS` | 54 | ❌ | `public static double fromDMS(String degDMS) {` |
| `toDMS` | 62 | ❌ | `public static String toDMS(double degDecimal) {` |

---

### 🔶 `Diaporama`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\Diaporama.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 26/52 éléments documentés (50.0%)

#### Propriétés (24)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `strNomDiaporama` | 14 | ❌ | `private String strNomDiaporama = "";` |
| `strFichierDiaporama` | 15 | ❌ | `private String strFichierDiaporama = "";` |
| `strFichiersImage` | 16 | ❌ | `private String[] strFichiersImage = new String[100];` |
| `strFichiers` | 17 | ❌ | `private String[] strFichiers = new String[100];` |
| `strLibellesImages` | 18 | ❌ | `private String[] strLibellesImages = new String[100];` |
| `delaiDiaporama` | 19 | ❌ | `private double delaiDiaporama = 5;` |
| `strCouleurFondDiaporama` | 20 | ❌ | `private String strCouleurFondDiaporama = "#333333";` |
| `opaciteDiaporama` | 21 | ❌ | `private double opaciteDiaporama = 0.8;` |
| `iNombreImages` | 22 | ❌ | `private int iNombreImages = 0;` |
| `iOrdreDiaporama` | 23 | ❌ | `private int[] iOrdreDiaporama = new int[100];` |
| `strSortie` | 27 | ❌ | `String strSortie = "";` |
| `strSortie` | 40 | ❌ | `return strSortie;` |
| `strNomDiaporama` | 47 | ❌ | `return strNomDiaporama;` |
| `strFichiersImage` | 61 | ❌ | `return strFichiersImage;` |
| `objet` | 65 | ❌ | `Object objet = null;` |
| `objet` | 71 | ❌ | `return objet;` |
| `strLibellesImages` | 101 | ❌ | `return strLibellesImages;` |
| `delaiDiaporama` | 131 | ❌ | `return delaiDiaporama;` |
| `strCouleurFondDiaporama` | 145 | ❌ | `return strCouleurFondDiaporama;` |
| `opaciteDiaporama` | 159 | ❌ | `return opaciteDiaporama;` |
| `iNombreImages` | 173 | ❌ | `return iNombreImages;` |
| `iOrdreDiaporama` | 187 | ❌ | `return iOrdreDiaporama;` |
| `strFichierDiaporama` | 201 | ❌ | `return strFichierDiaporama;` |
| `strFichiers` | 215 | ❌ | `return strFichiers;` |

#### Méthodes (28)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `toString` | 26 | ❌ | `public String toString() {` |
| `getStrNomDiaporama` | 46 | ✅ | `public String getStrNomDiaporama() {` |
| `setStrNomDiaporama` | 53 | ✅ | `public void setStrNomDiaporama(String nomDiaporama) {` |
| `getStrFichiersImage` | 60 | ✅ | `public String[] getStrFichiersImage() {` |
| `clone` | 64 | ❌ | `public Object clone() {` |
| `getStrFichiersImage` | 78 | ✅ | `public String getStrFichiersImage(int i) {` |
| `setStrFichiersImage` | 85 | ✅ | `public void setStrFichiersImage(String[] strImages) {` |
| `setStrFichiersImage` | 93 | ✅ | `public void setStrFichiersImage(String strImages, int i) {` |
| `getStrLibellesImages` | 100 | ✅ | `public String[] getStrLibellesImages() {` |
| `getStrLibellesImages` | 108 | ✅ | `public String getStrLibellesImages(int i) {` |
| `setStrLibellesImages` | 115 | ✅ | `public void setStrLibellesImages(String[] strLibellesImages) {` |
| `setStrLibellesImages` | 123 | ✅ | `public void setStrLibellesImages(String strLibellesImages, int i) {` |
| `getDelaiDiaporama` | 130 | ✅ | `public double getDelaiDiaporama() {` |
| `setDelaiDiaporama` | 137 | ✅ | `public void setDelaiDiaporama(double delaiDiaporama) {` |
| `getStrCouleurFondDiaporama` | 144 | ✅ | `public String getStrCouleurFondDiaporama() {` |
| `setStrCouleurFondDiaporama` | 151 | ✅ | `public void setStrCouleurFondDiaporama(String strCouleurFondDiaporama) {` |
| `getOpaciteDiaporama` | 158 | ✅ | `public double getOpaciteDiaporama() {` |
| `setOpaciteDiaporama` | 165 | ✅ | `public void setOpaciteDiaporama(double opaciteDiaporama) {` |
| `getiNombreImages` | 172 | ✅ | `public int getiNombreImages() {` |
| `setiNombreImages` | 179 | ✅ | `public void setiNombreImages(int iNombreImages) {` |
| `getiOrdreDiaporama` | 186 | ✅ | `public int[] getiOrdreDiaporama() {` |
| `setiOrdreDiaporama` | 193 | ✅ | `public void setiOrdreDiaporama(int[] iOrdreDiaporama) {` |
| `getStrFichierDiaporama` | 200 | ✅ | `public String getStrFichierDiaporama() {` |
| `setStrFichierDiaporama` | 207 | ✅ | `public void setStrFichierDiaporama(String strFichierDiaporama) {` |
| `getStrFichiers` | 214 | ✅ | `public String[] getStrFichiers() {` |
| `getStrFichiers` | 222 | ✅ | `public String getStrFichiers(int i) {` |
| `setStrFichiers` | 229 | ✅ | `public void setStrFichiers(String[] strFichiers) {` |
| `setStrFichiers` | 237 | ✅ | `public void setStrFichiers(String strFichiers, int i) {` |

---

### ⚠️ `DocumentationDialog`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\DocumentationDialog.java`

**Documentation de la classe :** ✅ Oui (3 lignes)

**Classes internes :** DocumentType, DocumentItem, TestApp

**Progression :** 7/27 éléments documentés (25.9%)

#### Propriétés (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `webView` | 22 | ❌ | `private final WebView webView;` |
| `markdownViewer` | 23 | ❌ | `private final MarkdownViewer markdownViewer;` |
| `documentSelector` | 24 | ❌ | `private final ComboBox<DocumentItem> documentSelector;` |
| `bundle` | 25 | ❌ | `private final ResourceBundle bundle;` |
| `titre` | 35 | ❌ | `private final String titre;` |
| `chemin` | 36 | ❌ | `private final String chemin;` |
| `titre` | 44 | ❌ | `return titre;` |
| `chemin` | 48 | ❌ | `return chemin;` |
| `type` | 56 | ❌ | `private final DocumentType type;` |
| `type` | 68 | ❌ | `return type;` |
| `markdown` | 175 | ❌ | `String markdown = "# ⚠️ Erreur\n\n" + message;` |

#### Méthodes (16)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getTitre` | 43 | ❌ | `public String getTitre() {` |
| `getChemin` | 47 | ❌ | `public String getChemin() {` |
| `DocumentItem` | 58 | ❌ | `public DocumentItem(DocumentType type) {` |
| `toString` | 63 | ❌ | `public String toString() {` |
| `getType` | 67 | ❌ | `public DocumentType getType() {` |
| `DocumentationDialog` | 75 | ✅ | `public DocumentationDialog() {` |
| `Label` | 97 | ❌ | `new Label("Document : "),` |
| `Separator` | 99 | ❌ | `new Separator(),` |
| `chargerDocumentSelectionne` | 125 | ✅ | `private void chargerDocumentSelectionne() {` |
| `chargerDocument` | 137 | ✅ | `public void chargerDocument(DocumentType type) {` |
| `ouvrirFichierMarkdown` | 153 | ✅ | `private void ouvrirFichierMarkdown() {` |
| `afficherErreur` | 174 | ✅ | `private void afficherErreur(String message) {` |
| `afficher` | 182 | ✅ | `public static void afficher() {` |
| `afficher` | 190 | ✅ | `public static void afficher(DocumentType type) {` |
| `start` | 201 | ❌ | `public void start(Stage primaryStage) {` |
| `main` | 211 | ❌ | `public static void main(String[] args) {` |

---

### ❌ `EditeurHTML`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\EditeurHTML.java`

**Documentation de la classe :** ❌ Non

**Progression :** 8/69 éléments documentés (11.6%)

#### Propriétés (51)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `bValide` | 83 | ❌ | `private boolean bValide = false;` |
| `bAnnule` | 84 | ❌ | `private boolean bAnnule = false;` |
| `hsHTML` | 86 | ❌ | `private HotspotHTML hsHTML;` |
| `tgPositionHTML` | 87 | ❌ | `private ToggleGroup tgPositionHTML;` |
| `tgbGauche` | 88 | ❌ | `private ToggleButton tgbGauche;` |
| `tgbCentre` | 89 | ❌ | `private ToggleButton tgbCentre;` |
| `tgbDroite` | 90 | ❌ | `private ToggleButton tgbDroite;` |
| `strNomRepertHTML` | 95 | ❌ | `private String strNomRepertHTML = strAppPath + "/pagesHTML";` |
| `stEditeurHTML` | 96 | ❌ | `private Stage stEditeurHTML;` |
| `strImages` | 97 | ❌ | `private String strImages = "";` |
| `heEditeurHTML` | 98 | ❌ | `private HTMLEditor heEditeurHTML;` |
| `nodeBarreIconesSuperieure` | 99 | ❌ | `private Node nodeBarreIconesSuperieure;` |
| `nodeEditeurHTML` | 100 | ❌ | `private Node nodeEditeurHTML;` |
| `tbBarreIconesSuperieure` | 101 | ❌ | `private ToolBar tbBarreIconesSuperieure;` |
| `wvEditeurHTML` | 102 | ❌ | `private WebView wvEditeurHTML;` |
| `engEditeurHTML` | 103 | ❌ | `private WebEngine engEditeurHTML;` |
| `btnValide` | 104 | ❌ | `private Button btnValide;` |
| `btnAnnule` | 105 | ❌ | `private Button btnAnnule;` |
| `btnAjouteImage` | 106 | ❌ | `private Button btnAjouteImage;` |
| `btnAjouteLien` | 107 | ❌ | `private Button btnAjouteLien;` |
| `apDialog` | 108 | ❌ | `private AnchorPane apDialog;` |
| `apEditeur` | 109 | ❌ | `private AnchorPane apEditeur;` |
| `apPrincipale` | 110 | ❌ | `private AnchorPane apPrincipale;` |
| `strNomFichierImage` | 111 | ❌ | `private String strNomFichierImage = "";` |
| `bDejaSauve` | 112 | ❌ | `private boolean bDejaSauve = true;` |
| `cpCouleurHTML` | 113 | ❌ | `private ColorPicker cpCouleurHTML;` |
| `cpCouleurFond` | 114 | ❌ | `private ColorPicker cpCouleurFond;` |
| `cpCouleurTexte` | 115 | ❌ | `private ColorPicker cpCouleurTexte;` |
| `strCouleurFond` | 116 | ❌ | `public String strCouleurFond = "#FFFFFF";` |
| `strCouleurTexte` | 117 | ❌ | `public String strCouleurTexte = "#000000";` |
| `null` | 164 | ❌ | `return null;` |
| `sz` | 166 | ❌ | `int sz;` |
| `strTemplate` | 244 | ❌ | `String strTemplate = "";` |
| `strTemplate` | 245 | ❌ | `return strTemplate;` |
| `diffHauteur` | 249 | ❌ | `double diffHauteur = 290;` |
| `strCoul` | 259 | ❌ | `String strCoul = "#000000";` |
| `i` | 450 | ❌ | `int i = 0;` |
| `strTrouve` | 514 | ❌ | `String strTrouve = "body\\{background-color : \\#......\\;\\}";` |
| `strTrouve` | 526 | ❌ | `String strTrouve = "body\\{color : \\#......\\;\\}";` |
| `iLargeur` | 699 | ❌ | `int iLargeur = 400;` |
| `iHauteur` | 700 | ❌ | `int iHauteur = 200;` |
| `iHauteur` | 779 | ❌ | `int iHauteur = 700;` |
| `iLargeur` | 780 | ❌ | `int iLargeur = 900;` |
| `fileProjet` | 877 | ❌ | `File fileProjet = null;` |
| `bValide` | 956 | ❌ | `return bValide;` |
| `ancienneValeur` | 963 | ❌ | `boolean ancienneValeur = this.bValide;` |
| `nouvelleValeur` | 965 | ❌ | `boolean nouvelleValeur = this.bValide;` |
| `bAnnule` | 973 | ❌ | `return bAnnule;` |
| `ancienneValeur` | 980 | ❌ | `boolean ancienneValeur = this.bAnnule;` |
| `nouvelleValeur` | 982 | ❌ | `boolean nouvelleValeur = this.bAnnule;` |
| `hsHTML` | 990 | ❌ | `return hsHTML;` |

#### Méthodes (18)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `addPropertyChangeListener` | 65 | ❌ | `public void addPropertyChangeListener(String propertyName, PropertyChangeList...` |
| `removePropertyChangeListener` | 69 | ❌ | `public void removePropertyChangeListener(String propertyName, PropertyChangeL...` |
| `firePropertyChange` | 73 | ❌ | `public void firePropertyChange(String propertyName, BigDecimal oldValue, BigD...` |
| `hex` | 149 | ✅ | `private static String hex(int i) {` |
| `escapeJavaStyleString` | 160 | ✅ | `private static String escapeJavaStyleString(String strEntree,` |
| `copieFichierRepertoire` | 237 | ❌ | `static public void copieFichierRepertoire(String strFichier, String strRepert...` |
| `setTemplate` | 243 | ❌ | `public String setTemplate() {` |
| `affiche` | 248 | ❌ | `public void affiche(Number largeur, Number hauteur) {` |
| `quitteEditeurHTML` | 687 | ❌ | `private void quitteEditeurHTML(Stage stFenetre) {` |
| `afficheConfigLien` | 692 | ❌ | `private void afficheConfigLien() {` |
| `afficheConfigImage` | 778 | ❌ | `private void afficheConfigImage() {` |
| `hideImageNodesMatching` | 932 | ❌ | `public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int d...` |
| `isbValide` | 954 | ✅ | `public boolean isbValide() {` |
| `setbValide` | 962 | ✅ | `public void setbValide(boolean bValide) {` |
| `isbAnnule` | 972 | ✅ | `public boolean isbAnnule() {` |
| `setbAnnule` | 979 | ✅ | `public void setbAnnule(boolean bAnnule) {` |
| `getHsHTML` | 989 | ✅ | `public HotspotHTML getHsHTML() {` |
| `setHsHTML` | 996 | ✅ | `public void setHsHTML(HotspotHTML hsHTML) {` |

---

### ⚠️ `EditeurPanovisu`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\EditeurPanovisu.java`

**Documentation de la classe :** ✅ Oui (22 lignes)

**Classes internes :** AncreForme, Delta

**Progression :** 230/779 éléments documentés (29.5%)

#### Propriétés (539)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `true` | 175 | ❌ | `return true;` |
| `false` | 179 | ❌ | `return false;` |
| `strStyleCSS` | 187 | ❌ | `return strStyleCSS;` |
| `strCodesLanguesTraduction` | 205 | ❌ | `return strCodesLanguesTraduction;` |
| `strLanguesTraduction` | 212 | ❌ | `return strLanguesTraduction;` |
| `strCurrentDir` | 219 | ❌ | `return strCurrentDir;` |
| `locale` | 233 | ❌ | `return locale;` |
| `iNumHTML` | 247 | ❌ | `return iNumHTML;` |
| `panoramiquesProjet` | 261 | ❌ | `return panoramiquesProjet;` |
| `plans` | 275 | ❌ | `return plans;` |
| `iNombrePanoramiques` | 289 | ❌ | `return iNombrePanoramiques;` |
| `iNombrePanoramiquesFichier` | 303 | ❌ | `return iNombrePanoramiquesFichier;` |
| `iNombrePlans` | 317 | ❌ | `return iNombrePlans;` |
| `iPanoActuel` | 331 | ❌ | `return iPanoActuel;` |
| `strPanoListeVignette` | 345 | ❌ | `return strPanoListeVignette;` |
| `tabPlan` | 359 | ❌ | `return tabPlan;` |
| `strSystemeExploitation` | 373 | ❌ | `return strSystemeExploitation;` |
| `strTooltipStyle` | 414 | ❌ | `return strTooltipStyle;` |
| `strRepertAppli` | 421 | ❌ | `return strRepertAppli;` |
| `strRepertTemp` | 542 | ❌ | `return strRepertTemp;` |
| `strRepertPanos` | 556 | ❌ | `return strRepertPanos;` |
| `strRepertHSImages` | 570 | ❌ | `return strRepertHSImages;` |
| `strRepertoireProjet` | 584 | ❌ | `return strRepertoireProjet;` |
| `strDernierRepertoireVisite` | 598 | ❌ | `return strDernierRepertoireVisite;` |
| `bDejaSauve` | 612 | ❌ | `return bDejaSauve;` |
| `stPrincipal` | 626 | ❌ | `return stPrincipal;` |
| `gestionnaireInterface` | 640 | ❌ | `return gestionnaireInterface;` |
| `gestionnairePlan` | 654 | ❌ | `return gestionnairePlan;` |
| `mniAjouterPlan` | 668 | ❌ | `return mniAjouterPlan;` |
| `mniAffichagePlan` | 682 | ❌ | `return mniAffichagePlan;` |
| `ivAjouterPlan` | 696 | ❌ | `return ivAjouterPlan;` |
| `tabInterface` | 710 | ❌ | `return tabInterface;` |
| `apAttends` | 724 | ❌ | `return apAttends;` |
| `strBingAPIKey` | 738 | ❌ | `return strBingAPIKey;` |
| `bAutoRotationDemarre` | 752 | ❌ | `return bAutoRotationDemarre;` |
| `iAutoRotationVitesse` | 766 | ❌ | `return iAutoRotationVitesse;` |
| `bAutoTourDemarre` | 780 | ❌ | `return bAutoTourDemarre;` |
| `iAutoTourLimite` | 794 | ❌ | `return iAutoTourLimite;` |
| `strAutoTourType` | 808 | ❌ | `return strAutoTourType;` |
| `bPetitePlaneteDemarrage` | 822 | ❌ | `return bPetitePlaneteDemarrage;` |
| `bIntroPetitePlanete` | 836 | ❌ | `return bIntroPetitePlanete;` |
| `poGeolocalisation` | 850 | ❌ | `return poGeolocalisation;` |
| `bInternet` | 864 | ❌ | `return bInternet;` |
| `iDecalageMac` | 878 | ❌ | `return iDecalageMac;` |
| `vbChoixPanoramique` | 892 | ❌ | `return vbChoixPanoramique;` |
| `apPVIS` | 906 | ❌ | `return apPVIS;` |
| `apAR` | 920 | ❌ | `return apAR;` |
| `apPPAN` | 934 | ❌ | `return apPPAN;` |
| `apDESCIA` | 948 | ❌ | `return apDESCIA;` |
| `apGEO` | 962 | ❌ | `return apGEO;` |
| `apVISU` | 976 | ❌ | `return apVISU;` |
| `apHS` | 990 | ❌ | `return apHS;` |
| `iAutoTourDemarrage` | 1004 | ❌ | `return iAutoTourDemarrage;` |
| `scnPrincipale` | 1018 | ❌ | `return scnPrincipale;` |
| `iNumDiapo` | 1032 | ❌ | `return iNumDiapo;` |
| `iNumImages` | 1046 | ❌ | `return iNumImages;` |
| `iNumPoints` | 1060 | ❌ | `return iNumPoints;` |
| `iNombreDiapo` | 1074 | ❌ | `return iNombreDiapo;` |
| `afficheLoupe` | 1088 | ❌ | `return afficheLoupe;` |
| `iTailleLoupe` | 1102 | ❌ | `return iTailleLoupe;` |
| `strTypeFichierTransf` | 1122 | ❌ | `return strTypeFichierTransf;` |
| `largeurE2C` | 1136 | ❌ | `return largeurE2C;` |
| `hauteurE2C` | 1150 | ❌ | `return hauteurE2C;` |
| `bNetteteTransf` | 1164 | ❌ | `return bNetteteTransf;` |
| `niveauNetteteTransf` | 1178 | ❌ | `return niveauNetteteTransf;` |
| `dpY` | 1194 | ❌ | `private final DoubleProperty dpX, dpY;` |
| `y` | 1263 | ❌ | `double x, y;` |
| `vbRacine` | 1267 | ❌ | `private static VBox vbRacine;` |
| `apPanovisu` | 1268 | ❌ | `private static AnchorPane apPanovisu;` |
| `largeurOutils` | 1269 | ❌ | `final public static double largeurOutils = 380d;` |
| `iTailleLoupe` | 1272 | ❌ | `private static int iTailleLoupe = 140;` |
| `iTailleLoupeMin` | 1273 | ❌ | `private static final int iTailleLoupeMin = 100;` |
| `iTailleLoupeMax` | 1274 | ❌ | `private static final int iTailleLoupeMax = 200;` |
| `strPositLoupe` | 1275 | ❌ | `private static String strPositLoupe = "gauche";` |
| `afficheLoupe` | 1276 | ❌ | `private static boolean afficheLoupe;` |
| `positLoupeX` | 1277 | ❌ | `private static double positLoupeX;` |
| `positLoupeY` | 1278 | ❌ | `private static double positLoupeY;` |
| `strTypeFichierTransf` | 1279 | ❌ | `private static String strTypeFichierTransf = "jpg";` |
| `largeurE2C` | 1280 | ❌ | `private static double largeurE2C = 650;` |
| `hauteurE2C` | 1281 | ❌ | `private static double hauteurE2C = 420;` |
| `bNetteteTransf` | 1282 | ❌ | `private static boolean bNetteteTransf = false;` |
| `niveauNetteteTransf` | 1283 | ❌ | `private static double niveauNetteteTransf = 0.2;` |
| `iNumeroPanoChoisitHS` | 1284 | ❌ | `private static int iNumeroPanoChoisitHS;` |
| `strNomPanoChoisitHS` | 1285 | ❌ | `private static String strNomPanoChoisitHS;` |
| `iDecalageMac` | 1286 | ❌ | `private static int iDecalageMac = 0;` |
| `cbAutoRotationDemarrage` | 1287 | ❌ | `private static CheckBox cbAutoRotationDemarrage;` |
| `cbIntroPetitePlanete` | 1288 | ❌ | `private static CheckBox cbIntroPetitePlanete;` |
| `bdfAutoRotationVitesse` | 1289 | ❌ | `private static BigDecimalField bdfAutoRotationVitesse;` |
| `cbAutoTourDemarrage` | 1290 | ❌ | `private static CheckBox cbAutoTourDemarrage;` |
| `slMinLat` | 1291 | ❌ | `private static Slider slMinLat;` |
| `slMaxLat` | 1292 | ❌ | `private static Slider slMaxLat;` |
| `slMinFov` | 1293 | ❌ | `private static Slider slMinFov;` |
| `slMaxFov` | 1294 | ❌ | `private static Slider slMaxFov;` |
| `cbMinLat` | 1295 | ❌ | `private static CheckBox cbMinLat;` |
| `cbMaxLat` | 1296 | ❌ | `private static CheckBox cbMaxLat;` |
| `ligNadir` | 1297 | ❌ | `private static Line ligNadir;` |
| `ligZenith` | 1298 | ❌ | `private static Line ligZenith;` |
| `diaporamas` | 1299 | ❌ | `public static Diaporama[] diaporamas = new Diaporama[100];` |
| `iNombreDiapo` | 1300 | ❌ | `private static int iNombreDiapo = 0;` |
| `bdfAutoTourLimite` | 1301 | ❌ | `private static BigDecimalField bdfAutoTourLimite;` |
| `bdfAutoTourDemarrage` | 1302 | ❌ | `private static BigDecimalField bdfAutoTourDemarrage;` |
| `cbAutoTourType` | 1304 | ❌ | `private static ComboBox cbAutoTourType;` |
| `cbAutoRotationVitesse` | 1306 | ❌ | `private static ComboBox cbAutoRotationVitesse;` |
| `bIntroPetitePlanete` | 1307 | ❌ | `static private boolean bIntroPetitePlanete = false;` |
| `bAutoRotationDemarre` | 1308 | ❌ | `static private boolean bAutoRotationDemarre = false;` |
| `iAutoRotationVitesse` | 1309 | ❌ | `static private int iAutoRotationVitesse = 30;` |
| `bAutoTourDemarre` | 1310 | ❌ | `static private boolean bAutoTourDemarre = false;` |
| `iAutoTourLimite` | 1311 | ❌ | `static private int iAutoTourLimite = 1;` |
| `iAutoTourDemarrage` | 1312 | ❌ | `private static int iAutoTourDemarrage = 0;` |
| `strAutoTourType` | 1313 | ❌ | `static private String strAutoTourType = "tours";` |
| `bPetitePlaneteDemarrage` | 1314 | ❌ | `static private boolean bPetitePlaneteDemarrage = true;` |
| `poGeolocalisation` | 1315 | ❌ | `static private PaneOutil poGeolocalisation;` |
| `strStyleCSS` | 1333 | ✅ | `public static String strStyleCSS = "clair";` |
| `strCodesLanguesTraduction` | 1341 | ✅ | `private static final String[] strCodesLanguesTraduction = {"fr_FR", "en_EN", ...` |
| `strLanguesTraduction` | 1348 | ✅ | `private static final String[] strLanguesTraduction = {"Français", "English", ...` |
| `strCurrentDir` | 1355 | ✅ | `private static String strCurrentDir = "";` |
| `rbLocalisation` | 1373 | ✅ | `private static ResourceBundle rbLocalisation;` |
| `lblDragDrop` | 1374 | ❌ | `private static Label lblDragDrop;` |
| `popUp` | 1375 | ❌ | `static private PopUpDialogController popUp;` |
| `ivImagePanoramique` | 1376 | ❌ | `static private ImageView ivImagePanoramique;` |
| `apVisuPano` | 1377 | ❌ | `private static AnchorPane apVisuPano;` |
| `imgPanoRetaille2` | 1378 | ❌ | `private static Image imgPanoRetaille2;` |
| `lblLat` | 1379 | ❌ | `static private Label lblLong, lblLat;` |
| `apPanneauPrincipal` | 1380 | ❌ | `static private AnchorPane apPanneauPrincipal;` |
| `apListeImagesPanoramiques` | 1381 | ❌ | `static private AnchorPane apListeImagesPanoramiques;` |
| `apVignettesPano` | 1382 | ❌ | `static private AnchorPane apVignettesPano;` |
| `rectVignettePano` | 1383 | ❌ | `static private Rectangle rectVignettePano;` |
| `iLargeurVignettes` | 1384 | ❌ | `final static private int iLargeurVignettes = 180;` |
| `hbCoordonnees` | 1385 | ❌ | `static private HBox hbCoordonnees;` |
| `iNumPoints` | 1386 | ❌ | `static private int iNumPoints = 0;` |
| `iNumImages` | 1387 | ❌ | `static private int iNumImages = 0;` |
| `dejaCharge` | 1388 | ❌ | `static private boolean dejaCharge = false;` |
| `iNumHTML` | 1399 | ✅ | `private static int iNumHTML = 0;` |
| `iNumDiapo` | 1406 | ✅ | `private static int iNumDiapo = 0;` |
| `panoramiquesProjet` | 1417 | ✅ | `private static Panoramique[] panoramiquesProjet = new Panoramique[100];` |
| `plans` | 1427 | ✅ | `private static Plan[] plans = new Plan[20];` |
| `iNombrePanoramiques` | 1435 | ✅ | `private static int iNombrePanoramiques = 0;` |
| `iNombrePanoramiquesFichier` | 1442 | ✅ | `private static int iNombrePanoramiquesFichier = 0;` |
| `pbarAvanceChargement` | 1443 | ❌ | `static private ProgressBar pbarAvanceChargement;` |
| `hbBarreBouton` | 1446 | ❌ | `static public HBox hbBarreBouton;` |
| `tpEnvironnement` | 1447 | ❌ | `static public TabPane tpEnvironnement;` |
| `apEnvironnement` | 1448 | ❌ | `static public AnchorPane apEnvironnement;` |
| `iNombrePlans` | 1449 | ❌ | `private static int iNombrePlans = 0;` |
| `iPanoActuel` | 1450 | ❌ | `private static int iPanoActuel = 0;` |
| `fileProjet` | 1451 | ❌ | `static private File fileProjet;` |
| `strPanoListeVignette` | 1452 | ❌ | `private static String strPanoListeVignette = "";` |
| `panePanoramique` | 1453 | ❌ | `static private Pane panePanoramique;` |
| `vbChoixPanoramique` | 1454 | ❌ | `private static VBox vbChoixPanoramique;` |
| `vbOutils` | 1455 | ❌ | `static private VBox vbOutils;` |
| `tabVisite` | 1456 | ❌ | `static private Tab tabVisite;` |
| `apAttends` | 1457 | ❌ | `static private AnchorPane apAttends;` |
| `lblCharge` | 1458 | ❌ | `static private Label lblCharge;` |
| `lblNiveaux` | 1459 | ❌ | `static private Label lblNiveaux;` |
| `tabInterface` | 1460 | ❌ | `static private Tab tabInterface;` |
| `tabPlan` | 1461 | ❌ | `private static Tab tabPlan;` |
| `scnPrincipale` | 1462 | ❌ | `private static Scene scnPrincipale;` |
| `spVuePanoramique` | 1464 | ❌ | `static private ScrollPane spVuePanoramique;` |
| `spPanneauOutils` | 1465 | ❌ | `static private ScrollPane spPanneauOutils;` |
| `largeurMax` | 1466 | ❌ | `static private double largeurMax;` |
| `bEstCharge` | 1467 | ❌ | `static private boolean bEstCharge = false;` |
| `bRepertSauveChoisi` | 1468 | ❌ | `static private boolean bRepertSauveChoisi = false;` |
| `strPanoEntree` | 1469 | ❌ | `static private String strPanoEntree = "";` |
| `strSystemeExploitation` | 1470 | ❌ | `private static String strSystemeExploitation = "";` |
| `strTooltipStyle` | 1471 | ❌ | `private static final String strTooltipStyle = "";` |
| `bDragDrop` | 1472 | ❌ | `static private boolean bDragDrop = false;` |
| `strTitreVisite` | 1473 | ❌ | `static private String strTitreVisite = "";` |
| `apParametresVisite` | 1475 | ❌ | `private static AnchorPane apParametresVisite;` |
| `navigateurPanoramique` | 1476 | ❌ | `private static NavigateurPanoramique navigateurPanoramique;` |
| `strBingAPIKey` | 1477 | ❌ | `private static String strBingAPIKey = "";` |
| `vbVisuHotspots` | 1478 | ❌ | `private static VBox vbVisuHotspots;` |
| `apVisuHS` | 1479 | ❌ | `private static AnchorPane apVisuHS;` |
| `bInternet` | 1480 | ❌ | `private static boolean bInternet;` |
| `apOpenLayers` | 1481 | ❌ | `private static AnchorPane apOpenLayers;` |
| `apHS1` | 1482 | ❌ | `private static PaneOutil apHS1;` |
| `apCreationBarre` | 1486 | ❌ | `private static AnchorPane apCreationBarre;` |
| `apCreationDiaporama` | 1487 | ❌ | `private static AnchorPane apCreationDiaporama;` |
| `apImgBarrePersonnalisee` | 1488 | ❌ | `static private AnchorPane apImgBarrePersonnalisee;` |
| `apZoneBarrePersonnalisee` | 1489 | ❌ | `static private AnchorPane apZoneBarrePersonnalisee;` |
| `btnAjouteZone` | 1490 | ❌ | `private static Button btnAjouteZone;` |
| `imgBarrePersonnalisee` | 1491 | ❌ | `static private Image imgBarrePersonnalisee;` |
| `bRecommenceZone` | 1492 | ❌ | `private static boolean bRecommenceZone = false;` |
| `iNombreZones` | 1493 | ❌ | `static private int iNombreZones;` |
| `iNombrePointsZone` | 1494 | ❌ | `private static int iNombrePointsZone;` |
| `y1Zone` | 1495 | ❌ | `private static double x1Zone, y1Zone;` |
| `strRepertBarrePersonnalisee` | 1496 | ❌ | `static private String strRepertBarrePersonnalisee = "";` |
| `strTypeZone` | 1497 | ❌ | `private static String strTypeZone = "";` |
| `strNomFichierShp` | 1498 | ❌ | `private static String strNomFichierShp = "";` |
| `zones` | 1499 | ❌ | `private static final ZoneTelecommande[] zones = new ZoneTelecommande[25];` |
| `pointsPolyZone` | 1500 | ❌ | `private static final double[] pointsPolyZone = new double[200];` |
| `bPleinEcranPanoramique` | 1501 | ❌ | `private static boolean bPleinEcranPanoramique = false;` |
| `strTouchesBarre` | 1502 | ❌ | `private static final String[] strTouchesBarre = {` |
| `strCodeBarre` | 1510 | ❌ | `private static final String[] strCodeBarre = {` |
| `strRepertAppli` | 1532 | ✅ | `private static String strRepertAppli = "";` |
| `strRepertTemp` | 1543 | ✅ | `private static String strRepertTemp = "";` |
| `strRepertPanos` | 1553 | ✅ | `static private String strRepertPanos = "";` |
| `strRepertHSImages` | 1563 | ✅ | `static private String strRepertHSImages = "";` |
| `strRepertoireProjet` | 1574 | ✅ | `private static String strRepertoireProjet = "";` |
| `strDernierRepertoireVisite` | 1586 | ✅ | `private static String strDernierRepertoireVisite = "";` |
| `fileRepertConfig` | 1594 | ✅ | `static public File fileRepertConfig;` |
| `apListePanoTriable` | 1597 | ❌ | `private static AnchorPane apListePanoTriable;` |
| `lblChoixPanoramique` | 1599 | ❌ | `static private Label lblChoixPanoramique;` |
| `bPanoCharge` | 1600 | ❌ | `static private boolean bPanoCharge = false;` |
| `strPanoAffiche` | 1601 | ❌ | `static private String strPanoAffiche = "";` |
| `bDejaSauve` | 1602 | ❌ | `private static boolean bDejaSauve = true;` |
| `stPrincipal` | 1603 | ❌ | `private static Stage stPrincipal;` |
| `strHistoFichiers` | 1604 | ❌ | `private static final String[] strHistoFichiers = new String[10];` |
| `nombreHistoFichiers` | 1605 | ❌ | `static private int nombreHistoFichiers = 0;` |
| `fileHistoFichiers` | 1606 | ❌ | `static private File fileHistoFichiers;` |
| `strTexteHisto` | 1607 | ❌ | `private static String strTexteHisto = "";` |
| `strNumVersion` | 1608 | ❌ | `public static String strNumVersion = "";` |
| `iHauteurInterface` | 1609 | ❌ | `private static int iHauteurInterface;` |
| `iLargeurInterface` | 1610 | ❌ | `private static int iLargeurInterface;` |
| `navigateurOpenLayers` | 1611 | ❌ | `public static NavigateurOpenLayers navigateurOpenLayers;` |
| `gestDiapo` | 1615 | ❌ | `private static GestionnaireDiaporamaController gestDiapo;` |
| `mniCube2EquiTransformation` | 1624 | ❌ | `private static MenuItem mniCube2EquiTransformation;` |
| `mniEqui2CubeTransformation` | 1625 | ❌ | `private static MenuItem mniEqui2CubeTransformation;` |
| `mniOutilsBarre` | 1626 | ❌ | `private static MenuItem mniOutilsBarre;` |
| `mniOutilsDiaporama` | 1627 | ❌ | `private static MenuItem mniOutilsDiaporama;` |
| `mniOutilsLoupe` | 1628 | ❌ | `private static MenuItem mniOutilsLoupe;` |
| `mniConfigTransformation` | 1629 | ❌ | `private static MenuItem mniConfigTransformation;` |
| `mniChargerModele` | 1630 | ❌ | `private static MenuItem mniChargerModele;` |
| `mniSauverModele` | 1631 | ❌ | `private static MenuItem mniSauverModele;` |
| `mniAPropos` | 1632 | ❌ | `private static MenuItem mniAPropos;` |
| `mniDocumentation` | 1634 | ❌ | `private static MenuItem mniDocumentation;` |
| `mniNouveauProjet` | 1636 | ❌ | `private static MenuItem mniNouveauProjet;` |
| `mniSauveProjet` | 1637 | ❌ | `private static MenuItem mniSauveProjet;` |
| `mniAjouterPano` | 1638 | ❌ | `private static MenuItem mniAjouterPano;` |
| `mniAjouterPlan` | 1639 | ❌ | `private static MenuItem mniAjouterPlan;` |
| `mniFermerProjet` | 1641 | ❌ | `private static MenuItem mniFermerProjet;` |
| `mniSauveSousProjet` | 1643 | ❌ | `private static MenuItem mniSauveSousProjet;` |
| `mniVisiteGenere` | 1644 | ❌ | `private static MenuItem mniVisiteGenere;` |
| `mniChargeProjet` | 1645 | ❌ | `private static MenuItem mniChargeProjet;` |
| `mniAffichageVisite` | 1647 | ❌ | `private static MenuItem mniAffichageVisite;` |
| `mniAffichageInterface` | 1648 | ❌ | `private static MenuItem mniAffichageInterface;` |
| `mniAffichagePlan` | 1649 | ❌ | `private static MenuItem mniAffichagePlan;` |
| `ivNouveauProjet` | 1651 | ❌ | `private static ImageView ivNouveauProjet;` |
| `ivSauveProjet` | 1652 | ❌ | `private static ImageView ivSauveProjet;` |
| `ivChargeProjet` | 1653 | ❌ | `private static ImageView ivChargeProjet;` |
| `ivVisiteGenere` | 1654 | ❌ | `private static ImageView ivVisiteGenere;` |
| `ivAjouterPano` | 1655 | ❌ | `private static ImageView ivAjouterPano;` |
| `ivAjouterPlan` | 1656 | ❌ | `private static ImageView ivAjouterPlan;` |
| `ivCube2Equi` | 1657 | ❌ | `private static ImageView ivCube2Equi;` |
| `ivEqui2Cube` | 1658 | ❌ | `private static ImageView ivEqui2Cube;` |
| `tfLongitude` | 1660 | ❌ | `private static TextField tfLongitude;` |
| `tfLatitude` | 1661 | ❌ | `private static TextField tfLatitude;` |
| `strOrdrePanos` | 1662 | ❌ | `private static String strOrdrePanos;` |
| `apPVIS` | 1667 | ✅ | `private static AnchorPane apPVIS;` |
| `apAR` | 1671 | ✅ | `private static AnchorPane apAR;` |
| `apPPAN` | 1675 | ✅ | `private static AnchorPane apPPAN;` |
| `apDESCIA` | 1679 | ✅ | `private static AnchorPane apDESCIA;` |
| `apGEO` | 1683 | ✅ | `private static AnchorPane apGEO;` |
| `apVISU` | 1687 | ✅ | `private static AnchorPane apVISU;` |
| `apHS` | 1691 | ✅ | `private static AnchorPane apHS;` |
| `strContenuFichier` | 1863 | ❌ | `String strContenuFichier;` |
| `fileXML` | 1864 | ❌ | `File fileXML;` |
| `strChargeImages` | 1865 | ❌ | `String strChargeImages = "";` |
| `strHTMLPanoRepert` | 1872 | ❌ | `String strHTMLPanoRepert = strHTMLRepert + "/" + i;` |
| `strFichierPano` | 1877 | ❌ | `String strFichierPano = "panos/panovisu" + i;` |
| `regX` | 1895 | ❌ | `double regX;` |
| `zN` | 1896 | ❌ | `double zN;` |
| `strTit` | 1926 | ❌ | `String strTit = "";` |
| `strTit` | 1937 | ❌ | `String strTit = "";` |
| `strTit` | 1948 | ❌ | `String strTit = "";` |
| `strNomPano` | 2173 | ❌ | `String strNomPano = "panovisu" + iPanoSuivant;` |
| `strPanoSuivant` | 2174 | ❌ | `String strPanoSuivant = "xml/" + strNomPano + ".xml";` |
| `strPanoPrecedent` | 2176 | ❌ | `String strPanoPrecedent = "xml/" + strNomPano + ".xml";` |
| `strFichier` | 2260 | ❌ | `String strFichier = "panovisu" + j + "Vignette.jpg";` |
| `strXML` | 2261 | ❌ | `String strXML = "panovisu" + j + ".xml";` |
| `strFichier` | 2292 | ❌ | `String strFichier = "panovisu" + j + "Vignette.jpg";` |
| `strXML` | 2293 | ❌ | `String strXML = "panovisu" + j + ".xml";` |
| `longit` | 2323 | ❌ | `double longit;` |
| `strUrlHTML` | 2361 | ❌ | `String strUrlHTML;` |
| `longit` | 2373 | ❌ | `double longit;` |
| `strUrlHTML` | 2407 | ❌ | `String strUrlHTML;` |
| `longit` | 2417 | ❌ | `double longit;` |
| `longit` | 2465 | ❌ | `double longit;` |
| `coords` | 2583 | ❌ | `CoordonneesGeographiques coords;` |
| `strHTML` | 2617 | ❌ | `String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-we...` |
| `strFichierXML` | 2621 | ❌ | `String strFichierXML = "panovisu" + iPoint2;` |
| `strNomXMLFile` | 2651 | ❌ | `String strNomXMLFile = "panovisu" + i + ".xml";` |
| `strFichierHTML` | 2669 | ❌ | `String strFichierHTML = "<!DOCTYPE html>\n"` |
| `fileRepert` | 2806 | ❌ | `File fileRepert;` |
| `tailleNiv0` | 2866 | ❌ | `double tailleNiv0 = 512.d;` |
| `imgPano` | 2868 | ❌ | `Image imgPano = null;` |
| `strFicImage` | 2887 | ❌ | `String strFicImage = strRepertoire + File.separator + "panovisu" + iNumPanoXM...` |
| `iNombreNiveaux` | 2895 | ❌ | `int iNombreNiveaux = 0;` |
| `iNbNiv` | 2900 | ❌ | `int iNbNiv = iNombreNiveaux;` |
| `ii` | 2902 | ❌ | `int ii = i;` |
| `iNombreNiv2` | 2903 | ❌ | `int iNombreNiv2 = iNombreNiveaux;` |
| `strRepNiveau` | 2922 | ❌ | `String strRepNiveau = strRepertoire + File.separator + "niveau" + i;` |
| `iNombreNiveaux` | 2942 | ❌ | `return iNombreNiveaux;` |
| `strSuffixe` | 2957 | ❌ | `String strSuffixe = "";` |
| `tailleNiv0` | 2978 | ❌ | `double tailleNiv0 = 256.d;` |
| `imgPano` | 2980 | ❌ | `Image imgPano = null;` |
| `strFicImage` | 2999 | ❌ | `String strFicImage = strRepertoire + File.separator + "panovisu" + iNumPanoXM...` |
| `iNombreNiveaux` | 3007 | ❌ | `int iNombreNiveaux = 0;` |
| `nbNiv` | 3011 | ❌ | `int nbNiv = iNombreNiveaux;` |
| `ii` | 3014 | ❌ | `int ii = i;` |
| `strRepNiveau` | 3033 | ❌ | `String strRepNiveau = strRepertoire + File.separator + "niveau" + i;` |
| `iNombreNiveaux` | 3048 | ❌ | `return iNombreNiveaux;` |
| `i` | 3073 | ❌ | `int i = 0;` |
| `iNb` | 3080 | ❌ | `int iNb = i;` |
| `taskTraitementChargeFichiers` | 3082 | ❌ | `Task taskTraitementChargeFichiers;` |
| `i` | 3102 | ❌ | `int i = 0;` |
| `fileLstFich1` | 3103 | ❌ | `File[] fileLstFich1 = new File[listFichiers.length];` |
| `strTypeFich1` | 3104 | ❌ | `String[] strTypeFich1 = new String[listFichiers.length];` |
| `fileFichier` | 3106 | ❌ | `File fileFichier = listFichiers[jj];` |
| `imgPano` | 3110 | ❌ | `Image imgPano = null;` |
| `bTrouveFichier` | 3131 | ❌ | `boolean bTrouveFichier = false;` |
| `strNom1` | 3133 | ❌ | `String strNom1 = "";` |
| `fileLstFich` | 3151 | ❌ | `File[] fileLstFich = new File[i];` |
| `fileFichier1` | 3155 | ❌ | `File fileFichier1 = fileLstFich[ii];` |
| `iNumP` | 3156 | ❌ | `int iNumP = ii + 1;` |
| `true` | 3175 | ❌ | `return true;` |
| `iNumero` | 3195 | ❌ | `int iNumero = i;` |
| `reponse` | 3327 | ❌ | `ButtonType reponse = null;` |
| `strTexte` | 3402 | ❌ | `String strTexte;` |
| `strLigneTexte` | 3406 | ❌ | `String strLigneTexte;` |
| `taskAnalysePVU` | 3415 | ❌ | `Task taskAnalysePVU;` |
| `iTrouve` | 3439 | ❌ | `int iTrouve = -1;` |
| `reponse` | 3500 | ❌ | `ButtonType reponse = null;` |
| `strTexte` | 3564 | ❌ | `String strTexte;` |
| `strLigneTexte` | 3568 | ❌ | `String strLigneTexte;` |
| `taskAnalysePVU` | 3577 | ❌ | `Task taskAnalysePVU;` |
| `strContenuFichier` | 3605 | ❌ | `String strContenuFichier = "";` |
| `strContenuFichier` | 3787 | ❌ | `String strContenuFichier = "";` |
| `oswFichierHisto` | 3791 | ❌ | `OutputStreamWriter oswFichierHisto = null;` |
| `reponse` | 3886 | ❌ | `ButtonType reponse = null;` |
| `reponse` | 3921 | ❌ | `ButtonType reponse = null;` |
| `bSucces` | 4008 | ❌ | `boolean bSucces = false;` |
| `strFichierPath` | 4017 | ❌ | `String strFichierPath = null;` |
| `fileList` | 4018 | ❌ | `File[] fileList = new File[100];` |
| `i` | 4019 | ❌ | `int i = 0;` |
| `iNb` | 4025 | ❌ | `int iNb = i;` |
| `taskChargeFichiers` | 4028 | ❌ | `Task taskChargeFichiers;` |
| `strChaine` | 4050 | ❌ | `String strChaine = "";` |
| `strChaine1` | 4051 | ❌ | `String strChaine1 = "abcdefghijklmnopqrstuvwxyz0123456789";` |
| `strChaine` | 4056 | ❌ | `return strChaine;` |
| `iNumero` | 4092 | ❌ | `int iNumero = i;` |
| `iPano` | 4112 | ❌ | `int iPano = -1;` |
| `ii` | 4113 | ❌ | `int ii = 0;` |
| `strChaine` | 4140 | ❌ | `return strChaine;` |
| `strEncode` | 4143 | ❌ | `static String[] strEncode = {"&lbrack", "&rbrack", "&pv", "&sp"};` |
| `strDecode` | 4144 | ❌ | `static String[] strDecode = {"[", "]", ";", " "};` |
| `strFichier` | 4150 | ❌ | `return strFichier;` |
| `strFichier` | 4157 | ❌ | `return strFichier;` |
| `strLigne` | 4175 | ❌ | `String strLigne;` |
| `strElementsLigne` | 4176 | ❌ | `String[] strElementsLigne;` |
| `strTypeElement` | 4177 | ❌ | `String[] strTypeElement;` |
| `iNbHS` | 4178 | ❌ | `int iNbHS = 0;` |
| `iNbImg` | 4179 | ❌ | `int iNbImg = 0;` |
| `iNbHTML` | 4180 | ❌ | `int iNbHTML = 0;` |
| `iNbHSDiapo` | 4181 | ❌ | `int iNbHSDiapo = 0;` |
| `iNbHsplan` | 4182 | ❌ | `int iNbHsplan = 0;` |
| `iNbImages` | 4183 | ❌ | `int iNbImages = 0;` |
| `imgPano` | 4219 | ❌ | `Image imgPano = null;` |
| `nmFich` | 4444 | ❌ | `String nmFich = strValeur[1];` |
| `inPano` | 4791 | ❌ | `int inPano = 0;` |
| `inHSHTML` | 4792 | ❌ | `int inHSHTML = 0;` |
| `inImage` | 4793 | ❌ | `int inImage = 0;` |
| `true` | 4822 | ❌ | `return true;` |
| `iNumPanoChoisitHS` | 4966 | ❌ | `int iNumPanoChoisitHS = -1;` |
| `strTexte` | 4997 | ❌ | `String strTexte;` |
| `strLangue` | 5000 | ❌ | `String strLangue = "fr";` |
| `strPays` | 5001 | ❌ | `String strPays = "FR";` |
| `strAPI` | 5003 | ❌ | `String strAPI = "";` |
| `valeur` | 5006 | ❌ | `String valeur;` |
| `oswFichierConfig` | 5064 | ❌ | `OutputStreamWriter oswFichierConfig = null;` |
| `strListe` | 5174 | ❌ | `String strListe = "";` |
| `null` | 5176 | ❌ | `return null;` |
| `strListe` | 5187 | ❌ | `return strListe;` |
| `navigateurPano2` | 5197 | ❌ | `NavigateurPanoramique navigateurPano2;` |
| `apVisuPanoHS` | 5198 | ❌ | `AnchorPane apVisuPanoHS;` |
| `ij` | 5251 | ❌ | `int ij = 0;` |
| `ivPano` | 5252 | ❌ | `ImageView[] ivPano;` |
| `xPos` | 5254 | ❌ | `double xPos;` |
| `yPos` | 5255 | ❌ | `double yPos;` |
| `iRow` | 5256 | ❌ | `int iRow = 0;` |
| `iNumeroPano1` | 5295 | ❌ | `int iNumeroPano1 = i;` |
| `iCol` | 5301 | ❌ | `int iCol = ij % 4;` |
| `aplistePano` | 5352 | ❌ | `return aplistePano;` |
| `lblPoint` | 5423 | ❌ | `Label lblPoint;` |
| `io` | 5424 | ❌ | `int io;` |
| `deplacement` | 5429 | ❌ | `double deplacement = 0;` |
| `iNum1` | 5436 | ❌ | `int iNum1 = io;` |
| `iNumPan` | 5496 | ❌ | `int iNumPan = iNum11;` |
| `iNbHS` | 5531 | ❌ | `int iNbHS = io;` |
| `iTaillePane` | 5532 | ❌ | `int iTaillePane = io * 325;` |
| `iNum` | 5538 | ❌ | `int iNum = io;` |
| `i` | 5617 | ❌ | `int i = io;` |
| `iNum` | 5650 | ❌ | `int iNum = io;` |
| `iNum` | 5738 | ❌ | `int iNum = io;` |
| `iii` | 5796 | ❌ | `int iii = io;` |
| `paneHotSpots` | 5823 | ❌ | `return paneHotSpots;` |
| `positionX` | 5850 | ❌ | `double positionX;` |
| `positionY` | 5851 | ❌ | `double positionY;` |
| `spLegende` | 5932 | ❌ | `return spLegende;` |
| `lat` | 6005 | ❌ | `double longit, lat;` |
| `strLat` | 6007 | ❌ | `String strLong, strLat;` |
| `nodePoint` | 6022 | ❌ | `Node nodePoint;` |
| `lat` | 6136 | ❌ | `double longit, lat;` |
| `strLat` | 6138 | ❌ | `String strLong, strLat;` |
| `nodePointImage` | 6153 | ❌ | `Node nodePointImage;` |
| `fileRepert` | 6179 | ❌ | `File fileRepert;` |
| `lat` | 6292 | ❌ | `double longit, lat;` |
| `strLat` | 6294 | ❌ | `String strLong, strLat;` |
| `nodePointDiapo` | 6309 | ❌ | `Node nodePointDiapo;` |
| `bTrouve` | 6347 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 6348 | ❌ | `int iTrouve = -1;` |
| `lat` | 6439 | ❌ | `double longit, lat;` |
| `strLat` | 6441 | ❌ | `String strLong, strLat;` |
| `nodePointImage` | 6456 | ❌ | `Node nodePointImage;` |
| `mouseX` | 6577 | ❌ | `double mouseX = X;` |
| `regardX` | 6580 | ❌ | `double regardX = 360.0f * mouseX / largeur - 180;` |
| `regardY` | 6581 | ❌ | `double regardY = 90.0d - 2.0f * mouseY / largeur * 180.0f;` |
| `mouseX` | 6599 | ❌ | `double mouseX = X;` |
| `regardX` | 6601 | ❌ | `double regardX = 360.0f * mouseX / largeur - 180;` |
| `regardX` | 6655 | ❌ | `double regardX = 360.0f * mouseX1 / largeur - 180;` |
| `regardX` | 6750 | ❌ | `double regardX = 360.0f * mouseX1 / largeur - 180;` |
| `regardY` | 6751 | ❌ | `double regardY = 90.0d - 2.0f * mouseY1 / largeur * 180.0f;` |
| `mouseX` | 6778 | ❌ | `double mouseX = X;` |
| `latitude` | 6781 | ❌ | `double longitude, latitude;` |
| `strLat` | 6783 | ❌ | `String strLong, strLat;` |
| `nodePoint` | 6820 | ❌ | `Node nodePoint;` |
| `lat` | 6871 | ❌ | `double longit, lat;` |
| `nodePoint` | 6890 | ❌ | `Node nodePoint;` |
| `mouseX` | 6948 | ❌ | `double mouseX = X;` |
| `latitude` | 6950 | ❌ | `double longitude, latitude;` |
| `fileRepert` | 6963 | ❌ | `File fileRepert;` |
| `lat` | 7064 | ❌ | `double longit, lat;` |
| `nodeImage` | 7083 | ❌ | `Node nodeImage;` |
| `fileRepert1` | 7105 | ❌ | `File fileRepert1;` |
| `mouseX` | 7163 | ❌ | `double mouseX = X;` |
| `latitude` | 7165 | ❌ | `double longitude, latitude;` |
| `lat` | 7263 | ❌ | `double longit, lat;` |
| `nodeImage` | 7282 | ❌ | `Node nodeImage;` |
| `mouseX` | 7345 | ❌ | `double mouseX = X;` |
| `latitude` | 7347 | ❌ | `double longitude, latitude;` |
| `bTrouve` | 7374 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 7375 | ❌ | `int iTrouve = -1;` |
| `lat` | 7448 | ❌ | `double longit, lat;` |
| `nodeImage` | 7467 | ❌ | `Node nodeImage;` |
| `bTrouve` | 7504 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 7505 | ❌ | `int iTrouve = -1;` |
| `latitude` | 7745 | ❌ | `double longitude, latitude;` |
| `hauteur` | 7774 | ❌ | `double hauteur = largeur / 2.0d;` |
| `lineCoordonnees` | 7775 | ❌ | `Line lineCoordonnees;` |
| `iY` | 7776 | ❌ | `int iX, iY;` |
| `iNl` | 7777 | ❌ | `int iNl = 0;` |
| `i` | 7825 | ❌ | `int i = 0;` |
| `nodeLigne` | 7826 | ❌ | `Node nodeLigne;` |
| `iHauteur` | 7931 | ❌ | `int iHauteur;` |
| `iPano` | 8063 | ❌ | `int iPano = -1;` |
| `ii` | 8064 | ❌ | `int ii = 0;` |
| `titreVisite` | 8164 | ❌ | `String titreVisite = null;` |
| `latitude` | 8171 | ❌ | `String latitude = null;` |
| `longitude` | 8172 | ❌ | `String longitude = null;` |
| `hasCoords` | 8184 | ❌ | `boolean hasCoords = latitude != null && longitude != null;` |
| `null` | 8231 | ❌ | `return null;` |
| `iHauteur` | 8243 | ❌ | `int iHauteur = iLargeur / 2 / iRapport;` |
| `imgMercator` | 8258 | ❌ | `return imgMercator;` |
| `imgPano3` | 8302 | ❌ | `Image imgPano3 = null;` |
| `imgPano4` | 8303 | ❌ | `Image imgPano4 = null;` |
| `imgPano5` | 8304 | ❌ | `Image imgPano5 = null;` |
| `imgTop` | 8355 | ❌ | `Image imgTop = null;` |
| `imgBottom` | 8356 | ❌ | `Image imgBottom = null;` |
| `imgLeft` | 8357 | ❌ | `Image imgLeft = null;` |
| `imgRight` | 8358 | ❌ | `Image imgRight = null;` |
| `imgFront` | 8359 | ❌ | `Image imgFront = null;` |
| `imgBehind` | 8360 | ❌ | `Image imgBehind = null;` |
| `imgPano3` | 8433 | ❌ | `Image imgPano3;` |
| `imgPano4` | 8434 | ❌ | `Image imgPano4;` |
| `imgPano3` | 8494 | ❌ | `Image imgPano3 = null;` |
| `imgPano4` | 8495 | ❌ | `Image imgPano4 = null;` |
| `imgPano5` | 8496 | ❌ | `Image imgPano5 = null;` |
| `imgTop` | 8548 | ❌ | `Image imgTop = null;` |
| `imgBottom` | 8549 | ❌ | `Image imgBottom = null;` |
| `imgLeft` | 8550 | ❌ | `Image imgLeft = null;` |
| `imgRight` | 8551 | ❌ | `Image imgRight = null;` |
| `imgFront` | 8552 | ❌ | `Image imgFront = null;` |
| `imgBehind` | 8553 | ❌ | `Image imgBehind = null;` |
| `imgPano3` | 8600 | ❌ | `Image imgPano3;` |
| `imgPano4` | 8601 | ❌ | `Image imgPano4;` |
| `fileTemplate` | 8652 | ❌ | `File fileTemplate;` |
| `strLigneTexte` | 8696 | ❌ | `String strLigneTexte;` |
| `idx` | 8732 | ❌ | `final int idx = i;` |
| `chaine` | 8739 | ❌ | `String chaine = "";` |
| `chaine` | 8747 | ❌ | `String chaine = "";` |
| `olAnchors` | 8756 | ❌ | `return olAnchors;` |
| `olAnchors` | 8808 | ❌ | `return olAnchors;` |
| `olAnchors` | 8867 | ❌ | `return olAnchors;` |
| `zone` | 8899 | ❌ | `ZoneTelecommande zone = zones[iNumeroZone];` |
| `index` | 8900 | ❌ | `int index = -1;` |
| `zone` | 8972 | ❌ | `ZoneTelecommande zone = zones[i];` |
| `points` | 8974 | ❌ | `double[] points = new double[strPoints.length];` |
| `largRect` | 8994 | ❌ | `double largRect = points[2] - points[0];` |
| `hautRect` | 8995 | ❌ | `double hautRect = points[3] - points[1];` |
| `chaine` | 9180 | ❌ | `String chaine = "";` |
| `strChaine` | 9241 | ❌ | `String strChaine = "";` |
| `oswFichierBarre` | 9286 | ❌ | `OutputStreamWriter oswFichierBarre = null;` |
| `strZones` | 9288 | ❌ | `String strZones = "";` |
| `zone` | 9290 | ❌ | `ZoneTelecommande zone = zones[i];` |
| `strDiapoNom` | 9342 | ❌ | `String strDiapoNom = "diapo" + iNumero + ".html";` |
| `strContenuHTML` | 9344 | ❌ | `String strContenuHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transi...` |
| `iLargeur` | 9520 | ❌ | `final int iLargeur = 800;` |
| `iHauteur` | 9521 | ❌ | `final int iHauteur = 630;` |
| `bTrouve` | 9569 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 9570 | ❌ | `int iTrouve = -1;` |
| `reponse` | 9627 | ❌ | `ButtonType reponse = null;` |
| `bTrouve` | 9642 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 9643 | ❌ | `int iTrouve = -1;` |
| `bTrouve` | 9661 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 9662 | ❌ | `int iTrouve = -1;` |
| `reponse` | 9679 | ❌ | `ButtonType reponse = null;` |
| `bTrouve` | 9694 | ❌ | `boolean bTrouve = false;` |
| `iTrouve` | 9695 | ❌ | `int iTrouve = -1;` |
| `bTrouve` | 9721 | ❌ | `boolean bTrouve = false;` |
| `iLargeur` | 9777 | ❌ | `final int iLargeur = 1200;` |
| `iHauteur` | 9778 | ❌ | `final int iHauteur = 600;` |
| `strNomFichier` | 9852 | ❌ | `String strNomFichier = strNomFichierBarre;` |
| `strNomFichierPng` | 9855 | ❌ | `String strNomFichierPng = strNomFichier + ".png";` |
| `fileRepert` | 9890 | ❌ | `File fileRepert;` |
| `strNomFichierPng` | 9903 | ❌ | `String strNomFichierPng = strNomFichier + ".png";` |
| `fImage` | 10533 | ❌ | `File fImage;` |
| `fileRemplace` | 10598 | ❌ | `File fileRemplace;` |
| `paneVisualiseur` | 10798 | ❌ | `Pane paneVisualiseur;` |
| `panePlan` | 10799 | ❌ | `Pane panePlan;` |
| `tfTitrePano` | 10810 | ❌ | `TextField tfTitrePano;` |
| `tfTitreVisite` | 10811 | ❌ | `TextField tfTitreVisite;` |
| `largeur` | 10841 | ❌ | `double largeur;` |
| `strLabelStyle` | 10842 | ❌ | `String strLabelStyle = "-fx-color : white;-fx-background-color : #fff;-fx-pad...` |
| `imgPano` | 11092 | ❌ | `Image imgPano = null;` |
| `strHTML` | 11445 | ❌ | `String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-we...` |
| `strTexte` | 11697 | ❌ | `String strTexte;` |
| `valeur` | 11702 | ❌ | `String valeur;` |
| `oswFichierHisto` | 11773 | ❌ | `OutputStreamWriter oswFichierHisto = null;` |
| `version` | 11834 | ❌ | `String version = "3.0";` |
| `iHauteur` | 11856 | ❌ | `int iHauteur;` |
| `reponse` | 11886 | ❌ | `ButtonType reponse = null;` |

#### Méthodes (240)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `netIsAvailable` | 170 | ✅ | `private static boolean netIsAvailable() {` |
| `RuntimeException` | 177 | ❌ | `throw new RuntimeException(e);` |
| `getStrStyleCSS` | 186 | ✅ | `public static String getStrStyleCSS() {` |
| `setStrStyleCSS` | 193 | ✅ | `public static void setStrStyleCSS(String aStrStyleCSS) {` |
| `getStrCodesLanguesTraduction` | 204 | ✅ | `public static String[] getStrCodesLanguesTraduction() {` |
| `getStrLanguesTraduction` | 211 | ✅ | `public static String[] getStrLanguesTraduction() {` |
| `getStrCurrentDir` | 218 | ✅ | `public static String getStrCurrentDir() {` |
| `setStrCurrentDir` | 225 | ✅ | `public static void setStrCurrentDir(String aStrCurrentDir) {` |
| `getLocale` | 232 | ✅ | `public static Locale getLocale() {` |
| `setLocale` | 239 | ✅ | `public static void setLocale(Locale aLocale) {` |
| `getiNumHTML` | 246 | ✅ | `public static int getiNumHTML() {` |
| `setiNumHTML` | 253 | ✅ | `public static void setiNumHTML(int aiNumHTML) {` |
| `getPanoramiquesProjet` | 260 | ✅ | `public static Panoramique[] getPanoramiquesProjet() {` |
| `setPanoramiquesProjet` | 267 | ✅ | `public static void setPanoramiquesProjet(Panoramique[] aPanoramiquesProjet) {` |
| `getPlans` | 274 | ✅ | `public static Plan[] getPlans() {` |
| `setPlans` | 281 | ✅ | `public static void setPlans(Plan[] aPlans) {` |
| `getiNombrePanoramiques` | 288 | ✅ | `public static int getiNombrePanoramiques() {` |
| `setiNombrePanoramiques` | 295 | ✅ | `public static void setiNombrePanoramiques(int aiNombrePanoramiques) {` |
| `getiNombrePanoramiquesFichier` | 302 | ✅ | `public static int getiNombrePanoramiquesFichier() {` |
| `setiNombrePanoramiquesFichier` | 309 | ✅ | `public static void setiNombrePanoramiquesFichier(int aiNombrePanoramiquesFich...` |
| `getiNombrePlans` | 316 | ✅ | `public static int getiNombrePlans() {` |
| `setiNombrePlans` | 323 | ✅ | `public static void setiNombrePlans(int aiNombrePlans) {` |
| `getiPanoActuel` | 330 | ✅ | `public static int getiPanoActuel() {` |
| `setiPanoActuel` | 337 | ✅ | `public static void setiPanoActuel(int aiPanoActuel) {` |
| `getStrPanoListeVignette` | 344 | ✅ | `public static String getStrPanoListeVignette() {` |
| `setStrPanoListeVignette` | 351 | ✅ | `public static void setStrPanoListeVignette(String aStrPanoListeVignette) {` |
| `getTabPlan` | 358 | ✅ | `public static Tab getTabPlan() {` |
| `setTabPlan` | 365 | ✅ | `public static void setTabPlan(Tab aTabPlan) {` |
| `getStrSystemeExploitation` | 372 | ✅ | `public static String getStrSystemeExploitation() {` |
| `setStrSystemeExploitation` | 379 | ✅ | `public static void setStrSystemeExploitation(String aStrSystemeExploitation) {` |
| `isMac` | 387 | ✅ | `public static Boolean isMac() {` |
| `getStrSystemeExploitation` | 391 | ❌ | `return getStrSystemeExploitation().contains("Mac");` |
| `isLinux` | 398 | ✅ | `public static Boolean isLinux() {` |
| `getStrSystemeExploitation` | 399 | ❌ | `return getStrSystemeExploitation().contains("Linux");` |
| `isWindows` | 406 | ✅ | `public static Boolean isWindows() {` |
| `getStrSystemeExploitation` | 407 | ❌ | `return getStrSystemeExploitation().contains("Windows");` |
| `getStrTooltipStyle` | 413 | ✅ | `public static String getStrTooltipStyle() {` |
| `getStrRepertAppli` | 420 | ✅ | `public static String getStrRepertAppli() {` |
| `setStrRepertAppli` | 427 | ✅ | `public static void setStrRepertAppli(String aStrRepertAppli) {` |
| `loadSvgIcon` | 448 | ✅ | `public static ImageView loadSvgIcon(String iconName, int size) {` |
| `loadSvgIcon` | 471 | ✅ | `public static ImageView loadSvgIcon(String iconName, int size, javafx.scene.p...` |
| `loadSvgIcon` | 495 | ✅ | `public static ImageView loadSvgIcon(String iconName, int width, int height, j...` |
| `rechargerIcones` | 511 | ✅ | `private static void rechargerIcones() {` |
| `getStrRepertTemp` | 541 | ✅ | `public static String getStrRepertTemp() {` |
| `setStrRepertTemp` | 548 | ✅ | `public static void setStrRepertTemp(String aStrRepertTemp) {` |
| `getStrRepertPanos` | 555 | ✅ | `public static String getStrRepertPanos() {` |
| `setStrRepertPanos` | 562 | ✅ | `public static void setStrRepertPanos(String aStrRepertPanos) {` |
| `getStrRepertHSImages` | 569 | ✅ | `public static String getStrRepertHSImages() {` |
| `setStrRepertHSImages` | 576 | ✅ | `public static void setStrRepertHSImages(String aStrRepertHSImages) {` |
| `getStrRepertoireProjet` | 583 | ✅ | `public static String getStrRepertoireProjet() {` |
| `setStrRepertoireProjet` | 590 | ✅ | `public static void setStrRepertoireProjet(String aStrRepertoireProjet) {` |
| `getStrDernierRepertoireVisite` | 597 | ✅ | `public static String getStrDernierRepertoireVisite() {` |
| `setStrDernierRepertoireVisite` | 604 | ✅ | `public static void setStrDernierRepertoireVisite(String aStrDernierRepertoire...` |
| `isbDejaSauve` | 611 | ✅ | `public static boolean isbDejaSauve() {` |
| `setbDejaSauve` | 618 | ✅ | `public static void setbDejaSauve(boolean abDejaSauve) {` |
| `getStPrincipal` | 625 | ✅ | `public static Stage getStPrincipal() {` |
| `setStPrincipal` | 632 | ✅ | `public static void setStPrincipal(Stage aStPrincipal) {` |
| `getGestionnaireInterface` | 639 | ✅ | `public static GestionnaireInterfaceController getGestionnaireInterface() {` |
| `setGestionnaireInterface` | 646 | ✅ | `public static void setGestionnaireInterface(GestionnaireInterfaceController a...` |
| `getGestionnairePlan` | 653 | ✅ | `public static GestionnairePlanController getGestionnairePlan() {` |
| `setGestionnairePlan` | 660 | ✅ | `public static void setGestionnairePlan(GestionnairePlanController aGestionnai...` |
| `getMniAjouterPlan` | 667 | ✅ | `public static MenuItem getMniAjouterPlan() {` |
| `setMniAjouterPlan` | 674 | ✅ | `public static void setMniAjouterPlan(MenuItem aMniAjouterPlan) {` |
| `getMniAffichagePlan` | 681 | ✅ | `public static MenuItem getMniAffichagePlan() {` |
| `setMniAffichagePlan` | 688 | ✅ | `public static void setMniAffichagePlan(MenuItem aMniAffichagePlan) {` |
| `getIvAjouterPlan` | 695 | ✅ | `public static ImageView getIvAjouterPlan() {` |
| `setIvAjouterPlan` | 702 | ✅ | `public static void setIvAjouterPlan(ImageView aIvAjouterPlan) {` |
| `getTabInterface` | 709 | ✅ | `public static Tab getTabInterface() {` |
| `setTabInterface` | 716 | ✅ | `public static void setTabInterface(Tab aTabInterface) {` |
| `getApAttends` | 723 | ✅ | `public static AnchorPane getApAttends() {` |
| `setApAttends` | 730 | ✅ | `public static void setApAttends(AnchorPane apAttends1) {` |
| `getStrBingAPIKey` | 737 | ✅ | `public static String getStrBingAPIKey() {` |
| `setStrBingAPIKey` | 744 | ✅ | `public static void setStrBingAPIKey(String strBingAPIKey1) {` |
| `isbAutoRotationDemarre` | 751 | ✅ | `public static boolean isbAutoRotationDemarre() {` |
| `setbAutoRotationDemarre` | 758 | ✅ | `public static void setbAutoRotationDemarre(boolean abAutoRotationDemarre) {` |
| `getiAutoRotationVitesse` | 765 | ✅ | `public static int getiAutoRotationVitesse() {` |
| `setiAutoRotationVitesse` | 772 | ✅ | `public static void setiAutoRotationVitesse(int aiAutoRotationVitesse) {` |
| `isbAutoTourDemarre` | 779 | ✅ | `public static boolean isbAutoTourDemarre() {` |
| `setbAutoTourDemarre` | 786 | ✅ | `public static void setbAutoTourDemarre(boolean abAutoTourDemarre) {` |
| `getiAutoTourLimite` | 793 | ✅ | `public static int getiAutoTourLimite() {` |
| `setiAutoTourLimite` | 800 | ✅ | `public static void setiAutoTourLimite(int aiAutoTourLimite) {` |
| `getStrAutoTourType` | 807 | ✅ | `public static String getStrAutoTourType() {` |
| `setStrAutoTourType` | 814 | ✅ | `public static void setStrAutoTourType(String aStrAutoTourType) {` |
| `isbPetitePlaneteDemarrage` | 821 | ✅ | `public static boolean isbPetitePlaneteDemarrage() {` |
| `setbPetitePlaneteDemarrage` | 828 | ✅ | `public static void setbPetitePlaneteDemarrage(boolean abPetitePlaneteDemarrag...` |
| `isbIntroPetitePlanete` | 835 | ✅ | `public static boolean isbIntroPetitePlanete() {` |
| `setbIntroPetitePlanete` | 842 | ✅ | `public static void setbIntroPetitePlanete(boolean abIntroPetitePlanete) {` |
| `getPoGeolocalisation` | 849 | ✅ | `public static PaneOutil getPoGeolocalisation() {` |
| `setPoGeolocalisation` | 856 | ✅ | `public static void setPoGeolocalisation(PaneOutil aPoGeolocalisation) {` |
| `isbInternet` | 863 | ✅ | `public static boolean isbInternet() {` |
| `setbInternet` | 870 | ✅ | `public static void setbInternet(boolean abInternet) {` |
| `getiDecalageMac` | 877 | ✅ | `public static int getiDecalageMac() {` |
| `setiDecalageMac` | 884 | ✅ | `public static void setiDecalageMac(int aiDecalageMac) {` |
| `getVbChoixPanoramique` | 891 | ✅ | `public static VBox getVbChoixPanoramique() {` |
| `setVbChoixPanoramique` | 898 | ✅ | `public static void setVbChoixPanoramique(VBox aVbChoixPanoramique) {` |
| `getApPVIS` | 905 | ✅ | `public static AnchorPane getApPVIS() {` |
| `setApPVIS` | 912 | ✅ | `public static void setApPVIS(AnchorPane aApPVIS) {` |
| `getApAR` | 919 | ✅ | `public static AnchorPane getApAR() {` |
| `setApAR` | 926 | ✅ | `public static void setApAR(AnchorPane aApAR) {` |
| `getApPPAN` | 933 | ✅ | `public static AnchorPane getApPPAN() {` |
| `setApPPAN` | 940 | ✅ | `public static void setApPPAN(AnchorPane aApPPAN) {` |
| `getApDESCIA` | 947 | ✅ | `public static AnchorPane getApDESCIA() {` |
| `setApDESCIA` | 954 | ✅ | `public static void setApDESCIA(AnchorPane aApDESCIA) {` |
| `getApGEO` | 961 | ✅ | `public static AnchorPane getApGEO() {` |
| `setApGEO` | 968 | ✅ | `public static void setApGEO(AnchorPane aApGEO) {` |
| `getApVISU` | 975 | ✅ | `public static AnchorPane getApVISU() {` |
| `setApVISU` | 982 | ✅ | `public static void setApVISU(AnchorPane aApVISU) {` |
| `getApHS` | 989 | ✅ | `public static AnchorPane getApHS() {` |
| `setApHS` | 996 | ✅ | `public static void setApHS(AnchorPane aApHS) {` |
| `getiAutoTourDemarrage` | 1003 | ✅ | `public static int getiAutoTourDemarrage() {` |
| `setiAutoTourDemarrage` | 1010 | ✅ | `public static void setiAutoTourDemarrage(int aiAutoTourDemarrage) {` |
| `getScnPrincipale` | 1017 | ✅ | `public static Scene getScnPrincipale() {` |
| `setScnPrincipale` | 1024 | ✅ | `public static void setScnPrincipale(Scene aScnPrincipale) {` |
| `getiNumDiapo` | 1031 | ✅ | `public static int getiNumDiapo() {` |
| `setiNumDiapo` | 1038 | ✅ | `public static void setiNumDiapo(int aiNumDiapo) {` |
| `getiNumImages` | 1045 | ✅ | `public static int getiNumImages() {` |
| `setiNumImages` | 1052 | ✅ | `public static void setiNumImages(int aiNumImages) {` |
| `getiNumPoints` | 1059 | ✅ | `public static int getiNumPoints() {` |
| `setiNumPoints` | 1066 | ✅ | `public static void setiNumPoints(int aiNumPoints) {` |
| `getiNombreDiapo` | 1073 | ✅ | `public static int getiNombreDiapo() {` |
| `setiNombreDiapo` | 1080 | ✅ | `public static void setiNombreDiapo(int aiNombreDiapo) {` |
| `isAfficheLoupe` | 1087 | ✅ | `public static boolean isAfficheLoupe() {` |
| `setAfficheLoupe` | 1094 | ✅ | `public static void setAfficheLoupe(boolean aAfficheLoupe) {` |
| `getiTailleLoupe` | 1101 | ✅ | `public static int getiTailleLoupe() {` |
| `setiTailleLoupe` | 1108 | ✅ | `public static void setiTailleLoupe(int aiTailleLoupe) {` |
| `getStrTypeFichierTransf` | 1121 | ✅ | `public static String getStrTypeFichierTransf() {` |
| `setStrTypeFichierTransf` | 1128 | ✅ | `public static void setStrTypeFichierTransf(String aStrTypeFichierTransf) {` |
| `getLargeurE2C` | 1135 | ✅ | `public static double getLargeurE2C() {` |
| `setLargeurE2C` | 1142 | ✅ | `public static void setLargeurE2C(double aLargeurE2C) {` |
| `getHauteurE2C` | 1149 | ✅ | `public static double getHauteurE2C() {` |
| `setHauteurE2C` | 1156 | ✅ | `public static void setHauteurE2C(double aHauteurE2C) {` |
| `isbNetteteTransf` | 1163 | ✅ | `public static boolean isbNetteteTransf() {` |
| `setbNetteteTransf` | 1170 | ✅ | `public static void setbNetteteTransf(boolean abNetteteTransf) {` |
| `getNiveauNetteteTransf` | 1177 | ✅ | `public static double getNiveauNetteteTransf() {` |
| `setNiveauNetteteTransf` | 1184 | ✅ | `public static void setNiveauNetteteTransf(double aNiveauNetteteTransf) {` |
| `enableDrag` | 1221 | ✅ | `private void enableDrag() {` |
| `genereVisite` | 1715 | ✅ | `private static void genereVisite() throws IOException {` |
| `start` | 2831 | ❌ | `public void start(Stage stage) {` |
| `iCreeNiveauxImageEqui` | 2861 | ✅ | `private static int iCreeNiveauxImageEqui(String strFichierImage, String strNo...` |
| `iCreeNiveauxImageCube` | 2953 | ✅ | `private static int iCreeNiveauxImageCube(String strFichierImage, String strRe...` |
| `panoramiquesAjouter` | 3055 | ✅ | `private static void panoramiquesAjouter() throws InterruptedException {` |
| `tskChargeListeFichiers` | 3098 | ✅ | `public static Task tskChargeListeFichiers(final File[] listFichiers, int iNb) {` |
| `Task` | 3099 | ❌ | `return new Task() {` |
| `call` | 3101 | ❌ | `protected Object call() throws Exception {` |
| `succeeded` | 3179 | ❌ | `protected void succeeded() {` |
| `planAjouter` | 3267 | ✅ | `private static void planAjouter() {` |
| `clickBtnValidePano` | 3302 | ✅ | `private static void clickBtnValidePano() {` |
| `projetCharge` | 3323 | ✅ | `private static void projetCharge() throws IOException, InterruptedException {` |
| `FileInputStream` | 3404 | ❌ | `new FileInputStream(fileProjet), "UTF-8"))) {` |
| `ajouteFichierHisto` | 3435 | ✅ | `private static void ajouteFichierHisto(String nomFich) {` |
| `projetChargeNom` | 3497 | ✅ | `private static void projetChargeNom(String strNomFich) throws IOException, In...` |
| `FileInputStream` | 3566 | ❌ | `new FileInputStream(fileProjet), "UTF-8"))) {` |
| `sauveFichierProjet` | 3595 | ✅ | `private static void sauveFichierProjet() throws IOException {` |
| `sauveHistoFichiers` | 3781 | ✅ | `private static void sauveHistoFichiers() throws IOException {` |
| `projetSauve` | 3821 | ✅ | `private static void projetSauve() throws IOException {` |
| `projetSauveSous` | 3844 | ✅ | `private static void projetSauveSous() throws IOException {` |
| `aideapropos` | 3865 | ✅ | `private static void aideapropos() {` |
| `projetsFermer` | 3879 | ✅ | `private static void projetsFermer() throws IOException {` |
| `projetsNouveau` | 3920 | ✅ | `private static void projetsNouveau() {` |
| `strGenereChaineAleatoire` | 4049 | ✅ | `private String strGenereChaineAleatoire(int iNombre) {` |
| `deleteDirectory` | 4063 | ✅ | `static public void deleteDirectory(String strEmplacement) {` |
| `rafraichitListePano` | 4076 | ❌ | `public static void rafraichitListePano() {` |
| `suprimeEspaceFin` | 4135 | ❌ | `private static String suprimeEspaceFin(String strChaine) {` |
| `strEncodeFichier` | 4146 | ❌ | `private static String strEncodeFichier(String strFichier) {` |
| `strDecodeFichier` | 4153 | ❌ | `private static String strDecodeFichier(String strFichier) {` |
| `tskAnalyseFichierPVU` | 4165 | ✅ | `public static Task tskAnalyseFichierPVU(final String strLigComplete) {` |
| `Task` | 4166 | ❌ | `return new Task() {` |
| `call` | 4168 | ❌ | `protected Object call() throws Exception {` |
| `succeeded` | 4827 | ❌ | `protected void succeeded() {` |
| `creeVignettesHS` | 4961 | ❌ | `private static void creeVignettesHS() {` |
| `lisFichierConfig` | 4993 | ✅ | `private static void lisFichierConfig() throws IOException {` |
| `FileInputStream` | 4999 | ❌ | `new FileInputStream(fileFichierConfig), "UTF-8"))) {` |
| `copieRepertoire` | 5097 | ✅ | `static public void copieRepertoire(String strEmplacement, String strRepertoir...` |
| `copieFichierRepertoire` | 5130 | ✅ | `static public void copieFichierRepertoire(String strFichier, String strRepert...` |
| `retireAffichageHotSpots` | 5139 | ✅ | `private static void retireAffichageHotSpots() {` |
| `retireAffichagePointsHotSpots` | 5148 | ✅ | `private static void retireAffichagePointsHotSpots() {` |
| `strListePano` | 5173 | ✅ | `private static String strListePano() {` |
| `apAfficherListePanosVignettes` | 5196 | ✅ | `private static AnchorPane apAfficherListePanosVignettes(int iNumHS) {` |
| `valideHS` | 5358 | ✅ | `private static void valideHS() {` |
| `paneAffichageHS` | 5416 | ✅ | `public static Pane paneAffichageHS(String strLstPano, int iNumPano) {` |
| `ajouteAffichageHotspots` | 5829 | ✅ | `private static void ajouteAffichageHotspots() {` |
| `spAfficheLegende` | 5849 | ✅ | `private static ScrollPane spAfficheLegende() {` |
| `afficheHS` | 5941 | ✅ | `private static void afficheHS(int i, double longitude, double latitude) {` |
| `afficheHSImage` | 6074 | ✅ | `private static void afficheHSImage(int i, double longitude, double latitude) {` |
| `afficheHSDiapo` | 6230 | ✅ | `private static void afficheHSDiapo(int i, double longitude, double latitude) {` |
| `afficheHSHTML` | 6377 | ✅ | `private static void afficheHSHTML(int i, double longitude, double latitude) {` |
| `ajouteAffichagePointsHotspots` | 6511 | ✅ | `private static void ajouteAffichagePointsHotspots() {` |
| `ajouteAffichagePointsHotspotsImage` | 6525 | ✅ | `private static void ajouteAffichagePointsHotspotsImage() {` |
| `ajouteAffichagePointsHotspotsDiapo` | 6538 | ✅ | `private static void ajouteAffichagePointsHotspotsDiapo() {` |
| `ajouteAffichagePointsHotspotsHTML` | 6551 | ✅ | `private static void ajouteAffichagePointsHotspotsHTML() {` |
| `sauveFichiers` | 6564 | ✅ | `private void sauveFichiers() throws IOException {` |
| `panoChoixRegard` | 6575 | ✅ | `private static void panoChoixRegard(double X, double Y) {` |
| `panoChoixNord` | 6597 | ✅ | `private static void panoChoixNord(double X) {` |
| `afficheNord` | 6613 | ✅ | `private static void afficheNord(double longitude) {` |
| `affichePoV` | 6674 | ✅ | `private static void affichePoV(double longitude, double latitude, double fov) {` |
| `panoMouseClic` | 6772 | ✅ | `private static void panoMouseClic(double X, double Y) {` |
| `panoAjouteImage` | 6942 | ✅ | `private static void panoAjouteImage(double X, double Y) {` |
| `panoAjouteHTML` | 7158 | ✅ | `private static void panoAjouteHTML(double X, double Y) {` |
| `panoAjouteDiaporama` | 7340 | ✅ | `private static void panoAjouteDiaporama(double X, double Y) {` |
| `gereSourisPanoramique` | 7536 | ✅ | `private static void gereSourisPanoramique(MouseEvent mouseEvent) {` |
| `afficheLoupe` | 7622 | ❌ | `private static void afficheLoupe(double x, double y) {` |
| `installeEvenements` | 7644 | ✅ | `private static void installeEvenements() {` |
| `ajouteAffichageLignes` | 7772 | ✅ | `private static void ajouteAffichageLignes() {` |
| `retireAffichageLigne` | 7824 | ✅ | `private static void retireAffichageLigne() {` |
| `creeLoupe` | 7839 | ✅ | `private static void creeLoupe() {` |
| `affichePanoChoisit` | 7894 | ❌ | `public static void affichePanoChoisit(int iNumPanochoisi) {` |
| `afficheInfoPano` | 8110 | ✅ | `private static void afficheInfoPano() {` |
| `genererDescriptionIA` | 8152 | ✅ | `private static void genererDescriptionIA(TextArea taDescriptionIA) {` |
| `imgTransformationImage` | 8241 | ✅ | `private static Image imgTransformationImage(Image imgRect, int iRapport) {` |
| `ajoutePanoramiqueProjet` | 8270 | ❌ | `private static void ajoutePanoramiqueProjet(String strFichierPano, String typ...` |
| `rechargePanoramiqueProjet` | 8463 | ❌ | `private static void rechargePanoramiqueProjet(String strFichierPano, String t...` |
| `transformationCube2Equi` | 8622 | ✅ | `private static void transformationCube2Equi() {` |
| `transformationEqui2Cube` | 8636 | ✅ | `private static void transformationEqui2Cube() {` |
| `modeleSauver` | 8651 | ✅ | `private static void modeleSauver() throws IOException {` |
| `modeleCharger` | 8683 | ✅ | `private static void modeleCharger() throws IOException {` |
| `FileInputStream` | 8694 | ❌ | `new FileInputStream(filetemplate), "UTF-8"));` |
| `olCreeAncresPourPolygone` | 8727 | ✅ | `private static ObservableList<AncreForme> olCreeAncresPourPolygone(int iNumZone,` |
| `olCreeAncresPourCercle` | 8765 | ✅ | `private static ObservableList<AncreForme> olCreeAncresPourCercle(int iNumZone,` |
| `olCreeAncresPourRectangle` | 8817 | ✅ | `private static ObservableList<AncreForme> olCreeAncresPourRectangle(int iNumZ...` |
| `choixZone` | 8878 | ✅ | `private static void choixZone(int iLargeur, int iHauteur, boolean bMasqueZone...` |
| `afficheBarrePersonnalisee` | 8960 | ✅ | `private static void afficheBarrePersonnalisee(int iLargeur, int iHauteur, boo...` |
| `ajouterZone` | 9032 | ✅ | `private static void ajouterZone(int iLargeur, int iHauteur, boolean bMasqueZo...` |
| `sauverBarre` | 9285 | ❌ | `private static void sauverBarre(String fichShp) throws FileNotFoundException,...` |
| `creeDiaporamaHTML` | 9319 | ❌ | `private static void creeDiaporamaHTML(Diaporama diapo, int iNumero) throws IO...` |
| `creerEditerDiaporama` | 9506 | ❌ | `public static void creerEditerDiaporama(String strDiaporama) {` |
| `creerEditerBarre` | 9754 | ✅ | `public static void creerEditerBarre(String strNomFichierBarre) {` |
| `creeEnvironnement` | 9946 | ✅ | `private void creeEnvironnement(Stage stPrimaryStage) throws Exception {` |
| `creeMenu` | 9955 | ✅ | `private static void creeMenu(VBox vbRacine) throws Exception {` |
| `FileInputStream` | 9994 | ❌ | `new FileInputStream(fileHistoFichiers), "UTF-8"))) {` |
| `retirePanoCourant` | 10521 | ✅ | `private static void retirePanoCourant() {` |
| `creeEnvironnement` | 10759 | ✅ | `private static void creeEnvironnement(Stage stPrimaryStage, int iLargeur, int...` |
| `getPanoramiquesProjet` | 11076 | ❌ | `0, getPanoramiquesProjet()[getiPanoActuel()].getStrNomFichier().lastIndexOf(F...` |
| `lisPreferences` | 11693 | ❌ | `private static void lisPreferences() throws IOException {` |
| `FileInputStream` | 11699 | ❌ | `new FileInputStream(fileFichPreferences), "UTF-8"))) {` |
| `sauvePreferences` | 11759 | ❌ | `private static void sauvePreferences() throws IOException {` |
| `start` | 11823 | ❌ | `public void start(Stage stPrimaryStage) throws Exception {` |
| `afficherDialogueTheme` | 11930 | ✅ | `private static void afficherDialogueTheme() {` |
| `Separator` | 11983 | ❌ | `new Separator(),` |
| `Separator` | 12017 | ❌ | `new Separator(),` |
| `main` | 12051 | ✅ | `public static void main(String[] args) {` |

---

### ❌ `EquiCubeDialogController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\EquiCubeDialogController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Classes internes :** ListeTransformationCouleur

**Progression :** 7/67 éléments documentés (10.4%)

#### Propriétés (55)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `stTransformations` | 60 | ❌ | `private static Stage stTransformations;` |
| `apTransformations` | 61 | ❌ | `private static AnchorPane apTransformations;` |
| `strTypeTransformation` | 62 | ❌ | `private String strTypeTransformation = "";` |
| `btnAnnuler` | 65 | ❌ | `static private Button btnAnnuler;` |
| `btnValider` | 66 | ❌ | `static private Button btnValider;` |
| `btnAjouteFichiers` | 67 | ❌ | `static private Button btnAjouteFichiers;` |
| `paneChoixTypeFichier` | 68 | ❌ | `static private Pane paneChoixTypeFichier;` |
| `bTraitementEffectue` | 69 | ❌ | `static private boolean bTraitementEffectue = false;` |
| `rbJpeg` | 70 | ❌ | `static private RadioButton rbJpeg;` |
| `rbBmp` | 71 | ❌ | `static private RadioButton rbBmp;` |
| `rbTiff` | 72 | ❌ | `static private RadioButton rbTiff;` |
| `cbSharpen` | 73 | ❌ | `static private CheckBox cbSharpen;` |
| `slSharpen` | 74 | ❌ | `static private Slider slSharpen;` |
| `lblSharpen` | 75 | ❌ | `static private Label lblSharpen;` |
| `pbBarreAvancement` | 76 | ❌ | `static private ProgressBar pbBarreAvancement;` |
| `lblDragDropE2C` | 78 | ❌ | `private Label lblDragDropE2C;` |
| `EQUI2CUBE` | 84 | ✅ | `public final static String EQUI2CUBE = "E2C";` |
| `CUBE2QUI` | 88 | ✅ | `public final static String CUBE2QUI = "C2E";` |
| `fileLstFichier` | 89 | ❌ | `private File[] fileLstFichier;` |
| `reponse` | 97 | ❌ | `ButtonType reponse = null;` |
| `imgEquiImage` | 126 | ❌ | `Image imgEquiImage = null;` |
| `strSuffixe` | 140 | ❌ | `String strSuffixe = "";` |
| `bSharpen` | 163 | ❌ | `boolean bSharpen = false;` |
| `quality` | 181 | ❌ | `float quality = 1.0f; //qualité jpeg à 100% : le moins de pertes possible` |
| `imgTop` | 195 | ❌ | `Image imgTop = null;` |
| `imgBottom` | 196 | ❌ | `Image imgBottom = null;` |
| `imgLeft` | 197 | ❌ | `Image imgLeft = null;` |
| `imgRight` | 198 | ❌ | `Image imgRight = null;` |
| `imgFront` | 199 | ❌ | `Image imgFront = null;` |
| `imgBehind` | 200 | ❌ | `Image imgBehind = null;` |
| `bSharpen` | 236 | ❌ | `boolean bSharpen = false;` |
| `quality` | 255 | ❌ | `float quality = 1.0f;` |
| `taskTraitement` | 292 | ❌ | `Task taskTraitement;` |
| `ii` | 322 | ❌ | `final int ii = i1;` |
| `true` | 336 | ❌ | `return true;` |
| `fileLstFich` | 360 | ❌ | `File[] fileLstFich = null;` |
| `i` | 368 | ❌ | `int i = 0;` |
| `bAttention` | 369 | ❌ | `boolean bAttention = false;` |
| `strExtension` | 377 | ❌ | `String strExtension;` |
| `img` | 379 | ❌ | `Image img = null;` |
| `bTrouve` | 399 | ❌ | `boolean bTrouve = false;` |
| `fileLstFich` | 428 | ❌ | `return fileLstFich;` |
| `strPre` | 444 | ❌ | `String strPre = "";` |
| `strTexte` | 445 | ❌ | `String strTexte = strItem;` |
| `imgTransf` | 543 | ❌ | `Image imgTransf;` |
| `bSucces` | 726 | ❌ | `boolean bSucces = false;` |
| `fileLstFich` | 727 | ❌ | `File[] fileLstFich;` |
| `stringFichiersPath` | 732 | ❌ | `String[] stringFichiersPath = new String[200];` |
| `i` | 733 | ❌ | `int i = 0;` |
| `iNb` | 738 | ❌ | `int iNb = i;` |
| `bAttention` | 740 | ❌ | `boolean bAttention = false;` |
| `fileLstFich1` | 741 | ❌ | `File[] fileLstFich1 = new File[stringFichiersPath.length];` |
| `strNomfich` | 744 | ❌ | `String strNomfich = stringFichiersPath[j];` |
| `img` | 751 | ❌ | `Image img = null;` |
| `bTrouve` | 773 | ❌ | `boolean bTrouve = false;` |

#### Méthodes (12)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `annulerE2C` | 95 | ✅ | `private void annulerE2C() {` |
| `traiteFichier` | 122 | ✅ | `private void traiteFichier(String nomFichier, int j) throws InterruptedExcept...` |
| `validerE2C` | 269 | ✅ | `private void validerE2C() {` |
| `tskTraitement` | 306 | ❌ | `public Task tskTraitement() {` |
| `Task` | 307 | ❌ | `return new Task() {` |
| `call` | 309 | ❌ | `protected Object call() throws Exception {` |
| `succeeded` | 340 | ❌ | `protected void succeeded() {` |
| `choixFichiers` | 359 | ✅ | `private File[] choixFichiers() {` |
| `updateItem` | 439 | ❌ | `public void updateItem(String strItem, boolean bEmpty) {` |
| `afficheFenetre` | 473 | ✅ | `public void afficheFenetre(String strTypeTransf) throws Exception {` |
| `call` | 701 | ❌ | `public ListCell<String> call(ListView<String> list) {` |
| `ListeTransformationCouleur` | 702 | ❌ | `return new ListeTransformationCouleur();` |

---

### 🔶 `EsriTileRetriever`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\EsriTileRetriever.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 3/6 éléments documentés (50.0%)

#### Propriétés (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `ESRI_TILE_URL` | 24 | ❌ | `private static final String ESRI_TILE_URL = "https://server.arcgisonline.com/...` |
| `image` | 56 | ❌ | `return image;` |
| `null` | 59 | ❌ | `return null;` |

#### Méthodes (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getTileUrl` | 34 | ✅ | `public static String getTileUrl(int zoom, long x, long y) {` |
| `loadTile` | 47 | ✅ | `public static Image loadTile(int zoom, long x, long y) {` |
| `getAttribution` | 68 | ✅ | `public static String getAttribution() {` |

---

### ❌ `ExtensionsFilter`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ExtensionsFilter.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 0/8 éléments documentés (0.0%)

#### Propriétés (7)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `chExtensions` | 13 | ❌ | `private char[][] chExtensions;` |
| `length` | 20 | ❌ | `int length = strExtensions.length;` |
| `iPStart` | 42 | ❌ | `int iPStart = chPath.length - 1;` |
| `iEStart` | 43 | ❌ | `int iEStart = chExtension.length - 1;` |
| `bSucces` | 44 | ❌ | `boolean bSucces = true;` |
| `true` | 54 | ❌ | `return true;` |
| `false` | 56 | ❌ | `return false;` |

#### Méthodes (1)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `accept` | 33 | ❌ | `public boolean accept(File fileCharge)` |

---

### ❌ `GestionnaireDiaporamaController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\GestionnaireDiaporamaController.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 4/32 éléments documentés (12.5%)

#### Propriétés (23)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `diaporama` | 45 | ❌ | `return diaporama;` |
| `diaporama` | 76 | ❌ | `private static Diaporama diaporama = null;` |
| `apDiaporama` | 80 | ❌ | `public AnchorPane apDiaporama = null;` |
| `bDisabled` | 81 | ❌ | `private boolean bDisabled = true;` |
| `apDiapo1` | 82 | ❌ | `private AnchorPane apDiapo1;` |
| `diapoSauve` | 83 | ❌ | `public boolean diapoSauve = true;` |
| `ivImage` | 84 | ❌ | `private ImageView ivImage;` |
| `iLargeur` | 96 | ❌ | `final int iLargeur = 800;` |
| `iHauteur` | 97 | ❌ | `final int iHauteur = 550;` |
| `debutOutils` | 135 | ❌ | `double debutOutils = 390;` |
| `largeurBouton` | 161 | ❌ | `double largeurBouton = 250;` |
| `ancienneValeur` | 215 | ❌ | `boolean ancienneValeur = false;` |
| `nouvelleValeur` | 216 | ❌ | `boolean nouvelleValeur = true;` |
| `ancienneValeur` | 221 | ❌ | `boolean ancienneValeur = false;` |
| `nouvelleValeur` | 222 | ❌ | `boolean nouvelleValeur = true;` |
| `ancienneValeur` | 228 | ❌ | `boolean ancienneValeur = false;` |
| `nouvelleValeur` | 229 | ❌ | `boolean nouvelleValeur = true;` |
| `reponse` | 257 | ❌ | `ButtonType reponse = null;` |
| `largImg1` | 329 | ❌ | `double largImg1 = largImg * 300 / hautImg;` |
| `hautImg1` | 332 | ❌ | `double hautImg1 = hautImg * 450 / largImg;` |
| `hautImg1` | 339 | ❌ | `double hautImg1 = hautImg * 450 / largImg;` |
| `largImg1` | 342 | ❌ | `double largImg1 = largImg * 300 / hautImg;` |
| `bDisabled` | 368 | ❌ | `return bDisabled;` |

#### Méthodes (9)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getDiaporama` | 44 | ✅ | `public Diaporama getDiaporama() {` |
| `setDiaporama` | 51 | ✅ | `public void setDiaporama(Diaporama aDiaporama) {` |
| `addPropertyChangeListener` | 57 | ❌ | `public void addPropertyChangeListener(String propertyName, PropertyChangeList...` |
| `removePropertyChangeListener` | 61 | ❌ | `public void removePropertyChangeListener(String propertyName, PropertyChangeL...` |
| `firePropertyChange` | 65 | ❌ | `public void firePropertyChange(String propertyName, BigDecimal oldValue, BigD...` |
| `initDiaporama` | 88 | ❌ | `public void initDiaporama() {` |
| `reInit` | 359 | ❌ | `public void reInit(Diaporama nouveauDiapo) {` |
| `isbDisabled` | 367 | ✅ | `public boolean isbDisabled() {` |
| `setbDisabled` | 374 | ✅ | `public void setbDisabled(boolean bDisabled) {` |

---

### ⚠️ `GestionnaireInterfaceController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\GestionnaireInterfaceController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 470/1309 éléments documentés (35.9%)

#### Propriétés (838)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `dtBuild` | 98 | ❌ | `public Date dtBuild;` |
| `iCalqueTitre` | 105 | ❌ | `private int iCalqueTitre = 1;` |
| `iCalqueBarreClassique` | 106 | ❌ | `private int iCalqueBarreClassique = 1;` |
| `iCalqueBarrePersonnalisee` | 107 | ❌ | `private int iCalqueBarrePersonnalisee = 1;` |
| `iCalqueMasquage` | 108 | ❌ | `private int iCalqueMasquage = 1;` |
| `iCalqueVisiteAuto` | 109 | ❌ | `private int iCalqueVisiteAuto = 1;` |
| `iCalquePartage` | 110 | ❌ | `private int iCalquePartage = 1;` |
| `iCalquePlan` | 111 | ❌ | `private int iCalquePlan = 1;` |
| `iCalqueCarte` | 112 | ❌ | `private int iCalqueCarte = 1;` |
| `iCalqueBoussole` | 113 | ❌ | `private int iCalqueBoussole = 1;` |
| `iCalqueVignettes` | 114 | ❌ | `private int iCalqueVignettes = 1;` |
| `iCalqueMenuPanoramiques` | 115 | ❌ | `private int iCalqueMenuPanoramiques = 1;` |
| `iCalqueSuivPrec` | 116 | ❌ | `private int iCalqueSuivPrec = 1;` |
| `cbCalques` | 130 | ❌ | `private CheckBox cbCalques;` |
| `iNombreCalques` | 131 | ❌ | `final private int iNombreCalques = 20;` |
| `apCalque` | 133 | ❌ | `private AnchorPane apCalque;` |
| `ivImageFond` | 135 | ❌ | `private ImageView[] ivImageFond = new ImageView[20];` |
| `imagesFond` | 146 | ❌ | `private ImageFond[] imagesFond = new ImageFond[50];` |
| `iNombreImagesFond` | 147 | ❌ | `private int iNombreImagesFond = 0;` |
| `bTemplate` | 148 | ❌ | `private boolean bTemplate;` |
| `poImageFond` | 149 | ❌ | `private PaneOutil poImageFond;` |
| `strTypeHS` | 150 | ❌ | `public static String strTypeHS = "";` |
| `strNomfichierHS` | 151 | ❌ | `public static String strNomfichierHS = "";` |
| `strTypeHSImage` | 152 | ❌ | `public static String strTypeHSImage = "";` |
| `strNomfichierHSImage` | 153 | ❌ | `public static String strNomfichierHSImage = "";` |
| `strTypeHSHTML` | 154 | ❌ | `public static String strTypeHSHTML = "";` |
| `strNomfichierHSHTML` | 155 | ❌ | `public static String strNomfichierHSHTML = "";` |
| `strStyleHotSpots` | 157 | ❌ | `private String strStyleHotSpots = "hotspot.png";` |
| `strStyleHotSpotImages` | 158 | ❌ | `private String strStyleHotSpotImages = "photo2.png";` |
| `strStyleHotSpotHTML` | 159 | ❌ | `private String strStyleHotSpotHTML = "html1.png";` |
| `rbLocalisation` | 160 | ❌ | `private ResourceBundle rbLocalisation;` |
| `offsetXBarreClassique` | 164 | ❌ | `private double offsetXBarreClassique = 0;` |
| `offsetYBarreClassique` | 165 | ❌ | `private double offsetYBarreClassique = 10;` |
| `tailleBarreClassique` | 166 | ❌ | `private double tailleBarreClassique = 26;` |
| `espacementBarreClassique` | 167 | ❌ | `private double espacementBarreClassique = 4;` |
| `strStyleDefautBarreClassique` | 168 | ❌ | `private final String strStyleDefautBarreClassique = "retina";` |
| `strPositionBarreClassique` | 169 | ❌ | `private String strPositionBarreClassique = "bottom:center";` |
| `styleBarreClassique` | 170 | ❌ | `private String styleBarreClassique = strStyleDefautBarreClassique;` |
| `strDeplacementsBarreClassique` | 171 | ❌ | `private String strDeplacementsBarreClassique = "oui";` |
| `strZoomBarreClassique` | 172 | ❌ | `private String strZoomBarreClassique = "oui";` |
| `strOutilsBarreClassique` | 173 | ❌ | `private String strOutilsBarreClassique = "oui";` |
| `strRotationBarreClassique` | 174 | ❌ | `private String strRotationBarreClassique = "oui";` |
| `strPleinEcranBarreClassique` | 175 | ❌ | `private String strPleinEcranBarreClassique = "oui";` |
| `strSourisBarreClassique` | 176 | ❌ | `private String strSourisBarreClassique = "oui";` |
| `strVisibiliteBarreClassique` | 177 | ❌ | `private String strVisibiliteBarreClassique = "oui";` |
| `cblisteStyleBarreClassique` | 178 | ❌ | `private ComboBox cblisteStyleBarreClassique;` |
| `rbTopLeftBarreClassique` | 179 | ❌ | `private RadioButton rbTopLeftBarreClassique;` |
| `rbTopCenterBarreClassique` | 180 | ❌ | `private RadioButton rbTopCenterBarreClassique;` |
| `rbTopRightBarreClassique` | 181 | ❌ | `private RadioButton rbTopRightBarreClassique;` |
| `rbMiddleLeftBarreClassique` | 182 | ❌ | `private RadioButton rbMiddleLeftBarreClassique;` |
| `rbMiddleRightBarreClassique` | 183 | ❌ | `private RadioButton rbMiddleRightBarreClassique;` |
| `rbBottomLeftBarreClassique` | 184 | ❌ | `private RadioButton rbBottomLeftBarreClassique;` |
| `rbBottomCenterBarreClassique` | 185 | ❌ | `private RadioButton rbBottomCenterBarreClassique;` |
| `rbBottomRightBarreClassique` | 186 | ❌ | `private RadioButton rbBottomRightBarreClassique;` |
| `cbBarreClassiqueVisible` | 187 | ❌ | `private CheckBox cbBarreClassiqueVisible;` |
| `cbDeplacementsBarreClassique` | 188 | ❌ | `private CheckBox cbDeplacementsBarreClassique;` |
| `cbZoomBarreClassique` | 189 | ❌ | `private CheckBox cbZoomBarreClassique;` |
| `cbOutilsBarreClassique` | 190 | ❌ | `private CheckBox cbOutilsBarreClassique;` |
| `cbFSBarreClassique` | 191 | ❌ | `private CheckBox cbFSBarreClassique;` |
| `cbSourisBarreClassique` | 192 | ❌ | `private CheckBox cbSourisBarreClassique;` |
| `cbRotationBarreClassique` | 193 | ❌ | `private CheckBox cbRotationBarreClassique;` |
| `slEspacementBarreClassique` | 194 | ❌ | `private Slider slEspacementBarreClassique;` |
| `bdfOffsetXBarreClassique` | 195 | ❌ | `private BigDecimalField bdfOffsetXBarreClassique;` |
| `bdfOffsetYBarreClassique` | 196 | ❌ | `private BigDecimalField bdfOffsetYBarreClassique;` |
| `cpCouleurBarreClassique` | 197 | ❌ | `private ColorPicker cpCouleurBarreClassique;` |
| `bCouleurOrigineBarrePersonnalisee` | 203 | ❌ | `private boolean bCouleurOrigineBarrePersonnalisee = true;` |
| `iNombreZonesBarrePersonnalisee` | 204 | ❌ | `private int iNombreZonesBarrePersonnalisee = 0;` |
| `offsetXBarrePersonnalisee` | 205 | ❌ | `private double offsetXBarrePersonnalisee = 0;` |
| `offsetYBarrePersonnalisee` | 206 | ❌ | `private double offsetYBarrePersonnalisee = 0;` |
| `tailleBarrePersonnalisee` | 207 | ❌ | `private double tailleBarrePersonnalisee = 100;` |
| `tailleIconesBarrePersonnalisee` | 208 | ❌ | `private double tailleIconesBarrePersonnalisee = 40;` |
| `strPositionBarrePersonnalisee` | 209 | ❌ | `private String strPositionBarrePersonnalisee = "bottom:right";` |
| `strDeplacementsBarrePersonnalisee` | 210 | ❌ | `private String strDeplacementsBarrePersonnalisee = "oui";` |
| `strZoomBarrePersonnalisee` | 211 | ❌ | `private String strZoomBarrePersonnalisee = "oui";` |
| `strInfoBarrePersonnalisee` | 212 | ❌ | `private String strInfoBarrePersonnalisee = "oui";` |
| `strAideBarrePersonnalisee` | 213 | ❌ | `private String strAideBarrePersonnalisee = "oui";` |
| `strRotationBarrePersonnalisee` | 214 | ❌ | `private String strRotationBarrePersonnalisee = "oui";` |
| `strPleinEcranBarrePersonnalisee` | 215 | ❌ | `private String strPleinEcranBarrePersonnalisee = "oui";` |
| `strSourisBarrePersonnalisee` | 216 | ❌ | `private String strSourisBarrePersonnalisee = "oui";` |
| `strVisibiliteBarrePersonnalisee` | 217 | ❌ | `private String strVisibiliteBarrePersonnalisee = "non";` |
| `strLienImageBarrePersonnalisee` | 218 | ❌ | `private String strLienImageBarrePersonnalisee = "";` |
| `strLien1BarrePersonnalisee` | 219 | ❌ | `private String strLien1BarrePersonnalisee = "";` |
| `strLien2BarrePersonnalisee` | 220 | ❌ | `private String strLien2BarrePersonnalisee = "";` |
| `zonesBarrePersonnalisee` | 221 | ❌ | `private ZoneTelecommande[] zonesBarrePersonnalisee = new ZoneTelecommande[50];` |
| `rbTopLeftBarrePersonnalisee` | 222 | ❌ | `private RadioButton rbTopLeftBarrePersonnalisee;` |
| `rbTopCenterBarrePersonnalisee` | 223 | ❌ | `private RadioButton rbTopCenterBarrePersonnalisee;` |
| `rbTopRightBarrePersonnalisee` | 224 | ❌ | `private RadioButton rbTopRightBarrePersonnalisee;` |
| `rbMiddleLeftBarrePersonnalisee` | 225 | ❌ | `private RadioButton rbMiddleLeftBarrePersonnalisee;` |
| `rbMiddleRightBarrePersonnalisee` | 226 | ❌ | `private RadioButton rbMiddleRightBarrePersonnalisee;` |
| `rbBottomLeftBarrePersonnalisee` | 227 | ❌ | `private RadioButton rbBottomLeftBarrePersonnalisee;` |
| `rbBottomCenterBarrePersonnalisee` | 228 | ❌ | `private RadioButton rbBottomCenterBarrePersonnalisee;` |
| `rbBottomRightBarrePersonnalisee` | 229 | ❌ | `private RadioButton rbBottomRightBarrePersonnalisee;` |
| `rbCouleurOrigineBarrePersonnalisee` | 230 | ❌ | `private RadioButton rbCouleurOrigineBarrePersonnalisee;` |
| `rbCouleurPersBarrePersonnalisee` | 231 | ❌ | `private RadioButton rbCouleurPersBarrePersonnalisee;` |
| `ivBarrePersonnalisee` | 232 | ❌ | `private ImageView ivBarrePersonnalisee;` |
| `imgPngBarrePersonnalisee` | 233 | ❌ | `private Image imgPngBarrePersonnalisee;` |
| `wiBarrePersonnaliseeCouleur` | 234 | ❌ | `private WritableImage wiBarrePersonnaliseeCouleur;` |
| `apAfficheBarrePersonnalisee` | 235 | ❌ | `private AnchorPane apAfficheBarrePersonnalisee;` |
| `cbBarrePersonnaliseeVisible` | 236 | ❌ | `private CheckBox cbBarrePersonnaliseeVisible;` |
| `cbDeplacementsBarrePersonnalisee` | 237 | ❌ | `private CheckBox cbDeplacementsBarrePersonnalisee;` |
| `cbZoomBarrePersonnalisee` | 238 | ❌ | `private CheckBox cbZoomBarrePersonnalisee;` |
| `cbFSBarrePersonnalisee` | 239 | ❌ | `private CheckBox cbFSBarrePersonnalisee;` |
| `cbSourisBarrePersonnalisee` | 240 | ❌ | `private CheckBox cbSourisBarrePersonnalisee;` |
| `cbRotationBarrePersonnalisee` | 241 | ❌ | `private CheckBox cbRotationBarrePersonnalisee;` |
| `tfLienImageBarrePersonnalisee` | 242 | ❌ | `private TextField tfLienImageBarrePersonnalisee;` |
| `tfLien1BarrePersonnalisee` | 243 | ❌ | `private TextField tfLien1BarrePersonnalisee;` |
| `tfLien2BarrePersonnalisee` | 244 | ❌ | `private TextField tfLien2BarrePersonnalisee;` |
| `sltailleBarrePersonnalisee` | 245 | ❌ | `private Slider sltailleBarrePersonnalisee;` |
| `sltailleIconesBarrePersonnalisee` | 246 | ❌ | `private Slider sltailleIconesBarrePersonnalisee;` |
| `bdfOffsetXBarrePersonnalisee` | 247 | ❌ | `private BigDecimalField bdfOffsetXBarrePersonnalisee;` |
| `bdfOffsetYBarrePersonnalisee` | 248 | ❌ | `private BigDecimalField bdfOffsetYBarrePersonnalisee;` |
| `cpCouleurBarrePersonnalisee` | 249 | ❌ | `private ColorPicker cpCouleurBarrePersonnalisee;` |
| `btnEditerBarre` | 253 | ❌ | `private Button btnEditerBarre;` |
| `bAfficheTitre` | 255 | ❌ | `private boolean bAfficheTitre = true;` |
| `strTitrePoliceNom` | 256 | ❌ | `private String strTitrePoliceNom = "Verdana";` |
| `strTitrePoliceStyle` | 257 | ❌ | `private String strTitrePoliceStyle = "Regular";` |
| `strTitrePoliceTaille` | 258 | ❌ | `private String strTitrePoliceTaille = "12.0";` |
| `strCouleurTitre` | 259 | ❌ | `private String strCouleurTitre = "#ffffff";` |
| `strCouleurFondTitre` | 260 | ❌ | `private String strCouleurFondTitre = "#4e8080";` |
| `strTitrePosition` | 261 | ❌ | `private String strTitrePosition = "center";` |
| `titreDecalage` | 262 | ❌ | `private double titreDecalage = 10;` |
| `bTitreVisite` | 263 | ❌ | `private boolean bTitreVisite = false;` |
| `bTitrePanoramique` | 264 | ❌ | `private boolean bTitrePanoramique = true;` |
| `bTitreAdapte` | 265 | ❌ | `private boolean bTitreAdapte = false;` |
| `titreOpacite` | 266 | ❌ | `private double titreOpacite = 0.8;` |
| `titreTaille` | 267 | ❌ | `private double titreTaille = 100.0;` |
| `bAfficheDescription` | 272 | ✅ | `private boolean bAfficheDescription = false;` |
| `cbAfficheDescription` | 273 | ❌ | `private CheckBox cbAfficheDescription;` |
| `poDescription` | 274 | ❌ | `private PaneOutil poDescription;` |
| `bChargementEnCours` | 275 | ❌ | `private boolean bChargementEnCours = false;` |
| `bAfficheBoussole` | 280 | ✅ | `private boolean bAfficheBoussole = false;` |
| `strImageBoussole` | 281 | ❌ | `private String strImageBoussole = "rose3.png";` |
| `strPositionBoussole` | 282 | ❌ | `private String strPositionBoussole = "top:right";` |
| `offsetXBoussole` | 283 | ❌ | `private double offsetXBoussole = 20;` |
| `offsetYBoussole` | 284 | ❌ | `private double offsetYBoussole = 20;` |
| `tailleBoussole` | 285 | ❌ | `private double tailleBoussole = 100;` |
| `opaciteBoussole` | 286 | ❌ | `private double opaciteBoussole = 0.8;` |
| `bAiguilleMobileBoussole` | 287 | ❌ | `private boolean bAiguilleMobileBoussole = true;` |
| `strRepertImagesFond` | 288 | ❌ | `private String strRepertImagesFond = "";` |
| `strRepertBarrePersonnalisee` | 289 | ❌ | `private String strRepertBarrePersonnalisee = "";` |
| `imgBoussole` | 290 | ❌ | `private ImageView imgBoussole;` |
| `imgAiguille` | 291 | ❌ | `private ImageView imgAiguille;` |
| `bdfOffsetXBoussole` | 292 | ❌ | `private BigDecimalField bdfOffsetXBoussole;` |
| `bdfOffsetYBoussole` | 293 | ❌ | `private BigDecimalField bdfOffsetYBoussole;` |
| `slTailleBoussole` | 294 | ❌ | `private Slider slTailleBoussole;` |
| `slOpaciteBoussole` | 295 | ❌ | `private Slider slOpaciteBoussole;` |
| `cbAiguilleMobile` | 296 | ❌ | `private CheckBox cbAiguilleMobile;` |
| `cbAfficheBoussole` | 297 | ❌ | `private CheckBox cbAfficheBoussole;` |
| `rbBoussTopLeft` | 298 | ❌ | `private RadioButton rbBoussTopLeft;` |
| `rbBoussTopRight` | 299 | ❌ | `private RadioButton rbBoussTopRight;` |
| `rbBoussBottomLeft` | 300 | ❌ | `private RadioButton rbBoussBottomLeft;` |
| `rbBoussBottomRight` | 301 | ❌ | `private RadioButton rbBoussBottomRight;` |
| `bAfficheFenetreInfo` | 306 | ✅ | `private boolean bAfficheFenetreInfo = false;` |
| `bAfficheFenetreAide` | 307 | ❌ | `private boolean bAfficheFenetreAide = false;` |
| `bFenetreInfoPersonnalise` | 308 | ❌ | `private boolean bFenetreInfoPersonnalise = false;` |
| `bFenetreAidePersonnalise` | 309 | ❌ | `private boolean bFenetreAidePersonnalise = false;` |
| `fenetreInfoTaille` | 310 | ❌ | `private double fenetreInfoTaille = 100.d;` |
| `fenetreAideTaille` | 311 | ❌ | `private double fenetreAideTaille = 100.d;` |
| `fenetreInfoPosX` | 312 | ❌ | `private double fenetreInfoPosX = 0.d;` |
| `fenetreInfoPosY` | 313 | ❌ | `private double fenetreInfoPosY = 0.d;` |
| `fenetreAidePosX` | 314 | ❌ | `private double fenetreAidePosX = 0.d;` |
| `fenetreAidePosY` | 315 | ❌ | `private double fenetreAidePosY = 0.d;` |
| `fenetreInfoposX` | 316 | ❌ | `private double fenetreInfoposX = 0.d;` |
| `fenetreInfoOpacite` | 317 | ❌ | `private double fenetreInfoOpacite = 0.8;` |
| `fenetreAideOpacite` | 318 | ❌ | `private double fenetreAideOpacite = 0.8;` |
| `fenetrePoliceTaille` | 319 | ❌ | `private double fenetrePoliceTaille = 10.d;` |
| `fenetreURLPosX` | 320 | ❌ | `private double fenetreURLPosX = 0.d;` |
| `fenetreURLPosY` | 321 | ❌ | `private double fenetreURLPosY = 0.d;` |
| `fenetreOpaciteFond` | 322 | ❌ | `private double fenetreOpaciteFond = 0.8;` |
| `strFenetreInfoImage` | 323 | ❌ | `private String strFenetreInfoImage = "";` |
| `strFenetreAideImage` | 324 | ❌ | `private String strFenetreAideImage = "";` |
| `strFenetreURL` | 325 | ❌ | `private String strFenetreURL = "";` |
| `strFenetreTexteURL` | 326 | ❌ | `private String strFenetreTexteURL = "";` |
| `strFenetreURLInfobulle` | 327 | ❌ | `private String strFenetreURLInfobulle = "";` |
| `strFenetreURLCouleur` | 328 | ❌ | `private String strFenetreURLCouleur = "#FFFF00";` |
| `strFenetrePolice` | 329 | ❌ | `private String strFenetrePolice = "Verdana";` |
| `strFenetreCouleurFond` | 330 | ❌ | `private String strFenetreCouleurFond = "#ffffff";` |
| `cbFenetreInfoPersonnalise` | 332 | ❌ | `private CheckBox cbFenetreInfoPersonnalise;` |
| `cbFenetreAidePersonnalise` | 333 | ❌ | `private CheckBox cbFenetreAidePersonnalise;` |
| `tfFenetreInfoImage` | 334 | ❌ | `private TextField tfFenetreInfoImage;` |
| `tfFenetreAideImage` | 335 | ❌ | `private TextField tfFenetreAideImage;` |
| `slFenetreInfoTaille` | 336 | ❌ | `private Slider slFenetreInfoTaille;` |
| `slFenetreAideTaille` | 337 | ❌ | `private Slider slFenetreAideTaille;` |
| `bdfFenetreInfoPosX` | 338 | ❌ | `private BigDecimalField bdfFenetreInfoPosX;` |
| `bdfFenetreInfoPosY` | 339 | ❌ | `private BigDecimalField bdfFenetreInfoPosY;` |
| `bdfFenetreAidePosX` | 340 | ❌ | `private BigDecimalField bdfFenetreAidePosX;` |
| `bdfFenetreAidePosY` | 341 | ❌ | `private BigDecimalField bdfFenetreAidePosY;` |
| `slFenetreInfoOpacite` | 342 | ❌ | `private Slider slFenetreInfoOpacite;` |
| `slFenetreAideOpacite` | 343 | ❌ | `private Slider slFenetreAideOpacite;` |
| `tfFenetreTexteURL` | 344 | ❌ | `private TextField tfFenetreTexteURL;` |
| `tfFenetreURL` | 345 | ❌ | `private TextField tfFenetreURL;` |
| `tfFenetreURLInfobulle` | 346 | ❌ | `private TextField tfFenetreURLInfobulle;` |
| `tfFenetrePolice` | 347 | ❌ | `private ComboBox tfFenetrePolice;` |
| `slFenetrePoliceTaille` | 348 | ❌ | `private Slider slFenetrePoliceTaille;` |
| `bdfFenetreURLPosX` | 349 | ❌ | `private BigDecimalField bdfFenetreURLPosX;` |
| `bdfFenetreURLPosY` | 350 | ❌ | `private BigDecimalField bdfFenetreURLPosY;` |
| `cpFenetreCouleurFond` | 351 | ❌ | `private ColorPicker cpFenetreCouleurFond;` |
| `cpFenetreURLCouleur` | 352 | ❌ | `private ColorPicker cpFenetreURLCouleur;` |
| `strRepertMasques` | 359 | ✅ | `private String strRepertMasques = "";` |
| `bAfficheMasque` | 360 | ❌ | `private boolean bAfficheMasque = false;` |
| `strImageMasque` | 361 | ❌ | `private String strImageMasque = "MA.png";` |
| `strPositionMasque` | 362 | ❌ | `private String strPositionMasque = "top:right";` |
| `dXMasque` | 363 | ❌ | `private double dXMasque = 20;` |
| `dYMasque` | 364 | ❌ | `private double dYMasque = 20;` |
| `tailleMasque` | 365 | ❌ | `private double tailleMasque = 30;` |
| `opaciteMasque` | 366 | ❌ | `private double opaciteMasque = 0.8;` |
| `bMasqueNavigation` | 367 | ❌ | `private boolean bMasqueNavigation = true;` |
| `bMasqueBoussole` | 368 | ❌ | `private boolean bMasqueBoussole = true;` |
| `bMasqueTitre` | 369 | ❌ | `private boolean bMasqueTitre = true;` |
| `bMasquePlan` | 370 | ❌ | `private boolean bMasquePlan = true;` |
| `bMasqueReseaux` | 371 | ❌ | `private boolean bMasqueReseaux = true;` |
| `bMasqueVignettes` | 372 | ❌ | `private boolean bMasqueVignettes = true;` |
| `bMasqueCombo` | 373 | ❌ | `private boolean bMasqueCombo = true;` |
| `bMasqueSuivPrec` | 374 | ❌ | `private boolean bMasqueSuivPrec = true;` |
| `bMasqueHotspots` | 375 | ❌ | `private boolean bMasqueHotspots = true;` |
| `ivMasque` | 376 | ❌ | `private ImageView ivMasque;` |
| `bdfOffsetXMasque` | 377 | ❌ | `private BigDecimalField bdfOffsetXMasque;` |
| `bdfOffsetYMasque` | 378 | ❌ | `private BigDecimalField bdfOffsetYMasque;` |
| `slTailleMasque` | 379 | ❌ | `private Slider slTailleMasque;` |
| `slOpaciteMasque` | 380 | ❌ | `private Slider slOpaciteMasque;` |
| `cbAfficheMasque` | 381 | ❌ | `private CheckBox cbAfficheMasque;` |
| `cbMasqueNavigation` | 382 | ❌ | `private CheckBox cbMasqueNavigation;` |
| `cbMasqueBoussole` | 383 | ❌ | `private CheckBox cbMasqueBoussole;` |
| `cbMasqueTitre` | 384 | ❌ | `private CheckBox cbMasqueTitre;` |
| `cbMasquePlan` | 385 | ❌ | `private CheckBox cbMasquePlan;` |
| `cbMasqueReseaux` | 386 | ❌ | `private CheckBox cbMasqueReseaux;` |
| `cbMasqueVignettes` | 387 | ❌ | `private CheckBox cbMasqueVignettes;` |
| `cbMasqueCombo` | 388 | ❌ | `private CheckBox cbMasqueCombo;` |
| `cbMasqueSuivPrec` | 389 | ❌ | `private CheckBox cbMasqueSuivPrec;` |
| `cbMasqueHotspots` | 390 | ❌ | `private CheckBox cbMasqueHotspots;` |
| `rbMasqueTopLeft` | 391 | ❌ | `private RadioButton rbMasqueTopLeft;` |
| `rbMasqueTopRight` | 392 | ❌ | `private RadioButton rbMasqueTopRight;` |
| `rbMasqueBottomLeft` | 393 | ❌ | `private RadioButton rbMasqueBottomLeft;` |
| `rbMasqueBottomRight` | 394 | ❌ | `private RadioButton rbMasqueBottomRight;` |
| `strRepertReseauxSociaux` | 399 | ✅ | `private String strRepertReseauxSociaux = "";` |
| `bAfficheReseauxSociaux` | 400 | ❌ | `private boolean bAfficheReseauxSociaux = false;` |
| `strImageReseauxSociauxTwitter` | 401 | ❌ | `private String strImageReseauxSociauxTwitter = "twitter.png";` |
| `strImageReseauxSociauxGoogle` | 402 | ❌ | `private String strImageReseauxSociauxGoogle = "google.png";` |
| `strImageReseauxSociauxFacebook` | 403 | ❌ | `private String strImageReseauxSociauxFacebook = "facebook.png";` |
| `strImageReseauxSociauxEmail` | 404 | ❌ | `private String strImageReseauxSociauxEmail = "email.png";` |
| `strPositionReseauxSociaux` | 405 | ❌ | `private String strPositionReseauxSociaux = "top:right";` |
| `dXReseauxSociaux` | 406 | ❌ | `private double dXReseauxSociaux = 20;` |
| `dYReseauxSociaux` | 407 | ❌ | `private double dYReseauxSociaux = 20;` |
| `tailleReseauxSociaux` | 408 | ❌ | `private double tailleReseauxSociaux = 30;` |
| `opaciteReseauxSociaux` | 409 | ❌ | `private double opaciteReseauxSociaux = 0.8;` |
| `bReseauxSociauxTwitter` | 410 | ❌ | `private boolean bReseauxSociauxTwitter = true;` |
| `bReseauxSociauxGoogle` | 411 | ❌ | `private boolean bReseauxSociauxGoogle = true;` |
| `bReseauxSociauxFacebook` | 412 | ❌ | `private boolean bReseauxSociauxFacebook = true;` |
| `bReseauxSociauxEmail` | 413 | ❌ | `private boolean bReseauxSociauxEmail = true;` |
| `ivTwitter` | 414 | ❌ | `private ImageView ivTwitter;` |
| `ivGoogle` | 415 | ❌ | `private ImageView ivGoogle;` |
| `ivFacebook` | 416 | ❌ | `private ImageView ivFacebook;` |
| `ivEmail` | 417 | ❌ | `private ImageView ivEmail;` |
| `bdfOffsetXReseauxSociaux` | 418 | ❌ | `private BigDecimalField bdfOffsetXReseauxSociaux;` |
| `bdfOffsetYreseauxSociaux` | 419 | ❌ | `private BigDecimalField bdfOffsetYreseauxSociaux;` |
| `slTailleReseauxSociaux` | 420 | ❌ | `private Slider slTailleReseauxSociaux;` |
| `slOpaciteReseauxSociaux` | 421 | ❌ | `private Slider slOpaciteReseauxSociaux;` |
| `cbAfficheReseauxSociaux` | 422 | ❌ | `private CheckBox cbAfficheReseauxSociaux;` |
| `cbReseauxSociauxTwitter` | 423 | ❌ | `private CheckBox cbReseauxSociauxTwitter;` |
| `cbReseauxSociauxGoogle` | 424 | ❌ | `private CheckBox cbReseauxSociauxGoogle;` |
| `cbReseauxSociauxFacebook` | 425 | ❌ | `private CheckBox cbReseauxSociauxFacebook;` |
| `cbReseauxSociauxEmail` | 426 | ❌ | `private CheckBox cbReseauxSociauxEmail;` |
| `rbReseauxSociauxTopLeft` | 427 | ❌ | `private RadioButton rbReseauxSociauxTopLeft;` |
| `rbReseauxSociauxTopRight` | 428 | ❌ | `private RadioButton rbReseauxSociauxTopRight;` |
| `rbReseauxSociauxBottomLeft` | 429 | ❌ | `private RadioButton rbReseauxSociauxBottomLeft;` |
| `rbReseauxSociauxBottomRight` | 430 | ❌ | `private RadioButton rbReseauxSociauxBottomRight;` |
| `apVignettes` | 434 | ❌ | `private AnchorPane apVignettes;` |
| `apVisuVignettes` | 435 | ❌ | `private AnchorPane apVisuVignettes;` |
| `bAfficheVignettes` | 436 | ❌ | `private boolean bAfficheVignettes = false;` |
| `strCouleurFondVignettes` | 437 | ❌ | `private String strCouleurFondVignettes = "#4e8080";` |
| `strCouleurTexteVignettes` | 438 | ❌ | `private String strCouleurTexteVignettes = "#ffffff";` |
| `strPositionVignettes` | 439 | ❌ | `private String strPositionVignettes = "bottom";` |
| `tailleImageVignettes` | 440 | ❌ | `private double tailleImageVignettes = 120;` |
| `opaciteVignettes` | 441 | ❌ | `private double opaciteVignettes = 0.8;` |
| `slOpaciteVignettes` | 442 | ❌ | `private Slider slOpaciteVignettes;` |
| `slTailleVignettes` | 443 | ❌ | `private Slider slTailleVignettes;` |
| `cbAfficheVignettes` | 444 | ❌ | `private CheckBox cbAfficheVignettes;` |
| `rbVignettesLeft` | 445 | ❌ | `private RadioButton rbVignettesLeft;` |
| `rbVignettesRight` | 446 | ❌ | `private RadioButton rbVignettesRight;` |
| `rbVignettesBottom` | 447 | ❌ | `private RadioButton rbVignettesBottom;` |
| `cpCouleurFondVignettes` | 448 | ❌ | `private ColorPicker cpCouleurFondVignettes;` |
| `cpCouleurTexteVignettes` | 449 | ❌ | `private ColorPicker cpCouleurTexteVignettes;` |
| `bReplieDemarrageVignettes` | 450 | ❌ | `private boolean bReplieDemarrageVignettes = false;` |
| `cbReplieDemarrageVignettes` | 451 | ❌ | `private CheckBox cbReplieDemarrageVignettes;` |
| `apComboMenu` | 456 | ❌ | `private AnchorPane apComboMenu;` |
| `apVisuComboMenu` | 457 | ❌ | `private AnchorPane apVisuComboMenu;` |
| `bAfficheComboMenu` | 458 | ❌ | `private boolean bAfficheComboMenu = false;` |
| `bAfficheComboMenuImages` | 459 | ❌ | `private boolean bAfficheComboMenuImages = true;` |
| `strPositionXComboMenu` | 460 | ❌ | `private String strPositionXComboMenu = "left";` |
| `strPositionYComboMenu` | 461 | ❌ | `private String strPositionYComboMenu = "top";` |
| `offsetXComboMenu` | 462 | ❌ | `private double offsetXComboMenu = 10;` |
| `offsetYComboMenu` | 463 | ❌ | `private double offsetYComboMenu = 10;` |
| `cbAfficheComboMenu` | 464 | ❌ | `private CheckBox cbAfficheComboMenu;` |
| `cbAfficheComboMenuImages` | 465 | ❌ | `private CheckBox cbAfficheComboMenuImages;` |
| `bdfOffsetXComboMenu` | 466 | ❌ | `private BigDecimalField bdfOffsetXComboMenu;` |
| `bdfOffsetYComboMenu` | 467 | ❌ | `private BigDecimalField bdfOffsetYComboMenu;` |
| `rbComboMenuTopLeft` | 468 | ❌ | `private RadioButton rbComboMenuTopLeft;` |
| `rbComboMenuTopCenter` | 469 | ❌ | `private RadioButton rbComboMenuTopCenter;` |
| `rbComboMenuTopRight` | 470 | ❌ | `private RadioButton rbComboMenuTopRight;` |
| `rbComboMenuBottomLeft` | 471 | ❌ | `private RadioButton rbComboMenuBottomLeft;` |
| `rbComboMenuBottomCenter` | 472 | ❌ | `private RadioButton rbComboMenuBottomCenter;` |
| `rbComboMenuBottomRight` | 473 | ❌ | `private RadioButton rbComboMenuBottomRight;` |
| `spOutils` | 474 | ❌ | `private ScrollPane spOutils;` |
| `apVis` | 475 | ❌ | `private AnchorPane apVis;` |
| `apBoutonVisiteAuto` | 479 | ❌ | `private AnchorPane apBoutonVisiteAuto;` |
| `apVisuBoutonVisiteAuto` | 480 | ❌ | `private AnchorPane apVisuBoutonVisiteAuto;` |
| `cbAfficheBoutonVisiteAuto` | 481 | ❌ | `private CheckBox cbAfficheBoutonVisiteAuto;` |
| `bdfOffsetXBoutonVisiteAuto` | 482 | ❌ | `private BigDecimalField bdfOffsetXBoutonVisiteAuto;` |
| `bdfOffsetYBoutonVisiteAuto` | 483 | ❌ | `private BigDecimalField bdfOffsetYBoutonVisiteAuto;` |
| `rbBoutonVisiteAutoTopLeft` | 484 | ❌ | `private RadioButton rbBoutonVisiteAutoTopLeft;` |
| `rbBoutonVisiteAutoTopCenter` | 485 | ❌ | `private RadioButton rbBoutonVisiteAutoTopCenter;` |
| `rbBoutonVisiteAutoTopRight` | 486 | ❌ | `private RadioButton rbBoutonVisiteAutoTopRight;` |
| `rbBoutonVisiteAutoBottomLeft` | 487 | ❌ | `private RadioButton rbBoutonVisiteAutoBottomLeft;` |
| `rbBoutonVisiteAutoBottomCenter` | 488 | ❌ | `private RadioButton rbBoutonVisiteAutoBottomCenter;` |
| `rbBoutonVisiteAutoBottomRight` | 489 | ❌ | `private RadioButton rbBoutonVisiteAutoBottomRight;` |
| `slTailleBoutonVisiteAuto` | 490 | ❌ | `private Slider slTailleBoutonVisiteAuto;` |
| `ivBtnVisiteAuto` | 491 | ❌ | `private ImageView ivBtnVisiteAuto;` |
| `apBtnVA` | 493 | ❌ | `private AnchorPane apBtnVA;` |
| `bAfficheBoutonVisiteAuto` | 494 | ❌ | `private boolean bAfficheBoutonVisiteAuto = false;` |
| `strPositionXBoutonVisiteAuto` | 495 | ❌ | `private String strPositionXBoutonVisiteAuto = "right";` |
| `strPositionYBoutonVisiteAuto` | 496 | ❌ | `private String strPositionYBoutonVisiteAuto = "top";` |
| `offsetXBoutonVisiteAuto` | 497 | ❌ | `private double offsetXBoutonVisiteAuto = 10;` |
| `offsetYBoutonVisiteAuto` | 498 | ❌ | `private double offsetYBoutonVisiteAuto = 10;` |
| `tailleBoutonVisiteAuto` | 499 | ❌ | `private double tailleBoutonVisiteAuto = 32;` |
| `apPlan` | 504 | ❌ | `private AnchorPane apPlan;` |
| `apVisuPlan` | 505 | ❌ | `private AnchorPane apVisuPlan;` |
| `bAffichePlan` | 506 | ❌ | `private boolean bAffichePlan = false;` |
| `strPositionPlan` | 507 | ❌ | `private String strPositionPlan = "left";` |
| `largeurPlan` | 508 | ❌ | `private double largeurPlan = 200;` |
| `opacitePlan` | 511 | ❌ | `private double opacitePlan = 0.8;` |
| `bAfficheRadar` | 514 | ❌ | `private boolean bAfficheRadar = false;` |
| `tailleRadar` | 519 | ❌ | `private double tailleRadar = 40;` |
| `opaciteRadar` | 520 | ❌ | `private double opaciteRadar = 0.5;` |
| `bReplieDemarragePlan` | 521 | ❌ | `private boolean bReplieDemarragePlan = false;` |
| `bOmbreInfoBulle` | 526 | ❌ | `public boolean bOmbreInfoBulle = true;` |
| `strCouleurFondInfoBulle` | 527 | ❌ | `public String strCouleurFondInfoBulle = "#eee";` |
| `strCouleurTexteInfoBulle` | 528 | ❌ | `public String strCouleurTexteInfoBulle = "#444";` |
| `strCouleurBordureInfoBulle` | 529 | ❌ | `public String strCouleurBordureInfoBulle = "#0A0";` |
| `strPoliceInfoBulle` | 530 | ❌ | `public String strPoliceInfoBulle = "Verdana";` |
| `taillePoliceInfoBulle` | 531 | ❌ | `public double taillePoliceInfoBulle = 16;` |
| `opaciteInfoBulle` | 532 | ❌ | `public double opaciteInfoBulle = 0.75;` |
| `iTailleBordureTop` | 533 | ❌ | `public int iTailleBordureTop = 1, iTailleBordureBottom = 1, iTailleBordureLef...` |
| `iArrondiTL` | 534 | ❌ | `public int iArrondiTL = 0, iArrondiTR = 5, iArrondiBL = 0, iArrondiBR = 5;` |
| `cbOmbreInfoBulle` | 539 | ❌ | `private CheckBox cbOmbreInfoBulle;` |
| `cpCouleurFondInfoBulle` | 540 | ❌ | `private ColorPicker cpCouleurFondInfoBulle;` |
| `cpCouleurTextInfoBulle` | 541 | ❌ | `private ColorPicker cpCouleurTextInfoBulle;` |
| `cpCouleurBordureInfoBulle` | 542 | ❌ | `private ColorPicker cpCouleurBordureInfoBulle;` |
| `cbListePoliceInfoBulle` | 543 | ❌ | `private ComboBox cbListePoliceInfoBulle;` |
| `slTaillePoliceInfoBulle` | 544 | ❌ | `private Slider slTaillePoliceInfoBulle;` |
| `slOpaciteInfoBulle` | 545 | ❌ | `private Slider slOpaciteInfoBulle;` |
| `bdfTailleBordureRight` | 546 | ❌ | `private BigDecimalField bdfTailleBordureTop, bdfTailleBordureBottom, bdfTaill...` |
| `bdfArrondiBR` | 547 | ❌ | `private BigDecimalField bdfArrondiTL, bdfArrondiTR, bdfArrondiBL, bdfArrondiBR;` |
| `cbAffichePlan` | 553 | ❌ | `private CheckBox cbAffichePlan;` |
| `slOpacitePlan` | 554 | ❌ | `private Slider slOpacitePlan;` |
| `rbPlanLeft` | 555 | ❌ | `private RadioButton rbPlanLeft;` |
| `rbPlanRight` | 556 | ❌ | `private RadioButton rbPlanRight;` |
| `cpCouleurFondPlan` | 557 | ❌ | `private ColorPicker cpCouleurFondPlan;` |
| `cpCouleurTextePlan` | 558 | ❌ | `private ColorPicker cpCouleurTextePlan;` |
| `slLargeurPlan` | 559 | ❌ | `private Slider slLargeurPlan;` |
| `cbAfficheRadar` | 560 | ❌ | `private CheckBox cbAfficheRadar;` |
| `cpCouleurFondRadar` | 561 | ❌ | `private ColorPicker cpCouleurFondRadar;` |
| `cpCouleurLigneRadar` | 562 | ❌ | `private ColorPicker cpCouleurLigneRadar;` |
| `cbReplieDemarragePlan` | 563 | ❌ | `private CheckBox cbReplieDemarragePlan;` |
| `slTailleRadar` | 564 | ❌ | `private Slider slTailleRadar;` |
| `slOpaciteRadar` | 565 | ❌ | `private Slider slOpaciteRadar;` |
| `apCarte` | 569 | ❌ | `private AnchorPane apCarte;` |
| `apVisuCarte` | 570 | ❌ | `private AnchorPane apVisuCarte;` |
| `bAfficheCarte` | 571 | ❌ | `private boolean bAfficheCarte = false;` |
| `strPositionCarte` | 573 | ❌ | `private String strPositionCarte = "left";` |
| `largeurCarte` | 574 | ❌ | `private double largeurCarte = 400;` |
| `hauteurCarte` | 575 | ❌ | `private double hauteurCarte = 300;` |
| `opaciteCarte` | 578 | ❌ | `private double opaciteCarte = 0.8;` |
| `bAfficheRadarCarte` | 581 | ❌ | `private boolean bAfficheRadarCarte = false;` |
| `tailleRadarCarte` | 586 | ❌ | `private double tailleRadarCarte = 20;` |
| `opaciteRadarCarte` | 587 | ❌ | `private double opaciteRadarCarte = 0.4;` |
| `coordCentreCarte` | 588 | ❌ | `private CoordonneesGeographiques coordCentreCarte;` |
| `iFacteurZoomCarte` | 589 | ❌ | `private int iFacteurZoomCarte = 14;` |
| `angleRadarCarte` | 590 | ❌ | `private double angleRadarCarte = 45.d;` |
| `ouvertureRadarCarte` | 591 | ❌ | `private double ouvertureRadarCarte = 35.d;` |
| `strNomLayers` | 592 | ❌ | `private String strNomLayers = "OpenStreetMap";` |
| `bReplieDemarrageCarte` | 593 | ❌ | `private boolean bReplieDemarrageCarte = false;` |
| `cbAfficheCarte` | 598 | ❌ | `private CheckBox cbAfficheCarte;` |
| `slOpaciteCarte` | 599 | ❌ | `private Slider slOpaciteCarte;` |
| `rbCarteLeft` | 600 | ❌ | `private RadioButton rbCarteLeft;` |
| `rbCarteRight` | 601 | ❌ | `private RadioButton rbCarteRight;` |
| `cpCouleurFondCarte` | 602 | ❌ | `private ColorPicker cpCouleurFondCarte;` |
| `cpCouleurTexteCarte` | 603 | ❌ | `private ColorPicker cpCouleurTexteCarte;` |
| `slLargeurCarte` | 604 | ❌ | `private Slider slLargeurCarte;` |
| `slHauteurCarte` | 605 | ❌ | `private Slider slHauteurCarte;` |
| `slZoomCarte` | 606 | ❌ | `private Slider slZoomCarte;` |
| `cbAfficheRadarCarte` | 607 | ❌ | `private CheckBox cbAfficheRadarCarte;` |
| `cpCouleurFondRadarCarte` | 608 | ❌ | `private ColorPicker cpCouleurFondRadarCarte;` |
| `cpCouleurLigneRadarCarte` | 609 | ❌ | `private ColorPicker cpCouleurLigneRadarCarte;` |
| `slTailleRadarCarte` | 610 | ❌ | `private Slider slTailleRadarCarte;` |
| `slOpaciteRadarCarte` | 611 | ❌ | `private Slider slOpaciteRadarCarte;` |
| `navigateurCarteOL` | 612 | ❌ | `public NavigateurCarteGluon navigateurCarteOL = null;` |
| `cbReplieDemarrageCarte` | 613 | ❌ | `private CheckBox cbReplieDemarrageCarte;` |
| `apImageFond` | 618 | ❌ | `private AnchorPane apImageFond;` |
| `apMenuContextuel` | 623 | ❌ | `private AnchorPane apMenuContextuel;` |
| `apVisuMenuContextuel` | 624 | ❌ | `private AnchorPane apVisuMenuContextuel;` |
| `bAfficheMenuContextuel` | 625 | ❌ | `private boolean bAfficheMenuContextuel = false;` |
| `bAffichePrecSuivMC` | 626 | ❌ | `private boolean bAffichePrecSuivMC = true;` |
| `bAffichePlanetNormalMC` | 627 | ❌ | `private boolean bAffichePlanetNormalMC = true;` |
| `bAffichePersMC1` | 628 | ❌ | `private boolean bAffichePersMC1 = false;` |
| `strPersLib1` | 629 | ❌ | `private String strPersLib1 = "";` |
| `strPersURL1` | 630 | ❌ | `private String strPersURL1 = "";` |
| `bAffichePersMC2` | 631 | ❌ | `private boolean bAffichePersMC2 = false;` |
| `strPersLib2` | 632 | ❌ | `private String strPersLib2 = "";` |
| `strPersURL2` | 633 | ❌ | `private String strPersURL2 = "";` |
| `cbAfficheMenuContextuel` | 638 | ❌ | `private CheckBox cbAfficheMenuContextuel;` |
| `cbAffichePrecSuivMC` | 639 | ❌ | `private CheckBox cbAffichePrecSuivMC;` |
| `cbAffichePlanetNormalMC` | 640 | ❌ | `private CheckBox cbAffichePlanetNormalMC;` |
| `cbAffichePersMC1` | 641 | ❌ | `private CheckBox cbAffichePersMC1;` |
| `cbAffichePersMC2` | 642 | ❌ | `private CheckBox cbAffichePersMC2;` |
| `tfPersLib1` | 643 | ❌ | `private TextField tfPersLib1;` |
| `tfPersURL1` | 644 | ❌ | `private TextField tfPersURL1;` |
| `tfPersLib2` | 645 | ❌ | `private TextField tfPersLib2;` |
| `tfPersURL2` | 646 | ❌ | `private TextField tfPersURL2;` |
| `paneTabInterface` | 648 | ❌ | `public Pane paneTabInterface;` |
| `hbInterface` | 649 | ❌ | `private HBox hbInterface;` |
| `apVisualisation` | 650 | ❌ | `private AnchorPane apVisualisation;` |
| `vbOutils` | 651 | ❌ | `private VBox vbOutils;` |
| `rbClair` | 652 | ❌ | `private RadioButton rbClair;` |
| `rbSombre` | 653 | ❌ | `private RadioButton rbSombre;` |
| `rbPerso` | 654 | ❌ | `private RadioButton rbPerso;` |
| `cbImage` | 655 | ❌ | `private ComboBox cbImage;` |
| `ivVisualisation` | 656 | ❌ | `private ImageView ivVisualisation;` |
| `imgClaire` | 667 | ❌ | `private Image imgClaire;` |
| `imgSombre` | 668 | ❌ | `private Image imgSombre;` |
| `hbbarreBoutons` | 669 | ❌ | `private HBox hbbarreBoutons;` |
| `hbOutils` | 670 | ❌ | `private HBox hbOutils;` |
| `lblTxtTitre` | 671 | ❌ | `private Label lblTxtTitre;` |
| `lblTxtTitre2` | 672 | ❌ | `private Label lblTxtTitre2;` |
| `ivInfo` | 673 | ❌ | `private ImageView ivInfo;` |
| `ivAide` | 674 | ❌ | `private ImageView ivAide;` |
| `ivAutoRotation` | 675 | ❌ | `private ImageView ivAutoRotation;` |
| `ivModeSouris` | 676 | ❌ | `private ImageView ivModeSouris;` |
| `ivModeSouris2` | 677 | ❌ | `private ImageView ivModeSouris2;` |
| `ivPleinEcran` | 678 | ❌ | `private ImageView ivPleinEcran;` |
| `ivPleinEcran2` | 679 | ❌ | `private ImageView ivPleinEcran2;` |
| `hbZoom` | 680 | ❌ | `private HBox hbZoom;` |
| `ivZoomPlus` | 681 | ❌ | `private ImageView ivZoomPlus;` |
| `ivZoomMoins` | 682 | ❌ | `private ImageView ivZoomMoins;` |
| `hbDeplacements` | 683 | ❌ | `private HBox hbDeplacements;` |
| `ivHaut` | 684 | ❌ | `private ImageView ivHaut;` |
| `ivBas` | 685 | ❌ | `private ImageView ivBas;` |
| `ivGauche` | 686 | ❌ | `private ImageView ivGauche;` |
| `ivDroite` | 687 | ❌ | `private ImageView ivDroite;` |
| `ivHotSpotPanoramique` | 688 | ❌ | `private ImageView ivHotSpotPanoramique;` |
| `ivHotSpotImage` | 689 | ❌ | `private ImageView ivHotSpotImage;` |
| `ivHotSpotHTML` | 690 | ❌ | `private ImageView ivHotSpotHTML;` |
| `strRepertBoutonsPrincipal` | 692 | ❌ | `private String strRepertBoutonsPrincipal = "";` |
| `strRepertHotSpots` | 693 | ❌ | `private String strRepertHotSpots = "";` |
| `strRepertHotSpotsPhoto` | 694 | ❌ | `private String strRepertHotSpotsPhoto = "";` |
| `strRepertHotSpotsHTML` | 695 | ❌ | `private String strRepertHotSpotsHTML = "";` |
| `strRepertBoussoles` | 696 | ❌ | `private String strRepertBoussoles = "";` |
| `cbSuivantPrecedent` | 697 | ❌ | `private CheckBox cbSuivantPrecedent;` |
| `imgSuivant` | 698 | ❌ | `private ImageView imgSuivant;` |
| `imgPrecedent` | 699 | ❌ | `private ImageView imgPrecedent;` |
| `paneFondSuivant` | 700 | ❌ | `private Pane paneFondSuivant;` |
| `paneFondPrecedent` | 701 | ❌ | `private Pane paneFondPrecedent;` |
| `bSuivantPrecedent` | 702 | ❌ | `private boolean bSuivantPrecedent;` |
| `cbAfficheTitre` | 703 | ❌ | `private CheckBox cbAfficheTitre;` |
| `cbTitreVisite` | 704 | ❌ | `private CheckBox cbTitreVisite;` |
| `cbTitrePanoramique` | 705 | ❌ | `private CheckBox cbTitrePanoramique;` |
| `cbTitreAdapte` | 706 | ❌ | `private CheckBox cbTitreAdapte;` |
| `bdfTitreDecalage` | 707 | ❌ | `private BigDecimalField bdfTitreDecalage;` |
| `rbLeftTitre` | 708 | ❌ | `private RadioButton rbLeftTitre;` |
| `rbCenterTitre` | 709 | ❌ | `private RadioButton rbCenterTitre;` |
| `rbRightTitre` | 710 | ❌ | `private RadioButton rbRightTitre;` |
| `cpCouleurFondTitre` | 712 | ❌ | `private ColorPicker cpCouleurFondTitre;` |
| `cpCouleurTitre` | 713 | ❌ | `private ColorPicker cpCouleurTitre;` |
| `cbListePolicesTitre` | 714 | ❌ | `private ComboBox cbListePolicesTitre;` |
| `slTaillePoliceTitre` | 715 | ❌ | `private Slider slTaillePoliceTitre;` |
| `slOpaciteTitre` | 716 | ❌ | `private Slider slOpaciteTitre;` |
| `slTailleTitre` | 717 | ❌ | `private Slider slTailleTitre;` |
| `cpCouleurFondTheme` | 718 | ❌ | `private ColorPicker cpCouleurFondTheme;` |
| `cpCouleurTexteTheme` | 719 | ❌ | `private ColorPicker cpCouleurTexteTheme;` |
| `slOpaciteTheme` | 720 | ❌ | `private Slider slOpaciteTheme;` |
| `cbPoliceTheme` | 721 | ❌ | `private ComboBox cbPoliceTheme;` |
| `cpCouleurHotspotsPanoramique` | 722 | ❌ | `private ColorPicker cpCouleurHotspotsPanoramique;` |
| `cpCouleurHotspotsPhoto` | 723 | ❌ | `private ColorPicker cpCouleurHotspotsPhoto;` |
| `cpCouleurHotspotsHTML` | 724 | ❌ | `private ColorPicker cpCouleurHotspotsHTML;` |
| `cpCouleurMasques` | 725 | ❌ | `private ColorPicker cpCouleurMasques;` |
| `iTailleHotspotsPanoramique` | 729 | ❌ | `private int iTailleHotspotsPanoramique = 25;` |
| `iTailleHotspotsImage` | 730 | ❌ | `private int iTailleHotspotsImage = 25;` |
| `iTailleHotspotsHTML` | 731 | ❌ | `private int iTailleHotspotsHTML = 25;` |
| `slTailleHotspotsPanoramique` | 732 | ❌ | `private Slider slTailleHotspotsPanoramique;` |
| `slTailleHotspotsImage` | 733 | ❌ | `private Slider slTailleHotspotsImage;` |
| `slTailleHotspotsHTML` | 734 | ❌ | `private Slider slTailleHotspotsHTML;` |
| `opaciteTheme` | 738 | ❌ | `final private double opaciteTheme = 0.8;` |
| `imgBoutons` | 739 | ❌ | `private Image[] imgBoutons = new Image[50];` |
| `strNomImagesBoutons` | 740 | ❌ | `private String[] strNomImagesBoutons = new String[25];` |
| `prLisBoutons` | 741 | ❌ | `private PixelReader[] prLisBoutons = new PixelReader[25];` |
| `wiNouveauxBoutons` | 742 | ❌ | `private WritableImage[] wiNouveauxBoutons = new WritableImage[25];` |
| `pwNouveauxBoutons` | 743 | ❌ | `private PixelWriter[] pwNouveauxBoutons = new PixelWriter[25];` |
| `iNombreImagesBouton` | 744 | ❌ | `private int iNombreImagesBouton = 0;` |
| `imgMasque` | 745 | ❌ | `private Image imgMasque;` |
| `prLisMasque` | 746 | ❌ | `private PixelReader prLisMasque;` |
| `wiNouveauxMasque` | 747 | ❌ | `private WritableImage wiNouveauxMasque;` |
| `pwNouveauxMasque` | 748 | ❌ | `private PixelWriter pwNouveauxMasque;` |
| `i` | 759 | ❌ | `int i = 0;` |
| `prBarrePersonnalisee` | 877 | ❌ | `PixelReader prBarrePersonnalisee;` |
| `couleur` | 889 | ❌ | `Color couleur;` |
| `couleur` | 902 | ❌ | `Color couleur;` |
| `bright1` | 903 | ❌ | `double bright1;` |
| `sat1` | 904 | ❌ | `double sat1;` |
| `couleur` | 943 | ❌ | `Color couleur;` |
| `couleur` | 973 | ❌ | `Color couleur;` |
| `bright1` | 975 | ❌ | `double bright1;` |
| `sat1` | 983 | ❌ | `double sat1;` |
| `bright1` | 984 | ❌ | `double bright1;` |
| `couleur` | 1015 | ❌ | `Color couleur;` |
| `bright1` | 1017 | ❌ | `double bright1;` |
| `sat1` | 1025 | ❌ | `double sat1;` |
| `bright1` | 1026 | ❌ | `double bright1;` |
| `couleur` | 1058 | ❌ | `Color couleur;` |
| `bright1` | 1060 | ❌ | `double bright1;` |
| `sat1` | 1068 | ❌ | `double sat1;` |
| `bright1` | 1069 | ❌ | `double bright1;` |
| `couleur` | 1101 | ❌ | `Color couleur;` |
| `bright1` | 1103 | ❌ | `double bright1;` |
| `sat1` | 1111 | ❌ | `double sat1;` |
| `bright1` | 1112 | ❌ | `double bright1;` |
| `posX` | 1149 | ❌ | `double posX = 0;` |
| `posY` | 1150 | ❌ | `double posY = 0;` |
| `posX` | 1218 | ❌ | `double posX = 0;` |
| `posY` | 1219 | ❌ | `double posY = 0;` |
| `posX` | 1270 | ❌ | `double posX;` |
| `posY` | 1271 | ❌ | `double posY = 0;` |
| `dX` | 1272 | ❌ | `double dX;` |
| `marge` | 1435 | ❌ | `double marge = 10.d;` |
| `bleu` | 1447 | ❌ | `int rouge, vert, bleu;` |
| `positionX` | 1453 | ❌ | `double positionX = 0;` |
| `positionY` | 1454 | ❌ | `double positionY;` |
| `coords` | 1482 | ❌ | `CoordonneesGeographiques coords;` |
| `marge` | 1524 | ❌ | `double marge = 10.d;` |
| `imgPlan` | 1527 | ❌ | `Image imgPlan;` |
| `bleu` | 1546 | ❌ | `int rouge, vert, bleu;` |
| `positionX` | 1556 | ❌ | `double positionX = 0;` |
| `positionY` | 1557 | ❌ | `double positionY;` |
| `strImageBoussole1` | 1600 | ❌ | `String strImageBoussole1 = "file:" + strRepertImagePlan + "/aiguillePlan.png";` |
| `posX` | 1648 | ❌ | `double posX = 0, posY = 0;` |
| `ivImageMenu` | 1683 | ❌ | `ImageView ivImageMenu;` |
| `posX` | 1690 | ❌ | `double posX = 0, posY = 0;` |
| `tailleTitre` | 1755 | ❌ | `double tailleTitre = 0;` |
| `tailleTitre2` | 1762 | ❌ | `double tailleTitre2 = 0;` |
| `LX` | 1816 | ❌ | `double LX = 0;` |
| `LY` | 1817 | ❌ | `double LY = 0;` |
| `posX` | 1844 | ❌ | `double posX = 0;` |
| `posY` | 1845 | ❌ | `double posY = 0;` |
| `iMaxVignettes` | 1991 | ❌ | `int iMaxVignettes = 5;` |
| `iNumero` | 2032 | ❌ | `int iNumero = -1;` |
| `strZone` | 2033 | ❌ | `String strZone = "area-" + nb;` |
| `iNumero` | 2039 | ❌ | `return iNumero;` |
| `LX` | 2072 | ❌ | `double LX = 0;` |
| `LY` | 2073 | ❌ | `double LY = 0;` |
| `cercles` | 2121 | ❌ | `Circle[] cercles = new Circle[50];` |
| `zone` | 2122 | ❌ | `int zone = 1;` |
| `iNombreZonesBarre` | 2199 | ❌ | `int iNombreZonesBarre;` |
| `strTexte` | 2200 | ❌ | `String strTexte;` |
| `strLigneTexte` | 2204 | ❌ | `String strLigneTexte;` |
| `strLigne` | 2234 | ❌ | `String strLigne;` |
| `strElementsLigne` | 2235 | ❌ | `String[] strElementsLigne;` |
| `strTypeElement` | 2236 | ❌ | `String[] strTypeElement;` |
| `iNombreZonesBarre` | 2310 | ❌ | `return iNombreZonesBarre;` |
| `fileRepert` | 2328 | ❌ | `File fileRepert;` |
| `strNomFichierShp` | 2340 | ❌ | `String strNomFichierShp = strNomFichier + ".shp";` |
| `strNomFichierPng` | 2341 | ❌ | `String strNomFichierPng = strNomFichier + ".png";` |
| `strNomFichierShp` | 2365 | ❌ | `String strNomFichierShp = strNomFichier + ".shp";` |
| `strNomFichierPng` | 2366 | ❌ | `String strNomFichierPng = strNomFichier + ".png";` |
| `apCalques1` | 2447 | ❌ | `return apCalques1;` |
| `bCalques` | 2475 | ❌ | `Boolean[] bCalques = new Boolean[20];` |
| `strRepertBoutons` | 2596 | ❌ | `String strRepertBoutons = "file:" + strRepertBoutonsPrincipal + File.separato...` |
| `posX` | 2605 | ❌ | `double posX = 0, posY = 0;` |
| `iNombreBoutons` | 2684 | ❌ | `int iNombreBoutons = 11;` |
| `LX` | 2761 | ❌ | `double LX = 0;` |
| `LY` | 2762 | ❌ | `double LY = 0;` |
| `strListe` | 2830 | ❌ | `return strListe;` |
| `strListe` | 2847 | ❌ | `return strListe;` |
| `strListe` | 2866 | ❌ | `return strListe;` |
| `strListe` | 2883 | ❌ | `return strListe;` |
| `coords` | 2892 | ❌ | `CoordonneesGeographiques coords;` |
| `fileRepertoirePlan` | 3143 | ❌ | `File fileRepertoirePlan;` |
| `strValeur` | 3152 | ❌ | `String strValeur = "";` |
| `strValeur` | 3245 | ❌ | `String strValeur = "";` |
| `strImgAffiche` | 4302 | ❌ | `String strImgAffiche = "";` |
| `strHTML` | 4333 | ❌ | `String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-we...` |
| `hauteurPanel` | 4386 | ❌ | `double hauteurPanel = 500;` |
| `ij` | 4391 | ❌ | `int ij = i;` |
| `iPosX` | 4484 | ❌ | `int iPosX = 160;` |
| `iPosY` | 4485 | ❌ | `int iPosY = 30;` |
| `fileRepert` | 4642 | ❌ | `File fileRepert;` |
| `txt` | 4814 | ❌ | `String txt = newValue;` |
| `txt` | 4824 | ❌ | `String txt = newValue;` |
| `fileRepert` | 4854 | ❌ | `File fileRepert;` |
| `fileRepert` | 4892 | ❌ | `File fileRepert;` |
| `strStyle` | 4932 | ❌ | `String strStyle = "-fx-margin : 10px;-fx-background-color: " + strCouleurFond...` |
| `ivHotspots` | 4986 | ❌ | `ImageView[] ivHotspots = new ImageView[iNombreHotSpots];` |
| `ivHotspotsPhoto` | 4989 | ❌ | `ImageView[] ivHotspotsPhoto = new ImageView[iNombreHotSpotsPhoto];` |
| `ivHotspotsHTML` | 4990 | ❌ | `ImageView[] ivHotspotsHTML = new ImageView[iNombreHotSpotsHTML];` |
| `iPosX1` | 5275 | ❌ | `int iPosX1 = 180;` |
| `iPosY1` | 5276 | ❌ | `int iPosY1 = 60;` |
| `i` | 5764 | ❌ | `int i = 0;` |
| `xHS` | 5765 | ❌ | `double xHS;` |
| `yHS` | 5766 | ❌ | `double yHS = 25;` |
| `iCol` | 5772 | ❌ | `int iCol = i % 9;` |
| `iRow` | 5773 | ❌ | `int iRow = i / 9;` |
| `iCol` | 5838 | ❌ | `int iCol = i % 9;` |
| `iRow` | 5839 | ❌ | `int iRow = i / 9;` |
| `iCol` | 5901 | ❌ | `int iCol = i % 9;` |
| `iRow` | 5902 | ❌ | `int iRow = i / 9;` |
| `iPosX` | 6024 | ❌ | `int iPosX = 250;` |
| `iPosY` | 6025 | ❌ | `int iPosY = 140;` |
| `iPos1X` | 6206 | ❌ | `int iPos1X = 250;` |
| `iPos1Y` | 6207 | ❌ | `int iPos1Y = 240;` |
| `ivBoussoles` | 6386 | ❌ | `ImageView[] ivBoussoles = new ImageView[iNombreBoussoles];` |
| `iCol` | 6388 | ❌ | `int iCol;` |
| `iRow` | 6389 | ❌ | `int iRow;` |
| `ivMasques` | 6539 | ❌ | `ImageView[] ivMasques = new ImageView[iNombreMasques];` |
| `iCol1` | 6544 | ❌ | `int iCol1 = i % 4;` |
| `iRow1` | 6545 | ❌ | `int iRow1 = i / 4;` |
| `viewportRect` | 7641 | ❌ | `Rectangle2D viewportRect;` |
| `strHTML` | 9280 | ❌ | `String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-we...` |
| `offsetXBarreClassique` | 9642 | ❌ | `return offsetXBarreClassique;` |
| `offsetYBarreClassique` | 9656 | ❌ | `return offsetYBarreClassique;` |
| `tailleBarreClassique` | 9670 | ❌ | `return tailleBarreClassique;` |
| `espacementBarreClassique` | 9684 | ❌ | `return espacementBarreClassique;` |
| `strStyleDefautBarreClassique` | 9698 | ❌ | `return strStyleDefautBarreClassique;` |
| `strPositionBarreClassique` | 9705 | ❌ | `return strPositionBarreClassique;` |
| `styleBarreClassique` | 9719 | ❌ | `return styleBarreClassique;` |
| `strDeplacementsBarreClassique` | 9733 | ❌ | `return strDeplacementsBarreClassique;` |
| `strZoomBarreClassique` | 9748 | ❌ | `return strZoomBarreClassique;` |
| `strOutilsBarreClassique` | 9762 | ❌ | `return strOutilsBarreClassique;` |
| `strRotationBarreClassique` | 9776 | ❌ | `return strRotationBarreClassique;` |
| `strPleinEcranBarreClassique` | 9790 | ❌ | `return strPleinEcranBarreClassique;` |
| `strSourisBarreClassique` | 9804 | ❌ | `return strSourisBarreClassique;` |
| `strVisibiliteBarreClassique` | 9818 | ❌ | `return strVisibiliteBarreClassique;` |
| `bCouleurOrigineBarrePersonnalisee` | 9832 | ❌ | `return bCouleurOrigineBarrePersonnalisee;` |
| `iNombreZonesBarrePersonnalisee` | 9847 | ❌ | `return iNombreZonesBarrePersonnalisee;` |
| `offsetXBarrePersonnalisee` | 9862 | ❌ | `return offsetXBarrePersonnalisee;` |
| `offsetYBarrePersonnalisee` | 9876 | ❌ | `return offsetYBarrePersonnalisee;` |
| `tailleBarrePersonnalisee` | 9890 | ❌ | `return tailleBarrePersonnalisee;` |
| `tailleIconesBarrePersonnalisee` | 9904 | ❌ | `return tailleIconesBarrePersonnalisee;` |
| `strPositionBarrePersonnalisee` | 9919 | ❌ | `return strPositionBarrePersonnalisee;` |
| `strDeplacementsBarrePersonnalisee` | 9934 | ❌ | `return strDeplacementsBarrePersonnalisee;` |
| `strZoomBarrePersonnalisee` | 9949 | ❌ | `return strZoomBarrePersonnalisee;` |
| `strInfoBarrePersonnalisee` | 9963 | ❌ | `return strInfoBarrePersonnalisee;` |
| `strAideBarrePersonnalisee` | 9977 | ❌ | `return strAideBarrePersonnalisee;` |
| `strRotationBarrePersonnalisee` | 9991 | ❌ | `return strRotationBarrePersonnalisee;` |
| `strPleinEcranBarrePersonnalisee` | 10006 | ❌ | `return strPleinEcranBarrePersonnalisee;` |
| `strSourisBarrePersonnalisee` | 10021 | ❌ | `return strSourisBarrePersonnalisee;` |
| `strVisibiliteBarrePersonnalisee` | 10035 | ❌ | `return strVisibiliteBarrePersonnalisee;` |
| `strLienImageBarrePersonnalisee` | 10050 | ❌ | `return strLienImageBarrePersonnalisee;` |
| `strLien1BarrePersonnalisee` | 10065 | ❌ | `return strLien1BarrePersonnalisee;` |
| `strLien2BarrePersonnalisee` | 10079 | ❌ | `return strLien2BarrePersonnalisee;` |
| `wiBarrePersonnaliseeCouleur` | 10093 | ❌ | `return wiBarrePersonnaliseeCouleur;` |
| `bAfficheTitre` | 10107 | ❌ | `return bAfficheTitre;` |
| `bAfficheDescription` | 10121 | ❌ | `return bAfficheDescription;` |
| `cbAfficheDescription` | 10135 | ❌ | `return cbAfficheDescription;` |
| `poDescription` | 10142 | ❌ | `return poDescription;` |
| `strTitrePoliceNom` | 10157 | ❌ | `return strTitrePoliceNom;` |
| `strTitrePoliceStyle` | 10171 | ❌ | `return strTitrePoliceStyle;` |
| `strTitrePoliceTaille` | 10185 | ❌ | `return strTitrePoliceTaille;` |
| `strCouleurTitre` | 10199 | ❌ | `return strCouleurTitre;` |
| `strCouleurFondTitre` | 10213 | ❌ | `return strCouleurFondTitre;` |
| `titreOpacite` | 10227 | ❌ | `return titreOpacite;` |
| `titreTaille` | 10241 | ❌ | `return titreTaille;` |
| `bAfficheBoussole` | 10255 | ❌ | `return bAfficheBoussole;` |
| `strImageBoussole` | 10269 | ❌ | `return strImageBoussole;` |
| `strPositionBoussole` | 10283 | ❌ | `return strPositionBoussole;` |
| `offsetXBoussole` | 10297 | ❌ | `return offsetXBoussole;` |
| `offsetYBoussole` | 10311 | ❌ | `return offsetYBoussole;` |
| `tailleBoussole` | 10325 | ❌ | `return tailleBoussole;` |
| `opaciteBoussole` | 10339 | ❌ | `return opaciteBoussole;` |
| `bAiguilleMobileBoussole` | 10353 | ❌ | `return bAiguilleMobileBoussole;` |
| `bFenetreInfoPersonnalise` | 10367 | ❌ | `return bFenetreInfoPersonnalise;` |
| `bFenetreAidePersonnalise` | 10381 | ❌ | `return bFenetreAidePersonnalise;` |
| `fenetreInfoTaille` | 10395 | ❌ | `return fenetreInfoTaille;` |
| `fenetreAideTaille` | 10409 | ❌ | `return fenetreAideTaille;` |
| `fenetreInfoPosX` | 10423 | ❌ | `return fenetreInfoPosX;` |
| `fenetreInfoPosY` | 10437 | ❌ | `return fenetreInfoPosY;` |
| `fenetreAidePosX` | 10451 | ❌ | `return fenetreAidePosX;` |
| `fenetreAidePosY` | 10465 | ❌ | `return fenetreAidePosY;` |
| `fenetreInfoposX` | 10479 | ❌ | `return fenetreInfoposX;` |
| `fenetreInfoOpacite` | 10493 | ❌ | `return fenetreInfoOpacite;` |
| `fenetreAideOpacite` | 10507 | ❌ | `return fenetreAideOpacite;` |
| `fenetrePoliceTaille` | 10521 | ❌ | `return fenetrePoliceTaille;` |
| `fenetreURLPosX` | 10535 | ❌ | `return fenetreURLPosX;` |
| `fenetreURLPosY` | 10549 | ❌ | `return fenetreURLPosY;` |
| `fenetreOpaciteFond` | 10563 | ❌ | `return fenetreOpaciteFond;` |
| `strFenetreInfoImage` | 10577 | ❌ | `return strFenetreInfoImage;` |
| `strFenetreAideImage` | 10591 | ❌ | `return strFenetreAideImage;` |
| `strFenetreURL` | 10605 | ❌ | `return strFenetreURL;` |
| `strFenetreTexteURL` | 10619 | ❌ | `return strFenetreTexteURL;` |
| `strFenetreURLInfobulle` | 10633 | ❌ | `return strFenetreURLInfobulle;` |
| `strFenetreURLCouleur` | 10647 | ❌ | `return strFenetreURLCouleur;` |
| `strFenetrePolice` | 10661 | ❌ | `return strFenetrePolice;` |
| `strFenetreCouleurFond` | 10675 | ❌ | `return strFenetreCouleurFond;` |
| `bAfficheMasque` | 10689 | ❌ | `return bAfficheMasque;` |
| `strImageMasque` | 10703 | ❌ | `return strImageMasque;` |
| `strPositionMasque` | 10717 | ❌ | `return strPositionMasque;` |
| `dXMasque` | 10731 | ❌ | `return dXMasque;` |
| `dYMasque` | 10745 | ❌ | `return dYMasque;` |
| `tailleMasque` | 10759 | ❌ | `return tailleMasque;` |
| `opaciteMasque` | 10773 | ❌ | `return opaciteMasque;` |
| `bMasqueNavigation` | 10787 | ❌ | `return bMasqueNavigation;` |
| `bMasqueBoussole` | 10801 | ❌ | `return bMasqueBoussole;` |
| `bMasqueTitre` | 10815 | ❌ | `return bMasqueTitre;` |
| `bMasquePlan` | 10829 | ❌ | `return bMasquePlan;` |
| `bMasqueReseaux` | 10843 | ❌ | `return bMasqueReseaux;` |
| `bMasqueVignettes` | 10857 | ❌ | `return bMasqueVignettes;` |
| `bMasqueCombo` | 10871 | ❌ | `return bMasqueCombo;` |
| `bMasqueSuivPrec` | 10885 | ❌ | `return bMasqueSuivPrec;` |
| `bMasqueHotspots` | 10899 | ❌ | `return bMasqueHotspots;` |
| `bAfficheReseauxSociaux` | 10913 | ❌ | `return bAfficheReseauxSociaux;` |
| `strImageReseauxSociauxTwitter` | 10927 | ❌ | `return strImageReseauxSociauxTwitter;` |
| `strImageReseauxSociauxGoogle` | 10942 | ❌ | `return strImageReseauxSociauxGoogle;` |
| `strImageReseauxSociauxFacebook` | 10957 | ❌ | `return strImageReseauxSociauxFacebook;` |
| `strImageReseauxSociauxEmail` | 10972 | ❌ | `return strImageReseauxSociauxEmail;` |
| `strPositionReseauxSociaux` | 10986 | ❌ | `return strPositionReseauxSociaux;` |
| `dXReseauxSociaux` | 11000 | ❌ | `return dXReseauxSociaux;` |
| `dYReseauxSociaux` | 11014 | ❌ | `return dYReseauxSociaux;` |
| `tailleReseauxSociaux` | 11028 | ❌ | `return tailleReseauxSociaux;` |
| `opaciteReseauxSociaux` | 11042 | ❌ | `return opaciteReseauxSociaux;` |
| `bReseauxSociauxTwitter` | 11056 | ❌ | `return bReseauxSociauxTwitter;` |
| `bReseauxSociauxGoogle` | 11070 | ❌ | `return bReseauxSociauxGoogle;` |
| `bReseauxSociauxFacebook` | 11084 | ❌ | `return bReseauxSociauxFacebook;` |
| `bReseauxSociauxEmail` | 11098 | ❌ | `return bReseauxSociauxEmail;` |
| `bAfficheVignettes` | 11112 | ❌ | `return bAfficheVignettes;` |
| `strCouleurFondVignettes` | 11126 | ❌ | `return strCouleurFondVignettes;` |
| `strCouleurTexteVignettes` | 11140 | ❌ | `return strCouleurTexteVignettes;` |
| `strPositionVignettes` | 11154 | ❌ | `return strPositionVignettes;` |
| `tailleImageVignettes` | 11168 | ❌ | `return tailleImageVignettes;` |
| `opaciteVignettes` | 11182 | ❌ | `return opaciteVignettes;` |
| `bAfficheComboMenu` | 11196 | ❌ | `return bAfficheComboMenu;` |
| `bAfficheComboMenuImages` | 11210 | ❌ | `return bAfficheComboMenuImages;` |
| `strPositionXComboMenu` | 11224 | ❌ | `return strPositionXComboMenu;` |
| `strPositionYComboMenu` | 11238 | ❌ | `return strPositionYComboMenu;` |
| `offsetXComboMenu` | 11252 | ❌ | `return offsetXComboMenu;` |
| `offsetYComboMenu` | 11266 | ❌ | `return offsetYComboMenu;` |
| `bAffichePlan` | 11280 | ❌ | `return bAffichePlan;` |
| `strPositionPlan` | 11294 | ❌ | `return strPositionPlan;` |
| `largeurPlan` | 11308 | ❌ | `return largeurPlan;` |
| `couleurFondPlan` | 11322 | ❌ | `return couleurFondPlan;` |
| `strCouleurFondPlan` | 11336 | ❌ | `return strCouleurFondPlan;` |
| `opacitePlan` | 11350 | ❌ | `return opacitePlan;` |
| `couleurTextePlan` | 11364 | ❌ | `return couleurTextePlan;` |
| `strCouleurTextePlan` | 11378 | ❌ | `return strCouleurTextePlan;` |
| `bAfficheRadar` | 11392 | ❌ | `return bAfficheRadar;` |
| `couleurLigneRadar` | 11406 | ❌ | `return couleurLigneRadar;` |
| `strCouleurLigneRadar` | 11420 | ❌ | `return strCouleurLigneRadar;` |
| `couleurFondRadar` | 11434 | ❌ | `return couleurFondRadar;` |
| `strCouleurFondRadar` | 11448 | ❌ | `return strCouleurFondRadar;` |
| `tailleRadar` | 11462 | ❌ | `return tailleRadar;` |
| `opaciteRadar` | 11476 | ❌ | `return opaciteRadar;` |
| `bAfficheMenuContextuel` | 11490 | ❌ | `return bAfficheMenuContextuel;` |
| `bAffichePrecSuivMC` | 11504 | ❌ | `return bAffichePrecSuivMC;` |
| `bAffichePlanetNormalMC` | 11518 | ❌ | `return bAffichePlanetNormalMC;` |
| `bAffichePersMC1` | 11532 | ❌ | `return bAffichePersMC1;` |
| `strPersLib1` | 11546 | ❌ | `return strPersLib1;` |
| `strPersURL1` | 11560 | ❌ | `return strPersURL1;` |
| `bAffichePersMC2` | 11574 | ❌ | `return bAffichePersMC2;` |
| `strPersLib2` | 11588 | ❌ | `return strPersLib2;` |
| `strPersURL2` | 11602 | ❌ | `return strPersURL2;` |
| `bSuivantPrecedent` | 11616 | ❌ | `return bSuivantPrecedent;` |
| `imgBoutons` | 11630 | ❌ | `return imgBoutons;` |
| `strNomImagesBoutons` | 11644 | ❌ | `return strNomImagesBoutons;` |
| `prLisBoutons` | 11658 | ❌ | `return prLisBoutons;` |
| `wiNouveauxBoutons` | 11672 | ❌ | `return wiNouveauxBoutons;` |
| `pwNouveauxBoutons` | 11686 | ❌ | `return pwNouveauxBoutons;` |
| `iNombreImagesBouton` | 11700 | ❌ | `return iNombreImagesBouton;` |
| `imgMasque` | 11714 | ❌ | `return imgMasque;` |
| `prLisMasque` | 11728 | ❌ | `return prLisMasque;` |
| `wiNouveauxMasque` | 11742 | ❌ | `return wiNouveauxMasque;` |
| `pwNouveauxMasque` | 11756 | ❌ | `return pwNouveauxMasque;` |
| `imagesFond` | 11770 | ❌ | `return imagesFond;` |
| `iNombreImagesFond` | 11784 | ❌ | `return iNombreImagesFond;` |
| `strStyleHotSpots` | 11798 | ❌ | `return strStyleHotSpots;` |
| `strStyleHotSpotImages` | 11812 | ❌ | `return strStyleHotSpotImages;` |
| `zonesBarrePersonnalisee` | 11826 | ❌ | `return zonesBarrePersonnalisee;` |
| `strStyleHotSpotHTML` | 11840 | ❌ | `return strStyleHotSpotHTML;` |
| `bAfficheCarte` | 11854 | ❌ | `return bAfficheCarte;` |
| `strPositionCarte` | 11868 | ❌ | `return strPositionCarte;` |
| `largeurCarte` | 11882 | ❌ | `return largeurCarte;` |
| `couleurFondCarte` | 11896 | ❌ | `return couleurFondCarte;` |
| `strCouleurFondCarte` | 11910 | ❌ | `return strCouleurFondCarte;` |
| `opaciteCarte` | 11924 | ❌ | `return opaciteCarte;` |
| `couleurTexteCarte` | 11938 | ❌ | `return couleurTexteCarte;` |
| `strCouleurTexteCarte` | 11952 | ❌ | `return strCouleurTexteCarte;` |
| `bAfficheRadarCarte` | 11966 | ❌ | `return bAfficheRadarCarte;` |
| `couleurLigneRadarCarte` | 11980 | ❌ | `return couleurLigneRadarCarte;` |
| `strCouleurLigneRadarCarte` | 11994 | ❌ | `return strCouleurLigneRadarCarte;` |
| `couleurFondRadarCarte` | 12008 | ❌ | `return couleurFondRadarCarte;` |
| `strCouleurFondRadarCarte` | 12022 | ❌ | `return strCouleurFondRadarCarte;` |
| `tailleRadarCarte` | 12036 | ❌ | `return tailleRadarCarte;` |
| `opaciteRadarCarte` | 12050 | ❌ | `return opaciteRadarCarte;` |
| `hauteurCarte` | 12064 | ❌ | `return hauteurCarte;` |
| `iFacteurZoomCarte` | 12078 | ❌ | `return iFacteurZoomCarte;` |
| `coordCentreCarte` | 12092 | ❌ | `return coordCentreCarte;` |
| `slZoomCarte` | 12106 | ❌ | `return slZoomCarte;` |
| `strNomLayers` | 12120 | ❌ | `return strNomLayers;` |
| `bReplieDemarrageCarte` | 12135 | ❌ | `return bReplieDemarrageCarte;` |
| `bReplieDemarragePlan` | 12151 | ❌ | `return bReplieDemarragePlan;` |
| `bReplieDemarrageVignettes` | 12167 | ❌ | `return bReplieDemarrageVignettes;` |
| `bTemplate` | 12182 | ❌ | `return bTemplate;` |
| `bAfficheBoutonVisiteAuto` | 12196 | ❌ | `return bAfficheBoutonVisiteAuto;` |
| `strPositionXBoutonVisiteAuto` | 12210 | ❌ | `return strPositionXBoutonVisiteAuto;` |
| `strPositionYBoutonVisiteAuto` | 12225 | ❌ | `return strPositionYBoutonVisiteAuto;` |
| `offsetXBoutonVisiteAuto` | 12240 | ❌ | `return offsetXBoutonVisiteAuto;` |
| `offsetYBoutonVisiteAuto` | 12254 | ❌ | `return offsetYBoutonVisiteAuto;` |
| `cbAfficheBoutonVisiteAuto` | 12268 | ❌ | `return cbAfficheBoutonVisiteAuto;` |
| `apBtnVA` | 12282 | ❌ | `return apBtnVA;` |
| `tailleBoutonVisiteAuto` | 12296 | ❌ | `return tailleBoutonVisiteAuto;` |
| `strTitrePosition` | 12310 | ❌ | `return strTitrePosition;` |
| `titreDecalage` | 12324 | ❌ | `return titreDecalage;` |
| `bTitreVisite` | 12338 | ❌ | `return bTitreVisite;` |
| `bTitrePanoramique` | 12352 | ❌ | `return bTitrePanoramique;` |
| `bTitreAdapte` | 12366 | ❌ | `return bTitreAdapte;` |
| `iTailleHotspotsPanoramique` | 12380 | ❌ | `return iTailleHotspotsPanoramique;` |
| `iTailleHotspotsImage` | 12394 | ❌ | `return iTailleHotspotsImage;` |
| `iTailleHotspotsHTML` | 12408 | ❌ | `return iTailleHotspotsHTML;` |
| `couleurFondTheme` | 12422 | ❌ | `return couleurFondTheme;` |
| `opaciteTheme` | 12436 | ❌ | `return opaciteTheme;` |
| `iCalqueTitre` | 12443 | ❌ | `return iCalqueTitre;` |
| `iCalqueBarreClassique` | 12457 | ❌ | `return iCalqueBarreClassique;` |
| `iCalqueBarrePersonnalisee` | 12471 | ❌ | `return iCalqueBarrePersonnalisee;` |
| `iCalqueMasquage` | 12485 | ❌ | `return iCalqueMasquage;` |
| `iCalqueVisiteAuto` | 12499 | ❌ | `return iCalqueVisiteAuto;` |
| `iCalquePartage` | 12513 | ❌ | `return iCalquePartage;` |
| `iCalquePlan` | 12527 | ❌ | `return iCalquePlan;` |
| `iCalqueCarte` | 12541 | ❌ | `return iCalqueCarte;` |
| `iCalqueBoussole` | 12555 | ❌ | `return iCalqueBoussole;` |
| `iCalqueVignettes` | 12569 | ❌ | `return iCalqueVignettes;` |
| `iCalqueMenuPanoramiques` | 12583 | ❌ | `return iCalqueMenuPanoramiques;` |
| `iCalqueSuivPrec` | 12597 | ❌ | `return iCalqueSuivPrec;` |
| `spOutils` | 12611 | ❌ | `return spOutils;` |
| `apVis` | 12625 | ❌ | `return apVis;` |

#### Méthodes (471)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `GestionnaireInterfaceController` | 100 | ❌ | `public GestionnaireInterfaceController() {` |
| `chargeBarre` | 756 | ✅ | `private void chargeBarre(String strStyleBarre, String strHotSpot, String strM...` |
| `changeCouleurBarrePersonnalisee` | 876 | ✅ | `private void changeCouleurBarrePersonnalisee(double couleurFinale, double sat...` |
| `changeCouleurBarreClassique` | 935 | ✅ | `private void changeCouleurBarreClassique(double couleurFinale, double sat, do...` |
| `changeCouleurMasque` | 966 | ✅ | `private void changeCouleurMasque(double couleurFinale, double sat, double bri...` |
| `changeCouleurHS` | 1007 | ✅ | `private void changeCouleurHS(double couleurFinale, double sat, double bright) {` |
| `changeCouleurHSPhoto` | 1050 | ✅ | `private void changeCouleurHSPhoto(double couleurFinale, double sat, double br...` |
| `changeCouleurHSHTML` | 1093 | ✅ | `private void changeCouleurHSHTML(double couleurFinale, double sat, double bri...` |
| `afficheBoussole` | 1133 | ✅ | `private void afficheBoussole() {` |
| `afficheImage` | 1199 | ✅ | `private void afficheImage(int index) {` |
| `afficheMasque` | 1209 | ✅ | `private void afficheMasque() {` |
| `afficheReseauxSociaux` | 1243 | ✅ | `private void afficheReseauxSociaux() {` |
| `afficheFenetreInfo` | 1342 | ✅ | `public void afficheFenetreInfo() {` |
| `afficheFenetreAide` | 1386 | ✅ | `public void afficheFenetreAide() {` |
| `afficheCarte` | 1422 | ✅ | `public void afficheCarte() {` |
| `getTailleRadarCarte` | 1495 | ❌ | `coords, angleRadarCarte, ouvertureRadarCarte, getTailleRadarCarte(), "#" + ge...` |
| `getTailleRadarCarte` | 1500 | ❌ | `coords, angleRadarCarte, ouvertureRadarCarte, getTailleRadarCarte(), "#" + ge...` |
| `affichePlan` | 1514 | ✅ | `public void affichePlan() {` |
| `afficheBoutonVisiteAuto` | 1638 | ✅ | `public void afficheBoutonVisiteAuto() {` |
| `afficheComboMenu` | 1678 | ✅ | `private void afficheComboMenu() {` |
| `afficheTitre` | 1718 | ✅ | `private void afficheTitre() {` |
| `afficheVignettes` | 1811 | ✅ | `private void afficheVignettes() {` |
| `chercheZone` | 2031 | ✅ | `private int chercheZone(int nb) {` |
| `afficheBarrePersonnalisee` | 2045 | ✅ | `public void afficheBarrePersonnalisee() {` |
| `lisFichierShp` | 2195 | ✅ | `public int lisFichierShp(String strNomFichier, ZoneTelecommande[] zonesBarre)...` |
| `FileInputStream` | 2202 | ❌ | `new FileInputStream(strNomFichier), "UTF-8"))) {` |
| `choixBarrePersonnalisee` | 2326 | ✅ | `public void choixBarrePersonnalisee() throws IOException {` |
| `chargeBarrePersonnalisee` | 2362 | ✅ | `public void chargeBarrePersonnalisee(String strNomFichier) throws IOException {` |
| `apAfficheCalques` | 2386 | ✅ | `private AnchorPane apAfficheCalques() {` |
| `reOrdonneElementsCalque` | 2453 | ✅ | `public void reOrdonneElementsCalque() {` |
| `afficheBarreClassique` | 2595 | ✅ | `public void afficheBarreClassique(String strPosition, double dX, double dY, d...` |
| `strListerStyle` | 2819 | ✅ | `private ArrayList<String> strListerStyle(String strRepertoire) {` |
| `strListerHotSpots` | 2838 | ✅ | `private ArrayList<String> strListerHotSpots(String strRepertoire) {` |
| `strListerBoussoles` | 2855 | ✅ | `private ArrayList<String> strListerBoussoles(String strRepertoire) {` |
| `strListerMasques` | 2874 | ✅ | `private ArrayList<String> strListerMasques(String strRepertoire) {` |
| `strGetTemplate` | 2890 | ✅ | `public String strGetTemplate() {` |
| `setTemplate` | 3126 | ✅ | `public void setTemplate(List<String> strTemplate) {` |
| `afficheTemplate` | 3896 | ✅ | `public void afficheTemplate() throws IOException {` |
| `rafraichit` | 4297 | ✅ | `public void rafraichit() {` |
| `retireImageFond` | 4357 | ✅ | `private void retireImageFond(int iNumImage) {` |
| `afficheImagesFondInterface` | 4366 | ❌ | `private void afficheImagesFondInterface() {` |
| `afficheImagesFondInterface` | 4373 | ✅ | `private void afficheImagesFondInterface(boolean bNouvelle) {` |
| `ajoutFenetreImage` | 4853 | ✅ | `private String ajoutFenetreImage() {` |
| `ajoutImageFond` | 4889 | ✅ | `private void ajoutImageFond() {` |
| `styleInfoBulle` | 4931 | ❌ | `public void styleInfoBulle() {` |
| `creeInterface` | 4951 | ✅ | `public void creeInterface(int iLargeur, int iHauteur) {` |
| `getOffsetXBarreClassique` | 9641 | ✅ | `public double getOffsetXBarreClassique() {` |
| `setOffsetXBarreClassique` | 9648 | ✅ | `public void setOffsetXBarreClassique(double offsetXBarreClassique) {` |
| `getOffsetYBarreClassique` | 9655 | ✅ | `public double getOffsetYBarreClassique() {` |
| `setOffsetYBarreClassique` | 9662 | ✅ | `public void setOffsetYBarreClassique(double offsetYBarreClassique) {` |
| `getTailleBarreClassique` | 9669 | ✅ | `public double getTailleBarreClassique() {` |
| `setTailleBarreClassique` | 9676 | ✅ | `public void setTailleBarreClassique(double tailleBarreClassique) {` |
| `getEspacementBarreClassique` | 9683 | ✅ | `public double getEspacementBarreClassique() {` |
| `setEspacementBarreClassique` | 9690 | ✅ | `public void setEspacementBarreClassique(double espacementBarreClassique) {` |
| `getStrStyleDefautBarreClassique` | 9697 | ✅ | `public String getStrStyleDefautBarreClassique() {` |
| `getStrPositionBarreClassique` | 9704 | ✅ | `public String getStrPositionBarreClassique() {` |
| `setStrPositionBarreClassique` | 9711 | ✅ | `public void setStrPositionBarreClassique(String strPositionBarreClassique) {` |
| `getStyleBarreClassique` | 9718 | ✅ | `public String getStyleBarreClassique() {` |
| `setStyleBarreClassique` | 9725 | ✅ | `public void setStyleBarreClassique(String styleBarreClassique) {` |
| `getStrDeplacementsBarreClassique` | 9732 | ✅ | `public String getStrDeplacementsBarreClassique() {` |
| `setStrDeplacementsBarreClassique` | 9740 | ✅ | `public void setStrDeplacementsBarreClassique(String strDeplacementsBarreClass...` |
| `getStrZoomBarreClassique` | 9747 | ✅ | `public String getStrZoomBarreClassique() {` |
| `setStrZoomBarreClassique` | 9754 | ✅ | `public void setStrZoomBarreClassique(String strZoomBarreClassique) {` |
| `getStrOutilsBarreClassique` | 9761 | ✅ | `public String getStrOutilsBarreClassique() {` |
| `setStrOutilsBarreClassique` | 9768 | ✅ | `public void setStrOutilsBarreClassique(String strOutilsBarreClassique) {` |
| `getStrRotationBarreClassique` | 9775 | ✅ | `public String getStrRotationBarreClassique() {` |
| `setStrRotationBarreClassique` | 9782 | ✅ | `public void setStrRotationBarreClassique(String strRotationBarreClassique) {` |
| `getStrPleinEcranBarreClassique` | 9789 | ✅ | `public String getStrPleinEcranBarreClassique() {` |
| `setStrPleinEcranBarreClassique` | 9796 | ✅ | `public void setStrPleinEcranBarreClassique(String strPleinEcranBarreClassique) {` |
| `getStrSourisBarreClassique` | 9803 | ✅ | `public String getStrSourisBarreClassique() {` |
| `setStrSourisBarreClassique` | 9810 | ✅ | `public void setStrSourisBarreClassique(String strSourisBarreClassique) {` |
| `getStrVisibiliteBarreClassique` | 9817 | ✅ | `public String getStrVisibiliteBarreClassique() {` |
| `setStrVisibiliteBarreClassique` | 9824 | ✅ | `public void setStrVisibiliteBarreClassique(String strVisibiliteBarreClassique) {` |
| `isbCouleurOrigineBarrePersonnalisee` | 9831 | ✅ | `public boolean isbCouleurOrigineBarrePersonnalisee() {` |
| `setbCouleurOrigineBarrePersonnalisee` | 9839 | ✅ | `public void setbCouleurOrigineBarrePersonnalisee(boolean bCouleurOrigineBarre...` |
| `getiNombreZonesBarrePersonnalisee` | 9846 | ✅ | `public int getiNombreZonesBarrePersonnalisee() {` |
| `setiNombreZonesBarrePersonnalisee` | 9854 | ✅ | `public void setiNombreZonesBarrePersonnalisee(int iNombreZonesBarrePersonnali...` |
| `getOffsetXBarrePersonnalisee` | 9861 | ✅ | `public double getOffsetXBarrePersonnalisee() {` |
| `setOffsetXBarrePersonnalisee` | 9868 | ✅ | `public void setOffsetXBarrePersonnalisee(double offsetXBarrePersonnalisee) {` |
| `getOffsetYBarrePersonnalisee` | 9875 | ✅ | `public double getOffsetYBarrePersonnalisee() {` |
| `setOffsetYBarrePersonnalisee` | 9882 | ✅ | `public void setOffsetYBarrePersonnalisee(double offsetYBarrePersonnalisee) {` |
| `getTailleBarrePersonnalisee` | 9889 | ✅ | `public double getTailleBarrePersonnalisee() {` |
| `setTailleBarrePersonnalisee` | 9896 | ✅ | `public void setTailleBarrePersonnalisee(double tailleBarrePersonnalisee) {` |
| `getTailleIconesBarrePersonnalisee` | 9903 | ✅ | `public double getTailleIconesBarrePersonnalisee() {` |
| `setTailleIconesBarrePersonnalisee` | 9911 | ✅ | `public void setTailleIconesBarrePersonnalisee(double tailleIconesBarrePersonn...` |
| `getStrPositionBarrePersonnalisee` | 9918 | ✅ | `public String getStrPositionBarrePersonnalisee() {` |
| `setStrPositionBarrePersonnalisee` | 9926 | ✅ | `public void setStrPositionBarrePersonnalisee(String strPositionBarrePersonnal...` |
| `getStrDeplacementsBarrePersonnalisee` | 9933 | ✅ | `public String getStrDeplacementsBarrePersonnalisee() {` |
| `setStrDeplacementsBarrePersonnalisee` | 9941 | ✅ | `public void setStrDeplacementsBarrePersonnalisee(String strDeplacementsBarreP...` |
| `getStrZoomBarrePersonnalisee` | 9948 | ✅ | `public String getStrZoomBarrePersonnalisee() {` |
| `setStrZoomBarrePersonnalisee` | 9955 | ✅ | `public void setStrZoomBarrePersonnalisee(String strZoomBarrePersonnalisee) {` |
| `getStrInfoBarrePersonnalisee` | 9962 | ✅ | `public String getStrInfoBarrePersonnalisee() {` |
| `setStrInfoBarrePersonnalisee` | 9969 | ✅ | `public void setStrInfoBarrePersonnalisee(String strInfoBarrePersonnalisee) {` |
| `getStrAideBarrePersonnalisee` | 9976 | ✅ | `public String getStrAideBarrePersonnalisee() {` |
| `setStrAideBarrePersonnalisee` | 9983 | ✅ | `public void setStrAideBarrePersonnalisee(String strAideBarrePersonnalisee) {` |
| `getStrRotationBarrePersonnalisee` | 9990 | ✅ | `public String getStrRotationBarrePersonnalisee() {` |
| `setStrRotationBarrePersonnalisee` | 9998 | ✅ | `public void setStrRotationBarrePersonnalisee(String strRotationBarrePersonnal...` |
| `getStrPleinEcranBarrePersonnalisee` | 10005 | ✅ | `public String getStrPleinEcranBarrePersonnalisee() {` |
| `setStrPleinEcranBarrePersonnalisee` | 10013 | ✅ | `public void setStrPleinEcranBarrePersonnalisee(String strPleinEcranBarrePerso...` |
| `getStrSourisBarrePersonnalisee` | 10020 | ✅ | `public String getStrSourisBarrePersonnalisee() {` |
| `setStrSourisBarrePersonnalisee` | 10027 | ✅ | `public void setStrSourisBarrePersonnalisee(String strSourisBarrePersonnalisee) {` |
| `getStrVisibiliteBarrePersonnalisee` | 10034 | ✅ | `public String getStrVisibiliteBarrePersonnalisee() {` |
| `setStrVisibiliteBarrePersonnalisee` | 10042 | ✅ | `public void setStrVisibiliteBarrePersonnalisee(String strVisibiliteBarrePerso...` |
| `getStrLienImageBarrePersonnalisee` | 10049 | ✅ | `public String getStrLienImageBarrePersonnalisee() {` |
| `setStrLienImageBarrePersonnalisee` | 10057 | ✅ | `public void setStrLienImageBarrePersonnalisee(String strLienImageBarrePersonn...` |
| `getStrLien1BarrePersonnalisee` | 10064 | ✅ | `public String getStrLien1BarrePersonnalisee() {` |
| `setStrLien1BarrePersonnalisee` | 10071 | ✅ | `public void setStrLien1BarrePersonnalisee(String strLien1BarrePersonnalisee) {` |
| `getStrLien2BarrePersonnalisee` | 10078 | ✅ | `public String getStrLien2BarrePersonnalisee() {` |
| `setStrLien2BarrePersonnalisee` | 10085 | ✅ | `public void setStrLien2BarrePersonnalisee(String strLien2BarrePersonnalisee) {` |
| `getWiBarrePersonnaliseeCouleur` | 10092 | ✅ | `public WritableImage getWiBarrePersonnaliseeCouleur() {` |
| `setWiBarrePersonnaliseeCouleur` | 10099 | ✅ | `public void setWiBarrePersonnaliseeCouleur(WritableImage wiBarrePersonnalisee...` |
| `isbAfficheTitre` | 10106 | ✅ | `public boolean isbAfficheTitre() {` |
| `setbAfficheTitre` | 10113 | ✅ | `public void setbAfficheTitre(boolean bAfficheTitre) {` |
| `isbAfficheDescription` | 10120 | ✅ | `public boolean isbAfficheDescription() {` |
| `setbAfficheDescription` | 10127 | ✅ | `public void setbAfficheDescription(boolean bAfficheDescription) {` |
| `getCbAfficheDescription` | 10134 | ✅ | `public CheckBox getCbAfficheDescription() {` |
| `getPoDescription` | 10141 | ✅ | `public PaneOutil getPoDescription() {` |
| `setbChargementEnCours` | 10149 | ✅ | `public void setbChargementEnCours(boolean bChargement) {` |
| `getStrTitrePoliceNom` | 10156 | ✅ | `public String getStrTitrePoliceNom() {` |
| `setStrTitrePoliceNom` | 10163 | ✅ | `public void setStrTitrePoliceNom(String strTitrePoliceNom) {` |
| `getStrTitrePoliceStyle` | 10170 | ✅ | `public String getStrTitrePoliceStyle() {` |
| `setStrTitrePoliceStyle` | 10177 | ✅ | `public void setStrTitrePoliceStyle(String strTitrePoliceStyle) {` |
| `getStrTitrePoliceTaille` | 10184 | ✅ | `public String getStrTitrePoliceTaille() {` |
| `setStrTitrePoliceTaille` | 10191 | ✅ | `public void setStrTitrePoliceTaille(String strTitrePoliceTaille) {` |
| `getStrCouleurTitre` | 10198 | ✅ | `public String getStrCouleurTitre() {` |
| `setStrCouleurTitre` | 10205 | ✅ | `public void setStrCouleurTitre(String strCouleurTitre) {` |
| `getStrCouleurFondTitre` | 10212 | ✅ | `public String getStrCouleurFondTitre() {` |
| `setStrCouleurFondTitre` | 10219 | ✅ | `public void setStrCouleurFondTitre(String strCouleurFondTitre) {` |
| `getTitreOpacite` | 10226 | ✅ | `public double getTitreOpacite() {` |
| `setTitreOpacite` | 10233 | ✅ | `public void setTitreOpacite(double titreOpacite) {` |
| `getTitreTaille` | 10240 | ✅ | `public double getTitreTaille() {` |
| `setTitreTaille` | 10247 | ✅ | `public void setTitreTaille(double titreTaille) {` |
| `isbAfficheBoussole` | 10254 | ✅ | `public boolean isbAfficheBoussole() {` |
| `setbAfficheBoussole` | 10261 | ✅ | `public void setbAfficheBoussole(boolean bAfficheBoussole) {` |
| `getStrImageBoussole` | 10268 | ✅ | `public String getStrImageBoussole() {` |
| `setStrImageBoussole` | 10275 | ✅ | `public void setStrImageBoussole(String strImageBoussole) {` |
| `getStrPositionBoussole` | 10282 | ✅ | `public String getStrPositionBoussole() {` |
| `setStrPositionBoussole` | 10289 | ✅ | `public void setStrPositionBoussole(String strPositionBoussole) {` |
| `getOffsetXBoussole` | 10296 | ✅ | `public double getOffsetXBoussole() {` |
| `setOffsetXBoussole` | 10303 | ✅ | `public void setOffsetXBoussole(double offsetXBoussole) {` |
| `getOffsetYBoussole` | 10310 | ✅ | `public double getOffsetYBoussole() {` |
| `setOffsetYBoussole` | 10317 | ✅ | `public void setOffsetYBoussole(double offsetYBoussole) {` |
| `getTailleBoussole` | 10324 | ✅ | `public double getTailleBoussole() {` |
| `setTailleBoussole` | 10331 | ✅ | `public void setTailleBoussole(double tailleBoussole) {` |
| `getOpaciteBoussole` | 10338 | ✅ | `public double getOpaciteBoussole() {` |
| `setOpaciteBoussole` | 10345 | ✅ | `public void setOpaciteBoussole(double opaciteBoussole) {` |
| `isbAiguilleMobileBoussole` | 10352 | ✅ | `public boolean isbAiguilleMobileBoussole() {` |
| `setbAiguilleMobileBoussole` | 10359 | ✅ | `public void setbAiguilleMobileBoussole(boolean bAiguilleMobileBoussole) {` |
| `isbFenetreInfoPersonnalise` | 10366 | ✅ | `public boolean isbFenetreInfoPersonnalise() {` |
| `setbFenetreInfoPersonnalise` | 10373 | ✅ | `public void setbFenetreInfoPersonnalise(boolean bFenetreInfoPersonnalise) {` |
| `isbFenetreAidePersonnalise` | 10380 | ✅ | `public boolean isbFenetreAidePersonnalise() {` |
| `setbFenetreAidePersonnalise` | 10387 | ✅ | `public void setbFenetreAidePersonnalise(boolean bFenetreAidePersonnalise) {` |
| `getFenetreInfoTaille` | 10394 | ✅ | `public double getFenetreInfoTaille() {` |
| `setFenetreInfoTaille` | 10401 | ✅ | `public void setFenetreInfoTaille(double fenetreInfoTaille) {` |
| `getFenetreAideTaille` | 10408 | ✅ | `public double getFenetreAideTaille() {` |
| `setFenetreAideTaille` | 10415 | ✅ | `public void setFenetreAideTaille(double fenetreAideTaille) {` |
| `getFenetreInfoPosX` | 10422 | ✅ | `public double getFenetreInfoPosX() {` |
| `setFenetreInfoPosX` | 10429 | ✅ | `public void setFenetreInfoPosX(double fenetreInfoPosX) {` |
| `getFenetreInfoPosY` | 10436 | ✅ | `public double getFenetreInfoPosY() {` |
| `setFenetreInfoPosY` | 10443 | ✅ | `public void setFenetreInfoPosY(double fenetreInfoPosY) {` |
| `getFenetreAidePosX` | 10450 | ✅ | `public double getFenetreAidePosX() {` |
| `setFenetreAidePosX` | 10457 | ✅ | `public void setFenetreAidePosX(double fenetreAidePosX) {` |
| `getFenetreAidePosY` | 10464 | ✅ | `public double getFenetreAidePosY() {` |
| `setFenetreAidePosY` | 10471 | ✅ | `public void setFenetreAidePosY(double fenetreAidePosY) {` |
| `getFenetreInfoposX` | 10478 | ✅ | `public double getFenetreInfoposX() {` |
| `setFenetreInfoposX` | 10485 | ✅ | `public void setFenetreInfoposX(double fenetreInfoposX) {` |
| `getFenetreInfoOpacite` | 10492 | ✅ | `public double getFenetreInfoOpacite() {` |
| `setFenetreInfoOpacite` | 10499 | ✅ | `public void setFenetreInfoOpacite(double fenetreInfoOpacite) {` |
| `getFenetreAideOpacite` | 10506 | ✅ | `public double getFenetreAideOpacite() {` |
| `setFenetreAideOpacite` | 10513 | ✅ | `public void setFenetreAideOpacite(double fenetreAideOpacite) {` |
| `getFenetrePoliceTaille` | 10520 | ✅ | `public double getFenetrePoliceTaille() {` |
| `setFenetrePoliceTaille` | 10527 | ✅ | `public void setFenetrePoliceTaille(double fenetrePoliceTaille) {` |
| `getFenetreURLPosX` | 10534 | ✅ | `public double getFenetreURLPosX() {` |
| `setFenetreURLPosX` | 10541 | ✅ | `public void setFenetreURLPosX(double fenetreURLPosX) {` |
| `getFenetreURLPosY` | 10548 | ✅ | `public double getFenetreURLPosY() {` |
| `setFenetreURLPosY` | 10555 | ✅ | `public void setFenetreURLPosY(double fenetreURLPosY) {` |
| `getFenetreOpaciteFond` | 10562 | ✅ | `public double getFenetreOpaciteFond() {` |
| `setFenetreOpaciteFond` | 10569 | ✅ | `public void setFenetreOpaciteFond(double fenetreOpaciteFond) {` |
| `getStrFenetreInfoImage` | 10576 | ✅ | `public String getStrFenetreInfoImage() {` |
| `setStrFenetreInfoImage` | 10583 | ✅ | `public void setStrFenetreInfoImage(String strFenetreInfoImage) {` |
| `getStrFenetreAideImage` | 10590 | ✅ | `public String getStrFenetreAideImage() {` |
| `setStrFenetreAideImage` | 10597 | ✅ | `public void setStrFenetreAideImage(String strFenetreAideImage) {` |
| `getStrFenetreURL` | 10604 | ✅ | `public String getStrFenetreURL() {` |
| `setStrFenetreURL` | 10611 | ✅ | `public void setStrFenetreURL(String strFenetreURL) {` |
| `getStrFenetreTexteURL` | 10618 | ✅ | `public String getStrFenetreTexteURL() {` |
| `setStrFenetreTexteURL` | 10625 | ✅ | `public void setStrFenetreTexteURL(String strFenetreTexteURL) {` |
| `getStrFenetreURLInfobulle` | 10632 | ✅ | `public String getStrFenetreURLInfobulle() {` |
| `setStrFenetreURLInfobulle` | 10639 | ✅ | `public void setStrFenetreURLInfobulle(String strFenetreURLInfobulle) {` |
| `getStrFenetreURLCouleur` | 10646 | ✅ | `public String getStrFenetreURLCouleur() {` |
| `setStrFenetreURLCouleur` | 10653 | ✅ | `public void setStrFenetreURLCouleur(String strFenetreURLCouleur) {` |
| `getStrFenetrePolice` | 10660 | ✅ | `public String getStrFenetrePolice() {` |
| `setStrFenetrePolice` | 10667 | ✅ | `public void setStrFenetrePolice(String strFenetrePolice) {` |
| `getStrFenetreCouleurFond` | 10674 | ✅ | `public String getStrFenetreCouleurFond() {` |
| `setStrFenetreCouleurFond` | 10681 | ✅ | `public void setStrFenetreCouleurFond(String strFenetreCouleurFond) {` |
| `isbAfficheMasque` | 10688 | ✅ | `public boolean isbAfficheMasque() {` |
| `setbAfficheMasque` | 10695 | ✅ | `public void setbAfficheMasque(boolean bAfficheMasque) {` |
| `getStrImageMasque` | 10702 | ✅ | `public String getStrImageMasque() {` |
| `setStrImageMasque` | 10709 | ✅ | `public void setStrImageMasque(String strImageMasque) {` |
| `getStrPositionMasque` | 10716 | ✅ | `public String getStrPositionMasque() {` |
| `setStrPositionMasque` | 10723 | ✅ | `public void setStrPositionMasque(String strPositionMasque) {` |
| `getdXMasque` | 10730 | ✅ | `public double getdXMasque() {` |
| `setdXMasque` | 10737 | ✅ | `public void setdXMasque(double dXMasque) {` |
| `getdYMasque` | 10744 | ✅ | `public double getdYMasque() {` |
| `setdYMasque` | 10751 | ✅ | `public void setdYMasque(double dYMasque) {` |
| `getTailleMasque` | 10758 | ✅ | `public double getTailleMasque() {` |
| `setTailleMasque` | 10765 | ✅ | `public void setTailleMasque(double tailleMasque) {` |
| `getOpaciteMasque` | 10772 | ✅ | `public double getOpaciteMasque() {` |
| `setOpaciteMasque` | 10779 | ✅ | `public void setOpaciteMasque(double opaciteMasque) {` |
| `isbMasqueNavigation` | 10786 | ✅ | `public boolean isbMasqueNavigation() {` |
| `setbMasqueNavigation` | 10793 | ✅ | `public void setbMasqueNavigation(boolean bMasqueNavigation) {` |
| `isbMasqueBoussole` | 10800 | ✅ | `public boolean isbMasqueBoussole() {` |
| `setbMasqueBoussole` | 10807 | ✅ | `public void setbMasqueBoussole(boolean bMasqueBoussole) {` |
| `isbMasqueTitre` | 10814 | ✅ | `public boolean isbMasqueTitre() {` |
| `setbMasqueTitre` | 10821 | ✅ | `public void setbMasqueTitre(boolean bMasqueTitre) {` |
| `isbMasquePlan` | 10828 | ✅ | `public boolean isbMasquePlan() {` |
| `setbMasquePlan` | 10835 | ✅ | `public void setbMasquePlan(boolean bMasquePlan) {` |
| `isbMasqueReseaux` | 10842 | ✅ | `public boolean isbMasqueReseaux() {` |
| `setbMasqueReseaux` | 10849 | ✅ | `public void setbMasqueReseaux(boolean bMasqueReseaux) {` |
| `isbMasqueVignettes` | 10856 | ✅ | `public boolean isbMasqueVignettes() {` |
| `setbMasqueVignettes` | 10863 | ✅ | `public void setbMasqueVignettes(boolean bMasqueVignettes) {` |
| `isbMasqueCombo` | 10870 | ✅ | `public boolean isbMasqueCombo() {` |
| `setbMasqueCombo` | 10877 | ✅ | `public void setbMasqueCombo(boolean bMasqueCombo) {` |
| `isbMasqueSuivPrec` | 10884 | ✅ | `public boolean isbMasqueSuivPrec() {` |
| `setbMasqueSuivPrec` | 10891 | ✅ | `public void setbMasqueSuivPrec(boolean bMasqueSuivPrec) {` |
| `isbMasqueHotspots` | 10898 | ✅ | `public boolean isbMasqueHotspots() {` |
| `setbMasqueHotspots` | 10905 | ✅ | `public void setbMasqueHotspots(boolean bMasqueHotspots) {` |
| `isbAfficheReseauxSociaux` | 10912 | ✅ | `public boolean isbAfficheReseauxSociaux() {` |
| `setbAfficheReseauxSociaux` | 10919 | ✅ | `public void setbAfficheReseauxSociaux(boolean bAfficheReseauxSociaux) {` |
| `getStrImageReseauxSociauxTwitter` | 10926 | ✅ | `public String getStrImageReseauxSociauxTwitter() {` |
| `setStrImageReseauxSociauxTwitter` | 10934 | ✅ | `public void setStrImageReseauxSociauxTwitter(String strImageReseauxSociauxTwi...` |
| `getStrImageReseauxSociauxGoogle` | 10941 | ✅ | `public String getStrImageReseauxSociauxGoogle() {` |
| `setStrImageReseauxSociauxGoogle` | 10949 | ✅ | `public void setStrImageReseauxSociauxGoogle(String strImageReseauxSociauxGoog...` |
| `getStrImageReseauxSociauxFacebook` | 10956 | ✅ | `public String getStrImageReseauxSociauxFacebook() {` |
| `setStrImageReseauxSociauxFacebook` | 10964 | ✅ | `public void setStrImageReseauxSociauxFacebook(String strImageReseauxSociauxFa...` |
| `getStrImageReseauxSociauxEmail` | 10971 | ✅ | `public String getStrImageReseauxSociauxEmail() {` |
| `setStrImageReseauxSociauxEmail` | 10978 | ✅ | `public void setStrImageReseauxSociauxEmail(String strImageReseauxSociauxEmail) {` |
| `getStrPositionReseauxSociaux` | 10985 | ✅ | `public String getStrPositionReseauxSociaux() {` |
| `setStrPositionReseauxSociaux` | 10992 | ✅ | `public void setStrPositionReseauxSociaux(String strPositionReseauxSociaux) {` |
| `getdXReseauxSociaux` | 10999 | ✅ | `public double getdXReseauxSociaux() {` |
| `setdXReseauxSociaux` | 11006 | ✅ | `public void setdXReseauxSociaux(double dXReseauxSociaux) {` |
| `getdYReseauxSociaux` | 11013 | ✅ | `public double getdYReseauxSociaux() {` |
| `setdYReseauxSociaux` | 11020 | ✅ | `public void setdYReseauxSociaux(double dYReseauxSociaux) {` |
| `getTailleReseauxSociaux` | 11027 | ✅ | `public double getTailleReseauxSociaux() {` |
| `setTailleReseauxSociaux` | 11034 | ✅ | `public void setTailleReseauxSociaux(double tailleReseauxSociaux) {` |
| `getOpaciteReseauxSociaux` | 11041 | ✅ | `public double getOpaciteReseauxSociaux() {` |
| `setOpaciteReseauxSociaux` | 11048 | ✅ | `public void setOpaciteReseauxSociaux(double opaciteReseauxSociaux) {` |
| `isbReseauxSociauxTwitter` | 11055 | ✅ | `public boolean isbReseauxSociauxTwitter() {` |
| `setbReseauxSociauxTwitter` | 11062 | ✅ | `public void setbReseauxSociauxTwitter(boolean bReseauxSociauxTwitter) {` |
| `isbReseauxSociauxGoogle` | 11069 | ✅ | `public boolean isbReseauxSociauxGoogle() {` |
| `setbReseauxSociauxGoogle` | 11076 | ✅ | `public void setbReseauxSociauxGoogle(boolean bReseauxSociauxGoogle) {` |
| `isbReseauxSociauxFacebook` | 11083 | ✅ | `public boolean isbReseauxSociauxFacebook() {` |
| `setbReseauxSociauxFacebook` | 11090 | ✅ | `public void setbReseauxSociauxFacebook(boolean bReseauxSociauxFacebook) {` |
| `isbReseauxSociauxEmail` | 11097 | ✅ | `public boolean isbReseauxSociauxEmail() {` |
| `setbReseauxSociauxEmail` | 11104 | ✅ | `public void setbReseauxSociauxEmail(boolean bReseauxSociauxEmail) {` |
| `isbAfficheVignettes` | 11111 | ✅ | `public boolean isbAfficheVignettes() {` |
| `setbAfficheVignettes` | 11118 | ✅ | `public void setbAfficheVignettes(boolean bAfficheVignettes) {` |
| `getStrCouleurFondVignettes` | 11125 | ✅ | `public String getStrCouleurFondVignettes() {` |
| `setStrCouleurFondVignettes` | 11132 | ✅ | `public void setStrCouleurFondVignettes(String strCouleurFondVignettes) {` |
| `getStrCouleurTexteVignettes` | 11139 | ✅ | `public String getStrCouleurTexteVignettes() {` |
| `setStrCouleurTexteVignettes` | 11146 | ✅ | `public void setStrCouleurTexteVignettes(String strCouleurTexteVignettes) {` |
| `getStrPositionVignettes` | 11153 | ✅ | `public String getStrPositionVignettes() {` |
| `setStrPositionVignettes` | 11160 | ✅ | `public void setStrPositionVignettes(String strPositionVignettes) {` |
| `getTailleImageVignettes` | 11167 | ✅ | `public double getTailleImageVignettes() {` |
| `setTailleImageVignettes` | 11174 | ✅ | `public void setTailleImageVignettes(double tailleImageVignettes) {` |
| `getOpaciteVignettes` | 11181 | ✅ | `public double getOpaciteVignettes() {` |
| `setOpaciteVignettes` | 11188 | ✅ | `public void setOpaciteVignettes(double opaciteVignettes) {` |
| `isbAfficheComboMenu` | 11195 | ✅ | `public boolean isbAfficheComboMenu() {` |
| `setbAfficheComboMenu` | 11202 | ✅ | `public void setbAfficheComboMenu(boolean bAfficheComboMenu) {` |
| `isbAfficheComboMenuImages` | 11209 | ✅ | `public boolean isbAfficheComboMenuImages() {` |
| `setbAfficheComboMenuImages` | 11216 | ✅ | `public void setbAfficheComboMenuImages(boolean bAfficheComboMenuImages) {` |
| `getStrPositionXComboMenu` | 11223 | ✅ | `public String getStrPositionXComboMenu() {` |
| `setStrPositionXComboMenu` | 11230 | ✅ | `public void setStrPositionXComboMenu(String strPositionXComboMenu) {` |
| `getStrPositionYComboMenu` | 11237 | ✅ | `public String getStrPositionYComboMenu() {` |
| `setStrPositionYComboMenu` | 11244 | ✅ | `public void setStrPositionYComboMenu(String strPositionYComboMenu) {` |
| `getOffsetXComboMenu` | 11251 | ✅ | `public double getOffsetXComboMenu() {` |
| `setOffsetXComboMenu` | 11258 | ✅ | `public void setOffsetXComboMenu(double offsetXComboMenu) {` |
| `getOffsetYComboMenu` | 11265 | ✅ | `public double getOffsetYComboMenu() {` |
| `setOffsetYComboMenu` | 11272 | ✅ | `public void setOffsetYComboMenu(double offsetYComboMenu) {` |
| `isbAffichePlan` | 11279 | ✅ | `public boolean isbAffichePlan() {` |
| `setbAffichePlan` | 11286 | ✅ | `public void setbAffichePlan(boolean bAffichePlan) {` |
| `getStrPositionPlan` | 11293 | ✅ | `public String getStrPositionPlan() {` |
| `setStrPositionPlan` | 11300 | ✅ | `public void setStrPositionPlan(String strPositionPlan) {` |
| `getLargeurPlan` | 11307 | ✅ | `public double getLargeurPlan() {` |
| `setLargeurPlan` | 11314 | ✅ | `public void setLargeurPlan(double largeurPlan) {` |
| `getCouleurFondPlan` | 11321 | ✅ | `public Color getCouleurFondPlan() {` |
| `setCouleurFondPlan` | 11328 | ✅ | `public void setCouleurFondPlan(Color couleurFondPlan) {` |
| `getStrCouleurFondPlan` | 11335 | ✅ | `public String getStrCouleurFondPlan() {` |
| `setStrCouleurFondPlan` | 11342 | ✅ | `public void setStrCouleurFondPlan(String strCouleurFondPlan) {` |
| `getOpacitePlan` | 11349 | ✅ | `public double getOpacitePlan() {` |
| `setOpacitePlan` | 11356 | ✅ | `public void setOpacitePlan(double opacitePlan) {` |
| `getCouleurTextePlan` | 11363 | ✅ | `public Color getCouleurTextePlan() {` |
| `setCouleurTextePlan` | 11370 | ✅ | `public void setCouleurTextePlan(Color couleurTextePlan) {` |
| `getStrCouleurTextePlan` | 11377 | ✅ | `public String getStrCouleurTextePlan() {` |
| `setStrCouleurTextePlan` | 11384 | ✅ | `public void setStrCouleurTextePlan(String strCouleurTextePlan) {` |
| `isbAfficheRadar` | 11391 | ✅ | `public boolean isbAfficheRadar() {` |
| `setbAfficheRadar` | 11398 | ✅ | `public void setbAfficheRadar(boolean bAfficheRadar) {` |
| `getCouleurLigneRadar` | 11405 | ✅ | `public Color getCouleurLigneRadar() {` |
| `setCouleurLigneRadar` | 11412 | ✅ | `public void setCouleurLigneRadar(Color couleurLigneRadar) {` |
| `getStrCouleurLigneRadar` | 11419 | ✅ | `public String getStrCouleurLigneRadar() {` |
| `setStrCouleurLigneRadar` | 11426 | ✅ | `public void setStrCouleurLigneRadar(String strCouleurLigneRadar) {` |
| `getCouleurFondRadar` | 11433 | ✅ | `public Color getCouleurFondRadar() {` |
| `setCouleurFondRadar` | 11440 | ✅ | `public void setCouleurFondRadar(Color couleurFondRadar) {` |
| `getStrCouleurFondRadar` | 11447 | ✅ | `public String getStrCouleurFondRadar() {` |
| `setStrCouleurFondRadar` | 11454 | ✅ | `public void setStrCouleurFondRadar(String strCouleurFondRadar) {` |
| `getTailleRadar` | 11461 | ✅ | `public double getTailleRadar() {` |
| `setTailleRadar` | 11468 | ✅ | `public void setTailleRadar(double tailleRadar) {` |
| `getOpaciteRadar` | 11475 | ✅ | `public double getOpaciteRadar() {` |
| `setOpaciteRadar` | 11482 | ✅ | `public void setOpaciteRadar(double opaciteRadar) {` |
| `isbAfficheMenuContextuel` | 11489 | ✅ | `public boolean isbAfficheMenuContextuel() {` |
| `setbAfficheMenuContextuel` | 11496 | ✅ | `public void setbAfficheMenuContextuel(boolean bAfficheMenuContextuel) {` |
| `isbAffichePrecSuivMC` | 11503 | ✅ | `public boolean isbAffichePrecSuivMC() {` |
| `setbAffichePrecSuivMC` | 11510 | ✅ | `public void setbAffichePrecSuivMC(boolean bAffichePrecSuivMC) {` |
| `isbAffichePlanetNormalMC` | 11517 | ✅ | `public boolean isbAffichePlanetNormalMC() {` |
| `setbAffichePlanetNormalMC` | 11524 | ✅ | `public void setbAffichePlanetNormalMC(boolean bAffichePlanetNormalMC) {` |
| `isbAffichePersMC1` | 11531 | ✅ | `public boolean isbAffichePersMC1() {` |
| `setbAffichePersMC1` | 11538 | ✅ | `public void setbAffichePersMC1(boolean bAffichePersMC1) {` |
| `getStrPersLib1` | 11545 | ✅ | `public String getStrPersLib1() {` |
| `setStrPersLib1` | 11552 | ✅ | `public void setStrPersLib1(String strPersLib1) {` |
| `getStrPersURL1` | 11559 | ✅ | `public String getStrPersURL1() {` |
| `setStrPersURL1` | 11566 | ✅ | `public void setStrPersURL1(String strPersURL1) {` |
| `isbAffichePersMC2` | 11573 | ✅ | `public boolean isbAffichePersMC2() {` |
| `setbAffichePersMC2` | 11580 | ✅ | `public void setbAffichePersMC2(boolean bAffichePersMC2) {` |
| `getStrPersLib2` | 11587 | ✅ | `public String getStrPersLib2() {` |
| `setStrPersLib2` | 11594 | ✅ | `public void setStrPersLib2(String strPersLib2) {` |
| `getStrPersURL2` | 11601 | ✅ | `public String getStrPersURL2() {` |
| `setStrPersURL2` | 11608 | ✅ | `public void setStrPersURL2(String strPersURL2) {` |
| `isbSuivantPrecedent` | 11615 | ✅ | `public boolean isbSuivantPrecedent() {` |
| `setbSuivantPrecedent` | 11622 | ✅ | `public void setbSuivantPrecedent(boolean bSuivantPrecedent) {` |
| `getImgBoutons` | 11629 | ✅ | `public Image[] getImgBoutons() {` |
| `setImgBoutons` | 11636 | ✅ | `public void setImgBoutons(Image[] imgBoutons) {` |
| `getStrNomImagesBoutons` | 11643 | ✅ | `public String[] getStrNomImagesBoutons() {` |
| `setStrNomImagesBoutons` | 11650 | ✅ | `public void setStrNomImagesBoutons(String[] strNomImagesBoutons) {` |
| `getPrLisBoutons` | 11657 | ✅ | `public PixelReader[] getPrLisBoutons() {` |
| `setPrLisBoutons` | 11664 | ✅ | `public void setPrLisBoutons(PixelReader[] prLisBoutons) {` |
| `getWiNouveauxBoutons` | 11671 | ✅ | `public WritableImage[] getWiNouveauxBoutons() {` |
| `setWiNouveauxBoutons` | 11678 | ✅ | `public void setWiNouveauxBoutons(WritableImage[] wiNouveauxBoutons) {` |
| `getPwNouveauxBoutons` | 11685 | ✅ | `public PixelWriter[] getPwNouveauxBoutons() {` |
| `setPwNouveauxBoutons` | 11692 | ✅ | `public void setPwNouveauxBoutons(PixelWriter[] pwNouveauxBoutons) {` |
| `getiNombreImagesBouton` | 11699 | ✅ | `public int getiNombreImagesBouton() {` |
| `setiNombreImagesBouton` | 11706 | ✅ | `public void setiNombreImagesBouton(int iNombreImagesBouton) {` |
| `getImgMasque` | 11713 | ✅ | `public Image getImgMasque() {` |
| `setImgMasque` | 11720 | ✅ | `public void setImgMasque(Image imgMasque) {` |
| `getPrLisMasque` | 11727 | ✅ | `public PixelReader getPrLisMasque() {` |
| `setPrLisMasque` | 11734 | ✅ | `public void setPrLisMasque(PixelReader prLisMasque) {` |
| `getWiNouveauxMasque` | 11741 | ✅ | `public WritableImage getWiNouveauxMasque() {` |
| `setWiNouveauxMasque` | 11748 | ✅ | `public void setWiNouveauxMasque(WritableImage wiNouveauxMasque) {` |
| `getPwNouveauxMasque` | 11755 | ✅ | `public PixelWriter getPwNouveauxMasque() {` |
| `setPwNouveauxMasque` | 11762 | ✅ | `public void setPwNouveauxMasque(PixelWriter pwNouveauxMasque) {` |
| `getImagesFond` | 11769 | ✅ | `public ImageFond[] getImagesFond() {` |
| `setImagesFond` | 11776 | ✅ | `public void setImagesFond(ImageFond[] imagesFond) {` |
| `getiNombreImagesFond` | 11783 | ✅ | `public int getiNombreImagesFond() {` |
| `setiNombreImagesFond` | 11790 | ✅ | `public void setiNombreImagesFond(int iNombreImagesFond) {` |
| `getStrStyleHotSpots` | 11797 | ✅ | `public String getStrStyleHotSpots() {` |
| `setStrStyleHotSpots` | 11804 | ✅ | `public void setStrStyleHotSpots(String strStyleHotSpots) {` |
| `getStrStyleHotSpotImages` | 11811 | ✅ | `public String getStrStyleHotSpotImages() {` |
| `setStrStyleHotSpotImages` | 11818 | ✅ | `public void setStrStyleHotSpotImages(String strStyleHotSpotImages) {` |
| `getZonesBarrePersonnalisee` | 11825 | ✅ | `public ZoneTelecommande[] getZonesBarrePersonnalisee() {` |
| `setZonesBarrePersonnalisee` | 11832 | ✅ | `public void setZonesBarrePersonnalisee(ZoneTelecommande[] zonesBarrePersonnal...` |
| `getStrStyleHotSpotHTML` | 11839 | ✅ | `public String getStrStyleHotSpotHTML() {` |
| `setStrStyleHotSpotHTML` | 11846 | ✅ | `public void setStrStyleHotSpotHTML(String strStyleHotSpotHTML) {` |
| `isbAfficheCarte` | 11853 | ✅ | `public boolean isbAfficheCarte() {` |
| `setbAfficheCarte` | 11860 | ✅ | `public void setbAfficheCarte(boolean bAfficheCarte) {` |
| `getStrPositionCarte` | 11867 | ✅ | `public String getStrPositionCarte() {` |
| `setStrPositionCarte` | 11874 | ✅ | `public void setStrPositionCarte(String strPositionCarte) {` |
| `getLargeurCarte` | 11881 | ✅ | `public double getLargeurCarte() {` |
| `setLargeurCarte` | 11888 | ✅ | `public void setLargeurCarte(double largeurCarte) {` |
| `getCouleurFondCarte` | 11895 | ✅ | `public Color getCouleurFondCarte() {` |
| `setCouleurFondCarte` | 11902 | ✅ | `public void setCouleurFondCarte(Color couleurFondCarte) {` |
| `getStrCouleurFondCarte` | 11909 | ✅ | `public String getStrCouleurFondCarte() {` |
| `setStrCouleurFondCarte` | 11916 | ✅ | `public void setStrCouleurFondCarte(String strCouleurFondCarte) {` |
| `getOpaciteCarte` | 11923 | ✅ | `public double getOpaciteCarte() {` |
| `setOpaciteCarte` | 11930 | ✅ | `public void setOpaciteCarte(double opaciteCarte) {` |
| `getCouleurTexteCarte` | 11937 | ✅ | `public Color getCouleurTexteCarte() {` |
| `setCouleurTexteCarte` | 11944 | ✅ | `public void setCouleurTexteCarte(Color couleurTexteCarte) {` |
| `getStrCouleurTexteCarte` | 11951 | ✅ | `public String getStrCouleurTexteCarte() {` |
| `setStrCouleurTexteCarte` | 11958 | ✅ | `public void setStrCouleurTexteCarte(String strCouleurTexteCarte) {` |
| `isbAfficheRadarCarte` | 11965 | ✅ | `public boolean isbAfficheRadarCarte() {` |
| `setbAfficheRadarCarte` | 11972 | ✅ | `public void setbAfficheRadarCarte(boolean bAfficheRadarCarte) {` |
| `getCouleurLigneRadarCarte` | 11979 | ✅ | `public Color getCouleurLigneRadarCarte() {` |
| `setCouleurLigneRadarCarte` | 11986 | ✅ | `public void setCouleurLigneRadarCarte(Color couleurLigneRadarCarte) {` |
| `getStrCouleurLigneRadarCarte` | 11993 | ✅ | `public String getStrCouleurLigneRadarCarte() {` |
| `setStrCouleurLigneRadarCarte` | 12000 | ✅ | `public void setStrCouleurLigneRadarCarte(String strCouleurLigneRadarCarte) {` |
| `getCouleurFondRadarCarte` | 12007 | ✅ | `public Color getCouleurFondRadarCarte() {` |
| `setCouleurFondRadarCarte` | 12014 | ✅ | `public void setCouleurFondRadarCarte(Color couleurFondRadarCarte) {` |
| `getStrCouleurFondRadarCarte` | 12021 | ✅ | `public String getStrCouleurFondRadarCarte() {` |
| `setStrCouleurFondRadarCarte` | 12028 | ✅ | `public void setStrCouleurFondRadarCarte(String strCouleurFondRadarCarte) {` |
| `getTailleRadarCarte` | 12035 | ✅ | `public double getTailleRadarCarte() {` |
| `setTailleRadarCarte` | 12042 | ✅ | `public void setTailleRadarCarte(double tailleRadarCarte) {` |
| `getOpaciteRadarCarte` | 12049 | ✅ | `public double getOpaciteRadarCarte() {` |
| `setOpaciteRadarCarte` | 12056 | ✅ | `public void setOpaciteRadarCarte(double opaciteRadarCarte) {` |
| `getHauteurCarte` | 12063 | ✅ | `public double getHauteurCarte() {` |
| `setHauteurCarte` | 12070 | ✅ | `public void setHauteurCarte(double hauteurCarte) {` |
| `getiFacteurZoomCarte` | 12077 | ✅ | `public int getiFacteurZoomCarte() {` |
| `setiFacteurZoomCarte` | 12084 | ✅ | `public void setiFacteurZoomCarte(int iFacteurZoomCarte) {` |
| `getCoordCentreCarte` | 12091 | ✅ | `public CoordonneesGeographiques getCoordCentreCarte() {` |
| `setCoordCentreCarte` | 12098 | ✅ | `public void setCoordCentreCarte(CoordonneesGeographiques coordCentreCarte) {` |
| `getSlZoomCarte` | 12105 | ✅ | `public Slider getSlZoomCarte() {` |
| `setSlZoomCarte` | 12112 | ✅ | `public void setSlZoomCarte(Slider slZoomCarte) {` |
| `getStrNomLayers` | 12119 | ✅ | `public String getStrNomLayers() {` |
| `setStrNomLayers` | 12126 | ✅ | `public void setStrNomLayers(String strNomLayers) {` |
| `isbReplieDemarrageCarte` | 12134 | ✅ | `public boolean isbReplieDemarrageCarte() {` |
| `setbReplieDemarrageCarte` | 12142 | ✅ | `public void setbReplieDemarrageCarte(boolean bReplieDemarrageCarte) {` |
| `isbReplieDemarragePlan` | 12150 | ✅ | `public boolean isbReplieDemarragePlan() {` |
| `setbReplieDemarragePlan` | 12158 | ✅ | `public void setbReplieDemarragePlan(boolean bReplieDemarragePlan) {` |
| `isbReplieDemarrageVignettes` | 12166 | ✅ | `public boolean isbReplieDemarrageVignettes() {` |
| `setbReplieDemarrageVignettes` | 12174 | ✅ | `public void setbReplieDemarrageVignettes(boolean bReplieDemarrageVignettes) {` |
| `isbTemplate` | 12181 | ✅ | `public boolean isbTemplate() {` |
| `setbTemplate` | 12188 | ✅ | `public void setbTemplate(boolean bTemplate) {` |
| `isbAfficheBoutonVisiteAuto` | 12195 | ✅ | `public boolean isbAfficheBoutonVisiteAuto() {` |
| `setbAfficheBoutonVisiteAuto` | 12202 | ✅ | `public void setbAfficheBoutonVisiteAuto(boolean bAfficheBoutonVisiteAuto) {` |
| `getStrPositionXBoutonVisiteAuto` | 12209 | ✅ | `public String getStrPositionXBoutonVisiteAuto() {` |
| `setStrPositionXBoutonVisiteAuto` | 12217 | ✅ | `public void setStrPositionXBoutonVisiteAuto(String strPositionXBoutonVisiteAu...` |
| `getStrPositionYBoutonVisiteAuto` | 12224 | ✅ | `public String getStrPositionYBoutonVisiteAuto() {` |
| `setStrPositionYBoutonVisiteAuto` | 12232 | ✅ | `public void setStrPositionYBoutonVisiteAuto(String strPositionYBoutonVisiteAu...` |
| `getOffsetXBoutonVisiteAuto` | 12239 | ✅ | `public double getOffsetXBoutonVisiteAuto() {` |
| `setOffsetXBoutonVisiteAuto` | 12246 | ✅ | `public void setOffsetXBoutonVisiteAuto(double offsetXBoutonVisiteAuto) {` |
| `getOffsetYBoutonVisiteAuto` | 12253 | ✅ | `public double getOffsetYBoutonVisiteAuto() {` |
| `setOffsetYBoutonVisiteAuto` | 12260 | ✅ | `public void setOffsetYBoutonVisiteAuto(double offsetYBoutonVisiteAuto) {` |
| `getCbAfficheBoutonVisiteAuto` | 12267 | ✅ | `public CheckBox getCbAfficheBoutonVisiteAuto() {` |
| `setCbAfficheBoutonVisiteAuto` | 12274 | ✅ | `public void setCbAfficheBoutonVisiteAuto(CheckBox cbAfficheBoutonVisiteAuto) {` |
| `getApBtnVA` | 12281 | ✅ | `public AnchorPane getApBtnVA() {` |
| `setApBtnVA` | 12288 | ✅ | `public void setApBtnVA(AnchorPane apBtnVA) {` |
| `getTailleBoutonVisiteAuto` | 12295 | ✅ | `public double getTailleBoutonVisiteAuto() {` |
| `setTailleBoutonVisiteAuto` | 12302 | ✅ | `public void setTailleBoutonVisiteAuto(double tailleBoutonVisiteAuto) {` |
| `getStrTitrePosition` | 12309 | ✅ | `public String getStrTitrePosition() {` |
| `setStrTitrePosition` | 12316 | ✅ | `public void setStrTitrePosition(String strTitrePosition) {` |
| `getTitreDecalage` | 12323 | ✅ | `public double getTitreDecalage() {` |
| `setTitreDecalage` | 12330 | ✅ | `public void setTitreDecalage(double titreDecalage) {` |
| `isbTitreVisite` | 12337 | ✅ | `public boolean isbTitreVisite() {` |
| `setbTitreVisite` | 12344 | ✅ | `public void setbTitreVisite(boolean bTitreVisite) {` |
| `isbTitrePanoramique` | 12351 | ✅ | `public boolean isbTitrePanoramique() {` |
| `setbTitrePanoramique` | 12358 | ✅ | `public void setbTitrePanoramique(boolean bTitrePanoramique) {` |
| `isbTitreAdapte` | 12365 | ✅ | `public boolean isbTitreAdapte() {` |
| `setbTitreAdapte` | 12372 | ✅ | `public void setbTitreAdapte(boolean bTitreAdapte) {` |
| `getiTailleHotspotsPanoramique` | 12379 | ✅ | `public int getiTailleHotspotsPanoramique() {` |
| `setiTailleHotspotsPanoramique` | 12386 | ✅ | `public void setiTailleHotspotsPanoramique(int iTailleHotspotsPanoramique) {` |
| `getiTailleHotspotsImage` | 12393 | ✅ | `public int getiTailleHotspotsImage() {` |
| `setiTailleHotspotsImage` | 12400 | ✅ | `public void setiTailleHotspotsImage(int iTailleHotspotsImage) {` |
| `getiTailleHotspotsHTML` | 12407 | ✅ | `public int getiTailleHotspotsHTML() {` |
| `setiTailleHotspotsHTML` | 12414 | ✅ | `public void setiTailleHotspotsHTML(int iTailleHotspotsHTML) {` |
| `getCouleurFondTheme` | 12421 | ✅ | `public Color getCouleurFondTheme() {` |
| `setCouleurFondTheme` | 12428 | ✅ | `public void setCouleurFondTheme(Color couleurFondTheme) {` |
| `getOpaciteTheme` | 12435 | ✅ | `public double getOpaciteTheme() {` |
| `getiCalqueTitre` | 12442 | ✅ | `public int getiCalqueTitre() {` |
| `setiCalqueTitre` | 12449 | ✅ | `public void setiCalqueTitre(int iCalqueTitre) {` |
| `getiCalqueBarreClassique` | 12456 | ✅ | `public int getiCalqueBarreClassique() {` |
| `setiCalqueBarreClassique` | 12463 | ✅ | `public void setiCalqueBarreClassique(int iCalqueBarreClassique) {` |
| `getiCalqueBarrePersonnalisee` | 12470 | ✅ | `public int getiCalqueBarrePersonnalisee() {` |
| `setiCalqueBarrePersonnalisee` | 12477 | ✅ | `public void setiCalqueBarrePersonnalisee(int iCalqueBarrePersonnalisee) {` |
| `getiCalqueMasquage` | 12484 | ✅ | `public int getiCalqueMasquage() {` |
| `setiCalqueMasquage` | 12491 | ✅ | `public void setiCalqueMasquage(int iCalqueMasquage) {` |
| `getiCalqueVisiteAuto` | 12498 | ✅ | `public int getiCalqueVisiteAuto() {` |
| `setiCalqueVisiteAuto` | 12505 | ✅ | `public void setiCalqueVisiteAuto(int iCalqueVisiteAuto) {` |
| `getiCalquePartage` | 12512 | ✅ | `public int getiCalquePartage() {` |
| `setiCalquePartage` | 12519 | ✅ | `public void setiCalquePartage(int iCalquePartage) {` |
| `getiCalquePlan` | 12526 | ✅ | `public int getiCalquePlan() {` |
| `setiCalquePlan` | 12533 | ✅ | `public void setiCalquePlan(int iCalquePlan) {` |
| `getiCalqueCarte` | 12540 | ✅ | `public int getiCalqueCarte() {` |
| `setiCalqueCarte` | 12547 | ✅ | `public void setiCalqueCarte(int iCalqueCarte) {` |
| `getiCalqueBoussole` | 12554 | ✅ | `public int getiCalqueBoussole() {` |
| `setiCalqueBoussole` | 12561 | ✅ | `public void setiCalqueBoussole(int iCalqueBoussole) {` |
| `getiCalqueVignettes` | 12568 | ✅ | `public int getiCalqueVignettes() {` |
| `setiCalqueVignettes` | 12575 | ✅ | `public void setiCalqueVignettes(int iCalqueVignettes) {` |
| `getiCalqueMenuPanoramiques` | 12582 | ✅ | `public int getiCalqueMenuPanoramiques() {` |
| `setiCalqueMenuPanoramiques` | 12589 | ✅ | `public void setiCalqueMenuPanoramiques(int iCalqueMenuPanoramiques) {` |
| `getiCalqueSuivPrec` | 12596 | ✅ | `public int getiCalqueSuivPrec() {` |
| `setiCalqueSuivPrec` | 12603 | ✅ | `public void setiCalqueSuivPrec(int iCalqueSuivPrec) {` |
| `getSpOutils` | 12610 | ✅ | `public ScrollPane getSpOutils() {` |
| `setSpOutils` | 12617 | ✅ | `public void setSpOutils(ScrollPane spOutils) {` |
| `getApVis` | 12624 | ✅ | `public AnchorPane getApVis() {` |
| `setApVis` | 12631 | ✅ | `public void setApVis(AnchorPane apVis) {` |

---

### ⚠️ `GestionnairePlanController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\GestionnairePlanController.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 35/120 éléments documentés (29.2%)

#### Propriétés (77)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `rbLocalisation` | 79 | ❌ | `private ResourceBundle rbLocalisation;` |
| `bDejaCharge` | 80 | ❌ | `private boolean bDejaCharge = false;` |
| `hbInterface` | 81 | ❌ | `private HBox hbInterface;` |
| `apPlan` | 82 | ❌ | `private AnchorPane apPlan;` |
| `vbOutils` | 83 | ❌ | `private VBox vbOutils;` |
| `slNordPlan` | 84 | ❌ | `private Slider slNordPlan;` |
| `bdfPositXBoussole` | 85 | ❌ | `private BigDecimalField bdfPositXBoussole;` |
| `bdfPositYBoussole` | 86 | ❌ | `private BigDecimalField bdfPositYBoussole;` |
| `rbBoussolePlanTopLeft` | 87 | ❌ | `private RadioButton rbBoussolePlanTopLeft;` |
| `rbBoussolePlanTopRight` | 88 | ❌ | `private RadioButton rbBoussolePlanTopRight;` |
| `rbBoussolePlanBottomLeft` | 89 | ❌ | `private RadioButton rbBoussolePlanBottomLeft;` |
| `rbBoussolePlanBottomRight` | 90 | ❌ | `private RadioButton rbBoussolePlanBottomRight;` |
| `lblDragDropPlan` | 93 | ❌ | `private Label lblDragDropPlan;` |
| `paneInterface` | 94 | ❌ | `private Pane paneInterface;` |
| `positionNordPlan` | 95 | ❌ | `private double positionNordPlan = 0;` |
| `strPositionBoussolePlan` | 96 | ❌ | `private String strPositionBoussolePlan = "top:right";` |
| `positXBoussolePlan` | 97 | ❌ | `private double positXBoussolePlan = 0;` |
| `positYBoussolePlan` | 98 | ❌ | `private double positYBoussolePlan = 0;` |
| `strPlanListeVignette` | 99 | ❌ | `private String strPlanListeVignette = "";` |
| `iPlanActuel` | 100 | ❌ | `private int iPlanActuel;` |
| `strRepertImagePlan` | 101 | ❌ | `private String strRepertImagePlan = "";` |
| `ivPlan` | 102 | ❌ | `private ImageView ivPlan;` |
| `imgPlan` | 103 | ❌ | `private Image imgPlan;` |
| `strTooltipStyle` | 104 | ❌ | `private String strTooltipStyle = "";` |
| `panePlan` | 105 | ❌ | `private Pane panePlan;` |
| `cbChoixPlan` | 106 | ❌ | `private ComboBox<String> cbChoixPlan;` |
| `apConfigPlan` | 107 | ❌ | `private AnchorPane apConfigPlan;` |
| `ivNord` | 108 | ❌ | `private ImageView ivNord;` |
| `imgBoussole` | 109 | ❌ | `private Image imgBoussole;` |
| `iNumPoints` | 110 | ❌ | `private int iNumPoints = 0;` |
| `spOutils` | 111 | ❌ | `private ScrollPane spOutils;` |
| `bDragDropPlan` | 112 | ❌ | `private boolean bDragDropPlan = false;` |
| `strContenuFichier` | 143 | ❌ | `String strContenuFichier = "";` |
| `strContenuFichier` | 168 | ❌ | `return strContenuFichier;` |
| `X` | 180 | ❌ | `double X = posX * largeur;` |
| `Y` | 181 | ❌ | `double Y = posY * hauteur;` |
| `nodePoint` | 231 | ❌ | `Node nodePoint;` |
| `iLargeurVignettes` | 260 | ❌ | `int iLargeurVignettes = 4;` |
| `lblPoint` | 304 | ❌ | `Label lblPoint;` |
| `iNum1` | 310 | ❌ | `int iNum1 = io;` |
| `paneHotSpots` | 410 | ❌ | `return paneHotSpots;` |
| `positionX` | 480 | ❌ | `double positionX = 0;` |
| `positionY` | 481 | ❌ | `double positionY = 0;` |
| `ij` | 521 | ❌ | `int ij = 0;` |
| `ivPano` | 522 | ❌ | `ImageView[] ivPano;` |
| `xPos` | 524 | ❌ | `double xPos;` |
| `yPos` | 525 | ❌ | `double yPos;` |
| `iRow` | 526 | ❌ | `int iRow = 0;` |
| `iNumeroPano` | 528 | ❌ | `int iNumeroPano = i;` |
| `iCol` | 534 | ❌ | `int iCol = ij % 4;` |
| `apListePano` | 581 | ❌ | `return apListePano;` |
| `iNum` | 691 | ❌ | `final int iNum = i;` |
| `cbF` | 693 | ❌ | `final CheckBox cbF = cbPanoramiques[i];` |
| `strListe` | 756 | ❌ | `String strListe = "";` |
| `null` | 758 | ❌ | `return null;` |
| `strListe` | 769 | ❌ | `return strListe;` |
| `posY` | 786 | ❌ | `double posX, posY;` |
| `iLargeurVignettes` | 807 | ❌ | `int iLargeurVignettes = 4;` |
| `nodePoint` | 860 | ❌ | `Node nodePoint;` |
| `iLargeurVignettes1` | 889 | ❌ | `int iLargeurVignettes1 = 4;` |
| `success` | 1076 | ❌ | `boolean success = false;` |
| `filePath` | 1080 | ❌ | `String filePath = null;` |
| `fileListe` | 1081 | ❌ | `File[] fileListe = new File[100];` |
| `i` | 1082 | ❌ | `int i = 0;` |
| `nb` | 1088 | ❌ | `int nb = i;` |
| `fichierPlan` | 1090 | ❌ | `File fichierPlan = fileListe[ijj];` |
| `lblDragDropPlan` | 1122 | ❌ | `return lblDragDropPlan;` |
| `paneInterface` | 1136 | ❌ | `return paneInterface;` |
| `positionNordPlan` | 1150 | ❌ | `return positionNordPlan;` |
| `strPositionBoussolePlan` | 1164 | ❌ | `return strPositionBoussolePlan;` |
| `positXBoussolePlan` | 1178 | ❌ | `return positXBoussolePlan;` |
| `positYBoussolePlan` | 1192 | ❌ | `return positYBoussolePlan;` |
| `strPlanListeVignette` | 1206 | ❌ | `return strPlanListeVignette;` |
| `iPlanActuel` | 1220 | ❌ | `return iPlanActuel;` |
| `strRepertImagePlan` | 1234 | ❌ | `return strRepertImagePlan;` |
| `apPlan` | 1248 | ❌ | `return apPlan;` |
| `spOutils` | 1262 | ❌ | `return spOutils;` |

#### Méthodes (43)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `GestionnairePlanController` | 114 | ❌ | `public GestionnairePlanController() {` |
| `retireAffichagePointsHotSpots` | 118 | ❌ | `private void retireAffichagePointsHotSpots() {` |
| `valideHsplan` | 125 | ❌ | `private void valideHsplan() {` |
| `getTemplate` | 142 | ❌ | `public String getTemplate() {` |
| `afficheHS` | 177 | ✅ | `private void afficheHS(int i, double posX, double posY) {` |
| `ajouteAffichagePointsHotspots` | 284 | ✅ | `private void ajouteAffichagePointsHotspots() {` |
| `affichageHS` | 299 | ✅ | `public Pane affichageHS(String strLstPano, int iNumPano) {` |
| `changed` | 356 | ❌ | `public void changed(ObservableValue ov, String t, String t1) {` |
| `ajouteAffichageHotspots` | 416 | ✅ | `private void ajouteAffichageHotspots() {` |
| `retireAffichageHotSpots` | 426 | ✅ | `private void retireAffichageHotSpots() {` |
| `ajouterPlan` | 435 | ✅ | `public void ajouterPlan() {` |
| `afficherPlan` | 446 | ✅ | `public void afficherPlan(int numeroPlan) {` |
| `afficheBoussole` | 476 | ✅ | `private void afficheBoussole() {` |
| `afficherListePanosVignettes` | 509 | ✅ | `private AnchorPane afficherListePanosVignettes(int numHS) {` |
| `afficheConfigPlan` | 587 | ✅ | `public void afficheConfigPlan() {` |
| `listePano` | 755 | ✅ | `private String listePano() {` |
| `planMouseClic` | 778 | ✅ | `private void planMouseClic(double X, double Y) {` |
| `retirePlanCourant` | 911 | ❌ | `private void retirePlanCourant() {` |
| `gereSourisPlan` | 952 | ❌ | `private void gereSourisPlan(MouseEvent me) {` |
| `creeInterface` | 965 | ✅ | `public void creeInterface(int iLargeur, int iHauteur) {` |
| `changed` | 1028 | ❌ | `public void changed(ObservableValue ov, String t, String t1) {` |
| `getLblDragDropPlan` | 1121 | ✅ | `public Label getLblDragDropPlan() {` |
| `setLblDragDropPlan` | 1128 | ✅ | `public void setLblDragDropPlan(Label lblDragDropPlan) {` |
| `getPaneInterface` | 1135 | ✅ | `public Pane getPaneInterface() {` |
| `setPaneInterface` | 1142 | ✅ | `public void setPaneInterface(Pane paneInterface) {` |
| `getPositionNordPlan` | 1149 | ✅ | `public double getPositionNordPlan() {` |
| `setPositionNordPlan` | 1156 | ✅ | `public void setPositionNordPlan(double positionNordPlan) {` |
| `getStrPositionBoussolePlan` | 1163 | ✅ | `public String getStrPositionBoussolePlan() {` |
| `setStrPositionBoussolePlan` | 1170 | ✅ | `public void setStrPositionBoussolePlan(String strPositionBoussolePlan) {` |
| `getPositXBoussolePlan` | 1177 | ✅ | `public double getPositXBoussolePlan() {` |
| `setPositXBoussolePlan` | 1184 | ✅ | `public void setPositXBoussolePlan(double positXBoussolePlan) {` |
| `getPositYBoussolePlan` | 1191 | ✅ | `public double getPositYBoussolePlan() {` |
| `setPositYBoussolePlan` | 1198 | ✅ | `public void setPositYBoussolePlan(double positYBoussolePlan) {` |
| `getStrPlanListeVignette` | 1205 | ✅ | `public String getStrPlanListeVignette() {` |
| `setStrPlanListeVignette` | 1212 | ✅ | `public void setStrPlanListeVignette(String strPlanListeVignette) {` |
| `getiPlanActuel` | 1219 | ✅ | `public int getiPlanActuel() {` |
| `setiPlanActuel` | 1226 | ✅ | `public void setiPlanActuel(int iPlanActuel) {` |
| `getStrRepertImagePlan` | 1233 | ✅ | `public String getStrRepertImagePlan() {` |
| `setStrRepertImagePlan` | 1240 | ✅ | `public void setStrRepertImagePlan(String strRepertImagePlan) {` |
| `getApPlan` | 1247 | ✅ | `public AnchorPane getApPlan() {` |
| `setApPlan` | 1254 | ✅ | `public void setApPlan(AnchorPane apPlan) {` |
| `getSpOutils` | 1261 | ✅ | `public ScrollPane getSpOutils() {` |
| `setSpOutils` | 1268 | ✅ | `public void setSpOutils(ScrollPane spOutils) {` |

---

### 🔶 `HotSpot`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\HotSpot.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 23/44 éléments documentés (52.3%)

#### Propriétés (21)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `latitude` | 17 | ❌ | `private double longitude, latitude;` |
| `strFichierImage` | 18 | ❌ | `private String strFichierImage = "";` |
| `strFichierXML` | 19 | ❌ | `private String strFichierXML = "";` |
| `strInfo` | 20 | ❌ | `private String strInfo = "";` |
| `bAnime` | 21 | ❌ | `private boolean bAnime;` |
| `iNumeroPano` | 22 | ❌ | `private int iNumeroPano = -1;` |
| `regardX` | 23 | ❌ | `private double regardX=-1000;` |
| `regardY` | 24 | ❌ | `private double regardY=-1000;` |
| `champVisuel` | 25 | ❌ | `private double champVisuel=0;` |
| `imgVueHs` | 26 | ❌ | `private Image imgVueHs;` |
| `longitude` | 45 | ❌ | `return longitude;` |
| `latitude` | 59 | ❌ | `return latitude;` |
| `strFichierImage` | 73 | ❌ | `return strFichierImage;` |
| `strFichierXML` | 87 | ❌ | `return strFichierXML;` |
| `strInfo` | 101 | ❌ | `return strInfo;` |
| `bAnime` | 115 | ❌ | `return bAnime;` |
| `iNumeroPano` | 129 | ❌ | `return iNumeroPano;` |
| `regardX` | 143 | ❌ | `return regardX;` |
| `regardY` | 157 | ❌ | `return regardY;` |
| `champVisuel` | 171 | ❌ | `return champVisuel;` |
| `imgVueHs` | 185 | ❌ | `return imgVueHs;` |

#### Méthodes (23)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `HotSpot` | 33 | ✅ | `public void HotSpot(Number longit, Number latit) {` |
| `getLongitude` | 44 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 51 | ✅ | `public void setLongitude(Number longitude) {` |
| `getLatitude` | 58 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 65 | ✅ | `public void setLatitude(Number latitude) {` |
| `getStrFichierImage` | 72 | ✅ | `public String getStrFichierImage() {` |
| `setStrFichierImage` | 79 | ✅ | `public void setStrFichierImage(String strFichierImage) {` |
| `getStrFichierXML` | 86 | ✅ | `public String getStrFichierXML() {` |
| `setStrFichierXML` | 93 | ✅ | `public void setStrFichierXML(String strFichierXML) {` |
| `getStrInfo` | 100 | ✅ | `public String getStrInfo() {` |
| `setStrInfo` | 107 | ✅ | `public void setStrInfo(String strInfo) {` |
| `isAnime` | 114 | ✅ | `public boolean isAnime() {` |
| `setAnime` | 121 | ✅ | `public void setAnime(boolean bAnime) {` |
| `getNumeroPano` | 128 | ✅ | `public int getNumeroPano() {` |
| `setNumeroPano` | 135 | ✅ | `public void setNumeroPano(int iNumeroPano) {` |
| `getRegardX` | 142 | ✅ | `public double getRegardX() {` |
| `setRegardX` | 149 | ✅ | `public void setRegardX(double regardX) {` |
| `getRegardY` | 156 | ✅ | `public double getRegardY() {` |
| `setRegardY` | 163 | ✅ | `public void setRegardY(double regardY) {` |
| `getChampVisuel` | 170 | ✅ | `public double getChampVisuel() {` |
| `setChampVisuel` | 177 | ✅ | `public void setChampVisuel(double champVisuel) {` |
| `getImgVueHs` | 184 | ✅ | `public Image getImgVueHs() {` |
| `setImgVueHs` | 191 | ✅ | `public void setImgVueHs(Image imgVueHs) {` |

---

### 🔶 `HotspotDiaporama`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\HotspotDiaporama.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 11/20 éléments documentés (55.0%)

#### Propriétés (9)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `latitude` | 15 | ❌ | `private double longitude, latitude;` |
| `iNumDiapo` | 16 | ❌ | `private int iNumDiapo=-1;` |
| `strInfo` | 17 | ❌ | `private String strInfo="";` |
| `bAnime` | 18 | ❌ | `private boolean bAnime = false;` |
| `longitude` | 36 | ❌ | `return longitude;` |
| `latitude` | 50 | ❌ | `return latitude;` |
| `iNumDiapo` | 64 | ❌ | `return iNumDiapo;` |
| `strInfo` | 78 | ❌ | `return strInfo;` |
| `bAnime` | 92 | ❌ | `return bAnime;` |

#### Méthodes (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `HotSpot` | 27 | ✅ | `public void HotSpot(Number longit, Number latit) {` |
| `getLongitude` | 35 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 42 | ✅ | `public void setLongitude(Number longitude) {` |
| `getLatitude` | 49 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 56 | ✅ | `public void setLatitude(Number latitude) {` |
| `getiNumDiapo` | 63 | ✅ | `public int getiNumDiapo() {` |
| `setiNumDiapo` | 70 | ✅ | `public void setiNumDiapo(int iNumDiapo) {` |
| `getStrInfo` | 77 | ✅ | `public String getStrInfo() {` |
| `setStrInfo` | 84 | ✅ | `public void setStrInfo(String strInfo) {` |
| `isbAnime` | 91 | ✅ | `public boolean isbAnime() {` |
| `setbAnime` | 98 | ✅ | `public void setbAnime(boolean bAnime) {` |

---

### ⚠️ `HotspotHTML`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\HotspotHTML.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 27/55 éléments documentés (49.1%)

#### Propriétés (27)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `latitude` | 25 | ❌ | `private double longitude, latitude;` |
| `strInfo` | 26 | ❌ | `private String strInfo = "";` |
| `bAnime` | 27 | ❌ | `private boolean bAnime = false;` |
| `strURLExterieure` | 28 | ❌ | `private String strURLExterieure = "";` |
| `bLienExterieur` | 29 | ❌ | `private boolean bLienExterieur = true;` |
| `opaciteHTML` | 30 | ❌ | `private double opaciteHTML = 1;` |
| `strTexteHTML` | 31 | ❌ | `private String strTexteHTML = "";` |
| `largeurHTML` | 32 | ❌ | `private double largeurHTML = 1000;` |
| `strPositionHTML` | 33 | ❌ | `private String strPositionHTML = "center";` |
| `strCouleurHTML` | 34 | ❌ | `private String strCouleurHTML = "#aaaaff";` |
| `imagesEditeur` | 35 | ❌ | `private ImageEditeurHTML[] imagesEditeur = new ImageEditeurHTML[50];` |
| `iNombreImages` | 36 | ❌ | `private int iNombreImages = 0;` |
| `longitude` | 55 | ❌ | `return longitude;` |
| `latitude` | 69 | ❌ | `return latitude;` |
| `strInfo` | 83 | ❌ | `return strInfo;` |
| `bAnime` | 97 | ❌ | `return bAnime;` |
| `strURLExterieure` | 111 | ❌ | `return strURLExterieure;` |
| `bLienExterieur` | 125 | ❌ | `return bLienExterieur;` |
| `opaciteHTML` | 139 | ❌ | `return opaciteHTML;` |
| `strTexteHTML` | 153 | ❌ | `return strTexteHTML;` |
| `largeurHTML` | 167 | ❌ | `return largeurHTML;` |
| `strPositionHTML` | 181 | ❌ | `return strPositionHTML;` |
| `strCouleurHTML` | 195 | ❌ | `return strCouleurHTML;` |
| `imagesEditeur` | 209 | ❌ | `return imagesEditeur;` |
| `iNombreImages` | 223 | ❌ | `return iNombreImages;` |
| `oswFichierHTML` | 234 | ❌ | `OutputStreamWriter oswFichierHTML = null;` |
| `strPageHTMLImages` | 236 | ❌ | `String strPageHTMLImages = strPageHTML + File.separator + "/images";` |

#### Méthodes (28)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `HotSpot` | 43 | ✅ | `public void HotSpot(Number longit, Number latit) {` |
| `getLongitude` | 54 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 61 | ✅ | `public void setLongitude(Number longitude) {` |
| `getLatitude` | 68 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 75 | ✅ | `public void setLatitude(Number latitude) {` |
| `getStrInfo` | 82 | ✅ | `public String getStrInfo() {` |
| `setStrInfo` | 89 | ✅ | `public void setStrInfo(String strInfo) {` |
| `isAnime` | 96 | ✅ | `public boolean isAnime() {` |
| `setAnime` | 103 | ✅ | `public void setAnime(boolean anime) {` |
| `getStrURLExterieure` | 110 | ✅ | `public String getStrURLExterieure() {` |
| `setStrURLExterieure` | 117 | ✅ | `public void setStrURLExterieure(String strURLExterieure) {` |
| `isbLienExterieur` | 124 | ✅ | `public boolean isbLienExterieur() {` |
| `setbLienExterieur` | 131 | ✅ | `public void setbLienExterieur(boolean bLienExterieur) {` |
| `getOpaciteHTML` | 138 | ✅ | `public double getOpaciteHTML() {` |
| `setOpaciteHTML` | 145 | ✅ | `public void setOpaciteHTML(double opaciteHTML) {` |
| `getStrTexteHTML` | 152 | ✅ | `public String getStrTexteHTML() {` |
| `setStrTexteHTML` | 159 | ✅ | `public void setStrTexteHTML(String strTexteHTML) {` |
| `getLargeurHTML` | 166 | ✅ | `public double getLargeurHTML() {` |
| `setLargeurHTML` | 173 | ✅ | `public void setLargeurHTML(double largeurHTML) {` |
| `getStrPositionHTML` | 180 | ✅ | `public String getStrPositionHTML() {` |
| `setStrPositionHTML` | 187 | ✅ | `public void setStrPositionHTML(String strPositionHTML) {` |
| `getStrCouleurHTML` | 194 | ✅ | `public String getStrCouleurHTML() {` |
| `setStrCouleurHTML` | 201 | ✅ | `public void setStrCouleurHTML(String strCouleurHTML) {` |
| `getImagesEditeur` | 208 | ✅ | `public ImageEditeurHTML[] getImagesEditeur() {` |
| `setImagesEditeur` | 215 | ✅ | `public void setImagesEditeur(ImageEditeurHTML[] imagesEditeur) {` |
| `getiNombreImages` | 222 | ✅ | `public int getiNombreImages() {` |
| `setiNombreImages` | 229 | ✅ | `public void setiNombreImages(int iNombreImages) {` |
| `creeHTML` | 233 | ❌ | `public void creeHTML(String strPageHTML) {` |

---

### 🔶 `HotspotImage`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\HotspotImage.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 19/35 éléments documentés (54.3%)

#### Propriétés (16)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `latitude` | 15 | ❌ | `private double longitude, latitude;` |
| `strNomImg` | 16 | ❌ | `private String strNomImg = "";` |
| `strUrlImage` | 17 | ❌ | `private String strUrlImage = "";` |
| `strFichierImage` | 18 | ❌ | `private String strFichierImage = "", strInfo = "";` |
| `strCouleurFond` | 19 | ❌ | `private String strCouleurFond="";` |
| `opacite` | 20 | ❌ | `private double opacite=-1;` |
| `bAnime` | 21 | ❌ | `private boolean bAnime;` |
| `longitude` | 40 | ❌ | `return longitude;` |
| `latitude` | 54 | ❌ | `return latitude;` |
| `strFichierImage` | 68 | ❌ | `return strFichierImage;` |
| `strNomImg` | 82 | ❌ | `return strNomImg;` |
| `strInfo` | 96 | ❌ | `return strInfo;` |
| `bAnime` | 110 | ❌ | `return bAnime;` |
| `strUrlImage` | 124 | ❌ | `return strUrlImage;` |
| `strCouleurFond` | 138 | ❌ | `return strCouleurFond;` |
| `opacite` | 152 | ❌ | `return opacite;` |

#### Méthodes (19)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `HotSpot` | 28 | ✅ | `public void HotSpot(Number longit, Number latit) {` |
| `getLongitude` | 39 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 46 | ✅ | `public void setLongitude(Number longitude) {` |
| `getLatitude` | 53 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 60 | ✅ | `public void setLatitude(Number latitude) {` |
| `getStrFichierImage` | 67 | ✅ | `public String getStrFichierImage() {` |
| `setStrFichierImage` | 74 | ✅ | `public void setStrFichierImage(String strFichierImage) {` |
| `getStrLienImg` | 81 | ✅ | `public String getStrLienImg() {` |
| `setStrLienImg` | 88 | ✅ | `public void setStrLienImg(String strLienImg) {` |
| `getStrInfo` | 95 | ✅ | `public String getStrInfo() {` |
| `setStrInfo` | 102 | ✅ | `public void setStrInfo(String strInfo) {` |
| `isAnime` | 109 | ✅ | `public boolean isAnime() {` |
| `setAnime` | 116 | ✅ | `public void setAnime(boolean anime) {` |
| `getStrUrlImage` | 123 | ✅ | `public String getStrUrlImage() {` |
| `setStrUrlImage` | 130 | ✅ | `public void setStrUrlImage(String strUrlImage) {` |
| `getStrCouleurFond` | 137 | ✅ | `public String getStrCouleurFond() {` |
| `setStrCouleurFond` | 144 | ✅ | `public void setStrCouleurFond(String strCouleurFond) {` |
| `getOpacite` | 151 | ✅ | `public double getOpacite() {` |
| `setOpacite` | 158 | ✅ | `public void setOpacite(double opacite) {` |

---

### ⚠️ `HuggingFaceClient`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\HuggingFaceClient.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 11/40 éléments documentés (27.5%)

#### Propriétés (16)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `API_BASE_URL` | 21 | ❌ | `private static final String API_BASE_URL = "https://api-inference.huggingface...` |
| `apiKey` | 22 | ❌ | `private final String apiKey;` |
| `modelName` | 23 | ❌ | `private final String modelName;` |
| `endpoint` | 70 | ❌ | `String endpoint = API_BASE_URL + modelName;` |
| `line` | 100 | ❌ | `String line;` |
| `line` | 114 | ❌ | `String line;` |
| `endpoint` | 154 | ❌ | `String endpoint = API_BASE_URL + "facebook/bart-large-cnn";` |
| `line` | 180 | ❌ | `String line;` |
| `line` | 190 | ❌ | `String line;` |
| `marker` | 219 | ❌ | `String marker = "\"generated_text\":";` |
| `jsonResponse` | 222 | ❌ | `return jsonResponse; // Retourner la réponse brute si format non reconnu` |
| `endIdx` | 233 | ❌ | `int endIdx = startIdx;` |
| `marker` | 249 | ❌ | `String marker = "\"summary_text\":";` |
| `jsonResponse` | 252 | ❌ | `return jsonResponse;` |
| `endIdx` | 261 | ❌ | `int endIdx = startIdx;` |
| `modelName` | 285 | ❌ | `return modelName;` |

#### Méthodes (24)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `HuggingFaceClient` | 28 | ✅ | `public HuggingFaceClient() {` |
| `HuggingFaceClient` | 42 | ✅ | `public HuggingFaceClient(String apiKey, String modelName) {` |
| `generateText` | 53 | ✅ | `public String generateText(String prompt) throws Exception {` |
| `generateText` | 54 | ❌ | `return generateText(prompt, 100, 0.7);` |
| `generateText` | 65 | ✅ | `public String generateText(String prompt, int maxLength, double temperature) ...` |
| `IllegalStateException` | 67 | ❌ | `throw new IllegalStateException("Clé API Hugging Face non configurée");` |
| `InputStreamReader` | 98 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `extractGeneratedText` | 108 | ❌ | `return extractGeneratedText(jsonResponse);` |
| `InputStreamReader` | 112 | ❌ | `new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 118 | ❌ | `throw new Exception("Erreur API Hugging Face (" + responseCode + "): " + error);` |
| `generateTextAsync` | 131 | ✅ | `public CompletableFuture<String> generateTextAsync(String prompt) {` |
| `generateText` | 134 | ❌ | `return generateText(prompt);` |
| `RuntimeException` | 136 | ❌ | `throw new RuntimeException("Erreur lors de la génération de texte", e);` |
| `summarize` | 148 | ✅ | `public String summarize(String text, int maxLength) throws Exception {` |
| `IllegalStateException` | 150 | ❌ | `throw new IllegalStateException("Clé API Hugging Face non configurée");` |
| `InputStreamReader` | 178 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `extractSummaryText` | 184 | ❌ | `return extractSummaryText(response.toString());` |
| `InputStreamReader` | 188 | ❌ | `new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 194 | ❌ | `throw new Exception("Erreur API Hugging Face (" + responseCode + "): " + error);` |
| `escapeJson` | 205 | ✅ | `private String escapeJson(String text) {` |
| `extractGeneratedText` | 217 | ✅ | `private String extractGeneratedText(String jsonResponse) {` |
| `extractSummaryText` | 248 | ✅ | `private String extractSummaryText(String jsonResponse) {` |
| `isConfigured` | 276 | ✅ | `public boolean isConfigured() {` |
| `getModelName` | 284 | ✅ | `public String getModelName() {` |

---

### 🔶 `ImageEditeurHTML`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ImageEditeurHTML.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 3/5 éléments documentés (60.0%)

#### Propriétés (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `strImagePath` | 16 | ❌ | `private String strImagePath="";` |
| `strImagePath` | 30 | ❌ | `return strImagePath;` |

#### Méthodes (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getStrNomImage` | 21 | ✅ | `public String getStrNomImage() {` |
| `getStrImagePath` | 29 | ✅ | `public String getStrImagePath() {` |
| `setStrImagePath` | 36 | ✅ | `public void setStrImagePath(String strImagePath) {` |

---

### 🔶 `ImageFond`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ImageFond.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 32/60 éléments documentés (53.3%)

#### Propriétés (28)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `strFichierImage` | 16 | ❌ | `private String strFichierImage = "";` |
| `imgFond` | 17 | ❌ | `private Image imgFond;` |
| `strPosY` | 18 | ❌ | `private String strPosY = "bottom", strPosX = "right";` |
| `offsetX` | 19 | ❌ | `private double offsetX = 0, offsetY = 0;` |
| `tailleY` | 20 | ❌ | `private int tailleX, tailleY;` |
| `opacite` | 21 | ❌ | `private double opacite = 0.8;` |
| `strUrl` | 22 | ❌ | `private String strUrl = "", strInfobulle = "";` |
| `bMasquable` | 23 | ❌ | `private boolean bMasquable = true;` |
| `strType` | 24 | ❌ | `private String strType="aucun";` |
| `iNumDiapo` | 25 | ❌ | `private int iNumDiapo=-1;` |
| `strCible` | 26 | ❌ | `private String strCible="interne";` |
| `iCalqueImage` | 27 | ❌ | `private int iCalqueImage=1;` |
| `strFichierImage` | 33 | ❌ | `return strFichierImage;` |
| `imgFond` | 47 | ❌ | `return imgFond;` |
| `strPosX` | 61 | ❌ | `return strPosX;` |
| `strPosY` | 75 | ❌ | `return strPosY;` |
| `offsetX` | 89 | ❌ | `return offsetX;` |
| `offsetY` | 103 | ❌ | `return offsetY;` |
| `strUrl` | 117 | ❌ | `return strUrl;` |
| `opacite` | 131 | ❌ | `return opacite;` |
| `tailleX` | 145 | ❌ | `return tailleX;` |
| `tailleY` | 159 | ❌ | `return tailleY;` |
| `bMasquable` | 173 | ❌ | `return bMasquable;` |
| `strInfobulle` | 187 | ❌ | `return strInfobulle;` |
| `strType` | 201 | ❌ | `return strType;` |
| `iNumDiapo` | 215 | ❌ | `return iNumDiapo;` |
| `strCible` | 229 | ❌ | `return strCible;` |
| `iCalqueImage` | 243 | ❌ | `return iCalqueImage;` |

#### Méthodes (32)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getStrFichierImage` | 32 | ✅ | `public String getStrFichierImage() {` |
| `setStrFichierImage` | 39 | ✅ | `public void setStrFichierImage(String strFichierImage) {` |
| `getImgFond` | 46 | ✅ | `public Image getImgFond() {` |
| `setImgFond` | 53 | ✅ | `public void setImgFond(Image imgFond) {` |
| `getStrPosX` | 60 | ✅ | `public String getStrPosX() {` |
| `setStrPosX` | 67 | ✅ | `public void setStrPosX(String strPosX) {` |
| `getStrPosY` | 74 | ✅ | `public String getStrPosY() {` |
| `setStrPosY` | 81 | ✅ | `public void setStrPosY(String strPosY) {` |
| `getOffsetX` | 88 | ✅ | `public double getOffsetX() {` |
| `setOffsetX` | 95 | ✅ | `public void setOffsetX(double offsetX) {` |
| `getOffsetY` | 102 | ✅ | `public double getOffsetY() {` |
| `setOffsetY` | 109 | ✅ | `public void setOffsetY(double offsetY) {` |
| `getStrUrl` | 116 | ✅ | `public String getStrUrl() {` |
| `setStrUrl` | 123 | ✅ | `public void setStrUrl(String strUrl) {` |
| `getOpacite` | 130 | ✅ | `public double getOpacite() {` |
| `setOpacite` | 137 | ✅ | `public void setOpacite(double opacite) {` |
| `getTailleX` | 144 | ✅ | `public int getTailleX() {` |
| `setTailleX` | 151 | ✅ | `public void setTailleX(int tailleX) {` |
| `getTailleY` | 158 | ✅ | `public int getTailleY() {` |
| `setTailleY` | 165 | ✅ | `public void setTailleY(int tailleY) {` |
| `isMasquable` | 172 | ✅ | `public boolean isMasquable() {` |
| `setMasquable` | 179 | ✅ | `public void setMasquable(boolean masquable) {` |
| `getStrInfobulle` | 186 | ✅ | `public String getStrInfobulle() {` |
| `setStrInfobulle` | 193 | ✅ | `public void setStrInfobulle(String strInfobulle) {` |
| `getStrType` | 200 | ✅ | `public String getStrType() {` |
| `setStrType` | 207 | ✅ | `public void setStrType(String strType) {` |
| `getiNumDiapo` | 214 | ✅ | `public int getiNumDiapo() {` |
| `setiNumDiapo` | 221 | ✅ | `public void setiNumDiapo(int iNumDiapo) {` |
| `getStrCible` | 228 | ✅ | `public String getStrCible() {` |
| `setStrCible` | 235 | ✅ | `public void setStrCible(String strCible) {` |
| `getiCalqueImage` | 242 | ✅ | `public int getiCalqueImage() {` |
| `setiCalqueImage` | 249 | ✅ | `public void setiCalqueImage(int iCalqueImage) {` |

---

### ❌ `Launcher`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\Launcher.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 0/1 éléments documentés (0.0%)

#### Méthodes (1)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `main` | 12 | ❌ | `public static void main(String[] args) {` |

---

### ❌ `ListePanoramiqueCellule`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ListePanoramiqueCellule.java`

**Documentation de la classe :** ❌ Non

**Progression :** 0/7 éléments documentés (0.0%)

#### Propriétés (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `iCell` | 26 | ❌ | `private int iCell = 0;` |
| `cellule` | 29 | ❌ | `ListCell cellule = this;` |
| `success` | 86 | ❌ | `boolean success = false;` |
| `i1` | 92 | ❌ | `int i1;` |
| `i2` | 93 | ❌ | `int i2;` |

#### Méthodes (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `ListePanoramiqueCellule` | 28 | ❌ | `public ListePanoramiqueCellule() {` |
| `updateItem` | 135 | ❌ | `protected void updateItem(String item, boolean empty) {` |

---

### ❌ `ManagedImageBufferedImageFactory`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ReadWriteImage.java`

**Documentation de la classe :** ❌ Non

**Progression :** 3/29 éléments documentés (10.3%)

#### Propriétés (20)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `ge` | 77 | ❌ | `final GraphicsEnvironment ge = GraphicsEnvironment` |
| `image` | 101 | ❌ | `return image;` |
| `imageRGBSharpen` | 107 | ❌ | `BufferedImage imageRGBSharpen = null;` |
| `format` | 121 | ❌ | `final ImageFormat format = ImageFormats.TIFF;` |
| `null` | 150 | ❌ | `return null;` |
| `imageRGBSharpen` | 165 | ❌ | `BufferedImage imageRGBSharpen = null;` |
| `iioImage` | 166 | ❌ | `IIOImage iioImage = null;` |
| `writer` | 181 | ❌ | `ImageWriter writer = null;` |
| `output` | 182 | ❌ | `FileImageOutputStream output = null;` |
| `ex` | 197 | ❌ | `throw ex;` |
| `imageRGBSharpen` | 220 | ❌ | `BufferedImage imageRGBSharpen = null;` |
| `iioImage` | 221 | ❌ | `IIOImage iioImage = null;` |
| `writer` | 237 | ❌ | `ImageWriter writer = null;` |
| `output` | 238 | ❌ | `FileImageOutputStream output = null;` |
| `ex` | 251 | ❌ | `throw ex;` |
| `imageRGBSharpen` | 274 | ❌ | `BufferedImage imageRGBSharpen = null;` |
| `iioImage` | 275 | ❌ | `IIOImage iioImage = null;` |
| `writer` | 288 | ❌ | `ImageWriter writer = null;` |
| `output` | 289 | ❌ | `FileImageOutputStream output = null;` |
| `ex` | 302 | ❌ | `throw ex;` |

#### Méthodes (9)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getColorBufferedImage` | 75 | ❌ | `public BufferedImage getColorBufferedImage(final int iLargeur, final int iHau...` |
| `getGrayscaleBufferedImage` | 86 | ❌ | `public BufferedImage getGrayscaleBufferedImage(final int width, final int hei...` |
| `getColorBufferedImage` | 88 | ❌ | `return getColorBufferedImage(width, height, hasAlpha);` |
| `readTiff` | 92 | ❌ | `public static Image readTiff(String strNomFich)` |
| `writeTiff` | 104 | ❌ | `public static void writeTiff(Image imgImage, String strNomFich, boolean bShar...` |
| `resizeImage` | 141 | ❌ | `public static Image resizeImage(Image img, int newW, int newH) {` |
| `writeJpeg` | 162 | ✅ | `public static void writeJpeg(Image img, String destFile, float quality, boole...` |
| `writeBMP` | 217 | ✅ | `public static void writeBMP(Image img, String destFile, boolean sharpen, floa...` |
| `writePng` | 271 | ✅ | `public static void writePng(Image img, String destFile, boolean sharpen, floa...` |

---

### ⚠️ `MapMarkerWithHtml`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurCarteGluon.java`

**Documentation de la classe :** ✅ Oui (3 lignes)

**Classes internes :** NavigateurCarteGluon

**Progression :** 26/53 éléments documentés (49.1%)

#### Propriétés (21)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `tooltipText` | 27 | ❌ | `private String tooltipText; // Texte pour le tooltip seulement` |
| `titre` | 43 | ❌ | `String titre = "";` |
| `nom` | 44 | ❌ | `String nom = "";` |
| `tooltipText` | 87 | ❌ | `return tooltipText;` |
| `null` | 93 | ❌ | `return null;` |
| `swingNode` | 110 | ❌ | `private SwingNode swingNode;` |
| `mapViewer` | 111 | ❌ | `private JMapViewer mapViewer;` |
| `bDebut` | 113 | ❌ | `private boolean bDebut = false;` |
| `mapInitialized` | 114 | ❌ | `private boolean mapInitialized = false;` |
| `locationIqApiKey` | 115 | ❌ | `private String locationIqApiKey = null;` |
| `closestMarker` | 189 | ❌ | `MapMarker closestMarker = null;` |
| `minDistance` | 190 | ❌ | `double minDistance = 15; // Distance max en pixels pour considérer un marqueur` |
| `bDebut` | 300 | ❌ | `return bDebut;` |
| `coords` | 365 | ❌ | `return coords;` |
| `coords` | 372 | ❌ | `return coords;` |
| `urlString` | 487 | ❌ | `String urlString = "https://us1.locationiq.com/v1/search.php?key="` |
| `inputLine` | 503 | ❌ | `String inputLine;` |
| `urlString` | 566 | ❌ | `String urlString = "https://us1.locationiq.com/v1/search.php?key="` |
| `inputLine` | 582 | ❌ | `String inputLine;` |
| `pane` | 705 | ❌ | `return pane;` |
| `true` | 719 | ❌ | `return true; // JMapViewer n'utilise pas Bing, donc toujours valide` |

#### Méthodes (32)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `MapMarkerWithHtml` | 29 | ❌ | `public MapMarkerWithHtml(double lat, double lon) {` |
| `setHtmlContent` | 38 | ✅ | `public void setHtmlContent(String html) {` |
| `getTooltipText` | 86 | ✅ | `public String getTooltipText() {` |
| `getName` | 91 | ❌ | `public String getName() {` |
| `NavigateurCarteGluon` | 120 | ✅ | `public NavigateurCarteGluon() {` |
| `loadLocationIqApiKey` | 131 | ✅ | `private void loadLocationIqApiKey() {` |
| `initializeMap` | 166 | ✅ | `private void initializeMap() {` |
| `mouseMoved` | 187 | ❌ | `public void mouseMoved(java.awt.event.MouseEvent e) {` |
| `isbDebut` | 299 | ✅ | `public boolean isbDebut() {` |
| `setbDebut` | 306 | ✅ | `public void setbDebut(boolean bDebut) {` |
| `allerCoordonnees` | 313 | ✅ | `public void allerCoordonnees(String strLatitude, String strLongitude) {` |
| `allerCoordonnees` | 336 | ✅ | `public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacte...` |
| `Coordinate` | 345 | ❌ | `new Coordinate(coordonnees.getLatitude(), coordonnees.getLongitude()),` |
| `recupereCoordonnees` | 359 | ✅ | `public CoordonneesGeographiques recupereCoordonnees() {` |
| `retireMarqueur` | 378 | ✅ | `public void retireMarqueur(int index) {` |
| `retireMarqueurs` | 393 | ✅ | `public void retireMarqueurs() {` |
| `ajouteMarqueur` | 408 | ✅ | `public int ajouteMarqueur(String strLatitude, String strLongitude, String str...` |
| `ajouteMarqueur` | 443 | ✅ | `public void ajouteMarqueur(int iNumero, CoordonneesGeographiques coordMarqueu...` |
| `allerAdresse` | 472 | ✅ | `public void allerAdresse(String strAdresse) {` |
| `Thread` | 484 | ❌ | `new Thread(() -> {` |
| `allerAdresse` | 551 | ✅ | `public void allerAdresse(String strAdresse, int iZoom) {` |
| `Thread` | 563 | ❌ | `new Thread(() -> {` |
| `choixZoom` | 630 | ✅ | `public void choixZoom(int intZoom) {` |
| `getListeTypeCartes` | 646 | ✅ | `public String getListeTypeCartes() {` |
| `changeCarte` | 653 | ✅ | `public void changeCarte(String strType) {` |
| `getRepertScript` | 661 | ✅ | `public String getRepertScript() {` |
| `afficheRadar` | 668 | ✅ | `public void afficheRadar(CoordonneesGeographiques coords, double dAngle,` |
| `retireRadar` | 677 | ✅ | `public void retireRadar() {` |
| `afficheNavigateurOpenLayer` | 684 | ✅ | `public void afficheNavigateurOpenLayer() {` |
| `afficheCartesOpenlayer` | 694 | ✅ | `public void afficheCartesOpenlayer() {` |
| `setBingApiKey` | 711 | ✅ | `public void setBingApiKey(String key) {` |
| `valideBingApiKey` | 718 | ✅ | `public boolean valideBingApiKey(String key) {` |

---

### 🔶 `MarkdownViewer`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\MarkdownViewer.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** Hello

**Progression :** 11/20 éléments documentés (55.0%)

#### Propriétés (7)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `parser` | 24 | ❌ | `private final Parser parser;` |
| `renderer` | 25 | ❌ | `private final HtmlRenderer renderer;` |
| `fontesChargees` | 26 | ❌ | `private static boolean fontesChargees = false;` |
| `html` | 155 | ❌ | `return html;` |
| `charset` | 317 | ❌ | `<meta charset="UTF-8">` |
| `name` | 318 | ❌ | `<meta name="viewport" content="width=device-width, initial-scale=1.0">` |
| `markdown` | 595 | ❌ | `String markdown = """` |

#### Méthodes (13)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `MarkdownViewer` | 31 | ✅ | `public MarkdownViewer() {` |
| `chargerFontes` | 57 | ✅ | `private void chargerFontes() {` |
| `getWingdingsFontUrl` | 74 | ✅ | `private String getWingdingsFontUrl() {` |
| `genererFontFaceCSS` | 87 | ✅ | `private String genererFontFaceCSS() {` |
| `afficherFichierMarkdown` | 112 | ✅ | `public void afficherFichierMarkdown(WebView webView, String markdownFilePath)...` |
| `afficherMarkdown` | 136 | ✅ | `public void afficherMarkdown(WebView webView, String markdownContent) {` |
| `convertirMarkdownEnHtml` | 148 | ✅ | `public String convertirMarkdownEnHtml(String markdown) {` |
| `remplacerEmojis` | 165 | ✅ | `private String remplacerEmojis(String html) {` |
| `envelopperDansHtml` | 299 | ✅ | `private String envelopperDansHtml(String bodyHtml) {` |
| `envelopperDansHtml` | 300 | ❌ | `return envelopperDansHtml(bodyHtml, null);` |
| `envelopperDansHtml` | 310 | ✅ | `private String envelopperDansHtml(String bodyHtml, String baseUrl) {` |
| `main` | 594 | ✅ | `public static void main(String[] args) {` |
| `main` | 610 | ❌ | `public static void main(String[] args) {` |

---

### 🔶 `MarqueurGeolocalisation`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\MarqueurGeolocalisation.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 6/12 éléments documentés (50.0%)

#### Propriétés (6)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `coordonnees` | 14 | ❌ | `private CoordonneesGeographiques coordonnees;` |
| `strXMLMarqueur` | 15 | ❌ | `private String strXMLMarqueur = "";` |
| `strHTMLMarqueur` | 16 | ❌ | `private String strHTMLMarqueur = "";` |
| `strXMLMarqueur` | 34 | ❌ | `return strXMLMarqueur;` |
| `strHTMLMarqueur` | 48 | ❌ | `return strHTMLMarqueur;` |
| `coordonnees` | 62 | ❌ | `return coordonnees;` |

#### Méthodes (6)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getStrXMLMarqueur` | 33 | ✅ | `public String getStrXMLMarqueur() {` |
| `setStrXMLMarqueur` | 40 | ✅ | `public void setStrXMLMarqueur(String strXMLMarqueur) {` |
| `getStrHTMLMarqueur` | 47 | ✅ | `public String getStrHTMLMarqueur() {` |
| `setStrHTMLMarqueur` | 54 | ✅ | `public void setStrHTMLMarqueur(String strHTMLMarqueur) {` |
| `getCoordonnees` | 61 | ✅ | `public CoordonneesGeographiques getCoordonnees() {` |
| `setCoordonnees` | 68 | ✅ | `public void setCoordonnees(CoordonneesGeographiques coordonnees) {` |

---

### 🔶 `NavigateurCarte`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurCarte.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 7/10 éléments documentés (70.0%)

#### Propriétés (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `css` | 133 | ❌ | `return css;` |
| `webView` | 149 | ❌ | `return webView;` |
| `webEngine` | 163 | ❌ | `return webEngine;` |

#### Méthodes (7)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `replaceImageUrlsWithBase64` | 114 | ✅ | `private String replaceImageUrlsWithBase64(String css) {` |
| `encodeImageToBase64` | 139 | ✅ | `private String encodeImageToBase64(String imagePath) throws java.io.IOExcepti...` |
| `getWebView` | 148 | ✅ | `public WebView getWebView() {` |
| `setWebView` | 155 | ✅ | `public void setWebView(WebView webView) {` |
| `getWebEngine` | 162 | ✅ | `public WebEngine getWebEngine() {` |
| `setWebEngine` | 169 | ✅ | `public void setWebEngine(WebEngine webEngine) {` |
| `invalidateMapSize` | 176 | ✅ | `public void invalidateMapSize() {` |

---

### 🔶 `NavigateurCarteSeul`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurCarteSeul.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 4/6 éléments documentés (66.7%)

#### Propriétés (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `webView` | 40 | ❌ | `return webView;` |
| `webEngine` | 54 | ❌ | `return webEngine;` |

#### Méthodes (4)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getWebView` | 39 | ✅ | `public WebView getWebView() {` |
| `setWebView` | 46 | ✅ | `public void setWebView(WebView webView) {` |
| `getWebEngine` | 53 | ✅ | `public WebEngine getWebEngine() {` |
| `setWebEngine` | 60 | ✅ | `public void setWebEngine(WebEngine webEngine) {` |

---

### 🔶 `NavigateurOpenLayers`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurOpenLayers.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** JavaApplication

**Progression :** 21/42 éléments documentés (50.0%)

#### Propriétés (18)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `marqueur` | 32 | ❌ | `private CoordonneesGeographiques marqueur;` |
| `navigateurCarte` | 33 | ❌ | `private NavigateurCarte navigateurCarte;` |
| `bDebut` | 34 | ❌ | `private boolean bDebut = false;` |
| `strCartesOpenLayers` | 35 | ❌ | `private String[] strCartesOpenLayers;` |
| `strCartoActive` | 38 | ❌ | `private String strCartoActive="";` |
| `bingApiKey` | 39 | ❌ | `private String bingApiKey = "";` |
| `tfLongitudeRef` | 41 | ❌ | `private TextField tfLongitudeRef = null;` |
| `tfLatitudeRef` | 42 | ❌ | `private TextField tfLatitudeRef = null;` |
| `marqueurMisAJourParJS` | 44 | ❌ | `private boolean marqueurMisAJourParJS = false;` |
| `marqueur` | 51 | ❌ | `return marqueur;` |
| `marqueur` | 82 | ❌ | `return marqueur;` |
| `coordonnees` | 111 | ❌ | `return coordonnees;` |
| `bCarteChoisie` | 234 | ❌ | `boolean bCarteChoisie = false;` |
| `strHTML` | 379 | ❌ | `String strHTML = "<span style='font-family : Verdana,Arial,sans-serif;font-we...` |
| `apOpenLayers` | 441 | ❌ | `return apOpenLayers;` |
| `strCartoActive` | 448 | ❌ | `return strCartoActive;` |
| `apChoixCartographie` | 462 | ❌ | `return apChoixCartographie;` |
| `bingApiKey` | 476 | ❌ | `return bingApiKey;` |

#### Méthodes (24)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getMarqueur` | 50 | ✅ | `public CoordonneesGeographiques getMarqueur() {` |
| `setMarqueur` | 57 | ✅ | `public void setMarqueur(CoordonneesGeographiques marqueur) {` |
| `allerCoordonnees` | 66 | ✅ | `public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacte...` |
| `recupereCoordonnees` | 78 | ✅ | `public CoordonneesGeographiques recupereCoordonnees() {` |
| `retireMarqueur` | 118 | ✅ | `public void retireMarqueur(int iNumeroMarqueur) {` |
| `ajouteMarqueur` | 132 | ✅ | `public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coor...` |
| `allerAdresse` | 145 | ✅ | `public void allerAdresse(String strAdresse, int iFacteurZoom) {` |
| `choixZoom` | 179 | ✅ | `public void choixZoom(int iFacteurZoom) {` |
| `valideBingApiKey` | 191 | ✅ | `public void valideBingApiKey(String bingApiKey) {` |
| `recupereCartographiesOpenLayers` | 211 | ✅ | `public String recupereCartographiesOpenLayers() {` |
| `changeCarte` | 221 | ❌ | `public void changeCarte(String strCarto) {` |
| `afficheCartesOpenlayer` | 229 | ❌ | `public void afficheCartesOpenlayer() {` |
| `afficheNavigateurOpenLayer` | 256 | ✅ | `public AnchorPane afficheNavigateurOpenLayer(TextField tfLongitude, TextField...` |
| `getApChoixCartographie` | 305 | ❌ | `tfRechercheAdresse, btnRechercheAdresse, getApChoixCartographie());` |
| `getStrCartoActive` | 447 | ✅ | `public String getStrCartoActive() {` |
| `setStrCartoActive` | 454 | ✅ | `public void setStrCartoActive(String strCartoActive) {` |
| `getApChoixCartographie` | 461 | ✅ | `public AnchorPane getApChoixCartographie() {` |
| `setApChoixCartographie` | 468 | ✅ | `public void setApChoixCartographie(AnchorPane apChoixCartographie) {` |
| `getBingApiKey` | 475 | ✅ | `public String getBingApiKey() {` |
| `setBingApiKey` | 482 | ✅ | `public void setBingApiKey(String bingApiKey) {` |
| `adresseInconnue` | 495 | ✅ | `public void adresseInconnue(String msg) {` |
| `adresseTrouvee` | 504 | ✅ | `public void adresseTrouvee(double lon, double lat) {` |
| `afficheChaine` | 514 | ✅ | `public void afficheChaine(String strChaine) {` |
| `updateCoordinates` | 523 | ✅ | `public void updateCoordinates(double lon, double lat) {` |

---

### 🔶 `NavigateurOpenLayersSeul`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurOpenLayersSeul.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** JavaApplication2

**Progression :** 28/43 éléments documentés (65.1%)

#### Propriétés (14)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `marqueur` | 22 | ❌ | `private CoordonneesGeographiques marqueur;` |
| `navigateurCarteSeul` | 23 | ❌ | `private NavigateurCarteSeul navigateurCarteSeul;` |
| `bDebut` | 24 | ❌ | `private boolean bDebut = false;` |
| `strCartesOpenLayers` | 25 | ❌ | `private String[] strCartesOpenLayers;` |
| `strCartoActive` | 28 | ❌ | `private String strCartoActive = "";` |
| `bingApiKey` | 29 | ❌ | `private String bingApiKey = "";` |
| `marqueur` | 36 | ❌ | `return marqueur;` |
| `coordonnees` | 66 | ❌ | `return coordonnees;` |
| `bCarteChoisie` | 192 | ❌ | `boolean bCarteChoisie = false;` |
| `apOpenLayers` | 259 | ❌ | `return apOpenLayers;` |
| `strCartoActive` | 266 | ❌ | `return strCartoActive;` |
| `apChoixCartographie` | 280 | ❌ | `return apChoixCartographie;` |
| `bingApiKey` | 294 | ❌ | `return bingApiKey;` |
| `bDebut` | 308 | ❌ | `return bDebut;` |

#### Méthodes (29)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getMarqueur` | 35 | ✅ | `public CoordonneesGeographiques getMarqueur() {` |
| `setMarqueur` | 42 | ✅ | `public void setMarqueur(CoordonneesGeographiques marqueur) {` |
| `allerCoordonnees` | 51 | ✅ | `public void allerCoordonnees(CoordonneesGeographiques coordonnees, int iFacte...` |
| `recupereCoordonnees` | 61 | ✅ | `public CoordonneesGeographiques recupereCoordonnees() {` |
| `retireMarqueur` | 73 | ✅ | `public void retireMarqueur(int iNumeroMarqueur) {` |
| `retireMarqueurs` | 80 | ✅ | `public void retireMarqueurs() {` |
| `ajouteMarqueur` | 90 | ✅ | `public void ajouteMarqueur(int iNumeroMarqueur, CoordonneesGeographiques coor...` |
| `allerAdresse` | 101 | ✅ | `public void allerAdresse(String strAdresse, int iFacteurZoom) {` |
| `choixZoom` | 109 | ✅ | `public void choixZoom(int iFacteurZoom) {` |
| `valideBingApiKey` | 117 | ✅ | `public void valideBingApiKey(String bingApiKey) {` |
| `recupereCartographiesOpenLayers` | 135 | ✅ | `public String recupereCartographiesOpenLayers() {` |
| `retireRadar` | 139 | ❌ | `public void retireRadar() {` |
| `afficheRadar` | 153 | ✅ | `public void afficheRadar(` |
| `changeCarte` | 180 | ✅ | `public void changeCarte(String strCarto) {` |
| `afficheCartesOpenlayer` | 187 | ✅ | `public void afficheCartesOpenlayer() {` |
| `afficheNavigateurOpenLayer` | 212 | ✅ | `public AnchorPane afficheNavigateurOpenLayer() {` |
| `getStrCartoActive` | 265 | ✅ | `public String getStrCartoActive() {` |
| `setStrCartoActive` | 272 | ✅ | `public void setStrCartoActive(String strCartoActive) {` |
| `getApChoixCartographie` | 279 | ✅ | `public AnchorPane getApChoixCartographie() {` |
| `setApChoixCartographie` | 286 | ✅ | `public void setApChoixCartographie(AnchorPane apChoixCartographie) {` |
| `getBingApiKey` | 293 | ✅ | `public String getBingApiKey() {` |
| `setBingApiKey` | 300 | ✅ | `public void setBingApiKey(String bingApiKey) {` |
| `isbDebut` | 307 | ✅ | `public boolean isbDebut() {` |
| `setbDebut` | 314 | ✅ | `public void setbDebut(boolean bDebut) {` |
| `adresseInconnue` | 327 | ✅ | `public void adresseInconnue(String msg) {` |
| `adresseTrouvee` | 336 | ✅ | `public void adresseTrouvee(double lon, double lat) {` |
| `afficheChaine` | 346 | ✅ | `public void afficheChaine(String strChaine) {` |
| `zoom` | 355 | ✅ | `public void zoom(int iZoom) {` |
| `changeLayer` | 363 | ✅ | `public void changeLayer(String strNomLayer) {` |

---

### ⚠️ `NavigateurPanoramique`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\NavigateurPanoramique.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 29/104 éléments documentés (27.9%)

#### Propriétés (58)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `minFov` | 94 | ❌ | `private double minFov = 1;   // 1° pour permettre un zoom important` |
| `bChoixHotSpot` | 95 | ❌ | `private boolean bChoixHotSpot = false;` |
| `rapportDegToRad` | 102 | ❌ | `private final double rapportDegToRad = Math.PI / 180.d;` |
| `camera1` | 103 | ❌ | `private PerspectiveCamera camera1;` |
| `sscPanorama` | 104 | ❌ | `private SubScene sscPanorama;` |
| `lblNordOuest` | 105 | ❌ | `private Label lblNord, lblSud, lblEst, lblOuest, lblNordEst, lblSudOuest, lbl...` |
| `apPanorama` | 106 | ❌ | `private AnchorPane apNord, apPanorama;` |
| `bMouvement` | 107 | ❌ | `private boolean bMouvement = false;` |
| `nomFichierPanoramique` | 114 | ❌ | `private String nomFichierPanoramique = "";` |
| `iChangeVignette` | 115 | ❌ | `private int iChangeVignette = 0;` |
| `imgPanoramique` | 116 | ❌ | `private Image imgPanoramique;` |
| `btnChoixVignette` | 117 | ❌ | `private Button btnChoixNord, btnChoixVue, btnChoixVignette;` |
| `imgVignetteHS` | 121 | ❌ | `private Image imgVignetteHS;` |
| `iHauteur` | 148 | ❌ | `int iHauteur = iLargeur / 2 / iRapport;` |
| `imgMercator` | 164 | ❌ | `return imgMercator;` |
| `perspectiveCamera` | 174 | ❌ | `return perspectiveCamera;` |
| `positionNord` | 213 | ❌ | `double positionNord;` |
| `positionSud` | 218 | ❌ | `double positionSud;` |
| `positionEst` | 223 | ❌ | `double positionEst;` |
| `positionOuest` | 228 | ❌ | `double positionOuest;` |
| `positionNordEst` | 233 | ❌ | `double positionNordEst;` |
| `positionNordOuest` | 238 | ❌ | `double positionNordOuest;` |
| `positionSudEst` | 243 | ❌ | `double positionSudEst;` |
| `positionSudOuest` | 248 | ❌ | `double positionSudOuest;` |
| `iNombreTrait` | 254 | ❌ | `int iNombreTrait = 360;` |
| `positionTrait` | 255 | ❌ | `double positionTrait;` |
| `couleur` | 259 | ❌ | `Color couleur = Color.WHITE;` |
| `hautTrait` | 260 | ❌ | `double hautTrait = 15;` |
| `apPanorama` | 687 | ❌ | `return apPanorama;` |
| `latitude` | 706 | ❌ | `return latitude;` |
| `ancienneValeur` | 713 | ❌ | `double ancienneValeur = this.latitude;` |
| `nouvelleValeur` | 715 | ❌ | `double nouvelleValeur = this.latitude;` |
| `longitude` | 723 | ❌ | `return longitude;` |
| `ancienneValeur` | 730 | ❌ | `double ancienneValeur = this.longitude;` |
| `nouvelleValeur` | 732 | ❌ | `double nouvelleValeur = this.longitude;` |
| `fov` | 740 | ❌ | `return fov;` |
| `ancienneValeur` | 747 | ❌ | `double ancienneValeur = this.fov;` |
| `nouvelleValeur` | 750 | ❌ | `double nouvelleValeur = this.fov;` |
| `positNord` | 758 | ❌ | `return positNord;` |
| `ancienneValeur` | 765 | ❌ | `double ancienneValeur = this.positNord;` |
| `nouvelleValeur` | 767 | ❌ | `double nouvelleValeur = this.positNord;` |
| `nomFichierPanoramique` | 775 | ❌ | `return nomFichierPanoramique;` |
| `choixLongitude` | 789 | ❌ | `return choixLongitude;` |
| `ancienneValeur` | 796 | ❌ | `double ancienneValeur = this.choixLongitude;` |
| `nouvelleValeur` | 798 | ❌ | `double nouvelleValeur = this.choixLongitude;` |
| `choixLatitude` | 806 | ❌ | `return choixLatitude;` |
| `ancienneValeur` | 813 | ❌ | `double ancienneValeur = this.choixLatitude;` |
| `nouvelleValeur` | 815 | ❌ | `double nouvelleValeur = this.choixLatitude;` |
| `choixFov` | 823 | ❌ | `return choixFov;` |
| `ancienneValeur` | 830 | ❌ | `double ancienneValeur = this.choixFov;` |
| `nouvelleValeur` | 832 | ❌ | `double nouvelleValeur = this.choixFov;` |
| `maxFov` | 854 | ❌ | `return maxFov;` |
| `minFov` | 874 | ❌ | `return minFov;` |
| `iChangeVignette` | 894 | ❌ | `return iChangeVignette;` |
| `ancienneValeur` | 901 | ❌ | `int ancienneValeur = this.iChangeVignette;` |
| `nouvelleValeur` | 903 | ❌ | `int nouvelleValeur = this.iChangeVignette;` |
| `bChoixHotSpot` | 911 | ❌ | `return bChoixHotSpot;` |
| `imgVignetteHS` | 925 | ❌ | `return imgVignetteHS;` |

#### Méthodes (46)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `captureEcran` | 56 | ❌ | `public void captureEcran() {` |
| `captureEcranHS` | 71 | ❌ | `public Image captureEcranHS() {` |
| `addPropertyChangeListener` | 77 | ❌ | `public void addPropertyChangeListener(String propertyName, PropertyChangeList...` |
| `removePropertyChangeListener` | 81 | ❌ | `public void removePropertyChangeListener(String propertyName, PropertyChangeL...` |
| `firePropertyChange` | 85 | ❌ | `public void firePropertyChange(String propertyName, BigDecimal oldValue, BigD...` |
| `NavigateurPanoramique` | 123 | ❌ | `public NavigateurPanoramique(Image imgFichierPanoramique, double positX, doub...` |
| `NavigateurPanoramique` | 133 | ❌ | `public NavigateurPanoramique(Image imgFichierPanoramique, double positX, doub...` |
| `imgTransformationImage` | 142 | ❌ | `public static Image imgTransformationImage(Image imgRect) {` |
| `imgTransformationImage` | 143 | ❌ | `return imgTransformationImage(imgRect, 2);` |
| `imgTransformationImage` | 146 | ❌ | `public static Image imgTransformationImage(Image imgRect, int iRapport) {` |
| `addCamera` | 167 | ❌ | `private PerspectiveCamera addCamera(SubScene scene) {` |
| `degToRad` | 182 | ✅ | `private double degToRad(double angleDeg) {` |
| `affiche` | 186 | ❌ | `public void affiche() {` |
| `changeTaille` | 296 | ❌ | `public void changeTaille(double largeur, double hauteur) {` |
| `reaffiche` | 302 | ❌ | `private void reaffiche() {` |
| `affichePano` | 675 | ❌ | `public AnchorPane affichePano() {` |
| `setNomImagePanoramique` | 691 | ❌ | `public void setNomImagePanoramique(String strImagePanoramique, int iRapport) {` |
| `setImagePanoramique` | 696 | ❌ | `public void setImagePanoramique(String strImagePanoramique, Image imgPanorami...` |
| `getLatitude` | 705 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 712 | ✅ | `public void setLatitude(double latitude) {` |
| `getLongitude` | 722 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 729 | ✅ | `public void setLongitude(double longitude) {` |
| `getFov` | 739 | ✅ | `public double getFov() {` |
| `setFov` | 746 | ✅ | `public void setFov(double fov) {` |
| `getPositNord` | 757 | ✅ | `public double getPositNord() {` |
| `setPositNord` | 764 | ✅ | `public void setPositNord(double positNord) {` |
| `getNomFichierPanoramique` | 774 | ✅ | `public String getNomFichierPanoramique() {` |
| `setNomFichierPanoramique` | 781 | ✅ | `public void setNomFichierPanoramique(String nomFichierPanoramique) {` |
| `getChoixLongitude` | 788 | ✅ | `public double getChoixLongitude() {` |
| `setChoixLongitude` | 795 | ✅ | `public void setChoixLongitude(double choixLongitude) {` |
| `getChoixLatitude` | 805 | ✅ | `public double getChoixLatitude() {` |
| `setChoixLatitude` | 812 | ✅ | `public void setChoixLatitude(double choixLatitude) {` |
| `getChoixFov` | 822 | ✅ | `public double getChoixFov() {` |
| `setChoixFov` | 829 | ✅ | `public void setChoixFov(double choixFov) {` |
| `getImgPanoramique` | 839 | ✅ | `public Image getImgPanoramique() {` |
| `setImgPanoramique` | 846 | ✅ | `public void setImgPanoramique(Image imgPanoramique) {` |
| `getMaxFov` | 853 | ✅ | `public double getMaxFov() {` |
| `setMaxFov` | 860 | ✅ | `public void setMaxFov(double maxFov) {` |
| `getMinFov` | 873 | ✅ | `public double getMinFov() {` |
| `setMinFov` | 880 | ✅ | `public void setMinFov(double minFov) {` |
| `getiChangeVignette` | 893 | ✅ | `public int getiChangeVignette() {` |
| `setiChangeVignette` | 900 | ✅ | `public void setiChangeVignette() {` |
| `isbChoixHotSpot` | 910 | ✅ | `public boolean isbChoixHotSpot() {` |
| `setbChoixHotSpot` | 917 | ✅ | `public void setbChoixHotSpot(boolean bChoixHotSpot) {` |
| `getImgVignetteHS` | 924 | ✅ | `public Image getImgVignetteHS() {` |
| `setImgVignetteHS` | 931 | ✅ | `public void setImgVignetteHS(Image imgVignetteHS) {` |

---

### ⚠️ `OllamaService`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\OllamaService.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 29/115 éléments documentés (25.2%)

#### Propriétés (66)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `OLLAMA_URL` | 24 | ❌ | `private static final String OLLAMA_URL = "http://localhost:11434";` |
| `GENERATE_ENDPOINT` | 25 | ❌ | `private static final String GENERATE_ENDPOINT = "/api/generate";` |
| `TAGS_ENDPOINT` | 26 | ❌ | `private static final String TAGS_ENDPOINT = "/api/tags";` |
| `ollamaModel` | 27 | ❌ | `private static String ollamaModel = "mistral"; // Sera détecté automatiquement` |
| `LOCATIONIQ_URL` | 30 | ❌ | `private static final String LOCATIONIQ_URL = "https://us1.locationiq.com/v1/r...` |
| `LOCATIONIQ_TOKEN` | 31 | ❌ | `private static String LOCATIONIQ_TOKEN = null;` |
| `OPENROUTER_URL` | 34 | ❌ | `private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/ch...` |
| `OPENROUTER_TOKEN` | 35 | ❌ | `private static String OPENROUTER_TOKEN = null;` |
| `OPENROUTER_MODELS` | 46 | ❌ | `private static final String[] OPENROUTER_MODELS = {` |
| `openrouterModel` | 57 | ❌ | `private static String openrouterModel = OPENROUTER_MODELS[0]; // Claude Sonne...` |
| `HUGGINGFACE_MODELS` | 61 | ❌ | `private static final String[] HUGGINGFACE_MODELS = {` |
| `HUGGINGFACE_URL` | 68 | ❌ | `private static String HUGGINGFACE_URL = "https://api-inference.huggingface.co...` |
| `currentModelIndex` | 69 | ❌ | `private static int currentModelIndex = 0;` |
| `HUGGINGFACE_TOKEN` | 72 | ❌ | `private static String HUGGINGFACE_TOKEN = null;` |
| `TIMEOUT_MS` | 74 | ❌ | `private static final int TIMEOUT_MS = 5000;` |
| `useHuggingFace` | 75 | ❌ | `private static boolean useHuggingFace = false;` |
| `forceOllama` | 78 | ❌ | `private static boolean forceOllama = false;` |
| `cheminFichier` | 91 | ❌ | `String cheminFichier = "api-keys.properties";` |
| `true` | 177 | ❌ | `return true;` |
| `false` | 196 | ❌ | `return false;` |
| `testRequest` | 214 | ❌ | `String testRequest = "{\"inputs\":\"test\"}";` |
| `true` | 226 | ❌ | `return true;` |
| `false` | 230 | ❌ | `return false;` |
| `true` | 234 | ❌ | `return true;` |
| `true` | 239 | ❌ | `return true;` |
| `line` | 252 | ❌ | `String line;` |
| `modelesPreferences` | 263 | ❌ | `String[] modelesPreferences = {` |
| `bestModel` | 278 | ❌ | `String bestModel = null;` |
| `bestPriority` | 279 | ❌ | `int bestPriority = Integer.MAX_VALUE;` |
| `searchPos` | 282 | ❌ | `int searchPos = 0;` |
| `modeles` | 344 | ❌ | `return modeles; // Liste vide` |
| `line` | 350 | ❌ | `String line;` |
| `searchPos` | 359 | ❌ | `int searchPos = 0;` |
| `modeles` | 373 | ❌ | `return modeles;` |
| `OPENROUTER_MODELS` | 381 | ❌ | `return OPENROUTER_MODELS;` |
| `ollamaModel` | 411 | ❌ | `return ollamaModel;` |
| `openrouterModel` | 419 | ❌ | `return openrouterModel;` |
| `forceOllama` | 436 | ❌ | `return forceOllama;` |
| `modelShort` | 452 | ❌ | `String modelShort = openrouterModel;` |
| `null` | 475 | ❌ | `return null;` |
| `urlStr` | 479 | ❌ | `String urlStr = LOCATIONIQ_URL + "?key=" + LOCATIONIQ_TOKEN` |
| `null` | 493 | ❌ | `return null;` |
| `line` | 500 | ❌ | `String line;` |
| `lieuStructure` | 518 | ❌ | `return lieuStructure;` |
| `null` | 522 | ❌ | `return null;` |
| `null` | 526 | ❌ | `return null;` |
| `champsTouristiques` | 545 | ❌ | `String[] champsTouristiques = {` |
| `localite` | 615 | ❌ | `String localite = null;` |
| `result` | 663 | ❌ | `return result;` |
| `searchStr` | 752 | ❌ | `String searchStr = "\"" + fieldName + "\":\"";` |
| `lieuReel` | 802 | ❌ | `String lieuReel = null;` |
| `description` | 825 | ❌ | `return description;` |
| `langue` | 845 | ❌ | `String langue = "français";` |
| `sujetPrincipal` | 876 | ❌ | `String sujetPrincipal = null;` |
| `mainSubject` | 919 | ❌ | `String mainSubject = null;` |
| `finalPrompt` | 1012 | ❌ | `return finalPrompt;` |
| `resultat` | 1030 | ❌ | `return resultat;` |
| `messageErreur` | 1047 | ❌ | `String messageErreur = "❌ Aucun service IA disponible !\n\n";` |
| `line` | 1114 | ❌ | `String line;` |
| `line` | 1125 | ❌ | `String line;` |
| `errorMsg` | 1186 | ❌ | `String errorMsg = "Erreur HTTP " + responseCode + " pour " + ollamaModel;` |
| `line` | 1196 | ❌ | `String line;` |
| `modelName` | 1237 | ❌ | `String modelName = HUGGINGFACE_MODELS[currentModelIndex];` |
| `jsonRequest` | 1238 | ❌ | `String jsonRequest;` |
| `line` | 1280 | ❌ | `String line;` |
| `line` | 1291 | ❌ | `String line;` |

#### Méthodes (49)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `chargerTokensAPI` | 88 | ✅ | `private static void chargerTokensAPI() {` |
| `verifierOpenRouterDisponible` | 150 | ✅ | `public static boolean verifierOpenRouterDisponible() {` |
| `isOllamaAvailable` | 158 | ✅ | `public static boolean isOllamaAvailable() {` |
| `detecterModeleOllama` | 247 | ✅ | `private static void detecterModeleOllama(HttpURLConnection conn) {` |
| `InputStreamReader` | 251 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `InputStreamReader` | 349 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `getModelesOpenRouterDisponibles` | 380 | ✅ | `public static String[] getModelesOpenRouterDisponibles() {` |
| `setOllamaModel` | 388 | ✅ | `public static void setOllamaModel(String modelName) {` |
| `setOpenRouterModel` | 399 | ✅ | `public static void setOpenRouterModel(String modelName) {` |
| `getOllamaModel` | 410 | ✅ | `public static String getOllamaModel() {` |
| `getOpenRouterModel` | 418 | ✅ | `public static String getOpenRouterModel() {` |
| `setForceOllama` | 426 | ✅ | `public static void setForceOllama(boolean force) {` |
| `isForceOllama` | 435 | ✅ | `public static boolean isForceOllama() {` |
| `getServiceName` | 443 | ✅ | `public static String getServiceName() {` |
| `geocodeReverse` | 469 | ✅ | `private static String geocodeReverse(String latitude, String longitude) {` |
| `InputStreamReader` | 499 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `parserLocationIQ` | 543 | ✅ | `private static String parserLocationIQ(String json) {` |
| `tradTypeTourisme` | 669 | ✅ | `private static String tradTypeTourisme(String type) {` |
| `tradTypeHistorique` | 686 | ✅ | `private static String tradTypeHistorique(String type) {` |
| `tradTypeNaturel` | 705 | ✅ | `private static String tradTypeNaturel(String type) {` |
| `tradTypeLoisir` | 722 | ✅ | `private static String tradTypeLoisir(String type) {` |
| `tradTypeEau` | 735 | ✅ | `private static String tradTypeEau(String type) {` |
| `extraireChamp` | 751 | ✅ | `private static String extraireChamp(String json, String fieldName) {` |
| `reinitialiserCacheDescriptions` | 771 | ✅ | `public static void reinitialiserCacheDescriptions() {` |
| `genererDescription` | 786 | ✅ | `public static CompletableFuture<String> genererDescription(` |
| `RuntimeException` | 829 | ❌ | `throw new RuntimeException("Erreur lors de la génération : " + e.getMessage()...` |
| `construirePrompt` | 839 | ✅ | `private static String construirePrompt(String titreVisite, String titrePanora...` |
| `appellerOllama` | 1023 | ✅ | `private static String appellerOllama(String prompt) throws Exception {` |
| `appellerOllamaLocal` | 1042 | ❌ | `return appellerOllamaLocal(prompt);` |
| `Exception` | 1067 | ❌ | `throw new Exception(messageErreur);` |
| `Exception` | 1072 | ❌ | `throw new Exception("❌ Mode Hugging Face désactivé. Utilisez OpenRouter ou Ol...` |
| `appellerOpenRouter` | 1079 | ✅ | `private static String appellerOpenRouter(String prompt) throws Exception {` |
| `InputStreamReader` | 1113 | ❌ | `new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 1119 | ❌ | `throw new Exception("OpenRouter HTTP " + responseCode + ": " + errorMsg.toStr...` |
| `InputStreamReader` | 1124 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 1140 | ❌ | `throw new Exception("Format de réponse OpenRouter invalide");` |
| `appellerOllamaLocal` | 1158 | ✅ | `private static String appellerOllamaLocal(String prompt) throws Exception {` |
| `Exception` | 1190 | ❌ | `throw new Exception(errorMsg);` |
| `InputStreamReader` | 1195 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `extraireReponse` | 1206 | ❌ | `return extraireReponse(jsonResponse);` |
| `appellerHuggingFace` | 1212 | ✅ | `private static String appellerHuggingFace(String prompt) throws Exception {` |
| `appellerHuggingFace` | 1263 | ❌ | `return appellerHuggingFace(prompt); // Réessayer` |
| `appellerHuggingFace` | 1272 | ❌ | `return appellerHuggingFace(prompt); // Réessayer avec le modèle suivant` |
| `InputStreamReader` | 1279 | ❌ | `new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 1285 | ❌ | `throw new Exception("Erreur Hugging Face (HTTP " + responseCode + "): " + err...` |
| `InputStreamReader` | 1290 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `extraireReponseHuggingFace` | 1301 | ❌ | `return extraireReponseHuggingFace(jsonResponse);` |
| `extraireReponse` | 1307 | ✅ | `private static String extraireReponse(String json) {` |
| `extraireReponseHuggingFace` | 1334 | ✅ | `private static String extraireReponseHuggingFace(String json) {` |

---

### ⚠️ `OpenRouterClient`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\OpenRouterClient.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 13/35 éléments documentés (37.1%)

#### Propriétés (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `API_BASE_URL` | 21 | ❌ | `private static final String API_BASE_URL = "https://openrouter.ai/api/v1/chat...` |
| `apiKey` | 22 | ❌ | `private final String apiKey;` |
| `modelName` | 23 | ❌ | `private final String modelName;` |
| `line` | 128 | ❌ | `String line;` |
| `line` | 140 | ❌ | `String line;` |
| `systemMessage` | 175 | ❌ | `String systemMessage = "Tu es un assistant qui aide à créer des descriptions ...` |
| `systemMessage` | 194 | ❌ | `String systemMessage = "Tu es un assistant qui aide à identifier des points d...` |
| `marker` | 222 | ❌ | `String marker = "\"content\":";` |
| `jsonResponse` | 225 | ❌ | `return jsonResponse; // Retourner la réponse brute si format non reconnu` |
| `endIdx` | 236 | ❌ | `int endIdx = startIdx;` |
| `modelName` | 267 | ❌ | `return modelName;` |

#### Méthodes (24)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `OpenRouterClient` | 28 | ✅ | `public OpenRouterClient() {` |
| `OpenRouterClient` | 42 | ✅ | `public OpenRouterClient(String apiKey, String modelName) {` |
| `chat` | 53 | ✅ | `public String chat(String userMessage) throws Exception {` |
| `chat` | 54 | ❌ | `return chat(userMessage, null, 0.7, 1000);` |
| `chat` | 64 | ✅ | `public String chat(String userMessage, String systemMessage) throws Exception {` |
| `chat` | 65 | ❌ | `return chat(userMessage, systemMessage, 0.7, 1000);` |
| `chat` | 77 | ✅ | `public String chat(String userMessage, String systemMessage, double temperatu...` |
| `IllegalStateException` | 79 | ❌ | `throw new IllegalStateException("Clé API OpenRouter non configurée");` |
| `InputStreamReader` | 126 | ❌ | `new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {` |
| `extractMessageContent` | 134 | ❌ | `return extractMessageContent(response.toString());` |
| `InputStreamReader` | 138 | ❌ | `new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {` |
| `Exception` | 144 | ❌ | `throw new Exception("Erreur API OpenRouter (" + responseCode + "): " + error);` |
| `chatAsync` | 157 | ✅ | `public CompletableFuture<String> chatAsync(String userMessage) {` |
| `chat` | 160 | ❌ | `return chat(userMessage);` |
| `RuntimeException` | 162 | ❌ | `throw new RuntimeException("Erreur lors de l'appel à OpenRouter", e);` |
| `generatePanoramaDescription` | 174 | ✅ | `public String generatePanoramaDescription(String titre, String tags) throws E...` |
| `chat` | 183 | ❌ | `return chat(userMessage, systemMessage, 0.8, 200);` |
| `suggestHotspots` | 193 | ✅ | `public String suggestHotspots(String titre, String description) throws Except...` |
| `chat` | 202 | ❌ | `return chat(userMessage, systemMessage, 0.7, 500);` |
| `escapeJson` | 208 | ✅ | `private String escapeJson(String text) {` |
| `extractMessageContent` | 220 | ✅ | `private String extractMessageContent(String jsonResponse) {` |
| `isConfigured` | 258 | ✅ | `public boolean isConfigured() {` |
| `getModelName` | 266 | ✅ | `public String getModelName() {` |
| `getFreeModels` | 274 | ✅ | `public static String[] getFreeModels() {` |

---

### ❌ `OrdrePanoramique`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\OrdrePanoramique.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 3/16 éléments documentés (18.8%)

#### Propriétés (8)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `lstStrPanos` | 25 | ❌ | `private ListView<String> lstStrPanos;` |
| `iTrouve` | 84 | ❌ | `int iTrouve = -1;` |
| `strPanos1` | 92 | ❌ | `ObservableList<String> strPanos1 = strPanos;` |
| `strPanos1` | 125 | ❌ | `ObservableList<String> strPanos1 = strPanos;` |
| `iTaillePano` | 157 | ❌ | `int iTaillePano = 0;` |
| `strPanos` | 196 | ❌ | `return strPanos;` |
| `cellulesPanoramiques` | 203 | ❌ | `return cellulesPanoramiques;` |
| `apListePanoramiques` | 210 | ❌ | `return apListePanoramiques;` |

#### Méthodes (8)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `creeListe` | 27 | ❌ | `public void creeListe() {` |
| `creeListe` | 54 | ❌ | `public void creeListe(String strOrdre) {` |
| `supprimerElement` | 83 | ❌ | `public void supprimerElement(int iElement) {` |
| `rafraichitListe` | 124 | ❌ | `public void rafraichitListe() {` |
| `ajouteNouveauxPanos` | 155 | ❌ | `public void ajouteNouveauxPanos() {` |
| `getStrPanos` | 195 | ✅ | `public ObservableList<String> getStrPanos() {` |
| `getCellulesPanoramiques` | 202 | ✅ | `public ObservableList<PanoramiqueCellule> getCellulesPanoramiques() {` |
| `getApListePanoramiques` | 209 | ✅ | `public AnchorPane getApListePanoramiques() {` |

---

### ⚠️ `PaneOutil`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\PaneOutil.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 9/22 éléments documentés (40.9%)

#### Propriétés (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `apOutil` | 21 | ❌ | `private AnchorPane apOutil;` |
| `apPaneOutil` | 22 | ❌ | `private AnchorPane apPaneOutil;` |
| `lblPanelTitre` | 23 | ❌ | `private Label lblPanelTitre;` |
| `bValide` | 24 | ❌ | `private boolean bValide;` |
| `bInvariant` | 25 | ❌ | `private boolean bInvariant=false;` |
| `ivBtnPlusOutil` | 26 | ❌ | `private ImageView ivBtnPlusOutil;` |
| `apOutil` | 100 | ❌ | `return apOutil;` |
| `apPaneOutil` | 114 | ❌ | `return apPaneOutil;` |
| `bValide` | 121 | ❌ | `return bValide;` |
| `bInvariant` | 145 | ❌ | `return bInvariant;` |
| `ivBtnPlusOutil` | 159 | ❌ | `return ivBtnPlusOutil;` |

#### Méthodes (11)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `deplieReplie` | 28 | ❌ | `public static void deplieReplie(AnchorPane apTitre, ImageView ivBtnPlusTitre) {` |
| `affiche` | 58 | ❌ | `private void affiche(String strTitre, AnchorPane apContenu, double largeur, b...` |
| `getApOutil` | 99 | ✅ | `public AnchorPane getApOutil() {` |
| `setApOutil` | 106 | ✅ | `public void setApOutil(AnchorPane apOutil) {` |
| `getApPaneOutil` | 113 | ✅ | `public AnchorPane getApPaneOutil() {` |
| `getbValide` | 120 | ✅ | `public boolean getbValide() {` |
| `setbValide` | 127 | ✅ | `public void setbValide(boolean bValide) {` |
| `isbInvariant` | 144 | ✅ | `public boolean isbInvariant() {` |
| `setbInvariant` | 151 | ✅ | `public void setbInvariant(boolean bInvariant) {` |
| `getIvBtnPlusOutil` | 158 | ✅ | `public ImageView getIvBtnPlusOutil() {` |
| `setIvBtnPlusOutil` | 165 | ✅ | `public void setIvBtnPlusOutil(ImageView ivBtnPlusOutil) {` |

---

### ⚠️ `PanoramicCube`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\PanoramicCube.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 4/18 éléments documentés (22.2%)

#### Propriétés (13)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `CUBE_SIZE` | 22 | ❌ | `private static final double CUBE_SIZE = 400;` |
| `faces` | 23 | ❌ | `private final Box[] faces = new Box[6];` |
| `materials` | 24 | ❌ | `private final PhongMaterial[] materials = new PhongMaterial[6];` |
| `FRONT` | 27 | ❌ | `private static final int FRONT = 0;` |
| `BACK` | 28 | ❌ | `private static final int BACK = 1;` |
| `LEFT` | 29 | ❌ | `private static final int LEFT = 2;` |
| `RIGHT` | 30 | ❌ | `private static final int RIGHT = 3;` |
| `TOP` | 31 | ❌ | `private static final int TOP = 4;` |
| `BOTTOM` | 32 | ❌ | `private static final int BOTTOM = 5;` |
| `halfSize` | 45 | ❌ | `double halfSize = CUBE_SIZE / 2;` |
| `source` | 143 | ❌ | `return source;` |
| `resized` | 169 | ❌ | `return resized;` |
| `CUBE_SIZE` | 177 | ❌ | `return CUBE_SIZE;` |

#### Méthodes (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `PanoramicCube` | 34 | ❌ | `public PanoramicCube() {` |
| `createCubeFaces` | 44 | ✅ | `private void createCubeFaces() {` |
| `setPanoramicImage` | 90 | ✅ | `public void setPanoramicImage(Image panoramicImage) {` |
| `resizeToEquirectangular` | 137 | ✅ | `private Image resizeToEquirectangular(Image source, int targetWidth, int targ...` |
| `getCubeSize` | 176 | ✅ | `public double getCubeSize() {` |

---

### 🔶 `Panoramique`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\Panoramique.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 81/145 éléments documentés (55.9%)

#### Propriétés (66)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `SPHERE` | 20 | ✅ | `public static final String SPHERE = "sphere";` |
| `CUBE` | 25 | ✅ | `public static final String CUBE = "cube";` |
| `hotspots` | 27 | ❌ | `private final HotSpot[] hotspots = new HotSpot[100];` |
| `imageHotspot` | 28 | ❌ | `private final HotspotImage[] imageHotspot = new HotspotImage[100];` |
| `HTMLHotspot` | 29 | ❌ | `private final HotspotHTML[] HTMLHotspot = new HotspotHTML[100];` |
| `diapoHotspot` | 30 | ❌ | `private final HotspotDiaporama[] diapoHotspot = new HotspotDiaporama[100];` |
| `strTitrePanoramique` | 31 | ❌ | `private String strTitrePanoramique="";` |
| `strNomFichier` | 32 | ❌ | `private String strNomFichier="";` |
| `regardX` | 33 | ❌ | `private double regardX = 0.d;` |
| `regardY` | 34 | ❌ | `private double regardY = 0.d;` |
| `champVisuel` | 35 | ❌ | `private double champVisuel=75.d;` |
| `minLat` | 36 | ❌ | `private double minLat=-1000;` |
| `maxLat` | 37 | ❌ | `private double maxLat=1000;` |
| `fovMax` | 38 | ❌ | `private double fovMax=70;` |
| `fovMin` | 39 | ❌ | `private double fovMin=12;` |
| `bMinLat` | 40 | ❌ | `private boolean bMinLat=false;` |
| `bMaxLat` | 41 | ❌ | `private boolean bMaxLat=false;` |
| `imgPanoramique` | 42 | ❌ | `private Image imgPanoramique;` |
| `imgVisuPanoramique` | 43 | ❌ | `private Image imgVisuPanoramique;` |
| `imgVignettePanoramique` | 44 | ❌ | `private Image imgVignettePanoramique;` |
| `imgPanoRect` | 45 | ❌ | `private Image imgPanoRect;` |
| `imgPanoRectListe` | 46 | ❌ | `private Image imgPanoRectListe;` |
| `imgCubeEqui` | 47 | ❌ | `private Image imgCubeEqui;` |
| `iNombreHotspots` | 48 | ❌ | `private int iNombreHotspots = 0;` |
| `iNombreHotspotImage` | 49 | ❌ | `private int iNombreHotspotImage = 0;` |
| `iNombreHotspotHTML` | 50 | ❌ | `private int iNombreHotspotHTML = 0;` |
| `iNombreHotspotDiapo` | 51 | ❌ | `private int iNombreHotspotDiapo = 0;` |
| `strTypePanoramique` | 52 | ❌ | `private String strTypePanoramique="";` |
| `bAfficheTitre` | 53 | ❌ | `private boolean bAfficheTitre;` |
| `bAffDescription` | 54 | ❌ | `private boolean bAffDescription;` |
| `bAfficheInfo` | 55 | ❌ | `private boolean bAfficheInfo;` |
| `bAffichePlan` | 56 | ❌ | `private boolean bAffichePlan;` |
| `iNumeroPlan` | 57 | ❌ | `private int iNumeroPlan = -1;` |
| `zeroNord` | 58 | ❌ | `private double zeroNord = 0;` |
| `nombreNiveaux` | 59 | ❌ | `private double nombreNiveaux = 0;` |
| `marqueurGeolocatisation` | 60 | ❌ | `private CoordonneesGeographiques marqueurGeolocatisation=null;` |
| `strDescriptionIA` | 61 | ❌ | `private String strDescriptionIA = "";` |
| `bAffichePlan` | 79 | ❌ | `return bAffichePlan;` |
| `strNomFichier` | 93 | ❌ | `return strNomFichier;` |
| `regardX` | 107 | ❌ | `return regardX;` |
| `regardY` | 121 | ❌ | `return regardY;` |
| `imgPanoramique` | 284 | ❌ | `return imgPanoramique;` |
| `iNombreHotspots` | 298 | ❌ | `return iNombreHotspots;` |
| `strTypePanoramique` | 312 | ❌ | `return strTypePanoramique;` |
| `bAfficheTitre` | 326 | ❌ | `return bAfficheTitre;` |
| `bAffDescription` | 340 | ❌ | `return bAffDescription;` |
| `bAfficheInfo` | 354 | ❌ | `return bAfficheInfo;` |
| `strTitrePanoramique` | 368 | ❌ | `return strTitrePanoramique;` |
| `imgVignettePanoramique` | 382 | ❌ | `return imgVignettePanoramique;` |
| `zeroNord` | 396 | ❌ | `return zeroNord;` |
| `nombreNiveaux` | 410 | ❌ | `return nombreNiveaux;` |
| `iNombreHotspotImage` | 424 | ❌ | `return iNombreHotspotImage;` |
| `iNombreHotspotHTML` | 438 | ❌ | `return iNombreHotspotHTML;` |
| `iNumeroPlan` | 452 | ❌ | `return iNumeroPlan;` |
| `marqueurGeolocatisation` | 480 | ❌ | `return marqueurGeolocatisation;` |
| `champVisuel` | 494 | ❌ | `return champVisuel;` |
| `imgPanoRect` | 508 | ❌ | `return imgPanoRect;` |
| `minLat` | 522 | ❌ | `return minLat;` |
| `maxLat` | 536 | ❌ | `return maxLat;` |
| `bMinLat` | 550 | ❌ | `return bMinLat;` |
| `bMaxLat` | 564 | ❌ | `return bMaxLat;` |
| `iNombreHotspotDiapo` | 578 | ❌ | `return iNombreHotspotDiapo;` |
| `imgPanoRectListe` | 592 | ❌ | `return imgPanoRectListe;` |
| `imgCubeEqui` | 606 | ❌ | `return imgCubeEqui;` |
| `fovMax` | 620 | ❌ | `return fovMax;` |
| `fovMin` | 634 | ❌ | `return fovMin;` |

#### Méthodes (79)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `Panoramique` | 67 | ✅ | `public void Panoramique(String strFichier) {` |
| `isAffichePlan` | 78 | ✅ | `public  boolean isAffichePlan() {` |
| `setAffichePlan` | 85 | ✅ | `public  void setAffichePlan(boolean bAffichePlan) {` |
| `getStrNomFichier` | 92 | ✅ | `public String getStrNomFichier() {` |
| `setStrNomFichier` | 99 | ✅ | `public void setStrNomFichier(String strNomFichier) {` |
| `getRegardX` | 106 | ✅ | `public double getRegardX() {` |
| `setRegardX` | 113 | ✅ | `public void setRegardX(double lookAtX) {` |
| `getRegardY` | 120 | ✅ | `public double getRegardY() {` |
| `setRegardY` | 127 | ✅ | `public void setRegardY(double lookAtY) {` |
| `getHotspot` | 135 | ✅ | `public HotSpot getHotspot(int i) {` |
| `setHotspot` | 143 | ✅ | `public void setHotspot(HotSpot hotspot, int i) {` |
| `addHotspot` | 151 | ✅ | `public void addHotspot(HotSpot hotspot) {` |
| `removeHotspot` | 160 | ✅ | `public void removeHotspot(int num) {` |
| `getHotspotImage` | 172 | ✅ | `public HotspotImage getHotspotImage(int i) {` |
| `setHotspotImage` | 180 | ✅ | `public void setHotspotImage(HotspotImage hotspot, int i) {` |
| `addHotspotImage` | 188 | ✅ | `public void addHotspotImage(HotspotImage hotspot) {` |
| `removeHotspotImage` | 197 | ✅ | `public void removeHotspotImage(int num) {` |
| `getHotspotHTML` | 209 | ✅ | `public HotspotHTML getHotspotHTML(int i) {` |
| `setHotspotHTML` | 217 | ✅ | `public void setHotspotHTML(HotspotHTML hotspot, int i) {` |
| `addHotspotHTML` | 225 | ✅ | `public void addHotspotHTML(HotspotHTML hotspot) {` |
| `removeHotspotHTML` | 234 | ✅ | `public void removeHotspotHTML(int num) {` |
| `getHotspotDiapo` | 246 | ✅ | `public HotspotDiaporama getHotspotDiapo(int i) {` |
| `setHotspotDiapo` | 254 | ✅ | `public void setHotspotDiapo(HotspotDiaporama hotspot, int i) {` |
| `addHotspotDiapo` | 262 | ✅ | `public void addHotspotDiapo(HotspotDiaporama hotspot) {` |
| `removeHotspotdiapo` | 271 | ✅ | `public void removeHotspotdiapo(int num) {` |
| `getImgPanoramique` | 283 | ✅ | `public Image getImgPanoramique() {` |
| `setImgPanoramique` | 290 | ✅ | `public void setImgPanoramique(Image imgPanoramique) {` |
| `getNombreHotspots` | 297 | ✅ | `public int getNombreHotspots() {` |
| `setNombreHotspots` | 304 | ✅ | `public void setNombreHotspots(int iNombreHotspots) {` |
| `getStrTypePanoramique` | 311 | ✅ | `public String getStrTypePanoramique() {` |
| `setStrTypePanoramique` | 318 | ✅ | `public void setStrTypePanoramique(String strTypePanoramique) {` |
| `isAfficheTitre` | 325 | ✅ | `public boolean isAfficheTitre() {` |
| `setAfficheTitre` | 332 | ✅ | `public void setAfficheTitre(boolean bAfficheTitre) {` |
| `isAffDescription` | 339 | ✅ | `public boolean isAffDescription() {` |
| `setAffDescription` | 346 | ✅ | `public void setAffDescription(boolean bAffDescription) {` |
| `isAfficheInfo` | 353 | ✅ | `public boolean isAfficheInfo() {` |
| `setAfficheInfo` | 360 | ✅ | `public void setAfficheInfo(boolean bAfficheInfo) {` |
| `getStrTitrePanoramique` | 367 | ✅ | `public String getStrTitrePanoramique() {` |
| `setStrTitrePanoramique` | 374 | ✅ | `public void setStrTitrePanoramique(String strTitrePanoramique) {` |
| `getImgVignettePanoramique` | 381 | ✅ | `public Image getImgVignettePanoramique() {` |
| `setImgVignettePanoramique` | 388 | ✅ | `public void setImgVignettePanoramique(Image imgVignettePanoramique) {` |
| `getZeroNord` | 395 | ✅ | `public double getZeroNord() {` |
| `setZeroNord` | 402 | ✅ | `public void setZeroNord(double zeroNord) {` |
| `getNombreNiveaux` | 409 | ✅ | `public double getNombreNiveaux() {` |
| `setNombreNiveaux` | 416 | ✅ | `public void setNombreNiveaux(double nombreNiveaux) {` |
| `getNombreHotspotImage` | 423 | ✅ | `public int getNombreHotspotImage() {` |
| `setNombreHotspotImage` | 430 | ✅ | `public void setNombreHotspotImage(int iNombreHotspotImage) {` |
| `getNombreHotspotHTML` | 437 | ✅ | `public int getNombreHotspotHTML() {` |
| `setNombreHotspotHTML` | 444 | ✅ | `public void setNombreHotspotHTML(int iNombreHotspotHTML) {` |
| `getNumeroPlan` | 451 | ✅ | `public int getNumeroPlan() {` |
| `setNumeroPlan` | 458 | ✅ | `public void setNumeroPlan(int iNumeroPlan) {` |
| `getImgVisuPanoramique` | 465 | ✅ | `public Image getImgVisuPanoramique() {` |
| `setImgVisuPanoramique` | 472 | ✅ | `public void setImgVisuPanoramique(Image imgVisuPanoramique) {` |
| `getMarqueurGeolocatisation` | 479 | ✅ | `public CoordonneesGeographiques getMarqueurGeolocatisation() {` |
| `setMarqueurGeolocatisation` | 486 | ✅ | `public void setMarqueurGeolocatisation(CoordonneesGeographiques marqueurGeolo...` |
| `getChampVisuel` | 493 | ✅ | `public double getChampVisuel() {` |
| `setChampVisuel` | 500 | ✅ | `public void setChampVisuel(double champVisuel) {` |
| `getImgPanoRect` | 507 | ✅ | `public Image getImgPanoRect() {` |
| `setImgPanoRect` | 514 | ✅ | `public void setImgPanoRect(Image imgPanoRect) {` |
| `getMinLat` | 521 | ✅ | `public double getMinLat() {` |
| `setMinLat` | 528 | ✅ | `public void setMinLat(double minLat) {` |
| `getMaxLat` | 535 | ✅ | `public double getMaxLat() {` |
| `setMaxLat` | 542 | ✅ | `public void setMaxLat(double maxLat) {` |
| `isbMinLat` | 549 | ✅ | `public boolean isbMinLat() {` |
| `setbMinLat` | 556 | ✅ | `public void setbMinLat(boolean bMinLat) {` |
| `isbMaxLat` | 563 | ✅ | `public boolean isbMaxLat() {` |
| `setbMaxLat` | 570 | ✅ | `public void setbMaxLat(boolean bMaxLat) {` |
| `getiNombreHotspotDiapo` | 577 | ✅ | `public int getiNombreHotspotDiapo() {` |
| `setiNombreHotspotDiapo` | 584 | ✅ | `public void setiNombreHotspotDiapo(int iNombreHotspotDiapo) {` |
| `getImgPanoRectListe` | 591 | ✅ | `public Image getImgPanoRectListe() {` |
| `setImgPanoRectListe` | 598 | ✅ | `public void setImgPanoRectListe(Image imgPanoRectListe) {` |
| `getImgCubeEqui` | 605 | ✅ | `public Image getImgCubeEqui() {` |
| `setImgCubeEqui` | 612 | ✅ | `public void setImgCubeEqui(Image imgCubeEqui) {` |
| `getFovMax` | 619 | ✅ | `public double getFovMax() {` |
| `setFovMax` | 626 | ✅ | `public void setFovMax(double fovMax) {` |
| `getFovMin` | 633 | ✅ | `public double getFovMin() {` |
| `setFovMin` | 640 | ✅ | `public void setFovMin(double fovMin) {` |
| `getStrDescriptionIA` | 647 | ✅ | `public String getStrDescriptionIA() {` |
| `setStrDescriptionIA` | 654 | ✅ | `public void setStrDescriptionIA(String strDescriptionIA) {` |

---

### 🔶 `PanoramiqueCellule`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\PanoramiqueCellule.java`

**Documentation de la classe :** ❌ Non

**Progression :** 8/16 éléments documentés (50.0%)

#### Propriétés (8)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `imgPanoramique` | 13 | ❌ | `private Image imgPanoramique;` |
| `strTitrePanoramique` | 14 | ❌ | `private String strTitrePanoramique;` |
| `strTitrePanoramiqueLigne2` | 15 | ❌ | `private String strTitrePanoramiqueLigne2;` |
| `iNumPano` | 16 | ❌ | `private int iNumPano;` |
| `imgPanoramique` | 22 | ❌ | `return imgPanoramique;` |
| `strTitrePanoramique` | 36 | ❌ | `return strTitrePanoramique;` |
| `strTitrePanoramiqueLigne2` | 50 | ❌ | `return strTitrePanoramiqueLigne2;` |
| `iNumPano` | 64 | ❌ | `return iNumPano;` |

#### Méthodes (8)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getImgPanoramique` | 21 | ✅ | `public Image getImgPanoramique() {` |
| `setImgPanoramique` | 28 | ✅ | `public void setImgPanoramique(Image imgPanoramique) {` |
| `getStrTitrePanoramique` | 35 | ✅ | `public String getStrTitrePanoramique() {` |
| `setStrTitrePanoramique` | 42 | ✅ | `public void setStrTitrePanoramique(String strTitre1) {` |
| `getStrTitrePanoramiqueLigne2` | 49 | ✅ | `public String getStrTitrePanoramiqueLigne2() {` |
| `setStrTitrePanoramiqueLigne2` | 56 | ✅ | `public void setStrTitrePanoramiqueLigne2(String strTitrePanoramiqueLigne2) {` |
| `getiNumPano` | 63 | ✅ | `public int getiNumPano() {` |
| `setiNumPano` | 70 | ✅ | `public void setiNumPano(int iNumPano) {` |

---

### 🔶 `Plan`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\Plan.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 29/56 éléments documentés (51.8%)

#### Propriétés (27)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `hotspots` | 14 | ❌ | `private final HotSpot[] hotspots = new HotSpot[100];` |
| `strTitrePlan` | 15 | ❌ | `private String strTitrePlan = "";` |
| `strImagePlan` | 16 | ❌ | `private String strImagePlan = "";` |
| `strLienPlan` | 17 | ❌ | `private String strLienPlan = "";` |
| `iNombreHotspots` | 18 | ❌ | `private int iNombreHotspots = 0;` |
| `strPanoramiques` | 19 | ❌ | `private final String[] strPanoramiques = new String[50];` |
| `iNombrePanoramiques` | 20 | ❌ | `private int iNombrePanoramiques = 0;` |
| `directionNord` | 21 | ❌ | `private double directionNord = 0;` |
| `strPosition` | 22 | ❌ | `private String strPosition = "top:right";` |
| `positionX` | 23 | ❌ | `private double positionX = 20;` |
| `positionY` | 24 | ❌ | `private double positionY = 20;` |
| `largeurPlan` | 25 | ❌ | `private double largeurPlan;` |
| `hauteurPlan` | 26 | ❌ | `private double hauteurPlan;` |
| `strTitrePlan` | 69 | ❌ | `return strTitrePlan;` |
| `iNombreHotspots` | 84 | ❌ | `return iNombreHotspots;` |
| `strPanoramiques` | 98 | ❌ | `return strPanoramiques;` |
| `trouve` | 113 | ❌ | `boolean trouve = false;` |
| `numero` | 114 | ❌ | `int numero = -1;` |
| `iNombrePanoramiques` | 134 | ❌ | `return iNombrePanoramiques;` |
| `directionNord` | 148 | ❌ | `return directionNord;` |
| `strPosition` | 162 | ❌ | `return strPosition;` |
| `positionX` | 176 | ❌ | `return positionX;` |
| `positionY` | 190 | ❌ | `return positionY;` |
| `strImagePlan` | 204 | ❌ | `return strImagePlan;` |
| `strLienPlan` | 218 | ❌ | `return strLienPlan;` |
| `largeurPlan` | 232 | ❌ | `return largeurPlan;` |
| `hauteurPlan` | 246 | ❌ | `return hauteurPlan;` |

#### Méthodes (29)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getHotspot` | 32 | ✅ | `public HotSpot getHotspot(int i) {` |
| `setHotspot` | 40 | ✅ | `public void setHotspot(HotSpot hotspot, int i) {` |
| `addHotspot` | 48 | ✅ | `public void addHotspot(HotSpot hotspot) {` |
| `removeHotspot` | 57 | ✅ | `public void removeHotspot(int iNum) {` |
| `getTitrePlan` | 68 | ✅ | `public String getTitrePlan() {` |
| `setTitrePlan` | 76 | ✅ | `public void setTitrePlan(String strTitrePlan) {` |
| `getNombreHotspots` | 83 | ✅ | `public int getNombreHotspots() {` |
| `setNombreHotspots` | 90 | ✅ | `public void setNombreHotspots(int iNombreHotspots) {` |
| `getStrPanoramiques` | 97 | ✅ | `public String[] getStrPanoramiques() {` |
| `addStrPanoramique` | 104 | ✅ | `public void addStrPanoramique(String strPanoramique) {` |
| `removeStrPanoramique` | 112 | ✅ | `public void removeStrPanoramique(String strPanoramique) {` |
| `getNombrePanoramiques` | 133 | ✅ | `public int getNombrePanoramiques() {` |
| `setNombrePanoramiques` | 140 | ✅ | `public void setNombrePanoramiques(int iNombrePanoramiques) {` |
| `getDirectionNord` | 147 | ✅ | `public double getDirectionNord() {` |
| `setDirectionNord` | 154 | ✅ | `public void setDirectionNord(double directionNord) {` |
| `getStrPosition` | 161 | ✅ | `public String getStrPosition() {` |
| `setStrPosition` | 168 | ✅ | `public void setStrPosition(String strPosition) {` |
| `getPositionX` | 175 | ✅ | `public double getPositionX() {` |
| `setPositionX` | 182 | ✅ | `public void setPositionX(double positionX) {` |
| `getPositionY` | 189 | ✅ | `public double getPositionY() {` |
| `setPositionY` | 196 | ✅ | `public void setPositionY(double positionY) {` |
| `getStrImagePlan` | 203 | ✅ | `public String getStrImagePlan() {` |
| `setStrImagePlan` | 210 | ✅ | `public void setStrImagePlan(String strImagePlan) {` |
| `getStrLienPlan` | 217 | ✅ | `public String getStrLienPlan() {` |
| `setStrLienPlan` | 224 | ✅ | `public void setStrLienPlan(String strLienPlan) {` |
| `getLargeurPlan` | 231 | ✅ | `public double getLargeurPlan() {` |
| `setLargeurPlan` | 238 | ✅ | `public void setLargeurPlan(double largeurPlan) {` |
| `getHauteurPlan` | 245 | ✅ | `public double getHauteurPlan() {` |
| `setHauteurPlan` | 252 | ✅ | `public void setHauteurPlan(double hauteurPlan) {` |

---

### ⚠️ `PopUpDialogController`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\PopUpDialogController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 3/8 éléments documentés (37.5%)

#### Propriétés (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `stPopUp` | 35 | ✅ | `public static Stage stPopUp;` |
| `btnQuitte` | 38 | ❌ | `Button btnQuitte;` |
| `hlLeMondea360` | 40 | ❌ | `Hyperlink hlLeMondea360;` |

#### Méthodes (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `handleQuitteAction` | 46 | ❌ | `public void handleQuitteAction() {` |
| `handleOuvreLien` | 55 | ❌ | `public void handleOuvreLien() throws URISyntaxException {` |
| `start` | 58 | ❌ | `public void start(Stage stage) {` |
| `popUpHandler` | 67 | ✅ | `public void popUpHandler() {` |
| `affichePopup` | 74 | ✅ | `public void affichePopup() throws Exception {` |

---

### ❌ `ReadWriteImage`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ReadWriteImage.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 1/6 éléments documentés (16.7%)

#### Propriétés (5)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `sharpenMatrix` | 46 | ❌ | `private static float[] sharpenMatrix = new float[9];` |
| `normalMatrix` | 54 | ❌ | `float[] normalMatrix = {` |
| `edgeMatrix` | 59 | ❌ | `float[] edgeMatrix = {` |
| `sharpMatrix` | 64 | ❌ | `float[] sharpMatrix = new float[9];` |
| `sharpMatrix` | 68 | ❌ | `return sharpMatrix;` |

#### Méthodes (1)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `calculeSharpenMatrix` | 53 | ✅ | `private static float[] calculeSharpenMatrix(float level) {` |

---

### ⚠️ `SvgIconLoader`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\util\SvgIconLoader.java`

**Documentation de la classe :** ✅ Oui (13 lignes)

**Progression :** 11/25 éléments documentés (44.0%)

#### Propriétés (4)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `SVG_PATH` | 33 | ❌ | `private static final String SVG_PATH = "images/svg/";` |
| `baseAppPath` | 34 | ❌ | `private static String baseAppPath = "";` |
| `svgPath` | 131 | ❌ | `String svgPath = baseAppPath + File.separator + SVG_PATH + iconName + ".svg";` |
| `pngPath` | 239 | ❌ | `String pngPath = "file:" + baseAppPath + File.separator + "images" + File.sep...` |

#### Méthodes (21)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `setBaseAppPath` | 43 | ✅ | `public static void setBaseAppPath(String path) {` |
| `clearCache` | 50 | ✅ | `public static void clearCache() {` |
| `getThemeColor` | 60 | ✅ | `private static Color getThemeColor() {` |
| `loadSvgIcon` | 116 | ✅ | `public static ImageView loadSvgIcon(String iconName, int width, int height, C...` |
| `ImageView` | 127 | ❌ | `return new ImageView(iconCache.get(cacheKey));` |
| `loadFallbackPng` | 136 | ❌ | `return loadFallbackPng(iconName, width);` |
| `ImageView` | 161 | ❌ | `return new ImageView(fxImage);` |
| `loadFallbackPng` | 166 | ❌ | `return loadFallbackPng(iconName, width);` |
| `loadSvgIcon` | 177 | ✅ | `public static ImageView loadSvgIcon(String iconName, int size, Color color) {` |
| `loadSvgIcon` | 178 | ❌ | `return loadSvgIcon(iconName, size, size, color);` |
| `convertSvgToPng` | 188 | ✅ | `private static BufferedImage convertSvgToPng(String svgContent, int width, in...` |
| `convertSvgToPng` | 218 | ✅ | `private static BufferedImage convertSvgToPng(String svgContent, int size) thr...` |
| `convertSvgToPng` | 219 | ❌ | `return convertSvgToPng(svgContent, size, size);` |
| `loadSvgIcon` | 228 | ✅ | `public static ImageView loadSvgIcon(String iconName, int size) {` |
| `loadSvgIcon` | 229 | ❌ | `return loadSvgIcon(iconName, size, null);` |
| `loadFallbackPng` | 235 | ✅ | `private static ImageView loadFallbackPng(String iconName, int size) {` |
| `ImageView` | 241 | ❌ | `return new ImageView(image);` |
| `ImageView` | 245 | ❌ | `return new ImageView();` |
| `mapSvgToPng` | 252 | ✅ | `private static String mapSvgToPng(String svgName) {` |
| `loadThemedIcon` | 287 | ✅ | `public static ImageView loadThemedIcon(String iconName, int size, boolean isD...` |
| `loadSvgIcon` | 289 | ❌ | `return loadSvgIcon(iconName, size, iconColor);` |

---

### ❌ `SvgIconLoaderTest`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\test\SvgIconLoaderTest.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 0/4 éléments documentés (0.0%)

#### Propriétés (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `ICON_NAMES` | 20 | ❌ | `private static final String[] ICON_NAMES = {` |
| `colors` | 59 | ❌ | `Color[] colors = {` |

#### Méthodes (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `start` | 46 | ❌ | `public void start(Stage primaryStage) {` |
| `main` | 103 | ❌ | `public static void main(String[] args) {` |

---

### ❌ `TestAIClients`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\TestAIClients.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 2/15 éléments documentés (13.3%)

#### Propriétés (12)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `prompt` | 47 | ❌ | `String prompt = "Panorama 360 degree virtual tour of";` |
| `prompt` | 60 | ❌ | `String prompt = "A beautiful landscape with";` |
| `null` | 69 | ❌ | `return null;` |
| `message` | 109 | ❌ | `String message = "In one sentence, what is a 360 degree panorama?";` |
| `systemMsg` | 122 | ❌ | `String systemMsg = "Tu es un expert en photographie panoramique et visites vi...` |
| `userMsg` | 123 | ❌ | `String userMsg = "Donne 3 conseils pour créer un bon panorama 360°";` |
| `titre` | 137 | ❌ | `String titre = "Château de Brézé";` |
| `tags` | 138 | ❌ | `String tags = "château, architecture, visite virtuelle";` |
| `titre` | 152 | ❌ | `String titre = "Cathédrale Notre-Dame";` |
| `description` | 153 | ❌ | `String description = "Vue intérieure de la cathédrale gothique avec ses vitra...` |
| `message` | 167 | ❌ | `String message = "What is the best time to take 360 photos?";` |
| `null` | 176 | ❌ | `return null;` |

#### Méthodes (3)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `main` | 11 | ❌ | `public static void main(String[] args) {` |
| `testHuggingFace` | 28 | ✅ | `private static void testHuggingFace() {` |
| `testOpenRouter` | 83 | ✅ | `private static void testOpenRouter() {` |

---

### ❌ `TestThemeDetection`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\test\TestThemeDetection.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 0/11 éléments documentés (0.0%)

#### Propriétés (4)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `iconContainer` | 22 | ❌ | `private VBox iconContainer;` |
| `themeLabel` | 23 | ❌ | `private Label themeLabel;` |
| `scene` | 24 | ❌ | `private Scene scene;` |
| `iconNames` | 98 | ❌ | `String[] iconNames = {` |

#### Méthodes (7)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `start` | 27 | ❌ | `public void start(Stage primaryStage) {` |
| `Separator` | 77 | ❌ | `new Separator(),` |
| `Separator` | 80 | ❌ | `new Separator(),` |
| `Label` | 81 | ❌ | `new Label("Icônes SVG avec détection automatique du thème :"),` |
| `loadIcons` | 95 | ❌ | `private void loadIcons() {` |
| `updateTheme` | 113 | ❌ | `private void updateTheme() {` |
| `main` | 119 | ❌ | `public static void main(String[] args) {` |

---

### ❌ `TextUtils`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\TextUtils.java`

**Documentation de la classe :** ❌ Non

**Progression :** 0/7 éléments documentés (0.0%)

#### Propriétés (6)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `helper` | 18 | ❌ | `static final Text helper;` |
| `DEFAULT_WRAPPING_WIDTH` | 19 | ❌ | `static final double DEFAULT_WRAPPING_WIDTH;` |
| `DEFAULT_LINE_SPACING` | 20 | ❌ | `static final double DEFAULT_LINE_SPACING;` |
| `DEFAULT_TEXT` | 21 | ❌ | `static final String DEFAULT_TEXT;` |
| `DEFAULT_BOUNDS_TYPE` | 22 | ❌ | `static final TextBoundsType DEFAULT_BOUNDS_TYPE;` |
| `d` | 43 | ❌ | `return d;` |

#### Méthodes (1)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `computeTextWidth` | 32 | ❌ | `public static double computeTextWidth(Font font, String text, double help0) {` |

---

### ⚠️ `ThemeManager`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ThemeManager.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** ThemeProvider, Theme

**Progression :** 15/39 éléments documentés (38.5%)

#### Propriétés (17)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `displayName` | 68 | ❌ | `private final String displayName;` |
| `provider` | 69 | ❌ | `private final ThemeProvider provider;` |
| `atlantaThemeClassName` | 70 | ❌ | `private final String atlantaThemeClassName; // Nom de la classe au lieu d'ins...` |
| `isDark` | 71 | ❌ | `private final boolean isDark;` |
| `displayName` | 83 | ❌ | `return displayName;` |
| `provider` | 87 | ❌ | `return provider;` |
| `atlantaTheme` | 109 | ❌ | `return atlantaTheme;` |
| `isDark` | 113 | ❌ | `return isDark;` |
| `provider` | 117 | ❌ | `return provider == ThemeProvider.CUSTOM;` |
| `provider` | 121 | ❌ | `return provider == ThemeProvider.ATLANTAFX;` |
| `provider` | 125 | ❌ | `return provider == ThemeProvider.MATERIALFX;` |
| `provider` | 129 | ❌ | `return provider == ThemeProvider.FLATLAF;` |
| `PREF_THEME_KEY` | 133 | ❌ | `private static final String PREF_THEME_KEY = "selected_theme";` |
| `currentTheme` | 135 | ❌ | `private static Theme currentTheme = Theme.PRIMER_LIGHT;` |
| `variant` | 275 | ❌ | `String variant = theme.atlantaThemeClassName; // On réutilise ce champ pour s...` |
| `currentTheme` | 320 | ❌ | `return currentTheme;` |
| `currentTheme` | 334 | ❌ | `return currentTheme;` |

#### Méthodes (22)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getDisplayName` | 82 | ❌ | `public String getDisplayName() {` |
| `getProvider` | 86 | ❌ | `public ThemeProvider getProvider() {` |
| `isDark` | 112 | ❌ | `public boolean isDark() {` |
| `isCustom` | 116 | ❌ | `public boolean isCustom() {` |
| `isAtlantaFX` | 120 | ❌ | `public boolean isAtlantaFX() {` |
| `isMaterialFX` | 124 | ❌ | `public boolean isMaterialFX() {` |
| `isFlatLaf` | 128 | ❌ | `public boolean isFlatLaf() {` |
| `applyTheme` | 143 | ✅ | `public static void applyTheme(Scene scene, Theme theme) {` |
| `cleanRootClasses` | 199 | ✅ | `private static void cleanRootClasses(Scene scene) {` |
| `applyAtlantaFXTheme` | 211 | ✅ | `private static void applyAtlantaFXTheme(Scene scene, Theme theme) {` |
| `applyCustomTheme` | 224 | ✅ | `private static void applyCustomTheme(Scene scene, Theme theme) {` |
| `applyMaterialFXTheme` | 240 | ✅ | `private static void applyMaterialFXTheme(Scene scene, Theme theme) {` |
| `applyFlatLafTheme` | 266 | ✅ | `private static void applyFlatLafTheme(Scene scene, Theme theme) {` |
| `addCustomStyles` | 294 | ✅ | `private static void addCustomStyles(Scene scene, Theme theme) {` |
| `loadSavedTheme` | 312 | ✅ | `public static Theme loadSavedTheme() {` |
| `saveThemePreference` | 326 | ✅ | `private static void saveThemePreference(Theme theme) {` |
| `getCurrentTheme` | 333 | ✅ | `public static Theme getCurrentTheme() {` |
| `applySavedTheme` | 340 | ✅ | `public static void applySavedTheme(Scene scene) {` |
| `toggleDarkMode` | 349 | ✅ | `public static void toggleDarkMode(Scene scene) {` |
| `getAllThemes` | 360 | ✅ | `public static Theme[] getAllThemes() {` |
| `getLightThemes` | 367 | ✅ | `public static Theme[] getLightThemes() {` |
| `getDarkThemes` | 376 | ✅ | `public static Theme[] getDarkThemes() {` |

---

### ❌ `TransformationsPanoramique`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\TransformationsPanoramique.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 3/67 éléments documentés (4.5%)

#### Propriétés (65)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `RAPPORTCUBEEQUI` | 27 | ✅ | `public static final double RAPPORTCUBEEQUI = 0.3183;` |
| `cube` | 37 | ❌ | `WritableImage[] cube = new WritableImage[6];` |
| `tailleCube` | 40 | ❌ | `int tailleCube;` |
| `theta` | 55 | ❌ | `double theta;` |
| `phi` | 56 | ❌ | `double phi;` |
| `deuxPI` | 58 | ❌ | `double deuxPI = 2 * Math.PI;` |
| `red` | 59 | ❌ | `double red;` |
| `green` | 60 | ❌ | `double green;` |
| `blue` | 61 | ❌ | `double blue;` |
| `pixelX` | 62 | ❌ | `double pixelX;` |
| `pixelY` | 63 | ❌ | `double pixelY;` |
| `Z` | 72 | ❌ | `double Z = 1;` |
| `coeff` | 84 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 87 | ❌ | `int pixX = i;` |
| `pixY` | 88 | ❌ | `int pixY = j;` |
| `Z` | 130 | ❌ | `double Z = -1;` |
| `coeff` | 144 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 147 | ❌ | `int pixX = i;` |
| `pixY` | 148 | ❌ | `int pixY = j;` |
| `X` | 187 | ❌ | `double X = 1;` |
| `coeff` | 201 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 204 | ❌ | `int pixX = i;` |
| `pixY` | 205 | ❌ | `int pixY = j;` |
| `X` | 245 | ❌ | `double X = 1;` |
| `coeff` | 259 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 262 | ❌ | `int pixX = i;` |
| `pixY` | 263 | ❌ | `int pixY = j;` |
| `Y` | 303 | ❌ | `double Y = 1;` |
| `coeff` | 320 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 323 | ❌ | `int pixX = i;` |
| `pixY` | 324 | ❌ | `int pixY = j;` |
| `Y` | 364 | ❌ | `double Y = -1;` |
| `coeff` | 381 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 384 | ❌ | `int pixX = i;` |
| `pixY` | 385 | ❌ | `int pixY = j;` |
| `cube` | 420 | ❌ | `return cube;` |
| `theta` | 442 | ❌ | `double theta;` |
| `phi` | 443 | ❌ | `double phi;` |
| `tailleEqui` | 444 | ❌ | `int tailleEqui;` |
| `rapport` | 457 | ❌ | `double rapport = 2.0d * Math.PI / tailleEqui;` |
| `red` | 458 | ❌ | `double red;` |
| `green` | 459 | ❌ | `double green;` |
| `blue` | 460 | ❌ | `double blue;` |
| `XX` | 462 | ❌ | `final int XX=X;` |
| `pixelX` | 473 | ❌ | `double pixelX;` |
| `pixelY` | 474 | ❌ | `double pixelY;` |
| `coeff` | 490 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 493 | ❌ | `int pixX = i;` |
| `pixY` | 494 | ❌ | `int pixY = j;` |
| `coeff` | 536 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 539 | ❌ | `int pixX = i;` |
| `pixY` | 540 | ❌ | `int pixY = j;` |
| `coeff` | 584 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 587 | ❌ | `int pixX = i;` |
| `pixY` | 588 | ❌ | `int pixY = j;` |
| `coeff` | 630 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 633 | ❌ | `int pixX = i;` |
| `pixY` | 634 | ❌ | `int pixY = j;` |
| `coeff` | 678 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 681 | ❌ | `int pixX = i;` |
| `pixY` | 682 | ❌ | `int pixY = j;` |
| `coeff` | 724 | ❌ | `double coeff = 0.0d;` |
| `pixX` | 727 | ❌ | `int pixX = i;` |
| `pixY` | 728 | ❌ | `int pixY = j;` |
| `equi` | 761 | ❌ | `return equi;` |

#### Méthodes (2)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `equi2cube` | 35 | ✅ | `public static Image[] equi2cube(Image equi, int taille) throws InterruptedExc...` |
| `cube2rect` | 434 | ✅ | `public static Image cube2rect(Image front, Image left, Image right, Image beh...` |

---

### ⚠️ `VisualiseurImagesPanoramiques`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\VisualiseurImagesPanoramiques.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 17/78 éléments documentés (21.8%)

#### Propriétés (47)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `fov` | 70 | ❌ | `latitude, longitude, fov = 70, positNord = 32,` |
| `rapportDegToRad` | 75 | ❌ | `private final double rapportDegToRad = Math.PI / 180.d;` |
| `camera1` | 76 | ❌ | `private PerspectiveCamera camera1;` |
| `sscPanorama` | 77 | ❌ | `private SubScene sscPanorama;` |
| `lblNordOuest` | 78 | ❌ | `private Label lblNord, lblSud, lblEst, lblOuest, lblNordEst, lblSudOuest, lbl...` |
| `apPanorama` | 79 | ❌ | `private AnchorPane apNord, apPanorama;` |
| `bMouvement` | 80 | ❌ | `private boolean bMouvement = false;` |
| `nomFichierPanoramique` | 84 | ❌ | `private String nomFichierPanoramique = "";` |
| `btnChoixVue` | 85 | ❌ | `private Button btnChoixNord, btnChoixVue;` |
| `iHauteur` | 106 | ❌ | `int iHauteur = iLargeur / 2 / iRapport;` |
| `imgMercator` | 122 | ❌ | `return imgMercator;` |
| `perspectiveCamera` | 132 | ❌ | `return perspectiveCamera;` |
| `positionNord` | 169 | ❌ | `double positionNord;` |
| `positionSud` | 174 | ❌ | `double positionSud;` |
| `positionEst` | 179 | ❌ | `double positionEst;` |
| `positionOuest` | 184 | ❌ | `double positionOuest;` |
| `positionNordEst` | 189 | ❌ | `double positionNordEst;` |
| `positionNordOuest` | 194 | ❌ | `double positionNordOuest;` |
| `positionSudEst` | 199 | ❌ | `double positionSudEst;` |
| `positionSudOuest` | 204 | ❌ | `double positionSudOuest;` |
| `iNombreTrait` | 210 | ❌ | `int iNombreTrait = 360;` |
| `positionTrait` | 211 | ❌ | `double positionTrait;` |
| `couleur` | 215 | ❌ | `Color couleur=Color.WHITE;` |
| `hautTrait` | 216 | ❌ | `double hautTrait = 15;` |
| `apPanorama` | 461 | ❌ | `return apPanorama;` |
| `latitude` | 483 | ❌ | `return latitude;` |
| `ancienneValeur` | 490 | ❌ | `double ancienneValeur = this.latitude;` |
| `nouvelleValeur` | 492 | ❌ | `double nouvelleValeur = this.latitude;` |
| `longitude` | 500 | ❌ | `return longitude;` |
| `ancienneValeur` | 507 | ❌ | `double ancienneValeur = this.longitude;` |
| `nouvelleValeur` | 509 | ❌ | `double nouvelleValeur = this.longitude;` |
| `fov` | 517 | ❌ | `return fov;` |
| `ancienneValeur` | 524 | ❌ | `double ancienneValeur = this.fov;` |
| `nouvelleValeur` | 526 | ❌ | `double nouvelleValeur = this.fov;` |
| `positNord` | 534 | ❌ | `return positNord;` |
| `ancienneValeur` | 541 | ❌ | `double ancienneValeur = this.positNord;` |
| `nouvelleValeur` | 543 | ❌ | `double nouvelleValeur = this.positNord;` |
| `nomFichierPanoramique` | 551 | ❌ | `return nomFichierPanoramique;` |
| `choixLongitude` | 565 | ❌ | `return choixLongitude;` |
| `ancienneValeur` | 572 | ❌ | `double ancienneValeur = this.choixLongitude;` |
| `nouvelleValeur` | 574 | ❌ | `double nouvelleValeur = this.choixLongitude;` |
| `choixLatitude` | 582 | ❌ | `return choixLatitude;` |
| `ancienneValeur` | 589 | ❌ | `double ancienneValeur = this.choixLatitude;` |
| `nouvelleValeur` | 591 | ❌ | `double nouvelleValeur = this.choixLatitude;` |
| `choixFov` | 599 | ❌ | `return choixFov;` |
| `ancienneValeur` | 606 | ❌ | `double ancienneValeur = this.choixFov;` |
| `nouvelleValeur` | 608 | ❌ | `double nouvelleValeur = this.choixFov;` |

#### Méthodes (31)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `addPropertyChangeListener` | 52 | ❌ | `public void addPropertyChangeListener(String propertyName, PropertyChangeList...` |
| `removePropertyChangeListener` | 56 | ❌ | `public void removePropertyChangeListener(String propertyName, PropertyChangeL...` |
| `firePropertyChange` | 60 | ❌ | `public void firePropertyChange(String propertyName, BigDecimal oldValue, BigD...` |
| `VisualiseurImagesPanoramiques` | 92 | ❌ | `public VisualiseurImagesPanoramiques(String nomFichierPanoramique, double pos...` |
| `imgTransformationImage` | 100 | ❌ | `public static Image imgTransformationImage(Image imgRect) {` |
| `imgTransformationImage` | 101 | ❌ | `return imgTransformationImage(imgRect, 2);` |
| `imgTransformationImage` | 104 | ❌ | `public static Image imgTransformationImage(Image imgRect, int iRapport) {` |
| `addCamera` | 125 | ❌ | `private PerspectiveCamera addCamera(SubScene scene) {` |
| `degToRad` | 140 | ✅ | `private double degToRad(double angleDeg) {` |
| `affiche` | 144 | ❌ | `public void affiche() {` |
| `changeTaille` | 246 | ❌ | `public void changeTaille(double largeur, double hauteur) {` |
| `reaffiche` | 252 | ❌ | `private void reaffiche() {` |
| `affichePano` | 447 | ❌ | `public AnchorPane affichePano() {` |
| `setNomImagePanoramique` | 465 | ❌ | `public void setNomImagePanoramique(String strImagePanoramique, int iRapport) {` |
| `setImagePanoramique` | 473 | ❌ | `public void setImagePanoramique(String strImagePanoramique, Image imgPanorami...` |
| `getLatitude` | 482 | ✅ | `public double getLatitude() {` |
| `setLatitude` | 489 | ✅ | `public void setLatitude(double latitude) {` |
| `getLongitude` | 499 | ✅ | `public double getLongitude() {` |
| `setLongitude` | 506 | ✅ | `public void setLongitude(double longitude) {` |
| `getFov` | 516 | ✅ | `public double getFov() {` |
| `setFov` | 523 | ✅ | `public void setFov(double fov) {` |
| `getPositNord` | 533 | ✅ | `public double getPositNord() {` |
| `setPositNord` | 540 | ✅ | `public void setPositNord(double positNord) {` |
| `getNomFichierPanoramique` | 550 | ✅ | `public String getNomFichierPanoramique() {` |
| `setNomFichierPanoramique` | 557 | ✅ | `public void setNomFichierPanoramique(String nomFichierPanoramique) {` |
| `getChoixLongitude` | 564 | ✅ | `public double getChoixLongitude() {` |
| `setChoixLongitude` | 571 | ✅ | `public void setChoixLongitude(double choixLongitude) {` |
| `getChoixLatitude` | 581 | ✅ | `public double getChoixLatitude() {` |
| `setChoixLatitude` | 588 | ✅ | `public void setChoixLatitude(double choixLatitude) {` |
| `getChoixFov` | 598 | ✅ | `public double getChoixFov() {` |
| `setChoixFov` | 605 | ✅ | `public void setChoixFov(double choixFov) {` |

---

### ⚠️ `ZoneTelecommande`

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ZoneTelecommande.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Progression :** 8/23 éléments documentés (34.8%)

#### Propriétés (14)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `strIdZone` | 16 | ❌ | `private String strIdZone = "";` |
| `strTypeZone` | 17 | ❌ | `private String strTypeZone = "";` |
| `strCoordonneesZone` | 18 | ❌ | `private String strCoordonneesZone = "";` |
| `iNombrePoints` | 20 | ❌ | `private int iNombrePoints = 0;` |
| `strIdZone` | 26 | ❌ | `return strIdZone;` |
| `strTypeZone` | 41 | ❌ | `return strTypeZone;` |
| `strCoordonneesZone` | 55 | ❌ | `return strCoordonneesZone;` |
| `centre` | 69 | ❌ | `return centre;` |
| `pointsZone` | 81 | ❌ | `Point2D[] pointsZone = new Point2D[50];` |
| `strPoints` | 82 | ❌ | `String[] strPoints;` |
| `xMax` | 95 | ❌ | `double xMax = -5000;` |
| `yMax` | 96 | ❌ | `double yMax = -5000;` |
| `yMin` | 97 | ❌ | `double yMin = 5000;` |
| `xMin` | 98 | ❌ | `double xMin = 5000;` |

#### Méthodes (9)

| Nom | Ligne | Documentée | Signature |
|-----|-------|------------|------------|
| `getStrIdZone` | 25 | ✅ | `public String getStrIdZone() {` |
| `setStrIdZone` | 33 | ✅ | `public void setStrIdZone(String strIdZone) {` |
| `getStrTypeZone` | 40 | ✅ | `public String getStrTypeZone() {` |
| `setStrTypeZone` | 47 | ✅ | `public void setStrTypeZone(String strTypeZone) {` |
| `getStrCoordonneesZone` | 54 | ✅ | `public String getStrCoordonneesZone() {` |
| `setStrCoordonneesZone` | 61 | ✅ | `public void setStrCoordonneesZone(String strCoordonneesZone) {` |
| `getCentre` | 68 | ✅ | `public Point2D getCentre() {` |
| `setCentre` | 75 | ✅ | `public void setCentre(Point2D centre) {` |
| `calculeCentre` | 79 | ❌ | `public void calculeCentre() {` |

---

## 📖 Légende

| Icône | Signification |
|-------|---------------|
| ✅ | Élément documenté avec Javadoc |
| ❌ | Élément non documenté |
| 🔶 | Classe partiellement documentée (50-80%) |
| ⚠️ | Classe peu documentée (20-50%) |
| 🏆 | Classe bien documentée (>80%) |

---

*Rapport généré automatiquement par `analyse-javadoc.py` le 15/10/2025 à 14:50:59*
