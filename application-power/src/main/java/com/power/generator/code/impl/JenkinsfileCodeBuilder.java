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
 * @author yu 2021/3/22.
 */
public class JenkinsfileCodeBuilder implements ICodeBuilder {


    private String Jenkinsfile = "Jenkinsfile";

    private Map<String, String> paths;

    public JenkinsfileCodeBuilder(){
        buildPath();
        buildCode();
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
        Template template = BeetlTemplateUtil.getByName(ConstVal.TPL_JENKINS_PIPELINE);
        template.binding(GeneratorConstant.COMMON_VARIABLE);
        Map<String, String> map = new HashMap<>(1);
        String path = PathUtil.connectPath(getBasePath(),Jenkinsfile);
        map.put(path, template.render());
        return map;
    }
}
