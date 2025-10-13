# Script pour copier Wingdings dans les ressources du projet
# Requis pour que JavaFX WebView puisse utiliser la police Wingdings

Write-Host "=== Copie de Wingdings pour JavaFX WebView ===" -ForegroundColor Cyan
Write-Host ""

# Chemins
$sourcePath = "C:\Windows\Fonts\wingding.ttf"
$destPath = "src\editeurpanovisu\fonts\wingdings.ttf"
$destDir = "src\editeurpanovisu\fonts"

# Vérifier que le fichier source existe
if (-not (Test-Path $sourcePath)) {
    Write-Host "❌ ERREUR: Wingdings non trouvé dans Windows!" -ForegroundColor Red
    Write-Host "   Chemin attendu: $sourcePath" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Recherche de fichiers Wingdings alternatifs..." -ForegroundColor Yellow
    $alternatives = Get-ChildItem "C:\Windows\Fonts" -Filter "*wingding*"
    if ($alternatives) {
        Write-Host "Fichiers trouvés:" -ForegroundColor Green
        $alternatives | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor Cyan }
    } else {
        Write-Host "Aucun fichier Wingdings trouvé!" -ForegroundColor Red
    }
    exit 1
}

# Créer le dossier de destination si nécessaire
if (-not (Test-Path $destDir)) {
    New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    Write-Host "✅ Dossier créé: $destDir" -ForegroundColor Green
}

# Copier le fichier
try {
    Copy-Item $sourcePath $destPath -Force
    Write-Host "✅ Wingdings copié avec succès!" -ForegroundColor Green
    Write-Host ""
    
    # Afficher les infos du fichier
    $fileInfo = Get-Item $destPath
    Write-Host "📊 Informations du fichier:" -ForegroundColor Cyan
    Write-Host "   Nom: $($fileInfo.Name)" -ForegroundColor White
    Write-Host "   Taille: $([math]::Round($fileInfo.Length / 1KB, 2)) KB" -ForegroundColor White
    Write-Host "   Chemin: $($fileInfo.FullName)" -ForegroundColor White
    Write-Host ""
    
    Write-Host "🎯 Prochaines étapes:" -ForegroundColor Yellow
    Write-Host "   1. Compiler le projet: mvn clean compile" -ForegroundColor White
    Write-Host "   2. Lancer l'application: mvn javafx:run" -ForegroundColor White
    Write-Host "   3. Tester avec: Menu Aide > Documentation > test-wingdings.md" -ForegroundColor White
    Write-Host ""
    
} catch {
    Write-Host "❌ ERREUR lors de la copie: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Vérifier aussi Webdings (optionnel)
$webdingsSource = "C:\Windows\Fonts\webdings.ttf"
if (Test-Path $webdingsSource) {
    Write-Host "💡 Webdings egalement disponible (alternative)" -ForegroundColor Cyan
    $webdingsDest = "src\editeurpanovisu\fonts\webdings.ttf"
    Write-Host "   Pour inclure aussi: Copy-Item $webdingsSource $webdingsDest" -ForegroundColor Gray
}

Write-Host ""
Write-Host "✅ Configuration terminée!" -ForegroundColor Green
