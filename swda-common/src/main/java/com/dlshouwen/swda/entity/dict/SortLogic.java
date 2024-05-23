package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sort_logic
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="排序逻辑", description="排序逻辑")
public class SortLogic {

	@Schema(title="正序")
	public static String ASC = "1";

	@Schema(title="倒序")
	public static String DESC = "2";

}