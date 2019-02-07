package readerSourceData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReaderSourceData {

    private static final String REGEX = ",";

    public String[][] readingSourceDataInTwoDimenArray(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            int countLines = countNumberLinesInFile(fileName);
            String[][] strings = new String[countLines][];
            String line;

            int index = 0;
            while ((line = reader.readLine()) != null) {
                strings[index] = line.split(REGEX);
                index++;
            }

            return strings;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new String[][]{};
    }

    public String[] readingSourceDataInOneDimenArray(String fileName) {
        String[] strings = {};
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            if ((line = reader.readLine()) != null) {
                strings = line.split(REGEX);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return strings;
    }

    private int countNumberLinesInFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            return (int) (long) reader.lines().count();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
