package com.power.common.util;

import com.power.common.enums.HttpCodeEnum;
import com.power.common.model.EnumDictionary;
import org.junit.Test;

import java.util.List;

/**
 * @author yu 2019/12/7.
 */
public class EnumUtilTest {

    /**
     * test get enum info
     */
    @Test
    public void testGetEnumInformation(){
        List<EnumDictionary> enumDictionaryList = EnumUtil.getEnumInformation(HttpCodeEnum.class,
                "code","message");
        System.out.println(enumDictionaryList);
    }
}
