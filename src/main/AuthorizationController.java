package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AuthorizationController {


    private int expertWindowWidth = 458;
    private int expertWindowHeight = 224;
    private int userWindowWidth = 682;
    private int userWindowHeight = 321;
    private static boolean neuralNetworkMode;
    @FXML
    private AnchorPane authorizationWindow;
    @FXML
    private Button signInButton;
    @FXML
    private RadioButton userModeRadioButton;
    @FXML
    private RadioButton expertModeRadioButton;
    @FXML
    private RadioButton neuralNetworkRadioButton;
    @FXML
    private RadioButton knowledgeBaseRadioButton;
    private ToggleGroup modeToggleGroup;
    private ToggleGroup methodToggleGroup;
    @FXML
    private Button testBDButton;

    public void pressTestBDButton() {
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
        String query = "select * from collocations";
        String neuralNetwork = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String secondname = rs.getString("secondname");
                System.out.println(name + " " + secondname);
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
    }

    public void initialize() {
        modeToggleGroup = new ToggleGroup();
        userModeRadioButton.setToggleGroup(modeToggleGroup);
        expertModeRadioButton.setToggleGroup(modeToggleGroup);
        methodToggleGroup = new ToggleGroup();
        knowledgeBaseRadioButton.setToggleGroup(methodToggleGroup);
        neuralNetworkRadioButton.setToggleGroup(methodToggleGroup);
        modeToggleGroup.selectToggle(modeToggleGroup.getToggles().get(0));
        methodToggleGroup.selectToggle(methodToggleGroup.getToggles().get(0));

        //test sh*t

        //

    }
    public void pressSignInButton() throws IOException {
        neuralNetworkMode = methodToggleGroup.getSelectedToggle().equals(neuralNetworkRadioButton);
        if (modeToggleGroup.getSelectedToggle().equals(userModeRadioButton)) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../forms/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), userWindowWidth, userWindowHeight);
            Stage stage = new Stage();
            stage.setTitle("Режим пользователя");
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../forms/expert.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), expertWindowWidth, expertWindowHeight);
            Stage stage = new Stage();
            stage.setTitle("Режим эксперта");
            stage.setScene(scene);
            stage.show();
        }
    }
    public void pressUserModeRadioButton() {
        methodToggleGroup.selectToggle(methodToggleGroup.getToggles().get(0));
        knowledgeBaseRadioButton.setDisable(false);
        neuralNetworkRadioButton.setDisable(false);
    }
    public void pressExpertModeRadioButton() {
        methodToggleGroup.selectToggle(methodToggleGroup.getToggles().get(0));
        knowledgeBaseRadioButton.setDisable(true);
        neuralNetworkRadioButton.setDisable(true);
    }
    public static boolean isNeuralNetworkMode() {
        return neuralNetworkMode;
    }
}
