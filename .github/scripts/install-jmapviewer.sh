#!/bin/bash

# Script pour installer jmapviewer manuellement dans le repo Maven local
# Utilisé par GitHub Actions si nécessaire

echo "📦 Installation manuelle de jmapviewer..."

# Créer le répertoire
mkdir -p ~/.m2/repository/org/openstreetmap/jmapviewer/jmapviewer/2.18

# Télécharger depuis JOSM (si disponible)
curl -L -o ~/.m2/repository/org/openstreetmap/jmapviewer/jmapviewer/2.18/jmapviewer-2.18.jar \
  "https://josm.openstreetmap.de/repository/releases/org/openstreetmap/jmapviewer/jmapviewer/2.18/jmapviewer-2.18.jar" \
  || echo "⚠️ Téléchargement échoué"

# Créer un POM minimal
cat > ~/.m2/repository/org/openstreetmap/jmapviewer/jmapviewer/2.18/jmapviewer-2.18.pom <<'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.openstreetmap.jmapviewer</groupId>
  <artifactId>jmapviewer</artifactId>
  <version>2.18</version>
</project>
EOF

echo "✅ Installation terminée"
