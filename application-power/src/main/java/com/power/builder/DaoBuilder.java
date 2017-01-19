package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.DateTimeUtil;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

/**
 * 生成dao层
 * @author sunyu 2016/12/7.
 */
public class DaoBuilder {
    /**
     * 实体名
     * @param entityName
     * @return
     */
    public String generateDao(String entityName){
        String entitySimpleName = StringUtils.toCapitalizeCamelCase(entityName);//类名
        Template daoTemplate = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_DAO);
        daoTemplate.binding(GeneratorConstant.AUTHOR,System.getProperty("user.name"));//作者
        daoTemplate.binding(GeneratorConstant.ENTITY_SIMPLE_NAME,entitySimpleName);//类名
        daoTemplate.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());//基包名
        daoTemplate.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());//创建时间
        return daoTemplate.render();
    }
}
