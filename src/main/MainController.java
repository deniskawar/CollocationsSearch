package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import words.Collocation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainController {
    private File inputFile;
    private File originalTextFile;
    private List<TableRow> tableRows = new ArrayList<>();
    private List<Collocation> collocations;
    private List<Collocation> allPastCollocations = new ArrayList<>();

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
    private MenuItem removeRuleMenuItem;
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


    private static int openProjectWindowWidth = 414;
    private static int openProjectWindowHeight = 278;
    private static int createProjectWindowWidth = 261;
    private static int createProjectWindowHeight = 112;

    private File fileForAnalysis;


    public void initialize() {
        loadFileMenuItem.setDisable(true);
        findCollocationsMenuItem.setDisable(true);
        knowledgeBaseMenuItem.setDisable(true);
        neuralNetworkMenuItem.setDisable(true);
        addRuleMenuItem.setDisable(true);
        removeRuleMenuItem.setDisable(true);
        showRulesMenuItem.setDisable(true);
        modeMenuItem.setDisable(true);
    }
    public void pressCreateProjectMenuItem() throws IOException{
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
            // НАДО ПРОТЕСТИТЬ СООТВЕТСТВУЕТ ЛИ ОН ЗАДУМАННОМУ ФОРМАТУ
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

    }
    public void pressAddRuleMenuItem() {

    }
    public void pressRemoveRuleMenuItem() {

    }
    public void pressShowRulesMenuItem() {

    }
    public void pressHelpMenuItem() {

    }
    public void pressAboutProgramMenuItem() {

    }
    public void pressSubmitButton() {

    }
    public void pressCancelButton() {

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
            collocations.get(i).setCollocationReally(((ObservableList<TableRow>) collocationsTable.getItems()).get(i).isChoice().isSelected());
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
        collocations = decoder.decodeInputFileToArray(inputFile);

        allPastCollocations.addAll(collocations);

        for (int i = 0; i < collocations.size(); i++) {
            Main.getNeuralNetwork().performCalculation(collocations.get(i));
        }

        refreshTable(collocations);

    }
    public void refreshTable(List<Collocation> collocations) {

        for (int i = 0; i < collocations.size(); i++) {
            Collocation collocation = collocations.get(i);
            tableRows.add(new TableRow(collocation.getFirstWord(), collocation.getSecondWord(), collocation.isCollocationByNeuralNetworkCalculation() ? "Да" : "Нет", new CheckBox()));
        }

        ObservableList<TableRow> list = FXCollections.observableArrayList(tableRows);

        wordColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("word"));
        homonymColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("homonym"));
        leftRightColumn.setCellValueFactory(new PropertyValueFactory<TableRow, Boolean>("result"));
        choiceColumn.setCellValueFactory(new PropertyValueFactory<TableRow, CheckBox>("choice"));

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
        String query = " insert into collocations (collocations_id, collocation) " +
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
        String query = "delete from collocations where id = ?";
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
