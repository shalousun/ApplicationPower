common-util是一个和任何模块都无关的模块，common-util模块中主要包含一些功能的类或者是通用的工具，
任何模块都可以依赖他，然后使用其内部提供的一些工具类。common-util已经发布到公共的maven仓库中，maven依赖使用如下：

```
<dependency>
  <groupId>com.github.shalousun</groupId>
  <artifactId>common-util</artifactId>
  <version>1.4</version>
</dependency>
```
common-util下包含众多的工具类，例如：DateTimeUtil、StringUtil、ValidateUtil、IpUtil等。
除此之外common-util中已包含一些公用的filter,如跨站请求伪造的过滤器RefererFilter,防止Xss和sql注入的XssAndSqlFilter
相关工具类的方法文件在doc下。

当前包含工具类
```
1. DateTimeUtil
2. StringUtil
3. ValidateUtil
4. IpUtil
5. AESUtil  // @since 0.2
6. MD6Util  // @since 0.2
7. RandomUtil
8. FileUtil
9. RSAUtil //@since 0.3
10 OkHttp3Util //since 0.3
```
## 仓库版本发布日志
 
#### 版本号：0.1
- 更新日期：2018-06-04
- 更新内容：
	1. 首次发布common-util.
#### 版本号：0.2  
- 更新日期：2018-06-09 
- 更新内容：
	1. 添加AESUtil和MD6加密工具包括相应文档.
	2. 修改Xss安全拦击，以便在项目中能自定义处理错误信息.
#### 版本号：0.3  
- 更新日期：2018-06-26 
- 更新内容：
	1. 添加OkHttpUtil工具类.
	2. 修改AESUtil加密工具.
	3. 修改FileUtil的nio写文件的参数顺序。
#### 版本号：1.0  
- 更新日期：2018-07-07
- 更新内容：
	1. 更新validateUtil中的手机验证码.
#### 版本号：1.1 
- 更新日期：2018-07-14
- 更新内容：
	1. 更新RandomUtil中随机数生成.
#### 版本号：1.1 
- 更新日期：2018-07-14
- 更新内容：
	1. 更新RandomUtil中随机数生成.
#### 版本号：1.2
- 更新日期：2018-08-04
- 更新内容：
	1. 升级common-util到1.2版本，解决api-doc随机值的生成。
#### 版本号：1.3
- 更新日期：2018-09-29
- 更新内容：
	1. 升级common-util到1.3版本，添加ip白名单和黑名单拦截。
#### 版本号：1.4
- 更新日期：2018-09-29
- 更新内容：
	1. 更新RandomUtil中随机数生成 。
	2. 添加UUIDUtil工具类。
	3. ValidateUtil中增加HH:mm:ss格式验证。
#### 版本号：1.5
- 更新日期：2018-11-29
- 更新内容：
	1. 优化AES加密工具 。
#### 版本号：1.6
- 更新日期：2018-12-11
- 更新内容：
	1. 更新RandomUtil随机生成字符串的localDate和localDateTime来支持smart-doc。

	