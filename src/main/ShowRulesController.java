package main;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import words.Rule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowRulesController {
    @FXML
    private Button deleteRuleButton;
    @FXML
    private TableColumn ruleId;
    @FXML
    private TableColumn characteristicName;
    @FXML
    private TableColumn firstWordCharacteristic;
    @FXML
    private TableColumn secondWordCharacteristic;
    @FXML
    private TableColumn alternativeCharacteristic;
    @FXML
    private TableColumn homonymPosition;
    @FXML
    private TableView showRulesTable;

    public void initialize() {
        List<Rule> rules = DB.getRulesFromDB(Main.currentProjectId);
        List<ShowRulesTableRow> showRulesTableRows = new ArrayList<>();

        List<Map<Integer, String>> characteristicsMaps = new ArrayList<>(Main.getCharacteristicsInfoForRules().values());

        for (Rule rule : rules) {
            int i = 0;
            StringBuilder characteristicName = new StringBuilder();
            StringBuilder firstWordCharacteristic = new StringBuilder();
            StringBuilder secondWordCharacteristic = new StringBuilder();
            StringBuilder alternativeWordCharacteristic = new StringBuilder();
            for (Map.Entry<String, Integer> entry : Main.getCharacteristicsInfo().entrySet()) {
                List<String> characteristicNames = new ArrayList<>(characteristicsMaps.get(i).values());
                characteristicName.append(entry.getKey() + "\n");
                firstWordCharacteristic.append(characteristicNames.get(rule.getFirstWordCharacteristics().get(i).getValue()) + "\n");
                secondWordCharacteristic.append(characteristicNames.get(rule.getSecondWordCharacteristics().get(i).getValue()) + "\n");
                alternativeWordCharacteristic.append(characteristicNames.get(rule.getAlternativeCharacteristics().get(i).getValue()) + "\n");
                i++;
            }
            showRulesTableRows.add(new ShowRulesTableRow(rule.getRuleId(), characteristicName.toString(), firstWordCharacteristic.toString(), secondWordCharacteristic.toString(), alternativeWordCharacteristic.toString(), (rule.isHomonymLeft()) ? 1 : 2));
        }


        ObservableList<ShowRulesTableRow> list = FXCollections.observableArrayList(showRulesTableRows);

        ruleId.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, Integer>("ruleId"));
        characteristicName.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, String>("characteristicName"));
        firstWordCharacteristic.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, String>("firstWordCharacteristic"));
        secondWordCharacteristic.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, String>("secondWordCharacteristic"));
        alternativeCharacteristic.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, String>("alternativeCharacteristic"));
        homonymPosition.setCellValueFactory(new PropertyValueFactory<ShowRulesTableRow, Integer>("homonymPosition"));

        showRulesTable.setItems(list);

    }
    public void pressDeleteRuleButton() {
        DB.deleteRuleFromBD(((ShowRulesTableRow)(showRulesTable.getSelectionModel().getSelectedItem())).getRuleId());
        showRulesTable.getItems().remove(showRulesTable.getSelectionModel().getSelectedItem());
    }
}
