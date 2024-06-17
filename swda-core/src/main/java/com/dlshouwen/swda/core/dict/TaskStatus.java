package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict task_status
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "任务状态")
public class TaskStatus {

	@Schema(description= "未启动")
	public static Integer STOP = 0;

	@Schema(description= "已启动")
	public static Integer START = 1;

}