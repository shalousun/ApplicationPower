package com.power.generator.builder;

import com.power.common.util.RandomUtil;
import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.Map;

/**
 * 创建controller层接口测试
 *
 * @author sunyu on 2016/12/7.
 */
public class ControllerTestBuilder implements IBuilder {


    private static final String controllerTestParams = "params";

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        Map<String, Column> columnMap = tableInfo.getColumnsInfo();
        //实体名需要移除表前缀
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);//类实例变量名
        Template controllerTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_CONTROLLER_TEST);
        controllerTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        controllerTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        controllerTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        controllerTemplate.binding(controllerTestParams, generateParams(columnMap));
        controllerTemplate.binding(GeneratorProperties.getGenerateMethods());
        return controllerTemplate.render();
    }

    /**
     * @return
     */
    private String generateParams(Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            Column column = entry.getValue();
            builder.append("\n");
            builder.append("            .param(\"").append(StringUtil.underlineToCamel(column.getColumnName()))
                    .append("\",\"").append(RandomUtil.randomValueByType(column.getColumnType()))
                    .append("\")");

        }
        return builder.toString();
    }
}
