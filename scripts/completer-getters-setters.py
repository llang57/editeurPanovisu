#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour compléter les Javadoc des getters/setters avec une description minimale

Ce script détecte les getters/setters qui n'ont que le tag @param ou @return
sans description et ajoute automatiquement une description appropriée.
"""

import re
import sys
from pathlib import Path

def generer_description_setter(nom_propriete: str) -> str:
    """Génère une description pour un setter"""
    return f"Définit la valeur de {nom_propriete}."

def generer_description_getter(nom_propriete: str) -> str:
    """Génère une description pour un getter"""
    return f"Retourne la valeur de {nom_propriete}."

def extraire_nom_propriete_depuis_param(param_line: str) -> str:
    """Extrait le nom de la propriété depuis une ligne @param"""
    # Format: @param nomPropriete the nomPropriete to set
    match = re.search(r'@param\s+(\w+)', param_line)
    if match:
        return match.group(1)
    return None

def extraire_nom_propriete_depuis_methode(method_line: str) -> str:
    """Extrait le nom de la propriété depuis la signature de la méthode"""
    # Pour setter: public void setNomPropriete(type nomPropriete)
    # Pour getter: public type getNomPropriete() ou public type isNomPropriete()
    
    # Essayer d'extraire depuis un setter
    match = re.search(r'set(\w+)\s*\(', method_line)
    if match:
        prop_name = match.group(1)
        # Convertir en camelCase (première lettre en minuscule)
        return prop_name[0].lower() + prop_name[1:] if len(prop_name) > 1 else prop_name.lower()
    
    # Essayer d'extraire depuis un getter
    match = re.search(r'(get|is)(\w+)\s*\(\s*\)', method_line)
    if match:
        prop_name = match.group(2)
        # Convertir en camelCase
        return prop_name[0].lower() + prop_name[1:] if len(prop_name) > 1 else prop_name.lower()
    
    return None

def completer_javadoc_fichier(fichier_path: str, dry_run: bool = True) -> int:
    """
    Complète les Javadoc des getters/setters dans un fichier
    
    Returns:
        Nombre de modifications effectuées
    """
    with open(fichier_path, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    modifications = 0
    nouvelles_lignes = []
    i = 0
    
    while i < len(lignes):
        ligne = lignes[i]
        nouvelles_lignes.append(ligne)
        
        # Détecter le début d'un Javadoc
        if ligne.strip().startswith("/**"):
            javadoc_start = i
            javadoc_lines = [ligne]
            i += 1
            
            # Collecter tout le Javadoc
            while i < len(lignes) and not lignes[i].strip().endswith("*/"):
                javadoc_lines.append(lignes[i])
                i += 1
            
            if i < len(lignes):
                javadoc_lines.append(lignes[i])  # Ligne avec */
                i += 1
            
            # Analyser le Javadoc
            javadoc_text = "".join(javadoc_lines)
            
            # Vérifier si c'est un Javadoc incomplet (seulement @param ou @return, pas de description)
            has_param = "@param" in javadoc_text
            has_return = "@return" in javadoc_text
            
            # Vérifier si le Javadoc a une description (ligne après /** qui ne commence pas par @)
            has_description = False
            for jline in javadoc_lines[1:]:  # Ignorer la première ligne /**
                stripped = jline.strip()
                if stripped and stripped != "*" and not stripped.startswith("*/"):
                    if not stripped.startswith("* @"):
                        has_description = True
                        break
            
            # Si pas de description et qu'il y a @param ou @return
            if not has_description and (has_param or has_return):
                # Trouver la ligne de méthode (juste après le Javadoc)
                method_line = None
                if i < len(lignes):
                    # Peut être sur plusieurs lignes, chercher jusqu'à trouver '('
                    method_text = ""
                    j = i
                    while j < len(lignes) and j < i + 5:  # Chercher max 5 lignes
                        method_text += lignes[j].strip() + " "
                        if "(" in lignes[j]:
                            method_line = method_text
                            break
                        j += 1
                
                if method_line:
                    # Extraire le nom de la propriété
                    nom_propriete = extraire_nom_propriete_depuis_methode(method_line)
                    
                    if nom_propriete:
                        # Déterminer si c'est un getter ou setter
                        is_setter = "set" in method_line.lower() and has_param
                        is_getter = ("get" in method_line.lower() or "is" in method_line.lower()) and has_return
                        
                        if is_setter or is_getter:
                            modifications += 1
                            
                            # Générer la description
                            if is_setter:
                                description = generer_description_setter(nom_propriete)
                            else:
                                description = generer_description_getter(nom_propriete)
                            
                            # Reconstruire le Javadoc avec la description
                            nouvelles_lignes_javadoc = []
                            nouvelles_lignes_javadoc.append(javadoc_lines[0])  # /**
                            
                            # Ajouter la description
                            nouvelles_lignes_javadoc.append(f"     * {description}\n")
                            nouvelles_lignes_javadoc.append("     *\n")
                            
                            # Ajouter les tags existants
                            for jline in javadoc_lines[1:]:
                                stripped = jline.strip()
                                if stripped.startswith("* @") or stripped == "*/":
                                    nouvelles_lignes_javadoc.append(jline)
                            
                            # Remplacer dans nouvelles_lignes
                            # Retirer les anciennes lignes Javadoc
                            for _ in range(len(javadoc_lines)):
                                nouvelles_lignes.pop()
                            
                            # Ajouter les nouvelles
                            nouvelles_lignes.extend(nouvelles_lignes_javadoc)
                            
                            if not dry_run:
                                print(f"   ✓ Ligne {javadoc_start + 1}: {'Setter' if is_setter else 'Getter'} {nom_propriete}")
                            else:
                                print(f"   → Ligne {javadoc_start + 1}: {'Setter' if is_setter else 'Getter'} {nom_propriete}")
                                print(f"      Description à ajouter: {description}")
            
            continue
        
        i += 1
    
    # Écrire le fichier si ce n'est pas un dry run
    if not dry_run and modifications > 0:
        with open(fichier_path, 'w', encoding='utf-8') as f:
            f.writelines(nouvelles_lignes)
    
    return modifications

def main():
    """Fonction principale"""
    import argparse
    
    parser = argparse.ArgumentParser(description="Complète les Javadoc des getters/setters")
    parser.add_argument("fichier", help="Fichier Java à traiter")
    parser.add_argument("--apply", action="store_true", help="Appliquer les modifications (sinon mode dry-run)")
    
    args = parser.parse_args()
    
    fichier_path = Path(args.fichier)
    
    if not fichier_path.exists():
        print(f"❌ Fichier non trouvé: {fichier_path}")
        return 1
    
    print(f"🔍 Analyse de {fichier_path.name}...")
    print()
    
    if not args.apply:
        print("📋 Mode DRY-RUN (aperçu seulement)")
        print("   Utilisez --apply pour appliquer les modifications")
        print()
    
    modifications = completer_javadoc_fichier(str(fichier_path), dry_run=not args.apply)
    
    print()
    if args.apply:
        print(f"✅ {modifications} getters/setters complétés")
    else:
        print(f"📊 {modifications} getters/setters à compléter")
        print()
        print("Pour appliquer les modifications:")
        print(f"   python3 {sys.argv[0]} {fichier_path} --apply")
    
    return 0

if __name__ == "__main__":
    sys.exit(main())
