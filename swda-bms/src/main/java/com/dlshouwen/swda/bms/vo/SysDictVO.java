package com.dlshouwen.swda.bms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * dict vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "dict")
public class SysDictVO {
	
	@Schema(description = "dict type")
	private String dictType;

	@Schema(description = "dict list")
	private List<DictData> dataList = new ArrayList<>();

	@Data
	@AllArgsConstructor
	@Schema(description = "dict")
	public static class DictData {

		@Schema(description = "label")
		private String dictLabel;

		@Schema(description = "value")
		private String dictValue;

		@Schema(description = "label class")
		private String labelClass;

	}

}
