package neuralNetwork;

import main.Main;
import neuralNetwork.learning.GeneticAlgorithm;
import words.Characteristic;
import words.Collocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NeuralNetwork {
    private List<Layer> layers;
    private boolean firstIteration = true;
    private int[] input;
    private double output;
    private int numberOfLayers;
    private double error;


    public NeuralNetwork(int numberOfLayers) {
        this.numberOfLayers = numberOfLayers;
        layers = new ArrayList<>();
        for (int i = 0; i < numberOfLayers; i++) {
            layers.add(new Layer());
        }
    }
    public void performCalculation(Collocation collocation) {
        decodeCollocationToInput(collocation);
        if (firstIteration) {
            initializeNeurons();
            createRandomEdges(new Random().nextDouble() * 2 - 1);
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

        output = layers.get(layers.size()-1).getNeurons().get(0).getOutputValue();
        collocation.setCollocationByNeuralNetworkCalculation(output > 0.5);
    }
    public void performLearning(List<Collocation> collocations) {


        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm
                (collocations, Main.getIterationsCount(), Main.getPersonsCount(),
                        Main.getChromosomeLength(),Main.getCrossingOverProbability(), Main.getMutationProbability(),
                        Main.getIntervalA(), Main.getIntervalB());
        geneticAlgorithm.mainProcedure();


/* FOR ANALYSIS
        double q, U, allQ = 0, allU = 0;


        for (int i = 10; i <= 100; i = i + 10) {
            for (int j = 0; j < 10; j++) {
                GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(collocations,
                        Main.getIterationsCount(), i, Main.getChromosomeLength(), 1, 1,
                        Main.getIntervalA(), Main.getIntervalB());
                geneticAlgorithm.mainProcedure();
                q = 1 / ((1) / (Main.getNeuralNetwork().getError() + 1));
                U = (geneticAlgorithm.getTotalIterations() - Main.getIterationsCount());
                System.out.println("Iteration: " + i / 10 + "; N = " + i +
                        "; q = " + q +
                        "; U = " + U);
                allQ = allQ + q;
                allU = allU + U;
            }
            System.out.println("q = " + allQ / 10 + "; U = " + allU / 10);
            allQ = 0; allU = 0;
            System.out.println();
        }

        for (int i = 1; i <= 10; i = i + 1) {
            for (int j = 0; j < 10; j++) {
                GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(collocations,
                        Main.getIterationsCount(), 100, Main.getChromosomeLength(), (float)i / 10,
                        1, Main.getIntervalA(), Main.getIntervalB());
                geneticAlgorithm.mainProcedure();
                q = 1 / ((1) / (Main.getNeuralNetwork().getError() + 1));
                U = (geneticAlgorithm.getTotalIterations() - Main.getIterationsCount());
                System.out.println("Iteration: " + i + "; pcross = " + (float)i/10 +
                        "; q = " + q +
                        "; U = " + U);
                allQ = allQ + q;
                allU = allU + U;
            }
            System.out.println("q = " + allQ / 10 + "; U = " + allU / 10);
            allQ = 0; allU = 0;
            System.out.println();
        }

        for (int i = 5; i <= 10; i = i + 1) {
            for (int j = 0; j < 10; j++) {
                GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(collocations,
                        Main.getIterationsCount(), 100, Main.getChromosomeLength(), 1,
                        (float) i / 10,
                        Main.getIntervalA(), Main.getIntervalB());
                geneticAlgorithm.mainProcedure();
                q = 1 / ((1) / (Main.getNeuralNetwork().getError() + 1));
                U = (geneticAlgorithm.getTotalIterations() - Main.getIterationsCount());
                System.out.println("Iteration: " + i + "; pmut = " + (float)i/10 +
                        "; q = " + q +
                        "; U = " + U);
                allQ = allQ + q;
                allU = allU + U;
            }
            System.out.println("q = " + allQ / 10 + "; U = " + allU / 10);
            allQ = 0; allU = 0;
            System.out.println();
        }

 FOR ANALYSIS */

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
    private void createRandomEdges(double value) {
        for (int i = 0; i < layers.size()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                for (int k = 0; k < input.length; k++) {
                    layers.get(i).getNeurons().get(j).getInputSynapses().add(new Synapse(value));
                }
            }
        }
        for (int k = 0; k < input.length; k++) {
            layers.get(layers.size() - 1).getNeurons().get(0).getInputSynapses().add(new Synapse(value));
        }
    }
    public void createRandomEdges(int chromosomeLength) {
        for (int i = 0; i < layers.size()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                for (int k = 0; k < input.length; k++) {
                    layers.get(i).getNeurons().get(j).getInputSynapses().add(new Synapse(GeneticAlgorithm.randomBinaryLength(chromosomeLength)));
                }
            }
        }
        for (int k = 0; k < input.length; k++) {
            layers.get(layers.size() - 1).getNeurons().get(0).getInputSynapses().add(new Synapse(GeneticAlgorithm.randomBinaryLength(chromosomeLength)));
        }
    }
    public void initializeNeurons() {
        for (int i = 0; i < layers.size()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                layers.get(i).getNeurons().add(new Neuron());
            }
        }
        layers.get(layers.size()-1).getNeurons().add(new Neuron());

    }
    public List<Layer> getLayers() {
        return layers;
    }
    public double getOutput() {
        return output;
    }
    public int[] getInput() {
        return input;
    }
    public void setInput(int[] input) {
        this.input = input;
    }
    public double getError() {
        return error;
    }
    public void setError(double error) {
        this.error = error;
    }
    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }
    public boolean isFirstIteration() {
        return firstIteration;
    }
    public void setFirstIteration(boolean firstIteration) {
        this.firstIteration = firstIteration;
    }

}
