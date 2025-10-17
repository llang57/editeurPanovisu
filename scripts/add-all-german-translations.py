#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script final pour ajouter toutes les traductions manquantes au fichier allemand
"""

from pathlib import Path

# Chemin des fichiers
GERMAN_FILE = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n" / "PanoVisu_de.properties"

# TOUTES les traductions à ajouter (complément + finales + TODO traduits)
ALL_NEW_TRANSLATIONS = """
# ===================================================================
# TRADUCTIONS COMPL\u00c9MENTAIRES - Version 3.2.0
# Ajout\u00e9 automatiquement - Traductions compl\u00e8tes
# ===================================================================

# DIAPO - Compl\u00e9ment
diapo.opacite=Deckkraft
diapo.sauver=Diashow best\u00e4tigen
diapo.sauverTexte=M\u00f6chten Sie die Diashow best\u00e4tigen?
diapo.supprimeImage=Bild entfernen
diapo.supprimerImage=Bild l\u00f6schen
diapo.supprimerImageTexte=M\u00f6chten Sie das aktuelle Bild wirklich l\u00f6schen?
diapo.visualise=Diashow anzeigen

# EDITEURHTML - Compl\u00e9ment
editeurHTML.couleurFond=HTML-Hintergrundfarbe
editeurHTML.couleurTexte=Textfarbe

# GENERAL - Traductions compl\u00e8tes
dimensions=Abmessungen
dragDropImages=Bilder hier ablegen
ecraserFichiersOrigine=Originaldateien \u00fcberschreiben
erreurAucunFichier=Keine Dateien ausgew\u00e4hlt
erreurAucunFormat=Kein Ausgabeformat ausgew\u00e4hlt
erreurAucunRepertoire=Kein Ausgabeverzeichnis ausgew\u00e4hlt
erreurHauteurInvalide=Ung\u00fcltige H\u00f6he (muss eine positive Zahl sein)
erreurLargeurInvalide=Ung\u00fcltige Breite (muss eine positive Zahl sein)
erreurSuffixeVide=Bitte geben Sie ein Suffix f\u00fcr Dateinamen ein
fichiersATraiter=Zu verarbeitende Dateien
fichiersImages=Bilddateien
formatSortie=Ausgabeformat
hauteur=H\u00f6he
largeur=Breite
nbFichiersATraiter=%d Datei(en) bereit zur Verarbeitung
nbImagesTraitees=%d Bild(er) erfolgreich verarbeitet
optionsSauvegarde=Speicheroptionen
ouCliquez=oder klicken Sie, um Dateien auszuw\u00e4hlen
outilsConversionRatio2to1=Konvertierung ins 2:1-Verh\u00e4ltnis
outilsDiaporama=Diashow bearbeiten/erstellen
outilsRatio2to1=Konvertierung ins 2:1-Verh\u00e4ltnis
outilsTraitementImages=Bildverarbeitung
pretATraiter=%d Datei(en) bereit zur Verarbeitung in "%s"
qualiteJpeg=JPEG-Qualit\u00e4t
repertoireSortie=Ausgabeverzeichnis
sauvegarderVisite=Tour speichern
sauverRepertoireOrigine=Im Originalverzeichnis speichern
sauverRepertoirePersonnalise=In benutzerdefiniertem Verzeichnis speichern
selectionnerFichier=Datei ausw\u00e4hlen
selectionnerImages=Bilder ausw\u00e4hlen
selectionnerRepertoire=Verzeichnis ausw\u00e4hlen
suffixeNomFichier=Dateinamensuffix
supprimerSelection=Auswahl entfernen
tooltipEcraser=Warnung: Originaldateien werden dauerhaft ersetzt
tooltipSousDossier=Erstellt einen Unterordner mit aktuellem Datum und Uhrzeit
tooltipSuffixe=Suffix, das an Dateinamen angeh\u00e4ngt wird (z.B. "_redim" -> bild_redim.jpg)
traitementEchec=Verarbeitung fehlgeschlagen
traitementEnCours=Verarbeitung l\u00e4uft...
traitementImage=Verarbeitung %d/%d: %s
traitementReussi=Erfolg
traitementTermine=Verarbeitung abgeschlossen!
valider=Best\u00e4tigen
viderListe=Liste leeren

# INTERFACE - Compl\u00e9ment
interface.afficheDescriptionChargement=Panoramabeschreibung beim Laden anzeigen
interface.calque=Ebene
interface.calques=Ebenen
interface.calquesTous=Alle/Keine
interface.changeImage=Bild \u00e4ndern
interface.choixCarte=Kartentyp
interface.choixCouleurBordure=Rahmenfarbe
interface.choixTitreAdapte=An Inhalt anpassen
interface.couleurFondTheme=Hintergrundfarbe
interface.couleurTexteTheme=Textfarbe
interface.description=Beschreibung
interface.lienCible=Ziel
interface.lienInterne=Intern
interface.lienPage=Neue Seite
interface.ombreInfoBulle=Tooltip-Schatten
interface.opaciteInfoBulle=Tooltip-Deckkraft
interface.opaciteTheme=Deckkraft
interface.policeTheme=Schriftart
interface.rayonBordure=Rahmenradius
interface.styleInfoBulle=Tooltip-Stil
interface.supprimeImage=Bild l\u00f6schen
interface.tailleBordure=Rahmenbreite
interface.theme=Allgemeine Eigenschaften

# MAIN - Suite et compl\u00e9ments
main.affiMasque=Maske anzeigen
main.afficheBarrePersonnalisee=Benutzerdefinierte Leiste anzeigen
main.autoRotationDemarrage=Autorotation beim Start
main.autoRotationVitesse=Autorotationsgeschwindigkeit
main.autoTour=Automatische Tour
main.autoTourChange=\u00c4nderungen nach
main.autoTourDemarrage=Autotour starten nach (s)
main.autoTourRotation=Autorotation / Automatische Tour
main.blocage=Auf alle Panoramen anwenden
main.blocageNadir=Nadir blockieren
main.blocageZenith=Zenit blockieren
main.choixDiapo=Diashow ausw\u00e4hlen
main.choixImageHS=Hotspot-Bild ausw\u00e4hlen
main.choixPolice=Schriftart w\u00e4hlen
main.choixTelechargement=Download-Auswahl
main.cibleChoisir=Ziel w\u00e4hlen
main.couleurFondPano=Panorama-Hintergrundfarbe
main.descriptionIA=KI-Beschreibung
main.descriptionIAAvertissement=\u26a0 \u00dcberpr\u00fcfen Sie immer KI-generierte Informationen
main.descriptionIADonneesManquantes=Fehlende Daten
main.descriptionIADonneesManquantesMsg=Um eine KI-Beschreibung zu generieren, m\u00fcssen Sie mindestens angeben:\\n\\n- Den Tourtitel, ODER\\n- Den Panoramatitel, ODER\\n- Geolokalisierung (GPS-Koordinaten)\\n\\nBitte f\u00fcllen Sie mindestens eines dieser Felder aus.
main.descriptionIAErreur=Generierungsfehler
main.descriptionIAGeneration=Generierung...
main.descriptionIAOllamaIndisponible=KI-Dienst nicht verf\u00fcgbar
main.descriptionIAOllamaIndisponibleMsg=Kein KI-Dienst ist derzeit verf\u00fcgbar.\\n\\nZwei kostenlose Optionen:\\n\\nOPTION 1 - Ollama (lokal, schnell, offline):\\n1. Download: https://ollama.ai\\n2. Installieren und ausf\u00fchren: ollama pull mistral\\n\\nOPTION 2 - Hugging Face (online, kostenlos):\\n1. Kostenloses Konto erstellen: https://huggingface.co/join\\n2. Token abrufen: https://huggingface.co/settings/tokens\\n3. Token in Startoptionen konfigurieren:\\n   -Dhuggingface.token=IHR_TOKEN
main.descriptionIAPlaceholder=Generierte KI-Beschreibung wird hier erscheinen...
main.dialog.panoInexistant=Datei nicht gefunden
main.dialog.panoInexistantTexte=Panoramabild nicht gefunden:
main.diapoHS=Diashow-Hotspot
main.diapos=Diashow
main.divCreationRapide=Schnellerstellung
main.echelleMaxPlan=Maximaler Planma\u00dfstab
main.echelleMinPlan=Minimaler Planma\u00dfstab
main.edite=HTML-Bearbeitung
main.editeHTML=HTML-Bearbeitung
main.editeZone=Bereich bearbeiten
main.effaceMarqueur=Markierung l\u00f6schen
main.genereHTML=HTML generieren
main.genererDescriptionIA=KI-Beschreibung generieren
main.genererIA=Mit KI generieren
main.hauteurImage=Bildh\u00f6he
main.imageChoisie=Gew\u00e4hltes Bild
main.imageChoisir=Bild w\u00e4hlen
main.imageHS=Hotspot-Bild
main.infoHS=Info-Hotspot
main.introPetitePlanete=Little Planet Intro
main.largeurImage=Bildbreite
main.latitude=Breitengrad
main.longitude=L\u00e4ngengrad
main.loupe=Lupe anzeigen/verbergen
main.marqueurGeo=Geolokalisierungsmarkierung
main.masque=Maske
main.modeDebug=Debug-Modus
main.modifiePanoCourant=Panoramabild \u00e4ndern
main.multiReso=Multi-Aufl\u00f6sung
main.nSecondes=n Sekunde(n)
main.nTours=n Runde(n)
main.nomDiapo=Diashowname
main.ordreAffichage=Anzeigereihenfolge
main.panoramiqueDestination=Zielpanorama
main.parTour=Sekunden/Runde
main.parametresPano=Panoramaansichtsparameter
main.parametresVisite=Tourparameter
main.pdf=PDF
main.polygon=Polygon
main.positionPano=Panoramaposition
main.rayonImage=Bildradius
main.rectangle=Rechteck
main.resetVisite=Tour zur\u00fccksetzen
main.rotation=Drehung
main.sourisSurvol=Maus-Hover
main.supprimePanoCourant=Angezeigtes Panorama l\u00f6schen
main.supprimerIA=KI-Beschreibung l\u00f6schen
main.survol=Hover
main.textAlter=Alternativer Text
main.texteHotspot=Hotspot-Text
main.titreMarqueur=Markierungstitel
main.transitionEntree=Eingangs\u00fcbergang
main.transitionSortie=Ausgangs\u00fcbergang
main.typeImage=Bildtyp
main.typeMarqueur=Markierungstyp
main.vignettes=Miniaturansichten
main.vitesse=Geschwindigkeit
main.zoomMaxPano=Maximaler Panorama-Zoom
main.zoomMinPano=Minimaler Panorama-Zoom

# MENUCONTEXTUELHOTSPOT
menuContextuelHotspot.afficher=Anzeigen
menuContextuelHotspot.cacher=Verbergen
menuContextuelHotspot.dupliquer=Duplizieren
menuContextuelHotspot.editer=Bearbeiten
menuContextuelHotspot.supprimer=L\u00f6schen

# MENUCONTEXTUELHS
menuContextuelHs.afficherInfobulle=Tooltip anzeigen
menuContextuelHs.changerImage=Bild \u00e4ndern
menuContextuelHs.couleurBordure=Rahmenfarbe
menuContextuelHs.couleurFond=Hintergrundfarbe
menuContextuelHs.opacite=Deckkraft

# PANORAMIQUE
panoramique.charger=Panorama laden
panoramique.genererVisite=Tour generieren
panoramique.ordre=Reihenfolge
panoramique.supprimer=Panorama l\u00f6schen
panoramique.titre=Panoramatitel

# PANO
pano.chargeImage=Bild laden
pano.genererHTML=HTML generieren
pano.masquer=Verbergen
pano.modele=Vorlage
pano.multiReso=Multi-Aufl\u00f6sung
pano.ordreAffichage=Anzeigereihenfolge

# REDIM - Compl\u00e9ment
redim.ajouter=Hinzuf\u00fcgen
redim.appliquer=Anwenden
redim.format=Format
redim.information=Information
redim.parcourir=Durchsuchen
redim.pixels=Pixel
redim.pourcentage=Prozentsatz
redim.qualiteJpeg=JPEG-Qualit\u00e4t
redim.traiterImages=Bilder verarbeiten

# VISITE - Compl\u00e9ment
visite.aPropos=\u00dcber
visite.auteur=Autor
visite.chargerModele=Vorlage laden
visite.configurationGenerale=Allgemeine Konfiguration
visite.copyright=Urheberrecht
visite.date=Datum
visite.description=Beschreibung
visite.export=Export
visite.generationEnCours=Generierung l\u00e4uft...
visite.langue=Sprache
visite.modele=Vorlage
visite.mots=Schl\u00fcsselw\u00f6rter
visite.nom=Tourenname
visite.parametres=Parameter
visite.sauverModele=Vorlage speichern

# VISU
visu.nonSupporte=Funktion nicht unterst\u00fctzt
"""


def add_translations_to_german_file():
    """
    Ajoute toutes les traductions au fichier allemand
    """
    print("=" * 70)
    print("  AJOUT DES TRADUCTIONS AU FICHIER ALLEMAND")
    print("=" * 70)
    print()
    
    # Lire le fichier allemand actuel
    with open(GERMAN_FILE, 'r', encoding='utf-8') as f:
        current_content = f.read()
    
    # Ajouter les nouvelles traductions
    new_content = current_content.rstrip() + "\n" + ALL_NEW_TRANSLATIONS
    
    # Écrire le fichier mis à jour
    with open(GERMAN_FILE, 'w', encoding='utf-8') as f:
        f.write(new_content)
    
    # Compter les traductions ajoutées
    lines_added = ALL_NEW_TRANSLATIONS.count('\n')
    translations_added = sum(1 for line in ALL_NEW_TRANSLATIONS.split('\n') 
                            if '=' in line and not line.strip().startswith('#'))
    
    print(f"✅ {translations_added} traductions ajout\u00e9es au fichier allemand")
    print(f"📄 {lines_added} lignes ajout\u00e9es (avec commentaires)")
    print()
    print(f"📁 Fichier mis \u00e0 jour: {GERMAN_FILE.relative_to(GERMAN_FILE.parent.parent.parent)}")
    print()
    print("=" * 70)
    print()
    print("📌 PROCHAINE \u00c9TAPE:")
    print()
    print("   Relancez l'analyse pour v\u00e9rifier:")
    print("   python scripts/check-translations.py")
    print()
    print("=" * 70)


if __name__ == '__main__':
    add_translations_to_german_file()
