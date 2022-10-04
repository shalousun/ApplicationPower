package com.power.common.util;

import com.power.common.model.BaseResult;
import org.junit.jupiter.api.Test;


import java.util.Map;

/**
 * @author yu 2022/10/4.
 */
public class ClassUtilTest {

    @Test
    public void testGetFinalFieldValue() throws IllegalAccessException {
        Map<String,String> map = ClassUtil.getFinalFieldValue(DateTimeUtil.class);
        for (Map.Entry<String,String> entry:map.entrySet()) {
            System.out.println(StringUtil.fillStringByArgs("key={0},value={1}", entry.getKey(),entry.getValue()));
        }
    }
}
