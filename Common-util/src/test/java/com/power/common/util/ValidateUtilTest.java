package com.power.common.util;

import org.junit.Test;

public class ValidateUtilTest {

    @Test
    public void testIsContainsForbiddenCharacter(){
        String str ="location.href";

        System.out.println(ValidateUtil.isContainsForbiddenCharacter(str));
    }
    @Test
    public void testIsFloat(){
        System.out.println(ValidateUtil.isFloat("10.a"));
    }

    @Test
    public void testIsArmyIdCard(){

        System.out.println(ValidateUtil.isPhone("15n41678167"));
    }
}
