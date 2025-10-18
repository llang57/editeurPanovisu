# Script pour créer la Release v3.1.0 manuellement
# À utiliser si l'action GitHub ne fonctionne pas

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Création manuelle de la Release v3.1.0" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que l'installateur existe
$installerPath = "target\installer\EditeurPanovisu-Setup-3.1.0.exe"
if (-not (Test-Path $installerPath)) {
    Write-Host "❌ ERREUR: Installateur non trouvé: $installerPath" -ForegroundColor Red
    Write-Host "   Exécutez d'abord: .\build-installer.ps1" -ForegroundColor Yellow
    exit 1
}

# Informations sur l'installateur
$fileInfo = Get-Item $installerPath
$fileSizeMB = [math]::Round($fileInfo.Length / 1MB, 2)
$sha256 = (Get-FileHash $installerPath -Algorithm SHA256).Hash

Write-Host "✅ Installateur trouvé:" -ForegroundColor Green
Write-Host "   Fichier: $($fileInfo.Name)" -ForegroundColor White
Write-Host "   Taille: $fileSizeMB MB" -ForegroundColor Cyan
Write-Host "   SHA256: $sha256" -ForegroundColor Gray
Write-Host ""

# Créer le fichier checksums
$checksumsFile = "target\installer\checksums.txt"
@"
# SHA256 Checksums - EditeurPanovisu v3.1.0

$sha256  $($fileInfo.Name)
"@ | Out-File -FilePath $checksumsFile -Encoding UTF8

Write-Host "✅ Fichier checksums créé: $checksumsFile" -ForegroundColor Green
Write-Host ""

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "INSTRUCTIONS POUR CRÉER LA RELEASE" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "1️⃣  Allez sur GitHub:" -ForegroundColor Yellow
Write-Host "   https://github.com/llang57/editeurPanovisu/releases/new" -ForegroundColor White
Write-Host ""

Write-Host "2️⃣  Remplissez le formulaire:" -ForegroundColor Yellow
Write-Host "   • Tag: v3.1.0" -ForegroundColor White
Write-Host "   • Titre: 🔧 v3.1.0 - Correctif Critique Case-Sensitivity" -ForegroundColor White
Write-Host ""

Write-Host "3️⃣  Description (copiez le contenu de RELEASE_NOTES_3.1.0.md)" -ForegroundColor Yellow
Write-Host ""

Write-Host "4️⃣  Uploadez les fichiers:" -ForegroundColor Yellow
Write-Host "   • $installerPath" -ForegroundColor White
Write-Host "   • $checksumsFile" -ForegroundColor White
Write-Host ""

Write-Host "5️⃣  Options:" -ForegroundColor Yellow
Write-Host "   ✅ Set as the latest release" -ForegroundColor Green
Write-Host "   ❌ This is a pre-release (décocher)" -ForegroundColor Red
Write-Host ""

Write-Host "6️⃣  Cliquez sur 'Publish release'" -ForegroundColor Yellow
Write-Host ""

Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "ALTERNATIVE: ACTION GITHUB" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Vous pouvez aussi lancer l'action GitHub pour créer automatiquement" -ForegroundColor White
Write-Host "les installateurs Windows, macOS et Linux:" -ForegroundColor White
Write-Host ""
Write-Host "   https://github.com/llang57/editeurPanovisu/actions/workflows/build-release.yml" -ForegroundColor Cyan
Write-Host ""
Write-Host "   → Cliquez sur 'Run workflow'" -ForegroundColor White
Write-Host "   → Version: 3.1.0" -ForegroundColor White
Write-Host "   → Cliquez sur 'Run workflow' (vert)" -ForegroundColor White
Write-Host ""

# Ouvrir le navigateur
$response = Read-Host "Voulez-vous ouvrir GitHub maintenant? (O/N)"
if ($response -eq "O" -or $response -eq "o") {
    Start-Process "https://github.com/llang57/editeurPanovisu/releases/new?tag=v3.1.0"
    Start-Process "https://github.com/llang57/editeurPanovisu/actions/workflows/build-release.yml"
}

Write-Host ""
Write-Host "[OK] Preparation terminee!" -ForegroundColor Green
