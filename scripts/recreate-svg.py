#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour recréer proprement les fichiers SVG avec l'encodage correct
"""

import os

# Répertoire des SVG
svg_dir = "images/svg"

# Définition des SVG à recréer (format propre)
svgs = {
    "modifie.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>',
    
    "ferme.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg>',
    
    "expand.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M7 14H5v5h5v-2H7v-3zm-2-4h2V7h3V5H5v5zm12 7h-3v2h5v-5h-2v3zM14 5v2h3v3h2V5h-5z"/></svg>',
    
    "shrink.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M5 16h3v3h2v-5H5v2zm3-8H5v2h5V5H8v3zm6 11h2v-3h3v-2h-5v5zm2-11V5h-2v5h5V8h-3z"/></svg>',
}

print("=== Recréation des fichiers SVG ===\n")

for filename, content in svgs.items():
    filepath = os.path.join(svg_dir, filename)
    print(f"Écriture de: {filename}")
    
    # Écrire avec UTF-8 sans BOM
    with open(filepath, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    
    print(f"  ✓ OK - {len(content)} caractères\n")

print("=== Terminé ===")
print(f"Fichiers recréés: {len(svgs)}")
