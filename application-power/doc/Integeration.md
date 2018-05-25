ApplicationPower生成的项目集成其他服务

## 集成dubbo
如果集成dubbo服务可根据自己的业务需求，配置不需要生成的代码层，例如：一般情况下dubbo一般不提供controller层，因此不必要生成这部分代码。集成dubbo的步骤如下：
1.在pom中添加dubbo服务所需的依赖

    <!-- dubbo dependence-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId>
        <version>2.5.3</version>
        <exclusions>
            <exclusion>
                <groupId>org.springframework</groupId>
                <artifactId>spring</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>com.github.sgroschupf</groupId>
        <artifactId>zkclient</artifactId>
        <version>0.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.4.6</version>
    </dependency>

2.通过通过spring配置管理注册dubbo服务
创建spring-dubbo-provider.xml文件并放在resource目录下，在文件中注册接口

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://code.alibabatech.com/schema/dubbo
           http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

        <!-- 提供方应用信息，用于计算依赖关系 -->
        <dubbo:application name="dubbo-server"/>

        <!-- 使用multicast广播注册中心暴露服务地址 -->
        <dubbo:registry address="zookeeper://localhost:2181"/>

        <!-- 用dubbo协议在20880端口暴露服务 -->
        <dubbo:protocol name="dubbo" port="20880"/>

        <!-- 声明需要暴露的服务接口 -->
        <dubbo:service interface="com.boco.sp.external.service.GoodsService" ref="goodsService"/>


    </beans>

3.在web.xml中加载spring-dubbo-provider.xml

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
            classpath:spring-mybatis.xml,
            classpath:spring-dubbo-provider.xml
        </param-value>
    </context-param>

4.dubbo服务消费放配置
在消费方的pom中加入dubbo依赖，请参考1，下一步在消费方创建spring-dubbo-consumer.xml放到resource目录下，并在web.xml中加载该配置文件，spring-dubbo-consumer.xml配置参考如下
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
    <dubbo:application name="dubbo-client"/>
    <!-- 使用multicast广播注册中心暴露发现服务地址 -->
    <dubbo:registry address="zookeeper://localhost:2181"/>

    <!-- 生成远程服务代理，可以和本地bean一样使用goodsService -->
    <dubbo:reference id="goodsService" interface="com.boco.sp.external.service.GoodsService"/>

</beans>

```




