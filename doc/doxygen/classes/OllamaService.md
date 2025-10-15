# ⚠️ OllamaService

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\OllamaService.java`

**Documentation de la classe :** ✅ Oui (6 lignes)

**Progression :** 29/115 éléments documentés (25.2%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 2 |
| 🟡 Partielle | 10 |
| 🟠 Minimale | 17 |
| ⚫ Absente | 20 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 66 |

## Propriétés (66)

### ⚫ `OLLAMA_URL` - Ligne 24

**Qualité :** Absente

**Déclaration :**
```java
private static final String OLLAMA_URL = "http://localhost:11434";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `GENERATE_ENDPOINT` - Ligne 25

**Qualité :** Absente

**Déclaration :**
```java
private static final String GENERATE_ENDPOINT = "/api/generate";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `TAGS_ENDPOINT` - Ligne 26

**Qualité :** Absente

**Déclaration :**
```java
private static final String TAGS_ENDPOINT = "/api/tags";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `ollamaModel` - Ligne 27

**Qualité :** Absente

**Déclaration :**
```java
private static String ollamaModel = "mistral"; // Sera détecté automatiquement
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `LOCATIONIQ_URL` - Ligne 30

**Qualité :** Absente

**Déclaration :**
```java
private static final String LOCATIONIQ_URL = "https://us1.locationiq.com/v1/r...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `LOCATIONIQ_TOKEN` - Ligne 31

**Qualité :** Absente

**Déclaration :**
```java
private static String LOCATIONIQ_TOKEN = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `OPENROUTER_URL` - Ligne 34

**Qualité :** Absente

**Déclaration :**
```java
private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/ch...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `OPENROUTER_TOKEN` - Ligne 35

**Qualité :** Absente

**Déclaration :**
```java
private static String OPENROUTER_TOKEN = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `OPENROUTER_MODELS` - Ligne 46

**Qualité :** Absente

**Déclaration :**
```java
private static final String[] OPENROUTER_MODELS = {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `openrouterModel` - Ligne 57

**Qualité :** Absente

**Déclaration :**
```java
private static String openrouterModel = OPENROUTER_MODELS[0]; // Claude Sonne...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `HUGGINGFACE_MODELS` - Ligne 61

**Qualité :** Absente

**Déclaration :**
```java
private static final String[] HUGGINGFACE_MODELS = {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `HUGGINGFACE_URL` - Ligne 68

**Qualité :** Absente

**Déclaration :**
```java
private static String HUGGINGFACE_URL = "https://api-inference.huggingface.co...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `currentModelIndex` - Ligne 69

**Qualité :** Absente

**Déclaration :**
```java
private static int currentModelIndex = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `HUGGINGFACE_TOKEN` - Ligne 72

**Qualité :** Absente

**Déclaration :**
```java
private static String HUGGINGFACE_TOKEN = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `TIMEOUT_MS` - Ligne 74

**Qualité :** Absente

**Déclaration :**
```java
private static final int TIMEOUT_MS = 5000;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `useHuggingFace` - Ligne 75

**Qualité :** Absente

**Déclaration :**
```java
private static boolean useHuggingFace = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `forceOllama` - Ligne 78

**Qualité :** Absente

**Déclaration :**
```java
private static boolean forceOllama = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `cheminFichier` - Ligne 91

**Qualité :** Absente

**Déclaration :**
```java
String cheminFichier = "api-keys.properties";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `true` - Ligne 177

**Qualité :** Absente

**Déclaration :**
```java
return true;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `false` - Ligne 196

**Qualité :** Absente

**Déclaration :**
```java
return false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `testRequest` - Ligne 214

**Qualité :** Absente

**Déclaration :**
```java
String testRequest = "{\"inputs\":\"test\"}";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `true` - Ligne 226

**Qualité :** Absente

**Déclaration :**
```java
return true;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `false` - Ligne 230

**Qualité :** Absente

**Déclaration :**
```java
return false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `true` - Ligne 234

**Qualité :** Absente

**Déclaration :**
```java
return true;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `true` - Ligne 239

**Qualité :** Absente

**Déclaration :**
```java
return true;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 252

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelesPreferences` - Ligne 263

**Qualité :** Absente

**Déclaration :**
```java
String[] modelesPreferences = {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bestModel` - Ligne 278

**Qualité :** Absente

**Déclaration :**
```java
String bestModel = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `bestPriority` - Ligne 279

**Qualité :** Absente

**Déclaration :**
```java
int bestPriority = Integer.MAX_VALUE;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `searchPos` - Ligne 282

**Qualité :** Absente

**Déclaration :**
```java
int searchPos = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modeles` - Ligne 344

**Qualité :** Absente

**Déclaration :**
```java
return modeles; // Liste vide
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 350

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `searchPos` - Ligne 359

**Qualité :** Absente

**Déclaration :**
```java
int searchPos = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modeles` - Ligne 373

**Qualité :** Absente

**Déclaration :**
```java
return modeles;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `OPENROUTER_MODELS` - Ligne 381

**Qualité :** Absente

**Déclaration :**
```java
return OPENROUTER_MODELS;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `ollamaModel` - Ligne 411

**Qualité :** Absente

**Déclaration :**
```java
return ollamaModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `openrouterModel` - Ligne 419

**Qualité :** Absente

**Déclaration :**
```java
return openrouterModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `forceOllama` - Ligne 436

**Qualité :** Absente

**Déclaration :**
```java
return forceOllama;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelShort` - Ligne 452

**Qualité :** Absente

**Déclaration :**
```java
String modelShort = openrouterModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 475

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `urlStr` - Ligne 479

**Qualité :** Absente

**Déclaration :**
```java
String urlStr = LOCATIONIQ_URL + "?key=" + LOCATIONIQ_TOKEN
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 493

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 500

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `lieuStructure` - Ligne 518

**Qualité :** Absente

**Déclaration :**
```java
return lieuStructure;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 522

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `null` - Ligne 526

**Qualité :** Absente

**Déclaration :**
```java
return null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `champsTouristiques` - Ligne 545

**Qualité :** Absente

**Déclaration :**
```java
String[] champsTouristiques = {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `localite` - Ligne 615

**Qualité :** Absente

**Déclaration :**
```java
String localite = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `result` - Ligne 663

**Qualité :** Absente

**Déclaration :**
```java
return result;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `searchStr` - Ligne 752

**Qualité :** Absente

**Déclaration :**
```java
String searchStr = "\"" + fieldName + "\":\"";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `lieuReel` - Ligne 802

**Qualité :** Absente

**Déclaration :**
```java
String lieuReel = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `description` - Ligne 825

**Qualité :** Absente

**Déclaration :**
```java
return description;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `langue` - Ligne 845

**Qualité :** Absente

**Déclaration :**
```java
String langue = "français";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `sujetPrincipal` - Ligne 876

**Qualité :** Absente

**Déclaration :**
```java
String sujetPrincipal = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `mainSubject` - Ligne 919

**Qualité :** Absente

**Déclaration :**
```java
String mainSubject = null;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `finalPrompt` - Ligne 1012

**Qualité :** Absente

**Déclaration :**
```java
return finalPrompt;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `resultat` - Ligne 1030

**Qualité :** Absente

**Déclaration :**
```java
return resultat;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `messageErreur` - Ligne 1047

**Qualité :** Absente

**Déclaration :**
```java
String messageErreur = "❌ Aucun service IA disponible !\n\n";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 1114

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 1125

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `errorMsg` - Ligne 1186

**Qualité :** Absente

**Déclaration :**
```java
String errorMsg = "Erreur HTTP " + responseCode + " pour " + ollamaModel;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 1196

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelName` - Ligne 1237

**Qualité :** Absente

**Déclaration :**
```java
String modelName = HUGGINGFACE_MODELS[currentModelIndex];
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `jsonRequest` - Ligne 1238

**Qualité :** Absente

**Déclaration :**
```java
String jsonRequest;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 1280

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 1291

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (49)

### 🟠 `chargerTokensAPI()` - Ligne 88

**Qualité :** Minimale

**Signature :**
```java
private static void chargerTokensAPI() {
```

**Documentation actuelle :**
```java
/**
* Charge les tokens API depuis le fichier api-keys.properties
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `verifierOpenRouterDisponible()` - Ligne 150

**Qualité :** Partielle

**Signature :**
```java
public static boolean verifierOpenRouterDisponible() {
```

**Documentation actuelle :**
```java
/**
* Vérifie si OpenRouter est disponible (token configuré)
* @return true si OpenRouter est disponible
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `isOllamaAvailable()` - Ligne 158

**Qualité :** Partielle

**Signature :**
```java
public static boolean isOllamaAvailable() {
```

**Documentation actuelle :**
```java
/**
* Vérifie si un service IA est disponible (Ollama ou Hugging Face)
* @return true si au moins un service est disponible
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟠 `detecterModeleOllama()` - Ligne 247

**Qualité :** Minimale

**Signature :**
```java
private static void detecterModeleOllama(HttpURLConnection conn) {
```

**Documentation actuelle :**
```java
/**
* Détecte automatiquement le meilleur modèle Ollama installé
* Priorise les modèles les plus fiables pour éviter les hallucinations
*/
```

**⚠️ Tags manquants :** @param

---

### ⚫ `InputStreamReader()` - Ligne 251

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 349

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟡 `getModelesOpenRouterDisponibles()` - Ligne 380

**Qualité :** Partielle

**Signature :**
```java
public static String[] getModelesOpenRouterDisponibles() {
```

**Documentation actuelle :**
```java
/**
* Retourne les modèles OpenRouter recommandés pour la génération de descriptions
* @return Tableau des modèles OpenRouter disponibles
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setOllamaModel()` - Ligne 388

**Qualité :** Partielle

**Signature :**
```java
public static void setOllamaModel(String modelName) {
```

**Documentation actuelle :**
```java
/**
* Définit le modèle Ollama à utiliser
* @param modelName Nom du modèle
*/
```

**Tags présents :** @param

---

### 🟡 `setOpenRouterModel()` - Ligne 399

**Qualité :** Partielle

**Signature :**
```java
public static void setOpenRouterModel(String modelName) {
```

**Documentation actuelle :**
```java
/**
* Définit le modèle OpenRouter à utiliser
* @param modelName Nom du modèle
*/
```

**Tags présents :** @param

---

### 🟡 `getOllamaModel()` - Ligne 410

**Qualité :** Partielle

**Signature :**
```java
public static String getOllamaModel() {
```

**Documentation actuelle :**
```java
/**
* Retourne le modèle Ollama actuellement configuré
* @return Nom du modèle Ollama
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getOpenRouterModel()` - Ligne 418

**Qualité :** Partielle

**Signature :**
```java
public static String getOpenRouterModel() {
```

**Documentation actuelle :**
```java
/**
* Retourne le modèle OpenRouter actuellement configuré
* @return Nom du modèle OpenRouter
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `setForceOllama()` - Ligne 426

**Qualité :** Partielle

**Signature :**
```java
public static void setForceOllama(boolean force) {
```

**Documentation actuelle :**
```java
/**
* Force l'utilisation d'Ollama (mode offline)
* @param force true pour forcer Ollama, false pour utiliser la priorité normale
*/
```

**Tags présents :** @param

---

### 🟡 `isForceOllama()` - Ligne 435

**Qualité :** Partielle

**Signature :**
```java
public static boolean isForceOllama() {
```

**Documentation actuelle :**
```java
/**
* Vérifie si le mode Ollama forcé est actif
* @return true si Ollama est forcé
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getServiceName()` - Ligne 443

**Qualité :** Partielle

**Signature :**
```java
public static String getServiceName() {
```

**Documentation actuelle :**
```java
/**
* Retourne le nom du service actuellement utilisé
* @return "OpenRouter (XXX)" ou "Ollama (local)" ou "Hugging Face (en ligne)"
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟢 `geocodeReverse()` - Ligne 469

**Qualité :** Complète

**Signature :**
```java
private static String geocodeReverse(String latitude, String longitude) {
```

**Documentation actuelle :**
```java
/**
* Géocodage inverse : convertit des coordonnées GPS en adresse/lieu réel
* Utilise l'API LocationIQ (5000 req/jour gratuit, OpenStreetMap)
* @param latitude Latitude
* @param longitude Longitude
* @return Adresse complète formatée, ou null si échec
*/
```

**Tags présents :** @param, @return

---

### ⚫ `InputStreamReader()` - Ligne 499

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `parserLocationIQ()` - Ligne 543

**Qualité :** Minimale

**Signature :**
```java
private static String parserLocationIQ(String json) {
```

**Documentation actuelle :**
```java
/**
* Parse la réponse JSON de LocationIQ et construit une description structurée
* pour que l'IA comprenne précisément chaque élément géographique
* Focus sur les informations touristiques/historiques, pas les détails de voirie
*
* Champs OpenStreetMap importants (https://wiki.openstreetmap.org/wiki/Map_Features):
* - tourism: viewpoint, attraction, museum, artwork, gallery, theme_park, zoo
* - historic: castle, ruins, monument, memorial, archaeological_site, battlefield, fort
* - amenity: place_of_worship, theatre, arts_centre, library, community_centre
* - natural: peak, cave_entrance, beach, cliff, waterfall
* - leisure: park, garden, nature_reserve, sports_centre
* - building: cathedral, church, chapel, temple, mosque, synagogue
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `tradTypeTourisme()` - Ligne 669

**Qualité :** Minimale

**Signature :**
```java
private static String tradTypeTourisme(String type) {
```

**Documentation actuelle :**
```java
/**
* Traduit les types de tourisme OpenStreetMap en français explicite
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `tradTypeHistorique()` - Ligne 686

**Qualité :** Minimale

**Signature :**
```java
private static String tradTypeHistorique(String type) {
```

**Documentation actuelle :**
```java
/**
* Traduit les types historiques OpenStreetMap en français explicite
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `tradTypeNaturel()` - Ligne 705

**Qualité :** Minimale

**Signature :**
```java
private static String tradTypeNaturel(String type) {
```

**Documentation actuelle :**
```java
/**
* Traduit les types naturels OpenStreetMap en français explicite
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `tradTypeLoisir()` - Ligne 722

**Qualité :** Minimale

**Signature :**
```java
private static String tradTypeLoisir(String type) {
```

**Documentation actuelle :**
```java
/**
* Traduit les types de loisirs OpenStreetMap en français explicite
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `tradTypeEau()` - Ligne 735

**Qualité :** Minimale

**Signature :**
```java
private static String tradTypeEau(String type) {
```

**Documentation actuelle :**
```java
/**
* Traduit les types de loisirs OpenStreetMap en français explicite
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `extraireChamp()` - Ligne 751

**Qualité :** Minimale

**Signature :**
```java
private static String extraireChamp(String json, String fieldName) {
```

**Documentation actuelle :**
```java
/**
* Extrait un champ d'un JSON simple (sans bibliothèque)
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `reinitialiserCacheDescriptions()` - Ligne 771

**Qualité :** Minimale

**Signature :**
```java
public static void reinitialiserCacheDescriptions() {
```

**Documentation actuelle :**
```java
/**
* Réinitialise le cache des descriptions (à appeler au changement de visite)
*/
```

**⚠️ Tags manquants :** @param

---

### 🟢 `genererDescription()` - Ligne 786

**Qualité :** Complète

**Signature :**
```java
public static CompletableFuture<String> genererDescription(
```

**Documentation actuelle :**
```java
/**
* Génère une description géographique basée sur les informations disponibles
* IMPORTANT : Tient compte des descriptions déjà générées pour éviter les redondances
* @param titreVisite Titre de la visite (peut être null)
* @param titrePanoramique Titre du panoramique (peut être null)
* @param latitude Latitude (peut être null)
* @param longitude Longitude (peut être null)
* @param locale Locale pour la langue de la description
* @return CompletableFuture avec la description générée
*/
```

**Tags présents :** @param, @return

---

### ⚫ `RuntimeException()` - Ligne 829

**Qualité :** Absente

**Signature :**
```java
throw new RuntimeException("Erreur lors de la génération : " + e.getMessage()...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `construirePrompt()` - Ligne 839

**Qualité :** Minimale

**Signature :**
```java
private static String construirePrompt(String titreVisite, String titrePanora...
```

**Documentation actuelle :**
```java
/**
* Construit le prompt pour Ollama selon les données disponibles et la langue
* Utilise le géocodage inverse pour obtenir le lieu réel depuis les coordonnées GPS
* NOUVEAU : Intègre les descriptions précédentes pour éviter les redondances
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `appellerOllama()` - Ligne 1023

**Qualité :** Minimale

**Signature :**
```java
private static String appellerOllama(String prompt) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Appelle l'IA avec fallback : OpenRouter → Ollama local → Hugging Face
* OpenRouter donne accès à GPT-4, Claude, Gemini pour des résultats de meilleure qualité
* Si forceOllama=true, saute OpenRouter et utilise directement Ollama
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `appellerOllamaLocal()` - Ligne 1042

**Qualité :** Absente

**Signature :**
```java
return appellerOllamaLocal(prompt);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 1067

**Qualité :** Absente

**Signature :**
```java
throw new Exception(messageErreur);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 1072

**Qualité :** Absente

**Signature :**
```java
throw new Exception("❌ Mode Hugging Face désactivé. Utilisez OpenRouter ou Ol...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `appellerOpenRouter()` - Ligne 1079

**Qualité :** Minimale

**Signature :**
```java
private static String appellerOpenRouter(String prompt) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Appelle OpenRouter API (accès unifié à GPT-4, Claude, Gemini, etc.)
* Documentation: https://openrouter.ai/docs
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `InputStreamReader()` - Ligne 1113

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 1119

**Qualité :** Absente

**Signature :**
```java
throw new Exception("OpenRouter HTTP " + responseCode + ": " + errorMsg.toStr...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 1124

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 1140

**Qualité :** Absente

**Signature :**
```java
throw new Exception("Format de réponse OpenRouter invalide");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `appellerOllamaLocal()` - Ligne 1158

**Qualité :** Minimale

**Signature :**
```java
private static String appellerOllamaLocal(String prompt) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Appelle l'API Ollama locale pour générer le texte
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `Exception()` - Ligne 1190

**Qualité :** Absente

**Signature :**
```java
throw new Exception(errorMsg);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 1195

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `extraireReponse()` - Ligne 1206

**Qualité :** Absente

**Signature :**
```java
return extraireReponse(jsonResponse);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `appellerHuggingFace()` - Ligne 1212

**Qualité :** Minimale

**Signature :**
```java
private static String appellerHuggingFace(String prompt) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Appelle l'API Hugging Face pour générer le texte
*/
```

**⚠️ Tags manquants :** @param, @return

---

### ⚫ `appellerHuggingFace()` - Ligne 1263

**Qualité :** Absente

**Signature :**
```java
return appellerHuggingFace(prompt); // Réessayer
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `appellerHuggingFace()` - Ligne 1272

**Qualité :** Absente

**Signature :**
```java
return appellerHuggingFace(prompt); // Réessayer avec le modèle suivant
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 1279

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `Exception()` - Ligne 1285

**Qualité :** Absente

**Signature :**
```java
throw new Exception("Erreur Hugging Face (HTTP " + responseCode + "): " + err...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 1290

**Qualité :** Absente

**Signature :**
```java
new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `extraireReponseHuggingFace()` - Ligne 1301

**Qualité :** Absente

**Signature :**
```java
return extraireReponseHuggingFace(jsonResponse);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `extraireReponse()` - Ligne 1307

**Qualité :** Minimale

**Signature :**
```java
private static String extraireReponse(String json) {
```

**Documentation actuelle :**
```java
/**
* Extrait la réponse du JSON retourné par Ollama
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `extraireReponseHuggingFace()` - Ligne 1334

**Qualité :** Minimale

**Signature :**
```java
private static String extraireReponseHuggingFace(String json) {
```

**Documentation actuelle :**
```java
/**
* Extrait la réponse du JSON retourné par Hugging Face
*/
```

**⚠️ Tags manquants :** @param, @return

---

