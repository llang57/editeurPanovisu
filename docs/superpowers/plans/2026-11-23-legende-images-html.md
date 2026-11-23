# Légende sous les images des hotspots HTML — plan d'implémentation

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** champ « Légende » facultatif dans la boîte *Insertion d'image* de l'éditeur HTML ; la légende s'affiche sous l'image et la suit dans toutes les positions.

**Architecture:** la construction du fragment HTML inséré sort de `EditeurHTML` vers une classe pure `FragmentImageHTML` (+ enum `PositionImage`), testée sans JavaFX. `EditeurHTML` ne fait plus que lire la boîte de dialogue et appeler la fabrique ; le script de suppression (⚓) devient lui aussi une méthode de la fabrique, testée dans un `WebEngine`.

**Tech Stack:** Java 25 (`--enable-preview`), JavaFX 19 (`HTMLEditor`, `WebEngine`), JUnit 5.

**Spec:** `docs/superpowers/specs/2026-11-20-legende-images-html-design.md`

## Global Constraints

- Sans légende (null, vide ou blanche), le fragment est **identique octet pour octet** au code actuel (`EditeurHTML.java:1509-1543`).
- Aucun changement du format `.pvu` ni de `HotspotHTML.creeHTML`.
- Classes CSS : conteneur `pv-image-legendee`, légende `pv-legende`.
- Style de la légende : `display:block;text-align:center;font-style:italic;font-size:0.85em;`.
- Texte de légende rogné puis échappé HTML : `&`→`&amp;`, `<`→`&lt;`, `>`→`&gt;`, `"`→`&quot;`, `'`→`&#39;`.
- i18n : clés `editeurHTML.legendeImage`, `editeurHTML.legendeImageAide` dans les 6 bundles, échappes `\uXXXX`, fins de ligne du fichier respectées.
- Commandes : `JAVA_HOME="/c/Program Files/Java/jdk-25"`, `mvn … -Dskip.build.increment=true`.

## Review Focus

- Légende contenant `'` : le fragment passe ensuite par `escapeJavaStyleString` puis `executeScript` — une apostrophe non échappée casserait l'attribut ou le JS. Couvert par le test d'échappement (Task 1).
- Légende contenant `:` `;` `[` `]` : doit survivre à l'enregistrement `.pvu` et à la validation (chemins existants). Couvert par la vérification manuelle (Task 2, étape finale).
- Suppression ⚓ après réouverture du projet : l'`<img>` a un nouvel id, enveloppé dans un `<div contenteditable=false>` à l'intérieur du conteneur. Couvert par `SuppressionImageTest.supprimeConteneurApresRechargement` (Task 2).
- Image sans légende : la suppression ne doit pas remonter à un ancêtre quelconque. Couvert par `SuppressionImageTest.imageSansLegende` (Task 2).
- Position « Texte » : l'image légendée ne doit pas couper le paragraphe — garanti par l'absence de balise bloc (`<figure>`, `<div>`) dans le fragment ; assertion dans `FragmentImageHTMLTest.texteAvecLegende`.

---

### Task 1: fabrique pure `FragmentImageHTML`

**Files:**
- Create: `src/editeurpanovisu/PositionImage.java`
- Create: `src/editeurpanovisu/FragmentImageHTML.java`
- Test: `src/test/java/editeurpanovisu/FragmentImageHTMLTest.java`

**Interfaces:**
- Produces:
  - `enum PositionImage { GAUCHE, CENTRE, DROITE, TEXTE }`
  - `static String FragmentImageHTML.ancre(String imageId)` — le `<button class='image-anchor' …>⚓</button>` actuel, à l'identique.
  - `static String FragmentImageHTML.construire(String imageId, String cheminImage, int largeur, PositionImage position, String legende)` — fragment complet (ancre comprise), `cheminImage` sans le préfixe `file:////` (la fabrique l'ajoute).
  - `static String FragmentImageHTML.echappe(String texte)` (package-private).

- [ ] **Step 1: écrire les tests**

  - `sansLegendeIdentique` (paramétré sur les 4 positions, et sur `legende` = `null`, `""`, `"   "`) : `assertEquals(attendu, construire("img_1", "C:/x/a.jpg", 300, pos, legende))`, où `attendu` est recopié **littéralement** du code actuel `EditeurHTML.java:1509-1543` avec `imageId="img_1"`, largeur `300`, chemin `C:/x/a.jpg`.
  - `gaucheAvecLegende` : le fragment commence par `ancre("img_1")`, contient `<span class='pv-image-legendee' style='display:inline-block;float:left;width:300px;margin:0 10px 10px 0;'>`, un `<img data-image-id='img_1' width='300' src='file:////C:/x/a.jpg'/>` **sans** `float`, puis `<span class='pv-legende' style='display:block;text-align:center;font-style:italic;font-size:0.85em;'>© Jorge</span></span><br/>`.
  - `droiteAvecLegende` : idem avec `float:right` et `margin:0 0 10px 10px;`.
  - `centreAvecLegende` : commence par `</p><p style='text-align : center;'>` + ancre, conteneur `display:inline-block;width:300px;`, finit par `</span></p><p>`.
  - `texteAvecLegende` : conteneur `display:inline-block;width:300px;vertical-align:bottom;margin:0 10px 0 0;` ; `assertFalse` sur `<figure`, `<div`, `<p`.
  - `legendeEchappee` : légende `  <b>"Tom" & l'été>  ` → contenu de `pv-legende` = `&lt;b&gt;&quot;Tom&quot; &amp; l&#39;été&gt;`.

- [ ] **Step 2: lancer, constater l'échec**

  Run: `mvn test -Dskip.build.increment=true -Dtest=FragmentImageHTMLTest`
  Expected: échec de compilation (classes absentes).

- [ ] **Step 3: implémenter `PositionImage` et `FragmentImageHTML`**

  Branche sans légende : recopie exacte des quatre branches actuelles. Branche avec légende : `ancre + conteneur(img sans style de position + légende)`, enveloppe centre conservée, `<br/>` final pour gauche/droite comme aujourd'hui.

- [ ] **Step 4: lancer, constater le succès**

  Run: `mvn test -Dskip.build.increment=true -Dtest=FragmentImageHTMLTest`
  Expected: tous verts.

- [ ] **Step 5: commit**

  `feat(editeurHTML): fabrique du fragment image avec legende (#18)`

### Task 2: brancher la boîte de dialogue, la suppression, i18n et aide

**Files:**
- Modify: `src/editeurpanovisu/FragmentImageHTML.java` (ajout `scriptSuppression`)
- Modify: `src/editeurpanovisu/EditeurHTML.java` (`afficheConfigImage` ~1294-1555, `supprimerImageDepuisJS` ~1807)
- Modify: `src/editeurpanovisu/i18n/PanoVisu*.properties` (6 fichiers)
- Modify: `aide/aide.md` (section *HotSpot HTML*)
- Test: `src/test/java/editeurpanovisu/SuppressionImageTest.java`

**Interfaces:**
- Consumes: `FragmentImageHTML.construire`, `FragmentImageHTML.ancre`, `PositionImage` (Task 1).
- Produces: `static String FragmentImageHTML.scriptSuppression(String imageId)` — JS qui retire l'ancre, puis le conteneur `.pv-image-legendee` le plus proche de l'image s'il existe, sinon l'image seule.

- [ ] **Step 1: écrire `SuppressionImageTest`** (toolkit JavaFX démarré en `@BeforeAll`, `assumeTrue` sinon — même motif que `ModeSourisModeleInterfaceTest`). Chaque test charge un HTML dans un `WebEngine` sur le fil FX, attend `SUCCEEDED`, exécute `scriptSuppression`, puis lit `document.body.innerHTML`.
  - `supprimeConteneurEtLegende` : HTML = `<p>avant</p>` + `construire("img_1", …, GAUCHE, "© Jorge")` + `<p>après</p>` → plus de `pv-image-legendee`, plus de `© Jorge`, plus d'`image-anchor`, `avant` et `après` présents.
  - `supprimeConteneurApresRechargement` : conteneur dont l'`<img data-image-id="img_2">` est enveloppé dans `<div contenteditable='false'>` + ancre (forme produite par `ajouterAncresAuxImages`) → conteneur retiré.
  - `imageSansLegende` : `<p>a<img data-image-id='img_3' src='x'/>b</p>` → l'image disparaît, le `<p>` et `a`, `b` restent.

- [ ] **Step 2: lancer, constater l'échec** — `mvn test -Dskip.build.increment=true -Dtest=SuppressionImageTest` ; Expected: échec de compilation (`scriptSuppression` absent).

- [ ] **Step 3: implémenter `scriptSuppression`** (via `img.closest('.pv-image-legendee')`), puis l'utiliser dans `supprimerImageDepuisJS`.

- [ ] **Step 4: lancer, constater le succès** — même commande ; Expected: 3 verts.

- [ ] **Step 5: boîte de dialogue** — dans `afficheConfigImage`, une `HBox` « Légende » (`Label` 120 px gras comme `lblLargeurImage`, `TextField` extensible, `promptText` = `editeurHTML.legendeImageAide`, `lblLegende.setLabelFor(tf)`), insérée entre `hboxTaille` et `spImageInserer`. `btnValideImage` remplace les quatre branches par `FragmentImageHTML.construire(imageId, strNomFichierImage, (int) slLargeurImage.getValue(), position, tfLegende.getText())`.

- [ ] **Step 6: i18n** — dans les 6 bundles, après la clé `editeurHTML.tailleImage` :
  | bundle | legendeImage | legendeImageAide |
  |---|---|---|
  | défaut / `_en` | Caption | Title, © author… (optional) |
  | `_fr` | L\u00e9gende | Titre, \u00a9 auteur\u2026 (facultatif) |
  | `_de` | Bildunterschrift | Titel, \u00a9 Autor\u2026 (optional) |
  | `_es_ES` | Pie de foto | T\u00edtulo, \u00a9 autor\u2026 (opcional) |
  | `_pt` | Legenda | T\u00edtulo, \u00a9 autor\u2026 (opcional) |

- [ ] **Step 7: aide** — sous « Images embarquées » de la section *HotSpot HTML* : une sous-ligne sur la légende facultative (sous l'image, suit sa position, modifiable dans le texte, retirée avec l'image par ⚓).

- [ ] **Step 8: suite complète** — `mvn test -Dskip.build.increment=true` ; Expected: BUILD SUCCESS, aucun échec.

- [ ] **Step 9: vérification manuelle (à faire par l'utilisateur, l'application étant graphique)** — insérer une image légendée `© Jorge : test; [1]` dans chaque position, valider, enregistrer, rouvrir le projet, supprimer une image par ⚓, exporter et ouvrir la page.

- [ ] **Step 10: commit** — `feat(editeurHTML): legende facultative sous les images (#18)`
