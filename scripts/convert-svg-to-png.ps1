# Script pour convertir les SVG en PNG
# Nécessite Inkscape installé

$svgFiles = @(
    "images\boussole.svg",
    "images\oeil.svg",
    "images\photo.svg"
)

$size = 20

foreach ($svgFile in $svgFiles) {
    $fullPath = Join-Path $PSScriptRoot "..\$svgFile"
    $pngFile = $fullPath -replace '\.svg$', '.png'
    
    Write-Host "Conversion de $svgFile en PNG..."
    
    # Avec Inkscape (si installé)
    if (Get-Command inkscape -ErrorAction SilentlyContinue) {
        inkscape --export-type=png --export-width=$size --export-height=$size --export-filename="$pngFile" "$fullPath"
    }
    # Sinon, afficher un message
    else {
        Write-Host "Inkscape n'est pas installé. Veuillez convertir manuellement ou utiliser un outil en ligne."
        Write-Host "Fichier source: $fullPath"
        Write-Host "Fichier destination: $pngFile"
    }
}

Write-Host "Conversion terminée!"
