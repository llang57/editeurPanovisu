#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script complet pour :
1. Nettoyer les 208 clés en trop dans l'allemand
2. Compléter toutes les traductions manquantes
"""

from pathlib import Path
from translation_functions import clean_properties_file, add_translations, create_spanish_file
from translations_pt import PORTUGUESE_TRANSLATIONS

# Chemins
I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"

# 1. LISTE DES CLÉS À SUPPRIMER DE L'ALLEMAND (208 clés obsolètes)
KEYS_TO_REMOVE = [
    "diapo.ordreParcours", "diapo.ordreRandom", "diapo.panoramiquesFichiers",
    "diapo.selectionneImage", "diapo.supprimerSelection", "diapo.texteAlternatif",
    "diapo.titreDiapo", "diapo.titreNouveauHS", "diapo.transition",
    "editehtml.charger", "editehtml.couleurPolice", "editehtml.couleurTexte",
    "editehtml.couperCalque", "editehtml.deplacerMoins", "editehtml.deplacerPlus",
    "editehtml.dupliquerCalque", "editehtml.emplacement", "editehtml.fichier",
    "editehtml.html", "editehtml.insererImage", "editehtml.lien",
    "editehtml.nouveauCalque", "editehtml.nouvelImage", "editehtml.policeGrasse",
    "editehtml.policeItalique", "editehtml.rotation", "editehtml.sauver",
    "editehtml.supprimerCalque", "editehtml.taille", "editehtml.taillePolice",
    "editehtml.texte", "editehtml.titre", "equi.afficherApercu",
    "equi.debutNomCubique", "equi.debutNomPano", "equi.efface",
    "equi.exporterCubique", "equi.exporterEqui", "equi.fichierCubique",
    "equi.fichierPano", "equi.formatCubique", "equi.formatPano",
    "equi.hautCubique", "equi.hautPano", "equi.largCubique",
    "equi.largPano", "equi.nomRepertoireCubique", "equi.nomRepertoirePano",
    "equi.numeroPano", "equi.qualite", "equi.repertoireCubique",
    "equi.repertoirePano", "equi.transformationCouleur", "erreurAjoutImage",
    "erreurChargementFichier", "erreurCreationDossier", "erreurLectureImage",
    "erreurSauvegarde", "geo.adresse", "geo.altitude", "geo.carte",
    "geo.coordonnees", "geo.latitude", "geo.longitude", "geo.position",
    "geo.rechercher", "geo.zoom", "hs.afficheInfobulle", "hs.couleurBordure",
    "hs.couleurFond", "hs.epaisseurBordure", "hs.forme", "hs.hyperlien",
    "hs.infobulle", "hs.opacite", "hs.taille", "hs.texte", "hs.titre",
    "hs.typeHotspot", "interface.appliquer", "interface.couleur",
    "interface.hauteur", "interface.image", "interface.largeur",
    "interface.opacite", "interface.visible", "main.affiMasque",
    "main.afficheBarrePersonnalisee", "main.choixImageHS", "main.choixPolice",
    "main.choixTelechargement", "main.cibleChoisir", "main.couleurFondPano",
    "main.diapoHS", "main.divCreationRapide", "main.echelleMaxPlan",
    "main.echelleMinPlan", "main.editeZone", "main.effaceMarqueur",
    "main.genereHTML", "main.genererIA", "main.hauteurImage",
    "main.imageChoisir", "main.imageHS", "main.infoHS", "main.largeurImage",
    "main.latitude", "main.longitude", "main.marqueurGeo", "main.masque",
    "main.modeDebug", "main.multiReso", "main.ordreAffichage", "main.pdf",
    "main.polygon", "main.positionPano", "main.rayonImage", "main.resetVisite",
    "main.rotation", "main.sourisSurvol", "main.supprimerIA", "main.survol",
    "main.textAlter", "main.titreMarqueur", "main.transitionEntree",
    "main.transitionSortie", "main.typeImage", "main.typeMarqueur",
    "main.vitesse", "main.zoomMaxPano", "main.zoomMinPano", "menu.aide",
    "menu.configuration", "menu.editer", "menu.fichier", "menu.nouveau",
    "menu.outils", "menu.quitter", "menu.recentrer",
    "menuContextuelHotspot.afficher", "menuContextuelHotspot.cacher",
    "menuContextuelHotspot.dupliquer", "menuContextuelHotspot.editer",
    "menuContextuelHotspot.supprimer", "menuContextuelHs.afficherInfobulle",
    "menuContextuelHs.changerImage", "menuContextuelHs.couleurBordure",
    "menuContextuelHs.couleurFond", "menuContextuelHs.opacite",
    "outils.conversionRatio", "outils.redimensionnement", "outilsConversion",
    "pano.chargeImage", "pano.genererHTML", "pano.masquer", "pano.modele",
    "pano.multiReso", "pano.ordreAffichage", "panoramique.charger",
    "panoramique.genererVisite", "panoramique.ordre", "panoramique.supprimer",
    "panoramique.titre", "plan.activerNord", "plan.afficherPlan",
    "plan.couleurFond", "plan.image", "projet.enregistrer", "projet.nouveau",
    "projet.ouvrir", "redim.ajouter", "redim.appliquer", "redim.conserver",
    "redim.format", "redim.hauteur", "redim.information", "redim.largeur",
    "redim.parcourir", "redim.pixels", "redim.pourcentage", "redim.qualite",
    "redim.qualiteJpeg", "redim.titre", "redim.traiterImages",
    "sauvegarderVisite", "selectionnerFichier", "selectionnerRepertoire",
    "succes", "succesEnregistrement", "visite.aPropos", "visite.auteur",
    "visite.charger", "visite.chargerModele", "visite.configurationGenerale",
    "visite.copyright", "visite.date", "visite.description", "visite.export",
    "visite.generationEnCours", "visite.generer", "visite.langue",
    "visite.modele", "visite.mots", "visite.nom", "visite.parametres",
    "visite.sauvegarder", "visite.sauverModele", "visite.titre"
]

# 2. TRADUCTIONS FRANÇAISES (1 clé manquante réelle - l'autre est un commentaire)
FRENCH_TRANSLATIONS = {
    "build.numero": "2\u00a0666"
}

# 3. TRADUCTIONS ANGLAISES EXPLICITES (103 clés)
ENGLISH_TRANSLATIONS = {
    "aideDocumentation": "Documentation (F1)",
    "ajouterFichiers": "Add files",
    "annuler": "Cancel",
    "aucunRepertoireSelectionne": "No directory selected",
    "build.numero": "2\u00a0666",
    "choisir": "Choose",
    "choisirRepertoireSortie": "Choose output directory",
    "confirmation": "Confirmation",
    "confirmerAnnulation": "Do you really want to cancel the processing?",
    "conserverRatio": "Keep aspect ratio",
    "conversion.ajouterFichiers": "Add files",
    "conversion.annuler": "Cancel",
    "conversion.bas": "Bottom",
    "conversion.centre": "Center",
    "conversion.choisirImages": "Select images",
    "conversion.choisirRepertoire": "Choose output directory",
    "conversion.dragDropImages": "Drop your images here or click to select files",
    "conversion.ecraserOrigine": "Overwrite original files",
    "conversion.erreur": "Error",
    "conversion.erreurAucunFichier": "No files selected",
    "conversion.erreurRepertoireVide": "Please choose an output directory",
    "conversion.erreurTraitement": "Error processing %s\\: %s",
    "conversion.fichiersATraiter": "Files to process",
    "conversion.formatSortie": "Output format",
    "conversion.haut": "Top",
    "conversion.imagesSupporte": "Supported images",
    "conversion.information": "Information",
    "conversion.optionsSauvegarde": "Save options",
    "conversion.parcourir": "Browse",
    "conversion.positionnementVertical": "Vertical positioning",
    "conversion.previsualisation": "Preview",
    "conversion.qualiteJpeg": "JPEG Quality",
    "conversion.regenerer": "Regenerate",
    "conversion.remplissageCouleurMoyenne": "Average color filling",
    "conversion.remplissageFlou": "Blurred filling (very strong)",
    "conversion.remplissageMiroir": "Mirror filling",
    "conversion.remplissageNoir": "Black filling",
    "conversion.repertoireOrigine": "Save in original directory",
    "conversion.repertoirePersonnalise": "Save in custom directory",
    "conversion.successTraitement": "%d file(s) successfully processed out of %d",
    "conversion.suffixe": "Filename suffix",
    "conversion.supprimerSelection": "Remove selection",
    "conversion.tousLesFichiers": "All files",
    "conversion.traitement": "Processing %d/%d\\: %s",
    "conversion.traitementTermine": "Processing completed!",
    "conversion.typeRemplissage": "Filling type",
    "conversion.valider": "Start processing",
    "creerSousDossierDate": "Create subfolder with date/time",
    "creerZipVisite": "Create ZIP archive of tour",
    "dimensions": "Dimensions",
    "dragDropImages": "Drop your images here",
    "ecraserFichiersOrigine": "Overwrite original files",
    "erreur": "Error",
    "erreurAucunFichier": "No files selected",
    "erreurAucunFormat": "No output format selected",
    "erreurAucunRepertoire": "No output directory selected",
    "erreurHauteurInvalide": "Invalid height (must be a positive number)",
    "erreurLargeurInvalide": "Invalid width (must be a positive number)",
    "erreurSuffixeVide": "Please enter a suffix for filenames (or check 'Overwrite original files')",
    "fichiersATraiter": "Files to process",
    "fichiersImages": "Image files",
    "formatSortie": "Output format",
    "hauteur": "Height",
    "interface.afficheDescriptionChargement": "Show panoramic description on loading",
    "interface.description": "Description",
    "largeur": "Width",
    "main.descriptionIA": "AI Description",
    "main.descriptionIAAvertissement": "\\u26a0 Always verify AI-generated information",
    "main.descriptionIADonneesManquantes": "Missing data",
    "main.descriptionIADonneesManquantesMsg": "To generate an AI description, you must provide at least:\\n\\n- The tour title, OR\\n- The panoramic title, OR\\n- Geolocation (GPS coordinates)\\n\\nPlease fill in at least one of these fields.",
    "main.descriptionIAErreur": "Generation error",
    "main.descriptionIAGeneration": "Generating...",
    "main.descriptionIAOllamaIndisponible": "AI service unavailable",
    "main.descriptionIAOllamaIndisponibleMsg": "No AI service is currently available.\\n\\nTwo free options:\\n\\nOPTION 1 - Ollama (local, fast, offline):\\n1. Download: https://ollama.ai\\n2. Install and run: ollama pull mistral\\n\\nOPTION 2 - Hugging Face (online, free):\\n1. Create free account: https://huggingface.co/join\\n2. Get token: https://huggingface.co/settings/tokens\\n3. Configure token in launch options:\\n   -Dhuggingface.token=YOUR_TOKEN",
    "main.descriptionIAPlaceholder": "Generated AI description will appear here...",
    "main.genererDescriptionIA": "Generate AI description",
    "nbFichiersATraiter": "%d file(s) ready to process",
    "nbImagesTraitees": "%d image(s) processed successfully",
    "optionsSauvegarde": "Save Options",
    "ouCliquez": "or click to select files",
    "outilsConversionRatio2to1": "Convert to 2\\:1 Ratio",
    "outilsRatio2to1": "Convert to 2:1 Ratio",
    "outilsRedimensionnement": "Resize/Compress Images",
    "outilsTraitementImages": "Image Processing",
    "pretATraiter": "%d file(s) ready to process in \"%s\"",
    "qualiteJpeg": "JPEG Quality",
    "repertoireSortie": "Output directory",
    "sauverRepertoireOrigine": "Save in original directory",
    "sauverRepertoirePersonnalise": "Save in custom directory",
    "selectionnerImages": "Select images",
    "suffixeNomFichier": "Filename suffix",
    "supprimerSelection": "Remove selection",
    "tooltipEcraser": "Warning: Original files will be permanently replaced",
    "tooltipSousDossier": "Creates a subfolder named with the current date and time",
    "tooltipSuffixe": "Suffix added to filenames (e.g., \"_redim\" -> image_redim.jpg)",
    "traitementEchec": "Processing failed",
    "traitementEnCours": "Processing...",
    "traitementImage": "Processing %d/%d: %s",
    "traitementReussi": "Success",
    "traitementTermine": "Processing completed!",
    "valider": "Validate",
    "viderListe": "Clear list"
}

def main():
    print("=" * 70)
    print("  COMPLETION DE TOUTES LES TRADUCTIONS")
    print("=" * 70)
    print()
    
    # 1. Nettoyer l'allemand
    print("1️⃣  NETTOYAGE DE L'ALLEMAND...")
    german_file = I18N_DIR / "PanoVisu_de.properties"
    removed = clean_properties_file(german_file, KEYS_TO_REMOVE)
    print(f"   ✅ {removed} clés obsolètes supprimées")
    print()
    
    # 2. Compléter le français
    print("2️⃣  FRANÇAIS...")
    french_file = I18N_DIR / "PanoVisu_fr.properties"
    added = add_translations(french_file, FRENCH_TRANSLATIONS)
    print(f"   ✅ {added} traduction(s) ajoutée(s)")
    print()
    
    # 3. Compléter l'anglais explicite
    print("3️⃣  ANGLAIS EXPLICITE...")
    english_file = I18N_DIR / "PanoVisu_en.properties"
    added = add_translations(english_file, ENGLISH_TRANSLATIONS)
    print(f"   ✅ {added} traduction(s) ajoutée(s)")
    print()
    
    # 4. Compléter le portugais
    print("4️⃣  PORTUGAIS...")
    portuguese_file = I18N_DIR / "PanoVisu_pt.properties"
    added = add_translations(portuguese_file, PORTUGUESE_TRANSLATIONS)
    print(f"   ✅ {added} traduction(s) ajoutée(s)")
    print()
    
    # 5. Créer l'espagnol
    print("5️⃣  ESPAGNOL (CRÉATION COMPLÈTE)...")
    lines = create_spanish_file()
    print(f"   ✅ Fichier créé avec {lines} traductions")
    print()
    
    print("=" * 70)
    print("✅ TOUTES LES TRADUCTIONS SONT COMPLÈTES!")
    print("=" * 70)
    print()
    print("📌 VÉRIFICATION:")
    print("   Relancez: python scripts/check-translations.py")
    print()

if __name__ == '__main__':
    main()
