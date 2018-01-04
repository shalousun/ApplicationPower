package com.power.builder;

import com.boco.common.util.StringUtil;
import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.database.Column;
import com.power.database.TableInfo;
import com.power.utils.BeetlTemplateUtil;

import com.power.utils.GeneratorProperties;
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
