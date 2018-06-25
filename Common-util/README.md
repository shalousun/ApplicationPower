common-util是一个和任何模块都无关的模块，common-util模块中主要包含一些功能的类或者是通用的工具，
任何模块都可以依赖他，然后使用其内部提供的一些工具类。common-util已经发布到公共的maven仓库中，maven依赖使用如下：

```
<dependency>
  <groupId>com.github.shalousun</groupId>
  <artifactId>common-util</artifactId>
  <version>0.3</version>
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
