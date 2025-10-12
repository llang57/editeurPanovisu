# 🚧 Version de Travail - Migration Java 25

**Date** : 11 octobre 2025  
**Statut** : 🚧 EN DÉVELOPPEMENT - Version de travail  
**Build** : ✅ Compilation réussie avec Java 25

---

## ⚠️ AVERTISSEMENT

**Cette version est une version de travail en cours de migration.**

Certaines fonctionnalités ne sont pas encore complètement fonctionnelles.
Ne pas utiliser en production.

---

## ✅ Ce qui fonctionne

### Infrastructure
- ✅ **Java 25** : Migration complète de Java 8 vers Java 25
- ✅ **JavaFX 19.0.2.1** : Remplacement du JavaFX embarqué de Java 8
- ✅ **Maven** : Système de build moderne remplaçant Ant/NetBeans
- ✅ **Compilation** : BUILD SUCCESS sans erreurs

### Fonctionnalités de base
- ✅ **Lancement de l'application** : Interface principale s'ouvre
- ✅ **Visualisation des panoramas** : Affichage des images panoramiques
- ✅ **Éditeur HTML** : Édition de code HTML/CSS/JavaScript
- ✅ **Gestion des projets** : Chargement/sauvegarde des fichiers .pvu

### Nouvelles fonctionnalités
- ✅ **Géolocalisation** : Intégration LocationIQ (API de géocodage)
- ✅ **Configuration centralisée** : ApiKeysConfig.java pour les clés API
- ✅ **Sécurité** : Système de gestion sécurisée des clés API
- ✅ **IA - Hugging Face** : Client pour génération de texte (gpt2)
- ✅ **IA - OpenRouter** : Client pour LLMs (llama-3.2-3b-instruct:free)
- ✅ **Scripts d'audit** : Vérification automatique de la sécurité

---

## 🚧 Ce qui est en cours de développement

### Cartographie
- 🚧 **NavigateurCarteGluon** : Tentative d'intégration Gluon Maps (problèmes de compatibilité)
- 🚧 **OpenLayers 9** : Migration vers la dernière version d'OpenLayers
- 🚧 **Leaflet** : Tests avec plusieurs bibliothèques de cartographie
- ⚠️ **WebView** : Problèmes de communication JavaScript↔Java

### Interface
- 🚧 **Dialogs** : Certaines boîtes de dialogue peuvent avoir des problèmes d'affichage
- 🚧 **Menus contextuels** : À vérifier sur toutes les fonctionnalités

### Fonctionnalités IA
- 🚧 **Interface utilisateur IA** : Pas encore intégrée dans l'interface principale
- 📝 **Tests unitaires IA** : Classes de test créées mais pas encore validées en production

---

## ❌ Problèmes connus

### Technique
1. **WebView JavaScript Bridge** : 
   - Communication JavaScript→Java intermittente
   - Nécessite plus de tests et d'optimisation
   
2. **Gluon Maps** :
   - Erreurs de chargement des tiles OpenStreetMap
   - Problème `org.openjfx.javafx.graphics` non résolu
   
3. **OpenLayers** :
   - Plusieurs tentatives de configuration (OL6, OL9)
   - Stabilisation nécessaire

### Fonctionnel
1. **Carte interactive** : 
   - Affichage parfois incomplet
   - Événements de clic pas toujours capturés
   
2. **Export des projets** :
   - À tester exhaustivement
   
3. **Compatibilité fichiers anciens** :
   - Vérifier la rétrocompatibilité avec les anciens projets .pvu

---

## 📦 Dépendances principales

```xml
<!-- Versions principales -->
<java.version>25</java.version>
<javafx.version>19.0.2.1</javafx.version>
<maven.compiler.release>25</maven.compiler.release>
```

### Bibliothèques clés
- **JavaFX** : 19.0.2.1 (controls, fxml, web, swing, media)
- **Gluon Maps** : 2.0.0-ea+2 (expérimental)
- **OkHttp** : 4.12.0 (clients HTTP pour IA)
- **JSON Simple** : 1.1.1 (parsing JSON)
- **Metadata Extractor** : 2.19.0 (EXIF)

---

## 🔧 Configuration requise pour le développement

### Prérequis
- **Java 25** : OpenJDK Temurin-25+36 (recommandé)
- **Maven** : 3.9+ 
- **Python** : 3.6+ (pour les scripts d'audit)

### Clés API (optionnelles)
Pour utiliser toutes les fonctionnalités, configurer `api-keys.properties` :

```bash
# Copier le template
cp api-keys.properties.template api-keys.properties

# Éditer avec vos clés API
# - LocationIQ : Géolocalisation (gratuit 5000 req/jour)
# - Hugging Face : IA génération de texte (gratuit)
# - OpenRouter : LLMs conversationnels (modèles gratuits disponibles)
```

Voir `doc/SECURITE_CLES_API.md` pour plus d'informations.

---

## 🚀 Commandes de développement

### Compilation
```bash
mvn clean compile
```

### Lancement
```bash
mvn javafx:run
```

### Tests IA (si clés configurées)
```bash
mvn exec:java -Dexec.mainClass="editeurpanovisu.TestAIClients"
```

### Audit de sécurité (avant commit)
```bash
python audit-security.py
```

---

## 📚 Documentation

### Guides disponibles
- **[README.md](README.md)** - Vue d'ensemble du projet
- **[INDEX.md](INDEX.md)** - Index complet de la documentation
- **[doc/migration/](doc/migration/)** - Plans et guides de migration Java 25
- **[doc/guides/](doc/guides/)** - Guides d'intégration (IA, géolocalisation)
- **[doc/travail/](doc/travail/)** - Journaux de développement quotidiens
- **[SECURITE_CLES_API.md](doc/SECURITE_CLES_API.md)** - Gestion sécurisée des clés API

### Scripts utiles
- **`build.ps1`** - Script PowerShell de build Maven
- **`audit-security.py`** - Audit de sécurité des clés API
- **`encode-images.ps1`** - Encodage des images en base64

---

## 🎯 Prochaines étapes

### Priorité 1 - Stabilisation
- [ ] Résoudre les problèmes de WebView JavaScript Bridge
- [ ] Stabiliser la cartographie (choisir entre Leaflet/OpenLayers/Gluon)
- [ ] Tester exhaustivement toutes les fonctionnalités existantes
- [ ] Valider la rétrocompatibilité avec les anciens projets

### Priorité 2 - Intégration IA
- [ ] Tester les clients IA avec de vraies clés API
- [ ] Créer l'interface utilisateur pour les fonctionnalités IA
- [ ] Implémenter "Générer une description" pour les panoramas
- [ ] Implémenter "Suggérer des hotspots"
- [ ] Ajouter un assistant conversationnel

### Priorité 3 - Documentation
- [ ] Documenter toutes les nouvelles fonctionnalités
- [ ] Créer des tutoriels vidéo
- [ ] Mettre à jour le guide utilisateur

### Priorité 4 - Tests
- [ ] Tests unitaires pour les nouvelles classes
- [ ] Tests d'intégration
- [ ] Tests de performance
- [ ] Tests de sécurité (clés API)

---

## 🤝 Contribution

Ce projet est en cours de migration active.

**Si vous souhaitez contribuer** :
1. Consultez les issues GitHub
2. Testez les fonctionnalités et rapportez les bugs
3. Proposez des améliorations via Pull Requests

**Avant tout commit** :
```bash
# Toujours exécuter l'audit de sécurité
python audit-security.py
```

---

## 📄 Licence

Voir le fichier [LICENSE](LICENSE) pour plus d'informations.

---

## 🐛 Rapporter un bug

Pour rapporter un bug, créez une issue GitHub avec :
- Version de Java utilisée
- Système d'exploitation
- Description détaillée du problème
- Logs d'erreur (si disponibles)
- Étapes pour reproduire

---

**Dernière mise à jour** : 11 octobre 2025  
**Version** : 0.9.0-SNAPSHOT (Java 25 WIP)  
**Status** : 🚧 Work In Progress
