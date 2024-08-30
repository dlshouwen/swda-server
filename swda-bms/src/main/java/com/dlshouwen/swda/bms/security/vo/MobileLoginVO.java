package com.dlshouwen.swda.bms.security.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * mobile login vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "mobile login")
public class MobileLoginVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "mobile")
	private String mobile;

	@Schema(description = "code")
	private String code;

}
