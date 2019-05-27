package neuralNetwork;

import main.Main;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> neurons = new ArrayList<>();

    public double activationFunction(double sum) {
        return 1 / (1 + Math.exp(-sum));
    }
    public List<Neuron> getNeurons() {
        return neurons;
    }
    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }
}
