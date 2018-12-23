package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UserController {
    @FXML
    private TableColumn wordColumn;
    @FXML
    private TableColumn homonymColumn;
    @FXML
    private TableColumn leftRightColumn;
    @FXML
    private TableColumn choiceColumn;
    @FXML
    private TableView collocationsTable;
    public void initialize() {
        /*TableColumn word = new TableColumn<>("Word");
        TableColumn homonym = new TableColumn<>("Homonym");

        collocationsTable.getColumns().addAll(word, homonym);*/


        TableRow tableRow1 = new TableRow("Lalala1", "fjadlkf1js",true, false);
        TableRow tableRow2 = new TableRow("Lalala2", "fjadl2kfjs",true, true);
        TableRow tableRow3 = new TableRow("Lalala3", "fjad3lkfjs",false, false);

        ObservableList<TableRow> list = FXCollections.observableArrayList(tableRow1,tableRow2,tableRow3);

        wordColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String >("homonym"));
        leftRightColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Boolean>("leftRight"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Boolean>("choice"));

        collocationsTable.setItems(list);



    }
}
