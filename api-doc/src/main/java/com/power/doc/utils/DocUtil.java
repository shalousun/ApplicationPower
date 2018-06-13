package com.power.doc.utils;

import com.power.common.util.RandomUtil;

/**
 * Description:
 * DocUtil
 *
 * @author yu 2018/06/11.
 */
public class DocUtil {

    /**
     * 随机生成json值
     * @param type0
     * @return
     */
    public static String jsonValueByType(String type0){
        String type = type0.contains("java.lang")?type0.substring(type0.lastIndexOf(".")+1,type0.length()):type0;
        String value = RandomUtil.randomValueByType(type);
        if("Integer".equals(type)||"int".equals(type)||"Long".equals(type)||"long".equals(type)
                ||"Double".equals(type)||"double".equals(type)|| "Float".equals(type)||"float".equals(type)||
                "BigDecimal".equals(type)||"boolean".equals(type)||"Boolean".equals(type)){
            return value;
        }else{
            StringBuilder builder = new StringBuilder();
            builder.append("\"").append(value).append("\"");
            return builder.toString();
        }
    }
}
