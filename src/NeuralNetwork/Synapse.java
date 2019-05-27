package neuralNetwork;

public class Synapse {
    private double weight;
    private String binaryWeight;

    public Synapse(double weight) {
        this.weight = weight;
    }
    public Synapse(String binaryWeight) {
        this.binaryWeight = binaryWeight;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getBinaryWeight() {
        return binaryWeight;
    }
    public void setBinaryWeight(String binaryWeight) {
        this.binaryWeight = binaryWeight;
    }
}
