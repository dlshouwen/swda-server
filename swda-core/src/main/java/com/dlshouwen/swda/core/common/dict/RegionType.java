package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict region_type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "区域类型")
public interface RegionType {

	@Schema(description = "省级")
	int PROVINCE = 1;

	@Schema(description = "市级")
	int CITY = 2;

	@Schema(description = "区级")
	int COUNTY = 3;

}