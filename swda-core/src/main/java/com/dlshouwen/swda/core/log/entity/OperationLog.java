package com.dlshouwen.swda.core.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_operation_log")
public class OperationLog extends BaseEntity {

	@TableId
	private Long operationLogId;
	
	private String userAgent;

	private String operationModule;

	private String operationName;

	private String operationType;
	
	private String requestUri;

	private String requestMethod;
	
	private String requestParams;

	private String responseResult;

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
