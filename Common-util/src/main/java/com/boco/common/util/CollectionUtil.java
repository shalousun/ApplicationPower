package com.boco.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: CollectionUtil <br/>
 * Function: TODO ADD FUNCTION 集合工具类
 * date: 2017年9月7日 上午9:24:00 <br/>
 *
 * @author yolanda0608
 * @version 
 * @since JDK 1.7+
 */
public class CollectionUtil {

	/**
	 * 
	 * isNotEmpty:(检查集合是否为空，不为空返回true)
	 * @author yolanda0608
	 * @param <T>
	 * @param c
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<T> c){
		if(c != null && c.size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * checkEmpty:(检查集合是否为空，为空返回true)
	 * @author yolanda0608
	 * @param <T>
	 * @param c
	 * @return
	 */
	public static  <T> boolean isEmpty(Collection<T> c){
		if(c == null || c.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 *  将List集合中的无效的map数据清空，被指定为排除的字段
	 *  即使map中这些指定字段有值,只要其他的key是无效的数据都会被被移除，但是0并不代表没有值
	 * @param list
	 * @param exceptKeys
	 * @return
	 */
	public static List<Map<String, Object>> filterEmpty(List<Map<String, Object>> list, String... exceptKeys) {
		List<Map<String,Object>> tempList = new ArrayList<>();
		for (Map<String, Object> map : list) {
			int a = map.size();
			int counter = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				for(String str:exceptKeys){
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
