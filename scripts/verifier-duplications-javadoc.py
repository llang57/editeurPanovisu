#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script pour détecter les duplications de documentation Javadoc
"""

import os
import re

def detecter_duplications(fichier):
    """
    Détecte les Javadoc en double dans un fichier
    Ne compte que les Javadoc immédiatement avant une déclaration
    """
    with open(fichier, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    duplications = []
    i = 0
    
    while i < len(lignes):
        ligne = lignes[i]
        
        # Détecter le début d'un Javadoc (vraie doc, pas commentaire)
        if ligne.strip().startswith('/**'):
            debut_javadoc = i
            
            # Trouver la fin du premier Javadoc
            j = i + 1
            while j < len(lignes) and '*/' not in lignes[j]:
                j += 1
            if j < len(lignes):
                j += 1  # Passer la ligne */
            
            # Maintenant compter les Javadoc consécutifs jusqu'à la déclaration
            nb_javadocs = 1
            
            while j < len(lignes):
                ligne_j = lignes[j].strip()
                
                # Si ligne vide ou annotation, continuer
                if ligne_j == '' or ligne_j.startswith('@'):
                    j += 1
                    continue
                
                # Si on trouve un autre Javadoc
                if ligne_j.startswith('/**'):
                    nb_javadocs += 1
                    # Sauter ce Javadoc
                    j += 1
                    while j < len(lignes) and '*/' not in lignes[j]:
                        j += 1
                    if j < len(lignes):
                        j += 1
                    continue
                
                # Si on trouve une déclaration (méthode, classe, etc.)
                if any(keyword in ligne_j for keyword in ['public ', 'protected ', 'private ', 'class ', 'interface ', 'enum ']):
                    # Extraire le nom de la méthode
                    nom_methode = "inconnue"
                    match = re.search(r'(public|protected|private).*?\s+(\w+)\s*\(', lignes[j])
                    if match:
                        nom_methode = match.group(2)
                    else:
                        # Peut-être une classe
                        match = re.search(r'(class|interface|enum)\s+(\w+)', lignes[j])
                        if match:
                            nom_methode = match.group(2)
                    
                    if nb_javadocs > 1:
                        duplications.append({
                            'ligne': debut_javadoc + 1,
                            'nombre': nb_javadocs,
                            'methode': nom_methode
                        })
                    break
                
                # Autre chose, on arrête de chercher
                break
            
            i = j if j < len(lignes) else i + 1
        else:
            i += 1
    
    return duplications

def main():
    print("="*70)
    print("  VÉRIFICATION DES DUPLICATIONS DE JAVADOC")
    print("="*70)
    print()
    
    repertoire = "src/editeurpanovisu"
    fichiers_problematiques = []
    total_duplications = 0
    
    for root, dirs, files in os.walk(repertoire):
        for file in files:
            if file.endswith('.java'):
                chemin = os.path.join(root, file)
                duplications = detecter_duplications(chemin)
                
                if duplications:
                    fichiers_problematiques.append((chemin, duplications))
                    total_duplications += len(duplications)
    
    if fichiers_problematiques:
        print("⚠️  DUPLICATIONS DÉTECTÉES !\n")
        
        for fichier, duplications in fichiers_problematiques:
            print(f"📄 {fichier}")
            for dup in duplications:
                print(f"   Ligne {dup['ligne']}: méthode '{dup['methode']}()' - {dup['nombre']} Javadoc consécutifs")
            print()
        
        print("="*70)
        print(f"❌ Total : {total_duplications} duplications dans {len(fichiers_problematiques)} fichiers")
        print("="*70)
    else:
        print("✅ Aucune duplication détectée !")
        print("="*70)

if __name__ == "__main__":
    main()
