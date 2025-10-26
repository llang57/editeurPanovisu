#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Script pour ajouter automatiquement la documentation Javadoc aux méthodes sans documentation
"""

import re
import os

# Dictionnaire des documentations à ajouter (méthode -> documentation)
DOCUMENTATIONS = {
    # EditeurHTML.java
    ("EditeurHTML.java", "setTemplate"): """    /**
     * Définit le template HTML de base pour l'éditeur
     * 
     * <p>Retourne une chaîne de template HTML vide qui peut être personnalisée.
     * Cette méthode sert de point d'extension pour définir des templates prédéfinis.</p>
     * 
     * @return Template HTML (actuellement vide)
     */""",
    
    ("EditeurHTML.java", "affiche"): """    /**
     * Affiche l'éditeur HTML dans une fenêtre modale
     * 
     * <p>Cette méthode crée et affiche une fenêtre d'édition HTML avec un éditeur riche (HTMLEditor).
     * La fenêtre inclut des contrôles pour l'édition du contenu, le choix de position, 
     * la configuration des couleurs et le réglage de l'opacité.</p>
     * 
     * @param largeur Largeur souhaitée de la fenêtre d'édition
     * @param hauteur Hauteur souhaitée de la fenêtre d'édition
     */""",
    
    # EditeurPanovisu.java
    ("EditeurPanovisu.java", "rafraichitListePano"): """    /**
     * Rafraîchit la liste des panoramiques dans l'interface utilisateur
     * 
     * <p>Met à jour le panneau de vignettes et la ComboBox de sélection.
     * Appelée après l'ajout ou la suppression de panoramiques.</p>
     * 
     * @see #getPanoramiquesProjet()
     * @see #getiNombrePanoramiques()
     */""",
    
    # GestionnaireDiaporamaController.java
    ("GestionnaireDiaporamaController.java", "reInit"): """    /**
     * Réinitialise le gestionnaire avec un nouveau diaporama
     * 
     * <p>Remplace le diaporama actuel par une copie du diaporama fourni
     * et réinitialise l'interface pour refléter les nouvelles données.</p>
     * 
     * @param nouveauDiapo Le diaporama à charger (sera cloné)
     * @see Diaporama#clone()
     */""",
    
    # HotspotHTML.java
    ("HotspotHTML.java", "creeHTML"): """    /**
     * Génère le fichier HTML du hotspot dans le répertoire de destination
     * 
     * <p>Crée la structure nécessaire (répertoires, fichier HTML, images)
     * pour le hotspot HTML personnalisé.</p>
     * 
     * @param strPageHTML Chemin du répertoire de destination
     */""",
    
    # NavigateurPanoramique.java
    ("NavigateurPanoramique.java", "captureEcran"): """    /**
     * Capture l'écran actuel du panoramique et sauvegarde en image
     * 
     * <p>Génère une vignette du point de vue actuel du panoramique.</p>
     */""",
    
    ("NavigateurPanoramique.java", "imgTransformationImage"): """    /**
     * Transforme une image rectangulaire en projection équirectangulaire
     * 
     * @param imgRect Image rectangulaire source
     * @return Image transformée en équirectangulaire
     */""",
    
    ("NavigateurPanoramique.java", "affiche"): """    /**
     * Affiche le navigateur panoramique dans l'interface
     * 
     * <p>Initialise et rend visible le composant de navigation 360°.</p>
     */""",
    
    ("NavigateurPanoramique.java", "changeTaille"): """    /**
     * Redimensionne le navigateur panoramique
     * 
     * @param largeur Nouvelle largeur en pixels
     * @param hauteur Nouvelle hauteur en pixels
     */""",
    
    ("NavigateurPanoramique.java", "setNomImagePanoramique"): """    /**
     * Définit l'image panoramique à afficher
     * 
     * @param strImagePanoramique Chemin vers le fichier image
     * @param iRapport Rapport de redimensionnement
     */""",
    
    # VisualiseurImagesPanoramiques.java  
    ("VisualiseurImagesPanoramiques.java", "affichePano"): """    /**
     * Affiche le panoramique dans un AnchorPane
     * 
     * @return AnchorPane contenant le panoramique affiché
     */""",
    
    # OrdrePanoramique.java
    ("OrdrePanoramique.java", "creeListe"): """    /**
     * Crée et initialise la liste ordonnée des panoramiques
     * 
     * <p>Génère l'interface permettant de réorganiser les panoramiques par glisser-déposer.</p>
     */""",
    
    ("OrdrePanoramique.java", "supprimerElement"): """    /**
     * Supprime un élément de la liste des panoramiques
     * 
     * @param iElement Index de l'élément à supprimer
     */""",
    
    ("OrdrePanoramique.java", "ajouteNouveauxPanos"): """    /**
     * Ajoute les nouveaux panoramiques à la liste
     * 
     * <p>Synchronise la liste avec les panoramiques du projet.</p>
     */""",
    
    # ZoneTelecommande.java
    ("ZoneTelecommande.java", "calculeCentre"): """    /**
     * Calcule le centre géométrique de la zone de télécommande
     * 
     * <p>Utilisé pour le positionnement des éléments interactifs.</p>
     */""",
    
    # NavigateurOpenLayersSeul.java
    ("NavigateurOpenLayersSeul.java", "retireRadar"): """    /**
     * Retire l'affichage du radar de la carte
     * 
     * <p>Masque l'indicateur de position sur la carte OpenLayers.</p>
     */""",
    
    # EquiCubeDialogController.java
    ("EquiCubeDialogController.java", "tskTraitement"): """    /**
     * Crée la tâche de traitement de transformation d'image
     * 
     * <p>Gère la conversion entre format équirectangulaire et faces cubiques.</p>
     * 
     * @return Tâche asynchrone de traitement
     */""",
    
    # DocumentationDialog.java - getters simples
    ("DocumentationDialog.java", "getTitre"): """    /**
     * Obtient le titre du document de documentation
     * 
     * @return Titre du document
     */""",
    
    ("DocumentationDialog.java", "getChemin"): """    /**
     * Obtient le chemin vers le fichier de documentation
     * 
     * @return Chemin du fichier
     */""",
    
    ("DocumentationDialog.java", "getType"): """    /**
     * Obtient le type de documentation (MARKDOWN, HTML, etc.)
     * 
     * @return Type de documentation
     */""",
    
    # EditeurHTML.java - autres méthodes
    ("EditeurHTML.java", "addPropertyChangeListener"): """    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété à écouter
     * @param listener Écouteur à ajouter
     */""",
    
    ("EditeurHTML.java", "removePropertyChangeListener"): """    /**
     * Retire un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     */""",
    
    ("EditeurHTML.java", "firePropertyChange"): """    /**
     * Déclenche un événement de changement de propriété
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur
     * @param newValue Nouvelle valeur
     */""",
    
    ("EditeurHTML.java", "hideImageNodesMatching"): """    /**
     * Masque les nœuds d'image correspondant au pattern
     * 
     * <p>Parcourt récursivement l'arbre des nœuds et masque les images
     * dont le nom correspond au pattern fourni.</p>
     * 
     * @param node Nœud racine à parcourir
     * @param imageNamePattern Pattern de nom d'image à rechercher
     * @param depth Profondeur actuelle dans l'arbre
     */""",
    
    # EditeurPanovisu.java - succeeded
    ("EditeurPanovisu.java", "succeeded"): """        /**
         * Méthode appelée lorsque la tâche de chargement réussit
         * 
         * <p>Gère la fin du chargement d'un fichier projet.</p>
         */""",
    
    # GestionnaireDiaporamaController.java - PropertyChangeSupport
    ("GestionnaireDiaporamaController.java", "addPropertyChangeListener"): """    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété à écouter
     * @param listener Écouteur à ajouter
     */""",
    
    ("GestionnaireDiaporamaController.java", "removePropertyChangeListener"): """    /**
     * Retire un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     */""",
    
    ("GestionnaireDiaporamaController.java", "firePropertyChange"): """    /**
     * Déclenche un événement de changement de propriété
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur
     * @param newValue Nouvelle valeur
     */""",
    
    # ListePanoramiqueCellule.java
    ("ListePanoramiqueCellule.java", "captureEcranHS"): """    /**
     * Capture l'écran du hotspot panoramique
     * 
     * @return Image capturée
     */""",
    
    ("ListePanoramiqueCellule.java", "addPropertyChangeListener"): """    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété à écouter
     * @param listener Écouteur à ajouter
     */""",
    
    ("ListePanoramiqueCellule.java", "removePropertyChangeListener"): """    /**
     * Retire un écouteur de changement de propriété
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     */""",
    
    ("ListePanoramiqueCellule.java", "firePropertyChange"): """    /**
     * Déclenche un événement de changement de propriété
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur
     * @param newValue Nouvelle valeur
     */""",
    
    # OrdrePanoramique.java - surcharge
    ("OrdrePanoramique.java", "creeListe"): """    /**
     * Crée et initialise la liste ordonnée des panoramiques avec ordre spécifique
     * 
     * @param strOrdre Ordre de tri des panoramiques
     */""",
    
    # VisualiseurImagesPanoramiques.java - surcharges
    ("VisualiseurImagesPanoramiques.java", "imgTransformationImage"): """    /**
     * Transforme une image rectangulaire en projection équirectangulaire
     * 
     * @param imgRect Image rectangulaire source
     * @return Image transformée
     */""",
    
    ("VisualiseurImagesPanoramiques.java", "setImagePanoramique"): """    /**
     * Définit l'image panoramique à afficher à partir d'une Image
     * 
     * @param strImagePanoramique Nom du fichier image
     * @param imgPanoramique Image JavaFX à afficher
     */""",
    
    ("VisualiseurImagesPanoramiques.java", "setNomImagePanoramique"): """    /**
     * Définit l'image panoramique à afficher à partir d'un chemin
     * 
     * @param strImagePanoramique Chemin vers le fichier image
     * @param iRapport Rapport de redimensionnement
     */""",
    
    # Méthodes main() - documentation minimale
    ("DocumentationDialog.java", "main"): """    /**
     * Point d'entrée pour tester la fenêtre de documentation
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */""",
    
    ("TestAIClients.java", "main"): """    /**
     * Point d'entrée pour tester les clients IA (Ollama, HuggingFace, OpenRouter)
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */""",
    
    ("TestImageResizeInteractive.java", "main"): """    /**
     * Point d'entrée pour tester interactivement le redimensionnement d'images GPU
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */""",
    
    ("TestReductionImage.java", "main"): """    /**
     * Point d'entrée pour tester la réduction d'images avec GPU
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */""",
    
    # ===== 8 méthodes restantes sans documentation =====
    
    # EditeurPanovisu.java - succeeded()
    ("EditeurPanovisu.java", "succeeded"): """            /**
             * Callback appelé après le chargement réussi du projet
             * 
             * <p>Méthode appelée automatiquement par JavaFX Task après l'exécution réussie
             * de la tâche de chargement. Elle met à jour l'interface utilisateur avec les
             * données du projet chargé, notamment les panoramiques, les plans et les hotspots.</p>
             * 
             * <p><strong>Actions effectuées :</strong></p>
             * <ul>
             *   <li>Rend visible le panneau de choix des panoramiques</li>
             *   <li>Initialise les plans si nécessaire</li>
             *   <li>Charge les hotspots de chaque panoramique</li>
             * </ul>
             * 
             * @see javafx.concurrent.Task#succeeded()
             */""",
    
    # GestionnaireDiaporamaController.java - initDiaporama()
    ("GestionnaireDiaporamaController.java", "initDiaporama"): """    /**
     * Initialise l'interface du gestionnaire de diaporama
     * 
     * <p>Crée et configure tous les composants visuels nécessaires pour la gestion
     * du diaporama : zones d'affichage des images, contrôles de navigation, 
     * sélecteurs de transitions, etc.</p>
     * 
     * <p><strong>Composants initialisés :</strong></p>
     * <ul>
     *   <li>AnchorPane principal (900x650px)</li>
     *   <li>Zone d'affichage des images (ImageView ou WebView selon le type)</li>
     *   <li>Sélecteur de transitions (ComboBox)</li>
     *   <li>Liste des images du diaporama</li>
     *   <li>Contrôles d'édition (ajout/suppression d'images)</li>
     * </ul>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * GestionnaireDiaporamaController controller = new GestionnaireDiaporamaController();
     * controller.initDiaporama();
     * }</pre>
     * 
     * @see #apDiaporama
     * @see #apDiapo1
     */""",
    
    # NavigateurPanoramique.java - captureEcranHS()
    ("NavigateurPanoramique.java", "captureEcranHS"): """    /**
     * Capture une image de l'écran du panorama pour les hotspots
     * 
     * <p>Crée une capture d'écran de la zone de visualisation panoramique actuelle.
     * Cette image est utilisée comme aperçu miniature pour les hotspots permettant
     * de visualiser la direction pointée.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * Image apercu = navigateur.captureEcranHS();
     * hotspot.setImageApercu(apercu);
     * }</pre>
     * 
     * @return Image capturée de la vue panoramique actuelle
     * @see javafx.scene.Node#snapshot(SnapshotParameters, WritableImage)
     */""",
    
    # NavigateurPanoramique.java - addPropertyChangeListener()
    ("NavigateurPanoramique.java", "addPropertyChangeListener"): """    /**
     * Ajoute un écouteur pour observer les changements d'une propriété spécifique
     * 
     * <p>Permet d'enregistrer un listener qui sera notifié lorsque la propriété
     * identifiée par son nom change de valeur. Utilisé principalement pour 
     * synchroniser les coordonnées géographiques entre composants.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * navigateur.addPropertyChangeListener("longitude", evt -> {
     *     BigDecimal nouvelleLongitude = (BigDecimal) evt.getNewValue();
     *     System.out.println("Longitude : " + nouvelleLongitude);
     * });
     * }</pre>
     * 
     * @param propertyName Nom de la propriété à observer (ex: "longitude", "latitude")
     * @param listener Écouteur à notifier lors des changements
     * @see #removePropertyChangeListener(String, PropertyChangeListener)
     * @see #firePropertyChange(String, BigDecimal, BigDecimal)
     */""",
    
    # NavigateurPanoramique.java - removePropertyChangeListener()
    ("NavigateurPanoramique.java", "removePropertyChangeListener"): """    /**
     * Retire un écouteur de changement de propriété
     * 
     * <p>Supprime l'écouteur précédemment enregistré pour la propriété spécifiée.
     * Après cette opération, le listener ne recevra plus de notifications.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * PropertyChangeListener listener = evt -> { ... };
     * navigateur.addPropertyChangeListener("longitude", listener);
     * // Plus tard...
     * navigateur.removePropertyChangeListener("longitude", listener);
     * }</pre>
     * 
     * @param propertyName Nom de la propriété observée
     * @param listener Écouteur à retirer
     * @see #addPropertyChangeListener(String, PropertyChangeListener)
     */""",
    
    # NavigateurPanoramique.java - firePropertyChange()
    ("NavigateurPanoramique.java", "firePropertyChange"): """    /**
     * Notifie les écouteurs d'un changement de valeur de propriété
     * 
     * <p>Déclenche un événement de changement de propriété si la nouvelle valeur
     * diffère de l'ancienne. Les écouteurs enregistrés via 
     * {@link #addPropertyChangeListener(String, PropertyChangeListener)} seront notifiés.</p>
     * 
     * <p><strong>Note :</strong> Si les valeurs sont identiques, aucun événement n'est déclenché
     * (optimisation pour éviter les notifications inutiles).</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * BigDecimal ancienneLongitude = getLongitude();
     * setLongitude(nouvelleLongitude);
     * firePropertyChange("longitude", ancienneLongitude, nouvelleLongitude);
     * }</pre>
     * 
     * @param propertyName Nom de la propriété qui a changé
     * @param oldValue Ancienne valeur (avant modification)
     * @param newValue Nouvelle valeur (après modification)
     * @see java.beans.PropertyChangeSupport#firePropertyChange(PropertyChangeEvent)
     */""",
    
    # OrdrePanoramique.java - rafraichitListe()
    ("OrdrePanoramique.java", "rafraichitListe"): """    /**
     * Rafraîchit l'affichage de la liste des panoramiques
     * 
     * <p>Reconstruit entièrement la liste visuelle des panoramiques en préservant
     * l'ordre actuel. Cette méthode recrée toutes les cellules graphiques avec
     * leurs vignettes et informations à jour.</p>
     * 
     * <p><strong>Actions effectuées :</strong></p>
     * <ul>
     *   <li>Supprime la ListView actuelle de l'AnchorPane</li>
     *   <li>Réinitialise les collections (cellules et ObservableList)</li>
     *   <li>Recrée chaque cellule avec image miniature et titre</li>
     *   <li>Met à jour l'affichage dans l'interface</li>
     * </ul>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * // Après modification de l'ordre des panoramiques
     * ordrePanoramique.rafraichitListe();
     * }</pre>
     * 
     * @see PanoramiqueCellule
     * @see #strPanos
     * @see #cellulesPanoramiques
     */""",
    
    # VisualiseurImagesPanoramiques.java - imgTransformationImage()
    ("VisualiseurImagesPanoramiques.java", "imgTransformationImage"): """    /**
     * Transforme et redimensionne une image panoramique rectangulaire
     * 
     * <p>Effectue une réduction optimisée de l'image panoramique en utilisant en
     * priorité l'accélération GPU (interpolation bicubique), avec un fallback CPU
     * si le GPU n'est pas disponible. La transformation pixel par pixel applique
     * une projection Mercator.</p>
     * 
     * <p><strong>Algorithme :</strong></p>
     * <ol>
     *   <li>Calcule les dimensions cibles : largeur/iRapport × (largeur/2/iRapport)</li>
     *   <li>Tente le redimensionnement GPU via ImageResizeGPU (méthode bicubique)</li>
     *   <li>En cas d'échec, utilise la transformation CPU pixel par pixel</li>
     * </ol>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * Image panoramaComplet = new Image("file:panorama.jpg");
     * // Divise par 4 la taille pour l'affichage miniature
     * Image miniature = imgTransformationImage(panoramaComplet, 4);
     * imageView.setImage(miniature);
     * }</pre>
     * 
     * @param imgRect Image panoramique rectangulaire source
     * @param iRapport Facteur de réduction (2 = moitié, 4 = quart, etc.)
     * @return Image transformée et redimensionnée
     * @see ImageResizeGPU#resizeAuto(Image, int, int, InterpolationMethod)
     * @see InterpolationMethod#BICUBIC
     */""",
    
    # ========== 8 MÉTHODES RESTANTES ==========
    
    # EditeurPanovisu.java - succeeded() (callback Task)
    ("EditeurPanovisu.java", "succeeded"): """            /**
             * Callback appelé après le chargement réussi des panoramiques
             * 
             * <p>Cette méthode est invoquée automatiquement lorsque la tâche asynchrone
             * de chargement des panoramiques se termine avec succès. Elle effectue :</p>
             * <ul>
             *   <li>Affichage du panneau de choix des panoramiques</li>
             *   <li>Ajout des plans au gestionnaire</li>
             *   <li>Mise à jour des références des hotspots entre panoramiques</li>
             * </ul>
             * 
             * <p><strong>Note :</strong> Cette méthode est une méthode de callback JavaFX Task
             * et s'exécute sur le thread JavaFX Application.</p>
             * 
             * @see javafx.concurrent.Task#succeeded()
             */""",
    
    # GestionnaireDiaporamaController.java - initDiaporama()
    ("GestionnaireDiaporamaController.java", "initDiaporama"): """    /**
     * Initialise l'interface du gestionnaire de diaporama
     * 
     * <p>Crée et configure tous les composants visuels nécessaires pour la gestion
     * du diaporama :</p>
     * <ul>
     *   <li>AnchorPane principal (900x650px)</li>
     *   <li>Zone d'affichage des images (ImageView ou WebView selon le type)</li>
     *   <li>Sélecteur de transitions (ComboBox)</li>
     *   <li>Liste des images du diaporama</li>
     *   <li>Contrôles d'édition (ajout/suppression d'images)</li>
     * </ul>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * GestionnaireDiaporamaController controller = new GestionnaireDiaporamaController();
     * controller.initDiaporama();
     * }</pre>
     * 
     * @see #apDiaporama
     * @see #apDiapo1
     */""",
    
    # NavigateurPanoramique.java - captureEcranHS()
    ("NavigateurPanoramique.java", "captureEcranHS"): """    /**
     * Capture une image de l'écran du panorama pour les hotspots
     * 
     * <p>Crée une capture d'écran de la zone de visualisation panoramique actuelle.
     * Cette image est utilisée comme aperçu miniature pour les hotspots permettant
     * de visualiser la direction pointée.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * NavigateurPanoramique nav = new NavigateurPanoramique();
     * Image apercu = nav.captureEcranHS();
     * hotspot.setImageApercu(apercu);
     * }</pre>
     * 
     * @return Image capturée de la vue panoramique actuelle
     * @see javafx.scene.SubScene#snapshot(javafx.scene.SnapshotParameters, javafx.scene.image.WritableImage)
     */""",
    
    # NavigateurPanoramique.java - addPropertyChangeListener()
    ("NavigateurPanoramique.java", "addPropertyChangeListener"): """    /**
     * Ajoute un écouteur de changement de propriété
     * 
     * <p>Permet d'écouter les modifications d'une propriété spécifique du navigateur.
     * Utilisé notamment pour surveiller les changements de longitude et latitude.</p>
     * 
     * <p><strong>Exemple d'utilisation :</strong></p>
     * <pre>{@code
     * navigateur.addPropertyChangeListener("longitude", evt -> {
     *     System.out.println("Nouvelle longitude: " + evt.getNewValue());
     * });
     * }</pre>
     * 
     * @param propertyName Nom de la propriété à écouter ("longitude", "latitude", etc.)
     * @param listener Écouteur qui sera notifié des changements
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     */""",
    
    # NavigateurPanoramique.java - removePropertyChangeListener()
    ("NavigateurPanoramique.java", "removePropertyChangeListener"): """    /**
     * Retire un écouteur de changement de propriété
     * 
     * <p>Supprime un écouteur précédemment ajouté pour une propriété spécifique.
     * L'écouteur ne recevra plus de notifications pour cette propriété.</p>
     * 
     * @param propertyName Nom de la propriété
     * @param listener Écouteur à retirer
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
     */""",
    
    # NavigateurPanoramique.java - firePropertyChange()
    ("NavigateurPanoramique.java", "firePropertyChange"): """    /**
     * Déclenche un événement de changement de propriété
     * 
     * <p>Notifie tous les écouteurs enregistrés qu'une propriété a changé.
     * Si les valeurs ancienne et nouvelle sont identiques, aucun événement n'est déclenché.</p>
     * 
     * <p><strong>Exemple d'utilisation interne :</strong></p>
     * <pre>{@code
     * BigDecimal ancienneLongitude = this.longitude;
     * this.longitude = nouvelleLongitude;
     * firePropertyChange("longitude", ancienneLongitude, nouvelleLongitude);
     * }</pre>
     * 
     * @param propertyName Nom de la propriété modifiée
     * @param oldValue Ancienne valeur (BigDecimal)
     * @param newValue Nouvelle valeur (BigDecimal)
     * @see java.beans.PropertyChangeSupport#firePropertyChange(java.beans.PropertyChangeEvent)
     */""",
    
    # OrdrePanoramique.java - rafraichitListe()
    ("OrdrePanoramique.java", "rafraichitListe"): """    /**
     * Rafraîchit la liste des panoramiques affichée
     * 
     * <p>Reconstruit complètement la liste visuelle des panoramiques en :</p>
     * <ol>
     *   <li>Vidant la liste actuelle des cellules</li>
     *   <li>Créant de nouvelles cellules pour chaque panoramique</li>
     *   <li>Associant les images miniatures et titres</li>
     *   <li>Mettant à jour l'affichage de la ListView</li>
     * </ol>
     * 
     * <p>Appelée après un réordonnancement ou une modification de la liste
     * des panoramiques pour synchroniser l'affichage.</p>
     * 
     * @see PanoramiqueCellule
     * @see #strPanos
     * @see #cellulesPanoramiques
     */""",
}

def ajouter_javadoc(fichier, nom_methode, javadoc):
    """
    Ajoute la documentation Javadoc avant une méthode
    """
    chemin_complet = f"src/editeurpanovisu/{fichier}"
    if not os.path.exists(chemin_complet):
        # Essayer avec gpu/
        chemin_complet = f"src/editeurpanovisu/gpu/{fichier}"
        if not os.path.exists(chemin_complet):
            print(f"  ⚠️  Fichier non trouvé : {fichier}")
            return False
    
    with open(chemin_complet, 'r', encoding='utf-8') as f:
        lignes = f.readlines()
    
    # Trouver la ligne de la méthode
    modifie = False
    i = 0
    while i < len(lignes):
        ligne = lignes[i]
        
        # Détecter la méthode (public/protected + nom)
        if (('public ' in ligne or 'protected ' in ligne) and 
            f'{nom_methode}(' in ligne):
            
            # Vérifier qu'il n'y a pas déjà de Javadoc (recherche étendue jusqu'à 50 lignes)
            a_javadoc = False
            for j in range(max(0, i-50), i):
                if '/**' in lignes[j]:
                    a_javadoc = True
                    break
            
            if not a_javadoc:
                # Insérer le Javadoc
                lignes.insert(i, javadoc + '\n')
                modifie = True
                break
        
        i += 1
    
    if modifie:
        with open(chemin_complet, 'w', encoding='utf-8') as f:
            f.writelines(lignes)
        return True
    
    return False

def main():
    print("="*70)
    print("  AJOUT DE DOCUMENTATION JAVADOC")
    print("="*70)
    print()
    
    total = len(DOCUMENTATIONS)
    succes = 0
    
    for (fichier, methode), javadoc in DOCUMENTATIONS.items():
        print(f"📝 {fichier} : {methode}()", end=" ... ")
        if ajouter_javadoc(fichier, methode, javadoc):
            print("✅")
            succes += 1
        else:
            print("❌")
    
    print()
    print("="*70)
    print(f"✅ Documentation ajoutée : {succes}/{total} méthodes")
    print("="*70)

if __name__ == "__main__":
    main()
