package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict region_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "region type")
public interface RegionType {

	@Schema(description = "province")
	String PROVINCE = "1";

	@Schema(description = "city")
	String CITY = "2";
	
	@Schema(description = "county")
	String COUNTY = "3";

}