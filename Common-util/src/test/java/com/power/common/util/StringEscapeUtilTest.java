package com.power.common.util;

import org.junit.Test;

/**
 * @author yu 2020/4/24.
 */
public class StringEscapeUtilTest {

    /**
     * 对apache comment-text组件内工具包的补充
     */
    @Test
    public void testEscapeJava(){
        String me = "appid=\"xx\"&timestamp=\"\"";
        System.out.println(me);

        String str = me.replaceAll("&", "&amp;")
                .replaceAll("\"","&quot;")
                .replaceAll("<","&lt;")
                .replaceAll(">","&gt;");
        System.out.println(str);
    }
}
