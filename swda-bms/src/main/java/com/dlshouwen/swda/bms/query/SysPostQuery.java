package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * post query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "post query")
public class SysPostQuery extends Query {

	@Schema(description = "post code")
	private String postCode;

	@Schema(description = "post name")
	private String postName;

	@Schema(description = "status")
	private Integer status;

}
