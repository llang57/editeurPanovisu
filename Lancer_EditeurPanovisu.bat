@echo off
cd /d "%~dp0"
start "" "runtime\bin\javaw.exe" -Dfile.encoding=UTF-8 --enable-preview --enable-native-access=ALL-UNNAMED -Xms512m -Xmx2048m -jar "editeurPanovisu-3.0.0-SNAPSHOT.jar"
