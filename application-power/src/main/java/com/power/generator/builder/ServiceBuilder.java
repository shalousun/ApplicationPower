package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

/**
 * 生成service层代码
 *
 * @author sunyu 2016/12/7.
 */
public class ServiceBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(entityName);//类名
        String templateName = GeneratorProperties.getDbTemplatePath()+"/"+ConstVal.TPL_SERVICE;
        Template serviceTemplate = BeetlTemplateUtil.getByName(templateName);
        serviceTemplate.binding(GeneratorConstant.PRIMARY_KEY_TYPE, tableInfo.getPrimaryKeyType());
        serviceTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        serviceTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        serviceTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return serviceTemplate.render();
    }
}
