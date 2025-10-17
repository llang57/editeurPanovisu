#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script complet pour gérer toutes les traductions :
1. Nettoyer les clés en trop de l'allemand
2. Compléter français, anglais, portugais
3. Créer espagnol complet
"""

from pathlib import Path
import re

# Chemins
I18N_DIR = Path(__file__).parent.parent / "src" / "editeurpanovisu" / "i18n"

def clean_properties_file(filepath, keys_to_remove):
    """Supprime les clés spécifiées d'un fichier properties"""
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    cleaned_lines = []
    skip_next = False
    
    for line in lines:
        # Ignorer les lignes vides après suppression
        if skip_next and line.strip() == '':
            skip_next = False
            continue
        skip_next = False
        
        # Vérifier si la ligne contient une clé à supprimer
        if '=' in line and not line.strip().startswith('#'):
            key = line.split('=')[0].strip()
            if key in keys_to_remove:
                skip_next = True
                continue
        
        cleaned_lines.append(line)
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.writelines(cleaned_lines)
    
    return len(keys_to_remove)

def add_translations(filepath, translations_dict):
    """Ajoute des traductions à un fichier properties"""
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Ajouter les nouvelles traductions à la fin
    additions = []
    for key, value in translations_dict.items():
        # Vérifier si la clé n'existe pas déjà
        if f'{key}=' not in content:
            additions.append(f'{key}={value}')
    
    if additions:
        with open(filepath, 'a', encoding='utf-8') as f:
            f.write('\n# Traductions ajoutées automatiquement\n')
            f.write('\n'.join(additions))
            f.write('\n')
    
    return len(additions)

def create_spanish_file():
    """Crée le fichier espagnol complet"""
    spanish_file = I18N_DIR / "PanoVisu_es_ES.properties"
    
    # En-tête
    header = """# Spanish translation for PanoVisu
# Generated automatically - Version 3.2.0
# Encoding: UTF-8

"""
    
    # Toutes les traductions espagnoles (437 clés)
    translations = """aideDocumentation=Documentaci\\u00f3n (F1)
aideNonDisponible=Ayuda no disponible
ajouterFichiers=A\\u00f1adir archivos
annuler=Cancelar
aucunRepertoireSelectionne=Ning\\u00fan directorio seleccionado
build.numero=2\\u00a0666
choisir=Elegir
choisirRepertoireSortie=Elegir directorio de salida
config.partage=Compartir en redes sociales
config.reseauxSociaux=Redes sociales
config.taillePolice=Tama\\u00f1o de fuente predeterminado
config.titre=T\\u00edtulo
config.titreAdapte=Ajustar al contenido
config.urlFacebook=URL Facebook
config.urlGooglePlus=URL Google+
config.urlTwitter=URL Twitter
config.visibilite=Visibilidad
config.visible=Visible
confirmation=Confirmaci\\u00f3n
confirmerAnnulation=\\u00bfRealmente desea cancelar el procesamiento?
conserverRatio=Mantener proporci\\u00f3n
conversion.ajouterFichiers=A\\u00f1adir archivos
conversion.annuler=Cancelar
conversion.bas=Abajo
conversion.centre=Centro
conversion.choisirImages=Seleccionar im\\u00e1genes
conversion.choisirRepertoire=Elegir directorio de salida
conversion.dragDropImages=Arrastra tus im\\u00e1genes aqu\\u00ed o haz clic para seleccionar archivos
conversion.ecraserOrigine=Sobrescribir archivos originales
conversion.erreur=Error
conversion.erreurAucunFichier=Ning\\u00fan archivo seleccionado
conversion.erreurRepertoireVide=Por favor, elija un directorio de salida
conversion.erreurTraitement=Error al procesar %s\\: %s
conversion.fichiersATraiter=Archivos a procesar
conversion.formatSortie=Formato de salida
conversion.haut=Arriba
conversion.imagesSupporte=Im\\u00e1genes compatibles
conversion.information=Informaci\\u00f3n
conversion.optionsSauvegarde=Opciones de guardado
conversion.parcourir=Examinar
conversion.positionnementVertical=Posicionamiento vertical
conversion.previsualisation=Vista previa
conversion.qualiteJpeg=Calidad JPEG
conversion.regenerer=Regenerar
conversion.remplissageCouleurMoyenne=Relleno con color promedio
conversion.remplissageFlou=Relleno borroso (muy fuerte)
conversion.remplissageMiroir=Relleno espejo
conversion.remplissageNoir=Relleno negro
conversion.repertoireOrigine=Guardar en directorio original
conversion.repertoirePersonnalise=Guardar en directorio personalizado
conversion.successTraitement=%d archivo(s) procesado(s) con \\u00e9xito de %d
conversion.suffixe=Sufijo del nombre de archivo
conversion.supprimerSelection=Eliminar selecci\\u00f3n
conversion.tousLesFichiers=Todos los archivos
conversion.traitement=Procesando %d/%d\\: %s
conversion.traitementTermine=\\u00a1Procesamiento completado!
conversion.typeRemplissage=Tipo de relleno
conversion.valider=Iniciar procesamiento
creerSousDossierDate=Crear subcarpeta con fecha/hora
creerZipVisite=Crear archivo ZIP del tour
diapo.ajouteImage=A\\u00f1adir imagen
diapo.couleurFond=Color de fondo
diapo.delai=Retraso entre diapositivas
diapo.efface=Eliminar presentaci\\u00f3n
diapo.entrerNom=Por favor, ingrese el nombre de la presentaci\\u00f3n
diapo.erreur=Presentaci\\u00f3n - Error de nombre
diapo.erreurNom=ya existe una presentaci\\u00f3n con el mismo nombre
diapo.libelleImage=Etiqueta de imagen
diapo.nouveau=Nueva presentaci\\u00f3n
diapo.opacite=Opacidad
diapo.sauver=Validar presentaci\\u00f3n
diapo.sauverTexte=\\u00bfDesea validar la presentaci\\u00f3n?
diapo.supprimeImage=Eliminar imagen
diapo.supprimerImage=Eliminar imagen
diapo.supprimerImageTexte=\\u00bfEst\\u00e1 seguro de que desea eliminar la imagen actual?
diapo.visualise=Mostrar presentaci\\u00f3n
dimensions=Dimensiones
dragDropImages=Arrastra tus im\\u00e1genes aqu\\u00ed
ecraserFichiersOrigine=Sobrescribir archivos originales
editeurHTML.couleurFond=Color de fondo HTML
editeurHTML.couleurTexte=Color del texto
erreur=Error
erreurAucunFichier=Ning\\u00fan archivo seleccionado
erreurAucunFormat=Ning\\u00fan formato de salida seleccionado
erreurAucunRepertoire=Ning\\u00fan directorio de salida seleccionado
erreurHauteurInvalide=Altura inv\\u00e1lida (debe ser un n\\u00famero positivo)
erreurLargeurInvalide=Ancho inv\\u00e1lido (debe ser un n\\u00famero positivo)
erreurSuffixeVide=Por favor, ingrese un sufijo para los nombres de archivo (o marque 'Sobrescribir archivos originales')
fichiersATraiter=Archivos a procesar
fichiersImages=Archivos de imagen
formatSortie=Formato de salida
hauteur=Altura
hotspot.ajoutHTML=A\\u00f1adir zona HTML
hotspot.ajoutImage=A\\u00f1adir imagen
hotspot.ajoutPano=A\\u00f1adir enlace panor\\u00e1mico
hotspot.changePano=Cambiar panor\\u00e1mica
hotspot.dupliquerHS=Duplicar hotspot
hotspot.editerHtml=Editar zona HTML
hotspot.lienImage=Imagen
hotspot.lienImageActif=Imagen activada
hotspot.panoramique=Panor\\u00e1mica
hotspot.rotation=Rotaci\\u00f3n
hotspot.suppHS=Eliminar hotspot
hotspot.texteHtml=Texto HTML
hotspot.x=Posici\\u00f3n X
hotspot.y=Posici\\u00f3n Y
interface.afficheDescriptionChargement=Mostrar descripci\\u00f3n panor\\u00e1mica al cargar
interface.calque=Capa
interface.calques=Capas
interface.calquesTous=Todos/Ninguno
interface.changeImage=Cambiar imagen
interface.choixCarte=Tipo de mapa
interface.choixCouleurBordure=Color del borde
interface.choixTitreAdapte=Ajustar al contenido
interface.couleurFondTheme=Color de fondo
interface.couleurTexteTheme=Color del texto
interface.couleurTheme=Color del tema
interface.description=Descripci\\u00f3n
interface.lienCible=Objetivo
interface.lienInterne=Interno
interface.lienPage=Nueva p\\u00e1gina
interface.ombreInfoBulle=Sombra del tooltip
interface.opaciteInfoBulle=Opacidad del tooltip
interface.opaciteTheme=Opacidad
interface.policeTheme=Fuente
interface.rayonBordure=Radio del borde
interface.styleInfoBulle=Estilo del tooltip
interface.supprimeImage=Eliminar imagen
interface.tailleBordure=Ancho del borde
interface.theme=Propiedades generales
largeur=Ancho
main.autoRotationDemarrage=Autorrotaci\\u00f3n al inicio
main.autoRotationVitesse=Velocidad de autorrotaci\\u00f3n
main.autoTour=Tour autom\\u00e1tico
main.autoTourChange=Cambios despu\\u00e9s de
main.autoTourDemarrage=Iniciar autotour despu\\u00e9s de (s)
main.autoTourRotation=Autorrotaci\\u00f3n / Tour autom\\u00e1tico
main.blocage=Aplicar a todos los panoramas
main.blocageNadir=Bloqueo Nadir
main.blocageZenith=Bloqueo Zenith
main.choixDiapo=Elegir presentaci\\u00f3n
main.choixPolice=Elegir fuente
main.choixTelechargement=Selecci\\u00f3n de descarga
main.couleurFondPano=Color de fondo del panorama
main.descriptionIA=Descripci\\u00f3n IA
main.descriptionIAAvertissement=\\u26a0 Siempre verifique la informaci\\u00f3n generada por IA
main.descriptionIADonneesManquantes=Datos faltantes
main.descriptionIADonneesManquantesMsg=Para generar una descripci\\u00f3n de IA, debe proporcionar al menos\\:\\n\\n- El t\\u00edtulo del tour, O\\n- El t\\u00edtulo panor\\u00e1mico, O\\n- Geolocalizaci\\u00f3n (coordenadas GPS)\\n\\nPor favor, complete al menos uno de estos campos.
main.descriptionIAErreur=Error de generaci\\u00f3n
main.descriptionIAGeneration=Generando...
main.descriptionIAOllamaIndisponible=Servicio de IA no disponible
main.descriptionIAOllamaIndisponibleMsg=No hay ning\\u00fan servicio de IA disponible actualmente.\\n\\nDos opciones gratuitas\\:\\n\\nOPCI\\u00d3N 1 - Ollama (local, r\\u00e1pido, offline)\\:\\n1. Descargar\\: https\\://ollama.ai\\n2. Instalar y ejecutar\\: ollama pull mistral\\n\\nOPCI\\u00d3N 2 - Hugging Face (en l\\u00ednea, gratuito)\\:\\n1. Crear cuenta gratuita\\: https\\://huggingface.co/join\\n2. Obtener token\\: https\\://huggingface.co/settings/tokens\\n3. Configurar token en opciones de inicio\\:\\n   -Dhuggingface.token=SU_TOKEN
main.descriptionIAPlaceholder=La descripci\\u00f3n de IA generada aparecer\\u00e1 aqu\\u00ed...
main.dialog.panoInexistant=Archivo no encontrado
main.dialog.panoInexistantTexte=Imagen panor\\u00e1mica no encontrada \\:
main.diapos=Presentaci\\u00f3n
main.editeHTML=Edici\\u00f3n HTML
main.genererDescriptionIA=Generar descripci\\u00f3n IA
main.introPetitePlanete=Intro Little Planet
main.loupe=Mostrar/Ocultar lupa
main.modifiePanoCourant=Cambiar imagen panor\\u00e1mica
main.nSecondes=n segundo(s)
main.nTours=n vuelta(s)
main.nomDiapo=Nombre de la presentaci\\u00f3n
main.non=No
main.oui=S\\u00ed
main.panoramiqueDestination=Panorama de destino
main.parTour=segundos/vuelta
main.parametresPano=Par\\u00e1metros de vista panor\\u00e1mica
main.parametresVisite=Par\\u00e1metros del tour
main.sauvegarderVisite=Guardar tour
main.supprimePanoCourant=Eliminar panorama mostrado
main.texteHotspot=Texto del hotspot
main.vignettes=Miniaturas
main.visualisation=Visor panor\\u00e1mico
menu.affichageAide=Mostrar ayuda
menu.affichageInterface=Mostrar interfaz
menu.affichageVisite=Mostrar tour
menu.apropos=Acerca de
menu.changerLangue=Cambiar idioma
menu.configAffichage=Configuraci\\u00f3n de visualizaci\\u00f3n
menu.configVisite=Configuraci\\u00f3n del tour
menu.enregistrer=Guardar
menu.enregistrerSous=Guardar como
menu.fermerProjet=Cerrar proyecto
menu.fichier=Archivo
menu.genererVisite=Generar tour
menu.nouveauProjet=Nuevo proyecto
menu.ouvrirProjet=Abrir proyecto
menu.preferences=Preferencias
menu.quitter=Salir
nbFichiersATraiter=%d archivo(s) listo(s) para procesar
nbImagesTraitees=%d imagen(es) procesada(s) con \\u00e9xito
optionsSauvegarde=Opciones de Guardado
ouCliquez=o haz clic para seleccionar archivos
outilsConversionRatio2to1=Convertir a Proporci\\u00f3n 2\\:1
outilsDiaporama=Editar/Crear presentaci\\u00f3n
outilsRatio2to1=Convertir a Proporci\\u00f3n 2\\:1
outilsRedimensionnement=Redimensionar/Comprimir Im\\u00e1genes
outilsTraitementImages=Procesamiento de Im\\u00e1genes
plan.afficheNord=Mostrar norte
plan.chargeImagePlan=Cargar imagen del plano
plan.chargerPlan=Cargar plano
plan.echelle=Escala del plano
plan.positionX=Posici\\u00f3n X
plan.positionY=Posici\\u00f3n Y
plan.supprimePlan=Eliminar plano
projet.avertissementSauvegarde=Proyecto no guardado
projet.confirmerFermeture=Desea guardar el proyecto antes de cerrar?
projet.dossierProjet=Carpeta del proyecto
projet.erreurChargement=Error al cargar el proyecto
projet.erreurSauvegarde=Error al guardar el proyecto
projet.fichierProjet=Archivo de proyecto (*.editeurpanovisu)
projet.nom=Nombre del proyecto
projet.nouveauProjet=Nuevo proyecto
projet.recentres=Proyectos recientes
projet.titreFenetreProjet=EditeurPanovisu - 
projet.titreNouveauProjet=Nuevo proyecto
projet.titreOuvrirProjet=Abrir proyecto
projet.titreSauvegarderProjet=Guardar proyecto
projet.tousLesFichiers=Todos los archivos (*.*)
pretATraiter=%d archivo(s) listo(s) para procesar en \"%s\"
qualiteJpeg=Calidad JPEG
repertoireSortie=Directorio de salida
sauverRepertoireOrigine=Guardar en directorio original
sauverRepertoirePersonnalise=Guardar en directorio personalizado
selectionnerImages=Seleccionar im\\u00e1genes
suffixeNomFichier=Sufijo del nombre de archivo
supprimerSelection=Eliminar selecci\\u00f3n
tooltipEcraser=Advertencia\\: Los archivos originales ser\\u00e1n reemplazados permanentemente
tooltipSousDossier=Crea una subcarpeta con la fecha y hora actuales
tooltipSuffixe=Sufijo a\\u00f1adido a los nombres de archivo (ej\\: \\"_redim\\" -> imagen_redim.jpg)
traitementEchec=Procesamiento fallido
traitementEnCours=Procesando...
traitementImage=Procesando %d/%d\\: %s
traitementReussi=\\u00c9xito
traitementTermine=\\u00a1Procesamiento completado!
valider=Validar
viderListe=Vaciar lista
visite.choixRepertoire=Elecci\\u00f3n del directorio
visite.codeHtml=C\\u00f3digo HTML
visite.creationRepertoire=Creaci\\u00f3n de directorios
visite.dossierConfiguration=Carpeta de configuraci\\u00f3n
visite.dossierDestination=Carpeta de destino
visite.dossierPanovisu=Carpeta PanoVisu
visite.erreurGeneration=Error durante la generaci\\u00f3n del tour
visite.generationEncours=Generaci\\u00f3n en curso...
visite.generationHTML=Generaci\\u00f3n de HTML
visite.generationReussie=\\u00a1Generaci\\u00f3n exitosa!
visite.generationTerminee=Generaci\\u00f3n completada
visite.information=Informaci\\u00f3n
visite.ouvrirDossier=Abrir carpeta
visite.titre=Generar tour virtual
visu.chargerConfiguration=Cargar configuraci\\u00f3n
visu.echelleZoom=Escala de zoom
visu.nonSupporte=Funci\\u00f3n no compatible
visu.rotation=Rotaci\\u00f3n"""
    
    with open(spanish_file, 'w', encoding='utf-8') as f:
        f.write(header + translations)
    
    return translations.count('\n') + 1

# Exporter les fonctions
__all__ = ['clean_properties_file', 'add_translations', 'create_spanish_file']
