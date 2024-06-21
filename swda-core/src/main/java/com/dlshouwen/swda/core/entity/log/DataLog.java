package com.dlshouwen.swda.core.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * data log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_data_log")
public class DataLog extends BaseEntity {

	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;

	private Integer callType;

	private String callSource;

	private int lineNo;

	private Integer operationType;

	private String operationSql;

	private String params;

	private Integer callResult;

	private String errorReason;

	private String executeType;

	private String executeResult;

	private String resultType;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private int cost;

	private Long userId;
	
	private Long tenantId;

	private String userName;

	private Long organId;

	private String organName;

	private String ip;

}
