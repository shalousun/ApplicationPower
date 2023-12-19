package com.power.common.util;

import org.junit.jupiter.api.Test;


/**
 * @author yu 2020/4/24.
 */
public class StringEscapeUtilTest {

    /**
     * 对apache comment-text组件内工具包的补充
     */
    @Test
    public void testEscapeJava() {
        String me = "appid=\"xx\"&timestamp=\"\"";
        System.out.println(me);

        String str = me.replaceAll("&", "&amp;")
                .replaceAll("\"", "&quot;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
        System.out.println(str);
    }

    @Test
    public void testUnescapeJava() {
        String me = "我appid=><%&\"xx\"&timestamp=\"\"";
        System.out.println(me);

        String escapeJava = StringEscapeUtil.escapeJava(me);

        System.out.println("escapeJava: " + escapeJava);
        System.out.println("unescapeJava: " + StringEscapeUtil.unescapeJava(escapeJava));

    }
}
