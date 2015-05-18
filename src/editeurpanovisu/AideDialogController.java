/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getStrRepertAppli;
import java.awt.Dimension;
import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Affichage de l'aide contenue dans le fichier aide/aide.html
 * 
 * @author llang
 */
public class AideDialogController {

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.getLocale());
    private static Stage stAide;
    private static AnchorPane apAideDialog;
    private static Button btnAnnuler;
    private static Scene sceneAideDialog;

    public static void affiche() {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int iHauteur = (int) tailleEcran.getHeight() - 20;
        int iLargeur = (int) tailleEcran.getWidth() - 20;
        stAide = new Stage(StageStyle.UTILITY);
        stAide.initModality(Modality.APPLICATION_MODAL);
        stAide.setResizable(false);
        apAideDialog = new AnchorPane();
        apAideDialog.setPrefWidth(iLargeur-100);
        apAideDialog.setPrefHeight(iHauteur-100);
        
        WebView wvNavigateur = new WebView();
        WebEngine weNavigateur = wvNavigateur.getEngine();
        weNavigateur.load("file:"+getStrRepertAppli() + File.separator+"aide/aide.html"); 
        wvNavigateur.setLayoutX(5);
        wvNavigateur.setLayoutY(5);
        wvNavigateur.setPrefWidth(iLargeur-110);
        wvNavigateur.setPrefHeight(iHauteur-200);
        sceneAideDialog = new Scene(apAideDialog, iLargeur-100, iHauteur-100, Color.web("#666970"));
        stAide.setScene(sceneAideDialog);
        stAide.show();
        btnAnnuler = new Button(rb.getString("aide.fermer"));
        btnAnnuler.setLayoutX(iLargeur-250);
        btnAnnuler.setLayoutY(iHauteur-150);
        apAideDialog.getChildren().addAll(wvNavigateur,btnAnnuler);
        btnAnnuler.setOnAction((ActionEvent e) -> {
            stAide.hide();
        });
    }

}
