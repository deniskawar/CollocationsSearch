package words;

import java.util.ArrayList;
import java.util.List;

public class Collocation {
    private String firstWord;
    private List<Characteristic> firstWordCharacteristics = new ArrayList<>();
    private String secondWord;
    private List<Characteristic> secondWordCharacteristics = new ArrayList<>();
    private boolean collocation = false;

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
}
