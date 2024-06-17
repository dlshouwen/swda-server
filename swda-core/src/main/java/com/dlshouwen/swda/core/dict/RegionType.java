package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict region_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "区域类型")
public class RegionType {

	@Schema(description= "省级")
	public static Integer PROVINCE = 1;

	@Schema(description= "市级")
	public static Integer CITY = 2;

	@Schema(description= "区级")
	public static Integer COUNTY = 3;

}