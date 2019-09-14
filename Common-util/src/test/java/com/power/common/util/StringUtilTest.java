package com.power.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yu on 2017/2/9.
 */
public class StringUtilTest {
    public static void main(String[] args) {
        String str = "36032319911018276X";

        //System.out.println(ValidateUtil.isIdCard(str));
        String url = "/login?a=b";
        List<String> list = new ArrayList<>();
        list.add("/abc/.*");
        list.add("/ac/*");
        list.add("/me/add");
        list.add("/login.*");
        System.out.println(DateTimeUtil.strToLong("2019-08-30 20:44:38.40", "yyyy-MM-dd HH:mm:ss.SS"));
        System.out.println(DateTimeUtil.long2Str(1567169078400L, DateTimeUtil.DATE_FORMAT_SECOND));
        // System.out.println(isMatches(list,url));


    }

    public static boolean isMatches(List<String> patterns, String url) {
        for (String str : patterns) {
            System.out.println("pattern:" + str);
            // System.out.println(str.endsWith("/*"));
            if (str.endsWith("/*")) {
                String name = str.substring(0, str.length() - 2);
                if (url.contains(name)) {
                    return true;
                }
            } else {
                Pattern pattern = Pattern.compile(str);
                if (pattern.matcher(url).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
