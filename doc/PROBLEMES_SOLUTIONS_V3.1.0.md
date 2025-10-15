# 🔧 Problèmes Connus et Solutions - Version 3.1.0

Ce document liste les problèmes connus avec leurs solutions pour faciliter le support.

---

## 🐛 Problèmes de migration

### P1: Erreurs 404 persistent après ré-export

**Symptôme :**
```
GET /visite/panovisu/libs/jquerymenu/... → 404
GET /visite/panovisu/libs/openlayers/... → 404
```

**Causes possibles :**
1. Cache du navigateur non vidé
2. Fichiers non uploadés complètement
3. Permissions incorrectes sur le serveur
4. CDN/Proxy cache l'ancienne version

**Solutions :**

**Solution 1 - Vider le cache**
```bash
# Navigateur : Ctrl+Shift+R (Windows/Linux) ou Cmd+Shift+R (Mac)
# Ou F12 > Network > Disable cache
```

**Solution 2 - Vérifier l'upload**
```bash
# Sur le serveur
cd /var/www/ma-visite/panovisu/libs/
ls -la | grep -E "(jquery|open)"

# Doit afficher :
# drwxr-xr-x jqueryMenu
# drwxr-xr-x openLayers
```

**Solution 3 - Corriger les permissions**
```bash
chmod -R 755 /var/www/ma-visite/
chown -R www-data:www-data /var/www/ma-visite/
```

**Solution 4 - Purger le cache CDN**
- Cloudflare : Purge Everything
- OVH : Vider le cache CDN
- Autre : Consultez la doc de votre hébergeur

---

### P2: La visite s'affiche mais l'interface ne fonctionne pas

**Symptôme :**
- Photo panoramique s'affiche
- Barre de navigation absente/non fonctionnelle
- Pas de navigation entre photos
- Console : erreurs JavaScript

**Cause :**
Fichiers JavaScript de `jqueryMenu` non chargés correctement.

**Diagnostic :**
```javascript
// Console navigateur (F12)
// Cherchez :
Uncaught ReferenceError: jQuery is not defined
Failed to load resource: jquerymenu/...
```

**Solution :**
1. Vérifiez que le dossier s'appelle bien `jqueryMenu` (M majuscule)
2. Vérifiez `panovisuInit.js` lignes 164-165 :
   ```javascript
   require(['panovisu/libs/jqueryMenu/...'
   require(['panovisu/libs/openLayers/...'
   ```
3. Re-exportez si nécessaire

---

### P3: La carte OpenStreetMap ne s'affiche pas

**Symptôme :**
- Visite fonctionne
- Onglet "Carte" vide ou erreur
- Console : erreurs liées à OpenLayers

**Cause :**
Fichiers OpenLayers non trouvés (problème de casse).

**Diagnostic :**
```javascript
// Console
Failed to load resource: openlayers/... → 404
```

**Solution :**
Même procédure que P1, focus sur `openLayers/`.

---

## 🔧 Problèmes d'installation

### P4: Windows - "Application ne peut pas démarrer"

**Symptôme :**
```
L'application n'a pas pu démarrer correctement (0xc000007b)
```

**Cause :**
Runtime C++ manquant (rare, normalement inclus).

**Solution :**
```bash
# Installer Microsoft Visual C++ Redistributables
# Télécharger depuis :
https://aka.ms/vs/17/release/vc_redist.x64.exe

# Installer, puis relancer EditeurPanovisu
```

---

### P5: macOS - "Application endommagée"

**Symptôme :**
```
"EditeurPanovisu.app" est endommagé et ne peut pas être ouvert.
```

**Cause :**
Gatekeeper bloque l'application non signée.

**Solution :**
```bash
# Terminal
sudo xattr -cr /Applications/EditeurPanovisu.app

# Ou via Interface
# Clic droit > Ouvrir (puis confirmer)
```

---

### P6: Linux - Dépendances manquantes

**Symptôme :**
```bash
dpkg: dependency problems prevent configuration of editeurpanovisu
```

**Cause :**
Paquets système manquants.

**Solution Debian/Ubuntu :**
```bash
sudo apt-get update
sudo apt-get install -f
sudo apt-get install libgtk-3-0 libgl1 libglib2.0-0
```

**Solution Fedora :**
```bash
sudo dnf install gtk3 mesa-libGL glib2
```

---

## 📦 Problèmes d'export

### P7: Export échoue avec "Erreur d'écriture"

**Symptôme :**
```
Erreur lors de l'export : Impossible d'écrire le fichier...
```

**Causes possibles :**
1. Permissions insuffisantes sur le dossier de destination
2. Disque plein
3. Chemin trop long (>260 caractères sous Windows)

**Solutions :**

**Solution 1 - Permissions**
```bash
# Windows : Clic droit > Propriétés > Sécurité
# Ajoutez "Contrôle total" pour votre utilisateur

# Linux/macOS
chmod 755 /chemin/destination/
```

**Solution 2 - Espace disque**
```bash
# Vérifier l'espace disponible
# Windows : Ce PC > Propriétés du disque
# Linux : df -h
```

**Solution 3 - Chemin court**
- Exportez vers `C:\Temp\visite` au lieu de `C:\Users\...\Documents\...\...\visite`

---

### P8: Export lent / bloqué

**Symptôme :**
Export prend beaucoup plus de temps que d'habitude ou semble bloqué.

**Causes :**
1. Antivirus scanne chaque fichier créé
2. Nombreuses photos haute résolution
3. Disque lent (HDD vs SSD)

**Solutions :**

**Solution 1 - Antivirus**
```
Ajoutez une exception dans votre antivirus pour :
- Le dossier d'export
- EditeurPanovisu.exe
```

**Solution 2 - Optimisation**
- Exportez sur un SSD si possible
- Fermez les autres applications
- Ne pas utiliser l'ordinateur pendant l'export

---

## 🌐 Problèmes serveur

### P9: "403 Forbidden" sur certains fichiers

**Symptôme :**
```
Forbidden - You don't have permission to access /visite/panovisu/...
```

**Cause :**
Permissions ou configuration Apache/Nginx.

**Solution Apache :**
```apache
# .htaccess
<Directory "/var/www/ma-visite">
    Options +Indexes +FollowSymLinks
    AllowOverride All
    Require all granted
</Directory>
```

**Solution Nginx :**
```nginx
location /ma-visite/ {
    autoindex on;
    try_files $uri $uri/ =404;
}
```

---

### P10: CORS errors (Cross-Origin)

**Symptôme :**
```javascript
Access to XMLHttpRequest blocked by CORS policy
```

**Cause :**
API externes (Google Maps, etc.) bloquées par CORS.

**Solution Apache :**
```apache
# .htaccess
Header set Access-Control-Allow-Origin "*"
```

**Solution Nginx :**
```nginx
add_header Access-Control-Allow-Origin "*";
```

⚠️ **Attention** : `*` permet tous les domaines. En production, spécifiez votre domaine.

---

## 🖥️ Problèmes de compatibilité

### P11: Java version incompatible

**Symptôme :**
```
Unsupported class file major version XX
```

**Cause :**
Tentative d'exécution avec une vieille version de Java.

**Solution :**
**Ne devrait pas arriver** car Java est embarqué dans l'installeur.

Si ça arrive quand même :
```bash
# Vérifiez la version Java
java -version

# Doit afficher : OpenJDK 25 ou supérieur
```

Réinstallez EditeurPanovisu v3.1.0 (Java inclus).

---

### P12: Problèmes d'affichage sur écrans HiDPI

**Symptôme :**
- Interface trop petite ou trop grande
- Texte flou

**Solution Windows :**
```
Clic droit sur EditeurPanovisu.exe
> Propriétés > Compatibilité
> Modifier les paramètres PPP élevés
> Cocher "Remplacer le comportement de mise à l'échelle..."
> Sélectionner "Système"
```

**Solution macOS :**
Interface s'adapte automatiquement (Retina).

**Solution Linux :**
```bash
export GDK_SCALE=2
export GDK_DPI_SCALE=0.5
editeurpanovisu
```

---

## 🔍 Problèmes de diagnostic

### P13: Logs introuvables

**Emplacement des logs :**

**Windows :**
```
%APPDATA%\EditeurPanovisu\logs\editeurpanovisu.log
# Ou
C:\Users\VotreNom\AppData\Roaming\EditeurPanovisu\logs\
```

**macOS :**
```
~/Library/Application Support/EditeurPanovisu/logs/editeurpanovisu.log
```

**Linux :**
```
~/.config/EditeurPanovisu/logs/editeurpanovisu.log
# Ou
~/.editeurpanovisu/logs/editeurpanovisu.log
```

**Commandes utiles :**
```bash
# Afficher les dernières erreurs
tail -n 50 editeurpanovisu.log | grep ERROR

# Suivre les logs en temps réel
tail -f editeurpanovisu.log
```

---

### P14: Version installée incertaine

**Vérifier la version :**

**Via l'interface :**
- Menu `Aide > À propos`
- Doit afficher : "Version 3.1.0 - Build 2609"

**Via fichiers :**
```bash
# Windows
type "C:\Program Files\EditeurPanovisu\app\build.num"

# Linux/macOS
cat /opt/editeurpanovisu/app/build.num
```

Doit contenir : `build.number=2609`

---

## 🆘 Problèmes non résolus

Si votre problème n'est pas listé ici :

### Étape 1 : Collecter les informations

- Version d'Éditeur Panovisu
- Système d'exploitation (version exacte)
- Fichier `editeurpanovisu.log`
- Captures d'écran des erreurs
- Étapes pour reproduire le problème

### Étape 2 : Rechercher

- **Discussions GitHub** : https://github.com/llang57/editeurPanovisu/discussions
- **Issues existants** : https://github.com/llang57/editeurPanovisu/issues

### Étape 3 : Signaler

Si problème non documenté :
1. https://github.com/llang57/editeurPanovisu/issues/new
2. Template "Bug Report"
3. Fournissez toutes les infos collectées

---

## 📊 Statistiques des problèmes

### Fréquence des problèmes

| Problème | Fréquence | Gravité | Temps de résolution |
|----------|-----------|---------|---------------------|
| P1 - 404 après export | ⭐⭐⭐⭐⭐ | 🔴 Haute | 5-10 min |
| P2 - Interface non fonctionnelle | ⭐⭐⭐⭐ | 🔴 Haute | 10 min |
| P5 - macOS "endommagé" | ⭐⭐⭐ | 🟡 Moyenne | 2 min |
| P7 - Erreur export | ⭐⭐⭐ | 🟡 Moyenne | 5 min |
| P9 - 403 Forbidden | ⭐⭐ | 🟡 Moyenne | 15 min |
| P10 - CORS | ⭐⭐ | 🟢 Basse | 5 min |
| P11 - Java incompatible | ⭐ | 🔴 Haute | 10 min |

---

## 🔄 Mises à jour de ce document

Ce document est mis à jour régulièrement avec les nouveaux problèmes signalés.

**Dernière mise à jour** : 16 octobre 2025  
**Version du document** : 1.0  
**Couvre** : EditeurPanovisu v3.1.0 (build 2609)

---

**💡 Vous avez trouvé une solution à un problème non listé ?**  
Partagez-la dans les Discussions GitHub ! Elle sera ajoutée à ce document.
