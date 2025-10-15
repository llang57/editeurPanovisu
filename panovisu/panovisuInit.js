var numeroPano = 0,
        version = "3.0",
        programmeur = "Laurent LANG",
        anneeProgramme = "2025",
        site = "http://lemondea360.fr",
        siteTexte = "le monde à 360°";
sitePanovisu = "",
        siteTextePanovisu = "";


window.requestAnimFrame = (function () {
    return window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            window.oRequestAnimationFrame ||
            window.msRequestAnimationFrame ||
            function (/* function */ callback, /* DOMElement */ element) {
                window.setTimeout(callback, 1000.0 / 60.0);
            };
})();

window.requestTimeout = function (fn, delay) {
    if (!window.requestAnimationFrame &&
            !window.webkitRequestAnimationFrame &&
            !(window.mozRequestAnimationFrame && window.mozCancelRequestAnimationFrame) && // Firefox 5 ships without cancel support
            !window.oRequestAnimationFrame &&
            !window.msRequestAnimationFrame)
        return window.setTimeout(fn, delay);
    var start = new Date().getTime(),
            handle = new Object();
    function loop() {
        var current = new Date().getTime(),
                delta = current - start;
        delta >= delay ? fn.call() : handle.value = requestAnimFrame(loop);
    }
    handle.value = requestAnimFrame(loop);
    return handle;
};


function traduction() {
    this.panoPrecedent = "panoramique précedent";
    this.panoSuivant = "panoramique suivant";
    this.petitePlanete = "Vue petite planète";
    this.vueNormale = "Vue Normale";
    this.masqueElements = "Masque les éléments";
    this.afficheElements = "Affiche les éléments";
    this.demarreAutoTour = "Lance la visite Automatique";
    this.arreteAutoTour = "Arrête la visite Automatique";
    this.aPropos = "A Propos de panoVisu ...";
    this.afficheMasque = "Affiche/masque les éléments";
    this.vignettes = "Vignettes";
    this.gauche = "Déplacement à gauche";
    this.haut = "Déplacement vers le haut";
    this.bas = "Déplacement vers le bas";
    this.droite = "Déplacement à droite";
    this.zoomPlus = "Zoom +";
    this.zoomMoins = "Zoom -";
    this.aide = "Aide";
    this.info = "Ecran d'accueil";
    this.pleinEcran = "Plein écran (Marche/Arrêt)";
    this.autorotation = "Autorotation (Marche/Arrêt)";
    this.souris = "change le mode de déplacement de la souris";
    this.plan = "Plan";
    this.carte = "Carte";
    this.panoVisuSite = "panoVisu le site";
    this.clicFenetre = "cliquez pour fermer la fenêtre";
    this.fenetreInfo = "<b>Panovisu version " +
            version +
            "</b><br>Un visualiseur de Visites Virtuelles 100% HTML5 - 100% libre<br>" +
            "Utilise la bibliothèque <a href='http://threejs.org/' target='_blank' title='voir la page de three.js' style='color: #ffffff; font-weight: bold; text-decoration: none;'>🔗 Three.js</a>" +
            "<br><br>&copy; " + programmeur + " (" + anneeProgramme + ")<br>" +
            "<br>une création : <a href='" + site + "' target='_blank' style='color: #ffffff; font-weight: bold; text-decoration: none;'>🔗 " + siteTexte + "</a>";
    this.fenetreAide = "<span style='font-weight:bolder;font-size:1.2em;font-variant: small-caps;'>Aide à la Navigation</span><br><br><div style='width:100px;height:90px;padding-left:5px;display:inline-block;'><img style='width:90px' src='panovisu/images/aide_souris.png'/></div>" +
            "<div style='width : 270px;display:inline-block;vertical-align:top; text-align: justify;'>Pour vous déplacer dans la vue cliquez avec le bouton gauche de la souris " +
            "sur le panoramique puis déplacez la souris en maintenant le bouton de la souris enfoncé<br><br>Vous pouvez également utiliser le menu pour vous déplacer</div>" +
            "<div><br><br>cliquez pour fermer la fenêtre</div>";
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
    var i = 0;
    // Créer l'objet
    imageObj = new Image();

    // Démarrer le préchargement
    for (i = 0; i <= images.length; i++)
    {
        imageObj.src = images[i];
    }
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

//include("panovisu/libs/openLayers/OpenLayers.js");
//include("panovisu/libs/openLayers/OpenStreetMap.js");
//include("panovisu/libs/openLayers/gmap.js?v=3.5&sensor=false");

include("panovisu/i18n/defaut.js");
include("panovisu/i18n/fr_FR.js");
include("panovisu/i18n/en_EN.js");
include("panovisu/i18n/de_DE.js");

include("panovisu/libs/screenfull.js");
include("panovisu/libs/jquery/jquery.min.js");
include("panovisu/libs/jquery/jquery.mousewheel.min.js");
include("panovisu/libs/jqueryMobile/jquery.mobile.custom.min.js");
include("panovisu/libs/three.js/types.js");  //redéfnit le type float32Array() pour IE9
include("panovisu/libs/three.js/three.min.js");
include("panovisu/libs/jqueryMenu/jquery.contextMenu.js");
include("panovisu/libs/jqueryMenu/jquery.ui.position.js");
include("panovisu/libs/msdropdown/jquery.dd.min.js");

