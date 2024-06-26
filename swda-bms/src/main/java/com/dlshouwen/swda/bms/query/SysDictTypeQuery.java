package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * dict type query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "dict type query")
public class SysDictTypeQuery extends Query {

	@Schema(description = "dict type")
	private String dictType;

	@Schema(description = "dict name")
	private String dictName;

}