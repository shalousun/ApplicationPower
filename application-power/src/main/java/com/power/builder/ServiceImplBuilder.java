package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.DateTimeUtil;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

/**
 * 生成service层实现模板
 * @author sunyu on 2016/12/7.
 */
public class ServiceImplBuilder {
    /**
     *
     * @param tableName
     * @return
     */
    public String generateServiceImpl(String tableName){
        String entitySimpleName = StringUtils.toCapitalizeCamelCase(tableName);//类名
        String firstLowName = StringUtils.firstToLowerCase(entitySimpleName);
        Template serviceImplTemplate = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_SERVICEIMPL);
        serviceImplTemplate.binding(GeneratorConstant.AUTHOR,System.getProperty("user.name"));//作者
        serviceImplTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME,firstLowName);
        serviceImplTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME,entitySimpleName);//类名
        serviceImplTemplate.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());//基包名
        serviceImplTemplate.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());//创建时间
        return serviceImplTemplate.render();
    }
}
