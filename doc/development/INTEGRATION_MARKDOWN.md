# Intégration du lecteur Markdown dans EditeurPanovisu

**Date :** 12 octobre 2025  
**Bibliothèque :** CommonMark-Java 0.22.0  
**Extensions :** Tables GFM, Strikethrough GFM

---

## 📚 Vue d'ensemble

Intégration d'un lecteur Markdown complet dans l'application pour afficher la documentation directement depuis l'interface utilisateur.

## 🔧 Composants ajoutés

### 1. Dépendances Maven (pom.xml)

```xml
<!-- Markdown Parser -->
<dependency>
    <groupId>org.commonmark</groupId>
    <artifactId>commonmark</artifactId>
    <version>0.22.0</version>
</dependency>

<!-- Extensions Markdown (tables, strikethrough, etc.) -->
<dependency>
    <groupId>org.commonmark</groupId>
    <artifactId>commonmark-ext-gfm-tables</artifactId>
    <version>0.22.0</version>
</dependency>
<dependency>
    <groupId>org.commonmark</groupId>
    <artifactId>commonmark-ext-gfm-strikethrough</artifactId>
    <version>0.22.0</version>
</dependency>
```

### 2. MarkdownViewer.java

**Classe utilitaire** pour convertir Markdown en HTML et l'afficher dans une WebView JavaFX.

**Fonctionnalités :**
- ✅ Conversion Markdown → HTML
- ✅ Support GitHub Flavored Markdown (GFM)
- ✅ Support des tables
- ✅ Support du texte barré (~~strikethrough~~)
- ✅ CSS moderne style GitHub
- ✅ Syntaxe highlighting pour le code
- ✅ Affichage dans WebView JavaFX

**Méthodes principales :**
```java
// Afficher un fichier Markdown
void afficherFichierMarkdown(WebView webView, String markdownFilePath)

// Afficher du contenu Markdown
void afficherMarkdown(WebView webView, String markdownContent)

// Convertir Markdown en HTML pur
String convertirMarkdownEnHtml(String markdown)
```

### 3. DocumentationDialog.java

**Fenêtre de dialogue** pour afficher la documentation de l'application.

**Fonctionnalités :**
- ✅ Sélecteur de documents (ComboBox)
- ✅ Documents prédéfinis :
  - Documentation générale (doc/README.md)
  - Installation (doc/installation/INSTALLATION_GUIDE.md)
  - Installation utilisateur (doc/installation/INSTALLATION_UTILISATEUR.md)
  - Guide de build (doc/installation/BUILD_EXE_GUIDE.md)
  - Migration Java 25 (doc/migration/MIGRATION_COMPLETED.md)
- ✅ Bouton "Ouvrir fichier..." pour charger n'importe quel fichier .md
- ✅ WebView pour affichage riche
- ✅ Taille de fenêtre : 1000x700 pixels

**Utilisation :**
```java
// Afficher la fenêtre avec le document par défaut
DocumentationDialog.afficher();

// Afficher un document spécifique
DocumentationDialog.afficher(DocumentType.INSTALLATION);
```

### 4. Intégration au menu Aide

**Menu Aide > Documentation (F1)**
- Nouveau menu item dans le menu Aide
- Raccourci clavier : **F1**
- Ouvre la fenêtre DocumentationDialog
- Traductions ajoutées :
  - 🇫🇷 Français : "Documentation (F1)"
  - 🇬🇧 Anglais : "Documentation (F1)"
  - 🇩🇪 Allemand : "Dokumentation (F1)"

## 🎨 Style CSS

Le lecteur Markdown utilise un style moderne inspiré de GitHub :

- **Typographie** : System fonts (Segoe UI, Roboto, etc.)
- **Code** : Police monospace (Consolas, Monaco, Courier New)
- **Tables** : Bordures et zebra striping
- **Liens** : Bleu GitHub (#0366d6)
- **Blockquotes** : Bordure gauche colorée
- **Images** : Coins arrondis, responsive

## 📖 Formats supportés

### Markdown standard
- Titres (# ## ### etc.)
- **Gras** et *italique*
- Listes (ordonnées et non ordonnées)
- Liens et images
- Code inline et blocs de code
- Citations (blockquotes)
- Lignes horizontales

### Extensions GFM
- ~~Texte barré~~
- Tables avec alignement
- Autolinks

## 🚀 Utilisation dans le code

### Exemple 1 : Afficher un fichier Markdown

```java
WebView webView = new WebView();
MarkdownViewer viewer = new MarkdownViewer();

try {
    viewer.afficherFichierMarkdown(webView, "doc/README.md");
} catch (IOException e) {
    e.printStackTrace();
}
```

### Exemple 2 : Afficher du Markdown dynamique

```java
String markdown = """
    # Mon Titre
    
    Contenu **en gras** et *en italique*.
    
    | Col 1 | Col 2 |
    |-------|-------|
    | Val 1 | Val 2 |
    """;

MarkdownViewer viewer = new MarkdownViewer();
viewer.afficherMarkdown(webView, markdown);
```

### Exemple 3 : Ouvrir la documentation

```java
// Depuis n'importe où dans l'application
DocumentationDialog.afficher();

// Ou pour un document spécifique
DocumentationDialog.afficher(DocumentType.INSTALLATION_USER);
```

## 📂 Structure des fichiers de documentation

```
doc/
├── README.md                           # Index principal
├── installation/
│   ├── INSTALLATION_GUIDE.md          # Guide développeur
│   ├── INSTALLATION_UTILISATEUR.md    # Guide utilisateur
│   └── BUILD_EXE_GUIDE.md             # Guide de build
├── migration/
│   ├── MIGRATION_COMPLETED.md         # Migration Java 25
│   └── ...
├── development/
│   ├── INTEGRATION_MARKDOWN.md        # Ce fichier
│   └── ...
└── ...
```

## 🔄 Améliorations possibles

### Court terme
- [ ] Ajouter un bouton "Rafraîchir" pour recharger le document
- [ ] Ajouter un champ de recherche dans le document
- [ ] Historique de navigation (Précédent/Suivant)
- [ ] Export HTML du document affiché

### Moyen terme
- [ ] Support du dark mode (thème sombre)
- [ ] Table des matières générée automatiquement
- [ ] Liens hypertexte entre documents
- [ ] Support des ancres (#section)

### Long terme
- [ ] Éditeur Markdown intégré
- [ ] Support des diagrammes (Mermaid)
- [ ] Support LaTeX pour les formules mathématiques
- [ ] Génération de PDF depuis Markdown

## 🐛 Tests

### Test 1 : Affichage basique
```bash
mvn clean compile
mvn javafx:run
# Menu Aide > Documentation
```

### Test 2 : Tous les documents
- Vérifier que tous les DocumentType chargent correctement
- Vérifier les liens internes
- Vérifier les images relatives

### Test 3 : Ouverture de fichier externe
- Cliquer sur "Ouvrir fichier..."
- Sélectionner un fichier .md externe
- Vérifier l'affichage correct

## 📝 Notes de développement

- **CommonMark-Java** est préféré à Flexmark pour sa légèreté
- Les extensions GFM sont essentielles pour les tables
- Le CSS est embarqué dans le HTML pour simplifier
- Les chemins des documents sont relatifs au répertoire du projet
- F1 est le raccourci standard pour l'aide dans de nombreuses applications

## 🔗 Références

- [CommonMark Spec](https://commonmark.org/)
- [CommonMark-Java GitHub](https://github.com/commonmark/commonmark-java)
- [GitHub Flavored Markdown](https://github.github.com/gfm/)

---

**Dernière mise à jour :** 12 octobre 2025
