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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class AuthorizationController{
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

    public void initialize() {
        ToggleGroup modeToggleGroup = new ToggleGroup();
        userModeRadioButton.setToggleGroup(modeToggleGroup);
        expertModeRadioButton.setToggleGroup(modeToggleGroup);
        ToggleGroup methodToggleGroup = new ToggleGroup();
        knowledgeBaseRadioButton.setToggleGroup(methodToggleGroup);
        neuralNetworkRadioButton.setToggleGroup(methodToggleGroup);
        modeToggleGroup.selectToggle(modeToggleGroup.getToggles().get(0));
        methodToggleGroup.selectToggle(methodToggleGroup.getToggles().get(0));
    }
    public void pressSignInButton() throws IOException{
        if (/*modeBox.getSelectionModel().getSelectedItem().toString().equals("Режим пользователя")*/true) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../forms/user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Режим пользователя");
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../forms/user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Режим эксперта");
            stage.setScene(scene);
            stage.show();
        }
    }
}
