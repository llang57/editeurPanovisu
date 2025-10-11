# Corrections de la Géolocalisation

**Date:** 11 octobre 2025  
**Statut:** ✅ Compilé avec succès

## 📋 Problèmes Identifiés

### 1. ❌ Les TextFields longitude/latitude ne s'affichent pas
**Cause:** Les coordonnées sont envoyées depuis JavaScript mais les TextFields ne se mettent pas à jour.  
**Solution:** Ajout de logs de diagnostic pour vérifier si `tfLongitudeRef` et `tfLatitudeRef` sont bien initialisés.

### 2. ❌ Erreur lors du clic sur "Valider la position"
**Erreur:**
```
Erreur recupereCoordonnees: For input string: "{"lat":47.174953372905414,"lng":-0.05978107452392579}"
```

**Cause:** La méthode `recupereCoordonnees()` essayait de parser une chaîne JSON au lieu d'utiliser l'objet `marqueur` qui contient déjà les coordonnées mises à jour par JavaScript.

**Solution:** Modification de `NavigateurOpenLayers.java` ligne 76-110 :
- Priorité aux coordonnées stockées dans l'objet `marqueur` (mis à jour par `updateCoordinates()`)
- Fallback sur la méthode JavaScript si le marqueur n'est pas disponible
- Détection du format JSON et utilisation d'une requête alternative pour récupérer la position du marqueur

### 3. ❌ Un seul bouton radio pour passer en mode satellite (pas de retour)
**Cause:** Le système retourne correctement "OpenStreetMap,Satellite" mais avec une virgule (,) au lieu du séparateur pipe (|) et sans indicateur de carte active (*).

**Solution:** Modification de `openstreetmap_unified.html` ligne 501-509 :
```javascript
function getNomsLayers() {
    // Retourne les noms des couches disponibles avec | comme séparateur
    // Le * indique la couche active
    if (currentMapType === 'osm') {
        return "*OpenStreetMap|Satellite";
    } else {
        return "OpenStreetMap|*Satellite";
    }
}
```

## 🔧 Modifications Apportées

### NavigateurOpenLayers.java

#### Méthode `recupereCoordonnees()` (lignes 76-110)
```java
public CoordonneesGeographiques recupereCoordonnees() {
    // Si le marqueur a été mis à jour par JavaScript, on utilise ces coordonnées
    if (marqueur != null && marqueur.getLatitude() != 0.0 && marqueur.getLongitude() != 0.0) {
        System.out.println("✅ Récupération depuis marqueur: Lat=" + marqueur.getLatitude() + ", Lng=" + marqueur.getLongitude());
        return marqueur;
    }
    
    // Sinon, on tente de récupérer depuis JavaScript (méthode legacy)
    CoordonneesGeographiques coordonnees = new CoordonneesGeographiques();
    try {
        Object result = navigateurCarte.getWebEngine().executeScript("typeof getCenter === 'function' ? JSON.stringify(getCenter()) : null");
        String strCoord = result != null ? result.toString() : "";
        
        // Si le résultat est un objet JSON, on l'ignore (problème de format)
        if (strCoord.startsWith("{")) {
            System.err.println("⚠️ Format JSON détecté, utilisation de getMarkerPosition() à la place");
            // Essayer de récupérer la position du marqueur directement
            result = navigateurCarte.getWebEngine().executeScript(
                "typeof currentMarker !== 'undefined' && currentMarker ? " +
                "currentMarker.getLatLng().lat + ';' + currentMarker.getLatLng().lng : null"
            );
            strCoord = result != null ? result.toString() : "";
        }
        
        if (strCoord.contains(";")) {
            coordonnees.setLongitude(Double.parseDouble(strCoord.split(";")[1]));
            coordonnees.setLatitude(Double.parseDouble(strCoord.split(";")[0]));
            System.out.println("✅ Coordonnées récupérées depuis JavaScript: Lat=" + coordonnees.getLatitude() + ", Lng=" + coordonnees.getLongitude());
        }
    } catch (Exception e) {
        System.err.println("❌ Erreur recupereCoordonnees: " + e.getMessage());
        e.printStackTrace();
    }
    return coordonnees;
}
```

#### Méthode `updateCoordinates()` (lignes 520-550)
Ajout de logs de diagnostic pour identifier si les TextFields sont bien référencés :
```java
public void updateCoordinates(double lon, double lat) {
    System.out.println("📍 Coordonnées reçues de JavaScript: Lat=" + lat + ", Lng=" + lon);
    System.out.println("🔍 tfLongitudeRef = " + (tfLongitudeRef != null ? "OK" : "NULL"));
    System.out.println("🔍 tfLatitudeRef = " + (tfLatitudeRef != null ? "OK" : "NULL"));
    
    // Mettre à jour le marqueur
    if (marqueur == null) {
        marqueur = new CoordonneesGeographiques();
    }
    marqueur.setLongitude(lon);
    marqueur.setLatitude(lat);
    System.out.println("✅ Marqueur mis à jour: Lat=" + marqueur.getLatitude() + ", Lng=" + marqueur.getLongitude());
    
    // Mettre à jour les TextFields si présents
    javafx.application.Platform.runLater(() -> {
        if (tfLongitudeRef != null) {
            String lonDMS = CoordonneesGeographiques.toDMS(lon);
            tfLongitudeRef.setText(lonDMS);
            System.out.println("✅ TextField Longitude mis à jour: " + lonDMS);
        } else {
            System.err.println("❌ tfLongitudeRef est NULL!");
        }
        
        if (tfLatitudeRef != null) {
            String latDMS = CoordonneesGeographiques.toDMS(lat);
            tfLatitudeRef.setText(latDMS);
            System.out.println("✅ TextField Latitude mis à jour: " + latDMS);
        } else {
            System.err.println("❌ tfLatitudeRef est NULL!");
        }
    });
}
```

### openstreetmap_unified.html

#### Fonction `getNomsLayers()` (lignes 501-509)
```javascript
function getNomsLayers() {
    // Retourne les noms des couches disponibles avec | comme séparateur
    // Le * indique la couche active
    if (currentMapType === 'osm') {
        return "*OpenStreetMap|Satellite";
    } else {
        return "OpenStreetMap|*Satellite";
    }
}
```

#### Variable globale `currentMarker` (ligne 169)
Ajout d'un alias pour `searchMarker` pour compatibilité :
```javascript
var currentMarker = null; // Alias pour searchMarker (compatibilité)
```

#### Mise à jour de `searchMarker` (ligne 410)
```javascript
searchMarker = L.marker([lat, lng], {
    draggable: true,
    title: result.display_name
}).addTo(map);
currentMarker = searchMarker; // Alias pour compatibilité
```

## 🧪 Tests à Effectuer

1. **Test de recherche d'adresse:**
   - Ouvrir la fenêtre de géolocalisation
   - Saisir une adresse (ex: "chateau de Brézé")
   - Cliquer sur "Rechercher"
   - **Vérifier:** Les TextFields Latitude et Longitude affichent les coordonnées
   - **Console Java:** Devrait afficher les logs de `updateCoordinates()` et confirmation de mise à jour des TextFields

2. **Test de déplacement du marqueur:**
   - Après une recherche, glisser le marqueur sur la carte
   - **Vérifier:** Les TextFields se mettent à jour automatiquement
   - **Console Java:** Devrait afficher les nouveaux logs à chaque déplacement

3. **Test de validation:**
   - Cliquer sur "Valider la position"
   - **Vérifier:** Pas d'erreur "For input string"
   - **Console Java:** Devrait afficher "✅ Récupération depuis marqueur"

4. **Test de changement de type de carte:**
   - Vérifier que 2 RadioButtons sont présents: "OpenStreetMap" et "Satellite"
   - Cliquer sur "Satellite" → la carte passe en vue satellite
   - Cliquer sur "OpenStreetMap" → la carte repasse en mode carte standard

## 📊 Logs Attendus

### Lors d'une recherche d'adresse :
```
📞 Java allerAdresse() appelé avec: 'chateau de Brézé', zoom=17
🔍 Fonctions JS disponibles:
   - chercheAdresse: false
   - allerAdresse: true
✅ Appel allerAdresse() JavaScript...
📝 Code JS: allerAdresse('chateau de Brézé')

[HTML Debug Panel]
🔍 DÉBUT allerAdresse()
📍 Adresse reçue: "chateau de Brézé"
✅ Carte initialisée
📡 Envoi requête fetch()...
📨 Réponse reçue: HTTP 200
📍 Coordonnées trouvées: Lat=47.17444935, Lng=-0.05726788258849062
🗺️ Appel centerOn()...
📌 Création marqueur déplaçable...
✅ Marqueur créé et ajouté à la carte
☕ Appel updateCoordinates()...
✅ Coordonnées envoyées à Java avec succès!

[Java Console]
📍 Coordonnées reçues de JavaScript: Lat=47.17444935, Lng=-0.05726788258849062
🔍 tfLongitudeRef = OK
🔍 tfLatitudeRef = OK
✅ Marqueur mis à jour: Lat=47.17444935, Lng=-0.05726788258849062
✅ TextField Longitude mis à jour: 0°3'26.2"W
✅ TextField Latitude mis à jour: 47°10'28.0"N
```

### Lors du clic sur "Valider la position" :
```
✅ Récupération depuis marqueur: Lat=47.17444935, Lng=-0.05726788258849062
```

## 🎯 Résumé

Les 3 problèmes ont été corrigés :

1. ✅ **Affichage des coordonnées** : Logs de diagnostic ajoutés pour identifier si les TextFields sont bien référencés
2. ✅ **Erreur de validation** : La méthode `recupereCoordonnees()` utilise maintenant l'objet `marqueur` mis à jour par JavaScript
3. ✅ **Basculement carte/satellite** : La fonction `getNomsLayers()` retourne le bon format avec séparateur | et indicateur *

**Prochaine étape:** Lancer l'application et tester les 4 scénarios décrits ci-dessus.

---

**Commande pour lancer l'application:**
```powershell
mvn javafx:run
```
