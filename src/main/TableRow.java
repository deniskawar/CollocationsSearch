package main;

import javafx.scene.control.CheckBox;

public class TableRow {
    private String word;
    private String homonym;
    private boolean result;
    private CheckBox choice;

    public TableRow(String word, String homonym, boolean result, CheckBox choice) {
        this.word = word;
        this.homonym = homonym;
        this.result = result;
        this.choice = choice;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public CheckBox isChoice() {
        return choice;
    }

    public void setChoice(CheckBox choice) {
        this.choice = choice;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHomonym() {
        return homonym;
    }

    public void setHomonym(String homonym) {
        this.homonym = homonym;
    }
}
