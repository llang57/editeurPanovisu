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
     * Retourne la valeur de apOutil.
     *
     * @return the apOutil
     */
    public AnchorPane getApOutil() {
        return apOutil;
    }

    /**
     * Définit la valeur de apOutil.
     *
     * @param apOutil the apOutil to set
     */
    public void setApOutil(AnchorPane apOutil) {
        this.apOutil = apOutil;
    }

    /**
     * Retourne la valeur de apPaneOutil.
     *
     * @return the apPaneOutil
     */
    public AnchorPane getApPaneOutil() {
        return apPaneOutil;
    }

    /**
     * Retourne la valeur de bValide.
     *
     * @return the bValide
     */
    public boolean getbValide() {
        return bValide;
    }

    /**
     * Définit la valeur de bValide.
     *
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
     * Retourne la valeur de bInvariant.
     *
     * @return the bInvariant
     */
    public boolean isbInvariant() {
        return bInvariant;
    }

    /**
     * Définit la valeur de bInvariant.
     *
     * @param bInvariant the bInvariant to set
     */
    public void setbInvariant(boolean bInvariant) {
        this.bInvariant = bInvariant;
    }

    /**
     * Retourne la valeur de ivBtnPlusOutil.
     *
     * @return the ivBtnPlusOutil
     */
    public ImageView getIvBtnPlusOutil() {
        return ivBtnPlusOutil;
    }

    /**
     * Définit la valeur de ivBtnPlusOutil.
     *
     * @param ivBtnPlusOutil the ivBtnPlusOutil to set
     */
    public void setIvBtnPlusOutil(ImageView ivBtnPlusOutil) {
        this.ivBtnPlusOutil = ivBtnPlusOutil;
    }

}
