# editeurPanovisu v2.0 - Migration Java 25

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

> Éditeur de visites virtuelles panoramiques - Modernisé pour Java 25 avec OpenStreetMap

## 🎯 Objectifs de la Migration

- ✅ **Java 8 → Java 25** : Syntaxe moderne, performances améliorées
- ✅ **JavaFX externe** : JavaFX 21 comme dépendance Maven
- ✅ **Google Maps → OpenStreetMap** : Cartes libres avec Leaflet.js
- ✅ **Serveur HTTP embarqué** : Résolution du problème `file://` URLs

## 📦 Nouveautés v2.0

### Technique
- **Java 25** avec syntaxe moderne (var, switch expressions, text blocks, records)
- **JavaFX 21** avec dépendances Maven/Gradle
- **Serveur HTTP embarqué** (Javalin) pour pages HTML locales
- **Build Maven** moderne avec plugins à jour

### Fonctionnel
- **Cartes OpenStreetMap** avec Leaflet.js
  - 7 types de cartes (OSM, OSM Topo, OSM Cycle, Stamen Terrain, etc.)
  - API JavaScript complète pour interaction Java↔JavaScript
  - Marqueurs, cercles, polylignes
  - Popups personnalisés
- **Meilleure sécurité** (pas de file:// URLs bloquées)
- **Performance améliorée** (serveur HTTP optimisé)

### Compatibilité
- ✅ **Projets v1.3** : ouverture sans modification
- ✅ **Export XML** : format inchangé
- ✅ **Migration automatique** : cartes Google → OSM

## 🚀 Démarrage Rapide

### Prérequis

1. **Java 25** ([Télécharger](https://adoptium.net/temurin/releases/?version=25))
2. **Maven 3.9+** ([Télécharger](https://maven.apache.org/download.cgi))
3. **Git** (optionnel)

### Installation

```powershell
# Cloner le projet
git clone https://github.com/llang57/editeurPanovisu.git
cd editeurPanovisu

# Créer la branche de migration (si vous contribuez)
git checkout -b migration/java25-openstreetmap
```

### Compilation

```powershell
# Via le script PowerShell (recommandé)
.\build.ps1 full

# Ou directement avec Maven
mvn clean package
```

### Exécution

```powershell
# Via le script PowerShell
.\build.ps1 run

# Ou directement avec Maven
mvn javafx:run
```

## 📖 Documentation

| Document | Description |
|----------|-------------|
| [QUICKSTART_MIGRATION.md](QUICKSTART_MIGRATION.md) | Guide de démarrage rapide |
| [MIGRATION_JAVA25_PLAN.md](MIGRATION_JAVA25_PLAN.md) | Plan complet de migration (1100+ lignes) |
| [RESUME_MIGRATION.md](RESUME_MIGRATION.md) | Résumé et checklist |
| [README_OLD.md](README.md) | Documentation originale v1.3 |

## 🗂️ Structure du Projet

```
editeurPanovisu/
├── pom.xml                          # Configuration Maven (NOUVEAU)
├── build.ps1                        # Script de build PowerShell (NOUVEAU)
├── src/
│   └── editeurpanovisu/
│       ├── EditeurPanovisu.java     # Classe principale
│       ├── NavigateurCarte.java     # Navigation carte (À MODIFIER)
│       ├── util/
│       │   └── LocalHTMLServer.java # Serveur HTTP embarqué (NOUVEAU)
│       └── ...
├── pagesHTML/
│   └── openstreetmap.html           # Carte Leaflet.js (NOUVEAU)
├── aide/
│   └── aide.html                    # Documentation
├── diaporama/                       # Diaporamas
├── images/                          # Ressources images
├── templates/                       # Templates visites
└── docs/
    ├── QUICKSTART_MIGRATION.md      # Guide rapide (NOUVEAU)
    ├── MIGRATION_JAVA25_PLAN.md     # Plan complet (NOUVEAU)
    └── RESUME_MIGRATION.md          # Résumé (NOUVEAU)
```

## 🔧 Commandes Utiles

### Build

```powershell
.\build.ps1 compile    # Compiler uniquement
.\build.ps1 test       # Tests unitaires
.\build.ps1 package    # Créer le JAR
.\build.ps1 run        # Lancer l'application
.\build.ps1 clean      # Nettoyer
.\build.ps1 full       # Build complet
```

### Maven Direct

```powershell
mvn clean              # Nettoyer
mvn compile            # Compiler
mvn test               # Tests
mvn package            # JAR
mvn javafx:run         # Lancer
mvn dependency:tree    # Dépendances
```

## 🗺️ API Carte OpenStreetMap

### Depuis Java

```java
// Ajouter un marqueur
navigateurCarte.addMarker(48.8566, 2.3522, "Paris", "Capitale de la France");

// Centrer la carte
navigateurCarte.setCenter(48.8566, 2.3522, 13);

// Changer le type de carte
navigateurCarte.setMapType("osm-topo");

// Effacer les marqueurs
navigateurCarte.clearMarkers();
```

### Depuis JavaScript (dans la page HTML)

```javascript
// Ajouter un marqueur
addMarker(48.8566, 2.3522, "Paris", "<b>Paris</b><br>Capitale");

// Centrer
setCenter(48.8566, 2.3522, 13);

// Changer type
setMapType("osm-cycle");

// Ajuster la vue sur tous les marqueurs
fitBounds();
```

### Types de Cartes Disponibles

- `osm` : OpenStreetMap standard
- `osm-topo` : Topographique
- `osm-cycle` : Cyclable
- `osm-hot` : Humanitaire
- `stamen-terrain` : Terrain (Stamen)
- `stamen-toner` : Noir et blanc
- `stamen-watercolor` : Aquarelle

## 🧪 Tests

### Tests Unitaires

```powershell
mvn test
```

### Tests Fonctionnels

Checklist :
- [ ] Application se lance
- [ ] Interface s'affiche
- [ ] Menu Aide s'ouvre (serveur HTTP)
- [ ] Carte OpenStreetMap fonctionne
- [ ] Marqueurs s'ajoutent
- [ ] Export visite fonctionne
- [ ] Visite s'affiche dans navigateur

## 🐛 Dépannage

### "Java version mismatch"

```powershell
# Vérifier la version
java -version  # Doit afficher "25"

# Configurer JAVA_HOME (Windows)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

### "JavaFX runtime components are missing"

```powershell
# Toujours utiliser mvn javafx:run
# PAS java -jar
mvn javafx:run
```

### "Port already in use"

```powershell
# Trouver le processus
netstat -ano | findstr :8080

# Tuer le processus
taskkill /PID <PID> /F
```

### "Cannot load aide/aide.html"

Vérifier que le serveur HTTP est démarré :
```java
// Dans EditeurPanovisu.java
LocalHTMLServer.getInstance().start(System.getProperty("user.dir"));
```

## 📊 État du Projet

| Tâche | État |
|-------|------|
| Configuration Maven | ✅ Terminé |
| Serveur HTTP | ✅ Terminé |
| Carte OpenStreetMap | ✅ Terminé |
| Documentation | ✅ Terminé |
| Migration code Java | 🔄 En cours |
| Tests complets | ⏳ À faire |
| Release v2.0 | ⏳ À faire |

## 🤝 Contribution

### Processus

1. Fork le projet
2. Créer une branche (`git checkout -b feature/amazing-feature`)
3. Commit (`git commit -m 'Add amazing feature'`)
4. Push (`git push origin feature/amazing-feature`)
5. Ouvrir une Pull Request

### Guidelines

- Suivre les conventions Java (CamelCase, etc.)
- Documenter le code (JavaDoc)
- Ajouter des tests unitaires
- Mettre à jour la documentation

## 📝 Changelog

### Version 2.0.0-SNAPSHOT (En cours)

**Ajouts :**
- Configuration Maven complète
- Serveur HTTP embarqé (Javalin)
- Carte OpenStreetMap avec Leaflet.js
- API JavaScript↔Java pour cartes
- Documentation complète de migration

**Modifications :**
- Java 8 → Java 25
- JavaFX intégré → JavaFX 21 externe
- Google Maps → OpenStreetMap
- Ant → Maven
- file:// URLs → http://localhost

**Compatibilité :**
- Projets v1.3 supportés
- Format XML inchangé
- Migration automatique

### Version 1.3.1 (Baseline)
- Dernière version Java 8
- Google Maps
- JavaFX 8 intégré
- Build Ant/NetBeans

## 📜 Licence

Ce projet est sous licence **MIT**. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🔗 Liens

- [GitHub Repository](https://github.com/llang57/editeurPanovisu)
- [Site Web panoVisu](https://panovisu.fr)
- [Documentation v1.3](http://panovisu.fr/documentation)
- [Forum Support](http://panovisu.fr/hesk/)

## 👤 Auteur

**Laurent Lang**
- GitHub: [@llang57](https://github.com/llang57)
- Site: [panovisu.fr](https://panovisu.fr)

## 🙏 Remerciements

- Communauté OpenJFX
- Projet Leaflet.js
- Contributeurs OpenStreetMap
- Eclipse Adoptium (Java 25)

---

**Note :** Cette version 2.0 est en cours de migration. Référez-vous à la documentation dans `docs/` pour suivre l'avancement.
