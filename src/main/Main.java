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
    private static final NeuralNetwork neuralNetwork = new NeuralNetwork(3);
    private static final Map<String,Integer> characteristicsInfo = new LinkedHashMap<String, Integer>(){
        {
            put("Part of speech", 11); // часть речи
            put("Kind of word", 4); // род
            put("Case", 7); // падеж
            put("Number", 3); // число
            put("Transitivity", 3); // переходность
            put("Voice", 3); // залог
        }
    };
    private static final int iterationsCount = 5;
    private static final int personsCount = 10; // только четное число
    private static final int chromosomeLength = 10; // только четное число
    private static double crossingOverProbability = 1;
    private static double mutationProbability = 1;
    private static double intervalA = -1;
    private static double intervalB = 1;



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

    public static int getIterationsCount() {
        return iterationsCount;
    }

    public static int getPersonsCount() {
        return personsCount;
    }

    public static int getChromosomeLength() {
        return chromosomeLength;
    }

    public static double getCrossingOverProbability() {
        return crossingOverProbability;
    }

    public static double getMutationProbability() {
        return mutationProbability;
    }

    public static double getIntervalA() {
        return intervalA;
    }

    public static double getIntervalB() {
        return intervalB;
    }
}
