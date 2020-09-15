package com.power.generator.code.impl;

import com.power.common.util.StringUtil;
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
 * kubernetes yaml
 *
 * @author yu 2018/8/8.
 */
public class KubeYmlCodeBuilder implements ICodeBuilder {


    /**
     * k8s dir
     */
    private final String K8S_DIR = "k8s";


    /**
     * kubernetes template
     */
    private final String K8S_DEPLOYMENT_TPL = "k8s/deployment.yml";
    //canary deployment
    private final String K8S_CANARY_DEPLOYMENT_TPL = "k8s/canary-deployment.yml";

    //canary ingress
    private final String K8S_CANARY_INGRESS_TPL = "k8s/canary-ingress.yml";
    //canary to prod ingress
    private final String K8S_CANARY_TO_PROD_INGRESS_TPL = "k8s/canary-to-prod-ingress.yml";

    private Map<String, String> paths;

    public KubeYmlCodeBuilder() {
        if (GeneratorProperties.useDocker()) {
            buildPath();
            buildCode();
        }
    }

    @Override
    public void buildPath() {
        String baseDir = getBasePath();
        paths = new HashMap<>();
        paths.put(K8S_DIR, baseDir + ConstVal.FILE_SEPARATOR + K8S_DIR);
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
        String applicationName = GeneratorProperties.applicationName();
        String lowerCaseAppName = applicationName.toLowerCase();
        Template k8sDeployment = BeetlTemplateUtil.getByName(K8S_DEPLOYMENT_TPL);
        k8sDeployment.binding(GeneratorConstant.COMMON_VARIABLE);
        k8sDeployment.binding("domain", getDomain(applicationName));
        String k8sDeployConfigPath = paths.get(K8S_DIR) + ConstVal.FILE_SEPARATOR + lowerCaseAppName +"-deployment.yaml";
        templates.put(k8sDeployConfigPath, k8sDeployment.render());


        String handleVersion = GeneratorProperties.appVersion();
        handleVersion = handleVersion.replace(".","-");

        Template k8sCanaryDeployment = BeetlTemplateUtil.getByName(K8S_CANARY_DEPLOYMENT_TPL);
        k8sCanaryDeployment.binding(GeneratorConstant.COMMON_VARIABLE);
        k8sCanaryDeployment.binding("handleVersion",handleVersion);
        k8sCanaryDeployment.binding("domain", getDomain(applicationName));
        String k8sCanaryDeployConfigPath = paths.get(K8S_DIR) + ConstVal.FILE_SEPARATOR + lowerCaseAppName +"-canary-deployment.yaml";
        templates.put(k8sCanaryDeployConfigPath,  k8sCanaryDeployment.render());

        Template canaryIngress = BeetlTemplateUtil.getByName(K8S_CANARY_INGRESS_TPL);
        canaryIngress.binding(GeneratorConstant.COMMON_VARIABLE);
        canaryIngress.binding("handleVersion",handleVersion);
        canaryIngress.binding("domain", getDomain(applicationName));
        String k8sCanaryIngressConfigPath = paths.get(K8S_DIR) + ConstVal.FILE_SEPARATOR + lowerCaseAppName +"-canary-ingress.yaml";
        templates.put(k8sCanaryIngressConfigPath,  canaryIngress.render());

        Template canaryToProdIngress = BeetlTemplateUtil.getByName(K8S_CANARY_TO_PROD_INGRESS_TPL);
        canaryToProdIngress.binding(GeneratorConstant.COMMON_VARIABLE);
        canaryToProdIngress.binding("handleVersion",handleVersion);
        canaryToProdIngress.binding("domain", getDomain(applicationName));
        String k8sCanaryToProdIngressConfigPath = paths.get(K8S_DIR) + ConstVal.FILE_SEPARATOR + lowerCaseAppName +"-canary-to-prod-ingress.yaml";
        templates.put(k8sCanaryToProdIngressConfigPath,  canaryToProdIngress.render());

        return templates;
    }

    /**
     * get domain by application name
     *
     * @param applicationName
     * @return
     */
    private String getDomain(String applicationName) {
        if (StringUtil.isNotEmpty(applicationName)) {
            applicationName = applicationName.replaceAll("-", "");
            applicationName = StringUtil.camelToUnderline(StringUtil.firstToLowerCase(applicationName));
            applicationName = applicationName.replaceAll("_", ".");
            return applicationName.toLowerCase();
        }
        return null;
    }
}
