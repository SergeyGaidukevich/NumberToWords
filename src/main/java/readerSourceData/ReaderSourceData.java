package readerSourceData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ReaderSourceData {

    private static final String REGEX = ",";

    public String[][] readingSourceDataInTwoDimenArray(String fileName) {
        String[][] strings = new String[23][4];
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            String[] units;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                if (!Objects.equals(line, "")) {
                    units = line.split(REGEX);
                    strings[index] = units;
                    index++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return strings;
    }

    public String[] readingSourceDataInOneDimenArray(String fileName) {
        String[] strings = {};
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!Objects.equals(line, "")) {
                    strings = line.split(REGEX);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return strings;
    }
}
