# Script pour corriger les fichiers SVG et les rendre compatibles avec Batik/Xerces
# Le probleme : encodage de fin de ligne

$svgDir = ".\images\svg"

Write-Host "=== Correction des fichiers SVG ===" -ForegroundColor Cyan
Write-Host "Repertoire: $svgDir`n"

# Obtenir tous les fichiers SVG
$svgFiles = Get-ChildItem -Path $svgDir -Filter "*.svg"

foreach ($file in $svgFiles) {
    Write-Host "Traitement de: $($file.Name)" -ForegroundColor Yellow
    
    # Lire le contenu avec l'encodage UTF-8
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    
    # Supprimer les BOM si presents
    $content = $content.TrimStart([char]0xFEFF)
    
    # Normaliser les fins de ligne en LF (Unix style)
    $content = $content -replace "`r`n", "`n"
    
    # S'assurer qu'il n'y a pas de ligne vide a la fin
    $content = $content.TrimEnd()
    
    # Sauvegarder avec UTF-8 sans BOM et LF comme fin de ligne
    [System.IO.File]::WriteAllText($file.FullName, $content + "`n", (New-Object System.Text.UTF8Encoding $false))
    
    Write-Host "  OK Corrige" -ForegroundColor Green
}

Write-Host "`n=== Correction terminee ===" -ForegroundColor Green
Write-Host "Fichiers traites: $($svgFiles.Count)"
