package com.dlshouwen.swda.bms.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * valid code expired exception
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ValidCodeExpiredException extends AuthenticationException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062182L;

	/**
	 * constructor
	 * @param message
	 */
	public ValidCodeExpiredException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ValidCodeExpiredException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
