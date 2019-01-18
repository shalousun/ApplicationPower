package com.power.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

public class IpUtil {

	private static String serverIp;

	static {
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
			serverIp = ia.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取ip地址
	 * @param request HttpServletRequest
	 * @return string
     */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//cast localhost
		if("0:0:0:0:0:0:0:1".equals(ip)){
			ip = getServerIp();
		}
		return ip;
	}


	/**
	 * 获取服务器的ip地址
	 * @return String
	 */
	public static String getServerIp() {
		return serverIp;
	}
}
