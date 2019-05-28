package main;

import database.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        if (!projectName.isEmpty() && !DB.projectRepeatCheckDB(projectName)) {
            int projectId = DB.addProjectToDB(projectName);
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
