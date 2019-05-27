package words;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Decoder {
    private List<Collocation> collocations = new ArrayList<>();
    private List<Characteristic> characteristicsInfo = new ArrayList<>();

    public Decoder(Map<String, Integer> characteristicsInfo) {
        for (Map.Entry<String, Integer> entry : characteristicsInfo.entrySet()) {
            this.characteristicsInfo.add(new Characteristic(entry.getKey(), entry.getValue()));
        }
    }
    public List<Collocation> decodeInputFileToArray(File inputFile) {
        /*try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"Cp1251"))) {
            bufferedReader.readLine();
            String s = bufferedReader.readLine();
            while (s != null) {

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
        */
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "Cp1251"))) {
            bufferedReader.readLine();
            String s = "";
            int symbol;
            while ((symbol = bufferedReader.read()) != -1) {
                s = s + (char) symbol;
                while ((char)(symbol = bufferedReader.read()) != '!') {
                    if (symbol == -1) throw new Exception("Неверный формат текста");
                    s = s + (char) symbol;
                }
                String[] wordsWithCharacteristics;
                wordsWithCharacteristics = s.split("#");
                List<Word> words = new ArrayList<>();
                for (int i = 1; i < wordsWithCharacteristics.length; i++) {
                    String word = wordsWithCharacteristics[i].split("<")[0];
                    String characteristicsString = wordsWithCharacteristics[i].split("<")[1].split(">")[0];
                    if (characteristicsString.contains("?")) {
                        // дописать для омомнимов
                    }
                    else {
                        String[] characteristicsIntegerValues = characteristicsString.split("_");
                        List<List<Characteristic>> characteristics = new ArrayList<>();
                        characteristics.add(new ArrayList<>());
                        for (int j = 0; j < characteristicsIntegerValues.length; j++) {
                            String name = characteristicsInfo.get(j).getName();
                            int maxValue = characteristicsInfo.get(j).getMaxValue();
                            int value = Integer.parseInt(characteristicsIntegerValues[j]);
                            Characteristic characteristic = new Characteristic(name, maxValue);
                            characteristic.setValue(value);
                            characteristics.get(0).add(characteristic);
                        }
                        words.add(new Word(word, characteristics));
                    }
                }
                System.out.println();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return collocations;
    }
}
