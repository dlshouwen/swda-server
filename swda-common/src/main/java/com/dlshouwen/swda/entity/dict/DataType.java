package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict data_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="数据类型", description="数据类型")
public class DataType {

	@Schema(title="字符型")
	public static String STRING = "1";

	@Schema(title="数值型")
	public static String NUMBER = "2";

	@Schema(title="日期型")
	public static String DATE = "3";

	@Schema(title="单选型")
	public static String SINGLE = "4";

	@Schema(title="多选型")
	public static String MULTIPLE = "5";

}