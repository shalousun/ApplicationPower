# StringUtil方法介绍
StringUtil所属的包为com.power.common.util

## 1. isEmpty(String str)方法

该方法用于片段字符串是否为空

Usage:

```
StringUtil.isEmpty(null);//return true
StringUtil.isEmpty("");//return true
StringUtil.isEmpty("lisi");//return false
```
## 2. isNotEmpty(String str)方法

该方法用于判断字符串是否不为空，它和isEmpty方法的返回结果刚好相反。

Usage:

```
StringUtil.isNotEmpty(null);//return false
StringUtil.isNotEmpty("");//return false
StringUtil.isNotEmpty("lisi");//return true
```
## 3. filterStr(String str)方法

该方法用户过滤掉一些特殊字符，将特殊字符去除。

方法源码：

```
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
```


Usage:

```
StringUtil.filterStr("hello%");//return hello
```
## 4. camelToUnderline(String str)方法

该方法主要是将驼峰转化成下划线

Usage:

```
StringUtil.camelToUnderline("helloWorld");//return hello_world
```

## 5. underlineToCamel(String str)方法

该方法是用于将下划线转化成驼峰格式

Usage:

```
StringUtil.underlineToCamel("hello_world");//return helloWorld
```
## 6. isSameCharacter(String str)方法

该方法用于判断输入的字符串中的字符是否相同，此方法的使用场景一般为对密码的严格校验，禁止用户输入一个相同字符的密码

Code:

```
/**
 * 判断字符串中字符是否都相同
 * @param s
 * @return
 */
public static boolean isSameCharacter(String s) {
    s = s.toUpperCase();
    String character = s.substring(0, 1);
    String replace = "";
    String test = s.replace(character, replace);
    return "".equals(test);
}
```


Usage:


```
StringUtil.isSameCharacter("aa");//return true
StringUtil.isSameCharacter("aab");//return false
```

## 7. isContinuityCharacter(String str)方法
该方法用于判断字符中的字符是否是连续字符，此方法的使用场景一般用于对密码的严格校验，禁止用户输入连续的字符。

Code:

```
/**
 * 判断字符串中的字符是否为连续的数字字符
 * @param s
 * @return
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
```
Usage:

```
StringUtil.isContinuityCharacter("123456");//return true
StringUtil.isContinuityCharacter("12356");//return false
```
## 8. trim(String str)方法
去除字符串中所有空格

Code:

```
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
```
Usage:

```
StringUtil.trim("123 45");//return 12345
```
## 9. cleanXSS(String str)方法

清除xss跨站脚本注入,为了提高系统的安全性。前端提交的字符串后端尽量使用该方法转义特殊字符

Code:

```
 /**
 * 清除xss脚本注入
 *
 * @param value
 * @return
 */
public static String cleanXSS(String value) {
    if (null == value) {
        return value;
    } else {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
    }
    return value;
}
```
Usage:

```
StringUtil.cleanXSS("<script>");//return "&lt;script&gt;";
```
## 10. firstToUpperCase(String str)方法

该方法用于将首字母转大写

Code:

```
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
```
Usage:
```
StringUtil.firstToUpperCase("hello");//return Hello
```

## 11. firstToLowerCase(String str)方法
将字符串首字母小写


Code:

```
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
```
Usage:

```
StringUtil.firstToLowerCase("Hello");//return hello
```
## 12. urlDecode(String str)方法
该方法用于将前端编码后的字符解码，一般用于处理get请求时中文参数值乱码的情况。使用该方法时前端需要用encodeURIComponent()进行两次编码。encodeURIComponent(encodeURIComponent(“张三”))

Code:

```
/**
 * 对url传递的参数进行解码
 * @param str
 * @return
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
```

Usage:

```
StringUtil.urlDecode("coded character");
```

## 13. trimBlank()方法
该方法用于去除字符串的左右空格。

Code:
```
/**
 * left trim and right trim
 * @param str
 * @return
 */
public static String trimBlank(String str) {
    if (isEmpty(str)) {
        return null;
    } else {
        return str.replaceAll("^[　 ]+|[　 ]+$", "");
    }
}
```
Usage:

```
StringUtil.trimBlank(" hello world ");//return hello world
```
## 14. getChinese(String str)方法
该方法用于提取字符串中的中文


Code:

```
/**
 * 提取中文
 *
 * @param str
 * @return
 */
public static String getChinese(String str) {
    String reg = "[^\u4e00-\u9fa5]";
    str = str.replaceAll(reg, "");
    return str;
}
```
Usage:

```
StringUtil.getChinese("hello 中国");//return 中国
```
## 15. getNotChinese(String str)方法
该方法用于提取字符串中非中文字符

Code:

```
 /**
 * 提非中文
 *
 * @param str
 * @return
 */
public static String getNotChinese(String str) {
    String reg = "[^A-Za-z0-9_]";
    str = str.replaceAll(reg, "");
    return str;
}
```
Usage:

```
StringUtil.getNotChinese("hello 中国");//return hello
```

## 16. split(String str,String regex)方法
该方法用于根据指定的字符切割字符串到数组中，第一个参数是源字符串，第二个参数是切割使用的字符

```
String a = "a,b";
String[] strArray = StringUtil.split(a,",");

```
## 17. seqNumLeftPadZero(long seq,int len)方法
该方法一般用于创建一个流水号，位数不够时自动用0来不为
```
StringUtil.seqNumLeftPadZero(1,4);//return "0001"
```