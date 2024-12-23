package com.dlshouwen.swda.bms.app.service;

import com.dlshouwen.swda.bms.app.vo.CaptchaVO;

/**
 * captcha service
 * @author liujingcheng@live.cn
 * @version 1.0.0
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

}
