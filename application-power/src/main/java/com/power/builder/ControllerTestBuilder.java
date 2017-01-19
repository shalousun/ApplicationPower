package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.DateTimeUtil;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

/**
 * 创建controller层接口测试
 * @author sunyu on 2016/12/7.
 */
public class ControllerTestBuilder {
    /**
     * 表名
     * @param tableName
     * @return
     */
    public String generateControllerTest(String tableName){
        String entitySimpleName = StringUtils.toCapitalizeCamelCase(tableName);//类名
        String firstLowName = StringUtils.firstToLowerCase(entitySimpleName);//类实例变量名
        Template controllerTemplate = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_CONTROLLER_TEST);
        controllerTemplate.binding(GeneratorConstant.AUTHOR,System.getProperty("user.name"));//作者
        controllerTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME,firstLowName);
        controllerTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME,entitySimpleName);//类名
        controllerTemplate.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());//基包名
        controllerTemplate.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());//创建时间
        return controllerTemplate.render();
    }
}
