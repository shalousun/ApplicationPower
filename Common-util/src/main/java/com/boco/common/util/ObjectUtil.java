package com.boco.common.util;
/**
 * 对象工具类
 * ClassName: ObjectUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2017年9月7日 上午9:51:34 <br/>
 *
 * @author yolanda0608
 * @version 
 * @since JDK 1.7+
 */
public class ObjectUtil {
	/**
	 * 
	 * isNotEmpty:(检查对象是否为空，不为空返回true)
	 * @author yolanda0608
	 * @param object
	 * @return
	 */
	public static boolean isNotEmpty(Object object){
		if(object != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * isNotEmpty:(检查对象是否为空，为空返回true)
	 * @author yolanda0608
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object){
		return !isNotEmpty(object);
	}
}
