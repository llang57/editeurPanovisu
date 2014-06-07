/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.repertAppli;
import java.io.File;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    public static final String styleDefaut = "retinavert";
    public static final String styleHotSpotDefaut = "hotspotvert.png";
    public Pane tabInterface;
    private static HBox HBInterface;
    private static AnchorPane APVisualisation;
    private static VBox VBOutils;
    private static RadioButton RBClair;
    private static RadioButton RBSombre;
    private static ImageView IMVisualisation;
    final ToggleGroup grpImage = new ToggleGroup();
    private static Image imageClaire;
    private static Image imageSombre;
    private static HBox HBbarreBoutons;
    private static HBox HBOutils;
    private static ImageView IVInfo;
    private static Image ImgInfo;
    private static ImageView IVAide;
    private static Image ImgAide;
    private static ImageView IVAutoRotation;
    private static Image ImgAutoRotation;
    private static ImageView IVModeSouris;
    private static Image ImgModeSouris;
    private static ImageView IVPleinEcran;
    private static Image ImgPleinEcran;
    private static HBox HBZoom;
    private static ImageView IVZoomPlus;
    private static Image ImgZoomPlus;
    private static ImageView IVZoomMoins;
    private static Image ImgZoomMoins;
    private static HBox HBDeplacements;
    private static ImageView IVHaut;
    private static Image ImgHaut;
    private static ImageView IVBas;
    private static Image ImgBas;
    private static ImageView IVGauche;
    private static Image ImgGauche;
    private static ImageView IVDroite;
    private static Image ImgDroite;
    private static ImageView IVHotSpot;
    private static Image ImgHotSpot;

    private void afficheBouton(String position, int dX, int dY, int taille, String styleBoutons,String styleHS) {
        String repertBoutons = "file:" + repertAppli + File.separator + "panovisu/images/" + styleBoutons;
        String repertHotSpots = "file:" + repertAppli + File.separator + "panovisu/images/hotspots";
        HBbarreBoutons = new HBox();
        ImgHotSpot=new Image(repertHotSpots+File.separator+styleHS);
        IVHotSpot=new ImageView(ImgHotSpot);
        IVHotSpot.setFitWidth(30);
        IVHotSpot.setPreserveRatio(true);
        IVHotSpot.setLayoutX(510);
        IVHotSpot.setLayoutY(310);
        int nombreBoutons = 11;
        double tailleBarre = (double) nombreBoutons * (double) taille;
        System.out.println(tailleBarre);
        HBbarreBoutons.setPrefWidth(tailleBarre);
        HBbarreBoutons.setPrefHeight((double) taille);
        HBbarreBoutons.setMinWidth(tailleBarre);
        HBbarreBoutons.setMinHeight((double) taille);
        HBbarreBoutons.setMaxWidth(tailleBarre);
        HBbarreBoutons.setMaxHeight((double)taille);
        HBDeplacements = new HBox();
        HBZoom = new HBox();
        HBOutils = new HBox();
        HBbarreBoutons.getChildren().addAll(HBDeplacements, HBZoom, HBOutils);
        System.out.println("repertBoutons" + repertBoutons);
        APVisualisation.getChildren().addAll(HBbarreBoutons,IVHotSpot);
        ImgHaut = new Image(repertBoutons + File.separator + "haut.png");
        ImgBas = new Image(repertBoutons + File.separator + "bas.png");
        ImgGauche = new Image(repertBoutons + File.separator + "gauche.png");
        ImgDroite = new Image(repertBoutons + File.separator + "droite.png");
        ImgZoomPlus = new Image(repertBoutons + File.separator + "zoomin.png");
        ImgZoomMoins = new Image(repertBoutons + File.separator + "zoomout.png");
        ImgInfo = new Image(repertBoutons + File.separator + "info.png");
        ImgAide = new Image(repertBoutons + File.separator + "aide.png");
        ImgModeSouris = new Image(repertBoutons + File.separator + "souris.png");
        ImgPleinEcran = new Image(repertBoutons + File.separator + "fs.png");
        ImgAutoRotation = new Image(repertBoutons + File.separator + "rotation.png");
        IVHaut = new ImageView(ImgHaut);
        IVHaut.setFitWidth(taille);
        IVHaut.setFitHeight(taille);
        IVBas = new ImageView(ImgBas);
        IVBas.setFitWidth(taille);
        IVBas.setFitHeight(taille);
        IVGauche = new ImageView(ImgGauche);
        IVGauche.setFitWidth(taille);
        IVGauche.setFitHeight(taille);
        IVDroite = new ImageView(ImgDroite);
        IVDroite.setFitWidth(taille);
        IVDroite.setFitHeight(taille);
        IVZoomPlus = new ImageView(ImgZoomPlus);
        IVZoomPlus.setFitWidth(taille);
        IVZoomPlus.setFitHeight(taille);
        IVZoomMoins = new ImageView(ImgZoomMoins);
        IVZoomMoins.setFitWidth(taille);
        IVZoomMoins.setFitHeight(taille);
        IVInfo = new ImageView(ImgInfo);
        IVInfo.setFitWidth(taille);
        IVInfo.setFitHeight(taille);
        IVPleinEcran = new ImageView(ImgPleinEcran);
        IVPleinEcran.setFitWidth(taille);
        IVPleinEcran.setFitHeight(taille);
        IVModeSouris = new ImageView(ImgModeSouris);
        IVModeSouris.setFitWidth(taille);
        IVModeSouris.setFitHeight(taille);
        IVAide = new ImageView(ImgAide);
        IVAide.setFitWidth(taille);
        IVAide.setFitHeight(taille);
        IVAutoRotation = new ImageView(ImgAutoRotation);
        IVAutoRotation.setFitWidth(taille);
        IVAutoRotation.setFitHeight(taille);
        String positVert = position.split(":")[0];
        String positHor = position.split(":")[1];
        HBDeplacements.getChildren().addAll(IVGauche, IVHaut, IVBas, IVDroite);
        HBZoom.getChildren().addAll(IVZoomPlus, IVZoomMoins);
        HBOutils.getChildren().addAll(IVPleinEcran, IVModeSouris, IVAutoRotation, IVInfo, IVAide);
        double LX = 0;
        double LY = 0;
        switch (positVert) {
            case "haut":
                LY = IMVisualisation.getLayoutY()+dY;
                System.out.println("haut");
                break;
            case "bas":
                LY = IMVisualisation.getLayoutY() + IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight()-dY;
                System.out.println("bas");
                break;
            case "milieu":
                LY = IMVisualisation.getLayoutY() + (IMVisualisation.getFitHeight() - HBbarreBoutons.getPrefHeight()) / 2.d-dY;
                System.out.println("milieu");

                break;
        }

        switch (positHor) {
            case "droite":
                LX = IMVisualisation.getLayoutX() + IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth()-dX;
                System.out.println("droite");
                break;
            case "gauche":
                LX = IMVisualisation.getLayoutX()+dX;
                System.out.println("gauche");
                break;
            case "centre":
                LX = IMVisualisation.getLayoutX() + (IMVisualisation.getFitWidth() - HBbarreBoutons.getPrefWidth()) / 2+dX;
                System.out.println("centre");
                break;
        }
        System.out.println("positVert : " + positVert + " LY =" + LY + "  |   positHor : " + positHor + " LX =" + LX);
        HBbarreBoutons.setLayoutX(LX);
        HBbarreBoutons.setLayoutY(LY);
    }

    public void creeInterface(int width, int height) {
        imageClaire = new Image("file:" + repertAppli + File.separator + "images/claire.jpg");
        imageSombre = new Image("file:" + repertAppli + File.separator + "images/sombre.jpg");

        tabInterface = new Pane();
        HBInterface = new HBox();
        HBInterface.setPrefWidth(width);
        HBInterface.setPrefHeight(height);
        tabInterface.getChildren().add(HBInterface);
        APVisualisation = new AnchorPane();
        APVisualisation.setPrefWidth(width * 0.8);
        APVisualisation.setPrefHeight(height);
        VBOutils = new VBox();
        VBOutils.setPrefWidth(width * 0.2);
        VBOutils.setPrefHeight(height);
        VBOutils.setStyle("-fx-background-color : #ccc;");
        HBInterface.getChildren().addAll(APVisualisation, VBOutils);
        IMVisualisation = new ImageView(imageClaire);
        IMVisualisation.setFitWidth(1200);
        IMVisualisation.setFitHeight(800);
        IMVisualisation.setSmooth(true);
        double LX = (width * 0.8 - IMVisualisation.getFitWidth()) / 2;
        IMVisualisation.setLayoutX(LX);
        IMVisualisation.setLayoutY(20);
        RBClair = new RadioButton("Image claire");
        RBSombre = new RadioButton("Image Sombre");
        RBClair.setToggleGroup(grpImage);
        RBSombre.setToggleGroup(grpImage);
        APVisualisation.getChildren().addAll(RBClair, RBSombre);
        RBClair.setLayoutX(LX + 40);
        RBClair.setLayoutY(830);
        RBClair.setSelected(true);
        RBSombre.setLayoutX(LX + 180);
        RBSombre.setLayoutY(830);
        RBClair.setUserData("claire");
        RBSombre.setUserData("sombre");
        APVisualisation.getChildren().add(IMVisualisation);
        afficheBouton("bas:centre", 0, 10, 30, styleDefaut,styleHotSpotDefaut);
        grpImage.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (grpImage.getSelectedToggle() != null) {
                switch (grpImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        IMVisualisation.setImage(imageClaire);
                        break;
                    case "sombre":
                        IMVisualisation.setImage(imageSombre);
                        break;
                }
            }
        });
    }

}
