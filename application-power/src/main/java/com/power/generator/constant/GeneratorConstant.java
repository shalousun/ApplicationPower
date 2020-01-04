package com.power.generator.constant;

import com.power.common.util.DateTimeUtil;
import com.power.generator.utils.GeneratorProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码创建工具，模板变量
 *
 * @author on 2016/12/7.
 */
public class GeneratorConstant {

    public static final Map<String, Object> COMMON_VARIABLE = new HashMap<>();
    /**
     * 代码作者
     */
    public static final String AUTHOR = "authorName";
    /**
     * 基包变量名
     */
    public static final String BASE_PACKAGE = "basePackage";
    /**
     * 类名称，不包含包
     */
    public static final String ENTITY_SIMPLE_NAME = "entitySimpleName";
    /**
     * 应用名称
     */
    public static final String APPLICATION_NAME = "applicationName";
    /**
     * 应用名称小写
     */
    public static final String APPLICATION_NAME_LOWER = "applicationNameLowerCase";
    /**
     * 表名常量
     */
    public static final String TABLE_NAME = "tableName";
    /**
     * 表注释
     */
    public static final String TABLE_COMMENT = "tableComment";
    /**
     * 类名第一个字母小写
     */
    public static final String FIRST_LOWER_NAME = "firstLowerName";
    /**
     * fields字段
     */
    public static final String FIELDS = "fields";
    /**
     * model模板中bean的get和set方法替换常量
     */
    public static final String GETTERS_AND_SETTERS = "gettersAndSetters";
    /**
     * 代码创建时间
     */
    public static final String CREATE_TIME = "createTime";
    /**
     * insert sql语句模板变量
     */
    public static final String INSERT_SQL = "insertSql";
    /**
     * 批量插入数据数据的sql模板变量
     */
    public static final String BATCH_INSERT_SQL = "batchInsertSql";
    /**
     * 更新语句的变量
     */
    public static final String UPDATE_SQL = "updateSql";
    /**
     * 批量更新语句
     */
    public static final String BATCH_UPDATE_SQL = "batchUpdateSql";
    /**
     * 查询语句
     */
    public static final String SELECT_SQL = "selectSql";
    /**
     * mybatis mapper文件result map部分
     */
    public static final String RESULT_MAP = "resultMap";
    /**
     * 是否需要生成result map
     */
    public static final String IS_RESULT_MAP = "isResultMap";
    /**
     * 是否开启驼峰转换
     */
    public static final String UNDERSCORE_TO_CAMEL_CASE = "underscoreToCamelCase";
    /**
     * 数据库主键
     */
    public static final String PRIMARY_KEY = "primaryKey";
    /**
     * 主键类型
     */
    public static final String PRIMARY_KEY_TYPE = "primaryKeyType";
    /**
     * toString方法
     */
    public static final String TO_STRING = "toString";
    /**
     *
     */
    public static final String IS_MULTIPLE_DATA_SOURCE = "isMultipleDataSource";
    /**
     * lombok
     */
    public static final String LOMBOK = "lombok";
    /**
     * SpringBoot启动名
     */
    public static final String APP_NAME = "SpringBootApp";
    /**
     * 是否需要创建数据库相关
     */
    public static final String USE_DB = "useDb";

    public static final String USE_MYBATIS_PLUS = "useMybatisPlus";

    /**
     * 版本号
     */
    public static final String VERSION = "version";

    static {
        COMMON_VARIABLE.put(GeneratorConstant.BASE_PACKAGE, GeneratorProperties.basePackage());
        COMMON_VARIABLE.put(GeneratorConstant.AUTHOR, System.getProperty("user.name"));
        COMMON_VARIABLE.put(GeneratorConstant.CREATE_TIME, DateTimeUtil.dateToStr(new Date(), "yyyy/MM/dd"));
        COMMON_VARIABLE.put(GeneratorConstant.APPLICATION_NAME, GeneratorProperties.applicationName());
        COMMON_VARIABLE.put(GeneratorConstant.APPLICATION_NAME_LOWER, GeneratorProperties.applicationName().toLowerCase());
        COMMON_VARIABLE.put(GeneratorConstant.USE_DB, GeneratorProperties.useDb());
        COMMON_VARIABLE.put(GeneratorConstant.VERSION,GeneratorProperties.appVersion());
        COMMON_VARIABLE.put(GeneratorConstant.USE_MYBATIS_PLUS,GeneratorProperties.useMyBatisPlus());

    }
}
