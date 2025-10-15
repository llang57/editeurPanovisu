# 🚀 Guide de Migration Rapide vers v3.1.0

Ce guide vous accompagne pas à pas pour migrer vers la version 3.1.0 et corriger vos visites existantes.

---

## ⏱️ Temps estimé

- **Installation** : 2-3 minutes
- **Par visite à ré-exporter** : 1-5 minutes selon la taille
- **Upload vers serveur** : Variable selon connexion

---

## 📋 Checklist avant de commencer

- [ ] Identifiez toutes vos visites hébergées sur Linux
- [ ] Vérifiez que vous avez les fichiers projet (`.pano`, `.cfg`)
- [ ] Notez les chemins d'upload FTP/SFTP de chaque visite
- [ ] Sauvegardez les anciennes versions (optionnel mais recommandé)

---

## 🔧 Étape 1 : Installation de la v3.1.0

### Windows

1. **Téléchargez l'installeur**
   ```
   https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe
   ```

2. **Lancez l'installeur**
   - Double-cliquez sur `EditeurPanovisu-Setup-3.1.0.exe`
   - Suivez l'assistant d'installation
   - L'ancienne version sera automatiquement remplacée

3. **Vérifiez l'installation**
   - Lancez l'application
   - Menu `Aide > À propos`
   - Vérifiez : "Version 3.1.0 - Build 2609"

---

### macOS

1. **Téléchargez le DMG**
   ```
   https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg
   ```

2. **Installez l'application**
   - Montez le fichier `.dmg`
   - Glissez `EditeurPanovisu.app` dans `/Applications`
   - Remplacez si demandé
   - Éjectez l'image disque

3. **Premier lancement**
   - Clic droit sur `EditeurPanovisu.app` > `Ouvrir`
   - Confirmez l'ouverture (sécurité macOS)

---

### Linux (Debian/Ubuntu)

```bash
# Télécharger
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb

# Installer
sudo dpkg -i editeurpanovisu_3.1.0_amd64.deb

# Si dépendances manquantes
sudo apt-get install -f

# Lancer
editeurpanovisu
```

---

### Linux (Fedora/RHEL/CentOS)

```bash
# Télécharger
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm

# Installer
sudo rpm -i editeurpanovisu-3.1.0.x86_64.rpm

# Lancer
editeurpanovisu
```

---

## 🔄 Étape 2 : Ré-export des visites

Pour **chaque visite hébergée sur Linux** :

### 2.1 Ouvrir le projet

1. Lancez Éditeur Panovisu v3.1.0
2. `Fichier > Ouvrir un projet`
3. Sélectionnez le fichier `.pano` de votre visite

### 2.2 Vérifier le projet

- ✅ Vérifiez que toutes les photos s'affichent
- ✅ Vérifiez les liens entre photos
- ✅ Testez la navigation
- ✅ Vérifiez les hotspots

### 2.3 Exporter la visite

1. `Fichier > Exporter la visite`
2. **IMPORTANT** : Choisissez un dossier temporaire (pas directement sur le serveur)
3. Attendez la fin de l'export
4. Vérifiez le message de succès

### 2.4 Vérifier l'export en local

1. Ouvrez `index.html` dans un navigateur
2. Testez la navigation
3. Ouvrez la console (F12) - **aucune erreur ne doit apparaître**
4. Vérifiez que ces dossiers existent :
   - `panovisu/libs/jqueryMenu/` (avec 'M' majuscule)
   - `panovisu/libs/openLayers/` (avec 'L' majuscule)

---

## 📤 Étape 3 : Upload vers le serveur

### 3.1 Sauvegarde (recommandé)

Avant de remplacer, sauvegardez l'ancienne version :

**Via FTP/SFTP :**
```bash
# Sur le serveur
mv /var/www/ma-visite /var/www/ma-visite.backup
```

**Via interface d'hébergement :**
- Renommez le dossier existant en `ma-visite.old`

### 3.2 Upload des nouveaux fichiers

**Option A - Remplacer complètement :**
```bash
# Supprimer l'ancien dossier
rm -rf /var/www/ma-visite

# Uploader le nouveau
scp -r /chemin/local/visite-exportee/ user@serveur:/var/www/ma-visite
```

**Option B - Remplacer uniquement les fichiers modifiés :**
1. Uploadez tout le dossier `panovisu/` (contient les corrections)
2. Uploadez `index.html` et `openstreetmap.html`
3. Les autres fichiers (photos, config) n'ont pas changé

### 3.3 Vérifier les permissions

```bash
# Sur le serveur Linux
chmod -R 755 /var/www/ma-visite
chown -R www-data:www-data /var/www/ma-visite
```

---

## ✅ Étape 4 : Tests post-migration

### 4.1 Test navigateur

1. Ouvrez la visite : `https://votresite.com/ma-visite/`
2. **Ouvrez la console** (F12)
3. **Vérifiez : aucune erreur 404**
4. Testez la navigation entre photos
5. Testez les hotspots
6. Testez la carte interactive (si applicable)
7. Testez la barre de navigation

### 4.2 Test multi-navigateurs

Testez sur :
- ✅ Chrome/Edge
- ✅ Firefox
- ✅ Safari (si possible)
- ✅ Mobile (Chrome Android / Safari iOS)

### 4.3 Test multi-appareils

- ✅ Desktop
- ✅ Tablette
- ✅ Smartphone

---

## 📊 Suivi de migration

Utilisez cette checklist pour suivre vos visites :

| Visite | URL | Ré-exporté | Uploadé | Testé | OK |
|--------|-----|------------|---------|-------|-----|
| Exemple 1 | https://... | ✅ | ✅ | ✅ | ✅ |
| Exemple 2 | https://... | ✅ | ✅ | ⏳ | ❌ |
| Exemple 3 | https://... | ⏳ | ❌ | ❌ | ❌ |

---

## 🆘 Dépannage

### Problème : "Erreur 404 toujours présente"

**Solutions :**
1. Vérifiez que vous avez bien uploadé **tous** les fichiers
2. Vérifiez les permissions sur le serveur
3. Videz le cache du navigateur (Ctrl+Shift+R)
4. Vérifiez que les dossiers ont la bonne casse :
   ```bash
   ls -la /var/www/ma-visite/panovisu/libs/
   # Doit afficher : jqueryMenu, openLayers (avec majuscules)
   ```

### Problème : "La visite ne s'affiche pas du tout"

**Solutions :**
1. Vérifiez que `index.html` est bien à la racine
2. Vérifiez les permissions (755 pour dossiers, 644 pour fichiers)
3. Consultez les logs du serveur web :
   ```bash
   tail -f /var/log/apache2/error.log
   # ou
   tail -f /var/log/nginx/error.log
   ```

### Problème : "L'ancien projet ne s'ouvre pas"

**Solutions :**
1. Vérifiez l'intégrité du fichier `.pano`
2. Consultez les logs : `logs/editeurpanovisu.log`
3. Essayez d'ouvrir un projet de sauvegarde

---

## 💡 Conseils et astuces

### Optimisation du temps

**Pour plusieurs visites à migrer :**
1. Installez v3.1.0
2. Ré-exportez **toutes** les visites en local (batch)
3. Uploadez-les toutes d'un coup
4. Testez ensuite

### Automatisation de l'upload

**Script FTP batch (exemple) :**
```bash
#!/bin/bash
VISITES=("visite1" "visite2" "visite3")
FTP_USER="votre_user"
FTP_HOST="ftp.votreserveur.com"

for visite in "${VISITES[@]}"; do
    echo "Upload de $visite..."
    lftp -u $FTP_USER,$FTP_PASS $FTP_HOST <<EOF
    mirror -R /local/$visite /public_html/$visite
    bye
EOF
done
```

### Documentation de la migration

Gardez une trace :
- Date de migration
- Version avant/après
- Problèmes rencontrés
- Tests effectués

---

## 📞 Besoin d'aide ?

- **Discussions GitHub** : https://github.com/llang57/editeurPanovisu/discussions
- **Problèmes techniques** : https://github.com/llang57/editeurPanovisu/issues
- **FAQ détaillée** : [FAQ_V3.1.0.md](FAQ_V3.1.0.md)

---

## ✨ Après la migration

Une fois toutes vos visites migrées :

1. ✅ Supprimez les sauvegardes (`.backup`, `.old`)
2. ✅ Mettez à jour votre documentation interne
3. ✅ Informez vos clients/utilisateurs si nécessaire
4. ✅ Désinstallez les anciennes versions

**Félicitations ! Vos visites sont maintenant compatibles avec tous les serveurs !** 🎉
