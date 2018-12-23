package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private int authorizationWindowWidth = 285;
    private int authorizationWindowHeight = 175;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../forms/authorization.fxml"));
        primaryStage.setTitle("Поиск коллокаций");
        primaryStage.setScene(new Scene(root, authorizationWindowWidth, authorizationWindowHeight));
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
