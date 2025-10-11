# Configuration des clés API

Ce projet utilise des services externes nécessitant des clés API. Pour des raisons de sécurité, ces clés ne doivent **jamais** être versionnées dans Git.

## Configuration

### 1. Créer le fichier de configuration

Copiez le fichier template vers le fichier de configuration :

```bash
cp api-keys.properties.template api-keys.properties
```

### 2. Obtenir vos clés API

#### LocationIQ (Géocodage gratuit)

1. Créez un compte sur [https://locationiq.com/](https://locationiq.com/)
2. Tier gratuit : **5 000 requêtes/jour**
3. Copiez votre clé API depuis le dashboard
4. Collez-la dans `api-keys.properties` :

```properties
locationiq.api.key=votre_clé_ici
```

### 3. Sécurité

⚠️ **Important** :
- Le fichier `api-keys.properties` est dans `.gitignore` et ne sera **jamais** commité
- Le fichier `api-keys.properties.template` peut être versionné (il ne contient pas de clés réelles)
- Ne partagez jamais vos clés API publiquement

### 4. Utilisation dans le code

Les clés sont chargées automatiquement via la classe `ApiKeysConfig` :

```java
String key = ApiKeysConfig.getLocationIQApiKey();
```

## Services utilisés

| Service | Usage | Limite gratuite | Documentation |
|---------|-------|----------------|---------------|
| LocationIQ | Géocodage d'adresses | 5 000 req/jour | [locationiq.com/docs](https://locationiq.com/docs) |

## Dépannage

Si vous voyez le message :
```
⚠️ Fichier api-keys.properties non trouvé
```

Cela signifie que vous devez créer le fichier de configuration (voir étape 1 ci-dessus).
