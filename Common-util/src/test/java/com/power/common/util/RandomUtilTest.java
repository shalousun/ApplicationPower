package com.power.common.util;

import org.junit.Test;

/**
 * @author yu 2019/9/18.
 */
public class RandomUtilTest {

    @Test
    public void testRandomInt(){
        for(int i=0;i<100;i++){
            System.out.println(RandomUtil.randomInt(0,3));
        }

    }
}
