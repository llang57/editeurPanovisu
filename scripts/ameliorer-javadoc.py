#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script d'analyse et d'amélioration de la documentation Javadoc
Vérifie la présence et la qualité de la documentation dans les fichiers Java
"""

import re
import os
from pathlib import Path

def analyser_methodes_java(chemin_fichier):
    """
    Analyse un fichier Java et identifie les méthodes sans documentation ou avec documentation incomplète
    
    Args:
        chemin_fichier: Chemin vers le fichier Java à analyser
        
    Returns:
        Tuple (methodes_sans_doc, methodes_doc_incomplete)
    """
    with open(chemin_fichier, 'r', encoding='utf-8') as f:
        contenu = f.read()
    
    methodes_sans_doc = []
    methodes_doc_incomplete = []
    
    # Pattern pour détecter les méthodes
    pattern_methode = re.compile(
        r'(?P<javadoc>/\*\*.*?\*/\s*)?'
        r'(?P<modifiers>(?:public|private|protected|static|final|synchronized|native|abstract)\s+)*'
        r'(?P<type>[\w<>\[\]]+)\s+'
        r'(?P<nom>\w+)\s*\('
        r'(?P<params>[^)]*)\)',
        re.MULTILINE | re.DOTALL
    )
    
    for match in pattern_methode.finditer(contenu):
        javadoc = match.group('javadoc')
        modifiers = match.group('modifiers') or ''
        type_retour = match.group('type')
        nom_methode = match.group('nom')
        parametres = match.group('params')
        
        # Ignorer les constructeurs
        if type_retour == nom_methode:
            continue
        
        # Ignorer les méthodes Override de classes anonymes internes
        if '@Override' in (javadoc or ''):
            continue
            
        # Vérifier si c'est une méthode publique ou protected
        if 'public' in modifiers or 'protected' in modifiers:
            # Compter les paramètres
            nb_params = 0
            if parametres.strip():
                nb_params = len([p for p in parametres.split(',') if p.strip()])
            
            info_methode = {
                'nom': nom_methode,
                'type_retour': type_retour,
                'parametres': parametres.strip(),
                'nb_params': nb_params,
                'modifiers': modifiers.strip(),
                'ligne': contenu[:match.start()].count('\n') + 1,
                'a_retour': type_retour != 'void'
            }
            
            if not javadoc:
                methodes_sans_doc.append(info_methode)
            else:
                # Analyser la qualité de la documentation
                problemes = []
                
                # Vérifier la présence des tags @param
                nb_params_doc = len(re.findall(r'@param\s+\w+', javadoc))
                if nb_params > 0 and nb_params_doc < nb_params:
                    problemes.append(f"Manque {nb_params - nb_params_doc} tag(s) @param (sur {nb_params})")
                
                # Vérifier la présence du tag @return
                if info_methode['a_retour'] and '@return' not in javadoc:
                    problemes.append("Manque tag @return")
                
                # Vérifier si la description est trop courte (moins de 20 caractères)
                desc = re.sub(r'/\*\*|\*/|@\w+.*|\*', '', javadoc).strip()
                if len(desc) < 20:
                    problemes.append("Description trop courte")
                
                # Vérifier la présence d'exemples pour les méthodes complexes
                if nb_params >= 3 and '@code' not in javadoc and 'exemple' not in javadoc.lower():
                    problemes.append("Pas d'exemple d'utilisation (recommandé)")
                
                if problemes:
                    info_methode['javadoc'] = javadoc
                    info_methode['problemes'] = problemes
                    methodes_doc_incomplete.append(info_methode)
    
    return methodes_sans_doc, methodes_doc_incomplete


def generer_rapport(fichier_java):
    """Génère un rapport d'analyse de la documentation"""
    
    print("="*70)
    print(f"  ANALYSE QUALITÉ JAVADOC - {os.path.basename(fichier_java)}")
    print("="*70)
    print()
    
    sans_doc, doc_incomplete = analyser_methodes_java(fichier_java)
    
    print(f"📊 Statistiques :")
    print(f"   - Méthodes publiques/protected SANS documentation : {len(sans_doc)}")
    print(f"   - Méthodes avec documentation INCOMPLÈTE : {len(doc_incomplete)}")
    print()
    
    if sans_doc:
        print("❌ Méthodes SANS documentation Javadoc :")
        print("-" * 70)
        for i, methode in enumerate(sans_doc[:20], 1):
            print(f"{i}. Ligne {methode['ligne']}: {methode['nom']}()")
            print(f"   Signature: {methode['modifiers']} {methode['type_retour']} {methode['nom']}({methode['parametres']})")
            if methode['nb_params'] > 0:
                print(f"   Paramètres: {methode['nb_params']}")
            if methode['a_retour']:
                print(f"   Retourne: {methode['type_retour']}")
            print()
        if len(sans_doc) > 20:
            print(f"   ... et {len(sans_doc) - 20} autres")
        print()
    
    if doc_incomplete:
        print("⚠️  Méthodes avec documentation INCOMPLÈTE (à améliorer) :")
        print("-" * 70)
        for i, methode in enumerate(doc_incomplete[:20], 1):
            print(f"{i}. Ligne {methode['ligne']}: {methode['nom']}()")
            print(f"   Problèmes identifiés :")
            for prob in methode['problemes']:
                print(f"      • {prob}")
            print()
        if len(doc_incomplete) > 20:
            print(f"   ... et {len(doc_incomplete) - 20} autres")
        print()
    
    # Recommandations
    print("💡 Critères d'une BONNE documentation Javadoc :")
    print("-" * 70)
    print("   1. Description détaillée du rôle de la méthode")
    print("      → Expliquer QUOI fait la méthode et POURQUOI")
    print()
    print("   2. Tag @param pour CHAQUE paramètre")
    print("      → @param nomParam Description du paramètre")
    print()
    print("   3. Tag @return si la méthode retourne une valeur")
    print("      → @return Description de la valeur retournée")
    print()
    print("   4. Tag @throws pour les exceptions levées")
    print("      → @throws IOException Si le fichier n'est pas accessible")
    print()
    print("   5. Exemples d'utilisation pour les méthodes complexes")
    print("      → {@code exemple de code}")
    print()
    print("   6. Références croisées si nécessaire")
    print("      → @see ClasseAssociée#methodeAssociée")
    print()
    
    # Score global
    total_methodes = len(sans_doc) + len(doc_incomplete)
    if total_methodes == 0:
        print("✅ EXCELLENT ! Toutes les méthodes publiques sont bien documentées !")
    else:
        print(f"📈 Score de documentation : {total_methodes} méthode(s) à améliorer")
    
    print("="*70)


def trouver_fichiers_java(repertoire_source):
    """
    Trouve tous les fichiers Java dans le répertoire source
    
    Args:
        repertoire_source: Répertoire racine à parcourir
        
    Returns:
        Liste des chemins vers les fichiers .java
    """
    fichiers_java = []
    for root, dirs, files in os.walk(repertoire_source):
        for file in files:
            if file.endswith('.java'):
                fichiers_java.append(os.path.join(root, file))
    return sorted(fichiers_java)


def generer_rapport_markdown(fichiers_java, fichier_sortie):
    """
    Génère un rapport Markdown complet de l'analyse de documentation
    
    Args:
        fichiers_java: Liste des fichiers Java à analyser
        fichier_sortie: Chemin du fichier Markdown de sortie
    """
    
    resultats_globaux = {
        'total_fichiers': 0,
        'total_sans_doc': 0,
        'total_doc_incomplete': 0,
        'details_par_fichier': []
    }
    
    print("🔍 Analyse de tous les fichiers Java du projet...")
    print()
    
    for fichier in fichiers_java:
        print(f"   Analyse de {os.path.basename(fichier)}...")
        
        sans_doc, doc_incomplete = analyser_methodes_java(fichier)
        
        if sans_doc or doc_incomplete:
            resultats_globaux['details_par_fichier'].append({
                'fichier': fichier,
                'sans_doc': sans_doc,
                'doc_incomplete': doc_incomplete
            })
        
        resultats_globaux['total_fichiers'] += 1
        resultats_globaux['total_sans_doc'] += len(sans_doc)
        resultats_globaux['total_doc_incomplete'] += len(doc_incomplete)
    
    # Générer le fichier Markdown
    with open(fichier_sortie, 'w', encoding='utf-8') as f:
        f.write("# État des lieux de la documentation Javadoc\n\n")
        f.write(f"**Date de génération :** {Path('.').absolute().name} - {os.popen('date').read().strip()}\n\n")
        
        # Statistiques globales
        f.write("## 📊 Statistiques globales\n\n")
        f.write(f"- **Fichiers Java analysés :** {resultats_globaux['total_fichiers']}\n")
        f.write(f"- **Méthodes SANS documentation :** {resultats_globaux['total_sans_doc']}\n")
        f.write(f"- **Méthodes avec documentation INCOMPLÈTE :** {resultats_globaux['total_doc_incomplete']}\n")
        
        total_problemes = resultats_globaux['total_sans_doc'] + resultats_globaux['total_doc_incomplete']
        f.write(f"- **Total de méthodes à améliorer :** {total_problemes}\n\n")
        
        # Score de qualité
        if total_problemes == 0:
            f.write("### ✅ Qualité : EXCELLENTE\n\n")
            f.write("Toutes les méthodes publiques et protected sont correctement documentées !\n\n")
        elif total_problemes < 10:
            f.write("### 🟢 Qualité : TRÈS BONNE\n\n")
            f.write("Seulement quelques méthodes nécessitent une amélioration.\n\n")
        elif total_problemes < 50:
            f.write("### 🟡 Qualité : BONNE\n\n")
            f.write("La documentation est globalement satisfaisante mais peut être améliorée.\n\n")
        else:
            f.write("### 🟠 Qualité : À AMÉLIORER\n\n")
            f.write("Plusieurs méthodes nécessitent une documentation plus complète.\n\n")
        
        # Détails par fichier
        f.write("## 📁 Détails par fichier\n\n")
        
        if not resultats_globaux['details_par_fichier']:
            f.write("✅ **Tous les fichiers ont une documentation complète !**\n\n")
        else:
            for detail in resultats_globaux['details_par_fichier']:
                fichier_relatif = detail['fichier'].replace('src/', '')
                nb_sans = len(detail['sans_doc'])
                nb_incomplete = len(detail['doc_incomplete'])
                
                f.write(f"### {fichier_relatif}\n\n")
                f.write(f"- Méthodes sans documentation : **{nb_sans}**\n")
                f.write(f"- Méthodes avec documentation incomplète : **{nb_incomplete}**\n\n")
                
                # Méthodes sans documentation
                if detail['sans_doc']:
                    f.write("#### ❌ Méthodes SANS documentation\n\n")
                    f.write("| Ligne | Méthode | Signature |\n")
                    f.write("|-------|---------|----------|\n")
                    for methode in detail['sans_doc'][:50]:  # Limiter à 50
                        signature = f"{methode['type_retour']} {methode['nom']}({methode['parametres'][:50]}...)" if len(methode['parametres']) > 50 else f"{methode['type_retour']} {methode['nom']}({methode['parametres']})"
                        f.write(f"| {methode['ligne']} | `{methode['nom']}()` | `{signature}` |\n")
                    
                    if len(detail['sans_doc']) > 50:
                        f.write(f"\n*... et {len(detail['sans_doc']) - 50} autres méthodes*\n")
                    f.write("\n")
                
                # Méthodes avec documentation incomplète
                if detail['doc_incomplete']:
                    f.write("#### ⚠️ Méthodes avec documentation INCOMPLÈTE\n\n")
                    f.write("| Ligne | Méthode | Problèmes |\n")
                    f.write("|-------|---------|----------|\n")
                    for methode in detail['doc_incomplete'][:50]:
                        problemes = "<br>".join([f"• {p}" for p in methode['problemes']])
                        f.write(f"| {methode['ligne']} | `{methode['nom']}()` | {problemes} |\n")
                    
                    if len(detail['doc_incomplete']) > 50:
                        f.write(f"\n*... et {len(detail['doc_incomplete']) - 50} autres méthodes*\n")
                    f.write("\n")
        
        # Recommandations
        f.write("## 💡 Recommandations\n\n")
        f.write("### Critères d'une bonne documentation Javadoc\n\n")
        f.write("1. **Description détaillée**\n")
        f.write("   - Expliquer QUOI fait la méthode et POURQUOI\n")
        f.write("   - Contexte d'utilisation\n\n")
        
        f.write("2. **Tag @param pour CHAQUE paramètre**\n")
        f.write("   ```java\n")
        f.write("   @param nomParam Description du paramètre et son rôle\n")
        f.write("   ```\n\n")
        
        f.write("3. **Tag @return si la méthode retourne une valeur**\n")
        f.write("   ```java\n")
        f.write("   @return Description de la valeur retournée\n")
        f.write("   ```\n\n")
        
        f.write("4. **Tag @throws pour les exceptions**\n")
        f.write("   ```java\n")
        f.write("   @throws IOException Si le fichier n'est pas accessible\n")
        f.write("   ```\n\n")
        
        f.write("5. **Exemples d'utilisation** (pour méthodes complexes)\n")
        f.write("   ```java\n")
        f.write("   /**\n")
        f.write("    * <p><b>Exemple :</b></p>\n")
        f.write("    * <pre>{@code\n")
        f.write("    * MonObjet obj = new MonObjet();\n")
        f.write("    * obj.maMethode(param1, param2);\n")
        f.write("    * }</pre>\n")
        f.write("    */\n")
        f.write("   ```\n\n")
        
        f.write("6. **Références croisées**\n")
        f.write("   ```java\n")
        f.write("   @see ClasseAssociée#methodeAssociée\n")
        f.write("   ```\n\n")
        
        # Ressources
        f.write("## 📚 Ressources\n\n")
        f.write("- [Guide officiel Javadoc d'Oracle](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)\n")
        f.write("- [Bonnes pratiques Javadoc](https://www.baeldung.com/javadoc)\n")
        f.write("- [Comment écrire des commentaires Javadoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html)\n\n")
    
    print(f"\n✅ Rapport généré : {fichier_sortie}")


def main():
    """Fonction principale"""
    
    # Trouver tous les fichiers Java
    repertoire_source = "src/editeurpanovisu"
    
    if not os.path.exists(repertoire_source):
        print(f"❌ Répertoire non trouvé : {repertoire_source}")
        return
    
    fichiers_java = trouver_fichiers_java(repertoire_source)
    
    if not fichiers_java:
        print(f"❌ Aucun fichier Java trouvé dans {repertoire_source}")
        return
    
    print("="*70)
    print("  ANALYSE COMPLÈTE DE LA DOCUMENTATION JAVADOC")
    print("="*70)
    print()
    print(f"📂 Répertoire analysé : {repertoire_source}")
    print(f"📄 Fichiers Java trouvés : {len(fichiers_java)}")
    print()
    
    # Générer le rapport Markdown
    fichier_sortie = "doc/ETAT_LIEUX_JAVADOC.md"
    generer_rapport_markdown(fichiers_java, fichier_sortie)
    
    print()
    print("="*70)
    print("✅ Analyse terminée !")
    print(f"📝 Consultez le rapport : {fichier_sortie}")
    print("="*70)


if __name__ == "__main__":
    main()
