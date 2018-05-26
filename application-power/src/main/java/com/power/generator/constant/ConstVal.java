package com.power.generator.constant;

/**
 * 定义常量
 * Created by yu on 2016/12/9.
 */
public class ConstVal {

    public static final String FILE_SEPARATOR =  System.getProperty("file.separator");

    public static final String JAVA_SUFFIX = ".java";
    public static final String XML_SUFFIX = ".xml";


    public static final String TEST_JAVA_PATH = "src\\test\\java";
    public static final String JAVA_PATH = "src\\main\\java";
    public static final String MAIN_PATH = "src\\main";
    public static final String RESOURCE_DIR = "src\\main\\resources";
    public static final String DOCS_PATH="docs";

    //assembly路径配置
    public static final String ASSEMBLY_DIR = "src\\main\\assembly";
    public static final String ASSEMBLY_BIN = "src\\main\\assembly\\bin";
    public static final String ASSEMBLY_CFG = "src\\main\\assembly\\config";


    public static final String STRING_BOOT_STATIC_DIR = RESOURCE_DIR + "\\static";
    public static final String STRING_BOOT_EORRO_DIR = STRING_BOOT_STATIC_DIR + "\\errors";

    public static final String ENTITY = "model";
    public static final String SERVICE = "service";
    public static final String SERVICE_SUBFIX = "Service.java";
    public static final String SERVICEIMPL = "ServiceImpl";
    public static final String SERVICEIMPL_SUBFIX = "ServiceImpl.java";
    public static final String DAO = "dao";
    public static final String DAO_SUBFIX = "Dao.java";
    public static final String MAPPER = "mapper";
    public static final String MAPPER_SUBFIX = "Dao.xml";
    public static final String CONTROLLER = "controller";
    public static final String CONTROLLER_SUBFIX = "Controller.java";
    public static final String CONTROLLER_TEST = "controllerTest";
    public static final String CONTROLLER_TEST_SUBFIX = "ControllerTest.java";
    public static final String SERVICE_TEST = "serviceTest";
    public static final String SERVICE_TEST_SUBFIX = "ServiceTest.java";
    public static final String DATE_CONVERTER = "DateConverter";
    public static final String REST_EXCEPTION = "RestExceptionHandler";

    //多数据源配置
    public static final String ASPECT = "spect";
    public static final String DATA_SOURCE_FIG = "datasource_cfg";
    public static final String CONSTANTS = "constants";

    public static final String JDBC = "jdbc.properties";

    public static final String WEB_INFO = "web-info";

    public static final String ENTITY_PATH = "entity_path";
    public static final String SERVICE_PATH = "serivce_path";
    public static final String SERVICEIMPL_PATH = "serviceimpl_path";
    public static final String MAPPER_PATH = "mapper_path";
    public static final String DAO_PATH = "dao_path";
    public static final String CONTROLLER_PATH = "controller_path";
    public static final String CONTROLLER_TEST_PATH = "controller_test_path";
    public static final String SERVICE_TEST_PATH = "service_test_path";
    public static final String DATE_CONVERTER_PATH = "date_converter_path";
    public static final String ERROR_PATH = "error_path";
    public static final String REST_ERROR_PATH = "rest_error_path";
    public static final String SPRINGBOOT_MAIN_PATH = "spring_boot_main_path";
    public static final String RESOURCE_PATH = "resource_path";
    public static final String WEB_INFO_PATH = "web_info_path";
    public static final String WEB_APP_PATH = "web_app_path";


    public static final String POM_PATH = "pom_path";
    public static final String MYBATIS_CFG_PATH = "mybatis_cfg_path";
    public static final String STRING_MVC_PATH = "spring_mvc_path";
    public static final String SPTING_MYBAIS_PATH = "spring_mybatis_path";
    public static final String HTML_404_PATH = "html404_path";
    public static final String HTML_505_PATH = "html505_path";
    public static final String WEB_XML_PATH = "web_xml_path";
    public static final String LOG4J_PATH = "log4j_path";
    public static final String JDBC_PATH = "jdbc_path";




    //定义模板名
    public static final String TPL_ENTITY = "model.btl";
    public static final String TPL_DAO = "Dao.btl";
    public static final String TPL_MAPPER = "Mapper.btl";
    public static final String TPL_SERVICE = "Service.btl";
    public static final String TPL_SERVICEIMPL = "ServiceImpl.btl";
    public static final String TPL_CONTROLLER = "Controller.btl";
    public static final String TPL_SERVICE_TEST = "ServiceTest.btl";
    public static final String TPL_CONTROLLER_TEST = "ControllerTest.btl";
    public static final String TPL_DATE_CONVERTER = "DateConverter.btl";

    public static final String TPL_CONTROLLER_BASE_TEST = "ControllerBaseTest.btl";
    public static final String TPL_SPRING_BOOT_CONTROLLER_BASE_TEST = "SpringBootBaseControllerTest.btl";
    public static final String TPL_SERVICE_BASE_TEST = "ServiceBaseTest.btl";
    public static final String TPL_SPRING_BOOT_SERVICE_BASE_TEST = "SpringBootServiceBaseTest.btl";

    public static final String TPL_POM = "config\\pom.btl";
    public static final String TPL_WEB_XML = "config\\web.btl";
    public static final String TPL_SPRING_MYBATIS = "config\\spring-mybatis.btl";
    public static final String TPL_SPRING_MYBATIS_MULTIPLE = "config\\spring-mybatis-multiple.btl";
    public static final String TPL_SPRING_MVC = "config\\spring-mvc.btl";
    public static final String TPL_LOF4J = "config\\log4j.btl";
    public static final String TPL_LOF4J2 = "config\\log4j2.btl";
    public static final String TPL_ASSEMBLY_LOG4J2 = TPL_LOF4J2;
    public static final String TPL_MYBATIS_CONFIG = "config\\mybatis-config.btl";
    public static final String TPL_400 = "error\\400.btl";
    public static final String TPL_404 = "error\\404.btl";
    public static final String TPL_500 = "error\\500.btl";
    public static final String TPL_JDBC = "jdbc.btl";

    public static final String TPL_SPRING_BOOT_POM = "config\\springboot-pom.btl";
    public static final String TPL_SPRING_BOOT_CFG_YML = "config\\application.btl";
    public static final String TPL_MULTIPLE_DATASOURCE_YML = "config\\application-multiple-db.btl";
    public static final String TPL_ASSEMBLY_SPRING_BOOT_YML = TPL_SPRING_BOOT_CFG_YML;
    public static final String TPL_SPRING_BOOT_MAIN = "SpringBootMainApplication.btl";

    public static final String TPL_ASSEMBLY_XML = "template/assembly/assembly.xml";

    public static final String TPL_DEPLOY_MD = "assembly/docs/DEPLOY.md";

    public static final String TPL_GITIGNORE = "gitignore.btl";

    //多数据源模板定义(针对springboot)
    public static final String TPL_DATASOURCE_CFG = "datasource\\AbstractDataSourceConfig.btl";
    public static final String TPL_DATASOURCE_KEY = "datasource\\DataSourceKey.btl";
    public static final String TPL_DATASOURCE_ASPECT = "datasource\\DbAspect.btl";
    public static final String TPL_JTA = "datasource\\TransactionManagerConfig.btl";
    public static final String TPL_SPRINGBOOT_MYBATIS_CFG = "datasource\\MyBatisConfig.btl";

    public static final String TPL_REST_ERROR = "error\\RestExceptionHandler.btl";

    public static final String JAVA_TMPDIR = "java.io.tmpdir";


}
