# PanoVisu - Release Notes v3.4.10

## Correctif : liste de tri des panoramiques tronquée au-delà de 10 panoramiques ([#16](https://github.com/llang57/editeurPanovisu/issues/16))

Dans l'onglet **Visite → Paramètres Visite**, la liste de tri des panoramiques n'affichait qu'une partie des panoramiques dès que le projet en comportait plus d'une dizaine. Une bande blanche occupait le bas du cadre et les derniers panoramiques restaient inaccessibles, alors que l'onglet **Plan de visite** les listait correctement.

### Origine du problème

`OrdrePanoramique` calculait la hauteur du cadre à partir d'une constante de **46 px par ligne**, sans jamais imposer cette hauteur à la liste. Cette hypothèse était exacte avec la feuille de style d'origine, mais les thèmes introduits en v3.x (AtlantaFX, MaterialFX, FlatLaf) réduisent le remplissage (`padding`) des cellules et ramènent la ligne à environ 39 px.

Le cadre étant verrouillé par `min = max = pref`, le mécanisme de virtualisation de JavaFX conservait le nombre de cellules calculé **avant** l'application du thème (465 / 46 ≈ 10) puis les affichait à leur hauteur réduite. Résultat : environ 72 px de vide en bas du cadre et les panoramiques suivants jamais rendus.

Ce code n'avait pas été modifié depuis la v1.2.6 : ce sont les thèmes ajoutés en v3.x qui ont invalidé la constante, ce qui explique que le défaut ne soit apparu que récemment, et seulement au-delà d'une dizaine de panoramiques.

### Correction

* **Hauteur de ligne imposée :** la hauteur est désormais fixée sur la liste (`setFixedCellSize`) *et* utilisée pour le calcul du cadre. Les deux valeurs ne peuvent plus diverger, quel que soit le thème actif.
* **Dimensionnement factorisé :** le bloc de calcul, jusque-là dupliqué à l'identique dans cinq méthodes (`creeListe()`, `creeListe(String)`, `supprimerElement()`, `rafraichitListe()`, `ajouteNouveauxPanos()`), est regroupé dans une méthode unique. Les nombres magiques `46`, `465`, `300` et `5` deviennent des constantes nommées.
* **Dimensionnement sur le contenu réel :** au rechargement d'un projet, le cadre est calculé à partir du nombre d'éléments réellement listés et non du nombre de panoramiques du projet. Un fichier `.pvu` dont l'ordre serait incomplet ne produit plus un cadre trop haut et partiellement vide.
* **Correction d'un empilement de listes :** `ajouteNouveauxPanos()` affectait une variable locale au lieu du champ de la classe. La référence interne pointait donc vers une liste orpheline, et un rafraîchissement ultérieur — après suppression d'un panoramique — superposait deux listes dans le même conteneur au lieu de remplacer l'ancienne.

### Vérification

Le comportement a été mesuré avant et après correction, thème et feuille de style réels appliqués :

| Mesure | Avant | Après |
|---|---|---|
| Hauteur de ligne rendue | 39,2 px | 46,0 px |
| Lignes affichées (14 panoramiques) | 10 | 11 |
| Bande blanche en bas de cadre | 71,7 px | aucune |
| Défilement jusqu'au dernier | inatteignable visuellement | atteint (`value = 1.0`) |

Non-régression contrôlée sur des listes de 3, 10, 11, 14 et 25 panoramiques : en dessous de 10 le cadre s'ajuste au contenu sans ascenseur, au-delà la virtualisation et le défilement fonctionnent normalement.

## Divers

* Montée de version à 3.4.10 dans `pom.xml`, `installer.iss` et les fichiers d'internationalisation.
* Documentation `CLAUDE.md` corrigée et complétée (format réel des fichiers `.pvu`, emplacement des noyaux OpenCL, serveur de prévisualisation, chaîne d'export vers le visualiseur HTML5, particularités du plan de compilation).

---

## 📅 Historique Récent

### v3.4.8
* Nouveaux modèles IA (OpenRouter, Ollama), lecture dynamique depuis les fichiers JSON.
* Refonte du prompt système anti-hallucination dans `OllamaService`.
* Correction des liens d'ancrage de l'aide intégrée (F1) et bouton « retour en haut ».
* Correction de l'interpolation du nom de JAR dans `build-installer.ps1`.

### v3.4.6
* Correction du rendu `PanoramicCube` (mapping UV par `TriangleMesh`, correction Mercator).
* Qualité plein écran portée à 2000 px.
