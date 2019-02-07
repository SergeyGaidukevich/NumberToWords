package readerData;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readerSourceData.ReaderSourceData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;


public class ReaderSourceDataTest {
    private static final String PATH_TENS_TXT = "data/tens.txt";
    private static final String PATH_UNITS_TXT = "data/units.txt";
    private final ReaderSourceData reader = new ReaderSourceData();
    private BufferedReader bufferedReader = null;

    @BeforeClass
    public void setUp() throws Exception {
        bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH_TENS_TXT), StandardCharsets.UTF_8));
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        bufferedReader = null;
    }

    @Test
    public void readingSourceDataInOneDimenArray() {
        String[] expendedTens = {"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
                "семьдесят", "восемьдесят", "девяносто"};
        String[] actualTens = reader.readingSourceDataInOneDimenArray(PATH_TENS_TXT);

        assertArrayEquals("test failed for readingSourceDataInOneDimenArray() : " + Arrays.toString(actualTens),
                expendedTens, actualTens);
    }

    @Test
    public void readingSourceDataInTwoDimenArray() {
        String[][] expendedTens = {
                {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
                {"", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
        };
        String[][] actualTens = reader.readingSourceDataInTwoDimenArray(PATH_UNITS_TXT);

        assertArrayEquals("test failed for readingSourceDataInTwoDimenArray() : " + Arrays.toString(actualTens),
                expendedTens, actualTens);
    }

}
