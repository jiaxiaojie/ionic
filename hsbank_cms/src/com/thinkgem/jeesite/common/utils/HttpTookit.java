package com.thinkgem.jeesite.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Description:基本Http工具类<br />
 * </p>
 * 
 * @title HttpTookit.java
 * @package com.util
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
public final class HttpTookit {
	private static Log log = LogFactory.getLog(HttpTookit.class);

	/**
	 * <p>
	 * Description:获得ip地址<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param request
	 * @return String
	 */
	public static String getRealIpAddr(HttpServletRequest request) {
		String ip = null;
		if (StringUtils.isNotEmpty((ip = request.getParameter("ip")))) {
			return ip;
		}

		ip = request.getHeader("X-Real-IP");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * <p>
	 * Description:获得绝对上下文地址<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param request
	 * @return String
	 */
	public static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	}

	/**
	 * 
	 * <p>
	 * Description:获得绝对上下文地址<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param request
	 * @return String
	 */
	public static String getPathNotPort(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + path + "/";
	}

}