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
 * 生成controller层代码
 *
 * @author sunyu on 2016/12/7.
 */
public class ControllerBuilder implements IBuilder{


    @Override
    public String generateTemplate(TableInfo tableInfo, Map<String, Column> columnMap) {
        String tableTemp = StringUtil.removePrefix(tableInfo.getName(), GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);//类实例变量名
        Template controllerTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_CONTROLLER);
        controllerTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        controllerTemplate.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        controllerTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        controllerTemplate.binding(GeneratorProperties.getGenerateMethods());//绑定需要生成的方法
        return controllerTemplate.render();
    }


}
