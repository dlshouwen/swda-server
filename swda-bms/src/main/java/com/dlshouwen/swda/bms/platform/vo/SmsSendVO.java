package com.dlshouwen.swda.bms.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * sms send
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "sms send")
public class SmsSendVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "sms platform id")
	private Long smsPlatformId;

	@Schema(description = "mobile")
	private String mobile;

	@Schema(description = "param key")
	private String paramKey;

	@Schema(description = "param value")
	private String paramValue;

}