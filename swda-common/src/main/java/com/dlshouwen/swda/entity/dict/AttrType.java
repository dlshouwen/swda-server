package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict attr_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="参数类型", description="参数类型")
public class AttrType {

	@Schema(title="系统参数")
	public static String SYSTEM = "11";

	@Schema(title="页面参数")
	public static String PAGE = "12";

	@Schema(title="基础参数")
	public static String BASE = "13";

	@Schema(title="日志参数")
	public static String LOG = "14";

	@Schema(title="接口参数")
	public static String INTERFACE = "15";

}