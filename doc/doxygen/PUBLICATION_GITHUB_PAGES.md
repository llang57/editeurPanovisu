# 📘 Publication de la documentation sur GitHub Pages

## 🎯 Solution recommandée : GitHub Pages

Votre documentation sera accessible à l'adresse :
```
https://llang57.github.io/editeurPanovisu/
```

---

## 🚀 Méthode 1 : Branche `gh-pages` (Recommandée)

### Étape 1 : Créer la branche gh-pages

```powershell
# Générer la documentation
.\generate-doc.ps1

# Créer une branche orpheline pour GitHub Pages
git checkout --orphan gh-pages

# Supprimer tous les fichiers du staging
git rm -rf .

# Copier uniquement la documentation HTML
Copy-Item -Path "doc\doxygen\html\*" -Destination "." -Recurse

# Créer un fichier .nojekyll (important !)
New-Item -ItemType File -Name ".nojekyll"

# Ajouter un README
@"
# Documentation EditeurPanovisu

Documentation technique générée avec Doxygen.

📖 **[Accéder à la documentation](https://llang57.github.io/editeurPanovisu/)**

Dernière mise à jour : $(Get-Date -Format "dd/MM/yyyy HH:mm")
"@ | Out-File -FilePath "README.md" -Encoding UTF8

# Commiter
git add .
git commit -m "docs: Première publication de la documentation Doxygen"

# Pousser vers GitHub
git push origin gh-pages

# Retourner sur master
git checkout master
```

### Étape 2 : Activer GitHub Pages

1. Allez sur votre dépôt GitHub : https://github.com/llang57/editeurPanovisu
2. Cliquez sur **Settings** (⚙️)
3. Dans le menu de gauche, cliquez sur **Pages**
4. Dans "Source", sélectionnez **Branch: gh-pages** et **/root**
5. Cliquez sur **Save**

⏱️ Attendez 2-3 minutes, puis votre documentation sera accessible à :
```
https://llang57.github.io/editeurPanovisu/
```

---

## 🔄 Méthode 2 : GitHub Actions (Automatique)

Créez un workflow pour publier automatiquement à chaque commit.

### Créer le workflow

Créez le fichier `.github/workflows/docs.yml` :

```yaml
name: Générer et publier la documentation

on:
  push:
    branches: [ master ]
    paths:
      - 'src/**/*.java'
      - 'panovisu/**/*.js'
      - 'doc/doxygen/Doxyfile'
  workflow_dispatch:

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
    - name: Checkout
      uses: actions/checkout@v4
    
    - name: Installer Doxygen
      run: |
        sudo apt-get update
        sudo apt-get install -y doxygen graphviz
    
    - name: Générer la documentation
      run: |
        cd doc/doxygen
        doxygen Doxyfile
    
    - name: Déployer sur GitHub Pages
      uses: peaceiris/actions-gh-pages@v3
      with:
        github_token: ${{ secrets.GITHUB_TOKEN }}
        publish_dir: ./doc/doxygen/html
        publish_branch: gh-pages
        user_name: 'github-actions[bot]'
        user_email: 'github-actions[bot]@users.noreply.github.com'
        commit_message: 'docs: Mise à jour automatique de la documentation'
```

**Avantages** :
- ✅ Mise à jour automatique à chaque commit
- ✅ Pas besoin de générer manuellement
- ✅ Toujours synchronisé avec le code

---

## 📁 Méthode 3 : Dossier `/docs` sur master

Plus simple mais moins flexible.

```powershell
# Copier la doc dans un dossier /docs à la racine
Copy-Item -Path "doc\doxygen\html\*" -Destination "docs" -Recurse -Force

# Créer .nojekyll
New-Item -ItemType File -Path "docs\.nojekyll" -Force

# Commiter
git add docs/
git commit -m "docs: Publication de la documentation"
git push
```

Puis dans **Settings > Pages**, sélectionnez **Branch: master** et **/docs**.

---

## 🌍 Autres options de publication

### 2. **Read the Docs** (readthedocs.org)

**URL** : `https://editeurpanovisu.readthedocs.io/`

**Avantages** :
- ✅ Gratuit pour projets open source
- ✅ Multi-versions (v1.0, v2.0, latest, etc.)
- ✅ Recherche avancée
- ✅ Thèmes personnalisables

**Inconvénients** :
- ❌ Nécessite conversion en Sphinx/MkDocs (Doxygen non natif)
- ❌ Configuration plus complexe

---

### 3. **GitLab Pages** (si miroir GitLab)

Similaire à GitHub Pages, gratuit aussi.

---

### 4. **Netlify / Vercel** (CDN moderne)

**Avantages** :
- ✅ Gratuit jusqu'à 100 GB/mois
- ✅ CDN mondial ultra-rapide
- ✅ Prévisualisation des PR
- ✅ Déploiement automatique

**Configuration Netlify** :

1. Connectez votre dépôt GitHub
2. Build command : `cd doc/doxygen && doxygen Doxyfile`
3. Publish directory : `doc/doxygen/html`

---

### 5. **Hébergement personnalisé**

Si vous avez votre propre serveur :

```powershell
# Générer
.\generate-doc.ps1

# Compresser
Compress-Archive -Path "doc\doxygen\html\*" -DestinationPath "documentation.zip"

# Uploader via FTP/SFTP vers votre serveur
```

---

## 📊 Comparaison

| Solution | Gratuit | Auto-update | URL propre | Difficulté |
|----------|---------|-------------|------------|------------|
| **GitHub Pages** | ✅ | ✅ (avec Actions) | ✅ | ⭐ Facile |
| Read the Docs | ✅ | ✅ | ✅ | ⭐⭐⭐ Moyen |
| Netlify/Vercel | ✅ | ✅ | ✅ | ⭐⭐ Facile |
| GitLab Pages | ✅ | ✅ | ✅ | ⭐⭐ Facile |
| Serveur perso | ❌ | ❌ | ✅ | ⭐⭐⭐⭐ Difficile |

---

## 🎯 Recommandation finale

**Pour EditeurPanovisu, je recommande GitHub Pages avec GitHub Actions** :

1. ✅ Gratuit et illimité
2. ✅ Intégré à votre workflow
3. ✅ Mise à jour automatique
4. ✅ URL propre : `llang57.github.io/editeurPanovisu`
5. ✅ HTTPS automatique

---

## 🚀 Script automatique de publication

Créez `publish-docs.ps1` à la racine :

```powershell
#!/usr/bin/env pwsh
# Script de publication de la documentation sur GitHub Pages

param(
    [switch]$Force,
    [switch]$Help
)

if ($Help) {
    Write-Host @"
Usage: .\publish-docs.ps1 [-Force] [-Help]

Options:
  -Force    Force la publication même si pas de changements
  -Help     Affiche cette aide

Exemple:
  .\publish-docs.ps1          # Publication normale
  .\publish-docs.ps1 -Force   # Force la publication
"@
    exit 0
}

Write-Host "`n╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     Publication de la documentation sur GitHub Pages     ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan

# 1. Sauvegarder la branche actuelle
$currentBranch = git rev-parse --abbrev-ref HEAD
Write-Host "📌 Branche actuelle : $currentBranch" -ForegroundColor White

# 2. Générer la documentation
Write-Host "`n📚 Génération de la documentation..." -ForegroundColor Cyan
.\generate-doc.ps1
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Erreur lors de la génération !" -ForegroundColor Red
    exit 1
}

# 3. Vérifier que la branche gh-pages existe
Write-Host "`n🌿 Vérification de la branche gh-pages..." -ForegroundColor Cyan
$ghPagesBranchExists = git rev-parse --verify gh-pages 2>$null
if (!$ghPagesBranchExists) {
    Write-Host "⚠ La branche gh-pages n'existe pas encore" -ForegroundColor Yellow
    Write-Host "💡 Utilisez d'abord la Méthode 1 du guide PUBLICATION_GITHUB_PAGES.md" -ForegroundColor Yellow
    exit 1
}

# 4. Passer sur gh-pages
Write-Host "`n🔄 Basculement sur gh-pages..." -ForegroundColor Cyan
git checkout gh-pages
if ($LASTEXITCODE -ne 0) {
    Write-Host "✗ Erreur lors du checkout !" -ForegroundColor Red
    git checkout $currentBranch
    exit 1
}

# 5. Fusionner les changements depuis master (documentation uniquement)
Write-Host "`n📥 Récupération de la documentation depuis $currentBranch..." -ForegroundColor Cyan

# Supprimer l'ancienne doc
Remove-Item -Path "*.html" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "*.css" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "*.js" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "*.png" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "search" -Recurse -Force -ErrorAction SilentlyContinue

# Copier la nouvelle doc depuis master
git checkout $currentBranch -- doc/doxygen/html
Copy-Item -Path "doc\doxygen\html\*" -Destination "." -Recurse -Force
Remove-Item -Path "doc" -Recurse -Force

# 6. Vérifier les changements
$changes = git status --porcelain
if (!$changes -and !$Force) {
    Write-Host "`n✓ Aucun changement détecté, documentation déjà à jour" -ForegroundColor Green
    git checkout $currentBranch
    exit 0
}

# 7. Commiter et pousser
Write-Host "`n💾 Commit des changements..." -ForegroundColor Cyan
git add .
$date = Get-Date -Format "dd/MM/yyyy HH:mm"
git commit -m "docs: Mise à jour documentation - $date"

Write-Host "`n🚀 Publication sur GitHub Pages..." -ForegroundColor Cyan
git push origin gh-pages

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✓ Documentation publiée avec succès !" -ForegroundColor Green
    Write-Host "`n📖 Accessible à : https://llang57.github.io/editeurPanovisu/" -ForegroundColor Yellow
    Write-Host "⏱️  Délai de disponibilité : 2-3 minutes`n" -ForegroundColor Gray
} else {
    Write-Host "`n✗ Erreur lors de la publication !" -ForegroundColor Red
}

# 8. Retour sur la branche d'origine
git checkout $currentBranch
Write-Host "📌 Retour sur $currentBranch`n" -ForegroundColor White
```

Puis :

```powershell
# Publier la doc
.\publish-docs.ps1

# Force la publication
.\publish-docs.ps1 -Force
```

---

## 📝 Checklist de publication

- [ ] Documentation générée localement (`.\generate-doc.ps1`)
- [ ] Branche `gh-pages` créée (voir Méthode 1)
- [ ] GitHub Pages activé dans les Settings
- [ ] Fichier `.nojekyll` présent
- [ ] Documentation poussée sur GitHub
- [ ] URL testée : `https://llang57.github.io/editeurPanovisu/`

---

## 🎓 Aller plus loin

### Badge dans le README

Ajoutez dans votre `README.md` :

```markdown
[![Documentation](https://img.shields.io/badge/docs-Doxygen-blue.svg)](https://llang57.github.io/editeurPanovisu/)
```

### Lien dans la release

Dans vos notes de release GitHub, ajoutez :

```markdown
📖 **Documentation** : https://llang57.github.io/editeurPanovisu/
```

### Custom domain (optionnel)

Si vous avez un domaine (ex: `docs.panovisu.fr`) :

1. Ajoutez un fichier `CNAME` dans `gh-pages` :
   ```
   docs.panovisu.fr
   ```
2. Configurez un CNAME DNS pointant vers `llang57.github.io`

---

**🎯 Prochaine étape : Suivez la Méthode 1 ou créez le workflow GitHub Actions !**
