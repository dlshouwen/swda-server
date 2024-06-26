package com.dlshouwen.swda.auth.third;

/**
 * third open id service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface ThirdOpenIdService {

	/**
	 * get open id
	 * @param thirdLogin
	 * @return open id
	 */
	String getOpenId(ThirdLogin login);

}
