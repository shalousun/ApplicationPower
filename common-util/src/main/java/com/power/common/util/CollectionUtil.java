package com.power.common.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CollectionUtil class provides utility methods for working with collections.
 * @javadoc
 * @author yu
 * @since JDK 1.8+
 */
public class CollectionUtil {

    /**
     * isNotEmpty:(Check if the collection is empty, not empty return true)
     *
     * @param <T>        type of param
     * @param collection collection
     * @return boolean
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }


    /**
     * checkEmpty:(Check if the collection is empty, return true if it is empty)
     *
     * @param <T>        type of param
     * @param collection collection
     * @return boolean
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * slice list
     *
     * @param <T>    type of param
     * @param source data of list
     * @param from   from index(included)
     * @param to     to index(not included)
     * @return list
     */
    public static <T> List<T> slice(List<T> source, int from, int to) {
        if (CollectionUtil.isEmpty(source)) {
            return null;
        }
        int size = source.size();
        if (to > size) {
            return slice(source.stream(), from, size).collect(Collectors.toList());
        } else {
            return slice(source.stream(), from, to).collect(Collectors.toList());
        }
    }

    /**
     * slice
     *
     * @param stream     Stream
     * @param startIndex (included)
     * @param endIndex   (not included)
     * @param <T>        type of param
     * @return Stream
     */
    public static <T> Stream<T> slice(Stream<T> stream, int startIndex, int endIndex) {
        return stream
                // Convert the stream to list
                .collect(Collectors.toList())
                // Fetch the subList between the specified index
                .subList(startIndex, endIndex)
                // Convert the subList to stream
                .stream();
    }

    /**
     * partition list by size
     *
     * @param list List of data
     * @param size page size
     * @param <T>  type of param
     * @return List
     */
    public static <T> List<List<T>> partition(List<T> list, final int size) {
        if (list != null && list.size() > 0) {
            return new ArrayList<>(IntStream.range(0, list.size()).boxed().collect(
                    Collectors.groupingBy(e -> e / size, Collectors.mapping(list::get, Collectors.toList())
                    )).values());
        } else {
            throw new NullPointerException("List can't be null");
        }

    }

    /**
     * Converts an array to a List, and returns an empty list if the array is empty
     *
     * @param <T> type of param
     * @param a   object array
     * @return List
     */
    @SafeVarargs
    public static <T> List<T> asList(T... a) {
        if (null != a) {
            return Arrays.asList(a);
        } else {
            return new ArrayList<>(0);
        }
    }


    /**
     * Poorly merge the values of the two lists cross-merge, merge in sequence,
     * for the case where the set data is not empty,
     * The value of result1 The first value is placed first
     *
     * @param <T>     type of param
     * @param result1 first of list
     * @param result2 second of list
     * @return List
     */
    public static <T> List<T> mergeAndSwap(List<T> result1, List<T> result2) {

        // If both collections are empty, return an empty collection
        if (CollectionUtil.isEmpty(result1) && CollectionUtil.isEmpty(result2)) {
            return new ArrayList<>(0);
        }

        // Use only the result of result2, no need to merge
        if (CollectionUtil.isEmpty(result1) && CollectionUtil.isNotEmpty(result2)) {
            return result2;
        }

        // Only use the result of result1, no need to merge
        if (CollectionUtil.isNotEmpty(result1) && CollectionUtil.isEmpty(result2)) {
            return result1;
        }

        int a = result1.size();
        int b = result2.size();
        int size = a + b;
        // Cross-merge the values ​​of two list collections
        List<T> finalResult = new ArrayList<>(size);
        if (a >= b) {
            for (int i = 0; i < size; i++) {
                if (i > (b << 1) - 1) {
                    finalResult.add(result1.get(i - b));
                } else {
                    if ((i & 1) == 0 && i >> 1 < a) {
                        finalResult.add(result1.get(i >> 1));
                    }
                }
                if ((i & 1) == 1 && (i - 1) >> 1 < b) {
                    finalResult.add(result2.get((i - 1) >> 1));
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if ((i & 1) == 0 && (i >> 1) < a) {
                    finalResult.add(result1.get(i >> 1));
                }
                if ((i & 1) == 1 && (i >> 1) < a - 1) {
                    finalResult.add(result2.get((i - 1) >> 1));
                }
                if (i >= (a << 1) - 1) {
                    finalResult.add(result2.get(i - a));
                }
            }
        }
        return finalResult;
    }


    /**
     * Clear the invalid map data in the List collection,
     * Even if these specified fields in the map have values, as long as other keys are invalid data will be removed,
     * but 0 does not mean no value
     *
     * @param list       list of map data
     * @param exceptKeys except keys
     * @return List
     */
    public static List<Map<String, Object>> filterEmpty(List<Map<String, Object>> list, String... exceptKeys) {
        List<Map<String, Object>> tempList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            int a = map.size();
            int counter = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (String str : exceptKeys) {
                    if (!entry.getKey().equals(str)) {
                        Object value = entry.getValue();
                        if (value instanceof String) {
                            if (StringUtil.isEmpty(String.valueOf(value))) {
                                counter++;
                            }
                        } else {
                            if (null == value) {
                                counter++;
                            }
                        }
                    }
                }
            }
            if (counter < a - exceptKeys.length) {
                tempList.add(map);
            }
        }
        return tempList;
    }
}
