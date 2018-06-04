common-util是一个和任何模块都无关的模块，common-util模块中主要包含一些功能的类或者是通用的工具，任何模块都可以依赖他，然后使用其内部提供的一些工具类。Common-util的maven依赖使用如下：

```
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>common-util</artifactId>
    <version>0.1</version>
</dependency>
```
除此之外common-util中已包含一些公用的filter,如跨站请求伪造的过滤器RefererFilter,防止Xss和sql注入的XssAndSqlFilter
相关工具类的方法文件在doc下。

