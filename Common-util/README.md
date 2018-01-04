Common-util是一个和任何模块都无关的模块，Common-util模块中主要包含一些功能的类或者是通用的工具，任何模块都可以依赖他，然后使用其内部提供的一些工具类。Common-util的maven依赖使用如下：

```
<dependency>
    <groupId>com.boco.sp</groupId>
    <artifactId>Common-util</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
除此之外Common-util中已包含一些公用的filter,如跨站请求伪造的过滤器RefererFilter,防止Xss和sql注入的XssAndSqlFilter

