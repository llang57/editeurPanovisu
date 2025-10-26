#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script pour détecter les Javadoc incomplets (sans description)
"""

import os
import re

def detecter_javadoc_incomplets(fichier):
    """
    Détecte les Javadoc sans description (seulement @param, @return, etc.)
    """
    with open(fichier, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    incomplets = []
    i = 0
    
    while i < len(lignes):
        ligne = lignes[i]
        
        # Détecter le début d'un Javadoc
        if ligne.strip().startswith('/**'):
            debut_javadoc = i
            contenu_javadoc = []
            
            # Capturer tout le Javadoc
            j = i
            while j < len(lignes):
                contenu_javadoc.append(lignes[j])
                if '*/' in lignes[j]:
                    j += 1
                    break
                j += 1
            
            # Vérifier si c'est un Javadoc de méthode (suivi d'une déclaration)
            while j < len(lignes):
                ligne_j = lignes[j].strip()
                
                # Sauter lignes vides et annotations
                if ligne_j == '' or ligne_j.startswith('@'):
                    j += 1
                    continue
                
                # Si on trouve une déclaration de méthode
                if any(keyword in ligne_j for keyword in ['public ', 'protected ', 'private ']):
                    if '(' in ligne_j:  # C'est une méthode
                        # Extraire le nom de la méthode
                        nom_methode = "inconnue"
                        match = re.search(r'(public|protected|private).*?\s+(\w+)\s*\(', lignes[j])
                        if match:
                            nom_methode = match.group(2)
                        
                        # Analyser le contenu du Javadoc
                        a_description = False
                        a_tags = False
                        
                        for ligne_doc in contenu_javadoc:
                            ligne_clean = ligne_doc.strip()
                            
                            # Ignorer les lignes de délimiteurs
                            if ligne_clean in ['/**', '*/'] or ligne_clean == '*':
                                continue
                            
                            # Si la ligne commence par @, c'est un tag
                            if ligne_clean.startswith('* @'):
                                a_tags = True
                            # Sinon c'est du texte de description
                            elif ligne_clean.startswith('*'):
                                texte = ligne_clean[1:].strip()
                                if texte and not texte.startswith('@'):
                                    a_description = True
                        
                        # Si a des tags mais pas de description, c'est incomplet
                        if a_tags and not a_description:
                            incomplets.append({
                                'ligne': debut_javadoc + 1,
                                'methode': nom_methode,
                                'javadoc': ''.join(contenu_javadoc)
                            })
                    
                    break
                
                # Autre chose, on arrête
                break
            
            i = j if j < len(lignes) else i + 1
        else:
            i += 1
    
    return incomplets

def main():
    print("="*70)
    print("  DÉTECTION DES JAVADOC INCOMPLETS")
    print("="*70)
    print()
    
    repertoire = "src/editeurpanovisu"
    total_incomplets = 0
    fichiers_problematiques = []
    
    for root, dirs, files in os.walk(repertoire):
        for file in files:
            if file.endswith('.java'):
                chemin = os.path.join(root, file)
                incomplets = detecter_javadoc_incomplets(chemin)
                
                if incomplets:
                    fichiers_problematiques.append((chemin, incomplets))
                    total_incomplets += len(incomplets)
    
    if fichiers_problematiques:
        print("⚠️  JAVADOC INCOMPLETS DÉTECTÉS !\n")
        
        for fichier, incomplets in fichiers_problematiques:
            print(f"📄 {fichier}")
            for item in incomplets:
                print(f"   Ligne {item['ligne']}: {item['methode']}()")
                print(f"   {item['javadoc']}")
            print()
        
        print("="*70)
        print(f"⚠️  Total : {total_incomplets} Javadoc incomplets dans {len(fichiers_problematiques)} fichiers")
        print("="*70)
    else:
        print("✅ Aucun Javadoc incomplet détecté !")
        print("="*70)

if __name__ == "__main__":
    main()
