# ValidateUtil方法说明
ValidateUtil里面主要是封装了一些常用字段验证方法，例如：邮箱、手机号等，ValidateUtil所属的包为com.boco.common.util。

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
ValidateUtil.isEmail("123456@qq.com");//return false
ValidateUtil.isEmail("123456");//return true
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
ValidateUtil.isChines("hello");//return true
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
ValidateUtile.isNumber("123a");//return false
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
验证字符串是否是中国的手机号。涵盖13、14、15、17、18开头的手机号


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
## 35. isPositiveFloat(String number)
该方法用于验证字符串是否是正浮点数。

Usage:

```
ValidateUtil.isPositiveFloat("1.0");//return false
ValidateUtil.isPositiveFloat("-1.0");//return false
```
## 36. isPositiveInteger(String number)
该方法用于判断输入的字符是否是正整数。

```
ValidateUtil.isPositiveInteger("1");//return true
ValidateUtil.isPositiveInteger("-1");//return false
```
## 37. isContainsForbiddenCharacter(String str)
该方法用于判断输入的字符是否包含非法的字符，通常用于判断xss和sql注入字符。
```
ValidateUtil.isContainsForbiddenCharacter("location.href");//return true
ValidateUtil.isContainsForbiddenCharacter("delete a");//return true
```

