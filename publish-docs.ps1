#!/usr/bin/env pwsh
# Script de publication de la documentation sur GitHub Pages
# Usage : .\publish-docs.ps1 [-Force] [-Help]

param(
    [switch]$Force,
    [switch]$Help
)

# Afficher l'aide
if ($Help) {
    Write-Host @"
╔═══════════════════════════════════════════════════════════╗
║     Publication de la documentation sur GitHub Pages     ║
╚═══════════════════════════════════════════════════════════╝

Usage: .\publish-docs.ps1 [-Force] [-Help]

Options:
  -Force    Force la publication même si pas de changements
  -Help     Affiche cette aide

Exemples:
  .\publish-docs.ps1          # Publication normale
  .\publish-docs.ps1 -Force   # Force la publication

La documentation sera publiée à :
  https://llang57.github.io/editeurPanovisu/

Note : Pour la première utilisation, suivez le guide
       doc/doxygen/PUBLICATION_GITHUB_PAGES.md
"@
    exit 0
}

# Fonctions d'affichage
function Write-Info($message) { Write-Host $message -ForegroundColor Cyan }
function Write-Success($message) { Write-Host $message -ForegroundColor Green }
function Write-Error($message) { Write-Host $message -ForegroundColor Red }
function Write-Warning($message) { Write-Host $message -ForegroundColor Yellow }

# En-tête
Write-Host ""
Write-Host "╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     Publication de la documentation sur GitHub Pages     ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# 1. Vérifier que nous sommes dans un dépôt Git
if (!(Test-Path ".git")) {
    Write-Error "✗ Erreur : Ce n'est pas un dépôt Git !"
    exit 1
}

# 2. Sauvegarder la branche actuelle
$currentBranch = git rev-parse --abbrev-ref HEAD
Write-Info "📌 Branche actuelle : $currentBranch"

# Vérifier qu'il n'y a pas de modifications non commitées
$uncommittedChanges = git status --porcelain
if ($uncommittedChanges) {
    Write-Warning "⚠ Vous avez des modifications non commitées !"
    Write-Warning "   Commitez ou stashez vos changements avant de publier."
    $response = Read-Host "   Voulez-vous continuer quand même ? (o/N)"
    if ($response -ne "o" -and $response -ne "O") {
        Write-Info "Publication annulée."
        exit 0
    }
}

# 3. Générer la documentation
Write-Host ""
Write-Info "📚 Génération de la documentation..."
.\generate-doc.ps1
if ($LASTEXITCODE -ne 0) {
    Write-Error "✗ Erreur lors de la génération de la documentation !"
    exit 1
}
Write-Success "✓ Documentation générée"

# Vérifier que la doc HTML existe
if (!(Test-Path "doc\doxygen\html\index.html")) {
    Write-Error "✗ Erreur : La documentation HTML n'a pas été générée !"
    exit 1
}

# 4. Vérifier que la branche gh-pages existe
Write-Host ""
Write-Info "🌿 Vérification de la branche gh-pages..."
$ghPagesBranchExists = git rev-parse --verify gh-pages 2>$null
if (!$ghPagesBranchExists) {
    Write-Warning "⚠ La branche gh-pages n'existe pas encore"
    Write-Host ""
    Write-Info "Création de la branche gh-pages..."
    
    # Créer une branche orpheline
    git checkout --orphan gh-pages
    git rm -rf .
    
    # Copier la documentation
    Copy-Item -Path "doc\doxygen\html\*" -Destination "." -Recurse
    
    # Créer .nojekyll
    New-Item -ItemType File -Name ".nojekyll" -Force | Out-Null
    
    # Créer README
    $readmeContent = @"
# 📖 Documentation EditeurPanovisu

Documentation technique générée avec Doxygen.

**[📚 Accéder à la documentation](https://llang57.github.io/editeurPanovisu/)**

Dernière mise à jour : $(Get-Date -Format "dd/MM/yyyy HH:mm")
"@
    $readmeContent | Out-File -FilePath "README.md" -Encoding UTF8
    
    # Commiter
    git add .
    git commit -m "docs: Première publication de la documentation Doxygen"
    
    Write-Success "✓ Branche gh-pages créée"
    
    # Retourner sur la branche d'origine
    git checkout $currentBranch
    
    Write-Host ""
    Write-Success "✓ Configuration initiale terminée !"
    Write-Host ""
    Write-Warning "⚠ Action requise : Activez GitHub Pages dans les paramètres du dépôt"
    Write-Info "   1. Allez sur https://github.com/llang57/editeurPanovisu/settings/pages"
    Write-Info "   2. Dans 'Source', sélectionnez 'gh-pages' et '/root'"
    Write-Info "   3. Cliquez sur 'Save'"
    Write-Host ""
    Write-Info "Puis lancez à nouveau ce script pour publier."
    exit 0
}

# 5. Sauvegarder les changements actuels si nécessaire
$hasStash = $false
if ($uncommittedChanges) {
    Write-Info "💾 Sauvegarde temporaire des modifications..."
    git stash push -m "Sauvegarde automatique avant publication docs"
    $hasStash = $true
}

# 6. Basculer sur gh-pages
Write-Host ""
Write-Info "🔄 Basculement sur gh-pages..."
git checkout gh-pages
if ($LASTEXITCODE -ne 0) {
    Write-Error "✗ Erreur lors du basculement sur gh-pages !"
    if ($hasStash) {
        git stash pop
    }
    exit 1
}

# 7. Récupérer la nouvelle documentation
Write-Host ""
Write-Info "📥 Récupération de la documentation depuis $currentBranch..."

# Supprimer l'ancienne doc (mais garder .git, README.md, .nojekyll)
Get-ChildItem -Exclude @(".git", "README.md", ".nojekyll") | Remove-Item -Recurse -Force

# Copier la nouvelle doc depuis master
git checkout $currentBranch -- doc/doxygen/html
if (Test-Path "doc\doxygen\html") {
    Copy-Item -Path "doc\doxygen\html\*" -Destination "." -Recurse -Force
    Remove-Item -Path "doc" -Recurse -Force
    Write-Success "✓ Documentation copiée"
} else {
    Write-Error "✗ Erreur : Impossible de récupérer la documentation depuis $currentBranch"
    git checkout $currentBranch
    if ($hasStash) {
        git stash pop
    }
    exit 1
}

# S'assurer que .nojekyll existe
if (!(Test-Path ".nojekyll")) {
    New-Item -ItemType File -Name ".nojekyll" -Force | Out-Null
}

# Mettre à jour le README
$readmeContent = @"
# 📖 Documentation EditeurPanovisu

Documentation technique générée avec Doxygen.

**[📚 Accéder à la documentation](https://llang57.github.io/editeurPanovisu/)**

## 📊 Informations

- **Version** : 3.0.0
- **Dernière mise à jour** : $(Get-Date -Format "dd/MM/yyyy à HH:mm")
- **Générateur** : Doxygen
- **Langage** : Java + JavaScript

Cette documentation est mise à jour automatiquement via GitHub Actions.
"@
$readmeContent | Out-File -FilePath "README.md" -Encoding UTF8

# 8. Vérifier les changements
$changes = git status --porcelain
if (!$changes -and !$Force) {
    Write-Host ""
    Write-Success "✓ Aucun changement détecté, documentation déjà à jour"
    git checkout $currentBranch
    if ($hasStash) {
        git stash pop
    }
    Write-Host ""
    Write-Info "📖 Documentation disponible à : https://llang57.github.io/editeurPanovisu/"
    Write-Host ""
    exit 0
}

# 9. Commiter les changements
Write-Host ""
Write-Info "💾 Commit des changements..."
git add .
$date = Get-Date -Format "dd/MM/yyyy HH:mm"
git commit -m "docs: Mise à jour documentation - $date"
Write-Success "✓ Changements commités"

# 10. Pousser vers GitHub
Write-Host ""
Write-Info "🚀 Publication sur GitHub Pages..."
git push origin gh-pages

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Success "╔═══════════════════════════════════════════════════════════╗"
    Write-Success "║          ✓ Documentation publiée avec succès !           ║"
    Write-Success "╚═══════════════════════════════════════════════════════════╝"
    Write-Host ""
    Write-Host "📖 La documentation sera accessible dans 2-3 minutes à :" -ForegroundColor Cyan
    Write-Host "   https://llang57.github.io/editeurPanovisu/" -ForegroundColor Yellow
    Write-Host ""
    
    # Afficher les statistiques
    $htmlFiles = (Get-ChildItem -Path "." -Filter "*.html" -Recurse).Count
    Write-Info "📊 Statistiques :"
    Write-Host "   - Fichiers HTML : $htmlFiles" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Error "╔═══════════════════════════════════════════════════════════╗"
    Write-Error "║            ✗ Erreur lors de la publication !             ║"
    Write-Error "╚═══════════════════════════════════════════════════════════╝"
    Write-Host ""
    Write-Warning "Vérifiez vos droits d'accès au dépôt GitHub."
    Write-Host ""
}

# 11. Retourner sur la branche d'origine
git checkout $currentBranch
if ($hasStash) {
    Write-Info "♻️  Restauration des modifications..."
    git stash pop
}
Write-Info "📌 Retour sur $currentBranch"
Write-Host ""
