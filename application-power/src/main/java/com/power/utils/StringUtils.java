package com.power.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunyu
 */

public class StringUtils {
    private static final String EMAIL_PATTERN = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
    private static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{0,}$";
    private static final String NUMBER_ADN_LETTER = "^[A-Za-z0-9]+$";
    private static final String QQ_PATTERN = "/[1-9][0-9]{4,}/";
    private static final String NUMBER_PATTERN = "[0-9]+";
    private static final String LETTER_PATTERN = "[a-zA-Z]+";
    private static final String ZIPCODE_PATTERN = "\\p{Digit}{6}";
    private static final String PHONE_PATTERN = "^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    private static final String TELEPHONE_PATTERN = "^(0\\d{2,3}-)?(\\d{7,8})(-(\\d{3,}))?$";
    private static final String IDCARD_PATTERN = "^((11|12|13|14|15|21|22|23|31"
            + "|32|33|34|35|36|37|41|42|43|44|45|46|50|51|"
            + "52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})"
            + "((((19|20)(([02468][048])|([13579][26]))0229))|"
            + "((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))"
            + "((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])"
            + "|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))"
            + "((\\d{3}(x|X))|(\\d{4}))$";
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_]{3,15}$";
    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)[0-9A-Za-z]{6,20}$";
    private static final String SERIALNO_PATTERN = "yyyyMMddHHmmssSSS";
    private static final char UNDERLINE = '_';

    /**
     * @param str String
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim())
                || "null".equals(str.trim()) || "NaN".equals(str.trim());
    }

    /**
     * @param str String
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @param email Email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return validate(email, EMAIL_PATTERN);
    }

    /**
     * not email
     *
     * @param email email
     * @return booleans
     */
    public static boolean isNotEmail(String email) {
        return !isEmail(email);
    }

    /**
     * Verify whether the Chinese
     *
     * @param chineseStr chineseStr
     * @return boolean
     */
    public static boolean isChinese(String chineseStr) {
        return validate(chineseStr, CHINESE_PATTERN);
    }

    /**
     * Verify whether the Numbers or letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumberAndLetter(String str) {
        return validate(str, NUMBER_ADN_LETTER);
    }

    /**
     * Verify whether QQ number
     *
     * @param qq String
     * @return boolean
     */
    public static boolean isQq(String qq) {
        return validate(qq, QQ_PATTERN);
    }

    /**
     * Verify whether numbers
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        return validate(str, NUMBER_PATTERN);
    }

    /**
     * Verify whether Letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isLetter(String str) {
        return validate(str, LETTER_PATTERN);
    }

    /**
     * validate Chinese ZipCode
     *
     * @param zipCode String
     * @return boolean
     */
    public static boolean isZipCode(String zipCode) {
        return validate(zipCode, ZIPCODE_PATTERN);
    }

    /**
     * validate Chinese Moble phone number the number include start with
     * 13,15,18
     *
     * @param phone String
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        return validate(phone, PHONE_PATTERN);
    }

    public static boolean isNotPhone(String phone) {
        return !isPhone(phone);
    }

    /**
     * Verify whether the fixed telephone in China
     *
     * @param phoneNumber String
     * @return boolean
     */
    public static boolean isTelephone(String phoneNumber) {
        return validate(phoneNumber, TELEPHONE_PATTERN);
    }

    /**
     * validate identity card number,if is valid return true else return false
     *
     * @param cardNumber IdCard Number
     * @return boolean
     */
    public static boolean isIdCard(String cardNumber) {
        return validate(cardNumber, IDCARD_PATTERN);
    }

    /**
     * validate UserName
     *
     * @param str String
     * @return boolean
     */
    public static boolean isUserName(String str) {
        return validate(str, USERNAME_PATTERN);
    }

    /**
     * @param str password
     * @return boolean
     */
    public static boolean isPassword(String str) {
        return validate(str, PASSWORD_PATTERN);
    }
    /**
     * 非负整数
     * @param str
     * @return
     */
    public static boolean isNonnegativeInteger(String str){
        return validate(str ,"^\\d+$");
    }
    public static boolean isSameCharacter(String s) {
        s = s.toUpperCase();
        String character = s.substring(0, 1);
        String replace = "";
        String test = s.replace(character, replace);
        return "".equals(test);
    }

    public static boolean isContinuityCharacter(String s) {
        boolean continuity = true;
        char[] data = s.toCharArray();
        for (int i = 0; i < data.length - 1; i++) {
            int a = Integer.parseInt(data[i] + "");
            int b = Integer.parseInt(data[i + 1] + "");
            continuity = continuity && (a + 1 == b || a - 1 == b);
        }
        return continuity;
    }

    /**
     * @param str String
     * @return String
     */
    public static String getCharCode(String str) {
        String temp = "";
        for (int i = 0; i < temp.length(); i++) {
            temp += Integer.toHexString((int) str.charAt(i)) + "nbsp;";
        }
        return temp;
    }

    /**
     * convert to 8859
     *
     * @param str String
     * @return String
     */
    public static String convertTo8859(String str) {
        String strOutPut = "";
        try {
            byte[] tempStrByte = str.getBytes("ISO-8859-1");
            strOutPut = new String(tempStrByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strOutPut;
    }

    public static String capitalise(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 去除字符串空格
     *
     * @param sourceStr input string
     * @return string
     */
    public static String trim(String sourceStr) {
        if (isEmpty(sourceStr)) {
            return null;
        } else {
            return sourceStr.replaceAll(" ", "");
        }
    }

    /**
     * 过滤特殊字符串
     *
     * @param str String
     * @return String
     */
    public static String filterStr(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = str.replace("?", "");
            str = str.replaceAll(";", "");
            str = str.replaceAll("%", "");
            str = str.replaceAll("--", "");
            str = str.replaceAll("/", "");
            str = str.replaceAll("=", "");
            str = str.replaceAll("<", "&lt");
            str = str.replaceAll(">", "&gt");
            return str;
        }
    }

    /**
     * 根据正则表但是匹配字符串
     *
     * @param str     字符串
     * @param pattern 正则表达式
     * @return boolean
     */
    public static boolean validate(String str, String pattern) {
        return isNotEmpty(str) && str.matches(pattern);
    }

    /**
     * 驼峰书写转化为下划线
     *
     * @param param 待转换字符
     * @return String
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下滑线写法转化为驼峰书写
     *
     * @param param 待转换字符
     * @return String
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < length) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @return
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = underlineToCamel(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    /**
     * 首字母转大写
     *
     * @param param String
     * @return String
     */
    public static String firstToUpperCase(String param) {
        char[] cs = param.toCharArray();
        if (cs[0] > 96 && cs[0] < 123) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

    /**
     * 首字母转小写
     *
     * @param param String
     * @return String
     */
    public static String firstToLowerCase(String param) {
        char[] cs = param.toCharArray();
        if (cs[0] > 64 && cs[0] < 91) {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    /**
     * 根据时间生成可视序列号
     *
     * @return String
     */
    public static String createSerialNo() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(SERIALNO_PATTERN);
        return format.format(cal.getTime());
    }

    /**
     * 二分法实现字符串反序 该方法效率不及原生的StringBuffer 或者StringBuildder提供的 reverse效率高
     *
     * @param str 待反序的字符
     * @return String
     */
    public static String reverse(String str) {

        int length = str.length();
        int half = (length >> 1) + 1;
        char[] a = str.toCharArray();
        for (int i = 0, j = length - 1; i < half; i++, j--) {
            char str1 = str.charAt(i);
            char str2 = str.charAt(j);
            a[i] = str2;
            a[j] = str1;
        }
        return String.valueOf(a);

    }

    public static String urlDecode(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            try {
                return java.net.URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 8859编码转换称utf8
     *
     * @param str String
     * @return String
     */
    public static String ios8859ToUtf8(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            try {
                return new String(str.getBytes("iso8859-1"), "utf-8");
            } catch (Exception e) {
                return null;
            }
        }
    }



    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuilder tmp = new StringBuilder();
        int iTmp;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    private static String fillStringByArgs(String str, String[] arr) {
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
        }
        return str;
    }

    public static boolean isUuid(String str) {
        if(isNotEmpty(str)){
            str = trim(str);
            return !(str.length() < 32 || str.length() > 50);
        }else{
            return false;
        }
    }

    public static boolean isNotUuid(String str) {
        return !isUuid(str);
    }

    //left trim and right trim
    public static String trimBlank(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            return str.replaceAll("^[　 ]+|[　 ]+$", "");
        }
    }

    public static int length(String str) {
        if (isEmpty(str)) {
            return 0;
        } else {
            return str.length();
        }
    }

    /**
     * 生成指定长度的随机整数
     *
     * @param length int
     * @return String
     */
    public static String createRandom(int length) {
        double a = Math.pow(10, length - 1);
        int num = (int) ((Math.random() * 9 + 1) * a);
        return String.valueOf(num);
    }

    /**
     * 移除查询关键词中单引号或双引号，避免sql错误
     *
     * @param str String
     * @return String
     */
    public static String removeQuotes(String str) {
        if (isNotEmpty(str)) {
            return str.replace("'", "").replace("\"", "");
        } else {
            return "";
        }
    }
    public static String replaceHtml(String html) {
        if (isEmpty(html)){
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }
    public static String replaceMobileHtml(String html){
        if (html == null){
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }
    public static boolean strToBoolean(String str){
        if(isEmpty(str)){
            return false;
        }else{
            str = str.trim();
            return Boolean.valueOf(str);
        }
    }

    /**
     * 提取中文
     * @param str
     * @return
     */
    public static String getChinese(String str){
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, "");
        return str;
    }

    /**
     * 提非中文
     * @param str
     * @return
     */
    public static String getNotChinese(String str){
        String reg = "[^A-Za-z0-9_]";
        str = str.replaceAll(reg, "");
        return str;
    }
    /**
     * 去掉指定前缀
     *
     * @param str 字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(String str, String prefix) {
        if(isEmpty(str) || isEmpty(prefix)){
            return str;
        }

        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }
}
