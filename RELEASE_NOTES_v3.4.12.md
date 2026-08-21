# PanoVisu - Release Notes v3.4.12

## Correctif : l'application ne démarrait pas sous macOS ([#17](https://github.com/llang57/editeurPanovisu/issues/17))

Le paquet `.dmg` publié pour macOS ne s'ouvrait pas. Lancé depuis le Terminal, le binaire renvoyait un message déroutant du lanceur Java :

```
Erreur : la méthode principale doit renvoyer une valeur de type void dans la classe {0},
définissez la méthode principale comme suit :
   public static void main(String[] args)
```

### Origine du problème

Le job macOS du workflow de construction empaquetait `editeurpanovisu.EditeurPanovisu` comme classe principale. Cette classe étend `javafx.application.Application`, et une telle classe **ne peut pas servir de point d'entrée** lorsqu'elle est chargée depuis un JAR *shaded*, c'est-à-dire via le classpath plutôt que le module-path.

C'est exactement la raison d'être de la classe `editeurpanovisu.Launcher`, qui enveloppe l'appel dans `Application.launch()`. Windows, l'archive portable Linux et le manifeste du JAR l'utilisent tous ; seul macOS ne le faisait pas.

Le comportement a été reproduit avec le JAR de production :

| Configuration testée | Résultat |
|---|---|
| `--main-class editeurpanovisu.EditeurPanovisu` | échec au démarrage |
| `--main-class editeurpanovisu.Launcher` | démarrage normal |

Le job macOS empaquetait cette classe depuis sa création : le paquet macOS n'a vraisemblablement jamais fonctionné.

### Corrections apportées au paquet macOS

* **Classe principale** : `editeurpanovisu.Launcher` au lieu de `editeurpanovisu.EditeurPanovisu`. C'est le correctif de fond.
* **Répertoire de travail** : ajout de `-Duser.dir=$APPDIR`. L'application ouvre ses ressources par chemin relatif (`new File("css/clair.css")`), or un bundle `.app` lancé depuis le Finder a pour répertoire courant `/` et non le sien. Sans cette option, l'application démarrait puis échouait immédiatement dans `creeEnvironnement`. Sous Windows le problème ne se pose pas : le raccourci passe par `Lancer_EditeurPanovisu.bat`, qui se place dans le bon répertoire avant de lancer la machine virtuelle.
* **Contenu du paquet** : `--input` vise désormais `target/app-input` et non le répertoire de construction entier. `app-input` contient le JAR et les ressources que l'application ouvre par chemin relatif (`panovisu/`, `css/`, `images/`, `aide/`, `configPV/`, `pagesHTML/`, `templates/`, `theme/`, `diaporama/`). Sans cela, l'application n'aurait pas trouvé ses ressources même après correction du démarrage. Le paquet passe au passage de 330 Mo à une taille comparable aux autres plateformes.
* **Options JVM alignées sur Windows** : ajout de `--enable-preview`, `--enable-native-access=ALL-UNNAMED` et `-Xms512m`. La seconde évite un blocage annoncé dans une future version du JDK.
* **Nettoyage** : suppression d'une étape de conversion d'icône sans effet (aucun appel `jpackage` ne passait `--icon`) et d'un bloc de repli strictement identique à l'appel principal.

### Version du paquet macOS

Le nom du `.dmg` reflète maintenant la version réelle. Le job retombait auparavant sur une valeur codée en dur `3.4.0` à chaque construction déclenchée par un tag, d'où l'artefact `EditeurPanovisu-3.4.0.dmg` publié avec la v3.4.10. La version est désormais déduite du nom du JAR, comme le font déjà les scripts Windows et Linux, avec repli sur le nom du tag.

### Vérification

Faute de Mac, la correction a été validée sur Windows en rejouant la commande `jpackage` corrigée, puis en lançant l'application produite :

| Configuration | Résultat |
|---|---|
| Classe principale `EditeurPanovisu` | ne démarre pas |
| Classe principale `Launcher`, lancée hors de son répertoire | démarre puis échoue dans `creeEnvironnement` |
| `Launcher` + `-Duser.dir=$APPDIR`, lancée depuis `C:\` | démarre et s'arrête proprement, aucune erreur |

### Réserve

La correction est fondée sur une reproduction du mécanisme de défaillance sous Windows, mais n'a pas pu être validée sur un Mac. Un retour d'utilisateur sous macOS est bienvenu. À noter que l'application n'est ni signée ni notarisée : macOS peut donc encore refuser de l'ouvrir au premier lancement, auquel cas il faut passer par **Réglages Système → Confidentialité et sécurité → Ouvrir quand même**.

---

## 📅 Historique Récent

### v3.4.10
* Correction de la liste de tri des panoramiques, tronquée au-delà de dix panoramiques ([#16](https://github.com/llang57/editeurPanovisu/issues/16)).

### v3.4.8
* Nouveaux modèles IA (OpenRouter, Ollama) et prompt anti-hallucination.
* Correction des liens d'ancrage de l'aide intégrée (F1).
