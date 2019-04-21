ApplicationPower是一个快速的项目生成脚手架，ApplicationPower一直秉承着简洁和做工程标准化，简洁主要体现在生成的代码简单可控，
不引入复杂的框架，只创建你需要的。但是在工程化上面的功能却相当完善，包括springboot项目整体框架初始化生成、基于mybatis的代码生成、
项目的简单部署打包一体化服务脚本生成、docker容器化配置生成、kubernates容器部署yaml模板自动生成【当前支持deployment、service、ingress】，
当然ApplicationPower还提供smart-doc这样颠覆swagger类似传统文档工具，做到无注解、无侵入和更加智能化的api文档自动生成工具。
<br/>
 喜欢的同学欢迎star，帮助ApplicationPower进入GVP.
        
 技术交流群：170651381<br/>
 [github同步地址](https://github.com/shalousun/ApplicationPower)
## 创建的项目技术
     ● 核心框架：Spring Boot 1.5.14.RELEASE或Spring Boot 2.1.14.RELEASE
     ● 数据库连接池：druid-spring-boot-starter 1.1.16
     ● 持久层框架：mybatis-spring-boot-starter 2.0.1
     ● 分页插件：pagehelper-spring-boot-starter 1.2.10
     ● 分布式事务管理：spring-boot-starter-jta-atomikos
     ● 日志管理：SLF4J和log4j2
## 结构说明
   1. smart-doc完全基于源代码和原生注释来推导的restful api文档，无需在代码添加任何注解或者引入新的写法，完全零侵入。
   2. common-util是开发中常用的一些工具类，目前文档比较详细，也是application-power所依赖的模块，目前已发布到中央仓库。
   3. application-power是整个项目的核心，专门用于生成Spring Boot微服务架构项目和Spring mvc+mybatis架构项目的脚手架，
   4. datasource-aspect是spring web应用下多数据源动态切换的通用模块【未经过可靠测试，使用请谨慎】
   5. mybatis-template是用于重写SqlSessionTemplate来支持分布式事务环境下的动态数据源切换【未经过可靠测试，使用请谨慎】
## 功能
  1. 一键创建maven或者gradle构建的springboot项目，开箱即运行
  2. 支持基于mybatis的dao、model、service、controller的代码生成
  3. 支持service层和controller层的mock单元测试生成
  4. 自主化创建.gitignore
  5. 支持基于assembly打包的springboot工程化启动脚本和部署说明文档
  6. 支持springboot项目容器化构建Dockerfile文件创建
  7. 自动创建项目的kubernates容器编排部署yaml文件。
  8. 自动集成当前基于代码推导的零侵入restful api文档生成工具。
  9. 支持自动集成atomikos分布式事务管理(谨慎使用,druid整合多数据源mysql驱动请先使用6.0.6版本)
  10. 吸纳开源提供一些可以使用的基础工具类
  11. 超自由的代码生成配置，自由控制自己需要的。
  12. 省心生成通用的restful exception处理类，和jsr303参数验证错误处理模板。
  
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

# @since 1.6
# 多数据源多个数据数据源用逗号隔开[就是给不同的数据源一个别名]，不需要多数据源环境则空出来
# 对于多数据源会集成分布式事务
# uage:generator.multiple.datasource=oracle,mysql
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

# @since 1.7.2
# default is use maven build,current support maven and gradle
# generator.build.tool=gradle //use gradle
generator.build.tool=

# @since 1.7.8
# 是否使用lombok来精简代码
generator.lombok=false
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
#### 版本号：1.7.2
- 更新日期：2018-06-06
- 更新内容：
	1. 优化使用maven构建下的docker构建脚本docker.sh.
	2. 将common-util模块的包名变更为com.power.common，完善common-util各工具类的的文档。
	3. 修改若干模板配置bug.
	4. 增加生成gradle项目的功能，包括SpringBoot在gradle下docker的容器化配置实现。
	5. 将common-util、datasource-aspect、mybatis-template发布到maven中央仓库。使用是无需
	再将这些模块安装到本地在使用
#### 版本号：1.7.3
- 更新日期：2018-06-26
- 更新内容：
	1. 升级common-util到03版本，增加OKHttp3Util,AESUtil增加新的ECB加解密方法
	2. 首次发布可以使用的api-doc文档工具，提高开发中的接口文档开发效率(不)。
#### 版本号：1.7.4
- 更新日期：2018-07-07
- 更新内容：
	1. 升级common-util到1.0版本，该版本解决了更新最新的手机号码验证。
	2. 发布api-doc v0.2,该版本增强的接口返回类型的推导能力，解决众多bug。
	3. application-power模块增加参数验证的restful错误码返回处理。
#### 版本号：1.7.5
- 更新日期：2018-08-04
- 更新内容：
	1. 升级common-util到1.2版本，解决api-doc随机值的生成。
	2. 发布api-doc v0.5,处理很多字段处理的报错的问题。
	3. 优化application-power代码生成中dockerfile、docker.sh和assembly打包的启动脚本。
	4. 增加springboot项目使用validate参数验证的错误拦截模板。	
#### 版本号：1.7.6
- 更新日期：2018-08-23
- 更新内容：
	1. 升级application-power生成springboot项目的springboot-start到1.5.14.RELEASE。
	2. 升级application-power生成springboot项目druid到1.1.10
	3. 修改log4j2.xml模板，优化dockerfile模板注释
	4. application-power代码生成中增加k8s部署文件模板生成。
	5. 修改原api-doc为smart-doc。
#### 版本号：1.7.7
- 更新日期：2018-08-25
- 更新内容：
	1. smart-doc模块升级到1.0，添加文档合并导出功能。
	2. 修复application-power在mac上生成代码路径错误的bug。
#### 版本号：1.7.8
- 更新日期：2018-09-01
- 更新内容：
	1. 创建springboot1.x分支，使用springboot 1.x版本拉取该分支。
	2. 添加lombok支持，生成代码时可自由选择。
	3. 升级springboot到2.0.3版本
	4. 内置容器暂时切换到tomcat，增加graceful shutdown【已测试】
#### 版本号：1.7.9
- 更新日期：2018-11-15
- 更新内容：
	1. 优化SpringBoot快速部署包中setenv.sh脚本中jvm参数设置
	2. 新增UUIDUtil工具。
	3. 升级springboot到2.1.0版本
	4. 修改代码生成中主键更新语句bug
	5. 修改start.sh启动脚本中端口检测的bug
	6. 优化k8s部署yml模板
#### 版本号：1.8.0
- 更新日期：2018-11-24
- 更新内容：
	1. 基于gradle构建的代码生成新增快速部署打包功能，类似于maven项目的assembly
	2. 优化stop.sh脚本提高兼容性。
	3. 优化mybatis mapper.xml文件中sql生成，自动识别表主键字段
	4. 升级gradle项目构建模板中的jar包依赖版本
	5. 优化项目采用驼峰书写是docker镜像tag错误无法构建镜像的bug[自动作小写转换]
#### 版本号：1.8.1
- 更新日期：2018-12-13
- 更新内容：
	1. 修改common-util模块CommonResult【兼容旧版本】，增加常用静态方法方便快速编码
	2. 优化代码生成的service模板，减少重复代码，该优化依赖common-util 1.7版本
	3. 代码生成删除ResultUtil,目前由CommonResult接管
#### 版本号：1.8.2
- 更新日期：2019-01-06
- 更新内容：
	1. 修改代码生成mybatis mapper目录为resources下的mappers下
	2. 优化代码生成，生成过程中可以自动根据主键类型和主键名称来变更对应的接口层参数类型
	3. 修改自动生成SpringBoot启动类的命名规则，根据应用名来自动实现
	4. 生成的代码model表注释规则优化为table+comment
#### 版本号：1.8.3
- 更新日期：2019-04-18
- 更新内容：
	1. 优化生成的k8s部署模板
	2. 升级代码生成对Common-util版本的依赖
#### 版本号：1.8.4
- 更新日期：2019-04-21
- 更新内容：
	1. 修复生成的多数据源代码错误问题

	




