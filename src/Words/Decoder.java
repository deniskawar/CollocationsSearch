package words;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoder {
    Map<String, String> collocations = new HashMap<>();
    List<Characteristic> characteristics = new ArrayList<>();

    public Decoder(Map<String, Integer> characteristicsInfo) {
        for (Map.Entry<String, Integer> entry : characteristicsInfo.entrySet()) {
            characteristics.add(new Characteristic(entry.getKey(), entry.getValue()));
        }
    }

    public double[] decodeInputFileToArray(File inputFile) {

        return new double[1];
    }
}
