#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script pour générer des templates de documentation Javadoc
pour les méthodes sans documentation
"""

import re
import os
from pathlib import Path

def analyser_methode_sans_doc(chemin_fichier):
    """
    Trouve toutes les méthodes publiques/protected sans documentation
    
    Returns:
        Liste de dictionnaires avec détails des méthodes
    """
    with open(chemin_fichier, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    methodes_sans_doc = []
    i = 0
    
    while i < len(lignes):
        ligne = lignes[i].strip()
        
        # Vérifier si c'est une déclaration de méthode public/protected
        if (ligne.startswith('public ') or ligne.startswith('protected ')) and '(' in ligne:
            # Vérifier qu'il n'y a pas de javadoc juste avant
            a_javadoc = False
            j = i - 1
            while j >= 0 and j >= i - 5:  # Regarder les 5 lignes précédentes
                ligne_prec = lignes[j].strip()
                if ligne_prec.startswith('/**') or '*/' in ligne_prec:
                    a_javadoc = True
                    break
                if ligne_prec and not ligne_prec.startswith('*') and not ligne_prec.startswith('@'):
                    break
                j -= 1
            
            # Si pas de javadoc et pas @Override
            if not a_javadoc and i > 0 and '@Override' not in lignes[i-1]:
                # Parser la signature
                signature = ligne
                ligne_suivante = i + 1
                while ligne_suivante < len(lignes) and ')' not in signature:
                    signature += ' ' + lignes[ligne_suivante].strip()
                    ligne_suivante += 1
                
                # Extraire les composants
                match = re.match(r'(public|protected)\s+(?:(static|final|synchronized)\s+)*([\w<>\[\]]+)\s+(\w+)\s*\(([^)]*)\)', signature)
                if match:
                    modifiers = match.group(1)
                    if match.group(2):
                        modifiers += ' ' + match.group(2)
                    type_retour = match.group(3)
                    nom_methode = match.group(4)
                    parametres_str = match.group(5)
                    
                    # Ignorer les constructeurs
                    if type_retour == nom_methode:
                        i += 1
                        continue
                    
                    # Parser les paramètres
                    params_list = []
                    if parametres_str.strip():
                        for param in parametres_str.split(','):
                            param = param.strip()
                            if param:
                                parts = param.split()
                                if len(parts) >= 2:
                                    param_type = ' '.join(parts[:-1])
                                    param_name = parts[-1]
                                    params_list.append({
                                        'type': param_type,
                                        'name': param_name
                                    })
                    
                    info_methode = {
                        'fichier': chemin_fichier,
                        'ligne': i + 1,
                        'annotations': '',
                        'modifiers': modifiers.strip(),
                        'type_retour': type_retour,
                        'nom': nom_methode,
                        'parametres': params_list,
                        'signature_complete': signature
                    }
                    
                    methodes_sans_doc.append(info_methode)
        
        i += 1
        
        i += 1
    
    return methodes_sans_doc


def generer_template_javadoc(methode):
    """
    Génère un template de documentation Javadoc pour une méthode
    
    Args:
        methode: Dictionnaire contenant les infos de la méthode
        
    Returns:
        String contenant le template Javadoc
    """
    template = "    /**\n"
    template += f"     * [TODO] Description de la méthode {methode['nom']}\n"
    template += "     * \n"
    
    # Description détaillée
    template += "     * <p>[TODO] Ajouter une description détaillée du rôle de cette méthode.</p>\n"
    template += "     * \n"
    
    # Tags @param
    if methode['parametres']:
        for param in methode['parametres']:
            template += f"     * @param {param['name']} [TODO] Description du paramètre\n"
    
    # Tag @return
    if methode['type_retour'] != 'void':
        template += f"     * @return [TODO] Description de la valeur retournée\n"
    
    template += "     */\n"
    
    return template


def generer_rapport_prioritaire(repertoire_source):
    """
    Génère un rapport des méthodes sans doc par ordre de priorité
    """
    fichiers_java = []
    for root, dirs, files in os.walk(repertoire_source):
        for file in files:
            if file.endswith('.java'):
                fichiers_java.append(os.path.join(root, file))
    
    toutes_methodes = []
    
    print("🔍 Recherche des méthodes sans documentation...\n")
    
    for fichier in sorted(fichiers_java):
        methodes = analyser_methode_sans_doc(fichier)
        if methodes:
            toutes_methodes.extend(methodes)
    
    # Trier par fichier puis par ligne
    toutes_methodes.sort(key=lambda m: (m['fichier'], m['ligne']))
    
    # Générer le rapport
    print("="*70)
    print("  MÉTHODES SANS DOCUMENTATION JAVADOC")
    print("="*70)
    print(f"\nTotal : {len(toutes_methodes)} méthodes\n")
    
    fichier_actuel = None
    compteur = 0
    
    for methode in toutes_methodes:
        if methode['fichier'] != fichier_actuel:
            fichier_actuel = methode['fichier']
            fichier_relatif = fichier_actuel.replace('src/', '')
            print(f"\n{'='*70}")
            print(f"📄 {fichier_relatif}")
            print(f"{'='*70}\n")
        
        compteur += 1
        print(f"{compteur}. Ligne {methode['ligne']}: {methode['nom']}()")
        print(f"   Signature: {methode['signature_complete'].strip()}")
        
        if methode['annotations']:
            print(f"   Annotations: {methode['annotations']}")
        
        # Afficher le template
        print("\n   Template Javadoc suggéré:")
        template = generer_template_javadoc(methode)
        for ligne in template.split('\n'):
            print(f"   {ligne}")
        print()
    
    # Sauvegarder dans un fichier
    fichier_sortie = "doc/METHODES_SANS_JAVADOC.md"
    with open(fichier_sortie, 'w', encoding='utf-8') as f:
        f.write("# Méthodes sans documentation Javadoc\n\n")
        f.write(f"**Date :** {os.popen('date').read().strip()}\n\n")
        f.write(f"**Total :** {len(toutes_methodes)} méthodes publiques/protected sans documentation\n\n")
        
        f.write("## Liste des méthodes\n\n")
        
        fichier_actuel = None
        for i, methode in enumerate(toutes_methodes, 1):
            if methode['fichier'] != fichier_actuel:
                fichier_actuel = methode['fichier']
                fichier_relatif = fichier_actuel.replace('src/', '')
                f.write(f"\n### {fichier_relatif}\n\n")
            
            f.write(f"#### {i}. `{methode['nom']}()` - Ligne {methode['ligne']}\n\n")
            f.write(f"**Signature :**\n```java\n{methode['signature_complete'].strip()}\n```\n\n")
            
            if methode['parametres']:
                f.write("**Paramètres :**\n")
                for param in methode['parametres']:
                    f.write(f"- `{param['name']}` ({param['type']})\n")
                f.write("\n")
            
            if methode['type_retour'] != 'void':
                f.write(f"**Retourne :** `{methode['type_retour']}`\n\n")
            
            f.write("**Template Javadoc :**\n```java\n")
            template = generer_template_javadoc(methode)
            f.write(template)
            f.write("```\n\n")
            f.write("---\n\n")
    
    print("\n" + "="*70)
    print(f"✅ Rapport sauvegardé : {fichier_sortie}")
    print("="*70)


def main():
    """Fonction principale"""
    repertoire_source = "src/editeurpanovisu"
    
    if not os.path.exists(repertoire_source):
        print(f"❌ Répertoire non trouvé : {repertoire_source}")
        return
    
    generer_rapport_prioritaire(repertoire_source)


if __name__ == "__main__":
    main()
