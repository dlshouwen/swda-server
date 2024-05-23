package com.dlshouwen.swda.entity.task;

import lombok.Data;

/**
 * task message
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class TaskMessage {

	private Long taskId;

	private String taskName;

	private int iteratorTime;

	private String permissionName;

	private String url;

	private String message;

	private int result;

}