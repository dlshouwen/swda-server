package com.dlshouwen.swda.core.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_operation_log")
public class OperationLog extends BaseEntity {

	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;

	private String callSource;

	private String url;

	private String method;

	private String userAgent;

	private String module;

	private String name;

	private String params;

	private int operationType;

	private String operationResult;

	private int callResult;

	private String errorReason;

	private LocalDateTime responseStart;

	private LocalDateTime responseEnd;

	private int cost;
	
	private Long tenantId;

	private Long userId;

	private String userName;

	private Long organId;

	private String organName;

	private String ip;

}
