package com.power.generator.code.impl;

import com.power.common.util.FileUtil;
import com.power.generator.builder.ScriptBuilder;
import com.power.generator.code.ICodeBuilder;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.CodeWriteUtil;
import com.power.generator.utils.GeneratorProperties;
import com.power.generator.utils.PathUtil;
import org.beetl.core.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * build artifact of gradle project
 *
 * @author yu 2018/06/03.
 */
public class GradleCodeBuilder implements ICodeBuilder {

    private static final String WRAPPER_PATH = "gradle";

    private static final String SETTINGS_TPL = "gradle/settings.btl";

    private static final String BUILD_TPL = "gradle/build.btl";
    /**
     * store paths for gradle project config
     */
    private Map<String, String> paths;

    public GradleCodeBuilder() {
        if (GeneratorProperties.useGradle()) {
            buildPath();
            buildCode();
        }
    }

    @Override
    public void buildPath() {
        // init path
        paths = new HashMap<>();
        String basePath = getBasePath();
        String wrapperPath = getBasePath() + ConstVal.FILE_SEPARATOR + ConstVal.GRADLE_WRAPPER;
        paths.put(WRAPPER_PATH, wrapperPath);
        paths.put("basePath", basePath);
        paths.put(ConstVal.GRADLE_BIN, PathUtil.connectPath(basePath, ConstVal.GRADLE_BIN));
        PathUtil.mkdirs(paths);

    }

    @Override
    public void buildCode() {
        String binPath = paths.get(ConstVal.GRADLE_BIN);
        Map<String, String> scripts = new ScriptBuilder().generateScripts();
        for (Map.Entry<String, String> entry : scripts.entrySet()) {
            FileUtil.writeFileNotAppend(entry.getValue(), binPath + ConstVal.FILE_SEPARATOR + entry.getKey());
        }
        CodeWriteUtil.writeFileNotAppend(handleTemplates());
        String basePath = getBasePath();
        //copy gradlew
        CodeWriteUtil.nioCopy("template/gradle/gradlew", PathUtil.connectPath(basePath, "gradlew"));
        //copy gradlew.bat
        CodeWriteUtil.nioCopy("template/gradle/gradlew.bat", PathUtil.connectPath(basePath, "gradlew.bat"));
        //copy wrapper
        CodeWriteUtil.nioCopyDir("template/gradle/wrapper/", paths.get(WRAPPER_PATH));

    }

    @Override
    public Map<String, String> handleTemplates() {
        //key is path ,value is template content
        Map<String, String> templates = new HashMap<>();
        //init settings
        Template settingsTpl = BeetlTemplateUtil.getByName(SETTINGS_TPL);
        settingsTpl.binding(GeneratorConstant.COMMON_VARIABLE);
        String settingsOut = PathUtil.connectPath(getBasePath(), "settings.gradle");
        templates.put(settingsOut, settingsTpl.render());

        Template buildTpl = BeetlTemplateUtil.getByName(BUILD_TPL);
        buildTpl.binding("basePackage", GeneratorProperties.basePackage());
        buildTpl.binding("springBootVersion", "${springBootVersion}");
        buildTpl.binding("useJTA", GeneratorProperties.isJTA());
        buildTpl.binding("isMultipleDataSource", GeneratorProperties.isMultipleDataSource());
        buildTpl.binding("jdkVersion", "${java.version}");
        buildTpl.binding("isUseDocker", GeneratorProperties.useDocker());
        String buildOut = PathUtil.connectPath(getBasePath(), "build.gradle");
        templates.put(buildOut, buildTpl.render());

        return templates;
    }
}
