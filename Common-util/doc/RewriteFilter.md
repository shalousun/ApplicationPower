# RewriteFilter过滤器
RewriteFilter是用于重定向前端路由的过滤，如果当前流行的前端mvvm框架，并且将前端代码最后合并到后端时，将采用RewriteFilter
来重写前端的路由。
## Springboot中配置实例：
```
@SpringBootApplication
public class SpringBootMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainApplication.class, args);
    }
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RewriteFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter(RewriteFilter.REWRITE_TO,"/index.html");
        registration.addInitParameter(RewriteFilter.REWRITE_PATTERNS, "/ui/*");
        registration.setName("testFilter");
        registration.setOrder(1);
        return registration;
    }
}
```

## SpringMvc中配置实例：
```
 <filter>
    <filter-name>RewriteFilter</filter-name>
    <filter-class>com.power.common.filter.RewriteFilter</filter-class>
    <init-param>
        <param-name>rewriteToUrl</param-name>
        <!--Setting Servers allow to referer,Multiple services are separated by semicolons  -->
        <param-value>/index.html</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>RewriteFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```