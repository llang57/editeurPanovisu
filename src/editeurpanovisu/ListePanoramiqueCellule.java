package editeurpanovisu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static editeurpanovisu.EditeurPanovisu.affichePanoChoisit;
import static editeurpanovisu.OrdrePanoramique.cellulesPanoramiques;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ListePanoramiqueCellule extends ListCell<String> {

    private final ImageView imageView = new ImageView();
    private int iCell = 0;

    public ListePanoramiqueCellule() {
        ListCell cellule = this;
        setOnMouseClicked((me)->{
           int inumPano=Integer.parseInt(getListView().getSelectionModel().getSelectedItem());
           affichePanoChoisit(inumPano);
        });
        setOnDragDetected(evenement -> {
            if (getItem() == null) {
                return;
            }

            ObservableList<String> items = getListView().getItems();

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(getItem());

            dragboard.setDragView(
                    cellulesPanoramiques.get(
                            items.indexOf(
                                    getItem()
                            )
                    ).getImgPanoramique()
            );
            dragboard.setContent(content);

            evenement.consume();
        });

        setOnDragOver(evenement -> {
            if (evenement.getGestureSource() != cellule
                    && evenement.getDragboard().hasString()) {
                evenement.acceptTransferModes(TransferMode.MOVE);
            }

//            evenement.consume();
        });

        setOnDragEntered(evenement -> {
            if (evenement.getGestureSource() != cellule
                    && evenement.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(evenement -> {
            if (evenement.getGestureSource() != cellule
                    && evenement.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(evenement -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = evenement.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                ObservableList<String> items = getListView().getItems();
                int draggedIdx = items.indexOf(db.getString());
                int thisIdx = items.indexOf(getItem());
                int i1;
                int i2;
                if (draggedIdx < thisIdx) {
                    i1 = draggedIdx;
                    i2 = thisIdx;
                    PanoramiqueCellule cpDragged = cellulesPanoramiques.get(draggedIdx);
                    String strItems = items.get(draggedIdx);
                    for (int i = i1; i < i2; i++) {
                        String strTemp = items.get(i + 1);
                        items.set(i, strTemp);
                        PanoramiqueCellule temp = cellulesPanoramiques.get(i + 1);
                        cellulesPanoramiques.set(i, temp);
                    }
                    cellulesPanoramiques.set(thisIdx, cpDragged);
                    items.set(thisIdx, strItems);
                } else {
                    i2 = draggedIdx;
                    i1 = thisIdx;
                    PanoramiqueCellule cpDragged = cellulesPanoramiques.get(draggedIdx);
                    String strItems = items.get(draggedIdx);
                    for (int i = i2 - 1; i >= i1; i--) {
                        String strTemp = items.get(i);
                        items.set(i + 1, strTemp);
                        PanoramiqueCellule temp = cellulesPanoramiques.get(i);
                        cellulesPanoramiques.set(i + 1, temp);
                    }
                    cellulesPanoramiques.set(thisIdx, cpDragged);
                    items.set(thisIdx, strItems);
                }
                //System.out.println(items);
                List<String> itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(itemscopy);
                EditeurPanovisu.rafraichitListePano();
                success = true;
            }
            evenement.setDropCompleted(success);

            evenement.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            iCell++;
            AnchorPane apCellFactory = new AnchorPane();
            apCellFactory.setMinHeight(40);
            apCellFactory.setMaxHeight(40);
            apCellFactory.setMaxWidth(295);
            Pane paneFond = new Pane(imageView);
            imageView.setImage(
                    cellulesPanoramiques.get(
                            getListView().getItems().indexOf(item)
                    ).getImgPanoramique()
            );
            paneFond.setLayoutX(0);
            paneFond.setLayoutY(2);
            paneFond.setPrefSize(72, 37);
            imageView.setLayoutX(1);
            imageView.setLayoutY(1);
            paneFond.setStyle("-fx-border-width : 1px;-fx-border-color:#700;-fx-border-style : solid;");
            Label lbl1 = new Label(cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getStrTitrePanoramique().toUpperCase());
            lbl1.setId("lvTitre"+cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getiNumPano());
            lbl1.setStyle("-fx-font-weight : bold;-fx-font-size : 1.0em; -fx-font-family : Verdana,Arial,sans-serif;");
            Label lbl2 = new Label(cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getStrTitrePanoramiqueLigne2());
            lbl2.setStyle("-fx-font-size : 0.8em; -fx-font-family : Verdana,Arial,sans-serif;");
            lbl1.setLayoutX(cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getImgPanoramique().getWidth() + 10);
            lbl1.setLayoutY(5);
            lbl1.setMaxWidth(300 - cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getImgPanoramique().getWidth() - 30);
            lbl2.setMaxWidth(300 - cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getImgPanoramique().getWidth() - 30);

            lbl2.setLayoutX(cellulesPanoramiques.get(getListView().getItems().indexOf(item)).getImgPanoramique().getWidth() + 10);
            lbl2.setLayoutY(25);
            apCellFactory.getChildren().addAll(paneFond, lbl1, lbl2);
            setGraphic(apCellFactory);
        }
    }
}
