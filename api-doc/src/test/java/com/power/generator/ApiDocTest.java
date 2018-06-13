package com.power.generator;

import com.alibaba.fastjson.JSON;
import com.power.doc.builer.ApiDocBuilder;
import com.power.doc.controller.Student;
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
//        String name = "com.power.doc.controller.Teacher<java.util.List<com.power.doc.controller.User>,com.power.doc.controller.User>";
//        List<String> list = getSimpleGicName(name);
//        System.out.println(JSON.toJSONString(list));
    }

    public static List<String> getSimpleGicName(String returnType) {
        List<String> list = new ArrayList<>();
        if (returnType.contains("<")) {
            String pre = returnType.substring(0,returnType.indexOf("<"));
            if("java.util.Map".equals(pre)){
                String key = returnType.substring(returnType.indexOf("<")+1,returnType.indexOf(","));
                String value = returnType.substring(returnType.indexOf(",")+1,returnType.lastIndexOf(">"));
                list.add(key);
                list.add(value);
                return list;
            }
            String type = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf(">"));
            char last = type.charAt(type.length() - 1);
            if(62 == last){
                System.out.println(type);
            }
            list.addAll(Arrays.asList(type.split(",")));
            return list;
        } else {
            list.addAll(Arrays.asList(returnType.split(" ")));
        }
        return list;
    }

    private String[] getMapKeyValueType(String gName){
        String[] arr = new String[2];
        String key = gName.substring(gName.indexOf("<")+1,gName.indexOf(","));
        String value = gName.substring(gName.indexOf(",")+1,gName.lastIndexOf(">"));
        arr[0] = key;
        arr[1] = value;
        return arr;
    }

    /**
     * 递归获取泛型化返回值
     *
     * @param type
     * @param counter
     * @return
     */
    public static List<String> getTypes(Type type, int counter) {
        List<String> nameList = new ArrayList<>();
        String typeName = type.getTypeName();
        int index = typeName.indexOf("<");
        if (index > -1) {
            nameList.add(typeName.substring(0, index) + "-" + counter);
        } else {
            nameList.add(typeName + "-" + counter);
        }
        if (type instanceof ParameterizedType) {
            counter++;
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            for (Type type0 : actualTypeArguments) {
                nameList.addAll(getTypes(type0, counter));
            }
        }
        return nameList;
    }
}
