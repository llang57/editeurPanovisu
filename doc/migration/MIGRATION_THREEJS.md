# Migration Three.js r71 vers r180

## État actuel
- **Version actuelle** : Three.js r71 (2015)
- **Version disponible** : Three.js r180 (Janvier 2025)
- **Écart** : 109 révisions, soit ~10 ans de développement

## Raisons de la mise à jour

### 1. Performance
- Améliorations significatives du moteur de rendu WebGL
- Meilleure gestion de la mémoire
- Optimisation du rendu pour les appareils mobiles

### 2. Fonctionnalités
- Support WebGL 2.0
- Nouvelles techniques de rendu (PBR, IBL)
- Meilleurs outils de débogage
- Support amélioré des formats de fichiers

### 3. Compatibilité
- Support des navigateurs modernes
- Corrections de bugs de sécurité
- Compatibilité avec les derniers standards Web

### 4. Maintenance
- Documentation mise à jour
- Communauté active
- Exemples récents

## Changements majeurs à prévoir

### API Breaking Changes

#### 1. Constructeurs et Géométries
```javascript
// Ancien (r71)
var geometry = new THREE.SphereGeometry(500, 60, 40);

// Nouveau (r180) - Identique, mais BufferGeometry par défaut
var geometry = new THREE.SphereGeometry(500, 60, 40);
```

#### 2. Matériaux
```javascript
// Ancien (r71)
var material = new THREE.MeshBasicMaterial({
    map: texture,
    side: THREE.BackSide
});

// Nouveau (r180) - API similaire mais propriétés améliorées
var material = new THREE.MeshBasicMaterial({
    map: texture,
    side: THREE.BackSide
});
```

#### 3. Renderer
```javascript
// Ancien (r71)
renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);

// Nouveau (r180) - setSize accepte maintenant updateStyle
renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.setPixelRatio(window.devicePixelRatio); // Nouveau pour écrans Retina
```

#### 4. Camera
```javascript
// Ancien (r71)
camera = new THREE.PerspectiveCamera(fov, aspect, near, far);
camera.target = new THREE.Vector3(0, 0, 0);

// Nouveau (r180) - Plus de camera.target
camera = new THREE.PerspectiveCamera(fov, aspect, near, far);
// Utiliser lookAt() directement
camera.lookAt(0, 0, 0);
```

### Fonctionnalités supprimées

1. **THREE.Geometry** → Remplacé par **THREE.BufferGeometry**
2. **THREE.Face3** → Plus nécessaire avec BufferGeometry
3. **THREE.Projector** → Déplacé dans examples/jsm/
4. **THREE.SceneUtils.createMultiMaterialObject** → Supprimé

## Plan de migration

### Phase 1 : Préparation (1-2h)
1. ✅ Télécharger Three.js r180 depuis https://github.com/mrdoob/three.js/releases
2. ✅ Sauvegarder la version actuelle (r71)
3. ✅ Créer une branche Git pour la migration
4. ✅ Lire le guide de migration officiel

### Phase 2 : Installation (15 min)
1. ✅ Remplacer `three.min.js` dans `panovisu/libs/three.js/`
2. ✅ Vérifier les dépendances (OrbitControls, etc.)

### Phase 3 : Tests et corrections (2-4h)
1. ✅ Tester le chargement de base
2. ✅ Vérifier l'affichage des panoramiques
3. ✅ Tester la navigation (rotation, zoom)
4. ✅ Tester les hotspots
5. ✅ Tester la compatibilité navigateurs
6. ✅ Corriger les erreurs de console

### Phase 4 : Optimisation (1-2h)
1. ✅ Activer les optimisations r180 (pixelRatio, antialias)
2. ✅ Profiler les performances
3. ✅ Ajuster les paramètres de rendu

### Phase 5 : Validation (1h)
1. ✅ Tests sur plusieurs navigateurs (Chrome, Firefox, Safari, Edge)
2. ✅ Tests mobiles (iOS, Android)
3. ✅ Validation des performances

## Fichiers concernés

### Principaux
- `panovisu/libs/three.js/three.min.js` (à remplacer)
- `panovisu/panovisu.js` (modifications API si nécessaires)

### Secondaires (vérification)
- Tous les fichiers utilisant THREE.*
- Templates HTML utilisant Three.js

## Risques et mitigations

### Risques élevés
1. **Incompatibilité API** → Tests approfondis
2. **Performance dégradée** → Profiling avant/après
3. **Bugs visuels** → Tests visuels systématiques

### Risques moyens
1. **Navigateurs anciens** → Documentation des prérequis
2. **Mobile** → Tests sur appareils réels

### Risques faibles
1. **Taille du fichier** → Minification et gzip
2. **Temps de chargement** → CDN et mise en cache

## Liens utiles

- **Site officiel** : https://threejs.org/
- **GitHub** : https://github.com/mrdoob/three.js/
- **Documentation** : https://threejs.org/docs/
- **Guide de migration** : https://github.com/mrdoob/three.js/wiki/Migration-Guide
- **Releases** : https://github.com/mrdoob/three.js/releases
- **Exemples** : https://threejs.org/examples/

## Estimation temps total
**8 à 10 heures** (selon la complexité des modifications API)

## Recommandations

1. **Priorité** : MOYENNE - La version r71 fonctionne mais est obsolète
2. **Timing** : À faire après la stabilisation des fonctionnalités actuelles
3. **Approche** : Progressive avec tests à chaque étape
4. **Sauvegarde** : Obligatoire avant toute modification

## Notes

- La plupart du code devrait fonctionner sans modification majeure
- Les principales différences concernent BufferGeometry (plus performant)
- Three.js maintient une bonne rétrocompatibilité dans l'ensemble
- Les gains de performance justifient largement la migration
