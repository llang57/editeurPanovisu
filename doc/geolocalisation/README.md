# 🗺️ Documentation Géolocalisation

Fonctionnalité de géolocalisation interactive avec recherche d'adresse, marqueur déplaçable et mise à jour automatique des coordonnées.

---

## 📁 Fichiers de ce Dossier

### 📄 CORRECTIONS_GEOLOCALISATION.md
**Contenu :** Solutions aux 3 problèmes principaux rencontrés lors de l'implémentation

**Problèmes traités :**
1. ❌ TextFields longitude/latitude non visibles
   - **Solution** : Ajout de Labels avec prompts explicites
   
2. ❌ Erreur "For input string: {JSON}" lors de la validation
   - **Solution** : Priorité à l'objet `marqueur` mis à jour par JavaScript
   - **Détection** : Format JSON détecté et requête alternative utilisée
   
3. ❌ Bouton radio unique pour carte/satellite (pas de retour)
   - **Solution** : Fonction `getNomsLayers()` retournant "*OpenStreetMap|Satellite"

**Fichiers modifiés :**
- `NavigateurOpenLayers.java` : Méthode `recupereCoordonnees()` et `updateCoordinates()`
- `openstreetmap_unified.html` : Fonction `getNomsLayers()` et variable `currentMarker`

---

### 📄 CORRECTIF_MARQUEUR_METZ.md
**Contenu :** Correctif du bug critique où les coordonnées retournées étaient toujours celles de Metz

**Problème :**
- Après recherche et déplacement du marqueur, cliquer sur "Valider" retournait toujours les coordonnées de Metz (position initiale)

**Cause racine :**
```java
// Condition incorrecte
if (marqueur != null && marqueur.getLatitude() != 0.0 && marqueur.getLongitude() != 0.0)
```
- Metz a des coordonnées non-nulles (Lat=49.1169, Lng=6.1752)
- La condition était donc toujours vraie, même pour la position initiale
- Aucune distinction entre "marqueur initialisé" et "marqueur mis à jour par JavaScript"

**Solution :**
- Ajout du flag `marqueurMisAJourParJS` (boolean)
- Activation dans `updateCoordinates()` après mise à jour par JavaScript
- Réinitialisation dans `allerAdresse()` avant nouvelle recherche
- Vérification stricte : `if (marqueurMisAJourParJS && marqueur != null)`

---

### 📄 GEOLOCALISATION_FIXES.md
**Contenu :** Historique des corrections apportées à la géolocalisation

---

### 📄 FONCTIONNALITE_GEOLOCALISATION.md
**Contenu :** Description complète de la fonctionnalité de géolocalisation implémentée

---

### 📄 RESUME_MODIFICATION_GEOLOCALISATION.md
**Contenu :** Résumé des modifications apportées au système de géolocalisation

**Impact :**
- ✅ Validation des coordonnées fonctionne correctement
- ✅ Cohérence entre affichage et données enregistrées
- ✅ Support du déplacement manuel du marqueur

---

## 🔧 Architecture Technique

### Pont JavaScript ↔ Java

```
┌─────────────────────────────────────────────────────────────┐
│                    openstreetmap_unified.html                │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Leaflet Map                                            │ │
│  │  - LocationIQ Geocoding                                │ │
│  │  - Draggable Marker                                    │ │
│  │  - OSM / Satellite Layers                              │ │
│  └────────────────────────────────────────────────────────┘ │
│                            │                                 │
│                            ▼                                 │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ JavaScript Functions                                   │ │
│  │  - allerAdresse(address)                               │ │
│  │  - updateCoordinates(lat, lng)                         │ │
│  │  - setMapType(type)                                    │ │
│  │  - getNomsLayers()                                     │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ window.javaConnector
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              NavigateurOpenLayers.java                       │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ JavaApplication (Inner Class)                          │ │
│  │  - updateCoordinates(lon, lat)                         │ │
│  │  - adresseTrouvee(lon, lat)                            │ │
│  │  - adresseInconnue(msg)                                │ │
│  └────────────────────────────────────────────────────────┘ │
│                            │                                 │
│                            ▼                                 │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Java Methods                                           │ │
│  │  - allerAdresse(strAdresse, zoom)                      │ │
│  │  - recupereCoordonnees()                               │ │
│  │  - setMarqueur(coordonnees)                            │ │
│  │  - changeCarte(type)                                   │ │
│  └────────────────────────────────────────────────────────┘ │
│                            │                                 │
│                            ▼                                 │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ State Management                                       │ │
│  │  - CoordonneesGeographiques marqueur                   │ │
│  │  - boolean marqueurMisAJourParJS                       │ │
│  │  - TextField tfLongitudeRef                            │ │
│  │  - TextField tfLatitudeRef                             │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 Flux de Données

### 1. Recherche d'adresse

```
Utilisateur saisit "Château de Brézé"
         │
         ▼
btnRechercheAdresse.setOnAction()
         │
         ▼
allerAdresse("Château de Brézé", 17)
         │
         ├─► marqueurMisAJourParJS = false  (réinitialisation)
         │
         ▼
executeScript("allerAdresse('Château de Brézé')")
         │
         ▼
[JavaScript] LocationIQ API Geocoding
         │
         ▼
[JavaScript] centerOn(lat, lng, zoom)
         │
         ▼
[JavaScript] Création marqueur déplaçable
         │
         ▼
[JavaScript] updateCoordinates(lat, lng)
         │
         ▼
[Java] updateCoordinates(lon, lat)
         │
         ├─► marqueur.setLongitude(lon)
         ├─► marqueur.setLatitude(lat)
         ├─► marqueurMisAJourParJS = true  ✅
         │
         ▼
Platform.runLater(() -> {
    tfLongitudeRef.setText(toDMS(lon))
    tfLatitudeRef.setText(toDMS(lat))
})
```

### 2. Déplacement du marqueur

```
Utilisateur glisse le marqueur
         │
         ▼
[JavaScript] marker.on('dragend', ...)
         │
         ▼
[JavaScript] updateCoordinates(newLat, newLng)
         │
         ▼
[Java] updateCoordinates(lon, lat)
         │
         ├─► marqueur mis à jour
         ├─► marqueurMisAJourParJS = true  ✅
         │
         ▼
TextFields mis à jour automatiquement
```

### 3. Validation de la position

```
Utilisateur clique "Valider la position"
         │
         ▼
btnRecupereCoordonnees.setOnAction()
         │
         ▼
setMarqueur(recupereCoordonnees())
         │
         ▼
recupereCoordonnees()
         │
         ├─► Vérification : marqueurMisAJourParJS ?
         │
         ├─► ✅ true : return marqueur  (coordonnées mises à jour)
         │
         └─► ❌ false : executeScript (méthode legacy)
```

---

## 📊 Variables d'État Critiques

### NavigateurOpenLayers.java

| Variable | Type | Rôle |
|----------|------|------|
| `marqueur` | `CoordonneesGeographiques` | Stocke les coordonnées actuelles |
| `marqueurMisAJourParJS` | `boolean` | Indique si JavaScript a mis à jour le marqueur |
| `tfLongitudeRef` | `TextField` | Référence au champ Longitude pour mise à jour |
| `tfLatitudeRef` | `TextField` | Référence au champ Latitude pour mise à jour |

### openstreetmap_unified.html

| Variable | Type | Rôle |
|----------|------|------|
| `map` | `L.Map` | Instance Leaflet de la carte |
| `searchMarker` | `L.Marker` | Marqueur de recherche déplaçable |
| `currentMarker` | `L.Marker` | Alias de `searchMarker` (compatibilité) |
| `currentMapType` | `string` | Type de carte actuel ('osm' ou 'satellite') |

---

## 🧪 Tests de Non-Régression

### Test 1 : Recherche Simple
1. Saisir "Tour Eiffel Paris"
2. Cliquer "Rechercher"
3. **Attendu** : Carte centrée, marqueur visible, coordonnées dans TextFields

### Test 2 : Déplacement du Marqueur
1. Rechercher une adresse
2. Glisser le marqueur
3. **Attendu** : TextFields mis à jour en temps réel

### Test 3 : Validation
1. Rechercher une adresse
2. Affiner la position manuellement
3. Cliquer "Valider"
4. **Attendu** : Coordonnées sauvegardées = coordonnées affichées

### Test 4 : Basculement Carte/Satellite
1. Cliquer "Satellite"
2. **Attendu** : Vue satellite affichée
3. Cliquer "OpenStreetMap"
4. **Attendu** : Vue carte affichée

### Test 5 : Nouvelle Recherche après Validation
1. Rechercher "Paris"
2. Valider
3. Rechercher "Lyon"
4. **Attendu** : Nouvelles coordonnées, pas d'erreur

---

## 📚 Ressources Externes

- **Leaflet.js** : https://leafletjs.com/
- **LocationIQ** : https://locationiq.com/
- **OpenStreetMap** : https://www.openstreetmap.org/

---

**Dernière mise à jour :** 11 octobre 2025, 08:45
