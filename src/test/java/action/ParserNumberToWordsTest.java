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

public class ParserNumberToWordsTest extends Assert {
    private static final String ZERO = "ноль";
    private static final String ERROR_IN_NUMBER = "Error in number = ";
    private ParserNumberToWords parser = new ParserNumberToWords();

    @Test
    public void parseZeroTest() {
        String actual = parser.parse("0");

        assertEquals(actual, ZERO, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersUnitTest() {
        String expected = "одиннадцать";
        String actual = parser.parse("11");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersTenTest() {
        String expected = "сорок два";
        String actual = parser.parse("42");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumbersHunTest() {
        String expected = "шестьсот тридцать четыре";
        String actual = parser.parse("634");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseLargeNumberTest() {
        String expected = "шесть ноналлионов" +
                " двести сорок шесть октиллионов четыреста двадцать пять септиллионов шестьсот тридцать четыре" +
                " секстиллиона пятьсот шестьдесят три квинтиллиона четыреста шестьдесят пять квадриллионов двести" +
                " сорок три триллиона пятьсот двадцать три миллиарда четыреста пятьдесят два миллиона триста" +
                " сорок шесть тысяч двести сорок шесть";
        String actual = parser.parse("6246425634563465243523452346246");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNumberWithDeclensionTest() {
        String expected = "две тысячи";
        String actual = parser.parse("2000");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNullNumberTest() {
        String expected = "This string contains not only Unicode numeric: null. Please, repeat enter.";
        String actual = parser.parse(null);

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNotNumberStringTest() {
        String expected = "This string contains not only Unicode numeric: ABC. Please, repeat enter.";
        String actual = parser.parse("ABC");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseNotPositiveNumberTest() {
        String expected = "This string contains not only Unicode numeric: -6. Please, repeat enter.";
        String actual = parser.parse("-6");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test
    public void parseFractionalNumberTest() {
        String expected = "This string contains not only Unicode numeric: 95.3. Please, repeat enter.";
        String actual = parser.parse("95.3");

        assertEquals(actual, expected, ERROR_IN_NUMBER + actual);
    }

    @Test(dataProvider = "getNameAllTableTest")
    public void parseNumberAllTest(String inputValue, String expectedValue) {
        String actual = parser.parse(inputValue);

        assertEquals(actual, expectedValue, ERROR_IN_NUMBER + inputValue);
    }

    @DataProvider(name = "getNameAllTableTest")
    public Object[][] getNameAllTableTest() throws IOException {
        InputStream in = new FileInputStream("data/test/testNumber.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);
        Sheet sheet = wb.getSheetAt(0);
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        Object[][] testData = new Object[numberOfRows][2];

        int i = 0;
        for (Row row : sheet) {
            row.forEach(cell -> cell.setCellType(Cell.CELL_TYPE_STRING));
            testData[i][0] = row.getCell(0).getStringCellValue();
            testData[i][1] = row.getCell(1).getStringCellValue();
            i++;
        }

        return testData;
    }
}

