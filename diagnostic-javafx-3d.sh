#!/bin/bash

# Script de diagnostic JavaFX 3D pour Linux

echo "=========================================="
echo "   Diagnostic JavaFX 3D - EditeurPanovisu"
echo "=========================================="
echo ""

echo "=== 1. Système d'exploitation ==="
uname -a
lsb_release -a 2>/dev/null || cat /etc/os-release
echo ""

echo "=== 2. Version Java ==="
java -version
echo ""

echo "=== 3. Paquets JavaFX installés ==="
dpkg -l | grep openjfx
echo ""

echo "=== 4. Bibliothèques OpenJFX ==="
ls -la /usr/share/openjfx/lib/ 2>/dev/null || echo "⚠️  Répertoire OpenJFX non trouvé"
echo ""

echo "=== 5. Support OpenGL ==="
glxinfo | grep "OpenGL"
echo ""

echo "=== 6. Pilotes graphiques ==="
lspci | grep -i vga
lspci | grep -i nvidia
echo ""

echo "=== 7. Test simple JavaFX 3D ==="
cat > /tmp/TestJavaFX3D.java << 'EOFTEST'
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

public class TestJavaFX3D extends Application {
    @Override
    public void start(Stage stage) {
        System.out.println("=== Support des fonctionnalités JavaFX ===");
        System.out.println("SCENE3D:   " + Platform.isSupported(ConditionalFeature.SCENE3D));
        System.out.println("GRAPHICS:  " + Platform.isSupported(ConditionalFeature.GRAPHICS));
        System.out.println("CONTROLS:  " + Platform.isSupported(ConditionalFeature.CONTROLS));
        System.out.println("FXML:      " + Platform.isSupported(ConditionalFeature.FXML));
        System.out.println("WEB:       " + Platform.isSupported(ConditionalFeature.WEB));
        System.out.println("MEDIA:     " + Platform.isSupported(ConditionalFeature.MEDIA));
        System.out.println("SWING:     " + Platform.isSupported(ConditionalFeature.SWING));
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
EOFTEST

javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.graphics /tmp/TestJavaFX3D.java 2>&1
if [ $? -eq 0 ]; then
    echo ""
    echo "Compilation réussie. Exécution du test..."
    export PRISM_VERBOSE=true
    export PRISM_FORCEGL=true
    java --module-path /usr/share/openjfx/lib --add-modules javafx.controls,javafx.graphics \
         -Dprism.verbose=true -Dprism.forceGPU=true \
         TestJavaFX3D 2>&1 | grep -A 20 "Support des fonctionnalités"
else
    echo "⚠️  Erreur de compilation du test"
fi
echo ""

echo "=== 8. Variables d'environnement Prism ==="
env | grep PRISM
echo ""

echo "=========================================="
echo "   Fin du diagnostic"
echo "=========================================="
echo ""
echo "💡 Pour résoudre les problèmes SCENE3D:"
echo "   1. Installer: sudo apt install openjfx libopenjfx-java"
echo "   2. Si GPU NVIDIA: sudo apt install nvidia-driver-xxx"
echo "   3. Redémarrer X11/Wayland après installation pilotes"
