package com.power.builder;

import com.power.constant.ConstVal;
import com.power.constant.GeneratorConstant;
import com.power.database.TableInfo;
import com.power.utils.BeetlTemplateUtil;
import com.power.utils.FileUtils;
import com.power.utils.GeneratorProperties;
import com.power.utils.StringUtils;
import org.beetl.core.Template;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author on 2016/12/7.
 */
public class CodeWriter extends AbstractCodeWriter {


    public void execute() {
        //初始化配置
        initConfig();
        //java代码目录
        mkdirs(config.getPathInfo());
        //创建配置文件路径
        mkdirs(config.getBaseConfigPathInfo());
        //创建配置文件
        writeBaseConfig(config.getBaseConfigFilesPath());
        //创建代码
        writeCode(config);
        //创建项目所需基础类
        writeBaseCode(config);
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
        Template template;
        String key;
        for (Map.Entry<String, String> entry : baseConfigFiles.entrySet()) {
            key = entry.getKey();
            if (ConstVal.TEMPLATE_JDBC.equals(key)) {
                String currentPath = Thread.currentThread().getContextClassLoader().getResource(ConstVal.JDBC).getPath();
                FileUtils.nioTransferCopy(new File(currentPath), new File(entry.getValue()));
            } else {
                template = BeetlTemplateUtil.getByName(key);
                template.binding(GeneratorConstant.BASE_PACKAGE, basePackage);
                template.binding(GeneratorConstant.APPLICATION_NAME, GeneratorProperties.applicationName());
                //spring config
                template.binding("mappingDir", basePackage.replaceAll("[.]", "/"));
                template.binding("jdbcUrl", "${jdbc.url}");
                template.binding("jdbcUserName", "${jdbc.username}");
                template.binding("jdbcPassword", "${jdbc.password}");
                //pom
                template.binding("projectVersion", "${project.version}");
                template.binding("springVersion", "${spring.version}");
                template.binding("mybatisVersion", "${mybatis.version}");
                template.binding("jacksonVersion", "${jackson.version}");
                template.binding("slf4jVersion", "${slf4j.version}");
                //mybatis config
                template.binding("cacheEnabled", GeneratorProperties.enableCache());
                FileUtils.writeFileNotAppend(template.render(), entry.getValue());
            }
        }
    }

    /**
     * base code是项目的一些基本类，包括单元测基类
     * 时间转换类
     *
     * @param config
     */
    private void writeBaseCode(ConfigBuilder config) {
        String basePackage = GeneratorProperties.basePackage();
        Map<String, String> dirMap = config.getPathInfo();
        for (Map.Entry<String, String> entry : dirMap.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (ConstVal.SERVICE_TEST_PATH.equals(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_SERVICE_BASE_TEST);
                template.binding(GeneratorConstant.BASE_PACKAGE, basePackage);
                FileUtils.writeFileNotAppend(template.render(), value + "\\ServiceBaseTest.java");
            }
            if (ConstVal.CONTROLLER_TEST_PATH.equals(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_CONTROLLER_BASE_TEST);
                template.binding(GeneratorConstant.BASE_PACKAGE, basePackage);
                FileUtils.writeFileNotAppend(template.render(), value + "\\ControllerBaseTest.java");
            }
            if (ConstVal.DATE_CONVERTER_PATH.equals(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_DATE_CONVERTER);
                template.binding(GeneratorConstant.BASE_PACKAGE, basePackage);
                FileUtils.writeFileNotAppend(template.render(), value + "\\DateConverter.java");
            }
            if (ConstVal.COMMON_RESULT_PATH.equals(key)) {
                Template template = BeetlTemplateUtil.getByName(ConstVal.TEMPLATE_COMMON_RESULT);
                template.binding(GeneratorConstant.BASE_PACKAGE, basePackage);
                FileUtils.writeFileNotAppend(template.render(), value + "\\CommonResult.java");
            }
        }
    }

    /**
     * 生成model,dao,service,controller,controllerTest,serviceTest代码
     *
     * @param config
     */
    private void writeCode(ConfigBuilder config) {
        Map<String, String> dirMap = config.getPathInfo();
        List<TableInfo> tables = config.getTableInfo();
        for (TableInfo tableInfo : tables) {
            String table = tableInfo.getName();
            //实体名需要移除表前缀
            String tableTemp = StringUtils.removePrefix(table, GeneratorProperties.tablePrefix());
            String entityName = StringUtils.toCapitalizeCamelCase(tableTemp);
            for (Map.Entry<String, String> entry : dirMap.entrySet()) {
                String value = entry.getValue();
                String key = entry.getKey();
                if (ConstVal.DAO_PATH.equals(key)) {
                    String daoCode = new DaoBuilder().generateDao(entityName);
                    FileUtils.writeFileNotAppend(daoCode, value + "\\" + entityName + "Dao.java");
                }
                if (ConstVal.ENTITY_PATH.equals(key)) {
                    String modelCode = new ModelBuilder().generateModel(tableInfo);
                    FileUtils.writeFileNotAppend(modelCode, value + "\\" + entityName + ".java");
                }
                if (ConstVal.SERIVCE_PATH.equals(key)) {
                    String serviceCode = new ServiceBuilder().generateService(entityName);
                    FileUtils.writeFileNotAppend(serviceCode, value + "\\" + entityName + "Service.java");
                    String serviceImplCode = new ServiceImplBuilder().generateServiceImpl(entityName);
                    FileUtils.writeFileNotAppend(serviceImplCode, value + "\\impl\\" + entityName + "ServiceImpl.java");
                }
                if (ConstVal.SERVICE_TEST_PATH.equals(key)) {
                    String serviceTestCode = new ServiceTestBuilder().generateServiceTest(entityName);
                    FileUtils.writeFileNotAppend(serviceTestCode, value + "\\" + entityName + "ServiceTest.java");
                }
                if (ConstVal.CONTROLLER_PATH.equals(key)) {
                    String controllerCode = new ControllerBuilder().generateController(entityName);
                    FileUtils.writeFileNotAppend(controllerCode, value + "\\" + entityName + "Controller.java");
                }
                if (ConstVal.CONTROLLER_TEST_PATH.equals(key)) {
                    String controllerCode = new ControllerTestBuilder().generateControllerTest(entityName);
                    FileUtils.writeFileNotAppend(controllerCode, value + "\\" + entityName + "ControllerTest.java");
                }

                if (ConstVal.MAPPER_PATH.equals(key)) {
                    String mapperCode = new MapperBuilder().generateMapper(table);
                    FileUtils.writeFileNotAppend(mapperCode, value + "\\" + entityName + "Dao.xml");
                }
            }
        }
    }
}
