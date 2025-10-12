# 📚 Documentation EditeurPanovisu

**Date de mise à jour :** 12 octobre 2025  
**Version :** 2.0.0-SNAPSHOT  
**Java :** 25 (OpenJDK Temurin-25+36)  
**JavaFX :** 19.0.2.1

---

## 📂 Structure de la Documentation

### 🔄 [migration/](migration/)
Documentation relative à la migration vers Java 25 et JavaFX 19.0.2.1

- **[MIGRATION_JAVA25_PLAN.md](migration/MIGRATION_JAVA25_PLAN.md)** - Plan détaillé de migration vers Java 25
- **[MEMO_MIGRATION.md](migration/MEMO_MIGRATION.md)** - Notes et mémos de la migration
- **[QUICKSTART_MIGRATION.md](migration/QUICKSTART_MIGRATION.md)** - Guide rapide de migration
- **[RESUME_MIGRATION.md](migration/RESUME_MIGRATION.md)** - Résumé de la migration effectuée
- **[MIGRATION_COMPLETED.md](migration/MIGRATION_COMPLETED.md)** - Migration complète vers Java 25
- **[PROCHAINES_ETAPES.md](migration/PROCHAINES_ETAPES.md)** - Étapes futures et améliorations prévues

### � [installation/](installation/)
Guides d'installation et de déploiement

- **[INSTALLATION_GUIDE.md](installation/INSTALLATION_GUIDE.md)** - Guide complet d'installation pour développeurs
  - Solution au problème de lancement jpackage
  - Architecture batch + VBS + Inno Setup
  - Procédure de build automatisée
  
- **[INSTALLATION_UTILISATEUR.md](installation/INSTALLATION_UTILISATEUR.md)** - Guide d'installation pour utilisateurs finaux
  - Installation simple par double-clic
  - Configuration post-installation
  
- **[BUILD_EXE_GUIDE.md](installation/BUILD_EXE_GUIDE.md)** - Guide de création de l'installeur Windows
  - Utilisation de build-installer.ps1
  - Génération de l'installeur Inno Setup

### �🗺️ [geolocalisation/](geolocalisation/)
Documentation de la fonctionnalité de géolocalisation avec Leaflet.js et LocationIQ

- **[CORRECTIONS_GEOLOCALISATION.md](geolocalisation/CORRECTIONS_GEOLOCALISATION.md)** - Corrections des 3 problèmes principaux
  - Affichage des coordonnées dans les TextFields
  - Erreur "For input string" lors de la validation
  - Basculement bidirectionnel carte/satellite
  
- **[CORRECTIF_MARQUEUR_METZ.md](geolocalisation/CORRECTIF_MARQUEUR_METZ.md)** - Correctif du bug du marqueur Metz
  - Problème : Les coordonnées retournées étaient toujours celles de Metz
  - Solution : Flag `marqueurMisAJourParJS` pour distinguer marqueur initial vs. mis à jour

### 🐛 [debug/](debug/)
Guides de débogage et diagnostics

- **[GUIDE_DEBUG_GEOLOCALISATION.md](debug/GUIDE_DEBUG_GEOLOCALISATION.md)** - Guide de débogage de la géolocalisation
  - Panel de debug visible dans le HTML
  - Logs Java détaillés
  - Vérification du pont JavaScript-Java

### 📖 [guides/](guides/)
Guides d'utilisation, configuration et tutoriels

- **[CONFIGURATION_API_KEYS.md](guides/CONFIGURATION_API_KEYS.md)** - Guide complet de configuration des clés API
  - Configuration rapide (3 minutes)
  - Obtention des clés pour chaque service (LocationIQ, Bing Maps, OpenWeather, Mapbox)
  - Sécurité et bonnes pratiques
  - Dépannage des problèmes courants
  
- **[API_KEYS_README.md](guides/API_KEYS_README.md)** - README simplifié pour les clés API
  - Instructions de base
  - Configuration minimale
  
- **[INTEGRATION_API_IA.md](guides/INTEGRATION_API_IA.md)** - Guide d'intégration des API d'IA
  - Configuration OpenAI, Claude, Gemini
  - Utilisation dans l'éditeur
  
- **[RESUME_INTEGRATION_API_IA.md](guides/RESUME_INTEGRATION_API_IA.md)** - Résumé de l'intégration IA
- **[RESUME_CONFIGURATION_API.md](guides/RESUME_CONFIGURATION_API.md)** - Résumé configuration API

### 💻 [development/](development/)
Documentation pour les développeurs

- **[NOTES_VERSION_JAVA25.md](development/NOTES_VERSION_JAVA25.md)** - Notes spécifiques à Java 25
- **[CSS_MODERNIZATION_2025.md](development/CSS_MODERNIZATION_2025.md)** - Modernisation du CSS
- **[FLAT_TITLEDPANE_2025.md](development/FLAT_TITLEDPANE_2025.md)** - Composants JavaFX personnalisés
- **[VERSIONING.md](development/VERSIONING.md)** - Gestion des versions et numéros de build
- **[AUDIT_SECURITY_README.md](development/AUDIT_SECURITY_README.md)** - Audit de sécurité du code

### 🏗️ [travail/](travail/)
Notes de travail et sessions de développement

- **[TRAVAIL_2025-10-11_VERSIONING.md](travail/TRAVAIL_2025-10-11_VERSIONING.md)** - Session versioning

---

## 🚀 Démarrage Rapide

### Compilation
```powershell
mvn clean compile
```

### Lancement de l'application
```powershell
mvn javafx:run
```

### Exécution des tests
```powershell
mvn test
```

---

## 📋 État Actuel du Projet

### ✅ Fonctionnalités Opérationnelles

- **Migration Java 25** : ✅ Complète et fonctionnelle
- **JavaFX 19.0.2.1** : ✅ Intégré avec succès
- **Migration OpenLayers** : ✅ Suppression Google Maps et Bing Maps
- **Cartes ESRI ArcGIS** : ✅ World Street Map, World Imagery, World Topo Map
- **OpenStreetMap** : ✅ Intégration complète
- **Géolocalisation** : ✅ Fonctionnelle
- **Installeur Windows** : ✅ Inno Setup automatisé (168 MB)
- **Déploiement** : ✅ Installation sans droits admin (AppData\Local)

### 🔧 Dépendances Principales

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>19.0.2.1</version>
</dependency>

<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-web</artifactId>
    <version>19.0.2.1</version>
</dependency>
```

### � Outils de Build et Déploiement

- **Maven 3.9.9** : Build automation
- **jpackage** : Création de l'app-image avec runtime Java embarqué
- **Inno Setup 6.5.4** : Création de l'installeur Windows
- **Script PowerShell** : `build-installer.ps1` pour automatisation complète

---

## 🎯 Problèmes Résolus Récemment

### 1. Lancement de l'application depuis l'installeur Windows (12/10/2025)
- **Problème** : "Failed to launch JVM" au double-clic sur l'EXE jpackage
- **Cause** : Répertoire de travail incorrect (root au lieu de app/)
- **Solution** : Launcher batch + VBS + raccourcis Inno Setup personnalisés
- **Statut** : ✅ Résolu - Installeur automatique de 168 MB

### 2. Migration OpenLayers - Suppression Google Maps et Bing Maps (12/10/2025)
- **Problème** : Dépendances aux API propriétaires Google et Bing
- **Solution** : Migration vers ESRI ArcGIS REST API (World Street Map, Imagery, Topo)
- **Services supprimés** : Google Maps, Google+, Bing Maps, clés API Bing
- **Statut** : ✅ Résolu - OpenLayers utilise maintenant OpenStreetMap et ESRI

### 3. Coordonnées toujours à Metz (11/10/2025)
- **Solution** : Flag `marqueurMisAJourParJS` pour distinction marqueur initial/mis à jour
- **Statut** : ✅ Résolu

### 4. Géolocalisation (11/10/2025)
- **Solutions** : TextFields visibles, validation correcte, basculement bidirectionnel
- **Statut** : ✅ Résolu

---

## 📞 Support et Contact

Pour toute question ou problème, consulter les guides de debug dans le dossier `debug/`.

---

## � Build et Installation

### Build de l'installeur Windows
```powershell
.\build-installer.ps1
```

**Génère** :
- `target\installer\EditeurPanovisu-Setup-2.0.0.exe` (168.42 MB)
- Inclut le runtime Java 25 complet
- Installation sans droits administrateur
- Raccourcis automatiques (Bureau + Menu Démarrer)

### Test en développement
```powershell
mvn clean javafx:run
```

---

## �📝 Historique des Modifications

| Date | Version | Modifications |
|------|---------|---------------|
| 12/10/2025 | 2.0.0-SNAPSHOT | ✅ Installeur Inno Setup automatisé (168 MB) |
| 12/10/2025 | 2.0.0-SNAPSHOT | ✅ Migration OpenLayers : suppression Google/Bing, ajout ESRI |
| 11/10/2025 | 2.0.0-SNAPSHOT | Correctif marqueur Metz + flag `marqueurMisAJourParJS` |
| 11/10/2025 | 2.0.0-SNAPSHOT | Corrections géolocalisation (3 problèmes) |
| 10/10/2025 | 2.0.0-SNAPSHOT | Intégration LocationIQ + marqueur déplaçable |
| 09/10/2025 | 2.0.0-SNAPSHOT | Migration Java 25 + JavaFX 19.0.2.1 complète |

---

**Dernière mise à jour :** 12 octobre 2025
