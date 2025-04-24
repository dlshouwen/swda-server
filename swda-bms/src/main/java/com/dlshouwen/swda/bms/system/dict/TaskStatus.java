package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict task status
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "task status")
public interface TaskStatus {

	@Schema(description = "runing")
	String RUNING = "1";

	@Schema(description = "pause")
	String PAUSE = "0";

}