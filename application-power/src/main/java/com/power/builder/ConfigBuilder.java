package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.PackageConfig;
import com.power.constant.ProjectConfig;
import com.power.database.DbProvider;
import com.power.database.TableInfo;
import com.power.utils.GeneratorProperties;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;

/**
 * 构建配置
 */
public class ConfigBuilder {
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;
    /**
     * 工程基础配置文件
     */
    private Map<String, String> baseConfigFilesPath;
    /**
     * 工程基础配置文件路径
     */
    private Map<String, String> baseConfigPathInfo;

    /**
     * 获取表信息
     */
    private List<TableInfo> tableInfo;

    public ConfigBuilder(DbProvider dataBaseInfo, PackageConfig packageConfig, ProjectConfig projectConfig) {
        //包配置
        if (null == packageConfig) {
            handlerPackage(GeneratorProperties.outDir(), new PackageConfig());
        } else {
            handlerPackage(GeneratorProperties.outDir(), packageConfig);
        }
        //创建工程所需配置
        if (null == projectConfig) {
            projectConfig = new ProjectConfig();
            handlerBaseConfigPath(GeneratorProperties.outDir(), projectConfig);
            handlerBaseConfigFiles(GeneratorProperties.outDir(), projectConfig);
        } else {
            projectConfig = new ProjectConfig();
            handlerBaseConfigPath(GeneratorProperties.outDir(), projectConfig);
            handlerBaseConfigFiles(GeneratorProperties.outDir(), projectConfig);
        }
        getTableInfoList(dataBaseInfo);

    }

    /**
     * 获取列信息
     * @param dbProvider
     */
    private void getTableInfoList(DbProvider dbProvider) {
        tableInfo = dbProvider.getTablesInfo(GeneratorProperties.getTableName());
    }

    /**
     * 处理包
     * @param outputDir
     * @param config
     */
    private void handlerPackage(String outputDir, PackageConfig config) {
        packageInfo = new HashMap<>(9);
        pathInfo = new LinkedHashMap<>(9);

        String basePackage = GeneratorProperties.basePackage();
        String fileSeparator = System.getProperty("file.separator");
        String applicationName = GeneratorProperties.applicationName();
        String basePath = outputDir + fileSeparator + applicationName;
        String javaDir = basePath + fileSeparator + ConstVal.JAVA_PATH;
        String testDir = basePath + fileSeparator + ConstVal.TEST_JAVA_PATH;
        String resourceDir = basePath + fileSeparator + ConstVal.RESOURCE_DIR;


        String layers = GeneratorProperties.layers();
        String[] layerArr = layers.split(",");
        Set<String> layerSet = new HashSet<>();
        layerSet.addAll(Arrays.asList(layerArr));
        for (String str : layerSet) {
            if (ConstVal.SERIVCE.equals(str)) {
                packageInfo.put(ConstVal.SERIVCE, joinPackage(basePackage, config.getService()));
                packageInfo.put(ConstVal.SERVICEIMPL, joinPackage(basePackage, config.getServiceImpl()));
                pathInfo.put(ConstVal.SERIVCE_PATH, joinPath(javaDir, packageInfo.get(ConstVal.SERIVCE)));
                pathInfo.put(ConstVal.SERVICEIMPL_PATH, joinPath(javaDir, packageInfo.get(ConstVal.SERVICEIMPL)));
            } else if (ConstVal.SERVICE_TEST.equals(str)) {
                packageInfo.put(ConstVal.SERVICE_TEST, joinPackage(basePackage, config.getService()));
                pathInfo.put(ConstVal.SERVICE_TEST_PATH, joinPath(testDir, packageInfo.get(ConstVal.SERVICE_TEST)));
            } else if (ConstVal.CONTROLLER_TEST.equals(str)) {
                packageInfo.put(ConstVal.CONTROLLER_TEST, joinPackage(basePackage, config.getController()));
                pathInfo.put(ConstVal.CONTROLLER_TEST_PATH, joinPath(testDir, packageInfo.get(ConstVal.CONTROLLER_TEST)));
            } else if (ConstVal.DAO.equals(str)) {
                packageInfo.put(ConstVal.DAO, joinPackage(basePackage, config.getDao()));
                pathInfo.put(ConstVal.DAO_PATH, joinPath(javaDir, packageInfo.get(ConstVal.DAO)));
            } else if (ConstVal.ENTITY.equals(str)) {
                packageInfo.put(ConstVal.ENTITY, joinPackage(basePackage, config.getEntity()));
                pathInfo.put(ConstVal.ENTITY_PATH, joinPath(javaDir, packageInfo.get(ConstVal.ENTITY)));
            } else if (ConstVal.MAPPER.equals(str)) {
                packageInfo.put(ConstVal.MAPPER, joinPackage(basePackage, config.getMapper()));
                pathInfo.put(ConstVal.MAPPER_PATH, joinPath(resourceDir, packageInfo.get(ConstVal.MAPPER)));
            } else if (ConstVal.CONTROLLER.equals(str)) {
                packageInfo.put(ConstVal.CONTROLLER, joinPackage(basePackage, config.getController()));
                pathInfo.put(ConstVal.CONTROLLER_PATH, joinPath(javaDir, packageInfo.get(ConstVal.CONTROLLER)));
            }
        }
        packageInfo.put(ConstVal.DATE_CONVERTER,joinPackage(basePackage,config.getConverter()));
        pathInfo.put(ConstVal.DATE_CONVERTER_PATH,joinPath(javaDir,packageInfo.get(ConstVal.DATE_CONVERTER)));
        packageInfo.put(ConstVal.COMMON_RESULT,joinPackage(basePackage,config.getVo()));
        pathInfo.put(ConstVal.COMMON_RESULT_PATH,joinPath(javaDir,packageInfo.get(ConstVal.COMMON_RESULT)));
    }

    /**
     * 处理工程配置文件
     * @param outputDir
     * @param config
     */
    private void handlerBaseConfigFiles(String outputDir, ProjectConfig config) {
        String fileSeparator = System.getProperty("file.separator");
        String applicationName = GeneratorProperties.applicationName();
        String basePath = outputDir + fileSeparator + applicationName;
        baseConfigFilesPath = new HashMap<>(10);

        baseConfigFilesPath.put(ConstVal.TEMPLATE_POM, connectPath(basePath, config.getPom()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_LOF4J, connectPath(basePath, config.getLog4j()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_400,connectPath(basePath,config.getHtml400()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_404, connectPath(basePath, config.getHtml404()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_500, connectPath(basePath, config.getHtml500()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_SPRING_MVC, connectPath(basePath, config.getSpringMvc()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_SPRING_MYBATIS, connectPath(basePath, config.getSpringMybatis()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_MYBATIS_CONFIG, connectPath(basePath, config.getMybatisConfig()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_WEB_XML, connectPath(basePath, config.getWebXml()));
        baseConfigFilesPath.put(ConstVal.TEMPLATE_JDBC, connectPath(basePath, config.getJdbc()));
    }

    private void handlerBaseConfigPath(String outputDir, ProjectConfig config) {
        String fileSeparator = System.getProperty("file.separator");
        String applicationName = GeneratorProperties.applicationName();
        String basePath = outputDir + fileSeparator + applicationName;
        baseConfigPathInfo = new HashMap<>(3);

        baseConfigPathInfo.put(ConstVal.RESOURCE_PATH, connectPath(basePath, config.getResource()));
        baseConfigPathInfo.put(ConstVal.WEB_INFO_PATH, connectPath(basePath, config.getWebInfoPath()));
        baseConfigPathInfo.put(ConstVal.ERROR_PATH, connectPath(basePath, config.getErrorPath()));
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", "\\" + File.separator);
        return parentDir + packageName;
    }

    /**
     * 两个路径的连接
     *
     * @param parentDir
     * @param path
     * @return
     */
    private String connectPath(String parentDir, String path) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        return parentDir + File.separator + path;
    }

    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (StringUtils.isEmpty(parent)) {
            return subPackage;
        }
        return parent + "." + subPackage;
    }

    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }

    public Map<String, String> getBaseConfigFilesPath() {
        return baseConfigFilesPath;
    }

    public Map<String, String> getBaseConfigPathInfo() {
        return baseConfigPathInfo;
    }

    public List<TableInfo> getTableInfo() {
        return tableInfo;
    }
}
