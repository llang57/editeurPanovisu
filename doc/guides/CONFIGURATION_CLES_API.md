# 🔑 Configuration des Clés API

## Vue d'ensemble

Les clés API sont maintenant gérées de manière centralisée dans le fichier `api-keys.properties` et peuvent être configurées via l'interface graphique.

---

## 🎨 Configuration via l'interface

### Accès aux préférences

1. **Lancer l'application**
2. **Menu** → **Fichier** → **Configuration**
3. **Scroll vers le bas** jusqu'à la section "═══ Clés API ═══"

### Champs disponibles

| Champ | Description | Format |
|-------|-------------|--------|
| **LocationIQ API Key** | Géocodage et recherche d'adresses | `pk.xxxxx...` |
| **Hugging Face API Key** | Modèles d'IA pour descriptions | `hf_xxxxx...` |
| **OpenRouter API Key** | Gateway pour modèles LLM | `sk-or-v1-xxxxx...` |

### Sauvegarder

1. **Saisir vos clés** dans les champs
2. **Cliquer "Sauvegarder"**
3. Les clés sont automatiquement sauvegardées dans `api-keys.properties`

---

## 📝 Configuration manuelle

### Méthode 1 : Créer depuis le template

```powershell
# Copier le template
Copy-Item api-keys.properties.template api-keys.properties

# Éditer avec votre éditeur favori
notepad api-keys.properties
```

### Méthode 2 : Créer directement

Créez le fichier `api-keys.properties` à la racine du projet :

```properties
locationiq.api.key=VOTRE_CLE_LOCATIONIQ_ICI
huggingface.api.key=VOTRE_CLE_HUGGINGFACE_ICI
openrouter.api.key=VOTRE_CLE_OPENROUTER_ICI
```

---

## 🔐 Obtenir des clés API gratuites

### 1. LocationIQ (Géocodage)

**Site** : https://locationiq.com/  
**Gratuit** : 5 000 requêtes/jour

**Étapes** :
1. Créer un compte gratuit
2. Aller dans "Dashboard" → "Access Tokens"
3. Copier votre token (commence par `pk.`)
4. Coller dans le champ "LocationIQ API Key"

### 2. Hugging Face (IA)

**Site** : https://huggingface.co/  
**Gratuit** : Accès illimité aux modèles publics

**Étapes** :
1. Créer un compte gratuit
2. Aller dans "Settings" → "Access Tokens"
3. Créer un nouveau token (Read ou Write)
4. Copier le token (commence par `hf_`)
5. Coller dans le champ "Hugging Face API Key"

### 3. OpenRouter (LLM Gateway)

**Site** : https://openrouter.ai/  
**Gratuit** : Certains modèles gratuits (marqués "free")

**Étapes** :
1. Créer un compte gratuit
2. Aller dans "Keys" → "Create Key"
3. Copier la clé (commence par `sk-or-v1-`)
4. Coller dans le champ "OpenRouter API Key"

---

## 🛡️ Sécurité

### ⚠️ Règles importantes

1. **Ne partagez JAMAIS vos clés publiquement**
   - Pas dans Git
   - Pas sur forums/réseaux sociaux
   - Pas dans des captures d'écran

2. **Vérifiez `.gitignore`**
   ```gitignore
   # Clés API (NE PAS VERSIONNER)
   api-keys.properties
   ```

3. **Régénérez si exposées**
   - Si vous avez accidentellement partagé une clé
   - Régénérez-la immédiatement sur le site du fournisseur

4. **Utilisez des comptes séparés**
   - Comptes différents pour développement/production
   - Limitez les permissions au minimum nécessaire

---

## 🔄 Migration depuis l'ancienne configuration

### Avant (dispersé)

```
panovisu.cfg :
- bingAPIKey=xxx

NavigateurCarte.java :
- LocationIQ key en dur dans le code

Autres fichiers :
- Clés éparpillées
```

### Après (centralisé)

```
api-keys.properties :
- locationiq.api.key=xxx
- huggingface.api.key=xxx
- openrouter.api.key=xxx
```

### Étapes de migration

1. **Récupérer vos anciennes clés**
   - Bing API Key : Supprimée (non utilisée)
   - Autres clés : Chercher dans le code

2. **Créer api-keys.properties**
   - Soit via l'interface (Préférences)
   - Soit manuellement

3. **Tester**
   - Relancer l'application
   - Vérifier que les fonctionnalités marchent

---

## 🧪 Vérification

### Test de chargement

**Console logs attendus** :
```
📖 Clés API chargées depuis api-keys.properties
```

### Test de sauvegarde

1. Modifier une clé dans les préférences
2. Cliquer "Sauvegarder"
3. **Console logs attendus** :
   ```
   ✅ Clés API sauvegardées dans api-keys.properties
   ```

### Test de persistance

1. Fermer l'application
2. Rouvrir l'application
3. Ouvrir les préférences
4. Les clés doivent être pré-remplies

---

## ❓ Dépannage

### "⚠️ Fichier api-keys.properties non trouvé"

**Cause** : Le fichier n'existe pas encore  
**Solution** : 
- Ouvrir Préférences → Saisir clés → Sauvegarder
- Ou créer manuellement depuis le template

### Les clés ne sont pas chargées

**Vérifications** :
1. Fichier à la racine du projet (pas dans un sous-dossier)
2. Nom exact : `api-keys.properties` (pas .txt)
3. Format correct : `cle=valeur` (pas d'espaces autour du =)
4. Encodage UTF-8

### Les modifications ne sont pas sauvegardées

**Vérifications** :
1. Droits en écriture sur le fichier
2. Pas d'antivirus bloquant
3. Console logs pour erreurs

---

## 📊 Limites gratuites

| Service | Limite gratuite | Notes |
|---------|----------------|-------|
| **LocationIQ** | 5 000 req/jour | Idéal pour développement |
| **Hugging Face** | Illimité* | *Quotas par modèle |
| **OpenRouter** | Variable | Modèles "free" disponibles |

### Conseils pour économiser

1. **Cache les résultats** quand possible
2. **Batch les requêtes** si l'API le permet
3. **Utilisez les modèles gratuits** en priorité
4. **Monitorez votre usage** sur les dashboards

---

## 📚 Ressources

- **LocationIQ Docs** : https://locationiq.com/docs
- **Hugging Face Docs** : https://huggingface.co/docs
- **OpenRouter Docs** : https://openrouter.ai/docs

---

**Dernière mise à jour** : 13 octobre 2025 (Build 2269)
