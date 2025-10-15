# 🎯 Configuration GitHub Pages - Quelle option choisir ?

## 📍 Vous êtes ici

**Settings > Pages > Build and deployment > Source**

Vous avez **2 options** :
- ⚙️ **GitHub Actions**
- 🌿 **Deploy from a branch**

---

## ✅ CHOISISSEZ : "Deploy from a branch"

### Pourquoi ?

Parce que votre workflow GitHub Actions (`.github/workflows/docs.yml`) est configuré pour :
1. Générer la documentation
2. **Créer/mettre à jour la branche `gh-pages`**
3. GitHub Pages publie **DEPUIS cette branche**

---

## 🔧 Configuration exacte

```
Build and deployment
│
├─ Source: Deploy from a branch ▼  ← Sélectionnez ceci
│
└─ Branch: 
   ├─ Branch: gh-pages ▼           ← Sélectionnez gh-pages
   ├─ Folder: / (root) ▼            ← Laissez / (root)
   └─ [Save]                        ← Cliquez sur Save
```

---

## ⚠️ La branche `gh-pages` n'existe pas encore ?

**C'est normal !** Elle sera créée automatiquement.

### Ordre des opérations

1. **Maintenant** : Sélectionnez "Deploy from a branch"
2. **Commitez et poussez** :
   ```powershell
   git add .
   git commit -m "docs: Configuration Doxygen + GitHub Pages"
   git push origin master
   ```

3. **Attendez GitHub Actions** :
   - Allez dans l'onglet **Actions**
   - Le workflow "📚 Générer et publier la documentation" démarre
   - Attendez qu'il soit ✅ vert (1-2 minutes)

4. **Revenez sur Settings > Pages** :
   - La branche `gh-pages` apparaît maintenant dans la liste
   - Sélectionnez-la : `gh-pages` + `/ (root)`
   - Cliquez sur **Save**

---

## 🤔 Pourquoi pas "GitHub Actions" ?

L'option "GitHub Actions" est pour les workflows qui :
- Publient **directement** le contenu (sans passer par une branche)
- Utilisent `actions/deploy-pages@v2` ou similaire

**Votre configuration actuelle utilise** :
- `peaceiris/actions-gh-pages@v3` 
- Qui **crée la branche `gh-pages`**
- Donc vous devez sélectionner "Deploy from a branch"

---

## 📋 Résumé rapide

| Option | Quand l'utiliser ? |
|--------|-------------------|
| **GitHub Actions** | Workflow qui publie directement (actions/deploy-pages) |
| **Deploy from a branch** ✅ | Workflow qui crée une branche gh-pages (votre cas) |

---

## ✅ Configuration finale

Votre configuration devrait ressembler à ça :

```
Build and deployment

Source
  Deploy from a branch

Branch
  gh-pages  / (root)  [Save]

Custom domain (optionnel - laissez vide)
  [                                      ]
```

---

## 🚀 Après avoir cliqué sur Save

1. GitHub Pages commence à publier (2-3 minutes)
2. Un bandeau vert apparaît : "Your site is live at https://llang57.github.io/editeurPanovisu/"
3. Visitez votre documentation ! 🎉

---

## 🆘 Problème ?

Si `gh-pages` n'apparaît pas dans la liste :

1. Vérifiez que vous avez bien **poussé les fichiers** sur GitHub
2. Allez dans **Actions** et vérifiez que le workflow s'est exécuté
3. Si le workflow échoue, regardez les logs pour voir l'erreur
4. Une fois le workflow ✅, rafraîchissez la page Settings > Pages

---

**🎯 L'essentiel : Sélectionnez "Deploy from a branch", puis gh-pages + / (root) + Save !**
