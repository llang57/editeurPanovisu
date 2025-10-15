# 📁 Fichiers de détail par classe

Ce répertoire contient **54 fichiers Markdown**, un pour chaque classe Java du projet EditeurPanovisu.

## 📋 Structure

Chaque fichier de classe contient :

### 1. En-tête
- **Icône de statut** (✅ 🔶 ⚠️ ❌) selon le taux de documentation
- **Lien retour** vers l'index principal ([← Retour à l'index](../ETAT_DOCUMENTATION.md))
- **Chemin du fichier source**
- **État de la documentation de classe** (présence, nombre de lignes)
- **Progression globale** (éléments documentés / total)

### 2. Statistiques détaillées

Répartition par niveau de qualité :

#### Méthodes
| Qualité | Description |
|---------|-------------|
| 🟢 Complète | Description + tags requis (@param, @return, etc.) |
| 🟡 Partielle | Description + quelques tags |
| 🟠 Minimale | Description seule |
| ⚫ Absente | Pas de Javadoc |

#### Propriétés
Même répartition avec comptage par niveau de qualité.

### 3. Propriétés

Pour chaque propriété :
- **Icône de qualité** (🟢🟡🟠⚫)
- **Numéro de ligne** dans le fichier source
- **Déclaration complète** (code Java)
- **Documentation actuelle** (si présente)
- **Tags présents** (ex: @see, @since)
- **Tags manquants** avec suggestions
- **Conseils d'amélioration**

### 4. Méthodes

Pour chaque méthode :
- **Icône de qualité** (🟢🟡🟠⚫)
- **Numéro de ligne** dans le fichier source
- **Signature complète** (code Java)
- **Documentation actuelle** (si présente)
- **Tags présents** (ex: @param, @return, @throws, @see, @example)
- **Tags manquants** avec suggestions
- **Conseils d'amélioration**

## 🎯 Utilisation

### Navigation depuis l'index

Depuis le fichier [ETAT_DOCUMENTATION.md](../ETAT_DOCUMENTATION.md), cliquez sur le nom d'une classe dans la table des matières :

```markdown
- ✅ [ApiKeysConfig](classes/ApiKeysConfig.md) - 83.3% (10/12) - 🟢1 🟡8 🟠1 ⚫2
```

### Navigation entre fichiers

Chaque fichier de classe contient un lien de retour vers l'index principal en haut de page.

### Recherche d'une classe

1. **Par ordre alphabétique** : Consultez la table des matières (A-Z)
2. **Par taux de documentation** : 
   - Top 5 meilleures classes (section 🏆)
   - Top 5 classes à améliorer (section ⚠️)
3. **Par fichier** : Listez ce répertoire et ouvrez directement le fichier `.md`

## 📊 Exemple d'analyse

Pour la classe **ApiKeysConfig** :

```
✅ ApiKeysConfig - 83.3% (10/12) - 🟢1 🟡8 🟠1 ⚫2
```

Signification :
- ✅ = Classe bien documentée (>80%)
- 83.3% = Taux global de documentation
- (10/12) = 10 éléments documentés sur 12
- 🟢1 = 1 élément avec doc complète (description + tous les tags)
- 🟡8 = 8 éléments avec doc partielle (manque quelques tags)
- 🟠1 = 1 élément avec doc minimale (description seule)
- ⚫2 = 2 éléments sans documentation

### Actions recommandées

1. **Priorité haute** : Documenter les 2 éléments ⚫ (absents)
2. **Priorité moyenne** : Compléter l'élément 🟠 avec des tags
3. **Amélioration** : Ajouter les tags manquants aux 8 éléments 🟡

## 🔧 Maintenance

### Régénération des fichiers

Pour mettre à jour tous les fichiers après modification du code :

```powershell
python scripts\analyse-javadoc.py
```

Le script :
1. Analyse tous les fichiers Java dans `src/`
2. Génère/met à jour `ETAT_DOCUMENTATION.md`
3. Génère/met à jour les 54 fichiers dans `classes/`

### Fichiers générés automatiquement

⚠️ **NE PAS MODIFIER MANUELLEMENT** les fichiers `.md` dans ce répertoire.

Ils sont régénérés automatiquement à chaque exécution du script `analyse-javadoc.py`.

Pour améliorer la documentation :
1. Modifier directement les fichiers Java source
2. Ajouter/améliorer les commentaires Javadoc
3. Relancer le script d'analyse

## 📈 Indicateurs de qualité

### Niveaux de documentation de classe

| Icône | Signification | Taux |
|-------|---------------|------|
| ✅ | Bien documentée | ≥ 80% |
| 🔶 | Partiellement documentée | 50-80% |
| ⚠️ | Peu documentée | 20-50% |
| ❌ | Très peu documentée | < 20% |

### Objectifs recommandés

- **Court terme** : Toutes les classes ≥ 50% (🔶 ou ✅)
- **Moyen terme** : 80% des classes ≥ 80% (✅)
- **Long terme** : 100% des classes ≥ 80% (✅)

## 🔍 Recherche dans les fichiers

### Exemples de requêtes

**Trouver toutes les méthodes complètes** :
```powershell
Get-ChildItem -Filter "*.md" | Select-String "### 🟢"
```

**Trouver les propriétés sans documentation** :
```powershell
Get-ChildItem -Filter "*.md" | Select-String "### ⚫.*propriété"
```

**Compter les éléments par qualité** :
```powershell
(Get-Content *.md | Select-String "### 🟢").Count  # Complets
(Get-Content *.md | Select-String "### 🟡").Count  # Partiels
(Get-Content *.md | Select-String "### 🟠").Count  # Minimaux
(Get-Content *.md | Select-String "### ⚫").Count   # Absents
```

## 📚 Documentation complémentaire

- [ETAT_DOCUMENTATION.md](../ETAT_DOCUMENTATION.md) - Index principal avec TDM
- [GUIDE_RAPPORT_JAVADOC.md](../GUIDE_RAPPORT_JAVADOC.md) - Guide d'utilisation détaillé
- [README.md](../../README.md) - Documentation du projet EditeurPanovisu

---

**Date de création :** 15/10/2025  
**Générés par :** `scripts/analyse-javadoc.py`  
**Version :** 3.0 (fichiers séparés + détails de qualité)
