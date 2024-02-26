package com.power.common.util;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionUtilTest {

    @Test
    public void testPartition() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,0);

        List<List<Integer>> result = CollectionUtil.partition(list, 3);

        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9),Arrays.asList(0));

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testPartitionNull() {
        assertThrows(NullPointerException.class, () -> CollectionUtil.partition(null, 3));
    }
}
