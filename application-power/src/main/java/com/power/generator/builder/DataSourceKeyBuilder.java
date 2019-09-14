package com.power.generator.builder;

import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.utils.BeetlTemplateUtil;
import org.beetl.core.Template;

import java.util.Set;

public class DataSourceKeyBuilder {

    public String builderDataSourceKey(Set<String> sourceKeys) {
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_DATASOURCE_KEY);
        template.binding(GeneratorConstant.COMMON_VARIABLE);
        template.binding("datasource", buildDataSource(sourceKeys));
        return template.render();
    }

    private String buildDataSource(Set<String> sourceKeys) {
        StringBuilder builder = new StringBuilder();
        for (String str : sourceKeys) {
            builder.append("    /** 数据库源").append(str).append("*/\n");
            builder.append("    public static final String ").append(str.toUpperCase());
            builder.append(" = \"").append(str).append("\";\n\n");
        }
        return builder.toString();
    }
}
