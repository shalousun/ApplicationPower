# ValidateUtil方法说明
ValidateUtil里面主要是封装了一些常用字段验证方法，例如：邮箱、手机号等，ValidateUtil所属的包为com.power.common.util。

## 1. isEmail(String str)方法
该方法用于验证输入的字符是否是邮箱。

Usage:

```
ValidateUtil.isEmail("123456@qq.com");//return true
ValidateUtil.isEmail("123456");//return false
```
## 2. isNotEmail(String str)方法
该方法用于验证字符串为非邮箱号，和isEmail方法的验证结果刚好相反。

Usage:

```
ValidateUtil.isNotEmail("123456@qq.com");//return false
ValidateUtil.isNotEmail("123456");//return true
```
## 3. isChines(String str)方法
该方法用于验证字符串是否是中文

Usage:

```
ValidateUtil.isChines("hello");//return false
```
## 4. isNotChinese(String str)
该方法用于验证字符串是否不是中文

Usage:

```
ValidateUtil.isNotChinese("hello");//return true
```
## 5. isNumberLetter(String str)方法
验证字符串是否是字母或者数字或者数字+字母

Usage:

```
ValidateUtil.isNumberLetter("ab123");//return true
ValidateUtil.isNumberLetter("123");//return true
ValidateUtil.isNumberLetter("aa");//return true
```
## 6. isNumber(String str)方法
验证字符串是否是数字

Usage:

```
ValidateUtil.isNumber("123");//return true
ValidateUtil.isNumber("123a");//return false
```
## 7. isNotNumber(String str)方法
验证字符串是否不是数字

Usage:

```
ValidateUtil.isNotNumber("123");//return false
ValidateUtile.isNotNumber("123a");//return true
```
## 8. isLetter(String str)方法
验证字符串是否是字母

Usage:
```
ValidateUtil.isLetter("ab");//return true
ValidateUtil.isLetter("123");//return false
```
## 9. isZipCode(String str)方法

验证字符串是否是中国的合法邮编

Usage:

```
ValidateUtil.isZipCode("430056");//return true
ValidateUtil.isZipCode("12345678");//return false
```
## 10. isPhone(String str)方法
验证字符串是否是中国的手机号。涵盖13、14、15、16、17、18、19开头的手机号


Usage:

```
ValidateUtil.isPhone("13510931498");//return true
ValidateUtil.isPhone("11510931498");//return false
```
## 11. isNotPhone(String str)方法

该方法用于验证字符串为非手机号,返回的结果isPhone相反

Usage:

```
ValidateUtil.isNotPhone("13510931498");//return false
ValidateUtil.isNotPhone("11510931498");//return true
```
## 12. isTelephone(String str)

该方法用于验证字符串是否为固定电话,包括普通固话和400固话。

Usage:

```
ValidateUtil.isTelephone("010-86551122");//return true
ValidateUtil.isTelephone("400-8075-592");//return true
ValidateUtil.isTelephone("4008075592");//return true
ValidateUtil.isTelephone("5008075592");//return false
```
## 13. isNotTelephone(String str)

该方法用于验证字符串是否不是固定电话。

Usage:

```
ValidateUtil.isNotTelephone("010-86551122");//return false
ValidateUtil.isNotTelephone("400-8075-592");//return false
ValidateUtil.isNotTelephone("4008075592");//return false
ValidateUtil.isNotTelephone("5008075592");//return true
```

## 14. isIdCard(String str)方法
该方法用于验证中国的居民身份证号,属于严格验证

Usage:

```
ValidateUtil.isIdCard("411722197508214014");//return true
ValidateUtil.isIdCard("111111111111111111");//return false

```
## 15. isNotIdCard(String str)方法
该方法用于验证输入的字符串是否不是身份证，不是则返回true,是则返回false

Usage:

```
ValidateUtil.isNotIdCard("411722197508214014");//return false
ValidateUtil.isNotIdCard("111111111111111111");//return true

```
## 16. isNonnegativeInteger(String str)方法
该方法用于验证属于字符串不能是负整数,一般验证需要用户输入整数的地方。

Usage:

```
ValidateUtil.isNonnegativeInteger("-1");//return false
ValidateUtil.isNonnegativeInteger("1");//return true
```
## 17. isDate(String date)方法
该方法用于验证输入的字符串时间格式是否正确，验证的日期格式必须是yyyy-MM-dd，该方法可做到对闰年平年的2月精准验证

Usage:

```
ValidateUtil.isDate("2017-01-03");//return true
ValidateUtil.isDate("2017-01-32");//return false
```

## 18. isNotDate(String date)方法
该方法用于验证输入的字符串是否不是时间格式，和isDate方法的检验结果相反。

Usage:

```
ValidateUtil.isNotDate("2017-01-03");//return false
ValidateUtil.isNotDate("2017-01-32");//return true
```


## 19. isTimestamp(String date)方法

该方法用于验证输入的字符串时间戳格式是否正确，验证的日期格式必须是yyyy-MM-dd HH:mm:ss，该方法可做到对闰年平年的2月精准验证

Usage:
```
ValidateUtil.isTimestamp("2017-02-29 23:59:59");//return false
```

## 20. isNotTimestamp(String date)方法

该方法用于验证输入的字符串时间戳格式是否不是时间戳，检测结果和isTimestamp相反。

Usage:
```
ValidateUtil.isNotTimestamp("2017-02-29 23:59:59");//return false
```


## 21. isUuid(String str)方法
该方法用于检测传入的参数是否是uuid,正常情况是数据字母和"-"组成36为长度字符串,如果去掉"-"也必须保持有32位长度

Usage:
```
ValidateUtil.isUuid("de07884ca92c4e2696eab18313a46d99");//return true
ValidateUtil.isUuid("1241285e-f10d-486e-bd7c-a86e032828e1");//return true
ValidateUtil.isUuid("1241285e-f10d-486e-bd7c-a86e032828e1dd");//return false
```
## 22. isNotUuid(String str)方法
该方法用于检测传入的参数是否是uuid,和isUuid检验结果相反

Usage:
```
ValidateUtil.isNotUuid("de07884ca92c4e2696eab18313a46d99");//return false
ValidateUtil.isNotUuid("1241285e-f10d-486e-bd7c-a86e032828e1");//return false
ValidateUtil.isNotUuid("1241285e-f10d-486e-bd7c-a86e032828e1dd");//return true
```

## 23. isIP(String ip)方法
该方法用于检测ip地址，包括ipv4和ipv6

Usage:
```
ValidateUtil.isIP("192.168.15.96");//return true
//ipv6可小写
ValidateUtil.isIP("ABCD:EF01:2345:6789:ABCD:EF01:2345:6789");//return true
```
## 24. isNotIP(String ip)方法
该方法用于检验字符串是否不是ip,检验结果和isIp方法相反。

Usage:
```
ValidateUtil.isNotIP("aa");//return true
//ipv6可小写
ValidateUtil.isNotIP("hhkkejh");//return true
```
## 25. isDomain(String domain)方法
该方法用于验证域名是否合法

Usage:
```
ValidateUtil.isDomain("www.yddkt.com");//return true
```
## 26. isNotDomain(String domain)方法
该方法用于验证输入的自否是否不是域名。

Usage:
```
ValidateUtil.isNotDomain("www.yddkt.com");//return false
```
## 27. isUrl(String url)方法
该方法用于验证url是否合法，目前无法识别中文。

Usage:

```
ValidateUtil.isUrl("https://www.baidu.com:8080/a/n?a=5");//return true
ValidateUtil.isUrl("https://192.168.15.96/a/n?a=5");//return true
```
## 28. isNotUrl(String url)方法
该方法用于验证输入的字符串是否不是Url地址，和isUrl方法检验结果相反。

Usage:

```
ValidateUtil.isNotUrl("https://www.baidu.com:8080/a/n?a=5");//return false
ValidateUtil.isNotUrl("https://192.168.15.96/a/n?a=5");//return false
```
## 29. isIntOrFloat(String number)方法
该方法用于验证字符串是否是整数或者是浮点数。

Usage:
```
ValidateUtil.isIntOrFloat("1.a");//false
ValidateUtil.isIntOrFloat("1.1");//true
ValidateUtil.isIntorFloat("1");//true

```
## 30. isNotIntOrFloat(String number)方法
该方法用于验证字符串是否不是整数或者不是浮点数。验证结果和isIntOrFloat相反。

Usage:
```
ValidateUtil.isNotIntOrFloat("1.a");//true
ValidateUtil.isNotIntOrFloat("1.1");//false
ValidateUtil.isNotIntorFloat("1");//false

```

## 31. isFloat(String number)方法
该方法用于判断输入的字符串是否是浮点数。

Usage:
```
ValidateUtil.isFloat("1.a");//false
ValidateUtil.isFloat("1.1");//true

```
## 32. isNotFloat(String number)方法
该方法用于判断输入的字符串是否不是浮点数。

Usage:
```
ValidateUtil.isNotFloat("1.a");//true
ValidateUtil.isNotFloat("1.1");//false

```
## 33. isNegativeFloat(String number)方法
该方法用于验证字符串是否是负浮点数。

Usage:

```
ValidateUtil.isNegativeFloat("1.0");//return false
ValidateUtil.isNegativeFloat("-1.1");//return true
```
## 34. isNotNegativeFloat(String number)方法
该方法用于验证字符串是否不是负浮点数。

Usage:

```
ValidateUtil.isNotNegativeFloat("1.0");//return true
ValidateUtil.isNotNegativeFloat("-1.1");//return false
```
## 35. isPositiveFloat(String number)方法
该方法用于验证字符串是否是正浮点数。

Usage:

```
ValidateUtil.isPositiveFloat("1.0");//return false
ValidateUtil.isPositiveFloat("-1.0");//return false
```
## 36. isPositiveInteger(String number)方法
该方法用于判断输入的字符是否是正整数。

Usage:
```
ValidateUtil.isPositiveInteger("1");//return true
ValidateUtil.isPositiveInteger("-1");//return false
```
## 37. isContainsForbiddenCharacter(String str)方法
该方法用于判断输入的字符是否包含非法的字符，通常用于判断xss和sql注入字符。

Usage:
```
ValidateUtil.isContainsForbiddenCharacter("location.href");//return true
ValidateUtil.isContainsForbiddenCharacter("delete a");//return true
```
## 38. isArmyIdCard(String armyIdCard)方法
@since 0.3

该方法用于判断中国的军官证

Usage:
```
ValidateUtil.isArmyIdCard("北字第7682126号");//return true
ValidateUtil.isArmyIdCard("美字第7682126号");//return false
```
## 39. isNotArmyIdCard(String armyIdCard)方法
@since 0.3

该方法用于判断非中国的军官证

Usage:
```
ValidateUtil.isNotArmyIdCard("北字第7682126号");//return false
ValidateUtil.isNotArmyIdCard("美字第7682126号");//return true
```
## 40. isTime(String time)方法
@since 1.4

该方法用于判断时间格式是否正确

Usage:
```
ValidateUtil.isTime("12:56:60");//return false
ValidateUtil.isTime("12:56:60");//return true
```
## 41. isNotTime(String time)方法

该方法判断逻辑和isTime方法相反

Usage:
```
ValidateUtil.isNotTime("12:56:60");//return true
ValidateUtil.isNotTime("12:56:60");//return false
```

# validate内置的常用的正则表达式
```
//邮箱
public static final String EMAIL_PATTERN = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
//中文
public static final String CHINESE_PATTERN = "^[\u4e00-\u9fa5]{0,}$";
//数字和字母
public static final String NUMBER_ADN_LETTER = "^[A-Za-z0-9]+$";
//qq
public static final String QQ_PATTERN = "/[1-9][0-9]{4,}/";
//数字
public static final String NUMBER_PATTERN = "[0-9]+";
//字母
public static final String LETTER_PATTERN = "[a-zA-Z]+";
//邮编
public static final String ZIPCODE_PATTERN = "\\p{Digit}{6}";
//手机
public static final String PHONE_PATTERN = "^(13[0-9]|14[579]|15[^4,\\D]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
//固定电话
public static final String TELEPHONE_PATTERN = "^(0\\d{2,3}-)?(\\d{7,8})(-(\\d{3,}))?$";
//400固话
public static final String TELEPHONE_400_PATTERN = "((400)(\\d{7}))|((400)-(\\d{3})-(\\d{4}))";
//中国身份证
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
//uuid
public static final String UUID_PATTERN = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";
//日期类型
public static final String DATE_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
//时间戳
public static final String TIME_STAMP_PATTERN = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$";
//ip v4
public static final String IPV4_PATTERN = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
//ip v6
public static final String IPV6_PATTERN = "([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)\\:([0-9a-f]+)";
//url
public static final String URL_PATTERN = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$#\\=~_\\-@]*)*$";
//域名
public static final String DOMAIN_PATTERN = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
//整数或者浮点数
public static final String INT_OR_FLOAT_PATTERN = "^\\d+\\.\\d+|\\d+$";
//浮点数
public static final String FLOAT_PATTERN = "^(-?\\d+)(\\.\\d+)?$";
//正整数
public static final String POSITIVE_INTEGER = "[1-9]+\\d{0,10}";
//军官证@since 0.3
public static final String ARMY_ID_CARD = "南字第(\\d{6,8})号|北字第(\\d{6,8})号|沈字第(\\d{6,8})号|兰字第(\\d{6,8})号|成字第(\\d{6,8})号|济字第(\\d{6,8})号|广字第(\\d{6,8})号|海字第(\\d{6,8})号|空字第(\\d{6,8})号|参字第(\\d{6,8})号|政字第(\\d{6,8})号|后字第(\\d{6,8})号|装字第(\\d{6,8})号";

```