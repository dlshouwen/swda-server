package com.dlshouwen.swda.bms.core.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * third unbind exception
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ThirdLoginException extends UsernameNotFoundException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062183L;

	/**
	 * constructor
	 * @param message
	 */
	public ThirdLoginException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ThirdLoginException(String message, Throwable throwable) {
		super(message, throwable);
	}

}