package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import words.Collocation;
import words.Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController {
    private File inputFile;
    private File originalTextFile;
    private List<TableRow> tableRows = new ArrayList<>();
    private List<Collocation> collocations;
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
        for (int i = 0; i < tableRows.size(); i++) {
            System.out.println(((ObservableList<TableRow>) collocationsTable.getItems()).get(i).isChoice().isSelected()); //!!!! TASK: SHOW CHECKBOX VALUE !!!
        }
        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void pressCancelButton() {
        collocationsTable.setItems(null);

        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void initialize() {
        findCollocationsButton.setDisable(true);
        submitButton.setDisable(true);
        cancelButton.setDisable(true);


        if (AuthorizationController.isNeuralNetworkMode())  initializeWithNeuralNetworkMethod(); else initializeWithKnowledgeDatabaseMethod();
    }
    public void initializeWithNeuralNetworkMethod() {
    }
    public void initializeWithKnowledgeDatabaseMethod() {

    }
    public void findCollocations() {


        Decoder decoder = new Decoder(Main.getCharacteristicsInfo());
        collocations = decoder.decodeInputFileToArray(inputFile);

        for (int i = 0; i < collocations.size(); i++) {
            Main.getNeuralNetwork().performCalculation(collocations.get(i));
        }

        refreshTable(collocations);

        System.out.println();

    }
    public void refreshTable(List<Collocation> collocations) {

        for (int i = 0; i < collocations.size(); i++) {
            Collocation collocation = collocations.get(i);
            tableRows.add(new TableRow(collocation.getFirstWord(), collocation.getSecondWord(), collocation.isCollocation(), new CheckBox()));
        }

        ObservableList<TableRow> list = FXCollections.observableArrayList(tableRows);

        wordColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("homonym"));
        leftRightColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Boolean>("result"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<TableRow, CheckBox>("choice"));

        collocationsTable.setItems(list);
    }

}
