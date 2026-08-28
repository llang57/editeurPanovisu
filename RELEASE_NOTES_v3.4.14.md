# PanoVisu - Release Notes v3.4.14

Cette version porte sur la génération de descriptions par intelligence artificielle, dont plusieurs défauts rendaient le résultat imprécis — voire inutilisable — chez les utilisateurs installés.

## Les catalogues de modèles n'étaient pas livrés

Les fichiers `configPV/ollama-models.json` et `configPV/openrouter-models.json` n'étaient pas versionnés. Un build d'intégration continue, réalisé à partir d'un dépôt neuf, ne les contenait donc pas ; l'installeur exclut par ailleurs le répertoire `configPV` ; et rien ne recréait de catalogue au premier lancement. **Tout utilisateur installé se retrouvait avec une liste de modèles vide.**

Les catalogues sont désormais embarqués dans l'application et recopiés dans `configPV/` au premier démarrage, où ils restent modifiables. Un mécanisme de repli qui ne s'était jamais déclenché a également été corrigé : la configuration par défaut portait une liste vide mais non nulle, ce qui empêchait la liste de secours d'être atteinte.

## Modèles mis à jour

Sur les onze modèles OpenRouter proposés, **quatre n'existaient plus** chez le fournisseur — dont les deux premiers de la liste, ceux utilisés par défaut. Les prix affichés avaient également dérivé, jusqu'à un facteur quatre.

Le catalogue a été reconstruit à partir du catalogue OpenRouter interrogé en direct : onze modèles vérifiés présents, du gratuit au premium. Côté Ollama, `gemma4` a été ajouté et l'ordre de préférence revu — le premier modèle proposé était auparavant un modèle de 20 Go qui ne démarre pas sur une machine courante.

## La génération locale ne pouvait pas fonctionner

La requête envoyée à Ollama ne précisait aucune longueur de contexte. Ollama allouait donc le contexte maximal du modèle, souvent 128 000 jetons : sur une machine ordinaire, la génération échouait en erreur serveur, y compris pour un petit modèle. L'application impose désormais ses propres paramètres et ne dépend plus des réglages Ollama de chaque poste.

Un second défaut y contribuait : demander un modèle précis pouvait en lancer un autre. La comparaison ne portait que sur le nom avant les deux-points, si bien que `qwen2.5:14b` pouvait démarrer `qwen2.5:32b` — une variante bien plus lourde, qui échouait faute de mémoire.

## Descriptions plus fiables

Plusieurs mesures ont été prises contre l'invention de faits :

* **Échantillonnage.** La requête ne fixait aucune température : Ollama appliquait donc 0,8 par défaut, un réglage d'écriture créative, pendant que le prompt énonçait des règles anti-hallucination. La température est désormais fixée à 0,1 sur les trois services.
* **Contradiction supprimée.** Le prompt demandait de « décrire le panorama visible depuis ce lieu » alors que le modèle ne voit aucune image — une invitation explicite à inventer, placée quelques lignes au-dessus de l'interdiction d'inventer.
* **Règles étendues**, de cinq à dix, en français et en anglais : interdiction des distinctions patrimoniales, des superlatifs de notoriété et des chiffres de fréquentation ; obligation d'omettre plutôt que de nuancer ; interdiction de qualifier un lieu d'un échelon administratif qui n'a pas été fourni.
* **Données de localisation.** Le code attribuait aux champs du géocodeur des catégories erronées, annonçant par exemple une commune comme un département. Ces valeurs sont maintenant transmises sans être qualifiées.

## Vérification des descriptions

Une description générée est désormais relue automatiquement. Dates, mesures chiffrées, distinctions, superlatifs, formulations dubitatives et **noms propres absents des informations fournies** sont signalés dans le journal, pour relecture avant publication.

Ce dispositif répond à une limite qu'il faut énoncer clairement : **aucune consigne ne rend l'hallucination impossible**. Lors des essais, un modèle a situé les châteaux de Lastours « dans les gorges de la rivière Orb » alors que la rivière qui les longe est l'Orbiel — malgré une température basse et une interdiction explicite d'inventer des noms propres. La vérification signale ce cas ; elle ne l'empêche pas. Une relecture humaine reste nécessaire.

Un enseignement pratique en découle : **renseigner la clé LocationIQ améliore la précision plus sûrement que n'importe quelle consigne**. Le même modèle, à la même température, cesse d'inventer dès qu'il reçoit une localisation réelle.

## Qualité du projet

* Mise en place d'une véritable suite de tests JUnit — 15 tests — là où `mvn test` n'en exécutait aucun. Les classes de test ne sont plus embarquées dans l'application livrée.
* Le journal d'intégration continue affiche désormais le résultat des tests, qu'il masquait.
* Les requêtes vers les services d'IA sont construites avec une bibliothèque JSON : l'échappement manuel précédent produisait une requête invalide si un titre de panoramique contenait certains caractères.

---

## 📅 Historique Récent

### v3.4.12
* Correction du paquet macOS, qui ne démarrait pas ([#17](https://github.com/llang57/editeurPanovisu/issues/17)).

### v3.4.10
* Correction de la liste de tri des panoramiques, tronquée au-delà de dix panoramiques ([#16](https://github.com/llang57/editeurPanovisu/issues/16)).
