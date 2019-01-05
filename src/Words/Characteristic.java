package words;

public class Characteristic {
    private String name;
    private int maxValue;
    private int value;

    public Characteristic(String name, int maxValue) {
        this.name = name;
        this.maxValue = maxValue;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getValue() {
        return value;

    }
}
