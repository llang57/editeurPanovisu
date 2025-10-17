#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script de génération des traductions allemandes manquantes pour PanoVisu
Génère un fichier avec les 172 clés manquantes traduites en allemand
"""

from pathlib import Path
from datetime import datetime

# Traductions allemandes des clés manquantes
GERMAN_TRANSLATIONS = {
    # Général
    'ajouterFichiers': 'Dateien hinzufügen',
    'annuler': 'Abbrechen',
    'aucunRepertoireSelectionne': 'Kein Verzeichnis ausgewählt',
    'build.numero': '2\u00a0666',
    'choisir': 'Auswählen',
    'choisirRepertoireSortie': 'Ausgabeverzeichnis wählen',
    'confirmation': 'Bestätigung',
    'confirmerAnnulation': 'Möchten Sie die Verarbeitung wirklich abbrechen?',
    'conserverRatio': 'Seitenverhältnis beibehalten',
    
    # Conversion
    'conversion.ajouterFichiers': 'Dateien hinzufügen',
    'conversion.annuler': 'Abbrechen',
    'conversion.bas': 'Unten',
    'conversion.centre': 'Mitte',
    'conversion.choisirImages': 'Bilder auswählen',
    'conversion.choisirRepertoire': 'Ausgabeverzeichnis wählen',
    'conversion.dragDropImages': 'Bilder hier ablegen oder klicken, um Dateien auszuwählen',
    'conversion.ecraserOrigine': 'Originaldateien überschreiben',
    'conversion.erreur': 'Fehler',
    'conversion.erreurAucunFichier': 'Keine Dateien ausgewählt',
    'conversion.erreurRepertoireVide': 'Bitte wählen Sie ein Ausgabeverzeichnis',
    'conversion.erreurTraitement': 'Fehler bei der Verarbeitung von %s\: %s',
    'conversion.fichiersATraiter': 'Zu verarbeitende Dateien',
    'conversion.formatSortie': 'Ausgabeformat',
    'conversion.haut': 'Oben',
    'conversion.imagesSupporte': 'Unterstützte Bilder',
    'conversion.information': 'Information',
    'conversion.optionsSauvegarde': 'Speicheroptionen',
    'conversion.parcourir': 'Durchsuchen',
    'conversion.positionnementVertical': 'Vertikale Positionierung',
    'conversion.previsualisation': 'Vorschau',
    'conversion.qualiteJpeg': 'JPEG-Qualität',
    'conversion.regenerer': 'Neu generieren',
    'conversion.remplissageCouleurMoyenne': 'Durchschnittsfarbfüllung',
    'conversion.remplissageFlou': 'Verschwommene Füllung (sehr stark)',
    'conversion.remplissageMiroir': 'Spiegelfüllung',
    'conversion.remplissageNoir': 'Schwarze Füllung',
    'conversion.repertoireOrigine': 'Im Originalverzeichnis speichern',
    'conversion.repertoirePersonnalise': 'In benutzerdefiniertem Verzeichnis speichern',
    'conversion.successTraitement': '%d Datei(en) erfolgreich verarbeitet von %d',
    'conversion.suffixe': 'Dateinamensuffix',
    'conversion.supprimerSelection': 'Auswahl entfernen',
    'conversion.tousLesFichiers': 'Alle Dateien',
    'conversion.traitement': 'Verarbeitung %d/%d\: %s',
    'conversion.traitementTermine': 'Verarbeitung abgeschlossen!',
    'conversion.typeRemplissage': 'Fülltyp',
    'conversion.valider': 'Verarbeitung starten',
    
    # Autres actions
    'creerSousDossierDate': 'Unterordner mit Datum/Uhrzeit erstellen',
    'creerZipVisite': 'ZIP-Archiv der Tour erstellen',
    
    # Diaporama
    'diapo.ajouteImage': 'Bild hinzufügen',
    'diapo.couleurFond': 'Hintergrundfarbe',
    'diapo.delai': 'Verzögerung zwischen Folien',
    'diapo.efface': 'Diashow löschen',
    'diapo.entrerNom': 'Bitte geben Sie den Namen der Diashow ein',
    'diapo.erreur': 'Diashow - Namensfehler',
    'diapo.erreurNom': 'Eine Diashow mit demselben Namen existiert bereits',
    'diapo.libelleImage': 'Bildbeschriftung',
    'diapo.nouveau': 'Neue Diashow',
    'diapo.ordreParcours': 'Reihenfolge durchlaufen',
    'diapo.ordreRandom': 'Zufällige Reihenfolge',
    'diapo.panoramiquesFichiers': 'Panoramadateien',
    'diapo.selectionneImage': 'Wählen Sie ein Bild aus',
    'diapo.supprimerSelection': 'Auswahl löschen',
    'diapo.texteAlternatif': 'Alternativer Text',
    'diapo.titreDiapo': 'Diashowname',
    'diapo.titreNouveauHS': 'Neuer Hotspot für Diashow',
    'diapo.transition': 'Übergang',
    
    # Édition HTML
    'editehtml.charger': 'Laden',
    'editehtml.couleurPolice': 'Schriftfarbe',
    'editehtml.couleurTexte': 'Textfarbe',
    'editehtml.couperCalque': 'Ebene ausschneiden',
    'editehtml.deplacerMoins': 'Nach hinten verschieben',
    'editehtml.deplacerPlus': 'Nach vorne verschieben',
    'editehtml.dupliquerCalque': 'Ebene duplizieren',
    'editehtml.emplacement': 'Speicherort',
    'editehtml.fichier': 'HTML-Datei',
    'editehtml.html': 'HTML-Inhalt',
    'editehtml.insererImage': 'Bild einfügen',
    'editehtml.lien': 'Link',
    'editehtml.nouvelImage': 'Neues Bild hinzufügen',
    'editehtml.nouveauCalque': 'Neue Ebene',
    'editehtml.policeGrasse': 'Fette Schrift',
    'editehtml.policeItalique': 'Kursive Schrift',
    'editehtml.rotation': 'Drehung',
    'editehtml.sauver': 'Speichern',
    'editehtml.supprimerCalque': 'Ebene löschen',
    'editehtml.taille': 'Größe',
    'editehtml.taillePolice': 'Schriftgröße',
    'editehtml.texte': 'Text',
    'editehtml.titre': 'HTML-Editor',
    
    # Équirectangulaire/Cubique
    'equi.afficherApercu': 'Vorschau anzeigen',
    'equi.debutNomCubique': 'Anfang des kubischen Namens',
    'equi.debutNomPano': 'Anfang des Panoramanamens',
    'equi.efface': 'Umwandlung löschen',
    'equi.exporterCubique': 'In Kubisch exportieren',
    'equi.exporterEqui': 'In Äquidistant exportieren',
    'equi.fichierCubique': 'Kubische Datei',
    'equi.fichierPano': 'Panoramadatei',
    'equi.formatCubique': 'Kubisches Format',
    'equi.formatPano': 'Panoramaformat',
    'equi.hautCubique': 'Höhe kubischer Flächen',
    'equi.hautPano': 'Panoramahöhe',
    'equi.largCubique': 'Breite kubischer Flächen',
    'equi.largPano': 'Panoramabreite',
    'equi.nomRepertoireCubique': 'Name des kubischen Verzeichnisses',
    'equi.nomRepertoirePano': 'Name des Panoramaverzeichnisses',
    'equi.numeroPano': 'Panoramanummer',
    'equi.qualite': 'Qualität',
    'equi.repertoireCubique': 'Kubisches Verzeichnis',
    'equi.repertoirePano': 'Panoramaverzeichnis',
    'equi.transformationCouleur': 'Farbtransformation',
    
    # Erreurs
    'erreur': 'Fehler',
    'erreurAjoutImage': 'Fehler beim Hinzufügen des Bildes',
    'erreurChargementFichier': 'Fehler beim Laden der Datei',
    'erreurCreationDossier': 'Fehler beim Erstellen des Ordners',
    'erreurLectureImage': 'Fehler beim Lesen des Bildes',
    'erreurSauvegarde': 'Fehler beim Speichern',
    
    # Géolocalisation
    'geo.adresse': 'Adresse',
    'geo.altitude': 'Höhe',
    'geo.carte': 'Karte',
    'geo.coordonnees': 'Koordinaten',
    'geo.latitude': 'Breitengrad',
    'geo.longitude': 'Längengrad',
    'geo.position': 'Position',
    'geo.rechercher': 'Suchen',
    'geo.zoom': 'Zoom',
    
    # Hotspots
    'hs.afficheInfobulle': 'Tooltip anzeigen',
    'hs.couleurBordure': 'Rahmenfarbe',
    'hs.couleurFond': 'Hintergrundfarbe',
    'hs.epaisseurBordure': 'Rahmenstärke',
    'hs.forme': 'Form',
    'hs.hyperlien': 'Hyperlink',
    'hs.infobulle': 'Tooltip',
    'hs.opacite': 'Deckkraft',
    'hs.taille': 'Größe',
    'hs.texte': 'Text',
    'hs.titre': 'Titel',
    'hs.typeHotspot': 'Hotspot-Typ',
    
    # Interface
    'interface.appliquer': 'Anwenden',
    'interface.couleur': 'Farbe',
    'interface.hauteur': 'Höhe',
    'interface.image': 'Bild',
    'interface.largeur': 'Breite',
    'interface.opacite': 'Deckkraft',
    'interface.position': 'Position',
    'interface.visible': 'Sichtbar',
    
    # Menu
    'menu.aide': 'Hilfe',
    'menu.configuration': 'Konfiguration',
    'menu.editer': 'Bearbeiten',
    'menu.fichier': 'Datei',
    'menu.nouveau': 'Neu',
    'menu.outils': 'Werkzeuge',
    'menu.quitter': 'Beenden',
    'menu.recentrer': 'Neu zentrieren',
    
    # Outils
    'outils.conversionRatio': 'Konvertierung 2\:1-Verhältnis',
    'outils.redimensionnement': 'Größenänderung/Komprimierung',
    'outilsConversion': 'Konvertierungswerkzeuge',
    'outilsRedimensionnement': 'Bildgrößenänderung und -komprimierung',
    
    # Plan
    'plan.activerNord': 'Norden aktivieren',
    'plan.afficherPlan': 'Plan anzeigen',
    'plan.couleurFond': 'Hintergrundfarbe',
    'plan.image': 'Planbild',
    
    # Projet
    'projet.enregistrer': 'Projekt speichern',
    'projet.nouveau': 'Neues Projekt',
    'projet.ouvrir': 'Projekt öffnen',
    
    # Redimensionnement
    'redim.conserver': 'Seitenverhältnis beibehalten',
    'redim.hauteur': 'Höhe',
    'redim.largeur': 'Breite',
    'redim.qualite': 'Qualität',
    'redim.titre': 'Bildgrößenänderung',
    
    # Succès
    'succes': 'Erfolg',
    'succesEnregistrement': 'Erfolgreich gespeichert',
    
    # Visite
    'visite.charger': 'Tour laden',
    'visite.generer': 'Tour generieren',
    'visite.sauvegarder': 'Tour speichern',
    'visite.titre': 'Tourtitel',
}


def generate_german_file():
    """
    Génère un fichier avec les traductions allemandes manquantes
    """
    output_file = Path(__file__).parent.parent / "doc" / "TRADUCTIONS_ALLEMAND_MANQUANTES.properties"
    
    print("=" * 70)
    print("  GÉNÉRATION DES TRADUCTIONS ALLEMANDES MANQUANTES")
    print("=" * 70)
    print()
    print(f"📝 Génération de {len(GERMAN_TRANSLATIONS)} traductions...")
    print()
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# Traductions allemandes manquantes pour PanoVisu\n")
        f.write(f"# Généré automatiquement le {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}\n")
        f.write(f"# Total: {len(GERMAN_TRANSLATIONS)} clés\n")
        f.write("#\n")
        f.write("# Instructions:\n")
        f.write("# 1. Vérifiez les traductions ci-dessous\n")
        f.write("# 2. Copiez-les dans le fichier PanoVisu_de.properties\n")
        f.write("# 3. Relancez check-translations.py pour vérifier\n")
        f.write("\n")
        f.write("# " + "=" * 66 + "\n\n")
        
        # Grouper par catégorie
        categories = {}
        for key, value in sorted(GERMAN_TRANSLATIONS.items()):
            category = key.split('.')[0] if '.' in key else 'general'
            if category not in categories:
                categories[category] = []
            categories[category].append((key, value))
        
        # Écrire par catégorie
        for category, items in sorted(categories.items()):
            f.write(f"# {category.upper()}\n")
            f.write(f"# {len(items)} clés\n\n")
            
            for key, value in items:
                f.write(f"{key}={value}\n")
            
            f.write("\n")
    
    print(f"✅ Fichier généré: {output_file.relative_to(output_file.parent.parent)}")
    print()
    print("📋 Traductions par catégorie:")
    for category, items in sorted(categories.items()):
        print(f"   {category:20} - {len(items):3} clés")
    print()
    print("=" * 70)
    print()
    print("📌 PROCHAINES ÉTAPES:")
    print()
    print("1. Vérifiez le fichier généré:")
    print(f"   {output_file}")
    print()
    print("2. Copiez les traductions dans:")
    print("   src/editeurpanovisu/i18n/PanoVisu_de.properties")
    print()
    print("3. Relancez l'analyse:")
    print("   python scripts/check-translations.py")
    print()
    print("=" * 70)


if __name__ == '__main__':
    generate_german_file()
