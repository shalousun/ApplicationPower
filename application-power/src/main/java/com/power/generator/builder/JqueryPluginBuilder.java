package com.power.generator.builder;

import com.power.common.util.StringUtil;
import com.power.generator.constant.ConstVal;
import com.power.generator.constant.GeneratorConstant;
import com.power.generator.utils.BeetlTemplateUtil;
import org.beetl.core.Template;

/**
 * @author sunyu on 2017/11/09
 */
public class JqueryPluginBuilder {
    private static final String TPL_PLUGIN = "front" + ConstVal.FILE_SEPARATOR + "jquery-plugin.btl";
    private static final String PLUGIN_NAME = "pluginName";
    private static final String PLUGIN_NAME_UPER = "pluginObject";

    /**
     * 创建jquery插件
     *
     * @param pluginName
     */
    public String writeBuilder(String pluginName) {
        Template daoTemplate = BeetlTemplateUtil.getByName(TPL_PLUGIN);
        daoTemplate.binding(GeneratorConstant.COMMON_VARIABLE);//作者
        daoTemplate.binding(PLUGIN_NAME, pluginName);//类名
        daoTemplate.binding(PLUGIN_NAME_UPER, StringUtil.firstToUpperCase(pluginName));//基包名
        return daoTemplate.render();
    }
}
