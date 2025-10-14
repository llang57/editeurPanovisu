#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour recréer TOUS les fichiers SVG avec un format strictement valide
Format: une seule ligne, UTF-8 sans BOM, LF comme fin de ligne
"""

import os

# Répertoire des SVG
svg_dir = "images/svg"

# Tous les SVG (format strict: une seule ligne)
svgs = {
    "nouveau-projet.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M14 2H6c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V8l-6-6zm4 18H6V4h7v5h5v11zM8 15.01l1.41 1.41L11 14.84V19h2v-4.16l1.59 1.59L16 15.01 12.01 11z"/></svg>',
    
    "ouvrir-projet.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M20 6h-8l-2-2H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm-1 12H5c-.55 0-1-.45-1-1V9c0-.55.45-1 1-1h14c.55 0 1 .45 1 1v8c0 .55-.45 1-1 1z"/></svg>',
    
    "sauve-projet.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm2 16H5V5h11.17L19 7.83V19zm-7-7c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3zM6 6h9v4H6z"/></svg>',
    
    "ajoute-panoramique.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M23 18V6c0-1.1-.9-2-2-2H3c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2zM8.5 12.5l2.5 3.01L14.5 11l4.5 6H5l3.5-4.5z"/></svg>',
    
    "ajoute-plan.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM10 5.47l4 1.4v11.66l-4-1.4V5.47zm-5 .99l3-1.01v11.7l-3 1.16V6.46zm14 11.08l-3 1.01V6.86l3-1.16v11.84z"/></svg>',
    
    "genere-visite.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><circle fill="currentColor" cx="12" cy="12" r="10" opacity="0.1"/><path fill="currentColor" d="M12 2C6.5 2 2 6.5 2 12s4.5 10 10 10 10-4.5 10-10S17.5 2 12 2zm0 18c-4.4 0-8-3.6-8-8s3.6-8 8-8 8 3.6 8 8-3.6 8-8 8z"/><path fill="currentColor" d="M12.5 6h-1v6.4l4.7 2.8 0.5-0.8-4.2-2.5V6z"/><circle fill="currentColor" cx="12" cy="12" r="1.5"/></svg>',
    
    "valide.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/></svg>',
    
    "annule.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M12 2C6.47 2 2 6.47 2 12s4.47 10 10 10 10-4.47 10-10S17.53 2 12 2zm5 13.59L15.59 17 12 13.41 8.41 17 7 15.59 10.59 12 7 8.41 8.41 7 12 10.59 15.59 7 17 8.41 13.41 12 17 15.59z"/></svg>',
    
    "ajoute-image.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M21 19V5c0-1.1-.9-2-2-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zM8.5 13.5l2.5 3.01L14.5 12l4.5 6H5l3.5-4.5z"/></svg>',
    
    "lien.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M3.9 12c0-1.71 1.39-3.1 3.1-3.1h4V7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h4v-1.9H7c-1.71 0-3.1-1.39-3.1-3.1zM8 13h8v-2H8v2zm9-6h-4v1.9h4c1.71 0 3.1 1.39 3.1 3.1s-1.39 3.1-3.1 3.1h-4V17h4c2.76 0 5-2.24 5-5s-2.24-5-5-5z"/></svg>',
    
    "loupe.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/></svg>',
    
    "ajoute.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/></svg>',
    
    "suppr.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/></svg>',
    
    "play-auto-tour.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M8 5v14l11-7z"/></svg>',
    
    "pause-auto-tour.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M6 19h4V5H6v14zm8-14v14h4V5h-4z"/></svg>',
    
    "maison.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/></svg>',
    
    "modifie.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>',
    
    "ferme.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg>',
    
    "expand.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M7 14H5v5h5v-2H7v-3zm-2-4h2V7h3V5H5v5zm12 7h-3v2h5v-5h-2v3zM14 5v2h3v3h2V5h-5z"/></svg>',
    
    "shrink.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="currentColor" d="M5 16h3v3h2v-5H5v2zm3-8H5v2h5V5H8v3zm6 11h2v-3h3v-2h-5v5zm2-11V5h-2v5h5V8h-3z"/></svg>',
    
    "vue-sphere.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 26 18" width="26" height="18"><circle fill="currentColor" cx="4.5" cy="9" r="4" opacity="0.1"/><circle fill="none" stroke="currentColor" stroke-width="0.5" cx="4.5" cy="9" r="3.5"/><path fill="none" stroke="currentColor" stroke-width="0.4" d="M4.5 5.5v7M1 9h7"/><ellipse fill="none" stroke="currentColor" stroke-width="0.4" cx="4.5" cy="9" rx="3.5" ry="1.8"/><ellipse fill="none" stroke="currentColor" stroke-width="0.4" cx="4.5" cy="9" rx="1.8" ry="3.5"/><path fill="currentColor" d="M9 7.5l3.5 1.5-3.5 1.5z"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="16" y="5" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="13" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="16" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="19" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="22" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="16" y="11" width="3" height="3"/></svg>',
    
    "vue-cube.svg": '<svg xmlns="http://www.w3.org/2000/svg" viewBox="-1 0 26 18" width="26" height="18"><rect fill="none" stroke="currentColor" stroke-width="0.5" x="5" y="5" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="-1" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="2" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="5" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="8" y="8" width="3" height="3"/><rect fill="none" stroke="currentColor" stroke-width="0.5" x="5" y="11" width="3" height="3"/><path fill="currentColor" d="M12 7.5l3.5 1.5-3.5 1.5z"/><circle fill="currentColor" cx="19.5" cy="9" r="4" opacity="0.1"/><circle fill="none" stroke="currentColor" stroke-width="0.5" cx="19.5" cy="9" r="3.5"/><path fill="none" stroke="currentColor" stroke-width="0.4" d="M19.5 5.5v7M16 9h7"/><ellipse fill="none" stroke="currentColor" stroke-width="0.4" cx="19.5" cy="9" rx="3.5" ry="1.8"/><ellipse fill="none" stroke="currentColor" stroke-width="0.4" cx="19.5" cy="9" rx="1.8" ry="3.5"/></svg>',
}

print("=== Recréation de TOUS les fichiers SVG ===\n")

for filename, content in svgs.items():
    filepath = os.path.join(svg_dir, filename)
    print(f"Écriture: {filename:<25} ({len(content)} caractères)")
    
    # Écrire en format strict: UTF-8 sans BOM, une seule ligne
    with open(filepath, 'w', encoding='utf-8', newline='') as f:
        f.write(content)

print(f"\n=== Terminé ===")
print(f"Fichiers recréés: {len(svgs)}")
