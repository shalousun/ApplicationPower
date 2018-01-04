ApplicationPower 是一个基于数据库单表Crud操作的项目生成器，生成的web项目自动集成spring,spring mvc,mybatis框架，最终生成基于maven构建的可
    运行web工程，生成完后只需要将生成的项目导入到eclipse、idea或者及其他开发工具部署至tomcat即可运行，当然生成的项目基于maven环境集成了
    jetty web容器，eclipse使用jetty:run命令即可运行，idea的用户只需点击maven projects下的plugins中找到jetty run即可启动项目。<br/>
        ApplicationPower是基于beetl模板来生成源代码的，因此可以灵活的修改模板来生成代码定义自己的开发接口规范。ApplicationPower相对
    mybatis generator来说配置更少、代码灵活性和可控性更高。
## 版本说明
    1. v1.0版本的CommonResult依赖于boco-health-common模块
    2. v1.1版本的CommonResult改为依赖独立模块Common-util
    3. v1.2版本升级spring到4.3.6，Controller层生成的代码使用@GetMapping和@PostMapping代替@RequestMapping注解
    4. v1.3版本，升级mybatis和druid的版本，生成的项目框架摒弃log4j，全面将日志升级到log4j2框架，mysql连接驱动升级到6.x版本
## 功能
  1. 根据连接的数据生成dao,model,service,controller,mapper,controllerTest,serviceTest代码
  2. 快速生成项目的maven web基础骨架
  3. 生成基于spring,spring mvc,mybatis框架结构项目所需的基础配置文件
  4. 生成web.xml配置文件
  5. 可以修改模板生成自己喜欢风格或者说修改修改来生成自己习惯的方法名
  6. 基于SL4J面向接口的标注化日志输出
  7. 可自由选择生成SSM框架项目或者Springboot的项目

## 使用说明
  1.使用注意事项
        在已经进行后，请勿将ApplicationPower的输出目录指定到当前工程，否则会出现代码覆盖，因此建议项目开发启动后将代码生成到别的地方拷贝到自己工        程下，后续会提供不覆盖配置，但是也有可能忘记修改配置，所以还是要小心。
  2.根据自己实际需求，修改generator.properties中的配置
```
是否生成注释
generator.comment=true

代码输出目录
generator.outDir=e:\\Test

基包名
generator.basePackage=com.boco

数据库表前缀,例如表t_user则需要去除前缀生成正确的实体
generator.table.prefix=

指定需要用哪张数据表生成代码，不指定则生成全部表的代码
generator.table.name=

生成项目的名称
generator.applicationName=Test

需要生成的代码层
可生成的代码层dao,model,service,controller,mapper,controllerTest,serviceTest
generator.layers=dao,model,service,controller,mapper,controllerTest

是否开启mybatis缓存，只能填写true或者false
generator.enableCache=true
```
  3.修改数据库配置jdbc.properties
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.username=root
jdbc.password=root
jdbc.url=jdbc\:mysql\://localhost:3306/cookbook?useUnicode=true&characterEncoding=UTF-8
```
  4.运行Test下的GenerateCodeTest生成项目
  5.将生成的项目导入编辑器

## 使用模板介绍
  1.model层模板
```
package ${basePackage}.model;

import java.io.Serializable;
${modelImports}

/**
*
* @author ${authorName}
* @date ${createTime}
*
*/
public class ${entitySimpleName} implements Serializable{

  private static final long serialVersionUID = ${SerialVersionUID}L;

 ${fields}
//getters and setters
 ${gettersAndSetters}
}
```
  2.dao层模板
```
package ${basePackage}.dao;

import java.util.List;
import java.util.Map;

import ${basePackage}.model.${entitySimpleName};

/**
*
* @author ${authorName}
* @date ${createTime}
*
*
*/

public interface ${entitySimpleName}Dao{

    /**
     * 保存数据
     * @param entity
     * @return
     */
    int save(${entitySimpleName} entity);
    
    /**
     * 更新数据
     * @param entity
     * @return
     */
    int update(${entitySimpleName} entity);
    
    /**
     * 删除数据
     * @param id
     * @return
     */
    int delete(int id);
    
    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    ${entitySimpleName} queryById(int id);
    
    /**
     * 查询所有数据
     * @return
     */
    List<${entitySimpleName}> queryAll();
    
    /**
     * query result to list of map
     * @param params Map查询参数
     * @return
     */
    List<Map<String,Object>> queryToListMap(Map<String,Object> params);
}
```
  3.service层模板
```
package ${basePackage}.service;

import java.util.List;
import ${basePackage}.model.${entitySimpleName};

/**
*
* @author ${authorName}
* @date ${createTime}
*
*/

public interface ${entitySimpleName}Service{

/**
 * 保存数据
 * @param entity
 * @return
 */
int save(${entitySimpleName} entity);

/**
 * 修改数据
 * @param entity
 * @return
 */
int update(${entitySimpleName} entity);

/**
 * 删除数据
 * @param id
 * @return
 */
int delete(int id);

/**
 * 根据id查询数据
 * @param id
 * @return
 */
${entitySimpleName} queryById(int id);

/**
 * 查询所有数据
 * @return
 */
List<${entitySimpleName}> queryAll();
}
```
  4.service实现层模板
  ```
  package ${basePackage}.service.impl;

  import java.util.List;

  import javax.annotation.Resource;
  import org.springframework.stereotype.Service;
  import ${basePackage}.model.${entitySimpleName};
  import ${basePackage}.dao.${entitySimpleName}Dao;
  import ${basePackage}.service.${entitySimpleName}Service;

  /**
   *
   * @author ${authorName}
   * @date ${createTime}
   *
   */
  @Service("${firstLowerName}Service")
  public class ${entitySimpleName}ServiceImpl  implements ${entitySimpleName}Service{

  	@Resource
  	private ${entitySimpleName}Dao ${firstLowerName}Dao;

  	@Override
  	public int save(${entitySimpleName} entity){
  		return ${firstLowerName}Dao.save(entity);
  	}

  	@Override
  	public int update(${entitySimpleName} entity){
  		return ${firstLowerName}Dao.update(entity);
  	}

  	@Override
  	public int delete(int id){
  		return ${firstLowerName}Dao.delete(id);
  	}

  	@Override
  	public List<${entitySimpleName}> queryAll(){
  		return ${firstLowerName}Dao.queryAll();
  	}

  	@Override
  	public ${entitySimpleName} queryById(int id){
  		return ${firstLowerName}Dao.queryById(id);
  	}

  }
  ```
  5.controller层模板
  ```
  package ${basePackage}.controller;


  import java.util.List;
  import javax.annotation.Resource;
  import javax.servlet.http.HttpServletRequest;

  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RequestMethod;
  import org.springframework.web.bind.annotation.ResponseBody;
  import org.springframework.web.bind.annotation.RequestMapping;

  import com.boco.health.common.model.CommonResult;
  import ${basePackage}.model.${entitySimpleName};
  import ${basePackage}.service.${entitySimpleName}Service;

  /**
   *
   * @author ${authorName}
   * @date ${createTime}
   *
   */
  @Controller
  @RequestMapping("${firstLowerName}")
  public class ${entitySimpleName}Controller{
  	@Resource
  	private ${entitySimpleName}Service ${firstLowerName}Service;

  	@ResponseBody
  	@RequestMapping(value="/add",method = RequestMethod.POST)
  	public CommonResult save(${entitySimpleName} entity){
  		CommonResult result = new CommonResult();
  		try{
  			${firstLowerName}Service.save(entity);
  			result.setSuccess(true);
  		}catch(Exception e){
  			result.setMessage("添加数据失败");
  		}
  		return result;
  	}

  	@ResponseBody
  	@RequestMapping(value="/update",method = RequestMethod.POST)
  	public CommonResult update(${entitySimpleName} entity){
  		CommonResult result = new CommonResult();
  		try{
  			${firstLowerName}Service.update(entity);
  			result.setSuccess(true);
  		}catch(Exception e){
  			result.setMessage("修改数据失败");
  		}
  		return result;
  	}

  	@ResponseBody
  	@RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
  	public CommonResult delete(@PathVariable int id){
  		CommonResult result = new CommonResult();
  		try{
  			${firstLowerName}Service.delete(id);
  			result.setSuccess(true);
  		}catch(Exception e){
  			result.setMessage("删除数据失败");
  		}
  		return result;
  	}

  	@ResponseBody
  	@RequestMapping(value="/query/{id}",method = RequestMethod.GET)
  	public CommonResult queryById(@PathVariable int id){
  		CommonResult result = new CommonResult();
  		${entitySimpleName} entity = ${firstLowerName}Service.queryById(id);
  		if(null != entity){
  			result.setData(entity);//成功返回数据
  			result.setSuccess(true);
  		}else{
  			result.setMessage("没有找到匹配数据");
  		}
  		return result;
  	}

  	@ResponseBody
  	@RequestMapping(value="/query/list",method = RequestMethod.GET)
  	public List<${entitySimpleName}> query${entitySimpleName}List(){
  	    return ${firstLowerName}Service.queryAll();
  	}
  }
  ```





