package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpringBootMybatisCfgBuilder {

    public String createMybatisCfg(Set<String> ds) {
        String basePackage = GeneratorProperties.basePackage();
        Map<String, String> code = createCode(ds);
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_SPRINGBOOT_MYBATIS_CFG);
        template.binding(GeneratorConstant.COMMON_VARIABLE);
        template.binding("mappingDir", basePackage.replaceAll("[.]", "/"));
        template.binding(code);
        return template.render();
    }


    public Map<String, String> createCode(Set<String> ds) {
        Map<String, String> codes = new HashMap<>();

        StringBuilder sourceBuilder = new StringBuilder();
        StringBuilder sessionBuilder = new StringBuilder();

        StringBuilder dynamicDataSource = new StringBuilder();
        StringBuilder targetDataSources = new StringBuilder();

        StringBuilder sqlSessionTemplate = new StringBuilder();

        StringBuilder sqlSessionFactoryMap = new StringBuilder();
        int i = 0;
        for (String str : ds) {
            String dataSourceName = "dataSource" + StringUtil.firstToUpperCase(str);
            String sessionName = "sqlSessionFactory" + StringUtil.firstToUpperCase(str);
            String factoryName = "factory" + StringUtil.firstToUpperCase(str);
            if (i == 0) {
                sourceBuilder.append("@Primary\n");
                codes.put("defaultDataSource", dataSourceName);
                codes.put("defaultSqlSessionFactory", factoryName);
            }

            sourceBuilder.append("    @Bean(name = \"").append(dataSourceName).append("\")\n");
            sourceBuilder.append("    public DataSource ").append(dataSourceName).append("(Environment env) {\n");
            sourceBuilder.append("        String prefix = \"spring.datasource.druid.").append(str).append(".\";\n");
            sourceBuilder.append("        return getDataSource(env,prefix,\"").append(str).append("\");\n");
            sourceBuilder.append("    }\n\n");


            sessionBuilder.append("    @Bean(name = \"").append(sessionName).append("\")\n");
            sessionBuilder.append("    public SqlSessionFactory ").append(sessionName).append("(@Qualifier(\"");
            sessionBuilder.append(dataSourceName).append("\") DataSource dataSource)\n");
            sessionBuilder.append("        throws Exception {\n");
            sessionBuilder.append("        return createSqlSessionFactory(dataSource);\n");
            sessionBuilder.append("    }\n\n");


            if (i < ds.size() - 1) {
                dynamicDataSource.append("@Qualifier(\"").append(dataSourceName).append("\")DataSource ");
                dynamicDataSource.append(dataSourceName).append(",");

                sqlSessionTemplate.append("@Qualifier(\"").append(sessionName).append("\")SqlSessionFactory ");
                sqlSessionTemplate.append(factoryName).append(",");
            } else {
                dynamicDataSource.append("@Qualifier(\"").append(dataSourceName).append("\")DataSource ");
                dynamicDataSource.append(dataSourceName);

                sqlSessionTemplate.append("@Qualifier(\"").append(sessionName).append("\")SqlSessionFactory ");
                sqlSessionTemplate.append(factoryName);
            }


            targetDataSources.append("        targetDataSources.put(\"").append(str).append("\",").append(dataSourceName);
            targetDataSources.append(");\n");


            sqlSessionFactoryMap.append("        sqlSessionFactoryMap.put(\"").append(str).append("\",");
            sqlSessionFactoryMap.append(factoryName).append(");\n");


            i++;
        }
        codes.put("dataSource", sourceBuilder.toString());
        codes.put("sqlSessionFactory", sessionBuilder.toString());
        codes.put("dynamicDataSource", dynamicDataSource.toString());
        codes.put("sqlSessionTemplate", sqlSessionTemplate.toString());
        codes.put("targetDataSources", targetDataSources.toString());
        codes.put("sqlSessionFactoryMap", sqlSessionFactoryMap.toString());
        return codes;
    }


}
