package words;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Decoder {
    Map<String, String> collocations = new HashMap<>();
    List<Characteristic> characteristicsInfo = new ArrayList<>();


    public Decoder(Map<String, Integer> characteristicsInfo) {
        for (Map.Entry<String, Integer> entry : characteristicsInfo.entrySet()) {
            this.characteristicsInfo.add(new Characteristic(entry.getKey(), entry.getValue()));
        }
    }

    public double[] decodeInputFileToArray(File inputFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile),"Cp1251"))) {
            bufferedReader.readLine();
            String s = bufferedReader.readLine();
            while (!(s == null)) {
                //System.out.println(s);
                s = s.split("\\{")[1].split("}")[0];
                String[] lol = s.split("#");
                String first = lol[1];
                String second = lol[2];


                s = bufferedReader.readLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new double[1];
    }
}
