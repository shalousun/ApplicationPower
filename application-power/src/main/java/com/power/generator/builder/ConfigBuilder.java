package com.power.generator.builder;

import com.power.generator.constant.*;
import com.power.generator.database.DbProvider;
import com.power.generator.database.TableInfo;
import com.power.generator.model.ProjectPath;
import com.power.generator.utils.GeneratorProperties;
import com.power.generator.utils.PathUtil;

import java.util.*;

/**
 * 构建配置
 */
public class ConfigBuilder {

    /**
     * 项目的doc文档目录
     */
    private Map<String,String> docsPath;
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    /**
     * 用于注入mvc代码层的路径，不包含其他配置类和配置文件路径
     */
    private Map<String, String> mybatisGenPath;
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

    /**
     * maven项目的路径结构
     */
    private ProjectPath projectPath;

    /**
     * 初始化构建一个maven项目配置表
     */
    private void initProjectPath() {
        projectPath = new ProjectPath();
        String fileSeparator = System.getProperty("file.separator");
        String applicationName = GeneratorProperties.applicationName();
        String basePath = GeneratorProperties.outDir() + fileSeparator + applicationName;
        String javaDir = basePath + fileSeparator + ConstVal.JAVA_PATH;
        String testDir = basePath + fileSeparator + ConstVal.TEST_JAVA_PATH;
        String resourceDir = basePath + fileSeparator + ConstVal.RESOURCE_DIR;
        String docsDir = basePath + fileSeparator + ConstVal.DOCS_PATH;

        projectPath.setBasePath(basePath);
        projectPath.setJavaSrcPath(javaDir);
        projectPath.setResourceDir(resourceDir);
        projectPath.setTestJavaSrcPath(testDir);


    }

    public ConfigBuilder(DbProvider dataBaseInfo, PackageConfig packageConfig, ProjectConfig projectConfig) {
        //全局设置项目的机构
        initProjectPath();
        //包配置
        if (null == packageConfig) {
            packageConfig = new PackageConfig();
            handlerPackage(packageConfig, false);
            handlerDocsPath(packageConfig);
        } else {
            handlerPackage(packageConfig, false);
            handlerDocsPath(packageConfig);
        }
        //创建工程所需配置
        if (null == projectConfig) {
            projectConfig = new ProjectConfig();
            handlerBaseConfigPath(projectConfig);
            handlerBaseConfigFiles(projectConfig);
        } else {
            projectConfig = new ProjectConfig();
            handlerBaseConfigPath(projectConfig);
            handlerBaseConfigFiles(projectConfig);
        }
        getTableInfoList(dataBaseInfo);

    }

    /**
     * 构建spring boot
     *
     * @param dataBaseInfo
     * @param packageConfig
     * @param projectConfig
     */
    public ConfigBuilder(DbProvider dataBaseInfo, PackageConfig packageConfig, SpringBootProjectConfig projectConfig) {
        //全局设置项目的结构
        initProjectPath();
        //包配置
        if (null == packageConfig) {
            handlerPackage(new PackageConfig(), true);
            handlerDocsPath(new PackageConfig());
        } else {
            handlerPackage(packageConfig, true);
            handlerDocsPath(packageConfig);
        }
        //创建工程所需配置
        if (null == projectConfig) {
            projectConfig = new SpringBootProjectConfig();
            handlerSpringBootConfigPath(projectConfig);
            handlerSpringBootConfigFiles(projectConfig);
        } else {
            projectConfig = new SpringBootProjectConfig();
            handlerSpringBootConfigPath(projectConfig);
            handlerSpringBootConfigFiles(projectConfig);
        }
        getTableInfoList(dataBaseInfo);
    }


    /**
     * 获取列信息
     *
     * @param dbProvider
     */
    private void getTableInfoList(DbProvider dbProvider) {
        tableInfo = dbProvider.getTablesInfo(GeneratorProperties.getTableName(), GeneratorProperties.tableFilterPrefix());
    }

    /**
     * 处理包
     *
     * @param config
     */
    private void handlerPackage(PackageConfig config, boolean isSpringBoot) {
        packageInfo = new HashMap<>(9);
        pathInfo = new LinkedHashMap<>(8);
        mybatisGenPath = new LinkedHashMap<>(8);

        String basePackage = GeneratorProperties.basePackage();
        String javaDir = projectPath.getJavaSrcPath();
        String testDir = projectPath.getTestJavaSrcPath();
        String resourceDir = projectPath.getResourceDir();


        String layers = GeneratorProperties.layers();
        String[] layerArr = layers.split(",");
        Set<String> layerSet = new HashSet<>();
        layerSet.addAll(Arrays.asList(layerArr));
        for (String str : layerSet) {
            if (ConstVal.SERVICE.equals(str)) {
                packageInfo.put(ConstVal.SERVICE, joinPackage(basePackage, config.getService()));
                packageInfo.put(ConstVal.SERVICEIMPL, joinPackage(basePackage, config.getServiceImpl()));
                pathInfo.put(ConstVal.SERVICE_PATH, joinPath(javaDir, packageInfo.get(ConstVal.SERVICE)));
                pathInfo.put(ConstVal.SERVICEIMPL_PATH, joinPath(javaDir, packageInfo.get(ConstVal.SERVICEIMPL)));
                mybatisGenPath.put(BuilderCfg.SERVICE_BUILDER, joinJavaFilePath(packageInfo.get(ConstVal.SERVICE), ConstVal.SERVICE_SUBFIX));
                mybatisGenPath.put(BuilderCfg.SERVICEIMPL_BUILER, joinJavaFilePath(packageInfo.get(ConstVal.SERVICEIMPL), ConstVal.SERVICEIMPL_SUBFIX));
            } else if (ConstVal.SERVICE_TEST.equals(str)) {
                packageInfo.put(ConstVal.SERVICE_TEST, joinPackage(basePackage, config.getService()));
                pathInfo.put(ConstVal.SERVICE_TEST_PATH, joinPath(testDir, packageInfo.get(ConstVal.SERVICE_TEST)));
                mybatisGenPath.put(BuilderCfg.SERVICE_TEST_BUILER, joinFinalPath(testDir, packageInfo.get(ConstVal.SERVICE_TEST), ConstVal.SERVICE_TEST_SUBFIX));
            } else if (ConstVal.CONTROLLER_TEST.equals(str)) {
                packageInfo.put(ConstVal.CONTROLLER_TEST, joinPackage(basePackage, config.getController()));
                pathInfo.put(ConstVal.CONTROLLER_TEST_PATH, joinPath(testDir, packageInfo.get(ConstVal.CONTROLLER_TEST)));
                mybatisGenPath.put(BuilderCfg.CONTROLLER_TEST_BUILER, joinFinalPath(testDir, packageInfo.get(ConstVal.CONTROLLER_TEST), ConstVal.CONTROLLER_TEST_SUBFIX));
            } else if (ConstVal.DAO.equals(str)) {
                packageInfo.put(ConstVal.DAO, joinPackage(basePackage, config.getDao()));
                pathInfo.put(ConstVal.DAO_PATH, joinPath(javaDir, packageInfo.get(ConstVal.DAO)));
                mybatisGenPath.put(BuilderCfg.DAO_BUILER, joinJavaFilePath(packageInfo.get(ConstVal.DAO), ConstVal.DAO_SUBFIX));
            } else if (ConstVal.ENTITY.equals(str)) {
                packageInfo.put(ConstVal.ENTITY, joinPackage(basePackage, config.getEntity()));
                pathInfo.put(ConstVal.ENTITY_PATH, joinPath(javaDir, packageInfo.get(ConstVal.ENTITY)));
                mybatisGenPath.put(BuilderCfg.MODEL_BUILER, joinJavaFilePath(packageInfo.get(ConstVal.ENTITY), ConstVal.JAVA_SUFFIX));
            } else if (ConstVal.MAPPER.equals(str)) {
                packageInfo.put(ConstVal.MAPPER, joinPackage(basePackage, config.getMapper()));
                pathInfo.put(ConstVal.MAPPER_PATH, joinPath(resourceDir, packageInfo.get(ConstVal.MAPPER)));
                mybatisGenPath.put(BuilderCfg.MAPPER_BUILDER, joinFinalPath(resourceDir, packageInfo.get(ConstVal.MAPPER), ConstVal.MAPPER_SUBFIX));
            } else if (ConstVal.CONTROLLER.equals(str)) {
                packageInfo.put(ConstVal.CONTROLLER, joinPackage(basePackage, config.getController()));
                pathInfo.put(ConstVal.CONTROLLER_PATH, joinPath(javaDir, packageInfo.get(ConstVal.CONTROLLER)));
                mybatisGenPath.put(BuilderCfg.CONTROLLER_BUILER, joinJavaFilePath(packageInfo.get(ConstVal.CONTROLLER), ConstVal.CONTROLLER_SUBFIX));
            }
        }
        packageInfo.put(ConstVal.DATE_CONVERTER, joinPackage(basePackage, config.getConverter()));
        pathInfo.put(ConstVal.DATE_CONVERTER_PATH, joinPath(javaDir, packageInfo.get(ConstVal.DATE_CONVERTER)));
        packageInfo.put(ConstVal.REST_EXCEPTION, joinPackage(basePackage, config.getRestError()));
        pathInfo.put(ConstVal.REST_ERROR_PATH, joinPath(javaDir, packageInfo.get(ConstVal.REST_EXCEPTION)));
        if (GeneratorProperties.getMultipleDataSource().size() > 0) {
            packageInfo.put(ConstVal.ASPECT, joinPackage(basePackage, config.getAspect()));
            pathInfo.put(ConstVal.ASPECT, joinPath(javaDir, packageInfo.get(ConstVal.ASPECT)));
            packageInfo.put(ConstVal.CONSTANTS, joinPackage(basePackage, config.getConstants()));
            pathInfo.put(ConstVal.CONSTANTS, joinPath(javaDir, packageInfo.get(ConstVal.CONSTANTS)));
            if (isSpringBoot) {
                packageInfo.put(ConstVal.DATA_SOURCE_FIG, joinPackage(basePackage, config.getConfig()));
                pathInfo.put(ConstVal.DATA_SOURCE_FIG, joinPath(javaDir, packageInfo.get(ConstVal.DATA_SOURCE_FIG)));
            }
        }
        if (GeneratorProperties.isJTA()) {
            packageInfo.put(ConstVal.DATA_SOURCE_FIG, joinPackage(basePackage, config.getConfig()));
            pathInfo.put(ConstVal.DATA_SOURCE_FIG, joinPath(javaDir, packageInfo.get(ConstVal.DATA_SOURCE_FIG)));
        }
    }

    /**
     * 处理ssm架构体系的工程配置文件
     *
     * @param config
     */
    private void handlerBaseConfigFiles(ProjectConfig config) {

        String basePath = projectPath.getBasePath();
        baseConfigFilesPath = new HashMap<>(10);

        baseConfigFilesPath.put(ConstVal.TPL_POM, connectPath(basePath, config.getPom()));
        baseConfigFilesPath.put(ConstVal.TPL_LOF4J2, connectPath(basePath, config.getLog4j2()));
        baseConfigFilesPath.put(ConstVal.TPL_400, connectPath(basePath, config.getHtml400()));
        baseConfigFilesPath.put(ConstVal.TPL_404, connectPath(basePath, config.getHtml404()));
        baseConfigFilesPath.put(ConstVal.TPL_500, connectPath(basePath, config.getHtml500()));
        baseConfigFilesPath.put(ConstVal.TPL_SPRING_MVC, connectPath(basePath, config.getSpringMvc()));
        if (GeneratorProperties.isMultipleDataSource()) {
            baseConfigFilesPath.put(ConstVal.TPL_SPRING_MYBATIS_MULTIPLE, connectPath(basePath, config.getSpringMybatis()));
        } else {
            baseConfigFilesPath.put(ConstVal.TPL_SPRING_MYBATIS, connectPath(basePath, config.getSpringMybatis()));
        }
        baseConfigFilesPath.put(ConstVal.TPL_SPRING_MYBATIS, connectPath(basePath, config.getSpringMybatis()));
        baseConfigFilesPath.put(ConstVal.TPL_MYBATIS_CONFIG, connectPath(basePath, config.getMybatisConfig()));
        baseConfigFilesPath.put(ConstVal.TPL_WEB_XML, connectPath(basePath, config.getWebXml()));
        baseConfigFilesPath.put(ConstVal.TPL_JDBC, connectPath(basePath, config.getJdbc()));
    }

    /**
     * 处理SpringBoot体系的项目配置文件
     *
     * @param config
     */
    private void handlerSpringBootConfigFiles(SpringBootProjectConfig config) {

        String basePath = projectPath.getBasePath();
        baseConfigFilesPath = new HashMap<>(10);
        baseConfigFilesPath.put(ConstVal.TPL_SPRING_BOOT_POM, connectPath(basePath, config.getPom()));
        if (GeneratorProperties.isMultipleDataSource()) {
            baseConfigFilesPath.put(ConstVal.TPL_MULTIPLE_DATASOURCE_YML, connectPath(basePath, config.getApplicationYml()));
        } else {
            baseConfigFilesPath.put(ConstVal.TPL_SPRING_BOOT_CFG_YML, connectPath(basePath, config.getApplicationYml()));
        }
        baseConfigFilesPath.put(ConstVal.TPL_LOF4J2, connectPath(basePath, config.getLog4j2()));
        baseConfigFilesPath.put(ConstVal.TPL_MYBATIS_CONFIG, connectPath(basePath, config.getMybatisConfig()));
        baseConfigFilesPath.put(ConstVal.TPL_400, connectPath(basePath, config.getHtml400()));
        baseConfigFilesPath.put(ConstVal.TPL_404, connectPath(basePath, config.getHtml404()));
        baseConfigFilesPath.put(ConstVal.TPL_500, connectPath(basePath, config.getHtml500()));
    }

    /**
     * 处理SpringBoot项目的配置路径
     *
     * @param config
     */
    private void handlerSpringBootConfigPath(SpringBootProjectConfig config) {
        String basePath = projectPath.getBasePath();
        baseConfigPathInfo = new HashMap<>(5);

        baseConfigPathInfo.put(ConstVal.RESOURCE_PATH, connectPath(basePath, config.getResource()));
        baseConfigPathInfo.put(ConstVal.STRING_BOOT_EORRO_DIR, connectPath(basePath, config.getErrorPath()));
        if (GeneratorProperties.getAssembly()) {
            baseConfigPathInfo.put(ConstVal.ASSEMBLY_DIR, connectPath(basePath, config.getAssemblyRoot()));
            baseConfigPathInfo.put(ConstVal.ASSEMBLY_BIN, connectPath(basePath, config.getAssemblyBin()));
            baseConfigPathInfo.put(ConstVal.ASSEMBLY_CFG, connectPath(basePath, config.getAssemblyCfg()));
        }

    }

    private void handlerBaseConfigPath(ProjectConfig config) {
        String basePath = projectPath.getBasePath();
        baseConfigPathInfo = new HashMap<>(3);

        baseConfigPathInfo.put(ConstVal.RESOURCE_PATH, connectPath(basePath, config.getResource()));
        baseConfigPathInfo.put(ConstVal.WEB_INFO_PATH, connectPath(basePath, config.getWebInfoPath()));
        baseConfigPathInfo.put(ConstVal.ERROR_PATH, connectPath(basePath, config.getErrorPath()));
    }

    /**
     * 项目的docs目录
     * @param config
     */
    private void handlerDocsPath(PackageConfig config){
        docsPath = new HashMap<>();
        String basePath = projectPath.getBasePath();
        String fileSeparator = System.getProperty("file.separator");
        String docsDir = basePath + fileSeparator + ConstVal.DOCS_PATH;
        docsPath.put(config.getDocs(),docsDir);
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        return PathUtil.joinPath(parentDir, packageName);
    }

    /**
     * 配置出最终的连接路径
     *
     * @param packageName
     * @param postfix
     * @return
     */
    private String joinJavaFilePath(String packageName, String postfix) {
        String javaDir = projectPath.getJavaSrcPath();
        return PathUtil.joinPath(javaDir, packageName) + "\\%s" + postfix;
    }

    private String joinFinalPath(String parentDir, String packageName, String postfix) {
        return PathUtil.joinPath(parentDir, packageName) + "\\%s" + postfix;
    }

    /**
     * 两个路径的连接
     *
     * @param parentDir
     * @param path
     * @return
     */
    private String connectPath(String parentDir, String path) {
        return PathUtil.connectPath(parentDir, path);
    }

    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        return PathUtil.joinPackage(parent, subPackage);
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

    public ProjectPath getProjectPath() {
        return projectPath;
    }

    public Map<String, String> getMybatisGenPath() {
        return mybatisGenPath;
    }

    public Map<String, String> getDocsPath() {
        return docsPath;
    }
}
