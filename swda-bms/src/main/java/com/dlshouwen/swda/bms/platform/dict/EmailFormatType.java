package com.dlshouwen.swda.bms.platform.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict email format type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "email format type")
public interface EmailFormatType {

	@Schema(description= "text")
	Integer TEXT = 1;

	@Schema(description= "html")
	Integer HTML = 2;
	
	@Schema(description= "template")
	Integer TEMPLATE = 3;
	
}