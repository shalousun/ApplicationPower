package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

/**
 * 创建service层测试框架
 *
 * @author sunyu on 2016/12/7.
 */
public class ServiceTestBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);
        String templateName = GeneratorProperties.getDbTemplatePath()+"/"+ConstVal.TPL_SERVICE_TEST;
        Template serviceTestTemplate = BeetlTemplateUtil.getByName(templateName);
        serviceTestTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        serviceTestTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        serviceTestTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        serviceTestTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return serviceTestTemplate.render();
    }
}
