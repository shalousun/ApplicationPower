package com.power.generator.builder;

import com.boco.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;

import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.Map;

/**
 * 生成service层实现模板
 *
 * @author sunyu on 2016/12/7.
 */
public class ServiceImplBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo, Map<String, Column> columnMap) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(entityName);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);
        Template serviceImplTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_SERVICEIMPL);
        serviceImplTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        serviceImplTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        serviceImplTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        serviceImplTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return serviceImplTemplate.render();
    }
}
