package com.power.doc.utils;

import com.github.javafaker.Faker;
import com.power.common.util.RandomUtil;
import com.power.common.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Description:
 * DocUtil
 *
 * @author yu 2018/06/11.
 */
public class DocUtil {

    private static Faker faker = new Faker(new Locale("zh-CN"));

    private static Map<String,String> fieldValue = new HashMap<>();

    static {
        fieldValue.put("uuid-string", UUID.randomUUID().toString());
        fieldValue.put("uid",UUID.randomUUID().toString());
        fieldValue.put("name-string",faker.name().username());
        fieldValue.put("teacherName-string",faker.name().username());
        fieldValue.put("studentName-string",faker.name().username());
        fieldValue.put("userName-string",faker.name().username());
        fieldValue.put("age-int",String.valueOf(RandomUtil.randomInt(0,70)));
        fieldValue.put("userAge-int",String.valueOf(RandomUtil.randomInt(0,70)));
        fieldValue.put("age-integer",String.valueOf(RandomUtil.randomInt(0,70)));
        fieldValue.put("userAge-integer",String.valueOf(RandomUtil.randomInt(0,70)));
        fieldValue.put("email-string",faker.internet().emailAddress());
        fieldValue.put("domain-string",faker.internet().domainName());
        fieldValue.put("phone-string",faker.phoneNumber().cellPhone());
        fieldValue.put("mobile-string",faker.phoneNumber().cellPhone());
        fieldValue.put("ip-string",faker.internet().ipV4Address());
        fieldValue.put("ipv4-string",faker.internet().ipV4Address());
        fieldValue.put("ipv6-string",faker.internet().ipV6Address());
        fieldValue.put("company-string",faker.company().name());
        fieldValue.put("createTime-long",String.valueOf(System.currentTimeMillis()));
        fieldValue.put("insertTime-long",String.valueOf(System.currentTimeMillis()));
        fieldValue.put("code-string",String.valueOf(RandomUtil.randomInt(100,99999)));
        fieldValue.put("message-string","success,fail".split(",")[RandomUtil.randomInt(0,1)]);
    }
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

    /**
     * 根据字段字段名和type生成字段值
     * @param type0 类型
     * @param filedName 字段名称
     * @return string
     */
    public static String getValByTypeAndFieldName(String type0,String filedName){
        String type = type0.contains("java.lang")?type0.substring(type0.lastIndexOf(".")+1,type0.length()):type0;
        String key = filedName+"-"+type.toLowerCase();
        String value = fieldValue.get(key);
        if(null == value){
            return jsonValueByType(type0);
        }else{
            if("string".equals(type.toLowerCase())){
                StringBuilder builder = new StringBuilder();
                builder.append("\"").append(value).append("\"");
                return builder.toString();
            }else{
                return value;
            }
        }
    }



    /**
     * 是否是合法的java类名称
     * @param className
     * @return
     */
    public static boolean isClassName(String className){
        if(StringUtil.isEmpty(className)){
            return false;
        }
        if(className.contains("<")&&!className.contains(">")){
            return false;
        }else if(className.contains(">")&&!className.contains("<")){
            return false;
        }else{
            return true;
        }
    }
}
