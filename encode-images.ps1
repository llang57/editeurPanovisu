# Script pour encoder les images Leaflet en base64

$images = @{
    'marker-icon.png' = 'MARKER_ICON'
    'marker-icon-2x.png' = 'MARKER_ICON_2X'
    'marker-shadow.png' = 'MARKER_SHADOW'
    'layers.png' = 'LAYERS'
    'layers-2x.png' = 'LAYERS_2X'
}

foreach ($img in $images.Keys) {
    $path = "src\editeurpanovisu\libs\leaflet\images\$img"
    if (Test-Path $path) {
        $base64 = [Convert]::ToBase64String([IO.File]::ReadAllBytes($path))
        $varName = $images[$img]
        Write-Host "`n=== $img ==="
        Write-Host "String $varName = `"data:image/png;base64,$base64`";"
    } else {
        Write-Host "ERREUR: $path introuvable"
    }
}
