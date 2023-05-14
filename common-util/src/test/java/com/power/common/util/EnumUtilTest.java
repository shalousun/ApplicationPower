package com.power.common.util;

import com.power.common.enums.HttpCodeEnum;
import com.power.common.model.EnumDictionary;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author yu 2019/12/7.
 */
public class EnumUtilTest {

    /**
     * test get enum info
     */
    @Test
    public void testGetEnumInformation() {
        List<EnumDictionary> enumDictionaryList = EnumUtil.getEnumInformation(null,
                "code", "message");
        System.out.println(enumDictionaryList);
    }

    @Test
    public void testGetEnumInformation2() {
        Map<String, List<Map<String, Object>>> map = EnumUtil.getEnumInformation(HttpCodeEnum.class);
        System.out.println(map);
    }

    @Test
    public void testGetEnumNames() {
        List<String> list = EnumUtil.getNames(HttpCodeEnum.class);
        System.out.println(list);
    }
}
