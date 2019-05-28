package words;

import java.util.ArrayList;
import java.util.List;

public class Collocation {
    private Word firstWord;
    private Word secondWord;
    private boolean collocationByNeuralNetworkCalculation = false;
    private boolean collocationReally = false;

    public Collocation(Word firstWord, Word secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public void setFirstWord(Word firstWord) {
        this.firstWord = firstWord;
    }

    public void setSecondWord(Word secondWord) {
        this.secondWord = secondWord;
    }

    public boolean isCollocationByNeuralNetworkCalculation() {
        return collocationByNeuralNetworkCalculation;
    }
    public void setCollocationByNeuralNetworkCalculation(boolean collocationByNeuralNetworkCalculation) {
        this.collocationByNeuralNetworkCalculation = collocationByNeuralNetworkCalculation;
    }
    public Word getFirstWord() {
        return firstWord;
    }
    public Word getSecondWord() {
        return secondWord;
    }
    public boolean isCollocationReally() {
        return collocationReally;
    }
    public void setCollocationReally(boolean collocationReally) {
        this.collocationReally = collocationReally;
    }

}
