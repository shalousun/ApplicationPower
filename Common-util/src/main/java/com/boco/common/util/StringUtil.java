package com.boco.common.util;

/**
 * @author sunyu
 */

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String SERIALNO_PATTERN = "yyyyMMddHHmmssSSS";
	private static final char UNDERLINE = '_';

	/**
	 * @param str String
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str.trim()) || "null".equals(str.trim()) || "NaN".equals(str.trim());
	}

	/**
	 * @param str String
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串中字符是否都相同
	 *
	 * @param s
	 * @return
	 */
	public static boolean isSameCharacter(String s) {
		s = s.toUpperCase();
		String character = s.substring(0, 1);
		String replace = "";
		String test = s.replace(character, replace);
		return "".equals(test);
	}

	/**
	 * 判断字符串中的字符是否为连续字符
	 *
	 * @param s
	 * @return
	 */
	public static boolean isContinuityCharacter(String s) {
		boolean continuity = true;
		char[] data = s.toCharArray();
		for (int i = 0; i < data.length - 1; i++) {
			int a = Integer.parseInt(data[i] + "");
			int b = Integer.parseInt(data[i + 1] + "");
			continuity = continuity && (a + 1 == b || a - 1 == b);
		}
		return continuity;
	}

	/**
	 * @param str String
	 * @return String
	 */
	public static String getCharCode(String str) {
		String temp = "";
		for (int i = 0; i < temp.length(); i++) {
			temp += Integer.toHexString(str.charAt(i)) + "nbsp;";
		}
		return temp;
	}

	/**
	 * convert to 8859
	 *
	 * @param str String
	 * @return String
	 */
	public static String convertTo8859(String str) {
		String strOutPut = "";
		try {
			byte[] tempStrByte = str.getBytes("ISO-8859-1");
			strOutPut = new String(tempStrByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOutPut;
	}

	public static String capitalise(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 去除字符串空格
	 *
	 * @param sourceStr input string
	 * @return string
	 */
	public static String trim(String sourceStr) {
		if (isEmpty(sourceStr)) {
			return null;
		} else {
			return sourceStr.replaceAll(" ", "");
		}
	}

	/**
	 * 过滤特殊字符串
	 *
	 * @param str String
	 * @return String
	 */
	public static String filterStr(String str) {
		if (isEmpty(str)) {
			return str;
		} else {
			str = str.replaceAll(";", "");
			str = str.replaceAll("%", "");
			str = str.replaceAll("--", "");
			str = str.replaceAll("/", "");
			str = str.replaceAll("=", "");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
			str = str.replaceAll("<", "&lt");
			str = str.replaceAll(">", "&gt");
			return str;
		}
	}

	/**
	 * sql字符清除
	 * @param str
	 * @return
	 */
	public static String cleanSqlWildCharater(String str) {
		if (isEmpty(str)) {
			return str;
		} else {
			str = str.replaceAll("%", "invalid character");
			str = str.replaceAll("_", "invalid character");
			str = str.replaceAll("=", "invalid character");
			return str;
		}
	}

	/**
	 * 清除xss脚本注入
	 *
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) {
		if (null == value) {
			return value;
		} else {
			value = value.replaceAll("\\bselect\\b", "非法字符");
			value = value.replaceAll("\\band\\b", "非法字符");
			value = value.replaceAll("\\bor\\b", "非法字符");
			value = value.replaceAll("\\bdelete\\b", "非法字符");
			value = value.replaceAll("\\bjoin\\b", "非法字符");
			value = value.replaceAll("\\bdrop\\b", "非法字符");

			value = value.replaceAll("\\+", "&#43;");
			value = value.replaceAll("&", "&amp;");
			value = value.replaceAll("%", "&#37;");
			// value = value.replaceAll("\"","&quot;");
			value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
			value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
			value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
			value = value.replaceAll("'", "&#39;");
			value = value.replaceAll("alert", "invalid character");
			value = value.replaceAll("eval\\((.*)\\)", "invalid character");
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			value = value.replaceAll("<\\s*script", "invalid character");
			value = value.replaceAll("location.href","invalid character");
		}
		return value;
	}

	/**
	 * 驼峰书写转化为下划线
	 *
	 * @param param 待转换字符
	 * @return String
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int length = param.length();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下滑线写法转化为驼峰书写
	 *
	 * @param param 待转换字符
	 * @return String
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int length = param.length();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < length) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * @param s
	 * @return
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = underlineToCamel(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 首字母转大写
	 *
	 * @param param String
	 * @return String
	 */
	public static String firstToUpperCase(String param) {
		char[] cs = param.toCharArray();
		if (cs[0] > 96 && cs[0] < 123) {
			cs[0] -= 32;
		}
		return String.valueOf(cs);
	}

	/**
	 * 首字母转小写
	 *
	 * @param param String
	 * @return String
	 */
	public static String firstToLowerCase(String param) {
		char[] cs = param.toCharArray();
		if (cs[0] > 64 && cs[0] < 91) {
			cs[0] += 32;
		}
		return String.valueOf(cs);
	}

	/**
	 * 根据时间生成可视序列号
	 *
	 * @return String
	 */
	public static String createSerialNo() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(SERIALNO_PATTERN);
		return format.format(cal.getTime());
	}

	/**
	 * 二分法实现字符串反序 该方法效率不及原生的StringBuffer 或者StringBuildder提供的 reverse效率高
	 *
	 * @param str 待反序的字符
	 * @return String
	 */
	public static String reverse(String str) {

		int length = str.length();
		int half = (length >> 1) + 1;
		char[] a = str.toCharArray();
		for (int i = 0, j = length - 1; i < half; i++, j--) {
			char str1 = str.charAt(i);
			char str2 = str.charAt(j);
			a[i] = str2;
			a[j] = str1;
		}
		return String.valueOf(a);

	}

	/**
	 * 对url传递的参数进行解码
	 * @param str
	 * @return
	 */
	public static String urlDecode(String str) {
		if (isEmpty(str)) {
			return null;
		} else {
			try {
				return java.net.URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 8859编码转换称utf8
	 *
	 * @param str String
	 * @return String
	 */
	public static String ios8859ToUtf8(String str) {
		if (isEmpty(str)) {
			return null;
		} else {
			try {
				return new String(str.getBytes("iso8859-1"), "utf-8");
			} catch (Exception e) {
				return null;
			}
		}
	}

	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuilder tmp = new StringBuilder();
		int iTmp;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	private static String fillStringByArgs(String str, String[] arr) {
		Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
		while (m.find()) {
			str = str.replace(m.group(), arr[Integer.parseInt(m.group(1))]);
		}
		return str;
	}

	/**
	 * left trim and right trim
	 * @param str
	 * @return
	 */
	public static String trimBlank(String str) {
		if (isEmpty(str)) {
			return null;
		} else {
			return str.replaceAll("^[　 ]+|[　 ]+$", "");
		}
	}

	public static int length(String str) {
		if (isEmpty(str)) {
			return 0;
		} else {
			return str.length();
		}
	}

	/**
	 * 生成指定长度的随机整数
	 *
	 * @param length int
	 * @return String
	 */
	public static String createRandom(int length) {
		double a = Math.pow(10, length - 1);
		int num = (int) ((Math.random() * 9 + 1) * a);
		return String.valueOf(num);
	}

	/**
	 * 移除查询关键词中单引号或双引号，避免sql错误
	 *
	 * @param str String
	 * @return String
	 */
	public static String removeQuotes(String str) {
		if (isNotEmpty(str)) {
			return str.replace("'", "").replace("\"", "");
		} else {
			return "";
		}
	}

	public static String replaceHtml(String html) {
		if (isEmpty(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	public static boolean strToBoolean(String str) {
		if (isEmpty(str)) {
			return false;
		} else {
			str = str.trim();
			return Boolean.valueOf(str);
		}
	}

	/**
	 * 提取中文
	 *
	 * @param str
	 * @return
	 */
	public static String getChinese(String str) {
		String reg = "[^\u4e00-\u9fa5]";
		str = str.replaceAll(reg, "");
		return str;
	}

	/**
	 * 提非中文
	 *
	 * @param str
	 * @return
	 */
	public static String getNotChinese(String str) {
		String reg = "[^A-Za-z0-9_]";
		str = str.replaceAll(reg, "");
		return str;
	}
	/**
	 * 去掉指定前缀
	 *
	 * @param str    字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
	 */
	public static String removePrefix(String str, String prefix) {
		if (isEmpty(str) || isEmpty(prefix)) {
			return str;
		}

		if (str.startsWith(prefix)) {
			return str.substring(prefix.length());
		}
		return str;
	}

	/**
	 *
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] split(String str,String regex){
		if(null != str){
			return str.split(regex);
		}
		return null;
	}
}
