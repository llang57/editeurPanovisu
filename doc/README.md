# 📚 Documentation EditeurPanovisu

**Date de mise à jour :** 11 octobre 2025  
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
- **[PROCHAINES_ETAPES.md](migration/PROCHAINES_ETAPES.md)** - Étapes futures et améliorations prévues

### 🗺️ [geolocalisation/](geolocalisation/)
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

### 🏗️ [architecture/](architecture/)
Documentation de l'architecture du projet *(à venir)*

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
- **JavaFX 19.0.2.1** : ✅ Intégré avec succès (downgrade depuis 23.0.1)
- **Gluon Maps 2.0.0-ea+6** : ✅ Alternative fonctionnelle
- **Leaflet.js 1.9.4** : ✅ Intégration WebView principale
- **Géolocalisation LocationIQ** : ✅ API gratuite (5000 req/jour)
- **Recherche d'adresse** : ✅ Avec centrage automatique
- **Marqueur déplaçable** : ✅ Mise à jour automatique des coordonnées
- **Basculement carte/satellite** : ✅ Bidirectionnel
- **Validation des coordonnées** : ✅ Récupération correcte

### 🔧 Dépendances Principales

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>19.0.2.1</version>
</dependency>

<dependency>
    <groupId>com.gluonhq</groupId>
    <artifactId>maps</artifactId>
    <version>2.0.0-ea+6</version>
</dependency>
```

### 🔑 Configuration Requise

**Fichier `src/api-keys.properties` :**
```properties
locationiq.api.key=pk.0f147952a41c555a5b70614039fd148b
```

---

## 🎯 Problèmes Résolus Récemment

### 1. TextFields non visibles
- **Solution** : Ajout de Labels et prompts explicites
- **Statut** : ✅ Résolu

### 2. Erreur "For input string: {JSON}"
- **Solution** : Priorité à l'objet `marqueur` mis à jour par JavaScript
- **Statut** : ✅ Résolu

### 3. Bouton radio unique carte/satellite
- **Solution** : Fonction `getNomsLayers()` retournant le bon format avec `|` et `*`
- **Statut** : ✅ Résolu

### 4. Coordonnées toujours à Metz
- **Solution** : Flag `marqueurMisAJourParJS` pour distinction marqueur initial/mis à jour
- **Statut** : ✅ Résolu

---

## 📞 Support et Contact

Pour toute question ou problème, consulter les guides de debug dans le dossier `debug/`.

---

## 📝 Historique des Modifications

| Date | Version | Modifications |
|------|---------|---------------|
| 11/10/2025 | 2.0.0-SNAPSHOT | Correctif marqueur Metz + flag `marqueurMisAJourParJS` |
| 11/10/2025 | 2.0.0-SNAPSHOT | Corrections géolocalisation (3 problèmes) |
| 10/10/2025 | 2.0.0-SNAPSHOT | Intégration LocationIQ + marqueur déplaçable |
| 09/10/2025 | 2.0.0-SNAPSHOT | Migration Java 25 + JavaFX 19.0.2.1 complète |

---

**Dernière mise à jour :** 11 octobre 2025, 08:45
