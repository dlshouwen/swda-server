package com.dlshouwen.swda.core.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

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
