# MD6Util方法介绍
MD6Util是MD6验证的实现，MD6安全级别比MD5要高，但是目前使用较少。所属的包为com.power.common.util。


## 1. md6(String content)方法
该方法表示使用md6加密字符串

参数 | 描述
---|---
content| 待加密字符串
 
Usage:

```
String content = "hello";
String encodeStr = MD6Util.md6(content);
```

## 2. equal(String md6Str,String content)方法
该方法用来验证待加密的字符串加密后和加密串是否相同

参数 | 描述
---|---
md6Str |md6加密串
content |待加密串

Usage:

```
String content = "hello";
String encodeStr = MD6Util.md6(content);
boolean decodeStr = MD6Util.equal(encodeStr,content); //return true
```