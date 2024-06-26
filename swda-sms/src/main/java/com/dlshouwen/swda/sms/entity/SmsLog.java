package com.dlshouwen.swda.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * sms log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_log")
@Schema(description = "sms log")
public class SmsLog {

	@TableId
	@Schema(description = "log id")
	private Long logId;

	@Schema(description = "sms platform id")
	private Long smsPlatformId;
	
	@Schema(description = "sms platform name")
	private String smsPlatformName;

	@Schema(description = "sms platform type")
	private Integer smsPlatformType;

	@Schema(description = "mobile")
	private String mobile;
	
	@Schema(description = "params")
	private String params;

	@Schema(description = "call result")
	private Integer callResult;

	@Schema(description = "error reason")
	private String errorReason;

	@Schema(description = "send time")
	private LocalDateTime sendTime;

}