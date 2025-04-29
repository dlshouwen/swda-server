package com.dlshouwen.swda.bms.log.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * job log vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "job log")
public class JobLogVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "job log id")
	private Long jobLogId;

	@Schema(description = "job id")
	private Long jobId;

	@Schema(description = "job code")
	private String jobCode;

	@Schema(description = "job name")
	private String jobName;

	@Schema(description = "job group")
	private String jobGroup;

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