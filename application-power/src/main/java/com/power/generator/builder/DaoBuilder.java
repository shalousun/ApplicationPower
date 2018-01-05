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
import java.util.Set;

/**
 * 生成dao层
 *
 * @author sunyu 2016/12/7.
 */
public class DaoBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo table, Map<String, Column> columnMap) {
        String tableTemp = StringUtil.removePrefix(table.getName(), GeneratorProperties.tablePrefix());
        String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(entityName);//类名
        Template daoTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_DAO);
        daoTemplate.binding(GeneratorConstant.COMMON_VARIABLE);
        daoTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        daoTemplate.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        daoTemplate.binding(GeneratorConstant.IS_MULTIPLE_DATA_SOURCE,GeneratorProperties.isMultipleDataSource());
        Set<String> dataSource = GeneratorProperties.getMultipleDataSource();
        int i = 0;
        for(String str:dataSource){
            if(i==0){
                daoTemplate.binding("defaultDataSource",str.toUpperCase());
                break;
            }
        }
        return daoTemplate.render();
    }
}
