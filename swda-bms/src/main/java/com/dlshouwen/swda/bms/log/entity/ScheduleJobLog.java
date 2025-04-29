package com.dlshouwen.swda.bms.log.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * schedule job log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_schedule_job_log")
public class ScheduleJobLog extends BaseEntity {
	
	@TableId
	private Long scheduleJobLogId;

	private Long scheduleJobId;

	private String scheduleJobCode;

	private String scheduleJobName;
	
	private String scheduleJobGroup;
	
	private String beanName;
	
	private String methodName;
	
	private String params;
	
	private String callResult;

	private String errorReason;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private Long cost;

}