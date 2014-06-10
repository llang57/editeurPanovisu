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
 *
 * @author llang
 */
public class popUpHandler {

    /**
     *
     */
    public static Stage popUp;

    @FXML
    Button btnQuitte;
    @FXML
    Hyperlink leMondea360;

    /**
     *
     */
    @FXML
    public void handleQuitteAction() {
        popUp.close();
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
            Logger.getLogger(popUpHandler.class.getName()).log(Level.SEVERE, null, ex);
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
        popUp= new Stage(StageStyle.UTILITY);
        popUp.initModality(Modality.APPLICATION_MODAL);        
        Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("popUpAccueil.fxml"));        
        Scene scene2 = new Scene(myPane);
        popUp.setScene(scene2);
        popUp.show();
    }

    
}
