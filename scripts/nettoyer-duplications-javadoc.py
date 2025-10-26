#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script pour nettoyer automatiquement les duplications de documentation Javadoc
Ne garde que la première occurrence (souvent la plus détaillée)
"""

import os
import re

def nettoyer_duplications(fichier):
    """
    Supprime les Javadoc en double, ne garde que le premier
    """
    with open(fichier, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    lignes_nettoyees = []
    i = 0
    corrections = 0
    
    while i < len(lignes):
        ligne = lignes[i]
        
        # Détecter le début d'un Javadoc
        if '/**' in ligne and not ligne.strip().startswith('*'):
            debut_javadoc = i
            
            # Capturer le premier Javadoc complet
            premier_javadoc = []
            j = i
            while j < len(lignes):
                premier_javadoc.append(lignes[j])
                if '*/' in lignes[j]:
                    j += 1
                    break
                j += 1
            
            # Ajouter le premier Javadoc
            lignes_nettoyees.extend(premier_javadoc)
            
            # Vérifier s'il y a des Javadoc supplémentaires avant la méthode
            duplications_trouvees = 0
            while j < len(lignes):
                ligne_j = lignes[j]
                
                # Si on trouve un autre Javadoc consécutif
                if '/**' in ligne_j and not ligne_j.strip().startswith('*'):
                    duplications_trouvees += 1
                    # Sauter ce Javadoc (ne pas l'ajouter)
                    while j < len(lignes) and '*/' not in lignes[j]:
                        j += 1
                    if j < len(lignes):
                        j += 1  # Sauter la ligne */
                    continue
                    
                # Si on trouve une annotation (@Override, @SuppressWarnings, etc.)
                elif ligne_j.strip().startswith('@'):
                    lignes_nettoyees.append(ligne_j)
                    j += 1
                    continue
                    
                # Si on trouve la déclaration de méthode/classe
                elif any(keyword in ligne_j for keyword in ['public ', 'protected ', 'private ', 'class ', 'interface ', 'enum ']):
                    lignes_nettoyees.append(ligne_j)
                    j += 1
                    break
                    
                # Ligne vide ou commentaire simple
                elif ligne_j.strip() == '' or ligne_j.strip().startswith('//'):
                    lignes_nettoyees.append(ligne_j)
                    j += 1
                    continue
                    
                else:
                    # Autre ligne, on l'ajoute et on sort
                    lignes_nettoyees.append(ligne_j)
                    j += 1
                    break
            
            if duplications_trouvees > 0:
                corrections += duplications_trouvees
            
            i = j
        else:
            lignes_nettoyees.append(ligne)
            i += 1
    
    # Sauvegarder si des modifications ont été faites
    if corrections > 0:
        with open(fichier, 'w', encoding='utf-8') as f:
            f.writelines(lignes_nettoyees)
    
    return corrections

def main():
    print("="*70)
    print("  NETTOYAGE DES DUPLICATIONS DE JAVADOC")
    print("="*70)
    print()
    
    repertoire = "src/editeurpanovisu"
    total_corrections = 0
    fichiers_corriges = 0
    
    for root, dirs, files in os.walk(repertoire):
        for file in files:
            if file.endswith('.java'):
                chemin = os.path.join(root, file)
                corrections = nettoyer_duplications(chemin)
                
                if corrections > 0:
                    fichiers_corriges += 1
                    total_corrections += corrections
                    print(f"✅ {chemin}: {corrections} duplication(s) supprimée(s)")
    
    print()
    print("="*70)
    if total_corrections > 0:
        print(f"✅ Nettoyage terminé : {total_corrections} duplications supprimées")
        print(f"   dans {fichiers_corriges} fichiers")
    else:
        print("✅ Aucune duplication à nettoyer")
    print("="*70)

if __name__ == "__main__":
    main()
