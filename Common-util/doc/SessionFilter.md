# SessionFilter过滤器
SessionFilter过滤器是用来对系统登录做统一过滤拦截，本过滤器可以集成到需要自定义登录的系统中。

SessionFilter的配置参考如下：

配置实例1：本例SessionFilter配置的url-pattern值/*。因此所有的资源都会被拦截，这种配置就需要对登录页面、登录接口、静态资源不进行过滤等资源进行放行。

```
 <filter>
    <filter-name>SessionFilter</filter-name>
    <filter-class>com.power.common.filter.SessionFilter</filter-class>
    <init-param>
        <!--系统重要存放用户名sessionKey-->
        <param-name>sessionKey</param-name>
        <param-value>username</param-value>
    </init-param>
    <init-param>
        <!-- 对登录页面、登录接口、静态资源不进行过滤，多个配置间用;隔开 -->
        <param-name>exceptUrlPattern</param-name>
        <param-value>/login;/user/login;/statics/*</param-value>
    </init-param>
    <init-param>
        <!-- 未通过跳转到登录界面 -->
        <param-name>redirectUrl</param-name>
        <param-value>/login</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>SessionFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

配置实例2：本例SessionFilter配置的url-pattern值/admin/* ,说明过滤器只对admin前缀路径url起作用,这种配置一般就不需要配置exceptUrlPattern了，当然如果想对admin路径下某个子路路径开例外，那么也可以配置exceptUrlPattern。
```
<filter>
    <filter-name>SessionFilter</filter-name>
    <filter-class>com.power.common.filter.SessionFilter</filter-class>
    <init-param>
        <!--系统重要存放用户名sessionKey-->
        <param-name>sessionKey</param-name>
        <param-value>username</param-value>
    </init-param>
    <init-param>
        <!-- 未通过跳转到登录界面 -->
        <param-name>redirectUrl</param-name>
        <param-value>/login</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>SessionFilter</filter-name>
    <url-pattern>/admin/*</url-pattern>
</filter-mapping>
```
