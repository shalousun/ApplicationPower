package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.DateTimeUtil;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

/**
 * 创建service层测试框架
 * @author sunyu on 2016/12/7.
 */
public class ServiceTestBuilder {

    /**
     * 创建service层测试框架
     * @param tableName
     * @return
     */
    public String generateServiceTest(String tableName){
        String entitySimpleName = StringUtils.toCapitalizeCamelCase(tableName);//类名
        String firstLowName = StringUtils.firstToLowerCase(entitySimpleName);
        Template serviceTestTemplate = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_SERVICE_TEST);
        serviceTestTemplate.binding(GeneratorConstant.AUTHOR,System.getProperty("user.name"));//作者
        serviceTestTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME,firstLowName);
        serviceTestTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME,entitySimpleName);//类名
        serviceTestTemplate.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());//基包名
        serviceTestTemplate.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());//创建时间
        return serviceTestTemplate.render();
    }
}
