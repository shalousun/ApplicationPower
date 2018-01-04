# XssAndSqlFilter过滤器
XssAndSqlFilter过滤器是用来对系统xss攻击和sql注入做统一过滤拦的，本过滤器可以配置到需要增加安全性的系统中。ignores使用来配置例外的。
被例外的路径将会被放过安全拦截，一般只有一些特殊接口需要提供特殊字符才配置例外，否则请不要配置，其次对于接口自行做好安全检测。过滤器遇到特殊字符后将会抛出RuntimeException异常，因此推荐在系统下建立统一的500错误页面来处理错误。


配置实例如下：

```
<filter>
    <filter-name>XssAndSqlFilter</filter-name>
    <filter-class>com.boco.common.filter.XssAndSqlFilter</filter-class>
    <init-param>
        <param-name>ignores</param-name>
        <param-value>/test/*;/user/add</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>XssAndSqlFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
无例外配置：
```
<filter>
    <filter-name>XssAndSqlFilter</filter-name>
    <filter-class>com.boco.common.filter.XssAndSqlFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>XssAndSqlFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```