package action;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ParserNumberToWordsTest extends Assert {
    private static final String ZERO = "ноль";
    private static final String ERROR_IN_NUMBER = "Error in number = ";
    private ParserNumberToWords parser = new ParserNumberToWords();

    @Test
    public void parseZeroTest() {
        String actual = parser.parse("0").get(0);

        assertEquals(actual, ZERO, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersUnitTest() {
        String expected = "одиннадцать";
        String actual = parser.parse("11").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersTenTest() {
        String expected = "сорок два";
        String actual = parser.parse("42").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersHunTest() {
        String expected = "шестьсот тридцать четыре";
        String actual = parser.parse("634").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseLargeNumberTest() {
        String expected = "шесть ноналлионов" +
                " двести сорок шесть октиллионов четыреста двадцать пять септиллионов шестьсот тридцать четыре" +
                " секстиллиона пятьсот шестьдесят три квинтиллиона четыреста шестьдесят пять квадриллионов двести" +
                " сорок три триллиона пятьсот двадцать три миллиарда четыреста пятьдесят два миллиона триста" +
                " сорок шесть тысяч двести сорок шесть";
        String actual = parser.parse("6246425634563465243523452346246").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumberWithDeclensionTest() {
        String expected = "две тысячи";
        String actual = parser.parse("2000").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNullNumberTest() {
        String expected = "This string contains not only Unicode numeric: null. Please, repeat enter.";
        String actual = parser.parse((String) null).get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNotNumberStringTest() {
        String expected = "This string contains not only Unicode numeric: ABC. Please, repeat enter.";
        String actual = parser.parse("ABC").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNotPositiveNumberTest() {
        String expected = "This string contains not only Unicode numeric: -6. Please, repeat enter.";
        String actual = parser.parse("-6").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseFractionalNumberTest() {
        String expected = "This string contains not only Unicode numeric: 95.3. Please, repeat enter.";
        String actual = parser.parse("95.3").get(0);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @DataProvider(parallel = true)
    public Object[][] getNameAllTableTest() throws IOException {
        Map<String, String> testingDate = new HashMap<>();
        InputStream in = new FileInputStream("data/test/testNumber.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);
        long number = 0;
        String inString = null;
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_NUMERIC:
                        number = (long) cell.getNumericCellValue();
                        break;
                    case Cell.CELL_TYPE_STRING:
                        inString = cell.getStringCellValue();
                        break;
                    default:
                        break;
                }
            }
            testingDate.put(inString, parser.parse(String.valueOf(number)).get(0));
        }

        Object[][] resultDate = new Object[testingDate.size()][2];
        int index = 0;
        testingDate.forEach((k, v) -> {
            resultDate[index][0] = v;
            resultDate[index][1] = k;
        });
        return resultDate;
    }

    @Test(dataProvider = "getNameAllTableTest")
    public void parseNumberAllTest(String actual, String expected) {
        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }
}

