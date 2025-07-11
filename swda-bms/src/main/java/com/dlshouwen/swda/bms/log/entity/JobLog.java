package com.dlshouwen.swda.bms.log.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * job log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_job_log")
public class JobLog extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = 2815124847727063478L;

	@TableId
	private Long jobLogId;

	private Long jobId;

	private String jobCode;

	private String jobName;
	
	private String jobGroup;
	
	private String beanName;
	
	private String methodName;
	
	private String params;
	
	private String callResult;

	private String errorReason;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private Long cost;

}