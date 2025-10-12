# 🔒 Script d'Audit de Sécurité

Script automatique pour vérifier qu'aucune clé API n'est exposée dans le repository.

---

## 🚀 Utilisation Rapide

```bash
# Exécuter l'audit
python audit-security.py
```

---

## ✅ Ce que le Script Vérifie

| Test | Description |
|------|-------------|
| **1. .gitignore** | Vérifie que `api-keys.properties` est dans `.gitignore` |
| **2. Git Status** | Vérifie que `api-keys.properties` n'est pas tracé |
| **3. Documentation (.md)** | Cherche les vraies clés dans les fichiers Markdown |
| **4. Template** | Vérifie que le template ne contient pas de vraies clés |
| **5. Configuration locale** | Vérifie que `api-keys.properties` existe et est configuré |
| **6. Code Java** | Cherche les clés hardcodées dans le code source |

---

## 📊 Résultat Attendu

### ✅ Succès (tout est OK)
```
🔍 AUDIT DE SÉCURITÉ DES CLÉS API
============================================================

1️⃣  Vérification .gitignore...
   ✅ api-keys.properties est dans .gitignore

2️⃣  Vérification git status...
   ✅ api-keys.properties n'est pas tracé par Git

3️⃣  Vérification documentation (.md)...
   ✅ Aucune vraie clé trouvée dans les fichiers .md

4️⃣  Vérification template...
   ✅ Template ne contient pas de vraies clés

5️⃣  Vérification configuration locale...
   ✅ Fichier semble configuré avec de vraies clés

6️⃣  Vérification code source (.java)...
   ✅ Aucune clé hardcodée dans les fichiers .java

📊 RÉSUMÉ DE L'AUDIT
============================================================

✅ SUCCÈS: Aucune erreur de sécurité détectée

   Le repository est sécurisé pour être poussé sur Git.
```

**Code de sortie** : `0` (OK)

---

### ❌ Échec (problèmes détectés)
```
🔍 AUDIT DE SÉCURITÉ DES CLÉS API
============================================================

...

3️⃣  Vérification documentation (.md)...
   ❌ Clés API trouvées dans la documentation! (2 occurrences)
      ./doc/guides/INTEGRATION_API_IA.md:107: Clé Hugging Face détectée
      ./doc/TRAVAIL_2025-10-11.md:23: Clé OpenRouter détectée

📊 RÉSUMÉ DE L'AUDIT
============================================================

❌ ÉCHEC: 2 erreur(s) détectée(s)

   ⚠️  NE PAS POUSSER SUR GIT AVANT CORRECTION!

Actions recommandées:
   1. Corriger les erreurs listées ci-dessus
   2. Relancer cet audit: python audit-security.py
   3. Si des clés ont été commitées, voir doc\SECURITE_CLES_API.md
```

**Code de sortie** : `1` (Erreur)

---

## 🔧 Intégration Git Hook (Optionnel)

Pour exécuter automatiquement l'audit avant chaque commit :

### Créer le hook pre-commit

**Linux/Mac** :
```bash
cat > .git/hooks/pre-commit << 'EOF'
#!/bin/sh
echo "🔍 Exécution de l'audit de sécurité..."
python audit-security.py
if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Audit de sécurité ÉCHOUÉ"
    echo "   Corrigez les erreurs avant de committer"
    exit 1
fi
EOF

chmod +x .git/hooks/pre-commit
```

**Windows (PowerShell)** :
```powershell
@"
#!/bin/sh
echo "🔍 Exécution de l'audit de sécurité..."
python audit-security.py
if [ `$? -ne 0 ]; then
    echo ""
    echo "❌ Audit de sécurité ÉCHOUÉ"
    echo "   Corrigez les erreurs avant de committer"
    exit 1
fi
"@ | Out-File -FilePath .git\hooks\pre-commit -Encoding ASCII

# Rendre exécutable (nécessite Git Bash)
git update-index --chmod=+x .git/hooks/pre-commit
```

---

## 🐍 Prérequis

- **Python 3.6+** (inclus par défaut sur la plupart des systèmes)
- Aucune dépendance externe (utilise uniquement la bibliothèque standard)

### Vérifier Python

```bash
python --version
# ou
python3 --version
```

Si Python n'est pas installé : https://www.python.org/downloads/

---

## 📖 Documentation Complète

Pour plus d'informations sur la sécurité des clés API :
- **[doc/SECURITE_CLES_API.md](doc/SECURITE_CLES_API.md)** - Guide complet de sécurité

---

## 🎯 Utilisation dans un Pipeline CI/CD

### GitHub Actions

```yaml
name: Security Audit

on: [push, pull_request]

jobs:
  audit:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Python
        uses: actions/setup-python@v2
        with:
          python-version: '3.x'
      - name: Run security audit
        run: python audit-security.py
```

### GitLab CI

```yaml
security-audit:
  image: python:3.9
  script:
    - python audit-security.py
  only:
    - merge_requests
    - main
```

---

## 🔍 Patterns Détectés

### Hugging Face
- **Pattern** : `hf_[A-Za-z0-9]{30,}`
- **Exemple** : `hf_AbCdEf123456789...`
- **Exclusions** : `YOUR`, `API`, `KEY`, `EXAMPLE`, `TEMPLATE`, `HERE`

### OpenRouter
- **Pattern** : `sk-or-v1-[A-Za-z0-9]{40,}`
- **Exemple** : `sk-or-v1-XyZ987654321...`
- **Exclusions** : `YOUR`, `API`, `KEY`, `EXAMPLE`, `TEMPLATE`, `HERE`

---

## 🛠️ Personnalisation

Pour ajouter d'autres services API, modifiez `audit-security.py` :

```python
# Ajouter un nouveau pattern
new_api_pattern = r'new_api_[A-Za-z0-9]{20,}'
new_findings = find_keys_in_files(new_api_pattern, '.md', ['YOUR', 'EXAMPLE'])
```

---

## 📝 Notes

- Le script ignore automatiquement les dossiers : `.git`, `target`, `node_modules`, `__pycache__`
- Les fichiers binaires sont ignorés (lecture avec `errors='ignore'`)
- Le script affiche max 5 erreurs par catégorie (pour éviter la surcharge)
- Le script est compatible Windows, Linux et macOS

---

**Créé** : 11 octobre 2025  
**Version** : 1.0.0  
**Python** : 3.6+  
**License** : Projet EditeurPanovisu
