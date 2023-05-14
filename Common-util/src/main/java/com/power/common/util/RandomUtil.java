package com.power.common.util;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

/**
 * @author yu 2018/06/03.
 */
public class RandomUtil {

    /**
     * random selected numbers
     */
    private static final String BASE_NUMBER = "0123456789";
    /**
     * random selected characters
     */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /**
     * random selected characters and numbers
     */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;
    private static final String FORMAT = "0.00";
    private static final Random random = new Random();

    /**
     * random int value between min and max
     *
     * @param min min value
     * @param max max value
     * @return int value
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * random int value between o and limit
     *
     * @param limit limit of max value
     * @return int value
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

    /**
     * random int value
     *
     * @return int value
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * random long
     *
     * @return long value
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * random long value between min and max
     *
     * @param min min value
     * @param max max value
     * @return long value
     */
    public static long randomLong(long min, long max) {
        return min + (((long) (new Random().nextDouble() * (max - min))));
    }

    /**
     * random string that only contains numbers and letters
     *
     * @param length length of String
     * @return random string
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * random string that only contains numbers
     *
     * @param length length of String
     * @return random string
     */
    public static String randomNumbers(int length) {
        return randomString(BASE_NUMBER, length);
    }


    /**
     * random string from base characters
     *
     * @param baseString base characters
     * @param length     length of String
     * @return random string
     */
    public static String randomString(String baseString, int length) {
        StringBuilder sb = new StringBuilder();

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * random double value between min and max
     *
     * @param min min value
     * @param max max value
     * @return double
     */
    public static double randomDouble(final double min, final double max) {
        return min + ((max - min) * random.nextDouble());
    }

    /**
     * random double value between 0 and 100
     *
     * @return double
     */
    public static double randomDouble() {
        return randomDouble(0.00, 100.00);
    }

    /**
     * random double value between 0 and 100
     *
     * @param format number format
     * @return String
     */
    public static String randomDouble(String format) {
        return new DecimalFormat(format).format(randomDouble());
    }

    /**
     * Generate random initial values based on type
     *
     * @param type type of object
     * @return string
     */
    public static String randomValueByType(String type) {
        switch (type) {
            //12
            case "char":
                return randomString(1);
            case "Integer":    //4
            case "int":
            case "Long": //-5
            case "long":
            case "BigDecimal":    //3
            case "BigInteger":
                return String.valueOf(randomInt(1000));
            case "Double": //8
            case "double":
            case "Float": //6
            case "float":
                return String.valueOf(randomDouble(FORMAT));
            case "short":
            case "Short":
                return String.valueOf(randomInt(0, 32767));
            case "Byte":
            case "byte":
                return String.valueOf(randomInt(0, 127));
            case "boolean":
            case "Boolean":
                return "true";
            case "Time":  //91
            case "Date":
                return DateTimeUtil.dateToStr(new Date(), DateTimeUtil.DATE_FORMAT_SECOND);
            case "LocalTime":
                return DateTimeUtil.dateToStr(new Date(), DateTimeUtil.LOCAL_TIME);
            case "Year":
                return DateTimeUtil.dateToStr(new Date(), DateTimeUtil.YEAR);
            case "MonthDay":
                return DateTimeUtil.dateToStr(new Date(), DateTimeUtil.MONTH_DAY);
            case "YearMonth":
                return DateTimeUtil.dateToStr(new Date(), DateTimeUtil.YEAR_MONTH);
            case "LocalDate":
                return DateTimeUtil.long2Str(System.currentTimeMillis(), DateTimeUtil.DATE_FORMAT_DAY);
            case "Timestamp":  //91
            case "LocalDateTime":
                return DateTimeUtil.long2Str(System.currentTimeMillis(), DateTimeUtil.DATE_FORMAT_SECOND);
            case "ZonedDateTime":
                return DateTimeUtil.zonedDateTimeToStr(ZonedDateTime.now(), DateTimeUtil.DATE_FORMAT_ZONED_DATE_TIME);
            case "OffsetDateTime":
                return OffsetDateTime.now().toString();
            case "uuid":
            case "UUID":
                return UUIDUtil.getUuid();
            default:
                return randomString(6);
        }
    }

    public static String generateDefaultValueByType(String type) {
        switch (type) {
            //12
            case "char":
                return "";
            case "Integer":    //4
            case "int":
            case "Long": //-5
            case "long":
            case "BigDecimal":    //3
            case "BigInteger":
                return "0";
            case "Double": //8
            case "double":
            case "Float": //6
            case "float":
                return "0.0";
            case "short":
            case "Short":
                return "0";
            case "boolean":
            case "Boolean":
                return "true";
            case "Time":  //91
            case "Date":
            case "Timestamp":
            case "LocalDateTime":
                return DateTimeUtil.DATE_FORMAT_SECOND;
            case "LocalTime":
                return DateTimeUtil.LOCAL_TIME;
            case "Year":
                return DateTimeUtil.YEAR;
            case "MonthDay":
                return DateTimeUtil.MONTH_DAY;
            case "YearMonth":
                return DateTimeUtil.YEAR_MONTH;
            case "LocalDate":
                return DateTimeUtil.DATE_FORMAT_DAY;
            case "ZonedDateTime":
            case "OffsetDateTime":
                return DateTimeUtil.DATE_FORMAT_ZONED_DATE_TIME;
            default:
                return "";
        }
    }
}
