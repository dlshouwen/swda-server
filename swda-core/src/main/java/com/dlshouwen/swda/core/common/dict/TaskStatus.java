package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict task_status
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "任务状态")
public interface TaskStatus {

	@Schema(description = "未启动")
	int STOP = 0;

	@Schema(description = "已启动")
	int START = 1;

}