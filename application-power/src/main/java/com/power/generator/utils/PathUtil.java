package com.power.generator.utils;

import com.power.generator.constant.ConstVal;

import java.io.File;
import java.util.Map;

/**
 * Created by yu on 2017/4/29.
 */
public class PathUtil {

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    public static String joinPath(String parentDir, String packageName) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!org.apache.commons.lang3.StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", ConstVal.FILE_SEPARATOR + File.separator);
        return parentDir + packageName;
    }

    /**
     * 两个路径的连接
     *
     * @param parentDir
     * @param path
     * @return
     */
    public static String connectPath(String parentDir, String path) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!org.apache.commons.lang3.StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        return parentDir + File.separator + path;
    }

    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    public static String joinPackage(String parent, String subPackage) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(parent)) {
            return subPackage;
        }
        return parent + "." + subPackage;
    }

    /**
     * 创建代码输出路径
     *
     * @param pathInfo 路径信息
     */
    public static void mkdirs(Map<String, String> pathInfo) {
        for (Map.Entry<String, String> entry : pathInfo.entrySet()) {
            File dir = new File(entry.getValue());
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

}
