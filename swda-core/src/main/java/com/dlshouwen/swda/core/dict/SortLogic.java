package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sort_logic
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "排序逻辑")
public class SortLogic {

	@Schema(description= "正序")
	public static Integer ASC = 1;

	@Schema(description= "倒序")
	public static Integer DESC = 2;

}