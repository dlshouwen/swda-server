package com.dlshouwen.swda.core.log.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * operation log dto
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class DataLogDTO {

	private Long logId;

	private Integer executeType;

	private String executeSql;

	private String executeMethod;

	private String executeParams;

	private String executeResultClass;

	private String executeResult;

	private int callType;

	private String callSource;

	private int lineNo;

	private Integer callResult;

	private String errorReason;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private int cost;

	private Long tenantId;

	private Long userId;

	private String realName;

	private Long organId;

	private String organName;

	private String ip;

}