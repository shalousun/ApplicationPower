ApplicationPower是一个快速的项目生成脚手架，ApplicationPower一直秉承着简洁和做工程标准化，简洁主要体现在生成的代码简单可控，
不引入复杂的框架，只创建你需要的。但是在工程化上面的功能却相当完善，包括Spring Boot项目整体框架初始化生成、基于mybatis的代码生成、
项目的简单部署打包一体化服务脚本生成、docker容器化配置生成、kubernetes容器部署yaml模板自动生成【当前支持deployment、service、ingress】，
当然ApplicationPower还提供smart-doc这样颠覆swagger类似传统文档工具，做到无注解、无侵入和更加智能化的api文档自动生成工具。
<br/>
 喜欢的同学欢迎star，帮助ApplicationPower进入GVP.
        
 技术交流群：170651381<br/>
 [github同步地址](https://github.com/shalousun/ApplicationPower)
## 创建的项目技术
     ● 核心框架：Spring Boot 1.5.14.RELEASE或Spring Boot 2.2.8.RELEASE
     ● 数据库连接池：druid-spring-boot-starter 1.1.22
     ● 持久层框架：mybatis-spring-boot-starter 2.1.2
     ● 分页插件：pagehelper-spring-boot-starter 1.2.13
     ● 分布式事务管理：spring-boot-starter-jta-atomikos
     ● 日志管理：SLF4J和log4j2
## 结构说明
   1. smart-doc已经单独移出本仓库独立开发[【smart-doc】](https://gitee.com/sunyurepository/smart-doc)。
   2. common-util是开发中常用的一些工具类，目前文档比较详细，也是application-power所依赖的模块，目前已发布到中央仓库。
   3. application-power是整个项目的核心，专门用于生成Spring Boot微服务架构项目和Spring mvc+mybatis架构项目的脚手架，
   4. datasource-aspect是spring web应用下多数据源动态切换的通用模块【未经过可靠测试，使用请谨慎】
   5. mybatis-template是用于重写SqlSessionTemplate来支持分布式事务环境下的动态数据源切换【未经过可靠测试，使用请谨慎】
## 功能
  1. 一键创建maven或者gradle构建的Spring Boot项目，开箱即运行
  2. 支持基于mybatis的dao、model、service、controller的代码生成
  3. 支持service层和controller层的mock单元测试生成
  4. 自主化创建.gitignore
  5. 支持基于assembly打包的Spring Boot工程化启动脚本和部署说明文档
  6. 支持Spring Boot项目容器化构建Dockerfile文件创建
  7. 自动创建项目的kubernetes容器编排部署yaml文件。
  8. 自动集成当前基于代码推导的零侵入文档生成工具[【smart-doc】](https://gitee.com/sunyurepository/smart-doc)。
  9. 支持自动集成atomikos分布式事务管理(谨慎使用,druid整合多数据源mysql驱动请先使用6.0.6版本)
  10. 吸纳开源提供一些可以使用的基础工具类
  11. 超自由的代码生成配置，自由控制自己需要的。
  12. 省心生成通用的restful exception处理类，和jsr303参数验证错误处理模板。
  13. 自动化创建项目README模板。
  
ApplicationPower提供的每一个功能都是走心的！不添油加醋，坚持简单不引入新的学习成本，只给你需要的！

## ApplicationPower发展和宗旨

ApplicationPower从诞生到现在，一直专注于去做项目前期标准化和减少各种重复的劳动。由于Application做的是业务无关性的东西，
因此已经在一些知名企业中应用。

ApplicationPower只做有态度的开源，坚决不做伪开源!


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
 generator.basePackage=com.power.demo.dubbo
 #数据库表前缀,例如表t_user则需要去除前缀生成正确的实体
 generator.table.prefix=t_
 #指定需要用哪张数据表生成代码，不指定则生成全部表的代码,指定表名后下面的过滤器将失效
 generator.table.name=
 # @since 1.6.3
 # 过滤数据库表，生成的时候只生成和过滤器匹配的表
 generator.table.filter.prefix=
 #生成项目的名称
 generator.applicationName=SpringBoot2-Dubbo-Provider
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
 # @since 1.6.4
 # 指定springboot项目的日志文件，避免使用assembly打包后在window修改脚本的烦恼
 # 推荐根据自己的日志在自动生成前指定好，屏蔽在系统间修改脚本的发生字符不一致问题
 generator.application.logConfig=log4j2.xml
 # @since 1.7.1
 # 该值为true时会为springboot maven项目创建Dockerfile和相应的构建脚本
 generator.docker=true
 # @since 1.6
 # 多数据源多个数据数据源用逗号隔开[就是给不同的数据源一个别名]，不需要多数据源环境则空出来
 # 对于多数据源会集成分布式事务
 # uage:generator.multiple.datasource=oracle,mysql
 generator.multiple.datasource=
 # @since 1.6
 # jta-atomikos分布式事务支持
 generator.jta=false
 
 # @since 1.7.2
 # default is use maven build,current support maven and gradle
 # generator.build.tool=gradle //use gradle
 generator.build.tool=
 
 # @since 1.7.8
 # if true use lombok
 generator.lombok=true
 
 # @since 1.7.9
 # 是否需要生成db相关代码
 generator.useDb=true
 
 # @since 1.8.6
 # 是否支持mybatis-plus
 generator.useMybatisPlus=true
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
//生成Spring Boot+Mybatis的工程
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
## 更新日志
 
[更新记录](https://github.com/shalousun/ApplicationPower/blob/master/CHANGELOG.md)
	

	




