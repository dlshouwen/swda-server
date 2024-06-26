package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.bms.vo.SysCaptchaVO;

/**
 * captcha service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysCaptchaService {

	/**
	 * generate captcha
	 * @return captcha vo
	 */
	SysCaptchaVO generate();

	/**
	 * valid
	 * @param key
	 * @param code
	 * @return valid result
	 */
	boolean validate(String key, String code);

	/**
	 * is captcha enabled
	 * @return is captcha enabled
	 */
	boolean isCaptchaEnabled();

}
