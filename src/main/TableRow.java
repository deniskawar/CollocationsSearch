package main;

import javafx.scene.control.CheckBox;

public class TableRow {
    private String word;
    private String homonym;
    private boolean leftRight;
    private CheckBox choice;

    public TableRow(String word, String homonym, boolean leftRight, CheckBox choice) {
        this.word = word;
        this.homonym = homonym;
        this.leftRight = leftRight;
        this.choice = choice;
    }

    public boolean isLeftRight() {
        return leftRight;
    }

    public void setLeftRight(boolean leftRight) {
        this.leftRight = leftRight;
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
