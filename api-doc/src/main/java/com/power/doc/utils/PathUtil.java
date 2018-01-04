package com.power.doc.utils;

import com.boco.common.util.StringUtil;

import java.io.File;

public class PathUtil {

    /**
     *  获取java类名
     * @param parentDir
     * @param className 类名
     * @return
     */
    public static String javaFilePath(String parentDir, String className) {
        if (StringUtil.isEmpty(parentDir)) {
            parentDir = "java.io.tmpdir";
        }
        if (!org.apache.commons.lang.StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        className = className.replaceAll("\\.", "\\" + File.separator);
        return parentDir + className+".java";
    }
}
