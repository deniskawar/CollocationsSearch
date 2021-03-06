package main;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainController {
    /*
    private File inputFile;
    private File originalTextFile;
    private List<CollocationsTableRow> tableRows = new ArrayList<>();
    private List<Collocation> collocationsFromFile;
    private List<Collocation> allPastCollocations = new ArrayList<>();
    */

    /*
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
*/
    //___________________________________________________________________________________________

    @FXML
    private MenuItem createProjectMenuItem;
    @FXML
    private MenuItem chooseProjectMenuItem;
    @FXML
    private MenuItem loadFileMenuItem;
    @FXML
    private MenuItem findCollocationsMenuItem;
    @FXML
    private CheckMenuItem knowledgeBaseMenuItem;
    @FXML
    private CheckMenuItem neuralNetworkMenuItem;
    @FXML
    private MenuItem addRuleMenuItem;
    @FXML
    private MenuItem showRulesMenuItem;
    @FXML
    private MenuItem modeMenuItem;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private MenuItem aboutProgramMenuItem;
    @FXML
    private Text currentProjectText;
    @FXML
    private TextArea textArea;
    @FXML
    private TableView collocationsTableView;
    @FXML
    private TableColumn wordColumn;
    @FXML
    private TableColumn homonymColumn;
    @FXML
    private TableColumn neuralNetworkDecisionColumn;
    @FXML
    private TableColumn choiceColumn;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;


    private static int openProjectWindowWidth = 414;
    private static int openProjectWindowHeight = 278;
    private static int createProjectWindowWidth = 261;
    private static int createProjectWindowHeight = 112;
    private static int addRuleWindowWidth = 590;
    private static int addRuleWindowHeight = 273;
    private static int showRulesWindowWidth = 761;
    private static int showRulesWindowHeight = 277;

    private List<Collocation> collocationsFromFile;

    private File fileForAnalysis;
    private List<CollocationsTableRow> collocationsTableRows = new ArrayList<>();

    public void initialize() {
        loadFileMenuItem.setDisable(true);
        findCollocationsMenuItem.setDisable(true);
        knowledgeBaseMenuItem.setDisable(true);
        neuralNetworkMenuItem.setDisable(true);
        addRuleMenuItem.setDisable(true);
        showRulesMenuItem.setDisable(true);
        modeMenuItem.setDisable(true);
        neuralNetworkMenuItem.setSelected(true);
        knowledgeBaseMenuItem.setSelected(false);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);

    }
    public void pressCreateProjectMenuItem() throws IOException{
        DB.setAutoIncrement();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/create_project.fxml"));
        Parent root = fxmlLoader.load();
        CreateProjectController createProjectController = fxmlLoader.getController();
        createProjectController.getMainConroller(this);
        Scene scene = new Scene(root, createProjectWindowWidth, createProjectWindowHeight);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage.getScene().getWindow());
        stage.setTitle("Открыть проект");
        stage.setScene(scene);
        stage.show();
    }
    public void pressOpenProjectMenuItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/open_project.fxml"));
        Parent root = fxmlLoader.load();
        OpenProjectController openProjectController = fxmlLoader.getController();
        openProjectController.getMainController(this);
        Scene scene = new Scene(root, openProjectWindowWidth, openProjectWindowHeight);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage.getScene().getWindow());
        stage.setTitle("Открыть проект");
        stage.setScene(scene);
        stage.show();
    }
    public void pressChooseFileMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(Main.primaryStage);
        if (file != null) {
            fileForAnalysis = file;
            String validationResult = Decoder.fileValidation(fileForAnalysis);
            if (validationResult != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка файла");
                alert.setHeaderText(null);
                alert.setContentText(validationResult);
                alert.show();
            }
            else {
                findCollocationsMenuItem.setDisable(false);
                addRuleMenuItem.setDisable(false);
                showRulesMenuItem.setDisable(false);
                List<Word> words = Decoder.decodeInputFileToArray(fileForAnalysis);
                for (Word word : words) word.defineHomonym();
                List<Collocation> collocations = new ArrayList<>();
                for (int i = 0; i < words.size()-1; i++) {
                    if (words.get(i).isHomonym() || words.get(i+1).isHomonym()) collocations.add(new Collocation(words.get(i), words.get(i+1)));
                }
                this.collocationsFromFile = collocations;
            }
        }
    }
    public void pressKnowledgeBaseMenuItem() {
        knowledgeBaseMenuItem.setSelected(true);
        neuralNetworkMenuItem.setSelected(false);
    }
    public void pressNeuralNetworkMenuItem() {
        knowledgeBaseMenuItem.setSelected(false);
        neuralNetworkMenuItem.setSelected(true);
    }
    public void pressFindCollocationsMenuItem() {
        modeMenuItem.setDisable(true);
        if (neuralNetworkMenuItem.isSelected()) {
            if (DB.isThereAnyNeuralNetworkInProject(Main.currentProjectId)) {
                Main.setNeuralNetwork(DB.getNeuralNetworkFromDB(Main.currentProjectId));
                Main.getNeuralNetwork().setFirstIteration(false);
            }
            for (Collocation collocation : collocationsFromFile) Main.getNeuralNetwork().performCalculation(collocation);
            putNeuralNetworkSearchResultsInTable(collocationsFromFile);

        } else {
            List<Rule> rules = DB.getRulesFromDB(Main.currentProjectId);
            for (Collocation collocation : collocationsFromFile) {
                for (Rule rule : rules) {
                    if (rule.isHomonymLeft() == collocation.getFirstWord().isHomonym()) {
                        boolean isCollocationByKnowledgeBase1 = true;
                        boolean isCollocationByKnowledgeBase2 = true;
                        if (rule.isHomonymLeft()) {
                            for (int i = 0; i < collocation.getFirstWord().getCharacteristics().get(0).size(); i++) {
                                if (rule.getFirstWordCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getFirstWordCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(0).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getFirstWordCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(1).get(i).getValue();
                                }
                                if (rule.getSecondWordCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getSecondWordCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(0).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getSecondWordCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(0).get(i).getValue();
                                }
                                if (rule.getAlternativeCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getAlternativeCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(1).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getAlternativeCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(0).get(i).getValue();
                                }
                            }

                        }
                        else {
                            for (int i = 0; i < collocation.getFirstWord().getCharacteristics().get(0).size(); i++) {
                                if (rule.getFirstWordCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getFirstWordCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(0).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getFirstWordCharacteristics().get(i).getValue() == collocation.getFirstWord().getCharacteristics().get(0).get(i).getValue();
                                }
                                if (rule.getSecondWordCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getSecondWordCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(0).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getSecondWordCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(1).get(i).getValue();
                                }
                                if (rule.getAlternativeCharacteristics().get(i).getValue() != 0) {
                                    isCollocationByKnowledgeBase1 = isCollocationByKnowledgeBase1 && rule.getAlternativeCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(1).get(i).getValue();
                                    isCollocationByKnowledgeBase2 = isCollocationByKnowledgeBase2 && rule.getAlternativeCharacteristics().get(i).getValue() == collocation.getSecondWord().getCharacteristics().get(0).get(i).getValue();
                                }
                            }
                        }
                        if (isCollocationByKnowledgeBase1 || isCollocationByKnowledgeBase2) {
                            collocation.setCollocationByKnowledgeBase(true);
                            collocation.setRuleId(rule.getRuleId());
                        }
                    }
                }
                putKnowledgeBaseSearchResultsInTable(collocationsFromFile);
            }

        }
        submitButton.setDisable(false);
        cancelButton.setDisable(false);
    }
    public void pressAddRuleMenuItem() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/add_rule.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, addRuleWindowWidth, addRuleWindowHeight);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage.getScene().getWindow());
        stage.setTitle("Добавить правило");
        stage.setScene(scene);
        stage.show();


    }
    public void pressShowRulesMenuItem() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/show_rules.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, showRulesWindowWidth, showRulesWindowHeight);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage.getScene().getWindow());
        stage.setTitle("Показать правила");
        stage.setScene(scene);
        stage.show();
    }
    public void pressHelpMenuItem() {

    }
    public void pressAboutProgramMenuItem() {

    }
    public void pressSubmitButton() {
        for (int i = 0; i < collocationsTableRows.size(); i++) {
            collocationsFromFile.get(i).setCollocationReally(((ObservableList<CollocationsTableRow>) collocationsTableView.getItems()).get(i).isChoice().isSelected());
        }
        List<Collocation> collocationsFromDB = DB.getCollocationsFromDB(Main.currentProjectId);
        if (neuralNetworkMenuItem.isSelected()) {
            List<Collocation> collocationsForLearning = new ArrayList<>();
            for (Collocation collocation : collocationsFromFile) collocationsForLearning.add(collocation);
            for (Collocation collocation1 : collocationsFromDB) {
                boolean areCollocationsEquals = false;
                for (Collocation collocation2 : collocationsFromFile) {
                    if ((collocation1.getFirstWord().getName().toLowerCase().equals(collocation2.getFirstWord().getName().toLowerCase()))
                            && (collocation1.getSecondWord().getName().toLowerCase().equals(collocation2.getSecondWord().getName().toLowerCase())))
                        areCollocationsEquals = true;
                }
                if (!areCollocationsEquals) collocationsForLearning.add(collocation1);
            }

            Main.getNeuralNetwork().performLearning(collocationsForLearning);
            DB.deleteNeuralNetworkFromDB(Main.currentProjectId);
            DB.addNeuralNetworkToDB(Main.getNeuralNetwork(), Main.currentProjectId);
        }

        List<Collocation> collocationsForAdding = new ArrayList<>();
        for (Collocation collocationFromFile : collocationsFromFile) {
            boolean areCollocationForAdding = false;
            if (collocationFromFile.isCollocationReally()) {
                areCollocationForAdding = true;
                for (Collocation collocationFromDB : collocationsFromDB) {
                    if (((collocationFromFile.getFirstWord().getName().toLowerCase().equals(collocationFromDB.getFirstWord().getName().toLowerCase()))
                            && (collocationFromFile.getSecondWord().getName().toLowerCase().equals(collocationFromDB.getSecondWord().getName().toLowerCase()))))
                        areCollocationForAdding = false;
                }
            }
            if (areCollocationForAdding) collocationsForAdding.add(collocationFromFile);
        }
        DB.addCollocationsToDB(collocationsForAdding, Main.currentProjectId);

        collocationsTableView.setItems(null);

        cancelButton.setDisable(true);
        submitButton.setDisable(true);
        modeMenuItem.setDisable(false);
    }
    public void pressCancelButton() {
        collocationsTableView.setItems(null);

        cancelButton.setDisable(true);
        submitButton.setDisable(true);
        modeMenuItem.setDisable(false);
    }
    public void makeEverythingAvailable() {
        loadFileMenuItem.setDisable(false);
        modeMenuItem.setDisable(false);
        knowledgeBaseMenuItem.setDisable(false);
        neuralNetworkMenuItem.setDisable(false);
    }
    public Text getCurrentProjectText() {
        return currentProjectText;
    }

    public MenuItem getCreateProjectMenuItem() {
        return createProjectMenuItem;
    }

    public MenuItem getChooseProjectMenuItem() {
        return chooseProjectMenuItem;
    }

    public MenuItem getLoadFileMenuItem() {
        return loadFileMenuItem;
    }

    public MenuItem getFindCollocationsMenuItem() {
        return findCollocationsMenuItem;
    }

    public CheckMenuItem getKnowledgeBaseMenuItem() {
        return knowledgeBaseMenuItem;
    }

    public CheckMenuItem getNeuralNetworkMenuItem() {
        return neuralNetworkMenuItem;
    }

    public MenuItem getAddRuleMenuItem() {
        return addRuleMenuItem;
    }

    public MenuItem getShowRulesMenuItem() {
        return showRulesMenuItem;
    }

    public MenuItem getModeMenuItem() {
        return modeMenuItem;
    }

    public MenuItem getHelpMenuItem() {
        return helpMenuItem;
    }

    public MenuItem getAboutProgramMenuItem() {
        return aboutProgramMenuItem;
    }

    public void putNeuralNetworkSearchResultsInTable(List<Collocation> collocations) {
        neuralNetworkDecisionColumn.setText("Решение НС");
        collocationsTableRows = new ArrayList<>();
            for (Collocation collocation : collocations) {
                String word = collocation.getFirstWord().isHomonym() ? collocation.getSecondWord().getName() : collocation.getFirstWord().getName();
                String homonym = !collocation.getFirstWord().isHomonym() ? collocation.getSecondWord().getName() : collocation.getFirstWord().getName();
                collocationsTableRows.add
                        (new CollocationsTableRow(word, homonym, collocation.isCollocationByNeuralNetworkCalculation() ? "Да" : "Нет", new CheckBox()));
            }

            ObservableList<CollocationsTableRow> list = FXCollections.observableArrayList(collocationsTableRows);
            wordColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("word"));
            homonymColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("homonym"));
            neuralNetworkDecisionColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("result"));
            choiceColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, CheckBox>("choice"));



        collocationsTableView.setItems(list);
    }
    public void putKnowledgeBaseSearchResultsInTable(List<Collocation> collocations) {
        collocationsTableRows = new ArrayList<>();
        neuralNetworkDecisionColumn.setText("№ Правила");
        for (Collocation collocation : collocations) {
            if (collocation.isCollocationByKnowledgeBase()) {
                String word = collocation.getFirstWord().isHomonym() ? collocation.getSecondWord().getName() : collocation.getFirstWord().getName();
                String homonym = !collocation.getFirstWord().isHomonym() ? collocation.getSecondWord().getName() : collocation.getFirstWord().getName();
                collocationsTableRows.add
                        (new CollocationsTableRow(word, homonym, String.valueOf(collocation.getRuleId()), new CheckBox()));
            }
        }

        ObservableList<CollocationsTableRow> list = FXCollections.observableArrayList(collocationsTableRows);
        wordColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("homonym"));
        neuralNetworkDecisionColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("result"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, CheckBox>("choice"));



        collocationsTableView.setItems(list);
    }



    //_________________________________________________________________________________

/*
    public void pressLoadFileButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll (
            new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        fileChooser.setTitle("Выбрать файл");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            inputFile = file;

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
            collocationsFromFile.get(i).setCollocationReally(((ObservableList<CollocationsTableRow>) collocationsTable.getItems()).get(i).isChoice().isSelected());
        }

        Main.getNeuralNetwork().performLearning(allPastCollocations);

        collocationsTable.setItems(null);
        tableRows = new ArrayList<>();
        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void pressCancelButton() {
        collocationsTable.setItems(null);
        tableRows = new ArrayList<>();

        loadFileButton.setDisable(false);
        findCollocationsButton.setDisable(true);
        cancelButton.setDisable(true);
        submitButton.setDisable(true);
    }
    public void initializeWithNeuralNetworkMethod() {
    }
    public void initializeWithKnowledgeDatabaseMethod() {

    }
    public void findCollocations() {
        Decoder decoder = new Decoder(Main.getCharacteristicsInfo());
        collocationsFromFile = decoder.decodeInputFileToArray(inputFile);

        allPastCollocations.addAll(collocationsFromFile);

        for (int i = 0; i < collocationsFromFile.size(); i++) {
            Main.getNeuralNetwork().performCalculation(collocationsFromFile.get(i));
        }

        refreshTable(collocationsFromFile);

    }
    public void refreshTable(List<Collocation> collocationsFromFile) {

        for (int i = 0; i < collocationsFromFile.size(); i++) {
            Collocation collocation = collocationsFromFile.get(i);
            tableRows.add(new CollocationsTableRow(collocation.getFirstWord(), collocation.getSecondWord(), collocation.isCollocationByNeuralNetworkCalculation() ? "Да" : "Нет", new CheckBox()));
        }

        ObservableList<CollocationsTableRow> list = FXCollections.observableArrayList(tableRows);

        wordColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, String>("homonym"));
        leftRightColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, Boolean>("result"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<CollocationsTableRow, CheckBox>("choice"));

        collocationsTable.setItems(list);
    }

    // lab4
    public static List<String> getRulesFromBD() {
        List<String> rules = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "select * from rules";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String rule = rs.getString("string-rule");
                rules.add(rule);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return rules;
    }
    public static void addRuleToBD(int ruleId, String rule) {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = " insert into users (rule_id, string-rule) " +
                "values (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, ruleId);
            preparedStatement.setString(2, rule);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }
    public static void removeRuleFromBDByID(int ruleId) {
        List<String> rules = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "delete from rules where id = ?";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, ruleId);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }
    public static String getCurrentNeuralNetworkFromBD() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "select * from neural_network";
        String neuralNetwork = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                neuralNetwork = rs.getString("neural_network");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return neuralNetwork;
    }
    public static void refreshNeuralNetworkInBDByID(int neuralNetworkId, String neuralNetwork) {
        List<String> rules = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "update neural_networks set neural_network = ? where neural_network_id = ?";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, neuralNetwork);
            preparedStatement.setInt(2, neuralNetworkId);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }
    public static void addNewCollocationToBD(int collocationId, String collocation) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = " insert into collocationsFromFile (collocations_id, collocation) " +
                "values (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, collocationId);
            preparedStatement.setString(2, collocation);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }
    public static void deleteCollocationFromBDByID(int collocationId) {
        List<String> rules = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "delete from collocationsFromFile where id = ?";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, collocationId);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }

    //lab4
*/
}
