# Script de préparation du package Linux depuis Windows
# Build 3587

Write-Host "=== Préparation du package EditeurPanovisu pour Linux ===" -ForegroundColor Cyan
Write-Host ""

# Vérifier que target/app-input existe
if (-not (Test-Path "target\app-input")) {
    Write-Host "❌ ERREUR: Le répertoire target\app-input n'existe pas" -ForegroundColor Red
    Write-Host ""
    Write-Host "📝 Exécutez d'abord: mvn clean package" -ForegroundColor Yellow
    exit 1
}

# Créer le répertoire de destination
$destDir = "editeur-panovisu-linux"
if (Test-Path $destDir) {
    Remove-Item -Recurse -Force $destDir
}
New-Item -ItemType Directory -Path $destDir | Out-Null

Write-Host "📦 Copie des fichiers depuis target\app-input\..." -ForegroundColor Yellow

# Copier tout le contenu de app-input
Copy-Item -Path "target\app-input\*" -Destination $destDir -Recurse

# Copier le script de lancement
Copy-Item -Path "lancer-panovisu-linux.sh" -Destination $destDir

Write-Host ""
Write-Host "✅ Package Linux préparé dans: $destDir\" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Pour transférer vers Linux:" -ForegroundColor Cyan
Write-Host "   1. Compresser le dossier $destDir\ en .zip ou .tar.gz"
Write-Host "   2. Transférer vers Linux"
Write-Host "   3. Décompresser sur Linux"
Write-Host "   4. Rendre le script exécutable: chmod +x lancer-panovisu-linux.sh"
Write-Host "   5. Lancer: ./lancer-panovisu-linux.sh"
Write-Host ""

# Option: créer automatiquement l'archive ZIP
$createZip = Read-Host "Créer l'archive .zip maintenant? (o/N)"
if ($createZip -match "^[OoYy]") {
    $zipFile = "editeur-panovisu-linux-build3587.zip"
    if (Test-Path $zipFile) {
        Remove-Item $zipFile
    }
    
    Write-Host "📦 Création de l'archive..." -ForegroundColor Yellow
    Compress-Archive -Path $destDir -DestinationPath $zipFile
    
    $size = (Get-Item $zipFile).Length / 1MB
    Write-Host "✅ Archive créée: $zipFile" -ForegroundColor Green
    Write-Host "   Taille: $([math]::Round($size, 2)) MB" -ForegroundColor Gray
}

Write-Host ""
Write-Host "✅ Préparation terminée!" -ForegroundColor Green
