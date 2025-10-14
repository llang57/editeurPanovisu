# 🤖 Système IA Complet - Documentation Technique

## 📋 Vue d'ensemble

Le système de génération de descriptions par IA dans EditeurPanovisu offre maintenant **trois services d'IA** avec une interface de configuration avancée et un système de bascule Online/Offline.

**Version actuelle** : Build 2584 (14 octobre 2025)

## ✨ Nouveautés (Build 2570-2584)

### 🎯 Fonctionnalités principales

- ✅ **8 modèles OpenRouter premium** (Claude Sonnet 4.5, GPT-4, Gemini Pro, etc.)
- ✅ **5 modèles Ollama locaux** détectés automatiquement avec système de priorité
- ✅ **Toggle Online/Offline** : Basculez facilement entre OpenRouter et Ollama
- ✅ **Interface emoji** : Identification visuelle des modèles (⭐🔷📅🇫🇷💰🌍🆓🔸)
- ✅ **DeepSeek-R1 32B** : Modèle de raisonnement avancé supporté
- ✅ **Gestion d'erreur intelligente** : Messages contextuels avec solutions
- ✅ **Persistance des préférences** : Modèles sélectionnés sauvegardés
- ✅ **max_tokens augmenté** : 1500 tokens pour éviter les troncatures

### 🚫 Suppressions

- ❌ **GPT-5 retiré** : Instabilité (`finish_reason: length` aléatoire)
- ❌ **Hugging Face désactivé** : Modèles obsolètes (HTTP 404)

## 🏗️ Architecture du système

```
┌─────────────────────────────────────────────┐
│  Interface Utilisateur (JavaFX)             │
│  ┌────────────────┐  ┌──────────────────┐   │
│  │ ComboBox       │  │ ToggleButton     │   │
│  │ OpenRouter (8) │  │ 🌐 Online        │   │
│  │ Ollama (5)     │  │ 🔸 Offline       │   │
│  └────────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────┐
│  OllamaService.java (Service Manager)       │
│                                             │
│  Mode Online (forceOllama = false)         │
│  ├─ 1. Try OpenRouter (8 modèles)          │
│  ├─ 2. Fallback: Ollama (5 modèles)        │
│  └─ 3. Error: Message détaillé             │
│                                             │
│  Mode Offline (forceOllama = true)         │
│  ├─ 1. Skip OpenRouter                     │
│  ├─ 2. Ollama uniquement                   │
│  └─ 3. Error: "Démarrez Ollama"            │
└─────────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────┐
│  Services IA                                │
│  ┌──────────────┐  ┌────────────────────┐   │
│  │ OpenRouter   │  │ Ollama Local       │   │
│  │ (Cloud API)  │  │ (http://localhost) │   │
│  └──────────────┘  └────────────────────┘   │
└─────────────────────────────────────────────┘
```

## 🎨 Modèles disponibles

### 🌐 OpenRouter (8 modèles cloud)

| Emoji | Modèle | Description | Taille | Spécialité |
|-------|--------|-------------|--------|------------|
| ⭐ | **Claude Sonnet 4.5** | `anthropic/claude-sonnet-4.5` | N/A | **Défaut** - Le plus récent (sept 2024) |
| 🔷 | **Claude 3 Opus** | `anthropic/claude-3-opus` | N/A | Le plus puissant d'Anthropic |
| 📅 | **Claude 3.5 Sonnet** | `anthropic/claude-3.5-sonnet:20241022` | N/A | Version datée spécifique |
| 🇫🇷 | **Mistral Nemo** | `mistralai/mistral-nemo` | N/A | Français, multilingue |
| 💰 | **GPT-OSS-120B** | `openai/gpt-oss-120b` | N/A | Open source, économique |
| 🌍 | **GPT-4 Turbo** | `openai/gpt-4-turbo` | N/A | Expert géographie |
| 🆓 | **Gemini Pro** | `google/gemini-pro` | N/A | Gratuit Google |
| 🆓 | **Llama 3.1 8B** | `meta-llama/llama-3.1-8b-instruct` | N/A | Gratuit, open source |

**Configuration requise** :
- Clé API OpenRouter (configurable dans Fichier > Configuration)
- Connexion Internet
- Aucune installation locale

### 🔸 Ollama (5 modèles locaux détectés)

| Emoji | Modèle | Taille | RAM min | Priorité | Spécialité |
|-------|--------|--------|---------|----------|------------|
| 🔸 | **mistral-nemo** | 7 GB | 8 GB | **1** | Créatif, précis (priorité max) |
| 🔸 | **deepseek-r1:32b** | ~20 GB | 24 GB | **2** | Raisonnement avancé (nouveau!) |
| 🔸 | **qwen2.5** | 4.7 GB | 8 GB | **3** | Excellent faits, très précis |
| 🔸 | **llama3.1** | 4.9 GB | 8 GB | **4** | Fiable, peu d'hallucinations |
| 🔸 | **gemma2** | 5.4 GB | 8 GB | **5** | Performant (Google) |

**Modèles fallback** (détectés mais priorité basse) :
- llama3, phi3, mistral, llama2

**Configuration requise** :
- Ollama installé ([https://ollama.ai](https://ollama.ai))
- Au moins un modèle téléchargé : `ollama pull mistral-nemo`
- Service démarré (automatique sur Windows)
- Pas de connexion Internet nécessaire

### ⚠️ Modèle retiré : DeepSeek-R1 70B

**Pourquoi ?** Le modèle 70B (42 GB) causait des erreurs HTTP 500 par manque de mémoire.  
**Solution** : Remplacé par la version **32B** (~20 GB) qui est plus légère et stable.

```bash
# Pour passer au 32B (recommandé)
ollama pull deepseek-r1:32b

# Pour supprimer le 70B (optionnel)
ollama rm deepseek-r1:70b
```

## 🎛️ Interface de configuration

### Panneau "Description IA"

```
┌─────────────────────────────────────────────────┐
│ Description IA                                  │
├─────────────────────────────────────────────────┤
│                                                 │
│ ┌─────────────────────────────────────────────┐ │
│ │ [Zone de texte description]                 │ │
│ │                                             │ │
│ └─────────────────────────────────────────────┘ │
│                                                 │
│ ⚠️ Les descriptions sont générées par IA...    │
│                                                 │
│ [Générer description IA]  Mode IA: [🌐 Online] │
│                                                 │
└─────────────────────────────────────────────────┘
```

**ToggleButton** :
- **🌐 Online** (vert) : Utilise OpenRouter puis Ollama en fallback
- **🔸 Offline** (bleu) : Utilise uniquement Ollama local

### Fenêtre de configuration (Fichier > Configuration)

**Onglet "IA"** :

```
┌─────────────────────────────────────────────────┐
│ Configuration IA                                │
├─────────────────────────────────────────────────┤
│                                                 │
│ Service OpenRouter (Cloud)                      │
│ ┌─────────────────────────────────────────────┐ │
│ │ Clé API : [****************************]   │ │
│ └─────────────────────────────────────────────┘ │
│                                                 │
│ Modèle OpenRouter :                             │
│ ┌─────────────────────────────────────────────┐ │
│ │ ⭐ Claude Sonnet 4.5 (Anthropic)          ▼│ │
│ │ 🔷 Claude 3 Opus (Anthropic)               │ │
│ │ 📅 Claude 3.5 Sonnet (22 oct 2024)         │ │
│ │ 🇫🇷 Mistral Nemo (Mistral AI)              │ │
│ │ 💰 GPT-OSS-120B (OpenAI)                   │ │
│ │ 🌍 GPT-4 Turbo (OpenAI)                    │ │
│ │ 🆓 Gemini Pro (Google)                     │ │
│ │ 🆓 Llama 3.1 8B Instruct (Meta)            │ │
│ └─────────────────────────────────────────────┘ │
│                                                 │
│ Service Ollama (Local)                          │
│ Modèles détectés :                              │
│ ┌─────────────────────────────────────────────┐ │
│ │ 🔸 mistral-nemo (7 GB)                    ▼│ │
│ │ 🔸 deepseek-r1:32b (~20 GB)                │ │
│ │ 🔸 qwen2.5 (4.7 GB)                        │ │
│ │ 🔸 llama3.1 (4.9 GB)                       │ │
│ │ 🔸 gemma2 (5.4 GB)                         │ │
│ └─────────────────────────────────────────────┘ │
│                                                 │
│                     [OK]  [Annuler]             │
└─────────────────────────────────────────────────┘
```

## 🔧 Installation et configuration

### 1. OpenRouter (Cloud - Recommandé pour débuter)

#### Obtenir une clé API

1. Créez un compte sur [https://openrouter.ai](https://openrouter.ai)
2. Allez dans **Settings > API Keys**
3. Cliquez sur **Create API Key**
4. Copiez la clé (format : `sk-or-v1-...`)

#### Configuration dans l'application

1. **Fichier > Configuration**
2. Onglet **"IA"**
3. Collez votre clé API dans le champ **"Clé OpenRouter"**
4. Choisissez un modèle (par défaut : **⭐ Claude Sonnet 4.5**)
5. Cliquez **OK**

**Fichier créé** : `api-keys.properties`
```properties
openrouter.token=sk-or-v1-xxxxxxxxxxxxxxxxxxxxx
openrouter.model=anthropic/claude-sonnet-4.5
```

⚠️ **Sécurité** : Le fichier `api-keys.properties` est dans `.gitignore` et ne sera jamais commité.

### 2. Ollama (Local - Recommandé pour la confidentialité)

#### Installation sur Windows

```powershell
# 1. Téléchargez l'installateur
# https://ollama.ai/download/windows

# 2. Exécutez OllamaSetup.exe

# 3. Vérifiez l'installation
ollama --version

# 4. Téléchargez les modèles recommandés
ollama pull mistral-nemo      # 7 GB - Priorité 1
ollama pull deepseek-r1:32b   # ~20 GB - Raisonnement avancé
ollama pull qwen2.5           # 4.7 GB - Précis
ollama pull llama3.1          # 4.9 GB - Fiable

# 5. Vérifiez les modèles installés
ollama list
```

#### Installation sur Linux

```bash
# 1. Installation
curl -fsSL https://ollama.ai/install.sh | sh

# 2. Démarrer le service
ollama serve &

# 3. Télécharger les modèles
ollama pull mistral-nemo
ollama pull deepseek-r1:32b
ollama pull qwen2.5
ollama pull llama3.1

# 4. Vérifier
ollama list
```

#### Installation sur macOS

```bash
# 1. Via Homebrew (recommandé)
brew install ollama

# 2. Ou téléchargez le .dmg
# https://ollama.ai/download/mac

# 3. Démarrer le service
ollama serve &

# 4. Télécharger les modèles
ollama pull mistral-nemo
ollama pull deepseek-r1:32b

# 5. Vérifier
ollama list
```

#### Configuration dans l'application

**Aucune configuration nécessaire !** Ollama est détecté automatiquement si :
- ✅ Le service tourne sur `http://localhost:11434`
- ✅ Au moins un modèle est installé

Le système choisira automatiquement le meilleur modèle selon la priorité définie.

## 📖 Utilisation

### Génération simple (mode automatique)

1. **Ouvrez un panoramique** dans l'éditeur
2. Renseignez **au moins une information** :
   - Titre du panoramique
   - OU Titre de la visite
   - OU Coordonnées GPS (via Géolocalisation)
3. Allez dans le panneau **"Description IA"**
4. Cliquez sur **"Générer description IA"**
5. Attendez la génération (~5-30 secondes selon le service)
6. **Modifiez** si nécessaire (sauvegarde automatique)

### Mode Online (🌐)

**Cas d'usage** : Vous avez une clé OpenRouter et souhaitez utiliser les modèles premium.

1. Configurez votre clé API OpenRouter (Fichier > Configuration)
2. Le toggle sera automatiquement sur **🌐 Online** (vert)
3. Cliquez sur **"Générer description IA"**

**Comportement** :
```
1. Essaie OpenRouter (Claude Sonnet 4.5 par défaut)
   ├─ ✅ Succès → Description générée
   └─ ❌ Échec → Passe à l'étape 2

2. Fallback vers Ollama local (si installé)
   ├─ ✅ Succès → Description générée
   └─ ❌ Échec → Message d'erreur

3. Erreur détaillée avec solutions
```

**Console** :
```
[IA] ➤ Tentative OpenRouter (anthropic/claude-sonnet-4.5)...
[IA] ✓ OpenRouter : Succès
```

### Mode Offline (🔸)

**Cas d'usage** : Vous souhaitez travailler sans Internet ou préférez Ollama local.

1. Installez Ollama et téléchargez des modèles
2. Cliquez sur le toggle pour passer en **🔸 Offline** (bleu)
3. Cliquez sur **"Générer description IA"**

**Comportement** :
```
1. Skip OpenRouter (mode forcé)

2. Essaie Ollama local uniquement
   ├─ ✅ Succès → Description générée
   └─ ❌ Échec → Message "Démarrez Ollama"
```

**Console** :
```
[IA] 🔸 Mode Ollama forcé - Skip OpenRouter
[IA] Recherche du meilleur modèle disponible...
[IA] ✓ Modèle trouvé : mistral-nemo:latest
[IA] ✓ Génération réussie (5.2s)
```

## 🎯 Système de priorité Ollama

Le système détecte automatiquement les modèles installés et choisit le meilleur selon cet ordre :

```java
String[] modelesPreferences = {
    "mistral-nemo", // 1️⃣ Priorité MAX (7 GB) - Créatif et précis
    "deepseek-r1",  // 2️⃣ Nouveau! (~20 GB 32B) - Raisonnement avancé
    "qwen2.5",      // 3️⃣ (4.7 GB) - Excellent pour les faits
    "llama3.1",     // 4️⃣ (4.9 GB) - Fiable, peu d'hallucinations
    "gemma2",       // 5️⃣ (5.4 GB) - Performant (Google)
    "llama3",       // 6️⃣ Fallback
    "phi3",         // 7️⃣ Fallback
    "mistral",      // 8️⃣ Fallback
    "llama2"        // 9️⃣ Fallback ancien
};
```

**Logique de sélection** :
1. Parcourt la liste par ordre de priorité
2. Pour chaque modèle, vérifie s'il est installé localement
3. Sélectionne le **premier trouvé**
4. Si aucun trouvé → Erreur "Aucun modèle Ollama installé"

**Exemple** :
```
Modèles installés : llama3.1, gemma2, deepseek-r1:32b
                       ↓
Recherche dans l'ordre de priorité :
  mistral-nemo → ❌ Non trouvé
  deepseek-r1  → ✅ TROUVÉ ! (sélectionné)
```

## 🛠️ Configuration avancée

### Modifier le modèle OpenRouter par défaut

**Fichier** : `src/editeurpanovisu/OllamaService.java`

```java
// Ligne 40
private static String openrouterModel = "anthropic/claude-sonnet-4.5"; // Modifiez ici
```

**Modèles disponibles** :
- `anthropic/claude-sonnet-4.5` ⭐ (défaut)
- `anthropic/claude-3-opus` 🔷
- `openai/gpt-4-turbo` 🌍
- `google/gemini-pro` 🆓
- etc.

### Ajuster max_tokens

**Fichier** : `src/editeurpanovisu/OllamaService.java`

```java
// Ligne 1024
String jsonRequest = String.format(
    "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"max_tokens\":1500,\"temperature\":0.3}",
    openrouterModel,
    prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")
);
```

**Valeurs recommandées** :
- `500` : Descriptions courtes (~2-3 phrases)
- `1000` : Descriptions moyennes (~1 paragraphe) 
- `1500` : **Défaut actuel** - Descriptions détaillées
- `2000` : Descriptions très détaillées (attention au coût API)

### Changer la priorité des modèles Ollama

**Fichier** : `src/editeurpanovisu/OllamaService.java`

```java
// Ligne 260
String[] modelesPreferences = {
    "mistral-nemo",  // Votre modèle préféré en premier
    "deepseek-r1",   // Deuxième choix
    "qwen2.5",       // Troisième choix
    // ... etc
};
```

### Ajouter un nouveau modèle à la liste

#### 1. OpenRouter

**Fichier** : `src/editeurpanovisu/OllamaService.java`

```java
// Ligne 34 - Ajoutez à la fin du tableau
private static final String[] OPENROUTER_MODELS = {
    // ... modèles existants ...
    "nouveau/modele-exemple"  // Ajoutez ici
};
```

**Fichier** : `src/editeurpanovisu/ConfigDialogController.java`

```java
// Ligne 418 - Ajoutez le mapping emoji
private static String ajouterEmojiModele(String modelName) {
    // ... mappings existants ...
    else if (modelName.equals("nouveau/modele-exemple")) {
        return "🆕 Mon Nouveau Modèle";
    }
    // ...
}

// Ligne 460 - Ajoutez le mapping inverse
private static String extraireNomModele(String displayName) {
    // ... mappings existants ...
    else if (displayName.contains("Mon Nouveau Modèle")) {
        return "nouveau/modele-exemple";
    }
    // ...
}
```

#### 2. Ollama (détection automatique)

**Rien à faire !** Ollama détecte automatiquement tous les modèles installés via `/api/tags`.

Pour ajouter un modèle à la liste de priorité :

```java
// OllamaService.java ligne 260
String[] modelesPreferences = {
    "mistral-nemo",
    "mon-nouveau-modele",  // Ajoutez ici
    "deepseek-r1",
    // ...
};
```

## 📊 Logs et débogage

### Console de l'application

Le système affiche des logs détaillés dans la console :

```
[IA] 🌐 Mode basculé: OpenRouter (online)
[IA] ➤ Tentative OpenRouter (anthropic/claude-sonnet-4.5)...
[IA] ✓ OpenRouter : Succès
```

```
[IA] 🔸 Mode Ollama forcé - Skip OpenRouter
[IA] Recherche du meilleur modèle disponible...
[IA] ✓ Modèle trouvé : mistral-nemo:latest
[IA] ➤ Appel Ollama : http://localhost:11434/api/generate
[IA] ✓ Génération réussie (5.2s)
```

### Vérifier les modèles Ollama

```bash
# Liste des modèles installés
ollama list

# Informations détaillées
ollama show mistral-nemo

# Tester un modèle
ollama run mistral-nemo "Bonjour"
```

### Tester l'API Ollama

```powershell
# PowerShell
$body = @{
    model = "mistral-nemo"
    prompt = "Test"
    stream = $false
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:11434/api/generate" -Method POST -Body $body -ContentType "application/json"
```

```bash
# Linux/macOS
curl -X POST http://localhost:11434/api/generate \
  -H "Content-Type: application/json" \
  -d '{"model":"mistral-nemo","prompt":"Test","stream":false}'
```

## ❌ Gestion des erreurs

### Messages d'erreur contextuels

Le système affiche des messages différents selon le contexte de l'échec :

#### 1. Mode Offline - Ollama indisponible

```
❌ Aucun service IA disponible !

Mode Ollama forcé mais Ollama n'est pas disponible.

Solutions:
1. Démarrez Ollama (ollama serve)
2. Basculez en mode Online (🌐) pour utiliser OpenRouter
```

#### 2. OpenRouter non configuré + Ollama indisponible

```
❌ Aucun service IA disponible !

OpenRouter non configuré ET Ollama non disponible.

Solutions:
1. Configurez votre clé OpenRouter dans Fichier > Configuration
2. OU installez et démarrez Ollama (gratuit, local)
   https://ollama.ai
```

#### 3. Les deux services ont échoué

```
❌ Aucun service IA disponible !

OpenRouter et Ollama ont échoué.

Solutions:
1. Vérifiez votre connexion internet
2. Vérifiez votre clé API OpenRouter
3. Démarrez Ollama si installé (ollama serve)
```

#### 4. HTTP 500 - Mémoire insuffisante

```
Erreur HTTP 500 pour deepseek-r1:70b
(mémoire insuffisante? Essayez un modèle plus léger)
```

**Solution** : Utilisez deepseek-r1:32b au lieu du 70B

### Erreurs courantes

| Erreur | Cause | Solution |
|--------|-------|----------|
| `Connection refused` | Ollama pas démarré | `ollama serve` |
| `HTTP 404` | Modèle non installé | `ollama pull mistral-nemo` |
| `HTTP 500` | Mémoire insuffisante | Utilisez un modèle plus léger |
| `HTTP 401` | Clé API invalide | Vérifiez votre clé OpenRouter |
| `Timeout` | Génération trop longue | Augmentez le timeout ou changez de modèle |

## 🔒 Sécurité et confidentialité

### Clés API

- ✅ Stockées dans `api-keys.properties` (jamais commité via `.gitignore`)
- ✅ Lecture sécurisée au démarrage de l'application
- ✅ Pas d'affichage en clair dans l'interface (masqué par `****`)

### Données utilisateur

**Mode OpenRouter (Cloud)** :
- ⚠️ Données envoyées à OpenRouter/Anthropic/OpenAI
- ⚠️ Soumis à leur politique de confidentialité
- ✅ Connexion HTTPS sécurisée

**Mode Ollama (Local)** :
- ✅ **100% local** - Aucune donnée ne quitte votre machine
- ✅ Pas de compte requis
- ✅ Pas de limite d'utilisation
- ✅ Open source

### Recommandations

1. **Utilisez Ollama** pour les données sensibles (adresses, noms, etc.)
2. **Utilisez OpenRouter** pour les descriptions génériques nécessitant une qualité maximale
3. **Ne commitez jamais** `api-keys.properties`

## 📈 Performances

### Temps de génération moyens

| Service | Modèle | Temps 1ère fois | Temps suivants |
|---------|--------|-----------------|----------------|
| **OpenRouter** | Claude Sonnet 4.5 | ~3-5s | ~2-3s |
| **OpenRouter** | GPT-4 Turbo | ~4-6s | ~3-4s |
| **Ollama** | mistral-nemo (7GB) | ~10-15s | ~5-7s |
| **Ollama** | qwen2.5 (4.7GB) | ~8-12s | ~4-6s |
| **Ollama** | deepseek-r1:32b (20GB) | ~20-30s | ~10-15s |

**Note** : Les temps Ollama dépendent fortement de votre CPU/GPU.

### Optimisations

1. **GPU** : Ollama utilise automatiquement le GPU si disponible (Nvidia CUDA, AMD ROCm)
2. **RAM** : Plus de RAM = modèles plus gros possibles
3. **SSD** : Améliore le chargement initial du modèle
4. **Première génération** : Le modèle se charge en mémoire (plus lent)
5. **Générations suivantes** : Le modèle reste en cache (rapide)

### Configuration matérielle recommandée

| Modèle | RAM min | RAM recommandé | GPU |
|--------|---------|----------------|-----|
| qwen2.5 (4.7GB) | 8 GB | 16 GB | Optionnel |
| llama3.1 (4.9GB) | 8 GB | 16 GB | Optionnel |
| mistral-nemo (7GB) | 12 GB | 16 GB | Recommandé |
| deepseek-r1:32b (~20GB) | 24 GB | 32 GB | Recommandé |

## 🆕 Changelog

### Build 2584 (14 octobre 2025)
- ✅ **DeepSeek-R1 32B** : Remplacé le 70B (42 GB) par le 32B (~20 GB)
- ✅ **Priorité ajustée** : deepseek-r1 en position 2 (après mistral-nemo)
- ✅ **Message d'erreur** : HTTP 500 ne mentionne plus spécifiquement "42 GB"

### Build 2583 (14 octobre 2025)
- ✅ **Hugging Face désactivé** : Modèles obsolètes (HTTP 404)
- ✅ **Gestion d'erreur améliorée** : Messages contextuels avec solutions
- ✅ **DeepSeek-R1 70B dépriorisé** : Mis en dernière position (HTTP 500)

### Build 2582 (14 octobre 2025)
- ✅ **ToggleButton Online/Offline** : Bascule visuelle 🌐/🔸
- ✅ **DeepSeek-R1 70B** : Ajouté à la liste Ollama
- ✅ **Mode forcé** : `forceOllama` pour skip OpenRouter

### Build 2579 (13 octobre 2025)
- ✅ **GPT-5 retiré** : Instabilité (`finish_reason: length`)
- ✅ **max_tokens augmenté** : 500 → 1500 (évite troncature)
- ✅ **getServiceName() fixé** : Affiche le vrai modèle utilisé

### Build 2575 (13 octobre 2025)
- ✅ **Émojis visuels** : ⭐🔷📅🇫🇷💰🌍🆓🔸 pour identifier les modèles
- ✅ **9 modèles** : 8 OpenRouter + GPT-5 (avant retrait)

### Build 2570 (12 octobre 2025)
- ✅ **Interface de configuration** : ComboBox pour sélection des modèles
- ✅ **Persistance** : Sauvegarde dans `preferences.cfg`
- ✅ **4 modèles initiaux** : Claude Sonnet 4.5, Claude Opus, Mistral Nemo, GPT-4 Turbo

## 📚 Ressources

### Documentation officielle

- **OpenRouter** : [https://openrouter.ai/docs](https://openrouter.ai/docs)
- **Ollama** : [https://github.com/ollama/ollama](https://github.com/ollama/ollama)
- **Claude** : [https://docs.anthropic.com](https://docs.anthropic.com)
- **Mistral** : [https://docs.mistral.ai](https://docs.mistral.ai)

### Modèles recommandés

**Pour débuter** :
- OpenRouter : ⭐ Claude Sonnet 4.5 (excellent rapport qualité/prix)
- Ollama : 🔸 qwen2.5 (léger, rapide, précis)

**Pour la qualité maximale** :
- OpenRouter : 🔷 Claude 3 Opus (le plus puissant)
- Ollama : 🔸 deepseek-r1:32b (raisonnement avancé)

**Pour le français** :
- OpenRouter : 🇫🇷 Mistral Nemo (français natif)
- Ollama : 🔸 mistral-nemo (même modèle en local)

**Gratuit** :
- OpenRouter : 🆓 Gemini Pro ou Llama 3.1 8B
- Ollama : 🔸 Tous les modèles (100% gratuit)

### Support

- **Issues GitHub** : [https://github.com/llang57/editeurPanovisu/issues](https://github.com/llang57/editeurPanovisu/issues)
- **Documentation projet** : `doc/README.md`
- **Ollama Discord** : [https://discord.gg/ollama](https://discord.gg/ollama)

---

**Version** : 3.0.0 | **Build** : 2584 | **Date** : 14 octobre 2025  
**Auteur** : GitHub Copilot & équipe EditeurPanovisu
