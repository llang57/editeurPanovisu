#!/bin/bash

# Script de lancement EditeurPanovisu pour Linux avec support 3D
# Build 3587+

# Détecter automatiquement le répertoire du script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

echo "=== Lancement EditeurPanovisu ==="
echo "Répertoire de travail: $SCRIPT_DIR"
echo ""

# Vérifier la présence du JAR
if [ ! -f "editeurPanovisu-3.3.2-SNAPSHOT.jar" ]; then
    echo "❌ ERREUR: editeurPanovisu-3.3.2-SNAPSHOT.jar non trouvé dans $SCRIPT_DIR"
    echo ""
    echo "📝 Instructions:"
    echo "1. Copiez tout le contenu de target/app-input/ depuis Windows"
    echo "2. Ou copiez au minimum:"
    echo "   - editeurPanovisu-3.3.2-SNAPSHOT.jar"
    echo "   - jmapviewer-2.18.jar"
    echo "   - Tous les dossiers de ressources (aide/, css/, images/, etc.)"
    exit 1
fi

# Variables d'environnement pour forcer JavaFX 3D
export PRISM_FORCEGL=true
export PRISM_FORCEGPU=true
export PRISM_ORDER=es2,sw

# Forcer OpenGL dans le système
export LIBGL_ALWAYS_SOFTWARE=0
export LIBGL_ALWAYS_INDIRECT=0

echo "=== Configuration JavaFX 3D ==="
echo "PRISM_VERBOSE: true"
echo "PRISM_FORCEGL: $PRISM_FORCEGL"
echo "PRISM_FORCEGPU: $PRISM_FORCEGPU"
echo "PRISM_ORDER: $PRISM_ORDER"
echo ""

echo "=== Test OpenGL ==="
glxinfo | grep "OpenGL version" || echo "⚠️  glxinfo non disponible (installer mesa-utils)"
echo ""

# Lancement avec options JavaFX 3D (ES2 Pipeline activé ✅)
java \
  --module-path /usr/share/openjfx/lib \
  --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media \
  -Dprism.forceGPU=true \
  -Dprism.order=es2,sw \
  -Dprism.verbose=true \
  -jar editeurPanovisu-3.3.2-SNAPSHOT.jar 2>&1 | tee editeur-panovisu.log

EXIT_CODE=$?

echo ""
if [ $EXIT_CODE -eq 0 ]; then
    echo "✅ EditeurPanovisu terminé normalement"
else
    echo "❌ EditeurPanovisu terminé avec le code d'erreur: $EXIT_CODE"
fi
echo "=== Log sauvegardé dans editeur-panovisu.log ==="
