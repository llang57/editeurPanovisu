/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author llang
 */
public class PaneOutil {

    private AnchorPane apOutil;
    private AnchorPane apPaneOutil;
    private Label lblPanelTitre;
    private boolean bValide;
    private boolean bInvariant=false;
    private ImageView ivBtnPlusOutil;

    public static void deplieReplie(AnchorPane apTitre, ImageView ivBtnPlusTitre) {
        if (apTitre.isVisible()) {
            apTitre.setMaxHeight(0);
            ivBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
        } else {
            apTitre.setMaxHeight(apTitre.getPrefHeight());
            ivBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
        }
        apTitre.setVisible(!apTitre.isVisible());

    }

    PaneOutil(String strTitre, AnchorPane apContenu, double largeur) {
        affiche(strTitre, apContenu, largeur, false);
    }

    PaneOutil(String strTitre, AnchorPane apContenu, double largeur, boolean bOuvert) {
        affiche(strTitre, apContenu, largeur, bOuvert);
    }

    PaneOutil(boolean bInvariant, String strTitre, AnchorPane apContenu, double largeur) {
        this.bInvariant = bInvariant;
        affiche(strTitre, apContenu, largeur, false);
    }

    PaneOutil(boolean bInvariant, String strTitre, AnchorPane apContenu, double largeur, boolean bOuvert) {
        this.bInvariant = bInvariant;
        affiche(strTitre, apContenu, largeur, bOuvert);
    }

    private void affiche(String strTitre, AnchorPane apContenu, double largeur, boolean bOuvert) {
        apOutil = apContenu;
        apOutil.setLayoutX(10);
        apOutil.setLayoutY(40);
        apPaneOutil = new AnchorPane();
        lblPanelTitre = new Label("    " + strTitre);
        lblPanelTitre.setPrefWidth(largeur);
        if (isbInvariant()) {
            lblPanelTitre.getStyleClass().add("titreOutilBleu");
        } else {
            lblPanelTitre.getStyleClass().add("titreOutil");
        }
        lblPanelTitre.setPadding(new Insets(5));
        lblPanelTitre.setLayoutX(10);
        lblPanelTitre.setLayoutY(10);
        apOutil.setMinHeight(0);

        if (!bOuvert) {
            ivBtnPlusOutil = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
            apOutil.setMaxHeight(0);
            apOutil.setVisible(false);
        } else {
            ivBtnPlusOutil = new ImageView(new Image("file:" + "images/moins.png", 20, 20, true, true));
            apOutil.setVisible(true);
        }
        ivBtnPlusOutil.setLayoutX(largeur - 20);
        ivBtnPlusOutil.setLayoutY(11);
        apPaneOutil.getChildren().addAll(lblPanelTitre, ivBtnPlusOutil, apOutil);

        lblPanelTitre.setOnMouseClicked((me) -> {
            deplieReplie(apOutil, ivBtnPlusOutil);
        });
        ivBtnPlusOutil.setOnMouseClicked((me) -> {
            deplieReplie(apOutil, ivBtnPlusOutil);
        });

    }

    /**
     * @return the apOutil
     */
    public AnchorPane getApOutil() {
        return apOutil;
    }

    /**
     * @param apOutil the apOutil to set
     */
    public void setApOutil(AnchorPane apOutil) {
        this.apOutil = apOutil;
    }

    /**
     * @return the apPaneOutil
     */
    public AnchorPane getApPaneOutil() {
        return apPaneOutil;
    }

    /**
     * @return the bValide
     */
    public boolean getbValide() {
        return bValide;
    }

    /**
     * @param bValide the bValide to set
     */
    public void setbValide(boolean bValide) {
        this.bValide = bValide;
        if (!bValide) {
            lblPanelTitre.getStyleClass().remove("titreOutil");
            lblPanelTitre.getStyleClass().remove("titreOutilRouge");
            lblPanelTitre.getStyleClass().add("titreOutil");

        } else {
            lblPanelTitre.getStyleClass().remove("titreOutil");
            lblPanelTitre.getStyleClass().remove("titreOutilRouge");
            lblPanelTitre.getStyleClass().add("titreOutilRouge");
        }
    }

    /**
     * @return the bInvariant
     */
    public boolean isbInvariant() {
        return bInvariant;
    }

    /**
     * @param bInvariant the bInvariant to set
     */
    public void setbInvariant(boolean bInvariant) {
        this.bInvariant = bInvariant;
    }

    /**
     * @return the ivBtnPlusOutil
     */
    public ImageView getIvBtnPlusOutil() {
        return ivBtnPlusOutil;
    }

    /**
     * @param ivBtnPlusOutil the ivBtnPlusOutil to set
     */
    public void setIvBtnPlusOutil(ImageView ivBtnPlusOutil) {
        this.ivBtnPlusOutil = ivBtnPlusOutil;
    }

}
