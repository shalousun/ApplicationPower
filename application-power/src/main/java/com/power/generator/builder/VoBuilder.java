package com.power.generator.builder;

import com.boco.common.util.DateTimeUtil;
import com.boco.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.Map;
import java.util.UUID;

/**
 * class description<br/>
 *
 * @author fujunsu
 * @version 1.0
 * @date 2018/3/29 13:57
 * @since JDK 1.8+
 */
public class VoBuilder extends ModelBuilder implements IBuilder {

    /**
     * 生成model
     *
     * @param tableInfo tableInfo
     * @return String
     */
    @Override
    public String generateTemplate(TableInfo tableInfo, Map<String, Column> columnMap) {
        String tableName = tableInfo.getName();
        String tableTemp = StringUtil.removePrefix(tableName, GeneratorProperties.tablePrefix());
        //类名
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);
        //DbProvider dbProvider = new DbProviderFactory().getInstance();
        //Map<String, Column> columnMap = dbProvider.getColumnsInfo(tableName);

        String fields = generateFields(columnMap);
        String gettersAndSetters = generateSetAndGetMethods(columnMap);
        String imports = generateImport(columnMap);
        String toString = generateToStringMethod(entitySimpleName, columnMap);
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_VO);
        // swagger 注解
        boolean isSwagger=GeneratorProperties.isSwagger();
        if (isSwagger) {
            String description = tableInfo.getRemarks();
            String apiModelProperty = generateApiModelProperty(entitySimpleName, description);
            // swagger 注解
            template.binding(GeneratorConstant.API_MODEL_PROPERTY, apiModelProperty);
        }
        //作者
        template.binding(GeneratorConstant.AUTHOR, System.getProperty("user.name"));
        //类名
        template.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);
        //基包名
        template.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());
        //字段
        template.binding(GeneratorConstant.FIELDS, fields);
        //get和set方法
        template.binding(GeneratorConstant.GETTERS_AND_SETTERS, gettersAndSetters);
        //创建时间
        template.binding(GeneratorConstant.CREATE_TIME, DateTimeUtil.getTime());
        //表注释
        template.binding(GeneratorConstant.TABLE_COMMENT, tableInfo.getRemarks());

        template.binding(GeneratorConstant.TO_STRING, toString);
        template.binding("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        template.binding("modelImports", imports);
        return template.render();
    }


    @Override
    protected String generateFields(Map<String, Column> columnMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            Column column = entry.getValue();

            // swagger 注解
            boolean isSwagger=GeneratorProperties.isSwagger();
            if (isSwagger) {
                if (StringUtil.isNotEmpty(column.getRemarks())) {
                    builder.append("	/** \n	* ").append(column.getRemarks()).append("\n	*/\n");
                    builder.append("	@ApiModelProperty(value = \"").append(column.getRemarks())
                            .append("\", dataType = \"").append(column.getColumnType()).append("\", required = ").append(column.isNullable()).append(")\n");
                }
            }
            if ("Timestamp".equals(column.getColumnType())) {
                builder.append("	@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\",timezone = \"GMT+8\")\n");
            }
            builder.append("	private ").append(column.getColumnType()).append(" ");
            builder.append(StringUtil.underlineToCamel(column.getColumnName()));
            builder.append(";\n");
        }
        return builder.toString();
    }

    /**
     * 生成 apiModelProperty
     *
     * @param apiModelProperty apiModelProperty
     * @param description      description
     * @return String like @ApiModel(value = "apiModelProperty", description = "description")
     */
    protected String generateApiModelProperty(String apiModelProperty, String description) {
        if (!StringUtil.isNotEmpty(description)) {
            description = "no description, Please check!!!";
        }
        return "@ApiModel(value = \"" + apiModelProperty + "\", description = \"" + description + "\")";
    }

}
