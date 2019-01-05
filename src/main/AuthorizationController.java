package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import neuralNetwork.NeuralNetwork;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class AuthorizationController {


    private int expertWindowWidth = 458;
    private int expertWindowHeight = 224;
    private int userWindowWidth = 633;
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

    public void initialize() {
        modeToggleGroup = new ToggleGroup();
        userModeRadioButton.setToggleGroup(modeToggleGroup);
        expertModeRadioButton.setToggleGroup(modeToggleGroup);
        methodToggleGroup = new ToggleGroup();
        knowledgeBaseRadioButton.setToggleGroup(methodToggleGroup);
        neuralNetworkRadioButton.setToggleGroup(methodToggleGroup);
        modeToggleGroup.selectToggle(modeToggleGroup.getToggles().get(0));
        methodToggleGroup.selectToggle(methodToggleGroup.getToggles().get(0));

    }
    public void pressSignInButton() throws IOException {
        neuralNetworkMode = methodToggleGroup.getSelectedToggle().equals(neuralNetworkRadioButton);
        if (modeToggleGroup.getSelectedToggle().equals(userModeRadioButton)) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../forms/user.fxml"));
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
