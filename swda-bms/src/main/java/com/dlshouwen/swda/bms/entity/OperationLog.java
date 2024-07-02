package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */

@Data
@TableName("bms_operation_log")
public class OperationLog {

	@TableId
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
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private Integer cost;
	
	private Long tenantId;
	
	private Long userId;

	private String userName;
	
	private Long organId;
	
	private String organName;

	private String ip;
	
}