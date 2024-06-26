package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数管理查询
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "参数管理查询")
public class SysParamsQuery extends Query {
	@Schema(description = "系统参数")
	private Integer paramType;

	@Schema(description = "参数键")
	private String paramKey;

	@Schema(description = "参数值")
	private String paramValue;

}