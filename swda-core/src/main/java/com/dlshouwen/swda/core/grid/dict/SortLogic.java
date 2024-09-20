package com.dlshouwen.swda.core.grid.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sort_logic
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "sort logic")
public interface SortLogic {

	@Schema(description = "asc")
	int ASC = 1;

	@Schema(description = "desc")
	int DESC = 2;

}