package com.power.builder;


import com.power.utils.BeetlTemplateUtil;
import com.power.utils.GeneratorProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建生成采用assembly打包是的启动脚本
 * @author sunyu 2017/12/15
 *
 */
public class ScriptBuilder {


    /**
     * 生成assembly打包后的启动脚本
     * @return
     */
    public Map<String,String> generateScripts(){
        Map<String,Object> placeholders = new HashMap<>();
        placeholders.put("appName", GeneratorProperties.applicationName());
        return BeetlTemplateUtil.getTemplatesRendered("template/assembly/bin",placeholders);
    }
}
