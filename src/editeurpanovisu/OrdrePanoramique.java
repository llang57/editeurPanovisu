/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.getPanoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.getiNombrePanoramiques;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author LANG Laurent
 */
public class OrdrePanoramique {

    /**
     * Hauteur d'une ligne de la liste, en pixels.
     *
     * <p>Cette valeur est <b>imposée</b> à la ListView via {@code setFixedCellSize()} et sert
     * en même temps au calcul de la hauteur du conteneur. Les deux ne peuvent donc plus diverger.</p>
     *
     * <p>Historiquement la hauteur était supposée valoir 46 px sans être imposée. Les thèmes
     * introduits en v3.x (AtlantaFX, MaterialFX, FlatLaf) réduisent le padding de {@code .list-cell}
     * et ramènent la ligne à ~39 px : le VirtualFlow figeait alors son nombre de cellules sur
     * l'ancienne hypothèse et laissait une bande blanche en bas de liste, masquant les derniers
     * panoramiques (issue #16).</p>
     */
    static final double HAUTEUR_LIGNE = 46;

    /** Nombre maximal de lignes affichées avant que la liste ne défile. */
    static final int MAX_LIGNES_VISIBLES = 10;

    /** Largeur de la liste, en pixels. */
    private static final double LARGEUR_LISTE = 300;

    /** Marge basse conservée sous la dernière ligne, en pixels. */
    static final double MARGE_BASSE = 5;

    /**
     * Calcule la hauteur du cadre de la liste pour un nombre d'éléments donné.
     *
     * <p>Extraite de {@link #dimensionneListe} pour être vérifiable sans interface
     * graphique. L'invariant à préserver est que cette hauteur corresponde à un nombre
     * entier de lignes de {@link #HAUTEUR_LIGNE} pixels, majorée d'une marge : c'est sa
     * violation qui a produit l'issue #16.</p>
     *
     * @param nbElements Nombre d'éléments réellement présents dans la liste
     * @return La hauteur en pixels, plafonnée à {@link #MAX_LIGNES_VISIBLES} lignes
     */
    static double hauteurListe(int nbElements) {
        return Math.min(
                HAUTEUR_LIGNE * nbElements + MARGE_BASSE,
                HAUTEUR_LIGNE * MAX_LIGNES_VISIBLES + MARGE_BASSE);
    }

    private static ObservableList<String> strPanos = FXCollections.observableArrayList();
    public static final ObservableList<PanoramiqueCellule> cellulesPanoramiques = FXCollections.observableArrayList();
    private final AnchorPane apListePanoramiques = new AnchorPane();
    private ListView<String> lstStrPanos;

    /**
     * Dimensionne la liste et son conteneur en fonction du nombre réel d'éléments affichés.
     *
     * <p>Impose la hauteur de ligne ({@link #HAUTEUR_LIGNE}) pour que le calcul de hauteur
     * corresponde exactement au rendu, quel que soit le thème actif. Au-delà de
     * {@link #MAX_LIGNES_VISIBLES} lignes, la liste défile.</p>
     *
     * @param liste      La ListView à dimensionner
     * @param nbElements Nombre d'éléments réellement présents dans la liste
     */
    private void dimensionneListe(ListView<String> liste, int nbElements) {
        liste.setFixedCellSize(HAUTEUR_LIGNE);
        double dHauteur = hauteurListe(nbElements);
        liste.setMinSize(LARGEUR_LISTE, dHauteur);
        liste.setPrefSize(LARGEUR_LISTE, dHauteur);
        liste.setMaxSize(LARGEUR_LISTE, dHauteur);
        apListePanoramiques.setMinSize(LARGEUR_LISTE, dHauteur);
        apListePanoramiques.setPrefSize(LARGEUR_LISTE, dHauteur);
        apListePanoramiques.setMaxSize(LARGEUR_LISTE, dHauteur);
    }

    /**
     * Crée et initialise la liste ordonnée des panoramiques
     * 
     * <p>Génère l'interface permettant de réorganiser les panoramiques par glisser-déposer.</p>
     */
    public void creeListe() {
        cellulesPanoramiques.clear();
        strPanos.clear();
        for (int i = 0; i < getiNombrePanoramiques(); i++) {
            PanoramiqueCellule cellPano = new PanoramiqueCellule();
            cellPano.setImgPanoramique(getPanoramiquesProjet()[i].getImgPanoRectListe());
            cellPano.setStrTitrePanoramique(getPanoramiquesProjet()[i].getStrTitrePanoramique());
            cellPano.setStrTitrePanoramiqueLigne2(getPanoramiquesProjet()[i].getStrNomFichier().substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length()));
            cellPano.setiNumPano(i);
            strPanos.add(Integer.toString(i));
            cellulesPanoramiques.add(cellPano);
        }
        lstStrPanos = new ListView<>(strPanos);
        lstStrPanos.setCellFactory(param -> new ListePanoramiqueCellule());
        dimensionneListe(lstStrPanos, strPanos.size());
        apListePanoramiques.getChildren().add(lstStrPanos);
        lstStrPanos.setLayoutX(0);
        lstStrPanos.setLayoutY(0);
    }

    /**
     * Crée et initialise la liste ordonnée des panoramiques avec ordre spécifique
     *
     * @param strOrdre Ordre de tri des panoramiques
     */
    public void creeListe(String strOrdre) {
        String[] strOrd1 = strOrdre.split(",");
        cellulesPanoramiques.clear();
        strPanos.clear();
        for (String strOrd11 : strOrd1) {
            int iPano = Integer.parseInt(strOrd11);
            PanoramiqueCellule cellPano = new PanoramiqueCellule();
            cellPano.setImgPanoramique(getPanoramiquesProjet()[iPano].getImgPanoRectListe());
            cellPano.setStrTitrePanoramique(getPanoramiquesProjet()[iPano].getStrTitrePanoramique());
            cellPano.setStrTitrePanoramiqueLigne2(getPanoramiquesProjet()[iPano].getStrNomFichier().substring(getPanoramiquesProjet()[iPano].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPano].getStrNomFichier().length()));
            cellPano.setiNumPano(iPano);
            strPanos.add(strOrd11);
            cellulesPanoramiques.add(cellPano);
        }
        lstStrPanos = new ListView<>(strPanos);
        lstStrPanos.setCellFactory(param -> new ListePanoramiqueCellule());
        // dimensionner sur le nombre d'éléments réellement listés, pas sur le nombre de
        // panoramiques du projet : un .pvu dont l'ordre est incomplet donnerait sinon un
        // cadre trop haut, partiellement vide (issue #16)
        dimensionneListe(lstStrPanos, strPanos.size());
        apListePanoramiques.getChildren().add(lstStrPanos);
        lstStrPanos.setLayoutX(0);
        lstStrPanos.setLayoutY(0);
    }

    /**
     * Supprime un élément de la liste des panoramiques
     * 
     * @param iElement Index de l'élément à supprimer
     */
    public void supprimerElement(int iElement) {
        int iTrouve = -1;
        for (int i = 0; i < strPanos.size(); i++) {
            if (strPanos.get(i).equals(Integer.toString(iElement))) {
                iTrouve = i;
            }
        }
        if (iTrouve != -1) {
            strPanos.remove(iTrouve);
            ObservableList<String> strPanos1 = strPanos;
            cellulesPanoramiques.clear();
            apListePanoramiques.getChildren().remove(lstStrPanos);
            lstStrPanos = null;
            strPanos = FXCollections.observableArrayList();
            for (int i = 0; i < strPanos1.size(); i++) {
                int iPano = Integer.parseInt(strPanos1.get(i));
                if (iPano > iElement) {
                    iPano--;
                }
                strPanos.add(Integer.toString(iPano));
                PanoramiqueCellule cellPano = new PanoramiqueCellule();
                cellPano.setImgPanoramique(getPanoramiquesProjet()[iPano].getImgPanoRectListe());
                cellPano.setStrTitrePanoramique(getPanoramiquesProjet()[iPano].getStrTitrePanoramique());
                cellPano.setStrTitrePanoramiqueLigne2(getPanoramiquesProjet()[iPano].getStrNomFichier().substring(getPanoramiquesProjet()[iPano].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPano].getStrNomFichier().length()));
                cellPano.setiNumPano(iPano);
                cellulesPanoramiques.add(cellPano);
            }
            lstStrPanos = new ListView<>(strPanos);
            lstStrPanos.setCellFactory(param -> new ListePanoramiqueCellule());
            dimensionneListe(lstStrPanos, strPanos.size());
            apListePanoramiques.getChildren().add(lstStrPanos);
        }
    }

    /**
     * Rafraîchit la liste des panoramiques affichée
     * 
     * <p>Reconstruit complètement la liste visuelle des panoramiques en :</p>
     * <ol>
     *   <li>Vidant la liste actuelle des cellules</li>
     *   <li>Créant de nouvelles cellules pour chaque panoramique</li>
     *   <li>Associant les images miniatures et titres</li>
     *   <li>Mettant à jour l'affichage de la ListView</li>
     * </ol>
     * 
     * <p>Appelée après un réordonnancement ou une modification de la liste
     * des panoramiques pour synchroniser l'affichage.</p>
     * 
     * @see PanoramiqueCellule
     * @see #strPanos
     * @see #cellulesPanoramiques
     */
    public void rafraichitListe() {
        ObservableList<String> strPanos1 = strPanos;
        cellulesPanoramiques.clear();
        apListePanoramiques.getChildren().remove(lstStrPanos);
        lstStrPanos = null;
        strPanos = FXCollections.observableArrayList();
        for (int i = 0; i < strPanos1.size(); i++) {
            int iPano = Integer.parseInt(strPanos1.get(i));
            strPanos.add(strPanos1.get(i));
            PanoramiqueCellule cellPano = new PanoramiqueCellule();
            cellPano.setImgPanoramique(getPanoramiquesProjet()[iPano].getImgPanoRectListe());
            cellPano.setStrTitrePanoramique(getPanoramiquesProjet()[iPano].getStrTitrePanoramique());
            cellPano.setStrTitrePanoramiqueLigne2(getPanoramiquesProjet()[iPano].getStrNomFichier().substring(getPanoramiquesProjet()[iPano].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[iPano].getStrNomFichier().length()));
            cellPano.setiNumPano(iPano);

            cellulesPanoramiques.add(cellPano);
        }
        lstStrPanos = new ListView<>(strPanos);
        lstStrPanos.setCellFactory(param -> new ListePanoramiqueCellule());
        dimensionneListe(lstStrPanos, strPanos.size());
        apListePanoramiques.getChildren().add(lstStrPanos);

    }

    /**
     * Ajoute les nouveaux panoramiques à la liste
     * 
     * <p>Synchronise la liste avec les panoramiques du projet.</p>
     */
    public void ajouteNouveauxPanos() {
        ObservableList<String> strPanos1 = FXCollections.observableArrayList();
        int iTaillePano = 0;
        if (strPanos == null) {
            iTaillePano = 0;
            strPanos = FXCollections.observableArrayList();
        } else {
            iTaillePano = strPanos.size();
            strPanos1 = strPanos;
            strPanos = FXCollections.observableArrayList();
            for (String strPano1 : strPanos1) {
                strPanos.add(strPano1);
            }
        }
        for (int i = iTaillePano; i < getiNombrePanoramiques(); i++) {
            PanoramiqueCellule cellPano = new PanoramiqueCellule();
            cellPano.setImgPanoramique(getPanoramiquesProjet()[i].getImgPanoRectListe());
            cellPano.setStrTitrePanoramique(getPanoramiquesProjet()[i].getStrTitrePanoramique());
            cellPano.setStrTitrePanoramiqueLigne2(getPanoramiquesProjet()[i].getStrNomFichier().substring(getPanoramiquesProjet()[i].getStrNomFichier().lastIndexOf(File.separator) + 1, getPanoramiquesProjet()[i].getStrNomFichier().length()));
            cellPano.setiNumPano(i);
            strPanos.add(Integer.toString(i));
            cellulesPanoramiques.add(cellPano);
        }
        // affecter le champ et non une variable locale : sinon lstStrPanos continue de
        // référencer une ListView orpheline, et le rafraichitListe() suivant n'enlève pas
        // la liste réellement affichée — elles s'empilent alors dans l'AnchorPane
        lstStrPanos = new ListView<>(strPanos);
        lstStrPanos.setCellFactory(param -> new ListePanoramiqueCellule());
        dimensionneListe(lstStrPanos, strPanos.size());
        apListePanoramiques.getChildren().clear();
        apListePanoramiques.getChildren().add(lstStrPanos);
        lstStrPanos.setLayoutX(0);
        lstStrPanos.setLayoutY(0);
    }

    /**
     * Retourne la valeur de strPanos.
     *
     * @return the strPanos
     */
    public ObservableList<String> getStrPanos() {
        return strPanos;
    }

    /**
     * Retourne la valeur de cellulesPanoramiques.
     *
     * @return the cellulesPanoramiques
     */
    public ObservableList<PanoramiqueCellule> getCellulesPanoramiques() {
        return cellulesPanoramiques;
    }

    /**
     * Retourne la valeur de apListePanoramiques.
     *
     * @return the apListePanoramiques
     */
    public AnchorPane getApListePanoramiques() {
        return apListePanoramiques;
    }

}
