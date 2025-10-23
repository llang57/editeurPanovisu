#!/bin/bash

# Script de préparation du package Linux depuis le build Maven
# À exécuter depuis Windows (Git Bash) ou Linux après avoir compilé

echo "=== Préparation du package EditeurPanovisu pour Linux ==="
echo ""

# Vérifier que target/app-input existe
if [ ! -d "target/app-input" ]; then
    echo "❌ ERREUR: Le répertoire target/app-input n'existe pas"
    echo ""
    echo "📝 Exécutez d'abord: mvn clean package"
    exit 1
fi

# Créer le répertoire de destination
DEST_DIR="editeur-panovisu-linux"
rm -rf "$DEST_DIR"
mkdir -p "$DEST_DIR"

echo "📦 Copie des fichiers depuis target/app-input/..."

# Copier tout le contenu de app-input
cp -r target/app-input/* "$DEST_DIR/"

# Copier le script de lancement
cp lancer-panovisu-linux.sh "$DEST_DIR/"
chmod +x "$DEST_DIR/lancer-panovisu-linux.sh"

echo ""
echo "✅ Package Linux préparé dans: $DEST_DIR/"
echo ""
echo "📝 Pour transférer vers Linux:"
echo "   1. Compresser: tar -czf editeur-panovisu-linux.tar.gz $DEST_DIR/"
echo "   2. Transférer le fichier .tar.gz vers Linux"
echo "   3. Décompresser: tar -xzf editeur-panovisu-linux.tar.gz"
echo "   4. Lancer: cd $DEST_DIR && ./lancer-panovisu-linux.sh"
echo ""

# Option: créer automatiquement l'archive
read -p "Créer l'archive .tar.gz maintenant? (o/N) " -n 1 -r
echo
if [[ $REPLY =~ ^[OoYy]$ ]]; then
    tar -czf editeur-panovisu-linux.tar.gz "$DEST_DIR/"
    echo "✅ Archive créée: editeur-panovisu-linux.tar.gz"
    echo "   Taille: $(du -h editeur-panovisu-linux.tar.gz | cut -f1)"
fi

echo ""
echo "✅ Préparation terminée!"
