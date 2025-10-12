# Installation EditeurPanovisu - Guide Utilisateur

## Pour l'utilisateur final (photographe)

### Installation simple

1. **Téléchargez** le fichier `EditeurPanovisu-2.0.0.msi`

2. **Double-cliquez** sur le fichier MSI

3. **Suivez l'assistant** d'installation :
   - Acceptez la licence
   - Choisissez le dossier d'installation (par défaut : `C:\Users\VotreNom\AppData\Local\EditeurPanovisu`)
   - Cliquez sur "Installer"

4. **C'est tout !** L'application est installée avec :
   - Un raccourci sur le Bureau
   - Un raccourci dans le Menu Démarrer (dossier "PanoVisu")

### Utilisation

- Double-cliquez sur l'icône **EditeurPanovisu** sur votre Bureau
- OU lancez depuis le Menu Démarrer > PanoVisu > EditeurPanovisu

L'application se lance immédiatement, sans fenêtre de console.

### Désinstallation

1. Panneau de configuration > Programmes > Désinstaller un programme
2. Sélectionnez "EditeurPanovisu"
3. Cliquez sur "Désinstaller"

---

## Pour le développeur

### Construction du MSI

Le script Python `build-msi-complet.py` automatise toute la création du MSI :

```bash
python build-msi-complet.py
```

Ce script :
1. Compile le projet avec Maven (`mvn clean package`)
2. Crée une image d'application avec jpackage (runtime Java + JAR + ressources)
3. Supprime l'EXE buggy jpackage
4. Ajoute les lanceurs Batch et VBS
5. Génère automatiquement un fichier WiX avec tous les fichiers
6. Compile le MSI avec WiX Toolset
7. Produit : `target/EditeurPanovisu-2.0.0.msi` (environ 200 MB)

### Prérequis développeur

- **Python 3.8+** (pour le script de build)
- **Maven 3.9+** (pour compiler le projet Java)
- **Java 25** (pour jpackage et le runtime embarqué)
- **WiX Toolset 4.0+** (pour créer le MSI)
  ```
  winget install WixToolset.Wix
  ```

### Pourquoi ce processus complexe ?

#### Problème initial
L'exécutable créé par jpackage (`EditeurPanovisu.exe`) a un bug :
- Fonctionne depuis PowerShell/CMD
- **Ne fonctionne PAS** quand lancé depuis l'Explorateur Windows (double-clic)
- Erreur : "Failed to launch JVM"

#### Cause racine
- L'application utilise des chemins relatifs : `new File("css/clair.css")`
- Ces fichiers sont dans `EditeurPanovisu/app/css/`
- Le lanceur jpackage définit le répertoire de travail à `EditeurPanovisu/` (racine)
- Résultat : fichiers non trouvés → NullPointerException

#### Solution implémentée
1. **Batch launcher** : `Lancer_EditeurPanovisu.bat`
   - Change le répertoire de travail vers `app/`
   - Lance Java directement (pas l'EXE jpackage)
   
2. **VBS launcher** : `Lancer_EditeurPanovisu.vbs`
   - Lance le batch de manière invisible (pas de console)
   - C'est ce fichier qui est ciblé par les raccourcis

3. **MSI personnalisé avec WiX**
   - jpackage crée l'image d'application
   - WiX crée le MSI avec les bons raccourcis
   - Les raccourcis pointent vers le VBS, pas l'EXE buggy

### Solution alternative (future)

Modifier le code Java pour utiliser des chemins absolus ou des ressources classpath :

```java
// Au lieu de :
File f = new File("css/clair.css");

// Utiliser :
File f = new File("app/css/clair.css");
// OU
InputStream is = getClass().getResourceAsStream("/css/clair.css");
```

Fichiers à corriger :
- `EditeurPanovisu.java` lignes 4653, 10227
- Probablement d'autres occurrences de `new File(chemin_relatif)`

Une fois corrigé, le lanceur jpackage standard fonctionnera et tout ce système de batch/VBS sera inutile.

### Structure du MSI final

```
EditeurPanovisu/
├── Lancer_EditeurPanovisu.bat    ← Lance Java avec bon working directory
├── Lancer_EditeurPanovisu.vbs    ← Lance le batch invisiblement (ciblé par raccourcis)
├── EditeurPanovisu.ico            ← Icône de l'application
├── app/
│   ├── editeurPanovisu-2.0.0-SNAPSHOT.jar
│   ├── css/
│   ├── images/
│   ├── panovisu/ (243 fichiers)
│   └── ... (toutes les ressources)
└── runtime/
    └── bin/
        └── javaw.exe              ← Java embarqué
```

### Distribution

Le fichier `target/EditeurPanovisu-2.0.0.msi` est autonome :
- Contient tout le runtime Java (pas besoin d'installer Java)
- Installe dans AppData (pas de droits admin)
- Crée automatiquement les raccourcis fonctionnels
- Prêt à distribuer aux utilisateurs

### Tests

Sur une machine propre :
1. Copier le MSI
2. Double-cliquer pour installer
3. Vérifier que les raccourcis sont créés
4. Double-cliquer sur le raccourci Bureau
5. L'application doit se lancer sans erreur

### Taille du package

- **Image d'application** : ~350 MB (runtime Java complet)
- **MSI compressé** : ~200 MB (compression WiX)
- **Installé** : ~350 MB sur disque

### Maintenance

Pour une nouvelle version :
1. Mettre à jour `<version>` dans `pom.xml`
2. Mettre à jour `--app-version` dans `build-msi-complet.py`
3. Exécuter `python build-msi-complet.py`
4. Distribuer le nouveau MSI

Le MSI utilise un `UpgradeCode` fixe, donc il pourra mettre à jour l'installation existante.
