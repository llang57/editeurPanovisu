var numeroPano = 0;
    function traduction() {
        this.panoPrecedent = "panoramique précedent";
        this.panoSuivant = "panoramique suivant";
        this.petitePlanete = "Vue petite planète";
        this.vueNoramale = "Vue Normale";
        this.aPropos = "A Propos de panoVisu ...";
        this.afficheMasque="Affiche/masque les éléments";
        this.vignettes="Vignettes";
        this.gauche="Déplacement à gauche";
        this.haut="Déplacement vers le haut";
        this.bas="Déplacement vers le bas";
        this.droite="Déplacement à droite";
        this.zoomPlus="Zoom +";
        this.zoomMoins="Zoom -";
        this.aide="Aide";
        this.pleinEcran="Plein écran (Marche/Arrêt)";
        this.autorotation="Autorotation (Marche/Arrêt)";
        this.souris="change le mode de déplacement de la souris";
}

    var chainesTraduction = new Array();

/**
 * inclusion de fichier javascript
 * 
 * @param {type} fileName
 * @returns {undefined}
 */
function include(fileName) {
    document.write("<script type='text/javascript' src='" + fileName + "'></script>");
}

/**
 * 
 * @param {type} images
 * @returns {undefined}
 */
function prechargeImages(images) {
//    var i = 0;
//    // Créer l'objet
//    imageObj = new Image();
//
//    // Démarrer le préchargement
//    for (i = 0; i <= images.length; i++)
//    {
//        imageObj.src = images[i];
//    }
}


/**
 * Teste si les canvas sont supportés
 * 
 * @returns {window.CanvasRenderingContext2D|Window.CanvasRenderingContext2D}
 */
function supportCanvas() {
    return !!window.CanvasRenderingContext2D;
}

/**
 * Teste si webGL est supporté
 *  
 * @returns {Window.WebGLRenderingContext|window.WebGLRenderingContext|Boolean}
 */
function supportWebgl() {
    try {
        var canvas = document.createElement('canvas');
        return !!window.WebGLRenderingContext && (
                canvas.getContext('webgl') || canvas.getContext('experimental-webgl'));
    } catch (e) {
        return false;
    }
}

/**
 * Crée les objets correspondants à chacun des panoramiques
 * 
 * @param {type} parametres
 * @returns {undefined}
 */

function ajoutePano(parametres) {
    numeroPano += 1;
    var pano = new panovisu(numeroPano);
    pano.initialisePano(parametres);
    return pano;
}

include("panovisu/i18n/defaut.js");
include("panovisu/i18n/fr_FR.js");
include("panovisu/i18n/en_EN.js");
include("panovisu/i18n/de_DE.js");

include("panovisu/libs/screenfull.js");
include("panovisu/libs/jquery/jquery.min.js");
include("panovisu/libs/jquery/jquery.mousewheel.min.js");
include("panovisu/libs/hammer/hammer.min.js");
include("panovisu/libs/three.js/types.js");  //redéfnit le type float32Array() pour IE9
include("panovisu/libs/three.js/three.min.js");
include("panovisu/libs/jqueryMenu/jquery.contextMenu.js");
include("panovisu/libs/jqueryMenu/jquery.ui.position.js");



