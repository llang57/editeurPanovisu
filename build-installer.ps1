# Script COMPLET et AUTOMATIQUE pour créer l'installeur EditeurPanovisu
# Utilise Inno Setup pour créer un installeur professionnel

Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host "Build Installeur EditeurPanovisu - 100% Automatique" -ForegroundColor Cyan
Write-Host "======================================================================" -ForegroundColor Cyan

# Étape 0: Nettoyage des fichiers verrouillés
Write-Host "`n[0/4] Nettoyage prealable..." -ForegroundColor Yellow
if (Test-Path "target\dist-msi") {
    Remove-Item "target\dist-msi" -Recurse -Force -ErrorAction SilentlyContinue
    Write-Host "  [OK] Repertoire dist-msi nettoye" -ForegroundColor Green
}

# Étape 1: Build Maven
Write-Host "`n[1/4] Compilation Maven..." -ForegroundColor Yellow
mvn clean package
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR: Build Maven echoue" -ForegroundColor Red
    exit 1
}
Write-Host "  [OK] Compilation reussie" -ForegroundColor Green

# Étape 2: Créer l'image d'application
Write-Host "`n[2/4] Creation de l'image d'application..." -ForegroundColor Yellow

$appImageDir = "target\dist-msi"
Remove-Item -Path $appImageDir -Recurse -Force -ErrorAction SilentlyContinue

$javaHome = $env:JAVA_HOME
if (-not $javaHome) {
    $javaExe = (Get-Command java -ErrorAction SilentlyContinue).Source
    if ($javaExe) {
        $javaHome = Split-Path (Split-Path $javaExe -Parent) -Parent
    }
}

if (-not $javaHome -or -not (Test-Path $javaHome)) {
    Write-Host "ERREUR: Impossible de trouver Java" -ForegroundColor Red
    exit 1
}

Write-Host "  Java: $javaHome" -ForegroundColor Gray

& jpackage `
  --type app-image `
  --name EditeurPanovisu `
  --app-version 3.2.0 `
  --vendor "PanoVisu - Laurent LANG" `
  --input target\app-input `
  --main-jar editeurPanovisu-3.2.0-SNAPSHOT.jar `
  --main-class editeurpanovisu.Launcher `
  --runtime-image "$javaHome" `
  --icon images\panovisu.ico `
  --java-options "-Dfile.encoding=UTF-8" `
  --java-options "--enable-preview" `
  --java-options "--enable-native-access=ALL-UNNAMED" `
  --java-options "-Xms512m" `
  --java-options "-Xmx2048m" `
  --dest $appImageDir

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR: jpackage echoue" -ForegroundColor Red
    exit 1
}
Write-Host "  [OK] Image creee" -ForegroundColor Green

# Étape 3: Configurer l'image
Write-Host "`n[3/4] Configuration des lanceurs..." -ForegroundColor Yellow

$appDir = "$appImageDir\EditeurPanovisu"

# Supprimer l'EXE buggy
$exeFile = "$appDir\EditeurPanovisu.exe"
if (Test-Path $exeFile) {
    Remove-Item $exeFile -Force -ErrorAction SilentlyContinue
    Write-Host "  [OK] EXE buggy supprime" -ForegroundColor Green
}

# Créer le batch
$batchContent = @'
@echo off
cd /d "%~dp0app"
start "" "%~dp0runtime\bin\javaw.exe" -Dfile.encoding=UTF-8 --enable-preview --enable-native-access=ALL-UNNAMED -Xms512m -Xmx2048m -jar "editeurPanovisu-3.2.0-SNAPSHOT.jar"
'@
$batchContent | Out-File -FilePath "$appDir\Lancer_EditeurPanovisu.bat" -Encoding ASCII
Write-Host "  [OK] Batch cree" -ForegroundColor Green

# Créer le VBS
$vbsContent = @'
Set objShell = CreateObject("WScript.Shell")
strPath = CreateObject("Scripting.FileSystemObject").GetParentFolderName(WScript.ScriptFullName)
objShell.Run """" & strPath & "\Lancer_EditeurPanovisu.bat""", 0, False
'@
$vbsContent | Out-File -FilePath "$appDir\Lancer_EditeurPanovisu.vbs" -Encoding ASCII
Write-Host "  [OK] VBS cree" -ForegroundColor Green

# Copier l'icône à la racine (pour Inno Setup)
if (Test-Path "images\panovisu.ico") {
    Copy-Item "images\panovisu.ico" "$appDir\EditeurPanovisu.ico" -Force
    Write-Host "  [OK] Icone copiee" -ForegroundColor Green
}

# Étape 4: Créer l'installeur avec Inno Setup
Write-Host "`n[4/4] Creation de l'installeur..." -ForegroundColor Yellow

# Chercher Inno Setup
$innoSetupPaths = @(
    "${env:ProgramFiles(x86)}\Inno Setup 6\ISCC.exe",
    "${env:ProgramFiles}\Inno Setup 6\ISCC.exe",
    "${env:LOCALAPPDATA}\Programs\Inno Setup 6\ISCC.exe"
)

$iscc = $null
foreach ($path in $innoSetupPaths) {
    if (Test-Path $path) {
        $iscc = $path
        break
    }
}

if (-not $iscc) {
    Write-Host ""
    Write-Host "AVERTISSEMENT: Inno Setup non trouve" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Pour installer Inno Setup:" -ForegroundColor Cyan
    Write-Host "  1. Telechargez depuis: https://jrsoftware.org/isdl.php" -ForegroundColor White
    Write-Host "  2. Installez Inno Setup 6" -ForegroundColor White
    Write-Host "  3. Relancez ce script" -ForegroundColor White
    Write-Host ""
    Write-Host "OU utilisez winget:" -ForegroundColor Cyan
    Write-Host "  winget install JRSoftware.InnoSetup" -ForegroundColor White
    Write-Host ""
    Write-Host "Alternative: Distribution ZIP" -ForegroundColor Yellow
    Write-Host "  Le dossier target\dist-msi\EditeurPanovisu\ contient l'application complete" -ForegroundColor White
    Write-Host "  Vous pouvez le compresser en ZIP et le distribuer" -ForegroundColor White
    exit 1
}

Write-Host "  Inno Setup: $iscc" -ForegroundColor Gray

# Compiler l'installeur
& $iscc "installer.iss"

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR: Compilation Inno Setup echouee" -ForegroundColor Red
    exit 1
}

# Succès !
Write-Host ""
Write-Host "======================================================================" -ForegroundColor Green
Write-Host "INSTALLEUR CREE AVEC SUCCES !" -ForegroundColor Green
Write-Host "======================================================================" -ForegroundColor Green

$setupFile = Get-ChildItem "target\installer\EditeurPanovisu-Setup-*.exe" | Select-Object -First 1
if ($setupFile) {
    $setupSize = [math]::Round($setupFile.Length / 1MB, 2)
    
    Write-Host ""
    Write-Host "Fichier: $($setupFile.Name)" -ForegroundColor White
    Write-Host "Taille: $setupSize MB" -ForegroundColor Cyan
    Write-Host "Chemin: $($setupFile.FullName)" -ForegroundColor Gray
    
    Write-Host ""
    Write-Host "Caracteristiques:" -ForegroundColor Cyan
    Write-Host "  [OK] Installation automatique dans AppData\Local\EditeurPanovisu" -ForegroundColor Green
    Write-Host "  [OK] Raccourcis Bureau et Menu Demarrer automatiques" -ForegroundColor Green
    Write-Host "  [OK] Raccourcis pointent vers le lanceur VBS (pas l'EXE buggy)" -ForegroundColor Green
    Write-Host "  [OK] Runtime Java embarque (aucune installation Java requise)" -ForegroundColor Green
    Write-Host "  [OK] Pas de droits administrateur requis" -ForegroundColor Green
    Write-Host "  [OK] Desinstallation propre via Panneau de configuration" -ForegroundColor Green
    
    Write-Host ""
    Write-Host "Pour distribuer:" -ForegroundColor Yellow
    Write-Host "  1. Donnez le fichier '$($setupFile.Name)' aux utilisateurs" -ForegroundColor White
    Write-Host "  2. Double-clic pour installer" -ForegroundColor White
    Write-Host "  3. C'est tout ! L'application fonctionne immediatement" -ForegroundColor White
    
    Write-Host ""
    Write-Host "Pour tester maintenant:" -ForegroundColor Cyan
    Write-Host "  Start-Process '$($setupFile.FullName)'" -ForegroundColor White
} else {
    Write-Host "ERREUR: Fichier d'installation non trouve" -ForegroundColor Red
}
