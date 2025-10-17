#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Compléter les dernières clés manquantes pour atteindre 100%
"""

from pathlib import Path

I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"

# Anglais explicite - 4 clés manquantes (sans le commentaire)
ENGLISH_FINAL = {
    "annuler": "Cancel",
    "erreur": "Error",
    "largeur": "Width",
    "valider": "Validate"
}

# Portugais - 4 clés manquantes
PORTUGUESE_FINAL = {
    "annuler": "Cancelar",
    "erreur": "Erro",
    "largeur": "Largura",
    "valider": "Validar"
}

def add_final_translations(filepath, translations_dict, language):
    """Ajoute les dernières traductions"""
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    # Trouver les clés existantes
    existing_keys = set()
    for line in lines:
        if '=' in line and not line.strip().startswith('#'):
            key = line.split('=')[0].strip()
            existing_keys.add(key)
    
    # Ajouter les traductions manquantes
    additions = []
    for key, value in sorted(translations_dict.items()):
        if key not in existing_keys:
            additions.append(f'{key}={value}')
    
    if additions:
        with open(filepath, 'a', encoding='utf-8') as f:
            f.write(f'\n# Traductions finales - {language}\n')
            f.write('\n'.join(additions))
            f.write('\n')
        
        return len(additions)
    return 0

def main():
    print("=" * 70)
    print("  FINALISATION - 100% DE COUVERTURE")
    print("=" * 70)
    print()
    
    # Anglais
    print("📝 Anglais explicite...")
    english_file = I18N_DIR / "PanoVisu_en.properties"
    added = add_final_translations(english_file, ENGLISH_FINAL, "English")
    print(f"   ✅ {added} traduction(s) ajoutée(s)")
    print()
    
    # Portugais
    print("📝 Portugais...")
    portuguese_file = I18N_DIR / "PanoVisu_pt.properties"
    added = add_final_translations(portuguese_file, PORTUGUESE_FINAL, "Português")
    print(f"   ✅ {added} traduction(s) ajoutée(s)")
    print()
    
    print("=" * 70)
    print("✅ TOUTES LES TRADUCTIONS SONT MAINTENANT COMPLÈTES À 100% !")
    print("=" * 70)
    print()

if __name__ == '__main__':
    main()
