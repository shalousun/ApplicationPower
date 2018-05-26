package com.power.generator.builder;

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
    private final String DOCKER_FILR = "Dockerfile";

    /**
     * DockerFile template
     */
    private final String DOCKER_FILE_TPL = "docker/Dockerfile.btl";

    /**
     * Docker 自动化构建脚本
     */
    private final String DOCKER_SH_TPL = "docker/docker.sh";


    public DockerCodeBuilder(){
        if(GeneratorProperties.useDocker()){
            buildPath();
            buildCode();
        }
    }

    /**
     *
     */
    private Map<String,String> paths;

    @Override
    public void buildPath() {
        String mainPth = getMainPath();
        paths = new HashMap<>();
        paths.put(DOCKER_DIR,mainPth+ ConstVal.FILE_SEPARATOR+DOCKER_DIR);
        PathUtil.mkdirs(paths);

    }


    @Override
    public void buildCode() {
        Map<String,String> templates = handleTemplates();
        CodeWriteUtil.writeFileNotAppend(templates);
    }

    @Override
    public Map<String,String> handleTemplates() {
        Map<String,String> templates = new HashMap<>(2);
        Template dockerFile = BeetlTemplateUtil.getByName(DOCKER_FILE_TPL);
        dockerFile.binding(GeneratorConstant.APPLICATION_NAME, GeneratorProperties.applicationName());
        String dockerFileOutPath = paths.get(DOCKER_DIR)+ConstVal.FILE_SEPARATOR+"Dockerfile";
        templates.put(dockerFileOutPath,dockerFile.render());

        Template dockerSh = BeetlTemplateUtil.getByName(DOCKER_SH_TPL);
        dockerSh.binding("MYIMAGE","${MYIMAGE}");
        dockerSh.binding("DOCKER_REGISTRY","${DOCKER_REGISTRY}");
        String dockerShOutPath = getBasePath()+ConstVal.FILE_SEPARATOR+"docker.sh";
        templates.put(dockerShOutPath,dockerSh.render());

        return templates;
    }
}
