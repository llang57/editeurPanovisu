#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Finalise les traductions espagnoles - 195 clés restantes
"""

from pathlib import Path

I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"

# Les 195 dernières traductions espagnoles
SPANISH_FINAL = {
    "interface.barrePersonnaliseeCouleurPersonnalisee": "Color personalizado",
    "interface.barrePersonnaliseeLien": "Elecci\\u00f3n",
    "interface.barrePersonnaliseeTaille": "Tama\\u00f1o",
    "interface.barrePersonnaliseeTailleIcones": "Tama\\u00f1o de iconos",
    "interface.barrePersonnaliseeVisible": "mostrar la barra personalizada",
    "interface.barrePresonnaliseeEditer": "Editando",
    "interface.barreVisible": "mostrar la barra cl\\u00e1sica",
    "interface.boussole": "Br\\u00fajula",
    "interface.boutonVisiteAuto": "Bot\\u00f3n de tour autom\\u00e1tico",
    "interface.carte": "Mapa",
    "interface.chercheAdresse": "Buscar",
    "interface.choixCentreCarte": "Elecci\\u00f3n del Centro",
    "interface.choixCouleur": "color del t\\u00edtulo",
    "interface.choixCouleurDiaporama": "Color de fondo",
    "interface.choixCouleurFond": "Color de fondo del t\\u00edtulo",
    "interface.choixCouleurFondVignettes": "Color de fondo",
    "interface.choixCouleurTexteVignettes": "Color del texto",
    "interface.choixHotspot": "Hotspots",
    "interface.choixImgBoussole": "Elegir la imagen de fondo",
    "interface.choixImgMasque": "Elegir la imagen del bot\\u00f3n",
    "interface.choixOpaciteDiaporama": "Opacidad",
    "interface.choixOpaciteTitre": "Opacidad del t\\u00edtulo",
    "interface.choixPolice": "Elegir Fuente",
    "interface.choixPositBoussole": "Posici\\u00f3n de la br\\u00fajula",
    "interface.choixPositBoutonVisiteAuto": "Posici\\u00f3n",
    "interface.choixPositComboMenu": "Posici\\u00f3n",
    "interface.choixPositMasque": "Posici\\u00f3n",
    "interface.choixPositReseauxSociaux": "Posici\\u00f3n",
    "interface.choixPositVignettes": "Posici\\u00f3n",
    "interface.choixTailleBoutonVisiteAuto": "Tama\\u00f1o",
    "interface.choixTaillePolice": "Tama\\u00f1o de la fuente",
    "interface.choixTailleTitre": "tama\\u00f1o de la barra de t\\u00edtulo (%)",
    "interface.comboMenu": "Cuadro combinado del tour",
    "interface.couleurBarre": "Color de la barra de navegaci\\u00f3n",
    "interface.couleurFondCarte": "color de fondo",
    "interface.couleurFondPlan": "color de fondo",
    "interface.couleurFondRadar": "color de fondo del radar",
    "interface.couleurFondRadarCarte": "color de fondo del radar",
    "interface.couleurHS": "color de los Hotspots panor\\u00e1micos",
    "interface.couleurHSHTML": "color de los Hotspots html",
    "interface.couleurHSPhoto": "color de los Hotspots de imagen",
    "interface.couleurLigneRadar": "color de la l\\u00ednea del radar",
    "interface.couleurLigneRadarCarte": "color de la l\\u00ednea del radar",
    "interface.couleurMasque": "color del bot\\u00f3n",
    "interface.couleurTexteCarte": "color del texto",
    "interface.couleurTextePlan": "color del texto",
    "interface.deplacementsVisible": "barra de navegaci\\u00f3n",
    "interface.diaporama": "Presentaci\\u00f3n",
    "interface.espacementBoutons": "Espaciado de botones",
    "interface.fenetreAidePersonnalise": "Ventana de Ayuda personalizada",
    "interface.fenetreImage": "Elecci\\u00f3n de imagen",
    "interface.fenetreInfoPersonnalise": "Ventana de informaci\\u00f3n personalizada",
    "interface.fenetreLibelleURL": "Etiqueta de URL",
    "interface.fenetreOpacite": "Opacidad",
    "interface.fenetrePoliceTaille": "Tama\\u00f1o de Fuente",
    "interface.fenetrePosX": "Desplazamiento X",
    "interface.fenetrePosY": "Desplazamiento Y",
    "interface.fenetreTaille": "Tama\\u00f1o de ventana",
    "interface.fenetreURLChoixCouleur": "Color de etiqueta de URL",
    "interface.hauteurCarte": "altura",
    "interface.imageFond": "Logos/Im\\u00e1genes de fondo",
    "interface.imageFondAjoute": "A\\u00f1adir una imagen",
    "interface.imageFondRetire": "Eliminar una imagen",
    "interface.infobulle": "tooltip",
    "interface.largeurCarte": "Ancho",
    "interface.largeurPlan": "Ancho",
    "interface.lienBarrePersonalisee": "Enlace",
    "interface.masquableImageFond": "Ocultable",
    "interface.masque": "Bot\\u00f3n de ocultar",
    "interface.masqueBoussole": "Ocultar la br\\u00fajula",
    "interface.masqueCombo": "Ocultar men\\u00fa combinado",
    "interface.masqueHotspots": "Ocultar hotspots",
    "interface.masqueNavigation": "Ocultar la barra de Navegaci\\u00f3n",
    "interface.masquePlan": "Ocultar el mapa",
    "interface.masqueReseaux": "Ocultar redes sociales",
    "interface.masqueSuivPrec": "Ocultar botones Siguiente/Anterior",
    "interface.masqueTitre": "Ocultar el t\\u00edtulo",
    "interface.masqueVignettes": "Ocultar miniaturas",
    "interface.menuContextuel": "Men\\u00fa contextual",
    "interface.menuContextuelLib": "Redacci\\u00f3n",
    "interface.menuContextuelPers1": "men\\u00fa personalizado 1",
    "interface.menuContextuelPers2": "men\\u00fa personalizado 2",
    "interface.menuContextuelPlaneteNormal": "little planet/vista normal",
    "interface.menuContextuelSuivPrec": "panorama siguiente/anterior",
    "interface.menuContextuelURL": "URL",
    "interface.opaciteBoussole": "Opacidad de la br\\u00fajula",
    "interface.opaciteCarte": "opacidad del fondo del mapa",
    "interface.opaciteMasque": "Opacidad",
    "interface.opacitePlan": "opacidad del fondo del plano",
    "interface.opaciteRadar": "opacidad del radar",
    "interface.opaciteRadarCarte": "opacidad del radar",
    "interface.opaciteReseauxSociaux": "Opacidad",
    "interface.opaciteVignettes": "opacidad",
    "interface.outilsFS": "Bot\\u00f3n de Pantalla completa",
    "interface.outilsRotation": "Bot\\u00f3n de Autorrotaci\\u00f3n",
    "interface.outilsSouris": "Bot\\u00f3n de modo de rat\\u00f3n",
    "interface.outilsVisible": "barra de herramientas",
    "interface.plan": "Plano",
    "interface.position": "posici\\u00f3n de la barra de botones",
    "interface.positionImageFond": "Posici\\u00f3n",
    "interface.recentreCarte": "Recentrar mapa",
    "interface.replie": "Plegado al inicio",
    "interface.reseauxSociaux": "Compartir",
    "interface.style": "estilo de barra de navegaci\\u00f3n",
    "interface.styleFenetreAide": "Ventana de Ayuda",
    "interface.styleFenetreInfo": "Ventana de Informaci\\u00f3n",
    "interface.styleTitre": "T\\u00edtulo",
    "interface.tailleBoussole": "Tama\\u00f1o de la br\\u00fajula",
    "interface.tailleMasque": "Tama\\u00f1o",
    "interface.tailleRadar": "tama\\u00f1o del radar",
    "interface.tailleRadarCarte": "tama\\u00f1o del radar",
    "interface.tailleReseauxSociaux": "Tama\\u00f1o",
    "interface.tailleVignettes": "Tama\\u00f1o de im\\u00e1genes",
    "interface.vignettes": "Miniaturas",
    "interface.visualiseDiaporama": "Visualizaci\\u00f3n",
    "interface.zoomCarte": "Zoom",
    "interface.zoomVisible": "barra de zoom",
    "main.ajouteZone": "A\\u00f1adir una zona",
    "main.annuler": "Cancelar",
    "main.attendsChargement": "Por favor, espere mientras se carga",
    "main.cercle": "C\\u00edrculo",
    "main.chargeImage": "Elegir la barra",
    "main.creationInterface": "Plantilla de interfaz",
    "main.creationVisite": "Tour",
    "main.creeBarrePersonnalisee": "Crear/Editar barra de navegaci\\u00f3n personalizada",
    "main.dialog.chargeProjet": "Cargar un Proyecto",
    "main.dialog.chargeProjetMessage": "No guard\\u00f3 su proyecto\\n\\u00bfDesea guardarlo?",
    "main.dialog.generationVisite": "Generaci\\u00f3n del tour",
    "main.dialog.generationVisiteMessage": "Su tour ha sido generado en el directorio \\:",
    "main.dialog.generationVisiteMessageErreur": "Su Tour no ha sido generado, guarde su proyecto antes",
    "main.dialog.nouveauProjet": "Nuevo proyecto",
    "main.dialog.quitterApplication": "Salir de la aplicaci\\u00f3n",
    "main.dialog.sauveModele": "Guardar el archivo de plantilla",
    "main.dialog.sauveModeleMessage": "Su plantilla ha sido guardada",
    "main.dialog.sauveProjet": "Guardar un proyecto",
    "main.dialog.sauveProjetMessage": "Su proyecto ha sido guardado",
    "main.dragDrop": "Puede soltar su imagen panor\\u00e1mica aqu\\u00ed",
    "main.edite": "Edici\\u00f3n HTML",
    "main.etesVousSur": "Advertencia, est\\u00e1 a punto de eliminar \\nel panorama actual \\n\\u00bfEst\\u00e1 seguro de que desea continuar?",
    "main.geolocalisation": "Geolocalizaci\\u00f3n",
    "main.imageChoisie": "Imagen elegida",
    "main.legendeHS": "Tour HotSpot\\n\\n\\n\\n-clic izquierdo para a\\u00f1adir o editar un Hotspot\\n         puede elegir el tipo de hotspot en el men\\u00fa contextual\\n-Ctrl + clic izquierdo para eliminar un HotSpot",
    "main.legendeHSHTML": "Hotspot HTML",
    "main.legendeHSImage": "Hotspot de imagen",
    "main.legendeNord": "Posici\\u00f3n del norte \\: - Shift+clic derecho para elegir la posici\\u00f3n",
    "main.legendePoV": "Punto de vista \\: -clic derecho del rat\\u00f3n para elegir el Punto de vista",
    "main.masqueZones": "Ocultar zonas",
    "main.panoAffiche": "Elecci\\u00f3n del panorama a mostrar",
    "main.panoEntree": "orden de panor\\u00e1micas (puede reordenarlas por arrastrar y soltar)",
    "main.polygone": "Pol\\u00edgono",
    "main.quitter": "Salir",
    "main.rectangle": "Rect\\u00e1ngulo",
    "main.sauver": "Guardar",
    "main.supprimePano": "Eliminar panorama actual del tour",
    "main.supprimerPano": "Eliminaci\\u00f3n del Panorama actual",
    "main.tabPlan": "Plano del tour",
    "main.titrePano": "T\\u00edtulo del panorama",
    "main.titreVisite": "T\\u00edtulo del tour",
    "main.typeZone": "tipo de zona",
    "main.valider": "Ok",
    "menuModele": "Plantillas",
    "modeleCharger": "Cargar plantilla",
    "modeleSauver": "Guardar plantilla",
    "navigateur.choixPOV": "Punto de Vista",
    "navigateur.nord": "Norte",
    "nouveauProjet": "Nuevo proyecto",
    "outils": "Herramientas/Transformaciones",
    "outilsBarre": "Editar/Crear una barra de navegaci\\u00f3n",
    "outilsCube2Equi": "caras de cubo > Equi",
    "outilsEqui2Cube": "Equi > caras de cubo",
    "ouvrirProjet": "Abrir proyecto",
    "panoramiques": "Tour",
    "plan.choixPlan": "Elecci\\u00f3n del plano a mostrar",
    "plan.dragDrop": "Puede soltar sus planos aqu\\u00ed",
    "plan.etesVousSur": "Advertencia, est\\u00e1 a punto \\nde eliminar el plano actual \\n\\u00bfest\\u00e1 seguro de que desea continuar?",
    "plan.panoAffichePlan": "Panoramas que muestran el plano",
    "plan.positXBoussolePlan": "dX",
    "plan.positYBoussolePlan": "dY",
    "plan.positionBoussolePlan": "posici\\u00f3n de la br\\u00fajula del plano",
    "plan.positionNordPlan": "Posici\\u00f3n del Norte",
    "plan.supprimerPlan": "Eliminar el plano actual",
    "plan.valideCB": "Desmarcar todo/marcar todo",
    "police.annuler": "Cancelar",
    "projets": "Proyectos",
    "quitterApplication": "Salir",
    "sauverProjet": "Guardar proyecto",
    "sauverProjetSous": "Guardar proyecto como...",
    "transformation.dragDropC2E": "Puede depositar sus caras de cubos aqu\\u00ed",
    "transformation.dragDropE2C": "Puede depositar sus equirectangulares aqu\\u00ed",
    "transformation.traiteImages": "Procesamiento de im\\u00e1genes",
    "transformation.traiteImagesMessage": "Advertencia, el proceso puede durar varios minutos\\n Por favor, espere hasta el final del proceso.\\n\\nGracias",
    "transformation.traiteImagesPasFichiers": "No eligi\\u00f3 im\\u00e1genes para procesar",
    "transformation.traiteImagesQuitte": "Las im\\u00e1genes no fueron procesadas\\n\\u00bfEst\\u00e1 seguro de que desea salir?",
    "transformation.traiteImagesType": "Algunos de los archivos que eligi\\u00f3 tienen un tama\\u00f1o/tipo incorrecto."
}

def finalize_spanish():
    """Ajoute les 195 dernières traductions espagnoles"""
    spanish_file = I18N_DIR / "PanoVisu_es_ES.properties"
    
    # Lire le fichier actuel
    with open(spanish_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    # Trouver les clés existantes
    existing_keys = set()
    for line in lines:
        if '=' in line and not line.strip().startswith('#'):
            key = line.split('=')[0].strip()
            existing_keys.add(key)
    
    # Ajouter les traductions manquantes
    additions = []
    for key, value in sorted(SPANISH_FINAL.items()):
        if key not in existing_keys:
            additions.append(f'{key}={value}')
    
    if additions:
        # Ajouter à la fin du fichier
        with open(spanish_file, 'a', encoding='utf-8') as f:
            f.write('\n# Traductions finales - 195 derni\\u00e8res cl\\u00e9s\n')
            f.write('\n'.join(additions))
            f.write('\n')
        
        print(f"\\u2705 {len(additions)} traductions espagnoles finales ajout\\u00e9es")
    else:
        print("\\u2705 Aucune traduction manquante")
    
    return len(additions)

if __name__ == '__main__':
    print("=" * 70)
    print("  FINALISATION DES TRADUCTIONS ESPAGNOLES")
    print("=" * 70)
    print()
    finalize_spanish()
    print()
    print("=" * 70)
