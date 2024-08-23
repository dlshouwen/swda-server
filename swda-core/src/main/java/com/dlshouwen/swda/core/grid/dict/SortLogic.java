package com.dlshouwen.swda.core.grid.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sort_logic
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "排序逻辑")
public interface SortLogic {

	@Schema(description = "正序")
	int ASC = 1;

	@Schema(description = "倒序")
	int DESC = 2;

}