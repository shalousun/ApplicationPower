package com.power.common.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML util
 *
 * @author yu 2022/10/4.
 */
public class HtmlUtil {

    public static final String LINE_BREAKS = "(\r\n|\r|\n|\n\r)";

    /**
     * convert line breaks
     *
     * @param str Input text
     * @return Filtered text
     */
    public static String lineBreaksToBr(String str) {
        Pattern CRLF = Pattern.compile(LINE_BREAKS);
        Matcher m = CRLF.matcher(str);
        if (m.find()) {
            str = m.replaceAll("<br/>");
        }
        return str;
    }

    /**
     * replace special character of html
     *
     * @param html html tag
     * @return String after replaced}
     */
    public static String replaceMobileHtml(String html) {
        if (Objects.isNull(html)) {
            return StringUtil.EMPTY;
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * replace special character of html
     *
     * @param html html tag
     * @return String after replaced}
     */
    public static String replaceHtml(String html) {
        if (Objects.isNull(html)) {
            return StringUtil.EMPTY;
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }
}
