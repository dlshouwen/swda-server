package com.dlshouwen.swda.core.dto;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * operation log dto
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class OperationLogDTO {

	private Long logId;

	private String userAgent;

	private String operationModule;

	private String operationName;

	private Integer operationType;

	private String requestUri;

	private String requestMethod;

	private String requestParams;

	private String responseResult;

	private String callSource;

	private Integer lineNo;
	
	private Integer callResult;

	private String errorReason;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime startTime;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime endTime;

	private Integer cost;

	private Long tenantId;
	
	private Long userId;
	
	private String userName;
	
	private Long organId;
	
	private String organName;

	private String ip;

}