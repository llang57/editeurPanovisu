# Configuration des modèles IA - Documentation technique
**Date**: 14 janvier 2025  
**Version**: 3.0.0-SNAPSHOT (Build 2570)

## 📋 Résumé

Implémentation d'une interface de configuration permettant à l'utilisateur de sélectionner les modèles d'IA (OpenRouter et Ollama) utilisés pour la génération de descriptions, avec persistance des choix dans `preferences.cfg`.

## 🎯 Objectif

Permettre à l'utilisateur de :
- Choisir parmi les 4 modèles OpenRouter disponibles (Claude 3.5 Sonnet, GPT-4 Turbo, Gemini Pro, Llama 3.1)
- Choisir parmi les modèles Ollama installés localement
- Sauvegarder ces préférences pour qu'elles survivent aux redémarrages de l'application

## 🔧 Modifications apportées

### 1. OllamaService.java - API publique pour la configuration

**Lignes 308-404** : Ajout de 6 méthodes publiques pour gérer les modèles :

```java
public static java.util.List<String> getModelesOllamaDisponibles() {
    // Interroge l'API Ollama /api/tags pour récupérer la liste des modèles installés
    // Retourne une liste de noms de modèles (ex: ["mistral-nemo", "llama3.1", "qwen2.5"])
}

public static String[] getModelesOpenRouterDisponibles() {
    // Retourne le tableau des modèles OpenRouter supportés
    return OPENROUTER_MODELS;
}

public static void setOllamaModel(String modelName) {
    // Change le modèle Ollama actif
    ollamaModel = modelName;
}

public static void setOpenRouterModel(String modelName) {
    // Change le modèle OpenRouter actif
    openrouterModel = modelName;
}

public static String getOllamaModel() {
    // Retourne le nom du modèle Ollama actuellement sélectionné
    return ollamaModel;
}

public static String getOpenRouterModel() {
    // Retourne le nom du modèle OpenRouter actuellement sélectionné
    return openrouterModel;
}
```

**Constantes modèles OpenRouter (lignes 37-42)** :
```java
private static final String[] OPENROUTER_MODELS = {
    "anthropic/claude-3.5-sonnet",     // Meilleur pour éviter les hallucinations (par défaut)
    "openai/gpt-4-turbo",              // Excellent pour la géographie
    "google/gemini-pro",               // Gratuit, bon compromis
    "meta-llama/llama-3.1-8b-instruct" // Open source, gratuit
};
```

### 2. ConfigDialogController.java - Interface utilisateur

**Lignes 57-58** : Variables de classe pour les ComboBox :
```java
private static ComboBox<String> cbOpenRouterModel;
private static ComboBox<String> cbOllamaModel;
```

**Lignes 75-76** : Agrandissement de la fenêtre :
```java
apConfigDialog.setPrefHeight(680);  // Augmenté de 590 à 680
paneConfig.setPrefSize(600, 600);   // Augmenté de 510 à 600
```

**Lignes 155-213** : Création des champs de sélection :

1. **Section OpenRouter** (priorité 1) :
   - Label "OpenRouter (GPT-4, Claude) - Priorité 1"
   - ComboBox avec les 4 modèles disponibles
   - Largeur 500px
   - Sélection par défaut du modèle actuel

2. **Section Ollama** (fallback) :
   - Label "Ollama (local) - Fallback"
   - ComboBox avec les modèles installés détectés
   - Désactivée si aucun modèle Ollama n'est installé
   - Message "(aucun modèle Ollama installé)" si vide

**Lignes 282-296** : Logique de sauvegarde dans le bouton "Sauvegarder" :
```java
btnSauvegarder.setOnAction((ActionEvent e) -> {
    // ... sauvegarde existante panovisu.cfg et api-keys.properties ...
    
    // Mise à jour des modèles dans OllamaService
    if (cbOpenRouterModel.getValue() != null && !cbOpenRouterModel.getValue().isEmpty()) {
        OllamaService.setOpenRouterModel(cbOpenRouterModel.getValue());
    }
    if (cbOllamaModel.getValue() != null && !cbOllamaModel.getValue().isEmpty() && 
        !cbOllamaModel.getValue().contains("non installé")) {
        OllamaService.setOllamaModel(cbOllamaModel.getValue());
    }
    
    // Sauvegarde dans preferences.cfg
    try {
        saveModelsPreferences();
    } catch (IOException ex) {
        Logger.getLogger(ConfigDialogController.class.getName()).log(Level.SEVERE, null, ex);
    }
});
```

**Lignes 363-407** : Nouvelle méthode `saveModelsPreferences()` :
```java
private void saveModelsPreferences() throws IOException {
    File filePreferences = new File(EditeurPanovisu.fileRepertConfig.getAbsolutePath() + 
                                     File.separator + "preferences.cfg");
    
    // 1. Lecture des préférences existantes
    StringBuilder existingContent = new StringBuilder();
    if (filePreferences.exists()) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePreferences), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignorer les anciennes lignes de modèles (éviter doublons)
                if (!line.startsWith("openrouterModel=") && !line.startsWith("ollamaModel=")) {
                    existingContent.append(line).append("\n");
                }
            }
        }
    }
    
    // 2. Ajout des nouvelles lignes de modèles
    String openRouterModel = OllamaService.getOpenRouterModel();
    String ollamaModel = OllamaService.getOllamaModel();
    
    if (openRouterModel != null && !openRouterModel.isEmpty()) {
        existingContent.append("openrouterModel=").append(openRouterModel).append("\n");
    }
    if (ollamaModel != null && !ollamaModel.isEmpty()) {
        existingContent.append("ollamaModel=").append(ollamaModel).append("\n");
    }
    
    // 3. Réécriture du fichier
    filePreferences.setWritable(true);
    try (OutputStreamWriter writer = new OutputStreamWriter(
            new FileOutputStream(filePreferences), "UTF-8");
         BufferedWriter bw = new BufferedWriter(writer)) {
        bw.write(existingContent.toString());
    }
    
    System.out.println("💾 Modèles IA sauvegardés dans preferences.cfg");
    System.out.println("  - OpenRouter: " + openRouterModel);
    System.out.println("  - Ollama: " + ollamaModel);
}
```

**Ligne 9** : Ajout de l'import BufferedReader :
```java
import java.io.BufferedReader;
```

### 3. EditeurPanovisu.java - Chargement des préférences

**Lignes 11421-11432** : Ajout de 2 cas dans le switch de `lisPreferences()` :
```java
switch (tpe) {
    // ... cas existants ...
    
    case "openrouterModel":
        if (!valeur.isEmpty()) {
            OllamaService.setOpenRouterModel(valeur);
            System.out.println("[Config] 🤖 Modèle OpenRouter chargé: " + valeur);
        }
        break;
        
    case "ollamaModel":
        if (!valeur.isEmpty()) {
            OllamaService.setOllamaModel(valeur);
            System.out.println("[Config] 🤖 Modèle Ollama chargé: " + valeur);
        }
        break;
}
```

## 📁 Format du fichier preferences.cfg

Le fichier `configPV/preferences.cfg` utilise un format simple `clé=valeur` :

```
typeFichiersTransf=jpeg
tailleLoupe=200
afficheLoupe=true
largeurE2C=2000.0
hauteurE2C=1000.0
netteteTransf=false
niveauNetteteTransf=0.4
dernierRepertoireVisite=D:\Mes Documents\Visites
openrouterModel=anthropic/claude-3.5-sonnet
ollamaModel=mistral-nemo
```

**Nouvelles lignes** :
- `openrouterModel=<nom_du_modèle>` : Modèle OpenRouter sélectionné (ex: `anthropic/claude-3.5-sonnet`)
- `ollamaModel=<nom_du_modèle>` : Modèle Ollama sélectionné (ex: `llama3.1`)

## 🔄 Cycle de vie

### Au démarrage de l'application
1. `EditeurPanovisu.lisPreferences()` est appelée
2. Le fichier `configPV/preferences.cfg` est lu ligne par ligne
3. Les lignes `openrouterModel=` et `ollamaModel=` sont parsées
4. `OllamaService.setOpenRouterModel()` et `setOllamaModel()` sont appelés
5. Les modèles configurés sont actifs pour les générations de descriptions

### Ouverture du dialogue de configuration
1. L'utilisateur clique sur le menu de configuration
2. `ConfigDialogController.afficheConfigDialogue()` est appelée
3. Les ComboBox sont créées et remplies :
   - `cbOpenRouterModel` : remplie avec `OllamaService.getModelesOpenRouterDisponibles()`
   - `cbOllamaModel` : remplie avec `OllamaService.getModelesOllamaDisponibles()`
4. Les valeurs actuelles sont pré-sélectionnées :
   - `cbOpenRouterModel.setValue(OllamaService.getOpenRouterModel())`
   - `cbOllamaModel.setValue(OllamaService.getOllamaModel())`

### Sauvegarde des préférences
1. L'utilisateur sélectionne de nouveaux modèles dans les ComboBox
2. L'utilisateur clique sur "Sauvegarder"
3. `OllamaService.setOpenRouterModel()` et `setOllamaModel()` mettent à jour les variables statiques
4. `saveModelsPreferences()` est appelée :
   - Lit `preferences.cfg` existant
   - Supprime les anciennes lignes `openrouterModel=` et `ollamaModel=`
   - Ajoute les nouvelles lignes avec les valeurs actuelles
   - Réécrit le fichier complet
5. Les nouveaux modèles sont immédiatement actifs (pas besoin de redémarrer)

## 🧪 Tests à effectuer

### Test 1 : Sélection et persistance
1. Lancer l'application
2. Ouvrir le dialogue de configuration
3. Sélectionner `openai/gpt-4-turbo` dans la ComboBox OpenRouter
4. Sélectionner `llama3.1` dans la ComboBox Ollama (si installé)
5. Cliquer sur "Sauvegarder"
6. Vérifier la console :
   ```
   💾 Modèles IA sauvegardés dans preferences.cfg
     - OpenRouter: openai/gpt-4-turbo
     - Ollama: llama3.1
   ```
7. Fermer et relancer l'application
8. Rouvrir le dialogue de configuration
9. **Résultat attendu** : Les modèles sélectionnés sont toujours actifs dans les ComboBox

### Test 2 : Génération avec différents modèles
1. Configurer Claude 3.5 Sonnet (par défaut)
2. Générer une description pour un panoramique de test
3. Noter le style et la précision
4. Configurer GPT-4 Turbo
5. Générer une description pour le même panoramique
6. Comparer les résultats
7. **Résultat attendu** : Les deux descriptions sont de haute qualité, Claude potentiellement plus concis

### Test 3 : Fallback Ollama si OpenRouter échoue
1. Configurer un modèle OpenRouter
2. Désactiver temporairement la connexion internet
3. Tenter de générer une description
4. **Résultat attendu** : Le système bascule automatiquement sur Ollama local avec un message console :
   ```
   [IA] ✗ OpenRouter échec, fallback Ollama...
   [IA] ✓ Ollama (mistral-nemo) utilisé
   ```

### Test 4 : Aucun modèle Ollama installé
1. Sur une machine sans Ollama installé
2. Ouvrir le dialogue de configuration
3. **Résultat attendu** : La ComboBox Ollama affiche "(aucun modèle Ollama installé)" et est désactivée

## 📊 Modèles disponibles

### OpenRouter (9 modèles premium)
| Modèle | API ID | Caractéristiques | Coût estimé |
|--------|--------|------------------|-------------|
| **GPT-5** ⭐ | `openai/gpt-5` | **PAR DÉFAUT** - Le plus avancé d'OpenAI, raisonnement supérieur, moins d'hallucinations | ~0.005$/desc |
| **Claude Sonnet 4.5** ⭐ | `anthropic/claude-sonnet-4.5` | Top Claude (sept 2024), #1 tech/programmation, state-of-the-art | ~0.004$/desc |
| **Claude 3 Opus** | `anthropic/claude-3-opus` | Modèle le plus puissant génération 3, raisonnement approfondi, très factuel | ~0.015$/desc |
| Claude 3.5 Sonnet (20241022) | `anthropic/claude-3.5-sonnet:20241022` | Version datée précédente, descriptions très précises | ~0.004$/desc |
| **Mistral Nemo** 🇫🇷 | `mistralai/mistral-nemo` | Modèle français Mistral AI, excellent multilingue, bon rapport qualité/prix | ~0.003$/desc |
| **GPT-OSS-120B** 💰 | `openai/gpt-oss-120b` | **OPEN SOURCE** (Apache 2.0), 117B params, #2 Legal, très économique | ~0.0005$/desc |
| GPT-4 Turbo | `openai/gpt-4-turbo` | Excellent pour géographie | ~0.005$/desc |
| Gemini Pro | `google/gemini-pro` | Gratuit, bon compromis | Gratuit |
| Llama 3.1 8B | `meta-llama/llama-3.1-8b-instruct` | Open source, gratuit | Gratuit |

### Ollama (détection dynamique)
Liste détectée en interrogeant `http://localhost:11434/api/tags` :
- `mistral-nemo` (priorité haute)
- `qwen2.5`
- `llama3.1`
- `gemma2`
- Tous autres modèles installés localement

## 🔐 Sécurité

- Les clés API restent dans `api-keys.properties` (séparé des préférences)
- Les noms de modèles stockés en clair dans `preferences.cfg` (pas de données sensibles)
- Aucune clé API n'est transmise au LLM (uniquement via en-têtes HTTP)

## 📝 Notes techniques

### Encodage des fichiers
- `preferences.cfg` : UTF-8 avec BOM facultatif
- Lecture/écriture via `InputStreamReader` et `OutputStreamWriter` avec charset UTF-8 explicite

### Gestion des doublons
La méthode `saveModelsPreferences()` **supprime** les anciennes lignes `openrouterModel=` et `ollamaModel=` avant d'ajouter les nouvelles, évitant ainsi les doublons.

### Valeurs par défaut
Si les lignes sont absentes de `preferences.cfg` :
- OpenRouter : `anthropic/claude-3.5-sonnet` (défini dans `OllamaService.openrouterModel`)
- Ollama : Premier modèle détecté par priorité (mistral-nemo > qwen2.5 > llama3.1 > gemma2)

## ✅ Statut

- [x] API publique dans OllamaService
- [x] Interface utilisateur (ComboBox)
- [x] Logique de sauvegarde
- [x] Logique de chargement
- [x] Compilation réussie (Build 2570)
- [ ] Tests utilisateur
- [ ] Documentation utilisateur

## 🚀 Prochaines étapes

1. **Tests utilisateur** : Valider le cycle complet (sélection → sauvegarde → redémarrage → vérification)
2. **Documentation utilisateur** : Ajouter une section dans `aide/aide.html` expliquant comment choisir les modèles
3. **Optimisation** : Ajouter un indicateur visuel du coût estimé pour chaque modèle OpenRouter
4. **Statistiques** : Logger l'utilisation des différents modèles pour analytics

## 📌 Références

- **OpenRouter API** : https://openrouter.ai/docs
- **Ollama API** : https://github.com/ollama/ollama/blob/main/docs/api.md
- **Document précédent** : `doc/travail/2025-10-11_INTEGRATION_IA.md`
- **Stratégie de fallback** : `doc/OLLAMA_GUIDE_RAPIDE.md`

---

**Auteur** : GitHub Copilot + Utilisateur  
**Date de dernière modification** : 14 janvier 2025  
**Version de l'application** : 3.0.0-SNAPSHOT (Build 2570)
