package com.power.common.util;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class RegexUtilTest {

  @Test
  public void testIsMatches(){
    Set<String> pattern = new TreeSet<>();
    pattern.add("com.yun:.*");
    String pk = "com.yun:a";
    System.out.println(RegexUtil.isMatches(pattern,pk));
  }

}
