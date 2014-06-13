/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.repertAppli;
import java.awt.Dimension;
import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author llang
 */
public class AideDialogController {

    private static final ResourceBundle rb = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
    private static Stage STAide;
    private static AnchorPane myPane;
    private static Button btnAnnuler;
    private static Scene scene2;

    public static void affiche() {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int) tailleEcran.getHeight() - 20;
        int largeur = (int) tailleEcran.getWidth() - 20;
        STAide = new Stage(StageStyle.UTILITY);
        STAide.initModality(Modality.APPLICATION_MODAL);
        STAide.setResizable(false);
        myPane = new AnchorPane();
        myPane.setPrefWidth(largeur-100);
        myPane.setPrefHeight(hauteur-100);
        
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load("file:"+repertAppli + File.separator+"aide/aide.html");
        browser.setLayoutX(5);
        browser.setLayoutY(5);
        browser.setPrefWidth(largeur-110);
        browser.setPrefHeight(hauteur-200);
        scene2 = new Scene(myPane, largeur-100, hauteur-100, Color.web("#666970"));
        STAide.setScene(scene2);
        STAide.show();
        btnAnnuler = new Button(rb.getString("aide.fermer"));
        btnAnnuler.setLayoutX(largeur-250);
        btnAnnuler.setLayoutY(hauteur-150);
        myPane.getChildren().addAll(browser,btnAnnuler);
        btnAnnuler.setOnAction((ActionEvent e) -> {
            STAide.hide();
        });
    }

}
