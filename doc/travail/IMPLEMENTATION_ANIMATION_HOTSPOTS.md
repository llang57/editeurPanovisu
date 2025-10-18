# Implémentation des animations de hotspots

## Date
17 octobre 2025 - Build 2682

## Objectif
Ajouter deux nouvelles options pour tous les types de hotspots :
1. **Hotspot animé** : Animation de type "pulse" (extensible pour d'autres effets)
2. **Agrandissement au survol** : Agrandissement 1.5x avec centre fixe

## Modifications apportées

### 1. Backend Java - Génération XML

#### EditeurPanovisu.java
Modifications dans la méthode `genereVisite()` pour générer l'attribut `agranditSurvol` dans le XML :

- **Ligne 2417-2422** : Hotspots panoramiques
  ```java
  String strAgranditSurvol = (HS.isAgranditSurvol()) ? "true" : "false";
  strContenuFichier += "           agranditSurvol=\"" + strAgranditSurvol + "\"\n"
  ```

- **Ligne 2437-2465** : Hotspots HTML
  ```java
  String strAgranditSurvol = (HS.isAgranditSurvol()) ? "true" : "false";
  strContenuFichier += "           agranditSurvol=\"" + strAgranditSurvol + "\"\n"
  ```

- **Ligne 2536-2559** : Hotspots images
  ```java
  String strAgranditSurvol = (HS.isAgranditSurvol()) ? "true" : "false";
  strContenuFichier += "           agranditSurvol=\"" + strAgranditSurvol + "\"\n"
  ```

**Note** : Les hotspots diaporama ne sont pas concernés (type spécial sans cette propriété).

### 2. Frontend JavaScript - Parsing XML

#### panovisu.js - Ligne 5875
Ajout de la lecture de l'attribut `agranditSurvol` depuis le XML :

```javascript
arrPointsInteret[i].anime = $(this).attr('anime') || "false";
arrPointsInteret[i].agranditSurvol = $(this).attr('agranditSurvol') || "false";
```

### 3. Frontend JavaScript - Création des hotspots

#### panovisu.js - Ligne 5251-5275
Modification de la fonction `creeHotspot()` pour appliquer les classes CSS :

```javascript
function creeHotspot(num) {
    // Créer le div du hotspot
    var $hotspot = $("<div>", { 
        id: "HS-" + num + "-" + iNumPano, 
        class: "hotSpots" 
    });
    
    // Ajouter les classes CSS selon les propriétés
    if (arrPointsInteret[num].anime === "true") {
        $hotspot.addClass("hotspot-anime-pulse");
    }
    if (arrPointsInteret[num].agranditSurvol === "true") {
        $hotspot.addClass("hotspot-agrandit");
    }
    
    $hotspot.appendTo("#panovisu-" + iNumPano);
    
    $("<img>", { 
        id: "imgHS-" + num + "-" + iNumPano, 
        width: arrPointsInteret[num].taille, 
        src: arrPointsInteret[num].image, 
        title: arrPointsInteret[num].info 
    }).appendTo("#HS-" + num + "-" + iNumPano);
    
    numHotspot += 1;
}
```

### 4. Styles CSS

#### panovisu/css/panovisu.css - Fin du fichier
Ajout des animations et effets :

```css
/* ============================================
   Animations des hotspots
   ============================================ */

/* Animation pulse - effet de pulsation */
@keyframes pulse {
    0%, 100% {
        transform: scale(1);
        opacity: 1;
    }
    50% {
        transform: scale(1.15);
        opacity: 0.8;
    }
}

.hotspot-anime-pulse {
    animation: pulse 2s ease-in-out infinite;
    transform-origin: center center;
}

/* Agrandissement au survol - 1.5x avec centre fixe */
.hotspot-agrandit {
    transition: transform 0.3s ease;
    transform-origin: center center;
}

.hotspot-agrandit:hover {
    transform: scale(1.5) !important;
    z-index: 1000;
}

/* Combinaison des deux effets */
.hotspot-anime-pulse.hotspot-agrandit:hover {
    animation: none;
    transform: scale(1.5) !important;
}
```

## Fonctionnement

### Animation "Pulse"
- Effet de pulsation continu (scale de 1 à 1.15)
- Changement d'opacité de 1 à 0.8
- Durée : 2 secondes
- Boucle infinie
- Centre fixe grâce à `transform-origin: center center`

### Agrandissement au survol
- Agrandissement de 1.5x au survol de la souris
- Transition fluide de 0.3 seconde
- Centre fixe grâce à `transform-origin: center center`
- Z-index élevé (1000) pour passer au-dessus des autres hotspots

### Combinaison des deux effets
- Si les deux options sont activées :
  - Animation pulse normale
  - Au survol : l'animation s'arrête et l'agrandissement 1.5x s'applique
  - Quand la souris sort : retour à l'animation pulse

## Architecture extensible

Le système est conçu pour être facilement étendu avec d'autres types d'animations :

1. **Ajouter un nouveau type d'animation CSS** :
   ```css
   @keyframes bounce {
       0%, 100% { transform: translateY(0); }
       50% { transform: translateY(-10px); }
   }
   
   .hotspot-anime-bounce {
       animation: bounce 1s ease-in-out infinite;
       transform-origin: center center;
   }
   ```

2. **Modifier le JavaScript** pour appliquer la classe selon un attribut :
   ```javascript
   if (arrPointsInteret[num].typeAnimation === "bounce") {
       $hotspot.addClass("hotspot-anime-bounce");
   }
   ```

3. **Ajouter l'option dans l'interface Java** (déjà prévu avec la propriété `anime`)

## Tests recommandés

1. ✅ Compilation Java réussie (build 2682)
2. ⏳ Créer un projet avec différents hotspots
3. ⏳ Activer l'animation sur certains hotspots
4. ⏳ Activer l'agrandissement sur d'autres
5. ⏳ Tester la combinaison des deux options
6. ⏳ Générer la visite et vérifier les effets dans le navigateur
7. ⏳ Tester sur différents navigateurs (Chrome, Firefox, Edge, Safari)
8. ⏳ Vérifier les performances avec plusieurs hotspots animés

## Fichiers modifiés

### Java (Backend)
- `src/editeurpanovisu/EditeurPanovisu.java`
  - Lignes 2417-2422 : Hotspots panoramiques
  - Lignes 2437-2465 : Hotspots HTML
  - Lignes 2536-2559 : Hotspots images

### JavaScript (Frontend)
- `panovisu/panovisu.js`
  - Ligne 5875 : Parsing XML
  - Lignes 5251-5275 : Fonction creeHotspot()

### CSS (Styles)
- `panovisu/css/panovisu.css`
  - Fin du fichier : Animations et effets

## Compatibilité navigateurs

Les animations CSS utilisées sont compatibles avec :
- ✅ Chrome/Edge (moteur Chromium)
- ✅ Firefox
- ✅ Safari
- ✅ Opera

Les propriétés utilisées (`@keyframes`, `transform`, `transition`, `transform-origin`) sont supportées par tous les navigateurs modernes.

## Notes importantes

1. **HotspotDiaporama** : Ce type de hotspot n'a pas reçu la propriété `agranditSurvol` car c'est un type spécial qui n'hérite pas de la classe de base.

2. **Propriété `anime` existante** : La propriété `bAnime` existait déjà dans les classes Java mais n'était pas implémentée côté JavaScript. Cette implémentation la rend maintenant fonctionnelle.

3. **Centre fixe** : L'utilisation de `transform-origin: center center` garantit que l'agrandissement et l'animation se font depuis le centre du hotspot, évitant les décalages visuels.

4. **Performance** : Les animations CSS sont gérées par le GPU, ce qui assure de bonnes performances même avec plusieurs hotspots animés.

## Suite du développement

Pour étendre le système avec d'autres types d'animations :

1. Ajouter un select dans l'interface Java pour choisir le type d'animation
2. Modifier la propriété `bAnime` en `strTypeAnimation` (valeurs : "none", "pulse", "bounce", "rotate", etc.)
3. Mettre à jour le XML pour stocker le type d'animation
4. Ajouter les keyframes CSS correspondants
5. Modifier `creeHotspot()` pour appliquer la classe appropriée

## Commit
```
feat: Implémentation des animations de hotspots (pulse + agrandissement survol)

- Génération attribut agranditSurvol dans XML (hotspots pano/image/HTML)
- Parsing agranditSurvol dans panovisu.js
- Application classes CSS selon propriétés (anime + agranditSurvol)
- Styles CSS : animation pulse + agrandissement 1.5x au survol
- Architecture extensible pour futurs types d'animations
- Centre fixe via transform-origin
- Build 2682
```
