# ⚠️ ConfigDialogController

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\ConfigDialogController.java`

**Documentation de la classe :** ✅ Oui (5 lignes)

**Progression :** 6/27 éléments documentés (22.2%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 2 |
| 🟡 Partielle | 3 |
| 🟠 Minimale | 1 |
| ⚫ Absente | 4 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 17 |

## Propriétés (17)

### ⚫ `stConfigDialog` - Ligne 46

**Qualité :** Absente

**Déclaration :**
```java
private static Stage stConfigDialog;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `apConfigDialog` - Ligne 47

**Qualité :** Absente

**Déclaration :**
```java
private static AnchorPane apConfigDialog;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `btnAnnuler` - Ligne 48

**Qualité :** Absente

**Déclaration :**
```java
private static Button btnAnnuler;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `btnSauvegarder` - Ligne 49

**Qualité :** Absente

**Déclaration :**
```java
private static Button btnSauvegarder;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `cbListeLangues` - Ligne 53

**Qualité :** Absente

**Déclaration :**
```java
private static ComboBox cbListeLangues;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `tfRepert` - Ligne 54

**Qualité :** Absente

**Déclaration :**
```java
private static TextField tfRepert;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `tfLocationIQKey` - Ligne 55

**Qualité :** Absente

**Déclaration :**
```java
private static TextField tfLocationIQKey;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `tfHuggingFaceKey` - Ligne 56

**Qualité :** Absente

**Déclaration :**
```java
private static TextField tfHuggingFaceKey;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `tfOpenRouterKey` - Ligne 57

**Qualité :** Absente

**Déclaration :**
```java
private static TextField tfOpenRouterKey;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `cbOpenRouterModel` - Ligne 58

**Qualité :** Absente

**Déclaration :**
```java
private static ComboBox<String> cbOpenRouterModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `cbOllamaModel` - Ligne 59

**Qualité :** Absente

**Déclaration :**
```java
private static ComboBox<String> cbOllamaModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `iCodeL` - Ligne 67

**Qualité :** Absente

**Déclaration :**
```java
int iCodeL = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `oswFichierConfig` - Ligne 267

**Qualité :** Absente

**Déclaration :**
```java
OutputStreamWriter oswFichierConfig = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `props` - Ligne 329

**Qualité :** Absente

**Déclaration :**
```java
return props;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 378

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelName` - Ligne 419

**Qualité :** Absente

**Déclaration :**
```java
return modelName;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `displayName` - Ligne 466

**Qualité :** Absente

**Déclaration :**
```java
return displayName;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (10)

### 🟡 `afficheFenetre()` - Ligne 65

**Qualité :** Partielle

**Signature :**
```java
public void afficheFenetre() throws IOException {
```

**Documentation actuelle :**
```java
/**
*
* @throws IOException Exception d'entrée sortie
*/
```

**Tags présents :** @throws

**⚠️ Tags manquants :** @param

---

### 🟡 `loadApiKeys()` - Ligne 313

**Qualité :** Partielle

**Signature :**
```java
private Properties loadApiKeys() {
```

**Documentation actuelle :**
```java
/**
* Charge les clés API depuis le fichier api-keys.properties
* @return Properties contenant les clés API
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `saveApiKeys()` - Ligne 338

**Qualité :** Partielle

**Signature :**
```java
private void saveApiKeys(String locationIQKey, String huggingFaceKey, String ...
```

**Documentation actuelle :**
```java
/**
* Sauvegarde les clés API dans le fichier api-keys.properties
* @param locationIQKey Clé LocationIQ
* @param huggingFaceKey Clé Hugging Face
* @param openRouterKey Clé OpenRouter
*/
```

**Tags présents :** @param

---

### 🟠 `saveModelsPreferences()` - Ligne 369

**Qualité :** Minimale

**Signature :**
```java
private void saveModelsPreferences() throws IOException {
```

**Documentation actuelle :**
```java
/**
* Sauvegarde les modèles IA sélectionnés dans preferences.cfg
*/
```

**⚠️ Tags manquants :** @param

---

### ⚫ `InputStreamReader()` - Ligne 377

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(new FileInputStream(filePreferences), "UTF-8"))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `FileOutputStream()` - Ligne 402

**Qualité :** Absente

**Signature :**
```java
new FileOutputStream(filePreferences), "UTF-8");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `ajouterEmojiModele()` - Ligne 417

**Qualité :** Complète

**Signature :**
```java
private static String ajouterEmojiModele(String modelName) {
```

**Documentation actuelle :**
```java
/**
* Ajoute un emoji visuel devant le nom du modèle pour l'interface
* @param modelName Nom du modèle (ex: "openai/gpt-5")
* @return Nom avec emoji (ex: "⭐ GPT-5")
*/
```

**Tags présents :** @param, @return

---

### ⚫ `if()` - Ligne 443

**Qualité :** Absente

**Signature :**
```java
else if (modelName.contains("deepseek-r1")) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `extraireNomModele()` - Ligne 464

**Qualité :** Complète

**Signature :**
```java
private static String extraireNomModele(String displayName) {
```

**Documentation actuelle :**
```java
/**
* Extrait le nom du modèle sans l'emoji et la description
* @param displayName Nom affiché avec emoji (ex: "⭐ GPT-5 (OpenAI)")
* @return Nom technique du modèle (ex: "openai/gpt-5")
*/
```

**Tags présents :** @param, @return

---

### ⚫ `if()` - Ligne 490

**Qualité :** Absente

**Signature :**
```java
else if (displayName.contains("DeepSeek-R1")) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

