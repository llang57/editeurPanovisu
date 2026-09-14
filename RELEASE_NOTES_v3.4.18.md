# PanoVisu - Release Notes v3.4.18

## Correctif : la transformation cube ↔ équirectangulaire était inutilisable

La fenêtre de transformation s'ouvrait tronquée. Le bouton **« Lancer le traitement »**, situé en bas, sortait du cadre — et la fenêtre refusait d'être agrandie, rendant l'outil impossible à utiliser.

### Origine du problème

Deux limitations se combinaient.

La taille **maximale** de la fenêtre était établie à partir des préférences enregistrées, dans lesquelles la taille courante est justement sauvegardée à chaque redimensionnement. La taille maximale valait donc en permanence la taille actuelle : l'agrandissement était bloqué, et chaque rétrécissement abaissait le plafond d'autant. La fenêtre ne pouvait plus que rétrécir, sans retour possible.

Par ailleurs, le panneau de contenu était plafonné à une hauteur fixe de 420 pixels. Comme la rangée de boutons est le dernier élément de la fenêtre, c'est elle qui disparaissait en premier dès que le contenu dépassait cette hauteur — ce que les thèmes introduits en version 3 rendent fréquent, puisqu'ils modifient la hauteur des contrôles.

### Correction

* Le plafond du panneau de contenu est supprimé ; celui-ci suit désormais la taille de la fenêtre.
* Les préférences enregistrées servent de taille **initiale** et non de limite.
* Une taille **minimale** est calculée à partir de la taille dont le contenu a réellement besoin, telle que JavaFX la détermine — et non d'une nouvelle valeur en dur, qui serait invalidée au prochain changement de thème. Le bouton reste ainsi toujours atteignable.
* Une préférence enregistrée plus petite que ce minimum est ignorée, ce qui débloque les installations déjà prises au piège. Sans cela, le correctif serait resté sans effet pour elles.

Le sens inverse, équirectangulaire vers cube, partage la même fenêtre et bénéficie donc de la même correction.

## Installations : les anciens fichiers sont désormais supprimés

Le nom du fichier programme portant le numéro de version, chaque mise à jour en déposait un nouveau sans retirer les précédents. Une installation ayant suivi plusieurs versions accumulait ainsi près de 100 Mo par version — trois fichiers coexistaient sur un poste de test, soit 282 Mo dont 188 inutiles.

L'installeur purge maintenant les versions antérieures avant de copier les nouveaux fichiers. Cette version est la première à en bénéficier : les fichiers obsolètes déjà présents seront supprimés lors de son installation.

## Qualité du projet

Cinq programmes de mise au point internes n'étaient utiles qu'au développement mais se retrouvaient compilés dans l'application distribuée. Ils en sont sortis. Par ailleurs, le contrôle automatique de l'affichage des listes, jusqu'ici ignoré faute d'écran sur le serveur d'intégration, s'y exécute désormais réellement.

Ces deux points ne changent rien au fonctionnement de l'application : ils réduisent le risque de régression pour les versions futures.

---

## 📅 Historique Récent

### v3.4.16
* Correction des descriptions vides avec les modèles d'IA récents ; catalogues de modèles à jour.

### v3.4.14
* Fiabilité de la génération par IA : catalogues enfin livrés, génération locale réparée.

### v3.4.12
* Correction du paquet macOS, qui ne démarrait pas ([#17](https://github.com/llang57/editeurPanovisu/issues/17)).
