package com.power.common.util;

import com.power.common.filter.FileNameFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassUtil
 * @apiNote A utility class for handling class-related operations.
 * @javadoc
 * @author sunyu
 */
public class ClassUtil {

    /**
     * Scans all classes in a specified package.
     *
     * @param modelPackage The package name to scan.
     * @param root         The root directory of the project.
     * @return List of classes found.
     */
    public static List<Class> getClasses(String modelPackage, String root) {
        String buffer = root.replaceAll("\\\\", "/")
            + "/src/main/java/"
            + modelPackage.replace(".", "/");
        List<Class> list = new ArrayList<>();
        File entryFile = new File(buffer);
        File[] eFiles = entryFile.listFiles(new FileNameFilter("java"));
        try {
            for (File ef : eFiles) {
                String alName = ef.getName();
                String name = alName.split("\\.")[0];
                Class<?> c = Class.forName(modelPackage + "." + name);
                list.add(c);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Reads the source code of a Java file to find the name of a specific class.
     *
     * @param filePath        The path to the Java source file.
     * @param simpleClassName The simple class name to search for.
     * @return The full class name, or null if not found.
     */
    public static String getClassName(String filePath, String simpleClassName) {
        final String regex = "(class (.*?)\\{)|(interfase (.*?)\\{)";
        String curLine;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((curLine = reader.readLine()) != null) {
                Matcher matcher = Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE).matcher(curLine);
                if (matcher.find()) {
                    String selector = matcher.group();
                    String[] strs = selector.split(" ");
                    for (String str : strs) {
                        if (str.contains(simpleClassName)) {
                            return str;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get simple type name for val
     *
     * @param val object val
     * @return String
     */
    public static String getSimpleTypeName(Object val) {
        if (val instanceof String) {
            return "string";
        } else if (val instanceof Float) {
            return "float(float32)";
        } else if (val instanceof Double) {
            return "double(float64)";
        } else if (val instanceof Long) {
            return "long(int64)";
        } else if (val instanceof Short) {
            return "short";
        } else if (val instanceof Boolean) {
            return "boolean";
        } else if (val instanceof Integer) {
            return "int32";
        } else {
            return "object";
        }
    }
}
