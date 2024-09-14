package com.dlshouwen.swda.bms.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信发送
 *
 * @author 阿沐 babamu@126.com
 */
@Data
@Schema(description = "短信发送")
public class SmsSendVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "sms platform id")
	private Long smsPlatformId;

	@Schema(description = "手机号")
	private String mobile;

	@Schema(description = "参数Key")
	private String paramKey;

	@Schema(description = "参数Value")
	private String paramValue;

}