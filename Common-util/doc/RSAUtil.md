# RSAUtil方法介绍
RSAUtil是对常用的RSA加解密的封装。所属的包为com.power.common.util. RSAUtil除了提供生成KeyPair和常用加解密方法外，
还提供了将KeyPair保存到秘钥文件和从秘钥文件生成KeyPair的方法，因此可以看出RSAUtil是提供了一套通用方法，
方便使用者在项目中根据实际秘钥保存方式，在RSAUtil的基础上进行简单的二次封装。

**注意：** 该工具产生的字符串秘钥都是经过base64编码的。


## 1. generateKeyPair(int keySize)方法
该方法用于产生RSA的密钥对，一般在初始生秘钥的时候使用，该方法自动打印公钥的modulus和exponent

参数 | 描述
---|---
keySize| 生成key的长度，长度越长越消耗性能

 
Usage:

```
RSAUtil.generateKeyPair(1024);
```
## 2. createKeys(int keySize)方法
该方法用于生成字符串类型的公钥和私钥然后分别存放到map中，生成的公钥和私钥都经过base64编码

参数 | 描述
---|---
keySize| 生成key的长度，长度越长越消耗性能
 
Usage:

```
Map<String,String> keyMap = RSAUtil.createKeys(2048);
String privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
System.out.println("私钥："+privateKey);
String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
System.out.println("公钥："+publicKey);

```
## 3. encryptString(String plaintext, String publicKey)方法
该方法使用公钥来加密字符串

参数 | 描述
---|---
plaintext| 待加密的字符串
publicKey |字符串公钥
 
Usage:

```
 Map<String,String> keyMap = RSAUtil.createKeys(2048);
 String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
 System.out.println("公钥："+publicKey);
 String encodedData = RSAUtil.encryptString("hello", publicKey);
 System.out.println("密文：\n" + encodedData);

```
## 4. decryptString(String plaintext, String privateKey)方法
该方法用来使用私钥解密已加密的字符串

参数 | 描述
---|---
plaintext  |待解密的字符串
privateKey |字符串私钥
 
Usage:

```
 Map<String,String> keyMap = RSAUtil.createKeys(2048);
 String privateKey = keyMap.get(RSAUtil.PRIVATE_KEY);
 System.out.println("私钥："+privateKey);
 String publicKey = keyMap.get(RSAUtil.PUBLIC_KEY);
 System.out.println("公钥："+publicKey);
 String encodedData = RSAUtil.encryptString("hello", publicKey);
 System.out.println("密文：\n" + encodedData);
 String decodedData = RSAUtil.decryptString(encodedData, privateKey);
 System.out.println("解密后文字: \n" + decodedData);
```
## 5. saveKeyPair(KeyPair kp, String filePath)方法
该方法用于将密钥对存储到文件中

参数 | 描述
---|---
kp      | 秘钥对
filePath| 保存秘钥的文件路径
 
Usage:

```
KeyPair kp = RSAUtil.generateKeyPair(1024);
RSAUtil.saveKeyPair(kp,"e:\\RSAKey.txt")
```
## 6. getKeyPair(String filePath)方法
该方法用于从文件中获取秘钥对

参数 | 描述
---|---
filePath|秘钥的文件路径
 
Usage:

```
KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt")
```

## 7. getPublicKey(KeyPair keyPair)方法
该方法用于从秘钥对中获取base64转码后的公钥

参数 | 描述
---|---
keyPair|秘钥对
 
Usage:

```
KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt");
String publicKey = RSAUtil.getPublicKey(kp);//return base64转码后的字符串公钥
```
## 8. getPrivateKey(KeyPair keyPair)方法
该方法用于从秘钥对中获取base64转码后的私钥

参数 | 描述
---|---
keyPair|秘钥对
 
Usage:

```
KeyPair kp = RSAUtil.getKeyPair("e:\\RSAKey.txt");
String privateKey = RSAUtil.getPrivateKey(kp);//return base64转码后的字符串私钥
```
