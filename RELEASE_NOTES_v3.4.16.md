# PanoVisu - Release Notes v3.4.16

Cette version corrige un défaut silencieux de la génération de descriptions par intelligence artificielle et met à jour les catalogues de modèles.

## Correctif important : descriptions vides avec les modèles récents

Les modèles à raisonnement — Qwen 3.x et les générations suivantes, de plus en plus répandus — placent leur réflexion interne dans un champ distinct de leur réponse. Cette réflexion consommait la totalité du budget de génération : l'application recevait une chaîne **vide**, sans message d'erreur, et la description restait blanche.

Mesuré sur `qwen3.5` : 400 jetons dépensés, 1 558 caractères de réflexion, réponse vide. La requête désactive désormais ce mode, sans effet sur les modèles classiques.

Ce défaut ne concernait pas seulement un modèle particulier : il se serait manifesté chez tout utilisateur installant un modèle récent, et sa fréquence n'aurait fait qu'augmenter.

## Catalogues de modèles à jour

### OpenRouter

Le catalogue a été reconstruit à partir du catalogue interrogé en direct, passé de 387 à 436 modèles. Les onze modèles précédents restaient tous valides — aucun n'avait disparu, aucun prix n'avait dérivé — mais leur ordre posait problème.

**L'ordre suit désormais la qualité et non le prix.** Le coût réel d'une description a été chiffré : entre 0 et 1,5 centime selon le modèle, soit au maximum 21 centimes pour une visite de quatorze panoramiques. Trier par prix croissant plaçait donc le modèle le plus faible en tête pour économiser une fraction de centime, sur la fonctionnalité dont toute la difficulté est l'exactitude.

Nouveautés intégrées : `gpt-5.6-luna` (plus récent **et** moins cher que `gpt-5-mini`), `gemini-3.8-flash`, `qwen3.8-flash` et `mistral-nemo`. Chaque entrée indique maintenant son coût indicatif par description.

### Ollama

Le catalogue local a été reconstruit et **ordonné à partir d'une campagne de vingt-cinq générations** — cinq lieux réels, cinq modèles — en comptant les violations explicites des consignes.

| Modèle | Violations relevées |
|---|---|
| Gemma 4 | 4 |
| Qwen 3.5 | 4 |
| Mistral Nemo | 4 |
| Qwen 2.5 (14B) | 5 |
| Phi-4 | 7 |

Les trois premiers sont à égalité dans le bruit de mesure ; seul Phi-4 se détache nettement, avec des ajouts non fournis et un superlatif interdit. À noter : la plupart des signalements portent sur des échelons administratifs **exacts mais non transmis** au modèle — le contrôle fonctionne comme prévu, les modèles n'inventent pas.

Deux enseignements, contraires à l'intuition : le modèle le plus récent n'est pas le meilleur, et un classement établi sur deux essais s'est révélé faux une fois porté à vingt-cinq.

## Vérification des descriptions

Le contrôle automatique ne signale plus à tort un nom propre pourtant fourni lorsque l'accentuation diffère — un titre saisi « Cathedrale » et une description écrivant « Cathédrale » ne déclenchent plus d'alerte. Un contrôle bruyant finit par être ignoré.

## Documentation

Les deux fichiers README ont été réorganisés : le projet et ses fonctionnalités sont présentés d'abord, les notes de version renvoyées en fin de document. L'historique détaillé rejoint le changelog du wiki, complété des versions qui y manquaient. Plusieurs technologies annoncées à tort ont été corrigées.

---

## 📅 Historique Récent

### v3.4.14
* Fiabilité de la génération par IA : catalogues enfin livrés, génération locale réparée, mesures contre l'invention de faits.

### v3.4.12
* Correction du paquet macOS, qui ne démarrait pas ([#17](https://github.com/llang57/editeurPanovisu/issues/17)).

### v3.4.10
* Correction de la liste de tri des panoramiques, tronquée au-delà de dix panoramiques ([#16](https://github.com/llang57/editeurPanovisu/issues/16)).
