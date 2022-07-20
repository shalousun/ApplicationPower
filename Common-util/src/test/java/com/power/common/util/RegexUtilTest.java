package com.power.common.util;

import java.util.Map;
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

  @Test
  public void testExtractMap() {
    String msg = "2022-07-20 16:55:05.415 [10.10.10.10] [nio-8080-exec-4] INFO org.springframework.web.servlet.DispatcherServlet@initServletBean:547 - Completed initialization in 9 ms\n";
    String reg = "(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+\\.\\d+)\\s+\\[([^]]+)]\\s+\\[([^]]+)]\\s+(\\w+)\\s+(\\S+)\\s-\\s(.*)";
    String[] arr = RegexUtil.extractStrings(reg,msg);
    for(String str:arr) {
      System.out.println(str);
    }
  }

}
