package neuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private List<Synapse> inputSynapses = new ArrayList<>();
    private double outputValue = 0;

    public List<Synapse> getInputSynapses() {
        return inputSynapses;
    }

    public void setInputSynapses(List<Synapse> inputSynapses) {
        this.inputSynapses = inputSynapses;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }
}
