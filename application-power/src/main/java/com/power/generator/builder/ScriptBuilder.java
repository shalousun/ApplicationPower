package com.power.generator.builder;


import com.power.generator.utils.BeetlTemplateUtil;
import com.power.generator.utils.GeneratorProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建生成采用assembly打包是的启动脚本
 *
 * @author sunyu 2017/12/15
 */
public class ScriptBuilder {


    /**
     * 生成assembly打包后的启动脚本
     *
     * @return
     */
    public Map<String, String> generateScripts() {
        Map<String, Object> placeholders = new HashMap<>();
        //处理start.sh特殊字符
        placeholders.put("arr_file", "(${arr[*]} $file)");
        placeholders.put("arr", "${arr[*]}");
        placeholders.put("extension", "${filename##*.}");
        placeholders.put("appName", GeneratorProperties.applicationName().toLowerCase());
        if (GeneratorProperties.useGradle()) {
            placeholders.put("jarName", GeneratorProperties.applicationName().toLowerCase() + "-1.0.jar");
        } else {
            placeholders.put("jarName", GeneratorProperties.applicationName().toLowerCase() + ".jar");
        }
        placeholders.put("logConfig", GeneratorProperties.getLogConfig());
        return BeetlTemplateUtil.getTemplatesRendered("template/assembly/bin", placeholders);
    }
}
