package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict data_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "数据类型")
public class DataType {

	@Schema(description= "字符型")
	public static Integer Integer = 1;

	@Schema(description= "数值型")
	public static Integer NUMBER = 2;

	@Schema(description= "日期型")
	public static Integer DATE = 3;

	@Schema(description= "单选型")
	public static Integer SINGLE = 4;

	@Schema(description= "多选型")
	public static Integer MULTIPLE = 5;

}