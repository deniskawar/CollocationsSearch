package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neuralNetwork.NeuralNetwork;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application {
    private static final NeuralNetwork neuralNetwork = new NeuralNetwork(3, 1);
    private static final Map<String,Integer> characteristicsInfo = new LinkedHashMap<String, Integer>(){
        {
            put("Part of speech", 11); // часть речи   0 + 12 + 5 + 8 + 4 + 4 + 4 =
            put("Kind of word", 4); // род
            put("Case", 7); // падеж
            put("Number", 3); // число
            put("Transitivity", 3); // переходность
            put("Voice", 3); // залог
        }
    };
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

    public static Map<String, Integer> getCharacteristicsInfo() {
        return characteristicsInfo;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
