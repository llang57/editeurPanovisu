# Script PowerShell simple pour mettre à jour les liens GitHub Discussions
param(
    [string]$Mode = "update"
)

# URLs des discussions GitHub
$FR_ANNOUNCEMENT = "https://github.com/llang57/editeurPanovisu/discussions/6"
$FR_FAQ = "https://github.com/llang57/editeurPanovisu/discussions/8"
$FR_MIGRATION = "https://github.com/llang57/editeurPanovisu/discussions/10"
$EN_ANNOUNCEMENT = "https://github.com/llang57/editeurPanovisu/discussions/7"
$EN_FAQ = "https://github.com/llang57/editeurPanovisu/discussions/9"
$EN_MIGRATION = "https://github.com/llang57/editeurPanovisu/discussions/11"

Write-Host "Mise a jour des liens GitHub Discussions..." -ForegroundColor Green
Write-Host ""

# Fonction pour mettre à jour README.md (français)
function Update-ReadmeFR {
    $file = "README.md"
    
    if (-not (Test-Path $file)) {
        Write-Host "Erreur: $file non trouve" -ForegroundColor Red
        return $false
    }
    
    $content = Get-Content $file -Raw
    
    # Nouveau contenu de la section
    $newSection = @"
### 📢 Discussions GitHub (RECOMMANDÉ)
- 🇫🇷 [📢 Annonce v3.1.0]($FR_ANNOUNCEMENT) • [❓ FAQ]($FR_FAQ) • [🚀 Guide migration]($FR_MIGRATION)
- 🇬🇧 [📢 v3.1.0 Announcement]($EN_ANNOUNCEMENT) • [❓ FAQ]($EN_FAQ) • [🚀 Migration Guide]($EN_MIGRATION)

"@
    
    # Chercher et remplacer la section existante
    $pattern = '### 📢 Discussions GitHub \(RECOMMANDÉ\).*?(?=### 📖|$)'
    $content = $content -replace $pattern, $newSection, 'Singleline'
    
    Set-Content $file $content -NoNewline
    Write-Host "✅ $file mis a jour" -ForegroundColor Green
    return $true
}

# Fonction pour mettre à jour README_EN.md (anglais)
function Update-ReadmeEN {
    $file = "README_EN.md"
    
    if (-not (Test-Path $file)) {
        Write-Host "Erreur: $file non trouve" -ForegroundColor Red
        return $false
    }
    
    $content = Get-Content $file -Raw
    
    # Nouveau contenu de la section
    $newSection = @"
### 📢 GitHub Discussions (RECOMMENDED)
- 🇬🇧 [📢 v3.1.0 Announcement]($EN_ANNOUNCEMENT) • [❓ FAQ]($EN_FAQ) • [🚀 Migration Guide]($EN_MIGRATION)
- 🇫🇷 [📢 Annonce v3.1.0]($FR_ANNOUNCEMENT) • [❓ FAQ]($FR_FAQ) • [🚀 Guide migration]($FR_MIGRATION)

"@
    
    # Chercher et remplacer la section existante
    $pattern = '### 📢 GitHub Discussions \(RECOMMENDED\).*?(?=### 📖|$)'
    $content = $content -replace $pattern, $newSection, 'Singleline'
    
    Set-Content $file $content -NoNewline
    Write-Host "✅ $file mis a jour" -ForegroundColor Green
    return $true
}

# Exécution
Write-Host "URLs configurees:" -ForegroundColor Yellow
Write-Host "  FR: Discussions 6, 8, 10" -ForegroundColor White
Write-Host "  EN: Discussions 7, 9, 11" -ForegroundColor White
Write-Host ""

$success = $true
$success = (Update-ReadmeFR) -and $success
$success = (Update-ReadmeEN) -and $success

if ($success) {
    Write-Host ""
    Write-Host "🎉 Tous les README ont ete mis a jour avec succes!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Prochaines etapes:" -ForegroundColor Yellow
    Write-Host "1. Verifiez: git diff README.md README_EN.md" -ForegroundColor White
    Write-Host "2. Commitez: git add README.md README_EN.md" -ForegroundColor White
    Write-Host "3. Commitez: git commit -m 'docs: Update README with GitHub Discussions links'" -ForegroundColor White
    Write-Host "4. Poussez: git push origin master" -ForegroundColor White
} else {
    Write-Host "Erreur lors de la mise a jour" -ForegroundColor Red
}