var numeroPano = 0;

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
}

include("panovisu/libs/jquery/jquery.min.js");
include("panovisu/libs/jquery/jquery.mousewheel.min.js");
include("panovisu/libs/hammer/hammer.min.js");
include("panovisu/libs/three.js/types.js");  //redéfnit le type float32Array() pour IE9
include("panovisu/libs/three.js/three.min.js");


