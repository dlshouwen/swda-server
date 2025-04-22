package com.dlshouwen.swda.bms.platform.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sms platform vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "sms platform")
public class SmsPlatformVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "sms platform id")
	private Long smsPlatformId;
	
	@Schema(description = "sms platform code")
	private String smsPlatformCode;
	
	@Schema(description = "sms platform name")
	private String smsPlatformName;

	@Schema(description = "sms platform type")
	private String smsPlatformType;

	@Schema(description = "status")
	private String status;

	@Schema(description = "group name")
	private String groupName;

	@Schema(description = "sign name")
	private String signName;

	@Schema(description = "template id")
	private String templateId;

	@Schema(description = "app id")
	private String appId;

	@Schema(description = "sender id")
	private String senderId;

	@Schema(description = "url")
	private String url;

	@Schema(description = "access key")
	private String accessKey;

	@Schema(description = "secret key")
	private String secretKey;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}