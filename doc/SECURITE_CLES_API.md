# 🔒 SÉCURITÉ - Gestion des Clés API

## ⚠️ IMPORTANT - NE JAMAIS COMMITTER LES CLÉS API

Ce document explique comment gérer en toute sécurité les clés API dans le projet EditeurPanovisu.

---

## 🚨 Règles de Sécurité

### ❌ À NE JAMAIS FAIRE

1. **Ne JAMAIS committer `api-keys.properties` avec de vraies clés**
2. **Ne JAMAIS mettre de clés API dans le code source**
3. **Ne JAMAIS partager vos clés API publiquement**
4. **Ne JAMAIS copier-coller vos clés dans des documents versionnés**

### ✅ Bonnes Pratiques

1. **Utiliser `api-keys.properties`** (fichier dans `.gitignore`)
2. **Utiliser `api-keys.properties.template`** comme référence (sans vraies clés)
3. **Régénérer les clés** si elles sont exposées accidentellement
4. **Vérifier avant chaque commit** qu'aucune clé n'est présente

---

## 📁 Fichiers de Configuration

### Fichier pour Git (versionné)

**`api-keys.properties.template`**
- ✅ Contient des exemples de format
- ✅ Contient `YOUR_API_KEY_HERE` comme placeholder
- ✅ Sert de référence pour les développeurs
- ✅ Est versionné dans Git

```properties
# Fichier TEMPLATE - Ne contient PAS de vraies clés

# Hugging Face API
huggingface.api.key=hf_YOUR_API_KEY_HERE
huggingface.model=gpt2

# OpenRouter API
openrouter.api.key=sk-or-v1-YOUR_API_KEY_HERE
openrouter.model=meta-llama/llama-3.2-3b-instruct:free
```

### Fichier Local (NON versionné)

**`api-keys.properties`**
- ✅ Contient vos VRAIES clés API
- ✅ Est dans `.gitignore` (ligne 219)
- ✅ N'est JAMAIS versionné
- ✅ Reste uniquement sur votre machine

```properties
# Fichier LOCAL - Contient vos vraies clés (NE PAS COMMITTER)

# Hugging Face API
huggingface.api.key=hf_AbCdEf123456789...  # Votre vraie clé
huggingface.model=gpt2

# OpenRouter API
openrouter.api.key=sk-or-v1-XyZ987654321...  # Votre vraie clé
openrouter.model=meta-llama/llama-3.2-3b-instruct:free
```

---

## 🛡️ Vérifications de Sécurité

### Avant Chaque Commit

```powershell
# 1. Vérifier que api-keys.properties est ignoré
git status | Select-String "api-keys.properties"
# Résultat attendu : AUCUNE correspondance

# 2. Vérifier qu'aucune clé n'est dans les fichiers staged
git diff --cached | Select-String -Pattern "hf_[A-Za-z0-9]|sk-or-v1-[A-Za-z0-9]"
# Résultat attendu : AUCUNE correspondance

# 3. Vérifier le .gitignore
Get-Content .gitignore | Select-String "api-keys.properties"
# Résultat attendu : api-keys.properties
```

### Vérifier les Fichiers de Documentation

```powershell
# Vérifier qu'aucune vraie clé n'est dans la doc
Get-ChildItem -Recurse -Filter "*.md" | Select-String -Pattern "hf_[A-Za-z0-9]{30,}|sk-or-v1-[A-Za-z0-9]{40,}"
# Résultat attendu : AUCUNE correspondance
```

---

## 🔄 En Cas d'Exposition Accidentelle

### Si vous avez commité une clé par erreur :

#### 1️⃣ **Révoquer immédiatement la clé**

**Hugging Face** :
1. Aller sur https://huggingface.co/settings/tokens
2. Trouver le token exposé
3. Cliquer sur "Revoke" (Révoquer)
4. Créer un nouveau token

**OpenRouter** :
1. Aller sur https://openrouter.ai/keys
2. Trouver la clé exposée
3. Cliquer sur "Delete" (Supprimer)
4. Créer une nouvelle clé

#### 2️⃣ **Supprimer la clé de l'historique Git**

⚠️ **ATTENTION** : Cette opération réécrit l'historique Git

```powershell
# Installer BFG Repo-Cleaner (si pas déjà installé)
# https://rtyley.github.io/bfg-repo-cleaner/

# Créer un fichier avec les clés à supprimer
@"
hf_YOUR_EXPOSED_KEY
sk-or-v1-YOUR_EXPOSED_KEY
"@ | Out-File passwords.txt

# Nettoyer l'historique
bfg --replace-text passwords.txt

# Forcer le push
git push --force
```

#### 3️⃣ **Mettre à jour le fichier local**

```powershell
# Mettre à jour api-keys.properties avec les nouvelles clés
notepad api-keys.properties
```

#### 4️⃣ **Vérifier que tout est correct**

```powershell
# Vérifier que la clé n'est plus dans l'historique
git log --all --source -S "hf_YOUR_OLD_KEY"
# Résultat attendu : AUCUNE correspondance
```

---

## 📋 Checklist de Sécurité

Avant chaque push vers Git :

- [ ] `api-keys.properties` est dans `.gitignore`
- [ ] Aucun fichier `.md` ne contient de vraies clés
- [ ] `git status` ne montre pas `api-keys.properties`
- [ ] Les fichiers de documentation utilisent `YOUR_API_KEY_HERE`
- [ ] Le template est à jour : `api-keys.properties.template`

---

## 🎓 Formation des Développeurs

### Pour les Nouveaux Développeurs

1. **Cloner le repository**
   ```powershell
   git clone https://github.com/llang57/editeurPanovisu.git
   cd editeurPanovisu
   ```

2. **Créer le fichier de configuration local**
   ```powershell
   Copy-Item api-keys.properties.template api-keys.properties
   ```

3. **Obtenir les clés API**
   - Hugging Face : https://huggingface.co/settings/tokens
   - OpenRouter : https://openrouter.ai/keys

4. **Configurer les clés**
   ```powershell
   notepad api-keys.properties
   # Remplacer YOUR_API_KEY_HERE par vos vraies clés
   ```

5. **Vérifier que c'est ignoré par Git**
   ```powershell
   git status
   # api-keys.properties ne doit PAS apparaître
   ```

---

## 🔍 Audit de Sécurité

### Script d'Audit Automatique

**Script Python** (recommandé) :
```bash
python audit-security.py
Write-Host "=" * 50

# 1. Vérifier .gitignore
Write-Host "`n1. Vérification .gitignore..." -ForegroundColor Yellow
if (Get-Content .gitignore | Select-String "api-keys.properties") {
    Write-Host "   ✅ api-keys.properties est dans .gitignore" -ForegroundColor Green
} else {
    Write-Host "   ❌ ERREUR: api-keys.properties manquant dans .gitignore" -ForegroundColor Red
}

# 2. Vérifier git status
Write-Host "`n2. Vérification git status..." -ForegroundColor Yellow
$status = git status --porcelain | Select-String "api-keys.properties"
if (-not $status) {
    Write-Host "   ✅ api-keys.properties n'est pas tracé" -ForegroundColor Green
} else {
    Write-Host "   ❌ ERREUR: api-keys.properties est tracé par Git!" -ForegroundColor Red
}

# 3. Vérifier les fichiers markdown
Write-Host "`n3. Vérification documentation..." -ForegroundColor Yellow
$mdKeys = Get-ChildItem -Recurse -Filter "*.md" | Select-String -Pattern "hf_[A-Za-z0-9]{30,}|sk-or-v1-[A-Za-z0-9]{40,}"
if (-not $mdKeys) {
    Write-Host "   ✅ Aucune vraie clé trouvée dans .md" -ForegroundColor Green
} else {
    Write-Host "   ❌ ERREUR: Clés API trouvées dans documentation!" -ForegroundColor Red
    $mdKeys | ForEach-Object { Write-Host "      $($_.Filename):$($_.LineNumber)" -ForegroundColor Red }
}

# 4. Vérifier le template
Write-Host "`n4. Vérification template..." -ForegroundColor Yellow
if (Test-Path "api-keys.properties.template") {
    $templateKeys = Get-Content "api-keys.properties.template" | Select-String -Pattern "hf_[A-Za-z0-9]{30,}|sk-or-v1-[A-Za-z0-9]{40,}"
    if (-not $templateKeys) {
        Write-Host "   ✅ Template ne contient pas de vraies clés" -ForegroundColor Green
    } else {
        Write-Host "   ❌ ERREUR: Vraies clés dans le template!" -ForegroundColor Red
    }
} else {
    Write-Host "   ⚠️  ATTENTION: Template manquant" -ForegroundColor Yellow
}

Write-Host "`n" + "=" * 50
Write-Host "Audit terminé." -ForegroundColor Cyan
```

**Utilisation** :
```bash
# Script Python (recommandé - multiplateforme)
python audit-security.py

# Ou script PowerShell (Windows uniquement)
.\audit-security.ps1
```

Le script vérifie automatiquement :
- ✅ `.gitignore` contient `api-keys.properties`
- ✅ `api-keys.properties` n'est pas tracé par Git
- ✅ Aucune vraie clé dans les fichiers `.md`
- ✅ Template ne contient pas de vraies clés
- ✅ Configuration locale existe
- ✅ Aucune clé hardcodée dans le code Java

---

## 📚 Ressources Supplémentaires

### Documentation Officielle

- **GitHub Security** : https://docs.github.com/en/code-security
- **Git Secrets** : https://github.com/awslabs/git-secrets
- **BFG Repo-Cleaner** : https://rtyley.github.io/bfg-repo-cleaner/

### Outils Recommandés

1. **git-secrets** : Empêche de committer des secrets
   ```powershell
   git secrets --install
   git secrets --register-aws
   ```

2. **pre-commit hooks** : Vérifications automatiques
   ```bash
   # .git/hooks/pre-commit
   #!/bin/sh
   if git diff --cached | grep -E "hf_[A-Za-z0-9]{30,}|sk-or-v1-[A-Za-z0-9]{40,}"; then
       echo "❌ ERREUR: Clé API détectée dans le commit!"
       exit 1
   fi
   ```

---

## ✅ Résumé

| Action | Statut |
|--------|--------|
| `api-keys.properties` dans `.gitignore` | ✅ Ligne 219 |
| Template disponible | ✅ `api-keys.properties.template` |
| Clés masquées dans la doc | ✅ Utilisation de placeholders |
| Script d'audit créé | ✅ `audit-security.ps1` |
| Documentation sécurité | ✅ Ce fichier |

---

**🔒 La sécurité des clés API est essentielle !**

En cas de doute, mieux vaut régénérer une nouvelle clé que risquer une exposition.

**Date de création** : 11 octobre 2025  
**Version** : 1.0.0  
**Auteur** : EditeurPanovisu Team - Équipe Sécurité
