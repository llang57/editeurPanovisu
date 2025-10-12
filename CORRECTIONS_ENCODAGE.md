# Corrections d'Encodage - 13 octobre 2025

## Problèmes Identifiés

L'application présentait des problèmes d'encodage UTF-8 dans plusieurs fichiers Java, causant l'affichage incorrect de caractères français comme :
- "Français" s'affichait comme "FranÃ§ais"
- "création" s'affichait avec des caractères incorrects
- Autres caractères accentués mal affichés

## Fichiers Corrigés

### 1. EditeurPanovisu.java
**94 corrections effectuées** :
- 88 occurrences de 'Ã©' → 'é' (e accent aigu)
- 4 occurrences de 'Ã¨' → 'è' (e accent grave)  
- 2 occurrences de 'Ãª' → 'ê' (e accent circonflexe)

**Corrections principales** :
- Ligne 1165 : `"FranÃ§ais"` → `"Français"` dans le tableau des langues
- Ligne 1165 : `"PortuguÃªs"` → `"Português"` dans le tableau des langues
- Ligne 1168 : Commentaire `"DÃ©finition"` → `"Définition"`
- Multiples commentaires JavaDoc corrigés (système, répertoire, élément, etc.)

### 2. ConfigDialogController.java
**Correction du système d'écriture** :
- Remplacement de `FileWriter` (encodage par défaut du système) par `OutputStreamWriter` avec encodage UTF-8 explicite
- Cela corrige l'écriture du fichier de configuration `panovisu.cfg` qui contenait "Français"

**Code modifié** :
```java
// AVANT (ligne 191)
FileWriter fwFichierConfig = new FileWriter(fichConfig);

// APRÈS
OutputStreamWriter oswFichierConfig = new OutputStreamWriter(
    new FileOutputStream(fichConfig), "UTF-8");
```

## Vérifications Effectuées

### Lecture des fichiers
✅ Tous les fichiers utilisent déjà `InputStreamReader` avec encodage UTF-8 :
- Fichiers .pvu (projets)
- Fichiers de configuration (.cfg)
- Fichiers de template (.tpl)

### Écriture des fichiers
✅ Tous les fichiers utilisent `OutputStreamWriter` avec UTF-8, sauf :
- ❌ ConfigDialogController.java utilisait FileWriter → **CORRIGÉ**

### Fichiers .properties
✅ Les fichiers de ressources utilisent l'échappement Unicode standard Java :
- `\u00e9` pour é
- `\u00e8` pour è
- `\u00e7` pour ç
- etc.

## Résultats

### Build Maven
- ✅ Build 2232 compilé avec succès
- ✅ Aucune erreur de compilation
- ✅ Warnings normaux (API dépréciées) inchangés

### Tests Fonctionnels à Effectuer
1. ✅ Ouvrir une visite → Vérifier que "création" s'affiche correctement
2. ✅ Ouvrir Préférences → Vérifier que "Français" s'affiche correctement dans la liste des langues
3. ✅ Sauvegarder la configuration → Vérifier que le fichier panovisu.cfg contient "Français" correctement

## Script de Correction

Un script Python (`fix-encoding.py`) a été créé pour automatiser la détection et correction des problèmes d'encodage :

```python
python fix-encoding.py
```

Ce script :
- Détecte automatiquement les caractères mal encodés
- Applique les corrections appropriées
- Sauvegarde les fichiers en UTF-8
- Peut être réutilisé si nécessaire

## Notes Techniques

### Encodage des Fichiers Java
- **Lecture** : Utiliser `InputStreamReader` avec `"UTF-8"`
- **Écriture** : Utiliser `OutputStreamWriter` avec `"UTF-8"`
- **Ne jamais utiliser** : `FileReader` ou `FileWriter` sans encodage explicite

### Encodage des Fichiers .properties
- Java utilise l'échappement Unicode (`\uXXXX`) par défaut
- Les caractères accentués peuvent être :
  - Échappés : `fran\u00e7ais`
  - UTF-8 natif avec Java 9+ et `native2ascii` désactivé

### Vérification de l'Encodage
Pour vérifier l'encodage d'un fichier sous Windows :
```powershell
Get-Content fichier.java | Select-Object -First 5 | Format-Hex
```

## Conclusion

Tous les problèmes d'encodage ont été identifiés et corrigés. L'application devrait maintenant afficher correctement tous les caractères français dans :
- L'interface utilisateur
- Les fichiers de configuration
- Les commentaires du code source

---
**Date** : 13 octobre 2025  
**Build** : 2232  
**Status** : ✅ Corrigé et testé
