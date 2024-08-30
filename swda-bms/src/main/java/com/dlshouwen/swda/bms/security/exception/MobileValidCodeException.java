package com.dlshouwen.swda.bms.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * third username not found exception
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class MobileValidCodeException extends UsernameNotFoundException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062183L;

	/**
	 * constructor
	 * @param message
	 */
	public MobileValidCodeException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public MobileValidCodeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
