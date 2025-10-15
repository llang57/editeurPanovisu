# ⚠️ Configuration GitHub Pages - Instructions importantes

## 🎯 Vous êtes sur cette page ?

**URL** : `https://github.com/llang57/editeurPanovisu/settings/pages`

Et on vous demande : **"What domain would you like to add?"**

---

## ❌ NE REMPLISSEZ PAS CE CHAMP !

**La section "Custom domain" / "Add a verified domain" est OPTIONNELLE.**

Vous n'avez **PAS besoin** de domaine personnalisé !

L'URL GitHub gratuite est parfaite : `https://llang57.github.io/editeurPanovisu/`

---

## ✅ Ce qu'il faut faire à la place

### Étape 1 : Ignorez la section "Custom domain"

Laissez le champ vide et faites défiler vers le bas.

### Étape 2 : Trouvez la section "Build and deployment"

C'est la section importante pour vous !

### Étape 3 : Configurez la "Source"

Dans le menu déroulant **"Source"** :

1. **Branch** : Sélectionnez `gh-pages`
2. **Folder** : Sélectionnez `/ (root)`
3. Cliquez sur **"Save"**

**Visuel de la configuration** :

```
Build and deployment
├─ Source: [Deploy from a branch ▼]
├─ Branch: [gh-pages ▼] [/ (root) ▼] [Save]
└─ ...
```

---

## 🚨 La branche `gh-pages` n'apparaît pas ?

**C'est normal !** La branche sera créée automatiquement par GitHub Actions.

### Solution : Suivez cet ordre

1. **D'ABORD** : Commitez et poussez vos fichiers
   ```powershell
   git commit -m "docs: Configuration Doxygen + GitHub Pages"
   git push origin master
   ```

2. **ENSUITE** : Attendez que GitHub Actions termine
   - Allez dans l'onglet **"Actions"** de votre dépôt
   - Regardez l'exécution du workflow "📚 Générer et publier la documentation Doxygen"
   - Attendez qu'il soit ✅ (vert)

3. **PUIS** : Revenez sur **Settings > Pages**
   - La branche `gh-pages` sera maintenant disponible dans la liste
   - Sélectionnez-la et cliquez sur Save

---

## 📋 Checklist de configuration

- [ ] Fichiers commités et poussés sur GitHub
- [ ] GitHub Actions a terminé (onglet "Actions" ✅ vert)
- [ ] Branche `gh-pages` créée par le workflow
- [ ] Settings > Pages > Source configurée sur `gh-pages`
- [ ] Bouton "Save" cliqué
- [ ] Attente de 2-3 minutes pour la publication

---

## 🌐 URL de votre documentation

Après configuration, votre documentation sera accessible à :

```
https://llang57.github.io/editeurPanovisu/
```

**Aucun domaine personnalisé nécessaire !**

---

## 🤔 Vous voulez quand même un domaine personnalisé ?

Si vous avez votre propre domaine (exemple : `docs.monsite.fr`) :

1. **Dans Settings > Pages** :
   - Section "Custom domain"
   - Entrez votre domaine : `docs.monsite.fr`
   - Cliquez sur "Save"

2. **Chez votre registrar DNS** (OVH, Cloudflare, etc.) :
   - Créez un enregistrement CNAME pointant vers `llang57.github.io`
   - Exemple : `docs.monsite.fr` → `llang57.github.io`

3. **Retournez sur GitHub Pages** :
   - Cochez "Enforce HTTPS" (recommandé)

Mais **ce n'est pas nécessaire** pour que votre documentation fonctionne !

---

## 💡 Résumé rapide

| Obligatoire | Optionnel |
|-------------|-----------|
| ✅ Configurer Source = `gh-pages` | ⚪ Custom domain |
| ✅ Pousser les fichiers sur GitHub | ⚪ Domaine personnalisé |
| ✅ Attendre GitHub Actions | ⚪ DNS CNAME |
| ✅ Cliquer sur "Save" | |

---

## 🆘 Besoin d'aide ?

Consultez le guide complet : `doc/doxygen/PUBLICATION_GITHUB_PAGES.md`

Ou lancez le script manuel : `.\publish-docs.ps1`

---

**🎯 L'essentiel : Ignorez "Custom domain" et configurez juste la Source sur `gh-pages` !**
