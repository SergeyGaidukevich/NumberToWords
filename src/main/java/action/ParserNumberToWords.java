package action;

import org.apache.commons.lang3.math.NumberUtils;
import readerSourceData.ReaderSourceData;

import java.util.ArrayList;

public class ParserNumberToWords {
    private static final String PATH_UNITS_TXT = "data/units.txt";
    private static final String PATH_HUNDREDS_TXT = "data/hundreds.txt";
    private static final String PATH_TEN_TO_TWENTY_TXT = "data/ten_to_twenty.txt";
    private static final String PATH_TENS_TXT = "data/tens.txt";
    private static final String PATH_FORMS_TXT = "data/forms.txt";
    private static final String ZERO = "ноль";
    private ReaderSourceData reader = new ReaderSourceData();

    public static void main(String[] args) {
        System.out.println(new ReaderSourceData().readingSourceDataInTwoDimenArray(PATH_UNITS_TXT).length);
        System.out.println(new ParserNumberToWords().parse("2006456498451984561"));
    }

    public String parse(String number) {
        if (NumberUtils.isDigits(number)) {
            return parse(new StringBuilder(number));
        } else {
            return "This string contains not only Unicode numeric: " + number + ". Please, repeat enter.";
        }
    }

    private String parse(StringBuilder number) {
        if (number.toString().equals("0")) {
            return ZERO;
        } else {
            String[][] units = reader.readingSourceDataInTwoDimenArray(PATH_UNITS_TXT);
            String[] hundreds = reader.readingSourceDataInOneDimenArray(PATH_HUNDREDS_TXT);
            String[] ten_to_twenty = reader.readingSourceDataInOneDimenArray(PATH_TEN_TO_TWENTY_TXT);
            String[] tens = reader.readingSourceDataInOneDimenArray(PATH_TENS_TXT);
            String[][] forms = reader.readingSourceDataInTwoDimenArray(PATH_FORMS_TXT);

            normalizeNumber(number);

            ArrayList<Integer> segments = new ArrayList<>();
            segmented(segments, number);

            StringBuilder stringNumberEntry = new StringBuilder();
            int currentSegmentOrder = segments.size() - 1;
            for (Integer segment : segments) {
                int segmentOrder;
                if (currentSegmentOrder + 1 < forms.length) {
                    segmentOrder = currentSegmentOrder;
                } else {
                    segmentOrder = forms.length - 1;
                }

                int gender = Integer.valueOf(forms[segmentOrder][3]);
                int currentSegment = Integer.valueOf(segment.toString());
                if (currentSegment == 0 && segmentOrder >= 1) {
                    currentSegmentOrder--;
                    continue;
                }

                String stringSegment = String.valueOf(currentSegment);
                stringSegment = normalizeSegment(stringSegment);

                int unitsNumber = Integer.valueOf(stringSegment.substring(0, 1));
                int decNumber = Integer.valueOf(stringSegment.substring(1, 2));
                int hunNumber = Integer.valueOf(stringSegment.substring(2, 3));
                int decAndHunNumber = Integer.valueOf(stringSegment.substring(1, 3));

                if (currentSegment > 99) {
                    stringNumberEntry.append(hundreds[unitsNumber]).append(" ");
                }
                if (decAndHunNumber > 20) {
                    stringNumberEntry.append(tens[decNumber]).append(" ");
                    stringNumberEntry.append(units[gender][hunNumber]).append(" ");
                } else {
                    if (decAndHunNumber > 9) {
                        stringNumberEntry.append(ten_to_twenty[decAndHunNumber - 9]).append(" ");
                    } else if (decAndHunNumber != 0) {
                        stringNumberEntry.append(units[gender][hunNumber]).append(" ");
                    }
                }

                stringNumberEntry.append(chooseForm(currentSegment, forms[segmentOrder][0], forms[segmentOrder][1],
                        forms[segmentOrder][2])).append(" ");
                currentSegmentOrder--;
            }

            return stringNumberEntry.toString().trim();
        }
    }

    private void segmented(ArrayList<Integer> segments, StringBuilder number) {
        for (int i = 0; i < number.length() / 3; i++) {
            segments.add(Integer.valueOf(number.substring((i * 3), i * 3 + 3)));
        }
    }

    private StringBuilder normalizeNumber(StringBuilder number) {
        for (int i = 0; i < number.length() % 3; i++) {
            number.insert(0, '0');
        }

        return number;
    }

    private String normalizeSegment(String segment) {
        if (segment.length() == 1) {
            segment = "00" + segment;
        }
        if (segment.length() == 2) {
            segment = "0" + segment;
        }

        return segment;
    }

    private String chooseForm(long n, String firstForm, String secondForm, String thirdForm) {
        n = Math.abs(n) % 100;
        long n1 = n % 10;

        if (n > 10 && n < 20) {
            return thirdForm;
        }
        if (n1 > 1 && n1 < 5) {
            return secondForm;
        }
        if (n1 == 1) {
            return firstForm;
        }

        return thirdForm;
    }
}
