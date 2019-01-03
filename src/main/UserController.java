package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import words.Decoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class UserController {
    private File inputFile;
    private File originalTextFile;
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
    @FXML
    private Button submitButton;
    @FXML
    private Button loadFileButton;
    @FXML
    private Button findCollocationsButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea originalTextArea;


    public void pressLoadFileButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll (
            new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        fileChooser.setTitle("Выбрать файл");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            inputFile = file;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"Cp1251"))){
            originalTextFile = new File(bufferedReader.readLine().split("<")[1].split(">")[0]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(originalTextFile),"Cp1251"))) {
            String s = bufferedReader.readLine();
            while (s != null) {

                originalTextArea.appendText(s +"\n");
                s = bufferedReader.readLine();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }


        findCollocationsButton.setDisable(false);

    }
    public void pressFindCollocationsButton() {
        findCollocations();
        findCollocationsButton.setDisable(true);
        loadFileButton.setDisable(true);
        submitButton.setDisable(false);
        cancelButton.setDisable(false);
    }
    public void pressSubmitButton() {
        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void pressCancelButton() {
        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void initialize() {
        findCollocationsButton.setDisable(true);
        submitButton.setDisable(true);
        cancelButton.setDisable(true);

        ///////////////////////////////////////////
        TableRow tableRow1 = new TableRow("Abcd", "123", true, new CheckBox());
        TableRow tableRow2 = new TableRow("Fghi", "456", true, new CheckBox());
        TableRow tableRow3 = new TableRow("Jklm", "789", false, new CheckBox());

        ObservableList<TableRow> list = FXCollections.observableArrayList(tableRow1, tableRow2, tableRow3);

        wordColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("homonym"));
        leftRightColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Boolean>("leftRight"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<TableRow, CheckBox>("choice"));

        collocationsTable.setItems(list);

        //////////////////////////////////////////////
        if (AuthorizationController.isNeuralNetworkMode())  initializeWithNeuralNetworkMethod(); else initializeWithKnowledgeDatabaseMethod();
    }
    public void initializeWithNeuralNetworkMethod() {
    }
    public void initializeWithKnowledgeDatabaseMethod() {

    }
    public void findCollocations() {
        Map<String,Integer> characteristicsInfo = new HashMap<String, Integer>(){
            {
                put("Part of speech", 11); // часть речи
                put("Kind of word", 4); // род
                put("Case", 7); // падеж
                put("Number", 3); // число
                put("Transitivity", 3); // переходность
                put("Voice", 3); // залог
            }
        };

        Decoder decoder = new Decoder(characteristicsInfo);
        decoder.decodeInputFileToArray(inputFile);

    }

}
