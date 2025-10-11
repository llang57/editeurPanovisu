# 🔑 Guide de Configuration des Clés API

**Date :** 11 octobre 2025  
**Version :** 2.0.0-SNAPSHOT

---

## 📋 Vue d'Ensemble

EditeurPanovisu utilise plusieurs services externes pour la géolocalisation et la cartographie. Ce guide explique comment obtenir et configurer vos clés API.

---

## 🚀 Configuration Rapide (3 minutes)

### Étape 1 : Créer le fichier de configuration

```powershell
# Depuis la racine du projet
Copy-Item api-keys.properties.template api-keys.properties
```

### Étape 2 : Obtenir une clé LocationIQ (obligatoire)

1. Aller sur [locationiq.com](https://locationiq.com/)
2. Cliquer sur "Sign Up" (gratuit)
3. Confirmer votre email
4. Copier votre clé depuis le dashboard

### Étape 3 : Ajouter votre clé

Ouvrir `api-keys.properties` et remplacer :

```properties
locationiq.api.key=YOUR_LOCATIONIQ_API_KEY_HERE
```

Par votre vraie clé :

```properties
locationiq.api.key=pk.0f147952a41c555a5b70614039fd148b
```

### Étape 4 : Compiler et tester

```powershell
mvn clean compile
mvn javafx:run
```

✅ **C'est tout !** La géolocalisation devrait fonctionner.

---

## 🗺️ Services Utilisés

### 1. LocationIQ (Obligatoire) ⭐

**Usage :** Géocodage d'adresses (recherche "Tour Eiffel" → coordonnées GPS)

| Caractéristique | Détails |
|----------------|---------|
| 🌐 **Site** | [locationiq.com](https://locationiq.com/) |
| 💰 **Gratuit** | 5 000 requêtes/jour |
| 📈 **Payant** | À partir de 49$/mois pour 100 000 req/jour |
| 📖 **Documentation** | [locationiq.com/docs](https://locationiq.com/docs) |
| 🔑 **Format clé** | `pk.` + 32 caractères hexadécimaux |

**Fonctionnalités :**
- ✅ Géocodage (adresse → coordonnées)
- ✅ Géocodage inverse (coordonnées → adresse)
- ✅ Autocomplétion
- ✅ Recherche de lieux

**Limites gratuites :**
- 5 000 requêtes/jour
- 2 requêtes/seconde max
- Pas de restriction de domaine

**Comment obtenir :**
1. Créer un compte sur [locationiq.com](https://locationiq.com/)
2. Vérifier votre email
3. Aller dans **Dashboard** → **Access Tokens**
4. Copier la clé (elle commence par `pk.`)

**Exemple de clé :**
```properties
locationiq.api.key=pk.4b8c7074547982766e8ba8dc1e944210
```

---

### 2. Bing Maps (Optionnel)

**Usage :** Alternative pour la cartographie (si utilisé)

| Caractéristique | Détails |
|----------------|---------|
| 🌐 **Site** | [bingmapsportal.com](https://www.bingmapsportal.com/) |
| 💰 **Gratuit** | 125 000 transactions/an |
| 📈 **Payant** | Pay-as-you-go au-delà |
| 📖 **Documentation** | [docs.microsoft.com/bingmaps](https://docs.microsoft.com/en-us/bingmaps/) |
| 🔑 **Format clé** | 64+ caractères alphanumériques |

**Fonctionnalités :**
- ✅ Imagerie aérienne
- ✅ Cartes routières
- ✅ Géocodage
- ✅ Calcul d'itinéraires

**Limites gratuites :**
- 125 000 transactions billables/an
- Environnement de test illimité

**Comment obtenir :**
1. Créer un compte Microsoft
2. Aller sur [Bing Maps Dev Center](https://www.bingmapsportal.com/)
3. **My Account** → **My Keys** → **Create Key**
4. Choisir **Basic** (gratuit)
5. Copier la clé

**Exemple de clé :**
```properties
bing.maps.api.key=AqGHvMp2d4hF6tZ7j8kL9mN0pQrSt1uV2wX3yZ4aB5cD6eF7gH8iJ9kL0mN1o2P3
```

---

### 3. OpenWeatherMap (Optionnel)

**Usage :** Données météorologiques (si fonctionnalité ajoutée)

| Caractéristique | Détails |
|----------------|---------|
| 🌐 **Site** | [openweathermap.org](https://openweathermap.org/) |
| 💰 **Gratuit** | 1 000 appels/jour |
| 📈 **Payant** | À partir de 40$/mois |
| 📖 **Documentation** | [openweathermap.org/api](https://openweathermap.org/api) |
| 🔑 **Format clé** | 32 caractères hexadécimaux |

**Fonctionnalités :**
- ✅ Météo actuelle
- ✅ Prévisions 5 jours
- ✅ Données historiques
- ✅ Alertes météo

**Limites gratuites :**
- 1 000 appels/jour
- 60 appels/minute

**Comment obtenir :**
1. Créer un compte sur [openweathermap.org](https://openweathermap.org/)
2. Aller dans **API keys**
3. Copier la clé par défaut ou créer une nouvelle

**Exemple de clé :**
```properties
openweather.api.key=a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6
```

---

### 4. Mapbox (Optionnel)

**Usage :** Alternative cartographique premium

| Caractéristique | Détails |
|----------------|---------|
| 🌐 **Site** | [mapbox.com](https://www.mapbox.com/) |
| 💰 **Gratuit** | 50 000 chargements/mois |
| 📈 **Payant** | Pay-as-you-go au-delà |
| 📖 **Documentation** | [docs.mapbox.com](https://docs.mapbox.com/) |
| 🔑 **Format token** | Commence par `pk.` |

**Fonctionnalités :**
- ✅ Cartes vectorielles personnalisables
- ✅ Imagerie satellite HD
- ✅ Navigation en 3D
- ✅ Styles de carte personnalisés

**Limites gratuites :**
- 50 000 chargements de carte/mois
- 100 000 requêtes géocodage/mois

**Comment obtenir :**
1. Créer un compte sur [mapbox.com](https://www.mapbox.com/)
2. Aller dans **Account** → **Access Tokens**
3. Copier le token par défaut ou créer un nouveau

**Exemple de token :**
```properties
mapbox.access.token=pk.eyJ1IjoiZXhhbXBsZSIsImEiOiJja2R1ZG1kZG0wMXd1MnFtdjBxYjB1Y3d1In0.abc123
```

---

## 🔒 Sécurité

### ⚠️ Règles Importantes

1. **Ne JAMAIS commiter le fichier `api-keys.properties`**
   - Il est dans `.gitignore` pour une raison
   - Si commité par erreur, régénérer TOUTES les clés

2. **Ne JAMAIS partager vos clés publiquement**
   - Pas dans les screenshots
   - Pas dans les issues GitHub
   - Pas dans les forums

3. **Utiliser des clés différentes pour dev/prod**
   - Facilite le traçage des usages
   - Limite l'impact d'une compromission

4. **Surveiller l'usage**
   - Activer les alertes de quota dans les dashboards
   - Vérifier régulièrement les statistiques
   - Détecter les usages anormaux

### 🛡️ Bonnes Pratiques

**Restrictions de domaine :**
```
Configurer dans les dashboards :
- LocationIQ : Pas de restriction nécessaire en dev
- Bing Maps : Ajouter localhost et votre domaine
- Mapbox : Restreindre aux URLs de votre application
```

**Rotation des clés :**
- Changer les clés tous les 6 mois minimum
- Changer immédiatement si compromission suspectée

**Surveillance :**
- Activer les notifications email de quota
- Configurer des alertes à 80% d'utilisation

---

## 🔧 Configuration Avancée

### Variables d'Environnement (Alternative)

Au lieu d'utiliser `api-keys.properties`, vous pouvez définir des variables d'environnement :

**Windows PowerShell :**
```powershell
$env:LOCATIONIQ_API_KEY = "pk.votre_clé_ici"
```

**Linux/Mac :**
```bash
export LOCATIONIQ_API_KEY="pk.votre_clé_ici"
```

**Modification dans le code :**
```java
// Dans ApiKeysConfig.java
String key = System.getenv("LOCATIONIQ_API_KEY");
if (key == null) {
    // Fallback sur api-keys.properties
    key = properties.getProperty("locationiq.api.key");
}
```

### Fichiers de Configuration Multiples

Pour gérer plusieurs environnements :

```
api-keys.dev.properties      # Développement
api-keys.test.properties     # Tests
api-keys.prod.properties     # Production
```

---

## 🧪 Tests de Configuration

### Vérifier que les clés sont chargées

```powershell
mvn compile
mvn test -Dtest=ApiKeysConfigTest
```

### Test manuel dans l'application

1. Lancer l'application : `mvn javafx:run`
2. Ouvrir la fenêtre de géolocalisation
3. Rechercher "Tour Eiffel Paris"
4. Vérifier la console pour les logs :
   ```
   ✅ Clé LocationIQ chargée : pk.4b8c...
   📡 Envoi requête fetch()...
   📨 Réponse reçue: HTTP 200
   ```

---

## 🚨 Dépannage

### Problème : "API key not found"

**Symptôme :** Message d'erreur "LocationIQ API key not configured"

**Solutions :**
1. Vérifier que `api-keys.properties` existe à la racine
2. Vérifier le nom exact de la clé : `locationiq.api.key`
3. Pas d'espaces autour du `=`
4. Recompiler : `mvn clean compile`

### Problème : HTTP 401 Unauthorized

**Symptôme :** Requête API retourne 401

**Solutions :**
1. Vérifier que la clé est valide (tester dans le dashboard)
2. Vérifier qu'il n'y a pas d'espaces dans la clé
3. Régénérer la clé dans le dashboard si nécessaire

### Problème : HTTP 429 Too Many Requests

**Symptôme :** Erreur "Rate limit exceeded"

**Solutions :**
1. Vérifier l'usage dans le dashboard du service
2. Attendre la réinitialisation du quota (généralement à minuit UTC)
3. Optimiser le code pour réduire les requêtes
4. Mettre en cache les résultats
5. Passer à un plan payant si nécessaire

---

## 📊 Tableau Récapitulatif

| Service | Obligatoire | Gratuit/Mois | Clé commence par | Documentation |
|---------|-------------|--------------|------------------|---------------|
| LocationIQ | ✅ Oui | 150 000 req | `pk.` | [Docs](https://locationiq.com/docs) |
| Bing Maps | ❌ Optionnel | ~10 400 req | Alphanum 64+ | [Docs](https://docs.microsoft.com/bingmaps) |
| OpenWeather | ❌ Optionnel | 30 000 req | Hex 32 | [Docs](https://openweathermap.org/api) |
| Mapbox | ❌ Optionnel | 50 000 req | `pk.` | [Docs](https://docs.mapbox.com/) |

---

## 📚 Ressources

### Documentation Officielle
- [LocationIQ API Reference](https://locationiq.com/docs)
- [Bing Maps API](https://docs.microsoft.com/en-us/bingmaps/rest-services/)
- [OpenWeatherMap API](https://openweathermap.org/api)
- [Mapbox API](https://docs.mapbox.com/api/)

### Dashboards
- [LocationIQ Dashboard](https://locationiq.com/dashboard)
- [Bing Maps Portal](https://www.bingmapsportal.com/)
- [OpenWeather Dashboard](https://home.openweathermap.org/)
- [Mapbox Account](https://account.mapbox.com/)

---

**Dernière mise à jour :** 11 octobre 2025, 09:15
