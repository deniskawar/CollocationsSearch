package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neuralNetwork.NeuralNetwork;

public class Main extends Application {
    private static final NeuralNetwork neuralNetwork = new NeuralNetwork(2, 1);
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

    public static NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
