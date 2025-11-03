# 🚀 Notes de version - EditeurPanovisu v3.4.0

**Date de release** : 3 novembre 2025  
**Build** : 3674+  
**Priorité** : 🤖 Configuration dynamique des modèles IA + 🎨 Améliorations UI

---

## ✨ Nouveautés majeures

### 🤖 Configuration dynamique des modèles IA

**Système de configuration JSON externe pour les modèles IA**
- Fichiers JSON éditables : `configPV/openrouter-models.json` et `configPV/ollama-models.json`
- Vérification automatique de la disponibilité des modèles au démarrage via API
- Interface graphique complète accessible via **Ctrl+M** ou menu **Affichage > Configuration des modèles IA**
- Modification des modèles sans recompilation du programme

**Interface de configuration**
- Deux onglets : OpenRouter (cloud) et Ollama (local)
- Tableaux éditables avec 7 colonnes par fournisseur
- Activation/désactivation des modèles avec checkboxes
- Réordonnancement par **drag & drop** pour définir les priorités
- Boutons de rafraîchissement pour vérifier la disponibilité en temps réel

**Dialogues d'ajout enrichis**
- Formulaires complets avec 5 champs :
  - ID du modèle (avec exemples)
  - Nom d'affichage
  - Description
  - Prix/Taille selon le type
  - Qualité (1-5 étoiles)
- Liens cliquables vers la documentation officielle :
  - OpenRouter : https://openrouter.ai/models
  - Ollama : https://ollama.ai/library
- Ouverture automatique du navigateur (détection Firefox, Chrome, Chromium, etc.)
- Fallback avec dialogue de copie d'URL si le navigateur ne peut pas être ouvert

**Amélioration de la fiabilité**
- Détection précoce des modèles invalides au démarrage
- 9/9 modèles OpenRouter vérifiés et disponibles
- 5/5 modèles Ollama détectés et disponibles
- Cache de vérification configurable (durée en secondes)
- Logs détaillés pour le débogage

---

## 🔧 Corrections et améliorations

### Interface utilisateur
- ✅ Dialogues d'ajout plus explicites avec aide contextuelle
- ✅ Icônes et emojis pour meilleure lisibilité
- ✅ Feedback visuel pendant la vérification API (indicateurs de progression)
- ✅ Bouton "Copier le lien" dans les dialogues d'erreur

### Compatibilité multi-plateforme
- ✅ Ouverture de liens compatible Linux/Windows/macOS
- ✅ Détection automatique des navigateurs installés (Firefox, Chrome, Chromium, Brave, Vivaldi, Opera)
- ✅ Fallback sur `xdg-open` (Linux), `open` (macOS), `cmd /c start` (Windows)
- ✅ Gestion des erreurs avec dialogue informatif

### Backend
- ✅ Dépendance Gson 2.11.0 ajoutée pour la sérialisation JSON
- ✅ Classes `ModelConfig` et `ModelConfigManager` pour la gestion des configurations
- ✅ Intégration dans `OllamaService` avec initialisation statique
- ✅ Méthodes de sauvegarde avec formatage JSON lisible (pretty-print)

---

## 📦 Fichiers créés

### Configuration
- `configPV/openrouter-models.json` - Configuration des modèles OpenRouter (10 modèles)
- `configPV/ollama-models.json` - Configuration des modèles Ollama (5 modèles)

### Code source
- `src/editeurpanovisu/config/ModelConfig.java` - Classe de données pour les modèles
- `src/editeurpanovisu/config/ModelConfigManager.java` - Gestionnaire des configurations
- `src/editeurpanovisu/ModelsConfig.fxml` - Interface FXML (900x700px)
- `src/editeurpanovisu/ModelsConfigController.java` - Contrôleur JavaFX (785 lignes)

### Modifications
- `src/editeurpanovisu/OllamaService.java` - Intégration de la vérification
- `src/editeurpanovisu/EditeurPanovisu.java` - Ajout du menu et du dialogue
- `pom.xml` - Ajout de la dépendance Gson

---

## 🎯 Utilisation

### Accéder à la configuration des modèles
1. **Via le menu** : Affichage > Configuration des modèles IA...
2. **Via le raccourci** : `Ctrl+M`

### Modifier la priorité des modèles
- **Glisser-déposer** : Cliquer et maintenir sur une ligne, puis déplacer vers la position souhaitée
- **Édition manuelle** : Double-cliquer sur le champ "Priorité" et entrer un nombre

### Ajouter un nouveau modèle
1. Cliquer sur le bouton **"Ajouter"** dans l'onglet correspondant
2. Cliquer sur le **lien bleu** pour consulter la documentation officielle
3. Remplir le formulaire avec les informations du modèle
4. Cliquer sur **"Ajouter"** pour confirmer

### Vérifier la disponibilité
- Cliquer sur **"Vérifier maintenant"** pour interroger l'API en temps réel
- Les modèles disponibles affichent **✓**, les indisponibles **✗**

### Sauvegarder les modifications
1. Cliquer sur **"Sauvegarder"**
2. Les modifications sont écrites dans les fichiers JSON
3. **Relancer l'application** pour appliquer les changements

---

## 🐛 Problèmes connus et solutions

### Le navigateur ne s'ouvre pas automatiquement
**Symptôme** : Clic sur un lien ne lance pas le navigateur

**Solutions** :
1. Le système affiche un dialogue avec l'URL et un bouton "Copier le lien"
2. Vérifier que `xdg-open` est installé sur Linux : `sudo apt install xdg-utils`
3. Définir le navigateur par défaut : `xdg-settings set default-web-browser firefox.desktop`

### Les modèles ne sont pas vérifiés au démarrage
**Symptôme** : Tous les modèles affichent "✗" alors qu'ils sont disponibles

**Solutions** :
1. Vérifier que `verifyAtStartup` est à `true` dans les fichiers JSON
2. Vérifier que le token OpenRouter est configuré dans `api-keys.properties`
3. Vérifier qu'Ollama est lancé : `systemctl status ollama` ou `ollama serve`

---

## 📊 Statistiques de la release

- **Lignes de code ajoutées** : ~1500
- **Fichiers créés** : 6
- **Fichiers modifiés** : 5
- **Tests effectués** : Vérification API, UI drag & drop, ouverture navigateur
- **Builds depuis v3.3.3** : 3674 → 3674+ (52 builds)

---

## 🙏 Remerciements

Merci aux utilisateurs pour leurs retours et suggestions qui ont permis de créer cette interface de configuration intuitive et complète.

---

## 🔗 Liens utiles

- **Documentation OpenRouter** : https://openrouter.ai/models
- **Documentation Ollama** : https://ollama.ai/library
- **Dépôt GitHub** : https://github.com/llang57/editeurPanovisu
- **Guide d'installation** : voir `INSTALLATION.md`

---

**Note** : Cette version nécessite Java 25 ou supérieur.
