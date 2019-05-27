package words;

import java.util.ArrayList;
import java.util.List;

public class Collocation {
    private String firstWord;
    private List<Characteristic> firstWordCharacteristics = new ArrayList<>();
    private String secondWord;
    private List<Characteristic> secondWordCharacteristics = new ArrayList<>();
    private boolean collocationByNeuralNetworkCalculation = false;
    private boolean collocationReally = false;

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }
    public void setFirstWordCharacteristics(List<Characteristic> firstWordCharacteristics) {
        this.firstWordCharacteristics = firstWordCharacteristics;
    }
    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }
    public void setSecondWordCharacteristics(List<Characteristic> secondWordCharacteristics) {
        this.secondWordCharacteristics = secondWordCharacteristics;
    }
    public boolean isCollocationByNeuralNetworkCalculation() {
        return collocationByNeuralNetworkCalculation;
    }
    public void setCollocationByNeuralNetworkCalculation(boolean collocationByNeuralNetworkCalculation) {
        this.collocationByNeuralNetworkCalculation = collocationByNeuralNetworkCalculation;
    }
    public String getFirstWord() {
        return firstWord;
    }
    public List<Characteristic> getFirstWordCharacteristics() {
        return firstWordCharacteristics;
    }
    public String getSecondWord() {
        return secondWord;
    }
    public List<Characteristic> getSecondWordCharacteristics() {
        return secondWordCharacteristics;
    }
    public boolean isCollocationReally() {
        return collocationReally;
    }
    public void setCollocationReally(boolean collocationReally) {
        this.collocationReally = collocationReally;
    }

}
