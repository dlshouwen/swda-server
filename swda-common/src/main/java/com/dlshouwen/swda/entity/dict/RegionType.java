package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict region_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="区域类型", description="区域类型")
public class RegionType {

	@Schema(title="省级")
	public static String PROVINCE = "1";

	@Schema(title="市级")
	public static String CITY = "2";

	@Schema(title="区级")
	public static String COUNTY = "3";

}