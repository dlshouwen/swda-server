package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict data_type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "数据类型")
public interface DataType {

	@Schema(description = "字符型")
	int Integer = 1;

	@Schema(description = "数值型")
	int NUMBER = 2;

	@Schema(description = "日期型")
	int DATE = 3;

	@Schema(description = "单选型")
	int SINGLE = 4;

	@Schema(description = "多选型")
	int MULTIPLE = 5;

}