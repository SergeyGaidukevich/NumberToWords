package readerData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import readerSourceData.ReaderSourceData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class ReaderSourceDataTest {
    private static final String PATH_TENS_TXT = "data/tens.txt";
    private static final String PATH_UNITS_TXT = "data/units.txt";
    private final ReaderSourceData reader = new ReaderSourceData();
    private BufferedReader bufferedReader = null;

    @Before
    public void setUp() throws Exception {
        bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(PATH_TENS_TXT), "UTF8"));
    }

    @After
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

        assertArrayEquals("test failed for ReaderSourceDataTest() : " + Arrays.toString(actualTens)
                + " ", expendedTens, actualTens);
    }

    @Test
    public void readingSourceDataInTwoDimenArray() {
        String[][] expendedTens = {
                {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
                {"", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
        };
        String[][] actualTens = reader.readingSourceDataInTwoDimenArray(PATH_UNITS_TXT);

        assertArrayEquals("test failed for ReaderSourceDataTest() : " + Arrays.toString(actualTens)
                + " ", expendedTens[0], actualTens[0]);
        assertArrayEquals("test failed for ReaderSourceDataTest() : " + Arrays.toString(actualTens)
                + " ", expendedTens[1], actualTens[1]);
    }

}
