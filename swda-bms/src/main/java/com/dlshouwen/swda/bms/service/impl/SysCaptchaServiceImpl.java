package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.enums.SysParamsEnum;
import com.dlshouwen.swda.bms.service.SysCaptchaService;
import com.dlshouwen.swda.bms.service.SysParamsService;
import com.dlshouwen.swda.bms.vo.SysCaptchaVO;
import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import org.springframework.stereotype.Service;

/**
 * captcha service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysCaptchaServiceImpl implements SysCaptchaService {
	
	/** redis cache */
	private final RedisCache redisCache;
	
	/** params service */
	private final SysParamsService sysParamsService;

	/**
	 * generate
	 * @return captcha vo
	 */
	@Override
	public SysCaptchaVO generate() {
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
		SysCaptchaVO captchaVO = new SysCaptchaVO();
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
//		if captcha enabled close then return true
		if (!isCaptchaEnabled()) {
			return true;
		}
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
	 * is captcha enabled
	 * @return is captcha enabled
	 */
	@Override
	public boolean isCaptchaEnabled() {
		return sysParamsService.getBoolean(SysParamsEnum.LOGIN_CAPTCHA.name());
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
