package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neuralNetwork.NeuralNetwork;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application {
    private static NeuralNetwork neuralNetwork = new NeuralNetwork(3);
    private static final Map<String,Integer> characteristicsInfo = new LinkedHashMap<String, Integer>(){
        {
            put("Часть речи", 8);
            put("Падеж", 7);
            put("Число", 3);
            put("Род", 4);
            put("Время", 4);
            put("Залог", 3);
            put("Тип местоимения", 10);
            put("Наклонение", 4);

        }
    };
    private static final int iterationsCount = 15;
    private static final int personsCount = 10; // только четное число
    private static final int chromosomeLength = 10; // только четное число
    private static double crossingOverProbability = 1;
    private static double mutationProbability = 1;
    private static double intervalA = -1;
    private static double intervalB = 1;
    public static Stage primaryStage;
    public static int currentProjectId = -1;
    public static String currentProjectName;
    public static int mainWindowWidth = 783;
    public static int mainWindowHeight = 470;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../forms/main.fxml"));
        primaryStage.setTitle("Экспертная система поиска коллокаций в документации на ЭС");
        primaryStage.setScene(new Scene(root, mainWindowWidth, mainWindowHeight));
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

    public static void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        Main.neuralNetwork = neuralNetwork;
    }
}
