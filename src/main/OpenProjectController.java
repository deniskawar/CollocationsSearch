package main;

import database.BD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OpenProjectController {
    @FXML
    private TableView openProjectTable;
    @FXML
    private TableColumn projectNameColumn;
    @FXML
    private TableColumn projectIdColumn;
    @FXML
    private Button openProjectButton;
    private List<OpenProjectTableRow> openProjectTableRows = new ArrayList<>();

    private MainController mainController;

    public void getMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        Map<Integer, String> projects = BD.getProjectsFromDB();
        for (Map.Entry<Integer, String> entry : projects.entrySet()) {
            openProjectTableRows.add(new OpenProjectTableRow(entry.getKey(), entry.getValue()));
        }
        ObservableList<OpenProjectTableRow> list = FXCollections.observableArrayList(openProjectTableRows);

        projectIdColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("projectId"));
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("projectName"));

        openProjectTable.setItems(list);
    }
    public void pressOpenProjectButton() {
        if (openProjectTable.getSelectionModel().getSelectedItem() != null) {
            Main.currentProjectId = ((OpenProjectTableRow) openProjectTable.getSelectionModel().getSelectedItem()).getProjectId();
            Main.currentProjectName = ((OpenProjectTableRow) openProjectTable.getSelectionModel().getSelectedItem()).getProjectName();
            Stage stage = (Stage) openProjectTable.getScene().getWindow();
            mainController.getCurrentProjectText().setText("Текущий проект: №" + Main.currentProjectId + " '" + Main.currentProjectName + "'");
            stage.close();
            mainController.makeEverythingAvailable();
        }
    }
    public void pressDeleteProjectButton() {
        if (openProjectTable.getSelectionModel().getSelectedItem() != null && ((OpenProjectTableRow) openProjectTable.getSelectionModel().getSelectedItem()).getProjectId() != Main.currentProjectId) {
            BD.deleteProjectFromDBById(((OpenProjectTableRow) openProjectTable.getSelectionModel().getSelectedItem()).getProjectId());
            openProjectTable.getItems().remove(openProjectTable.getSelectionModel().getSelectedItem());
        }
    }
}
