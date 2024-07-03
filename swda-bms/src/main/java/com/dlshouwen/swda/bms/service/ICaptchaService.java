package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.bms.vo.CaptchaVO;

/**
 * captcha service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface ICaptchaService {

	/**
	 * generate captcha
	 * @return captcha vo
	 */
	CaptchaVO generate();

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
