#!/usr/bin/env pwsh
# Script de génération de la documentation Doxygen
# Usage : .\generate-doc.ps1 [-Open] [-Clean]

param(
    [switch]$Open,      # Ouvrir automatiquement dans le navigateur
    [switch]$Clean,     # Nettoyer avant de générer
    [switch]$Help       # Afficher l'aide
)

# Afficher l'aide
if ($Help) {
    Write-Host @"
Usage: .\generate-doc.ps1 [-Open] [-Clean] [-Help]

Options:
  -Open     Ouvrir la documentation dans le navigateur après génération
  -Clean    Nettoyer les fichiers générés avant de régénérer
  -Help     Afficher ce message d'aide

Exemples:
  .\generate-doc.ps1                    # Générer la documentation
  .\generate-doc.ps1 -Open              # Générer et ouvrir
  .\generate-doc.ps1 -Clean -Open       # Nettoyer, générer et ouvrir
"@
    exit 0
}

# Couleurs pour les messages
function Write-Info($message) {
    Write-Host $message -ForegroundColor Cyan
}

function Write-Success($message) {
    Write-Host $message -ForegroundColor Green
}

function Write-Error($message) {
    Write-Host $message -ForegroundColor Red
}

function Write-Warning($message) {
    Write-Host $message -ForegroundColor Yellow
}

# En-tête
Write-Host ""
Write-Host "╔═══════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   Génération Documentation Doxygen - EditeurPV   ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Vérifier que Doxygen est installé
Write-Info "Vérification de Doxygen..."
if (!(Get-Command doxygen -ErrorAction SilentlyContinue)) {
    Write-Error "✗ Erreur : Doxygen n'est pas installé !"
    Write-Warning ""
    Write-Warning "Installation recommandée :"
    Write-Warning "  - Avec Chocolatey : choco install doxygen.install graphviz"
    Write-Warning "  - Avec Scoop      : scoop install doxygen graphviz"
    Write-Warning "  - Téléchargement  : https://www.doxygen.nl/download.html"
    Write-Warning ""
    exit 1
}

# Obtenir la version de Doxygen
$doxygenVersion = (doxygen --version 2>&1) -replace '\r?\n', ''
Write-Success "✓ Doxygen trouvé (version $doxygenVersion)"

# Vérifier Graphviz (optionnel)
if (Get-Command dot -ErrorAction SilentlyContinue) {
    $graphvizVersion = (dot -V 2>&1) -replace '.*version (\S+).*', '$1'
    Write-Success "✓ Graphviz trouvé (version $graphvizVersion) - Diagrammes disponibles"
} else {
    Write-Warning "⚠ Graphviz non trouvé - Les diagrammes ne seront pas générés"
    Write-Warning "  Installation : choco install graphviz"
}

# Naviguer vers le répertoire doxygen
$doxygenDir = Join-Path $PSScriptRoot "doc\doxygen"
if (!(Test-Path $doxygenDir)) {
    Write-Error "✗ Répertoire doc\doxygen introuvable !"
    exit 1
}

Push-Location $doxygenDir

try {
    # Nettoyage si demandé
    if ($Clean) {
        Write-Info ""
        Write-Info "Nettoyage des fichiers générés..."
        
        $dirsToClean = @("html", "latex", "xml", "man", "rtf")
        $filesDeleted = 0
        
        foreach ($dir in $dirsToClean) {
            if (Test-Path $dir) {
                Remove-Item $dir -Recurse -Force
                $filesDeleted++
                Write-Success "  ✓ Supprimé : $dir\"
            }
        }
        
        # Supprimer les logs
        Get-ChildItem -Filter "*.log" | ForEach-Object {
            Remove-Item $_.FullName -Force
            $filesDeleted++
            Write-Success "  ✓ Supprimé : $($_.Name)"
        }
        
        if ($filesDeleted -eq 0) {
            Write-Info "  → Rien à nettoyer"
        } else {
            Write-Success "  → $filesDeleted élément(s) supprimé(s)"
        }
    }

    # Génération de la documentation
    Write-Info ""
    Write-Info "Génération de la documentation..."
    Write-Host ""
    
    # Exécuter Doxygen
    $startTime = Get-Date
    doxygen Doxyfile
    $endTime = Get-Date
    $duration = ($endTime - $startTime).TotalSeconds
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Success "✓ Documentation générée avec succès ! (en $([math]::Round($duration, 2))s)"
        
        # Afficher les statistiques
        $htmlDir = Join-Path $PWD "html"
        if (Test-Path $htmlDir) {
            $htmlFiles = (Get-ChildItem $htmlDir -Recurse -File).Count
            $htmlSize = (Get-ChildItem $htmlDir -Recurse -File | Measure-Object -Property Length -Sum).Sum
            $htmlSizeMB = [math]::Round($htmlSize / 1MB, 2)
            
            Write-Host ""
            Write-Info "Statistiques :"
            Write-Host "  - Fichiers HTML : $htmlFiles" -ForegroundColor White
            Write-Host "  - Taille totale : $htmlSizeMB MB" -ForegroundColor White
        }
        
        # Vérifier les warnings
        $warningFile = "doxygen_warnings.log"
        if (Test-Path $warningFile) {
            $warnings = Get-Content $warningFile
            if ($warnings.Count -gt 0) {
                Write-Warning ""
                Write-Warning "⚠ $($warnings.Count) avertissement(s) détecté(s)"
                Write-Warning "  Consultez : $warningFile"
            }
        }
        
        # Ouvrir dans le navigateur si demandé
        if ($Open) {
            $indexPath = Join-Path $htmlDir "index.html"
            if (Test-Path $indexPath) {
                Write-Info ""
                Write-Info "Ouverture dans le navigateur..."
                Start-Process $indexPath
                Write-Success "✓ Documentation ouverte"
            }
        } else {
            Write-Info ""
            Write-Info "Pour consulter la documentation :"
            Write-Host "  Start-Process doc\doxygen\html\index.html" -ForegroundColor Yellow
        }
        
        Write-Host ""
        exit 0
        
    } else {
        Write-Host ""
        Write-Error "✗ Erreur lors de la génération de la documentation !"
        Write-Warning "Consultez les logs pour plus de détails"
        Write-Host ""
        exit 1
    }
    
} finally {
    Pop-Location
}
