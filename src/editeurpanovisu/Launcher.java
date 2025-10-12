package editeurpanovisu;

import javafx.application.Application;

/**
 * Classe de lancement pour jpackage
 * Cette classe wrapper est nécessaire pour lancer une application JavaFX
 * depuis un JAR shaded. Elle utilise Application.launch() au lieu d'appeler
 * directement main() pour garantir l'initialisation correcte du toolkit JavaFX.
 */
public class Launcher {
    public static void main(String[] args) {
        // Lancement de l'application JavaFX via Application.launch()
        // Ceci initialise correctement le toolkit JavaFX même dans un JAR shaded
        Application.launch(EditeurPanovisu.class, args);
    }
}
