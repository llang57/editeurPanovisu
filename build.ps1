# Script de Build et Test - editeurPanovisu v2.0
# PowerShell Script pour Windows

param(
    [Parameter(Mandatory=$false)]
    [ValidateSet("compile", "test", "package", "run", "clean", "full", "help")]
    [string]$Action = "help"
)

# Couleurs
$successColor = "Green"
$errorColor = "Red"
$infoColor = "Cyan"
$warningColor = "Yellow"

function Write-Success {
    param([string]$Message)
    Write-Host "✅ $Message" -ForegroundColor $successColor
}

function Write-Error-Custom {
    param([string]$Message)
    Write-Host "❌ $Message" -ForegroundColor $errorColor
}

function Write-Info {
    param([string]$Message)
    Write-Host "ℹ️  $Message" -ForegroundColor $infoColor
}

function Write-Warning-Custom {
    param([string]$Message)
    Write-Host "⚠️  $Message" -ForegroundColor $warningColor
}

function Write-Header {
    param([string]$Message)
    Write-Host ""
    Write-Host "============================================" -ForegroundColor Cyan
    Write-Host "  $Message" -ForegroundColor Cyan
    Write-Host "============================================" -ForegroundColor Cyan
    Write-Host ""
}

function Check-Prerequisites {
    Write-Header "Vérification des Prérequis"
    
    # Vérifier Maven
    try {
        $mavenVersion = mvn --version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
        Write-Success "Maven installé: $mavenVersion"
    } catch {
        Write-Error-Custom "Maven n'est pas installé ou pas dans le PATH"
        Write-Info "Installer depuis: https://maven.apache.org/download.cgi"
        return $false
    }
    
    # Vérifier Java
    try {
        $javaVersion = java -version 2>&1 | Select-String "version" | Select-Object -First 1
        Write-Success "Java installé: $javaVersion"
        
        # Vérifier version Java
        if ($javaVersion -match "version `"(\d+)") {
            $version = [int]$matches[1]
            if ($version -lt 17) {
                Write-Warning-Custom "Java $version détecté. Java 17+ recommandé (idéalement 25)"
            } elseif ($version -eq 25) {
                Write-Success "Java 25 - Version cible atteinte!"
            }
        }
    } catch {
        Write-Error-Custom "Java n'est pas installé ou pas dans le PATH"
        Write-Info "Installer depuis: https://adoptium.net/"
        return $false
    }
    
    # Vérifier pom.xml
    if (!(Test-Path "pom.xml")) {
        Write-Error-Custom "Fichier pom.xml introuvable"
        Write-Info "Êtes-vous dans le répertoire racine du projet?"
        return $false
    }
    
    Write-Success "Tous les prérequis sont satisfaits"
    return $true
}

function Do-Clean {
    Write-Header "Nettoyage du Projet"
    mvn clean
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Nettoyage terminé"
        return $true
    } else {
        Write-Error-Custom "Erreur lors du nettoyage"
        return $false
    }
}

function Do-Compile {
    Write-Header "Compilation du Projet"
    Write-Info "Compilation des sources Java..."
    
    mvn compile
    
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Compilation réussie"
        
        # Compter les fichiers compilés
        $classFiles = Get-ChildItem -Path "target\classes" -Recurse -Filter "*.class" -ErrorAction SilentlyContinue
        Write-Info "Fichiers .class générés: $($classFiles.Count)"
        
        return $true
    } else {
        Write-Error-Custom "Erreur de compilation"
        Write-Info "Consultez les erreurs ci-dessus pour plus de détails"
        return $false
    }
}

function Do-Test {
    Write-Header "Exécution des Tests"
    mvn test
    
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Tous les tests ont réussi"
        return $true
    } else {
        Write-Error-Custom "Des tests ont échoué"
        return $false
    }
}

function Do-Package {
    Write-Header "Création du Package JAR"
    mvn package -DskipTests
    
    if ($LASTEXITCODE -eq 0) {
        Write-Success "Package créé avec succès"
        
        # Trouver le JAR créé
        $jarFile = Get-ChildItem -Path "target" -Filter "*.jar" | Where-Object { $_.Name -notmatch "original" } | Select-Object -First 1
        if ($jarFile) {
            $jarSize = [math]::Round($jarFile.Length / 1MB, 2)
            Write-Info "JAR: $($jarFile.Name) ($jarSize MB)"
            Write-Info "Chemin: $($jarFile.FullName)"
        }
        
        return $true
    } else {
        Write-Error-Custom "Erreur lors de la création du package"
        return $false
    }
}

function Do-Run {
    Write-Header "Lancement de l'Application"
    Write-Info "Démarrage de editeurPanovisu..."
    Write-Warning-Custom "Appuyez sur Ctrl+C pour arrêter"
    Write-Host ""
    
    mvn javafx:run
}

function Do-Full {
    Write-Header "Build Complet"
    
    if (!(Check-Prerequisites)) {
        return
    }
    
    Write-Info "Étape 1/4: Nettoyage"
    if (!(Do-Clean)) {
        return
    }
    
    Write-Info "Étape 2/4: Compilation"
    if (!(Do-Compile)) {
        return
    }
    
    Write-Info "Étape 3/4: Tests"
    Do-Test  # Continue même si les tests échouent
    
    Write-Info "Étape 4/4: Packaging"
    if (!(Do-Package)) {
        return
    }
    
    Write-Header "Build Complet Terminé"
    Write-Success "Le projet est prêt à être exécuté"
    Write-Info "Pour lancer: .\build.ps1 run"
}

function Show-Help {
    Write-Host ""
    Write-Host "🚀 editeurPanovisu - Script de Build" -ForegroundColor Cyan
    Write-Host "=====================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Usage: .\build.ps1 <action>" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Actions disponibles:" -ForegroundColor White
    Write-Host ""
    Write-Host "  compile    " -NoNewline -ForegroundColor Green
    Write-Host "- Compile les sources Java"
    Write-Host "  test       " -NoNewline -ForegroundColor Green
    Write-Host "- Execute les tests unitaires"
    Write-Host "  package    " -NoNewline -ForegroundColor Green
    Write-Host "- Crée le fichier JAR"
    Write-Host "  run        " -NoNewline -ForegroundColor Green
    Write-Host "- Lance l'application"
    Write-Host "  clean      " -NoNewline -ForegroundColor Green
    Write-Host "- Nettoie les fichiers générés"
    Write-Host "  full       " -NoNewline -ForegroundColor Green
    Write-Host "- Build complet (clean + compile + test + package)"
    Write-Host "  help       " -NoNewline -ForegroundColor Green
    Write-Host "- Affiche cette aide"
    Write-Host ""
    Write-Host "Exemples:" -ForegroundColor Yellow
    Write-Host "  .\build.ps1 compile    # Compiler uniquement"
    Write-Host "  .\build.ps1 full       # Build complet"
    Write-Host "  .\build.ps1 run        # Lancer l'application"
    Write-Host ""
    Write-Host "Commandes Maven directes:" -ForegroundColor Yellow
    Write-Host "  mvn clean compile      # Nettoyer et compiler"
    Write-Host "  mvn test               # Tests"
    Write-Host "  mvn package            # Créer le JAR"
    Write-Host "  mvn javafx:run         # Lancer"
    Write-Host ""
    Write-Host "Documentation:" -ForegroundColor White
    Write-Host "  - QUICKSTART_MIGRATION.md"
    Write-Host "  - MIGRATION_JAVA25_PLAN.md"
    Write-Host "  - RESUME_MIGRATION.md"
    Write-Host ""
}

# Point d'entrée principal
try {
    switch ($Action) {
        "compile" {
            if (Check-Prerequisites) {
                Do-Compile
            }
        }
        "test" {
            if (Check-Prerequisites) {
                Do-Test
            }
        }
        "package" {
            if (Check-Prerequisites) {
                Do-Package
            }
        }
        "run" {
            if (Check-Prerequisites) {
                Do-Run
            }
        }
        "clean" {
            if (Check-Prerequisites) {
                Do-Clean
            }
        }
        "full" {
            Do-Full
        }
        "help" {
            Show-Help
        }
        default {
            Show-Help
        }
    }
} catch {
    Write-Error-Custom "Une erreur s'est produite: $_"
    exit 1
}
