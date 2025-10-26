#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour ajouter une description aux Javadoc des getters/setters

Ce script utilise des regex simples pour ajouter une description minimale
aux Javadoc qui n'en ont pas (seulement @param ou @return).
"""

import re
import sys
from pathlib import Path

def process_file(filepath: str, dry_run: bool = True):
    """Traite un fichier Java pour ajouter les descriptions manquantes"""
    
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    modifications = 0
    
    # Pattern 1: Setter avec seulement @param (pas de texte avant)
    # Cherche: /**\n     * @param nomVar the XXX to set
    # Accepte les préfixes comme aNomVar, aiNomVar, etc.
    # MAIS PAS si déjà suivi d'une description (éviter les doublons)
    
    pattern_setter = r'(/\*\*\n)(\s+\* @param \w+ the (\w+) to set)'
    
    def replacement_setter(match):
        nonlocal modifications
        modifications += 1
        param_name = match.group(3)
        # Reconstruire: /** + description + ligne vide + @param original
        return f'{match.group(1)}     * Définit la valeur de {param_name}.\n     *\n{match.group(2)}'
    
    content_modified = re.sub(pattern_setter, replacement_setter, content)
    
    # Pattern 2: Getter avec seulement @return (pas de texte avant)
    # Cherche: /**\n     * @return the nomVar
    
    pattern_getter = r'(/\*\*\n)(\s+\* @return the (\w+))'
    
    def replacement_getter(match):
        nonlocal modifications
        modifications += 1
        param_name = match.group(3)
        return f'{match.group(1)}     * Retourne la valeur de {param_name}.\n     *\n{match.group(2)}'
    
    content_modified = re.sub(pattern_getter, replacement_getter, content_modified)
    
    # Pattern 3: Getter booléen (isXXX) avec seulement @return
    # Cherche: /**\n     * @return the nomVar
    # (après un isNomVar())
    # Ce pattern sera attrapé par pattern_getter ci-dessus, donc on n'a rien à faire de plus
    
    if dry_run:
        print(f"📊 {modifications} getters/setters à compléter")
        print("\nPour appliquer les modifications:")
        print(f"   python3 {sys.argv[0]} {filepath} --apply")
    else:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content_modified)
        print(f"✅ {modifications} getters/setters complétés")
    
    return modifications

def main():
    """Fonction principale"""
    import argparse
    
    parser = argparse.ArgumentParser(description="Ajoute des descriptions aux Javadoc des getters/setters")
    parser.add_argument("fichier", help="Fichier Java à traiter")
    parser.add_argument("--apply", action="store_true", help="Appliquer les modifications (sinon mode dry-run)")
    
    args = parser.parse_args()
    
    fichier_path = Path(args.fichier)
    
    if not fichier_path.exists():
        print(f"❌ Fichier non trouvé: {fichier_path}")
        return 1
    
    print(f"🔍 Traitement de {fichier_path.name}...")
    print()
    
    if not args.apply:
        print("📋 Mode DRY-RUN (aperçu seulement)")
        print()
    
    process_file(str(fichier_path), dry_run=not args.apply)
    
    return 0

if __name__ == "__main__":
    sys.exit(main())
