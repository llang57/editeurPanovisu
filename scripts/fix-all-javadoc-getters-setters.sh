#!/bin/bash
# Script pour appliquer les corrections Javadoc à tous les fichiers Java

echo "🔍 Recherche de tous les fichiers Java..."
files=$(find src/editeurpanovisu -name "*.java" -type f | sort)
total=$(echo "$files" | wc -l)

echo "📄 $total fichiers Java trouvés"
echo ""

total_modifications=0
fichiers_modifies=0

for file in $files; do
    filename=$(basename "$file")
    echo -n "   Traitement: $filename ... "
    
    # Exécuter le script en mode dry-run pour compter
    result=$(python3 scripts/fix-javadoc-getters-setters.py "$file" 2>&1 | grep "getters/setters à compléter")
    
    if [[ $result =~ ([0-9]+) ]]; then
        count="${BASH_REMATCH[1]}"
        
        if [ "$count" -gt 0 ]; then
            # Appliquer les modifications
            python3 scripts/fix-javadoc-getters-setters.py "$file" --apply > /dev/null 2>&1
            echo "✓ $count modif(s)"
            total_modifications=$((total_modifications + count))
            fichiers_modifies=$((fichiers_modifies + 1))
        else
            echo "✓ déjà ok"
        fi
    else
        echo "✓ déjà ok"
    fi
done

echo ""
echo "=========================================="
echo "✅ Traitement terminé !"
echo "=========================================="
echo "   Fichiers modifiés : $fichiers_modifies / $total"
echo "   Total modifications : $total_modifications getters/setters"
echo ""
