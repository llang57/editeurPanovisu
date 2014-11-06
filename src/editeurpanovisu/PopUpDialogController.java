/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editeurpanovisu;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controleur de la Popup d'info
 * 
 * @author llang
 */
public class PopUpDialogController {

    /**
     *
     */
    public static Stage stPopUp;

    @FXML
    Button btnQuitte;
    @FXML
    Hyperlink hlLeMondea360;

    /**
     *
     */
    @FXML
    public void handleQuitteAction() {
        stPopUp.hide();
    }

    /**
     *
     * @throws URISyntaxException
     */
    @FXML
    public void handleOuvreLien() throws URISyntaxException {
        try {
            java.awt.Desktop.getDesktop().browse(new URI("http://lemondea360.fr"));
        } catch (IOException ex) {
            Logger.getLogger(PopUpDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void popUpHandler(){
    }
    
    /**
     *
     * @throws Exception
     */
    public void affichePopup() throws Exception {
        stPopUp= new Stage(StageStyle.UTILITY);
        stPopUp.initModality(Modality.APPLICATION_MODAL);        
        Pane panePopup = (Pane) FXMLLoader.load(getClass().getResource("popUpAccueil.fxml"));        
        Scene scnPopup = new Scene(panePopup);
        stPopUp.setScene(scnPopup);
        stPopUp.show();
    }

    
}
