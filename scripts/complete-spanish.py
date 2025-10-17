#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Complète le fichier espagnol avec TOUTES les traductions manquantes
"""

from pathlib import Path

I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"

# Dictionnaire complet des traductions espagnoles manquantes
SPANISH_MISSING = {
    "affichage": "Visualización",
    "affichageConfiguration": "Preferencias",
    "aide": "Ayuda",
    "aide.fermer": "OK - Cerrar ayuda",
    "aideAPropos": "Acerca de...",
    "aideAide": "Ayuda",
    "ajouterPanoramiques": "Añadir panorama al Tour",
    "ajouterPlan": "Añadir Plano",
    "config.annuler": "Cancelar",
    "config.choixRepert": "Por favor, elija el directorio inicial",
    "config.choixStyle": "Elegir la hoja de estilos",
    "config.langue": "Elegir idioma",
    "config.masthead": "\\u00a1Advertencia!",
    "config.message": "Reinicie su aplicación para tener en cuenta los cambios.",
    "config.sauvegarder": "Guardar la configuración",
    "config.styleClair": "Estilo Claro",
    "config.styleFonce": "Estilo Oscuro",
    "config.titreDialogue": "Copia de seguridad de configuración",
    "derniersProjets": "Proyectos recientes",
    "editeurHTML.ajouteImage": "Añadir imagen",
    "editeurHTML.ajouteLien": "Añadir hipervínculo",
    "editeurHTML.annule": "Cancelar",
    "editeurHTML.centre": "centrado",
    "editeurHTML.choixImage": "Elección de imagen",
    "editeurHTML.cibleLien": "destino del enlace",
    "editeurHTML.couleur": "Color",
    "editeurHTML.droite": "a la derecha",
    "editeurHTML.gauche": "a la izquierda",
    "editeurHTML.htmlInterne": "Editor interno",
    "editeurHTML.largeur": "Ancho del editor",
    "editeurHTML.lienExterne": "enlace externo",
    "editeurHTML.opacite": "Opacidad",
    "editeurHTML.position": "Posición",
    "editeurHTML.tailleImage": "tamaño de imagen",
    "editeurHTML.texte": "en texto",
    "editeurHTML.texteLien": "texto del hipervínculo",
    "editeurHTML.url": "URL",
    "editeurHTML.urlComplete": "URL completa incluyendo http\\://",
    "editeurHTML.valide": "Ok",
    "genererVisite": "Generar el tour",
    "interface.HSHTML": "Hotspot HTML",
    "interface.HSPanoramique": "HotSpot html",
    "interface.HSPhoto": "HotSpot de imagen",
    "interface.SuivantPrecedent": "Botones siguiente/anterior",
    "interface.affichageBoussole": "mostrar la brújula",
    "interface.affichageBoutonVisiteAuto": "Mostrar el botón",
    "interface.affichageCarte": "Mostrar el mapa",
    "interface.affichageComboMenu": "mostrar el cuadro combinado",
    "interface.affichageComboMenuImages": "Añadir imágenes",
    "interface.affichageMasque": "Visualización de botón",
    "interface.affichageMenuContextuel": "Mostrar el menú",
    "interface.affichagePlan": "Mostrar el plano",
    "interface.affichageReseauxSociaux": "Mostrar compartir",
    "interface.affichageVignettes": "Visualización de miniaturas",
    "interface.afficheFenetreAide": "Mostrar la ventana de Ayuda",
    "interface.afficheFenetreInfo": "Muestra la ventana de información",
    "interface.afficheRadar": "Mostrar el radar",
    "interface.afficheRadarCarte": "Mostrar el radar",
    "interface.afficheTitre": "Mostrar título",
    "interface.aiguilleMobile": "Aguja móvil",
    "interface.barreBoutons": "Barra de navegación clásica",
    "interface.barreBoutonsPersonalisee": "barra de navegación personalizada",
    "interface.barrePersonnaliseeCouleurOrigine": "Color original",
    "interface.boite": "Caja",
    "interface.bouton=Caja": "Botón",
    "interface.boutons": "Botones",
    "interface.cercle": "Círculo",
    "interface.chargeImage": "Cargar imagen",
    "interface.charger": "Cargar",
    "interface.choixBoussole": "Elección de la brújula",
    "interface.choixFleche": "Elección de flecha",
    "interface.choixMarqueurPoint": "Elección del marcador del punto",
    "interface.choixMarqueurTrace": "Elección del marcador del trazo",
    "interface.choixRepertoireTheme": "Elija el directorio del tema",
    "interface.cliquable": "Cliqueable",
    "interface.cocher": "Marcar todo/Ninguno",
    "interface.couleur2": "Color 2",
    "interface.couleurBordure": "Color del borde",
    "interface.couleurFond": "Color de fondo",
    "interface.couleurTexte": "Color del texto",
    "interface.entrerUrl": "Introduzca la URL",
    "interface.erreur=Erreur": "Error",
    "interface.flou": "Desenfoque",
    "interface.fondVisible": "Fondo visible",
    "interface.form": "Forma",
    "interface.forme": "Forma",
    "interface.hauteurBoite": "Altura de la caja",
    "interface.heure": "Hora",
    "interface.infoBulle=ToolTip": "ToolTip",
    "interface.interface": "Interfaz",
    "interface.largeurBoite": "Ancho de la caja",
    "interface.lien": "Enlace",
    "interface.multilignes": "Multilínea",
    "interface.nbBoutons": "número de botones",
    "interface.ordreParcours": "Orden del recorrido",
    "interface.parametres": "Parámetros",
    "interface.police": "Fuente",
    "interface.positionBoussole": "Posición de la brújula",
    "interface.positionCarte": "Posición del mapa",
    "interface.positionFenetre": "Posición de la ventana",
    "interface.positionPlan": "Posición del plano",
    "interface.positionRadar": "Posición del radar",
    "interface.positionTelecommande": "Posición del mando a distancia",
    "interface.positionVignettes": "Posición de las miniaturas",
    "interface.random": "Aleatorio",
    "interface.reglage": "Ajuste",
    "interface.selectionRepertoire": "Por favor, seleccione el directorio del tema",
    "interface.sequentiel": "Secuencial",
    "interface.souris": "Con el ratón",
    "interface.suivantPrecedent": "Botones siguiente/anterior",
    "interface.survolBouton": "Sobrevolar botón",
    "interface.taille": "Tamaño",
    "interface.taille2": "Tamaño 2",
    "interface.tailleAiguilleBoussole": "Tamaño de la aguja de la brújula",
    "interface.tailleCadreBoussole": "Tamaño del marco de la brújula",
    "interface.tailleTexte": "Tamaño del texto",
    "interface.temps": "Tiempo",
    "interface.texteInfo": "Texto de información",
    "interface.theme": "Tema",
    "interface.titre": "Título",
    "interface.titreAdapte": "Ajustar al contenido",
    "interface.transparence": "Transparencia",
    "interface.valeurs": "Valores",
    "interface.visibilite": "Visibilidad",
    "interface.visible": "Visible",
    "main.actionRaf": "Acción de actualización",
    "main.afficheInterface": "Mostrar interfaz",
    "main.afficherPanoChoisi": "Mostrar panorama elegido",
    "main.apercuPano": "Vista previa del panorama",
    "main.chargerModele": "Cargar plantilla",
    "main.couleurfond": "Color de fondo",
    "main.definitionPanoCharge": "Panorama cargado",
    "main.degres": "grados",
    "main.dialog.annuler": "Cancelar",
    "main.dialog.confirmer": "Confirmar",
    "main.dialog.fichierNonTrouve": "Archivo no encontrado",
    "main.dialog.fichierNonTrouveMsg": "El archivo no fue encontrado. \\u00bfDesea eliminarlo de la lista?",
    "main.dialog.message": "Mensaje",
    "main.dialog.nouveauProjetNonEnregistre": "El proyecto actual no se ha guardado. \\u00bfDesea guardarlo antes de crear uno nuevo?",
    "main.dialog.ok": "OK",
    "main.dialog.ouvrirProjetNonEnregistre": "El proyecto actual no se ha guardado. \\u00bfDesea guardarlo antes de abrir otro proyecto?",
    "main.dialog.projetNonEnregistre": "Proyecto no guardado",
    "main.dialog.quitter": "Salir",
    "main.dialog.quitterMessage": "¿Desea guardar el proyecto antes de salir?",
    "main.dialog.supprimerPanoramique": "Eliminar panorama",
    "main.dialog.supprimerPanorami queTexte": "\\u00bfEst\\u00e1 seguro de que desea eliminar el panorama?",
    "main.dialog.titre": "T\\u00edtulo",
    "main.espace": "Espacio de nombre",
    "main.existePas": "no existe",
    "main.fichier": "archivo",
    "main.fichierProjet": "archivo de proyecto",
    "main.formatImage": "Formato de imagen",
    "main.geo": "Geolocalización",
    "main.gererHotspots": "Gestionar hotspots",
    "main.gestionHS": "Gestión de hotspots",
    "main.hotspots": "Hotspots",
    "main.image": "imagen",
    "main.infosPanoramique": "Información del panorama",
    "main.infosProjet": "Información del proyecto",
    "main.introLP": "Intro Little Planet",
    "main.lienImage": "Imagen de enlace",
    "main.lienImageActive": "Imagen de enlace activa",
    "main.longitude1": "Longitud",
    "main.marqueurPosition": "Marcador de posición",
    "main.modeCreationHS": "Modo de creación de hotspot",
    "main.modeHTML": "Modo HTML",
    "main.modeImage": "Modo imagen",
    "main.modePano": "Modo panorama",
    "main.nomProjet": "Nombre del proyecto",
    "main.note": "nota",
    "main.ouvrirEditeur": "Abrir editor",
    "main.parametresAffichage": "Parámetros de visualización",
    "main.parametresHS": "Parámetros del hotspot",
    "main.parametresPano": "Parámetros del panorama",
    "main.pointNord": "Punto norte",
    "main.position": "Posición",
    "main.positionGPS": "Posición GPS",
    "main.prise": "Toma",
    "main.proprietes": "Propiedades",
    "main.repetition": "Repetición",
    "main.repeterVisite": "Repetir la visita",
    "main.sauverModele": "Guardar plantilla",
    "main.selectionnerImage": "Seleccionar imagen",
    "main.sousTitre": "Subtítulo",
    "main.tailleHS": "Tamaño del hotspot",
    "main.taillePolice": "Tamaño de fuente",
    "main.theme": "Tema",
    "main.titre": "Título",
    "main.titres": "Títulos",
    "main.typeImageSource": "Tipo de imagen de origen",
    "main.typeProj": "Tipo de proyección",
    "main.voirHS": "Ver hotspot",
    "main.voirImage": "Ver imagen",
    "menu.aide": "Ayuda",
    "menu.configurationGeneral": "Configuración general",
    "menu.fichierProjet": "Archivo de proyecto",
    "menu.informationsProjet": "Información del proyecto",
    "menu.nouveauHS": "Nuevo hotspot",
    "menu.nouveauPanoramique": "Nuevo panorama",
    "menu.options": "Opciones",
    "menu.outils": "Herramientas",
    "menu.ouvrirDerniers": "Abrir recientes",
    "menu.parametresVisualiseur": "Parámetros del visualizador",
    "menu.reinitialiserInterface": "Restablecer interfaz",
    "panoramique.proprietes": "Propiedades del panorama",
    "plan.activation": "Activación del plano",
    "plan.ajoutPoint": "Añadir punto",
    "plan.choixCouleurDessin": "Elección de color de dibujo",
    "plan.choixCouleurFond": "Elección de color de fondo",
    "plan.choixFichier": "Elección del archivo",
    "plan.echellePlan": "Escala del plano",
    "plan.epaisseur": "Grosor",
    "plan.modeAjoutPoints": "Modo de añadir puntos",
    "plan.modeSuppressionPoints": "Modo de eliminación de puntos",
    "plan.nord": "Norte",
    "plan.opacite": "Opacidad",
    "plan.planNonDefini": "Plano no definido",
    "plan.positionNord": "Posición del norte",
    "plan.rotationNord": "Rotación del norte",
    "plan.supprimePoint": "Eliminar punto",
    "plan.visibilitePlan": "Visibilidad del plano",
    "projet.chargerProjet": "Cargar proyecto",
    "projet.dossier": "Carpeta",
    "projet.fichierInvalide": "Archivo de proyecto inválido",
    "projet.nonEnregistre": "no guardado",
    "projet.projetNonValide": "Proyecto no válido",
    "projet.titre": "Título del proyecto",
    "sauvegarder": "Guardar",
    "selectionner": "Seleccionar",
    "selectionnerFichier": "Seleccionar archivo",
    "selectionnerRepertoire": "Seleccionar directorio"
}

def complete_spanish():
    """Ajoute toutes les traductions manquantes au fichier espagnol"""
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
    for key, value in sorted(SPANISH_MISSING.items()):
        if key not in existing_keys:
            additions.append(f'{key}={value}')
    
    if additions:
        # Ajouter à la fin du fichier
        with open(spanish_file, 'a', encoding='utf-8') as f:
            f.write('\n# Traductions complémentaires ajoutées automatiquement\n')
            f.write('\n'.join(additions))
            f.write('\n')
        
        print(f"✅ {len(additions)} traductions espagnoles ajoutées")
    else:
        print("✅ Aucune traduction manquante")
    
    return len(additions)

if __name__ == '__main__':
    print("=" * 70)
    print("  COMPLÉTION DES TRADUCTIONS ESPAGNOLES")
    print("=" * 70)
    print()
    complete_spanish()
    print()
    print("=" * 70)
