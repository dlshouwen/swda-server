package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict task_status
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="任务状态")
public class TaskStatus {

	@Schema(title="未启动")
	public static String STOP = "0";

	@Schema(title="已启动")
	public static String START = "1";

}