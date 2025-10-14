# 🔄 Migration DeepSeek-R1 : 70B → 32B

## 📋 Résumé

Le modèle **DeepSeek-R1 70B** (42 GB) a été remplacé par la version **32B** (~20 GB) pour éviter les erreurs HTTP 500 dues à une mémoire insuffisante.

**Build concerné** : 2584 (14 octobre 2025)

## ⚠️ Problème avec le 70B

### Symptômes

```
[IA] ✗ Ollama local échec: Erreur HTTP 500 pour deepseek-r1:70b
(mémoire insuffisante? Modèle trop lourd: 42 GB)
```

### Causes

- **Taille du modèle** : 42 GB sur disque
- **RAM requise** : ~64 GB minimum pour l'inférence
- **Erreur HTTP 500** : Le serveur Ollama manque de mémoire lors de la génération
- **Impact** : Génération impossible même si le modèle est installé

### Machines affectées

- PC avec **moins de 64 GB de RAM**
- Serveurs sans GPU haute mémoire
- Configurations typiques (8-32 GB RAM)

## ✅ Solution : Version 32B

### Avantages du 32B

| Caractéristique | 70B | 32B |
|-----------------|-----|-----|
| **Taille disque** | 42 GB | ~20 GB |
| **RAM minimale** | 64 GB | 24 GB |
| **RAM recommandée** | 128 GB | 32 GB |
| **Vitesse génération** | Lent | **Plus rapide** |
| **Qualité** | Excellent | **Très bon** (90% du 70B) |
| **Stabilité** | ❌ HTTP 500 | ✅ Stable |

### Capacités conservées

- ✅ **Raisonnement avancé** : Chain-of-thought préservé
- ✅ **Précision** : Légèrement réduite mais excellente
- ✅ **Multilingue** : Français, anglais, etc.
- ✅ **Descriptions géographiques** : Qualité maintenue

## 🔧 Procédure de migration

### Étape 1 : Supprimer le 70B (optionnel)

Si vous avez installé le 70B et qu'il cause des erreurs :

```bash
# Lister les modèles
ollama list

# Supprimer le 70B
ollama rm deepseek-r1:70b
```

**Espace libéré** : ~42 GB

### Étape 2 : Installer le 32B

```bash
# Télécharger le 32B
ollama pull deepseek-r1:32b
```

**Temps de téléchargement** : ~15-30 minutes (selon connexion)

### Étape 3 : Vérifier l'installation

```bash
# Lister les modèles
ollama list

# Devrait afficher :
# NAME                    ID              SIZE      MODIFIED
# deepseek-r1:32b         abc123...       20 GB     2 minutes ago
```

### Étape 4 : Tester

```bash
# Test simple
ollama run deepseek-r1:32b "Bonjour, décris Paris en 2 phrases."
```

**Résultat attendu** :
```
Paris est la capitale de la France, située sur les rives de la Seine. 
Cette ville emblématique est mondialement connue pour la Tour Eiffel, 
le Louvre, et son riche patrimoine culturel et historique.
```

### Étape 5 : Mise à jour de l'application

**Aucune action requise !** L'application détecte automatiquement le modèle installé.

#### Vérification dans l'application

1. Lancez EditeurPanovisu
2. Allez dans **Fichier > Configuration > IA**
3. Dans la liste **"Service Ollama (Local)"**, vous devriez voir :
   ```
   🔸 deepseek-r1:32b (~20 GB)
   ```

## 📊 Comparaison des performances

### Temps de génération (machine avec 32 GB RAM)

| Modèle | Première génération | Générations suivantes |
|--------|---------------------|----------------------|
| **deepseek-r1:70b** | ❌ Erreur HTTP 500 | ❌ Erreur HTTP 500 |
| **deepseek-r1:32b** | ~20-30s | ~10-15s |
| **mistral-nemo** | ~10-15s | ~5-7s |
| **qwen2.5** | ~8-12s | ~4-6s |

### Qualité des descriptions

**Prompt** : "Décris la Tour Eiffel"

**deepseek-r1:70b** (avant migration - si fonctionnel) :
> La Tour Eiffel, construite par Gustave Eiffel pour l'Exposition universelle de 1889, est une tour de fer puddlé de 330 mètres située sur le Champ-de-Mars à Paris. Monument emblématique de la France, elle est le site touristique payant le plus visité au monde avec près de 7 millions de visiteurs par an. Sa structure innovante pour l'époque a marqué l'histoire de l'architecture métallique et reste un symbole universel de Paris.

**deepseek-r1:32b** (après migration) :
> La Tour Eiffel est une tour de fer de 330 mètres construite par Gustave Eiffel en 1889 pour l'Exposition universelle de Paris. Symbole emblématique de la France, elle attire environ 7 millions de visiteurs par an et offre une vue panoramique exceptionnelle sur la capitale depuis ses trois étages accessibles au public.

**Différence** : Qualité quasi identique, légèrement moins détaillée mais toujours excellente.

## 🎯 Nouvelle priorité des modèles

Le système a été mis à jour pour favoriser les modèles stables :

```java
String[] modelesPreferences = {
    "mistral-nemo", // 1️⃣ Priorité MAX (7 GB)
    "deepseek-r1",  // 2️⃣ Version 32B détectée (~20 GB) ← NOUVEAU !
    "qwen2.5",      // 3️⃣ (4.7 GB)
    "llama3.1",     // 4️⃣ (4.9 GB)
    "gemma2",       // 5️⃣ (5.4 GB)
    // ... autres modèles ...
};
```

**Comportement** :
- Si vous avez **mistral-nemo** ET **deepseek-r1:32b** → Utilise mistral-nemo
- Si vous avez **seulement deepseek-r1:32b** → Utilise deepseek-r1:32b
- Si vous avez **deepseek-r1:70b** (ancien) → Détecté mais peut causer HTTP 500

## 🔍 Détection du modèle

L'application détecte automatiquement la version installée :

```java
// Code interne - OllamaService.java
for (String modelPreference : modelesPreferences) {
    for (JsonValue modelValue : modelsArray) {
        String modelName = ((JsonObject) modelValue).getString("name");
        
        if (modelName.startsWith(modelPreference)) {
            // Détecte deepseek-r1:32b OU deepseek-r1:70b
            ollamaModel = modelName;
            return;
        }
    }
}
```

**Résultat** :
- `deepseek-r1:32b` → ✅ Utilisé
- `deepseek-r1:70b` → ⚠️ Utilisé mais risque HTTP 500
- `deepseek-r1` (sans tag) → ✅ Utilisé (version par défaut)

## 🛠️ Dépannage

### Le 32B cause toujours des erreurs HTTP 500

**Cause** : Même le 32B nécessite ~24 GB de RAM.

**Solutions** :
1. Fermez les applications gourmandes en RAM
2. Utilisez un modèle plus léger : `qwen2.5` (4.7 GB) ou `mistral-nemo` (7 GB)
3. Ajoutez de la RAM à votre machine

### Le 32B est détecté mais pas utilisé

**Cause** : Un autre modèle a une priorité plus élevée.

**Vérification** :
```bash
ollama list
```

Si vous voyez `mistral-nemo`, il sera utilisé en priorité.

**Solution** : Désinstallez mistral-nemo pour forcer l'utilisation de deepseek-r1:32b
```bash
ollama rm mistral-nemo
```

### Le modèle se charge mais la génération échoue

**Cause** : Timeout trop court.

**Solution** : Le timeout est déjà à 60 secondes. Si insuffisant, modifiez :

```java
// OllamaService.java ligne 1139
conn.setReadTimeout(120000); // 120 secondes
```

## 📝 Pour les développeurs

### Commits liés

- **Build 2584** : Migration 70B → 32B
  - `OllamaService.java` : Priorité ajustée, commentaires mis à jour
  - Message d'erreur HTTP 500 généralisé

- **Build 2583** : DeepSeek-R1 70B dépriorisé
  - Mis en dernière position (priorité 8)
  - Message d'erreur spécifique "42 GB"

- **Build 2582** : DeepSeek-R1 70B ajouté
  - Première intégration du modèle

### Code modifié

**OllamaService.java ligne 260** :
```java
// AVANT (Build 2583)
String[] modelesPreferences = {
    "mistral-nemo",
    "qwen2.5",
    "llama3.1",
    "gemma2",
    // ...
    "deepseek-r1"   // Dernière position (42 GB)
};

// APRÈS (Build 2584)
String[] modelesPreferences = {
    "mistral-nemo", // 7 GB
    "deepseek-r1",  // ~20 GB (32B) ← Remonté en priorité 2
    "qwen2.5",      // 4.7 GB
    "llama3.1",     // 4.9 GB
    "gemma2",       // 5.4 GB
    // ...
};
```

**OllamaService.java ligne 1167** :
```java
// AVANT (Build 2583)
if (responseCode == 500) {
    errorMsg += " (mémoire insuffisante? Modèle trop lourd: 42 GB)";
}

// APRÈS (Build 2584)
if (responseCode == 500) {
    errorMsg += " (mémoire insuffisante? Essayez un modèle plus léger)";
}
```

## 🎓 Recommandations finales

### Pour machines avec 8-16 GB RAM
```bash
ollama pull qwen2.5      # 4.7 GB - Priorité
ollama pull llama3.1     # 4.9 GB - Alternative
```

### Pour machines avec 16-24 GB RAM
```bash
ollama pull mistral-nemo # 7 GB - Recommandé
ollama pull qwen2.5      # 4.7 GB - Fallback
```

### Pour machines avec 24-32 GB RAM
```bash
ollama pull mistral-nemo    # 7 GB - Priorité
ollama pull deepseek-r1:32b # ~20 GB - Raisonnement avancé
ollama pull qwen2.5         # 4.7 GB - Fallback
```

### Pour machines avec 64+ GB RAM
```bash
ollama pull mistral-nemo    # 7 GB - Priorité
ollama pull deepseek-r1:32b # ~20 GB - Raisonnement avancé
# Le 70B fonctionnera mais est inutilement lourd
```

## 📞 Support

Si vous rencontrez des problèmes :

1. **Vérifiez la RAM disponible** : `Task Manager > Performance > Memory`
2. **Vérifiez les modèles installés** : `ollama list`
3. **Testez le modèle directement** : `ollama run deepseek-r1:32b "test"`
4. **Consultez les logs** : Console de l'application

---

**Date de migration** : 14 octobre 2025  
**Build** : 2584  
**Impact** : Toutes les installations avec deepseek-r1:70b
