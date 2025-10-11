# Corrections de l'affichage de la carte de géolocalisation

## 🐛 Problème identifié

La carte OpenStreetMap/Leaflet.js présentait des problèmes d'affichage :
- **Tuiles qui se chevauchent** : Les tuiles de la carte ne s'alignaient pas correctement
- **Trous dans la carte** : Certaines zones ne chargeaient pas les tuiles
- **Cause** : Initialisation prématurée de Leaflet avant le chargement complet du DOM et dimensionnement incorrect

## ✅ Corrections appliquées

### 1. **Initialisation différée de la carte**
```javascript
// AVANT : Initialisation immédiate
const map = L.map('map', { ... });

// APRÈS : Initialisation différée
let map = null;
function initMap() {
    map = L.map('map', { ... });
    setTimeout(() => map.invalidateSize(), 100);
}

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(() => initMap(), 100);
});
```

### 2. **Configuration optimale des tuiles**
Ajout de paramètres pour éviter les problèmes de rendu :
```javascript
L.tileLayer(url, {
    tileSize: 256,           // Taille standard des tuiles
    updateWhenIdle: false,   // Mise à jour continue
    updateWhenZooming: false,// Pas d'attente pendant le zoom
    keepBuffer: 2            // Garde 2 rangées de tuiles en mémoire
});
```

### 3. **Gestion du redimensionnement**
```javascript
window.addEventListener('resize', function() {
    if (map) {
        setTimeout(() => map.invalidateSize(), 100);
    }
});
```

### 4. **Protection des fonctions de compatibilité**
Toutes les fonctions vérifient maintenant que la carte est initialisée :
```javascript
function allerCoordonnees(longitude, latitude, zoom) {
    if (!map) {
        console.warn('⚠️ Carte non initialisée');
        return;
    }
    map.setView([latitude, longitude], zoom || 13);
    setTimeout(() => map.invalidateSize(), 100);
}
```

## 📋 Fonctions de compatibilité OpenLayers → Leaflet

| Fonction OpenLayers | Fonction Leaflet | Description |
|---------------------|------------------|-------------|
| `allerCoordonnees(lon, lat, zoom)` | `map.setView([lat, lon], zoom)` | Navigate to coordinates |
| `getCoordonnees()` | `map.getCenter()` | Get map center |
| `ajouteMarqueur(id, lon, lat, html)` | `L.marker([lat, lon]).addTo(map)` | Add marker |
| `enleveMarqueur(id)` | `map.removeLayer(marker)` | Remove marker |
| `choixZoom(zoom)` | `map.setZoom(zoom)` | Change zoom level |
| `getNomsLayers()` | Custom function | Get layer names |
| `changeLayer(type)` | Custom function | Switch map type |
| `setBingApiKey(key)` | No-op | Not needed (Esri is free) |

## 🗺️ Sources de tuiles utilisées

### OpenStreetMap Standard (par défaut)
```javascript
url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
attribution: '© OpenStreetMap contributors'
maxZoom: 19
```

### Esri World Imagery (Satellite)
```javascript
url: 'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
attribution: '© Esri, Maxar, Earthstar Geographics'
maxZoom: 19
```

**Avantages :**
- ✅ 100% gratuit, aucune clé API requise
- ✅ Pas de quota de requêtes
- ✅ Fiable et performant
- ✅ Mise à jour régulière

## 🧪 Tests recommandés

1. **Ouvrir la fenêtre de géolocalisation** dans l'éditeur PanoVisu
2. **Vérifier l'affichage** : Les tuiles doivent s'afficher sans chevauchement ni trous
3. **Changer de type de carte** : Basculer entre OSM et Satellite
4. **Zoomer/Dézoomer** : Les tuiles doivent se charger proprement
5. **Ajouter un marqueur** : Cliquer sur la carte pour placer un marqueur
6. **Redimensionner la fenêtre** : La carte doit s'adapter automatiquement

## 📝 Notes techniques

- **Leaflet version** : 1.9.4 (chargé via CDN unpkg)
- **Projection** : EPSG:3857 (Web Mercator) - standard pour les cartes web
- **Format des coordonnées** : WGS84 (latitude/longitude décimale)
- **Ordre des paramètres** : Leaflet utilise `[lat, lng]` contrairement à OpenLayers qui utilise `[lng, lat]`

## 🔧 Débogage

En cas de problème, ouvrir la console JavaScript du navigateur (F12) :
- ✅ `🔧 DOM chargé, initialisation de la carte...`
- ✅ `✅ Carte Leaflet initialisée`
- ✅ `✅ API de compatibilité OpenLayers chargée`

Messages d'avertissement utiles :
- ⚠️ `Carte non initialisée pour [fonction]` → La carte n'est pas encore prête

## 🎯 Prochaines étapes

- [x] Corriger l'affichage des tuiles
- [ ] Tester les marqueurs et popups
- [ ] Tester la récupération des coordonnées
- [ ] Tester le basculement entre types de carte
- [ ] Vérifier la compatibilité avec toutes les fonctionnalités de l'éditeur
