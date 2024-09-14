package com.dlshouwen.swda.bms.core.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * third username not found exception
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ThirdUserNotFoundException extends UsernameNotFoundException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062183L;

	/**
	 * constructor
	 * @param message
	 */
	public ThirdUserNotFoundException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ThirdUserNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
