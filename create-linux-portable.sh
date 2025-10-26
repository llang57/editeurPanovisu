#!/bin/bash

# Script de création d'une archive portable Linux pour EditeurPanovisu
# Usage: ./create-linux-portable.sh

set -e

echo "=== Création de l'archive portable Linux EditeurPanovisu ==="

# Vérifier que le build a été fait
if [ ! -f "target/app-input/editeurPanovisu-3.3.3-SNAPSHOT.jar" ]; then
    echo "❌ Erreur: Le JAR n'existe pas. Exécutez d'abord: mvn clean package -DskipTests -Pportable"
    echo "   (Le profil -Pportable est requis pour inclure les natives Linux et Windows)"
    exit 1
fi

# Vérifier que les fichiers d'installation existent
if [ ! -f "doc/install/INSTALLATION.md" ] || \
   [ ! -f "doc/install/INSTALLATION.txt" ] || \
   [ ! -f "doc/install/INSTALLATION.html" ]; then
    echo "❌ Erreur: Les fichiers d'installation sont manquants dans doc/install/"
    exit 1
fi

# Créer le répertoire de travail
LINUX_DIR="target/linux-portable"
if [ -d "$LINUX_DIR" ]; then
    rm -rf "$LINUX_DIR"
fi
mkdir -p "$LINUX_DIR"

echo "📁 Copie des fichiers depuis app-input..."

# Copier tout le contenu de app-input
cp -r target/app-input/* "$LINUX_DIR/"

# Supprimer les fichiers Windows inutiles pour Linux
echo "🗑️  Suppression des fichiers Windows (.bat, .vbs)..."
find "$LINUX_DIR" -name "*.bat" -delete 2>/dev/null || true
find "$LINUX_DIR" -name "*.vbs" -delete 2>/dev/null || true

# Vider configPV mais garder le répertoire
echo "🧹 Vidage du répertoire configPV..."
if [ -d "$LINUX_DIR/configPV" ]; then
    rm -rf "$LINUX_DIR/configPV"/*
fi

# Créer le script de lancement Linux
echo "📝 Création du script de lancement Linux..."
cat > "$LINUX_DIR/lancer-editeur-panovisu.sh" << 'EOF'
#!/bin/bash
# Script de lancement EditeurPanovisu pour Linux

echo "=== Lancement EditeurPanovisu ==="
echo "Répertoire de travail: $(pwd)"
echo ""

# Détecter le répertoire du script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

# Configuration JavaFX 3D
export PRISM_VERBOSE=false
export PRISM_FORCEGL=true
export PRISM_FORCEGPU=true
export PRISM_ORDER=es2,sw

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo "❌ Erreur: Java n'est pas installé ou n'est pas dans le PATH"
    echo "   Consultez INSTALLATION.txt pour les instructions"
    exit 1
fi

# Vérifier la version Java
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 25 ]; then
    echo "⚠️  Attention: Java 25 ou supérieur est recommandé (version détectée: $JAVA_VERSION)"
fi

echo "🚀 === Démarrage de l'application ==="
java -Dfile.encoding=UTF-8 \
     --enable-native-access=ALL-UNNAMED \
     -Xms256m \
     -Xmx4096m \
     -jar editeurPanovisu-3.3.3-SNAPSHOT.jar

exit_code=$?

if [ $exit_code -eq 0 ]; then
    echo "✅ OK: EditeurPanovisu terminé normalement"
else
    echo "❌ Erreur: EditeurPanovisu terminé avec le code d'erreur: $exit_code"
fi

exit $exit_code
EOF

# Rendre le script exécutable
chmod +x "$LINUX_DIR/lancer-editeur-panovisu.sh"

# Copier les fichiers d'installation depuis doc/install/
echo "📄 Copie des fichiers d'installation..."
cp "doc/install/INSTALLATION.md" "$LINUX_DIR/INSTALLATION.md"
cp "doc/install/INSTALLATION.txt" "$LINUX_DIR/INSTALLATION.txt"
cp "doc/install/INSTALLATION.html" "$LINUX_DIR/INSTALLATION.html"

# Copier les fichiers d'aide dans le répertoire doc/
echo "📚 Copie des fichiers d'aide dans doc/..."
mkdir -p "$LINUX_DIR/doc"
if [ -f "doc/PRESENTATION.md" ]; then
    cp "doc/PRESENTATION.md" "$LINUX_DIR/doc/PRESENTATION.md"
fi
if [ -f "doc/INSTALLATION_OLLAMA.md" ]; then
    cp "doc/INSTALLATION_OLLAMA.md" "$LINUX_DIR/doc/INSTALLATION_OLLAMA.md"
fi

# Créer l'archive ZIP
echo "📦 Création de l'archive ZIP..."
VERSION="3.3.3"
ZIP_FILE="target/EditeurPanovisu-Linux-Portable-$VERSION.zip"
if [ -f "$ZIP_FILE" ]; then
    rm "$ZIP_FILE"
fi

# Vérifier si zip est installé
if command -v zip &> /dev/null; then
    cd "$LINUX_DIR"
    zip -r "../EditeurPanovisu-Linux-Portable-$VERSION.zip" ./*
    cd - > /dev/null
    echo "✅ Archive créée: $ZIP_FILE"
else
    echo "⚠️  Attention: 'zip' n'est pas installé. Vous pouvez l'installer avec:"
    echo "   sudo apt install zip"
fi

# Créer l'archive TAR.GZ
echo "📦 Création de l'archive TAR.GZ..."
TAR_FILE="target/EditeurPanovisu-Linux-Portable-$VERSION.tar.gz"

cd "$LINUX_DIR"
tar -czf "../EditeurPanovisu-Linux-Portable-$VERSION.tar.gz" ./*
cd - > /dev/null

echo "✅ Archive créée: $TAR_FILE"

echo ""
echo "🎉 === Archive portable Linux créée avec succès ==="
echo ""
echo "📋 Contenu:"
echo "  - Application complète avec toutes les ressources"
echo "  - Script de lancement: lancer-editeur-panovisu.sh"
echo "  - Documentation d'installation: INSTALLATION.txt/.md/.html"
echo "  - Répertoire configPV vide (sera créé au premier lancement)"
echo ""
echo "📁 Fichiers générés:"
if [ -f "$ZIP_FILE" ]; then
    echo "  - $ZIP_FILE ($(du -h "$ZIP_FILE" | cut -f1))"
fi
echo "  - $TAR_FILE ($(du -h "$TAR_FILE" | cut -f1))"
echo ""
echo "💡 Pour tester l'archive:"
echo "  1. Extraire l'archive dans un répertoire temporaire"
echo "  2. cd vers le répertoire extrait"
echo "  3. ./lancer-editeur-panovisu.sh"
echo ""