package words;

import java.util.List;

public class Word {
    private String name;
    private List<Characteristic> characteristics;

    public Word(String name, List<Characteristic> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }
}
