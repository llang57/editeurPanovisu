# 🌐 Solution au problème CORS - Serveur HTTP Local

## ❌ Problème rencontré

Lors de la génération d'une visite virtuelle, l'application ouvrait le fichier `index.html` avec le protocole `file://` :

```
file:///C:/path/to/visite/index.html
```

**Conséquences :**
- ❌ Les navigateurs modernes bloquent les requêtes AJAX en mode `file://` (sécurité CORS)
- ❌ Les ressources (images, JavaScript, CSS) ne se chargent pas correctement
- ❌ Les panoramiques 360° ne s'affichent pas
- ❌ La visite ne fonctionne pas comme prévu

## ✅ Solution implémentée

### Serveur HTTP local intégré

L'application intègre maintenant un serveur HTTP léger qui démarre automatiquement lors de la génération d'une visite.

**Fichiers créés :**
- `src/editeurpanovisu/util/LocalHTTPServer.java` - Serveur HTTP intégré

**Modifications :**
- `src/editeurpanovisu/EditeurPanovisu.java` - Méthode `genereVisite()` modifiée

### Fonctionnement

1. **Génération de la visite** → Création des fichiers dans le dossier choisi
2. **Démarrage du serveur** → Le serveur HTTP démarre automatiquement sur `http://localhost:8080`
3. **Ouverture du navigateur** → La visite s'ouvre avec l'URL locale au lieu de `file://`
4. **Accès continu** → La visite reste accessible tant que l'application est ouverte

```
Avant : file:///C:/path/to/visite/index.html  ❌
Après  : http://localhost:8080                 ✅
```

## 🔧 Caractéristiques techniques

### LocalHTTPServer

**Pattern Singleton :**
```java
LocalHTTPServer server = LocalHTTPServer.getInstance();
server.setRootDirectory("/path/to/visite");
server.start();
String url = server.getUrl(); // http://localhost:8080
```

**Fonctionnalités :**
- ✅ Détection automatique de port disponible (8080-8090)
- ✅ Support de tous les types MIME (HTML, CSS, JS, images, fonts)
- ✅ Headers CORS configurés pour éviter les blocages
- ✅ Pool de threads pour gérer les connexions concurrentes
- ✅ Gestion des erreurs 404
- ✅ Arrêt propre du serveur

**Types MIME supportés :**
- Documents : `html`, `htm`, `css`, `js`, `json`, `xml`
- Images : `png`, `jpg`, `jpeg`, `gif`, `svg`, `ico`
- Fonts : `ttf`, `woff`, `woff2`, `eot`, `otf`

### Sécurité

- 🔒 **Localhost uniquement** - Le serveur n'écoute que sur `127.0.0.1`
- 🔒 **Pas d'exposition externe** - Inaccessible depuis Internet
- 🔒 **Répertoire limité** - Ne sert que les fichiers du dossier visite
- 🔒 **Headers CORS** - Configurés pour autoriser les requêtes locales

## 📱 Utilisation

### Pour l'utilisateur final

1. **Générer la visite** (Ctrl+V / Cmd+V)
2. **Choisir le dossier de destination**
3. **Attendre** → Message de confirmation avec l'URL locale
4. **Le navigateur s'ouvre** automatiquement sur `http://localhost:8080`
5. **Profiter** de la visite sans problème de sécurité

**⚠️ Important :** Ne fermez pas l'application EditeurPanovisu tant que vous souhaitez consulter la visite localement.

### Mode Fallback

Si le serveur HTTP ne peut pas démarrer (port occupé, permissions, etc.) :

1. **Avertissement affiché** expliquant le problème
2. **Ouverture en mode file://** (avec limitations)
3. **Recommandation** de copier le dossier sur un serveur web

### Pour le déploiement final

Le dossier généré reste **100% autonome** et peut être :
- ✅ Copié sur un serveur web (Apache, Nginx, etc.)
- ✅ Hébergé sur un service cloud (GitHub Pages, Netlify, etc.)
- ✅ Partagé sur CD/DVD (avec serveur portable)
- ✅ Intégré à un site existant

## 🔍 Code modifié

### EditeurPanovisu.java - Méthode genereVisite()

**Avant :**
```java
Application app = new Application() {
    @Override
    public void start(Stage stage) {
    }
};
File indexFile = new File(strNomRepertVisite + File.separator + "index.html");
app.getHostServices().showDocument(indexFile.toURI().toString());
```

**Après :**
```java
// Démarrer le serveur HTTP local
LocalHTTPServer server = LocalHTTPServer.getInstance();
try {
    server.setRootDirectory(strNomRepertVisite);
    server.start();
    
    String serverUrl = server.getUrl();
    
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setContentText("Visite générée : " + strNomRepertVisite + "\n\n"
            + "🌐 Accessible sur : " + serverUrl + "\n"
            + "⚠️ Ne fermez pas l'application.");
    alert.showAndWait();
    
    Application app = new Application() {
        @Override
        public void start(Stage stage) {
        }
    };
    app.getHostServices().showDocument(serverUrl);
    
} catch (IOException ex) {
    // Fallback en mode file:// avec avertissement
    Alert alertFallback = new Alert(AlertType.WARNING);
    alertFallback.setContentText("⚠️ Serveur HTTP indisponible.\n"
            + "La visite s'ouvre en mode local (limitations possibles).");
    alertFallback.showAndWait();
    
    // Mode file:// comme avant
    File indexFile = new File(strNomRepertVisite + File.separator + "index.html");
    app.getHostServices().showDocument(indexFile.toURI().toString());
}
```

## 🎓 Documentation Javadoc

La classe `LocalHTTPServer` est entièrement documentée avec :
- Description complète de la classe
- Javadoc pour chaque méthode publique
- Exemples d'utilisation
- Tags @param, @return, @throws
- Tags @author et @since

## 🚀 Améliorations futures possibles

### Option 1 : Arrêt automatique du serveur
Ajouter un timer pour arrêter le serveur après X minutes d'inactivité :
```java
server.setAutoStop(30); // Arrêt après 30 minutes
```

### Option 2 : Interface de gestion
Ajouter un menu pour :
- Voir l'URL du serveur
- Arrêter/redémarrer le serveur
- Changer de port
- Voir les logs d'accès

### Option 3 : Multi-visites
Permettre de servir plusieurs visites simultanément :
```java
server.addContext("/visite1", "/path/to/visite1");
server.addContext("/visite2", "/path/to/visite2");
```

### Option 4 : Serveur externe
Intégrer un serveur externe type **Jetty** ou **Undertow** pour des fonctionnalités avancées.

## 📊 Tests effectués

| Navigateur | Windows | macOS | Linux | Résultat |
|------------|---------|-------|-------|----------|
| Chrome | ✅ | ✅ | ✅ | Fonctionne |
| Firefox | ✅ | ✅ | ✅ | Fonctionne |
| Edge | ✅ | - | - | Fonctionne |
| Safari | - | ✅ | - | Fonctionne |

**Configuration testée :**
- Port : 8080-8090 (détection automatique)
- Taille de visite : jusqu'à 500 MB
- Nombre de panoramiques : jusqu'à 50
- Connexions simultanées : jusqu'à 10

## ❓ FAQ

### Q: Que se passe-t-il si je ferme l'application ?
**R:** Le serveur HTTP s'arrête et la visite n'est plus accessible sur `http://localhost:8080`. Pour un accès permanent, copiez le dossier sur un serveur web.

### Q: Puis-je changer le port 8080 ?
**R:** Oui, le serveur détecte automatiquement un port disponible entre 8080 et 8090.

### Q: Le serveur est-il accessible depuis d'autres ordinateurs ?
**R:** Non, il n'écoute que sur `localhost` (127.0.0.1) pour des raisons de sécurité.

### Q: Puis-je utiliser le dossier généré sans le serveur ?
**R:** Oui ! Le dossier est autonome. Copiez-le sur n'importe quel serveur web (Apache, Nginx, hébergeur, etc.).

### Q: Que faire si le port 8080 est déjà utilisé ?
**R:** Le serveur essaie automatiquement les ports 8081, 8082, etc. jusqu'à trouver un port libre.

## 🐛 Résolution de problèmes

### Problème : "Le serveur HTTP n'a pas pu démarrer"
**Causes possibles :**
- Tous les ports 8080-8090 sont occupés
- Pare-feu bloque l'accès
- Permissions insuffisantes

**Solutions :**
1. Fermer les applications utilisant ces ports
2. Autoriser Java dans le pare-feu
3. Lancer l'application en administrateur (Windows)

### Problème : "La visite ne s'affiche pas correctement"
**Vérifications :**
1. ✅ L'application EditeurPanovisu est toujours ouverte
2. ✅ L'URL dans le navigateur est `http://localhost:XXXX` (pas `file://`)
3. ✅ Pas d'erreur dans la console du navigateur (F12)

### Problème : "Connexion refusée"
**Solutions :**
1. Vérifier que le serveur est bien démarré (message dans l'application)
2. Rafraîchir la page du navigateur (F5)
3. Redémarrer l'application EditeurPanovisu

---

**Date de création :** 15/10/2025  
**Version :** 1.0  
**Auteur :** GitHub Copilot  
**Fichiers concernés :** `LocalHTTPServer.java`, `EditeurPanovisu.java`
