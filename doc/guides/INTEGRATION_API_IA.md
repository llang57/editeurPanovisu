# 🤖 Intégration des API d'Intelligence Artificielle

Guide complet pour l'utilisation des clients API IA dans EditeurPanovisu.

---

## 📋 Table des Matières

1. [Vue d'Ensemble](#vue-densemble)
2. [Services Disponibles](#services-disponibles)
3. [Configuration](#configuration)
4. [Utilisation de HuggingFaceClient](#utilisation-de-huggingfaceclient)
5. [Utilisation de OpenRouterClient](#utilisation-de-openrouterclient)
6. [Cas d'Usage pour PanoVisu](#cas-dusage-pour-panovisu)
7. [Exemples de Code](#exemples-de-code)
8. [Dépannage](#dépannage)

---

## Vue d'Ensemble

EditeurPanovisu intègre deux clients API pour les services d'intelligence artificielle :

| Client | Service | Modèle par défaut | Gratuit | Limite |
|--------|---------|-------------------|---------|--------|
| **HuggingFaceClient** | Hugging Face Inference API | gpt2 | ✅ Oui | Modèles publics |
| **OpenRouterClient** | OpenRouter API Gateway | llama-3.2-3b-instruct:free | ✅ Oui | Certains modèles |

### ✨ Fonctionnalités

- **Génération de texte** : Créer des descriptions, suggestions, contenus
- **Résumé automatique** : Synthétiser des textes longs
- **Chat interactif** : Dialoguer avec des modèles LLM
- **Suggestions contextuelles** : Aide à la création de panoramas
- **Exécution asynchrone** : Ne bloque pas l'interface utilisateur

---

## Services Disponibles

### 🤗 Hugging Face

**Site** : https://huggingface.co/

**Modèles disponibles** :
- `gpt2` - Génération de texte généraliste
- `facebook/bart-large-cnn` - Résumé de texte
- Tous les modèles publics Hugging Face

**Avantages** :
- ✅ Gratuit pour les modèles publics
- ✅ Pas de limite stricte de requêtes
- ✅ Large choix de modèles spécialisés

**Limites** :
- ⚠️ Temps de chargement initial (cold start)
- ⚠️ Qualité variable selon le modèle

---

### 🚀 OpenRouter

**Site** : https://openrouter.ai/

**Modèles gratuits** :
- `meta-llama/llama-3.2-3b-instruct:free` ⭐ (recommandé)
- `meta-llama/llama-3.2-1b-instruct:free`
- `mistralai/mistral-7b-instruct:free`
- `google/gemma-2-9b-it:free`
- `microsoft/phi-3-mini-128k-instruct:free`

**Avantages** :
- ✅ API unifiée pour plusieurs fournisseurs
- ✅ Modèles récents et performants
- ✅ Réponses rapides
- ✅ Format de chat standard (OpenAI-compatible)

**Limites** :
- ⚠️ Modèles payants pour les plus performants
- ⚠️ Limites de requêtes selon le modèle

---

## Configuration

### 1️⃣ Obtenir les Clés API

#### Hugging Face

1. Créer un compte sur https://huggingface.co/
2. Aller dans **Settings** → **Access Tokens**
3. Créer un nouveau token (Read access suffit)
4. Copier le token (format : `hf_...`)

#### OpenRouter

1. Créer un compte sur https://openrouter.ai/
2. Aller dans **Keys** → **Create Key**
3. Créer une clé API
4. Copier la clé (format : `sk-or-v1-...`)

### 2️⃣ Ajouter les Clés dans api-keys.properties

⚠️ **IMPORTANT** : Ne jamais committer vos vraies clés API sur Git !

Créez ou éditez le fichier `api-keys.properties` à la racine du projet :

```properties
# Hugging Face API
huggingface.api.key=hf_YOUR_API_KEY_HERE
huggingface.model=gpt2

# OpenRouter API
openrouter.api.key=sk-or-v1-YOUR_API_KEY_HERE
openrouter.model=meta-llama/llama-3.2-3b-instruct:free
```

**Où obtenir les clés** :
- Hugging Face : https://huggingface.co/settings/tokens
- OpenRouter : https://openrouter.ai/keys

**Sécurité** :
- ✅ Le fichier `api-keys.properties` est dans `.gitignore`
- ✅ Vos clés ne seront jamais versionnées
- ✅ Utilisez `api-keys.properties.template` comme référence

### 3️⃣ Vérifier la Configuration

```java
// Vérifier Hugging Face
HuggingFaceClient hfClient = new HuggingFaceClient();
if (hfClient.isConfigured()) {
    System.out.println("✅ Hugging Face configuré");
}

// Vérifier OpenRouter
OpenRouterClient orClient = new OpenRouterClient();
if (orClient.isConfigured()) {
    System.out.println("✅ OpenRouter configuré");
}
```

---

## Utilisation de HuggingFaceClient

### Création du Client

```java
// Avec configuration automatique depuis api-keys.properties
HuggingFaceClient client = new HuggingFaceClient();

// Ou avec paramètres personnalisés
HuggingFaceClient client = new HuggingFaceClient("hf_YOUR_KEY", "gpt2");
```

### Génération de Texte

#### Génération Simple

```java
try {
    String prompt = "Une visite virtuelle est";
    String result = client.generateText(prompt);
    System.out.println(result);
} catch (Exception e) {
    e.printStackTrace();
}
```

#### Génération avec Paramètres

```java
try {
    String prompt = "Panorama 360 degrees photography";
    int maxLength = 150;        // Longueur maximale
    double temperature = 0.8;   // Créativité (0.0-1.0)
    
    String result = client.generateText(prompt, maxLength, temperature);
    System.out.println(result);
} catch (Exception e) {
    e.printStackTrace();
}
```

#### Génération Asynchrone

```java
client.generateTextAsync("Virtual tour of a castle")
      .thenAccept(result -> {
          // Traiter le résultat dans un callback
          Platform.runLater(() -> {
              textArea.setText(result);
          });
      })
      .exceptionally(error -> {
          System.err.println("Erreur: " + error.getMessage());
          return null;
      });
```

### Résumé de Texte

```java
try {
    String longText = "Votre texte long à résumer...";
    int maxLength = 100;
    
    String summary = client.summarize(longText, maxLength);
    System.out.println("Résumé: " + summary);
} catch (Exception e) {
    e.printStackTrace();
}
```

---

## Utilisation de OpenRouterClient

### Création du Client

```java
// Avec configuration automatique
OpenRouterClient client = new OpenRouterClient();

// Ou avec paramètres personnalisés
OpenRouterClient client = new OpenRouterClient(
    "sk-or-v1-YOUR_KEY",
    "meta-llama/llama-3.2-3b-instruct:free"
);
```

### Chat Simple

```java
try {
    String response = client.chat("Qu'est-ce qu'un panorama 360° ?");
    System.out.println(response);
} catch (Exception e) {
    e.printStackTrace();
}
```

### Chat avec Contexte Système

```java
try {
    String systemMessage = "Tu es un expert en photographie panoramique.";
    String userMessage = "Comment créer un bon panorama ?";
    
    String response = client.chat(userMessage, systemMessage);
    System.out.println(response);
} catch (Exception e) {
    e.printStackTrace();
}
```

### Chat avec Paramètres Personnalisés

```java
try {
    String userMessage = "Explique les panoramas 360°";
    String systemMessage = "Tu es un guide technique.";
    double temperature = 0.7;  // Créativité
    int maxTokens = 500;       // Longueur maximale
    
    String response = client.chat(
        userMessage,
        systemMessage,
        temperature,
        maxTokens
    );
    System.out.println(response);
} catch (Exception e) {
    e.printStackTrace();
}
```

### Chat Asynchrone

```java
client.chatAsync("What are hotspots in 360 tours?")
      .thenAccept(response -> {
          Platform.runLater(() -> {
              outputLabel.setText(response);
          });
      })
      .exceptionally(error -> {
          System.err.println("Erreur: " + error.getMessage());
          return null;
      });
```

---

## Cas d'Usage pour PanoVisu

### 1. Génération de Descriptions de Panoramas

```java
OpenRouterClient client = new OpenRouterClient();

try {
    String titre = "Château de Chambord";
    String tags = "château, renaissance, architecture, visite virtuelle";
    
    String description = client.generatePanoramaDescription(titre, tags);
    panorama.setDescription(description);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

### 2. Suggestion de Hotspots

```java
OpenRouterClient client = new OpenRouterClient();

try {
    String titre = "Cathédrale Notre-Dame";
    String description = "Vue intérieure avec vitraux et rosaces";
    
    String suggestions = client.suggestHotspots(titre, description);
    
    // Parser les suggestions et créer les hotspots
    System.out.println("Hotspots suggérés:\n" + suggestions);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

### 3. Aide à la Navigation

```java
HuggingFaceClient client = new HuggingFaceClient();

try {
    String prompt = "Instructions pour naviguer dans une visite virtuelle 360:";
    String instructions = client.generateText(prompt, 200, 0.6);
    
    aideTextArea.setText(instructions);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

### 4. Génération de Contenu HTML

```java
OpenRouterClient client = new OpenRouterClient();

try {
    String systemMsg = "Tu es un générateur de contenu HTML pour visites virtuelles.";
    String userMsg = "Génère un texte de bienvenue HTML pour une visite de château";
    
    String htmlContent = client.chat(userMsg, systemMsg);
    
    // Utiliser le contenu dans les hotspots HTML
    hotspot.setContenuHTML(htmlContent);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

### 5. Traduction Automatique

```java
OpenRouterClient client = new OpenRouterClient();

try {
    String systemMsg = "Tu es un traducteur professionnel.";
    String userMsg = "Traduis en anglais: " + descriptionFR;
    
    String descriptionEN = client.chat(userMsg, systemMsg);
    panorama.setDescriptionEN(descriptionEN);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

---

## Exemples de Code

### Exemple Complet : Assistant de Création de Panorama

```java
public class PanoramaAssistant {
    
    private final OpenRouterClient aiClient;
    
    public PanoramaAssistant() {
        this.aiClient = new OpenRouterClient();
    }
    
    /**
     * Génère une description complète pour un panorama
     */
    public void generatePanoramaContent(Panoramique panorama) {
        if (!aiClient.isConfigured()) {
            System.err.println("OpenRouter non configuré");
            return;
        }
        
        String titre = panorama.getTitrePanoramique();
        String tags = String.join(", ", panorama.getTags());
        
        // Génération asynchrone pour ne pas bloquer l'UI
        aiClient.chatAsync(
            String.format(
                "Génère une description attractive pour un panorama 360° " +
                "intitulé '%s' avec les thèmes: %s",
                titre, tags
            )
        ).thenAccept(description -> {
            Platform.runLater(() -> {
                panorama.setDescription(description);
                System.out.println("✅ Description générée");
            });
        }).exceptionally(error -> {
            System.err.println("❌ Erreur: " + error.getMessage());
            return null;
        });
    }
    
    /**
     * Suggère des hotspots pertinents
     */
    public void suggestHotspots(Panoramique panorama, TextArea outputArea) {
        String systemMsg = "Tu es un expert en visites virtuelles. " +
                          "Suggère 3-5 points d'intérêt avec leurs descriptions.";
        
        String userMsg = String.format(
            "Pour un panorama '%s', suggère des hotspots intéressants.",
            panorama.getTitrePanoramique()
        );
        
        aiClient.chatAsync(userMsg, systemMsg)
               .thenAccept(suggestions -> {
                   Platform.runLater(() -> {
                       outputArea.setText(suggestions);
                   });
               });
    }
}
```

### Test des Clients

Pour tester les clients, exécutez la classe de test :

```bash
mvn exec:java -Dexec.mainClass="editeurpanovisu.TestAIClients"
```

---

## Dépannage

### ❌ "Clé API non configurée"

**Cause** : Le fichier `api-keys.properties` n'existe pas ou ne contient pas la clé

**Solution** :
1. Vérifier que `api-keys.properties` existe à la racine du projet
2. Vérifier que les clés sont présentes :
   ```bash
   cat api-keys.properties | grep -E "huggingface|openrouter"
   ```

### ❌ Erreur 401 Unauthorized

**Cause** : Clé API invalide ou expirée

**Solution** :
1. Vérifier la clé sur le site du service
2. Régénérer une nouvelle clé si nécessaire
3. Mettre à jour `api-keys.properties`

### ❌ Erreur 429 Too Many Requests

**Cause** : Limite de requêtes dépassée

**Solution** :
- Hugging Face : Attendre quelques secondes (cold start)
- OpenRouter : Vérifier les limites sur https://openrouter.ai/

### ❌ Timeout ou Pas de Réponse

**Cause** : Modèle en cours de chargement (cold start)

**Solution** :
- Attendre 10-30 secondes pour le premier appel
- Les appels suivants seront plus rapides
- Utiliser `chatAsync()` ou `generateTextAsync()` pour ne pas bloquer l'UI

### ❌ Réponse Incohérente

**Cause** : Température trop élevée ou prompt imprécis

**Solution** :
- Réduire la température (0.3-0.5 pour plus de cohérence)
- Améliorer le prompt avec plus de contexte
- Ajouter un message système pour guider le modèle

### 🔍 Debug

Activer les logs détaillés :

```java
// Dans ApiKeysConfig.java, les logs sont déjà actifs
System.out.println("✅ Configuration API chargée");
```

Vérifier la configuration :

```java
System.out.println("HF Key: " + ApiKeysConfig.getHuggingFaceApiKey().substring(0, 10) + "...");
System.out.println("HF Model: " + ApiKeysConfig.getHuggingFaceModel());
System.out.println("OR Key: " + ApiKeysConfig.getOpenRouterApiKey().substring(0, 15) + "...");
System.out.println("OR Model: " + ApiKeysConfig.getOpenRouterModel());
```

---

## 📚 Ressources

### Documentation Officielle

- **Hugging Face** : https://huggingface.co/docs/api-inference/
- **OpenRouter** : https://openrouter.ai/docs

### Modèles Recommandés

#### Pour Génération de Texte
- Hugging Face : `gpt2`, `gpt2-large`
- OpenRouter : `meta-llama/llama-3.2-3b-instruct:free`

#### Pour Résumé
- Hugging Face : `facebook/bart-large-cnn`
- OpenRouter : `google/gemma-2-9b-it:free`

#### Pour Code/Technique
- OpenRouter : `microsoft/phi-3-mini-128k-instruct:free`

### Exemples de Prompts

```text
# Description de panorama
"Génère une description courte et attractive pour un panorama 360° 
d'un [lieu] avec [caractéristiques]. Maximum 2-3 phrases."

# Suggestion de hotspots
"Pour un panorama de [lieu], suggère 3-5 points d'intérêt 
(hotspots) avec une courte description pour chacun."

# Traduction
"Traduis en [langue]: [texte]"

# Aide navigation
"Explique en 3 étapes comment naviguer dans une visite virtuelle 360°"
```

---

## 🎯 Bonnes Pratiques

1. **Utilisation Asynchrone** : Toujours utiliser `chatAsync()` ou `generateTextAsync()` pour ne pas bloquer l'interface

2. **Gestion d'Erreurs** : Toujours entourer les appels API de try-catch

3. **Timeout** : Prévoir un timeout de 30 secondes minimum pour le premier appel

4. **Cache** : Mettre en cache les réponses fréquemment demandées

5. **Prompts Clairs** : Écrire des prompts précis et contextualisés

6. **Messages Système** : Utiliser des messages système pour guider le comportement du modèle

7. **Température** : 
   - 0.0-0.3 : Réponses cohérentes et prévisibles
   - 0.4-0.7 : Équilibre créativité/cohérence ⭐
   - 0.8-1.0 : Maximum de créativité

---

**Date de création** : 11 octobre 2025  
**Version** : 1.0.0  
**Auteur** : EditeurPanovisu Team
