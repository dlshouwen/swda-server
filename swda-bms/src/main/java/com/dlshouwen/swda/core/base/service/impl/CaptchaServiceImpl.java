package com.dlshouwen.swda.bms.app.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.app.service.ICaptchaService;
import com.dlshouwen.swda.bms.app.vo.CaptchaVO;
import com.dlshouwen.swda.core.common.cache.RedisCache;
import com.dlshouwen.swda.core.common.constant.Constant;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import org.springframework.stereotype.Service;

/**
 * captcha service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {
	
	/** redis cache */
	private final RedisCache redisCache;
	
	/**
	 * generate
	 * @return captcha vo
	 */
	@Override
	public CaptchaVO generate() {
//		generate key
		String key = UUID.randomUUID().toString();
//		generate captcha
		SpecCaptcha captcha = new SpecCaptcha(150, 40);
		captcha.setLen(5);
		captcha.setCharType(Captcha.TYPE_DEFAULT);
		String image = captcha.toBase64();
//		save to cache
		redisCache.set(Constant.CAPTCHA_PREFIX + key, captcha.text(), 300);
//		construct captcha vo
		CaptchaVO captchaVO = new CaptchaVO();
		captchaVO.setKey(key);
		captchaVO.setImage(image);
//		return captcha vo
		return captchaVO;
	}

	/**
	 * validate
	 * @param key
	 * @param code
	 * @return is validate
	 */
	@Override
	public boolean validate(String key, String code) {
//		if key or code is empty then return false
		if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
			return false;
		}
//		get cache
		String captcha = getCache(key);
//		return validate result
		return code.equalsIgnoreCase(captcha);
	}

	/**
	 * get cache
	 * @param key
	 * @return captcha
	 */
	private String getCache(String key) {
//		append prefix
		key = Constant.CAPTCHA_PREFIX + key;
//		get captcha
		String captcha = (String) redisCache.get(key);
//		if has captcha
		if (captcha != null) {
//			delete cache
			redisCache.delete(key);
		}
//		return captcha
		return captcha;
	}

}
