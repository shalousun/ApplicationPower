package com.power.common.util;

/**
 * 数据校验常用正则封装
 *
 * @author sunyu
 */
public class ValidateUtil {

    public static final String EMAIL_PATTERN = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
    public static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{0,}$";
    public static final String NUMBER_ADN_LETTER = "^[A-Za-z0-9]+$";
    public static final String QQ_PATTERN = "/[1-9][0-9]{4,}/";
    public static final String NUMBER_PATTERN = "[0-9]+";
    public static final String LETTER_PATTERN = "[a-zA-Z]+";
    public static final String ZIPCODE_PATTERN = "\\p{Digit}{6}";
    public static final String PHONE_PATTERN = "^(13[0-9]|14[579]|15[^4,\\D]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    public static final String TELEPHONE_PATTERN = "^(0\\d{2,3}-)?(\\d{7,8})(-(\\d{3,}))?$";
    public static final String TELEPHONE_400_PATTERN = "((400)(\\d{7}))|((400)-(\\d{3})-(\\d{4}))";
    public static final String IDCARD_PATTERN = "^((11|12|13|14|15|21|22|23|31"
            + "|32|33|34|35|36|37|41|42|43|44|45|46|50|51|"
            + "52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})"
            + "((((19|20)(([02468][048])|([13579][26]))0229))|"
            + "((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))"
            + "((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])"
            + "|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))"
            + "((\\d{3}(x|X))|(\\d{4}))$";
    public static final String USERNAME_PATTERN = "^[A-Za-z0-9_]{3,15}$";
    public static final String PASSWORD_PATTERN = "^(?![0-9]+$)[0-9A-Za-z]{6,20}$";
    public static final String UUID_PATTERN = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";
    public static final String YEAR_PATTERN = "^(19|20)\\d{2}$";
    public static final String DATE_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    public static final String TIME_STAMP_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$";
    public static final String IPV4_PATTERN = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    public static final String IPV6_PATTERN = "([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)";
    public static final String URL_PATTERN = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$#\\=~_\\-@]*)*$";
    public static final String DOMAIN_PATTERN = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
    public static final String INT_OR_FLOAT_PATTERN = "^\\d+\\.\\d+|\\d+$";
    public static final String FLOAT_PATTERN = "^(-?\\d+)(\\.\\d+)?$";
    public static final String POSITIVE_INTEGER = "[1-9]+\\d{0,10}";
    public static final String ARMY_ID_CARD = "南字第(\\d{6,8})号|北字第(\\d{6,8})号|沈字第(\\d{6,8})号|兰字第(\\d{6,8})号|成字第(\\d{6,8})号|济字第(\\d{6,8})号|广字第(\\d{6,8})号|海字第(\\d{6,8})号|空字第(\\d{6,8})号|参字第(\\d{6,8})号|政字第(\\d{6,8})号|后字第(\\d{6,8})号|装字第(\\d{6,8})号";


    public static final String[] forbidden = {"\\bselect\\b", "\\bor\\b", "\\bdelete\\b", "\\bjoin\\b","\\btable\\b"
            , "\\bdrop\\b", "\\biframe\\b","\\bwindow\\b", "\\b_\\b", "\\+", "%", "\\<", "\\>", "'","=",
            "%3C", "\\(", "%28", "alert", "eval((.*))", "script","location.href"};

    /**
     * Match strings based on regular expressions
     * @param str     String
     * @param pattern regular expressions
     * @return boolean
     */
    public static boolean validate(String str, String pattern) {
        return StringUtil.isNotEmpty(str) && str.matches(pattern);
    }

    /**
     * Check if the string is a email
     * @param email Email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return validate(email, EMAIL_PATTERN);
    }

    /**
     * Check if the string is  not a email
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
     * Verify whether is not Chinese
     * @param chineseStr characters
     * @return boolean
     */
    public static boolean isNotChinese(String chineseStr){
        return !validate(chineseStr, CHINESE_PATTERN);
    }

    /**
     * Verify whether the Numbers or letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumberLetter(String str) {
        return validate(str, NUMBER_ADN_LETTER);
    }

    /**
     * Verify whether the not Numbers or letters
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNotNumberLetter(String str) {
        return !validate(str, NUMBER_ADN_LETTER);
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
     * Verify whether not numbers
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNotNumber(String str) {
        return !validate(str, NUMBER_PATTERN);
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
        return validate(phoneNumber, TELEPHONE_PATTERN) || validate(phoneNumber, TELEPHONE_400_PATTERN);
    }

    /**
     * Verify whether not the fixed telephone in China
     *
     * @param phoneNumber String phone number
     * @return boolean
     */
    public static boolean isNotTelephone(String phoneNumber) {
        return !ValidateUtil.isTelephone(phoneNumber);
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
     * validate identity card number,if is valid return false else return true
     *
     * @param cardNumber chinese IdCard
     * @return boolean
     */
    public static boolean isNotIdCard(String cardNumber) {
        return !validate(cardNumber, IDCARD_PATTERN);
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
     * validate password
     * @param str password
     * @return boolean
     */
    public static boolean isPassword(String str) {
        return validate(str, PASSWORD_PATTERN);
    }

    /**
     * Verify non-negative integers
     *
     * @param str string
     * @return boolean
     */
    public static boolean isNonnegativeInteger(String str) {
        return validate(str, "^\\d+$");
    }

    /**
     * Verify that it is a valid uuid,
     * If you remove the "-" you must also have a 32-bit length.
     * @param str String
     * @return boolean
     */
    public static boolean isUuid(String str) {
        return validate(str, UUID_PATTERN) || validate(str, "[0-9a-z]{32}");
    }

    /**
     * Verify that it is not a valid uuid
     *
     * @param str uuid
     * @return boolean
     */
    public static boolean isNotUuid(String str) {
        return !isUuid(str);
    }

    /**
     * Verification date format matches yyyy-MM-dd
     *
     * @param date String date
     * @return boolean
     */
    public static boolean isDate(String date) {
        return validate(date, DATE_PATTERN);
    }

    /**
     * Verification date format not matches yyyy-MM-dd
     *
     * @param date String date
     * @return boolean
     */
    public static boolean isNotDate(String date) {
        return !validate(date, DATE_PATTERN);
    }

    /**
     * Verification time format matches yyyy-MM-dd HH:mm:ss.
     * @param date String date
     * @return boolean
     */
    public static boolean isTimestamp(String date) {
        return validate(date, TIME_STAMP_PATTERN);
    }

    /**
     * Verification time format not matches yyyy-MM-dd HH:mm:ss.
     *
     * @param date String date
     * @return boolean
     */
    public static boolean isNotTimestamp(String date) {
        return !validate(date, TIME_STAMP_PATTERN);
    }

    /**
     * Verify ip address is legal, including ipv4 and ipv6
     *
     * @param ip ip address
     * @return boolean
     */
    public static boolean isIP(String ip) {
        if (StringUtil.isEmpty(ip)) {
            return false;
        } else {
            ip = ip.toLowerCase();
            return validate(ip, IPV4_PATTERN) || validate(ip, IPV6_PATTERN);
        }
    }

    /**
     * Verify ip address is invalid, including ipv4 and ipv6
     *
     * @param ip ip address
     * @return boolean
     */
    public static boolean isNotIP(String ip) {
        return !ValidateUtil.isIP(ip);
    }

    /**
     * Verify that it is a valid url address and does not support Chinese.
     * Usage:
     * https://www.baidu.com:8080/a/n?a=5，
     * https://192.168.15.96:8080/a/n?a=5
     *
     * @param url http,https,ftp url
     * @return boolean
     */
    public static boolean isUrl(String url) {
        return validate(url, URL_PATTERN);
    }

    /**
     * Verify that it is not a valid url address
     *
     * @param url url
     * @return boolean
     */
    public static boolean isNotUrl(String url) {
        return !validate(url, URL_PATTERN);
    }

    /**
     * Verify that the domain name is valid.
     * Can only be a standard domain name like www.baidu.com.
     *
     * @param domain domain
     * @return boolean
     */
    public static boolean isDomain(String domain) {
        return validate(domain, DOMAIN_PATTERN);
    }

    /**
     * Verify that the domain name is not valid
     *
     * @param domain domain
     * @return boolean
     */
    public static boolean isNotDomain(String domain) {
        return !validate(domain, DOMAIN_PATTERN);
    }

    /**
     * 只能是整数或者浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isIntOrFloat(String number) {
        return validate(number, INT_OR_FLOAT_PATTERN);
    }

    /**
     * 判断不是整数或者浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isNotIntOrFloat(String number) {
        return !validate(number, INT_OR_FLOAT_PATTERN);
    }

    /**
     * 判断是否是浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isFloat(String number) {
        return validate(number, FLOAT_PATTERN);
    }

    /**
     * 判断是费浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isNotFloat(String number) {
        return !validate(number, FLOAT_PATTERN);
    }

    /**
     * 判断是否是负浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isNegativeFloat(String number) {
        return validate(number, "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$");
    }

    /**
     * 判断是否不是负浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isNotNegativeFloat(String number) {
        return !isNegativeFloat(number);
    }

    /**
     * 判断是否是正浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isPositiveFloat(String number) {
        return validate(number, "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$");
    }

    /**
     * 判断是否不是正浮点数
     *
     * @param number String number
     * @return boolean
     */
    public static boolean isNotPositiveFloat(String number) {
        return !isPositiveFloat(number);
    }
    
    /**
     * 
     * isPositiveInteger:(判断是否正整数)
     * @author yolanda0608
     * @param number String number
     * @return boolean
     */
    public static boolean isPositiveInteger(String number){
    	return validate(number,POSITIVE_INTEGER);
    }


    /**
     * Verify whether it is Army Identity Card
     * @param armyIdCard Army Identity Card
     * @return boolean
     */
    public static boolean isArmyIdCard(String armyIdCard){
    	return validate(armyIdCard,ARMY_ID_CARD);
    }

    /**
     * Verify whether it is not Army Identity Card
     * @param armyIdCard Army Identity Card
     * @return boolean
     */
    public static boolean isNotArmyIdCard(String armyIdCard){
        return !validate(armyIdCard,ARMY_ID_CARD);
    }

    /**
     * is Contains Forbidden Character
     * @param number String number
     * @return boolean
     */
    public static boolean isContainsForbiddenCharacter(String number) {
        if (StringUtil.isEmpty(number)) {
            return false;
        } else {
            for (String reg : forbidden) {
                number = number.toLowerCase();
                String me = number.replaceAll(reg, "invalid character");
                if (me.length() != number.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * valid year number between 1900 and 2099
     * @param yearNumber
     * @return boolean
     */
    public static boolean isYear(String yearNumber){
        return validate(yearNumber,YEAR_PATTERN);
    }

    /**
     * valid year number between 1900 and 2099
     * @param yearNumber
     * @return boolean
     */
    public static boolean isNotYear(String yearNumber){
        return !isYear(yearNumber);
    }


}
