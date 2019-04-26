editeurPanovisu
===============

Nouveau Version 2.0.alpha1 disponible
------------------------------
la version 1.3 de panoVisu est disponible au téléchargement.

Elle corrige quelques petits défauts (angle de FOV max, blocage lors du chargement d'images panoramiques de format incorrect) et ajoute le surlignage de la vignette courante lors de la visite. 
[Télécharger la dernière version](http://panovisu.fr/telechargement/editeurPanovisu_1_3_1.zip)

panoVisu Kézako ?
-----------------

panoVisu est un visualiseur de panoramiques et de visites virtuelles construit sur une architecture HTML5/CSS3/javaScript. Il utilise la technologie webGL pour visualiser les panoramiques/visites virtuelles. Afin de simplifier son écriture, j'ai utilisé la bibliothèque three.js. Le visualiseur utilise des fichiers de description écrits en xml pour modéliser les éléments de la scène. Il permet l'ajout de nombreux éléments :

    
    panoramiques sphériques équirectangulaires ou faces de cube, sphériques partiels et cylindriques
    ajout de hotspots de visite, vers des images/photos ou des pages HTML internes/externes
    plans avec hotspots et radar
    Cartes (Google/Bing/OSM) avec hotspots et radar
    boussole
    images de fond avec ou sans lien vers une page extérieure ou un diaporama
    vignettes des panoramiques de la visite
    réseaux sociaux
    menu contextuel paramétrable
    barres de navigation pouvant être définies par l'utilisateur
    actionneurs externe permettant de commander la visite en javascript depuis la page HTML
    un système de calques à 10 niveau permettant de placer les éléments les uns par rapport aux autres
    ...


Vous pouvez visualiser des visites réalisées avec editeurPanovisu [Voir les visites](http://panovisu.fr/exemples).

J'ai débuté le développement de panoVisu en mars 2014, parce que j'aime comprendre ce que j'utilise. J'ai donc d'abord construit un visualiseur en utilisant uniquement le CSS3, puis le canvas 3D et enfin webGL.

J'ai voulu panoVisu entièrement libre et gratuit, ses sources sont disponibles sur la plateforme [GitHub](https://github.com/llang57/editeurPanovisu).

Une fois le développement du visualiseur commencé, je me suis dit que pour simplifier l'écriture des visites virtuelles il serait bien d'avoir un éditeur. Celui ci est aussi entièrement libre et gratuit. Il ne saurait rivaliser avec des produits professionnels tel panoTour, mais permettra aux amateurs (dont je fais partie) de pouvoir réaliser des visites virtuelles de qualité à moindre coût. Afin d'assurer un maximum de portabilité à l' éditeur, je l'ai écrit en java; il est donc utilisable (une fois java installé sur votre machine) sur windows, mac-os ou linux.
La version minimale de java devant être installée est la 8u20 (disponible ici puis choisir celle correspondant à votre système d'exploitation). Je suivrais l' évolution de java et vous préviendrais des mises à jour à effectuer.

Ces deux programmes sont en développement continu, donc n'hésitez pas à me faire remonter vos remarques et desiderata en utilisant le système de tickets mis en place : [Créer un ticket](http://panovisu.fr/hesk/). J'essayerais de faire évoluer les programmes le plus rapidement possible (ceci étant fait sur mon temps libre). Vous pouvez également apporter votre pierre à l' édifice en participant au développement sur la plateforme [GitHub](https://github.com/llang57/editeurPanovisu).

Il est donc possible aujourd'hui de réaliser des visites virtuelles (montages des vues avec Hugin et création des visites avec panoVisu) à l'aide de logiciels libres et gratuits.

Compatibilité
-------------

J'ai fait le choix de développer ce visualiseur en utilisant le langage HTML5 plutôt que Flash pour plusieurs raisons. La première est que Flash est un langage lourd et en perte de vitesse nécessitant l'installation d'un plugin sur le navigateur, de plus il ne fonctionne pas sur toutes les plateformes. La seconde étant que je n'avais ni le temps ni les compétences pour le faire.

Le langage HTML5 est le nouveau standard pour les animations, la vidéo et le son sur les navigateurs. Il est à l'heure actuelle supporté par l'immense majorité d'entre eux (à divers niveaux) dans leurs dernières versions.

Le support de l'accélération matérielle par webGL est lui également actif sur la plupart des navigateurs (moyennant une petite manipulation sur certains). Si webGL n'est pas supporté, le visualiseur utilise le canvas 3D à la place (opérationnel mais beaucoup, beaucoup plus lent). Le visualiseur est supporté par :

    Internet Explorer 9 et suivants
    Firefox 4 et suivants
    Safari 5.1 et suivants (WebGL-experimental doit être validé dans les préférences)
    Chrome 9 et suivants


**Laurent LANG**
