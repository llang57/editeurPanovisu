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
                 has_javadoc: bool, javadoc_lines: int = 0, signature: str = ""):
        self.name = name
        self.element_type = element_type  # 'class', 'method', 'field'
        self.line_number = line_number
        self.has_javadoc = has_javadoc
        self.javadoc_lines = javadoc_lines
        self.signature = signature

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

def extract_javadoc(lines: List[str], start_index: int) -> Tuple[bool, int]:
    """
    Vérifie s'il y a un commentaire Javadoc avant l'élément
    
    Retourne (has_javadoc, nb_lines)
    """
    # Remonter pour chercher le Javadoc
    i = start_index - 1
    javadoc_lines = 0
    in_javadoc = False
    
    while i >= 0:
        line = lines[i].strip()
        
        # Si ligne vide ou commentaire simple, continuer
        if not line or line.startswith("//"):
            i -= 1
            continue
        
        # Fin du Javadoc (début dans le sens de la lecture)
        if line.startswith("/**"):
            in_javadoc = True
            javadoc_lines += 1
            # Vérifier que ce n'est pas un commentaire vide /** */
            if line.endswith("*/") and len(line.strip()) <= 5:
                return False, 0
            return True, javadoc_lines
        
        # Ligne de Javadoc
        if line.startswith("*"):
            javadoc_lines += 1
            i -= 1
            continue
        
        # Si on trouve autre chose, pas de Javadoc
        break
        
    return False, 0

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
            has_javadoc, javadoc_lines = extract_javadoc(lines, i - 1)
            
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
            
            has_javadoc, javadoc_lines = extract_javadoc(lines, i - 1)
            
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
                signature=signature
            )
            current_class.methods.append(method)
            continue
        
        # Détecter un champ
        field_match = field_pattern.match(stripped)
        if field_match and "(" not in line:  # Éviter les faux positifs avec méthodes
            field_name = field_match.group(5)
            has_javadoc, javadoc_lines = extract_javadoc(lines, i - 1)
            
            signature = stripped
            if len(signature) > 80:
                signature = signature[:77] + "..."
            
            field = JavaElement(
                name=field_name,
                element_type="field",
                line_number=i,
                has_javadoc=has_javadoc,
                javadoc_lines=javadoc_lines,
                signature=signature
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
    
    total_fields = sum(len(c.fields) for c in all_classes)
    documented_fields = sum(sum(1 for f in c.fields if f.has_javadoc) for c in all_classes)
    
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
        
        # Détail par classe
        f.write("## 📋 Détail par classe\n\n")
        f.write("---\n\n")
        
        # Trier les classes par nom
        all_classes.sort(key=lambda x: x.name)
        
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
                f.write("| Nom | Ligne | Documentée | Signature |\n")
                f.write("|-----|-------|------------|------------|\n")
                
                for field in java_class.fields:
                    status = "✅" if field.has_javadoc else "❌"
                    signature = field.signature.replace("|", "\\|")  # Escape pipe pour Markdown
                    f.write(f"| `{field.name}` | {field.line_number} | {status} | `{signature}` |\n")
                f.write("\n")
            
            # Méthodes
            if java_class.methods:
                f.write(f"#### Méthodes ({len(java_class.methods)})\n\n")
                f.write("| Nom | Ligne | Documentée | Signature |\n")
                f.write("|-----|-------|------------|------------|\n")
                
                for method in java_class.methods:
                    status = "✅" if method.has_javadoc else "❌"
                    signature = method.signature.replace("|", "\\|")
                    f.write(f"| `{method.name}` | {method.line_number} | {status} | `{signature}` |\n")
                f.write("\n")
            
            f.write("---\n\n")
        
        # Légende
        f.write("## 📖 Légende\n\n")
        f.write("| Icône | Signification |\n")
        f.write("|-------|---------------|\n")
        f.write("| ✅ | Élément documenté avec Javadoc |\n")
        f.write("| ❌ | Élément non documenté |\n")
        f.write("| 🔶 | Classe partiellement documentée (50-80%) |\n")
        f.write("| ⚠️ | Classe peu documentée (20-50%) |\n")
        f.write("| 🏆 | Classe bien documentée (>80%) |\n\n")
        
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
