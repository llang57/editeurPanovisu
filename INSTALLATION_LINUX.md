# 📦 Installation EditeurPanovisu sur Linux

Guide complet d'installation de l'application EditeurPanovisu sur un système Linux vierge.

**Version du guide :** 1.0  
**Date :** Octobre 2025  
**Build minimum requis :** 3500

---

## 📋 Prérequis système

- **OS :** Ubuntu 20.04+ / Debian 11+ / distributions similaires
- **Java :** OpenJDK 25 (requis)
- **RAM :** 4 GB minimum, 8 GB recommandé
- **Espace disque :** 500 MB pour l'application + espace pour les projets
- **Environnement graphique :** X11 ou Wayland avec support OpenGL

---

## 🔧 Installation des dépendances

### 1. Mise à jour du système

```bash
sudo apt update
sudo apt upgrade -y
```

### 2. Installation de Java (OpenJDK 25)

```bash
# Installer OpenJDK 25
sudo apt install -y openjdk-25-jdk

# Si OpenJDK 25 n'est pas disponible dans les dépôts par défaut :
# Ajouter le PPA OpenJDK
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt update
sudo apt install -y openjdk-25-jdk

# Vérifier l'installation
java -version
```

La version doit afficher Java 25.

### 3. Installation de JavaFX avec support 3D

```bash
# Installer tous les composants OpenJFX (critique pour le support 3D)
sudo apt install -y openjfx libopenjfx-java libopenjfx-jni

# Vérifier que les bibliothèques sont présentes
ls -la /usr/share/openjfx/lib/
```

Vous devriez voir des fichiers `.so` (bibliothèques natives Linux).

### 4. Installation du support OpenGL

```bash
# Bibliothèques Mesa OpenGL (essentielles pour JavaFX 3D)
sudo apt install -y \
  libgl1-mesa-glx \
  libgl1-mesa-dri \
  libglu1-mesa \
  mesa-utils

# Bibliothèques graphiques supplémentaires
sudo apt install -y libglvnd0 libglvnd-dev libglfw3
```

### 5. (Optionnel) Pilotes GPU NVIDIA

**Uniquement si vous avez une carte NVIDIA :**

```bash
# Détecter les pilotes disponibles
ubuntu-drivers devices

# Installer le pilote recommandé (exemple: 535)
sudo apt install -y nvidia-driver-535

# Redémarrer obligatoirement après installation
sudo reboot
```

### 6. Vérification du support OpenGL

Après installation, vérifiez que OpenGL fonctionne :

```bash
# Afficher la version OpenGL
glxinfo | grep "OpenGL version"

# Vérifier le rendu direct (doit être "Yes")
glxinfo | grep "direct rendering"

# Test visuel avec un cube 3D animé
glxgears
```

**Sortie attendue :**
```
OpenGL version string: 4.x Mesa xx.x.x
direct rendering: Yes
```

Si `glxgears` affiche des engrenages qui tournent, OpenGL fonctionne correctement.

---

## 📥 Installation de l'application

### 1. Créer le répertoire d'installation

**Option recommandée : Installation système dans /opt**

```bash
sudo mkdir -p /opt/editeurpanovisu
sudo chown $USER:$USER /opt/editeurpanovisu
cd /opt/editeurpanovisu
```

**Option alternative : Installation utilisateur**

```bash
# Installation dans le répertoire personnel
mkdir -p ~/editeurpanovisu
cd ~/editeurpanovisu
```

> 💡 **Note :** Le script de lancement détecte automatiquement son répertoire, vous pouvez donc installer l'application n'importe où.

### 2. Télécharger les fichiers

**Option A : Depuis Windows via SCP**

```bash
# Depuis votre machine Windows (PowerShell)
scp target/editeurPanovisu-3.3.2-SNAPSHOT.jar utilisateur@serveur:/opt/editeurpanovisu/
scp lancer-panovisu-linux.sh utilisateur@serveur:/opt/editeurpanovisu/
scp -r theme utilisateur@serveur:/opt/editeurpanovisu/
scp -r css utilisateur@serveur:/opt/editeurpanovisu/
scp -r images utilisateur@serveur:/opt/editeurpanovisu/
scp -r configPV utilisateur@serveur:/opt/editeurpanovisu/
scp -r panovisu utilisateur@serveur:/opt/editeurpanovisu/
```

**Option B : Via clé USB**

1. Copiez tous les fichiers du projet sur une clé USB
2. Montez la clé sur Linux et copiez les fichiers :

```bash
cp /media/usb/editeurPanovisu-3.3.2-SNAPSHOT.jar /opt/editeurpanovisu/
cp /media/usb/lancer-panovisu-linux.sh /opt/editeurpanovisu/
cp -r /media/usb/{theme,css,images,configPV,panovisu} /opt/editeurpanovisu/
```

### 3. Créer le script de lancement

Si vous n'avez pas copié `lancer-panovisu-linux.sh`, créez-le :

```bash
nano /opt/editeurpanovisu/lancer-panovisu.sh
```

Collez ce contenu :

```bash
#!/bin/bash

# Script de lancement EditeurPanovisu pour Linux avec support 3D
# Build 3500+

# Détecter automatiquement le répertoire du script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

# Variables d'environnement pour forcer JavaFX 3D
export PRISM_FORCEGL=true
export PRISM_FORCEGPU=true
export PRISM_ORDER=es2,sw

# Forcer OpenGL dans le système
export LIBGL_ALWAYS_SOFTWARE=0
export LIBGL_ALWAYS_INDIRECT=0

echo "=== Lancement EditeurPanovisu ==="
echo "Répertoire: $SCRIPT_DIR"
echo "Configuration JavaFX 3D activée (ES2 Pipeline)"
echo ""
echo "=== Test OpenGL ==="
glxinfo | grep "OpenGL version" || echo "⚠️  glxinfo non disponible"
echo ""

# Lancement de l'application
java \
  --module-path /usr/share/openjfx/lib \
  --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media \
  -Dprism.forceGPU=true \
  -Dprism.order=es2,sw \
  -jar editeurPanovisu-3.3.2-SNAPSHOT.jar 2>&1 | tee editeur-panovisu.log

echo ""
echo "=== Log sauvegardé dans editeur-panovisu.log ==="
```

Sauvegardez avec `Ctrl+O`, `Entrée`, puis quittez avec `Ctrl+X`.

### 4. Rendre le script exécutable

```bash
chmod +x /opt/editeurpanovisu/lancer-panovisu.sh
```

### 5. Structure finale des fichiers

Votre répertoire `/opt/editeurpanovisu` doit contenir :

```
/opt/editeurpanovisu/
├── editeurPanovisu-3.3.2-SNAPSHOT.jar  (fichier principal)
├── lancer-panovisu.sh                   (script de lancement)
├── theme/                               (thèmes visuels)
├── css/                                 (feuilles de style)
├── images/                              (icônes et images)
├── configPV/                            (configurations)
└── panovisu/                            (visualiseur panoramique)
```

---

## 🚀 Lancement de l'application

### Première exécution

```bash
cd /opt/editeurpanovisu
./lancer-panovisu.sh
```

**Au premier lancement :**
- L'application peut prendre 10-15 secondes à démarrer
- Un fichier `editeur-panovisu.log` sera créé avec les logs détaillés
- La configuration sera initialisée dans `~/.config/editeurpanovisu/`

### Vérification du support 3D

Dans les logs du terminal, cherchez ces lignes :

```
Initialized prism pipeline: com.sun.prism.es2.ES2Pipeline
Graphics Vendor: Mesa/NVIDIA/Intel
OpenGL version: 4.x
```

✅ **ES2Pipeline** = Support 3D activé  
❌ **SwPipeline** = Mode logiciel uniquement (pas de 3D)

### Test du visualiseur panoramique

1. Lancez l'application
2. Ouvrez un projet existant ou créez-en un nouveau
3. Ajoutez une image panoramique équirectangulaire
4. Cliquez sur l'image dans la liste

**Résultat attendu :**
- Le visualiseur 3D s'affiche avec navigation interactive
- Vous pouvez faire pivoter la vue avec la souris
- Les contrôles de navigation (boutons, boussole) sont visibles

**Si vous voyez "Fonction non supportée" :**
- Le support 3D n'est pas activé
- Consultez la section Dépannage ci-dessous

---

## 🐛 Dépannage

### Problème : "Fonction non supportée" dans le visualiseur

**Diagnostic :**
```bash
cd /opt/editeurpanovisu
./diagnostic-javafx-3d.sh
```

Regardez la ligne `SCENE3D:` dans les résultats.

**Solution si SCENE3D: false**

1. **Réinstaller OpenJFX complet :**
   ```bash
   sudo apt purge openjfx libopenjfx-java libopenjfx-jni
   sudo apt autoremove
   sudo apt install -y openjfx libopenjfx-java libopenjfx-jni
   ```

2. **Vérifier les pilotes GPU :**
   ```bash
   # Pour NVIDIA
   nvidia-smi
   
   # Pour Intel/AMD (Mesa)
   glxinfo | grep renderer
   ```

3. **Forcer le rendu logiciel en dernier recours :**
   
   Modifiez `lancer-panovisu.sh` :
   ```bash
   export LIBGL_ALWAYS_SOFTWARE=1
   export PRISM_ORDER=sw
   ```

### Problème : Application ne démarre pas

**1. Vérifier Java :**
```bash
java -version
# Doit afficher Java 25
```

**2. Vérifier le JAR :**
```bash
ls -lh editeurPanovisu-3.3.2-SNAPSHOT.jar
# Doit faire ~50-80 MB
```

**3. Vérifier les permissions :**
```bash
chmod +x lancer-panovisu.sh
chmod 644 editeurPanovisu-3.3.2-SNAPSHOT.jar
```

**4. Consulter les logs :**
```bash
cat editeur-panovisu.log | grep -i error
cat editeur-panovisu.log | grep -i exception
```

### Problème : Erreur "module javafx.xxx not found"

**Solution :**
```bash
# Vérifier l'emplacement d'OpenJFX
ls /usr/share/openjfx/lib/

# Si vide ou inexistant, réinstaller
sudo apt install --reinstall openjfx
```

### Problème : Images ne se chargent pas

**Vérifier les répertoires de ressources :**
```bash
cd /opt/editeurpanovisu
ls -R theme/ css/ images/
```

Si des dossiers manquent, recopiez-les depuis la version Windows.

### Problème : Erreur OpenCL

**Messages comme :**
```
⚠️  OpenCL non disponible sur ce système - Mode CPU activé
```

**C'est normal !** L'application fonctionne sans OpenCL. OpenCL est optionnel pour l'accélération GPU des transformations d'images. L'application utilise automatiquement le CPU comme fallback.

Pour activer OpenCL (optionnel) :
```bash
# Pour NVIDIA
sudo apt install -y nvidia-opencl-icd

# Pour AMD
sudo apt install -y mesa-opencl-icd

# Pour Intel
sudo apt install -y intel-opencl-icd
```

---

## 🔄 Mise à jour de l'application

Pour mettre à jour vers une nouvelle version :

```bash
# 1. Sauvegarder vos projets
cp -r ~/.config/editeurpanovisu ~/.config/editeurpanovisu.backup

# 2. Remplacer le JAR
cd /opt/editeurpanovisu
mv editeurPanovisu-3.3.2-SNAPSHOT.jar editeurPanovisu-3.3.2-SNAPSHOT.jar.old
# Copiez le nouveau JAR ici

# 3. Mettre à jour les ressources si nécessaire
# Copiez theme/, css/, images/ mis à jour

# 4. Relancer l'application
./lancer-panovisu.sh
```

---

## 📝 Création d'un lanceur de bureau

Pour lancer l'application depuis le menu d'applications :

```bash
nano ~/.local/share/applications/editeurpanovisu.desktop
```

Contenu :

```ini
[Desktop Entry]
Name=Editeur Panovisu
Comment=Éditeur de visites virtuelles panoramiques
Exec=/opt/editeurpanovisu/lancer-panovisu.sh
Icon=/opt/editeurpanovisu/images/logo.png
Terminal=false
Type=Application
Categories=Graphics;Photography;
```

Sauvegardez et rendez exécutable :

```bash
chmod +x ~/.local/share/applications/editeurpanovisu.desktop
```

L'application apparaîtra dans votre menu Applications.

---

## 🔐 Configuration multi-utilisateurs

Pour installer l'application pour tous les utilisateurs :

```bash
# 1. Installer dans /opt (déjà fait)
# 2. Créer un lanceur global
sudo nano /usr/share/applications/editeurpanovisu.desktop

# Même contenu que ci-dessus

# 3. Permissions
sudo chmod 755 /opt/editeurpanovisu
sudo chmod 755 /opt/editeurpanovisu/lancer-panovisu.sh
sudo chmod 644 /opt/editeurpanovisu/editeurPanovisu-3.3.2-SNAPSHOT.jar
```

Chaque utilisateur aura sa propre configuration dans `~/.config/editeurpanovisu/`.

---

## 📊 Vérification de l'installation

Script de test complet :

```bash
#!/bin/bash
echo "=== Test d'installation EditeurPanovisu ==="
echo ""

echo "1. Java version :"
java -version
echo ""

echo "2. OpenJFX présent :"
ls /usr/share/openjfx/lib/ | grep javafx.graphics
echo ""

echo "3. OpenGL fonctionnel :"
glxinfo | grep "OpenGL version"
echo ""

echo "4. Fichiers application :"
ls -lh /opt/editeurpanovisu/editeurPanovisu-3.3.2-SNAPSHOT.jar
echo ""

echo "5. Script de lancement :"
test -x /opt/editeurpanovisu/lancer-panovisu.sh && echo "✅ Exécutable" || echo "❌ Non exécutable"
echo ""

echo "=== Fin du test ==="
```

---

## 📞 Support

### Logs utiles pour le support

Si vous rencontrez un problème, fournissez ces informations :

```bash
# Version du système
lsb_release -a

# Version Java
java -version

# Version OpenGL
glxinfo | grep "OpenGL version"

# Paquets JavaFX installés
dpkg -l | grep openjfx

# Dernières lignes du log
tail -50 /opt/editeurpanovisu/editeur-panovisu.log
```

### Fichiers de configuration

- Configuration utilisateur : `~/.config/editeurpanovisu/`
- Projets par défaut : `~/PanovisuProjects/`
- Logs : `/opt/editeurpanovisu/editeur-panovisu.log`

---

## ✅ Checklist d'installation

- [ ] Java 25 installé (requis)
- [ ] OpenJFX avec support 3D installé
- [ ] Mesa OpenGL installé
- [ ] `glxgears` fonctionne
- [ ] JAR copié dans `/opt/editeurpanovisu/`
- [ ] Ressources (theme, css, images) copiées
- [ ] Script `lancer-panovisu.sh` exécutable
- [ ] Application se lance sans erreur
- [ ] Visualiseur 3D fonctionne (pas de "fonction non supportée")
- [ ] Logs montrent "ES2Pipeline initialized"

---

**Date de dernière mise à jour :** 22 octobre 2025  
**Version du guide :** 1.0  
**Build testé :** 3500
