# Fonctionnalité de Géolocalisation avec Recherche Interactive

## 📋 Date : 11 octobre 2025

## ✨ Fonctionnalité implémentée

### 🎯 Objectif
Permettre à l'utilisateur de rechercher une adresse, afficher les coordonnées dans des TextFields, et affiner la position avec un marqueur déplaçable.

## 🔧 Modifications apportées

### 1. Fichier HTML (`openstreetmap_unified.html`)

#### Ajout d'un marqueur de recherche déplaçable :
```javascript
var searchMarker = null; // Variable globale pour le marqueur
```

#### Fonction `allerAdresse()` améliorée :
- ✅ Utilise LocationIQ pour le géocodage (5000 requêtes/jour gratuites)
- ✅ Crée un marqueur **déplaçable** (`draggable: true`)
- ✅ Affiche un popup avec les coordonnées
- ✅ Met à jour automatiquement les coordonnées lors du déplacement
- ✅ Notifie Java via `updateCoordinates(lat, lng)`

#### Événement de déplacement :
```javascript
searchMarker.on('dragend', function(event) {
    const position = event.target.getLatLng();
    updateCoordinates(position.lat, position.lng);
    // Met à jour le popup avec les nouvelles coordonnées
});
```

#### Fonction de communication JavaScript → Java :
```javascript
function updateCoordinates(lat, lng) {
    if (window.javaConnector && typeof window.javaConnector.updateCoordinates === 'function') {
        window.javaConnector.updateCoordinates(lng, lat);
    }
}
```

### 2. Classe Java (`NavigateurOpenLayers.java`)

#### Ajout de champs pour les TextFields :
```java
private TextField tfLongitudeRef = null;
private TextField tfLatitudeRef = null;
```

#### Stockage des références dans `afficheNavigateurOpenLayer()` :
```java
this.tfLongitudeRef = tfLongitude;
this.tfLatitudeRef = tfLatitude;
```

#### Nouvelle méthode dans `JavaApplication` :
```java
public void updateCoordinates(double lon, double lat) {
    // Mettre à jour le marqueur
    marqueur.setLongitude(lon);
    marqueur.setLatitude(lat);
    
    // Mettre à jour les TextFields (thread-safe avec Platform.runLater)
    javafx.application.Platform.runLater(() -> {
        if (tfLongitudeRef != null) {
            tfLongitudeRef.setText(CoordonneesGeographiques.toDMS(lon));
        }
        if (tfLatitudeRef != null) {
            tfLatitudeRef.setText(CoordonneesGeographiques.toDMS(lat));
        }
    });
}
```

#### Connexion du pont JavaScript :
```java
JSObject window = (JSObject) navigateurCarte.getWebEngine().executeScript("window");
JavaApplication javaApp = new JavaApplication();
window.setMember("javafx", javaApp);
window.setMember("javaConnector", javaApp); // Pour updateCoordinates
```

## 🚀 Utilisation

### Scénario d'utilisation :

1. **Rechercher une adresse** :
   - L'utilisateur tape "Paris Tour Eiffel" dans le champ de recherche
   - Clique sur le bouton "Recherche" 📍

2. **Résultat automatique** :
   - La carte se centre sur l'adresse trouvée
   - Un marqueur **rouge déplaçable** apparaît
   - Les coordonnées s'affichent dans les TextFields :
     ```
     Longitude: 2°17'30.5"E
     Latitude: 48°51'29.6"N
     ```

3. **Affiner la position** :
   - L'utilisateur **glisse le marqueur** vers la position exacte souhaitée
   - Les TextFields se mettent à jour **automatiquement** en temps réel
   - Un popup affiche les nouvelles coordonnées

4. **Validation** :
   - Les coordonnées sont stockées dans `marqueur`
   - Prêtes à être utilisées pour la géolocalisation du panorama

## 📊 Flux de données

```
┌─────────────────────────────────────────────────────────────┐
│ 1. Utilisateur saisit "Paris"                               │
│    └─> btnRechercheAdresse clicked                          │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│ 2. JavaScript: allerAdresse("Paris")                        │
│    └─> Appel API LocationIQ                                 │
│    └─> Reçoit: lat=48.8566, lng=2.3522                     │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│ 3. JavaScript: Créé marqueur déplaçable                     │
│    └─> centerOn(lat, lng, zoom)                            │
│    └─> searchMarker = L.marker([lat, lng], {draggable})   │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│ 4. JavaScript: updateCoordinates(lat, lng)                  │
│    └─> window.javaConnector.updateCoordinates(lng, lat)    │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│ 5. Java: JavaApplication.updateCoordinates()                │
│    └─> marqueur.setLongitude(lon)                          │
│    └─> marqueur.setLatitude(lat)                           │
│    └─> tfLongitudeRef.setText(CoordonneesGeographiques.toDMS(lon)) │
│    └─> tfLatitudeRef.setText(CoordonneesGeographiques.toDMS(lat))  │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│ 6. Utilisateur déplace le marqueur                          │
│    └─> Événement 'dragend'                                  │
│    └─> Nouvelles coordonnées → Retour à l'étape 4          │
└─────────────────────────────────────────────────────────────┘
```

## ✅ Avantages

1. **Interaction intuitive** : Glisser-déposer naturel
2. **Mise à jour automatique** : TextFields synchronisés en temps réel
3. **Précision** : Affinage visuel de la position
4. **Feedback visuel** : Popup avec coordonnées sur le marqueur
5. **Thread-safe** : Utilisation de `Platform.runLater()`

## 🎨 Interface utilisateur

### Popup du marqueur :
```
┌─────────────────────────────┐
│ Paris, Île-de-France, France│
│ Lat: 48.856600              │
│ Lng: 2.352200               │
│ Glissez le marqueur pour    │
│ affiner la position          │
└─────────────────────────────┘
```

### Popup après déplacement :
```
┌─────────────────────────────┐
│ Position affinée            │
│ Lat: 48.857234              │
│ Lng: 2.351789               │
└─────────────────────────────┘
```

## 🔍 Tests recommandés

1. **Test de recherche** :
   ```
   Recherche: "Metz rue Serpenoise"
   Résultat attendu: Marqueur à Metz avec coordonnées affichées
   ```

2. **Test de déplacement** :
   ```
   Action: Glisser le marqueur de 100m
   Résultat attendu: TextFields mis à jour automatiquement
   ```

3. **Test de recherches multiples** :
   ```
   Recherche 1: "Paris"
   Recherche 2: "Lyon"
   Résultat attendu: Ancien marqueur supprimé, nouveau affiché
   ```

## 📝 Notes techniques

- **Format des coordonnées** : DMS (Degrés, Minutes, Secondes)
  - Exemple : `2°17'30.5"E` pour la longitude
  - Exemple : `48°51'29.6"N` pour la latitude

- **Service de géocodage** : LocationIQ
  - Clé API configurable via `api-keys.properties`
  - Limite gratuite : 5000 requêtes/jour
  - Endpoint : `https://us1.locationiq.com/v1/search.php`

- **Communication JavaScript ↔ Java** :
  - Pont : `window.javaConnector`
  - Méthode : `updateCoordinates(lng, lat)`
  - Thread-safe avec `Platform.runLater()`

## 🚦 Prochaines étapes possibles

1. ✅ **Terminé** : Recherche d'adresse avec LocationIQ
2. ✅ **Terminé** : Marqueur déplaçable
3. ✅ **Terminé** : Mise à jour automatique des TextFields
4. 🔄 **Optionnel** : Géocodage inversé (coordonnées → adresse)
5. 🔄 **Optionnel** : Historique des recherches
6. 🔄 **Optionnel** : Rayon de précision autour du marqueur
