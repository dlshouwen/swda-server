package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * operation log query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "operation log query")
public class SysLogOperateQuery extends Query {

	@Schema(description = "real name")
	private String realName;

	@Schema(description = "module")
	private String module;

	@Schema(description = "request uri")
	private String reqUri;

	@Schema(description = "status")
	private Integer status;

}