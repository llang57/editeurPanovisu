# ⚠️ OpenRouterClient

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\OpenRouterClient.java`

**Documentation de la classe :** ✅ Oui (8 lignes)

**Progression :** 13/35 éléments documentés (37.1%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 6 |
| 🟡 Partielle | 4 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 11 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 11 |

## Propriétés (11)

### ⚫ `API_BASE_URL` - Ligne 21

**Qualité :** Absente

**Déclaration :**
```java
private static final String API_BASE_URL = "https://openrouter.ai/api/v1/chat...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `apiKey` - Ligne 22

**Qualité :** Absente

**Déclaration :**
```java
private final String apiKey;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelName` - Ligne 23

**Qualité :** Absente

**Déclaration :**
```java
private final String modelName;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 128

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `line` - Ligne 140

**Qualité :** Absente

**Déclaration :**
```java
String line;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `systemMessage` - Ligne 175

**Qualité :** Absente

**Déclaration :**
```java
String systemMessage = "Tu es un assistant qui aide à créer des descriptions ...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `systemMessage` - Ligne 194

**Qualité :** Absente

**Déclaration :**
```java
String systemMessage = "Tu es un assistant qui aide à identifier des points d...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `marker` - Ligne 222

**Qualité :** Absente

**Déclaration :**
```java
String marker = "\"content\":";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `jsonResponse` - Ligne 225

**Qualité :** Absente

**Déclaration :**
```java
return jsonResponse; // Retourner la réponse brute si format non reconnu
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `endIdx` - Ligne 236

**Qualité :** Absente

**Déclaration :**
```java
int endIdx = startIdx;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `modelName` - Ligne 267

**Qualité :** Absente

**Déclaration :**
```java
return modelName;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (24)

### 🟠 `OpenRouterClient()` - Ligne 28

**Qualité :** Minimale

**Signature :**
```java
public OpenRouterClient() {
```

**Documentation actuelle :**
```java
/**
* Constructeur avec configuration automatique depuis ApiKeysConfig
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟡 `OpenRouterClient()` - Ligne 42

**Qualité :** Partielle

**Signature :**
```java
public OpenRouterClient(String apiKey, String modelName) {
```

**Documentation actuelle :**
```java
/**
* Constructeur avec clé API et modèle personnalisés
* @param apiKey Clé API OpenRouter
* @param modelName Nom du modèle (ex: "meta-llama/llama-3.2-3b-instruct:free")
*/
```

**Tags présents :** @param

**⚠️ Tags manquants :** @return

---

### 🟢 `chat()` - Ligne 53

**Qualité :** Complète

**Signature :**
```java
public String chat(String userMessage) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Envoie un message au modèle et obtient une réponse
* @param userMessage Le message de l'utilisateur
* @return La réponse du modèle
* @throws Exception Si une erreur survient lors de l'appel API
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `chat()` - Ligne 54

**Qualité :** Absente

**Signature :**
```java
return chat(userMessage, null, 0.7, 1000);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `chat()` - Ligne 64

**Qualité :** Complète

**Signature :**
```java
public String chat(String userMessage, String systemMessage) throws Exception {
```

**Documentation actuelle :**
```java
/**
* Envoie un message avec un contexte système
* @param userMessage Le message de l'utilisateur
* @param systemMessage Message système pour définir le comportement du modèle
* @return La réponse du modèle
* @throws Exception Si une erreur survient lors de l'appel API
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `chat()` - Ligne 65

**Qualité :** Absente

**Signature :**
```java
return chat(userMessage, systemMessage, 0.7, 1000);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `chat()` - Ligne 77

**Qualité :** Complète

**Signature :**
```java
public String chat(String userMessage, String systemMessage, double temperatu...
```

**Documentation actuelle :**
```java
/**
* Envoie un message avec tous les paramètres personnalisables
* @param userMessage Le message de l'utilisateur
* @param systemMessage Message système (peut être null)
* @param temperature Température pour la génération (0.0-2.0)
* @param maxTokens Nombre maximum de tokens à générer
* @return La réponse du modèle
* @throws Exception Si une erreur survient lors de l'appel API
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `IllegalStateException()` - Ligne 79

**Qualité :** Absente

**Signature :**
```java
throw new IllegalStateException("Clé API OpenRouter non configurée");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 126

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

### ⚫ `extractMessageContent()` - Ligne 134

**Qualité :** Absente

**Signature :**
```java
return extractMessageContent(response.toString());
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `InputStreamReader()` - Ligne 138

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

### ⚫ `Exception()` - Ligne 144

**Qualité :** Absente

**Signature :**
```java
throw new Exception("Erreur API OpenRouter (" + responseCode + "): " + error);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `chatAsync()` - Ligne 157

**Qualité :** Complète

**Signature :**
```java
public CompletableFuture<String> chatAsync(String userMessage) {
```

**Documentation actuelle :**
```java
/**
* Envoie un message de manière asynchrone
* @param userMessage Le message de l'utilisateur
* @return CompletableFuture contenant la réponse
*/
```

**Tags présents :** @param, @return

---

### ⚫ `chat()` - Ligne 160

**Qualité :** Absente

**Signature :**
```java
return chat(userMessage);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `RuntimeException()` - Ligne 162

**Qualité :** Absente

**Signature :**
```java
throw new RuntimeException("Erreur lors de l'appel à OpenRouter", e);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `generatePanoramaDescription()` - Ligne 174

**Qualité :** Complète

**Signature :**
```java
public String generatePanoramaDescription(String titre, String tags) throws E...
```

**Documentation actuelle :**
```java
/**
* Génère des suggestions de description pour un panorama
* @param titre Titre du panorama
* @param tags Tags associés
* @return Une description générée
* @throws Exception Si une erreur survient
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `chat()` - Ligne 183

**Qualité :** Absente

**Signature :**
```java
return chat(userMessage, systemMessage, 0.8, 200);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `suggestHotspots()` - Ligne 193

**Qualité :** Complète

**Signature :**
```java
public String suggestHotspots(String titre, String description) throws Except...
```

**Documentation actuelle :**
```java
/**
* Suggère des hotspots pertinents pour un panorama
* @param titre Titre du panorama
* @param description Description du lieu
* @return Suggestions de hotspots
* @throws Exception Si une erreur survient
*/
```

**Tags présents :** @param, @return, @throws

---

### ⚫ `chat()` - Ligne 202

**Qualité :** Absente

**Signature :**
```java
return chat(userMessage, systemMessage, 0.7, 500);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `escapeJson()` - Ligne 208

**Qualité :** Minimale

**Signature :**
```java
private String escapeJson(String text) {
```

**Documentation actuelle :**
```java
/**
* Échappe les caractères spéciaux pour JSON
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `extractMessageContent()` - Ligne 220

**Qualité :** Minimale

**Signature :**
```java
private String extractMessageContent(String jsonResponse) {
```

**Documentation actuelle :**
```java
/**
* Extrait le contenu du message de la réponse JSON
* Format OpenRouter: {"choices": [{"message": {"content": "..."}}]}
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟡 `isConfigured()` - Ligne 258

**Qualité :** Partielle

**Signature :**
```java
public boolean isConfigured() {
```

**Documentation actuelle :**
```java
/**
* Vérifie si le client est configuré correctement
* @return true si la clé API est présente
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getModelName()` - Ligne 266

**Qualité :** Partielle

**Signature :**
```java
public String getModelName() {
```

**Documentation actuelle :**
```java
/**
* Récupère le nom du modèle utilisé
* @return Le nom du modèle
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `getFreeModels()` - Ligne 274

**Qualité :** Partielle

**Signature :**
```java
public static String[] getFreeModels() {
```

**Documentation actuelle :**
```java
/**
* Liste des modèles gratuits populaires sur OpenRouter
* @return Tableau de noms de modèles gratuits
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

