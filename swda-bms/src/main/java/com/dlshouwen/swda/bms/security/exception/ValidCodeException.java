package com.dlshouwen.swda.bms.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * valid code exception
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ValidCodeException extends AuthenticationException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3882887736273062181L;

	/**
	 * constructor
	 * @param message
	 */
	public ValidCodeException(String message) {
		super(message);
	}

	/**
	 * constructor
	 */
	public ValidCodeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
