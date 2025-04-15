package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * task vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "task")
public class TaskVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "task id")
	private Long taskId;
	
	@Schema(description = "task code")
	@NotBlank(message = "任务编码不能为空")
	private String taskCode;

	@Schema(description = "task name")
	@NotBlank(message = "任务名称不能为空")
	private String taskName;
	
	@Schema(description = "task group")
	@NotBlank(message = "任务分组不能为空")
	private String taskGroup;
	
	@Schema(description = "task status")
	@NotBlank(message = "任务状态不能为空")
	private String taskStatus;
	
	@Schema(description = "allow concurrent")
	@NotBlank(message = "允许并发不能为空")
	private String allowConcurrent;
	
	@Schema(description = "bean name")
	@NotBlank(message = "Bean名称不能为空")
	private String beanName;
	
	@Schema(description = "method name")
	@NotBlank(message = "方法名称不能为空")
	private String methodName;
	
	@Schema(description = "params")
	private String params;
	
	@Schema(description = "core expression")
	@NotBlank(message = "Core表达式不能为空")
	private String coreExpression;

	@Schema(description = "remark")
	private String remark;
	
	@Schema(description = "assist_search")
	private String assistSearch;
	
	@Schema(description = "sort")
	private int sort;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}