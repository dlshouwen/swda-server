package com.dlshouwen.swda.bms.security.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * send code vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "send code")
public class SendCodeVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "mobile")
	private String mobile;
	
	@Schema(description = "key")
	private String key;

	@Schema(description = "captcha")
	private String captcha;

}
