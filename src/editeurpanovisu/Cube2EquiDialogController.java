/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class Cube2EquiDialogController{
    public static Stage STCube2Equi;

    @FXML
    static private Button btnAnnuler;
    
    @FXML
    private void annulerC2E(){
        STCube2Equi.close();
    }
    

    public void Cube2EquiDialogController() {
    }
    public void afficheCube2Equi() throws Exception {
        STCube2Equi= new Stage(StageStyle.UTILITY);
        STCube2Equi.setTitle("Transformation de faces de cube en équirectangulaire");
        STCube2Equi.initModality(Modality.APPLICATION_MODAL);
        STCube2Equi.setResizable(false);
        Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("cube2EquiDialog.fxml"));
        Scene scene2 = new Scene(myPane);
        STCube2Equi.setScene(scene2);
        STCube2Equi.show();
    }

}
