# RefererFilter过滤器
RefererFilter过滤器是用来拦击跨站钓鱼伪造referer的行为。ignores是用来配置例外referer的，例如想入从一个门户挂个连接去访问一个其他系统
则必须将门户的项目名称配置到ignores,否则将被视作跨站伪造请求。
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
        registration.setFilter(new RefererFilter());
        registration.addUrlPatterns("/*");
        //多个例外配置使用分号隔开，注意这里的例外是可选的，如果没有配置例外，则表示拦截所有
        registration.addInitParameter(RefererFilter.IGNORES,"protal;portal");
        registration.setName("refererFilter");
        registration.setOrder(1);
        return registration;
    }
}
```
## spring mvc中配置实例：
```
 <filter>
    <filter-name>RefererFilter</filter-name>
    <filter-class>com.power.common.filter.RefererFilter</filter-class>
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