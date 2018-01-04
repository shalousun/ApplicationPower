package com.boco.common.util;

/**
 * 数据校验常用正则封装
 *
 * @author sunyu
 */
public class ValidateUtil {

    private static final String EMAIL_PATTERN = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
    private static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{0,}$";
    private static final String NUMBER_ADN_LETTER = "^[A-Za-z0-9]+$";
    private static final String QQ_PATTERN = "/[1-9][0-9]{4,}/";
    private static final String NUMBER_PATTERN = "[0-9]+";
    private static final String LETTER_PATTERN = "[a-zA-Z]+";
    private static final String ZIPCODE_PATTERN = "\\p{Digit}{6}";
    private static final String PHONE_PATTERN = "^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    private static final String TELEPHONE_PATTERN = "^(0\\d{2,3}-)?(\\d{7,8})(-(\\d{3,}))?$";
    private static final String TELEPHONE_400_PATTERN = "((400)(\\d{7}))|((400)-(\\d{3})-(\\d{4}))";
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
    private static final String UUID_PATTERN = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";
    private static final String DATE_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    private static final String TIME_STAMP_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$";
    private static final String IPV4_PATTERN = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    private static final String IPV6_PATTERN = "([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)";
    private static final String URL_PATTERN = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$#\\=~_\\-@]*)*$";
    private static final String DOMAIN_PATTERN = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
    private static final String INT_OR_FLOAT_PATTERN = "^\\d+\\.\\d+|\\d+$";
    private static final String FLOAT_PATTERN = "^(-?\\d+)(\\.\\d+)?$";
    private static final String POSITIVE_INTEGER = "[1-9]+\\d{0,10}";
    private static final String CERTIFICATE_CARD = "南字第(\\d{8})号|北字第(\\d{8})号|沈字第(\\d{8})号|兰字第(\\d{8})号|成字第(\\d{8})号|济字第(\\d{8})号|广字第(\\d{8})号|海字第(\\d{8})号|空字第(\\d{8})号|参字第(\\d{8})号|政字第(\\d{8})号|后字第(\\d{8})号|装字第(\\d{8})号";


    public static final String[] forbidden = {"\\bselect\\b", "\\bor\\b", "\\bdelete\\b", "\\bjoin\\b","\\btable\\b"
            , "\\bdrop\\b", "\\biframe\\b","\\bwindow\\b", "\\b_\\b", "\\+", "%", "\\<", "\\>", "'","=",
            "%3C", "\\(", "%28", "alert", "eval((.*))", "script","location.href"};

    /**
     * 根据正则表达式匹配字符串
     *
     * @param str     字符串
     * @param pattern 正则表达式
     * @return boolean
     */
    public static boolean validate(String str, String pattern) {
        return StringUtil.isNotEmpty(str) && str.matches(pattern);
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
     * Verify whether is not Chinese
     * @param chineseStr
     * @return
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
     * @param str
     * @return
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
     * 和isNumber方法返回值相反
     *
     * @param str
     * @return
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
     * @param phoneNumber
     * @return
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
     * @param cardNumber
     * @return
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
     * @param str password
     * @return boolean
     */
    public static boolean isPassword(String str) {
        return validate(str, PASSWORD_PATTERN);
    }

    /**
     * 非负整数
     *
     * @param str
     * @return
     */
    public static boolean isNonnegativeInteger(String str) {
        return validate(str, "^\\d+$");
    }

    /**
     * 验证是否是合法的uuid,正常情况是数据字母和"-"组成36为长度字符串
     * 如果去掉"-"也必须保持有32位长度
     *
     * @param str
     * @return
     */
    public static boolean isUuid(String str) {
        return validate(str, UUID_PATTERN) || validate(str, "[0-9a-z]{32}");
    }

    /**
     * 验证不是uuid
     *
     * @param str
     * @return
     */
    public static boolean isNotUuid(String str) {
        return !isUuid(str);
    }

    /**
     * 验证日期格式是否正确必须是yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        return validate(date, DATE_PATTERN);
    }

    /**
     * 和isDate方法相反
     *
     * @param date
     * @return
     */
    public static boolean isNotDate(String date) {
        return !validate(date, DATE_PATTERN);
    }

    /**
     * 验证时间格式是否是yyyy-MM-dd HH:mm:ss
     * 可以做到精准验证
     *
     * @param date
     * @return
     */
    public static boolean isTimestamp(String date) {
        return validate(date, TIME_STAMP_PATTERN);
    }

    /**
     * 和isTimestamp方法相反
     *
     * @param date
     * @return
     */
    public static boolean isNotTimestamp(String date) {
        return !validate(date, TIME_STAMP_PATTERN);
    }

    /**
     * 检测ip地址包括ipv4和ipv6
     *
     * @param ip
     * @return
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
     * 和isIP方法返回值相反
     *
     * @param ip
     * @return
     */
    public static boolean isNotIP(String ip) {
        return !ValidateUtil.isIP(ip);
    }

    /**
     * 验证是否是合法的url地址，不支持中文，验证包含：https://www.baidu.com:8080/a/n?a=5，
     * https://192.168.15.96:8080/a/n?a=5
     *
     * @param url http,https,ftp url
     * @return
     */
    public static boolean isUrl(String url) {
        return validate(url, URL_PATTERN);
    }

    /**
     * 和isUrl方法相反
     *
     * @param url
     * @return
     */
    public static boolean isNotUrl(String url) {
        return !validate(url, URL_PATTERN);
    }

    /**
     * 验证域名是否合法：只能是www.baidu.com这类标准域名
     *
     * @param domain
     * @return
     */
    public static boolean isDomain(String domain) {
        return validate(domain, DOMAIN_PATTERN);
    }

    /**
     * 和isDomain相反
     *
     * @param domain
     * @return
     */
    public static boolean isNotDomain(String domain) {
        return !validate(domain, DOMAIN_PATTERN);
    }

    /**
     * 只能是整数或者浮点数
     *
     * @param number
     * @return
     */
    public static boolean isIntOrFloat(String number) {
        return validate(number, INT_OR_FLOAT_PATTERN);
    }

    /**
     * 判断不是整数或者浮点数
     *
     * @param number
     * @return
     */
    public static boolean isNotIntOrFloat(String number) {
        return !validate(number, INT_OR_FLOAT_PATTERN);
    }

    /**
     * 判断是否是浮点数
     *
     * @param number
     * @return
     */
    public static boolean isFloat(String number) {
        return validate(number, FLOAT_PATTERN);
    }

    /**
     * 判断是费浮点数
     *
     * @param number
     * @return
     */
    public static boolean isNotFloat(String number) {
        return !validate(number, FLOAT_PATTERN);
    }

    /**
     * 判断是否是负浮点数
     *
     * @param number
     * @return
     */
    public static boolean isNegativeFloat(String number) {
        return validate(number, "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$");
    }

    /**
     * 判断是否不是负浮点数
     *
     * @param number
     * @return
     */
    public static boolean isNotNegativeFloat(String number) {
        return !isNegativeFloat(number);
    }

    /**
     * 判断是否是正浮点数
     *
     * @param number
     * @return
     */
    public static boolean isPositiveFloat(String number) {
        return validate(number, "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$");
    }

    /**
     * 判断是否不是正浮点数
     *
     * @param number
     * @return
     */
    public static boolean isNotPositiveFloat(String number) {
        return !isPositiveFloat(number);
    }
    
    /**
     * 
     * isPositiveInteger:(判断是否正整数)
     * @author yolanda0608
     * @param number
     * @return
     */
    public static boolean isPositiveInteger(String number){
    	return validate(number,POSITIVE_INTEGER);
    }
    
    /**
     * 
     * issCertificate:(判断是否军官证，是返回true)
     * @author yolanda0608
     * @param number
     * @return
     */
    public static boolean isCertificate(String number){
    	return validate(number,CERTIFICATE_CARD);
    }

    /**
     * @param number
     * @return
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
}
