package words;

import main.Main;

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
    public static List<Word> decodeInputFileToArray(File inputFile) {
        /*
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
        */
        List<Map.Entry<String,Integer>> characteristicsInfo = new ArrayList<>(Main.getCharacteristicsInfo().entrySet());
        List<Word> wordsList = new ArrayList<>();
        StringBuilder fullTextStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "Cp1251"))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fullTextStringBuilder.append(line);
            }
            String[] sentences = new String[1];
            if (fullTextStringBuilder.toString().contains("!")) {
                sentences = fullTextStringBuilder.toString().split("!");
            } else {
                sentences[0] = fullTextStringBuilder.toString();
            }
            for (int i = 0; i < sentences.length; i++) {
                String[] wordsWithCharacteristics = sentences[i].split("#");
                String[] words = new String[wordsWithCharacteristics.length];
                String[] characteristics = new String[wordsWithCharacteristics.length];
                for (int j = 1; j < wordsWithCharacteristics.length; j++) {
                    characteristics[j] = wordsWithCharacteristics[j].substring(wordsWithCharacteristics[j].indexOf("<")+1, wordsWithCharacteristics[j].indexOf(">"));
                    words[j] = wordsWithCharacteristics[j].split("<")[0];
                }
                for (int j = 1; j < characteristics.length; j++) {
                    List<List<Characteristic>> characteristicListList = new ArrayList<>();
                    if (characteristics[j].contains("?"))
                    {
                        String[] homonymNumbers = characteristics[j].split("\\?");
                        for (int k = 0; k < homonymNumbers.length; k++) {
                            String[] numbers = homonymNumbers[k].split("_");
                            List<Characteristic> characteristicList = new ArrayList<>();
                            for (int m = 0; m < numbers.length; m++) {
                                int value = Integer.parseInt(numbers[m]);
                                Characteristic characteristic = new Characteristic(characteristicsInfo.get(m).getKey(), characteristicsInfo.get(m).getValue());
                                characteristic.setValue(value);
                                characteristicList.add(characteristic);
                            }
                            characteristicListList.add(characteristicList);
                        }
                        Word word = new Word(words[j], characteristicListList);
                        wordsList.add(word);
                    }
                    else {
                        String[] numbers = characteristics[j].split("_");
                        List<Characteristic> characteristicList = new ArrayList<>();
                        for (int k = 0; k < numbers.length; k++) {
                            int value = Integer.parseInt(numbers[k]);
                            Characteristic characteristic = new Characteristic(characteristicsInfo.get(k).getKey(), characteristicsInfo.get(k).getValue());
                            characteristic.setValue(value);
                            characteristicList.add(characteristic);
                        }
                        characteristicListList.add(characteristicList);
                        Word word = new Word(words[j], characteristicListList);
                        wordsList.add(word);
                    }
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wordsList;
    }

    public static String fileValidation(File file) {
        StringBuilder fullTextStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fullTextStringBuilder.append(line);
            }

            if (fullTextStringBuilder.toString().isEmpty()) return "Файл пуст";
            if (!fullTextStringBuilder.toString().contains("?")) return "В тексте отсутствуют омонимы";
            String[] sentences = new String[1];
            if (fullTextStringBuilder.toString().contains("!")) {
                sentences = fullTextStringBuilder.toString().split("!");
                for (int i = 0; i < sentences.length; i++) {
                    if (!sentences[i].contains("#")) return "Ошибка в разметке слов";
                }
            } else {
                sentences[0] = fullTextStringBuilder.toString();
                if (!sentences[0].contains("#")) return "Ошибка в разметке слов";
            }
            for (int i = 0; i < sentences.length; i++) {
                String[] wordsWithCharacteristics = sentences[i].split("#");
                String[] characteristics = new String[wordsWithCharacteristics.length];
                for (int j = 1; j < wordsWithCharacteristics.length; j++) {
                    if (!wordsWithCharacteristics[j].contains("<") && !wordsWithCharacteristics[j].contains(">")) return "Ошибка в разметке снаружи характеристик";
                    characteristics[j] = wordsWithCharacteristics[j].substring(wordsWithCharacteristics[j].indexOf("<")+1, wordsWithCharacteristics[j].indexOf(">"));

                }
                for (int j = 1; j < characteristics.length; j++) {
                    if (characteristics[j].contains("?"))
                    {
                        String[] homonymNumbers = characteristics[j].split("\\?");
                        for (int k = 0; k < homonymNumbers.length; k++) {
                            if (!homonymNumbers[k].contains("_")) return "Ошибка в разметке внутри характеристик омонима";
                            String[] numbers = homonymNumbers[k].split("_");
                            if (numbers.length != Main.getCharacteristicsInfo().size()) return "Ошибка в количестве характеристик";
                            for (int m = 0; m < numbers.length; m++) {
                                try {
                                    Integer.parseInt(numbers[m]);
                                } catch (NumberFormatException ex) {
                                    return "Ошибка в числах характеристик";
                                }
                            }
                        }
                    }
                    else {
                        if (!characteristics[j].contains("_")) return "Ошибка в разметке внутри характеристик";
                        String[] numbers = characteristics[j].split("_");
                        if (numbers.length != Main.getCharacteristicsInfo().size()) return "Ошибка в количестве характеристик";
                        for (int k = 0; k < numbers.length; k++) {
                            try {
                                Integer.parseInt(numbers[k]);
                            } catch (NumberFormatException ex) {
                                return "Ошибка в числах характеристик";
                            }
                        }
                    }
                }
            }


        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
