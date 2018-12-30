package neuralNetwork;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<Layer> layers;
    private double[] input;
    private double[] output;

    public NeuralNetwork(int numberOfLayers, int numberOfInputs, int numberOfOutputs) {
        layers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            layers.set(i, new Layer());
        }
        input = new double[numberOfInputs];
        output = new double[numberOfOutputs];
    }

    public void performCalculation(double[] input) {
        this.input = input;

    }
    public void performLearning() {

    }
}
