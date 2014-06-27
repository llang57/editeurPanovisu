/**
 * @name panoVisu
 * 
 * @version 0.50
 * @author LANG Laurent
 */


/**
 * teste si appareil tactile
 * 
 * @returns {window|String}
 */
version = "0.80";
programmeur = "Laurent LANG";
anneeProgramme = "2014";
site = "http://lemondea360.fr";
siteTexte = "le monde à 360°";

function estTactile() {
    return !!('ontouchstart' in window);
}


function panovisu(num_pano) {
    var camera, scene, renderer;

    function spot() {
        this.id = 0;
        this.image = "";
        this.texte = "";
        this.long = 0;
        this.lat = 0;
        this.posY = 0;
    }

    function pointInteret() {
        this.type = "panoramique";
        this.long = 0;
        this.lat = 0;
        this.contenu = "";
        this.image = "";
        this.anime = "false";
        this.info = "";
    }

    function vignettePano() {
        this.image = "";
        this.xml = "";
        this.txt = "";
    }


    var timer,
            timers,
            i,
            affHS = 0,
            deltaHS = 0.02,
            numHS,
            numHotspot = 0,
            mouseMove,
            isReloaded = false,
            hotSpot = new Array(),
            pointsInteret = new Array(),
            vignettesPano = new Array(),
            mode = 1,
            longitude = 0,
            latitude = 0,
            fenPanoramique,
            texture_placeholder,
            isUserInteracting = false,
            onPointerDownPointerX = 0,
            onPointerDownPointerY = 0,
            onPointerDownLon = 0,
            onPointerDownLat = 0,
            phi = 0,
            theta = 0,
            fov = 75,
            dx = 0,
            dy = 0,
            deltaX = 0,
            deltaY = 0,
            $_GET = [],
            maxFOV = 125,
            minFOV = 25,
            target = new THREE.Vector3(),
            bPleinEcran = false,
            largeur,
            hauteur,
            xmlFile,
            fenetreX,
            fenetreY,
            panovisu,
            fenetreUniteX,
            fenetreUniteY,
            typeVignettes,
            marginPanoLeft,
            nbPanoCharges = 0,
            positionVignettesX = 0,
            positionVignettesY = 0
            ;
    /**
     * Variables par défaut pour l'affichage du panoramique
     * 
     * @type String|@exp;_L1@pro;panoImage|@exp;XMLPano@call;attr
     */
    var panoImage,
            loader,
            couleur,
            bSuivantPrecedent,
            XMLsuivant,
            XMLprecedent,
            styleBoutons,
            bordure,
            panoTitre,
            multiReso,
            nombreNiveaux,
            titrePolice,
            titreTaille,
            titreTailleUnite,
            titreTailleFenetre,
            titreTaillePolice,
            titreCouleur,
            titreFond,
            titreOpacite,
            panoType,
            affInfo,
            bAfficheInfo,
            bAfficheAide,
            afficheTitre,
            zooms,
            outils,
            deplacements,
            fs,
            autoR,
            souris,
            boutons,
            autoRotation,
            positionX,
            positionY,
            dX,
            dY,
            mesh,
            container,
            pano,
            pano1,
            zeroNord,
            boussole,
            boussoleImage,
            boussoleTaille,
            boussolePositionX,
            boussolePositionY,
            boussoleDX,
            boussoleDY,
            boussoleAffiche,
            boussoleOpacite,
            boussoleAiguille,
            elementsVisibles,
            marcheArret,
            marcheArretAffiche,
            marcheArretImage,
            marcheArretOpacite,
            marcheArretPositionX,
            marcheArretPositionY,
            marcheArretDX,
            marcheArretDY,
            marcheArretTitre,
            marcheArretNavigation,
            marcheArretBoussole,
            marcheArretPlan,
            marcheArretReseaux,
            marcheArretVignettes,
            marcheArretTaille,
            reseauxSociaux,
            reseauxSociauxAffiche,
            reseauxSociauxOpacite,
            reseauxSociauxPositionX,
            reseauxSociauxPositionY,
            reseauxSociauxDX,
            reseauxSociauxDY,
            reseauxSociauxTaille,
            reseauxSociauxTwitter,
            reseauxSociauxFacebook,
            reseauxSociauxGoogle,
            reseauxSociauxEmail,
            vignettesAffiche,
            vignettes,
            vignettesOpacite,
            vignettesPosition,
            vignettesFondCouleur,
            vignettesTaille,
            vignettesTailleImage;


    /**
     * Evènements souris / Touche sur écran
     * 
     */


    $(document).on("webkitfullscreenchange mozfullscreenchange fullscreenchange MSFullscreenChange", function() {
        bPleinEcran = !bPleinEcran;
        changeTaille();
    });

    $(document).on("click", "#container-" + num_pano, function(evenement) {
        /*
         * Evite de déclencher un hotspot si on arrête la souris dessus
         */
        if (mouseMove === false) {
            var mouse = new THREE.Vector2();
            var projector = new THREE.Projector();
            var raycaster = new THREE.Raycaster();
            var position = $(this).offset();
            var X = evenement.pageX - parseInt(position.left);
            var Y = evenement.pageY - parseInt(position.top);
            mouse.x = (X / $(this).width()) * 2 - 1;
            mouse.y = -(Y / $(this).height()) * 2 + 1;
            var vector = new THREE.Vector3(mouse.x, mouse.y, 1);
            projector.unprojectVector(vector, camera);
            raycaster.set(camera.position, vector.sub(camera.position).normalize());
            var intersects = raycaster.intersectObjects(scene.children);
            if (intersects.length > 0) {
                var intersect = intersects[ 0 ];
                var object = intersect.object;
                for (var i = 0; i < hotSpot.length; i++)
                {
                    if (object.id === hotSpot[i].id) {
                        numHS = i;
                        switch (pointsInteret[i].type) {
                            case "panoramique" :
                                $("#info-" + num_pano).fadeOut(1000);
                                pano1.fadeOut(1000, function() {
                                    chargeNouveauPano(numHS);
                                });
                                break;
                            case "image" :
                                afficheImage(pointsInteret[numHS].contenu);
                                break;
                            case "html" :
                                afficheHTML(pointsInteret[numHS].contenu);
                                break;
                        }
                    }
                }

            }
        }
        else {
            mouseMove = false;
        }
    });

    $(document).on("mousedown", "#container-" + num_pano, function(evenement) {

        evenement.preventDefault();
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });

        }
        onPointerDownPointerX = evenement.clientX;
        onPointerDownPointerY = evenement.clientY;
        isUserInteracting = true;
        if (mode === 1) {
            deltaX = 0;
            deltaY = 0;
            clearInterval(timer);
            timer = setInterval(function() {
                deplaceMode2();
            }, 10);
        }
        else
        {
            onPointerDownLon = longitude;
            onPointerDownLat = latitude;
            pano.addClass('curseurCroix');
        }



    });
    $(document).on("mousemove", "#container-" + num_pano, function(evenement) {

        if (isUserInteracting === true) {

            mouseMove = true;
            if (mode === 1) {
                deltaX = -(onPointerDownPointerX - evenement.clientX) * 0.01;
                deltaY = (onPointerDownPointerY - evenement.clientY) * 0.01;
            }
            else {
                longitude = (onPointerDownPointerX - evenement.clientX) * 0.1 + onPointerDownLon;
                latitude = (evenement.clientY - onPointerDownPointerY) * 0.1 + onPointerDownLat;
                affiche();
            }
        }
        else {
            $("#infoBulle-" + num_pano).hide();
            var mouse = new THREE.Vector2();
            var projector = new THREE.Projector();
            var raycaster = new THREE.Raycaster();
            var position = $(this).offset();
            var X = evenement.pageX - parseInt(position.left);
            var Y = evenement.pageY - parseInt(position.top);
            mouse.x = (X / $(this).width()) * 2 - 1;
            mouse.y = -(Y / $(this).height()) * 2 + 1;
            var vector = new THREE.Vector3(mouse.x, mouse.y, 1);
            projector.unprojectVector(vector, camera);
            raycaster.set(camera.position, vector.sub(camera.position).normalize());
            var intersects = raycaster.intersectObjects(scene.children);
            if (intersects.length > 0) {
                pano.css({cursor: "pointer"});
                var intersect = intersects[ 0 ];
                var object = intersect.object;
                var positions = object.geometry.attributes.position.array;
                for (var i = 0; i < hotSpot.length; i++)
                {
                    if (object.id === hotSpot[i].id) {
                        haut = Y - 5;
                        gauche = X + 20;
                        (pointsInteret[i].info !== "") ? $("#infoBulle-" + num_pano).html(pointsInteret[i].info) : $("#infoBulle-" + num_pano).html(pointsInteret[i].contenu);
                        $("#infoBulle-" + num_pano).css({top: haut + "px", left: gauche + "px"});
                        $("#infoBulle-" + num_pano).show();
                    }

                }

            }
            else {
                pano.css({cursor: "auto"});
//                $("#infoBulle-" + num_pano).hide();
            }

        }

    });
    $(document).on("mouseup mouseleave", "#container-" + num_pano, function(evenement) {
        clearInterval(timer);
        pano.removeClass('curseurCroix');
        isUserInteracting = false;
    });
    /**
     * Gestion de la molette de la souris
     * 
     */
    $(document).on("mousewheel", "#container-" + num_pano,
            function(evenement, delta) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });

                }
                evenement.preventDefault();
                fov -= delta;
                zoom();
            });
    /**
     * Changement de la taille de l'écran
     * 
     */
    $(window).resize(function() {
        changeTaille();
    });
    /**
     * Gestion du clavier
     */
    $(document).keydown(
            function(evenement) {
                if (bPleinEcran) {
                    if (bAfficheInfo)
                    {
                        $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                            $(this).css({display: "none"});
                            bAfficheInfo = false;
                        });

                    }
                    evenement.preventDefault();
                    var touche = evenement.which;
                    switch (touche)
                    {
                        case 37:
                            longitude -= 0.5;
                            break;
                        case 38:
                            latitude += 0.5;
                            break;
                        case 39:
                            longitude += 0.5;
                            break;
                        case 40:
                            latitude -= 0.5;
                            break;
                        case 16:
                            fov += 1;
                            break;
                        case 17:
                            fov -= 1;
                            break;
                    }
                    zoom();
                }
            });
    /**
     * 
     * @param {type} id
     * @returns {undefined}
     */
    function dXdY(id) {
        dx = 0;
        dy = 0;
        id1 = id.split("-");
        id = id1[0];
        switch (id)
        {
            case "xmoins" :
                dx -= 1;
                break;
            case "xplus" :
                dx += 1;
                break;
            case "ymoins" :
                dy -= 1;
                break;
            case "yplus" :
                dy += 1;
                break;
        }

    }
    /**
     * Gestion du click prolongé
     * 
     */
    $(document).on("mousedown", "#xmoins-" + num_pano + ",#xplus-" + num_pano + ",#ymoins-" + num_pano + ",#yplus-" + num_pano,
            function(evenement) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });

                }
                dXdY($(this).attr('id'));
                clearInterval(timer);
                timer = setInterval(function() {
                    longitude += dx;
                    latitude += dy;
                    affiche();
                }, 100);
                evenement.preventDefault();
            });
    $(document).on("mouseup mouseleave", "#xmoins-" + num_pano + ",#xplus-" + num_pano + ",#ymoins-" + num_pano + ",#yplus-" + num_pano, function(evenement) {
        clearInterval(timer);
        evenement.preventDefault();
    });
    /**
     * Gestion des clicks souris
     * 
     */
    $(document).on("click", "#xmoins-" + num_pano + ",#xplus-" + num_pano + ",#ymoins-" + num_pano + ",#yplus-" + num_pano,
            function(evenement) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });

                }
                dXdY($(this).attr('class'));
                longitude += dx;
                latitude += dy;
                affiche();
                evenement.preventDefault();
            });
    $(document).on("click", "#zoomPlus-" + num_pano, function() {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });

        }
        fov -= 1;
        zoom();
    });
    $(document).on("click", "#zoomMoins-" + num_pano, function() {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });

        }
        fov += 1;
        zoom();
    });
    $(document).on("click", "#souris-" + num_pano, function() {
        mode = 1 - mode;
    });
    $(document).on("click", "#pleinEcran-" + num_pano, function() {
        pleinEcran();
    });
    $(document).on("click", "#auto-" + num_pano, function() {
        if (autoRotation === "oui")
        {
            autoRotation = "non";
            stoppeAutoRotation();
        }
        else
        {
            autoRotation = "oui";
            demarreAutoRotation();
        }
    });
    $(document).on("click", ".binfo", function() {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + num_pano).fadeOut(1000, function() {
                $("#infoPanovisu-" + num_pano).css({display: "none"});
                bAfficheInfo = false;
            });

        }
        else {
            if (bAfficheAide) {
                $("#aidePanovisu-" + num_pano).fadeOut(1000, function() {
                    bAfficheAide = false;
                });
                $("#infoPanovisu-" + num_pano).fadeIn(1000, function() {
                    bAfficheInfo = true;
                });
            } else {
                $("#infoPanovisu-" + num_pano).fadeIn(1000, function() {
                    bAfficheInfo = true;
                });

            }

        }
    });
    $(document).on("click", ".aide", function() {
        if (bAfficheAide)
        {
            $("#aidePanovisu-" + num_pano).fadeOut(1000, function() {
                $("#aidePanovisu-" + num_pano).css({display: "none"});
                bAfficheAide = false;
            });
        } else {
            if (bAfficheInfo) {
                $("#infoPanovisu-" + num_pano).fadeOut(1000, function() {
                    bAfficheInfo = false;
                });
                $("#aidePanovisu-" + num_pano).fadeIn(1000, function() {
                    bAfficheAide = true;
                });
            } else {
                $("#aidePanovisu-" + num_pano).fadeIn(1000, function() {
                    bAfficheAide = true;
                });
            }

        }
    });

    $(document).on("click", ".infoPanovisu", function() {
        $(this).fadeOut(2000, function() {
            $(this).css({display: "none"});
            bAfficheInfo = false;
        });
    });
    $(document).on("click", ".aidePanovisu", function() {
        $(this).fadeOut(2000, function() {
            $(this).css({display: "none"});
            bAfficheAide = false;
        });
    });

    $(document).on("click", ".infoPanovisu a", function(event) {
        event.stopPropagation();
    });

    $(document).on("click", ".imgVignette", function(evenement) {
        if (vignettesPano.length !== 0) {
            console.log(vignettesPano);
            var element = $(this).attr("id");
            var numelement = parseInt(element.substring(6).split("-")[0]);
            console.log("Numéro" + (numelement + 1));
            clearInterval(timers);
            longitude = 0;
            latitude = 0;
            fov = 75;
            $("#infoBulle-" + num_pano).hide();
            $("#infoBulle-" + num_pano).html("");
            isReloaded = true;
            xmlFile = vignettesPano[numelement].xml;
            hotSpot = new Array();
            pointsInteret = new Array();
            vignettesPano = new Array();
            numHotspot = 0;
            $("#boussole-" + num_pano).hide();
            $("#marcheArret-" + num_pano).hide();
            $("#divVignettes-" + num_pano).html("");
            $("#divVignettes-" + num_pano).hide();
            chargeXML(xmlFile);
        }

    });


    $(document).on("click", ".positionVignettes", function(evenement) {
        if (vignettesTailleImage) {
            evenement.stopPropagation();
            element = $(this).attr("id");
            console.log(vignettesTailleImage + " " + elementsVisibles + "=>" + element);
            if ((element === "gaucheVignettes-" + num_pano) || (element === "droiteVignettes-" + num_pano)) {
                deplace = vignettesTailleImage + 10;
                if (element === "gaucheVignettes-" + num_pano) {
                    positionVignettesX += deplace;
                }
                else {
                    positionVignettesX -= deplace;
                }
                if (positionVignettesX > 0)
                    positionVignettesX = 0;
                if (-positionVignettesX + $("#divVignettes-" + num_pano).width() > (vignettesTailleImage + 10) * vignettesPano.length)
                    positionVignettesX = $("#divVignettes-" + num_pano).width() - (vignettesTailleImage + 10) * vignettesPano.length;
                console.log("gauche / droite " + positionVignettesX);

                $("#vignettes-" + num_pano).css({
                    transform: "translate(" + positionVignettesX + "px,0px)"
                });
            }
            if ((element === "basVignettes-" + num_pano) || (element === "hautVignettes-" + num_pano)) {
                deplace = vignettesTailleImage / 2 + 5;
                if (element === "hautVignettes-" + num_pano) {
                    positionVignettesY += deplace;
                }
                else {
                    positionVignettesY -= deplace;
                }
                if (positionVignettesY > 0)
                    positionVignettesY = 0;
                if (-positionVignettesY + $("#divVignettes-" + num_pano).height() > (vignettesTailleImage / 2 + 15) * vignettesPano.length)
                    positionVignettesY = $("#divVignettes-" + num_pano).height() - (vignettesTailleImage / 2 + 15) * vignettesPano.length;
                console.log("haut / bas " + positionVignettesY);

                $("#vignettes-" + num_pano).css({
                    transform: "translate(0px," + positionVignettesY + "px)"
                });
            }
            if (bAfficheInfo)
            {
                $("#infoPanovisu-" + num_pano).fadeOut(2000, function() {
                    $(this).css({display: "none"});
                    bAfficheInfo = false;
                });

            }
        }
    });


    $(document).on("click", ".marcheArret", function(evenement) {
        evenement.stopPropagation();
        console.log(vignettesTailleImage + " " + elementsVisibles);
        if (elementsVisibles) {
            if (marcheArretNavigation === "oui")
                $("#barre-" + num_pano).fadeOut(500);
            if (marcheArretBoussole === "oui")
                $("#boussole-" + num_pano).fadeOut(500);
            if (marcheArretTitre === "oui")
                $("#info-" + num_pano).fadeOut(500);
            if (marcheArretPlan === "oui")
                $("#plan-" + num_pano).fadeOut(500);
            if (marcheArretReseaux === "oui")
                $("#reseauxSociaux-" + num_pano).fadeOut(500);
            if (marcheArretVignettes === "oui")
            {
                $("#divVignettes-" + num_pano).fadeOut(500);
                console.log("position vignettes :"+vignettesPosition);
                if (vignettesPosition==="left"){
                    $("#divPrecedent-"+num_pano).css({left:0});
                }
                if (vignettesPosition==="right"){
                    $("#divSuivant-"+num_pano).css({right:0});
                }
            }
            elementsVisibles = false;
        }
        else {
            if (marcheArretNavigation === "oui")
                $("#barre-" + num_pano).fadeIn(500);
            if (marcheArretBoussole === "oui")
                $("#boussole-" + num_pano).fadeIn(500);
            if (marcheArretTitre === "oui")
                $("#info-" + num_pano).fadeIn(500);
            if (marcheArretPlan === "oui")
                $("#plan-" + num_pano).fadeIn(500);
            if (marcheArretReseaux === "oui")
                $("#reseauxSociaux-" + num_pano).fadeIn(500);
            if (marcheArretVignettes === "oui")
            {
                $("#divVignettes-" + num_pano).fadeIn(500);
                console.log("position vignettes :"+vignettesPosition);
                var largeur=$("#divVignettes-" + num_pano).width()+6;
                if (vignettesPosition==="left"){
                    $("#divPrecedent-"+num_pano).css({left:largeur});
                }
                if (vignettesPosition==="right"){
                    $("#divSuivant-"+num_pano).css({right:largeur});
                }
                
            }
            elementsVisibles = true;
        }
    });

$(document).on("click","#divSuivant-"+num_pano,function(){
            clearInterval(timers);
        longitude = 0;
        latitude = 0;
        fov = 75;
        $("#infoBulle-" + num_pano).hide();
        $("#infoBulle-" + num_pano).html("");
        isReloaded = true;
        xmlFile = XMLsuivant;
        hotSpot = new Array();
        pointsInteret = new Array();
        numHotspot = 0;
        $("#boussole-" + num_pano).hide();
        $("#marcheArret-" + num_pano).hide();
        chargeXML(xmlFile);

})

    $(document).on("click","#divPrecedent-"+num_pano,function(){
            clearInterval(timers);
        longitude = 0;
        latitude = 0;
        fov = 75;
        $("#infoBulle-" + num_pano).hide();
        $("#infoBulle-" + num_pano).html("");
        isReloaded = true;
        xmlFile = XMLprecedent;
        hotSpot = new Array();
        pointsInteret = new Array();
        numHotspot = 0;
        $("#boussole-" + num_pano).hide();
        $("#marcheArret-" + num_pano).hide();
        chargeXML(xmlFile);

})

    /**
     * 
     * @returns {undefined}
     */
    function deplaceMode2() {
        longitude += deltaX;
        latitude += deltaY;
        affiche();
    }


    function afficheVignettesHorizontales() {
        typeVignettes = "horizontales";
        $("#hautVignettes-" + num_pano).hide();
        $("#basVignettes-" + num_pano).hide();

        $("<div>", {id: "vignettes-" + num_pano, class: "vignettes"}).appendTo("#divVignettes-" + num_pano);
        var hauteur = vignettesTailleImage / 2;
        var largeurFenetre = $("#pano1-" + num_pano).width() - 15;
        if (largeurFenetre < (vignettesTailleImage + 10) * vignettesPano.length) {
            $("#gaucheVignettes-" + num_pano).show(500);
            $("#gaucheVignettes-" + num_pano).css({
                left: 0,
                height: vignettesTailleImage / 2 + 6,
                width: 15,
                bottom: 0
            });

            $("#droiteVignettes-" + num_pano).show(500);
            $("#droiteVignettes-" + num_pano).css({
                right: "0px",
                height: vignettesTailleImage / 2 + 6,
                width: 15,
                bottom: 0
            });
        }
        $("#divVignettes-" + num_pano).css(vignettesPosition, "0px");
        $("#divVignettes-" + num_pano).css({
            height: hauteur,
            width: largeurFenetre,
            paddingLeft: "17px",
            paddingTop: "3px",
            paddingBottom: "3px",
            backgroundColor: vignettesFondCouleur,
            opacity: vignettesOpacite,
            overflow: "hidden"
        });

        console.log($("#pano1-" + num_pano).width() + ";" + $("#pano1-" + num_pano).height());
        $("#vignettes-" + num_pano).css({
            height: hauteur,
            width: 3000
        });
        $("#divVignettes-" + num_pano).css(vignettesPosition, "0px");
        for (var i = 0; i < vignettesPano.length; i++) {
            if (vignettesPano[i].txt !== "") {
                texte = vignettesPano[i].txt;
            }
            else {
                texte = vignettesPano[i].xml;
            }

            $("<img>", {
                id: "imgVig" + i + "-" + num_pano,
                class: "imgVignette",
                src: vignettesPano[i].image,
                title: vignettesPano[i].txt,
                width: vignettesTailleImage,
                height: vignettesTailleImage / 2
            }).appendTo("#vignettes-" + num_pano);
        }
        $("#divVignettes-" + num_pano).show();
    }

    function afficheVignettesVerticales() {
        typeVignettes = "verticales";
        $("#gaucheVignettes-" + num_pano).hide();
        $("#droiteVignettes-" + num_pano).hide();

        $("<div>", {id: "vignettes-" + num_pano, class: "vignettes"}).appendTo("#divVignettes-" + num_pano);
        var hauteur = $("#pano1-" + num_pano).height() - 15;
        var largeurFenetre = vignettesTailleImage + 5;
        if (largeurFenetre < (vignettesTailleImage + 10) * vignettesPano.length) {
            $("#hautVignettes-" + num_pano).show(500);
            $("#hautVignettes-" + num_pano).css({
                left: 0,
                top: 0,
                height: 15,
                width: vignettesTailleImage + 11
            });

            $("#basVignettes-" + num_pano).show(500);
            $("#basVignettes-" + num_pano).css({
                left: 0,
                height: 15,
                width: vignettesTailleImage + 11,
                bottom: 0
            });
        }
        $("#divVignettes-" + num_pano).css(vignettesPosition, "0px");
        $("#divVignettes-" + num_pano).css({
            height: hauteur - ($("#info-" + num_pano).height() - 10) - 22,
            width: largeurFenetre,
            paddingLeft: "3px",
            paddingTop: "17px",
            paddingRight: "3px",
            backgroundColor: vignettesFondCouleur,
            opacity: vignettesOpacite,
            overflow: "hidden",
            top: ($("#info-" + num_pano).height() + 10) + "px"
        });

        console.log($("#pano1-" + num_pano).width() + ";" + $("#pano1-" + num_pano).height());
        $("#vignettes-" + num_pano).css({
            height: 3000,
            width: largeurFenetre
        });
        $("#divVignettes-" + num_pano).css(vignettesPosition, "0px");
        for (var i = 0; i < vignettesPano.length; i++) {
            if (vignettesPano[i].txt !== "") {
                texte = vignettesPano[i].txt;
            }
            else {
                texte = vignettesPano[i].xml;
            }

            $("<img>", {
                id: "imgVig" + i + "-" + num_pano,
                class: "imgVignette",
                src: vignettesPano[i].image,
                title: vignettesPano[i].txt,
                width: vignettesTailleImage,
                height: vignettesTailleImage / 2
            }).appendTo("#vignettes-" + num_pano).css({
                marginBottom: "2px",
                marginTop: "2px",
                marginLeft: "0px",
                paddingRight: "0px"
            });
        }
        $("#divVignettes-" + num_pano).show();
        if (vignettesPosition === "right") {
            $("#divSuivant-" + num_pano).css("right", largeurFenetre + 6);
        }
        if (vignettesPosition === "left") {
            $("#divPrecedent-" + num_pano).css("left", largeurFenetre + 6);
        }
    }


    /**
     * 
     * @returns {undefined}
     */
    function affiche() {
        if (latitude > 89.99)
            latitude = 89.99;
        if (latitude < -90)
            latitude = -90;
        phi = THREE.Math.degToRad(90 - latitude);
        theta = THREE.Math.degToRad(longitude);
        target.x = 500 * Math.sin(phi) * Math.cos(theta);
        target.y = 500 * Math.cos(phi);
        target.z = 500 * Math.sin(phi) * Math.sin(theta);
        camera.lookAt(target);
        renderer.render(scene, camera);
        var bouss = longitude - zeroNord;
        if (boussoleAiguille === "oui")
        {
            $("#bousAig-" + num_pano).css({transform: "rotate(" + bouss + "deg)"});
        }
        else {
            $("#bousImg-" + num_pano).css({transform: "rotate(" + (-bouss) + "deg)"});

        }

    }
//    $(document).on("click", "#divImage-" + num_pano, function() {
//        $("#divImage-" + num_pano).animate({opacity: 0.01}, 10, function() {
//            $("#divImage-" + num_pano).hide();
//        });
//
//    });

    function afficheImage(image) {
        var lrg = $("#pano1-" + num_pano).width();
        var haut = $("#pano1-" + num_pano).height();
        posX = Math.round(lrg * 0.1);
        posY = Math.round(haut * 0.1);
        var img = new Image();
        img.src = image;
        img.onload = function() {
            var hautImg = img.height;
            var largImg = img.width;
            ratio = largImg / hautImg;
            var larg1 = lrg - 2 * posX;
            var haut1 = larg1 / ratio;
//            alert(hautImg + ", " + largImg+"ratio : "+ratio);
            if (haut1 > haut - 2 * posY) {
                hautImg = haut - 2 * posY;
                largImg = Math.round(hautImg * ratio);
            }
            else {
                largImg = lrg - 2 * posX;
                hautImg = Math.round(largImg / ratio);

            }
            mrgX = Math.round((lrg - 2 * posX - largImg) / 2);
            mrgY = Math.round((haut - 2 * posY - hautImg) / 2);
//            alert(hautImg + ", " + largImg+"ratio : "+ratio);
            $("#divImage-" + num_pano).html("");
            $("#divImage-" + num_pano).css({
                width: Math.round(lrg - 2 * posX) + "px",
                height: Math.round(haut - 2 * posY) + "px",
                padding: posY + "px " + posX + "px",
                top: "0px",
                left: "0px",
                zIndex: 10010,
                opacity: 0,
                backgroundColor: "rgba(0,0,0,0.7)"
            });

            $("<img>", {id: "hsImg-" + num_pano, class: "hsImg", src: image, title: "Cliquz sur l'image pour quitter"}).appendTo("#divImage-" + num_pano);


            $("#hsImg-" + num_pano).css({
                width: largImg + "px",
                height: hautImg + "px",
                marginTop: mrgY + "px",
                marginLeft: mrgX + "px"
            });

            $("<img>", {
                id: "imgFerme-" + num_pano,
                class: "imgFerme",
                src: "panovisu/images/fermer.png",
                style: "height :30px;width : 30px;position : absolute;top:10px;right : " + posY + "px;"
            }).appendTo("#divImage-" + num_pano);

            $("#divImage-" + num_pano).show();
            $("#divImage-" + num_pano).animate({opacity: 1}, 1500);

        };
    }

    $(document).on("click", "#imgFerme-" + num_pano, function() {
        $("#divHTML-" + num_pano).hide();
        $("#divImage-" + num_pano).hide();
    });

    function afficheHTML(url) {
        var lrg = $("#pano1-" + num_pano).width();
        var haut = $("#pano1-" + num_pano).height();
        posX = Math.round(lrg * 0.02);
        posY = Math.round(haut * 0.02);
//            alert(hautImg + ", " + largImg+"ratio : "+ratio);
        $("#divHTML-" + num_pano).html("");
        $("#divHTML-" + num_pano).css({
            width: Math.round(lrg - 2 * posX) + "px",
            height: Math.round(haut - 2 * posY) + "px",
            padding: posY + "px " + posX + "px",
            top: "0px",
            left: "0px",
            zIndex: 10010,
            opacity: 0,
            backgroundColor: "rgba(0,0,0,0.7)"
        });
        $("<iframe>", {
            id: "hsHTML-" + num_pano,
            class: "hsHTML",
            src: url,
            height: Math.round(haut - 2 * posY) + "px",
            width: Math.round(lrg - 2 * posX) + "px"
        }).appendTo("#divHTML-" + num_pano);

        $("<img>", {
            id: "imgFerme-" + num_pano,
            class: "imgFerme",
            src: "panovisu/images/fermer.png",
            style: "height :30px;width : 30px;position : absolute;top : 10px;right : 15px;"
        }).appendTo("#divHTML-" + num_pano);

        $("#divHTML-" + num_pano).show();
        $("#divHTML-" + num_pano).animate({opacity: 1}, 1500);
    }


    /**
     * 
     * @returns {undefined}
     */
    function zoom() {
        if (fov > maxFOV) {
            fov = maxFOV;
        }
        if (fov < minFOV) {
            fov = minFOV;
        }
        camera.fov = fov;
        camera.updateProjectionMatrix();
        affiche();
    }
    /**
     * 
     * @param {type} larg1
     * @param {type} haut1
     * @returns {undefined}
     */
    function afficheBarre(larg1, haut1) {
        if (pano.width() < 400 || pano.height() < 200)
        {
            $("#barre-" + num_pano + " button").css({height: "20px", width: "20px", borderRadius: "0px"});
            $("#barre-" + num_pano + " button img").css({height: "18px", width: "18px", paddingBottom: "2px", marginLeft: "-2px"});
            $("#barre-" + num_pano).css({height: "25px"});
        }
        else
        {
            $("#barre-" + num_pano + " button").css({height: "30px", width: "30px", borderRadius: "3px"});
            $("#barre-" + num_pano + " button img").css({height: "26px", width: "26px", paddingBottom: "0px", marginLeft: "0px"});
            $("#barre-" + num_pano).css({height: "40px"});
        }
        setTimeout(function() {
            w1 = $("#barre-" + num_pano).width();
            h1 = $("#barre-" + num_pano).height();
            dX1 = parseInt(dX);
            dY1 = parseInt(dY);
            switch (positionX) {
                case "left" :
                    $("#barre-" + num_pano).css({left: dX1 + "px"});
                    break;
                case "center" :
                    posX = (larg1 - w1) / 2 + dX1;
                    $("#barre-" + num_pano).css({left: posX + "px"});
                    break;
                case "right" :
                    $("#barre-" + num_pano).css({right: dX1 + "px"});
                    break;
            }
            switch (positionY) {
                case "top" :
                    posY = -(haut1 - dY1);
                    $("#barre-" + num_pano).css({top: posY + "px"});
                    break;
                case "center" :
                    posY = -(haut1 + h1) / 2 - dY1;
                    $("#barre-" + num_pano).css({top: posY + "px"});
                    break;
                case "bottom" :
                    posY = -(h1 + dY1);
                    $("#barre-" + num_pano).css({top: posY + "px"});
                    break;
            }
        }, 200);
    }

    /**
     * 
     * @param {type} divObj
     * @returns {undefined}
     */
    function passeEnPleinEcran(divObj) {
        if (divObj.requestFullscreen) {
            divObj.requestFullscreen();
        }
        else if (divObj.msRequestFullscreen) {
            divObj.msRequestFullscreen();
        }
        else if (divObj.mozRequestFullScreen) {
            divObj.mozRequestFullScreen();
        }
        else if (divObj.webkitRequestFullscreen) {
            divObj.webkitRequestFullscreen();
        }
        inFullScreen = true;
        return;
    }
    /**
     * 
     * @returns {undefined}
     */
    function sortPleinEcran() {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.cancelFullScreen) {
            document.cancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        }
        inFullScreen = false;
        return;

    }
    /**
     * 
     * @param {type} nHotspot
     * @returns {undefined}
     */
    function chargeNouveauPano(nHotspot) {
        clearInterval(timers);
        longitude = 0;
        latitude = 0;
        fov = 75;
        $("#infoBulle-" + num_pano).hide();
        $("#infoBulle-" + num_pano).html("");
        isReloaded = true;
//        loader.load(function() {
//
//        });
        xmlFile = pointsInteret[nHotspot].contenu;
        hotSpot = new Array();
        pointsInteret = new Array();
        numHotspot = 0;
        $("#boussole-" + num_pano).hide();
        $("#marcheArret-" + num_pano).hide();
        chargeXML(xmlFile);
    }

    /**
     * 
     * @returns {undefined}
     */
    function pleinEcran() {
        element = document.getElementById("panovisu-" + num_pano);
        var largeurFenetre;
        if (screenfull.enabled) {
            screenfull.toggle(element);
        }

//        if (bPleinEcran) {
//            sortPleinEcran();
//        }
//        else {
//            passeEnPleinEcran(element);
//        }
        setTimeout(function() {
            changeTaille();
        }, 300);
    }

    /**
     * 
     * @returns {undefined}
     */
    function afficheInfo() {
        posGauche = (pano.width() - $("#infoPanovisu-" + num_pano).width()) / 2;
        posHaut = (pano.height() - $("#infoPanovisu-" + num_pano).height()) / 2;
        $("#infoPanovisu-" + num_pano).css({top: posHaut + "px", left: posGauche + "px"});
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + num_pano).css({display: "block"});
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheAide() {
        posGauche = (pano.width() - $("#aidePanovisu-" + num_pano).width()) / 2;
        posHaut = (pano.height() - $("#aidePanovisu-" + num_pano).height()) / 2;
        $("#aidePanovisu-" + num_pano).css({top: posHaut + "px", left: posGauche + "px"});
        if (bAfficheAide)
        {
            $("#aidePanovisu-" + num_pano).css({display: "block"});
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheErreur() {
        panoInfo = "<b>Panovisu version " +
                version +
                "</b><br><span style='color : red; font-weight : bold;'>Désolé votre navigateur ne supporte pas Webgl & Canvas</span><br>\n\
                Veuillez opter pour un navigateur plus récent<br>\n\
                Internet Explorer 10+ / Firefox / Chrome / Opéra / Safari<br><br>\n\
                &copy; Laurent LANG (2014)";
        $("#infoPanovisu-" + num_pano).css({width: "450px", height: "150px"});
        posGauche = (pano.width() - $("#infoPanovisu-" + num_pano).width()) / 2;
        posHaut = (pano.height() - $("#infoPanovisu-" + num_pano).height()) / 2;
        $("#infoPanovisu-" + num_pano).css({top: posHaut + "px", left: posGauche + "px"});
        $("#infoPanovisu-" + num_pano).html(panoInfo);
    }

    /**
     * 
     * @param {type} fenetre
     * @returns {undefined}
     */
    function init(fenetre) {
        if (boussole) {
            console.log("test  : " + boussolePositionX + " : " + boussoleDX + "px");
            $("#boussole-" + num_pano).css(boussolePositionX, boussoleDX + "px");
            $("#boussole-" + num_pano).css(boussolePositionY, boussoleDY + "px");
            $("#boussole-" + num_pano).css("opacity", boussoleOpacite);
            $("#boussole-" + num_pano).css({
                width: boussoleTaille + "px",
                height: boussoleTaille + "px"
            });
            $("#bousImg-" + num_pano).attr("src", "panovisu/images/boussoles/" + boussoleImage);
            var largBous = Math.round(parseInt(boussoleTaille) / 5);
            var posX = Math.round((parseInt(boussoleTaille) - largBous) / 2);
            $("#bousAig-" + num_pano).css({
                position: "absolute",
                top: "0px",
                left: posX + "px",
                width: largBous + "px",
                height: boussoleTaille + "px"
            });
            $("#bousImg-" + num_pano).css({
                width: boussoleTaille + "px",
                height: boussoleTaille + "px",
                top: "0px",
                left: "0px"
            });
            $("#boussole-" + num_pano).show();
        }
        $("#divSuivant-" + num_pano).hide();
        $("#divPrecedent-" + num_pano).hide();
        console.log("suivant : " + XMLsuivant + " precedent : " + XMLprecedent);
        if (XMLsuivant !== "") {
            $("#divSuivant-" + num_pano).show();
        }
        if (XMLprecedent !== "") {
            $("#divPrecedent-" + num_pano).show();
        }
        if (marcheArret) {
            console.log("test  : " + marcheArretPositionX + " : " + marcheArretDX + "px");
            $("#marcheArret-" + num_pano).css(marcheArretPositionX, marcheArretDX + "px");
            $("#marcheArret-" + num_pano).css(marcheArretPositionY, marcheArretDY + "px");
            $("#marcheArret-" + num_pano).css({
                width: marcheArretTaille + "px",
                height: marcheArretTaille + "px"
            });
            $("#marcheArret-" + num_pano).css("opacity", marcheArretOpacite);

            $("#MAImg-" + num_pano).attr("src", "panovisu/images/MA/" + marcheArretImage);
            $("#MAImg-" + num_pano).css({
                width: marcheArretTaille + "px",
                height: marcheArretTaille + "px",
                top: "0px",
                left: "0px"
            });
            $("#marcheArret-" + num_pano).show();
            elementsVisibles = true;


        }
        if (reseauxSociaux) {
            $("#reseauxSociaux-" + num_pano).css(reseauxSociauxPositionX, reseauxSociauxDX + "px");
            $("#reseauxSociaux-" + num_pano).css(reseauxSociauxPositionY, reseauxSociauxDY + "px");
            $("#reseauxSociaux-" + num_pano).css({
                width: (reseauxSociauxTaille + 5) * 4 + "px",
                height: reseauxSociauxTaille + "px"
            });
            $("#reseauxSociaux-" + num_pano).css("opacity", reseauxSociauxOpacite);

            $("#RSTW-" + num_pano).attr("src", "panovisu/images/reseaux/twitter.png");
            $("#RSGO-" + num_pano).attr("src", "panovisu/images/reseaux/google.png");
            $("#RSFB-" + num_pano).attr("src", "panovisu/images/reseaux/facebook.png");
            $("#RSEM-" + num_pano).attr("src", "panovisu/images/reseaux/email.png");
            $("#RSTW-" + num_pano + ", #RSGO-" + num_pano + ", #RSFB-" + num_pano + ", #RSEM-" + num_pano).css({
                width: reseauxSociauxTaille + "px",
                height: reseauxSociauxTaille + "px",
                top: "0px"
            });
            if (reseauxSociauxTwitter === "non")
                $("#RSTW-" + num_pano).hide(0);
            if (reseauxSociauxGoogle === "non")
                $("#RSGO-" + num_pano).hide(0);
            if (reseauxSociauxFacebook === "non")
                $("#RSFB-" + num_pano).hide(0);
            if (reseauxSociauxEmail === "non")
                $("#RSEM-" + num_pano).hide(0);
            $("#reseauxSociaux-" + num_pano).show();
            elementsVisibles = true;
        }

        $("#info-" + num_pano).html(panoTitre);

        (boutons === "oui") ? $("#boutons-" + num_pano).show() : $("#boutons-" + num_pano).hide();
        (deplacements === "oui") ? $("#deplacement-" + num_pano).css({display: "inline-block"}) : $("#deplacement-" + num_pano).hide();
        (zooms === "oui") ? $("#zoom-" + num_pano).css({display: "inline-block"}) : $("#zoom-" + num_pano).hide();
        (outils === "oui") ? $("#outils-" + num_pano).css({display: "inline-block"}) : $("#outils-" + num_pano).hide();
        (fs === "oui") ? $("#pleinEcran-" + num_pano).show() : $("#pleinEcran-" + num_pano).hide();
        (autoR === "oui") ? $("#auto-" + num_pano).show() : $("#auto-" + num_pano).hide;
        (souris === "oui") ? $("#souris-" + num_pano).show() : $("#souris-" + num_pano).hide();
        if (afficheTitre === "oui")
            $("#info-" + num_pano).fadeIn(2500);
        if (fenetreUniteX === "%") {
            largeur = Math.round(fenetreX * $("#" + fenPanoramique).parent().width());
        }
        else {
            largeur = fenetreX;
        }
        afficheInfoTitre();
        if (fenetreUniteY === "%") {
            hauteur = Math.round(fenetreY * $("#" + fenPanoramique).parent().height());
        }
        else {
            hauteur = fenetreY;
        }
        largeur += "px";
        hauteur += "px";
        $("#" + fenetre).width(largeur);
        $("#" + fenetre).height(hauteur);
        pano.width(largeur);
        pano.height(hauteur);
        afficheBarre(pano.width(), pano.height());
        var posit = (hauteur - $("#divSuivant-" + num_pano).height()) / 2;
        $("#divSuivant-" + num_pano).css("top", posit);
        $("#divPrecedent-" + num_pano).css("top", posit);

        afficheInfo();
        afficheInfoTitre();
        afficheAide();
        if (vignettes) {
            if (vignettesPosition === "bottom") {
                afficheVignettesHorizontales();
            }
            else
            {
                afficheVignettesVerticales();

            }
        }

    }
    function afficheNiveauSphere(image, niveau) {
        if (niveau < nombreNiveaux) {
            loader = new THREE.TextureLoader();
            var nomimage = panoImage.split("/")[0] + "/niveau" + niveau + "/" + panoImage.split("/")[1];
            loader.load(nomimage + ".jpg", function(texture) {
//                alert(nomimage);
                var img = nomimage.split("/")[2];
                var img2 = panoImage.split("/")[1];
                console.log(img + " ==>" + img2);
                //alert(texture);
                if (img === img2) {
                    var geometry = new THREE.SphereGeometry(405 - niveau, 50, 50);
                    var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                    var mesh1 = new THREE.Mesh(geometry, material);
                    mesh1.scale.x = -1;
                    scene.add(mesh1);
                    niveau += 1;
                    afficheNiveauSphere(image, niveau);
                    affiche();
                }
            });
        }
        else {
            loader = new THREE.TextureLoader();
            var nomimage = panoImage;
            loader.load(nomimage + ".jpg", function(texture) {
//                alert(nomimage);
                var img = nomimage.split("/")[1];
                var img2 = panoImage.split("/")[1];
                console.log(img + " ==>" + img2);
                if (img === img2)
                {
                    var geometry = new THREE.SphereGeometry(405 - niveau, 50, 50);
                    var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                    var meshF = new THREE.Mesh(geometry, material);
                    meshF.scale.x = -1;
                    scene.add(meshF);
                    affiche();
                }
            });

        }
    }
    /**
     * Initialisation du panoramique / équilatéral
     * 
     * @returns {undefined}
     */
    function initPanoSphere() {
        camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
        scene = new THREE.Scene();
        if (multiReso === "oui") {
            loader = new THREE.TextureLoader();
            var nomimage = panoImage.split("/")[0] + "/niveau0/" + panoImage.split("/")[1];
            loader.load(nomimage + ".jpg", function(texture) {
//                alert(nomimage);
                var geometry = new THREE.SphereGeometry(405, 50, 50);
                var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                mesh = new THREE.Mesh(geometry, material);
                mesh.scale.x = -1;
                scene.add(mesh);
                if (!isReloaded)
                {
                    if (supportWebgl())
                    {
                        renderer = new THREE.WebGLRenderer();
                    }
                    else {
                        if (supportCanvas()) {
                            renderer = new THREE.CanvasRenderer();
                        }
                        else {
                            afficheErreur();
                        }
                    }
                }
                afficheNiveauSphere(panoImage, 1);
                renderer.setSize(pano.width(), pano.height());
                container.append(renderer.domElement);
                for (var i = 0; i < pointsInteret.length; i++)
                {
                    var pi = pointsInteret[i];
                    creeHotspot(pi.long, pi.lat, pi.contenu, pi.image);
                }
                timers = setInterval(function() {
                    rafraichitHS();
                }, 50);

                affiche();
                afficheInfoTitre();
                pano1.fadeIn(2000, function() {
                    afficheBarre(pano.width(), pano.height());
                    affiche();
                });
                if (autoRotation === "oui")
                    demarreAutoRotation();
            });

        }
        else {
            loader = new THREE.TextureLoader();
            loader.load(panoImage + ".jpg", function(texture) {
                var geometry = new THREE.SphereGeometry(405, 50, 50);
                var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                mesh = new THREE.Mesh(geometry, material);
                mesh.scale.x = -1;
                scene.add(mesh);
                if (!isReloaded)
                {
                    if (supportWebgl())
                    {
                        renderer = new THREE.WebGLRenderer();
                    }
                    else {
                        if (supportCanvas()) {
                            renderer = new THREE.CanvasRenderer();
                        }
                        else {
                            afficheErreur();
                        }
                    }
                }
                renderer.setSize(pano.width(), pano.height());
                container.append(renderer.domElement);
                for (var i = 0; i < pointsInteret.length; i++)
                {
                    var pi = pointsInteret[i];
                    creeHotspot(pi.long, pi.lat, pi.contenu, pi.image);
                }
                timers = setInterval(function() {
                    rafraichitHS();
                }, 50);

                affiche();
                afficheInfoTitre();
                pano1.fadeIn(2000, function() {
                    afficheBarre(pano.width(), pano.height());
                    affiche();
                });
                if (autoRotation === "oui")
                    demarreAutoRotation();
            });
        }
    }

    function loadTexture1(path, niveau) {
        var texture = new THREE.Texture(texture_placeholder);
        var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
        var image = new Image();
        image.onload = function() {
            var img = path.split("/")[2].split("_")[0];
            var img2 = panoImage.split("/")[1];
            console.log(img + " ==>" + img2);
            if (img === img2) {
                texture.image = this;
                texture.needsUpdate = true;
                nbPanoCharges += 1;
                if (nbPanoCharges < 6)
                    $("#panovisuCharge-" + num_pano).html(nbPanoCharges + "/6");
                else
                {
                    $("#panovisuCharge-" + num_pano).html("&nbsp;");
                    afficheBarre(pano.width(), pano.height());
                    afficheInfoTitre();
                    if (multiReso === "oui" && niveau < nombreNiveaux - 1)
                    {
                        niveau += 1;
                        var nomimage = panoImage.split("/")[0] + "/niveau" + niveau + "/" + panoImage.split("/")[1];
                        //alert(nomimage);
                        var materials1 = [
                            loadTexture1(nomimage + '_r.jpg', niveau), // droite   x+
                            loadTexture1(nomimage + '_l.jpg', niveau), // gauche   x-
                            loadTexture1(nomimage + '_u.jpg', niveau), // dessus   y+
                            loadTexture1(nomimage + '_d.jpg', niveau), // dessous  y-
                            loadTexture1(nomimage + '_f.jpg', niveau), // devant   z+
                            loadTexture1(nomimage + '_b.jpg', niveau)  // derriere z-
                        ];
                        nbPanoCharges = 0;
                        var mesh1 = new THREE.Mesh(new THREE.CubeGeometry(405 - niveau, 405 - niveau, 405 - niveau, 10, 10, 10), new THREE.MeshFaceMaterial(materials1));
                        mesh1.scale.x = -1;
                        scene.add(mesh1);
                        affiche();
                    }
                    else {
                        //alert(panoImage);
                        var materials = [
                            loadTexture(panoImage + '_r.jpg'), // droite   x+
                            loadTexture(panoImage + '_l.jpg'), // gauche   x-
                            loadTexture(panoImage + '_u.jpg'), // dessus   y+
                            loadTexture(panoImage + '_d.jpg'), // dessous  y-
                            loadTexture(panoImage + '_f.jpg'), // devant   z+
                            loadTexture(panoImage + '_b.jpg')  // derriere z-
                        ];
                        var meshF = new THREE.Mesh(new THREE.CubeGeometry(395, 395, 395, 10, 10, 10), new THREE.MeshFaceMaterial(materials));
                        meshF.scale.x = -1;
                        scene.add(meshF);
                        affiche();
                    }

                }
            }

        };
        image.src = path;
        return material;
    }


    /**
     * 
     * @param {type} path
     * @returns {THREE.MeshBasicMaterial}
     */
    function loadTexture(path) {
        var texture = new THREE.Texture(texture_placeholder);
        var material = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
        var image = new Image();
        image.onload = function() {
            texture.image = this;
            texture.needsUpdate = true;
            nbPanoCharges += 1;
            if (nbPanoCharges < 6)
                $("#panovisuCharge-" + num_pano).html(nbPanoCharges + "/6");
            else
            {
                $("#panovisuCharge-" + num_pano).html("&nbsp;");
                afficheBarre(pano.width(), pano.height());
                afficheInfoTitre();
            }
            affiche();
        };
        image.src = path;
        return material;
    }

    /**
     * Initialisation du panoramique / faces de cube
     * 
     * @returns {undefined}
     */
    function initPanoCube() {

        $("#panovisuCharge-" + num_pano).html("0/6");
        camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
        scene = new THREE.Scene();
        if (!isReloaded)
        {
            texture_placeholder = document.createElement('canvas');
            texture_placeholder.width = 128;
            texture_placeholder.height = 128;
            var context = texture_placeholder.getContext('2d');
            context.fillStyle = 'rgb( 128, 128, 128 )';
            context.fillRect(0, 0, texture_placeholder.width, texture_placeholder.height);
            if (supportWebgl())
            {
                renderer = new THREE.WebGLRenderer();
            }
            else {
                if (supportCanvas()) {
                    renderer = new THREE.CanvasRenderer();
                }
                else {
                    afficheErreur();
                }
            }
        }
        if (multiReso === "oui") {
            var nomimage = panoImage.split("/")[0] + "/niveau0/" + panoImage.split("/")[1];
            //alert(nomimage);
            var materials = [
                loadTexture1(nomimage + '_r.jpg', 0), // droite   x+
                loadTexture1(nomimage + '_l.jpg', 0), // gauche   x-
                loadTexture1(nomimage + '_u.jpg', 0), // dessus   y+
                loadTexture1(nomimage + '_d.jpg', 0), // dessous  y-
                loadTexture1(nomimage + '_f.jpg', 0), // devant   z+
                loadTexture1(nomimage + '_b.jpg', 0)  // derriere z-
            ];
        }
        else {
            var materials = [
                loadTexture(panoImage + '_r.jpg'), // droite   x+
                loadTexture(panoImage + '_l.jpg'), // gauche   x-
                loadTexture(panoImage + '_u.jpg'), // dessus   y+
                loadTexture(panoImage + '_d.jpg'), // dessous  y-
                loadTexture(panoImage + '_f.jpg'), // devant   z+
                loadTexture(panoImage + '_b.jpg')  // derriere z-
            ];
        }
        mesh = new THREE.Mesh(new THREE.CubeGeometry(405, 405, 405, 10, 10, 10), new THREE.MeshFaceMaterial(materials));
        mesh.scale.x = -1;
        scene.add(mesh);
        renderer.setSize(pano.width(), pano.height());
        container.append(renderer.domElement);
        setTimeout(function() {
            for (var i = 0; i < pointsInteret.length; i++)
            {
                var pi = pointsInteret[i];
                creeHotspot(pi.long, pi.lat, pi.contenu, pi.image);
            }
            affiche();
            timers = setInterval(function() {
                rafraichitHS();
            }, 50);
            $("#info-" + num_pano).fadeIn(2000);
            pano1.fadeIn(2000, function() {
                affiche();
            });
            if (autoRotation === "oui")
                demarreAutoRotation();
        }, 1000);
    }
    function afficheInfoTitre() {
        var largeur = pano.width();
        var infoPosX = titreTailleFenetre;
        if (titreTailleUnite === "%") {
            infoPosX = (largeur - 10) * titreTailleFenetre;
        }
        $("#info-" + num_pano).css({
            fontFamily: "'" + titrePolice + "',Verdana,Arial,sans-serif",
            fontSize: titreTaillePolice,
            color: titreCouleur,
            backgroundColor: titreFond,
            opacity: titreOpacite,
            width: infoPosX + "px"
        });
        var infoPosX = titreTailleFenetre;
        if (titreTailleUnite === "%") {
            infoPosX = largeur * titreTailleFenetre;
        }
        infoPosX = (largeur - infoPosX) / 2;
//        alert("largeur : " + largeur + " titre taille : " + $("#info-" + num_pano).width() + " posX : " + infoPosX);

        $("#info-" + num_pano).css({
            marginLeft: infoPosX
        });

    }
    /**
     * 
     * @returns {undefined}
     */
    function changeTaille() {
        if (!bPleinEcran) {


            if (fenetreUniteX === "%") {
                largeur = Math.round(fenetreX * $("#" + fenPanoramique).parent().width());
            }
            else {
                largeur = fenetreX;
            }
            if (fenetreUniteY === "%") {
                hauteur = Math.round(fenetreY * $("#" + fenPanoramique).parent().height());
            }
            else {
                hauteur = fenetreY;
            }

            $("#" + fenPanoramique).css({
                width: largeur + "px",
                height: hauteur + "px"
            });
            pano.css({
                width: largeur + "px",
                height: hauteur + "px"
            });
        }
        else {
            largeur = screen.width;
            hauteur = screen.height;
            pano.css({
                width: largeur + "px",
                height: hauteur + "px"
            });
        }
        camera.aspect = pano.width() / pano.height();
        camera.updateProjectionMatrix();
        renderer.setSize(pano.width(), pano.height());
        affiche();
        setTimeout(function() {
            afficheInfo();
            afficheAide();
            afficheBarre(pano.width(), pano.height());
            afficheInfoTitre();
            if ((vignettesPano.length > 0) && (typeVignettes === "horizontales")) {
                var largeurFenetre;
                if (bPleinEcran) {
                    largeurFenetre = $("#pano1-" + num_pano).width();
                }
                else {
                    largeurFenetre = $("#pano1-" + num_pano).width() - 15;
                }
                console.log("taille :" + largeurFenetre);
                $("#divVignettes-" + num_pano).css({
                    width: largeurFenetre
                });
                $("#vignettes-" + num_pano).css({
                    transform: "translate(0px,0px)"
                });
                var tailleImages = ((vignettesTailleImage + 10) * vignettesPano.length);
                console.log(vignettesPano + "tailleImages " + tailleImages + " > " + largeurFenetre + " < " + vignettesTailleImage);
                if (largeurFenetre < tailleImages) {
                    console.log("plus petit");
                    $("#gaucheVignettes-" + num_pano).show();
                    $("#droiteVignettes-" + num_pano).show();
                }
                else
                {
                    console.log("plus grand");
                    $("#gaucheVignettes-" + num_pano).hide();
                    $("#droiteVignettes-" + num_pano).hide();
                }
            }
            if ((vignettesPano.length > 0) && (typeVignettes === "verticales")) {
                var hauteurFenetre;
                if (bPleinEcran) {
                    hauteurFenetre = $("#pano1-" + num_pano).height() - ($("#info-" + num_pano).height() - 10) - 37;
                }
                else {
                    hauteurFenetre = $("#pano1-" + num_pano).height() - ($("#info-" + num_pano).height() - 10) - 37;
                }
                console.log("hauteur :" + hauteurFenetre);
                $("#divVignettes-" + num_pano).css({
                    height: hauteurFenetre
                });
                $("#vignettes-" + num_pano).css({
                    transform: "translate(0px,0px)"
                });
                var tailleImages = ((vignettesTailleImage / 2 + 5) * vignettesPano.length);
                console.log(vignettesPano + "tailleImages " + tailleImages + " > " + largeurFenetre + " < " + vignettesTailleImage);
                if (hauteurFenetre < tailleImages) {
                    console.log("plus petit");
                    $("#hautVignettes-" + num_pano).show();
                    $("#basVignettes-" + num_pano).show();
                }
                else
                {
                    console.log("plus grand");
                    $("#hautVignettes-" + num_pano).hide();
                    $("#basVignettes-" + num_pano).hide();
                }
            }
            var posit = (hauteur - $("#divSuivant-" + num_pano).height()) / 2;
            $("#divSuivant-" + num_pano).css("top", posit);
            $("#divPrecedent-" + num_pano).css("top", posit);

        }, 300);

    }


    /**
     * 
     * @returns {undefined}
     */
    function demarreAutoRotation() {

        id = requestAnimationFrame(demarreAutoRotation);
        if (isUserInteracting === false) {

            longitude += 0.25;
        }

        latitude = Math.max(-85, Math.min(85, latitude));
        affiche();
    }
    /**
     * 
     * @returns {undefined}
     */
    function stoppeAutoRotation() {

        cancelAnimationFrame(id);
    }
    /**
     * 
     * @returns {undefined}
     */
    function creeBarreNavigation() {
        $("<button>", {type: "button", id: "xmoins-" + num_pano, class: "xmoins", title: "déplacement à gauche",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#deplacement-" + num_pano);
        $("<button>", {type: "button", id: "ymoins-" + num_pano, class: "ymoins", title: "déplacement vers le haut",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#deplacement-" + num_pano);
        $("<button>", {type: "button", id: "yplus-" + num_pano, class: "yplus", title: "déplacement vers le bas",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#deplacement-" + num_pano);
        $("<button>", {type: "button", id: "xplus-" + num_pano, class: "xplus", title: "déplacement à droite",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#deplacement-" + num_pano);
        $("<button>", {type: "button", id: "zoomPlus-" + num_pano, class: "zoomPlus", title: "zoom +",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#zoom-" + num_pano);
        $("<button>", {type: "button", id: "zoomMoins-" + num_pano, class: "zoomMoins", title: "zoom -",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#zoom-" + num_pano);
        $("<button>", {type: "button", id: "pleinEcran-" + num_pano, class: "pleinEcran", title: "plein ecran",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#outils-" + num_pano);
        $("<button>", {type: "button", id: "souris-" + num_pano, class: "souris", title: "change le mode de déplacement de la souris",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#outils-" + num_pano);
        $("<button>", {type: "button", id: "auto-" + num_pano, class: "auto", title: "autorotation (M/A)",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#outils-" + num_pano);
        $("<button>", {type: "button", id: "binfo-" + num_pano, class: "binfo", title: "A propos ...",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#outils-" + num_pano);
        $("<button>", {type: "button", id: "aide-" + num_pano, class: "aide", title: "Aide",
            style: "background-color : " + couleur + ";border : 1px solid " + bordure + ";"}).appendTo("#outils-" + num_pano);
    }
    /**
     * 
     * @returns {undefined}
     */
    function creeImagesboutons() {
        $("#xmoins-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/gauche.png", alt: ""}).appendTo("#xmoins-" + num_pano);
        $("#ymoins-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/haut.png", alt: ""}).appendTo("#ymoins-" + num_pano);
        $("#yplus-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/bas.png", alt: ""}).appendTo("#yplus-" + num_pano);
        $("#xplus-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/droite.png", alt: ""}).appendTo("#xplus-" + num_pano);
        $("#zoomPlus-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/zoomin.png", alt: ""}).appendTo("#zoomPlus-" + num_pano);
        $("#zoomMoins-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/zoomout.png", alt: ""}).appendTo("#zoomMoins-" + num_pano);
        $("#pleinEcran-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/fs.png", alt: ""}).appendTo("#pleinEcran-" + num_pano);
        $("#souris-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/souris.png", alt: ""}).appendTo("#souris-" + num_pano);
        $("#auto-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/rotation.png", alt: ""}).appendTo("#auto-" + num_pano);
        $("#binfo-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/info.png", alt: ""}).appendTo("#binfo-" + num_pano);
        $("#aide-" + num_pano).html("");
        $("<img>", {src: "panovisu/images/" + styleBoutons + "/aide.png", alt: ""}).appendTo("#aide-" + num_pano);
    }

    /**
     * 
     * @param {type} fenetrePanoramique
     * @returns {undefined}
     */
    function creeInfo(fenetrePanoramique) {
        $("<div>", {id: "infoPanovisu-" + num_pano, class: "infoPanovisu"}).appendTo("#" + fenetrePanoramique);
        panoInfo = "<b>Panovisu version " +
                version +
                "</b><br><br>Un visualiseur 100% HTML5 - 100% libre<br>" +
                "Utilise la bibliothèque <a href='http://threejs.org/' target='_blank' title='voir la page de three.js'>Three.js</a>" +
                "<br><br>&copy; " + programmeur + " (" + anneeProgramme + ")<br>" +
                "<br>une création : <a href='" + site + "' target='_blank'>" + siteTexte + "</a><br>" +
                "<div id='panovisuCharge-" + num_pano + "'>&nbsp;</div>cliquez pour fermer la fenêtre";
        $("#infoPanovisu-" + num_pano).css({width: "450px", height: "190px"});
        $("#infoPanovisu-" + num_pano).html(panoInfo);
    }
    /**
     * 
     * @param {type} fenetrePanoramique
     * @returns {undefined}
     */
    function creeAide(fenetrePanoramique) {
        $("<div>", {id: "aidePanovisu-" + num_pano, class: "aidePanovisu"}).appendTo("#" + fenetrePanoramique);
        panoInfo = "<span style='font-weight:bolder;font-size:1.2em;font-variant: small-caps;'>Aide à la Navigation</span><br><br><div style='width:100px;height:90px;padding-left:5px;display:inline-block;'><img style='width:90px' src='panovisu/images/aide_souris.png'/></div>" +
                "<div style='width : 270px;display:inline-block;vertical-align:top; text-align: justify;'>Pour vous déplacer dans la vue cliquez avec le bouton gauche de la souris " +
                "sur le panoramique puis déplacez la souris en maintenant le bouton de la souris enfoncé<br><br>Vous pouvez également utiliser le menu pour vous déplacer</div>" +
                "<div><br><br>cliquez pour fermer la fenêtre</div>";
        $("#aidePanovisu-" + num_pano).css({width: "400px", height: "220px"});
        $("#aidePanovisu-" + num_pano).html(panoInfo);
    }
    /**
     * 
     * @param {type} long
     * @param {type} lat
     * @param {type} xml
     * @param {type} img
     * @returns {undefined}
     */
    function creeHotspot(long, lat, xml, img) {
        var image = THREE.ImageUtils.loadTexture(img);
        var matSprite = new THREE.SpriteMaterial({map: image, color: 0xffffff, fog: true});
        var sprite = new THREE.Sprite(matSprite);
        phi = THREE.Math.degToRad(90 - lat);
        theta = THREE.Math.degToRad(long);
        var vect = new THREE.Vector3();
        vect.setX(Math.sin(phi) * Math.cos(theta));
        vect.setY(Math.cos(phi));
        vect.setZ(Math.sin(phi) * Math.sin(theta));
        hotSpot[numHotspot] = new spot();
        hotSpot[numHotspot].id = sprite.id;
        hotSpot[numHotspot].image = xml;
        hotSpot[numHotspot].texte = "";
        radius = 20;
        sprite.position.set(vect.x, vect.y, vect.z);
        sprite.position.normalize();
        sprite.position.multiplyScalar(radius);
        hotSpot[numHotspot].posY = sprite.position.y;
        scene.add(sprite);
        affiche();
        numHotspot += 1;
    }
    /**
     * 
     * @returns {undefined}
     */
    function rafraichitHS() {
        for (var i = 0, l = scene.children.length; i < l; i++) {
            var object = scene.children[ i ];
            for (var j = 0; j < hotSpot.length; j++)
            {
                if (object.id === hotSpot[j].id) {
                    if (pointsInteret[j].anime === "true")
                    {
                        affHS += deltaHS;
                        if ((affHS > 0.25) || (affHS < -0.25))
                            deltaHS = -deltaHS;
                        object.position.y = hotSpot[j].posY + affHS;
                        renderer.render(scene, camera);
                    }
                }
            }
        }
    }
    /**
     * 
     * @param {type} xmlFile
     * @returns {undefined}
     */
    function chargeXML(xmlFile) {
        $.get(xmlFile,
                function(d) {
                    var typeHSDefaut = "panoramique";
                    panoImage = "faces";
                    couleur = "none";
                    styleBoutons = "classique";
                    bordure = "none";
                    panoTitre = "";
                    titrePolice = "Monospace";
                    titreCouleur = "#fff";
                    titreTaillePolice = "13px";
                    titreTaille = "50%";
                    titreFond = "#000";
                    titreOpacite = "0.5";
                    panoType = "cube";
                    affInfo = "oui";
                    bAfficheInfo = true;
                    bAfficheAide = false;
                    afficheTitre = "oui";
                    zooms = "oui";
                    outils = "oui";
                    deplacements = "oui";
                    fs = "oui";
                    autoR = "oui";
                    souris = "oui";
                    boutons = "oui";
                    autoRotation = "non";
                    positionX = "center";
                    positionY = "bottom";
                    dX = "0";
                    dY = "10";
                    zeroNord = 0;
                    boussole = false;
                    boussoleImage = "rose2.png";
                    boussoleTaille = "120";
                    boussolePositionX = "right";
                    boussolePositionY = "bottom";
                    boussoleDX = "20";
                    boussoleDY = "20";
                    boussoleAffiche = "non";
                    boussoleOpacite = 0.75;
                    boussoleAiguille = "non";
                    marcheArret = false;
                    marcheArretAffiche = "non";
                    marcheArretOpacite = 0.8;
                    marcheArretImage = "MAVert.png";
                    marcheArretPositionX = "left";
                    marcheArretPositionY = "bottom";
                    marcheArretDX = 10;
                    marcheArretDY = 10;
                    marcheArretTaille = 30;
                    marcheArretNavigation = "non";
                    marcheArretTitre = "non";
                    marcheArretBoussole = "non";
                    marcheArretPlan = "non";
                    marcheArretReseaux = "non";
                    marcheArretVignettes = "non";
                    reseauxSociaux = false;
                    reseauxSociauxAffiche = "non";
                    reseauxSociauxOpacite = 0.8;
                    reseauxSociauxPositionX = "left";
                    reseauxSociauxPositionY = "bottom";
                    reseauxSociauxDX = 10;
                    reseauxSociauxDY = 10;
                    reseauxSociauxTaille = 30;
                    reseauxSociauxTwitter = "non";
                    reseauxSociauxFacebook = "non";
                    reseauxSociauxGoogle = "non";
                    reseauxSociauxEmail = "non";
                    vignettesAffiche = "non";
                    vignettes = false;
                    multiReso = "non";
                    nombreNiveaux = 0;
                    bSuivantPrecedent = false;
                    vignettesOpacite = 0.8;
                    vignettesPosition = "bottom";
                    vignettesFondCouleur = "green";
                    vignettesTailleImage = 120;
                    vignettesPano = new Array();
                    pointsInteret = new Array();
                    $("#divVignettes-" + num_pano).html("");
                    $("<img>", {id: "gaucheVignettes-" + num_pano, class: "positionVignettes", src: "panovisu/images/interface/gauche.jpg"}).appendTo("#divVignettes-" + num_pano);
                    $("<img>", {id: "droiteVignettes-" + num_pano, class: "positionVignettes", src: "panovisu/images/interface/droite.jpg"}).appendTo("#divVignettes-" + num_pano);
                    $("<img>", {id: "hautVignettes-" + num_pano, class: "positionVignettes", src: "panovisu/images/interface/haut.jpg"}).appendTo("#divVignettes-" + num_pano);
                    $("<img>", {id: "basVignettes-" + num_pano, class: "positionVignettes", src: "panovisu/images/interface/bas.jpg"}).appendTo("#divVignettes-" + num_pano);
                    $("#divVignettes-" + num_pano).hide();
                    $("#reseauxSociaux-" + num_pano).hide();
                    /**
                     * Définition du panoramique à afficher 
                     */
                    var XMLPano = $(d).find('pano');
                    panoImage = XMLPano.attr('image') || panoImage;
                    panoTitre = XMLPano.attr('titre') || panoTitre;
                    titrePolice = XMLPano.attr('titrePolice') || titrePolice;
                    titreCouleur = XMLPano.attr('titreCouleur') || titreCouleur;
                    titreTaille = XMLPano.attr('titreTaille') || titreTaille;
                    titreTaillePolice = XMLPano.attr('titreTaillePolice') || titreTaillePolice;
                    titreFond = XMLPano.attr('titreFond') || titreFond;
                    titreOpacite = XMLPano.attr('titreOpacite') || titreOpacite;
                    panoType = XMLPano.attr('type') || panoType;
                    multiReso = XMLPano.attr('multiReso') || multiReso;
                    nombreNiveaux = parseInt(XMLPano.attr('nombreNiveaux')) || nombreNiveaux;
                    autoRotation = XMLPano.attr('rotation') || autoRotation;
                    longitude = parseFloat(XMLPano.attr('regardX')) || longitude;
                    latitude = parseFloat(XMLPano.attr('regardY')) || latitude;
                    fov = XMLPano.attr('champVision') || fov;
                    afficheTitre = XMLPano.attr('afficheTitre') || afficheTitre;
                    affInfo = XMLPano.attr('afficheInfo') || affInfo;
                    zeroNord = parseFloat(XMLPano.attr('zeroNord')) || zeroNord;
                    if (isReloaded) {
                        affInfo = false;
                    }
                    if (affInfo === "oui") {
                        bAfficheInfo = true;
                    } else {
                        bAfficheInfo = false;
                    }
                    var XMLSuivantPrecedent = $(d).find('suivantPrecedent');
                    XMLsuivant = XMLSuivantPrecedent.attr('suivant') || "";
                    XMLprecedent = XMLSuivantPrecedent.attr('precedent') || "";
                    bSuivantPrecedent = (XMLsuivant !== "") || (XMLprecedent !== "");
                    console.log("Suivant - Précédent : " + bSuivantPrecedent + " => " + XMLsuivant + "," + XMLprecedent);
                    /*
                     * 
                     * 
                     */
                    var XMLBoussole = $(d).find('boussole');
                    boussoleAffiche = XMLBoussole.attr('affiche') || boussoleAffiche;
                    boussole = (boussoleAffiche === "oui");
                    boussoleImage = XMLBoussole.attr('image') || boussoleImage;
                    boussoleTaille = XMLBoussole.attr('taille') || boussoleTaille;
                    boussolePositionX = XMLBoussole.attr('positionX') || boussolePositionX;
                    boussolePositionY = XMLBoussole.attr('positionY') || boussolePositionY;
                    boussoleDX = XMLBoussole.attr('dX') || boussoleDX;
                    boussoleDY = XMLBoussole.attr('dY') || boussoleDY;
                    boussoleOpacite = parseFloat(XMLBoussole.attr('opacite')) || boussoleOpacite;
                    boussoleAiguille = XMLBoussole.attr('aiguille') || boussoleAiguille;
                    /*
                     * Reseaux Sociaux
                     * 
                     */
                    var XMLReseauxSociaux = $(d).find('reseauxSociaux');
                    reseauxSociauxAffiche = XMLReseauxSociaux.attr('affiche') || reseauxSociauxAffiche;
                    reseauxSociaux = (reseauxSociauxAffiche === "oui");
                    reseauxSociauxOpacite = parseFloat(XMLReseauxSociaux.attr('opacite')) || reseauxSociauxOpacite;
                    reseauxSociauxPositionX = XMLReseauxSociaux.attr('positionX') || reseauxSociauxPositionX;
                    reseauxSociauxPositionY = XMLReseauxSociaux.attr('positionY') || reseauxSociauxPositionY;
                    reseauxSociauxDX = parseFloat(XMLReseauxSociaux.attr('dX')) || reseauxSociauxDX;
                    reseauxSociauxDY = parseFloat(XMLReseauxSociaux.attr('dY')) || reseauxSociauxDY;
                    reseauxSociauxTaille = parseFloat(XMLReseauxSociaux.attr('taille')) || reseauxSociauxTaille;
                    reseauxSociauxTwitter = XMLReseauxSociaux.attr('twitter') || reseauxSociauxTwitter;
                    reseauxSociauxFacebook = XMLReseauxSociaux.attr('facebook') || reseauxSociauxFacebook;
                    reseauxSociauxGoogle = XMLReseauxSociaux.attr('google') || reseauxSociauxGoogle;
                    reseauxSociauxEmail = XMLReseauxSociaux.attr('email') || reseauxSociauxEmail;
                    /*
                     * Bouton de masquage
                     * 
                     */

                    var XMLMarcheArret = $(d).find('marcheArret');
                    marcheArretAffiche = XMLMarcheArret.attr('affiche') || marcheArretAffiche;
                    marcheArret = (marcheArretAffiche === "oui");
                    marcheArretImage = XMLMarcheArret.attr('image') || marcheArretImage;
                    marcheArretOpacite = parseFloat(XMLMarcheArret.attr('opacite')) || marcheArretOpacite;
                    marcheArretPositionX = XMLMarcheArret.attr('positionX') || marcheArretPositionX;
                    marcheArretPositionY = XMLMarcheArret.attr('positionY') || marcheArretPositionY;
                    marcheArretDX = parseFloat(XMLMarcheArret.attr('dX')) || marcheArretDX;
                    marcheArretDY = parseFloat(XMLMarcheArret.attr('dY')) || marcheArretDY;
                    marcheArretTaille = parseFloat(XMLMarcheArret.attr('taille')) || marcheArretTaille;
                    marcheArretNavigation = XMLMarcheArret.attr('navigation') || marcheArretNavigation;
                    marcheArretBoussole = XMLMarcheArret.attr('boussole') || marcheArretBoussole;
                    marcheArretTitre = XMLMarcheArret.attr('titre') || marcheArretTitre;
                    marcheArretPlan = XMLMarcheArret.attr('plan') || marcheArretPlan;
                    marcheArretReseaux = XMLMarcheArret.attr('reseaux') || marcheArretReseaux;
                    marcheArretVignettes = XMLMarcheArret.attr('vignettes') || marcheArretVignettes;

                    //alert(boussoleImage);
                    /**
                     * Défintion pour la barre des boutons
                     */
                    var XMLBoutons = $(d).find('boutons');
                    deplacements = XMLBoutons.attr('deplacements') || deplacements;
                    styleBoutons = XMLBoutons.attr('styleBoutons') || styleBoutons;
                    zooms = XMLBoutons.attr('zoom') || zooms;
                    outils = XMLBoutons.attr('outils') || outils;
                    fs = XMLBoutons.attr('fs') || fs;
                    autoR = XMLBoutons.attr('rotation') || autoR;
                    souris = XMLBoutons.attr('souris') || souris;
                    boutons = XMLBoutons.attr('visible') || boutons;
                    positionX = XMLBoutons.attr('positionX') || positionX;
                    positionY = XMLBoutons.attr('positionY') || positionY;
                    couleur = XMLBoutons.attr('couleur') || couleur;
                    bordure = XMLBoutons.attr('bordure') || bordure;
                    $("#xmoins-" + num_pano + ",#xplus-" + num_pano + ",#ymoins-" + num_pano + ",#yplus-" + num_pano + ",#zoomPlus-" + num_pano + ",#zoomMoins-" + num_pano +
                            ",#pleinEcran-" + num_pano + ",#souris-" + num_pano + ",#auto-" + num_pano + ",#binfo-" + num_pano + ",#aide-" + num_pano).css({
                        backgroundColor: couleur,
                        border: "1px solid " + bordure
                    });
                    dX = XMLBoutons.attr('dX') || dX;
                    dY = XMLBoutons.attr('dY') || dY;
                    i = 0;
                    /*
                     * Hotspots
                     */
                    $(d).find('point').each(function() {
                        pointsInteret[i] = new pointInteret();
                        pointsInteret[i].type = $(this).attr('type') || pointsInteret[i].type;
                        switch (pointsInteret[i].type) {
                            case "panoramique" :
                                pointsInteret[i].contenu = $(this).attr('xml');
                                break;
                            case "image" :
                                pointsInteret[i].contenu = $(this).attr('img');
                                break;
                            case "html" :
                                pointsInteret[i].contenu = $(this).attr('url');
                                break;
                        }
                        pointsInteret[i].info = $(this).attr('info') || "";
                        pointsInteret[i].image = $(this).attr('image') || "panovisu/images/sprite2.png";
                        pointsInteret[i].long = $(this).attr('long') || 0;
                        pointsInteret[i].anime = $(this).attr('anime') || "false";
                        pointsInteret[i].lat = $(this).attr('lat') || 0;
                        i++;
                    });
                    /*
                     * 
                     * barre des vignettes
                     */
                    var XMLVignettes = $(d).find('vignettes');
                    vignettesAffiche = XMLVignettes.attr('affiche') || vignettesAffiche;
                    vignettes = (vignettesAffiche === "oui");
                    vignettesOpacite = parseFloat(XMLVignettes.attr('opacite')) || vignettesOpacite;
                    vignettesPosition = XMLVignettes.attr('position') || vignettesPosition;
                    vignettesFondCouleur = XMLVignettes.attr("fondCouleur") || vignettesFondCouleur;
                    vignettesTaille = parseFloat(XMLVignettes.attr('taille')) || vignettesTaille;
                    vignettesTailleImage = parseFloat(XMLVignettes.attr('tailleImage')) || vignettesTailleImage;


                    /*
                     *   vignettes des panoramiques
                     */
                    i = 0;
                    $(d).find('imageVignette').each(function() {
                        vignettesPano[i] = new vignettePano();
                        vignettesPano[i].xml = $(this).attr('xml');
                        vignettesPano[i].image = $(this).attr('image');
                        vignettesPano[i].txt = $(this).attr('texte') || "";
                        i++;
                    });
                    console.log(vignettesPano);



                    /**
                     * Initialisation de l'interface
                     */
                    if (titreTaille.match("[px]", "g"))
                    {
                        titreTailleUnite = "px";
                        titreTailleFenetre = parseInt(titreTaille);
                    }
                    else
                    {
                        titreTailleUnite = "%";
                        titreTailleFenetre = parseInt(titreTaille) / 100.0;
                    }

                    init(fenPanoramique);
                    creeImagesboutons();
                    /**
                     * Initialisation de l'affichage du panoramique
                     */
                    switch (panoType)
                    {
                        case "cube":
                            initPanoCube();
                            break;
                        case "sphere":
                            initPanoSphere();
                            break;
                    }
                    changeTaille();
                });

    }

    /**
     * Création du contexte de fenetre
     * 
     * @param {type} fenetre
     * @returns {undefined}
     */
    function creeContexte(fenetre) {
        /**
         * Création de la barre de titreF
         */
        var fenetrePanoramique = "panovisu-" + num_pano;
        $("<div>", {id: fenetrePanoramique, class: "panovisu", style: "width : 100%;height : 100%;position: relative;"}).appendTo("#" + fenetre);
        $("<div>", {id: "boussole-" + num_pano, class: "boussole"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "bousImg-" + num_pano, class: "bousImg", src: ""}).appendTo("#boussole-" + num_pano);
        $("<img>", {id: "bousAig-" + num_pano, class: "bousAig", src: "panovisu/images/boussoles/aiguille.png"}).appendTo("#boussole-" + num_pano);
        $("#boussole-" + num_pano).hide();
        $("<div>", {id: "marcheArret-" + num_pano, class: "marcheArret"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "MAImg-" + num_pano, class: "MAImg", src: "", title: "Affiche/Masque les éléments"}).appendTo("#marcheArret-" + num_pano);
        $("#marcheArret-" + num_pano).hide();
        $("<div>", {id: "reseauxSociaux-" + num_pano, class: "reseauxSociaux"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "RSTW-" + num_pano, class: "RS reseauSocial-twitter", src: "", title: "twitter"}).appendTo("#reseauxSociaux-" + num_pano);
        $("<img>", {id: "RSGO-" + num_pano, class: "RS reseauSocial-google", src: "", title: "google"}).appendTo("#reseauxSociaux-" + num_pano);
        $("<img>", {id: "RSFB-" + num_pano, class: "RS reseauSocial-fb", src: "", title: "facebook"}).appendTo("#reseauxSociaux-" + num_pano);
        $("<a>", {id: "lienEmail" + num_pano, class: "RS reseauSocial-email", href: ""}).appendTo("#reseauxSociaux-" + num_pano);
        $("<img>", {id: "RSEM-" + num_pano, src: "", title: "email"}).appendTo("#lienEmail" + num_pano);
        $("#reseauxSociaux-" + num_pano).hide();
        $("<div>", {id: "info-" + num_pano, class: "info"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "infoBulle-" + num_pano, class: "infoBulle", style: "display:none;position: absolute;"}).appendTo("#" + fenetrePanoramique);
        $("#infoBulle-" + num_pano).html("infoBulle");
        /**
         * création du conteneur du panoramique
         */
        $("<div>", {id: "pano1-" + num_pano, class: "pano1"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "container-" + num_pano, class: "container"}).appendTo("#pano1-" + num_pano);
        $("<div>", {id: "divVignettes-" + num_pano, class: "vignettes"}).appendTo("#pano1-" + num_pano);

        $("#divVignettes-" + num_pano).hide();
        $("<div>", {id: "divImage-" + num_pano, class: "vignettes"}).appendTo("#pano1-" + num_pano);
        $("#divImage-" + num_pano).hide();
        $("<div>", {id: "divHTML-" + num_pano, class: "vignettes"}).appendTo("#pano1-" + num_pano);
        $("#divHTML-" + num_pano).hide();


        $("<div>", {id: "divPrecedent-" + num_pano, class: "precedent",title : "Panoramique précédent"}).appendTo("#pano1-" + num_pano);
        $("<div>", {id: "divSuivant-" + num_pano, class: "suivant",title : "Panoramique suivant"}).appendTo("#pano1-" + num_pano);
        $("#divPrecedent-" + num_pano).hide();
        $("#divSuivant-" + num_pano).hide();

        /**
         * Création de la barre de boutons
         * et des trois éléments de barre 
         *              - déplacement, 
         *              - zoom 
         *              - outils (plein écran, mode souris et autorotation)
         */
        $("<div>", {id: "boutons-" + num_pano, class: "boutons"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "barre-" + num_pano, class: "barre"}).appendTo("#boutons-" + num_pano);
        $("<div>", {id: "deplacement-" + num_pano, class: "deplacement"}).appendTo("#barre-" + num_pano);
        $("<div>", {id: "zoom-" + num_pano, class: "zoom"}).appendTo("#barre-" + num_pano);
        $("<div>", {id: "outils-" + num_pano, class: "outils"}).appendTo("#barre-" + num_pano);
        /**
         * On rajoute enfin les boutons & les fenêtre d'information.
         */
        creeBarreNavigation();
        creeInfo(fenetrePanoramique);
        creeAide(fenetrePanoramique);
        /**
         * Création des racourcis vers les différentes fenêtres
         */
        container = $("#container-" + num_pano);
        pano = $("#" + fenetrePanoramique);
        pano1 = $("#pano1-" + num_pano);
    }



    /**
     * intégration du panoramique 
     * 
     * @param {type} contexte
     * @returns {undefined}
     */
    this.initialisePano = function(contexte)
    {
        var defaut = {
            xml: 'xml/panovisu.xml',
            fenX: "75%",
            fenY: "80%",
            panoramique: "panovisu",
            minFOV: 25,
            maxFOV: 120
        };
        contexte = $.extend(defaut, contexte);
        fenPanoramique = contexte.panoramique;
        maxFOV = contexte.maxFOV;
        minFOV = contexte.minFOV;
        var fenetre = fenPanoramique;
        $(fenetre).css({overflow: "hidden"});
        creeContexte(fenetre);
        xmlFile = contexte.xml;
        if (contexte.fenX.match("[px]", "g"))
        {
            fenetreUniteX = "px";
            fenetreX = parseInt(contexte.fenX);
        }
        else
        {
            fenetreUniteX = "%";
            fenetreX = parseInt(contexte.fenX) / 100.0;
        }
        if (contexte.fenY.match("[px]", "g"))
        {
            fenetreUniteY = "px";
            fenetreY = parseInt(contexte.fenY);
        }
        else
        {
            fenetreUniteY = "%";
            fenetreY = parseInt(contexte.fenY) / 100.0;
        }
        /**
         * passe en mode souris alternatif si appareil mobile
         */
        if (estTactile())
            mode = 0;

        /**
         * lecture du fichier XML
         */
        chargeXML(xmlFile);
    };
}

/*
 * ajoute la feuille de style pour l'affichage des panoramiques
 * 
 */
$("head").append(
        $(document.createElement("link")).attr({rel: "stylesheet", type: "text/css", href: "panovisu/css/panovisu.css", media: "screen"})
        );

/*
 * Teste si le navigateur supporte les fonctions HTML5-3D
 */
if (!supportWebgl() && !supportCanvas()) {
    alert("Navigateur non supporté");
}
