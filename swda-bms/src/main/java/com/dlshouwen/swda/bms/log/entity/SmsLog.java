package com.dlshouwen.swda.bms.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * sms log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_log")
public class SmsLog {

	@TableId
	private Long smsLogId;

	private Long smsPlatformId;
	
	private String smsPlatformName;

	private String smsPlatformType;

	private String mobile;
	
	private String params;

	private String callResult;

	private String errorReason;

	private LocalDateTime sendTime;

}