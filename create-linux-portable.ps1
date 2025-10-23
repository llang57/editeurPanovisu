# Script de creation d'une archive portable Linux pour EditeurPanovisu
# Usage: .\create-linux-portable.ps1

$ErrorActionPreference = "Stop"

Write-Host "=== Creation de l'archive portable Linux EditeurPanovisu ===" -ForegroundColor Cyan

# Verifier que le build a ete fait
if (-not (Test-Path "target\app-input\editeurPanovisu-3.3.3-SNAPSHOT.jar")) {
    Write-Host "Erreur: Le JAR n'existe pas. Executez d'abord: mvn clean package -DskipTests" -ForegroundColor Red
    exit 1
}

# Verifier que les fichiers d'installation existent
if (-not (Test-Path "doc\install\INSTALLATION.md") -or `
    -not (Test-Path "doc\install\INSTALLATION.txt") -or `
    -not (Test-Path "doc\install\INSTALLATION.html")) {
    Write-Host "Erreur: Les fichiers d'installation sont manquants dans doc\install\" -ForegroundColor Red
    exit 1
}

# Creer le repertoire de travail
$linuxDir = "target\linux-portable"
if (Test-Path $linuxDir) {
    Remove-Item -Path $linuxDir -Recurse -Force
}
New-Item -Path $linuxDir -ItemType Directory | Out-Null

Write-Host "Copie des fichiers depuis app-input..." -ForegroundColor Yellow

# Copier tout le contenu de app-input
Copy-Item -Path "target\app-input\*" -Destination $linuxDir -Recurse -Force

# Supprimer les fichiers Windows inutiles pour Linux
Write-Host "Suppression des fichiers Windows (.bat, .vbs)..." -ForegroundColor Yellow
Remove-Item -Path "$linuxDir\*.bat" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "$linuxDir\*.vbs" -Force -ErrorAction SilentlyContinue

# Vider configPV mais garder le repertoire
Write-Host "Vidage du repertoire configPV..." -ForegroundColor Yellow
if (Test-Path "$linuxDir\configPV") {
    Remove-Item -Path "$linuxDir\configPV\*" -Recurse -Force
}

# Creer le script de lancement Linux
Write-Host "Creation du script de lancement Linux..." -ForegroundColor Yellow
$launchScript = @"
#!/bin/bash
# Script de lancement EditeurPanovisu pour Linux

echo "=== Lancement EditeurPanovisu ==="
echo "Repertoire de travail: `$(pwd)"
echo ""

# Detecter le repertoire du script
SCRIPT_DIR="`$( cd "`$( dirname "`${BASH_SOURCE[0]}" )" && pwd )"
cd "`$SCRIPT_DIR"

# Configuration JavaFX 3D
export PRISM_VERBOSE=false
export PRISM_FORCEGL=true
export PRISM_FORCEGPU=true
export PRISM_ORDER=es2,sw

# Verifier Java
if ! command -v java &> /dev/null; then
    echo "Erreur: Java n'est pas installe ou n'est pas dans le PATH"
    echo "Consultez INSTALLATION.txt pour les instructions"
    exit 1
fi

# Verifier la version Java
JAVA_VERSION=`$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "`$JAVA_VERSION" -lt 25 ]; then
    echo "Attention: Java 25 ou superieur est recommande (version detectee: `$JAVA_VERSION)"
fi

echo "=== Demarrage de l'application ==="
java -Dfile.encoding=UTF-8 \
     -Xms256m \
     -Xmx4096m \
     -jar editeurPanovisu-3.3.3-SNAPSHOT.jar

exit_code=`$?

if [ `$exit_code -eq 0 ]; then
    echo "OK: EditeurPanovisu termine normalement"
else
    echo "Erreur: EditeurPanovisu termine avec le code d'erreur: `$exit_code"
fi

exit `$exit_code
"@

$launchScript | Out-File -FilePath "$linuxDir\lancer-editeur-panovisu.sh" -Encoding UTF8 -NoNewline

# Copier les fichiers d'installation depuis doc/install/
Write-Host "Copie des fichiers d'installation..." -ForegroundColor Yellow
Copy-Item -Path "doc\install\INSTALLATION.md" -Destination "$linuxDir\INSTALLATION.md" -Force
Copy-Item -Path "doc\install\INSTALLATION.txt" -Destination "$linuxDir\INSTALLATION.txt" -Force
Copy-Item -Path "doc\install\INSTALLATION.html" -Destination "$linuxDir\INSTALLATION.html" -Force

# Creer l'archive ZIP
Write-Host "Creation de l'archive ZIP..." -ForegroundColor Yellow
$version = "3.3.3"
$zipFile = "target\EditeurPanovisu-Linux-Portable-$version.zip"
if (Test-Path $zipFile) {
    Remove-Item $zipFile -Force
}

# Compresser avec PowerShell
Compress-Archive -Path "$linuxDir\*" -DestinationPath $zipFile -CompressionLevel Optimal

Write-Host "Archive creee: $zipFile" -ForegroundColor Green

# Creer aussi une archive TAR.GZ si tar est disponible
if (Get-Command tar -ErrorAction SilentlyContinue) {
    Write-Host "Creation de l'archive TAR.GZ..." -ForegroundColor Yellow
    $tarFile = "target\EditeurPanovisu-Linux-Portable-$version.tar.gz"
    
    Push-Location $linuxDir
    tar -czf "..\..\$tarFile" *
    Pop-Location
    
    Write-Host "Archive creee: $tarFile" -ForegroundColor Green
} else {
    Write-Host "Attention: tar non disponible, seule l'archive ZIP a ete creee" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Archive portable Linux creee avec succes ===" -ForegroundColor Green
Write-Host ""
Write-Host "Contenu:" -ForegroundColor Cyan
Write-Host "  - Application complete avec toutes les ressources"
Write-Host "  - Script de lancement: lancer-editeur-panovisu.sh"
Write-Host "  - Documentation d'installation: INSTALLATION.txt/.md/.html"
Write-Host "  - Repertoire configPV vide (sera cree au premier lancement)"
Write-Host ""
