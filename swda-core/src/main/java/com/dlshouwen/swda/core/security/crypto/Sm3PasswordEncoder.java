package com.dlshouwen.swda.core.security.crypto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * sm3 password encoder
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class Sm3PasswordEncoder implements PasswordEncoder {
	
	/**
	 * encode
	 * @param rawPassword
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return SmUtil.sm3(rawPassword.toString());
	}

	/**
	 * matches
	 * @param rawPassword
	 * @param encodedPassword
	 * @return is equals
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return StrUtil.equals(SmUtil.sm3(rawPassword.toString()), encodedPassword);
	}

}
