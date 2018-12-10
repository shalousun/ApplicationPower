# AESUtil方法介绍
AESUtil是对常用的AES加解密的封装。所属的包为com.power.common.util。目前该工具类封装了
两种加解密的模式，分别是AES的ECB和CBC模式，这两种模式基本可以满足平常的开发要求。1.5开始common-util中的
AESUtil增加了对AES/CBC/PKCS7Padding的支持，常用的填充常量在AesPaddings中，对于不需要传递填充模式的方法
默认都是用CBC或者ECB的PKCS5Padding。

**注意：** 平时开发和别人协商一些接口签名验证时需要协商好双方所采用的AES加解密模式，是ECB还是CBC。
AES加密模式中除了ECB模式外，都需要提供初始化加密向量，在使用该工具是也需要注意加解密的模式必须是相同的。



## 1. encodeByCBC(String content, String key, String initVector)方法
该方法表示使用CBC模式来加密字符串

参数 | 描述
---|---
content| 待加密字符串
key|解密的密匙
initVector|初始向量
 
Usage:

```
 private static final String KEY = "aespower98765432";

/**
 * 非ECB加密模式的向量
 */
 private static final String IV = "7201084316056726";
 AESUtil.encodeByCBC("hello world",KEY,IV);//renturn "4ddee0776425fb5a3893a8fe1a9caa0b"

```
## 2. decodeByCBC(String content, String key, String initVector)方法
该方法表示使用CBC模式来解密已加密的字符串

参数 | 描述
---|---
content| 待解密字符串
key|解密的密匙
initVector|初始向量
 
Usage:

```
 private static final String KEY = "aespower98765432";

/**
 * 非ECB加密模式的向量
 */
 private static final String IV = "7201084316056726";
 
 String encodeStr = "4ddee0776425fb5a3893a8fe1a9caa0b";
 AESUtil.decodeByCBC(encodeStr,KEY,IV);// return "hello world"

```
## 3. encodeByECB(String content, String key)方法
该方法表示使用EBC模式来加密字符串

参数 | 描述
---|---
content| 待加密字符串
key|解密的密匙
 
Usage:

```
 private static final String KEY = "aespower98765432";

 AESUtil.encodeByECB("hello world",KEY);//renturn "4uw/w/ozV7rZWZvcz57ExA=="

```
## 4. decodeByEBC(String content, String key)方法
该方法表示使用ECB模式来解密已加密的字符串

参数 | 描述
---|---
content| 待解密字符串
key|解密的密匙
 
Usage:

```
 private static final String KEY = "aespower98765432";

 String encodeStr = "4uw/w/ozV7rZWZvcz57ExA==";
 AESUtil.decodeByECB(encodeStr,KEY);// return "hello world"

```
## 5. decryptByECB(byte[] content, byte[] key)方法
该方法使用ECB模式来解密，解密后返回二进制数组，本方法主要用于解密第三方加密的秘串，
前面直接解密加密字符串，在解密其他人给的加密串是并不有效，因为加密实现不对等。
但是该方法没有做任何多余实现，更够解密其他aes加密的二进制密文。



参数 | 描述
---|---
content| 待解密字符串
key|解密的密匙
 
Usage:

```
 byte[] key = Base64.decodeBase64("T2+PfV1qoSpkSUI6Yu1ZsH==");
 
 byte[] content = Base64.decodeBase64("VMAgOo407rSCigdy6gRPkfpAMpQKyOUuJPrRBTHj1sIsRGGleTnXQqKNseOeZtDJy0E6mKxvsrrslcN5EeXiTW4oCPGjJzq93DOLFbUfRZU=");

 byte[] result = AESUtil.decryptByECB(content,key);

 System.out.println(new String(result));
 
 //仅供参考，测试时请自行修改数据

```
## 6. encryptByECB(byte[] content, byte[] key)方法
该方法表示使用ECB模式来加密字符串

参数 | 描述
---|---
content| 待加密字符串
key|解密的密匙
 
Usage:

```
byte[] key = Base64.decodeBase64("T2+PfV1qoSpkSUI6Yu1ZsQ==");

byte[] content = Base64.encodeBase64("hello".getBytes());

//加密
byte[] encodeResult = AESUtil.encryptByECB(content,key);

//解密
byte[] result = AESUtil.decryptByECB(encodeResult,key);

System.out.println(new String(Base64.decodeBase64(result)));//hello
```

# AESUtil的使用建议

AESUtil是针对开发中常用的AES加解密的基础封装，在项目一般的加密的key或者初始化向量在确定下来后，
很少多修改，因此在项目中如果不想在调用的地方频繁的传key，可以在项目中针对AESUtil提供的方法在做封装。