package com.dlshouwen.swda.bms.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * third unbind exception
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class ThirdUnbindException extends UsernameNotFoundException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062183L;

	/**
	 * constructor
	 * @param message
	 */
	public ThirdUnbindException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ThirdUnbindException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
