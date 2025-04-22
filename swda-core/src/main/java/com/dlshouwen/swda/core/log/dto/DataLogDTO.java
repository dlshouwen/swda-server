package com.dlshouwen.swda.core.log.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * operation log dto
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class DataLogDTO {

	private Long dataLogId;

	private String executeType;

	private String executeSql;

	private String executeMethod;

	private String executeParams;

	private String executeResultClass;

	private String executeResult;

	private String callType;

	private String callSource;

	private Integer lineNo;

	private String callResult;

	private String errorReason;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Integer cost;

	private Long tenantId;

	private Long userId;

	private String realName;

	private Long organId;

	private String organName;

	private String ip;

}