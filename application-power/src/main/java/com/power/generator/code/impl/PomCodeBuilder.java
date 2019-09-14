package com.power.generator.code.impl;

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
 * 处理SpringBoot maven项目的pom.xml
 *
 * @author yu
 */
public class PomCodeBuilder implements ICodeBuilder {

    private String pom = "pom.xml";

    /**
     *
     */
    private Map<String, String> paths;

    public PomCodeBuilder() {
        if (GeneratorProperties.useMaven()) {
            buildPath();
            buildCode();
        }

    }

    @Override
    public void buildPath() {
        paths = new HashMap<>();
        paths.put("basePath", getBasePath());
        PathUtil.mkdirs(paths);
    }

    @Override
    public void buildCode() {
        Map<String, String> templates = handleTemplates();
        CodeWriteUtil.writeFileNotAppend(templates);
    }

    @Override
    public Map<String, String> handleTemplates() {
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_SPRING_BOOT_POM);
        template.binding(GeneratorConstant.COMMON_VARIABLE);
        template.binding(GeneratorConstant.LOMBOK, GeneratorProperties.useLombok());
        template.binding("projectVersion", "${project.version}");
        template.binding("springVersion", "${spring.version}");
        template.binding("mybatisVersion", "${mybatis.version}");
        template.binding("jacksonVersion", "${jackson.version}");
        template.binding("slf4jVersion", "${slf4j.version}");
        template.binding("log4j2Version", "${log4j2.version}");
        template.binding("atomikosVersion", "${atomikos.version}");
        template.binding("project_basedir", "${project.basedir}");
        template.binding("project_build_directory", "${project.build.directory}");
        template.binding("project_build_finalName", "${project.build.finalName}");
        template.binding("project_version", "${project.version}");
        template.binding("project_groupId", "${project.groupId}");
        template.binding("useAssembly", GeneratorProperties.getAssembly());
        template.binding("useJTA", GeneratorProperties.isJTA());
        template.binding("isMultipleDataSource", GeneratorProperties.isMultipleDataSource());
        template.binding("jdkVersion", "${java.version}");
        template.binding("isUseDocker", GeneratorProperties.useDocker());

        Map<String, String> map = new HashMap<>(1);
        String path = PathUtil.connectPath(getBasePath(), pom);
        map.put(path, template.render());
        return map;
    }
}
