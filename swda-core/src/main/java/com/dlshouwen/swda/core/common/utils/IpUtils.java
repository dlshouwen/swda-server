package com.dlshouwen.swda.core.common.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.dlshouwen.swda.core.common.exception.SwdaException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
public class IpUtils {
	
	/** searcher */
	private final static Searcher searcher;

	/**
	 * init
	 */
	static {
//		get ip2 region db resource
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource("classpath:ip2region.xdb");
//		try catch
		try {
//			init searcher
			searcher = Searcher.newWithBuffer(resource.getContentAsByteArray());
		} catch (Exception e) {
//			log error
			log.error("ip2region init error", e);
//			throw exception
			throw new SwdaException("ip2region init error", e);
		}
	}

	/**
	 * get address by ip
	 * @param ip
	 * @return address
	 */
	public static String getAddressByIP(String ip) {
//		if internal ip
		if (IpUtils.internalIp(ip)) {
			return "[internal!]";
		}
//		try catch
		try {
//			get address
			String address = searcher.search(ip);
//			return address
			return address.replace("0|", "").replace("|0", "").replace("中国|", "");
		} catch (Exception e) {
//			log error
			log.error("get address ip error", ip);
//			return unknow
			return "unknow";
		}
	}

	/**
	 * get ip address
	 * @param request
	 * @return address
	 */
	public static String getIp(HttpServletRequest request) {
//		if request is empty then return unknow
		if (request == null) {
			return "unknown";
		}
//		get ip from header: x-forwarded-for
		String ip = request.getHeader("X-Forwarded-For");
//		get ip from header: proxy-client-ip
		if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
//		get ip from header: wl-proxy-client-ip
		if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
//		get ip from header: x-real-ip
		if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
//		get ip from request get remote addr
		if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
//		return ip
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
	}

	/**
	 * internal ip
	 * @param ip
	 * @return is internal ip
	 */
	public static boolean internalIp(String ip) {
//		get byte addr
		byte[] addr = textToNumericFormatV4(ip);
//		internal ip
		return internalIp(addr) || "127.0.0.1".equals(ip);
	}

	/**
	 * internal ip
	 * @param addr
	 * @return is internal ip
	 */
	private static boolean internalIp(byte[] addr) {
		if (addr == null || addr.length < 2) {
			return true;
		}
		final byte b0 = addr[0];
		final byte b1 = addr[1];
//		10.x.x.x/8
		final byte SECTION_1 = 0x0A;
//		172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
//		192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			if (b1 == SECTION_6) {
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * text to numeric format v4
	 * @param text
	 * @return byte
	 */
	public static byte[] textToNumericFormatV4(String text) {
		if (StrUtil.isBlank(text)) {
			return null;
		}
		byte[] bytes = new byte[4];
		String[] elements = text.split("\\.", -1);
		try {
			long l;
			int i;
			switch (elements.length) {
			case 1:
				l = Long.parseLong(elements[0]);
				if ((l < 0L) || (l > 4294967295L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l >> 24 & 0xFF);
				bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 2:
				l = Integer.parseInt(elements[0]);
				if ((l < 0L) || (l > 255L)) {
					return null;
				}
				bytes[0] = (byte) (int) (l & 0xFF);
				l = Integer.parseInt(elements[1]);
				if ((l < 0L) || (l > 16777215L)) {
					return null;
				}
				bytes[1] = (byte) (int) (l >> 16 & 0xFF);
				bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 3:
				for (i = 0; i < 2; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				l = Integer.parseInt(elements[2]);
				if ((l < 0L) || (l > 65535L)) {
					return null;
				}
				bytes[2] = (byte) (int) (l >> 8 & 0xFF);
				bytes[3] = (byte) (int) (l & 0xFF);
				break;
			case 4:
				for (i = 0; i < 4; ++i) {
					l = Integer.parseInt(elements[i]);
					if ((l < 0L) || (l > 255L)) {
						return null;
					}
					bytes[i] = (byte) (int) (l & 0xFF);
				}
				break;
			default:
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return bytes;
	}

	/**
	 * get host ip
	 * @return host ip
	 */
	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ignored) {

		}
		return "127.0.0.1";
	}

	/**
	 * get host name
	 * @return host name
	 */
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ignored) {

		}
		return "unknow";
	}

	/**
	 * get mutistage reverse proxy ip
	 * @param ip
	 * @return ip
	 */
	public static String getMultistageReverseProxyIp(String ip) {
		if (ip.indexOf(",") > 0) {
			final String[] ips = ip.trim().split(",");
			for (String sub : ips) {
				if (!"unknown".equalsIgnoreCase(sub)) {
					ip = sub;
					break;
				}
			}
		}
		return ip;
	}

}
