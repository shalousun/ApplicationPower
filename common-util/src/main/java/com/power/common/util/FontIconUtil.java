package com.power.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generate a font library icon list by reading the css file of the front-end font library
 * @javadoc
 * @author sunyu
 */
public class FontIconUtil {

    /**
     * Read font library style files such as font-awesome.min.css to get a list of icons
     * Note: Before reading the file, you need to format the css file, that is, convert it to non-compressed mode
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
     * Get a single icon name
     *
     * @param selector css selector
     * @return String
     */
    private static String getIcon(String selector) {
        return selector.substring(selector.indexOf(".") + 1, selector.indexOf(":"));
    }
}
