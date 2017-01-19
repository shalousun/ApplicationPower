package com.power.builder;


import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.database.Column;
import com.power.database.DbProvider;
import com.power.database.TableInfo;
import com.power.factory.DbProviderFactory;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.DateTimeUtil;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

import java.util.*;

/**
 * @author sunyu on 2016/12/6.
 */
public class ModelBuilder {


    /**
     * 生成model
     * @param tableInfo
     * @return
     */
    public String generateModel(TableInfo tableInfo){
        String tableName = tableInfo.getName();
        String tableTemp = StringUtils.removePrefix(tableName, GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtils.toCapitalizeCamelCase(tableTemp);//类名
        DbProvider dbProvider = new DbProviderFactory().getInstance();
        Map<String, Column> columnMap = dbProvider.getColumnsInfo(tableName);
        String fields = generateFields(columnMap);
        String gettersAndSetters = generateSetAndGetMethods(columnMap);
        String imports = generateImport(columnMap);
        Template template = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_ENTITY);
        template.binding(GeneratorConstant.AUTHOR,System.getProperty("user.name"));//作者
        template.binding(GeneratorConstant.ENTITY_SIMPLE_NAME,entitySimpleName);//类名
        template.binding(GeneratorConstant.BASE_PACKAGE,GeneratorProperties.basePackage());//基包名
        template.binding(GeneratorConstant.FIELDS,fields);//字段
        template.binding(GeneratorConstant.GETTERS_AND_SETTERS,gettersAndSetters);//get和set方法
        template.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());//创建时间
        template.binding(GeneratorConstant.TABLE_COMMENT,tableInfo.getRemarks());//表注释
        template.binding("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        template.binding("modelImports",imports);
        return template.render();
    }

    /**
     * 生成model的字段
     * @param columnMap
     * @return
     */
    private String generateFields(Map<String,Column> columnMap){
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,Column> entry:columnMap.entrySet()){
            Column column = entry.getValue();
            if(StringUtils.isNotEmpty(column.getRemarks())){
                builder.append("	//").append(column.getRemarks()).append("\n");
            }
            if("Timestamp".equals(column.getColumnType())){
                builder.append("	@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
            }
            builder.append("	private ").append(column.getColumnType()).append(" ");
            builder.append(StringUtils.underlineToCamel(column.getColumnName())).append(";\n");
        }
        return builder.toString();
    }

    /**
     * 生成model导包块
     * @param columnMap
     * @return
     */
    private String generateImport(Map<String,Column> columnMap){
        StringBuilder builder = new StringBuilder();

        List<String> list = new ArrayList<>();
        boolean flag = true;
        for(Map.Entry<String,Column> entry:columnMap.entrySet()){
           String type = entry.getValue().getColumnType();
            if("BigDecimal".equals(type)){
                list.add("import java.math.BigDecimal;\n");
            }
            if("Date".equals(type)){
                list.add("import java.sql.Date;\n");
            }
            if("Timestamp".equals(type)){
                list.add("import java.sql.Timestamp;\n");
                flag = true;
            }
            if("Time".equals(type)){
                list.add("import java.sql.Time;\n");
            }
        }
        if(flag){
            list.add("import com.fasterxml.jackson.annotation.JsonFormat;\n\n");
            Collections.reverse(list);
        }
        Set<String> set = new HashSet<>();
        set.addAll(list);
        for(String str:set){
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * 生成get和set方法
     * @param columnMap
     * @return
     */
    private String generateSetAndGetMethods(Map<String,Column> columnMap){
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,Column> entry:columnMap.entrySet()){
            Column column = entry.getValue();
            String columnName = column.getColumnName();
            //get
            builder.append("	public ").append(column.getColumnType()).append(" get");
            builder.append(StringUtils.toCapitalizeCamelCase(column.getColumnName())).append("(){\n");
            builder.append("		return ").append(StringUtils.underlineToCamel(columnName)).append(";\n");
            builder.append("	}\n");
            //set
            builder.append("	public void set").append(StringUtils.toCapitalizeCamelCase(columnName));
            builder.append("(").append(column.getColumnType()).append(" ").append(StringUtils.underlineToCamel(columnName));
            builder.append("){\n");
            builder.append("		this.").append(StringUtils.underlineToCamel(columnName));
            builder.append(" = ").append(StringUtils.underlineToCamel(columnName)).append(";\n");
            builder.append("	}\n");
        }
        return builder.toString();
    }

}
