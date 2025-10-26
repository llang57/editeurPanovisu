#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script de nettoyage des fichiers de traduction PanoVisu
Supprime les clés obsolètes qui ne sont plus présentes dans le fichier de référence
"""

import os
import re
from pathlib import Path
from datetime import datetime

# Configuration
I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"
REFERENCE_FILE = "PanoVisu.properties"

# Langues à nettoyer (exclut le fichier de référence)
TRANSLATION_FILES = [
    'PanoVisu_fr.properties',
    'PanoVisu_de.properties',
    'PanoVisu_es_ES.properties',
    'PanoVisu_pt.properties',
    'PanoVisu_en.properties'
]


def parse_properties_file(filepath):
    """
    Parse un fichier .properties et extrait les clés avec leurs lignes
    
    Args:
        filepath: Chemin vers le fichier .properties
        
    Returns:
        tuple: (dict {clé: valeur}, list de toutes les lignes)
    """
    properties = {}
    lines = []
    
    if not filepath.exists():
        return properties, lines
    
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            lines = f.readlines()
            
        for line_num, line in enumerate(lines):
            # Garder les lignes vides et commentaires
            stripped = line.strip()
            if not stripped or stripped.startswith('#') or stripped.startswith('!'):
                continue
            
            # Parser la ligne clé=valeur
            match = re.match(r'^([^=:]+)\s*[=:]\s*(.*)$', stripped)
            if match:
                key = match.group(1).strip()
                value = match.group(2).strip()
                properties[key] = (value, line_num)
                
    except Exception as e:
        print(f"⚠️  Erreur lors de la lecture de {filepath.name}: {e}")
    
    return properties, lines


def clean_translation_file(ref_keys, translation_file):
    """
    Nettoie un fichier de traduction en supprimant les clés obsolètes
    
    Args:
        ref_keys: Set des clés du fichier de référence
        translation_file: Nom du fichier de traduction à nettoyer
        
    Returns:
        tuple: (nombre de clés supprimées, liste des clés supprimées)
    """
    filepath = I18N_DIR / translation_file
    
    if not filepath.exists():
        print(f"❌ Fichier non trouvé: {translation_file}")
        return 0, []
    
    print(f"\n🔍 Analyse de {translation_file}...")
    
    # Parser le fichier
    properties, lines = parse_properties_file(filepath)
    
    # Identifier les clés obsolètes
    trans_keys = set(properties.keys())
    obsolete_keys = trans_keys - ref_keys
    
    if not obsolete_keys:
        print(f"✅ Aucune clé obsolète trouvée")
        return 0, []
    
    print(f"⚠️  {len(obsolete_keys)} clé(s) obsolète(s) trouvée(s):")
    for key in sorted(obsolete_keys):
        print(f"   - {key}")
    
    # Créer une sauvegarde
    backup_path = filepath.with_suffix('.properties.bak')
    print(f"💾 Création d'une sauvegarde: {backup_path.name}")
    
    try:
        with open(filepath, 'r', encoding='utf-8') as f_in:
            with open(backup_path, 'w', encoding='utf-8') as f_out:
                f_out.write(f_in.read())
    except Exception as e:
        print(f"❌ Erreur lors de la création de la sauvegarde: {e}")
        return 0, []
    
    # Filtrer les lignes
    lines_to_keep = []
    lines_removed = 0
    
    for i, line in enumerate(lines):
        stripped = line.strip()
        
        # Garder les lignes vides et commentaires
        if not stripped or stripped.startswith('#') or stripped.startswith('!'):
            lines_to_keep.append(line)
            continue
        
        # Parser la ligne
        match = re.match(r'^([^=:]+)\s*[=:]\s*(.*)$', stripped)
        if match:
            key = match.group(1).strip()
            
            # Garder seulement si la clé est dans le fichier de référence
            if key in ref_keys:
                lines_to_keep.append(line)
            else:
                lines_removed += 1
                print(f"   🗑️  Suppression: {key}")
        else:
            # Ligne malformée, on la garde par sécurité
            lines_to_keep.append(line)
    
    # Écrire le fichier nettoyé
    try:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.writelines(lines_to_keep)
        print(f"✅ Fichier nettoyé: {lines_removed} ligne(s) supprimée(s)")
    except Exception as e:
        print(f"❌ Erreur lors de l'écriture du fichier: {e}")
        # Restaurer la sauvegarde en cas d'erreur
        with open(backup_path, 'r', encoding='utf-8') as f_in:
            with open(filepath, 'w', encoding='utf-8') as f_out:
                f_out.write(f_in.read())
        return 0, []
    
    return lines_removed, sorted(obsolete_keys)


def main():
    """
    Fonction principale
    """
    print()
    print("=" * 70)
    print("  NETTOYAGE DES FICHIERS DE TRADUCTION PANOVISU")
    print("=" * 70)
    print()
    
    # Vérifier que le répertoire existe
    if not I18N_DIR.exists():
        print(f"❌ Erreur: Le répertoire {I18N_DIR} n'existe pas")
        return 1
    
    # Charger le fichier de référence
    ref_path = I18N_DIR / REFERENCE_FILE
    if not ref_path.exists():
        print(f"❌ Erreur: Le fichier de référence {REFERENCE_FILE} n'existe pas")
        return 1
    
    print(f"📖 Lecture du fichier de référence: {REFERENCE_FILE}")
    ref_properties, _ = parse_properties_file(ref_path)
    ref_keys = set(ref_properties.keys())
    print(f"   {len(ref_keys)} clés de référence trouvées")
    
    # Nettoyer chaque fichier de traduction
    total_removed = 0
    all_obsolete = {}
    
    for translation_file in TRANSLATION_FILES:
        removed, obsolete = clean_translation_file(ref_keys, translation_file)
        total_removed += removed
        if obsolete:
            all_obsolete[translation_file] = obsolete
    
    # Résumé
    print()
    print("=" * 70)
    print("📊 RÉSUMÉ DU NETTOYAGE")
    print("=" * 70)
    
    if total_removed == 0:
        print("✅ Tous les fichiers sont déjà propres !")
    else:
        print(f"🗑️  Total de clés obsolètes supprimées: {total_removed}")
        print()
        print("Fichiers modifiés:")
        for filename, keys in all_obsolete.items():
            print(f"   • {filename}: {len(keys)} clé(s)")
        
        print()
        print("💾 Les sauvegardes ont été créées avec l'extension .properties.bak")
        print("   Vous pouvez les supprimer si le résultat est satisfaisant.")
    
    print()
    print("✅ Nettoyage terminé !")
    print()
    
    # Suggestion d'exécuter l'analyse
    print("💡 Astuce: Exécutez check-translations.py pour vérifier le résultat")
    print()
    
    return 0


if __name__ == '__main__':
    exit(main())
