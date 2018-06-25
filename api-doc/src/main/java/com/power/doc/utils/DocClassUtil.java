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
     *
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
     *
     * @param returnType generic class name
     * @return array of string
     */
    public static String[] getSimpleGicName(String returnType) {
        if (returnType.contains("<")) {
            String pre = returnType.substring(0, returnType.indexOf("<"));
            if ("java.util.Map".equals(pre)) {
                return getMapKeyValueType(returnType);
            }
            String type = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf(">"));
            if ("java.util.List".equals(pre)) {
                return type.split(" ");
            }
            String[] arr = type.split(",");
            return classNameFix(arr);
        } else {
            return returnType.split(" ");
        }
    }

    /**
     * Get a simple type name from a generic class name
     *
     * @param gicName Generic class name
     * @return String
     */
    public static String getSimpleName(String gicName) {
        if (gicName.contains("<")) {
            return gicName.substring(0, gicName.indexOf("<"));
        } else {
            return gicName;
        }
    }

    /**
     * Automatic repair of generic split class names
     *
     * @param arr arr of class name
     * @return array of String
     */
    public static String[] classNameFix(String[] arr) {
        List<String> classes = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        int globIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (classes.size() > 0) {
                int index = classes.size() - 1;
                if (!DocUtil.isClassName(classes.get(index))) {
                    globIndex = globIndex + 1;
                    if (globIndex < arr.length) {
                        indexList.add(globIndex);
                        String className = classes.get(index) + "," + arr[globIndex];
                        classes.set(index, className);
                    }

                } else {
                    globIndex = globIndex + 1;
                    if (globIndex < arr.length) {
                        if (DocUtil.isClassName(arr[globIndex])) {
                            indexList.add(globIndex);
                            classes.add(arr[globIndex]);
                        } else {
                            if (!indexList.contains(globIndex) && !indexList.contains(globIndex + 1)) {
                                indexList.add(globIndex);
                                classes.add(arr[globIndex] + "," + arr[globIndex + 1]);
                                globIndex = globIndex + 1;
                                indexList.add(globIndex);
                            }
                        }
                    }
                }
            } else {
                if (DocUtil.isClassName(arr[i])) {
                    indexList.add(i);
                    classes.add(arr[i]);
                } else {
                    if (!indexList.contains(i) && !indexList.contains(i + 1)) {
                        globIndex = i + 1;
                        classes.add(arr[i] + "," + arr[globIndex]);
                        indexList.add(i);
                        indexList.add(i + 1);
                    }
                }
            }
        }
        return classes.toArray(new String[classes.size()]);
    }

    /**
     * get map key and value type name populate into array.
     *
     * @param gName generic class name
     * @return array of string
     */
    public static String[] getMapKeyValueType(String gName) {
        String[] arr = new String[2];
        String key = gName.substring(gName.indexOf("<") + 1, gName.indexOf(","));
        String value = gName.substring(gName.indexOf(",") + 1, gName.lastIndexOf(">"));
        arr[0] = key;
        arr[1] = value;
        return arr;
    }

    /**
     * Convert the parameter types exported to the api document
     *
     * @param javaTypeName java simple typeName
     * @return String
     */
    public static String processTypeNameForParams(String javaTypeName) {
        if (javaTypeName.length() == 1) {
            return "object";
        }
        switch (javaTypeName) {
            case "java.lang.String":
                return "string";
            case "string":
                return "string";
            case "java.util.List":
                return "array";
            case "list":
                return "array";
            case "java.lang.Integer":
                return "int";
            case "integer":
                return "int";
            case "int":
                return "int";
            case "double":
                return "number";
            case "java.lang.Long":
                return "number";
            case "long":
                return "number";
            case "java.lang.Float":
                return "number";
            case "float":
                return "number";
            case "java.lang.Boolean":
                return "boolean";
            case "boolean":
                return "boolean";
            case "map":
                return "map";
            default:
                return "object";
        }

    }


}
