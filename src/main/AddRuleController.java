package main;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddRuleController {
    @FXML
    private Text homonymPositionText;
    @FXML
    private TableView addRuleTableView;
    @FXML
    private Button addRuleButton;
    @FXML
    private RadioButton firstWordRadioButton;
    @FXML
    private RadioButton secondWordRadioButton;
    @FXML
    private TableColumn characteristicName;
    @FXML
    private TableColumn firstWordChoice;
    @FXML
    private TableColumn secondWordChoice;
    @FXML
    private TableColumn alternativeChoice;


    public void initialize() {
        List<AddRuleTableRow> addRuleTableRows = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, String>> entry : Main.getCharacteristicsInfoForRules().entrySet()) {
            String characteristicName = entry.getKey();
            ChoiceBox firstWordChoiceBox = new ChoiceBox();
            ChoiceBox secondWordChoiceBox = new ChoiceBox();
            ChoiceBox alternativeChoiceBox = new ChoiceBox();
            for (Map.Entry<Integer, String> entry1 : entry.getValue().entrySet()) {
                firstWordChoiceBox.getItems().add(entry1.getValue());
                secondWordChoiceBox.getItems().add(entry1.getValue());
                alternativeChoiceBox.getItems().add(entry1.getValue());
            }
            firstWordChoiceBox.getSelectionModel().selectFirst();
            secondWordChoiceBox.getSelectionModel().selectFirst();
            alternativeChoiceBox.getSelectionModel().selectFirst();
            addRuleTableRows.add(new AddRuleTableRow(characteristicName, firstWordChoiceBox, secondWordChoiceBox, alternativeChoiceBox));
        }
        ObservableList<AddRuleTableRow> list = FXCollections.observableArrayList(addRuleTableRows);

        characteristicName.setCellValueFactory(new PropertyValueFactory<AddRuleTableRow, String>("characteristicName"));
        firstWordChoice.setCellValueFactory(new PropertyValueFactory<AddRuleTableRow, ChoiceBox>("firstWordChoice"));
        secondWordChoice.setCellValueFactory(new PropertyValueFactory<AddRuleTableRow, ChoiceBox>("secondWordChoice"));
        alternativeChoice.setCellValueFactory(new PropertyValueFactory<AddRuleTableRow, ChoiceBox>("alternativeChoice"));

        addRuleTableView.setItems(list);
    }
    public void pressAddRuleButton() {
        DB.addRuleToDB(addRuleTableView, Main.currentProjectId, firstWordRadioButton.isSelected());
        Stage stage = (Stage) addRuleTableView.getScene().getWindow();
        stage.close();
    }
    public void pressFirstWordRadioButton() {
        secondWordRadioButton.setSelected(false);
    }
    public void pressSecondWordRadioButton() {
        firstWordRadioButton.setSelected(false);
    }


}
