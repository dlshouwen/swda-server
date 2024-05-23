package com.dlshouwen.swda.entity.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * R
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class R {

	/** result */
	private Boolean result;

	/** code */
	private Integer code;

	/** message */
	private String message;

	/** data */
	private Map<String, Object> data = new HashMap<>();

	/**
	 * construct
	 */
	private R(){}

	/**
	 * success
	 * @return R
	 */
	public static R success() {
		R r = new R();
		r.setResult(true);
		r.setCode(ResultCode.SUCCESS);
		r.setMessage("success");
		return r;
	}

	/**
	 * error
	 * @return R
	 */
	public static R error(){
		R r = new R();
		r.setResult(false);
		r.setCode(ResultCode.ERROR);
		r.setMessage("has error");
		return r;
	}

	/**
	 * result
	 * @param result
	 * @return R
	 */
	public R result(Boolean result){
		this.setResult(result);
		return this;
	}

	/**
	 * message
	 * @param message
	 * @return R
	 */
	public R message(String message){
		this.setMessage(message);
		return this;
	}

	/**
	 * code
	 * @param code
	 * @return R
	 */
	public R code(Integer code){
		if(code.intValue()==ResultCode.SUCCESS){
			this.setResult(true);
		}else{
			this.setResult(false);
		}
		this.setCode(code);
		return this;
	}

	/**
	 * data
	 * @param data
	 * @return R
	 */
	public R data(Map<String, Object> data){
		this.setData(data);
		return this;
	}

	/**
	 * data
	 * @param key
	 * @param value
	 * @return R
	 */
	public R data(String key, Object value){
		this.getData().put(key, value);
		return this;
	}

}