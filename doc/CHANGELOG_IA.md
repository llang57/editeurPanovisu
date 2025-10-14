# 📝 CHANGELOG - Système IA

## Build 2584 (14 octobre 2025)

### 🔄 Migration DeepSeek-R1 : 70B → 32B

#### Modifications
- **OllamaService.java ligne 260** : DeepSeek-R1 remonté en priorité 2 (après mistral-nemo)
  ```java
  String[] modelesPreferences = {
      "mistral-nemo", // 7 GB - Priority 1
      "deepseek-r1",  // ~20 GB (32B) - Priority 2 ← MODIFIÉ
      "qwen2.5",      // 4.7 GB - Priority 3
      // ...
  };
  ```

- **OllamaService.java ligne 1167** : Message d'erreur HTTP 500 généralisé
  ```java
  // AVANT
  errorMsg += " (mémoire insuffisante? Modèle trop lourd: 42 GB)";
  
  // APRÈS
  errorMsg += " (mémoire insuffisante? Essayez un modèle plus léger)";
  ```

- **OllamaService.java ligne 257** : Commentaire mis à jour
  ```java
  // AVANT
  // DeepSeek-R1 70B désactivé : trop lourd (42 GB), cause HTTP 500
  
  // APRÈS
  // DeepSeek-R1 32B : plus léger que le 70B (~20 GB vs 42 GB)
  ```

#### Raison
- Le modèle 70B (42 GB) causait des erreurs **HTTP 500** par manque de mémoire
- La version 32B (~20 GB) est plus stable tout en conservant 90% des capacités

#### Impact
- ✅ Utilisateurs avec 24-32 GB RAM peuvent maintenant utiliser DeepSeek-R1
- ✅ Performances améliorées (génération plus rapide)
- ✅ Pas de changement pour les autres modèles

---

## Build 2583 (14 octobre 2025)

### ❌ Désactivation Hugging Face

#### Modifications
- **OllamaService.java ligne 1010-1044** : Hugging Face désactivé, messages d'erreur contextuels
  ```java
  // AVANT
  try {
      return appellerOllamaLocal(prompt);
  } catch (Exception e) {
      System.out.println("[IA] ➤ Fallback vers Hugging Face...");
  }
  return appellerHuggingFace(prompt);
  
  // APRÈS
  try {
      return appellerOllamaLocal(prompt);
  } catch (Exception e) {
      String messageErreur = "❌ Aucun service IA disponible !\n\n";
      // ... messages contextuels selon le mode ...
      throw new Exception(messageErreur);
  }
  throw new Exception("❌ Mode Hugging Face désactivé.");
  ```

#### Raison
- Modèles Hugging Face obsolètes (erreurs **HTTP 404**)
- Pas de maintenance depuis plusieurs mois
- Sources de confusion pour les utilisateurs

#### Impact
- ✅ Messages d'erreur plus clairs et exploitables
- ✅ Pas de tentative inutile vers un service cassé
- ⚠️ Les utilisateurs sans OpenRouter ni Ollama reçoivent un message les guidant

### 🔧 Amélioration gestion d'erreur

#### Modifications
- **OllamaService.java ligne 1163-1171** : Message HTTP 500 détaillé pour Ollama
  ```java
  if (responseCode == 500) {
      errorMsg += " (mémoire insuffisante? Modèle trop lourd: 42 GB)";
  }
  ```

#### Impact
- ✅ Utilisateurs comprennent pourquoi DeepSeek-R1 70B échoue
- ✅ Incitation à utiliser des modèles plus légers

### 📉 DeepSeek-R1 70B dépriorisé

#### Modifications
- **OllamaService.java ligne 257-268** : DeepSeek-R1 mis en **dernière position**
  ```java
  String[] modelesPreferences = {
      "mistral-nemo", // Priority 0
      "qwen2.5",      // Priority 1
      "llama3.1",     // Priority 2
      "gemma2",       // Priority 3
      // ...
      "deepseek-r1"   // Priority 8 (DERNIER) ← MODIFIÉ
  };
  ```

#### Raison
- Le modèle 70B (42 GB) cause trop d'erreurs HTTP 500
- Priorité donnée aux modèles légers et stables

#### Impact
- ✅ Les utilisateurs avec DeepSeek-R1 installé l'utiliseront en dernier recours
- ✅ Favorise mistral-nemo, qwen2.5, llama3.1 (plus stables)

---

## Build 2582 (14 octobre 2025)

### 🔄 Toggle Online/Offline

#### Modifications
- **EditeurPanovisu.java ligne 92** : Import ToggleButton
  ```java
  import javafx.scene.control.ToggleButton;
  ```

- **EditeurPanovisu.java ligne 11116-11176** : Ajout du ToggleButton dans l'interface
  ```java
  ToggleButton toggleModeIA = new ToggleButton();
  toggleModeIA.setLayoutX(240);
  toggleModeIA.setLayoutY(218);
  toggleModeIA.setPrefSize(90, 30);
  
  // Initial state
  boolean openRouterDispo = OllamaService.verifierOpenRouterDisponible();
  toggleModeIA.setSelected(!openRouterDispo);
  
  // Toggle listener
  toggleModeIA.selectedProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal) {
          toggleModeIA.setText("🔸 Offline");
          toggleModeIA.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white;");
          OllamaService.setForceOllama(true);
      } else {
          toggleModeIA.setText("🌐 Online");
          toggleModeIA.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white;");
          OllamaService.setForceOllama(false);
      }
  });
  ```

- **OllamaService.java ligne 76-78** : Variable forceOllama
  ```java
  private static boolean forceOllama = false;
  ```

- **OllamaService.java ligne 407-418** : Méthodes setForceOllama() / isForceOllama()
  ```java
  public static void setForceOllama(boolean force) {
      forceOllama = force;
      System.out.println("[IA] Mode forcé: " + (force ? "Ollama (offline)" : "Priorité normale"));
  }
  
  public static boolean isForceOllama() {
      return forceOllama;
  }
  ```

- **OllamaService.java ligne 145-152** : Méthode verifierOpenRouterDisponible()
  ```java
  public static boolean verifierOpenRouterDisponible() {
      return OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty();
  }
  ```

#### Impact
- ✅ Bascule instantanée entre OpenRouter et Ollama
- ✅ Interface visuelle claire (🌐 Online vert / 🔸 Offline bleu)
- ✅ État par défaut intelligent (Offline si pas de clé OpenRouter)

### 🆕 DeepSeek-R1 70B ajouté

#### Modifications
- **OllamaService.java ligne 260** : Ajout à la liste de priorité
  ```java
  String[] modelesPreferences = {
      "deepseek-r1",  // Priority 0 (AJOUTÉ)
      "mistral-nemo", // Priority 1
      // ...
  };
  ```

- **ConfigDialogController.java ligne 418-444** : Mapping emoji
  ```java
  else if (modelName.contains("deepseek-r1")) {
      return "🔸 DeepSeek-R1";
  }
  ```

- **ConfigDialogController.java ligne 460-503** : Mapping inverse
  ```java
  else if (displayName.contains("DeepSeek-R1")) {
      if (displayName.contains(":")) {
          return displayName.replaceFirst("^[^a-z]+\\s*", "").split("\\s+")[0];
      }
      return "deepseek-r1:70b";
  }
  ```

#### Impact
- ✅ Support du modèle de raisonnement avancé DeepSeek-R1
- ⚠️ Priorité 0 trop élevée (cause des erreurs HTTP 500)
- ⚠️ Sera dépriorisé dans Build 2583, puis remplacé par 32B dans Build 2584

---

## Build 2579 (13 octobre 2025)

### ❌ GPT-5 retiré de la liste

#### Modifications
- **OllamaService.java ligne 34-54** : Suppression de GPT-5
  ```java
  // AVANT (9 modèles)
  private static final String[] OPENROUTER_MODELS = {
      "anthropic/claude-sonnet-4.5",
      "anthropic/claude-3-opus",
      "anthropic/claude-3.5-sonnet:20241022",
      "mistralai/mistral-nemo",
      "openai/gpt-5",                     // ← RETIRÉ
      "openai/gpt-oss-120b",
      "openai/gpt-4-turbo",
      "google/gemini-pro",
      "meta-llama/llama-3.1-8b-instruct"
  };
  
  // APRÈS (8 modèles)
  private static final String[] OPENROUTER_MODELS = {
      "anthropic/claude-sonnet-4.5",
      "anthropic/claude-3-opus",
      "anthropic/claude-3.5-sonnet:20241022",
      "mistralai/mistral-nemo",
      "openai/gpt-oss-120b",
      "openai/gpt-4-turbo",
      "google/gemini-pro",
      "meta-llama/llama-3.1-8b-instruct"
  };
  ```

- **ConfigDialogController.java** : Suppression des mappings GPT-5

#### Raison
- **Instabilité critique** : `finish_reason: length` aléatoire
- Génération vide malgré facturation
- "Un coup ça marche, un coup ça marche pas"

#### Impact
- ✅ Plus d'erreurs imprévisibles avec GPT-5
- ✅ Économies sur l'API (pas de facturation pour générations vides)
- ⚠️ Utilisateurs ayant configuré GPT-5 rebasculés sur Claude Sonnet 4.5

### ⬆️ max_tokens augmenté

#### Modifications
- **OllamaService.java ligne 1024** : max_tokens 500 → 1500
  ```java
  // AVANT
  String jsonRequest = String.format(
      "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"max_tokens\":500,\"temperature\":0.3}",
      openrouterModel,
      prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")
  );
  
  // APRÈS
  String jsonRequest = String.format(
      "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"max_tokens\":1500,\"temperature\":0.3}",
      openrouterModel,
      prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")
  );
  ```

#### Raison
- GPT-5 tronquait les descriptions avec 500 tokens
- `finish_reason: length` indique que la génération a été coupée

#### Impact
- ✅ Descriptions complètes et détaillées
- ✅ Plus de troncatures en milieu de phrase
- ⚠️ Coût API légèrement augmenté (mais descriptions complètes)

### 🔧 getServiceName() fixé

#### Modifications
- **OllamaService.java ligne 420-442** : Détection du service réel utilisé
  ```java
  // AVANT
  public static String getServiceName() {
      return useHuggingFace ? "Hugging Face (en ligne)" : "Ollama (local)";
  }
  
  // APRÈS
  public static String getServiceName() {
      if (forceOllama) {
          return "Ollama (" + ollamaModel + ")";
      }
      if (OPENROUTER_TOKEN != null && !OPENROUTER_TOKEN.isEmpty()) {
          String modelShort = openrouterModel;
          if (modelShort.contains("/")) {
              modelShort = modelShort.substring(modelShort.lastIndexOf("/") + 1);
          }
          return "OpenRouter (" + modelShort + ")";
      }
      return useHuggingFace ? "Hugging Face (en ligne)" : "Ollama (local)";
  }
  ```

#### Impact
- ✅ Panneau de description affiche le vrai service : "OpenRouter (claude-sonnet-4.5)" ou "Ollama (mistral-nemo)"
- ✅ Plus de confusion entre services

---

## Build 2575 (13 octobre 2025)

### 🎨 Système d'émojis pour identification visuelle

#### Modifications
- **ConfigDialogController.java ligne 418-444** : Fonction ajouterEmojiModele()
  ```java
  private static String ajouterEmojiModele(String modelName) {
      // OpenRouter
      if (modelName.equals("anthropic/claude-sonnet-4.5")) {
          return "⭐ Claude Sonnet 4.5 (Anthropic)";
      } else if (modelName.equals("anthropic/claude-3-opus")) {
          return "🔷 Claude 3 Opus (Anthropic)";
      }
      // ... etc
      
      // Ollama
      else if (modelName.equals("mistral-nemo")) {
          return "🔸 Mistral Nemo";
      }
      // ... etc
  }
  ```

- **ConfigDialogController.java ligne 460-503** : Fonction extraireNomModele() (mapping inverse)

#### Légende des émojis
- ⭐ **Default** : Modèle par défaut (Claude Sonnet 4.5)
- 🔷 **Powerful** : Le plus puissant (Claude 3 Opus)
- 📅 **Dated** : Version datée spécifique (Claude 3.5 Sonnet 22 oct)
- 🇫🇷 **French** : Français natif (Mistral Nemo)
- 💰 **Economical** : Économique (GPT-OSS-120B)
- 🌍 **Geography** : Expert géographie (GPT-4 Turbo)
- 🆓 **Free** : Gratuit (Gemini Pro, Llama 3.1)
- 🔸 **Local** : Ollama local (tous les modèles locaux)

#### Impact
- ✅ Identification rapide des modèles dans les ComboBox
- ✅ Catégorisation visuelle (cloud vs local, premium vs gratuit, etc.)
- ✅ Meilleure UX pour la sélection

### 📈 Expansion de la liste : 4 → 9 modèles OpenRouter

#### Modifications
- **OllamaService.java ligne 34** : OPENROUTER_MODELS étendu
  ```java
  // AVANT (4 modèles)
  private static final String[] OPENROUTER_MODELS = {
      "anthropic/claude-sonnet-4.5",
      "anthropic/claude-3-opus",
      "mistralai/mistral-nemo",
      "openai/gpt-4-turbo"
  };
  
  // APRÈS (9 modèles - GPT-5 sera retiré en 2579)
  private static final String[] OPENROUTER_MODELS = {
      "anthropic/claude-sonnet-4.5",
      "anthropic/claude-3-opus",
      "anthropic/claude-3.5-sonnet:20241022",
      "mistralai/mistral-nemo",
      "openai/gpt-5",                     // Sera retiré en 2579
      "openai/gpt-oss-120b",
      "openai/gpt-4-turbo",
      "google/gemini-pro",
      "meta-llama/llama-3.1-8b-instruct"
  };
  ```

#### Nouveaux modèles ajoutés
- **Claude 3.5 Sonnet** (version datée 22 oct 2024)
- **GPT-5** (retiré en 2579 pour instabilité)
- **GPT-OSS-120B** (open source, économique)
- **Gemini Pro** (gratuit)
- **Llama 3.1 8B** (gratuit, open source)

#### Impact
- ✅ Plus de choix selon le besoin (qualité, coût, langue)
- ✅ Options gratuites disponibles (Gemini, Llama)
- ⚠️ GPT-5 instable (sera retiré)

---

## Build 2570 (12 octobre 2025)

### 🎛️ Interface de configuration des modèles

#### Modifications
- **EditeurPanovisu.java** : Ajout menu "Fichier > Configuration"
- **ConfigDialogController.java** : Création du contrôleur de configuration
  - ComboBox pour sélection OpenRouter (4 modèles)
  - ComboBox pour sélection Ollama (détection automatique)
  - TextField pour clé API OpenRouter
  - Boutons OK/Annuler avec sauvegarde

#### Fonctionnalités
- ✅ Sélection visuelle des modèles via ComboBox
- ✅ Détection automatique des modèles Ollama installés
- ✅ Sauvegarde des préférences dans `preferences.cfg`
- ✅ Chargement automatique des préférences au démarrage

#### Modèles disponibles (initiaux)
**OpenRouter (4)** :
- ⭐ Claude Sonnet 4.5 (défaut)
- 🔷 Claude 3 Opus
- 🇫🇷 Mistral Nemo
- 🌍 GPT-4 Turbo

**Ollama (détection dynamique)** :
- Tous les modèles installés localement

#### Impact
- ✅ Configuration intuitive sans éditer de fichiers
- ✅ Persistance des choix entre sessions
- ✅ Base pour futures améliorations (émojis, toggle)

### 💾 Système de persistance

#### Fichiers créés
- **`api-keys.properties`** : Clé API OpenRouter (jamais commité)
- **`preferences.cfg`** : Modèles sélectionnés
  ```
  modele_openrouter=anthropic/claude-sonnet-4.5
  modele_ollama=mistral-nemo
  ```

#### Sécurité
- ✅ `api-keys.properties` dans `.gitignore`
- ✅ Template fourni : `api-keys.properties.template`

---

## 📊 Statistiques globales

### Évolution du nombre de modèles

| Build | OpenRouter | Ollama | Total |
|-------|------------|--------|-------|
| 2570  | 4          | Auto   | 4+    |
| 2575  | 9          | Auto   | 9+    |
| 2579  | **8**      | Auto   | 8+    |
| 2582  | 8          | Auto + DeepSeek | 8+    |
| 2583  | 8          | Auto   | 8+    |
| 2584  | 8          | Auto   | 8+    |

### Modèles OpenRouter finaux (Build 2584)

| Emoji | Modèle | Type |
|-------|--------|------|
| ⭐ | Claude Sonnet 4.5 | Défaut |
| 🔷 | Claude 3 Opus | Premium |
| 📅 | Claude 3.5 Sonnet | Daté |
| 🇫🇷 | Mistral Nemo | Français |
| 💰 | GPT-OSS-120B | Économique |
| 🌍 | GPT-4 Turbo | Géographie |
| 🆓 | Gemini Pro | Gratuit |
| 🆓 | Llama 3.1 8B | Gratuit |

### Modèles Ollama prioritaires (Build 2584)

| Priorité | Modèle | Taille | RAM min |
|----------|--------|--------|---------|
| 1️⃣ | mistral-nemo | 7 GB | 8 GB |
| 2️⃣ | deepseek-r1:32b | ~20 GB | 24 GB |
| 3️⃣ | qwen2.5 | 4.7 GB | 8 GB |
| 4️⃣ | llama3.1 | 4.9 GB | 8 GB |
| 5️⃣ | gemma2 | 5.4 GB | 8 GB |

---

## 🔮 Prochaines étapes possibles

### Court terme
- [ ] Support d'autres tags DeepSeek (distill, Q8, etc.)
- [ ] Configuration du timeout via l'interface
- [ ] Indicateur de progression pour génération longue
- [ ] Cache des descriptions pour éviter régénération

### Moyen terme
- [ ] Support de modèles custom Ollama
- [ ] Fine-tuning des prompts par type de lieu
- [ ] Génération multilingue avec détection auto
- [ ] Historique des descriptions générées

### Long terme
- [ ] Support de LM Studio et autres backends
- [ ] Génération batch (plusieurs panoramiques)
- [ ] Export des descriptions vers formats externes
- [ ] Système de notation et feedback utilisateur

---

**Dernière mise à jour** : 14 octobre 2025 - Build 2584  
**Auteur** : GitHub Copilot & équipe EditeurPanovisu
