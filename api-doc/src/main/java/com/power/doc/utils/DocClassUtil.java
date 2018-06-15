package com.power.doc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * class的工具
 *
 * @author yu 2018//14.
 */
public class DocClassUtil {

    /**
     * Check if it is the basic data type of json data
     * @param type0 java class name
     * @return boolean
     */
    public static boolean isPrimitive(String type0) {
        String type = type0.contains("java.lang") ? type0.substring(type0.lastIndexOf(".") + 1, type0.length()) : type0;
        if ("Integer".equals(type) || "int".equals(type) || "Long".equals(type) || "long".equals(type)
                || "Double".equals(type) || "double".equals(type) || "Float".equals(type) || "float".equals(type) ||
                "BigDecimal".equals(type) || "String".equals(type) || "boolean".equals(type) || "Boolean".equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get class names by generic class name
     * @param returnType generic class name
     * @return array of string
     */
    public static String[] getSimpleGicName(String returnType) {
        if (returnType.contains("<")) {
            String pre = returnType.substring(0,returnType.indexOf("<"));
            if("java.util.Map".equals(pre)){
                return getMapKeyValueType(returnType);
            }
            String type = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf(">"));
            String[] arr = type.split(",");
            return classNameFix(arr);
        } else {
            return returnType.split(" ");
        }
    }

    /**
     * Get a simple type name from a generic class name
     * @param gicName Generic class name
     * @return String
     */
    public static String getSimpleName(String gicName){
        if(gicName.contains("<")){
            return gicName.substring(0,gicName.indexOf("<"));
        }else{
            return gicName;
        }
    }

    /**
     * Automatic repair of generic split class names
     * @param arr arr of class name
     * @return array of String
     */
    public static String[] classNameFix(String[] arr){
        List<String> classes = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            if(DocUtil.isClassName(arr[i])){
                indexList.add(i);
                classes.add(arr[i]);
            }else {
                if(!indexList.contains(i)&&!indexList.contains(i+1)){
                    classes.add(arr[i]+","+arr[i+1]);
                    indexList.add(i);
                    indexList.add(i+1);
                }
            }
        }
        return classes.toArray(new String[classes.size()]);
    }

    /**
     * get map key and value type name populate into array.
     * @param gName generic class name
     * @return array of string
     */
    public static String[] getMapKeyValueType(String gName){
        String[] arr = new String[2];
        String key = gName.substring(gName.indexOf("<")+1,gName.indexOf(","));
        String value = gName.substring(gName.indexOf(",")+1,gName.lastIndexOf(">"));
        arr[0] = key;
        arr[1] = value;
        return arr;
    }
}
