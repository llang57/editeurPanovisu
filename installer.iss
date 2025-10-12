; Script Inno Setup pour EditeurPanovisu
; Installe l'application avec les bons raccourcis vers le lanceur VBS

#define MyAppName "EditeurPanovisu"
#define MyAppVersion "2.0.0"
#define MyAppPublisher "PanoVisu - Laurent LANG"
#define MyAppExeName "Lancer_EditeurPanovisu.vbs"

[Setup]
; Informations de base
AppId={{A1B2C3D4-E5F6-7890-1234-567890ABCDEF}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={localappdata}\{#MyAppName}
DefaultGroupName=PanoVisu
DisableProgramGroupPage=yes
; Pas de privilèges admin requis
PrivilegesRequired=lowest
PrivilegesRequiredOverridesAllowed=dialog
OutputDir=target\installer
OutputBaseFilename=EditeurPanovisu-Setup-{#MyAppVersion}
SetupIconFile=images\panovisu.ico
Compression=lzma2/fast
SolidCompression=yes
WizardStyle=modern
DiskSpanning=no
UninstallDisplayIcon={app}\EditeurPanovisu.ico

[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; Copier TOUS les fichiers de l'image d'application
Source: "target\dist-msi\EditeurPanovisu\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Ne pas utiliser "Flags: ignoreversion" sur les exécutables système

[Icons]
; Raccourci Menu Démarrer - pointe vers le VBS
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\EditeurPanovisu.ico"
; Raccourci Bureau - pointe vers le VBS
Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\EditeurPanovisu.ico"; Tasks: desktopicon

[Run]
; Proposer de lancer l'application après installation
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent nowait
