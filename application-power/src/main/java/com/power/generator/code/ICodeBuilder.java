package com.power.generator.code;


import com.power.generator.constant.ConstVal;
import com.power.generator.utils.GeneratorProperties;

import java.util.Map;

/**
 * 各模块代码生成的流程模板
 */
public interface ICodeBuilder {


    /**
     * 构建代码的输出路径
     */
    void buildPath();

    /**
     * 构建各个模块的代码
     */
    void buildCode();

    /**
     * 处理个模块的模板，key为路径，value是模板内容
     *
     * @return
     */
    Map<String, String> handleTemplates();

    /**
     * 代码输出的基目录
     *
     * @return
     */
    default String getBasePath() {
        String applicationName = GeneratorProperties.applicationName();
        String basePath = GeneratorProperties.outDir() + ConstVal.FILE_SEPARATOR + applicationName;
        return basePath;
    }

    /**
     * src/main
     *
     * @return
     */
    default String getMainPath() {
        String basePath = getBasePath();
        String mainPath = basePath + ConstVal.FILE_SEPARATOR + ConstVal.MAIN_PATH;
        return mainPath;
    }


    /**
     * java src目录
     *
     * @return
     */
    default String getJavaSrcPath() {
        String basePath = getBasePath();
        String javaPath = basePath + ConstVal.FILE_SEPARATOR + ConstVal.JAVA_PATH;
        return javaPath;
    }

    /**
     * base package
     *
     * @return
     */
    default String basePackage() {
        return GeneratorProperties.basePackage();
    }

    /**
     * 获取
     *
     * @return
     */
    default String getTestSrcPath() {
        String testDir = getBasePath() + ConstVal.FILE_SEPARATOR + ConstVal.TEST_JAVA_PATH;
        return testDir;
    }

}
