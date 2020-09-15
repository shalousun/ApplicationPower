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
 * 生成docker模块
 *
 * @author yu
 */
public class DockerCodeBuilder implements ICodeBuilder {

    /**
     * docker目录
     */
    private final String DOCKER_DIR = "docker";

    /**
     * DockerFile
     */
    private final String DOCKER_FILE = "Dockerfile";

    /**
     * DockerFile template
     */
    private final String DOCKER_FILE_TPL = "docker/Dockerfile.btl";

    /**
     * Docker 自动化构建脚本
     */
    private final String DOCKER_MAVEN_SH_TPL = "docker/docker-maven.sh";

    /**
     * 基于gradle构建docker
     */
    private final String DOCKER_GRADLE_SH_TPL = "docker/docker-gradle.sh";

    private final String DOCKER_DOC_TPL = "docker/DOCKER.md";

    private final String DOCKER_DOC_GRADLE_TPL = "docker/DOCKER-GRADLE.md";
    /**
     *
     */
    private Map<String, String> paths;

    public DockerCodeBuilder() {
        if (GeneratorProperties.useDocker()) {
            buildPath();
            buildCode();
        }
    }

    @Override
    public void buildPath() {
        String mainPth = getMainPath();
        paths = new HashMap<>();
        paths.put(DOCKER_DIR, mainPth + ConstVal.FILE_SEPARATOR + DOCKER_DIR);
        PathUtil.mkdirs(paths);

    }


    @Override
    public void buildCode() {
        Map<String, String> templates = handleTemplates();
        CodeWriteUtil.writeFileNotAppend(templates);
    }

    @Override
    public Map<String, String> handleTemplates() {
        Map<String, String> templates = new HashMap<>(2);

        String applicationName;
        String dockerShTplPath;
        String dockerDocTpl;
        if (GeneratorProperties.useMaven()) {
            applicationName = GeneratorProperties.applicationName();
            dockerShTplPath = DOCKER_MAVEN_SH_TPL;
            dockerDocTpl = DOCKER_DOC_TPL;
        } else {
            applicationName = GeneratorProperties.applicationName() + "-1.0-SNAPSHOT";
            dockerShTplPath = DOCKER_GRADLE_SH_TPL;
            dockerDocTpl = DOCKER_DOC_GRADLE_TPL;
        }

        Template dockerFile = BeetlTemplateUtil.getByName(DOCKER_FILE_TPL);
        dockerFile.binding(GeneratorConstant.APPLICATION_NAME_LOWER, applicationName.toLowerCase());
        String dockerFileOutPath = paths.get(DOCKER_DIR) + ConstVal.FILE_SEPARATOR + "Dockerfile";
        templates.put(dockerFileOutPath, dockerFile.render());


        Template dockerSh = BeetlTemplateUtil.getByName(dockerShTplPath);
        dockerSh.binding(GeneratorConstant.COMMON_VARIABLE);
        dockerSh.binding("MYIMAGE", "${MYIMAGE}");
        dockerSh.binding("DOCKER_REGISTRY", "${DOCKER_REGISTRY}");
        String dockerShOutPath = PathUtil.connectPath(getBasePath(), "docker.sh");
        templates.put(dockerShOutPath, dockerSh.render());

        Template yamlShTemplate = BeetlTemplateUtil.getByName("assembly/bin/yaml.sh");
        String yamlShPath = PathUtil.connectPath(getBasePath(), "yaml.sh");
        templates.put(yamlShPath, yamlShTemplate.render());

        Template dockerDoc = BeetlTemplateUtil.getByName(dockerDocTpl);
        String docPath = PathUtil.connectPath(getBasePath(), "docs/DOCKER.md");
        dockerDoc.binding(GeneratorConstant.COMMON_VARIABLE);
        dockerDoc.binding("deployment_cfg", "deployment.yaml");
        templates.put(docPath, dockerDoc.render());

        return templates;
    }
}
