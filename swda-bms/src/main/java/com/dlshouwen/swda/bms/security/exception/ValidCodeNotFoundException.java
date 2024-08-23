package com.dlshouwen.swda.bms.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * valid code not found exception
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ValidCodeNotFoundException extends AuthenticationException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062183L;

	/**
	 * constructor
	 * @param message
	 */
	public ValidCodeNotFoundException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ValidCodeNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
