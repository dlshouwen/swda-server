package com.dlshouwen.swda.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class IpUtils {

	/**
	 * get ip addr
	 * @param request
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request){
//		get ip from header
		String ip = request.getHeader("x-forwarded-for");
		if(StrUtil.isEmpty(ip)||"unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if(StrUtil.isEmpty(ip)||"unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if(StrUtil.isEmpty(ip)||"unknown".equalsIgnoreCase(ip)) {
//			get ip from remote addr
			ip = request.getRemoteAddr();
//			if localhost
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
//				get ip from inet
				try {
					InetAddress inet = InetAddress.getLocalHost();
					ip = inet.getHostAddress();
				} catch (UnknownHostException ignore) {
				}
			}
		}
//		if ip is valid
		if(ip!=null&&ip.length()>15&&ip.indexOf(",")>0) {
			ip = ip.substring(0, ip.indexOf(","));
		}
//		return
		return ip;
	}

	/**
	 * get mac address
	 * @param ip
	 * @return mac
	 */
	public static String getMACAddress(String ip){
		String str;
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

}