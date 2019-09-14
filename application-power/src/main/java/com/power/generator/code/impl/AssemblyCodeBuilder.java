package com.power.generator.code.impl;

import com.power.common.util.FileUtil;
import com.power.generator.builder.ScriptBuilder;
import com.power.generator.code.ICodeBuilder;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.constant.SpringBootProjectConfig;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.CodeWriteUtil;
import com.power.generator.utils.GeneratorProperties;
import com.power.generator.utils.PathUtil;
import org.beetl.core.Template;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringBoot Assembly 打包方案
 *
 * @author yu 2018/05/27.
 */
public class AssemblyCodeBuilder implements ICodeBuilder {

    /**
     * Assembly相关代码输出目录
     */
    private Map<String, String> paths;

    public AssemblyCodeBuilder() {
        if (GeneratorProperties.getAssembly() && GeneratorProperties.useMaven()) {
            buildPath();
            buildCode();
        }
    }

    @Override
    public void buildPath() {
        paths = new HashMap<>(5);
        String basePath = getBasePath();
        SpringBootProjectConfig config = new SpringBootProjectConfig();
        paths.put(ConstVal.ASSEMBLY_DIR, PathUtil.connectPath(basePath, config.getAssemblyRoot()));
        paths.put(ConstVal.ASSEMBLY_BIN, PathUtil.connectPath(basePath, config.getAssemblyBin()));
        paths.put(ConstVal.ASSEMBLY_CFG, PathUtil.connectPath(basePath, config.getAssemblyCfg()));

        //使用assembly打包自动创建部署文档
        String docsDir = basePath + ConstVal.FILE_SEPARATOR + ConstVal.DOCS_PATH;
        paths.put(ConstVal.DOCS_PATH, docsDir);

        PathUtil.mkdirs(paths);
    }

    @Override
    public void buildCode() {
        //创建脚本
        String binPath = paths.get(ConstVal.ASSEMBLY_BIN);
        Map<String, String> scripts = new ScriptBuilder().generateScripts();
        for (Map.Entry<String, String> entry : scripts.entrySet()) {
            FileUtil.writeFileNotAppend(entry.getValue(), binPath + ConstVal.FILE_SEPARATOR + entry.getKey());
        }
        //复制assembly.xml
        String assemblyRoot = paths.get(ConstVal.ASSEMBLY_DIR);
        String assemblyXml = Thread.currentThread().getContextClassLoader().getResource(ConstVal.TPL_ASSEMBLY_XML).getPath();
        FileUtil.nioTransferCopy(new File(assemblyXml), new File(assemblyRoot + ConstVal.FILE_SEPARATOR + "assembly.xml"));

        Map<String, String> templatesMap = handleTemplates();
        CodeWriteUtil.writeFileNotAppend(templatesMap);
    }

    @Override
    public Map<String, String> handleTemplates() {
        Map<String, String> templatesMap = new HashMap<>(1);

        //处理部署文档模板
        String deployDoc = paths.get(ConstVal.DOCS_PATH);
        Template deployTemplate = BeetlTemplateUtil.getByName(ConstVal.TPL_DEPLOY_MD);
        deployTemplate.binding(GeneratorConstant.COMMON_VARIABLE);
        String docOutPath = PathUtil.connectPath(deployDoc, "DEPLOY.md");
        templatesMap.put(docOutPath, deployTemplate.render());

        return templatesMap;
    }
}
