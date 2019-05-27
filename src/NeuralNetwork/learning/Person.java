package neuralNetwork.learning;

import neuralNetwork.NeuralNetwork;

public class Person {
    private int position;
    private NeuralNetwork neuralNetwork;

    public Person(NeuralNetwork neuralNetwork, int position) {
        this.position = position;
        this.neuralNetwork = neuralNetwork;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
