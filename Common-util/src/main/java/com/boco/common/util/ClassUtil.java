package com.boco.common.util;

import com.boco.common.filter.FileNameFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunyu
 */
public class ClassUtil {
	/**
	 * 根据包名的到该包下的类
	 * @param modelPackage String
	 * @param root String
	 * @return List
	 */
	public static List<Class> getClasses(String modelPackage, String root){
		StringBuilder buffer = new StringBuilder();
		buffer.append(root.replaceAll("\\\\", "/"));
		buffer.append("/src/main/java/");
		buffer.append(modelPackage.replace(".","/"));
		List<Class> list = new ArrayList<>();
		File entryFile=new File(buffer.toString());
		File[] eFiles=entryFile.listFiles(new FileNameFilter("java"));
		try {
			for(File ef:eFiles){
				String alName=ef.getName();
				String name=alName.split("\\.")[0];
				Class<?> c=Class.forName(modelPackage+"."+name);
				list.add(c);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 *  读取源代码获取类名
	 * @param filePath java源文件路径
	 * @param simpleClassName 和简单的类名进行对比，避免再往下读取
	 * @return
	 */
	public static String getClassName(String filePath,String simpleClassName) {
		final String  regex = "(class (.*?)\\{)|(interfase (.*?)\\{)";
		String curLine;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			while ((curLine = reader.readLine()) != null) {
				Matcher matcher = Pattern.compile(regex, Pattern.DOTALL | Pattern.MULTILINE).matcher(curLine);
				if (matcher.find()) {
					String selector = matcher.group();
					String[] strs = selector.split(" ");
					for(String str:strs){
						if(str.contains(simpleClassName)){
							return str;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
