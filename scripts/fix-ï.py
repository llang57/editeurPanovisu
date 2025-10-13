#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Correction spécifique du caractère Ï suivi d'espace ou tabulation
"""

filepath = "src/editeurpanovisu/EditeurPanovisu.java"

with open(filepath, 'r', encoding='utf-8') as f:
    content = f.read()

original = content
count_before = content.count('Ï')

# Remplacer toutes les occurrences de Ï par à
content = content.replace('Ï', 'à')

if content != original:
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"✅ {count_before} corrections effectuées : 'Ï' → 'à'")
else:
    print("✅ Aucune correction nécessaire")
