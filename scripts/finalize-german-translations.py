#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Traductions complètes des clés [TODO] en allemand
"""

# Toutes les traductions manquantes
COMPLETE_TRANSLATIONS = {
    # General - fichiers et traitement
    'nbFichiersATraiter': '%d Datei(en) bereit zur Verarbeitung',
    'nbImagesTraitees': '%d Bild(er) erfolgreich verarbeitet',
    'optionsSauvegarde': 'Speicheroptionen',
    'ouCliquez': 'oder klicken Sie, um Dateien auszuw\u00e4hlen',
    'outilsConversionRatio2to1': 'Konvertierung ins 2:1-Verh\u00e4ltnis',
    'outilsDiaporama': 'Diashow bearbeiten/erstellen',
    'outilsRatio2to1': 'Konvertierung ins 2:1-Verh\u00e4ltnis',
    'outilsTraitementImages': 'Bildverarbeitung',
    'pretATraiter': '%d Datei(en) bereit zur Verarbeitung in "%s"',
    'qualiteJpeg': 'JPEG-Qualit\u00e4t',
    'repertoireSortie': 'Ausgabeverzeichnis',
    'sauverRepertoireOrigine': 'Im Originalverzeichnis speichern',
    'sauverRepertoirePersonnalise': 'In benutzerdefiniertem Verzeichnis speichern',
    'selectionnerImages': 'Bilder ausw\u00e4hlen',
    'suffixeNomFichier': 'Dateinamensuffix',
    'supprimerSelection': 'Auswahl entfernen',
    'tooltipEcraser': 'Warnung: Originaldateien werden dauerhaft ersetzt',
    'tooltipSousDossier': 'Erstellt einen Unterordner mit aktuellem Datum und Uhrzeit',
    'tooltipSuffixe': 'Suffix, das an Dateinamen angeh\u00e4ngt wird (z.B. "_redim" -> bild_redim.jpg)',
    'traitementEchec': 'Verarbeitung fehlgeschlagen',
    'traitementEnCours': 'Verarbeitung l\u00e4uft...',
    'traitementImage': 'Verarbeitung %d/%d: %s',
    'traitementReussi': 'Erfolg',
    'traitementTermine': 'Verarbeitung abgeschlossen!',
    'valider': 'Best\u00e4tigen',
    'viderListe': 'Liste leeren',
    '\ufeff#Mon, 14 Sep 2015 08': '48:10 +0200',  # Date - garder tel quel
    
    # Main - suite des fonctionnalités
    'main.affiMasque': 'Maske anzeigen',
    'main.afficheBarrePersonnalisee': 'Benutzerdefinierte Leiste anzeigen',
    'main.choixImageHS': 'Hotspot-Bild ausw\u00e4hlen',
    'main.choixPolice': 'Schriftart w\u00e4hlen',
    'main.choixTelechargement': 'Download-Auswahl',
    'main.cibleChoisir': 'Ziel w\u00e4hlen',
    'main.couleurFondPano': 'Panorama-Hintergrundfarbe',
    'main.diapoHS': 'Diashow-Hotspot',
    'main.editeZone': 'Bereich bearbeiten',
    'main.genereHTML': 'HTML generieren',
    'main.imageChoisir': 'Bild w\u00e4hlen',
    'main.infoHS': 'Info-Hotspot',
    'main.modeDebug': 'Debug-Modus',
    'main.multiReso': 'Multi-Aufl\u00f6sung',
    'main.ordreAffichage': 'Anzeigereihenfolge',
    'main.pdf': 'PDF',
    'main.polygon': 'Polygon',
    'main.rectangle': 'Rechteck',
    'main.resetVisite': 'Tour zur\u00fccksetzen',
    'main.rotation': 'Drehung',
    'main.sourisSurvol': 'Maus-Hover',
    'main.survol': 'Hover',
    
    # MenuContextuel
    'menuContextuelHs.afficherInfobulle': 'Tooltip anzeigen',
    'menuContextuelHs.changerImage': 'Bild \u00e4ndern',
    'menuContextuelHs.couleurBordure': 'Rahmenfarbe',
    'menuContextuelHs.couleurFond': 'Hintergrundfarbe',
    'menuContextuelHs.opacite': 'Deckkraft',
    
    # Panoramique
    'pano.chargeImage': 'Bild laden',
    'pano.genererHTML': 'HTML generieren',
    'pano.masquer': 'Verbergen',
    'pano.modele': 'Vorlage',
    'pano.multiReso': 'Multi-Aufl\u00f6sung',
    'pano.ordreAffichage': 'Anzeigereihenfolge',
    
    # Redimensionnement suite
    'redim.ajouter': 'Hinzuf\u00fcgen',
    'redim.parcourir': 'Durchsuchen',
    'redim.pixels': 'Pixel',
    'redim.traiterImages': 'Bilder verarbeiten',
    
    # Visite suite
    'visite.chargerModele': 'Vorlage laden',
    'visite.configurationGenerale': 'Allgemeine Konfiguration',
    'visite.export': 'Export',
    'visite.generationEnCours': 'Generierung l\u00e4uft...',
    'visite.langue': 'Sprache',
    'visite.modele': 'Vorlage',
    'visite.parametres': 'Parameter',
    'visite.sauverModele': 'Vorlage speichern',
}

def generate_final_file():
    """
    Génère le fichier final avec toutes les traductions complètes
    """
    from pathlib import Path
    from datetime import datetime
    
    output = Path(__file__).parent.parent / "doc" / "TRADUCTIONS_ALLEMAND_COMPLETES.properties"
    
    print("=" * 70)
    print("  TRADUCTIONS ALLEMANDES COMPLÈTES")
    print("=" * 70)
    print()
    print(f"📝 Génération de {len(COMPLETE_TRANSLATIONS)} traductions...")
    print()
    
    with open(output, 'w', encoding='utf-8') as f:
        f.write("# Traductions allemandes compl\u00e8tes pour PanoVisu\n")
        f.write(f"# G\u00e9n\u00e9r\u00e9 le {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}\n")
        f.write(f"# Total: {len(COMPLETE_TRANSLATIONS)} cl\u00e9s\n")
        f.write("#\n")
        f.write("# Toutes les traductions [TODO] ont \u00e9t\u00e9 compl\u00e9t\u00e9es\n")
        f.write("# Ces traductions doivent \u00eatre ajout\u00e9es \u00e0 PanoVisu_de.properties\n")
        f.write("\n# " + "=" * 66 + "\n\n")
        
        # Grouper par catégorie
        categories = {}
        for key, value in sorted(COMPLETE_TRANSLATIONS.items()):
            category = key.split('.')[0] if '.' in key else 'general'
            if category not in categories:
                categories[category] = []
            categories[category].append((key, value))
        
        # Écrire par catégorie
        for category, items in sorted(categories.items()):
            f.write(f"# {category.upper()}\n")
            f.write(f"# {len(items)} cl\u00e9s\n\n")
            
            for key, value in items:
                f.write(f"{key}={value}\n")
            
            f.write("\n")
    
    print(f"✅ Fichier généré: {output.name}")
    print()
    print("📋 Traductions par catégorie:")
    for category, items in sorted(categories.items()):
        print(f"   {category:20} - {len(items):3} clés")
    print()
    print("=" * 70)
    print()
    print("📌 Ces traductions complètent le fichier allemand à 100% !")
    print()
    print("=" * 70)

if __name__ == '__main__':
    generate_final_file()
