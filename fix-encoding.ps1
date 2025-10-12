# Script pour corriger les problèmes d'encodage dans EditeurPanovisu.java
# Remplace les caractères mal encodés par leurs équivalents UTF-8

$fichier = "src\editeurpanovisu\EditeurPanovisu.java"

# Lire le contenu avec l'encodage UTF-8
$contenu = Get-Content $fichier -Raw -Encoding UTF8

# Remplacements des caractères mal encodés
$corrections = @{
    'Ã©' = 'é'    # e accent aigu
    'Ã¨' = 'è'    # e accent grave
    'Ãª' = 'ê'    # e accent circonflexe
    'Ã ' = 'à'    # a accent grave
    'Ã§' = 'ç'    # c cédille
    'Ã´' = 'ô'    # o accent circonflexe
    'Ã»' = 'û'    # u accent circonflexe
    'Ã¢' = 'â'    # a accent circonflexe
    'Ã®' = 'î'    # i accent circonflexe
    'Ã¯' = 'ï'    # i tréma
    'Ã¼' = 'ü'    # u tréma
    'Ã«' = 'ë'    # e tréma
    'â€™' = "'"   # apostrophe typographique
    'â€"' = '—'   # tiret cadratin
}

Write-Host "🔍 Recherche des problèmes d'encodage..." -ForegroundColor Cyan

$nbCorrections = 0
foreach ($mauvais in $corrections.Keys) {
    $bon = $corrections[$mauvais]
    $occurrences = ([regex]::Matches($contenu, [regex]::Escape($mauvais))).Count
    if ($occurrences -gt 0) {
        Write-Host "   Correction de '$mauvais' → '$bon' ($occurrences occurrence(s))" -ForegroundColor Yellow
        $contenu = $contenu.Replace($mauvais, $bon)
        $nbCorrections += $occurrences
    }
}

if ($nbCorrections -gt 0) {
    # Sauvegarder avec l'encodage UTF-8 (sans BOM)
    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText((Resolve-Path $fichier), $contenu, $utf8NoBom)
    
    Write-Host ""
    Write-Host "✅ $nbCorrections corrections effectuées dans $fichier" -ForegroundColor Green
} else {
    Write-Host "✅ Aucun problème d'encodage détecté" -ForegroundColor Green
}
