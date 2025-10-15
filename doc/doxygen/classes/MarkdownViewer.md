# 🔶 MarkdownViewer

[← Retour à l'index](../ETAT_DOCUMENTATION.md)

---

**Fichier :** `D:\developpement\java\editeurPanovisu\src\editeurpanovisu\MarkdownViewer.java`

**Documentation de la classe :** ✅ Oui (4 lignes)

**Classes internes :** Hello

**Progression :** 11/20 éléments documentés (55.0%)

## 📊 Statistiques

### Méthodes

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 4 |
| 🟡 Partielle | 4 |
| 🟠 Minimale | 3 |
| ⚫ Absente | 2 |

### Propriétés

| Qualité | Nombre |
|---------|--------|
| 🟢 Complète | 0 |
| 🟡 Partielle | 0 |
| 🟠 Minimale | 0 |
| ⚫ Absente | 7 |

## Propriétés (7)

### ⚫ `parser` - Ligne 24

**Qualité :** Absente

**Déclaration :**
```java
private final Parser parser;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `renderer` - Ligne 25

**Qualité :** Absente

**Déclaration :**
```java
private final HtmlRenderer renderer;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `fontesChargees` - Ligne 26

**Qualité :** Absente

**Déclaration :**
```java
private static boolean fontesChargees = false;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `html` - Ligne 155

**Qualité :** Absente

**Déclaration :**
```java
return html;
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `charset` - Ligne 317

**Qualité :** Absente

**Déclaration :**
```java
<meta charset="UTF-8">
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `name` - Ligne 318

**Qualité :** Absente

**Déclaration :**
```java
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

### ⚫ `markdown` - Ligne 595

**Qualité :** Absente

**Déclaration :**
```java
String markdown = """
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.

---

## Méthodes (13)

### 🟠 `MarkdownViewer()` - Ligne 31

**Qualité :** Minimale

**Signature :**
```java
public MarkdownViewer() {
```

**Documentation actuelle :**
```java
/**
* Constructeur avec support des extensions GFM (GitHub Flavored Markdown)
*/
```

**⚠️ Tags manquants :** @param, @return

---

### 🟠 `chargerFontes()` - Ligne 57

**Qualité :** Minimale

**Signature :**
```java
private void chargerFontes() {
```

**Documentation actuelle :**
```java
/**
* Charge les polices personnalisées pour le WebView
* Wingdings doit être chargée explicitement car WebView ne peut pas accéder aux polices système Windows
*/
```

**⚠️ Tags manquants :** @param

---

### 🟡 `getWingdingsFontUrl()` - Ligne 74

**Qualité :** Partielle

**Signature :**
```java
private String getWingdingsFontUrl() {
```

**Documentation actuelle :**
```java
/**
* Retourne l'URL de la police Wingdings pour @font-face CSS
* @return URL de la police ou chaîne vide si non trouvée
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `genererFontFaceCSS()` - Ligne 87

**Qualité :** Partielle

**Signature :**
```java
private String genererFontFaceCSS() {
```

**Documentation actuelle :**
```java
/**
* Génère le CSS @font-face pour charger Wingdings dans le WebView
* @return Code CSS @font-face ou chaîne vide si police non disponible
*/
```

**Tags présents :** @return

**⚠️ Tags manquants :** @param

---

### 🟡 `afficherFichierMarkdown()` - Ligne 112

**Qualité :** Partielle

**Signature :**
```java
public void afficherFichierMarkdown(WebView webView, String markdownFilePath)...
```

**Documentation actuelle :**
```java
/**
* Affiche un fichier Markdown dans une WebView
*
* @param webView La WebView dans laquelle afficher le contenu
* @param markdownFilePath Chemin vers le fichier Markdown
* @throws IOException Si le fichier ne peut pas être lu
*/
```

**Tags présents :** @param, @throws

---

### 🟡 `afficherMarkdown()` - Ligne 136

**Qualité :** Partielle

**Signature :**
```java
public void afficherMarkdown(WebView webView, String markdownContent) {
```

**Documentation actuelle :**
```java
/**
* Affiche du contenu Markdown dans une WebView
*
* @param webView La WebView dans laquelle afficher le contenu
* @param markdownContent Contenu Markdown à afficher
*/
```

**Tags présents :** @param

---

### 🟢 `convertirMarkdownEnHtml()` - Ligne 148

**Qualité :** Complète

**Signature :**
```java
public String convertirMarkdownEnHtml(String markdown) {
```

**Documentation actuelle :**
```java
/**
* Convertit du Markdown en HTML
*
* @param markdown Contenu Markdown
* @return HTML généré
*/
```

**Tags présents :** @param, @return

---

### 🟢 `remplacerEmojis()` - Ligne 165

**Qualité :** Complète

**Signature :**
```java
private String remplacerEmojis(String html) {
```

**Documentation actuelle :**
```java
/**
* Remplace les émojis courants par des badges visuels stylisés
* Affiche uniquement l'icône, le texte apparaît au survol (tooltip)
*
* @param html Contenu HTML avec émojis
* @return HTML avec émojis remplacés par des icônes avec tooltips
*/
```

**Tags présents :** @param, @return

---

### 🟢 `envelopperDansHtml()` - Ligne 299

**Qualité :** Complète

**Signature :**
```java
private String envelopperDansHtml(String bodyHtml) {
```

**Documentation actuelle :**
```java
/**
* Enveloppe le HTML dans une structure complète avec CSS
*
* @param bodyHtml Contenu HTML du body
* @return HTML complet avec head et styles
*/
```

**Tags présents :** @param, @return

---

### ⚫ `envelopperDansHtml()` - Ligne 300

**Qualité :** Absente

**Signature :**
```java
return envelopperDansHtml(bodyHtml, null);
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre
- Tag `@return` pour la valeur de retour

---

### 🟢 `envelopperDansHtml()` - Ligne 310

**Qualité :** Complète

**Signature :**
```java
private String envelopperDansHtml(String bodyHtml, String baseUrl) {
```

**Documentation actuelle :**
```java
/**
* Enveloppe le HTML dans une structure complète avec CSS et URL de base
*
* @param bodyHtml Contenu HTML du body
* @param baseUrl URL de base pour résoudre les chemins relatifs (peut être null)
* @return HTML complet avec head et styles
*/
```

**Tags présents :** @param, @return

---

### 🟠 `main()` - Ligne 594

**Qualité :** Minimale

**Signature :**
```java
public static void main(String[] args) {
```

**Documentation actuelle :**
```java
/**
* Exemple d'utilisation
*/
```

**⚠️ Tags manquants :** @param

---

### ⚫ `main()` - Ligne 610

**Qualité :** Absente

**Signature :**
```java
public static void main(String[] args) {
```

**❌ Aucune documentation**

**Suggestion :** Ajouter un Javadoc avec :
- Description de la méthode
- Tag `@param` pour chaque paramètre

---

