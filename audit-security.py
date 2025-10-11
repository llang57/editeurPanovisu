#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script d'Audit de Sécurité des Clés API
Vérifie qu'aucune clé API n'est exposée dans le repository
"""

import os
import re
import sys
from pathlib import Path
from typing import List, Tuple

# Couleurs pour le terminal
class Colors:
    CYAN = '\033[96m'
    GREEN = '\033[92m'
    YELLOW = '\033[93m'
    RED = '\033[91m'
    GRAY = '\033[90m'
    RESET = '\033[0m'
    BOLD = '\033[1m'

def print_header(text: str):
    """Affiche un en-tête coloré"""
    print(f"\n{Colors.CYAN}{Colors.BOLD}{text}{Colors.RESET}")
    print(f"{Colors.CYAN}{'=' * 60}{Colors.RESET}\n")

def print_success(text: str):
    """Affiche un message de succès"""
    print(f"{Colors.GREEN}   ✅ {text}{Colors.RESET}")

def print_error(text: str):
    """Affiche un message d'erreur"""
    print(f"{Colors.RED}   ❌ {text}{Colors.RESET}")

def print_warning(text: str):
    """Affiche un avertissement"""
    print(f"{Colors.YELLOW}   ⚠️  {text}{Colors.RESET}")

def print_info(text: str):
    """Affiche une information"""
    print(f"{Colors.GRAY}   ℹ️  {text}{Colors.RESET}")

def check_gitignore() -> Tuple[bool, str]:
    """Vérifie que api-keys.properties est dans .gitignore"""
    if not os.path.exists('.gitignore'):
        return False, "Fichier .gitignore non trouvé"
    
    with open('.gitignore', 'r', encoding='utf-8') as f:
        content = f.read()
        if 'api-keys.properties' in content:
            return True, "api-keys.properties est dans .gitignore"
        else:
            return False, "api-keys.properties manquant dans .gitignore"

def check_git_status() -> Tuple[bool, str]:
    """Vérifie que api-keys.properties n'est pas tracé par Git"""
    try:
        import subprocess
        result = subprocess.run(
            ['git', 'status', '--porcelain'],
            capture_output=True,
            text=True,
            check=True
        )
        
        # Chercher exactement "api-keys.properties" (pas le template)
        lines = result.stdout.split('\n')
        for line in lines:
            # Ignorer le template
            if 'api-keys.properties.template' in line:
                continue
            # Vérifier le fichier de clés
            if 'api-keys.properties' in line:
                return False, f"api-keys.properties est tracé par Git! ({line.strip()})"
        
        return True, "api-keys.properties n'est pas tracé par Git"
    except (subprocess.CalledProcessError, FileNotFoundError):
        return None, "Impossible de vérifier git status (Git installé?)"

def find_keys_in_files(pattern: str, file_pattern: str, exclude_words: List[str]) -> List[Tuple[str, int]]:
    """Cherche les clés API dans les fichiers"""
    findings = []
    
    for root, _, files in os.walk('.'):
        # Ignorer les dossiers .git, target, node_modules, etc.
        if any(skip in root for skip in ['.git', 'target', 'node_modules', '__pycache__']):
            continue
            
        for file in files:
            if not file.endswith(file_pattern):
                continue
                
            file_path = os.path.join(root, file)
            
            try:
                with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                    content = f.read()
                    
                    # Chercher le pattern
                    matches = re.finditer(pattern, content)
                    for match in matches:
                        # Vérifier que ce n'est pas un placeholder
                        matched_text = match.group(0)
                        if not any(word in matched_text.upper() for word in exclude_words):
                            # Compter le numéro de ligne
                            line_num = content[:match.start()].count('\n') + 1
                            findings.append((file_path, line_num))
            except Exception:
                # Ignorer les fichiers qui ne peuvent pas être lus
                pass
    
    return findings

def check_markdown_files() -> Tuple[bool, List[str]]:
    """Vérifie les fichiers Markdown"""
    errors = []
    
    # Pattern pour Hugging Face (hf_ + 30+ caractères alphanumériques)
    hf_pattern = r'hf_[A-Za-z0-9]{30,}'
    hf_findings = find_keys_in_files(hf_pattern, '.md', ['YOUR', 'API', 'KEY', 'EXAMPLE', 'TEMPLATE', 'HERE'])
    
    for file_path, line_num in hf_findings:
        errors.append(f"{file_path}:{line_num}: Clé Hugging Face détectée")
    
    # Pattern pour OpenRouter (sk-or-v1- + 40+ caractères)
    or_pattern = r'sk-or-v1-[A-Za-z0-9]{40,}'
    or_findings = find_keys_in_files(or_pattern, '.md', ['YOUR', 'API', 'KEY', 'EXAMPLE', 'TEMPLATE', 'HERE'])
    
    for file_path, line_num in or_findings:
        errors.append(f"{file_path}:{line_num}: Clé OpenRouter détectée")
    
    return len(errors) == 0, errors

def check_java_files() -> Tuple[bool, List[str]]:
    """Vérifie les fichiers Java"""
    errors = []
    
    # Pattern pour clés hardcodées (entre guillemets)
    hf_pattern = r'"hf_[A-Za-z0-9]{30,}"'
    hf_findings = find_keys_in_files(hf_pattern, '.java', [])
    
    for file_path, line_num in hf_findings:
        errors.append(f"{file_path}:{line_num}: Clé HF hardcodée")
    
    or_pattern = r'"sk-or-v1-[A-Za-z0-9]{40,}"'
    or_findings = find_keys_in_files(or_pattern, '.java', [])
    
    for file_path, line_num in or_findings:
        errors.append(f"{file_path}:{line_num}: Clé OR hardcodée")
    
    return len(errors) == 0, errors

def check_template() -> Tuple[bool, str]:
    """Vérifie le fichier template"""
    if not os.path.exists('api-keys.properties.template'):
        return None, "Template api-keys.properties.template manquant"
    
    with open('api-keys.properties.template', 'r', encoding='utf-8') as f:
        content = f.read()
        
        # Vérifier qu'il n'y a pas de vraies clés
        has_real_hf = re.search(r'hf_[A-Za-z0-9]{30,}', content) and 'YOUR' not in content and 'HERE' not in content
        has_real_or = re.search(r'sk-or-v1-[A-Za-z0-9]{40,}', content) and 'YOUR' not in content and 'HERE' not in content
        
        if has_real_hf or has_real_or:
            return False, "Vraies clés détectées dans le template!"
        else:
            return True, "Template ne contient pas de vraies clés"

def check_local_config() -> Tuple[bool, str]:
    """Vérifie la configuration locale"""
    if not os.path.exists('api-keys.properties'):
        return None, "Fichier api-keys.properties manquant (créez-le depuis le template)"
    
    with open('api-keys.properties', 'r', encoding='utf-8') as f:
        content = f.read()
        
        if 'YOUR_API_KEY_HERE' in content:
            return None, "Fichier contient des placeholders (pas configuré)"
        else:
            return True, "Fichier semble configuré avec de vraies clés"

def main():
    """Fonction principale"""
    print_header("🔍 AUDIT DE SÉCURITÉ DES CLÉS API")
    
    errors_found = 0
    
    # 1. Vérifier .gitignore
    print(f"{Colors.YELLOW}1️⃣  Vérification .gitignore...{Colors.RESET}")
    success, message = check_gitignore()
    if success:
        print_success(message)
    elif success is False:
        print_error(message)
        errors_found += 1
    else:
        print_warning(message)
    
    # 2. Vérifier git status
    print(f"\n{Colors.YELLOW}2️⃣  Vérification git status...{Colors.RESET}")
    success, message = check_git_status()
    if success:
        print_success(message)
    elif success is False:
        print_error(message)
        errors_found += 1
    else:
        print_warning(message)
    
    # 3. Vérifier les fichiers Markdown
    print(f"\n{Colors.YELLOW}3️⃣  Vérification documentation (.md)...{Colors.RESET}")
    success, error_list = check_markdown_files()
    if success:
        print_success("Aucune vraie clé trouvée dans les fichiers .md")
    else:
        print_error(f"Clés API trouvées dans la documentation! ({len(error_list)} occurrences)")
        for error in error_list[:5]:  # Afficher max 5 erreurs
            print(f"{Colors.RED}      {error}{Colors.RESET}")
        if len(error_list) > 5:
            print(f"{Colors.RED}      ... et {len(error_list) - 5} autres{Colors.RESET}")
        errors_found += len(error_list)
    
    # 4. Vérifier le template
    print(f"\n{Colors.YELLOW}4️⃣  Vérification template...{Colors.RESET}")
    success, message = check_template()
    if success:
        print_success(message)
    elif success is False:
        print_error(message)
        errors_found += 1
    else:
        print_warning(message)
    
    # 5. Vérifier la configuration locale
    print(f"\n{Colors.YELLOW}5️⃣  Vérification configuration locale...{Colors.RESET}")
    success, message = check_local_config()
    if success:
        print_success(message)
    elif success is False:
        print_error(message)
        errors_found += 1
    else:
        print_warning(message)
    
    # 6. Vérifier les fichiers Java
    print(f"\n{Colors.YELLOW}6️⃣  Vérification code source (.java)...{Colors.RESET}")
    success, error_list = check_java_files()
    if success:
        print_success("Aucune clé hardcodée dans les fichiers .java")
    else:
        print_error(f"Clés hardcodées trouvées! ({len(error_list)} occurrences)")
        for error in error_list[:5]:
            print(f"{Colors.RED}      {error}{Colors.RESET}")
        if len(error_list) > 5:
            print(f"{Colors.RED}      ... et {len(error_list) - 5} autres{Colors.RESET}")
        errors_found += len(error_list)
    
    # Résumé final
    print_header("📊 RÉSUMÉ DE L'AUDIT")
    
    if errors_found == 0:
        print(f"{Colors.GREEN}{Colors.BOLD}✅ SUCCÈS: Aucune erreur de sécurité détectée{Colors.RESET}\n")
        print(f"{Colors.GREEN}   Le repository est sécurisé pour être poussé sur Git.{Colors.RESET}\n")
    else:
        print(f"{Colors.RED}{Colors.BOLD}❌ ÉCHEC: {errors_found} erreur(s) détectée(s){Colors.RESET}\n")
        print(f"{Colors.RED}   ⚠️  NE PAS POUSSER SUR GIT AVANT CORRECTION!{Colors.RESET}\n")
        print(f"{Colors.YELLOW}Actions recommandées:{Colors.RESET}")
        print(f"{Colors.GRAY}   1. Corriger les erreurs listées ci-dessus{Colors.RESET}")
        print(f"{Colors.GRAY}   2. Relancer cet audit: python audit-security.py{Colors.RESET}")
        print(f"{Colors.GRAY}   3. Si des clés ont été commitées, voir doc\\SECURITE_CLES_API.md{Colors.RESET}\n")
    
    print(f"{Colors.CYAN}Documentation: doc\\SECURITE_CLES_API.md{Colors.RESET}\n")
    
    # Code de sortie
    sys.exit(1 if errors_found > 0 else 0)

if __name__ == '__main__':
    main()
