package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

/**
 * 生成service层实现模板
 *
 * @author sunyu on 2016/12/7.
 */
public class ServiceImplBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(entityName);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);
        String templateName = GeneratorProperties.getDbTemplatePath()+"/"+ConstVal.TPL_SERVICE_IMPL;
        Template serviceImplTemplate = BeetlTemplateUtil.getByName(templateName);
        serviceImplTemplate.binding(GeneratorConstant.PRIMARY_KEY_TYPE, tableInfo.getPrimaryKeyType());
        serviceImplTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        serviceImplTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        serviceImplTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        serviceImplTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return serviceImplTemplate.render();
    }
}
