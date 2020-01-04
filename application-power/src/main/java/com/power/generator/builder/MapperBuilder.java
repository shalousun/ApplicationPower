package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.database.Column;
import com.power.generator.database.TableInfo;
import com.power.generator.database.TypeConvert;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;
import org.beetl.core.Template;

import java.util.Map;

/**
 * 创建mybatis mapper文件
 *
 * @author sunyu on 2016/12/7.
 */
public class MapperBuilder implements IBuilder {

    @Override
    public String generateTemplate(TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        String tableTemp = StringUtil.removePrefix(tableName, GeneratorProperties.tablePrefix());
        String entitySimpleName = StringUtil.toCapitalizeCamelCase(tableTemp);//类名
        String firstLowName = StringUtil.firstToLowerCase(entitySimpleName);
        Map<String, Column> columnMap = tableInfo.getColumnsInfo();
        String insertSql = generateInsertSql(columnMap, tableName);
        String batchInsertSql = generateBatchInsertSql(columnMap, tableName);
        String updateSql = generateConditionUpdateSql(tableInfo);
        String batchUpdateSql = generateBatchUpdateSql(tableInfo);
        String selectSql = generateSelectSql(tableInfo);
        String results = generateResultMap(columnMap);
        String primaryKey = getPrimaryKey(columnMap);
        String template = GeneratorProperties.getDbTemplatePath()+"/"+ConstVal.TPL_MAPPER;
        Template mapper = BeetlTemplateUtil.getByName(template);
        String idType = TypeConvert.mybatisType(tableInfo.getPrimaryKeyType());
        mapper.binding(GeneratorConstant.PRIMARY_KEY_TYPE, idType);
        mapper.binding(GeneratorConstant.FIRST_LOWER_NAME, firstLowName);
        mapper.binding(GeneratorConstant.ENTITY_SIMPLE_NAME, entitySimpleName);//类名
        mapper.binding(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());//基包名
        mapper.binding(GeneratorConstant.INSERT_SQL, insertSql);
        mapper.binding(GeneratorConstant.BATCH_INSERT_SQL, batchInsertSql);
        mapper.binding(GeneratorConstant.UPDATE_SQL, updateSql);
        // mapper.binding(GeneratorConstant.BATCH_UPDATE_SQL,batchUpdateSql);
        mapper.binding(GeneratorConstant.SELECT_SQL, selectSql);
        mapper.binding(GeneratorConstant.RESULT_MAP, results);
        mapper.binding(GeneratorConstant.IS_RESULT_MAP, GeneratorProperties.getResultMap());
        mapper.binding(GeneratorConstant.TABLE_NAME, tableName);
        mapper.binding(GeneratorConstant.PRIMARY_KEY, primaryKey);
        mapper.binding(GeneratorProperties.getGenerateMethods());//过滤方法
        return mapper.render();
    }

    /**
     * 获取主键
     *
     * @param columnMap
     * @return
     */
    private String getPrimaryKey(Map<String, Column> columnMap) {
        String primaryKey = null;
        Column column;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            column = entry.getValue();
            if (column.isPrimaryKey()) {
                primaryKey = entry.getKey();
                break;
            }
        }
        return primaryKey;
    }

    /**
     * 生成insert语句，过滤掉则增列
     *
     * @param columnMap
     * @param tableName
     * @return
     */
    private String generateInsertSql(Map<String, Column> columnMap, String tableName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(tableName).append("(\n");
        StringBuilder insertValues = new StringBuilder();
        int i = 0;
        int size = columnMap.size();
        Column column;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            column = entry.getValue();
            if (!column.isAutoIncrement()) {
                if (i < size - 1) {
                    insertSql.append("			").append(entry.getKey()).append(",\n");
                    insertValues.append("			#{").append(StringUtil.underlineToCamel(entry.getKey())).append("},\n");
                } else {
                    insertSql.append("			").append(entry.getKey()).append("\n");
                    insertValues.append("			#{").append(StringUtil.underlineToCamel(entry.getKey())).append("}\n");
                }
            }
            i++;
        }
        insertSql.append("		) values (\n");
        insertSql.append(insertValues);
        insertSql.append("		)");
        return insertSql.toString();
    }

    /**
     * 生成批量插入的sql
     *
     * @param columnMap
     * @param tableName
     * @return
     */
    private String generateBatchInsertSql(Map<String, Column> columnMap, String tableName) {
        StringBuilder batchInsertSql = new StringBuilder();
        batchInsertSql.append("insert into ").append(tableName).append("(\n");
        StringBuilder insertValues = new StringBuilder();
        int counter = 0;
        int size = columnMap.size();
        Column column;
        String key;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            column = entry.getValue();
            key = entry.getKey();
            if (!column.isAutoIncrement()) {
                if (counter < size - 1) {
                    batchInsertSql.append("			").append(key).append(",\n");
                    insertValues.append("			#{item.").append(StringUtil.underlineToCamel(key)).append("},\n");
                } else {
                    batchInsertSql.append("			").append(key).append("\n");
                    insertValues.append("			#{item.").append(StringUtil.underlineToCamel(key)).append("}\n");
                }
            }
            counter++;
        }
        batchInsertSql.append("		) values\n");
        batchInsertSql.append("        <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">\n");
        batchInsertSql.append("            (\n").append(insertValues);
        batchInsertSql.append("            )\n");
        batchInsertSql.append("        </foreach>");

        return batchInsertSql.toString();
    }

    /**
     * 生成update语句,过滤掉自增列
     *
     * @param columnMap
     * @param tableName
     * @return
     */
    private String generateUpdateSql(Map<String, Column> columnMap, String tableName) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(tableName).append(" set\n");
        int i = 0;
        int size = columnMap.size();
        Column column;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            column = entry.getValue();
            if (!column.isAutoIncrement()) {
                if (i < size - 1) {
                    updateSql.append("			").append(entry.getKey()).append(" = #{");
                    updateSql.append(StringUtil.underlineToCamel(entry.getKey())).append("},\n");
                } else {
                    updateSql.append("			").append(entry.getKey()).append(" = #{");
                    updateSql.append(StringUtil.underlineToCamel(entry.getKey())).append("}");
                }
            }
            i++;
        }
        return updateSql.toString();
    }

    /**
     * 生成update语句,过滤掉自增列,使用trim
     *
     * @param tableInfo
     * @return
     */
    private String generateConditionUpdateSql(TableInfo tableInfo) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(tableInfo.getName()).append("\n");
        updateSql.append("\t\t<trim prefix=\"set\" suffixOverrides=\",\">\n");
        Column column;
        for (Map.Entry<String, Column> entry : tableInfo.getColumnsInfo().entrySet()) {
            column = entry.getValue();
            String camelKey = StringUtil.underlineToCamel(entry.getKey());
            if (!column.isPrimaryKey()) {
                updateSql.append("			").append("<if test=\"").append(camelKey).append("!=null\">");
                updateSql.append(entry.getKey()).append(" = #{");
                updateSql.append(StringUtil.underlineToCamel(entry.getKey())).append("},</if>\n");
            }
        }
        String pk = tableInfo.getPrimaryKey();
        String camelPk = StringUtil.underlineToCamel(pk);
        updateSql.append("\t\t</trim>\n");
        updateSql.append("\t\twhere ").append(pk).append(" = #{").append(camelPk).append("}");
        return updateSql.toString();
    }

    /**
     * 批量修改的语句
     *
     * @param tableInfo
     * @return
     */
    private String generateBatchUpdateSql(TableInfo tableInfo) {
        String primaryKeyName = tableInfo.getPrimaryKey();
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(tableInfo.getName()).append("\n");
        updateSql.append("\t\t<trim prefix=\"set\" suffixOverrides=\",\">\n");
        Column column;
        for (Map.Entry<String, Column> entry : tableInfo.getColumnsInfo().entrySet()) {
            column = entry.getValue();
            String camelKey = StringUtil.underlineToCamel(entry.getKey());
            if (!column.isPrimaryKey()) {
                updateSql.append("			").append("<if test=\"").append(camelKey).append("!=null\">");
                updateSql.append(entry.getKey()).append(" = #{item.");
                updateSql.append(StringUtil.underlineToCamel(entry.getKey())).append("},</if>\n");
            }
        }
        updateSql.append("\t\t</trim>\n").append("\t\twhere ").append(primaryKeyName).append(" in\n")
                .append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\">\n")
                .append("\t\t\t${item.").append(primaryKeyName).append("}\n")
                .append("\t\t</foreach>");
        return updateSql.toString();
    }

    /**
     * 生成查询语句
     *
     * @param tableInfo
     * @return
     */
    private String generateSelectSql(TableInfo tableInfo) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select \n");
        int i = 0;
        Map<String, Column> columnMap = tableInfo.getColumnsInfo();
        int size = columnMap.size();
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            if (i < size - 1) {
                selectSql.append("			").append(entry.getKey()).append(",\n");
            } else {
                selectSql.append("			").append(entry.getKey()).append("\n");
            }
            i++;
        }
        selectSql.append(" 		from ").append(tableInfo.getName());
        return selectSql.toString();
    }

    /**
     * mapper映射文件中resultMap下的result
     *
     * @param columnMap
     * @return
     */
    private String generateResultMap(Map<String, Column> columnMap) {
        StringBuilder results = new StringBuilder();
        String property;
        for (Map.Entry<String, Column> entry : columnMap.entrySet()) {
            property = StringUtil.underlineToCamel(entry.getKey());
            results.append("\t\t<result property=\"").append(property).append("\" column=\"");
            results.append(entry.getKey()).append("\" />\n");
        }
        return results.toString();
    }
}