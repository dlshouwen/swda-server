package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * params query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "params query")
public class SysParamsQuery extends Query {

	@Schema(description = "param type")
	private Integer paramType;

	@Schema(description = "param key")
	private String paramKey;

	@Schema(description = "param value")
	private String paramValue;

}