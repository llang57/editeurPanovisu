# ⚠️ LocalHTTPServer

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `/home/llang/developpement/java/editeurPanovisu/src/editeurpanovisu/util/LocalHTTPServer.java`

**Documentation de la classe :** ✅ Oui (20 lignes)

**Classes internes :** FileHandler

**Progression :** 11/27 éléments documentés (40.7%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 7 |
| 🟡 Partielle | 1 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 4 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 12 |

## Propriétés (12)

### ⚫ `instance` - Ligne 37

**Qualité :** Absente

**Déclaration :**
```java
private static LocalHTTPServer instance;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `server` - Ligne 38

**Qualité :** Absente

**Déclaration :**
```java
private HttpServer server;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `port` - Ligne 39

**Qualité :** Absente

**Déclaration :**
```java
private int port = 8080;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `rootDirectory` - Ligne 40

**Qualité :** Absente

**Déclaration :**
```java
private String rootDirectory;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `isRunning` - Ligne 41

**Qualité :** Absente

**Déclaration :**
```java
private boolean isRunning = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `instance` - Ligne 81

**Qualité :** Absente

**Déclaration :**
```java
return instance;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `tentatives` - Ligne 118

**Qualité :** Absente

**Déclaration :**
```java
int tentatives = 0;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `portOriginal` - Ligne 119

**Qualité :** Absente

**Déclaration :**
```java
int portOriginal = port;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `isRunning` - Ligne 172

**Qualité :** Absente

**Déclaration :**
```java
return isRunning;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `port` - Ligne 190

**Qualité :** Absente

**Déclaration :**
```java
return port;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `rootDirectory` - Ligne 197

**Qualité :** Absente

**Déclaration :**
```java
private final String rootDirectory;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `response` - Ligne 218

**Qualité :** Absente

**Déclaration :**
```java
String response = "404 - Fichier non trouvé";
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (15)

### 🟠 `LocalHTTPServer()` - Ligne 69

**Qualité :** Minimale

**Signature :**
```java
private LocalHTTPServer() {
```

**Documentation actuelle :**
```java
/**
* Constructeur privé (pattern Singleton)
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟢 `getInstance()` - Ligne 77

**Qualité :** Complète

**Signature :**
```java
public static synchronized LocalHTTPServer getInstance() {
```

**Documentation actuelle :**
```java
/**
* Obtient l'instance unique du serveur
*
* @return L'instance du serveur HTTP local
*/
```

**Tags présents :** @return

---

### 🟢 `setRootDirectory()` - Ligne 89

**Qualité :** Complète

**Signature :**
```java
public void setRootDirectory(String rootDirectory) {
```

**Documentation actuelle :**
```java
/**
* Définit le répertoire racine à servir
*
* @param rootDirectory Chemin absolu du répertoire contenant les fichiers
*/
```

**Tags présents :** @param

---

### 🟢 `setPort()` - Ligne 98

**Qualité :** Complète

**Signature :**
```java
public void setPort(int port) {
```

**Documentation actuelle :**
```java
/**
* Définit le port du serveur
*
* @param port Numéro de port (par défaut 8080)
*/
```

**Tags présents :** @param

---

### 🟡 `start()` - Ligne 107

**Qualité :** Partielle

**Signature :**
```java
public void start() throws IOException {
```

**Documentation actuelle :**
```java
/**
* Démarre le serveur HTTP
*
* @throws IOException Si le serveur ne peut pas démarrer
*/
```

**Tags présents :** @throws

**⚠️ Tags manquants :** @param

---

### ⚫ `IllegalStateException()` - Ligne 114

**Qualité :** Absente

**Signature :**
```java
throw new IllegalStateException("Le répertoire racine n'est pas défini");
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `IOException()` - Ligne 130

**Qualité :** Absente

**Signature :**
```java
throw new IOException("Impossible de trouver un port disponible après " + ten...
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟠 `stop()` - Ligne 148

**Qualité :** Minimale

**Signature :**
```java
public void stop() {
```

**Documentation actuelle :**
```java
/**
* Arrête le serveur HTTP
*/
```

**⚠️ Tags manquants :** @param

---

### 🟠 `reset()` - Ligne 159

**Qualité :** Minimale

**Signature :**
```java
public void reset() {
```

**Documentation actuelle :**
```java
/**
* Réinitialise le serveur (arrête et remet le port à 8080)
*/
```

**⚠️ Tags manquants :** @param

---

### 🟢 `isRunning()` - Ligne 171

**Qualité :** Complète

**Signature :**
```java
public boolean isRunning() {
```

**Documentation actuelle :**
```java
/**
* Vérifie si le serveur est en cours d'exécution
*
* @return true si le serveur est démarré
*/
```

**Tags présents :** @return

---

### 🟢 `getUrl()` - Ligne 180

**Qualité :** Complète

**Signature :**
```java
public String getUrl() {
```

**Documentation actuelle :**
```java
/**
* Obtient l'URL complète du serveur
*
* @return L'URL (ex: http://localhost:8080)
*/
```

**Tags présents :** @return

---

### 🟢 `getPort()` - Ligne 189

**Qualité :** Complète

**Signature :**
```java
public int getPort() {
```

**Documentation actuelle :**
```java
/**
* Obtient le numéro de port utilisé
*
* @return Le numéro de port
*/
```

**Tags présents :** @return

---

### ⚫ `FileHandler()` - Ligne 199

**Qualité :** Absente

**Signature :**
```java
public FileHandler(String rootDirectory) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### ⚫ `handle()` - Ligne 204

**Qualité :** Absente

**Signature :**
```java
public void handle(HttpExchange exchange) throws IOException {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

### 🟢 `getFileExtension()` - Ligne 252

**Qualité :** Complète

**Signature :**
```java
private String getFileExtension(String fileName) {
```

**Documentation actuelle :**
```java
/**
* Extrait l'extension d'un nom de fichier
*
* @param fileName Nom du fichier
* @return L'extension en minuscules (sans le point)
*/
```

**Tags présents :** @param, @return

---

