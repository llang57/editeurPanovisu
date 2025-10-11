# Script d'Audit de Sécurité des Clés API
# Vérifie qu'aucune clé API n'est exposée dans le repository

Write-Host ""
Write-Host "🔍 AUDIT DE SÉCURITÉ DES CLÉS API" -ForegroundColor Cyan
Write-Host "=" * 60 -ForegroundColor Cyan
Write-Host ""

$errorsFound = 0

# 1. Vérifier .gitignore
Write-Host "1️⃣  Vérification .gitignore..." -ForegroundColor Yellow
if (Test-Path ".gitignore") {
    $gitignoreContent = Get-Content .gitignore -Raw
    if ($gitignoreContent -match "api-keys\.properties") {
        Write-Host "   ✅ api-keys.properties est dans .gitignore" -ForegroundColor Green
    } else {
        Write-Host "   ❌ ERREUR: api-keys.properties manquant dans .gitignore!" -ForegroundColor Red
        $errorsFound++
    }
} else {
    Write-Host "   ⚠️  ATTENTION: Fichier .gitignore non trouvé" -ForegroundColor Yellow
}

# 2. Vérifier git status
Write-Host "`n2️⃣  Vérification git status..." -ForegroundColor Yellow
try {
    $gitStatus = git status --porcelain 2>&1
    $apiKeysTracked = $gitStatus | Select-String "api-keys.properties"
    if (-not $apiKeysTracked) {
        Write-Host "   ✅ api-keys.properties n'est pas tracé par Git" -ForegroundColor Green
    } else {
        Write-Host "   ❌ ERREUR: api-keys.properties est tracé par Git!" -ForegroundColor Red
        Write-Host "      $apiKeysTracked" -ForegroundColor Red
        $errorsFound++
    }
} catch {
    Write-Host "   ⚠️  Impossible de vérifier git status (Git installé?)" -ForegroundColor Yellow
}

# 3. Vérifier les fichiers Markdown
Write-Host "`n3️⃣  Vérification documentation (.md)..." -ForegroundColor Yellow
$mdFiles = Get-ChildItem -Recurse -Filter "*.md" -ErrorAction SilentlyContinue
$keysFoundInMd = @()

foreach ($file in $mdFiles) {
    # Pattern pour détecter les vraies clés (pas les placeholders)
    $content = Get-Content $file.FullName -Raw
    
    # Hugging Face keys - pattern simplifié
    if ($content -match "hf_[A-Za-z0-9]{30,50}" -and $content -notmatch "YOUR|EXAMPLE|TEMPLATE") {
        $keysFoundInMd += "   ❌ $($file.Name): Clé Hugging Face détectée"
        $errorsFound++
    }
    
    # OpenRouter keys - pattern simplifié
    if ($content -match "sk-or-v1-[A-Za-z0-9]{40,60}" -and $content -notmatch "YOUR|EXAMPLE|TEMPLATE") {
        $keysFoundInMd += "   ❌ $($file.Name): Clé OpenRouter détectée"
        $errorsFound++
    }
}

if ($keysFoundInMd.Count -eq 0) {
    Write-Host "   ✅ Aucune vraie clé trouvée dans les fichiers .md" -ForegroundColor Green
} else {
    Write-Host "   ❌ ERREUR: Clés API trouvées dans la documentation!" -ForegroundColor Red
    foreach ($finding in $keysFoundInMd) {
        Write-Host $finding -ForegroundColor Red
    }
}

# 4. Vérifier le fichier template
Write-Host "`n4️⃣  Vérification template..." -ForegroundColor Yellow
if (Test-Path "api-keys.properties.template") {
    $templateContent = Get-Content "api-keys.properties.template" -Raw
    
    $hasRealHfKey = $templateContent -match "hf_[A-Za-z0-9]{30,50}" -and $templateContent -notmatch "YOUR|EXAMPLE|HERE"
    $hasRealOrKey = $templateContent -match "sk-or-v1-[A-Za-z0-9]{40,60}" -and $templateContent -notmatch "YOUR|EXAMPLE|HERE"
    
    if (-not $hasRealHfKey -and -not $hasRealOrKey) {
        Write-Host "   ✅ Template ne contient pas de vraies clés" -ForegroundColor Green
    } else {
        Write-Host "   ❌ ERREUR: Vraies clés détectées dans le template!" -ForegroundColor Red
        $errorsFound++
    }
} else {
    Write-Host "   ⚠️  ATTENTION: Template api-keys.properties.template manquant" -ForegroundColor Yellow
}

# 5. Vérifier que le fichier api-keys.properties existe localement
Write-Host "`n5️⃣  Vérification configuration locale..." -ForegroundColor Yellow
if (Test-Path "api-keys.properties") {
    Write-Host "   ✅ Fichier api-keys.properties existe (configuration locale)" -ForegroundColor Green
    
    # Vérifier s'il contient des placeholders (mauvais signe)
    $localContent = Get-Content "api-keys.properties" -Raw
    if ($localContent -match "YOUR_API_KEY_HERE") {
        Write-Host "   ⚠️  ATTENTION: Fichier contient des placeholders (pas configuré)" -ForegroundColor Yellow
    } else {
        Write-Host "   ℹ️  Fichier semble configuré avec de vraies clés" -ForegroundColor Cyan
    }
} else {
    Write-Host "   ⚠️  ATTENTION: Fichier api-keys.properties manquant" -ForegroundColor Yellow
    Write-Host "      Créez-le depuis le template: Copy-Item api-keys.properties.template api-keys.properties" -ForegroundColor Gray
}

# 6. Vérifier les fichiers Java
Write-Host "`n6️⃣  Vérification code source (.java)..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Recurse -Filter "*.java" -ErrorAction SilentlyContinue
$keysFoundInJava = @()

foreach ($file in $javaFiles) {
    $content = Get-Content $file.FullName -Raw
    
    # Chercher les clés hardcodées (pas les variables)
    $patternHF = '"hf_[A-Za-z0-9]+'
    $patternOR = '"sk-or-v1-[A-Za-z0-9]+'
    
    if ($content -match $patternHF) {
        $keysFoundInJava += "   ❌ $($file.Name): Clé HF hardcodée détectée"
        $errorsFound++
    }
    
    if ($content -match $patternOR) {
        $keysFoundInJava += "   ❌ $($file.Name): Clé OR hardcodée détectée"
        $errorsFound++
    }
}

if ($keysFoundInJava.Count -eq 0) {
    Write-Host "   ✅ Aucune clé hardcodée dans les fichiers .java" -ForegroundColor Green
} else {
    Write-Host "   ❌ ERREUR: Clés hardcodées trouvées dans le code source!" -ForegroundColor Red
    foreach ($finding in $keysFoundInJava) {
        Write-Host $finding -ForegroundColor Red
    }
}

# 7. Vérifier l'historique Git (derniers commits)
Write-Host "`n7️⃣  Vérification historique Git récent..." -ForegroundColor Yellow
try {
    $recentCommits = git log --all --oneline -10 --grep="api-keys" 2>&1
    if ($recentCommits -and ($recentCommits | Measure-Object).Count -gt 0) {
        Write-Host "   ⚠️  ATTENTION: Commits mentionnant 'api-keys' trouvés" -ForegroundColor Yellow
        Write-Host "      Vérifiez que les clés n'ont pas été commitées:" -ForegroundColor Gray
        Write-Host "      git log --all -S 'hf_' --source" -ForegroundColor Gray
    } else {
        Write-Host "   ✅ Pas de commit suspect récent" -ForegroundColor Green
    }
} catch {
    Write-Host "   ℹ️  Vérification historique ignorée (Git non disponible)" -ForegroundColor Cyan
}

# Résumé final
Write-Host ""
Write-Host "=" * 60 -ForegroundColor Cyan
Write-Host "📊 RÉSUMÉ DE L'AUDIT" -ForegroundColor Cyan
Write-Host "=" * 60 -ForegroundColor Cyan
Write-Host ""

if ($errorsFound -eq 0) {
    Write-Host "✅ SUCCÈS: Aucune erreur de sécurité détectée" -ForegroundColor Green
    Write-Host ""
    Write-Host "   Le repository est sécurisé pour être poussé sur Git." -ForegroundColor Green
    Write-Host ""
} else {
    Write-Host "❌ ÉCHEC: $errorsFound erreur(s) détectée(s)" -ForegroundColor Red
    Write-Host ""
    Write-Host "   ⚠️  NE PAS POUSSER SUR GIT AVANT CORRECTION!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Actions recommandees:" -ForegroundColor Yellow
    Write-Host "   1. Corriger les erreurs listees ci-dessus" -ForegroundColor Gray
    Write-Host "   2. Relancer cet audit: .\audit-security.ps1" -ForegroundColor Gray
    Write-Host '   3. Si des cles ont ete commitees, voir doc\SECURITE_CLES_API.md' -ForegroundColor Gray
    Write-Host ""
}

Write-Host 'Documentation: doc\SECURITE_CLES_API.md' -ForegroundColor Cyan
Write-Host ""

# Code de sortie
if ($errorsFound -gt 0) {
    exit 1
} else {
    exit 0
}
