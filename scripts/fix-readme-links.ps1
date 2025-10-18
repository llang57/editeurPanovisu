# Script PowerShell pour mettre à jour les README avec les bonnes URLs
Write-Host "Mise a jour des liens GitHub Discussions..." -ForegroundColor Green

# Mettre à jour README.md
$content = Get-Content "README.md" -Raw

$newFRSection = @"
### 📢 Discussions GitHub (RECOMMANDÉ)
- 🇫🇷 [📢 Annonce v3.1.0](https://github.com/llang57/editeurPanovisu/discussions/6) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/8) • [🚀 Guide migration](https://github.com/llang57/editeurPanovisu/discussions/10)
- 🇬🇧 [📢 v3.1.0 Announcement](https://github.com/llang57/editeurPanovisu/discussions/7) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/9) • [🚀 Migration Guide](https://github.com/llang57/editeurPanovisu/discussions/11)

### 📖 Documentation complète
"@

# Remplacer dans README.md
$content = $content -replace '### 📢 Discussions GitHub \(RECOMMANDÉ\).*?### 📖 Documentation complète', $newFRSection

Set-Content "README.md" $content -NoNewline

# Mettre à jour README_EN.md
$contentEN = Get-Content "README_EN.md" -Raw

$newENSection = @"
### 📢 GitHub Discussions (RECOMMENDED)
- 🇬🇧 [📢 v3.1.0 Announcement](https://github.com/llang57/editeurPanovisu/discussions/7) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/9) • [🚀 Migration Guide](https://github.com/llang57/editeurPanovisu/discussions/11)
- 🇫🇷 [📢 Annonce v3.1.0](https://github.com/llang57/editeurPanovisu/discussions/6) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/8) • [🚀 Guide migration](https://github.com/llang57/editeurPanovisu/discussions/10)

### 📖 Complete Documentation
"@

# Remplacer dans README_EN.md
$contentEN = $contentEN -replace '### 📢 GitHub Discussions \(RECOMMENDED\).*?### 📖 Complete Documentation', $newENSection

Set-Content "README_EN.md" $contentEN -NoNewline

Write-Host "✅ README.md mis a jour" -ForegroundColor Green
Write-Host "✅ README_EN.md mis a jour" -ForegroundColor Green
Write-Host ""
Write-Host "URLs configurees:" -ForegroundColor Yellow
Write-Host "  FR Annonce: discussions/6" -ForegroundColor White
Write-Host "  EN Annonce: discussions/7" -ForegroundColor White
Write-Host "  FR FAQ: discussions/8" -ForegroundColor White  
Write-Host "  EN FAQ: discussions/9" -ForegroundColor White
Write-Host "  FR Migration: discussions/10" -ForegroundColor White
Write-Host "  EN Migration: discussions/11" -ForegroundColor White