# 📋 Instructions pour copier Wingdings.ttf

## ⚠️ Action OBLIGATOIRE avant de compiler

Le WebView JavaFX ne peut pas accéder directement aux polices Windows système.  
Il faut **copier le fichier Wingdings** dans les ressources du projet.

---

## 📂 Étape 1 : Copier le fichier

```powershell
# Copier wingdings.ttf depuis Windows vers le projet
Copy-Item "C:\Windows\Fonts\wingding.ttf" "src\editeurpanovisu\fonts\wingdings.ttf"
```

**Note importante** : Le fichier Windows s'appelle `wingding.ttf` (sans 's') mais on le renomme `wingdings.ttf` dans le projet.

---

## ✅ Vérification

```powershell
# Vérifier que le fichier existe
Test-Path "src\editeurpanovisu\fonts\wingdings.ttf"
```

Doit retourner : `True`

---

## 🔧 Étape 2 : Compiler

Une fois le fichier copié :

```powershell
mvn clean compile
mvn javafx:run
```

Le code Java chargera automatiquement la police au démarrage via `Font.loadFont()`.

---

## 📊 Taille du fichier

- wingdings.ttf : ~45-50 KB
- Cela n'impacte pas significativement la taille du JAR final

---

## 🚨 Problème avec Webdings (alternative)

Si Wingdings ne suffit pas, vous pouvez aussi copier Webdings :

```powershell
Copy-Item "C:\Windows\Fonts\webdings.ttf" "src\editeurpanovisu\fonts\webdings.ttf"
```

---

## 📝 Résumé

1. ✅ Créer le dossier `src/editeurpanovisu/fonts/` (déjà fait)
2. ✅ Copier `C:\Windows\Fonts\wingding.ttf` → `src/editeurpanovisu/fonts/wingdings.ttf`
3. ✅ Compiler avec Maven
4. ✅ Tester avec `aide/test-wingdings.md`

---

## ⚖️ Note légale

Wingdings est une police Microsoft. L'inclusion dans une application Java pour usage interne est généralement acceptable, mais pour une distribution commerciale, vérifiez les conditions de licence Microsoft.
