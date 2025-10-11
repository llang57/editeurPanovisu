# 📚 INDEX - Documentation EditeurPanovisu v2.0

> Navigation rapide dans toute la documentation du projet

**Dernière mise à jour :** 11 octobre 2025, 09:00

---

## 🗂️ Documentation Organisée

### 📚 [doc/](doc/) - Documentation Complète
**Point d'entrée principal** : [doc/README.md](doc/README.md)

La documentation est maintenant organisée par thématique :

- 🔄 **[doc/migration/](doc/migration/)** - Migration Java 25 et JavaFX 19.0.2.1
- 🗺️ **[doc/geolocalisation/](doc/geolocalisation/)** - Fonctionnalité de géolocalisation
- 🐛 **[doc/debug/](doc/debug/)** - Guides de débogage
- 📖 **[doc/guides/](doc/guides/)** - Guides utilisateur (à venir)
- 🏗️ **[doc/architecture/](doc/architecture/)** - Architecture technique (à venir)

---

## 🚀 Par Où Commencer ?

### Nouveau sur le Projet ?
1. 📖 **[doc/README.md](doc/README.md)** - Vue d'ensemble complète du projet v2.0
2. 🗺️ **[doc/migration/README.md](doc/migration/README.md)** - Synthèse de la migration
3. 📝 **[doc/migration/QUICKSTART_MIGRATION.md](doc/migration/QUICKSTART_MIGRATION.md)** - Démarrage rapide (30 min)

### Développer une Fonctionnalité ?
1. 🗺️ **[doc/geolocalisation/README.md](doc/geolocalisation/README.md)** - Documentation géolocalisation
2. 🐛 **[doc/debug/README.md](doc/debug/README.md)** - Guides de débogage
3. 🏗️ **[doc/architecture/](doc/architecture/)** - Architecture du projet (à venir)

### Migration Déjà Effectuée ?
✅ La migration vers Java 25 et JavaFX 19.0.2.1 est **complète et fonctionnelle**.

Consulter :
- 📊 **[doc/migration/RESUME_MIGRATION.md](doc/migration/RESUME_MIGRATION.md)** - Résumé de la migration
- 📋 **[doc/migration/PROCHAINES_ETAPES.md](doc/migration/PROCHAINES_ETAPES.md)** - Améliorations futures

---

## 📖 Documentation par Thématique

### 🔄 Migration Java 25
**Dossier :** [doc/migration/](doc/migration/)

| Document | Description | Temps Lecture |
|----------|-------------|---------------|
| **[README.md](doc/migration/README.md)** | Index migration avec synthèse | 10 min |
| **[MIGRATION_JAVA25_PLAN.md](doc/migration/MIGRATION_JAVA25_PLAN.md)** | Plan complet de migration | 60 min |
| **[QUICKSTART_MIGRATION.md](doc/migration/QUICKSTART_MIGRATION.md)** | Guide de démarrage rapide | 15 min |
| **[RESUME_MIGRATION.md](doc/migration/RESUME_MIGRATION.md)** | Résumé exécutif | 30 min |
| **[MEMO_MIGRATION.md](doc/migration/MEMO_MIGRATION.md)** | Notes techniques | 20 min |
| **[PROCHAINES_ETAPES.md](doc/migration/PROCHAINES_ETAPES.md)** | Roadmap future | 20 min |

### 🗺️ Géolocalisation
**Dossier :** [doc/geolocalisation/](doc/geolocalisation/)

| Document | Description | Temps Lecture |
|----------|-------------|---------------|
| **[README.md](doc/geolocalisation/README.md)** | Index géolocalisation avec architecture | 15 min |
| **[CORRECTIONS_GEOLOCALISATION.md](doc/geolocalisation/CORRECTIONS_GEOLOCALISATION.md)** | Solutions aux 3 problèmes principaux | 20 min |
| **[CORRECTIF_MARQUEUR_METZ.md](doc/geolocalisation/CORRECTIF_MARQUEUR_METZ.md)** | Bug marqueur Metz corrigé | 10 min |

### 🐛 Debug et Diagnostics
**Dossier :** [doc/debug/](doc/debug/)

| Document | Description | Temps Lecture |
|----------|-------------|---------------|
| **[README.md](doc/debug/README.md)** | Index debug avec outils | 15 min |
| **[GUIDE_DEBUG_GEOLOCALISATION.md](doc/debug/GUIDE_DEBUG_GEOLOCALISATION.md)** | Guide de debug complet | 25 min |

### 📖 Guides Utilisateur
**Dossier :** [doc/guides/](doc/guides/)

| Document | Description | Temps Lecture |
|----------|-------------|---------------|
| **[README.md](doc/guides/README.md)** | Index des guides | 10 min |
| **[CONFIGURATION_API_KEYS.md](doc/guides/CONFIGURATION_API_KEYS.md)** | Configuration clés API | 20 min |
| **[API_KEYS_README.md](doc/guides/API_KEYS_README.md)** | README simplifié API | 5 min |
| **[INTEGRATION_API_IA.md](doc/guides/INTEGRATION_API_IA.md)** | 🤖 **NOUVEAU** Intégration IA | 30 min |

**✨ Nouveau** : Guide complet d'intégration des API d'Intelligence Artificielle (Hugging Face, OpenRouter)

### 🎨 README Racine

| Document | Description | Temps Lecture |
|----------|-------------|---------------|
| **[README.md](README.md)** | README original du projet | 5 min |
| **[README_V2.md](README_V2.md)** | README modernisé v2.0 | 10 min |

### ⚙️ Configuration

| Fichier | Description | Lignes |
|---------|-------------|--------|
| **[pom.xml](pom.xml)** | Configuration Maven | 280 |
| **[build.ps1](build.ps1)** | Script de build Windows | 250 |
| **[.gitignore_maven](.gitignore_maven)** | Exclusions Git Maven | 80 |

### 💻 Code Source

| Fichier | Description | Lignes |
|---------|-------------|--------|
| **[src/editeurpanovisu/util/LocalHTMLServer.java](src/editeurpanovisu/util/LocalHTMLServer.java)** | Serveur HTTP embarqué | 332 |
| **[pagesHTML/openstreetmap.html](pagesHTML/openstreetmap.html)** | Carte Leaflet.js | 400+ |

---

## 🗺️ Par Tâche

### Installation et Configuration

1. **Prérequis système**
   - [QUICKSTART_MIGRATION.md > Prérequis](QUICKSTART_MIGRATION.md#prérequis)
   - [PROCHAINES_ETAPES.md > Action 1.1: Installer Java 25](PROCHAINES_ETAPES.md#-action-11--installer-java-25)

2. **Configuration Maven**
   - [pom.xml](pom.xml) - Configuration complète
   - [QUICKSTART_MIGRATION.md > Compilation](QUICKSTART_MIGRATION.md#compilation)

3. **Configuration Git**
   - [PROCHAINES_ETAPES.md > Action 1.2: Créer la Branche](PROCHAINES_ETAPES.md#-action-12--créer-la-branche-git)
   - [.gitignore_maven](.gitignore_maven) - Exclusions

### Migration Technique

1. **Serveur HTTP Embarqué**
   - [LocalHTMLServer.java](src/editeurpanovisu/util/LocalHTMLServer.java) - Code source
   - [MIGRATION_JAVA25_PLAN.md > Phase 3](MIGRATION_JAVA25_PLAN.md#phase-3--serveur-http-embarqué-jour-6-7)
   - [PROCHAINES_ETAPES.md > Action 3.1](PROCHAINES_ETAPES.md#-action-31--intégrer-localhtmlserver)

2. **Google Maps → OpenStreetMap**
   - [openstreetmap.html](pagesHTML/openstreetmap.html) - Carte Leaflet
   - [MIGRATION_JAVA25_PLAN.md > Phase 2](MIGRATION_JAVA25_PLAN.md#phase-2--migration-google-maps--openstreetmap-jour-3-5)
   - [QUICKSTART_MIGRATION.md > API Carte](QUICKSTART_MIGRATION.md#-api-carte-openstreetmap)

3. **Modernisation Java**
   - [MIGRATION_JAVA25_PLAN.md > Phase 4](MIGRATION_JAVA25_PLAN.md#phase-4--migration-java-8--java-25-jour-8-12)
   - [MIGRATION_JAVA25_PLAN.md > Modernisation Syntaxe](MIGRATION_JAVA25_PLAN.md#41-modernisation-de-la-syntaxe)

### Tests et Validation

1. **Tests Unitaires**
   - [MIGRATION_JAVA25_PLAN.md > Phase 5.1](MIGRATION_JAVA25_PLAN.md#51-tests-unitaires)

2. **Tests Fonctionnels**
   - [MIGRATION_JAVA25_PLAN.md > Phase 5.2](MIGRATION_JAVA25_PLAN.md#52-tests-dintégration)
   - [RESUME_MIGRATION.md > Checklist](RESUME_MIGRATION.md#-checklist-de-validation-finale)

3. **Validation Finale**
   - [PROCHAINES_ETAPES.md > Checklist Finale](PROCHAINES_ETAPES.md#-checklist-finale-de-validation)

---

## 🎯 Par Niveau d'Urgence

### 🔴 URGENT - À Faire Maintenant

1. ✅ **[PROCHAINES_ETAPES.md > Action 1.1](PROCHAINES_ETAPES.md#-action-11--installer-java-25)** - Installer Java 25
2. ✅ **[PROCHAINES_ETAPES.md > Action 1.2](PROCHAINES_ETAPES.md#-action-12--créer-la-branche-git)** - Créer branche Git
3. ✅ **[PROCHAINES_ETAPES.md > Action 2.1](PROCHAINES_ETAPES.md#-action-21--tester-maven)** - Premier build

### 🟡 IMPORTANT - Cette Semaine

1. 🔄 **[PROCHAINES_ETAPES.md > Action 3.1](PROCHAINES_ETAPES.md#-action-31--intégrer-localhtmlserver)** - Intégrer LocalHTMLServer
2. 🔄 **[PROCHAINES_ETAPES.md > Action 3.2](PROCHAINES_ETAPES.md#-action-32--mettre-à-jour-aidedialogcontroller)** - Mettre à jour Aide
3. 🔄 **[PROCHAINES_ETAPES.md > Action 3.3](PROCHAINES_ETAPES.md#-action-33--créer-navigateurcarte-moderne)** - NavigateurCarte

### 🟢 NORMAL - Semaine 2-3

1. ⏳ **[MIGRATION_JAVA25_PLAN.md > Phase 4](MIGRATION_JAVA25_PLAN.md#phase-4--migration-java-8--java-25-jour-8-12)** - Modernisation Java
2. ⏳ **[MIGRATION_JAVA25_PLAN.md > Phase 5](MIGRATION_JAVA25_PLAN.md#phase-5--tests-et-validation-jour-13-15)** - Tests complets
3. ⏳ **[MIGRATION_JAVA25_PLAN.md > Phase 6](MIGRATION_JAVA25_PLAN.md#phase-6--documentation-et-déploiement-jour-16-17)** - Documentation finale

---

## 🔧 Par Composant

### Serveur HTTP Embarqué

| Ressource | Type | Lien |
|-----------|------|------|
| Code source | Java | [LocalHTMLServer.java](src/editeurpanovisu/util/LocalHTMLServer.java) |
| Documentation | Guide | [MIGRATION_JAVA25_PLAN.md > Phase 3](MIGRATION_JAVA25_PLAN.md#phase-3--serveur-http-embarqué-jour-6-7) |
| Intégration | Guide | [PROCHAINES_ETAPES.md > Action 3.1](PROCHAINES_ETAPES.md#-action-31--intégrer-localhtmlserver) |
| Tests | Guide | [MIGRATION_JAVA25_PLAN.md > Tests](MIGRATION_JAVA25_PLAN.md#51-tests-unitaires) |

### Carte OpenStreetMap

| Ressource | Type | Lien |
|-----------|------|------|
| Page HTML | HTML/JS | [openstreetmap.html](pagesHTML/openstreetmap.html) |
| API JavaScript | Doc | [README_V2.md > API Carte](README_V2.md#-api-carte-openstreetmap) |
| Migration | Guide | [MIGRATION_JAVA25_PLAN.md > Phase 2](MIGRATION_JAVA25_PLAN.md#phase-2--migration-google-maps--openstreetmap-jour-3-5) |
| Intégration Java | Guide | [PROCHAINES_ETAPES.md > Action 3.3](PROCHAINES_ETAPES.md#-action-33--créer-navigateurcarte-moderne) |

### Build Maven

| Ressource | Type | Lien |
|-----------|------|------|
| Configuration | XML | [pom.xml](pom.xml) |
| Script Windows | PowerShell | [build.ps1](build.ps1) |
| Guide | Doc | [QUICKSTART_MIGRATION.md > Compilation](QUICKSTART_MIGRATION.md#compilation) |
| Commandes | Doc | [README_V2.md > Commandes](README_V2.md#-commandes-utiles) |

---

## 📊 Par Temps Disponible

### ⚡ 15 Minutes

- **[QUICKSTART_MIGRATION.md](QUICKSTART_MIGRATION.md)** - Survol rapide
- **[README_V2.md](README_V2.md)** - Présentation v2.0
- **[PROCHAINES_ETAPES.md > Actions Immédiates](PROCHAINES_ETAPES.md#--todo---actions-immédiates-cette-semaine)** - TODO urgent

### ⏱️ 30 Minutes

- **[QUICKSTART_MIGRATION.md](QUICKSTART_MIGRATION.md)** - Guide complet
- **[RESUME_MIGRATION.md](RESUME_MIGRATION.md)** - Résumé exécutif
- Installation Java 25 + premier build

### ⏳ 1 Heure

- **[MIGRATION_JAVA25_PLAN.md](MIGRATION_JAVA25_PLAN.md)** - Plan complet
- **[PROCHAINES_ETAPES.md](PROCHAINES_ETAPES.md)** - Planning détaillé
- Configuration complète de l'environnement

### 🕐 1 Journée

- Lecture complète de toute la documentation
- Installation + configuration + premier build
- Première intégration (LocalHTMLServer)

---

## 🔍 Recherche Rapide

### Commandes Fréquentes

```powershell
# Build complet
.\build.ps1 full

# Lancer l'application
.\build.ps1 run

# Nettoyer
.\build.ps1 clean

# Aide
.\build.ps1 help
```

### Fichiers Clés à Modifier

1. **EditeurPanovisu.java** - Intégrer LocalHTMLServer
2. **AideDialogController.java** - Changer file:// → http://
3. **NavigateurCarte.java** - Google Maps → Leaflet
4. **CartePanoVisu.java** - Types de cartes OSM

### Erreurs Communes

| Erreur | Solution | Lien |
|--------|----------|------|
| "Java version mismatch" | Installer Java 25 | [QUICKSTART > Dépannage](QUICKSTART_MIGRATION.md#-dépannage) |
| "JavaFX runtime missing" | Utiliser `mvn javafx:run` | [README_V2 > Dépannage](README_V2.md#-dépannage) |
| "Port already in use" | Tuer processus | [QUICKSTART > Dépannage](QUICKSTART_MIGRATION.md#erreur--port-already-in-use) |
| "Cannot load aide.html" | Vérifier serveur HTTP | [PROCHAINES_ETAPES > Action 3.1](PROCHAINES_ETAPES.md#-action-31--intégrer-localhtmlserver) |

---

## 📞 Support

### Documentation Interne
- **[QUICKSTART_MIGRATION.md](QUICKSTART_MIGRATION.md)** - Démarrage rapide
- **[MIGRATION_JAVA25_PLAN.md](MIGRATION_JAVA25_PLAN.md)** - Plan complet
- **[PROCHAINES_ETAPES.md](PROCHAINES_ETAPES.md)** - Actions détaillées

### Liens Externes
- [GitHub Repository](https://github.com/llang57/editeurPanovisu)
- [Java 25 Documentation](https://docs.oracle.com/en/java/javase/25/)
- [JavaFX 21 Documentation](https://openjfx.io/javadoc/21/)
- [Leaflet.js Documentation](https://leafletjs.com/reference.html)
- [Maven Guide](https://maven.apache.org/guides/index.html)

### Communauté
- [GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)
- [StackOverflow - JavaFX](https://stackoverflow.com/questions/tagged/javafx)
- [Forum panoVisu](http://panovisu.fr/hesk/)

---

## 📈 Progression

### Suivi de l'Avancement

Créer un fichier `PROGRESS.md` (voir [PROCHAINES_ETAPES.md > Métriques](PROCHAINES_ETAPES.md#-métriques-de-suivi))

### Checklist Globale

- [ ] **Semaine 1 :** Infrastructure (5 jours)
- [ ] **Semaine 2 :** Migration code (5 jours)
- [ ] **Semaine 3 :** Tests et finalisation (5 jours)

Détails : [PROCHAINES_ETAPES.md > Planning](PROCHAINES_ETAPES.md#-planning-détaillé-3-semaines)

---

## 🎓 Concepts Clés

### Serveur HTTP Embarqué
**Pourquoi ?** WebView moderne bloque `file://` URLs  
**Solution ?** Serveur HTTP local (`http://localhost`)  
**Documentation :** [RESUME_MIGRATION.md > Concepts](RESUME_MIGRATION.md#1-pourquoi-un-serveur-http-embarqué-)

### OpenStreetMap vs Google Maps
**Avantages :** Gratuit, open source, pas de clé API  
**Technologies :** Leaflet.js + tuiles OSM  
**Documentation :** [RESUME_MIGRATION.md > Concepts](RESUME_MIGRATION.md#2-pourquoi-openstreetmap-)

### JavaFX Externe
**Changement :** JavaFX séparé du JDK depuis Java 11  
**Gestion :** Dépendances Maven/Gradle  
**Documentation :** [RESUME_MIGRATION.md > Concepts](RESUME_MIGRATION.md#3-javafx-externe-depuis-java-11)

---

## ✅ Validation Finale

Avant de merger vers `main` :

1. ✅ **Build** → [PROCHAINES_ETAPES.md > Checklist Build](PROCHAINES_ETAPES.md#build-)
2. ✅ **Application** → [PROCHAINES_ETAPES.md > Checklist App](PROCHAINES_ETAPES.md#application-)
3. ✅ **Fonctionnalités** → [PROCHAINES_ETAPES.md > Checklist Fonc](PROCHAINES_ETAPES.md#fonctionnalités-)
4. ✅ **Documentation** → [PROCHAINES_ETAPES.md > Checklist Doc](PROCHAINES_ETAPES.md#documentation-)

---

**Dernière mise à jour :** 10 octobre 2025  
**Version :** 2.0.0-SNAPSHOT  
**Statut :** 🔄 Migration en cours

---

## 🚀 Démarrage Ultra-Rapide (TL;DR)

```powershell
# 1. Installer Java 25
# https://adoptium.net/

# 2. Vérifier
java -version  # Doit afficher "25"

# 3. Build
cd d:\developpement\java\editeurPanovisu
mvn clean compile

# 4. Lire PROCHAINES_ETAPES.md pour la suite
```

**Documentation principale :** [PROCHAINES_ETAPES.md](PROCHAINES_ETAPES.md)
