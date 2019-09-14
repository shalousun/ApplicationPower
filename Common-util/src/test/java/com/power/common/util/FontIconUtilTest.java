package com.power.common.util;

import org.junit.Test;

import java.io.File;
import java.util.List;


/**
 * Created by yu on 2017/3/17.
 */
public class FontIconUtilTest {

    @Test
    public void testGetIcons() throws Exception {
        List<String> icons = FontIconUtil.getIcons(new File("D:\\me\\WEBT\\boco_6.3.4\\boco_moban\\css\\font-awesome.min.css"));
        StringBuilder builder = new StringBuilder();
        //拼装数据icon-picker插件
        builder.append("var icons = [");
        for (int i = 0; i < icons.size(); i++) {
            String icon = icons.get(i);
            String simpleName = icon.substring(icon.indexOf("-") + 1);
            if (i < icons.size() - 1) {
                builder.append("'").append(simpleName).append("',");
            } else {
                builder.append("'").append(simpleName).append("'");
            }
        }
        builder.append("];");
        System.out.println(builder.toString());
    }
}