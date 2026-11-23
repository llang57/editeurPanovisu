# Légende sous les images des hotspots HTML — conception courte

Proposition de PhotoDidacte (Jorge), [discussion #18](https://github.com/llang57/editeurPanovisu/discussions/18).

## Intention

Pouvoir afficher sous une image insérée dans un hotspot HTML (page interne) un texte visible : titre, crédit
« © auteur ». Un attribut `title` ne suffit pas : il n'apparaît qu'au survol, et pas du tout au doigt.

## Objectifs / non-objectifs

- **Objectif** : champ « Légende » facultatif dans la boîte *Insertion d'image* de l'éditeur HTML ; la légende
  s'affiche centrée, en petit italique, sous l'image, dans l'éditeur comme dans la visite exportée, et suit l'image
  quelle que soit sa position (gauche, centre, droite, dans le texte).
- **Non-objectif** : modifier la légende d'une image déjà insérée par une boîte de dialogue. Elle fait partie du
  texte : on la corrige directement dans l'éditeur.
- **Non-objectif** : réglages de style de la légende (police, taille, couleur). L'éditeur WYSIWYG permet déjà de la
  mettre en forme.
- **Anti-objectif** : changer le HTML produit pour une image **sans** légende, ou le format `.pvu`.

## Approches considérées

1. **`<figure>` / `<figcaption>`** — sémantique idéale, choisie au départ. **Écartée après lecture du code** : le
   parseur HTML ferme tout `<p>` ouvert dès qu'il rencontre `<figure>`. En position « Texte » (image au fil de la
   ligne) le paragraphe serait coupé en deux, à l'insertion comme au rechargement.
2. **Paragraphe sous l'image** — se décroche dès que l'image flotte à gauche ou à droite. Écartée.
3. **Conteneur `<span>` en `inline-block`** (retenue) — un `<span>` est autorisé partout dans un paragraphe ; le
   conteneur porte la position (flottant, centré, en ligne) et la largeur de l'image, la légende est un `<span>` en
   `display:block` dessous. Rendu identique à l'option 1, sans la coupure. On perd l'association sémantique
   `figcaption` pour les lecteurs d'écran ; la légende reste lue juste après l'image, dans l'ordre du texte.

## Conception

- La construction du fragment HTML d'insertion sort de l'`EventHandler` vers une classe pure
  `FragmentImageHTML` (testable en JUnit sans JavaFX) avec une énumération `PositionImage`
  (`GAUCHE`, `CENTRE`, `DROITE`, `TEXTE`).
- **Sans légende** (vide ou blanche) : le fragment est **identique octet pour octet** au code actuel.
- **Avec légende** : ancre ⚓, puis
  `<span class='pv-image-legendee' style='display:inline-block;width:Lpx;…position…'><img …/><span class='pv-legende' style='display:block;text-align:center;font-style:italic;font-size:0.85em;'>texte</span></span>`.
  Le flottement gauche/droite passe de l'`<img>` au conteneur ; le centrage garde l'enveloppe
  `</p><p style='text-align : center;'>…</p><p>` existante.
- Le texte de la légende est échappé HTML (`&`, `<`, `>`, `"`, `'`) et rogné. Les `:` `;` `[` `]` sont déjà
  traités par le chemin existant (`&dp`/`&pv` dans le `.pvu`, `&#91;`/`&#93;` à la validation — le codage précédent, `&lbrace;`/`&rbrace;`, transformait en fait les crochets en accolades ; corrigé dans cette branche).
- **Suppression (⚓)** : si l'image est dans un `.pv-image-legendee`, tout le conteneur est retiré (légende
  comprise) ; sinon comportement actuel. Fonctionne aussi après rechargement, où l'`<img>` reçoit un nouvel
  identifiant mais garde son conteneur.
- Validation et export : rien à changer — ni le nettoyage (`<button>` d'ancre, `<div contenteditable>`,
  `data-image-id`) ni `HotspotHTML.creeHTML` ne touchent aux `<span>`.
- i18n : `editeurHTML.legendeImage` (libellé) et `editeurHTML.legendeImageAide` (texte indicatif) dans les 6 bundles.
- Aide F1 (`aide/aide.md`, section *HotSpot HTML*) : une ligne sur la légende.

## Laissé au constructeur

Mise en page exacte du champ dans la boîte (sous la taille), largeur du champ.
