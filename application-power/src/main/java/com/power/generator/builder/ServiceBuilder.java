package com.power.generator.builder;

import com.boco.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.GeneratorProperties;
import com.power.generator.utils.BeetlTemplateUtil;

import org.beetl.core.Template;

import java.util.Map;

/**
 * 生成service层代码
 *
 * @author sunyu 2016/12/7.
 */
public class ServiceBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo, Map<String, Column> columnMap) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(entityName);//类名
        Template serviceTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_SERVICE);
        serviceTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        serviceTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        serviceTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return serviceTemplate.render();
    }
}
