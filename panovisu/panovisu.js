/* global THREE, chainesTraduction, OpenLayers, javafx, version, programmeur, anneeProgramme */

/**
 * @name panoVisu
 * 
 * @version 1.3.dev
 * @author LANG Laurent
 */




/**
 * teste si appareil tactile
 * 
 * @returns {window|String}
 */

function estTactile() {
    return !!('ontouchstart' in window);
}


/**
 * 
 * @param {type} iNumPano
 * @returns {panovisu}
 */
function panovisu(iNumPano) {

    var idInfoBulle = "infoBulle",
            ClasseInfobulle = "infoBulleMarche",
            suiviSouris = true,
            donnees = "_infoBulle",
            decalageX = 20,
            decalageY = 10,
            container;


    afficheInfobulle = function (evenement) {
        var haut = evenement.pageY + decalageY;
        var gauche = evenement.pageX + decalageX;

        if (gauche + $("#" + idInfoBulle).width() + 40 > $("#container-" + iNumPano).width()) {
            gauche = $("#container-" + iNumPano).width() - $("#" + idInfoBulle).width() - decalageX - 40;
        }
        if (haut + $("#" + idInfoBulle).height() + 20 > $("#container-" + iNumPano).height()) {
            haut = evenement.pageY - decalageY - $("#" + idInfoBulle).height() - 20;
        }
        $("#" + idInfoBulle).html($(evenement.target).data(donnees)).css({
            position: "absolute", top: haut, left: gauche
        }).show();
    };
    $(document).on("mouseenter", "*[title]", function (evenement) {
        var attr = $(this).attr('title');
        if ($(this).attr("title") !== "" && $(this).attr("title") !== null && typeof attr !== typeof undefined && attr !== false) {
            $(this).data(donnees, $(this).attr("title"));
            $(this).removeAttr("title").addClass(ClasseInfobulle);
            $("<div>", {id: idInfoBulle, class: idInfoBulle}).appendTo("#container-" + iNumPano);

            //$("<div id='" + idInfoBulle + "' />").appendTo("body");
            afficheInfobulle(evenement);
        }
    });
    $(document).on("mouseleave", "." + ClasseInfobulle, function (e) {
        $(this).attr("title", $(this).data(donnees)).removeClass(ClasseInfobulle);
        $("#" + idInfoBulle).remove();
    });
    if (suiviSouris) {
        $(document).on("mousemove", "." + ClasseInfobulle, afficheInfobulle);
    }


    var tmp = window.location.search.substring(1).split("&");
    var GET = [];
    for (var i in tmp)
        if (tmp[i].indexOf("=") !== -1)
            GET[decodeURI(tmp[i].substring(0, tmp[i].indexOf("=")))] = decodeURI(tmp[i].substring(tmp[i].indexOf("=") + 1));
        else
            GET[decodeURI(tmp[i])] = '';
    /**
     * 
     * @type THREE.PerspectiveCamera|THREE.PerspectiveCamera|THREE.PerspectiveCamera|THREE.PerspectiveCamera|THREE.PerspectiveCamera|THREE.PerspectiveCamera
     */
    var camera,
            scene,
            renderer,
            materiaux = [],
            textures = [],
            meshes = [],
            geometries = [],
            iNbMateriaux = 0,
            iNbTextures = 0,
            iNbMeshes = 0,
            iNbGeometries = 0;

    /**
     * 
     * @returns {panovisu.spot}
     */
    function spot() {
        this.id = 0;
        this.image = "";
        this.texte = "";
        this.long = 0;
        this.lat = 0;
        this.posY = 0;
    }

    /**
     * 
     * @returns {panovisu.pointInteret}
     */
    function pointInteret() {
        this.type = "panoramique";
        this.long = 0;
        this.lat = 0;
        this.contenu = "";
        this.image = "";
        this.anime = "false";
        this.longitude = -1000;
        this.latitude = -1000;
        this.fov = 0;
        this.info = "";
        this.largeur = "100%";
        this.position = "center";
        this.couleur = "rgba(0,0,0,0.7)";
        this.taille = "30px";
    }

    /**
     * 
     * @returns {panovisu.vignettePano}
     */
    function vignettePano() {
        this.image = "";
        this.xml = "";
        this.txt = "";
    }

    /**
     * 
     * @returns {panovisu.cbMenuPano}
     */
    function cbMenuPano() {
        this.image = "";
        this.xml = "";
        this.titre = "";
        this.sousTitre = "";
        this.select = "";
    }

    /**
     * 
     * @returns {panovisu.pointPlan}
     */
    function pointPlan() {
        this.positX = 0;
        this.positY = 0;
        this.xml = "";
        this.texte = "";
    }

    /**
     * 
     * @returns {panovisu.pointCarte}
     */
    function pointCarte() {
        this.positX = 0;
        this.positY = 0;
        this.xml = "";
        this.html = "";
        this.image = "";
    }

    /**
     * 
     * @returns {panovisu.imageFond}
     */
    function imageFond() {
        this.fichier = "";
        this.url = "";
        this.infobulle = "";
        this.posX = "";
        this.posY = "";
        this.offsetX = 0;
        this.offsetY = 0;
        this.opacite = 1.0;
        this.tailleX = "";
        this.tailleY = "";
        this.masquable = true;
        this.cible = "interne";
        this.calque = 1;
    }

    /**
     * 
     * @returns {panovisu.boutonTelecommande}
     */
    function boutonTelecommande() {
        this.id = "";
        this.alt = "";
        this.title = "";
        this.shape = "";
        this.coords = "";
        this.centerX = 0;
        this.centerY = 0;
    }

    /**
     * 
     * @returns {panovisu.defRadar}
     */
    function defRadar() {
        this.longitude = 0;
        this.latitude = 0;
        this.angleOuverture = 0;
        this.angleradar = 0;
        this.pcRayon = 0;
        this.couleurLigne = "";
        this.couleurFond = "";
        this.opaciteFond = "";
    }

    if (!Date.now) {
        Date.now = function now() {
            return new Date().getTime();
        };
    }
    /**
     * 
     * @type @exp;document@call;getElementById
     */
    var nouvLong = -1000,
            nouvLat = -1000,
            nouvFov = 0,
            conteneur,
            isUserInteracting = false,
            isZoom = false,
            ddxAutorotation = 0,
            dxAutorotation = 0,
            timerAutorotation,
            timerDebutRotation,
            longitudeDebutRotation,
            bEltMasque = null,
            timer,
            ddx,
            ddy,
            telecEnCours,
            bDeplacement = false,
            bAutorotation = false,
            bWebGL = true,
            positXRadar,
            positYRadar,
            maxTextureSize,
            timers,
            i,
            affHS = 0,
            deltaHS = 0.02,
            numHS,
            numHotspot = 0,
            mouseMove,
            bDejaCharge = false,
            bReloaded = false,
            arrHotSpot = new Array(),
            arrPointsInteret = new Array(),
            arrVignettesPano = new Array(),
            arrComboMenuPano = new Array(),
            arrPointsPlan = new Array(),
            arrImagesFond = new Array(),
            arrBoutonsTelecommande = new Array(),
            arrZonesTelecommande = new Array(),
            iNombreImageFond = 0,
            bPlanRentre,
            bCarteRentre,
            iNbPointCarte,
            bVignettesRentre,
            iMode = 1,
            longitude = 0,
            latitude = 0,
            minLat = -1000,
            maxLat = 1000,
            bDessusHS = false,
            fenPanoramique,
            texture_placeholder,
            fichierXML = "",
            bUserInteracting = false,
            onPointerDownPointerX = 0,
            onPointerDownPointerY = 0,
            onPointerZoom = 0,
            distance = 0,
            onPointerDownLon = 0,
            onPointerDownLat = 0,
            phi = 0,
            theta = 0,
            fov = 50,
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
            positionVignettesY = 0,
            memMaxFOV,
            memFOV,
            memLatitude,
            memLongitude,
            bMemCarteRentre,
            bMemPlanRentre,
            bMemVignettesRentre,
            bMemAutorotation,
            bMemAutoTour,
            bLittleDisabled = false,
            bNormalDisbled = true,
            masqueTout = "",
            bLittlePlanetView = false,
            allerLongit,
            allerLatit,
            arreteMouvement,
            deltaLongit,
            longitInit,
            latitInit,
            fovInit,
            deltaFOV,
            start,
            seconds,
            nombreIterMax,
            deltaLatit,
            nombreIter = 0,
            bDisableSuivant,
            bDisablePrecedent,
            strLangage = "fr_FR",
            bClavierActif = false,
            cDegToRad = Math.PI / 180.0
            ;

    /**
     * Variables pour OpenLayers
     * 
     * @type OpenLayers.Map
     */
    var
            map,
            openStreetMap,
            googleMap,
            googleHybride,
            googlePhysique,
            googleSatellite,
            bingMap = null,
            bingHybride = null,
            bingSatellite = null,
            layerMarqueurs,
            layerRadar,
            arrMarqueurs = new Array(),
            iMaxMarqueur = -1,
            radar,
            ctrlLayerSwitcher,
            bRadar = false;
    /**
     * Variables par défaut pour l'affichage du panoramique
     * 
     * @type String|@exp;_L1@pro;panoImage|@exp;XMLPano@call;attr
     */
    var strPanoImage,
            loader,
            strCouleur = "rgba(255,255,255,0)",
            bSuivantPrecedent,
            XMLsuivant,
            XMLprecedent,
            strStyleBoutons,
            strBordure = "rgba(255,255,255,0)",
            strPanoTitre,
            strPanoTitre2,
            strMultiReso,
            iNombreNiveaux,
            strTitrePolice,
            strTitreTaille,
            strTitreTailleUnite,
            iTitreTailleFenetre,
            strTitreTaillePolice,
            strTitreTaillePolice2,
            strTitrePosition,
            strTitreDecalage,
            strTitreCouleur,
            strTitreFond,
            strTitreOpacite,
            bFenetreInfoPersonnalise,
            strFenetreInfoImage,
            fenetreInfoTaille,
            fenetreInfoDX,
            fenetreInfoDY,
            fenetreInfoOpacite,
            strFenetreInfoURL,
            strFenetreInfoTexteURL,
            strFenetreInfoCouleurURL,
            fenetreInfoTailleURL,
            fenetreInfoDXURL,
            fenetreInfoDYURL,
            bFenetreAidePersonnalise,
            strFenetreAideImage,
            fenetreAideTaille,
            fenetreAideDX,
            fenetreAideDY,
            fenetreAideOpacite,
            strDiaporamaCouleur,
            vitesseAutorotation,
            bAutoTour,
            bBtnAutoTour,
            positBtnAutoTourX,
            positBtnAutoTourY,
            offsetBtnAutoTourX,
            offsetBtnAutoTourY,
            tailleBtnAutoTour,
            strAutoTourType,
            autoTourLimite,
            autoTourDemarrage,
            dejaDemarre = false,
            bPetitePlaneteDemarrage,
            strPanoType,
            strAffInfo,
            bAfficheInfo,
            bAfficheAide,
            strAfficheTitre,
            strZooms,
            strOutils,
            strDeplacements,
            strFS,
            strAutoRotation,
            strModeSouris,
            iEspacementBoutons,
            strBoutons,
            strAutoRotationMarche,
            strPositionX,
            strPositionY,
            strDX,
            strDY,
            mesh,
            carteCalque = 1,
            planCalque = 1,
            comboCalque = 1,
            vignettesCalque = 1,
            partageCalque = 1,
            masquageCalque = 1,
            boussoleCalque = 1,
            barreCCalque = 1,
            barrePCalque = 1,
            atCalque = 1,
            titreCalque = 1,
            suivPrecCalque = 1
            ;
    /**
     * 
     * @type @call;$
     */
    var pano,
            pano1,
            zeroNord,
            bBoussole,
            strBoussoleImage,
            strBoussoleTaille,
            strBoussolePositionX,
            strBoussolePositionY,
            strBoussoleDX,
            strBoussoleDY,
            strBoussoleAffiche,
            boussoleOpacite,
            strBoussoleAiguille,
            elementsVisibles,
            bMarcheArret,
            bMarcheArretAffiche,
            strMarcheArretImage,
            marcheArretOpacite,
            strMarcheArretPositionX,
            strMarcheArretPositionY,
            marcheArretDX,
            marcheArretDY,
            strMarcheArretTitre,
            strMarcheArretNavigation,
            strMarcheArretBoussole,
            strMarcheArretPlan,
            strMarcheArretReseaux,
            strMarcheArretCombo,
            strMarcheArretSuivPrec,
            strMarcheArretHotspots,
            strMarcheArretVignettes,
            marcheArretTaille,
            bReseauxSociaux,
            strReseauxSociauxAffiche,
            reseauxSociauxOpacite,
            strReseauxSociauxPositionX,
            strReseauxSociauxPositionY,
            reseauxSociauxDX,
            reseauxSociauxDY,
            reseauxSociauxTaille,
            strReseauxSociauxTwitter,
            strReseauxSociauxFacebook,
            strReseauxSociauxGoogle,
            strReseauxSociauxEmail,
            strVignettesAffiche,
            bVignettes,
            vignettesOpacite,
            strVignettesPosition,
            strVignettesFondCouleur,
            strVignettesTexteCouleur,
            vignettesTaille,
            vignettesTailleImage,
            bPlanAffiche,
            planLargeur,
            strPlanImage,
            strPlanPosition,
            strPlanCouleurFond,
            strPlanCouleurTexte,
            opacitePlan,
            planNord,
            strPlanBoussolePosition,
            iPlanBoussoleX,
            iPlanBoussoleY,
            bRadarAffiche,
            iRadarTaille,
            radarOpacite,
            strRadarCouleurFond,
            strRadarCouleurLigne,
            strCarteAff,
            bCarteAffiche,
            strCartePosition,
            strCarteCouleurFond,
            strCarteCouleurTexte,
            opaciteCarte,
            carteLargeur,
            carteNord,
            strAfficheRadarCarte,
            iRadarCarteTaille,
            radarCarteOpacite,
            strRadarCarteCouleurFond,
            strRadarCarteCouleurLigne,
            radarCarteAffiche,
            arrPointsCarte = new Array(),
            carteHauteur,
            iCarteZoom,
            coordCentreLong,
            coordCentreLat,
            strNomLayerCarte,
            strBingAPIKey,
            bAfficheMenuContextuel,
            bPrecedentSuivantMenuContextuel,
            bPlaneteMenuContextuel,
            bMenuPersonnalise1,
            bMenuPersonnalise2,
            strLibelleMenuContextuel1,
            strLibelleMenuContextuel2,
            strUrlMenuContextuel1,
            strUrlMenuContextuel2,
            strTelecommande,
            bTelecommande,
            strTelecommandeFS,
            strTelecommandeAutorotation,
            strTelecommandeSouris,
            strTelecommandeInfo,
            strTelecommandeAide,
            strTelecommandePositionX,
            strTelecommandePositionY,
            telecommandeDX,
            telecommandeDY,
            telecommandeTaille,
            telecommandeTailleBouton,
            strLien1BarrePersonnalisee,
            strLien2BarrePersonnalisee,
            strComboMenu,
            bComboMenuAffiche,
            strComboMenuPositionX,
            strComboMenuPositionY,
            comboMenuDX,
            comboMenuDY;

    /**
     * Geston des Evènements souris / Clavier / Touche sur écran
     * 
     */


    $(document).on("webkitfullscreenchange mozfullscreenchange fullscreenchange MSFullscreenChange", function () {
        bPleinEcran = !bPleinEcran;
        changeTaille();
    });

    $(document).on("touchstart click", ".hotSpots", function (evenement) {
        evenement.preventDefault();
        idHS = $(this).attr("id");
        numHS = parseInt(idHS.split("-")[1]);
        switch (arrPointsInteret[numHS].type) {
            case "panoramique" :
                //$("#container-" + iNumPano).fadeOut(200, function () {
                chargeNouveauPano(numHS);
                //});
                break;
            case "image" :
                afficheImage(numHS);
                break;
            case "html" :
                afficheHTML(numHS);
                break;
        }
    });

    $(document).on("mouseover", ".hotSpots", function (evenement) {
        bDessusHS = true;
    });

    $(document).on("mouseout", ".hotSpots", function (evenement) {
        bDessusHS = false;
    });

    $(document).on("click", "#container-" + iNumPano, function (evenement) {
        mouseMove = false;
    });

    $(document).on("mousedown", "#container-" + iNumPano, function (evenement) {
        if (evenement.which === 1) {
            if (bAfficheInfo)
            {
                $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                    $(this).css({display: "none"});
                    bAfficheInfo = false;
                });
            }
            onPointerDownPointerX = evenement.clientX;
            onPointerDownPointerY = evenement.clientY;
            bUserInteracting = true;
            if (iMode === 1) {
                deltaX = 0;
                deltaY = 0;
                timer = requestAnimFrame(deplaceMode2);
            }
            else
            {
                onPointerDownLon = longitude;
                onPointerDownLat = latitude;
                pano.addClass('curseurCroix');
                if (iMode === 0) {
                    $("#container-" + iNumPano).css("cursor", "grabbing");
                }

            }

        }
        evenement.preventDefault();
    });

    $(document).on("mousemove", "#container-" + iNumPano, function (evenement) {

        if (bUserInteracting === true) {

            mouseMove = true;
            if (iMode === 1) {
                deltaX = -(onPointerDownPointerX - evenement.clientX) * 0.01;
                deltaY = (onPointerDownPointerY - evenement.clientY) * 0.01;
            }
            else {
                positionHS();
                longitude = (onPointerDownPointerX - evenement.clientX) * 0.1 + onPointerDownLon;
                latitude = (evenement.clientY - onPointerDownPointerY) * 0.1 + onPointerDownLat;
                affiche();
            }
        }
        evenement.preventDefault();
    });

    $(document).on("mouseup", "#container-" + iNumPano, function (evenement) {
        pano.removeClass('curseurCroix');
        if (iMode === 0) {
            $("#container-" + iNumPano).css("cursor", "grab");
        }
        else {
            if (bUserInteracting) {
                bUserInteracting = false;
                ddx = deltaX;
                ddy = deltaY;
                dx = deltaX / 4.0;
                dy = deltaY / 4.0;
                timer = requestAnimFrame(ralenti);
            }
        }
        bUserInteracting = false;
    });
    $(window).on('mouseup', function () {
        pano.removeClass('curseurCroix');
        if (iMode === 0) {
            $("#container-" + iNumPano).css("cursor", "grab");
        }
        else {
            if (bUserInteracting) {
                ddx = deltaX;
                ddy = deltaY;
                dx = deltaX / 4.0;
                dy = deltaY / 4.0;
                timer = requestAnimFrame(ralenti);
            }
        }
        bUserInteracting = false;
    });

    $(document).on("mouseover", "#panovisu-" + iNumPano, function (evenement) {
        bClavierActif = true;
    });

    $(document).on("mouseleave", "#panovisu-" + iNumPano, function (evenement) {
        bClavierActif = false;
    });

    /*
     * Gestion de la molette de la souris
     * 
     */
    $(document).on("mousewheel", "#container-" + iNumPano,
            function (evenement, delta) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
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
    $(window).resize(function () {
        changeTaille();
    });

    $(document).keydown(
            function (evenement) {
//                if (clavierActif) {
                if (true) {
                    if (bAfficheInfo)
                    {
                        $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                            $(this).css({display: "none"});
                            bAfficheInfo = false;
                        });
                    }
                    evenement.preventDefault();
                    var touche = evenement.which;
                    switch (touche)
                    {
                        case 37:
                            longitude -= 1;
                            break;
                        case 38:
                            latitude += 1;
                            break;
                        case 39:
                            longitude += 1;
                            break;
                        case 40:
                            latitude -= 1;
                            break;
                        case 16:
                        case 109:
                            fov += 1;
                            break;
                        case 17:
                        case 107:
                            fov -= 1;
                            break;
                        case 70:
                            pleinEcran();
                            break;
                        case 86:
                            bVignettesRentre = !bVignettesRentre;
                            vignettesRentre();
                            break;
                        case 80:
                            bPlanRentre = !bPlanRentre;
                            if (strPlanPosition === "left") {

                                planRentreGauche();
                            }
                            else {
                                planRentreDroite();
                            }
                            break;
                        case 77:
                            toggleElements();
                            break;
                    }
                    zoom();
                }
            });


    $(document).on("touchstart mousedown", "#xmoins-" + iNumPano + ",#xplus-" + iNumPano + ",#ymoins-" + iNumPano + ",#yplus-" + iNumPano,
            function (evenement) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });
                }
                if (!telecEnCours) {
                    telecEnCours = true;
                    dXdY($(this).attr('id'));
                    ddx = dx / 5;
                    ddy = dy / 5;
                    timer = requestAnimFrame(accelere);
                }
                evenement.preventDefault();

            });

    $(document).on("mouseup mouseleave touchend", "#xmoins-" + iNumPano + ",#xplus-" + iNumPano + ",#ymoins-" + iNumPano + ",#yplus-" + iNumPano, function (evenement) {
        if (telecEnCours) {
            telecEnCours = false;
            timer = requestAnimFrame(ralenti);
            evenement.stopPropagation();
            evenement.preventDefault();
        }
    });

    $(document).on("touchstart mousedown", "#zoomPlus-" + iNumPano,
            function (evenement) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });
                }
                telecEnCours = true;
                timer = requestAnimFrame(zoomPlus);
                evenement.preventDefault();
            });

    $(document).on("touchend mouseup mouseleave", "#zoomPlus-" + iNumPano, function (evenement) {
        telecEnCours = false;
        evenement.preventDefault();
    });

//    $(document).on("click", "#zoomMoins-" + iNumPano, function (evenement) {
//        if (bAfficheInfo)
//        {
//            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
//                $(this).css({display: "none"});
//                bAfficheInfo = false;
//            });
//        }
//        fov += 1;
//        zoom();
//        evenement.preventDefault();
//    });

    $(document).on("touchstart mousedown", "#zoomMoins-" + iNumPano,
            function (evenement) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });
                }
                telecEnCours = true;
                timer = requestAnimFrame(zoomMoins);
                evenement.preventDefault();
            });

    $(document).on("touchend mouseup mouseleave", "#zoomMoins-" + iNumPano, function (evenement) {
        telecEnCours = false;
        evenement.preventDefault();
    });

//    $(document).on("click", "#xmoins-" + iNumPano + ",#xplus-" + iNumPano + ",#ymoins-" + iNumPano + ",#yplus-" + iNumPano,
//            function (evenement) {
//                if (bAfficheInfo)
//                {
//                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
//                        $(this).css({display: "none"});
//                        bAfficheInfo = false;
//                    });
//                }
//                dXdY($(this).attr('class'));
//                longitude += 2 * dx;
//                latitude += 2 * dy;
//                affiche();
//            });

//    $(document).on("click", "#zoomPlus-" + iNumPano, function (evenement) {
//        if (bAfficheInfo)
//        {
//            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
//                $(this).css({display: "none"});
//                bAfficheInfo = false;
//            });
//        }
//        fov -= 1;
//        zoom();
//        evenement.preventDefault();
//    });

    $(document).on("click", "#souris-" + iNumPano, function () {
        changeModeSouris();
    });

    $(document).on("click", "#pleinEcran-" + iNumPano, function () {
        pleinEcran();
    });

    $(document).on("click", "#auto-" + iNumPano, function () {
        toggleAutorotation();
    });

    $(document).on("click", ".binfo", function () {
        afficheFenetreInfo();
    });

    $(document).on("click", ".aide", function () {
        afficheFenetreAide();
    });

    $(document).on("click", ".infoPanovisu", function () {
        $(this).fadeOut(2000, function () {
            $(this).css({display: "none"});
            bAfficheInfo = false;
        });
    });

    $(document).on("click", ".aidePanovisu", function () {
        $(this).fadeOut(2000, function () {
            $(this).css({display: "none"});
            bAfficheAide = false;
        });
    });
    $(document).on("click", ".infoPanovisu a", function (event) {
        event.stopPropagation();
    });

    $(document).on("click", ".imgVignette", function (evenement) {
        if (arrVignettesPano.length !== 0) {
            var element = $(this).attr("id");
            var numelement = parseInt(element.substring(6).split("-")[0]);
            xmlFile = arrVignettesPano[numelement].xml;
            //$("#container-" + iNumPano).fadeOut(200, function () {
            rechargePano(xmlFile);
            //});
        }

    });

    $(document).on("click", ".positionVignettes", function (evenement) {
        if (vignettesTailleImage) {
            evenement.stopPropagation();
            element = $(this).attr("id");
            if ((element === "gaucheVignettes-" + iNumPano) || (element === "droiteVignettes-" + iNumPano)) {
                deplace = vignettesTailleImage + 10;
                if (element === "gaucheVignettes-" + iNumPano) {
                    positionVignettesX += deplace;
                }
                else {
                    positionVignettesX -= deplace;
                }
                if (positionVignettesX > 0)
                    positionVignettesX = 0;
                if (-positionVignettesX + $("#divVignettes-" + iNumPano).width() > (vignettesTailleImage + 10) * arrVignettesPano.length)
                    positionVignettesX = $("#divVignettes-" + iNumPano).width() - (vignettesTailleImage + 10) * arrVignettesPano.length;
                $("#vignettes-" + iNumPano).css({
                    transform: "translate(" + positionVignettesX + "px,0px)"
                });
            }
            if ((element === "basVignettes-" + iNumPano) || (element === "hautVignettes-" + iNumPano)) {
                deplace = vignettesTailleImage / 2 + 5;
                if (element === "hautVignettes-" + iNumPano) {
                    positionVignettesY += deplace;
                }
                else {
                    positionVignettesY -= deplace;
                }
                if (positionVignettesY > 0)
                    positionVignettesY = 0;
                if (-positionVignettesY + $("#divVignettes-" + iNumPano).height() > (vignettesTailleImage / 2 + 15) * arrVignettesPano.length)
                    positionVignettesY = $("#divVignettes-" + iNumPano).height() - (vignettesTailleImage / 2 + 15) * arrVignettesPano.length;
                $("#vignettes-" + iNumPano).css({
                    transform: "translate(0px," + positionVignettesY + "px)"
                });
            }
            if (bAfficheInfo)
            {
                $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                    $(this).css({display: "none"});
                    bAfficheInfo = false;
                });
            }
        }
    });

    $(document).on("click", ".marcheArret", function (evenement) {
        evenement.stopPropagation();
        toggleElements();
    });

    $(document).on("click", "#divSuivant-" + iNumPano, function () {
        //$("#container-" + iNumPano).fadeOut(200, function () {
        rechargePano(XMLsuivant);
        //});
    });

    $(document).on("click", "#divPrecedent-" + iNumPano, function () {
        //$("#container-" + iNumPano).fadeOut(500, function () {
        rechargePano(XMLprecedent);
        //});
    });

    $(document).on("click", "#planTitre-" + iNumPano, function () {
        if (strPlanPosition === "left") {

            if (!bPlanRentre) {
                bPlanRentre = true;
            } else {
                bPlanRentre = false;
            }
            planRentreGauche();
        }
        else {
            if (!bPlanRentre) {
                bPlanRentre = true;
            } else {
                bPlanRentre = false;
            }
            planRentreDroite();
        }
    });

    $(document).on("click", "#carteTitre-" + iNumPano, function () {
        if (strCartePosition === "left") {

            if (!bCarteRentre) {
                bCarteRentre = true;
            } else {
                bCarteRentre = false;
            }
            carteRentreGauche();
        }
        else {
            if (!bCarteRentre) {
                bCarteRentre = true;
            } else {
                bCarteRentre = false;
            }
            carteRentreDroite();
        }
    });

    $(document).on("click", ".titreVignettes", function () {
        if (!bVignettesRentre) {
            bVignettesRentre = true;
        } else {
            bVignettesRentre = false;
        }
        vignettesRentre();
    });

    $(document).on("click", ".planPoint", function () {
        if (arrPointsPlan.length !== 0) {
            var numPlanPoint = parseInt($(this).attr("id").split("-")[1]);
            xmlFile = arrPointsPlan[numPlanPoint].xml;
            if (xmlFile !== "actif") {
                //$("#container-" + iNumPano).fadeOut(200, function () {
                rechargePano(xmlFile);
                //});
            }
        }
    });

    $(document).on("click", "#imgFerme-" + iNumPano, function () {
        $("#divHTML-" + iNumPano).hide();
        $("#divImage-" + iNumPano).hide();
    });

    $(document).on("click", "#homeCarte-" + iNumPano, function () {
        if (strNomLayerCarte.substring(0, 6) === "Google") {
            allerCoordonnees(coordCentreLong, coordCentreLat, iCarteZoom - 11);
        }
        else {
            allerCoordonnees(coordCentreLong, coordCentreLat, iCarteZoom - 10);
        }
    });

    $(document).on("click", ".clicTelec,.imgTelecFS,.imgTelecInfo", function (evenement) {
        telecEnCours = true;
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        idClic = $(this).attr("id");
        sens = "";
        sensZoom = "";
        switch (idClic) {
            case "telUp-" + iNumPano:
                sens = "ymoins";
                break;
            case "telDown-" + iNumPano:
                sens = "yplus";
                break;
            case "telRight-" + iNumPano:
                sens = "xplus";
                break;
            case "telLeft-" + iNumPano:
                sens = "xmoins";
                break;
            case "telZoomMoins-" + iNumPano:
                fov += 2;
                zoom();
                break;
            case "telZoomPlus-" + iNumPano:
                fov -= 2;
                zoom();
                break;
            case "telInfo-" + iNumPano:
                afficheFenetreInfo();
                break;
            case "telAide-" + iNumPano:
                afficheFenetreAide();
                break;
            case "telFS-" + iNumPano:
                pleinEcran();
                break;
            case "telSouris-" + iNumPano:
                changeModeSouris();
                break;
            case "telRotation-" + iNumPano:
                toggleAutorotation();
                break;
            case "telLien-1-" + iNumPano:
                window.open(strLien1BarrePersonnalisee);
                break;
            case "telLien-2-" + iNumPano:
                window.open(strLien2BarrePersonnalisee);
                break;
        }
        if (sens !== "") {
            dXdY(sens);
            longitude += 2 * dx;
            latitude += 2 * dy;
            affiche();
        }
        evenement.stopPropagation();
        evenement.preventDefault();
    });

    $(document).on("touchstart mousedown", ".clicTelec", function (evenement) {
        telecEnCours = true;
        bDeplacement = false;
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        idClic = $(this).attr("id");
        sens = "";
        sensZoom = "";
        switch (idClic) {
            case "telUp-" + iNumPano:
                sens = "ymoins";
                break;
            case "telDown-" + iNumPano:
                sens = "yplus";
                break;
            case "telRight-" + iNumPano:
                sens = "xplus";
                break;
            case "telLeft-" + iNumPano:
                sens = "xmoins";
                break;
            case "telZoomMoins-" + iNumPano:
                timer = requestAnimFrame(zoomMoins);
                break;
            case "telZoomPlus-" + iNumPano:
                timer = requestAnimFrame(zoomPlus);
                break;
        }
        if (sens !== "") {
            bDeplacement = true;
            dXdY(sens);
            ddx = dx / 5;
            ddy = dy / 5;
            timer = requestAnimFrame(accelere);
        }
        evenement.stopPropagation();
        evenement.preventDefault();
    });

    $(document).on("touchend mouseup mouseleave", ".clicTelec", function (evenement) {
        if (telecEnCours) {
            evenement.stopPropagation();
            telecEnCours = false;
            cancelAnimationFrame(timer);
            if (bDeplacement) {
                timer = requestAnimFrame(ralenti);
            }
            evenement.preventDefault();
        }
    });
    $(document).on("touchstart click", ".btnVisiteAuto", function (evenement) {
        evenement.preventDefault();
        if (bAutoTour) {
            bAutoTour = false;
            bAutorotation = false;
            arretAutoTour();
        }
        else {
            bAutoTour = true;
            bAutorotation = true;
            demarreAutoTour();
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
                dx -= 0.5;
                break;
            case "xplus" :
                dx += 0.5;
                break;
            case "ymoins" :
                dy += 0.5;
                break;
            case "yplus" :
                dy -= 0.5;
                break;
        }

    }
    /**
     * Gestion du click prolongé
     * 
     */
    function zoomMoins() {
        fov += 1;
        zoom();
        if (telecEnCours)
            timer = requestAnimFrame(zoomMoins);
    }
    /**
     * 
     * @returns {undefined}
     */
    function zoomPlus() {
        fov -= 1;
        zoom();
        if (telecEnCours)
            timer = requestAnimFrame(zoomPlus);
    }
    /**
     * 
     * @returns {undefined}
     */
    function accelere() {
        if (Math.abs(ddx) < Math.abs(4 * dx))
            ddx += dx / 5;
        if (Math.abs(ddy) < Math.abs(4 * dy))
            ddy += dy / 5;
        longitude += ddx;
        latitude += ddy;
        affiche();
        if (telecEnCours)
            timer = requestAnimFrame(accelere);
    }
    /**
     * 
     * @returns {undefined}
     */
    function ralenti() {
        finX = false;
        finY = false;
        if (Math.round(Math.abs(ddx) * 10) / 10 > 0)
            ddx -= dx / 5;
        else
            finX = true;
        if (Math.round(Math.abs(ddy) * 10) / 10 > 0)
            ddy -= dy / 5;
        else
            finY = true;
        longitude += ddx;
        if ((latitude + ddy < 90) && (latitude + ddy > -90)) {
            latitude += ddy;
        }
        affiche();
        if (finX && finY) {
            cancelAnimationFrame(timer);
        }
        else {
            timer = requestAnimFrame(ralenti);
        }

    }

    /**
     * 
     * @returns {undefined}
     */
    function changeModeSouris() {
        iMode = 1 - iMode;
        if (iMode === 0) {
            $("#container-" + iNumPano).css("cursor", "grab");
            img = new Image();
            img.src = "./panovisu/images/" + strStyleBoutons + "/souris2.png";
            img.onload = function () {
                $("#souris-" + iNumPano + ">img").attr("src", this.src);
            };
            img2 = new Image();
            img2.src = "./panovisu/images/telecommande/souris2.png";
            img2.onload = function () {
                $("#telSouris-" + iNumPano + ">img").attr("src", this.src);
            };
        }
        else {
            $("#container-" + iNumPano).css("cursor", "default");
            img = new Image();
            img.src = "./panovisu/images/" + strStyleBoutons + "/souris.png";
            img.onload = function () {
                $("#souris-" + iNumPano + ">img").attr("src", this.src);
            };
            img2 = new Image();
            img2.src = "./panovisu/images/telecommande/souris.png";
            img2.onload = function () {
                $("#telSouris-" + iNumPano + ">img").attr("src", this.src);
            };
        }



    }
    /**
     * 
     * @returns {undefined}
     */
    function toggleAutorotation() {
        if (bAutorotation)
        {
            strAutoRotationMarche = "non";
            bAutorotation = false;
            bAutoTour = false;
            arreteAutorotation();
        }
        else
        {
            strAutoRotationMarche = "oui";
            bAutorotation = true;
            dxAutorotation = 1 / vitesseAutorotation;
            ddxAutorotation = 0;
            timerAutorotation = Date.now();
            timerDebutRotation = timerAutorotation;
            longitudeDebutRotation = 0;
            demarreAutoRotation();
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheFenetreInfo() {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(1000, function () {
                $("#infoPanovisu-" + iNumPano).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        else {
            if (bFenetreInfoPersonnalise) {
                img = new Image();
                img.src = strFenetreInfoImage;
                img.onload = function () {
                    $("#infoPanovisu-" + iNumPano).html("");
                    larg = Math.round(this.width * fenetreInfoTaille / 100);
                    haut = Math.round(this.height * fenetreInfoTaille / 100);
                    $("<img>", {id: "infoImg-" + iNumPano, src: this.src, alt: "", height: haut, width: larg}).appendTo("#infoPanovisu-" + iNumPano);
                    $("#infoImg-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
                    $("<a>", {id: "infoUrl-" + iNumPano,
                        text: strFenetreInfoTexteURL,
                        href: strFenetreInfoURL,
                        target: "_blank"}).appendTo("#infoPanovisu-" + iNumPano);
                    $("#infoPanovisu-" + iNumPano).css({
                        width: larg,
                        height: haut
                    });
                    $("#infoPanovisu-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
                    posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2 + fenetreInfoDX;
                    posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2 + fenetreInfoDX;
                    $("#infoPanovisu-" + iNumPano).css({
                        top: posHaut + "px",
                        left: posGauche + "px",
                        border: "none",
                        backgroundColor: "rgba(255,255,255,0)",
                        opacity: "1.0"
                    });
                    topUrl = ($("#infoPanovisu-" + iNumPano).height() - $("#infoUrl-" + iNumPano).height()) / 2 + fenetreInfoDYURL;
                    leftUrl = ($("#infoPanovisu-" + iNumPano).width() - $("#infoUrl-" + iNumPano).width()) / 2 + fenetreInfoDXURL;
                    $("#infoUrl-" + iNumPano).css({color: strFenetreInfoCouleurURL,
                        fontSize: fenetreInfoTailleURL + "px",
                        position: "absolute",
                        top: topUrl + "px",
                        left: leftUrl + "px"
                    });
                };
            }
            else {
                panoInfo = chainesTraduction[strLangage].fenetreInfo;
                $("#infoPanovisu-" + iNumPano).css({width: "450px", height: "190px"});
                $("#infoPanovisu-" + iNumPano).html(panoInfo);
                $("#infoPanovisu-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
                posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2;
                posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2;
                $("#infoPanovisu-" + iNumPano).css({top: posHaut + "px", left: posGauche + "px"});
            }
            if (bAfficheAide) {
                $("#aidePanovisu-" + iNumPano).fadeOut(1000, function () {
                    bAfficheAide = false;
                });
                $("#infoPanovisu-" + iNumPano).fadeIn(1000, function () {
                    bAfficheInfo = true;
                });
            } else {
                $("#infoPanovisu-" + iNumPano).fadeIn(1000, function () {
                    bAfficheInfo = true;
                });
            }

        }

    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheFenetreAide() {
        if (bAfficheAide)
        {
            $("#aidePanovisu-" + iNumPano).fadeOut(1000, function () {
                $("#aidePanovisu-" + iNumPano).css({display: "none"});
                bAfficheAide = false;
            });
        } else {
            if (bAfficheInfo) {
                $("#infoPanovisu-" + iNumPano).fadeOut(1000, function () {
                    bAfficheInfo = false;
                });
                $("#aidePanovisu-" + iNumPano).fadeIn(1000, function () {
                    bAfficheAide = true;
                });
            } else {
                $("#aidePanovisu-" + iNumPano).fadeIn(1000, function () {
                    bAfficheAide = true;
                });
            }

        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function toggleElements() {
        if (elementsVisibles) {
            if (strMarcheArretNavigation === "oui") {
                $("#barre-" + iNumPano).fadeOut(500);
                $("#telec-" + iNumPano).fadeOut(500);
            }
            if (strMarcheArretBoussole === "oui")
                $("#boussole-" + iNumPano).fadeOut(500);
            if (strMarcheArretTitre === "oui")
                $("#info-" + iNumPano).fadeOut(500);
            if (strMarcheArretPlan === "oui") {
                $("#plan-" + iNumPano).fadeOut(500);
                $("#planTitre-" + iNumPano).fadeOut(500);
                $("#carte-" + iNumPano).fadeOut(500);
                $("#carteTitre-" + iNumPano).fadeOut(500);
            }
            if (strMarcheArretReseaux === "oui")
                $("#reseauxSociaux-" + iNumPano).fadeOut(500);
            if (strMarcheArretCombo === "oui")
                $("#comboMenu-" + iNumPano).fadeOut(500);
            if (strMarcheArretSuivPrec === "oui" && bSuivantPrecedent) {
                $("#divPrecedent-" + iNumPano).fadeOut(500);
                $("#divSuivant-" + iNumPano).fadeOut(500);
            }
            if (strMarcheArretHotspots === "oui") {
                $(".hotSpots").fadeOut(500);
            }

            if (strMarcheArretVignettes === "oui")
            {
                $("#titreVignettes-" + iNumPano).fadeOut(500);
                $("#divVignettes-" + iNumPano).fadeOut(500);
                if (strVignettesPosition === "left") {
                    $("#divPrecedent-" + iNumPano).css({left: 0});
                }
                if (strVignettesPosition === "right") {
                    $("#divSuivant-" + iNumPano).css({right: 0});
                }
            }
            for (i = 0; i < iNombreImageFond; i++) {
                if (arrImagesFond[i].masquable) {
                    $("#imageFond-" + i + "-" + iNumPano).fadeOut(500);
                }
            }
            elementsVisibles = false;
        }
        else {
            if (strMarcheArretNavigation === "oui") {
                $("#barre-" + iNumPano).fadeIn(500);
                $("#telec-" + iNumPano).fadeIn(500);
            }
            if (strMarcheArretBoussole === "oui")
                $("#boussole-" + iNumPano).fadeIn(500);
            if ((strMarcheArretTitre === "oui") && (strAfficheTitre === "oui"))
                $("#info-" + iNumPano).fadeIn(500);
            if (strMarcheArretPlan === "oui") {
                if (bPlanAffiche) {
                    $("#plan-" + iNumPano).fadeIn(500);
                    $("#planTitre-" + iNumPano).fadeIn(500);
                }
                if (bCarteAffiche) {
                    $("#carte-" + iNumPano).fadeIn(500);
                    $("#carteTitre-" + iNumPano).fadeIn(500);
                }
            }
            if (strMarcheArretReseaux === "oui")
                $("#reseauxSociaux-" + iNumPano).fadeIn(500);
            if (strMarcheArretCombo === "oui")
                $("#comboMenu-" + iNumPano).fadeIn(500);
            if (strMarcheArretSuivPrec === "oui" && bSuivantPrecedent) {
                $("#divPrecedent-" + iNumPano).fadeIn(500);
                $("#divSuivant-" + iNumPano).fadeIn(500);
            }
            if (strMarcheArretHotspots === "oui") {
                $(".hotSpots").fadeIn(500);
            }

            if (strMarcheArretVignettes === "oui")
            {
                $("#titreVignettes-" + iNumPano).fadeIn(500);
                $("#divVignettes-" + iNumPano).fadeIn(500);
                if (!bVignettesRentre) {
                    var largeur = $("#divVignettes-" + iNumPano).width() + 6;
                    if (strVignettesPosition === "left") {
                        $("#divPrecedent-" + iNumPano).css({left: largeur});
                    }
                    if (strVignettesPosition === "right") {
                        $("#divSuivant-" + iNumPano).css({right: largeur});
                    }
                }
                else {
                    if (strVignettesPosition === "left") {
                        $("#divPrecedent-" + iNumPano).css({left: 0});
                    }
                    if (strVignettesPosition === "right") {
                        $("#divSuivant-" + iNumPano).css({right: 0});
                    }
                }

            }
            for (i = 0; i < iNombreImageFond; i++) {
                if (arrImagesFond[i].masquable) {
                    $("#imageFond-" + i + "-" + iNumPano).fadeIn(500);
                }
            }

            elementsVisibles = true;
        }

    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheMasqueElements() {
        if (!elementsVisibles) {
            if (strMarcheArretNavigation === "oui") {
                $("#barre-" + iNumPano).hide();
                $("#telec-" + iNumPano).hide();
            }
            if (strMarcheArretBoussole === "oui")
                $("#boussole-" + iNumPano).hide();
            if (strMarcheArretTitre === "oui")
                $("#info-" + iNumPano).hide();
            if (strMarcheArretPlan === "oui") {
                $("#plan-" + iNumPano).hide();
                $("#planTitre-" + iNumPano).hide();
                $("#carte-" + iNumPano).hide();
                $("#carteTitre-" + iNumPano).hide();
            }
            if (strMarcheArretReseaux === "oui")
                $("#reseauxSociaux-" + iNumPano).hide();
            if (strMarcheArretCombo === "oui")
                $("#comboMenu-" + iNumPano).hide();
            if (strMarcheArretSuivPrec === "oui" && bSuivantPrecedent) {
                $("#divPrecedent-" + iNumPano).hide();
                $("#divSuivant-" + iNumPano).hide();
            }
            if (strMarcheArretHotspots === "oui") {
                $(".hotSpots").hide();
            }

            if (strMarcheArretVignettes === "oui")
            {
                $("#titreVignettes-" + iNumPano).hide();
                $("#divVignettes-" + iNumPano).hide();
                if (strVignettesPosition === "left") {
                    $("#divPrecedent-" + iNumPano).css({left: 0});
                }
                if (strVignettesPosition === "right") {
                    $("#divSuivant-" + iNumPano).css({right: 0});
                }
            }
            for (i = 0; i < iNombreImageFond; i++) {
                if (arrImagesFond[i].masquable) {
                    $("#imageFond-" + i + "-" + iNumPano).hide();
                }
            }
        }
        else {
            if (strMarcheArretNavigation === "oui") {
                $("#barre-" + iNumPano).show();
                $("#telec-" + iNumPano).show();
            }
            if (strMarcheArretBoussole === "oui")
                $("#boussole-" + iNumPano).show();
            if ((strMarcheArretTitre === "oui") && (strAfficheTitre === "oui"))
                $("#info-" + iNumPano).show();
            if (strMarcheArretPlan === "oui") {
                if (bPlanAffiche) {
                    $("#plan-" + iNumPano).show();
                    $("#planTitre-" + iNumPano).show();
                }
                if (bCarteAffiche) {
                    $("#carte-" + iNumPano).show();
                    $("#carteTitre-" + iNumPano).show();
                }
            }
            if (strMarcheArretReseaux === "oui")
                $("#reseauxSociaux-" + iNumPano).show();
            if (strMarcheArretCombo === "oui")
                $("#comboMenu-" + iNumPano).show();
            if (strMarcheArretSuivPrec === "oui" && bSuivantPrecedent) {
                $("#divPrecedent-" + iNumPano).show();
                $("#divSuivant-" + iNumPano).show();
            }
            if (strMarcheArretHotspots === "oui") {
                $(".hotSpots").show();
            }

            if (strMarcheArretVignettes === "oui")
            {
                $("#titreVignettes-" + iNumPano).show();
                $("#divVignettes-" + iNumPano).show();
                if (!bVignettesRentre) {
                    var largeur = $("#divVignettes-" + iNumPano).width() + 6;
                    if (strVignettesPosition === "left") {
                        $("#divPrecedent-" + iNumPano).css({left: largeur});
                    }
                    if (strVignettesPosition === "right") {
                        $("#divSuivant-" + iNumPano).css({right: largeur});
                    }
                }
                else {
                    if (strVignettesPosition === "left") {
                        $("#divPrecedent-" + iNumPano).css({left: 0});
                    }
                    if (strVignettesPosition === "right") {
                        $("#divSuivant-" + iNumPano).css({right: 0});
                    }
                }

            }
            for (i = 0; i < iNombreImageFond; i++) {
                if (arrImagesFond[i].masquable) {
                    $("#imageFond-" + i + "-" + iNumPano).show();
                }
            }
        }

    }
    /**
     * 
     * @returns {undefined}
     */
    function planRentreGauche() {
        if (bPlanRentre) {
            $("#planTitre-" + iNumPano).css({
                transform: "translateX(-" + parseInt($("#planImg-" + iNumPano).width() + 20) + "px) rotate(90deg)"
            });
            $("#plan-" + iNumPano).css({
                transform: "translateX(-" + parseInt($("#planImg-" + iNumPano).width() + 20) + "px)"
            });
        } else {
            $("#planTitre-" + iNumPano).css({
                transform: "translateX(0px)  rotate(90deg)"
            });
            $("#plan-" + iNumPano).css({
                transform: "translateX(0px)"
            });
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function planRentreDroite() {
        if (bPlanRentre) {
            $("#planTitre-" + iNumPano).css({
                transform: "translateX(" + parseInt($("#planImg-" + iNumPano).width() + 20) + "px)  rotate(90deg)"
            });
            $("#plan-" + iNumPano).css({
                transform: "translateX(" + parseInt($("#planImg-" + iNumPano).width() + 20) + "px)"
            });
        } else {
            $("#planTitre-" + iNumPano).css({
                transform: "translateX(0px)  rotate(90deg)"
            });
            $("#plan-" + iNumPano).css({
                transform: "translateX(0px)"
            });
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function carteRentreGauche() {
        if (bCarteRentre) {
            $("#carteTitre-" + iNumPano).css({
                transform: "translateX(-" + parseInt(carteLargeur + 20) + "px) rotate(90deg)"
            });
            $("#carte-" + iNumPano).css({
                transform: "translateX(-" + parseInt(carteLargeur + 20) + "px)"
            });
        } else {
            $("#carteTitre-" + iNumPano).css({
                transform: "translateX(0px)  rotate(90deg)"
            });
            $("#carte-" + iNumPano).css({
                transform: "translateX(0px)"
            });
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function carteRentreDroite() {
        if (bCarteRentre) {
            $("#carteTitre-" + iNumPano).css({
                transform: "translateX(" + parseInt(carteLargeur + 20) + "px)  rotate(90deg)"
            });
            $("#carte-" + iNumPano).css({
                transform: "translateX(" + parseInt(carteLargeur + 20) + "px)"
            });
        } else {
            $("#carteTitre-" + iNumPano).css({
                transform: "translateX(0px)  rotate(90deg)"
            });
            $("#carte-" + iNumPano).css({
                transform: "translateX(0px)"
            });
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function vignettesRentre() {
        var largeurFenetre = vignettesTailleImage + 5;
        if (bVignettesRentre) {
            switch (strVignettesPosition) {
                case "left":

                    $("#divVignettes-" + iNumPano).css({transform: "translateX(-" + ($("#divVignettes-" + iNumPano).width() + 5) + "px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "translateX(-" + ($("#divVignettes-" + iNumPano).width() + 5) + "px) rotate(90deg)"});
                    dX1 = parseInt(strDX);
                    $("#divPrecedent-" + iNumPano).css("left", "0");
                    if (strPositionX === "left") {
                        if (strPositionY === "top") {
                            $("#barre-" + iNumPano).css({left: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({left: dX1 + "px"});
                        }
                    }
                    dX1 = parseInt(telecommandeDX);
                    if (strTelecommandePositionX === "left") {
                        if (strTelecommandePositionY === "top") {
                            $("#telec-" + iNumPano).css({left: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({left: dX1 + "px"});
                        }
                    }

                    if (bBoussole && (strBoussolePositionX === "left")) {
                        if (strBoussolePositionY === "top") {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + $("#titreVignettes-" + iNumPano).height() + 4) + "px");
                        }
                        else {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX)) + "px");
                        }
                    }

                    break;
                case "right":
                    $("#divVignettes-" + iNumPano).css({transform: "translateX(" + ($("#divVignettes-" + iNumPano).width() + 5) + "px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "translateX(" + ($("#divVignettes-" + iNumPano).width() + 5) + "px) rotate(90deg)"});
                    dX1 = parseInt(strDX);
                    $("#divSuivant-" + iNumPano).css("right", "0");
                    if (strPositionX === "right") {
                        if (strPositionY === "top") {
                            $("#barre-" + iNumPano).css({right: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({right: dX1 + "px"});
                        }
                    }
                    dX1 = parseInt(telecommandeDX);
                    if (strTelecommandePositionX === "right") {
                        if (strTelecommandePositionY === "top") {
                            $("#telec-" + iNumPano).css({right: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({right: dX1 + "px"});
                        }
                    }

                    if (bBoussole && (strBoussolePositionX === "right")) {
                        if (strBoussolePositionY === "top") {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + $("#titreVignettes-" + iNumPano).height() + 4) + "px");
                        }
                        else {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX)) + "px");
                        }
                    }

                    if (strPositionX === "right")
                        $("#barre-" + iNumPano).css({right: dX1 + "px"});
                    if (bBoussole && (strBoussolePositionX === "right"))
                        $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX)) + "px");
                    break;
                case "bottom":

                    $("#divVignettes-" + iNumPano).css({transform: "translateY(" + ($("#divVignettes-" + iNumPano).height() + 5) + "px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "translateY(" + ($("#divVignettes-" + iNumPano).height() + 5) + "px)"});
                    dY1 = parseInt(strDY);
                    if (strPositionY === "bottom") {
                        if (strPositionX === "right") {
                            $("#barre-" + iNumPano).css({top: -(35 + dY1 - 2 + $("#titreVignettes-" + iNumPano).height()) + "px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({top: -(35 + dY1 - 2) + "px"});
                        }
                    }
                    dY1 = parseInt(telecommandeDY);
                    if (strTelecommandePositionY === "bottom") {
                        if (strTelecommandePositionX === "right") {
                            $("#telec-" + iNumPano).css({bottom: dY1 + $("#titreVignettes-" + iNumPano).height() + "px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({bottom: dY1 + "px"});
                        }
                    }

                    if (bBoussole && (strBoussolePositionY === "bottom"))
                        if (strBoussolePositionX === "right") {
                            $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + 10 + $("#titreVignettes-" + iNumPano).height()) + "px");
                        }
                        else {
                            $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + 10) + "px");
                        }

                    break;
            }
        }
        else {
            switch (strVignettesPosition) {
                case "left":
                    $("#divVignettes-" + iNumPano).css({transform: "translateX(0px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "rotate(90deg)"});
                    dX1 = parseInt(strDX) + largeurFenetre;
                    $("#divPrecedent-" + iNumPano).css("left", largeurFenetre + 6);
                    $("#divPrecedent-" + iNumPano).css("left", largeurFenetre + 6);
                    dX1 = parseInt(strDX) + parseInt(vignettesTailleImage) + 5;
                    if (strPositionX === "left") {
                        if (strPositionY === "top") {
                            $("#barre-" + iNumPano).css({left: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({left: dX1 + "px"});
                        }
                    }
                    dX1 = parseInt(telecommandeDX) + parseInt(vignettesTailleImage) + 5;
                    if (strTelecommandePositionX === "left") {
                        if (strTelecommandePositionY === "top") {
                            $("#telec-" + iNumPano).css({left: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({left: dX1 + "px"});
                        }
                    }


                    if (bBoussole && (strBoussolePositionX === "left")) {
                        if (strBoussolePositionY === "top") {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5 + $("#titreVignettes-" + iNumPano).height() + 4) + "px");
                        }
                        else {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5) + "px");
                        }
                    }

                    break;
                case "right":
                    $("#divVignettes-" + iNumPano).css({transform: "translateX(0px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "rotate(90deg)"});
                    dX1 = parseInt(strDX) + largeurFenetre;
                    $("#divSuivant-" + iNumPano).css("right", largeurFenetre + 6);
                    if (strPositionX === "right") {
                        if (strPositionY === "top") {
                            $("#barre-" + iNumPano).css({right: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({right: dX1 + "px"});
                        }
                    }
                    dX1 = parseInt(telecommandeDX) + largeurFenetre;
                    $("#divSuivant-" + iNumPano).css("right", largeurFenetre + 6);
                    if (strTelecommandePositionX === "right") {
                        if (strTelecommandePositionY === "top") {
                            $("#telec-" + iNumPano).css({right: dX1 + $("#titreVignettes-" + iNumPano).height() + 4 + "px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({right: dX1 + "px"});
                        }
                    }

                    if (bBoussole && (strBoussolePositionX === "right")) {
                        if (strBoussolePositionY === "top") {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5 + $("#titreVignettes-" + iNumPano).height() + 4) + "px");
                        }
                        else {
                            $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5) + "px");
                        }
                    }

                    break;
                case "bottom":
                    $("#divVignettes-" + iNumPano).css({transform: "translateY(0px)"});
                    $("#titreVignettes-" + iNumPano).css({transform: "translateY(0px)"});
                    dY1 = parseInt(strDY) + $("#divVignettes-" + iNumPano).height() + 5;
                    if (strPositionY === "bottom") {
                        if (strPositionX === "right") {
                            $("#barre-" + iNumPano).css({top: -(35 + dY1 - 2 + $("#titreVignettes-" + iNumPano).height()) + +"px"});
                        }
                        else {
                            $("#barre-" + iNumPano).css({top: -(35 + dY1 - 2) + "px"});
                        }
                    }
                    dY1 = parseInt(telecommandeDY) + $("#divVignettes-" + iNumPano).height() + 5;
                    if (strTelecommandePositionY === "bottom") {
                        if (strTelecommandePositionX === "right") {
                            $("#telec-" + iNumPano).css({bottom: dY1 + $("#titreVignettes-" + iNumPano).height() + +"px"});
                        }
                        else {
                            $("#telec-" + iNumPano).css({bottom: dY1 + "px"});
                        }
                    }

                    if (strVignettesPosition === "bottom") {
                        if (bBoussole && (strBoussolePositionY === "bottom"))
                            if (strBoussolePositionX === "right") {
                                $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + parseInt(vignettesTailleImage) / 2.0 + 5 + $("#titreVignettes-" + iNumPano).height() + 10) + "px");
                            }
                            else {
                                $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + parseInt(vignettesTailleImage) / 2.0 + 15) + "px");
                            }
                    }

                    break;
            }

        }
        if (iNombreImageFond > 0) {
            positionImagesFond();
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    var lastLoop = new Date;

    function deplaceMode2() {
        var thisLoop = new Date;
        var fps = 1000 / (thisLoop - lastLoop);
        lastLoop = thisLoop;
        console.log("fps : " + fps + "img/s");
        longitude += deltaX;
        latitude += deltaY;
        affiche();
        if (bUserInteracting || isUserInteracting)
            timer = requestAnimFrame(deplaceMode2);
    }

    /**
     * 
     * @returns {undefined}
     */
    function afficheVignettesHorizontales() {
        typeVignettes = "horizontales";
        $("#hautVignettes-" + iNumPano).hide();
        $("#basVignettes-" + iNumPano).hide();
        $("<div>", {id: "vignettes-" + iNumPano, class: "vignettes"}).appendTo("#divVignettes-" + iNumPano);
        var hauteur = vignettesTailleImage / 2;
        var largeurFenetre = $("#pano1-" + iNumPano).width() - 15;
        if (largeurFenetre < (vignettesTailleImage + 10) * arrVignettesPano.length) {
            $("#gaucheVignettes-" + iNumPano).show(500);
            positVig = (vignettesTailleImage / 2 - 64) / 2;
            $("#gaucheVignettes-" + iNumPano).css({
                left: 0,
                bottom: positVig
            });
            $("#droiteVignettes-" + iNumPano).show(500);
            $("#droiteVignettes-" + iNumPano).css({
                right: 0,
                bottom: positVig
            });
        }
        $("#divVignettes-" + iNumPano).css(strVignettesPosition, "0px");
        $("#divVignettes-" + iNumPano).css({
            height: hauteur,
            width: largeurFenetre,
            paddingLeft: "17px",
            paddingTop: "3px",
            paddingBottom: "3px",
            backgroundColor: strVignettesFondCouleur,
            opacity: vignettesOpacite,
            overflow: "hidden"
        });
        paddingTitre = 2;
        $("#titreVignettes-" + iNumPano).css({
            width: "80px",
            height: "30px",
            bottom: hauteur + 5,
            right: 0,
            padding: paddingTitre + "px",
            paddingTop: "3px",
            backgroundColor: strVignettesFondCouleur,
            color: strVignettesTexteCouleur,
            opacity: vignettesOpacite,
            borderTopLeftRadius: "5px",
            borderTopRightRadius: "5px",
            textAlign: "center"
        });
        $("#vignettes-" + iNumPano).css({
            height: hauteur,
            width: 3000
        });
        $("#divVignettes-" + iNumPano).css(strVignettesPosition, "0px");
        for (var i = 0; i < arrVignettesPano.length; i++) {
            if (arrVignettesPano[i].txt !== "") {
                texte = arrVignettesPano[i].txt;
            }
            else {
                texte = arrVignettesPano[i].xml;
            }

            $("<img>", {
                id: "imgVig" + i + "-" + iNumPano,
                class: "imgVignette",
                src: arrVignettesPano[i].image,
                title: arrVignettesPano[i].txt,
                width: vignettesTailleImage,
                height: vignettesTailleImage / 2
            }).appendTo("#vignettes-" + iNumPano);
        }

        if (strVignettesPosition === "bottom") {
            if (bBoussole && (strBoussolePositionY === "bottom"))
                if (strBoussolePositionX === "right") {
                    $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + parseInt(vignettesTailleImage) / 2.0 + 5 + $("#titreVignettes-" + iNumPano).height() + 2 * paddingTitre) + "px");
                }
                else {
                    $("#boussole-" + iNumPano).css(strBoussolePositionY, (parseInt(strBoussoleDY) + parseInt(vignettesTailleImage) / 2.0 + 15) + "px");
                }
        }
        $("#divVignettes-" + iNumPano).show();
        $("#titreVignettes-" + iNumPano).show();
    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheVignettesVerticales() {
        typeVignettes = "verticales";
        $("#gaucheVignettes-" + iNumPano).hide();
        $("#droiteVignettes-" + iNumPano).hide();
        $("<div>", {id: "vignettes-" + iNumPano, class: "vignettes"}).appendTo("#divVignettes-" + iNumPano);
        if (strAfficheTitre === "oui") {
            var hauteur = $("#pano1-" + iNumPano).height() - 30 - $("#info-" + iNumPano).height();
        }
        else {
            var hauteur = $("#pano1-" + iNumPano).height() - 30;
        }
        var largeurFenetre = vignettesTailleImage + 5;
        topVignettes = "4px";
        if (hauteur < (vignettesTailleImage / 2 + 10) * (arrVignettesPano.length + 1)) {
            $("#hautVignettes-" + iNumPano).show(500);
            positVig = (vignettesTailleImage - 45) / 2;
            $("#hautVignettes-" + iNumPano).css({
                left: positVig,
                top: 0
            });
            $("#basVignettes-" + iNumPano).show(500);
            $("#basVignettes-" + iNumPano).css({
                left: positVig,
                bottom: 0
            });
            topVignettes = "17px";
        }
        $("#divVignettes-" + iNumPano).css(strVignettesPosition, "0px");
        $("#divVignettes-" + iNumPano).css({
            height: hauteur - ($("#info-" + iNumPano).height() - 10) - 22,
            width: largeurFenetre,
            paddingLeft: "3px",
            paddingTop: topVignettes,
            paddingRight: "3px",
            backgroundColor: strVignettesFondCouleur,
            opacity: vignettesOpacite,
            overflow: "hidden",
            top: ($("#info-" + iNumPano).height() + 10) + "px"
        });
        paddingTitre = 2;
        $("#titreVignettes-" + iNumPano).css({
            width: "80px",
            height: "30px",
            top: ($("#info-" + iNumPano).height() + 10) + "px",
            padding: paddingTitre + "px",
            paddingTop: "4px",
            backgroundColor: strVignettesFondCouleur,
            color: strVignettesTexteCouleur,
            opacity: vignettesOpacite,
            transformOrigin: "0% 0%",
            transform: "rotate(90deg)",
            textAlign: "center"
        });
        if (strVignettesPosition === "left") {
            $("#titreVignettes-" + iNumPano).css(strVignettesPosition, (largeurFenetre + 5 + 2 * paddingTitre + $("#titreVignettes-" + iNumPano).height()) + "px");
            $("#titreVignettes-" + iNumPano).css({
                borderTopLeftRadius: "5px",
                borderTopRightRadius: "5px"
            });

        }
        else {
            $("#titreVignettes-" + iNumPano).css(strVignettesPosition, (largeurFenetre + 5 - 2 * paddingTitre - $("#titreVignettes-" + iNumPano).width()) + "px");
            $("#titreVignettes-" + iNumPano).css({
                borderBottomLeftRadius: "5px",
                borderBottomRightRadius: "5px"
            });
        }

        $("#vignettes-" + iNumPano).css({
            height: 3000,
            width: largeurFenetre
        });
        $("#divVignettes-" + iNumPano).css(strVignettesPosition, "0px");
        for (var i = 0; i < arrVignettesPano.length; i++) {
            if (arrVignettesPano[i].txt !== "") {
                texte = arrVignettesPano[i].txt;
            }
            else {
                texte = arrVignettesPano[i].xml;
            }

            $("<img>", {
                id: "imgVig" + i + "-" + iNumPano,
                class: "imgVignette",
                src: arrVignettesPano[i].image,
                title: arrVignettesPano[i].txt,
                width: vignettesTailleImage,
                height: vignettesTailleImage / 2
            }).appendTo("#vignettes-" + iNumPano).css({
                marginBottom: "2px",
                marginTop: "2px",
                marginLeft: "0px",
                paddingRight: "0px"
            });
        }
        $("#divVignettes-" + iNumPano).show();
        $("#titreVignettes-" + iNumPano).show();
        if (strVignettesPosition === "right") {
            $("#divSuivant-" + iNumPano).css("right", largeurFenetre + 6);
            dX1 = parseInt(strDX) + parseInt(vignettesTailleImage) + 5;
            if (strPositionX === "right") {
                if (strPositionY === "top") {
                    $("#barre-" + iNumPano).css({right: dX1 + $("#titreVignettes-" + iNumPano).height() + 2 * paddingTitre + "px"});
                }
                else {
                    $("#barre-" + iNumPano).css({right: dX1 + "px"});
                }
            }

            if (bBoussole && (strBoussolePositionX === "right")) {
                if (strBoussolePositionY === "top") {
                    $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5 + $("#titreVignettes-" + iNumPano).height() + 2 * paddingTitre) + "px");
                }
                else {
                    $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5) + "px");
                }
            }
        }
        if (strVignettesPosition === "left") {
            $("#divPrecedent-" + iNumPano).css("left", largeurFenetre + 6);
            dX1 = parseInt(strDX) + parseInt(vignettesTailleImage) + 5;
            if (strPositionX === "left") {
                if (strPositionY === "top") {
                    $("#barre-" + iNumPano).css({left: dX1 + $("#titreVignettes-" + iNumPano).height() + 2 * paddingTitre + "px"});
                }
                else {
                    $("#barre-" + iNumPano).css({left: dX1 + "px"});
                }
            }
            if (bBoussole && (strBoussolePositionX === "left")) {
                if (strBoussolePositionY === "top") {
                    $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5 + $("#titreVignettes-" + iNumPano).height() + 2 * paddingTitre) + "px");
                }
                else {
                    $("#boussole-" + iNumPano).css(strBoussolePositionX, (parseInt(strBoussoleDX) + parseInt(vignettesTailleImage) + 5) + "px");
                }
            }
        }

    }


    /**
     * 
     * @returns {undefined}
     */
    function affiche() {
        pWidth = $("#" + fenPanoramique).width();
        pHeight = $("#" + fenPanoramique).height();
        hfov = fov * 1.5;
        if (strBoutons === "oui" && ((strMarcheArretNavigation !== "oui") || (strMarcheArretNavigation === "oui" && elementsVisibles))) {
            $("#boutons-" + iNumPano).show();
        }
        if (strAfficheTitre === "oui" && ((strMarcheArretTitre !== "oui") || (strMarcheArretTitre === "oui" && elementsVisibles))) {
            $("#info-" + iNumPano).show();
        }
        if (latitude > 89.9999)
            latitude = 89.9999;
        if (latitude < -90)
            latitude = -90;
        if ((latitude + fov / 2) > maxLat) {
            latitude = maxLat - fov / 2;
        }
        if ((latitude - fov / 2) < minLat) {
            latitude = minLat + fov / 2;
        }
        longitude = longitude % 360;
        positionHS();
        phi = THREE.Math.degToRad(90 - latitude);
        theta = THREE.Math.degToRad(longitude);
        target.x = 500 * Math.sin(phi) * Math.cos(theta);
        target.y = 500 * Math.cos(phi);
        target.z = 500 * Math.sin(phi) * Math.sin(theta);
        camera.lookAt(target);
        if (bLittlePlanetView) {
            camera.position.copy(target).negate();
        }
        if (renderer)
            renderer.render(scene, camera);
        var bouss = longitude - zeroNord;
        if (strBoussoleAiguille === "oui")
        {
            $("#bousAig-" + iNumPano).css({transform: "rotate(" + bouss + "deg)"});
        }
        else {
            $("#bousImg-" + iNumPano).css({transform: "rotate(" + (-bouss) + "deg)"});
        }
        if (bPlanAffiche && bRadarAffiche) {
            angleRadar = fov / 180 * Math.PI / 2;
            var canvas = document.getElementById("radar-" + iNumPano);
            if (canvas.getContext)
            {
                var ctx = canvas.getContext("2d");
                ctx.clearRect(0, 0, iRadarTaille * 2, iRadarTaille * 2);
                ctx.fillStyle = strRadarCouleurFond;
                ctx.strokeStyle = strRadarCouleurLigne;
                ctx.strokeWidth = 1;
                ctx.beginPath();
                ctx.moveTo(iRadarTaille, iRadarTaille);
                ctx.arc(iRadarTaille, iRadarTaille, iRadarTaille, -angleRadar, angleRadar, false);
                ctx.lineTo(iRadarTaille, iRadarTaille);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();
            }
            angleRadar = longitude - zeroNord + planNord - 90;
            $("#radar-" + iNumPano).css({
                transform: "rotate(" + angleRadar + "deg)"
            });
        }
        if (bCarteAffiche && strAfficheRadarCarte === "oui") {
            radarPosition(-bouss + 90, hfov);
        }
        $('.data-title:before').css('content', "PanoVisu " + version + " - " + programmeur + "(" + anneeProgramme + ")");
    }

    function afficheImage(numHS) {
        image = arrPointsInteret[numHS].contenu;
        couleur = arrPointsInteret[numHS].couleur;
        var lrg = $("#pano1-" + iNumPano).width();
        var haut = $("#pano1-" + iNumPano).height();
        posX = Math.round(lrg * 0.1);
        posY = Math.round(haut * 0.1);
        var img = new Image();
        img.src = image;
        img.onload = function () {
            var hautImg = img.height;
            var largImg = img.width;
            ratio = largImg / hautImg;
            var larg1 = lrg - 2 * posX;
            var haut1 = larg1 / ratio;
            if (haut1 > haut - 2 * posY) {
                hautImg = haut - 2 * posY;
                largImg = Math.round(hautImg * ratio);
            }
            else {
                largImg = lrg - 2 * posX;
                hautImg = Math.round(largImg / ratio);
            }
            mrgX = Math.round((lrg - 2 * posX - largImg) / 2) - 20;
            mrgY = Math.round((haut - 2 * posY - hautImg) / 2) - 20;
            $("#divImage-" + iNumPano).html("");
            $("#divImage-" + iNumPano).css({
                width: Math.round(lrg - 2 * posX) + "px",
                height: Math.round(haut - 2 * posY) + "px",
                padding: posY + "px " + posX + "px",
                top: "0px",
                left: "0px",
                zIndex: 10100,
                opacity: 0,
                backgroundColor: couleur
            });
            $("<img>", {id: "hsImg-" + iNumPano, class: "hsImg", src: image, title: "",
                style: "background-color : #fff;padding : 20px;box-shadow: 10px 10px 20px 0px #656565;"}).appendTo("#divImage-" + iNumPano);
            $("#hsImg-" + iNumPano).css({
                width: largImg + "px",
                height: hautImg + "px",
                marginTop: mrgY + "px",
                marginLeft: mrgX + "px"
            });
            topCroix = haut - hautImg - posY - mrgY - 15 - 40;
            rightCroix = lrg - largImg - posX - mrgX - 15 - 40;
            $("<img>", {
                id: "imgFerme-" + iNumPano,
                class: "imgFerme",
                src: "panovisu/images/fermer.png",
                style: "cursor : pointer;height :30px;width : 30px;position : absolute;top:" + topCroix + "px;right : " + rightCroix + "px;",
                title: "Cliquez pour quitter"
            }).appendTo("#divImage-" + iNumPano);
            $("#divImage-" + iNumPano).show();
            $("#divImage-" + iNumPano).animate({opacity: 1}, 1500);
        };
    }

    function afficheHTML2(url) {
        var lrg = $("#pano1-" + iNumPano).width();
        var haut = $("#pano1-" + iNumPano).height();
        posX = Math.round(lrg * 0.02);
        posY = Math.round(haut * 0.02);
        $("#divHTML-" + iNumPano).html("");
        $("#divHTML-" + iNumPano).css({
            width: lrg + "px",
            height: haut + "px",
            top: "0px",
            left: "0px",
            zIndex: 10100,
            opacity: 0.8,
            backgroundColor: "#333333"
        });
        $("<iframe>", {
            id: "ifHTML-" + iNumPano,
            class: "hsHTML",
            src: url,
            style: "background-color : #fff;position : absolute;" + "top :" + posY + "px;border : 0;",
            height: Math.round(haut - 2 * posY) + "px",
            width: "90%"
        }).appendTo("#divHTML-" + iNumPano);
        var posLeft = 5;
        $("#ifHTML-" + iNumPano).css("left", posLeft + "%");
        $("<img>", {
            id: "imgFerme-" + iNumPano,
            class: "imgFerme",
            src: "panovisu/images/fermer.png",
            style: "height :30px;width : 30px;position : absolute;top : 0px;"
        }).appendTo("#divHTML-" + iNumPano);
        $("#imgFerme-" + iNumPano).css("left", "94%");
        $("#divHTML-" + iNumPano).show();
        $("#divHTML-" + iNumPano).animate({opacity: 1}, 1500);
    }

    function afficheHTML(numeroHS) {
        url = arrPointsInteret[numeroHS].contenu;
        var lrg = $("#pano1-" + iNumPano).width();
        var haut = $("#pano1-" + iNumPano).height();
        posX = Math.round(lrg * 0.02);
        posY = Math.round(haut * 0.02);
        $("#divHTML-" + iNumPano).html("");
        $("#divHTML-" + iNumPano).css({
            width: lrg + "px",
            height: haut + "px",
            top: "0px",
            left: "0px",
            zIndex: 10100,
            opacity: 0,
            backgroundColor: arrPointsInteret[numeroHS].couleur
        });
        if (arrPointsInteret[numeroHS].largeur.lastIndexOf("%") === -1) {
            if (parseInt(arrPointsInteret[numeroHS].largeur) > $("#divHTML-" + iNumPano).width()) {
                taille = $("#divHTML-" + iNumPano).width();
            }
            else {
                taille = parseInt(arrPointsInteret[numeroHS].largeur);
            }

            $("<iframe>", {
                id: "ifHTML-" + iNumPano,
                class: "hsHTML",
                src: url,
                style: "background-color : #fff;position : absolute;" + "top :" + posY + "px;border : 0;",
                height: Math.round(haut - 2 * posY) + "px",
                width: taille


            }).appendTo("#divHTML-" + iNumPano);
        }
        else {
            taille = parseInt(arrPointsInteret[numeroHS].largeur);
            $("<iframe>", {
                id: "ifHTML-" + iNumPano,
                class: "hsHTML",
                src: url,
                style: "background-color : #fff;position : absolute;" + "top :" + posY + "px;border : 0;",
                height: Math.round(haut - 2 * posY) + "px",
                width: arrPointsInteret[numeroHS].largeur


            }).appendTo("#divHTML-" + iNumPano);

        }
        switch (arrPointsInteret[numeroHS].position) {
            case "left":
            case "right" :
                $("#ifHTML-" + iNumPano).css(arrPointsInteret[numeroHS].position, "10px");
                break;
            case "center":
                if (arrPointsInteret[numeroHS].largeur.lastIndexOf("%") === -1) {
                    posLeft = ($("#divHTML-" + iNumPano).width() - taille) / 2;
                    $("#ifHTML-" + iNumPano).css("left", posLeft);
                }
                else {
                    posLeft = (100 - taille) / 2;
                    $("#ifHTML-" + iNumPano).css("left", posLeft + "%");

                }
                break;
        }
        $("<img>", {
            id: "imgFerme-" + iNumPano,
            class: "imgFerme",
            src: "panovisu/images/fermer.png",
            style: "height :30px;width : 30px;position : absolute;top : 0px;"
        }).appendTo("#divHTML-" + iNumPano);
        switch (arrPointsInteret[numeroHS].position) {

            case "left":
                $("#imgFerme-" + iNumPano).css(arrPointsInteret[numeroHS].position, (taille - 10) + "px");
                break
            case "right" :
                $("#imgFerme-" + iNumPano).css(arrPointsInteret[numeroHS].position, "1px");
                break;
            case "center":
                if (arrPointsInteret[numeroHS].largeur.lastIndexOf("%") === -1) {
                    $("#imgFerme-" + iNumPano).css("left", (($("#divHTML-" + iNumPano).width() + taille) / 2 - 20) + "px");
                }
                else {
                    $("#imgFerme-" + iNumPano).css("left", ((100 + taille) / 2 - 1) + "%");
                }
                break;
        }
        $("#divHTML-" + iNumPano).show();
        $("#divHTML-" + iNumPano).animate({opacity: 1}, 1500);
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

    function positionneTelecommande() {
        var dX1 = telecommandeDX;
        var dY1 = telecommandeDY;
        if (bVignettes && !bVignettesRentre)
        {
            if ((strVignettesPosition === "right") && (strTelecommandePositionX === "right")) {
                dX1 = dX1 + parseInt(vignettesTailleImage) + 5;
            }
            if ((strVignettesPosition === "left") && (strTelecommandePositionX === "left")) {
                dX1 = dX1 + parseInt(vignettesTailleImage) + 5;
            }
            if ((strVignettesPosition === "bottom") && (strTelecommandePositionY === "bottom")) {
                dY1 = dY1 - 2 + parseInt(vignettesTailleImage) / 2 + 5;
            }
        }
        if (strTelecommandePositionX === "center") {
            $("#telec-" + iNumPano).css("left", (pano.width() - $("#telec-" + iNumPano).width()) / 2.0 + telecommandeDX + "px");
        }

        $("#telec-" + iNumPano).css(strTelecommandePositionX, dX1 + "px");
        $("#telec-" + iNumPano).css(strTelecommandePositionY, dY1 + "px");
    }
    /**
     * 
     * @param {type} larg1
     * @param {type} haut1
     * @returns {undefined}
     */
    function afficheBarre(larg1, haut1) {
        $("#barre-" + iNumPano + " button").css({
            height: "30px",
            width: "30px",
            borderRadius: "3px",
            border: "1px solid " + strBordure,
            backgroundColor: strCouleur
        });
        $("#barre-" + iNumPano + " button img").css({height: "26px", width: "26px", paddingBottom: "0px", marginLeft: "0px"});
        $("#barre-" + iNumPano).css({height: "40px"});
        $("#button-" + iNumPano).show();
        requestTimeout(function () {
            w1 = $("#barre-" + iNumPano).width();
            h1 = $("#barre-" + iNumPano).height();
            dX1 = parseInt(strDX);
            dY1 = parseInt(strDY) - 2;
            if (bVignettes)
            {
                if ((strVignettesPosition === "right") && (strPositionX === "right") && (!bVignettesRentre)) {
                    dX1 = parseInt(strDX) + parseInt(vignettesTailleImage) + 5;
                }
                if ((strVignettesPosition === "left") && (strPositionX === "left") && (!bVignettesRentre)) {
                    dX1 = parseInt(strDX) + parseInt(vignettesTailleImage) + 5;
                }
                if ((strVignettesPosition === "bottom") && (strPositionY === "bottom") && (!bVignettesRentre)) {
                    dY1 = parseInt(strDY) - 2 + parseInt(vignettesTailleImage) / 2 + 5;
                }
            }

            switch (strPositionX) {
                case "left" :
                    $("#barre-" + iNumPano).css({left: dX1 + "px"});
                    break;
                case "center" :
                    posX = (larg1 - w1) / 2 + dX1;
                    $("#barre-" + iNumPano).css({left: posX + "px"});
                    break;
                case "right" :
                    $("#barre-" + iNumPano).css({right: dX1 + "px"});
                    break;
            }
            switch (strPositionY) {
                case "top" :
                    posY = -(haut1 - dY1);
                    $("#barre-" + iNumPano).css({top: posY + "px"});
                    break;
                case "center" :
                    posY = -(haut1 + h1) / 2 - dY1;
                    $("#barre-" + iNumPano).css({top: posY + "px"});
                    break;
                case "bottom" :
                    $("#barre-" + iNumPano).css({top: -(35 + dY1) + "px"});
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
        //$("#container-" + iNumPano).fadeOut(200, function () {
        nouvLong = arrPointsInteret[nHotspot].longitude;
        nouvLat = arrPointsInteret[nHotspot].latitude;
        nouvFov = arrPointsInteret[nHotspot].fov;
        rechargePano(arrPointsInteret[nHotspot].contenu);
        //});
    }

    /**
     * 
     * @returns {undefined}
     */
    function pleinEcran() {
        element = document.getElementById("panovisu-" + iNumPano);
        var largeurFenetre;
        if (screenfull.enabled) {
            screenfull.toggle(element);
        }
        requestTimeout(function () {
            changeTaille();
        }, 300);
    }

    /**
     * 
     * @returns {undefined}
     */
    function afficheInfo() {
        if (bFenetreInfoPersonnalise) {
            img = new Image();
            img.src = strFenetreInfoImage;
            img.onload = function () {
                $("#infoPanovisu-" + iNumPano).html("");
                larg = Math.round(this.width * fenetreInfoTaille / 100);
                haut = Math.round(this.height * fenetreInfoTaille / 100);
                $("<img>", {
                    id: "infoImg-" + iNumPano,
                    src: this.src,
                    alt: "",
                    height: haut,
                    width: larg
                    , title: chainesTraduction[strLangage].clicFenetre
                }).appendTo("#infoPanovisu-" + iNumPano);
                $("<a>", {id: "infoUrl-" + iNumPano, text: strFenetreInfoTexteURL, href: strFenetreInfoURL, target: "_blank"}).appendTo("#infoPanovisu-" + iNumPano);
                $("#infoPanovisu-" + iNumPano).css({width: larg, height: haut});
                //$("#infoPanovisu-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
                posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2 + fenetreInfoDX;
                posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2 + fenetreInfoDX;
                $("#infoPanovisu-" + iNumPano).css({
                    top: posHaut + "px",
                    left: posGauche + "px",
                    border: "none",
                    backgroundColor: "rgba(255,255,255,0)",
                    opacity: fenetreInfoOpacite
                });
                topUrl = ($("#infoPanovisu-" + iNumPano).height() - $("#infoUrl-" + iNumPano).height()) / 2 + fenetreInfoDYURL;
                leftUrl = ($("#infoPanovisu-" + iNumPano).width() - $("#infoUrl-" + iNumPano).width()) / 2 + fenetreInfoDXURL;
                $("#infoUrl-" + iNumPano).css({color: strFenetreInfoCouleurURL,
                    fontSize: fenetreInfoTailleURL + "px",
                    position: "absolute",
                    top: topUrl + "px",
                    left: leftUrl + "px"
                });
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).css({display: "block"});
                }
            };
        }
        else {
            panoInfo = chainesTraduction[strLangage].fenetreInfo;
            $("#infoPanovisu-" + iNumPano).css({width: "450px", height: "190px"});
            $("#infoPanovisu-" + iNumPano).html(panoInfo);
            $("#infoPanovisu-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
            posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2;
            posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2;
            $("#infoPanovisu-" + iNumPano).css({top: posHaut + "px", left: posGauche + "px"});
            if (bAfficheInfo)
            {
                $("#infoPanovisu-" + iNumPano).css({display: "block"});
            }
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function afficheAide() {
        if (bFenetreAidePersonnalise) {
            img = new Image();
            img.src = strFenetreAideImage;
            img.onload = function () {
                $("#aidePanovisu-" + iNumPano).html("");
                larg = Math.round(this.width * fenetreAideTaille / 100);
                haut = Math.round(this.height * fenetreAideTaille / 100);
                $("<img>", {id: "infoImg-" + iNumPano, src: this.src, alt: "", height: haut, width: larg}).appendTo("#aidePanovisu-" + iNumPano);
                $("#aidePanovisu-" + iNumPano).css({width: larg, height: haut});
                $("#aidePanovisu-" + iNumPano).attr("title", chainesTraduction[strLangage].clicFenetre);
                posGauche = (pano.width() - $("#aidePanovisu-" + iNumPano).width()) / 2 + fenetreAideDX;
                posHaut = (pano.height() - $("#aidePanovisu-" + iNumPano).height()) / 2 + fenetreAideDX;
                $("#aidePanovisu-" + iNumPano).css({
                    top: posHaut + "px",
                    left: posGauche + "px",
                    border: "none",
                    backgroundColor: "rgba(255,255,255,0)",
                    opacity: fenetreAideOpacite
                });
                if (bAfficheAide)
                {
                    $("#aidePanovisu-" + iNumPano).css({display: "block"});
                }
            };
        }
        else {
            panoInfo = chainesTraduction[strLangage].fenetreAide;
            $("#aidePanovisu-" + iNumPano).css({width: "400px", height: "220px"});
            $("#aidePanovisu-" + iNumPano).html(panoInfo);
            posGauche = (pano.width() - $("#aidePanovisu-" + iNumPano).width()) / 2;
            posHaut = (pano.height() - $("#aidePanovisu-" + iNumPano).height()) / 2;
            $("#aidePanovisu-" + iNumPano).css({top: posHaut + "px", left: posGauche + "px"});
            if (bAfficheAide)
            {
                $("#aidePanovisu-" + iNumPano).css({display: "block"});
            }
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
        $("#infoPanovisu-" + iNumPano).css({width: "450px", height: "150px"});
        posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2;
        posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2;
        $("#infoPanovisu-" + iNumPano).css({top: posHaut + "px", left: posGauche + "px"});
        $("#infoPanovisu-" + iNumPano).html(panoInfo);
    }

    function reaffiche(lat, sensLat, FOV, sensFOV) {
        latitude += sensLat;
        fov += sensFOV;
        if (sensLat === 1 && latitude > lat) {
            latitude = lat;
        }
        if (sensLat === -1 && latitude < lat) {
            latitude = lat;
        }
        if (sensFOV === 1 && fov > FOV) {
            fov = FOV;
        }
        if (sensFOV === -1 && fov < FOV) {
            fov = FOV;
        }
        if (latitude !== lat || fov !== FOV) {
            camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
            camera.fov = fov;
            camera.updateProjectionMatrix();
            affiche();
            requestTimeout(function () {
                reaffiche(lat, sensLat, FOV, sensFOV);
                bUserInteracting = false;
            }, 5);
        }
    }

    function gotoAffiche(lat, champVisuel) {
        if (lat > latitude) {
            sensLat = 1;
        }
        else {
            sensLat = -1;
        }
        if (champVisuel > fov) {
            sensVisuel = 1;
        }
        else {
            sensVisuel = -1;
        }
        requestTimeout(function () {
            reaffiche(lat, sensLat, champVisuel, sensVisuel);
        }, 5);
    }
    /**
     * 
     * @param {type} fenetre
     * @returns {undefined}
     */
    function init(fenetre) {
        afficheInfoTitre();

        if (bPetitePlaneteDemarrage || bAutoTour) {
            if (bEltMasque === null) {
                bEltMasque = elementsVisibles;
            }
            elementsVisibles = false;
            afficheMasqueElements();
        }
        if (iNombreImageFond > 0) {
            for (i = 0; i < iNombreImageFond; i++) {
                $("<div>", {id: "imageFond-" + i + "-" + iNumPano, class: "imgFond"}).appendTo("#panovisu-" + iNumPano);
                if (arrImagesFond[i].tailleX !== "") {
                    $("<img>", {id: "imgFond-" + i + "-" + iNumPano, width: arrImagesFond[i].tailleX, height: arrImagesFond[i].tailleY, src: arrImagesFond[i].fichier, title: arrImagesFond[i].infobulle}).appendTo("#imageFond-" + i + "-" + iNumPano);
                }
                else {
                    $("<img>", {id: "imgFond-" + i + "-" + iNumPano, src: arrImagesFond[i].fichier, title: arrImagesFond[i].infobulle}).appendTo("#imageFond-" + i + "-" + iNumPano);
                }
                $("#imgFond-" + i + "-" + iNumPano).css({
                    opacity: arrImagesFond[i].opacite,
                    position: "absolute"
                });
                if (arrImagesFond[i].url !== "") {
                    $("#imgFond-" + i + "-" + iNumPano).css({
                        cursor: "pointer",
                        opacity: arrImagesFond[i].opacite
                    });
                    $("#imgFond-" + i + "-" + iNumPano).on("click", function () {
                        idHS = $(this).attr("id");
                        numHS = parseInt(idHS.split("-")[1]);
                        url1 = arrImagesFond[numHS].url;
                        cible = arrImagesFond[numHS].cible;

                        if (cible === "page") {
                            window.open(url1);
                        } else {
                            afficheHTML2(url1);
                        }
                    });
                }
            }
            positionImagesFond();
        }
        $(".barre button").css("margin-right", iEspacementBoutons);
        if (bBoussole) {
            $("#boussole-" + iNumPano).css(strBoussolePositionX, strBoussoleDX + "px");
            $("#boussole-" + iNumPano).css(strBoussolePositionY, strBoussoleDY + "px");
            $("#boussole-" + iNumPano).css("opacity", boussoleOpacite);
            $("#boussole-" + iNumPano).css({
                width: strBoussoleTaille + "px",
                height: strBoussoleTaille + "px"
            });
            $("#bousImg-" + iNumPano).attr("src", "panovisu/images/boussoles/" + strBoussoleImage);
            $("#bousAig-" + iNumPano).attr("src", "panovisu/images/boussoles/aiguille.png");
            var largBous = Math.round(parseInt(strBoussoleTaille) / 5);
            var posX = Math.round((parseInt(strBoussoleTaille) - largBous) / 2);
            $("#bousAig-" + iNumPano).css({
                position: "absolute",
                top: "0px",
                left: posX + "px",
                width: largBous + "px",
                height: strBoussoleTaille + "px"
            });
            $("#bousImg-" + iNumPano).css({
                width: strBoussoleTaille + "px",
                height: strBoussoleTaille + "px",
                top: "0px",
                left: "0px"
            });
            $("#boussole-" + iNumPano).show();
        }
        else {
            $("#bousAig-" + iNumPano).hide();
            $("#bousImg-" + iNumPano).hide();
        }
        $("#divSuivant-" + iNumPano).hide();
        $("#divPrecedent-" + iNumPano).hide();
        bDisableSuivant = (XMLsuivant === "");
        bDisablePrecedent = (XMLprecedent === "");
        if (XMLsuivant !== "" && bSuivantPrecedent) {
            $("#divSuivant-" + iNumPano).show();
        }
        if (XMLprecedent !== "" && bSuivantPrecedent) {
            $("#divPrecedent-" + iNumPano).show();
        }
        if (bMarcheArret) {
            $("#marcheArret-" + iNumPano).css(strMarcheArretPositionX, marcheArretDX + "px");
            $("#marcheArret-" + iNumPano).css(strMarcheArretPositionY, marcheArretDY + "px");
            $("#marcheArret-" + iNumPano).css({
                width: marcheArretTaille + "px",
                height: marcheArretTaille + "px"
            });
            $("#marcheArret-" + iNumPano).css("opacity", marcheArretOpacite);
            $("#MAImg-" + iNumPano).attr("src", "panovisu/images/MA/" + strMarcheArretImage);
            $("#MAImg-" + iNumPano).css({
                width: marcheArretTaille + "px",
                height: marcheArretTaille + "px",
                top: "0px",
                left: "0px"
            });
            $("#marcheArret-" + iNumPano).show();
        }
        if (bReseauxSociaux) {
            $(".RS").show();
            $("#reseauxSociaux-" + iNumPano).css(strReseauxSociauxPositionX, reseauxSociauxDX + "px");
            $("#reseauxSociaux-" + iNumPano).css(strReseauxSociauxPositionY, reseauxSociauxDY + "px");
            $("#reseauxSociaux-" + iNumPano).css({
                width: (reseauxSociauxTaille + 5) * 4 - 5 + "px",
                height: reseauxSociauxTaille + "px"
            });
            $("#reseauxSociaux-" + iNumPano).css("opacity", reseauxSociauxOpacite);
            $("#RSTW-" + iNumPano).attr("src", "panovisu/images/reseaux/twitter.png");
            $("#RSGO-" + iNumPano).attr("src", "panovisu/images/reseaux/google.png");
            $("#RSFB-" + iNumPano).attr("src", "panovisu/images/reseaux/facebook.png");
            $("#RSEM-" + iNumPano).attr("src", "panovisu/images/reseaux/email.png");
            $("#RSTW-" + iNumPano + ", #RSGO-" + iNumPano + ", #RSFB-" + iNumPano + ", #RSEM-" + iNumPano).css({
                width: reseauxSociauxTaille + "px",
                height: reseauxSociauxTaille + "px",
                top: "0px"
            });
            if (strReseauxSociauxTwitter === "non")
                $("#RSTW-" + iNumPano).hide(0);
            if (strReseauxSociauxGoogle === "non")
                $("#RSGO-" + iNumPano).hide(0);
            if (strReseauxSociauxFacebook === "non")
                $("#RSFB-" + iNumPano).hide(0);
            if (strReseauxSociauxEmail === "non")
                $("#RSEM-" + iNumPano).hide(0);
            $("#reseauxSociaux-" + iNumPano).show();
        }
        else {
            $(".RS").hide();
        }
        if (strAfficheTitre === "oui") {
            strTit = strPanoTitre;
            if (strPanoTitre2 !== "")
                strTit += "<br><span style='font-size:" + strTitreTaillePolice2 + "'>" + strPanoTitre2 + "</span>";
            $("#info-" + iNumPano).html(strTit);
            $("#info-" + iNumPano).show();
            $("#info-" + iNumPano).css({
                top: "0px",
                paddingLeft: "15px",
                paddingRight: "15px"
            });
            $("#info-" + iNumPano).css("text-align", strTitrePosition);
            if (strTitrePosition !== "center") {
                $("#info-" + iNumPano).css(strTitrePosition, "0px");
                $("#info-" + iNumPano).css("padding-" + strTitrePosition, strTitreDecalage);
            }

        }
        else {
            $("#info-" + iNumPano).css({
                display: "none",
                height: "0px",
                top: "-1000px"
            });
        }

        (strBoutons === "oui") ? $("#boutons-" + iNumPano).show() : $("#boutons-" + iNumPano).hide();
        (strDeplacements === "oui") ? $("#deplacement-" + iNumPano).css({display: "inline-block"}) : $("#deplacement-" + iNumPano).hide();
        (strZooms === "oui") ? $("#zoom-" + iNumPano).css({display: "inline-block"}) : $("#zoom-" + iNumPano).hide();
        (strOutils === "oui") ? $("#outils-" + iNumPano).css({display: "inline-block"}) : $("#outils-" + iNumPano).hide();
        (strFS === "oui") ? $("#pleinEcran-" + iNumPano).show() : $("#pleinEcran-" + iNumPano).hide();
        (strAutoRotation === "oui") ? $("#auto-" + iNumPano).show() : $("#auto-" + iNumPano).hide;
        (strModeSouris === "oui") ? $("#souris-" + iNumPano).show() : $("#souris-" + iNumPano).hide();
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
        var posit = (hauteur - $("#divSuivant-" + iNumPano).height()) / 2;
        $("#divSuivant-" + iNumPano).css("top", posit);
        $("#divPrecedent-" + iNumPano).css("top", posit);
        afficheInfo();
        afficheAide();
        if (bVignettes) {
            if (strVignettesPosition === "bottom") {
                afficheVignettesHorizontales();
            }
            else
            {
                afficheVignettesVerticales();
            }
            vignettesRentre();
        }
        if (bPlanAffiche) {
            $("#plan-" + iNumPano).html("");
            $("#plan-" + iNumPano).css(strPlanPosition, "0px");
            $("#plan-" + iNumPano).css({
                backgroundColor: strPlanCouleurFond,
                padding: "10px",
                top: "0px"
            });
            $("<img>", {id: "planAig-" + iNumPano, class: "planAig", src: "panovisu/images/plan/aiguillePlan.png"}).appendTo("#plan-" + iNumPano);
            bousX = strPlanBoussolePosition.split(":")[1];
            bousY = strPlanBoussolePosition.split(":")[0];
            $("#planAig-" + iNumPano).css(bousY, parseInt(iPlanBoussoleY + 10) + "px");
            imageObj = new Image();
            imageObj.src = "panovisu/images/plan/aiguillePlan.png";
            $("#planAig-" + iNumPano).css(bousX, parseInt(iPlanBoussoleX + 10
                    + imageObj.height / 2) + "px");
            $("#planAig-" + iNumPano).css({
                transform: "rotate(" + planNord + "deg)"
            });
            $("<img>", {id: "planImg-" + iNumPano, class: "planImg", src: strPlanImage, width: planLargeur}).appendTo("#plan-" + iNumPano);
            if (strAfficheTitre === "oui") {
                var positPlan = $("#info-" + iNumPano).height() + 10;
            }
            else {
                var positPlan = 0;
            }
            $("#plan-" + iNumPano).css("top", positPlan);
            $("<canvas>", {id: "radar-" + iNumPano, class: "radar"}).appendTo("#plan-" + iNumPano);
            transformTitre = $("#planTitre-" + iNumPano).css("transform");
            $("#planTitre-" + iNumPano).css({
                width: "160px",
                height: "30px",
                textAlign: "center",
                paddingLeft: "6px",
                transformOrigin: "0 0",
                borderTopLeftRadius: "3px",
                borderTopRightRadius: "3px",
                transform: "rotate(90deg)",
                backgroundColor: strPlanCouleurFond,
                color: strPlanCouleurTexte,
                top: positPlan
            });
            $("#planTitre-" + iNumPano).show();
            $("#planTitre-" + iNumPano).html(chainesTraduction[strLangage].plan);
            if (strPlanPosition === "left") {
                $("#planTitre-" + iNumPano).css(strPlanPosition, $("#planImg-" + iNumPano).width() + 20 + $("#planTitre-" + iNumPano).height() + "px");
                planRentreGauche();
            } else {
                $("#planTitre-" + iNumPano).css(strPlanPosition, $("#planImg-" + iNumPano).width() + 20 - $("#planTitre-" + iNumPano).width()
                        - parseInt($("#planTitre-" + iNumPano).css("paddingLeft")) + "px");
                planRentreDroite();
            }
            $("#radar-" + iNumPano).attr("width", 2 * iRadarTaille);
            $("#radar-" + iNumPano).attr("height", 2 * iRadarTaille);
            $("#radar-" + iNumPano).css({transformOrigin: "50% 50%"});
            var angleRadar = fov / 180 * Math.PI / 2;
            $("#radar-" + iNumPano).css({opacity: radarOpacite});
            if (bRadarAffiche) {
                var canvas = document.getElementById("radar-" + iNumPano);
                if (canvas.getContext)
                {
                    var ctx = canvas.getContext("2d");
                    ctx.fillStyle = strRadarCouleurFond;
                    ctx.strokeStyle = strRadarCouleurLigne;
                    ctx.beginPath();
                    ctx.moveTo(iRadarTaille, iRadarTaille);
                    ctx.arc(iRadarTaille, iRadarTaille, iRadarTaille, -angleRadar, angleRadar, false);
                    ctx.lineTo(iRadarTaille, iRadarTaille);
                    ctx.closePath();
                    ctx.fill();
                    ctx.stroke();
                }
                angleRadar = longitude - zeroNord + planNord;
            }
            for (var i = 0; i < arrPointsPlan.length; i++) {
                if (arrPointsPlan[i].xml === "actif") {
                    $("<img>", {id: "planPt-" + i + "-" + iNumPano, class: "planPoint actif", src: "panovisu/images/plan/pointActif.png", width: "12"}).appendTo("#plan-" + iNumPano);
                    if (bRadarAffiche) {
                        positXRadar = arrPointsPlan[i].positX - iRadarTaille + 10;
                        positYRadar = (arrPointsPlan[i].positY - iRadarTaille + 10);
                        $("#radar-" + iNumPano).css({
                            top: positYRadar,
                            left: positXRadar,
                            transform: "rotate(" + angleRadar + "deg)"
                        });
                    }

                }
                else {
                    $("<img>", {id: "planPt-" + i + "-" + iNumPano, class: "planPoint", title: arrPointsPlan[i].texte, src: "panovisu/images/plan/point.png", width: "12"}).appendTo("#plan-" + iNumPano);
                }
                $("#planPt-" + i + "-" + iNumPano).css({
                    top: arrPointsPlan[i].positY - $("#planPt-" + i + "-" + iNumPano).width() / 2 + 10,
                    left: arrPointsPlan[i].positX - $("#planPt-" + i + "-" + iNumPano).width() / 2 + 10
                });
            }
            $("#plan-" + iNumPano).show();
            $("#planTitre-" + iNumPano).show();
        }
        if (bCarteAffiche) {
            if (!bDejaCharge) {
                $("#carte-" + iNumPano).html("");
                $("#carte-" + iNumPano).css(strCartePosition, "0px");
                $("#carte-" + iNumPano).css({
                    backgroundColor: strCarteCouleurFond,
                    padding: "10px",
                    top: "0px",
                    width: carteLargeur + "px",
                    height: carteHauteur + "px"
                });
                $("<div>", {id: "carteOL-" + iNumPano}).appendTo("#carte-" + iNumPano);
                $("#carteOL-" + iNumPano).css({
                    padding: "0px",
                    width: carteLargeur + "px",
                    height: carteHauteur + "px"
                });

                dessineCarte("carteOL-" + iNumPano);
                setBingApiKey(strBingAPIKey);
                changeLayer(strNomLayerCarte);

                if (strAfficheTitre === "oui") {
                    var positCarte = $("#info-" + iNumPano).height() + 10;
                }
                else {
                    var positCarte = 0;
                }

                $("#carte-" + iNumPano).css("top", positCarte);
                $("<img>", {id: "homeCarte-" + iNumPano,
                    src: "panovisu/images/marqueursOL/home.png",
                    style: "position : absolute;top :19px;left : 57px;width : 24px;height: 24px;z-index :1000000;cursor : pointer;"}
                ).appendTo("#carte-" + iNumPano);
                transformTitre = $("#carteTitre-" + iNumPano).css("transform");
                $("#carteTitre-" + iNumPano).css({
                    width: "160px",
                    height: "30px",
                    textAlign: "center",
                    paddingLeft: "6px",
                    paddingTop: "3px",
                    transformOrigin: "0 0",
                    transform: "rotate(90deg)",
                    backgroundColor: strCarteCouleurFond,
                    color: strCarteCouleurTexte,
                    top: positCarte
                });
                $("#carteTitre-" + iNumPano).show();
                $("#carteTitre-" + iNumPano).html(chainesTraduction[strLangage].carte);
                if (strCartePosition === "left") {
                    $("#carteTitre-" + iNumPano).css(strCartePosition, carteLargeur + 20 + $("#carteTitre-" + iNumPano).height() + "px");
                    $("#carteTitre-" + iNumPano).css({
                        borderTopLeftRadius: "5px",
                        borderTopRightRadius: "5px"
                    });

                    carteRentreGauche();
                } else {
                    $("#carteTitre-" + iNumPano).css(strCartePosition, carteLargeur + 20 - $("#carteTitre-" + iNumPano).width()
                            - parseInt($("#carteTitre-" + iNumPano).css("paddingLeft")) + "px");
                    $("#carteTitre-" + iNumPano).css({
                        borderBottomLeftRadius: "5px",
                        borderBottomRightRadius: "5px"
                    });
                    carteRentreDroite();
                }
                if (strNomLayerCarte.substring(0, 6) === "Google") {
                    allerCoordonnees(coordCentreLong, coordCentreLat, iCarteZoom - 11);
                }
                else {
                    allerCoordonnees(coordCentreLong, coordCentreLat, iCarteZoom - 10);
                }
                $("#carte-" + iNumPano).show();
                $("#carteTitre-" + iNumPano).show();
            }
            enleveMarqueurs();
            for (var i = 0; i < iNbPointCarte; i++) {
                ajouteMarqueur(i, arrPointsCarte[i].positX, arrPointsCarte[i].positY, arrPointsCarte[i].html + "<br><img src='" + arrPointsCarte[i].image + "' width='150' style='margin-left : 60px' />");
                if (arrPointsCarte[i].xml === "actif" && strAfficheRadarCarte === "oui") {
                    afficheRadars(arrPointsCarte[i].positX, arrPointsCarte[i].positY, 45, 45, iRadarCarteTaille, strRadarCarteCouleurLigne, strRadarCarteCouleurFond, radarCarteOpacite);
                }
            }

        }
        if (bBtnAutoTour) {
            $("#btnVisiteAuto-" + iNumPano).html("");
            if (bAutoTour) {
                $("<img>", {id: "imgBtnAutoTour-" + iNumPano, src: "panovisu/images/visiteAutomatique/pauseAutoTour.png", width: tailleBtnAutoTour + "px"}).appendTo("#btnVisiteAuto-" + iNumPano);
            } else {
                $("<img>", {id: "imgBtnAutoTour-" + iNumPano, src: "panovisu/images/visiteAutomatique/playAutoTour.png", width: tailleBtnAutoTour + "px"}).appendTo("#btnVisiteAuto-" + iNumPano);
            }
            $("#btnVisiteAuto-" + iNumPano).show();
            switch (positBtnAutoTourX) {
                case "left":
                case "right":
                    $("#btnVisiteAuto-" + iNumPano).css(positBtnAutoTourX, offsetBtnAutoTourX + "px");
                    break;
            }
            $("#btnVisiteAuto-" + iNumPano).css(positBtnAutoTourY, offsetBtnAutoTourY + "px");
        }
        $("#carte-" + iNumPano).css("z-index", (10000 + carteCalque));
        $("#carteTitre-" + iNumPano).css("z-index", (10000 + carteCalque));
        $("#plan-" + iNumPano).css("z-index", (10000 + planCalque));
        $("#planTitre-" + iNumPano).css("z-index", (10000 + planCalque));
        $("#comboMenu-" + iNumPano).css("z-index", (10000 + comboCalque));
        $("#divVignettes-" + iNumPano).css("z-index", (10000 + vignettesCalque));
        $("#titreVignettes-" + iNumPano).css("z-index", (10000 + vignettesCalque));
        $("#reseauxSociaux-" + iNumPano).css("z-index", (10000 + partageCalque));
        $("#marcheArret-" + iNumPano).css("z-index", (10000 + masquageCalque));
        $("#boussole-" + iNumPano).css("z-index", (10000 + boussoleCalque));
        $("#telec-" + iNumPano).css("z-index", (10000 + barrePCalque));
        $("#barre-" + iNumPano).css("z-index", (10000 + barreCCalque));
        $("#btnVisiteAuto-" + iNumPano).css("z-index", (10000 + atCalque));
        $("#info-" + iNumPano).css("z-index", (10000 + titreCalque));
        $("#divPrecedent-" + iNumPano).css("z-index", (10000 + suivPrecCalque));
        $("#divSuivant-" + iNumPano).css("z-index", (10000 + suivPrecCalque));
        for (i = 0; i < iNombreImageFond; i++) {
            $("#imageFond-" + i + "-" + iNumPano).css("z-index", (10000 + arrImagesFond[i].calque));
        }

        vignettesRentre();
        afficheMasqueElements();
        telecommandeNavigation();
        positionneTelecommande();
        comboMenuAffiche();

    }

    function demarreAutoTour() {
        if (bEltMasque === null) {
            bEltMasque = elementsVisibles;
        }
        elementsVisibles = false;
        afficheMasqueElements();

        if (bAfficheInfo) {
            requestTimeout(function () {
                $("#infoPanovisu-" + iNumPano).fadeOut(500, function () {
                    $("#infoPanovisu-" + iNumPano).css({display: "none"});
                    bAfficheInfo = false;
                });
            }, 5000);
        }
        if (bBtnAutoTour) {
            $("#btnVisiteAuto-" + iNumPano).html("");
            $("<img>", {id: "imgBtnAutoTour-" + iNumPano,
                src: "panovisu/images/visiteAutomatique/pauseAutoTour.png",
                alt: chainesTraduction[strLangage].arreteAutoTour,
                title: chainesTraduction[strLangage].arreteAutoTour,
                style: "cursor: pointer;width : " + tailleBtnAutoTour + "px"
            }).appendTo("#btnVisiteAuto-" + iNumPano);
        }

        dxAutorotation = 1 / vitesseAutorotation;
        ddxAutorotation = 0;
        timerAutorotation = Date.now();
        timerDebutRotation = timerAutorotation;
        longitudeDebutRotation = 0;
        if (!dejaDemarre) {
            dejaDemarre = true;
            requestTimeout(function () {
                demarreAutoRotation();
            }, autoTourDemarrage * 1000);

        }
        else {
            demarreAutoRotation();
        }
    }

    function arretAutoTour() {
        elementsVisibles = bEltMasque;
        afficheMasqueElements();
        arreteAutorotation();
        if (bBtnAutoTour) {
            $("#btnVisiteAuto-" + iNumPano).html("");
            $("<img>", {id: "imgBtnAutoTour-" + iNumPano,
                src: "panovisu/images/visiteAutomatique/playAutoTour.png",
                alt: chainesTraduction[strLangage].demarreAutoTour,
                title: chainesTraduction[strLangage].demarreAutoTour,
                style: "cursor: pointer;width : " + tailleBtnAutoTour + "px"
            }).appendTo("#btnVisiteAuto-" + iNumPano);
        }

    }

    function afficheMenuContextuel() {
        if (!estTactile()) {
            var arrItem = new Array();
            for (i = 0; i < 7; i++) {
                arrItem[i] = {};
            }
            $.contextMenu('destroy');
            if (bAfficheMenuContextuel) {
                if (bPrecedentSuivantMenuContextuel) {
                    arrItem[0] = {
                        "suiv": {name: chainesTraduction[strLangage].panoSuivant, disabled: function (key, opt) {
                                return bDisableSuivant;
                            }},
                        "prec": {name: chainesTraduction[strLangage].panoPrecedent, disabled: function (key, opt) {
                                return bDisablePrecedent;
                            }},
                        "sep1": "---------"
                    };
                }
                if (bPlaneteMenuContextuel) {
                    arrItem[1] = {
                        "little": {name: chainesTraduction[strLangage].petitePlanete, disabled: function (key, opt) {
                                return bLittleDisabled;
                            }},
                        "normal": {name: chainesTraduction[strLangage].vueNormale, disabled: function (key, opt) {
                                return bNormalDisbled;
                            }},
                        "sep2": "---------"
                    };
                }
                if (bMenuPersonnalise1) {
                    arrItem[2] = {
                        "pers1": {name: strLibelleMenuContextuel1}
                    };
                    if (bMenuPersonnalise2) {
                        arrItem[3] = {
                            "pers2": {name: strLibelleMenuContextuel2}
                        };
                    }
                    arrItem[4] = {
                        "sep3": "---------"
                    };
                }
                arrItem[5] = {
                    "masqueTout": {name: function (key, opt) {
                            if (masqueTout === "")
                                masqueTout = chainesTraduction[strLangage].masqueElements;
                            return masqueTout;
                        }},
                    "visiteAuto": {name: function (key, opt) {
                            if (bAutoTour) {
                                strVisiteAuto = chainesTraduction[strLangage].arreteAutoTour;
                            } else {
                                strVisiteAuto = chainesTraduction[strLangage].demarreAutoTour;
                            }
                            return strVisiteAuto;
                        }},
                    "sep4": "---------"
                };
            }
            arrItem[6] = {
                "lm360": {name: chainesTraduction[strLangage].panoVisuSite},
                "apropos": {name: chainesTraduction[strLangage].aPropos}
            };
            items = $.extend(arrItem[0], arrItem[1], arrItem[2], arrItem[3], arrItem[4], arrItem[5], arrItem[6]);
            $.contextMenu({
                selector: "#panovisu-" + iNumPano,
                appendTo: "#container-" + iNumPano,
                zIndex: 15000,
                build: function ($trigger, e) {
                    return{
                        className: 'data-title',
                        position: function (opt, x, y)
                        {
                            opt.$menu.css({
                                top: y - $("#container-" + iNumPano).offset().top,
                                left: x - $("#container-" + iNumPano).offset().left
                            });
                        },
                        callback: function (key, options) {
                            switch (key) {
                                case "lm360":
                                    window.open("http://panovisu.fr");
                                    break;
                                case "pers1":
                                    window.open(strUrlMenuContextuel1);
                                    break;
                                case "pers2":
                                    window.open(strUrlMenuContextuel2);
                                    break;
                                case "masqueTout":
                                    toggleElements();
                                    if (elementsVisibles) {
                                        masqueTout = chainesTraduction[strLangage].masqueElements;
                                    }
                                    else {
                                        masqueTout = chainesTraduction[strLangage].afficheElements;
                                    }
                                    break;
                                case "visiteAuto":
                                    if (bAutoTour) {
                                        bAutoTour = false;
                                        bAutorotation = false;
                                        arretAutoTour();
                                    }
                                    else {
                                        bAutoTour = true;
                                        bAutorotation = true;

                                        demarreAutoTour();
                                    }
                                    break;
                                case "suiv":
                                    //$("#container-" + iNumPano).fadeOut(200, function () {
                                    rechargePano(XMLsuivant);
                                    //});
                                    break;
                                case "prec":
                                    //$("#container-" + iNumPano).fadeOut(200, function () {
                                    rechargePano(XMLprecedent);
                                    //});
                                    break;
                                case "apropos":
                                    if (bAfficheInfo)
                                    {
                                        $("#infoPanovisu-" + iNumPano).fadeOut(1000, function () {
                                            $("#infoPanovisu-" + iNumPano).css({display: "none"});
                                            bAfficheInfo = false;
                                        });
                                    }
                                    else {
                                        panoInfo = chainesTraduction[strLangage].fenetreInfo;
                                        $("#infoPanovisu-" + iNumPano).css({width: "450px", height: "190px"});
                                        $("#infoPanovisu-" + iNumPano).html(panoInfo);
                                        posGauche = (pano.width() - $("#infoPanovisu-" + iNumPano).width()) / 2;
                                        posHaut = (pano.height() - $("#infoPanovisu-" + iNumPano).height()) / 2;
                                        $("#infoPanovisu-" + iNumPano).css({top: posHaut + "px", left: posGauche + "px"});
                                        $("#infoPanovisu-" + iNumPano).css({
                                            backgroundColor: "#000",
                                            border: "1px solid white",
                                            opacity: "0.8"
                                        });
                                        if (bAfficheAide) {
                                            $("#aidePanovisu-" + iNumPano).fadeOut(1000, function () {
                                                bAfficheAide = false;
                                            });
                                            $("#infoPanovisu-" + iNumPano).fadeIn(1000, function () {
                                                bAfficheInfo = true;
                                            });
                                        } else {
                                            $("#infoPanovisu-" + iNumPano).fadeIn(1000, function () {
                                                bAfficheInfo = true;
                                            });
                                        }

                                    }
                                    break;
                                case "little":
                                    bLittleDisabled = true;
                                    bNormalDisbled = false;
                                    memFOV = fov;
                                    memMaxFOV = maxFOV;
                                    maxFOV = 170;
                                    bLittlePlanetView = true;
                                    gotoAffiche(-90, 145);
                                    break;
                                case "normal":
                                    bLittleDisabled = false;
                                    bNormalDisbled = true;
                                    maxFOV = memMaxFOV;
                                    bLittlePlanetView = false;
                                    gotoAffiche(0, memFOV);
                                    break;
                            }
                            bUserInteracting = false;
                        },
                        items: items
                    };
                }
            });
            $('.data-title').attr('data-menutitle', "PanoVisu " + version + " - " + programmeur + "(" + anneeProgramme + ")");
        }
    }


    function afficheNiveauSphere(image, niveau) {
        if (niveau < iNombreNiveaux) {
            loader = new THREE.TextureLoader();

            var nomimage = strPanoImage.split("/")[0] + "/niveau" + niveau + "/" + strPanoImage.split("/")[1];
            loader.load(nomimage + ".jpg", function (texture) {
                iNbTextures++;
                textures[iNbTextures] = texture;
                textures[iNbTextures].minFilter = THREE.LinearFilter;
                if (texture.image.width <= maxTextureSize) {
                    var img = nomimage.split("/")[2];
                    var img2 = strPanoImage.split("/")[1];
                    if (img === img2) {
                        if (bWebGL) {
                            iNbGeometries++;
                            geometries[iNbGeometries] = new THREE.SphereGeometry(405 - niveau, 100, 50);
                        }
                        else {
                            iNbGeometries++;
                            geometries[iNbGeometries] = new THREE.SphereGeometry(405 - niveau, 50, 25);
                        }
                        iNbMateriaux++;
                        materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                        iNbMeshes++;
                        meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], materiaux[iNbMateriaux]);
                        meshes[iNbMeshes].scale.x = -1;
                        scene.add(meshes[iNbMeshes]);
                        for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {
                            var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                            vertex.normalize();
                            vertex.multiplyScalar(550 - niveau);
                        }
                        niveau += 1;
                        afficheNiveauSphere(image, niveau);
                        affiche();
                    }
                }
            });
        }
        else {
            loader = new THREE.TextureLoader();
            var nomimage = strPanoImage;
            loader.load(nomimage + ".jpg", function (texture) {
                iNbTextures++;
                textures[iNbTextures] = texture;
                textures[iNbTextures].minFilter = THREE.LinearFilter;
                if (texture.image.width <= maxTextureSize) {
                    var img = nomimage.split("/")[1];
                    var img2 = strPanoImage.split("/")[1];
                    if (img === img2)
                    {
                        if (bWebGL) {
                            iNbGeometries++;
                            geometries[iNbGeometries] = new THREE.SphereGeometry(390, 100, 50);
                        }
                        else {
                            iNbGeometries++;
                            geometries[iNbGeometries] = new THREE.SphereGeometry(390, 50, 25);
                        }
                        iNbMateriaux++;
                        materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                        iNbMeshes++;
                        meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], materiaux[iNbMateriaux]);
                        meshes[iNbMeshes].scale.x = -1;
                        scene.add(meshes[iNbMeshes]);
                        for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {

                            var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                            vertex.normalize();
                            vertex.multiplyScalar(545);
                        }
                        affiche();
                    }
                }
                $(".chargement").hide();
            });
        }
    }

    function reafficheLP(lat, sensLat, lon, sensLon, FOV, sensFOV) {
        latitude += sensLat;
        longitude += sensLon;
        fov += sensFOV;
        if (sensLat === 1 && latitude > lat) {
            latitude = lat;
        }
        if (sensLat === -1 && latitude < lat) {
            latitude = lat;
        }
        if (sensLon === 1 && longitude > lon) {
            longitude = lon;
        }
        if (sensLon === -1 && longitude < lon) {
            longitude = lon;
        }
        if (sensFOV === 1 && fov > FOV) {
            fov = FOV;
        }
        if (sensFOV === -1 && fov < FOV) {
            fov = FOV;
        }
        if (latitude !== lat || fov !== FOV || longitude !== lon) {
            camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
            camera.fov = fov;
            camera.updateProjectionMatrix();
            affiche();
            requestTimeout(function () {
                reafficheLP(lat, sensLat, lon, sensLon, FOV, sensFOV);
                bUserInteracting = false;
            }, 5);
        }
        else {
            camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
            camera.fov = fov;
            camera.updateProjectionMatrix();
            bLittlePlanetView = false;
            fov = memFOV;
            maxFOV = memMaxFOV;
            longitude = memLongitude;
            latitude = memLatitude;
            elementsVisibles = bEltMasque;
            afficheMasqueElements();
            affiche();
            afficheBarre(pano.width(), pano.height());
            afficheInfoTitre();
            //$("#container-" + iNumPano).fadeIn(200);
            if (strAutoRotationMarche === "oui")
                bAutorotation = true;
            dx = 1;
            ddx = 0;
            dxAutorotation = 1 / vitesseAutorotation;
            ddxAutorotation = 0;
            timerAutorotation = Date.now();
            timerDebutRotation = timerAutorotation;
            longitudeDebutRotation = 0;
            if (bAutoTour) {
                demarreAutoTour();
            }
            else {
                demarreAutoRotation();
            }

        }
    }

    function gotoAfficheLP(lat, lon, champVisuel) {
        if (lat > latitude) {
            sensLat = 1;
        }
        else {
            sensLat = -1;
        }
        if (lon > longitude) {
            sensLon = 1;
        }
        else {
            sensLon = -1;
        }
        if (champVisuel > fov) {
            sensVisuel = 1;
        }
        else {
            sensVisuel = -1;
        }
        requestTimeout(function () {
            reafficheLP(lat, sensLat, lon, sensLon, champVisuel, sensVisuel);
        }, 5);
    }



    function IntroPetitePlanete() {
        afficheMenuContextuel();
        memFOV = fov;
        memMaxFOV = maxFOV;
        memLatitude = latitude;
        memLongitude = longitude;
        maxFOV = 170;
        bLittlePlanetView = true;
        latitude = -90;
        fov = 150;
        camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
        camera.fov = fov;
        camera.updateProjectionMatrix();
        affiche();
        if (bAfficheInfo) {
            requestTimeout(function () {
                $("#infoPanovisu-" + iNumPano).fadeOut(500, function () {
                    $("#infoPanovisu-" + iNumPano).css({display: "none"});
                    bAfficheInfo = false;
                });
            }, 4000);
        }

        requestTimeout(function () {
            gotoAfficheLP(memLatitude, memLongitude, memFOV);
        }, 8000);
    }
    /**
     * Initialisation du panoramique / équirectangulaire
     * 
     * @returns {undefined}
     */
    function initPanoSphere() {
        if (bReloaded) {
            bDessusHS = false;
            for (i = 1; i <= iNbMeshes; i++) {
                scene.remove(meshes[i]);
            }
            for (i = 1; i <= iNbTextures; i++) {
                textures[i].dispose();
            }
            for (i = 1; i <= iNbGeometries; i++) {
                geometries[i].dispose();
            }
            for (i = 1; i <= iNbMateriaux; i++) {
                materiaux[i].dispose();
            }
        }
        iNbMateriaux = 0;
        iNbTextures = 0;
        iNbMeshes = 0;
        iNbGeometries = 0;
        camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
        scene = new THREE.Scene();
        if (strMultiReso === "oui") {
            loader = new THREE.TextureLoader();
            var nomimage = strPanoImage.split("/")[0] + "/niveau0/" + strPanoImage.split("/")[1];
            loader.load(nomimage + ".jpg", function (texture) {
                iNbTextures++;
                textures[iNbTextures] = texture;
                textures[iNbTextures].minFilter = THREE.LinearFilter;
                if (!bReloaded)
                {
                    if (supportWebgl())
                    {
                        renderer = new THREE.WebGLRenderer();
                    }
                    else {
                        if (supportCanvas()) {
                            renderer = new THREE.CanvasRenderer();
                            bWebGL = false;
                        }
                        else {
                            afficheErreur();
                        }
                    }
                }
                if (bWebGL) {
                    iNbGeometries++;
                    geometries[iNbGeometries] = new THREE.SphereGeometry(405, 100, 50);
                }
                else {
                    iNbGeometries++;
                    geometries[iNbGeometries] = new THREE.SphereGeometry(405, 50, 25);
                }
                if (bWebGL) {
                    var webgl = renderer.context;
                    maxTextureSize = webgl.getParameter(webgl.MAX_TEXTURE_SIZE);
                    if (estTactile()) {
                        maxTextureSize = 4096;
                    }
                }
                else {
                    maxTextureSize = 4096;
                }
                iNbMateriaux++;
                materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                iNbMeshes++;
                meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], materiaux[iNbMateriaux]);
                meshes[iNbMeshes].scale.x = -1;
                scene.add(meshes[iNbMeshes]);
                for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {

                    var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                    vertex.normalize();
                    vertex.multiplyScalar(550);
                }
                afficheNiveauSphere(strPanoImage, 1);
                renderer.setSize(pano.width(), pano.height());
                container.append(renderer.domElement);
                for (var i = 0; i < arrPointsInteret.length; i++)
                {
                    creeHotspot(i);
                }
                timers = setInterval(function () {
                    positionHS();
                }, 100);
                changeTaille();
                if (bPetitePlaneteDemarrage) {
                    IntroPetitePlanete();
                }
                else {
                    affiche();
                    afficheBarre(pano.width(), pano.height());
                    afficheInfoTitre();
                    afficheMenuContextuel();
                    //$("#container-" + iNumPano).fadeIn(200);
                    if (strAutoRotationMarche === "oui")
                        bAutorotation = true;
                    dx = 1;
                    ddx = 0;
                    dxAutorotation = 1 / vitesseAutorotation;
                    ddxAutorotation = 0;
                    timerAutorotation = Date.now();
                    timerDebutRotation = timerAutorotation;
                    longitudeDebutRotation = 0;
                    if (bAutoTour) {
                        demarreAutoTour();
                    }
                    else {
                        demarreAutoRotation();
                    }
                }
            });
        }
        else {
            loader = new THREE.TextureLoader();
            loader.load(strPanoImage + ".jpg", function (texture) {
                iNbTextures++;
                textures[iNbTextures] = texture;
                textures[iNbTextures].minFilter = THREE.LinearFilter;
                if (!bReloaded)
                {
                    if (supportWebgl())
                    {
                        renderer = new THREE.WebGLRenderer({
                            preserveDrawingBuffer: true
                        });
                    }
                    else {
                        if (supportCanvas()) {
                            renderer = new THREE.CanvasRenderer();
                            bWebGL = false;
                        }
                        else {
                            afficheErreur();
                        }
                    }
                }
                if (bWebGL) {
                    iNbGeometries++;
                    geometries[iNbGeometries] = new THREE.SphereGeometry(405, 100, 50);
                    var webgl = renderer.context;
                    maxTextureSize = webgl.getParameter(webgl.MAX_TEXTURE_SIZE);
                }
                else {
                    iNbGeometries++;
                    geometries[iNbGeometries] = new THREE.SphereGeometry(405, 50, 25);
                    maxTextureSize = 4096;
                }

                iNbMateriaux++;
                materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: texture, overdraw: true});
                iNbMeshes++;
                meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], materiaux[iNbMateriaux]);
                meshes[iNbMeshes].scale.x = -1;
                scene.add(meshes[iNbMeshes]);
                for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {

                    var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                    vertex.normalize();
                    vertex.multiplyScalar(550);
                }
                renderer.setSize(pano.width(), pano.height());
                container.append(renderer.domElement);
                for (var i = 0; i < arrPointsInteret.length; i++)
                {
                    creeHotspot(i);
                }
                timers = setInterval(function () {
                    positionHS();
                }, 50);
                changeTaille();
                if (bPetitePlaneteDemarrage) {
                    IntroPetitePlanete();
                }
                else {
                    affiche();
                    afficheBarre(pano.width(), pano.height());
                    afficheInfoTitre();
                    afficheMenuContextuel();
                    //$("#container-" + iNumPano).fadeIn(200);
                    if (strAutoRotationMarche === "oui")
                        bAutorotation = true;
                    dx = 1;
                    ddx = 0;
                    dxAutorotation = 1 / vitesseAutorotation;
                    ddxAutorotation = 0;
                    timerAutorotation = Date.now();
                    timerDebutRotation = timerAutorotation;
                    longitudeDebutRotation = 0;
                    if (bAutoTour) {
                        demarreAutoTour();
                    }
                    else {
                        demarreAutoRotation();
                    }
                }
                $(".chargement").hide();

            });
        }
    }

    function loadTexture1(path, niveau) {

        iNbTextures++;
        var iText = iNbTextures;
        textures[iText] = new THREE.Texture(texture_placeholder);
        textures[iText].minFilter = THREE.LinearFilter;
        iNbMateriaux++;
        materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: textures[iText], overdraw: true});
        var image = new Image();
        image.onload = function () {
            var img = path.split("/")[2].split("_")[0];
            var img2 = strPanoImage.split("/")[1].split("_")[0];
            if (img === img2) {
                textures[iText].image = this;
                textures[iText].needsUpdate = true;
                nbPanoCharges += 1;
                if (nbPanoCharges < 6)
                {
                    $(".panovisuCharge").html(nbPanoCharges + "/6");
                }
                else
                {
                    $(".panovisuCharge").html("&nbsp;");
                    afficheBarre(pano.width(), pano.height());
                    afficheInfoTitre();
                    affiche();
                    if (strMultiReso === "oui" && niveau < iNombreNiveaux - 1)
                    {
                        nbPanoCharges = 0;
                        niveau += 1;
                        var nomimage = strPanoImage.split("/")[0] + "/niveau" + niveau + "/" + strPanoImage.split("/")[1];
                        var materials1 = [
                            loadTexture1(nomimage + '_r.jpg', niveau), // droite   x+
                            loadTexture1(nomimage + '_l.jpg', niveau), // gauche   x-
                            loadTexture1(nomimage + '_u.jpg', niveau), // dessus   y+
                            loadTexture1(nomimage + '_d.jpg', niveau), // dessous  y-
                            loadTexture1(nomimage + '_f.jpg', niveau), // devant   z+
                            loadTexture1(nomimage + '_b.jpg', niveau)  // derriere z-
                        ];
                        iNbGeometries++;
                        geometries[iNbGeometries] = new THREE.BoxGeometry(405 + niveau, 405 + niveau, 405 + niveau, 5, 5, 5);
                        if (iNbMeshes > 1) {
                            eff = iNbMeshes - 1;
                            meshes[eff].visible = false;
                        }
                        iNbMeshes++;
                        meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], new THREE.MeshFaceMaterial(materials1));
                        meshes[iNbMeshes].scale.x = -1;
                        scene.add(meshes[iNbMeshes]);
                        for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {
                            var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                            vertex.normalize();
                            vertex.multiplyScalar(550 - niveau);
                        }
                    }
                    else {
                        nbPanoCharges = 0;
                        var materials = [
                            loadTexture(strPanoImage + '_r.jpg'), // droite   x+
                            loadTexture(strPanoImage + '_l.jpg'), // gauche   x-
                            loadTexture(strPanoImage + '_u.jpg'), // dessus   y+
                            loadTexture(strPanoImage + '_d.jpg'), // dessous  y-
                            loadTexture(strPanoImage + '_f.jpg'), // devant   z+
                            loadTexture(strPanoImage + '_b.jpg')  // derriere z-
                        ];
                        iNbGeometries++;
                        geometries[iNbGeometries] = new THREE.BoxGeometry(415, 415, 415, 40, 40, 40);
                        if (iNbMeshes > 1) {
                            eff = iNbMeshes - 1;
                            meshes[eff].visible = false;
                        }
                        iNbMeshes++;
                        meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], new THREE.MeshFaceMaterial(materials));
                        meshes[iNbMeshes].scale.x = -1;
                        scene.add(meshes[iNbMeshes]);
                        for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {
                            var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
                            vertex.normalize();
                            vertex.multiplyScalar(550 - niveau);
                        }
                    }

                }
                changeTaille();
                affiche();
                afficheBarre(pano.width(), pano.height());
                afficheInfoTitre();
            }

        };
        image.src = path;
        return materiaux[iNbMateriaux];
    }


    /**
     * 
     * @param {type} path
     * @returns {THREE.MeshBasicMaterial}
     */
    function loadTexture(path) {
        iNbTextures++;
        var iText = iNbTextures;
        textures[iText] = new THREE.Texture(texture_placeholder);
        textures[iText].minFilter = THREE.LinearFilter;
        iNbMateriaux++;
        materiaux[iNbMateriaux] = new THREE.MeshBasicMaterial({map: textures[iText], overdraw: true});

        var image = new Image();
        image.onload = function () {
            textures[iText].image = this;
            textures[iText].needsUpdate = true;

            nbPanoCharges += 1;
            if (nbPanoCharges < 6)
                $(".panovisuCharge").html(nbPanoCharges + "/6");
            else
            {
                if (strMultiReso === "oui") {
                    if (iNbMeshes > 1) {
                        eff = iNbMeshes - 1;
                        meshes[eff].visible = false;
                    }
                }
                $(".panovisuCharge").html("&nbsp;");
                afficheBarre(pano.width(), pano.height());
                afficheInfoTitre();
                $(".chargement").hide();

            }
            changeTaille();
            affiche();
            afficheBarre(pano.width(), pano.height());
            afficheInfoTitre();
        };
        image.src = path;
        return materiaux[iNbMateriaux];
    }

    /**
     * Initialisation du panoramique / faces de cube
     * 
     * @returns {undefined}
     */
    function initPanoCube() {
        if (bReloaded) {
            for (i = 1; i <= iNbMeshes; i++) {
                scene.remove(meshes[i]);
            }
            scene.remove(mesh);
            for (i = 1; i <= iNbTextures; i++) {
                textures[i].dispose();
            }
            for (i = 1; i <= iNbGeometries; i++) {
                geometries[i].dispose();
            }
            for (i = 1; i <= iNbMateriaux; i++) {
                materiaux[i].dispose();
            }
        }
        iNbMateriaux = 0;
        iNbTextures = 0;
        iNbMeshes = 0;
        iNbGeometries = 0;

        $(".panovisuCharge").html("0/6");
        camera = new THREE.PerspectiveCamera(fov, pano.width() / pano.height(), 1, 1100);
        scene = new THREE.Scene();
        bWebGL = false;
        if (!bReloaded)
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
                bWebGL = true;
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
        if (bWebGL) {
            var webgl = renderer.context;
            maxTextureSize = webgl.getParameter(webgl.MAX_TEXTURE_SIZE);
            if (estTactile()) {
                maxTextureSize = 4096;
            }
        }
        else {
            maxTextureSize = 4096;
        }
        if (strMultiReso === "oui") {
            var nomimage = strPanoImage.split("/")[0] + "/niveau0/" + strPanoImage.split("/")[1];
            var materials = [
                loadTexture1(nomimage + '_r.jpg', 0), // droite   x+
                loadTexture1(nomimage + '_l.jpg', 0), // gauche   x-
                loadTexture1(nomimage + '_u.jpg', 0), // dessus   y+
                loadTexture1(nomimage + '_d.jpg', 0), // dessous  y-
                loadTexture1(nomimage + '_f.jpg', 0), // devant   z+
                loadTexture1(nomimage + '_b.jpg', 0)  // derriere z-
            ];
            iNbGeometries++;
            geometries[iNbGeometries] = new THREE.BoxGeometry(405, 405, 405, 5, 5, 5);
            iNbMeshes++;
            meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], new THREE.MeshFaceMaterial(materials));
        }
        else {
            nbPanoCharges = 0;
            var materials = [
                loadTexture(strPanoImage + '_r.jpg'), // droite   x+
                loadTexture(strPanoImage + '_l.jpg'), // gauche   x-
                loadTexture(strPanoImage + '_u.jpg'), // dessus   y+
                loadTexture(strPanoImage + '_d.jpg'), // dessous  y-
                loadTexture(strPanoImage + '_f.jpg'), // devant   z+
                loadTexture(strPanoImage + '_b.jpg')  // derriere z-
            ];
            iNbGeometries++;
            geometries[iNbGeometries] = new THREE.BoxGeometry(405, 405, 405, 40, 40, 40);
            iNbMeshes++;
            meshes[iNbMeshes] = new THREE.Mesh(geometries[iNbGeometries], new THREE.MeshFaceMaterial(materials));
        }
        meshes[iNbMeshes].scale.x = -1;
        scene.add(meshes[iNbMeshes]);
        for (var i = 0, l = meshes[iNbMeshes].geometry.vertices.length; i < l; i++) {
            var vertex = meshes[iNbMeshes].geometry.vertices[ i ];
            vertex.normalize();
            vertex.multiplyScalar(550);
        }

        renderer.setSize(pano.width(), pano.height());
        container.append(renderer.domElement);
        requestTimeout(function () {
            for (var i = 0; i < arrPointsInteret.length; i++)
            {
                creeHotspot(i);
            }
            changeTaille();
            if (bPetitePlaneteDemarrage) {
                IntroPetitePlanete();
            }
            else {

                affiche();
                afficheBarre(pano.width(), pano.height());
                afficheInfoTitre();
                afficheMenuContextuel();
                timers = setInterval(function () {
                    positionHS();
                }, 50);
                //$("#container-" + iNumPano).fadeIn(200);
                if (strAutoRotationMarche === "oui")
                    bAutorotation = true;
                dx = 1;
                ddx = 0;
                dxAutorotation = 1 / vitesseAutorotation;
                ddxAutorotation = 0;
                timerAutorotation = Date.now();
                timerDebutRotation = timerAutorotation;
                longitudeDebutRotation = 0;
                if (bAutoTour) {
                    demarreAutoTour();
                }
                else {
                    demarreAutoRotation();
                }
            }
        }, 1000);
    }


    function afficheInfoTitre() {
        if (strAfficheTitre === "oui" && ((strMarcheArretTitre !== "oui") || (strMarcheArretTitre === "oui" && elementsVisibles))) {
            var largeur = pano.width();
            var infoPosX = iTitreTailleFenetre;
            if (strTitreTaille !== "adapte")
                if (strTitreTailleUnite === "%") {
                    infoPosX = (largeur - 10) * iTitreTailleFenetre;
                }

            $("#info-" + iNumPano).css({
                fontFamily: "'" + strTitrePolice + "',Verdana,Arial,sans-serif",
                fontSize: strTitreTaillePolice,
                color: strTitreCouleur,
                opacite: 1.0,
                backgroundColor: strTitreFond,
                display: "block"
            });
            if (strTitreTaille !== "adapte") {
                $("#info-" + iNumPano).css({
                    width: infoPosX + "px"
                });
            }
            else {
                $("#info-" + iNumPano).css({
                    width: "auto"
                });

            }
            titreDecalage = parseFloat(strTitreDecalage) / 2.0;
            infoPosX = parseInt((largeur - $("#info-" + iNumPano).width() - parseFloat(strTitreDecalage)) / 2.0);
            if (strTitrePosition === "center") {
                $("#info-" + iNumPano).css({
                    marginLeft: infoPosX + "px",
                    paddingLeft: titreDecalage + "px",
                    paddingRight: titreDecalage + "px"
                });
            }
            else {
                $("#info-" + iNumPano).css({
                    marginLeft: "0px"
                });

            }
        }
    }
    function positionImagesFond() {
        for (i = 0; i < iNombreImageFond; i++) {
            switch (arrImagesFond[i].posX) {
                case "left" :
                    if (!(bVignettesRentre) && strVignettesAffiche === "oui" && strVignettesPosition === "left") {
                        var posit = parseInt(arrImagesFond[i].offsetX) + Math.round(vignettesTailleImage) + 10;
                        $("#imageFond-" + i + "-" + iNumPano).css(
                                arrImagesFond[i].posX, posit + "px"
                                );
                    }
                    else {
                        $("#imageFond-" + i + "-" + iNumPano).css(
                                arrImagesFond[i].posX, parseInt(arrImagesFond[i].offsetX) + "px"
                                );
                    }
                    $("#imgFond-" + i + "-" + iNumPano).css(
                            arrImagesFond[i].posX, "0px"
                            );

                    break;
                case "right" :
                    if (!(bVignettesRentre) && strVignettesAffiche === "oui" && strVignettesPosition === "right") {
                        var posit = parseInt(arrImagesFond[i].offsetX) + Math.round(vignettesTailleImage) + 10;
                        $("#imageFond-" + i + "-" + iNumPano).css(
                                arrImagesFond[i].posX, posit + "px"
                                );
                    }
                    else {
                        $("#imageFond-" + i + "-" + iNumPano).css(
                                arrImagesFond[i].posX, arrImagesFond[i].offsetX + "px"
                                );
                    }
                    $("#imgFond-" + i + "-" + iNumPano).css(
                            arrImagesFond[i].posX, "0px"
                            );
                    break;
                case "center" :
                    var positX = ($("#panovisu-" + iNumPano).width() - $("#imgFond-" + i + "-" + iNumPano).width()) / 2 + parseInt(arrImagesFond[i].offsetX);
                    $("#imageFond-" + i + "-" + iNumPano).css("left", positX + "px");
                    break
            }
            if (arrImagesFond[i].posY !== "middle") {
                if (!(bVignettesRentre) && strVignettesAffiche === "oui" && strVignettesPosition === "bottom" && arrImagesFond[i].posY === "bottom") {
                    var posit = parseInt(arrImagesFond[i].offsetY) + Math.round(vignettesTailleImage / 2) + 10;
                    $("#imageFond-" + i + "-" + iNumPano).css(
                            arrImagesFond[i].posY, posit + "px"
                            );
                }
                else {
                    $("#imageFond-" + i + "-" + iNumPano).css(
                            arrImagesFond[i].posY, arrImagesFond[i].offsetY + "px"
                            );
                }
                $("#imgFond-" + i + "-" + iNumPano).css(
                        arrImagesFond[i].posY, "0px"
                        );

            }
            else {
                var positY = ($("#panovisu-" + iNumPano).height() - $("#imgFond-" + i + "-" + iNumPano).height()) / 2 + parseInt(arrImagesFond[i].offsetY);
                $("#imageFond-" + i + "-" + iNumPano).css("top", positY + "px");
            }
        }
    }
    /**
     * 
     * @returns {undefined}
     */
    function changeTaille() {
        if (!bPleinEcran) {
            img = new Image();
            var nomImage = "./panovisu/images/" + strStyleBoutons + "/fs.png";
            img.src = nomImage;
            img.onload = function () {
                $("#pleinEcran-" + iNumPano + ">img").attr("src", this.src);
            };
            if (bTelecommande) {
                nomImage = "./panovisu/images/telecommande/fs.png";
                img.src = nomImage;
                img.onload = function () {
                    $("#telFS-" + iNumPano + ">img").attr("src", this.src);
                };
            }
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
            img = new Image();
            var nomImage = "./panovisu/images/" + strStyleBoutons + "/fs2.png";
            img.src = nomImage;
            img.onload = function () {
                $("#pleinEcran-" + iNumPano + ">img").attr("src", this.src);
            };
            if (bTelecommande) {
                nomImage = "./panovisu/images/telecommande/fs2.png";
                img.src = nomImage;
                img.onload = function () {
                    $("#telFS-" + iNumPano + ">img").attr("src", this.src);
                };
            }
            largeur = screen.width;
            hauteur = screen.height;
            pano.css({
                width: largeur + "px",
                height: hauteur + "px"
            });
        }
        camera.aspect = pano.width() / pano.height();
        camera.updateProjectionMatrix();
        if (renderer)
            renderer.setSize(pano.width(), pano.height());
        affiche();
        requestTimeout(function () {
            afficheInfo();
            afficheAide();
            afficheBarre(pano.width(), pano.height());
            afficheInfoTitre();
            if ((arrVignettesPano.length > 0) && (typeVignettes === "horizontales")) {
                var largeurFenetre;
                if (bPleinEcran) {
                    largeurFenetre = $("#pano1-" + iNumPano).width();
                }
                else {
                    largeurFenetre = $("#pano1-" + iNumPano).width() - 15;
                }
                $("#divVignettes-" + iNumPano).css({
                    width: largeurFenetre
                });
                $("#vignettes-" + iNumPano).css({
                    transform: "translate(0px,0px)"
                });
                var tailleImages = ((vignettesTailleImage + 10) * arrVignettesPano.length);
                if (largeurFenetre < tailleImages) {
                    $("#gaucheVignettes-" + iNumPano).show();
                    $("#droiteVignettes-" + iNumPano).show();
                }
                else
                {
                    $("#gaucheVignettes-" + iNumPano).hide();
                    $("#droiteVignettes-" + iNumPano).hide();
                }
            }
            if ((arrVignettesPano.length > 0) && (typeVignettes === "verticales")) {
                var hauteurFenetre;
                if (strAfficheTitre === "oui") {
                    var hauteurFenetre = $("#pano1-" + iNumPano).height() - 30 - $("#info-" + iNumPano).height();
                }
                else {
                    var hauteurFenetre = $("#pano1-" + iNumPano).height() - 30;
                }

                $("#divVignettes-" + iNumPano).css({
                    height: hauteurFenetre
                });
                $("#vignettes-" + iNumPano).css({
                    transform: "translate(0px,0px)"
                });
                var tailleImages = ((vignettesTailleImage / 2 + 10) * (arrVignettesPano.length + 1));
                if (hauteurFenetre < tailleImages) {
                    $("#hautVignettes-" + iNumPano).show();
                    $("#basVignettes-" + iNumPano).show();
                    positVig = (vignettesTailleImage - 64) / 2;
                    $("#hautVignettes-" + iNumPano).css({
                        left: positVig,
                        top: 0
                    });
                    $("#basVignettes-" + iNumPano).css({
                        left: positVig,
                        bottom: 0
                    });
                    topVignette = "17px";
                    dHauteur = 3;
                }
                else
                {
                    $("#hautVignettes-" + iNumPano).hide();
                    $("#basVignettes-" + iNumPano).hide();
                    topVignette = "4px";
                    dHauteur = 20;
                }
                $("#divVignettes-" + iNumPano).css({
                    height: hauteurFenetre + dHauteur,
                    paddingTop: topVignette
                });
            }
            var posit = (hauteur - $("#divSuivant-" + iNumPano).height()) / 2;
            $("#divSuivant-" + iNumPano).css("top", posit);
            $("#divPrecedent-" + iNumPano).css("top", posit);
            if (iNombreImageFond > 0) {
                positionImagesFond();
            }
            positionneTelecommande();
        }, 300);
    }


    /**
     * 
     * @returns {undefined}
     */
    function demarreAutoRotation() {
        timer2 = Date.now();
        bFinPano = false;
        dureeTotale = (timer2 - timerDebutRotation) / 1000.0;
        if (!bUserInteracting && !bDessusHS) {
            temps = (timer2 - timerAutorotation) / 1000.0;
            timerAutorotation = timer2;
            if (Math.abs(ddxAutorotation) < Math.abs(dxAutorotation))
                ddxAutorotation += dxAutorotation / 60.0;
            longitude += 360.0 * ddxAutorotation * temps;
            latitude = Math.max(-85, Math.min(85, latitude));
            longitudeDebutRotation += 360.0 * ddxAutorotation * temps;
            affiche();
        }
        if (bDessusHS)
            timerAutorotation = timer2;
        if (bAutoTour) {
            switch (strAutoTourType) {
                case "tours" :
                    if (longitudeDebutRotation > autoTourLimite * 360)
                        bFinPano = true;
                    break;
                case "secondes":
                    if (dureeTotale > autoTourLimite)
                        bFinPano = true;
                    break;
            }
            if (bFinPano) {
                timerDebutRotation = Date.now();
                longitudeDebutRotation = 0;
                rechargePano(XMLsuivant);
            }
        }
        if (bAutorotation && !bFinPano)
            requestAnimFrame(demarreAutoRotation);
    }

    function arreteAutorotation() {
        finX = false;
        if (!bUserInteracting) {
            timer2 = Date.now();
            temps = (timer2 - timerAutorotation) / 1000;
            timerAutorotation = timer2;
            if (Math.round(Math.abs(ddxAutorotation) * 10) / 10 > 0)
                ddxAutorotation -= dxAutorotation / 60;
            else
                finX = true;
            longitude += 360 * ddxAutorotation * temps;
            affiche();
        }
        if (!finX) {
            requestAnimFrame(arreteAutorotation);
        }


    }
    /**
     * 
     * @returns {undefined}
     */
    function creeBarreNavigation() {
        $("<div>", {id: "xmoins-" + iNumPano, class: "xmoins", title: chainesTraduction[strLangage].gauche,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#deplacement-" + iNumPano);
        $("<div>", {id: "ymoins-" + iNumPano, class: "ymoins", title: chainesTraduction[strLangage].haut,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#deplacement-" + iNumPano);
        $("<div>", {id: "yplus-" + iNumPano, class: "yplus", title: chainesTraduction[strLangage].bas,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#deplacement-" + iNumPano);
        $("<div>", {id: "xplus-" + iNumPano, class: "xplus", title: chainesTraduction[strLangage].droite,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#deplacement-" + iNumPano);
        $("<div>", {id: "zoomPlus-" + iNumPano, class: "zoomPlus", title: chainesTraduction[strLangage].zoomPlus,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#zoom-" + iNumPano);
        $("<div>", {id: "zoomMoins-" + iNumPano, class: "zoomMoins", title: chainesTraduction[strLangage].zoomMoins,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#zoom-" + iNumPano);
        $("<div>", {id: "pleinEcran-" + iNumPano, class: "pleinEcran", title: chainesTraduction[strLangage].pleinEcran,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#outils-" + iNumPano);
        $("<div>", {id: "souris-" + iNumPano, class: "souris", title: chainesTraduction[strLangage].souris,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#outils-" + iNumPano);
        $("<div>", {id: "auto-" + iNumPano, class: "auto", title: chainesTraduction[strLangage].autorotation,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#outils-" + iNumPano);
        $("<div>", {id: "binfo-" + iNumPano, class: "binfo", title: chainesTraduction[strLangage].aPropos,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#outils-" + iNumPano);
        $("<div>", {id: "aide-" + iNumPano, class: "aide", title: chainesTraduction[strLangage].aide,
            style: "background-color : " + strCouleur + ";border : 1px solid " + strBordure + ";"}).appendTo("#outils-" + iNumPano);
    }
    /**
     * 
     * @returns {undefined}
     */
    function creeImagesboutons() {
        if (strBoutons === "oui") {
            $("#xmoins-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/gauche.png", alt: ""}).appendTo("#xmoins-" + iNumPano);
            $("#ymoins-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/haut.png", alt: ""}).appendTo("#ymoins-" + iNumPano);
            $("#yplus-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/bas.png", alt: ""}).appendTo("#yplus-" + iNumPano);
            $("#xplus-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/droite.png", alt: ""}).appendTo("#xplus-" + iNumPano);
            $("#zoomPlus-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/zoomin.png", alt: ""}).appendTo("#zoomPlus-" + iNumPano);
            $("#zoomMoins-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/zoomout.png", alt: ""}).appendTo("#zoomMoins-" + iNumPano);
            $("#pleinEcran-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/fs.png", alt: ""}).appendTo("#pleinEcran-" + iNumPano);
            $("#souris-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/souris.png", alt: ""}).appendTo("#souris-" + iNumPano);
            $("#auto-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/rotation.png", alt: ""}).appendTo("#auto-" + iNumPano);
            $("#binfo-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/info.png", alt: ""}).appendTo("#binfo-" + iNumPano);
            $("#aide-" + iNumPano).html("");
            $("<img>", {src: "panovisu/images/" + strStyleBoutons + "/aide.png", alt: ""}).appendTo("#aide-" + iNumPano);
        }
    }

    /**
     * 
     * @param {type} fenetrePanoramique
     * @returns {undefined}
     */
    function creeInfo(fenetrePanoramique) {
        $("<div>", {id: "infoPanovisu-" + iNumPano, class: "infoPanovisu"}).appendTo("#" + fenetrePanoramique);
        $("#infoPanovisu-" + iNumPano).hide();
    }
    /**
     * 
     * @param {type} fenetrePanoramique
     * @returns {undefined}
     */
    function creeAide(fenetrePanoramique) {
        $("<div>", {id: "aidePanovisu-" + iNumPano, class: "aidePanovisu"}).appendTo("#" + fenetrePanoramique);
        $("#aidePanovisu-" + iNumPano).hide();
    }
    /**
     * 
     * @param {type} num
     * @returns {undefined}
     */
    function creeHotspot(num) {
        $("<div>", {id: "HS-" + num + "-" + iNumPano, class: "hotSpots"}).appendTo("#panovisu-" + iNumPano);
        $("<img>", {id: "imgHS-" + num + "-" + iNumPano, width: arrPointsInteret[num].taille, src: arrPointsInteret[num].image, title: arrPointsInteret[num].info}).appendTo("#HS-" + num + "-" + iNumPano);
        numHotspot += 1;
    }

    function enleveHS() {
        for (var i = 0; i < numHotspot; i++)
        {
            $("#HS-" + i + "-" + iNumPano).html();
            $("#HS-" + i + "-" + iNumPano).remove();
        }
        arrPointsInteret = new Array();
        numHotspot = 0;
    }

    function positionHS() {
        pHeight = $("#panovisu-" + iNumPano).height();
        pWidth = $("#panovisu-" + iNumPano).width();
        cLatitude = Math.cos(latitude * cDegToRad);
        sLatitude = Math.sin(latitude * cDegToRad);
        c1 = -pHeight / Math.tan(fov * Math.PI / 360.0) / 2; // * 0.3 -> * 1.2 / 4.0
        for (var i = 0; i < numHotspot; i++)
        {
            deltaLong = longitude % 360 - arrPointsInteret[i].long;
            cLat = Math.cos(arrPointsInteret[i].lat * cDegToRad);
            sLat = Math.sin(arrPointsInteret[i].lat * cDegToRad);
            cDeltaLong = Math.cos((deltaLong) * cDegToRad);
            sDeltaLong = Math.sin((deltaLong) * cDegToRad);
            denom = (sLat * sLatitude + cLat * cLatitude * cDeltaLong);
            coef = c1 / denom;
            if (strMarcheArretHotspots === "non" || (strMarcheArretHotspots === "oui" && elementsVisibles))
                if (cDeltaLong < 0 || bLittlePlanetView) {
                    $("#HS-" + i + "-" + iNumPano).hide();
                } else {
                    $("#HS-" + i + "-" + iNumPano).show();
                    top1 = coef * (sLat * cLatitude - cLat * sLatitude * cDeltaLong)
                            + pHeight / 2.0 - 15.0 + 'px';
                    left1 = coef * sDeltaLong * cLat
                            + pWidth / 2.0 - 15.0 + 'px';
                    $("#HS-" + i + "-" + iNumPano).css({top: top1, left: left1});
                }

        }
    }

    /**
     * 
     * @param {type} xmlFile
     * @returns {undefined}
     */
    function chargeXML(xmlFile) {
        $("#infoBulle").remove();
        $("#infoBulle").remove();
        $("#infoBulle").remove();
        bingMap = null;
        bingHybride = null;
        bingSatellite = null;
        fichierXML = xmlFile;
        bMemPlanRentre = bPlanRentre;
        bMemCarteRentre = bCarteRentre;
        bMemVignettesRentre = bVignettesRentre;
        bMemAutorotation = bAutorotation;
        bMemAutoTour = bAutoTour;
        $(".chargement").show();
        $.get(xmlFile,
                function (d) {
                    carteCalque = 1;
                    planCalque = 1;
                    comboCalque = 1;
                    vignettesCalque = 1;
                    partageCalque = 1;
                    masquageCalque = 1;
                    boussoleCalque = 1;
                    barreCCalque = 1;
                    barrePCalque = 1;
                    atCalque = 1;
                    titreCalque = 1;
                    suivPrecCalque = 1;
                    bLittleDisabled = false;
                    bNormalDisbled = true;
                    bLittlePlanetView = false;
                    var strTypeHSDefaut = "panoramique";
                    strPanoImage = "faces";
                    strCouleur = "none";
                    strStyleBoutons = "navigation";
                    strBordure = "none";
                    strPanoTitre = "";
                    strPanoTitre2 = "";
                    strTitrePolice = "Monospace";
                    strTitreCouleur = "#fff";
                    strTitreTaillePolice = "13px";
                    strTitreTaillePolice2 = "11px";
                    strTitrePosition = "center";
                    strTitreDecalage = "10px";
                    strTitreTaille = "50%";
                    strTitreFond = "#000";
                    strTitreOpacite = "1.0";
                    strDiaporamaCouleur = "rgba(0,0,0,0.8)";
                    strPanoType = "cube";
                    strAffInfo = "oui";
                    bAfficheInfo = true;
                    bAfficheAide = false;
                    maxLat = 1000;
                    minLat = -1000;
                    strAfficheTitre = "oui";
                    bFenetreInfoPersonnalise = false;
                    strFenetreInfoImage = "";
                    fenetreInfoTaille = 100;
                    fenetreInfoDX = 0;
                    fenetreInfoDY = 0;
                    fenetreInfoOpacite = 0.8;
                    strFenetreInfoURL = "";
                    strFenetreInfoTexteURL = "";
                    strFenetreInfoCouleurURL = "#ffff00";
                    fenetreInfoTailleURL = 12;
                    fenetreInfoDXURL = 0;
                    fenetreInfoDYURL = 0;
                    bFenetreAidePersonnalise = false;
                    strFenetreAideImage = "";
                    fenetreAideTaille = 100;
                    fenetreAideDX = 0;
                    fenetreAideDY = 0;
                    fenetreAideOpacite = 0.8;
                    strZooms = "oui";
                    strOutils = "oui";
                    strDeplacements = "oui";
                    strFS = "oui";
                    strAutoRotation = "oui";
                    strModeSouris = "oui";
                    strBoutons = "non";
                    strAutoRotationMarche = "non";
                    strPositionX = "center";
                    strPositionY = "bottom";
                    strDX = "0";
                    strDY = "10";
                    zeroNord = 0;
                    bBoussole = false;
                    strBoussoleImage = "rose2.png";
                    strBoussoleTaille = "120";
                    strBoussolePositionX = "right";
                    strBoussolePositionY = "bottom";
                    strBoussoleDX = "20";
                    strBoussoleDY = "20";
                    strBoussoleAffiche = "non";
                    boussoleOpacite = 0.75;
                    strBoussoleAiguille = "non";
                    bMarcheArret = false;
                    bMarcheArretAffiche = "non";
                    marcheArretOpacite = 0.8;
                    strMarcheArretImage = "MAVert.png";
                    strMarcheArretPositionX = "left";
                    strMarcheArretPositionY = "bottom";
                    marcheArretDX = 10;
                    marcheArretDY = 10;
                    marcheArretTaille = 30;
                    strMarcheArretNavigation = "non";
                    strMarcheArretTitre = "non";
                    strMarcheArretBoussole = "non";
                    strMarcheArretPlan = "non";
                    strMarcheArretReseaux = "non";
                    strMarcheArretCombo = "non";
                    strMarcheArretSuivPrec = "non";
                    strMarcheArretHotspots = "non";
                    strMarcheArretVignettes = "non";
                    bReseauxSociaux = false;
                    strReseauxSociauxAffiche = "non";
                    reseauxSociauxOpacite = 0.8;
                    strReseauxSociauxPositionX = "left";
                    strReseauxSociauxPositionY = "bottom";
                    reseauxSociauxDX = 0;
                    reseauxSociauxDY = 0;
                    reseauxSociauxTaille = 30;
                    strReseauxSociauxTwitter = "non";
                    strReseauxSociauxFacebook = "non";
                    strReseauxSociauxGoogle = "non";
                    strReseauxSociauxEmail = "non";
                    strVignettesAffiche = "non";
                    bVignettes = false;
                    strMultiReso = "non";
                    iNombreNiveaux = 0;
                    bSuivantPrecedent = false;
                    iNombreImageFond = 0;
                    vignettesOpacite = 0.8;
                    strVignettesPosition = "bottom";
                    strVignettesFondCouleur = "green";
                    strVignettesTexteCouleur = "yellow";
                    vignettesTailleImage = 0;
                    arrVignettesPano = new Array();
                    arrPointsInteret = new Array();
                    arrComboMenuPano = new Array();
                    strComboMenu = "non";
                    bComboMenuAffiche = false;
                    strComboMenuPositionX = "top";
                    strComboMenuPositionY = "right";
                    vitesseAutorotation = 30;
                    bAutorotation = false;
                    bAutoTour = false;
                    bBtnAutoTour = false;
                    positBtnAutoTourX = "right";
                    positBtnAutoTourY = "top";
                    offsetBtnAutoTourX = 10;
                    offsetBtnAutoTourY = 10;
                    tailleBtnAutoTour = 32;

                    strAutoTourType = "tours";
                    autoTourLimite = 1;
                    autoTourDemarrage = 0;
                    strARDemarrage = "non";
                    strATDemarrage = "non";
                    strPetitePlanete = "non";
                    strBtnAT = "non";
                    comboMenuDX = 10;
                    comboMenuDY = 60;
                    arrPointsPlan = new Array();
                    bPlanAffiche = false;
                    strPlanImage = "";
                    planLargeur = 0;
                    planNord = 0;
                    strPlanPosition = "left";
                    strPlanCouleurFond = "rgba(0,0,0,0.5)";
                    opacitePlan = 0.8;
                    strPlanCouleurTexte = "white";
                    strPlanBoussolePosition = "top:right";
                    iPlanBoussoleX = 0;
                    iPlanBoussoleY = 0;
                    iRadarTaille = 50;
                    radarOpacite = 0.6;
                    strRadarCouleurFond = "rgb(128,128,128)";
                    strRadarCouleurLigne = "rgb(200,200,0)";
                    bRadarAffiche = false;
                    strCarteAff = "non";
                    strCartePosition = "right";
                    strCarteCouleurFond = "#ff0000";
                    strCarteCouleurTexte = "#ffff00";
                    opaciteCarte = 0.7;
                    carteLargeur = 400;
                    carteHauteur = 300;
                    iCarteZoom = 17;
                    strAfficheRadarCarte = "non";
                    iRadarCarteTaille = 30;
                    radarCarteOpacite = 0.4;
                    strRadarCarteCouleurFond = "#ff0000";
                    strRadarCarteCouleurLigne = "#ffff00";
                    coordCentreLong = 0.0;
                    coordCentreLat = 0.0;
                    strNomLayerCarte = "OpenStreetMap";
                    strBingAPIKey = "";
                    arrPointsCarte = new Array;
                    bAfficheMenuContextuel = true;
                    bPrecedentSuivantMenuContextuel = true;
                    bPlaneteMenuContextuel = true;
                    bMenuPersonnalise1 = false;
                    bMenuPersonnalise2 = false;
                    strLibelleMenuContextuel1 = "";
                    strLibelleMenuContextuel2 = "";
                    strUrlMenuContextuel1 = "";
                    strUrlMenuContextuel2 = "";
                    strTelecommande = "non";
                    bTelecommande = false;
                    strTelecommandeFS = "oui";
                    strTelecommandeAutorotation = "oui";
                    strTelecommandeSouris = "oui";
                    strTelecommandeInfo = "oui";
                    strTelecommandeAide = "oui";
                    strTelecommandePositionX = "bottom";
                    strTelecommandePositionY = "right";
                    telecommandeDX = 0;
                    telecommandeDY = 0;
                    telecommandeTaille = 25.0;
                    telecommandeTailleBouton = 40.0;
                    strLien1BarrePersonnalisee = "";
                    strLien2BarrePersonnalisee = "";
                    $("#divVignettes-" + iNumPano).html("");
                    $("<img>", {id: "gaucheVignettes-" + iNumPano, class: "positionVignettes", src: "panovisu/images/interface/gauche.png"}).appendTo("#divVignettes-" + iNumPano);
                    $("<img>", {id: "droiteVignettes-" + iNumPano, class: "positionVignettes", src: "panovisu/images/interface/droite.png"}).appendTo("#divVignettes-" + iNumPano);
                    $("<img>", {id: "hautVignettes-" + iNumPano, class: "positionVignettes", src: "panovisu/images/interface/haut.png"}).appendTo("#divVignettes-" + iNumPano);
                    $("<img>", {id: "basVignettes-" + iNumPano, class: "positionVignettes", src: "panovisu/images/interface/bas.png"}).appendTo("#divVignettes-" + iNumPano);
                    /**
                     * Définition du panoramique Ã  afficher 
                     */
                    var XMLPano = $(d).find('pano');
                    strPanoImage = XMLPano.attr('image') || strPanoImage;
                    strPanoTitre = XMLPano.attr('titre') || strPanoTitre;
                    strPanoTitre2 = XMLPano.attr('titre2') || strPanoTitre2;
                    strTitrePolice = XMLPano.attr('titrePolice') || strTitrePolice;
                    strTitreCouleur = XMLPano.attr('titreCouleur') || strTitreCouleur;
                    strTitreTaille = XMLPano.attr('titreTaille') || strTitreTaille;
                    strTitreTaillePolice = XMLPano.attr('titreTaillePolice') || strTitreTaillePolice;
                    strTitreTaillePolice2 = XMLPano.attr('titreTaillePolice2') || strTitreTaillePolice2;
                    strTitrePosition = XMLPano.attr('titrePosition') || strTitrePosition;
                    strTitreDecalage = XMLPano.attr('titreDecalage') || strTitreDecalage;
                    strTitreFond = XMLPano.attr('titreFond') || strTitreFond;
                    strTitreOpacite = XMLPano.attr('titreOpacite') || strTitreOpacite;
                    titreCalque = parseInt(XMLPano.attr('titreCalque')) || titreCalque;
                    strDiaporamaCouleur = XMLPano.attr('diaporamaCouleur') || strDiaporamaCouleur;
                    strPanoType = XMLPano.attr('type') || strPanoType;
                    strMultiReso = XMLPano.attr('multiReso') || strMultiReso;
                    iNombreNiveaux = parseInt(XMLPano.attr('nombreNiveaux')) || iNombreNiveaux;
                    strAutoRotationMarche = XMLPano.attr('rotation') || strAutoRotationMarche;
                    maxLat = parseFloat(XMLPano.attr('maxLat')) || maxLat;
                    minLat = parseFloat(XMLPano.attr('minLat')) || minLat;
                    if (nouvLong !== -1000) {
                        longitude = nouvLong;
                    }
                    else {
                        if (XMLPano.attr('regardX') === "0") {
                            longitude = 0;
                        }
                        else {
                            longitude = parseFloat(XMLPano.attr('regardX')) || longitude;
                        }
                    }
                    if (nouvLat !== -1000) {
                        latitude = nouvLat;
                    }
                    else {
                        if (XMLPano.attr('regardY') === "0") {
                            latitude = 0;
                        }
                        else {
                            latitude = parseFloat(XMLPano.attr('regardY')) || latitude;
                        }
                    }

                    if (nouvFov !== 0) {
                        fov = nouvFov;
                    }
                    else {
                        fov = parseFloat(XMLPano.attr('champVisuel')) || fov;
                    }
                    maxFOV = parseFloat(XMLPano.attr('maxFOV')) || maxFOV;
                    minFOV = parseFloat(XMLPano.attr('minFOV')) || minFOV;
                    if (fov > maxFOV)
                        fov = maxFOV;
                    if (fov < minFOV)
                        fov = minFOV;
                    nouvLong = -1000;
                    nouvLat = -1000;
                    nouvFov = 0;
                    strAfficheTitre = XMLPano.attr('afftitre') || strAfficheTitre;
                    strAffInfo = XMLPano.attr('affinfo') || strAffInfo;
                    zeroNord = parseFloat(XMLPano.attr('zeroNord')) || zeroNord;
                    strReplieVignettes = XMLPano.attr('replieVignettes') || "";
                    bVignettesRentre = (strReplieVignettes === "oui" || bVignettesRentre);
                    strRepliePlan = XMLPano.attr('repliePlan') || "";
                    bPlanRentre = (strRepliePlan === "oui" || bPlanRentre);
                    strReplieCarte = XMLPano.attr('replieCarte') || "";
                    bCarteRentre = (strReplieCarte === "oui" || bCarteRentre);
                    strARDemarrage = XMLPano.attr('autorotation') || "non";
                    bAutorotation = (strARDemarrage === "oui");
                    vitesseAutorotation = parseFloat(XMLPano.attr('vitesseAR')) || vitesseAutorotation;
                    strATDemarrage = XMLPano.attr('autotour') || "non";
                    bAutoTour = (strATDemarrage === "oui");
                    strBtnAT = XMLPano.attr('atBouton') || "non";
                    bBtnAutoTour = (strBtnAT === "oui");
                    tailleBtnAutoTour = parseFloat(XMLPano.attr('atBoutonTaille')) || tailleBtnAutoTour;
                    positBtnAutoTourX = XMLPano.attr('atBoutonPositionX') || positBtnAutoTourX;
                    positBtnAutoTourY = XMLPano.attr('atBoutonPositionY') || positBtnAutoTourY;
                    offsetBtnAutoTourX = parseFloat(XMLPano.attr('atBoutonOffsetX')) || offsetBtnAutoTourX;
                    offsetBtnAutoTourY = parseFloat(XMLPano.attr('atBoutonOffsetY')) || offsetBtnAutoTourY;
                    atCalque = parseInt(XMLPano.attr('atCalque')) || atCalque;
                    autoTourLimite = parseFloat(XMLPano.attr('limiteAT')) || autoTourLimite;
                    autoTourDemarrage = parseFloat(XMLPano.attr('demarrageAT')) || autoTourDemarrage;
                    strAutoTourType = XMLPano.attr('typeAT') || strAutoTourType;
                    if (bAutoTour)
                        bAutorotation = true;
                    strPetitePlanete = XMLPano.attr('petitePlanete') || "non";
                    bPetitePlaneteDemarrage = (strPetitePlanete === "oui");

                    if (bDejaCharge) {
                        bPetitePlaneteDemarrage = false;
                        bVignettesRentre = bMemVignettesRentre;
                        bCarteRentre = bMemCarteRentre;
                        bPlanRentre = bMemPlanRentre;
                        bAutoTour = bMemAutoTour;
                        bAutorotation = bMemAutorotation;
                    }
                    if (bReloaded) {
                        strAffInfo = false;
                    }
                    if (strAffInfo === "oui") {
                        bAfficheInfo = true;
                    } else {
                        bAfficheInfo = false;
                    }
                    var XMLFenetreInfo = $(d).find('fenetreInfo');
                    InfoAffiche = XMLFenetreInfo.attr('affiche') || "non";
                    bFenetreInfoPersonnalise = (InfoAffiche === "oui");
                    if (bFenetreInfoPersonnalise) {
                        strFenetreInfoImage = XMLFenetreInfo.attr('image') || strFenetreInfoImage;
                        fenetreInfoTaille = parseFloat(XMLFenetreInfo.attr('taille')) || fenetreInfoTaille;
                        fenetreInfoDX = parseFloat(XMLFenetreInfo.attr('dX')) || fenetreInfoDX;
                        fenetreInfoDY = parseFloat(XMLFenetreInfo.attr('dY')) || fenetreInfoDY;
                        fenetreInfoOpacite = parseFloat(XMLFenetreInfo.attr('opacite')) || fenetreInfoOpacite;
                        strFenetreInfoURL = XMLFenetreInfo.attr('URL') || strFenetreInfoURL;
                        strFenetreInfoTexteURL = XMLFenetreInfo.attr('texteURL') || strFenetreInfoTexteURL;
                        strFenetreInfoCouleurURL = XMLFenetreInfo.attr('couleurURL') || strFenetreInfoCouleurURL;
                        fenetreInfoTailleURL = parseFloat(XMLFenetreInfo.attr('tailleURL')) || fenetreInfoTailleURL;
                        fenetreInfoDXURL = parseFloat(XMLFenetreInfo.attr('URLdX')) || fenetreInfoDXURL;
                        fenetreInfoDYURL = parseFloat(XMLFenetreInfo.attr('URLdY')) || fenetreInfoDYURL;
                    }

                    var XMLFenetreAide = $(d).find('fenetreAide');
                    AideAffiche = XMLFenetreAide.attr('affiche') || "non";
                    bFenetreAidePersonnalise = (AideAffiche === "oui");
                    if (bFenetreAidePersonnalise) {
                        strFenetreAideImage = XMLFenetreAide.attr('image') || strFenetreAideImage;
                        fenetreAideTaille = parseFloat(XMLFenetreAide.attr('taille')) || fenetreAideTaille;
                        fenetreAideDX = parseFloat(XMLFenetreAide.attr('dX')) || fenetreAideDX;
                        fenetreAideDY = parseFloat(XMLFenetreAide.attr('dY')) || fenetreAideDY;
                        fenetreAideOpacite = parseFloat(XMLFenetreAide.attr('opacite')) || fenetreAideOpacite;
                    }



                    var XMLSuivantPrecedent = $(d).find('suivantPrecedent');
                    XMLsuivant = XMLSuivantPrecedent.attr('suivant') || "";
                    XMLprecedent = XMLSuivantPrecedent.attr('precedent') || "";
                    suivPrecCalque = parseInt(XMLSuivantPrecedent.attr('suivPrecCalque')) || suivPrecCalque;
                    strAfficheSuivantPrecedent = XMLSuivantPrecedent.attr('afficheSuivantPrecedent') || "non";
                    bSuivantPrecedent = (strAfficheSuivantPrecedent === "oui");
                    /*
                     * 
                     * 
                     */
                    var XMLBoussole = $(d).find('boussole');
                    strBoussoleAffiche = XMLBoussole.attr('affiche') || strBoussoleAffiche;
                    bBoussole = (strBoussoleAffiche === "oui");
                    strBoussoleImage = XMLBoussole.attr('image') || strBoussoleImage;
                    strBoussoleTaille = XMLBoussole.attr('taille') || strBoussoleTaille;
                    strBoussolePositionX = XMLBoussole.attr('positionX') || strBoussolePositionX;
                    strBoussolePositionY = XMLBoussole.attr('positionY') || strBoussolePositionY;
                    boussoleCalque = parseInt(XMLBoussole.attr('boussoleCalque')) || boussoleCalque;
                    strBoussoleDX = XMLBoussole.attr('dX') || strBoussoleDX;
                    strBoussoleDY = XMLBoussole.attr('dY') || strBoussoleDY;
                    boussoleOpacite = parseFloat(XMLBoussole.attr('opacite')) || boussoleOpacite;
                    strBoussoleAiguille = XMLBoussole.attr('aiguille') || strBoussoleAiguille;
                    /*
                     * 
                     * 
                     */
                    var XMLMenuContextuel = $(d).find('menuContextuel');
                    bMCAffiche = XMLMenuContextuel.attr('affiche') || "non";
                    bAfficheMenuContextuel = (bMCAffiche === "oui");
                    strPrecSuivMC = XMLMenuContextuel.attr('precSuiv') || "oui";
                    bPrecedentSuivantMenuContextuel = (strPrecSuivMC === "oui");
                    strPlaneteMC = XMLMenuContextuel.attr('planete') || "oui";
                    bPlaneteMenuContextuel = (strPlaneteMC === "oui");
                    strPersMC1 = XMLMenuContextuel.attr('pers1') || "non";
                    bMenuPersonnalise1 = (strPersMC1 === "oui");
                    strPersMC2 = XMLMenuContextuel.attr('pers2') || "non";
                    bMenuPersonnalise2 = (strPersMC2 === "oui");
                    strLibelleMenuContextuel1 = XMLMenuContextuel.attr('lib1') || strLibelleMenuContextuel1;
                    strLibelleMenuContextuel2 = XMLMenuContextuel.attr('lib2') || strLibelleMenuContextuel2;
                    strUrlMenuContextuel1 = XMLMenuContextuel.attr('url1') || strUrlMenuContextuel1;
                    strUrlMenuContextuel2 = XMLMenuContextuel.attr('url2') || strUrlMenuContextuel2;
                    /*
                     * Reseaux Sociaux
                     * 
                     */
                    var XMLReseauxSociaux = $(d).find('reseauxSociaux');
                    strReseauxSociauxAffiche = XMLReseauxSociaux.attr('affiche') || strReseauxSociauxAffiche;
                    bReseauxSociaux = (strReseauxSociauxAffiche === "oui");
                    reseauxSociauxOpacite = parseFloat(XMLReseauxSociaux.attr('opacite')) || reseauxSociauxOpacite;
                    strReseauxSociauxPositionX = XMLReseauxSociaux.attr('positionX') || strReseauxSociauxPositionX;
                    strReseauxSociauxPositionY = XMLReseauxSociaux.attr('positionY') || strReseauxSociauxPositionY;
                    reseauxSociauxDX = parseFloat(XMLReseauxSociaux.attr('dX')) || reseauxSociauxDX;
                    reseauxSociauxDY = parseFloat(XMLReseauxSociaux.attr('dY')) || reseauxSociauxDY;
                    partageCalque = parseInt(XMLReseauxSociaux.attr('partageCalque')) || partageCalque;

                    reseauxSociauxTaille = parseFloat(XMLReseauxSociaux.attr('taille')) || reseauxSociauxTaille;
                    strReseauxSociauxTwitter = XMLReseauxSociaux.attr('twitter') || strReseauxSociauxTwitter;
                    strReseauxSociauxFacebook = XMLReseauxSociaux.attr('facebook') || strReseauxSociauxFacebook;
                    strReseauxSociauxGoogle = XMLReseauxSociaux.attr('google') || strReseauxSociauxGoogle;
                    strReseauxSociauxEmail = XMLReseauxSociaux.attr('email') || strReseauxSociauxEmail;
                    /*
                     * Bouton de masquage
                     * 
                     */

                    var XMLMarcheArret = $(d).find('marcheArret');
                    bMarcheArretAffiche = XMLMarcheArret.attr('affiche') || bMarcheArretAffiche;
                    bMarcheArret = (bMarcheArretAffiche === "oui");
                    strMarcheArretImage = XMLMarcheArret.attr('image') || strMarcheArretImage;
                    marcheArretOpacite = parseFloat(XMLMarcheArret.attr('opacite')) || marcheArretOpacite;
                    strMarcheArretPositionX = XMLMarcheArret.attr('positionX') || strMarcheArretPositionX;
                    strMarcheArretPositionY = XMLMarcheArret.attr('positionY') || strMarcheArretPositionY;
                    marcheArretDX = parseFloat(XMLMarcheArret.attr('dX')) || marcheArretDX;
                    marcheArretDY = parseFloat(XMLMarcheArret.attr('dY')) || marcheArretDY;
                    marcheArretTaille = parseFloat(XMLMarcheArret.attr('taille')) || marcheArretTaille;
                    strMarcheArretNavigation = XMLMarcheArret.attr('navigation') || strMarcheArretNavigation;
                    strMarcheArretBoussole = XMLMarcheArret.attr('boussole') || strMarcheArretBoussole;
                    strMarcheArretTitre = XMLMarcheArret.attr('titre') || strMarcheArretTitre;
                    strMarcheArretPlan = XMLMarcheArret.attr('plan') || strMarcheArretPlan;
                    strMarcheArretReseaux = XMLMarcheArret.attr('reseaux') || strMarcheArretReseaux;
                    masquageCalque = parseInt(XMLMarcheArret.attr('masquageCalque')) || masquageCalque;
                    strMarcheArretVignettes = XMLMarcheArret.attr('vignettes') || strMarcheArretVignettes;
                    strMarcheArretCombo = XMLMarcheArret.attr('combo') || strMarcheArretCombo;
                    strMarcheArretSuivPrec = XMLMarcheArret.attr('suivPrec') || strMarcheArretSuivPrec;
                    strMarcheArretHotspots = XMLMarcheArret.attr('hotspots') || strMarcheArretHotspots;
                    /**
                     * Défintion pour la barre des boutons
                     */
                    var XMLBoutons = $(d).find('boutons');
                    strDeplacements = XMLBoutons.attr('deplacements') || strDeplacements;
                    strStyleBoutons = XMLBoutons.attr('styleBoutons') || strStyleBoutons;
                    strZooms = XMLBoutons.attr('zoom') || strZooms;
                    strOutils = XMLBoutons.attr('outils') || strOutils;
                    iEspacementBoutons = parseInt(XMLBoutons.attr('espacement')) - 4 || 0;
                    strFS = XMLBoutons.attr('fs') || strFS;
                    strAutoRotation = XMLBoutons.attr('rotation') || strAutoRotation;
                    strModeSouris = XMLBoutons.attr('souris') || strModeSouris;
                    strBoutons = XMLBoutons.attr('visible') || strBoutons;
                    strPositionX = XMLBoutons.attr('positionX') || strPositionX;
                    strPositionY = XMLBoutons.attr('positionY') || strPositionY;
                    barreCCalque = parseInt(XMLBoutons.attr('barreCCalque')) || barreCCalque;

                    strCouleur = XMLBoutons.attr('couleur') || strCouleur;
                    strBordure = XMLBoutons.attr('bordure') || strBordure;
                    $("#xmoins-" + iNumPano + ",#xplus-" + iNumPano + ",#ymoins-" + iNumPano + ",#yplus-" + iNumPano + ",#zoomPlus-" + iNumPano + ",#zoomMoins-" + iNumPano +
                            ",#pleinEcran-" + iNumPano + ",#souris-" + iNumPano + ",#auto-" + iNumPano + ",#binfo-" + iNumPano + ",#aide-" + iNumPano).css({
                        backgroundColor: strCouleur,
                        border: "1px solid " + strBordure
                    });
                    strDX = XMLBoutons.attr('dX') || strDX;
                    strDY = XMLBoutons.attr('dY') || strDY;
                    /**
                     * Défintion pour la barre des telecommande
                     */
                    arrBoutonsTelecommande = new Array();
                    arrZonesTelecommande = new Array();
                    var XMLTelecommande = $(d).find('telecommande');
                    strTelecommande = XMLTelecommande.attr('visible') || strTelecommande;
                    bTelecommande = (strTelecommande === "oui");
                    strTelecommandeFS = XMLTelecommande.attr('fs') || strTelecommandeFS;
                    strTelecommandeAutorotation = XMLTelecommande.attr('rotation') || strTelecommandeAutorotation;
                    strTelecommandeSouris = XMLTelecommande.attr('souris') || strTelecommandeSouris;
                    strTelecommandeInfo = XMLTelecommande.attr('info') || strTelecommandeSouris;
                    strTelecommandeAide = XMLTelecommande.attr('aide') || strTelecommandeSouris;
                    strTelecommandePositionX = XMLTelecommande.attr('positionX') || strTelecommandePositionX;
                    strTelecommandePositionY = XMLTelecommande.attr('positionY') || strTelecommandePositionY;
                    barrePCalque = parseInt(XMLTelecommande.attr('barrePCalque')) || barrePCalque;

                    telecommandeDX = parseFloat(XMLTelecommande.attr('dX')) || telecommandeDX;
                    telecommandeDY = parseFloat(XMLTelecommande.attr('dY')) || telecommandeDY;
                    telecommandeTaille = parseFloat(XMLTelecommande.attr('taille')) || telecommandeTaille;
                    telecommandeTailleBouton = parseFloat(XMLTelecommande.attr('tailleBouton')) || telecommandeTailleBouton;
                    strLien1BarrePersonnalisee = XMLTelecommande.attr('lien1') || strLien1BarrePersonnalisee;
                    strLien2BarrePersonnalisee = XMLTelecommande.attr('lien2') || strLien2BarrePersonnalisee;
                    i = 0;
                    j = 0;
                    $(d).find('zoneNavPerso').each(function () {
                        if ($(this).attr('id').substring(0, 4) === "area") {
                            var iNum = parseInt($(this).attr('id').substring(5, 6));
                            arrZonesTelecommande[iNum] = new boutonTelecommande();
                            arrZonesTelecommande[iNum].id = $(this).attr('id');
                            arrZonesTelecommande[iNum].alt = $(this).attr('alt');
                            arrZonesTelecommande[iNum].title = $(this).attr('title') || "";
                            arrZonesTelecommande[iNum].shape = $(this).attr('shape') || "";
                            arrZonesTelecommande[iNum].coords = $(this).attr('coords') || "";
                            xmin = 1500;
                            ymin = 1500;
                            xmax = -1500;
                            ymax = -1500;
                            if (arrZonesTelecommande[iNum].shape === "poly" || arrZonesTelecommande[iNum].shape === "rect") {
                                coord = arrZonesTelecommande[iNum].coords.split(",");
                                for (kk = 0; kk < coord.length; kk += 2) {
                                    if (parseFloat(coord[kk]) < xmin)
                                        xmin = parseFloat(coord[kk]);
                                    if (parseFloat(coord[kk + 1]) < ymin)
                                        ymin = parseFloat(coord[kk + 1]);
                                    if (parseFloat(coord[kk]) > xmax)
                                        xmax = parseFloat(coord[kk]);
                                    if (parseFloat(coord[kk + 1]) > ymax)
                                        ymax = parseFloat(coord[kk + 1]);
                                    arrZonesTelecommande[iNum].centerX = (xmin + xmax) / 2.0;
                                    arrZonesTelecommande[iNum].centerY = (ymin + ymax) / 2.0;
                                }
                            }
                            else {
                                coord = arrZonesTelecommande[iNum].coords.split(",");
                                xmin = parseFloat(coord[0]);
                                ymin = parseFloat(coord[1]);
                                arrZonesTelecommande[iNum].centerX = xmin;
                                arrZonesTelecommande[iNum].centerY = ymin;
                            }
                            j++;
                        }
                        else {
                            arrBoutonsTelecommande[i] = new boutonTelecommande();
                            arrBoutonsTelecommande[i].id = $(this).attr('id');
                            arrBoutonsTelecommande[i].alt = $(this).attr('alt');
                            arrBoutonsTelecommande[i].title = $(this).attr('title') || "";
                            arrBoutonsTelecommande[i].shape = $(this).attr('shape') || "";
                            arrBoutonsTelecommande[i].coords = $(this).attr('coords') || "";
                            xmin = 1500;
                            ymin = 1500;
                            i++;
                        }
                    });
                    /*
                     * Hotspots
                     */
                    enleveHS();
                    i = 0;
                    $(d).find('point').each(function () {
                        arrPointsInteret[i] = new pointInteret();
                        arrPointsInteret[i].type = $(this).attr('type') || arrPointsInteret[i].type;
                        switch (arrPointsInteret[i].type) {
                            case "panoramique" :
                                arrPointsInteret[i].contenu = $(this).attr('xml');
                                break;
                            case "image" :
                                arrPointsInteret[i].contenu = $(this).attr('img');
                                break;
                            case "html" :
                                arrPointsInteret[i].contenu = $(this).attr('url');
                                break;
                        }
                        arrPointsInteret[i].info = $(this).attr('info') || "";
                        arrPointsInteret[i].image = $(this).attr('image') || "panovisu/images/sprite2.png";
                        arrPointsInteret[i].long = $(this).attr('long') || 0;
                        arrPointsInteret[i].anime = $(this).attr('anime') || "false";
                        arrPointsInteret[i].lat = $(this).attr('lat') || 0;
                        arrPointsInteret[i].longitude = parseFloat($(this).attr('regardX')) || -1000;
                        arrPointsInteret[i].latitude = parseFloat($(this).attr('regardY')) || -1000;
                        arrPointsInteret[i].fov = parseFloat($(this).attr('champVisuel')) || 0;
                        arrPointsInteret[i].largeur = $(this).attr('taille') || "100%";
                        arrPointsInteret[i].taille = $(this).attr('tailleHS') || "30px";
                        arrPointsInteret[i].position = $(this).attr('position') || "center";
                        arrPointsInteret[i].couleur = $(this).attr('couleur') || "rgba(0,0,0,0.7)";
                        i++;
                    });
                    /*
                     * 
                     * barre des vignettes
                     */
                    var XMLVignettes = $(d).find('vignettes');
                    strVignettesAffiche = XMLVignettes.attr('affiche') || strVignettesAffiche;
                    bVignettes = (strVignettesAffiche === "oui");
                    vignettesOpacite = parseFloat(XMLVignettes.attr('opacite')) || vignettesOpacite;
                    strVignettesPosition = XMLVignettes.attr('position') || strVignettesPosition;
                    strVignettesFondCouleur = XMLVignettes.attr("fondCouleur") || strVignettesFondCouleur;
                    vignettesCalque = parseInt(XMLVignettes.attr('vignettesCalque')) || vignettesCalque;
                    strVignettesTexteCouleur = XMLVignettes.attr("texteCouleur") || strVignettesTexteCouleur;
                    vignettesTaille = parseFloat(XMLVignettes.attr('taille')) || vignettesTaille;
                    vignettesTailleImage = parseFloat(XMLVignettes.attr('tailleImage')) || vignettesTailleImage;
                    /*
                     *   vignettes des panoramiques
                     */
                    i = 0;
                    $(d).find('imageVignette').each(function () {
                        arrVignettesPano[i] = new vignettePano();
                        arrVignettesPano[i].xml = $(this).attr('xml');
                        arrVignettesPano[i].image = $(this).attr('image');
                        arrVignettesPano[i].txt = $(this).attr('infoBulle') || "";
                        i++;
                    });
                    /*
                     * 
                     * barre des comboMenu
                     */

                    var XMLComboMenu = $(d).find('comboMenu');
                    strComboMenu = XMLComboMenu.attr('affiche') || strComboMenu;
                    bComboMenuAffiche = (strComboMenu === "oui");
                    strComboMenuPositionX = XMLComboMenu.attr('positionX') || strComboMenuPositionX;
                    strComboMenuPositionY = XMLComboMenu.attr('positionY') || strComboMenuPositionY;
                    comboCalque = parseInt(XMLComboMenu.attr('comboCalque')) || comboCalque;

                    comboMenuDX = XMLComboMenu.attr('dX') || comboMenuDX;
                    comboMenuDY = XMLComboMenu.attr('dY') || comboMenuDY;
                    /*
                     *   comboMenu des panoramiques
                     */
                    i = 0;
                    $(d).find('imageComboMenu').each(function () {
                        arrComboMenuPano[i] = new cbMenuPano();
                        arrComboMenuPano[i].xml = $(this).attr('xml');
                        arrComboMenuPano[i].image = $(this).attr('image');
                        arrComboMenuPano[i].titre = $(this).attr('titre') || "";
                        arrComboMenuPano[i].select = $(this).attr('selectionne') || "";
                        arrComboMenuPano[i].sousTitre = $(this).attr('sousTitre') || "";
                        i++;
                    });
                    /*
                     * Images de fond
                     */

                    iNombreImageFond = 0;
                    arrImagesFond = new Array();
                    $(".imgFond").remove();
                    $(d).find('imageFond').each(function () {
                        arrImagesFond[iNombreImageFond] = new imageFond();
                        arrImagesFond[iNombreImageFond].fichier = $(this).attr('fichier') || "";
                        arrImagesFond[iNombreImageFond].url = $(this).attr('url') || "";
                        arrImagesFond[iNombreImageFond].infobulle = $(this).attr('infobulle') || "";
                        arrImagesFond[iNombreImageFond].tailleX = $(this).attr('tailleX') || "";
                        arrImagesFond[iNombreImageFond].tailleY = $(this).attr('tailleY') || "";
                        arrImagesFond[iNombreImageFond].posX = $(this).attr('posX') || "right";
                        arrImagesFond[iNombreImageFond].posY = $(this).attr('posY') || "bottom";
                        arrImagesFond[iNombreImageFond].offsetX = $(this).attr('offsetX') || 0;
                        arrImagesFond[iNombreImageFond].offsetY = $(this).attr('offsetY') || 0;
                        arrImagesFond[iNombreImageFond].opacite = $(this).attr('opacite') || 0;
                        arrImagesFond[iNombreImageFond].calque = parseInt($(this).attr('calque')) || 1;
                        arrImagesFond[iNombreImageFond].cible = $(this).attr('cible') || "interne";
                        arrImagesFond[iNombreImageFond].masquable = (($(this).attr('masquable') || "oui") === "oui");
                        iNombreImageFond++;
                    });
                    var XMLPlan = $(d).find('plan');
                    planAff = XMLPlan.attr('affiche') || "non";
                    bPlanAffiche = (planAff === "oui");
                    strPlanImage = XMLPlan.attr('image') || strPlanImage;
                    strPlanPosition = XMLPlan.attr('position') || strPlanPosition;
                    strPlanCouleurFond = XMLPlan.attr("couleurFond") || strPlanCouleurFond;
                    strPlanCouleurTexte = XMLPlan.attr("couleurTexte") || strPlanCouleurTexte;
                    opacitePlan = parseFloat(XMLPlan.attr("opacitePlan")) || opacitePlan;
                    planCalque = parseInt(XMLPlan.attr('planCalque')) || planCalque;

                    planLargeur = parseFloat(XMLPlan.attr('largeur')) || planLargeur;
                    planNord = parseFloat(XMLPlan.attr('nord')) || planNord;
                    strPlanBoussolePosition = XMLPlan.attr('boussolePosition') || strPlanBoussolePosition;
                    iPlanBoussoleX = parseInt(XMLPlan.attr('boussoleX')) || iPlanBoussoleX;
                    iPlanBoussoleY = parseInt(XMLPlan.attr('boussoleY')) || iPlanBoussoleY;
                    afficheRadar = XMLPlan.attr('radarAffiche') || "non";
                    iRadarTaille = parseInt(XMLPlan.attr('radarTaille')) || iRadarTaille;
                    radarOpacite = parseFloat(XMLPlan.attr('radarOpacite')) || radarOpacite;
                    strRadarCouleurFond = XMLPlan.attr('radarCouleurFond') || strRadarCouleurFond;
                    strRadarCouleurLigne = XMLPlan.attr('radarCouleurLigne') || strRadarCouleurLigne;
                    bRadarAffiche = (afficheRadar === "oui");
                    /*
                     *   points du plan
                     */
                    i = 0;
                    $(d).find('pointPlan').each(function () {
                        arrPointsPlan[i] = new pointPlan();
                        arrPointsPlan[i].xml = $(this).attr('xml');
                        arrPointsPlan[i].texte = $(this).attr('texte') || "";
                        arrPointsPlan[i].positX = parseInt($(this).attr('positX')) || 0;
                        arrPointsPlan[i].positY = parseInt($(this).attr('positY')) || 0;
                        i++;
                    });
                    var XMLCarte = $(d).find('carte');
                    strCarteAff = XMLCarte.attr('affiche') || "non";
                    bCarteAffiche = (strCarteAff === "oui");
                    strCartePosition = XMLCarte.attr('position') || strCartePosition;
                    strCarteCouleurFond = XMLCarte.attr("couleurFond") || strCarteCouleurFond;
                    strCarteCouleurTexte = XMLCarte.attr("couleurTexte") || strCarteCouleurTexte;
                    opaciteCarte = parseFloat(XMLCarte.attr("opaciteCarte")) || opaciteCarte;
                    carteLargeur = parseFloat(XMLCarte.attr('largeur')) || carteLargeur;
                    carteHauteur = parseFloat(XMLCarte.attr('hauteur')) || carteHauteur;
                    iCarteZoom = parseInt(XMLCarte.attr('zoom')) || iCarteZoom;
                    carteCalque = parseInt(XMLCarte.attr('carteCalque')) || carteCalque;

                    coordCentreLong = parseFloat(XMLCarte.attr("coordCentreLong")) || coordCentreLong;
                    coordCentreLat = parseFloat(XMLCarte.attr("coordCentreLat")) || coordCentreLat;
                    strNomLayerCarte = XMLCarte.attr("nomLayer") || strNomLayerCarte;
                    strBingAPIKey = XMLCarte.attr("bingAPIKey") || strBingAPIKey;
                    strAfficheRadarCarte = XMLCarte.attr('radarCarteAffiche') || "non";
                    iRadarCarteTaille = parseInt(XMLCarte.attr('radarCarteTaille')) || iRadarCarteTaille;
                    radarCarteOpacite = parseFloat(XMLCarte.attr('radarCarteOpacite')) || radarCarteOpacite;
                    strRadarCarteCouleurFond = XMLCarte.attr('radarCarteCouleurFond') || strRadarCarteCouleurFond;
                    strRadarCarteCouleurLigne = XMLCarte.attr('radarCarteCouleurLigne') || strRadarCarteCouleurLigne;
                    radarCarteAffiche = (strAfficheRadarCarte === "oui");
                    /*
                     *   points du carte
                     */
                    i = 0;
                    $(d).find('pointCarte').each(function () {
                        arrPointsCarte[i] = new pointCarte();
                        arrPointsCarte[i].xml = $(this).attr('xml');
                        arrPointsCarte[i].html = $(this).attr('html') || "";
                        arrPointsCarte[i].image = $(this).attr('image') || "";
                        arrPointsCarte[i].positX = parseFloat($(this).attr('positX')) || 0;
                        arrPointsCarte[i].positY = parseFloat($(this).attr('positY')) || 0;
                        arrPointsCarte[i].html = arrPointsCarte[i].html.replace("&gt;", ">").replace("&lt;", "<");
                        i++;
                    });
                    iNbPointCarte = i;
                    /**
                     * Initialisation de l'interface
                     */
                    if (strTitreTaille !== "adapte")
                        if (strTitreTaille.match("[px]", "g"))
                        {
                            strTitreTailleUnite = "px";
                            iTitreTailleFenetre = parseInt(strTitreTaille);
                        }
                        else
                        {
                            strTitreTailleUnite = "%";
                            iTitreTailleFenetre = parseInt(strTitreTaille) / 100.0;
                        }
                    init(fenPanoramique);
                    creeImagesboutons();
                    /**
                     * Initialisation de l'affichage du panoramique
                     */
                    switch (strPanoType)
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
        var fenetrePanoramique = "panovisu-" + iNumPano;
        $("<div>", {id: fenetrePanoramique, class: "panovisu", style: "width : 100%;height : 100%;position: relative;"}).appendTo("#" + fenetre);
        $("<img>", {id: "charge-" + iNumPano, class: "chargement", src: "panovisu/images/chargement.gif"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "boussole-" + iNumPano, class: "boussole"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "bousImg-" + iNumPano, class: "bousImg", src: ""}).appendTo("#boussole-" + iNumPano);
        $("<img>", {id: "bousAig-" + iNumPano, class: "bousAig", src: ""}).appendTo("#boussole-" + iNumPano);
        $("#boussole-" + iNumPano).hide();
        $("<div>", {id: "marcheArret-" + iNumPano, class: "marcheArret"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "MAImg-" + iNumPano, class: "MAImg", src: "", title: chainesTraduction[strLangage].afficheMasque}).appendTo("#marcheArret-" + iNumPano);
        $("#marcheArret-" + iNumPano).hide();
        $("<div>", {id: "reseauxSociaux-" + iNumPano, class: "reseauxSociaux"}).appendTo("#" + fenetrePanoramique);
        $("<img>", {id: "RSTW-" + iNumPano, class: "RS reseauSocial-twitter", src: "", title: "twitter"}).appendTo("#reseauxSociaux-" + iNumPano);
        $("<img>", {id: "RSGO-" + iNumPano, class: "RS reseauSocial-google", src: "", title: "google"}).appendTo("#reseauxSociaux-" + iNumPano);
        $("<img>", {id: "RSFB-" + iNumPano, class: "RS reseauSocial-fb", src: "", title: "facebook"}).appendTo("#reseauxSociaux-" + iNumPano);
        $("<a>", {id: "lienEmail" + iNumPano, class: "RS reseauSocial-email", href: ""}).appendTo("#reseauxSociaux-" + iNumPano);
        $("<img>", {id: "RSEM-" + iNumPano, src: "", title: "email"}).appendTo("#lienEmail" + iNumPano);
        $("#reseauxSociaux-" + iNumPano).hide();
        $("<div>", {id: "info-" + iNumPano, class: "info"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "infoBulle-" + iNumPano, class: "infoBulle", style: "display:none;position: absolute;"}).appendTo("#" + fenetrePanoramique);
        //$("#infoBulle-" + iNumPano).html("infoBulle");
        /**
         * création du conteneur du panoramique
         */
        $("<div>", {id: "pano1-" + iNumPano, class: "pano1"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "container-" + iNumPano, class: "container", style: "z-index:10000"}).appendTo("#pano1-" + iNumPano);
        $("<div>", {id: "divVignettes-" + iNumPano, class: "vignettes"}).appendTo("#pano1-" + iNumPano);
        $("<div>", {id: "titreVignettes-" + iNumPano, class: "titreVignettes"}).appendTo("#pano1-" + iNumPano);
        $("#titreVignettes-" + iNumPano).html(chainesTraduction[strLangage].vignettes);
        $("#divVignettes-" + iNumPano).hide();
        $("#titreVignettes-" + iNumPano).hide();
        $("<div>", {id: "divImage-" + iNumPano, class: "vignettes"}).appendTo("#pano1-" + iNumPano);
        $("#divImage-" + iNumPano).hide();
        $("<div>", {id: "divHTML-" + iNumPano, class: "vignettes"}).appendTo("#pano1-" + iNumPano);
        $("#divHTML-" + iNumPano).hide();
        $("<div>", {id: "divPrecedent-" + iNumPano, class: "precedent", title: chainesTraduction[strLangage].panoPrecedent}).appendTo("#pano1-" + iNumPano);
        $("<div>", {id: "divSuivant-" + iNumPano, class: "suivant", title: chainesTraduction[strLangage].panoSuivant}).appendTo("#pano1-" + iNumPano);
        $("#divPrecedent-" + iNumPano).hide();
        $("#divSuivant-" + iNumPano).hide();
        $("<div>", {id: "btnVisiteAuto-" + iNumPano, class: "btnVisiteAuto"}).appendTo("#" + fenetrePanoramique);
        bPlanRentre = false;
        bCarteRentre = false;
        bVignettesRentre = true;
        /**
         * Création de la barre de boutons
         * et des trois éléments de barre 
         *              - déplacement, 
         *              - zoom 
         *              - outils (plein écran, mode souris et autorotation)
         */
        $("<div>", {id: "telec-" + iNumPano, class: "telecommande"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "comboMenu-" + iNumPano, class: "comboMenu"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "boutons-" + iNumPano, class: "boutons"}).appendTo("#" + fenetrePanoramique);
        $("<div>", {id: "barre-" + iNumPano, class: "barre"}).appendTo("#boutons-" + iNumPano);
        $("<div>", {id: "deplacement-" + iNumPano, class: "deplacement"}).appendTo("#barre-" + iNumPano);
        $("<div>", {id: "zoom-" + iNumPano, class: "zoom"}).appendTo("#barre-" + iNumPano);
        $("<div>", {id: "outils-" + iNumPano, class: "outils"}).appendTo("#barre-" + iNumPano);
        $("<div>", {id: "plan-" + iNumPano, class: "plan"}).appendTo("#pano1-" + iNumPano);
        $("<div>", {id: "planTitre-" + iNumPano, class: "planTitre"}).appendTo("#pano1-" + iNumPano);
        $("#plan-" + iNumPano).hide();
        $("#planTitre-" + iNumPano).hide();
        $("#planTitre-" + iNumPano).css("transform", "rotate(90deg)");
        $("<div>", {id: "carte-" + iNumPano, class: "carte"}).appendTo("#pano1-" + iNumPano);
        $("<div>", {id: "carteTitre-" + iNumPano, class: "carteTitre"}).appendTo("#pano1-" + iNumPano);
        $("#carte-" + iNumPano).hide();
        $("#carteTitre-" + iNumPano).hide();
        $("#carteTitre-" + iNumPano).css("transform", "rotate(90deg)");
        /**
         * On rajoute enfin les boutons & les fenêtres d'Information/Aide.
         */
        creeBarreNavigation();
        creeInfo(fenetrePanoramique);
        creeAide(fenetrePanoramique);
        /**
         * Création des racourcis vers les différentes fenêtres
         */
        container = $("#container-" + iNumPano);
        pano = $("#" + fenetrePanoramique);
        pano1 = $("#pano1-" + iNumPano);


        conteneur = document.getElementById("container-" + iNumPano);

        $(document).on("touchstart", "#marcheArret-" + iNumPano, function (evenement) {
            evenement.preventDefault();
            toggleElements();
        });

        conteneur.addEventListener('touchstart', function (evenement) {
            evenement.preventDefault();
            if (evenement.targetTouches.length === 1) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });

                }
                isUserInteracting = true;
                onPointerDownPointerX = evenement.touches[0].pageX;
                onPointerDownPointerY = evenement.touches[0].pageY;

                if (iMode === 1) {
                    deltaX = 0;
                    deltaY = 0;
                    timer = requestAnimFrame(deplaceMode2);
                }
                else
                {
                    onPointerDownLon = longitude;
                    onPointerDownLat = latitude;

                }

            }
            if (evenement.targetTouches.length === 2) {
                if (bAfficheInfo)
                {
                    $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                        $(this).css({display: "none"});
                        bAfficheInfo = false;
                    });

                }
                onPointerDownPointerX1 = evenement.touches[0].pageX;
                onPointerDownPointerY1 = evenement.touches[0].pageY;
                onPointerDownPointerX2 = evenement.touches[1].pageX;
                onPointerDownPointerY2 = evenement.touches[1].pageY;
                dx = onPointerDownPointerX1 - onPointerDownPointerX2;
                dy = onPointerDownPointerY1 - onPointerDownPointerY2;
                distance = dx * dx + dy * dy;
                isZoom = true;
                onPointerZoom = fov;
            }

        }, false);

        conteneur.addEventListener('touchmove', function (evenement) {
            evenement.preventDefault();

            if (isUserInteracting) {
                if (evenement.targetTouches.length === 2) {
                    isUserInteracting = false;
                    onPointerDownPointerX1 = evenement.touches[0].pageX;
                    onPointerDownPointerY1 = evenement.touches[0].pageY;
                    onPointerDownPointerX2 = evenement.touches[1].pageX;
                    onPointerDownPointerY2 = evenement.touches[1].pageY;
                    dx = onPointerDownPointerX1 - onPointerDownPointerX2;
                    dy = onPointerDownPointerY1 - onPointerDownPointerY2;
                    distance = dx * dx + dy * dy;
                    isZoom = true;
                    onPointerZoom = fov;
                }
                else {
                    if (iMode === 1) {
                        deltaX = -(onPointerDownPointerX - evenement.touches[0].pageX) * 0.01;
                        deltaY = (onPointerDownPointerY - evenement.touches[0].pageY) * 0.01;
                    }
                    else {

                        mouseMove = true;
                        longitude = (onPointerDownPointerX - evenement.touches[0].pageX) * 0.1 + onPointerDownLon;
                        latitude = (evenement.touches[0].pageY - onPointerDownPointerY) * 0.1 + onPointerDownLat;
                        affiche();
                    }
                }
            }
            else if (isZoom) {
                onPointerDownPointerXX1 = evenement.touches[0].pageX;
                onPointerDownPointerYY1 = evenement.touches[0].pageY;
                onPointerDownPointerXX2 = evenement.touches[1].pageX;
                onPointerDownPointerYY2 = evenement.touches[1].pageY;
                dx = onPointerDownPointerXX1 - onPointerDownPointerXX2;
                dy = onPointerDownPointerYY1 - onPointerDownPointerYY2;
                distance2 = dx * dx + dy * dy;
                dZ = distance / distance2;
                fov = onPointerZoom * Math.pow(dZ, 0.25);
                isZoom = true;
                zoom();
            }

        }, false);

        conteneur.addEventListener('touchend', function (evenement) {
            if (iMode === 1) {
                ddx = deltaX;
                ddy = deltaY;
                dx = deltaX / 4.0;
                dy = deltaY / 4.0;
                timer = requestAnimFrame(ralenti);
            }
            isUserInteracting = false;
            isZoom = false;
        }, false);

    }

    function telecommandeNavigation() {
        if (!bDejaCharge) {
            $("#telec-" + iNumPano).html("");
            if (bTelecommande) {
                img = new Image();
                img.src = "panovisu/images/telecommande/telecommande.png";
                img.onload = function () {
                    $("#telec-" + iNumPano).css({
                        width: this.width,
                        height: this.height
                    });
                    $("<img>", {
                        id: "telecommande-" + iNumPano,
                        class: "imgTelec",
                        src: this.src,
                        border: "0",
                        width: this.width,
                        height: this.height,
                        usemap: "#telecommandeMap-" + iNumPano,
                        alt: ""}).appendTo("#telec-" + iNumPano);
                    var tailleBtnTelec = telecommandeTailleBouton;
                    var decalage = 0;
                    var zoneActive = 1;
                    if (strTelecommandeInfo === "oui") {
                        if (arrZonesTelecommande[zoneActive]) {
                            xm1 = arrZonesTelecommande[zoneActive].centerX - tailleBtnTelec / 2 - decalage;
                            ym1 = arrZonesTelecommande[zoneActive].centerY - tailleBtnTelec / 2 - decalage;
                            $("<div>", {
                                id: "telInfo-" + iNumPano,
                                class: "imgTelecInfo",
                                style: "left : " + xm1 + "px;top : " + ym1 + "px;"
                            }).appendTo("#telec-" + iNumPano);
                            $("<img>", {
                                src: "panovisu/images/telecommande/info.png",
                                border: "0",
                                width: tailleBtnTelec,
                                alt: "",
                                title: chainesTraduction[strLangage].info
                            }).appendTo("#telInfo-" + iNumPano);
                        }
                        zoneActive += 1;
                    }
                    if (strTelecommandeAide === "oui") {
                        if (arrZonesTelecommande[zoneActive]) {
                            xm1 = arrZonesTelecommande[zoneActive].centerX - tailleBtnTelec / 2 - decalage;
                            ym1 = arrZonesTelecommande[zoneActive].centerY - tailleBtnTelec / 2 - decalage;
                            $("<div>", {
                                id: "telAide-" + iNumPano,
                                class: "imgTelecInfo",
                                style: "left : " + xm1 + "px;top : " + ym1 + "px;"
                            }).appendTo("#telec-" + iNumPano);
                            $("<img>", {
                                src: "panovisu/images/telecommande/aide.png",
                                border: "0",
                                width: tailleBtnTelec,
                                alt: "",
                                title: chainesTraduction[strLangage].aide
                            }).appendTo("#telAide-" + iNumPano);
                        }
                        zoneActive += 1;
                    }


                    if (strTelecommandeFS === "oui") {
                        if (arrZonesTelecommande[zoneActive]) {
                            xm1 = arrZonesTelecommande[zoneActive].centerX - tailleBtnTelec / 2 - decalage;
                            ym1 = arrZonesTelecommande[zoneActive].centerY - tailleBtnTelec / 2 - decalage;
                            $("<div>", {
                                id: "telFS-" + iNumPano,
                                class: "imgTelecFS",
                                style: "left : " + xm1 + "px;top : " + ym1 + "px;"
                            }).appendTo("#telec-" + iNumPano);
                            $("<img>", {
                                src: "panovisu/images/telecommande/fs.png",
                                border: "0",
                                width: tailleBtnTelec,
                                alt: "",
                                title: chainesTraduction[strLangage].pleinEcran
                            }).appendTo("#telFS-" + iNumPano);
                        }
                        zoneActive += 1;
                    }


                    if (strTelecommandeAutorotation === "oui") {
                        if (arrZonesTelecommande[zoneActive]) {

                            xm1 = arrZonesTelecommande[zoneActive].centerX - tailleBtnTelec / 2 - decalage;
                            ym1 = arrZonesTelecommande[zoneActive].centerY - tailleBtnTelec / 2 - decalage;
                            $("<div>", {
                                id: "telRotation-" + iNumPano,
                                class: "imgTelecFS",
                                style: "left : " + xm1 + "px;top : " + ym1 + "px;"
                            }).appendTo("#telec-" + iNumPano);
                            $("<img>", {
                                src: "panovisu/images/telecommande/rotation.png",
                                border: "0",
                                width: tailleBtnTelec,
                                alt: "",
                                title: chainesTraduction[strLangage].autorotation
                            }).appendTo("#telRotation-" + iNumPano);
                        }

                        zoneActive += 1;
                    }

                    if (strTelecommandeSouris === "oui") {
                        if (arrZonesTelecommande[zoneActive]) {
                            xm1 = arrZonesTelecommande[zoneActive].centerX - tailleBtnTelec / 2 - decalage;
                            ym1 = arrZonesTelecommande[zoneActive].centerY - tailleBtnTelec / 2 - decalage;
                            $("<div>", {
                                id: "telSouris-" + iNumPano,
                                class: "imgTelecFS",
                                style: "left : " + xm1 + "px;top : " + ym1 + "px;"
                            }).appendTo("#telec-" + iNumPano);
                            $("<img>", {
                                src: "panovisu/images/telecommande/souris.png",
                                border: "0",
                                width: tailleBtnTelec,
                                alt: "",
                                title: chainesTraduction[strLangage].souris
                            }).appendTo("#telSouris-" + iNumPano);
                        }
                        zoneActive += 1;
                    }


                    $("<map>", {
                        id: "telecommandeMap-" + iNumPano,
                        name: "telecommandeMap-" + iNumPano
                    }).appendTo("#telec-" + iNumPano);
                    for (i = 0; i < arrBoutonsTelecommande.length; i++) {
                        switch (arrBoutonsTelecommande[i].id) {
                            case "telUp":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].haut;
                                break;
                            case "telDown":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].bas;
                                break;
                            case "telRight":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].droite;
                                break;
                            case "telLeft":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].gauche;
                                break;
                            case "telZoomPlus":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].zoomPlus;
                                break;
                            case "telZoomMoins":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].zoomMoins;
                                break;
                            case "telInfo":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].info;
                                break;
                            case "telAide":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].aide;
                                break;
                            case "telFS":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].pleinEcran;
                                break;
                            case "telRotation":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].autorotation;
                                break;
                            case "telSouris":
                                arrBoutonsTelecommande[i].title = chainesTraduction[strLangage].souris;
                                break;
                            case "telLien-1":
                                arrBoutonsTelecommande[i].title = strLien1BarrePersonnalisee;
                                break;
                            case "telLien-2":
                                arrBoutonsTelecommande[i].title = strLien2BarrePersonnalisee;
                                break;
                        }
                        $("<area>", {
                            id: arrBoutonsTelecommande[i].id + "-" + iNumPano,
                            class: "clicTelec",
                            alt: "",
                            title: arrBoutonsTelecommande[i].title,
                            shape: arrBoutonsTelecommande[i].shape,
                            coords: arrBoutonsTelecommande[i].coords,
                            style: "outline:none;",
                            target: "_self"}).appendTo("#telecommandeMap-" + iNumPano);
                    }
                    trX = "50%";
                    trY = "50%";
                    switch (strTelecommandePositionX) {
                        case "left":
                            trX = "0%";
                            break;
                        case "right":
                            trX = "100%";
                            break;
                    }
                    switch (strTelecommandePositionY) {
                        case "top":
                            trY = "0%";
                            break;
                        case "bottom":
                            trY = "100%";
                            break;
                    }

                    $("#telec-" + iNumPano).css({
                        transformOrigin: trX + " " + trY,
                        transform: "scale(" + telecommandeTaille / 100.0 + "," + telecommandeTaille / 100.0 + ")"
                    });
                    if (strTelecommandePositionX === "center") {
                        $("#telec-" + iNumPano).css("left", (pano.width() - $("#telec-" + iNumPano).width()) / 2.0 + telecommandeDX + "px");
                    }
                };
            }
        }
    }


    function comboMenuAffiche() {
        $("#comboMenu-" + iNumPano).html("");
        if (bComboMenuAffiche) {
            $("#comboMenu-" + iNumPano).css(strComboMenuPositionX, comboMenuDX + "px");
            $("#comboMenu-" + iNumPano).css(strComboMenuPositionY, comboMenuDY + "px");
            $("<select>", {
                id: "cbMenu-" + iNumPano,
                class: "cbMenu",
                style: "left : 0px;width : 300px;"
            }).appendTo("#comboMenu-" + iNumPano);
            arrComboMenuPano.forEach(function (element) {
                if (element.select === "selected") {
                    attrib = "selected";
                }
                else {
                    attrib = "not-selected";
                }
                if (element.image !== "") {
                    $("#cbMenu-" + iNumPano).append($("<option>", {
                        value: element.xml
                    }).attr("data-image", element.image)
                            .attr("data-description", element.sousTitre)
                            .attr(attrib, element.select)
                            .text(element.titre));
                }
                else {
                    $("#cbMenu-" + iNumPano).append($("<option>", {
                        value: element.xml
                    }).attr("data-description", element.sousTitre)
                            .attr(attrib, element.select)
                            .text(element.titre));
                }
            });
            $("#cbMenu-" + iNumPano).msDropdown({visibleRows: 4});
            $("#cbMenu-" + iNumPano).on('change', function () {
                var nomPanoXML = this.value;
                //$("#container-" + iNumPano).fadeOut(500, function () {
                rechargePano(nomPanoXML);
                //});
            });
        }
    }



    function rechargePano(xmlFile) {
        bDejaCharge = true;
        clearInterval(timers);
//        if (!bReloaded) {
//            longitude = 0;
//            latitude = 0;
//            fov = 50;
//        }
        bReloaded = true;
        enleveHS();
        arrHotSpot = new Array();
        arrPointsInteret = new Array();
        numHotspot = 0;
        $("#divVignettes-" + iNumPano).html("");
        $("#infoBulle-" + iNumPano).html("");
        $("#infoBulle-" + iNumPano).remove();
        chargeXML(xmlFile);
    }

    /**
     * intégration du panoramique 
     * 
     * @param {type} contexte
     * @returns {undefined}
     */
    this.initialisePano = function (contexte)
    {
        var defaut = {
            langue: "fr_FR",
            xml: 'xml/panovisu.xml',
            fenX: "75%",
            fenY: "80%",
            panoramique: "panovisu",
            minFOV: 25,
            maxFOV: 140
        };
        elementsVisibles = true;
        contexte = $.extend(defaut, contexte);
        fenPanoramique = contexte.panoramique;
        strLangage = contexte.langue;
        $("#" + fenPanoramique).css("overflow", "hidden");
        maxFOV = contexte.maxFOV;
        minFOV = contexte.minFOV;
        var fenetre = fenPanoramique;
        $(fenetre).css({overflow: "hidden"});
        creeContexte(fenetre);
        xmlFile = GET["xml"] || contexte.xml;
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
            iMode = 0;
        /**
         * lecture du fichier XML
         */
        chargeXML(xmlFile);
    };
    /*
     *  Fonctions de gestion Openlayers
     *  
     *  Cartographie
     *  
     */

    /**
     * 
     * @param {type} centreX
     * @param {type} centreY
     * @param {type} rayon
     * @param {type} angleDebut
     * @param {type} angleFin
     * @param {type} secteur
     * @param {type} couleurFond
     * @param {type} couleurLigne
     * @param {type} opaciteFond
     * @returns {unresolved}
     */
    function traceArc(centreX, centreY, rayon, angleDebut, angleFin, secteur, couleurFond, couleurLigne, opaciteFond)
    {
        var epaisseur = (secteur) ? 1 : 3;
        layerRadar.styleMap = new OpenLayers.StyleMap({'default':
                    {
                        'strokeWidth': 1,
                        'fillColor': couleurFond,
                        'fillOpacity': opaciteFond,
                        'strokeColor': couleurLigne
                    }
        });

        var segments = Math.abs(angleFin - angleDebut) * 2;
        var pointCercle = [];
        if (secteur) {
            pointCercle.push(new OpenLayers.Geometry.Point(centreX, centreY));
        }
        var dAngle = segments + 1;
        for (var i = 0; i < dAngle; i++)
        {
            var Angle = angleDebut - (angleDebut - angleFin) * i / (dAngle - 1);
            var x = centreX + rayon * Math.cos(Angle * Math.PI / 180);
            var y = centreY + rayon * Math.sin(Angle * Math.PI / 180);

            var point = new OpenLayers.Geometry.Point(x, y);
            pointCercle.push(point);
        }
        var arcCercle = null;
        if (secteur) {
            var linearRing = new OpenLayers.Geometry.LinearRing(pointCercle);
            arcCercle = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon(linearRing));
        }
        else {
            arcCercle = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(pointCercle));
        }
        return(arcCercle);
    }

    /**
     * 
     * @param {type} longitude
     * @param {type} latitude
     * @param {type} zoom
     * @returns {undefined}
     */
    function allerCoordonnees(longitude, latitude, zoom) {
        var x = longitude2Mercator(longitude);
        var y = latitude2Mercator(latitude);
        map.setCenter(new OpenLayers.LonLat(x, y), zoom);
    }
    /**
     * 
     * @param {type} zoom
     * @returns {undefined}
     */
    function choixZoom(zoom) {
        map.setCenter(map.getCenter(), zoom);
    }
    /**
     * 
     * @param {type} degDecimal
     * @returns {String}
     */
    function toDMS(degDecimal) {
        var signe = "";
        if (degDecimal < 0) {
            signe = "-";
            degDecimal = Math.abs(degDecimal);
        }
        var degres = Math.round(degDecimal - 0.5);
        var min = (degDecimal - degres) * 60;
        var minutes = Math.round((degDecimal - degres) * 60 - 0.5);
        var secondes = Math.round(((min - minutes) * 60) * 100) / 100;
        return signe + degres + "°" + minutes + "'" + secondes + "\"";
    }
    /**
     * 
     * @param {type} lon
     * @returns {Number}
     */
    function longitude2Mercator(lon) {
        return 20037508.34 * lon / 180;
    }
    /**
     * 
     * @param {type} lat
     * @returns {Number}
     */
    function latitude2Mercator(lat) {
        var PI = 3.14159265358979323846;
        lat = Math.log(Math.tan((90 + lat) * PI / 360)) / (PI / 180);
        return 20037508.34 * lat / 180;
    }
    /**
     * 
     * @param {type} lon
     * @returns {Number}
     */
    function mercator2Longitude(lon) {
        return lon * 180 / 20037508.34;
    }
    /**
     * 
     * @param {type} lat
     * @returns {Number}
     */
    function mercator2Latitude(lat) {
        return Math.atan(Math.exp(lat * Math.PI / 20037508.34)) * 360 / Math.PI - 90;
    }


    /**
     * 
     * @param {type} numeroMarqueur
     * @param {type} longitude
     * @param {type} latitude
     * @param {type} contenuPopup
     * @returns {undefined}
     */
    function ajouteMarqueur(numeroMarqueur, longitude, latitude, contenuPopup) {
        if (numeroMarqueur > iMaxMarqueur) {
            iMaxMarqueur = numeroMarqueur;
        }
        var popup;
        var layer = layerMarqueurs;
        var ll = new OpenLayers.LonLat(longitude2Mercator(longitude), latitude2Mercator(latitude));
        var size = new OpenLayers.Size(24, 27);
        var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
        var icon;
        if (arrPointsCarte[numeroMarqueur].xml === "actif") {
            icon = new OpenLayers.Icon('./panovisu/images/marqueursOL/marqueurActif.png', size, offset);
            map.setCenter(ll);
        } else {
            icon = new OpenLayers.Icon('./panovisu/images/marqueursOL/marqueur.png', size, offset);
        }
        var marker = new OpenLayers.Marker(ll, icon);
        if (arrPointsCarte[numeroMarqueur].xml !== "actif") {
            marker.setOpacity(0.8);
        }
        else {
            marker.setOpacity(1.0);
        }
        marker.events.register("mouseover", marker,
                function () {
                    if (arrPointsCarte[numeroMarqueur].xml !== "actif") {
                        popup = new OpenLayers.Popup(
                                "marq" + numeroMarqueur,
                                new OpenLayers.LonLat(longitude2Mercator(longitude), latitude2Mercator(latitude)),
                                new OpenLayers.Size(250, 150),
                                contenuPopup,
                                true
                                );
                        map.addPopup(popup);
                    }
                    this.setOpacity(1);
                }
        );
        marker.events.register("mouseout", marker,
                function () {
                    if (arrPointsCarte[numeroMarqueur].xml !== "actif") {
                        map.removePopup(popup);
                    }
                    if (arrPointsCarte[numeroMarqueur].xml !== "actif") {
                        marker.setOpacity(0.8);
                    }
                    else {
                        marker.setOpacity(1.0);
                    }
                }
        );
        marker.events.register("mousedown", marker,
                function () {
                    if (arrPointsCarte[numeroMarqueur].xml !== "actif") {
                        map.removePopup(popup);
                        //$("#container-" + iNumPano).fadeOut(200, function () {
                        rechargePano(arrPointsCarte[numeroMarqueur].xml);
                        //});
                    }
                }
        );
        layer.addMarker(marker);
        arrMarqueurs[numeroMarqueur] = marker;
    }

    /**
     * 
     * @returns {undefined}
     */
    function enleveMarqueurs() {
        for (var i = 0; i <= iMaxMarqueur; i++) {
            var marker = arrMarqueurs[i];
            if (marker !== null)
            {
                layerMarqueurs.removeMarker(marker);
            }
        }
    }

    /**
     * 
     * @param {type} pId
     * @returns {undefined}
     */
    function enleveMarqueur(pId) {
        var marker = arrMarqueurs[pId];
        if (marker !== null)
        {
            layerMarqueurs.removeMarker(marker);
        }
    }
    /**
     * 
     * @returns {String}
     */
    function getCoordonnees() {
        lon = Math.round(mercator2Longitude(this.map.getCenter().lon) * 1000000) / 1000000;
        lat = Math.round(mercator2Latitude(this.map.getCenter().lat) * 1000000) / 1000000;
        return lon + ";" + lat;
    }

    /**
     * 
     * @returns {unresolved}
     */
    function getNomsLayers() {
        var strNoms = "";
        for (var i = 0; i < map.getNumLayers(); i++) {
            if (map.layers[i].isBaseLayer) {
                if (map.layers[i].name === map.baseLayer.name) {
                    strNoms += "*" + map.layers[i].name + "|";
                }
                else {
                    strNoms += map.layers[i].name + "|";
                }
            }
        }
        return strNoms;
    }

    /**
     * 
     * @returns {layerRadar.rayon}
     */
    function getRayonRadar() {
        return layerRadar.rayon;
    }
    /**
     * 
     * @param {type} rayonRadar
     * @returns {layerRadar.rayonrayonRadar}
     */
    function setRayonRadar(rayonRadar) {
        return layerRadar.rayon = rayonRadar;
    }
    /**
     * 
     * @returns {undefined}
     */
    function retireRadar() {
        layerRadar.destroyFeatures();
        bRadar = false;
    }
    /**
     * 
     * @param {type} strLayer
     * @returns {undefined}
     */
    function changeLayer(strLayer) {
        for (var i = 0; i < map.getNumLayers(); i++) {
            if (map.layers[i].name === strLayer) {
                map.setBaseLayer(map.layers[i]);
            }
        }
    }
    var resolutions = OpenLayers.Layer.Bing.prototype.serverResolutions.slice(10, 20);
    /**
     * 
     * @param {type} bingApiKey
     * @returns {undefined}
     */
    function setBingApiKey(bingApiKey) {
        if (bingApiKey !== "") {
            if (bingMap === null) {
                bingMap = new OpenLayers.Layer.Bing({
                    name: "Bing",
                    type: "Road",
                    key: bingApiKey,
                    zoomOffset: 10,
                    resolutions: resolutions,
                    numZoomLevels: 19,
                    transitionEffect: "resize"

                });
                bingHybride = new OpenLayers.Layer.Bing({
                    name: "Bing Hybride",
                    type: "AerialWithLabels",
                    key: bingApiKey,
                    zoomOffset: 10,
                    resolutions: resolutions,
                    numZoomLevels: 19,
                    transitionEffect: "resize"

                });
                bingSatellite = new OpenLayers.Layer.Bing({
                    name: "Bing Satellite",
                    type: "Aerial",
                    key: bingApiKey,
                    zoomOffset: 10,
                    resolutions: resolutions,
                    numZoomLevels: 19,
                    transitionEffect: "resize"

                });
                map.addLayers([bingMap, bingHybride, bingSatellite]);
            }
        }
    }


    /**
     * 
     * @returns {undefined}
     */
    function affichageRadar() {
        layerRadar.destroyFeatures();
        var rayon = radar.pcRayon / 100.0 * Math.min(map.getExtent().right - map.getExtent().left, map.getExtent().top - map.getExtent().bottom) / 2.0;
        var angleDepart = radar.angleradar - radar.angleOuverture / 2;
        var angleArrivee = radar.angleradar + radar.angleOuverture / 2;
        var centreX = longitude2Mercator(radar.longitude);
        var centreY = latitude2Mercator(radar.latitude);
        var arc = traceArc(centreX, centreY, rayon, angleDepart, angleArrivee, true, radar.couleurLigne, radar.couleurFond, radar.opaciteFond);
        var origine = new OpenLayers.Geometry.Point(centreX, centreY);
        var style = {
            strokeColor: "#999999",
            strokeOpacity: 1,
            fillColor: "#555555",
            strokeWidth: 1,
            pointRadius: 5,
            pointerEvents: "visiblePainted"
        };
        var pointCentre = new OpenLayers.Feature.Vector(origine, null, style);
        layerRadar.addFeatures([arc, pointCentre]);

    }


    /**
     * 
     * @param {type} idCarte
     * @returns {undefined}
     */
    function dessineCarte(idCarte) {
        radar = new defRadar();
        OpenLayers.Lang.setCode('fr');
        map = new OpenLayers.Map(idCarte,
                {
                    projection: new OpenLayers.Projection("EPSG:900913"),
                    displayProjection: new OpenLayers.Projection("EPSG:4326"),
                    controls: [
                        new OpenLayers.Control.Navigation(),
                        new OpenLayers.Control.PanZoomBar(),
                        new OpenLayers.Control.ScaleLine(),
                        new OpenLayers.Control.MousePosition(),
                        new OpenLayers.Control.LayerSwitcher()
                    ],
                    maxExtent:
                            new OpenLayers.Bounds(-20037508.34, -20037508.34,
                                    20037508.34, 20037508.34),
                    maxResolution: 156543,
                    lang: "fr",
                    units: 'meters'
                });

        openStreetMap = new OpenLayers.Layer.OSM.Mapnik("OpenStreetMap", {
            zoomOffset: 10, resolutions: resolutions,
            numZoomLevels: 20,
            transitionEffect: "resize"
        });
        layerMarqueurs = new OpenLayers.Layer.Markers("Adresse", {
            projection: new OpenLayers.Projection("EPSG:900913"),
            visibility: true,
            displayInLayerSwitcher: false
        });
        layerRadar = creeRadar("none", "red", 0.4, 35);
        map.addLayers([openStreetMap, layerRadar, layerMarqueurs]);
        map.autoUpdateSize = false;
        googleMap = new OpenLayers.Layer.Google("Google", {
            minZoomLevel: 10,
            maxZoomLevel: 20,
            transitionEffect: "resize"
        });
        googlePhysique = new OpenLayers.Layer.Google("Google Physique", {
            type: google.maps.MapTypeId.TERRAIN,
            minZoomLevel: 10,
            maxZoomLevel: 16,
            transitionEffect: "resize"

        });
        googleHybride = new OpenLayers.Layer.Google("Google Hybride", {
            type: google.maps.MapTypeId.HYBRID,
            minZoomLevel: 10,
            maxZoomLevel: 20,
            transitionEffect: "resize"

        });
        googleSatellite = new OpenLayers.Layer.Google("Google Satellite", {
            type: google.maps.MapTypeId.SATELLITE,
            minZoomLevel: 10,
            maxZoomLevel: 20,
            transitionEffect: "resize"

        });
        map.addLayers([googlePhysique, googleMap, googleHybride, googleSatellite]);

        map.events.register('move', map, function () {
            if (bRadar) {
                affichageRadar();
            }
        });
        map.events.register('zoomend', map, function () {
            if (bRadar) {
                affichageRadar();
            }
        });
    }

    /**
     * 
     * @param {type} longitude
     * @param {type} latitude
     * @param {type} angleOuverture
     * @param {type} angleradar
     * @param {type} pcRayon
     * @param {type} couleurLigne
     * @param {type} couleurFond
     * @param {type} opaciteFond
     * @returns {undefined}
     */
    function afficheRadars(longitude, latitude, angleOuverture, angleradar, pcRayon, couleurLigne, couleurFond, opaciteFond) {
        couleurLigne = couleurLigne || "yellow";
        couleurFond = couleurFond || "green";
        opaciteFond = opaciteFond || 0.2;
        radar.longitude = longitude;
        radar.latitude = latitude;
        radar.angleOuverture = angleOuverture;
        radar.angleradar = angleradar;
        radar.pcRayon = pcRayon;
        radar.couleurLigne = couleurLigne;
        radar.couleurFond = couleurFond;
        radar.opaciteFond = opaciteFond;
        affichageRadar();
        bRadar = true;
    }

    /**
     * 
     * @param {type} angleRadar
     * @param {type} angleOuverture
     * @returns {undefined}
     */
    function radarPosition(angleRadar, angleOuverture) {
        if (bRadar) {
            radar.angleOuverture = angleOuverture;
            radar.angleradar = angleRadar;
            affichageRadar();
        }
    }

    /**
     * 
     * @param {type} couleurTrait
     * @param {type} couleurFond
     * @param {type} opacite
     * @param {type} rayon
     * @returns {unresolved}
     */
    function creeRadar(couleurTrait, couleurFond, opacite, rayon) {
        var vector = new OpenLayers.Layer.Vector("Radar", {'displayInLayerSwitcher': false});
        vector.styleMap = new OpenLayers.StyleMap({'default':
                    {
                        'strokeWidth': 1,
                        'strokeColor': couleurTrait,
                        'fillColor': couleurFond,
                        'fillOpacity': opacite
                    }
        });
        vector.rayon = rayon;
        return vector;
    }

    /**
     * 
     * @param {type} adresse
     * @param {type} zoom
     * @returns {undefined}
     */
    function chercheAdresse(adresse, zoom) {
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({'address': adresse},
        function (results, status) {
            if (status !== google.maps.GeocoderStatus.OK) {
                javafx.adresseInconnue("\"" + adresse + "\" non trouvée\n" + status);
            } else {
                javafx.adresseTrouvee(results[0].geometry.location.lng(), results[0].geometry.location.lat());
                map.setCenter(
                        new OpenLayers.LonLat(
                                longitude2Mercator(results[0].geometry.location.lng()),
                                latitude2Mercator(results[0].geometry.location.lat())
                                ),
                        zoom);
            }
        });
    }

    /**
     * Definition des actionneurs extérieurs
     * 
     */

    /**
     * 
     * @param {type} timestamp
     * @returns {undefined}
     */
    function allerFOV2(timestamp) {
        if (start === null)
            start = timestamp;
        var deltaT = (timestamp - start) / 1000.0;
        nombreIter++;
        fov = fovInit + deltaFOV * deltaT / seconds;
        zoom();
        if (deltaT < seconds && !arreteMouvement)
            requestAnimFrame(allerFOV2);
    }


    /**
     * 
     * @param {type} fovFinale
     * @param {type} secondes
     * @returns {undefined}
     */
    this.allerFOV = function (fovFinale, secondes) {
        arreteMouvement = false;
        start = null;
        seconds = secondes || 2;
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(200, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }

        fovInit = fov;
        deltaFOV = (fovFinale - fov);

        requestAnimFrame(allerFOV2);

    };

    /**
     * 
     * @param {type} champVision
     * @returns {undefined}
     */
    this.setFOV = function (champVision) {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        fov = champVision;
        zoom();
    };

    /**
     * 
     * @returns {unresolved}
     */
    this.getFOV = function () {
        return fov;
    };

    /**
     * Permet de definir de l'exterieur la longitude
     * 
     * @param {type} longit
     * @returns {undefined}
     */
    this.setLongitude = function (longit) {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        longitude = longit;
        affiche();
    };

    /**
     * 
     * @returns {unresolved}
     */
    this.getLongitude = function () {
        return longitude;
    };

    /**
     * 
     * @param {type} latit
     * @returns {undefined}
     */
    this.setLatitude = function (latit) {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        latitude = latit;
        affiche();
    };

    /**
     * 
     * @returns {unresolved}
     */
    this.getLatitude = function () {
        return latitude;
    };

    /**
     * 
     * @param {type} longit
     * @param {type} latit
     * @returns {undefined}
     */
    this.setPoint = function (longit, latit) {
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(2000, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        longitude = longit;
        latitude = latit;
        affiche();
    };

    /**
     * 
     * @param {type} timestamp
     * @returns {undefined}
     */
    function allerPoint(timestamp) {
        if (start === null)
            start = timestamp;
        var deltaT = (timestamp - start) / 1000.0;
        nombreIter++;
        longitude = longitInit + deltaLongit * deltaT / seconds;
        latitude = latitInit + deltaLatit * deltaT / seconds;
        affiche();
        if (deltaT < seconds && !arreteMouvement)
            requestAnimFrame(allerPoint);
    }

    /**
     * 
     * @param {type} longit
     * @param {type} latit
     * @param {type} secondes
     * @returns {undefined}
     */
    this.allerAuPoint = function (longit, latit, secondes) {
        arreteMouvement = false;
        start = null;
        seconds = secondes || 2;
        if (bAfficheInfo)
        {
            $("#infoPanovisu-" + iNumPano).fadeOut(200, function () {
                $(this).css({display: "none"});
                bAfficheInfo = false;
            });
        }
        longitude = longitude % 360;
        if (longitude < 0)
            longitude += 360;
        longitInit = longitude;
        deltaLongit = (longit - longitude);
        latitInit = latitude;
        deltaLatit = (latit - latitude);

        requestAnimFrame(allerPoint);
    };
    /**
     * 
     * @param {type} xmlFile
     * @returns {undefined}
     */
    this.setPano = function (xmlFile) {
        //$("#container-" + iNumPano).fadeOut(200, function () {
        rechargePano(xmlFile);
        //});
    };
    this.stopMouvement = function () {
        arreteMouvement = true;
    };
    /**
     * 
     * @returns {unresolved}
     */
    this.getXML = function () {
        return fichierXML;
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
