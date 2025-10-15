# Script pour créer les discussions GitHub pour la v3.1.0
# Utilisation : Copiez-collez les contenus dans l'interface GitHub Discussions

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "AIDE - Création des Discussions GitHub" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "📋 Étapes pour créer les discussions :" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. Allez sur : https://github.com/llang57/editeurPanovisu/discussions/new" -ForegroundColor White
Write-Host ""
Write-Host "2. Créez 3 discussions avec les contenus suivants :" -ForegroundColor White
Write-Host ""

# Discussion 1 - Annonce
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "DISCUSSION 1 : ANNONCE DE LA VERSION" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "Catégorie : 📢 Announcements" -ForegroundColor Yellow
Write-Host "Titre : 🔧 Version 3.1.0 - Correctif Critique de Case-Sensitivity Disponible" -ForegroundColor Yellow
Write-Host ""
Write-Host "Fichier source : doc\DISCUSSION_V3.1.0.md" -ForegroundColor White
Write-Host "Commande pour ouvrir :" -ForegroundColor White
Write-Host "  notepad doc\DISCUSSION_V3.1.0.md" -ForegroundColor Gray
Write-Host ""
Write-Host "📝 Actions :" -ForegroundColor Yellow
Write-Host "  1. Copiez le contenu du fichier DISCUSSION_V3.1.0.md" -ForegroundColor White
Write-Host "  2. Collez-le dans la nouvelle discussion" -ForegroundColor White
Write-Host "  3. Cliquez 'Start discussion'" -ForegroundColor White
Write-Host ""

# Pause
Write-Host "Appuyez sur Entrée pour voir la Discussion 2..." -ForegroundColor Cyan
Read-Host

# Discussion 2 - FAQ
Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "DISCUSSION 2 : FAQ" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "Catégorie : ❓ Q&A" -ForegroundColor Yellow
Write-Host "Titre : ❓ FAQ - Version 3.1.0 : Questions fréquentes sur le correctif case-sensitivity" -ForegroundColor Yellow
Write-Host ""
Write-Host "Fichier source : doc\FAQ_V3.1.0.md" -ForegroundColor White
Write-Host "Commande pour ouvrir :" -ForegroundColor White
Write-Host "  notepad doc\FAQ_V3.1.0.md" -ForegroundColor Gray
Write-Host ""
Write-Host "📝 Actions :" -ForegroundColor Yellow
Write-Host "  1. Copiez le contenu du fichier FAQ_V3.1.0.md" -ForegroundColor White
Write-Host "  2. Collez-le dans la nouvelle discussion" -ForegroundColor White
Write-Host "  3. Cliquez 'Start discussion'" -ForegroundColor White
Write-Host ""

# Pause
Write-Host "Appuyez sur Entrée pour voir la Discussion 3..." -ForegroundColor Cyan
Read-Host

# Discussion 3 - Guide de migration
Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "DISCUSSION 3 : GUIDE DE MIGRATION" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "Catégorie : 💡 General" -ForegroundColor Yellow
Write-Host "Titre : 🚀 Guide de Migration Rapide vers v3.1.0" -ForegroundColor Yellow
Write-Host ""
Write-Host "Fichier source : doc\MIGRATION_GUIDE_V3.1.0.md" -ForegroundColor White
Write-Host "Commande pour ouvrir :" -ForegroundColor White
Write-Host "  notepad doc\MIGRATION_GUIDE_V3.1.0.md" -ForegroundColor Gray
Write-Host ""
Write-Host "📝 Actions :" -ForegroundColor Yellow
Write-Host "  1. Copiez le contenu du fichier MIGRATION_GUIDE_V3.1.0.md" -ForegroundColor White
Write-Host "  2. Collez-le dans la nouvelle discussion" -ForegroundColor White
Write-Host "  3. Cliquez 'Start discussion'" -ForegroundColor White
Write-Host ""

# Pause
Write-Host "Appuyez sur Entrée pour voir les commandes rapides..." -ForegroundColor Cyan
Read-Host

# Commandes rapides
Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "COMMANDES RAPIDES" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "Pour ouvrir tous les fichiers à copier :" -ForegroundColor Yellow
Write-Host ""
Write-Host "notepad doc\DISCUSSION_V3.1.0.md" -ForegroundColor White
Write-Host "notepad doc\FAQ_V3.1.0.md" -ForegroundColor White
Write-Host "notepad doc\MIGRATION_GUIDE_V3.1.0.md" -ForegroundColor White
Write-Host ""
Write-Host "Pour ouvrir GitHub Discussions :" -ForegroundColor Yellow
Write-Host ""
Write-Host "start https://github.com/llang57/editeurPanovisu/discussions/new" -ForegroundColor White
Write-Host ""

# Résumé
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "RÉSUMÉ DES DISCUSSIONS À CRÉER" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""

$discussions = @(
    @{
        Numero = "1"
        Categorie = "📢 Announcements"
        Titre = "🔧 Version 3.1.0 - Correctif Critique"
        Fichier = "DISCUSSION_V3.1.0.md"
        Priorite = "HAUTE"
    },
    @{
        Numero = "2"
        Categorie = "❓ Q&A"
        Titre = "❓ FAQ - Version 3.1.0"
        Fichier = "FAQ_V3.1.0.md"
        Priorite = "HAUTE"
    },
    @{
        Numero = "3"
        Categorie = "💡 General"
        Titre = "🚀 Guide de Migration Rapide"
        Fichier = "MIGRATION_GUIDE_V3.1.0.md"
        Priorite = "MOYENNE"
    }
)

foreach ($disc in $discussions) {
    Write-Host "[$($disc.Numero)] " -ForegroundColor Cyan -NoNewline
    Write-Host "$($disc.Titre)" -ForegroundColor White
    Write-Host "    Catégorie : $($disc.Categorie)" -ForegroundColor Gray
    Write-Host "    Fichier   : $($disc.Fichier)" -ForegroundColor Gray
    Write-Host "    Priorité  : $($disc.Priorite)" -ForegroundColor $(if ($disc.Priorite -eq "HAUTE") { "Yellow" } else { "White" })
    Write-Host ""
}

Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""

# Offrir d'ouvrir les fichiers
Write-Host "Voulez-vous ouvrir tous les fichiers maintenant ? (O/N)" -ForegroundColor Yellow
$reponse = Read-Host

if ($reponse -eq "O" -or $reponse -eq "o") {
    Write-Host ""
    Write-Host "Ouverture des fichiers..." -ForegroundColor Green
    Start-Process "notepad" -ArgumentList "doc\DISCUSSION_V3.1.0.md"
    Start-Sleep -Seconds 1
    Start-Process "notepad" -ArgumentList "doc\FAQ_V3.1.0.md"
    Start-Sleep -Seconds 1
    Start-Process "notepad" -ArgumentList "doc\MIGRATION_GUIDE_V3.1.0.md"
    Start-Sleep -Seconds 1
    Write-Host ""
    Write-Host "✅ Fichiers ouverts dans Notepad" -ForegroundColor Green
}

Write-Host ""
Write-Host "Voulez-vous ouvrir GitHub Discussions maintenant ? (O/N)" -ForegroundColor Yellow
$reponse = Read-Host

if ($reponse -eq "O" -or $reponse -eq "o") {
    Write-Host ""
    Write-Host "Ouverture de GitHub Discussions..." -ForegroundColor Green
    Start-Process "https://github.com/llang57/editeurPanovisu/discussions/new"
    Start-Sleep -Seconds 1
    Write-Host ""
    Write-Host "✅ Page GitHub ouverte dans le navigateur" -ForegroundColor Green
}

Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host "✅ Prêt à créer les discussions !" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Cyan
Write-Host ""
Write-Host "📝 N'oubliez pas :" -ForegroundColor Yellow
Write-Host "  1. Copiez le contenu de chaque fichier" -ForegroundColor White
Write-Host "  2. Créez la discussion avec la bonne catégorie" -ForegroundColor White
Write-Host "  3. Utilisez le titre exact indiqué" -ForegroundColor White
Write-Host "  4. Cliquez 'Start discussion'" -ForegroundColor White
Write-Host ""
Write-Host "Bonne création de discussions ! 🎉" -ForegroundColor Green
Write-Host ""
