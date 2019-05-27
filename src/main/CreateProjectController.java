package main;

import database.BD;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateProjectController {
    @FXML
    private TextField projectNameTextField;
    @FXML
    private Button createProjectButton;
    private MainController mainController;

    public void getMainConroller(MainController mainController) {
        this.mainController = mainController;
    }

    public void pressCreateProjectButton() {
        String projectName = projectNameTextField.getText();
        if (!projectName.isEmpty() && !BD.projectRepeatCheckDB(projectName)) {
            int projectId = BD.addProjectToDB(projectName);
            Main.currentProjectId = projectId;
            Main.currentProjectName = projectName;
            Stage stage = (Stage) projectNameTextField.getScene().getWindow();
            mainController.getCurrentProjectText().setText("Текущий проект: №" + Main.currentProjectId + " '" + Main.currentProjectName + "'");
            mainController.makeEverythingAvailable();
            stage.close();
        } else {

        }
    }
}
