/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author LANG Laurent
 */
public class Equi2CubeDialogController {

    private static Stage STEqui2Cube ;

    @FXML
    static private Button btnAnnuler;

    @FXML
    public void annulerE2C() {
        System.out.println("Annuler");
        
        STEqui2Cube.hide();
    }

    public void Equi2CubeDialogController() {
    }

    public void afficheEqui2Cube() throws Exception {
        STEqui2Cube= new Stage(StageStyle.UTILITY);
        STEqui2Cube.setTitle("Transformation d'équirectangulaire en faces de cube");
        STEqui2Cube.initModality(Modality.APPLICATION_MODAL);
        STEqui2Cube.setResizable(false);
        Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("equi2CubeDialog.fxml"));
        Scene scene2 = new Scene(myPane);
        STEqui2Cube.setScene(scene2);
        STEqui2Cube.show();
    }


}
