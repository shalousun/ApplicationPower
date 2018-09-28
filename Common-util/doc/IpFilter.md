# IpFilter过滤器
IpFilter用于过滤ip白名单和黑名单
## Springboot中配置实例：
```
/**
 * @author yu 2018/9/28.
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean ipFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new IpFilter());
        registration.addUrlPatterns("/*");
        //ip 白名单，多个ip用英文逗号隔开
        registration.addInitParameter(IpFilter.ALLOW,"127.0.0.1");
        //ip 黑名单，多个ip用英文逗号隔开，黑名单优先级高于白名单
        registration.addInitParameter(IpFilter.DENY,"192.168.248.1");
        //设置ip拦截响应时json响应信息，根据自己的应用来定义，如果不设置则直接抛出异常
        //对于Spring Boot应用，不好抓取filter的异常，建议设置错误响应
        registration.addInitParameter(IpFilter.MSG, JSON.toJSONString(ResultUtil.error(ErrorCodeEnum.UNKNOWN_ERROR)));
        registration.setName("ipFilter");
        return registration;
    }
}
```