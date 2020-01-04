package com.power.generator.builder;


import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.*;

/**
 * @author sunyu on 2016/12/6.
 */
public class ModelBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        String tableTemp = StringUtil.removePrefix(tableName, GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);//类名
        Map<String, Column> columnMap = tableInfo.getColumnsInfo();
        String fields = generateFields(columnMap);
        String gettersAndSetters = this.generateSetAndGetMethods(columnMap);
        String imports = this.generateImport(columnMap);
        String toString = this.generateToStringMethod(entitySimpleName, columnMap);
        String templateName = GeneratorProperties.getDbTemplatePath()+"/"+ConstVal.TPL_ENTITY;
        Template template = BeetlTemplateUtil.getByName(templateName);
        template.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        template.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        template.binding(GeneratorConstant.FIELDS, fields);//字段
        template.binding(GeneratorConstant.GETTERS_AND_SETTERS, gettersAndSetters);//get和set方法
        template.binding(GeneratorConstant.TABLE_COMMENT, tableInfo.getRemarks());//表注释
        template.binding(GeneratorConstant.TO_STRING, toString);
        template.binding(GeneratorConstant.LOMBOK, GeneratorProperties.useLombok());
        template.binding(GeneratorConstant.TABLE_NAME,tableName);
        template.binding("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        template.binding("modelImports", imports);
        return template.render();
    }


    /**
     * 生成model的字段
     *
     * @param columnMap
     * @return
     */
    private String generateFields(Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            Column column = entry.getValue();
            if (StringUtil.isNotEmpty(column.getRemarks())) {
                builder.append("	/** \n");
                builder.append("	 * ").append(column.getRemarks()).append("\n");
                builder.append("	 */\n ");
            }
            if ("Timestamp".equals(column.getColumnType())) {
                builder.append("	@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
            }
            builder.append("	private ").append(column.getColumnType()).append(" ");
            builder.append(StringUtil.underlineToCamel(column.getColumnName()));
            builder.append(";\n\n");
        }
        return builder.toString();
    }

    /**
     * 生成model导包块
     *
     * @param columnMap
     * @return
     */
    private String generateImport(Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();

        List<String> list = new ArrayList<>();
        boolean flag = false;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            String type = entry.getValue().getColumnType();
            if ("BigDecimal".equals(type)) {
                list.add("import java.math.BigDecimal;\n");
            }
            if ("Date".equals(type)) {
                list.add("import java.sql.Date;\n");
            }
            if ("Timestamp".equals(type)) {
                list.add("import java.sql.Timestamp;\n");
                flag = true;
            }
            if ("Time".equals(type)) {
                list.add("import java.sql.Time;\n");
            }
        }
        if (flag) {
            list.add("import com.fasterxml.jackson.annotation.JsonFormat;\n\n");
            Collections.reverse(list);
        }
        Set<String> set = new HashSet<>();
        set.addAll(list);
        for (String str : set) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * 生成get和set方法
     *
     * @param columnMap
     * @return
     */
    private String generateSetAndGetMethods(Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            Column column = entry.getValue();
            String columnName = column.getColumnName();
            //get
            builder.append("	public ").append(column.getColumnType()).append(" get");
            builder.append(StringUtil.toCapitalizeCamelCase(column.getColumnName())).append("() {\n");
            builder.append("		return ").append(StringUtil.underlineToCamel(columnName)).append(";\n");
            builder.append("	}\n\n");
            //set
            builder.append("	public void set").append(StringUtil.toCapitalizeCamelCase(columnName));
            builder.append("(").append(column.getColumnType()).append(" ").append(StringUtil.underlineToCamel(columnName));
            builder.append(") {\n");
            builder.append("		this.").append(StringUtil.underlineToCamel(columnName));
            builder.append(" = ").append(StringUtil.underlineToCamel(columnName)).append(";\n");
            builder.append("	}\n\n");
        }
        return builder.toString();
    }

    /**
     * 生成model的toString方法
     *
     * @param className 类名
     * @param columnMap 类的字段map表
     * @return
     */
    private String generateToStringMethod(String className, Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("\"").append(className).append("{\" + \n");
        int index = 0;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            Column column = entry.getValue();
            String columnName = column.getColumnName();
            String fieldName = StringUtil.underlineToCamel(columnName);
            String type = entry.getValue().getColumnType();
            if (index < 1) {
                if ("String".equals(type)) {
                    builder.append("                \"").append(fieldName).append(" ='\"");
                    builder.append(" + ").append(fieldName).append(" +'\\''").append("+");
                } else {
                    builder.append("                \"").append(fieldName).append(" =\"");
                    builder.append(" + ").append(fieldName).append(" +\n");
                }
            } else {
                if ("String".equals(type)) {
                    builder.append("                \",").append(fieldName).append(" ='\"");
                    builder.append(" + ").append(fieldName).append(" + '\\''").append(" +\n");
                } else {
                    builder.append("                \",").append(fieldName).append(" =\"");
                    builder.append(" + ").append(fieldName).append(" +\n");
                }
            }
            index++;
        }
        builder.append("                '}';");
        return builder.toString();
    }

}
