package com.power.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author yu 2018/9/15.
 */
public class IDCardUtil {


    /**
     * generate IdCard
     *
     * @return String
     */
    public static String getIdCard() {
        String provinces[] = {"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82"};
        String no = new Random().nextInt(899) + 100 + "";
        String checks[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        StringBuilder builder = new StringBuilder();
        builder.append(randomOne(provinces));
        builder.append(randomCityCode(18));
        builder.append(randomCityCode(28));
        builder.append(randomBirth(20, 50));
        builder.append(no).append(randomOne(checks));
        return builder.toString();
    }


    private static String randomOne(String s[]) {
        return s[new Random().nextInt(s.length - 1)];
    }


    private static String randomCityCode(int max) {
        int i = new Random().nextInt(max) + 1;
        return i > 9 ? i + "" : "0" + i;
    }

    private static String randomBirth(int minAge, int maxAge) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        int randomDay = 365 * minAge + new Random().nextInt(365 * (maxAge - minAge));
        date.set(Calendar.DATE, date.get(Calendar.DATE) - randomDay);
        return dft.format(date.getTime());
    }
}
