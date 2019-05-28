package words;

import java.util.List;

public class Word {
    private String name;
    private List<List<Characteristic>> characteristics;

    public Word(String name, List<List<Characteristic>> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }
    public boolean isHomonym() {
        return characteristics.size() > 1;
    }
}
