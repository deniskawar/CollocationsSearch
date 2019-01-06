package neuralNetwork;

import main.AuthorizationController;
import main.Main;
import words.Characteristic;
import words.Collocation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NeuralNetwork {
    private List<Layer> layers;
    private boolean firstIteration = true;
    private int[] input;
    private double[] output;


    public NeuralNetwork(int numberOfLayers, int numberOfOutputs) {
        layers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            layers.add(new Layer());
        }
        output = new double[numberOfOutputs];
    }

    public void performCalculation(Collocation collocation) {
        decodeCollocationToInput(collocation);
        if (firstIteration) {
            createRandomEdges();
            firstIteration = false;
        }
        int currentLayerNumber = 0;
        while (currentLayerNumber != layers.size()) {
            Layer currentLayer = layers.get(currentLayerNumber);
            if (currentLayerNumber == 0) {
                for (int i = 0; i < currentLayer.getNeurons().size(); i++) {
                    double sum = 0;
                    for (int j = 0; j < currentLayer.getNeurons().get(i).getInputSynapses().size(); j++) {
                        sum = sum + input[j] * currentLayer.getNeurons().get(i).getInputSynapses().get(j).getWeight();
                    }
                    currentLayer.getNeurons().get(i).setOutputValue(currentLayer.activationFunction(sum));
                }
            }
            else {
                for (int i = 0; i < currentLayer.getNeurons().size(); i++) {
                    double sum = 0;
                    for (int j = 0; j < currentLayer.getNeurons().get(i).getInputSynapses().size(); j++) {
                        sum = sum + layers.get(currentLayerNumber-1).getNeurons().get(j).getOutputValue()
                                * currentLayer.getNeurons().get(i).getInputSynapses().get(j).getWeight();
                    }
                    currentLayer.getNeurons().get(i).setOutputValue(currentLayer.activationFunction(sum));
                }
            }
            currentLayerNumber++;
        }
        for (int i = 0; i < output.length; i++) {
            output[i] = layers.get(layers.size()-1).getNeurons().get(i).getOutputValue();
        }
        System.out.println();
        // I already can start to work on calculation because I have all input data in proper form

    }
    public void performLearning() {

    }
    private void decodeCollocationToInput(Collocation collocation) {
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

    }

    private void createRandomEdges() {
        initializeNeurons();
        for (int i = 0; i < layers.size()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                for (int k = 0; k < input.length; k++) {
                    layers.get(i).getNeurons().get(j).getInputSynapses().add(new Synapse(new Random().nextDouble() * 2 - 1));
                }
            }
        }
        for (int j = 0; j < output.length; j++) {
            for (int k = 0; k < input.length; k++) {
                layers.get(layers.size() - 1).getNeurons().get(j).getInputSynapses().add(new Synapse(new Random().nextDouble() * 2 - 1));
            }
        }

    }
    private void initializeNeurons() {
        for (int i = 0; i < layers.size()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                layers.get(i).getNeurons().add(new Neuron());
            }
        }

        for (int j = 0; j < output.length; j++) {
            layers.get(layers.size()-1).getNeurons().add(new Neuron());
        }
    }
}
