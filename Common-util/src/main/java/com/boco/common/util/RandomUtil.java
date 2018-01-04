package com.boco.common.util;

import java.util.Date;
import java.util.Random;

/**
 *
 */
public class RandomUtil {
    private static Random random = new Random();

    /** 用于随机选的数字 */
    private static final String BASE_NUMBER = "0123456789";
    /** 用于随机选的字符 */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /** 用于随机选的字符和数字 */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

    /**
     * 获得指定范围内的随机数
     * @param min 最小数
     * @param max 最大数
     * @return
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }
    /**
     * 获得随机数
     * @return 随机数
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符）
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * 获得一个只包含数字的字符串
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomNumbers(int length) {
        return randomString(BASE_NUMBER, length);
    }


    /**
     * 获得一个随机的字符串
     * @param baseString 随机字符选取的样本
     * @param length 字符串的长度
     * @return 随机字符串
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
     *
     * @param min
     * @param max
     * @return
     */
    public static double randomDouble(final double min, final double max) {
        return min + ((max - min) * random.nextDouble());
    }

    /**
     *
     * @return
     */
    public static double randomDouble(){
        return randomDouble(0.00,100.00);
    }


    /**
     * 根据类型生成初始值
     * @param type
     * @return
     */
    public static String randomValueByType(String type) {
        String dataType = "";
        switch (type) {
            case "String":  //12
                dataType = randomString(6);
                break;
            case "Integer":    //4
                dataType = String.valueOf(randomInt(1000));
                break;
            case "Long": //-5
                dataType = String.valueOf(randomInt(1000));
                break;
            case "Double": //8
                dataType = String.valueOf(randomDouble());
                break;
            case "Float": //6
                dataType = String.valueOf(randomDouble());
                break;
            case "BigDecimal":    //3
                dataType = "BigDecimal";
                break;
            case "Time":  //91
                dataType = DateTimeUtil.dateToStr(new Date());
                break;
            case "Timestamp":  //91
                dataType = DateTimeUtil.long2Str(System.currentTimeMillis(),DateTimeUtil.DATE_FORMAT_SECOND);
                break;
            default:
                dataType = randomString(6);
        }
        return dataType;
    }
}
