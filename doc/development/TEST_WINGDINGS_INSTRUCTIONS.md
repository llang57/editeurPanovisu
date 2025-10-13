# 🧪 Instructions de test - Wingdings dans WebView

## Étape 1 : Tester Wingdings (5 minutes)

### Méthode A : Via l'application (recommandé)

1. **Compiler** :
   ```powershell
   mvn clean compile
   ```

2. **Lancer l'application** :
   ```powershell
   mvn javafx:run
   ```

3. **Ouvrir le fichier de test** :
   - Menu : **Aide** → **Documentation** (F1)
   - Ou ouvrez manuellement : `aide\test-wingdings.md`

4. **Analyser les résultats** :
   - **Test 1** (Entités HTML) : ✅ Devrait fonctionner parfaitement
   - **Test 2** (Emojis Unicode) : ❌ Devrait afficher ������ (normal)
   - **Test 3** (Wingdings) : **❓ CRITIQUE - Notez le résultat !**
   - **Test 4** (Badges complets) : ✅ Devrait fonctionner

### Méthode B : Test rapide HTML

Créez un fichier `test-wingdings.html` et ouvrez-le dans un navigateur :

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        .wi {
            font-family: 'Wingdings', 'Webdings', 'Segoe UI Symbol', sans-serif;
            font-size: 1.5em;
        }
    </style>
</head>
<body>
    <h2>Test Wingdings</h2>
    <p><span class='wi'>&#128;</span> Document (128)</p>
    <p><span class='wi'>&#48;</span> Dossier (48)</p>
    <p><span class='wi'>&#112;</span> Ordinateur (112)</p>
    <p><span class='wi'>&#119;</span> Marteau (119)</p>
    <p><span class='wi'>&#236;</span> Monument (236)</p>
</body>
</html>
```

---

## Étape 2 : Interpréter les résultats

### ✅ Cas 1 : Wingdings fonctionne

**Symptômes** : Vous voyez des symboles Wingdings reconnaissables dans Test 3

**Action** : Exécutez le script de conversion automatique

```powershell
.\convert-emojis.ps1
mvn clean compile
mvn javafx:run
```

Puis testez `aide.md` (Menu Aide → Documentation).

---

### ❌ Cas 2 : Wingdings ne fonctionne PAS

**Symptômes** : Test 3 affiche des carrés vides ou caractères corrompus

**Diagnostic** : JavaFX WebView ne supporte pas Wingdings sur votre système

**Solution alternative** : Utiliser UNIQUEMENT des entités HTML standard

#### Modification requise dans MarkdownViewer.java

Remplacez TOUS les Wingdings par des entités HTML :

```java
// ❌ NE PAS utiliser
.replace("📚", "<span class='badge badge-doc'><span class='wi'>&#128;</span> DOC</span>")

// ✅ UTILISER à la place
.replace("📚", "<span class='badge badge-doc'>&#128214; DOC</span>")
```

**Table de fallback** (sans Wingdings) :

| Emoji | Fallback HTML Entity | Code |
|-------|---------------------|------|
| 📚 | Livre | `&#128214;` |
| 📱 | Mobile | `&#128241;` |
| 🏛️ | Monument | `&#127963;` |
| 🛒 | Panier | `&#128722;` |
| 📸 | Caméra | `&#128248;` |
| 💻 | Ordi | `&#128187;` |
| 🗺️ | Carte | `&#128506;` |
| 🎨 | Palette | `&#127912;` |

⚠️ **Attention** : Ces entités HTML peuvent AUSSI ne pas fonctionner dans WebView. Dans ce cas, utiliser des symboles ultra-simples :

```java
.replace("📚", "<span class='badge badge-doc'>● DOC</span>")
.replace("📱", "<span class='badge badge-mobile'>■ MOBILE</span>")
.replace("🏛️", "<span class='badge badge-monument'>▲ MONUMENT</span>")
```

---

## Étape 3 : Conversion automatique (si Wingdings fonctionne)

```powershell
# 1. Sauvegarde automatique créée
.\convert-emojis.ps1

# 2. Compiler
mvn clean compile

# 3. Test Build 2246
mvn javafx:run
```

---

## Étape 4 : Validation finale

Ouvrez l'application et vérifiez dans `aide.md` :

### Sections à vérifier

1. **Section "Génération de description IA"** (ligne ~410)
   - Badge `⚙ IA` doit s'afficher correctement

2. **Section "Éditeur d'Interface"** (ligne ~530)
   - Badges pour thème, design, interface

3. **Menu et Toolbar** (ligne ~123)
   - Tous les badges des menus

### Critères de succès

✅ Tous les badges affichent : **SYMBOLE + TEXTE + FOND COLORÉ**  
✅ Aucun ������ visible  
✅ Si symbole ne s'affiche pas, le TEXTE reste lisible  
✅ Couleurs cohérentes (vert=OK, rouge=erreur, etc.)

---

## 🆘 Dépannage

### Problème : Script PowerShell ne s'exécute pas

**Solution** :
```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
.\convert-emojis.ps1
```

### Problème : Wingdings affiche des carrés

**Cause** : Police Wingdings manquante ou WebView ne la charge pas

**Solution 1** : Vérifier que Wingdings est installée
```powershell
Get-ChildItem "C:\Windows\Fonts" | Where-Object { $_.Name -like "*wingding*" }
```

**Solution 2** : Utiliser Webdings en fallback
```css
.wi {
    font-family: 'Webdings', 'Segoe UI Symbol', sans-serif;
}
```

**Solution 3** : Abandonner les symboles, utiliser uniquement le texte
```java
.replace("📚", "<span class='badge badge-doc'>DOC</span>")
```

### Problème : Compilation échoue après script

**Solution** : Le script a peut-être corrompu le fichier avec des quotes

Restaurez le backup :
```powershell
$latest = Get-ChildItem "src\editeurpanovisu\MarkdownViewer.java.backup-*" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
Copy-Item $latest.FullName "src\editeurpanovisu\MarkdownViewer.java"
```

---

## 📊 Rapport de test

À la fin du test, notez :

```
=== RAPPORT DE TEST WINGDINGS ===

Build testé : 2245

Test 1 (Entités HTML) : [ ] ✅ Fonctionne [ ] ❌ Ne fonctionne pas
Test 2 (Emojis Unicode) : [ ] ❌ Affiche ������ (attendu)
Test 3 (Wingdings) : [ ] ✅ Fonctionne [ ] ❌ Ne fonctionne pas
Test 4 (Badges complets) : [ ] ✅ Fonctionne [ ] ❌ Ne fonctionne pas

Solution choisie :
[ ] Wingdings + Entités HTML (si Test 3 OK)
[ ] Uniquement Entités HTML (si Test 3 KO)
[ ] Badges texte uniquement (si tout KO)

Commentaires :
___________________________________________
___________________________________________
```

---

## ✅ Prochaine étape

Une fois le test terminé, **REVENEZ ME VOIR** avec le résultat du **Test 3 (Wingdings)** pour que je puisse :

- ✅ **Si Wingdings OK** : Finaliser la conversion des 120+ emojis restants
- ❌ **Si Wingdings KO** : Adapter la stratégie avec fallback complet
