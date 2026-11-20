# Mode souris au démarrage — conception courte

Origine : discussion GitHub #20 (PhotoDidacte). Approuvé le 2026-11-20.

## Intention

Permettre à l'auteur d'une visite de choisir le mode de déplacement souris actif au chargement :
**Classique** (glisser = vitesse, `iMode = 1`, comportement actuel) ou **StreetView** (glisser = saisir
l'image, `iMode = 0`). Aujourd'hui le viewer démarre toujours en Classique ; seul le visiteur peut
basculer, et uniquement si un bouton souris est affiché.

## Objectifs / non-objectifs

- Objectif : un réglage par projet, sauvegardé dans le modèle d'interface, exporté dans le XML.
- Objectif : les projets et modèles existants s'ouvrent en Classique, sans changement de rendu.
- Non-objectif : changer le forçage du mode StreetView sur écran tactile (`estTactile()`), conservé.
- Anti-objectif : réinitialiser le mode à chaque changement de scène (le choix du visiteur doit tenir).

## Approches

1. **Attribut sur `<boutons>`** (proposé initialement) — écarté : `<boutons>` n'est écrit que si la barre
   classique est visible ; sans barre, le réglage serait perdu alors que c'est le cas où il compte le plus.
2. **Attribut `modeSouris` sur la racine `<pano>`** — retenu : toujours écrit, lu dans `chargeXML`.

## Conception

- **Modèle** (`GestionnaireInterfaceController`) : champ `strModeSourisDemarrage` (`"classique"` |
  `"streetview"`, défaut `"classique"`), clé `modeSourisDemarrage=` dans `strGetTemplate()` /
  `setTemplate()`. Remis au défaut avant lecture d'un modèle pour qu'un ancien projet ne garde pas la
  valeur du projet précédent.
- **UI** : un `ComboBox` « Mode souris au démarrage » dans chacun des deux panneaux de barre
  (classique et personnalisée), liés bidirectionnellement (une seule valeur), placés hors du sous-panneau
  désactivé quand la barre est masquée. Modification → projet marqué non sauvegardé.
- **Export** (`EditeurPanovisu`, balise `<pano`) : `modeSouris="classique|streetview"`.
- **Viewer** (`panovisu.js`) : dans `chargeXML`, une seule fois (`bModeSourisInitialise`), si l'appareil
  n'est pas tactile, `iMode = (modeSouris === "streetview") ? 0 : 1` et curseur `grab` en mode 0.
  `creeImagesboutons()` et la télécommande choisissent l'icône (`souris.png` / `souris2.png`) selon
  `iMode` — corrige au passage l'icône remise à `souris.png` à chaque changement de scène.
- **i18n** : libellés dans les 6 bundles.

## Laissé au constructeur

Position exacte du ComboBox dans chaque panneau (layout absolu), texte précis des libellés.
