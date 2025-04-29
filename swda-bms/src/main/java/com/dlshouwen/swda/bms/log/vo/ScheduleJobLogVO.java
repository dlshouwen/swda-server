package com.dlshouwen.swda.bms.log.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * schedule job log vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "schedule job log")
public class ScheduleJobLogVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "schedule job log id")
	private Long scheduleJobLogId;

	@Schema(description = "schedule job id")
	private Long scheduleJobId;

	@Schema(description = "schedule job code")
	private String scheduleJobCode;

	@Schema(description = "schedule job name")
	private String scheduleJobName;

	@Schema(description = "schedule job group")
	private String scheduleJobGroup;

	@Schema(description = "bean name")
	private String beanName;

	@Schema(description = "method name")
	private String methodName;

	@Schema(description = "params")
	private String params;

	@Schema(description = "call result")
	private String callResult;

	@Schema(description = "error reason")
	private String errorReason;

	@Schema(description = "start time")
	private LocalDateTime startTime;
	
	@Schema(description = "end time")
	private LocalDateTime endTime;

	@Schema(description = "cost")
	private Long cost;

}