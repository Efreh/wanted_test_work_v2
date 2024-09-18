package tasks;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MoneyParser {

    private final String[] BEFORE_TWENTY =
            {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять",
                    "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private final String[] TENS = {"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private final String[] HUNDREDS = {"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};
    private final String[] THOUSANDS = {"", "одна тысяча", "две тысячи", "три тысячи", "четыре тысячи", "пять тысяч", "шесть тысяч", "семь тысяч", "восемь тысяч", "девять тысяч"};




    public String priceAlphabeticView(BigDecimal price) {

        StringBuilder result = new StringBuilder();
        BigInteger wholePart = price.toBigInteger();
        BigInteger decimalPart = price.remainder(BigDecimal.ONE).unscaledValue();

        BigInteger twentyBig = BigInteger.valueOf(20);
        BigInteger oneHundredBig = BigInteger.valueOf(100);
        BigInteger oneThousandsBig = BigInteger.valueOf(1000);
        BigInteger oneHundredThousandBig = BigInteger.valueOf(100000);

        if (wholePart.compareTo(twentyBig) < 0) {
            result.append(BEFORE_TWENTY[wholePart.intValue()]);
        } else if (wholePart.compareTo(oneHundredBig) < 0) {
            int intBefore100 = wholePart.intValue();
            int tens = intBefore100 / 10;
            int ones = intBefore100 % 10;
            result.append(TENS[tens]);
            if (ones != 0) {
                result.append(" ").append(BEFORE_TWENTY[ones]);
            }
        } else if (wholePart.compareTo(oneThousandsBig) < 0) {
            int intBefore1k = wholePart.intValue();
            int hundreds = intBefore1k / 100;
            int tens = intBefore1k % 100 / 10;
            int ones = intBefore1k % 10;
            result.append(HUNDREDS[hundreds]);
            if (tens > 1) {
                result.append(" ").append(TENS[tens]);
                if (ones != 0) {
                    result.append(" ").append(BEFORE_TWENTY[ones]);
                }
            } else {
                result.append(" ").append(BEFORE_TWENTY[intBefore1k % 100]);
            }
        } else if (wholePart.compareTo(oneHundredThousandBig) < 0) {
            int intBefore100k = wholePart.intValue();
            int thousand = intBefore100k / 1000;
            int hundreds = intBefore100k % 1000 / 100;
            int tens = intBefore100k % 100 / 10;
            int ones = intBefore100k % 10;
            if (thousand < 10) {
                result.append(THOUSANDS[thousand]);
            } else if (thousand < 20) {
                result.append(BEFORE_TWENTY[thousand]).append(" тысяч");
            } else {
                if (thousand % 10 == 0) {
                    result.append(TENS[thousand / 10]).append(" тысяч");
                } else {
                    result.append(TENS[thousand / 10]).append(" ").append(BEFORE_TWENTY[thousand % 10]).append(" тысяч");
                }
            }

            if (hundreds != 0) {
                result.append(" ").append(HUNDREDS[hundreds]);
            }
            if (tens > 1) {
                result.append(" ").append(TENS[tens]);
                if (ones != 0) {
                    result.append(" ").append(BEFORE_TWENTY[ones]);
                }
            } else {
                result.append(" ").append(BEFORE_TWENTY[intBefore100k % 100]);
            }
        }

        int wholeTwoLastNum = wholePart.mod(BigInteger.valueOf(100)).intValue();
        if (wholeTwoLastNum >= 11 && wholeTwoLastNum <= 19) {
            result.append(" рублей");
        } else {
            switch (wholePart.mod(BigInteger.TEN).intValue()) {
                case 1:
                    result.append(" рубль");
                    break;
                case 2:
                case 3:
                case 4:
                    result.append(" рубля");
                    break;
                default:
                    result.append(" рублей");
            }
        }


        if (decimalPart.compareTo(BigInteger.ZERO) > 0) {
            int decimalInt = decimalPart.intValue();
            if (decimalInt < 20) {
                if (decimalInt == 1) {
                    result.append(" одна");
                } else if (decimalInt == 2) {
                    result.append(" две");
                } else {
                    result.append(" ").append(BEFORE_TWENTY[decimalInt]);
                }
            } else {
                if (decimalInt % 10 == 1) {
                    result.append(" ").append(TENS[decimalInt / 10]).append(" одна");
                } else if (decimalInt % 10 == 2) {
                    result.append(" ").append(TENS[decimalInt / 10]).append(" две");
                } else {
                    result.append(" ").append(TENS[decimalInt / 10]).append(" ").append(BEFORE_TWENTY[decimalInt % 10]);
                }
            }
        }

        int decimalTwoLastNum = decimalPart.mod(BigInteger.valueOf(100)).intValue();
        if (decimalTwoLastNum >= 11 && decimalTwoLastNum <= 19) {
            result.append(" копеек");
        } else {
            switch (decimalPart.mod(BigInteger.TEN).intValue()) {
                case 1:
                    result.append(" копейка");
                    break;
                case 2:
                case 3:
                case 4:
                    result.append(" копейки");
                    break;
                default:
                    result.append(" копеек");
            }
        }
        return result.toString();
    }
}

class MoneyParserTest {
    public static void main(String[] args) {
        System.out.println(new MoneyParser().priceAlphabeticView(new BigDecimal("45361.16")));
    }
}