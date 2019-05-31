package main;

import javafx.scene.control.ChoiceBox;

public class AddRuleTableRow {
    private String characteristicName;
    private ChoiceBox firstWordChoice;
    private ChoiceBox secondWordChoice;
    private ChoiceBox alternativeChoice;

    public AddRuleTableRow(String characteristicName, ChoiceBox firstWordChoice,ChoiceBox secondWordChoice, ChoiceBox alternativeChoice) {
        this.characteristicName = characteristicName;
        this.firstWordChoice = firstWordChoice;
        this.secondWordChoice = secondWordChoice;
        this.alternativeChoice = alternativeChoice;
    }

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public ChoiceBox getFirstWordChoice() {
        return firstWordChoice;
    }

    public void setFirstWordChoice(ChoiceBox firstWordChoice) {
        this.firstWordChoice = firstWordChoice;
    }

    public ChoiceBox getSecondWordChoice() {
        return secondWordChoice;
    }

    public void setSecondWordChoice(ChoiceBox secondWordChoice) {
        this.secondWordChoice = secondWordChoice;
    }

    public ChoiceBox getAlternativeChoice() {
        return alternativeChoice;
    }

    public void setAlternativeChoice(ChoiceBox alternativeChoice) {
        this.alternativeChoice = alternativeChoice;
    }
}
