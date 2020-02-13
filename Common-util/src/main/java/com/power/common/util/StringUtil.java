package com.power.common.util;

/**
 * @author sunyu
 */

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String ENMPTY = "";
    private static final String SERIALNO_PATTERN = "yyyyMMddHHmmssSSS";
    private static final char UNDERLINE = '_';
    private static final char HYPHEN_LINE = '-';

    /**
     * Checks if a CharSequence is empty or null.
     *
     * @param str String
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equals(str.trim()) || "NaN".equals(str.trim());
    }

    /**
     * Checks if a CharSequence is empty or null.
     *
     * @param str String
     * @return {@code false } if the CharSequence is empty or null
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Check if characters are the same in the string.
     *
     * @param s the CharSequence to check.
     * @return {@code true } if characters are the same in the string
     */
    public static boolean isSameCharacter(String s) {
        s = s.toUpperCase();
        String character = s.substring(0, 1);
        String replace = "";
        String test = s.replace(character, replace);
        return "".equals(test);
    }

    /**
     * Check if the character in a string is a continuous character
     *
     * @param s the CharSequence to check
     * @return {@code true } if the character in a string is a continuous character
     */
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
     * get char code
     *
     * @param str String
     * @return String
     */
    public static String getCharCode(String str) {
        String temp = "";
        for (int i = 0; i < temp.length(); i++) {
            temp += Integer.toHexString(str.charAt(i)) + "nbsp;";
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
     * Remove string space
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
     * Clear special characters in a string
     *
     * @param str String
     * @return String
     */
    public static String filterStr(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = str.replaceAll(";", "");
            str = str.replaceAll("%", "");
            str = str.replaceAll("--", "");
            str = str.replaceAll("/", "");
            str = str.replaceAll("=", "");
            str = str.replaceAll("'", "&#39;");
            str = str.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
            str = str.replaceAll("<", "&lt");
            str = str.replaceAll(">", "&gt");
            return str;
        }
    }

    /**
     * Clear wildcards in sql
     *
     * @param str sql
     * @return string
     */
    public static String cleanSqlWildCharater(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = str.replaceAll("%", "invalid character");
            str = str.replaceAll("_", "invalid character");
            str = str.replaceAll("=", "invalid character");
            return str;
        }
    }

    /**
     * Clear xss script injection
     *
     * @param value script
     * @return not contains xss script
     */
    public static String cleanXSS(String value) {
        if (null == value) {
            return value;
        } else {
            value = value.replaceAll("\\bselect\\b", "invalid character");
            value = value.replaceAll("\\band\\b", "invalid character");
            value = value.replaceAll("\\bor\\b", "invalid character");
            value = value.replaceAll("\\bdelete\\b", "invalid character");
            value = value.replaceAll("\\bjoin\\b", "invalid character");
            value = value.replaceAll("\\bdrop\\b", "invalid character");

            value = value.replaceAll("\\+", "&#43;");
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("%", "&#37;");
            // value = value.replaceAll("\"","&quot;");
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("alert", "invalid character");
            value = value.replaceAll("eval\\((.*)\\)", "invalid character");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value = value.replaceAll("<\\s*script", "invalid character");
            value = value.replaceAll("location.href", "invalid character");
        }
        return value;
    }

    /**
     * camel to underline
     *
     * @param param pending character
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
     * underline to camel
     *
     * @param param pending character
     * @return String
     */
    public static String underlineToCamel(String param) {
        return toCamel(param, UNDERLINE);
    }

    /**
     * hyphen line to camel
     *
     * @param param pending character
     * @return String
     */
    public static String hyphenLineToCamel(String param) {
        return toCamel(param, HYPHEN_LINE);
    }

    /**
     * Camel case
     *
     * @param s characters
     * @return String after Camel case
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = underlineToCamel(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Turn the first letter into a uppercase
     *
     * @param param pending character
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
     * Turn the first letter into a lowercase
     *
     * @param param pending character
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
     * Generating sequence number according to timestamp
     *
     * @return String
     */
    public static String createSerialNo() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(SERIALNO_PATTERN);
        return format.format(cal.getTime());
    }

    /**
     * Decoding the parameters passed by the URL
     *
     * @param str pending character
     * @return String after decode
     */
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
     * convert 8859 to utf-8
     *
     * @param str pending character
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

    /**
     * Convert binary strings to hexadecimal strings
     *
     * @param bString binary string
     * @return String
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
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

    /**
     * Convert hex strings to binary strings
     *
     * @param hexString hexadecimal strings
     * @return binary strings
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * @param str
     * @param arr
     * @return
     */
    private static String fillStringByArgs(String str, String[] arr) {
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
        }
        return str;
    }

    /**
     * left trim and right trim
     *
     * @param str string
     * @return string
     */
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
     * Remove single or double quotes in query keywords to avoid sql errors
     *
     * @param str String
     * @return String
     */
    public static String removeQuotes(String str) {
        if (isNotEmpty(str)) {
            return str.replaceAll("'", "").replaceAll("\"", "");
        } else {
            return "";
        }
    }

    /**
     * replace special character of html
     *
     * @param html html tag
     * @return String after replaced}
     */
    public static String replaceHtml(String html) {
        if (isEmpty(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * replace special character of html
     *
     * @param html html tag
     * @return String after replaced}
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }


    /**
     * Extract Chinese in a string
     *
     * @param str characters
     * @return Chinese characters
     */
    public static String getChinese(String str) {
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, "");
        return str;
    }

    /**
     * Extract non-Chinese characters in a string
     *
     * @param str characters
     * @return non-Chinese characters
     */
    public static String getNotChinese(String str) {
        String reg = "[^A-Za-z0-9_]";
        str = str.replaceAll(reg, "");
        return str;
    }

    /**
     * Remove the specified prefix
     *
     * @param str    source
     * @param prefix prefix
     * @return If the prefix does not match, return the original string
     */
    public static String removePrefix(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }

        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * split
     *
     * @param str   source str
     * @param regex regex expression
     * @return array of String
     */
    public static String[] split(String str, String regex) {
        if (null != str) {
            return str.split(regex);
        }
        return null;
    }

    /**
     * Left pad a long number with zero
     *
     * @param seq sequence number
     * @param len max length of number
     * @return String
     */
    public static String seqNumLeftPadZero(long seq, int len) {
        String b = String.valueOf(seq);
        StringBuilder builder = new StringBuilder();
        int rest = len - b.length();
        for (int i = 0; i < rest; i++) {
            builder.append("0");
        }
        builder.append(b);
        return builder.toString();
    }

    /**
     * convert unicode to string
     *
     * @param unicode unicode character
     * @return String
     */
    public static String unicode2String(String unicode) {
        if (StringUtil.isEmpty(unicode)) {
            return "";
        }
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

    private static String toCamel(String param, char s) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (c == s) {
                if (++i < length) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
