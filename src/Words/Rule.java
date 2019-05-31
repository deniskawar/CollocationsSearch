package words;

import java.util.List;

public class Rule {
    private int ruleId;
    private List<Characteristic> firstWordCharacteristics;
    private List<Characteristic> secondWordCharacteristics;
    private List<Characteristic> alternativeCharacteristics;
    private boolean isHomonymLeft;

    public Rule(int ruleId, List<Characteristic> firstWordCharacteristics, List<Characteristic> secondWordCharacteristics, List<Characteristic> alternativeCharacteristics, boolean isHomonymLeft) {
        this.ruleId = ruleId;
        this.firstWordCharacteristics = firstWordCharacteristics;
        this.secondWordCharacteristics = secondWordCharacteristics;
        this.alternativeCharacteristics = alternativeCharacteristics;
        this.isHomonymLeft = isHomonymLeft;
    }

    public List<Characteristic> getFirstWordCharacteristics() {
        return firstWordCharacteristics;
    }

    public void setFirstWordCharacteristics(List<Characteristic> firstWordCharacteristics) {
        this.firstWordCharacteristics = firstWordCharacteristics;
    }

    public List<Characteristic> getSecondWordCharacteristics() {
        return secondWordCharacteristics;
    }

    public void setSecondWordCharacteristics(List<Characteristic> secondWordCharacteristics) {
        this.secondWordCharacteristics = secondWordCharacteristics;
    }

    public List<Characteristic> getAlternativeCharacteristics() {
        return alternativeCharacteristics;
    }

    public void setAlternativeCharacteristics(List<Characteristic> alternativeCharacteristics) {
        this.alternativeCharacteristics = alternativeCharacteristics;
    }

    public boolean isHomonymLeft() {
        return isHomonymLeft;
    }

    public void setHomonymLeft(boolean homonymLeft) {
        isHomonymLeft = homonymLeft;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}
