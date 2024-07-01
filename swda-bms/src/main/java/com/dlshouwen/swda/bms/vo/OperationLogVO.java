package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * operation log vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "operation log")
public class OperationLogVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "log id")
	private Long logId;

	@Schema(description = "user agent")
	private String userAgent;

	@Schema(description = "operation module")
	private String operationModule;

	@Schema(description = "operation name")
	private String operationName;

	@Schema(description = "operation type")
	private Integer operationType;

	@Schema(description = "request uri")
	private String requestUri;

	@Schema(description = "request method")
	private String requestMethod;

	@Schema(description = "request params")
	private String requestParams;

	@Schema(description = "response result")
	private String responseResult;

	@Schema(description = "call source")
	private String callSource;

	@Schema(description = "line no")
	private Integer lineNo;
	
	@Schema(description = "call result")
	private Integer callResult;

	@Schema(description = "error reason")
	private String errorReason;

	@Schema(description = "start time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime startTime;

	@Schema(description = "end time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime endTime;

	@Schema(description = "cost")
	private Integer cost;

	@Schema(description = "tenant id")
	private Long tenantId;
	
	@Schema(description = "user id")
	private Long userId;
	
	@Schema(description = "user name")
	private String userName;
	
	@Schema(description = "organ id")
	private Long organId;
	
	@Schema(description = "organ name")
	private String organName;

	@Schema(description = "ip")
	private String ip;

}