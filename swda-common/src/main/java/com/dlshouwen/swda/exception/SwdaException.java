package com.dlshouwen.swda.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * swda expcetion
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class SwdaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** code */
	private Integer code;

	/** message */
	private String message;

	/**
	 * to string
	 * @return info
	 */
	@Override
	public String toString(){
		return "SwdaException{code="+this.getCode()+", message="+this.getMessage()+"}";
	}

}
