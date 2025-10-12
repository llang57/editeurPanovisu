# Installation d'EditeurPanovisu

## Installation du MSI

1. Double-cliquez sur `EditeurPanovisu-2.0.0-SNAPSHOT.msi`
2. Suivez les instructions d'installation
3. L'application sera installée dans : `C:\Users\[VotreNom]\AppData\Local\EditeurPanovisu`

## ⚠️ Configuration post-installation OBLIGATOIRE

**L'exécutable EditeurPanovisu.exe ne fonctionne pas correctement.**  
Vous devez utiliser le lanceur `Lancer_EditeurPanovisu.vbs` à la place.

### Étape 1 : Créer le raccourci Bureau

Ouvrez PowerShell et exécutez cette commande :

```powershell
$WshShell = New-Object -ComObject WScript.Shell
$Shortcut = $WshShell.CreateShortcut("$env:USERPROFILE\Desktop\EditeurPanovisu.lnk")
$Shortcut.TargetPath = "$env:LOCALAPPDATA\EditeurPanovisu\app\Lancer_EditeurPanovisu.vbs"
$Shortcut.IconLocation = "$env:LOCALAPPDATA\EditeurPanovisu\EditeurPanovisu.ico"
$Shortcut.Save()
```

### Étape 2 : Créer le raccourci Menu Démarrer

Exécutez également cette commande :

```powershell
$WshShell = New-Object -ComObject WScript.Shell
$Shortcut = $WshShell.CreateShortcut("$env:APPDATA\Microsoft\Windows\Start Menu\Programs\EditeurPanovisu.lnk")
$Shortcut.TargetPath = "$env:LOCALAPPDATA\EditeurPanovisu\app\Lancer_EditeurPanovisu.vbs"
$Shortcut.IconLocation = "$env:LOCALAPPDATA\EditeurPanovisu\EditeurPanovisu.ico"
$Shortcut.Save()
```

### Explication technique

Le lanceur standard d'EditeurPanovisu.exe a un problème :
- Il ne définit pas le bon répertoire de travail
- L'application ne trouve pas ses ressources (css/, images/, etc.)

Le fichier `Lancer_EditeurPanovisu.vbs` corrige ce problème en :
- Définissant le répertoire de travail sur `app/`
- Lançant Java avec les bons paramètres
- S'exécutant sans fenêtre de console visible

## Utilisation

Après avoir créé les raccourcis :
- Double-cliquez sur l'icône **EditeurPanovisu** sur le Bureau
- Ou lancez depuis le Menu Démarrer

**Ne lancez PAS** l'exécutable EditeurPanovisu.exe directement !

## Désinstallation

1. Panneau de configuration > Programmes > Désinstaller un programme
2. Sélectionnez "EditeurPanovisu"
3. Supprimez manuellement les raccourcis créés (Bureau et Menu Démarrer)

## Note pour le développeur

**Solution permanente recommandée** : Modifier le code Java pour utiliser des chemins absolus ou des ressources classpath au lieu de `new File("css/clair.css")`.

Fichiers concernés :
- `EditeurPanovisu.java` ligne 4653 : `File f = new File("css/clair.css");`
- `EditeurPanovisu.java` ligne 10227 : `File f = new File("css/clair.css");`

Probablement d'autres occurrences à rechercher avec :
```
new File\("(?!/)
```

Cette correction permettrait d'utiliser le lanceur standard jpackage sans workaround.
