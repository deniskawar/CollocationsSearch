package main;

import javafx.scene.control.CheckBox;

public class OpenProjectTableRow {
    private int projectId;
    private String projectName;

    public OpenProjectTableRow(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
