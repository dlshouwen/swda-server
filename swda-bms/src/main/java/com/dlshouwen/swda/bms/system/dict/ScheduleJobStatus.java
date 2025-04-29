package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict schedule job status
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "schedule job status")
public interface ScheduleJobStatus {

	@Schema(description = "normal")
	String NORMAL = "1";

	@Schema(description = "pause")
	String PAUSE = "0";

}