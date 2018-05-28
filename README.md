ApplicationPower 是一个快速的项目生成脚手架，只需要连接数据库便可快速的创建一个基于maven构建的SpringMvc+Mybatis或者是Spring Boot+Mybatis架构的项目。
创建的标准工程无论是基于SpringMvc的传统项目还是基于Spring Boot的微服务项目都可做到开箱即可正确运行，使用它两分钟就可以快速的创建一个学习的demo或者是一个项目初期的
架构。ApplicationPower在创建过程中会帮您创建许多标准的配置文件和通用代码，屏蔽了人工创建项目初期中的配置繁琐和错误情况，大大减少了初期建项目工程的时间。<br/>
        ApplicationPower是基于beetl模板来生成源代码的，因此可以灵活的修改模板来生成代码定义自己的开发接口规范。ApplicationPower在一直不断努力地去减少项目开发
        中的各种重复工作。<br/>
        技术交流群：170651381<br/>
    **重点：** ApplicationPower目前对于Springboot+Mybatis框架的项目创建能力更加，因此推荐选择她来创建springboot。
## 创建的项目技术
     ● 核心框架：Spring Boot 1.5.9 或Spring MVC 4.3.6
     ● 数据库连接池：druid-spring-boot-starter 1.1.6或druid 1.1.6
     ● 持久层框架：mybatis-spring-boot-starter或MyBatis 3.4.2
     ● 分页插件：pagehelper-spring-boot-starter或pagehelper 5.x
     ● 分布式事务管理：spring-boot-starter-jta-atomikos或atomikos 4.04
     ● 日志管理：SLF4J和log4j2
## 结构说明
   1. api-doc是一个未来将使用原生doc注释来生成markdown api文档的项目，目前不可用
   2. common-util是开发中常用的一些工具类，目前文档比较详细，也是application-power所依赖的模块，在使用application-power前需要将它安装到你的本地。
   3. application-power是整个项目的核心，专门用于生成Spring Boot微服务架构项目和Spring mvc+mybatis架构项目的脚手架，
   4. datasource-aspect是spring web应用下多数据源动态切换的通用模块
   5. mybatis-template是用于重写SqlSessionTemplate来支持分布式事务环境下的动态数据源切换
        将assembly打包的springboot启动脚本jvm参数设置提取到setenv.sh中
ps: [1.6及以前的版本地址：](https://gitee.com/stana/ApplicationPower)
## 功能
  1. 根据连接的数据生成dao,model,service,controller,mapper,controllerTest,serviceTest代码
  2. 项目的maven web基础骨架
  3. 生成基于spring,spring mvc,mybatis框架结构项目所需的基础配置文件
  4. 生成web.xml配置文件
  5. 可以修改模板生成自己喜欢风格或者说修改修改来生成自己习惯的方法名
  6. 基于SL4J面向接口的标注化日志输出
  7. 支持创建多数据源和集成atomikos分布式事务
  8. 自动为您创建.gitignore模版到项目中
  9. 创建标准化的非docker部署打包方案和完整服务启动脚本和部署文档

## 使用说明
  1.使用注意事项
        在已经进行后，请勿将ApplicationPower的输出目录指定到当前工程，否则会出现代码覆盖，因此建议项目开发启动后将代码生成到别的地方拷贝到自己工        程下，后续会提供不覆盖配置，但是也有可能忘记修改配置，所以还是要小心。
  2.根据自己实际需求，修改generator.properties中的配置
 ```
  
#是否生成注释
generator.comment=true

#代码输出目录
generator.outDir=E:\\Test

#基包名
generator.basePackage=com.sunyu.hbase



#数据库表前缀,例如表t_user则需要去除前缀生成正确的实体
generator.table.prefix=tb_

#指定需要用哪张数据表生成代码，不指定则生成全部表的代码,指定表名后下面的过滤器将失效
generator.table.name=

# @since 1.6.3
# 过滤数据库表，生成的时候只生成和过滤器匹配的表
generator.table.filter.prefix=TB_

#生成项目的名称
generator.applicationName=test

#需要生成的代码层
#可生成的代码层dao,model,service,controller,mapper,controllerTest,serviceTest
generator.layers=dao,model,service,controller,mapper,controllerTest,serviceTest

#需要生成的方法，方法间用英文逗号隔开，写错将无法生成基础方法
#可生成的方法包括add,update,delete,query,page,queryToListMap。
# query方法查询单条数据，page生成分页,queryToListMap是查询结果以List<Map<Stirng,Object>>返回
generator.methods=add,update,delete,query,page

#mybatis自动转驼峰映射，默认开启
generator.mapUnderscoreToCamelCase=true
#是否开启mybatis缓存，只能填写true或者false
generator.enableCache=true

#是否需要生成mybatis mapper配置文件的ResultMap
#默认不生成result
generator.resultMap=false

# @since 1.5
# 打包springboot时是否采用assembly
# 如果采用则将生成一系列的相关配置和一系列的部署脚本
generator.package.assembly=true

#@since 1.6
# 多数据源多个数据数据源用逗号隔开，不需要多数据源环境则空出来
# 对于多数据源会集成分布式事务
generator.multiple.datasource=

# @since 1.6
# jta-atomikos分布式事务支持
generator.jta=true

# @since 1.6.4
# 指定springboot项目的日志文件，避免使用assembly打包后在window修改脚本的烦恼
# 推荐根据自己的日志在自动生成前指定好，屏蔽在系统间修改脚本的发生字符不一致问题
generator.application.logConfig=log4j2.xml

# @since 1.7.1
# 该值为true时会为springboot maven项目创建Dockerfile和相应的构建脚本
generator.docker=true

```
  3.修改数据库配置jdbc.properties
```
  jdbc.driver=com.mysql.jdbc.Driver
  jdbc.username=root
  jdbc.password=root
  jdbc.url=jdbc\:mysql\://localhost:3306/cookbook?useUnicode=true&characterEncoding=UTF-8
```
  4.运行Test下的GenerateCodeTest生成项目
```
//生成普通SSM框架的maven工程
new CodeWriter().execute();
//生成Springboot+Mybatis的工程
new CodeWriter().executeSpringBoot();
```
## 项目的代码模板
 
 了解代码模板请查阅application-power/doc/template.md
 
## 关于Spring boot应用的打包部署
1. 目前对于互联网的项目，推荐使用docker容器化部署
2. 对于传统企业小应用单体部署，推荐使用application-power的整合assembly后的整套标准打包方案，
   application-power自动为您生成优雅的启动停止服务脚本，并且为您的项目自动创建了一个基本的部署文档。
 
ps：关于spring的非docker打包，请了解改方案[Springboot基于assembly的服务化打包方案](https://my.oschina.net/u/1760791/blog/1587996)

application-power整合的springboot打包后的结构参考
```
├─bin
│      dump.sh
│      server.sh
│      setenv.sh
│      start.bat
│      start.sh
│      stop.sh
│      yaml.sh   
├─config
│      application.yml
│      log4j2.xml
│      mybatis-config.xml
├─docs
│      DEPLOY.md
├─lib
│      springboot-script.jar  
└─logs
```


## application-power创建的springboot项目结构
```
├─docs
│      DEPLOY.md
└─src
│   ├─main
│   │  ├─assembly
│   │  │  ├─bin  
│   │  │  └─config
│   │  ├─java
│   │  │  └─com.company.script
│   │  └─resources
│   │                  
│   └─test
│      └─java
│         └─com.company.script
│                               
│ .gitignore
│ pom.xml
```
##更新日志
 
#### 历史更新
    1. v1.0版本的CommonResult依赖于boco-health-common模块
    2. v1.1版本的CommonResult改为依赖独立模块Common-util
    3. v1.2版本升级spring到4.3.6，Controller层生成的代码使用@GetMapping和@PostMapping代替@RequestMapping注解。
    4. v1.3版本升级mybatis和druid的版本，全面将日志升级到log4j2框架，mysql驱动升级到6.x,支持创建springboot项目。
    5. v1.4版本升级实现生成方法可自由控制(ps:参考generator.properties)，基础方法增加返回List<Map<String,Object>>的方法。
    6. v1.4.1版本升级springboot和其他依赖的版本，修改springboot测试模板错误，springboot项目增加springloaded热部署插件。
    7. v1.4.2版本优化生成代码时对数据库的连接次数，restful接口单元测试生成中add和update方法增加自动添加参数和赋予随机值的功能
    8. v1.5版本增加springboot项目基于assembly的服务化打包功能，完备的服务脚本使得在window或linux系统启动和运维项目更轻松
    9. v1.6版本增加配置来支持springmvc和springboot多数据源动态切换和atomikos分布式事务支持，快随构建一个数据一致性架构的项目.
       demo:https://gitee.com/sunyurepository/multiple-datasource
    10. v1.6.1版本修改生成的springboot项目打成jar包后mybatis别名扫描出错的bug
    11. v1.6.2版本修改创建的springmvc分布式事务多数据源项目8小时中断的配置bug，添加支持oracle库代码生成
    12. v1.6.3版本添加generator.table.filter.prefix用来过滤表，修改连接oracle库表生成的代码字段错误的bug

#### 版本号：1.7.0  
- 更新日期：2018-05-25  
- 更新内容：
	1. 添加.gitignore的创建.
	2. assembly打包springboot项目时自动生成DEPLOY.md部署文档，并将DEPLOY.md存放到docs下.
	3. 将assembly打包的springboot启动脚本jvm参数设置提取到setenv.sh中.
#### 版本号：1.7.1  
- 更新日期：2018-05-28 
- 更新内容：
	1. 修改assembly打包的springboot项目的start.sh和start.bat启动脚本,支持动态加载打包后的properties配置文件.
	2. 增加创建maven项目自动创建dockerfile和docker.sh构建脚本.




