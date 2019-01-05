package neuralNetwork;

import main.AuthorizationController;
import main.Main;
import words.Characteristic;
import words.Collocation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeuralNetwork {
    private List<Layer> layers;
    private boolean firstIteration = true;
    private int[] input;
    private double[] output;

    public NeuralNetwork(int numberOfLayers, int numberOfInputs, int numberOfOutputs) {
        layers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            layers.set(i, new Layer());
        }
        input = new int[numberOfInputs];
        output = new double[numberOfOutputs];
    }
    public NeuralNetwork(int numberOfLayers, int numberOfOutputs) {
        layers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            layers.add(new Layer());
        }
        output = new double[numberOfOutputs];
    }

    public void performCalculation(Collocation collocation) {
        decodeCollocationToInput(collocation);
        if (firstIteration) createRandomEdges();

        // I already can start to work on calculation because I have all input data in proper form

    }
    public void performLearning() {

    }
    public void decodeCollocationToInput(Collocation collocation) {
        int inputAmount = 0;

        for (Map.Entry<String, Integer> entry : Main.getCharacteristicsInfo().entrySet()) {
            inputAmount += entry.getValue() + 1;
        }
        inputAmount *= 2;
        input = new int[inputAmount];
        for (int i = 0; i < input.length; i++) {
            input[i] = 0;
        }
        int index = 0;
        for (Characteristic characteristic : collocation.getFirstWordCharacteristics()) {
            input[index + characteristic.getValue()] = 1; //
            index += characteristic.getMaxValue() + 1;
        }
        for (Characteristic characteristic : collocation.getSecondWordCharacteristics()) {
            input[index + characteristic.getValue()] = 1; //
            index += characteristic.getMaxValue() + 1;
        }
        System.out.println();

    }

    public void createRandomEdges() {

    }
}
