package com.power.generator.builder;

import com.power.common.util.FileUtil;
import com.power.common.util.StringUtil;
import com.power.generator.code.impl.*;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.constant.SpringBootProjectConfig;
import com.power.generator.database.DbProvider;
import com.power.generator.database.TableInfo;
import com.power.generator.factory.DbProviderFactory;
import com.power.generator.utils.*;
import org.beetl.core.Template;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author on 2016/12/7.
 */
public class CodeWriter extends AbstractCodeWriter {

    public void executeSpringBoot() {
        //初始化配置
        initSpringBootConfig();
        //java代码目录
        mkdirs(config.getPathInfo());
        //创建配置文件路径
        mkdirs(config.getBaseConfigPathInfo());
        //创建文档路径
        mkdirs(config.getDocsPath());
        //创建配置文件
        writeBaseConfig(config.getBaseConfigFilesPath());
        //创建代码
        writeCode(config);
        //创建项目所需基础类
        writeSpringBootBaseCode(config);

        //创建assembly配置
        writeAssemblyConfig();

        writeDbSourceAndJTACode(config, new SpringBootProjectConfig());

        new DockerCodeBuilder();

        new KubeYmlCodeBuilder();

        new PomCodeBuilder();

        new GradleCodeBuilder();

        new BaseCodeBuilder();

        new ApiDocTestCodeBuilder();

    }

    /**
     * 处理输出目录
     *
     * @param pathInfo 路径信息
     */
    private void mkdirs(Map<String, String> pathInfo) {
        for (Map.Entry<String, String> entry : pathInfo.entrySet()) {
            File dir = new File(entry.getValue());
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    /**
     * 创建工程所需配置文件
     *
     * @param baseConfigFiles web工程所需的配置文件
     */
    private void writeBaseConfig(Map<String, String> baseConfigFiles) {
        String basePackage = GeneratorProperties.basePackage();
        PropertiesUtils dbProp = new PropertiesUtils("jdbc.properties");
        Template template;
        String key;
        for (Map.Entry<String, String> entry : baseConfigFiles.entrySet()) {
            key = entry.getKey();
            if (ConstVal.TPL_JDBC.equals(key)) {
                String currentPath = Thread.currentThread().getContextClassLoader().getResource(ConstVal.JDBC).getPath();
                FileUtil.nioTransferCopy(new File(currentPath), new File(entry.getValue()));
            } else {
                template = BeetlTemplateUtil.getByName(key);
                template.binding(GeneratorConstant.COMMON_VARIABLE);

                //spring mybatis config
                template.binding("mappingDir", basePackage.replaceAll("[.]", "/"));
                template.binding("jdbcDriver", "${jdbc.driver}");
                template.binding("jdbcUrl", "${jdbc.url}");
                template.binding("jdbcUserName", "${jdbc.username}");
                template.binding("jdbcPassword", "${jdbc.password}");
                Set<String> dataSourceSet = GeneratorProperties.getMultipleDataSource();
                Map<String, String> dataSourceMap = new HashMap<>();
                int i = 0;
                for (String str : dataSourceSet) {
                    dataSourceMap.put(StringUtil.firstToUpperCase(str), str);
                    if (i == 0) {
                        template.binding("defaultDs", StringUtil.firstToUpperCase(str));
                    }
                    i++;
                }
                template.binding("dataSourceMap", dataSourceMap);
                //pom
                template.binding("useAssembly", GeneratorProperties.getAssembly());
                template.binding("useJTA", GeneratorProperties.isJTA());
                template.binding("isMultipleDataSource", GeneratorProperties.isMultipleDataSource());
                template.binding("isUseDocker", GeneratorProperties.useDocker());
                template.binding(GeneratorConstant.LOMBOK, GeneratorProperties.useLombok());
                //log4j2
                template.binding("LOG_PATH", "${sys:logging.path}");
                //mybatis config
                template.binding("cacheEnabled", GeneratorProperties.enableCache());

                //SpringBoot yml
                template.binding("dbUrl", dbProp.getProperty("jdbc.url"));
                template.binding("dbUserName", YmlUtil.addDoubleQuote(dbProp.getProperty("jdbc.username")));
                template.binding("dbPassword", YmlUtil.addDoubleQuote(dbProp.getProperty("jdbc.password")));
                template.binding("dbDriver", dbProp.getProperty("jdbc.driver"));
                template.binding("list", GeneratorProperties.getMultipleDataSource());
                template.binding("isJTA", GeneratorProperties.isJTA());
                template.binding("mybatis",GeneratorProperties.getDbTemplatePath());

                FileUtil.writeFileNotAppend(template.render(), entry.getValue());
            }
        }
        template = BeetlTemplateUtil.getByName(ConstVal.TPL_GITIGNORE);
        FileUtil.writeFileNotAppend(template.render(), config.getProjectPath().getBasePath() + "/.gitignore");
    }

    /**
     * base code是项目的一些基本类，包括单元测基类
     * 时间转换类
     *
     * @param config
     */
    private void writeBaseCode(ConfigBuilder config, boolean isSpringboot) {
        Map<String, String> dirMap = config.getPathInfo();
        for (Map.Entry<String, String> entry : dirMap.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (ConstVal.SERVICE_TEST_PATH.equals(key)) {
                String templateName = ConstVal.TPL_SERVICE_BASE_TEST;
                if (isSpringboot) {
                    templateName = ConstVal.TPL_SPRING_BOOT_SERVICE_BASE_TEST;
                }
                Template template = BeetlTemplateUtil.getByName(templateName);
                template.binding(GeneratorConstant.COMMON_VARIABLE);
                FileUtil.writeFileNotAppend(template.render(), value + ConstVal.FILE_SEPARATOR + "ServiceBaseTest.java");
            }
            if (ConstVal.CONTROLLER_TEST_PATH.equals(key)) {
                String templateName = ConstVal.TPL_CONTROLLER_BASE_TEST;
                if (isSpringboot) {
                    templateName = ConstVal.TPL_SPRING_BOOT_CONTROLLER_BASE_TEST;
                }
                Template template = BeetlTemplateUtil.getByName(templateName);
                template.binding(GeneratorConstant.COMMON_VARIABLE);
                FileUtil.writeFileNotAppend(template.render(), value + ConstVal.FILE_SEPARATOR + "ControllerBaseTest.java");
            }
            if (ConstVal.DATE_CONVERTER_PATH.equals(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_DATE_CONVERTER);
                template.binding(GeneratorConstant.COMMON_VARIABLE);
                FileUtil.writeFileNotAppend(template.render(), value + ConstVal.FILE_SEPARATOR + "DateConverter.java");
            }
            if (ConstVal.REST_ERROR_PATH.contains(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_REST_ERROR);
                template.binding(GeneratorConstant.COMMON_VARIABLE);
                FileUtil.writeFileNotAppend(template.render(), value + ConstVal.FILE_SEPARATOR + "RestExceptionHandler.java");
            }
        }
    }

    /**
     * 创建SpringBoot的基础类代码
     *
     * @param config
     */
    private void writeSpringBootBaseCode(ConfigBuilder config) {
        String basePackage = GeneratorProperties.basePackage();
        writeBaseCode(config, true);

        String appName = StringUtil.firstToUpperCase(StringUtil.hyphenLineToCamel(GeneratorProperties.applicationName()));
        //创建启动的主类
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_SPRING_BOOT_MAIN);
        template.binding(GeneratorConstant.COMMON_VARIABLE);
        template.binding(GeneratorConstant.APP_NAME, appName);
        String basePackagePath = PathUtil.joinPath(config.getProjectPath().getJavaSrcPath(), basePackage);
        FileUtil.writeFileNotAppend(template.render(), basePackagePath + ConstVal.FILE_SEPARATOR + appName + "Application.java");
    }

    private void writeDbSourceAndJTACode(ConfigBuilder config, SpringBootProjectConfig projectConfig) {
        String basePackage = GeneratorProperties.basePackage();
        Map<String, String> dirMap = config.getPathInfo();
        Set<String> dataSources = GeneratorProperties.getMultipleDataSource();
        if (GeneratorProperties.isJTA() || dataSources.size() > 0) {
            Template jtaTpl = BeetlTemplateUtil.getByName(ConstVal.TPL_JTA);
            jtaTpl.binding(GeneratorConstant.COMMON_VARIABLE);
//            FileUtil.writeFileNotAppend(jtaTpl.render(),dirMap.get(ConstVal.DATA_SOURCE_FIG)+"\\TransactionManagerConfig.java");
        }
        if (dataSources.size() > 0) {
            String configPath = dirMap.get(ConstVal.DATA_SOURCE_FIG);
            Template aspectTpl = BeetlTemplateUtil.getByName(ConstVal.TPL_DATASOURCE_ASPECT);
            aspectTpl.binding(GeneratorConstant.COMMON_VARIABLE);
            FileUtil.writeFileNotAppend(aspectTpl.render(), dirMap.get(ConstVal.ASPECT) + ConstVal.FILE_SEPARATOR + "DbAspect.java");

            DataSourceKeyBuilder sourceKeyBuilder = new DataSourceKeyBuilder();
            String dataSourceTpl = sourceKeyBuilder.builderDataSourceKey(dataSources);
            FileUtil.writeFileNotAppend(dataSourceTpl, dirMap.get(ConstVal.CONSTANTS) + ConstVal.FILE_SEPARATOR + "DataSourceKey.java");

            Template abstractCfg = BeetlTemplateUtil.getByName(ConstVal.TPL_DATASOURCE_CFG);
            abstractCfg.binding(GeneratorConstant.COMMON_VARIABLE);
            FileUtil.writeFileNotAppend(abstractCfg.render(), configPath + ConstVal.FILE_SEPARATOR + "AbstractDataSourceConfig.java");

            SpringBootMybatisCfgBuilder builder = new SpringBootMybatisCfgBuilder();
            String mybatisCfgTpl = builder.createMybatisCfg(dataSources);
            FileUtil.writeFileNotAppend(mybatisCfgTpl, configPath + ConstVal.FILE_SEPARATOR + "MyBatisConfig.java");
        }
    }

    /**
     * assembly
     */
    private void writeAssemblyConfig() {
        new AssemblyCodeBuilder();
    }

    /**
     * 生成model,dao,service,controller,controllerTest,serviceTest代码
     *
     * @param config
     */
    private boolean writeCode(ConfigBuilder config) {
        if (!GeneratorProperties.useDb()) {
            return false;
        }
        Map<String, String> dirMap = config.getMybatisGenPath();
        List<TableInfo> tables = config.getTableInfo();
        DbProvider dbProvider = new DbProviderFactory().getInstance();
        for (TableInfo tableInfo : tables) {
            String table = tableInfo.getName();
            String comment = tableInfo.getRemarks();
            tableInfo = dbProvider.getTableInfo(table);
            tableInfo.setName(table);
            tableInfo.setRemarks(comment);
            //实体名需要移除表前缀
            String tableTemp = StringUtil.removePrefix(table, GeneratorProperties.tablePrefix());
            String entityName = StringUtil.toCapitalizeCamelCase(tableTemp);
            for (Map.Entry<String, String> entry : dirMap.entrySet()) {
                String value = entry.getValue();
                String key = entry.getKey();
                IBuilder builder = null;
                try {
                    builder = (IBuilder) Class.forName(key).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String template = builder.generateTemplate(tableInfo);
                FileUtil.writeFileNotAppend(template, String.format(value, entityName));
            }
        }
        return true;
    }
}
