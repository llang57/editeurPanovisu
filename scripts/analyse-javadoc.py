#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Analyse la documentation Javadoc du projet EditeurPanovisu

Ce script parcourt tous les fichiers Java du projet et génère un rapport
détaillé sur l'état de la documentation Javadoc.

Auteur: Script généré pour EditeurPanovisu
Date: 2025-10-15
"""

import os
import re
from pathlib import Path
from datetime import datetime
from typing import List, Dict, Tuple

class JavaElement:
    """Représente un élément Java (classe, méthode, propriété)"""
    
    def __init__(self, name: str, element_type: str, line_number: int, 
                 has_javadoc: bool, javadoc_lines: int = 0, signature: str = "",
                 javadoc_content: str = "", javadoc_quality: str = "none"):
        self.name = name
        self.element_type = element_type  # 'class', 'method', 'field'
        self.line_number = line_number
        self.has_javadoc = has_javadoc
        self.javadoc_lines = javadoc_lines
        self.signature = signature
        self.javadoc_content = javadoc_content  # Contenu du Javadoc
        self.javadoc_quality = javadoc_quality  # 'none', 'minimal', 'partial', 'complete'

class JavaClass:
    """Représente une classe Java avec tous ses éléments"""
    
    def __init__(self, name: str, file_path: str):
        self.name = name
        self.file_path = file_path
        self.has_javadoc = False
        self.javadoc_lines = 0
        self.fields: List[JavaElement] = []
        self.methods: List[JavaElement] = []
        self.inner_classes: List[str] = []

def find_java_files(src_dir: str) -> List[Path]:
    """Trouve tous les fichiers .java dans le répertoire source"""
    src_path = Path(src_dir)
    return list(src_path.rglob("*.java"))

def extract_javadoc(lines: List[str], start_index: int) -> Tuple[bool, int, str, str]:
    """
    Vérifie s'il y a un commentaire Javadoc avant l'élément et analyse sa qualité
    
    Retourne (has_javadoc, nb_lines, content, quality)
    quality: 'none', 'minimal', 'partial', 'complete'
    """
    # Remonter pour chercher le Javadoc
    i = start_index - 1
    javadoc_lines_list = []
    
    while i >= 0:
        line = lines[i].strip()
        
        # Si ligne vide ou commentaire simple, continuer
        if not line or line.startswith("//"):
            i -= 1
            continue
        
        # Fin du Javadoc (début dans le sens de la lecture)
        if line.startswith("/**"):
            # Vérifier que ce n'est pas un commentaire vide /** */
            if line.endswith("*/") and len(line.strip()) <= 5:
                return False, 0, "", "none"
            
            javadoc_lines_list.append(line)
            javadoc_lines_list.reverse()  # Remettre dans l'ordre
            
            # Extraire le contenu et analyser la qualité
            content = "\n".join(javadoc_lines_list)
            quality = analyze_javadoc_quality(content)
            
            return True, len(javadoc_lines_list), content, quality
        
        # Ligne de Javadoc
        if line.startswith("*"):
            javadoc_lines_list.append(line)
            i -= 1
            continue
        
        # Si on trouve autre chose, pas de Javadoc
        break
        
    return False, 0, "", "none"

def analyze_javadoc_quality(javadoc_content: str) -> str:
    """
    Analyse la qualité d'un commentaire Javadoc
    
    Retourne:
    - 'none': Pas de Javadoc
    - 'minimal': Javadoc présent mais sans tags (@param, @return, etc.)
    - 'partial': Javadoc avec quelques tags mais incomplet
    - 'complete': Javadoc complet avec description et tous les tags nécessaires
    """
    if not javadoc_content:
        return "none"
    
    # Nettoyer le contenu
    content = javadoc_content.lower()
    
    # Détecter les tags
    has_description = len(javadoc_content) > 20  # Au moins une description minimale
    has_param = "@param" in content
    has_return = "@return" in content
    has_throws = "@throws" in content or "@exception" in content
    has_see = "@see" in content
    has_author = "@author" in content
    has_since = "@since" in content
    has_deprecated = "@deprecated" in content
    has_example = "@example" in content or "{@code" in content
    
    # Compter les tags présents
    tags_count = sum([
        has_param, has_return, has_throws, has_see, 
        has_author, has_since, has_deprecated, has_example
    ])
    
    # Évaluer la qualité
    if not has_description:
        return "minimal"
    
    if tags_count == 0:
        return "minimal"  # Juste une description, pas de tags
    
    if tags_count >= 3 or (has_param and has_return):
        return "complete"  # Description + plusieurs tags importants
    
    return "partial"  # Description + quelques tags

def parse_java_file(file_path: Path) -> List[JavaClass]:
    """Parse un fichier Java et extrait les informations"""
    
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            lines = content.split('\n')
    except Exception as e:
        print(f"Erreur lecture {file_path}: {e}")
        return []
    
    classes = []
    current_class = None
    
    # Pattern pour détecter les classes
    class_pattern = re.compile(
        r'^\s*(public|private|protected)?\s*(static)?\s*(abstract)?\s*(final)?\s*'
        r'(class|interface|enum)\s+(\w+)'
    )
    
    # Pattern pour détecter les méthodes
    method_pattern = re.compile(
        r'^\s*(public|private|protected)?\s*(static)?\s*(final)?\s*(synchronized)?\s*'
        r'([\w<>\[\],\s]+)\s+(\w+)\s*\('
    )
    
    # Pattern pour détecter les champs/propriétés
    field_pattern = re.compile(
        r'^\s*(public|private|protected)?\s*(static)?\s*(final)?\s*'
        r'([\w<>\[\],\s]+)\s+(\w+)\s*[=;]'
    )
    
    for i, line in enumerate(lines, 1):
        stripped = line.strip()
        
        # Ignorer les commentaires et lignes vides
        if not stripped or stripped.startswith("//") or stripped.startswith("/*") or stripped.startswith("*"):
            continue
        
        # Détecter une classe
        class_match = class_pattern.match(stripped)
        if class_match:
            class_name = class_match.group(6)
            has_javadoc, javadoc_lines, javadoc_content, javadoc_quality = extract_javadoc(lines, i - 1)
            
            # Si classe interne, l'ajouter à la classe parente
            if current_class and "{" in line:
                current_class.inner_classes.append(class_name)
            else:
                # Nouvelle classe principale
                java_class = JavaClass(class_name, str(file_path))
                java_class.has_javadoc = has_javadoc
                java_class.javadoc_lines = javadoc_lines
                classes.append(java_class)
                current_class = java_class
            continue
        
        if not current_class:
            continue
        
        # Détecter une méthode
        method_match = method_pattern.match(stripped)
        if method_match and "(" in line:
            method_name = method_match.group(6)
            # Ignorer les getters/setters simples (optionnel)
            # if method_name.startswith("get") or method_name.startswith("set"):
            #     continue
            
            has_javadoc, javadoc_lines, javadoc_content, javadoc_quality = extract_javadoc(lines, i - 1)
            
            # Extraire la signature complète
            signature = stripped
            if len(signature) > 80:
                signature = signature[:77] + "..."
            
            method = JavaElement(
                name=method_name,
                element_type="method",
                line_number=i,
                has_javadoc=has_javadoc,
                javadoc_lines=javadoc_lines,
                signature=signature,
                javadoc_content=javadoc_content,
                javadoc_quality=javadoc_quality
            )
            current_class.methods.append(method)
            continue
        
        # Détecter un champ
        field_match = field_pattern.match(stripped)
        if field_match and "(" not in line:  # Éviter les faux positifs avec méthodes
            field_name = field_match.group(5)
            has_javadoc, javadoc_lines, javadoc_content, javadoc_quality = extract_javadoc(lines, i - 1)
            
            signature = stripped
            if len(signature) > 80:
                signature = signature[:77] + "..."
            
            field = JavaElement(
                name=field_name,
                element_type="field",
                line_number=i,
                has_javadoc=has_javadoc,
                javadoc_lines=javadoc_lines,
                signature=signature,
                javadoc_content=javadoc_content,
                javadoc_quality=javadoc_quality
            )
            current_class.fields.append(field)
    
    return classes

def generate_markdown_report(all_classes: List[JavaClass], output_file: str):
    """Génère le rapport Markdown"""
    
    # Calculer les statistiques globales
    total_classes = len(all_classes)
    documented_classes = sum(1 for c in all_classes if c.has_javadoc)
    
    total_methods = sum(len(c.methods) for c in all_classes)
    documented_methods = sum(sum(1 for m in c.methods if m.has_javadoc) for c in all_classes)
    complete_methods = sum(sum(1 for m in c.methods if m.javadoc_quality == "complete") for c in all_classes)
    partial_methods = sum(sum(1 for m in c.methods if m.javadoc_quality == "partial") for c in all_classes)
    minimal_methods = sum(sum(1 for m in c.methods if m.javadoc_quality == "minimal") for c in all_classes)
    
    total_fields = sum(len(c.fields) for c in all_classes)
    documented_fields = sum(sum(1 for f in c.fields if f.has_javadoc) for c in all_classes)
    complete_fields = sum(sum(1 for f in c.fields if f.javadoc_quality == "complete") for c in all_classes)
    partial_fields = sum(sum(1 for f in c.fields if f.javadoc_quality == "partial") for c in all_classes)
    minimal_fields = sum(sum(1 for f in c.fields if f.javadoc_quality == "minimal") for c in all_classes)
    
    # Calculer les pourcentages
    pct_classes = (documented_classes / total_classes * 100) if total_classes > 0 else 0
    pct_methods = (documented_methods / total_methods * 100) if total_methods > 0 else 0
    pct_fields = (documented_fields / total_fields * 100) if total_fields > 0 else 0
    pct_total = ((documented_classes + documented_methods + documented_fields) / 
                 (total_classes + total_methods + total_fields) * 100) if (total_classes + total_methods + total_fields) > 0 else 0
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# 📊 Rapport d'analyse Javadoc - EditeurPanovisu\n\n")
        f.write(f"**Date de génération :** {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}\n\n")
        
        # Statistiques globales
        f.write("## 📈 Statistiques globales\n\n")
        f.write("| Élément | Total | Documentés | Non documentés | % Documentés |\n")
        f.write("|---------|-------|------------|----------------|---------------|\n")
        f.write(f"| **Classes** | {total_classes} | {documented_classes} | {total_classes - documented_classes} | {pct_classes:.1f}% |\n")
        f.write(f"| **Méthodes** | {total_methods} | {documented_methods} | {total_methods - documented_methods} | {pct_methods:.1f}% |\n")
        f.write(f"| **Propriétés** | {total_fields} | {documented_fields} | {total_fields - documented_fields} | {pct_fields:.1f}% |\n")
        f.write(f"| **TOTAL** | {total_classes + total_methods + total_fields} | {documented_classes + documented_methods + documented_fields} | {(total_classes + total_methods + total_fields) - (documented_classes + documented_methods + documented_fields)} | **{pct_total:.1f}%** |\n\n")
        
        # Qualité de la documentation
        f.write("## 🎯 Qualité de la documentation\n\n")
        f.write("### Méthodes\n\n")
        f.write("| Qualité | Nombre | % |\n")
        f.write("|---------|--------|---|\n")
        f.write(f"| 🟢 Complète (tags @param, @return, etc.) | {complete_methods} | {(complete_methods/total_methods*100) if total_methods > 0 else 0:.1f}% |\n")
        f.write(f"| 🟡 Partielle (quelques tags) | {partial_methods} | {(partial_methods/total_methods*100) if total_methods > 0 else 0:.1f}% |\n")
        f.write(f"| 🟠 Minimale (description seulement) | {minimal_methods} | {(minimal_methods/total_methods*100) if total_methods > 0 else 0:.1f}% |\n")
        f.write(f"| ⚫ Absente | {total_methods - documented_methods} | {((total_methods - documented_methods)/total_methods*100) if total_methods > 0 else 0:.1f}% |\n\n")
        
        f.write("### Propriétés\n\n")
        f.write("| Qualité | Nombre | % |\n")
        f.write("|---------|--------|---|\n")
        f.write(f"| 🟢 Complète | {complete_fields} | {(complete_fields/total_fields*100) if total_fields > 0 else 0:.1f}% |\n")
        f.write(f"| 🟡 Partielle | {partial_fields} | {(partial_fields/total_fields*100) if total_fields > 0 else 0:.1f}% |\n")
        f.write(f"| 🟠 Minimale | {minimal_fields} | {(minimal_fields/total_fields*100) if total_fields > 0 else 0:.1f}% |\n")
        f.write(f"| ⚫ Absente | {total_fields - documented_fields} | {((total_fields - documented_fields)/total_fields*100) if total_fields > 0 else 0:.1f}% |\n\n")
        
        # Barre de progression visuelle
        f.write("### 📊 Progression globale\n\n")
        f.write("```\n")
        bar_length = 50
        filled = int(pct_total / 100 * bar_length)
        bar = "█" * filled + "░" * (bar_length - filled)
        f.write(f"[{bar}] {pct_total:.1f}%\n")
        f.write("```\n\n")
        
        # Top 5 classes les mieux documentées
        f.write("## 🏆 Top 5 - Classes les mieux documentées\n\n")
        
        class_scores = []
        for c in all_classes:
            total_elements = 1 + len(c.methods) + len(c.fields)  # +1 pour la classe elle-même
            documented_elements = (1 if c.has_javadoc else 0) + \
                                  sum(1 for m in c.methods if m.has_javadoc) + \
                                  sum(1 for f in c.fields if f.has_javadoc)
            score = (documented_elements / total_elements * 100) if total_elements > 0 else 0
            class_scores.append((c, score, documented_elements, total_elements))
        
        class_scores.sort(key=lambda x: x[1], reverse=True)
        
        for i, (c, score, doc, total) in enumerate(class_scores[:5], 1):
            icon = "🥇" if i == 1 else "🥈" if i == 2 else "🥉" if i == 3 else "⭐"
            f.write(f"{icon} **{c.name}** : {score:.1f}% ({doc}/{total} éléments)\n")
        f.write("\n")
        
        # Classes nécessitant le plus de documentation
        f.write("## ⚠️ Top 5 - Classes nécessitant le plus de documentation\n\n")
        
        class_scores.sort(key=lambda x: (x[3] - x[2], -x[1]), reverse=True)  # Trier par nombre d'éléments non documentés
        
        for i, (c, score, doc, total) in enumerate(class_scores[:5], 1):
            missing = total - doc
            f.write(f"{i}. **{c.name}** : {missing} éléments non documentés ({score:.1f}% complété)\n")
        f.write("\n")
        
        # Trier les classes par nom pour la TDM et le détail
        all_classes.sort(key=lambda x: x.name)
        
        # Table des matières
        f.write("## � Table des matières\n\n")
        f.write("*Cliquez sur le nom d'une classe pour accéder à son détail*\n\n")
        
        # Grouper par première lettre pour faciliter la navigation
        classes_by_letter = {}
        for c in all_classes:
            first_letter = c.name[0].upper()
            if first_letter not in classes_by_letter:
                classes_by_letter[first_letter] = []
            classes_by_letter[first_letter].append(c)
        
        for letter in sorted(classes_by_letter.keys()):
            f.write(f"### {letter}\n\n")
            for c in classes_by_letter[letter]:
                # Calculer les stats
                total_elements = len(c.methods) + len(c.fields)
                documented_elements = sum(1 for m in c.methods if m.has_javadoc) + \
                                     sum(1 for f in c.fields if f.has_javadoc)
                pct = (documented_elements / total_elements * 100) if total_elements > 0 else 0
                
                # Choisir l'icône selon le statut
                if c.has_javadoc:
                    if pct >= 80:
                        icon = "✅"
                    elif pct >= 50:
                        icon = "🔶"
                    elif pct >= 20:
                        icon = "⚠️"
                    else:
                        icon = "❌"
                else:
                    icon = "❌"
                
                # Créer le lien vers la classe (ancre Markdown)
                anchor = c.name.lower().replace(" ", "-")
                f.write(f"- {icon} [{c.name}](#{anchor}) - {pct:.1f}% ({documented_elements}/{total_elements}) - {len(c.methods)} méthode(s), {len(c.fields)} propriété(s)\n")
            f.write("\n")
        
        # Détail par classe
        f.write("## 📋 Détail par classe\n\n")
        f.write("---\n\n")
        
        for java_class in all_classes:
            # Calculer les stats de la classe
            total_elements = len(java_class.methods) + len(java_class.fields)
            documented_elements = sum(1 for m in java_class.methods if m.has_javadoc) + \
                                 sum(1 for f in java_class.fields if f.has_javadoc)
            pct_class = (documented_elements / total_elements * 100) if total_elements > 0 else 0
            
            # Icône selon le pourcentage
            if pct_class >= 80:
                status_icon = "✅"
            elif pct_class >= 50:
                status_icon = "🔶"
            elif pct_class >= 20:
                status_icon = "⚠️"
            else:
                status_icon = "❌"
            
            f.write(f"### {status_icon} `{java_class.name}`\n\n")
            
            # Informations sur la classe
            f.write(f"**Fichier :** `{java_class.file_path}`\n\n")
            f.write(f"**Documentation de la classe :** {'✅ Oui' if java_class.has_javadoc else '❌ Non'}")
            if java_class.has_javadoc:
                f.write(f" ({java_class.javadoc_lines} lignes)")
            f.write("\n\n")
            
            if java_class.inner_classes:
                f.write(f"**Classes internes :** {', '.join(java_class.inner_classes)}\n\n")
            
            # Stats de la classe
            f.write(f"**Progression :** {documented_elements}/{total_elements} éléments documentés ({pct_class:.1f}%)\n\n")
            
            # Propriétés
            if java_class.fields:
                f.write(f"#### Propriétés ({len(java_class.fields)})\n\n")
                
                for field in java_class.fields:
                    # Icône selon la qualité
                    if field.javadoc_quality == "complete":
                        quality_icon = "🟢"
                        quality_text = "Complète"
                    elif field.javadoc_quality == "partial":
                        quality_icon = "🟡"
                        quality_text = "Partielle"
                    elif field.javadoc_quality == "minimal":
                        quality_icon = "🟠"
                        quality_text = "Minimale"
                    else:
                        quality_icon = "⚫"
                        quality_text = "Absente"
                    
                    signature = field.signature.replace("|", "\\|")
                    
                    f.write(f"##### {quality_icon} `{field.name}` - Ligne {field.line_number}\n\n")
                    f.write(f"**Qualité :** {quality_text}\n\n")
                    f.write(f"**Déclaration :**\n```java\n{field.signature}\n```\n\n")
                    
                    if field.has_javadoc and field.javadoc_content:
                        f.write(f"**Documentation actuelle :**\n```java\n{field.javadoc_content}\n```\n\n")
                    else:
                        f.write(f"**❌ Aucune documentation**\n\n")
                        f.write(f"**Suggestion :** Ajouter un Javadoc avec une description du rôle de cette propriété.\n\n")
                    
                    f.write("---\n\n")
            
            # Méthodes
            if java_class.methods:
                f.write(f"#### Méthodes ({len(java_class.methods)})\n\n")
                
                for method in java_class.methods:
                    # Icône selon la qualité
                    if method.javadoc_quality == "complete":
                        quality_icon = "🟢"
                        quality_text = "Complète"
                    elif method.javadoc_quality == "partial":
                        quality_icon = "🟡"
                        quality_text = "Partielle"
                    elif method.javadoc_quality == "minimal":
                        quality_icon = "🟠"
                        quality_text = "Minimale"
                    else:
                        quality_icon = "⚫"
                        quality_text = "Absente"
                    
                    signature = method.signature.replace("|", "\\|")
                    
                    f.write(f"##### {quality_icon} `{method.name}()` - Ligne {method.line_number}\n\n")
                    f.write(f"**Qualité :** {quality_text}\n\n")
                    f.write(f"**Signature :**\n```java\n{method.signature}\n```\n\n")
                    
                    if method.has_javadoc and method.javadoc_content:
                        f.write(f"**Documentation actuelle :**\n```java\n{method.javadoc_content}\n```\n\n")
                        
                        # Analyser les tags présents
                        content_lower = method.javadoc_content.lower()
                        tags_present = []
                        tags_missing = []
                        
                        if "@param" in content_lower:
                            tags_present.append("@param")
                        else:
                            tags_missing.append("@param")
                        
                        if "@return" in content_lower:
                            tags_present.append("@return")
                        else:
                            tags_missing.append("@return")
                        
                        if "@throws" in content_lower or "@exception" in content_lower:
                            tags_present.append("@throws")
                        
                        if "@see" in content_lower:
                            tags_present.append("@see")
                        
                        if "@author" in content_lower:
                            tags_present.append("@author")
                        
                        if "@example" in content_lower or "{@code" in content_lower:
                            tags_present.append("@example")
                        
                        if tags_present:
                            f.write(f"**Tags présents :** {', '.join(tags_present)}\n\n")
                        
                        if tags_missing and "(" in method.signature:  # Si la méthode a des paramètres
                            f.write(f"**⚠️ Tags manquants :** {', '.join(tags_missing)}\n\n")
                    else:
                        f.write(f"**❌ Aucune documentation**\n\n")
                        f.write(f"**Suggestion :** Ajouter un Javadoc avec :\n")
                        f.write(f"- Description de la méthode\n")
                        if "(" in method.signature and "void" not in method.signature.split("(")[0]:
                            f.write(f"- Tag `@param` pour chaque paramètre\n")
                        if "void" not in method.signature.split()[0]:
                            f.write(f"- Tag `@return` pour la valeur de retour\n")
                        f.write(f"\n")
                    
                    f.write("---\n\n")
            
            f.write("---\n\n")
        
        # Légende
        f.write("## 📖 Légende\n\n")
        f.write("### Qualité de la documentation\n\n")
        f.write("| Icône | Qualité | Description |\n")
        f.write("|-------|---------|-------------|\n")
        f.write("| 🟢 | Complète | Description + tags (@param, @return, @throws, @see, etc.) |\n")
        f.write("| 🟡 | Partielle | Description + quelques tags |\n")
        f.write("| 🟠 | Minimale | Description seule, sans tags structurés |\n")
        f.write("| ⚫ | Absente | Pas de Javadoc |\n\n")
        
        f.write("### État des classes\n\n")
        f.write("| Icône | Signification |\n")
        f.write("|-------|---------------|\n")
        f.write("| ✅ | Classe bien documentée (>80%) |\n")
        f.write("| 🔶 | Classe partiellement documentée (50-80%) |\n")
        f.write("| ⚠️ | Classe peu documentée (20-50%) |\n")
        f.write("| ❌ | Classe très peu documentée (<20%) |\n\n")
        
        f.write("---\n\n")
        f.write(f"*Rapport généré automatiquement par `analyse-javadoc.py` le {datetime.now().strftime('%d/%m/%Y à %H:%M:%S')}*\n")

def main():
    """Fonction principale"""
    
    print("🔍 Analyse de la documentation Javadoc...")
    print()
    
    # Trouver le répertoire source
    script_dir = Path(__file__).parent
    project_dir = script_dir.parent
    src_dir = project_dir / "src"
    
    if not src_dir.exists():
        print(f"❌ Erreur : Répertoire source non trouvé : {src_dir}")
        return
    
    print(f"📁 Répertoire source : {src_dir}")
    
    # Trouver tous les fichiers Java
    java_files = find_java_files(str(src_dir))
    print(f"📄 Fichiers Java trouvés : {len(java_files)}")
    print()
    
    # Parser tous les fichiers
    all_classes = []
    for java_file in java_files:
        print(f"   Analyse : {java_file.name}...", end=" ")
        classes = parse_java_file(java_file)
        all_classes.extend(classes)
        print(f"✓ ({len(classes)} classe(s))")
    
    print()
    print(f"✅ {len(all_classes)} classes analysées")
    print()
    
    # Générer le rapport
    output_file = project_dir / "doc" / "doxygen" / "ETAT_DOCUMENTATION.md"
    output_file.parent.mkdir(parents=True, exist_ok=True)
    
    print(f"📝 Génération du rapport : {output_file}")
    generate_markdown_report(all_classes, str(output_file))
    
    print()
    print("✨ Rapport généré avec succès !")
    print(f"📄 Fichier : {output_file}")
    print()
    print("Vous pouvez consulter le rapport avec :")
    print(f"   cat {output_file}")

if __name__ == "__main__":
    main()
