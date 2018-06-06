datasource-aspect是一个spring web应用中的多数据源切换模块。依赖配置如下
```
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>datasource-aspect</artifactId>
    <version>0.1</version>
</dependency>
```

# 使用说明

1. 在需要动态切换数据源的类或者方法上注入注解,注解的value就是项目制定的一个数据源的唯一标识名
```
@TargetDataSource("one")
```
因为是公用的模块
2. 继承DataSourceAspect
由于该模块只依赖spring-jdbc和aspect-jweaver，因此DataSourceAspect并没有注入@Component注解，所以需要自己继承并加入注解
```
@Aspect
@Component
public class DbAspect extends DataSourceAspect {

    @Pointcut("execution(* com.boco.*.dao.*.*(..))")
    @Override
    protected void datasourceAspect() {
        super.datasourceAspect();
    }
}

```
**重点：** 另一点需要注意的是在多数据源动态切换的环境中DataSourceAspect使用的CustomDataSource注解触发可能会不起作用，仅有execution生效，
遇到这种情况可以重写DataSourceAspect的datasourceAspect方法修改Pointcut
