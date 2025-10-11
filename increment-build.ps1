# Script PowerShell pour incrémenter le numéro de build
# Exécuté automatiquement par Maven avant la compilation

$ErrorActionPreference = "Stop"

Write-Host "=== Incrémentation du numéro de build ===" -ForegroundColor Cyan

# Fichiers à mettre à jour
$buildNumFile = "build.num"
$i18nFile = "src\editeurpanovisu\i18n\PanoVisu.properties"
$projectFile = "src\project.properties"

# Fonction pour lire le numéro de build
function Get-BuildNumber {
    if (!(Test-Path $buildNumFile)) {
        Write-Host "Fichier build.num non trouvé, création avec build=1990" -ForegroundColor Yellow
        return 1990
    }
    
    $content = Get-Content $buildNumFile
    foreach ($line in $content) {
        if ($line -match "^build\.number=(\d+)") {
            return [int]$matches[1]
        }
    }
    
    return 1990
}

# Fonction pour mettre à jour build.num
function Update-BuildNumFile {
    param([int]$buildNumber)
    
    $timestamp = Get-Date -Format "ddd MMM dd HH:mm:ss 'CEST' yyyy"
    $content = @"
#Build Number - Auto-incremented by Maven
#$timestamp
build.number=$buildNumber
"@
    
    Set-Content -Path $buildNumFile -Value $content -Encoding UTF8
    Write-Host "Fichier $buildNumFile mis a jour" -ForegroundColor Green
}

# Fonction pour formater le numéro avec espace insécable
function Format-BuildNumber {
    param([int]$buildNumber)
    
    $buildStr = $buildNumber.ToString()
    if ($buildStr.Length -le 3) {
        return $buildStr
    }
    
    # Séparer les milliers avec \u00a0 (espace insécable)
    $formatted = ""
    for ($i = 0; $i -lt $buildStr.Length; $i++) {
        if ($i -gt 0 -and (($buildStr.Length - $i) % 3 -eq 0)) {
            $formatted += "\u00a0"
        }
        $formatted += $buildStr[$i]
    }
    
    return $formatted
}

# Fonction pour mettre à jour i18n/PanoVisu.properties
function Update-I18nFile {
    param([int]$buildNumber)
    
    if (!(Test-Path $i18nFile)) {
        Write-Host "Fichier $i18nFile non trouvé" -ForegroundColor Yellow
        return
    }
    
    $lines = Get-Content $i18nFile -Encoding UTF8
    $found = $false
    $formattedBuild = Format-BuildNumber $buildNumber
    
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match "^build\.numero=") {
            $lines[$i] = "build.numero=$formattedBuild"
            $found = $true
            break
        }
    }
    
    if (!$found) {
        # Ajouter après la première ligne
        $lines = @($lines[0], "build.numero=$formattedBuild") + $lines[1..($lines.Count-1)]
    }
    
    Set-Content -Path $i18nFile -Value $lines -Encoding UTF8
    Write-Host "Fichier $i18nFile mis a jour" -ForegroundColor Green
}

# Fonction pour mettre à jour project.properties
function Update-ProjectFile {
    param([int]$buildNumber)
    
    if (!(Test-Path $projectFile)) {
        Write-Host "Fichier $projectFile non trouvé" -ForegroundColor Yellow
        return
    }
    
    $lines = Get-Content $projectFile -Encoding UTF8
    $found = $false
    
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match "^Application\.buildnumber=") {
            $lines[$i] = "Application.buildnumber=$buildNumber"
            $found = $true
            break
        }
    }
    
    if (!$found) {
        # Ajouter après project.version
        for ($i = 0; $i -lt $lines.Count; $i++) {
            if ($lines[$i] -match "^project\.version=") {
                $lines = $lines[0..$i] + "Application.buildnumber=$buildNumber" + $lines[($i+1)..($lines.Count-1)]
                break
            }
        }
    }
    
    Set-Content -Path $projectFile -Value $lines -Encoding UTF8
    Write-Host "Fichier $projectFile mis a jour" -ForegroundColor Green
}

# Script principal
# Lire le numéro actuel
$currentBuild = Get-BuildNumber
Write-Host "Build actuel : $currentBuild" -ForegroundColor White

# Incrémenter
$newBuild = $currentBuild + 1
Write-Host "Nouveau build : $newBuild" -ForegroundColor Yellow

# Mettre à jour les fichiers
Update-BuildNumFile $newBuild
Update-I18nFile $newBuild
Update-ProjectFile $newBuild

Write-Host ""
Write-Host "Build mis a jour avec succes !" -ForegroundColor Green
exit 0
