#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour corriger les problèmes d'encodage dans les fichiers Java
Remplace les caractères mal encodés par leurs équivalents UTF-8
"""

import os
import sys

def fix_encoding(filepath):
    """Corrige l'encodage d'un fichier"""
    
    # Dictionnaire des corrections (minuscules)
    corrections = {
        'Ã©': 'é',    # e accent aigu
        'Ã¨': 'è',    # e accent grave
        'Ãª': 'ê',    # e accent circonflexe
        'Ã ': 'à',    # a accent grave
        'Ã¢': 'â',    # a accent circonflexe
        'Ã§': 'ç',    # c cédille
        'Ã®': 'î',    # i accent circonflexe
        'Ã¯': 'ï',    # i tréma
        'Ã´': 'ô',    # o accent circonflexe
        'Ã¹': 'ù',    # u accent grave
        'Ã»': 'û',    # u accent circonflexe
        'Ã¼': 'ü',    # u tréma
        'Ã«': 'ë',    # e tréma
        # Majuscules
        'Ã‰': 'É',    # E accent aigu
        'Ãˆ': 'È',    # E accent grave
        'ÃŠ': 'Ê',    # E accent circonflexe
        'Ã€': 'À',    # A accent grave
        'Ã‚': 'Â',    # A accent circonflexe
        'Ã‡': 'Ç',    # C cédille
        'ÃŽ': 'Î',    # I accent circonflexe
        'Ã"': 'Ô',    # O accent circonflexe
        'Ã™': 'Ù',    # U accent grave
        'Ã›': 'Û',    # U accent circonflexe
        'Ã‹': 'Ë',    # E tréma
        'Ã': 'Ï',    # I tréma
        'Ãœ': 'Ü',    # U tréma
        # Cas spéciaux d'encodage double
        'Ï ': 'à ',   # à suivi d'espace (encodé deux fois)
        'Â°': '°',    # degré
        'â€™': "'",   # apostrophe
        'â€œ': '"',   # guillemet ouvrant
        'â€': '"',   # guillemet fermant
        'â€"': '—',   # tiret cadratin
        'â€"': '–',   # tiret demi-cadratin
        'â€¦': '…',   # points de suspension
    }
    
    # Lire le fichier
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            contenu = f.read()
    except Exception as e:
        print(f"❌ Erreur lors de la lecture de {filepath}: {e}")
        return False
    
    # Appliquer les corrections
    contenu_original = contenu
    nb_corrections = 0
    
    for mauvais, bon in corrections.items():
        if mauvais in contenu:
            occurrences = contenu.count(mauvais)
            print(f"   Correction de '{mauvais}' → '{bon}' ({occurrences} occurrence(s))")
            contenu = contenu.replace(mauvais, bon)
            nb_corrections += occurrences
    
    # Sauvegarder si des modifications ont été faites
    if contenu != contenu_original:
        try:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(contenu)
            print(f"\n✅ {nb_corrections} corrections effectuées dans {filepath}")
            return True
        except Exception as e:
            print(f"❌ Erreur lors de l'écriture de {filepath}: {e}")
            return False
    else:
        print(f"✅ Aucun problème d'encodage détecté dans {filepath}")
        return True

def main():
    print("🔍 Recherche des problèmes d'encodage...\n")
    
    # Scanner tous les fichiers Java
    fichiers = []
    for root, dirs, files in os.walk("src"):
        for file in files:
            if file.endswith(".java"):
                fichiers.append(os.path.join(root, file))
    
    succes = True
    for fichier in fichiers:
        if os.path.exists(fichier):
            print(f"📝 Traitement de {fichier}...")
            if not fix_encoding(fichier):
                succes = False
            print()
        else:
            print(f"⚠️  Fichier non trouvé: {fichier}\n")
    
    if succes:
        print("✅ Tous les fichiers ont été traités avec succès !")
        return 0
    else:
        print("❌ Des erreurs se sont produites")
        return 1

if __name__ == "__main__":
    sys.exit(main())
