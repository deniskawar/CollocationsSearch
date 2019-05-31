package main;

public class ShowRulesTableRow {
    private int ruleId;
    private String characteristicName;
    private String firstWordCharacteristic;
    private String secondWordCharacteristic;
    private String alternativeCharacteristic;
    private int homonymPosition;

    public ShowRulesTableRow(int ruleId, String characteristicName, String firstWordCharacteristic, String secondWordCharacteristic, String alternativeCharacteristic, int homonymPosition) {
        this.ruleId = ruleId;
        this.characteristicName = characteristicName;
        this.firstWordCharacteristic = firstWordCharacteristic;
        this.secondWordCharacteristic = secondWordCharacteristic;
        this.alternativeCharacteristic = alternativeCharacteristic;
        this.homonymPosition = homonymPosition;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public String getFirstWordCharacteristic() {
        return firstWordCharacteristic;
    }

    public void setFirstWordCharacteristic(String firstWordCharacteristic) {
        this.firstWordCharacteristic = firstWordCharacteristic;
    }

    public String getSecondWordCharacteristic() {
        return secondWordCharacteristic;
    }

    public void setSecondWordCharacteristic(String secondWordCharacteristic) {
        this.secondWordCharacteristic = secondWordCharacteristic;
    }

    public String getAlternativeCharacteristic() {
        return alternativeCharacteristic;
    }

    public void setAlternativeCharacteristic(String alternativeCharacteristic) {
        this.alternativeCharacteristic = alternativeCharacteristic;
    }

    public int getHomonymPosition() {
        return homonymPosition;
    }

    public void setHomonymPosition(int homonymPosition) {
        this.homonymPosition = homonymPosition;
    }
}
