package words;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Decoder {
    List<Collocation> collocations = new ArrayList<>();
    List<Characteristic> characteristicsInfo = new ArrayList<>();


    public Decoder(Map<String, Integer> characteristicsInfo) {
        for (Map.Entry<String, Integer> entry : characteristicsInfo.entrySet()) {
            this.characteristicsInfo.add(new Characteristic(entry.getKey(), entry.getValue()));
        }
    }

    public List<Collocation> decodeInputFileToArray(File inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"Cp1251"))) {
            bufferedReader.readLine();
            String s = bufferedReader.readLine();
            while (!(s == null)) {

                s = s.split("\\{")[1].split("}")[0];
                String[] lol = s.split("#");
                String first = lol[1];
                String second = lol[2];
                String[] ch1 = first.split("_");
                String[] ch2 = second.split("_");

                Collocation collocation = new Collocation();
                collocation.setFirstWord(ch1[0]);
                collocation.setSecondWord(ch2[0]);

                List<Characteristic> firstCharacteristics = new ArrayList<>();
                List<Characteristic> secondCharacteristics = new ArrayList<>();
                for (int i = 1; i < ch1.length; i++) {
                    String characteristicName = characteristicsInfo.get(i-1).getName();
                    int characteristicMaxValue = characteristicsInfo.get(i-1).getMaxValue();

                    firstCharacteristics.add(new Characteristic(characteristicName, characteristicMaxValue));
                    firstCharacteristics.get(i-1).setValue(Integer.parseInt(ch1[i]));

                    secondCharacteristics.add(new Characteristic(characteristicName, characteristicMaxValue));
                    secondCharacteristics.get(i-1).setValue(Integer.parseInt(ch2[i]));
                }
                collocation.setFirstWordCharacteristics(firstCharacteristics);
                collocation.setSecondWordCharacteristics(secondCharacteristics);
                collocations.add(collocation);

                s = bufferedReader.readLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return collocations;
    }
}
