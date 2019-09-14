package com.power.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过读取前端字体题库的css文件来生成字体库列表
 *
 * @author sunyu
 */
public class FontIconUtil {

    /**
     * 读取font-awesome.min.css等字体库样式文件获取图标列表
     * 注意：在读取文件前需要将css文件格式化，即转化为非压缩模式
     *
     * @param cssFile css file
     * @return List
     */
    public static List<String> getIcons(File cssFile) {
        List<String> icons = new ArrayList<>(790);
        final String regex = ".(.*?):before\\s*\\{";
        String curLine;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(cssFile));
            while ((curLine = reader.readLine()) != null) {
                Matcher matcher = Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE).matcher(curLine);
                if (matcher.find()) {
                    String selector = matcher.group();
                    if (selector.contains(",")) {
                        String[] iconArr = selector.split(",");
                        for (String str : iconArr) {
                            icons.add(getIcon(str));
                        }
                    } else {
                        icons.add(getIcon(selector));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return icons;
    }

    /**
     * 获取单个图标名称
     *
     * @param selector css selector
     * @return String
     */
    private static String getIcon(String selector) {
        return selector.substring(selector.indexOf(".") + 1, selector.indexOf(":"));
    }
}
