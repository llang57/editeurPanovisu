# Script pour mettre à jour les liens GitHub Discussions dans les README
# Usage: Modifiez les URLs ci-dessous avec les vraies URLs de vos discussions

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "MISE À JOUR DES LIENS DISCUSSIONS" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Cyan

# URLs des discussions GitHub (À MODIFIER avec les vraies URLs)
$DISCUSSIONS = @{
    "FR_ANNOUNCEMENT" = "https://github.com/llang57/editeurPanovisu/discussions/6"
    "FR_FAQ" = "https://github.com/llang57/editeurPanovisu/discussions/8"  
    "FR_MIGRATION" = "https://github.com/llang57/editeurPanovisu/discussions/10"
    "EN_ANNOUNCEMENT" = "https://github.com/llang57/editeurPanovisu/discussions/7"
    "EN_FAQ" = "https://github.com/llang57/editeurPanovisu/discussions/9"
    "EN_MIGRATION" = "https://github.com/llang57/editeurPanovisu/discussions/11"
}

Write-Host "📋 URLs des discussions à mettre à jour :" -ForegroundColor Yellow
Write-Host ""
Write-Host "🇫🇷 FRANÇAIS :" -ForegroundColor Blue
Write-Host "  📢 Annonce    : $($DISCUSSIONS.FR_ANNOUNCEMENT)" -ForegroundColor White
Write-Host "  ❓ FAQ        : $($DISCUSSIONS.FR_FAQ)" -ForegroundColor White  
Write-Host "  🚀 Migration  : $($DISCUSSIONS.FR_MIGRATION)" -ForegroundColor White
Write-Host ""
Write-Host "🇬🇧 ENGLISH :" -ForegroundColor Blue
Write-Host "  📢 Announce  : $($DISCUSSIONS.EN_ANNOUNCEMENT)" -ForegroundColor White
Write-Host "  ❓ FAQ       : $($DISCUSSIONS.EN_FAQ)" -ForegroundColor White
Write-Host "  🚀 Migration : $($DISCUSSIONS.EN_MIGRATION)" -ForegroundColor White
Write-Host ""

# Vérifier si toutes les URLs sont configurées
$allConfigured = $true
foreach ($key in $DISCUSSIONS.Keys) {
    if ($DISCUSSIONS[$key] -like "*XXX*") {
        $allConfigured = $false
        break
    }
}

if (-not $allConfigured) {
    Write-Host "⚠️  ATTENTION : Vous devez d'abord modifier ce script !" -ForegroundColor Red
    Write-Host ""
    Write-Host "1. Remplacez les 'XXX' par les vrais numéros de discussions" -ForegroundColor Yellow
    Write-Host "2. Exemple : 'discussions/XXX' → 'discussions/123'" -ForegroundColor Yellow
    Write-Host "3. Relancez ce script" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "📍 Pour trouver les numéros de discussions :" -ForegroundColor Cyan
    Write-Host "   - Allez sur https://github.com/llang57/editeurPanovisu/discussions" -ForegroundColor White
    Write-Host "   - Cliquez sur chaque discussion" -ForegroundColor White
    Write-Host "   - L'URL affiche le numéro : discussions/123" -ForegroundColor White
    Write-Host ""
    
    # Ouvrir la page des discussions
    $openDiscussions = Read-Host "Voulez-vous ouvrir la page des discussions maintenant ? (O/N)"
    if ($openDiscussions -eq "O" -or $openDiscussions -eq "o") {
        Start-Process "https://github.com/llang57/editeurPanovisu/discussions"
        Write-Host "✅ Page des discussions ouverte dans le navigateur" -ForegroundColor Green
    }
    
    Write-Host ""
    Write-Host "Modifiez ce script et relancez-le quand vous aurez les URLs !" -ForegroundColor Yellow
    exit
}

Write-Host "✅ Toutes les URLs sont configurées !" -ForegroundColor Green
Write-Host ""

# Fonction pour mettre à jour un fichier
function Update-ReadmeFile {
    param(
        [string]$FilePath,
        [hashtable]$Links,
        [string]$Language
    )
    
    Write-Host "📝 Mise à jour de $FilePath..." -ForegroundColor Cyan
    
    if (-not (Test-Path $FilePath)) {
        Write-Host "❌ Fichier non trouvé : $FilePath" -ForegroundColor Red
        return $false
    }
    
    $content = Get-Content $FilePath -Raw
    
    if ($Language -eq "FR") {
        # Mise à jour du README français
        $newSection = "### " + [char]0x1F4E2 + " Discussions GitHub (RECOMMANDÉ)`n"
        $newSection += "- " + [char]0x1F1EB + [char]0x1F1F7 + " [" + [char]0x1F4E2 + " Annonce v3.1.0]($($Links.FR_ANNOUNCEMENT)) • [" + [char]0x2753 + " FAQ]($($Links.FR_FAQ)) • [" + [char]0x1F680 + " Guide migration]($($Links.FR_MIGRATION))`n"
        $newSection += "- " + [char]0x1F1EC + [char]0x1F1E7 + " [" + [char]0x1F4E2 + " v3.1.0 Announcement]($($Links.EN_ANNOUNCEMENT)) • [" + [char]0x2753 + " FAQ]($($Links.EN_FAQ)) • [" + [char]0x1F680 + " Migration Guide]($($Links.EN_MIGRATION))"
    } else {
        # Mise à jour du README anglais
        $newSection = "### " + [char]0x1F4E2 + " GitHub Discussions (RECOMMENDED)`n"
        $newSection += "- " + [char]0x1F1EC + [char]0x1F1E7 + " [" + [char]0x1F4E2 + " v3.1.0 Announcement]($($Links.EN_ANNOUNCEMENT)) • [" + [char]0x2753 + " FAQ]($($Links.EN_FAQ)) • [" + [char]0x1F680 + " Migration Guide]($($Links.EN_MIGRATION))`n"
        $newSection += "- " + [char]0x1F1EB + [char]0x1F1F7 + " [" + [char]0x1F4E2 + " Annonce v3.1.0]($($Links.FR_ANNOUNCEMENT)) • [" + [char]0x2753 + " FAQ]($($Links.FR_FAQ)) • [" + [char]0x1F680 + " Guide migration]($($Links.FR_MIGRATION))"
    }
    
    # Remplacer la section GitHub Discussions
    $pattern = '### [' + [char]0x1F4E2 + '] (?:Discussions )?GitHub.*?(?=### [' + [char]0x1F4D6 + ']|$)'
    $content = $content -replace $pattern, ($newSection + "`n`n")
    
    # Sauvegarder le fichier
    $content | Set-Content $FilePath -NoNewline
    
    Write-Host "✅ $FilePath mis à jour avec succès" -ForegroundColor Green
    return $true
}

# Mettre à jour les deux README
$success = $true

Write-Host "🔄 Mise à jour des fichiers README..." -ForegroundColor Yellow
Write-Host ""

$success = (Update-ReadmeFile -FilePath "README.md" -Links $DISCUSSIONS -Language "FR") -and $success
$success = (Update-ReadmeFile -FilePath "README_EN.md" -Links $DISCUSSIONS -Language "EN") -and $success

Write-Host ""

if ($success) {
    Write-Host "🎉 SUCCÈS ! Tous les README ont été mis à jour" -ForegroundColor Green
    Write-Host ""
    Write-Host "📋 Prochaines étapes :" -ForegroundColor Yellow
    Write-Host "1. ✅ Vérifiez les modifications : git diff" -ForegroundColor White
    Write-Host "2. ✅ Commitez : git add README.md README_EN.md" -ForegroundColor White
    Write-Host "3. ✅ Poussez : git commit -m 'docs: Update README with GitHub Discussions links'" -ForegroundColor White
    Write-Host "4. ✅ Publiez : git push origin master" -ForegroundColor White
    
    Write-Host ""
    $viewDiff = Read-Host "Voulez-vous voir les modifications maintenant ? (O/N)"
    if ($viewDiff -eq "O" -or $viewDiff -eq "o") {
        git diff README.md README_EN.md
    }
    
} else {
    Write-Host "❌ Erreur lors de la mise à jour" -ForegroundColor Red
    Write-Host "Vérifiez les messages d'erreur ci-dessus" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Script terminé" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan