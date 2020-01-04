package com.power.generator.utils;

import com.power.common.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author sunyu 2016/12/5.
 */
public class GeneratorProperties {

    private static PropertiesUtils props = new PropertiesUtils("generator.properties");

    /**
     * 是否需要添加注释
     *
     * @return
     */
    public static Boolean comment() {
        return Boolean.valueOf(props.getProperty("generator.comment"));
    }

    /**
     * 获取基包名
     *
     * @return
     */
    public static String basePackage() {
        return props.getProperty("generator.basePackage");
    }

    /**
     * 获取表前缀
     *
     * @return
     */
    public static String tablePrefix() {
        return props.getProperty("generator.table.prefix");
    }

    /**
     * 过滤表
     *
     * @return
     */
    public static String tableFilterPrefix() {
        return props.getProperty("generator.table.filter.prefix");
    }

    /**
     * 获取应用名称
     *
     * @return
     */
    public static String applicationName() {
        return props.getProperty("generator.applicationName");
    }

    /**
     * 获取需要生成的代码层
     *
     * @return
     */
    public static String layers() {
        return props.getProperty("generator.layers");
    }

    /**
     * 代码数据目录
     *
     * @return
     */
    public static String outDir() {
        return props.getProperty("generator.outDir");
    }

    /**
     * 是否开启mybatis二级缓存
     *
     * @return
     */
    public static String enableCache() {
        return props.getProperty("generator.enableCache");
    }

    /**
     * 获取需要生成代码的表名
     *
     * @return
     */
    public static String getTableName() {
        return props.getProperty("generator.table.name");
    }

    /**
     * 是否需要resultMap
     *
     * @return
     */
    public static Boolean getResultMap() {
        return Boolean.valueOf(props.getProperty("generator.resultMap"));
    }

    /**
     * 是否采用assembly打包
     *
     * @return
     */
    public static Boolean getAssembly() {
        return Boolean.valueOf(props.getProperty("generator.package.assembly"));
    }

    /**
     * 分布式事务
     *
     * @return
     */
    public static Boolean isJTA() {
        return Boolean.valueOf(props.getProperty("generator.jta")) || isMultipleDataSource();
    }

    /**
     * 是否需要生成docker配置
     *
     * @return
     */
    public static Boolean useDocker() {
        return Boolean.valueOf(props.getProperty("generator.docker"));
    }

    /**
     * 使用gradle构建
     *
     * @return
     */
    public static Boolean useGradle() {
        if ("gradle".equals(props.getProperty("generator.build.tool"))) {
            return true;
        }
        return false;
    }

    /**
     * 使用maven构建
     *
     * @return
     */
    public static Boolean useMaven() {
        return !useGradle();
    }

    /**
     * 获取多数据源
     *
     * @return
     */
    public static Set<String> getMultipleDataSource() {
        String datasourceStr = props.getProperty("generator.multiple.datasource");
        Set<String> dataSourceSet = new LinkedHashSet<>();
        if (StringUtil.isNotEmpty(datasourceStr)) {
            String[] dataSources = datasourceStr.split(",");
            for (String str : dataSources) {
                dataSourceSet.add(str);
            }
        }
        return dataSourceSet;
    }

    public static boolean isMultipleDataSource() {
        return getMultipleDataSource().size() > 0;
    }

    /**
     * 获取需要生成的方法
     *
     * @return
     */
    public static Map<String, Boolean> getGenerateMethods() {
        String methodsStr = props.getProperty("generator.methods");
        if (StringUtil.isEmpty(methodsStr)) {
            throw new RuntimeException("generator.methods can not be null or ''");
        }
        String[] methods = methodsStr.split(",");
        //约定的方法，
        Map<String, Boolean> map = new HashMap<>();
        map.put("add", false);
        map.put("delete", false);
        map.put("update", false);
        map.put("query", false);
        map.put("page", false);
        map.put("queryToListMap", false);
        for (String str : methods) {
            map.put(str, true);
        }
        return map;
    }

    /**
     * 系统实现的日志配置
     *
     * @return
     */
    public static String getLogConfig() {
        return props.getProperty("generator.application.logConfig");
    }

    /**
     * 获取lombok配置
     *
     * @return
     */
    public static Boolean useLombok() {
        return Boolean.valueOf(props.getProperty("generator.lombok"));
    }

    /**
     * 是否需要生成db相关代码
     *
     * @return
     */
    public static Boolean useDb() {
        return Boolean.valueOf(props.getProperty("generator.useDb"));
    }

    /**
     * 是否需要生成db相关代码
     *
     * @return
     */
    public static Boolean useMyBatisPlus() {
        return Boolean.valueOf(props.getProperty("generator.useMybatisPlus"));
    }

    public static String  getDbTemplatePath(){
        if(useMyBatisPlus()){
            return "mybatis-plus";
        }else {
            return "mybatis";
        }
    }

    /**
     * 获取app版本号
     * @return
     */
    public static String appVersion() {
        return props.getProperty("generator.application.version");
    }
}
