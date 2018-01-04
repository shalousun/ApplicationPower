# RefererFilter过滤器
RefererFilter过滤器是用来拦击跨站钓鱼伪造referer的行为，在黄岛安全测试被大量系统使用。ignores是用来配置例外referer的，例如想入从一个门户挂个连接去访问一个其他系统
则必须将门户的项目名称配置到ignores,否则将被视作跨站伪造请求。

配置实例：
```
 <filter>
    <filter-name>RefererFilter</filter-name>
    <filter-class>com.boco.common.filter.RefererFilter</filter-class>
    <init-param>
        <param-name>ignores</param-name>
        <!--Setting Servers allow to referer,Multiple services are separated by semicolons  -->
        <param-value>protal;portal</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>RefererFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```