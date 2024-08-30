package com.dlshouwen.swda.core.security.third;

/**
 * third open id service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ThirdOpenIdService {

	/**
	 * get open id
	 * @param thirdLogin
	 * @return open id
	 */
	String getOpenId(ThirdLogin login);

}
