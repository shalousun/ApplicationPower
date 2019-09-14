package com.power.generator.code.impl;

import com.power.generator.code.ICodeBuilder;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.CodeWriteUtil;
import com.power.generator.utils.PathUtil;
import org.beetl.core.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2018/8/16.
 */
public class ApiDocTestCodeBuilder implements ICodeBuilder {

    private static final String TEST_PATH = "testBasePath";
    /**
     *
     */
    private Map<String, String> paths;

    public ApiDocTestCodeBuilder() {
        buildPath();
        buildCode();
    }

    @Override
    public void buildPath() {
        String testSrc = getTestSrcPath();
        String testBasePackPath = PathUtil.joinPath(testSrc, basePackage());
        paths = new HashMap<>();
        paths.put(TEST_PATH, testBasePackPath);
        PathUtil.mkdirs(paths);

    }


    @Override
    public void buildCode() {
        Map<String, String> templatesMap = handleTemplates();
        CodeWriteUtil.writeFileNotAppend(templatesMap);
    }

    @Override
    public Map<String, String> handleTemplates() {
        //key is path ,value is template content
        Map<String, String> templates = new HashMap<>();
        Template tpl = BeetlTemplateUtil.getByName("/test/DocCreatorTest.btl");
        tpl.binding(GeneratorConstant.COMMON_VARIABLE);
        String tplOut = PathUtil.connectPath(paths.get(TEST_PATH), "DocCreatorTest.java");
        templates.put(tplOut, tpl.render());
        return templates;
    }
}
