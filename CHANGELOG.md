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
#### 版本号：1.8.5
- 更新日期：2019-09-14
- 更新内容：
	1. 优化升级生成的k8s部署模板
	2. 生成代码时支持最小生成，可通过useDb配置来开启和关闭数据库相关操作的代码
#### 版本号：1.8.6
- 更新日期：2020-01-04
- 更新内容：
	1. 删除spring mvc支持
	2. 添加mybatis-plus支持
#### 版本号：1.8.7
- 更新日期：2020-09-15
- 更新内容：
	1. 删除原始spring mvc留下的残留文件
	2. 优化代码和模板