package com.dlshouwen.swda.core.entity.base;

import java.util.HashMap;
import java.util.Map;

import com.dlshouwen.swda.core.enums.ResultCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * r
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "Result")
public class R<T> {

	@Schema(description = "code, 0:success, other:failure")
	private int code = 0;

	@Schema(description = "message")
	private String message = "success";

	@Schema(description = "data")
	private T data;

	/**
	 * ok
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> ok() {
		return ok(null);
	}

	/**
	 * ok
	 * @param <T>
	 * @param data
	 * @return
	 */
	public static <T> R<T> ok(T data) {
		return new R<T>().data(data);
	}

	/**
	 * error
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> error() {
		return error(ResultCode.INTERNAL_SERVER_ERROR);
	}

	/**
	 * error
	 * @param <T>
	 * @param resultCode
	 * @return
	 */
	public static <T> R<T> error(ResultCode resultCode) {
		return error(resultCode.getCode(), resultCode.getMessage());
	}

	/**
	 * error
	 * @param <T>
	 * @param message
	 * @return
	 */
	public static <T> R<T> error(String message) {
		return error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
	}

	/**
	 * error
	 * @param <T>
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> R<T> error(int code, String message) {
		return error(code, message, null);
	}

	/**
	 * error
	 * @param <T>
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> R<T> error(int code, String message, T data) {
		return new R<T>().code(code).message(message).data(data);
	}

	/**
	 * code
	 * @param code
	 * @return R
	 */
	public R<T> code(Integer code) {
		this.setCode(code);
		return this;
	}

	/**
	 * message
	 * @param message
	 * @return R
	 */
	public R<T> message(String message) {
		this.setMessage(message);
		return this;
	}

	/**
	 * data
	 * @param data
	 * @return R
	 */
	public R<T> data(T data) {
		this.setData(data);
		return this;
	}

	/**
	 * data
	 * @param key
	 * @param value
	 * @return R
	 */
	@SuppressWarnings("unchecked")
	public R<T> data(String key, Object value) {
		if (this.getData() == null) {
			data((T) new HashMap<String, Object>());
		}
		((Map<String, Object>) this.getData()).put(key, value);
		return this;
	}

}
