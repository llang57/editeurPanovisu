# Script de conversion automatique des emojis en entités HTML/Wingdings
# Utilisation : .\convert-emojis.ps1

Write-Host "=== Conversion des emojis en entités HTML ===" -ForegroundColor Cyan
Write-Host ""

$fichier = "d:\developpement\java\editeurPanovisu\src\editeurpanovisu\MarkdownViewer.java"

if (-not (Test-Path $fichier)) {
    Write-Host "Erreur : Fichier non trouvé : $fichier" -ForegroundColor Red
    exit 1
}

# Backup
$backup = "$fichier.backup-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
Copy-Item $fichier $backup
Write-Host "Backup créé : $backup" -ForegroundColor Green

# Lire le contenu
$content = Get-Content $fichier -Raw -Encoding UTF8

# Table de remplacement : Emoji Unicode → Entité HTML/Wingdings
# Format : @{ emoji = remplacement }
$replacements = @{
    # Emojis qui plantent actuellement
    '📱' = '<span class=''badge badge-mobile''><span class=''wi''>&#112;</span> MOBILE</span>'
    '🏛️' = '<span class=''badge badge-monument''><span class=''wi''>&#236;</span> MONUMENT</span>'
    '🏛' = '<span class=''badge badge-monument''><span class=''wi''>&#236;</span> MONUMENT</span>'
    '🏰' = '<span class=''badge badge-castle''><span class=''wi''>&#236;</span> CHATEAU</span>'
    '🛒' = '<span class=''badge badge-cart''><span class=''wi''>&#254;</span> SHOP</span>'
    '📸' = '<span class=''badge badge-camera''><span class=''wi''>&#112;</span> PHOTO</span>'
    
    # Emojis restants à convertir (exemples)
    '🔗' = '<span class=''badge badge-link''><span class=''wi''>&#182;</span> LIEN</span>'
    '📝' = '<span class=''badge badge-note''>&#9998; NOTE</span>'
    '💻' = '<span class=''badge badge-dev''><span class=''wi''>&#112;</span> DEV</span>'
    '🏗️' = '<span class=''badge badge-build''><span class=''wi''>&#119;</span> BUILD</span>'
    '🏗' = '<span class=''badge badge-build''><span class=''wi''>&#119;</span> BUILD</span>'
    '🗺️' = '<span class=''badge badge-map''><span class=''wi''>&#231;</span> CARTE</span>'
    '🗺' = '<span class=''badge badge-map''><span class=''wi''>&#231;</span> CARTE</span>'
    '🔒' = '<span class=''badge badge-lock''><span class=''wi''>&#128;</span> SECURITE</span>'
    '💡' = '<span class=''badge badge-idea''><span class=''wi''>&#161;</span> IDEE</span>'
    '🔑' = '<span class=''badge badge-key''><span class=''wi''>&#128;</span> CLE</span>'
    '🔄' = '<span class=''badge badge-refresh''>&#8634; SYNC</span>'
    
    # Emojis de présentation
    '🌍' = '<span class=''badge badge-world''><span class=''wi''>&#128;</span> MONDE</span>'
    '🌐' = '<span class=''badge badge-world''><span class=''wi''>&#128;</span> WEB</span>'
    '🤝' = '<span class=''badge badge-handshake''>&#9729; PARTENAIRE</span>'
    '👥' = '<span class=''badge badge-users''>&#9762; COMMUNAUTE</span>'
    '📊' = '<span class=''badge badge-chart''><span class=''wi''>&#151;</span> STATS</span>'
    
    # Ollama/IA
    '🤖' = '<span class=''badge badge-robot''>&#9881; IA</span>'
    '💰' = '<span class=''badge badge-money''>&#36; GRATUIT</span>'
    '⭐' = '<span class=''badge badge-star''>&#9733; STAR</span>'
    '🏷️' = '<span class=''badge badge-tag''><span class=''wi''>&#35;</span> TAG</span>'
    '🏷' = '<span class=''badge badge-tag''><span class=''wi''>&#35;</span> TAG</span>'
    '✏️' = '<span class=''badge badge-pencil''>&#9998; EDITER</span>'
    '✏' = '<span class=''badge badge-pencil''>&#9998; EDITER</span>'
    '💾' = '<span class=''badge badge-save''><span class=''wi''>&#53;</span> SAVE</span>'
    '⏱️' = '<span class=''badge badge-timer''><span class=''wi''>&#183;</span> TIMER</span>'
    '⏱' = '<span class=''badge badge-timer''><span class=''wi''>&#183;</span> TIMER</span>'
    '✨' = '<span class=''badge badge-sparkles''>&#10022; MAGIC</span>'
    '💬' = '<span class=''badge badge-comment''>&#128172; FORUM</span>'
    '🎥' = '<span class=''badge badge-video''>&#9654; VIDEO</span>'
    '🆘' = '<span class=''badge badge-sos''>&#9888; AIDE</span>'
    '🎉' = '<span class=''badge badge-party''>&#9733; SUCCES</span>'
    
    # Interface
    '🎨' = '<span class=''badge badge-art''><span class=''wi''>&#114;</span> DESIGN</span>'
    '📍' = '<span class=''badge badge-pin''>&#9679; POSITION</span>'
    '🌫️' = '<span class=''badge badge-cloud''>&#9729; OPACITE</span>'
    '🌫' = '<span class=''badge badge-cloud''>&#9729; OPACITE</span>'
    '🖌️' = '<span class=''badge badge-brush''><span class=''wi''>&#114;</span> PINCEAU</span>'
    '🖌' = '<span class=''badge badge-brush''><span class=''wi''>&#114;</span> PINCEAU</span>'
    '🔘' = '<span class=''badge badge-radio''>&#9675; OPTION</span>'
    '☑️' = '<span class=''badge badge-checkbox''>&#9745; COCHE</span>'
    '☑' = '<span class=''badge badge-checkbox''>&#9745; COCHE</span>'
    '🏞️' = '<span class=''badge badge-panorama''><span class=''wi''>&#90;</span> PANORAMA</span>'
    '🏞' = '<span class=''badge badge-panorama''><span class=''wi''>&#90;</span> PANORAMA</span>'
    '🖼️' = '<span class=''badge badge-picture''><span class=''wi''>&#90;</span> IMAGE</span>'
    '🖼' = '<span class=''badge badge-picture''><span class=''wi''>&#90;</span> IMAGE</span>'
    '📽️' = '<span class=''badge badge-projector''>&#9654; DIAPO</span>'
    '📽' = '<span class=''badge badge-projector''>&#9654; DIAPO</span>'
    '🧭' = '<span class=''badge badge-compass''><span class=''wi''>&#174;</span> BOUSSOLE</span>'
    '👁️' = '<span class=''badge badge-eye''><span class=''wi''>&#78;</span> OEIL</span>'
    '👁' = '<span class=''badge badge-eye''><span class=''wi''>&#78;</span> OEIL</span>'
    
    # Lieux
    '🏨' = '<span class=''badge badge-hotel''><span class=''wi''>&#182;</span> HOTEL</span>'
    '🏡' = '<span class=''badge badge-house''><span class=''wi''>&#112;</span> MAISON</span>'
    '🏙️' = '<span class=''badge badge-city''><span class=''wi''>&#245;</span> VILLE</span>'
    '🏙' = '<span class=''badge badge-city''><span class=''wi''>&#245;</span> VILLE</span>'
    '🏘️' = '<span class=''badge badge-houses''><span class=''wi''>&#112;</span> QUARTIER</span>'
    '🏘' = '<span class=''badge badge-houses''><span class=''wi''>&#112;</span> QUARTIER</span>'
    
    # Actions
    '🚪' = '<span class=''badge badge-door''><span class=''wi''>&#170;</span> PORTE</span>'
    '📏' = '<span class=''badge badge-ruler''><span class=''wi''>&#240;</span> REGLE</span>'
    '📐' = '<span class=''badge badge-triangle''><span class=''wi''>&#240;</span> TRIANGLE</span>'
    '🎭' = '<span class=''badge badge-theater''><span class=''wi''>&#103;</span> THEATRE</span>'
    '🎬' = '<span class=''badge badge-clapper''>&#9654; ACTION</span>'
    '👴' = '<span class=''badge badge-elder''><span class=''wi''>&#226;</span> ACCESSIBILITE</span>'
    '🖱️' = '<span class=''badge badge-mouse''><span class=''wi''>&#112;</span> SOURIS</span>'
    '🖱' = '<span class=''badge badge-mouse''><span class=''wi''>&#112;</span> SOURIS</span>'
    '🖵' = '<span class=''badge badge-fullscreen''>&#9635; PLEIN ECRAN</span>'
    '📘' = '<span class=''badge badge-book-blue''><span class=''wi''>&#128;</span> LIVRE</span>'
    '🐦' = '<span class=''badge badge-bird''><span class=''wi''>&#187;</span> TWITTER</span>'
    '📄' = '<span class=''badge badge-page''><span class=''wi''>&#52;</span> DOC</span>'
    '🏠' = '<span class=''badge badge-home''><span class=''wi''>&#112;</span> ACCUEIL</span>'
    '🌳' = '<span class=''badge badge-tree''><span class=''wi''>&#116;</span> ARBRE</span>'
    '🆓' = '<span class=''badge badge-free''>&#36; GRATUIT</span>'
    '🏆' = '<span class=''badge badge-trophy''><span class=''wi''>&#175;</span> TROPHEE</span>'
    '🔖' = '<span class=''badge badge-bookmark''><span class=''wi''>&#35;</span> SIGNET</span>'
    '🎛️' = '<span class=''badge badge-controls''><span class=''wi''>&#166;</span> CONTROLES</span>'
    '🎛' = '<span class=''badge badge-controls''><span class=''wi''>&#166;</span> CONTROLES</span>'
}

Write-Host "Nombre de remplacements à effectuer : $($replacements.Count)" -ForegroundColor Yellow
Write-Host ""

$count = 0
foreach ($emoji in $replacements.Keys) {
    if ($content -match [regex]::Escape($emoji)) {
        $content = $content -replace [regex]::Escape($emoji), $replacements[$emoji]
        $count++
        Write-Host "✓ Remplacé : $emoji" -ForegroundColor Green
    }
}

# Sauvegarder
$content | Out-File -FilePath $fichier -Encoding UTF8 -NoNewline

Write-Host ""
Write-Host "=== Conversion terminée ===" -ForegroundColor Cyan
Write-Host "Remplacements effectués : $count" -ForegroundColor Green
Write-Host "Fichier mis à jour : $fichier" -ForegroundColor Green
Write-Host ""
Write-Host "Prochaine étape : mvn clean compile" -ForegroundColor Yellow
