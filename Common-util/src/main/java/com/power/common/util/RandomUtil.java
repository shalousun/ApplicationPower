package com.power.common.util;

import java.text.DecimalFormat;
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
    private static String FORMAT = "0.00";
    private static Random random = new Random();

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
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        return rangeLong;
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
        StringBuffer sb = new StringBuffer();

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
            case "String":  //12
                return randomString(6);
            case "char":
                return randomString(1);
            case "Integer":    //4
                return String.valueOf(randomInt(1000));
            case "int":
                return String.valueOf(randomInt(1000));
            case "Long": //-5
                return String.valueOf(randomInt(1000));
            case "long":
                return String.valueOf(randomInt(1000));
            case "Double": //8
                return String.valueOf(randomDouble(FORMAT));
            case "double":
                return String.valueOf(randomDouble(FORMAT));
            case "Float": //6
                return String.valueOf(randomDouble(FORMAT));
            case "float":
                return String.valueOf(randomDouble(FORMAT));
            case "short":
                return String.valueOf(randomInt(0, 32767));
            case "Short":
                return String.valueOf(randomInt(0, 32767));
            case "boolean":
                return "true";
            case "Boolean":
                return "true";
            case "BigDecimal":    //3
                return String.valueOf(randomInt(1000));
            case "BigInteger":
                return String.valueOf(randomInt(1000));
            case "Time":  //91
                return DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
            case "Date":
                return DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
            case "LocalDate":
                return DateTimeUtil.long2Str(System.currentTimeMillis(), DateTimeUtil.DATE_FORMAT_DAY);
            case "Timestamp":  //91
                return DateTimeUtil.long2Str(System.currentTimeMillis(), DateTimeUtil.DATE_FORMAT_SECOND);
            case "LocalDateTime":
                return DateTimeUtil.long2Str(System.currentTimeMillis(), DateTimeUtil.DATE_FORMAT_SECOND);
            default:
                return randomString(6);
        }
    }
}
