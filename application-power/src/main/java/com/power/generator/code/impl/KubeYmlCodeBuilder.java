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
 * kuberates yaml
 * @author yu 2018/8/8.
 */
public class KubeYmlCodeBuilder implements ICodeBuilder {


    /**
     * k8s dir
     */
    private final String K8S_DIR = "k8s";


    /**
     * kubernates template
     */
    private final String K8S_DEPLOYMENT_TPL = "k8s/deployment.btl";



    public KubeYmlCodeBuilder(){
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
        String baseDir = getBasePath();
        paths = new HashMap<>();
        paths.put(K8S_DIR,baseDir+ ConstVal.FILE_SEPARATOR+K8S_DIR);
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
        Template k8sDeployment = BeetlTemplateUtil.getByName(K8S_DEPLOYMENT_TPL);
        k8sDeployment.binding(GeneratorConstant.COMMON_VARIABLE);
        String k8sDeployConfigPath = paths.get(K8S_DIR)+ConstVal.FILE_SEPARATOR+"deployment.yaml";
        templates.put(k8sDeployConfigPath,k8sDeployment.render());
        return templates;
    }
}
