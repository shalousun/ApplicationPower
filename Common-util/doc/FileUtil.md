# FileUtil方法介绍
FileUtil是一些常见的文件操作统一封装。所属的包为com.power.common.util。

**注意：** 文档中@since xx表示拥有方法的起始版本

## 1. writeFile(String source, String filePath, boolean append)方法
该方法用于将字符串内容写到本地文件中，写文件时可选择是否追加，默认编码为utf-8

参数 | 描述
---|---
 source| 字符串内容
 filePath | 保存的文件路径
 append | 是否使用文件追加，true表示追加

Usage:

```
FileUtil.writeFile("hello", "/usr/local/hello.txt",true);//return true or false
```

## 2. writeFileNotAppend(String source, String filePath)方法
该方法用于字符串内容写到本地文件系统中，每次写入都会覆盖文件中旧的内容，默认编码为utf-8

参数 | 描述
---|---
 source| 字符串内容
 filePath | 保存的文件路径
 
Usage:

```
FileUtil.writeFileNotAppend("hello", "/usr/local/hello.txt");//return true or false
```
## 3. writeFile(String source, File file,boolean append)方法
该方法用于字符串内容写到本地文件系统中，写文件时可选择是否追加


参数 | 描述
---|---
 source| 字符串内容
 file | 保存内容的文件
 append | 是否使用文件追加，true表示追加

Usage:

```
FileUtil.writeFile("hello", new File("/usr/local/hello.txt"),true);//return true or false
```
## 4. getFileContent(String fileName)方法
该方法用于获取文件内容并将内容做为字符串返回

参数 | 描述
---|---
fileName| 文件名称


Usage:

```
FileUtil.getFileContent("/usr/local/hello.txt");//return "hello"
```
## 5. getFileContent(InputStream is)方法

该方法用于从输入流中获取字符串内容

参数 | 描述
---|---
is| 输入流

## 6. nioWriteFile(String contents,String filePath)方法

该方法采用nio的方式将字符串内容写入到文件系统中，写入时旧的内容会被新的覆盖，默认编码为utf-8

参数 | 描述
---|---
 source| 字符串内容
 filePath | 保存的文件路径
 
Usage:

```
FileUtil.nioWriteFile("hello","/usr/local/hello.txt");//return true or false
```
## 7. nioWriteAppendable(String contents,String filePath,)方法
该方法采用nio的方式将字符串内容写入到文件系统中，新的内容会被追加到旧的内容后面，默认编码为utf-8

参数 | 描述
---|---
 source| 字符串内容
 filePath | 保存的文件路径
 
Usage:

```
FileUtil.nioWriteAppendable("hello","/usr/local/hello.txt");//return true or false
```

## 8. nioTransferCopy(File source, File target)方法
该方法采用nio的高效方式来实现文件复制。

参数 | 描述
---|---
 source| 复制的源文件
target| 目标文件
 
Usage:

```
FileUtil.nioTransferCopy("/usr/local/hello.txt","/usr/local/hello1.txt");
```
## 9. toSuffix(String fileName)方法
@since 0.2

该方法用于获取文件名不带后缀的文件名。

参数 | 描述
---|---
fileName| 文件名称


Usage:

```
FileUtil.toSuffix("me.java");//return me
```