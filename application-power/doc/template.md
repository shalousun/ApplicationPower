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
/** getters and setters */
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
import java.util.Map;
import com.boco.common.model.CommonResult;
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
    CommonResult save(${entitySimpleName} entity);
    
    /**
     * 修改数据
     * @param entity
     * @return
     */
    CommonResult update(${entitySimpleName} entity);
    
    /**
     * 删除数据
     * @param id
     * @return
     */
    CommonResult delete(int id);
    
    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    CommonResult queryById(int id);
    
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
  4.service实现层模板
```
package ${basePackage}.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.boco.common.model.CommonResult;
import ${basePackage}.model.${entitySimpleName};
import ${basePackage}.dao.${entitySimpleName}Dao;
import ${basePackage}.service.${entitySimpleName}Service;

/**
 * Created by ApplicationPower.
 * @author ${authorName} on ${createTime}.
 */
@Service("${firstLowerName}Service")
public class ${entitySimpleName}ServiceImpl  implements ${entitySimpleName}Service{

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(${entitySimpleName}Service.class);

    @Resource
    private ${entitySimpleName}Dao ${firstLowerName}Dao;

    @Override
    public CommonResult save(${entitySimpleName} entity) {
        CommonResult result = new CommonResult();
        try {
        	${firstLowerName}Dao.save(entity);
        	result.setSuccess(true);
        } catch (Exception e) {
        	result.setMessage("添加数据失败");
        	logger.error("${entitySimpleName}Service添加数据异常：",e);
        }
        return result;
    }

    @Override
    public CommonResult update(${entitySimpleName} entity) {
        CommonResult result = new CommonResult();
        try {
            ${firstLowerName}Dao.update(entity);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("修改数据失败");
            logger.error("${entitySimpleName}Service修改数据异常：",e);
        }
        return result;
    }

    @Override
    public CommonResult delete(int id) {
        CommonResult result = new CommonResult();
        try {
            ${firstLowerName}Dao.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("删除数据失败");
            logger.error("${entitySimpleName}Service删除数据异常：",e);
        }
        return result;
    }

    @Override
    public CommonResult queryById(int id) {
        CommonResult result = new CommonResult();
        ${entitySimpleName} entity = ${firstLowerName}Dao.queryById(id);
        if (null != entity) {
            //成功返回数据
            result.setData(entity);
            result.setSuccess(true);
        } else {
            result.setMessage("没有找到匹配数据");
            logger.info("${entitySimpleName}Service未查询到数据，编号：{}",id);
        }
        return result;
    }

    @Override
    public PageInfo queryPage(int offset, int limit) {
        PageHelper.offsetPage(offset,limit);
        List<${entitySimpleName}> list = ${firstLowerName}Dao.queryPage();
        return new PageInfo(list);
    }

    @Override
    public List<Map<String,Object>> queryToListMap(Map<String,Object> params){
        return ${firstLowerName}Dao.queryToListMap(params);
    }
}
```
  5.controller层模板
```
package ${basePackage}.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageInfo;
import com.boco.common.model.CommonResult;
import ${basePackage}.model.${entitySimpleName};
import ${basePackage}.service.${entitySimpleName}Service;

/**
 * Created by ApplicationPower.
 * @author ${authorName} on ${createTime}.
 */
@Controller
@RequestMapping("${firstLowerName}")
public class ${entitySimpleName}Controller {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(${entitySimpleName}Controller.class);

    @Resource
    private ${entitySimpleName}Service ${firstLowerName}Service;

    @ResponseBody
    @PostMapping(value = "/add")
    public CommonResult save(${entitySimpleName} entity) {
        return ${firstLowerName}Service.save(entity);
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public CommonResult update(${entitySimpleName} entity) {
        return ${firstLowerName}Service.update(entity);
    }

    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable int id) {
        return ${firstLowerName}Service.delete(id);
    }

    @ResponseBody
    @GetMapping(value = "/query/{id}")
    public CommonResult queryById(@PathVariable int id) {
        return ${firstLowerName}Service.queryById(id);
    }

    @ResponseBody
    @GetMapping(value = "/page/{offset}/{limit}")
    public PageInfo queryPage(@PathVariable int offset,@PathVariable int limit) {
        return ${firstLowerName}Service.queryPage(offset,limit);
    }

    @ResponseBody
    @GetMapping(value = "/listMap")
    public List<Map<String,Object>> queryToListMap(@RequestParam Map<String,Object> params) {
        return ${firstLowerName}Service.queryToListMap(params);
    }
}
```