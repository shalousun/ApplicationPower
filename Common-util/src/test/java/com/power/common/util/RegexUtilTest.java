package com.power.common.util;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

public class RegexUtilTest {

  @Test
  public void testIsMatches(){
    Set<String> pattern = new TreeSet<>();
    pattern.add("com.yun:.*");
    String pk = "com.yun:a";
    System.out.println(RegexUtil.isMatches(pattern,pk));

    Optional<String> optional = Optional.empty();
    if(optional.isPresent()){
      System.out.println(optional.get());
    } else {
      System.out.println("没有数据");
    }
  }

}
