package com.dlshouwen.swda.core.exception;

import com.dlshouwen.swda.core.enums.ResultCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * swda expcetion
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SwdaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** code */
	private Integer code;

	/** message */
	private String message;

	/**
	 * constructor
	 * @param message
	 */
	public SwdaException(String message) {
		super(message);
		this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
		this.message = message;
	}

	/**
	 * constructor
	 * @param resultCode
	 */
	public SwdaException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

	/**
	 * constructor
	 * @param message
	 * @param e
	 */
	public SwdaException(String message, Throwable e) {
		super(message, e);
		this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
		this.message = message;
	}

}
