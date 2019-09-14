package com.power.common.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidateUtilTest {

    @Test
    public void testIsContainsForbiddenCharacter() {
        String str = "location.href";

        System.out.println(ValidateUtil.isContainsForbiddenCharacter(str));
    }

    @Test
    public void testIsFloat() {
        System.out.println(ValidateUtil.isFloat("10.a"));
    }

    @Test
    public void testIsArmyIdCard() {

        System.out.println(ValidateUtil.isPhone("15n41678167"));
    }

    @Test
    public void testIsEmail() {
        List<String> emails = new ArrayList<>();
        emails.add("user@domain.com");
        emails.add("user@domain.co.in");
        emails.add("user.name@domain.com");
        emails.add("user_name@domain.com");
        emails.add("username@yahoo.corporate.in");

        //Invalid emails
        emails.add(".username@yahoo.com");
        emails.add("username@yahoo.com.");
        emails.add("username@yahoo..com");
        emails.add("username@yahoo.c");
        emails.add("username@yahoo.corporate");

        emails.forEach(str -> System.out.println(ValidateUtil.isEmail(str)));
    }
}
