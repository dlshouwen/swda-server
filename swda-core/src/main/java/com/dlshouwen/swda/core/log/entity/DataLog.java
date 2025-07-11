package com.dlshouwen.swda.core.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * data log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_data_log")
public class DataLog extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -5194281652879441045L;

	@TableId
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
