package words;

import java.util.List;

public class Word {
    private String name;
    private List<List<Characteristic>> characteristics;
    private boolean homonym = false;

    public Word(String name, List<List<Characteristic>> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }
    public void defineHomonym() {
        this.homonym = characteristics.size() > 1;
    }
    public boolean isHomonym() {
        return homonym;
    }

    public String getName() {
        return name;
    }

    public List<List<Characteristic>> getCharacteristics() {
        return characteristics;
    }
}
