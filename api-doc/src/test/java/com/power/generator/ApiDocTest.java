package com.power.generator;

import com.alibaba.fastjson.JSON;
import com.power.common.util.StringUtil;
import com.power.doc.builer.ApiDocBuilder;
import com.power.doc.controller.Student;
import com.power.doc.utils.DocClassUtil;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Description:
 * ApiDoc测试
 *
 * @author yu 2018/06/11.
 */
public class ApiDocTest {

    @Test
    public void testBuilderControllersApi() {

        Map<String, String> strMap = new HashMap<>();
        strMap.put("hello", "world");
        strMap.put("sunyu", "chinese");
        List<Map<String, String>> listMap = new ArrayList<>();
        listMap.add(strMap);
//        System.out.println(type);
        System.out.println(JSON.toJSONString(listMap));

       ApiDocBuilder.builderControllersApi("d:\\md",true);
//        String name = "java.util.Map<java.lang.String,com.power.doc.controller.User>,com.power.doc.controller.User,java.util.Map<java.lang.String,com.power.doc.controller.User>";
//          String[] arr = name.split(",");
//          String[] arr1 =DocClassUtil.classNameFix(arr);
//        System.out.println(JSON.toJSONString(arr));
//        System.out.println(JSON.toJSONString(arr1));
//        List<String> list = getSimpleGicName(name);
//        System.out.println(JSON.toJSONString(list));
    }

}
