#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script d'analyse des fichiers de traduction PanoVisu
Génère un rapport Markdown avec les clés manquantes par langue
"""

import os
import re
from pathlib import Path
from datetime import datetime
from collections import defaultdict

# Configuration
I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"
OUTPUT_FILE = Path(__file__).parent.parent / "doc" / "RAPPORT_TRADUCTIONS.md"

# Langues supportées
LANGUAGES = {
    'PanoVisu.properties': 'English (Default)',
    'PanoVisu_fr.properties': 'Français',
    'PanoVisu_de.properties': 'Deutsch (German)',
    'PanoVisu_es_ES.properties': 'Español (Spanish)',
    'PanoVisu_pt.properties': 'Português (Portuguese)',
    'PanoVisu_en.properties': 'English (Explicit)'
}


def parse_properties_file(filepath):
    """
    Parse un fichier .properties et extrait les clés
    
    Args:
        filepath: Chemin vers le fichier .properties
        
    Returns:
        dict: Dictionnaire {clé: valeur}
    """
    properties = {}
    
    if not filepath.exists():
        return properties
    
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            for line_num, line in enumerate(f, 1):
                # Ignorer les lignes vides et les commentaires
                line = line.strip()
                if not line or line.startswith('#') or line.startswith('!'):
                    continue
                
                # Parser la ligne clé=valeur
                match = re.match(r'^([^=:]+)\s*[=:]\s*(.*)$', line)
                if match:
                    key = match.group(1).strip()
                    value = match.group(2).strip()
                    properties[key] = value
    except Exception as e:
        print(f"⚠️  Erreur lors de la lecture de {filepath.name}: {e}")
    
    return properties


def analyze_translations():
    """
    Analyse tous les fichiers de traduction
    
    Returns:
        tuple: (dict de propriétés par langue, statistiques)
    """
    all_properties = {}
    all_keys = set()
    
    print("📊 Analyse des fichiers de traduction...")
    print("-" * 60)
    
    # Charger tous les fichiers
    for filename, language_name in LANGUAGES.items():
        filepath = I18N_DIR / filename
        if filepath.exists():
            properties = parse_properties_file(filepath)
            all_properties[filename] = properties
            all_keys.update(properties.keys())
            print(f"✅ {filename:30} - {len(properties):4} clés - {language_name}")
        else:
            all_properties[filename] = {}
            print(f"❌ {filename:30} - Fichier manquant")
    
    print("-" * 60)
    print(f"📦 Total de clés uniques: {len(all_keys)}")
    print()
    
    return all_properties, all_keys


def generate_markdown_report(all_properties, all_keys):
    """
    Génère un rapport Markdown complet
    
    Args:
        all_properties: Dictionnaire des propriétés par langue
        all_keys: Ensemble de toutes les clés
    """
    print("📝 Génération du rapport Markdown...")
    
    # Fichier de référence (anglais par défaut)
    reference_file = 'PanoVisu.properties'
    reference_keys = set(all_properties.get(reference_file, {}).keys())
    
    # Calculer les statistiques
    stats = {}
    missing_keys = defaultdict(list)
    extra_keys = defaultdict(list)
    
    for filename, properties in all_properties.items():
        if not properties:
            continue
            
        current_keys = set(properties.keys())
        missing = reference_keys - current_keys
        extra = current_keys - reference_keys
        
        stats[filename] = {
            'total': len(properties),
            'missing': len(missing),
            'extra': len(extra),
            'coverage': (len(current_keys & reference_keys) / len(reference_keys) * 100) if reference_keys else 0
        }
        
        missing_keys[filename] = sorted(missing)
        extra_keys[filename] = sorted(extra)
    
    # Générer le rapport
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        # En-tête
        f.write("# 📊 Rapport d'analyse des traductions PanoVisu\n\n")
        f.write(f"**Date**: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}\n\n")
        f.write(f"**Répertoire**: `{I18N_DIR.relative_to(I18N_DIR.parent.parent.parent)}`\n\n")
        f.write("---\n\n")
        
        # Résumé
        f.write("## 📈 Résumé\n\n")
        f.write(f"- **Fichier de référence**: `{reference_file}` ({len(reference_keys)} clés)\n")
        f.write(f"- **Langues supportées**: {len(LANGUAGES)}\n")
        f.write(f"- **Clés totales uniques**: {len(all_keys)}\n\n")
        
        # Table de couverture
        f.write("## 🎯 Couverture par langue\n\n")
        f.write("| Fichier | Langue | Total clés | Manquantes | En trop | Couverture |\n")
        f.write("|---------|--------|------------|------------|---------|------------|\n")
        
        for filename in sorted(LANGUAGES.keys()):
            language_name = LANGUAGES[filename]
            if filename in stats:
                stat = stats[filename]
                coverage_color = "🟢" if stat['coverage'] >= 95 else "🟡" if stat['coverage'] >= 80 else "🔴"
                f.write(f"| `{filename}` | {language_name} | {stat['total']} | {stat['missing']} | {stat['extra']} | {coverage_color} {stat['coverage']:.1f}% |\n")
            else:
                f.write(f"| `{filename}` | {language_name} | ❌ | - | - | 🔴 0.0% |\n")
        
        f.write("\n---\n\n")
        
        # Détails par langue
        f.write("## 📋 Détails par langue\n\n")
        
        for filename in sorted(LANGUAGES.keys()):
            if filename == reference_file:
                continue  # Pas de comparaison pour le fichier de référence
                
            language_name = LANGUAGES[filename]
            f.write(f"### {language_name} (`{filename}`)\n\n")
            
            if filename not in stats:
                f.write("❌ **Fichier manquant**\n\n")
                continue
            
            stat = stats[filename]
            missing = missing_keys[filename]
            extra = extra_keys[filename]
            
            # Statistiques
            f.write(f"**Statistiques**:\n")
            f.write(f"- Total de clés: {stat['total']}\n")
            f.write(f"- Couverture: {stat['coverage']:.1f}%\n")
            f.write(f"- Clés manquantes: {stat['missing']}\n")
            f.write(f"- Clés en trop: {stat['extra']}\n\n")
            
            # Clés manquantes
            if missing:
                f.write(f"#### ❌ Clés manquantes ({len(missing)})\n\n")
                f.write("<details>\n")
                f.write("<summary>Cliquez pour afficher la liste</summary>\n\n")
                f.write("```\n")
                for key in missing:
                    ref_value = all_properties[reference_file].get(key, '')
                    f.write(f"{key}={ref_value}\n")
                f.write("```\n")
                f.write("</details>\n\n")
            else:
                f.write("#### ✅ Aucune clé manquante\n\n")
            
            # Clés en trop
            if extra:
                f.write(f"#### ⚠️ Clés en trop (non présentes dans le fichier de référence) ({len(extra)})\n\n")
                f.write("<details>\n")
                f.write("<summary>Cliquez pour afficher la liste</summary>\n\n")
                f.write("```\n")
                for key in extra:
                    f.write(f"{key}\n")
                f.write("```\n")
                f.write("</details>\n\n")
            
            f.write("---\n\n")
        
        # Section des clés par catégorie
        f.write("## 🗂️ Analyse par catégorie\n\n")
        
        # Grouper les clés par préfixe
        categories = defaultdict(list)
        for key in sorted(reference_keys):
            prefix = key.split('.')[0] if '.' in key else 'autres'
            categories[prefix].append(key)
        
        f.write(f"Le fichier de référence contient **{len(categories)}** catégories:\n\n")
        
        for category, keys in sorted(categories.items()):
            f.write(f"### `{category}` ({len(keys)} clés)\n\n")
            
            # Tableau de couverture par langue pour cette catégorie
            f.write("| Langue | Couverture |\n")
            f.write("|--------|------------|\n")
            
            for filename, language_name in sorted(LANGUAGES.items()):
                if filename == reference_file or filename not in all_properties:
                    continue
                
                properties = all_properties[filename]
                category_keys = set(keys)
                present = sum(1 for k in category_keys if k in properties)
                coverage = (present / len(category_keys) * 100) if category_keys else 0
                coverage_color = "🟢" if coverage >= 95 else "🟡" if coverage >= 80 else "🔴"
                
                f.write(f"| {language_name} | {coverage_color} {present}/{len(category_keys)} ({coverage:.1f}%) |\n")
            
            f.write("\n")
        
        # Footer
        f.write("---\n\n")
        f.write("## 📝 Notes\n\n")
        f.write("- Le fichier de référence est `PanoVisu.properties` (anglais par défaut)\n")
        f.write("- 🟢 Couverture ≥ 95% : Excellent\n")
        f.write("- 🟡 Couverture ≥ 80% : Bon (quelques clés manquantes)\n")
        f.write("- 🔴 Couverture < 80% : À améliorer\n\n")
        f.write("**Pour contribuer**: Complétez les traductions manquantes dans les fichiers `.properties` correspondants.\n\n")
        f.write(f"*Rapport généré automatiquement par `{Path(__file__).name}`*\n")
    
    print(f"✅ Rapport généré: {OUTPUT_FILE}")
    print()


def print_summary(all_properties, all_keys):
    """
    Affiche un résumé dans la console
    """
    reference_file = 'PanoVisu.properties'
    reference_keys = set(all_properties.get(reference_file, {}).keys())
    
    print("=" * 60)
    print("📊 RÉSUMÉ")
    print("=" * 60)
    
    for filename, language_name in sorted(LANGUAGES.items()):
        if filename not in all_properties or not all_properties[filename]:
            print(f"❌ {language_name:25} - Fichier manquant")
            continue
        
        properties = all_properties[filename]
        current_keys = set(properties.keys())
        missing = len(reference_keys - current_keys)
        coverage = (len(current_keys & reference_keys) / len(reference_keys) * 100) if reference_keys else 0
        
        status = "🟢" if coverage >= 95 else "🟡" if coverage >= 80 else "🔴"
        print(f"{status} {language_name:25} - {coverage:5.1f}% ({missing} manquantes)")
    
    print("=" * 60)
    print()


def main():
    """
    Fonction principale
    """
    print()
    print("=" * 60)
    print("  ANALYSE DES TRADUCTIONS PANOVISU")
    print("=" * 60)
    print()
    
    # Vérifier que le répertoire existe
    if not I18N_DIR.exists():
        print(f"❌ Erreur: Le répertoire {I18N_DIR} n'existe pas")
        return 1
    
    # Analyser les fichiers
    all_properties, all_keys = analyze_translations()
    
    # Générer le rapport
    generate_markdown_report(all_properties, all_keys)
    
    # Afficher le résumé
    print_summary(all_properties, all_keys)
    
    print(f"📄 Rapport complet disponible: {OUTPUT_FILE.relative_to(OUTPUT_FILE.parent.parent)}")
    print()
    
    return 0


if __name__ == '__main__':
    exit(main())
