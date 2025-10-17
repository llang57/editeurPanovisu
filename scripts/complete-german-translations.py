#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script pour compléter automatiquement les traductions allemandes manquantes
en extrayant les clés du rapport et en générant les traductions
"""

import re
from pathlib import Path

# Chemin des fichiers
RAPPORT_FILE = Path(__file__).parent.parent / "doc" / "RAPPORT_TRADUCTIONS.md"
GERMAN_FILE = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n" / "PanoVisu_de.properties"
OUTPUT_FILE = Path(__file__).parent.parent / "doc" / "COMPLEMENT_ALLEMAND.properties"


# Traductions supplémentaires en allemand
ADDITIONAL_TRANSLATIONS = {
    # Diapo suite
    'diapo.opacite': 'Deckkraft',
    'diapo.sauver': 'Diashow best\u00e4tigen',
    'diapo.sauverTexte': 'M\u00f6chten Sie die Diashow best\u00e4tigen?',
    'diapo.supprimeImage': 'Bild entfernen',
    'diapo.supprimerImage': 'Bild l\u00f6schen',
    'diapo.supprimerImageTexte': 'M\u00f6chten Sie das aktuelle Bild wirklich l\u00f6schen?',
    'diapo.visualise': 'Diashow anzeigen',
    
    # Dimensions et formats
    'dimensions': 'Abmessungen',
    'dragDropImages': 'Bilder hier ablegen',
    'ecraserFichiersOrigine': 'Originaldateien \u00fcberschreiben',
    
    # EditeurHTML suite
    'editeurHTML.couleurFond': 'HTML-Hintergrundfarbe',
    'editeurHTML.couleurTexte': 'Textfarbe',
    
    # Erreurs
    'erreurAucunFichier': 'Keine Dateien ausgew\u00e4hlt',
    'erreurAucunFormat': 'Kein Ausgabeformat ausgew\u00e4hlt',
    'erreurAucunRepertoire': 'Kein Ausgabeverzeichnis ausgew\u00e4hlt',
    'erreurHauteurInvalide': 'Ung\u00fcltige H\u00f6he (muss eine positive Zahl sein)',
    'erreurLargeurInvalide': 'Ung\u00fcltige Breite (muss eine positive Zahl sein)',
    'erreurSuffixeVide': 'Bitte geben Sie ein Suffix f\u00fcr Dateinamen ein (oder w\u00e4hlen Sie "\u00dcberschreiben der Originaldateien")',
    
    # Fichiers et traitement
    'fichiersATraiter': 'Zu verarbeitende Dateien',
    'fichiersImages': 'Bilddateien',
    'formatSortie': 'Ausgabeformat',
    'hauteur': 'H\u00f6he',
    'largeur': 'Breite',
    
    # Interface suite
    'interface.afficheDescriptionChargement': 'Panoramabeschreibung beim Laden anzeigen',
    'interface.calque': 'Ebene',
    'interface.calques': 'Ebenen',
    'interface.calquesTous': 'Alle/Keine',
    'interface.changeImage': 'Bild \u00e4ndern',
    'interface.choixCarte': 'Kartentyp',
    'interface.choixCouleurBordure': 'Rahmenfarbe',
    'interface.choixTitreAdapte': 'An Inhalt anpassen',
    'interface.couleurFondTheme': 'Hintergrundfarbe',
    'interface.couleurTexteTheme': 'Textfarbe',
    'interface.description': 'Beschreibung',
    'interface.lienCible': 'Ziel',
    'interface.lienInterne': 'Intern',
    'interface.lienPage': 'Neue Seite',
    'interface.ombreInfoBulle': 'Tooltip-Schatten',
    'interface.opaciteInfoBulle': 'Tooltip-Deckkraft',
    'interface.opaciteTheme': 'Deckkraft',
    'interface.policeTheme': 'Schriftart',
    'interface.rayonBordure': 'Rahmenradius',
    'interface.styleInfoBulle': 'Tooltip-Stil',
    'interface.supprimeImage': 'Bild l\u00f6schen',
    'interface.tailleBordure': 'Rahmenbreite',
    'interface.theme': 'Allgemeine Eigenschaften',
    
    # Main suite (auto-rotation, tour, etc.)
    'main.autoRotationDemarrage': 'Autorotation beim Start',
    'main.autoRotationVitesse': 'Autorotationsgeschwindigkeit',
    'main.autoTour': 'Automatische Tour',
    'main.autoTourChange': '\u00c4nderungen nach',
    'main.autoTourDemarrage': 'Autotour starten nach (s)',
    'main.autoTourRotation': 'Autorotation / Automatische Tour',
    'main.blocage': 'Auf alle Panoramen anwenden',
    'main.blocageNadir': 'Nadir blockieren',
    'main.blocageZenith': 'Zenit blockieren',
    'main.choixDiapo': 'Diashow ausw\u00e4hlen',
    'main.descriptionIA': 'KI-Beschreibung',
    'main.divCreationRapide': 'Schnellerstellung',
    'main.echelleMaxPlan': 'Maximaler Planma\u00dfstab',
    'main.echelleMinPlan': 'Minimaler Planma\u00dfstab',
    'main.effaceMarqueur': 'Markierung l\u00f6schen',
    'main.genererIA': 'Mit KI generieren',
    'main.hauteurImage': 'Bildh\u00f6he',
    'main.imageHS': 'Hotspot-Bild',
    'main.largeurImage': 'Bildbreite',
    'main.longitude': 'L\u00e4ngengrad',
    'main.latitude': 'Breitengrad',
    'main.marqueurGeo': 'Geolokalisierungsmarkierung',
    'main.masque': 'Maske',
    'main.positionPano': 'Panoramaposition',
    'main.rayonImage': 'Bildradius',
    'main.supprimerIA': 'KI-Beschreibung l\u00f6schen',
    'main.textAlter': 'Alternativer Text',
    'main.titreMarqueur': 'Markierungstitel',
    'main.transitionEntree': 'Eingangs\u00fcbergang',
    'main.transitionSortie': 'Ausgangs\u00fcbergang',
    'main.typeImage': 'Bildtyp',
    'main.typeMarqueur': 'Markierungstyp',
    'main.vitesse': 'Geschwindigkeit',
    'main.zoomMaxPano': 'Maximaler Panorama-Zoom',
    'main.zoomMinPano': 'Minimaler Panorama-Zoom',
    
    # MenuContextuelHotspot
    'menuContextuelHotspot.afficher': 'Anzeigen',
    'menuContextuelHotspot.cacher': 'Verbergen',
    'menuContextuelHotspot.dupliquer': 'Duplizieren',
    'menuContextuelHotspot.editer': 'Bearbeiten',
    'menuContextuelHotspot.supprimer': 'L\u00f6schen',
    
    # Panoramique
    'panoramique.charger': 'Panorama laden',
    'panoramique.genererVisite': 'Tour generieren',
    'panoramique.ordre': 'Reihenfolge',
    'panoramique.supprimer': 'Panorama l\u00f6schen',
    'panoramique.titre': 'Panoramatitel',
    
    # Redimensionnement suite
    'redim.appliquer': 'Anwenden',
    'redim.format': 'Format',
    'redim.information': 'Information',
    'redim.pourcentage': 'Prozentsatz',
    'redim.qualiteJpeg': 'JPEG-Qualit\u00e4t',
    
    # Sauvegarde
    'sauvegarderVisite': 'Tour speichern',
    'selectionnerFichier': 'Datei ausw\u00e4hlen',
    'selectionnerRepertoire': 'Verzeichnis ausw\u00e4hlen',
    
    # Visite suite
    'visite.aPropos': '\u00dcber',
    'visite.auteur': 'Autor',
    'visite.copyright': 'Urheberrecht',
    'visite.date': 'Datum',
    'visite.description': 'Beschreibung',
    'visite.mots': 'Schl\u00fcsselw\u00f6rter',
    'visite.nom': 'Tourenname',
}


def extract_missing_keys_from_report():
    """
    Extrait les clés manquantes depuis le rapport Markdown
    """
    print("📖 Lecture du rapport de traductions...")
    
    with open(RAPPORT_FILE, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Trouver la section allemande
    match = re.search(r'### Deutsch.*?```\n(.*?)```', content, re.DOTALL)
    if not match:
        print("❌ Section allemande non trouvée dans le rapport")
        return {}
    
    missing_section = match.group(1)
    
    # Extraire les lignes clé=valeur
    missing_keys = {}
    for line in missing_section.split('\n'):
        line = line.strip()
        if '=' in line and not line.startswith('#'):
            key, value = line.split('=', 1)
            missing_keys[key] = value
    
    print(f"✅ {len(missing_keys)} clés extraites du rapport")
    return missing_keys


def generate_completion_file():
    """
    Génère un fichier de complément avec les traductions allemandes
    """
    print("=" * 70)
    print("  GÉNÉRATION DU COMPLÉMENT DE TRADUCTIONS ALLEMANDES")
    print("=" * 70)
    print()
    
    # Extraire les clés manquantes
    missing_from_report = extract_missing_keys_from_report()
    
    # Combiner avec les traductions additionnelles
    all_translations = {**ADDITIONAL_TRANSLATIONS}
    
    # Ajouter les clés du rapport qui ne sont pas déjà traduites
    for key in missing_from_report:
        if key not in all_translations:
            # Pas de traduction - ajouter un placeholder
            all_translations[key] = f"[TODO] {missing_from_report[key]}"
    
    print(f"📝 {len(all_translations)} traductions à ajouter")
    print()
    
    # Écrire le fichier de complément
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write("# Compl\u00e9ment de traductions allemandes pour PanoVisu\n")
        f.write("# G\u00e9n\u00e9r\u00e9 automatiquement\n")
        f.write(f"# Total\: {len(all_translations)} cl\u00e9s\n")
        f.write("#\n")
        f.write("# Instructions\:\n")
        f.write("# 1. V\u00e9rifiez les traductions ci-dessous\n")
        f.write("# 2. Les cl\u00e9s marqu\u00e9es [TODO] n\u00e9cessitent une traduction manuelle\n")
        f.write("# 3. Ajoutez-les \u00e0 PanoVisu_de.properties\n")
        f.write("\n# " + "=" * 66 + "\n\n")
        
        # Grouper par catégorie
        categories = {}
        for key, value in sorted(all_translations.items()):
            category = key.split('.')[0] if '.' in key else 'general'
            if category not in categories:
                categories[category] = []
            categories[category].append((key, value))
        
        # Écrire par catégorie
        for category, items in sorted(categories.items()):
            needs_translation = sum(1 for _, v in items if v.startswith('[TODO]'))
            status = f" - {needs_translation} [TODO]" if needs_translation > 0 else ""
            
            f.write(f"# {category.upper()}{status}\n")
            f.write(f"# {len(items)} cl\u00e9s\n\n")
            
            for key, value in items:
                f.write(f"{key}={value}\n")
            
            f.write("\n")
    
    print(f"✅ Fichier généré\: {OUTPUT_FILE.relative_to(OUTPUT_FILE.parent.parent)}")
    print()
    
    # Statistiques
    todo_count = sum(1 for v in all_translations.values() if v.startswith('[TODO]'))
    done_count = len(all_translations) - todo_count
    
    print("📊 Statistiques\:")
    print(f"   ✅ Traduites\: {done_count}")
    print(f"   ⚠️  À faire\: {todo_count}")
    print()
    
    print("=" * 70)
    print()
    print("📌 PROCHAINES ÉTAPES\:")
    print()
    print(f"1. Vérifiez le fichier\: {OUTPUT_FILE}")
    print(f"2. Traduisez les {todo_count} clés marquées [TODO]")
    print("3. Ajoutez toutes les traductions à PanoVisu_de.properties")
    print("4. Relancez\: python scripts/check-translations.py")
    print()
    print("=" * 70)


if __name__ == '__main__':
    generate_completion_file()
